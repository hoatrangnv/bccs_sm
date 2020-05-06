/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.viettel.bccs.cm.api.InterfaceCMToIM;
import com.viettel.bccs.cm.database.BO.pre.Customer;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.bean.ViewPackageCheck;
import com.viettel.im.client.form.SaleToCollaboratorForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.ResponseWallet;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.viettel.im.database.BO.AccountAgent;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.PackageGoodsDetail;
import com.viettel.im.database.BO.SaleTransDetailOrder;
import com.viettel.im.database.BO.SaleTransEmola;
import com.viettel.im.database.BO.SaleTransOrder;
import com.viettel.im.database.BO.SaleTransSerialOrder;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockType;
import com.viettel.security.util.DbProcessor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
//them Log log4j
import java.util.ResourceBundle;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


import com.google.gson.Gson;
import java.text.NumberFormat;

/**
 *
 * @author tuan
 */
public class SaleToCollaboratorDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleToCollaboratorDAO.class);
    private static final Logger logdetail = Logger.getLogger(SaleToCollaboratorDAO.class);
    //
    private static final String UPDATE_LIST_MODEL_PRICE = "updateListModelPrice";
    private static final String GET_LIST_COLLABORATOR_CODE = "getListCollaboratorCode";
    private static final String GET_COLLABORATOR_NAME = "getCollaboratorName";
    private static final String LIST_GOOD = "lstGoods";         //Danh sach hang hoa
    private static final String LIST_GOOD_V2 = "lstGoodsV2";         //Danh sach hang hosa
    private static final String LIST_PAYMETHOD = "listPayMethod";       //Danh sach hinh thuc thanh toan
    private static final String LIST_TELECOM_SERVICE = "listTelecomService";        //Danh sach dich vu vien thong
    private static final String LIST_REASON_SALE_EXP_COLLABORATOR = "listReaSonSaleExpCollaborator";        //Danh sach ly do lap giao dich
    private static final String LIST_CHANNEL_TYPE = "listChannelType";      //Danh sach loai doi tuong
    private static final String LIST_PRICE = "priceList";      //Hang hoa theo dich vu vien thong
    private static final String LIST_MODEL = "modelList";      //Gia theo hang hoa
//    private static final String NEW_TYPE = "new";
//    private static final String UPDATE_TYPE = "update";
    private static final String IS_EDIT = "isEdit";     //Trang thai sua hay them moi
    private static final String REQUEST_MESSAGE = "returnMsg";      //Message tra ve Client
    private static final String SALE_GROUP = "1"; //Ban theo bo
    private static final String SALE_SINGLE = "2"; //Ban rieng le tung mat hang
    private String pageForward;
    private static final String PAGE_FORWARD_SALE_TO_COLLABORATOR = "saleToCollaborator";       //Refresh ca trang
    private static final String PAGE_FORWARD_SALE_TO_COLLABORATOR_ADD_GOOD = "editGoodsCollaborator";       //Refresh phan them hang hoa
    private static final String PAGE_FORWARD_SALE_TO_COLLABORATOR_GOOD_LIST = "addGoodsCollaborator";       //Refresh phan danh sach hang hoa
    private static final String REQUEST_MESSAGE_PARAM = "returnMsgValue";
    private String LIST_ORDER = "lstInvoice";
    SaleToCollaboratorForm saleToCollaboratorForm = new SaleToCollaboratorForm();
    //    TrongLV 11-11-07
    //fix khong quan ly hang hoa theo kenh
    private final boolean IS_STOCK_STAFF_OWNER = true;
    //tannh20180111 upload file va download file
    private InputStream fileInputStream;
    public static String fileDowd;

    public InputStream getFileInputStream() {

        return fileInputStream;

    }

    public SaleToCollaboratorForm getSaleToCollaboratorForm() {
        return saleToCollaboratorForm;
    }

    public void setSaleToCollaboratorForm(SaleToCollaboratorForm saleToCollaboratorForm) {
        this.saleToCollaboratorForm = saleToCollaboratorForm;
    }

    public SaleToCollaboratorDAO() {
        String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");
        PropertyConfigurator.configure(log4JPath);
    }
    /*
     * Xoa bien session
     */

    private void clearTempList(HttpSession session) {
        session.setAttribute(LIST_MODEL, null);
        session.setAttribute(LIST_PRICE, null);
    }

    public String prepareSaleToCollaborator() throws Exception {
        log.info("Begin method prepareSaleToCollaborator of SaleToCollaboratorDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                //LinhNBV start modified on May 24 2018: Check over limit.
//                if (isStaffMaputo(userToken.getLoginName())) {
                DbProcessor db = new DbProcessor();
                boolean isOverLimit = db.checkOverLimitBeforeSale(userToken.getLoginName());
                //true meaning limit valid.
                if (isOverLimit) {
                    req.getSession().setAttribute("isOverLimit", 1);
                } else {
                    //false limit invalid.
                    req.getSession().setAttribute("isOverLimit", 0);
                }
//                } else {
//                    req.getSession().setAttribute("isOverLimit", 1);
//                }
                //end.

                //LY DO BAN
                session.removeAttribute(LIST_REASON_SALE_EXP_COLLABORATOR);
                session.setAttribute(LIST_REASON_SALE_EXP_COLLABORATOR, getListReaSonSaleExpCollaborator());

                //DICH VU VIEN THONG
                session.removeAttribute(LIST_TELECOM_SERVICE);
                TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
                telecomServiceDAO.setSession(this.getSession());
                session.setAttribute(LIST_TELECOM_SERVICE, telecomServiceDAO.findAll());

                //HINH THUC THANH TOAN
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(getSession());
                List payMethodList = appParamsDAO.findAppParamsList("PAY_METHOD", "1");
                session.setAttribute(LIST_PAYMETHOD, payMethodList);

                //LOAI DOI TUONG
                session.setAttribute(LIST_CHANNEL_TYPE, AccountBalanceDAO.getChannelTypeList(this.getSession(), Constant.OBJECT_TYPE_STAFF, Constant.NOT_IS_VT_UNIT));

                removeTabSession(LIST_GOOD);
                clearTempList(session);

//                session.setAttribute("stockModelId", null);
//                session.setAttribute("priceId", null);

                saleToCollaboratorForm.setSaleDate(DateTimeUtils.getSysDateTime("yyyy-MM-dd"));
                saleToCollaboratorForm.setPayMethodId(Constant.PAY_METHOD_MONNEY);
                pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;

                //LinhNBV start modified on May 22 2018: Show download order when create order success.
                req.getSession().setAttribute("downloadOrderForChannel", 0);

            } catch (Exception e) {
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method prepareSaleToCollaborator of SaleDAO");
        return pageForward;
    }

    /**
     * Add hang hoa vao danh sach
     *
     * @return
     * @throws Exception
     */
    public String addGoods() throws Exception {
        log.debug("# Begin method addGoods");
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        SaleToCollaboratorForm goodsFrm = getSaleToCollaboratorForm();
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.saleToCollaboratorForm.getStockModelCode();
        Long quantityF9 = this.saleToCollaboratorForm.getQuantity();
        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.saleToCollaboratorForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.saleToCollaboratorForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.saleToCollaboratorForm.setStockModelId(stockModelF9.getStockModelId());
            this.saleToCollaboratorForm.setStockModelName(stockModelF9.getName());
            //bo sung them doan nay cua anh Trong de phan biet goi hang
            this.saleToCollaboratorForm.setItemId("0:" + stockModelF9.getStockModelId());
        }

        //--------kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        //kiem tra truong so luong phai ko am
        if (quantityF9.compareTo(0L) <= 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương

            //lay danh sach gia cua mat hang
            getDataForCombobox();

            return pageForward;
        }

        StockTotalDAO stockTotalDAOF9 = new StockTotalDAO();
        stockTotalDAOF9.setSession(this.getSession());
        Long ownerTypeF9 = Constant.OWNER_TYPE_STAFF;
        Long ownerIdF9 = userToken.getUserID();

        StockTotal stockTotalF9 = stockTotalDAOF9.getStockTotal(ownerTypeF9, ownerIdF9, this.saleToCollaboratorForm.getStockModelId(), Constant.STATE_NEW);
        if (stockTotalF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ

            //lay danh sach gia cua mat hang
            getDataForCombobox();

            return pageForward;
        }

        if (quantityF9.compareTo(stockTotalF9.getQuantityIssue()) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ

            //lay danh sach gia cua mat hang
            getDataForCombobox();

            return pageForward;
        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------


        //Check ma cong tac vien/diem ban
        if (goodsFrm.getAgentTypeIdSearch() == null || goodsFrm.getAgentTypeIdSearch().trim().equals("")) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.085");
            return pageForward;
        }
        if (goodsFrm.getAgentCodeSearch() == null || goodsFrm.getAgentCodeSearch().trim().equals("")) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.086");
            return pageForward;
        }

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff coll = staffDAO.findStaffAvailableByStaffCode(goodsFrm.getAgentCodeSearch().trim());
        if (coll == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.087");
            return pageForward;
        }
        if (coll.getChannelTypeId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.088");
            return pageForward;
        }

        //check channel type of channel must is_vt_unit = 2
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(coll.getChannelTypeId());
        if (channelType == null || channelType.getIsVtUnit() == null || channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.089");
            return pageForward;
        }

        if ((goodsFrm.getTelecomServiceId() == null) || (goodsFrm.getTelecomServiceId().equals(0L))) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.telecom");
            return pageForward;
        }

        if ((goodsFrm.getStockModelId() == null) || (goodsFrm.getStockModelId().equals(0L))) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.stock");
            return pageForward;
        }
        if ((goodsFrm.getPriceId() == null) || (goodsFrm.getPriceId() <= 0)) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.price");
            return pageForward;
        }

        if ((goodsFrm.getQuantity() == null) || (goodsFrm.getQuantity() <= 0)) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.quantity");
            return pageForward;
        }

        List lstGoods = (List) getTabSession(LIST_GOOD);
        if (lstGoods == null) {
            lstGoods = new ArrayList();
            setTabSession(LIST_GOOD, lstGoods);
        }

        //Kiem tra hang hoa co ton tai trong kho
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(goodsFrm.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.090");
            return pageForward;
        }

        goodsFrm.setStockModelCode(stockModel.getStockModelCode());


        //check gia xem co dung khong Vunt        
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_COLLABOR);

        //QuocDM1 bo sung chon gia theo loai ban hang
        if (saleToCollaboratorForm.getTypeOfSale() == 1L) {
            priceDAO.setCheckSalePackagePrice(true);
        } else if (saleToCollaboratorForm.getTypeOfSale() == 2L) {
            priceDAO.setCheckSalePackagePrice(false);
        }

        String pricePolicy = coll.getPricePolicy();
        priceDAO.setPricePolicyFilter(pricePolicy);
        priceDAO.setStockModelIdFilter(stockModel.getStockModelId());
        priceDAO.setPriceId(goodsFrm.getPriceId());

        String listBranhConfig = ResourceBundleUtils.getResource("LIST_BRANCH_HAVE_PRICE_POLICY_BRANCH");
        String[] strSplit = listBranhConfig.split(";");
        HashMap<String, String> listShopMap = new HashMap<String, String>();

        String listBranhDiscountConfig = ResourceBundleUtils.getResource("LIST_BRANCH_DISCOUNT");
        String[] strSplitBranch = listBranhDiscountConfig.split(";");
        HashMap<String, String> listShopBranchMap = new HashMap<String, String>();

        String param = "(";
        if (strSplit.length > 0) {
            for (int i = 0; i < strSplit.length; i++) {
                if (i == (strSplit.length - 1)) {
                    param = param + "'" + strSplit[i] + "'";
                } else {
                    param = param + "'" + strSplit[i] + "',";
                }
            }
        } else {
            param = param + "'CABBR','NACBR'";
        }

        param = param + ")";

        String paramBranch = "(";
        if (strSplitBranch.length > 0) {
            for (int i = 0; i < strSplitBranch.length; i++) {
                if (i == (strSplitBranch.length - 1)) {
                    paramBranch = paramBranch + "'" + strSplitBranch[i] + "'";
                } else {
                    paramBranch = paramBranch + "'" + strSplitBranch[i] + "',";
                }
            }
        } else {
            paramBranch = paramBranch + "'MACBR','TETBR'";
        }

        paramBranch = paramBranch + ")";

        List<Shop> listShop = listShopOfBranch(getSession(), param);
        for (int k = 0; k < listShop.size(); k++) {
            if (listShop.get(k).getShopCode() != null) {
                listShopMap.put(listShop.get(k).getShopId().toString(), listShop.get(k).getShopCode());
            }
        }

        List<Shop> listShopBranch = listShopOfBranch(getSession(), paramBranch);
        for (int k1 = 0; k1 < listShopBranch.size(); k1++) {
            if (listShopBranch.get(k1).getShopCode() != null) {
                listShopBranchMap.put(listShopBranch.get(k1).getShopId().toString(), listShopBranch.get(k1).getShopCode());
            }
        }
        Long TypeBranch = 0L;
        if (staff != null && listShopMap.containsKey(staff.getShopId().toString())) {
            TypeBranch = 1L;
        } else if (staff != null && listShopBranchMap.containsKey(staff.getShopId().toString())) {
            TypeBranch = 2L;
        }
        List priceList = new ArrayList();
        if (staff != null && staff.getShopId() != null
                && (listShopMap.containsKey(staff.getShopId().toString())
                || listShopBranchMap.containsKey(staff.getShopId().toString()))
                && priceDAO.findManyPriceForSaleChannelForBranch(TypeBranch).size() > 0) {
            priceList = priceDAO.findManyPriceForSaleChannelForBranch(TypeBranch);
        } else {
            priceList = priceDAO.findManyPriceForSaleChannel();
        }
        if (priceList == null || priceList.isEmpty()) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.062");
            return pageForward;
        }

        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */
        boolean checkValidatePrice = false;
        Iterator iter = priceList.iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            Long priceId = new Long(temp[0].toString());
            if (priceId != null && priceId.equals(goodsFrm.getPriceId())) {
                checkValidatePrice = true;
                break;
            }
        }

        if (!checkValidatePrice) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.062");
            recoverTempList(req.getSession());
            return pageForward;
        }
        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */


        //Begin check same vat
        if (lstGoods.size() > 0) {
            SaleToCollaboratorForm frm = (SaleToCollaboratorForm) lstGoods.get(0);

            if (frm.getToOwnerId() == null || coll == null || frm.getToOwnerId().compareTo(coll.getStaffId()) != 0) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "E.200001");
                return pageForward;
            }

            if (goodsFrm.getTelecomServiceId().compareTo(frm.getTelecomServiceId()) != 0) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.091");
                return pageForward;
            }

            //old vat
            Price price_old = new Price();
            price_old = this.findPrice(frm.getPriceId());

            //new vat
            Price price = this.findPrice(goodsFrm.getPriceId());
            Double newVat = price.getVat();//vat cu

            if ((price.getVat() == null) && (price_old.getVat() == null)) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.NotSameVat");
                return pageForward;

            }

            if (price.getVat() != null && !newVat.equals(price_old.getVat())) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.NotSameVat");
                return pageForward;
            }

            //Bo sung cung currency
            if (price.getCurrency() == null || price_old.getCurrency() == null) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "Error. Price of goods must be same currency!");
                return pageForward;
            }
            if (!price.getCurrency().equals(price_old.getCurrency())) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "Error. Price of goods must be same currency!");
                return pageForward;
            }

        }//End of check same vat

        //TEN HANG HOA
        goodsFrm.setStockModelId(stockModel.getStockModelId());
        goodsFrm.setStockModelName(stockModel.getName());
        goodsFrm.setCheckSerial(stockModel.getCheckSerial());
        Long checkDial = 0L;
        if (goodsFrm.getCheckDial() != null && goodsFrm.getCheckDial().toString().equals("true")) {
            checkDial = 1L;
        }
        goodsFrm.setCheckDial(checkDial);

        //TEN DON VI
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        AppParams appParams = (AppParams) appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());
        goodsFrm.setUnit(appParams.getName());

        goodsFrm.setToOwnerType(Constant.OWNER_TYPE_STAFF);
        goodsFrm.setToOwnerId(coll.getStaffId());

        goodsFrm.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
        goodsFrm.setFromOwnerId(userToken.getUserID());
        goodsFrm.setStateId(Constant.STATE_NEW);

        boolean check = StockCommonDAO.checkStockGoods(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, goodsFrm.getStockModelId(), Constant.STATE_NEW, goodsFrm.getQuantity().longValue(), Constant.UN_CHECK_DIAL, this.getSession());
        if (check == false) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saleQuantity");
            return pageForward;
        }

        Price price = priceDAO.findById(goodsFrm.getPriceId());
        if (price == null || price.getPriceId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.092");
            return pageForward;
        }

        //SO LUONG
        goodsFrm.setQuantityMoney(NumberUtils.formatNumber(goodsFrm.getQuantity()));
        //DON GIA
        goodsFrm.setPriceId(price.getPriceId());
        goodsFrm.setPrice(price.getPrice());
//        goodsFrm.setPriceMoney(StringUtils.formatMoney(price.getPrice()));
        goodsFrm.setPriceMoney(NumberUtils.rounđAndFormatAmount(goodsFrm.getPrice()));
        //VAT
        goodsFrm.setItemVat(price.getVat().toString());
        //TONG TIEN
        goodsFrm.setAmount(goodsFrm.getQuantity() * goodsFrm.getPrice());
        goodsFrm.setAmountMoney(NumberUtils.rounđAndFormatAmount(goodsFrm.getAmount()));


        SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
        saleCommonDAO.setSession(this.getSession());
        goodsFrm.setDiscountGroupId(saleCommonDAO.getDiscountGroupId(goodsFrm.getStockModelId(), coll.getDiscountPolicy()));

        //DICH VU VIEN THONG
        TelecomServiceDAO telDAO = new TelecomServiceDAO();
        telDAO.setSession(this.getSession());
        TelecomService tel = telDAO.findById(goodsFrm.getTelecomServiceId());
        if (tel == null || tel.getTelecomServiceId() == null) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.093");
            this.sumSaleTransAmount(lstGoods);
            return pageForward;
        }
        goodsFrm.setTelecomServiceId(tel.getTelecomServiceId());
        goodsFrm.setTelecomServiceName(tel.getTelServiceName());

        //Kiem tra hang hoa da duoc them tu truoc chua
        String exist = checkGoodsExist(lstGoods, goodsFrm.getStockModelId().toString());

        if (!exist.equals("")) {
            recoverTempList(req.getSession());
            req.setAttribute(REQUEST_MESSAGE, exist);
            this.sumSaleTransAmount(lstGoods);
            return pageForward;
        }

        /* KIEM TRA MAT HANG LA KIT DA NANG : CHI CHO PHEP BAN SO LUONG 1 */
        if (checkKITCTV(goodsFrm.getStockModelCode().trim().toUpperCase())) {
            if (!goodsFrm.getQuantity().equals(1L)) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "Error. You only sale KIT_MFS with quantity = 1");
                this.sumSaleTransAmount(lstGoods);
                return pageForward;
            }

            /* Check CTV da co thue bao da nang chua 120226 */
            Session cmPreSession = getSession("cm_pre");
            cmPreSession.beginTransaction();
            boolean result = InterfaceCMToIM.checkIdNoAlreadyRegisterSTK(coll.getIdNo(), cmPreSession);
            if (result) {
                recoverTempList(req.getSession());
                req.setAttribute(REQUEST_MESSAGE, "Error. You can not sale KIT_MFS because exists STK subcriber with ID_NO = " + coll.getIdNo());
                this.sumSaleTransAmount(lstGoods);
                return pageForward;
            }

            /* Check CTV da co thue bao da nang chua 120226 */

        }

        SaleToCollaboratorForm tmp = new SaleToCollaboratorForm();
        BeanUtils.copyProperties(tmp, goodsFrm);

        tmp.setChannelTypeId(coll.getChannelTypeId());
        lstGoods.add(tmp);

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);

        goodsFrm.resetForm();
        clearTempList(session);

        req.setAttribute(REQUEST_MESSAGE, "MSG.SAE.076");
        log.debug("# End method SALE_TO_COLLABORATOR");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 17/09/2010 desc : thay the ham private void
     * recoverTempList(HttpSession session) khi chuyen sang lib moi
     *
     */
    private void getDataForCombobox() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //thong tin CTV/DB
            String collCode = saleToCollaboratorForm.getAgentCodeSearch();
            if (collCode == null || collCode.trim().equals("")) {
                return;
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff coll = staffDAO.findAvailableByStaffCode(collCode.trim());
            if (this.IS_STOCK_STAFF_OWNER) {
                if (coll == null || coll.getStaffOwnerId().compareTo(userToken.getUserID()) != 0) {
                    return;
                }
            } else {
                if (coll == null) {
                    return;
                }
            }


            //chinh sach gia cua CTV/DB
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_COLLABOR);
            priceDAO.setPricePolicyFilter(coll.getPricePolicy());
            priceDAO.setStockModelIdFilter(saleToCollaboratorForm.getStockModelId());
//            List price = priceDAO.findPriceForSaleRetail();
            List price = priceDAO.findManyPriceForSaleChannel();
            List<Price> priceList = new ArrayList<Price>();
            for (int i = 0; i < price.size(); i++) {
                Object[] obj = (Object[]) price.get(i);
                Price p = new Price();
                p.setPriceId(Long.valueOf(obj[0].toString()));
                p.setPrice(Double.valueOf(obj[1].toString()));
                priceList.add(p);
            }

            req.getSession().setAttribute(LIST_PRICE, priceList);

        } catch (Exception ex) {
        }
    }

    /*
     * Author: Tuannv Date created: 26/02/2009 Purpose: Nhap serial cho mat hang
     * can xuat kho
     */
    public String refreshListGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = PAGE_FORWARD_SALE_TO_COLLABORATOR_GOOD_LIST;
        try {
            //Tien chua thue
            String amountNotTax = (String) QueryCryptUtils.getParameter(req, "amountNotTax");
            saleToCollaboratorForm.setAmountNotTaxMoney(amountNotTax);
            //Tien thue
            String tax = (String) QueryCryptUtils.getParameter(req, "tax");
            saleToCollaboratorForm.setTaxMoney(tax);
            //Tien chiet khau
            String discout = (String) QueryCryptUtils.getParameter(req, "discout");
            saleToCollaboratorForm.setDiscoutMoney(discout);
            //Tien phai tra
            String amountTax = (String) QueryCryptUtils.getParameter(req, "amountTax");
            saleToCollaboratorForm.setAmountTaxMoney(amountTax);
            req.setAttribute("editable", "true");
        } catch (Exception ex) {
        }

        return pageFoward;
    }

    /*
     * Author: TuanNV Date created: 26/02/2009 Purpose: prepare edit goods in
     * goods list
     */
    public String prepareEditGoods() throws Exception {
        log.debug("# Begin method prepareEditGoods");
        HttpServletRequest req = getRequest();
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;
        HttpSession session = req.getSession();

//        String stockModelId = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        String strStockModelId = req.getParameter("stockModelId");
        if (strStockModelId == null || strStockModelId.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.094");
            return pageForward;
        }

        //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
        List lstGoods = (List) getTabSession("lstGoods");
        Long stockModelId = Long.parseLong(strStockModelId.trim());

        SaleToCollaboratorForm goodsFrm;
        for (int i = 0; i < lstGoods.size(); i++) {
            goodsFrm = (SaleToCollaboratorForm) lstGoods.get(i);
            if (goodsFrm.getStockModelId().equals(stockModelId)) {
                saleToCollaboratorForm.setTelecomServiceId(goodsFrm.getTelecomServiceId());
                saleToCollaboratorForm.setStockModelId(stockModelId);
                saleToCollaboratorForm.setStockModelCode(goodsFrm.getStockModelCode());
                saleToCollaboratorForm.setStockModelName(goodsFrm.getStockModelName());
                saleToCollaboratorForm.setPriceId(goodsFrm.getPriceId());
                saleToCollaboratorForm.setQuantity(goodsFrm.getQuantity());
                saleToCollaboratorForm.setNote(goodsFrm.getNote());

                StockModelDAO modelDAO = new StockModelDAO();
                modelDAO.setSession(this.getSession());
                modelDAO.setTelecomServiceIdFilter(goodsFrm.getTelecomServiceId());

                UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
                Long shopId = userToken.getShopId();

                StockModel stockModel = modelDAO.findById(stockModelId);
                List<StockModel> modelList = new ArrayList();
                modelList.add(stockModel);
                session.setAttribute(LIST_MODEL, modelList);
                modelDAO.setShopIdFilter(shopId);

                PriceDAO priceDAO = new PriceDAO();
                priceDAO.setSession(getSession());
                Price price = priceDAO.findById(goodsFrm.getPriceId());
                List<Price> priceList = new ArrayList();
                priceList.add(price);
                session.setAttribute(LIST_PRICE, priceList);
                break;
            }
        }

        req.setAttribute(IS_EDIT, true);

        log.debug("# End method prepareEditGoods");
        return pageForward;
    }

    /*
     * Author: Tuannv Date created: 26/02/2009 Purpose: edit goods in goods list
     */
    public String editGoods() throws Exception {
        log.debug("# Begin method editGoods");
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR;
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        SaleToCollaboratorForm goodsFrm = getSaleToCollaboratorForm();

        //TRANG THAI SUA SO LUONG HANG HOA
        req.setAttribute(IS_EDIT, true);

        //Check ma cong tac vien/diem ban
        if (goodsFrm.getAgentTypeIdSearch() == null || goodsFrm.getAgentTypeIdSearch().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.085");
            return pageForward;
        }
        if (goodsFrm.getAgentCodeSearch() == null || goodsFrm.getAgentCodeSearch().trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.086");
            return pageForward;
        }

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff coll = staffDAO.findStaffAvailableByStaffCode(goodsFrm.getAgentCodeSearch().trim());
        if (coll == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.087");
            return pageForward;
        }
        if (coll.getChannelTypeId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.088");
            return pageForward;
        }

        //check channel type of channel must is_vt_unit = 2
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
        ChannelType channelType = channelTypeDAO.findById(coll.getChannelTypeId());
        if (channelType == null || channelType.getIsVtUnit() == null || channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.089");
            return pageForward;
        }

        if ((goodsFrm.getTelecomServiceId() == null) || (goodsFrm.getTelecomServiceId().equals(0L))) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.telecom");
            return pageForward;
        }

        if ((goodsFrm.getStockModelId() == null) || (goodsFrm.getStockModelId().equals(0L))) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.stock");
            return pageForward;
        }
        if ((goodsFrm.getPriceId() == null) || (goodsFrm.getPriceId() <= 0)) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.price");
            return pageForward;
        }

        if ((goodsFrm.getQuantity() == null) || (goodsFrm.getQuantity() <= 0)) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.quantity");
            return pageForward;
        }

        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        boolean check = StockCommonDAO.checkStockGoods(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, goodsFrm.getStockModelId(), Constant.STATE_NEW, saleToCollaboratorForm.getQuantity().longValue(), Constant.UN_CHECK_DIAL, this.getSession());

        if (check == false) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saleQuantity");
            return pageForward;
        }

        List<SaleToCollaboratorForm> lstGoods = (List<SaleToCollaboratorForm>) getTabSession(LIST_GOOD);
        if (lstGoods == null || lstGoods.isEmpty()) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
            return pageForward;
        }

        Long stockModelId = goodsFrm.getStockModelId();

        for (int i = 0; i < lstGoods.size(); i++) {
            SaleToCollaboratorForm frm = lstGoods.get(i);

            /* KIEM TRA MAT HANG LA KIT DA NANG : CHI CHO PHEP BAN SO LUONG 1 */
            if (checkKITCTV(frm.getStockModelCode().trim().toUpperCase())) {
                if (!goodsFrm.getQuantity().equals(1L)) {
                    recoverTempList(req.getSession());
                    req.setAttribute(REQUEST_MESSAGE, "Error. You only sale KIT_MFS with quantity = 1");
                    this.sumSaleTransAmount(lstGoods);
                    return pageForward;
                }
            }

            if (frm.getStockModelId().equals(stockModelId)) {
                frm.setNote(goodsFrm.getNote());

                //SO LUONG
                frm.setQuantity(goodsFrm.getQuantity());
                frm.setQuantityMoney(NumberUtils.formatNumber(frm.getQuantity()));

                //TONG TIEN
                frm.setAmount(frm.getQuantity() * frm.getPrice());
                frm.setAmountMoney(NumberUtils.rounđAndFormatAmount(frm.getAmount()));

                break;
            }
        }

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);

        goodsFrm.resetForm();
        clearTempList(session);

        //TRANG THAI KHONG THEM MOI HANG HOA
        req.setAttribute(IS_EDIT, false);

        req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saveSuccess");
        return pageForward;
    }


    /*
     * Author: Tuannv Date created: 26/02/2009 Purpose: cacel sua hang hoa
     */
    public String cancelEditGoods() throws Exception {
        log.debug("# Begin method cancelEditGoods");
        pageForward = PAGE_FORWARD_SALE_TO_COLLABORATOR_ADD_GOOD;
        HttpServletRequest req = getRequest();
        saleToCollaboratorForm.resetForm();
        clearTempList(req.getSession());
        log.debug("# End method prepareEditGoods");
        return pageForward;
    }

    /*
     * Author: Tuannv Date created: 20/04/2009 Purpose: Kiem tra hang hoa da
     * duoc them truoc do
     */
    public String checkGoodsExist(List lstGoods, String stockModelId) throws Exception {
        if (lstGoods != null) {

            if (stockModelId != null && !"".equals(stockModelId.trim())) {
                Long lStockModelId = Long.parseLong(stockModelId.trim());
                for (int i = 0; i < lstGoods.size(); i++) {
                    if (lstGoods.get(i) instanceof SaleToCollaboratorForm) {
                        SaleToCollaboratorForm form = (SaleToCollaboratorForm) lstGoods.get(i);
                        if (form.getStockModelId().equals(lStockModelId)) {
                            return "saleColl.warn.Existed";
                        }
                    }
                }

            } else {

                return "";
            }
        }
        return "";
    }

    /*
     * Author: Tuannv Date created: 20/04/2009 Purpose: Thuc hien xoa hang hoa
     */
    public String delGoods() throws Exception {
        log.debug("# Begin method delGoods");
        pageForward = "delGoodsCollaborator";
        HttpServletRequest req = getRequest();
        List<SaleToCollaboratorForm> lstGoods = (List<SaleToCollaboratorForm>) getTabSession(LIST_GOOD);
        if (lstGoods == null) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.Empty");
            return pageForward;
        }
        String stockModelId = (String) req.getParameter("stockModelId");
        if (stockModelId == null || stockModelId.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.NotSelect");
            return pageForward;
        }
        Long lStockModelId = Long.parseLong(stockModelId.trim());
        for (int i = 0; i < lstGoods.size(); i++) {
            SaleToCollaboratorForm frm = lstGoods.get(i);
            if (frm.getStockModelId().equals(lStockModelId)) {
                lstGoods.remove(i);
                break;
            }
        }

        this.sumSaleTransAmount(lstGoods);
        setTabSession(LIST_GOOD, lstGoods);
        saleToCollaboratorForm.resetForm();
        clearTempList(req.getSession());
        req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.delSuccess");
        log.debug("# End method addResource");
        return pageForward;
    }

    private Staff getStaffByIdNotStatus(Long staffId) {
        Staff staff = null;
        if (staffId == null) {
            return staff;
        }

        String strQuery = "from Staff where staffId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, staffId);
        List<Staff> listStaff = query.list();
        if (listStaff != null && listStaff.size() > 0) {
            staff = listStaff.get(0);
        }
        return staff;
    }

    /**
     *
     * author : Tuannv date : 20/04/2009 purpose : thuc hien ban hang cho cong
     * tac vien modified : tamdt1, 28/10/2009
     *
     */
    public String saveSaleToCollaborator() throws Exception {
        log.debug("Begin method saveSaleToCollaborator of SaleToCollaboratorDAO...");
        HttpServletRequest req = getRequest();
        Session cmPreSession = null;
        try {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());

            req.setAttribute(IS_EDIT, false);
            saleToCollaboratorForm.resetForm();

            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            if (saleToCollaboratorForm.getAgentTypeIdSearch() == null || saleToCollaboratorForm.getAgentTypeIdSearch().trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.085");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            if (saleToCollaboratorForm.getAgentCodeSearch() == null || saleToCollaboratorForm.getAgentCodeSearch().trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.086");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            Staff staff = staffDAO.findStaffAvailableByStaffCode(saleToCollaboratorForm.getAgentCodeSearch().trim());


            // tannh20180509 : kiem tra han muc cong no
            DbProcessor db = new DbProcessor();
            //LinhNBV 201180524: Comment code. already check.
//            Staff staffLogin = getStaffByIdNotStatus(userToken.getUserID());
//            if (staffLogin.getLimitMoney() == null || staffLogin.getLimitDay() == null || staffLogin.getLimitApproveTime() == null
//                    || staffLogin.getLimitMoney().equalsIgnoreCase("") || staffLogin.getLimitDay().equalsIgnoreCase("")) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Error. You have not limit money !");
//                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
//            }
//
//            if (db.checkHaveTransOverMoney(userToken.getLoginName(), userToken.getUserID(), staffLogin.getLimitMoney(), Double.parseDouble(saleToCollaboratorForm.getAmountTaxMoney()))) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Limit money !");
//                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
//            }

            if (staff == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.087");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            if (staff.getChannelTypeId() != 1000485L && "2".equals(saleToCollaboratorForm.getPayMethodId())) {
                //Paymethod = 2: Bank Transfer --> Only accept for Master agent.
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.160");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if (staff.getShopId().compareTo(userToken.getShopId()) != 0) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.096");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if (this.IS_STOCK_STAFF_OWNER) {
                if (staff.getStaffOwnerId().compareTo(userToken.getUserID()) != 0) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.096");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
            }
            if (staff.getChannelTypeId() == null) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.088");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if ((saleToCollaboratorForm.getPayMethodId() == null) || (saleToCollaboratorForm.getPayMethodId().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "saleRetail.warn.pay");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                //Check them thanh toan qua Emola can co SDT Emola
            } else if (saleToCollaboratorForm.getPayMethodId().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
                if (saleToCollaboratorForm.getIsdnWallet() == null || saleToCollaboratorForm.getIsdnWallet().equals("")) {
                    req.setAttribute(REQUEST_MESSAGE, "saleRetail.warn.IsdnWallet");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
            }

            //check channel type of channel must is_vt_unit = 2
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO(getSession());
            ChannelType channelType = channelTypeDAO.findById(staff.getChannelTypeId());
            if (channelType == null || channelType.getIsVtUnit() == null || channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.089");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if ((saleToCollaboratorForm.getReasonId() == null) || (saleToCollaboratorForm.getReasonId().equals(0L))) {
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.reason");
                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if ((saleToCollaboratorForm.getPayMethodId() == null) || (saleToCollaboratorForm.getPayMethodId().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.payMethodNotEmpty");
                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            if ((saleToCollaboratorForm.getSaleDate() == null) || (saleToCollaboratorForm.getSaleDate().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "saleColl.warn.saleDate");
                log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }


            List<SaleToCollaboratorForm> lstGoods = (List<SaleToCollaboratorForm>) getTabSession(LIST_GOOD);
            if (lstGoods == null || lstGoods.isEmpty()) {
                req.setAttribute("returnMsg", "Error. You must select goods!");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            /*
             *add by sonbc2
             *date: 11/04/2016
             */
            ResourceBundle resource = ResourceBundle.getBundle("config");
            if (lstGoods.size() > Integer.parseInt(resource.getString("MAX_OF_GOODS"))) {
                req.setAttribute(REQUEST_MESSAGE, "sale.warn.Exceeds");
                List listMessageParam = new ArrayList();
                listMessageParam.add(resource.getString("MAX_OF_GOODS"));
                req.setAttribute("returnMsgValue", listMessageParam);
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            //end sonbc2

            // tutm1 03/11/2011 : check danh sach hang hoa co mat hang nao khong phai la mat hang ban dut theo kenh hay khong
            String notSaleStockModel = null;
            StockModelDAO stockModelDAO = new StockModelDAO();
            notSaleStockModel = stockModelDAO.getNotDepositSaleModel(lstGoods, staff.getChannelTypeId(), Constant.STOCKMODEL_TRANSTYPE_SALE);
            if (notSaleStockModel != null && !notSaleStockModel.trim().equals("")) {
                req.setAttribute("returnMsg", "stock.model.notSale");
                List listMessageParam = new ArrayList();
                listMessageParam.add(notSaleStockModel);
                req.setAttribute("returnMsgValue", listMessageParam);
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }


            /**
             * Modified by : TrongLV Modified date : 2011-08-17 Purpose : check
             * ban bo hang hoa
             */
            String result = this.checkSalePackageGoods(lstGoods);

            if (!result.equals("")) {
                String[] arr = result.split("@");
                if (arr.length < 2) {
                    req.setAttribute("returnMsg", "E.100028");
                } else if (arr.length == 2) {
                    //req.setAttribute("returnMsg", "E.100034");
                    req.setAttribute("returnMsg", "E.100036");
                    /*List listParamValue = new ArrayList();
                     listParamValue.add(arr[0]);
                     listParamValue.add(arr[1]);
                     req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);*/
                } else {
                    req.setAttribute("returnMsg", "E.100035");
                    List listParamValue = new ArrayList();
                    listParamValue.add(arr[0]);
                    listParamValue.add(arr[1]);
                    listParamValue.add(arr[2]);
                    req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
                }
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            //Tinh lai tong tien, chiet khau
            this.sumSaleTransAmount(lstGoods);

            Map<String, Map<Long, Double>> map = (Map<String, Map<Long, Double>>) getTabSession("MapStockModelDiscountAmount");

            Map<Long, Double> mapStockModelDiscount = map.get("mapStockModelDiscount");
            SaleToCollaboratorForm tmpForm = lstGoods.get(0);
            Long telecomServiceId = tmpForm.getTelecomServiceId();
            Long priceId = tmpForm.getPriceId();
            if (telecomServiceId == null) {
//                req.setAttribute("returnMsg", "Lỗi. Không có loại dịch vụ!");
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            if (priceId == null) {
//                req.setAttribute("returnMsg", "Lỗi mặt hàng chưa được khai báo giá!");
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }
            Price priceBO = priceDAO.findById(priceId);
            if (priceBO == null) {
//                req.setAttribute("returnMsg", "Lỗi mặt hàng chưa được khai báo giá!");
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.095");
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

            /**/
            saleToCollaboratorForm.setTelecomServiceId(telecomServiceId);//Loai dich vu
            saleToCollaboratorForm.setSaleTransVat(priceBO.getVat().toString());//Ti gia VAT
            saleToCollaboratorForm.setCurrency(priceBO.getCurrency());//Loai tien te
            //Check dieu kien neu ban goi hang thi da du chua
            List<ViewPackageCheck> listView = new ArrayList<ViewPackageCheck>();
            for (int i = 0; i < lstGoods.size(); i++) {
                SaleToCollaboratorForm saleTransForm = (SaleToCollaboratorForm) lstGoods.get(i);
                ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
//                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                StockModel stockModel = stockModelDAO.findById(saleTransForm.getStockModelId());
                viewPackageCheck.setStockModelId(stockModel.getStockModelId());
                viewPackageCheck.setStockModelCode(stockModel.getStockModelCode());
                viewPackageCheck.setQuantity(saleTransForm.getQuantity());
                listView.add(viewPackageCheck);
            }
            String outPut = checkPackageGoodsToSaleTrans(listView);
            if (!"".equals(outPut)) {
                for (int i = 0; i < lstGoods.size(); i++) {
                    SaleToCollaboratorForm saleTransForm = (SaleToCollaboratorForm) lstGoods.get(i);
                    PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
                    List<PackageGoodsDetail> listPackageGoodsDetail = packageGoodsDetailDAO.findByStockModelId(saleTransForm.getStockModelId());
                    if (listPackageGoodsDetail.size() != 0) {
                        req.setAttribute("returnMsg", outPut);
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                    }

                    if (listPackageGoodsDetail.size() == 0 && saleTransForm.getTypeOfSale() == 1L) {
                        req.setAttribute("returnMsg", "MSG.TYP.004");
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                    }
                }
            }
            //QuocDM1: Check mat hang duoc ban rieng/ban theo goi thi khong dc phep ban rieng voi gia ban theo goi.
            String strResult = validateGoodsWhenSale(lstGoods);
            if (!"".equals(strResult)) {
                req.setAttribute("returnMsg", strResult);
                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
            }

//            tannh20180111 
            String lStr = null;
            if (saleToCollaboratorForm.getPayMethodId().equals("2")) {

                // check khong nhap orderCode
//                if (saleToCollaboratorForm.getOrderCode() == null || saleToCollaboratorForm.getOrderCode().trim().equalsIgnoreCase("")) {
//                    req.setAttribute("returnMsg", "ERR.SAE.153");
//                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
//                } else {
//                    if (checkAlreadlyHaveOrderCode(saleToCollaboratorForm.getOrderCode())) {
//                        req.setAttribute("returnMsg", "ERR.SAE.152");
//                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
//                    }
//                }


//                if (!saleToCollaboratorForm.getImageUrl().equals("")) {
//                    String serverFilePath = this.saleToCollaboratorForm.getImageUrl();
//                    String[] fileParth = serverFilePath.split(",");
//                    // tannh 11/23/2017 ; upload file to server 
//                    String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
//                    String userName = ResourceBundleUtils.getResource("server_get_file_to_server_username");
//                    String password = ResourceBundleUtils.getResource("server_get_file_to_server_password");
//                    String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir_upto_file_accounttrans");
//                    if (fileParth[0].length() == 0) {
//                        saleToCollaboratorForm.setImageUrl("");
//                    } else {
//                        if (fileParth[0].endsWith(".rar")) {
//
//                            if (fileParth[0].contains("\\")) {
//                                lStr = fileParth[0].substring(fileParth[0].lastIndexOf("\\"));
//                            } else {
//                                lStr = fileParth[0].substring(fileParth[0].lastIndexOf("/"));
//                            }
//                            lStr = tempDir + lStr.substring(1);
//                            saleToCollaboratorForm.setImageUrl(lStr);
//                            List<String> lst = new ArrayList<String>();
//                            lst.add(fileParth[0]);
//                            uploadListFileFromFTPServer(host, userName, password, lst, tempDir, tempDir);
//                        } else {
//                            this.req.setAttribute("messageUpdate", "ER.RAR");
//                            return PAGE_FORWARD_SALE_TO_COLLABORATOR;
//                        }
//                    }
//                }
                //save giao dich ban hang
                SaleTransOrder saleTransOrder = expSaleTransOrder(userToken, staff, lStr, saleToCollaboratorForm.getOrderCode());
                logdetail.info("SaleToCollaborator: " + "CustName: " + saleToCollaboratorForm.getAgentCodeSearch() + "isdnWallet " + saleToCollaboratorForm.getIsdnWallet() + "SaleTransOrder");
                if (saleTransOrder == null) {
                    logdetail.info("expSaleTransOrder is fail.");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    //save giao dich ban hang that bai
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.097");
                    log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }

                /* check ban kit da nang : tu dong dang ky thong tin thue bao tra truoc */
                String kitMfsIsdn = "";
                String sendMess = getText("msg.send.saleToCollaborator.begin.buy");
                sendMess = String.format(sendMess, DateTimeUtils.convertDateToString_tuannv(getSysdate()));
                for (int i = 0; i < lstGoods.size(); i++) {
                    SaleToCollaboratorForm SaleTransForm = (SaleToCollaboratorForm) lstGoods.get(i);
                    /*check da nhap serial hay chua*/
                    if (SaleTransForm.getCheckSerial() != null && SaleTransForm.getCheckSerial().equals(1L)) {
                    } else {

                        if (SaleTransForm.getStockModelCode() != null && SaleTransForm.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            //save giao dich ban hang that bai
                            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.098");
                            //LinhNBV change saleTransId --> saleTransOrderId
                            logdetail.info("SaleTransToRetail: dont accept Anypay " + saleTransOrder.getSaleTransOrderId());
                            log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                            return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                        }
                    }
                    if (SaleTransForm.getToOwnerId() == null || !staff.getStaffId().equals(SaleTransForm.getToOwnerId())) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        //save giao dich ban hang that bai
                        req.setAttribute(REQUEST_MESSAGE, "E.200001");
                        //LinhNBV change saleTransId --> saleTransOrderId
                        logdetail.info("SaleToCollaborator: Check ToOwnerId Fail" + saleTransOrder.getSaleTransOrderId());
                        log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;

                    }
                    //save thong tin chi tiet ve giao dich, saleTransDetail + saleTransSerial
                    logdetail.info("SaleToCollaborator: saveSaleTransDetail ");
                    SaleTransDetailOrder saleTransDetailOrder = expSaleTransDetailOrder(userToken, staff, saleTransOrder, SaleTransForm, mapStockModelDiscount);
                    if (saleTransDetailOrder == null) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.099");
                        //LinhNBV change saleTransId --> saleTransOrderId
                        logdetail.info("SaleToCollaborator: saveSaleTransDetailOrder fail. saletransId" + saleTransOrder.getSaleTransOrderId());
                        log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                    }

                    SaleTransForm.setKitMfsIsdn(null);
                    logdetail.info("SaleToCollaborator: saveSaleTransSerial ");
//                    if (SaleTransForm.getCheckSerial() != null && SaleTransForm.getCheckSerial().equals(1L)) {
//                        if (!saveSaleTransSerialOrder(userToken, staff, SaleTransForm, saleTransDetailOrder)) {
//                            getSession().clear();
//                            getSession().getTransaction().rollback();
//                            getSession().beginTransaction();
//                            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.100");
//                            //LinhNBV change saleTransId --> saleTransOrderId
//                            logdetail.info("SaleToCollaborator: saveSaleTransSerialOrder fail. saletransId" + saleTransOrder.getSaleTransOrderId());
//                            log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
//                            return PAGE_FORWARD_SALE_TO_COLLABORATOR;
//                        }
//                    }
                    if (kitMfsIsdn.equals("") && SaleTransForm.getKitMfsIsdn() != null && !SaleTransForm.getKitMfsIsdn().equals("")) {
                        kitMfsIsdn = SaleTransForm.getKitMfsIsdn();
                    }
                    if (i != 0) {
                        sendMess += ", " + saleTransDetailOrder.getQuantity() + " " + saleTransDetailOrder.getStockModelName();
                    } else {
                        sendMess += " " + saleTransDetailOrder.getQuantity() + " " + saleTransDetailOrder.getStockModelName();
                    }

                }
                fileDowd = expOrderToExcel(lstGoods, saleToCollaboratorForm);
            } else {
                //LinhNBV start modified on May 24 2018: Check amountSaleTrans + deposit + payment + amountTax > limitMoney
//            if (isStaffMaputo(userToken.getLoginName())) {
                double sumSaleTrans = db.sumSaleTransDebitByStaffCode(userToken.getLoginName());
                double sumDeposit = db.sumDepositDebitByStaffCode(userToken.getLoginName());
                double sumPayment = db.sumPaymentDebitByStaffCode(userToken.getLoginName());
                double limitMoney = db.getLimitMoneyByStaffCode(userToken.getLoginName());
                if ((sumSaleTrans + sumDeposit + sumPayment + Double.parseDouble(saleToCollaboratorForm.getAmountTaxMoney().replaceAll(",", ""))) > limitMoney) {
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.159");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
//            }
                //end.
                //save giao dich ban hang
                SaleTrans saleTrans = expSaleTrans(userToken, staff);
                logdetail.info("SaleToCollaborator: " + "CustName: " + saleToCollaboratorForm.getAgentCodeSearch() + "isdnWallet " + saleToCollaboratorForm.getIsdnWallet() + "saveSaleTrans");
                if (saleTrans == null) {
                    logdetail.info("expSaleTrans is fail.");
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    //save giao dich ban hang that bai
                    req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.097");
                    log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }
                //DINHDC CODE THEM PHAN THANH TOAN QUA EMOLA
                ResponseWallet responseWallet = new ResponseWallet();
                SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
                if (saleToCollaboratorForm.getPayMethodId() != null
                        && saleToCollaboratorForm.getPayMethodId().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
                    if (saleTrans.getSaleTransId() > 0) {
                        logdetail.info("SaleToCollaborator: " + "CustName: " + saleToCollaboratorForm.getAgentCodeSearch() + "isdnWallet " + saleToCollaboratorForm.getIsdnWallet() + "saleTransEmola");
                        SaleTransEmola saleTransEmola = saleCommonDAO.insertSaleTransEmola(saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
                                saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
                                "0", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), saleToCollaboratorForm.getIsdnWallet(), null, "New Create Transaction", "1", getSysdate());
                        if (saleTransEmola == null || saleTransEmola.getSaleTransId() == null) {
                            logdetail.info("SaleToCollaborator: saleTransEmola is fail. saleTransId :" + saleTrans.getSaleTransId());
                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();
                        } else {
                            logdetail.info("SaleToCollaborator: saleTransEmola is success. saleTransId: " + saleTrans.getSaleTransId());
                            //Commit truoc sau do se giao tiep voi API Vi Dien Tu
                            this.getSession().save(saleTransEmola);
                            this.getSession().getTransaction().commit();
                            this.getSession().getTransaction().begin();
                        }
                    }
                }
                //END
                /* check ban kit da nang : tu dong dang ky thong tin thue bao tra truoc */
                String kitMfsIsdn = "";

                String sendMess = getText("msg.send.saleToCollaborator.begin.buy");
                sendMess = String.format(sendMess, DateTimeUtils.convertDateToString_tuannv(getSysdate()));
                for (int i = 0; i < lstGoods.size(); i++) {
                    SaleToCollaboratorForm SaleTransForm = (SaleToCollaboratorForm) lstGoods.get(i);
                    /*check da nhap serial hay chua*/
                    if (SaleTransForm.getCheckSerial() != null && SaleTransForm.getCheckSerial().equals(1L)) {
                        if (SaleTransForm.getLstSerial() == null || SaleTransForm.getLstSerial().isEmpty()) {
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            //save giao dich ban hang that bai
                            logdetail.info("SaleToCollaborator: Serial list empty. saletransId" + saleTrans.getSaleTransId());
                            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.098");
                            log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                            return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                        }
                    } else {
                        if (SaleTransForm.getStockModelCode() != null && SaleTransForm.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            //save giao dich ban hang that bai
                            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.098");
                            logdetail.info("SaleTransToRetail: dont accept Anypay " + saleTrans.getSaleTransId());
                            log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                            return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                        }
                    }
                    if (SaleTransForm.getToOwnerId() == null || !staff.getStaffId().equals(SaleTransForm.getToOwnerId())) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        //save giao dich ban hang that bai
                        req.setAttribute(REQUEST_MESSAGE, "E.200001");
                        logdetail.info("SaleToCollaborator: Check ToOwnerId Fail" + saleTrans.getSaleTransId());
                        log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;

                    }
                    //save thong tin chi tiet ve giao dich, saleTransDetail + saleTransSerial
                    logdetail.info("SaleToCollaborator: saveSaleTransDetail ");
                    SaleTransDetail saleTransDetail = expSaleTransDetail(userToken, staff, saleTrans, SaleTransForm, mapStockModelDiscount);
                    if (saleTransDetail == null) {
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.099");
                        logdetail.info("SaleToCollaborator: saveSaleTransDetail fail. saletransId" + saleTrans.getSaleTransId());
                        log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                    }

                    SaleTransForm.setKitMfsIsdn(null);
                    logdetail.info("SaleToCollaborator: saveSaleTransSerial ");
                    if (SaleTransForm.getCheckSerial() != null && SaleTransForm.getCheckSerial().equals(1L)) {
                        if (!saveSaleTransSerial(userToken, staff, SaleTransForm, saleTransDetail)) {
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.100");
                            logdetail.info("SaleToCollaborator: saveSaleTransSerial fail. saletransId" + saleTrans.getSaleTransId());
                            log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
                            return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                        }
                    }

                    if (kitMfsIsdn.equals("") && SaleTransForm.getKitMfsIsdn() != null && !SaleTransForm.getKitMfsIsdn().equals("")) {
                        kitMfsIsdn = SaleTransForm.getKitMfsIsdn();
                    }

                    if (i != 0) {
                        sendMess += ", " + saleTransDetail.getQuantity() + " " + saleTransDetail.getStockModelName();
                    } else {
                        sendMess += " " + saleTransDetail.getQuantity() + " " + saleTransDetail.getStockModelName();
                    }

                }
                sendMess += getText("msg.send.saleToCollaborator.end");


                /* HAN MUC KHO HANG */
                Double saleTransAmount = sumAmountByGoodsList(lstGoods);
                if (saleTransAmount != null && saleTransAmount > 0) {
                    //Cap nhat lai gia tri hang hoa cua nhan vien
                    if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * saleTransAmount)) {
                        req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0001"));
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                    }
                } else if (saleTransAmount != null && saleTransAmount < 0) {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0014"));
                    this.getSession().getTransaction().rollback();
                    this.getSession().clear();
                    this.getSession().getTransaction().begin();
                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                }

                /* DKTT cho KIT da nang */
                if (!kitMfsIsdn.equals("")) {
                    cmPreSession = getSession("cm_pre");
                    cmPreSession.beginTransaction();


                    boolean isExists = InterfaceCMToIM.checkIdNoAlreadyRegisterSTK(staff.getIdNo(), cmPreSession);
                    if (isExists) {
                        req.setAttribute(REQUEST_MESSAGE, "Error. You can not sale KIT_MFS because exists STK subcriber with ID_NO = " + staff.getIdNo());
                        this.getSession().getTransaction().rollback();
                        this.getSession().clear();
                        this.getSession().getTransaction().begin();
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                    }

                    if (!regCust(1, userToken, staff, kitMfsIsdn, cmPreSession, req, REQUEST_MESSAGE)) {
                        this.getSession().getTransaction().rollback();
                        this.getSession().clear();
                        this.getSession().getTransaction().begin();

                        cmPreSession.getTransaction().rollback();
                        cmPreSession.clear();
                        cmPreSession.beginTransaction();
                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                    }
                }

                //Nhan tin thong bao cho CTV
                String sql = "From AccountAgent where lower(ownerCode) = ?";
                Query query = getSession().createQuery(sql);
                query.setParameter(0, staff.getStaffCode().toLowerCase());
                List<AccountAgent> list = query.list();
                if (list != null && list.size() > 0) {
                    AccountAgent accountAgent = list.get(0);
//                int sentResult = SmsClient.sendSMS155(accountAgent.getMsisdn(), sendMess);
                }

                //DINHDC CODE THEM PHAN THANH TOAN QUA EMOLA
                if (saleToCollaboratorForm.getPayMethodId() != null
                        && saleToCollaboratorForm.getPayMethodId().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
                    String amountPay = "";
                    if (saleTrans.getSaleTransId() > 0) {
                        SaleTransEmola saleTransEmolaUpdate = saleCommonDAO.getSaleTransEmolaWithSaleTransId(this.getSession(), saleTrans.getSaleTransId());
                        logdetail.info("SaleTransToRetail: Start Call API Payment Keeto.saletransId" + saleTrans.getSaleTransId());
                        if (saleTransEmolaUpdate != null) {
                            if (saleTrans.getAmountTax() != null) {
                                amountPay = saleTrans.getAmountTax().toString();
                            }
                            //Giao tiep voi API ben vi
                            String response = saleCommonDAO.funcPaymentEMolaSaleTrans(this.getSession(),
                                    saleToCollaboratorForm.getIsdnWallet(), amountPay, saleToCollaboratorForm.getCustName(),
                                    saleTrans.getSaleTransCode(), saleTrans.getSaleTransId().toString());
                            if ("ERROR".equals(response)) {
                                req.setAttribute(Constant.RETURN_MESSAGE, "Error.Call.WS.Payment.Wallet");
                                //rollback nghiep vu cua ban hang
                                logdetail.info("SaleTransToRetail: Error Call API Payment Keeto .saletransId" + saleTrans.getSaleTransId());
                                this.getSession().clear();
                                this.getSession().getTransaction().rollback();
                                this.getSession().getTransaction().begin();
                                //Update trang thai Sale Trans va Sale Trans Emola
                                logdetail.info("SaleTransToRetail: Start Update status Error:" + saleTrans.getSaleTransId());
                                saleTransEmolaUpdate.setStatus("2");
                                saleTransEmolaUpdate.setErrorCode("0");
                                saleTransEmolaUpdate.setNote("Error.Call.WS.Payment.Wallet");
                                this.getSession().update(saleTransEmolaUpdate);
                                saleTrans.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
                                this.getSession().update(saleTrans);
                                logdetail.info("SaleTransToRetail: End Update status Error:" + saleTrans.getSaleTransId());
                                return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                            } else {
                                Gson gson = new Gson();
                                responseWallet = gson.fromJson(response, ResponseWallet.class);
                                if (responseWallet != null && responseWallet.getResponseCode() != null) {
                                    if (responseWallet.getResponseCode().equals("01")) {
                                        req.setAttribute(Constant.RETURN_MESSAGE, "Payment.Sale.Trans.Success");

//                          saleCommonDAO.insertSaleTransEmola(this.getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
//                                  saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
//                                  "1", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), this.saleTransForm.getIsdnWallet(), "1", "PAYMENT SUCCESS", "1", getSysdate());

                                        logdetail.info("SaleTransToRetail: Start Update status success:" + saleTrans.getSaleTransId());
                                        saleTransEmolaUpdate.setStatus("1");
                                        saleTransEmolaUpdate.setErrorCode("1");
                                        saleTransEmolaUpdate.setNote("Payment.Sale.Trans.Success");
                                        this.getSession().update(saleTransEmolaUpdate);

                                        saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
                                        this.getSession().update(saleTrans);
                                        logdetail.info("SaleTransToRetail: End Update status success:" + saleTrans.getSaleTransId());
                                    } else {
                                        req.setAttribute(Constant.RETURN_MESSAGE, "Payment.Emola.Sale.Trans.UnSuccess");

//                          saleCommonDAO.insertSaleTransEmola(this.getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
//                                  saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
//                                  "2", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), this.saleTransForm.getIsdnWallet(), "0", "Payment.Emola.Sale.Trans.UnSuccess", "1", getSysdate());

                                        //rollback nghiep vu cua ban hang
                                        logdetail.info("SaleTransToRetail: Error Payment .saletransId" + saleTrans.getSaleTransId());
                                        this.getSession().clear();
                                        this.getSession().getTransaction().rollback();
                                        this.getSession().getTransaction().begin();
                                        //Update trang thai Sale Trans va Sale Trans Emola
                                        logdetail.info("SaleTransToRetail: Start Update status Error :" + saleTrans.getSaleTransId());
                                        saleTransEmolaUpdate.setStatus("2");
                                        saleTransEmolaUpdate.setErrorCode("0");
                                        saleTransEmolaUpdate.setNote("Payment.Emola.Sale.Trans.UnSuccess:" + responseWallet.getResponseCode());
                                        this.getSession().update(saleTransEmolaUpdate);

                                        saleTrans.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
                                        this.getSession().update(saleTrans);
                                        logdetail.info("SaleTransToRetail: End Update status Error :" + saleTrans.getSaleTransId());
                                        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                                    }
                                } else {
                                    req.setAttribute(Constant.RETURN_MESSAGE, "Error.Call.WS.Payment.Wallet");

//                       saleCommonDAO.insertSaleTransEmola(this.getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
//                               saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
//                               "2", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), this.saleTransForm.getIsdnWallet(), "0", "Error.Call.WS.Payment.Wallet", "1", getSysdate());

                                    //rollback nghiep vu cua ban hang
                                    logdetail.info("SaleTransToRetail: Error.Call.WS.Payment.Wallet" + saleTrans.getSaleTransId());
                                    this.getSession().clear();
                                    this.getSession().getTransaction().rollback();
                                    this.getSession().getTransaction().begin();
                                    //Update trang thai Sale Trans va Sale Trans Emola
                                    logdetail.info("SaleTransToRetail: Start Update status Error :" + saleTrans.getSaleTransId());
                                    saleTransEmolaUpdate.setStatus("2");
                                    saleTransEmolaUpdate.setErrorCode("0");
                                    saleTransEmolaUpdate.setNote("Error.Call.WS.Payment.Wallet");
                                    this.getSession().update(saleTransEmolaUpdate);

                                    saleTrans.setStatus(Constant.SALE_TRANS_STATUS_CANCEL);
                                    this.getSession().update(saleTrans);
                                    logdetail.info("SaleTransToRetail: End Update status Error :" + saleTrans.getSaleTransId());
                                    return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                                }
                            }
                        } else {
                            logdetail.info("SaleTransToRetail: Error. Can not update sale transaction! :" + saleTrans.getSaleTransId());
                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();
                            return PAGE_FORWARD_SALE_TO_COLLABORATOR;
                        }
                    }
                }
                //END
            }


            getSession().getTransaction().commit();
            getSession().flush();
            getSession().beginTransaction();

            if (cmPreSession != null) {
                cmPreSession.getTransaction().commit();
                cmPreSession.flush();
                cmPreSession.beginTransaction();
            }

            removeTabSession(LIST_GOOD);



            if (saleToCollaboratorForm.getPayMethodId().equals("2")) {
                req.setAttribute(REQUEST_MESSAGE, "Payment.Create.Order.Trans.Success");
                //Send sms to Finance staff
                String message = ResourceBundle.getBundle("config").getString("msgToFinanceStaff");
                String staffCode = userToken.getLoginName();
//                String orderCode = saleToCollaboratorForm.getOrderCode();
                String orderCode = "";

                List<String> lstIsdn = getListNumberFinancialDepartment(userToken.getShopCode());
                for (String isdn : lstIsdn) {
                    db.sendSms("258" + isdn, message.replace("XXX", staffCode).replace("YYY", orderCode), "86904");
                }
//                test
//                db.sendSms("258871233799", message.replace("XXX", staffCode).replace("YYY", orderCode), "86904");
                saleToCollaboratorForm = new SaleToCollaboratorForm();
                saleToCollaboratorForm.setPayMethodId(Constant.PAY_METHOD_ACCOUNT_TRANFER);

                //LinhNBV start modified on May 22 2018: Show download order when create order success.
                req.getSession().setAttribute("downloadOrderForChannel", 1);

            } else {
                saleToCollaboratorForm = new SaleToCollaboratorForm();
                saleToCollaboratorForm.setPayMethodId(Constant.PAY_METHOD_MONNEY);
                req.setAttribute(REQUEST_MESSAGE, "Payment.Sale.Trans.Success");
            }
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            ex.printStackTrace();
            if (cmPreSession != null) {
                cmPreSession.getTransaction().rollback();
                cmPreSession.clear();
                cmPreSession.beginTransaction();
            }

            req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.097");
        }

        saleToCollaboratorForm.setSaleDate(DateTimeUtils.getSysDateTime("yyyy-MM-dd"));
        log.debug("End method saveSaleToCollaborator of SaleToCollaboratorDAO");
        return PAGE_FORWARD_SALE_TO_COLLABORATOR;
    }

    /**
     * @Author: Tuannv
     * @Date created: 25/03/2009
     * @Purpose: save sale trans serial
     */
    private boolean saveSaleTransSerial(UserToken userToken, Staff staff, SaleToCollaboratorForm saleTransDetailBean, SaleTransDetail saleTransDetail)
            throws Exception {

        Long stockModelId = 0L;
        List lstSerial = null;
        boolean noError = true;

        stockModelId = saleTransDetailBean.getStockModelId();

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return false;
        }
        int total = 0;
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        String tableName = baseStockDAO.getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
        String SQLUPDATE = "update " + tableName + " set status = ?, owner_type = ?, owner_id = ? where stock_model_id = ? "
                + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";

        if (stockModel.getStockTypeId() != null
                && (stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)
                || stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_PRE_PAID)
                || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_POST_PAID))) {

            SQLUPDATE = "update " + tableName + " set status = ?, owner_type = ?, owner_id = ?, CHANNEL_STOCK_STATUS = ? where stock_model_id = ? "
                    + " and owner_type =? and owner_id = ? and  to_number(serial) >= ? and to_number(serial) <= ? and status = ? ";
        }
        lstSerial = saleTransDetailBean.getLstSerial();
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
                BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
                Long totals = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                Long saleTransDetailId = getSequence("SALE_TRANS_SERIAL_SEQ");
                SaleTransSerial saleTransSerial = new SaleTransSerial();
                saleTransSerial.setSaleTransSerialId(saleTransDetailId);
                saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
                saleTransSerial.setStockModelId(stockModelId);
                saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
                saleTransSerial.setFromSerial(stockSerial.getFromSerial());
                saleTransSerial.setToSerial(stockSerial.getToSerial());
                saleTransSerial.setQuantity(totals);
                this.getSession().save(saleTransSerial);

                //Cap nhat ve kho CTV/DB & trang thai da ban
                Query query = getSession().createSQLQuery(SQLUPDATE);
                if (stockModel.getStockTypeId() != null
                        && (stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)
                        || stockModel.getStockTypeId().equals(Constant.STOCK_KIT)
                        || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_PRE_PAID)
                        || stockModel.getStockTypeId().equals(Constant.STOCK_SIM_POST_PAID))) {

                    query.setParameter(0, Constant.STATUS_SALED);
                    query.setParameter(1, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(2, staff.getStaffId());
                    query.setParameter(3, 1L);
                    query.setParameter(4, stockModelId);
                    query.setParameter(5, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(6, userToken.getUserID());
                    query.setParameter(7, stockSerial.getFromSerial());
                    query.setParameter(8, stockSerial.getToSerial());
                    query.setParameter(9, Constant.STATUS_USE);
                } else {
                    query.setParameter(0, Constant.STATUS_SALED);
                    query.setParameter(1, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(2, staff.getStaffId());
                    query.setParameter(3, stockModelId);
                    query.setParameter(4, Constant.OWNER_TYPE_STAFF);
                    query.setParameter(5, userToken.getUserID());
                    query.setParameter(6, stockSerial.getFromSerial());
                    query.setParameter(7, stockSerial.getToSerial());
                    query.setParameter(8, Constant.STATUS_USE);
                }

                int count = query.executeUpdate();
                if (count != totals.intValue()) {
                    return false;
                }
                total += count;
                //luu thong tin vao bang VcRequets                                
                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                    VcRequestDAO vcRequestDAO = new VcRequestDAO();
                    vcRequestDAO.setSession(getSession());
                    vcRequestDAO.saveVcRequest(DateTimeUtils.getSysDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_CTV, saleTransDetail.getSaleTransId());
                }

                if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                    ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                    reqActiveKitDAO.setSession(getSession());
                    reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                            Long.parseLong(Constant.SALE_TRANS_TYPE_COLLABORATOR), getSysdate());

                    /* Kiem tra neu la mat hang KIT da nang thi lay gia tri ISDN gan voi KIT */
                    if (checkKITCTV(stockModel.getStockModelCode())) {
                        if (stockSerial.getFromSerial().equals(stockSerial.getToSerial())) {
                            String sql = "From StockKit where to_number(serial) = ?";
                            Query query2 = getSession().createQuery(sql);
                            query2.setParameter(0, stockSerial.getFromSerial());
                            List<StockKit> list = query2.list();
                            if (list != null && list.size() > 0) {
                                saleTransDetailBean.setKitMfsIsdn(list.get(0).getIsdn());
                            }
                        }
                    }
                }
            }
        }
        //Check so luong serial update duoc khong bang tong so luong hang xuat di
        if (total != saleTransDetail.getQuantity().intValue()) {
            return false;
        }

        return noError;
    }

    /**
     *
     * author : TuanNV date : 25/03/2009 purpose : cap nhat giao dich ban hang
     * cho cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTrans expSaleTrans(UserToken userToken, Staff staff) {
        try {
            Long saleTransId = getSequence("SALE_TRANS_SEQ");
            SaleTrans saleTrans = new SaleTrans();
            saleTrans.setSaleTransId(saleTransId);
            saleTrans.setSaleTransDate(getSysdate());

            saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_COLLABORATOR); //loai giao dich: ban cho CTV/ diem ban
            saleTrans.setStatus(String.valueOf(Constant.SALE_PAY_NOT_BILL)); //da thanh toan nhung chua lap hoa don
            saleTrans.setShopId(userToken.getShopId());
            saleTrans.setStaffId(userToken.getUserID());

            saleTrans.setPayMethod(this.saleToCollaboratorForm.getPayMethodId()); //mac dinh giao dich do CM day sang la tien mat
            saleTrans.setDiscount(this.saleToCollaboratorForm.getDiscout()); //tien chiet khau
            saleTrans.setAmountTax(this.saleToCollaboratorForm.getAmountTax()); //tong tien phai tra cua KH, = chua thue + thue - KM - C/Khau
            saleTrans.setAmountNotTax(this.saleToCollaboratorForm.getAmountNotTax()); //tien chua thue

            if (this.saleToCollaboratorForm.getSaleTransVat() != null && !this.saleToCollaboratorForm.getSaleTransVat().trim().equals("")) {
                saleTrans.setVat(Double.valueOf(this.saleToCollaboratorForm.getSaleTransVat().replaceAll(",", ""))); //tien vat
            }

            saleTrans.setTax(this.saleToCollaboratorForm.getTax());
            saleTrans.setCurrency(this.saleToCollaboratorForm.getCurrency());

            saleTrans.setCustName(staff.getStaffCode() + " - " + staff.getName()); //doi voi ban hang cho CTV, luu thong tin truong nay la ten CTV
            saleTrans.setReceiverId(staff.getStaffId());
            saleTrans.setReceiverType(Constant.OWNER_TYPE_STAFF);

            saleTrans.setAddress(saleTrans.getAddress()); //CM day sang
            saleTrans.setReasonId(this.saleToCollaboratorForm.getReasonId());
            saleTrans.setTelecomServiceId(this.saleToCollaboratorForm.getTelecomServiceId());
            saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTransId));
            saleTrans.setCreateStaffId(userToken.getUserID());

            this.getSession().save(saleTrans);
            return saleTrans;
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return null;
        }

    }

    /**
     * author : TuanNV date : 25/03/2009 purpose : cap nhat giao dich ban hang
     * cho cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTransDetail expSaleTransDetail(UserToken userToken, Staff staff, SaleTrans saleTrans, SaleToCollaboratorForm saleTransForm, Map<Long, Double> mapStockModelDiscount) {
        try {
            /**
             * DUCTM_20110215_START log.
             */
            String function = "com.viettel.im.database.DAO.SaleToCollaboratorDAO.expSaleTransDetail";
            Long callId = getApCallId();
            Date startTime = new Date();
            logStartCallBegin(startTime, function, callId);

            SaleTransDetail saleTransDetail = new SaleTransDetail();

            //tamdt1, start, 15/07/2010
            //cap nhat them cac truong can thiet phuc vu bao cao doanh thu
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(saleTransForm.getStockModelId());
            if (stockModel != null) {
                saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
                saleTransDetail.setStockModelName(stockModel.getName());

                saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
                saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());

            } else {
                //khong tim thay gia
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }

            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
            if (stockType != null) {
                saleTransDetail.setStockTypeId(stockType.getStockTypeId());
                saleTransDetail.setStockTypeName(stockType.getName());
            } else {
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }
            //tamdt1, end

            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());
            Price price = priceDAO.findById(saleTransForm.getPriceId());
            if (price == null || price.getPriceId() == null) {
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }

            saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
            saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
            saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

            saleTransDetail.setStockModelId(saleTransForm.getStockModelId());

            saleTransDetail.setPriceId(price.getPriceId());

            saleTransDetail.setCurrency(price.getCurrency());
            saleTransDetail.setPriceVat(price.getVat());//thue suat
            saleTransDetail.setPrice(price.getPrice());
            saleTransDetail.setAmount(price.getPrice() * saleTransForm.getQuantity());
            saleTransDetail.setQuantity(saleTransForm.getQuantity());//so luong
            saleTransDetail.setDiscountAmount(0.0);//chiet khau
            saleTransDetail.setDiscountId(saleTransForm.getDiscountId());

            Double amountAfterTax = 0.0;
            Double amountTax = 0.0;
            Double amountBeforeTax = 0.0;

            if (!Constant.PRICE_AFTER_VAT) {
                amountBeforeTax = price.getPrice() * saleTransForm.getQuantity();
                amountTax = amountBeforeTax * price.getVat() / 100.0;
                amountAfterTax = amountBeforeTax + amountTax;
            } else {
                amountAfterTax = price.getPrice() * saleTransForm.getQuantity();
                amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
                amountTax = amountAfterTax - amountBeforeTax;
            }

            saleTransDetail.setAmountBeforeTax(amountBeforeTax);
            saleTransDetail.setAmountTax(amountTax);
            saleTransDetail.setVatAmount(amountTax);
            saleTransDetail.setAmountAfterTax(amountAfterTax);

            if (mapStockModelDiscount != null && !mapStockModelDiscount.isEmpty()) {
                Double discountAmount = mapStockModelDiscount.get(saleTransForm.getStockModelId());
                if (discountAmount != null) {//chiet khau
                    saleTransDetail.setDiscountAmount(discountAmount);
                }
            }

            saleTransDetail.setStateId(Constant.STATE_NEW);
            saleTransDetail.setNote(saleTransForm.getNote());
            this.getSession().save(saleTransDetail);

            //Tru kho nhan vien quan ly CTV/DB
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(this.getSession());
//            if (!stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW,
//                    saleTransForm.getStockModelId(), saleTransForm.getQuantity(), true)) {
//
//                logEndCall(startTime, new Date(), function, callId);
//                return null;
//            }
            GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF, saleTransForm.getStockModelId(),
                    Constant.STATE_NEW, -saleTransForm.getQuantity(), -saleTransForm.getQuantity(), null,
                    userToken.getUserID(), 0L, 0L, "", Constant.TRANS_EXPORT.toString(), StockTotalAuditDAO.SourceType.SALE_TRANS);
            boolean noError = genResult.isSuccess();

            //R499 trung dh3 add end
            if (noError == false) {
                return null;
            }

            logEndCall(startTime, new Date(), function, callId);
            return saleTransDetail;
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

            return null;
        }

    }

    /**
     *
     * author tuannv date: 28/04/2009 Tinh toan cac gia tri tong
     *
     */
    public void sumSaleTransAmount(List lstGoods) throws Exception {
        //Khoi tao gia tri tong
        SaleToCollaboratorForm f = this.getSaleToCollaboratorForm();
        getReqSession();

        List<SaleToCollaboratorForm> tmpList = new ArrayList(lstGoods);
        List<SaleTransDetailV2Bean> saleTransDetailBeanList = new ArrayList();

        for (int idx = 0; idx < tmpList.size(); idx++) {
            SaleToCollaboratorForm oldBean = tmpList.get(idx);
            SaleTransDetailV2Bean newBean = new SaleTransDetailV2Bean();

            newBean.setStockModelId(oldBean.getStockModelId());
            newBean.setQuantity(oldBean.getQuantity());
            newBean.setPriceId(oldBean.getPriceId());
            newBean.setPrice(oldBean.getPrice());
            newBean.setVat(Double.parseDouble(oldBean.getItemVat()));
            newBean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(oldBean.getPrice()));

            if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
                newBean.setAmountAfterTax(
                        newBean.getQuantity() * newBean.getPrice());//For update to sale_trans_detail
                newBean.setAmountBeforeTax(newBean.getAmountAfterTax() / (1.0 + newBean.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
                newBean.setAmountTax(newBean.getAmountAfterTax() - newBean.getAmountBeforeTax());
            } else {//Neu la gia truoc thue (HAITI)
                newBean.setAmountBeforeTax(
                        newBean.getQuantity() * newBean.getPrice());//For update to sale_trans_detail
                newBean.setAmountTax(newBean.getAmountBeforeTax() * newBean.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
                newBean.setAmountAfterTax(newBean.getAmountTax() + newBean.getAmountBeforeTax());
            }
            newBean.setAmountAfterTaxDisplay(
                    NumberUtils.rounđAndFormatAmount(newBean.getAmountAfterTax()));//For display to interface
            newBean.setAmountTaxDisplay(
                    NumberUtils.rounđAndFormatAmount(newBean.getAmountTax()));//For display to interface
            newBean.setAmountBeforeTaxDisplay(
                    NumberUtils.rounđAndFormatAmount(newBean.getAmountBeforeTax()));//For display to interface

            newBean.setDiscountGroupId(oldBean.getDiscountGroupId());
            newBean.setDiscountId(oldBean.getDiscountId());

            saleTransDetailBeanList.add(newBean);
        }

        //Tinh chiet khau
        SaleTransForm saleTransForm = new SaleTransForm();
        SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
        saleCommonDAO.setSession(this.getSession());
        Map<String, Map<Long, Double>> map = saleCommonDAO.sumDiscount(req, saleTransDetailBeanList, saleTransForm);
        setTabSession("MapStockModelDiscountAmount", map);

        //Tam thoi update discountId lai list ban dau / sau nay se thay doi su dung list moi
        for (int idx = 0; idx < saleTransDetailBeanList.size(); idx++) {
            for (int j = 0; j < lstGoods.size(); j++) {
                SaleToCollaboratorForm oldBean = (SaleToCollaboratorForm) lstGoods.get(j);
                if (oldBean != null && oldBean.getStockModelId() != null && saleTransDetailBeanList.get(idx).getStockModelId().equals(oldBean.getStockModelId())) {
                    oldBean.setDiscountGroupId(saleTransDetailBeanList.get(idx).getDiscountGroupId());
                    oldBean.setDiscountId(saleTransDetailBeanList.get(idx).getDiscountId());
                    break;
                }
            }
        }

        f.setAmountNotTaxMoney((saleTransForm.getAmountBeforeTaxDisplay()));
        f.setDiscountMoney((saleTransForm.getAmountDiscountDisplay()));
        f.setDiscoutMoney((saleTransForm.getAmountDiscountDisplay()));
        f.setTaxMoney((saleTransForm.getAmountTaxDisplay()));
        f.setAmountTaxMoney((saleTransForm.getAmountAfterTaxDisplay()));

        f.setTax(saleTransForm.getAmountTax());
        f.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        f.setAmountTax(saleTransForm.getAmountAfterTax());
        f.setDiscount(saleTransForm.getAmountDiscount());
        f.setDiscout(saleTransForm.getAmountDiscount());

    }

    /**
     *
     * author tuannv1 date: 16/04/2009 Tim kiem phieu xuat hang cho cong tac
     * vien
     *
     */
    private List getListReaSonSaleExpCollaborator() {
        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(getSession());
        Long[] saleTransType = new Long[1];
        saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_COLLABORATOR);
        List reasonList = reasonDAO.getReasonBySaleTransType(saleTransType);
        return reasonList;
    }

    public String getListCollaboratorCode() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String staffCode = httpServletRequest.getParameter("saleToCollaboratorForm.staffCode");
            if (staffCode != null && !staffCode.trim().equals("")) {
                //lay danh sach cac staff_code
                UserToken userToken = (UserToken) getRequest().getSession().getAttribute(Constant.USER_TOKEN);
                Long staffId = userToken.getUserID();
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                List<Staff> listStaff = staffDAO.findByPreStaffCode("staffCode", staffCode + "%", staffId);
                if (listStaff != null && listStaff.size() > 0) {
                    for (int i = 0; i < listStaff.size(); i++) {
                        Staff tmpStaff = listStaff.get(i);
                        hashMapResult.put(tmpStaff.getStaffId(), tmpStaff.getStaffCode());

                    }
                }
            }

        } catch (Exception e) {
            throw e;
        }

        return GET_LIST_COLLABORATOR_CODE;
    }

    public String getCollaboratorName() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String staffCode = httpServletRequest.getParameter("staffCode");
            String target = httpServletRequest.getParameter("target");
            if (staffCode != null && staffCode.trim().length() > 0) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                List<Staff> listStaff = staffDAO.findByPropertyAndStatus(StaffDAO.STAFF_CODE, staffCode, Constant.STATUS_ACTIVE);
                if (listStaff != null && listStaff.size() > 0) {
                    hashMapResult.put(target, listStaff.get(0).getName());
                } else {
                    hashMapResult.put(target, "");
                }
            } else {
                hashMapResult.put(target, "");
            }

        } catch (Exception ex) {
            throw ex;
        }

        return GET_COLLABORATOR_NAME;
    }
    private Map listModelCombo = new HashMap();
    private Map priceListMap = new HashMap();
    Map hashMapResult = new HashMap();

    public String getCollaboratorModel() throws Exception {
        log.info("Begin.");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
//            String telecomServiceIdTemp = (String) QueryCryptUtils.getParameter(req, "telecomServiceId");
//            BaseDAOAction baseDAOAction = new BaseDAOAction();

            List tmpList = new ArrayList();
            String[] header = {"", getText("MSG.SAE.023")};
            tmpList.add(header);

            String strTelecomServiceId = req.getParameter("telecomServiceId");
            if (strTelecomServiceId == null || strTelecomServiceId.trim().equals("")) {
                listModelCombo.put("stockModelId", tmpList);
                return "success";
            }

            Long telecomServiceId = Long.valueOf(strTelecomServiceId.trim());

            StockModelDAO modelDAO = new StockModelDAO();
            modelDAO.setSession(this.getSession());
            if (telecomServiceId != null) {
                modelDAO.setTelecomServiceIdFilter(telecomServiceId);
            }
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            modelDAO.setStaffSalerIdFilter(userToken.getUserID());
            List<StockModel> model = modelDAO.findStockModelForSaleCollaborater();
            if (model != null && model.size() > 0) {
                tmpList.addAll(model);
            }
            listModelCombo.put("stockModelId", tmpList);
        } catch (Exception e) {
            throw e;
        }
        return "success";
    }

    public String updateStockModelPrice() throws Exception {
        log.info("Begin.");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();

        try {
//            BaseDAOAction baseDAOAction = new BaseDAOAction();

            String[] header = {"", getText("MSG.SAE.024")};
            List tmpList = new ArrayList();

            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String collCode = req.getParameter("staffId");
            if (collCode == null || collCode.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "success";
            }

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff coll = staffDAO.findAvailableByStaffCode(collCode.trim());
            if (this.IS_STOCK_STAFF_OWNER) {
                if (coll == null || coll.getStaffOwnerId().compareTo(userToken.getUserID()) != 0) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);
                    return "success";
                }
            } else {
                if (coll == null) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);
                    return "success";
                }
            }


            String strStockModelId = req.getParameter("stockModelId");
//            String stockModelIdTemp = (String) QueryCryptUtils.getParameter(req, "stockModelId");

            if (strStockModelId == null || strStockModelId.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);
                return "success";
            }

            Long stockModelId = new Long(strStockModelId);

            /**
             * Get price list
             */
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_COLLABOR);

            String pricePolicy = coll.getPricePolicy();
            priceDAO.setPricePolicyFilter(pricePolicy);
            priceDAO.setStockModelIdFilter(stockModelId);
            List priceList = priceDAO.findPriceForSaleRetail();
            if (priceList == null || priceList.isEmpty()) {
                tmpList.add(header);
            } else {
                tmpList.addAll(priceList);
            }
            priceListMap.put("priceId", tmpList);

        } catch (Exception e) {
            throw e;
        }


        return "success";
    }

    public String updateListModelPrice() throws Exception {
        log.info("Begin method updateListModelPrice of SaleToCollaboratorDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        try {
//            BaseDAOAction baseDAOAction = new BaseDAOAction();
            String listBranhConfig = ResourceBundleUtils.getResource("LIST_BRANCH_HAVE_PRICE_POLICY_BRANCH");
            String[] strSplit = listBranhConfig.split(";");
            HashMap<String, String> listShopMap = new HashMap<String, String>();
            String listBranhDiscountConfig = ResourceBundleUtils.getResource("LIST_BRANCH_DISCOUNT");
            String[] strSplitBranch = listBranhDiscountConfig.split(";");
            HashMap<String, String> listShopBranchMap = new HashMap<String, String>();

            String param = "(";
            if (strSplit.length > 0) {
                for (int i = 0; i < strSplit.length; i++) {
                    if (i == (strSplit.length - 1)) {
                        param = param + "'" + strSplit[i] + "'";
                    } else {
                        param = param + "'" + strSplit[i] + "',";
                    }
                }
            } else {
                param = param + "'CABBR','NACBR'";
            }

            param = param + ")";

            String paramBranch = "(";
            if (strSplitBranch.length > 0) {
                for (int i = 0; i < strSplitBranch.length; i++) {
                    if (i == (strSplitBranch.length - 1)) {
                        paramBranch = paramBranch + "'" + strSplitBranch[i] + "'";
                    } else {
                        paramBranch = paramBranch + "'" + strSplitBranch[i] + "',";
                    }
                }
            } else {
                paramBranch = paramBranch + "'MACBR','TETBR'";
            }

            paramBranch = paramBranch + ")";
            List<Shop> listShop = listShopOfBranch(getSession(), param);
            for (int k = 0; k < listShop.size(); k++) {
                if (listShop.get(k).getShopCode() != null) {
                    listShopMap.put(listShop.get(k).getShopId().toString(), listShop.get(k).getShopCode());
                }
            }

            List<Shop> listShopBranch = listShopOfBranch(getSession(), paramBranch);
            for (int k1 = 0; k1 < listShopBranch.size(); k1++) {
                if (listShopBranch.get(k1).getShopCode() != null) {
                    listShopBranchMap.put(listShopBranch.get(k1).getShopId().toString(), listShopBranch.get(k1).getShopCode());
                }
            }

            String[] header = {"", getText("MSG.SAE.024")};
            List tmpList = new ArrayList();

            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String collCode = req.getParameter("staffId");
            Long typeOfSale = Long.valueOf(req.getParameter("typeOfSale"));

            if (collCode == null || collCode.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);

                log.info("End method updateListModelPrice of SaleToCollaboratorDAO");
                return UPDATE_LIST_MODEL_PRICE;
            }

            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff coll = staffDAO.findAvailableByStaffCode(collCode.trim());
            if (this.IS_STOCK_STAFF_OWNER) {//khong quan ly theo nvql
                if (coll == null || coll.getStaffOwnerId().compareTo(userToken.getUserID()) != 0) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);

                    log.info("End method updateListModelPrice of SaleToCollaboratorDAO");
                    return UPDATE_LIST_MODEL_PRICE;
                }
            } else {
                if (coll == null) {
                    tmpList.add(header);
                    priceListMap.put("priceId", tmpList);

                    log.info("End method updateListModelPrice of SaleToCollaboratorDAO");
                    return UPDATE_LIST_MODEL_PRICE;
                }
            }

            String stockModelCodeF9 = (String) req.getParameter("stockModelCode");
            if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("")) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);

                log.info("End method updateListModelPrice of SaleToCollaboratorDAO");
                return UPDATE_LIST_MODEL_PRICE;
            }

            StockModelDAO stockModelDAOF9 = new StockModelDAO();
            stockModelDAOF9.setSession(this.getSession());
            StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9.trim());
            if (stockModelF9 == null) {
                tmpList.add(header);
                priceListMap.put("priceId", tmpList);

                log.info("End method updateListModelPrice of SaleToCollaboratorDAO");
                return UPDATE_LIST_MODEL_PRICE;
            }

            //lay danh sach gia mat hang
            Long stockModelId = stockModelF9.getStockModelId();
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_COLLABOR);
            //QuocDM1 bo sung chon gia theo loai ban hang
            if (typeOfSale == 1L) {
                priceDAO.setCheckSalePackagePrice(true);
            } else if (typeOfSale == 2L) {
                priceDAO.setCheckSalePackagePrice(false);
            }

            String pricePolicy = coll.getPricePolicy();
            priceDAO.setPricePolicyFilter(pricePolicy);
            priceDAO.setStockModelIdFilter(stockModelId);
//            List priceList = priceDAO.findPriceForSaleRetail();
            List priceList = new ArrayList();
            Long TypeBranch = 0L;
            if (staff != null && listShopMap.containsKey(staff.getShopId().toString())) {
                TypeBranch = 1L;
            } else if (staff != null && listShopBranchMap.containsKey(staff.getShopId().toString())) {
                TypeBranch = 2L;
            }
            if (staff != null && staff.getShopId() != null
                    && (listShopMap.containsKey(staff.getShopId().toString())
                    || listShopBranchMap.containsKey(staff.getShopId().toString()))
                    && priceDAO.findManyPriceForSaleChannelForBranch(TypeBranch).size() > 0) {
                priceList = priceDAO.findManyPriceForSaleChannelForBranch(TypeBranch);
            } else {
                priceList = priceDAO.findManyPriceForSaleChannel();
            }

            if (priceList == null || priceList.isEmpty()) {
                tmpList.add(header);
            } else {
                tmpList.addAll(priceList);
            }

            priceListMap.put("priceId", tmpList);

        } catch (Exception e) {

            //
//            BaseDAOAction baseDAOAction = new BaseDAOAction();
            List tmpList = new ArrayList();
            String[] header = {"", getText("MSG.SAE.024")};
            tmpList.add(header);
            priceListMap.put("priceId", tmpList);
        }

        log.info("End method updateListModelPrice of SaleToCollaboratorDAO");
        return UPDATE_LIST_MODEL_PRICE;
    }

    public List findAll() {
        log.debug("finding all TelecomService instances");
        try {
            String queryString = "from TelecomService";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Price findPrice(java.lang.Long id) {
        log.debug("getting Price instance with id: " + id);
        try {
            Price instance = (Price) getSession().get("com.viettel.im.database.BO.Price", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public Map getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(Map hashMapResult) {
        this.hashMapResult = hashMapResult;
    }

    public Map getListModelCombo() {
        return listModelCombo;
    }

    public void setListModelCombo(Map listModelCombo) {
        this.listModelCombo = listModelCombo;
    }

    public Map getPriceListMap() {
        return priceListMap;
    }

    public void setPriceListMap(Map priceListMap) {
        this.priceListMap = priceListMap;
    }

    //Hien thi lai danh sach hang hoa khi thao tac bi loi
    private void recoverTempList(HttpSession session) {
        try {
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            //Danh sach hang hoa cua nhan vien quan ly CTV/DB
            StockModelDAO modelDAO = new StockModelDAO();
            modelDAO.setSession(this.getSession());
            modelDAO.setTelecomServiceIdFilter(saleToCollaboratorForm.getTelecomServiceId());
            modelDAO.setStaffSalerIdFilter(userToken.getUserID());
            List model = modelDAO.findStockModelForSaleCollaborater();
            List<StockModel> modelList = new ArrayList<StockModel>();
            for (int i = 0; i < model.size(); i++) {
                Object[] obj = (Object[]) model.get(i);
                StockModel stockModel = new StockModel();
                stockModel.setStockModelId(Long.valueOf(obj[0].toString()));
                stockModel.setName(obj[1].toString());
                modelList.add(stockModel);
            }
            session.setAttribute(LIST_MODEL, modelList);

            //Thong tin CTV/DB
            String collCode = saleToCollaboratorForm.getAgentCodeSearch();
            if (collCode == null || collCode.trim().equals("")) {
                return;
            }
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff coll = staffDAO.findAvailableByStaffCode(collCode.trim());

            if (this.IS_STOCK_STAFF_OWNER) {
                if (coll == null || coll.getStaffOwnerId().compareTo(userToken.getUserID()) != 0) {
                    return;
                }
            } else {
                if (coll == null) {
                    return;
                }
            }

            //Chinh sach gia cua CTV/DB
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_COLLABOR);
            priceDAO.setPricePolicyFilter(coll.getPricePolicy());
            priceDAO.setStockModelIdFilter(saleToCollaboratorForm.getStockModelId());
//            List price = priceDAO.findPriceForSaleRetail();
            List price = priceDAO.findManyPriceForSaleChannel();
            List<Price> priceList = new ArrayList<Price>();
            for (int i = 0; i < price.size(); i++) {
                Object[] obj = (Object[]) price.get(i);
                Price p = new Price();
                p.setPriceId(Long.valueOf(obj[0].toString()));
                p.setPriceName((obj[1].toString()));
                priceList.add(p);
            }
            session.setAttribute(LIST_PRICE, priceList);
        } catch (Exception ex) {
        }
    }

    /**
     * Created by: TrongLV Purpose: Kiem tra chuoi kieu so
     *
     * @param sNumber
     * @return
     */
    public boolean chkNumber(String sNumber) {
        if (sNumber == null) {
            return false;
        }
        int i = 0;
        for (i = 0; i < sNumber.length(); i++) {
            // Check that current character is number.
            if (!Character.isDigit(sNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param type type = 1 : register type = 2 : update
     * @param userToken
     * @param staff
     * @param isdn
     * @param cmPreSession
     * @param req
     * @return
     */
    public boolean regCust(int type, UserToken userToken, Staff staff, String isdn, Session cmPreSession, HttpServletRequest req, String P_REQUEST_MESSAGE) {

        Customer cust = new Customer();
        cust.setTelFax(staff.getTel());
        cust.setName(staff.getName());
//        cust.setIdType(staff.getIdType());
        cust.setIdNo(staff.getIdNo());
        cust.setIdIssueDate(staff.getIdIssueDate());
//        cust.setIdExpireDate(staff.getIdExpireDate());
        cust.setIdIssuePlace(staff.getIdIssuePlace());
        cust.setBirthDate(staff.getBirthday());
        cust.setProvince(staff.getProvince());
        cust.setDistrict(staff.getDistrict());
        cust.setPrecinct(staff.getPrecinct());
        cust.setStreetBlockName(staff.getStreetBlockName());
        cust.setStreetName(staff.getStreetName());
        cust.setHome(staff.getHome());
        cust.setAddress(staff.getAddress());

        int result = -1;
        if (type == 1) {
            result = InterfaceCMToIM.registerSubscriber(isdn, cust, userToken.getLoginName(), userToken.getShopCode(), cmPreSession);;
            System.out.println("InterfaceCMToIM.registerSubscriber : " + result);
        } else if (type == 2) {
            result = InterfaceCMToIM.updateCustomerInformation(isdn, cust, userToken.getLoginName(), userToken.getShopCode(), cmPreSession);
            System.out.println("InterfaceCMToIM.updateCustomerInformation : " + result);
        }
        if (result == 0) {
            return true;
        }
        if (P_REQUEST_MESSAGE == null || P_REQUEST_MESSAGE.trim().equals("")) {
            P_REQUEST_MESSAGE = REQUEST_MESSAGE;
        }

        if (result == -1) {
//            req.setAttribute(REQUEST_MESSAGE, "-1: định dạng Id_no không đúng");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200030");
        } else if (result == -2) {
//            req.setAttribute(REQUEST_MESSAGE, "-2: hết hạn mức thuê bao");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200031");
        } else if (result == -3) {
//            req.setAttribute(REQUEST_MESSAGE, "-3: Exception");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200037");
        } else if (result == -4) {
//            req.setAttribute(REQUEST_MESSAGE, "-4: shop, staff không đúng");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200032");
        } else if (result == -5) {
//            req.setAttribute(REQUEST_MESSAGE, "-5: isdn truyền vào không đúng");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200033");
        } else if (result == -6) {
//            req.setAttribute(REQUEST_MESSAGE, "-6: số thuê bao không tồn tại trên hệ thống");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200034");
        } else if (result == -7) {
//            req.setAttribute(REQUEST_MESSAGE, "-7: thuê bao đã được đăng kí thông tin");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200035");
        } else if (result == -8) {
//            req.setAttribute(REQUEST_MESSAGE, "-8: Customer truyền vào không đúng (customer = null)");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200036");
        } else if (result == -11) {//ngay sinh khong khop
            req.setAttribute(P_REQUEST_MESSAGE, "E.200039");
        } else if (result == -12) {//ngay cap cmnd khong khop
            req.setAttribute(P_REQUEST_MESSAGE, "E.200040");
        } else {
//            req.setAttribute(REQUEST_MESSAGE, "error");
            req.setAttribute(P_REQUEST_MESSAGE, "E.200037");
        }

        return false;
    }

    /**
     *
     */
    public boolean checkKITCTV(String stockModelCode) {
//        if (Constant.STOCK_MODEL_CODE_KITCTV.indexOf(stockModelCode) >= 0) {
//            return true;
//        }
        String tmpKitCodeList = Constant.STOCK_MODEL_CODE_KITCTV;
        while (tmpKitCodeList.indexOf("(") >= 0) {
            tmpKitCodeList = tmpKitCodeList.replace("(", "");
        }
        while (tmpKitCodeList.indexOf(")") >= 0) {
            tmpKitCodeList = tmpKitCodeList.replace(")", "");
        }
        String[] kitCodeList = tmpKitCodeList.split(",");
        for (int i = 0; i < kitCodeList.length; i++) {
            String kitCode = kitCodeList[i].trim();
            if (stockModelCode.equals(kitCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * QuocDM1 28/05/2015: Lay ra danh sach mat hang - co phu thuoc vao viec lua
     * chon Type of sale.
     *
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        String typeOfsale = null;
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            typeOfsale = imSearchBean.getOtherParamValue();
        }

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");

        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? ) ");
        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);

        if (typeOfsale.equals(SALE_SINGLE)) {
            strQuery1.append("AND NOT EXISTS (SELECT 'x' FROM PackageGoodsDetail pgd WHERE pgd.stockModelId = a.stockModelId AND pgd.requiredCheck = 1 AND pgd.status = 1) ");
        } else if (typeOfsale.equals(SALE_GROUP)) {
            strQuery1.append("AND EXISTS (SELECT 'x' FROM PackageGoodsDetail pgd WHERE pgd.stockModelId = a.stockModelId AND pgd.status = 1) ");
        }


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }


        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     * QuocDM1 28/05/2015: Lay ra danh sach mat hang - co phu thuoc vao viec lua
     * chon Type of sale.
     *
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) {
        String typeOfsale = null;
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            typeOfsale = imSearchBean.getOtherParamValue();
        }

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");

        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? ) ");
        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);

        if (typeOfsale.equals(SALE_SINGLE)) {
            strQuery1.append("AND NOT EXISTS (SELECT 'x' FROM PackageGoodsDetail pgd WHERE pgd.stockModelId = a.stockModelId AND pgd.requiredCheck = 1 AND pgd.status = 1) ");
        } else if (typeOfsale.equals(SALE_GROUP)) {
            strQuery1.append("AND EXISTS (SELECT 'x' FROM PackageGoodsDetail pgd WHERE pgd.stockModelId = a.stockModelId AND pgd.status = 1) ");
        }


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }


        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     * QuocDM1 28/05/2015: Lay ra danh sach mat hang - co phu thuoc vao viec lua
     * chon Type of sale.
     *
     * @param imSearchBean
     * @return
     */
    public Long getListStockModelSize(ImSearchBean imSearchBean) {
        String typeOfsale = null;
        req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            typeOfsale = imSearchBean.getOtherParamValue();
        }

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");

        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? ) ");
        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);

        if (typeOfsale.equals(SALE_SINGLE)) {
            strQuery1.append("AND NOT EXISTS (SELECT 'x' FROM PackageGoodsDetail pgd WHERE pgd.stockModelId = a.stockModelId AND pgd.requiredCheck = 1 AND pgd.status = 1) ");
        } else if (typeOfsale.equals(SALE_GROUP)) {
            strQuery1.append("AND EXISTS (SELECT 'x' FROM PackageGoodsDetail pgd WHERE pgd.stockModelId = a.stockModelId AND pgd.status = 1) ");
        }


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add("%" + imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }


        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return Long.valueOf(listImSearchBean.size());
    }

    private String validateGoodsWhenSale(List<SaleToCollaboratorForm> lstGoods) {

        return "";
    }

    public List<Shop> listShopOfBranch(Session session, String param) throws Exception {

        List<Shop> listShop = new ArrayList<Shop>();
        String strQuery = " SELECT Shop_Code AS shopCode, Shop_Id AS shopId FROM shop WHERE shop_Id IN (SELECT  sh.shop_id FROM shop sh "
                + " WHERE sh.status = 1 "
                + " START WITH sh.shop_code IN " + param + " CONNECT BY PRIOR sh.shop_id = sh.parent_shop_id )";

        Query query = session.createSQLQuery(strQuery)
                .addScalar("shopCode", Hibernate.STRING)
                .addScalar("shopId", Hibernate.LONG)
                .setResultTransformer(Transformers.aliasToBean(Shop.class));

        listShop = query.list();

        return listShop;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham upload file
     *
     */
    public static String uploadListFileFromFTPServer(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory("ACCOUNT_TRANS");
//                client.changeWorkingDirectory(destinationDir);
                //new forder By DateTime


                // create temp file on temp directory
                for (int i = 0; i < fileName.size(); i++) {
//                    String lStr = fileName.get(i).substring(fileName.get(i).lastIndexOf("\\"));
                    String lStr = fileName.get(i).substring(fileName.get(i).lastIndexOf("/"));
                    lStr = lStr.substring(1);
                    InputStream inputStream = new FileInputStream(new File(fileName.get(i)));
                    client.storeFile(lStr, inputStream);
                }
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return fileNameDir;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham lay file tu tren server ve local may
     *
     */
    public static String retrieveListFileFromFTPServer(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory("ACCOUNT_TRANS");
//                client.changeWorkingDirectory(destinationDir);
                // create temp file on temp directory
                for (int i = 0; i < fileName.size(); i++) {
                    // retrieve file from server and logout
                    String[] filesrc = fileName.get(i).split("\\/", 5);
                    ///u01/scan_doc/CONTRACT_CHANNEL/20171122104349_tony luis.rar.zip
                    File tempFile = new File(tempDir + filesrc[3] + "/" + filesrc[4]);
                    boolean success = (new File(tempFile.getParent())).mkdirs();
                    if (!success) {
                        File file = new File(tempDir + filesrc[3] + "/" + filesrc[4]);
                        file.createNewFile();
                    }
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));

                    if (client.retrieveFile(filesrc[4], outputStream)) {
                        outputStream.close();
                    }
                }
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return fileNameDir;
    }

    /**
     * author : Tannh date : 11/01/2018 purpose : cap nhat giao dich ban hang
     * cho cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTransDetailOrder expSaleTransDetailOrder(UserToken userToken, Staff staff, SaleTransOrder saleTrans, SaleToCollaboratorForm saleTransForm, Map<Long, Double> mapStockModelDiscount) {
        try {
            /**
             * DUCTM_20110215_START log.
             */
            String function = "com.viettel.im.database.DAO.SaleToCollaboratorDAO.expSaleTransDetailOrder";
            Long callId = getApCallId();
            Date startTime = new Date();
            logStartCallBegin(startTime, function, callId);

            SaleTransDetailOrder saleTransDetail = new SaleTransDetailOrder();

            //tamdt1, start, 15/07/2010
            //cap nhat them cac truong can thiet phuc vu bao cao doanh thu
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(saleTransForm.getStockModelId());
            if (stockModel != null) {
                saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
                saleTransDetail.setStockModelName(stockModel.getName());

                saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
                saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());

            } else {
                //khong tim thay gia
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }

            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
            if (stockType != null) {
                saleTransDetail.setStockTypeId(stockType.getStockTypeId());
                saleTransDetail.setStockTypeName(stockType.getName());
            } else {
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }
            //tamdt1, end

            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());
            Price price = priceDAO.findById(saleTransForm.getPriceId());
            if (price == null || price.getPriceId() == null) {
                logEndCall(startTime, new Date(), function, callId);
                return null;
            }

            saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_ORDER_SEQ"));
            //LinhNBV change saleTransId --> saleTransOrderId
            saleTransDetail.setSaleTransOrderId(saleTrans.getSaleTransOrderId());
            saleTransDetail.setSaleTransId(0L);
            //LinhNBV end.
            saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

            saleTransDetail.setStockModelId(saleTransForm.getStockModelId());

            saleTransDetail.setPriceId(price.getPriceId());

            saleTransDetail.setCurrency(price.getCurrency());
            saleTransDetail.setPriceVat(price.getVat());//thue suat
            saleTransDetail.setPrice(price.getPrice());
            saleTransDetail.setAmount(price.getPrice() * saleTransForm.getQuantity());
            saleTransDetail.setQuantity(saleTransForm.getQuantity());//so luong
            saleTransDetail.setDiscountAmount(0.0);//chiet khau
            saleTransDetail.setDiscountId(saleTransForm.getDiscountId());

            Double amountAfterTax = 0.0;
            Double amountTax = 0.0;
            Double amountBeforeTax = 0.0;

            if (!Constant.PRICE_AFTER_VAT) {
                amountBeforeTax = price.getPrice() * saleTransForm.getQuantity();
                amountTax = amountBeforeTax * price.getVat() / 100.0;
                amountAfterTax = amountBeforeTax + amountTax;
            } else {
                amountAfterTax = price.getPrice() * saleTransForm.getQuantity();
                amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
                amountTax = amountAfterTax - amountBeforeTax;
            }

            saleTransDetail.setAmountBeforeTax(amountBeforeTax);
            saleTransDetail.setAmountTax(amountTax);
            saleTransDetail.setVatAmount(amountTax);
            saleTransDetail.setAmountAfterTax(amountAfterTax);

            if (mapStockModelDiscount != null && !mapStockModelDiscount.isEmpty()) {
                Double discountAmount = mapStockModelDiscount.get(saleTransForm.getStockModelId());
                if (discountAmount != null) {//chiet khau
                    saleTransDetail.setDiscountAmount(discountAmount);
                }
            }

            saleTransDetail.setStateId(Constant.STATE_NEW);
            saleTransDetail.setNote(saleTransForm.getNote());
            this.getSession().save(saleTransDetail);

            logEndCall(startTime, new Date(), function, callId);
            return saleTransDetail;
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

            return null;
        }

    }

    /**
     * @Author: Tannh
     * @Date created: 11/01/2018
     * @Purpose: save sale trans serial order
     */
    private boolean saveSaleTransSerialOrder(UserToken userToken, Staff staff, SaleToCollaboratorForm saleTransDetailBean, SaleTransDetailOrder saleTransDetail)
            throws Exception {

        Long stockModelId = 0L;
        List lstSerial = null;
        boolean noError = true;

        stockModelId = saleTransDetailBean.getStockModelId();

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return false;
        }
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        lstSerial = saleTransDetailBean.getLstSerial();
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
                BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
                Long totals = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                Long saleTransDetailId = getSequence("SALE_TRANS_SERIAL_ORDER_SEQ");
                SaleTransSerialOrder saleTransSerial = new SaleTransSerialOrder();
                saleTransSerial.setSaleTransSerialId(saleTransDetailId);
                saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
                saleTransSerial.setStockModelId(stockModelId);
                saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
                saleTransSerial.setFromSerial(stockSerial.getFromSerial());
                saleTransSerial.setToSerial(stockSerial.getToSerial());
                saleTransSerial.setQuantity(totals);
                this.getSession().save(saleTransSerial);

            }
        }
        return noError;
    }

    /**
     *
     * author : tannh date :11/01/2018 purpose : cap nhat giao dich ban hang cho
     * cong tac vien modified : tamdt1, 28/10/2009
     *
     */
    private SaleTransOrder expSaleTransOrder(UserToken userToken, Staff staff, String pathFileScan, String orderCode) {
        try {
            Long saleTransId = getSequence("SALE_TRANS_ORDER_SEQ");
            SaleTransOrder saleTrans = new SaleTransOrder();
            //LinhNBV change saleTransId --> saleTransOrderId
            saleTrans.setSaleTransOrderId(saleTransId);
            saleTrans.setSaleTransId(0L);
            //end.
            saleTrans.setSaleTransDate(getSysdate());

            saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_COLLABORATOR); //loai giao dich: ban cho CTV/ diem ban
            saleTrans.setStatus(String.valueOf(Constant.SALE_PAY_NOT_BILL)); //da thanh toan nhung chua lap hoa don
            saleTrans.setShopId(userToken.getShopId());
            saleTrans.setStaffId(userToken.getUserID());

            saleTrans.setPayMethod(this.saleToCollaboratorForm.getPayMethodId()); //mac dinh giao dich do CM day sang la tien mat
            saleTrans.setDiscount(this.saleToCollaboratorForm.getDiscout()); //tien chiet khau
            saleTrans.setAmountTax(this.saleToCollaboratorForm.getAmountTax()); //tong tien phai tra cua KH, = chua thue + thue - KM - C/Khau
            saleTrans.setAmountNotTax(this.saleToCollaboratorForm.getAmountNotTax()); //tien chua thue

            if (this.saleToCollaboratorForm.getSaleTransVat() != null && !this.saleToCollaboratorForm.getSaleTransVat().trim().equals("")) {
                saleTrans.setVat(Double.valueOf(this.saleToCollaboratorForm.getSaleTransVat().replaceAll(",", ""))); //tien vat
            }

            saleTrans.setTax(this.saleToCollaboratorForm.getTax());
            saleTrans.setCurrency(this.saleToCollaboratorForm.getCurrency());

            saleTrans.setCustName(staff.getStaffCode() + " - " + staff.getName()); //doi voi ban hang cho CTV, luu thong tin truong nay la ten CTV
            saleTrans.setReceiverId(staff.getStaffId());
            saleTrans.setReceiverType(Constant.OWNER_TYPE_STAFF);

            saleTrans.setAddress(saleTrans.getAddress()); //CM day sang
            saleTrans.setReasonId(this.saleToCollaboratorForm.getReasonId());
            saleTrans.setTelecomServiceId(this.saleToCollaboratorForm.getTelecomServiceId());
            saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTransId));
            saleTrans.setCreateStaffId(userToken.getUserID());


            saleTrans.setOrderCode(orderCode);
            saleTrans.setScanPath(pathFileScan);
            saleTrans.setIsCheck(0L);

            this.getSession().save(saleTrans);
            return saleTrans;
        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return null;
        }

    }

    public int[] insertQueueOutput(List<String> listRecords) {

        /*
         * insert into MT(MT_ID,MO_HIS_ID,MSISDN,MESSAGE,RECEIVE_TIME,RETRY_NUM,CHANNEL) "
         + "values(MT_SEQ.NEXTVAL, ?, ?, ?, sysdate, 0, ?)
         */
        String batchId = "";
        int[] res = new int[0];

        try {
            for (String isdn : listRecords) {
                if (isdn == null || isdn.trim().length() <= 0) {
                    continue;
                }
                StringBuilder sqlInsertMT = new StringBuilder();
                sqlInsertMT.append("INSERT INTO MT (MT_ID, MO_HIS_ID, MSISDN, MESSAGE, RETRY_NUM, CHANNEL, RECEIVE_TIME)");
                sqlInsertMT.append(" VALUES (?, ?, ?, ?, ?, ?, ?)");
                Query queryInsertMT = getSession().createSQLQuery(sqlInsertMT.toString());
                Long mtId = getSequence("MT_SEQ");
                queryInsertMT.setParameter(0, mtId);
                queryInsertMT.setParameter(1, mtId);
                queryInsertMT.setParameter(2, "258" + isdn);
                queryInsertMT.setParameter(3, "message test");
                queryInsertMT.setParameter(4, 0);
                queryInsertMT.setParameter(5, "86904");
                queryInsertMT.setParameter(6, getSysdate());
                queryInsertMT.executeUpdate();
            }
            return res;
        } catch (Exception ex) {
            log.error("ERROR insertQueueOutput batchid " + batchId, ex);
            try {
                return res;
            } catch (Exception ex1) {
                log.error("ERROR retry insertQueueOutput MT, batchid " + batchId, ex1);
                log.error(ex1);
                return null;
            }
        }
    }

    public List<String> getListNumberFinancialDepartment(String shopCode) throws Exception {
        log.debug("# Begin method getListNumberFinancialDepartment");
        HttpServletRequest req = getRequest();
        List<String> list = new ArrayList();
        // get shopCode
        shopCode = shopCode.substring(0, Math.min(shopCode.length(), 3)) + "BR";

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select u.cellPhone from vsa_v3.users u where u.status = 1 "
                    + " and u.user_id in (    select user_id from vsa_v3.role_user where is_active = 1 and "
                    + " role_id in (select role_id from vsa_v3.roles where status = 1 and role_code in "
                    + " ('BR_FINANCE_APPROVE')))and u.user_name in (  "
                    + "  select lower(staff_code) from staff where status = 1 and shop_id in (select shop_id "
                    + " from shop where status = 1 and shop_code = ?))");

            Query query = getSession().createSQLQuery(sql.toString()).addScalar("cellPhone", Hibernate.STRING);
            query.setParameter(0, shopCode);
            list = query.list();

        } catch (Exception ex) {
            log.error(" Begin method getListNumberFinancialDepartment ; Exception : " + ex);
        }
        return list;
    }

    //Ham xuat ra excel
    public String expOrderToExcel(List<SaleToCollaboratorForm> lstGoods, SaleToCollaboratorForm saleToCollaboratorForm) throws Exception {
        log.debug("# Begin method expOrderToExcel");
        HttpServletRequest req = this.getRequest();
        String pathFilePDF = null;
//        form.setExportUrl("");
//        saleToCollaboratorForm.setex
//        String pageForward = searchInvoice();
        if (lstGoods != null) {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            SimpleDateFormat hoursFormat = new SimpleDateFormat("yyyyMMddhh24mmss");
            String dateTime = hoursFormat.format(new Date());

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "list_order.xls";
            String fileName = "list_order_" + userToken.getLoginName().toLowerCase() + "_" + dateTime;
            String fileType = ".xls";
            String fullFileName = fileName + fileType;
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + fullFileName;

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
//             with server
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            Map beans = new HashMap();
            beans.put("list", lstGoods);
            beans.put("userToken", userToken);
            Date date = new Date();
            beans.put("sysDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            double sum = 0;
            NumberFormat format = NumberFormat.getCurrencyInstance();
            Number number;
            for (int i = 0; i < lstGoods.size(); i++) {
                number = format.parse("$" + lstGoods.get(i).getAmountMoney());
                sum += lstGoods.get(i).getDiscout() + Double.parseDouble(number.toString().replaceAll(",", ""));
            }
            beans.put("sum", sum);
            beans.put("saleToCollaboratorForm", saleToCollaboratorForm);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
//            form.setExportUrl(filePath);

//            with file excel
            pathFilePDF = realPath;

//             String srcFile = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE")+ fileName +".pdf";

            // with server file PDF
//           
//            String pathFilePDFReal = req.getSession().getServletContext().getRealPath(srcFile);
//            
//            
//            pathFilePDF = excel2pdf(realPath , pathFilePDFReal);



        }

        log.debug("# End method expOrderToExcel");
        return pathFilePDF;
    }

    //tannh20180120 Ham convert excel to pdf
//      public String excel2pdf(String realPath,String pathFilePDFReal)    {
//        try {
//            
//            
//        Workbook workbook = new Workbook(realPath);
//       
//        
////        pathFilePDF = srcFile;
//        //with server
//       
//        //Save the document in PDF format
//        workbook.save(pathFilePDFReal, SaveFormat.PDF);
//        
////        pdfWatermark(pathFilePDF, fileName);
//        
//        } catch (Exception ex) {
//            logdetail.info("Exception excel2pdf " + ex);
//        }
//        return pathFilePDFReal;
//    }
    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham luu file lay tu tren server ve local
     *
     */
    public String myDownload() throws Exception {
        HttpServletRequest req = getRequest();
//        getFileLocationToServer(fileDowd);
        if (fileDowd == null || fileDowd.trim().length() <= 0) {
            logdetail.info("myDownload is fail.");
            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "Not_Create_Order.pdf";
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            fileInputStream = new FileInputStream(new File(templateRealPath));
            return SUCCESS;
        } else {
            fileInputStream = new FileInputStream(new File(fileDowd));
            return SUCCESS;
        }
    }
//    private boolean isStaffMaputo(String staffCode) {
//        if (staffCode == null) {
//            return false;
//        }
//
//        String strQuery = "select * from sm.staff where shop_id in (select shop_id from shop where shop_path like '%1000911%') and status =1 and staff_code = ? ";
//        Query query = getSession().createSQLQuery(strQuery);
//        query.setParameter(0, staffCode.toUpperCase());
//        List<Staff> listStaff = query.list();
//        if (listStaff != null && listStaff.size() > 0) {
//            return true;
//        }
//        return false;
//    }
}
