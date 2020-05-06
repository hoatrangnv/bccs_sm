/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.client.bean;

/**
 *
 * @author MrSun
 */
public class InvoiceNoSaleItem {

    //MrSun
    private String itemId = "";
    private String itemCode = "";
    private String itemName = "";
    private String itemUnit = "";
    private String itemNote = "";
    
    private Long itemPrice = 0L;
    private Long itemQty = 0L;
    private Long itemAmount = 0L;//Amount = Price * Qty
    private Long itemVAT = 0L;
    private Long itemAmountVAT = 0L;
    private Long itemDiscount = 0L;
    private Long itemPromotion = 0L;
    private Long itemTotal = 0L;//Total = Amount * (1+VAT/100) - Discount - Promotion

    private Long invoiceAmount = 0L;//Amount = Price * Qty
    private Long invoiceVAT = 0L;
    private Long invoiceAmountVAT = 0L;
    private Long invoiceDiscount = 0L;
    private Long invoicePromotion = 0L;
    private Long invoiceTotal = 0L;//Total = Amount * (1+VAT/100) - Discount - Promotion

    private String itemPriceString = "";
    private String itemQtyString = "";
    private String itemDiscountString = "";
    private String itemPromotionString = "";

    private InvoiceNoSaleItem[] invoiceNoSaleItem ;

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

    public Long getInvoiceAmountVAT() {
        return invoiceAmountVAT;
    }

    public void setInvoiceAmountVAT(Long invoiceAmountVAT) {
        this.invoiceAmountVAT = invoiceAmountVAT;
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

    public InvoiceNoSaleItem[] getInvoiceNoSaleItem() {
        return invoiceNoSaleItem;
    }

    public void setInvoiceNoSaleItem(InvoiceNoSaleItem[] invoiceNoSaleItem) {
        this.invoiceNoSaleItem = invoiceNoSaleItem;
    }

    //Clear infomation of Item
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
        this.itemPromotionString = "";
        this.itemDiscountString = "";
    }
}
