package com.viettel.im.database.BO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TelecomService entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TelecomService implements java.io.Serializable {

	// Fields

	private Long telecomServiceId;
	private String telServiceName;
	private String description;
	private Date createDate;
	private Long status;
	private Set appParamses = new HashSet(0);
	private Set saleServiceses = new HashSet(0);

	// Constructors

	/** default constructor */
	public TelecomService() {
	}

	/** minimal constructor */
	public TelecomService(Long telecomServiceId, String telServiceName) {
		this.telecomServiceId = telecomServiceId;
		this.telServiceName = telServiceName;
	}

	/** full constructor */
	public TelecomService(Long telecomServiceId, String telServiceName,
			String description, Date createDate, Long status, Set appParamses,
			Set saleServiceses) {
		this.telecomServiceId = telecomServiceId;
		this.telServiceName = telServiceName;
		this.description = description;
		this.createDate = createDate;
		this.status = status;
		this.appParamses = appParamses;
		this.saleServiceses = saleServiceses;
	}

	// Property accessors

	public Long getTelecomServiceId() {
		return this.telecomServiceId;
	}

	public void setTelecomServiceId(Long telecomServiceId) {
		this.telecomServiceId = telecomServiceId;
	}

	public String getTelServiceName() {
		return this.telServiceName;
	}

	public void setTelServiceName(String telServiceName) {
		this.telServiceName = telServiceName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Set getAppParamses() {
		return this.appParamses;
	}

	public void setAppParamses(Set appParamses) {
		this.appParamses = appParamses;
	}

	public Set getSaleServiceses() {
		return this.saleServiceses;
	}

	public void setSaleServiceses(Set saleServiceses) {
		this.saleServiceses = saleServiceses;
	}

}