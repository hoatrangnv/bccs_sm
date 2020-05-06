/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.Date;

/**
 *
 * @author vunt7
 */
public class TargetStaffMonthDetail implements java.io.Serializable {

    private Long targetStaffMonthDetailId;
    private Long targetStaffMonthId;
    private Long targetId;
    private Long amountTarget;
    private Long status;
    private Date createDate;
    private Date lastUpdateDate;
    private String createUser;
    private String updateUser;

    public TargetStaffMonthDetail() {
    }

    public Long getAmountTarget() {
        return amountTarget;
    }

    public void setAmountTarget(Long amountTarget) {
        this.amountTarget = amountTarget;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getTargetStaffMonthDetailId() {
        return targetStaffMonthDetailId;
    }

    public void setTargetStaffMonthDetailId(Long targetStaffMonthDetailId) {
        this.targetStaffMonthDetailId = targetStaffMonthDetailId;
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
}
