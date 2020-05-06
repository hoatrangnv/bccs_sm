package com.viettel.im.database.BO;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractBankAccountGroup entity provides the base persistence definition of
 * the BankAccountGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BankAccountGroup implements java.io.Serializable {

	// Fields

	private Long bankAccountGroupId;
	private String code;
	private String name;
	private Long status;

	// Constructors

	/** default constructor */
	public BankAccountGroup() {
	}

	/** minimal constructor */
	public BankAccountGroup(Long bankAccountGroupId) {
		this.bankAccountGroupId = bankAccountGroupId;
	}

	/** full constructor */
	public BankAccountGroup(Long bankAccountGroupId, String code,
			String name, Long status, Set telecomServices, Set bankAccounts) {
		this.bankAccountGroupId = bankAccountGroupId;
		this.code = code;
		this.name = name;
		this.status = status;
		
	}

	// Property accessors

	public Long getBankAccountGroupId() {
		return this.bankAccountGroupId;
	}

	public void setBankAccountGroupId(Long bankAccountGroupId) {
		this.bankAccountGroupId = bankAccountGroupId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}