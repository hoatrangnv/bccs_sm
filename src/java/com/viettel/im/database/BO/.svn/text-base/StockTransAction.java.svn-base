package com.viettel.im.database.BO;

import java.util.Date;

/**
 * StockTransAction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockTransAction implements java.io.Serializable {

	// Fields

	private Long actionId;
	private Long stockTransId;
	private String actionCode;
	private Long actionType;
	private String note;
	private String username;
	private Long actionStaffId;
	private Long approveStaffId;
	private Date approveDate;
    private Date createDatetime;
    private String fromActionCode;
    private Long actionStatus;

    //lamnt 06082017 them cho phan kho giam tru
    private Long reason;
    private String fileUpload;

    public Long getReason() {
        return reason;
    }

    public void setReason(Long reason) {
        this.reason = reason;
    }

    public String getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(String fileUpload) {
        this.fileUpload = fileUpload;
    }
    //end lamnt
	// Constructors

	/** default constructor */
	public StockTransAction() {
	}

	/** minimal constructor */
	public StockTransAction(Long actionId) {
		this.actionId = actionId;
	}

	/** full constructor */
	public StockTransAction(Long actionId, Long stockTransId,
			String actionCode, Long actionType, String note, String username,
			Long actionStaffId, Long approveStaffId, Date approveDate,Date createDatetime,String fromActionCode,
                        Long reason, String fileUpload) {
		this.actionId = actionId;
		this.stockTransId = stockTransId;
		this.actionCode = actionCode;
		this.actionType = actionType;
		this.note = note;
		this.username = username;
		this.actionStaffId = actionStaffId;
		this.approveStaffId = approveStaffId;
		this.approveDate = approveDate;
        this.createDatetime=createDatetime;
        this.fromActionCode=fromActionCode;
        
        this.reason = reason;
        this.fileUpload = fileUpload;
	}

	// Property accessors

	public Long getActionId() {
		return this.actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Long getStockTransId() {
		return this.stockTransId;
	}

	public void setStockTransId(Long stockTransId) {
		this.stockTransId = stockTransId;
	}

	public String getActionCode() {
		return this.actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public Long getActionType() {
		return this.actionType;
	}

	public void setActionType(Long actionType) {
		this.actionType = actionType;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getActionStaffId() {
		return this.actionStaffId;
	}

	public void setActionStaffId(Long actionStaffId) {
		this.actionStaffId = actionStaffId;
	}

	public Long getApproveStaffId() {
		return this.approveStaffId;
	}

	public void setApproveStaffId(Long approveStaffId) {
		this.approveStaffId = approveStaffId;
	}

	public Date getApproveDate() {
		return this.approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getFromActionCode() {
        return fromActionCode;
    }

    public void setFromActionCode(String fromActionCode) {
        this.fromActionCode = fromActionCode;
    }

    public Long getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(Long actionStatus) {
        this.actionStatus = actionStatus;
    }
     

}