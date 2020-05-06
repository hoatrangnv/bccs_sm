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
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
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
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Vunt
 */
public class ReportDevelopSubscriberDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(ReportDevelopSubscriberDAO.class);
    private String pageForward;
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();
    private final String REPORT_ACCOUNT_AGENT = "reportSubscriber";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";

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
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
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
                        this.reportRevenueForm.setStaffManageCode(userToken.getLoginName().toUpperCase());
                        this.reportRevenueForm.setStaffManageName(userToken.getStaffName());
                    }
                }
            }
        }

        pageForward = REPORT_ACCOUNT_AGENT;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    public String reportSubscriber() throws Exception {
        log.info("Begin method reportRevenue of ReportRevenueDAO...");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_ACCOUNT_AGENT;
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);

        }
        Calendar calFromDate = Calendar.getInstance();
        calFromDate.setTime(fromDate);
        Calendar calToDate = Calendar.getInstance();
        calToDate.setTime(toDate);
        if (calFromDate.get(Calendar.MONTH) != calToDate.get(Calendar.MONTH)) {
            //req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một tháng");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.014");
            return pageForward;
        }
        if (calFromDate.get(Calendar.YEAR) != calToDate.get(Calendar.YEAR)) {
            //req.setAttribute(REQUEST_MESSAGE, "Phải chọn từ ngày đến ngày trong cùng một năm");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.015");
            return pageForward;
        }
        Long reportType = this.reportRevenueForm.getReportType();

        String shopName = "";
        Long shopId = 7282L;
        Long channelTypeId = reportRevenueForm.getChannelTypeId();
        String provinceCode = reportRevenueForm.getProvinceCode();
        String shopCode = reportRevenueForm.getShopCode();
        Shop shopProvince = getShopId(provinceCode);

        if (provinceCode != null && !provinceCode.equals("") && shopProvince == null) {
            //req.setAttribute(REQUEST_MESSAGE, "Mã tỉnh chưa chính xác");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.013");
            return pageForward;
        }
        if (shopProvince != null) {
            shopName = shopProvince.getShopCode() + " - " + shopProvince.getName();
            shopId = shopProvince.getShopId();
        }
        Shop shop = getShopId(shopCode);
        if (shopCode != null && !shopCode.equals("") && shop == null) {
            //req.setAttribute(REQUEST_MESSAGE, "Mã cửa hàng chưa chính xác");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.012");
            return pageForward;
        }
        if (shop != null) {
            shopName = shop.getShopCode() + " - " + shop.getName();
            shopId = shop.getShopId();
        }
        if (!reportRevenueForm.getBilledSaleTrans() && !reportRevenueForm.getCancelSaleTrans() && !reportRevenueForm.getNotBilledSaleTrans()) {
            //req.setAttribute(REQUEST_MESSAGE, "Chưa chọn trạng thái hóa đơn");
            req.setAttribute(REQUEST_MESSAGE, "ERR.RET.016");
            return pageForward;
        }
        ChannelType channelType = channelTypeDAO.findById(channelTypeId);

//        List<ViewAccountSubscriberBean> listSubscriberBeanDetail = new ArrayList<ViewAccountSubscriberBean>();
//        String templatePathResource = "";
//        //lay danh sach list bao cao
//        if (reportType.equals(1L)) {
//            //bao cao tong hop
//            if (channelTypeId != null) {
//                if (channelTypeId.equals(1L)) {
//                    //cho dai ly
//                    listSubscriberBeanDetail = getlistSubscriberBeanGeneralAgent();
//                } else {
//                    //cho CTV/DB
//                    if (channelTypeId.equals(2L)) {
//                        //CTV
//                        listSubscriberBeanDetail = getlistSubscriberBeanGeneralStaff(2L);
//                    } else {
//                        if (channelTypeId.equals(3L)) {
//                            //DB
//                            listSubscriberBeanDetail = getlistSubscriberBeanGeneralStaff(1L);
//                        }
//                    }
//
//                }
//            }
//            templatePathResource = "report_accountAgentSubscriber_General";
//        } else {
//            if (reportType.equals(2L)) {
//                //bao cao chi tiet
//                if (channelTypeId != null) {
//                    if (channelTypeId.equals(1L)) {
//                        //cho dai ly
//                        listSubscriberBeanDetail = getlistSubscriberBeanDetailAgent();
//                    } else {
//                        //cho CTV/DB
//                        if (channelTypeId.equals(2L)) {
//                            //CTV
//                            listSubscriberBeanDetail = getlistSubscriberBeanDetailStaff(2L);
//                        } else {
//                            if (channelTypeId.equals(3L)) {
//                                //DB
//                                listSubscriberBeanDetail = getlistSubscriberBeanDetailStaff(1L);
//                            }
//                        }
//                    }
//                }
//
//                templatePathResource = "report_accountAgentSubscriber_Detail";
//            }
//        }
//
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
//            beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strFromDate)));
//            beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(strToDate)));
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

        //code chuyen sang report server
        ViettelMsg request = new OriginalViettelMsg();

        String sql = "From Shop where parentShopId = ? and channelTypeId = 1";
        Query sqlQuery = getSession().createQuery(sql);
        sqlQuery.setParameter(0, shopId);
        List<Shop> list = sqlQuery.list();
        if (list != null && list.size() >0){
            request.set("CHECK_SELECT", "true");
        }
        else{
            request.set("CHECK_SELECT", "false");
        }

        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());

        request.set("SHOP_NAME", shopName);
        request.set("SHOP_ID", shopId);
        request.set("CHANNEL_TYPE", channelTypeId);
        request.set("REPORT_TYPE", reportType);
        request.set("METHODE", reportRevenueForm.getMethode());
        request.set("ISDN_TYPE", reportRevenueForm.getIsdnType());
        request.set("STAFF_MANAGEMENT", reportRevenueForm.getStaffManageCode());
        request.set("Not_Billed_Sale_Trans", reportRevenueForm.getNotBilledSaleTrans());
        request.set("Billed_Sale_Trans", reportRevenueForm.getBilledSaleTrans());
        request.set("Cancel_Sale_Trans", reportRevenueForm.getCancelSaleTrans());
        request.set("OBJECT_TYPE", channelType.getObjectType());
        request.set("CHANNEL_TYPE_NAME", channelType.getName());

        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_DEVELOP_SUBSCRIBER);

        ViettelMsg response = ReportRequestSender.sendRequest(request);
        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }

        pageForward = REPORT_ACCOUNT_AGENT;

        log.info("End method reportRevenue of ReportRevenueDAO");
        return pageForward;


    }

    private List<ViewAccountSubscriberBean> getlistSubscriberBeanDetailStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
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

        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        strNormalSaleTransQuery.append(""
                + " SELECT a.sale_trans_id, a.sub_id, a.telecom_service_id, a.sale_service_id,"
                + " a.isdn, a.sale_trans_date, a.status, ss.code,"
                + " ss.NAME AS servicename, tl.tel_service_name, st.staff_code,"
                + " st.NAME AS staff_name, st1.staff_code AS staffcodemag,"
                + " st1.NAME AS staffnamemag, svt.shop_code, svt.shop_name,"
                + " b.stock_model_id, svt.shop_code_select, a.reason_id,ag.isdn as agentisdn,"
                + " DECODE (a.status,"
                + " 2, 'Chưa lập hóa đơn',"
                + " 3, 'Đã lập hóa đơn',"
                + " 4, 'Đã hủy'"
                + " ) as invoiceName,"
                + " DECODE (a.telecom_service_id,"
                + " 1, (SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 2, (SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn))"
                + " ) AS isdntype,"
                + " DECODE (a.telecom_service_id,"
                + " 1, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " ),"
                + " 2, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " ) "
                + " ) AS isdntypename"
                + " FROM sale_trans a,"
                + " sale_trans_detail b,"
                + " sale_services ss,"
                + " telecom_service tl,"
                + " account_agent ag,"
                + " staff st,"
                + " staff st1,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM v_shop_tree"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)"
                + " UNION"
                + " SELECT shop_code AS shop_code, NAME AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM shop"
                + " WHERE shop_id = ?) svt"
                + " WHERE 1 = 1"
                + " AND b.sale_trans_id = a.sale_trans_id"
                + " AND ss.sale_services_id = a.sale_service_id"
                + " AND tl.telecom_service_id = a.telecom_service_id"
                + " AND ag.owner_id = a.staff_id"
                + " AND ag.owner_type = 2"
                + " AND st.staff_id = ag.owner_id"
                + " AND st.staff_owner_id = st1.staff_id"
                + " AND a.shop_id = svt.shop_id"
                + " AND a.sale_trans_type = 7"
                + " AND a.sale_trans_date >= ?"
                + " AND a.sale_trans_date < ?"
                + " AND st.point_of_sale = ?"
                + " AND b.stock_model_id IN (SELECT stock_model_id"
                + " FROM stock_model "
                + " WHERE stock_type_id IN (1, 2))");
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(pointOfSale);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT a.code as  serviceCode, a.servicename as seviceName , a.tel_service_name as telecomServiceName,"
                + " a.staff_code ||' - ' || a.staff_name as ownerCode, a.staff_name as ownerName, a.staffcodemag as ownerManagementCode, a.staffnamemag as ownerManagementName,"
                + " a.shop_code as shopCode , a.shop_name as shopName,a.isdntypename as isdnTypeName, a.invoiceName as invoiceName,a.sale_trans_date as saleTransDate,a.agentisdn as agentisdn,a.isdn as isdn ");
        strQuery.append("" + " FROM ").append("( ").append(strNormalSaleTransQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");
        if (reportRevenueForm.getMethode() != null) {
            if (reportRevenueForm.getMethode().equals(1L)) {
                strQuery.append(" and a.reason_id is null ");
            } else {
                strQuery.append(" and a.reason_id is not null ");
            }
        }
        if (reportRevenueForm.getIsdnType() != null && !reportRevenueForm.getIsdnType().equals("")) {
            if (reportRevenueForm.getIsdnType().equals("1")) {
                strQuery.append(" and a.isdntype = '1' ");
            } else {
                strQuery.append(" and a.isdntype = '2' ");
            }
        }
        if (reportRevenueForm.getStaffManageCode() != null && !reportRevenueForm.getStaffManageCode().equals("")) {
            strQuery.append(" and upper(a.staffcodemag) = ? ");
            listParameter.add(reportRevenueForm.getStaffManageCode().toUpperCase());
        }
        strQuery.append(" and (a.status = '-1' ");
        if (reportRevenueForm.getNotBilledSaleTrans()) {
            strQuery.append(" or a.status = '2' ");
        }
        if (reportRevenueForm.getBilledSaleTrans()) {
            strQuery.append(" or a.status = '3' ");
        }
        if (reportRevenueForm.getCancelSaleTrans()) {
            strQuery.append(" or a.status = '4' ");
        }
        strQuery.append(" ) ");
        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("serviceCode", Hibernate.STRING).addScalar("seviceName", Hibernate.STRING).addScalar("telecomServiceName", Hibernate.STRING).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("ownerManagementCode", Hibernate.STRING).addScalar("ownerManagementName", Hibernate.STRING).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("isdnTypeName", Hibernate.STRING).addScalar("invoiceName", Hibernate.STRING).addScalar("saleTransDate", Hibernate.TIMESTAMP).addScalar("agentisdn", Hibernate.STRING).addScalar("isdn", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listView = query.list();

        return listView;
    }

    private List<ViewAccountSubscriberBean> getlistSubscriberBeanDetailAgent() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
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

        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        strNormalSaleTransQuery.append(""
                + " SELECT a.sale_trans_id, a.sub_id, a.telecom_service_id, a.sale_service_id,"
                + " a.isdn, a.sale_trans_date, a.status, ss.code, ss.NAME AS servicename,"
                + " tl.tel_service_name, st.shop_code AS agent_code, st.NAME AS agent_name,"
                + " st1.shop_code AS shopcodemag, st1.NAME AS shopnamemag, svt.shop_code,"
                + " svt.shop_name, b.stock_model_id, svt.shop_code_select, a.reason_id, ag.isdn as agentisdn,"
                + " DECODE (a.status,"
                + " 2, 'Chưa lập hóa đơn',"
                + " 3, 'Đã lập hóa đơn',"
                + " 4, 'Đã hủy'"
                + " ) AS invoicename,"
                + " DECODE (a.telecom_service_id,"
                + " 1, (SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 2, (SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn))"
                + " ) AS isdntype,"
                + " DECODE (a.telecom_service_id,"
                + " 1, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " ),"
                + " 2, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " )"
                + " ) AS isdntypename"
                + " FROM sale_trans a,"
                + " sale_trans_detail b,"
                + " sale_services ss,"
                + " telecom_service tl,"
                + " account_agent ag,"
                + " shop st,"
                + " shop st1,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM v_shop_tree"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)"
                + " UNION"
                + " SELECT shop_code AS shop_code, NAME AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM shop"
                + " WHERE shop_id = ?) svt"
                + " WHERE 1 = 1"
                + " AND b.sale_trans_id = a.sale_trans_id"
                + " AND ss.sale_services_id = a.sale_service_id"
                + " AND tl.telecom_service_id = a.telecom_service_id"
                + " AND ag.owner_id = a.shop_id"
                + " AND ag.owner_type = 1"
                + " AND st.shop_id = ag.owner_id"
                + " AND st.parent_shop_id = st1.shop_id"
                + " AND a.staff_id IS NULL"
                + " AND a.shop_id = svt.shop_id"
                + " AND a.sale_trans_type = 7"
                + " AND a.sale_trans_date >= ?"
                + " AND a.sale_trans_date < ?"
                + " AND b.stock_model_id IN (SELECT stock_model_id"
                + " FROM stock_model"
                + " WHERE stock_type_id IN (1, 2))");

        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "SELECT a.code as  serviceCode, a.servicename as seviceName , a.tel_service_name as telecomServiceName,"
                + " a.agent_code || ' - ' || a.agent_name as ownerCode, a.agent_name as ownerName, a.shopcodemag as ownerManagementCode, a.shopnamemag as ownerManagementName,"
                + " a.shop_code as shopCode , a.shop_name as shopName,a.isdntypename as isdnTypeName, a.invoiceName as invoiceName,a.sale_trans_date as saleTransDate,a.agentisdn as agentisdn,a.isdn as isdn  ");
        strQuery.append("" + " FROM ").append("( ").append(strNormalSaleTransQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");
        if (reportRevenueForm.getMethode() != null) {
            if (reportRevenueForm.getMethode().equals(1L)) {
                strQuery.append(" and a.reason_id is null ");
            } else {
                strQuery.append(" and a.reason_id is not null ");
            }
        }
        if (reportRevenueForm.getIsdnType() != null && !reportRevenueForm.getIsdnType().equals("")) {
            if (reportRevenueForm.getIsdnType().equals("1")) {
                strQuery.append(" and a.isdntype = '1' ");
            } else {
                strQuery.append(" and a.isdntype = '2' ");
            }
        }
        strQuery.append(" and (a.status = '-1' ");
        if (reportRevenueForm.getNotBilledSaleTrans()) {
            strQuery.append(" or a.status = '2' ");
        }
        if (reportRevenueForm.getBilledSaleTrans()) {
            strQuery.append(" or a.status = '3' ");
        }
        if (reportRevenueForm.getCancelSaleTrans()) {
            strQuery.append(" or a.status = '4' ");
        }
        strQuery.append(" ) ");
        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("serviceCode", Hibernate.STRING).addScalar("seviceName", Hibernate.STRING).addScalar("telecomServiceName", Hibernate.STRING).addScalar("ownerCode", Hibernate.STRING).addScalar("ownerName", Hibernate.STRING).addScalar("ownerManagementCode", Hibernate.STRING).addScalar("ownerManagementName", Hibernate.STRING).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("isdnTypeName", Hibernate.STRING).addScalar("invoiceName", Hibernate.STRING).addScalar("saleTransDate", Hibernate.TIMESTAMP).addScalar("agentisdn", Hibernate.STRING).addScalar("isdn", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listView = query.list();
        return listView;
    }

    private List<ViewAccountSubscriberBean> getlistSubscriberBeanGeneralStaff(Long pointOfSale) throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
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

        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        strNormalSaleTransQuery.append(""
                + " SELECT a.sale_trans_id, a.sub_id, a.telecom_service_id, a.sale_service_id,"
                + " a.isdn, a.sale_trans_date, a.status, ss.code,"
                + " ss.NAME AS servicename, tl.tel_service_name, st.staff_code,"
                + " st.NAME AS staff_name, st1.staff_code AS staffcodemag,"
                + " st1.NAME AS staffnamemag, svt.shop_code, svt.shop_name,"
                + " b.stock_model_id, svt.shop_code_select, a.reason_id,ag.isdn as agentisdn,"
                + " DECODE (a.status,"
                + " 2, 'Chưa lập hóa đơn',"
                + " 3, 'Đã lập hóa đơn',"
                + " 4, 'Đã hủy'"
                + " ) as invoiceName,"
                + " DECODE (a.telecom_service_id,"
                + " 1, (SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 2, (SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn))"
                + " ) AS isdntype,"
                + " DECODE (a.telecom_service_id,"
                + " 1, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " ),"
                + " 2, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " ) "
                + " ) AS isdntypename"
                + " FROM sale_trans a,"
                + " sale_trans_detail b,"
                + " sale_services ss,"
                + " telecom_service tl,"
                + " account_agent ag,"
                + " staff st,"
                + " staff st1,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM v_shop_tree"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)"
                + " UNION"
                + " SELECT shop_code AS shop_code, NAME AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM shop"
                + " WHERE shop_id = ?) svt"
                + " WHERE 1 = 1"
                + " AND b.sale_trans_id = a.sale_trans_id"
                + " AND ss.sale_services_id = a.sale_service_id"
                + " AND tl.telecom_service_id = a.telecom_service_id"
                + " AND ag.owner_id = a.staff_id"
                + " AND ag.owner_type = 2"
                + " AND st.staff_id = ag.owner_id"
                + " AND st.staff_owner_id = st1.staff_id"
                + " AND a.shop_id = svt.shop_id"
                + " AND a.sale_trans_type = 7"
                + " AND a.sale_trans_date >= ?"
                + " AND a.sale_trans_date < ?"
                + " AND st.point_of_sale = ?"
                + " AND b.stock_model_id IN (SELECT stock_model_id"
                + " FROM stock_model "
                + " WHERE stock_type_id IN (1, 2))");
        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);
        listParameter.add(pointOfSale);

        String sql = "From Shop where parentShopId = ? and channelTypeId = 1";
        Query sqlQuery = getSession().createQuery(sql);
        sqlQuery.setParameter(0, shopId);
        List<Shop> list = sqlQuery.list();
        StringBuffer strQuery = new StringBuffer("");
        if (list != null && list.size() > 0) {
            strQuery.append("" + "Select a.shop_code || ' - ' || a.shop_name AS shopCode,"
                    + " a.shop_name AS shopName, a.isdntypename as isdnTypeName, a.tel_service_name as telecomServiceName,"
                    + " count(distinct a.agentisdn) as amountAgentIsdnTS, count(distinct a.isdn) as amountIsdnTS ");
        } else {
            strQuery.append("" + "Select a.staff_code || ' - ' || a.staff_name AS shopCode,"
                    + " a.staff_name AS shopName, a.isdntypename as isdnTypeName, a.tel_service_name as telecomServiceName,"
                    + " count(distinct a.agentisdn) as amountAgentIsdnTS, count(distinct a.isdn) as amountIsdnTS ");
        }

        strQuery.append("" + " FROM ").append("( ").append(strNormalSaleTransQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");
        if (reportRevenueForm.getMethode() != null) {
            if (reportRevenueForm.getMethode().equals(1L)) {
                strQuery.append(" and a.reason_id is null ");
            } else {
                strQuery.append(" and a.reason_id is not null ");
            }
        }
        if (reportRevenueForm.getIsdnType() != null && !reportRevenueForm.getIsdnType().equals("")) {
            if (reportRevenueForm.getIsdnType().equals("1")) {
                strQuery.append(" and a.isdntype = '1' ");
            } else {
                strQuery.append(" and a.isdntype = '2' ");
            }
        }
        if (reportRevenueForm.getStaffManageCode() != null && !reportRevenueForm.getStaffManageCode().equals("")) {
            strQuery.append(" and upper(a.staffcodemag) = ? ");
            listParameter.add(reportRevenueForm.getStaffManageCode().toUpperCase());
        }
        strQuery.append(" and (a.status = '-1' ");
        if (reportRevenueForm.getNotBilledSaleTrans()) {
            strQuery.append(" or a.status = '2' ");
        }
        if (reportRevenueForm.getBilledSaleTrans()) {
            strQuery.append(" or a.status = '3' ");
        }
        if (reportRevenueForm.getCancelSaleTrans()) {
            strQuery.append(" or a.status = '4' ");
        }
        strQuery.append(" ) ");
        if (list != null && list.size() > 0) {
            strQuery.append(" group by a.shop_code, a.shop_name,a.isdntypename , a.tel_service_name  ");
            strQuery.append(" order by a.shop_code,a.isdntypename,a.tel_service_name  ");

        } else {
            strQuery.append(" group by a.staff_code, a.staff_name,a.isdntypename , a.tel_service_name  ");
            strQuery.append(" order by a.staff_code,a.staff_name,a.tel_service_name  ");

        }

        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("isdnTypeName", Hibernate.STRING).addScalar("telecomServiceName", Hibernate.STRING).addScalar("amountAgentIsdnTS", Hibernate.LONG).addScalar("amountIsdnTS", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        listView = query.list();
        List<ViewAccountSubscriberBean> listView1 = new ArrayList<ViewAccountSubscriberBean>();
        for (int i = 1; i < listView.size(); i++) {
            if (listView.get(i).getShopCode().equals(listView.get(i - 1).getShopCode())
                    && listView.get(i).getTelecomServiceName().equals(listView.get(i - 1).getTelecomServiceName())) {
                listView.get(i).setAmountAgentIsdnTT(listView.get(i).getAmountAgentIsdnTS());
                listView.get(i).setAmountIsdnTT(listView.get(i).getAmountIsdnTS());
                listView.get(i).setAmountAgentIsdnTS(listView.get(i - 1).getAmountAgentIsdnTS());
                listView.get(i).setAmountIsdnTS(listView.get(i - 1).getAmountIsdnTS());
                listView.get(i).setIsdnTypeName("Trả sau");
            } else {
                if (listView.get(i - 1).getIsdnTypeName().equals("Trả trước")) {
                    listView.get(i - 1).setAmountAgentIsdnTT(listView.get(i - 1).getAmountAgentIsdnTS());
                    listView.get(i - 1).setAmountIsdnTT(listView.get(i - 1).getAmountIsdnTS());
                    listView.get(i - 1).setAmountAgentIsdnTS(0L);
                    listView.get(i - 1).setAmountIsdnTS(0L);
                }
                listView1.add(listView.get(i - 1));
            }
        }
        if (listView != null && listView.size() > 0) {
            if (listView.get(listView.size() - 1).getIsdnTypeName().equals("Trả trước")) {
                listView.get(listView.size() - 1).setAmountAgentIsdnTT(listView.get(listView.size() - 1).getAmountAgentIsdnTS());
                listView.get(listView.size() - 1).setAmountIsdnTT(listView.get(listView.size() - 1).getAmountIsdnTS());
                listView.get(listView.size() - 1).setAmountAgentIsdnTS(0L);
                listView.get(listView.size() - 1).setAmountIsdnTS(0L);
            }
            listView1.add(listView.get(listView.size() - 1));
        }
        return listView1;
    }

    private List<ViewAccountSubscriberBean> getlistSubscriberBeanGeneralAgent() throws Exception {
        HttpServletRequest req = getRequest();
        List<ViewAccountSubscriberBean> listView = new ArrayList<ViewAccountSubscriberBean>();
        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Date fromDate = new Date();
        if (strFromDate != null && !strFromDate.trim().equals("")) {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        }

        Date toDate = new Date();
        Date afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);

        if (strToDate != null && !strToDate.equals("")) {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            afterToDateOneDay = new Date(toDate.getTime() + 24 * 60 * 60 * 1000);
        }
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

        StringBuffer strNormalSaleTransQuery = new StringBuffer("");
        List listParameter = new ArrayList();
        strNormalSaleTransQuery.append(""
                + " SELECT a.sale_trans_id, a.sub_id, a.telecom_service_id, a.sale_service_id,"
                + " a.isdn, a.sale_trans_date, a.status, ss.code, ss.NAME AS servicename,"
                + " tl.tel_service_name, st.shop_code AS agent_code, st.NAME AS agent_name,"
                + " st1.shop_code AS shopcodemag, st1.NAME AS shopnamemag, svt.shop_code,"
                + " svt.shop_name, b.stock_model_id, svt.shop_code_select, a.reason_id, ag.isdn as agentisdn,"
                + " DECODE (a.status,"
                + " 2, 'Chưa lập hóa đơn',"
                + " 3, 'Đã lập hóa đơn',"
                + " 4, 'Đã hủy'"
                + " ) AS invoicename,"
                + " DECODE (a.telecom_service_id,"
                + " 1, (SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 2, (SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn))"
                + " ) AS isdntype,"
                + " DECODE (a.telecom_service_id,"
                + " 1, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_mobile"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " ),"
                + " 2, DECODE ((SELECT isdn_type"
                + " FROM stock_isdn_homephone"
                + " WHERE isdn = TO_NUMBER (a.isdn)),"
                + " 1, 'Trả trước',"
                + " 2, 'Trả sau'"
                + " )"
                + " ) AS isdntypename"
                + " FROM sale_trans a,"
                + " sale_trans_detail b,"
                + " sale_services ss,"
                + " telecom_service tl,"
                + " account_agent ag,"
                + " shop st,"
                + " shop st1,"
                + " (SELECT root_code AS shop_code, root_name AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM v_shop_tree"
                + " WHERE root_id IN (SELECT shop_id"
                + " FROM shop"
                + " WHERE parent_shop_id = ?)"
                + " UNION"
                + " SELECT shop_code AS shop_code, NAME AS shop_name, shop_id,"
                + " shop_code AS shop_code_select"
                + " FROM shop"
                + " WHERE shop_id = ?) svt"
                + " WHERE 1 = 1"
                + " AND b.sale_trans_id = a.sale_trans_id"
                + " AND ss.sale_services_id = a.sale_service_id"
                + " AND tl.telecom_service_id = a.telecom_service_id"
                + " AND ag.owner_id = a.shop_id"
                + " AND ag.owner_type = 1"
                + " AND st.shop_id = ag.owner_id"
                + " AND st.parent_shop_id = st1.shop_id"
                + " AND a.staff_id IS NULL"
                + " AND a.shop_id = svt.shop_id"
                + " AND a.sale_trans_type = 7"
                + " AND a.sale_trans_date >= ?"
                + " AND a.sale_trans_date < ?"
                + " AND b.stock_model_id IN (SELECT stock_model_id"
                + " FROM stock_model"
                + " WHERE stock_type_id IN (1, 2))");

        listParameter.add(shopId);
        listParameter.add(shopId);
        listParameter.add(fromDate);
        listParameter.add(afterToDateOneDay);

        StringBuffer strQuery = new StringBuffer("");
        strQuery.append("" + "Select a.shop_code || ' - ' || a.shop_name AS shopCode,"
                + " a.shop_name AS shopName, a.isdntypename as isdnTypeName, a.tel_service_name as telecomServiceName,"
                + " count(distinct a.agentisdn) as amountAgentIsdnTS, count(distinct a.isdn) as amountIsdnTS ");
        strQuery.append("" + " FROM ").append("( ").append(strNormalSaleTransQuery).append(" ) a ");
        strQuery.append("" + "WHERE 1 = 1 ");
        if (reportRevenueForm.getMethode() != null) {
            if (reportRevenueForm.getMethode().equals(1L)) {
                strQuery.append(" and a.reason_id is null ");
            } else {
                strQuery.append(" and a.reason_id is not null ");
            }
        }
        if (reportRevenueForm.getIsdnType() != null && !reportRevenueForm.getIsdnType().equals("")) {
            if (reportRevenueForm.getIsdnType().equals("1")) {
                strQuery.append(" and a.isdntype = '1' ");
            } else {
                strQuery.append(" and a.isdntype = '2' ");
            }
        }
        strQuery.append(" and (a.status = '-1' ");
        if (reportRevenueForm.getNotBilledSaleTrans()) {
            strQuery.append(" or a.status = '2' ");
        }
        if (reportRevenueForm.getBilledSaleTrans()) {
            strQuery.append(" or a.status = '3' ");
        }
        if (reportRevenueForm.getCancelSaleTrans()) {
            strQuery.append(" or a.status = '4' ");
        }
        strQuery.append(" ) ");
        strQuery.append(" group by a.shop_code, a.shop_name,a.isdntypename , a.tel_service_name  ");
        strQuery.append(" order by a.shop_code,a.isdntypename,a.tel_service_name  ");
        Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).addScalar("isdnTypeName", Hibernate.STRING).addScalar("telecomServiceName", Hibernate.STRING).addScalar("amountAgentIsdnTS", Hibernate.LONG).addScalar("amountIsdnTS", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(ViewAccountSubscriberBean.class));

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }
        listView = query.list();
        List<ViewAccountSubscriberBean> listView1 = new ArrayList<ViewAccountSubscriberBean>();
        for (int i = 1; i < listView.size(); i++) {
            if (listView.get(i).getShopCode().equals(listView.get(i - 1).getShopCode())
                    && listView.get(i).getTelecomServiceName().equals(listView.get(i - 1).getTelecomServiceName())) {
                listView.get(i).setAmountAgentIsdnTT(listView.get(i).getAmountAgentIsdnTS());
                listView.get(i).setAmountIsdnTT(listView.get(i).getAmountIsdnTS());
                listView.get(i).setAmountAgentIsdnTS(listView.get(i - 1).getAmountAgentIsdnTS());
                listView.get(i).setAmountIsdnTS(listView.get(i - 1).getAmountIsdnTS());
                listView.get(i).setIsdnTypeName("Trả sau");
            } else {
                if (listView.get(i - 1).getIsdnTypeName().equals("Trả trước")) {
                    listView.get(i - 1).setAmountAgentIsdnTT(listView.get(i - 1).getAmountAgentIsdnTS());
                    listView.get(i - 1).setAmountIsdnTT(listView.get(i - 1).getAmountIsdnTS());
                    listView.get(i - 1).setAmountAgentIsdnTS(0L);
                    listView.get(i - 1).setAmountIsdnTS(0L);
                }
                listView1.add(listView.get(i - 1));
            }
        }
        if (listView != null && listView.size() > 0) {
            if (listView.get(listView.size() - 1).getIsdnTypeName().equals("Trả trước")) {
                listView.get(listView.size() - 1).setAmountAgentIsdnTT(listView.get(listView.size() - 1).getAmountAgentIsdnTS());
                listView.get(listView.size() - 1).setAmountIsdnTT(listView.get(listView.size() - 1).getAmountIsdnTS());
                listView.get(listView.size() - 1).setAmountAgentIsdnTS(0L);
                listView.get(listView.size() - 1).setAmountIsdnTS(0L);
            }
            listView1.add(listView.get(listView.size() - 1));
        }
        return listView1;
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
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter1 = new ArrayList();
        //lay danh sach cua hang thuoc tinh
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.shopName) ");
        strQuery1.append("from VShopTree a ");
        strQuery1.append("where 1 = 1 ");
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and rootId = 7282 ");
            //listParameter1.add(getShopId(imSearchBean.getOtherParamValue().toString()).getShopId());
        } else {
            return listImSearchBean;
        }
        strQuery1.append("and a.channelTypeId <> 4 ");
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

        return listImSearchBean;
    }

    public List<ImSearchBean> getListStaffManagement(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String shopCode = "";
        String channelTypeId = "0";
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String otherParam = imSearchBean.getOtherParamValue().trim();
            int index = otherParam.indexOf(";");
            if (index == 0 || index == otherParam.length() - 1) {
                return listImSearchBean;
            } else {
                shopCode = otherParam.substring(0, index).toLowerCase();
                channelTypeId = otherParam.substring(index + 1, otherParam.length()).toLowerCase();
            }
        }

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        if (!channelTypeId.equals("1")) {
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
            strQuery1.append("from Staff a WHERE exists  ");
            if (channelTypeId.equals("2")) {
                strQuery1.append(" (SELECT staffId FROM Staff WHERE staffOwnerId=a.staffId and status = 1 and pointOfSale = 2) ");
            } else {
                if (channelTypeId.equals("3")) {
                    strQuery1.append(" (SELECT staffId FROM Staff WHERE staffOwnerId=a.staffId and status = 1 and pointOfSale = 1) ");
                }
            }

            strQuery1.append(" AND a.shopId=? and a.status = 1 ");
            listParameter1.add(getShopId(shopCode).getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }
            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) like ? ");
                listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(300L);

            strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

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
        return listImSearchBean;
    }
}
