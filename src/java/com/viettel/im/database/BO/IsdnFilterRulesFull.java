/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.BO;


public class IsdnFilterRulesFull implements java.io.Serializable {
    private Long rulesId;
    private Long filterTypeId;
    private Long status;
    private String name;
    private String notes;
    private Long price;
    private String maskMapping;
    private Long ruleOrder;
    private Long stockModelId;
    private String stockModelName;
    private String filterTypeName;

    public IsdnFilterRulesFull() {
    }

    public IsdnFilterRulesFull(Long rulesId) {
        this.rulesId = rulesId;
    }

    public IsdnFilterRulesFull(Long rulesId, Long filterTypeId, Long status, String name, String notes, Long price, String maskMapping, Long ruleOrder, Long stockModelId, String stockModelName, String filterTypeName) {
        this.rulesId = rulesId;
        this.filterTypeId = filterTypeId;
        this.status = status;
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.maskMapping = maskMapping;
        this.ruleOrder = ruleOrder;
        this.stockModelId = stockModelId;
        this.stockModelName = stockModelName;
        this.filterTypeName = filterTypeName;
    }

    public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public String getFilterTypeName() {
        return filterTypeName;
    }

    public void setFilterTypeName(String filterTypeName) {
        this.filterTypeName = filterTypeName;
    }

    public String getMaskMapping() {
        return maskMapping;
    }

    public void setMaskMapping(String maskMapping) {
        this.maskMapping = maskMapping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(Long ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    public Long getRulesId() {
        return rulesId;
    }

    public void setRulesId(Long rulesId) {
        this.rulesId = rulesId;
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

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }
    
}
