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
import com.viettel.im.database.BO.ReqCardNotSale;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ApproveRequestLockUserDAO extends BaseDAOAction{
    
    private static final Logger log = Logger.getLogger(ApproveRequestLockUserDAO.class);
    private String pageForward;
    private final String APPROVE_REQ_LOCK_USER = "approveRequestLockUser";
    private final String PAGE_NAVIGATOR = "pageNavigator";
    private final String REQUEST_MESSAGE = "message";
    private final String SESSION_REQUEST_LIST = "listRequest";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    
    private  String staffCodePublic;
    private  String reqCodePublic;
    private     Long statusPublic;
    private     Date fromDatePublic;
    private     Date toDatePublic;
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
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            this.reqCardNotSaleFrom.resetForm();
            //reqCardNotSaleFrom.setStaffCode(vsaUserToken.getUserName().toUpperCase());
            //reqCardNotSaleFrom.setStaffName(vsaUserToken.getUserName().toUpperCase());
            pageForward = APPROVE_REQ_LOCK_USER;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }
    
     public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of ApproveRequestLockUserDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of ApproveRequestLockUserDAO");
        pageForward = PAGE_NAVIGATOR;
        return pageForward;
    }
    
    public String searchRequestLock() {
        
        log.info("Begin method searchRequestLock of ApproveRequestLockUserDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();
        req.getSession().setAttribute(SESSION_REQUEST_LIST, new ArrayList<ReqCardNotSaleBean>());
        String staffCode = reqCardNotSaleFrom.getStaffCode();
        String reqCode = reqCardNotSaleFrom.getReqCode();
        Date fromDate = reqCardNotSaleFrom.getFromDate();
        Date toDate = reqCardNotSaleFrom.getToDate();
        Long status = reqCardNotSaleFrom.getStatus();
        
        staffCodePublic = staffCode;
        reqCodePublic = reqCode;
        fromDatePublic = fromDate;
        toDatePublic = toDate;
        statusPublic = status;
        
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" SELECT REQ_ID AS reqId , ");
        strQueryRequestList.append(" REQ_CODE AS reqCode , ");
        strQueryRequestList.append(" STAFF_CODE AS staffCode , ");
        strQueryRequestList.append(" CREATE_REQ_DATE AS createReqDate , ");
        strQueryRequestList.append(" STATUS AS status ");
        strQueryRequestList.append(" FROM REQ_CARD_NOT_SALE ");
        strQueryRequestList.append(" WHERE 1 = 1  ");
      
        List listParam = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (staffCode != null && !staffCode.equals("")) {
            strQueryRequestList.append(" AND Staff_Code =? ");
            listParam.add(staffCode.trim().toUpperCase());
        }
        if (reqCode != null && !reqCode.equals("")) {
            strQueryRequestList.append(" AND Req_Code Like ? ");
            listParam.add("%"+reqCode.trim().toUpperCase()+"%");
        }
        if (fromDate != null) {
            strQueryRequestList.append(" AND CREATE_REQ_DATE >= to_date(?,'dd/mm/yyyy') ");
            listParam.add(dateFormat.format(fromDate));
        }
        if (toDate != null) {
            strQueryRequestList.append(" AND CREATE_REQ_DATE < to_date(?,'dd/mm/yyyy') + 1 ");
            listParam.add(dateFormat.format(toDate));
        }
        if (status != null) {
            strQueryRequestList.append(" AND status = ? ");
            listParam.add(status);
        }
        
        strQueryRequestList.append(" ORDER BY CREATE_REQ_DATE ");
        
        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                    addScalar("reqId", Hibernate.LONG).
                    addScalar("reqCode", Hibernate.STRING).
                    addScalar("staffCode", Hibernate.STRING).
                    addScalar("createReqDate", Hibernate.DATE).
                    addScalar("status", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(ReqCardNotSaleBean.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        }
            
        List<ReqCardNotSaleBean> listReqCardNotSaleBean = queryRequestList.list();
        req.getSession().setAttribute(SESSION_REQUEST_LIST, listReqCardNotSaleBean);

        if (listReqCardNotSaleBean != null && listReqCardNotSaleBean.size() > 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.success.list.request");
            List listParamValue = new ArrayList();
            listParamValue.add(listReqCardNotSaleBean.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParamValue);

        } else {
            //
            req.setAttribute(REQUEST_MESSAGE, "search.Unsuccess.list.request");
        }
        
        pageForward = APPROVE_REQ_LOCK_USER;
        
        return pageForward;
    }
 
    public List<ImSearchBean> getListStaffCode(ImSearchBean imSearchBean) {
        try {
//            UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
//            String param = imSearchBean.getOtherParamValue();
//            String[] arrParam = param.split(";");
//
//            if (arrParam != null && arrParam.length >= 1) {
//                ownerType = Long.valueOf(arrParam[0]);
//
//            }
//            if (ownerType.equals(0L)) {
//                return null;
//            }
            List parameterList = new ArrayList();

            String SELECT_STAFF = "select new com.viettel.im.client.bean.ImSearchBean(staffCode, name) from Staff st where status= ? ";
            parameterList.add(Constant.STATUS_USE);

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                SELECT_STAFF += " and lower(staffCode) like ? ";
                parameterList.add("%" + imSearchBean.getCode().toLowerCase().trim() + "%");
            }
            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                SELECT_STAFF += " and lower(name) like ? ";
                parameterList.add("%" + imSearchBean.getName().toLowerCase().trim() + "%");
            }

            Query qSelectShopStaff = getSession().createQuery(SELECT_STAFF);

            for (int i = 0; i < parameterList.size(); i++) {
                qSelectShopStaff.setParameter(i, parameterList.get(i));
            }
            qSelectShopStaff.setMaxResults(300);
            return qSelectShopStaff.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String lookDetailRequest() throws Exception {
        log.info("Begin. lookDetailRequest");
        String pageForward = "lookDetailRequest";
        Long reqId = Long.parseLong(getRequest().getParameter("reqId"));

        List<ReqCardNotSaleBean> requestListDeatil = getRequestListDeatil(reqId);

        getRequest().getSession().setAttribute("requestListDeatil", requestListDeatil);


        return pageForward;
    }
    
    public String acceptRequest() throws Exception {
        
        String pageForward = APPROVE_REQ_LOCK_USER;
        HttpServletRequest req = getRequest();
        Long reqId = Long.parseLong(getRequest().getParameter("reqId"));
        log.info("Begin acceptRequest" + reqId);
        Long staffId = 0L;
        String staffCode = "";
        
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        if (staff != null) {
            staffId = staff.getStaffId();
            staffCode = staff.getStaffCode();
        }
        
        StringBuilder sqlAccept = new StringBuilder();
        sqlAccept.append(" UPDATE ");
        sqlAccept.append(" REQ_CARD_NOT_SALE ");
        sqlAccept.append(" SET Status  = 1, ");
        sqlAccept.append(" approve_date = ?, ");
        sqlAccept.append(" approve_staff_id = ? ");
        sqlAccept.append(" WHERE ");
        sqlAccept.append(" Req_ID = ? ");
        Query query = getSession().createSQLQuery(sqlAccept.toString());
        query.setParameter(0, getSysdate());
        query.setParameter(1, staffId);
        query.setParameter(2, reqId);
        int count = query.executeUpdate();
        ReqCardNotSale reqCardNotSale = findById(reqId);
        List<ReqCardNotSaleBean> listReqCardNotSaleDetail =  getRequestListDeatil(reqId);
        List<ReqCardNotSaleBean> listStockCard = (ArrayList<ReqCardNotSaleBean>) req.getSession().getAttribute(SESSION_REQUEST_LIST);
        if (count > 0 && listReqCardNotSaleDetail != null && listReqCardNotSaleDetail.size() > 0) {
            for (int i = 0; i< listReqCardNotSaleDetail.size(); i ++) {
                StringBuilder sqlInsert = new StringBuilder();
                StringBuilder sqlInsertMT = new StringBuilder();
                StringBuilder sqlUpdateDetail = new StringBuilder();
                sqlUpdateDetail.append(" UPDATE ");
                sqlUpdateDetail.append(" REQ_CARD_DETAIL_NOT_SALE ");
                sqlUpdateDetail.append(" SET Status  = 1 ");
                sqlUpdateDetail.append(" WHERE ");
                sqlUpdateDetail.append(" Req_Detail_ID = ? ");
                Query queryUpdate = getSession().createSQLQuery(sqlUpdateDetail.toString());
                queryUpdate.setParameter(0, listReqCardNotSaleDetail.get(i).getReqDetailId());
                queryUpdate.executeUpdate();
                
                String messgage = "O seu centro tem recarga {0} que ainda nao foi vendido no sistema. Solicite ao seu staff para vende-lo para ser possivel usar o sistema";
                sqlInsert.append("INSERT INTO LOCK_USER_INFO(STAFF_ID, TRANS_ID, TRANS_CODE, TRANS_DATE, TRANS_STATUS, CREATE_DATE, LOCK_TYPE_ID, SERIAL, STOCK_MODEL_ID, LAST_CHECK, SHOP_ID )");
                sqlInsert.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
                Query queryInsert = getSession().createSQLQuery(sqlInsert.toString());
                queryInsert.setParameter(0, listReqCardNotSaleDetail.get(i).getLockStaffId());
                queryInsert.setParameter(1, reqId);
                queryInsert.setParameter(2, reqCardNotSale.getReqCode());
                queryInsert.setParameter(3, getSysdate());
                queryInsert.setParameter(4, 9L);
                queryInsert.setParameter(5, getSysdate());
                queryInsert.setParameter(6, 15L);
                queryInsert.setParameter(7, Long.valueOf(listReqCardNotSaleDetail.get(i).getSerial()));
                queryInsert.setParameter(8, listReqCardNotSaleDetail.get(i).getStockModelId());
                queryInsert.setParameter(9, getSysdate());
                queryInsert.setParameter(10, listReqCardNotSaleDetail.get(i).getShopId());
                queryInsert.executeUpdate();
                //Insert du lieu de SMS cho Staff bi Lock
                StaffDAO staffDAO = new StaffDAO();
                String isdnStaff = "";
                String isdnStaffOwner = "";
                String prefix = "258";
                Staff staffLock = staffDAO.findById(listReqCardNotSaleDetail.get(i).getLockStaffId());
                if (staffLock != null) {
                    isdnStaff = prefix + getISDNOfUserVSA(getSession(), staffLock.getStaffCode().toUpperCase());
                }
                messgage = messgage.replace("{0}", listReqCardNotSaleDetail.get(i).getSerial().toString());
                
                sqlInsertMT.append("INSERT INTO MT (MT_ID, MO_HIS_ID, MSISDN, MESSAGE, RETRY_NUM, CHANNEL, RECEIVE_TIME)");
                sqlInsertMT.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
                Query queryInsertMT = getSession().createSQLQuery(sqlInsertMT.toString());
                Long mtId = getSequence("MT_SEQ");
                queryInsertMT.setParameter(0, mtId);
                queryInsertMT.setParameter(1, mtId);
                queryInsertMT.setParameter(2, isdnStaff);
                queryInsertMT.setParameter(3, messgage);
                queryInsertMT.setParameter(4, 0);
                queryInsertMT.setParameter(5, "86904");
                queryInsertMT.setParameter(6, getSysdate());
                queryInsertMT.executeUpdate();
                //INSERT du lieu  SMS cho nhan vien co the cao
                if (listReqCardNotSaleDetail.get(i).getOwnerType() != null 
                        && listReqCardNotSaleDetail.get(i).getOwnerType().equals("2")) {
                    String messgageOwner = "O seu centro tem recarga {0} que ainda nao foi vendido no sistema. Solicite ao seu staff para vende-lo para ser possivel usar o sistema";
                    Staff staffOwner = staffDAO.findById(listReqCardNotSaleDetail.get(i).getOwnerId());
                    if (staffOwner != null) {
                        isdnStaffOwner = prefix + getISDNOfUserVSA(getSession(), staffOwner.getStaffCode().toUpperCase());
                    }
                    StringBuilder sqlInsertMTForStaff = new StringBuilder();
                    messgageOwner = messgageOwner.replace("{0}", listReqCardNotSaleDetail.get(i).getSerial().toString());
                    sqlInsertMTForStaff.append("INSERT INTO MT (MT_ID, MO_HIS_ID, MSISDN, MESSAGE, RETRY_NUM, CHANNEL, RECEIVE_TIME)");
                    sqlInsertMTForStaff.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
                    Query queryInsertMTForStaff = getSession().createSQLQuery(sqlInsertMTForStaff.toString());
                    Long Id = getSequence("MT_SEQ");
                    queryInsertMTForStaff.setParameter(0, Id);
                    queryInsertMTForStaff.setParameter(1, Id);
                    queryInsertMTForStaff.setParameter(2, isdnStaffOwner);
                    queryInsertMTForStaff.setParameter(3, messgageOwner);
                    queryInsertMTForStaff.setParameter(4, 0);
                    queryInsertMTForStaff.setParameter(5, "86904");
                    queryInsertMTForStaff.setParameter(6, getSysdate());
                    queryInsertMTForStaff.executeUpdate();
                }
            }
            req.setAttribute(REQUEST_MESSAGE, "approve.success");
        } else {
            //
            req.setAttribute(REQUEST_MESSAGE, "approve.Unsuccess");
        }
        //refesh lai vung lam viec
                    
         if (listStockCard != null && listStockCard.size() > 0) {
            ReqCardNotSaleBean reqCardNotSaleBean1 = null;
            for (int i = 0; i < listStockCard.size(); i++) {
                reqCardNotSaleBean1 = listStockCard.get(i);
                if (reqId.equals(reqCardNotSaleBean1.getReqId())) {
                    reqCardNotSaleBean1.setStatus(1L);
                    break;
                }
            }
        }
         
        //List<ReqCardNotSaleBean> listReqCardNotSaleBean = getListReqCardNotSale(getSession(), staffCodePublic, reqCodePublic, fromDatePublic, toDatePublic, statusPublic);
        req.getSession().setAttribute(SESSION_REQUEST_LIST, listStockCard);
        
        log.info("End acceptRequest");
        
        return pageForward;
    }
    
    public void deleteDetailRequest() throws Exception {
        
        HttpServletRequest req = getRequest();
        String pageForward = "lookDetailRequest";
        Long reqDetailId = Long.parseLong(getRequest().getParameter("reqDetailId"));
        Long reqId = Long.parseLong(getRequest().getParameter("reqId"));
        log.info("Begin deleteDetailRequest" + reqDetailId);
        
        StringBuilder sqlAccept = new StringBuilder();
        sqlAccept.append(" DELETE ");
        sqlAccept.append(" REQ_CARD_DETAIL_NOT_SALE ");
        sqlAccept.append(" WHERE ");
        sqlAccept.append(" Req_Detail_Id = ? ");
        Query query = getSession().createSQLQuery(sqlAccept.toString());
        query.setParameter(0, reqDetailId);
        int count = query.executeUpdate();
        if (count > 0) {
            req.setAttribute(REQUEST_MESSAGE, "delete.success");
        }
        else {
            //
            req.setAttribute(REQUEST_MESSAGE, "delete.Unsuccess");
        }
        // refesh lai vung can xoa
        List<ReqCardNotSaleBean> requestListDeatil = getRequestListDeatil(reqId);
        
        req.getSession().removeAttribute("requestListDeatil");
        req.getSession().setAttribute("requestListDeatil", requestListDeatil);
        
        log.info("End deleteDetailRequest");
        
    }
    
    public List<ReqCardNotSaleBean> getListReqCardNotSale(Session session, String staffCode, String reqCode, Date fromDate, Date toDate, Long status) {

        List<ReqCardNotSaleBean> listReqCardNotSaleBean  = new ArrayList<ReqCardNotSaleBean>();
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append(" SELECT REQ_ID AS reqId , ");
        strQueryRequestList.append(" REQ_CODE AS reqCode , ");
        strQueryRequestList.append(" STAFF_CODE AS staffCode , ");
        strQueryRequestList.append(" CREATE_REQ_DATE AS createReqDate , ");
        strQueryRequestList.append(" STATUS AS status ");
        strQueryRequestList.append(" FROM REQ_CARD_NOT_SALE ");
        strQueryRequestList.append(" WHERE 1 = 1  ");

        List listParam = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (staffCode != null && !staffCode.equals("")) {
            strQueryRequestList.append(" AND Staff_Code =? ");
            listParam.add(staffCode.trim().toUpperCase());
        }
        if (reqCode != null && !reqCode.equals("")) {
            strQueryRequestList.append(" AND Req_Code Like ? ");
            listParam.add("%" + reqCode.trim().toUpperCase() + "%");
        }
        if (fromDate != null) {
            strQueryRequestList.append(" AND CREATE_REQ_DATE >= to_date(?,'dd/mm/yyyy') ");
            listParam.add(dateFormat.format(fromDate));
        }
        if (toDate != null) {
            strQueryRequestList.append(" AND CREATE_REQ_DATE <= to_date(?,'dd/mm/yyyy') ");
            listParam.add(dateFormat.format(toDate));
        }
        if (status != null) {
            strQueryRequestList.append(" AND status = ? ");
            listParam.add(status);
        }

        strQueryRequestList.append(" ORDER BY CREATE_REQ_DATE ");

        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                addScalar("reqId", Hibernate.LONG).
                addScalar("reqCode", Hibernate.STRING).
                addScalar("staffCode", Hibernate.STRING).
                addScalar("createReqDate", Hibernate.DATE).
                addScalar("status", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(ReqCardNotSaleBean.class));

        for (int i = 0; i < listParam.size(); i++) {
            queryRequestList.setParameter(i, listParam.get(i));
        }
        listReqCardNotSaleBean = queryRequestList.list();

        return listReqCardNotSaleBean;
    }
    
    public List<ReqCardNotSaleBean> getRequestListDeatil(Long reqId) {
        try {
            List parameterList = new ArrayList();

            String listSQL = "SELECT rc.REQ_ID AS reqId, rc.REQ_DETAIL_ID AS reqDetailId , "
                    + " rc.SERIAL AS serial, rc.Lock_Staff_Id AS lockStaffId , rc.stock_model_id AS stockModelId, "
                    + " (CASE WHEN rc.Owner_type = '1' THEN (SELECT Shop_Code FROM Shop WHERE Shop_ID = rc.Owner_id) "
                    + " WHEN rc.Owner_type = '2' THEN (SELECT Staff_Code FROM staff WHERE Staff_Id = rc.Owner_id) "
                    + " ELSE null END) AS storageCode, "
                    + " rc.Owner_type AS ownerType, rc.Owner_Id AS ownerId, "
                    + " (SELECT Staff_Code FROM Staff WHERE Staff_id = rc.lock_staff_id) AS staffCode, "
                    + " rc.cust_name AS custName, rc.Req_no AS reqNo,  rc.status AS status, rc.shop_id AS shopId "
                    + " FROM REQ_CARD_DETAIL_NOT_SALE rc WHERE rc.req_id = ? ";
            
            parameterList.add(reqId);

            Query qlist = getSession().createSQLQuery(listSQL).addScalar("reqId", Hibernate.LONG)
                                    .addScalar("reqDetailId", Hibernate.LONG)
                                    .addScalar("serial", Hibernate.STRING)
                                    .addScalar("lockStaffId", Hibernate.LONG)
                                    .addScalar("stockModelId", Hibernate.LONG)
                                    .addScalar("storageCode", Hibernate.STRING)
                                    .addScalar("ownerType", Hibernate.STRING)
                                    .addScalar("ownerId", Hibernate.LONG)
                                    .addScalar("staffCode", Hibernate.STRING)
                                    .addScalar("custName", Hibernate.STRING)
                                    .addScalar("reqNo", Hibernate.STRING)
                                    .addScalar("status", Hibernate.LONG)
                                    .addScalar("shopId", Hibernate.LONG)
                                    .setResultTransformer(Transformers.aliasToBean(ReqCardNotSaleBean.class));

            for (int i = 0; i < parameterList.size(); i++) {
                qlist.setParameter(i, parameterList.get(i));
            }
            qlist.setMaxResults(300);
            return qlist.list();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public ReqCardNotSale findById(java.lang.Long id) {
        log.debug("getting ReqCardNotSale instance with ReqCardNotSaleId: " + id);
        try {
            ReqCardNotSale instance = (ReqCardNotSale) getSession().get("com.viettel.im.database.BO.ReqCardNotSale", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public String getISDNOfUserVSA(Session session, String staffName) {
        
        log.info("START getISDNOfUserVSA");
        String isdn = "";
        try {
            String strQuery = " SELECT CellPhone AS cellPhone FROM vsa_v3.users WHERE UPPER(user_name) = UPPER(?) ";
            SQLQuery query = session.createSQLQuery(strQuery).addScalar("cellPhone", Hibernate.STRING);
            query.setParameter(0, staffName);
            Object cellPhoneObj = query.list().get(0);
            if (cellPhoneObj != null) {
               isdn = cellPhoneObj.toString();
            }
        } catch (Exception e) {
            log.info("ERROR getISDNOfUserVSA" + e);
        }
        return isdn;
    }
}
