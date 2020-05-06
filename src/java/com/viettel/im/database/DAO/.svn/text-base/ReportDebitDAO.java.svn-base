/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ReportDebitForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
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

/**
 *
 * @author tronglv
 */
public class ReportDebitDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportDebitDAO.class);
    private ReportDebitForm form;
    private String pageForward;
    private final String PREPARE_PAGE = "preparePage";

    public ReportDebitForm getForm() {
        return form;
    }

    public void setForm(ReportDebitForm form) {
        this.form = form;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String preparePage() throws Exception {
        log.info("# Begin method preparePage of ReportDebitDAO...");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                return Constant.SESSION_TIME_OUT;
            }

            form = new ReportDebitForm();

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            if (shop == null || shop.getShopId() == null) {
                 return Constant.SESSION_TIME_OUT;
            }
            form.setShopCode(shop.getShopCode());
            form.setShopName(shop.getName());

            form.setFromDate(DateTimeUtils.getSysdateForWeb(-1));
            form.setToDate(DateTimeUtils.getSysdateForWeb(-1));

            form.setReportType(Constant.REPORT_GENERAL.toString());

            pageForward = PREPARE_PAGE;

        } catch (Exception ex) {
            ex.printStackTrace();
            pageForward = Constant.ERROR_PAGE;
        }
        log.info("# End method preparePage of ReportDebitDAO");
        return pageForward;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public String reportDebit() throws Exception {
        log.info("# Begin method reportDebit of ReportDebitDAO...");
        try {
            pageForward = PREPARE_PAGE;
            
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                throw new Exception("Time out session");
            }
            if (form == null) {
                preparePage();
            }
            //Shop bao cao null
            if (form.getShopCode() == null || form.getShopCode().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.052"));
                return pageForward;
            }
            //Shop bao cao khong hop le
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopVTByCodeAndStatusAndUnit(form.getShopCode().trim(), Constant.STATUS_USE, Constant.IS_VT_UNIT);
            if (shop == null || shop.getShopId() == null || shop.getShopPath() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
                return pageForward;
            }
            //Shop bao cao khong phai truc thuoc don vi dang nhap
//            if (shop.getShopPath().indexOf("_" + userToken.getShopId().toString() + "_") < 0
//                    && !shop.getShopPath().substring(shop.getShopPath().lastIndexOf("_") + 1).equals(userToken.getShopId())) {
//                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.053"));
//                return pageForward;
//            }

            Date fromDate = new Date();
            Date toDate = new Date();
            //Tu ngay null
            if (form.getFromDate() == null || form.getFromDate().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.055"));
                return pageForward;
            } else {
                fromDate = DateTimeUtils.convertStringToDateForWeb(form.getFromDate().trim());
            }
            //Den ngay null
            if (form.getToDate() == null || form.getToDate().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.056"));
                return pageForward;
            } else {
                toDate = DateTimeUtils.convertStringToDateForWeb(form.getToDate().trim());
            }
            //Tu ngay > Den ngay
            if (fromDate.after(toDate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.RET.058"));
                return pageForward;
            }
            //Tu ngay & Den ngay : khong cung thang
            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }
            if (form.getReportType() == null || form.getReportType().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, getText("Lỗi. Chưa chọn loại báo cáo"));
                return pageForward;
            }

            //Gui lenh len ReportServer
            ViettelMsg request = new OriginalViettelMsg();
            request.set("USER_CODE", userToken.getLoginName());
            request.set("USER_NAME", userToken.getFullName());
            request.set("FROM_DATE", form.getFromDate().trim());
            request.set("TO_DATE", form.getToDate().trim());
            request.set("REPORT_TYPE", form.getReportType());
            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_CODE", shop.getShopCode());
            request.set("SHOP_NAME", shop.getName());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_DEBIT);

            //Ket qua bao cao
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
            ex.printStackTrace();
            pageForward = Constant.ERROR_PAGE;
        }
        log.info("# End method reportDebit of ReportDebitDAO");
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
}
