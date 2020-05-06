/**
 *  activeKit
 *  @author: kdvt_tronglv
 *  @version: 1.0
 *  @since: 1.0
 */


package com.viettel.im.database.DAO;

import com.viettel.common.ExchMsg;
import com.viettel.common.ObjectClientChannel;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ActiveKitForm;
import com.viettel.im.client.form.AssignStockModelForIsdnForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockKitSale;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import java.util.HashMap;
import java.util.Map;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.BaseStock;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.database.BO.GroupFilterRule;
import com.viettel.im.database.BO.IsdnFilterRules;
import com.viettel.im.database.BO.ReqActiveKit;
import com.viettel.im.client.bean.StockKitSaleBean;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import com.viettel.im.database.BO.IsdnTrans;
import com.viettel.im.database.BO.IsdnTransDetail;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import java.util.Map;
import javax.servlet.http.HttpSession;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author kdvt_tronglv
 */
public class ReqActiveKitDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReqActiveKitDAO.class);
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_PATH = "detailIsdnRangePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE = "detailIsdnRangeMessage";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String REQUEST_IS_FILE_PREVIEW_MODE = "isFilePreviewMode";
    private final String LIST_KIT_SALE = "lstKitSale";
    //cac hang so forward
    private String pageForward;
    private final String ACTIVE_KIT = "activeKit";
    //
    private Long IMP_BY_RANGE = 1L;
    private Long IMP_BY_FILE = 2L;
    private int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi se commit 1 lan
    public static final String SPLITER = " ";
    //Cac thuoc tinh cua lop
    public static ObjectClientChannel channel;
    private List<String> repMsgs = new ArrayList<String>(); // ket qua tong dai tra ve
//    static {
//        try {
//            if (channel == null) {
//                channel = new ObjectClientChannel(
//                        ResourceBundleUtils.getResource("PRO_ACT_URL"),
//                        Integer.parseInt(ResourceBundleUtils.getResource("PRO_ACT_PORT")),
//                        ResourceBundleUtils.getResource("PRO_ACT_USER"),
//                        ResourceBundleUtils.getResource("PRO_ACT_PASS"), true);
//                //Thoi gian timeout
//                channel.setReceiverTimeout(260000); //10 phut
//            } //Ket noi
//            channel.reconnect();
//        } catch (IOException ex) {
////            log.getLogger(ReqActiveKitDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    //bien form
    private ActiveKitForm activeKitForm = new ActiveKitForm();

    public ActiveKitForm getActiveKitForm() {
        return activeKitForm;
    }

    public void setActiveKitForm(ActiveKitForm activeKitForm) {
        this.activeKitForm = activeKitForm;
    }

    public void saveReqActiveKit(String fromSerial, String toSerial, Long saleType, Long transId, Long saleTransType, Date saleTransDate) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            ReqActiveKit reqActiveKit = new ReqActiveKit();
            reqActiveKit.setReqId(getSequence("REQ_ID_SEQ"));
            reqActiveKit.setSaleTransId(transId);
            reqActiveKit.setFromSerial(fromSerial);
            reqActiveKit.setToSerial(toSerial);
            reqActiveKit.setShopId(userToken.getShopId());
            reqActiveKit.setStaffId(userToken.getUserID());
            reqActiveKit.setSaleTransDate(saleTransDate);
            reqActiveKit.setCreateDate(getSysdate());
            reqActiveKit.setSaleType(saleType);
            reqActiveKit.setSaleTransType(saleTransType);
            reqActiveKit.setProcessStatus(0L);
            getSession().save(reqActiveKit);

            try {
                /*
                String SQL_INSERT_KIT_SALE = " INSERT INTO "
                        + " STOCK_KIT_SALE (ID, SUB_ID ,SALE_TRANS_ID, SALE_ACTION_TYPE ,ISDN ,SERIAL ,STATUS ,"
                        + " CREATE_DATE ,LAST_UPDATE_TIME ,LAST_UPDATE_USER ,PROCESS_COUNT, OWNER_ID, OWNER_TYPE) "
                        + " "
                        + " SELECT STOCK_KIT_SALE_SEQ.NEXTVAL, NULL, " + transId.toString() + ", " + saleType.toString() + ", ISDN, SERIAL, 0, "
                        + " SYSDATE, SYSDATE, '" + userToken.getLoginName() + "', 0, OWNER_ID, OWNER_TYPE  "
                        + " "
                        + " FROM STOCK_KIT where 1=1 AND TO_NUMBER(SERIAL) >= " + fromSerial + " AND TO_NUMBER(SERIAL)<= " + toSerial;
                */
                String SQL_INSERT_KIT_SALE = " INSERT INTO "
                        + " STOCK_KIT_SALE (ID, SUB_ID ,SALE_TRANS_ID, SALE_ACTION_TYPE ,ISDN ,SERIAL ,STATUS ,"
                        + " CREATE_DATE ,LAST_UPDATE_TIME ,LAST_UPDATE_USER ,PROCESS_COUNT, OWNER_ID, OWNER_TYPE) "
                        + " "
                        + " SELECT STOCK_KIT_SALE_SEQ.NEXTVAL, NULL, ?, ?, ISDN, SERIAL, 0, "
                        + " SYSDATE, SYSDATE, ?, 0, OWNER_ID, OWNER_TYPE  "
                        + " "
                        + " FROM STOCK_KIT where 1=1 AND TO_NUMBER(SERIAL) >= ? AND TO_NUMBER(SERIAL)<= ?";

                Query q = getSession().createSQLQuery(SQL_INSERT_KIT_SALE);

                q.setParameter(0, transId.toString());
                q.setParameter(1, saleType.toString());
                q.setParameter(2, userToken.getLoginName());
                q.setParameter(3, fromSerial);
                q.setParameter(4, toSerial);

                int result = q.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("INSERT INTO STOCK_KIT_SALE - ", ex);
            }

        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     *
     * author   : tutm1
     * date     : 17/11/2011
     * desc     : chuan bi form de kich hoat KIT
     *
     */
    public String preparePage() throws Exception {
        log.debug("begin method preparePage of AssignChannelTypeForSerialDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //resetForm
            this.activeKitForm.resetForm();

            //lay du lieu cho combobox

            //set kho mac dinh la kho hien tai
            this.activeKitForm.setShopId(userToken.getShopId());
            this.activeKitForm.setShopCode(userToken.getShopCode());
            this.activeKitForm.setShopName(userToken.getShopName());
            this.activeKitForm.setStaffCode(userToken.getLoginName());
            this.activeKitForm.setStaffName(userToken.getFullName());

        } catch (Exception ex) {
            //rollback
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        //return
        pageForward = ACTIVE_KIT;
        log.debug("end method preparePage of AssignChannelTypeForSerialDAO");
        return pageForward;
    }

    /**
     *
     * author   : tutm1
     * date     : 19/11/2011
     * desc     : xem truoc noi dung file import
     *
     */
    public String filePreview() throws Exception {
        log.info("Begin filePreview of ReqActiveKitDAO");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
            String serverFileName = this.activeKitForm.getServerFileName();
            serverFileName = getSafeFileName(serverFileName);
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow - 1; //tru di dong dau tien
            List<StockKitSaleBean> listRowInFile = new ArrayList<StockKitSaleBean>();
            numberRow = numberRow > 1000 ? 1000 : numberRow; //truong hop file > 1000 row -> chi hien thi 1000 row dau tien

            // dinh nghia loi du lieu trong file excel
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ACTIVE.KIT.SERIAL.EMPTY", getText("ACTIVE.KIT.SERIAL.EMPTY"));
            hashMapError.put("ERR.STK.126", getText("ERR.STK.126")); //!!!Lỗi. Cột serial khong phai la so nguyen duong


            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet
                String tmpStrSerial = sheet.getCell(0, rowIndex).getContents(); //serial

                BigInteger tmpSerial = new BigInteger("-1");
                if (tmpStrSerial == null || tmpStrSerial.trim().equals("")) {
                    StockKitSaleBean errActiveKitBean = new StockKitSaleBean();
                    errActiveKitBean.setSerial(tmpStrSerial);
                    errActiveKitBean.setErrorMessage(hashMapError.get("ACTIVE.KIT.SERIAL.EMPTY")); //!!!Lỗi. Cột serial không được để trống                    
                    listRowInFile.add(errActiveKitBean);
                    continue;
                } else {
                    tmpStrSerial = tmpStrSerial.trim();
                    try {
                        tmpSerial = new BigInteger(tmpStrSerial);
                    } catch (NumberFormatException ex) {
                        //serial khong dung dinh dang
                        StockKitSaleBean errActiveKitBean = new StockKitSaleBean();
                        errActiveKitBean.setSerial(tmpStrSerial);
                        errActiveKitBean.setErrorMessage(hashMapError.get("ERR.STK.126")); //!!!Lỗi. Cột serial khong phai la so nguyen duong
                        listRowInFile.add(errActiveKitBean);
                        continue;
                    }
                }

                StockKitSaleBean tmpActiveKitBean = new StockKitSaleBean();
                tmpActiveKitBean.setSerial(tmpStrSerial);
                listRowInFile.add(tmpActiveKitBean);
            }

            //day len bien session
            req.setAttribute(LIST_KIT_SALE, listRowInFile);
            req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ACTIVE_KIT;
        log.info("End filePreview of ReqActiveKitDAO");
        return pageForward;

    }

    /**
     * tutm1: tim kiem dai serial bo kit trong StockKitSale
     * @return
     * @throws Exception
     */
    public String searchKitSale() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String fromSerial = this.activeKitForm.getFromSerial().trim();
        String toSerial = this.activeKitForm.getToSerial().trim();
        String shopIdUser = userToken.getShopId().toString();
        List<StockKitSaleBean> lstKitSale = null;


        //Kiem tra dieu kien dai Serial
        if (fromSerial == null || "".equals(fromSerial) || toSerial == null || "".equals(toSerial)) {
            req.setAttribute(REQUEST_MESSAGE, "ACTIVE.KIT.SERIAL.INPUT");//Bạn chưa nhập dải Serial
            return ACTIVE_KIT;
        }

        lstKitSale = getListKitSale(activeKitForm.getFromSerial(), activeKitForm.getToSerial());

        if (lstKitSale.size() > 100) {
            req.setAttribute(REQUEST_MESSAGE, "ACTIVE.KIT.RECORD.100");//Có nhiều hơn 100 bản ghi, bạn cần thu hẹp khoảng tìm kiếm
            return ACTIVE_KIT;
        }

        req.setAttribute(LIST_KIT_SALE, lstKitSale);
        activeKitForm.setLstSelectedItem(null);
        return ACTIVE_KIT;
    }

    /**
     * tutm1: lay danh sach serial KIT can kich hoat
     * @param fromSerial
     * @param toSerial
     * @return 
     * @throws Exception
     */
    private List<StockKitSaleBean> getListKitSale(String fromSerial, String toSerial) throws Exception {
        try {

            String strQuery = "select new com.viettel.im.client.bean.StockKitSaleBean(sk.id, "
                    + "sk.subId, sk.saleTransId, sk.isdn, sk.serial, sk.status, sk.createDate, "
                    + " sk.lastUpdateTime, sk.lastUpdateUser, sk.processCount, sk.saleActionType, "
                    + " sk.ownerId, sk.ownerType, sk.errorDescription, sk.saleTransDate, st.saleTransCode) "
                    + " from StockKitSale sk, SaleTrans st where sk.serial >= to_number(?) and sk.serial <= to_number(?) and sk.saleTransId = st.saleTransId ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, fromSerial);
            query.setParameter(1, toSerial);

            List<StockKitSaleBean> lst = query.list();
            return lst;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * @Purpose  Chuc nang kich hoat KIT
     * @throws java.lang.Exception
     */
    public String activeKit() throws Exception {
        //Xuat ket qua
        BufferedWriter bwErr = null;
        HttpServletRequest req = getRequest();
        try {
            String errFileName = "/share/report_out/ActiveKitResult_" + DateTimeUtils.convertDateTimeToString(new Date(), "ddMMyyyy-hhss") + ".txt";
            String desErrFile = req.getRealPath(errFileName);

            String[] selectedItem = activeKitForm.getLstSelectedItem();
            if (selectedItem == null || selectedItem.length == 0) {
                req.setAttribute(REQUEST_MESSAGE, "ACTIVE.KIT.SERIAL.CHOICE");//Chưa chọn dải serial để kích hoạt
                return ACTIVE_KIT;
            }
            if (selectedItem != null && selectedItem.length == 1 && "false".equals(selectedItem[0].toLowerCase())) {
                req.setAttribute(REQUEST_MESSAGE, "ACTIVE.KIT.SERIAL.CHOICE");//Chưa chọn dải serial để kích hoạt
                return ACTIVE_KIT;
            }

            List<Long> rLst = new ArrayList();
            String msgReturn = "";
            Long activeCount = 0L;
            Long destroyCount = 0L;
            Long total = 0L;
            List<StockKitSale> lstReq = getListStockKit(selectedItem);
            if (lstReq != null && lstReq.size() > 0) {
                List<Long> temp = new ArrayList();

                for (StockKitSale item : lstReq) {
                    total++;
                    // KIT da duoc kich hoat tu truoc
                    if (item.getStatus().equals(2L)) {
                        StringBuffer strMsg = new StringBuffer();
                        strMsg.append(item.getIsdn()).append(SPLITER);
                        strMsg.append(item.getSerial()).append(SPLITER);
                        strMsg.append("Null").append(SPLITER);
                        strMsg.append("Already actived before").append(SPLITER);
                        repMsgs.add(strMsg.toString());
                    } else {
                        // kich hoat/huy tung isdn cua  bo KIT
                        // giao dich ban SALE_TYPE = 1 => kich hoat
                        // giao dich huy SALE_TYPE = 2 => chan
                        // ban ghi dang o trang thai cho phan hoi cua tong dai
                        item.setStatus(1L);
                        if (item.getSaleActionType().equals(Constant.SALE_TYPE)) {
                            msgReturn = activeKitByIsdn(Constant.PREFIX_ISDN_HAITI + item.getIsdn(), item.getSerial());
                        } else if (item.getSaleActionType().equals(Constant.SALE_TYPE_DESTROY)) {
                            msgReturn = blockKitByIsdn(Constant.PREFIX_ISDN_HAITI + item.getIsdn(), item.getSerial());
                        } else {// null => ghi vao file ban ghi null
                            StringBuffer strMsg = new StringBuffer();
                            strMsg.append(item.getIsdn()).append(SPLITER);
                            strMsg.append(item.getSerial()).append(SPLITER);
                            strMsg.append("Null").append(SPLITER);
                            strMsg.append("Null").append(SPLITER);
                            repMsgs.add(strMsg.toString());
                        }
                        if (msgReturn.equals("Successful")) {
                            // dem so ban ghi thanh cong
                            if (item.getSaleActionType().equals(Constant.SALE_TYPE)) {
                                activeCount++;
                            } else {
                                destroyCount++;
                            }
                            item.setStatus(2L); // thanh cong
                        } else {
                            item.setStatus(3L); // that bai
                        }
                        item.setErrorDescription(msgReturn);
                        getSession().save(item);
                    }
                }
                rLst.add(activeCount);
                rLst.add(destroyCount);
                rLst.add(total);
            }

            //Ghi noi dung loi ra file
            bwErr = new BufferedWriter(new FileWriter(desErrFile));
            for (String msg : repMsgs) {
                bwErr.write(msg);
                bwErr.newLine();
            }


            req.setAttribute("errFilePath", req.getContextPath() + errFileName);
            if (rLst != null && rLst.size() > 0) {
                List<String> params = new ArrayList<String>();
                params.add(rLst.get(0) + "/" + rLst.get(2));
                params.add(rLst.get(1) + "/" + rLst.get(2));
                req.setAttribute(REQUEST_MESSAGE_PARAM, params);
                req.setAttribute(REQUEST_MESSAGE, "ACTIVE.KIT.SUCCESS");//Kích hoạt thành công
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "Error: " + ex.toString());
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
        } finally {
            //stopConnection();
            if (bwErr != null) {
                bwErr.flush();
                bwErr.close();
            }
        }

        return ACTIVE_KIT;
    }

    public String activeKitByFile() throws Exception {
        log.info("Begin filePreview of activeKitByFile");
        //Xuat ket qua
        BufferedWriter bwErr = null;
        HttpServletRequest req = getRequest();

        try {
            String errFileName = "/share/report_out/ActiveKitResult_" + DateTimeUtils.convertDateTimeToString(new Date(), "ddMMyyyy-hhss") + ".txt";
            String desErrFile = req.getRealPath(errFileName);

            // lay ket noi
            Connection conn = null;
            PreparedStatement stmUpdate = null;

            conn = getSession().connection();
            String serverFileName = this.activeKitForm.getServerFileName();
            serverFileName = getSafeFileName(serverFileName);
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow - 1; //tru di dong dau tien
            List<StockKitSaleBean> listRowInFile = new ArrayList<StockKitSaleBean>();
            numberRow = numberRow > 1000 ? 1000 : numberRow; //truong hop file > 1000 row -> chi hien thi 1000 row dau tien

            // dinh nghia loi du lieu trong file excel
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ACTIVE.KIT.SERIAL.EMPTY", getText("ACTIVE.KIT.SERIAL.EMPTY"));
            hashMapError.put("ERR.STK.126", getText("ERR.STK.126")); //!!!Lỗi. Cột serial khong phai la so nguyen duong

            //doc du lieu tu file -> day du lieu vao list
            List<Long> rLst = new ArrayList();
            String msgReturn = "";
            Long activeCount = 0L;
            Long destroyCount = 0L;
            Long total = 0L;
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                total++;
                //doc tat ca cac dong trong sheet
                String tmpStrSerial = sheet.getCell(0, rowIndex).getContents(); //serial
                BigInteger tmpSerial = new BigInteger("-1");
                StockKitSaleBean errActiveKitBean = new StockKitSaleBean();
                if (tmpStrSerial == null || tmpStrSerial.trim().equals("")) {
                    errActiveKitBean.setSerial(tmpStrSerial);
                    errActiveKitBean.setErrorMessage(hashMapError.get("ACTIVE.KIT.SERIAL.EMPTY")); //!!!Lỗi. Cột serial không được để trống                    
                    listRowInFile.add(errActiveKitBean);
                    continue;
                } else {
                    tmpStrSerial = tmpStrSerial.trim();
                    try {
                        tmpSerial = new BigInteger(tmpStrSerial);
                    } catch (NumberFormatException ex) {
                        //serial khong dung dinh dang
                        errActiveKitBean.setSerial(tmpStrSerial);
                        errActiveKitBean.setErrorMessage(hashMapError.get("ERR.STK.126")); //!!!Lỗi. Cột serial khong phai la so nguyen duong
                        listRowInFile.add(errActiveKitBean);
                        continue;
                    }
                }

                // kich hoat serial KIT ko bi loi
                StockKitSale item = findStockKitSaleBySerial(tmpStrSerial); // tim item theo serial
                if (item == null) {
                    msgReturn = "Serial not found!"; // ko tim thay serial
                } else {
                    if (item.getStatus().equals(2L)) {
                        StringBuffer strMsg = new StringBuffer();
                        strMsg.append(item.getIsdn()).append(SPLITER);
                        strMsg.append(item.getSerial()).append(SPLITER);
                        strMsg.append("Null").append(SPLITER);
                        strMsg.append("Already actived before").append(SPLITER);
                        repMsgs.add(strMsg.toString());
                    } else {
                        // kich hoat/huy tung isdn cua  bo KIT
                        // giao dich ban SALE_TYPE = 1 => kich hoat
                        // giao dich huy SALE_TYPE = 2 => chan
                        // ban ghi dang o trang thai cho phan hoi cua tong dai
                        item.setStatus(1L);
                        if (item.getSaleActionType().equals(Constant.SALE_TYPE)) {
                            msgReturn = activeKitByIsdn(Constant.PREFIX_ISDN_HAITI + item.getIsdn(), item.getSerial());
                        } else if (item.getSaleActionType().equals(Constant.SALE_TYPE_DESTROY)) {
                            msgReturn = blockKitByIsdn(Constant.PREFIX_ISDN_HAITI + item.getIsdn(), item.getSerial());
                        } else {// null => ghi vao file ban ghi null
                            msgReturn = "Null";
                        }
                        if (msgReturn.equals("Successful")) {
                            // dem so ban ghi thanh cong
                            if (item.getSaleActionType().equals(Constant.SALE_TYPE)) {
                                activeCount++;
                            } else {
                                destroyCount++;
                            }
                            item.setStatus(2L); // thanh cong
                        } else {
                            item.setStatus(3L); // that bai
                        }
                        item.setErrorDescription(msgReturn);
                        getSession().save(item);
                        rLst.add(activeCount);
                        rLst.add(destroyCount);
                        rLst.add(total);
                    }
                }
                errActiveKitBean.setErrorMessage(msgReturn);
            }
            //Ghi noi dung loi ra file
            bwErr = new BufferedWriter(new FileWriter(desErrFile));
            for (String msg : repMsgs) {
                bwErr.write(msg);
                bwErr.newLine();
            }


            req.setAttribute("errFilePath", req.getContextPath() + errFileName);
            if (rLst != null && rLst.size() > 0) {
                List<String> params = new ArrayList<String>();
                params.add(rLst.get(0) + "/" + rLst.get(2));
                params.add(rLst.get(1) + "/" + rLst.get(2));
                req.setAttribute(REQUEST_MESSAGE_PARAM, params);
                req.setAttribute(REQUEST_MESSAGE, "ACTIVE.KIT.SUCCESS");//Kích hoạt thành công
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        } finally {
            //stopConnection();
            if (bwErr != null) {
                bwErr.flush();
                bwErr.close();
            }
        }
        pageForward = ACTIVE_KIT;
        log.info("End filePreview of ReqActiveKitDAO");
        return pageForward;
    }

    /**
     * @author Tutm1 kich hoat KIT theo isdn don le
     * @param isdn
     * @throws Exception
     */
    private String activeKitByIsdn(String isdn, String serial) throws Exception {
        String msgReturn = "";
        List rLst = new ArrayList();
        startConnect();
        if (channel != null && channel.isConnected()) {
            StringBuffer strMsg = null;
            ExchMsg request = new ExchMsg();

            // set tham so gui len tong dai
            request.setCommand("CHARGING_DPREACTIVE"); // lenh kich hoat KIT
            request.set("MSISDNFrom", isdn);
            request.set("MSISDNTo", isdn);
//            request.set("ClientTimeout", Constant.CONNECTION_TIMEOUT);//10 phut

            ViettelMsg msg = (ViettelMsg) channel.send(request);

            if (msg instanceof ExchMsg) {
                ExchMsg exchMsg = (ExchMsg) msg;
                strMsg = new StringBuffer();
                strMsg.append(isdn).append(SPLITER);
                strMsg.append(serial).append(SPLITER);
                strMsg.append(exchMsg.getError()).append(SPLITER);
                strMsg.append(exchMsg.getDescription()).append(SPLITER);
                msgReturn = exchMsg.getDescription();
                repMsgs.add(strMsg.toString());
            }
        }
        return msgReturn;
    }

    /**
     * @author Tutm1 chan KIT theo isdn don le
     * @param isdn
     * @throws Exception
     */
    private String blockKitByIsdn(String isdn, String serial) throws Exception {
        String msgReturn = "";
        List rLst = new ArrayList();
        startConnect();
        if (channel != null && channel.isConnected()) {
            StringBuffer strMsg = null;
            ExchMsg request = new ExchMsg();

            // set tham so gui len tong dai
            request.setCommand("CHARGING_DPBLOCK"); // lenh chan KIT
            request.set("MSISDNFrom", isdn);
            request.set("MSISDNTo", isdn);
//            request.set("ClientTimeout", Constant.CONNECTION_TIMEOUT);//10 phut
            ViettelMsg msg = (ViettelMsg) channel.send(request);

            if (msg instanceof ExchMsg) {
                ExchMsg exchMsg = (ExchMsg) msg;
                strMsg = new StringBuffer();
                strMsg.append(isdn).append(SPLITER);
                strMsg.append(exchMsg.getError()).append(SPLITER);
                strMsg.append(exchMsg.getDescription()).append(SPLITER);
                repMsgs.add(strMsg.toString());
                msgReturn = exchMsg.getDescription();
            }
        }
        return msgReturn;
    }

    /**
     * tutm1: lay danh sach Isdn, serial KIT da ban
     * @param fromSerial
     * @param toSerial
     * @return
     * @throws Exception
     */
    private List<StockKitSale> getListStockKit(String[] lstReqId) throws Exception {
        try {
//            String lstReq = lstReqId[0];
//            for (int i = 1; i < lstReqId.length; i++) {
//                lstReq += "," + lstReqId[i];
//            }
//
//            Query query = getSession().createQuery("from StockKitSale where id IN (" + lstReq + ")");

            Query query = getSession().createQuery("from StockKitSale where id IN (:lstReq) ");
            query.setParameterList("lstReq", lstReqId);

            return query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    // ket noi toi tong dai Provisioning de kich hoat KIT
    private boolean startConnect() {
        try {
            if (channel == null) {
                channel = new ObjectClientChannel(
                        ResourceBundleUtils.getResource("PRO_ACT_KIT"),
                        Integer.parseInt(ResourceBundleUtils.getResource("PRO_ACT_KIT_PORT")),
                        ResourceBundleUtils.getResource("PRO_ACT_KIT_USER"),
                        ResourceBundleUtils.getResource("PRO_ACT_KIT_PASS"),
                        true);
                //Thoi gian timeout
                channel.setReceiverTimeout(260000); //10 phut
            }
            //Neu theo co che khong dong bo, can chi dinh handler
            //Ket noi
            channel.reconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    // ngat ket noi tong dai

    private boolean stopConnection() {
        try {
            if (channel == null) {
                return true;
            }

            channel.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * ThanhNC, 20/06/2009
     * Lay staff available bang staff_code
     */
    public StockKitSale findStockKitSaleBySerial(String serial) throws Exception {
        log.debug("finding all findStockKitSaleBySerial");
        try {
            if (serial == null || serial.trim().equals("")) {
                return null;
            }
            String queryString = "from StockKitSale a where a.serial = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, serial);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (StockKitSale) lst.get(0);
            }
            return null;
        } catch (Exception re) {
            log.error("find all failed", re);
            throw re;
        }
    }
}
