package com.viettel.im.database.BO;

/**
 * ModelDepositChannelId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ModelDepositChannelId implements java.io.Serializable {

	// Fields

	private Long stockModelId;
	private Long channelTypeId;

	// Constructors

	/** default constructor */
	public ModelDepositChannelId() {
	}

	/** full constructor */
	public ModelDepositChannelId(Long stockModelId, Long channelTypeId) {
		this.stockModelId = stockModelId;
		this.channelTypeId = channelTypeId;
	}

	// Property accessors

	public Long getStockModelId() {
		return this.stockModelId;
	}

	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}

	public Long getChannelTypeId() {
		return this.channelTypeId;
	}

	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ModelDepositChannelId))
			return false;
		ModelDepositChannelId castOther = (ModelDepositChannelId) other;

		return ((this.getStockModelId() == castOther.getStockModelId()) || (this
				.getStockModelId() != null
				&& castOther.getStockModelId() != null && this
				.getStockModelId().equals(castOther.getStockModelId())))
				&& ((this.getChannelTypeId() == castOther.getChannelTypeId()) || (this
						.getChannelTypeId() != null
						&& castOther.getChannelTypeId() != null && this
						.getChannelTypeId()
						.equals(castOther.getChannelTypeId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getStockModelId() == null ? 0 : this.getStockModelId()
						.hashCode());
		result = 37
				* result
				+ (getChannelTypeId() == null ? 0 : this.getChannelTypeId()
						.hashCode());
		return result;
	}

}