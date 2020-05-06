/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class InvoiceListReportBean extends RetrieveBillBean {


    /** Department name of invoice list */
    private String departmentName;

    /** Parent name of invoice list */
    private String parentName;


    /** Parent id of invoice list */
    private Long parentId;


    /** Status name of invoice list */
    private String statusName;

    private String statusCode;

    private String dateString;
    private String storeString;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    
    private String invoiceListPath;


    private static final Long VIETTEL_TELECOM_ID = 1L;


    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getDateString() {
        if (null != this.dateString)
            return dateString;
        else
            return "";
    }

    public void setDateString(String dateString) {
        if (null == dateString)
            this.dateString = "";
        else
            this.dateString = dateString;
    }

    public String getStoreString() {
        if (null != this.storeString)
            return storeString;
        else
            return "";

    }

    public void setStoreString(String storeString) {
        if (null == storeString)
            this.dateString = "";
        else
            this.storeString = storeString;
    }

    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
        StringBuffer path = new StringBuffer();
        if (getStaffName() != null && !getStaffName()   .equals("")) {
            path.append(getStaffName());
            path.append(" - ");
        }
        path.append(getDepartmentName());
        if (this.parentName != null && !this.parentName.equals("")) {
            path.append(" - ");
            path.append(getParentName());
        }
        if (parentId != null && !parentId.equals(VIETTEL_TELECOM_ID)) {
            path.append(" - ");
            path.append(" VIETTEL TELECOM ");
        }
        this.invoiceListPath = path.toString();
    }

    public String getInvoiceListPath() {
        return invoiceListPath;
    }

    public void setInvoiceListPath(String invoiceListPath) {
        this.invoiceListPath = invoiceListPath;
    }
}
