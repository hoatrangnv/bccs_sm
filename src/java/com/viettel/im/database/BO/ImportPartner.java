package com.viettel.im.database.BO;

import java.util.Date;

/**
 * ImportPartner entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ImportPartner implements java.io.Serializable {

	// Fields

	private Long impPartnerId;
	private String partnerName;
	private Date impDate;
	private Long transporterId;
	private Long shopId;
	private Long staffId;
	private Long status;

	// Constructors

	/** default constructor */
	public ImportPartner() {
	}

	/** minimal constructor */
	public ImportPartner(Long impPartnerId) {
		this.impPartnerId = impPartnerId;
	}

	/** full constructor */
	public ImportPartner(Long impPartnerId, String partnerName, Date impDate,
			Long transporterId, Long shopId, Long staffId, Long status) {
		this.impPartnerId = impPartnerId;
		this.partnerName = partnerName;
		this.impDate = impDate;
		this.transporterId = transporterId;
		this.shopId = shopId;
		this.staffId = staffId;
		this.status = status;
	}

	// Property accessors

	public Long getImpPartnerId() {
		return this.impPartnerId;
	}

	public void setImpPartnerId(Long impPartnerId) {
		this.impPartnerId = impPartnerId;
	}

	public String getPartnerName() {
		return this.partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Date getImpDate() {
		return this.impDate;
	}

	public void setImpDate(Date impDate) {
		this.impDate = impDate;
	}

	public Long getTransporterId() {
		return this.transporterId;
	}

	public void setTransporterId(Long transporterId) {
		this.transporterId = transporterId;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}