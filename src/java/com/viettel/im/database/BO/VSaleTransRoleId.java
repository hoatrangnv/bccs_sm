package com.viettel.im.database.BO;

import java.util.Date;

/**
 * VSaleTransRoleId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VSaleTransRoleId implements java.io.Serializable {

	// Fields

	private Long saleTransId;
	private Date saleTransDate;
	private Long shopId;
	private Long staffId;
	private String roleName;
	private String roleValue;

	// Constructors

	/** default constructor */
	public VSaleTransRoleId() {
	}

	/** full constructor */
	public VSaleTransRoleId(Long saleTransId, Date saleTransDate, Long shopId,
			Long staffId, String roleName, String roleValue) {
		this.saleTransId = saleTransId;
		this.saleTransDate = saleTransDate;
		this.shopId = shopId;
		this.staffId = staffId;
		this.roleName = roleName;
		this.roleValue = roleValue;
	}

	// Property accessors

	public Long getSaleTransId() {
		return this.saleTransId;
	}

	public void setSaleTransId(Long saleTransId) {
		this.saleTransId = saleTransId;
	}

	public Date getSaleTransDate() {
		return this.saleTransDate;
	}

	public void setSaleTransDate(Date saleTransDate) {
		this.saleTransDate = saleTransDate;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleValue() {
		return this.roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}
}