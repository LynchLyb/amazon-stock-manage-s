package com.stock.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stock.manage.mapper.ProductMapper;

import com.stock.manage.entity.Product;
import com.stock.manage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean logicalDeleteById(Long id) {
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("is_deleted", 1);
        return this.update(updateWrapper);
    }

    @Override
    public boolean physicalDeleteById(Long id) {
        return this.removeById(id);
    }

    @Override
    public Product selectBySkuAndFnCode(String skuCode, String skuFnCode) {
        return productMapper.selectBySkuAndFnCode(skuCode, skuFnCode);
    }

}