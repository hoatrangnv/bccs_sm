package com.viettel.im.database.BO;

/**
 * TableColumnFull entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TableColumnFull implements java.io.Serializable {

    // Fields
    private TableColumnFullId id;
    private String columnComment;
    private String columnNullable;
    private String columnName;

    // Constructors
    /** default constructor */
    public TableColumnFull() {
    }

    public TableColumnFull(String columnName) {
        this.columnName = columnName;
    }

    /** full constructor */
    public TableColumnFull(TableColumnFullId id) {
        this.id = id;
    }

    // Property accessors
    public TableColumnFullId getId() {
        return this.id;
    }

    public void setId(TableColumnFullId id) {
        this.id = id;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnNullable() {
        return columnNullable;
    }

    public void setColumnNullable(String columnNullable) {
        this.columnNullable = columnNullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
