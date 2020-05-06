package com.viettel.im.client.form;

import com.viettel.im.database.BO.StockTransFull;
import java.util.Date;
import java.util.List;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Doan Thanh 8 date 12/03/2009 Modified by TrongLV at 2010-01-18
 *
 */
public class SaleToCollaboratorForm extends StockTransFull {
    // Fields

    private List listReaSonSaleExpCollaborator;
    private List listTelecomService;
    private List listPriceCollaborator;
    private String saleDate;
    //price: duoc dinh nghia trong stocktransfull
    private Double amount;//Tong tien hang hoa
    private Double amountNotTax;//Tong tien truoc thue
    private Double amountTax;//Tong tien thanh toan
    private Double tax;//Tong thue
    private Double discout;//Tong CK
    private Double promotionAmount;//tong tien KM
    //Gia tri kieu tien te
    private String quantityMoney;//So luong (format)
    private String priceMoney;//Don gia (format)
    private String amountMoney;//Tong tien hang hoa (format)    
    private String amountNotTaxMoney;//Tong tien truoc thue (format)
    private String amountTaxMoney;//Tong tien thanh toan (format)
    private String taxMoney;//Tong thue (format)
    private String discoutMoney;//Tong CK (format)
    private String promotionAmountMoney;//Tong KM (format)
    private Long discountId;
    private Long promotionId;
    //Thong tin hang hoa
    private Long staffId;
    private String staffCode;
    private String staffName;
    private Long staffCodeKey_BK;
    //Co cho nhap serial khong
    private String inputSerial = "false";
    private String reSult;
    private String custName;
    private String company;
    private String tin;
    private String dateSale;
    private String payMethodId;
    private Long salerId;
    private boolean other;
    private Long drawStatus;
    private String address;
    private String TeleNumber;
    private String ISDN;
    private String NoteSaleTrans;
    //Create by TrongLV
    private Long saleTransId;
    private String serial;
    private String agentTypeIdSearch;
    private String agentCodeSearch;
    private String agentNameSearch;
    private Long telecomServiceId;
    private Long stockModelId;
    private String telecomServiceName;
    private String itemType = "0";
    private String itemId;
    private String saleTransVat;
    private String itemVat;
    private String referenceNo;
    private Double discountAfterRate;//CK sau thue
    private Long discountMethod;//discount_group_method
    private Long discountType;//discount_type
    String discountMoney;
    Double discount;
    String kitMfsIsdn;
    //QuocDM1 bo sung de lay gia theo cach ban nao?
    private Long typeOfSale;
    //Add Pay Emola
    private String isdnWallet;
    //22670 xuat hang ban cho dai ly
    private String shopCode;
    private String shopName;
    private String parentMasterAgentCode;
    private String parentMasterAgentName;
    private Long parentMasterAgentId;
    private String paymentPapersCode;
    private String amountPayment;
    //tannh20180111 upload file va checked
    private String imageUrl;
    private Long isChecked;
    private String orderCode;

    public String getParentMasterAgentCode() {
        return parentMasterAgentCode;
    }

    public void setParentMasterAgentCode(String parentMasterAgentCode) {
        this.parentMasterAgentCode = parentMasterAgentCode;
    }

    public String getParentMasterAgentName() {
        return parentMasterAgentName;
    }

    public void setParentMasterAgentName(String parentMasterAgentName) {
        this.parentMasterAgentName = parentMasterAgentName;
    }

    public Long getParentMasterAgentId() {
        return parentMasterAgentId;
    }

    public void setParentMasterAgentId(Long parentMasterAgentId) {
        this.parentMasterAgentId = parentMasterAgentId;
    }

    public String getPaymentPapersCode() {
        return paymentPapersCode;
    }

    public void setPaymentPapersCode(String paymentPapersCode) {
        this.paymentPapersCode = paymentPapersCode;
    }

    public String getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(String amountPayment) {
        this.amountPayment = amountPayment;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getIsdnWallet() {
        return isdnWallet;
    }

    public void setIsdnWallet(String isdnWallet) {
        this.isdnWallet = isdnWallet;
    }

    public Long getTypeOfSale() {
        return typeOfSale;
    }

    public void setTypeOfSale(Long typeOfSale) {
        this.typeOfSale = typeOfSale;
    }

    public String getKitMfsIsdn() {
        return kitMfsIsdn;
    }

    public void setKitMfsIsdn(String kitMfsIsdn) {
        this.kitMfsIsdn = kitMfsIsdn;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDiscountAfterRate() {
        return discountAfterRate;
    }

    public void setDiscountAfterRate(Double discountAfterRate) {
        this.discountAfterRate = discountAfterRate;
    }

    public String getQuantityMoney() {
        return quantityMoney;
    }

    public void setQuantityMoney(String quantityMoney) {
        this.quantityMoney = quantityMoney;
    }

    public String getAgentCodeSearch() {
        return agentCodeSearch;
    }

    public void setAgentCodeSearch(String agentCodeSearch) {
        this.agentCodeSearch = agentCodeSearch;
    }

    public String getAgentNameSearch() {
        return agentNameSearch;
    }

    public void setAgentNameSearch(String agentNameSearch) {
        this.agentNameSearch = agentNameSearch;
    }

    public String getAgentTypeIdSearch() {
        return agentTypeIdSearch;
    }

    public void setAgentTypeIdSearch(String agentTypeIdSearch) {
        this.agentTypeIdSearch = agentTypeIdSearch;
    }

    public String getItemVat() {
        return itemVat;
    }

    public void setItemVat(String itemVat) {
        this.itemVat = itemVat;
    }

    public String getSaleTransVat() {
        return saleTransVat;
    }

    public void setSaleTransVat(String saleTransVat) {
        this.saleTransVat = saleTransVat;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getSaleTransId() {
        return saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }

    //End: Create by TrongLV
    public SaleToCollaboratorForm() {
    }

    public void resetForm() {
        setTelecomServiceId(null);
        setStockModelId(null);
        setStockModelCode("");
        setStockModelName("");
        setPriceId(null);
        setQuantity(null);
        setNote(null);

        itemId = "";
        itemType = "0";
        itemVat = "";



    }

    public void resetForm(boolean resetAll) {
        setTelecomServiceId(null);
        setStockModelId(null);
        setPriceId(null);
        setQuantity(null);
        setNote(null);
        if (resetAll) {
            setAmount(null);
            setAmountNotTax(null);
            setAmountTax(null);
            setDiscout(null);
            setTax(null);
            setPromotionAmount(null);
            setDiscout(null);
            amountMoney = null;
            amountNotTaxMoney = null;
            amountTaxMoney = null;
            taxMoney = null;
            discoutMoney = null;
            promotionAmountMoney = null;

            //Bo sung
            amount = null;//Tong tien hang hoa
            amountNotTax = null;//Tong tien truoc thue
            amountTax = null;//Tong tien thanh toan
            tax = null;//Tong thue
            discout = null;//Tong CK
            promotionAmount = null;//tong tien KM

            //Gia tri kieu tien te
            quantityMoney = "";//So luong (format)
            priceMoney = "";//Don gia (format)
            amountMoney = "";//Tong tien hang hoa (format)
            amountNotTaxMoney = "";//Tong tien truoc thue (format)
            amountTaxMoney = "";//Tong tien thanh toan (format)
            taxMoney = "";//Tong thue (format)
            discoutMoney = "";//Tong CK (format)
            promotionAmountMoney = "";//Tong KM (format)

        }
        itemId = "";
        itemType = "0";
        itemVat = "";
    }

    public String getPromotionAmountMoney() {
        return promotionAmountMoney;
    }

    public void setPromotionAmountMoney(String promotionAmountMoney) {
        this.promotionAmountMoney = promotionAmountMoney;
    }

    public String getNoteSaleTrans() {
        return NoteSaleTrans;
    }

    public void setNoteSaleTrans(String NoteSaleTrans) {
        this.NoteSaleTrans = NoteSaleTrans;
    }

    public String getISDN() {
        return ISDN;
    }

    public void setISDN(String ISDN) {
        this.ISDN = ISDN;
    }

    public String getTeleNumber() {
        return TeleNumber;
    }

    public void setTeleNumber(String TeleNumber) {
        this.TeleNumber = TeleNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAmountNotTax() {
        return amountNotTax;
    }

    public void setAmountNotTax(Double amountNotTax) {
        this.amountNotTax = amountNotTax;
    }

    public Double getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getDateSale() {
        return dateSale;
    }

    public void setDateSale(String dateSale) {
        this.dateSale = dateSale;
    }

    public Double getDiscout() {
        return discout;
    }

    public void setDiscout(Double discout) {
        this.discout = discout;
    }

    public String getInputSerial() {
        return inputSerial;
    }

    public void setInputSerial(String inputSerial) {
        this.inputSerial = inputSerial;
    }

    public List getListPriceCollaborator() {
        return listPriceCollaborator;
    }

    public void setListPriceCollaborator(List listPriceCollaborator) {
        this.listPriceCollaborator = listPriceCollaborator;
    }

    public List getListReaSonSaleExpCollaborator() {
        return listReaSonSaleExpCollaborator;
    }

    public void setListReaSonSaleExpCollaborator(List listReaSonSaleExpCollaborator) {
        this.listReaSonSaleExpCollaborator = listReaSonSaleExpCollaborator;
    }

    public List getListTelecomService() {
        return listTelecomService;
    }

    public void setListTelecomService(List listTelecomService) {
        this.listTelecomService = listTelecomService;
    }

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public String getPayMethodId() {
        return payMethodId;
    }

    public void setPayMethodId(String payMethodId) {
        this.payMethodId = payMethodId;
    }

    public String getReSult() {
        return reSult;
    }

    public void setReSult(String reSult) {
        this.reSult = reSult;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public Long getSalerId() {
        return salerId;
    }

    public void setSalerId(Long salerId) {
        this.salerId = salerId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getStaffCodeKey_BK() {
        return staffCodeKey_BK;
    }

    public void setStaffCodeKey_BK(Long staffCodeKey_BK) {
        this.staffCodeKey_BK = staffCodeKey_BK;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public String getTelecomServiceName() {
        return telecomServiceName;
    }

    public void setTelecomServiceName(String telecomServiceName) {
        this.telecomServiceName = telecomServiceName;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    /**
     * @return the drawStatus
     */
    public Long getDrawStatus() {
        return drawStatus;
    }

    /**
     * @param drawStatus the drawStatus to set
     */
    public void setDrawStatus(Long drawStatus) {
        this.drawStatus = drawStatus;
    }

    public Double getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Double promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the stockModelId
     */
    public Long getStockModelId() {
        return stockModelId;
    }

    /**
     * @param stockModelId the stockModelId to set
     */
    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    /**
     * @return the discountId
     */
    public Long getDiscountId() {
        return discountId;
    }

    /**
     * @param discountId the discountId to set
     */
    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getAmountNotTaxMoney() {
        return amountNotTaxMoney;
    }

    public void setAmountNotTaxMoney(String amountNotTaxMoney) {
        this.amountNotTaxMoney = amountNotTaxMoney;
    }

    public String getAmountTaxMoney() {
        return amountTaxMoney;
    }

    public void setAmountTaxMoney(String amountTaxMoney) {
        this.amountTaxMoney = amountTaxMoney;
    }

    public String getDiscoutMoney() {
        return discoutMoney;
    }

    public void setDiscoutMoney(String discoutMoney) {
        this.discoutMoney = discoutMoney;
    }

    public String getTaxMoney() {
        return taxMoney;
    }

    public void setTaxMoney(String taxMoney) {
        this.taxMoney = taxMoney;
    }

    /**
     * @return the priceMoney
     */
    public String getPriceMoney() {
        return priceMoney;
    }

    /**
     * @param priceMoney the priceMoney to set
     */
    public void setPriceMoney(String priceMoney) {
        this.priceMoney = priceMoney;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Long getDiscountMethod() {
        return discountMethod;
    }

    public void setDiscountMethod(Long discountMethod) {
        this.discountMethod = discountMethod;
    }

    public Long getDiscountType() {
        return discountType;
    }

    public void setDiscountType(Long discountType) {
        this.discountType = discountType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Long isChecked) {
        this.isChecked = isChecked;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
}
