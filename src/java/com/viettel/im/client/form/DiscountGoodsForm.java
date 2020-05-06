package com.viettel.im.client.form;

import com.viettel.im.database.BO.StockModel;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 24/03/2009
 *
 */
public class DiscountGoodsForm extends ActionForm {
    private Long stockModelId;
	private String stockModelCode;
	private String name;

    public DiscountGoodsForm() {
        stockModelId = 0L;
        stockModelCode = "";
        name = "";
    }

    public void copyDataFromBO(StockModel stockModel){
        this.setStockModelId(stockModel.getStockModelId());
        this.setStockModelCode(stockModel.getStockModelCode());
        this.setName(stockModel.getName());
    }

    public void copyDataToBO(StockModel stockModel){
        stockModel.setStockModelId(this.getStockModelId());
        stockModel.setStockModelCode(this.getStockModelCode());
        stockModel.setName(this.getName());
    }

    public void resetForm() {
        stockModelId = 0L;
        stockModelCode = "";
        name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockModelCode() {
        return stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    

}
