package com.viettel.im.database.BO;

import java.util.Date;

/**
 * AccountAnypay entity.
 * 
 * @author MyEclipse Persistence Tools *
 */

public class AccountAnypay implements java.io.Serializable {

	// Fields

	private long accountId;
	private long ownerType;
	private long ownerId;
	private String isdn;
	private String serial;
	private String password;
	private Date startDate;
	private Date endDate;
	private String userCreated;
	private Date dateCreated;
	private long inStatus;

        private String ownerCode;

        private String newIsdn;
        private String newSerial;
        private String newInStatus;
        
	// Constructors

	/** default constructor */
	public AccountAnypay() {
	}

	/** full constructor */
	public AccountAnypay(long accountId, long ownerType, long ownerId,
			String isdn, String serial, String password, Date startDate,
			Date endDate, String userCreated, Date dateCreated, long inStatus) {
		this.accountId = accountId;
		this.ownerType = ownerType;
		this.ownerId = ownerId;
		this.isdn = isdn;
		this.serial = serial;
		this.password = password;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userCreated = userCreated;
		this.dateCreated = dateCreated;
		this.inStatus = inStatus;
	}

	// Property accessors

	public long getAccountId() {
		return this.accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public long getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(long ownerType) {
		this.ownerType = ownerType;
	}

	public long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getIsdn() {
		return this.isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUserCreated() {
		return this.userCreated;
	}

	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public long getInStatus() {
		return this.inStatus;
	}

	public void setInStatus(long inStatus) {
		this.inStatus = inStatus;
	}

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getNewInStatus() {
        return newInStatus;
    }

    public void setNewInStatus(String newInStatus) {
        this.newInStatus = newInStatus;
    }

    public String getNewIsdn() {
        return newIsdn;
    }

    public void setNewIsdn(String newIsdn) {
        this.newIsdn = newIsdn;
    }

    public String getNewSerial() {
        return newSerial;
    }

    public void setNewSerial(String newSerial) {
        this.newSerial = newSerial;
    }

    

}