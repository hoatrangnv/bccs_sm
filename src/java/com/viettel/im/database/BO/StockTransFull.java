package com.viettel.im.database.BO;

import com.viettel.im.client.bean.SaleTransDetailBean;
import java.util.Date;
import java.util.List;

/**
 * StockTransFull entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StockTransFull implements java.io.Serializable {

    // Fields
    private Long stt;
    private Long stockTransDetailId;
    private Long stockTypeId;
    private String stockTypeName;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private Long checkSerial;
    private Long checkDeposit;
    private Long checkDial;
    private String unit;
    private Long discountGroupId;
    private Long fromOwnerType;
    private Long fromOwnerId;
    private Long toOwnerType;
    private Long toOwnerId;
    private Date createDatetime;
    private Long stockTransType;
    private Long stockTransId;
    private Long reasonId;
    private Long stockTransStatus;
    private Long payStatus;
    private Long depositStatus;
    private String actionCode;
    private Long actionId;
    private Long actionType;
    private String username;
    private Long actionStaffId;
    private Long stateId;
    private Long quantity;//so luong
    private Long quantityReal;
    private String note;
    private Long basisPrice;
    private Long telecomServiceID;
    private Double price;//gia
    private String priceDisplay;//gia
    private Long priceId;//id cua gia
    private String stateName;
    //TrongLV
    //La hang hoa hay la goi dich vu
    private Long isService = 0L;
    private Long parentId;
    private Long parentPriceId;
    private List<SaleTransDetailBean> lstSaleServiceDetail;    //Danh sach mat hang trong goi hang
    private Long isInputSerial = 0L;
    private Double totalDepositPrice = 0.0;

    private Long channelTypeId;

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    

    public Long getIsInputSerial() {
        return isInputSerial;
    }

    public void setIsInputSerial(Long isInputSerial) {
        this.isInputSerial = isInputSerial;
    }

    public Long getIsService() {
        return isService;
    }

    public void setIsService(Long isService) {
        this.isService = isService;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentPriceId() {
        return parentPriceId;
    }

    public void setParentPriceId(Long parentPriceId) {
        this.parentPriceId = parentPriceId;
    }

    public List<SaleTransDetailBean> getLstSaleServiceDetail() {
        return lstSaleServiceDetail;
    }

    public void setLstSaleServiceDetail(List<SaleTransDetailBean> lstSaleServiceDetail) {
        this.lstSaleServiceDetail = lstSaleServiceDetail;
    }
    //TrongLV
    private List lstSerial;//=new ArrayList<SerialGoods>();
    private String[] arrSerial;//=new ArrayList<SerialGoods>();
    // Constructors

    /** default constructor */
    public StockTransFull() {
    }

    /** minimal constructor */
    public StockTransFull(Long stockTransDetailId, Long stockTypeId,
            String stockTypeName, Long stockModelId, String stockModelCode,
            String stockModelName, Long stockTransType) {
        this.stockTransDetailId = stockTransDetailId;
        this.stockTypeId = stockTypeId;
        this.stockTypeName = stockTypeName;
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.stockTransType = stockTransType;
    }

    // Property accessors
    public List getLstSerial() {
        return lstSerial;
    }

    public void setLstSerial(List lstSerial) {
        this.lstSerial = lstSerial;
    }

    public Long getStockTransDetailId() {
        return this.stockTransDetailId;
    }

    public void setStockTransDetailId(Long stockTransDetailId) {
        this.stockTransDetailId = stockTransDetailId;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }

    public Long getStockTypeId() {
        return this.stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getStockTypeName() {
        return this.stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public Long getStockModelId() {
        return this.stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelCode() {
        return this.stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public String getStockModelName() {
        return this.stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Long getCheckSerial() {
        return this.checkSerial;
    }

    public void setCheckSerial(Long checkSerial) {
        this.checkSerial = checkSerial;
    }

    public Long getCheckDeposit() {
        return this.checkDeposit;
    }

    public void setCheckDeposit(Long checkDeposit) {
        this.checkDeposit = checkDeposit;
    }

    public Long getCheckDial() {
        return this.checkDial;
    }

    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getDiscountGroupId() {
        return this.discountGroupId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
    }

    public Long getFromOwnerType() {
        return this.fromOwnerType;
    }

    public void setFromOwnerType(Long fromOwnerType) {
        this.fromOwnerType = fromOwnerType;
    }

    public Long getFromOwnerId() {
        return this.fromOwnerId;
    }

    public void setFromOwnerId(Long fromOwnerId) {
        this.fromOwnerId = fromOwnerId;
    }

    public Long getToOwnerType() {
        return this.toOwnerType;
    }

    public void setToOwnerType(Long toOwnerType) {
        this.toOwnerType = toOwnerType;
    }

    public Long getToOwnerId() {
        return this.toOwnerId;
    }

    public void setToOwnerId(Long toOwnerId) {
        this.toOwnerId = toOwnerId;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Long getStockTransType() {
        return this.stockTransType;
    }

    public void setStockTransType(Long stockTransType) {
        this.stockTransType = stockTransType;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public Long getReasonId() {
        return this.reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Long getStockTransStatus() {
        return this.stockTransStatus;
    }

    public void setStockTransStatus(Long stockTransStatus) {
        this.stockTransStatus = stockTransStatus;
    }

    public Long getPayStatus() {
        return this.payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public Long getDepositStatus() {
        return this.depositStatus;
    }

    public void setDepositStatus(Long depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getActionType() {
        return this.actionType;
    }

    public void setActionType(Long actionType) {
        this.actionType = actionType;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getActionStaffId() {
        return this.actionStaffId;
    }

    public void setActionStaffId(Long actionStaffId) {
        this.actionStaffId = actionStaffId;
    }

    public Long getStateId() {
        return this.stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityReal() {
        return this.quantityReal;
    }

    public void setQuantityReal(Long quantityReal) {
        this.quantityReal = quantityReal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String[] getArrSerial() {
        return arrSerial;
    }

    public void setArrSerial(String[] arrSerial) {
        this.arrSerial = arrSerial;
    }

    public Long getBasisPrice() {
        return basisPrice;
    }

    public void setBasisPrice(Long basisPrice) {
        this.basisPrice = basisPrice;
    }

    public Long getTelecomServiceID() {
        return telecomServiceID;
    }

    public void setTelecomServiceID(Long telecomServiceID) {
        this.telecomServiceID = telecomServiceID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPriceDisplay() {
        return priceDisplay;
    }

    public void setPriceDisplay(String priceDisplay) {
        this.priceDisplay = priceDisplay;
    }

    public Double getTotalDepositPrice() {
        return totalDepositPrice;
    }

    public void setTotalDepositPrice(Double totalDepositPrice) {
        this.totalDepositPrice = totalDepositPrice;
    }
}
