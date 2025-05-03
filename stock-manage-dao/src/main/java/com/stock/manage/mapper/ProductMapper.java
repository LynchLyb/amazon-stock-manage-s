package com.stock.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stock.manage.entity.ProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 商品表 Mapper 接口
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {
    @Select("SELECT * FROM amazon_stock_manager.product WHERE sku_code = #{skuCode} AND sku_fn_code = #{skuFnCode} AND is_deleted = 0 LIMIT 1")
    ProductDO selectBySkuAndFnCode(@Param("skuCode") String skuCode, @Param("skuFnCode") String skuFnCode);

}