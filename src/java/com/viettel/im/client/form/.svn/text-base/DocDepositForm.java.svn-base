package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.BankReceipt;
import com.viettel.im.client.bean.BankReceiptBean;
import com.viettel.im.common.Constant;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author DucDD
 * String 18/04/2009
 *
 */
public class DocDepositForm extends ActionForm {

    private Long bankReceiptId;
    private String receiver;
    private String receiverAddress;
    private String amount;
    private String content;
    private String bankAccountId;
    private String bankDate;
    private String status;
    private String channelId;
    private String channelTypeId;
    private String shopId;
    private String staffId;
    private String receiptId;
    private String createDatetime;
    private String telecomServiceId;
    private String shopName;
    private String bankName;
    private String shopCode;
    private String shopCodeKey;
    private String accountNo;
    private String accountNoKey;
    private String telServiceName;
    private String submitType;
    private String submitTypeB;
    private String channelIdB;
    private String shopCodeB;
    private String shopCodeBKey;
    private String shopNameB;
    private String accountNoB;
    private String accountNoBKey;
    private String bankNameB;
    private String telecomServiceIdB;
    private String fromDateB;
    private String statusB;
    private String toDateB;
    private String bankAccountIdB;
    private String bankReceiptMessage;

    public DocDepositForm() {
        resetForm();
    }

    public void copyDataFromBean(BankReceiptBean bankReceiptBean) {
        try {
            this.bankReceiptId = bankReceiptBean.getBankReceiptId();
            this.shopId = String.valueOf(bankReceiptBean.getShopId());
            this.shopName = String.valueOf(bankReceiptBean.getShopName());
            this.shopCode = bankReceiptBean.getShopCode();
            this.bankAccountId = String.valueOf(bankReceiptBean.getBankAccountId());
            this.accountNo = bankReceiptBean.getAccountNo();
            this.accountNoKey = String.valueOf(bankReceiptBean.getBankAccountId());
            this.bankName = String.valueOf(bankReceiptBean.getBankName());
            if (bankReceiptBean.getTelecomServiceId() != null) {
                this.telecomServiceId = String.valueOf(bankReceiptBean.getTelecomServiceId());
            }
            this.telServiceName = bankReceiptBean.getTelServiceName();
            this.receiver = bankReceiptBean.getReceiver();
            this.receiverAddress = bankReceiptBean.getReceiverAddress();
            this.amount = String.valueOf(bankReceiptBean.getAmount());
            this.content = bankReceiptBean.getContent();
            this.bankDate = DateTimeUtils.convertDateToString(bankReceiptBean.getBankDate());
            this.status = String.valueOf(bankReceiptBean.getStatus());
            this.shopCodeKey = String.valueOf(bankReceiptBean.getChannelId());
            this.channelTypeId = String.valueOf(bankReceiptBean.getChannelTypeId());
            this.staffId = String.valueOf(bankReceiptBean.getStaffId());
            this.receiptId = String.valueOf(bankReceiptBean.getReceiptId());
            this.createDatetime = DateTimeUtils.convertDateToString(bankReceiptBean.getCreateDatetime());
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void copyDataFromBO(BankReceipt bankReceipt) {

        try {
            this.bankReceiptId = bankReceipt.getBankReceiptId();
            this.receiver = bankReceipt.getReceiver();
            this.receiverAddress = bankReceipt.getReceiverAddress();
            this.amount = String.valueOf(bankReceipt.getAmount());
            this.content = bankReceipt.getContent();
            this.bankAccountId = String.valueOf(bankReceipt.getBankAccountId());
            this.status = String.valueOf(bankReceipt.getStatus());
            this.channelId = String.valueOf(bankReceipt.getChannelId());
            this.channelTypeId = String.valueOf(bankReceipt.getChannelTypeId());
            this.shopId = String.valueOf(bankReceipt.getShopId());
            this.staffId = String.valueOf(bankReceipt.getStaffId());
            this.receiptId = String.valueOf(bankReceipt.getReceiptId());
            this.telecomServiceId = String.valueOf(bankReceipt.getTelecomServiceId());

            this.bankDate = DateTimeUtils.convertDateToString(bankReceipt.getBankDate());
            this.createDatetime = DateTimeUtils.convertDateToString(bankReceipt.getCreateDatetime());
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void copyDataToBO(BankReceipt bankReceipt) {
        try {

            bankReceipt.setBankReceiptId(this.getBankReceiptId());
            bankReceipt.setReceiver(this.getReceiver());
            bankReceipt.setReceiverAddress(this.getReceiverAddress());
            bankReceipt.setAmount(Long.parseLong(this.getAmount()));
            bankReceipt.setChannelId(Long.parseLong(this.getShopCodeKey()));
            bankReceipt.setBankDate(DateTimeUtils.convertStringToDate(this.getBankDate()));
            bankReceipt.setBankAccountId(Long.parseLong(this.getAccountNoKey()));
            if (this.getTelecomServiceId() != null && !this.getTelecomServiceId().equals("")) {
                bankReceipt.setTelecomServiceId(Long.parseLong(this.getTelecomServiceId()));
            }
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void softResetForm() {
        this.bankReceiptId = 0L;
        this.receiver = "";
        this.receiverAddress = "";
        this.amount = "";
        this.content = "";
        this.bankAccountId = "";
        this.bankDate = "";
        this.status = "";
        this.channelId = "";
        this.channelTypeId = "";
        this.shopId = "";
        this.staffId = "";
        this.receiptId = "";
        this.createDatetime = "";
        this.telecomServiceId = "";
        this.shopName = "";
        this.bankName = "";
        this.shopCode = "";
        this.shopCodeKey = "";
        this.accountNo = "";
        this.accountNoKey = "";
        this.submitType = "";
        this.bankReceiptMessage = "";
    }

    public void resetForm() {
        this.bankReceiptId = 0L;
        this.receiver = Constant.VIETTEL_TELECOM_NAME;
        this.receiverAddress = Constant.VIETTEL_TELECOM_ADDRESS;
        this.amount = "";
        this.content = "";
        this.bankAccountId = "";
        this.bankDate = "";
        this.status = "";
        this.channelId = "";
        this.channelTypeId = "";
        this.shopId = "";
        this.staffId = "";
        this.receiptId = "";
        this.createDatetime = "";
        this.telecomServiceId = "";
        this.shopName = "";
        this.bankName = "";
        this.shopCode = "";
        this.shopCodeKey = "";
        this.accountNo = "";
        this.accountNoKey = "";
        this.submitType = "";
        this.channelIdB = "";
        this.shopCodeB = "";
        this.shopCodeBKey = "";
        this.shopNameB = "";
        this.accountNoB = "";
        this.accountNoBKey = "";
        this.bankNameB = "";
        this.telecomServiceIdB = "";
        //this.fromDateB = "";
        this.statusB = "";
        //this.toDateB = "";
        this.bankAccountIdB = "";
        this.bankReceiptMessage = "";
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public String getSubmitTypeB() {
        return submitTypeB;
    }

    public void setSubmitTypeB(String submitTypeB) {
        this.submitTypeB = submitTypeB;
    }

    public String getShopCodeKey() {
        return shopCodeKey;
    }

    public void setShopCodeKey(String shopCodeKey) {
        this.shopCodeKey = shopCodeKey;
    }

    public String getAccountNoKey() {
        return accountNoKey;
    }

    public void setAccountNoKey(String accountNoKey) {
        this.accountNoKey = accountNoKey;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNoB() {
        return accountNoB;
    }

    public void setAccountNoB(String accountNoB) {
        this.accountNoB = accountNoB;
    }

    public String getAccountNoBKey() {
        return accountNoBKey;
    }

    public void setAccountNoBKey(String accountNoBKey) {
        this.accountNoBKey = accountNoBKey;
    }

    public String getAmount() {
        if(amount != null && !amount.trim().equals("")){
             amount = amount.replace(",", "");
             amount = amount.replace(".", "");
             amount = amount.trim();
        }
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getBankAccountIdB() {
        return bankAccountIdB;
    }

    public void setBankAccountIdB(String bankAccountIdB) {
        this.bankAccountIdB = bankAccountIdB;
    }

    public String getBankDate() {
        return bankDate;
    }

    public void setBankDate(String bankDate) {
        this.bankDate = bankDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNameB() {
        return bankNameB;
    }

    public void setBankNameB(String bankNameB) {
        this.bankNameB = bankNameB;
    }

    public Long getBankReceiptId() {
        return bankReceiptId;
    }

    public void setBankReceiptId(Long bankReceiptId) {
        this.bankReceiptId = bankReceiptId;
    }

    public String getBankReceiptMessage() {
        return bankReceiptMessage;
    }

    public void setBankReceiptMessage(String bankReceiptMessage) {
        this.bankReceiptMessage = bankReceiptMessage;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelIdB() {
        return channelIdB;
    }

    public void setChannelIdB(String channelIdB) {
        this.channelIdB = channelIdB;
    }

    public String getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getFromDateB() {
        return fromDateB;
    }

    public void setFromDateB(String fromDateB) {
        this.fromDateB = fromDateB;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopCodeB() {
        return shopCodeB;
    }

    public void setShopCodeB(String shopCodeB) {
        this.shopCodeB = shopCodeB;
    }

    public String getShopCodeBKey() {
        return shopCodeBKey;
    }

    public void setShopCodeBKey(String shopCodeBKey) {
        this.shopCodeBKey = shopCodeBKey;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopNameB() {
        return shopNameB;
    }

    public void setShopNameB(String shopNameB) {
        this.shopNameB = shopNameB;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusB() {
        return statusB;
    }

    public void setStatusB(String statusB) {
        this.statusB = statusB;
    }

    public String getTelServiceName() {
        return telServiceName;
    }

    public void setTelServiceName(String telServiceName) {
        this.telServiceName = telServiceName;
    }

    public String getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(String telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getTelecomServiceIdB() {
        return telecomServiceIdB;
    }

    public void setTelecomServiceIdB(String telecomServiceIdB) {
        this.telecomServiceIdB = telecomServiceIdB;
    }

    public String getToDateB() {
        return toDateB;
    }

    public void setToDateB(String toDateB) {
        this.toDateB = toDateB;
    }
}