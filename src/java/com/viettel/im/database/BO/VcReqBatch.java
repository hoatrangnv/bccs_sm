/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.Date;

/**
 *
 * @author Cty-129
 */
public class VcReqBatch implements java.io.Serializable {

    // Fields
    private Long batchId;
    private String batchNo;
    private String staNo;
    private String endNo;
    private String currentNo;
    private Long status;
    private Long processCount;
    private Long requestId;
    private String description;
    private String errCode;
    private String failedNumber;
    private String failedFile;
    private Long fileStatus;
    private Date lastRetryTime;

    // Constructors
    /** default constructor */
    public VcReqBatch() {
    }

    /** minimal constructor */
    public VcReqBatch(Long batchId, String batchNo, String staNo, String endNo) {
        this.batchId = batchId;
        this.batchNo = batchNo;
        this.staNo = staNo;
        this.endNo = endNo;
    }

    /** full constructor */
    public VcReqBatch(Long batchId, String batchNo, String staNo, String endNo,
            String currentNo, Long status, Long processCount, Long requestId,
            String description, String errCode, String failedNumber,
            String failedFile, Long fileStatus, Date lastRetryTime) {
        this.batchId = batchId;
        this.batchNo = batchNo;
        this.staNo = staNo;
        this.endNo = endNo;
        this.currentNo = currentNo;
        this.status = status;
        this.processCount = processCount;
        this.requestId = requestId;
        this.description = description;
        this.errCode = errCode;
        this.failedNumber = failedNumber;
        this.failedFile = failedFile;
        this.fileStatus = fileStatus;
        this.lastRetryTime = lastRetryTime;
    }

    // Property accessors
    public Long getBatchId() {
        return this.batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStaNo() {
        return this.staNo;
    }

    public void setStaNo(String staNo) {
        this.staNo = staNo;
    }

    public String getEndNo() {
        return this.endNo;
    }

    public void setEndNo(String endNo) {
        this.endNo = endNo;
    }

    public String getCurrentNo() {
        return this.currentNo;
    }

    public void setCurrentNo(String currentNo) {
        this.currentNo = currentNo;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getProcessCount() {
        return this.processCount;
    }

    public void setProcessCount(Long processCount) {
        this.processCount = processCount;
    }

    public Long getRequestId() {
        return this.requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getFailedNumber() {
        return this.failedNumber;
    }

    public void setFailedNumber(String failedNumber) {
        this.failedNumber = failedNumber;
    }

    public String getFailedFile() {
        return this.failedFile;
    }

    public void setFailedFile(String failedFile) {
        this.failedFile = failedFile;
    }

    public Long getFileStatus() {
        return this.fileStatus;
    }

    public void setFileStatus(Long fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Date getLastRetryTime() {
        return this.lastRetryTime;
    }

    public void setLastRetryTime(Date lastRetryTime) {
        this.lastRetryTime = lastRetryTime;
    }
}
