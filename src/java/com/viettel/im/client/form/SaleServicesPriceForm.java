package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.database.BO.SaleServicesPrice;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 20/03/2009
 *
 */
public class SaleServicesPriceForm extends ActionForm {

    private Long saleServicesPriceId;
    private Long saleServicesId;
    private String price;
    private Long status;
    private String description;
    private String staDate;
    private String endDate;
    private Long vat;
    private String createDate;
    private String username;
    private String pricePolicy;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public SaleServicesPriceForm() {
        resetForm();
    }

    public void copyDataFromBO(SaleServicesPrice saleServicesPrice) {
        this.setSaleServicesPriceId(saleServicesPrice.getSaleServicesPriceId());
        this.setSaleServicesId(saleServicesPrice.getSaleServicesId());

//        this.setPrice(saleServicesPrice.getPrice().toString());
        this.setPrice(NumberUtils.rounđAndFormatAmount(saleServicesPrice.getPrice()));

        this.setStatus(saleServicesPrice.getStatus());
        this.setDescription(saleServicesPrice.getDescription());
        try {
            this.setStaDate(DateTimeUtils.convertDateToString(saleServicesPrice.getStaDate()));
        } catch (Exception ex) {
            this.setStaDate(null);
        }
        try {
            this.setEndDate(DateTimeUtils.convertDateToString(saleServicesPrice.getEndDate()));
        } catch (Exception ex) {
            this.setEndDate(null);
        }
        this.setVat(saleServicesPrice.getVat().longValue());
        this.setPricePolicy(saleServicesPrice.getPricePolicy());

        this.setCurrency(saleServicesPrice.getCurrency());
    }

    public void copyDataToBO(SaleServicesPrice saleServicesPrice) {
        saleServicesPrice.setSaleServicesPriceId(this.getSaleServicesPriceId());
        saleServicesPrice.setSaleServicesId(this.getSaleServicesId());

//        saleServicesPrice.setPrice(this.getPrice().doubleValue());
        try {
//            saleServicesPrice.setPrice(Double.parseDouble(this.getPrice()));
            saleServicesPrice.setPrice(NumberUtils.convertStringtoNumber(this.getPrice()));
        } catch (Exception ex) {
            ex.printStackTrace();
            saleServicesPrice.setPrice(-1.0);
        }

        saleServicesPrice.setStatus(this.getStatus());
        saleServicesPrice.setDescription(this.getDescription());
        try {
            saleServicesPrice.setStaDate(DateTimeUtils.convertStringToDate(this.getStaDate()));
        } catch (Exception ex) {
            saleServicesPrice.setStaDate(null);
        }
        try {
            saleServicesPrice.setEndDate(DateTimeUtils.convertStringToDate(this.getEndDate()));
        } catch (Exception ex) {
            saleServicesPrice.setEndDate(null);
        }
        saleServicesPrice.setVat(this.getVat().doubleValue());
        saleServicesPrice.setPricePolicy(this.getPricePolicy());

        saleServicesPrice.setCurrency(this.getCurrency());
    }

    public void resetForm() {
        saleServicesPriceId = 0L;
        saleServicesId = 0L;
        price = null;
        status = 1L;
        description = "";
        try {
            staDate = DateTimeUtils.convertDateToString(new Date());
        } catch (Exception ex) {
            staDate = null;
        }
        endDate = "";
        vat = null;
        pricePolicy = "";

        currency = "";
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public Long getSaleServicesId() {
        return saleServicesId;
    }

    public void setSaleServicesId(Long saleServicesId) {
        this.saleServicesId = saleServicesId;
    }

    public Long getSaleServicesPriceId() {
        return saleServicesPriceId;
    }

    public void setSaleServicesPriceId(Long saleServicesPriceId) {
        this.saleServicesPriceId = saleServicesPriceId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public String getPricePolicy() {
        return pricePolicy;
    }

    public void setPricePolicy(String pricePolicy) {
        this.pricePolicy = pricePolicy;
    }
}
