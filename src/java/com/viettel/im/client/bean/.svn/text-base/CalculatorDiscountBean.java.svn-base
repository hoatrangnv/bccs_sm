/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import com.viettel.im.database.BO.StockTransDetail;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author haidd
 */
public class CalculatorDiscountBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List listStockModels = null;
    private Long rate = null;

    public List getListStockModels() {
        return listStockModels;
    }

    public void setListStockModels(List listStockModels) {
        this.listStockModels = listStockModels;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public long startTotalMoneyForDiscountRate() throws Exception {
        
        long temp = startTotalMoneyStockModel();
        long tempRate = getRate();
                
        
        long tempDiscount = temp * (tempRate)/100;
        
        return tempDiscount;
    }

    public long startTotalMoneyStockModel() throws Exception {
        if (listStockModels != null && listStockModels.size() > 0) {
            Iterator iterator = listStockModels.iterator();
            long total = 0L;
            while (iterator.hasNext()) {
                StockTransDetail object = (StockTransDetail) iterator.next();
                total = total + object.getAmount().longValue();
            }

            return total;
        }
        return 0;
    }
}
