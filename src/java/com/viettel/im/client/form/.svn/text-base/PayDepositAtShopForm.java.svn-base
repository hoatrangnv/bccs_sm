/*
* Copyright YYYY Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/

package com.viettel.im.client.form;

import com.viettel.im.client.bean.DepositBean;
import com.viettel.im.database.BO.Deposit;
import org.apache.struts.action.ActionForm;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.ReceiptExpense;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author tuannv
 * date 18/02/2009
 *
 */
public class PayDepositAtShopForm extends ActionForm {

    private Long depositId;
    private String amount;
    private String receiptNo;
    private String type;
    private String depositTypeId;
    private String reasonId;
    private String shopId;
    private String receiptId;
    private String deliverId;
    private String subId;
    private String isdn;
    private String custName;
    private String address;
    private String tin;
    private String createDate;
    private String status;
    private String descripttion;
    private String createBy;
    private String telecomeServiceId;
    private String isdnB;
    private String depositTypeIdB;
    private String fromDateB;
    private String toDateB;
    private String custNameB;
    private String addressB;
    private String payDepositAtShopMessage;
    private String name;
    private String telServiceName;
    private String shopName;
    private String staffName;
    private String payMethod;
    private String errorType;
    private String[] listDepositId;
    private String staffId;
    private String custNameS;
    private String typeS;
    private String statusS;
    private String shopCodeSearch;
    private String shopNameSearch;
    private String staffNameSearch;
    private String staffCodeSearch;

    public String getShopCodeSearch() {
        return shopCodeSearch;
    }

    public void setShopCodeSearch(String shopCodeSearch) {
        this.shopCodeSearch = shopCodeSearch;
    }

    public String getShopNameSearch() {
        return shopNameSearch;
    }

    public void setShopNameSearch(String shopNameSearch) {
        this.shopNameSearch = shopNameSearch;
    }

    public String getStaffCodeSearch() {
        return staffCodeSearch;
    }

    public void setStaffCodeSearch(String staffCodeSearch) {
        this.staffCodeSearch = staffCodeSearch;
    }

    public String getStaffNameSearch() {
        return staffNameSearch;
    }

    public void setStaffNameSearch(String staffNameSearch) {
        this.staffNameSearch = staffNameSearch;
    }

    public String getStatusS() {
        return statusS;
    }

    public void setStatusS(String statusS) {
        this.statusS = statusS;
    }

    public String getCustNameS() {
        return custNameS;
    }

    public void setCustNameS(String custNameS) {
        this.custNameS = custNameS;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getTypeS() {
        return typeS;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public PayDepositAtShopForm() {
        resetForm();
    }

    public void resetForm() {
        this.depositId = 0L;
        this.amount = "";
        this.receiptNo = "";
        this.depositTypeIdB = "";
        this.reasonId = "";
        this.isdn = "";
        this.custName = "";
        this.address = "";
        this.createBy = "";
        this.telecomeServiceId = "";
        this.custNameB = "";
        this.addressB = "";
        this.shopName = "";
        this.payMethod = "";
        this.listDepositId = null;
        this.payDepositAtShopMessage = "";
        this.toDateB = "";
        this.fromDateB = "";
    }

    public void resetHalfForm() {
        this.depositId = null;
        this.amount = "";
        this.receiptNo = "";
        this.reasonId = "";
        this.isdn = "";
        this.custName = "";
        this.address = "";
        this.createBy = "";
        this.telecomeServiceId = "";
        this.custNameB = "";
        this.addressB = "";
        this.shopName = "";
        this.payMethod = "";
        this.listDepositId = null;
        this.payDepositAtShopMessage = "";
    }

    public void copyDataFromBean(DepositBean depositBean) {
        try {
            this.depositId = depositBean.getDepositId();
            this.amount = String.valueOf(depositBean.getAmount());
            this.receiptId = String.valueOf(depositBean.getReceiptId());
            this.type = depositBean.getType();
            this.depositTypeId = String.valueOf(depositBean.getDepositTypeId());
            this.reasonId = String.valueOf(depositBean.getReasonId());
            this.shopId = String.valueOf(depositBean.getShopId());
            this.deliverId = String.valueOf(depositBean.getDeliverId());
            this.subId = String.valueOf(depositBean.getSubId());
            this.isdn = depositBean.getIsdn();
            this.custName = depositBean.getCustName();
            this.custNameB = depositBean.getCustNameB();
            this.addressB = depositBean.getAddressB();
            this.address = depositBean.getAddress();
            this.tin = depositBean.getTin();
            this.createDate = DateTimeUtils.convertDateToString(depositBean.getCreateDate());
            this.status = depositBean.getStatus();
            this.descripttion = depositBean.getDescription();
            this.createBy = depositBean.getCreateBy();
            this.telecomeServiceId = String.valueOf(depositBean.getTelecomServiceId());
            this.name = depositBean.getName();
            this.telServiceName = depositBean.getTelServiceName();
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void copyDataFromBO(Deposit deposit) {

        try {
            this.depositId = deposit.getDepositId();
            this.amount = String.valueOf(deposit.getAmount());
            this.receiptId = String.valueOf(deposit.getReceiptId());
            this.type = deposit.getType();
            this.depositTypeId = String.valueOf(deposit.getDepositTypeId());
            this.reasonId = String.valueOf(deposit.getReasonId());
            this.shopId = String.valueOf(deposit.getShopId());
            this.deliverId = String.valueOf(deposit.getDeliverId());
            this.subId = String.valueOf(deposit.getSubId());
            this.isdn = deposit.getIsdn();
            this.custName = deposit.getCustName();
            this.address = deposit.getAddress();
            this.tin = deposit.getTin();
            this.createDate = DateTimeUtils.convertDateToString(deposit.getCreateDate());
            this.status = deposit.getStatus();
            this.descripttion = deposit.getDescription();
            this.createBy = deposit.getCreateBy();
            this.telecomeServiceId = String.valueOf(deposit.getTelecomServiceId());
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void copyDataToBO(Deposit deposit) {
        try {
            deposit.setDepositId(this.getDepositId());
            deposit.setAmount(Double.valueOf(this.getAmount()));
            deposit.setReceiptId(Long.parseLong(this.getReceiptId()));
            deposit.setType(this.getType());
            deposit.setDepositTypeId(Long.parseLong(this.getDepositTypeId()));
            deposit.setReasonId(Long.parseLong(this.getReasonId()));
            deposit.setShopId(Long.parseLong(this.getShopId()));
            deposit.setDeliverId(Long.parseLong(this.getDeliverId()));
            deposit.setSubId(Long.parseLong(this.getSubId()));
            deposit.setIsdn(this.getIsdn());
            deposit.setCustName(this.getCustName());
            deposit.setAddress(this.getAddress());
            deposit.setTin(this.getTin());
            deposit.setStatus(this.getStatus());
            deposit.setDescription(this.getDescripttion());
            deposit.setCreateBy(this.getCreateBy());
            deposit.setTelecomServiceId(Long.parseLong(this.getTelecomeServiceId()));
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void copyDataToReceiptExpenseBO(ReceiptExpense receiptExpense) {
        try {
            receiptExpense.setAmount(Double.valueOf(this.getAmount()));
            receiptExpense.setReasonId(Long.parseLong(this.getReasonId()));
            receiptExpense.setDeliverer(this.getCustName());
            receiptExpense.setAddress(this.getAddress());
            receiptExpense.setReceiptNo(this.getReceiptNo());
            receiptExpense.setAddress(this.getAddress());
            receiptExpense.setPayMethod(this.getPayMethod());
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressB() {
        return addressB;
    }

    public void setAddressB(String addressB) {
        this.addressB = addressB;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustNameB() {
        return custNameB;
    }

    public void setCustNameB(String custNameB) {
        this.custNameB = custNameB;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public Long getDepositId() {
        return depositId;
    }

    public void setDepositId(Long depositId) {
        this.depositId = depositId;
    }

    public String getDepositTypeId() {
        return depositTypeId;
    }

    public void setDepositTypeId(String depositTypeId) {
        this.depositTypeId = depositTypeId;
    }

    public String getDepositTypeIdB() {
        return depositTypeIdB;
    }

    public void setDepositTypeIdB(String depositTypeIdB) {
        this.depositTypeIdB = depositTypeIdB;
    }

    public String getDescripttion() {
        return descripttion;
    }

    public void setDescripttion(String descripttion) {
        this.descripttion = descripttion;
    }

    public String getFromDateB() {
        return fromDateB;
    }

    public void setFromDateB(String fromDateB) {
        this.fromDateB = fromDateB;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getIsdnB() {
        return isdnB;
    }

    public void setIsdnB(String isdnB) {
        this.isdnB = isdnB;
    }

    public String[] getListDepositId() {
        return listDepositId;
    }

    public void setListDepositId(String[] listDepositId) {
        this.listDepositId = listDepositId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayDepositAtShopMessage() {
        return payDepositAtShopMessage;
    }

    public void setPayDepositAtShopMessage(String payDepositAtShopMessage) {
        this.payDepositAtShopMessage = payDepositAtShopMessage;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getTelServiceName() {
        return telServiceName;
    }

    public void setTelServiceName(String telServiceName) {
        this.telServiceName = telServiceName;
    }

    public String getTelecomeServiceId() {
        return telecomeServiceId;
    }

    public void setTelecomeServiceId(String telecomeServiceId) {
        this.telecomeServiceId = telecomeServiceId;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getToDateB() {
        return toDateB;
    }

    public void setToDateB(String toDateB) {
        this.toDateB = toDateB;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
}
