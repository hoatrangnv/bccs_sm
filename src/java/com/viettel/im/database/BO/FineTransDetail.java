package com.viettel.im.database.BO;

import java.util.Date;

/**
 * FineTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FineTransDetail implements java.io.Serializable {

	// Fields

	private Long fineTransDetailId;
	private Long fineTransId;
	private Date fineTransDate;
	private Long fineItemId;
	private Long fineItemPriceId;
	private Long quantity;
	private Long amount;
	private String note;
	private Long vatAmount;

	// Constructors

	/** default constructor */
	public FineTransDetail() {
	}

	/** minimal constructor */
	public FineTransDetail(Long fineTransDetailId, Long fineTransId,
			Date fineTransDate, Long fineItemId, Long fineItemPriceId,
			Long quantity) {
		this.fineTransDetailId = fineTransDetailId;
		this.fineTransId = fineTransId;
		this.fineTransDate = fineTransDate;
		this.fineItemId = fineItemId;
		this.fineItemPriceId = fineItemPriceId;
		this.quantity = quantity;
	}

	/** full constructor */
	public FineTransDetail(Long fineTransDetailId, Long fineTransId,
			Date fineTransDate, Long fineItemId, Long fineItemPriceId,
			Long quantity, Long amount, String note, Long vatAmount) {
		this.fineTransDetailId = fineTransDetailId;
		this.fineTransId = fineTransId;
		this.fineTransDate = fineTransDate;
		this.fineItemId = fineItemId;
		this.fineItemPriceId = fineItemPriceId;
		this.quantity = quantity;
		this.amount = amount;
		this.note = note;
		this.vatAmount = vatAmount;
	}

	// Property accessors

	public Long getFineTransDetailId() {
		return this.fineTransDetailId;
	}

	public void setFineTransDetailId(Long fineTransDetailId) {
		this.fineTransDetailId = fineTransDetailId;
	}

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

	public Long getFineItemId() {
		return this.fineItemId;
	}

	public void setFineItemId(Long fineItemId) {
		this.fineItemId = fineItemId;
	}

	public Long getFineItemPriceId() {
		return this.fineItemPriceId;
	}

	public void setFineItemPriceId(Long fineItemPriceId) {
		this.fineItemPriceId = fineItemPriceId;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getVatAmount() {
		return this.vatAmount;
	}

	public void setVatAmount(Long vatAmount) {
		this.vatAmount = vatAmount;
	}


}