package com.viettel.im.database.BO;

import java.util.Date;

/**
 * AbstractTblSimeActionLog entity provides the base persistence definition of
 * the TblSimeActionLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TblSheActionLog implements java.io.Serializable {

    // Fields
    private Long actionLogId;
    private String isdn;
    private String actionType;
    private String sessionUser;
    private String hostName;
    private String ipAddress;
    private String terminal;
    private String actionUser;
    private String actionIpAddress;
    private Date actionTime;
    private String columnName;
    private String oldValue;
    private String newValue;
    private String ownerCodeOld;
    private String ownerNameOld;
    private String ownerCodeNew;
    private String ownerNameNew;

    // Constructors
    /** default constructor */
    public TblSheActionLog() {
    }

    /** minimal constructor */
    public TblSheActionLog(Long actionLogId) {
        this.actionLogId = actionLogId;
    }

    /** full constructor */
    public TblSheActionLog(Long actionLogId, String isdn,
            String actionType, String sessionUser, String hostName,
            String ipAddress, String terminal, String actionUser,
            String actionIpAddress, Date actionTime) {
        this.actionLogId = actionLogId;
        this.isdn = isdn;
        this.actionType = actionType;
        this.sessionUser = sessionUser;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.terminal = terminal;
        this.actionUser = actionUser;
        this.actionIpAddress = actionIpAddress;
        this.actionTime = actionTime;
    }

    public TblSheActionLog(String actionUser, String actionIpAddress, Date actionTime, String oldValue, String newValue,
            String ownerCodeOld, String ownerNameOld, String ownerCodeNew, String ownerNameNew) {
        this.actionUser = actionUser;
        this.actionIpAddress = actionIpAddress;
        this.actionTime = actionTime;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.ownerCodeOld = ownerCodeOld;
        this.ownerNameOld = ownerNameOld;
        this.ownerCodeNew = ownerCodeNew;
        this.ownerNameNew = ownerNameNew;
    }

    public TblSheActionLog(String actionUser, String actionIpAddress, Date actionTime, String oldValue, String newValue) {
        this.actionUser = actionUser;
        this.actionIpAddress = actionIpAddress;
        this.actionTime = actionTime;
        this.oldValue = oldValue;
        this.newValue = newValue;

    }

    // Property accessors
    public Long getActionLogId() {
        return this.actionLogId;
    }

    public void setActionLogId(Long actionLogId) {
        this.actionLogId = actionLogId;
    }

    public String getIsdn() {
        return this.isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getSessionUser() {
        return this.sessionUser;
    }

    public void setSessionUser(String sessionUser) {
        this.sessionUser = sessionUser;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getActionUser() {
        return this.actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public String getActionIpAddress() {
        return this.actionIpAddress;
    }

    public void setActionIpAddress(String actionIpAddress) {
        this.actionIpAddress = actionIpAddress;
    }

    public Date getActionTime() {
        return this.actionTime;
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

    public String getOwnerCodeNew() {
        return ownerCodeNew;
    }

    public void setOwnerCodeNew(String ownerCodeNew) {
        this.ownerCodeNew = ownerCodeNew;
    }

    public String getOwnerCodeOld() {
        return ownerCodeOld;
    }

    public void setOwnerCodeOld(String ownerCodeOld) {
        this.ownerCodeOld = ownerCodeOld;
    }

    public String getOwnerNameNew() {
        return ownerNameNew;
    }

    public void setOwnerNameNew(String ownerNameNew) {
        this.ownerNameNew = ownerNameNew;
    }

    public String getOwnerNameOld() {
        return ownerNameOld;
    }

    public void setOwnerNameOld(String ownerNameOld) {
        this.ownerNameOld = ownerNameOld;
    }
}