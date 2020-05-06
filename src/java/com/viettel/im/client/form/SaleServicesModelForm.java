package com.viettel.im.client.form;

import com.viettel.im.database.BO.SaleServicesModel;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 14/03/2009
 *
 */
public class SaleServicesModelForm extends ActionForm {
    //
    private Long saleServicesModelId;
	private Long saleServicesId;
	private Long stockTypeId;

    private Long stockModelId;
    
    private String notes;
    private Long updateStock;
    private Boolean checkStaffStock;
    private Boolean checkShopStock;

    //
    private String stockTypeName;
    private Long stockTypeStatus;
    private String stockTypeNotes;

    //
    private String[] arrSelectedShopId;

    public SaleServicesModelForm() {
        resetForm();
        
    }

    public void copyDataFromBO(SaleServicesModel saleServicesModel){
        this.setSaleServicesModelId(saleServicesModel.getSaleServicesModelId());
        this.setSaleServicesId(saleServicesModel.getSaleServicesId());
        this.setStockTypeId(saleServicesModel.getStockTypeId());
        this.setNotes(saleServicesModel.getNotes());
        this.setUpdateStock(saleServicesModel.getUpdateStock());
        if(saleServicesModel.getCheckStaffStock() != null && saleServicesModel.getCheckStaffStock().equals(1L)) {
            this.setCheckStaffStock(true);
        } else {
            this.setCheckStaffStock(false);
        }
        if(saleServicesModel.getCheckShopStock() != null && saleServicesModel.getCheckShopStock().equals(1L)) {
            this.setCheckShopStock(true);
        } else {
            this.setCheckShopStock(false);
        }
    }

    public void copyDataToBO(SaleServicesModel saleServicesModel){
        saleServicesModel.setSaleServicesModelId(this.getSaleServicesModelId());
        saleServicesModel.setSaleServicesId(this.getSaleServicesId());
        saleServicesModel.setStockTypeId(this.getStockTypeId());
        saleServicesModel.setNotes(this.getNotes());
        saleServicesModel.setUpdateStock(this.getUpdateStock());
        if(this.getCheckStaffStock() != null && this.getCheckStaffStock()) {
            saleServicesModel.setCheckStaffStock(1L);
        } else {
            saleServicesModel.setCheckStaffStock(0L);
        }
        if(this.getCheckShopStock() != null && this.getCheckShopStock()) {
            saleServicesModel.setCheckShopStock(1L);
        } else {
            saleServicesModel.setCheckShopStock(0L);
        }
        
    }
    
    public void resetForm() {
        saleServicesModelId = 0L;
        saleServicesId = 0L;
        stockTypeId = 0L;
        notes = "";
        updateStock = 1L;
        stockTypeName = "";
        stockTypeStatus = 0L;
        stockTypeNotes = "";
        checkStaffStock = false;
        checkShopStock = false;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public Long getSaleServicesModelId() {
        return saleServicesModelId;
    }

    public void setSaleServicesModelId(Long saleServicesModelId) {
        this.saleServicesModelId = saleServicesModelId;
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

    public String getStockTypeNotes() {
        return stockTypeNotes;
    }

    public void setStockTypeNotes(String stockTypeNotes) {
        this.stockTypeNotes = stockTypeNotes;
    }

    public Long getStockTypeStatus() {
        return stockTypeStatus;
    }

    public void setStockTypeStatus(Long stockTypeStatus) {
        this.stockTypeStatus = stockTypeStatus;
    }

    public Long getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(Long updateStock) {
        this.updateStock = updateStock;
    }

    public String[] getArrSelectedShopId() {
        return arrSelectedShopId;
    }

    public void setArrSelectedShopId(String[] arrSelectedShopId) {
        this.arrSelectedShopId = arrSelectedShopId;
    }

    public Boolean getCheckShopStock() {
        return checkShopStock;
    }

    public void setCheckShopStock(Boolean checkShopStock) {
        this.checkShopStock = checkShopStock;
    }

    public Boolean getCheckStaffStock() {
        return checkStaffStock;
    }

    public void setCheckStaffStock(Boolean checkStaffStock) {
        this.checkStaffStock = checkStaffStock;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }


}
