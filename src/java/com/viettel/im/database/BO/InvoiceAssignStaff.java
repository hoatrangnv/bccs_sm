package com.viettel.im.database.BO;

import java.util.Date;


/**
 * InvoiceAssignStaff entity. @author MyEclipse Persistence Tools
 */

public class InvoiceAssignStaff  implements java.io.Serializable {


    // Fields    

     private Long invoiceAssignStaffId;
     private Long invoiceListId;
     private Long staffId;
     private Long fromInvoice;
     private Long toInvoice;
     private Long currInvoiceNo;
     private String remainInvoiceNum;
     private String userCreated;
     private Date dateCreated;
     private Long status;


    // Constructors

    /** default constructor */
    public InvoiceAssignStaff() {
    }

	/** minimal constructor */
    public InvoiceAssignStaff(Long invoiceAssignStaffId, Long staffId, Long fromInvoice, Long toInvoice) {
        this.invoiceAssignStaffId = invoiceAssignStaffId;
        this.staffId = staffId;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
    }
    
    /** full constructor */
    public InvoiceAssignStaff(Long invoiceAssignStaffId, Long invoiceListId, Long staffId, Long fromInvoice, Long toInvoice, Long currInvoiceNo, String remainInvoiceNum, String userCreated, Date dateCreated, Long status) {
        this.invoiceAssignStaffId = invoiceAssignStaffId;
        this.invoiceListId = invoiceListId;
        this.staffId = staffId;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.currInvoiceNo = currInvoiceNo;
        this.remainInvoiceNum = remainInvoiceNum;
        this.userCreated = userCreated;
        this.dateCreated = dateCreated;
        this.status = status;
    }

   
    // Property accessors

    public Long getInvoiceAssignStaffId() {
        return this.invoiceAssignStaffId;
    }
    
    public void setInvoiceAssignStaffId(Long invoiceAssignStaffId) {
        this.invoiceAssignStaffId = invoiceAssignStaffId;
    }

    public Long getInvoiceListId() {
        return this.invoiceListId;
    }
    
    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public Long getStaffId() {
        return this.staffId;
    }
    
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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

    public Long getCurrInvoiceNo() {
        return this.currInvoiceNo;
    }
    
    public void setCurrInvoiceNo(Long currInvoiceNo) {
        this.currInvoiceNo = currInvoiceNo;
    }

    public String getRemainInvoiceNum() {
        return this.remainInvoiceNum;
    }
    
    public void setRemainInvoiceNum(String remainInvoiceNum) {
        this.remainInvoiceNum = remainInvoiceNum;
    }

    public String getUserCreated() {
        return this.userCreated;
    }
    
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
   








}