package com.quark.api.auto.bean;
/**
 * @author kingsley
 * @copyright quarktimes.com
 * @datetime 2016-11-30 09:07:14
 *
 */
public class ProductListResult{
   //page number
   public ProductList ProductList;

    public com.quark.api.auto.bean.ProductList getProductList() {
        return ProductList;
    }

    public void setProductList(com.quark.api.auto.bean.ProductList productList) {
        ProductList = productList;
    }
}