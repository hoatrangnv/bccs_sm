package com.viettel.im.database.BO;

import java.util.Date;

public class LookupIsdnFull implements java.io.Serializable {

    // Fields
    private LookupIsdnFullId id;
    private String isdnType;
    private Long status;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private Long ownerType;
    private Long ownerId;
    private String ownerCode;
    private Long rulesId;
    private String ruleName;
    private Long price;
    private String statusName;
    private String isdnTypeCode;
    private String isdnTypeName;
    private String stockIsdnCode;
    private String stockIsdnName;
    private Date lastModify;
    private Date lastUpdateTime;

    public LookupIsdnFull() {
    }

    public LookupIsdnFull(LookupIsdnFullId id, String isdnType, Long status, Long stockModelId, String stockModelName, Long ownerType, Long ownerId, String ownerCode, Long rulesId, String ruleName, Long price, Date lastModify) {
        this.id = id;
        this.isdnType = isdnType;
        this.status = status;
        this.stockModelId = stockModelId;
        this.stockModelName = stockModelName;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.ownerCode = ownerCode;
        this.rulesId = rulesId;
        this.ruleName = ruleName;
        this.price = price;
        this.lastModify = lastModify;
    }

    public String getStockIsdnCode() {
        return stockIsdnCode;
    }

    public void setStockIsdnCode(String stockIsdnCode) {
        this.stockIsdnCode = stockIsdnCode;
    }

    public String getStockIsdnName() {
        return stockIsdnName;
    }

    public void setStockIsdnName(String stockIsdnName) {
        this.stockIsdnName = stockIsdnName;
    }

    public String getIsdnTypeCode() {
        return isdnTypeCode;
    }

    public void setIsdnTypeCode(String isdnTypeCode) {
        this.isdnTypeCode = isdnTypeCode;
    }

    public String getIsdnTypeName() {
        return isdnTypeName;
    }

    public void setIsdnTypeName(String isdnTypeName) {
        this.isdnTypeName = isdnTypeName;
    }

    public LookupIsdnFullId getId() {
        return id;
    }

    public void setId(LookupIsdnFullId id) {
        this.id = id;
    }

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Long getRulesId() {
        return rulesId;
    }

    public void setRulesId(Long rulesId) {
        this.rulesId = rulesId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date lastModify) {
        this.lastModify = lastModify;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}
