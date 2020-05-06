package com.viettel.im.client.form;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.database.BO.Price;
import java.util.Date;

/**
 *
 * @author tamdt1
 * 
 */
public class PriceForm extends ActionSupport {

    private Long priceId;
    private Long stockModelId;
    private String type;
    private String price;
    private Long status;
    private String description;
    private String staDate;
    private String endDate;
    private Long vat;
    private String pricePolicy;
    private Long pledgeAmount; //muc tien cam ket hang thang
    private Long pledgeTime; //thoi gian cam ket
    private Long priorPay; //so thang ung truoc
    private String currency;

    public PriceForm() {
        resetForm();
    }

    public void copyDataFromBO(Price price) {
        this.setPriceId(price.getPriceId());
        this.setStockModelId(price.getStockModelId());
        this.setType(price.getType());

//        this.setPrice(price.getPrice().longValue());
        // do chuc nang them gia tu dong format kieu US, khi copy vao BO phai fix kieu US.
        this.setPrice(NumberUtils.roundAndFormatNumberUSLocale(price.getPrice()));

        this.setStatus(price.getStatus());
        this.setDescription(price.getDescription());
        try {
            this.setStaDate(DateTimeUtils.convertDateToString(price.getStaDate()));
        } catch (Exception ex) {
            this.setStaDate("");
        }
        try {
            this.setEndDate(DateTimeUtils.convertDateToString(price.getEndDate()));
        } catch (Exception ex) {
            this.setEndDate("");
        }
        this.setVat(price.getVat().longValue());
        this.setPricePolicy(price.getPricePolicy());
        this.setPricePolicy(price.getPricePolicy());
        this.setPledgeAmount(price.getPledgeAmount());
        this.setPledgeTime(price.getPledgeTime());
        this.setPriorPay(price.getPriorPay());

        this.setCurrency(price.getCurrency());
    }

    public void copyDataToBO(Price price) {
        price.setPriceId(this.getPriceId());
        price.setStockModelId(this.getStockModelId());
        price.setType(this.getType());

//        price.setPrice(this.getPrice().doubleValue());
        try {
//            price.setPrice(Double.parseDouble(this.getPrice()));
            price.setPrice(NumberUtils.convertStringToNumberUSLocale(this.getPrice()));
        } catch (Exception ex) {
            ex.printStackTrace();
            price.setPrice(-1.0);
        }

        price.setStatus(this.getStatus());
        price.setDescription(this.getDescription());
        try {
            price.setStaDate(DateTimeUtils.convertStringToDate(this.getStaDate()));
        } catch (Exception ex) {
            price.setStaDate(new Date());
        }
        try {
            price.setEndDate(DateTimeUtils.convertStringToDate(this.getEndDate()));
        } catch (Exception ex) {
            price.setEndDate(new Date());
        }
        price.setVat(this.getVat().doubleValue());
        price.setPricePolicy(this.getPricePolicy());
        price.setPledgeAmount(this.getPledgeAmount());
        price.setPledgeTime(this.getPledgeTime());
        price.setPriorPay(this.getPriorPay());

        price.setCurrency(this.getCurrency());
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void resetForm() {
        priceId = 0L;
        stockModelId = 0L;
        type = "";
        price = null;
        status = 1L;
        description = "";
        try {
            staDate = DateTimeUtils.convertDateToString(new Date());
        } catch (Exception ex) {
            staDate = "";
        }

        endDate = "";
        vat = null;
        pricePolicy = "";
        pledgeAmount = null;
        pledgeTime = null;
        priorPay = null;

        currency = "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getPricePolicy() {
        return pricePolicy;
    }

    public void setPricePolicy(String pricePolicy) {
        this.pricePolicy = pricePolicy;
    }

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
        this.staDate = staDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public Long getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(Long pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public Long getPledgeTime() {
        return pledgeTime;
    }

    public void setPledgeTime(Long pledgeTime) {
        this.pledgeTime = pledgeTime;
    }

    public Long getPriorPay() {
        return priorPay;
    }

    public void setPriorPay(Long priorPay) {
        this.priorPay = priorPay;
    }
}
