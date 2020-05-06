package com.viettel.im.database.BO;

/**
 * SaleServicesStockId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SaleServicesStockId implements java.io.Serializable {

	// Fields

	private Long saleServicesModelId;
	private Long shopId;

	// Constructors

	/** default constructor */
	public SaleServicesStockId() {
	}

	/** full constructor */
	public SaleServicesStockId(Long saleServicesModelId, Long shopId) {
		this.saleServicesModelId = saleServicesModelId;
		this.shopId = shopId;
	}

    public Long getSaleServicesModelId() {
        return saleServicesModelId;
    }

    public void setSaleServicesModelId(Long saleServicesModelId) {
        this.saleServicesModelId = saleServicesModelId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

	// Property accessors

}