/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ReportDepositForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 *
 * @author ITHC
 */
public class ReportMaxDebitDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportDepositDAO.class);
    private ReportDepositForm reportDepositForm = new ReportDepositForm();
    private static String REPORT_DEMO = "reportMaxDebit";
    private static String pageForward;
//    private String LIST_DEPOSIT_TYPE = "listDepositType";

    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportBalanceDeposit");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = REPORT_DEMO;
//        removeTabSession(LIST_DEPOSIT_TYPE);
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        reportDepositForm.setToDate(dateFomat.format(cal.getTime()));
        reportDepositForm.setFromDate(dateFomat.format(cal.getTime()));
        reportDepositForm.setShopCode(userToken.getShopCode());
        reportDepositForm.setShopName(userToken.getShopName());
        reportDepositForm.setStaffCode(userToken.getLoginName());
        reportDepositForm.setStaffName(userToken.getStaffName());
        reportDepositForm.setReportDetail(1L);
//
//        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
//        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
//        req.setAttribute("listChannelType", lstChannelTypeCol);

        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    public String exportReportMaxDebit() throws Exception {
        log.debug("# Begin method ReportDepositDAO.exportReportDepositBranch");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_DEMO;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String shopCode = reportDepositForm.getShopCode();
            if (shopCode == null || "".equals(shopCode.trim())) {
                req.setAttribute("resultMessage1", "ERR.RET.034");
                return pageForward;
            }
            Shop shop1 = getShopId(shopCode);
            if (shop1 == null) {
                /*Mã cửa hàng chưa chính xác*/
                req.setAttribute("resultMessage1", "ERR.RET.012");
                return pageForward;
            }
            /*check shop cap duoi*/
            ChannelDAO channelDAO = new ChannelDAO();
            channelDAO.setSession(getSession());
            if (!channelDAO.checkShopUnder(userToken.getShopId(), shop1.getShopId())) {
                /*Mã cửa hàng khong phai la cap duoi*/
                req.setAttribute("resultMessage1", "ERR.CHL.140");
                return pageForward;
            }

            ViettelMsg request = new OriginalViettelMsg();
            request.set("SHOP_CODE", shopCode);
            request.set("LEVEL_TYPE_1", getText("L.200058"));
            request.set("LEVEL_TYPE_2", getText("L.200059"));
            request.set("LEVEL_TYPE_3", getText("L.200060"));
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_MAX_DEBIT);
            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportStockImportPendingMessage", "reportRevenue.reportRevenueMessage");
                System.out.println(response.get(ReportConstant.RESULT_FILE).toString());
//                req.setAttribute("resultMessage1", "Export successfull");
            } else {
                req.setAttribute("resultMessage1", "report.warning.noResult");

                return pageForward;
            }
        } catch (Exception ex) {
            req.setAttribute("resultMessage1", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return pageForward;

    }

    public ReportDepositForm getReportDepositForm() {
        return reportDepositForm;
    }

    public void setReportDepositForm(ReportDepositForm reportDepositForm) {
        this.reportDepositForm = reportDepositForm;
    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

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

        strQuery1.append("and rownum < ? ");
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

    public List<ImSearchBean> getShopName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_SHOP);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


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

        strQuery1.append("and rownum < ? ");
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

    public Shop getShopId(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
        if (shop != null) {
            return shop;
        }
        return null;
    }
}
