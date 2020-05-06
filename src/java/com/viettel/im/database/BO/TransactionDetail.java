package com.viettel.im.database.BO;

import java.util.Date;

/**
 * TransactionDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TransactionDetail implements java.io.Serializable {

	// Fields

	private Long transDetailId;
	private Date createDatetime;
	private Long transId;
	private Long stockModelId;
	
	private Long stateId;
	private Long quantity;
	private Long priceId;
	private Long discountId;
	private Long exportQuantity;

	// Constructors

	/** default constructor */
	public TransactionDetail() {
	}

	/** minimal constructor */
	public TransactionDetail(Long transDetailId, Long stateId, Long quantity,
			Long priceId) {
		this.transDetailId = transDetailId;
		this.stateId = stateId;
		this.quantity = quantity;
		this.priceId = priceId;
	}

	/** full constructor */
	public TransactionDetail(Long transDetailId, Date createDatetime,
			Long transId, Long stockModelId,  Long stateId,
			Long quantity, Long priceId, Long discountId, Long exportQuantity) {
		this.transDetailId = transDetailId;
		this.createDatetime = createDatetime;
		this.transId = transId;
		this.stockModelId = stockModelId;
		
		this.stateId = stateId;
		this.quantity = quantity;
		this.priceId = priceId;
		this.discountId = discountId;
		this.exportQuantity = exportQuantity;
	}

	// Property accessors

	public Long getTransDetailId() {
		return this.transDetailId;
	}

	public void setTransDetailId(Long transDetailId) {
		this.transDetailId = transDetailId;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Long getTransId() {
		return this.transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

	

	public Long getStateId() {
		return this.stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getPriceId() {
		return this.priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public Long getDiscountId() {
		return this.discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public Long getExportQuantity() {
		return this.exportQuantity;
	}

	public void setExportQuantity(Long exportQuantity) {
		this.exportQuantity = exportQuantity;
	}

}