package com.viettel.im.database.BO;

import java.util.Date;
import java.util.List;

/**
 * PackageGoods entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PackageGoodsV2 implements java.io.Serializable {

    // Fields
    private Long packageGoodsId;
    private String code;
    private String name;
    private String note;
    private Long status;
    private String createdBy;
    private Date createdDate;
    private Date lastUpdatedDate;
    private String lastUpdatedBy;
    private Long stt;
    private List lstPackageGoodsDetailV2;

    public List getLstPackageGoodsDetailV2() {
        return lstPackageGoodsDetailV2;
    }

    public void setLstPackageGoodsDetailV2(List lstPackageGoodsDetailV2) {
        this.lstPackageGoodsDetailV2 = lstPackageGoodsDetailV2;
    }

    public Long getStt() {
        return stt;
    }

    public void setStt(Long stt) {
        this.stt = stt;
    }
   
    public PackageGoodsV2() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPackageGoodsId() {
        return packageGoodsId;
    }

    public void setPackageGoodsId(Long packageGoodsId) {
        this.packageGoodsId = packageGoodsId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    
}