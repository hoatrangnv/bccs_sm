/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.Date;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class LogCallWsWallet implements java.io.Serializable {
    
    private Long id;
    private String isdn;
    private Long ewalletId;
    private String actionType;
    private Long statusProcess;
    private Long numberProcess;
    private Date insertDate;
    private String description;
    private String customerName;
    private Date doB;
    private String idNo;
    private String channelType;
    private Long parentId;
    private String idIssuePlace;
    private Date idIssueDate; 

    public LogCallWsWallet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public Long getEwalletId() {
        return ewalletId;
    }

    public void setEwalletId(Long ewalletId) {
        this.ewalletId = ewalletId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(Long statusProcess) {
        this.statusProcess = statusProcess;
    }

    public Long getNumberProcess() {
        return numberProcess;
    }

    public void setNumberProcess(Long numberProcess) {
        this.numberProcess = numberProcess;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDoB() {
        return doB;
    }

    public void setDoB(Date doB) {
        this.doB = doB;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getIdIssuePlace() {
        return idIssuePlace;
    }

    public void setIdIssuePlace(String idIssuePlace) {
        this.idIssuePlace = idIssuePlace;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }
    
}
