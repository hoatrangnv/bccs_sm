package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * StockTrans entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockTrans extends BasicBO {

	// Fields

	private Long stockTransId;
	private Long fromOwnerId;
	private Long fromOwnerType;
	private Long toOwnerId;
	private Long toOwnerType;
	private Date createDatetime;
	private Long stockTransType;
	private Long reasonId;
	private Long stockTransStatus;
	private Long payStatus;
	private Long depositStatus;
	private String note;
    private Long drawStatus;
    private Date realTransDate;
    private Long realTransUserId;
    private Long fromStockTransId;

    private Long channelTypeId;
    //trung dh3 R499
    private Long transType;
    private String contractCode;
    private String batchCode;
    //DINHDC PYC 22670 xuat hang cho dai ly
    private String fileAcceptNote;
    private String fileAcceptConfirm;
    private Long fileAcceptStatus;

    public String getFileAcceptNote() {
        return fileAcceptNote;
    }

    public void setFileAcceptNote(String fileAcceptNote) {
        this.fileAcceptNote = fileAcceptNote;
    }

    public String getFileAcceptConfirm() {
        return fileAcceptConfirm;
    }

    public void setFileAcceptConfirm(String fileAcceptConfirm) {
        this.fileAcceptConfirm = fileAcceptConfirm;
    }

    public Long getFileAcceptStatus() {
        return fileAcceptStatus;
    }

    public void setFileAcceptStatus(Long fileAcceptStatus) {
        this.fileAcceptStatus = fileAcceptStatus;
    }

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
    // Constructors
	/** default constructor */
	public StockTrans() {
	}

	/** minimal constructor */
	public StockTrans(Long stockTransId) {
		this.stockTransId = stockTransId;
	}

	/** full constructor */
	public StockTrans(Long stockTransId, Long fromOwnerId,
			Long fromOwnerType, Long toOwnerId, Long toOwnerType,
			Date createDatetime, Long stockTransType, Long reasonId,
			Long stockTransStatus, Long payStatus, Long depositStatus,
			String note, Long drawStatus) {
		this.stockTransId = stockTransId;
		this.fromOwnerId = fromOwnerId;
		this.fromOwnerType = fromOwnerType;
		this.toOwnerId = toOwnerId;
		this.toOwnerType = toOwnerType;
		this.createDatetime = createDatetime;
		this.stockTransType = stockTransType;
		this.reasonId = reasonId;
		this.stockTransStatus = stockTransStatus;
		this.payStatus = payStatus;
		this.depositStatus = depositStatus;
		this.note = note;
        this.drawStatus=drawStatus;
	}

	// Property accessors

	public Long getStockTransId() {
		return this.stockTransId;
	}

	public void setStockTransId(Long stockTransId) {
		this.stockTransId = stockTransId;
	}

	public Long getFromOwnerId() {
		return this.fromOwnerId;
	}

	public void setFromOwnerId(Long fromOwnerId) {
		this.fromOwnerId = fromOwnerId;
	}

	public Long getFromOwnerType() {
		return this.fromOwnerType;
	}

	public void setFromOwnerType(Long fromOwnerType) {
		this.fromOwnerType = fromOwnerType;
	}

	public Long getToOwnerId() {
		return this.toOwnerId;
	}

	public void setToOwnerId(Long toOwnerId) {
		this.toOwnerId = toOwnerId;
	}

	public Long getToOwnerType() {
		return this.toOwnerType;
	}

	public void setToOwnerType(Long toOwnerType) {
		this.toOwnerType = toOwnerType;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Long getStockTransType() {
		return this.stockTransType;
	}

	public void setStockTransType(Long stockTransType) {
		this.stockTransType = stockTransType;
	}

	public Long getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Long getStockTransStatus() {
		return this.stockTransStatus;
	}

	public void setStockTransStatus(Long stockTransStatus) {
		this.stockTransStatus = stockTransStatus;
	}

	public Long getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(Long payStatus) {
		this.payStatus = payStatus;
	}

	public Long getDepositStatus() {
		return this.depositStatus;
	}

	public void setDepositStatus(Long depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

    public Long getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Long drawStatus) {
        this.drawStatus = drawStatus;
    }

    public Date getRealTransDate() {
        return realTransDate;
    }

    public void setRealTransDate(Date realTransDate) {
        this.realTransDate = realTransDate;
    }

    public Long getRealTransUserId() {
        return realTransUserId;
    }

    public void setRealTransUserId(Long realTransUserId) {
        this.realTransUserId = realTransUserId;
    }

    public Long getFromStockTransId() {
        return fromStockTransId;
    }

    public void setFromStockTransId(Long fromStockTransId) {
        this.fromStockTransId = fromStockTransId;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getTransType() {
        return transType;
    }

    public void setTransType(Long transType) {
        this.transType = transType;
    }
    
}