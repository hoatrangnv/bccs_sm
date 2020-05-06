package com.viettel.im.database.BO;

import java.util.Date;

/**
 * InvoiceTransferLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class InvoiceTransferLog implements java.io.Serializable {

	// Fields

	private Long id;
	private Long invoiceListId;
	private String serialNo;
	private Long blockNo;
	private Long invoiceNo;
	private Long fromInvoice;
	private Long toInvoice;
	private String currInvoice;
	private Long parentShopId;
	private Long childShopId;
	private Long staffId;
	private String userCreated;
	private Date dateCreated;
	private Long transferType;

	// Constructors

	/** default constructor */
	public InvoiceTransferLog() {
	}

	/** minimal constructor */
	public InvoiceTransferLog(Long id) {
		this.id = id;
	}

	/** full constructor */
	public InvoiceTransferLog(Long id, Long invoiceListId, String serialNo,
			Long blockNo, Long invoiceNo, Long fromInvoice, Long toInvoice,
			String currInvoice, Long parentShopId, Long childShopId,
			Long staffId, String userCreated, Date dateCreated,
			Long transferType) {
		this.id = id;
		this.invoiceListId = invoiceListId;
		this.serialNo = serialNo;
		this.blockNo = blockNo;
		this.invoiceNo = invoiceNo;
		this.fromInvoice = fromInvoice;
		this.toInvoice = toInvoice;
		this.currInvoice = currInvoice;
		this.parentShopId = parentShopId;
		this.childShopId = childShopId;
		this.staffId = staffId;
		this.userCreated = userCreated;
		this.dateCreated = dateCreated;
		this.transferType = transferType;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceListId() {
		return this.invoiceListId;
	}

	public void setInvoiceListId(Long invoiceListId) {
		this.invoiceListId = invoiceListId;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getBlockNo() {
		return this.blockNo;
	}

	public void setBlockNo(Long blockNo) {
		this.blockNo = blockNo;
	}

	public Long getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Long getFromInvoice() {
		return this.fromInvoice;
	}

	public void setFromInvoice(Long fromInvoice) {
		this.fromInvoice = fromInvoice;
	}

	public Long getToInvoice() {
		return this.toInvoice;
	}

	public void setToInvoice(Long toInvoice) {
		this.toInvoice = toInvoice;
	}

	public String getCurrInvoice() {
		return this.currInvoice;
	}

	public void setCurrInvoice(String currInvoice) {
		this.currInvoice = currInvoice;
	}

	public Long getParentShopId() {
		return this.parentShopId;
	}

	public void setParentShopId(Long parentShopId) {
		this.parentShopId = parentShopId;
	}

	public Long getChildShopId() {
		return this.childShopId;
	}

	public void setChildShopId(Long childShopId) {
		this.childShopId = childShopId;
	}

	public Long getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getUserCreated() {
		return this.userCreated;
	}

	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getTransferType() {
		return this.transferType;
	}

	public void setTransferType(Long transferType) {
		this.transferType = transferType;
	}

}