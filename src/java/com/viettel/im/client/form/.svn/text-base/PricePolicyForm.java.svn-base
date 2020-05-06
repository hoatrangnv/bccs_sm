package com.viettel.im.client.form;

import com.opensymphony.xwork2.ActionSupport;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AppParams;

/**
 *
 * author   : Doan Thanh 8
 * date     : 29/09/2010
 * desc     : thong tin ve chinh sach chiet khau
 *
 */
public class PricePolicyForm extends ActionSupport {
    private String pricePolicyId;
    private String pricePolicyName;
    private String defaultPricePolicyId;
    private String defaultPricePolicyName;
    private Long pricePolicyStatus;
    private Boolean isDefaultPricePolicy;

    public PricePolicyForm() {
    }

    public PricePolicyForm(String pricePolicyId, String pricePolicyName, String defaultPricePolicyId, String defaultPricePolicyName, Long pricePolicyStatus) {
        this.pricePolicyId = pricePolicyId;
        this.pricePolicyName = pricePolicyName;
        this.defaultPricePolicyId = defaultPricePolicyId;
        this.defaultPricePolicyName = defaultPricePolicyName;
        this.pricePolicyStatus = pricePolicyStatus;
    }

    public String getDefaultPricePolicyId() {
        return defaultPricePolicyId;
    }

    public void setDefaultPricePolicyId(String defaultPricePolicyId) {
        this.defaultPricePolicyId = defaultPricePolicyId;
    }

    public String getPricePolicyId() {
        return pricePolicyId;
    }

    public void setPricePolicyId(String pricePolicyId) {
        this.pricePolicyId = pricePolicyId;
    }

    public String getPricePolicyName() {
        return pricePolicyName;
    }

    public void setPricePolicyName(String pricePolicyName) {
        this.pricePolicyName = pricePolicyName;
    }

    public Long getPricePolicyStatus() {
        return pricePolicyStatus;
    }

    public void setPricePolicyStatus(Long pricePolicyStatus) {
        this.pricePolicyStatus = pricePolicyStatus;
    }

    public String getDefaultPricePolicyName() {
        return defaultPricePolicyName;
    }

    public void setDefaultPricePolicyName(String defaultPricePolicyName) {
        this.defaultPricePolicyName = defaultPricePolicyName;
    }

    public Boolean getIsDefaultPricePolicy() {
        return isDefaultPricePolicy;
    }

    public void setIsDefaultPricePolicy(Boolean isDefaultPricePolicy) {
        this.isDefaultPricePolicy = isDefaultPricePolicy;
    }

    public void resetForm() {
        this.setPricePolicyId("");
        this.setPricePolicyName("");
        this.setDefaultPricePolicyId("");
        this.setDefaultPricePolicyName("");
        this.setPricePolicyStatus(null);
        this.setIsDefaultPricePolicy(true);
    }

    public void copyDataToBO(AppParams appParams) {
        appParams.getId().setType(Constant.APP_PARAMS_PRICE_POLICY);
        appParams.getId().setCode(this.getPricePolicyId());
        appParams.setName(this.getPricePolicyName());
        appParams.setStatus(this.getPricePolicyStatus().toString());
        if(this.getIsDefaultPricePolicy()) {
            appParams.setValue(Constant.PRICE_POLICY_DEFAULT);
        } else {
            appParams.setValue(this.getDefaultPricePolicyId());
        }
    }

    public void copyDataFromBO(AppParams appParams) {
        this.setPricePolicyId(appParams.getId().getCode());
        this.setPricePolicyName(appParams.getName());
        this.setPricePolicyStatus(Long.parseLong(appParams.getStatus()));
        if(appParams.getValue().equals(Constant.PRICE_POLICY_DEFAULT)) {
            this.setIsDefaultPricePolicy(true);
        } else {
            this.setDefaultPricePolicyId(appParams.getValue());
            this.setIsDefaultPricePolicy(false);
        }
    }

}
