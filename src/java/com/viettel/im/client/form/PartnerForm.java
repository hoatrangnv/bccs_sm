package com.viettel.im.client.form;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Partner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author namnx
 * date 07/03/2009
 *
 */
public class PartnerForm extends ActionForm {

    private long partnerId;
    private String partnerName;
    private String partnerType;
    private String address;
    private String phone;
    private String fax;
//    private String contactName;
    private String staDate;
    private String endDate;
    private String status;
    private String message;
    private String partnerCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public String getContactName() {
//        return contactName;
//    }
//
//    public void setContactName(String contactName) {
//        this.contactName = contactName;
//    }
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
        this.staDate = staDate;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public void resetForm() {
        this.partnerName = "";
        this.partnerType = "";
        this.address = "";
//        this.contactName = "";
        this.fax = "";
        this.partnerCode = "";
        this.message = "";
        this.phone = "";
        this.status = "";
        this.staDate = "";
        this.endDate = "";

    }

    public void copyDataToBO(Partner partner) {

        partner.setPartnerId(this.getPartnerId());
        partner.setPartnerName(this.getPartnerName().trim());
//            partner.setContactName(this.getContactName().trim());
        if (this.getAddress() != null) {
            partner.setAddress(this.getAddress().trim());
        }
        partner.setPartnerCode(this.getPartnerCode().trim());
        if (this.getFax() != null) {
            partner.setFax(this.getFax().trim());
        }
        partner.setPartnerType(Long.parseLong(this.getPartnerType()));
        partner.setStatus(Long.parseLong(this.getStatus()));
        if (this.getPhone() != null) {
            partner.setPhone(this.getPhone().trim());
        }
//            partner.setEndDate(DateTimeUtils.convertStringToDateTime(this.endDate));
//            partner.setStaDate(DateTimeUtils.convertStringToDateTime(this.staDate));

        try {
            partner.setEndDate(DateTimeUtils.convertStringToDate(this.getEndDate()));
            partner.setStaDate(DateTimeUtils.convertStringToDate(this.getStaDate()));
        } catch (Exception ex) {

            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void copyDataFromBO(Partner partner) {
        this.partnerId = partner.getPartnerId();
        this.partnerName = partner.getPartnerName();
        this.partnerType = String.valueOf(partner.getPartnerType());
        this.address = partner.getAddress();
//        this.contactName = partner.getContactName();
        this.partnerCode = partner.getPartnerCode();
        this.fax = partner.getFax();
        this.phone = partner.getPhone();
        this.status = String.valueOf(partner.getStatus());
        try {
            this.endDate = DateTimeUtils.convertDateToString(partner.getEndDate());
            this.staDate = DateTimeUtils.convertDateToString(partner.getStaDate());
        } catch (Exception ex) {
            Logger.getLogger(PartnerForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
