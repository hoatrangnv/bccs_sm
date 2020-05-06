package com.viettel.im.client.form;

import java.util.Date;
import java.util.List;
import org.apache.struts.action.ActionForm;
import com.viettel.im.client.bean.InvoiceNoSaleItem;

/**
 *
 * @author Doan Thanh 8
 * date 12/03/2009
 *
 */
public class SaleForm extends ActionForm {

    private String billSerial;
    private String billNum;
    private Long stateId;
    private String startDate;
    private String endDate;
    private Long staffId;
    private String reasonId;
    private String custName;
    private String transId;
    private String custNameBill;
    private String company;
    private String address;
    private String tin;
    private String account;
    private String payMethod;
    private String note;
    private Long amount;
    private Long tax;
    private Long amountTax;
    private Long invoiceListId;
    private Long fromInvoice;
    private Long toInvoice;
    private Long currInvoiceNo;
    private List invoiceListDisplay;
    private Long finalAmount;
    private String transStatus;
    private String agentAccount;
    private String agentTin;

    private Long telecomServiceId;
    private String exportUrl;
    private String exprotUrlRac;

    private String strFromInvoice;
    private String strToInvoice;
    private String strCurrInvoice;
    private String InvoiceNo;


    

    public String getExprotUrlRac() {
        return exprotUrlRac;
    }

    public void setExprotUrlRac(String exprotUrlRac) {
        this.exprotUrlRac = exprotUrlRac;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String InvoiceNo) {
        this.InvoiceNo = InvoiceNo;
    }

    public String getStrCurrInvoice() {
        return strCurrInvoice;
    }

    public void setStrCurrInvoice(String strCurrInvoice) {
        this.strCurrInvoice = strCurrInvoice;
    }

    public String getStrFromInvoice() {
        return strFromInvoice;
    }

    public void setStrFromInvoice(String strFromInvoice) {
        this.strFromInvoice = strFromInvoice;
    }

    public String getStrToInvoice() {
        return strToInvoice;
    }

    public void setStrToInvoice(String strToInvoice) {
        this.strToInvoice = strToInvoice;
    }

    /** List of sale trans id which was choosen */
    private String[] saleTransId;


    /** List of invoice used id which was choosen */
    private String[] invoiceUsedId;


    /** Agent ID of sale trans invoice to be created */
    private Long agentId;


    /** Name of Staff shop who create invoice */
    private String shopName;


    /** Name of staff */
    private String staffName;


    /** Address of agent */
    private String agentAddress;

    /** Name of Agent */
    private String agentName;


    /** Direct create invoice type */
    private Long invoiceType;


    /** Discount */
    private String discount;


    /** Promotion */
    private String promotion;


    /** Vat */
    private String vat;


    /** Reason name */
    private String reasonName;


    private Long saleTransType;


    private Long salerId;


    private String channelTypeId;


    public SaleForm() {
    }

    public SaleForm(String shopName, String staffName, String billSerial, String billNum, Long stateId, String startDate,
            String endDate, Long staffId, String reasonId, String custName, String transId,
            String custNameBill, String company, String address, String tin, String account,
            String payMethod, String note, Long amount, Long tax, Long amountTax,
            Long invoiceListId, Long fromInvoice, Long toInvoice, Long currInvoiceNo, List invoiceListDisplay, Long finalAmount) {
        this.shopName = shopName;
        this.staffName = staffName;
        this.billSerial = billSerial;
        this.billNum = billNum;
        this.stateId = stateId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.staffId = staffId;
        this.reasonId = reasonId;
        this.custName = custName;
        this.transId = transId;
        this.custNameBill = custNameBill;
        this.company = company;
        this.address = address;
        this.tin = tin;
        this.account = account;
        this.payMethod = payMethod;
        this.note = note;
        this.amount = amount;
        this.tax = tax;
        this.amountTax = amountTax;
        this.invoiceListId = invoiceListId;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.currInvoiceNo = currInvoiceNo;
        this.invoiceListDisplay = invoiceListDisplay;
        this.finalAmount = finalAmount;

    }


    public void resetSaleForm() {
        this.custName = null;
        this.startDate = null;
        this.telecomServiceId = null;
        this.transId = null;
        this.payMethod = null;
        this.reasonId = null;
    }

    public String getAgentTin() {
        return agentTin;
    }

    public void setAgentTin(String agentTin) {
        this.agentTin = agentTin;
    }

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public Long getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(Long finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Long amountTax) {
        this.amountTax = amountTax;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public String getBillSerial() {
        return billSerial;
    }

    public void setBillSerial(String billSerial) {
        this.billSerial = billSerial;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getCurrInvoiceNo() {
        return currInvoiceNo;
    }

    public void setCurrInvoiceNo(Long currInvoiceNo) {
        this.currInvoiceNo = currInvoiceNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustNameBill() {
        return custNameBill;
    }

    public void setCustNameBill(String custNameBill) {
        this.custNameBill = custNameBill;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getFromInvoice() {
        return fromInvoice;
    }

    public void setFromInvoice(Long fromInvoice) {
        this.fromInvoice = fromInvoice;
    }

    public List getInvoiceListDisplay() {
        return invoiceListDisplay;
    }

    public void setInvoiceListDisplay(List invoiceListDisplay) {
        this.invoiceListDisplay = invoiceListDisplay;
    }

    public Long getInvoiceListId() {
        return invoiceListId;
    }

    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

 

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public Long getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(Long toInvoice) {
        this.toInvoice = toInvoice;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getReasonId() {
        return reasonId;
    }

    public String[] getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(String[] saleTransId) {
        this.saleTransId = saleTransId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Long getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Long invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String[] getInvoiceUsedId() {
        return invoiceUsedId;
    }

    public void setInvoiceUsedId(String[] invoiceUsedId) {
        this.invoiceUsedId = invoiceUsedId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public Long getSaleTransType() {
        return saleTransType;
    }

    public void setSaleTransType(Long saleTransType) {
        this.saleTransType = saleTransType;
    }

    public Long getSalerId() {
        return salerId;
    }

    public void setSalerId(Long salerId) {
        this.salerId = salerId;
    }

    public String getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    //MrSun
    private String itemId = "";
    private String itemCode = "";
    private String itemName = "";
    private String itemUnit = "";
    private String itemNote = "";
    
    private Long itemPrice = 0L;
    private Long itemQty = 0L;
    private Long itemAmount;//Amount = Price * Qty
    private Long itemVAT = 0L;
    private Long itemAmountVAT = 0L;
    private Long itemDiscount = 0L;
    private Long itemPromotion = 0L;
    private Long itemTotal = 0L;//Total = Amount * (1+VAT/100) - Discount - Promotion

    private Long invoiceAmount = 0L;//Amount = Price * Qty
    private Long invoiceVAT = 0L;
    private Long invoiceDiscount = 0L;
    private Long invoicePromotion = 0L;
    private Long invoiceTotal = 0L;//Total = Amount * (1+VAT/100) - Discount - Promotion

    private String itemVATString = "";
    private String itemPriceString = "";
    private String itemQtyString = "";
    private String itemDiscountString = "";
    private String itemPromotionString = "";

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Long getInvoiceDiscount() {
        return invoiceDiscount;
    }

    public void setInvoiceDiscount(Long invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    public Long getInvoicePromotion() {
        return invoicePromotion;
    }

    public void setInvoicePromotion(Long invoicePromotion) {
        this.invoicePromotion = invoicePromotion;
    }

    public Long getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(Long invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public Long getInvoiceVAT() {
        return invoiceVAT;
    }

    public void setInvoiceVAT(Long invoiceVAT) {
        this.invoiceVAT = invoiceVAT;
    }

    public Long getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Long itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Long getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(Long itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNote() {
        return itemNote;
    }

    public void setItemNote(String itemNote) {
        this.itemNote = itemNote;
    }

    public Long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Long getItemPromotion() {
        return itemPromotion;
    }

    public void setItemPromotion(Long itemPromotion) {
        this.itemPromotion = itemPromotion;
    }

    public Long getItemQty() {
        return itemQty;
    }

    public void setItemQty(Long itemQty) {
        this.itemQty = itemQty;
    }

    public Long getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Long itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Long getItemVAT() {
        return itemVAT;
    }

    public void setItemVAT(Long itemVAT) {
        this.itemVAT = itemVAT;
    }

    public Long getItemAmountVAT() {
        return itemAmountVAT;
    }

    public void setItemAmountVAT(Long itemAmountVAT) {
        this.itemAmountVAT = itemAmountVAT;
    }

    public String getItemDiscountString() {
        return itemDiscountString;
    }

    public void setItemDiscountString(String itemDiscountString) {
        this.itemDiscountString = itemDiscountString;
    }

    public String getItemPromotionString() {
        return itemPromotionString;
    }

    public void setItemPromotionString(String itemPromotionString) {
        this.itemPromotionString = itemPromotionString;
    }

    public String getItemVATString() {
        return itemVATString;
    }

    public void setItemVATString(String itemVATString) {
        this.itemVATString = itemVATString;
    }


    public String getItemPriceString() {
        return itemPriceString;
    }

    public void setItemPriceString(String itemPriceString) {
        this.itemPriceString = itemPriceString;
    }

    public String getItemQtyString() {
        return itemQtyString;
    }

    public void setItemQtyString(String itemQtyString) {
        this.itemQtyString = itemQtyString;
    }

    public boolean calcItemAmount(){
        boolean result = true;
        try{            
            if (!"".equals(itemPriceString))
                itemPrice = Long.parseLong(itemPriceString);
            if (!"".equals(itemQtyString))
                itemQty = Long.parseLong(itemQtyString);
            if (!"".equals(itemPromotionString))
                itemPromotion = Long.parseLong(itemPromotionString);
            if (!"".equals(itemDiscountString))
                itemDiscount = Long.parseLong(itemDiscountString);
            if (!"".equals(itemVATString))
                itemVAT = Long.parseLong(itemVATString);

            itemAmount = itemPrice * itemQty;
            itemAmountVAT = itemAmount * itemVAT/100;
            itemTotal = itemAmount + itemAmountVAT - itemPromotion - itemDiscount;            

        }catch(Exception ex){
            result = false;
        }
        return result;
    }

    //Copy info from BEAN to FORM
    public InvoiceNoSaleItem copyItemInfoB2F(){
        InvoiceNoSaleItem tmp = new InvoiceNoSaleItem();
        try{            
            tmp.setItemId(this.itemId);
            tmp.setItemCode(this.itemCode);
            tmp.setItemName(this.itemName);
            tmp.setItemUnit(this.itemUnit);
            tmp.setItemNote(this.itemNote);
            tmp.setItemPrice(this.itemPrice);
            tmp.setItemQty(this.itemQty);
            tmp.setItemAmount(this.itemAmount);
            tmp.setItemVAT(this.itemVAT);
            tmp.setItemAmountVAT(this.itemAmountVAT);
            tmp.setItemDiscount(this.itemDiscount);
            tmp.setItemPromotion(this.itemPromotion);
            tmp.setItemTotal(this.itemTotal);
        }catch(Exception ex){
            tmp = null;
        }
        return tmp;
    }

    //Copy info from FORM to BEAN
    public boolean copyItemInfoF2B(InvoiceNoSaleItem form){
        boolean result = true;
        try{
            this.itemId = form.getItemId();
            this.itemCode = form.getItemCode();
            this.itemName = form.getItemName();
            this.itemUnit = form.getItemUnit();
            this.itemNote = form.getItemNote();
            this.itemPrice = form.getItemPrice();
            this.itemQty = form.getItemQty();
            this.itemAmount = form.getItemAmount();
            this.itemVAT = form.getItemVAT();
            this.itemAmountVAT = form.getItemAmountVAT();
            this.itemDiscount = form.getItemDiscount();
            this.itemPromotion = form.getItemPromotion();
            this.itemTotal = form.getItemTotal();

            if (this.itemPrice != null && this.itemPrice>0L)
                this.itemPriceString = this.itemPrice.toString();
            if (this.itemQty != null && this.itemQty>0L)
                this.itemQtyString = this.itemQty.toString();
            if (this.itemVAT != null && this.itemVAT>0L)
                this.itemVATString = this.itemVAT.toString();
            if (this.itemDiscount != null && this.itemDiscount>0L)
                this.itemDiscountString = this.itemDiscount.toString();
            if (this.itemPromotion != null && this.itemPromotion>0L)
                this.itemPromotionString = this.itemPromotion.toString();
            
        }catch(Exception ex){
            result = false;
        }
        return result;
    }

    //Clear info of item in FORM
    public void clearItemInfo(){
        this.itemId = "";
        this.itemCode = "";
        this.itemName = "";
        this.itemUnit = "";
        this.itemNote = "";

        this.itemPrice = 0L;
        this.itemQty = 0L;
        this.itemAmount = 0L;
        this.itemVAT = 0L;
        this.itemAmountVAT = 0L;
        this.itemDiscount = 0L;
        this.itemPromotion = 0L;
        this.itemTotal = 0L;
        

        this.itemPriceString = "";
        this.itemQtyString = "";
        this.itemVATString = "";
        this.itemDiscountString = "";
        this.itemPromotionString = "";
        
    }
    //MrSun

    private String invoiceStatus;

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
    
}



