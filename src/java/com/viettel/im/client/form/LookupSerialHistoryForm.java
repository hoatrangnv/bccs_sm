package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author Doan Thanh 8
 */
public class LookupSerialHistoryForm extends ActionForm {
    private Long stockTypeId; //loai hang
    private Long stockModelId; //mat hang
    private String serial; //serial can tim lich su
    private Date fromDate; //tu ngay
    private Date toDate;    //den ngay

    public LookupSerialHistoryForm() {
        resetForm();
    }

    public void resetForm() {
        stockTypeId = Constant.STOCK_KIT;
        stockModelId = 0L;
        serial = "";
        Date now = new Date();
        fromDate = now;
        toDate = now;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
