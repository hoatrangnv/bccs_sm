package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * ActionLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ActionLog extends BasicBO {

    // Fields
    private Long actionId;
    private String actionType;
    private String actionCode;
    private String description;
    private String actionUser;
    private Date actionDate;
    private String actionIp;
    private Long objectId;
    
    private String itemCode;
    private String impactType;

    // Constructors
    /** default constructor */
    public ActionLog() {
    }

    /** minimal constructor */
    public ActionLog(Long actionId) {
        this.actionId = actionId;
    }

    /** full constructor */
    public ActionLog(Long actionId, String actionType, String description, String actionUser, Date actionDate, String actionIp, Long objectId) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.description = description;
        this.actionUser = actionUser;
        this.actionDate = actionDate;
        this.actionIp = actionIp;
        this.objectId = objectId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    
    
    // Property accessors
    public Long getActionId() {
        return this.actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionUser() {
        return this.actionUser;
    }

    public void setActionUser(String actionUser) {
        this.actionUser = actionUser;
    }

    public Date getActionDate() {
        return this.actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionIp() {
        return this.actionIp;
    }

    public void setActionIp(String actionIp) {
        this.actionIp = actionIp;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getImpactType() {
        return impactType;
    }

    public void setImpactType(String impactType) {
        this.impactType = impactType;
    }
    
}
