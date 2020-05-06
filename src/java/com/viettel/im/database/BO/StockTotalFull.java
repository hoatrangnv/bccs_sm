package com.viettel.im.database.BO;

import java.util.Date;
import java.util.List;

/**
 * StockTotalFull entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StockTotalFull implements java.io.Serializable {

	// Fields

	private StockTotalFullId id;
        private Long quantity;
	private Long quantityIssue;
	private Long quantityDial;
	private Date modifiedDate;
	private Long status;
	private String stockModelCode;
	private String name;
	private Long stockTypeId;
    private String unit;
    private Long checkSerial;
    private String state;    
    private List<StockTransSerial> listSerial;
    private String staffCode;
    private Long ownerType;
    private String shop;
    private Long staffOwnerId;
    private Long isStaff;

    public Long getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(Long isStaff) {
        this.isStaff = isStaff;
    }

    public Long getStaffOwnerId() {
        return staffOwnerId;
    }

    public void setStaffOwnerId(Long staffOwnerId) {
        this.staffOwnerId = staffOwnerId;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

	// Constructors

	/** default constructor */
	public StockTotalFull() {
	}

	/** full constructor */
	public StockTotalFull(StockTotalFullId id) {
		this.id = id;
	}

	// Property accessors

	public StockTotalFullId getId() {
		return this.id;
	}

	public void setId(StockTotalFullId id) {
		this.id = id;
	}

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityIssue() {
        return quantityIssue;
    }

    public void setQuantityIssue(Long quantityIssue) {
        this.quantityIssue = quantityIssue;
    }

    public Long getQuantityDial() {
        return quantityDial;
    }

    public void setQuantityDial(Long quantityDial) {
        this.quantityDial = quantityDial;
    }
    
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getCheckSerial() {
        return checkSerial;
    }

    public void setCheckSerial(Long checkSerial) {
        this.checkSerial = checkSerial;
    }

    public List<StockTransSerial> getListSerial() {
        return listSerial;
    }

    public void setListSerial(List<StockTransSerial> listSerial) {
        this.listSerial = listSerial;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    

}