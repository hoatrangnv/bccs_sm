package com.viettel.im.database.BO;

/**
 * InvoiceList entity. @author MyEclipse Persistence Tools
 */

public class InvoiceListTemp  implements java.io.Serializable {

    // Fields

     private Long Id;
     

     private Long invoiceListId;


     private Long fromInvoice;


     private Long toInvoice;


     private String usid;

     
    /** default constructor */
    public InvoiceListTemp() {
    }

    public Long getFromInvoice() {
        return fromInvoice;
    }

    public void setFromInvoice(Long fromInvoice) {
        this.fromInvoice = fromInvoice;
    }

    public Long getInvoiceListId() {
        return invoiceListId;
    }

    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public Long getToInvoice() {
        return toInvoice;
    }

    public void setToInvoice(Long toInvoice) {
        this.toInvoice = toInvoice;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    
}