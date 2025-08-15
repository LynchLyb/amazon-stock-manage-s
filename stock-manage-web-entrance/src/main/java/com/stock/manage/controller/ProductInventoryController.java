package com.stock.manage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.manage.convertor.InventoryConvertor;
import com.stock.manage.convertor.ProductConvertor;
import com.stock.manage.entity.DailySalesRecordDO;
import com.stock.manage.entity.InventoryDO;
import com.stock.manage.entity.ProductDO;
import com.stock.manage.service.DailySalesRecordService;
import com.stock.manage.service.InventoryService;
import com.stock.manage.service.ProductService;
import com.stock.manage.vo.ProductMonthlySalesVO;
import com.stock.manage.common.Result;
import com.stock.manage.vo.ProductInventorySalesVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product-inventory")
@RequiredArgsConstructor
@Tag(name = "商品库存管理接口", description = "提供商品库存和销量相关的接口")
public class ProductInventoryController {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final DailySalesRecordService dailySalesRecordService;
    private final ProductConvertor productConvertor;
    private final InventoryConvertor inventoryConvertor;

    @GetMapping("/list")
    @Operation(summary = "获取商品库存和销量信息", description = "获取所有商品的库存和销量信息")
    public Result<List<ProductMonthlySalesVO>> getProductInventorySales() {
        try {
            // 1. 获取所有未删除的商品
            List<ProductDO> products = productService.lambdaQuery()
                    .eq(ProductDO::getIsDeleted, false)
                    .list();

            // 2. 获取所有商品的库存信息
            List<Long> productIds = products.stream()
                    .map(ProductDO::getId)
                    .collect(Collectors.toList());
            
            Map<Long, InventoryDO> inventoryMap = inventoryService.lambdaQuery()
                    .in(InventoryDO::getProductId, productIds)
                    .eq(InventoryDO::getIsDeleted, false)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(InventoryDO::getProductId, inventory -> inventory));

            // 3. 获取最近30天的日期范围
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusDays(29);

            // 4. 获取所有商品的日销量数据
            QueryWrapper<DailySalesRecordDO> salesWrapper = new QueryWrapper<>();
            salesWrapper.in("product_id", productIds)
                    .between("record_date", startDate, endDate)
                    .eq("is_deleted", false)
                    .orderByDesc("record_date");
            List<DailySalesRecordDO> allSalesRecords = dailySalesRecordService.list(salesWrapper);

            // 5. 按商品ID分组销量数据
            Map<Long, List<DailySalesRecordDO>> salesMap = allSalesRecords.stream()
                    .collect(Collectors.groupingBy(DailySalesRecordDO::getProductId));

            // 6. 组装返回数据
            List<ProductMonthlySalesVO> result = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (ProductDO product : products) {
                ProductMonthlySalesVO vo = new ProductMonthlySalesVO();
                vo.setProduct(productConvertor.toVO(product));
                vo.setInventory(inventoryConvertor.toVO(inventoryMap.get(product.getId())));

                // 获取该商品的销量数据
                List<DailySalesRecordDO> productSales = salesMap.getOrDefault(product.getId(), new ArrayList<>());
                
                // 生成30天的日期列表（降序）
                List<ProductMonthlySalesVO.DailySalesVO> dailySalesVOs = new ArrayList<>();
                for (LocalDate date = endDate; !date.isBefore(startDate); date = date.minusDays(1)) {
                    final LocalDate currentDate = date;
                    ProductMonthlySalesVO.DailySalesVO dailySalesVO = new ProductMonthlySalesVO.DailySalesVO();
                    dailySalesVO.setDate(currentDate.format(formatter));
                    
                    // 查找当天的销量数据
                    int quantity = productSales.stream()
                            .filter(record -> record.getRecordDate().equals(currentDate))
                            .mapToInt(DailySalesRecordDO::getQuantity)
                            .sum();
                    
                    // 获取数据来源
                    String dataSource = productSales.stream()
                            .filter(record -> record.getRecordDate().equals(currentDate))
                            .findFirst()
                            .map(DailySalesRecordDO::getDataSource)
                            .orElse("未知");
                    
                    dailySalesVO.setQuantity(quantity);
                    dailySalesVO.setDataSource(dataSource);
                    dailySalesVOs.add(dailySalesVO);
                }
                
                vo.setDailySales(dailySalesVOs);
                result.add(vo);
            }

            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取商品库存销量数据失败");
        }
    }

    @PostMapping("/{id}/daily-sales")
    @Operation(summary = "更新商品日销量", description = "更新指定商品的日销量信息")
    public Result<Void> updateDailySales(
            @Parameter(description = "商品ID") @PathVariable Long id,
            @Parameter(description = "销量") @RequestParam Integer quantity,
            @Parameter(description = "数据来源") @RequestParam(required = false, defaultValue = "手动更新") String dataSource) {
        try {
            // 1. 检查商品是否存在
            ProductDO product = productService.lambdaQuery()
                    .eq(ProductDO::getId, id)
                    .eq(ProductDO::getIsDeleted, false)
                    .one();

            if (product == null) {
                return Result.error("商品不存在");
            }

            // 2. 获取今天的日期
            LocalDate today = LocalDate.now();

            // 3. 查找今天的销量记录
            DailySalesRecordDO salesRecord = dailySalesRecordService.lambdaQuery()
                    .eq(DailySalesRecordDO::getProductId, id)
                    .eq(DailySalesRecordDO::getRecordDate, today)
                    .eq(DailySalesRecordDO::getIsDeleted, false)
                    .one();

            if (salesRecord == null) {
                // 4. 如果不存在，创建新记录
                salesRecord = new DailySalesRecordDO();
                salesRecord.setProductId(id);
                salesRecord.setRecordDate(today);
                salesRecord.setQuantity(quantity);
                salesRecord.setDataSource(dataSource != null ? dataSource : "手动更新");
                salesRecord.setIsDeleted(false);
                dailySalesRecordService.save(salesRecord);
            } else {
                // 5. 如果存在，更新记录
                salesRecord.setQuantity(quantity);
                if (dataSource != null) {
                    salesRecord.setDataSource(dataSource);
                }
                dailySalesRecordService.updateById(salesRecord);
            }

            return Result.success();
        } catch (Exception e) {
            return Result.error("更新日销量数据失败");
        }
    }

    @PostMapping("/{id}/inventory")
    @Operation(summary = "更新商品库存", description = "更新指定商品的库存信息")
    public Result<Void> updateInventory(
            @Parameter(description = "商品ID") @PathVariable Long id,
            @Parameter(description = "可售数量") @RequestParam Integer sellableQty,
            @Parameter(description = "预留数量") @RequestParam Integer reservedQty,
            @Parameter(description = "入库数量") @RequestParam Integer inboundQty) {
        try {
            // 1. 检查商品是否存在
            ProductDO product = productService.lambdaQuery()
                    .eq(ProductDO::getId, id)
                    .eq(ProductDO::getIsDeleted, false)
                    .one();

            if (product == null) {
                return Result.error("商品不存在");
            }

            // 2. 查找库存记录
            InventoryDO inventory = inventoryService.lambdaQuery()
                    .eq(InventoryDO::getProductId, id)
                    .eq(InventoryDO::getIsDeleted, false)
                    .one();

            if (inventory == null) {
                // 3. 如果不存在，创建新记录
                inventory = new InventoryDO();
                inventory.setProductId(id);
                inventory.setSellableQty(sellableQty);
                inventory.setReservedQty(reservedQty);
                inventory.setInboundQty(inboundQty);
                inventory.setIsDeleted(false);
                inventoryService.save(inventory);
            } else {
                // 4. 如果存在，更新记录
                inventory.setSellableQty(sellableQty);
                inventory.setReservedQty(reservedQty);
                inventory.setInboundQty(inboundQty);
                inventoryService.updateById(inventory);
            }

            return Result.success();
        } catch (Exception e) {
            return Result.error("更新库存数据失败");
        }
    }
} 