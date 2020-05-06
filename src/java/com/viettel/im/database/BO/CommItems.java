package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CommItems entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CommItems extends BasicBO {

    private Long itemId;
    private Long itemGroupId;
    private Long counterId;
    private String itemName;
    private String unit;
    private String checkedDoc;
    private String inputType;
    private String reportType;
    private Long itemOrder;
    private Date startDate;
    private Date endDate;
    private String status;
    private String description;
    private List lstCommTransactionAuto = new ArrayList();//tuannv1, 25/06/2009, danh sach khoan muc tu dong
    private List lstCommTransactionPlus = new ArrayList();//tuannv1, 25/06/2009, danh sach khoan muc tu dong
    private List lstCommTransactionMinus = new ArrayList();//tuannv1, 25/06/2009, danh sach khoan muc tu dong
    // Constructors

    /** default constructor */
    public CommItems() {
    }

    /** minimal constructor */
    public CommItems(Long itemId, Long itemGroupId, String inputType) {
        this.itemId = itemId;
        this.itemGroupId = itemGroupId;
        this.inputType = inputType;
    }

    /** full constructor */
    public CommItems(Long itemId, Long itemGroupId, Long counterId,
            String itemName, String unit, String checkedDoc, String inputType,
            String reportType, Long itemOrder, Date startDate, Date endDate,
            String status) {
        this.itemId = itemId;
        this.itemGroupId = itemGroupId;
        this.counterId = counterId;
        this.itemName = itemName;
        this.unit = unit;
        this.checkedDoc = checkedDoc;
        this.inputType = inputType;
        this.reportType = reportType;
        this.itemOrder = itemOrder;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public CommItems(Long itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    // Property accessors
    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getItemGroupId() {
        return this.itemGroupId;
    }

    public void setItemGroupId(Long itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public Long getCounterId() {
        return this.counterId;
    }

    public void setCounterId(Long counterId) {
        this.counterId = counterId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCheckedDoc() {
        return this.checkedDoc;
    }

    public void setCheckedDoc(String checkedDoc) {
        this.checkedDoc = checkedDoc;
    }

    public String getInputType() {
        return this.inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getReportType() {
        return this.reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Long getItemOrder() {
        return this.itemOrder;
    }

    public void setItemOrder(Long itemOrder) {
        this.itemOrder = itemOrder;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List getLstCommTransactionAuto() {
        return lstCommTransactionAuto;
    }

    public void setLstCommTransactionAuto(List lstCommTransactionAuto) {
        this.lstCommTransactionAuto = lstCommTransactionAuto;
    }

    public List getLstCommTransactionMinus() {
        return lstCommTransactionMinus;
    }

    public void setLstCommTransactionMinus(List lstCommTransactionMinus) {
        this.lstCommTransactionMinus = lstCommTransactionMinus;
    }

    public List getLstCommTransactionPlus() {
        return lstCommTransactionPlus;
    }

    public void setLstCommTransactionPlus(List lstCommTransactionPlus) {
        this.lstCommTransactionPlus = lstCommTransactionPlus;
    }


}