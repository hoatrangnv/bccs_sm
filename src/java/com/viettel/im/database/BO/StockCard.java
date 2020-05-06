package com.viettel.im.database.BO;

import java.util.Date;

/**
 * StockCard entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StockCard implements java.io.Serializable {

    // Fields
    private Long id;
    private Long stockModelId;
    private String serial;
    private String cardType;
    private String amountType;
    private Long ownerId;
    private Long ownerType;
    private Date createDate;
    private Date expiredDate;
    private Long status;
    private Long telecomServiceId;
    private Long stateId;
    private Long checkDial;
    private Long dialStatus;
    private String userSessionId;
    private String createUser;
    private Long channelTypeId;
    
    private Long ownerReceiverId;
    private Long ownerReceiverType;
    private String receiverName;

    private Long connectionStatus;
    private Long connectionType;
    private Date connectionDate;    
    private java.lang.Double depositPrice;
    private Long activeStatus;
    private Date activeDate;

    // Constructors
    /** default constructor */
    public StockCard() {
    }

    /** minimal constructor */
    public StockCard(Long id) {
        this.id = id;
    }

    /** full constructor */
    public StockCard(Long id, Long stockModelId, String serial,
            String cardType, String amountType, Long ownerId, Long ownerType,
            Date createDate, Date expiredDate, Long status,
            Long telecomServiceId, Long stateId, Long checkDial,
            Long dialStatus, String userSessionId, String createUser,
            Long channelTypeId) {
        this.id = id;
        this.stockModelId = stockModelId;
        this.serial = serial;
        this.cardType = cardType;
        this.amountType = amountType;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.createDate = createDate;
        this.expiredDate = expiredDate;
        this.status = status;
        this.telecomServiceId = telecomServiceId;
        this.stateId = stateId;
        this.checkDial = checkDial;
        this.dialStatus = dialStatus;
        this.userSessionId = userSessionId;
        this.createUser = createUser;
        this.channelTypeId = channelTypeId;
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

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAmountType() {
        return this.amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
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

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return this.expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTelecomServiceId() {
        return this.telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public Long getStateId() {
        return this.stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getCheckDial() {
        return this.checkDial;
    }

    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    public Long getDialStatus() {
        return this.dialStatus;
    }

    public void setDialStatus(Long dialStatus) {
        this.dialStatus = dialStatus;
    }

    public String getUserSessionId() {
        return this.userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getChannelTypeId() {
        return this.channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Long getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Long activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(Double depositPrice) {
        this.depositPrice = depositPrice;
    }
}
