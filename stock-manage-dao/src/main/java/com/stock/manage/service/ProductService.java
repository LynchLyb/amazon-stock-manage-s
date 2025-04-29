package com.stock.manage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.stock.manage.entity.Product;

public interface ProductService extends IService<Product> {

    boolean logicalDeleteById(Long id);

    boolean physicalDeleteById(Long id);

    Product selectBySkuAndFnCode(String skuCode, String skuFnCode);

}