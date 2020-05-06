/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author dattv
 */
public class FilterISDNForm extends ActionForm {

    private Long stockTypeId;
    
    private String groupFilterCode;
    private Long filterTypeId;
    private Long filterId;
    private String fromIsdn;
    private String toIsdn;
    private Long shopId;
    private boolean statusNew=true;
    private boolean statusUsed=true;
    private boolean statusPause=true;
    private boolean filterAgain=false;
    
    private Long numIsdnSuccess=0L;
    private String[] ruleSelected;
    private String pathOut="";
    String[] selectedRules;

    public String[] getSelectedRules() {
        return selectedRules;
    }

    public void setSelectedRules(String[] selectedRules) {
        this.selectedRules = selectedRules;
    }

    
    public String getPathOut() {
        return pathOut;
    }

    public void setPathOut(String pathOut) {
        this.pathOut = pathOut;
    }

    

    public String[] getRuleSelected() {
        return ruleSelected;
    }

    public void setRuleSelected(String[] ruleSelected) {
        this.ruleSelected = ruleSelected;
    }


    
    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }


    
    public Long getNumIsdnSuccess() {
        return numIsdnSuccess;
    }

    public void setNumIsdnSuccess(Long numIsdnSuccess) {
        this.numIsdnSuccess = numIsdnSuccess;
    }

    
    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public String getFromIsdn() {
        return fromIsdn;
    }

    public void setFromIsdn(String fromIsdn) {
        this.fromIsdn = fromIsdn;
    }

    public String getGroupFilterCode() {
        return groupFilterCode;
    }

    public void setGroupFilterCode(String groupFilterCode) {
        this.groupFilterCode = groupFilterCode;
    }

    public String getToIsdn() {
        return toIsdn;
    }

    public void setToIsdn(String toIsdn) {
        this.toIsdn = toIsdn;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public boolean isStatusNew() {
        return statusNew;
    }

    public void setStatusNew(boolean statusNew) {
        this.statusNew = statusNew;
    }

    public boolean isStatusPause() {
        return statusPause;
    }

    public void setStatusPause(boolean statusPause) {
        this.statusPause = statusPause;
    }

    public boolean isStatusUsed() {
        return statusUsed;
    }

    public void setStatusUsed(boolean statusUsed) {
        this.statusUsed = statusUsed;
    }

    public boolean isFilterAgain() {
        return filterAgain;
    }

    public void setFilterAgain(boolean filterAgain) {
        this.filterAgain = filterAgain;
    }
  
    
}
