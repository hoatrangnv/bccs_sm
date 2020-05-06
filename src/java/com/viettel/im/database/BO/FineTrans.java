package com.viettel.im.database.BO;

import java.util.Date;

/**
 * FineTrans entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FineTrans implements java.io.Serializable {

	// Fields

	private Long fineTransId;
	private Date fineTransDate;
	private String status;
	private Long invoiceUsedId;
	private Date invoiceCreateDate;
	private Long shopId;
	private Long staffId;
	private String payMethod;
	private Long amountTax;
	private Long amountNotTax;
	private Long tax;
	private Long subId;
	private String isdn;
	private String custName;
	private String contractNo;
	private String telNumber;
	private String company;
	private String address;
	private String tin;
	private String note;
	private String destroyUser;
	private Date destroyDate;
	private String approverUser;
	private Date approverDate;
	private Long reasonId;
	private Long telecomServiceId;
	private String fineTransCode;
	private Long vat;



	// Constructors

	/** default constructor */
	public FineTrans() {
	}

	/** minimal constructor */
	public FineTrans(Long fineTransId, Date fineTransDate, String status) {
		this.fineTransId = fineTransId;
		this.fineTransDate = fineTransDate;
		this.status = status;
	}

	/** full constructor */
	public FineTrans(Long fineTransId, Date fineTransDate, String status,
			Long invoiceUsedId, Date invoiceCreateDate, Long shopId,
			Long staffId, String payMethod, Long amountTax, Long amountNotTax,
			Long tax, Long subId, String isdn, String custName,
			String contractNo, String telNumber, String company,
			String address, String tin, String note, String destroyUser,
			Date destroyDate, String approverUser, Date approverDate,
			Long reasonId, Long telecomServiceId, String fineTransCode, Long vat) {
		this.fineTransId = fineTransId;
		this.fineTransDate = fineTransDate;
		this.status = status;
		this.invoiceUsedId = invoiceUsedId;
		this.invoiceCreateDate = invoiceCreateDate;
		this.shopId = shopId;
		this.staffId = staffId;
		this.payMethod = payMethod;
		this.amountTax = amountTax;
		this.amountNotTax = amountNotTax;
		this.tax = tax;
		this.subId = subId;
		this.isdn = isdn;
		this.custName = custName;
		this.contractNo = contractNo;
		this.telNumber = telNumber;
		this.company = company;
		this.address = address;
		this.tin = tin;
		this.note = note;
		this.destroyUser = destroyUser;
		this.destroyDate = destroyDate;
		this.approverUser = approverUser;
		this.approverDate = approverDate;
		this.reasonId = reasonId;
		this.telecomServiceId = telecomServiceId;
		this.fineTransCode = fineTransCode;
		this.vat = vat;
	}

	// Property accessors

	public Long getFineTransId() {
		return this.fineTransId;
	}

	public void setFineTransId(Long fineTransId) {
		this.fineTransId = fineTransId;
	}

	public Date getFineTransDate() {
		return this.fineTransDate;
	}

	public void setFineTransDate(Date fineTransDate) {
		this.fineTransDate = fineTransDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getInvoiceUsedId() {
		return this.invoiceUsedId;
	}

	public void setInvoiceUsedId(Long invoiceUsedId) {
		this.invoiceUsedId = invoiceUsedId;
	}

	public Date getInvoiceCreateDate() {
		return this.invoiceCreateDate;
	}

	public void setInvoiceCreateDate(Date invoiceCreateDate) {
		this.invoiceCreateDate = invoiceCreateDate;
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

	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Long getAmountTax() {
		return this.amountTax;
	}

	public void setAmountTax(Long amountTax) {
		this.amountTax = amountTax;
	}

	public Long getAmountNotTax() {
		return this.amountNotTax;
	}

	public void setAmountNotTax(Long amountNotTax) {
		this.amountNotTax = amountNotTax;
	}

	public Long getTax() {
		return this.tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public Long getSubId() {
		return this.subId;
	}

	public void setSubId(Long subId) {
		this.subId = subId;
	}

	public String getIsdn() {
		return this.isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getTelNumber() {
		return this.telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTin() {
		return this.tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDestroyUser() {
		return this.destroyUser;
	}

	public void setDestroyUser(String destroyUser) {
		this.destroyUser = destroyUser;
	}

	public Date getDestroyDate() {
		return this.destroyDate;
	}

	public void setDestroyDate(Date destroyDate) {
		this.destroyDate = destroyDate;
	}

	public String getApproverUser() {
		return this.approverUser;
	}

	public void setApproverUser(String approverUser) {
		this.approverUser = approverUser;
	}

	public Date getApproverDate() {
		return this.approverDate;
	}

	public void setApproverDate(Date approverDate) {
		this.approverDate = approverDate;
	}

	public Long getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Long getTelecomServiceId() {
		return this.telecomServiceId;
	}

	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public String getFineTransCode() {
		return this.fineTransCode;
	}

	public void setFineTransCode(String fineTransCode) {
		this.fineTransCode = fineTransCode;
	}

	public Long getVat() {
		return this.vat;
	}

	public void setVat(Long vat) {
		this.vat = vat;
	}
}