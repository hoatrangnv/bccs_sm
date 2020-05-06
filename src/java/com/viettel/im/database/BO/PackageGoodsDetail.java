package com.viettel.im.database.BO;

import java.util.Date;

/**
 * PackageGoodsDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PackageGoodsDetail implements java.io.Serializable {

    // Fields
    private Long packageGoodsDetailId;
    private Long packageGoodsMapId;
    private Long stockModelId;
    private Long status;
    private Long requiredCheck;
    private Date createDate;
    private Date modifyDate;
    private String userCreate;
    private String userModify;
    private String stockModelCode;
    private String stockModelName;
    private String stockTypeName;
    private String decriptions;

    // Constructors
    /** default constructor */
    public PackageGoodsDetail() {
    }

    // Property accessors
    public Long getPackageGoodsDetailId() {
        return this.packageGoodsDetailId;
    }

    public void setPackageGoodsDetailId(Long packageGoodsDetailId) {
        this.packageGoodsDetailId = packageGoodsDetailId;
    }

    public Long getPackageGoodsMapId() {
        return packageGoodsMapId;
    }

    public void setPackageGoodsMapId(Long packageGoodsMapId) {
        this.packageGoodsMapId = packageGoodsMapId;
    }

    public Long getStockModelId() {
        return this.stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getUserCreate() {
        return this.userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public String getUserModify() {
        return this.userModify;
    }

    public void setUserModify(String userModify) {
        this.userModify = userModify;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
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

    public String getDecriptions() {
        return decriptions;
    }

    public void setDecriptions(String decriptions) {
        this.decriptions = decriptions;
    }

    public Long getRequiredCheck() {
        return requiredCheck;
    }

    public void setRequiredCheck(Long requiredCheck) {
        this.requiredCheck = requiredCheck;
    }
}
