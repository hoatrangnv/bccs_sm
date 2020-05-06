package com.viettel.im.database.BO;

import java.util.Date;

/**
 * BlockDetail entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class ReqCardDetailNotSale implements java.io.Serializable {

    // Fields
    private Long reqDetailId;
    private Long reqId;
    private String serial;
    private Long stockModelId;
    private Long ownerId;
    private String ownerType;
    private Long lockStaffId;
    private Long unLockStaffId;
    private Long status;
    private String custName;
    private String reqNo;
    private Long shopBranchId;
    private Long shopId;
    private String staffCodePunish;
    private String staffNamePunish;
    private String unitCodePunish;
    private String unitChiefCodePunish;
    private String unitChiefNamePunish;
    private String unitChiefPhonePunish;
    private String saleStaffCodePunish;
    
    private String trueStaffCode;
    private String trueStaffName;
    private String trueUnitChiefCode;
    private String trueUnitChiefName;
    private String trueUnitChiefPhone;

    // Constructors
    /**
     * default constructor
     */
    public ReqCardDetailNotSale() {
    }

    /**
     * minimal constructor
     */
    public ReqCardDetailNotSale(Long reqDetailId) {
        this.reqDetailId = reqDetailId;
    }

    /**
     * full constructor
     */
    public ReqCardDetailNotSale(Long reqDetailId, Long reqId, String serial,
            Long stockModelId, Long ownerId, String ownerType,
            Long lockStaffId, Long unLockStaffId, Long status, String custName, String reqNo) {
        this.reqDetailId = reqDetailId;
        this.reqId = reqId;
        this.serial = serial;
        this.stockModelId = stockModelId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.lockStaffId = lockStaffId;
        this.unLockStaffId = unLockStaffId;
        this.custName = custName;
        this.status = status;
        this.reqNo = reqNo;
    }

    public ReqCardDetailNotSale(Long reqDetailId, Long reqId, String serial, Long stockModelId, Long ownerId, String ownerType, Long lockStaffId, Long unLockStaffId, Long status, String custName, String reqNo, Long shopBranchId, Long shopId, String staffCodePunish, String staffNamePunish, String unitCodePunish, String unitChiefCodePunish, String unitChiefNamePunish, String unitChiefPhonePunish, String saleStaffCodePunish) {
        this.reqDetailId = reqDetailId;
        this.reqId = reqId;
        this.serial = serial;
        this.stockModelId = stockModelId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.lockStaffId = lockStaffId;
        this.unLockStaffId = unLockStaffId;
        this.status = status;
        this.custName = custName;
        this.reqNo = reqNo;
        this.shopBranchId = shopBranchId;
        this.shopId = shopId;
        this.staffCodePunish = staffCodePunish;
        this.staffNamePunish = staffNamePunish;
        this.unitCodePunish = unitCodePunish;
        this.unitChiefCodePunish = unitChiefCodePunish;
        this.unitChiefNamePunish = unitChiefNamePunish;
        this.unitChiefPhonePunish = unitChiefPhonePunish;
        this.saleStaffCodePunish = saleStaffCodePunish;
    }

    public ReqCardDetailNotSale(Long reqDetailId, Long reqId, String serial, Long stockModelId, Long ownerId, String ownerType, Long lockStaffId, Long unLockStaffId, Long status, String custName, String reqNo, Long shopBranchId, Long shopId, String staffCodePunish, String staffNamePunish, String unitCodePunish, String unitChiefCodePunish, String unitChiefNamePunish, String unitChiefPhonePunish, String saleStaffCodePunish, String trueStaffCode, String trueStaffName, String trueUnitChiefCode, String trueUnitChiefName, String trueUnitChiefPhone) {
        this.reqDetailId = reqDetailId;
        this.reqId = reqId;
        this.serial = serial;
        this.stockModelId = stockModelId;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.lockStaffId = lockStaffId;
        this.unLockStaffId = unLockStaffId;
        this.status = status;
        this.custName = custName;
        this.reqNo = reqNo;
        this.shopBranchId = shopBranchId;
        this.shopId = shopId;
        this.staffCodePunish = staffCodePunish;
        this.staffNamePunish = staffNamePunish;
        this.unitCodePunish = unitCodePunish;
        this.unitChiefCodePunish = unitChiefCodePunish;
        this.unitChiefNamePunish = unitChiefNamePunish;
        this.unitChiefPhonePunish = unitChiefPhonePunish;
        this.saleStaffCodePunish = saleStaffCodePunish;
        this.trueStaffCode = trueStaffCode;
        this.trueStaffName = trueStaffName;
        this.trueUnitChiefCode = trueUnitChiefCode;
        this.trueUnitChiefName = trueUnitChiefName;
        this.trueUnitChiefPhone = trueUnitChiefPhone;
    }
    
    // Property accessors
    public Long getReqId() {
        return this.reqId;
    }

    public void setReqId(Long reqId) {
        this.reqId = reqId;
    }

    public Long getReqDetailId() {
        return this.reqDetailId;
    }

    public void setReqDetailId(Long reqDetailId) {
        this.reqDetailId = reqDetailId;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getStockModelId() {
        return this.stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Long getLockStaffId() {
        return this.lockStaffId;
    }

    public void setLockStaffId(Long lockStaffId) {
        this.lockStaffId = lockStaffId;
    }

    public Long getUnLockStaffId() {
        return this.unLockStaffId;
    }

    public void setUnLockStaffId(Long unLockStaffId) {
        this.unLockStaffId = unLockStaffId;
    }

    public String getCustName() {
        return this.custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getReqNo() {
        return this.reqNo;
    }

    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    public Long getShopBranchId() {
        return this.shopBranchId;
    }

    public void setShopBranchId(Long shopBranchId) {
        this.shopBranchId = shopBranchId;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getStaffCodePunish() {
        return staffCodePunish;
    }

    public void setStaffCodePunish(String staffCodePunish) {
        this.staffCodePunish = staffCodePunish;
    }

    public String getStaffNamePunish() {
        return staffNamePunish;
    }

    public void setStaffNamePunish(String staffNamePunish) {
        this.staffNamePunish = staffNamePunish;
    }

    public String getUnitCodePunish() {
        return unitCodePunish;
    }

    public void setUnitCodePunish(String unitCodePunish) {
        this.unitCodePunish = unitCodePunish;
    }

    public String getUnitChiefCodePunish() {
        return unitChiefCodePunish;
    }

    public void setUnitChiefCodePunish(String unitChiefCodePunish) {
        this.unitChiefCodePunish = unitChiefCodePunish;
    }

    public String getUnitChiefNamePunish() {
        return unitChiefNamePunish;
    }

    public void setUnitChiefNamePunish(String unitChiefNamePunish) {
        this.unitChiefNamePunish = unitChiefNamePunish;
    }

    public String getUnitChiefPhonePunish() {
        return unitChiefPhonePunish;
    }

    public void setUnitChiefPhonePunish(String unitChiefPhonePunish) {
        this.unitChiefPhonePunish = unitChiefPhonePunish;
    }

    public String getSaleStaffCodePunish() {
        return saleStaffCodePunish;
    }

    public void setSaleStaffCodePunish(String saleStaffCodePunish) {
        this.saleStaffCodePunish = saleStaffCodePunish;
    }

    public String getTrueStaffCode() {
        return trueStaffCode;
    }

    public void setTrueStaffCode(String trueStaffCode) {
        this.trueStaffCode = trueStaffCode;
    }

    public String getTrueStaffName() {
        return trueStaffName;
    }

    public void setTrueStaffName(String trueStaffName) {
        this.trueStaffName = trueStaffName;
    }

    public String getTrueUnitChiefCode() {
        return trueUnitChiefCode;
    }

    public void setTrueUnitChiefCode(String trueUnitChiefCode) {
        this.trueUnitChiefCode = trueUnitChiefCode;
    }

    public String getTrueUnitChiefName() {
        return trueUnitChiefName;
    }

    public void setTrueUnitChiefName(String trueUnitChiefName) {
        this.trueUnitChiefName = trueUnitChiefName;
    }

    public String getTrueUnitChiefPhone() {
        return trueUnitChiefPhone;
    }

    public void setTrueUnitChiefPhone(String trueUnitChiefPhone) {
        this.trueUnitChiefPhone = trueUnitChiefPhone;
    }
    
}