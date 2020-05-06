package com.viettel.im.database.BO;

/**
 * DslamAreaId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DslamAreaId implements java.io.Serializable {

	// Fields

	private Long dslamId;
	private String areaCode;

	// Constructors

	/** default constructor */
	public DslamAreaId() {
	}

	/** full constructor */
	public DslamAreaId(Long dslamId, String areaCode) {
		this.dslamId = dslamId;
		this.areaCode = areaCode;
	}

	// Property accessors

	public Long getDslamId() {
		return this.dslamId;
	}

	public void setDslamId(Long dslamId) {
		this.dslamId = dslamId;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}