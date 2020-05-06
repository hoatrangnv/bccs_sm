package com.viettel.im.database.BO;

/**
 * VShopStaffId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VShopStaffId implements java.io.Serializable {

	// Fields

	private Long ownerId;
	private Long ownerType;

	// Constructors

	/** default constructor */
	public VShopStaffId() {
	}

	/** full constructor */
	public VShopStaffId(Long ownerId, Long ownerType) {
		this.ownerId = ownerId;
		this.ownerType = ownerType;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VShopStaffId))
			return false;
		VShopStaffId castOther = (VShopStaffId) other;

		return ((this.getOwnerId() == castOther.getOwnerId()) || (this
				.getOwnerId() != null
				&& castOther.getOwnerId() != null && this.getOwnerId().equals(
				castOther.getOwnerId())))
				&& ((this.getOwnerType() == castOther.getOwnerType()) || (this
						.getOwnerType() != null
						&& castOther.getOwnerType() != null && this
						.getOwnerType().equals(castOther.getOwnerType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getOwnerId() == null ? 0 : this.getOwnerId().hashCode());
		result = 37 * result
				+ (getOwnerType() == null ? 0 : this.getOwnerType().hashCode());
		return result;
	}

}