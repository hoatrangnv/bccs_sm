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
public class DiscountBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long discountId = 0L;
    private Double discountRate = 0.0;
    private Double discountAmount = 0.0;
    private String name = null;
    private List list = null;
    private Long stockModelId = null;
    private List listStockModels = null;
    private Long rate = null;
    private Long discountGroupId = null;
    private String type = null;
    private String discountRateString ="";

    public Long getDiscountGroupId() {
        return discountGroupId;
    }

    public void setDiscountGroupId(Long discountGroupId) {
        this.discountGroupId = discountGroupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Double startTotalMoneyStockModel() throws Exception {
        if (listStockModels != null && listStockModels.size() > 0) {
            Iterator iterator = listStockModels.iterator();
            Double total = 0.0;
            while (iterator.hasNext()) {
                StockTransDetail object = (StockTransDetail) iterator.next();
                total = total + object.getAmount();
            }

            return total;
        }
        return 0.0;
    }

    public Double startTotal() throws Exception {
        return startTotalMoneyStockModel() - startTotalRate();
    }

    public Double startTotalRate() throws Exception {
        if (list != null && list.size() > 0) {
            Double total = 0.0, totalDiscount = 0.0;
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                DiscountBean discountBean = (DiscountBean) iterator.next();
                if (discountBean.getDiscountRate() != null) {
                    total = total + discountBean.getDiscountRate();
                }
                if (discountBean.getDiscountAmount() != null) {
                    totalDiscount = totalDiscount + discountBean.getDiscountAmount();
                }

            }

            Iterator iterator1 = listStockModels.iterator();
            Double totalMoney = 0.0;
            while (iterator1.hasNext()) {
                StockTransDetail object = (StockTransDetail) iterator1.next();
                totalMoney = totalMoney + object.getAmount();
            }

            Double t = 0.0;
            t = totalMoney * (total) / 100;
            t = t + totalDiscount;

            return t;

        }
        return 0.0;
    }

    public List getListStockModels() {
        return listStockModels;
    }

    public void setListStockModels(List listStockModels) {
        this.listStockModels = listStockModels;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscountRateString() {
        return discountRateString;
    }

    public void setDiscountRateString(String discountRateString) {
        this.discountRateString = discountRateString;
    }
    
}
