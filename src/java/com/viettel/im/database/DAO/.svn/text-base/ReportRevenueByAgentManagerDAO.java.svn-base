/*
 * Copyright YYYY Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.ReportConstant;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.ReportRevenueForm;
import java.util.List;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VSaleTransDetail;
import com.viettel.im.database.BO.ChannelType;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author tamdt1
 * bao cao doanh thu
 *
 */
public class ReportRevenueByAgentManagerDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(LookupIsdnDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String REPORT_REVENUE_BY_AGENT_MANAGER = "reportRevenueByAgentManager";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_REPORT_PATH = "reportPath";
    private final String REQUEST_REPORT_MESSAGE = "reportMessage";
    //khai bao bien form
    private ReportRevenueForm reportRevenueForm = new ReportRevenueForm();

    public ReportRevenueForm getReportRevenueForm() {
        return reportRevenueForm;
    }

    public void setReportRevenueForm(ReportRevenueForm reportRevenueForm) {
        this.reportRevenueForm = reportRevenueForm;
    }

    /**
     *
     * author tutm1
     * date: 21/07/2011
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ReportRevenueDAO ...");

        HttpServletRequest req = getRequest();
        //reset form
        this.reportRevenueForm.resetForm();
        //thiet lap cac truong mac dinh cho shop code va staff code
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.reportRevenueForm.setShopCode(userToken.getShopCode());
        this.reportRevenueForm.setShopName(userToken.getShopName());
        this.reportRevenueForm.setStaffCode(userToken.getLoginName());
        this.reportRevenueForm.setStaffName(userToken.getStaffName());
        this.reportRevenueForm.setReportType(1L);
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);
        pageForward = REPORT_REVENUE_BY_AGENT_MANAGER;
        log.info("End method preparePage of ReportRevenueDAO");
        return pageForward;
    }

    /**
     *
     * author tutm1
     * date: 21/07/2011
     * bao cao doanh thu theo chuc danh nhan vien quan ly dai ly
     *
     */
    public String reportRevenueByAgentManager() throws Exception {
        log.info("Begin method reportRevenueByAgentManager of ReportRevenueByAgentManagerDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        String strFromDate = this.reportRevenueForm.getFromDate();
        String strToDate = this.reportRevenueForm.getToDate();
        Long groupType = this.reportRevenueForm.getGroupType();
        String shopCode = this.reportRevenueForm.getShopCode();
        String shopName = this.reportRevenueForm.getShopName();
        String staffCode = this.reportRevenueForm.getStaffCode();
        String staffName = this.reportRevenueForm.getStaffName();
        Long channelTypeId = this.reportRevenueForm.getChannelTypeId();

        // load danh sach kenh
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("lstChannelTypeCol", lstChannelTypeCol);

        Date fromDate;
        Date toDate;
        pageForward = REPORT_REVENUE_BY_AGENT_MANAGER;
        //kiem tra cac truong bat buoc
        if (shopCode == null || shopCode.trim().equals("") || strFromDate == null || strFromDate.trim().equals("") || strToDate == null || strToDate.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.requiredFieldsEmpty");
            log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
            return pageForward;
        }

        // kiem tra shop co ton tai hay ko
        // tao doi tuong tim kiem
        ImSearchBean imSearchBean = new ImSearchBean();
        imSearchBean.setHttpServletRequest(req);
        imSearchBean.setCode(shopCode);
        List<ImSearchBean> listShop = new CommonDAO().getExtractListShopVT(imSearchBean);

        if (listShop == null || listShop.isEmpty()) {
            req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.shopNotExist");
            log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
            return pageForward;
        }

        shopCode = listShop.get(0).getCode();
        shopName = listShop.get(0).getName();

        // kiem tra nhan vien quan ly co ton tai hay ko
        if (staffCode != null && !staffCode.trim().equals("")) {
            imSearchBean.setCode(staffCode);
            imSearchBean.setOtherParamValue(shopCode);
            List<ImSearchBean> listStaff = new CommonDAO().getExtractAgentManagers(imSearchBean);
            if (listStaff == null || listStaff.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "reportRevenue.error.staffNotExist");
                log.info("End method reportRevenueBoughtByCollaborator of ReportRevenueBoughtByCollaboratorDAO");
                return pageForward;
            }
            staffCode = listStaff.get(0).getCode();
            staffName = listStaff.get(0).getName();
        }


        try {
            fromDate = DateTimeUtils.convertStringToDate(strFromDate);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
            req.setAttribute("message", "ERR.RET.024");
            return pageForward;
        }
        try {
            toDate = DateTimeUtils.convertStringToDate(strToDate);
            //toDate = DateTimeUtils.addDate(toDate, 1);
        } catch (Exception ex) {
//            req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
            req.setAttribute("message", "ERR.RET.025");
            return pageForward;
        }
        if (fromDate.after(toDate)) {
//            req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
            req.setAttribute("message", "ERR.RET.026");
            return pageForward;
        }
        if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
            req.setAttribute("message", "stock.report.impExp.error.fromDateToDateNotSame");
            return pageForward;
        }


        Shop shop = null;
        if (reportRevenueForm.getShopCode() != null) {
            shop = new ShopDAO().findShopsAvailableByShopCode(reportRevenueForm.getShopCode());

        }
        Long shopId = shop.getShopId();
        Long channelTypeShopId = shop.getChannelTypeId();

        // cua hang thuoc cap nao
        ChannelType channelTypeShop = new ChannelTypeDAO().findById(channelTypeShopId);


        //truyen du lieu sang server bao cao
        ViettelMsg request = new OriginalViettelMsg();
        request.set("FROM_DATE", strFromDate);
        request.set("TO_DATE", strToDate);
        request.set("USER_NAME", userToken.getLoginName());
        request.set("STAFF_CODE", staffCode);
        request.set("STAFF_NAME", staffName);
        request.set("SHOP_ID", shopId);
        request.set("SHOP_CODE", shopCode);
        request.set("SHOP_NAME", shopName);
        request.set("GROUP_TYPE", groupType);
        request.set("PREFIX_OBJECT_CODE", channelTypeShop.getPrefixObjectCode());
        request.set("CHANNEL_TYPE_ID", channelTypeId);
        request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_REVENUE_BY_AGENT_MANAGER);

        //truyen du lieu sang server bao cao
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());

        AppParams appParams = appParamsDAO.findAppParams("GET_DATABASE_ONLINE", String.valueOf(ReportConstant.IM_REPORT_REVENUE_BY_AGENT_MANAGER));
        String getDatabase = appParams == null ? "1" : appParams.getValue();
        if (getDatabase == null) {
            request.set("DATABASE_ONLINE", "1");
        } else {
            request.set("DATABASE_ONLINE", getDatabase);
        }

        ViettelMsg response = ReportRequestSender.sendRequest(request);


        if (response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_PATH, downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
            //req.setAttribute(REQUEST_REPORT_PATH, response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute(REQUEST_REPORT_MESSAGE, "reportRevenue.reportRevenueMessage");
        } else {
            req.setAttribute(REQUEST_MESSAGE, "report.warning.noResult");
        }

        log.info("End method reportRevenueByAgentManager of ReportRevenueDAO");
        return pageForward;

    }
}
