package com.viettel.im.database.BO;

import java.util.Date;

/**
 * Offers entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Offers implements java.io.Serializable {

	// Fields

	private Long offersId;
	private Long shopId;
	private Long channelId;
	private String channelName;
	private Date offersDate;
	private Long status;

	// Constructors

	/** default constructor */
	public Offers() {
	}

	/** minimal constructor */
	public Offers(Long offersId) {
		this.offersId = offersId;
	}

	/** full constructor */
	public Offers(Long offersId, Long shopId, Long channelId,
			String channelName, Date offersDate, Long status) {
		this.offersId = offersId;
		this.shopId = shopId;
		this.channelId = channelId;
		this.channelName = channelName;
		this.offersDate = offersDate;
		this.status = status;
	}

	// Property accessors

	public Long getOffersId() {
		return this.offersId;
	}

	public void setOffersId(Long offersId) {
		this.offersId = offersId;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getChannelId() {
		return this.channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return this.channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getOffersDate() {
		return this.offersDate;
	}

	public void setOffersDate(Date offersDate) {
		this.offersDate = offersDate;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}