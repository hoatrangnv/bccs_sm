package com.viettel.im.database.BO;

import java.util.ArrayList;
import java.util.List;

/**
 * StockType entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class StockType implements java.io.Serializable {

	// Fields

	private Long stockTypeId;
	private String name;
	private Long status;
	private String notes;
    private String tableName;
    private Long checkExp;
    private List<StockModel> models = null;
    private List<StockTotalFull> listStockDetail =new ArrayList<StockTotalFull>();

    public List<StockTotalFull> getListStockDetail() {
        return listStockDetail;
    }

    public void setListStockDetail(List<StockTotalFull> listStockDetail) {
        this.listStockDetail = listStockDetail;
    }

    
    public List<StockModel> getModels() {
        return models;
    }

    public void setModels(List<StockModel> models) {
        this.models = models;
    }

	// Constructors

	/** default constructor */
	public StockType() {
	}

	/** minimal constructor */
	public StockType(Long stockTypeId, String name, Long status) {
		this.stockTypeId = stockTypeId;
		this.name = name;
		this.status = status;
	}

	/** full constructor */
	public StockType(Long stockTypeId, String name, Long status, String notes, String tableName) {
		this.stockTypeId = stockTypeId;
		this.name = name;
		this.status = status;
		this.notes = notes;
        this.tableName = tableName;
	}

    public Long getCheckExp() {
        return checkExp;
    }

    public void setCheckExp(Long checkExp) {
        this.checkExp = checkExp;
    }



	// Property accessors


     public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
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

}