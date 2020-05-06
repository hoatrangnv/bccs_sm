package com.viettel.im.database.BO;

/**
 * StockModel entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class StockModel implements java.io.Serializable {

    // Fields
    private Long stockModelId;
    private String stockModelCode;
    private Long stockTypeId;
    private String name;
    private Long checkSerial;
    private Long checkDeposit;
    private Long checkDial;
    private String unit;
    private Long status;
    private String notes;
    private Long discountGroupId;
    private Long profileId;
    private Long telecomServiceId;
    private String nameUnit;    
    /*
     * author : hieptd2
     * add : them truong tim kiem check_stock_channel
     * 0 : Khong quan ly theo kenh
     * 1 : Quan ly theo kenh
     */
    private Integer checkStockChannel;
    //
    private String telecomServiceName;
    private String unitName;
    private String stockTypeName;
    private Long discountModelMapId; //tamdt1, them de phuc vu cho viec khai bao nhom chiet khau
    private Double sourcePrice;

    private String accountingModelCode;
    private String accountingModelName;
    //9969
    private Long stockTypeGroup;
    private Long stockModelType;

    public Long getStockTypeGroup() {
        return stockTypeGroup;
    }

    public void setStockTypeGroup(Long stockTypeGroup) {
        this.stockTypeGroup = stockTypeGroup;
    }

    public Long getStockModelType() {
        return stockModelType;
    }

    public void setStockModelType(Long stockModelType) {
        this.stockModelType = stockModelType;
    }

    //tamdt1, phuc vu viec hien thi thong tin mat hang
    public StockModel(Long stockModelId, String stockModelCode, Long stockTypeId, String name, Long checkSerial, Long checkDial, String telecomServiceName, String unitName, String stockTypeName) {
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockTypeId = stockTypeId;
        this.name = name;
        this.checkSerial = checkSerial;
        this.checkDial = checkDial;
        this.telecomServiceName = telecomServiceName;
        this.unitName = unitName;
        this.stockTypeName = stockTypeName;
    }

    public StockModel(Long stockModelId, String stockModelCode, String name, Long stockTypeId, String stockTypeName, Long telecomServiceId, String telecomServiceName, Long profileId) {
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockTypeId = stockTypeId;
        this.name = name;
        this.profileId = profileId;
        this.telecomServiceId = telecomServiceId;
        this.telecomServiceName = telecomServiceName;
        this.stockTypeName = stockTypeName;
    }

    public StockModel(Long stockModelId, String stockModelCode, String name, Long stockTypeId, String stockTypeName, Long telecomServiceId, String telecomServiceName, Long profileId, Double sourcePrice) {
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockTypeId = stockTypeId;
        this.name = name;
        this.profileId = profileId;
        this.telecomServiceId = telecomServiceId;
        this.telecomServiceName = telecomServiceName;
        this.stockTypeName = stockTypeName;
        this.sourcePrice = sourcePrice;
    }



    // Constructors
    /** default constructor */
    public StockModel() {
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    /** minimal constructor */
    public StockModel(Long stockModelId, String stockModelCode, String name,
            Long status) {
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.name = name;
        this.status = status;
    }

    /** full constructor */
    public StockModel(Long stockModelId, String stockModelCode,
            Long stockTypeId, String name, Long telecomServiceId, Long checkSerial,
            Long checkDeposit, Long checkDial, String unit, Long status,
            String notes, Long discountGroupId) {
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockTypeId = stockTypeId;
        this.name = name;
        this.telecomServiceId = telecomServiceId;
        this.checkSerial = checkSerial;
        this.checkDeposit = checkDeposit;
        this.checkDial = checkDial;
        this.unit = unit;
        this.status = status;
        this.notes = notes;
        this.discountGroupId = discountGroupId;
    }

    public StockModel(Long stockModelId, String stockModelCode,
            Long stockTypeId, String name, Long telecomServiceId, Long checkSerial,
            Long checkDeposit, Long checkDial, String unit, Long status,
            String notes, Long discountGroupId, String nameUnit) {
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockTypeId = stockTypeId;
        this.name = name;
        this.telecomServiceId = telecomServiceId;
        this.checkSerial = checkSerial;
        this.checkDeposit = checkDeposit;
        this.checkDial = checkDial;
        this.unit = unit;
        this.status = status;
        this.notes = notes;
        this.discountGroupId = discountGroupId;
        this.nameUnit = nameUnit;
    }

    public StockModel(Long stockModelId, String name) {
        this.stockModelId = stockModelId;
        this.name = name;
    }

    // Property accessors
    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
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

    public Long getStockTypeId() {
        return this.stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public Long getCheckSerial() {
        return this.checkSerial;
    }

    public void setCheckSerial(Long checkSerial) {
        this.checkSerial = checkSerial;
    }

    public Long getCheckDeposit() {
        return this.checkDeposit;
    }

    public void setCheckDeposit(Long checkDeposit) {
        this.checkDeposit = checkDeposit;
    }

    public Long getCheckDial() {
        return this.checkDial;
    }

    public void setCheckDial(Long checkDial) {
        this.checkDial = checkDial;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getDiscountGroupId() {
        return this.discountGroupId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
    }

    public String getTelecomServiceName() {
        return telecomServiceName;
    }

    public void setTelecomServiceName(String telecomServiceName) {
        this.telecomServiceName = telecomServiceName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getDiscountModelMapId() {
        return discountModelMapId;
    }

    public void setDiscountModelMapId(Long discountModelMapId) {
        this.discountModelMapId = discountModelMapId;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public Double getSourcePrice() {
        return sourcePrice;
    }

    public void setSourcePrice(Double sourcePrice) {
        this.sourcePrice = sourcePrice;
    }

    public String getAccountingModelCode() {
        return accountingModelCode;
    }

    public void setAccountingModelCode(String accountingModelCode) {
        this.accountingModelCode = accountingModelCode;
    }

    public String getAccountingModelName() {
        return accountingModelName;
    }

    public void setAccountingModelName(String accountingModelName) {
        this.accountingModelName = accountingModelName;
    }
    
    /*
     * author : hieptd
     * get set for Check Stock  Channel
     */
    public Integer getCheckStockChannel() {
        return checkStockChannel;
    }

    public void setCheckStockChannel(Integer checkStockChannel) {
        this.checkStockChannel = checkStockChannel;
    }
    private Long quantity;
    private Long amount;

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public boolean equals(Object obj) {
        if (obj instanceof StockModel) {
            StockModel smToCompare = (StockModel) obj;
            return this.stockModelId.equals(smToCompare.getStockModelId());
        }
        return false;
    }
}