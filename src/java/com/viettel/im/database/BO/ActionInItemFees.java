package com.viettel.im.database.BO;

import java.util.Date;

/**
 * ActionInItemFeesId entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class ActionInItemFees implements java.io.Serializable {

    // Fields
    private Long id;
    private Long itemId;
    private String itemName;
    private Long feeId;
    private Long fee;
    private Long vat;
    private Long channelTypeId;
    private Long staffId;
    private String staffName;
    private Long shopId;
    private String shopName;
    private Date billCycle;
    private Long quantity;
    private Date startDate;
    private Date endDate;
    private Long totalMoney;
    private Long taxMoney;
    private Long telecomServiceId;
    private Long status;
    private Long payStatus;
    private Long approved;
    private Date createDate;

    // Constructors
    /** default constructor */
    public ActionInItemFees() {
    }

    /** full constructor */
    public ActionInItemFees(Long itemId, String itemName, Long feeId,
            Long fee, Long vat, Long channelTypeId, Long staffId,
            String staffName, Long shopId, String shopName, Date billCycle,
            Long quantity, Date startDate, Date endDate, Long totalMoney,
            Long taxMoney, Long telecomServiceId, Long status,
            Long payStatus, Long approved, Date createDate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.feeId = feeId;
        this.fee = fee;
        this.vat = vat;
        this.channelTypeId = channelTypeId;
        this.staffId = staffId;
        this.staffName = staffName;
        this.shopId = shopId;
        this.shopName = shopName;
        this.billCycle = billCycle;
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalMoney = totalMoney;
        this.taxMoney = taxMoney;
        this.telecomServiceId = telecomServiceId;
        this.status = status;
        this.payStatus = payStatus;
        this.approved = approved;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Property accessors
    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getFeeId() {
        return this.feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public Long getFee() {
        return this.fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getVat() {
        return this.vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public Long getChannelTypeId() {
        return this.channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return this.staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getBillCycle() {
        return this.billCycle;
    }

    public void setBillCycle(Date billCycle) {
        this.billCycle = billCycle;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getTotalMoney() {
        return this.totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getTaxMoney() {
        return this.taxMoney;
    }

    public void setTaxMoney(Long taxMoney) {
        this.taxMoney = taxMoney;
    }

    public Long getTelecomServiceId() {
        return this.telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getPayStatus() {
        return this.payStatus;
    }

    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    public Long getApproved() {
        return this.approved;
    }

    public void setApproved(Long approved) {
        this.approved = approved;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}