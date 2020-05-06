package com.viettel.im.client.form;

import com.viettel.im.database.BO.SaleServicesDetail;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 16/03/2009
 *
 */
public class SaleServicesDetailForm extends ActionForm {
    //tuong ung cac truong trong DB
    private Long id;
	private Long stockModelId;
	private Long saleServicesModelId;
	private Long priceId;
	private String notes;
    private Long saleServicesId;
    //
    private Long stockTypeId;
    private String price;
    private Boolean requiredCheck;
    private String stockModelName;
    private Long packageGoodsMapId;

    public SaleServicesDetailForm() {
        id = 0L;
        stockModelId = 0L;
        saleServicesModelId = 0L;
        priceId = 0L;
        notes = "";
        price = "";
        stockModelName = "";
    }

    public void resetForm(){
        id = 0L;
        stockModelId = 0L;
        saleServicesModelId = 0L;
        priceId = 0L;
        notes = "";
        price = "";
        stockModelName = "";
    }

    public void copyDataFromBO(SaleServicesDetail saleServicesDetail){
        this.setId(saleServicesDetail.getId());
        this.setStockModelId(saleServicesDetail.getStockModelId());
        this.setSaleServicesModelId(saleServicesDetail.getSaleServicesModelId());
        this.setPriceId(saleServicesDetail.getPriceId());
    }

    public void copyDataToBO(SaleServicesDetail saleServicesDetail){
        saleServicesDetail.setId(this.getId());
        saleServicesDetail.setStockModelId(this.getStockModelId());
        saleServicesDetail.setSaleServicesModelId(this.getSaleServicesModelId());
        saleServicesDetail.setPriceId(this.getPriceId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getPriceId() {
        return priceId;
    }

    public void setPriceId(Long priceId) {
        this.priceId = priceId;
    }

    public Long getSaleServicesModelId() {
        return saleServicesModelId;
    }

    public void setSaleServicesModelId(Long saleServicesModelId) {
        this.saleServicesModelId = saleServicesModelId;
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

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public Long getPackageGoodsMapId() {
        return packageGoodsMapId;
    }

    public void setPackageGoodsMapId(Long packageGoodsMapId) {
        this.packageGoodsMapId = packageGoodsMapId;
    }

    public Boolean getRequiredCheck() {
        return requiredCheck;
    }

    public void setRequiredCheck(Boolean requiredCheck) {
        this.requiredCheck = requiredCheck;
    }
}
