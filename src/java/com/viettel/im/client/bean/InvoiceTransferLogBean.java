package com.viettel.im.client.bean;

import com.viettel.im.common.util.StringUtils;
import java.util.Date;




public class InvoiceTransferLogBean implements java.io.Serializable  { 
    private Long id;
	private Long invoiceListId;
	private String serialNo;
	private Long blockNo;
	private Long invoiceNo;
	private Long fromInvoice;
	private Long toInvoice;
	private String currInvoice;

	private Long parentShopId;
    private String parentShopCode;

	private Long childShopId;
    private String childShopCode;

    private String fromToInvoice;

	private Long staffId;
    private String staffCode;

	private String userCreated;
	private Date dateCreated;

    private Long transferType;
    private String transferTypeName;

    private Long numberOfInvoice;


	// Constructors

	/** default constructor */
	public InvoiceTransferLogBean() {
	}

	/** minimal constructor */
	public InvoiceTransferLogBean(Long id) {
		this.id = id;
	}

    /** full constructor */
    public InvoiceTransferLogBean(Long id, Long invoiceListId, String serialNo, Long blockNo, Long invoiceNo, Long fromInvoice, Long toInvoice, String currInvoice, Long parentShopId, String parentShopCode, Long childShopId, String childShopCode, String fromToInvoice, Long staffId, String staffCode, String userCreated, Date dateCreated, Long transferType, String transferTypeName) {
        this.id = id;
        this.invoiceListId = invoiceListId;
        this.serialNo = serialNo;
        this.blockNo = blockNo;
        this.invoiceNo = invoiceNo;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.currInvoice = currInvoice;
        this.parentShopId = parentShopId;
        this.parentShopCode = parentShopCode;
        this.childShopId = childShopId;
        this.childShopCode = childShopCode;
        this.fromToInvoice = fromToInvoice;
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.userCreated = userCreated;
        this.dateCreated = dateCreated;
        this.transferType = transferType;
        this.transferTypeName = transferTypeName;
    }

    public Long getNumberOfInvoice() {
        return numberOfInvoice;
    }

    public void setNumberOfInvoice(Long numberOfInvoice) {
        this.numberOfInvoice = numberOfInvoice;
    }

    public String getChildShopCode() {
        return childShopCode;
    }

    public void setChildShopCode(String childShopCode) {
        this.childShopCode = childShopCode;
    }

    public String getParentShopCode() {
        return parentShopCode;
    }

    public void setParentShopCode(String parentShopCode) {
        this.parentShopCode = parentShopCode;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getTransferTypeName() {
        return transferTypeName;
    }

    public void setTransferTypeName(String transferTypeName) {
        this.transferTypeName = transferTypeName;
    }

    public String getFromToInvoice() {
        return fromToInvoice;
    }

    public void setFromToInvoice(String fromToInvoice) {
        this.fromToInvoice = fromToInvoice;
    }

  	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceListId() {
		return this.invoiceListId;
	}

	public void setInvoiceListId(Long invoiceListId) {
		this.invoiceListId = invoiceListId;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Long getBlockNo() {
		return this.blockNo;
	}

	public void setBlockNo(Long blockNo) {
		this.blockNo = blockNo;
	}

	public Long getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(Long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Long getFromInvoice() {
		return this.fromInvoice;
	}

	public void setFromInvoice(Long fromInvoice) {
		this.fromInvoice = fromInvoice;
	}

	public Long getToInvoice() {
		return this.toInvoice;
	}

	public void setToInvoice(Long toInvoice) {
		this.toInvoice = toInvoice;
        this.fromToInvoice = StringUtils.standardInvoiceString(this.fromInvoice) + " - " + StringUtils.standardInvoiceString(this.toInvoice);
        this.numberOfInvoice = this.toInvoice - this.fromInvoice + 1;
	}

	public String getCurrInvoice() {
		return this.currInvoice;
	}

	public void setCurrInvoice(String currInvoice) {
		this.currInvoice = currInvoice;
	}

	public Long getParentShopId() {
		return this.parentShopId;
	}

	public void setParentShopId(Long parentShopId) {
		this.parentShopId = parentShopId;
	}

	public Long getChildShopId() {
		return this.childShopId;
	}

	public void setChildShopId(Long childShopId) {
		this.childShopId = childShopId;
	}

	public Long getStaffId() {
		return this.staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getUserCreated() {
		return this.userCreated;
	}

	public void setUserCreated(String userCreated) {
		this.userCreated = userCreated;
	}

	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getTransferType() {
		return this.transferType;
	}

	public void setTransferType(Long transferType) {
		this.transferType = transferType;
	}

}