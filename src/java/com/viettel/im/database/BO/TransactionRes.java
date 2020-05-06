package com.viettel.im.database.BO;

/**
 * TransactionRes entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TransactionRes implements java.io.Serializable {

	// Fields

	private Long transSerialId;
	private Long transDetailId;
	private String fromSerial;
	private String toSerial;

	// Constructors

	/** default constructor */
	public TransactionRes() {
	}

	/** minimal constructor */
	public TransactionRes(Long transSerialId) {
		this.transSerialId = transSerialId;
	}

	/** full constructor */
	public TransactionRes(Long transSerialId, Long transDetailId,
			String fromSerial, String toSerial) {
		this.transSerialId = transSerialId;
		this.transDetailId = transDetailId;
		this.fromSerial = fromSerial;
		this.toSerial = toSerial;
	}

	// Property accessors

	public Long getTransSerialId() {
		return this.transSerialId;
	}

	public void setTransSerialId(Long transSerialId) {
		this.transSerialId = transSerialId;
	}

	public Long getTransDetailId() {
		return this.transDetailId;
	}

	public void setTransDetailId(Long transDetailId) {
		this.transDetailId = transDetailId;
	}

	public String getFromSerial() {
		return this.fromSerial;
	}

	public void setFromSerial(String fromSerial) {
		this.fromSerial = fromSerial;
	}

	public String getToSerial() {
		return this.toSerial;
	}

	public void setToSerial(String toSerial) {
		this.toSerial = toSerial;
	}

}