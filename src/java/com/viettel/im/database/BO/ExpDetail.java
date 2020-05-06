package com.viettel.im.database.BO;

import java.util.Date;

/**
 * ExpDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ExpDetail implements java.io.Serializable {

	// Fields

	private Long expDetailId;
	private Long stockModelId;
	private String unit;
	private Long reqQuantity;
	private Long realQuantity;
	private Date expDate;
	private String expReqCode;
	private String expStaCode;
	private String note;
	private Long status;

	// Constructors

	/** default constructor */
	public ExpDetail() {
	}

	/** minimal constructor */
	public ExpDetail(Long expDetailId) {
		this.expDetailId = expDetailId;
	}

	/** full constructor */
	public ExpDetail(Long expDetailId, Long stockModelId, String unit,
			Long reqQuantity, Long realQuantity, Date expDate,
			String expReqCode, String expStaCode, String note, Long status) {
		this.expDetailId = expDetailId;
		this.stockModelId = stockModelId;
		this.unit = unit;
		this.reqQuantity = reqQuantity;
		this.realQuantity = realQuantity;
		this.expDate = expDate;
		this.expReqCode = expReqCode;
		this.expStaCode = expStaCode;
		this.note = note;
		this.status = status;
	}

	// Property accessors

	public Long getExpDetailId() {
		return this.expDetailId;
	}

	public void setExpDetailId(Long expDetailId) {
		this.expDetailId = expDetailId;
	}

	public Long getStockModelId() {
		return this.stockModelId;
	}

	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getReqQuantity() {
		return this.reqQuantity;
	}

	public void setReqQuantity(Long reqQuantity) {
		this.reqQuantity = reqQuantity;
	}

	public Long getRealQuantity() {
		return this.realQuantity;
	}

	public void setRealQuantity(Long realQuantity) {
		this.realQuantity = realQuantity;
	}

	public Date getExpDate() {
		return this.expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getExpReqCode() {
		return this.expReqCode;
	}

	public void setExpReqCode(String expReqCode) {
		this.expReqCode = expReqCode;
	}

	public String getExpStaCode() {
		return this.expStaCode;
	}

	public void setExpStaCode(String expStaCode) {
		this.expStaCode = expStaCode;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}