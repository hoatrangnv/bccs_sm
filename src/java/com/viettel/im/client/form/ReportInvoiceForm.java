package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import java.util.Date;

/**
 *
 * @author Doan Thanh 8
 */
public class ReportInvoiceForm {
    private Long shopId;
    private String shopCode;
    private Long staffId;
    private String staffCode;
    private String fromDate;
    private String toDate;
    private Long notIncludeChild; //kieu bao cao (bao cao cua chinh cua hang, bao gom ca du lieu cua cap con)
    private Long groupType; //kieu gom nhom (tong hop, chi tiet cap duoi)

    private String shopName;
    private String staffName;

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
        notIncludeChild = 1L;
        groupType = 1L;

        shopName = "";
        staffName = "";
    }


    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Long getGroupType() {
        return groupType;
    }

    public void setGroupType(Long groupType) {
        this.groupType = groupType;
    }

    public Long getNotIncludeChild() {
        return notIncludeChild;
    }

    public void setNotIncludeChild(Long notIncludeChild) {
        this.notIncludeChild = notIncludeChild;
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

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
