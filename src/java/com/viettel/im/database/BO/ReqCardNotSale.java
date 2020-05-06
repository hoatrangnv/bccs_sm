package com.viettel.im.database.BO;

import java.util.Date;

/**
 * BlockDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ReqCardNotSale implements java.io.Serializable {

	// Fields

	private Long reqId;
        private String reqCode;
        private Long createStaffId;
	private Long shopId;
        private Date createReqDate;
	private Date approveDate;
	private Long approveStaffId;
	private Long total;
	private Long totalValid;
	private Long status;
        private String filePath;
         private String staffCode;

	// Constructors

	/** default constructor */
	public ReqCardNotSale() {
	}

	/** minimal constructor */
	public ReqCardNotSale(Long reqId) {
		this.reqId = reqId;
	}

	/** full constructor */
	public ReqCardNotSale(Long reqId, Long shopId, String reqCode,
			Long createStaffId, Date createReqDate, Date approveDate, Long approveStaffId,
                        Long total, Long totalValid, Long status, String filePath, String staffCode) {
		this.reqId = reqId;
		this.shopId = shopId;
		this.reqCode = reqCode;
		this.createStaffId = createStaffId;
		this.createReqDate = createReqDate;
		this.approveDate = approveDate;
		this.approveStaffId = approveStaffId;
                this.total = total;
                this.totalValid = totalValid;
                this.status = status;
                this.filePath = filePath;
                this.staffCode = staffCode;
	}

	// Property accessors

	public Long getReqId() {
		return this.reqId;
	}

	public void setReqId(Long reqId) {
		this.reqId = reqId;
	}

	public String getReqCode() {
		return this.reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}

	public Long getCreateStaffId() {
		return this.createStaffId;
	}

	public void setCreateStaffId(Long createStaffId) {
		this.createStaffId = createStaffId;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Date getCreateReqDate() {
		return this.createReqDate;
	}

	public void setCreateReqDate(Date createReqDate) {
		this.createReqDate = createReqDate;
	}

	public Date getApproveDate() {
		return this.approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Long getApproveStaffId() {
		return this.approveStaffId;
	}

	public void setApproveStaffId(Long approveStaffId) {
		this.approveStaffId = approveStaffId;
	}
        
        public Long getTotal() {
		return this.total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}
        
        public Long getTotalValid() {
		return this.totalValid;
	}

	public void setTotalValid(Long totalValid) {
		this.totalValid = totalValid;
	}
        
        public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
        
        public String getFilePath() {
		return this.filePath;
	}
        
        public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
        
         public String getStaffCode() {
		return this.staffCode;
	}
        
        public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

}