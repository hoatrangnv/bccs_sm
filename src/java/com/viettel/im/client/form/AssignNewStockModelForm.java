package com.viettel.im.client.form;

/**
 *
 * @author tamdt1, 05/06/2009
 * form chua cac thong tin phuc vu viec gan mat hang moi cho tai nguyen
 *
 */
public class AssignNewStockModelForm {
    //phan tim kiem thong tin ve dai serial can cap nhat mat hang

    private Long stockTypeId;
    private Long shopId;
    private String shopCode;
    private String shopName;
    private Long stockModelId;
    private String fromSerial;
    private String toSerial;
    private Long stateId;
    //thong tin ve stockModel moi
    private Long newStockModelId;
    //
    private Long realQuantity; //so luong serial thuc trong dai

    public void resetForm() {
        stockTypeId = 0L;
        shopId = 0L;
        shopCode = "";
        shopName = "";
        stockModelId = 0L;
        fromSerial = "";
        toSerial = "";
        stateId = 0L;
        newStockModelId = 0L;
        realQuantity = 0L;
    }

    public AssignNewStockModelForm() {
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public Long getNewStockModelId() {
        return newStockModelId;
    }

    public void setNewStockModelId(Long newStockModelId) {
        this.newStockModelId = newStockModelId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(Long realQuantity) {
        this.realQuantity = realQuantity;
    }
}
