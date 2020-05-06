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
public class Target implements java.io.Serializable {

    private Long targetId;
    private String targetCode;
    private String targetName;
    private Long status;
    private Date createDate;
    private Date lastUpdateDate;
    private String createUser;
    private String updateUser;

    public Target() {
    }

    public Target(Long targetId, String targetCode, String targetName, Long status, Date createDate, Date lastUpdateDate, String createUser, String updateUser) {
        this.targetId = targetId;
        this.targetCode = targetCode;
        this.targetName = targetName;
        this.status = status;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.createUser = createUser;
        this.updateUser = updateUser;
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

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
