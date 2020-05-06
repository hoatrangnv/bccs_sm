/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

import com.viettel.im.client.bean.PstnDistrictCodeViewHelper;
import com.viettel.im.common.util.ValidateUtils;
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author TungTV
 */
public class ImportPSTNForm extends ValidatorForm {

    private String location;
    private String stockPstnHeader;
    private String startStockPstn;
    private String endStockPstn;
    private String startPortPstn;
    private String endPortPstn;
    private String pstnModule;
    private String pstnRack;
    private String pstnShelf;
    private String pstnSlot;
    private String pstnDas;
    private String pstnDeviceType;
    private String stockPstnDistrict;
    private String stockPstnNumber;
    private String status;
    private String dluId;
    private String pathLogFile;
    private File impFile;
    private String serverFileName;
    private String clientFileName;
    private String isdn;
    private String port;
    private String error;
    private String dslamcod;
    private String servicesType;
    private static int maxLengthHeader = 3;
    private static int maxLengthNumber = 4;
    private static int maxLength = 10;
    private static final String PSTN_DISTRICT_CODE_VIEWHELPER = "pstnDistrictCodeViewHelper";
    private String provinceCode;
    private String provinceName;
    private String dslamCode;
    private String dslamName;
    private String pathExpFile;
    String[] selectedItems;
    private String isdnType;

    public String getIsdnType() {
        return isdnType;
    }

    public void setIsdnType(String isdnType) {
        this.isdnType = isdnType;
    }

    public String[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(String[] selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getPathExpFile() {
        return pathExpFile;
    }

    public void setPathExpFile(String pathExpFile) {
        this.pathExpFile = pathExpFile;
    }

    /**
     *
     * author tamdt1
     * date: 07/05/2009
     *
     */
    public ImportPSTNForm() {
    }

    public void resetForm() {
        location = "";
        stockPstnHeader = "";
        startStockPstn = "";
        endStockPstn = "";
        startPortPstn = "";
        endPortPstn = "";
        pstnModule = "";
        pstnRack = "";
        pstnShelf = "";
        pstnSlot = "";
        pstnDas = "";
        pstnDeviceType = "";
        stockPstnDistrict = "";
        stockPstnNumber = "";
        status = "";
        dluId = "";
        pathLogFile = "";
        isdn = "";
        port = "";
        error = "";
        dslamcod = "";

        //
        serverFileName = "";
        clientFileName = "";

        provinceCode = "";
        provinceName = "";
        dslamCode = "";
        dslamName = "";

    }

    public String getDslamCode() {
        return dslamCode;
    }

    public void setDslamCode(String dslamCode) {
        this.dslamCode = dslamCode;
    }

    public String getDslamName() {
        return dslamName;
    }

    public void setDslamName(String dslamName) {
        this.dslamName = dslamName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getServicesType() {
        return servicesType;
    }

    public void setServicesType(String servicesType) {
        this.servicesType = servicesType;
    }

    public String getEndStockPstn() {
        return endStockPstn;
    }

    public void setEndStockPstn(String endStockPstn) {
        this.endStockPstn = endStockPstn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEndPortPstn() {
        return endPortPstn;
    }

    public void setEndPortPstn(String endPortPstn) {
        this.endPortPstn = endPortPstn;
    }

    public String getPstnDas() {
        return pstnDas;
    }

    public void setPstnDas(String pstnDas) {
        this.pstnDas = pstnDas;
    }

    public String getPstnDeviceType() {
        return pstnDeviceType;
    }

    public void setPstnDeviceType(String pstnDeviceType) {
        this.pstnDeviceType = pstnDeviceType;
    }

    public String getPstnModule() {
        return pstnModule;
    }

    public void setPstnModule(String pstnModule) {
        this.pstnModule = pstnModule;
    }

    public String getPstnRack() {
        return pstnRack;
    }

    public void setPstnRack(String pstnRack) {
        this.pstnRack = pstnRack;
    }

    public String getPstnShelf() {
        return pstnShelf;
    }

    public void setPstnShelf(String pstnShelf) {
        this.pstnShelf = pstnShelf;
    }

    public String getPstnSlot() {
        return pstnSlot;
    }

    public void setPstnSlot(String pstnSlot) {
        this.pstnSlot = pstnSlot;
    }

    public String getStartPortPstn() {
        return startPortPstn;
    }

    public void setStartPortPstn(String startPortPstn) {
        this.startPortPstn = startPortPstn;
    }

    public String getStartStockPstn() {
        return startStockPstn;
    }

    public void setStartStockPstn(String startStockPstn) {
        this.startStockPstn = startStockPstn;
    }

    public String getStockPstnHeader() {
        return stockPstnHeader;
    }

    public void setStockPstnHeader(String stockPstnHeader) {
        this.stockPstnHeader = stockPstnHeader;
    }

    public String getStockPstnDistrict() {
        return stockPstnDistrict;
    }

    public void setStockPstnDistrict(String stockPstnDistrict) {
        this.stockPstnDistrict = stockPstnDistrict;
    }

    public String getStockPstnNumber() {
        return stockPstnNumber;
    }

    public void setStockPstnNumber(String stockPstnNumber) {
        this.stockPstnNumber = stockPstnNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDluId() {
        return dluId;
    }

    public void setDluId(String dluId) {
        this.dluId = dluId;
    }

    public String getPathLogFile() {
        return pathLogFile;
    }

    public void setPathLogFile(String pathLogFile) {
        this.pathLogFile = pathLogFile;
    }

    public File getImpFile() {
        return impFile;
    }

    public void setImpFile(File impFile) {
        this.impFile = impFile;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDslamcod() {
        return dslamcod;
    }

    public void setDslamcod(String dslamcod) {
        this.dslamcod = dslamcod;
    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        String edit = request.getParameter("edit");
        if (this.location == null || this.location.equals("")) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa chọn tỉnh thành cần gán DSLAM", false));
        }
        if (edit == null) {
            if (this.stockPstnHeader == null || this.stockPstnHeader.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập đầu số cho dải PSTN", false));
            } else {
                if (!ValidateUtils.isMaxLength(stockPstnHeader, maxLengthHeader)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đầu số không dài quá 3 ký tự", false));
                } else if (!ValidateUtils.isInteger(stockPstnHeader)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đầu số phải là số", false));
                }
            }
        }
        boolean validPstn = true;
        if (edit == null) {
            if (this.startStockPstn == null || this.startStockPstn.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Số đầu dải", false));
                validPstn = false;
            } else {
                if (!ValidateUtils.isMaxLength(startStockPstn, maxLengthNumber)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số đầu dải không dài quá 4 ký tự", false));
                    validPstn = false;
                } else if (!ValidateUtils.isInteger(startStockPstn)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số đầu dải phải là số", false));
                    validPstn = false;
                }
            }
            if (this.endStockPstn == null || this.endStockPstn.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Số cuối dải", false));
                validPstn = false;
            } else {
                if (!ValidateUtils.isMaxLength(endStockPstn, maxLengthNumber)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số cuối dải không dài quá 4 ký tự", false));
                    validPstn = false;
                } else if (!ValidateUtils.isInteger(startStockPstn)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số cuối dải phải là số", false));
                    validPstn = false;
                }
            }
        } else {
            if (this.stockPstnNumber == null || this.stockPstnNumber.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Số PSTN", false));
                validPstn = false;
            } else {
                if (!ValidateUtils.isMaxLength(stockPstnNumber, maxLengthNumber + maxLengthHeader)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường SSố PSTN không dài quá 7 ký tự", false));
                    validPstn = false;
                } else if (!ValidateUtils.isInteger(stockPstnNumber)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số PSTN phải là số", false));
                    validPstn = false;
                }
            }
        }
        if (edit == null) {
            if (validPstn) {
                if (startStockPstn.length() != endStockPstn.length()) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số đầu dải và Số cuối dải không cùng độ dài", false));
                    validPstn = false;
                }
            }
        }
        boolean validPort = true;
        if (edit == null) {
            if (this.startPortPstn == null || this.startPortPstn.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Từ thiết bị/port", false));
                validPort = false;
            } else {
                if (!ValidateUtils.isMaxLength(startPortPstn, maxLength)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Từ thiết bị/port không dài quá 10 ký tự", false));
                    validPort = false;
                } else if (!ValidateUtils.isInteger(startPortPstn)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Từ thiết bị/port phải là số", false));
                    validPort = false;
                }
            }
            if (this.endPortPstn == null || this.endPortPstn.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Đến thiết bị/port", false));
                validPort = false;
            } else {
                if (!ValidateUtils.isMaxLength(endPortPstn, maxLength)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đến thiết bị/port không dài quá 10 ký tự", false));
                    validPort = false;
                } else if (!ValidateUtils.isInteger(endPortPstn)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Đến thiết bị/port phải là số", false));
                    validPort = false;
                }
            }
        } else {
            if (this.startPortPstn == null || this.startPortPstn.equals("")) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Thiết bị/port", false));
                validPort = false;
            } else {
                if (!ValidateUtils.isMaxLength(startPortPstn, maxLength)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Thiết bị/port không dài quá 10 ký tự", false));
                    validPort = false;
                } else if (!ValidateUtils.isInteger(startPortPstn)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Thiết bị/port phải là số", false));
                    validPort = false;
                }
            }
        }
        if (edit == null) {
            if (validPort) {
                if (startPortPstn.length() != endPortPstn.length()) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Số đầu dải và Số cuối dải không cùng độ dài", false));
                    validPort = false;
                }
            }
        }
        if (edit == null) {
            if (validPstn && validPort) {
                Long tempPstnTotal = new Long(endStockPstn) - new Long(startStockPstn);
                Long tempPortTotal = new Long(endPortPstn) - new Long(startPortPstn);
                if (!tempPstnTotal.equals(tempPortTotal)) {
                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Tổng số số PSTN cần đấu nối và tổng số port không bằng nhau", false));
                }
            }
        }
        if (this.pstnModule == null || this.pstnModule.equals("")) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Module", false));
        } else {
            if (!ValidateUtils.isMaxLength(pstnModule, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Module không dài quá 10 ký tự", false));
            } else if (!ValidateUtils.isInteger(pstnModule)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Module phải là số", false));
            }
        }
        if (this.pstnRack == null || this.pstnRack.equals("")) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Rack/DnzSet", false));
        } else {
            if (!ValidateUtils.isMaxLength(pstnRack, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Rack/DnzSet không dài quá 10 ký tự", false));
            } else if (!ValidateUtils.isInteger(pstnRack)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Rack/DnzSet phải là số", false));
            }
        }
        if (this.pstnShelf == null || this.pstnShelf.equals("")) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Shelf/Call Source", false));
        } else {
            if (!ValidateUtils.isMaxLength(pstnShelf, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Shelf/Call Source không dài quá 10 ký tự", false));
            } else if (!ValidateUtils.isInteger(pstnShelf)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Shelf/Call Source phải là số", false));
            }
        }
        if (this.pstnSlot == null || this.pstnSlot.equals("")) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Slot/Charging Source code", false));
        } else {
            if (!ValidateUtils.isMaxLength(pstnSlot, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Slot/Charging Source code không dài quá 10 ký tự", false));
            } else if (!ValidateUtils.isInteger(pstnSlot)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Slot/Charging Source code phải là số", false));
            }
        }
        if (this.pstnDas == null || this.pstnDas.equals("")) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập DAS/Frame", false));
        } else {
            if (!ValidateUtils.isMaxLength(pstnDas, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường DAS/Frame không dài quá 10 ký tự", false));
            } else if (!ValidateUtils.isInteger(pstnDas)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường DAS/Frame phải là số", false));
            }
        }
        if (this.pstnDeviceType == null || this.pstnDeviceType.equals("")) {
            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Bạn chưa nhập Loại thiết bị", false));
        } else {
            if (!ValidateUtils.isMaxLength(pstnDeviceType, maxLength)) {
                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Trường Loại thiết bị không dài quá 10 ký tự", false));
            }
        }

        /** Now we save viewHelper */
        PstnDistrictCodeViewHelper viewHelper = (PstnDistrictCodeViewHelper) request.getSession().getAttribute(PSTN_DISTRICT_CODE_VIEWHELPER);

        if (viewHelper == null) {
            viewHelper = new PstnDistrictCodeViewHelper();
            request.getSession().setAttribute(PSTN_DISTRICT_CODE_VIEWHELPER, viewHelper);
        }
        viewHelper.setStockPreviousPstnNumber(viewHelper.getStockPstnNumber());
        viewHelper.setStockPstnNumber(this.stockPstnNumber);

        viewHelper.setLocation(this.location);
        viewHelper.setStartPortPstn(this.startPortPstn);
        viewHelper.setPstnDas(this.pstnDas);
        viewHelper.setPstnDeviceType(this.pstnDeviceType);
        viewHelper.setPstnModule(this.pstnModule);
        viewHelper.setPstnRack(this.pstnRack);
        viewHelper.setPstnShelf(this.pstnShelf);
        viewHelper.setPstnSlot(this.pstnSlot);

        return errors;
    }
}
