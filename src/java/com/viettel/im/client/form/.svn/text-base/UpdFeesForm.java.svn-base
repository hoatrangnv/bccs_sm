/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import java.util.Calendar;
import java.util.Date;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author tuan
 */
public class UpdFeesForm extends ActionForm {

    private Long commTransId;
    private Long payTypeCode; //channelType
    private Long shopId;
    private String shopCode;
    private String shopName;
    private String inputType; //kieu nhap du lieu (nhap tay so luong, nhap tay so tien)
    private Long itemId; //khoan muc
    private Long count; //so luong/ tong tien
    private String itemDate;

    public UpdFeesForm() {
        resetForm();
    }

    public void resetForm() {
        commTransId = 0L;
        payTypeCode = 0L;
        shopId = 0L;
        shopCode = "";
        shopName = "";
        inputType = "";
        itemId= 0L;
        count = 0L;
        try {
//            itemDate = DateTimeUtils.convertDateToString(new Date());

            Calendar calendarBillCycle = Calendar.getInstance();            
            Date tmp = new Date();
            calendarBillCycle.setTime(tmp);
            calendarBillCycle.set(Calendar.DATE, 1); //mac dinh luu chu ky tinh la ngay dau thang
            calendarBillCycle.add(Calendar.MONTH, -1);
            Date firstDateOfBillCycle = calendarBillCycle.getTime();
            
            itemDate = DateTimeUtils.convertDateToString(firstDateOfBillCycle);
        } catch (Exception ex) {
            itemDate = "";
        }
    }

    public Long getCommTransId() {
        return commTransId;
    }

    public void setCommTransId(Long commTransId) {
        this.commTransId = commTransId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getPayTypeCode() {
        return payTypeCode;
    }

    public void setPayTypeCode(Long payTypeCode) {
        this.payTypeCode = payTypeCode;
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

}
