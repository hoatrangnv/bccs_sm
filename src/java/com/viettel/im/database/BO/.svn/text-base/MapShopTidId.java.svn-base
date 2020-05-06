package com.viettel.im.database.BO;

/**
 * MapShopTidId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MapShopTidId implements java.io.Serializable {

	// Fields

	private Long shopId;
	private Long tid;

	// Constructors

	/** default constructor */
	public MapShopTidId() {
	}

	/** full constructor */
	public MapShopTidId(Long shopId, Long tid) {
		this.shopId = shopId;
		this.tid = tid;
	}

	// Property accessors

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getTid() {
		return this.tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MapShopTidId))
			return false;
		MapShopTidId castOther = (MapShopTidId) other;

		return ((this.getShopId() == castOther.getShopId()) || (this
				.getShopId() != null
				&& castOther.getShopId() != null && this.getShopId().equals(
				castOther.getShopId())))
				&& ((this.getTid() == castOther.getTid()) || (this.getTid() != null
						&& castOther.getTid() != null && this.getTid().equals(
						castOther.getTid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getShopId() == null ? 0 : this.getShopId().hashCode());
		result = 37 * result
				+ (getTid() == null ? 0 : this.getTid().hashCode());
		return result;
	}

}