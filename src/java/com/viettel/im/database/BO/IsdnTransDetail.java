package com.viettel.im.database.BO;

import java.util.Date;

/**
 * IsdnTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class IsdnTransDetail implements java.io.Serializable {

	// Fields

	private Long isdnTransDetailId;
	private Long isdnTransId;
	private String fromIsdn;
	private String toIsdn;
	private Long quantity;
	private String oldValueType;
	private String oldValue;
	private String newValueType;
	private String newValue;

    //tamdt1, bo sung them de hien thi tren list
    private String lastUpdateUser;
	private Date lastUpdateTime;

	// Constructors

	/** default constructor */
	public IsdnTransDetail() {
	}

    public IsdnTransDetail(Long isdnTransDetailId, Long isdnTransId, String fromIsdn, String toIsdn, Long quantity, String lastUpdateUser, Date lastUpdateTime) {
        this.isdnTransDetailId = isdnTransDetailId;
        this.isdnTransId = isdnTransId;
        this.fromIsdn = fromIsdn;
        this.toIsdn = toIsdn;
        this.quantity = quantity;
        this.lastUpdateUser = lastUpdateUser;
        this.lastUpdateTime = lastUpdateTime;
    }

	/** minimal constructor */
	public IsdnTransDetail(Long isdnTransDetailId) {
		this.isdnTransDetailId = isdnTransDetailId;
	}

	/** full constructor */
	public IsdnTransDetail(Long isdnTransDetailId, Long isdnTransId,
			String fromIsdn, String toIsdn, Long quantity, String oldValueType,
			String oldValue, String newValueType, String newValue) {
		this.isdnTransDetailId = isdnTransDetailId;
		this.isdnTransId = isdnTransId;
		this.fromIsdn = fromIsdn;
		this.toIsdn = toIsdn;
		this.quantity = quantity;
		this.oldValueType = oldValueType;
		this.oldValue = oldValue;
		this.newValueType = newValueType;
		this.newValue = newValue;
	}

	// Property accessors

	public Long getIsdnTransDetailId() {
		return this.isdnTransDetailId;
	}

	public void setIsdnTransDetailId(Long isdnTransDetailId) {
		this.isdnTransDetailId = isdnTransDetailId;
	}

    public Long getIsdnTransId() {
        return isdnTransId;
    }

    public void setIsdnTransId(Long isdnTransId) {
        this.isdnTransId = isdnTransId;
    }

	public String getFromIsdn() {
		return this.fromIsdn;
	}

	public void setFromIsdn(String fromIsdn) {
		this.fromIsdn = fromIsdn;
	}

	public String getToIsdn() {
		return this.toIsdn;
	}

	public void setToIsdn(String toIsdn) {
		this.toIsdn = toIsdn;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getOldValueType() {
		return this.oldValueType;
	}

	public void setOldValueType(String oldValueType) {
		this.oldValueType = oldValueType;
	}

	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValueType() {
		return this.newValueType;
	}

	public void setNewValueType(String newValueType) {
		this.newValueType = newValueType;
	}

	public String getNewValue() {
		return this.newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

}