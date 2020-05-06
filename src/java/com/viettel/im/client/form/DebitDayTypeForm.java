/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;

import com.viettel.im.database.BO.DebitDayType;
import java.util.Date;

/**
 * @since 26/04/13
 * @author linhnt28
 */
public class DebitDayTypeForm {
    
    private Long debitDayTypeId;
    private String debitDayType;
    private Date staDate;
    private Date endDate;
    private Long status;
    private String message;
    private String fileName;
    private byte[] fileContent;
    private String serverFileName;
    private String ddtName;

    public String getDdtName() {
        return ddtName;
    }

    public void setDdtName(String ddtName) {
        this.ddtName = ddtName;
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

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public void resetForm() {
        this.debitDayTypeId = null;
        this.debitDayType = "";
        this.staDate = null;
        this.endDate = null;
        this.status = null;
        fileName = null;
        serverFileName = null;
        this.ddtName = "";
    }

    public DebitDayTypeForm() {
    }

    public void copyDataToForm(DebitDayType bo) {
        this.debitDayTypeId = bo.getId();
        this.debitDayType = bo.getDebitDayType();
        this.staDate = bo.getStaDate();
        this.endDate = bo.getEndDate();
        this.status = bo.getStatus();
        this.fileName = bo.getFileName();
        this.ddtName = bo.getDdtName().trim();
    }

    public void copyDataToBO(DebitDayType bo) {
        bo.setId(this.debitDayTypeId);
        bo.setDebitDayType(this.debitDayType);
        bo.setStaDate(this.staDate);
        bo.setEndDate(this.endDate);
        bo.setStatus(this.status);
        if(fileName!=null){
            bo.setFileName(fileName);
            bo.setFileContent(fileContent);
        }
        bo.setDdtName(this.ddtName.trim());
    }
    
    public String getDebitDayType() {
        return debitDayType;
    }

    public void setDebitDayType(String debitDayType) {
        this.debitDayType = debitDayType;
    }

    public Long getDebitDayTypeId() {
        return debitDayTypeId;
    }

    public void setDebitDayTypeId(Long debitDayTypeId) {
        this.debitDayTypeId = debitDayTypeId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStaDate() {
        return staDate;
    }

    public void setStaDate(Date staDate) {
        this.staDate = staDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    }
