/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ReportBankReceiptBean;
import com.viettel.im.client.bean.ReportReceiptExpenseBean;
import com.viettel.im.client.form.ReportBankReceiptInExForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tronglv
 */
public class ReportBankReceiptInExDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportBankReceiptDAO.class);
    static private String PAGE_REPORT_BANK_RECEIPT = "reportBankReceipt";
    static private String PAGE_REPORT_BANK_RECEIPT_EXTERNAL = "reportBankReceiptExternal";
    private ReportBankReceiptInExForm form = new ReportBankReceiptInExForm();
    private String STATUS_ALL = "0";

    public ReportBankReceiptInExForm getForm() {
        return form;
    }

    public void setForm(ReportBankReceiptInExForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.debug("# Begin method preparePage");
        String pageForward = PAGE_REPORT_BANK_RECEIPT;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return Constant.SESSION_TIME_OUT;
        }

        form = new ReportBankReceiptInExForm();
        form.setFromDate(DateTimeUtils.getSysdateForWeb(-1));
        form.setToDate(DateTimeUtils.getSysdateForWeb(-1));
        form.setReportType(Constant.REPORT_GENERAL);//chi tiet & tong hop
        form.setStatus(this.STATUS_ALL);//ca da duyet va chua duyet

        //Shop
        form.setShopCode(userToken.getShopCode());
        form.setShopName(userToken.getShopName());

        log.debug("# End method preparePage");
        return pageForward;
    }

    //Tao bao cao
    public String reportBankReceipt() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = PAGE_REPORT_BANK_RECEIPT;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (userToken == null) {
                return Constant.SESSION_TIME_OUT;
            }

            String shopCode = form.getShopCode();

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop;
            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
            } else {
                shop = shopDAO.findById(userToken.getShopId());
            }

            if (shop == null || shop.getShopId() == null || shop.getShopPath() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
                return pageForward;
            }
            //Shop bao cao khong phai truc thuoc don vi dang nhap
            if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
                return pageForward;
            }
//            if (shop.getShopPath().indexOf("_" + userToken.getShopId().toString() + "_") < 0
//                    && !shop.getShopPath().substring(shop.getShopPath().lastIndexOf("_") + 1).equals(userToken.getShopId())) {
//                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
//                return pageForward;
//            }

            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.022");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.023");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.024");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.025");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.026");
                return pageForward;
            }
            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }

            if (form.getReportType() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("Lỗi. Chưa chọn loại báo cáo"));
                return pageForward;
            }

            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", form.getFromDate());
            request.set("TO_DATE", form.getToDate());
            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getName());
            request.set("REPORT_TYPE", form.getReportType());
            if (form.getStatus() != null && !form.getStatus().equals(this.STATUS_ALL)) {
                request.set("STATUS", form.getStatus());
            }
            request.set("USER_NAME", userToken.getLoginName());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_BANK_RECEIPT_IN_EX);

            request.set("EXTERNAL", false);
            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
                //req.setAttribute(Constant.RETURN_MESSAGE, "report.stocktrans.error.successMessage");
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute(Constant.RETURN_MESSAGE, "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType b where b.isVtUnit = ? ) ");
        listParameter1.add(Constant.IS_VT_UNIT);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(300L);

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

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add("");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType b where b.isVtUnit = ? ) ");
        listParameter1.add(Constant.IS_VT_UNIT);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(300L);

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

    public Long getListShopSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType b where b.isVtUnit = ? ) ");
        listParameter1.add(Constant.IS_VT_UNIT);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
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

    public String preparePageExternal() throws Exception {
        log.debug("# Begin method preparePageExternal");
        String pageForward = PAGE_REPORT_BANK_RECEIPT_EXTERNAL;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken == null) {
            return Constant.SESSION_TIME_OUT;
        }

        form = new ReportBankReceiptInExForm();
        form.setFromDate(DateTimeUtils.getSysdateForWeb(-1));
        form.setToDate(DateTimeUtils.getSysdateForWeb(-1));
        form.setReportType(Constant.REPORT_GENERAL);//chi tiet & tong hop
        form.setStatus(this.STATUS_ALL);//ca da duyet va chua duyet

        //Shop
        form.setShopCode(userToken.getShopCode());
        form.setShopName(userToken.getShopName());

        log.debug("# End method preparePageExternal");
        return pageForward;
    }

    public String reportBankReceiptExternal() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = PAGE_REPORT_BANK_RECEIPT_EXTERNAL;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (userToken == null) {
                return Constant.SESSION_TIME_OUT;
            }

            String shopCode = form.getShopCode();

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop;
            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
            } else {
                shop = shopDAO.findById(userToken.getShopId());
            }

            if (shop == null || shop.getShopId() == null || shop.getShopPath() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
                return pageForward;
            }
            //Shop bao cao khong phai truc thuoc don vi dang nhap
            if (!checkShopUnder(userToken.getShopId(), shop.getShopId())) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
                return pageForward;
            }
//            if (shop.getShopPath().indexOf("_" + userToken.getShopId().toString() + "_") < 0
//                    && !shop.getShopPath().substring(shop.getShopPath().lastIndexOf("_") + 1).equals(userToken.getShopId())) {
//                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
//                return pageForward;
//            }

            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.022");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.023");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.024");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.025");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.RET.026");
                return pageForward;
            }
            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }

            if (form.getReportType() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("Lỗi. Chưa chọn loại báo cáo"));
                return pageForward;
            }

            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", form.getFromDate());
            request.set("TO_DATE", form.getToDate());
            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getName());
            request.set("REPORT_TYPE", form.getReportType());
            if (form.getStatus() != null && !form.getStatus().equals(this.STATUS_ALL)) {
                request.set("STATUS", form.getStatus());
            }
            request.set("USER_NAME", userToken.getLoginName());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_BANK_RECEIPT_IN_EX);

            request.set("EXTERNAL", true);
            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute(Constant.RETURN_MESSAGE, "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return pageForward;
    }
}
