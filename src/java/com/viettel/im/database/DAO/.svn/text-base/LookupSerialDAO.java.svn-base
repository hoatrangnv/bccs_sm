package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.LookupSerialBean;
import com.viettel.im.client.bean.ViewSerialHistoryBean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.LookupSerialForm;
import java.util.List;
import com.viettel.im.database.BO.StockType;
import java.util.ArrayList;
import org.hibernate.Session;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.UserToken;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author tamdt1
 * xu ly cac tac vu lien quan den viec tim kiem serial
 *
 */
public class LookupSerialDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(LookupSerialDAO.class);
    //dinh nghia cac hang so pageForward
    private String pageForward;
    private final String LOOKUP_SERIAL = "lookupSerial";
    private final String LOOKUP_SERIAL_EXACT = "lookupSerialExact";
    private final String LIST_LOOKUP_SERIAL = "listLookupSerial";
    private final String VIEW_LOOKUP_SERIAL_HISTORY = "viewLookupSerialHistory";
    private final String GET_SHOP_CODE = "getShopCode";
    private final String GET_SHOP_NAME = "getShopName";
    private final String CHANGE_OWNER_TYPE = "changeOwnerType";
    private final String CHANGE_STOCK_TYPE = "changeStockType";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_TELECOM_SERVICE = "listTelecomService";
    private final String REQUEST_LIST_LOOKUP_SERIAL_BEAN = "listLookupSerialBean";
    //
    private final Long MAX_RESULT = 1000L;
    //khai bao bien form
    LookupSerialForm lookupSerialForm = new LookupSerialForm();

    public LookupSerialForm getLookupSerialForm() {
        return lookupSerialForm;
    }

    public void setLookupSerialForm(LookupSerialForm lookupSerialForm) {
        this.lookupSerialForm = lookupSerialForm;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of LookupSerialDAO...");

        this.lookupSerialForm.resetForm();

        //lay du lieu cho combobox
        getDataForCombobox();

        pageForward = LOOKUP_SERIAL;

        log.info("End method preparePage of LookupSerialDAO");

        return pageForward;
    }

    public String preparePageLookupExact() throws Exception {
        log.info("Begin method preparePage of LookupSerialDAO...");

        this.lookupSerialForm.resetForm();

        //lay du lieu cho combobox
        getDataForCombobox();

        pageForward = LOOKUP_SERIAL_EXACT;

        log.info("End method preparePage of LookupSerialDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * tim kiem serial dua tren cac dieu kien tim kiem
     *
     */
    public String lookupSerial() throws Exception {
        log.info("Begin method lookupSerial of LookupSerialDAO ...");

        HttpServletRequest req = getRequest();

        Long ownerType = this.lookupSerialForm.getOwnerType();
        Long stockTypeId = this.lookupSerialForm.getStockTypeId();
        Long stockModelId = this.lookupSerialForm.getStockModelId();
        String shopCode = this.lookupSerialForm.getShopCode();
        Long stateId = this.lookupSerialForm.getStateId();
        String strFromSerial = this.lookupSerialForm.getFromSerial();
        String strToSerial = this.lookupSerialForm.getToSerial();
        Long status = this.lookupSerialForm.getStatus();


        if (stockTypeId != null) {
            List listParameter = new ArrayList();

            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_HIBERNATE);
            StringBuffer strQuery;

            /*START ANHDK: R5705: Backup cac bang du lieu lon -> query du lieu trong view*/
            //xu ly rieng doi voi truong hop the cao, 17/08/2010
            /*if (strTableName.equals("StockCard")) {
                strTableName = "VStockCard";
            }*/
            if ("StockAccessories".equals(strTableName)) {
                strTableName = "StockAccessories";
            } else if ("StockSimPrePaid".equals(strTableName) || "StockSimPostPaid".equals(strTableName)) {
                strTableName = "ViewStockSim";
            } else {
                strTableName = "View" + strTableName;
            }
            /*END ANHDK*/
            strQuery = new StringBuffer("select " + "new com.viettel.im.client.bean.LookupSerialBean(a.serial, b.stockModelCode, b.name, a.contractCode, a.batchCode, a.stateId, a.ownerType, a.ownerId, d.ownerCode, d.ownerName, a.status) " + "from " + strTableName + " a, StockModel b, VShopStaff d " + "where a.stockModelId = b.stockModelId and a.ownerType = d.id.ownerType and a.ownerId = d.id.ownerId ");

            //neu co tim kiem theo shopCode
            if (ownerType != null && ownerType.compareTo(0L) > 0 && shopCode != null && !shopCode.trim().equals("")) {
                strQuery.append("and a.ownerType = ? ");
                listParameter.add(ownerType);

                //tuy vao ownerType la shop hay staff de tim kiem
                if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
                    strQuery.append("and a.ownerId in (select shopId from Shop where lower(shopCode) = ?) ");
                    listParameter.add(shopCode.trim().toLowerCase());
                } else if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
                    strQuery.append("and a.ownerId in (select staffId from Staff where lower(staffCode) = ?) ");
                    listParameter.add(shopCode.trim().toLowerCase());
                }
            }

            //neu tim kiem theo stockModel
            if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                strQuery.append("and a.stockModelId = ? ");
                listParameter.add(stockModelId);
            } else {
                strQuery.append("and a.stockModelId in (select stockModelId from StockModel where stockTypeId = ?) ");
                listParameter.add(stockTypeId);
            }

            //tim kiem theo tinh trang
            if (stateId != null && stateId.compareTo(0L) > 0) {
                strQuery.append("and a.stateId = ? ");
                listParameter.add(stateId);
            }

            //neu tim kiem theo trang thai
            if (status != null && status.compareTo(0L) >= 0) {
                strQuery.append("and a.status = ? ");
                listParameter.add(status);
            }

            //
            if (strFromSerial != null && !strFromSerial.trim().equals("")) {
                BigInteger fromSerial;
                try {
                    fromSerial = new BigInteger(strFromSerial.trim());
                    this.lookupSerialForm.setFromSerial(strFromSerial.trim());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    fromSerial = new BigInteger("-1");
                }
                strQuery.append("and to_number(a.serial) >= ? ");
                listParameter.add(fromSerial);
            }
            if (strToSerial != null && !strToSerial.trim().equals("")) {
                BigInteger toSerial;
                try {
                    toSerial = new BigInteger(strToSerial.trim());
                    this.lookupSerialForm.setToSerial(strToSerial.trim());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    toSerial = new BigInteger("-1");
                }
                strQuery.append("and to_number(a.serial) <= ? ");
                listParameter.add(toSerial);
            }

            strQuery.append("and rownum <= ? ");
            listParameter.add(MAX_RESULT);

            //100825 Tunning code bo phan order by trong cau lenh sql thay bang order by tren list ket qua tim duoc
            //strQuery.append("order by a.stockModelId, a.stateId, to_number(a.serial), a.ownerType, a.ownerId ");

            //
            Query query = getSession().createQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List<LookupSerialBean> listLookupSerialBean = query.list();

            //100825 Tunning code bo phan order by trong cau lenh sql thay bang order by tren list ket qua tim duoc
            if (listLookupSerialBean != null && !listLookupSerialBean.isEmpty()) {
                Collections.sort(listLookupSerialBean);
            }

            req.setAttribute(REQUEST_LIST_LOOKUP_SERIAL_BEAN, listLookupSerialBean);

            if (listLookupSerialBean != null) {
                if (listLookupSerialBean.size() >= MAX_RESULT) {
                    //truogn hop tim thay so ban ghi >= maxResult
                    req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessageOverMaxResult");
                    List listParam = new ArrayList();
                    listParam.add(MAX_RESULT);
                    req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                } else {
                    //truong hop so luong ban ghi tim thay < maxResult
                    req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessage");
                    List listParam = new ArrayList();
                    listParam.add(listLookupSerialBean.size());
                    req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                }
            }
        }


        //lay du lieu cho cac combobox
        getDataForCombobox();

        pageForward = LOOKUP_SERIAL;
        log.info("End method lookupSerial of LookupSerialDAO");
        return pageForward;



//        HttpServletRequest req = getRequest();
//
////        Long telecomServiceId = this.lookupSerialForm.getTelecomServiceId();
//        Long stockTypeId = this.lookupSerialForm.getStockTypeId();
//        Long stockModelId = this.lookupSerialForm.getStockModelId();
//        Long ownerType = req.getSession().getAttribute(SESSION_CURRENT_OWNER_TYPE) != null ?
//                    (Long) req.getSession().getAttribute(SESSION_CURRENT_OWNER_TYPE) : Constant.OWNER_TYPE_SHOP; //mac dinh lay shop
//        Long shopId = this.lookupSerialForm.getShopId();
//        Long stateId = this.lookupSerialForm.getStateId();
//        String strFromSerial = this.lookupSerialForm.getFromSerial();
//        String strToSerial = this.lookupSerialForm.getToSerial();
//
//        if(stockTypeId != null) {
//            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_HIBERNATE);
//            StringBuffer strQuery;
//
//            if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
//                //chon tu bang staff
//                strQuery = new StringBuffer("select " +
//                    "new com.viettel.im.client.bean.LookupSerialBean(a.serial, b.name, a.stateId, c.name , a.ownerType, a.ownerId, d.staffCode) " +
//                    "from " + strTableName + " a, StockModel b, AppParams c, Staff d " +
//                    "where a.stockModelId = b.stockModelId and b.unit = c.code and a.ownerId = d.staffId ");
//            } else {
//                strQuery = new StringBuffer("select " +
//                    "new com.viettel.im.client.bean.LookupSerialBean(a.serial, b.name, a.stateId, c.name , a.ownerType, a.ownerId, d.shopCode) " +
//                    "from " + strTableName + " a, StockModel b, AppParams c, Shop d " +
//                    "where a.stockModelId = b.stockModelId and b.unit = c.code and a.ownerId = d.shopId ");
//            }
//
//
//            List listParameter = new ArrayList();
//
//            strQuery.append("and c.type = ? ");
//            listParameter.add(Constant.APP_PARAMS_STOCK_MODEL_UNIT);
//
//            if(stockModelId != null && stockModelId.compareTo(0L) > 0) {
//                strQuery.append("and a.stockModelId = ? ");
//                listParameter.add(stockModelId);
//            }
//            if(shopId != null && shopId.compareTo(0L) > 0) {
//                strQuery.append("and a.ownerType = ? ");
//                listParameter.add(ownerType);
//                strQuery.append("and a.ownerId = ? ");
//                listParameter.add(shopId);
//            }
//            if(stateId != null && stateId.compareTo(0L) > 0) {
//                strQuery.append("and a.stateId = ? ");
//                listParameter.add(stateId);
//            }
//            if(strFromSerial != null && !strFromSerial.trim().equals("")) {
//                BigInteger fromSerial;
//                try {
//                    fromSerial = new BigInteger(strFromSerial.trim());
//                    this.lookupSerialForm.setFromSerial(strFromSerial.trim());
//                } catch (NumberFormatException ex) {
//                    ex.printStackTrace();
//                    fromSerial = new BigInteger("-1");
//                }
//                strQuery.append("and to_number(a.serial) >= ? ");
//                listParameter.add(fromSerial);
//            }
//            if(strToSerial != null && !strToSerial.trim().equals("")) {
//                BigInteger toSerial;
//                try {
//                    toSerial = new BigInteger(strToSerial.trim());
//                    this.lookupSerialForm.setToSerial(strToSerial.trim());
//                } catch (NumberFormatException ex) {
//                    ex.printStackTrace();
//                    toSerial = new BigInteger("-1");
//                }
//                strQuery.append("and to_number(a.serial) <= ? ");
//                listParameter.add(toSerial);
//            }
//
//            Query query = getSession().createQuery(strQuery.toString());
//            for(int i = 0; i < listParameter.size(); i++) {
//                query.setParameter(i, listParameter.get(i));
//            }
//            query.setMaxResults(MAX_RESULT);
//
//            List<LookupSerialBean> listLookupSerialBean = query.list();
//            req.getSession().setAttribute(SESSION_LIST_LOOKUP_SERIAL_BEAN, listLookupSerialBean);
//
//            if (listLookupSerialBean != null) {
//                if (listLookupSerialBean.size() >= MAX_RESULT) {
//                    //truogn hop tim thay so ban ghi >= maxResult
//                    req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessageOverMaxResult");
//                    List listParam = new ArrayList();
//                    listParam.add(MAX_RESULT);
//                    req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
//                } else {
//                    //truong hop so luong ban ghi tim thay < maxResult
//                    req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessage");
//                    List listParam = new ArrayList();
//                    listParam.add(listLookupSerialBean.size());
//                    req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
//                }
//
//            }
//
//        }
//
//
//        //lay du lieu cho cac combobox
//
//        //lay danh sach cac telecomservice
////        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
////        telecomServiceDAO.setSession(this.getSession());
////        List<TelecomService> listTelecomService = telecomServiceDAO.findByProperty(
////                TelecomServiceDAO.STATUS, Constant.STATUS_USE);
////        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);
//
//        //lay danh sach cac stockType
//        StockTypeDAO stockTypeDAO = new StockTypeDAO();
//        stockTypeDAO.setSession(this.getSession());
//        List<StockType> listStockType = stockTypeDAO.getListForLookupSerial();
//        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);
//
//        //lay danh sach cac mat hang
//        if (stockTypeId != null) {
//            StockModelDAO stockModelDAO = new StockModelDAO();
//            stockModelDAO.setSession(this.getSession());
//            List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
//                    StockModelDAO.STOCK_TYPE_ID, stockTypeId, Constant.STATUS_USE);
//            req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);
//        }
//
//        pageForward = LOOKUP_SERIAL;
//
//        log.info("End method lookupSerial of LookupSerialDAO");
//
//        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * tim kiem serial dua tren cac dieu kien tim kiem
     *
     */
    public String lookupSerialExact() throws Exception {
        log.info("Begin method lookupSerial of LookupSerialDAO ...");

        HttpServletRequest req = getRequest();

        Long ownerType = this.lookupSerialForm.getOwnerType();
        Long stockTypeId = this.lookupSerialForm.getStockTypeId();
        Long stockModelId = this.lookupSerialForm.getStockModelId();
        String shopCode = this.lookupSerialForm.getShopCode();
        Long stateId = this.lookupSerialForm.getStateId();
        String serial = this.lookupSerialForm.getSerial();
        Long status = this.lookupSerialForm.getStatus();


        if (stockTypeId != null) {
            List listParameter = new ArrayList();

            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_HIBERNATE);
            StringBuffer strQuery;
            
            //xu ly rieng doi voi truong hop the cao, 17/08/2010
            if (strTableName.equals("StockCard")) {
                /*strTableName = "StockCard";*/
                strTableName = "ViewStockCard";//sua thanh select trong view
                strQuery = new StringBuffer("select " + "new com.viettel.im.client.bean.LookupSerialBean(a.serial, b.stockModelCode, b.name, a.contractCode, a.batchCode, a.stateId, a.ownerType, a.ownerId, d.ownerCode, d.ownerName, a.status) " + "from " + strTableName + " a, StockModel b, VShopStaff d " + "where a.stockModelId = b.stockModelId and a.ownerType = d.id.ownerType and a.ownerId = d.id.ownerId ");

                //tim kiem theo mat hang
                strQuery.append("and a.stockModelId in (select stockModelId from StockModel where stockTypeId = ?) ");
                listParameter.add(stockTypeId);
                //tim kiem chinh xac theo serial
                if (serial != null && !serial.trim().equals("")) {
                    BigInteger searchSerial;
                    try {
                        searchSerial = new BigInteger(serial.trim());
                        this.lookupSerialForm.setFromSerial(serial.trim());
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        searchSerial = new BigInteger("-1");
                    }
                    strQuery.append("and to_number(a.serial) = ? ");
                    listParameter.add(searchSerial);
                }

                Query query = getSession().createQuery(strQuery.toString());
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }
                List<LookupSerialBean> listLookupSerialBean = query.list();

                /* 120504 - TRONGLV - Bo khong tim trong 2 bang backup nua */
                //neu chua tim thay thi tim tiep
                /*if (listLookupSerialBean == null || listLookupSerialBean.size() == 0) {
                    listParameter = new ArrayList();
                    strTableName = "StockCard201007";
                    strQuery = new StringBuffer("select " + "new com.viettel.im.client.bean.LookupSerialBean(a.serial, b.stockModelCode, b.name, a.stateId, a.ownerType, a.ownerId, d.ownerCode, d.ownerName, a.status) " + "from " + strTableName + " a, StockModel b, VShopStaff d " + "where a.stockModelId = b.stockModelId and a.ownerType = d.id.ownerType and a.ownerId = d.id.ownerId ");

                    //tim kiem theo mat hang
                    strQuery.append("and a.stockModelId in (select stockModelId from StockModel where stockTypeId = ?) ");
                    listParameter.add(stockTypeId);
                    //tim kiem chinh xac theo serial
                    if (serial != null && !serial.trim().equals("")) {
                        BigInteger searchSerial;
                        try {
                            searchSerial = new BigInteger(serial.trim());
                            this.lookupSerialForm.setFromSerial(serial.trim());
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            searchSerial = new BigInteger("-1");
                        }
                        strQuery.append("and to_number(a.serial) = ? ");
                        listParameter.add(searchSerial);
                    }
                    query = getSession().createQuery(strQuery.toString());
                    for (int i = 0; i < listParameter.size(); i++) {
                        query.setParameter(i, listParameter.get(i));
                    }
                    listLookupSerialBean = query.list();
                    //chua tim thay tim tiep
                    if (listLookupSerialBean == null || listLookupSerialBean.size() == 0) {
                        listParameter = new ArrayList();
                        strTableName = "StockCardSaled";
                        strQuery = new StringBuffer("select " + "new com.viettel.im.client.bean.LookupSerialBean(a.serial, b.stockModelCode, b.name, a.stateId, a.ownerType, a.ownerId, d.ownerCode, d.ownerName, a.status) " + "from " + strTableName + " a, StockModel b, VShopStaff d " + "where a.stockModelId = b.stockModelId and a.ownerType = d.id.ownerType and a.ownerId = d.id.ownerId ");

                        //tim kiem theo mat hang
                        strQuery.append("and a.stockModelId in (select stockModelId from StockModel where stockTypeId = ?) ");
                        listParameter.add(stockTypeId);
                        //tim kiem chinh xac theo serial
                        if (serial != null && !serial.trim().equals("")) {
                            BigInteger searchSerial;
                            try {
                                searchSerial = new BigInteger(serial.trim());
                                this.lookupSerialForm.setFromSerial(serial.trim());
                            } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                                searchSerial = new BigInteger("-1");
                            }
                            strQuery.append("and to_number(a.serial) = ? ");
                            listParameter.add(searchSerial);
                        }
                        query = getSession().createQuery(strQuery.toString());
                        for (int i = 0; i < listParameter.size(); i++) {
                            query.setParameter(i, listParameter.get(i));
                        }
                        listLookupSerialBean = query.list();
                    }
                }*/

                req.setAttribute(REQUEST_LIST_LOOKUP_SERIAL_BEAN, listLookupSerialBean);

                if (listLookupSerialBean != null) {
                    if (listLookupSerialBean.size() >= MAX_RESULT) {
                        //truogn hop tim thay so ban ghi >= maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessageOverMaxResult");
                        List listParam = new ArrayList();
                        listParam.add(MAX_RESULT);
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    } else {
                        //truong hop so luong ban ghi tim thay < maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessage");
                        List listParam = new ArrayList();
                        listParam.add(listLookupSerialBean.size());
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    }
                }
            } else {
                
                /*START ANHDK: R5705: Backup cac bang du lieu lon -> query du lieu trong view*/
                if ("StockAccessories".equals(strTableName)) {
                    strTableName = "StockAccessories";
                } else if ("StockSimPrePaid".equals(strTableName) || "StockSimPostPaid".equals(strTableName)) {
                    strTableName = "ViewStockSim";
                } else {
                    strTableName = "View" + strTableName;
                }
                /*END ANHDK*/
                strQuery = new StringBuffer("select " + "new com.viettel.im.client.bean.LookupSerialBean(a.serial, b.stockModelCode, b.name, a.contractCode, a.batchCode, a.stateId, a.ownerType, a.ownerId, d.ownerCode, d.ownerName, a.status) " + "from " + strTableName + " a, StockModel b, VShopStaff d " + "where a.stockModelId = b.stockModelId and a.ownerType = d.id.ownerType and a.ownerId = d.id.ownerId ");

                //tim kiem theo mat hang
                strQuery.append("and a.stockModelId in (select stockModelId from StockModel where stockTypeId = ?) ");
                listParameter.add(stockTypeId);
                //tim kiem chinh xac theo serial
                if (serial != null && !serial.trim().equals("")) {
                    BigInteger searchSerial;
                    try {
                        searchSerial = new BigInteger(serial.trim());
                        this.lookupSerialForm.setFromSerial(serial.trim());
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        searchSerial = new BigInteger("-1");
                    }
                    strQuery.append("and to_number(a.serial) = ? ");
                    listParameter.add(searchSerial);
                }

                Query query = getSession().createQuery(strQuery.toString());
                for (int i = 0; i < listParameter.size(); i++) {
                    query.setParameter(i, listParameter.get(i));
                }

                List<LookupSerialBean> listLookupSerialBean = query.list();

                //100825 Tunning code bo phan order by trong cau lenh sql thay bang order by tren list ket qua tim duoc
                if (listLookupSerialBean != null && !listLookupSerialBean.isEmpty()) {
                    Collections.sort(listLookupSerialBean);
                }

                req.setAttribute(REQUEST_LIST_LOOKUP_SERIAL_BEAN, listLookupSerialBean);

                if (listLookupSerialBean != null) {
                    if (listLookupSerialBean.size() >= MAX_RESULT) {
                        //truogn hop tim thay so ban ghi >= maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessageOverMaxResult");
                        List listParam = new ArrayList();
                        listParam.add(MAX_RESULT);
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    } else {
                        //truong hop so luong ban ghi tim thay < maxResult
                        req.setAttribute(REQUEST_MESSAGE, "lookupSerial.searchMessage");
                        List listParam = new ArrayList();
                        listParam.add(listLookupSerialBean.size());
                        req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                    }
                }
            }
        }


        //lay du lieu cho cac combobox
        getDataForCombobox();
        pageForward = LOOKUP_SERIAL_EXACT;
        log.info("End method lookupSerial of LookupSerialDAO");
        return pageForward;



//      
    }
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * lay du lieu cho autocompleter
     *
     */
    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("lookupSerialForm.shopCode");

            if (shopCode != null && shopCode.trim().length() > 0) {
                //mac dinh la tim trong bang shop truoc, tim trong bang staff sau
                String queryString = "from Shop where lower(shopCode) like ? and status = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, "%" + shopCode.trim().toLowerCase() + "%");
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setMaxResults(8);
                int listShopSize = 0;
                List<Shop> listShop = queryObject.list();
                if (listShop != null && listShop.size() > 0) {
                    for (int i = 0; i < listShop.size(); i++) {
                        this.listItem.put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
                    }
                    listShopSize = listShop.size();
                }

                if (listShopSize < 8) {
                    //neu chua du 8 ban ghi, tim tiep trong bang staff
                    queryString = "from Staff where lower(staffCode) like ? and status = ? ";
                    queryObject = getSession().createQuery(queryString);
                    queryObject.setParameter(0, "%" + shopCode.trim().toLowerCase() + "%");
                    queryObject.setParameter(1, Constant.STATUS_USE);
                    queryObject.setMaxResults(8 - listShopSize);
                    List<Staff> listStaff = queryObject.list();
                    if (listStaff != null && listStaff.size() > 0) {
                        for (int i = 0; i < listStaff.size(); i++) {
                            this.listItem.put(listStaff.get(i).getStaffId(), listStaff.get(i).getStaffCode());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return GET_SHOP_CODE;


//        try {
//            HttpServletRequest req = getRequest();
//            String shopCode = req.getParameter("lookupSerialForm.shopCode");
//
//            if (shopCode != null && shopCode.trim().length() > 0) {
//                //tuy thuoc vao shop hay staff ma tim trong bang tuong ung
//                //mac dinh la tim trong bang shop
//                Long ownerType = req.getSession().getAttribute(SESSION_CURRENT_OWNER_TYPE) != null ?
//                    (Long) req.getSession().getAttribute(SESSION_CURRENT_OWNER_TYPE) : Constant.OWNER_TYPE_SHOP;
//
//                if(ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
//                    String queryString = "from Staff where lower(staffCode) like ? and status = ? ";
//                    Query queryObject = getSession().createQuery(queryString);
//                    queryObject.setParameter(0, "%" + shopCode.trim().toLowerCase() + "%");
//                    queryObject.setParameter(1, Constant.STATUS_USE);
//                    queryObject.setMaxResults(8);
//                    List<Staff> listStaff = queryObject.list();
//                    if (listStaff != null && listStaff.size() > 0) {
//                        for (int i = 0; i < listStaff.size(); i++) {
//                            this.listItem.put(listStaff.get(i).getStaffId(), listStaff.get(i).getStaffCode());
//                        }
//                    }
//                } else {
//                    String queryString = "from Shop where lower(shopCode) like ? and status = ? ";
//                    Query queryObject = getSession().createQuery(queryString);
//                    queryObject.setParameter(0, "%" + shopCode.trim().toLowerCase() + "%");
//                    queryObject.setParameter(1, Constant.STATUS_USE);
//                    queryObject.setMaxResults(8);
//                    List<Shop> listShop = queryObject.list();
//                    if (listShop != null && listShop.size() > 0) {
//                        for (int i = 0; i < listShop.size(); i++) {
//                            this.listItem.put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
//                        }
//                    }
//                }
//                
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return GET_SHOP_CODE;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * lay ten shopCode cap nhat vao textbox
     *
     */
    public String getShopName() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String strShopCode = req.getParameter("shopCode");
            String target = req.getParameter("target");

            if (strShopCode != null && strShopCode.trim().length() > 0) {
                //mac dinh la tim trong bang shop truoc, bang staff sau
                String queryString = "from Shop where shopCode = ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, strShopCode.trim());
                queryObject.setMaxResults(8);
                List<Shop> listShop = queryObject.list();
                if (listShop != null && listShop.size() > 0) {
                    this.listItem.put(target, listShop.get(0).getName());
                } else {
                    //tim trong bang staff
                    queryString = "from Staff where staffCode = ? ";
                    queryObject = getSession().createQuery(queryString);
                    queryObject.setParameter(0, strShopCode.trim());
                    queryObject.setMaxResults(8);
                    List<Staff> listStaff = queryObject.list();
                    if (listStaff != null && listStaff.size() > 0) {
                        this.listItem.put(target, listStaff.get(0).getName());
                    } else {
                        this.listItem.put(target, "");
                    }
                }
            } else {
                this.listItem.put(target, "");
            }

        } catch (Exception ex) {
            throw ex;
        }

        return GET_SHOP_NAME;

//        try {
//            HttpServletRequest req = getRequest();
//            String strShopId = req.getParameter("shopId");
//            String target = req.getParameter("target");
//
//            if (strShopId != null && strShopId.trim().length() > 0) {
//                //tuy thuoc vao shop hay staff ma tim trong bang tuong ung
//                //mac dinh la tim trong bang shop
//                Long ownerType = req.getSession().getAttribute(SESSION_CURRENT_OWNER_TYPE) != null ?
//                    (Long) req.getSession().getAttribute(SESSION_CURRENT_OWNER_TYPE) : Constant.OWNER_TYPE_SHOP;
//
//                if(ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
//                    String queryString = "from Staff where staffId = ? ";
//                    Query queryObject = getSession().createQuery(queryString);
//                    queryObject.setParameter(0, Long.valueOf(strShopId));
//                    queryObject.setMaxResults(8);
//                    List<Staff> listStaff = queryObject.list();
//                    if (listStaff != null && listStaff.size() > 0) {
//                        this.listItem.put(target, listStaff.get(0).getName());
//                    }
//                } else {
//                    String queryString = "from Shop where shopId = ? ";
//                    Query queryObject = getSession().createQuery(queryString);
//                    queryObject.setParameter(0, Long.valueOf(strShopId));
//                    queryObject.setMaxResults(8);
//                    List<Shop> listShop = queryObject.list();
//                    if (listShop != null && listShop.size() > 0) {
//                        this.listItem.put(target, listShop.get(0).getName());
//                    }
//                }
//            }
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//
//        return GET_SHOP_NAME;
    }

//    /**
//     *
//     * author tamdt1
//     * date: 21/06/2009
//     * thay doi bien currentOwnerType khi ownerType thay doi
//     *
//     */
//    public String changeOwnerType() throws Exception {
//        try {
//            HttpServletRequest req = getRequest();
//            String strOwnerType = req.getParameter("ownerType");
//            String target = req.getParameter("target");
//
//            try {
//                Long ownerType = Long.valueOf(strOwnerType);
//                req.getSession().setAttribute(SESSION_CURRENT_OWNER_TYPE, ownerType);
//
//            } catch (NumberFormatException ex) {
//                ex.printStackTrace();
//                req.getSession().setAttribute(SESSION_CURRENT_OWNER_TYPE, null);
//
//            }
//
//            //
//            this.listItem.put(target, strOwnerType);
//
//
//        } catch (Exception ex) {
//            throw ex;
//        }
//        return CHANGE_OWNER_TYPE;
//    }
    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * thay doi bien currentOwnerType khi ownerType thay doi
     *
     */
    public String changeStockType() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String strStockTypeId = req.getParameter("stockTypeId");
            String target = req.getParameter("target");

            if (strStockTypeId != null && !strStockTypeId.trim().equals("")) {
                Long stockTypeId = Long.valueOf(strStockTypeId);
                String queryString = "select stockModelId, name "
                        + "from StockModel as model "
                        + "where model.stockTypeId = ? and status = ? "
                        + "order by nlssort(name,'nls_sort=Vietnamese') asc";
                Session session = getSession();
                Query queryObject = session.createQuery(queryString);
                queryObject.setParameter(0, stockTypeId);
                queryObject.setParameter(1, Constant.STATUS_USE);
                List tmpList = queryObject.list();
                String[] tmpHeader = {"", getText("MSG.GOD.217")};
                //String[] tmpHeader = {"", "--Chọn mặt hàng--"};
                List list = new ArrayList();
                list.add(tmpHeader);
                list.addAll(tmpList);
                this.listItem.put(target, list);

            } else {
                String[] tmpHeader = {"", getText("MSG.GOD.217")};
                //String[] tmpHeader = {"", "--Chọn mặt hàng--"};
                List list = new ArrayList();
                list.add(tmpHeader);
                this.listItem.put(target, list);

            }

        } catch (Exception ex) {
            throw ex;
        }
        return CHANGE_STOCK_TYPE;
    }

    /**
     *
     * author tamdt1
     * date: 21/06/2009
     * phuc vu muc dich phan trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNagivator of LookupSerialDAO ...");

        pageForward = LIST_LOOKUP_SERIAL;
        log.info("End method pageNagivator of LookupSerialDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 18/07/2009
     * lay du lieu cho cac combobox
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac stockType
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List<StockType> listStockType = stockTypeDAO.getListForLookupSerial();
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);

        //lay danh sach cac mat hang (mac dinh stockType la bo kit)
        Long stockTypeId = this.lookupSerialForm.getStockTypeId();
        if (stockTypeId == null) {
            //neu == nul lay mac dinh la stockKit
            stockTypeId = Constant.STOCK_KIT;
            this.lookupSerialForm.setStockTypeId(stockTypeId);
        }
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                StockModelDAO.STOCK_TYPE_ID, Constant.STOCK_KIT, Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String strOwnerType = imSearchBean.getOtherParamValue();
        Long ownerType = -1L;
        try {
            ownerType = Long.valueOf(strOwnerType);
        } catch (NumberFormatException ex) {
            ownerType = -1L;
        }

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
            //neu ownerType la shop lay danh sach cua hang hien tai + cua hang cap duoi
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
            strQuery1.append("from Shop a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

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
            listParameter1.add(100L);

            strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();

        } else if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            //neu ownerType la staff lay danh sach nhan vien cua hang hien tai + cua hang cap duoi
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
            strQuery1.append("from Staff a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and a.shopId in (select shopId from Shop where shopPath like ? or shopId = ?) ");
            listParameter1.add("%_" + userToken.getShopId() + "_%");
            listParameter1.add(userToken.getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
            }

            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) like ? ");
                listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(100L);

            strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String strOwnerType = imSearchBean.getOtherParamValue();
        Long ownerType = -1L;
        try {
            ownerType = Long.valueOf(strOwnerType);
        } catch (NumberFormatException ex) {
            ownerType = -1L;
        }

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
            //lay ten cua cua hang
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
            strQuery1.append("from Shop a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
            listParameter1.add("%_" + userToken.getShopId() + "_%");
            listParameter1.add(userToken.getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.shopCode) = ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
            } else {
                return listImSearchBean;
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(2L);

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();

        } else if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
            //lay ten staff
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
            strQuery1.append("from Staff a ");
            strQuery1.append("where 1 = 1 ");

            strQuery1.append("and a.status = ? ");
            listParameter1.add(Constant.STATUS_ACTIVE);

            strQuery1.append("and a.shopId in (select shopId from Shop where shopPath like ? or shopId = ?) ");
            listParameter1.add("%_" + userToken.getShopId() + "_%");
            listParameter1.add(userToken.getShopId());

            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) = ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
            } else {
                return listImSearchBean;
            }

            strQuery1.append("and rownum < ? ");
            listParameter1.add(2L);

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            listImSearchBean = query1.list();

        }

        return listImSearchBean;
    }

    /**
     *
     * author   : TheTM
     * date     :
     * desc     : xem lich su Serial
     * 
     */
    public String viewLookUpSerialHistory() throws Exception {
        log.info("Begin method viewLookUpSerialHistory of LookupSerialDAO");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

//        String serial = req.getParameter("serial").trim();
//        String stockModelCode = req.getParameter("stockModelCode").trim();
        String serial = this.lookupSerialForm.getFromSerial();
        String stockModelCode = this.lookupSerialForm.getStockModelCode();
        Date fromDate = this.lookupSerialForm.getFromDate();
        Date toDate = this.lookupSerialForm.getToDate();
        if (toDate == null) {
            this.lookupSerialForm.setFromDate(getSysdate());
            this.lookupSerialForm.setToDate(getSysdate());
            pageForward = VIEW_LOOKUP_SERIAL_HISTORY;
            log.info("End method viewLookUpSerialHistory of LookupSerialDAO");
            return pageForward;
        }
        if (fromDate == null) {
            this.lookupSerialForm.setFromDate(getSysdate());
            pageForward = VIEW_LOOKUP_SERIAL_HISTORY;
            log.info("End method viewLookUpSerialHistory of LookupSerialDAO");
            return pageForward;
        }
        if (dateDiff(fromDate, toDate) > 60) {
            pageForward = VIEW_LOOKUP_SERIAL_HISTORY;
            req.setAttribute("message", "Error. From date and to date must less than 2 month! ");
            log.info("End method viewLookUpSerialHistory of LookupSerialDAO");
            return pageForward;
        }
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List<StockModel> listStockModel = stockModelDAO.findByStockModelCode(stockModelCode);
        Long stockModelId = listStockModel.get(0).getStockModelId();
        List<ViewSerialHistoryBean> stockTransHistoryList = listLookUpSerialHistory(serial, stockModelId, fromDate, DateTimeUtils.addDate(toDate, 1));
        //Neu serial o trang thai da ban --> tra cuu them lich su ban hang
        if (lookupSerialForm.getStatus() != null && lookupSerialForm.getStatus().equals(Constant.STATUS_DELETE)) {
            List<ViewSerialHistoryBean> saleTransHistoryList = listLookUpSaleTransHistory(serial, stockModelId, fromDate, DateTimeUtils.addDate(toDate, 1));
            saleTransHistoryList.addAll(stockTransHistoryList);
            req.setAttribute("invoiceListHistory", saleTransHistoryList);

        } else {
            req.setAttribute("invoiceListHistory", stockTransHistoryList);
        }
        pageForward = VIEW_LOOKUP_SERIAL_HISTORY;
        log.info("End method viewLookUpSerialHistory of LookupSerialDAO");
        return pageForward;
    }

    /**
     *
     * author   : TheTM
     * modified : tamdt1, 20/03/2010
     * desc     : lay danh sach lich su serial
     *
     */
    private List<ViewSerialHistoryBean> listLookUpSerialHistory(String serial, Long stockModelId, Date fromDate, Date toDate) {
        if (toDate == null) {
            toDate = DateTimeUtils.getSysDate();
        }
        if (fromDate == null) {
            fromDate = DateTimeUtils.addDate(toDate, -10); //lui lai 10 ngay so voi ngay hien tai
        }

        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append("      SELECT  a.stock_trans_id AS stockTransId, ");
        sqlBuffer.append("              DECODE (a.stock_trans_type, 1, 'Stock out', 'Stock in') AS stockTransType, ");

        sqlBuffer.append("              CASE WHEN a.from_owner_type = 1 THEN (SELECT shop_code || ' - ' || NAME FROM shop WHERE shop_id = a.from_owner_id) WHEN a.from_owner_type = 2 THEN (SELECT staff_code || ' - ' || NAME FROM staff WHERE staff_id = a.from_owner_id) ELSE NULL END AS exportStore, ");
        sqlBuffer.append("              CASE WHEN a.to_owner_type = 1 THEN (SELECT shop_code || ' - ' || NAME FROM shop WHERE shop_id = a.to_owner_id) WHEN a.to_owner_type = 2 THEN (SELECT staff_code || ' - ' || NAME FROM staff WHERE staff_id = a.to_owner_id) ELSE NULL END AS importStore, ");

        sqlBuffer.append("              DECODE (    a.stock_trans_status, ");
        sqlBuffer.append("                          '1', '" + getText("MSG.GOD.190") + "', ");
        sqlBuffer.append("                          '2', '" + getText("MSG.GOD.191") + "', ");
        sqlBuffer.append("                          '3', decode(a.stock_trans_type,1,'" + getText("MSG.GOD.194") + "',2,'" + getText("MSG.GOD.192") + "','-'), ");
        sqlBuffer.append("                          '4', '" + getText("MSG.GOD.195") + "', ");
        sqlBuffer.append("                          '5', '" + getText("MSG.GOD.196") + "', ");
        sqlBuffer.append("                          '6', '" + getText("MSG.GOD.197") + "','-' ");
        sqlBuffer.append("              ) AS stockTransStatus, ");
        sqlBuffer.append("              (SELECT staff.staff_code ||' - ' || staff.name FROM staff WHERE staff_id = a.real_trans_user_id) AS userSerial, ");
        sqlBuffer.append("              a.create_datetime AS creatDate ");
        sqlBuffer.append("      FROM    stock_trans a, stock_trans_serial b ");
        sqlBuffer.append("      WHERE   a.stock_trans_id = b.stock_trans_id ");

        sqlBuffer.append("              AND a.CREATE_DATETIME >= ? ");
        parameterList.add(fromDate);
        sqlBuffer.append("              AND a.CREATE_DATETIME < ? ");
        parameterList.add(toDate);
        sqlBuffer.append("              AND b.CREATE_DATETIME >= ? ");
        parameterList.add(fromDate);
        sqlBuffer.append("              AND b.CREATE_DATETIME < ? ");
        parameterList.add(toDate);

        sqlBuffer.append("              AND a.pay_status IS NULL ");

        sqlBuffer.append("              AND b.stock_model_id= ? ");
        parameterList.add(stockModelId);
        sqlBuffer.append("              AND b.from_serial <= ? ");
        parameterList.add(serial);
        sqlBuffer.append("              AND b.to_serial >= ? ");
        parameterList.add(serial);

        sqlBuffer.append("ORDER BY creatDate DESC, stockTransId DESC ");

        Session session = getSession();
        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).
                addScalar("stockTransId", Hibernate.LONG).
                addScalar("stockTransType", Hibernate.STRING).
                addScalar("exportStore", Hibernate.STRING).
                addScalar("importStore", Hibernate.STRING).
                addScalar("stockTransStatus", Hibernate.STRING).
                addScalar("userSerial", Hibernate.STRING).
                addScalar("creatDate", Hibernate.TIMESTAMP).
                setResultTransformer(Transformers.aliasToBean(ViewSerialHistoryBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        return queryObject.list();
    }

    private List<ViewSerialHistoryBean> listLookUpSaleTransHistory(String serial, Long stockModelId, Date fromDate, Date toDate) {
        if (toDate == null) {
            toDate = DateTimeUtils.getSysDate();
        }
        if (fromDate == null) {
            fromDate = DateTimeUtils.addDate(toDate, -10); //lui lai 10 ngay so voi ngay hien tai
        }

        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();


        sqlBuffer.append("      SELECT  a.sale_trans_id AS stockTransId, ");
        sqlBuffer.append("              DECODE (    a.sale_trans_type, ");

        sqlBuffer.append("                        1, 'Sales Retail', ");
        sqlBuffer.append("                        2, 'Sales to Big Agent', ");
        sqlBuffer.append("                        3, 'Sales to Channel', ");
        sqlBuffer.append("                        4, 'Make services from Showroom', ");
        sqlBuffer.append("                        5, 'Sales for Promotion', ");
        sqlBuffer.append("                        6, 'Sales for Internal', ");
        sqlBuffer.append("                        7, 'Make services from Channel', ");
        sqlBuffer.append("                        9, 'Make trans for Channel', ");
        sqlBuffer.append("                        'Others' ");

        sqlBuffer.append("              ) AS stockTransType, ");

        sqlBuffer.append("              CASE WHEN a.sale_trans_type IN (2, 6) THEN (SELECT d.shop_code || ' - ' || d.NAME FROM shop d WHERE d.shop_id = a.shop_id) ELSE (SELECT d.staff_code || ' - ' || d.NAME FROM staff d WHERE d.staff_id = a.staff_id) END AS exportStore, ");
        sqlBuffer.append("              a.cust_name AS importStore, ");

        sqlBuffer.append("              DECODE (    a.status, ");
        sqlBuffer.append("                          '2', 'Not created invoice', ");
        sqlBuffer.append("                          '3', 'Created invoice', ");
        sqlBuffer.append("                          '4', 'Cancelled' ");
        sqlBuffer.append("              ) AS stockTransStatus, ");
        sqlBuffer.append("              (SELECT  e.staff_code || ' - ' || e.NAME FROM staff e WHERE e.staff_id = a.staff_id) AS userSerial, ");
        sqlBuffer.append("              a.sale_trans_date AS creatDate ");
        sqlBuffer.append("      FROM    sale_trans a, sale_trans_detail b, sale_trans_serial c ");
        sqlBuffer.append("      WHERE   a.sale_trans_id=b.sale_trans_id ");
        sqlBuffer.append("              AND b.sale_trans_detail_id=c.sale_trans_detail_id ");

        sqlBuffer.append("              AND a.SALE_TRANS_DATE >= ? ");
        parameterList.add(fromDate);
        sqlBuffer.append("              AND a.SALE_TRANS_DATE < ? ");
        parameterList.add(toDate);
        sqlBuffer.append("              AND b.SALE_TRANS_DATE >= ? ");
        parameterList.add(fromDate);
        sqlBuffer.append("              AND b.SALE_TRANS_DATE < ? ");
        parameterList.add(toDate);
        sqlBuffer.append("              AND c.SALE_TRANS_DATE >= ? ");
        parameterList.add(fromDate);
        sqlBuffer.append("              AND c.SALE_TRANS_DATE < ? ");
        parameterList.add(toDate);
        sqlBuffer.append("              AND b.stock_model_id = ? ");
        parameterList.add(stockModelId);
        sqlBuffer.append("              AND c.from_serial <= ? ");
        parameterList.add(serial);
        sqlBuffer.append("              AND c.to_serial >= ? ");
        parameterList.add(serial);

        sqlBuffer.append("ORDER BY creatDate DESC, stockTransId DESC ");

        Session session = getSession();
        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).
                addScalar("stockTransId", Hibernate.LONG).
                addScalar("stockTransType", Hibernate.STRING).
                addScalar("exportStore", Hibernate.STRING).
                addScalar("importStore", Hibernate.STRING).
                addScalar("stockTransStatus", Hibernate.STRING).
                addScalar("userSerial", Hibernate.STRING).
                addScalar("creatDate", Hibernate.TIMESTAMP).
                setResultTransformer(Transformers.aliasToBean(ViewSerialHistoryBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        return queryObject.list();
    }

    private Long dateDiff(Date fromDate, Date toDate) {
        Long diff = toDate.getTime() - fromDate.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }
}
