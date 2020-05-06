/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author User
 */
public class PackageGoodsDetailForm {

    private Long packageGoodsId;
    private Long packageGoodsDetailId;
    private Long stockTypeId;
    private String stockModelCode;
    private String stockModelName;
    private String groupCode;
    private String groupName;
    private String note;
    private Long packageGoodsMapId;

    public PackageGoodsDetailForm() {
    }

    public Long getPackageGoodsDetailId() {
        return packageGoodsDetailId;
    }

    public void setPackageGoodsDetailId(Long packageGoodsDetailId) {
        this.packageGoodsDetailId = packageGoodsDetailId;
    }

    public Long getPackageGoodsId() {
        return packageGoodsId;
    }

    public void setPackageGoodsId(Long packageGoodsId) {
        this.packageGoodsId = packageGoodsId;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPackageGoodsMapId() {
        return packageGoodsMapId;
    }

    public void setPackageGoodsMapId(Long packageGoodsMapId) {
        this.packageGoodsMapId = packageGoodsMapId;
    }
}
