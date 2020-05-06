/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author ANHLT
 */
public class StockKitForm extends ActionForm {

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
    private String startISDN;
    private String endISDN;
    private String shopCode;
    private String shopName;
    private List strDrawID=new ArrayList();
    private String ReceiptMessage;

    public StockKitForm() {
    }

    public StockKitForm(Long id, Long stockModelID, String imsi, String iccid, String puk, String pin,
            String puk2, String pin2, String hlrID, String simType, String aucStatus, Date aucRegDate, Date aucReDate, Long hlrStatus,
            Date hlrRegDate, Date hlrRemoveDate, Long ownerID, Long ownerType, Long oldOwnerID, Long oldownerType, Long status, Long ckDial, Long dialStatus, String serial, String sISDN, String eISDN, String shopcode, String shopname,List lstID,String mess) {
        this.id = id;
        this.stockModelId = stockModelID;
        this.imsi = imsi;
        this.iccid = iccid;
        this.puk = puk;
        this.pin = pin;
        this.puk2 = puk2;
        this.pin2 = pin2;
        this.hlrId = hlrID;
        this.simType = simType;
        this.aucStatus = aucStatus;
        this.aucRegDate = aucRegDate;
        this.aucRemoveDate = aucReDate;
        this.hlrStatus = hlrStatus;
        this.hlrRegDate = hlrRegDate;
        this.hlrRemoveDate = hlrRemoveDate;
        this.ownerId = ownerID;
        this.ownerType = ownerType;
        this.oldOwnerId = oldOwnerID;
        this.oldOwnerType = oldownerType;
        this.status = status;
        this.checkDial = ckDial;
        this.dialStatus = dialStatus;
        this.serial = serial;
        this.startISDN = sISDN;
        this.endISDN = eISDN;
        this.shopCode = shopcode;
        this.shopName = shopname;
        this.strDrawID = lstID;
        this.ReceiptMessage = mess;
    }

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
     * @return the startISDN
     */
    public String getStartISDN() {
        return startISDN;
    }

    /**
     * @param startISDN the startISDN to set
     */
    public void setStartISDN(String startISDN) {
        this.startISDN = startISDN;
    }

    /**
     * @return the endISDN
     */
    public String getEndISDN() {
        return endISDN;
    }

    /**
     * @param endISDN the endISDN to set
     */
    public void setEndISDN(String endISDN) {
        this.endISDN = endISDN;
    }

    /**
     * @return the shopCode
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * @param shopCode the shopCode to set
     */
    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    /**
     * @return the shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @param shopName the shopName to set
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * @return the strDrawID
     */
    public List getStrDrawID() {
        return strDrawID;
    }

    /**
     * @param strDrawID the strDrawID to set
     */
    public void setStrDrawID(List strDrawID) {
        this.strDrawID = strDrawID;
    }

    /**
     * @return the ReceiptMessage
     */
    public String getReceiptMessage() {
        return ReceiptMessage;
    }

    /**
     * @param ReceiptMessage the ReceiptMessage to set
     */
    public void setReceiptMessage(String ReceiptMessage) {
        this.ReceiptMessage = ReceiptMessage;
    }

}
