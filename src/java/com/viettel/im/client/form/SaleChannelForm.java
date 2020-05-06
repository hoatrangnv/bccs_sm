package com.viettel.im.client.form;

import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 18/02/2009
 *
 */
public class SaleChannelForm extends ActionForm {
    private String saleChannelId;
    private String saleChannelTypeName;
    private String saleChannelType;
    private String saleChannelDescription;
    private String saleChannelStatus;
    private String description;

    public SaleChannelForm() {
        saleChannelId = "";
        saleChannelTypeName = "";
        saleChannelType = "";
        saleChannelDescription = "";
        saleChannelStatus = "";
    }

    public String getSaleChannelDescription() {
        return saleChannelDescription;
    }

    public void setSaleChannelDescription(String saleChannelDescription) {
        this.saleChannelDescription = saleChannelDescription;
    }

    public String getSaleChannelId() {
        return saleChannelId;
    }

    public void setSaleChannelId(String saleChannelId) {
        this.saleChannelId = saleChannelId;
    }

    public String getSaleChannelStatus() {
        return saleChannelStatus;
    }

    public void setSaleChannelStatus(String saleChannelStatus) {
        this.saleChannelStatus = saleChannelStatus;
    }

    public String getSaleChannelType() {
        return saleChannelType;
    }

    public void setSaleChannelType(String saleChannelType) {
        this.saleChannelType = saleChannelType;
    }

    public String getSaleChannelTypeName() {
        return saleChannelTypeName;
    }

    public void setSaleChannelTypeName(String saleChannelTypeName) {
        this.saleChannelTypeName = saleChannelTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String str) {
        this.description = str;
    }
}
