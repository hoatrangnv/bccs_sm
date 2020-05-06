package com.viettel.im.database.BO;

import java.util.Date;

/**
 * CommItemParamReason entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CommItemParamReason implements java.io.Serializable {

    // Fields
    private Long id;
    private Long reasonId;
    private Long itemId;
    private String actionCode;
    private Date createDate;
    private Long status;
    private String reasonName;
    private String paramName;
    private String itemName;
    private Date startDate;
    private Date endDate;
    private Long serType;//=1: tra truoc;=2: trả sau    
    // Constructors
    /** default constructor */
    public CommItemParamReason() {
    }

    /** minimal constructor */
    public CommItemParamReason(Long id, Long reasonId, Long itemId,
            String actionCode) {
        this.id = id;
        this.reasonId = reasonId;
        this.itemId = itemId;
        this.actionCode = actionCode;
    }

    /** full constructor */
    public CommItemParamReason(Long id, Long reasonId, Long itemId,
            String actionCode, Date createDate, Date startDate, Date endDate, Long status, Long objectId) {
        this.id = id;
        this.reasonId = reasonId;
        this.itemId = itemId;
        this.actionCode = actionCode;
        this.createDate = createDate;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CommItemParamReason(Long id, Long reasonId, Long itemId,
            String actionCode, Date createDate, Date startDate, Date endDate, Long status, String reasonName, String paramName, String itemName) {
        this.id = id;
        this.reasonId = reasonId;
        this.itemId = itemId;
        this.actionCode = actionCode;
        this.createDate = createDate;
        this.status = status;
        this.reasonName = reasonName;
        this.paramName = paramName;
        this.itemName = itemName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CommItemParamReason(Long id, Long reasonId, Long itemId,
            String actionCode, Date createDate, Date startDate, Date endDate, Long status, String reasonName, String paramName, Long serType) {
        this.id = id;
        this.reasonId = reasonId;
        this.itemId = itemId;
        this.actionCode = actionCode;
        this.createDate = createDate;
        this.status = status;
        this.reasonName = reasonName;
        this.paramName = paramName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.serType = serType;
    }

    public CommItemParamReason(Long id, Long itemId,
            String actionCode, Date createDate, Date startDate, Date endDate, Long status, String paramName, Long serType) {
        this.id = id;
        this.itemId = itemId;
        this.actionCode = actionCode;
        this.createDate = createDate;
        this.status = status;        
        this.paramName = paramName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.serType = serType;
    }

    // Property accessors
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getSerType() {
        return serType;
    }

    public void setSerType(Long serType) {
        this.serType = serType;
    }
}
