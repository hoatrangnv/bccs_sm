/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author NamNX
 * form bao cao doanh thu so dep
 */
public class ReportRevenueNiceNumberForm {

    private Long shopId;
    private String shopCode;
    private Long staffId;
    private Long saleServicesId;
    private String staffCode;
    private String fromDate;
    private String toDate;
    private String saleServiceCode; //dich vu bao hanh
    private String saleServiceName; //dich vu bao hanh
    private Boolean hasMoney; //dang bao cao: co thu tien
    private Boolean noMoney; //dang bao cao: khong thu tien
    private String shopName;
    private String staffName;
    private String pathExpFile;

    public ReportRevenueNiceNumberForm() {
        resetForm();
    }

    public void resetForm() {
        shopId = 0L;
        shopCode = "";
        staffId = 0L;
        staffCode = "";

        try {
            fromDate = DateTimeUtils.convertDateToString(new Date());
            toDate = fromDate;
        } catch (Exception ex) {
            fromDate = "";
            toDate = "";
        }

        hasMoney = true;
        noMoney = false;

        //
        shopName = "";
        staffName = "";
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Boolean getHasMoney() {
        return hasMoney;
    }

    public void setHasMoney(Boolean hasMoney) {
        this.hasMoney = hasMoney;
    }

    public Boolean getNoMoney() {
        return noMoney;
    }

    public void setNoMoney(Boolean noMoney) {
        this.noMoney = noMoney;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSaleServiceCode() {
        return saleServiceCode;
    }

    public void setSaleServiceCode(String saleServiceCode) {
        this.saleServiceCode = saleServiceCode;
    }

    public String getSaleServiceName() {
        return saleServiceName;
    }

    public void setSaleServiceName(String saleServiceName) {
        this.saleServiceName = saleServiceName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }
}
