package com.viettel.im.database.BO;

/**
 * LookupIsdnFullId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class LookupIsdnFullId implements java.io.Serializable {

	// Fields

	private String isdn;
	private String tableName;

	// Constructors

	/** default constructor */
	public LookupIsdnFullId() {
	}

	/** full constructor */
	public LookupIsdnFullId(String isdn, String tableName) {
		this.isdn = isdn;
		this.tableName = tableName;
	}

	// Property accessors

	public String getIsdn() {
		return this.isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LookupIsdnFullId))
			return false;
		LookupIsdnFullId castOther = (LookupIsdnFullId) other;

		return ((this.getIsdn() == castOther.getIsdn()) || (this.getIsdn() != null
				&& castOther.getIsdn() != null && this.getIsdn().equals(
				castOther.getIsdn())))
				&& ((this.getTableName() == castOther.getTableName()) || (this
						.getTableName() != null
						&& castOther.getTableName() != null && this
						.getTableName().equals(castOther.getTableName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getIsdn() == null ? 0 : this.getIsdn().hashCode());
		result = 37 * result
				+ (getTableName() == null ? 0 : this.getTableName().hashCode());
		return result;
	}

}