/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.common.util.ValidateUtils;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author dattv
 */
public class KitIntegrationForm extends ValidatorForm {
    private Long formId = 0L; //id gia cua form

    private String firstSimSerial;
    private String endSimSerial;
    private Long simQuantity; //so luong sim trong dai
    private Long simTotalQuantity = 0L; //tong so luong sim cua tat ca cac dai trong list
    private String serverSimFileName; //ten file tren server trong truogn hop upload du lieu sim
    private String clientSimFileName; //ten file tren client trong truogn hop upload du lieu sim
    private Long impSimType = 1L;; //kieu nhap du lieu sim (theo file hoac theo dai)
    private String startStockIsdn;
    private String endStockIsdn;
    private Long isdnQuantity; //so luong isdn trong dai
    private Long isdnTotalQuantity = 0L; //so luong isdn cua tat ca cac dai trong list
    private String serverIsdnFileName; //ten file tren server trong truogn hop upload du lieu so
    private String clientIsdnFileName; //ten file tren client trong truogn hop upload du lieu so
    private Long impIsdnType = 1L;; //kieu nhap du lieu so (theo file hoac theo dai)
    

    private String simType;
    private String serviceType;
    private String location;
    private String stockIsdnHeader;
    private String stockIsdnStatus;


    /* Do dai lon nhat cua truong */
    private static int maxLengthStockIsdn = 12;


    /* Do dai lon nhat cua truong */
    private static int maxLengthIsdnHeader = 4;


    private static final Long HOMEPHONE_STOCK_TYPE_ID = 2L;


    public String getEndSimSerial() {
        return endSimSerial;
    }

    public void setEndSimSerial(String endSimSerial) {
        this.endSimSerial = endSimSerial;
    }

    public String getFirstSimSerial() {
        return firstSimSerial;
    }

    public void setFirstSimSerial(String firstSimSerial) {
        this.firstSimSerial = firstSimSerial;
    }

    public String getSimType() {
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEndStockIsdn() {
        return endStockIsdn;
    }

    public void setEndStockIsdn(String endStockIsdn) {
        this.endStockIsdn = endStockIsdn;
    }

    public String getStartStockIsdn() {
        return startStockIsdn;
    }

    public void setStartStockIsdn(String startStockIsdn) {
        this.startStockIsdn = startStockIsdn;
    }

    public String getStockIsdnHeader() {
        return stockIsdnHeader;
    }

    public void setStockIsdnHeader(String stockIsdnHeader) {
        this.stockIsdnHeader = stockIsdnHeader;
    }

    public String getStockIsdnStatus() {
        return stockIsdnStatus;
    }

    public void setStockIsdnStatus(String stockIsdnStatus) {
        this.stockIsdnStatus = stockIsdnStatus;
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
            ActionErrors errors = new ActionErrors();
            boolean valid = true;
            String action = request.getParameter("actionSearch");
            request.getSession().setAttribute("actionSearch", action);//Where to display errors
            if (action !=null && action.equals("0")) {
                if (this.simType == null || this.simType.equals("")) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Loại sim", false));
                    valid = false;
                }
            
                if (this.firstSimSerial == null || this.firstSimSerial.equals("")) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Số serial đầu dải", false));
                    valid = false;
                } else {
                    if (!ValidateUtils.isMaxLength(firstSimSerial, maxLengthStockIsdn)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số serial đầu dải dài quá 12 ký tự", false));
                        valid = false;
                    } else if (!ValidateUtils.isInteger(firstSimSerial)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số serial đầu dải phải là số", false));
                        valid = false;
                    }
                }
                if (this.endSimSerial == null || this.endSimSerial.equals("")) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Số serial cuối dải", false));
                    valid = false;
                } else {
                    if (!ValidateUtils.isMaxLength(firstSimSerial, maxLengthStockIsdn)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số serial cuối dải quá 12 ký tự", false));
                        valid = false;
                    } else if (!ValidateUtils.isInteger(endSimSerial)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số serial cuối dải phải là số", false));
                        valid = false;
                    }
                }
                if (valid) {
                    if (this.endSimSerial.length() != this.firstSimSerial.length()) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số serial đầu dải và serial cuối dải không cùng độ dài", false));
                        valid = false;
                    }
                }
            }
            if (action !=null && action.equals("1")) {
                if (this.serviceType == null || this.serviceType.equals("")) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Loại dịch vụ", false));
                    valid = false;
                }
                if (valid) {
                    if(this.serviceType.equals(HOMEPHONE_STOCK_TYPE_ID.toString())) {
                        if (this.location == null || this.location.equals("")) {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Địa bàn", false));
                        }
                    }
                }
                if (this.stockIsdnHeader == null || this.stockIsdnHeader.equals("")) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Đầu số", false));
                    valid = false;
                } else {
                    if (!ValidateUtils.isMaxLength(stockIsdnHeader, maxLengthIsdnHeader)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đầu số không dài quá 4 ký tự", false));
                        valid = false;
                    } else if (!ValidateUtils.isInteger(stockIsdnHeader)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đầu số phải là số", false));
                        valid = false;
                    }
                }
                if (this.startStockIsdn == null || this.startStockIsdn.equals("")) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Số đầu dải", false));
                    valid = false;
                } else {
                    if (!ValidateUtils.isMaxLength(startStockIsdn, maxLengthStockIsdn)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số đầu dải dài quá 6 ký tự", false));
                        valid = false;
                    } else if (!ValidateUtils.isInteger(startStockIsdn)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số đầu dải phải là số", false));
                        valid = false;
                    }
                }
                if (this.endStockIsdn == null || this.endStockIsdn.equals("")) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn Số cuối dải", false));
                    valid = false;
                } else {
                    if (!ValidateUtils.isMaxLength(endStockIsdn, maxLengthStockIsdn)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số cuối dải dài quá 6 ký tự", false));
                        valid = false;
                    } else if (!ValidateUtils.isInteger(endStockIsdn)) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số cuối dải phải là số", false));
                        valid = false;
                    }
                }
                if (valid) {
                    if (startStockIsdn.length() != endStockIsdn.length()) {
                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số đầu dải và Số cuối dải không cùng số chữ số", false));
                    } else {
                        Long tempStartStockIsdn = new Long(startStockIsdn);
                        Long tempEndStockIsdn = new Long(endStockIsdn);
                        if (tempStartStockIsdn > tempEndStockIsdn) {
                            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Số đầu dải đến Số cuối dải không phù hợp", false));
                        }
                    }
                }
            }
            return errors;
    }

    public Long getIsdnQuantity() {
        return isdnQuantity;
    }

    public void setIsdnQuantity(Long isdnQuantity) {
        this.isdnQuantity = isdnQuantity;
    }

    public Long getSimQuantity() {
        return simQuantity;
    }

    public void setSimQuantity(Long simQuantity) {
        this.simQuantity = simQuantity;
    }

    public Long getIsdnTotalQuantity() {
        return isdnTotalQuantity;
    }
    public void setIsdnTotalQuantity(Long isdnTotalQuantity) {
        this.isdnTotalQuantity = isdnTotalQuantity;
    }
    public Long getSimTotalQuantity() {
        return simTotalQuantity;
    }
    public void setSimTotalQuantity(Long simTotalQuantity) {
        this.simTotalQuantity = simTotalQuantity;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getClientIsdnFileName() {
        return clientIsdnFileName;
    }

    public void setClientIsdnFileName(String clientIsdnFileName) {
        this.clientIsdnFileName = clientIsdnFileName;
    }

    public String getClientSimFileName() {
        return clientSimFileName;
    }

    public void setClientSimFileName(String clientSimFileName) {
        this.clientSimFileName = clientSimFileName;
    }

    public String getServerIsdnFileName() {
        return serverIsdnFileName;
    }

    public void setServerIsdnFileName(String serverIsdnFileName) {
        this.serverIsdnFileName = serverIsdnFileName;
    }

    public String getServerSimFileName() {
        return serverSimFileName;
    }

    public void setServerSimFileName(String serverSimFileName) {
        this.serverSimFileName = serverSimFileName;
    }

    public Long getImpIsdnType() {
        return impIsdnType;
    }

    public void setImpIsdnType(Long impIsdnType) {
        this.impIsdnType = impIsdnType;
    }

    public Long getImpSimType() {
        return impSimType;
    }

    public void setImpSimType(Long impSimType) {
        this.impSimType = impSimType;
    }

}
