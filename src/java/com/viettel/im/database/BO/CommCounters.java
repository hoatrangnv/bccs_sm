package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * CommCounters entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CommCounters extends BasicBO  {

	// Fields

	private Long counterId;
	private String counterCode;
	private String counterName;
	private String functionName;
	private Date createDate;
	private String status;
	private String detailFunctionName;
	private String reportTemplate;

	// Constructors

	/** default constructor */
	public CommCounters() {
	}

	/** minimal constructor */
	public CommCounters(Long counterId) {
		this.counterId = counterId;
	}

	/** full constructor */
	public CommCounters(Long counterId, String counterCode, String counterName,
			String functionName, Date createDate, String status,
			String detailFunctionName, String reportTemplate) {
		this.counterId = counterId;
		this.counterCode = counterCode;
		this.counterName = counterName;
		this.functionName = functionName;
		this.createDate = createDate;
		this.status = status;
		this.detailFunctionName = detailFunctionName;
		this.reportTemplate = reportTemplate;
	}

	// Property accessors

	public Long getCounterId() {
		return this.counterId;
	}

	public void setCounterId(Long counterId) {
		this.counterId = counterId;
	}

	public String getCounterCode() {
		return this.counterCode;
	}

	public void setCounterCode(String counterCode) {
		this.counterCode = counterCode;
	}

	public String getCounterName() {
		return this.counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDetailFunctionName() {
		return this.detailFunctionName;
	}

	public void setDetailFunctionName(String detailFunctionName) {
		this.detailFunctionName = detailFunctionName;
	}

	public String getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

}