package com.viettel.im.database.BO;

import java.util.Date;

/**
 * StockHandset entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ViewStockHandset implements java.io.Serializable {

	// Fields

	private Long id;
	private Long stockModelId;
	private String name;
	private String serial;
	private String imei;
	private String handsetType;
	private Long ownerId;
	private Long ownerType;
	private Date createDate;
	private Long status;
	private String expStaCode;
    private Long stateId;
    private Long checkDial;
    private Double depositPrice;
    private Long dialStatus;
    private Long channelTypeId;
    private String contractCode;
    private String batchCode;

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }
	/** default constructor */
	public ViewStockHandset() {
	}

	/** minimal constructor */
	public ViewStockHandset(Long id) {
		this.id = id;
	}

	/** full constructor */
	public ViewStockHandset(Long id, Long stockModelId, String name, String serial,
			String imei, String handsetType, Long ownerId, Long ownerType,
			Date createDate, Long status, String expStaCode) {
		this.id = id;
		this.stockModelId = stockModelId;
		this.name = name;
		this.serial = serial;
		this.imei = imei;
		this.handsetType = handsetType;
		this.ownerId = ownerId;
		this.ownerType = ownerType;
		this.createDate = createDate;
		this.status = status;
		this.expStaCode = expStaCode;
	}

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockModelId() {
		return this.stockModelId;
	}

	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getHandsetType() {
		return this.handsetType;
	}

	public void setHandsetType(String handsetType) {
		this.handsetType = handsetType;
	}

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

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getExpStaCode() {
		return this.expStaCode;
	}

	public void setExpStaCode(String expStaCode) {
		this.expStaCode = expStaCode;
	}

    public Long getCheckDial() {
        return checkDial;
    }

    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    public Long getDialStatus() {
        return dialStatus;
    }

    public void setDialStatus(Long dialStatus) {
        this.dialStatus = dialStatus;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(Double depositPrice) {
        this.depositPrice = depositPrice;
    }
}