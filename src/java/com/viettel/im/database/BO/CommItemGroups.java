package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CommItemGroups entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CommItemGroups extends BasicBO {

    // Fields
    private Long itemGroupId;
    private Long parentItemGroupId;
    private String groupName;
    private String reportType;
    private String status;
    private Date createDate;
    private String itemGroupIndex;
    private List lstCommItemsAuto = new ArrayList(); //tuannv1, 25/06/2009, danh sach khoan muc tu dong
    private List lstCommItemsManualPlus = new ArrayList(); //tuannv1, 25/06/2009, danh sach khoan muc nhap tay bo sung
    private List lstCommItemsManualMinus = new ArrayList(); //tuannv1, 25/06/2009, danh sach khoan muc nhap tay giam tru

    // Constructors
    /** default constructor */
    public CommItemGroups() {
    }

    /** minimal constructor */
    public CommItemGroups(Long itemGroupId, String groupName) {
        this.itemGroupId = itemGroupId;
        this.groupName = groupName;
    }

    /** full constructor */
    public CommItemGroups(Long itemGroupId, Long parentItemGroupId,
            String groupName, String reportType, String status, Date createDate) {
        this.itemGroupId = itemGroupId;
        this.parentItemGroupId = parentItemGroupId;
        this.groupName = groupName;
        this.reportType = reportType;
        this.status = status;
        this.createDate = createDate;
    }

    public String getItemGroupIndex() {
        return itemGroupIndex;
    }

    public void setItemGroupIndex(String itemGroupIndex) {
        this.itemGroupIndex = itemGroupIndex;
    }

    // Property accessors
    public Long getItemGroupId() {
        return this.itemGroupId;
    }

    public void setItemGroupId(Long itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public Long getParentItemGroupId() {
        return this.parentItemGroupId;
    }

    public void setParentItemGroupId(Long parentItemGroupId) {
        this.parentItemGroupId = parentItemGroupId;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getReportType() {
        return this.reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List getLstCommItemsAuto() {
        return lstCommItemsAuto;
    }

    public void setLstCommItemsAuto(List lstCommItemsAuto) {
        this.lstCommItemsAuto = lstCommItemsAuto;
    }

    public List getLstCommItemsManualMinus() {
        return lstCommItemsManualMinus;
    }

    public void setLstCommItemsManualMinus(List lstCommItemsManualMinus) {
        this.lstCommItemsManualMinus = lstCommItemsManualMinus;
    }

    public List getLstCommItemsManualPlus() {
        return lstCommItemsManualPlus;
    }

    public void setLstCommItemsManualPlus(List lstCommItemsManualPlus) {
        this.lstCommItemsManualPlus = lstCommItemsManualPlus;
    }
}