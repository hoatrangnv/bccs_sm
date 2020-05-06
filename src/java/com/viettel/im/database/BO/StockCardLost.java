/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.Date;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class StockCardLost implements java.io.Serializable{
    
        // Field
	private Long stockCardLostId;
        private String serial;
        private Long stockModelId;
	private Long ownerId;
        private String ownerType;
        private Long reasonId;
        private Date createDate;
	private Date lostDate;
        private String filePath;
	private Long status;
        private String fileApprovePath;

	// Constructors

	/** default constructor */
	public StockCardLost() {
	}

	/** minimal constructor */
	public StockCardLost(Long stockCardLostId) {
		this.stockCardLostId = stockCardLostId;
	}

	/** full constructor */
	public StockCardLost(Long stockCardLostId, String serial,
			Long stockModelId, Long ownerId, String ownerType, Long reasonId, Date createDate, Date lostDate, String filePath,
                        Long status, String fileApprovePath) {
		this.stockCardLostId = stockCardLostId;
		this.serial = serial;
		this.stockModelId = stockModelId;
		this.ownerId = ownerId;
		this.ownerType = ownerType;
		this.reasonId = reasonId;
		this.createDate = createDate;
                this.lostDate = lostDate;
                this.filePath = filePath;
                this.status = status;
                this.fileApprovePath = fileApprovePath;
	}

	// Property accessors

	public Long getStockCardLostId() {
		return this.stockCardLostId;
	}

	public void setStockCardLostId(Long stockCardLostId) {
		this.stockCardLostId = stockCardLostId;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Long getStockModelId() {
		return this.stockModelId;
	}

	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerType() {
		return this.ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public Long getReasonId() {
		return this.reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
        
        public Date getLostDate() {
		return this.lostDate;
	}

	public void setLostDate(Date lostDate) {
		this.lostDate = lostDate;
	}
        
        public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
        
        public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
        
        public String getFileApprovePath() {
		return this.fileApprovePath;
	}
        
        public void setFileApprovePath(String fileApprovePath) {
		this.fileApprovePath = fileApprovePath;
	}

}
