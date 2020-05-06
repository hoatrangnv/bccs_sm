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
import com.viettel.im.client.bean.ReportBankReceiptBean;
import com.viettel.im.client.bean.ReportReceiptExpenseBean;
import com.viettel.im.client.form.ReportDepositForm;
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

public class ReportBankReceiptDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportBankReceiptDAO.class);
    static private String PAGE_REPORT_BANK_RECEIPT = "reportBankReceipt";
    static private String LIST_ITEMS = "lstItems";
    private String LIST_TELECOM_SERVICE = "lstTelecom";
    private ReportDepositForm form = new ReportDepositForm();
    public String pageForward;

    public ReportDepositForm getForm() {
        return form;
    }

    public void setForm(ReportDepositForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.debug("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = PAGE_REPORT_BANK_RECEIPT;
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        form.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10);
        form.setFromDate(dateFomat.format(cal.getTime()));
        form.setReportDetail(1L);
        form.setReceiptStatus("0");

        //Shop
        form.setShopCode(userToken.getShopCode());
        form.setShopName(userToken.getShopName());

        //TelecomService
        req.getSession().setAttribute(LIST_TELECOM_SERVICE, null);
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(getSession());
        List<TelecomService> lstTelecom = telecomServiceDAO.findByProperty("STATUS", Constant.STATUS_USE);
        req.getSession().setAttribute(LIST_TELECOM_SERVICE, lstTelecom);

        log.debug("# End method preparePage");
        return pageForward;
    }

    //Tao bao cao
    public String reportBankReceipt() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = PAGE_REPORT_BANK_RECEIPT;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String shopCode = form.getShopCode();

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop;
            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
            } else {
                shop = shopDAO.findById(userToken.getShopId());
            }
            if (shop == null) {
//                req.setAttribute("displayResultMsgClient", "Mã đối tượng không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.021");
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
                //toDate = DateTimeUtils.addDate(toDate, 1);
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

            /*TuanPV - 24/02/2010 - Comment de chuyen sang mo hinh ReportServer*/
            /*
            String receiptStatus = "(1, 2)";
            if(form.getReceiptStatus() != null && !form.getReceiptStatus().equals("0"))
            {
            receiptStatus = "(" + form.getReceiptStatus() + ")";
            }
            Long reportDetail = form.getReportDetail();
            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer();
            List<ReportReceiptExpenseBean> lstTemp = new ArrayList<ReportReceiptExpenseBean>();
            if(reportDetail.equals(2L))
            {
            queryString.append(" SELECT bankReceiptId,createDatetime,staffId ");
            queryString.append(" ,(CASE WHEN staffId is not null then staffname WHEN shopId is not null then shopName ELSE '" + shop.getName() + "' END) as staffname ");
            queryString.append(" ,shopId,shopname,amount,bankAccountId,bankname,bankAddress ");
            queryString.append(" ,receiver,accountNo,content,status ");
            queryString.append(" ,(CASE when objectid IS not NULL then objectId ELSE " + shop.getShopId() + " END) AS objectId ");
            queryString.append("          ,(CASE when objectid IS not NULL then (SELECT NAME             FROM shop sh            WHERE sh.shop_id = objectid) ELSE '" + shop.getName() + "' END) AS objectName ");
            queryString.append("  ");
            queryString.append(" FROM ( ");
            queryString.append(" SELECT br.bank_receipt_id as bankReceiptId, br.bank_date as createDatetime , null as staffId, ");
            queryString.append("                  '' AS staffname, br.channel_id as shopId, sp.NAME AS shopname, br.amount as amount,  ");
            queryString.append("                  br.bank_account_id as bankAccountId, ba.bank_name AS bankname, ba.bank_address as bankAddress,  ");
            queryString.append("                  br.receiver as receiver, ba.account_no as accountNo, br.content as content, br.status as status , sp.parent_shop_id, sp.shop_path ");

            queryString.append("                  ,(SELECT shop_id FROM shop b WHERE sp.shop_path LIKE ('%' || TO_CHAR (b.shop_path) || '%') AND (b.parent_shop_id = ? ) AND ROWNUM <= 1) AS objectId ");
            lstParam.add(shop.getShopId());

            queryString.append("                   FROM bank_receipt br, shop sp, bank_account ba  ");
            queryString.append("                  WHERE 1=1   ");
            queryString.append("                      AND br.channel_type_id = sp.channel_type_id ");
            queryString.append("                      AND br.channel_id = sp.shop_id ");
            queryString.append("                  AND br.bank_account_id = ba.bank_account_id                   ");

            if(form.getTelecomServiceId() != null && form.getTelecomServiceId().compareTo(0L) > 0)
            {
            queryString.append(" AND br.telecom_service_id = ? ");
            lstParam.add(form.getTelecomServiceId());
            }

            queryString.append(" UNION  ALL                 ");
            queryString.append("                  ");
            queryString.append(" select br.bank_receipt_id as bankReceiptId, br.bank_date as createDatetime , br.staff_id as staffId, ");
            queryString.append("                  sf.NAME AS staffname, br.channel_id as shopId, sp.NAME AS shopname, br.amount as amount,  ");
            queryString.append("                  br.bank_account_id as bankAccountId, ba.bank_name AS bankname, ba.bank_address as bankAddress,  ");
            queryString.append("                  br.receiver as receiver, ba.account_no as accountNo, br.content as content, br.status as status , sp.parent_shop_id, sp.shop_path ");

            queryString.append("                  ,(SELECT shop_id FROM shop b WHERE sp.shop_path LIKE ('%' || TO_CHAR (b.shop_path) || '%') AND (b.parent_shop_id = ? )  AND ROWNUM <= 1) AS objectId ");
            lstParam.add(shop.getShopId());

            queryString.append("                   FROM bank_receipt br, shop sp, staff sf, bank_account ba  ");
            queryString.append("                  WHERE 1=1   ");
            queryString.append("                     AND br.channel_type_id = sf.channel_type_id ");
            queryString.append("                     AND br.channel_id = sf.staff_id ");
            queryString.append("                     AND sf.shop_id = sp.shop_id ");
            queryString.append("                  AND br.bank_account_id = ba.bank_account_id  ");

            if(form.getTelecomServiceId() != null && form.getTelecomServiceId().compareTo(0L) > 0)
            {
            queryString.append(" AND br.telecom_service_id = ? ");
            lstParam.add(form.getTelecomServiceId());
            }

            queryString.append("                   ");
            queryString.append(" ) ");
            queryString.append("  ");
            queryString.append(" WHERE 1=1 ");
            queryString.append("               ");

            queryString.append(" AND (parent_shop_id = ? or shop_path like ? ) ");
            lstParam.add(shop.getShopId());
            lstParam.add("%" + shop.getShopId() + "%");

            queryString.append(" AND ( status IN " + receiptStatus + " ) ");

            queryString.append(" AND createDatetime >= ? ");
            lstParam.add(fromDate);
            queryString.append(" AND trunc(createDatetime) <= ? ");
            lstParam.add(toDate);


            queryString.append(" ORDER BY objectName ");
            Query queryObject = getSession().createSQLQuery(queryString.toString()).
            addScalar("bankReceiptId", Hibernate.LONG).
            addScalar("createDatetime", Hibernate.DATE).
            addScalar("staffId", Hibernate.LONG).
            addScalar("staffname", Hibernate.STRING).
            addScalar("shopId", Hibernate.LONG).
            addScalar("shopname", Hibernate.STRING).
            addScalar("amount", Hibernate.LONG).
            addScalar("bankAccountId", Hibernate.LONG).
            addScalar("bankname", Hibernate.STRING).
            addScalar("bankAddress", Hibernate.STRING).
            addScalar("receiver", Hibernate.STRING).
            addScalar("accountNo", Hibernate.STRING).
            addScalar("content", Hibernate.STRING).
            addScalar("status", Hibernate.STRING).
            addScalar("objectId", Hibernate.LONG).
            addScalar("objectName", Hibernate.STRING).
            setResultTransformer(Transformers.aliasToBean(ReportBankReceiptBean.class));

            for(int idx = 0; idx < lstParam.size(); idx++)
            {
            queryObject.setParameter(idx, lstParam.get(idx));
            }
            lstTemp = queryObject.list();
            }else
            {
            queryString.append(" SELECT sp.shop_id AS shopId, sp.NAME as shopname, sp.address as bankAddress, SUM(tbl.amount) AS amount ");
            queryString.append(" FROM shop sp  ");
            queryString.append(" JOIN  ");
            queryString.append(" ( ");
            queryString.append(" SELECT sp.shop_id ");
            queryString.append("         ,br.amount, br.bank_date as create_datetime, br.status AS receiptStatus ");
            queryString.append("     FROM shop sp, bank_receipt br, channel_type cnt ");
            queryString.append("     WHERE sp.shop_id = br.channel_id ");
            queryString.append("         AND br.channel_type_id = cnt.channel_type_id ");
            queryString.append("         AND cnt.object_type = 1 ");
            queryString.append("         AND sp.shop_path LIKE ? ");
            lstParam.add("%" + shop.getShopId() + "%");
            if(form.getTelecomServiceId() != null && form.getTelecomServiceId().compareTo(0L) > 0)
            {
            queryString.append(" AND br.telecom_service_id = ? ");
            lstParam.add(form.getTelecomServiceId());
            }

            queryString.append(" UNION ALL ");
            queryString.append(" SELECT sp.shop_id ");
            queryString.append("         ,br.amount,  br.bank_date as create_datetime, br.status AS receiptStatus ");
            queryString.append("     FROM shop sp, bank_receipt br, channel_type cnt, staff sf ");
            queryString.append("     WHERE sf.staff_id = br.channel_id ");
            queryString.append("         AND br.channel_type_id = cnt.channel_type_id ");
            queryString.append("         AND cnt.object_type = 2 ");
            queryString.append("         AND sf.shop_id = sp.shop_id ");
            queryString.append("         AND sp.shop_path LIKE ? ");
            lstParam.add("%" + shop.getShopId() + "%");
            if(form.getTelecomServiceId() != null && form.getTelecomServiceId().compareTo(0L) > 0)
            {
            queryString.append(" AND br.telecom_service_id = ? ");
            lstParam.add(form.getTelecomServiceId());
            }

            queryString.append(" ) tbl ");
            queryString.append(" ON sp.shop_id = tbl.shop_id   ");
            queryString.append(" WHERE (sp.parent_shop_id = ? OR sp.shop_id = ?) ");
            lstParam.add(shop.getShopId());
            lstParam.add(shop.getShopId());
            queryString.append(" AND (receiptStatus IN " + receiptStatus + " ) ");

            queryString.append(" AND create_datetime >= ? ");
            lstParam.add(fromDate);
            queryString.append(" AND trunc(create_datetime) <= ? ");
            lstParam.add(toDate);

            queryString.append(" GROUP BY sp.shop_id, sp.NAME, sp.address ");

            Query queryObject = getSession().createSQLQuery(queryString.toString()).
            addScalar("shopId", Hibernate.LONG).
            addScalar("shopname", Hibernate.STRING).
            addScalar("bankAddress", Hibernate.STRING).
            addScalar("amount", Hibernate.LONG).
            setResultTransformer(Transformers.aliasToBean(ReportBankReceiptBean.class));

            for(int idx = 0; idx < lstParam.size(); idx++)
            {
            queryObject.setParameter(idx, lstParam.get(idx));
            }
            lstTemp = queryObject.list();

            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            if(reportDetail.equals(2L))
            {
            templatePath = "_Detail";
            }
            String prefixPath = "ReportBankReceipt";
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

            /*TuanPV - 24/02/2010 - Gửi lệnh xuất báo cáo sang app ReportServer*/
            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", form.getFromDate());
            request.set("TO_DATE", form.getToDate());
            request.set("USER_NAME", userToken.getLoginName());

            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getName());
            if (form.getReportDetail() != null && form.getReportDetail().equals(2L)) {
                request.set("IS_REPORT_DETAIL", "true");
            } else {
                request.set("IS_REPORT_DETAIL", "false");
            }

            request.set("RECEIPT_STATUS", form.getReceiptStatus());
            request.set("TELECOM_SERVICE_ID", form.getTelecomServiceId());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_BANK_RECEIPT);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
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
