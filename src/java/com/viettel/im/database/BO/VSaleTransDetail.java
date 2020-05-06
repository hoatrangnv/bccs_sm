package com.viettel.im.database.BO;

import java.util.Date;

/**
 * VSaleTransDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class VSaleTransDetail implements java.io.Serializable {

    // Fields
    private Long saleTransDetailId;
    private Long stockTypeId;
    private String stockTypeName;
    private Long stockModelId;
    private String stockModelCode;
    private String stockModelName;
    private Long quantity;
    private Long price;
    private Double amount;
    private Long discountAmount;
    private Long vatAmount;
    private Long saleTransId;
    private Long shopId;
    private String shopCode;
    private String shopName;
    private Long staffId;
    private String staffCode;
    private String staffName;
    private Date saleTransDate;
    private String saleTransType;
    private String saleTransTypeName;
    private Long reasonId;
    private Long telecomServiceId;
    private String saleTransStatus;

    /**
     *
     * author tamdt1, 25/06/2009
     * ham khoi tao phuc vu viec xuat bao cao (bao cao tong hop)
     *
     */
    public VSaleTransDetail(String saleTransTypeName, String stockTypeName, String stockModelCode, String stockModelName,
            Long quantity, Long price, Double amount, Long discountAmount,
            Long vatAmount) {
        this.saleTransTypeName = saleTransTypeName;
        this.stockTypeName = stockTypeName;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.vatAmount = vatAmount;
    }

    /**
     *
     * author tamdt1, 25/06/2009
     * ham khoi tao phuc vu viec xuat bao cao (bao cao chi tiet cap duoi)
     *
     */
    public VSaleTransDetail(String shopName, String saleTransTypeName, String stockTypeName, String stockModelCode, String stockModelName,
            Long quantity, Long price, Double amount, Long discountAmount,
            Long vatAmount) {
        this.shopName = shopName;
        this.saleTransTypeName = saleTransTypeName;
        this.stockTypeName = stockTypeName;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.vatAmount = vatAmount;
    }

    /**
     *
     * author tamdt1, 25/06/2009
     * ham khoi tao phuc vu viec xuat bao cao (bao cao chi tiet nhan vien)
     *
     */
    public VSaleTransDetail(String staffName, String shopName, String saleTransTypeName, String stockTypeName, String stockModelCode, String stockModelName,
            Long quantity, Long price, Double amount, Long discountAmount,
            Long vatAmount) {
        this.staffName = staffName;
        this.shopName = shopName;
        this.saleTransTypeName = saleTransTypeName;
        this.stockTypeName = stockTypeName;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.vatAmount = vatAmount;
    }

    // Constructors
    /** default constructor */
    public VSaleTransDetail() {
    }

    /** minimal constructor */
    public VSaleTransDetail(Long saleTransDetailId, String stockTypeName,
            String stockModelCode, String stockModelName, Long quantity,
            Long saleTransId, Date saleTransDate, String saleTransStatus) {
        this.saleTransDetailId = saleTransDetailId;
        this.stockTypeName = stockTypeName;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.quantity = quantity;
        this.saleTransId = saleTransId;
        this.saleTransDate = saleTransDate;
        this.saleTransStatus = saleTransStatus;
    }

    /** full constructor */
    public VSaleTransDetail(Long saleTransDetailId, Long stockTypeId, String stockTypeName,
            Long stockModelId, String stockModelCode, String stockModelName, Long quantity,
            Long price, Double amount, Long discountAmount, Long vatAmount,
            Long saleTransId, Long shopId, String shopCode, String shopName,
            Long staffId, String staffCode, String staffName,
            Date saleTransDate, String saleTransType, String saleTransTypeName, Long reasonId,
            Long telecomServiceId, String saleTransStatus) {
        this.saleTransDetailId = saleTransDetailId;
        this.stockTypeId = stockTypeId;
        this.stockTypeName = stockTypeName;
        this.stockModelId = stockModelId;
        this.stockModelCode = stockModelCode;
        this.stockModelName = stockModelName;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.discountAmount = discountAmount;
        this.vatAmount = vatAmount;
        this.saleTransId = saleTransId;
        this.shopId = shopId;
        this.shopCode = shopCode;
        this.shopName = shopName;
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.staffName = staffName;
        this.saleTransDate = saleTransDate;
        this.saleTransType = saleTransType;
        this.saleTransTypeName = saleTransTypeName;
        this.reasonId = reasonId;
        this.telecomServiceId = telecomServiceId;
        this.saleTransStatus = saleTransStatus;
    }

    // Property accessors
    public Long getSaleTransDetailId() {
        return this.saleTransDetailId;
    }

    public void setSaleTransDetailId(Long saleTransDetailId) {
        this.saleTransDetailId = saleTransDetailId;
    }

    public Long getSaleTransId() {
        return this.saleTransId;
    }

    public void setSaleTransId(Long saleTransId) {
        this.saleTransId = saleTransId;
    }

    public String getStockTypeName() {
        return this.stockTypeName;
    }

    public void setStockTypeName(String stockTypeName) {
        this.stockTypeName = stockTypeName;
    }

    public String getStockModelCode() {
        return this.stockModelCode;
    }

    public void setStockModelCode(String stockModelCode) {
        this.stockModelCode = stockModelCode;
    }

    public String getStockModelName() {
        return this.stockModelName;
    }

    public void setStockModelName(String stockModelName) {
        this.stockModelName = stockModelName;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return this.price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getDiscountAmount() {
        return this.discountAmount;
    }

    public void setDiscountAmount(Long discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getVatAmount() {
        return this.vatAmount;
    }

    public void setVatAmount(Long vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Date getSaleTransDate() {
        return saleTransDate;
    }

    public void setSaleTransDate(Date saleTransDate) {
        this.saleTransDate = saleTransDate;
    }

    public String getSaleTransStatus() {
        return saleTransStatus;
    }

    public void setSaleTransStatus(String saleTransStatus) {
        this.saleTransStatus = saleTransStatus;
    }

    public String getSaleTransType() {
        return saleTransType;
    }

    public void setSaleTransType(String saleTransType) {
        this.saleTransType = saleTransType;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getTelecomServiceId() {
        return telecomServiceId;
    }

    public void setTelecomServiceId(Long telecomServiceId) {
        this.telecomServiceId = telecomServiceId;
    }

    public Long getStockModelId() {
        return stockModelId;
    }

    public void setStockModelId(Long stockModelId) {
        this.stockModelId = stockModelId;
    }

    public Long getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(Long stockTypeId) {
        this.stockTypeId = stockTypeId;
    }

    public String getSaleTransTypeName() {
        return saleTransTypeName;
    }

    public void setSaleTransTypeName(String saleTransTypeName) {
        this.saleTransTypeName = saleTransTypeName;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof VSaleTransDetail)) {
            return false;
        }
        VSaleTransDetail castOther = (VSaleTransDetail) other;

        return ((this.getSaleTransDetailId() == castOther.getSaleTransDetailId()) || (this.getSaleTransDetailId() != null && castOther.getSaleTransDetailId() != null && this.getSaleTransDetailId().equals(castOther.getSaleTransDetailId()))) && ((this.getStockTypeName() == castOther.getStockTypeName()) || (this.getStockTypeName() != null && castOther.getStockTypeName() != null && this.getStockTypeName().equals(castOther.getStockTypeName()))) && ((this.getStockModelCode() == castOther.getStockModelCode()) || (this.getStockModelCode() != null && castOther.getStockModelCode() != null && this.getStockModelCode().equals(
                castOther.getStockModelCode()))) && ((this.getStockModelName() == castOther.getStockModelName()) || (this.getStockModelName() != null && castOther.getStockModelName() != null && this.getStockModelName().equals(
                castOther.getStockModelName()))) && ((this.getQuantity() == castOther.getQuantity()) || (this.getQuantity() != null && castOther.getQuantity() != null && this.getQuantity().equals(castOther.getQuantity()))) && ((this.getPrice() == castOther.getPrice()) || (this.getPrice() != null && castOther.getPrice() != null && this.getPrice().equals(castOther.getPrice()))) && ((this.getAmount() == castOther.getAmount()) || (this.getAmount() != null && castOther.getAmount() != null && this.getAmount().equals(castOther.getAmount()))) && ((this.getDiscountAmount() == castOther.getDiscountAmount()) || (this.getDiscountAmount() != null && castOther.getDiscountAmount() != null && this.getDiscountAmount().equals(
                castOther.getDiscountAmount()))) && ((this.getVatAmount() == castOther.getVatAmount()) || (this.getVatAmount() != null && castOther.getVatAmount() != null && this.getVatAmount().equals(castOther.getVatAmount()))) && ((this.getSaleTransId() == castOther.getSaleTransId()) || (this.getSaleTransId() != null && castOther.getSaleTransId() != null && this.getSaleTransId().equals(castOther.getSaleTransId()))) && ((this.getShopId() == castOther.getShopId()) || (this.getShopId() != null && castOther.getShopId() != null && this.getShopId().equals(castOther.getShopId()))) && ((this.getShopCode() == castOther.getShopCode()) || (this.getShopCode() != null && castOther.getShopCode() != null && this.getShopCode().equals(castOther.getShopCode()))) && ((this.getShopName() == castOther.getShopName()) || (this.getShopName() != null && castOther.getShopName() != null && this.getShopName().equals(castOther.getShopName()))) && ((this.getStaffId() == castOther.getStaffId()) || (this.getStaffId() != null && castOther.getStaffId() != null && this.getStaffId().equals(castOther.getStaffId()))) && ((this.getStaffCode() == castOther.getStaffCode()) || (this.getStaffCode() != null && castOther.getStaffCode() != null && this.getStaffCode().equals(castOther.getStaffCode()))) && ((this.getStaffName() == castOther.getStaffName()) || (this.getStaffName() != null && castOther.getStaffName() != null && this.getStaffName().equals(castOther.getStaffName()))) && ((this.getSaleTransDate() == castOther.getSaleTransDate()) || (this.getSaleTransDate() != null && castOther.getSaleTransDate() != null && this.getSaleTransDate().equals(castOther.getSaleTransDate()))) && ((this.getSaleTransType() == castOther.getSaleTransType()) || (this.getSaleTransType() != null && castOther.getSaleTransType() != null && this.getSaleTransType().equals(castOther.getSaleTransType()))) && ((this.getReasonId() == castOther.getReasonId()) || (this.getReasonId() != null && castOther.getReasonId() != null && this.getReasonId().equals(castOther.getReasonId()))) && ((this.getTelecomServiceId() == castOther.getTelecomServiceId()) || (this.getTelecomServiceId() != null && castOther.getTelecomServiceId() != null && this.getTelecomServiceId().equals(
                castOther.getTelecomServiceId()))) && ((this.getSaleTransStatus() == castOther.getSaleTransStatus()) || (this.getSaleTransStatus() != null && castOther.getSaleTransStatus() != null && this.getSaleTransStatus().equals(
                castOther.getSaleTransStatus())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getSaleTransDetailId() == null ? 0 : this.getSaleTransDetailId().hashCode());
        result = 37 * result + (getStockTypeName() == null ? 0 : this.getStockTypeName().hashCode());
        result = 37 * result + (getStockModelCode() == null ? 0 : this.getStockModelCode().hashCode());
        result = 37 * result + (getStockModelName() == null ? 0 : this.getStockModelName().hashCode());
        result = 37 * result + (getQuantity() == null ? 0 : this.getQuantity().hashCode());
        result = 37 * result + (getPrice() == null ? 0 : this.getPrice().hashCode());
        result = 37 * result + (getAmount() == null ? 0 : this.getAmount().hashCode());
        result = 37 * result + (getDiscountAmount() == null ? 0 : this.getDiscountAmount().hashCode());
        result = 37 * result + (getVatAmount() == null ? 0 : this.getVatAmount().hashCode());
        result = 37 * result + (getSaleTransId() == null ? 0 : this.getSaleTransId().hashCode());
        result = 37 * result + (getShopId() == null ? 0 : this.getShopId().hashCode());
        result = 37 * result + (getShopCode() == null ? 0 : this.getShopCode().hashCode());
        result = 37 * result + (getShopName() == null ? 0 : this.getShopName().hashCode());
        result = 37 * result + (getStaffId() == null ? 0 : this.getStaffId().hashCode());
        result = 37 * result + (getStaffCode() == null ? 0 : this.getStaffCode().hashCode());
        result = 37 * result + (getStaffName() == null ? 0 : this.getStaffName().hashCode());
        result = 37 * result + (getSaleTransDate() == null ? 0 : this.getSaleTransDate().hashCode());
        result = 37 * result + (getSaleTransType() == null ? 0 : this.getSaleTransType().hashCode());
        result = 37 * result + (getReasonId() == null ? 0 : this.getReasonId().hashCode());
        result = 37 * result + (getTelecomServiceId() == null ? 0 : this.getTelecomServiceId().hashCode());
        result = 37 * result + (getSaleTransStatus() == null ? 0 : this.getSaleTransStatus().hashCode());
        return result;
    }
}