/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;

/**
 *
 * @author thuannx1
 */
public class TargetForm {

    private String staffCode;
    private String staffName;
    private String shopCode;
    private String shopName;
    private Long targetStaffMonthId;
    private Long staffId;
    private String monthTarget;
    private Long status;
    private Date createDate;
    private Date lastUpdateDate;
    private String createUser;
    private String updateUser;
    private String monthSelect;
    private String yearSelect;

    public TargetForm() {
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getMonthSelect() {
        return monthSelect;
    }

    public void setMonthSelect(String monthSelect) {
        this.monthSelect = monthSelect;
    }

    public String getMonthTarget() {
        return monthTarget;
    }

    public void setMonthTarget(String monthTarget) {
        this.monthTarget = monthTarget;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTargetStaffMonthId() {
        return targetStaffMonthId;
    }

    public void setTargetStaffMonthId(Long targetStaffMonthId) {
        this.targetStaffMonthId = targetStaffMonthId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getYearSelect() {
        return yearSelect;
    }

    public void setYearSelect(String yearSelect) {
        this.yearSelect = yearSelect;
    }
}
