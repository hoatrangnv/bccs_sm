/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author haint
 */
public class PackageGoodsReplaceBean {

    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private Long packageGoodsReplaceId;

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Long getPackageGoodsReplaceId() {
        return packageGoodsReplaceId;
    }

    public void setPackageGoodsReplaceId(Long packageGoodsReplaceId) {
        this.packageGoodsReplaceId = packageGoodsReplaceId;
    }
    
    
}
