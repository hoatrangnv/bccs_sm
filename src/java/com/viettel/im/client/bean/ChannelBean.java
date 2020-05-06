/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.io.Serializable;

/**
 *
 * @author AnDV
 */
public class ChannelBean implements Serializable {

    private Long id;
    private String name;
    private String code;
    private Long channelTypeId;
    private String discountPolicy;
    private String pricePolicy;
    private Long status;
    private String channelTypeName;
    private String discountPolicyName;
    private String pricePolicyName;
    private String provinceName;
    private String objectType;

    public ChannelBean() {
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

   

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(String discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public String getDiscountPolicyName() {
        return discountPolicyName;
    }

    public void setDiscountPolicyName(String discountPolicyName) {
        this.discountPolicyName = discountPolicyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

   

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getPricePolicy() {
        return pricePolicy;
    }

    public void setPricePolicy(String pricePolicy) {
        this.pricePolicy = pricePolicy;
    }

    public String getPricePolicyName() {
        return pricePolicyName;
    }

    public void setPricePolicyName(String pricePolicyName) {
        this.pricePolicyName = pricePolicyName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }
//Full contructor
    public ChannelBean(Long id, String name, String code, Long channelTypeId, String discountPolicy, String pricePolicy, Long status, String channelTypeName, String discountPolicyName, String pricePolicyName, String provinceName, String objectType) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.channelTypeId = channelTypeId;
        this.discountPolicy = discountPolicy;
        this.pricePolicy = pricePolicy;
        this.status = status;
        this.channelTypeName = channelTypeName;
        this.discountPolicyName = discountPolicyName;
        this.pricePolicyName = pricePolicyName;
        this.provinceName = provinceName;
        this.objectType = objectType;
    }
//Contructor for CollectFeesDAO
//AnDV
    public ChannelBean(Long id, String name, String code, Long channelTypeId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.channelTypeId = channelTypeId;
    }



   
   
}

