package com.stock.manage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stock.manage.entity.ProductDO;

public interface ProductService extends IService<ProductDO> {

    boolean logicalDeleteById(Long id);

    boolean physicalDeleteById(Long id);

    ProductDO selectBySkuAndFnCode(String skuCode, String skuFnCode);

}