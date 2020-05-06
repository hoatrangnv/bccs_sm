/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.anypay.util.DataUtil;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.ShowMessage;
import com.viettel.im.database.BO.ExportCheckSerial;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.security.util.DbProcessor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Vu Ngoc Ha
 */
public class BaseStockDAO extends BaseDAOAction {
//
//    /* LamNV ADD START */
//    private Session session;
//
//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return BaseHibernateDAO.getSession();
//        }
//        return this.session;
//    }
//    /* LamNV ADD STOP */
//    

//    protected static Log log = LogFactory.getLog(BaseStockDAO.class);
    Logger log = Logger.getLogger(BaseStockDAO.class);
    protected String pageForward = "";
    protected static Thread importThread = null;
    public String realPath = "";
    public Long shopId = null;
    public Long staffId = null;
    public static String NAME_TYPE_HIBERNATE = "hibernate";
    public static String NAME_TYPE_NORMAL = "normal";

    public BaseStockDAO() {
        //Them ghi Log4j
        String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");
        PropertyConfigurator.configure(log4JPath);
    }


    /* Modify by ThanhNC
     * Purposes:    Get ten bang tuong ung bang stockModelId
     * Param:       stockModelId ma kieu kho
     *              nameType + normal: Tra ve ten bang vat ly
     *                       + hibernate: Tra ve ten kieu hibernate (Object)
     */
    public String getTableNameByStockModel(Long stockModelId, String nameType) {
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return null;
        }
        return getTableNameByStockType(stockModel.getStockTypeId(), nameType);
    }
    /* Modify by ThanhNC
     * Purposes:    Get ten bang tuong ung bang stockType
     * Param:       stockType ma kieu kho
     *              nameType + normal: Tra ve ten bang vat ly
     *                       + hibernate: Tra ve ten kieu hibernate (Object)
     */

    public String getTableNameByStockType(Long stockType, String nameType) {
        log.debug("Start getTableNameByStockTypeId .. ");
        try {
            String stockModelNameNormal = "";
            String stockModelNameHibernate = "";
            if (stockType.equals(Constant.STOCK_CARD)) {
                stockModelNameHibernate = "StockCard";
                stockModelNameNormal = "STOCK_CARD";
            }

            if (stockType.equals(Constant.STOCK_HANDSET)) {
                stockModelNameHibernate = "StockHandset";
                stockModelNameNormal = "STOCK_HANDSET";
            }

            if (stockType.equals(Constant.STOCK_ISDN_HOMEPHONE)) {
                stockModelNameHibernate = "StockIsdnHomephone";
                stockModelNameNormal = "STOCK_ISDN_HOMEPHONE";
            }

            if (stockType.equals(Constant.STOCK_ISDN_MOBILE)) {
                stockModelNameHibernate = "StockIsdnMobile";
                stockModelNameNormal = "STOCK_ISDN_MOBILE";
            }

            if (stockType.equals(Constant.STOCK_ISDN_PSTN)) {
                stockModelNameHibernate = "StockIsdnPstn";
                stockModelNameNormal = "STOCK_ISDN_PSTN";
            }

            if (stockType.equals(Constant.STOCK_KIT)) {
                stockModelNameHibernate = "StockKit";
                stockModelNameNormal = "STOCK_KIT";
            }

            if (stockType.equals(Constant.STOCK_SIM_POST_PAID)) {
                stockModelNameHibernate = "StockSimPostPaid";
                stockModelNameNormal = "STOCK_SIM";
            }

            if (stockType.equals(Constant.STOCK_SIM_PRE_PAID)) {
                stockModelNameHibernate = "StockSimPrePaid";
                stockModelNameNormal = "STOCK_SIM";
            }

            if (stockType.equals(Constant.STOCK_SUMO)) {
                stockModelNameHibernate = "StockSumo";
                stockModelNameNormal = "STOCK_SUMO";
            }
            if (stockType.equals(Constant.STOCK_ACCESSORIES)) {
                stockModelNameHibernate = "StockAccessories";
                stockModelNameNormal = "STOCK_ACCESSORIES";
            }

            if (stockType.equals(Constant.STOCK_LAPTOP)) {
                stockModelNameHibernate = "StockHandset";
                stockModelNameNormal = "STOCK_HANDSET";
            }
            if (stockModelNameHibernate.equals("")) {
                stockModelNameHibernate = "StockSerial";
                stockModelNameNormal = "STOCK_SERIAL";
            }

            if (NAME_TYPE_NORMAL.equals(nameType)) {
                return stockModelNameNormal;
            }

            return stockModelNameHibernate;

        } catch (RuntimeException re) {
            log.error("getTableNameByStockTypeId failed", re);
            throw re;
        }
    }

    /* Modify by ThanhNC
     * Purposes: Tim kiem hang hoa trong kho bang cac property cua hang hoa
     */
    public List findInShopByProperty(Object stockTypeId, HashMap<String, Object> property) {
        log.debug("finding values in Stock with some property");

        try {
            Long stockType = Long.parseLong(stockTypeId.toString());
            String stockModelName = getTableNameByStockType(stockType, NAME_TYPE_HIBERNATE);

            if ("".equals(stockModelName)) {
                log.error("Stock model id = " + stockType.toString() + " not found!!");
                return null;
            }
            List lstParam = new ArrayList();
            StringBuffer queryString = new StringBuffer("from " + stockModelName + " where 1=1 ");
            Set set = property.entrySet();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                Map.Entry me = (Map.Entry) iter.next();
                queryString.append(" and " + (String) me.getKey() + " = ? ");
                lstParam.add(me.getValue());
            }

            Query queryObject = getSession().createQuery(queryString.toString());
            for (int i = 0; i < lstParam.size(); i++) {
                queryObject.setParameter(i, lstParam.get(i));
            }
            return queryObject.list();

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /* Modify by ThanhNC
     * Purposes:    Lay thong tin stock bang ownerType va OnwerId
     * Param:       ownerType ma kieu kho
     *              ownerId: Id kho
     */
    public Object getStockByTypeAndId(Long ownerType, Long ownerId) {
        log.debug("Start getStockByTypeAndId .. ");
        try {
            if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                return shopDAO.findById(ownerId);
            }
            if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                return staffDAO.findById(ownerId);
            }
            return null;

        } catch (RuntimeException re) {
            log.error("getTableNameByStockTypeId failed", re);
            throw re;
        }
    }

    /* Modify by ThanhNC
     * Purposes: Tim kiem ra 1 loai hang hoa khi nhap vao loai kho, serial, va ma kho
     */
    public Object findInShopBySerialAndModel(Object stockTypeId, Object stockModelId, Object serial, Object shopType, Object shopId) {
        log.debug("start findInShopBySerialAndModel ..");
        try {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("stockModelId", stockModelId);
            hash.put("to_number(serial)", serial);
            hash.put("ownerType", shopType);
            hash.put("ownerId", shopId);
            //hash.put("status", Constant.STATUS_USE);
            List lstResult = findInShopByProperty(stockTypeId, hash);
            if (lstResult.size() > 0) {
                return lstResult.get(0);
            }
        } catch (RuntimeException re) {
            log.error("findInShopBySerialAndModel error", re);
            throw re;
        }
        return null;
    }

    public Object findInShopBySerialAndModel(Object stockTypeId, Object stockModelId, Object serial, Object shopType, Object shopId, Long status) {
        log.debug("start findInShopBySerialAndModel ..");
        try {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("stockModelId", stockModelId);
            hash.put("to_number(serial)", serial);
            hash.put("ownerType", shopType);
            hash.put("ownerId", shopId);
            hash.put("status", status);
            List lstResult = findInShopByProperty(stockTypeId, hash);
            if (lstResult.size() > 0) {
                return lstResult.get(0);
            }
        } catch (RuntimeException re) {
            log.error("findInShopBySerialAndModel error", re);
            throw re;
        }
        return null;
    }
    /* Modify by ThanhNC
     * Purposes: Tim kiem ra 1 loai hang hoa khi nhap vao loai kho, serial, va ma kho
     */

    public Object findBySerialAndModel(Object stockTypeId, Object stockModelId, Object serial) {
        log.debug("start findInShopBySerialAndModel ..");
        try {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("stockModelId", stockModelId);
            hash.put("to_number(serial)", serial);
            //hash.put("status", Constant.STATUS_USE);
            List lstResult = findInShopByProperty(stockTypeId, hash);
            if (lstResult.size() > 0) {
                return lstResult.get(0);
            }
        } catch (RuntimeException re) {
            log.error("findInShopBySerialAndModel error", re);
            throw re;
        }
        return null;
    }

    public Object findBySerialAndModel(Object stockTypeId, Object stockModelId, Object serial, Long status) {
        log.debug("start findInShopBySerialAndModel ..");
        try {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("stockModelId", stockModelId);
            hash.put("to_number(serial)", serial);
            hash.put("status", status);
            List lstResult = findInShopByProperty(stockTypeId, hash);
            if (lstResult.size() > 0) {
                return lstResult.get(0);
            }
        } catch (RuntimeException re) {
            log.error("findInShopBySerialAndModel error", re);
            throw re;
        }
        return null;
    }

    /*
     * Author: ThanhNC
     * Date created: 17/03/2009
     * Purpose: Cap nhap trang thai mat hang (theo serial) trong kho
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              fromOwnerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              fromOwnerId: Ma kho xuat
     *              status: trang thai moi
     *
     *              TrongLV
     *              2011/04/09
     *              Gan kenh cho serial hang hoa
     */
    public boolean updateSeialExp(List lstSerial, Long stockModelId, Long fromOwnerType,
            Long fromOwnerId, Long oldStatus, Long newStatus, Long quantity, Long channelTypeId) throws Exception {
        boolean noError = true;
        HttpServletRequest req = getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {

            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(stockModelId));
                lstError.add(new ShowMessage("stock.error.stockModelId.notFound", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (lstSerial == null || lstSerial.isEmpty()) {
                return true;
            }
            stockTypeId = stockModel.getStockTypeId();

            // Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;

            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
//            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? where stock_model_id = ? "
//                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? "; //Huynq13 2017/04/03 ignore
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? where  "
                    + " owner_id = ? and  serial >= ? and serial <= ? and stock_model_id = ? and status = ? "; //Huynq13 2017/04/03 add
            if (channelTypeId != null) {
                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? "
                        + " , channel_Type_Id = ? "
                        + " where stock_model_id = ? "
                        + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";

                //Add by hieptd
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());
                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    query.setParameter(0, newStatus);
                    query.setParameter(1, channelTypeId);
                    query.setParameter(2, stockModelId);
                    query.setParameter(3, fromOwnerType);
                    query.setParameter(4, fromOwnerId);
                    query.setParameter(5, fromSerial);
                    query.setParameter(6, toSerial);
                    query.setParameter(7, oldStatus);
                    count = query.executeUpdate();
                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        List listParams = new ArrayList<String>();
                        listParams.add(String.valueOf(fromSerial));
                        listParams.add(String.valueOf(toSerial));
                        listParams.add(String.valueOf(oldStatus));
                        listParams.add(String.valueOf(count));
                        listParams.add(String.valueOf(fromOwnerType));
                        listParams.add(String.valueOf(fromOwnerId));
                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                        req.setAttribute("lstError", lstError);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;
                    }
                    total += count;
                }
            } else {


                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());
                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    query.setParameter(0, newStatus);
//                    query.setParameter(1, stockModelId);
//                    query.setParameter(2, fromOwnerType);
                    query.setParameter(1, fromOwnerId);
                    query.setParameter(2, fromSerial);
                    query.setParameter(3, toSerial);
                    query.setParameter(4, stockModelId);
                    query.setParameter(5, oldStatus);
                    count = query.executeUpdate();
                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        List listParams = new ArrayList<String>();
                        listParams.add(String.valueOf(fromSerial));
                        listParams.add(String.valueOf(toSerial));
                        listParams.add(String.valueOf(oldStatus));
                        listParams.add(String.valueOf(count));
                        listParams.add(String.valueOf(fromOwnerType));
                        listParams.add(String.valueOf(fromOwnerId));
                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                        req.setAttribute("lstError", lstError);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;
                    }
                    total += count;
                }
            }
            //Check so luong serial update duoc khong bang tong so luong hang xuat di
            if (total != quantity.intValue()) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(count));
                listParams.add(String.valueOf(quantity));
                lstError.add(new ShowMessage("stock.error.updateSerial.all", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }
        return noError;
    }

    public boolean updateSeialExpNewWay(List lstSerial, Long stockModelId, Long fromOwnerType,
            Long fromOwnerId, Long oldStatus, Long newStatus, Long quantity, Long channelTypeId, String userLogin, Long stockTransId) throws Exception {
        boolean noError = true;
        HttpServletRequest req = getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {

            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(stockModelId));
                lstError.add(new ShowMessage("stock.error.stockModelId.notFound", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (lstSerial == null || lstSerial.isEmpty()) {
                return true;
            }
            stockTypeId = stockModel.getStockTypeId();

            // Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;

            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            List<ExportCheckSerial> listExportCheck = new ArrayList<ExportCheckSerial>();
//Huynq13 20170407 start ignore to use new way export            
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
//            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? where stock_model_id = ? "
//                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? "; //Huynq13 2017/04/03 ignore
//                        String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? where  "
//                    + " owner_id = ? and  serial >= ? and serial <= ? and stock_model_id = ? and status = ? "; //Huynq13 2017/04/03 add
//Huynq13 20170407 end ignore to use new way export
            if (channelTypeId != null) {
//Huynq13 20170407 start ignore to use new way export                
//                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? "
//                        + " , channel_Type_Id = ? "
//                        + " where stock_model_id = ? "
//                        + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
//Huynq13 20170407 end ignore to use new way export
                //Add by hieptd
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());
//Huynq13 20170407 start ignore to use new way export                    
//                    Query query = getSession().createSQLQuery(SQLUPDATE);
//                    query.setParameter(0, newStatus);
//                    query.setParameter(1, channelTypeId);
//                    query.setParameter(2, stockModelId);
//                    query.setParameter(3, fromOwnerType);
//                    query.setParameter(4, fromOwnerId);
//                    query.setParameter(5, fromSerial);
//                    query.setParameter(6, toSerial);
//                    query.setParameter(7, oldStatus);
//                    count = query.executeUpdate();
//                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
//                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
//                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
//                        List listParams = new ArrayList<String>();
//                        listParams.add(String.valueOf(fromSerial));
//                        listParams.add(String.valueOf(toSerial));
//                        listParams.add(String.valueOf(oldStatus));
//                        listParams.add(String.valueOf(count));
//                        listParams.add(String.valueOf(fromOwnerType));
//                        listParams.add(String.valueOf(fromOwnerId));
//                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
//                        req.setAttribute("lstError", lstError);
//                        getSession().clear();
//                        getSession().getTransaction().rollback();
//                        getSession().beginTransaction();
//                        return false;
//                    }
//Huynq13 20170407 end ignore to use new way export       
//Huynq13 20170406 start add to use new way validate serial                                             
                    ExportCheckSerial exportCheck = new ExportCheckSerial();
                    exportCheck.setCreateUser(userLogin);
                    exportCheck.setFromSerial(fromSerial + "");
                    exportCheck.setToSerial(toSerial + "");
                    exportCheck.setOwnerId(fromOwnerId);
                    exportCheck.setStockModelId(stockModelId);
                    exportCheck.setOldStatus(oldStatus);
                    exportCheck.setNewStatus(newStatus);
                    exportCheck.setStockTransId(stockTransId);
                    exportCheck.setChannelTypeId(channelTypeId);
                    exportCheck.setTableName(tableName);
                    exportCheck.setFromOwnerTypeId(fromOwnerType);
                    listExportCheck.add(exportCheck);
//Huynq13 20170406 end add to use new way validate serial                                             
                    total += count;
                }
//Huynq13 20170406 start add to use new way validate serial                         
                DbProcessor db = new DbProcessor();
                int[] resultInsert = db.insertExportSerialChannelId(listExportCheck, userLogin, stockTransId);
                if (resultInsert == null) {
                    List listParams = new ArrayList<String>();
                    listParams.add("can not insert ExportSerial");
                    lstError.add(new ShowMessage("stock.error.exception", listParams));
                    req.setAttribute("lstError", lstError);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return false;
                }
//Huynq13 20170406 end add to use new way validate serial  
            } else {


                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());
//Huynq13 20170407 start ignore to use new way export                        
//                    Query query = getSession().createSQLQuery(SQLUPDATE);
//                    query.setParameter(0, newStatus);
////                    query.setParameter(1, stockModelId);
////                    query.setParameter(2, fromOwnerType);
//                    query.setParameter(1, fromOwnerId);
//                    query.setParameter(2, fromSerial);
//                    query.setParameter(3, toSerial);
//                    query.setParameter(4, stockModelId);
//                    query.setParameter(5, oldStatus);
//                    count = query.executeUpdate();
//                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
//                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
//                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
//                        List listParams = new ArrayList<String>();
//                        listParams.add(String.valueOf(fromSerial));
//                        listParams.add(String.valueOf(toSerial));
//                        listParams.add(String.valueOf(oldStatus));
//                        listParams.add(String.valueOf(count));
//                        listParams.add(String.valueOf(fromOwnerType));
//                        listParams.add(String.valueOf(fromOwnerId));
//                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
//                        req.setAttribute("lstError", lstError);
//                        getSession().clear();
//                        getSession().getTransaction().rollback();
//                        getSession().beginTransaction();
//                        return false;
//                    }
//Huynq13 20170407 end ignore to use new way export     
//Huynq13 20170406 start add to use new way validate serial                                             
                    ExportCheckSerial exportCheck = new ExportCheckSerial();
                    exportCheck.setCreateUser(userLogin);
                    exportCheck.setFromSerial(fromSerial + "");
                    exportCheck.setToSerial(toSerial + "");
                    exportCheck.setOwnerId(fromOwnerId);
                    exportCheck.setStockModelId(stockModelId);
                    exportCheck.setOldStatus(oldStatus);
                    exportCheck.setNewStatus(newStatus);
                    exportCheck.setStockTransId(stockTransId);
                    exportCheck.setTableName(tableName);
                    exportCheck.setFromOwnerTypeId(fromOwnerType);
                    listExportCheck.add(exportCheck);
//Huynq13 20170406 end add to use new way validate serial                                             
                    total += count;
                }
//Huynq13 20170406 start add to use new way validate serial                         
                DbProcessor db = new DbProcessor();
                int[] resultInsert = db.insertExportSerial(listExportCheck, userLogin, stockTransId);
                if (resultInsert == null) {
                    List listParams = new ArrayList<String>();
                    listParams.add("can not insert ExportSerial");
                    lstError.add(new ShowMessage("stock.error.exception", listParams));
                    req.setAttribute("lstError", lstError);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return false;
                }
//Huynq13 20170406 end add to use new way validate serial  
            }
            //Check so luong serial update duoc khong bang tong so luong hang xuat di
//Huynq13 20170407 start ignore to use new way export                 
//            if (total != quantity.intValue()) {
//                List listParams = new ArrayList<String>();
//                listParams.add(String.valueOf(count));
//                listParams.add(String.valueOf(quantity));
//                lstError.add(new ShowMessage("stock.error.updateSerial.all", listParams));
//                req.setAttribute("lstError", lstError);
//                getSession().clear();
//                getSession().getTransaction().rollback();
//                getSession().beginTransaction();
//                return false;
//            }
//Huynq13 20170407 end ignore to use new way export                 
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }
        return noError;
    }

    public boolean updateSeialByListNewWay(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType,
            Long toOwnerId, Long oldStatus, Long newStatus, Long quantity, Long channelTypeId, String userLogin, Long stockTransId) throws Exception {
        boolean noError = true;
        HttpServletRequest req = getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {

            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(stockModelId));
                lstError.add(new ShowMessage("stock.error.stockModelId.notFound", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockModel.getCheckSerial().equals(Constant.GOODS_NON_SERIAL)) {
                return true;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (lstSerial == null || lstSerial.isEmpty()) {
                return true;
            }
            stockTypeId = stockModel.getStockTypeId();

            // Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;

            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            List<ExportCheckSerial> listExportCheck = new ArrayList<ExportCheckSerial>();
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (channelTypeId != null) {
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());
                    ExportCheckSerial exportCheck = new ExportCheckSerial();
                    exportCheck.setCreateUser(userLogin);
                    exportCheck.setFromSerial(fromSerial + "");
                    exportCheck.setToSerial(toSerial + "");
                    exportCheck.setFromOwnerId(fromOwnerId);
                    exportCheck.setToOwnerId(toOwnerId);
                    exportCheck.setToOwnerTypeId(toOwnerType);
                    exportCheck.setStockModelId(stockModelId);
                    exportCheck.setOldStatus(oldStatus);
                    exportCheck.setNewStatus(newStatus);
                    exportCheck.setStockTransId(stockTransId);
                    exportCheck.setChannelTypeId(channelTypeId);
                    exportCheck.setTableName(tableName);
                    exportCheck.setFromOwnerTypeId(fromOwnerType);
                    listExportCheck.add(exportCheck);
                    total += count;
                }
                DbProcessor db = new DbProcessor();
                int[] resultInsert = db.insertImportSerialChannelId(listExportCheck, userLogin, stockTransId);
                if (resultInsert == null) {
                    List listParams = new ArrayList<String>();
                    listParams.add("can not insert ExportSerial");
                    lstError.add(new ShowMessage("stock.error.exception", listParams));
                    req.setAttribute("lstError", lstError);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return false;
                }
            } else {
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());
                    ExportCheckSerial exportCheck = new ExportCheckSerial();
                    exportCheck.setCreateUser(userLogin);
                    exportCheck.setFromSerial(fromSerial + "");
                    exportCheck.setToSerial(toSerial + "");
                    exportCheck.setFromOwnerId(fromOwnerId);
                    exportCheck.setToOwnerId(toOwnerId);
                    exportCheck.setToOwnerTypeId(toOwnerType);
                    exportCheck.setStockModelId(stockModelId);
                    exportCheck.setOldStatus(oldStatus);
                    exportCheck.setNewStatus(newStatus);
                    exportCheck.setStockTransId(stockTransId);
                    exportCheck.setTableName(tableName);
                    exportCheck.setFromOwnerTypeId(fromOwnerType);
                    listExportCheck.add(exportCheck);
                    total += count;
                }
                DbProcessor db = new DbProcessor();
                int[] resultInsert = db.insertImportSerial(listExportCheck, userLogin, stockTransId);
                if (resultInsert == null) {
                    List listParams = new ArrayList<String>();
                    listParams.add("can not insert ExportSerial");
                    lstError.add(new ShowMessage("stock.error.exception", listParams));
                    req.setAttribute("lstError", lstError);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return false;
                }
            }
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }
        return noError;
    }
    /*
     * Author: ThanhNC
     * Date created: 17/03/2009
     * Purpose: Cap nhap shopId moi cho cac serial duoc xuat khoi kho
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              fromOwnerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              fromOwnerId: Ma kho xuat
     *              toOwnerType: Loai kho nhap 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              toOwnerId: Ma kho nhap
     *
     *              TrongLV
     *              2011/04/09
     *              channelTypeId: kenh can gan cho serial hang hoa
     */

    public boolean updateSeialByList(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType, Long toOwnerId, Long oldStatus, Long newStatus, Long quantity, Long channelTypeId) throws Exception {
        HttpServletRequest req = getRequest();

        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {

            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(stockModelId));
                lstError.add(new ShowMessage("stock.error.stockModelId.notFound", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockModel.getCheckSerial().equals(Constant.GOODS_NON_SERIAL)) {
                return true;
            }

            if (lstSerial == null) {
                lstError.add(new ShowMessage("stock.error.lstSerial.null"));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;
            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);//TruongNQ5 R6505 them create_date = sysdate 
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0, create_date = sysdate  where stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
//            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0, create_date = sysdate  where  "
//                    + " owner_id = ? and  serial >= ? and serial <= ? and stock_model_id = ? and status = ? "; //Huynq13 2017/04/03 add


            //Neu kenh can gan != null
            //Bo sung chuc nang gan kenh cho serial hang hoa
            if (channelTypeId != null) {
                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0 "
                        + ", channel_Type_Id = ?, create_date = sysdate  "
                        + " where stock_model_id = ? "
                        + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
                //fix by hieptd
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    int i = 0;
                    query.setParameter(i++, newStatus);
                    query.setParameter(i++, toOwnerType);
                    query.setParameter(i++, toOwnerId);
                    query.setParameter(i++, channelTypeId);
                    query.setParameter(i++, stockModelId);
                    query.setParameter(i++, fromOwnerType);
                    query.setParameter(i++, fromOwnerId);
                    query.setParameter(i++, fromSerial);
                    query.setParameter(i++, toSerial);
                    query.setParameter(i++, oldStatus);
                    count = query.executeUpdate();
                    //Neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi

                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        List listParams = new ArrayList<String>();
                        listParams.add(String.valueOf(fromSerial));
                        listParams.add(String.valueOf(toSerial));
                        listParams.add(String.valueOf(oldStatus));
                        listParams.add(String.valueOf(count));
                        listParams.add(String.valueOf(fromOwnerType));
                        listParams.add(String.valueOf(fromOwnerId));
                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                        req.setAttribute("lstError", lstError);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }
            } else {

                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    int i = 0;
                    query.setParameter(i++, newStatus);
                    query.setParameter(i++, toOwnerType);
                    query.setParameter(i++, toOwnerId);
                    query.setParameter(i++, stockModelId);
                    query.setParameter(i++, fromOwnerType);
                    query.setParameter(i++, fromOwnerId);
                    query.setParameter(i++, fromSerial);
                    query.setParameter(i++, toSerial);
//                    query.setParameter(i++, stockModelId);
                    query.setParameter(i++, oldStatus);
                    count = query.executeUpdate();
                    //Neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi

                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        List listParams = new ArrayList<String>();
                        listParams.add(String.valueOf(fromSerial));
                        listParams.add(String.valueOf(toSerial));
                        listParams.add(String.valueOf(oldStatus));
                        listParams.add(String.valueOf(count));
                        listParams.add(String.valueOf(fromOwnerType));
                        listParams.add(String.valueOf(fromOwnerId));
                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                        req.setAttribute("lstError", lstError);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }
            }
            if (total != quantity.intValue()) {
                log.debug("Khong cap nhap du so luong serial can xuat kho. "
                        + " So luong serial cap nhap thanh cong = " + count + " / tong so serial can update " + quantity);
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(total));
                listParams.add(String.valueOf(quantity));
                lstError.add(new ShowMessage("stock.error.updateSerial.all", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            return true;
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }

    }

    public boolean updateSeialByList(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType, Long toOwnerId, Long oldStatus, Long newStatus, Long quantity, Long channelTypeId, Double depositPrice) throws Exception {
        HttpServletRequest req = getRequest();

        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {

            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(stockModelId));
                lstError.add(new ShowMessage("stock.error.stockModelId.notFound", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockModel.getCheckSerial().equals(Constant.GOODS_NON_SERIAL)) {
                return true;
            }

            if (lstSerial == null) {
                lstError.add(new ShowMessage("stock.error.lstSerial.null"));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;
            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);//TruongNQ5 R6505 them create_date = sysdate 
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0,deposit_price=?, create_date = sysdate  where stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";


            //Neu kenh can gan != null
            //Bo sung chuc nang gan kenh cho serial hang hoa
            if (channelTypeId != null) {
                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0 "
                        + ", channel_Type_Id = ?,deposit_price=?, create_date = sysdate  "
                        + " where stock_model_id = ? "
                        + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
                //fix by hieptd
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    int i = 0;
                    query.setParameter(i++, newStatus);
                    query.setParameter(i++, toOwnerType);
                    query.setParameter(i++, toOwnerId);
                    query.setParameter(i++, channelTypeId);
                    query.setParameter(i++, depositPrice);
                    query.setParameter(i++, stockModelId);
                    query.setParameter(i++, fromOwnerType);
                    query.setParameter(i++, fromOwnerId);
                    query.setParameter(i++, fromSerial);
                    query.setParameter(i++, toSerial);
                    query.setParameter(i++, oldStatus);
                    count = query.executeUpdate();
                    //Neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi

                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        List listParams = new ArrayList<String>();
                        listParams.add(String.valueOf(fromSerial));
                        listParams.add(String.valueOf(toSerial));
                        listParams.add(String.valueOf(oldStatus));
                        listParams.add(String.valueOf(count));
                        listParams.add(String.valueOf(fromOwnerType));
                        listParams.add(String.valueOf(fromOwnerId));
                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                        req.setAttribute("lstError", lstError);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }
            } else {

                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    int i = 0;
                    query.setParameter(i++, newStatus);
                    query.setParameter(i++, toOwnerType);
                    query.setParameter(i++, toOwnerId);
                    query.setParameter(i++, depositPrice);
                    query.setParameter(i++, stockModelId);
                    query.setParameter(i++, fromOwnerType);
                    query.setParameter(i++, fromOwnerId);
                    query.setParameter(i++, fromSerial);
                    query.setParameter(i++, toSerial);
                    query.setParameter(i++, oldStatus);
                    count = query.executeUpdate();
                    //Neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi

                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        List listParams = new ArrayList<String>();
                        listParams.add(String.valueOf(fromSerial));
                        listParams.add(String.valueOf(toSerial));
                        listParams.add(String.valueOf(oldStatus));
                        listParams.add(String.valueOf(count));
                        listParams.add(String.valueOf(fromOwnerType));
                        listParams.add(String.valueOf(fromOwnerId));
                        lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                        req.setAttribute("lstError", lstError);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }
            }
            if (total != quantity.intValue()) {
                log.debug("Khong cap nhap du so luong serial can xuat kho. "
                        + " So luong serial cap nhap thanh cong = " + count + " / tong so serial can update " + quantity);
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(total));
                listParams.add(String.valueOf(quantity));
                lstError.add(new ShowMessage("stock.error.updateSerial.all", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            return true;
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }

    }

    /*
     * Author: Vunt
     * Date created: 17/03/2009
     * Purpose: Cap nhap shopId moi cho cac serial duoc xuat khoi kho
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              fromOwnerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              fromOwnerId: Ma kho xuat
     *              toOwnerType: Loai kho nhap 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              toOwnerId: Ma kho nhap
     *
     *              TrongLV
     *              2011/04/09
     *              channelTypeId: kenh can gan cho serial hang hoa
     * 
     */
    public boolean updateSeialByListAndStateId(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType, Long toOwnerId, Long oldStatus, Long newStatus, Long stateId, Long quantity, Long channelTypeId) throws Exception {

        try {
            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockModel.getCheckSerial().equals(Constant.GOODS_NON_SERIAL)) {
                return true;
            }

            if (lstSerial == null) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;
            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);//TruongNQ5 R6505 them create_date = sysdate 
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set state_id = ?, status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0, create_date = sysdate where stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status= ? ";

            //Neu kenh can gan != null
            //Bo sung chuc nang gan kenh cho serial hang hoa
            if (channelTypeId != null) {
                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set state_id = ?, status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0 "
                        + " , channel_Type_Id = ?, create_date = sysdate "
                        + " where stock_model_id = ? "
                        + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status= ?";
                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    query.setParameter(0, stateId);
                    query.setParameter(1, newStatus);
                    query.setParameter(2, toOwnerType);
                    query.setParameter(3, toOwnerId);
                    query.setParameter(4, channelTypeId);
                    query.setParameter(5, stockModelId);
                    query.setParameter(6, fromOwnerType);
                    query.setParameter(7, fromOwnerId);
                    query.setParameter(8, fromSerial);
                    query.setParameter(9, toSerial);
                    query.setParameter(10, oldStatus);
                    count = query.executeUpdate();
                    //Neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi

                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + +count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }


            } else {

                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    query.setParameter(0, stateId);
                    query.setParameter(1, newStatus);
                    query.setParameter(2, toOwnerType);
                    query.setParameter(3, toOwnerId);
                    query.setParameter(4, stockModelId);
                    query.setParameter(5, fromOwnerType);
                    query.setParameter(6, fromOwnerId);
                    query.setParameter(7, fromSerial);
                    query.setParameter(8, toSerial);
                    query.setParameter(9, oldStatus);
                    count = query.executeUpdate();
                    //Neu so luong ban ghi update duoc khong dung so voi so luong tu serial den serial --> bao loi

                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + +count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }
            }
            if (total != quantity.intValue()) {
                log.debug("Khong cap nhap du so luong serial can xuat kho. "
                        + " So luong serial cap nhap thanh cong = " + count + " / tong so serial can update " + quantity);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }

    }

    /*
     * Author: ThanhNC
     * Date created: 17/03/2009
     * Purpose: Cap nhap shopId moi cho cac serial duoc xuat khoi kho
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho     
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              fromOwnerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              fromOwnerId: Ma kho xuat
     *              toOwnerType: Loai kho nhap 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              toOwnerId: Ma kho nhap
     *              checkDial: co can boc tham hay khong (0: khong can boc tham 1: phai boc tham)
     *              dialStatus: trang thai boc tham (0: chua thuoc block nao 1: da chia vao block
     *              TrongLV
     *              2011/04/09
     *              channelTypeId: kenh can gan cho serial hang hoa
     *
     */
    public boolean updateSeialByList(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType, Long toOwnerId, Long oldStatus, Long newStatus, Long checkDial, Long dialStatus, Long quantity, Long channelTypeId) throws Exception {

        try {

            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockModel.getCheckSerial().equals(Constant.GOODS_NON_SERIAL)) {
                return true;
            }

            if (lstSerial == null) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;
            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);//TruongNQ5 R6505 them create_date = sysdate 
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =?,check_Dial=? , dial_Status=?, create_date = sysdate   where stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ?  and status = ?";

            //Neu kenh can gan != null
            //Bo sung chuc nang gan kenh cho serial hang hoa
            if (channelTypeId != null) {
                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =?,check_Dial=? , dial_Status=? "
                        + " , channel_Type_Id = ?, create_date = sysdate  "
                        + " where stock_model_id = ? "
                        + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ?  and status = ?";

                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    query.setParameter(0, newStatus);
                    query.setParameter(1, toOwnerType);
                    query.setParameter(2, toOwnerId);
                    query.setParameter(3, checkDial);
                    query.setParameter(4, dialStatus);
                    query.setParameter(5, channelTypeId);
                    query.setParameter(6, stockModelId);
                    query.setParameter(7, fromOwnerType);
                    query.setParameter(8, fromOwnerId);
                    query.setParameter(9, fromSerial);
                    query.setParameter(10, toSerial);
                    query.setParameter(11, oldStatus);
                    count = query.executeUpdate();
                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + +count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }
            } else {

                for (int idx = 0; idx < lstSerial.size(); idx++) {
                    count = 0;
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigInteger(stockSerial.getFromSerial());
                    toSerial = new BigInteger(stockSerial.getToSerial());

                    Query query = getSession().createSQLQuery(SQLUPDATE);
                    query.setParameter(0, newStatus);
                    query.setParameter(1, toOwnerType);
                    query.setParameter(2, toOwnerId);
                    query.setParameter(3, checkDial);
                    query.setParameter(4, dialStatus);
                    query.setParameter(5, stockModelId);
                    query.setParameter(6, fromOwnerType);
                    query.setParameter(7, fromOwnerId);
                    query.setParameter(8, fromSerial);
                    query.setParameter(9, toSerial);
                    query.setParameter(10, oldStatus);
                    count = query.executeUpdate();
                    if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                        log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                                + +count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return false;

                    }
                    total += count;
                    System.out.println("result = " + count);
                }
            }
            if (total != quantity.intValue()) {
                log.debug("Khong cap nhap du so luong serial can xuat kho. "
                        + " So luong serial cap nhap thanh cong = " + count + " / tong so serial can update " + quantity);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                return false;
            }

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }

    }

    /*
     * Author: LamNV
     * Date created: 14/11/2009
     * Purpose: Cap nhap trang thai cho serial 
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              ownerType: Loai kho 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              ownerId: Ma kho 
     *              status: 
     *              TrongLV
     *              2011/04/09
     *              channelTypeId: kenh can gan cho serial hang hoa
     *
     */
    public boolean updateStatusSeial(StockTransSerial stockSerial, Long stockModelId, Long ownerType, Long ownerId, Long status, Long channelTypeId) throws Exception {

        try {
            boolean noError = true;
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (stockSerial == null) {
                return true;
            }
            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                return false;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;


            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);//TruongNQ5 R6505 them create_date = sysdate 
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? , create_date = sysdate "
                    + " where  stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? "
                    + " and to_number(serial) >= ? and to_number(serial) <= ?  ";


            //Neu kenh can gan != null
            //Bo sung chuc nang gan kenh cho serial hang hoa
            if (channelTypeId != null) {
                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ? "
                        + " , channel_Type_Id = ?, create_date = sysdate  "
                        + " where  stock_model_id = ? "
                        + " and owner_type =? and owner_id = ? "
                        + " and to_number(serial) >= ? and to_number(serial) <= ?  ";

                fromSerial = new BigInteger(stockSerial.getFromSerial());
                toSerial = new BigInteger(stockSerial.getToSerial());

                Query query = getSession().createSQLQuery(SQLUPDATE);
                query.setParameter(0, status);
                query.setParameter(1, channelTypeId);
                query.setParameter(2, stockModelId);
                query.setParameter(3, ownerType);
                query.setParameter(4, ownerId);
                query.setParameter(5, fromSerial);
                query.setParameter(6, toSerial);
                int result = query.executeUpdate();
                System.out.println("result = " + result);
            } else {
                fromSerial = new BigInteger(stockSerial.getFromSerial());
                toSerial = new BigInteger(stockSerial.getToSerial());

                Query query = getSession().createSQLQuery(SQLUPDATE);
                query.setParameter(0, status);
                query.setParameter(1, stockModelId);
                query.setParameter(2, ownerType);
                query.setParameter(3, ownerId);
                query.setParameter(4, fromSerial);
                query.setParameter(5, toSerial);
                int result = query.executeUpdate();
                System.out.println("result = " + result);
            }




            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }

    }
//    /*
//     * Author: ThanhNC
//     * Date created: 17/03/2009
//     * Purpose: Cap nhap trang thai cho cac serial da duoc confirm nhap kho
//     * Parameter:
//     *              stockModelId: ModelId cua loai hang xuat di
//     *              ownerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
//     *              ownerId: Ma kho nhan
//     *              expStaCode: Ma phieu xuat kho (Cua kho xuat hang)
//     * Return value: So ban ghi duoc update trang thai
//     */
//
//    public int updateSeialImp(Long stockModelId, Long ownerType, Long ownerId, String expStaCode) throws Exception {
//        Long stockTypeId = 0L;
//        StockModel stockModel = new StockModelDAO().findById(stockModelId);
//        if (stockModel == null) {
//            return 0;
//        }
//        stockTypeId = stockModel.getStockTypeId();
//        String table_name = getTableNameByStockType(stockTypeId, NAME_TYPE_NORMAL);
//        if ("".equals(table_name)) {
//            log.error("Stock model id = " + stockTypeId.toString() + " not found!!");
//            return 0;
//        }
//
//        //Cap nhap trang thai hang hoa trong kho ve trang thai da confirm nhap hang
//        String SQL_UPDATE_STOCK = "update " + table_name + " set STATUS= " + Constant.STATUS_IMPORT_COMPLETED + " , EXP_STA_CODE = null " +
//                " where STOCK_MODEL_ID = " + stockModelId + " and  OWNER_TYPE = " + ownerType + " and OWNER_ID = " + ownerId + " and STATUS = " + Constant.STATUS_IMPORT_NOT_EXECUTE + " and EXP_STA_CODE = '" + expStaCode + "'";
//
//        Query q = getSession().createSQLQuery(SQL_UPDATE_STOCK);
//        return q.executeUpdate();
//    }
    /*
    
     //    public ActionResultBO actionImportByFile(ActionForm form, HttpServletRequest req) throws Exception {
     //        ActionResultBO actionResultBO = new ActionResultBO();
     //
     //        pageForward = "importSimSuccess";
     //        //String path = "";
     //        //int type = 0;
     //        //Test
     //        String path = "C:\\VT_EastCompeat  HLR1 Pre Test 05 20081230 ( Output).txt";
     //        int type = 1;
     //
     //        readResourceFile(path, type);
     //        actionResultBO.setPageForward(pageForward);
     //        return actionResultBO;
     //    }
     //
     //    /**
     //     * Thuc hien doc file
     //     */
//    protected void readResourceFile(String path, int type) throws Exception  {
//        //Thuc hien
//        Thread t = new Thread(new ReadFile(path, type));
//        t.start();
//    }

    /**
     * thuc hien doc data tu bang import partner va import partner detail de
     * thuc hien
     */

    /*
     * Author: ThanhNC
     * Date created: 17/03/2009
     * Purpose: Cap nhap shopId moi cho cac serial duoc xuat khoi kho
     * Parameter:   lstSerial: Danh sach serial xuat khoi kho
     *              stockModelId: ModelId cua loai hang xuat di
     *              stockModelName: Ten loai hang xuat khoi kho
     *              fromOwnerType: Loai kho xuat 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              fromOwnerId: Ma kho xuat
     *              toOwnerType: Loai kho nhap 1: kho cua hang , chi nhanh. 2 kho nhan vien   3 kho dai ly, cong tac vien
     *              toOwnerId: Ma kho nhap
     *              TrongLV
     *              2011/04/09
     *              channelTypeId: kenh can gan cho serial hang hoa
     *
     */
    public void updateSeialByList(List lstSerial, Long stockModelId, Long fromOwnerType, Long fromOwnerId, Long toOwnerType, Long toOwnerId, Long status, Long channelTypeId) throws Exception {

        PreparedStatement stmt = null;

        try {
            boolean noError = true;
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (lstSerial == null) {
                return;
            }
            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                return;
            }
            stockTypeId = stockModel.getStockTypeId();

            //Object baseStock = null;
            BigDecimal fromSerial;
            BigDecimal toSerial;
            StockTransSerial stockSerial;

            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);//TruongNQ5 R6505 them create_date = sysdate 
            String SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0, create_date = sysdate  where stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ?  ";

            //Neu kenh can gan != null
            //Bo sung chuc nang gan kenh cho serial hang hoa
            if (channelTypeId != null) {
                SQLUPDATE = "update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(tableName) + " set status = ?, owner_type =? ,owner_id =? ,check_dial=0, dial_status =0 "
                        + " , channel_Type_Id = ?, create_date = sysdate  "
                        + " where stock_model_id = ? "
                        + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ?  ";

                //Edit by hieptd
                for (int idx = 0; idx < lstSerial.size(); idx++) {

                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigDecimal(stockSerial.getFromSerial());
                    toSerial = new BigDecimal(stockSerial.getToSerial());

                    stmt = getSession().connection().prepareStatement(SQLUPDATE);
                    stmt.setLong(1, status);
                    stmt.setLong(2, toOwnerType);
                    stmt.setLong(3, toOwnerId);
                    stmt.setLong(4, channelTypeId);
                    stmt.setLong(5, stockModelId);
                    stmt.setLong(6, fromOwnerType);
                    stmt.setLong(7, fromOwnerId);
                    stmt.setBigDecimal(8, fromSerial);
                    stmt.setBigDecimal(9, toSerial);
                    int result = stmt.executeUpdate();
                }
            } else {
                for (int idx = 0; idx < lstSerial.size(); idx++) {

                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    fromSerial = new BigDecimal(stockSerial.getFromSerial());
                    toSerial = new BigDecimal(stockSerial.getToSerial());

                    stmt = getSession().connection().prepareStatement(SQLUPDATE);
                    stmt.setLong(1, status);
                    stmt.setLong(2, toOwnerType);
                    stmt.setLong(3, toOwnerId);
                    stmt.setLong(4, stockModelId);
                    stmt.setLong(5, fromOwnerType);
                    stmt.setLong(6, fromOwnerId);
                    stmt.setBigDecimal(7, fromSerial);
                    stmt.setBigDecimal(8, toSerial);
                    int result = stmt.executeUpdate();
                }
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            stmt.close();
        }

    }
    //ThinDM R6762

    public boolean updateSeialExpAndState(Session session, List lstSerial, Long stockModelId, Long fromOwnerType,
            Long fromOwnerId, Long oldStatus, Long newStatus, Long quantity, Long stateId) throws Exception {
        boolean noError = true;
        HttpServletRequest req = getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {
            Long stockTypeId = 0L;
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(stockModelId));
                lstError.add(new ShowMessage("stock.error.stockModelId.notFound", listParams));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            //Mat hang khong co serial --> khong can update chi tiet serial
            if (lstSerial == null || lstSerial.isEmpty()) {
                return true;
            }
            stockTypeId = stockModel.getStockTypeId();
            // Object baseStock = null;
            BigInteger fromSerial;
            BigInteger toSerial;
            StockTransSerial stockSerial;
            Long total = 0L;
            int count = 0;
            String tableName = getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            //vunt - neu khong tim thay bang de update thi return
            if (tableName == null || tableName.equals("")) {
                return true;
            }
            StringBuilder SQLUPDATE = new StringBuilder();
            SQLUPDATE.append("update " + tableName + " set status = ? ");
            if (DataUtil.safeEqual(newStatus, Constant.STATUS_SALED)) {
                SQLUPDATE.append(", sale_date = sysdate ");
            }
            SQLUPDATE.append(" where stock_model_id = ? ");
            SQLUPDATE.append(" and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? and state_id=? ");
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                count = 0;
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                fromSerial = new BigInteger(stockSerial.getFromSerial());
                toSerial = new BigInteger(stockSerial.getToSerial());
                Query query = getSession().createSQLQuery(SQLUPDATE.toString());
                query.setParameter(0, newStatus);
                query.setParameter(1, stockModelId);
                query.setParameter(2, fromOwnerType);
                query.setParameter(3, fromOwnerId);
                query.setParameter(4, fromSerial);
                query.setParameter(5, toSerial);
                query.setParameter(6, oldStatus);
                query.setParameter(7, stateId);
                count = query.executeUpdate();
                if (count != (toSerial.subtract(fromSerial).intValue() + 1)) {
                    log.debug("Dai tu serial " + fromSerial + " den serial " + toSerial + " khong du so luong "
                            + count + " trong kho owner_type =" + fromOwnerType + " owner_id = " + fromOwnerId);
                    List listParams = new ArrayList<String>();
                    listParams.add(String.valueOf(fromSerial));
                    listParams.add(String.valueOf(toSerial));
                    listParams.add(String.valueOf(oldStatus));
                    listParams.add(String.valueOf(count));
                    listParams.add(String.valueOf(fromOwnerType));
                    listParams.add(String.valueOf(fromOwnerId));
                    listParams.add(String.valueOf(newStatus));
                    lstError.add(new ShowMessage("stock.error.updateSerial", listParams));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return false;
                }
                total += count;
            }
            //Check so luong serial update duoc khong bang tong so luong hang xuat di
            if (total != quantity.intValue()) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(count));
                listParams.add(String.valueOf(quantity));
                lstError.add(new ShowMessage("stock.error.updateSerial.all", listParams));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            return false;
        }
        return noError;
    }

    public String getTableName(Long stockType, String nameType) {
        log.debug("Start getTableNameByStockTypeId .. ");
        try {
            String stockModelNameNormal = "";
            String stockModelNameHibernate = "";
            if (stockType.equals(Constant.STOCK_CARD)) {
                stockModelNameHibernate = "StockCard";
                stockModelNameNormal = "STOCK_CARD";
            }

            if (stockType.equals(Constant.STOCK_HANDSET)) {
                stockModelNameHibernate = "StockHandset";
                stockModelNameNormal = "STOCK_HANDSET";
            }

            if (stockType.equals(Constant.STOCK_ISDN_HOMEPHONE)) {
                stockModelNameHibernate = "StockIsdnHomephone";
                stockModelNameNormal = "STOCK_ISDN_HOMEPHONE";
            }

            if (stockType.equals(Constant.STOCK_ISDN_MOBILE)) {
                stockModelNameHibernate = "StockIsdnMobile";
                stockModelNameNormal = "STOCK_ISDN_MOBILE";
            }

            if (stockType.equals(Constant.STOCK_ISDN_PSTN)) {
                stockModelNameHibernate = "StockIsdnPstn";
                stockModelNameNormal = "STOCK_ISDN_PSTN";
            }

            if (stockType.equals(Constant.STOCK_KIT)) {
                stockModelNameHibernate = "StockKit";
                stockModelNameNormal = "STOCK_KIT";
            }

            if (stockType.equals(Constant.STOCK_SIM_POST_PAID)) {
                stockModelNameHibernate = "StockSimPostPaid";
                stockModelNameNormal = "STOCK_SIM";
            }

            if (stockType.equals(Constant.STOCK_SIM_PRE_PAID)) {
                stockModelNameHibernate = "StockSimPrePaid";
                stockModelNameNormal = "STOCK_SIM";
            }

            if (stockType.equals(Constant.STOCK_SUMO)) {
                stockModelNameHibernate = "StockSumo";
                stockModelNameNormal = "STOCK_SUMO";
            }
            if (stockType.equals(Constant.STOCK_ACCESSORIES)) {
                stockModelNameHibernate = "StockAccessories";
                stockModelNameNormal = "STOCK_ACCESSORIES";
            }

            if (stockType.equals(Constant.STOCK_LAPTOP)) {
                stockModelNameHibernate = "StockHandset";
                stockModelNameNormal = "STOCK_HANDSET";
            }
//            if (stockModelNameHibernate.equals("")) {
//                stockModelNameHibernate = "StockSerial";
//                stockModelNameNormal = "STOCK_SERIAL";
//            }

            if (NAME_TYPE_NORMAL.equals(nameType)) {
                return stockModelNameNormal;
            }

            return stockModelNameHibernate;

        } catch (RuntimeException re) {
            log.error("getTableNameByStockTypeId failed", re);
            throw re;
        }
    }
}
