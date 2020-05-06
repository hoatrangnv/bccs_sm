/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ReqCardNotSaleBean;
import com.viettel.im.client.bean.ReqCardNotSaleErrorBean;
import com.viettel.im.client.form.QuotasAssignedSingleForm;
import com.viettel.im.client.form.ReqCardNotSaleFrom;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.FileUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.ValidateUtils;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockCard;
import com.viettel.im.database.BO.StockCardLost;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class ImportStockCardLostDAO extends BaseDAOAction{
    
    private static final Logger log = Logger.getLogger(ImportQuotasUnitUnderTheFileDAO.class);
    private String pageForward;
    private final String IMP_STOCK_CARD_LOST = "importStockCardLost";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    private final String REASON_TYPE_LOSTCARD = "LOST_CARD";
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
            ReasonDAO reasonDAO = new ReasonDAO();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            reqCardNotSaleFrom.setUserCodeCreate(vsaUserToken.getUserName().toUpperCase());
            List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_LOSTCARD);
            req.setAttribute("listReasonLostCard", listReason);
            pageForward = IMP_STOCK_CARD_LOST;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }

    public String importCardLost() throws Exception {
        log.info("Begin method importFile of ImportCardNotSaleFileDAO ...");
        HttpServletRequest req = getRequest();
        //UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //Session imSession = this.getSession();
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        pageForward = IMP_STOCK_CARD_LOST;
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(reqCardNotSaleFrom.getServerFileName());
        String serFileNameAccept = com.viettel.security.util.StringEscapeUtils.getSafeFileName(reqCardNotSaleFrom.getServerFileNameApp());
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        String serFileAcceptPath = req.getSession().getServletContext().getRealPath(tempPath + serFileNameAccept);
        File impFile = new File(serverFilePath);
        File impFileAccept = new File(serFileAcceptPath);
        List<ReqCardNotSaleErrorBean> listError = new ArrayList<ReqCardNotSaleErrorBean>();
        List<ReqCardNotSaleBean> listcaCardNotSale = new ArrayList<ReqCardNotSaleBean>();
        List<Staff> listStaff = new ArrayList<Staff>();
        HashMap<String, Long> listReq = new HashMap<String, Long>();
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 3);
        if (list == null) {
            return pageForward;
        }
        Long stockCardLostId = 0L;
        Long ownerId = 0L;
        Long ownerType = 0L;
        Long stockModelId = 0L;
        
        File uploadDirectory = new File(Constant.UPLOAD_DIR);//FileUtils.getAppsRealPath() +
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
        }
        FileUtils.saveFile(impFileAccept, serFileNameAccept, uploadDirectory);
        
        for (int i = 0; i < list.size(); i++) {
            Object[] object = (Object[]) list.get(i);
            String serial = object[0].toString().trim();
            String lostDateString = object[1].toString().trim();
            String error = "";
           
            Date lostDate = null;
            if (lostDateString != null) {
                try {
                    lostDate = DateTimeUtils.convertStringToDateTimeVunt(lostDateString);
                    if (!checkValidDate(lostDate)) {
                        error += ";" + getText("ERR.CHL.086");
                    }
                } catch (Exception ex) {
//                error = "Ð?nh d?ng ngày  không chính xác";
                    error += ";" + getText("ERR.CHL.086");
                }
            }
            
            if (serial == null || serial.trim().equals("")) {
                error += ";" + getText("Error. SERIAL IS NOT NULL!");
            } else {
                StockCardDAO stockCard = new StockCardDAO();
                List<StockCard> listStockCard = new ArrayList<StockCard>();
               
                listStockCard = getInfoStockCardBySerial(Long.valueOf(serial));
                if (stockCard.checkSerialLost(Long.valueOf(serial))) {
                    error += ";" + getText("Error. SERIAL IS Exist In ListCardLost!");
                }
                
                if (listStockCard.size() <= 0) {
                    error += ";" + getText("Error. SERIAL IS NOT Exist OR Sale(Status != 1)!");
                } else {
                    ownerId = listStockCard.get(0).getOwnerId();
                    ownerType = listStockCard.get(0).getOwnerType();
                    stockModelId = listStockCard.get(0).getStockModelId();
                }
            }
            if (!error.equals("")) {
                ReqCardNotSaleErrorBean errorBean = new ReqCardNotSaleErrorBean();
                errorBean.setSerial(serial);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                
                StockCardLost stockCardLost = new StockCardLost();
                stockCardLostId = getSequence("STOCK_CARD_LOST_SEQ");
                stockCardLost.setStockCardLostId(stockCardLostId);
                stockCardLost.setSerial(serial);
                stockCardLost.setOwnerId(ownerId);
                stockCardLost.setOwnerType(ownerType.toString());
                stockCardLost.setStockModelId(stockModelId);
                stockCardLost.setReasonId(reqCardNotSaleFrom.getReasonId());
                stockCardLost.setCreateDate(getSysdate());
                stockCardLost.setLostDate(lostDate);
                stockCardLost.setStatus(0L);
                stockCardLost.setFilePath(serFileAcceptPath);
                //stockCardLost.setFileApprovePath(uploadDirectory.toString() + fileName);
                getSession().save(stockCardLost);
                
                //Danh sach thanh cong
                ReqCardNotSaleErrorBean errorBean = new ReqCardNotSaleErrorBean();
                errorBean.setSerial(serial);
                errorBean.setError("Success");
                listError.add(errorBean);
            } 
        }
        //Hien thi toan bo
        downloadFile("errorImportCardLostFile", listError);
        ReasonDAO reasonDAO = new ReasonDAO();
        List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_LOSTCARD);
        req.setAttribute("listReasonLostCard", listReason);
        reqCardNotSaleFrom.setClientFileName(null);
        reqCardNotSaleFrom.setServerFileName(null);
        reqCardNotSaleFrom.setReasonId(null);
        
        return pageForward;
    }
    
    
    public String inputRange() throws Exception {
        log.info("Begin method inputRange of ImportCardNotSaleFileDAO ...");
        HttpServletRequest req = getRequest();
        //UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        //Session imSession = this.getSession();
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        pageForward = IMP_STOCK_CARD_LOST;
        //String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(reqCardNotSaleFrom.getServerFileName());
        String serFileNameAccept = com.viettel.security.util.StringEscapeUtils.getSafeFileName(reqCardNotSaleFrom.getServerFileNameApp());
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serFileAcceptPath = req.getSession().getServletContext().getRealPath(tempPath + serFileNameAccept);
        
        File impFileAccept = new File(serFileAcceptPath);
        List<ReqCardNotSaleErrorBean> listError = new ArrayList<ReqCardNotSaleErrorBean>();
        List<ReqCardNotSaleBean> listcaCardNotSale = new ArrayList<ReqCardNotSaleBean>();
        
        File uploadDirectory = new File(Constant.UPLOAD_DIR);//FileUtils.getAppsRealPath() +
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
        }
        FileUtils.saveFile(impFileAccept, serFileNameAccept, uploadDirectory);
        Long stockCardLostId = 0L;
        Long ownerId = 0L;
        Long ownerType = 0L;
        Long stockModelId = 0L;
        Long fromSerial = 0L;
        Long toSerial = 0L;
        String fromSerialStr = reqCardNotSaleFrom.getSerial();
        String toSerialStr = reqCardNotSaleFrom.getToSerial();
        if (fromSerialStr != null) {
            fromSerial = Long.valueOf(fromSerialStr);
        }
        if (toSerialStr != null) {
            toSerial = Long.valueOf(toSerialStr);
        }
        
        if (fromSerial.compareTo(toSerial) > 0 ) {
            
        } else {
            for (Long i = fromSerial; i <= toSerial; i ++) {
                StockCardDAO stockCard = new StockCardDAO();
                List<StockCard> listStockCard = new ArrayList<StockCard>();
                listStockCard = getInfoStockCardBySerial(i);
                String error = "";
                if (listStockCard.size() <= 0) {
                    error += ";" + getText("Error. SERIAL IS NOT Exist OR Sale(Status != 1)!");
                } else {
                    ownerId = listStockCard.get(0).getOwnerId();
                    ownerType = listStockCard.get(0).getOwnerType();
                    stockModelId = listStockCard.get(0).getStockModelId();
                }
                if (stockCard.checkSerialLost(i)) {
                    error += ";" + getText("Error. SERIAL IS Exist In ListCardLost!");
                }
                if (!error.equals("")) {
                    ReqCardNotSaleErrorBean errorBean = new ReqCardNotSaleErrorBean();
                    errorBean.setSerial(i.toString());
                    errorBean.setError(error);
                    listError.add(errorBean);
                } else {
                    StockCardLost stockCardLost = new StockCardLost();
                    stockCardLostId = getSequence("STOCK_CARD_LOST_SEQ");
                    stockCardLost.setStockCardLostId(stockCardLostId);
                    stockCardLost.setSerial(i.toString());
                    stockCardLost.setOwnerId(ownerId);
                    stockCardLost.setOwnerType(ownerType.toString());
                    stockCardLost.setStockModelId(stockModelId);
                    stockCardLost.setReasonId(reqCardNotSaleFrom.getReasonId());
                    stockCardLost.setCreateDate(getSysdate());
                    stockCardLost.setLostDate(getSysdate());
                    stockCardLost.setStatus(0L);
                    stockCardLost.setFilePath(serFileAcceptPath);
                    //stockCardLost.setFileApprovePath(uploadDirectory.toString() + fileName);
                    getSession().save(stockCardLost);

                    //Update the cao ve da mat cap
                    StringBuilder sqlAccept = new StringBuilder();
                    sqlAccept.append(" UPDATE ");
                    sqlAccept.append(" REQ_CARD_DETAIL_NOT_SALE ");
                    sqlAccept.append(" SET Status  = 2 ");
                    sqlAccept.append(" WHERE ");
                    sqlAccept.append(" SERIAL = ? ");
                    Query query = getSession().createSQLQuery(sqlAccept.toString());
                    query.setParameter(0, i);
                    query.executeUpdate();

                    //Danh sach thanh cong
                    ReqCardNotSaleErrorBean errorBean = new ReqCardNotSaleErrorBean();
                    errorBean.setSerial(i.toString());
                    errorBean.setError("Success");
                    listError.add(errorBean);
                }
            }
            //Hien thi toan bo
            downloadFile("errorImportCardLostFile", listError);
            ReasonDAO reasonDAO = new ReasonDAO();
            List<Reason> listReason = reasonDAO.findAllReasonByType(REASON_TYPE_LOSTCARD);
            req.setAttribute("listReasonLostCard", listReason);
            reqCardNotSaleFrom.setClientFileName(null);
            reqCardNotSaleFrom.setServerFileName(null);
            reqCardNotSaleFrom.setReasonId(null);
        }
        
        return pageForward;
    }

    public String checkValidate(QuotasAssignedSingleForm tmp) {
        try {
            String returnMsg = "";
//            if (tmp.getImportType() == null) {
//                returnMsg = "ERR.TT.24"; //L?i, b?n chua ch?n lo?i import!
//                return returnMsg;
//            }
            if (tmp.getServerFileName() == null || tmp.getServerFileName().trim().equals("")) {
                returnMsg = "ERR.GOD.067"; //L?i, b?n chua nhap file dau vao!
                return returnMsg;
            }
            return returnMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private ReqCardNotSaleFrom getDataListfromFile(ReqCardNotSaleFrom tmp) {
        reqCardNotSaleFrom = tmp;
        try {
            
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
        }
        return reqCardNotSaleFrom;
    }
    
     //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
        //req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "N?u h? th?ng không t? download. Click vào dây d? t?i File luu thông tin không c?p nh?t du?c");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }
    /*
    public List getInfoStockCardBySerial(Long serial) {
        
        List<StockCard> listStockCard = new ArrayList<StockCard>();
        
        try {
            String queryString = " from StockCard WHERE serial = ? " +
                                 " AND status = 1 and stateId = 1 ";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, serial);
            listStockCard = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
        
        return  listStockCard;
    }
    */
    public List getInfoStockCardBySerial(Long serial) {
        
        List<StockCard> listStockCard = new ArrayList<StockCard>();
        
        try {
            String queryString = " SELECT Owner_Type AS ownerType, Owner_Id AS ownerId, Stock_Model_Id AS stockModelId, Status AS status "
                    + " FROM Stock_Card WHERE serial = ? and state_Id = 1";

            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("ownerType", Hibernate.LONG)
                    .addScalar("ownerId", Hibernate.LONG)
                    .addScalar("stockModelId", Hibernate.LONG)
                    .addScalar("status", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(StockCard.class));
            queryObject.setParameter(0, serial);
            listStockCard = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
        
        return  listStockCard;
    }
    
     private boolean checkValidDate(Date birthDate) {
        if (birthDate == null) {
            return false;
        }
        Date currentDate = new Date();
        if (birthDate.after(currentDate)) {
            return false;
        }
        
        return true;
    }
}
