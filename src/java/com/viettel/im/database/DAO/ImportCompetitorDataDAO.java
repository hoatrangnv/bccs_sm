/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.anypay.util.DataUtil;
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.ViewImportCompetitorBean;
import com.viettel.im.client.bean.ViewStaffImportBean;
import com.viettel.im.client.form.ChannelForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ErrorChangeChannelBean;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.SurveyCompetitor;
import com.viettel.im.database.BO.SurveyCompetitorDetail;
import com.viettel.im.database.BO.VsaUser;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author kdvt_thaiph1
 */
public class ImportCompetitorDataDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChannelDAO.class);
    //khai bao cac hang so forward
    private String pageForward = "";
    private final String PREPARE_IMPORT_DATA_REPORT = "importCompetitorData";
    private final String REQUEST_MESSAGE = "importCompetitorData";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    private final String PREPARE_EXPORT_REPORT = "prepareExportCompetitorData";
    private ChannelForm channelForm = new ChannelForm();

    public ChannelForm getChannelForm() {
        return channelForm;
    }

    public void setChannelForm(ChannelForm channelForm) {
        this.channelForm = channelForm;
    }

    /**
     * Modified by :        TrongLV
     * Modify date :        29-04-2011
     * Purpose :            Open form to import staff by file
     * @return
     * @throws Exception
     */
    public String prepare() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        removeTabSession("listStaffImportFile");

        if (channelForm == null) {
            channelForm = new ChannelForm();
        }
        channelForm.setChangeType(Constant.CHANNEL_TYPE_STAFF);
        channelForm.setTodayDate(getSysdate());
        channelForm.setCompCollectorDate(getSysdate());

        pageForward = PREPARE_IMPORT_DATA_REPORT;
        return pageForward;

    }

    public String addStaffByWebForm() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = PREPARE_IMPORT_DATA_REPORT;
        Long kitMovitelLong = channelForm.getCompKitMovitel();
        Long kitMctelLong = channelForm.getCompKitMctel();
        Long kitVodacomLong = channelForm.getCompKitVodacom();
        Long cardMovitelLong = channelForm.getCompCardMovitel();
        Long cardMctelLong = channelForm.getCompCardMctel();
        Long cardVodacomLong = channelForm.getCompCardVodacom();
        Date collectorDate = channelForm.getCompCollectorDate();
        String staffCode = channelForm.getCompStaffCode();
        String shopCode = channelForm.getCompShopCode();
        String error = null;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Staff staff = null;
        if (staffCode == null || staffCode.trim().equals("")) {
            error += ";" + getText("Error. Staff code is null!");
            req.setAttribute("resultImportSingle", error);
            return pageForward;
        } else {
            staff = getStaff(staffCode);
            if (staff == null) {
                error += ";" + getText("Error. Staff code is invalid!");
                req.setAttribute("resultImportSingle", error);
                return pageForward;
            } else {
                Shop shop = getShop(staff.getShopId());

                if (shop == null) {
//                error = "Mã cửa hàng không chính xác";
                    error += ";" + getText("ERR.CHL.083");
                }
                if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
//                error = "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
                    error += ";" + getText("ERR.CHL.046");
                }
                if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                    error = "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập";
                    error += ";" + getText("ERR.CHL.056");
                }
                if (shop != null && (shop.getProvince() == null || shop.getProvince().trim().equals(""))) {
                    error += ";" + getText("Error. Province of shop is null!");
                }
                if (staffCode == null || staffCode.trim().equals("")) {
                    error += ";" + getText("ERR.CHL.125");
                }
                if (staffCode != null && !staffCode.trim().equals("") && !CommonDAO.checkValidateObjectCode(staffCode)) {
                    error += ";" + getText("Error. Staff code is invalid!");
                }

                if (shop == null || !shop.getShopCode().toLowerCase().equals(shopCode.toLowerCase())) {
                    error += ";" + getText("Error. Shop is invalid!");
                }
                if (collectorDate == null || DateTimeUtils.distanceBeetwen2Date(collectorDate, getSysdate()) < 0) {
                    error += ";" + getText("Error. Collector Date is invalid!");
                }

                if (error != null && !error.equals("")) {
                    req.setAttribute("resultImportSingle", error);
                    return pageForward;
                } else {

                    SurveyCompetitor surveyCompetitor = new SurveyCompetitor();
                    Long surveyId = getSequence("SURVEY_COMPETITIOR_ID");
                    surveyCompetitor.setSurveyId(surveyId);
                    surveyCompetitor.setUserId(userToken.getUserID());
                    surveyCompetitor.setCreateDate(getSysdate());

                    lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, surveyCompetitor));
                    getSession().save(surveyCompetitor);

                    saveSurveyCompetitorDetail(surveyId, "MV", kitMovitelLong, cardMovitelLong, collectorDate, staff.getStaffId());
                    saveSurveyCompetitorDetail(surveyId, "MC", kitMctelLong, cardMctelLong, collectorDate, staff.getStaffId());
                    saveSurveyCompetitorDetail(surveyId, "VC", kitVodacomLong, cardVodacomLong, collectorDate, staff.getStaffId());
                }

            }
        }
        channelForm.resetForm();
        req.setAttribute("resultImportSingle", "Import successfull");
        return pageForward;
    }

    public String viewFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_IMPORT_DATA_REPORT;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

//        if (channelForm.getChangeType() == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
//            return pageForward;
//        }
//        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
//        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
//        if (objectType == null || objectType.getChannelTypeId() == null
//                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
//                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
//            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
//            return pageForward;
//        }


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1000);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        List<ViewImportCompetitorBean> listStaff = new ArrayList<ViewImportCompetitorBean>();
        for (int i = 0; i < list.size(); i++) {
            String kitMovitel = null;
            String kitMctel = null;
            String kitVodacom = null;
            String cardMovitel = null;
            String cardMctel = null;
            String cardVodacom = null;
            Object[] object = (Object[]) list.get(i);
            String staffCode = object[0].toString();
            String collectorDateString = object[1].toString();
            try {
                kitMovitel = object[2].toString();
            } catch (Exception ex) {
            }
            try {
                kitMctel = object[3].toString();
            } catch (Exception ex) {
            }
            try {
                kitVodacom = object[4].toString();
            } catch (Exception ex) {
            }
            try {
                cardMovitel = object[5].toString();
            } catch (Exception ex) {
            }
            try {
                cardMctel = object[6].toString();
            } catch (Exception ex) {
            }
            try {
                cardVodacom = object[7].toString();
            } catch (Exception ex) {
            }

//            Date collectorDate = null;
//            try {
//                collectorDate = DateTimeUtils.convertStringToDateTimeVunt(collectorDateString);
//            } catch (Exception ex) {
//            }

            ViewImportCompetitorBean addRow = new ViewImportCompetitorBean();
            addRow.setStaffCode(staffCode);
            addRow.setCollectorDateString(collectorDateString);
            addRow.setKitMovitel(kitMovitel);
            addRow.setKitMctel(kitMctel);
            addRow.setKitVodacom(kitVodacom);
            addRow.setCardMovitel(cardMovitel);
            addRow.setCardMctel(cardMctel);
            addRow.setCardVodacom(cardVodacom);
            listStaff.add(addRow);
        }
        setTabSession("listStaffImportFile", listStaff);
        return pageForward;

    }

    public String addStaffByFile() throws Exception {
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(channelForm.getServerFileName());
        HttpServletRequest req = getRequest();
        pageForward = PREPARE_IMPORT_DATA_REPORT;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);


//        if (channelForm.getChangeType() == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Error. Pls select Channel Type!");
//            return pageForward;
//        }
//        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(this.getSession());
//        ChannelType objectType = channelTypeDAO.findById(channelForm.getChangeType());
//        if (objectType == null || objectType.getChannelTypeId() == null
//                || objectType.getObjectType() == null || !objectType.getObjectType().equals(Constant.OBJECT_TYPE_STAFF)
//                || objectType.getIsVtUnit() == null || !objectType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
//            req.setAttribute("resultViewChangeStaffInShop", getText("ERR.STAFF.0037"));//Error. Channel Type is invalid!
//            return pageForward;
//        }

        /* LAMNV ADD START 17/05/2012.*/
//        Long defaultRole = null;
//        AppParamsDAO appParamDAO = new AppParamsDAO();
//        appParamDAO.setSession(getSession());
//        AppParams appParam = appParamDAO.findAppParams("CHANNEL_DEFAULT_ROLE", DataUtil.safeToString(objectType.getChannelTypeId()));
//
//        try {
//            defaultRole = Long.parseLong(appParam.getValue());
//        } catch (Exception ex) {
//            defaultRole = null;
//        }
//
//        if (appParam == null || defaultRole == null) {
//            prepare();
//            req.setAttribute(REQUEST_MESSAGE, getText("ERR.STAFF.0047"));
//            return pageForward;
//        }
        /* LAMNV END START 17/05/2012.*/


        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1000);
        if (list == null) {
//            req.setAttribute("resultViewChangeStaffInShop", "Bạn kiểm tra lại file Import đã giồng template chưa, phải đặt tên sheet của file Import là Sheet1");
            req.setAttribute("resultViewChangeStaffInShop", "ERR.CHL.059");
            return pageForward;
        }
        Long count = 0L;
        Long total = 0L;
        Map<String, Staff> staffCodeHashMap = new HashMap<String, Staff>();

        List<ViewImportCompetitorBean> listError = new ArrayList<ViewImportCompetitorBean>();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        HashMap<String, AppParams> hashMap = this.getStaffTypeList();
        for (int i = 0; i < list.size(); i++) {
            total++;
            String kitMovitel = null;
            String kitMctel = null;
            String kitVodacom = null;
            String cardMovitel = null;
            String cardMctel = null;
            String cardVodacom = null;
            Long kitMovitelLong = null;
            Long kitMctelLong = null;
            Long kitVodacomLong = null;
            Long cardMovitelLong = null;
            Long cardMctelLong = null;
            Long cardVodacomLong = null;

            Object[] object = (Object[]) list.get(i);
            String staffCode = object[0].toString();
            String collectorDateString = object[1].toString();
            try {
                kitMovitel = object[2].toString();
                kitMovitelLong = Long.valueOf(kitMovitel);
            } catch (Exception ex) {
            }
            try {
                kitMctel = object[3].toString();
                kitMctelLong = Long.valueOf(kitMctel);
            } catch (Exception ex) {
            }
            try {
                kitVodacom = object[4].toString();
                kitVodacomLong = Long.valueOf(kitVodacom);
            } catch (Exception ex) {
            }
            try {
                cardMovitel = object[5].toString();
                cardMovitelLong = Long.valueOf(cardMovitel);
            } catch (Exception ex) {
            }
            try {
                cardMctel = object[6].toString();
                cardMctelLong = Long.valueOf(cardMctel);
            } catch (Exception ex) {
            }
            try {
                cardVodacom = object[7].toString();
                cardVodacomLong = Long.valueOf(cardVodacom);
            } catch (Exception ex) {
            }

            Date collectorDate = null;
            try {
                collectorDate = DateTimeUtils.convertStringToDateTimeVunt(collectorDateString);
            } catch (Exception ex) {
            }
            String error = "";
            Staff staff = null;
            if (staffCode == null || staffCode.trim().equals("")) {
                error += ";" + getText("Error. Staff code is null!");
            } else {
                staff = getStaff(staffCode);
                if (staff == null) {
                    error += ";" + getText("Error. Staff code is invalid!");
                } else {
                    Shop shop = getShop(staff.getShopId());
                    if (shop == null) {
                        error += ";" + getText("Error. Shop is invalid!");
                    } else {

                        if (shop == null) {
//                error = "Mã cửa hàng không chính xác";
                            error += ";" + getText("ERR.CHL.083");
                        }
                        if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
//                error = "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
                            error += ";" + getText("ERR.CHL.046");
                        }
                        if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                    error = "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập";
                            error += ";" + getText("ERR.CHL.056");
                        }
                        if (shop != null && (shop.getProvince() == null || shop.getProvince().trim().equals(""))) {
                            error += ";" + getText("Error. Province of shop is null!");
                        }
                        if (staffCode == null || staffCode.trim().equals("")) {
                            error += ";" + getText("ERR.CHL.125");
                        }
                        if (staffCode != null && !staffCode.trim().equals("") && !CommonDAO.checkValidateObjectCode(staffCode)) {
                            error += ";" + getText("Error. Staff code is invalid!");
                        }

                        if (kitMovitelLong == null || kitMovitelLong.compareTo(0L) < 0) {
                            error += ";" + getText("Error. kitMovitel is null or invalid!");
                        }
                        if (kitMctelLong == null || kitMctelLong.compareTo(0L) < 0) {
                            error += ";" + getText("Error. kitMctel is null or invalid!");
                        }
                        if (kitVodacomLong == null || kitVodacomLong.compareTo(0L) < 0) {
                            error += ";" + getText("Error. kitVodacom is null or invalid!");
                        }
                        if (cardMovitelLong == null || cardMovitelLong.compareTo(0L) < 0) {
                            error += ";" + getText("Error. cardMovitel is null or invalid!");
                        }
                        if (cardMctelLong == null || cardMctelLong.compareTo(0L) < 0) {
                            error += ";" + getText("Error. cardMctel is null or invalid!");
                        }
                        if (cardVodacomLong == null || cardVodacomLong.compareTo(0L) < 0) {
                            error += ";" + getText("Error. cardVodacom is null or invalid!");
                        }
                        if (collectorDate == null || DateTimeUtils.distanceBeetwen2Date(collectorDate, getSysdate()) < 0) {
                            error += ";" + getText("Error. Collector Date is null or invalid!");
                        }
                    }
                }
            }
            if (!error.equals("")) {
                ViewImportCompetitorBean errorBean = new ViewImportCompetitorBean();
                errorBean.setStaffCode(staffCode);
                errorBean.setCollectorDateString(collectorDateString);
                errorBean.setKitMovitel(kitMovitel);
                errorBean.setKitMctel(kitMctel);
                errorBean.setKitVodacom(kitVodacom);
                errorBean.setCardMovitel(cardMovitel);
                errorBean.setCardMctel(cardMctel);
                errorBean.setCardVodacom(cardVodacom);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                count++;
                SurveyCompetitor surveyCompetitor = new SurveyCompetitor();
                Long surveyId = getSequence("SURVEY_COMPETITIOR_ID");
                surveyCompetitor.setSurveyId(surveyId);
                surveyCompetitor.setUserId(userToken.getUserID());
                surveyCompetitor.setCreateDate(getSysdate());

                lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, surveyCompetitor));
                getSession().save(surveyCompetitor);

                saveSurveyCompetitorDetail(surveyId, "MV", kitMovitelLong, cardMovitelLong, collectorDate, staff.getStaffId());
                saveSurveyCompetitorDetail(surveyId, "MC", kitMctelLong, cardMctelLong, collectorDate, staff.getStaffId());
                saveSurveyCompetitorDetail(surveyId, "VC", kitVodacomLong, cardVodacomLong, collectorDate, staff.getStaffId());


                //Luu danh sach thanh cong
//                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
//                errorBean.setOwnerCode(addStaff.getStaffCode());
//                errorBean.setOwnerName(" : " + staffCode + " : " );
//                errorBean.setError(error);
//                listError.add(errorBean);
            }
        }
        req.setAttribute("resultViewChangeStaffInShop", getText("ERR.CHL.099") + " " + count.toString() + "/" + total.toString() + " " + getText("ERR.CHL.100"));

//        if (count.compareTo(total) < 0) {
//            downloadFile("errorAddStaffByFile", listError);
//        }
        //Hien thi toan bo
        downloadFile("errorImportCompetitorDataByFile", listError);

        channelForm.setChangeType(null);
        channelForm.setClientFileName(null);
        channelForm.setServerFileName(null);
        removeTabSession("listStaffImportFile");
        return pageForward;

    }

    public String prepareReportPage() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = PREPARE_EXPORT_REPORT;
        channelForm.setToDate(getSysdate());
        Date currentDate = new Date();
        currentDate = DateTimeUtils.addDate(currentDate, -10);
        channelForm.setFromDate(currentDate);
        channelForm.setCompShopCode(userToken.getShopCode());
        channelForm.setCompShopName(userToken.getShopName());
        return pageForward;
    }

    public String exportReportCompetitor() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date fromDate = channelForm.getFromDate();
        Date toDate = channelForm.getToDate();
        String staffCode = channelForm.getCompStaffCode();
        String shopCode = channelForm.getCompShopCode();
        pageForward = PREPARE_EXPORT_REPORT;

        String error = null;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Staff staff = null;
        if (shopCode == null || shopCode.trim().equals("")) {
            error += ";" + getText("Error. shopCode is null!");
            req.setAttribute("resultViewChangeStaffInShopClient", error);
            return pageForward;
        } else {

            Shop shop = getShop(shopCode);
            if (shop == null) {
                error += ";" + getText("Error. Shopcode is invalid!");
                req.setAttribute("resultViewChangeStaffInShopClient", error);
                return pageForward;
            } else {

                if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
//                error = "Mã cửa hàng đang ở trạng thái ngưng hoạt động";
                    error += ";" + getText("ERR.CHL.046");
                }
                if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                    error = "Mã cửa hàng không phải mã con của cửa hàng user đăng nhập";
                    error += ";" + getText("ERR.CHL.056");
                }
                if (shop != null && (shop.getProvince() == null || shop.getProvince().trim().equals(""))) {
                    error += ";" + getText("Error. Province of shop is null!");
                }
                if (staffCode != null && !staffCode.trim().equals("")) {
                    if (staffCode == null || staffCode.trim().equals("")) {
                        error += ";" + getText("ERR.CHL.125");
                    }
                    if (staffCode != null && !staffCode.trim().equals("") && !CommonDAO.checkValidateObjectCode(staffCode)) {
                        error += ";" + getText("Error. Staff code is invalid!");
                    }
                    staff = getStaff(staffCode);
                    if (!shop.getShopId().equals(staff.getShopId())) {
                        error += ";" + getText("Error. Staff not in shop!");
                    }
                }

                if (error != null && !error.equals("")) {
                    req.setAttribute("resultViewChangeStaffInShopClient", error);
                    return pageForward;
                } else {
                    ViettelMsg request = new OriginalViettelMsg();
                    request.set("FROM_DATE", fromDate);
                    request.set("TO_DATE", toDate);
                    if (staff != null) {
                        request.set("STAFF_ID", staff.getStaffId());
                    }
                    request.set("SHOP_NAME", userToken.getShopName());
                    request.set("USER_NAME", userToken.getLoginName());
                    request.set("BRANCH_NAME", userToken.getShopName());
                    request.set("SHOP_PATH", shop.getShopPath());

                    request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_COMPETITOR_BUSINESS);

                    ViettelMsg response = ReportRequestSender.sendRequest(request);
                    if (response != null
                            && response.get(ReportConstant.RESULT_FILE) != null
                            && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                        DownloadDAO downloadDAO = new DownloadDAO();
                        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                        //req.setAttribute("reportAccountPath", response.get(ReportConstant.RESULT_FILE).toString());
                    } else {
                        req.setAttribute("resultViewChangeStaffInShopClient", "report.warning.noResult");
                        return pageForward;
                    }
                }
            }
        }
        req.setAttribute("reportAccountMessage", "Export successfull");

        return pageForward;
    }

    private boolean saveSurveyCompetitorDetail(Long surveyId, String competitorCode, Long kit, Long card, Date collectorDate, Long staffId) throws Exception {
        boolean result = true;
        SurveyCompetitorDetail surveyCompetitorDetail = new SurveyCompetitorDetail();
        Long surveyDetailId = getSequence("SEQ_SURVEY_COMPETITOR_DETAIL");
        surveyCompetitorDetail.setSurveyDetailId(surveyDetailId);
        surveyCompetitorDetail.setSurveyId(surveyId);
        surveyCompetitorDetail.setCardAmount(card);
        surveyCompetitorDetail.setKitQuantity(kit);
        surveyCompetitorDetail.setCollecterDate(collectorDate);
        surveyCompetitorDetail.setCompetitorCode(competitorCode);
        surveyCompetitorDetail.setCollectorId(staffId);
        surveyCompetitorDetail.setCreateDate(getSysdate());
        getSession().save(surveyCompetitorDetail);
        return result;
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

    public Shop getShop(Long shopId) throws Exception {
        if (shopId == null) {
            return null;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(shopId);
        if (shop != null) {
            return shop;
        }
        return null;
    }

    public Shop getShop(String shopCode) throws Exception {
        if (shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCodeNotStatus(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }

    public boolean checkShopUnder(Long shopIdLogin, Long shopIdSelect) {
        if (shopIdLogin.equals(shopIdSelect)) {
            return true;
        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shopLogin = shopDAO.findById(shopIdLogin);
        Shop shopSelect = shopDAO.findById(shopIdSelect);
        if (shopSelect.getShopPath().indexOf(shopLogin.getShopPath() + "_") < 0) {
            return false;
        } else {
            return true;
        }

    }

    ///////////////////////////////BO SUNG//////////////////////////////////////
    public Area getArea(String areaCode) {
        if (areaCode == null || areaCode.trim().equals("")) {
            return null;
        }
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        return areaDAO.findByAreaCode(areaCode.toUpperCase());
    }

    /**
     * @author tutm1
     * @purpose them ban ghi moi co han muc mac dinh maxDebit
     * @param ownerId
     * @param ownerType
     * @param staffCode
     * @param staffName
     * @param channelTypeId
     * @param maxDebit
     * @return
     * @throws Exception
     */
    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String staffCode, String staffName,
            Long channelTypeId, Double maxDebit) throws Exception {
        //insert vao bang stock_owner_tmp
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list == null || list.size() == 0) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(staffCode);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            stockOwnerTmp.setMaxDebit(maxDebit);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = list.get(0);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }

    /**
     * @author tutm1
     * @param idNo 
     * @param channelTypeId 
     * @param staffHashMap
     * @return kiem tra idNo & channelTypeId da ton tai trong bang staff hay chua
     */
    private boolean checkExistIdNo(String idNo, Long channelTypeId, Map<String, Staff> staffHashMap) {
        try {
            if (idNo == null || idNo.trim().equals("")) {
                return false;
            }
//            String sql = "from Staff where idNo = ? and channelTypeId = ? ";
            String sql = "from Staff where idNo = ? and status != ? ";
            Query query = getSession().createQuery(sql);
            query.setParameter(0, idNo);
//            query.setParameter(1, channelTypeId);
            query.setParameter(1, Constant.ACCOUNT_STATUS_INACTIVE);
            List<Staff> list = query.list();
            if (list != null && list.size() > 0) {
                return true;
            } else {
                if (staffHashMap != null && !staffHashMap.isEmpty()) {
                    Set<String> key = staffHashMap.keySet();
                    Iterator<String> iteratorKey = key.iterator();
                    while (iteratorKey.hasNext()) {
                        String staffCode = iteratorKey.next();
                        Staff staff = staffHashMap.get(staffCode);
                        if (staff != null && staff.getIdNo() != null && staff.getIdNo().equals(idNo)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (Exception ex) {
            log.debug("", ex);
            return true;
        }
    }

    public boolean insertStockOwnerTmp(Long ownerId, String ownerType, String staffCode, String staffName, Long channelTypeId) throws Exception {
        //insert vao bang stock_owner_tmp
        String sql = "From StockOwnerTmp where ownerId = ? and ownerType = ?";
        Query query = getSession().createQuery(sql);
        query.setParameter(0, ownerId);
        query.setParameter(1, ownerType);
        List<StockOwnerTmp> list = query.list();
        if (list == null || list.size() == 0) {
            StockOwnerTmp stockOwnerTmp = new StockOwnerTmp();
            stockOwnerTmp.setStockId(getSequence("STOCK_OWNER_TMP_SEQ"));
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            stockOwnerTmp.setCode(staffCode);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setOwnerId(ownerId);
            stockOwnerTmp.setOwnerType(ownerType);
            getSession().save(stockOwnerTmp);
        } else {
            StockOwnerTmp stockOwnerTmp = list.get(0);
            stockOwnerTmp.setName(staffName);
            stockOwnerTmp.setChannelTypeId(channelTypeId);
            getSession().update(stockOwnerTmp);
        }
        return true;
    }

    public Staff getStaff(String staffCode) throws Exception {
        if (staffCode == null || staffCode.trim().equals("")) {
            return null;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCodeNotStatus(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff;
        }
        return null;
    }

    private String getStaffCodeSeqIsVt(String staffCode, String prefixObjectCode) {
        if (staffCode == null || staffCode.trim().equals("")) {
            return "";
        } else {
            staffCode = staffCode.trim().toUpperCase();
        }
        if (prefixObjectCode != null) {
            prefixObjectCode = prefixObjectCode.trim().toUpperCase();
        } else {
            prefixObjectCode = "";
        }
        try {

            Staff staff = getStaff(staffCode + "_" + prefixObjectCode);
            if (staff == null || staff.getStaffId() == null) {
                return staffCode + "_" + prefixObjectCode;
            }

//            String strQuery = "select max( substr(staff_code,length('" + staffCode + "')+1,length(staff_code)-length('" + staffCode + "')-length('_" + prefixObjectCode + "')))  "
//                    + " from staff where staff_code like '" + staffCode + "%$_" + prefixObjectCode + "' escape '$' and staff_code <> '" + staffCode + "_" + prefixObjectCode + "' ";
//
            String strQuery = "select max( substr(staff_code,length(?)+1,length(staff_code)-length(?)-length(?)))  "
                    + " from staff where staff_code like ? escape '$' and staff_code <> ? ";

            Query queryObject = getSession().createSQLQuery(strQuery);
            queryObject.setParameter(0, staffCode);
            queryObject.setParameter(1, staffCode);
            queryObject.setParameter(2, "_" + prefixObjectCode);
            queryObject.setParameter(3, staffCode + "%$_" + prefixObjectCode);
            queryObject.setParameter(4, staffCode + "_" + prefixObjectCode);


            String tmp = (String) queryObject.uniqueResult();
            if (tmp == null || tmp.trim().equals("")) {
                if (staff == null || staff.getStaffId() == null) {
                    tmp = staffCode + "_" + prefixObjectCode;
                } else {
                    tmp = staffCode + "2" + "_" + prefixObjectCode;
                }
            } else {
                tmp = staffCode + String.valueOf(Long.parseLong(tmp) + 1) + "_" + prefixObjectCode;
            }
            return tmp;
        } catch (Exception ex) {
            return staffCode + "_" + prefixObjectCode.trim();
        }



    }

    private HashMap<String, AppParams> getStaffTypeList() {
        HashMap<String, AppParams> hashMap = new HashMap<String, AppParams>();
        try {
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listAppParams = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_STAFF_TYPE);
            if (listAppParams != null && !listAppParams.isEmpty()) {
                for (int i = 0; i < listAppParams.size(); i++) {
                    if (listAppParams.get(i).getStatus() != null && listAppParams.get(i).getStatus().equals(Constant.STATUS_USE.toString())) {
                        try {
                            long tmp = Long.parseLong(listAppParams.get(i).getCode());
                            hashMap.put(listAppParams.get(i).getCode(), listAppParams.get(i));
                        } catch (Exception ex) {
                        }

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hashMap;
    }

    //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }
}
