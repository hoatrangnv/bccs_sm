package com.viettel.im.database.BO;

import com.viettel.database.BO.BasicBO;
import java.util.Date;

/**
 * CommTransaction entity. @author MyEclipse Persistence Tools
 */
public class CommTransaction extends BasicBO {

    // Fields
    private Long commTransId;
    private Long invoiceUsedId;
    private Long shopId;
    private Long staffId;
    private Long channelTypeId;
    private Long itemId;
    private Date billCycle;
    private Date createDate;
    private Long feeId;
    private Long fee;
    private Long totalMoney;
    private Long taxMoney;
    private Long quantity;
    private Long quantityOntime;
    private Long quantityExpired1;
    private Long quantityExpired2;
    private Long quantityExpired3;
    private Long quantityUnpay;
    private Long receiptId;
    private Long status;
    private Long validStatus;
    private Long payerId;
    private Long payStatus;
    private Date payDate;
    private Long approverId;
    private Long approved;
    private Date approvedDate;
    private Boolean telecomServiceId;
    private Date itemDate;
    //Bo sung thong tin nguoi tong hop
    private Long collecterId;
    private Long collectType;
    //
    private String itemName;
    private String inputType;

    public CommTransaction(Long commTransId, Long shopId, Long staffId, Long channelTypeId, Long itemId, Date billCycle, Date createDate, Long feeId, Long totalMoney, Long taxMoney, Long quantity, Long receiptId, Long status, Long payStatus, Date payDate, Long approved, Date approvedDate, Date itemDate, String itemName, String inputType) {
        this.commTransId = commTransId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.channelTypeId = channelTypeId;
        this.itemId = itemId;
        this.billCycle = billCycle;
        this.createDate = createDate;
        this.feeId = feeId;
        this.totalMoney = totalMoney;
        this.taxMoney = taxMoney;
        this.quantity = quantity;
        this.receiptId = receiptId;
        this.status = status;
        this.payStatus = payStatus;
        this.payDate = payDate;
        this.approved = approved;
        this.approvedDate = approvedDate;
        this.itemDate = itemDate;
        this.itemName = itemName;
        this.inputType = inputType;     
    }
    public CommTransaction(Long commTransId, Long shopId, Long staffId, Long channelTypeId, Long itemId, Date billCycle, Date createDate, Long feeId, Long totalMoney, Long taxMoney, Long quantity, Long receiptId, Long status, Long payStatus, Date payDate, Long approved, Date approvedDate, Date itemDate, String itemName, String inputType, Long validStatus) {
        this.commTransId = commTransId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.channelTypeId = channelTypeId;
        this.itemId = itemId;
        this.billCycle = billCycle;
        this.createDate = createDate;
        this.feeId = feeId;
        this.totalMoney = totalMoney;
        this.taxMoney = taxMoney;
        this.quantity = quantity;
        this.receiptId = receiptId;
        this.status = status;
        this.payStatus = payStatus;
        this.payDate = payDate;
        this.approved = approved;
        this.approvedDate = approvedDate;
        this.itemDate = itemDate;
        this.itemName = itemName;
        this.inputType = inputType;
        this.validStatus = validStatus;
    }

    public CommTransaction(Long commTransId, Long shopId, Long staffId, Long channelTypeId, Long itemId, Date billCycle, Date createDate, Long feeId, Long fee, Long totalMoney, Long taxMoney, Long quantity, Long receiptId, Long status, Long payStatus, Date payDate, Long approved, Date approvedDate, Date itemDate, String itemName, String inputType,Long validStatus) {
        this.commTransId = commTransId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.channelTypeId = channelTypeId;
        this.itemId = itemId;
        this.billCycle = billCycle;
        this.createDate = createDate;
        this.feeId = feeId;
        this.fee = fee;
        this.totalMoney = totalMoney;
        this.taxMoney = taxMoney;
        this.quantity = quantity;
        this.receiptId = receiptId;
        this.status = status;
        this.payStatus = payStatus;
        this.payDate = payDate;
        this.approved = approved;
        this.approvedDate = approvedDate;
        this.itemDate = itemDate;
        this.itemName = itemName;
        this.inputType = inputType;
        this.validStatus = validStatus;
    }
        public CommTransaction(Long commTransId, Long shopId, Long staffId, Long channelTypeId, Long itemId, Date billCycle, Date createDate, Long feeId, Long fee, Long totalMoney, Long taxMoney, Long quantity, Long receiptId, Long status, Long payStatus, Date payDate, Long approved, Date approvedDate, Date itemDate, String itemName, String inputType) {
        this.commTransId = commTransId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.channelTypeId = channelTypeId;
        this.itemId = itemId;
        this.billCycle = billCycle;
        this.createDate = createDate;
        this.feeId = feeId;
        this.fee = fee;
        this.totalMoney = totalMoney;
        this.taxMoney = taxMoney;
        this.quantity = quantity;
        this.receiptId = receiptId;
        this.status = status;
        this.payStatus = payStatus;
        this.payDate = payDate;
        this.approved = approved;
        this.approvedDate = approvedDate;
        this.itemDate = itemDate;
        this.itemName = itemName;
        this.inputType = inputType;
    }


    // Constructors
    /** default constructor */
    public CommTransaction() {
    }

    /** tuannv constructor phuc vu bao cao phi hoa hong*/
    public CommTransaction(Long quantity, Long totalMoney) {
        this.quantity = quantity;
        this.totalMoney = totalMoney;
    }

    /** tuannv constructor phuc vu bao cao phi hoa hong*/
    public CommTransaction(Long quantity, Long quantityOntime, Long fee, Long totalMoney) {
        this.quantity = quantity;
        this.quantityOntime = quantityOntime;
        this.fee = fee;
        this.totalMoney = totalMoney;
    }

    /** minimal constructor */
    public CommTransaction(Long commTransId, Long shopId, Long itemId,
            Date billCycle) {
        this.commTransId = commTransId;
        this.shopId = shopId;
        this.itemId = itemId;
        this.billCycle = billCycle;
    }

    /** full constructor */
    public CommTransaction(Long commTransId, Long invoiceUsedId, Long shopId,
            Long staffId, Long channelTypeId, Long itemId, Date billCycle,
            Date createDate, Long feeId, Long totalMoney, Long taxMoney,
            Long quantity, Long quantityOntime, Long quantityExpired1,
            Long quantityExpired2, Long quantityUnpay, Long receiptId,
            Long status, Long payStatus, Date payDate, Long approved,
            Date approvedDate, Boolean telecomServiceId) {
        this.commTransId = commTransId;
        this.invoiceUsedId = invoiceUsedId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.channelTypeId = channelTypeId;
        this.itemId = itemId;
        this.billCycle = billCycle;
        this.createDate = createDate;
        this.feeId = feeId;
        this.totalMoney = totalMoney;
        this.taxMoney = taxMoney;
        this.quantity = quantity;
        this.quantityOntime = quantityOntime;
        this.quantityExpired1 = quantityExpired1;
        this.quantityExpired2 = quantityExpired2;
        this.quantityUnpay = quantityUnpay;
        this.receiptId = receiptId;
        this.status = status;
        this.payStatus = payStatus;
        this.payDate = payDate;
        this.approved = approved;
        this.approvedDate = approvedDate;
        this.telecomServiceId = telecomServiceId;
    }

    public CommTransaction(Long commTransId, Long invoiceUsedId, Long shopId,
            Long staffId, Long channelTypeId, Long itemId, Date billCycle,
            Date createDate, Long feeId, Long totalMoney, Long taxMoney,
            Long quantity, Long quantityOntime, Long quantityExpired1,
            Long quantityExpired2, Long quantityUnpay, Long receiptId,
            Long status, Long payStatus, Date payDate, Long approved,
            Date approvedDate, Boolean telecomServiceId, Long collecterId, Long collectType) {
        this.commTransId = commTransId;
        this.invoiceUsedId = invoiceUsedId;
        this.shopId = shopId;
        this.staffId = staffId;
        this.channelTypeId = channelTypeId;
        this.itemId = itemId;
        this.billCycle = billCycle;
        this.createDate = createDate;
        this.feeId = feeId;
        this.totalMoney = totalMoney;
        this.taxMoney = taxMoney;
        this.quantity = quantity;
        this.quantityOntime = quantityOntime;
        this.quantityExpired1 = quantityExpired1;
        this.quantityExpired2 = quantityExpired2;
        this.quantityUnpay = quantityUnpay;
        this.receiptId = receiptId;
        this.status = status;
        this.payStatus = payStatus;
        this.payDate = payDate;
        this.approved = approved;
        this.approvedDate = approvedDate;
        this.telecomServiceId = telecomServiceId;

        this.collecterId = collecterId;
        this.collectType = collectType;
    }

    // Property accessors
    public Long getCollectType() {
        return collectType;
    }

    public void setCollectType(Long collectType) {
        this.collectType = collectType;
    }

    public Long getCommTransId() {
        return this.commTransId;
    }

    public void setCommTransId(Long commTransId) {
        this.commTransId = commTransId;
    }

    public Long getInvoiceUsedId() {
        return this.invoiceUsedId;
    }

    public void setInvoiceUsedId(Long invoiceUsedId) {
        this.invoiceUsedId = invoiceUsedId;
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getStaffId() {
        return this.staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getChannelTypeId() {
        return this.channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Date getBillCycle() {
        return this.billCycle;
    }

    public void setBillCycle(Date billCycle) {
        this.billCycle = billCycle;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getFeeId() {
        return this.feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public Long getTotalMoney() {
        return this.totalMoney;
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getTaxMoney() {
        return this.taxMoney;
    }

    public void setTaxMoney(Long taxMoney) {
        this.taxMoney = taxMoney;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityOntime() {
        return this.quantityOntime;
    }

    public void setQuantityOntime(Long quantityOntime) {
        this.quantityOntime = quantityOntime;
    }

    public Long getQuantityExpired1() {
        return this.quantityExpired1;
    }

    public void setQuantityExpired1(Long quantityExpired1) {
        this.quantityExpired1 = quantityExpired1;
    }

    public Long getQuantityExpired2() {
        return this.quantityExpired2;
    }

    public void setQuantityExpired2(Long quantityExpired2) {
        this.quantityExpired2 = quantityExpired2;
    }

    public Long getQuantityUnpay() {
        return this.quantityUnpay;
    }

    public Long getQuantityExpired3() {
        return quantityExpired3;
    }

    public void setQuantityExpired3(Long quantityExpired3) {
        this.quantityExpired3 = quantityExpired3;
    }

    public void setQuantityUnpay(Long quantityUnpay) {
        this.quantityUnpay = quantityUnpay;
    }

    public Long getReceiptId() {
        return this.receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Date getPayDate() {
        return this.payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Long getApproved() {
        return this.approved;
    }

    public void setApproved(Long approved) {
        this.approved = approved;
    }

    public Date getApprovedDate() {
        return this.approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Boolean getTelecomServiceId() {
        return this.telecomServiceId;
    }

    public void setTelecomServiceId(Boolean telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    /**
     * @return the status
     */
    public Long getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Long validStatus) {
        this.validStatus = validStatus;
    }

    /**
     * @return the payStatus
     */
    public Long getPayStatus() {
        return payStatus;
    }

    /**
     * @param payStatus the payStatus to set
     */
    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * @return the approverId
     */
    public Long getApproverId() {
        return approverId;
    }

    /**
     * @param approverId the approverId to set
     */
    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public Date getItemDate() {
        return itemDate;
    }

    public void setItemDate(Date itemDate) {
        this.itemDate = itemDate;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }

    public Long getCollecterId() {
        return collecterId;
    }

    public void setCollecterId(Long collecterId) {
        this.collecterId = collecterId;
    }
}
