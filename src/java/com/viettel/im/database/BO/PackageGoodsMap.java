package com.viettel.im.database.BO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PackageGoodsMap entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class PackageGoodsMap implements java.io.Serializable {

    // Fields
    private Long packageGoodsMapId;
    private Long stockTypeId;
    private String stockTypeCode;
    private String stockTypeName;
    private String note;
    private Date createDate;
    private Long status;
    private Date modifyDate;
    private String userCreate;
    private String userModify;
    private Long packageGoodsId;
    private String groupCode;
    private String groupName;
    private List<PackageGoodsDetail> listPackageGoodsDetail = new ArrayList<PackageGoodsDetail>();

    // Constructors
    /** default constructor */
    public PackageGoodsMap() {
    }

    /** minimal constructor */
    public PackageGoodsMap(Long packageGoodsMapId) {
        this.packageGoodsMapId = packageGoodsMapId;
    }

    // Property accessors
    public Long getPackageGoodsMapId() {
        return this.packageGoodsMapId;
    }

    public void setPackageGoodsMapId(Long packageGoodsMapId) {
        this.packageGoodsMapId = packageGoodsMapId;
    }

    public Long getStockTypeId() {
        return this.stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getStockTypeCode() {
        return this.stockTypeCode;
    }

    public void setStockTypeCode(String stockTypeCode) {
        this.stockTypeCode = stockTypeCode;
    }

    public String getStockTypeName() {
        return this.stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public Long getPackageGoodsId() {
        return this.packageGoodsId;
    }

    public void setPackageGoodsId(Long packageGoodsId) {
        this.packageGoodsId = packageGoodsId;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<PackageGoodsDetail> getListPackageGoodsDetail() {
        return listPackageGoodsDetail;
    }

    public void setListPackageGoodsDetail(List<PackageGoodsDetail> listPackageGoodsDetail) {
        this.listPackageGoodsDetail = listPackageGoodsDetail;
    }
}
