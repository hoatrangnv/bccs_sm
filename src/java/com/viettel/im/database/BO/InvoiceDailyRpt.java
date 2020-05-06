package com.viettel.im.database.BO;

import java.util.Date;

/**
 * InvoiceDailyRpt entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InvoiceDailyRpt implements java.io.Serializable {

    // Fields

    private Long invoiceDailyRptId;
	private Long shopId;
	private Long staffId;
	private Date createdDate;
	private Long usedInvoiceQuantity;
	private Long createdInvoiceQuantity;
	private Long assignedInvoiceQuantity;
	private Long destroyedInvoiceQuantity;
	private Long deletedInvoiceQuantity;
	private Long retrievedInvoiceQuantity;
	private Long otherInvoiceQuantity;
	private Long recordCount;
    private String docNo;
    private String serialNo;
	private Long volumeNo;
	
    

    private Long remainQuantity;

    public InvoiceDailyRpt() {
    }

    public InvoiceDailyRpt(String docNo, String serialNo, Long volumeNo, Long remainQuantity) {
        this.docNo = docNo;
        this.serialNo = serialNo;
        this.volumeNo = volumeNo;
        this.remainQuantity = remainQuantity;
    }

    public InvoiceDailyRpt(Date createdDate, Long usedInvoiceQuantity, Long createdInvoiceQuantity, Long assignedInvoiceQuantity, Long destroyedInvoiceQuantity, Long deletedInvoiceQuantity, Long retrievedInvoiceQuantity, String docNo, String serialNo, Long volumeNo) {
        this.createdDate = createdDate;
        this.usedInvoiceQuantity = usedInvoiceQuantity;
        this.createdInvoiceQuantity = createdInvoiceQuantity;
        this.assignedInvoiceQuantity = assignedInvoiceQuantity;
        this.destroyedInvoiceQuantity = destroyedInvoiceQuantity;
        this.deletedInvoiceQuantity = deletedInvoiceQuantity;
        this.retrievedInvoiceQuantity = retrievedInvoiceQuantity;
        this.docNo = docNo;
        this.serialNo = serialNo;
        this.volumeNo = volumeNo;
    }

    public InvoiceDailyRpt(String docNo, String serialNo, Long volumeNo, Long usedInvoiceQuantity, Long createdInvoiceQuantity, Long assignedInvoiceQuantity, Long destroyedInvoiceQuantity, Long deletedInvoiceQuantity, Long retrievedInvoiceQuantity) {
        this.docNo = docNo;
        this.serialNo = serialNo;
        this.volumeNo = volumeNo;
        this.usedInvoiceQuantity = usedInvoiceQuantity;
        this.createdInvoiceQuantity = createdInvoiceQuantity;
        this.assignedInvoiceQuantity = assignedInvoiceQuantity;
        this.destroyedInvoiceQuantity = destroyedInvoiceQuantity;
        this.deletedInvoiceQuantity = deletedInvoiceQuantity;
        this.retrievedInvoiceQuantity = retrievedInvoiceQuantity;
    }

    public Long getAssignedInvoiceQuantity() {
        return assignedInvoiceQuantity;
    }

    public void setAssignedInvoiceQuantity(Long assignedInvoiceQuantity) {
        this.assignedInvoiceQuantity = assignedInvoiceQuantity;
    }

    public Long getInvoiceDailyRptId() {
        return invoiceDailyRptId;
    }

    public void setInvoiceDailyRptId(Long invoiceDailyRptId) {
        this.invoiceDailyRptId = invoiceDailyRptId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Long getVolumeNo() {
        return volumeNo;
    }

    public void setVolumeNo(Long volumeNo) {
        this.volumeNo = volumeNo;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedInvoiceQuantity() {
        return createdInvoiceQuantity;
    }

    public void setCreatedInvoiceQuantity(Long createdInvoiceQuantity) {
        this.createdInvoiceQuantity = createdInvoiceQuantity;
    }

    public Long getDeletedInvoiceQuantity() {
        return deletedInvoiceQuantity;
    }

    public void setDeletedInvoiceQuantity(Long deletedInvoiceQuantity) {
        this.deletedInvoiceQuantity = deletedInvoiceQuantity;
    }

    public Long getDestroyedInvoiceQuantity() {
        return destroyedInvoiceQuantity;
    }

    public void setDestroyedInvoiceQuantity(Long destroyedInvoiceQuantity) {
        this.destroyedInvoiceQuantity = destroyedInvoiceQuantity;
    }

    public Long getOtherInvoiceQuantity() {
        return otherInvoiceQuantity;
    }

    public void setOtherInvoiceQuantity(Long otherInvoiceQuantity) {
        this.otherInvoiceQuantity = otherInvoiceQuantity;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Long getRetrievedInvoiceQuantity() {
        return retrievedInvoiceQuantity;
    }

    public void setRetrievedInvoiceQuantity(Long retrievedInvoiceQuantity) {
        this.retrievedInvoiceQuantity = retrievedInvoiceQuantity;
    }

    public Long getUsedInvoiceQuantity() {
        return usedInvoiceQuantity;
    }

    public void setUsedInvoiceQuantity(Long usedInvoiceQuantity) {
        this.usedInvoiceQuantity = usedInvoiceQuantity;
    }

    public Long getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(Long remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }
}