/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.guhesan.querycrypt.QueryCrypt;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ReqCardNotSaleBean;
import com.viettel.im.client.bean.ReqCardNotSaleErrorBean;
import com.viettel.im.client.form.ReqCardNotSaleFrom;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
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
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ExportListCardLost extends BaseDAOAction{
    
    private static final Logger log = Logger.getLogger(ImportQuotasUnitUnderTheFileDAO.class);
    private String pageForward;
    private final String EXPORT_PREPARE = "exportListCardLost";
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
    
     public String ExportListCardLost() throws Exception {
        
        log.info("Begin method ExportListCardLost of ExportListCardLost ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        
        String shopCode = reqCardNotSaleFrom.getShopCode();
        String serial = reqCardNotSaleFrom.getSerial();
        String toSerial = reqCardNotSaleFrom.getToSerial();
        Date fromDate = reqCardNotSaleFrom.getFromDate();
        Date toDate = reqCardNotSaleFrom.getToDate();
        Long status = reqCardNotSaleFrom.getStatus();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        pageForward = EXPORT_PREPARE;
        List<ReqCardNotSaleBean> listStockCardLost = new ArrayList<ReqCardNotSaleBean>();
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" WITH temp(stockCardLostId,serial, stockModelId, storageCode, shopId, ownerType, createReqDateStr, createReqDate, statusApprove) as ( ");
        strQueryRequestList.append(" SELECT stock_card_lost_id AS stockCardLostId, ");
        strQueryRequestList.append(" serial AS serial, stock_model_id AS stockModelId, ");
        strQueryRequestList.append(" (CASE WHEN Owner_type = '1' THEN (SELECT Shop_Code FROM Shop WHERE Shop_ID = Owner_id) ");
        strQueryRequestList.append(" WHEN Owner_type = '2' THEN (SELECT Staff_Code FROM staff WHERE Staff_Id = Owner_id) ELSE null END) AS storageCode, ");
        strQueryRequestList.append(" (CASE WHEN Owner_type = '1' THEN Owner_id ");
        strQueryRequestList.append(" WHEN Owner_type = '2' THEN (SELECT Shop_id FROM staff WHERE Staff_Id = Owner_id ) ELSE 1 END) AS shopId, ");
        strQueryRequestList.append(" owner_type AS ownerType, ");
        strQueryRequestList.append(" to_char(Create_date, 'dd/MM/yyyy') AS createReqDateStr, Create_date AS createReqDate , ");
        strQueryRequestList.append(" (CASE WHEN status = 1 THEN 'Approved' ELSE 'Not Approve' END) AS statusApprove ");
        strQueryRequestList.append(" FROM stock_card_lost WHERE 1 = 1) ");
        
        strQueryRequestList.append(" SELECT stockCardLostId, serial, stockModelId, storageCode, shopId, ownerType, createReqDateStr, createReqDate, statusApprove ");
        strQueryRequestList.append(" FROM temp WHERE 1=1 ");
        
        List listParam = new ArrayList();
        
        if (shopCode != null && !("").equals(shopCode)) {
                strQueryRequestList.append(" and shopId IN (SELECT  sh.shop_id FROM shop sh "
                        + " WHERE sh.status = 1 "
                        + " START WITH sh.shop_code = ? CONNECT BY PRIOR sh.shop_id = sh.parent_shop_id ) ");
                listParam.add(shopCode);
        }
        
        if (serial != null && !serial.equals("")) {
            strQueryRequestList.append(" AND to_number(serial) >= ? ");
            listParam.add(Long.valueOf(serial));
        }
        if (toSerial != null && !toSerial.equals("")) {
            strQueryRequestList.append(" AND to_number(serial) <= ? ");
            listParam.add(Long.valueOf(toSerial));
        }
        if (fromDate != null) {
            strQueryRequestList.append(" AND createReqDate >= to_date(?,'dd/mm/yyyy') ");
            listParam.add(dateFormat.format(fromDate));
        }
        if (toDate != null) {
            strQueryRequestList.append(" AND createReqDate < to_date(?,'dd/mm/yyyy') + 1");
            listParam.add(dateFormat.format(toDate));
        }
        
        if (status != null) {
            strQueryRequestList.append(" AND status = ? ");
            listParam.add(status);
        }

        strQueryRequestList.append(" ORDER BY createReqDate DESC ");

        Query queryRequestList = getSession().createSQLQuery(strQueryRequestList.toString()).
                addScalar("stockCardLostId", Hibernate.LONG).
                addScalar("serial", Hibernate.STRING).
                addScalar("stockModelId", Hibernate.LONG).
                addScalar("storageCode", Hibernate.STRING).
                addScalar("shopId", Hibernate.LONG).
                addScalar("ownerType", Hibernate.STRING).
                addScalar("createReqDateStr", Hibernate.STRING).
                addScalar("createReqDate", Hibernate.DATE).
                addScalar("statusApprove", Hibernate.STRING).
                
                setResultTransformer(Transformers.aliasToBean(ReqCardNotSaleBean.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        }
        listStockCardLost = queryRequestList.list();
        
        if (listStockCardLost != null && listStockCardLost.size() > 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List listParamValue = new ArrayList();
            listParamValue.add(listStockCardLost.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);
        } else {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }
        //Hien thi toan bo
        downloadFile("exportListCardLost", listStockCardLost, fromDate, toDate);
        
        return pageForward;
    }
    
    public void downloadFile(String templatePathResource, List listReport, Date fromDate, Date todate) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String fromDateStr = "";
        String toDateStr = "";
        if (fromDate != null) {
            fromDateStr = f.format(fromDate);
        }
        if (todate != null) {
            toDateStr = f.format(todate);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realPath = filePath;
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        Date sysDate = getSysdate();
        String sysDateStr = "";
        if (sysDate != null) {
           sysDateStr = f.format(sysDate);
        }
        beans.put("sysDate", sysDateStr);
        beans.put("fromDate", fromDateStr);
        beans.put("todate", toDateStr);
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        //req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
        DownloadDAO downloadDAO = new DownloadDAO();
        //String tempAction = "/download.do?" + QueryCrypt.encrypt(req, "filename=" + filePath);
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
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
