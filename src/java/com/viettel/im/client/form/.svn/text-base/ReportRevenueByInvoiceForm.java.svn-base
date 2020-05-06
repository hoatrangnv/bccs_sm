/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author tamdt1, 03/08/2009
 * form bao cao doanh thu dua tren hoa don
 *
 */
public class ReportRevenueByInvoiceForm {
    private Long shopId;
    private String shopCode;
    private Long staffId;
    private String staffCode;
    private String fromDate;
    private String toDate;
    private Long telecomServiceId; //dich vu (mobile, hp, ap)
    private Long payMoney; //dang bao cao: co thu tien hay khong (1: co, 0: khong)
    private Boolean usedInvoice; //trang thai hoa don: da lap hoa don
    private Boolean destroyedInvoice; //trang thai hoa don: da huy

    private String shopName;
    private String staffName;
    
    private Boolean reportSimple;//bao cao su dung template don gian, khong phan cap

    public ReportRevenueByInvoiceForm() {
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
        telecomServiceId = 0L;
        payMoney = null;
        usedInvoice = true;
        destroyedInvoice = false;

        shopName = "";
        staffName = "";
        
        reportSimple = false;
    }

    public Boolean getDestroyedInvoice() {
        return destroyedInvoice;
    }

    public void setDestroyedInvoice(Boolean destroyedInvoice) {
        this.destroyedInvoice = destroyedInvoice;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Long payMoney) {
        this.payMoney = payMoney;
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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Boolean getUsedInvoice() {
        return usedInvoice;
    }

    public void setUsedInvoice(Boolean usedInvoice) {
        this.usedInvoice = usedInvoice;
    }
    
    public Boolean getReportSimple() {
        return reportSimple;
    }

    public void setReportSimple(Boolean reportSimple) {
        this.reportSimple = reportSimple;
    }

}
