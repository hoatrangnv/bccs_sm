package com.viettel.im.database.BO;

/**
 * SaleServicesDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class SaleServicesDetail implements java.io.Serializable {
    // Fields

    private Long id;
    private Long stockModelId;
    private Long saleServicesModelId;
    private Long priceId;
    private Long status;
    private String notes;
    //tamdt1 - start, 16/03/2009
    private Double price;
    private String priceDescription;
    private String stockModelName;
    //ThanhNC add
    private String stockTypeName;
    private String stockModelCode;
    private Long stockTypeId;
    private Long saleServicesId;

    public SaleServicesDetail(Long id, Long stockModelId, Long saleServicesModelId, Long priceId,
            Long status, String notes, String stockModelName, Double price, String priceDescription) {
        this.id = id;
        this.stockModelId = stockModelId;
        this.saleServicesModelId = saleServicesModelId;
        this.priceId = priceId;
        this.status = status;
        this.notes = notes;
        this.price = price;
        this.priceDescription = priceDescription;
        this.stockModelName = stockModelName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }

    //tamdt1 - end
    // Constructors
    /** default constructor */
    public SaleServicesDetail() {
    }

    /** minimal constructor */
    public SaleServicesDetail(Long id, Long status) {
        this.id = id;
        this.status = status;
    }

    /** full constructor */
    public SaleServicesDetail(Long id, Long stockModelId,
            Long saleServicesModelId, Long priceId, Long status, String notes) {
        this.id = id;
        this.stockModelId = stockModelId;
        this.saleServicesModelId = saleServicesModelId;
        this.priceId = priceId;
        this.status = status;
        this.notes = notes;
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

    public Long getSaleServicesModelId() {
        return this.saleServicesModelId;
    }

    public void setSaleServicesModelId(Long saleServicesModelId) {
        this.saleServicesModelId = saleServicesModelId;
    }

    public Long getPriceId() {
        return this.priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
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

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }
}
