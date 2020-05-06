/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author User
 */
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ReportReceiveExpenseForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

public class ReportReceiveExpenseDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportDepositDAO.class);
    static private String PAGE_REPORT_RECEIVE_EXPENSE = "reportReceiveExpense";
    static private String LIST_ITEMS = "lstItems";
    public String pageForward;
    private ReportReceiveExpenseForm form = new ReportReceiveExpenseForm();

    public ReportReceiveExpenseForm getForm() {
        return form;
    }

    public void setForm(ReportReceiveExpenseForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.debug("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        pageForward = PAGE_REPORT_RECEIVE_EXPENSE;
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setToDate(dateFomat.format(cal.getTime()));
        cal.add(Calendar.MONTH, -1);
        form.setFromDate(dateFomat.format(cal.getTime()));
        form.setReportDetail(false);
        log.debug("# End method preparePage");
        return pageForward;
    }

    //Tao bao cao
    public String reportReceiveExpense() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = PAGE_REPORT_RECEIVE_EXPENSE;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String shopCode = form.getShopCode();

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop;
            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            } else {
                shop = shopDAO.findById(userToken.getShopId());
            }
            if (shop == null) {
//                req.setAttribute("displayResultMsgClient", "Mã CN/CH không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.037");
                return pageForward;
            }
            String sFromDate = form.getFromDate();
            String sToDate = form.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn từ ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.022");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.023");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
//                req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.024");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
                toDate = DateTimeUtils.addDate(toDate, 1);
            } catch (Exception ex) {
//                req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.025");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
//                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.026");
                return pageForward;
            }
            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }

            /*TuanPV - 25/02/2010 - Comment để chuyển qua mô hình ReportServer*/
            /*
            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer(" SELECT b.NAME AS shopName ");
            queryString.append("         ,b.address ");
            queryString.append("         ,SUM(a.amount) AS amount ");
            queryString.append("     FROM receipt_expense a , shop b ");
            queryString.append("     WHERE 1=1  ");
            queryString.append("         AND a.shop_id = b.shop_id ");
            queryString.append("         AND b.parent_shop_id = ? ");
            lstParam.add(shop.getShopId());
            queryString.append("         AND a.receipt_date >= ? ");
            lstParam.add(fromDate);
            queryString.append("         AND a.receipt_date < ? ");
            lstParam.add(toDate);

            queryString.append("     GROUP BY b.NAME, b.address ");
            queryString.append("     ORDER BY b.NAME ");
            Query queryObject = getSession().createSQLQuery(queryString.toString()).
            addScalar("shopName", Hibernate.STRING).
            addScalar("address", Hibernate.STRING).
            addScalar("amount", Hibernate.LONG).
            setResultTransformer(Transformers.aliasToBean(ReportReceiptExpenseBean.class));

            for(int idx = 0; idx < lstParam.size(); idx++)
            {
            queryObject.setParameter(idx, lstParam.get(idx));
            }

            List<ReportReceiptExpenseBean> lstTemp = queryObject.list();

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            if(form.getReportDetail())
            {
            templatePath = "Detail";
            }
            String prefixPath = "ReportReceiptExpense";
            templatePath = tmp + prefixPath + templatePath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("fromDate", fromDate);
            beans.put("toDate", toDate);
            beans.put("shopName", shop.getName());
            req.setAttribute("filePath", filePath);

            beans.put(LIST_ITEMS, lstTemp);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            End TuanPV Comment*/

            /*TuanPV - 25/02/2010 - Gửi lệnh xuất báo cáo sang app ReportServer*/
            ViettelMsg request = new OriginalViettelMsg();
            request.set("USER_NAME", userToken.getLoginName());
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getName());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_RECEIPIENTS_EXPENSE);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //Thong bao len jsp
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
                req.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");
            } else {
                req.setAttribute("displayResultMsgClient", "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return pageForward;
    }

    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

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

        //lay danh sach cac kho dac biet
        List listParameter2 = new ArrayList();
        StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery2.append("from Shop a ");
        strQuery2.append("where 1 = 1 ");

        strQuery2.append("and channelTypeId = ? ");
        String strSpecialChannelTypeId = ResourceBundleUtils.getResource("SHOP_SPECIAL");
        Long specialChannelTypeId = -1L;
        try {
            specialChannelTypeId = Long.valueOf(strSpecialChannelTypeId.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        listParameter2.add(specialChannelTypeId);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery2.append("and lower(a.shopCode) like ? ");
            listParameter2.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery2.append("and lower(a.name) like ? ");
            listParameter2.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery2.append("and rownum < ? ");
        listParameter2.add(300L);

        strQuery2.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query2 = getSession().createQuery(strQuery2.toString());
        for (int i = 0; i < listParameter2.size(); i++) {
            query2.setParameter(i, listParameter2.get(i));
        }

        List<ImSearchBean> tmpList2 = query2.list();
        if (tmpList2 != null && tmpList2.size() > 0) {
            listImSearchBean.addAll(tmpList2);
        }

        return listImSearchBean;
    }
}
