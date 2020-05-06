/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author thinhph2
 */
public class MapDebitDayTypeForm {
    private String shopCode;
    private Long shopId;
    private String shopName;
    private String debitDayTypeName;
    private String debitDayTypeShopId;
    private Long debitDayTypeId;
    private Long status;
    private Long id;
    private boolean checkAll;

    public String getDebitDayTypeShopId() {
        return debitDayTypeShopId;
    }

    public void setDebitDayTypeShopId(String debitDayTypeShopId) {
        this.debitDayTypeShopId = debitDayTypeShopId;
    }
    
    public void resetForm(){
        this.debitDayTypeId = null;
        this.shopCode = "";
        this.shopId = null;
        this.shopName = "";
        this.status = null;
        checkAll = false;
    }
    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDebitDayTypeName() {
        return debitDayTypeName;
    }

    public void setDebitDayTypeName(String debitDayTypeName) {
        this.debitDayTypeName = debitDayTypeName;
    }

    public Long getDebitDayTypeId() {
        return debitDayTypeId;
    }

    public void setDebitDayTypeId(Long debitDayTypeId) {
        this.debitDayTypeId = debitDayTypeId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void loadFormPreUpdate(MapDebitDayTypeForm input){
        this.debitDayTypeId = input.getDebitDayTypeId();
        this.status = input.getStatus();
        this.id = input.getId();
    }

    public boolean isCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }
    
    
}
