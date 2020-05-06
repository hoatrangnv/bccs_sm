package com.viettel.im.database.BO;

import java.util.HashSet;
import java.util.Set;

/**
 * ReasonCm entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ReasonCm implements java.io.Serializable {

	// Fields

	private String reasonCode;
	private String reasonName;
	private String description;
	private Long status;
	private Set commItemParamReasons = new HashSet(0);
	private Set actionAudits = new HashSet(0);

	// Constructors

	/** default constructor */
	public ReasonCm() {
	}

	/** minimal constructor */
	public ReasonCm(String reasonCode, String reasonName) {
		this.reasonCode = reasonCode;
		this.reasonName = reasonName;
	}

	/** full constructor */
	public ReasonCm(String reasonCode, String reasonName, String description,
			Long status, Set commItemParamReasons, Set actionAudits) {
		this.reasonCode = reasonCode;
		this.reasonName = reasonName;
		this.description = description;
		this.status = status;
		this.commItemParamReasons = commItemParamReasons;
		this.actionAudits = actionAudits;
	}

	// Property accessors

	public String getReasonCode() {
		return this.reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonName() {
		return this.reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Set getCommItemParamReasons() {
		return this.commItemParamReasons;
	}

	public void setCommItemParamReasons(Set commItemParamReasons) {
		this.commItemParamReasons = commItemParamReasons;
	}

	public Set getActionAudits() {
		return this.actionAudits;
	}

	public void setActionAudits(Set actionAudits) {
		this.actionAudits = actionAudits;
	}

}