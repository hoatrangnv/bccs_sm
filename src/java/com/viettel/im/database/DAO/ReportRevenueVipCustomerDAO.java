/*
 * Copyright 2014 Viettel Software. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ReportForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 *
 * @author Toancx
 * @version
 * @since Mar 25, 2015
 */
public class ReportRevenueVipCustomerDAO extends BaseDAOAction {

    private static final Log logger = LogFactory.getLog(ReportRevenueVipCustomerDAO.class);
    private ReportForm reportForm = new ReportForm();
    private String pageForward = "reportRevenueVipCustomer";

    public ReportForm getReportForm() {
        return reportForm;
    }

    public void setReportForm(ReportForm reportForm) {
        this.reportForm = reportForm;
    }

    public String preparePage() throws Exception {
        logger.info("begin ReportRevenueVipCustomerDAO ....");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (reportForm == null) {
            reportForm = new ReportForm();
        }
        reportForm.setToDate(getSysdate());
        reportForm.setFromDate(DateTimeUtils.getDateWithDayFirstMonth(Calendar.getInstance().getTime()));
        reportForm.setShopCode(userToken.getShopCode());
        reportForm.setShopName(userToken.getShopName());
        reportForm.setStaffCode(userToken.getLoginName());
        reportForm.setStaffName(userToken.getFullName());
        return pageForward;
    }

    public String exportReport() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Date fromDate = reportForm.getFromDate();
        Date toDate = reportForm.getToDate();

        String shopCode = reportForm.getShopCode();
        String staffCode = reportForm.getStaffCode();

        String error = null;
        Staff staff = null;
        if (staffCode != null && !staffCode.equals("")) {
            staff = getStaff(staffCode);
        }
        req.setAttribute("displayResultMsgClient", "Export successfull");

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
                    error += ";" + getText("ERR.CHL.046");
                }
                if (shop != null && !checkShopUnder(userToken.getShopId(), shop.getShopId())) {
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
                    request.set("FROM_DATE", DateTimeUtils.convertDateTimeToString(fromDate, "yyyy-MM-dd"));
                    request.set("TO_DATE", DateTimeUtils.convertDateTimeToString(toDate, "yyyy-MM-dd"));

                    if (staff != null) {
                        request.set("STAFF_ID", staff.getStaffId());
                    }

                    request.set("SHOP_ID", shop.getShopId());
                    request.set("SHOP_NAME", userToken.getShopName());
                    request.set("USER_NAME", userToken.getLoginName());
                    request.set("SHOP_PATH", shop.getShopPath());
                    request.set("MSISDN", StringUtils.convertToMsisdn(reportForm.getCustMobile()));

                    request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_RENVENUE_VIP_CUSTOMER);

                    ViettelMsg response = ReportRequestSender.sendRequest(request);
                    if (response != null
                            && response.get(ReportConstant.RESULT_FILE) != null
                            && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                        DownloadDAO downloadDAO = new DownloadDAO();
                        req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                    } else {
                        req.setAttribute("displayResultMsgClient", "report.warning.noResult");
                        return pageForward;
                    }
                }
            }
        }

        return pageForward;
    }

    /**
     * getStaff
     *
     * @param staffCode
     * @return
     * @throws Exception
     */
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

    /**
     * getShop
     *
     * @param shopCode
     * @return
     * @throws Exception
     */
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

    /**
     * checkShopUnder
     *
     * @param shopIdLogin
     * @param shopIdSelect
     * @return
     */
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
    
    //Lay danh sach shop
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and channelTypeId IN (SELECT channelTypeId FROM ChannelType WHERE objectType = ? AND isVtUnit = ? AND status = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.STATUS_USE);
        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        Shop shop = (Shop) getSession().get(Shop.class, userToken.getShopId());
        if (shop != null) {
            listParameter1.add(shop.getShopPath() + "_%");
        } else {
            listParameter1.add("%_" + userToken.getShopId() + "_%");
        }
        listParameter1.add(userToken.getShopId());

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
}
