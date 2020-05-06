package com.viettel.im.database.BO;

import java.util.Date;

/**
 * TransactionId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TransactionId implements java.io.Serializable {

	// Fields

	private Long transId;
	private Date createDatetime;

	// Constructors

	/** default constructor */
	public TransactionId() {
	}

	/** full constructor */
	public TransactionId(Long transId, Date createDatetime) {
		this.transId = transId;
		this.createDatetime = createDatetime;
	}

	// Property accessors

	public Long getTransId() {
		return this.transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TransactionId))
			return false;
		TransactionId castOther = (TransactionId) other;

		return ((this.getTransId() == castOther.getTransId()) || (this
				.getTransId() != null
				&& castOther.getTransId() != null && this.getTransId().equals(
				castOther.getTransId())))
				&& ((this.getCreateDatetime() == castOther.getCreateDatetime()) || (this
						.getCreateDatetime() != null
						&& castOther.getCreateDatetime() != null && this
						.getCreateDatetime().equals(
								castOther.getCreateDatetime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTransId() == null ? 0 : this.getTransId().hashCode());
		result = 37
				* result
				+ (getCreateDatetime() == null ? 0 : this.getCreateDatetime()
						.hashCode());
		return result;
	}

}