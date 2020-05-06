package com.viettel.im.database.BO;

/**
 * VFilterIsdn entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VFilterIsdn implements java.io.Serializable {

	// Fields

	private String groupFilterRuleCode;
	private String groupFilterRuleName;
	private String filterTypeName;
	private Long filterTypeId;
	private Long rulesId;
	private String rulesName;
	private Long quantity;

	// Constructors

	/** default constructor */
	public VFilterIsdn() {
	}

	/** full constructor */
	public VFilterIsdn(String groupFilterRuleCode,
			String groupFilterRuleName, String filterTypeName,
			Long filterTypeId, Long rulesId, String rulesName, Long quantity) {
		this.groupFilterRuleCode = groupFilterRuleCode;
		this.groupFilterRuleName = groupFilterRuleName;
		this.filterTypeName = filterTypeName;
		this.filterTypeId = filterTypeId;
		this.rulesId = rulesId;
		this.rulesName = rulesName;
		this.quantity = quantity;
	}

	// Property accessors

	public String getGroupFilterRuleCode() {
		return this.groupFilterRuleCode;
	}

	public void setGroupFilterRuleCode(String groupFilterRuleCode) {
		this.groupFilterRuleCode = groupFilterRuleCode;
	}

	public String getGroupFilterRuleName() {
		return this.groupFilterRuleName;
	}

	public void setGroupFilterRuleName(String groupFilterRuleName) {
		this.groupFilterRuleName = groupFilterRuleName;
	}

	public String getFilterTypeName() {
		return this.filterTypeName;
	}

	public void setFilterTypeName(String filterTypeName) {
		this.filterTypeName = filterTypeName;
	}

	public Long getFilterTypeId() {
		return this.filterTypeId;
	}

	public void setFilterTypeId(Long filterTypeId) {
		this.filterTypeId = filterTypeId;
	}

	public Long getRulesId() {
		return this.rulesId;
	}

	public void setRulesId(Long rulesId) {
		this.rulesId = rulesId;
	}

	public String getRulesName() {
		return this.rulesName;
	}

	public void setRulesName(String rulesName) {
		this.rulesName = rulesName;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}



}