package com.viettel.im.database.BO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ReceiptType entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class ReceiptType implements java.io.Serializable {

	// Fields

	private Long receiptTypeId;
	private String receiptTypeCode;
	private String receiptTypeName;
	private String description;
	private String type;
	private String status;
	private String userName;
	private Date createDatetime;
	private String own;
	private String debt;
	private String checkDebit;
	private Set receiptExpenses = new HashSet(0);

	// Constructors

	/** default constructor */
	public ReceiptType() {
	}

	/** minimal constructor */
	public ReceiptType(Long receiptTypeId, String receiptTypeCode, String type,
			String status) {
		this.receiptTypeId = receiptTypeId;
		this.receiptTypeCode = receiptTypeCode;
		this.type = type;
		this.status = status;
	}

	/** full constructor */
	public ReceiptType(Long receiptTypeId, String receiptTypeCode,
			String receiptTypeName, String description, String type,
			String status, String userName, Date createDatetime, String own,
			String debt, String checkDebit, Set receiptExpenses) {
		this.receiptTypeId = receiptTypeId;
		this.receiptTypeCode = receiptTypeCode;
		this.receiptTypeName = receiptTypeName;
		this.description = description;
		this.type = type;
		this.status = status;
		this.userName = userName;
		this.createDatetime = createDatetime;
		this.own = own;
		this.debt = debt;
		this.checkDebit = checkDebit;
		this.receiptExpenses = receiptExpenses;
	}

	// Property accessors

	public Long getReceiptTypeId() {
		return this.receiptTypeId;
	}

	public void setReceiptTypeId(Long receiptTypeId) {
		this.receiptTypeId = receiptTypeId;
	}

	public String getReceiptTypeCode() {
		return this.receiptTypeCode;
	}

	public void setReceiptTypeCode(String receiptTypeCode) {
		this.receiptTypeCode = receiptTypeCode;
	}

	public String getReceiptTypeName() {
		return this.receiptTypeName;
	}

	public void setReceiptTypeName(String receiptTypeName) {
		this.receiptTypeName = receiptTypeName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getOwn() {
		return this.own;
	}

	public void setOwn(String own) {
		this.own = own;
	}

	public String getDebt() {
		return this.debt;
	}

	public void setDebt(String debt) {
		this.debt = debt;
	}

	public String getCheckDebit() {
		return this.checkDebit;
	}

	public void setCheckDebit(String checkDebit) {
		this.checkDebit = checkDebit;
	}

	public Set getReceiptExpenses() {
		return this.receiptExpenses;
	}

	public void setReceiptExpenses(Set receiptExpenses) {
		this.receiptExpenses = receiptExpenses;
	}

}