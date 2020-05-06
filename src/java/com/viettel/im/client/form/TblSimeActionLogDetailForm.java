/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;

/**
 * Author: TheTM
 * Date created: Sep 10, 2010
 * Purpose:
 */
public class TblSimeActionLogDetailForm {

    private Long actionLogDetailId;
    private Long actionLogId;
    private Date actionTime;
    private String columnName;
    private String oldValue;
    private String newValue;

    public Long getActionLogDetailId() {
        return actionLogDetailId;
    }

    public void setActionLogDetailId(Long actionLogDetailId) {
        this.actionLogDetailId = actionLogDetailId;
    }

    public Long getActionLogId() {
        return actionLogId;
    }

    public void setActionLogId(Long actionLogId) {
        this.actionLogId = actionLogId;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
}
