package com.viettel.im.database.BO;

import java.util.Date;

/**
 * ViewStockSim entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ViewStockSim implements java.io.Serializable {

    // Fields
    private Long id;
    private Long stockModelId;
    private String imsi;
    private String iccid;
    private String pin;
    private String puk;
    private String pin2;
    private String puk2;
    private String isdn;
    private String hlrId;
    private String simType;
    private String aucStatus;
    private Date aucRegDate;
    private Date aucRemoveDate;
    private Long hlrStatus;
    private Date hlrRegDate;
    private Date hlrRemoveDate;
    private Long ownerId;
    private Long ownerType;
    private Long oldOwnerId;
    private Long oldOwnerType;
    private Long status;
    private String adm1;
    private String eki;
    private String serial;
    private Long stateId;
    private Long checkDial;
    private Long dialStatus;
    private String a3a8;
    private Long channelTypeId;
    private String contractCode;
    private String batchCode;

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
    // Constructors

    /** default constructor */
    public ViewStockSim() {
    }

    /** minimal constructor */
    public ViewStockSim(Long id, String imsi) {
        this.id = id;
        this.imsi = imsi;
    }

    public ViewStockSim(String serial,Long stockModelId) {
        this.stockModelId = stockModelId;
        this.serial = serial;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public ViewStockSim(Long id, Long stockModelId, String imsi, String iccid, String pin, String puk, String pin2, String puk2, String isdn, String hlrId, String simType, String aucStatus, Date aucRegDate, Date aucRemoveDate, Long hlrStatus, Date hlrRegDate, Date hlrRemoveDate, Long ownerId, Long ownerType, Long oldOwnerId, Long oldOwnerType, Long status, String adm1, String eki, String serial, Long stateId) {
        this.id = id;
        this.stockModelId = stockModelId;
        this.imsi = imsi;
        this.iccid = iccid;
        this.pin = pin;
        this.puk = puk;
        this.pin2 = pin2;
        this.puk2 = puk2;
        this.isdn = isdn;
        this.hlrId = hlrId;
        this.simType = simType;
        this.aucStatus = aucStatus;
        this.aucRegDate = aucRegDate;
        this.aucRemoveDate = aucRemoveDate;
        this.hlrStatus = hlrStatus;
        this.hlrRegDate = hlrRegDate;
        this.hlrRemoveDate = hlrRemoveDate;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.oldOwnerId = oldOwnerId;
        this.oldOwnerType = oldOwnerType;
        this.status = status;
        this.adm1 = adm1;
        this.eki = eki;
        this.serial = serial;
        this.stateId = stateId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockModelId() {
        return this.stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getImsi() {
        return this.imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getIccid() {
        return this.iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPuk() {
        return this.puk;
    }

    public void setPuk(String puk) {
        this.puk = puk;
    }

    public String getPin2() {
        return this.pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public String getPuk2() {
        return this.puk2;
    }

    public void setPuk2(String puk2) {
        this.puk2 = puk2;
    }

    public String getIsdn() {
        return this.isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getHlrId() {
        return this.hlrId;
    }

    public void setHlrId(String hlrId) {
        this.hlrId = hlrId;
    }

    public String getSimType() {
        return this.simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    public String getAucStatus() {
        return this.aucStatus;
    }

    public void setAucStatus(String aucStatus) {
        this.aucStatus = aucStatus;
    }

    public Date getAucRegDate() {
        return this.aucRegDate;
    }

    public void setAucRegDate(Date aucRegDate) {
        this.aucRegDate = aucRegDate;
    }

    public Date getAucRemoveDate() {
        return this.aucRemoveDate;
    }

    public void setAucRemoveDate(Date aucRemoveDate) {
        this.aucRemoveDate = aucRemoveDate;
    }

    public Long getHlrStatus() {
        return this.hlrStatus;
    }

    public void setHlrStatus(Long hlrStatus) {
        this.hlrStatus = hlrStatus;
    }

    public Date getHlrRegDate() {
        return this.hlrRegDate;
    }

    public void setHlrRegDate(Date hlrRegDate) {
        this.hlrRegDate = hlrRegDate;
    }

    public Date getHlrRemoveDate() {
        return this.hlrRemoveDate;
    }

    public void setHlrRemoveDate(Date hlrRemoveDate) {
        this.hlrRemoveDate = hlrRemoveDate;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public Long getOldOwnerId() {
        return this.oldOwnerId;
    }

    public void setOldOwnerId(Long oldOwnerId) {
        this.oldOwnerId = oldOwnerId;
    }

    public Long getOldOwnerType() {
        return this.oldOwnerType;
    }

    public void setOldOwnerType(Long oldOwnerType) {
        this.oldOwnerType = oldOwnerType;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getAdm1() {
        return adm1;
    }

    public void setAdm1(String adm1) {
        this.adm1 = adm1;
    }

    public String getEki() {
        return eki;
    }

    public void setEki(String eki) {
        this.eki = eki;
    }

    public Long getCheckDial() {
        return checkDial;
    }

    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    public Long getDialStatus() {
        return dialStatus;
    }

    public void setDialStatus(Long dialStatus) {
        this.dialStatus = dialStatus;
    }

    public String getA3a8() {
        return a3a8;
    }

    public void setA3a8(String a3a8) {
        this.a3a8 = a3a8;
    }

    public ViewStockSim(Long id, Long stockModelId, String imsi, String iccid, String pin, String puk, String pin2, String puk2, String isdn, String hlrId, String simType, String aucStatus, Date aucRegDate, Date aucRemoveDate, Long hlrStatus, Date hlrRegDate, Date hlrRemoveDate, Long ownerId, Long ownerType, Long oldOwnerId, Long oldOwnerType, Long status, String adm1, String eki, String serial, Long stateId, Long checkDial, Long dialStatus, String a3a8) {
        this.id = id;
        this.stockModelId = stockModelId;
        this.imsi = imsi;
        this.iccid = iccid;
        this.pin = pin;
        this.puk = puk;
        this.pin2 = pin2;
        this.puk2 = puk2;
        this.isdn = isdn;
        this.hlrId = hlrId;
        this.simType = simType;
        this.aucStatus = aucStatus;
        this.aucRegDate = aucRegDate;
        this.aucRemoveDate = aucRemoveDate;
        this.hlrStatus = hlrStatus;
        this.hlrRegDate = hlrRegDate;
        this.hlrRemoveDate = hlrRemoveDate;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.oldOwnerId = oldOwnerId;
        this.oldOwnerType = oldOwnerType;
        this.status = status;
        this.adm1 = adm1;
        this.eki = eki;
        this.serial = serial;
        this.stateId = stateId;
        this.checkDial = checkDial;
        this.dialStatus = dialStatus;
        this.a3a8 = a3a8;
    }

}
