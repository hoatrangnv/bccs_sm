package com.viettel.im.database.BO;

/**
 * SaleInvoiceTypeId entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class SaleInvoiceType implements java.io.Serializable {

    // Fields
    private Long saleInvoiceTypeId;
    private String code;
    private String name;
    private String description;
    private Long saleGroup;
    private Long invoiceGroup;
    private String reasonSaleGroupCode;
    private String reasonSaleGroupName;
    private String reasonInvoiceGroupCode;
    private String reasonInvoiceGroupName;
    private Long saleTransType;

    // Constructors

    /** default constructor */
    public SaleInvoiceType() {
    }

    /** minimal constructor */
    public SaleInvoiceType(Long saleInvoiceTypeId, String code, String name) {
        this.saleInvoiceTypeId = saleInvoiceTypeId;
        this.code = code;
        this.name = name;
    }

    /** full constructor */
    public SaleInvoiceType(Long saleInvoiceTypeId, String code, String name, String description, Long saleGroup, Long invoiceGroup, String reasonSaleGroupCode, String reasonSaleGroupName, String reasonInvoiceGroupCode, String reasonInvoiceGroupName, Long saleTransType) {
        this.saleInvoiceTypeId = saleInvoiceTypeId;
        this.code = code;
        this.name = name;
        this.description = description;
        this.saleGroup = saleGroup;
        this.invoiceGroup = invoiceGroup;
        this.reasonSaleGroupCode = reasonSaleGroupCode;
        this.reasonSaleGroupName = reasonSaleGroupName;
        this.reasonInvoiceGroupCode = reasonInvoiceGroupCode;
        this.reasonInvoiceGroupName = reasonInvoiceGroupName;
        this.saleTransType = saleTransType;
    }

    // Property accessors
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getInvoiceGroup() {
        return invoiceGroup;
    }

    public void setInvoiceGroup(Long invoiceGroup) {
        this.invoiceGroup = invoiceGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReasonInvoiceGroupCode() {
        return reasonInvoiceGroupCode;
    }

    public void setReasonInvoiceGroupCode(String reasonInvoiceGroupCode) {
        this.reasonInvoiceGroupCode = reasonInvoiceGroupCode;
    }

    public String getReasonInvoiceGroupName() {
        return reasonInvoiceGroupName;
    }

    public void setReasonInvoiceGroupName(String reasonInvoiceGroupName) {
        this.reasonInvoiceGroupName = reasonInvoiceGroupName;
    }

    public String getReasonSaleGroupCode() {
        return reasonSaleGroupCode;
    }

    public void setReasonSaleGroupCode(String reasonSaleGroupCode) {
        this.reasonSaleGroupCode = reasonSaleGroupCode;
    }

    public String getReasonSaleGroupName() {
        return reasonSaleGroupName;
    }

    public void setReasonSaleGroupName(String reasonSaleGroupName) {
        this.reasonSaleGroupName = reasonSaleGroupName;
    }

    public Long getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(Long saleGroup) {
        this.saleGroup = saleGroup;
    }

    public Long getSaleInvoiceTypeId() {
        return saleInvoiceTypeId;
    }

    public void setSaleInvoiceTypeId(Long saleInvoiceTypeId) {
        this.saleInvoiceTypeId = saleInvoiceTypeId;
    }

    public Long getSaleTransType() {
        return saleTransType;
    }

    public void setSaleTransType(Long saleTransType) {
        this.saleTransType = saleTransType;
    }
}