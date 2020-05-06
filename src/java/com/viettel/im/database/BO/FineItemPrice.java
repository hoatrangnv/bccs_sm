package com.viettel.im.database.BO;

import java.util.Date;

/**
 * FineItemPriceId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class FineItemPrice implements java.io.Serializable {

	// Fields

	private Long fineItemPriceId;
	private Long fineItemId;
	private Long status;
	private String description;
	private Date staDate;
	private Date endDate;
	private Long vat;
	private Date createDate;
	private String userName;
	private Long price;
    

	// Constructors

	/** default constructor */
	public FineItemPrice() {
	}

	/** minimal constructor */
	public FineItemPrice(Long fineItemPriceId, Long fineItemId, Long status) {
		this.fineItemPriceId = fineItemPriceId;
		this.fineItemId = fineItemId;
		this.status = status;
	}

	/** full constructor */
	public FineItemPrice(Long fineItemPriceId, Long fineItemId, Long status,
			String description, Date staDate, Date endDate, Long vat,
			Date createDate, String userName, Long price) {
		this.fineItemPriceId = fineItemPriceId;
		this.fineItemId = fineItemId;
		this.status = status;
		this.description = description;
		this.staDate = staDate;
		this.endDate = endDate;
		this.vat = vat;
		this.createDate = createDate;
		this.userName = userName;
		this.price = price;
	}

	// Property accessors

	public Long getFineItemPriceId() {
		return this.fineItemPriceId;
	}

	public void setFineItemPriceId(Long fineItemPriceId) {
		this.fineItemPriceId = fineItemPriceId;
	}

	public Long getFineItemId() {
		return this.fineItemId;
	}

	public void setFineItemId(Long fineItemId) {
		this.fineItemId = fineItemId;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStaDate() {
		return this.staDate;
	}

	public void setStaDate(Date staDate) {
		this.staDate = staDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getVat() {
		return this.vat;
	}

	public void setVat(Long vat) {
		this.vat = vat;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

  

	
}