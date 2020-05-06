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
public class DiscountPolicyForm extends ActionSupport {
    private String discountPolicyId;
    private String discountPolicyName;
    private String defaultDiscountPolicyId;
    private String defaultDiscountPolicyName;
    private Long discountPolicyStatus;
    private Boolean isDefaultDiscountPolicy;

    public DiscountPolicyForm() {
    }

    public DiscountPolicyForm(String discountPolicyId, String discountPolicyName, String defaultDiscountPolicyId, String defaultDiscountPolicyName, Long discountPolicyStatus) {
        this.discountPolicyId = discountPolicyId;
        this.discountPolicyName = discountPolicyName;
        this.defaultDiscountPolicyId = defaultDiscountPolicyId;
        this.defaultDiscountPolicyName = defaultDiscountPolicyName;
        this.discountPolicyStatus = discountPolicyStatus;
    }

    public String getDefaultDiscountPolicyId() {
        return defaultDiscountPolicyId;
    }

    public void setDefaultDiscountPolicyId(String defaultDiscountPolicyId) {
        this.defaultDiscountPolicyId = defaultDiscountPolicyId;
    }

    public String getDiscountPolicyId() {
        return discountPolicyId;
    }

    public void setDiscountPolicyId(String discountPolicyId) {
        this.discountPolicyId = discountPolicyId;
    }

    public String getDiscountPolicyName() {
        return discountPolicyName;
    }

    public void setDiscountPolicyName(String discountPolicyName) {
        this.discountPolicyName = discountPolicyName;
    }

    public Long getDiscountPolicyStatus() {
        return discountPolicyStatus;
    }

    public void setDiscountPolicyStatus(Long discountPolicyStatus) {
        this.discountPolicyStatus = discountPolicyStatus;
    }

    public String getDefaultDiscountPolicyName() {
        return defaultDiscountPolicyName;
    }

    public void setDefaultDiscountPolicyName(String defaultDiscountPolicyName) {
        this.defaultDiscountPolicyName = defaultDiscountPolicyName;
    }

    public Boolean getIsDefaultDiscountPolicy() {
        return isDefaultDiscountPolicy;
    }

    public void setIsDefaultDiscountPolicy(Boolean isDefaultDiscountPolicy) {
        this.isDefaultDiscountPolicy = isDefaultDiscountPolicy;
    }

    public void resetForm() {
        this.setDiscountPolicyId("");
        this.setDiscountPolicyName("");
        this.setDefaultDiscountPolicyId("");
        this.setDefaultDiscountPolicyName("");
        this.setDiscountPolicyStatus(null);
        this.setIsDefaultDiscountPolicy(true);
    }

    public void copyDataToBO(AppParams appParams) {
        appParams.getId().setType(Constant.APP_PARAMS_DISCOUNT_POLICY);
        appParams.getId().setCode(this.getDiscountPolicyId());
        appParams.setName(this.getDiscountPolicyName());
        appParams.setStatus(this.getDiscountPolicyStatus().toString());
        if(this.getIsDefaultDiscountPolicy()) {
            appParams.setValue(Constant.DISCOUNT_POLICY_DEFAULT);
        } else {
            appParams.setValue(this.getDefaultDiscountPolicyId());
        }
    }

    public void copyDataFromBO(AppParams appParams) {
        this.setDiscountPolicyId(appParams.getId().getCode());
        this.setDiscountPolicyName(appParams.getName());
        this.setDiscountPolicyStatus(Long.parseLong(appParams.getStatus()));
        if(appParams.getValue().equals(Constant.DISCOUNT_POLICY_DEFAULT)) {
            this.setIsDefaultDiscountPolicy(true);
        } else {
            this.setDefaultDiscountPolicyId(appParams.getValue());
            this.setIsDefaultDiscountPolicy(false);
        }
    }

}
