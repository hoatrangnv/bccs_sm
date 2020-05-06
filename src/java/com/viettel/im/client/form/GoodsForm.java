/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.ArrayList;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author dattv
 */
public class GoodsForm extends ActionForm {

    private String actionCode; //ma lenh, phieu (bo sung)
    private String actionNote; //ghi chu doi voi lenh, phieu
    private Long stockTypeId; //loai hang
    private Long stockModelId;//Id mat hang
    private Long telecomServiceId;// Id dịch vụ
    private String stockModelCode;
    private String stockModelName;
    private String stockTypeName;
    private String unit;
    private Long quantity;
    private String quantityToString;
    private String note;
    private Long stateId;//Trang thai hang hoa (1: moi, 2: cu, 3: Hong)
    private String stateName;
    private Long ownerId;
    private String ownerCode;
    private Long ownerType;
    private String ownerName;
    private Long checkSerial;
    private Long checkDeposit;
    private String checkDial;
    //Thuoc tinh cua mat hang co the boc tham
    private Long canDial;
    //Loai giao dich xuat kho
    private String expType;
    //Co cho nhap serial khong
    private String inputSerial = "false";
    private String editable;
    //Kieu xem kho
    private String viewType = "";
    private String exportUrl;
    private List<SerialGoods> lstSerial;
    private String shopCode;
    private String shopName;
    private String staffCode;
    private String staffName;
    private String status;
    private String statusGoods;
    private Boolean isStaff;
    private Boolean isStaffOwner;
    private Boolean isShop;
    private Boolean isShopOwner;

    public Boolean getIsShop() {
        return isShop;
    }

    public void setIsShop(Boolean isShop) {
        this.isShop = isShop;
    }

    public Boolean getIsShopOwner() {
        return isShopOwner;
    }

    public void setIsShopOwner(Boolean isShopOwner) {
        this.isShopOwner = isShopOwner;
    }
    private String errorMessage;

    public Boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Boolean isStaff) {
        this.isStaff = isStaff;
    }

    public Boolean getIsStaffOwner() {
        return isStaffOwner;
    }

    public void setIsStaffOwner(Boolean isStaffOwner) {
        this.isStaffOwner = isStaffOwner;
    }
    //private String[] arrSerial ;

    public List<SerialGoods> getLstSerial() {
        return lstSerial;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public GoodsForm() {
        this.stockTypeId = 0L;
        this.stockModelId = 0L;
        this.telecomServiceId = 0L;
        this.stockModelCode = "";
        this.stockModelName = "";
        this.stockTypeName = "";
        this.unit = "";
        this.quantity = null;
        this.note = "";
        this.stateId = 0L;

        this.ownerId = 0L;
        this.ownerCode = "";
        this.ownerType = 0L;
        this.ownerName = "";

        this.checkDeposit = null;
        this.checkDial = null;
        this.checkSerial = null;
        this.lstSerial = new ArrayList<SerialGoods>();
        this.status = "";
        this.statusGoods = "";

    }

    public GoodsForm(Long stockTypeId, Long stockModelId, String stockModelCode, String stockModelName, String stockTypeName, String unit, Long quantity, String note, Long stateId, Long ownerId, String ownerCode, Long ownerType, String ownerName, List<SerialGoods> lstSerial, Long telecomServiceId, String statusGoods) {
        this.stockTypeId = stockTypeId;
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.stockTypeName = stockTypeName;
        this.unit = unit;
        this.quantity = quantity;
        this.note = note;
        this.stateId = stateId;
        this.ownerId = ownerId;
        this.ownerCode = ownerCode;
        this.ownerType = ownerType;
        this.ownerName = ownerName;
        this.lstSerial = lstSerial;
        this.telecomServiceId = telecomServiceId;
        this.statusGoods = statusGoods;
    }

    public void resetForm() {
        this.stockTypeId = 0L;
        this.stockModelId = 0L;
        this.telecomServiceId = 0L;
        this.stockModelCode = "";
        this.stockModelName = "";
        this.stockTypeName = "";
        this.unit = "";
        this.quantity = null;
        this.note = "";
        this.lstSerial = null;
        this.stateId = 0L;

        this.checkDeposit = null;
        this.checkDial = null;
        this.checkSerial = null;
        this.statusGoods = "";

        // this.ownerId=null;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getInputSerial() {
        return inputSerial;
    }

    public void setInputSerial(String inputSerial) {
        this.inputSerial = inputSerial;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public void setLstSerial(List<SerialGoods> lstSerial) {
        this.lstSerial = lstSerial;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

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

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Long getCheckDeposit() {
        return checkDeposit;
    }

    public void setCheckDeposit(Long checkDeposit) {
        this.checkDeposit = checkDeposit;
    }

    public String getCheckDial() {
        return checkDial;
    }

    public void setCheckDial(String checkDial) {
        this.checkDial = checkDial;
    }

    public Long getCheckSerial() {
        return checkSerial;
    }

    public void setCheckSerial(Long checkSerial) {
        this.checkSerial = checkSerial;
    }

    public Long getCanDial() {
        return canDial;
    }

    public void setCanDial(Long canDial) {
        this.canDial = canDial;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusGoods() {
        return statusGoods;
    }

    public void setStatusGoods(String statusGoods) {
        this.statusGoods = statusGoods;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionNote() {
        return actionNote;
    }

    public void setActionNote(String actionNote) {
        this.actionNote = actionNote;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setQuantityToString(String quantityToString) {
        this.quantityToString = quantityToString;
    }

    public String getQuantityToString() {
        return quantityToString;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
