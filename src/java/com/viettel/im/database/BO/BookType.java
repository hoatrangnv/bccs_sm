package com.viettel.im.database.BO;

/**
 * BookType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class BookType implements java.io.Serializable {

    // Fields
    private Long bookTypeId;
    private Long numInvoice;
    private String blockName;
    private Long lengthName;
    private Long lengthInvoice;
    private String type;
    private Long book;
    private String serialReal;
    private String serialCode;
    private String description;
    private Long status;
    private Long pageWidth;
    private Long pageHeight;
    private Long rowSpacing;
    private Long maxRow;
    private Long invoiceType;

    // Constructors
    /** default constructor */
    public BookType() {
    }

    /** full constructor */
    public BookType(Long bookTypeId, Long numInvoice, String blockName,
            Long lengthName, Long lengthInvoice, String type, Long book,
            String serialReal, String serialCode, String description, Long status) {
        this.bookTypeId = bookTypeId;
        this.numInvoice = numInvoice;
        this.blockName = blockName;
        this.lengthName = lengthName;
        this.lengthInvoice = lengthInvoice;
        this.type = type;
        this.book = book;

        this.serialReal = serialReal;
        this.serialCode = serialCode;
        this.description = description;
        this.status = status;
    }

    public Long getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(Long maxRow) {
        this.maxRow = maxRow;
    }

    public Long getRowSpacing() {
        return rowSpacing;
    }

    public void setRowSpacing(Long rowSpacing) {
        this.rowSpacing = rowSpacing;
    }
    // Property accessors
    public Long getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(Long pageHeight) {
        this.pageHeight = pageHeight;
    }

    public Long getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(Long pageWidth) {
        this.pageWidth = pageWidth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getSerialReal() {
        return serialReal;
    }

    public void setSerialReal(String serialReal) {
        this.serialReal = serialReal;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getBookTypeId() {
        return this.bookTypeId;
    }

    public void setBookTypeId(Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public Long getNumInvoice() {
        return this.numInvoice;
    }

    public void setNumInvoice(Long numInvoice) {
        this.numInvoice = numInvoice;
    }

    public String getBlockName() {
        return this.blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public Long getLengthName() {
        return this.lengthName;
    }

    public void setLengthName(Long lengthName) {
        this.lengthName = lengthName;
    }

    public Long getLengthInvoice() {
        return this.lengthInvoice;
    }

    public void setLengthInvoice(Long lengthInvoice) {
        this.lengthInvoice = lengthInvoice;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBook() {
        return this.book;
    }

    public void setBook(Long book) {
        this.book = book;
    }

    public Long getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Long invoiceType) {
        this.invoiceType = invoiceType;
    }
}