/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ReportAnyPayBean;
import com.viettel.im.client.form.ReportAnyPayForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author MrSun
 */
public class ReportAnyPayDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportBankReceiptDAO.class);
    static private String PAGE_REPORT_ANYPAY = "reportAnyPay";
    private ReportAnyPayForm form = new ReportAnyPayForm();
    static private String LIST_ITEMS = "lstItems";
    public String pageForward;

    public ReportAnyPayForm getForm() {
        return form;
    }

    public void setForm(ReportAnyPayForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.debug("# Begin method preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = PAGE_REPORT_ANYPAY;
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        form.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10);
        form.setFromDate(dateFomat.format(cal.getTime()));

        //Shop
        form.setShopCode(userToken.getShopCode());
        form.setShopName(userToken.getShopName());

        form.setReportType("1");

        log.debug("# End method preparePage");
        return pageForward;
    }

    //Tao bao cao
    public String reportAnyPay() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = PAGE_REPORT_ANYPAY;
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.setAttribute("listChannelType", listChannelType);
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String shopCode = form.getShopCode();
            String staffCode = form.getStaffCode();

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = null;
            Shop parentShop = null;
            if (shopCode != null && !"".equals(shopCode.trim())) {
                shop = shopDAO.findShopsAvailableByShopCode(shopCode.trim().toLowerCase());
            } else {
                shop = shopDAO.findById(userToken.getShopId());
            }
            if (shop == null) {
                //req.setAttribute("displayResultMsgClient", "Mã đối tượng không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.021");
                return pageForward;
            } else {
                shopCode = "";
            }
            if (shop.getParentShopId() != null) {
                parentShop = shopDAO.findById(shop.getParentShopId());
            }

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = null;
            if (staffCode != null && !"".equals(staffCode.trim())) {
                staff = staffDAO.findStaffAvailableByStaffCode(staffCode.trim().toLowerCase());
            } else {
                staff = staffDAO.findById(userToken.getUserID());
            }
            if (staff == null) {
//                req.setAttribute("displayResultMsgClient", "Mã đối tượng không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.021");
                return pageForward;
            } else {
                staffCode = "";
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
            if (fromDate == null) {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn từ ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.022");
                return pageForward;
            }
            if (toDate == null) {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.023");
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
             * */
            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer();
            List<ReportAnyPayForm> lstTemp = new ArrayList<ReportAnyPayForm>();

            if (form.getReportType() == null || form.getReportType().trim().equals("") || (!form.getReportType().trim().equals("1") && !form.getReportType().trim().equals("2"))) {
//                req.setAttribute("displayResultMsgClient", "Bạn phải chọn loại báo cáo");
                req.setAttribute("displayResultMsgClient", "ERR.RET.027");
                return pageForward;
            }



            //Start
            ViettelMsg request = new OriginalViettelMsg();
            request.set("REPORT_TYPE", form.getReportType());
            request.set("TRANS_TYPE", form.getTransType());
            request.set("CHANNEL_TYPE_ID", form.getChannelTypeId());
            request.set("OWNER_TYPE", form.getOwnerType());

            request.set("FROM_DATE", form.getFromDate());
            request.set("TO_DATE", form.getToDate());

            request.set("USER_CODE", userToken.getLoginName());
            request.set("USER_NAME", userToken.getStaffName());

            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getName());

            if (parentShop != null) {
                request.set("PARENT_SHOP_ID", parentShop.getShopId());
                request.set("PARENT_SHOP_NAME", parentShop.getName());
            }


            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_ANYPAY);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //Thong bao len jsp
                req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                req.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");
            } else {
                req.setAttribute("displayResultMsgClient", "report.warning.noResult");
            }
            //Finish
            if (1 == 1) {
                return pageForward;
            }


            if (form.getReportType().trim().equals("2")) { //Chi tiet


                queryString.append(" SELECT obj_code as objCode, obj_name as objName, obj_isdn as objIsdn, DECODE (trans_type, 'LOAD', 'Nap tien', 'Chuyen tien') AS transType, sum(sum_trans) as sumTrans, sum(sum_tar_isdn) as sumTarIsdn, sum(sum_amount) as sumAmount FROM  ");
                queryString.append(" ( ");
                queryString.append(" select b.owner_id, b.owner_type, a.source_msisdn, a.last_modified, a.trans_type, a.sum_trans, a.sum_tar_isdn, a.sum_amount, c.shop_id, d.shop_path || '_' AS shop_path, c.channel_type_id, c.staff_code AS obj_code, c.NAME AS obj_name, b.isdn AS obj_isdn ");
                queryString.append(" from sum_trans_anypay a, account_agent b, staff c, shop d ");
                queryString.append(" WHERE a.source_agent_id = b.agent_id ");
                queryString.append(" AND b.owner_type = 2 ");
                queryString.append(" AND b.owner_id = c.staff_id ");
                queryString.append(" AND c.shop_id = d.shop_id ");
                queryString.append(" UNION ALL ");
                queryString.append(" select b.owner_id, b.owner_type, a.source_msisdn, a.last_modified, a.trans_type, a.sum_trans, a.sum_tar_isdn, a.sum_amount, c.parent_shop_id as shop_id, d.shop_path || '_' AS shop_path, c.channel_type_id, c.shop_code AS obj_code, c.NAME AS obj_name, b.isdn AS obj_isdn ");
                queryString.append(" from sum_trans_anypay a, account_agent b, shop c, shop d ");
                queryString.append(" WHERE a.source_agent_id = b.agent_id ");
                queryString.append(" AND b.owner_type = 1 ");
                queryString.append(" AND b.owner_id = c.shop_id ");
                queryString.append(" AND c.parent_shop_id = d.shop_id ");

                queryString.append(" ) where 1=1 ");


                if (shop != null && shopCode.equals("")) {
                    queryString.append("      AND shop_path LIKE (?)  ESCAPE '$' ");
                    lstParam.add("%$_" + shop.getShopId().toString() + "$_%");
                }
                if (fromDate != null) {
                    queryString.append("      AND last_modified >= ? ");
                    lstParam.add(fromDate);
                }
                if (toDate != null) {
                    queryString.append("      AND last_modified <= ? ");
                    lstParam.add(toDate);
                }
                if (form.getTransType() != null && !form.getTransType().trim().equals("")) {
                    queryString.append("      AND trans_type = ? ");
                    lstParam.add(form.getTransType().trim());
                }

                if (form.getChannelTypeId() != null && !form.getChannelTypeId().trim().equals("")) {
                    queryString.append("      AND channel_type_id = ? ");
                    lstParam.add(Long.valueOf(form.getChannelTypeId().trim()));
                }

                if (form.getOwnerType() != null && !form.getOwnerType().trim().equals("")) {
                    queryString.append("      AND owner_type = ? ");
                    lstParam.add(Long.valueOf(form.getOwnerType().trim()));
                }

                queryString.append(" GROUP BY obj_code, obj_name, obj_isdn, trans_type ");
                queryString.append(" ORDER BY obj_code, trans_type ");

                Query queryObject = getSession().createSQLQuery(queryString.toString()).
                        addScalar("objCode", Hibernate.STRING).
                        addScalar("objName", Hibernate.STRING).
                        addScalar("objIsdn", Hibernate.STRING).
                        addScalar("transType", Hibernate.STRING).
                        addScalar("sumTrans", Hibernate.LONG).
                        addScalar("sumTarIsdn", Hibernate.LONG).
                        addScalar("sumAmount", Hibernate.LONG).
                        setResultTransformer(Transformers.aliasToBean(ReportAnyPayBean.class));
                for (int idx = 0; idx < lstParam.size(); idx++) {
                    queryObject.setParameter(idx, lstParam.get(idx));
                }
                lstTemp = queryObject.list();
            } else {
                queryString.append(" SELECT   root_code || ' - ' || root_name AS objCode, ");
                queryString.append("          DECODE (trans_type, ");
                queryString.append("                  'LOAD', 'Nap tien', ");
                queryString.append("                  'Chuyen tien' ");
                queryString.append("                 ) AS transType, COUNT (source_msisdn) as countIsdn, SUM (sum_trans) as sumTrans, ");
                queryString.append("          SUM (sum_tar_isdn) sumTarIsdn, SUM (sum_amount) as sumAmount ");
                queryString.append("     FROM (SELECT b.owner_id, b.owner_type, a.source_msisdn, a.last_modified, ");
                queryString.append("                  a.trans_type, a.sum_trans, a.sum_tar_isdn, a.sum_amount, ");
                queryString.append("                  c.shop_id, d.shop_path || '_' AS shop_path, ");
                queryString.append("                  c.channel_type_id, c.staff_code AS obj_code, ");
                queryString.append("                  c.NAME AS obj_name, b.isdn AS obj_isdn ");
                queryString.append("             FROM sum_trans_anypay a, ");
                queryString.append("                  account_agent b, ");
                queryString.append("                  staff c, ");
                queryString.append("                  shop d ");
                queryString.append("            WHERE a.source_agent_id = b.agent_id ");
                queryString.append("              AND b.owner_type = 2 ");
                queryString.append("              AND b.owner_id = c.staff_id ");
                queryString.append("              AND c.shop_id = d.shop_id ");
                queryString.append("           UNION ALL ");
                queryString.append("           SELECT b.owner_id, b.owner_type, a.source_msisdn, a.last_modified, ");
                queryString.append("                  a.trans_type, a.sum_trans, a.sum_tar_isdn, a.sum_amount, ");
                queryString.append("                  c.parent_shop_id AS shop_id, d.shop_path || '_' AS shop_path, ");
                queryString.append("                  c.channel_type_id, c.shop_code AS obj_code, ");
                queryString.append("                  c.NAME AS obj_name, b.isdn AS obj_isdn ");
                queryString.append("             FROM sum_trans_anypay a, ");
                queryString.append("                  account_agent b, ");
                queryString.append("                  shop c, ");
                queryString.append("                  shop d ");
                queryString.append("            WHERE a.source_agent_id = b.agent_id ");
                queryString.append("              AND b.owner_type = 1 ");
                queryString.append("              AND b.owner_id = c.shop_id ");
                queryString.append("              AND c.parent_shop_id = d.shop_id) grp, ");
                queryString.append("          (SELECT root_id, root_code, root_name, channel_type_id, shop_id ");
                queryString.append("             FROM v_shop_tree ");
                queryString.append("            WHERE root_id IN (SELECT shop_id ");
                queryString.append("                                FROM shop ");
                queryString.append("                               WHERE parent_shop_id = ?) ");
                lstParam.add(shop.getShopId());
                queryString.append("           UNION ALL ");
                queryString.append("           SELECT shop_id AS root_id, shop_code AS root_code, ");
                queryString.append("                  NAME AS root_name, channel_type_id, shop_id ");
                queryString.append("             FROM shop ");
                queryString.append("            WHERE shop_id = ?) b ");
                lstParam.add(shop.getShopId());
                queryString.append("    WHERE grp.shop_id = b.shop_id ");

                if (fromDate != null) {
                    queryString.append("      AND grp.last_modified >= ? ");
                    lstParam.add(fromDate);
                }
                if (toDate != null) {
                    queryString.append("      AND grp.last_modified <= ? ");
                    lstParam.add(toDate);
                }
                if (form.getTransType() != null && !form.getTransType().trim().equals("")) {
                    queryString.append("      AND grp.trans_type = ? ");
                    lstParam.add(form.getTransType().trim());
                }

                if (form.getChannelTypeId() != null && !form.getChannelTypeId().trim().equals("")) {
                    queryString.append("      AND grp.channel_type_id = ? ");
                    lstParam.add(Long.valueOf(form.getChannelTypeId().trim()));
                }

                if (form.getOwnerType() != null && !form.getOwnerType().trim().equals("")) {
                    queryString.append("      AND grp.owner_type = ? ");
                    lstParam.add(Long.valueOf(form.getOwnerType().trim()));
                }

                queryString.append(" GROUP BY root_code, root_name, trans_type ");
                queryString.append(" ORDER BY objCode, transType ");

                Query queryObject = getSession().createSQLQuery(queryString.toString()).
                        addScalar("objCode", Hibernate.STRING).
                        addScalar("transType", Hibernate.STRING).
                        addScalar("countIsdn", Hibernate.LONG).
                        addScalar("sumTrans", Hibernate.LONG).
                        addScalar("sumTarIsdn", Hibernate.LONG).
                        addScalar("sumAmount", Hibernate.LONG).
                        setResultTransformer(Transformers.aliasToBean(ReportAnyPayBean.class));
                for (int idx = 0; idx < lstParam.size(); idx++) {
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
            if (form.getReportType().trim().equals("1")) {
                templatePath = "_General";
                if (parentShop == null) {
                    templatePath += "_VT";
                } else if (parentShop.getShopId().intValue() == Constant.VT_SHOP_ID.intValue()) {
                    templatePath += "_CN";
                } else {
                    templatePath += "_CH";
                }
            }
            String prefixPath = "ReportAnyPay";
            templatePath = tmp + prefixPath + templatePath + ".xls";
            filePath += prefixPath + "_" + userToken.getLoginName() + "_" + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertDateTimeToString(getSysdate()));
            beans.put("fromDate", DateTimeUtils.convertDateTimeToString(fromDate));
            beans.put("toDate", DateTimeUtils.convertDateTimeToString(toDate));
            if (parentShop != null) {
                beans.put("parentShopName", parentShop.getName());
            } else {
                beans.put("parentShopName", "");
            }
            beans.put("shopName", shop.getName());
            beans.put("staffName", staff.getName());

            beans.put(LIST_ITEMS, lstTemp);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            req.setAttribute("filePath", filePath);
            req.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");

            /* End TuanPV Comment */

            /*TuanPV - 24/02/2010 - Gửi lệnh xuất báo cáo sang app ReportServer*/
            /*
            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", form.getFromDate());
            request.set("TO_DATE", form.getToDate());
            request.set("USER_NAME", userToken.getLoginName());

            request.set("SHOP_ID", shop.getShopId());
            request.set("SHOP_NAME", shop.getName());
            if(form.getReportDetail() != null && form.getReportDetail().equals(2L))
            {
            request.set("IS_REPORT_DETAIL", "true");
            }else
            {
            request.set("IS_REPORT_DETAIL", "false");
            }

            request.set("RECEIPT_STATUS", form.getReceiptStatus());
            request.set("TELECOM_SERVICE_ID", form.getTelecomServiceId());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_BANK_RECEIPT);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if(response != null
            && response.get(ReportConstant.RESULT_FILE) != null
            && !"".equals(response.get(ReportConstant.RESULT_FILE).toString()))
            {
            //Thong bao len jsp
            req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
            req.setAttribute("displayResultMsgClient", "report.stocktrans.error.successMessage");
            }else
            {
            req.setAttribute("displayResultMsgClient", "report.warning.noResult");
            }
             */

        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "report.warning.noResult");
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

