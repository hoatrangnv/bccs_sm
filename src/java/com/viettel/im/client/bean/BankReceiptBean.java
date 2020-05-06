package com.viettel.im.client.bean;

import java.util.Date;

/**
 * StockTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BankReceiptBean implements java.io.Serializable {

    // Fields
    private Long shopId;
    private String shopName;
    private Long bankAccountId;
    private String bankName;
    private Long telecomServiceId;
    private String telServiceName;
  
    private Long bankReceiptId;
	private String receiver;
	private String receiverAddress;
	private Long amount;
	private String content;
	private Date bankDate;
	private Date approveDatetime;
	private String status;
	private String approver;
	private Date destroyDatetime;
	private String destroyer;
	private Long channelId;
	private String channelTypeId;
	private Long staffId;
	private Long receiptId;
	private Date createDatetime;

    private String shopCode;
    private String bankCode;
    private String accountNo;


    public BankReceiptBean() {
    }

    public BankReceiptBean(Long channelId, String shopName, Long bankAccountId, String bankName,
            Long telecomServiceId, String telServiceName, Long bankReceiptId, String receiver,
            String receiverAddress, Long amount, String content, Date bankDate, Date approveDatetime,
            String status, String approver, Date destroyDatetime, String destroyer, String shopCode,
            String channelTypeId, Long staffId, Long receiptId, Date createDatetime, Long shopId,
            String bankCode, String accountNo) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.bankAccountId = bankAccountId;
        this.bankName = bankName;
        this.telecomServiceId = telecomServiceId;
        this.telServiceName = telServiceName;
        this.bankReceiptId = bankReceiptId;
        this.receiver = receiver;
        this.receiverAddress = receiverAddress;
        this.amount = amount;
        this.content = content;
        this.bankDate = bankDate;
        this.approveDatetime = approveDatetime;
        this.status = status;
        this.approver = approver;
        this.destroyDatetime = destroyDatetime;
        this.destroyer = destroyer;
        this.channelId = channelId;
        this.channelTypeId = channelTypeId;
        this.staffId = staffId;
        this.receiptId = receiptId;
        this.createDatetime = createDatetime;

        this.shopCode = shopCode;
        this.bankCode = bankCode;
        this.accountNo = accountNo;
    }

    public Long getAmount() {
        return amount;
    }

    public Date getApproveDatetime() {
        return approveDatetime;
    }

    public String getApprover() {
        return approver;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public Date getBankDate() {
        return bankDate;
    }

    public String getBankName() {
        return bankName;
    }

    public Long getBankReceiptId() {
        return bankReceiptId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public String getChannelTypeId() {
        return channelTypeId;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public Date getDestroyDatetime() {
        return destroyDatetime;
    }

    public String getDestroyer() {
        return destroyer;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public Long getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public Long getStaffId() {
        return staffId;
    }

    public String getStatus() {
        return status;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public String getTelServiceName() {
        return telServiceName;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setApproveDatetime(Date approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public void setBankDate(Date bankDate) {
        this.bankDate = bankDate;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankReceiptId(Long bankReceiptId) {
        this.bankReceiptId = bankReceiptId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public void setDestroyDatetime(Date destroyDatetime) {
        this.destroyDatetime = destroyDatetime;
    }

    public void setDestroyer(String destroyer) {
        this.destroyer = destroyer;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public void setTelServiceName(String telecomServiceName) {
        this.telServiceName = telecomServiceName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    


}