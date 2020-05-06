package com.viettel.im.database.BO;

/**
 * TableColumnFullId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TableColumnFullId implements java.io.Serializable {

	// Fields

	private String tableName;
	private String columnName;

	// Constructors

	/** default constructor */
	public TableColumnFullId() {
	}

	/** minimal constructor */
	public TableColumnFullId(String tableName, String columnName) {
		this.tableName = tableName;
		this.columnName = columnName;
	}

	/** full constructor */
	public TableColumnFullId(String tableName, String columnName,
			String columnComment) {
		this.tableName = tableName;
		this.columnName = columnName;
	}

	// Property accessors

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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TableColumnFullId))
			return false;
		TableColumnFullId castOther = (TableColumnFullId) other;

		return ((this.getTableName() == castOther.getTableName()) || (this
				.getTableName() != null
				&& castOther.getTableName() != null && this.getTableName()
				.equals(castOther.getTableName())))
				&& ((this.getColumnName() == castOther.getColumnName()) || (this
						.getColumnName() != null
						&& castOther.getColumnName() != null && this
						.getColumnName().equals(castOther.getColumnName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		result = 37
				* result
				+ (getColumnName() == null ? 0 : this.getColumnName()
						.hashCode());
		return result;
	}

}