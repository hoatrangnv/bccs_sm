/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.sql.Timestamp;

/**
 *
 * @author Huynq13
 */
public class ExportCheckSerial {

    private Long exportCheckSerialId;
    private Long stockTransId;
    private String fromSerial;
    private String toSerial;
    private Long quantityRequire;
    private Long quantityReal;
    private String resultCheck;
    private Timestamp requestTime;
    private String createUser;
    private Long stockModelId;
    private Long ownerId;
    private Long fromOwnerId;
    private Long toOwnerId;
    private Long toOwnerTypeId;
    private Long stateId;
    private Long oldStatus;
    private Long newStatus;
    private Long channelTypeId;
    private Long duration;
    private String tableName;
    private String nodeName;
    private String clusterName;
    private Timestamp responseTime;
    private String description;
    private String resultCode;
    private String threadName;
    private String idBatch;
    private Long id;
    private Long fromOwnerTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdBatch() {
        return idBatch;
    }

    public void setIdBatch(String idBatch) {
        this.idBatch = idBatch;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExportCheckSerial() {
    }

    public Long getExportCheckSerialId() {
        return exportCheckSerialId;
    }

    public void setExportCheckSerialId(Long exportCheckSerialId) {
        this.exportCheckSerialId = exportCheckSerialId;
    }

    public Long getStockTransId() {
        return stockTransId;
    }

    public void setStockTransId(Long stockTransId) {
        this.stockTransId = stockTransId;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public Long getQuantityRequire() {
        return quantityRequire;
    }

    public void setQuantityRequire(Long quantityRequire) {
        this.quantityRequire = quantityRequire;
    }

    public Long getQuantityReal() {
        return quantityReal;
    }

    public void setQuantityReal(Long quantityReal) {
        this.quantityReal = quantityReal;
    }

    public String getResultCheck() {
        return resultCheck;
    }

    public void setResultCheck(String resultCheck) {
        this.resultCheck = resultCheck;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Timestamp getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Timestamp responseTime) {
        this.responseTime = responseTime;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public Long getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Long oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Long getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Long newStatus) {
        this.newStatus = newStatus;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getFromOwnerId() {
        return fromOwnerId;
    }

    public void setFromOwnerId(Long fromOwnerId) {
        this.fromOwnerId = fromOwnerId;
    }

    public Long getToOwnerId() {
        return toOwnerId;
    }

    public void setToOwnerId(Long toOwnerId) {
        this.toOwnerId = toOwnerId;
    }

    public Long getToOwnerTypeId() {
        return toOwnerTypeId;
    }

    public void setToOwnerTypeId(Long toOwnerTypeId) {
        this.toOwnerTypeId = toOwnerTypeId;
    }

    public Long getFromOwnerTypeId() {
        return fromOwnerTypeId;
    }

    public void setFromOwnerTypeId(Long fromOwnerTypeId) {
        this.fromOwnerTypeId = fromOwnerTypeId;
    }
}
