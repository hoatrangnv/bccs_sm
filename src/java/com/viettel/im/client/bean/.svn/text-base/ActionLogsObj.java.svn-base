package com.viettel.im.client.bean;

/**
 * StockTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ActionLogsObj {

    // Fields
    private Object oldObject;
    private Object newObject;
    private String tableName;
    private String columnName;
    private String oldValue;
    private String newValue;
    private Long idValue;

    public ActionLogsObj(Object oldObject, Object newObject) {
        this.oldObject = oldObject;
        this.newObject = newObject;
        this.tableName = null;
        this.columnName = null;
        this.oldValue = null;
        this.newValue = null;
    }

    public ActionLogsObj(Object oldObject, Object newObject, Long idValue) {
        this.oldObject = oldObject;
        this.newObject = newObject;
        this.idValue = idValue;
        this.tableName = null;
        this.columnName = null;
        this.oldValue = null;
        this.newValue = null;
    }

//    public ActionLogsObj(String tableName, String columnName, String oldValue, String newValue) {
//        this.tableName = tableName;
//        this.columnName = columnName;
//        this.oldValue = oldValue;
//        this.newValue = newValue;
//        this.oldObject = null;
//        this.newObject = null;
//    }
//    
//    public ActionLogsObj(String tableName, String columnName, String oldValue, String newValue, Long idValue) {
//        this.tableName = tableName;
//        this.columnName = columnName;
//        this.oldValue = oldValue;
//        this.newValue = newValue;
//        this.idValue = idValue;
//        this.oldObject = null;
//        this.newObject = null;
//    }
    
    /**
     * Created by : TrongLV
     * Create date : 2012-02-03
     * Purpose : 
     * @param tableName
     * @param columnName
     * @param oldValue
     * @param newValue 
     */
    public ActionLogsObj(String tableName, String columnName, Object oldValue, Object newValue) {
        this.tableName = tableName;
        this.columnName = columnName;        
        this.oldValue = oldValue==null?null:oldValue.toString();
        this.newValue = newValue==null?null:newValue.toString();
        this.oldObject = null;
        this.newObject = null;
    }
    
    public ActionLogsObj(String tableName, String columnName, Object oldValue, Object newValue, Long idValue) {
        this.tableName = tableName;
        this.columnName = columnName;        
        this.oldValue = oldValue==null?null:oldValue.toString();
        this.newValue = newValue==null?null:newValue.toString();
        this.idValue = idValue;
        this.oldObject = null;
        this.newObject = null;
    }

    public Object getNewObject() {
        return newObject;
    }

    public void setNewObject(Object newObject) {
        this.newObject = newObject;
    }

    public Object getOldObject() {
        return oldObject;
    }

    public void setOldObject(Object oldObject) {
        this.oldObject = oldObject;
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getIdValue() {
        return idValue;
    }

    public void setIdValue(Long idValue) {
        this.idValue = idValue;
    }
    
    
}
