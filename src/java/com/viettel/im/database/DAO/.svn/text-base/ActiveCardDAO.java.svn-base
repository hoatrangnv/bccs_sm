/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.ExchMsg;
import com.viettel.common.ObjectClientChannel;
import com.viettel.common.ViettelService;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ActiveCardForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.SerialPair;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VcReqBatch;
import com.viettel.im.database.BO.VcRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import org.hibernate.Query;
import java.util.Calendar;

/**
 *
 * @author LamNV5
 */
public class ActiveCardDAO extends BaseDAOAction {
    //dinh nghia ten cac bien session hoac bien request

    /*Query mode:
    0: queries all voucher card information in the batch
    1: returning the serial no. list of voucher card which can be locked.
    2: returning the serial no. list of voucher card which can be unlocked.
    returning the serial no list of voucher card which are in idle(not activated) state
    4: returning the serial no list of voucher card which are in used state
     */
    private static final String QUERY_TYPE_ALL = "0";
    private static final String ATTR_LIST = "BATCHNO&SERIALNO&CREATEDATE&EXPIREDATE&HOTCARDFLAG";
    private static final String ACT_RESULT = "activeCard";
    private static final String ACT_RESULT_RAPID = "activeCardRapid";
    private static final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    //private static final String[] CARD_STATUS_SIGN = {"Thẻ đã được tạo, chưa được kích hoạt", "Thẻ đã được kích hoạt, sẵn sàng êể nạp tiền", "Thẻ đã bị nạp tiền", "Thẻ đang ở trạng thái khóa"};
    private static final String[] CARD_STATUS_NOSIGN = {"msg.active.card.status.01", "msg.active.card.status.02", "msg.active.card.status.03", "msg.active.card.status.04"};
    private static final String ATTR_SERIAL = "msg.active.card.serial";
    private static final String ATTR_CREATE_DATE = "msg.active.card.date.create";
    private static final String ATTR_EXPIRE_DATE = "msg.active.card.date.expire";
    private static final String ATTR_CARD_STATUS = "msg.active.card.status.text";
    private static final String FORMAT_ROW = "%1$-12s|%2$-9s|%3$-13s|%4$-50s|\n";
    public static final Long boundL = 99999L;
    public static final Long boundH = 0L;
    public static final int MAX_BATCH_SIZE = 2500;
    public static final String SPLITER = " ";
    public static final String RECORD_SPLITER = "&";
    public static final String ATTR_SPLITER = "\\|";
    public static final int CONNECTION_TIMEOUT = 620000;
    //Cac thuoc tinh cua lop
    public static ObjectClientChannel channel;
    ActiveCardForm activeCardForm = new ActiveCardForm();
    private List<String> repMsgs;

    static {
        try {
            if (channel == null) {
                channel = new ObjectClientChannel(ResourceBundleUtils.getResource("PRO_ACT_URL"),
                        Integer.parseInt(ResourceBundleUtils.getResource("PRO_ACT_PORT")),
                        ResourceBundleUtils.getResource("PRO_ACT_USER"),
                        ResourceBundleUtils.getResource("PRO_ACT_PASS"), true);
                //Thoi gian timeout
                channel.setReceiverTimeout(260000); //10 phut
            } //Ket noi
            channel.reconnect();
        } catch (IOException ex) {
            Logger.getLogger(ActiveCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public static void main(String[] args) {
//        try {
//            BigDecimal fromSerial = BigDecimal.valueOf(888899990053L);
//            BigDecimal toSerial = BigDecimal.valueOf(888899990072L);
//            ActiveCardDAO a = new ActiveCardDAO();
//            a.activeRequest(fromSerial, toSerial);
//        } catch (Exception ex) {
//        }
//    }
    public ActiveCardDAO() {
        repMsgs = new ArrayList<String>();
    }

    public ActiveCardForm getActiveCardForm() {
        return activeCardForm;
    }

    public void setActiveCardForm(ActiveCardForm activeCardForm) {
        this.activeCardForm = activeCardForm;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.ActiveCard.InputSerial");//Nhập vào dải serial cần kích hoạt
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.activeCardForm.setShopCode(userToken.getShopCode());
        this.activeCardForm.setShopName(userToken.getShopName());
        this.activeCardForm.setStaffCode(userToken.getLoginName());
        this.activeCardForm.setStaffName(userToken.getFullName());
//        this.activeCardForm.setMaxCardCheck(ResourceBundleUtils.getResource("HW_MAX_CARD"));
//        this.activeCardForm.setMaxCardActive(ResourceBundleUtils.getResource("HW_MAX_CARD_ACTIVE"));



//        try {
//            BigDecimal fromSerial = BigDecimal.valueOf(888899990053L);
//            BigDecimal toSerial = BigDecimal.valueOf(888899990072L);
//            ActiveCardDAO a = new ActiveCardDAO();
//            a.activeRequest(fromSerial, toSerial);
//        } catch (Exception ex) {
//        }


        return ACT_RESULT;
    }

    public String preparePageRapid() throws Exception {
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.ActiveCard.InputSerial");//Nhập vào dải serial cần kích hoạt
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        this.activeCardForm.setShopCode(userToken.getShopCode());
        this.activeCardForm.setShopName(userToken.getShopName());
        this.activeCardForm.setStaffCode(userToken.getLoginName());
        this.activeCardForm.setStaffName(userToken.getFullName());
//        this.activeCardForm.setMaxCardCheck(ResourceBundleUtils.getResource("HW_MAX_CARD"));
//        this.activeCardForm.setMaxCardActive(ResourceBundleUtils.getResource("HW_MAX_CARD_ACTIVE"));
        return ACT_RESULT_RAPID;
    }

    /**
     *
     * @Purpose  Chuc nang kich hoat the cao nhanh
     * @throws java.lang.Exception
     */
    public String activeCardRapid() throws Exception {
        HttpServletRequest req = getRequest();
        BufferedWriter bwErr = null; //Xuat ket qua
        try {
            /* Tao ket noi toi VC HW */
            startConnect();

            /* Ghi log ket qua ra file */
            String errFileName = "/share/report_out/ActiveCardResult_" + DateTimeUtils.convertDateTimeToString(new Date(), "ddMMyyyy-hhss") + ".txt";
            String desErrFile = req.getRealPath(errFileName);

            /* Check dai serial vao */

            BigDecimal hwMaxCard = new BigDecimal(ResourceBundleUtils.getResource("HW_MAX_CARD_ACTIVE"));
            BigDecimal fromSerial = null;
            BigDecimal toSerial = null;

            try {
                fromSerial = new BigDecimal(activeCardForm.getFromSerial());
                toSerial = new BigDecimal(activeCardForm.getToSerial());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0001");//Serial nhập vào phải là dạng số
                return ACT_RESULT_RAPID;
            }


            /* Kich hoat dai the cao */
            List<Long> rLst = new ArrayList();
            if (toSerial.subtract(fromSerial).compareTo(hwMaxCard) <= 0) {
                rLst = activeRequestAdvan(activeCardForm.getFromSerial(), activeCardForm.getToSerial());
            } else {
                List listMessageParam = new ArrayList();
                listMessageParam.add(hwMaxCard.toString());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0002");
                return ACT_RESULT_RAPID;
            }

            /* Ghi noi dung loi ra file */
            bwErr = new BufferedWriter(new FileWriter(desErrFile));
            for (String msg : repMsgs) {
                bwErr.write(msg);
                bwErr.newLine();
            }

            if (rLst != null && rLst.size() > 0) {
                req.setAttribute("errFilePath", req.getContextPath() + errFileName);
                List listMessageParam = new ArrayList();
                Long successCount = rLst.get(1) - rLst.get(0);
                listMessageParam.add(successCount.toString() + "/" + rLst.get(1).toString());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0004");//Kích hoạt thành công
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0003");//Không tạo được kết nối tới Provisioning
            }

            /*
            if (channel == null || channel.isConnected() == false) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0003");//Không tạo được kết nối tới Provisioning
            } else {
            req.setAttribute("errFilePath", req.getContextPath() + errFileName);
            req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0004");//Kích hoạt thành công
            }
             */

            saveLogActiveCard(req, "AC_ACTIVE_RAPID", activeCardForm.getFromSerial(), activeCardForm.getToSerial());
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "Error: " + ex.toString());
        } finally {
            //stopConnection();
            if (bwErr != null) {
                bwErr.flush();
                bwErr.close();
            }
        }

        return ACT_RESULT_RAPID;
    }

    /**
     * LamNV: Kiem tra trang thai serial
     * @return
     * @throws Exception
     */
    public String checkSerial() throws Exception {
        HttpServletRequest req = getRequest();
        BufferedWriter bwErr = null; //Xuat ket qua
        try {

            String errFileName = "/share/report_out/CheckCardResult_" + DateTimeUtils.convertDateTimeToString(new Date(), "ddMMyyyy-hhss");
            String desErrFile = req.getRealPath(errFileName);

            //Ket noi toi ZTE
            startConnect();

            /* Kiem tra don le tung serial
            List<ActiveCardInfo> lstCardInfo = new ArrayList<ActiveCardInfo>();
            ExchMsg request = new ExchMsg();
            request.setCommand("IN_QUERYCARD");
            request.set("ClientTimeout", 260000);//10 phut
            request.set("SEQUENCE", activeCardForm.getFromSerial());
            ViettelMsg msg = (ViettelMsg) channel.send(request);
            
            ActiveCardInfo info = new ActiveCardInfo();
            
            info.setCardStatus((String) msg.get("HOTCARDFLAG"));
            info.setCardExpired((String) msg.get("CARDEXPIRED"));
            info.setCardValue((String) msg.get("CARDVALUE"));
            lstCardInfo.add(info);
            
            req.setAttribute("tblListCheckCard", lstCardInfo);
            if (msg.getError() != null && !msg.getError().equals("0")) {
            req.setAttribute(REQUEST_MESSAGE, msg.toString());
            }
             */

            /* Check dai serial vao */
            BigDecimal hwMaxCard = new BigDecimal(ResourceBundleUtils.getResource("HW_MAX_CARD"));
            BigDecimal fromSerial = null;
            BigDecimal toSerial = null;

            try {
                fromSerial = new BigDecimal(activeCardForm.getFromSerial());
                toSerial = new BigDecimal(activeCardForm.getToSerial());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0001");//Serial nhập vào phải là dạng số
                return ACT_RESULT_RAPID;
            }

            if (toSerial.subtract(fromSerial).compareTo(hwMaxCard) == 1) {
                toSerial = fromSerial.add(hwMaxCard);
            }

            /* Kiem tra theo dai */
            String strResult = queryListSerial(fromSerial, toSerial);
            //Ghi noi dung loi ra file
            bwErr = new BufferedWriter(new FileWriter(desErrFile));
            bwErr.write(strResult);

            if (channel == null || channel.isConnected() == false) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0003");//Không tạo được kết nối tới Provisioning
            } else {
                req.setAttribute("errFilePath", req.getContextPath() + errFileName);
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0005");//Tra cứu thành công
            }

            saveLogActiveCard(req, "AC_CHECK", activeCardForm.getFromSerial(), activeCardForm.getToSerial());

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "Error: " + ex.getMessage());
        } finally {
            //stopConnection();
            if (bwErr != null) {
                bwErr.flush();
                bwErr.close();
            }
        }

        return ACT_RESULT;
    }

    /**
     * LamNV: Kich hoat the cao theo dai
     * @return
     * @throws Exception
     */
    public String activeCard() throws Exception {
        //Xuat ket qua
        BufferedWriter bwErr = null;
        HttpServletRequest req = getRequest();
        try {
            String errFileName = "/share/report_out/ActiveCardResult_" + DateTimeUtils.convertDateTimeToString(new Date(), "ddMMyyyy-hhss") + ".txt";
            String desErrFile = req.getRealPath(errFileName);

            String[] selectedItem = activeCardForm.getLstSelectedItem();
            if (selectedItem == null || selectedItem.length == 0) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0006");//Chưa chọn dải serial để kích hoạt
                return ACT_RESULT;
            }
            if (selectedItem != null && selectedItem.length == 1 && "false".equals(selectedItem[0].toLowerCase())) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0006");//Chưa chọn dải serial để kích hoạt
                return ACT_RESULT;
            }

            List<Long> rLst = new ArrayList();
            List<VcRequest> lstReq = getListRequest(selectedItem);
            if (lstReq != null && lstReq.size() > 0) {
                startConnect();
                List<Long> temp = new ArrayList();
                Long successCount = 0L;
                Long total = 0L;
                for (VcRequest item : lstReq) {
                    saveLogActiveCard(req, "AC_ACTIVE_NOMAL", item.getFromSerial(), item.getToSerial());
                    temp = activeRequestAdvan(item.getFromSerial(), item.getToSerial());
                    if (temp != null && temp.size() == 2) {
                        successCount += temp.get(0);
                        total += temp.get(1);
                    }
                    item.setStatus(10L);
                    getSession().save(item);
                }
                rLst.add(successCount);
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
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0004");//Kích hoạt thành công
                List listMessageParam = new ArrayList();
                listMessageParam.add(rLst.get(0).toString() + "/" + rLst.get(1).toString());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0003");//Không tạo được kết nối tới Provisioning
            }
            //req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0004");//Kích hoạt thành công

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

        return ACT_RESULT;
    }

    /**
     * LamNV: tim kiem dai serial trong VC_REQUEST
     * @return
     * @throws Exception
     */
    public String lookupSerial() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String shopCode = this.activeCardForm.getShopCode().trim();
        String staffCode = this.activeCardForm.getStaffCode().trim();
        String fromSerial = this.activeCardForm.getFromSerial().trim();
        String toSerial = this.activeCardForm.getToSerial().trim();
        Long shopId = null;
        Long staffId = null;
        String shopPath = null;
        Long shopIdOfStaff = null;
        String shopIdUser = userToken.getShopId().toString();
        List<VcRequest> lstReq = null;


        //Kiem tra dieu kien dai Serial
        if (fromSerial == null || "".equals(fromSerial) || toSerial == null || "".equals(toSerial)) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0007");//Bạn chưa nhập dải Serial
            return ACT_RESULT;
        }

        //lay shopId theo shopCode
        if (shopCode != null && !"".equals(shopCode)) {
            try {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = (Shop) shopDAO.findByShopCode(shopCode).get(0);
                shopId = shop.getShopId();
                shopPath = shop.getShopPath();
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.GOD.035");//Mã kho không tồn tại
                return ACT_RESULT;
            }
        }

        //Kiem tra tinh hop le cua shopCode 
        if (shopPath != null && !"".equals(shopPath)) {
            int index = shopPath.indexOf(shopIdUser);
            if (index == -1) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0008");//Bạn không có quyền xem dải serial trong cửa hàng này
                return ACT_RESULT;
            }
        }

        //Lay staffId theo staffCode
        if (staffCode != null && !"".equals(staffCode)) {
            try {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = (Staff) staffDAO.findByStaffCode(staffCode).get(0);
                staffId = staff.getStaffId();
                shopIdOfStaff = staff.getShopId();
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.CHL.141");//Mã nhân viên không tồn tại
                return ACT_RESULT;
            }
        }

        //Lay shopId cua User dang nhap neu shopCode = null
        if (shopId == null) {
            shopId = userToken.getShopId();
        }

        if (staffId == null) {
            lstReq = getListRequest(activeCardForm.getFromSerial(), activeCardForm.getToSerial(), shopId);
        } else {

            //Kiem tra tinh hop le cua staffCode
            String strQuery = "select a.shopPath from Shop a, Staff b where a.shopId = b.shopId and b.staffId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, staffId);

            List lstShopPath = query.list();
            String shopPathStaff = (String) lstShopPath.get(0);
            int index = shopPathStaff.indexOf(shopId.toString());
            if (index == -1) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0009");//Bạn không có quyền xem dải serial của nhân viên này
                return ACT_RESULT;
            }

            lstReq = getListRequest(activeCardForm.getFromSerial(), activeCardForm.getToSerial(), shopId, staffId);
        }

        if (lstReq.size() > 100) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.ACTIVE.CARD.0010");//Có nhiều hơn 100 bản ghi, bạn cần thu hẹp khoảng tìm kiếm
            return ACT_RESULT;
        }

        req.setAttribute("tblListSerialPair", lstReq);
        activeCardForm.setLstSelectedItem(null);
        return ACT_RESULT;
    }

    /**
     * LamNV: Kich hoat the cao theo dai
     * @note: ho tro active dai > 50000 serial
     * @param fromSerial
     * @param toSerial
     * @throws Exception
     */
    private List<Long> activeRequestAdvan(String fromSerial, String toSerial) throws Exception {
        BigDecimal bFromSerial = new BigDecimal(fromSerial);
        BigDecimal bToSerial = new BigDecimal(toSerial);

        List<Long> rLst = new ArrayList();
        /* Kiem tra so luong co vuot qua 50000, neu vuot chia nho hon */
        if (bToSerial.subtract(bFromSerial).compareTo(new BigDecimal(Constant.BATCH_CARD_NUM)) <= 0) {
            rLst = activeRequest(bFromSerial, bToSerial);
        } else {
            List<SerialPair> lstReq = splitRequestLowLevel(fromSerial, toSerial);
            List<Long> temp = new ArrayList();
            Long failCount = 0L;
            Long total = 0L;
            for (SerialPair request : lstReq) {
                temp = activeRequest(request.getFromSerial(), request.getToSerial());
                if (temp != null && temp.size() == 2) {
                    failCount += temp.get(0);
                    total += temp.get(1);
                }
            }
            rLst.add(failCount);
            rLst.add(total);
        }
        return rLst;
    }

    /**
     * LamNV: Kich hoat the cao theo dai
     * @note:chi active dai <= 50000 serial
     * @param fromSerial
     * @param toSerial
     * @throws Exception
     */
    private List<Long> activeRequest(BigDecimal fromSerial, BigDecimal toSerial) throws Exception {
        List rLst = new ArrayList();
        if (channel != null && channel.isConnected()) {
            List<VcReqBatch> lstBatch = splitBatch(fromSerial, toSerial);
            StringBuffer strMsg = null;
            Long failCount = 0L;
            for (VcReqBatch batch : lstBatch) {
                ExchMsg request = new ExchMsg();

                //Phan biet truong hop kich hoat don le va kich hoat theo lo
                int staNo = Integer.valueOf(batch.getStaNo());
                int endNo = Integer.valueOf(batch.getEndNo());

                if (endNo - staNo + 1 <= 100) {
                    for (int i = staNo; i <= endNo; i++) {
                        request.setCommand("VCHW_ACTIVECARD");
                        request.set("ClientTimeout", 10000);//3 phut
                        request.set("SEQUENCE", batch.getBatchNo() + String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", staNo + i));
                        request.set("CARDFLAG", "0");
                        request.set("INDEXFLAG", "1");
                        request.set("REASON", "a");

                        channel.connect();
                        ViettelMsg msg = (ViettelMsg) channel.send(request);
                        if (msg instanceof ExchMsg) {
                            ExchMsg exchMsg = (ExchMsg) msg;
                            strMsg = new StringBuffer();
                            strMsg.append(batch.getBatchNo()).append(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", staNo + i)).append(SPLITER);
                            strMsg.append(batch.getBatchNo()).append(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", staNo + i)).append(SPLITER);
                            strMsg.append(exchMsg.getError()).append(SPLITER);
                            strMsg.append(exchMsg.get("FAILED_NUMBER")).append(SPLITER);
                            strMsg.append(exchMsg.get("FAILED_FILE")).append(SPLITER);
                            strMsg.append(exchMsg.getDescription()).append(SPLITER);

                            repMsgs.add(strMsg.toString());
                            failCount += Long.parseLong(exchMsg.get("FAILED_NUMBER").toString());
                        }
                    }

                } else {
                    ExchMsg request1 = new ExchMsg();
                    request1.setCommand("VCHW_GETTASKID");
                    request1.set("TOTALNUMBER", "1");
                    ViettelMsg msg1 = channel.send(request1);
                    String taskId = msg1.get("TASKID").toString();
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MINUTE, 15);
                    Date date = cal.getTime();
                    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                    String startupTime = df.format(date);
                    request.setCommand("VCHW_ADDUVCTASK");
                    request.set("ClientTimeout", Constant.CONNECTION_TIMEOUT);//3 phut
                    request.set("TASKID", taskId);
                    request.set("STARTUPTIME", startupTime);
                    request.set("NOTCEFLAG", "1");
                    request.set("TASKTYPE", "104");
                    request.set("PARAMNAME", "VCID|BATCH|STARTSEQUENCE|STOPSEQUENCE");
//                    request.set("PARAMVALUE", "106|" + fromSerial.substring(0, 6) + "|" + fromSerial + "|" + toSerial);
                    request.set("PARAMVALUE", "106|" + batch.getBatchNo() + "|" + batch.getStaNo() + "|" + batch.getEndNo());
                    request.set("DL_ID", "123456");

                    ViettelMsg msg = (ViettelMsg) channel.send(request);
                    if (msg instanceof ExchMsg) {
                        ExchMsg exchMsg = (ExchMsg) msg;
                        strMsg = new StringBuffer();
                        strMsg.append(batch.getBatchNo()).append(batch.getStaNo()).append(SPLITER);
                        strMsg.append(batch.getBatchNo()).append(batch.getEndNo()).append(SPLITER);
                        strMsg.append(exchMsg.getError()).append(SPLITER);
                        strMsg.append(exchMsg.get("FAILED_NUMBER")).append(SPLITER);
                        strMsg.append(exchMsg.get("FAILED_FILE")).append(SPLITER);
                        strMsg.append(exchMsg.getDescription()).append(SPLITER);

                        repMsgs.add(strMsg.toString());
                        failCount += Long.parseLong(exchMsg.get("FAILED_NUMBER").toString());
                    }
                }
            }
            
            rLst.add(failCount);
            rLst.add(Long.valueOf(lstBatch.size()));
        }
        return rLst;
    }

    /**
     * LamNV: tra cuu trang thai serial theo dai
     * @param fromSerial
     * @param toSerial
     * @throws Exception
     */
    private String queryListSerial(BigDecimal fromSerial, BigDecimal toSerial) throws Exception {
        StringBuilder resultBuilder = new StringBuilder();
        ExchMsg exchMsg = null;
        try {
            if (channel != null && channel.isConnected()) {
                List<VcReqBatch> lstBatch = splitBatch(fromSerial, toSerial);
                for (VcReqBatch batch : lstBatch) {
                    ExchMsg request = new ExchMsg();
                    request.setCommand("IN_QUERYBATCHCRD");
                    request.set("ClientTimeout", Constant.CONNECTION_TIMEOUT);//10 phut
                    request.setExchId("vchw_activate");
//                    request.set("BATCHNO", batch.getBatchNo());

//                    request.set("START_SERIAL", batch.getStaNo());
//                    request.set("END_SERIAL", batch.getEndNo());

                    request.set("QUERYTYPE", QUERY_TYPE_ALL);
                    request.set("ATTR", ATTR_LIST);

                    request.set("STARTNO", batch.getStaNo());
                    request.set("ENDNO", batch.getEndNo());

                    ViettelMsg msg = (ViettelMsg) channel.send(request);


                    if (msg instanceof ExchMsg) {
                        exchMsg = (ExchMsg) msg;
                        resultBuilder.append(String.format(FORMAT_ROW, getText(ATTR_SERIAL), getText(ATTR_CREATE_DATE), getText(ATTR_EXPIRE_DATE), getText(ATTR_CARD_STATUS))).append("\n");
                        //String attrStr = exchMsg.get("ATTR").toString();
                        //strMsg.append(attrStr);
                        String resultStr = exchMsg.get("RESULT").toString();
                        String[] records = resultStr.split(RECORD_SPLITER);
                        for (int i = 0; i < records.length; i++) {
                            String[] attrs = records[i].split(ATTR_SPLITER);
                            resultBuilder.append(String.format(FORMAT_ROW, attrs[0] + attrs[1], attrs[2], attrs[3], getText(CARD_STATUS_NOSIGN[Integer.parseInt(attrs[4])])));
                        }

                    }
                }
            }
        } catch (Exception ex) {
            resultBuilder = new StringBuilder();
            resultBuilder.append(exchMsg.toString());
        }

        return resultBuilder.toString();
    }

    private boolean startConnect() {
        try {
            if (channel == null) {
                channel = new ObjectClientChannel(
                        ResourceBundleUtils.getResource("PRO_ACT_URL"),
                        Integer.parseInt(ResourceBundleUtils.getResource("PRO_ACT_PORT")),
                        ResourceBundleUtils.getResource("PRO_ACT_USER"),
                        ResourceBundleUtils.getResource("PRO_ACT_PASS"),
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
     * LamNV
     * Mot dai serial cung batch duoc chia thanh cac dai serial nho co size = 200
     * @param session
     * @param lstaNo
     * @param lendNo
     * @param reqId
     * @param batchNo
     */
    private static List<SerialPair> splitRequestLowLevel(String fromSerial, String toSerial) throws Exception {
        BigDecimal staNoTemp = new BigDecimal(fromSerial);
        BigDecimal endNoTemp = new BigDecimal(toSerial);
        BigDecimal i = new BigDecimal(fromSerial);

        BigDecimal maxReqSize = new BigDecimal(Constant.BATCH_CARD_NUM);

        List<SerialPair> lstReq = new ArrayList<SerialPair>();

        i = i.add(maxReqSize.subtract(BigDecimal.ONE));
        while (i.compareTo(endNoTemp) == -1) {
            SerialPair vcReq = new SerialPair();
            vcReq.setFromSerial(staNoTemp);
            vcReq.setToSerial(i);
            staNoTemp = i.add(BigDecimal.ONE);

            lstReq.add(vcReq);
            i = i.add(maxReqSize);
        }

        if (staNoTemp.compareTo(endNoTemp) <= 0) {
            SerialPair vcReq = new SerialPair();
            vcReq.setFromSerial(staNoTemp);
            vcReq.setToSerial(endNoTemp);
            lstReq.add(vcReq);
        }

        return lstReq;
    }

    /**
     * @author: LamNV
     * @purpose: chia request thanh cac batch
     * @param vcRequest
     */
    private static List<VcReqBatch> splitBatch(BigDecimal fromSerial, BigDecimal toSerial) throws Exception {

        List<VcReqBatch> lstBatch = null;

//        String sFromSerial = String.format("%011d", toSerial.longValue());
//        String sToSerial = String.format("%011d", toSerial.longValue());
//        String batchNoL = sFromSerial.substring(0, 6);
//        String batchNoH = sToSerial.substring(0, 6);
//        String staNo = sFromSerial.substring(6, 11);
//        String endNo = sToSerial.substring(6, 11);

        String sFromSerial = String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO) + "d", fromSerial.longValue());
        String sToSerial = String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO) + "d", toSerial.longValue());
        String batchNoL = sFromSerial.substring(0, Constant.LENGTH_BATCH_NO);
        String batchNoH = sToSerial.substring(0, Constant.LENGTH_BATCH_NO);
        String staNo = sFromSerial.substring(Constant.LENGTH_BATCH_NO, Constant.LENGTH_CARD_NO);
        String endNo = sToSerial.substring(Constant.LENGTH_BATCH_NO, Constant.LENGTH_CARD_NO);

        Long lstaNo = Long.parseLong(staNo);
        Long lendNo = Long.parseLong(endNo);

        if (batchNoL.equals(batchNoH)) {
            lstBatch = splitLowLevel(lstaNo, lendNo, batchNoL);
        } else {
            //Chia batch phan tren
            lstBatch = splitLowLevel(lstaNo, boundL, batchNoL);
            lstBatch.addAll(splitLowLevel(boundH, lendNo, batchNoH));
        }

        return lstBatch;
    }

    /**
     * LamNV
     * Mot dai serial cung batch duoc chia thanh cac dai serial nho co size = 200
     * @param session
     * @param lstaNo
     * @param lendNo
     * @param reqId
     * @param batchNo
     */
    private static List<VcReqBatch> splitLowLevel(Long lstaNo, Long lendNo, String batchNo) throws Exception {
        Long staNoTemp = lstaNo;
        Long endNoTemp = lendNo;
        List<VcReqBatch> lstBatch = new ArrayList<VcReqBatch>();

        for (Long i = lstaNo + MAX_BATCH_SIZE - 1;
                i.compareTo(lendNo) < 0;
                i = i + MAX_BATCH_SIZE) {
            VcReqBatch vcReqBatch = new VcReqBatch();
            vcReqBatch.setBatchNo(batchNo);
            vcReqBatch.setCurrentNo(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", staNoTemp));
            vcReqBatch.setStaNo(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", staNoTemp));
            vcReqBatch.setEndNo(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", i));
            staNoTemp = i + 1;
            lstBatch.add(vcReqBatch);
        }
        if (staNoTemp.compareTo(endNoTemp) <= 0) {
            VcReqBatch vcReqBatch = new VcReqBatch();
            vcReqBatch.setBatchNo(batchNo);
            vcReqBatch.setCurrentNo(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", staNoTemp));
            vcReqBatch.setStaNo(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", staNoTemp));
            vcReqBatch.setEndNo(String.format("%0" + String.valueOf(Constant.LENGTH_CARD_NO - Constant.LENGTH_BATCH_NO) + "d", endNoTemp));
            lstBatch.add(vcReqBatch);
        }

        return lstBatch;

        /*
        int staNoTemp = lstaNo.intValue();
        int endNoTemp = lendNo.intValue();
        
        List<VcReqBatch> lstBatch = new ArrayList<VcReqBatch>();
        
        for (int i = lstaNo.intValue() + MAX_BATCH_SIZE - 1;
        i < lendNo.intValue();
        i = i + MAX_BATCH_SIZE) {
        VcReqBatch vcReqBatch = new VcReqBatch();
        vcReqBatch.setBatchNo(batchNo);
        vcReqBatch.setCurrentNo(String.format("%05d", staNoTemp));
        vcReqBatch.setStaNo(String.format("%05d", staNoTemp));
        vcReqBatch.setEndNo(String.format("%05d", i));
        staNoTemp = i + 1;
        
        lstBatch.add(vcReqBatch);
        
        }
        
        if (staNoTemp <= endNoTemp) {
        VcReqBatch vcReqBatch = new VcReqBatch();
        vcReqBatch.setBatchNo(batchNo);
        vcReqBatch.setCurrentNo(String.format("%05d", staNoTemp));
        vcReqBatch.setStaNo(String.format("%05d", staNoTemp));
        vcReqBatch.setEndNo(String.format("%05d", endNoTemp));
        lstBatch.add(vcReqBatch);
        }
        
        return lstBatch;
        
         */
    }

    /**
     * LamNV: lay danh sach serial
     * @param fromSerial
     * @param toSerial
     * @return
     * @throws Exception
     */
    private List<VcRequest> getListRequest(String[] lstReqId) throws Exception {
        try {
//            String lstReq = lstReqId[0];
//            for (int i = 1; i < lstReqId.length; i++) {
//                lstReq += "," + lstReqId[i];
//            }

            Query query = getSession().createQuery("from VcRequest where requestId IN (:lstReq)");

            //Modify by hieptd
            List<Long> params = new ArrayList();
            for(int i = 0; i< lstReqId.length; i++){
                params.add(Long.parseLong(lstReqId[i]));
            }
            query.setParameterList("lstReq", params);
            
            return query.list();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * LamNV: lay danh sach serial chi theo ShopId
     * @param fromSerial
     * @param toSerial
     * @return
     * @throws Exception
     */
    private List<VcRequest> getListRequest(String fromSerial, String toSerial, Long shopId) throws Exception {
        try {

            String strQuery = "select new VcRequest(a.requestId, a.userId,a.fromSerial,a.toSerial,a.createTime,a.lastProcessTime)" + " from VcRequest a, Shop b where a.fromSerial >= ? and a.toSerial <= ? and a.shopId = b.shopId" + " and (b.shopPath like ? or b.shopId = ?) ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, fromSerial);
            query.setParameter(1, toSerial);
            query.setParameter(2, "%_" + shopId + "_%");
            query.setParameter(3, shopId);

            List<VcRequest> lst = query.list();

            if (lst.size() == 0) {
                String strQuery1 = "select new VcRequest(a.requestId, a.userId,a.fromSerial,a.toSerial,a.createTime,a.lastProcessTime)" + " from VcRequest a ,Shop b where ? >= a.fromSerial and ? <= a.toSerial " + "and a.shopId = b.shopId and (b.shopPath like ? or b.shopId = ?) ";
                query = getSession().createQuery(strQuery1);
                query.setParameter(0, fromSerial);
                query.setParameter(1, fromSerial);
                query.setParameter(2, "%_" + shopId + "_%");
                query.setParameter(3, shopId);

                lst = query.list();
            }
            return lst;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *author TheTM
     *purpose Lay danh sach VcRequest theo shop va staff mac dinh cua User dang nhap
     */
    private List<VcRequest> getListRequest(String fromSerial, String toSerial) throws Exception {
        try {
            Query query = getSession().createQuery("from VcRequest where fromSerial >= ? and toSerial <= ? ");
            query.setParameter(0, fromSerial);
            query.setParameter(1, toSerial);

            List lst = query.list();

            if (lst.size() == 0) {
                query = getSession().createQuery("from VcRequest where ? >= fromSerial and ? <= toSerial");
                query.setParameter(0, fromSerial);
                query.setParameter(1, fromSerial);
                lst = query.list();
            }
            return lst;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *author TheTM
     *purpose Lay danh sach VcRequest theo shopId va staffId
     */
    private List<VcRequest> getListRequest(String fromSerial, String toSerial, Long shopId, Long staffId) throws Exception {
        try {

            String strQuery = "select new VcRequest(a.requestId, a.userId,a.fromSerial,a.toSerial,a.createTime,a.lastProcessTime)" + " from VcRequest a, Shop b where a.fromSerial >= ? and a.toSerial <= ? and a.shopId = b.shopId" + " and (b.shopPath like ? or b.shopId = ?) and a.staffId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, fromSerial);
            query.setParameter(1, toSerial);
            query.setParameter(2, "%_" + shopId + "_%");
            query.setParameter(3, shopId);
            query.setParameter(4, staffId);

            List<VcRequest> lst = query.list();

            if (lst.size() == 0) {
                String strQuery1 = "select new VcRequest(a.requestId, a.userId,a.fromSerial,a.toSerial,a.createTime,a.lastProcessTime)" + " from VcRequest a ,Shop b where ? >= a.fromSerial and ? <= a.toSerial " + "and a.shopId = b.shopId and (b.shopPath like ? or b.shopId = ?) and a.staffId = ? ";
                query = getSession().createQuery(strQuery1);
                query.setParameter(0, fromSerial);
                query.setParameter(1, fromSerial);
                query.setParameter(2, "%_" + shopId + "_%");
                query.setParameter(3, shopId);
                query.setParameter(4, staffId);

                lst = query.list();
            }
            return lst;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    private List getListShopId(Long shopId) {
        String strQuery = "select shopId from Shop where shopPath like ? or shopId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, "%_" + shopId + "_%");
        query.setParameter(1, shopId);
        List listShopId = query.list();
        return listShopId;
    }

    private void saveLogActiveCard(HttpServletRequest req, String actionType, String fromSerial, String toSerial) {
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            ActionLog actionLog = new ActionLog();
            actionLog.setActionId(getSequence("ACTION_LOG_SEQ"));
            actionLog.setActionDate(getSysdate());
            actionLog.setActionType(actionType);
            actionLog.setActionIp(req.getRemoteAddr());
            actionLog.setActionUser(userToken.getLoginName());
            actionLog.setActionUser(userToken.getShopCode() + "_" + userToken.getStaffName());
            actionLog.setDescription(fromSerial + ":" + toSerial);
            getSession().save(actionLog);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @author tutm1 query theo serial đơn lẻ len tong dai, tra ve thong tin cua the cao
     * @date 21/08/2012
     * @param serial
     * @return attribute array
     * @throws Exception
     */
    public ViettelMsg querySerial(String serial, ObjectClientChannel channelPro) throws Exception {
        try {
            /* Kiem tra don le tung serial
            List<ActiveCardInfo> lstCardInfo = new ArrayList<ActiveCardInfo>();
            ExchMsg request = new ExchMsg();
            request.setCommand("IN_QUERYCARD");
            request.set("ClientTimeout", 260000);//10 phut
            request.set("SEQUENCE", activeCardForm.getFromSerial());
            ViettelMsg msg = (ViettelMsg) channel.send(request);
            
            ActiveCardInfo info = new ActiveCardInfo();
            
            info.setCardStatus((String) msg.get("HOTCARDFLAG"));
            info.setCardExpired((String) msg.get("CARDEXPIRED"));
            info.setCardValue((String) msg.get("CARDVALUE"));
            lstCardInfo.add(info);
            
            req.setAttribute("tblListCheckCard", lstCardInfo);
            if (msg.getError() != null && !msg.getError().equals("0")) {
            req.setAttribute(REQUEST_MESSAGE, msg.toString());
            }
             */
            if (channelPro != null && channelPro.isConnected()) {
                System.out.println("Query thong tin serial tren tong dai");
                ViettelService request = new ViettelService();
                request.set("SEQUENCE", serial);
                String processCode = ResourceBundleUtils.getResource("PRO_SER_ACT_PROCESS_CODE");
                Random random = new Random(Calendar.getInstance().getTimeInMillis());
                long systemTrace = random.nextLong();
                request.setMessageType(ResourceBundleUtils.getResource("PRO_SER_MESSAGE_TYPE"));
                request.setProcessCode(processCode);
                request.setTransactionTime(new java.util.Date());
                request.setClientId(ResourceBundleUtils.getResource("PRO_SER_ACT_CLIENT_ID"));
                request.setSystemTrace(systemTrace);
                request.set("ClientTimeout", Long.valueOf(ResourceBundleUtils.getResource("PRO_SER_ACT_TIMEOUT")));
                ViettelMsg response = channelPro.send(request);
                return response;
                
                
//                ExchMsg request = new ExchMsg();
//                request.setCommand("IN_QUERYCARD");
//                request.set("ClientTimeout", 260000);//10 phut
//                request.set("SEQUENCE", activeCardForm.getFromSerial());
//                ViettelMsg msg = (ViettelMsg) channel.send(request);
//                ViettelMsg response = channel.send(request);
//                return response;
            }
        } catch (Exception e) {
            throw e;
        }
        return null;
    }
}
