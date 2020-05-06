/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author User
 */
public class ReportDepositForm extends ActionForm {

    private String shopCode;
    private String shopName;
    private String shopCodeBranch;
    private String shopNameBranch;
    private String fromDate;
    private String toDate;
    private Long reportDetail;
    private Long depositTypeId;
    private String receiptStatus;
    private Long telecomServiceId;
    private String isdn;
    //loint
    private String staffName;
    private String staffCode;

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public ReportDepositForm() {
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Long getDepositTypeId() {
        return depositTypeId;
    }

    public void setDepositTypeId(Long depositTypeId) {
        this.depositTypeId = depositTypeId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(Long reportDetail) {
        this.reportDetail = reportDetail;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(String receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public String getShopCodeBranch() {
        return shopCodeBranch;
    }

    public void setShopCodeBranch(String shopCodeBranch) {
        this.shopCodeBranch = shopCodeBranch;
    }

    public String getShopNameBranch() {
        return shopNameBranch;
    }

    public void setShopNameBranch(String shopNameBranch) {
        this.shopNameBranch = shopNameBranch;
    }
}
