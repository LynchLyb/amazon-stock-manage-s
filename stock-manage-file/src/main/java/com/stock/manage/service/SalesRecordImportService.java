package com.stock.manage.service;

import com.alibaba.excel.EasyExcel;
import com.stock.manage.convertor.ImportModelConvertor;
import com.stock.manage.entity.Inventory;
import com.stock.manage.entity.Product;
import com.stock.manage.entity.SalesRecord;
import com.stock.manage.model.ExcelDataImportDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesRecordImportService {

    @Autowired
    private SalesRecordService salesRecordService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ImportModelConvertor importModelConvertor;


    public void importSalesData(MultipartFile file) throws IOException {
        List<ExcelDataImportDTO> list = EasyExcel.read(file.getInputStream())
                .head(ExcelDataImportDTO.class)
                .sheet()
                .doReadSync();

        list.removeIf(excelDataImportDTO ->
                excelDataImportDTO.getSkuName() == null || excelDataImportDTO.getSkuName().trim().isEmpty());
        list.forEach(excelDataImportDTO -> {
            excelDataImportDTO.setSkuName(excelDataImportDTO.getSkuName().replaceAll("\n", ""));
        });

        if (CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("导入数据为空");
        }

        List<SalesRecord> records = new ArrayList<>();

        for (ExcelDataImportDTO dto : list) {
            //查询商品
            Product product = productService.selectBySkuAndFnCode(dto.getSkuCode(), dto.getSkuFnCode());
            if (product == null) {
                product = importModelConvertor.excelDataModelToProductEntity(dto);
                productService.save(product);
            }

            // 查询库存记录
            Inventory inventory = inventoryService.query()
                    .eq("product_id", product.getId())
                    .eq("is_deleted", 0)
                    .one();
            if (inventory == null) {
                inventory = importModelConvertor.excelDataModelToInventoryEntity(dto);
                inventory.setProductId(product.getId());
            } else {
                inventory.setSellableQty(dto.getSellableQty());
                inventory.setReservedQty(dto.getReservedQty());
                inventory.setInboundQty(dto.getInboundQty());
            }

            inventoryService.saveOrUpdate(inventory);

            SalesRecord record = importModelConvertor.excelDataModelToSaleRecordEntity(dto, product);
            if (record.getStartDate() == null) {
                record.setStartDate(new Date());
            }
            records.add(record);
        }

        // 批量保存
        salesRecordService.saveOrUpdateBatch(records);
    }
}