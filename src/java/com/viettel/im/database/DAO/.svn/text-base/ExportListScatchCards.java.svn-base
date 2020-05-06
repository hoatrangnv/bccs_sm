/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ReqCardNotSaleBean;
import com.viettel.im.client.bean.ReqCardNotSaleErrorBean;
import com.viettel.im.client.form.ReqCardNotSaleFrom;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ExportListScatchCards extends BaseDAOAction{
    
    private static final Logger log = Logger.getLogger(ImportQuotasUnitUnderTheFileDAO.class);
    private String pageForward;
    private final String EXPORT_PREPARE = "exportListScatchCards";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final Long MAX_SEARCH_RESULT = 150L;
    ReqCardNotSaleFrom reqCardNotSaleFrom = new ReqCardNotSaleFrom();

    public ReqCardNotSaleFrom getReqCardNotSaleFrom() {
        return reqCardNotSaleFrom;
    }

    public void setReqCardNotSaleFrom(ReqCardNotSaleFrom reqCardNotSaleFrom) {
        this.reqCardNotSaleFrom = reqCardNotSaleFrom;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            pageForward = EXPORT_PREPARE;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }
    
     public String ExportListScathCards() throws Exception {
        
        log.info("Begin method importFile of ImportCardNotSaleFileDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
     
        String shopCode = reqCardNotSaleFrom.getShopCode();
        Date fromDate = reqCardNotSaleFrom.getFromDate();
        Date toDate = reqCardNotSaleFrom.getToDate();
        Long status = reqCardNotSaleFrom.getStatus();
        Long statusSole = reqCardNotSaleFrom.getStatusSold();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<ReqCardNotSaleErrorBean> listReportCard = new ArrayList<ReqCardNotSaleErrorBean>();
        
        pageForward = EXPORT_PREPARE;
        List listParam = new ArrayList();
        
        StringBuilder sqlExport = new StringBuilder();
        sqlExport.append(" WITH temp(reqDetailId, serial, createReqDate, createStaffId, approveDate, approveStaffId,  reqCode, ownerType, ownerId, lockStaffId, shopId, statusCard, status, staffName, staffCode, cellPhone) as ( ");
        sqlExport.append(" SELECT rcd.req_detail_id AS reqDetailId, ");
        sqlExport.append(" rcd.serial             AS serial, ");
        sqlExport.append(" rc.create_req_date     AS createReqDate, ");
        sqlExport.append(" rc.create_Staff_Id     AS createStaffId, ");
        sqlExport.append(" rc.approve_date     AS approveDate, ");
        sqlExport.append(" rc.approve_Staff_Id     AS approveStaffId, ");
        sqlExport.append(" rc.req_code     AS reqCode, ");
        sqlExport.append(" rcd.owner_type         AS ownerType, ");
        sqlExport.append(" rcd.owner_id           AS ownerId, ");
        sqlExport.append(" rcd.lock_staff_id      AS lockStaffId, ");
        sqlExport.append(" (SELECT Shop_id FROM staff WHERE staff_id = rcd.lock_staff_id) AS shopId, ");
        sqlExport.append(" (SELECT status FROM Stock_Card WHERE serial = to_number(rcd.serial)) AS statusCard, rcd.status AS status, ");
        sqlExport.append(" s.staff_Code AS staffCode, ");
        
        sqlExport.append(" s.name AS staffName, ");
        sqlExport.append(" (SELECT CellPhone AS cellPhone FROM vsa_v3.users WHERE UPPER(user_name) = UPPER(s.Staff_Code)) AS cellPhone ");
        sqlExport.append(" FROM req_card_detail_not_sale rcd ");
        sqlExport.append(" INNER JOIN req_card_not_sale rc ON rcd.req_id = rc.req_id ");
        sqlExport.append(" INNER JOIN staff s ON rcd.lock_staff_id = s.staff_id ) ");
        //SELECT bang tam temp
        sqlExport.append(" SELECT reqDetailId, serial, to_char(createReqDate, 'dd/MM/yyyy') AS createReqDateStr , reqCode, ");
        sqlExport.append(" (CASE WHEN ownerType = 2 THEN  (SELECT staff_code FROM staff WHERE staff_id = ownerId)  ");
        sqlExport.append(" WHEN ownerType = 1 THEN (SELECT staff_code FROM staff WHERE staff_id = lockStaffId) ELSE null END) AS staffCodeOwner, ");
        sqlExport.append(" (CASE WHEN ownerType = 2 THEN  (SELECT name FROM staff WHERE staff_id = ownerId)  ");
        sqlExport.append(" WHEN ownerType = 1 THEN (SELECT name FROM staff WHERE staff_id = lockStaffId) ELSE null END) AS staffNameOwner, ");
        sqlExport.append(" (SELECT Shop_Code FROM Shop WHERE Shop_id = shopId) AS shopCode, ");
        sqlExport.append(" staffName, staffCode, cellPhone, ");
        sqlExport.append(" (CASE WHEN statusCard = 0 THEN 'Sold' ELSE 'UnSold' END) AS statusCard, ");
        sqlExport.append(" (CASE WHEN status = 0 THEN 'Not Approve' WHEN status = 1 THEN 'Approved'  WHEN  status = 2 THEN 'Lost Card '"
                + " WHEN status = 3 THEN 'Unlocked' ELSE 'IMPORTED SOLD' END) AS statusApprove, ");
        sqlExport.append(" (SELECT staff_code FROM staff WHERE staff_id = createStaffId) AS  staffCreateCode, ");
        sqlExport.append(" to_char(approveDate, 'dd/MM/yyyy') AS approveReqDateStr, ");
        sqlExport.append(" (SELECT staff_code FROM staff WHERE staff_id = approveStaffId) AS  approveStaffCode ");
        sqlExport.append(" FROM temp WHERE 1 = 1  ");
        
        if (fromDate != null) {
            sqlExport.append(" AND createReqDate >= to_date(?,'dd/mm/yyyy') ");
            listParam.add(dateFormat.format(fromDate));
        }
        if (toDate != null) {
            sqlExport.append(" AND createReqDate < to_date(?,'dd/mm/yyyy') + 1 ");
            listParam.add(dateFormat.format(toDate));
        }
        
        if (shopCode != null && !("").equals(shopCode)) {
                sqlExport.append(" and shopId IN (SELECT  sh.shop_id FROM shop sh "
                        + " WHERE sh.status = 1 "
                        + " START WITH sh.shop_code = ? CONNECT BY PRIOR sh.shop_id = sh.parent_shop_id ) ");
                listParam.add(shopCode);
        }
        
        if (status != null) {
            sqlExport.append(" AND status = ? ");
            listParam.add(status);
        }
        
        if (statusSole != null) {
            if (statusSole == 0) {
                sqlExport.append(" AND statusCard = ? ");
                listParam.add(statusSole);
            } else {
                sqlExport.append(" AND statusCard != ? ");
                listParam.add(0);
            }
        }
        
        Query queryRequestList = getSession().createSQLQuery(sqlExport.toString()).
                    addScalar("reqDetailId", Hibernate.LONG).
                    addScalar("serial", Hibernate.STRING).
                    addScalar("createReqDateStr", Hibernate.STRING).
                    addScalar("reqCode", Hibernate.STRING).
                    addScalar("staffCodeOwner", Hibernate.STRING).
                    addScalar("staffNameOwner", Hibernate.STRING).
                    addScalar("shopCode", Hibernate.STRING).
                    addScalar("staffName", Hibernate.STRING).
                    addScalar("staffCode", Hibernate.STRING).
                    addScalar("cellPhone", Hibernate.STRING).
                    addScalar("statusCard", Hibernate.STRING).
                    addScalar("statusApprove", Hibernate.STRING).
                    addScalar("staffCreateCode", Hibernate.STRING).
                    addScalar("approveReqDateStr", Hibernate.STRING).
                    addScalar("approveStaffCode", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ReqCardNotSaleBean.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        } 
        listReportCard  = queryRequestList.list();   
        
        if (listReportCard != null && listReportCard.size() > 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List listParamValue = new ArrayList();
            listParamValue.add(listReportCard.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);
        } else {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }
        //Hien thi toan bo
        downloadFile("exportListScatchCards", listReportCard, fromDate, toDate);
        
        return pageForward;
    }
    
    public void downloadFile(String templatePathResource, List listReport, Date fromDate, Date todate) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Date sysDate = getSysdate();
        String fromDateStr = "";
        String toDateStr = "";
        String sysDateStr = "";
        if (fromDate != null) {
            fromDateStr = f.format(fromDate);
        }
        if (todate != null) {
            toDateStr = f.format(todate);
        }
        if (sysDate != null) {
           sysDateStr = f.format(sysDate);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("sysDate", sysDateStr);
        beans.put("fromDate", fromDateStr);
        beans.put("todate", toDateStr);
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        //String tempAction = "/download.do?" + QueryCrypt.encrypt(req, "filename=" + filePath);
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
        //req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

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

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua shop dua tren code
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and status = 1 ");

        strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        strQuery1.append("and lower(a.shopCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

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
