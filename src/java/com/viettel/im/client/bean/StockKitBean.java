/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.util.Date;

/**
 *
 * @author ANHLT
 */
public class StockKitBean implements java.io.Serializable {

    private Long id;
    private Long stockModelId;
    private String imsi;
    private String isdn;
    private String iccid;
    private String puk;
    private String pin;
    private String puk2;
    private String pin2;
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
    private Long checkDial;
    private Long dialStatus;
    private String serial;
    private Long stateId;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the stockModelId
     */
    public Long getStockModelId() {
        return stockModelId;
    }

    /**
     * @param stockModelId the stockModelId to set
     */
    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    /**
     * @return the imsi
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * @param imsi the imsi to set
     */
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    /**
     * @return the isdn
     */
    public String getIsdn() {
        return isdn;
    }

    /**
     * @param isdn the isdn to set
     */
    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    /**
     * @return the iccid
     */
    public String getIccid() {
        return iccid;
    }

    /**
     * @param iccid the iccid to set
     */
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    /**
     * @return the puk
     */
    public String getPuk() {
        return puk;
    }

    /**
     * @param puk the puk to set
     */
    public void setPuk(String puk) {
        this.puk = puk;
    }

    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

    /**
     * @return the puk2
     */
    public String getPuk2() {
        return puk2;
    }

    /**
     * @param puk2 the puk2 to set
     */
    public void setPuk2(String puk2) {
        this.puk2 = puk2;
    }

    /**
     * @return the pin2
     */
    public String getPin2() {
        return pin2;
    }

    /**
     * @param pin2 the pin2 to set
     */
    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    /**
     * @return the hlrId
     */
    public String getHlrId() {
        return hlrId;
    }

    /**
     * @param hlrId the hlrId to set
     */
    public void setHlrId(String hlrId) {
        this.hlrId = hlrId;
    }

    /**
     * @return the simType
     */
    public String getSimType() {
        return simType;
    }

    /**
     * @param simType the simType to set
     */
    public void setSimType(String simType) {
        this.simType = simType;
    }

    /**
     * @return the aucStatus
     */
    public String getAucStatus() {
        return aucStatus;
    }

    /**
     * @param aucStatus the aucStatus to set
     */
    public void setAucStatus(String aucStatus) {
        this.aucStatus = aucStatus;
    }

    /**
     * @return the aucRegDate
     */
    public Date getAucRegDate() {
        return aucRegDate;
    }

    /**
     * @param aucRegDate the aucRegDate to set
     */
    public void setAucRegDate(Date aucRegDate) {
        this.aucRegDate = aucRegDate;
    }

    /**
     * @return the aucRemoveDate
     */
    public Date getAucRemoveDate() {
        return aucRemoveDate;
    }

    /**
     * @param aucRemoveDate the aucRemoveDate to set
     */
    public void setAucRemoveDate(Date aucRemoveDate) {
        this.aucRemoveDate = aucRemoveDate;
    }

    /**
     * @return the hlrStatus
     */
    public Long getHlrStatus() {
        return hlrStatus;
    }

    /**
     * @param hlrStatus the hlrStatus to set
     */
    public void setHlrStatus(Long hlrStatus) {
        this.hlrStatus = hlrStatus;
    }

    /**
     * @return the hlrRegDate
     */
    public Date getHlrRegDate() {
        return hlrRegDate;
    }

    /**
     * @param hlrRegDate the hlrRegDate to set
     */
    public void setHlrRegDate(Date hlrRegDate) {
        this.hlrRegDate = hlrRegDate;
    }

    /**
     * @return the hlrRemoveDate
     */
    public Date getHlrRemoveDate() {
        return hlrRemoveDate;
    }

    /**
     * @param hlrRemoveDate the hlrRemoveDate to set
     */
    public void setHlrRemoveDate(Date hlrRemoveDate) {
        this.hlrRemoveDate = hlrRemoveDate;
    }

    /**
     * @return the ownerId
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * @return the ownerType
     */
    public Long getOwnerType() {
        return ownerType;
    }

    /**
     * @param ownerType the ownerType to set
     */
    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    /**
     * @return the oldOwnerId
     */
    public Long getOldOwnerId() {
        return oldOwnerId;
    }

    /**
     * @param oldOwnerId the oldOwnerId to set
     */
    public void setOldOwnerId(Long oldOwnerId) {
        this.oldOwnerId = oldOwnerId;
    }

    /**
     * @return the oldOwnerType
     */
    public Long getOldOwnerType() {
        return oldOwnerType;
    }

    /**
     * @param oldOwnerType the oldOwnerType to set
     */
    public void setOldOwnerType(Long oldOwnerType) {
        this.oldOwnerType = oldOwnerType;
    }

    /**
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * @return the checkDial
     */
    public Long getCheckDial() {
        return checkDial;
    }

    /**
     * @param checkDial the checkDial to set
     */
    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    /**
     * @return the dialStatus
     */
    public Long getDialStatus() {
        return dialStatus;
    }

    /**
     * @param dialStatus the dialStatus to set
     */
    public void setDialStatus(Long dialStatus) {
        this.dialStatus = dialStatus;
    }

    /**
     * @return the serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @param serial the serial to set
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * @return the stateId
     */
    public Long getStateId() {
        return stateId;
    }

    /**
     * @param stateId the stateId to set
     */
    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
}
