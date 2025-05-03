package com.stock.manage.service;

import com.alibaba.excel.EasyExcel;
import com.stock.manage.convertor.ImportModelConvertor;
import com.stock.manage.entity.InventoryDO;
import com.stock.manage.entity.PeriodSalesRecordDO;
import com.stock.manage.entity.ProductDO;
import com.stock.manage.model.ExcelDataImportDTO;
import com.stock.manage.constant.enums.ProductStageEnums;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesRecordImportService {

    @Autowired
    private PeriodSalesRecordService periodSalesRecordService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ImportModelConvertor importModelConvertor;


    public void importSalesData(MultipartFile file, String uploadTemplateName) throws IOException {
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

        List<PeriodSalesRecordDO> records = new ArrayList<>();
        List<InventoryDO> inventories = new ArrayList<>();

        for (ExcelDataImportDTO dto : list) {
            //查询商品
            ProductDO productDO = productService.selectBySkuAndFnCode(dto.getSkuCode(), dto.getSkuFnCode());
            if (productDO == null) {
                productDO = importModelConvertor.excelDataModelToProductEntity(dto);
            }else {
                productDO.setProductStage(ProductStageEnums.getCodeByDescription(dto.getProductStage()));
            }
            productService.saveOrUpdate(productDO);

            // 查询库存记录
            InventoryDO inventoryDO = inventoryService.query()
                    .eq("product_id", productDO.getId())
                    .eq("is_deleted", 0)
                    .one();
            if (inventoryDO == null) {
                inventoryDO = importModelConvertor.excelDataModelToInventoryEntity(dto);
                inventoryDO.setProductId(productDO.getId());
            } else {
                inventoryDO.setSellableQty(dto.getSellableQty());
                inventoryDO.setReservedQty(dto.getReservedQty());
                inventoryDO.setInboundQty(dto.getInboundQty());
            }
            inventories.add(inventoryDO);


            PeriodSalesRecordDO record = importModelConvertor.excelDataModelToSaleRecordEntity(dto, productDO);
            if (record.getStartDate() == null) {
                record.setEndDate(LocalDate.now(ZoneId.of("Asia/Shanghai")));
                record.setStartDate(LocalDate.now(ZoneId.of("Asia/Shanghai")).minusMonths(1));
            }
            records.add(record);
        }

        // 批量保存
        inventoryService.saveOrUpdateBatch(inventories);
        periodSalesRecordService.saveOrUpdateBatch(records);
    }
}