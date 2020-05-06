package com.viettel.im.database.BO;

import java.util.Date;

/**
 * VInvoiceUsedRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VInvoiceUsedRole implements java.io.Serializable {

	// Fields

        private VInvoiceUsedRoleId id;
	//private Long invoiceUsedId;
	private Date invoiceDate;
	private String serialNo;
	private Long invoiceId;
	private String invoiceNo;
	private Long shopId;
	private Long staffId;
	//private String roleName;
	private String roleValue;

	// Constructors

	/** default constructor */
	public VInvoiceUsedRole() {
	}

	/** full constructor */
	public VInvoiceUsedRole(Long invoiceUsedId, Date invoiceDate,
			String serialNo, Long invoiceId, String invoiceNo, Long shopId,
			Long staffId, String roleName, String roleValue) {
		//this.invoiceUsedId = invoiceUsedId;
		this.invoiceDate = invoiceDate;
		this.serialNo = serialNo;
		this.invoiceId = invoiceId;
		this.invoiceNo = invoiceNo;
		this.shopId = shopId;
		this.staffId = staffId;
		//this.roleName = roleName;
		this.roleValue = roleValue;
	}

    public VInvoiceUsedRoleId getId() {
        return id;
    }

    public void setId(VInvoiceUsedRoleId id) {
        this.id = id;
    }

    public VInvoiceUsedRole(VInvoiceUsedRoleId id, Date invoiceDate, String serialNo, Long invoiceId, String invoiceNo, Long shopId, Long staffId, String roleValue) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.serialNo = serialNo;
        this.invoiceId = invoiceId;
        this.invoiceNo = invoiceNo;
        this.shopId = shopId;
        this.staffId = staffId;
        this.roleValue = roleValue;
    }



	// Property accessors

//	public Long getInvoiceUsedId() {
//		return this.invoiceUsedId;
//	}
//
//	public void setInvoiceUsedId(Long invoiceUsedId) {
//		this.invoiceUsedId = invoiceUsedId;
//	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

//	public String getRoleName() {
//		return this.roleName;
//	}
//
//	public void setRoleName(String roleName) {
//		this.roleName = roleName;
//	}
//
	public String getRoleValue() {
		return this.roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

}