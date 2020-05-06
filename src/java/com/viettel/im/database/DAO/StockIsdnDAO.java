package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.NewStockIsdnForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.IsdnTrans;
import com.viettel.im.database.BO.IsdnTransDetail;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author TungTV
 * modified by tamdt1
 *
 */

public class StockIsdnDAO extends BaseDAOAction {
    private static final Logger log = Logger.getLogger(StockIsdnDAO.class);

    private final Integer HASHMAP_KEY_NUMBER_SUCCESS_RECORD = 1; //key hashMap (so luong ban ghi import thanh cong)
    private final Integer HASHMAP_KEY_TOTAL_RECORD = 2; //key hashMap (so luong ban ghi bi loi)
    private final Integer HASHMAP_KEY_RESULT_SUCCESS = 5; //key hashMap (ket qua goi ham co thanh cong hay khog)
    private final Integer HASHMAP_KEY_ERROR_MESSAGE = 6; //key hashMap (thong bao loi)

    //
    private final String REQUEST_MESSAGE = "message"; //ten bien request
    private final String REQUEST_MESSAGE_PARAM = "messageParam"; //ten bien request
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType"; //ten bien request
    private final String REQUEST_LIST_LOCATION = "listLocation"; //ten bien request
    private final String SESSION_IMPORT_HISTORY_LIST= "importHistoryList"; //ten bien session, danh sach cac dai so da tao

    //cac hang so bien pageForward
    private String pageForward;
    private final String CREATE_STOCK_ISDN = "createStockIsdn";
    private final String CREATE_STOCK_ISDN_SEARCH_RESULT = "createStockIsdnSearchResult";

    //bien form
    private NewStockIsdnForm newStockIsdnForm = new NewStockIsdnForm();

    public NewStockIsdnForm getNewStockIsdnForm() {
        return newStockIsdnForm;
    }

    public void setNewStockIsdnForm(NewStockIsdnForm newStockIsdnForm) {
        this.newStockIsdnForm = newStockIsdnForm;
    }

    /**
     * 
     * author   : tamdt1
     * date     : 16/11/2009
     * purpose  : chuan bi form tao dai so moi
     *
     */
    public String preparePage() throws Exception {
        log.info("begin preparePage of StockIsdnDAO...");

        HttpServletRequest req = getRequest();

        //lay danh sach lich su tao dai so
        IsdnTransDetailDAO isdnTransDetailDAO = new IsdnTransDetailDAO();
        isdnTransDetailDAO.setSession(this.getSession());
        List<IsdnTransDetail> listIsdnTransDetail = isdnTransDetailDAO.getListCreatedIsdn(null, null);
        req.getSession().setAttribute(SESSION_IMPORT_HISTORY_LIST, listIsdnTransDetail);

        //lay du lieu can thiet cho combobox
        getDataForCombobox();

        pageForward = CREATE_STOCK_ISDN;
        log.info("end preparePage of StockIsdnDAO");
        return pageForward;
    }

    /**
     * 
     * author   : tamdt1
     * date     : 20/08/2009
     * purpose  : lay cac du lieu can thiet cho combobox
     * 
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        //loai dich vu
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(getSession());
        List<StockType> listStockType = stockTypeDAO
                .getListStockTypeMHP(Constant.STOCK_ISDN_MOBILE, Constant.STOCK_ISDN_HOMEPHONE, Constant.STOCK_ISDN_PSTN);
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, listStockType);

        //danh sach cac tinh/ tp
        String serviceType = this.newStockIsdnForm.getServiceType();
        if (serviceType != null && !serviceType.equals("")) {
            Long lServiceType = Long.parseLong(serviceType);
            if (lServiceType.equals(Constant.STOCK_ISDN_HOMEPHONE) || lServiceType.equals(Constant.STOCK_ISDN_PSTN)) {
                AreaDAO areaDAO = new AreaDAO();
                areaDAO.setSession(getSession());
                List<Area> areaList = areaDAO.findAllProvince();
                req.setAttribute(REQUEST_LIST_LOCATION, areaList);
            }
        }
    }

    /**
     *
     * xu ly su kien khi loai dich vu thay doi
     *
     */
    public String changeServiceType() throws Exception {
        log.info("begin changeServiceType of StockIsdnDAO...");

        HttpServletRequest req = getRequest();

        String strServiceType = this.newStockIsdnForm.getServiceType();
        Long serviceType;
        try {
            serviceType = Long.parseLong(strServiceType.trim());
        } catch (Exception ex) {
            serviceType = null;
        }

        //lay danh sach lich su tao dai so
        IsdnTransDetailDAO isdnTransDetailDAO = new IsdnTransDetailDAO();
        isdnTransDetailDAO.setSession(this.getSession());
        List<IsdnTransDetail> listIsdnTransDetail = isdnTransDetailDAO.getListCreatedIsdn(serviceType, null);
        req.getSession().setAttribute(SESSION_IMPORT_HISTORY_LIST, listIsdnTransDetail);

        //lay du lieu can thiet cho combobox
        getDataForCombobox();

        log.info("end changeServiceType of StockIsdnDAO");
        pageForward = CREATE_STOCK_ISDN;
        return pageForward;
    }
    
    /**
     *
     * xu ly su kien thay doi dia ban
     *
     */
    public String changeProvince() throws Exception {
        log.info("begin changeProvince of StockIsdnDAO");

        HttpServletRequest req = getRequest();

        String location = this.newStockIsdnForm.getLocation();
        String strServiceType = this.newStockIsdnForm.getServiceType();

        if (location == null || location.trim().equals("") ||
                strServiceType == null || strServiceType.trim().equals("")) {

            //
            getDataForCombobox();

            pageForward = CREATE_STOCK_ISDN;
            log.info("end changeProvince of StockIsdnDAO");
            return pageForward;
        }
        
        Long serviceType = Long.valueOf(strServiceType);
        if (serviceType.equals(Constant.STOCK_ISDN_HOMEPHONE) || serviceType.equals(Constant.STOCK_ISDN_PSTN)) {
            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(getSession());
            List tempAreaList = areaDAO.findByProperty("areaCode", location.trim());
            String isdnProvinceHeader = "";
            if (tempAreaList != null && tempAreaList.size() != 0) {
                Area area = (Area) tempAreaList.get(0);
                isdnProvinceHeader = area.getPstnCode();
            }

            //lay danh sach lich su tao dai so
            IsdnTransDetailDAO isdnTransDetailDAO = new IsdnTransDetailDAO();
            isdnTransDetailDAO.setSession(this.getSession());
            List<IsdnTransDetail> listIsdnTransDetail = isdnTransDetailDAO.getListCreatedIsdn(serviceType, isdnProvinceHeader);
            req.getSession().setAttribute(SESSION_IMPORT_HISTORY_LIST, listIsdnTransDetail);

            this.newStockIsdnForm.setStockPstnDistrict(isdnProvinceHeader);
        }

        //
        getDataForCombobox();

        pageForward = CREATE_STOCK_ISDN;
        log.info("end changeProvince of StockIsdnDAO");
        
        return pageForward;
        
    }

    /**
     * 
     * author   : tamdt1
     * date     : 13/11/2009
     * purpose  : tao dai so moi
     * 
     */
    public String createStockIsdn() throws Exception {
        log.debug("begin method createStockIsdn of StockIsdnDAO...");

        HttpServletRequest req = getRequest();

        if(!checkValidIsdnRangeToCreate()) {
            //lay du lieu cho combobox
            getDataForCombobox();

            //
            pageForward = CREATE_STOCK_ISDN;
            log.debug("end method inputStockIsdn of StockIsdnDAO");
            return pageForward;
        }

        //import dai so vao DB
        List<String> listMessage = new ArrayList<String>();
        StockIsdnDAO.listProgressMessage.put(req.getSession().getId(), listMessage);

        HashMap result = new HashMap();
        Long stockTypeId = Long.valueOf(this.newStockIsdnForm.getServiceType());
        Long fromIsdn = Long.valueOf(this.newStockIsdnForm.getStartStockIsdn());
        Long toIsdn = Long.valueOf(this.newStockIsdnForm.getEndStockIsdn());
        result = importDataByIsdnRange(stockTypeId, fromIsdn, toIsdn);
        Boolean bResult = (Boolean)result.get(HASHMAP_KEY_RESULT_SUCCESS);
        if (bResult != null && bResult.equals(true)) {
            req.setAttribute(REQUEST_MESSAGE, "Đã tạo thành công " +
                result.get(HASHMAP_KEY_NUMBER_SUCCESS_RECORD) + "/ " +
                result.get(HASHMAP_KEY_TOTAL_RECORD) + " số isdn");
            
        } else {
            //co loi xay ra trong qua trinh import du lieu
            req.setAttribute(REQUEST_MESSAGE, result.get(HASHMAP_KEY_ERROR_MESSAGE));

            //lay du lieu cho combobox
            getDataForCombobox();

            //
            pageForward = CREATE_STOCK_ISDN;
            log.debug("end method inputStockIsdn of StockIsdnDAO");
            return pageForward;
        }

        //reset form
        //this.newStockIsdnForm.setQuantityIsdn(null);
        this.newStockIsdnForm.setStartStockIsdn("");
        this.newStockIsdnForm.setEndStockIsdn("");

        //lay danh sach lich su tao dai so
        IsdnTransDetailDAO isdnTransDetailDAO = new IsdnTransDetailDAO();
        isdnTransDetailDAO.setSession(this.getSession());
        List<IsdnTransDetail> listIsdnTransDetail = isdnTransDetailDAO.getListCreatedIsdn(stockTypeId, null);
        req.getSession().setAttribute(SESSION_IMPORT_HISTORY_LIST, listIsdnTransDetail);

        //lay du lieu cho combobox
        getDataForCombobox();

        //
        pageForward = CREATE_STOCK_ISDN;
        log.debug("end method inputStockIsdn of StockIsdnDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 13/11/2009
     * purpose  : kiem tra tinh hop le cua dai so truoc khi tao dai so moi
     *
     */
    private boolean checkValidIsdnRangeToCreate() {
        HttpServletRequest req = getRequest();

        String strServiceType = this.newStockIsdnForm.getServiceType();
        String strStartStockIsdn = this.newStockIsdnForm.getStartStockIsdn();
        String strEndStockIsdn = this.newStockIsdnForm.getEndStockIsdn();

        //kiem tra cac truong ko duoc de trong
        if(strServiceType == null || strServiceType.trim().equals("") ||
                strStartStockIsdn == null || strStartStockIsdn.trim().equals("") ||
                strEndStockIsdn == null || strEndStockIsdn.trim().equals("")) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createStockIsdn.error.requiredFieldsEmpty");
            return false;
        }

        Long stockTypeId = Long.parseLong(strServiceType);
        String pstnCode = "";
        if (stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE) || stockTypeId.equals(Constant.STOCK_ISDN_PSTN)) {
            String areaCode = this.newStockIsdnForm.getLocation();
            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(getSession());
            List<Area> listArea = areaDAO.findByProperty("areaCode", areaCode);
            if (listArea != null && listArea.size() > 0) {
                    Area area = listArea.get(0);
                    pstnCode = area.getPstnCode() != null ? area.getPstnCode().trim() : "";
            }
        }

        String tempStartStockIsdn = pstnCode + this.newStockIsdnForm.getStartStockIsdn().trim();
        String tempEndStockIsdn = pstnCode + this.newStockIsdnForm.getEndStockIsdn().trim();

        if (tempStartStockIsdn.length() != tempEndStockIsdn.length()) {
            //do dai so bat dau va so ket thuc ko bang nhau
            req.setAttribute(REQUEST_MESSAGE, "createStockIsdn.error.startAndEndIsdnDifferent");
            return false;
        }

        Long startStockIsdn;
        Long endStockIsdn;

        try {
            startStockIsdn = Long.valueOf(tempStartStockIsdn.trim());
            endStockIsdn = Long.valueOf(tempEndStockIsdn.trim());
        } catch (Exception ex) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createStockIsdn.error.isdnNegative");
            return false;
        }

        //kiem tra so duong cua so dau va so ket thuc
        if (startStockIsdn.compareTo(0L) <= 0 || endStockIsdn.compareTo(0L) <= 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createStockIsdn.error.isdnNegative");
            return false;
        }

        //kiem tra so cuoi dai > so dau dai
        if (startStockIsdn.compareTo(endStockIsdn) > 0) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createStockIsdn.error.invalidIsdn");
            return false;
        }

        Long maxIsdnImport = -1L;
        try {
            String strMaxIsdnImport = ResourceBundleUtils.getResource("MAX_ISDN_IMPORT");
            maxIsdnImport = Long.valueOf(strMaxIsdnImport);
        } catch (Exception ex) {
            ex.printStackTrace();
            maxIsdnImport = -1L;
        }

        if ((endStockIsdn - startStockIsdn + 1) > maxIsdnImport) {
            //
            req.setAttribute(REQUEST_MESSAGE, "createStockIsdn.error.quantityOverMaximum");
            List listParam = new ArrayList();
            listParam.add(maxIsdnImport);
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);

            return false;
        }

        //kiem tra su ton tai cua dai so
        try {
            List<Long> listStockTypeId = new ArrayList<Long>();
            if(stockTypeId.equals(Constant.STOCK_ISDN_MOBILE)) {
                listStockTypeId.add(Constant.STOCK_ISDN_MOBILE);
            } else if(stockTypeId.equals(Constant.STOCK_ISDN_HOMEPHONE) || stockTypeId.equals(Constant.STOCK_ISDN_PSTN)) {
                //truong hop la homphone hoac pstn thi phai kiem tra ca 2 bang (khong co su trung lap o ca 2 bang)
                listStockTypeId.add(Constant.STOCK_ISDN_HOMEPHONE);
                listStockTypeId.add(Constant.STOCK_ISDN_PSTN);
            }

            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("select count(a.isdnTransDetailId) from IsdnTransDetail a, IsdnTrans b ");
            strQuery.append("where 1 = 1 ");
            strQuery.append("and ((to_number(a.fromIsdn) <= :startStockIsdn and to_number(a.toIsdn) >= :startStockIsdn) "); //startStockIsdn roi vao khoang da ton tai
            strQuery.append("or (to_number(a.fromIsdn) <= :endStockIsdn and to_number(a.toIsdn) >= :endStockIsdn) "); //endStockIsdn roi vao khoang da ton tai
            strQuery.append("or (to_number(a.fromIsdn) >= :startStockIsdn and to_number(a.toIsdn) <= :endStockIsdn)) "); //start - endStockIsdn bao phu cac khoang da ton tai
            strQuery.append("and a.isdnTransId = b.isdnTransId ");
            strQuery.append("and b.stockTypeId in (:listStockTypeId)");

            Query query = getSession().createQuery(strQuery.toString());
            query.setParameter("startStockIsdn", startStockIsdn);
            query.setParameter("endStockIsdn", endStockIsdn);
            query.setParameterList("listStockTypeId", listStockTypeId);

            Long count = 0L;
            List list = query.list();
            if (list != null && list.size() > 0) {
                count = (Long) list.get(0);
            }

            if (count.compareTo(0L) > 0) {
                //
                req.setAttribute(REQUEST_MESSAGE, "createStockIsdn.error.duplicateIsdnRange");
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }

        //
        this.newStockIsdnForm.setServiceType(strServiceType.trim());
        this.newStockIsdnForm.setStartStockIsdn(tempStartStockIsdn.trim());
        this.newStockIsdnForm.setEndStockIsdn(tempEndStockIsdn.trim());

        return true;
    }

    
    /**
     * Input Stock History Page Navigator
     * @param form
     * @param req
     * @return
     * @throws java.lang.Exception
     */
    public String pageNavigator() throws Exception {
        log.info("begin importDataBySerialRange of StockIsdnDAO");
        
        String forwardPage = CREATE_STOCK_ISDN_SEARCH_RESULT;
        
        log.info("end importDataBySerialRange of StockIsdnDAO");
        
        return forwardPage;
    }


    /**
     *
     * author tamdt1
     * date: 25/04/2009
     * muc dich: import du lieu theo dai isdn
     * dau vao:
     *              - mat bang can insert du lieu, isdn dau, isdn cuoi
     * dau ra:
     *              - so luong ban ghi duoc insert thanh cong/ tong so ban ghi duoc insert
     */
    private HashMap importDataByIsdnRange(Long stockTypeId, Long fromIsdn, Long toIsdn) {
        log.info("Begin importDataBySerialRange of StockIsdnDAO");

        HashMap result = new HashMap();
        int NUMBER_CMD_IN_BATCH = 5000; //so luong ban ghi se commit 1 lan
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        String strTableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        if (userToken.getShopId() == null ||
                fromIsdn == null || toIsdn == null ||
                strTableName == null || strTableName.trim().equals("")) {
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            result.put(HASHMAP_KEY_ERROR_MESSAGE, "!!Lỗi. Truyền tham số hàm importDataByIsdnRange");
            return result;
        }

        //connect toi DB, tao cau lenh insert
        try {
            Session session = getSession();
            Connection conn = null;
            PreparedStatement stmInsert = null;
            String userSessionId = req.getSession().getId();
            List<String> listMessage = StockIsdnDAO.listProgressMessage.get(userSessionId);
            String message = "";

            //----------------------------------------------------------------
            //ket noi toi CSDL
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu kết nối tới CSDL";
            listMessage.add(message);
            System.out.println(message);
            conn = session.connection();

            //tao cau lenh insert
            StringBuffer strInsertQuery = new StringBuffer();
            strInsertQuery.append("insert into ");
            strInsertQuery.append(strTableName);

            //them update truong isdnType doi voi truong hop la so pstn
            if (strTableName.equals("STOCK_ISDN_PSTN")) {
                strInsertQuery.append("(ID, OWNER_TYPE, OWNER_ID, STATUS, CREATE_DATE, ISDN, USER_SESSION_ID, LAST_UPDATE_USER, LAST_UPDATE_IP_ADDRESS, LAST_UPDATE_TIME, LAST_UPDATE_KEY, ISDN_TYPE ");
                strInsertQuery.append(") values (" + strTableName + "_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
            } else {
                strInsertQuery.append("(ID, OWNER_TYPE, OWNER_ID, STATUS, CREATE_DATE, ISDN, USER_SESSION_ID,LAST_UPDATE_USER, LAST_UPDATE_IP_ADDRESS, LAST_UPDATE_TIME, LAST_UPDATE_KEY ");
                strInsertQuery.append(") values (" + strTableName + "_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
            }
            strInsertQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log
            stmInsert = conn.prepareStatement(strInsertQuery.toString());

            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " kết nối tới CSDL thành công";
            listMessage.add(message);
            System.out.println(message);

            //----------------------------------------------------------------
            //luu thong tin dai so can tao isdnTrans va isdnTransDetail
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu lưu thông tin dải số cần tạo";
            listMessage.add(message);
            System.out.println(message);

            IsdnTrans isdnTrans = new IsdnTrans();
            Long isdnTransId = getSequence("ISDN_TRANS_SEQ");
            isdnTrans.setIsdnTransId(isdnTransId);
            isdnTrans.setStockTypeId(stockTypeId);
            isdnTrans.setTransType(Constant.ISDN_TRANS_TYPE_CREATE);
            isdnTrans.setLastUpdateUser(userToken.getLoginName() + "-" + req.getRemoteHost());
            isdnTrans.setLastUpdateIpAddress(req.getRemoteAddr());
            isdnTrans.setLastUpdateTime(DateTimeUtils.getSysDate());
            session.save(isdnTrans);

            IsdnTransDetail isdnTransDetail = new IsdnTransDetail();
            Long isdnTransDetailId = getSequence("ISDN_TRANS_DETAIL_SEQ");
            isdnTransDetail.setIsdnTransDetailId(isdnTransDetailId);
            isdnTransDetail.setIsdnTransId(isdnTransId);
            isdnTransDetail.setFromIsdn(fromIsdn.toString());
            isdnTransDetail.setQuantity(0L);
            session.save(isdnTransDetail);
            session.flush();

            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " kết thúc lưu thông tin dải số cần tạo";
            listMessage.add(message);
            System.out.println(message);

            //----------------------------------------------------------------
            //import du lieu tu fromIsdn toi toIsdn theo batch vao db
            java.sql.Date now = new java.sql.Date(new Date().getTime());
            Long isdnQuantity = toIsdn - fromIsdn + 1;
            Long numberBatch = isdnQuantity / NUMBER_CMD_IN_BATCH; //so luong batch
            String strToIsdnInBatch = "";
            for (long i = 0; i < numberBatch; i++) {
                try {
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu lưu dữ liệu vào CSDL - lô " + i;
                    listMessage.add(message);
                    System.out.println(message);

                    for (long j = 0; j < NUMBER_CMD_IN_BATCH; j++) {
                        Long currentIsdn = fromIsdn + i * NUMBER_CMD_IN_BATCH + j;
                        stmInsert.setLong(1, Constant.OWNER_TYPE_SHOP); //thiet lap truong ownerType
                        stmInsert.setLong(2, userToken.getShopId()); //thiet lap truong ownerId
                        stmInsert.setLong(3, Constant.STATUS_USE); //thiet lap truong status
                        stmInsert.setDate(4, now); //thiet lap truong createDate
                        stmInsert.setLong(5, currentIsdn); //isdn can import
                        stmInsert.setString(6, userSessionId); //session id cua user dang nhap (phuc vu viec err log)
                        stmInsert.setString(7, userToken.getLoginName()); //last_update_user
                        stmInsert.setString(8, req.getRemoteAddr()); //LAST_UPDATE_IP_ADDRESS
                        stmInsert.setDate(9, new java.sql.Date(DateTimeUtils.getSysDate().getTime())); //LAST_UPDATE_TIME
                        stmInsert.setString(10, Constant.LAST_UPDATE_KEY); //LAST_UPDATE_KEY
                        if (strTableName.equals("STOCK_ISDN_PSTN")) {
                            stmInsert.setString(11, Constant.ISDN_TYPE_POST);
                        }

                        stmInsert.addBatch();

                        strToIsdnInBatch = String.valueOf(currentIsdn);
                    }
                    
                    //commit batch vao db
                    conn.setAutoCommit(false);
                    int[] updateCount = stmInsert.executeBatch();

                    //cap nhat vao bang isdnTransDetail
                    isdnTransDetail.setToIsdn(strToIsdnInBatch);
                    session.update(isdnTransDetail);

                    conn.commit();
//                    conn.setAutoCommit(true);tronglv comment 120604

                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " kết thúc lưu dữ liệu vào CSDL - lô " + i;
                    listMessage.add(message);
                    System.out.println(message);

                } catch (BatchUpdateException b) {
                    message = "<font color='red'>!!!Lỗi. " + simpleDateFormat.format(DateTimeUtils.getSysDate());
                    message += b.getMessage();
                    message += "</font>";
                    listMessage.add(message);
                    System.out.println(message);

                    b.printStackTrace();

                } catch (SQLException ex) {
                    message = "<font color='red'>!!!Lỗi. " + simpleDateFormat.format(DateTimeUtils.getSysDate());
                    message += ex.getMessage();
                    message += "</font>";
                    listMessage.add(message);
                    System.out.println(message);

                    ex.printStackTrace();

                } catch (Exception ex) {
                    message = "<font color='red'>!!!Lỗi. " + simpleDateFormat.format(DateTimeUtils.getSysDate());
                    message += ex.getMessage();
                    message += "</font>";
                    listMessage.add(message);
                    System.out.println(message);

                    ex.printStackTrace();

                }
            }

            //chen so luong ban ghi le con lai
            try {
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " bắt đầu lưu dữ liệu vào CSDL - lô lẻ cuối cùng";
                listMessage.add(message);
                System.out.println(message);

                for (long currentIsdn = fromIsdn + numberBatch * NUMBER_CMD_IN_BATCH; currentIsdn <= toIsdn; currentIsdn++) {
                    stmInsert.setLong(1, Constant.OWNER_TYPE_SHOP); //thiet lap truong ownerType
                    stmInsert.setLong(2, userToken.getShopId()); //thiet lap truong ownerId
                    stmInsert.setLong(3, Constant.STATUS_USE); //thiet lap truong status
                    stmInsert.setDate(4, now); //thiet lap truong createDate
                    stmInsert.setLong(5, currentIsdn); //isdn can import
                    stmInsert.setString(6, userSessionId); //session id cua user dang nhap (phuc vu viec err log)
                    stmInsert.setString(7, userToken.getLoginName()); //last_update_user
                    stmInsert.setString(8, req.getRemoteAddr()); //LAST_UPDATE_IP_ADDRESS
                    stmInsert.setDate(9, new java.sql.Date(DateTimeUtils.getSysDate().getTime())); //LAST_UPDATE_TIME
                    stmInsert.setString(10, Constant.LAST_UPDATE_KEY); //LAST_UPDATE_KEY
                    if (strTableName.equals("STOCK_ISDN_PSTN")) {
                        stmInsert.setString(11, Constant.ISDN_TYPE_POST);
                    }

                    stmInsert.addBatch();

                    strToIsdnInBatch = String.valueOf(currentIsdn);

                }

                //commit batch vao db
                conn.setAutoCommit(false);
                int[] updateCount = stmInsert.executeBatch();

                //cap nhat vao bang isdnTransDetail
                isdnTransDetail.setToIsdn(strToIsdnInBatch);
                session.update(isdnTransDetail);

                conn.commit();
//                conn.setAutoCommit(true);tronglv comment 120604

                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " kết thúc lưu dữ liệu vào CSDL - lô lẻ cuối cùng";
                listMessage.add(message);
                System.out.println(message);


            } catch (BatchUpdateException ex) {
                message = "<font color='red'>!!!Lỗi. " + simpleDateFormat.format(DateTimeUtils.getSysDate());
                message += ex.getMessage();
                message += "</font>";
                listMessage.add(message);
                System.out.println(message);

                ex.printStackTrace();

            } catch (SQLException ex) {
                message = "<font color='red'>!!!Lỗi. " + simpleDateFormat.format(DateTimeUtils.getSysDate());
                message += ex.getMessage();
                message += "</font>";
                listMessage.add(message);
                System.out.println(message);

                ex.printStackTrace();

            } catch (Exception ex) {
                message = "<font color='red'>!!!Lỗi. " + simpleDateFormat.format(DateTimeUtils.getSysDate());
                message += ex.getMessage();
                message += "</font>";
                listMessage.add(message);
                System.out.println(message);

                ex.printStackTrace();

            }

            Long numberErrorRecord = moveDataToErrorTable(strTableName, conn, userSessionId);
            Long numberSuccessRecord = isdnQuantity - numberErrorRecord;

            //cap nhat vao bang isdnTransDetail
            conn.setAutoCommit(false);
            isdnTransDetail.setQuantity(numberSuccessRecord);
            session.update(isdnTransDetail);
            conn.commit();
//            conn.setAutoCommit(true);tronglv comment 120604

            if (numberSuccessRecord.compareTo(0L) > 0) {
                result.put(HASHMAP_KEY_NUMBER_SUCCESS_RECORD, numberSuccessRecord);
                result.put(HASHMAP_KEY_TOTAL_RECORD, isdnQuantity);
                result.put(HASHMAP_KEY_RESULT_SUCCESS, true);
            } else {
                result.put(HASHMAP_KEY_ERROR_MESSAGE, "!!!Lỗi. Tạo dải số không thành công");
                result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            }

            //
            listMessage.clear();

        } catch (Exception ex) {
            ex.printStackTrace();

            result.put(HASHMAP_KEY_ERROR_MESSAGE, "!!!Lỗi. Insert dữ liệu không thành công - " + ex.toString());
            result.put(HASHMAP_KEY_RESULT_SUCCESS, false);
            return result;
        }

        log.info("Begin importDataBySerialRange of StockIsdnDAO");
        return result;
    }

    /**
     *
     * author tamdt1
     * date: 27/04/2009
     * di chuyen du lieu tu bang ERR$_ sang bang ERR_, xoa du lieu trong bang ERR$_
     *
     */
    private Long moveDataToErrorTable(String strTableName, Connection conn, String userSessionId) {
//        int numberEffectedRow = 0;
        try {
            PreparedStatement stmInsert = null;
            StringBuffer strInsertQuery;

//            //tao cau lenh chuyen du lieu tu bang ERR$_ sang bang ERR_
//            strInsertQuery = new StringBuffer();
//            strInsertQuery.append("insert into ERR_");
//            strInsertQuery.append(strTableName);
//            strInsertQuery.append(" value (select a.*, '0' as TMP_STATUS from ERR$_");
//            strInsertQuery.append(strTableName);
//            strInsertQuery.append(" a where USER_SESSION_ID = '" + userSessionId + "')");
//            stmInsert = conn.prepareStatement(strInsertQuery.toString());
//            numberEffectedRow = stmInsert.executeUpdate();
//            System.out.println("end copy error log - " + new java.util.Date());


            //String strQuery = "select count(*) from ERR$_" + strTableName + " where USER_SESSION_ID = '" + userSessionId + "'";

            /**
             * @Modify by hieptd
             */
            String strQuery = "select count(*) from ERR$_" + strTableName + " where USER_SESSION_ID = ?";

            Query query = getSession().createSQLQuery(strQuery);
            query.setString(0, userSessionId);

            Long count = Long.valueOf(query.list().get(0).toString());

            //xoa du lieu trong bang ERR$_
            System.out.print("start delete error$_ table - " + new java.util.Date());
            strInsertQuery = new StringBuffer();
            strInsertQuery.append("delete from ERR$_");
            strInsertQuery.append(strTableName);
            //strInsertQuery.append(" where USER_SESSION_ID = '" + userSessionId + "'");
            strInsertQuery.append(" where USER_SESSION_ID = ?");
            stmInsert = conn.prepareStatement(strInsertQuery.toString());
            stmInsert.setString(1, userSessionId);//TruongNQ5
            stmInsert.execute();
            System.out.println("end delete error$_ table " + new java.util.Date());

            return count;

        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }

        return 0L;
    }

    private Map listItems = new HashMap();

    public Map getListItems() {
        return listItems;
    }

    public void setListItems(Map listItems) {
        this.listItems = listItems;
    }

    //hang so phuc vu viec hien thi progress
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>(); //

    /**
     *
     * author tamdt1
     * date: 14/11/2009
     * tra ve du lieu cap nhat cho divProgress
     *
     */

    public String updateProgressDiv(HttpServletRequest req) {
        log.info("Begin updateProgressDiv of StockIsdnDAO");

        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = StockIsdnDAO.listProgressMessage.get(userSessionId);
            if (listMessage != null && listMessage.size() > 0) {
                String message = listMessage.get(0);
                listMessage.remove(0);
                return message;
            } else {
                return "";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
