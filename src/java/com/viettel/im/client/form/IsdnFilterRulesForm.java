/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author Administrator
 */
import com.viettel.im.database.BO.IsdnFilterRules;
import org.apache.struts.action.ActionForm;

public class IsdnFilterRulesForm extends ActionForm {

    private Long rulesId;
    private Long filterTypeId;
    private Long status;
    private String name;
    private String notes;
    private Long price;
    private String maskMapping;
    private String ruleOrder;
    private String condition;
    private String pathExpFile;

    //
    private String groupFilterRuleCode;

    public IsdnFilterRulesForm() {
        resetForm();
    }

    public void copyDataFromBO(IsdnFilterRules isdnFilterRules) {
        this.setFilterTypeId(isdnFilterRules.getFilterTypeId());
        this.setName(isdnFilterRules.getName());
        this.setNotes(isdnFilterRules.getNotes());
        this.setPrice(isdnFilterRules.getPrice());
        this.setMaskMapping(isdnFilterRules.getMaskMapping());
        this.setRuleOrder(String.valueOf(isdnFilterRules.getRuleOrder()));
        this.setCondition(isdnFilterRules.getCondition());
    }

    public void copyDataToBO(IsdnFilterRules isdnFilterRules) {
        isdnFilterRules.setRulesId(this.getRulesId());
        isdnFilterRules.setFilterTypeId(this.getFilterTypeId());
        isdnFilterRules.setName(this.getName());
        isdnFilterRules.setNotes(this.getNotes());
        isdnFilterRules.setPrice(this.getPrice());
        isdnFilterRules.setMaskMapping(this.getMaskMapping());
        isdnFilterRules.setRuleOrder(Long.parseLong(this.getRuleOrder()));
        isdnFilterRules.setCondition(this.getCondition());
    }

    public void resetForm() {
        rulesId = 0L;
        filterTypeId = 0L;
        status = 0L;
        name = "";
        notes = "";
        price = null;
        maskMapping = "";
        ruleOrder = "";
        condition = "";
        groupFilterRuleCode = "";
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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

 

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(String ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    

    public String getGroupFilterRuleCode() {
        return groupFilterRuleCode;
    }

    public void setGroupFilterRuleCode(String groupFilterRuleCode) {
        this.groupFilterRuleCode = groupFilterRuleCode;
    }

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }


    

}
