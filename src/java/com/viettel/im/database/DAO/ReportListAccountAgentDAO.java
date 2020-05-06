/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ViewAccountBookBean;
import com.viettel.im.client.bean.ViewAccountSubscriberBean;
import com.viettel.im.common.ReportConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueForm;
import java.util.List;
import java.util.ArrayList;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VSaleTransDetail;
import com.viettel.im.client.bean.AppParamsBean;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author User
 */
public class ReportListAccountAgentDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportDevelopSubscriberDAO.class);
    private String pageForward;
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();
    private final String REPORT_ACCOUNT_AGENT = "reportListAccountAgent";
    private final String REPORT_LIST_CHANNEL = "reportListChannel";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    private Map listItemsCombo = new HashMap();

    public Map getListItemsCombo() {
        return listItemsCombo;
    }

    public void setListItemsCombo(Map listItemsCombo) {
        this.listItemsCombo = listItemsCombo;
    }

    public ReportRevenueForm getReportRevenueForm() {
        return reportRevenueForm;
    }

    public void setReportRevenueForm(ReportRevenueForm reportRevenueForm) {
        this.reportRevenueForm = reportRevenueForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueDAO ...");
        HttpServletRequest req = getRequest();
        //reset form
        this.reportRevenueForm.resetForm();
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //this.reportRevenueForm.setStaffCode(userToken.getLoginName());
        //this.reportRevenueForm.setStaffName(userToken.getStaffName());
        this.reportRevenueForm.setReportType(1L);
        // Lay ten tin cua shop dang nhap
        Shop shop = getShopId(userToken.getShopCode());
        if (shop != null) {
            String shop_path = shop.getShopPath() + "_";
            int index = shop_path.indexOf("_", 1);
            if (index != -1 && index < shop_path.length()) {
                int index1 = shop_path.indexOf("_", index + 1);
                if (index1 != -1) {
                    String shopId = shop_path.substring(index + 1, index1);
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    Shop shopLogin = shopDAO.findById(Long.parseLong(shopId));
                    if (shopLogin != null) {
                        reportRevenueForm.setProvinceCode(shopLogin.getShopCode());
                        reportRevenueForm.setProvinceName(shopLogin.getName());
                        reportRevenueForm.setShopCode(userToken.getShopCode());
                        reportRevenueForm.setShopName(userToken.getShopName());
                    }
                }
            }
        }
        //getDataForComboBox();

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = ctDao.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        //List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        //req.setAttribute("lstChannelTypeCol", lstChannelTypeCol)
        pageForward = REPORT_ACCOUNT_AGENT;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    //NamLT add Bao cao danh muc kenh 12/11/2010
    public String preparePageReportListChannel() throws Exception {
        log.info("Begin method preparePage of ReportRevenueDAO ...");
        HttpServletRequest req = getRequest();
        this.reportRevenueForm.resetForm();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        reportRevenueForm.setShopCode(userToken.getShopCode());
        reportRevenueForm.setShopName(userToken.getShopName());

        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        // tutm1 commnent : load danh sach agent type sau khi da chon channel_type
        //req.setAttribute("listAgentType", appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_STAFF_AGENT_TYPE, Constant.STATUS_USE.toString()));
        req.setAttribute("listStaffStatus", appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_STAFF_STATUS, Constant.STATUS_USE.toString()));

        pageForward = REPORT_LIST_CHANNEL;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public List getAgentTypeByChannelType(Long channelTypeId, String type, String status) {
        log.info("Begin");

        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append(" select ");
        sqlBuffer.append(" code, ");
        sqlBuffer.append(" name");
        sqlBuffer.append(" FROM ");
        sqlBuffer.append(" AppParams ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" status = ? and value = ? ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" type = ? ORDER BY code");

        Query query = getSession().createQuery(sqlBuffer.toString());
        query.setParameter(0, status);
        query.setParameter(1, String.valueOf(channelTypeId));
        query.setParameter(2, type);

        List list = query.list();

        log.info("End");
        return list;
    }

    /**
     *
     * author tutm1
     * date: 21/09/2011
     * lay danh sach loai dai ly agent_type theo loai kenh Channel_type (Ajax loading)
     *
     */
    public String getComboboxSource() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();

            //Lay danh sach mat hang tu loai mat hang
            String channelTypeIdStr = httpServletRequest.getParameter("channelTypeId");
            String strTarget = httpServletRequest.getParameter("target");
            String[] arrTarget = strTarget.split(",");
            if (channelTypeIdStr != null && !channelTypeIdStr.equals("")) {
                Long channelTypeId = Long.parseLong(channelTypeIdStr);
                List lst = new ArrayList();
                String[] header = {"", getText("label.option")};
                lst.add(header);
                List lstAgentType = getAgentTypeByChannelType(channelTypeId, Constant.APP_PARAMS_TYPE_STAFF_AGENT_TYPE, Constant.STATUS_USE.toString());
                lst.addAll(lstAgentType);
                listItemsCombo.put(arrTarget[0], lst);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "getComboboxSource";
    }

    public void getDataForComboBox() throws Exception {
        HttpServletRequest req = getRequest();
        String sql = " from ChannelType where 1=1  and objectType=? and isVtUnit = ? and status=?";
        Query qry = getSession().createQuery(sql);
        qry.setParameter(0, Constant.OBJECT_TYPE_SHOP);
        qry.setParameter(1, Constant.NOT_IS_VT_UNIT);
        qry.setParameter(2, Constant.STATUS_USE);
        //   qry.setParameter(3, Constant.CHANNEL_TYPE_CTV);
        List<ChannelType> lst = qry.list();
        ChannelType channelTypePointSale = new ChannelType();
        channelTypePointSale.setChannelTypeId(3L);
        channelTypePointSale.setCheckComm("1");
        channelTypePointSale.setIsVtUnit("2");
        channelTypePointSale.setName("Kênh Điểm Bán");
        channelTypePointSale.setObjectType("2");
        channelTypePointSale.setStatus(1L);
        channelTypePointSale.setStockReportTemplate(null);
        channelTypePointSale.setStockType(1L);
        channelTypePointSale.setTotalDebit(0L);
        ChannelType channelTypeCollarborator = new ChannelType();
        channelTypeCollarborator.setChannelTypeId(2L);
        channelTypeCollarborator.setCheckComm("1");
        channelTypeCollarborator.setIsVtUnit("2");
        channelTypeCollarborator.setName("Kênh NVĐB");
        channelTypeCollarborator.setObjectType("2");
        channelTypeCollarborator.setStatus(1L);
        channelTypeCollarborator.setStockReportTemplate(null);
        channelTypeCollarborator.setStockType(1L);
        channelTypeCollarborator.setTotalDebit(0L);
        lst.add(channelTypePointSale);
        lst.add(channelTypeCollarborator);
        req.setAttribute("lstChannelTypeId", lst);

    }

    public void getDataForComboBoxChannel() throws Exception {
        HttpServletRequest req = getRequest();
        String sql = " from ChannelType where 1=1 and status=?";
        Query qry = getSession().createQuery(sql);
        //  qry.setParameter(0, Constant.OBJECT_TYPE_SHOP);
        qry.setParameter(0, Constant.STATUS_USE);
        //  qry.setParameter(1, Constant.STATUS_USE);
        //  qry.setParameter(2, Constant.CHANNEL_TYPE_CTV);
        List<ChannelType> lst = qry.list();
//        ChannelType channelTypePointSale = new ChannelType();
//        channelTypePointSale.setChannelTypeId(3L);
//        channelTypePointSale.setCheckComm("1");
//        channelTypePointSale.setIsVtUnit("2");
//        channelTypePointSale.setName("Kênh Điểm Bán");
//        channelTypePointSale.setObjectType("2");
//        channelTypePointSale.setStatus(1L);
//        channelTypePointSale.setStockReportTemplate(null);
//        channelTypePointSale.setStockType(1L);
//        channelTypePointSale.setTotalDebit(0L);
//        ChannelType channelTypeCollarborator = new ChannelType();
//        channelTypeCollarborator.setChannelTypeId(2L);
//        channelTypeCollarborator.setCheckComm("1");
//        channelTypeCollarborator.setIsVtUnit("2");
//        channelTypeCollarborator.setName("Kênh Cộng Tác Viên");
//        channelTypeCollarborator.setObjectType("2");
//        channelTypeCollarborator.setStatus(1L);
//        channelTypeCollarborator.setStockReportTemplate(null);
//        channelTypeCollarborator.setStockType(1L);
//        channelTypeCollarborator.setTotalDebit(0L);
//        lst.add(channelTypePointSale);
//        lst.add(channelTypeCollarborator);
        req.setAttribute("lstChannelTypeId", lst);

    }

    public String reportListAccountAgent() throws Exception {
        log.info("Begin method reportRevenue of ReportRevenueDAO...");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_ACCOUNT_AGENT;
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String shopName = "";
        Long channelTypeId = reportRevenueForm.getChannelTypeId();
        String provinceCode = reportRevenueForm.getProvinceCode();
        String shopCode = reportRevenueForm.getShopCode();
        Shop shopProvince = getShopId(provinceCode);
        String channelTypeName = "";
        String objectType = "";
        //getDataForComboBox();
        ChannelTypeDAO channelDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        if (provinceCode != null && !provinceCode.equals("") && shopProvince == null) {
//            req.setAttribute(REQUEST_MESSAGE, "Mã tỉnh chưa chính xác");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.013");
            return pageForward;
        }
        if (shopProvince != null) {
            shopName = shopProvince.getName();
        }
        Shop shop = getShopId(shopCode);
        if (shopCode != null && !shopCode.equals("") && shop == null) {
//            req.setAttribute(REQUEST_MESSAGE, "Mã cửa hàng chưa chính xác");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.012");
            return pageForward;
        }
        if (shop != null) {
            shopName = shop.getName();
        }

        ChannelType channelType = null;
        if (channelTypeId != null) {
            channelType = channelDAO.findById(channelTypeId);
        }
        channelTypeName = channelType != null ? channelType.getName() : "";
        objectType = channelType != null ? channelType.getObjectType() : "";
        //Start
        ViettelMsg request = new OriginalViettelMsg();
        request.set("CHANNEL_TYPE_ID", channelTypeId);
        request.set("OBJECT_TYPE", objectType);
        request.set("PROVINCE_CODE", provinceCode);
        if (shop != null) {
            request.set("SHOP_ID", shop.getShopId());
        } else {
            request.set("SHOP_ID", shopProvince.getShopId());
        }
        request.set("SHOP_NAME", shopName);
        request.set("CHANNEL_TYPE_NAME", channelTypeName);
        request.set("STATUS_STK", reportRevenueForm.getStatusSTK());
        request.set("STATUS_ACCOUNT_BALANCE", reportRevenueForm.getStatusAcountBalance());
        request.set("STATUS_ANYPAY", reportRevenueForm.getStatusAnyPay());
        request.set("STATUS_OWNER", reportRevenueForm.getStatusOwner());

        request.set("USER_CODE", userToken.getLoginName());
        request.set("USER_NAME", userToken.getStaffName());

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_LIST_ACCOUNT_AGENT);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            //Thong bao len jsp
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "report.stocktrans.error.successMessage");
        } else {
            //getDataForComboBox();
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }
        getDataForComboBox();
        return pageForward;




//        List<ViewAccountSubscriberBean> listSubscriberBeanDetail = new ArrayList<ViewAccountSubscriberBean>();
//        String templatePathResource = "";
//        //lay danh sach list bao cao
//        if (provinceCode == null || provinceCode.equals("")) {
//            //bao cao tong hop
//            if (channelTypeId != null) {
//                if (channelTypeId.equals(1L)) {
//                    //cho dai ly
//                    listSubscriberBeanDetail = getlistReportAccountAgentGeneralAgent();
//                } else {
//                    //cho CTV/DB
//                    if (channelTypeId.equals(2L)) {
//                        //CTV
//                        listSubscriberBeanDetail = getlistReportAccountAgentGeneralStaff(2L);
//                    } else {
//                        if (channelTypeId.equals(3L)) {
//                            //DB
//                            listSubscriberBeanDetail = getlistReportAccountAgentGeneralStaff(1L);
//                        }
//                    }
//                }
//            } else {
//                listSubscriberBeanDetail.addAll(getlistReportAccountAgentGeneralAgent());
//                listSubscriberBeanDetail.addAll(getlistReportAccountAgentGeneralStaff(2L));
//                listSubscriberBeanDetail.addAll(getlistReportAccountAgentGeneralStaff(1L));
//            }
//
//            templatePathResource = "report_listAccountAgent_General";
//        } else {
//            //bao cao chi tiet
//            if (channelTypeId != null) {
//                if (channelTypeId.equals(1L)) {
//                    //cho dai ly
//                    listSubscriberBeanDetail = getlistReportAccountAgentDetailAgent();
//                } else {
//                    //cho CTV/DB
//                    if (channelTypeId.equals(2L)) {
//                        //CTV
//                        listSubscriberBeanDetail = getlistReportAccountAgentDetailStaff(2L);
//                    } else {
//                        if (channelTypeId.equals(3L)) {
//                            //DB
//                            listSubscriberBeanDetail = getlistReportAccountAgentDetailStaff(1L);
//                        }
//                    }
//                }
//            } else {
//                listSubscriberBeanDetail.addAll(getlistReportAccountAgentDetailAgent());
//                listSubscriberBeanDetail.addAll(getlistReportAccountAgentDetailStaff());
//            }
//            templatePathResource = "report_listAccountAgent_Detail";
//        }
//        //ket xuat ket qua ra file excel
//        try {
//            String DATE_FORMAT = "yyyyMMddHHmmss";
//            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
//            filePath = filePath != null ? filePath : "/";
//            //UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//            filePath += "reportAccountSubscriber_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
//            String realPath = req.getSession().getServletContext().getRealPath(filePath);
//
//            String templatePath = ResourceBundleUtils.getResource(templatePathResource);
//            if (templatePath == null || templatePath.trim().equals("")) {
//                //lay du lieu cho cac combobox
//                //khong tim thay duong dan den file template de xuat ket qua
//                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.templateNotExist");
//                return pageForward;
//            }
//
//            String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
//            File fTemplateFile = new File(realTemplatePath);
//            if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
//                //khong tim thay file template de xuat ket qua
//                req.setAttribute(REQUEST_MESSAGE, "revokeIsdn.error.templateNotExist");
//                return pageForward;
//            }
//            Map beans = new HashMap();
//            beans.put("listAccountBook", listSubscriberBeanDetail);
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            beans.put("reportDate", simpleDateFormat.format(DateTimeUtils.getSysDate()));
//            beans.put("shopName", shopName);
//            beans.put("userCreate", userToken.getLoginName());
//            if (reportRevenueForm.getChannelTypeId() != null && reportRevenueForm.getChannelTypeId().equals(1L)) {
//                beans.put("channelTypeName", "Đại lý");
//            } else {
//                if (reportRevenueForm.getChannelTypeId() != null && reportRevenueForm.getChannelTypeId().equals(2L)) {
//                    beans.put("channelTypeName", "Cộng tác viên");
//                } else {
//                    if (reportRevenueForm.getChannelTypeId() != null && reportRevenueForm.getChannelTypeId().equals(3L)) {
//                        beans.put("channelTypeName", "Điểm bán");
//                    }
//                }
//            }
//
//            XLSTransformer transformer = new XLSTransformer();
//            transformer.transformXLS(realTemplatePath, beans, realPath);
//
//            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "reportRevenue.reportRevenueMessage");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
//            return pageForward;
//        }
//        pageForward = REPORT_ACCOUNT_AGENT;
//
//        log.info("End method reportRevenue of ReportRevenueDAO");
//        return pageForward;
    }

    public String reportListChannel() throws Exception {
        log.info("Begin method reportRevenue of ReportRevenueDAO...");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_LIST_CHANNEL;
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        // tutm1 comment 05/09/2011, xuat bao cao theo kenh chon tren giao dien thay vi fix cung' kenh diem ban
//        Long channelTypeId = Constant.CHANNEL_TYPE_DB;//reportRevenueForm.getChannelTypeId();
        Long channelTypeId = reportRevenueForm.getChannelTypeId();
        String shopCode = reportRevenueForm.getShopCode();
        String shopName = reportRevenueForm.getShopName();
        Long agentType = reportRevenueForm.getAgentType() == null ? -1L : reportRevenueForm.getAgentType();
        Long statusStaff = reportRevenueForm.getStatusStaff() == null ? -1L : reportRevenueForm.getStatusStaff();
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        String agentTypeText = "";
        String statusStaffText = "";
        if (agentType.equals(-1L)) {
            //Tất cả các loại đại lý
            agentTypeText = getText("label.all");
        } else {
            AppParams app = appParamsDAO.findAppParams(Constant.APP_PARAMS_TYPE_STAFF_AGENT_TYPE, agentType.toString());
            if (app != null) {
                agentTypeText = app.getName();
            }
        }
        if (statusStaff.equals(-1L)) {
            //Tất cả trạng thái đại lý
            statusStaffText = getText("label.all");
        } else {
            AppParams app = appParamsDAO.findAppParams(Constant.APP_PARAMS_TYPE_STAFF_STATUS, statusStaff.toString());
            if (app != null) {
                statusStaffText = app.getName();
            }
        }
        if (shopCode == null || "".equals(shopCode)) {
            /*chưa nhập mã cửa hàng*/
            req.setAttribute(REQUEST_MESSAGE, "ERR.REPORT.0001");
            return pageForward;
        }
        Shop shop = getShopId(shopCode);
        if (shopCode != null && !shopCode.equals("") && shop == null) {
            /*Mã cửa hàng chưa chính xác*/
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.012");
            return pageForward;
        }
        if (shop != null) {
            shopName = shop.getName();
        }

        // tutm1 05/09/2011 : load danh sach kenh ctv, db
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);

        ChannelTypeDAO channelDAO = new ChannelTypeDAO();
        channelDAO.setSession(this.getSession());

        ViettelMsg request = new OriginalViettelMsg();
        request.set("CHANNEL_TYPE_ID", channelTypeId);

        if (shop != null) {
            request.set("SHOP_ID", shop.getShopId());
        }
        request.set("SHOP_NAME", shopName);
        request.set("AGENT_TYPE", agentType);
        request.set("AGENT_TYPE_TEXT", agentTypeText);
        request.set("STATUS_STAFF", statusStaff);
        request.set("STATUS_STAFF_TEXT", statusStaffText);
        request.set("USER_CODE", userToken.getLoginName());
        request.set("USER_NAME", userToken.getStaffName());

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_LIST_CHANNEL);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            //Thong bao len jsp
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "report.stocktrans.error.successMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }
        //set lai cac list attrubute
        req.setAttribute("listAgentType", appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_STAFF_AGENT_TYPE, Constant.STATUS_USE.toString()));
        req.setAttribute("listStaffStatus", appParamsDAO.findAppParamsList(Constant.APP_PARAMS_TYPE_STAFF_STATUS, Constant.STATUS_USE.toString()));
        return pageForward;
    }

    public Staff getStaffId(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());

        if (staff != null) {
            return staff;
        }
        return null;
    }

    public Shop getShopId(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }

    public List<ImSearchBean> getListProvince(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        Shop shop = getShopId(userToken.getShopCode());
        if (shop != null) {
            String shop_path = shop.getShopPath() + "_";
            int index = shop_path.indexOf("_", 1);
            if (index != -1 && index < shop_path.length()) {
                int index1 = shop_path.indexOf("_", index + 1);
                if (index1 != -1) {
                    String shopId = shop_path.substring(index + 1, index1);
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    Shop shopLogin = shopDAO.findById(Long.parseLong(shopId));
                    if (shopLogin != null) {
                        ImSearchBean imSearchBeanAdd = new ImSearchBean(shopLogin.getShopCode(), shopLogin.getName());
                        listImSearchBean.add(imSearchBeanAdd);
                        return listImSearchBean;
                    }
                }
            }
        }
        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and (parentShopId = 7282 or shopId = 7282) ");
        strQuery1.append("and status = 1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(100L);
        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");
        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);

        }
        return listImSearchBean;

    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();//req.getParameter("code");
//        if (shopCode == null || "".equals(shopCode)) {
//            shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();
//        }

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List lstParam = new ArrayList();
        StringBuilder sql = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");

        sql.append(" FROM Shop a WHERE LOWER(a.shopPath || '_') LIKE LOWER(?)");
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        if (shopCode != null && !"".equals(shopCode)) {
            sql.append(" AND LOWER(a.shopCode) LIKE ? ");
            lstParam.add("%" + shopCode.toLowerCase() + "%");
        }

        sql.append(" and a.status =? ");
        lstParam.add(Constant.STATUS_USE);
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            sql.append(" AND LOWER(a.name) LIKE ? ");
            lstParam.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        sql.append(" ORDER BY a.shopCode");
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        listImSearchBean = query.list();

        /*
        List listParameter1 = new ArrayList();
        //lay danh sach cua hang thuoc tinh
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.shopName) ");
        strQuery1.append("from TblShopTree a ");
        strQuery1.append("where 1 = 1 ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
        strQuery1.append("and rootId = 7282 ");
        //listParameter1.add(getShopId(imSearchBean.getOtherParamValue().toString()).getShopId());
        } else {
        return listImSearchBean;
        }
        strQuery1.append("and a.channelTypeId =? ");
        listParameter1.add(Constant.CHANNEL_TYPE_SHOP);
        strQuery1.append(" and a.shopStatus =? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and shop_level > 3 ");
        strQuery1.append("and a.shopPathTree like ? ");
        listParameter1.add(getShopId(imSearchBean.getOtherParamValue().toString()).getShopPath() + "_%");
        strQuery1.append("and (a.shopPathTree like ? or a.id.shopId = ?) ");
        listParameter1.add(getShopId(userToken.getShopCode()).getShopPath() + "_%");
        listParameter1.add(userToken.getShopId());
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
        strQuery1.append("and lower(a.shopCode) like ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
        strQuery1.append("and lower(a.shopName) like ? ");
        listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(100L);
        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");
        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
        query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
        listImSearchBean.addAll(tmpList1);
        }
         */
        return listImSearchBean;
    }

    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode().trim();//req.getParameter("code");
//        if (shopCode == null || "".equals(shopCode)) {
//            shopCode = imSearchBean.getCode() == null ? "" : imSearchBean.getCode();
//        }
        List lstParam = new ArrayList();
        StringBuilder sql = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");

        sql.append(" FROM Shop a WHERE LOWER(a.shopCode) = ? AND LOWER(a.shopPath || '_') LIKE LOWER(?)");
        lstParam.add(shopCode.trim().toLowerCase());
        lstParam.add("%_" + userToken.getShopId().toString() + "_%");

        sql.append(" AND a.status = ? ");
        lstParam.add(Constant.STATUS_USE);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        Query query = getSession().createQuery(sql.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            query.setParameter(i, lstParam.get(i));
        }
        listImSearchBean = query.list();
        return listImSearchBean;
    }

    private List<ViewAccountSubscriberBean> getlistReportAccountAgentGeneralAgent() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        Long shopId = 0L;
        String provinceCode = reportRevenueForm.getProvinceCode();
        if (provinceCode == null || provinceCode.equals("")) {
            shopId = 7282L;
        } else {
            shopId = getShopId(provinceCode).getShopId();
        }
        strNormalSaleTransQuery.append(""
                + " SELECT   shop_code || ' - ' ||shop_name as shopCode, shop_name as shopName,"
                + " NVL (SUM (CASE"
                + " WHEN status = 0"
                + " THEN count_id"
                + " ELSE 0"
                + " END), 0) STATUS0,"
                + " NVL (SUM (CASE"
                + " WHEN status = 1"
                + " THEN count_id"
                + " ELSE 0"
                + " END), 0) STATUS1,"
                + " NVL (SUM (CASE"
                + " WHEN status = 2"
                + " THEN count_id"
                + " ELSE 0"
                + " END), 0) STATUS2,"
                + " 'Đại lý' channelTypeName"
                + " FROM (SELECT   svt.shop_code, svt.shop_name, ag.status,"
                + " COUNT (ag.account_id) count_id"
                + " FROM account_agent ag,"
                + " shop sh,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name,"
                + " shop_id, shop_code AS shop_code_select"
                + " FROM TBL_SHOP_TREE"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)) svt"
                + " WHERE 1 = 1"
                + " AND ag.owner_id = sh.shop_id"
                + " AND ag.owner_type = 1"
                + " AND sh.shop_id = svt.shop_id"
                + " GROUP BY svt.shop_code, svt.shop_name, ag.status"
                + " ORDER BY svt.shop_code, ag.status ASC)"
                + " GROUP BY shop_code, shop_name");
        listParameter.add(shopId);
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("STATUS0", Hibernate.LONG).addScalar("STATUS1", Hibernate.LONG).addScalar("STATUS2", Hibernate.LONG).addScalar("channelTypeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listView = query.list();
        return listView;
    }

    private List<ViewAccountSubscriberBean> getlistReportAccountAgentGeneralStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        Long shopId = 0L;
        String provinceCode = reportRevenueForm.getProvinceCode();
        if (provinceCode == null || provinceCode.equals("")) {
            shopId = 7282L;
        } else {
            shopId = getShopId(provinceCode).getShopId();
        }
        strNormalSaleTransQuery.append(""
                + " SELECT   shop_code || ' - ' || shop_name as shopCode,shop_name as shopName,"
                + " NVL (SUM (CASE"
                + " WHEN status = 0"
                + " THEN count_id"
                + " ELSE 0"
                + " END), 0) STATUS0,"
                + " NVL (SUM (CASE"
                + " WHEN status = 1"
                + " THEN count_id"
                + " ELSE 0"
                + " END), 0) STATUS1,"
                + " NVL (SUM (CASE"
                + " WHEN status = 2"
                + " THEN count_id"
                + " ELSE 0"
                + " END), 0) STATUS2,");
        if (pointOfSale.equals(2L)) {
            strNormalSaleTransQuery.append(" 'Cộng tác viên' channelTypeName");
        } else {
            strNormalSaleTransQuery.append(" 'Điểm bán' channelTypeName");
        }
        strNormalSaleTransQuery.append(" FROM (SELECT   svt.shop_code, svt.shop_name, ag.status,"
                + " COUNT (ag.account_id) count_id"
                + " FROM account_agent ag,"
                + " staff sh,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name,"
                + " shop_id, shop_code AS shop_code_select"
                + " FROM TBL_SHOP_TREE"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)) svt"
                + " WHERE 1 = 1"
                + " AND ag.owner_id = sh.staff_id"
                + " AND ag.owner_type = 2"
                + " AND sh.staff_owner_id is not null"
                + " AND sh.shop_id = svt.shop_id"
                + " AND sh.point_of_sale = ?");
        listParameter.add(shopId);
        listParameter.add(pointOfSale);
        strNormalSaleTransQuery.append(""
                + " GROUP BY svt.shop_code, svt.shop_name, ag.status"
                + " ORDER BY svt.shop_code, ag.status ASC)"
                + " GROUP BY shop_code, shop_name");

        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("STATUS0", Hibernate.LONG).addScalar("STATUS1", Hibernate.LONG).addScalar("STATUS2", Hibernate.LONG).addScalar("channelTypeName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listView = query.list();
        return listView;
    }

    private List<ViewAccountSubscriberBean> getlistReportAccountAgentDetailAgent() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        String provinceCode = reportRevenueForm.getProvinceCode();
        Long shopId = 0L;
        if (provinceCode == null || provinceCode.equals("")) {
            shopId = 7282L;
        } else {
            Shop shop = getShopId(provinceCode);
            if (shop != null) {
                shopId = shop.getShopId();
            }
        }
        String shopCode = reportRevenueForm.getShopCode();
        if (shopCode != null && !shopCode.equals("")) {
            Shop shop = getShopId(shopCode);
            if (shop != null) {
                shopId = shop.getShopId();
            }
        }
        strNormalSaleTransQuery.append(""
                + " SELECT  svt.shop_code || ' - '|| svt.shop_name as shopCode,  svt.shop_name as shopName, ag.isdn as isdn,sh.shop_code as ownerCode, sh.NAME as ownerName, ag.birth_date as birthDate,"
                + " ag.id_no as idNo, ag.issue_date as makeDate, ag.issue_place as makeAddress, ag.contact_no as contact,"
                + " ag.outlet_address as address,sh1.shop_code || ' - ' || sh1.Name as ownerManagementName,"
                + " DECODE (ag.status, 0, 'Ngưng hoạt động', 1, 'Đang hoạt động', 2, 'Hủy') AS statusSTKName,"
                + " DECODE (ab.status, 2, 'Ngưng hoạt động', 1, 'Đang hoạt động', 3, 'Hủy') AS statusAcountBalanceName,"
                + " DECODE (sh.status, 1, 'Đang hoạt động', 2, 'Không hoạt động') AS statusOwnerName,"
                + " DECODE (ag.status_anypay,"
                + " 0, 'Ngưng hoạt động',"
                + " 1, 'Đang hoạt động',"
                + " 9, 'Hủy',"
                + " 2, 'Ngưng hoạt động'"
                + " ) AS statusAnyPayName,"
                + " ag.date_created as dateCreated"
                + " FROM account_agent ag,"
                + " account_balance ab,"
                + " shop sh,"
                + " shop sh1,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM TBL_SHOP_TREE"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)"
                + " UNION"
                + " SELECT shop_code AS shop_code, NAME AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM shop"
                + " WHERE shop_id = ?) svt"
                + " WHERE 1 = 1"
                + " AND ag.owner_id = sh.shop_id"
                + " AND ag.owner_type = 1"
                + " AND sh1.shop_id = svt.shop_id"
                + " AND ag.account_id = ab.account_id(+)"
                + " AND sh.parent_shop_id = sh1.shop_id");
        listParameter.add(shopId);
        listParameter.add(shopId);
        if (reportRevenueForm.getStatusSTK() != null) {
            strNormalSaleTransQuery.append(" AND ag.status = ? ");
            listParameter.add(reportRevenueForm.getStatusSTK());
        }
        if (reportRevenueForm.getStatusAcountBalance() != null) {
            strNormalSaleTransQuery.append(" AND ab.status = ? ");
            listParameter.add(reportRevenueForm.getStatusAcountBalance());
        }
        if (reportRevenueForm.getStatusAnyPay() != null) {
            strNormalSaleTransQuery.append(" AND ag.status_anypay = ? ");
            listParameter.add(reportRevenueForm.getStatusAnyPay());
        }
        if (reportRevenueForm.getStatusOwner() != null) {
            strNormalSaleTransQuery.append(" AND sh.status = ? ");
            listParameter.add(reportRevenueForm.getStatusOwner());
        }
        strNormalSaleTransQuery.append(" ORDER BY svt.shop_code, ag.status ASC");
        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("isdn", Hibernate.STRING).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("birthDate", Hibernate.DATE).addScalar("idNo", Hibernate.STRING).addScalar("makeDate", Hibernate.DATE).addScalar("makeAddress", Hibernate.STRING).addScalar("contact", Hibernate.STRING).addScalar("address", Hibernate.STRING).addScalar("ownerManagementName", Hibernate.STRING).addScalar("statusSTKName", Hibernate.STRING).addScalar("statusAcountBalanceName", Hibernate.STRING).addScalar("statusOwnerName", Hibernate.STRING).addScalar("statusAnyPayName", Hibernate.STRING).addScalar("dateCreated", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));
        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listView = query.list();
        return listView;
    }

    private List<ViewAccountSubscriberBean> getlistReportAccountAgentDetailStaff() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        String provinceCode = reportRevenueForm.getProvinceCode();
        Long shopId = 0L;
        if (provinceCode == null || provinceCode.equals("")) {
            shopId = 7282L;
        } else {
            Shop shop = getShopId(provinceCode);
            if (shop != null) {
                shopId = shop.getShopId();
            }
        }
        String shopCode = reportRevenueForm.getShopCode();
        if (shopCode != null && !shopCode.equals("")) {
            Shop shop = getShopId(shopCode);
            if (shop != null) {
                shopId = shop.getShopId();
            }
        }
        strNormalSaleTransQuery.append(""
                + " SELECT  svt.shop_code || ' - ' || svt.shop_name as shopCode,  svt.shop_name as shopName, ag.isdn as isdn,"
                + " sh.staff_code as ownerCode, sh.NAME as ownerName, ag.birth_date as birthDate, ag.id_no as idNo, ag.issue_date as makeDate,"
                + " ag.issue_place as makeAddress, ag.contact_no as contact, ag.outlet_address as address,"
                + " sh1.staff_code || ' - ' || sh1.NAME AS ownerManagementName,"
                + " DECODE (ag.status, 0, 'Ngưng hoạt động', 1, 'Đang hoạt động', 2, 'Hủy') AS statusSTKName,"
                + " DECODE (ab.status, 2, 'Ngưng hoạt động', 1, 'Đang hoạt động', 3, 'Hủy') AS statusAcountBalanceName,"
                + " DECODE (sh.status, 1, 'Đang hoạt động', 0, 'Không hoạt động') AS statusOwnerName,"
                + " DECODE (ag.status_anypay,"
                + " 0, 'Ngưng hoạt động',"
                + " 1, 'Đang hoạt động',"
                + " 9, 'Hủy',"
                + " 2, 'Ngưng hoạt động'"
                + " ) AS statusAnyPayName,"
                + " ag.date_created as dateCreated"
                + " FROM account_agent ag,"
                + " account_balance ab,"
                + " staff sh,"
                + " staff sh1,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM TBL_SHOP_TREE"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)"
                + " UNION"
                + " SELECT shop_code AS shop_code, NAME AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM shop"
                + " WHERE shop_id = ?) svt"
                + " WHERE 1 = 1"
                + " AND ag.owner_id = sh.staff_id"
                + " AND ag.owner_type = 2"
                + " AND sh.shop_id = svt.shop_id"
                + " AND sh.staff_owner_id is not null"
                + " AND ag.account_id = ab.account_id(+)"
                + " AND sh.staff_owner_id = sh1.staff_id"
                + " AND sh.point_of_sale  in (1,2)");

        listParameter.add(shopId);
        listParameter.add(shopId);
        if (reportRevenueForm.getStatusSTK() != null) {
            strNormalSaleTransQuery.append(" AND ag.status = ? ");
            listParameter.add(reportRevenueForm.getStatusSTK());
        }
        if (reportRevenueForm.getStatusAcountBalance() != null) {
            strNormalSaleTransQuery.append(" AND ab.status = ? ");
            listParameter.add(reportRevenueForm.getStatusAcountBalance());
        }
        if (reportRevenueForm.getStatusAnyPay() != null) {
            strNormalSaleTransQuery.append(" AND ag.status_anypay = ? ");
            listParameter.add(reportRevenueForm.getStatusAnyPay());
        }
        if (reportRevenueForm.getStatusOwner() != null) {
            strNormalSaleTransQuery.append(" AND sh.status = ? ");
            listParameter.add(reportRevenueForm.getStatusOwner());
        }
        strNormalSaleTransQuery.append(""
                + " ORDER BY svt.shop_code, ag.status ASC");

        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("isdn", Hibernate.STRING).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("birthDate", Hibernate.DATE).addScalar("idNo", Hibernate.STRING).addScalar("makeDate", Hibernate.DATE).addScalar("makeAddress", Hibernate.STRING).addScalar("contact", Hibernate.STRING).addScalar("address", Hibernate.STRING).addScalar("ownerManagementName", Hibernate.STRING).addScalar("statusSTKName", Hibernate.STRING).addScalar("statusAcountBalanceName", Hibernate.STRING).addScalar("statusOwnerName", Hibernate.STRING).addScalar("statusAnyPayName", Hibernate.STRING).addScalar("dateCreated", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listView = query.list();
        return listView;
    }

    private List<ViewAccountSubscriberBean> getlistReportAccountAgentDetailStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        String provinceCode = reportRevenueForm.getProvinceCode();
        Long shopId = 0L;
        if (provinceCode == null || provinceCode.equals("")) {
            shopId = 7282L;
        } else {
            Shop shop = getShopId(provinceCode);
            if (shop != null) {
                shopId = shop.getShopId();
            }
        }
        String shopCode = reportRevenueForm.getShopCode();
        if (shopCode != null && !shopCode.equals("")) {
            Shop shop = getShopId(shopCode);
            if (shop != null) {
                shopId = shop.getShopId();
            }
        }
        strNormalSaleTransQuery.append(""
                + " SELECT  svt.shop_code || ' - ' || svt.shop_name as shopCode,  svt.shop_name as shopName, ag.isdn as isdn,"
                + " sh.staff_code as ownerCode, sh.NAME as ownerName, ag.birth_date as birthDate, ag.id_no as idNo, ag.issue_date as makeDate,"
                + " ag.issue_place as makeAddress, ag.contact_no as contact, ag.outlet_address as address,"
                + " sh1.staff_code || ' - ' || sh1.NAME AS ownerManagementName,"
                + " DECODE (ag.status, 0, 'Ngưng hoạt động', 1, 'Đang hoạt động', 2, 'Hủy') AS statusSTKName,"
                + " DECODE (ab.status, 2, 'Ngưng hoạt động', 1, 'Đang hoạt động', 3, 'Hủy') AS statusAcountBalanceName,"
                + " DECODE (sh.status, 1, 'Đang hoạt động', 0, 'Không hoạt động') AS statusOwnerName,"
                + " DECODE (ag.status_anypay,"
                + " 0, 'Ngưng hoạt động',"
                + " 1, 'Đang hoạt động',"
                + " 9, 'Hủy',"
                + " 2, 'Ngưng hoạt động'"
                + " ) AS statusAnyPayName,"
                + " ag.date_created as dateCreated"
                + " FROM account_agent ag,"
                + " account_balance ab,"
                + " staff sh,"
                + " staff sh1,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM TBL_SHOP_TREE"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)"
                + " UNION"
                + " SELECT shop_code AS shop_code, NAME AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM shop"
                + " WHERE shop_id = ?) svt"
                + " WHERE 1 = 1"
                + " AND ag.owner_id = sh.staff_id"
                + " AND ag.owner_type = 2"
                + " AND sh.shop_id = svt.shop_id"
                + " AND sh.staff_owner_id is not null"
                + " AND ag.account_id = ab.account_id(+)"
                + " AND sh.staff_owner_id = sh1.staff_id"
                + " AND sh.point_of_sale = ?");
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(pointOfSale);
        if (reportRevenueForm.getStatusSTK() != null) {
            strNormalSaleTransQuery.append(" AND ag.status = ? ");
            listParameter.add(reportRevenueForm.getStatusSTK());
        }
        if (reportRevenueForm.getStatusAcountBalance() != null) {
            strNormalSaleTransQuery.append(" AND ab.status = ? ");
            listParameter.add(reportRevenueForm.getStatusAcountBalance());
        }
        if (reportRevenueForm.getStatusAnyPay() != null) {
            strNormalSaleTransQuery.append(" AND ag.status_anypay = ? ");
            listParameter.add(reportRevenueForm.getStatusAnyPay());
        }
        if (reportRevenueForm.getStatusOwner() != null) {
            strNormalSaleTransQuery.append(" AND sh.status = ? ");
            listParameter.add(reportRevenueForm.getStatusOwner());
        }
        strNormalSaleTransQuery.append(""
                + " ORDER BY svt.shop_code, ag.status ASC");

        Query query = getSession().createSQLQuery(strNormalSaleTransQuery.toString()).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("isdn", Hibernate.STRING).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("birthDate", Hibernate.DATE).addScalar("idNo", Hibernate.STRING).addScalar("makeDate", Hibernate.DATE).addScalar("makeAddress", Hibernate.STRING).addScalar("contact", Hibernate.STRING).addScalar("address", Hibernate.STRING).addScalar("ownerManagementName", Hibernate.STRING).addScalar("statusSTKName", Hibernate.STRING).addScalar("statusAcountBalanceName", Hibernate.STRING).addScalar("statusOwnerName", Hibernate.STRING).addScalar("statusAnyPayName", Hibernate.STRING).addScalar("dateCreated", Hibernate.DATE).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listView = query.list();
        return listView;
    }
}
