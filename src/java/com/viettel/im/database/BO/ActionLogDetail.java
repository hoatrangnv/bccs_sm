package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * ActionLogDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ActionLogDetail extends BasicBO {

    // Fields
    private Long actionDetailId;
    private Long actionId;
    private String tableName;
    private String columnName;
    private String oldValue;
    private String newValue;
    private String description;
    private Date actionDate;
    private Long objectId;

    // Constructors
    /** default constructor */
    public ActionLogDetail() {
    }

    /** minimal constructor */
    public ActionLogDetail(Long actionDetailId) {
        this.actionDetailId = actionDetailId;
    }

    /** full constructor */
    public ActionLogDetail(Long actionDetailId, Long actionId,
            String tableName, String columnName, String oldValue,
            String newValue, String description) {
        this.actionDetailId = actionDetailId;
        this.actionId = actionId;
        this.tableName = tableName;
        this.columnName = columnName;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.description = description;
    }

    // Property accessors

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
    
    
    public Long getActionDetailId() {
        return this.actionDetailId;
    }

    public void setActionDetailId(Long actionDetailId) {
        this.actionDetailId = actionDetailId;
    }

    public Long getActionId() {
        return this.actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return this.newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
}
