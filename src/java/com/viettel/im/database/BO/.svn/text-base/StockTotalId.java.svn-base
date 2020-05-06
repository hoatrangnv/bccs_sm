package com.viettel.im.database.BO;

/**
 * StockTotalId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockTotalId implements java.io.Serializable {

	// Fields

	private Long ownerId;
	private Long ownerType;
	private Long stockModelId;
	private Long stateId;

	// Constructors

	/** default constructor */
	public StockTotalId() {
	}

    public StockTotalId(Long ownerId, Long ownerType, Long stockModelId) {
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.stockModelId = stockModelId;
    }

    
	/** full constructor */
	public StockTotalId(Long ownerId, Long ownerType, Long stockModelId,
			Long stateId) {
		this.ownerId = ownerId;
		this.ownerType = ownerType;
		this.stockModelId = stockModelId;
		this.stateId = stateId;
	}

	// Property accessors

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(Long ownerType) {
		this.ownerType = ownerType;
	}

	public Long getStockModelId() {
		return this.stockModelId;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StockTotalId))
			return false;
		StockTotalId castOther = (StockTotalId) other;

		return ((this.getOwnerId() == castOther.getOwnerId()) || (this
				.getOwnerId() != null
				&& castOther.getOwnerId() != null && this.getOwnerId().equals(
				castOther.getOwnerId())))
				&& ((this.getOwnerType() == castOther.getOwnerType()) || (this
						.getOwnerType() != null
						&& castOther.getOwnerType() != null && this
						.getOwnerType().equals(castOther.getOwnerType())))
				&& ((this.getStockModelId() == castOther.getStockModelId()) || (this
						.getStockModelId() != null
						&& castOther.getStockModelId() != null && this
						.getStockModelId().equals(castOther.getStockModelId())))
				&& ((this.getStateId() == castOther.getStateId()) || (this
						.getStateId() != null
						&& castOther.getStateId() != null && this.getStateId()
						.equals(castOther.getStateId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOwnerId() == null ? 0 : this.getOwnerId().hashCode());
		result = 37 * result
				+ (getOwnerType() == null ? 0 : this.getOwnerType().hashCode());
		result = 37
				* result
				+ (getStockModelId() == null ? 0 : this.getStockModelId()
						.hashCode());
		result = 37 * result
				+ (getStateId() == null ? 0 : this.getStateId().hashCode());
		return result;
	}

}