package com.viettel.im.client.form;

/**
 *
 * @author Vunt
 */
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.BookType;
import org.apache.struts.action.ActionForm;

public class BookTypeForm extends ActionForm {

    private String booktypeid;
    private String numinvoice;
    private String blockname;
    private String lengthname;
    private String lengthinvoice;
    private String type;
    private String book;
    private String serialreal;
    private String serialcode;
    private String decription;
    private String status;
    private String pagewith;
    private String pageheight;
    private String rowspacing;
    private String maxrow;
    private Long invoiceType;

    public String getBooktypeid() {
        return booktypeid;
    }

    public void setBooktypeid(String booktypeid) {
        this.booktypeid = booktypeid;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getLengthinvoice() {
        return lengthinvoice;
    }

    public void setLengthinvoice(String lengthinvoice) {
        this.lengthinvoice = lengthinvoice;
    }

    public String getLengthname() {
        return lengthname;
    }

    public void setLengthname(String lengthname) {
        this.lengthname = lengthname;
    }

    public String getMaxrow() {
        return maxrow;
    }

    public void setMaxrow(String maxrow) {
        this.maxrow = maxrow;
    }

    public String getNuminvoice() {
        return numinvoice;
    }

    public void setNuminvoice(String numinvoice) {
        this.numinvoice = numinvoice;
    }

    public String getPageheight() {
        return pageheight;
    }

    public void setPageheight(String pageheight) {
        this.pageheight = pageheight;
    }

    public String getPagewith() {
        return pagewith;
    }

    public void setPagewith(String pagewith) {
        this.pagewith = pagewith;
    }

    public String getRowspacing() {
        return rowspacing;
    }

    public void setRowspacing(String rowspacing) {
        this.rowspacing = rowspacing;
    }

    public String getSerialcode() {
        return serialcode;
    }

    public void setSerialcode(String serialcode) {
        this.serialcode = serialcode;
    }

    public String getSerialreal() {
        return serialreal;
    }

    public void setSerialreal(String serialreal) {
        this.serialreal = serialreal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Long invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void resetForm() {
        this.setBlockname("");
        this.setBook("");
        this.setDecription("");
        this.setLengthinvoice("");
        this.setLengthname("");
        this.setMaxrow("");
        this.setNuminvoice("");
        this.setPageheight("");
        this.setPagewith("");
        this.setRowspacing("");
        this.setSerialcode("");
        this.setSerialreal("");
        this.setStatus("");
        this.setType("");
        this.setInvoiceType(Constant.INVOICE_TYPE_SALE);
    }

    public void copyDataToBO(BookType bookType) {
        if (this.getBooktypeid() != null && !this.getBooktypeid().trim().equals("")) {
            bookType.setBookTypeId(Long.parseLong(this.getBooktypeid().trim()));
        }
        if (this.getNuminvoice() != null && !this.getNuminvoice().trim().equals("")) {
            bookType.setNumInvoice(Long.parseLong(this.getNuminvoice().trim()));
        }
        bookType.setBlockName(this.getBlockname().trim());
        if (this.getLengthname() != null && !this.getLengthname().trim().equals("")) {
            bookType.setLengthName(Long.parseLong(this.getLengthname().trim()));
        }
        if (this.getLengthinvoice() != null && !this.getLengthinvoice().trim().equals("")) {
            bookType.setLengthInvoice(Long.parseLong(this.getLengthinvoice().trim()));
        }
        bookType.setType(this.getType().trim());
        if (this.getBook() != null && !this.getBook().trim().equals("")) {
            bookType.setBook(Long.parseLong(this.getBook().trim()));
        }
        bookType.setSerialReal(this.getSerialreal().trim());
        bookType.setSerialCode(this.getSerialcode().trim());
        bookType.setDescription(this.getDecription().trim());
        if (this.getStatus() != null && !this.getStatus().trim().equals("")) {
            bookType.setStatus(Long.parseLong(this.getStatus().trim()));
        }
        if (this.getPagewith() != null && !this.getPagewith().trim().equals("")) {
            bookType.setPageWidth(Long.parseLong(this.getPagewith().trim()));
        }
        if (this.getPageheight() != null && !this.getPageheight().trim().equals("")) {
            bookType.setPageHeight(Long.parseLong(this.getPageheight().trim()));
        }
        if (this.getRowspacing() != null && !this.getRowspacing().trim().equals("")) {
            bookType.setRowSpacing(Long.parseLong(this.getRowspacing().trim()));
        }
        if (this.getMaxrow() != null && !this.getMaxrow().trim().equals("")) {
            bookType.setMaxRow(Long.parseLong(this.getMaxrow().trim()));
        }
        if (this.getInvoiceType() != null) {
            bookType.setInvoiceType(this.getInvoiceType());
        }
    }

    public void copyDataFromBO(BookType bookType) {
        this.setBooktypeid(bookType.getBookTypeId().toString());
        this.setNuminvoice(bookType.getNumInvoice().toString());
        this.setBlockname(bookType.getBlockName());
        this.setLengthname(bookType.getLengthName().toString());
        this.setLengthinvoice(bookType.getLengthInvoice().toString());
        this.setType(bookType.getType());
        this.setBook(bookType.getBook().toString());
        this.setSerialreal(bookType.getSerialReal());
        this.setSerialcode(bookType.getSerialCode());
        this.setDecription(bookType.getDescription());
        this.setStatus((bookType.getStatus() == null) ? "" : String.valueOf(bookType.getStatus()));
        this.setPagewith((bookType.getPageWidth() == null) ? "" : String.valueOf(bookType.getPageWidth()));
        this.setPageheight((bookType.getPageHeight() == null) ? "" : String.valueOf(bookType.getPageHeight()));
        this.setRowspacing((bookType.getRowSpacing() == null) ? "" : String.valueOf(bookType.getRowSpacing()));
        this.setMaxrow((bookType.getMaxRow() == null) ? "" : String.valueOf(bookType.getMaxRow()));
        this.setInvoiceType(bookType.getInvoiceType());
    }
}
