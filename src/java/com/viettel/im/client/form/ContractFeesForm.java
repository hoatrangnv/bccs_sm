/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tuan
 */
public class ContractFeesForm extends ActionForm {

    private Long channelTypeId;
    private String objectCode;
    private String objectName;
    private Date billCycle;
    private String actionCode;
    private String actionName;
    String[] selectedItems;

    public void ResetFormSearch() {
        channelTypeId = null;
        objectCode = null;
        objectName = null;
        billCycle = null;
        actionCode = null;
        actionName = null;
        selectedItems = null;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Date getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(Date billCycle) {
        this.billCycle = billCycle;
    }

    public String[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(String[] selectedItems) {
        this.selectedItems = selectedItems;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }    
    
}
