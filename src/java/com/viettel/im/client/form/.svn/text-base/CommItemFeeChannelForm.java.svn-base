package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.CommItemFeeChannel;
import java.util.Date;

/**
 *
 * @author tamdt1
 * 
 */
public class CommItemFeeChannelForm {
    private Long itemFeeChannelId;
    private Long itemId;
    private Long channelTypeId;
    private Long fee;
    private Long vat;
    private String staDate;
    private String endDate;
    private String status;
    //
    private String itemName;
    //Advance
    private String[] channelTypeIdList;
    private String[] channelTypeIdCheckedList;
    private String[] feeList;
    private String[] vatList;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
    public String[] getChannelTypeIdCheckedList() {
        return channelTypeIdCheckedList;
    }

    public void setChannelTypeIdCheckedList(String[] channelTypeIdCheckedList) {
        this.channelTypeIdCheckedList = channelTypeIdCheckedList;
    }
    
    public String[] getChannelTypeIdList() {
        return channelTypeIdList;
    }

    public void setChannelTypeIdList(String[] channelTypeIdList) {
        this.channelTypeIdList = channelTypeIdList;
    }

    public String[] getFeeList() {
        return feeList;
    }

    public void setFeeList(String[] feeList) {
        this.feeList = feeList;
    }

    public String[] getVatList() {
        return vatList;
    }

    public void setVatList(String[] vatList) {
        this.vatList = vatList;
    }

    

    public CommItemFeeChannelForm() {
        resetForm();
    }

    public void resetForm() {
        itemFeeChannelId = 0L;
        itemId = 0L;
        channelTypeId = 0L;
        fee = 0L;
        vat = 0L;
        try {
            staDate = DateTimeUtils.convertDateToString(new Date());
        } catch (Exception ex) {
            ex.printStackTrace();
            staDate = "";
        }
        
        endDate = "";
        status = "";
        itemName = "";

        channelTypeIdList = null;
        feeList = null;
        vatList = null;
        
    }

    public void copyDataFromBO(CommItemFeeChannel commItemFeeChannel) {
        this.setItemFeeChannelId(commItemFeeChannel.getItemFeeChannelId());
        this.setItemId(commItemFeeChannel.getItemId());
        this.setChannelTypeId(commItemFeeChannel.getChannelTypeId());
        this.setFee(commItemFeeChannel.getFee());
        this.setVat(commItemFeeChannel.getVat());
        try {
            this.setStaDate(DateTimeUtils.convertDateToString(commItemFeeChannel.getStaDate()));
        } catch (Exception ex) {
            this.setStaDate(null);
        }
        try {
            this.setEndDate(DateTimeUtils.convertDateToString(commItemFeeChannel.getEndDate()));
        } catch (Exception ex) {
            this.setEndDate(null);
        }
        this.setStatus(commItemFeeChannel.getStatus());
    }

    public void copyDataToBO(CommItemFeeChannel commItemFeeChannel) {
        commItemFeeChannel.setItemFeeChannelId(this.getItemFeeChannelId());
        commItemFeeChannel.setItemId(this.getItemId());
        commItemFeeChannel.setChannelTypeId(this.getChannelTypeId());
        commItemFeeChannel.setFee(this.getFee());
        commItemFeeChannel.setVat(this.getVat());
        try {
            commItemFeeChannel.setStaDate(DateTimeUtils.convertStringToDate(this.getStaDate()));
        } catch (Exception ex) {
            commItemFeeChannel.setStaDate(null);
        }
        try {
            commItemFeeChannel.setEndDate(DateTimeUtils.convertStringToDate(this.getEndDate()));
        } catch (Exception ex) {
            commItemFeeChannel.setEndDate(null);
        }
        commItemFeeChannel.setStatus(this.getStatus());
    }


    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getItemFeeChannelId() {
        return itemFeeChannelId;
    }

    public void setItemFeeChannelId(Long itemFeeChannelId) {
        this.itemFeeChannelId = itemFeeChannelId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
        this.staDate = staDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getVat() {
        return vat;
    }

    public void setVat(Long vat) {
        this.vat = vat;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    

}
