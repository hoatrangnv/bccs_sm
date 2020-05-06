package com.viettel.im.database.BO;

// default package

import com.viettel.im.client.form.SerialGoods;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ExpDetailFull entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ExpDetailFull implements java.io.Serializable {

	// Fields

	private Long expDetailId;
	private Long stockModelId;
	private String stockModelName;
	private String unit;
	private Long reqQuantity;
	private Long quantity;
	private Date expDate;
	private String expReqCode;
	private String expStaCode;
	private Long stockTypeId;
	private String stockTypeName;
	private String note;
    private List<SerialGoods> lstSerial ;//=new ArrayList<SerialGoods>();
    private Long status;

	// Constructors

	/** default constructor */
	public ExpDetailFull() {
        lstSerial =new ArrayList<SerialGoods>();
	}

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Long getExpDetailId() {
        return expDetailId;
    }

    public void setExpDetailId(Long expDetailId) {
        this.expDetailId = expDetailId;
    }

    public String getExpReqCode() {
        return expReqCode;
    }

    public void setExpReqCode(String expReqCode) {
        this.expReqCode = expReqCode;
    }

    public String getExpStaCode() {
        return expStaCode;
    }

    public void setExpStaCode(String expStaCode) {
        this.expStaCode = expStaCode;
    }

    public List<SerialGoods> getLstSerial() {
        return lstSerial;
    }

    public void setLstSerial(List<SerialGoods> lstSerial) {
        this.lstSerial = lstSerial;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(Long reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

	
}