/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.form;
import org.apache.struts.action.ActionForm;
import java.util.Date;

/**
 *
 * @author DatPV
 */

public class CommissionPolicyForm extends ActionForm{
    private String commItemFee;
    private String commItems;
    private String displayState;
    private String stockModelId1;
    private String stockModelId;
    private String commissionPolicyId;
    private String commissionPolicyName;    
    private String commissionPolicyStatus;
    private Date commissionPolicyStart;
    private Date commissionPolicyEnd;

    public String getCommItemFee() {
        return commItemFee;
    }

    public void setCommItemFee(String commItemFee) {
        this.commItemFee = commItemFee;
    }

    public String getCommItems() {
        return commItems;
    }

    public void setCommItems(String commItems) {
        this.commItems = commItems;
    }

    public Date getCommissionPolicyEnd() {
        return commissionPolicyEnd;
    }

    public void setCommissionPolicyEnd(Date commissionPolicyEnd) {
        this.commissionPolicyEnd = commissionPolicyEnd;
    }

    public String getCommissionPolicyId() {
        return commissionPolicyId;
    }

    public void setCommissionPolicyId(String commissionPolicyId) {
        this.commissionPolicyId = commissionPolicyId;
    }

    public String getCommissionPolicyName() {
        return commissionPolicyName;
    }

    public void setCommissionPolicyName(String commissionPolicyName) {
        this.commissionPolicyName = commissionPolicyName;
    }

    public Date getCommissionPolicyStart() {
        return commissionPolicyStart;
    }

    public void setCommissionPolicyStart(Date commissionPolicyStart) {
        this.commissionPolicyStart = commissionPolicyStart;
    }

    public String getCommissionPolicyStatus() {
        return commissionPolicyStatus;
    }

    public void setCommissionPolicyStatus(String commissionPolicyStatus) {
        this.commissionPolicyStatus = commissionPolicyStatus;
    }

    public String getDisplayState() {
        return displayState;
    }

    public void setDisplayState(String displayState) {
        this.displayState = displayState;
    }

    public String getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(String stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelId1() {
        return stockModelId1;
    }

    public void setStockModelId1(String stockModelId1) {
        this.stockModelId1 = stockModelId1;
    }

}