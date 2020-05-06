package com.viettel.im.client.form;

import com.viettel.im.database.BO.GroupFilterRule;

/**
 *
 * @author AnDV
 *
 */
public class GroupFilterRuleForm {

    private Long groupFilterRuleId;
    private String groupFilterRuleCode;
    private String name;
    private String notes;
    private Long stockTypeId;

    public GroupFilterRuleForm() {
        resetForm();
    }

    public void resetForm() {
        this.groupFilterRuleId = 0L;
        this.groupFilterRuleCode = "";
        this.notes = "";
        this.name = "";
        this.stockTypeId = 0L;
    }

    public void copyDataToBO(GroupFilterRule groupFilterRule) {
        groupFilterRule.setGroupFilterRuleId(this.getGroupFilterRuleId());
        groupFilterRule.setGroupFilterRuleCode(this.getGroupFilterRuleCode());
        groupFilterRule.setNotes(this.getNotes());
        groupFilterRule.setName(this.getName());
        groupFilterRule.setStockTypeId(this.getStockTypeId());
    }

    public void copyDataFromBO(GroupFilterRule groupFilterRule) {
        this.setGroupFilterRuleId(groupFilterRule.getGroupFilterRuleId());
        this.setGroupFilterRuleCode(groupFilterRule.getGroupFilterRuleCode());
        this.setNotes(groupFilterRule.getNotes());
        this.setName(groupFilterRule.getName());
        this.setStockTypeId(groupFilterRule.getStockTypeId());
    }

    public String getGroupFilterRuleCode() {
        return groupFilterRuleCode;
    }

    public void setGroupFilterRuleCode(String groupFilterRuleCode) {
        this.groupFilterRuleCode = groupFilterRuleCode;
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

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public Long getGroupFilterRuleId() {
        return groupFilterRuleId;
    }

    public void setGroupFilterRuleId(Long groupFilterRuleId) {
        this.groupFilterRuleId = groupFilterRuleId;
    }

    
}
