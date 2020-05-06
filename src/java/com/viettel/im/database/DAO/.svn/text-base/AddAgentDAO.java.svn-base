/**
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.im.database.DAO;

import com.viettel.anypay.util.DataUtil;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.AddAgentForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.ValidateUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.ErrorChangeChannelBean;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VsaUser;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author haint
 */
public class AddAgentDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AddAgentDAO.class);
    private final String ADD_AGENT_BY_FILE = "addAgentByFile";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String LIST_AGENT_IMPORT = "listAgentImport";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private String pageForward;
    private AddAgentForm addAgentForm = new AddAgentForm();

    public AddAgentForm getAddAgentForm() {
        return addAgentForm;
    }

    public void setAddAgentForm(AddAgentForm addAgentForm) {
        this.addAgentForm = addAgentForm;
    }

    public String prapareAddAgentByFile() {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

//        if (channelForm == null) {
//            channelForm = new ChannelForm();
//        }
        removeTabSession(LIST_AGENT_IMPORT);
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByObjectTypeAndIsVtUnitAndIsPrivate(Constant.OBJECT_TYPE_SHOP, Constant.IS_NOT_VT_UNIT, Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);
        setTabSession(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        pageForward = ADD_AGENT_BY_FILE;
        return pageForward;
    }

    public String viewImportAgentFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(addAgentForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = LIST_AGENT_IMPORT;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

//        if (addAgentForm.getChangeType() == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
//            return pageForward;
//        }
//        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
//        ChannelType objectType = channelTypeDAO.findById(addAgentForm.getChangeType());
//        if (objectType == null || objectType.getChannelTypeId() == null
//                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)
//                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
//            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
//            return pageForward;
//        }

        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        Workbook workbook = Workbook.getWorkbook(impFile);
        Sheet sheet = workbook.getSheet(0);
        int numberRow = sheet.getRows();
        numberRow = numberRow - 1; //tru di dong dau tien
        List<AddAgentForm> listRowInFile = new ArrayList<AddAgentForm>();
        if (numberRow > 1000) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.139");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.140");
            List listParams = new ArrayList<String>();
            listParams.add(String.valueOf(numberRow));
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParams);
        }
        numberRow = numberRow > 1000 ? 1000 : numberRow; //truong hop file > 1000 row -> chi hien thi 1000 row dau tien

        //doc du lieu tu file -> day du lieu vao list
        for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
            //doc tat ca cac dong trong sheet
            String parShopCode = sheet.getCell(0, rowIndex).getContents();
            String shopCode = sheet.getCell(1, rowIndex).getContents();
            String shopName = sheet.getCell(2, rowIndex).getContents();
            String tradeName = sheet.getCell(3, rowIndex).getContents();
            String tin = sheet.getCell(4, rowIndex).getContents();
            String account = sheet.getCell(5, rowIndex).getContents();
            String bank = sheet.getCell(6, rowIndex).getContents();
            String contactName = sheet.getCell(7, rowIndex).getContents();
            String contactTitle = sheet.getCell(8, rowIndex).getContents();
            String mail = sheet.getCell(9, rowIndex).getContents();
            String idNo = sheet.getCell(10, rowIndex).getContents();
            String province = sheet.getCell(11, rowIndex).getContents();
            String district = sheet.getCell(12, rowIndex).getContents();
            String precinct = sheet.getCell(13, rowIndex).getContents();
            String address = sheet.getCell(14, rowIndex).getContents();
            String profileState = sheet.getCell(15, rowIndex).getContents();
            String registryDate = sheet.getCell(16, rowIndex).getContents();
            String usefulWidth = sheet.getCell(17, rowIndex).getContents();
            String surfaceArea = sheet.getCell(18, rowIndex).getContents();
            String boardState = sheet.getCell(19, rowIndex).getContents();
            String status = sheet.getCell(20, rowIndex).getContents();
            String checkVAT = sheet.getCell(21, rowIndex).getContents();
            String agentType = sheet.getCell(22, rowIndex).getContents();
            String telNumber = sheet.getCell(23, rowIndex).getContents();
            //haint41 11/02/2012 : them thong tin to,duong,so nha
            String streetBlockName = sheet.getCell(24, rowIndex).getContents();
            String streetName = sheet.getCell(25, rowIndex).getContents();
            String home = sheet.getCell(26, rowIndex).getContents();

            if (((parShopCode
                    == null || parShopCode.trim().equals(""))
                    && (shopCode == null || shopCode.trim().equals(""))
                    && (shopName == null || shopName.trim().equals(""))
                    && (tradeName == null || tradeName.trim().equals(""))
                    && (tin == null || tin.trim().equals(""))
                    && (account == null || account.trim().equals(""))
                    && (bank == null || bank.trim().equals(""))
                    && (contactName == null || contactName.trim().equals(""))
                    && (contactTitle == null || contactTitle.trim().equals(""))
                    && (mail == null || mail.trim().equals(""))
                    && (idNo == null || idNo.trim().equals(""))
                    && (province == null || province.trim().equals(""))
                    && (district == null || district.trim().equals(""))
                    && (precinct == null || precinct.trim().equals(""))
                    && (address == null || address.trim().equals(""))
                    && (profileState == null || profileState.trim().equals(""))
                    && (registryDate == null || registryDate.trim().equals(""))
                    && (usefulWidth == null || usefulWidth.trim().equals(""))
                    && (surfaceArea == null || surfaceArea.trim().equals(""))
                    && (boardState == null || boardState.trim().equals(""))
                    && (status == null || status.trim().equals(""))
                    && (checkVAT == null || checkVAT.trim().equals(""))
                    && (agentType == null || agentType.trim().equals(""))
                    && (telNumber == null || telNumber.trim().equals(""))
                    && (streetBlockName == null || agentType.trim().equals(""))
                    && (streetName == null || agentType.trim().equals(""))
                    && (home == null || agentType.trim().equals("")))) {
                continue;
            }

            AddAgentForm tmpAddAgentForm = new AddAgentForm();
            tmpAddAgentForm.setParShopCode(parShopCode);
            tmpAddAgentForm.setShopCode(shopCode);
            tmpAddAgentForm.setShopName(shopName);
            tmpAddAgentForm.setTradeName(tradeName);
            tmpAddAgentForm.setTin(tin);
            tmpAddAgentForm.setAccount(account);
            tmpAddAgentForm.setBank(bank);
            tmpAddAgentForm.setContactName(contactName);
            tmpAddAgentForm.setContactTitle(contactTitle);
            tmpAddAgentForm.setMail(mail);
            tmpAddAgentForm.setIdNo(idNo);
            tmpAddAgentForm.setProvince(province);
            tmpAddAgentForm.setDistrict(district);
            tmpAddAgentForm.setPrecinct(precinct);
            tmpAddAgentForm.setAddress(address);
            tmpAddAgentForm.setProfileState(profileState);
            tmpAddAgentForm.setRegistryDate(registryDate);
            tmpAddAgentForm.setUsefulWidth(usefulWidth);
            tmpAddAgentForm.setSurfaceArea(surfaceArea);
            tmpAddAgentForm.setBoardState(boardState);
            tmpAddAgentForm.setStatus(status);
            tmpAddAgentForm.setCheckVAT(checkVAT);
            tmpAddAgentForm.setAgentType(agentType);
            tmpAddAgentForm.setTelNumber(telNumber);
            tmpAddAgentForm.setStreetBlockName(streetBlockName);
            tmpAddAgentForm.setStreetName(streetName);
            tmpAddAgentForm.setHome(home);
            listRowInFile.add(tmpAddAgentForm);
        }
        setTabSession(LIST_AGENT_IMPORT, listRowInFile);
        return pageForward;
    }

    public String addAgentByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(addAgentForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = ADD_AGENT_BY_FILE;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (addAgentForm.getChangeType() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Error. Pls select Channel Type!");
            return pageForward;
        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
        ChannelType objectType = channelTypeDAO.findById(addAgentForm.getChangeType());
        if (objectType == null || objectType.getChannelTypeId() == null
                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)
                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_NOT_VT_UNIT)) {
            req.setAttribute(REQUEST_MESSAGE, getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
            return pageForward;
        }

        /* LAMNV ADD START 17/05/2012.*/
        Long defaultRole = null;
        AppParamsDAO appParamDAO = new AppParamsDAO();
        appParamDAO.setSession(getSession());
        AppParams appParam = appParamDAO.findAppParams("CHANNEL_DEFAULT_ROLE", DataUtil.safeToString(objectType.getChannelTypeId()));

        try {
            defaultRole = Long.parseLong(appParam.getValue());
        } catch (Exception ex) {
            defaultRole = null;
        }

        if (appParam == null || defaultRole == null) {
            req.setAttribute(REQUEST_MESSAGE, getText("ERR.STAFF.0047"));
            prapareAddAgentByFile();

            return pageForward;
        }
        /* LAMNV END START 17/05/2012.*/


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        Workbook workbook = Workbook.getWorkbook(impFile);
        Sheet sheet = workbook.getSheet(0);
        int numberRow = sheet.getRows();
        numberRow = numberRow - 1; //tru di dong dau tien

        Long count = 0L;
        Long total = 0L;
        Map<String, Shop> shopCodeHashMap = new HashMap<String, Shop>();
        List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        ChannelDAO channelDAO = new ChannelDAO();
        for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
            if (((sheet.getCell(0, rowIndex) == null || sheet.getCell(0, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(1, rowIndex).getContents() == null || sheet.getCell(1, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(2, rowIndex).getContents() == null || sheet.getCell(2, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(3, rowIndex).getContents() == null || sheet.getCell(3, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(4, rowIndex).getContents() == null || sheet.getCell(4, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(5, rowIndex).getContents() == null || sheet.getCell(5, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(6, rowIndex).getContents() == null || sheet.getCell(6, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(7, rowIndex).getContents() == null || sheet.getCell(7, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(8, rowIndex).getContents() == null || sheet.getCell(8, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(9, rowIndex).getContents() == null || sheet.getCell(9, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(10, rowIndex).getContents() == null || sheet.getCell(10, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(11, rowIndex).getContents() == null || sheet.getCell(11, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(12, rowIndex).getContents() == null || sheet.getCell(12, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(13, rowIndex).getContents() == null || sheet.getCell(13, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(14, rowIndex).getContents() == null || sheet.getCell(14, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(15, rowIndex).getContents() == null || sheet.getCell(15, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(16, rowIndex).getContents() == null || sheet.getCell(16, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(17, rowIndex).getContents() == null || sheet.getCell(17, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(18, rowIndex).getContents() == null || sheet.getCell(18, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(19, rowIndex).getContents() == null || sheet.getCell(19, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(20, rowIndex).getContents() == null || sheet.getCell(20, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(21, rowIndex).getContents() == null || sheet.getCell(21, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(22, rowIndex).getContents() == null || sheet.getCell(22, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(23, rowIndex).getContents() == null || sheet.getCell(23, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(24, rowIndex).getContents() == null || sheet.getCell(24, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(25, rowIndex).getContents() == null || sheet.getCell(25, rowIndex).getContents().trim().equals(""))
                    && (sheet.getCell(26, rowIndex).getContents() == null || sheet.getCell(26, rowIndex).getContents().trim().equals("")))) {
                continue;
            }
            total++;

            String parShopCode = sheet.getCell(0, rowIndex).getContents();
            String shopCode = sheet.getCell(1, rowIndex).getContents();
            String shopName = sheet.getCell(2, rowIndex).getContents();
            String tradeName = sheet.getCell(3, rowIndex).getContents();
            String tin = sheet.getCell(4, rowIndex).getContents();
            String account = sheet.getCell(5, rowIndex).getContents();
            String bank = sheet.getCell(6, rowIndex).getContents();
            String contactName = sheet.getCell(7, rowIndex).getContents();
            String contactTitle = sheet.getCell(8, rowIndex).getContents();
            String mail = sheet.getCell(9, rowIndex).getContents();
            String idNo = sheet.getCell(10, rowIndex).getContents();
            String province = sheet.getCell(11, rowIndex).getContents();
            String district = sheet.getCell(12, rowIndex).getContents();
            String precinct = sheet.getCell(13, rowIndex).getContents();

            //haint41 11/02/2012 : them thong tin to,duong,so nha
            String streetBlockName = sheet.getCell(14, rowIndex).getContents();
            String streetName = sheet.getCell(15, rowIndex).getContents();
            String home = sheet.getCell(16, rowIndex).getContents();

            String address = sheet.getCell(17, rowIndex).getContents();
            String profileState = sheet.getCell(18, rowIndex).getContents();
            String registryDate = sheet.getCell(19, rowIndex).getContents();
            String usefulWidth = sheet.getCell(20, rowIndex).getContents();
            String surfaceArea = sheet.getCell(21, rowIndex).getContents();
            String boardState = sheet.getCell(22, rowIndex).getContents();
            String status = sheet.getCell(23, rowIndex).getContents();
            String checkVAT = sheet.getCell(24, rowIndex).getContents();
            String agentType = sheet.getCell(25, rowIndex).getContents();
            String telNumber = sheet.getCell(26, rowIndex).getContents();


            String error = "";

            Shop parentShop = null;
            if (parShopCode == null || parShopCode.trim().equals("")) {
                error += ";" + getText("ERR.CHL.128");
            }
            if (parShopCode != null && !parShopCode.equals("")) {
                parShopCode = parShopCode.trim();
                parentShop = channelDAO.getShop(parShopCode);
            }

            if (parShopCode != null && !parShopCode.equals("") && parentShop == null) {
                error += ";" + getText("ERR.CHL.094");
            }
            if (parentShop != null && (parentShop.getStatus() == null || parentShop.getStatus().equals(0L))) {
//                error = "Mã cửa hàng cha đang ở trạng thái ngưng hoạt động";
                error += ";" + getText("ERR.CHL.095");
            }
            if (parentShop != null && (parentShop.getProvince() == null || parentShop.getProvince().equals(""))) {
//                error = "Mã cửa hàng cha đang ở trạng thái ngưng hoạt động";
//                error += ";" + getText("Error. Province of Parent shop is null!");
                error += ";" + getText("ERR.AGENT.014");
            }

            if (shopName == null || shopName.equals("")) {
//                error += ";" + getText("Error. Shop name is null!");
                error += ";" + getText("ERR.AGENT.015");
            } else {
                shopName = shopName.trim();
            }

            if (tradeName == null || tradeName.trim().equals("")) {
//                error += ";" + getText("Error. TradeName is null!");
                error += ";" + getText("ERR.AGENT.016");
            }
            if (tradeName != null) {
                tradeName = tradeName.trim();
            }

            if (contactName == null || contactName.trim().equals("")) {
//                error += ";" + getText("Error. ContactName is null!");
                error += ";" + getText("ERR.AGENT.017");
            }
            if (contactName != null) {
                contactName = contactName.trim();
            }

            if (idNo == null || idNo.trim().equals("")) {
//                error += ";" + getText("Error. IdNo is null!");
                error += ";" + getText("ERR.AGENT.018");
            }
            if (idNo != null) {
                idNo = idNo.trim();
            }

            if (province == null || province.trim().equals("")) {
//                error += ";" + getText("Error. Province is null!");
                error += ";" + getText("ERR.AGENT.019");
            }

            Area provinceArea = null;
            if (province != null && !province.equals("")) {
                province = province.trim();
                provinceArea = channelDAO.getArea(province);
            }
            if (provinceArea == null || provinceArea.getProvince() == null) {
//                error += ";" + getText("Error. Province is invalid!");
                error += ";" + getText("ERR.AGENT.020");
            }
            if (provinceArea != null && !provinceArea.getProvince().equals("") && parentShop != null && parentShop.getProvince() != null && !parentShop.getProvince().equals("")
                    && !provinceArea.getProvince().equals(parentShop.getProvince())
                    && parentShop.getParentShopId() != null) {
//                error += ";" + getText("Error. Province diffirence from Provicne of Shop!");
                error += ";" + getText("ERR.AGENT.021");
            }

            if (parentShop != null) {
                if (!channelDAO.checkShopUnder(userToken.getShopId(), parentShop.getShopId())) {
//                    error = "Mã cửa hàng cha không phải mã con của cửa hàng user đăng nhập";
                    error += ";" + getText("ERR.CHL.098");
                }
            }

            //if shop is branch; check exist shop code
//            if (parentShop != null && parentShop.getParentShopId() == null
//                    && provinceArea != null && provinceArea.getProvince() != null) {
//                Shop checkShop = channelDAO.getShop(provinceArea.getProvince() + objectType.getPrefixObjectCode());
//                if (checkShop != null) {
//                    error += ";" + getText("Error. Shop code is exist!");
//                }
//            }
            if (district == null || district.trim().equals("")) {
//                error += ";" + getText("Error. District is null!");
                error += ";" + getText("ERR.AGENT.022");
            }

            Area districtArea = null;
            if (district != null && !district.equals("")) {
                district = district.trim();
                districtArea = channelDAO.getArea(province + district);
            }
            if (districtArea == null || districtArea.getDistrict() == null) {
//                error += ";" + getText("Error. District is invalid!");
                error += ";" + getText("ERR.AGENT.023");
            }

            if (precinct == null || precinct.trim().equals("")) {
//                error += ";" + getText("Error. Precinct is null!");
                error += ";" + getText("ERR.AGENT.024");
            }

            Area precinctArea = null;
            if (precinct != null && !precinct.equals("")) {
                precinct = precinct.trim();
                precinctArea = channelDAO.getArea(province + district + precinct);
            }
            if (precinctArea == null || precinctArea.getPrecinct() == null) {
//                error += ";" + getText("Error. District is invalid!");
                error += ";" + getText("ERR.AGENT.025");
            }

            if (profileState == null || profileState.trim().equals("")) {
//                error += ";" + getText("Error. ProfileState is null!");
                error += ";" + getText("ERR.AGENT.026");
            }
            if (profileState != null) {
                profileState = profileState.trim();
                try {
                    Long checkProfileState = Long.parseLong(profileState);
                    if (checkProfileState < 0 || checkProfileState > 2) {
                        error += ";" + getText("ERR.STAFF.0020");
                    }
                } catch (Exception e) {
                    error += ";" + getText("ERR.STAFF.0020");
                }
            }

            if (registryDate == null || registryDate.trim().equals("")) {
//                error += ";" + getText("Error. RegistryDate is null!");
                error += ";" + getText("ERR.AGENT.028");
            }
            if (registryDate != null) {
                registryDate = registryDate.trim();
                try {
                    Date checkRegistryDate = DateTimeUtils.convertStringToDateTimeVunt(registryDate);
                    if (!checkValidBirthDate(checkRegistryDate)) {
                        error += ";" + getText("ERR.AGENT.029");//Error. Registry date is invalid!
                    }
                } catch (Exception e) {
//                    error += ";" + getText("Error. registryDate column is in wrong format!");
                    error += ";" + getText("ERR.AGENT.029");
                }
            }

            if (status == null || status.trim().equals("")) {
//                error += ";" + getText("Error. status is null!");
                error += ";" + getText("ERR.AGENT.030");
            }
            if (status != null) {
                status = status.trim();
                try {
                    Long checkStatus = Long.parseLong(status);
                    if (checkStatus < 0 || checkStatus > 2) {
                        error += ";" + getText("ERR.STAFF.0022");
                    }
                } catch (Exception e) {
                    error += ";" + getText("ERR.AGENT.031");
                }

            }

            if (checkVAT != null) {
                checkVAT = checkVAT.trim();
                try {
                    Long validateVAT = Long.valueOf(checkVAT);
                    if (validateVAT < 0 || validateVAT > 1) {
                        error += ";" + getText("ERR.STAFF.0023");
                    }
                } catch (Exception e) {
//                    error += ";" + getText("Error. checkVAT column is in wrong format!");
                    error += ";" + getText("ERR.AGENT.032");
                }
            }

            if (telNumber == null || telNumber.trim().equals("")) {
//                error += ";" + getText("Error. telNumber is null!");
                error += ";" + getText("ERR.AGENT.033");
            }
            if (telNumber != null) {
                telNumber = telNumber.trim();
                if (!ValidateUtils.isInteger(telNumber)) {
//                    error += ";" + getText("Error. telNumber column is in wrong format!");
                    error += ";" + getText("ERR.AGENT.034");
                }
            }

            if (tin != null) {
                tin = tin.trim();
            }
            if (account != null) {
                account = account.trim();
            }
            if (bank != null) {
                bank = bank.trim();
            }

            if (contactTitle != null) {
                contactTitle = contactTitle.trim();
            }
            if (mail != null) {
                mail = mail.trim();
            }

            if (address != null) {
                address = address.trim();
            }

            if (usefulWidth != null) {
                usefulWidth = usefulWidth.trim();
            }
            if (surfaceArea != null) {
                surfaceArea = surfaceArea.trim();
            }
            if ((boardState != null) && (!"".equals(boardState.trim()))) {
                boardState = boardState.trim();
                try {
                    Long.valueOf(boardState);
                } catch (Exception e) {
//                    error += ";" + getText("Error. boardState column is in wrong format!");
                    error += ";" + getText("ERR.AGENT.035");
                }
            }

            if ((agentType != null) && (!"".equals(agentType.trim()))) {
                agentType = agentType.trim();
                try {
                    Long.valueOf(agentType);
                } catch (Exception e) {
//                    error += ";" + getText("Error. agentType column is in wrong format!");
                    error += ";" + getText("ERR.AGENT.036");
                }
            }

            if (streetBlockName != null && !streetBlockName.trim().equals("")) {
                if (streetBlockName.length() > 50) {
                    error += ";" + getText("ERR.STAFF.0041");
                }
            }

            if (streetName != null && !streetName.trim().equals("")) {
                if (streetName.length() > 50) {
                    error += ";" + getText("ERR.STAFF.0042");
                }
            }

            if (home != null && !home.trim().equals("")) {
                if (home.length() > 50) {
                    error += ";" + getText("ERR.STAFF.0043");
                }
            }

            //lamnv_test
            //error = "";
            if (!error.equals("")) {
                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                if (parShopCode == null) {
                    parShopCode = "";
                }
                if (shopCode == null) {
                    shopCode = "";
                }
                if (shopName == null) {
                    shopName = "";
                }
                errorBean.setOwnerCode(parShopCode + ":" + shopCode + ":" + shopName);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {

                count++;
                Shop addAgent = new Shop();
                Long shopId = getSequence("SHOP_SEQ");
                addAgent.setShopId(shopId);

                shopCode = channelDAO.getShopCodeSeqIsNotVt(provinceArea.getProvinceReference(), objectType.getPrefixObjectCode(), shopCodeHashMap);
                addAgent.setName(shopName);
                addAgent.setParentShopId(parentShop.getShopId());
                addAgent.setAccount(account);
                addAgent.setBankName(bank);
                //addAgent.setAddress(address);
                if (address != null && !address.trim().equals("")) {
                    addAgent.setAddress(address.trim() + " " + precinctArea.getFullName());
                } else {
                    addAgent.setAddress(precinctArea.getFullName());
                }
                addAgent.setTel(telNumber);

                addAgent.setShopCode(shopCode.toUpperCase());

                addAgent.setContactName(contactName);
                addAgent.setContactTitle(contactTitle);
                addAgent.setTelNumber(telNumber);
                addAgent.setEmail(mail);
                addAgent.setProvince(provinceArea.getProvince());
                addAgent.setParShopCode(parShopCode);
                addAgent.setCreateDate(getSysdate());
                addAgent.setTin(tin);
                addAgent.setProvinceCode(province);
                addAgent.setChannelTypeId(objectType.getChannelTypeId());
                addAgent.setDiscountPolicy(objectType.getDiscountPolicyDefault());
                addAgent.setPricePolicy(objectType.getPricePolicyDefault());
                addAgent.setStatus(Constant.STATUS_USE);
                addAgent.setShopPath(parentShop.getShopPath() + "_" + shopId.toString());

                addAgent.setLastUpdateUser(userToken.getLoginName());
                addAgent.setLastUpdateTime(getSysdate());
                addAgent.setLastUpdateIpAddress(req.getRemoteAddr());
                addAgent.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
                addAgent.setSyncStatus(Constant.STATUS_NOT_SYNC);

                addAgent.setDistrict(district);
                addAgent.setPrecinct(precinct);
                addAgent.setTradeName(tradeName);
                if (profileState != null && !"".equals(profileState.trim())) {
                    addAgent.setProfileState(Long.valueOf(profileState));
                }
                if (registryDate != null && !"".equals(registryDate.trim())) {
                    addAgent.setRegistryDate(DateTimeUtils.convertStringToDateTimeVunt(registryDate));
                }
                addAgent.setUsefulWidth(usefulWidth);
                addAgent.setSurfaceArea(surfaceArea);
                if ((boardState != null) && (!"".equals(boardState.trim()))) {
                    addAgent.setBoardState(Long.valueOf(boardState));
                }
                if (checkVAT != null && !"".equals(checkVAT)) {
                    addAgent.setCheckVAT(Long.valueOf(checkVAT));
                }
                if ((agentType != null) && (!"".equals(agentType.trim()))) {
                    addAgent.setAgentType(Long.valueOf(agentType));
                }
                addAgent.setIdNo(idNo);
                addAgent.setStreetBlockName(streetBlockName);
                addAgent.setStreetName(streetName);
                addAgent.setHome(home);

                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, addAgent));
                getSession().save(addAgent);

                /* LAMNV ADD START 17/05/2012.*/
                VsaUser vsaUser = new VsaUser();
                vsaUser.setUserName(addAgent.getShopCode());
                vsaUser.setFullName(addAgent.getName());
                boolean result = VsaDAO.insertUser(getSession(), vsaUser);
                result = VsaDAO.assignRole(getSession(), vsaUser.getUserId(), defaultRole);
                /* LAMNV ADD END 17/05/2012.*/

                //insert vao bang stock_owner_tmp
                channelDAO.insertStockOwnerTmp(addAgent.getShopId(), Constant.SHOP_TYPE, addAgent.getShopCode(), addAgent.getName(), addAgent.getChannelTypeId());
                saveLog(Constant.ACTION_ADD_AGENT_BY_FILE, "Thêm mới đại lý theo file", lstLogObj, shopId);
            }
        }
        req.setAttribute(REQUEST_MESSAGE, getText("ERR.AGENT.001") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.AGENT.002"));
        if (count.compareTo(total) < 0) {
            channelDAO.downloadFile("errorAddShopByFile", listError);
        }
        return pageForward;
    }

    private boolean checkValidBirthDate(Date birthDate) {
        if (birthDate == null) {
            return false;
        }
        Date currentDate = new Date();
        if (birthDate.after(currentDate)) {
            return false;
        }
        currentDate = DateTimeUtils.addYear(currentDate, -100);
        if (birthDate.before(currentDate)) {
            return false;
        }
        return true;
    }
}
