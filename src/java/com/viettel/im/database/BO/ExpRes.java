package com.viettel.im.database.BO;

/**
 * ExpRes entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ExpRes implements java.io.Serializable {

	// Fields

	private Long id;
	private Long expDetailId;
	private Long expReqDetailId;
	private Long resourceTypeId;
	private String fromShopId;
	private String toSerial;
	private Long quantity;

	// Constructors

	/** default constructor */
	public ExpRes() {
	}

	/** minimal constructor */
	public ExpRes(Long id) {
		this.id = id;
	}

	/** full constructor */
	public ExpRes(Long id, Long expDetailId, Long expReqDetailId,
			Long resourceTypeId, String fromShopId, String toSerial,
			Long quantity) {
		this.id = id;
		this.expDetailId = expDetailId;
		this.expReqDetailId = expReqDetailId;
		this.resourceTypeId = resourceTypeId;
		this.fromShopId = fromShopId;
		this.toSerial = toSerial;
		this.quantity = quantity;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExpDetailId() {
		return this.expDetailId;
	}

	public void setExpDetailId(Long expDetailId) {
		this.expDetailId = expDetailId;
	}

	public Long getExpReqDetailId() {
		return this.expReqDetailId;
	}

	public void setExpReqDetailId(Long expReqDetailId) {
		this.expReqDetailId = expReqDetailId;
	}

	public Long getResourceTypeId() {
		return this.resourceTypeId;
	}

	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public String getFromShopId() {
		return this.fromShopId;
	}

	public void setFromShopId(String fromShopId) {
		this.fromShopId = fromShopId;
	}

	public String getToSerial() {
		return this.toSerial;
	}

	public void setToSerial(String toSerial) {
		this.toSerial = toSerial;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}