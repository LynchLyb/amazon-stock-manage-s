package com.stock.manage.convertor;

import com.stock.manage.entity.InventoryDO;
import com.stock.manage.entity.PeriodSalesRecordDO;
import com.stock.manage.entity.ProductDO;
import com.stock.manage.model.ExcelDataImportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ImportModelConvertor {


    @Mappings({
            @Mapping(target = "productId", source = "productDO.id"),
            @Mapping(target = "quantity", source = "excelDataImportDTO.quantity"),
            @Mapping(target = "startDate", source = "excelDataImportDTO.startDate", ignore = true),
            @Mapping(target = "endDate", source = "excelDataImportDTO.endDate", ignore = true),
            @Mapping(target = "dataSource", constant = "IMPORT"),
            @Mapping(target = "isDeleted", ignore = true),
    })
    PeriodSalesRecordDO excelDataModelToSaleRecordEntity(ExcelDataImportDTO excelDataImportDTO, ProductDO productDO);


    @Mappings({
            @Mapping(target = "skuCode", source = "excelDataImportDTO.skuCode"),
            @Mapping(target = "skuFnCode", source = "excelDataImportDTO.skuFnCode"),
            @Mapping(target = "skuName", source = "excelDataImportDTO.skuName"),      // 直接赋空
            @Mapping(target = "memo", ignore = true),
            @Mapping(target = "extendInfo", ignore = true),
            @Mapping(target = "productStage", ignore = true),
            @Mapping(target = "isDeleted", ignore = true),
    })
    ProductDO excelDataModelToProductEntity(ExcelDataImportDTO excelDataImportDTO);


    @Mappings({
            @Mapping(target = "sellableQty", source = "sellableQty"),
            @Mapping(target = "reservedQty", source = "reservedQty"),
            @Mapping(target = "inboundQty", source = "inboundQty"),
            @Mapping(target = "dataSource", constant = "IMPORT"),
            @Mapping(target = "isDeleted", ignore = true),
    })
    InventoryDO excelDataModelToInventoryEntity(ExcelDataImportDTO excelDataImportDTO);
}