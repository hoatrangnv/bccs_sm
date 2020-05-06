package com.viettel.im.database.DAO;


import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.CheckSerialBean;
import com.viettel.im.client.form.CreateExpCmdFromFileForm;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.ProfileDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockDeposit;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockTypeProfile;
import com.viettel.im.database.BO.TableColumnFull;
import com.viettel.im.database.BO.UserToken;
import com.viettel.security.util.StringEscapeUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * author   : Doan Thanh 8
 * date     : 01/09/2010
 * desc     : lap lenh xuat theo file
 *
 */
public class CreateExpCmdFromFileDAO extends BaseDAOAction {

    public void CreateExpCmdFromFileDAO(){

    }


    private final Logger log = Logger.getLogger(CreateExpCmdFromFileDAO.class);
    private String pageForward;

    //cac hang so forwrad
    private final String CREATE_EXP_CMD_FROM_FILE = "createExpCmdFromFile";

    //cac bien request, session
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private final String REQUEST_LIST_ROW_IN_FILE = "listRowInFile";

    //bien form
    private CreateExpCmdFromFileForm createExpCmdFromFileForm = new CreateExpCmdFromFileForm();

    public CreateExpCmdFromFileForm getCreateExpCmdFromFileForm() {
        return createExpCmdFromFileForm;
    }

    public void setCreateExpCmdFromFileForm(CreateExpCmdFromFileForm createExpCmdFromFileForm) {
        this.createExpCmdFromFileForm = createExpCmdFromFileForm;
    }
    //DINHDC ADD Them HashMap check khong cho phep trung ma phieu khi tao giao dich kho
     private HashMap<String, String> transCodeMap = new HashMap<String, String>(2000);
    //
    private final String SEPERATOR_STRING = "_I_N_V_E_N_T_O_R_Y_"; //chuoi ky tu phan cach giua ma lenh va ma kho (phuc vu viec xu ly trong code :D


    /**
     *
     * author   : tamdt1
     * date     : 01/09/2010
     * desc     : chuan bi form de tao lenh xuat tu file
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of CreateExpCmdFromFileDAO ...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = CREATE_EXP_CMD_FROM_FILE;
        log.info("End method preparePage of CreateExpCmdFromFileDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 01/09/2010
     * desc     : tao lenh xuat kho tu file
     * 
     */
    public String createExpCmdFromFile() throws Exception {
        log.info("Begin createExpCmdFromFile of CreateExpCmdFromFileDAO");
        
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(this.createExpCmdFromFileForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + StringEscapeUtils.getSafeFileName(serverFileName));
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow  - 1; //tru di dong dau tien

            HashMap<String, ExportStockForm> hashMapActionCodeAndExpForm = new HashMap<String, ExportStockForm>();
            HashMap<String, List<StockTransFull>> hashMapActionCodeAndListGood = new HashMap<String, List<StockTransFull>>();
            HashMap<Long, Long> hashMapStockModelQuantity = new HashMap<Long, Long>();
            
            List<GoodsForm> listErrorRow = new ArrayList<GoodsForm>();

            //doc du lieu tu file -> put vao 2 hashmap
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet
                String tmpStrExpActionCode = sheet.getCell(0, rowIndex).getContents(); //ma lenh xuat
                String tmpStrToShopCode = sheet.getCell(1, rowIndex).getContents(); //ma kho nhan hang
                String tmpStrModelCode = sheet.getCell(2, rowIndex).getContents(); //ma mat hang
                String tmpStrModelState = sheet.getCell(3, rowIndex).getContents(); //trang thai hang
                String tmpStrQuantity = sheet.getCell(4, rowIndex).getContents(); //so luong hang
                String tmpStrNote = sheet.getCell(5, rowIndex).getContents(); //ghi chu

                //kiem tra cac truong can thiet
                GoodsForm tmpGoodsForm = new GoodsForm();
                tmpGoodsForm.setActionCode(tmpStrExpActionCode);
                tmpGoodsForm.setOwnerCode(tmpStrToShopCode);
                tmpGoodsForm.setStockModelCode(tmpStrModelCode);
                tmpGoodsForm.setStateName(tmpStrModelState);
                tmpGoodsForm.setQuantityToString(tmpStrQuantity);
                tmpGoodsForm.setActionNote(tmpStrNote);

                String resultCheckValid = checkValidRow(tmpGoodsForm, hashMapActionCodeAndExpForm, hashMapStockModelQuantity);
                if(!"".equals(resultCheckValid)) {
                    //
                    tmpGoodsForm.setErrorMessage(resultCheckValid);
                    listErrorRow.add(tmpGoodsForm);
                    
                    continue;
                }

                //cong so luong total
                Long quantityInList = hashMapStockModelQuantity.get(tmpGoodsForm.getStockModelId());
                if(quantityInList != null) {
                    //truong hop mat hang + so luong da ton tai
                    quantityInList += tmpGoodsForm.getQuantity();
                } else {
                    //truong hop mat hang + so luong chua ton tai
                    hashMapStockModelQuantity.put(tmpGoodsForm.getStockModelId(), tmpGoodsForm.getQuantity());
                }

                //
                List<StockTransFull> tmpListGood = hashMapActionCodeAndListGood.get(tmpStrExpActionCode + SEPERATOR_STRING + tmpStrToShopCode);
                if(tmpListGood != null) {
                    //truong hop da ton tai lenh xuat -> bo sung them mat hang vao lenh xuat
                    StockTransFull tmpStockTransFull = new StockTransFull();
                    tmpStockTransFull.setStockTypeId(tmpGoodsForm.getStockTypeId());
                    tmpStockTransFull.setStockModelId(tmpGoodsForm.getStockModelId());
                    tmpStockTransFull.setCheckSerial(tmpGoodsForm.getCheckSerial());
                    tmpStockTransFull.setCheckDial(Long.valueOf(tmpGoodsForm.getCheckDial()));
                    tmpStockTransFull.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
                    tmpStockTransFull.setFromOwnerId(userToken.getShopId());
                    tmpStockTransFull.setQuantity(tmpGoodsForm.getQuantity());
                    tmpStockTransFull.setStateId(tmpGoodsForm.getStateId());
                    tmpStockTransFull.setNote(tmpGoodsForm.getActionNote());

                    tmpListGood.add(tmpStockTransFull);

                } else {
                    //truong hop chua ton tai lenh xuat -> bo sung them lenh xuat + mat hang
                    ExportStockForm tmpExportStockForm = new ExportStockForm();
                    tmpExportStockForm.setShopExportId(userToken.getShopId());
                    tmpExportStockForm.setShopImportedCode(tmpGoodsForm.getOwnerCode());
                    tmpExportStockForm.setCmdExportCode(tmpGoodsForm.getActionCode());
                    tmpExportStockForm.setDateExported(DateTimeUtils.getSysdate());
                    tmpExportStockForm.setReasonId(Constant.EXP_STOCK_TO_UNDERLYING_REASON_ID.toString());
                    hashMapActionCodeAndExpForm.put(tmpStrExpActionCode + SEPERATOR_STRING + tmpStrToShopCode, tmpExportStockForm);
                    
                    StockTransFull tmpStockTransFull = new StockTransFull();
                    tmpStockTransFull.setStockTypeId(tmpGoodsForm.getStockTypeId());
                    tmpStockTransFull.setStockModelId(tmpGoodsForm.getStockModelId());
                    tmpStockTransFull.setCheckSerial(tmpGoodsForm.getCheckSerial());
                    tmpStockTransFull.setCheckDial(Long.valueOf(tmpGoodsForm.getCheckDial()));
                    tmpStockTransFull.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
                    tmpStockTransFull.setFromOwnerId(userToken.getShopId());
                    tmpStockTransFull.setQuantity(tmpGoodsForm.getQuantity());
                    tmpStockTransFull.setStateId(tmpGoodsForm.getStateId());
                    tmpStockTransFull.setNote(tmpGoodsForm.getActionNote());

                    tmpListGood = new ArrayList<StockTransFull>();
                    tmpListGood.add(tmpStockTransFull);

                    hashMapActionCodeAndListGood.put(tmpStrExpActionCode + SEPERATOR_STRING + tmpStrToShopCode, tmpListGood);
                }

            }

            //doc du lieu tu 2 hashmap -> tao lenh xuat
            Session session = getSession();
            String strResultCreateCmd = "";
            Iterator<String> iterator = hashMapActionCodeAndExpForm.keySet().iterator();
            while (iterator.hasNext()) {
                String strKey = iterator.next();
                strResultCreateCmd = createDeliverCmd(session, hashMapActionCodeAndExpForm.get(strKey), hashMapActionCodeAndListGood.get(strKey));
                if (!"".equals(strResultCreateCmd)) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();

                    //
                    req.setAttribute(REQUEST_MESSAGE, strResultCreateCmd);

                    //
                    pageForward = CREATE_EXP_CMD_FROM_FILE;
                    log.info("End createExpCmdFromFile of CreateExpCmdFromFileDAO");
                    return pageForward;
                }
            }

            //ket xuat ket qua ra file excel trong truong hop co loi
            if (listErrorRow != null && listErrorRow.size() > 0) {
                req.setAttribute(REQUEST_MESSAGE, getText("createExpCmdFromFile.success") + " " + (numberRow - listErrorRow.size()) + "/ " + numberRow);
                try {
                    String DATE_FORMAT = "yyyyMMddHHmmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    filePath = filePath != null ? filePath : "/";
                    filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("createExpCmdFromFileErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
                    String templatePath = ResourceBundleUtils.getResource("CECFF_TMP_PATH_ERR");
                    if (templatePath == null || templatePath.trim().equals("")) {
                        //khong tim thay duong dan den file template de xuat ket qua
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "createExpCmdFromFile.error.errTemplateNotExist");
                    }
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "createExpCmdFromFile.error.errTemplateNotExist");
                    }
                    Map beans = new HashMap();
                    beans.put("listError", listErrorRow);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(realTemplatePath, beans, realPath);
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, filePath);

                } catch (Exception ex) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, "!!!Lỗi. " + ex.toString());
                }

            } else {
                req.setAttribute(REQUEST_MESSAGE, getText("createExpCmdFromFile.success") + " " + numberRow + "/ " + numberRow);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = CREATE_EXP_CMD_FROM_FILE;
        log.info("End createExpCmdFromFile of CreateExpCmdFromFileDAO");
        return pageForward;
        
    }

    /**
     *
     * author   : tamdt1
     * date     : 06/09/2010
     * desc     : xem truoc noi dung file truoc khi tao lenh xuat
     *
     */
    public String filePreview() throws Exception {
        log.info("Begin filePreview of CreateExpCmdFromFileDAO");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        try {
            String serverFileName = this.createExpCmdFromFileForm.getServerFileName();
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + StringEscapeUtils.getSafeFileName(serverFileName));
            File impFile = new File(serverFilePath);
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow  - 1; //tru di dong dau tien
            List<GoodsForm> listRowInFile = new ArrayList<GoodsForm>();

            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet
                String tmpStrExpActionCode = sheet.getCell(0, rowIndex).getContents(); //ma lenh xuat
                String tmpStrToShopCode = sheet.getCell(1, rowIndex).getContents(); //ma kho nhan hang
                String tmpStrModelCode = sheet.getCell(2, rowIndex).getContents(); //ma mat hang
                String tmpStrModelState = sheet.getCell(3, rowIndex).getContents(); //trang thai hang
                String tmpStrQuantity = sheet.getCell(4, rowIndex).getContents(); //so luong hang
                String tmpStrNote = sheet.getCell(5, rowIndex).getContents(); //ghi chu

                GoodsForm tmpGoodsForm = new GoodsForm();
                tmpGoodsForm.setActionCode(tmpStrExpActionCode);
                tmpGoodsForm.setOwnerCode(tmpStrToShopCode);
                tmpGoodsForm.setStockModelCode(tmpStrModelCode);
                tmpGoodsForm.setStateName(tmpStrModelState);
                tmpGoodsForm.setQuantityToString(tmpStrQuantity);
                tmpGoodsForm.setActionNote(tmpStrNote);

                listRowInFile.add(tmpGoodsForm);
            }

            //day len bien request
            req.setAttribute(REQUEST_LIST_ROW_IN_FILE, listRowInFile);

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        //
        pageForward = CREATE_EXP_CMD_FROM_FILE;
        log.info("End filePreview of CreateExpCmdFromFileDAO");
        return pageForward;

    }

    /**
     *
     * author   : tamdt
     * date     : 06/09/2010
     * desc     : kiem tra tinh hop le cua cac truong truoc khi thuc hien tao lenh xuat
     *
     */
    private String checkValidRow(GoodsForm goodsForm,
            HashMap<String, ExportStockForm> hashMapActionCodeAndExpForm,
            HashMap<Long, Long> hashMapStockModelQuantity) throws Exception {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        //kiem tra cac truong bat buoc khong duoc de trong
        if(goodsForm.getActionCode()  == null || goodsForm.getActionCode().trim().equals("") ||
                goodsForm.getOwnerCode()  == null || goodsForm.getOwnerCode().trim().equals("") ||
                goodsForm.getStockModelCode()  == null || goodsForm.getStockModelCode().trim().equals("") ||
                goodsForm.getStateName()  == null || goodsForm.getStateName().trim().equals("") ||
                goodsForm.getQuantityToString()  == null || goodsForm.getQuantityToString().trim().equals("")) {
            return ("createExpCmdFromFile.err.requiredFiledNotEmpty");

        }

        //trim cac truong can thiet
        goodsForm.setActionCode(goodsForm.getActionCode().trim());
        goodsForm.setOwnerCode(goodsForm.getOwnerCode().trim());
        goodsForm.setStockModelCode(goodsForm.getStockModelCode().trim());
        goodsForm.setStateName(goodsForm.getStateName().trim());
        goodsForm.setQuantityToString(goodsForm.getQuantityToString().trim());
        if(goodsForm.getNote() != null) {
            goodsForm.setNote(goodsForm.getNote().trim());
        }

        //kiem tra do dai cac truong
        if(goodsForm.getActionCode().length() > 20 || goodsForm.getNote().length() > 450) {
            return ("createExpCmdFromFile.err.fieldTooLong");
        }

//        //kiem tra trung ma lenh xuat, ma kho nhan
//        ExportStockForm tmpExportStockForm = hashMapActionCodeAndExpForm.get(goodsForm.getActionCode());
//        if(tmpExportStockForm != null) {
//            //truong hop da ton tai ma lenh xuat -> ma kho nhan han p trung nhau (ma lenh xuat + ma kho nhan hang = primary key)
//            if(!tmpExportStockForm.getShopImportedCode().equals(goodsForm.getOwnerCode())) {
    //                return "createExpCmdFromFile.err.duplicateActionCode";
//            }
//        }
        
        
        //kiem tra bat buoc cac ma lenh xuat p trung nhau
        Set<String> keys = hashMapActionCodeAndExpForm.keySet();
        if(keys.size() > 0) {
            Iterator<String> i = keys.iterator();
            String key = i.next();
            String tmpActionCode = key.split(SEPERATOR_STRING)[0];
            if(!goodsForm.getActionCode().equals(tmpActionCode)) {
                return ("createExpCmdFromFile.err.notSimilarActionCode");
            }
        }

        //kiem tra kho nhan hang p ton tai va thuoc cap duoi
        List listParameterShop = new ArrayList();
        StringBuilder strQueryShop = new StringBuilder("");
        strQueryShop.append("from Shop a ");
        strQueryShop.append("where 1 = 1 ");

        strQueryShop.append("and a.parentShopId = ? ");
        listParameterShop.add(userToken.getShopId());

        strQueryShop.append("and lower(a.shopCode) = ? ");
        listParameterShop.add(goodsForm.getOwnerCode().toLowerCase());

        strQueryShop.append("and status = ? ");
        listParameterShop.add(Constant.STATUS_ACTIVE);

        strQueryShop.append("and rownum <= ? ");
        listParameterShop.add(1L);

        Query queryShop = getSession().createQuery(strQueryShop.toString());
        for (int i = 0; i < listParameterShop.size(); i++) {
            queryShop.setParameter(i, listParameterShop.get(i));
        }

        List<Shop> listShop = queryShop.list();
        if (listShop == null || listShop.isEmpty()) {
            //khogn tim thay shop
            return ("createExpCmdFromFile.error.shopCodeNotExistOrHasNotPrivilege");
        } else {
            goodsForm.setOwnerType(Constant.OWNER_TYPE_SHOP);
            goodsForm.setOwnerId(listShop.get(0).getShopId());
        }

        //kiem tra ma mat hang p ton tai
        List listParameterGoods = new ArrayList();
        StringBuilder strQueryGoods = new StringBuilder("from StockModel a ");
        strQueryGoods.append("where 1 = 1 ");

        strQueryGoods.append("and lower(a.stockModelCode) = ? ");
        listParameterGoods.add(goodsForm.getStockModelCode().toLowerCase());

        strQueryGoods.append("and status = ? ");
        listParameterGoods.add(Constant.STATUS_ACTIVE);

        strQueryGoods.append("and rownum <= ? ");
        listParameterGoods.add(1L);

        Query queryGoods = getSession().createQuery(strQueryGoods.toString());
        for (int i = 0; i < listParameterGoods.size(); i++) {
            queryGoods.setParameter(i, listParameterGoods.get(i));
        }

        List<StockModel> listStockModel = queryGoods.list();
        if (listStockModel == null || listStockModel.isEmpty()) {
            //khong tim thay ma mat hang
            return ("createExpCmdFromFile.error.stockModelCodeNotExists");
        } else {
            goodsForm.setStockModelId(listStockModel.get(0).getStockModelId());
            goodsForm.setStockTypeId(listStockModel.get(0).getStockTypeId());
            goodsForm.setCheckSerial(listStockModel.get(0).getCheckSerial());
            goodsForm.setCheckDial(listStockModel.get(0).getCheckDial() != null ? listStockModel.get(0).getCheckDial().toString() : "0");
        }

        //kiem tra so luong p la so nguyen duong
        Long quantity = -1L;
        try {
            quantity = Long.parseLong(goodsForm.getQuantityToString());
        } catch (NumberFormatException ex) {
            return ("createExpCmdFromFile.error.quantityIsNotPositiveInteger");
        }
        if(quantity.compareTo(0L) <= 0) {
            return ("createExpCmdFromFile.error.quantityIsNotPositiveInteger");
        } else {
            goodsForm.setQuantity(quantity);
        }

        //kiem tra truong state, chi chap nhan 1 trong 2 trang thai, moi hoac hong
        if(Constant.STATE_NEW_DESC.equals(goodsForm.getStateName())) {
            goodsForm.setStateId(Constant.STATE_NEW);
        } else if(Constant.STATE_DAMAGE_DESC.equals(goodsForm.getStateName())) {
            goodsForm.setStateId(Constant.STATE_DAMAGE);
        } else {
            return ("createExpCmdFromFile.error.goodsStateNotDefined");
        }

        //kiem tra luong hang trong kho co du khong
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        StockTotal stockTotal = stockTotalDAO.getStockTotal(Constant.OWNER_TYPE_SHOP, userToken.getShopId(), goodsForm.getStockModelId(), goodsForm.getStateId());
        if(stockTotal == null) {
            return ("createExpCmdFromFile.error.goodsQuantityNotEnough");
        }

        Long quantityCompare = 0L;
        Long quantityInList = hashMapStockModelQuantity.get(goodsForm.getStockModelId());
        if(quantityInList != null) {
            quantityCompare = quantity + quantityInList;
        } else {
            quantityCompare = quantity;
        }

        if(quantityCompare.compareTo(stockTotal.getQuantityIssue()) > 0) {
            return "createExpCmdFromFile.error.goodsQuantityNotEnough";
        }

        return "";
    }

    /**
     *
     * author       : ThanhNC
     * date         : 27/02/2009
     * purpose      : Tao lenh xuat kho
     * modified     : tamdt1, 01/09/2010, lay code tu file StockUnderlyingDAO.java
     *
     */
    private String createDeliverCmd(Session session, ExportStockForm exportForm, List<StockTransFull> lstGoods) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //kiem tra danh sach hang hoa
            if (lstGoods == null || lstGoods.size() == 0) {
                return "error.stock.noGoods";
            }

            //check cac dieu kien bat buoc nhap
            if (exportForm.getShopExportId() == null || 
                    exportForm.getShopImportedCode() == null ||
                    "".equals(exportForm.getShopImportedCode().trim()) ||
                    exportForm.getCmdExportCode() == null ||
                    exportForm.getDateExported() == null ||
                    exportForm.getReasonId() == null ||
                    exportForm.getReasonId().trim().equals("")) {
                return "error.stock.noRequirevalue";
            }


            //kiem tra tinh hop le cua shop nhap hang
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shopImp = shopDAO.findChildByShopCode(
                    exportForm.getShopImportedCode().trim(),
                    exportForm.getShopExportId());
            if (shopImp == null) {
                return "error.stock.shopImpNotValid";
            }

            //
            Long channelTypeId = 0L;
            if (shopImp != null) {
                channelTypeId = shopImp.getChannelTypeId();
            }
            Date createDate = new Date();

            //kiem tra tinh trung lap ma
            if (!StockCommonDAO.checkDuplicateActionCode(exportForm.getCmdExportCode(), session)) {
                return "error.stock.duplicate.ExpReqCode";
            }

            //Luu thong tin giao dich (stock_transaction)
            Long stockTrasId = getSequence("STOCK_TRANS_SEQ");
            StockTrans stockTrans = new StockTrans();
            stockTrans.setStockTransId(stockTrasId);

            stockTrans.setFromOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setFromOwnerId(exportForm.getShopExportId());
            stockTrans.setToOwnerType(Constant.OWNER_TYPE_SHOP);
            stockTrans.setToOwnerId(shopImp.getShopId());

            stockTrans.setCreateDatetime(createDate);
            stockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat kho
            stockTrans.setStockTransStatus(Constant.TRANS_NON_NOTE); //Giao dich chua lap phieu xuat
            stockTrans.setReasonId(exportForm.getReasonId() == null ? null : Long.parseLong(exportForm.getReasonId()));
            stockTrans.setNote(exportForm.getNote());


            session.save(stockTrans);

            //Luu thong tin lenh xuat (stock_transaction_action)
            Long actionId = getSequence("STOCK_TRANS_ACTION_SEQ");
            StockTransAction transAction = new StockTransAction();
            transAction.setActionId(actionId);
            transAction.setStockTransId(stockTrasId);
            transAction.setActionCode(exportForm.getCmdExportCode().trim()); //Ma lenh xuat
            //DINHDC ADD check trung ma theo HashMap
            if (exportForm.getCmdExportCode() != null) {
                if (transCodeMap != null && transCodeMap.containsKey(exportForm.getCmdExportCode().trim())) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return "error.stock.duplicate.ExpReqCode";
                } else {
                    transCodeMap.put(exportForm.getCmdExportCode().trim(), actionId.toString());
                }
            }
            transAction.setActionType(Constant.ACTION_TYPE_CMD); //Loai giao dich xuat kho
            transAction.setNote(exportForm.getNote());
            transAction.setUsername(userToken.getLoginName());
            transAction.setCreateDatetime(createDate);
            transAction.setActionStaffId(userToken.getUserID());

            session.save(transAction);

            StockDepositDAO stockDepositDAO = new StockDepositDAO();
            stockDepositDAO.setSession(session);
            StockDeposit stockDeposit = null;
            //Luu chi tiet lenh xuat
            StockTransDetail transDetail = null;
            Long drawStatus = null;
            StockTransFull stockTransFull = null;
            for (int i = 0; i < lstGoods.size(); i++) {
                transDetail = new StockTransDetail();
                stockTransFull = (StockTransFull) lstGoods.get(i);

                //Kiem tra so luong hang con trong kho va lock tai nguyen
                boolean check = StockCommonDAO.checkStockAndLockGoods(exportForm.getShopExportId(),
                        Constant.OWNER_TYPE_SHOP, stockTransFull.getStockModelId(),
                        stockTransFull.getStateId(), stockTransFull.getQuantity(),
                        stockTransFull.getCheckDial(), session);
                //Khong con du hang trong kho
                if (check == false) {
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return "error.stock.notEnough";
                }

                //Luu thong tin chi tiet phieu xuat
                transDetail.setStockTransId(stockTrasId);
                transDetail.setStockModelId(stockTransFull.getStockModelId());
                transDetail.setStateId(stockTransFull.getStateId());
                //Neu co 1 mat hang phai boc tham --> trang thai giao dich la chua boc tham
                if (stockTransFull.getCheckDial() != null && stockTransFull.getCheckDial().equals(1L)) {
                    drawStatus = Constant.NON_DRAW;
                }
                transDetail.setCheckDial(stockTransFull.getCheckDial());
                transDetail.setQuantityRes(stockTransFull.getQuantity());
                transDetail.setCreateDatetime(createDate);
                transDetail.setNote(stockTransFull.getNote());
                session.save(transDetail);

                //Kiem tra xem mat hang co phai boc tham khong
                if (drawStatus != null) {
                    stockTrans.setDrawStatus(drawStatus);
                }

                //Check voi doi tuong la dai ly --> check trang thai dat coc va trang thai thanh toan
                if (channelTypeId != null) {
                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                    channelTypeDAO.setSession(this.getSession());
                    ChannelType channelType = channelTypeDAO.findById(channelTypeId);
                    if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                        //Check xem xuat kho cho DL co phai lap hoa don dat coc ko --  Vunt
                        //Check xem mat hang co phai dat coc voi doi tuong dai ly khong
                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        stockCommonDAO.setSession(this.getSession());
                        stockDeposit = stockDepositDAO.findByStockModelAndChannelType(stockTransFull.getStockModelId(), channelTypeId);
                        StockTransFull strans = (StockTransFull) lstGoods.get(0);
                        Long firstStockModelId = strans.getStockModelId();
                        if (stockDeposit != null) {
                            stockTrans.setDepositStatus(Constant.NOT_DEPOSIT); //Chua dat coc
                        } else {
                            //Trang thai thanh toan la chua thanh toan
                            stockTrans.setPayStatus(Constant.NOT_PAY);

                            //Check truong hop lap lenh xuat mat hang ban dut cho dai ly cac mat hang phai cung VAT
                            String priceType = ResourceBundleUtils.getResource("PRICE_TYPE_AGENT");
                            //Tronglv
                            //20091121
                            //Lay loai gia trong file Constant (gia dai ly = gia ban le)
                            priceType = Constant.PRICE_TYPE_AGENT;
                            //---------------++++++++++++++++++--------------------------------------//
                            //Tam thoi check khong cho xuat mat hang the cao dien tu cho dai ly     //
                            //--------------+++++++++++++++++++-------------------------------------//
//                        if (lstGoods.size() > 0) {
//                            StockTransFull tmp = (StockTransFull) lstGoods.get(0);
//                            if (tmp.getStockModelId().equals(1076L)) {
//                                req.setAttribute("resultCreateExp", "Hiện tại hệ thống chưa hỗ trợ xuất thẻ cào điện tử cho đại lý");
//                                session.clear();
//                                session.getTransaction().rollback();
//                                session.beginTransaction();
//                                return pageForward;
//                            }
//                        }
                            Long firstVAT = stockCommonDAO.getVATByStockModelIdAndType(firstStockModelId, priceType);
                            for (int idx = 1; idx < lstGoods.size(); idx++) {
                                StockTransFull tmp = (StockTransFull) lstGoods.get(idx);
                                //---------------++++++++++++++++++--------------------------------------//
                                //Tam thoi check khong cho xuat mat hang the cao dien tu cho dai ly     //
                                //--------------+++++++++++++++++++-------------------------------------//
//                            if (tmp.getStockModelId().equals(1076L)) {
//                                req.setAttribute("resultCreateExp", "Hiện tại hệ thống chưa hỗ trợ xuất thẻ cào điện tử cho đại lý");
//                                session.clear();
//                                session.getTransaction().rollback();
//                                session.beginTransaction();
//                                return pageForward;
//                            }
                                if (!stockCommonDAO.checkVAT(tmp.getStockModelId(), firstVAT, priceType)) {
                                    session.clear();
                                    session.getTransaction().rollback();
                                    session.beginTransaction();
                                    return "error.stock.exp.agent.notSameVAT";
                                }
                                if (!stockCommonDAO.checkTelecomService(firstStockModelId, tmp.getStockModelId())) {
                                    session.clear();
                                    session.getTransaction().rollback();
                                    session.beginTransaction();
                                    return "error.stock.exp.agent.notSameTelecomService";
                                }

                            }
                        }
                        //check cac mat hang trong lenh xuat phai cung loai dich vu
                        for (int idx = 1; idx < lstGoods.size(); idx++) {
                            StockTransFull tmp = (StockTransFull) lstGoods.get(idx);
                            //---------------++++++++++++++++++--------------------------------------//
                            //Tam thoi check khong cho xuat mat hang the cao dien tu cho dai ly     //
                            //--------------+++++++++++++++++++-------------------------------------//
//                            if (tmp.getStockModelId().equals(1076L)) {
//                                req.setAttribute("resultCreateExp", "Hiện tại hệ thống chưa hỗ trợ xuất thẻ cào điện tử cho đại lý");
//                                session.clear();
//                                session.getTransaction().rollback();
//                                session.beginTransaction();
//                                return pageForward;
//                            }

                            if (!stockCommonDAO.checkTelecomService(firstStockModelId, tmp.getStockModelId())) {
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                return "error.stock.exp.agent.notSameTelecomService";
                            }
                        }
                    }
                }
            }

            //Luu thong tin giao dich
            session.update(stockTrans);
            exportForm.setActionId(actionId);
            return "";

        } catch (Exception ex) {
            ex.printStackTrace();

            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();

            return "!!!Exception - " + ex.toString();

        }
    }

}
