package com.viettel.im.database.BO;

import java.util.Date;

/**
 * Promotion entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Promotion implements java.io.Serializable {

	// Fields

	private Long promotionId;
	private TelecomService telecomService;
	private String promotionName;
	private Long status;
	private Date staDate;
	private Date endDate;

    private Long rate;

    private Long amount;

    private Long type;

	// Constructors

	/** default constructor */
	public Promotion() {
	}

	/** minimal constructor */
	public Promotion(Long promotionId) {
		this.promotionId = promotionId;
	}

	/** full constructor */
	public Promotion(Long promotionId, TelecomService telecomService,
			String promotionCode, String promotionName, Long status,
			Date staDate, Date endDate) {
		this.promotionId = promotionId;
		this.telecomService = telecomService;
		this.promotionName = promotionName;
		this.status = status;
		this.staDate = staDate;
		this.endDate = endDate;
	}

	// Property accessors

	public Long getPromotionId() {
		return this.promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public TelecomService getTelecomService() {
		return this.telecomService;
	}

	public void setTelecomService(TelecomService telecomService) {
		this.telecomService = telecomService;
	}

	public String getPromotionName() {
		return this.promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

}