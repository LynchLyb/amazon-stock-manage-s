package com.stock.manage.inventory.v1;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.stock.manage.common.Result;
import com.stock.manage.constant.enums.CommonDataSourceEnums;
import com.stock.manage.convertor.InventoryConvertor;
import com.stock.manage.convertor.ProductConvertor;
import com.stock.manage.entity.DailySalesRecordDO;
import com.stock.manage.entity.InventoryDO;
import com.stock.manage.entity.ProductDO;
import com.stock.manage.service.DailySalesRecordService;
import com.stock.manage.service.InventoryService;
import com.stock.manage.service.ProductService;
import com.stock.manage.vo.ProductDailySalesVO;
import com.stock.manage.vo.ProductMonthlySalesVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product-inventory")
@RequiredArgsConstructor
@Tag(name = "商品库存管理接口", description = "提供商品库存的接口")
public class InventoryController {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final DailySalesRecordService dailySalesRecordService;
    private final ProductConvertor productConvertor;
    private final InventoryConvertor inventoryConvertor;
    @GetMapping("/list")
    @Operation(summary = "获取商品库存和销量信息", description = "获取所有商品的库存和销量信息")
    public Result<List<ProductDailySalesVO>> getProductInventorySales() {
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

            // 3. 获取最新的日销量
            LocalDate startDate = LocalDate.now();

            // 4. 获取所有商品的日销量数据
            QueryWrapper<DailySalesRecordDO> salesWrapper = new QueryWrapper<>();
            salesWrapper.in("product_id", productIds)
                    .eq("record_date", startDate)
                    .eq("is_deleted", false)
                    .orderByDesc("record_date");
            List<DailySalesRecordDO> allSalesRecords = dailySalesRecordService.list(salesWrapper);

            // 5. 按商品ID分组销量数据
            Map<Long, DailySalesRecordDO> salesMap = allSalesRecords.stream()
                    .collect(Collectors.toMap(DailySalesRecordDO::getProductId, salesRecord -> salesRecord));

            // 6. 组装返回数据
            List<ProductDailySalesVO> result = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (ProductDO product : products) {
                ProductDailySalesVO vo = new ProductDailySalesVO();
                vo.setProduct(productConvertor.toVO(product));
                vo.setInventory(inventoryConvertor.toVO(inventoryMap.get(product.getId())));

                // 获取该商品的销量数据
                DailySalesRecordDO productSales = salesMap.getOrDefault(product.getId(), new DailySalesRecordDO());

                ProductDailySalesVO.DailySalesVO dailySalesVO = new ProductDailySalesVO.DailySalesVO();
                dailySalesVO.setQuantity(productSales.getQuantity());
                dailySalesVO.setDataSource(productSales.getDataSource());
                dailySalesVO.setDate(productSales.getRecordDate().format(formatter));
                vo.setDailySale(dailySalesVO);
                result.add(vo);
            }

            return Result.success(result);
        } catch (Exception e) {
            return Result.error("获取商品库存销量数据失败");
        }
    }
}
