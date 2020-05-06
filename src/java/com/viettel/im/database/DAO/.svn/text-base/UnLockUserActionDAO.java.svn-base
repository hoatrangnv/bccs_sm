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
import com.viettel.im.common.util.FileUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.ActionLogDetail;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.ReqCardDetailNotSale;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
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
public class UnLockUserActionDAO extends BaseDAOAction{
    
    private static final Logger log = Logger.getLogger(ApproveRequestLockUserDAO.class);
    private String pageForward;
    private final String APPROVE_REQ_UNLOCK_LOCK_USER = "unLockUser";
    private final String PAGE_NAVIGATOR = "pageNavigator";
    private final String REQUEST_MESSAGE = "message";
    private final String SESSION_REQUEST_LIST = "listRequestUnLock";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REASON_TYPE_UNLOCKUSER = "UNLOCK_USER";
    private final String REASON_CODE = "UNLOCK_USER";
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
            this.reqCardNotSaleFrom.resetForm();
            //reqCardNotSaleFrom.setStaffCode(vsaUserToken.getUserName().toUpperCase());
            //reqCardNotSaleFrom.setStaffName(vsaUserToken.getUserName().toUpperCase());
            pageForward = APPROVE_REQ_UNLOCK_LOCK_USER;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }
    
     public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of UnLockUserActionDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("UnlockUserCardOfBranch"), req)) {
                req.getSession().setAttribute("UnlockUserCardOfBranch", 1);
                ReasonDAO reasonDAO = new ReasonDAO();
                List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_UNLOCKUSER);
                req.setAttribute("listReasonUnLockUser", listReason);
            } else {
                req.getSession().setAttribute("UnlockUserCardOfBranch", 0);
                ReasonDAO reasonDAO = new ReasonDAO();
                List<Reason> listReason = reasonDAO.findReasonByTypeCode(REASON_TYPE_UNLOCKUSER, REASON_CODE);
                req.setAttribute("listReasonUnLockUser", listReason);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, ex.toString());
        }

        log.info("End method pageNavigator of UnLockUserActionDAO");
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
        String serial = reqCardNotSaleFrom.getSerial();
        String toSerial = reqCardNotSaleFrom.getToSerial();
        
        StringBuilder strQueryRequestList = new StringBuilder("");
        strQueryRequestList.append("   SELECT rc.REQ_ID AS reqId , rcd.Req_Detail_Id AS reqDetailId , ");
        strQueryRequestList.append(" rc.REQ_CODE AS reqCode , ");
        strQueryRequestList.append(" (SELECT STAFF_CODE FROM STAFF WHERE STAFF_ID = rcd.Lock_staff_id AND ROWNUM <=1) AS staffCode , ");
        strQueryRequestList.append(" (SELECT STAFF_CODE FROM STAFF WHERE STAFF_ID = rc.CREATE_STAFF_ID AND ROWNUM <=1) AS staffCreateCode , ");
        strQueryRequestList.append(" (SELECT STAFF_CODE FROM STAFF WHERE STAFF_ID = rc.APPROVE_STAFF_ID AND ROWNUM <=1) AS approveStaffCode , ");
        strQueryRequestList.append(" rc.Approve_Date AS approveDate, rcd.Serial AS serial, rcd.status AS  status ");
        strQueryRequestList.append(" FROM REQ_CARD_NOT_SALE rc INNER JOIN REQ_CARD_DETAIL_NOT_SALE rcd ON rc.REQ_ID = rcd.REQ_ID ");
        strQueryRequestList.append(" WHERE 1 = 1  AND rcd.status = 1 ");
      
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
            strQueryRequestList.append(" AND CREATE_REQ_DATE <= to_date(?,'dd/mm/yyyy') ");
            listParam.add(dateFormat.format(toDate));
        }
        
        if (serial != null && !serial.equals("")) {
            strQueryRequestList.append(" AND to_number(serial) >= ? ");
            listParam.add(Long.valueOf(serial));
        }
        if (toSerial != null && !toSerial.equals("")) {
            strQueryRequestList.append(" AND to_number(serial) <= ? ");
            listParam.add(Long.valueOf(toSerial));
        }
        
        strQueryRequestList.append(" ORDER BY CREATE_REQ_DATE ");
        
        Query queryRequestList = session.createSQLQuery(strQueryRequestList.toString()).
                    addScalar("reqId", Hibernate.LONG).
                    addScalar("reqDetailId", Hibernate.LONG).
                    addScalar("reqCode", Hibernate.STRING).
                    addScalar("staffCode", Hibernate.STRING).
                    addScalar("staffCreateCode", Hibernate.STRING).
                    addScalar("approveStaffCode", Hibernate.STRING).
                    addScalar("approveDate", Hibernate.DATE).
                    addScalar("serial", Hibernate.STRING).
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
        
        if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("UnlockUserCardOfBranch"), req)) {
            req.getSession().setAttribute("UnlockUserCardOfBranch", 1);
            ReasonDAO reasonDAO = new ReasonDAO();
            List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_UNLOCKUSER);
            req.setAttribute("listReasonUnLockUser", listReason);
        } else {
            req.getSession().setAttribute("UnlockUserCardOfBranch", 0);
            ReasonDAO reasonDAO = new ReasonDAO();
            List<Reason> listReason = reasonDAO.findReasonByTypeCode(REASON_TYPE_UNLOCKUSER, REASON_CODE);
            req.setAttribute("listReasonUnLockUser", listReason);
        }
        
        pageForward = APPROVE_REQ_UNLOCK_LOCK_USER;
        
        return pageForward;
    } 
    
    public String popupUnLockUser() throws Exception {
        log.info("Begin. popupUnLockUser");
        HttpServletRequest req = getRequest();
        String pageForward = "popupUnlockUser";
        ReasonDAO reasonDAO = new ReasonDAO();
        List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_UNLOCKUSER);
        req.setAttribute("listReasonUnLockUser", listReason);
        Long reqId = Long.parseLong(getRequest().getParameter("reqId"));
        reqCardNotSaleFrom.setReqId(reqId);

        return pageForward;
    }
    
    public String UnlockUserCardOfBranch() throws Exception {
        
        HttpServletRequest req = getRequest();
        String pageForward = APPROVE_REQ_UNLOCK_LOCK_USER;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //Long reqId = reqCardNotSaleFrom.getReqId();
        Long reasonId = reqCardNotSaleFrom.getReasonId();
        String reasonCode = "STOCK_OF_BRANCH";
        log.info("Begin unLockUser:");
        
//        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(reqCardNotSaleFrom.getServerFileName());
//        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
//        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
//        File inputFile = new File(serverFilePath);
//        String fileName = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
//        File uploadDirectory = new File(Constant.UPLOAD_DIR);//FileUtils.getAppsRealPath() +
//            if (!uploadDirectory.exists()) {
//                uploadDirectory.mkdirs();
//        }
//        FileUtils.saveFile(inputFile, serverFileName, uploadDirectory);
        ReasonDAO reason = new ReasonDAO();
        List<ReqCardNotSaleBean> listReqCardNotSaleBean = (ArrayList<ReqCardNotSaleBean>) req.getSession().getAttribute(SESSION_REQUEST_LIST);
        
        if (reason.findById(reasonId) != null && reason.findById(reasonId).getReasonCode().equals(reasonCode)) {
            
            if (reqCardNotSaleFrom.getSelectedStockCardLostIds() != null
                    && reqCardNotSaleFrom.getSelectedStockCardLostIds().length > 0) {
                for (String reqDetailIds : reqCardNotSaleFrom.getSelectedStockCardLostIds()) {
                    Long reqDetailId = Long.valueOf(reqDetailIds);
                    Long serial = 0L;
                    Long reqId = 0L;
                    Long staffId = 0L;
                    ReqCardDetailNotSale reqCardDetailNotSale = findById(reqDetailId);
                    if (reqCardDetailNotSale != null && reqCardDetailNotSale.getSerial() != null) {
                        serial = Long.valueOf(reqCardDetailNotSale.getSerial());
                    }
                    if (reqCardDetailNotSale != null && reqCardDetailNotSale.getReqId() != null) {
                        reqId = reqCardDetailNotSale.getReqId();
                    }
                    if (reqCardDetailNotSale != null && reqCardDetailNotSale.getLockStaffId() != null) {
                        staffId = reqCardDetailNotSale.getLockStaffId();
                    }
                    //Update trang thai bang khoa User
                    StringBuilder sqlAccept = new StringBuilder();
                    sqlAccept.append(" UPDATE ");
                    sqlAccept.append(" LOCK_USER_INFO  SET TRANS_STATUS = 10, LAST_CHECK = ?");
                    sqlAccept.append(" WHERE ");
                    sqlAccept.append(" TRANS_ID = ? AND LOCK_TYPE_ID = 15 AND Serial = ? ");
                    Query query = getSession().createSQLQuery(sqlAccept.toString());
                    query.setParameter(0, getSysdate());
                    query.setParameter(1, reqId);
                    query.setParameter(2, serial);

                    query.executeUpdate();
                    //Update trang thai bang chi tiet the cao
//                    StringBuilder sqlUpdateDetail = new StringBuilder();
//                    sqlUpdateDetail.append(" UPDATE ");
//                    sqlUpdateDetail.append(" REQ_CARD_DETAIL_NOT_SALE ");
//                    sqlUpdateDetail.append(" SET Status  = 3 ");
//                    sqlUpdateDetail.append(" WHERE ");
//                    sqlUpdateDetail.append(" Req_Detail_ID = ? ");
//                    Query queryUpdate = getSession().createSQLQuery(sqlUpdateDetail.toString());
//                    queryUpdate.setParameter(0, reqDetailId);
//                    queryUpdate.executeUpdate();
                    
                    //Insert MT SMS
                    String messgage = "You have 60 minutes to solve this serial {0}(export to staff stock and sell)";
                    StaffDAO staffDAO = new StaffDAO();
                    String isdnStaff = "";
                    String prefix = "258";
                    Staff staffLock = staffDAO.findById(staffId);
                    if (staffLock != null) {
                        isdnStaff = prefix + getISDNOfUserVSA(getSession(), staffLock.getStaffCode().toUpperCase());
                    }
                    StringBuilder sqlInsertMTForStaff = new StringBuilder();
                    messgage = messgage.replace("{0}", serial.toString());
                    sqlInsertMTForStaff.append("INSERT INTO MT (MT_ID, MO_HIS_ID, MSISDN, MESSAGE, RETRY_NUM, CHANNEL, RECEIVE_TIME)");
                    sqlInsertMTForStaff.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
                    Query queryInsertMTForStaff = getSession().createSQLQuery(sqlInsertMTForStaff.toString());
                    Long Id = getSequence("MT_SEQ");
                    queryInsertMTForStaff.setParameter(0, Id);
                    queryInsertMTForStaff.setParameter(1, Id);
                    queryInsertMTForStaff.setParameter(2, isdnStaff);
                    queryInsertMTForStaff.setParameter(3, messgage);
                    queryInsertMTForStaff.setParameter(4, 0);
                    queryInsertMTForStaff.setParameter(5, "86904");
                    queryInsertMTForStaff.setParameter(6, getSysdate());
                    queryInsertMTForStaff.executeUpdate();
                    
                    ActionLog actionLog = new ActionLog();
                    Long actionId = getSequence("ACTION_LOG_SEQ");
                    actionLog.setActionId(actionId);
                    actionLog.setActionDate(getSysdate());
                    actionLog.setActionType("UNLOCK_USER");
                    actionLog.setActionIp(getIpAddress());
                    actionLog.setActionUser(userToken.getLoginName());
                    actionLog.setDescription("UNLOCK USER STOCK OF BRANCH REQ_ID:" + reqDetailId);
                    actionLog.setObjectId(reqDetailId);
                    getSession().save(actionLog);
                    //Ghi log vao action log detail
                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                    actionLogDetail.setActionId(actionId);
                    actionLogDetail.setTableName("LOCK_USER_INFO");
                    actionLogDetail.setColumnName("TRANS_ID");
                    actionLogDetail.setOldValue(reqDetailId.toString());
                    actionLogDetail.setNewValue(reqDetailId.toString());
                    actionLogDetail.setActionDate(getSysdate());
                    getSession().save(actionLogDetail);
                    //refesh lai vung lam viec
                    if (listReqCardNotSaleBean != null && listReqCardNotSaleBean.size() > 0) {
                        ReqCardNotSaleBean reqCardNotSaleBean1 = null;
                        for (int i = 0; i < listReqCardNotSaleBean.size(); i++) {
                            reqCardNotSaleBean1 = listReqCardNotSaleBean.get(i);
                            if (reqDetailId.equals(reqCardNotSaleBean1.getStockCardLostId())) {
                                reqCardNotSaleBean1.setStatus(3L);
                                break;
                            }
                        }
                    }
                }
                
                req.setAttribute(REQUEST_MESSAGE, "approve.success");

                req.getSession().setAttribute(SESSION_REQUEST_LIST, listReqCardNotSaleBean);
            }
        } else {
            req.setAttribute(REQUEST_MESSAGE, "input.error.reason");
        }
        if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("UnlockUserCardOfBranch"), req)) {
            req.getSession().setAttribute("UnlockUserCardOfBranch", 1);
            ReasonDAO reasonDAO = new ReasonDAO();
            List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_UNLOCKUSER);
            req.setAttribute("listReasonUnLockUser", listReason);
        } else {
            req.getSession().setAttribute("UnlockUserCardOfBranch", 0);
            ReasonDAO reasonDAO = new ReasonDAO();
            List<Reason> listReason = reasonDAO.findReasonByTypeCode(REASON_TYPE_UNLOCKUSER, REASON_CODE);
            req.setAttribute("listReasonUnLockUser", listReason);
        }
        log.info("End unLockUser");
        
        return pageForward;
    }
    
    public String unLockUser() throws Exception {
            
        HttpServletRequest req = getRequest();
        String pageForward = APPROVE_REQ_UNLOCK_LOCK_USER;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //Long reqId = reqCardNotSaleFrom.getReqId();
        Long reasonId = reqCardNotSaleFrom.getReasonId();
        String reasonCode = "UNLOCK_USER";
        log.info("Begin unLockUser:");
        
        ReasonDAO reason = new ReasonDAO();
        List<ReqCardNotSaleBean> listReqCardNotSaleBean = (ArrayList<ReqCardNotSaleBean>) req.getSession().getAttribute(SESSION_REQUEST_LIST);
        
        if (reason.findById(reasonId) != null && reason.findById(reasonId).getReasonCode().equals(reasonCode)) {
            String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(reqCardNotSaleFrom.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File inputFile = new File(serverFilePath);
            String fileName = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File uploadDirectory = new File(Constant.UPLOAD_DIR);//FileUtils.getAppsRealPath() +
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            FileUtils.saveFile(inputFile, serverFileName, uploadDirectory);
            if (reqCardNotSaleFrom.getSelectedStockCardLostIds() != null
                    && reqCardNotSaleFrom.getSelectedStockCardLostIds().length > 0) {
                for (String reqDetailIds : reqCardNotSaleFrom.getSelectedStockCardLostIds()) {
                    Long reqDetailId = Long.valueOf(reqDetailIds);
                    Long serial = 0L;
                    Long reqId = 0L;
                    String req_Code = "";
                    ReqCardDetailNotSale reqCardDetailNotSale = findById(reqDetailId);
                    if (reqCardDetailNotSale != null && reqCardDetailNotSale.getSerial() != null) {
                        serial = Long.valueOf(reqCardDetailNotSale.getSerial());
                    }
                    if (reqCardDetailNotSale != null && reqCardDetailNotSale.getReqId() != null) {
                        reqId = reqCardDetailNotSale.getReqId();
                    }
                    if (reqCardDetailNotSale != null && reqCardDetailNotSale.getReqNo() != null) {
                        req_Code = reqCardDetailNotSale.getReqNo();
                    }
                    //Delete ban ghi bang khoa User
                    StringBuilder sqlAccept = new StringBuilder();
                    sqlAccept.append(" DELETE ");
                    sqlAccept.append(" LOCK_USER_INFO ");
                    sqlAccept.append(" WHERE ");
                    sqlAccept.append(" TRANS_ID = ? AND Serial = ? AND LOCK_TYPE_ID = 15 ");
                    Query query = getSession().createSQLQuery(sqlAccept.toString());
                    query.setParameter(0, reqId);
                    query.setParameter(1, serial);
                    query.executeUpdate();

                    //Update trang thai bang chi tiet the cao
                    StringBuilder sqlUpdateDetail = new StringBuilder();
                    sqlUpdateDetail.append(" UPDATE ");
                    sqlUpdateDetail.append(" REQ_CARD_DETAIL_NOT_SALE ");
                    sqlUpdateDetail.append(" SET Status  = 3 ");
                    sqlUpdateDetail.append(" WHERE ");
                    sqlUpdateDetail.append(" Serial = ? ");
                    Query queryUpdate = getSession().createSQLQuery(sqlUpdateDetail.toString());
                    queryUpdate.setParameter(0, serial);
                    queryUpdate.executeUpdate();
                    
                    //Update trang thai bang the cao chua xuat ban
                    StringBuilder sqlUpdate = new StringBuilder();
                    sqlUpdate.append(" UPDATE ");
                    sqlUpdate.append(" REQ_CARD_NOT_SALE ");
                    sqlUpdate.append(" SET Req_Code  = ? ");
                    sqlUpdate.append(" WHERE ");
                    sqlUpdate.append(" Req_Id = ? ");
                    Query queryUpdateCard = getSession().createSQLQuery(sqlUpdate.toString());
                    queryUpdateCard.setParameter(0, req_Code +"_"+ "WRONG");
                    queryUpdateCard.setParameter(1, reqId);
                    queryUpdateCard.executeUpdate();
                    
                    ActionLog actionLog = new ActionLog();
                    Long actionId = getSequence("ACTION_LOG_SEQ");
                    actionLog.setActionId(actionId);
                    actionLog.setActionDate(getSysdate());
                    actionLog.setActionType("UNLOCK_USER");
                    actionLog.setActionIp(getIpAddress());
                    actionLog.setActionUser(userToken.getLoginName());
                    actionLog.setDescription("UNLOCK USER REQ_ID:" + reqDetailId);
                    actionLog.setObjectId(reqDetailId);
                    getSession().save(actionLog);
                    //Ghi log vao action log detail
                    ActionLogDetail actionLogDetail = new ActionLogDetail();
                    actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
                    actionLogDetail.setActionId(actionId);
                    actionLogDetail.setTableName("LOCK_USER_INFO");
                    actionLogDetail.setColumnName("TRANS_ID");
                    actionLogDetail.setOldValue(reqDetailId.toString());
                    actionLogDetail.setNewValue(reqDetailId.toString());
                    actionLogDetail.setActionDate(getSysdate());
                    getSession().save(actionLogDetail);
                    //refesh lai vung lam viec
                    if (listReqCardNotSaleBean != null && listReqCardNotSaleBean.size() > 0) {
                        ReqCardNotSaleBean reqCardNotSaleBean1 = null;
                        for (int i = 0; i < listReqCardNotSaleBean.size(); i++) {
                            reqCardNotSaleBean1 = listReqCardNotSaleBean.get(i);
                            if (reqDetailId.equals(reqCardNotSaleBean1.getReqDetailId())) {
                                reqCardNotSaleBean1.setStatus(3L);
                                break;
                            }
                        }
                    }
                }
                
                req.setAttribute(REQUEST_MESSAGE, "approve.success");

                req.getSession().setAttribute(SESSION_REQUEST_LIST, listReqCardNotSaleBean);
            }
        } else {
            req.setAttribute(REQUEST_MESSAGE, "input.error.reason");
        }
         if (AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource("UnlockUserCardOfBranch"), req)) {
            req.getSession().setAttribute("UnlockUserCardOfBranch", 1);
            ReasonDAO reasonDAO = new ReasonDAO();
            List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_UNLOCKUSER);
            req.setAttribute("listReasonUnLockUser", listReason);
        } else {
            req.getSession().setAttribute("UnlockUserCardOfBranch", 0);
            ReasonDAO reasonDAO = new ReasonDAO();
            List<Reason> listReason = reasonDAO.findReasonByTypeCode(REASON_TYPE_UNLOCKUSER, REASON_CODE);
            req.setAttribute("listReasonUnLockUser", listReason);
        }
        
        log.info("End unLockUser");
        
        return pageForward;
    }
    
    public List<ImSearchBean> getListStaffCode(ImSearchBean imSearchBean) {
        try {

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
    
    public ReqCardDetailNotSale findById(java.lang.Long id) {
        log.debug("getting ReqCardDetailNotSale instance with ReqCardDetailNotSaleId: " + id);
        try {
            ReqCardDetailNotSale instance = (ReqCardDetailNotSale) getSession().get("com.viettel.im.database.BO.ReqCardDetailNotSale", id);
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
