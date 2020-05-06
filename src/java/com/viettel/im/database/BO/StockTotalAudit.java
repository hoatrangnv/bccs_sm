/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.Date;

/**
 *
 * @author LamNV5_S
 */
public class StockTotalAudit implements java.io.Serializable  {

    private Long ownerId;
    private Long ownerType;
    private String ownerCode;
    private String ownerName;
    private Long userId;
    private String userCode;
    private String userName;
    private Long stateId;
    private Long stockModelId;
    private String stockModelName;
    private Date createDate;
    private Long status;
    private Long qty;
    private Long qtyIssue;
    private Long qtyHang;
    private Long qtyBf;
    private Long qtyIssueBf;
    private Long qtyHangBf;
    private Long qtyAf;
    private Long qtyIssueAf;
    private Long qtyHangAf;
    private Long transId;
    private String transCode;
    private Long transType;
    private Long sourceType;
    private String stickCode;
    private String reasonName;
    private Long reasonId;
    private String description;
    private Long shopIdLv1;
    private Long shopIdLv2;
    private Long shopIdLv3;
    private Long shopIdLv4;
    private Long shopIdLv5;
    private String shopCodeLv1;
    private String shopCodeLv2;
    private String shopCodeLv3;
    private String shopCodeLv4;
    private String shopCodeLv5;
    private String shopNameLv1;
    private String shopNameLv2;
    private String shopNameLv3;
    private String shopNameLv4;
    private String shopNameLv5;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getQtyAf() {
        return qtyAf;
    }

    public void setQtyAf(Long qtyAf) {
        this.qtyAf = qtyAf;
    }

    public Long getQtyBf() {
        return qtyBf;
    }

    public void setQtyBf(Long qtyBf) {
        this.qtyBf = qtyBf;
    }

    public Long getQtyHang() {
        return qtyHang;
    }

    public void setQtyHang(Long qtyHang) {
        this.qtyHang = qtyHang;
    }

    public Long getQtyHangAf() {
        return qtyHangAf;
    }

    public void setQtyHangAf(Long qtyHangAf) {
        this.qtyHangAf = qtyHangAf;
    }

    public Long getQtyHangBf() {
        return qtyHangBf;
    }

    public void setQtyHangBf(Long qtyHangBf) {
        this.qtyHangBf = qtyHangBf;
    }

    public Long getQtyIssue() {
        return qtyIssue;
    }

    public void setQtyIssue(Long qtyIssue) {
        this.qtyIssue = qtyIssue;
    }

    public Long getQtyIssueAf() {
        return qtyIssueAf;
    }

    public void setQtyIssueAf(Long qtyIssueAf) {
        this.qtyIssueAf = qtyIssueAf;
    }

    public Long getQtyIssueBf() {
        return qtyIssueBf;
    }

    public void setQtyIssueBf(Long qtyIssueBf) {
        this.qtyIssueBf = qtyIssueBf;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getShopCodeLv1() {
        return shopCodeLv1;
    }

    public void setShopCodeLv1(String shopCodeLv1) {
        this.shopCodeLv1 = shopCodeLv1;
    }

    public String getShopCodeLv2() {
        return shopCodeLv2;
    }

    public void setShopCodeLv2(String shopCodeLv2) {
        this.shopCodeLv2 = shopCodeLv2;
    }

    public String getShopCodeLv3() {
        return shopCodeLv3;
    }

    public void setShopCodeLv3(String shopCodeLv3) {
        this.shopCodeLv3 = shopCodeLv3;
    }

    public String getShopCodeLv4() {
        return shopCodeLv4;
    }

    public void setShopCodeLv4(String shopCodeLv4) {
        this.shopCodeLv4 = shopCodeLv4;
    }

    public String getShopCodeLv5() {
        return shopCodeLv5;
    }

    public void setShopCodeLv5(String shopCodeLv5) {
        this.shopCodeLv5 = shopCodeLv5;
    }

    public Long getShopIdLv1() {
        return shopIdLv1;
    }

    public void setShopIdLv1(Long shopIdLv1) {
        this.shopIdLv1 = shopIdLv1;
    }

    public Long getShopIdLv2() {
        return shopIdLv2;
    }

    public void setShopIdLv2(Long shopIdLv2) {
        this.shopIdLv2 = shopIdLv2;
    }

    public Long getShopIdLv3() {
        return shopIdLv3;
    }

    public void setShopIdLv3(Long shopIdLv3) {
        this.shopIdLv3 = shopIdLv3;
    }

    public Long getShopIdLv4() {
        return shopIdLv4;
    }

    public void setShopIdLv4(Long shopIdLv4) {
        this.shopIdLv4 = shopIdLv4;
    }

    public Long getShopIdLv5() {
        return shopIdLv5;
    }

    public void setShopIdLv5(Long shopIdLv5) {
        this.shopIdLv5 = shopIdLv5;
    }

    public String getShopNameLv1() {
        return shopNameLv1;
    }

    public void setShopNameLv1(String shopNameLv1) {
        this.shopNameLv1 = shopNameLv1;
    }

    public String getShopNameLv2() {
        return shopNameLv2;
    }

    public void setShopNameLv2(String shopNameLv2) {
        this.shopNameLv2 = shopNameLv2;
    }

    public String getShopNameLv3() {
        return shopNameLv3;
    }

    public void setShopNameLv3(String shopNameLv3) {
        this.shopNameLv3 = shopNameLv3;
    }

    public String getShopNameLv4() {
        return shopNameLv4;
    }

    public void setShopNameLv4(String shopNameLv4) {
        this.shopNameLv4 = shopNameLv4;
    }

    public String getShopNameLv5() {
        return shopNameLv5;
    }

    public void setShopNameLv5(String shopNameLv5) {
        this.shopNameLv5 = shopNameLv5;
    }

    public Long getSourceType() {
        return sourceType;
    }

    public void setSourceType(Long sourceType) {
        this.sourceType = sourceType;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStickCode() {
        return stickCode;
    }

    public void setStickCode(String stickCode) {
        this.stickCode = stickCode;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public String getStockModelName() {
        return stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public Long getTransId() {
        return transId;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getTransType() {
        return transType;
    }

    public void setTransType(Long transType) {
        this.transType = transType;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
