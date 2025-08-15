package com.stock.manage.convertor;

import com.stock.manage.entity.ProductDO;
import com.stock.manage.vo.ProductVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductConvertor {


    /**
     * Converts a ProductDO object to a ProductVO object.
     *
     * @param product the ProductDO object to convert
     * @return the converted ProductVO object
     */

    ProductVO toVO(ProductDO product);
}
