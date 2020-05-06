package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.AssignChannelTypeForSerialForm;
import com.viettel.im.client.form.AssignStockModelForIsdnForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
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
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import com.viettel.im.database.BO.IsdnTrans;
import com.viettel.im.database.BO.IsdnTransDetail;
import com.viettel.security.util.StringEscapeUtils;
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
 * author   : Doan Thanh 8
 * desc     : gan kenh cho dai serial hang hoa
 *
 */
public class AssignChannelTypeForSerialDAO extends BaseDAOAction {
    private static final Log log = LogFactory.getLog(AssignChannelTypeForSerialDAO.class);
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private final String REQUEST_MESSAGE_LIST = "messageList";
    private final String REQUEST_MESSAGE_LIST_PARAM = "messageListParam";
    private final String REQUEST_DETAIL_ISDN_RANGE_PATH = "detailIsdnRangePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE = "detailIsdnRangeMessage";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String REQUEST_IS_FILE_PREVIEW_MODE = "isFilePreviewMode";
    private final String SESSION_LIST_SERIAL_RANGE = "listSerialRange";

    //cac hang so forward
    private String pageForward;
    private final String ASSIGN_CHANNEL_TYPE_FOR_SERIAL = "assignChannelTypeForSerial";

    //
    private Long IMP_BY_RANGE = 1L;
    private Long IMP_BY_FILE = 2L;
    private int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi se commit 1 lan

    //bien form
    private AssignChannelTypeForSerialForm assignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();

    public AssignChannelTypeForSerialForm getAssignChannelTypeForSerialForm() {
        return assignChannelTypeForSerialForm;
    }

    public void setAssignChannelTypeForSerialForm(AssignChannelTypeForSerialForm assignChannelTypeForSerialForm) {
        this.assignChannelTypeForSerialForm = assignChannelTypeForSerialForm;
    }

    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * desc     : chuan bi form de gan kenh cho dai serial hang hoa
     *
     */
    public String preparePage() throws Exception {
        log.debug("begin method preparePage of AssignChannelTypeForSerialDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            //resetForm
            this.assignChannelTypeForSerialForm.resetForm();

            //
            removeTabSession(SESSION_LIST_SERIAL_RANGE);

            //set loai dich vu mac dinh la kit
//            this.assignChannelTypeForSerialForm.setStockTypeId(Constant.STOCK_KIT);

            //lay du lieu cho combobox
            getDataForCombobox();

            //
            List<String> listMessage = new ArrayList<String>();
            String userSessionId = req.getSession().getId();
            AssignChannelTypeForSerialDAO.listProgressMessage.put(userSessionId, listMessage);

            //
            //set kho mac dinh la kho hien tai
            this.assignChannelTypeForSerialForm.setShopId(userToken.getShopId());
            this.assignChannelTypeForSerialForm.setShopCode(userToken.getShopCode());
            this.assignChannelTypeForSerialForm.setShopName(userToken.getShopName());
            //haint41 3/10/2011 : set mac dinh kho hien tai khi chon nhap du lieu theo file
            this.assignChannelTypeForSerialForm.setShopCodeNew(userToken.getShopCode());
            this.assignChannelTypeForSerialForm.setShopNameNew(userToken.getShopName());

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
        pageForward = ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
        log.debug("end method preparePage of AssignChannelTypeForSerialDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * desc     : tim kiem cac dai serial thoa man dieu kien
     *
     */
    public String searchSerialRange() throws Exception {
        log.info("begin method searchSerialRange of AssignChannelTypeForSerialDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            Long impType = this.assignChannelTypeForSerialForm.getImpType();

            if (impType.equals(IMP_BY_RANGE)) {
                String resultCheckValidSerialRange = checkValidSerialRange();
                if (resultCheckValidSerialRange == null || !resultCheckValidSerialRange.equals("")) {
                    //
                    req.setAttribute(REQUEST_MESSAGE, resultCheckValidSerialRange);

                    //
                    getDataForCombobox();

                    //return
                    pageForward = ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
                    log.info("end method searchSerialRange of AssignChannelTypeForSerialDAO");
                    return pageForward;
                }

                Long stockTypeId = this.assignChannelTypeForSerialForm.getStockTypeId();
                Long shopId = this.assignChannelTypeForSerialForm.getShopId();
                Long stockModelId = this.assignChannelTypeForSerialForm.getStockModelId();
                String strFromSerial = this.assignChannelTypeForSerialForm.getFromSerial();
                String strToSerial = this.assignChannelTypeForSerialForm.getToSerial();
                Long channelTypeId = this.assignChannelTypeForSerialForm.getChannelTypeId();

                String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                List listParameter = new ArrayList();

                //
                //cau lenh select ra cac khoang isdn theo min, max, isdn_type, status, owner_id
                StringBuffer strIsdnRangeQuery = new StringBuffer("");
                strIsdnRangeQuery.append("SELECT MIN(serial) lb, MAX (serial) ub, channel_type_id, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("FROM (SELECT to_number(serial) serial, ");
                strIsdnRangeQuery.append("          to_number(serial) - ROW_NUMBER () OVER (ORDER BY channel_type_id, owner_id, stock_model_id, to_number(serial)) rn, ");
                strIsdnRangeQuery.append("          channel_type_id, owner_id, stock_model_id ");
                strIsdnRangeQuery.append("      FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
                strIsdnRangeQuery.append("      WHERE 1 = 1 ");
                strIsdnRangeQuery.append("          and to_number(a.serial) >= to_number(?) ");
                listParameter.add(strFromSerial.trim());
                strIsdnRangeQuery.append("          and to_number(a.serial) <= to_number(?) ");
                listParameter.add(strToSerial.trim());

                if (shopId != null && shopId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("       and a.owner_type = ? and a.owner_id = ? ");
                    listParameter.add(Constant.OWNER_TYPE_SHOP);
                    listParameter.add(shopId);
                }

                if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("      and a.stock_model_id = ? ");
                    listParameter.add(stockModelId);
                }

                if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
                    strIsdnRangeQuery.append("      and a.channel_type_id = ? ");
                    listParameter.add(channelTypeId);
                }

                //chi cho phep gan lai kenh doi voi cac serial trong kho
                strIsdnRangeQuery.append("      and status = ? ");
                listParameter.add(Constant.STATUS_ACTIVE);

                strIsdnRangeQuery.append("      ) ");
                strIsdnRangeQuery.append("GROUP BY channel_type_id, owner_id, stock_model_id, rn ");
                strIsdnRangeQuery.append("ORDER BY to_number(lb) ");

                //join bang shop voi cau lenh tren de tim ra danh sach cac khoang serial + thong tin ve kho chua serial
                //ham khoi tao: 
                StringBuffer strSearchQuery = new StringBuffer("");
                strSearchQuery.append("SELECT rownum formId, " + stockTypeId + " stockTypeId, " +
                        "a.lb fromSerial, a.ub toSerial, (a.ub - a.lb + 1) countSerial, " +
                        "a.owner_id shopId, b.shop_code shopCode, b.name shopName, " +
                        "a.stock_model_id stockModelId, " +
                        "(SELECT c.stock_model_code FROM stock_model c WHERE c.stock_model_id = a.stock_model_id) stockModelCode, " +
                        "(SELECT c.NAME FROM stock_model c WHERE c.stock_model_id = a.stock_model_id) stockModelName, " +
                        "a.channel_type_id channelTypeId, " +
                        "(SELECT c.name FROM channel_type c WHERE c.channel_type_id = a.channel_type_id) channelTypeName ");
                strSearchQuery.append("FROM (").append(strIsdnRangeQuery).append(") a, shop b ");
                strSearchQuery.append("WHERE a.owner_id = b.shop_id ");

                Query searchQuery = getSession().createSQLQuery(strSearchQuery.toString())
                        .addScalar("formId", Hibernate.LONG)
                        .addScalar("stockTypeId", Hibernate.LONG)
                        .addScalar("fromSerial", Hibernate.STRING)
                        .addScalar("toSerial", Hibernate.STRING)
                        .addScalar("countSerial", Hibernate.LONG)
                        .addScalar("shopId", Hibernate.LONG)
                        .addScalar("shopCode", Hibernate.STRING)
                        .addScalar("shopName", Hibernate.STRING)
                        .addScalar("stockModelId", Hibernate.LONG)
                        .addScalar("stockModelCode", Hibernate.STRING)
                        .addScalar("stockModelName", Hibernate.STRING)
                        .addScalar("channelTypeId", Hibernate.LONG)
                        .addScalar("channelTypeName", Hibernate.STRING)
                        .setResultTransformer(Transformers.aliasToBean(AssignChannelTypeForSerialForm.class));

                for (int i = 0; i < listParameter.size(); i++) {
                    searchQuery.setParameter(i, listParameter.get(i));
                }

                List<AssignChannelTypeForSerialForm> listAssignChannelTypeForSerialForm = searchQuery.list();
                setTabSession(SESSION_LIST_SERIAL_RANGE, listAssignChannelTypeForSerialForm);

                if (listAssignChannelTypeForSerialForm != null && listAssignChannelTypeForSerialForm.size() != 0) {
                    req.setAttribute(REQUEST_MESSAGE, "About " + listAssignChannelTypeForSerialForm.size() + " results");
                } else {
                    req.setAttribute(REQUEST_MESSAGE, "No results found");
                }

            } else {
                req.setAttribute(REQUEST_MESSAGE, "!!!Err. Imp type not correct");

            }

            //
            getDataForCombobox();


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
        pageForward = ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
        log.info("end method searchSerialRange of AssignChannelTypeForSerialDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 06/09/2010
     * desc     : xem truoc noi dung file
     *
     */
    public String filePreview() throws Exception {
        log.info("Begin filePreview of AssignChannelTypeForSerialDAO");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            String serverFileName = StringEscapeUtils.getSafeFileName(this.assignChannelTypeForSerialForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow  - 1; //tru di dong dau tien
            List<AssignChannelTypeForSerialForm> listRowInFile = new ArrayList<AssignChannelTypeForSerialForm>();
            numberRow = numberRow > 1000 ? 1000 : numberRow; //truong hop file > 1000 row -> chi hien thi 1000 row dau tien

            //haint41 12/10/2011 : them phan kiem tra noi dung file excel
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ERR.STK.125", getText("ERR.STK.125")); //!!!Lỗi. Cột serial và kênh mới không được để trống
            hashMapError.put("ERR.STK.126", getText("ERR.STK.126")); //!!!Lỗi. Cột serial hoặc mã kênh mới không phải là số nguyên dương
            hashMapError.put("ERR.STK.127", getText("ERR.STK.127")); //!!!Lỗi. Cột mã kênh mới không tồn tại
            hashMapError.put("ERR.STK.128", getText("ERR.STK.128")); //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
            hashMapError.put("ERR.STK.133", getText("ERR.STK.133")); //!!!Lỗi. Serial không thuộc chi nhánh
//            List<AssignChannelTypeForSerialForm> listError = new ArrayList<AssignChannelTypeForSerialForm>();
            HashMap<String, Long> hashMapStockType = new HashMap<String, Long>();
            //kiem tra stock model co ton tai khong
            Long stockTypeId = -1L;
            Long stockModelId = -1L;
            String stockModelName = "";
            String stockModelCode = this.assignChannelTypeForSerialForm.getStockModelCode();

            //kiem tra cac truong bat buoc
            if (stockModelCode == null || stockModelCode.trim().equals("")) {
                req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);
                req.setAttribute(REQUEST_MESSAGE, "assignChannelTypeForSerial.error.requiredFieldsEmpty");
                return ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
            }
            
            if (stockModelCode != null && !stockModelCode.trim().equals("")) {
                String strStockModelQuery = "from StockModel a "
                        + "where lower(a.stockModelCode) = ? and a.status = ? ";
                Query stockModelQuery = getSession().createQuery(strStockModelQuery);
                stockModelQuery.setParameter(0, stockModelCode.trim().toLowerCase());
                stockModelQuery.setParameter(1, Constant.STATUS_ACTIVE);
                List<StockModel> listStockModel = stockModelQuery.list();
                if (listStockModel == null || listStockModel.isEmpty()) {
                    req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);
                    req.setAttribute(REQUEST_MESSAGE, "assignChannelTypeForSerial.error.stockModelNotExist");
                    return ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
                }
                stockModelId = listStockModel.get(0).getStockModelId();
                stockTypeId = listStockModel.get(0).getStockTypeId();
                stockModelName = listStockModel.get(0).getName();
            }

            //kiem tra su ton tai cua stock_type_id
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);
                req.setAttribute(REQUEST_MESSAGE, "assignChannelTypeForSerial.error.stockTypeTableNotExist");
                return ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
            }            
            String shopCode = this.assignChannelTypeForSerialForm.getShopCodeNew();
            Long shopId = 0L;
            String shopName = "";
            if (shopCode != null && !shopCode.trim().equals("")) {
                String strShopQuery = "from Shop a "
                        + "where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                shopQuery.setParameter(3, userToken.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.isEmpty()) {
                    req.setAttribute(REQUEST_MESSAGE, "assignChannelTypeForSerial.error.shopCodeNotExist");
                    return ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
                }
                shopId = listShop.get(0).getShopId();
                shopName = listShop.get(0).getName();
            }
            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet
                String tmpStrSerial = sheet.getCell(0, rowIndex).getContents(); //serial
//                String tmpStrNewChannelTypeId = sheet.getCell(1, rowIndex).getContents(); //ma kenh moi
//
//                AssignChannelTypeForSerialForm tmpAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
//                tmpAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
//                tmpAssignChannelTypeForSerialForm.setChannelTypeName(tmpStrNewChannelTypeId);
//
//                listRowInFile.add(tmpAssignChannelTypeForSerialForm);
//            }

                BigInteger tmpSerial = new BigInteger("-1");
                String tmpStrNewChannelType = sheet.getCell(1, rowIndex).getContents();
                if (tmpStrSerial == null || tmpStrSerial.trim().equals("") || tmpStrNewChannelType == null || tmpStrNewChannelType.trim().equals("")) {
                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                    errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.125")); //!!!Lỗi. Cột serial và kênh mới không được để trống                    
                    listRowInFile.add(errAssignChannelTypeForSerialForm);
                    continue;
                } else {
                    tmpStrSerial = tmpStrSerial.trim();
                    tmpStrNewChannelType = tmpStrNewChannelType.trim();
                    try {
                        tmpSerial = new BigInteger(tmpStrSerial);
                    } catch (NumberFormatException ex) {
                        //serial khong dung dinh dang
                        AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                        errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                        errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.126")); //!!!Lỗi. Cột serial khong phai la so nguyen duong
                        listRowInFile.add(errAssignChannelTypeForSerialForm);
                        continue;
                    }
                }

                Long tmpOldChannelTypeId = -1L;
                tmpOldChannelTypeId = getSerialChannelTypeId(strTableName, tmpSerial);
                if (tmpOldChannelTypeId == null) {
                    //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                    errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.128")); //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                    listRowInFile.add(errAssignChannelTypeForSerialForm);
                    continue;
                }               
                //Check dieu kien : chi nhanh chi duoc gan kenh voi cac hang hoa thuoc chi nhanh
                
                BaseStock stockInfo = getOwnerId(tmpSerial, strTableName, stockModelId);
                if (stockInfo == null) {
                    //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                    errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.128")); //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                    listRowInFile.add(errAssignChannelTypeForSerialForm);
                    continue;
                }                           
              
                AssignChannelTypeForSerialForm tmpAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                tmpAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                tmpAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                tmpAssignChannelTypeForSerialForm.setCountSerial(1L);
                tmpAssignChannelTypeForSerialForm.setStockModelCode(stockModelCode);
                tmpAssignChannelTypeForSerialForm.setStockModelName(stockModelName);
                
                String strShopQuery = "from Shop a "
                        + "where shopId = ? and a.status = ? ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, stockInfo.getOwnerId());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                List<Shop> lstShop = shopQuery.list();
                if (lstShop == null || lstShop.isEmpty()) {
                    tmpAssignChannelTypeForSerialForm.setErrorMessage(getText("assignChannelTypeForSerial.error.shopCodeNotExist"));
                    listRowInFile.add(tmpAssignChannelTypeForSerialForm);
                    continue;
                } else {
                    tmpAssignChannelTypeForSerialForm.setShopCode(lstShop.get(0).getShopCode());
                    tmpAssignChannelTypeForSerialForm.setShopName(lstShop.get(0).getName());
                }
                tmpAssignChannelTypeForSerialForm.setChannelTypeName(getChannelTypeName(tmpOldChannelTypeId));  
                tmpAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                Long tmpNewChannelTypeId = hashMapStockType.get(tmpStrNewChannelType);
                if (tmpNewChannelTypeId == null) {
                    tmpNewChannelTypeId = getChannelTypeId(tmpStrNewChannelType);
                    if (tmpNewChannelTypeId != null) {
                        //tim thay ma kenh moi -> day vao map de su dung sau nay
                        hashMapStockType.put(tmpStrNewChannelType, tmpNewChannelTypeId);
                    } else {
                        //khong tim thay -> day vao -1 de khong p tim lai
                        hashMapStockType.put(tmpStrNewChannelType, -1L);
                    }
                }

                if (!stockInfo.getOwnerId().equals(shopId)) {
                    //!!!Lỗi. Serial không thuộc kho được chọn
                    tmpAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.133")); //!!!Lỗi. Serial không thuộc kho được chọn                    
                } else if (tmpNewChannelTypeId == null || tmpNewChannelTypeId.equals(-1L)) {
                    //ma kenh khong ton tai
                    tmpAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.127")); //!!!Lỗi. Cột mã kênh mới không tồn tại                    
                } 
                listRowInFile.add(tmpAssignChannelTypeForSerialForm);
            }

            //day len bien session
            setTabSession(SESSION_LIST_SERIAL_RANGE, listRowInFile);

            //
            req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);

            //
            getDataForCombobox();

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
        log.info("End filePreview of AssignChannelTypeForSerialDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * desc     : gan kenh cho dai serial hang hoa
     *
     */
    public String assignChannelTypeForSerial() throws Exception {
        log.debug("begin method assignChannelTypeForSerial of AssignChannelTypeForSerialDAO ...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            Long impType = this.assignChannelTypeForSerialForm.getImpType();
            String resultAssign = "";

            if (impType.equals(IMP_BY_FILE)) {
                //neu la gan theo file
                resultAssign = assignChannelTypeForSerialByFile();

            } else if (impType.equals(IMP_BY_RANGE)) {
                //neu la gan theo dai
                resultAssign = assignChannelTypeForSerialByRange();
                
            } else {
                //
                resultAssign = "!!!Err. Imp type not correct";
            }

            if (resultAssign.equals("")) {
                //reset lai bien session
                removeTabSession(SESSION_LIST_SERIAL_RANGE);

                //resetForm
//                this.assignChannelTypeForSerialForm.resetForm();
                //thay man hinh theo yeu cau cua VTG, giu nguyen trang thai man hinh sau khi gan kenh
                searchSerialRange();

                //
                req.setAttribute(REQUEST_MESSAGE, "assignChannelTypeForSerial.successfull");

            } else {
                req.setAttribute(REQUEST_MESSAGE, resultAssign);
            }
            
            //reset lai progress
            AssignChannelTypeForSerialDAO.listProgressMessage.put(req.getSession().getId(), new ArrayList<String>());

            //lay du lieu cho combobox
            getDataForCombobox();

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

        //
        pageForward = ASSIGN_CHANNEL_TYPE_FOR_SERIAL;
        log.debug("end method assignChannelTypeForSerial of AssignChannelTypeForSerialDAO ...");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * desc     : gan kenh cho serial hang hoa theo dai
     *
     */
    private String assignChannelTypeForSerialByRange() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        //kiem tra list co rong khong
        List<AssignChannelTypeForSerialForm> listAssignChannelTypeForSerialForm = (List<AssignChannelTypeForSerialForm>) getTabSession(SESSION_LIST_SERIAL_RANGE);
        if (listAssignChannelTypeForSerialForm == null || listAssignChannelTypeForSerialForm.isEmpty()) {
            //
            return "assignChannelTypeForSerial.error.listIsEmpty";
        }

        List<AssignChannelTypeForSerialForm> listError = new ArrayList<AssignChannelTypeForSerialForm>();
        try {
            Long newChannelTypeId = this.assignChannelTypeForSerialForm.getNewChannelTypeId();
            //kiem tra cac truong bat buoc
            if (newChannelTypeId == null || newChannelTypeId.compareTo(0L) <= 0) {
                //
                return "assignChannelTypeForSerial.error.requiredFieldsEmpty";
            }

            String[] selectedFormId = this.assignChannelTypeForSerialForm.getSelectedFormId();
            if (selectedFormId == null || selectedFormId.length <= 0 || selectedFormId[0].equals("false")) {
                //
                return "assignChannelTypeForSerial.error.noSerialRangeHasSelected";
            }

            for (int i = 0; i < selectedFormId.length; i++) {
                AssignChannelTypeForSerialForm tmpAssignChannelTypeForSerialForm = listAssignChannelTypeForSerialForm.get(Integer.parseInt(selectedFormId[i]) - 1); //-1 do chi so mang bat dau tu 0, rownum bat dau tu 1
                try {
                    Long stockTypeId = tmpAssignChannelTypeForSerialForm.getStockTypeId();
                    String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                    if (strTableName == null || strTableName.equals("")) {
                        AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                        BeanUtils.copyProperties(errAssignChannelTypeForSerialForm, tmpAssignChannelTypeForSerialForm);
                        errAssignChannelTypeForSerialForm.setErrorMessage("assignChannelTypeForSerial.error.stockTypeTableNotExist");
                        listError.add(errAssignChannelTypeForSerialForm);

                        continue;
                    }

                    List listParameter = new ArrayList();
                    StringBuffer strQuery = new StringBuffer("update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a set a.channel_type_id = ? ");
                    listParameter.add(newChannelTypeId);

                    Long shopId = tmpAssignChannelTypeForSerialForm.getShopId();
                    Long stockModelId = tmpAssignChannelTypeForSerialForm.getStockModelId();
                    String strFromSerial = tmpAssignChannelTypeForSerialForm.getFromSerial();
                    String strToSerial = tmpAssignChannelTypeForSerialForm.getToSerial();
                    Long channelTypeId = tmpAssignChannelTypeForSerialForm.getChannelTypeId();
                    BigInteger fromSerial = new BigInteger(strFromSerial);
                    BigInteger toSerial = new BigInteger(strToSerial);

                    strQuery.append("where 1 = 1 ");

                    strQuery.append("and to_number(a.serial) >= ? ");
                    listParameter.add(fromSerial);

                    strQuery.append("and to_number(a.serial) <= ? ");
                    listParameter.add(toSerial);

                    if (shopId != null && shopId.compareTo(0L) > 0) {
                        strQuery.append("       and a.owner_type = ? and a.owner_id = ? ");
                        listParameter.add(Constant.OWNER_TYPE_SHOP);
                        listParameter.add(shopId);
                    }

                    if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
                        strQuery.append("      and a.stock_model_id = ? ");
                        listParameter.add(stockModelId);
                    }

                    if (channelTypeId != null && channelTypeId.compareTo(0L) > 0) {
                        strQuery.append("      and a.channel_type_id = ? ");
                        listParameter.add(channelTypeId);
                    }

                    //chi cho phep gan cac serial o trang thai moi
                    strQuery.append("      and a.status = ? ");
                    listParameter.add(Constant.STATUS_ACTIVE);

                    Query query = session.createSQLQuery(strQuery.toString());
                    for (int indexParameter = 0; indexParameter < listParameter.size(); indexParameter++) {
                        query.setParameter(indexParameter, listParameter.get(indexParameter));
                    }

                    int numberRowUpdated = query.executeUpdate();
                    if (numberRowUpdated == (toSerial.subtract(fromSerial).add(BigInteger.ONE).intValue())) {
                        //neu update thanh cong
                        session.flush();
                        session.beginTransaction();
                        session.getTransaction().commit();
                        session.getTransaction().begin();

                    } else {
                        //truong hop bi loi
                        session.clear();
                        session.beginTransaction();
                        session.getTransaction().rollback();
                        session.getTransaction().begin();

                        AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                        BeanUtils.copyProperties(errAssignChannelTypeForSerialForm, tmpAssignChannelTypeForSerialForm);
                        errAssignChannelTypeForSerialForm.setErrorMessage("assignChannelTypeForSerial.error.updateNotSuccess");
                        listError.add(errAssignChannelTypeForSerialForm);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();

                    session.clear();
                    session.beginTransaction();
                    session.getTransaction().rollback();
                    session.getTransaction().begin();

                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    BeanUtils.copyProperties(errAssignChannelTypeForSerialForm, tmpAssignChannelTypeForSerialForm);
                    errAssignChannelTypeForSerialForm.setErrorMessage("assignChannelTypeForSerial.error.exception");
                    listError.add(errAssignChannelTypeForSerialForm);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            session.clear();
            session.beginTransaction();
            session.getTransaction().rollback();
            session.getTransaction().begin();

            return "assignChannelTypeForSerial.error.exception";
        }

        //ket xuat ket qua ra file excel trong truong hop co loi
        if (listError != null && listError.size() > 0) {
            StringBuffer strMessage = new StringBuffer("");

            try {
                String DATE_FORMAT = "yyyyMMddHHmmss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                filePath = filePath != null ? filePath : "/";
                filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("assignChannelTypeForSerialErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                String realPath = req.getSession().getServletContext().getRealPath(filePath);

                String templatePath = ResourceBundleUtils.getResource("ACTFS_TMP_PATH_ERR");
                if (templatePath == null || templatePath.trim().equals("")) {
                    //khong tim thay duong dan den file template de xuat ket qua
                    strMessage.append("assignChannelTypeForSerial.error.errTemplateNotExist");
                } else {
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        strMessage.append("assignChannelTypeForSerial.error.errTemplateNotExist");
                    } else {
                        Map beans = new HashMap();
                        beans.put("listError", listError);

                        XLSTransformer transformer = new XLSTransformer();
                        transformer.transformXLS(realTemplatePath, beans, realPath);

                        strMessage.append(filePath);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                strMessage.append("!!!Exception. " + ex.toString());
            }

            if (strMessage != null && !strMessage.toString().equals("")) {
                req.setAttribute(REQUEST_ERROR_FILE_PATH, strMessage);
            }

        }

        //
        return "";
    }

    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * desc     : gan kenh cho serial hang hoa theo dai
     *
     */
    private String assignChannelTypeForSerialByFile() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        boolean rejectLogErrorAssignChannelType = false;
        String strRejectLogErrorAssignChannelType = ResourceBundleUtils.getResource("rejectLogErrorAssignChannelType");
        strRejectLogErrorAssignChannelType = strRejectLogErrorAssignChannelType != null ? strRejectLogErrorAssignChannelType : "";

        if (AuthenticateDAO.checkAuthority(strRejectLogErrorAssignChannelType, req)) {
            //bo qua viec ghi log khi phan phoi so
            rejectLogErrorAssignChannelType = true;
        }

        try {
            Connection conn = null;
            PreparedStatement stmUpdate = null;

            conn = session.connection();

            //kiem tra stock model co ton tai khong
            Long stockTypeId = -1L;
            Long stockModelId = -1L;
            String stockModelCode = this.assignChannelTypeForSerialForm.getStockModelCode();

            //kiem tra cac truong bat buoc
            if (stockModelCode == null || stockModelCode.trim().equals("")) {
                req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);
                return "assignChannelTypeForSerial.error.requiredFieldsEmpty";
            }
            
            if (stockModelCode != null && !stockModelCode.trim().equals("")) {
                String strStockModelQuery = "from StockModel a "
                        + "where lower(a.stockModelCode) = ? and a.status = ? ";
                Query stockModelQuery = getSession().createQuery(strStockModelQuery);
                stockModelQuery.setParameter(0, stockModelCode.trim().toLowerCase());
                stockModelQuery.setParameter(1, Constant.STATUS_ACTIVE);
                List<StockModel> listStockModel = stockModelQuery.list();
                if (listStockModel == null || listStockModel.isEmpty()) {
                    req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);
                    return "assignChannelTypeForSerial.error.stockModelNotExist";
                }
                stockModelId = listStockModel.get(0).getStockModelId();
                stockTypeId = listStockModel.get(0).getStockTypeId();
            }

            //kiem tra su ton tai cua stock_type_id
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);
                return "assignChannelTypeForSerial.error.stockTypeTableNotExist";
            }

            String userSessionId = req.getSession().getId();

            //tao cau lenh update
            StringBuffer strUpdateQuery = new StringBuffer();
            strUpdateQuery.append("update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
            strUpdateQuery.append("set a.channel_type_id = ? ");
            strUpdateQuery.append("where 1 = 1 ");
            strUpdateQuery.append("and to_number(a.serial) = to_number(?) ");

            //chi cho phep gan kenh doi voi cac serial o trong kho
            strUpdateQuery.append("and a.status = " + Constant.STATUS_ACTIVE + " ");
            if(!rejectLogErrorAssignChannelType) {
                strUpdateQuery.append("and (a.channel_type_id = ? or a.channel_type_id is null) ");
            }
            strUpdateQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log

            stmUpdate = conn.prepareStatement(strUpdateQuery.toString());

            //lay du lieu tu file
            List<AssignChannelTypeForSerialForm> listError = new ArrayList<AssignChannelTypeForSerialForm>();
            String serverFileName = this.assignChannelTypeForSerialForm.getServerFileName();
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + StringEscapeUtils.getSafeFileName(serverFileName));
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow  - 1; //bo dong dau tien chua title

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            List<String> listMessage = AssignChannelTypeForSerialDAO.listProgressMessage.get(userSessionId);
            String message = "";

            //lay cac message thong bao loi
//            BaseDAOAction baseDAOAction = new BaseDAOAction();
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ERR.STK.125", getText("ERR.STK.125")); //!!!Lỗi. Cột serial và kênh mới không được để trống
            hashMapError.put("ERR.STK.126", getText("ERR.STK.126")); //!!!Lỗi. Cột serial hoặc mã kênh mới không phải là số nguyên dương
            hashMapError.put("ERR.STK.127", getText("ERR.STK.127")); //!!!Lỗi. Cột mã kênh mới không tồn tại
            hashMapError.put("ERR.STK.128", getText("ERR.STK.128")); //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
            hashMapError.put("ERR.STK.133", getText("ERR.STK.133")); //!!!Lỗi. Serial không thuộc chi nhánh

            HashMap<String, String> hashMapMessage = new HashMap<String, String>();
            hashMapMessage.put("MSG.ISN.048", getText("MSG.ISN.048")); //đang xử lý từ dòng
            hashMapMessage.put("MSG.ISN.049", getText("MSG.ISN.049")); //đến
            hashMapMessage.put("MSG.ISN.050", getText("MSG.ISN.050")); //dòng trong file

            HashMap<String, Long> hashMapStockType = new HashMap<String, Long>();

            if (numberRow > 0) {
                int toRow = NUMBER_CMD_IN_BATCH;
                toRow = toRow < numberRow ? toRow : numberRow;
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " " + hashMapMessage.get("MSG.ISN.048") + " 1 " + hashMapMessage.get("MSG.ISN.049") + " " + toRow + "/ " + numberRow + " " + hashMapMessage.get("MSG.ISN.050");
                listMessage.add(message);
            }

            String shopCode = this.assignChannelTypeForSerialForm.getShopCodeNew();
            Long shopId = 0L;
            if (shopCode != null && !shopCode.trim().equals("")) {
                String strShopQuery = "from Shop a "
                        + "where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                shopQuery.setParameter(3, userToken.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.isEmpty()) {
                    return "assignChannelTypeForSerial.error.shopCodeNotExist";
                }
                shopId = listShop.get(0).getShopId();
            }

            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet, cot dau tien la isdn, cot sau la ma mat hang moi can cap nhat
                String tmpStrSerial = sheet.getCell(0, rowIndex).getContents();
                BigInteger tmpSerial = new BigInteger("-1");
                String tmpStrNewChannelType = sheet.getCell(1, rowIndex).getContents();
                if (tmpStrSerial == null || tmpStrSerial.trim().equals("") || tmpStrNewChannelType == null || tmpStrNewChannelType.trim().equals("")) {
                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                    errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.125")); //!!!Lỗi. Cột serial và kênh mới không được để trống
                    listError.add(errAssignChannelTypeForSerialForm);
                    continue;
                } else {
                    tmpStrSerial = tmpStrSerial.trim();
                    tmpStrNewChannelType = tmpStrNewChannelType.trim();
                    try {
                        tmpSerial = new BigInteger(tmpStrSerial);
                    } catch (NumberFormatException ex) {
                        //serial khong dung dinh dang
                        AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                        errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                        errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.126")); //!!!Lỗi. Cột serial khong phai la so nguyen duong
                        listError.add(errAssignChannelTypeForSerialForm);
                        continue;
                    }
                }
                
                Long tmpOldChannelTypeId = -1L;
                if (!rejectLogErrorAssignChannelType) {
                    tmpOldChannelTypeId = getSerialChannelTypeId(strTableName, tmpSerial);
                    if (tmpOldChannelTypeId == null) {
                        //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                        AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                        errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                        errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.128")); //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                        listError.add(errAssignChannelTypeForSerialForm);
                        continue;
                    }
                }
                //haint41 30/9/2011 : chi nhanh chi duoc gan kenh voi cac hang hoa thuoc chi nhanh                
                BaseStock stockInfo = getOwnerId(tmpSerial, strTableName, stockModelId);
                if (stockInfo == null) {
                    //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                    errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.128")); //!!!Lỗi. Serial không tồn tại hoặc không được phép gán kênh mới
                    listError.add(errAssignChannelTypeForSerialForm);
                    continue;
                }
//                Boolean checkShopId = checkShopId(stockInfo.getOwnerId(), shopId);
                //if (checkShopId == false) {
                if(!stockInfo.getOwnerId().equals(shopId)){
                    //!!!Lỗi. Serial không thuộc kho được chọn
                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                    errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.133")); //!!!Lỗi. Serial không thuộc kho được chọn
                    listError.add(errAssignChannelTypeForSerialForm);
                    continue;
                }
                    
                Long tmpNewChannelTypeId = hashMapStockType.get(tmpStrNewChannelType);
                if (tmpNewChannelTypeId == null) {
                    tmpNewChannelTypeId = getChannelTypeId(tmpStrNewChannelType);
                    if (tmpNewChannelTypeId != null) {
                        //tim thay ma kenh moi -> day vao map de su dung sau nay
                        hashMapStockType.put(tmpStrNewChannelType, tmpNewChannelTypeId);
                    } else {
                        //khong tim thay -> day vao -1 de khong p tim lai
                        hashMapStockType.put(tmpStrNewChannelType, -1L);
                    }
                }

                if (tmpNewChannelTypeId == null || tmpNewChannelTypeId.equals(-1L)) {
                    //ma kenh khong ton tai
                    AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                    errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                    errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                    errAssignChannelTypeForSerialForm.setErrorMessage(hashMapError.get("ERR.STK.127")); //!!!Lỗi. Cột mã kênh mới không tồn tại
                    listError.add(errAssignChannelTypeForSerialForm);
                    continue;

                } else {
                    try {
                        stmUpdate.setLong(1, tmpNewChannelTypeId); //thiet lap truong newChannelTypeId
                        stmUpdate.setString(2, tmpStrSerial); //thiet lap truong serial
                        if (!rejectLogErrorAssignChannelType) {
                            stmUpdate.setLong(3, tmpOldChannelTypeId); //
                        }
                        stmUpdate.addBatch();

                    } catch (Exception ex) {
                        //
                        AssignChannelTypeForSerialForm errAssignChannelTypeForSerialForm = new AssignChannelTypeForSerialForm();
                        errAssignChannelTypeForSerialForm.setFromSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setToSerial(tmpStrSerial);
                        errAssignChannelTypeForSerialForm.setNewChannelTypeName(tmpStrNewChannelType);
                        errAssignChannelTypeForSerialForm.setErrorMessage("!!!Ex. " + ex.getMessage()); //!!!Lỗi. Cột mã kênh mới không tồn tại
                        listError.add(errAssignChannelTypeForSerialForm);
                        continue;
                    }
                }

                if(rowIndex % NUMBER_CMD_IN_BATCH == 0) {
                    //commit
                    conn.setAutoCommit(false);
                    //chay batch chen du lieu vao cac bang tuong ung
                    int[] updateCount = stmUpdate.executeBatch();
                    conn.commit();
//                    conn.setAutoCommit(true);tronglv comment 120604

                    int fromRow = rowIndex + 1;
                    int toRow = fromRow + NUMBER_CMD_IN_BATCH;
                    toRow = toRow < numberRow ? toRow : numberRow;
//                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " đang xử lý từ dòng " + (fromRow + 1) + " đến " + toRow + "/ " + numberRow + " dòng trong file";
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " " + hashMapMessage.get("MSG.ISN.048") + " " + (fromRow + 1) + " " + hashMapMessage.get("MSG.ISN.049") + " " + toRow + "/ " + numberRow + " " + hashMapMessage.get("MSG.ISN.050");
                    listMessage.add(message);
                }
            }

            //chen not cac ban ghi con lai
            //commit
            conn.setAutoCommit(false);
            //chay batch chen du lieu vao cac bang tuong ung
            int[] updateCount = stmUpdate.executeBatch();
            conn.commit();
//            conn.setAutoCommit(true);tronglv comment 120604

            //ket xuat ket qua ra file excel trong truong hop co loi
            if (listError != null && listError.size() > 0) {
                req.setAttribute(REQUEST_MESSAGE, hashMapMessage.get("MSG.ISN.051") + " " + (numberRow - listError.size()) + "/ " + numberRow);
                try {
                    String DATE_FORMAT = "yyyyMMddHHmmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    filePath = filePath != null ? filePath : "/";
                    filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("assignChannelTypeForSerialErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
                    String templatePath = ResourceBundleUtils.getResource("ACTFS_TMP_PATH_ERR");
                    if (templatePath == null || templatePath.trim().equals("")) {
                        //khong tim thay duong dan den file template de xuat ket qua
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "assignChannelTypeForSerial.error.errTemplateNotExist");
                    }
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "assignChannelTypeForSerial.error.errTemplateNotExist");
                    }
                    Map beans = new HashMap();
                    beans.put("listError", listError);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(realTemplatePath, beans, realPath);
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, filePath);

                } catch (Exception ex) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, "!!!Ex. " + ex.toString());
                }                

            } else {
                req.setAttribute(REQUEST_MESSAGE, hashMapMessage.get("MSG.ISN.051") + " " + numberRow + "/ " + numberRow);
            }
            return getText("assignChannelTypeForSerial.successfull") + " " + (numberRow - listError.size()) + "/" + numberRow + " serial";
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_IS_FILE_PREVIEW_MODE, true);
            return "!!!Exception. " + ex.toString();
        }

        //return "";        

    }


    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * desc     : kiem tra 1 serial co hop le khong
     *
     */
    private Long getSerialChannelTypeId(String strTableName, BigInteger serial) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            //
            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT channel_type_id ");
            strQuery.append("FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
            strQuery.append("WHERE 1 = 1 ");
            strQuery.append("      and to_number(a.serial) = ? ");
            listParameter.add(serial);

            //chi cho phep phan phoi so moi va so ngung su dung
            strQuery.append("and status = ? ");
            listParameter.add(Constant.STATUS_ACTIVE);

            strQuery.append("and exists (SELECT 'X' FROM SHOP WHERE shop_id = a.owner_id and (shop_id = ? or shop_path like ?) ) ");
            listParameter.add(userToken.getShopId());
            listParameter.add("%_" + userToken.getShopId() + "_%");

            Query query = session.createSQLQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List list = query.list();
            if (list != null && list.size() == 1) {
                Object tmpItem = list.get(0);
                if(tmpItem != null && !tmpItem.toString().equals("")) {
                    return Long.valueOf(tmpItem.toString());
                } else {
                    return -1L;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/08/2010
     * desc     : lay channelTypeId
     *
     */
    private Long getChannelTypeId(String channelTypeName) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT channel_type_id a ");
            strQuery.append("FROM channel_type a ");
            strQuery.append("where 1 = 1 ");
            strQuery.append("and a.status = 1 ");
            strQuery.append("and a.is_vt_unit = '2' "); //haint41 12/10/2011 : them dieu kien check theo is_vt_unit

//            strQuery.append("and lower(a.name) = ? ");
//            listParameter.add(channelTypeName.trim().toLowerCase());

            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeName.trim());

            Query query = session.createSQLQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List list = query.list();
            if (list != null && list.size() == 1) {
                return Long.valueOf(list.get(0).toString());
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * desc     : lay du lieu cho cac combobox
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach loai kenh
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        //haint41 19/10/2011 : sua ham load du lieu cho cac combobox,them dieu kien is_vt_unit = '2'
        //List<ChannelType> listChannelType = channelTypeDAO.findByProperty(ChannelTypeDAO.STATUS, Constant.STATUS_ACTIVE);
        String[] property = {ChannelTypeDAO.STATUS,ChannelTypeDAO.IS_VT_UNIT};
        Object[] value = {Constant.STATUS_ACTIVE,Constant.IS_NOT_VT_UNIT};               
        List<ChannelType> listChannelType = channelTypeDAO.findByProperty(property, value);
        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
    }

    /**
     *
     * author   : tamdt1
     * date     : 07/09/2010
     * purpose  : kiem tra tinh hop le cua dai serial
     *
     */
    private String checkValidSerialRange() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String shopCode = this.assignChannelTypeForSerialForm.getShopCode();
            Long stockTypeId = this.assignChannelTypeForSerialForm.getStockTypeId();
            String stockModelCode = this.assignChannelTypeForSerialForm.getStockModelCode();
            String strFromSerial = this.assignChannelTypeForSerialForm.getFromSerial();
            String strToSerial = this.assignChannelTypeForSerialForm.getToSerial();
            Long channelTypeId = this.assignChannelTypeForSerialForm.getChannelTypeId();

            //kiem tra cac truong bat buoc
            if (stockModelCode == null || stockModelCode.trim().equals("")
                    || strFromSerial == null || strFromSerial.trim().equals("")
                    || strToSerial == null || strToSerial.trim().equals("")) {

                return "assignChannelTypeForSerial.error.requiredFieldsEmpty";
            }

            //kiem tra shop co ton tai khong
            Long ownerId = 0L;
            if (shopCode != null && !shopCode.trim().equals("")) {
                String strShopQuery = "from Shop a "
                        + "where lower(a.shopCode) = ? and a.status = ? and (a.shopPath like ? or a.shopId = ?) ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
                shopQuery.setParameter(2, "%_" + userToken.getShopId() + "_%");
                shopQuery.setParameter(3, userToken.getShopId());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.isEmpty()) {
                    return "assignChannelTypeForSerial.error.shopCodeNotExist";
                }
                ownerId = listShop.get(0).getShopId();
            }

            //kiem tra stock model co ton tai khong
            Long stockModelId = 0L;
            if (stockModelCode != null && !stockModelCode.trim().equals("")) {
                String strStockModelQuery = "from StockModel a "
                        + "where lower(a.stockModelCode) = ? and a.status = ? ";
                Query stockModelQuery = getSession().createQuery(strStockModelQuery);
                stockModelQuery.setParameter(0, stockModelCode.trim().toLowerCase());
                stockModelQuery.setParameter(1, Constant.STATUS_ACTIVE);
                List<StockModel> listStockModel = stockModelQuery.list();
                if (listStockModel == null || listStockModel.isEmpty()) {
                    return "assignChannelTypeForSerial.error.stockModelNotExist";
                }
                stockModelId = listStockModel.get(0).getStockModelId();
                stockTypeId = listStockModel.get(0).getStockTypeId();
            }

            //kiem tra su ton tai cua stock_type_id
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                return "assignChannelTypeForSerial.error.stockTypeTableNotExist";
            }

            //kiem tra truong fromSerial, toSerial phai la so nguyen duong
            BigInteger fromSerial = new BigInteger("0");
            BigInteger toSerial = new BigInteger("0");
            try {
                fromSerial = new BigInteger(strFromSerial.trim());
                toSerial = new BigInteger(strToSerial.trim());
            } catch (NumberFormatException ex) {
                return "assignChannelTypeForSerial.error.serialIsNotPositiveInteger";
            }

            if(fromSerial.compareTo(BigInteger.ZERO) <= 0 || toSerial.compareTo(BigInteger.ZERO) <= 0) {
                return "assignChannelTypeForSerial.error.serialIsNotPositiveInteger";
            }

            //kiem tra truong fromSerial khong duoc lon hon truong toSerial
            if (fromSerial.compareTo(toSerial) > 0) {
                return "assignChannelTypeForSerial.error.invalidIsdn";
            }

            //
            this.assignChannelTypeForSerialForm.setShopId(ownerId);
            this.assignChannelTypeForSerialForm.setStockTypeId(stockTypeId);
            this.assignChannelTypeForSerialForm.setStockModelId(stockModelId);

            //trim cac truong can thiet
            this.assignChannelTypeForSerialForm.setShopCode(shopCode.trim());
            this.assignChannelTypeForSerialForm.setStockModelCode(stockModelCode.trim());
            this.assignChannelTypeForSerialForm.setFromSerial(strFromSerial.trim());
            this.assignChannelTypeForSerialForm.setToSerial(strToSerial.trim());

            return "";

        } catch (Exception e) {
            e.printStackTrace();
            return "!!!Error function checkValidIsdnRange(): " + e.toString();

        }
    }

    

    //bien phuc vu viec hien thi progress
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>(); //

    /**
     *
     * author   : tamdt1
     * date     : 14/11/2009
     * desc     : tra ve du lieu cap nhat cho divProgress
     *
     */
    public String updateProgressDiv(HttpServletRequest req) {
        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = AssignChannelTypeForSerialDAO.listProgressMessage.get(userSessionId);
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
    
    /**
     * @author haint41
     * @since 30/9/2011
     * @description : ham lay owner_id trong cac bang stock_card,stock_sim ... tu serial
     * @param seria 
     * @param strTableName
     * @return owner_id
     */
    private BaseStock getOwnerId(BigInteger serial, String strTableName, Long stockModelId) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            List listParameter = new ArrayList();

            StringBuffer strQuery = new StringBuffer("");
            strQuery.append(" SELECT owner_id as ownerId,owner_type as ownerType ");
            strQuery.append(" FROM ").append(com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName));
            strQuery.append(" where 1 = 1 ");
            strQuery.append(" and serial = ? ");
            strQuery.append(" and stock_model_id = ? ");
            listParameter.add(serial);
            listParameter.add(stockModelId);
            
            Query query = session.createSQLQuery(strQuery.toString())
                    .addScalar("ownerId",Hibernate.LONG)
                    .addScalar("ownerType", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(BaseStock.class));
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }
           
            List<BaseStock> lst = query.list();
            if (lst != null && !lst.isEmpty()){
                return lst.get(0);
            } else {
                return null;
            }
            
            //List list = query.list();
            //BaseStock baseStock = (BaseStock) query.list().get(0);
//            if (tmpList1 != null && tmpList1.size() > 0) {
//                listImSearchBean.addAll(tmpList1);
//            }
//            if (list != null && !list.isEmpty()) {
//                return Long.valueOf(list.get(0).toString());
//            } else {
//                return null;
//            }
        } catch (Exception ex) {            
//            ex.printStackTrace();
            log.error("Loi khi lay shop_id tu serial : " + ex);
            return null;
        }
    }
    
    /**
     * @author : haint41
     * @since : 3/10/2011
     * @description : Ham kiem tra serial hang hoa co thuoc kho hien tai hay kho cap duoi hay ko
     * @param ownerId : owner_id lay tu serial
     * @param shopId : shop_id tuong ung voi ma kho duoc chon tren giao dien
     * @return : neu thuoc kho hien tai tra ve true, con lai tra ve false
     */
    private boolean checkShopId(Long ownerId, Long shopId){
        String strQuery = "";
        try {
            strQuery = " select * from shop where 1 = 1 ";
            strQuery += " and shop_id = ? ";
            strQuery += " and (shop_id = ? ";
            strQuery += " or shop_path like ? )";
            Query query = getSession().createSQLQuery(strQuery);

            //query.setLong(0, ownerId);
            //query.setLong(1, shopId);
            //query.setString(2, "%_" + shopId + "_%");
            
            query.setParameter(0, ownerId);
            query.setParameter(1, shopId);
            query.setParameter(2, "%_" + shopId + "_%");

            List lst = query.list();
            if (lst != null && !lst.isEmpty()){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Loi khi check shop_id : " + e);
            return false;
        }        
    }

    private String getChannelTypeName(Long channelTypeId) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT name a ");
            strQuery.append("FROM channel_type a ");
            strQuery.append("where 1 = 1 ");
            strQuery.append("and a.status = 1 ");
            strQuery.append("and a.is_vt_unit = '2' "); 
            strQuery.append("and a.channel_type_id = ? ");
            listParameter.add(channelTypeId);

            Query query = session.createSQLQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List list = query.list();
            if (list != null && list.size() == 1) {
                return list.get(0).toString();
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
