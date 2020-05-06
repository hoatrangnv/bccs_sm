/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author ANHLT
 */
public class CommItemFeeBean implements java.io.Serializable{
    private Long itemId;
    private String itemName;
    private Long inputType;
    private Long itemfeechannelId;
    private Long fee;

    public CommItemFeeBean(){}

    public CommItemFeeBean(Long itemID,String name,Long Type,Long feeId,Long fee)
    {
        this.itemId=itemID;
        this.itemName=name;
        this.inputType=Type;
        this.itemfeechannelId=feeId;
        this.fee=fee;
    }

    /**
     * @return the itemId
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the inputType
     */
    public Long getInputType() {
        return inputType;
    }

    /**
     * @param inputType the inputType to set
     */
    public void setInputType(Long inputType) {
        this.inputType = inputType;
    }

    /**
     * @return the itemfeechannelId
     */
    public Long getItemfeechannelId() {
        return itemfeechannelId;
    }

    /**
     * @param itemfeechannelId the itemfeechannelId to set
     */
    public void setItemfeechannelId(Long itemfeechannelId) {
        this.itemfeechannelId = itemfeechannelId;
    }

    /**
     * @return the fee
     */
    public Long getFee() {
        return fee;
    }

    /**
     * @param fee the fee to set
     */
    public void setFee(Long fee) {
        this.fee = fee;
    }

}
