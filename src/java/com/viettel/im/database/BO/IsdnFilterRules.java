/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

/**
 *
 * @author Administrator
 */
public class IsdnFilterRules implements Comparable<IsdnFilterRules> {

    private Long rulesId;
    private Long filterTypeId;
    private Long status;
    private String name;
    private String notes;
    private Long price;
    private String maskMapping;
    private Long ruleOrder;
    private Long stockModelId;
    private String condition;
    private String groupFilterRuleName;
    private String filterTypeName;

    /**
     * So sanh 2 doi tuong IsdnFilterRules
     * Neu co ruleOrder lon hon thi lon hon
     */
    public int compareTo(IsdnFilterRules o) {
        return this.ruleOrder.intValue() - o.ruleOrder.intValue() ;
    }

    
    @Override
    public boolean equals(Object obj1) {
        IsdnFilterRules obj = (IsdnFilterRules) obj1;
        return this.rulesId.equals(obj.rulesId);
    }

    
    public Long getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(Long ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getGroupFilterRuleName() {
        return groupFilterRuleName;
    }

    public void setGroupFilterRuleName(String groupFilterRuleName) {
        this.groupFilterRuleName = groupFilterRuleName;
    }

   


    public IsdnFilterRules(Long rulesId, Long filterTypeId, Long status, String name, String notes, Long price, String maskMapping, Long stockModelId, String condition) {
        this.rulesId = rulesId;
        this.filterTypeId = filterTypeId;
        this.status = status;
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.maskMapping = maskMapping;
        this.stockModelId = stockModelId;
        this.condition = condition;
    }
    public IsdnFilterRules(Long rulesId, Long filterTypeId, Long status, String name, String notes, Long price, String maskMapping, Long stockModelId, String condition,Long ruleOrder) {
        this.rulesId = rulesId;
        this.filterTypeId = filterTypeId;
        this.status = status;
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.maskMapping = maskMapping;
        this.stockModelId = stockModelId;
        this.condition = condition;
        this.ruleOrder=ruleOrder;
    }
    public IsdnFilterRules(Long rulesId, Long filterTypeId, Long status, String name, String notes, Long price, String maskMapping, Long stockModelId, String condition,Long ruleOrder,String groupFilterRuleName) {
        this.rulesId = rulesId;
        this.filterTypeId = filterTypeId;
        this.status = status;
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.maskMapping = maskMapping;
        this.stockModelId = stockModelId;
        this.condition = condition;
        this.ruleOrder=ruleOrder;
        this.groupFilterRuleName = groupFilterRuleName;
    }
    public IsdnFilterRules() {
    }

    public IsdnFilterRules(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public IsdnFilterRules(Long rulesId, Long filterTypeId, Long status, String name, String notes, Long price, String maskMapping) {
        this.rulesId = rulesId;
        this.filterTypeId = filterTypeId;
        this.status = status;
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.maskMapping = maskMapping;
    }

    public IsdnFilterRules(Long rulesId, Long ruleOrder) {
        this.rulesId = rulesId;
        this.ruleOrder = ruleOrder;
    }

       public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
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

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFilterTypeName() {
        return filterTypeName;
    }

    public void setFilterTypeName(String filterTypeName) {
        this.filterTypeName = filterTypeName;
    }


}
