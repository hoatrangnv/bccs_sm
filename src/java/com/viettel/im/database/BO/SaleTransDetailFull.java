 package com.viettel.im.database.BO;

import java.util.Date;

/**
 * SaleTransDetailFull entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SaleTransDetailFull implements java.io.Serializable {

	// Fields

	private Long saleTransDetailId;
	private Long saleTransId;
	private Date saleTransDate;
	private Long stockModelId;
	private String stockModelCode;
	private String name;
    private Long checkSerial;
	private Long stateId;
	private Long priceId;
	private Double price;
	private String description;
	private Long quantity;
	private Long discountId;
	private Double amount;
	private String transferGood;
	private Long promotionId;
	private Double promotionAmount;
	private String note;
	private Long updateStockType;
	private Long userDeliver;
	private String userDeliverCode;
	private String userDeliverName;
	private Date deliverDate;
	private Long userUpdate;
	private String userUpdateCode;
	private String userUpdateName;
	private Long deliverStatus;
    private Long saleServicesId;
    private Long  levels;
    private Long  isFineTrans;

    private String quantityMoney;
    private String priceMoney;
    private String amountMoney;

	// Constructors

	/** default constructor */
	public SaleTransDetailFull() {
	}

	/** minimal constructor */
	public SaleTransDetailFull(Long saleTransDetailId, Long saleTransId,
			Date saleTransDate, Long stockModelId, String stockModelCode,
			String name, Long stateId, Long priceId, Long quantity) {
		this.saleTransDetailId = saleTransDetailId;
		this.saleTransId = saleTransId;
		this.saleTransDate = saleTransDate;
		this.stockModelId = stockModelId;
		this.stockModelCode = stockModelCode;
		this.name = name;
		this.stateId = stateId;
		this.priceId = priceId;
		this.quantity = quantity;
	}

	/** full constructor */
	public SaleTransDetailFull(Long saleTransDetailId, Long saleTransId,
			Date saleTransDate, Long stockModelId, String stockModelCode,
			String name, Long stateId, Long priceId, Double price,
			String description, Long quantity, Long discountId, Double amount,
			String transferGood, Long promotionId, Double promotionAmount,
			String note, Long updateStockType, Long userDeliver,
			String userDeliverCode, String userDeliverName, Date deliverDate,
			Long userUpdate, String userUpdateCode, String userUpdateName,
			Long deliverStatus) {
		this.saleTransDetailId = saleTransDetailId;
		this.saleTransId = saleTransId;
		this.saleTransDate = saleTransDate;
		this.stockModelId = stockModelId;
		this.stockModelCode = stockModelCode;
		this.name = name;
		this.stateId = stateId;
		this.priceId = priceId;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.discountId = discountId;
		this.amount = amount;
		this.transferGood = transferGood;
		this.promotionId = promotionId;
		this.promotionAmount = promotionAmount;
		this.note = note;
		this.updateStockType = updateStockType;
		this.userDeliver = userDeliver;
		this.userDeliverCode = userDeliverCode;
		this.userDeliverName = userDeliverName;
		this.deliverDate = deliverDate;
		this.userUpdate = userUpdate;
		this.userUpdateCode = userUpdateCode;
		this.userUpdateName = userUpdateName;
		this.deliverStatus = deliverStatus;
	}

    public Long getIsFineTrans() {
        return isFineTrans;
    }

    public void setIsFineTrans(Long isFineTrans) {
        this.isFineTrans = isFineTrans;
    }

	// Property accessors

        
	public Long getSaleTransDetailId() {
		return this.saleTransDetailId;
	}

	public void setSaleTransDetailId(Long saleTransDetailId) {
		this.saleTransDetailId = saleTransDetailId;
	}

	public Long getSaleTransId() {
		return this.saleTransId;
	}

	public void setSaleTransId(Long saleTransId) {
		this.saleTransId = saleTransId;
	}

	public Date getSaleTransDate() {
		return this.saleTransDate;
	}

	public void setSaleTransDate(Date saleTransDate) {
		this.saleTransDate = saleTransDate;
	}

	public Long getStockModelId() {
		return this.stockModelId;
	}

	public void setStockModelId(Long stockModelId) {
		this.stockModelId = stockModelId;
	}

	public String getStockModelCode() {
		return this.stockModelCode;
	}

	public void setStockModelCode(String stockModelCode) {
		this.stockModelCode = stockModelCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStateId() {
		return this.stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getPriceId() {
		return this.priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getDiscountId() {
		return this.discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransferGood() {
		return this.transferGood;
	}

	public void setTransferGood(String transferGood) {
		this.transferGood = transferGood;
	}

	public Long getPromotionId() {
		return this.promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Double getPromotionAmount() {
		return this.promotionAmount;
	}

	public void setPromotionAmount(Double promotionAmount) {
		this.promotionAmount = promotionAmount;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getUpdateStockType() {
		return this.updateStockType;
	}

	public void setUpdateStockType(Long updateStockType) {
		this.updateStockType = updateStockType;
	}

	public Long getUserDeliver() {
		return this.userDeliver;
	}

	public void setUserDeliver(Long userDeliver) {
		this.userDeliver = userDeliver;
	}

	public String getUserDeliverCode() {
		return this.userDeliverCode;
	}

	public void setUserDeliverCode(String userDeliverCode) {
		this.userDeliverCode = userDeliverCode;
	}

	public String getUserDeliverName() {
		return this.userDeliverName;
	}

	public void setUserDeliverName(String userDeliverName) {
		this.userDeliverName = userDeliverName;
	}

	public Date getDeliverDate() {
		return this.deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Long getUserUpdate() {
		return this.userUpdate;
	}

	public void setUserUpdate(Long userUpdate) {
		this.userUpdate = userUpdate;
	}

	public String getUserUpdateCode() {
		return this.userUpdateCode;
	}

	public void setUserUpdateCode(String userUpdateCode) {
		this.userUpdateCode = userUpdateCode;
	}

	public String getUserUpdateName() {
		return this.userUpdateName;
	}

	public void setUserUpdateName(String userUpdateName) {
		this.userUpdateName = userUpdateName;
	}

	public Long getDeliverStatus() {
		return this.deliverStatus;
	}

	public void setDeliverStatus(Long deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

    public Long getCheckSerial() {
        return checkSerial;
    }

    public void setCheckSerial(Long checkSerial) {
        this.checkSerial = checkSerial;
    }

    public Long getLevels() {
        return levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getPriceMoney() {
        return priceMoney;
    }

    public void setPriceMoney(String priceMoney) {
        this.priceMoney = priceMoney;
    }

    public String getQuantityMoney() {
        return quantityMoney;
    }

    public void setQuantityMoney(String quantityMoney) {
        this.quantityMoney = quantityMoney;
    }



}