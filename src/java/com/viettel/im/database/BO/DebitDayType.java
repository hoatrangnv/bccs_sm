/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.BO;

import java.io.Serializable;
import java.util.Date;

/**
 * @since 26/04/2013
 * @author linhnt28
 */
public class DebitDayType implements Serializable {
    private Long Id;
    private String debitDayType;
    private Date createDate;
    private Date endDate;
    private Date staDate;
    private Long status;
    private String createUser;
    private String lastUpdateUser;
    private Date lastUpdateDate;
    private String debitDayTypeName;
    private String fileName;
    private byte[] fileContent;
    private String ddtName;

    public String getDdtName() {
        return ddtName;
    }

    public void setDdtName(String ddtName) {
        this.ddtName = ddtName;
    }
    
    public String getDebitDayTypeName() {
        return debitDayTypeName;
    }

    public void setDebitDayTypeName(String debitDayTypeName) {
        this.debitDayTypeName = debitDayTypeName;
    }

    public DebitDayType() {
    }

    public DebitDayType(Long Id, String debitDayType, Date createDate, Date endDate, Long status) {
        this.Id = Id;
        this.debitDayType = debitDayType;
        this.createDate = createDate;
        this.endDate = endDate;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDebitDayType() {
        return debitDayType;
    }

    public void setDebitDayType(String debitDayType) {
        this.debitDayType = debitDayType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getStaDate() {
        return staDate;
    }

    public void setStaDate(Date staDate) {
        this.staDate = staDate;
    }
}
