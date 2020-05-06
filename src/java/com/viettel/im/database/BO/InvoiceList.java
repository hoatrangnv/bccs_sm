package com.viettel.im.database.BO;

import java.util.Date;
import com.viettel.im.common.util.StringUtils;

/**
 * InvoiceList entity. @author MyEclipse Persistence Tools
 */
public class InvoiceList implements java.io.Serializable {

    // Fields    
    private Long invoiceListId;
    private Long bookTypeId;
    private String serialNo;
    private String strSerialNo;
    private String blockNo;
    private String strBlockNo;
    private Long fromInvoice;
    private String strFromInvoice;
    private Long toInvoice;
    private String strToInvoice;
    private Long currInvoiceNo;
    private String strCurrInvoiceNo;
    private Long shopId;
    private Long staffId;
    private String userCreated;
    private Date dateCreated;
    private Long status;
    private String userAssign;
    private Date dateAssign;
    private BookType bookType;
    private Long lastUpdateUserId; //user_id cuoi cung tac dong
    private String lastUpdateUserName; //user_name cuoi cung tac dong
    private Date lastUpdateTime; //date_time cuoi cung tac dong

    // Constructors
    /** default constructor */
    public InvoiceList() {
    }

    /** minimal constructor */
    public InvoiceList(Long invoiceListId, String serialNo, String blockNo) {
        this.invoiceListId = invoiceListId;
        this.serialNo = serialNo;
        this.blockNo = blockNo;
    }

    /** full constructor */
    public InvoiceList(Long invoiceListId, Long bookTypeId, String serialNo, String blockNo,
            Long fromInvoice, Long toInvoice, Long currInvoiceNo, Long shopId, String userCreated,
            Date dateCreated, Long status, String userAssign, Date dateAssign) {
        this.invoiceListId = invoiceListId;
        this.bookTypeId = bookTypeId;
        this.serialNo = serialNo;
        this.blockNo = blockNo;
        this.fromInvoice = fromInvoice;
        this.toInvoice = toInvoice;
        this.currInvoiceNo = currInvoiceNo;
        this.shopId = shopId;
        this.userCreated = userCreated;
        this.dateCreated = dateCreated;
        this.status = status;
        this.userAssign = userAssign;
        this.dateAssign = dateAssign;
    }

    public String getStrBlockNo() {
        return strBlockNo;
    }

    public void setStrBlockNo(String strBlockNo) {
        this.strBlockNo = strBlockNo;

    }

    public String getStrSerialNo() {
        return strSerialNo;
    }

    public void setStrSerialNo(String strSerialNo) {
        this.strSerialNo = strSerialNo;
    }

    // Property accessors
    public Date getDateAssign() {
        return dateAssign;
    }

    public void setDateAssign(Date dateAssign) {
        this.dateAssign = dateAssign;
    }

    public String getUserAssign() {
        return userAssign;
    }

    public void setUserAssign(String userAssign) {
        this.userAssign = userAssign;
    }

    public Long getInvoiceListId() {
        return this.invoiceListId;
    }

    public void setInvoiceListId(Long invoiceListId) {
        this.invoiceListId = invoiceListId;
    }

    public Long getBookTypeId() {
        return this.bookTypeId;
    }

    public void setBookTypeId(Long bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBlockNo() {
        return this.blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
        this.strBlockNo = StringUtils.standardBlockNoString(this.blockNo);
    }

    public Long getFromInvoice() {
        return this.fromInvoice;
    }

    public void setFromInvoice(Long fromInvoice) {
        this.fromInvoice = fromInvoice;
        this.strFromInvoice = StringUtils.standardInvoiceString(this.fromInvoice);
    }

    public Long getToInvoice() {
        return this.toInvoice;
    }

    public void setToInvoice(Long toInvoice) {
        this.toInvoice = toInvoice;
        this.strToInvoice = StringUtils.standardInvoiceString(this.toInvoice);
    }

    public Long getCurrInvoiceNo() {
        return this.currInvoiceNo;
    }

    public void setCurrInvoiceNo(Long currInvoiceNo) {
        this.currInvoiceNo = currInvoiceNo;
        this.strCurrInvoiceNo = StringUtils.standardInvoiceString(this.currInvoiceNo);
    }

    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
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

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
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

    public String getStrCurrInvoiceNo() {
        return strCurrInvoiceNo;
    }

    public void setStrCurrInvoiceNo(String strCurrInvoiceNo) {
        this.strCurrInvoiceNo = strCurrInvoiceNo;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    public void setLastUpdateUserId(Long lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public String getLastUpdateUserName() {
        return lastUpdateUserName;
    }

    public void setLastUpdateUserName(String lastUpdateUserName) {
        this.lastUpdateUserName = lastUpdateUserName;
    }

    
}
