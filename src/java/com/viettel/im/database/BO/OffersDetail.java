package com.viettel.im.database.BO;

/**
 * OffersDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OffersDetail implements java.io.Serializable {

	// Fields

	private Long offersDetailId;
	private Long offersId;
	private Long goodsId;
	private Long amount;
	private Long dialId;

	// Constructors

	/** default constructor */
	public OffersDetail() {
	}

	/** minimal constructor */
	public OffersDetail(Long offersDetailId) {
		this.offersDetailId = offersDetailId;
	}

	/** full constructor */
	public OffersDetail(Long offersDetailId, Long offersId, Long goodsId,
			Long amount, Long dialId) {
		this.offersDetailId = offersDetailId;
		this.offersId = offersId;
		this.goodsId = goodsId;
		this.amount = amount;
		this.dialId = dialId;
	}

	// Property accessors

	public Long getOffersDetailId() {
		return this.offersDetailId;
	}

	public void setOffersDetailId(Long offersDetailId) {
		this.offersDetailId = offersDetailId;
	}

	public Long getOffersId() {
		return this.offersId;
	}

	public void setOffersId(Long offersId) {
		this.offersId = offersId;
	}

	public Long getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Long getAmount() {
		return this.amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getDialId() {
		return this.dialId;
	}

	public void setDialId(Long dialId) {
		this.dialId = dialId;
	}

}