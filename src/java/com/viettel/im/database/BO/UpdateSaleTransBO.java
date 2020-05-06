package com.viettel.im.database.BO;

import java.util.Date;

/**
 * UpdateSaleTransBO entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UpdateSaleTransBO implements java.io.Serializable {

	// Fields

	private Long saleTransId;
	private Date saleTransDate;
	private Long shopId;
	private String shopCode;
	private String shopName;
	private Long staffId;
	private String staffCode;
	private String staffName;
	private String custName;
	private String telNumber;
	private String company;
	private String tin;
	private String contractNo;
	private String isdn;
	private String address;
	private String note;
	private Long amountNotTax;
	private Long tax;
	private Long promotion;
	private Long discount;
	private Long amountTax;
        private String saleTransType;
        private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaleTransType() {
        return saleTransType;
    }

    public void setSaleTransType(String saleTransType) {
        this.saleTransType = saleTransType;
    }

	// Constructors

	/** default constructor */
	public UpdateSaleTransBO() {
	}

	/** minimal constructor */
	public UpdateSaleTransBO(Long saleTransId, Date saleTransDate,
			String shopCode, String shopName, String staffCode) {
		this.saleTransId = saleTransId;
		this.saleTransDate = saleTransDate;
		this.shopCode = shopCode;
		this.shopName = shopName;
		this.staffCode = staffCode;
	}

	/** full constructor */
	public UpdateSaleTransBO(Long saleTransId, Date saleTransDate,
			Long shopId, String shopCode, String shopName, Long staffId,
			String staffCode, String staffName, String custName,
			String telNumber, String company, String tin, String contractNo,
			String isdn, String address, String note, Long amountNotTax,
			Long tax, Long promotion, Long discount, Long amountTax) {
		this.saleTransId = saleTransId;
		this.saleTransDate = saleTransDate;
		this.shopId = shopId;
		this.shopCode = shopCode;
		this.shopName = shopName;
		this.staffId = staffId;
		this.staffCode = staffCode;
		this.staffName = staffName;
		this.custName = custName;
		this.telNumber = telNumber;
		this.company = company;
		this.tin = tin;
		this.contractNo = contractNo;
		this.isdn = isdn;
		this.address = address;
		this.note = note;
		this.amountNotTax = amountNotTax;
		this.tax = tax;
		this.promotion = promotion;
		this.discount = discount;
		this.amountTax = amountTax;
	}

	// Property accessors

	public Long getSaleTransId() {
		return this.saleTransId;
	}

	public void setSaleTransId(Long saleTransId) {
		this.saleTransId = saleTransId;
	}

	public Date getSaleTransDate() {
		return this.saleTransDate;
	}

	public void setSaleTransDate(Date saleTransDate) {
		this.saleTransDate = saleTransDate;
	}

	public Long getShopId() {
		return this.shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return this.shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getStaffCode() {
		return this.staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return this.staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getTelNumber() {
		return this.telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTin() {
		return this.tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getIsdn() {
		return this.isdn;
	}

	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getAmountNotTax() {
		return this.amountNotTax;
	}

	public void setAmountNotTax(Long amountNotTax) {
		this.amountNotTax = amountNotTax;
	}

	public Long getTax() {
		return this.tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public Long getPromotion() {
		return this.promotion;
	}

	public void setPromotion(Long promotion) {
		this.promotion = promotion;
	}

	public Long getDiscount() {
		return this.discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getAmountTax() {
		return this.amountTax;
	}

	public void setAmountTax(Long amountTax) {
		this.amountTax = amountTax;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UpdateSaleTransBO))
			return false;
		UpdateSaleTransBO castOther = (UpdateSaleTransBO) other;

		return ((this.getSaleTransId() == castOther.getSaleTransId()) || (this
				.getSaleTransId() != null
				&& castOther.getSaleTransId() != null && this.getSaleTransId()
				.equals(castOther.getSaleTransId())))
				&& ((this.getSaleTransDate() == castOther.getSaleTransDate()) || (this
						.getSaleTransDate() != null
						&& castOther.getSaleTransDate() != null && this
						.getSaleTransDate()
						.equals(castOther.getSaleTransDate())))
				&& ((this.getShopId() == castOther.getShopId()) || (this
						.getShopId() != null
						&& castOther.getShopId() != null && this.getShopId()
						.equals(castOther.getShopId())))
				&& ((this.getShopCode() == castOther.getShopCode()) || (this
						.getShopCode() != null
						&& castOther.getShopCode() != null && this
						.getShopCode().equals(castOther.getShopCode())))
				&& ((this.getShopName() == castOther.getShopName()) || (this
						.getShopName() != null
						&& castOther.getShopName() != null && this
						.getShopName().equals(castOther.getShopName())))
				&& ((this.getStaffId() == castOther.getStaffId()) || (this
						.getStaffId() != null
						&& castOther.getStaffId() != null && this.getStaffId()
						.equals(castOther.getStaffId())))
				&& ((this.getStaffCode() == castOther.getStaffCode()) || (this
						.getStaffCode() != null
						&& castOther.getStaffCode() != null && this
						.getStaffCode().equals(castOther.getStaffCode())))
				&& ((this.getStaffName() == castOther.getStaffName()) || (this
						.getStaffName() != null
						&& castOther.getStaffName() != null && this
						.getStaffName().equals(castOther.getStaffName())))
				&& ((this.getCustName() == castOther.getCustName()) || (this
						.getCustName() != null
						&& castOther.getCustName() != null && this
						.getCustName().equals(castOther.getCustName())))
				&& ((this.getTelNumber() == castOther.getTelNumber()) || (this
						.getTelNumber() != null
						&& castOther.getTelNumber() != null && this
						.getTelNumber().equals(castOther.getTelNumber())))
				&& ((this.getCompany() == castOther.getCompany()) || (this
						.getCompany() != null
						&& castOther.getCompany() != null && this.getCompany()
						.equals(castOther.getCompany())))
				&& ((this.getTin() == castOther.getTin()) || (this.getTin() != null
						&& castOther.getTin() != null && this.getTin().equals(
						castOther.getTin())))
				&& ((this.getContractNo() == castOther.getContractNo()) || (this
						.getContractNo() != null
						&& castOther.getContractNo() != null && this
						.getContractNo().equals(castOther.getContractNo())))
				&& ((this.getIsdn() == castOther.getIsdn()) || (this.getIsdn() != null
						&& castOther.getIsdn() != null && this.getIsdn()
						.equals(castOther.getIsdn())))
				&& ((this.getAddress() == castOther.getAddress()) || (this
						.getAddress() != null
						&& castOther.getAddress() != null && this.getAddress()
						.equals(castOther.getAddress())))
				&& ((this.getNote() == castOther.getNote()) || (this.getNote() != null
						&& castOther.getNote() != null && this.getNote()
						.equals(castOther.getNote())))
				&& ((this.getAmountNotTax() == castOther.getAmountNotTax()) || (this
						.getAmountNotTax() != null
						&& castOther.getAmountNotTax() != null && this
						.getAmountNotTax().equals(castOther.getAmountNotTax())))
				&& ((this.getTax() == castOther.getTax()) || (this.getTax() != null
						&& castOther.getTax() != null && this.getTax().equals(
						castOther.getTax())))
				&& ((this.getPromotion() == castOther.getPromotion()) || (this
						.getPromotion() != null
						&& castOther.getPromotion() != null && this
						.getPromotion().equals(castOther.getPromotion())))
				&& ((this.getDiscount() == castOther.getDiscount()) || (this
						.getDiscount() != null
						&& castOther.getDiscount() != null && this
						.getDiscount().equals(castOther.getDiscount())))
				&& ((this.getAmountTax() == castOther.getAmountTax()) || (this
						.getAmountTax() != null
						&& castOther.getAmountTax() != null && this
						.getAmountTax().equals(castOther.getAmountTax())));
	}

    @Override
	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSaleTransId() == null ? 0 : this.getSaleTransId()
						.hashCode());
		result = 37
				* result
				+ (getSaleTransDate() == null ? 0 : this.getSaleTransDate()
						.hashCode());
		result = 37 * result
				+ (getShopId() == null ? 0 : this.getShopId().hashCode());
		result = 37 * result
				+ (getShopCode() == null ? 0 : this.getShopCode().hashCode());
		result = 37 * result
				+ (getShopName() == null ? 0 : this.getShopName().hashCode());
		result = 37 * result
				+ (getStaffId() == null ? 0 : this.getStaffId().hashCode());
		result = 37 * result
				+ (getStaffCode() == null ? 0 : this.getStaffCode().hashCode());
		result = 37 * result
				+ (getStaffName() == null ? 0 : this.getStaffName().hashCode());
		result = 37 * result
				+ (getCustName() == null ? 0 : this.getCustName().hashCode());
		result = 37 * result
				+ (getTelNumber() == null ? 0 : this.getTelNumber().hashCode());
		result = 37 * result
				+ (getCompany() == null ? 0 : this.getCompany().hashCode());
		result = 37 * result
				+ (getTin() == null ? 0 : this.getTin().hashCode());
		result = 37
				* result
				+ (getContractNo() == null ? 0 : this.getContractNo()
						.hashCode());
		result = 37 * result
				+ (getIsdn() == null ? 0 : this.getIsdn().hashCode());
		result = 37 * result
				+ (getAddress() == null ? 0 : this.getAddress().hashCode());
		result = 37 * result
				+ (getNote() == null ? 0 : this.getNote().hashCode());
		result = 37
				* result
				+ (getAmountNotTax() == null ? 0 : this.getAmountNotTax()
						.hashCode());
		result = 37 * result
				+ (getTax() == null ? 0 : this.getTax().hashCode());
		result = 37 * result
				+ (getPromotion() == null ? 0 : this.getPromotion().hashCode());
		result = 37 * result
				+ (getDiscount() == null ? 0 : this.getDiscount().hashCode());
		result = 37 * result
				+ (getAmountTax() == null ? 0 : this.getAmountTax().hashCode());
		return result;
	}

}