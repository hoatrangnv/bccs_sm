package com.viettel.im.client.form;

import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tamdt1
 * date 24/02/2009
 *
 */
public class StockCardForm extends ActionForm {
    //tuong ung cac truong trong DB
    private Long id;
    private Long goodsDefId;
    private Long goodsInstanceId;
    private Long resourceTypeId;
    private String serial;
    private String cardType;
    private String amountType;
    private Long shopId;
    private Long staffId;
    private Date createDate;
    private Date expiredDate;
    private Long status;
    private Long fromPrice;
    private Long toPrice;

    public StockCardForm() {
        id = 0L;
        goodsDefId = 0L;
        goodsInstanceId = 0L;
        resourceTypeId = 0L;
        serial = "";
        cardType = "";
        amountType = "";
        shopId = 0L;
        staffId = 0L;
        createDate = new Date();
        expiredDate = new Date();
        status = 0L;
        fromPrice = 0L;
        toPrice = 0L;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Long getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(Long fromPrice) {
        this.fromPrice = fromPrice;
    }

    public Long getGoodsDefId() {
        return goodsDefId;
    }

    public void setGoodsDefId(Long goodsDefId) {
        this.goodsDefId = goodsDefId;
    }

    public Long getGoodsInstanceId() {
        return goodsInstanceId;
    }

    public void setGoodsInstanceId(Long goodsInstanceId) {
        this.goodsInstanceId = goodsInstanceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Long resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getToPrice() {
        return toPrice;
    }

    public void setToPrice(Long toPrice) {
        this.toPrice = toPrice;
    }
}
