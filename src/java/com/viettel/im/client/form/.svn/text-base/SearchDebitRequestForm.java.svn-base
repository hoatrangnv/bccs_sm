/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ThinhPH2
 */
public class SearchDebitRequestForm {
    private String userCreate;
    private String requestCode;
    private String status;
    private Date fromDate;
    private Date toDate;
    private Long debitRequestId;
    private Long[] listRequestDetailId;
    private Long[] listRequestId;
    private String[] listApprovalReject;
    private String[] listDescription;
    private String[] statusList;
    private String staffCode;
    private String requestObjectType;

    public String getRequestObjectType() {
        return requestObjectType;
    }

    public void setRequestObjectType(String requestObjectType) {
        this.requestObjectType = requestObjectType;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String[] getStatusList() {
        return statusList;
    }

    public void setStatusList(String[] statusList) {
        this.statusList = statusList;
    }

    public Long[] getListRequestId() {
        return listRequestId;
    }

    public void setListRequestId(Long[] listRequestId) {
        this.listRequestId = listRequestId;
    }

    public String[] getListDescription() {
        return listDescription;
    }

    public void setListDescription(String[] listDescription) {
        this.listDescription = listDescription;
    }

    public String[] getListApprovalReject() {
        return listApprovalReject;
    }

    public void setListApprovalReject(String[] listApprovalReject) {
        this.listApprovalReject = listApprovalReject;
    }

    public Long[] getListRequestDetailId() {
        return listRequestDetailId;
    }

    public void setListRequestDetailId(Long[] listRequestDetailId) {
        this.listRequestDetailId = listRequestDetailId;
    }
    
    public Long getDebitRequestId() {
        return debitRequestId;
    }

    public void setDebitRequestId(Long debitRequestId) {
        this.debitRequestId = debitRequestId;
    }
        
    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }   

    public String getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(String userCreate) {
        this.userCreate = userCreate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void resetForm(){
        Calendar calendar = Calendar.getInstance();
        this.toDate = calendar.getTime();
        this.requestCode = "";
        this.status = "";
        this.userCreate = "";
    }
}
