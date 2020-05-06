package com.viettel.im.database.BO;

import java.util.Date;

/**
 * MethodCallLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MethodCallLog implements java.io.Serializable {

	// Fields

	private Long methodCallLogId;
	private String className;
	private String methodName;
	private String parameter;
	private String createUser;
	private Date createDate;
	private Date lastUpdateTime;
	private String methodCallResult;

	// Constructors

	/** default constructor */
	public MethodCallLog() {
	}

	/** minimal constructor */
	public MethodCallLog(Long methodCallLogId) {
		this.methodCallLogId = methodCallLogId;
	}

	/** full constructor */
	public MethodCallLog(Long methodCallLogId, String className,
			String methodName, String parameter, String createUser,
			Date createDate, Date lastUpdateTime, String methodCallResult) {
		this.methodCallLogId = methodCallLogId;
		this.className = className;
		this.methodName = methodName;
		this.parameter = parameter;
		this.createUser = createUser;
		this.createDate = createDate;
		this.lastUpdateTime = lastUpdateTime;
		this.methodCallResult = methodCallResult;
	}

	// Property accessors

	public Long getMethodCallLogId() {
		return this.methodCallLogId;
	}

	public void setMethodCallLogId(Long methodCallLogId) {
		this.methodCallLogId = methodCallLogId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParameter() {
		return this.parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getMethodCallResult() {
		return this.methodCallResult;
	}

	public void setMethodCallResult(String methodCallResult) {
		this.methodCallResult = methodCallResult;
	}

}