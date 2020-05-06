/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

import java.io.Serializable;

/**
 *
 * @author NamNX
 * Bean phuc vu viec bao cao so dep
 */
public class NiceNumberBean implements Serializable {

    private String groupFilterRuleCode; //tap luat
    private Long filterTypeId; //nhom luat
    private Long ruleId; //luat
    private Long count;//so luong
    private Long price;//gia tien
    private Long totalMoney;//tong tien
    private String groupFilterRuleName; //dau so
    private String filterTypeName; //nhom so
    private String ruleName; //loai so

    public NiceNumberBean(Long ruleId, Long count) {
        this.ruleId = ruleId;
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public String getGroupFilterRuleCode() {
        return groupFilterRuleCode;
    }

    public void setGroupFilterRuleCode(String groupFilterRuleCode) {
        this.groupFilterRuleCode = groupFilterRuleCode;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getFilterTypeName() {
        return filterTypeName;
    }

    public void setFilterTypeName(String filterTypeName) {
        this.filterTypeName = filterTypeName;
    }

    public String getGroupFilterRuleName() {
        return groupFilterRuleName;
    }

    public void setGroupFilterRuleName(String groupFilterRuleName) {
        this.groupFilterRuleName = groupFilterRuleName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }


    public NiceNumberBean(Long count, Long totalMoney, String groupFilterRuleName, String filterTypeName, String ruleName, Long price) {
        this.count = count;
        this.totalMoney = totalMoney;
        this.groupFilterRuleName = groupFilterRuleName;
        this.filterTypeName = filterTypeName;
        this.ruleName = ruleName;
        this.price = price;

    }

    public NiceNumberBean(String ruleName, String filterTypeName, String groupFilterRuleName, Long price) {

        this.groupFilterRuleName = groupFilterRuleName;
        this.filterTypeName = filterTypeName;
        this.ruleName = ruleName;
        this.price = price;
    }
}
