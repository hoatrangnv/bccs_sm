package com.viettel.im.database.BO;

import java.util.ArrayList;
import java.util.List;

/**
 * SaleServicesModel entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SaleServicesModel implements java.io.Serializable {

	// Fields
	private Long saleServicesModelId;
	private Long saleServicesId;
	private Long stockTypeId;
	private Long status;
	private String notes;
    private Long updateStock;
    private Long checkStaffStock;
    private Long checkShopStock;

    //tamdt1 - start, 15/03/2009
    private String stockTypeName;
    private String shopName;

    public SaleServicesModel(Long saleServicesModelId, Long saleServicesId, Long stockTypeId, Long status,
            String notes, Long updateStock, Long checkStaffStock, Long checkShopStock, String stockTypeName) {
        this.saleServicesModelId = saleServicesModelId;
        this.saleServicesId = saleServicesId;
        this.stockTypeId = stockTypeId;
        this.status = status;
        this.notes = notes;
        this.updateStock = updateStock;
        this.checkStaffStock = checkStaffStock;
        this.checkShopStock = checkShopStock;
        this.stockTypeName = stockTypeName;
    }



    //phan bieu dien cay
    private List<SaleServicesDetail> listSaleServicesDetail = new ArrayList<SaleServicesDetail>();

    public List<SaleServicesDetail> getListSaleServicesDetail() {
        return listSaleServicesDetail;
    }

    public void setListSaleServicesDetail(List<SaleServicesDetail> listSaleServicesDetail) {
        this.listSaleServicesDetail = listSaleServicesDetail;
    }

    public String getStockTypeName() {
        return stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    //tamdt1 - end

	// Constructors

	/** default constructor */
	   public SaleServicesModel() {
        saleServicesModelId = 0L;
        saleServicesId = 0L;
        stockTypeId = 0L;
        status = 0L;
        notes = "";
    }

	/** minimal constructor */
	public SaleServicesModel(Long saleServicesModelId, Long status) {
		this.saleServicesModelId = saleServicesModelId;
		this.status = status;
	}

	/** full constructor */
	public SaleServicesModel(Long saleServicesModelId, Long saleServicesId,
			Long stockTypeId, Long status, String notes) {
		this.saleServicesModelId = saleServicesModelId;
		this.saleServicesId = saleServicesId;
		this.stockTypeId = stockTypeId;
		this.status = status;
		this.notes = notes;
	}

	// Property accessors

	public Long getSaleServicesModelId() {
		return this.saleServicesModelId;
	}

	public void setSaleServicesModelId(Long saleServicesModelId) {
		this.saleServicesModelId = saleServicesModelId;
	}

	public Long getSaleServicesId() {
		return this.saleServicesId;
	}

	public void setSaleServicesId(Long saleServicesId) {
		this.saleServicesId = saleServicesId;
	}

	public Long getStockTypeId() {
		return this.stockTypeId;
	}

	public void setStockTypeId(Long stockTypeId) {
		this.stockTypeId = stockTypeId;
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

    public Long getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(Long updateStock) {
        this.updateStock = updateStock;
    }

    public Long getCheckShopStock() {
        return checkShopStock;
    }

    public void setCheckShopStock(Long checkShopStock) {
        this.checkShopStock = checkShopStock;
    }

    public Long getCheckStaffStock() {
        return checkStaffStock;
    }

    public void setCheckStaffStock(Long checkStaffStock) {
        this.checkStaffStock = checkStaffStock;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
}