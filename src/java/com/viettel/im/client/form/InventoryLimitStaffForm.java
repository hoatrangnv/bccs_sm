/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author thinhph2
 */
public class InventoryLimitStaffForm {
    private String shopCode;
    private String shopName;
    private String staffCode;
    private String staffName;
    private String reportType;
    private Long fromLimit;
    private Long toLimit;
    private Date fromDate;
    private Date toDate;

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

    public Long getFromLimit() {
        return fromLimit;
    }

    public void setFromLimit(Long fromLimit) {
        this.fromLimit = fromLimit;
    }

    public Long getToLimit() {
        return toLimit;
    }

    public void setToLimit(Long toLimit) {
        this.toLimit = toLimit;
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

}
