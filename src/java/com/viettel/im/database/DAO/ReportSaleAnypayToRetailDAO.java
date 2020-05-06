/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.BuyAndNotBuyForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockOwnerTmp;
import com.viettel.im.database.BO.StockType;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;

/**
 *
 * @author kdvt_thaiph1
 */
public class ReportSaleAnypayToRetailDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChannelDAO.class);
    //khai bao cac hang so forward
    private String pageForward = "";
    private final String PREPARE_REPORT = "reportSaleAnypayToDetail";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    private final String PREPARE_EXPORT_REPORT = "prepareExportCompetitorData";
    private BuyAndNotBuyForm buyAndNotBuyForm = new BuyAndNotBuyForm();

    public BuyAndNotBuyForm getBuyAndNotBuyForm() {
        return buyAndNotBuyForm;
    }

    public void setBuyAndNotBuyForm(BuyAndNotBuyForm buyAndNotBuyForm) {
        this.buyAndNotBuyForm = buyAndNotBuyForm;
    }

    /**
     * Modified by :        thaiph
     * Modify date :        07-06-2012
     * Purpose :            Open form to import staff by file
     * @return
     * @throws Exception
     */
    public String preparePage() throws Exception {
        log.info("Begin method searchShop of ChannelDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        removeTabSession("listStaffImportFile");

        if (buyAndNotBuyForm == null) {
            buyAndNotBuyForm = new BuyAndNotBuyForm();
        }
        buyAndNotBuyForm.setToDate(getSysdate());
        Date currentDate = new Date();
        currentDate = DateTimeUtils.getDateWithDayFirstMonth(currentDate);
        buyAndNotBuyForm.setFromDate(currentDate);
        buyAndNotBuyForm.setShopCode(userToken.getShopCode());
        buyAndNotBuyForm.setShopName(userToken.getShopName());
        buyAndNotBuyForm.setStaffCode(userToken.getLoginName());
        buyAndNotBuyForm.setStaffName(userToken.getFullName());

        pageForward = PREPARE_REPORT;
        return pageForward;

    }

    public String exportReport() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date fromDate = buyAndNotBuyForm.getFromDate();
        Date toDate = buyAndNotBuyForm.getToDate();

        String shopCode = buyAndNotBuyForm.getShopCode();
        String staffCode = buyAndNotBuyForm.getStaffCode();
        String saleType = buyAndNotBuyForm.getSaleType();

        pageForward = PREPARE_REPORT;

        String error = null;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Staff staff = null;
        if (staffCode != null && !staffCode.equals("")) {
            staff = getStaff(staffCode);
        }
        req.setAttribute("displayResultMsgClient", "Export successfull");

        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByPropertyAndStatus(
                StockTypeDAO.CHECK_EXP, Constant.STOCK_MUST_EXP, Constant.STATUS_USE);
        req.setAttribute("listStockType", listStockType);

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List listChannelType = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("listChannelType", listChannelType);


        if (shopCode == null || shopCode.trim().equals("")) {
            error += ";" + getText("Error. shopCode is null!");
            req.setAttribute("displayResultMsgClient", error);
            return pageForward;
        } else {

            Shop shop = getShop(shopCode);
            if (shop == null) {
                error += ";" + getText("Error. Shopcode is invalid!");
                req.setAttribute("displayResultMsgClient", error);
                return pageForward;
            } else {

                if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
//                error = "MÃ£ cá»­a hÃ ng Ä‘ang á»Ÿ tráº¡ng thÃ¡i ngÆ°ng hoáº¡t Ä‘á»™ng";
                    error += ";" + getText("ERR.CHL.046");
                }
                if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                    error = "MÃ£ cá»­a hÃ ng khÃ´ng pháº£i mÃ£ con cá»§a cá»­a hÃ ng user Ä‘Äƒng nháº­p";
                    error += ";" + getText("ERR.CHL.056");
                }
                if (shop != null && (shop.getProvince() == null || shop.getProvince().trim().equals(""))) {
                    error += ";" + getText("Error. Province of shop is null!");
                }

                if (error != null && !error.equals("")) {
                    req.setAttribute("displayResultMsgClient", error);
                    return pageForward;
                } else {
                    ViettelMsg request = new OriginalViettelMsg();
//                    request.set("FROM_DATE", DateTimeUtils.convertDateTimeToString(fromDate, "dd/MM/yyyy"));
//                    request.set("TO_DATE", DateTimeUtils.convertDateTimeToString(toDate, "dd/MM/yyyy"));

                    request.set("FROM_DATE", fromDate);
                    request.set("TO_DATE", toDate);

                    if (staff != null) {
                        request.set("STAFF_ID", staff.getStaffId());
                    }
                    if (saleType != null) {
                        request.set("SALE_TYPE", saleType);
                    }

                    request.set("SHOP_ID", shop.getShopId());
                    request.set("SHOP_NAME", userToken.getShopName());
                    request.set("USER_NAME", userToken.getLoginName());
                    request.set("BRANCH_NAME", userToken.getShopName());
                    request.set("SHOP_PATH", shop.getShopPath());

                    request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_SALE_ANYPAY_TO_RETAIL);

                    ViettelMsg response = ReportRequestSender.sendRequest(request);
                    if (response != null
                            && response.get(ReportConstant.RESULT_FILE) != null
                            && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                        req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                    } else {
                        req.setAttribute("displayResultMsgClient", "report.warning.noResult");
                        return pageForward;
                    }
                }
            }
        }

        return pageForward;
    }

    public String reportSaleOfChannel() throws Exception {
        pageForward = "reportSaleOfChannel";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date currentDate = new Date();
        currentDate = DateTimeUtils.addDate(currentDate, -10);
        buyAndNotBuyForm.setFromDate(currentDate);
        buyAndNotBuyForm.setShopCode(userToken.getShopCode());
        buyAndNotBuyForm.setShopName(userToken.getShopName());
        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByPropertyAndStatus(
                StockTypeDAO.CHECK_EXP, Constant.STOCK_MUST_EXP, Constant.STATUS_USE);
        req.setAttribute("listStockType", listStockType);

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List listChannelType = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("listChannelType", listChannelType);
        return pageForward;
    }

    public String exportReportSaleOfChannel() throws Exception {
        pageForward = "reportSaleOfChannel";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date fromDate = buyAndNotBuyForm.getFromDate();

        Long channelTypeId = buyAndNotBuyForm.getChannelTypeId();
        Long reportType = buyAndNotBuyForm.getReportType();
        Long stockTypeId = buyAndNotBuyForm.getStockTypeId();
        String stockModelCode = buyAndNotBuyForm.getStockModelCode();
        String staffCode = buyAndNotBuyForm.getStaffCode();
        String shopCode = buyAndNotBuyForm.getShopCode();

        Date toDate = new Date();
        toDate = DateTimeUtils.addDate(fromDate, 1);

        if (reportType.equals(2L)) { //bao cao theo thang
            fromDate = DateTimeUtils.getDateWithDayFirstMonth(fromDate);
            toDate = DateTimeUtils.addMonth(fromDate, 1);
            toDate = DateTimeUtils.addDate(toDate, 1);
        }


        String error = null;
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        Staff staff = null;
        if (staffCode != null && !staffCode.equals("")) {
            staff = getStaff(staffCode);
        }
        req.setAttribute("displayResultMsgClient", "Export successfull");

        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.findByPropertyAndStatus(
                StockTypeDAO.CHECK_EXP, Constant.STOCK_MUST_EXP, Constant.STATUS_USE);
        req.setAttribute("listStockType", listStockType);

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List listChannelType = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("listChannelType", listChannelType);
        Long stockModelId = null;
        if (stockModelCode != null && !stockModelCode.trim().equals("")) {
            StockModelDAO stockModelDAO = new StockModelDAO();
            StockModel stockModel = stockModelDAO.getStockModelByCode(stockModelCode);
            stockModelId = stockModel.getStockModelId();
        }

        if (shopCode == null || shopCode.trim().equals("")) {
            error += ";" + getText("Error. shopCode is null!");
            req.setAttribute("displayResultMsgClient", error);
            return pageForward;
        } else {

            Shop shop = getShop(shopCode);
            if (shop == null) {
                error += ";" + getText("Error. Shopcode is invalid!");
                req.setAttribute("displayResultMsgClient", error);
                return pageForward;
            } else {

                if (shop != null && shop.getStatus() != null && shop.getStatus().equals(0L)) {
//                error = "MÃ£ cá»­a hÃ ng Ä‘ang á»Ÿ tráº¡ng thÃ¡i ngÆ°ng hoáº¡t Ä‘á»™ng";
                    error += ";" + getText("ERR.CHL.046");
                }
                if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
//                    error = "MÃ£ cá»­a hÃ ng khÃ´ng pháº£i mÃ£ con cá»§a cá»­a hÃ ng user Ä‘Äƒng nháº­p";
                    error += ";" + getText("ERR.CHL.056");
                }
                if (shop != null && (shop.getProvince() == null || shop.getProvince().trim().equals(""))) {
                    error += ";" + getText("Error. Province of shop is null!");
                }

                if (error != null && !error.equals("")) {
                    req.setAttribute("displayResultMsgClient", error);
                    return pageForward;
                } else {
                    ViettelMsg request = new OriginalViettelMsg();
                    request.set("FROM_DATE", DateTimeUtils.convertDateTimeToString(fromDate, "dd/MM/yyyy"));
                    request.set("TO_DATE", DateTimeUtils.convertDateTimeToString(toDate, "dd/MM/yyyy"));

                    request.set("CHANNEL_TYPE_ID", channelTypeId);
                    request.set("STOCK_TYPE_ID", stockTypeId);
                    request.set("STOCK_MODEL_ID", stockModelId);

                    if (staff != null) {
                        request.set("STAFF_ID", staff.getStaffId());
                    }
                    request.set("SHOP_ID", shop.getShopId());
                    request.set("SHOP_NAME", userToken.getShopName());
                    request.set("USER_NAME", userToken.getLoginName());
                    request.set("BRANCH_NAME", userToken.getShopName());
                    request.set("SHOP_PATH", shop.getShopPath());

                    request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_SALE_OF_CHANNEL);

                    ViettelMsg response = ReportRequestSender.sendRequest(request);
                    if (response != null
                            && response.get(ReportConstant.RESULT_FILE) != null
                            && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                        req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                    } else {
                        req.setAttribute("displayResultMsgClient", "report.warning.noResult");
                        return pageForward;
                    }
                }
            }
        }

        return pageForward;
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

    //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Náº¿u há»‡ thá»‘ng khÃ´ng tá»± download. Click vÃ o Ä‘Ã¢y Ä‘á»ƒ táº£i File lÆ°u thÃ´ng tin khÃ´ng cáº­p nháº­t Ä‘Æ°á»£c");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }
}
