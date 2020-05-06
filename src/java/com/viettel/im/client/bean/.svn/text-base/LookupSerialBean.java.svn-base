package com.viettel.im.client.bean;

import java.util.Date;

/**
 *
 * @author tamdt1
 * 
 */
public class LookupSerialBean implements Comparable<LookupSerialBean> {

    private String serial;
    private String stockModelCode;
    private String stockModelName;
    private Long stateId;
    private Long ownerType;
    private Long ownerId;
    private String ownerCode; //co the la shopCode hoac staffCode
    private String ownerName;
    private Long status;
    private Long activeStatus; //trang thai kich hoat the cao : 2 : dang cho huy kich hoat + 3 : huy kich hoat that bai + : chua kich hoạt/kich hoạt thanh cong
    private Date activeDate; //ngay kich hoat
    private String contractCode;
    private String batchCode;

    public Long getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Long activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public int compareTo(LookupSerialBean o) {
        if (!this.ownerType.equals(o.ownerType)) {
            return this.ownerType.compareTo(o.ownerType);
        }
        if (!this.ownerId.equals(o.ownerId)) {
            return this.ownerId.compareTo(o.ownerId);
        }
        if (!this.stockModelCode.equals(o.stockModelCode)) {
            return this.stockModelCode.compareTo(o.stockModelCode);
        }
        if (!this.stateId.equals(o.stateId)) {
            return this.stateId.compareTo(o.stateId);
        }
        if (!this.serial.equals(o.serial)) {
            return this.serial.compareTo(o.serial);
        }
        return 0;

    }

    @Override
    public boolean equals(Object obj1) {
        LookupSerialBean obj = (LookupSerialBean) obj1;
        return (this.ownerType.equals(obj.ownerType) && this.ownerId.equals(obj.ownerId)
                && this.stockModelCode.equals(obj.stockModelCode) && this.stateId.equals(obj.stateId) && this.serial.equals(obj.serial));
    }

    public LookupSerialBean(String serial, String stockModelCode, String stockModelName, Long stateId,
            Long ownerType, Long ownerId, String ownerCode, String ownerName, Long status) {
        this.serial = serial;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.stateId = stateId;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
        this.status = status;
    }

    public LookupSerialBean(String serial, String stockModelCode, String stockModelName, String contractCode, String batchCode, Long stateId,
            Long ownerType, Long ownerId, String ownerCode, String ownerName, Long status) {
        this.serial = serial;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.contractCode = contractCode;
        this.batchCode = batchCode;
        this.stateId = stateId;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.ownerCode = ownerCode;
        this.ownerName = ownerName;
        this.status = status;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    
    
}
