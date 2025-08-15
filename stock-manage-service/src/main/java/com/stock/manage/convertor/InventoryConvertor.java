package com.stock.manage.convertor;


import com.stock.manage.entity.InventoryDO;
import com.stock.manage.vo.InventoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface InventoryConvertor {

    @Mappings({
            // Add any specific mappings here if needed
    })
    InventoryVO toVO(InventoryDO inventoryDO);
}
