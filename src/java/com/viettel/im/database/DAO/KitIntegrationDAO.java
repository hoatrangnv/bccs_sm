package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.KitIntegrationForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.TempIsdn;
import com.viettel.im.database.BO.TempSim;
import com.viettel.im.database.BO.UserToken;
import com.viettel.security.util.StringEscapeUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 *
 * @author thanhnv
 * @author TungTV
 */
public class KitIntegrationDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(KitIntegrationDAO.class);
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_ERR_LOG_PATH = "errLogPath";
    private final String REQUEST_ERR_LOG_MESSAGE = "errLogMessage";
    private final String REQUEST_DETAIL_SIM_RANGE_PATH = "detailSimRangePath";
    private final String REQUEST_DETAIL_SIM_RANGE_MESSAGE = "detailSimRangeMessage";
    private final String REQUEST_DETAIL_SIM_RANGE_MESSAGE_PARAM = "detailSimRangeMessageParam";
    private final String REQUEST_DETAIL_ISDN_RANGE_PATH = "detailIsdnRangePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE = "detailIsdnRangeMessage";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE_PARAM = "detailIsdnRangeMessageParam";
    private final String REQUEST_KIT_FILE_PATH = "kitFilePath";
    private final String REQUEST_KIT_FILE_MESSAGE = "kitFileMessage";
    private final String SESSION_SIM_LIST = "simList";
    private final String SESSION_NUMBER_LIST = "numberList";
    private final BigInteger NUMBER_ROW_PER_SHEET = new BigInteger("10000"); //so luong row/ sheet
    private final int MAX_RESULT = 10000; //so luong ban ghi trong 1 lan truy van
    //
    private final Long IMP_BY_SERIAL_RANGE = 1L;
    private final Long IMP_BY_FILE = 2L;
    private KitIntegrationForm form = new KitIntegrationForm();

    public KitIntegrationForm getForm() {
        return form;
    }

    public void setForm(KitIntegrationForm form) {
        this.form = form;
    }

    public String preparePage() throws Exception {
        log.debug("begin method preparePage of KitIntegrationDAO");

        HttpServletRequest httpServletRequest = getRequest();

        try {
            String userSessionId = httpServletRequest.getSession().getId();

            //xoa du lieu trong cac bang ta.m
            //bang tempSim
            String strQuery = "delete from TempSim where userSessionId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, userSessionId);
            int result = query.executeUpdate();
            //bang tempIsdn
            strQuery = "delete from TempIsdn where userSessionId = ? ";
            query = getSession().createQuery(strQuery);
            query.setParameter(0, userSessionId);
            result = query.executeUpdate();

            //xoa du lieu cac bien session
            httpServletRequest.getSession().removeAttribute(SESSION_SIM_LIST);
            httpServletRequest.getSession().removeAttribute(SESSION_NUMBER_LIST);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }


        log.debug("end method preparePage of KitIntegrationDAO");

        String pageForward = "linkSimSoPrepare";
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 19/05/2009
     * them dai sim moi (thay the cho ham searchsim)
     *
     */
    public String addSimRange() throws Exception {
        log.info("Begin method addSimRange of KitIntegrationDAO ...");

        HttpServletRequest httpServletRequest = getRequest();
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_SIM_LIST);
        if (listKitIntegrationForm == null) {
            listKitIntegrationForm = new ArrayList<KitIntegrationForm>();
            httpServletRequest.getSession().setAttribute(SESSION_SIM_LIST, listKitIntegrationForm);
        }

        String pageForward;

        if (this.form.getImpSimType().equals(IMP_BY_SERIAL_RANGE)) {
            //truong hop nhap du lieu sim theo dai
            String strResultCheck = checkValidSimRange();
            if (!strResultCheck.equals("")) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, getText(strResultCheck));
                pageForward = "searchSimComplete";
                return pageForward;
            } else {
                //chen du lieu vao bang tam
                insertTempSimRange();
            }

        } else if (this.form.getImpSimType().equals(IMP_BY_FILE)) {
            //truong hop nhap du lieu sim theo file
            List<String> listErrorLine = new ArrayList<String>();

            String serverFileName = StringEscapeUtils.getSafeFileName(this.form.getServerSimFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = httpServletRequest.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File simDataFile = new File(serverFilePath);
            if (!simDataFile.exists() || !simDataFile.isFile()) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.invalidDataFile");
                pageForward = "searchSimComplete";
                return pageForward;
            }

            BufferedReader bufferedReader = null;
            FileReader fileReader = null;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_kkmmss");
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);
            String errorLogFileName = "kitIntegration_ErrorLog_" + userToken.getLoginName() + "_" + dateFormat.format(new Date()) + ".txt";
            String tempPathErr = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String errLogFilePath = httpServletRequest.getSession().getServletContext().getRealPath(tempPathErr + errorLogFileName);
            File errorLogFile = new File(errLogFilePath);
            PrintWriter printWriter = null;

            try {
                fileReader = new FileReader(simDataFile);
                bufferedReader = new BufferedReader(fileReader);

                String line = null;
                int count = 0;
                //doc tung dong trong file du lieu, xu ly va day vao bien list session
                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        this.form.setFirstSimSerial(line);
                        this.form.setEndSimSerial(line);

                        String strResultCheck = checkValidSimRange();
                        if (!strResultCheck.equals("")) {
                            this.form.setFirstSimSerial("");
                            this.form.setEndSimSerial("");

                            listErrorLine.add(line + " - " + strResultCheck);
                            continue;
                        } else {
                            //chen du lieu vao bang tam
                            insertTempSimRange();
                        }
                    }
                }

                //trong truong hop co loi xay ra, ghi du lieu loi tu listErrorLine
                if (listErrorLine != null && listErrorLine.size() > 0) {
                    printWriter = new PrintWriter(errorLogFile);
                    for (int i = 0; i < listErrorLine.size(); i++) {
                        printWriter.println(listErrorLine.get(i));
                    }
                    printWriter.flush();
                    //
                    httpServletRequest.setAttribute(REQUEST_ERR_LOG_MESSAGE, "kitIntegration.error.errLogMessage");

                    httpServletRequest.setAttribute(REQUEST_ERR_LOG_PATH, tempPathErr + errorLogFileName);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                pageForward = "searchSimComplete";
                return pageForward;
            } catch (IOException e) {
                e.printStackTrace();
                httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                pageForward = "searchSimComplete";
                return pageForward;
            } catch (Exception e) {
                e.printStackTrace();
                httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                pageForward = "searchSimComplete";
                return pageForward;
            } finally {
                try {
                    if (bufferedReader != null && fileReader != null) {
                        bufferedReader.close();
                        fileReader.close();
                    }
                    if (printWriter != null) {
                        printWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                    pageForward = "searchSimComplete";
                    return pageForward;
                }
            }

        }

        log.info("End method addSimRange of KitIntegrationDAO");

        pageForward = "searchSimComplete";
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date 17/09/2009
     * chen du lieu vao bang tam
     *
     */
    private boolean insertTempSimRange() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();

        try {
            BigInteger startSerial = new BigInteger(this.form.getFirstSimSerial().trim());
            BigInteger endSerial = new BigInteger(this.form.getEndSimSerial().trim());

            String userSessionId = httpServletRequest.getSession().getId();

            Long KIT_INTEGRATION_SHOP_ID = Constant.KIT_INTEGRATION_SHOP_ID;
            ShopDAO shopDAO = new ShopDAO(getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(Constant.KIT_INTEGRATION_SHOP_CODE);
            if (shop != null && shop.getShopId() != null) {
                KIT_INTEGRATION_SHOP_ID = shop.getShopId();
            }

            Long KIT_INTEGRATION_SIM_STOCK_MODEL_ID = Constant.KIT_INTEGRATION_SIM_STOCK_MODEL_ID;
            StockModelDAO stockModelDAO = new StockModelDAO();
            StockModel stockModel = stockModelDAO.getStockModelByCode(Constant.KIT_INTEGRATION_SIM_STOCK_MODEL_CODE);
            if (stockModel != null && stockModel.getStockModelId() != null) {
                KIT_INTEGRATION_SIM_STOCK_MODEL_ID = stockModel.getStockModelId();
            }

            BaseStockDAO baseStockDAO = new BaseStockDAO();
            String strSimTableName = baseStockDAO.getTableNameByStockType(Constant.STOCK_SIM_PRE_PAID, BaseStockDAO.NAME_TYPE_NORMAL);
            String strQuery = "insert into TEMP_SIM(ID, USER_SESSION_ID, SIM_SERIAL, SIM_IMSI, PIN, PUK) "
                    + "(select TEMP_SIM_SEQ.NEXTVAL, '" + userSessionId + "', " + "a.SERIAL, a.IMSI, a.PIN, a.PUK "
                    + "from " + strSimTableName + " a "
                    + "where to_number(a.SERIAL) >= ? and to_number(a.SERIAL) <= ? "
                    + "and a.STOCK_MODEL_ID = ? and a.STATUS = ? and a.OWNER_ID = ?) "; //tim nhung sim mobile thuoc kho VT;
            Query query = getSession().createSQLQuery(strQuery);
            query.setParameter(0, startSerial);
            query.setParameter(1, endSerial);
            query.setParameter(2, KIT_INTEGRATION_SIM_STOCK_MODEL_ID);
            query.setParameter(3, Constant.STATUS_USE);
            query.setParameter(4, KIT_INTEGRATION_SHOP_ID);
            int result = query.executeUpdate();

            //thuc hien lap qua list cu
            //              - tim maxFormId de tao id gia (phuc vu viec xem chi tiet dai so + delete dai so)
            //              - tim tong so luong isdn trong list cu (phuc vu viec hien thi ra man hinh tong so isdn)
            List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_SIM_LIST);
            Long maxFormId = 0L;
            Long simTotalQuantity = 0L;
            for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);

                //tim maxFormId
                if (kitIntegrationForm.getFormId().compareTo(maxFormId) > 0) {
                    maxFormId = kitIntegrationForm.getFormId();
                }

                //tinh tong so isdn da co trong list
                simTotalQuantity += kitIntegrationForm.getSimQuantity();
            }

            //thiet lap id gia cua form
            this.form.setFormId(maxFormId + 1);
            //them vao bien session
            KitIntegrationForm tmpKitIntegrationForm = new KitIntegrationForm();
            BeanUtils.copyProperties(tmpKitIntegrationForm, this.form);
            listKitIntegrationForm.add(0, tmpKitIntegrationForm);

            //tinh tong so isdn da co trong list (them so isdn cua phan tu moi add)
            simTotalQuantity += this.form.getSimQuantity();

            this.form = new KitIntegrationForm();
            this.form.setSimTotalQuantity(simTotalQuantity);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }


        return true;
    }

    /**
     *
     * author tamdt1
     * date: 19/05/2009
     * kiem tra dai sim co hop le hay khong
     * ket qua tra ve xau rong "" neu dai sim hop le
     *
     */
    private String checkValidSimRange() throws Exception {
        String strStartSerial = this.form.getFirstSimSerial();
        String strEndSerial = this.form.getEndSimSerial();

        //kiem tra cac truong bat buoc
        if (strStartSerial == null || strStartSerial.trim().equals("")
                || strEndSerial == null || strEndSerial.trim().equals("")) {

            return "kitIntegration.error.requiredFieldsEmpty";
        }

        BigInteger startSerial;
        BigInteger endSerial;
        try {
            startSerial = new BigInteger(strStartSerial.trim());
            endSerial = new BigInteger(strEndSerial.trim());
        } catch (Exception ex) {
            return "kitIntegration.error.invalidSerialFormat";
        }

        //serial dau dai + cuoi dai phai la so khogn am
        if (startSerial.compareTo(BigInteger.ZERO) < 0 || endSerial.compareTo(BigInteger.ZERO) < 0) {
            return "kitIntegration.error.serialNegative";
        }

        //serial dau dai khong duoc lon hon serial cuoi dai
        if (startSerial.compareTo(endSerial) > 0) {
            return "kitIntegration.error.invalidSerial";
        }
        //kiem tra truong hop  serial vuot qua do dai cho phep
        if (strStartSerial.length() > Constant.MAX_LENGTH_SERIAL) {
            return "kitIntegration.error.invalidSerial";
        }
        if (strEndSerial.length() > Constant.MAX_LENGTH_SERIAL) {
            return "kitIntegration.error.invalidSerial";
        }

        //kiem tra dai serial da ton tai trong list chua
        HttpServletRequest httpServletRequest = getRequest();
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_SIM_LIST);
        if (listKitIntegrationForm != null && listKitIntegrationForm.size() > 0) {
            for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                KitIntegrationForm tmp = listKitIntegrationForm.get(i);
                BigInteger tmpStartSerial = new BigInteger(tmp.getFirstSimSerial());
                BigInteger tmpEndSerial = new BigInteger(tmp.getEndSimSerial());
                if ((tmpStartSerial.compareTo(startSerial) >= 0 && tmpStartSerial.compareTo(endSerial) <= 0)
                        || (tmpEndSerial.compareTo(startSerial) >= 0 && tmpEndSerial.compareTo(endSerial) <= 0)
                        || (startSerial.compareTo(tmpStartSerial) >= 0 && startSerial.compareTo(tmpEndSerial) <= 0)
                        || (endSerial.compareTo(tmpStartSerial) >= 0 && endSerial.compareTo(tmpEndSerial) <= 0)) {

                    return "kitIntegration.error.duplicateSerialRange";
                }
            }

        }


        Long KIT_INTEGRATION_SHOP_ID = Constant.KIT_INTEGRATION_SHOP_ID;
        ShopDAO shopDAO = new ShopDAO(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(Constant.KIT_INTEGRATION_SHOP_CODE);
        if (shop != null && shop.getShopId() != null) {
            KIT_INTEGRATION_SHOP_ID = shop.getShopId();
        }

        Long KIT_INTEGRATION_SIM_STOCK_MODEL_ID = Constant.KIT_INTEGRATION_SIM_STOCK_MODEL_ID;
        StockModelDAO stockModelDAO = new StockModelDAO();
        StockModel stockModel = stockModelDAO.getStockModelByCode(Constant.KIT_INTEGRATION_SIM_STOCK_MODEL_CODE);
        if (stockModel != null && stockModel.getStockModelId() != null) {
            KIT_INTEGRATION_SIM_STOCK_MODEL_ID = stockModel.getStockModelId();
        }



        //kiem tra dai serial co ton tai hay khong trong CSDL khong
        //      - sim lay tu kho Viettel
        //      - sim thuoc mat hang sim tra truoc
        //      - sim o trang thai trong kho va la sim moi
        String strQuery = "select count(*) "
                + "from StockSimPrePaid where to_number(serial) >= ? and to_number(serial) <= ? "
                + "and stockModelId = ? and status = ? and ownerType = ? and ownerId = ? and stateId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, startSerial);
        query.setParameter(1, endSerial);
        query.setParameter(2, KIT_INTEGRATION_SIM_STOCK_MODEL_ID);
        query.setParameter(3, Constant.STATUS_USE);
        query.setParameter(4, Constant.OWNER_TYPE_SHOP);
        query.setParameter(5, KIT_INTEGRATION_SHOP_ID);
        query.setParameter(6, Constant.STATE_NEW);
        List<Long> list = query.list();
        Long count = list.get(0);
        if (count.compareTo(0L) <= 0) {
            //dai serial khong ton tai
            return "kitIntegration.error.serialRangeNotExist";
        } else {
            this.form.setSimQuantity(count);
        }

        this.form.setFirstSimSerial(strStartSerial.trim());
        this.form.setEndSimSerial(strEndSerial.trim());


        return "";
    }

    /**
     *
     * author tamdt1
     * date: 21/05/2009
     * xoa dai serial khoi list
     *
     */
    public String delSimRange() throws Exception {
        log.debug("begin method delSimRange of KitIntegrationDAO");

        HttpServletRequest httpServletRequest = getRequest();
        String pageForward = "searchSimComplete";

        try {
            String strFormId = httpServletRequest.getParameter("formId");
            if (strFormId == null || strFormId.trim().equals("")) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.delete");
                return pageForward;
            }

            Long formId = Long.valueOf(strFormId);
            KitIntegrationForm selectedKitIntegrationForm = null;

            //tim kiem du lieu trong bien session
            List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_SIM_LIST);
            if (listKitIntegrationForm != null && listKitIntegrationForm.size() > 0) {
                for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                    KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
                    if (kitIntegrationForm.getFormId().equals(formId)) {
                        selectedKitIntegrationForm = kitIntegrationForm;
                        break;
                    }
                }

                //khong tim thay form trong bien session
                if (selectedKitIntegrationForm == null) {
                    httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.delete");
                    return pageForward;
                }


                BigInteger fromSerial = new BigInteger(selectedKitIntegrationForm.getFirstSimSerial());
                BigInteger toSerial = new BigInteger(selectedKitIntegrationForm.getFirstSimSerial());

                String userSessionId = httpServletRequest.getSession().getId();

                //xoa du lieu trong bang tam
                String strQuery = "delete from TempSim "
                        + "where to_number(simSerial) >= ? and to_number(simSerial) <= ? "
                        + "and userSessionId = ? ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, fromSerial);
                query.setParameter(1, toSerial);
                query.setParameter(2, userSessionId);
                int result = query.executeUpdate();

                //xoa du lieu trong bien session
                listKitIntegrationForm.remove(selectedKitIntegrationForm);

                //tinh lai so luong sim trong list
                Long simTotalQuantity = 0L;
                for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                    KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
                    simTotalQuantity += kitIntegrationForm.getSimQuantity();
                }
                this.form.setSimTotalQuantity(simTotalQuantity);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        log.debug("end method delSimRange of KitIntegrationDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/05/2009
     * phuc vu viec phan trang danh sach sim
     *
     */
    public String pageNagivatorSimRange() throws Exception {
        log.debug("begin method pageNagivatorSimRange of KitIntegrationDAO");

        HttpServletRequest httpServletRequest = getRequest();
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_SIM_LIST);

        if (listKitIntegrationForm != null) {
            Long simTotalQuantity = 0L;
            for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
                simTotalQuantity += kitIntegrationForm.getSimQuantity();
            }

            this.form.setSimTotalQuantity(simTotalQuantity);
        }


        String pageForward = "searchSimComplete";

        log.debug("end method pageNagivatorSimRange of KitIntegrationDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 19/05/2009
     * them dai sim moi (thay the cho ham searchisdn)
     *
     */
    public String addIsdnRange() throws Exception {
        log.info("Begin method addIsdnRange of KitIntegrationDAO ...");

        HttpServletRequest httpServletRequest = getRequest();
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_NUMBER_LIST);
        if (listKitIntegrationForm == null) {
            listKitIntegrationForm = new ArrayList<KitIntegrationForm>();
            httpServletRequest.getSession().setAttribute(SESSION_NUMBER_LIST, listKitIntegrationForm);
        }

        String pageForward;

        if (this.form.getImpIsdnType().equals(IMP_BY_SERIAL_RANGE)) {
            //truong hop nhap du lieu isdn theo dai
            String strResultCheck = checkValidIsdnRange();
            if (!strResultCheck.equals("")) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, strResultCheck);
                pageForward = "searchIsdnComplete";
                return pageForward;
            } else {
                //chen du lieu vao bang tam
                insertTempIsdnRange();
            }

        } else if (this.form.getImpIsdnType().equals(IMP_BY_FILE)) {
            //truong hop nhap du lieu isdn theo file
            List<String> listErrorLine = new ArrayList<String>();

            String serverFileName = StringEscapeUtils.getSafeFileName(this.form.getServerIsdnFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = httpServletRequest.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File simDataFile = new File(serverFilePath);
            BufferedReader bufferedReader = null;
            FileReader fileReader = null;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_kkmmss");
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);
            String errorLogFileName = "kitIntegration_ErrorLog_" + userToken.getLoginName() + "_" + dateFormat.format(new Date()) + ".txt";
            String tempPathErr = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String errLogFilePath = httpServletRequest.getSession().getServletContext().getRealPath(tempPathErr + errorLogFileName);
            File errorLogFile = new File(errLogFilePath);
            PrintWriter printWriter = null;

            try {
                fileReader = new FileReader(simDataFile);
                bufferedReader = new BufferedReader(fileReader);

                String line = null;
                int count = 0;
                //doc tung dong trong file du lieu, xu ly va day vao bien list session
                while ((line = bufferedReader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        this.form.setStartStockIsdn(line);
                        this.form.setEndStockIsdn(line);

                        String strResultCheck = checkValidIsdnRange();
                        if (!strResultCheck.equals("")) {
                            this.form.setStartStockIsdn("");
                            this.form.setEndStockIsdn("");
                            listErrorLine.add(line + " - " + getText(strResultCheck));
                            continue;
                        } else {
                            //chen du lieu vao bang tam
                            insertTempIsdnRange();
                        }
                    }
                }

                //trong truong hop co loi xay ra, ghi du lieu loi tu listErrorLine
                if (listErrorLine != null && listErrorLine.size() > 0) {
                    printWriter = new PrintWriter(errorLogFile);
                    for (int i = 0; i < listErrorLine.size(); i++) {
                        printWriter.println(listErrorLine.get(i));
                    }
                    printWriter.flush();
                    httpServletRequest.setAttribute(REQUEST_ERR_LOG_MESSAGE, "kitIntegration.error.errLogMessage");
                    httpServletRequest.setAttribute(REQUEST_ERR_LOG_PATH, tempPathErr + errorLogFileName);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                pageForward = "searchIsdnComplete";
                return pageForward;
            } catch (IOException e) {
                e.printStackTrace();
                httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                pageForward = "searchIsdnComplete";
                return pageForward;
            } catch (Exception e) {
                e.printStackTrace();
                httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                pageForward = "searchIsdnComplete";
                return pageForward;
            } finally {
                try {
                    if (bufferedReader != null && fileReader != null) {
                        bufferedReader.close();
                        fileReader.close();
                    }
                    if (printWriter != null) {
                        printWriter.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    httpServletRequest.setAttribute(REQUEST_MESSAGE, e.toString());
                    pageForward = "searchIsdnComplete";
                    return pageForward;
                }
            }

        }

        log.info("End method addIsdnRange of KitIntegrationDAO");

        pageForward = "searchIsdnComplete";
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date 17/09/2009
     * chen du lieu vao bang tam
     *
     */
    private boolean insertTempIsdnRange() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();

        try {
            //chen du lieu vao bang ta.m
            BigInteger startIsdn = new BigInteger(this.form.getStartStockIsdn().trim());
            BigInteger endIsdn = new BigInteger(this.form.getEndStockIsdn().trim());

            String userSessionId = httpServletRequest.getSession().getId();

            Long KIT_INTEGRATION_SHOP_ID = Constant.KIT_INTEGRATION_SHOP_ID;
            ShopDAO shopDAO = new ShopDAO(getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(Constant.KIT_INTEGRATION_SHOP_CODE);
            if (shop != null && shop.getShopId() != null) {
                KIT_INTEGRATION_SHOP_ID = shop.getShopId();
            }

            String strQuery = "insert into TEMP_ISDN(ID, USER_SESSION_ID, ISDN) "
                    + "(select TEMP_ISDN_SEQ.NEXTVAL, '" + userSessionId + "', " + "a.ISDN "
                    + "from STOCK_ISDN_MOBILE a "
                    + "where to_number(a.ISDN) >= ? and to_number(a.ISDN) <= ? "
                    + "and a.STATUS = ? and a.ISDN_TYPE = ? and a.OWNER_TYPE = ? and a.OWNER_ID = ?) "; //lay nhung so tra truoc moi tu kho dau lo hoac kho thu hoi

            Query query = getSession().createSQLQuery(strQuery);
            query.setParameter(0, startIsdn);
            query.setParameter(1, endIsdn);
            query.setParameter(2, Constant.STATUS_ISDN_NEW);
            query.setParameter(3, Constant.ISDN_TYPE_PRE);
            query.setParameter(4, Constant.OWNER_TYPE_SHOP);
            query.setParameter(5, KIT_INTEGRATION_SHOP_ID);

            int result = query.executeUpdate();

            //thuc hien lap qua list cu
            //              - tim maxFormId de tao id gia (phuc vu viec xem chi tiet dai so + delete dai so)
            //              - tim tong so luong isdn trong list cu (phuc vu viec hien thi ra man hinh tong so isdn)
            List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_NUMBER_LIST);
            Long maxFormId = 0L;
            Long isdnTotalQuantity = 0L;
            for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);

                //tim maxFormId
                if (kitIntegrationForm.getFormId().compareTo(maxFormId) > 0) {
                    maxFormId = kitIntegrationForm.getFormId();
                }

                //tinh tong so isdn da co trong list
                isdnTotalQuantity += kitIntegrationForm.getIsdnQuantity();
            }

            //thiet lap id gia cua form
            this.form.setFormId(maxFormId + 1);
            //them vao bien session
            KitIntegrationForm tmpKitIntegrationForm = new KitIntegrationForm();
            BeanUtils.copyProperties(tmpKitIntegrationForm, this.form);
            listKitIntegrationForm.add(0, tmpKitIntegrationForm);

            //tinh tong so isdn da co trong list (them so isdn cua phan tu moi add)
            isdnTotalQuantity += this.form.getIsdnQuantity();
            //
            this.form = new KitIntegrationForm();
            this.form.setIsdnTotalQuantity(isdnTotalQuantity);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 19/05/2009
     * kiem tra dai so co hop le hay khong
     * tra ve chuoi rong "" neu dai so hop le
     *
     */
    private String checkValidIsdnRange() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();

        String strStartIsdn = this.form.getStartStockIsdn();
        String strEndIsdn = this.form.getEndStockIsdn();

        //kiem tra cac truong bat buoc
        if (strStartIsdn == null || strStartIsdn.trim().equals("")
                || strEndIsdn == null || strEndIsdn.trim().equals("")) {

            return "kitIntegration.error.requiredFieldsEmpty";
        }

        BigInteger startIsdn;
        BigInteger endIsdn;
        try {
            startIsdn = new BigInteger(strStartIsdn.trim());
            endIsdn = new BigInteger(strEndIsdn.trim());
        } catch (Exception ex) {
            return "kitIntegration.error.invalidIsdnFormat";
        }

        //so dau dai + cuoi dai phai la so khogn am
        if (startIsdn.compareTo(BigInteger.ZERO) < 0 || endIsdn.compareTo(BigInteger.ZERO) < 0) {
            return "kitIntegration.error.isdnNegative";
        }

        //so dau dai khong duoc lon hon so cuoi dai
        if (startIsdn.compareTo(endIsdn) > 0) {
            return "kitIntegration.error.invalidIsdn";
        }
        //isdn co do dai qua do dai cho phep trong truong hop nhap tu file
        if (strStartIsdn.length() > Constant.MAX_LENGTH_ISDN) {
            return "kitIntegration.error.invalidIsdn";
        }
        if (strEndIsdn.length() > Constant.MAX_LENGTH_ISDN) {
            return "kitIntegration.error.invalidIsdn";
        }

        //kiem tra dai so da ton tai trong list chua
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_NUMBER_LIST);
        if (listKitIntegrationForm != null && listKitIntegrationForm.size() > 0) {
            for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                KitIntegrationForm tmp = listKitIntegrationForm.get(i);
                BigInteger tmpStartIsdn = new BigInteger(tmp.getStartStockIsdn());
                BigInteger tmpEndIsdn = new BigInteger(tmp.getEndStockIsdn());
                if ((tmpStartIsdn.compareTo(startIsdn) >= 0 && tmpStartIsdn.compareTo(endIsdn) <= 0)
                        || (tmpEndIsdn.compareTo(startIsdn) >= 0 && tmpEndIsdn.compareTo(endIsdn) <= 0)
                        || (startIsdn.compareTo(tmpStartIsdn) >= 0 && startIsdn.compareTo(tmpEndIsdn) <= 0)
                        || (endIsdn.compareTo(tmpStartIsdn) >= 0 && endIsdn.compareTo(tmpEndIsdn) <= 0)) {

                    return "kitIntegration.error.duplicateIsdnRange";
                }
            }

        }

        Long KIT_INTEGRATION_SHOP_ID = Constant.KIT_INTEGRATION_SHOP_ID;
        ShopDAO shopDAO = new ShopDAO(getSession());
        Shop shop = shopDAO.findShopsAvailableByShopCode(Constant.KIT_INTEGRATION_SHOP_CODE);
        if (shop != null && shop.getShopId() != null) {
            KIT_INTEGRATION_SHOP_ID = shop.getShopId();
        }

        String strQuery = "select  count(*) from StockIsdnMobile where to_number(isdn) >= ? and to_number(isdn) <= ? "
                + "and status = ? and isdnType = ? and ownerType = ? and ownerId = ?  ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, startIsdn);
        query.setParameter(1, endIsdn);
        query.setParameter(2, Constant.STATUS_ISDN_NEW);
        query.setParameter(3, Constant.ISDN_TYPE_PRE);
        query.setParameter(4, Constant.OWNER_TYPE_SHOP);
        query.setParameter(5, KIT_INTEGRATION_SHOP_ID);

        List<Long> list = query.list();
        Long count = list.get(0);
        if (count.compareTo(0L) <= 0) {
            //dai serial khong ton tai
            return "kitIntegration.error.isdnRangeNotExist";
        } else {
            this.form.setIsdnQuantity(count);
        }

        this.form.setStartStockIsdn(strStartIsdn.trim());
        this.form.setEndStockIsdn(strEndIsdn.trim());

        return "";
    }

    /**
     *
     * author tamdt1
     * date: 21/05/2009
     * xoa dai isdn khoi list
     *
     */
    public String delIsdnRange() throws Exception {
        log.debug("begin method delIsdnRange of KitIntegrationDAO");

        HttpServletRequest httpServletRequest = getRequest();
        String pageForward = "searchIsdnComplete";

        try {
            String strFormId = httpServletRequest.getParameter("formId");
            if (strFormId == null || strFormId.trim().equals("")) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.delete");
                return pageForward;
            }

            Long formId = Long.valueOf(strFormId);
            KitIntegrationForm selectedKitIntegrationForm = null;

            //tim kiem du lieu trong bien sesion
            List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_NUMBER_LIST);
            if (listKitIntegrationForm != null && listKitIntegrationForm.size() > 0) {
                for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                    KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
                    if (kitIntegrationForm.getFormId().equals(formId)) {
                        selectedKitIntegrationForm = kitIntegrationForm;
                        break;
                    }
                }

                //khong tim thay form trong bien session
                if (selectedKitIntegrationForm == null) {
                    httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.delete");
                    return pageForward;
                }

                BigInteger fromIsdn = new BigInteger(selectedKitIntegrationForm.getStartStockIsdn());
                BigInteger toIsdn = new BigInteger(selectedKitIntegrationForm.getEndStockIsdn());

                String userSessionId = httpServletRequest.getSession().getId();

                //xoa du lieu trong bang tam
                String strQuery = "delete from TempIsdn "
                        + "where to_number(isdn) >= ? and to_number(isdn) <= ? "
                        + "and userSessionId = ? ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, fromIsdn);
                query.setParameter(1, toIsdn);
                query.setParameter(2, userSessionId);
                int result = query.executeUpdate();


                //xoa du lieu trong bien session
                listKitIntegrationForm.remove(selectedKitIntegrationForm);

                //tinh lai tong so isdn trong list
                Long isdnTotalQuantity = 0L;
                for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                    KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
                    isdnTotalQuantity += kitIntegrationForm.getIsdnQuantity();
                }
                this.form.setIsdnTotalQuantity(isdnTotalQuantity);

            } else {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.delete");
                return pageForward;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        log.debug("end method delIsdnRange of KitIntegrationDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/05/2009
     * phuc vu viec phan trang danh sach isdn
     *
     */
    public String pageNagivatorIsdnRange() throws Exception {
        log.debug("begin method pageNagivatorIsdnRange of KitIntegrationDAO");

        HttpServletRequest httpServletRequest = getRequest();
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_NUMBER_LIST);

        if (listKitIntegrationForm != null) {
            Long isdnTotalQuantity = 0L;
            for (int i = 0; i < listKitIntegrationForm.size(); i++) {
                KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
                isdnTotalQuantity += kitIntegrationForm.getIsdnQuantity();
            }

            this.form.setIsdnTotalQuantity(isdnTotalQuantity);
        }

        String pageForward = "searchIsdnComplete";

        log.debug("end method pageNagivatorIsdnRange of KitIntegrationDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/05/2009
     * tao file ghep lo tu cac bang tam
     *
     */
    public String createKitFile() throws Exception {
        log.debug("begin method createKitFile of KitIntegrationDAO");

        HttpServletRequest httpServletRequest = getRequest();

        try {
            String userSessionId = httpServletRequest.getSession().getId();

            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
            filePath += "kitIntegration_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".txt";
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);
            PrintWriter output = null;
            File f = new File(realPath);
            output = new PrintWriter(f);

            Long qtySim = 0L;
            Long qtyIsdn = 0L;

            while (true) {
                //doc du lieu tu bang tempSim
                String strQuerySim = "from TempSim where userSessionId = ?";
                Query querySim = getSession().createQuery(strQuerySim);
                querySim.setParameter(0, userSessionId);
                querySim.setMaxResults(MAX_RESULT);
                List<TempSim> listTempSim = querySim.list();

                //doc du lieu tu bang tempIsdn
                String strQueryIsdn = "from TempIsdn where userSessionId = ?";
                Query queryIsdn = getSession().createQuery(strQueryIsdn);
                queryIsdn.setParameter(0, userSessionId);
                queryIsdn.setMaxResults(MAX_RESULT);
                List<TempIsdn> listTempIsdn = queryIsdn.list();

                if (listTempSim == null || listTempSim.size() == 0
                        || listTempIsdn == null || listTempIsdn.size() == 0) {
                    break;
                }

                qtySim += listTempSim.size();
                qtyIsdn += listTempIsdn.size();

                int minSize = listTempSim.size() < listTempIsdn.size() ? listTempSim.size() : listTempIsdn.size();
                List<Long> listTempSimId = new ArrayList<Long>();
                List<Long> listTempIsdnId = new ArrayList<Long>();

                for (int i = 0; i < minSize; i++) {
                    TempSim tmpTempSim = listTempSim.get(i);
                    TempIsdn tmpTempIsdn = listTempIsdn.get(i);
                    String line = ""
                            + tmpTempSim.getSimSerial() + " "
                            + tmpTempSim.getSimImsi() + " "
                            + tmpTempIsdn.getIsdn() + "";
//                            + tmpTempSim.getPin() + ","
//                            + tmpTempSim.getPuk() + "";

                    output.println(line);
                    listTempSimId.add(tmpTempSim.getId());
                    listTempIsdnId.add(tmpTempIsdn.getId());
                }

                //xoa du lieu trong bang tam
                String strQueryDeleteSim = "delete from TempSim where id in (:listTempSimId)";
                Query queryDeleteSim = getSession().createQuery(strQueryDeleteSim);
                queryDeleteSim.setParameterList("listTempSimId", listTempSimId);
                queryDeleteSim.executeUpdate();

                String strQueryDeleteIsdn = "delete from TempIsdn where id in (:listTempIsdnId)";
                Query queryDeleteIsdn = getSession().createQuery(strQueryDeleteIsdn);
                queryDeleteIsdn.setParameterList("listTempIsdnId", listTempIsdnId);
                queryDeleteIsdn.executeUpdate();
            }

            output.flush();
            output.close();

            if (!qtySim.equals(0L) && !qtyIsdn.equals(0L)) {
                httpServletRequest.setAttribute(REQUEST_KIT_FILE_PATH, filePath);
                httpServletRequest.setAttribute(REQUEST_KIT_FILE_MESSAGE, "kitIntegration.downloadMessage");
            }else{
                httpServletRequest.setAttribute(REQUEST_KIT_FILE_MESSAGE, "Data is empty!");
            }

            //xoa du lieu trong cac bang ta.m
            //bang tempSim
            String strQuery = "delete from TempSim where userSessionId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, userSessionId);
            int result = query.executeUpdate();
            //bang tempIsdn
            strQuery = "delete from TempIsdn where userSessionId = ? ";
            query = getSession().createQuery(strQuery);
            query.setParameter(0, userSessionId);
            result = query.executeUpdate();

            //xoa du lieu cac bien session
            httpServletRequest.getSession().removeAttribute(SESSION_SIM_LIST);
            httpServletRequest.getSession().removeAttribute(SESSION_NUMBER_LIST);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }


        log.debug("end method createKitFile of KitIntegrationDAO");

        String pageForward = "linkSimSoPrepare";
        return pageForward;
    }

    /***
     * AnDv
     * modified 28/9/09
     * @return
     * @throws Exception
     */
    public String detailSimRange() throws Exception {
        log.debug("begin method detailIsdnRange of KitIntegrationDAO");
        HttpServletRequest httpServletRequest = getRequest();


        String pageForward = "searchSimComplete";

        String strFormId = httpServletRequest.getParameter("formId");
        if (strFormId == null || strFormId.trim().equals("")) {
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.rangeNotExist");
            return pageForward;
        }

        Long formId = Long.valueOf(strFormId);
        KitIntegrationForm selectedKitIntegrationForm = null;

        //tim kiem du lieu trong bien sesion
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_SIM_LIST);
        if (listKitIntegrationForm == null || listKitIntegrationForm.size() == 0) {
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.rangeNotExist");
            return pageForward;
        }
        for (int i = 0; i < listKitIntegrationForm.size(); i++) {
            KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
            if (kitIntegrationForm.getFormId().equals(formId)) {
                selectedKitIntegrationForm = kitIntegrationForm;
                break;
            }
        }

        if (selectedKitIntegrationForm == null) {
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.rangeNotExist");
            return pageForward;
        }

        BigInteger fromSerial = new BigInteger(selectedKitIntegrationForm.getFirstSimSerial());
        BigInteger toSerial = new BigInteger(selectedKitIntegrationForm.getEndSimSerial());


        //ghi du lieu ra file
        try {

            Long KIT_INTEGRATION_SHOP_ID = Constant.KIT_INTEGRATION_SHOP_ID;
            ShopDAO shopDAO = new ShopDAO(getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(Constant.KIT_INTEGRATION_SHOP_CODE);
            if (shop != null && shop.getShopId() != null) {
                KIT_INTEGRATION_SHOP_ID = shop.getShopId();
            }

            Long KIT_INTEGRATION_SIM_STOCK_MODEL_ID = Constant.KIT_INTEGRATION_SIM_STOCK_MODEL_ID;
            StockModelDAO stockModelDAO = new StockModelDAO();
            StockModel stockModel = stockModelDAO.getStockModelByCode(Constant.KIT_INTEGRATION_SIM_STOCK_MODEL_CODE);
            if (stockModel != null && stockModel.getStockModelId() != null) {
                KIT_INTEGRATION_SIM_STOCK_MODEL_ID = stockModel.getStockModelId();
            }



            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
            filePath += "kitIntegration_detailSim_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".txt";
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);

            BigInteger serialQuantity = toSerial.subtract(fromSerial).add(BigInteger.ONE); //so luong serial
            BigInteger numberSheet = serialQuantity.divide(NUMBER_ROW_PER_SHEET);
            numberSheet = numberSheet.add(BigInteger.ONE); //them 1 sheet de chua cac phan tu con lai (trong truong hop co' du*)
            BigInteger index = new BigInteger("0");

            //mo file chuan bi ghi du lieu
            PrintWriter output = null;
            File file = new File(realPath);
            output = new PrintWriter(file);
//Query: chi select serial de giam thoi gian truy van
            while (index.compareTo(numberSheet) < 0) {
                BigInteger tmpFromSerial = fromSerial.add(index.multiply(NUMBER_ROW_PER_SHEET));
                BigInteger tmpToSerial = tmpFromSerial.add(NUMBER_ROW_PER_SHEET).subtract(BigInteger.ONE);
                if (tmpToSerial.compareTo(toSerial) > 0) {
                    //truong hop lan lap cuoi cung (so ban ghi le)
                    tmpToSerial = toSerial;
                }


                String strQuery = "select serial from StockSimPrePaid where to_number(serial) >= ? and to_number(serial) <= ? "
                        + "and stockModelId = ? and status = ? and ownerType = ? and ownerId = ? and stateId = ? ";

                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, tmpFromSerial);
                query.setParameter(1, tmpToSerial);
//                query.setParameter(2, Constant.KIT_INTEGRATION_SIM_STOCK_MODEL_ID);
                query.setParameter(2, KIT_INTEGRATION_SIM_STOCK_MODEL_ID);
                query.setParameter(3, Constant.STATUS_USE);
                query.setParameter(4, Constant.OWNER_TYPE_SHOP);
//                query.setParameter(5, Constant.VT_SHOP_ID);
                query.setParameter(5, KIT_INTEGRATION_SHOP_ID);
                query.setParameter(6, Constant.STATE_NEW);

                List<String> tmpListStockSimPrePaid = query.list();

                if (tmpListStockSimPrePaid != null && tmpListStockSimPrePaid.size() > 0) {
                    for (String stockSimPrePaid : tmpListStockSimPrePaid) {
                        output.println(stockSimPrePaid);
                    }
                }
                output.flush();
                index = index.add(BigInteger.ONE);
            }
            output.close();

            httpServletRequest.setAttribute(REQUEST_DETAIL_SIM_RANGE_PATH, filePath);
            httpServletRequest.setAttribute(REQUEST_DETAIL_SIM_RANGE_MESSAGE, "kitIntegration.detailSerialRangeMessage");
            List<String> listSerialRangeParam = new ArrayList<String>();
            listSerialRangeParam.add(selectedKitIntegrationForm.getFirstSimSerial());
            listSerialRangeParam.add(selectedKitIntegrationForm.getEndSimSerial());
            httpServletRequest.setAttribute(REQUEST_DETAIL_SIM_RANGE_MESSAGE_PARAM, listSerialRangeParam);

        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        log.debug("end method detailIsdnRange of KitIntegrationDAO");

        return pageForward;
    }

    /***
     * AnDv
     * modified 28/9/09
     * @return
     * @throws Exception
     */
    public String detailIsdnRange() throws Exception {
        log.debug("begin method detailIsdnRange of KitIntegrationDAO");
        HttpServletRequest httpServletRequest = getRequest();

        String pageForward = "searchIsdnComplete";


        String strFormId = httpServletRequest.getParameter("formId");
        if (strFormId == null || strFormId.trim().equals("")) {
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.rangeNotExist");
            return pageForward;
        }

        Long formId = Long.valueOf(strFormId);
        KitIntegrationForm selectedKitIntegrationForm = null;

        //tim kiem du lieu trong bien sesion
        List<KitIntegrationForm> listKitIntegrationForm = (List<KitIntegrationForm>) httpServletRequest.getSession().getAttribute(SESSION_NUMBER_LIST);
        if (listKitIntegrationForm == null || listKitIntegrationForm.size() == 0) {
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.rangeNotExist");
            return pageForward;
        }
        for (int i = 0; i < listKitIntegrationForm.size(); i++) {
            KitIntegrationForm kitIntegrationForm = listKitIntegrationForm.get(i);
            if (kitIntegrationForm.getFormId().equals(formId)) {
                selectedKitIntegrationForm = kitIntegrationForm;
                break;
            }
        }

        if (selectedKitIntegrationForm == null) {
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "kitIntegration.error.rangeNotExist");
            return pageForward;
        }

        BigInteger fromIsdn = new BigInteger(selectedKitIntegrationForm.getStartStockIsdn());
        BigInteger toIsdn = new BigInteger(selectedKitIntegrationForm.getEndStockIsdn());


        //ghi du lieu ra file
        try {

            Long KIT_INTEGRATION_SHOP_ID = Constant.KIT_INTEGRATION_SHOP_ID;
            ShopDAO shopDAO = new ShopDAO(getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(Constant.KIT_INTEGRATION_SHOP_CODE);
            if (shop != null && shop.getShopId() != null) {
                KIT_INTEGRATION_SHOP_ID = shop.getShopId();
            }


            String DATE_FORMAT = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            filePath = filePath != null ? filePath : "/";
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
            filePath += "kitIntegration_detailIsdn_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".txt";
            String realPath = httpServletRequest.getSession().getServletContext().getRealPath(filePath);

            //doan ghi ra file text, thay the cho doan ghi ra file excel
            //do du lieu co the nhieu -> phan doan, doc NUMBER_ROW_PER_SHEET/ 1 lan
            BigInteger isdnQuantity = toIsdn.subtract(fromIsdn).add(BigInteger.ONE); //so luong serial
            BigInteger numberSheet = isdnQuantity.divide(NUMBER_ROW_PER_SHEET); //so luong lan truy van CSDL
            numberSheet = numberSheet.add(BigInteger.ONE); //them 1 lan truy van de lay cac phan tu con lai
            BigInteger index = new BigInteger("0");

            //mo file chuan bi ghi du lieu
            PrintWriter output = null;
            File file = new File(realPath);
            output = new PrintWriter(file);

            while (index.compareTo(numberSheet) < 0) {
                BigInteger tmpFromIsdn = fromIsdn.add(index.multiply(NUMBER_ROW_PER_SHEET));
                BigInteger tmpToIsdn = tmpFromIsdn.add(NUMBER_ROW_PER_SHEET).subtract(BigInteger.ONE);
                if (tmpToIsdn.compareTo(toIsdn) > 0) {
                    //truong hop lan lap cuoi cung (so ban ghi le)
                    tmpToIsdn = toIsdn;
                }

                String strQuery = "select isdn from StockIsdnMobile where to_number(isdn) >= ? and to_number(isdn) <= ? "
                        + "and status = ? and isdnType = ? and ownerType = ? and ownerId = ?  ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, tmpFromIsdn);
                query.setParameter(1, tmpToIsdn);
                query.setParameter(2, Constant.STATUS_ISDN_NEW);
                query.setParameter(3, Constant.ISDN_TYPE_PRE);
                query.setParameter(4, Constant.OWNER_TYPE_SHOP);
                query.setParameter(5, KIT_INTEGRATION_SHOP_ID);

                List<String> tmpListStockIsdnMobile = query.list();

                if (tmpListStockIsdnMobile != null && tmpListStockIsdnMobile.size() > 0) {
                    for (String stockIsdnMobile : tmpListStockIsdnMobile) {
                        output.println(stockIsdnMobile);
                    }
                }
                output.flush();
                index = index.add(BigInteger.ONE);

            }

            output.close();

            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_PATH, filePath);
            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE, "kitIntegration.detailIsdnRangeMessage");
            List<String> listIsdnRangeMessageParam = new ArrayList<String>();
            listIsdnRangeMessageParam.add(selectedKitIntegrationForm.getStartStockIsdn());
            listIsdnRangeMessageParam.add(selectedKitIntegrationForm.getEndStockIsdn());
            httpServletRequest.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE_PARAM, listIsdnRangeMessageParam);

        } catch (Exception ex) {
            ex.printStackTrace();
            httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return pageForward;
        }

        log.debug("end method detailIsdnRange of KitIntegrationDAO");

        return pageForward;
    }
}
