/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ReqCardNotSaleBean;
import com.viettel.im.client.form.ReqCardNotSaleFrom;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ApproveStockCardLostDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(ApproveRequestLockUserDAO.class);
    private String pageForward;
    private final String APPROVE_STOCK_CARD_LOST = "approveStockCardLost";
    private final String PAGE_NAVIGATOR = "pageNavigator";
    private final String REQUEST_MESSAGE = "message";
    private final String SESSION_REQUEST_LIST = "listStockCardLost";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final Long MAX_SEARCH_RESULT = 150L;
    ReqCardNotSaleFrom reqCardNotSaleFrom = new ReqCardNotSaleFrom();
    public String[] selectedStockCardLostIds;

    public ReqCardNotSaleFrom getReqCardNotSaleFrom() {
        return reqCardNotSaleFrom;
    }

    public void setReqCardNotSaleFrom(ReqCardNotSaleFrom reqCardNotSaleFrom) {
        this.reqCardNotSaleFrom = reqCardNotSaleFrom;
    }
    
    public String[] getSelectedStockCardLostIds() {
        return selectedStockCardLostIds;
    }

    public void setSelectedStockCardLostIds(String[] selectedStockCardLostIds) {
        this.selectedStockCardLostIds = selectedStockCardLostIds;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }

            //reqCardNotSaleFrom.setStaffCode(vsaUserToken.getUserName().toUpperCase());
            //reqCardNotSaleFrom.setStaffName(vsaUserToken.getUserName().toUpperCase());
            pageForward = APPROVE_STOCK_CARD_LOST;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of ApproveStockCardLostDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of ApproveStockCardLostDAO");
        pageForward = PAGE_NAVIGATOR;
        return pageForward;
    }

    public String searchStockCardLost() {

        log.info("Begin method searchStockCardLost of ApproveStockCardLostDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();
        req.getSession().setAttribute(SESSION_REQUEST_LIST, new ArrayList<ReqCardNotSaleBean>());
        String shopCode = reqCardNotSaleFrom.getShopCode();
        String serial = reqCardNotSaleFrom.getSerial();
        String toSerial = reqCardNotSaleFrom.getToSerial();
        Date fromDate = reqCardNotSaleFrom.getFromDate();
        Date toDate = reqCardNotSaleFrom.getToDate();

        List<ReqCardNotSaleBean> listStockCardLost = getListStockCardLost(session, shopCode, serial, toSerial, fromDate, toDate);
        req.getSession().setAttribute(SESSION_REQUEST_LIST, listStockCardLost);

        if (listStockCardLost != null && listStockCardLost.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List listParamValue = new ArrayList();
            listParamValue.add(listStockCardLost.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);

        } else {
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }

        pageForward = APPROVE_STOCK_CARD_LOST;

        return pageForward;
    }
    
    public String approveStockCardLost() throws Exception {
        
        HttpServletRequest req = getRequest();
        String pageForward = APPROVE_STOCK_CARD_LOST;
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(
                Constant.USER_TOKEN);
        String shopCode = reqCardNotSaleFrom.getShopCode();
        String serial = reqCardNotSaleFrom.getSerial();
        String toSerial = reqCardNotSaleFrom.getToSerial();
        try {
            //List<ReqCardNotSaleBean> listStockCardLost = (ArrayList<ReqCardNotSaleBean>) req.getSession().getAttribute(SESSION_REQUEST_LIST);
            if (reqCardNotSaleFrom.getSelectedStockCardLostIds() != null
                    && reqCardNotSaleFrom.getSelectedStockCardLostIds().length > 0) {
                for (String stockCardLostIds : reqCardNotSaleFrom.getSelectedStockCardLostIds()) {
                    Long stockCardLostId = Long.valueOf(stockCardLostIds);
                    StringBuilder sqlAccept = new StringBuilder();
                    sqlAccept.append(" UPDATE ");
                    sqlAccept.append(" Stock_Card_Lost  ");
                    sqlAccept.append(" SET Status  = 1 ");
                    sqlAccept.append(" WHERE ");
                    sqlAccept.append(" stock_card_lost_id = ? ");
                    Query query = getSession().createSQLQuery(sqlAccept.toString());
                    query.setParameter(0, stockCardLostId);
                    int count = query.executeUpdate();
                    
                    //Update the cao ve da mat cap
                    StringBuilder sqlUpdate = new StringBuilder();
                    sqlUpdate.append(" UPDATE ");
                    sqlUpdate.append(" REQ_CARD_DETAIL_NOT_SALE ");
                    sqlUpdate.append(" SET Status  = 2 ");
                    sqlUpdate.append(" WHERE ");
                    sqlUpdate.append(" SERIAL IN (SELECT SERIAL FROM Stock_Card_Lost WHERE stock_card_lost_id = ? ) ");
                    Query queryUpdate = getSession().createSQLQuery(sqlUpdate.toString());
                    queryUpdate.setParameter(0, stockCardLostId);
                    queryUpdate.executeUpdate();
                    //refesh lai vung lam viec
                    /*
                    if (listStockCardLost != null && listStockCardLost.size() > 0) {
                        ReqCardNotSaleBean reqCardNotSaleBean1 = null;
                        for (int i = 0; i < listStockCardLost.size(); i++) {
                            reqCardNotSaleBean1 = listStockCardLost.get(i);
                            if (stockCardLostId.equals(reqCardNotSaleBean1.getStockCardLostId())) {
                                reqCardNotSaleBean1.setStatus(1L);
                                break;
                            }
                        }
                    }
                    */ 
                }
                req.setAttribute(REQUEST_MESSAGE, "approve.success");
                List<ReqCardNotSaleBean> listStockCardLost = getListStockCardLost(getSession() , shopCode, serial, toSerial, null, null);
                req.getSession().setAttribute(SESSION_REQUEST_LIST, listStockCardLost);
            }
        } catch (Exception ex) {
            log.error("Error:" + ex);
            req.setAttribute(REQUEST_MESSAGE, "approve.Unsuccess");
        }
        return pageForward;
    }

    public List<ReqCardNotSaleBean> getListStockCardLost(Session session, String shopCode, String serial, String toSerial, Date fromDate, Date toDate) {

        List<ReqCardNotSaleBean> listStockCardLost = new ArrayList<ReqCardNotSaleBean>();
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" WITH temp(stockCardLostId,serial, stockModelId, storageCode, shopId, ownerType, createReqDate, lostDate, status) as ( ");
        strQueryRequestList.append(" SELECT stock_card_lost_id AS stockCardLostId, ");
        strQueryRequestList.append(" serial AS serial, stock_model_id AS stockModelId, ");
        strQueryRequestList.append(" (CASE WHEN Owner_type = '1' THEN (SELECT Shop_Code FROM Shop WHERE Shop_ID = Owner_id) ");
        strQueryRequestList.append(" WHEN Owner_type = '2' THEN (SELECT Staff_Code FROM staff WHERE Staff_Id = Owner_id) ELSE null END) AS storageCode, ");
        strQueryRequestList.append(" (CASE WHEN Owner_type = '1' THEN Owner_id ");
        strQueryRequestList.append(" WHEN Owner_type = '2' THEN (SELECT Shop_id FROM staff WHERE Staff_Id = Owner_id) ELSE null END) AS shopId, ");
        strQueryRequestList.append(" owner_type AS ownerType, ");
        strQueryRequestList.append(" create_date AS createReqDate, lost_date AS lostDate, status ");
        strQueryRequestList.append(" FROM stock_card_lost WHERE 1 = 1 AND status = 0 ) ");
        strQueryRequestList.append(" SELECT stockCardLostId, serial, stockModelId, storageCode, shopId, ownerType, createReqDate, lostDate, status ");
        strQueryRequestList.append(" FROM temp WHERE 1=1 ");

        List listParam = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
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
            strQueryRequestList.append(" AND createReqDate < to_date(?,'dd/mm/yyyy') + 1 ");
            listParam.add(dateFormat.format(toDate));
        }

        strQueryRequestList.append(" ORDER BY createReqDate DESC ");

        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("stockCardLostId", Hibernate.LONG).
                addScalar("serial", Hibernate.STRING).
                addScalar("stockModelId", Hibernate.LONG).
                addScalar("storageCode", Hibernate.STRING).
                addScalar("shopId", Hibernate.LONG).
                addScalar("ownerType", Hibernate.STRING).
                addScalar("createReqDate", Hibernate.DATE).
                addScalar("lostDate", Hibernate.DATE).
                addScalar("status", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(ReqCardNotSaleBean.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        }
        listStockCardLost = queryRequestList.list();

        return listStockCardLost;
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
