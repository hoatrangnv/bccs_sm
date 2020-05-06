package com.viettel.im.database.BO;

import java.util.Date;

/**
 * InvoiceDailyRptV2 entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class InvoiceDailyRptV2 implements java.io.Serializable {

    // Fields
    private Long invoiceDailyRptId;
    private Long shopId;
    private Long staffId;
    private Date invoiceDate;
    private Long invoiceListId;
    private String docNo;
    private String serialNo;
    private Long volumeNo;
    private Long fromInvoice;
    private Long toInvoice;
    private Long actionType;
    private Date createdDate;
    private Date lastUpdatedTime;
    private String lastUpdatedUser;
    private Long status;

    // Constructors
    /** default constructor */
    public InvoiceDailyRptV2() {
    }

    /** minimal constructor */
    public InvoiceDailyRptV2(Long invoiceDailyRptId) {
        this.invoiceDailyRptId = invoiceDailyRptId;
    }

    public InvoiceDailyRptV2(Long shopId, Long staffId, Long invoiceListId, String docNo, String serialNo, Long volumeNo, Long fromInvoice, Long toInvoice) {
        this.shopId = shopId;
        this.staffId = staffId;
        this.invoiceListId = invoiceListId;
        this.docNo = docNo;
        this.serialNo = serialNo;
        this.volumeNo = volumeNo;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
    }

    /** full constructor */
    public InvoiceDailyRptV2(Long invoiceDailyRptId, Long shopId, Long staffId,
            Date invoiceDate, Long invoiceListId, String docNo,
            String serialNo, Long volumeNo, Long fromInvoice, Long toInvoice,
            Long actionType, Date createdDate, Date lastUpdatedTime,
            String lastUpdatedUser, Long status) {
        this.invoiceDailyRptId = invoiceDailyRptId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.invoiceDate = invoiceDate;
        this.invoiceListId = invoiceListId;
        this.docNo = docNo;
        this.serialNo = serialNo;
        this.volumeNo = volumeNo;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.actionType = actionType;
        this.createdDate = createdDate;
        this.lastUpdatedTime = lastUpdatedTime;
        this.lastUpdatedUser = lastUpdatedUser;
        this.status = status;
    }

    // Property accessors
    public Long getInvoiceDailyRptId() {
        return this.invoiceDailyRptId;
    }

    public void setInvoiceDailyRptId(Long invoiceDailyRptId) {
        this.invoiceDailyRptId = invoiceDailyRptId;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Date getInvoiceDate() {
        return this.invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getInvoiceListId() {
        return this.invoiceListId;
    }

    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public String getDocNo() {
        return this.docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getVolumeNo() {
        return this.volumeNo;
    }

    public void setVolumeNo(Long volumeNo) {
        this.volumeNo = volumeNo;
    }

    public Long getFromInvoice() {
        return this.fromInvoice;
    }

    public void setFromInvoice(Long fromInvoice) {
        this.fromInvoice = fromInvoice;
    }

    public Long getToInvoice() {
        return this.toInvoice;
    }

    public void setToInvoice(Long toInvoice) {
        this.toInvoice = toInvoice;
    }

    public Long getActionType() {
        return this.actionType;
    }

    public void setActionType(Long actionType) {
        this.actionType = actionType;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedTime() {
        return this.lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedUser() {
        return this.lastUpdatedUser;
    }

    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}
