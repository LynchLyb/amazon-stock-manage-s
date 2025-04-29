package com.stock.manage.convertor;

import com.stock.manage.entity.Inventory;
import com.stock.manage.entity.Product;
import com.stock.manage.entity.SalesRecord;
import com.stock.manage.model.ExcelDataImportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ImportModelConvertor {


    @Mappings({
            @Mapping(target = "productId", source = "product.id"),
            @Mapping(target = "recordType", constant = "MONTH"),
            @Mapping(target = "quantity", source = "excelDataImportDTO.quantity"),
            @Mapping(target = "startDate", source = "excelDataImportDTO.startDate", ignore = true),
            @Mapping(target = "endDate", source = "excelDataImportDTO.endDate", ignore = true),
            @Mapping(target = "dataSource", constant = "IMPORT"),
    })
    SalesRecord excelDataModelToSaleRecordEntity(ExcelDataImportDTO excelDataImportDTO, Product product);


    @Mappings({
            @Mapping(target = "skuCode", source = "excelDataImportDTO.skuCode"),
            @Mapping(target = "skuFnCode", source = "excelDataImportDTO.skuFnCode"),
            @Mapping(target = "skuName", source = "excelDataImportDTO.skuName"),      // 直接赋空
            @Mapping(target = "memo", constant = ""),
            @Mapping(target = "extendInfo", constant = ""),
            @Mapping(target = "productStage", constant = ""),
            @Mapping(target = "isDeleted", constant = "0"),
    })
    Product excelDataModelToProductEntity(ExcelDataImportDTO excelDataImportDTO);


    @Mappings({
            @Mapping(target = "sellableQty", source = "sellableQty"),
            @Mapping(target = "reservedQty", source = "reservedQty"),
            @Mapping(target = "inboundQty", source = "inboundQty"),
            @Mapping(target = "dataSource", constant = "IMPORT"),
    })
    Inventory excelDataModelToInventoryEntity(ExcelDataImportDTO excelDataImportDTO);
}