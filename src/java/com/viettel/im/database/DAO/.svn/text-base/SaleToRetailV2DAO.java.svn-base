package com.viettel.im.database.DAO;

import com.google.gson.Gson;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.bean.CustInforBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.form.SaleTransDetailForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.ResponseWallet;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleTransDetailOrder;
import com.viettel.im.database.BO.SaleTransEmola;
import com.viettel.im.database.BO.SaleTransOrder;
import com.viettel.im.database.BO.SaleTransSerialOrder;
import com.viettel.im.database.BO.SaleTransVip;
import com.viettel.im.database.BO.SaleTransVipOtp;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.DAO.StockTotalAuditDAO.SourceType;
import com.viettel.im.sms.SmsClient;
import com.viettel.security.util.DbProcessor;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import sun.misc.BASE64Encoder;
//them Log log4j
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author MrSun
 */
public class SaleToRetailV2DAO extends BaseDAOAction {

    private SaleTransForm saleTransForm;
    private static final Long MAX_SEARCH_RESULT = 100L;
    private static final String SALE_TO_RETAIL = "saleToRetail";
    private static final String SALE_TO_RETAIL_DETAIL = "saleToRetailDetail";
    private static final String GET_CUST_OTP = "getCustOTP";
    private static final String UPDATE_LIST_MODEL_PRICE = "updateListModelPrice";//update combobox listprice of stockmodel
    private static final String SESSION_LIST_GOODS = "lstGoods";//listGoods save in tabsession
    private static final String REQUEST_LIST_PAY_METHOD = "listPayMethod";
    private static final String REQUEST_LIST_PAY_REASON = "listReason";
    private final String REQUEST_LIST_STOCK_MODEL_PRICE = "listPrice";
    private Map listElement = new HashMap();
    private static final Logger logdetail = Logger.getLogger(SaleToRetailV2DAO.class);
    //tannh20180111 upload file va download file
    private InputStream fileInputStream;
    public static String fileDowd;

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public static String getFileDowd() {
        return fileDowd;
    }

    public static void setFileDowd(String fileDowd) {
        SaleToRetailV2DAO.fileDowd = fileDowd;
    }

    public SaleToRetailV2DAO() {
        String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");
        PropertyConfigurator.configure(log4JPath);
    }

    public Map getListElement() {
        return listElement;
    }

    public void setListElement(Map listElement) {
        this.listElement = listElement;
    }

    public SaleTransForm getSaleTransForm() {
        return saleTransForm;
    }

    public void setSaleTransForm(SaleTransForm saleTransForm) {
        this.saleTransForm = saleTransForm;
    }

    public String preparePage() throws Exception {
        String pageForward = Constant.ERROR_PAGE;
        //LinhNBV start modified on May 24 2018: Check over limit.
        HttpServletRequest request = getRequest();
        UserToken userToken = (UserToken) request.getSession().getAttribute(Constant.USER_TOKEN);
        DbProcessor db = new DbProcessor();
//        if (isStaffMaputo(userToken.getLoginName())) {
        boolean isOverLimit = db.checkOverLimitBeforeSale(userToken.getLoginName());
        Staff s = getStaffByStaffCode(userToken.getLoginName().toUpperCase());
        //true meaning limit valid.
        if (isOverLimit) {
            request.getSession().setAttribute("isOverLimit", 1);
        } else {
            //false limit invalid.
            request.getSession().setAttribute("isOverLimit", 0);
        }
        if (s == null || s.getLimitApproveUser() == null || "".equalsIgnoreCase(s.getLimitApproveUser())) {
            //false limit invalid.
            request.getSession().setAttribute("isOverLimit", 0);
        }
//        } else {
//            request.getSession().setAttribute("isOverLimit", 1);
//        }
        //end.
        saleTransForm = new SaleTransForm();
        saleTransForm.setSaleTransDate(getSysdate());
        getDataForCombobox();

        setTabSession(SESSION_LIST_GOODS, new ArrayList());

        saleTransForm.setReasonIdVipCustomer(getReasonIdVipCustomer());
        pageForward = SALE_TO_RETAIL;
        //LinhNBV start modified on May 22 2018: Show download order when create order success.
        if (saleTransForm.getPayMethod() != null && saleTransForm.getPayMethod().equals("2")) {
            request.getSession().setAttribute("downloadOrder", 0);
        }
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

    private Staff getStaffByStaffCode(String staffCode) {
        Staff staff = null;
        if (staffCode == null) {
            return staff;
        }

        String strQuery = "from Staff where staffCode = ?  and status =1";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, staffCode);
        List<Staff> listStaff = query.list();
        if (listStaff != null && listStaff.size() > 0) {
            staff = listStaff.get(0);
        }
        return staff;
    }

    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        List payMethodList = appParamsDAO.findAppParamsList(Constant.PARAM_TYPE_PAY_METHOD, Constant.STATUS_USE.toString());
        req.setAttribute(REQUEST_LIST_PAY_METHOD, payMethodList);

        ReasonDAO reasonDAO = new ReasonDAO();
        reasonDAO.setSession(getSession());
        Long[] saleTransType = new Long[1];
        saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL);
        List<Reason> reasonList = reasonDAO.getReasonBySaleTransType(saleTransType);
        req.setAttribute(REQUEST_LIST_PAY_REASON, reasonList);
        /* khong can mac dinh ly do
         if (saleTransForm.getReasonId() == null) {
         saleTransForm.setReasonId(reasonList.get(0).getReasonId());
         } else {
         saleTransForm.setReasonId(saleTransForm.getReasonId());
         }
         */
    }

    public String updateListModelPrice() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        try {
            /*
             String listBranhConfig = ResourceBundleUtils.getResource("LIST_BRANCH_HAVE_PRICE_POLICY_BRANCH");
             String[] strSplit = listBranhConfig.split(";");
             String listBranhDiscountConfig = ResourceBundleUtils.getResource("LIST_BRANCH_DISCOUNT");
             String[] strSplitBranch = listBranhDiscountConfig.split(";");
             HashMap<String, String> listShopMap = new HashMap<String, String>();
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
             */
            String stockModelCodeF9 = (String) req.getParameter("stockModelCode");
            if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("")) {
                List tmpList = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                tmpList.add(header);
                listElement.put("priceId", tmpList);
                return UPDATE_LIST_MODEL_PRICE;
            }

            StockModelDAO stockModelDAOF9 = new StockModelDAO();
            stockModelDAOF9.setSession(this.getSession());
            StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9.trim());
            if (stockModelF9 == null || stockModelF9.getStockTypeId() == null) {
                List listItems = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                listItems.add(header);
                listElement.put("priceId", listItems);
                return UPDATE_LIST_MODEL_PRICE;
            }

            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            priceDAO.setPriceManyFilter(Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shop = shopDAO.findById(userToken.getShopId());
            //Neu san pham ban theo goi khong cho phep ban le
            //Khong cho phep ban hang o chuc nang ban le
            boolean notAllowSaleRetail = checkProductSaleToPackage(stockModelF9.getStockModelId(), false);
            if (notAllowSaleRetail) {
                List listItems = new ArrayList();
                String[] header = {"", getText("MSG.SAE.024")};
                listItems.add(header);
                listElement.put("priceId", listItems);
                return UPDATE_LIST_MODEL_PRICE;
            }

            priceDAO.setPricePolicyFilter(shop.getPricePolicy());
            priceDAO.setStockModelIdFilter(stockModelF9.getStockModelId());
            //Edit 18/01/2016
            List priceList = new ArrayList();
            /*
             Long TypeBranch = 0L;
             if (staff != null && listShopMap.containsKey(staff.getShopId().toString())) {
             TypeBranch = 1L;
             } else if (staff != null && listShopBranchMap.containsKey(staff.getShopId().toString())) {
             TypeBranch = 2L;
             }
             if (staff != null && staff.getShopId() != null 
             && (listShopMap.containsKey(staff.getShopId().toString())
             || listShopBranchMap.containsKey(staff.getShopId().toString()))
             && priceDAO.findPriceForBranchSaleRetail(TypeBranch).size() > 0) {
             priceList = priceDAO.findPriceForBranchSaleRetail(TypeBranch);
             } else if (staff != null && checkUserOfSH(getSession(), staff.getStaffId()) && priceDAO.findPriceOnlyShowroomSaleRetail().size() > 0) {
             priceList = priceDAO.findPriceOnlyShowroomSaleRetail();
             } else {
             priceList = priceDAO.findManyPriceForSaleRetail();
             }
             */
            priceList = priceDAO.findManyPriceForSaleRetail();
            List listItems = new ArrayList();
            if (priceList != null && priceList.size() > 0) {
                listItems.addAll(priceList);
            } else {
                String[] header = {"", getText("MSG.SAE.024")};
                listItems.add(header);
            }

            listElement.put("priceId", listItems);

        } catch (Exception e) {
            e.printStackTrace();
            List listItems = new ArrayList();
            String[] header = {"", getText("MSG.SAE.024")};
            listItems.add(header);
            listElement.put("priceId", listItems);
        }

        return UPDATE_LIST_MODEL_PRICE;
    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? and b.id.stateId = ? ) ");

        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    //R8119
    /**
     * getListStockModelRetail
     *
     * @param imSearchBean
     * @return
     */
    public List<ImSearchBean> getListStockModelRetail(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select a.stock_model_code code, a.name ");
        strQuery1.append("from stock_model a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from stock_total b where b.stock_model_id = a.stock_model_id and b.quantity_issue>0 and b.owner_id = ? and b.owner_type = ? and b.state_id = ? ) ");
        strQuery1.append("and stock_model_id not in ( select  pgd.stock_model_id ");
        strQuery1.append("from package_goods pg, package_goods_map pgm, package_goods_detail pgd ");
        strQuery1.append("where pg.status = 1 and pg.package_goods_id = pgm.package_goods_id ");
        strQuery1.append("and pgm.package_goods_map_id = pgd.package_goods_map_id ");
        strQuery1.append("and pgd.required_check <> 0 )");

        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stock_model_code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stock_model_code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createSQLQuery(strQuery1.toString())
                .addScalar("code", Hibernate.STRING)
                .addScalar("name", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(ImSearchBean.class));
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    public Long getListStockModelRetailSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long result = 0L;
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from stock_model a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from stock_total b where b.stock_model_id = a.stock_model_id and b.quantity_issue>0 and b.owner_id = ? and b.owner_type = ? and b.state_id = ? ) ");
        strQuery1.append("and stock_model_id not in ( select  pgd.stock_model_id ");
        strQuery1.append("from package_goods pg, package_goods_map pgm, package_goods_detail pgd ");
        strQuery1.append("where pg.status = 1 and pg.package_goods_id = pgm.package_goods_id ");
        strQuery1.append("and pgm.package_goods_map_id = pgd.package_goods_map_id ");
        strQuery1.append("and pgd.required_check <> 0 )");

        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stock_model_code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createSQLQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            if (tmpList1.get(0) != null) {
                result = Long.valueOf(tmpList1.get(0).toString());
            }
        }

        return result;
    }
    //End R8119

    /**
     *
     * author : tamdt1 date : 21/01/2009 purpose : lay danh sach mat hang
     *
     */
    public Long getListStockModelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long result = 0L;
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? and b.id.stateId = ? ) ");
        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            if (tmpList1.get(0) != null) {
                result = tmpList1.get(0);
            }
        }

        return result;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay ten mat hang
     *
     */
    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua mat hang dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and exists (select 'x' from StockTotal b where b.id.stockModelId = a.stockModelId and b.quantityIssue>0 and b.id.ownerId = ? and b.id.ownerType = ? and b.id.stateId = ? ) ");
        listParameter1.add(userToken.getUserID());
        listParameter1.add(Constant.OWNER_TYPE_STAFF);
        listParameter1.add(Constant.STATE_NEW);

        strQuery1.append("and lower(a.stockModelCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(MAX_SEARCH_RESULT);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;
    }

    private List<Price> getListPrice(Long stockModelId) throws Exception {
        //Danh cho ban le
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        /*
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
         */
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceManyFilter(Constant.SELECT_PRICE_TYPE_SHOP_RETAIL);
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());
        priceDAO.setPricePolicyFilter(shop.getPricePolicy());
        priceDAO.setStockModelIdFilter(stockModelId);
        //Edit 18/01/2016
        List listPrice = new ArrayList();
        /*
         Long TypeBranch = 0L;
         if (staff != null && listShopMap.containsKey(staff.getShopId().toString())) {
         TypeBranch = 1L;
         } else if (staff != null && listShopBranchMap.containsKey(staff.getShopId().toString())) {
         TypeBranch = 2L;
         }
         if (staff != null && staff.getShopId() != null 
         && ( listShopMap.containsKey(staff.getShopId().toString()) 
         || listShopBranchMap.containsKey(staff.getShopId().toString()))
         && priceDAO.findPriceForBranchSaleRetail(TypeBranch).size() > 0) {
         listPrice = priceDAO.findPriceForBranchSaleRetail(TypeBranch);
         }
         else if (staff != null && checkUserOfSH(getSession(), staff.getStaffId()) && priceDAO.findPriceOnlyShowroomSaleRetail().size() > 0) {
         listPrice = priceDAO.findPriceOnlyShowroomSaleRetail();
         } else {
         listPrice = priceDAO.findManyPriceForSaleRetail();
         }
         */
        listPrice = priceDAO.findManyPriceForSaleRetail();
        Iterator iter = listPrice.iterator();
        List<Price> listPriceTemp = new ArrayList<Price>();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            Price price = new Price();
            price.setPriceId(new Long(temp[0].toString()));
            price.setPriceName(temp[1].toString());
            listPriceTemp.add(price);
        }
        return listPriceTemp;

    }

    private boolean checkExistsGoods(List<SaleTransDetailV2Bean> lstGoods, Long stockModelId) {
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                return false;
            }
        }
        return true;
    }

    private void sumSaleTransAmount(List<SaleTransDetailV2Bean> lstGoods) throws Exception {
        saleTransForm.setAmountBeforeTax(0.0);
        saleTransForm.setAmountTax(0.0);
        saleTransForm.setAmountAfterTax(0.0);
        saleTransForm.setAmountDiscount(0.0);

        saleTransForm.setAmountBeforeTaxDisplay("0");
        saleTransForm.setAmountTaxDisplay("0");
        saleTransForm.setAmountAfterTaxDisplay("0");
        saleTransForm.setAmountDiscountDisplay("0");

        if (lstGoods == null || lstGoods.isEmpty()) {
            return;
        }

        Double amountAfterTax = 0.0;
        Double amountBeforeTax = 0.0;
        Double amountTax = 0.0;


        for (int i = 0; i < lstGoods.size(); i++) {
            SaleTransDetailV2Bean bean = lstGoods.get(i);
            if (bean == null) {
                continue;
            }
            amountBeforeTax += bean.getAmountBeforeTax();
            amountTax += bean.getAmountTax();
            amountAfterTax += bean.getAmountAfterTax();
        }
        saleTransForm.setAmountBeforeTax(amountBeforeTax);
        saleTransForm.setAmountTax(amountTax);
        saleTransForm.setAmountAfterTax(amountAfterTax);

        saleTransForm.setAmountBeforeTaxDisplay(NumberUtils.rounđAndFormatAmount(amountBeforeTax));
        saleTransForm.setAmountTaxDisplay(NumberUtils.rounđAndFormatAmount(amountTax));
        saleTransForm.setAmountAfterTaxDisplay(NumberUtils.rounđAndFormatAmount(amountAfterTax));
    }

    public String addGoods() throws Exception {
        String pageForward = SALE_TO_RETAIL_DETAIL;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            setTabSession(SESSION_LIST_GOODS, lstGoods);
        } else {
            //R8388
            if (isSaleToVipCustomer()) {
                this.sumSaleTransAmountVip(lstGoods);
            } else {
                this.sumSaleTransAmount(lstGoods);
            }
        }

        String stockModelCode = this.saleTransForm.getSaleTransDetailForm().getStockModelCode();
        Long priceId = this.saleTransForm.getSaleTransDetailForm().getPriceId();
        Long quantity = this.saleTransForm.getSaleTransDetailForm().getQuantity();
        if (stockModelCode == null || stockModelCode.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        //trim cac truong can thiet
        stockModelCode = stockModelCode.trim().toUpperCase();
        this.saleTransForm.getSaleTransDetailForm().setStockModelCode(stockModelCode);
        //
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.getStockModelByCode(stockModelCode);
        if (stockModel == null || stockModel.getStockModelId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        }
        if (stockModel.getTelecomServiceId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotTel");
            return pageForward;
        }


        if (lstGoods != null && lstGoods.size() > 0) {
            Long telecomServiceId = ((SaleTransDetailV2Bean) lstGoods.get(0)).getTelecomServiceId();
            if (!stockModel.getTelecomServiceId().equals(telecomServiceId)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.060");
                return pageForward;
            }
        }

        if (!checkExistsGoods(lstGoods, stockModel.getStockModelId())) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.SameStock");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (priceId == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotPrice");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */
        boolean checkValidatePrice = false;
        List<Price> lstPrice = this.getListPrice(stockModel.getStockModelId());
        if (lstPrice != null && lstPrice.size() > 0) {
            for (int i = 0; i < lstPrice.size(); i++) {
                if (lstPrice.get(i) != null && lstPrice.get(i).getPriceId() != null && lstPrice.get(i).getPriceId().equals(priceId)) {
                    checkValidatePrice = true;
                    break;
                }
            }
        }
        if (!checkValidatePrice) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotPrice");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, lstPrice);
            return pageForward;
        }
        /*120501 : TRONGLV : check gia + mat hang co khop nhau khong */


        //--------kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        //kiem tra truong so luong phai ko am
        if (quantity == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.quantity");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(0L) <= 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        Long ownerType = Constant.OWNER_TYPE_STAFF;
        Long ownerId = userToken.getUserID();

        StockTotal stockTotal = stockTotalDAO.getStockTotal(ownerType, ownerId, stockModel.getStockModelId(), Constant.STATE_NEW);
        if (stockTotal == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(stockTotal.getQuantityIssue()) > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }


        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Long priceBasic = priceDAO.findBasicPrice(stockModel.getStockModelId(), shop.getPricePolicy());
        if (priceBasic == null && checkStockOwnerTmpDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.143");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        Price newPrice = priceDAO.findById(priceId);
        if (newPrice == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Price is not exists!");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }
        if (lstGoods != null && lstGoods.size() > 0) {
            //check vat & currency
            Long oldPriceId = ((SaleTransDetailV2Bean) lstGoods.get(0)).getPriceId();
            Price oldPrice = priceDAO.findById(oldPriceId);
            if (!checkSamePrice(oldPrice, newPrice)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Goods must be same tax rate and currency type!");
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
                return pageForward;
            }
        }

        SaleTransDetailV2Bean bean = new SaleTransDetailV2Bean();
        bean.setStockModelId(stockModel.getStockModelId());
        bean.setStockModelCode(stockModel.getStockModelCode());
        bean.setStockModelName(stockModel.getName());
        bean.setTelecomServiceId(stockModel.getTelecomServiceId());
        bean.setCheckSerial(stockModel.getCheckSerial());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParamsBean appParamsBean = appParamsDAO.findAppParamsByCode(Constant.APP_PARAMS_STOCK_MODEL_UNIT, stockModel.getUnit());
        if (appParamsBean != null) {
            bean.setStockModelUnit(appParamsBean.getName());
        }

        bean.setQuantity(quantity);

        bean.setPriceId(newPrice.getPriceId());
        bean.setPrice(newPrice.getPrice());
        bean.setVat(newPrice.getVat());
        bean.setCurrency(newPrice.getCurrency());
        bean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(newPrice.getPrice()));

        if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
            bean.setAmountAfterTax(
                    quantity * newPrice.getPrice());//For update to sale_trans_detail
            bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
            bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
        } else {//Neu la gia truoc thue (HAITI)
            bean.setAmountBeforeTax(
                    quantity * newPrice.getPrice());//For update to sale_trans_detail            
            bean.setAmountTax(bean.getAmountBeforeTax() * newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
            bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
        }
        bean.setAmountAfterTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
        bean.setAmountTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
        bean.setAmountBeforeTaxDisplay(
                NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface
        //neu ban cho khach hang vip
        if (isSaleToVipCustomer()) {
            bean.setDiscountGroupId(getDiscountGroupIdVip(bean));
        }

        lstGoods.add(bean);

        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());

        //R8388
        if (isSaleToVipCustomer()) {
            this.sumSaleTransAmountVip(lstGoods);
        } else {
            this.sumSaleTransAmount(lstGoods);
        }
        setTabSession(SESSION_LIST_GOODS, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.STK.020");
        return pageForward;
    }

    public String prepareEditGoods() throws Exception {
        saleTransForm = new SaleTransForm();
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }
        //kiem tra truong hop neu ban cho khach hang vip toancx R8388
        String reasonId = (String) req.getParameter("reasonId");
        if (reasonId != null && reasonId.equals(getReasonIdVipCustomer())) {
            this.sumSaleTransAmountVip(lstGoods);
        } else {
            this.sumSaleTransAmount(lstGoods);
        }

        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp == null || stockModelIdTemp.trim().equals("")) {
            return pageForward;
        }
        Long stockModelId = Long.valueOf(stockModelIdTemp.trim());
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                SaleTransDetailForm form = new SaleTransDetailForm();
                form.setStockModelId(bean.getStockModelId());
                form.setStockModelCode(bean.getStockModelCode());
                form.setStockModelName(bean.getStockModelName());
                form.setPriceId(bean.getPriceId());
                form.setQuantity(bean.getQuantity());
                form.setNote(bean.getNote());

                saleTransForm.setSaleTransDetailForm(form);
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(bean.getStockModelId()));
                break;
            }
        }
        return pageForward;
    }

    public String deleteGoods() throws Exception {
        saleTransForm = new SaleTransForm();
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }

        String stockModelIdTemp = (String) req.getParameter("stockModelId");
        if (stockModelIdTemp == null || stockModelIdTemp.trim().equals("")) {
            return pageForward;
        }

        Long stockModelId = Long.valueOf(stockModelIdTemp.trim());
        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                lstGoods.remove(bean);
                break;
            }
        }
        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());

        //kiem tra truong hop neu ban cho khach hang vip toancx R8388
        String reasonId = (String) req.getParameter("reasonId");
        if (reasonId != null && reasonId.equals(getReasonIdVipCustomer())) {
            this.sumSaleTransAmountVip(lstGoods);
        } else {
            this.sumSaleTransAmount(lstGoods);
        }
        setTabSession(SESSION_LIST_GOODS, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.STK.021");
        return pageForward;
    }

    public String cancelEditGoods() throws Exception {
        String reasonIdCF = null;
        if (this.saleTransForm != null) {
            reasonIdCF = this.saleTransForm.getReasonId() != null ? this.saleTransForm.getReasonId().toString() : null;
        }
        saleTransForm = new SaleTransForm();
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }
        //tinh chiet khau neu khach hang VIP
        String reasonId = req.getSession().getAttribute("reasonIdRetail") != null ? req.getSession().getAttribute("reasonIdRetail").toString() : "";
        if (reasonIdCF != null && !reasonIdCF.isEmpty()) {
            reasonId = reasonIdCF;
        }
        if (reasonId.equals(getReasonIdVipCustomer())) {
            this.sumSaleTransAmountVip(lstGoods);
            this.saleTransForm.setReasonId(Long.valueOf(reasonId));
            //set OTP
            String otp = req.getSession().getAttribute("otpVipCustomer") != null ? req.getSession().getAttribute("otpVipCustomer").toString() : "";
            this.saleTransForm.setCustOTP(otp);
            //remove session
            req.getSession().removeAttribute("reasonIdRetail");
            req.getSession().removeAttribute("otpVipCustomer");
        } else {
            this.sumSaleTransAmount(lstGoods);
        }

        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());
        return pageForward;
    }

    public String editGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            lstGoods = new ArrayList();
            return pageForward;
        }

        Long priceId = this.saleTransForm.getSaleTransDetailForm().getPriceId();
        Long quantity = this.saleTransForm.getSaleTransDetailForm().getQuantity();
        Long stockModelId = this.saleTransForm.getSaleTransDetailForm().getStockModelId();

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null || stockModel.getStockModelId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        }

        if (priceId == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.NotPrice");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        //--------kiem tra luong hang trong kho co du de thuc hien xuat theo so luong o tren khong
        //kiem tra truong so luong phai ko am
        if (quantity == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.quantity");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(0L) <= 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.118"); //!!!Lỗi. Số lượng phải là số nguyên dương
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        Long ownerType = Constant.OWNER_TYPE_STAFF;
        Long ownerId = userToken.getUserID();

        StockTotal stockTotal = stockTotalDAO.getStockTotal(ownerType, ownerId, stockModel.getStockModelId(), Constant.STATE_NEW);
        if (stockTotal == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        if (quantity.compareTo(stockTotal.getQuantityIssue()) > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.STK.117"); //!!!Lỗi. Số lượng hàng trong kho không đủ
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }


        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Long priceBasic = priceDAO.findBasicPrice(stockModel.getStockModelId(), shop.getPricePolicy());
        if (priceBasic == null && checkStockOwnerTmpDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF)) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.143");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }

        Price newPrice = priceDAO.findById(priceId);
        if (newPrice == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Price is not exists!");
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
            return pageForward;
        }
        if (lstGoods != null && lstGoods.size() > 0) {
            //check vat & currency
            Long oldPriceId = ((SaleTransDetailV2Bean) lstGoods.get(0)).getPriceId();
            Price oldPrice = priceDAO.findById(oldPriceId);
            if (!checkSamePrice(oldPrice, newPrice)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Goods must be same tax rate and currency type!");
                req.setAttribute(REQUEST_LIST_STOCK_MODEL_PRICE, this.getListPrice(stockModel.getStockModelId()));
                return pageForward;
            }
        }

        for (SaleTransDetailV2Bean bean : lstGoods) {
            if (bean.getStockModelId().equals(stockModelId)) {
                bean.setPriceId(newPrice.getPriceId());
                bean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(newPrice.getPrice()));
                bean.setQuantity(quantity);

                if (Constant.PRICE_AFTER_VAT) {//Neu la gia sau thue (VIETNAM)
                    bean.setAmountAfterTax(
                            quantity * newPrice.getPrice());//For update to sale_trans_detail
                    bean.setAmountBeforeTax(bean.getAmountAfterTax() / (1.0 + newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT));
                    bean.setAmountTax(bean.getAmountAfterTax() - bean.getAmountBeforeTax());
                } else {//Neu la gia truoc thue (HAITI)
                    bean.setAmountBeforeTax(
                            quantity * newPrice.getPrice());//For update to sale_trans_detail
                    bean.setAmountTax(bean.getAmountBeforeTax() * newPrice.getVat() / Constant.DISCOUNT_RATE_DENOMINATOR_DEFAULT);
                    bean.setAmountAfterTax(bean.getAmountTax() + bean.getAmountBeforeTax());
                }
                bean.setAmountAfterTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountAfterTax()));//For display to interface
                bean.setAmountTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountTax()));//For display to interface
                bean.setAmountBeforeTaxDisplay(
                        NumberUtils.rounđAndFormatAmount(bean.getAmountBeforeTax()));//For display to interface
                //toancx R8388
                //neu khach hang vip them chinh sach chiet khau
                if (isSaleToVipCustomer()) {
                    bean.setDiscountGroupId(getDiscountGroupIdVip(bean));
                }
                break;
            }
        }
        this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());

        //toancx R8388
        //tinh them chiet khau neu khach hang vip
        if (isSaleToVipCustomer()) {
            this.sumSaleTransAmountVip(lstGoods);
        } else {
            this.sumSaleTransAmount(lstGoods);
        }
        setTabSession(SESSION_LIST_GOODS, lstGoods);

        req.setAttribute(Constant.RETURN_MESSAGE, "M.200003");

        return pageForward;
    }

    public String updateListGoods() throws Exception {
        return this.cancelEditGoods();
    }

    private boolean checkSamePrice(Price oldPrice, Price newPrice) {
        if (oldPrice == null || newPrice == null
                || oldPrice.getVat() == null || newPrice.getVat() == null
                || oldPrice.getCurrency() == null || newPrice.getCurrency() == null) {
            return false;
        }
        if (!oldPrice.getVat().equals(newPrice.getVat())
                || !oldPrice.getCurrency().equals(newPrice.getCurrency())) {
            return false;
        }
        return true;
    }

    private boolean checkValidateSaleTrans() throws Exception {
        HttpServletRequest req = getRequest();
//        if ((saleTransForm.getCustName() == null) || (saleTransForm.getCustName().equals(""))) {
//            req.setAttribute("returnMsg", "saleRetail.warn.cust");
//            return false;
//        }

        if ((saleTransForm.getReasonId() == null) || (saleTransForm.getReasonId().equals(0L))) {
            req.setAttribute("returnMsg", "saleRetail.warn.reason");
            return false;
        }
        if ((saleTransForm.getPayMethod() == null) || (saleTransForm.getPayMethod().equals(""))) {
            req.setAttribute("returnMsg", "saleRetail.warn.pay");
            return false;
            //Check them thanh toan qua Emola can co SDT Emola
        } else if (saleTransForm.getPayMethod().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
            if (saleTransForm.getIsdnWallet() == null || saleTransForm.getIsdnWallet().equals("")) {
                req.setAttribute("returnMsg", "saleRetail.warn.IsdnWallet");
                return false;
            }
        }
        if (isSaleToVipCustomer()) {
            if (saleTransForm.getCustOTP() == null || saleTransForm.getCustOTP().isEmpty()) {
                req.setAttribute("returnMsg", "saleRetail.warn.otp");
                return false;
            }
        }

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods == null || lstGoods.isEmpty()) {
            req.setAttribute("returnMsg", "saleRetail.warn.NotStock");
            return false;
        }
        //R8388
        if (isSaleToVipCustomer()) {
            //validate otp expire
            String errorMsg = validateOTPVipCustomer(this.saleTransForm.getCustMobile(), this.saleTransForm.getCustOTP());
            if (errorMsg != null && !errorMsg.isEmpty()) {
                req.setAttribute("returnMsg", errorMsg);
                return false;
            }

            //validate khach hang da ton tai giao dich vip
            if (checkExistedTransInMonthVIPCust(this.saleTransForm.getCustMobile())) {
                req.setAttribute("returnMsg", "saleRetail.warn.existedTransVIP");
                return false;
            }

            this.sumSaleTransAmountVip(lstGoods);
        } else {
            this.sumSaleTransAmount(lstGoods);
        }
        return true;
    }

    public String saveSaleTransToRetail() throws Exception {
        String pageForward = SALE_TO_RETAIL;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        getDataForCombobox();
        logdetail.info("Begin Method saveSaleTransToRetail :..........");
        System.out.println("Begin Method saveSaleTransToRetail :..........");
        try {
            this.saleTransForm.setSaleTransDetailForm(new SaleTransDetailForm());
            //hien thi giao dien ban cho khach hang vip
            if (isSaleToVipCustomer()) {
                logdetail.info("SaleTransToRetail: " + " CustName:" + this.saleTransForm.getCustName() + "isdnWallet " + this.saleTransForm.getIsdnWallet() + ":isVipCustomer");
                req.setAttribute("isVipCustomer", "true");
            }

            if (!this.checkValidateSaleTrans()) {
                return pageForward;
            }
            //            tannh20180509 : kiem tra han muc cong no
            //LinhNBV 20180524: fixed check debit of staff.
            DbProcessor db = new DbProcessor();


            /**
             * Modified by : haint41 Modified date : 16/11/2011 Purpose : check
             * ban bo hang hoa
             */
            List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
            String result = this.checkSalePackageGoods(lstGoods);

            if (!result.equals("")) {
                String[] arr = result.split("@");
                if (arr.length < 2) {
                    req.setAttribute("returnMsg", "E.100028");
                } else if (arr.length == 2) {
                    req.setAttribute("returnMsg", "E.100036");
                    /*
                     req.setAttribute("returnMsg", "E.100034");
                     List listParamValue = new ArrayList();
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
                return pageForward;
            }
            /*ket thuc check ban bo hang hoa theo cach cu cua mzb*/

            //R8119: chi cho phep ban san pham duoc phep ban le
            //khong cho phep san pham chi cho phep ban theo goi
            /*bo sung check theo yeu cau moi*/
            //Check dieu kien neu ban goi hang thi da du chua
//        List<ViewPackageCheck> listView = new ArrayList<ViewPackageCheck>();
//        for (int i = 0; i < lstGoods.size(); i++) {
////            SaleTransDetailBean saleTransDetailBean = (SaleTransDetailBean) lstGoods.get(i);
//            SaleTransDetailV2Bean saleTransDetailBean = (SaleTransDetailV2Bean) lstGoods.get(i);
//            ViewPackageCheck viewPackageCheck = new ViewPackageCheck();
//            StockModelDAO stockModelDAO = new StockModelDAO();
//            stockModelDAO.setSession(this.getSession());
//            StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
//            viewPackageCheck.setStockModelId(stockModel.getStockModelId());
//            viewPackageCheck.setStockModelCode(stockModel.getStockModelCode());
//            viewPackageCheck.setQuantity(saleTransDetailBean.getQuantity());
//            listView.add(viewPackageCheck);
//        }
//        String outPut = checkPackageGoodsToSaleTrans(listView);
//        if (!"".equals(outPut)) {

            /*check mat hang cho phep ban le*/
            /*kiem tra trong danh sach mat hang, co mat hang nao nam trong bo hang hoa bat ky, ma trang thai check = (khong cho ban le) ==> khong cho ban*/
            /**/
//        String outPut = "";
//        for (int i = 0; i < lstGoods.size(); i++) {
//            SaleTransDetailV2Bean saleTransDetailBean = (SaleTransDetailV2Bean) lstGoods.get(i);
//            PackageGoodsDetailDAO packageGoodsDetailDAO = new PackageGoodsDetailDAO();
//            List<PackageGoodsDetail> listPackageGoodsDetail = packageGoodsDetailDAO.findByStockModelId(saleTransDetailBean.getStockModelId());
//            if (listPackageGoodsDetail != null && listPackageGoodsDetail.size() != 0) {
//                if(i == 0){
//                    outPut = getText("ERR.RET.060") + listPackageGoodsDetail.get(0).getStockModelCode();
//                } else {
//                    outPut += "," + listPackageGoodsDetail.get(0).getStockModelCode();
//                }             
//            }
//        }
//        
//        if(!outPut.isEmpty()){
//            req.setAttribute("returnMsg", outPut);
//            return pageForward;
//       }
//            if (true) {
//                req.setAttribute("returnMsg", outPut);
////                req.setAttribute("lstGoods", lstGoods);
////                logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), outPut);
//                return pageForward;
//            }
//        }
            /*ket thuc check theo yeu cau moi*/

            /*Tinh lai tong tien, chiet khau*/
            if (isSaleToVipCustomer()) {
                this.sumSaleTransAmountVip(lstGoods);
            } else {
                this.sumSaleTransAmount(lstGoods);
            }

            if (!this.saleTransForm.getPayMethod().equals("2")) {
                //LinhNBV start modified on May 24 2018: Check amountSaleTrans + deposit + payment + amountTax > limitMoney
//            if (isStaffMaputo(userToken.getLoginName())) {
                double sumSaleTrans = db.sumSaleTransDebitByStaffCode(userToken.getLoginName());
                double sumDeposit = db.sumDepositDebitByStaffCode(userToken.getLoginName());
                double sumPayment = db.sumPaymentDebitByStaffCode(userToken.getLoginName());
                double limitMoney = db.getLimitMoneyByStaffCode(userToken.getLoginName());
                if ((sumSaleTrans + sumDeposit + sumPayment + Double.parseDouble(this.saleTransForm.getAmountAfterTaxDisplay().replaceAll(",", ""))) > limitMoney) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.159");
                    return pageForward;
                }
//            }
                //end.


                /*luu giao dich ban hang*/
                SaleTrans saleTrans = this.saveSaleTrans();
                logdetail.info("SaleTransToRetail: " + "CustName: " + this.saleTransForm.getCustName() + "isdnWallet " + this.saleTransForm.getIsdnWallet() + "saveSaleTrans");
                if (saleTrans == null || saleTrans.getSaleTransId() == null) {
                    logdetail.info("saveSaleTrans is fail.");
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }

                //DINHDC CODE THEM PHAN THANH TOAN QUA EMOLA
                ResponseWallet responseWallet = new ResponseWallet();
                SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
                if (this.saleTransForm.getPayMethod() != null
                        && this.saleTransForm.getPayMethod().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
                    if (saleTrans.getSaleTransId() > 0) {
                        logdetail.info("SaleTransToRetail: " + "CustName: " + this.saleTransForm.getCustName() + "isdnWallet " + this.saleTransForm.getIsdnWallet() + "saleTransEmola");
                        SaleTransEmola saleTransEmola = saleCommonDAO.insertSaleTransEmola(saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
                                saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
                                "0", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), this.saleTransForm.getIsdnWallet(), null, "New Create Transaction", "1", getSysdate());
                        if (saleTransEmola == null || saleTransEmola.getSaleTransId() == null) {
                            logdetail.info("SaleTransToRetail: saleTransEmola is fail. saleTransId :" + saleTrans.getSaleTransId());
                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();
                        } else {
                            logdetail.info("SaleTransToRetail: saleTransEmola is success. saleTransId: " + saleTrans.getSaleTransId());
                            //Commit truoc sau do se giao tiep voi API Vi Dien Tu
                            this.getSession().save(saleTransEmola);
                            this.getSession().getTransaction().commit();
                            this.getSession().getTransaction().begin();
                        }
                    }
                }
                //END
            /*luu giao dich vip*/
                if (isSaleToVipCustomer()) {
                    logdetail.info("SaleTransToRetail: saveSaleTransVip ");
                    SaleTransVip saleTransVip = this.saveSaleTransVip(saleTrans);
                    if (saleTransVip == null || saleTransVip.getSaleTransVipId() == null) {
                        logdetail.info("SaleTransToRetail: saveSaleTransVip Error. saleTransId: " + saleTrans.getSaleTransId());
                        req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction vip!");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }
                }

                for (SaleTransDetailV2Bean bean : lstGoods) {
                    logdetail.info("SaleTransToRetail: saveSaleTransDetail ");
                    SaleTransDetail saleTransDetail = this.saveSaleTransDetail(saleTrans, bean);
                    if (saleTransDetail == null || saleTransDetail.getSaleTransId() == null) {
                        logdetail.info("SaleTransToRetail: saveSaleTransDetail fail. saletransId" + saleTrans.getSaleTransId());
                        req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction detail!");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }

                    if (bean.getCheckSerial() != null && bean.getCheckSerial().equals(1L)) {
                        if (bean.getLstSerial() == null || bean.getLstSerial().isEmpty()) {
                            logdetail.info("SaleTransToRetail: Serial list empty. saletransId" + saleTrans.getSaleTransId());
                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Serial list must be not empty!");
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();
                            return pageForward;
                        }
                        if (!saveSaleTransSerial(saleTransDetail, bean.getLstSerial())) {
                            logdetail.info("SaleTransToRetail: saveSaleTransSerial fail. saletransId" + saleTrans.getSaleTransId());
                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save serial list of sale transaction!");
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();
                            return pageForward;
                        }
                    } else//neu la mat hang khong quan ly theo serial, khong cho chon mat hang anypay
                    if (bean.getStockModelCode() != null && bean.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
                        logdetail.info("SaleTransToRetail: dont accept Anypay " + saleTrans.getSaleTransId());
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        //save giao dich ban hang that bai
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.098");
                        //log.debug("End method saveSaleTransToRetail of SaleToRetailV2DAO");
                        return pageForward;
                    }
                }
                //tinh tong gia tri hang hoa trong giao dich ban hang
                Double saleTransAmount = sumAmountByGoodsList(lstGoods);
                if (saleTransAmount != null && saleTransAmount >= 0) {
                    //Cap nhat lai gia tri hang hoa cua nhan vien
                    if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * saleTransAmount)) {
                        req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0001"));
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }
                } else {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0014"));
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }

                saleTransForm = new SaleTransForm();
                saleTransForm.setSaleTransDate(getSysdate());
                saleTransForm.setReasonIdVipCustomer(getReasonIdVipCustomer());
                setTabSession(SESSION_LIST_GOODS, new ArrayList());
                req.setAttribute(Constant.RETURN_MESSAGE, "Payment.Sale.Trans.Success");
                //DINHDC CODE THEM PHAN THANH TOAN QUA EMOLA
                if (this.saleTransForm.getPayMethod() != null
                        && this.saleTransForm.getPayMethod().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
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
                                    this.saleTransForm.getIsdnWallet(), amountPay, this.saleTransForm.getCustName(),
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
                                return pageForward;
                            } else {
                                Gson gson = new Gson();
                                responseWallet = gson.fromJson(response, ResponseWallet.class);
                                if (responseWallet != null && responseWallet.getResponseCode() != null) {
                                    if (responseWallet.getResponseCode().equals("01")) {
                                        req.setAttribute(Constant.RETURN_MESSAGE, "Payment.Sale.Trans.Success");
                                        /*
                                         saleCommonDAO.insertSaleTransEmola(this.getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
                                         saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
                                         "1", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), this.saleTransForm.getIsdnWallet(), "1", "PAYMENT SUCCESS", "1", getSysdate());
                                         */
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
                                        /* 
                                         saleCommonDAO.insertSaleTransEmola(this.getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
                                         saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
                                         "2", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), this.saleTransForm.getIsdnWallet(), "0", "Payment.Emola.Sale.Trans.UnSuccess", "1", getSysdate());
                                         */
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
                                        return pageForward;
                                    }
                                } else {
                                    req.setAttribute(Constant.RETURN_MESSAGE, "Error.Call.WS.Payment.Wallet");
                                    /*
                                     saleCommonDAO.insertSaleTransEmola(this.getSession(), saleTrans.getSaleTransId(), saleTrans.getSaleTransCode(),
                                     saleTrans.getCustName(), saleTrans.getSaleTransDate(), saleTrans.getSaleTransType(),
                                     "2", saleTrans.getShopId(), saleTrans.getStaffId(), saleTrans.getAmountTax(), this.saleTransForm.getIsdnWallet(), "0", "Error.Call.WS.Payment.Wallet", "1", getSysdate());
                                     */
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
                                    return pageForward;
                                }
                            }
                        } else {
                            logdetail.info("SaleTransToRetail: Error. Can not update sale transaction! :" + saleTrans.getSaleTransId());
                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
                            this.getSession().clear();
                            this.getSession().getTransaction().rollback();
                            this.getSession().getTransaction().begin();
                            return pageForward;
                        }
                    }
                }

            } else {

//                // check khong nhap orderCode
//                if (saleTransForm.getOrderCode() == null || saleTransForm.getOrderCode().trim().equalsIgnoreCase("")) {
//                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.153");
//                    return pageForward;
//                } else {
//                    if (checkAlreadlyHaveOrderCode(saleTransForm.getOrderCode())) {
//                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.152");
//                        return pageForward;
//                    }
//                }

                //Thuc hien nghiep vu Account transfer...
                //Upload file to ftp
                String lStr = null;
//                if (!saleTransForm.getImageUrl().equals("")) {
//                    String serverFilePath = this.saleTransForm.getImageUrl();
//                    String[] fileParth = serverFilePath.split(",");
//                    // tannh 11/23/2017 ; upload file to server 
//                    String host = ResourceBundleUtils.getResource("server_get_file_to_server_host");
//                    String userName = ResourceBundleUtils.getResource("server_get_file_to_server_username");
//                    String password = ResourceBundleUtils.getResource("server_get_file_to_server_password");
//                    String tempDir = ResourceBundleUtils.getResource("server_get_file_to_server_tempdir_upto_file_accounttrans");
//                    if (fileParth[0].length() == 0) {
//                        saleTransForm.setImageUrl("");
//                    } else {
//                        if (fileParth[0].endsWith(".rar")) {
//
//                            if (fileParth[0].contains("\\")) {
//                                lStr = fileParth[0].substring(fileParth[0].lastIndexOf("\\"));
//                            } else {
//                                lStr = fileParth[0].substring(fileParth[0].lastIndexOf("/"));
//                            }
//                            lStr = tempDir + lStr.substring(1);
//                            saleTransForm.setImageUrl(lStr);
//                            List<String> lst = new ArrayList<String>();
//                            lst.add(fileParth[0]);
//                            FTPUtils.uploadListFileFromFTPServer(host, userName, password, lst, tempDir, tempDir);
//                        } else {
//                            this.req.setAttribute("messageUpdate", "ER.RAR");
//                            return pageForward;
//                        }
//                    }
//                }
                /*luu giao dich ban hang*/
                SaleTransOrder saleTrans = this.saveSaleTransOrder(lStr, saleTransForm.getOrderCode());
                logdetail.info("SaleTransOrderToRetail: " + "CustName: " + this.saleTransForm.getCustName() + "isdnWallet " + this.saleTransForm.getIsdnWallet() + "saveSaleTrans");
                //LinhNBV change saleTransId --> saleTransOrderId
                if (saleTrans == null || saleTrans.getSaleTransOrderId() == null) {
                    logdetail.info("saveSaleTransOrder is fail.");
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save sale transaction!");
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }

                for (SaleTransDetailV2Bean bean : lstGoods) {
                    logdetail.info("SaleTransToRetail: SaleTransDetailOrderToRetail ");
                    SaleTransDetailOrder saleTransDetail = this.saveSaleTransDetailOrder(saleTrans, bean);
                    //LinhNBV change saleTransId --> saleTransOrderId
                    if (saleTransDetail == null || saleTransDetail.getSaleTransOrderId() == null) {
                        //LinhNBV change saleTransId --> saleTransOrderId
                        logdetail.info("SaleTransDetailOrderToRetail: SaleTransDetailOrderToRetail fail. saletransId" + saleTrans.getSaleTransOrderId());
                        req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save SaleTransDetailOrderToRetail transaction detail!");
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }

                    if (bean.getCheckSerial() != null && bean.getCheckSerial().equals(1L)) {
                        if (bean.getLstSerial() == null || bean.getLstSerial().isEmpty()) {
//                            logdetail.info("SaleTransToRetail: Serial list empty. saletransId" + saleTrans.getSaleTransId());
//                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Serial list must be not empty!");
//                            this.getSession().clear();
//                            this.getSession().getTransaction().rollback();
//                            this.getSession().getTransaction().begin();
//                            return pageForward;
                            //nothing do
                        }
//                        if (!this.saveSaleTransSerialOrder(saleTransDetail, bean.getLstSerial())) {
////                            logdetail.info("SaleTransToRetail: saveSaleTransSerialOrder fail. saletransId" + saleTrans.getSaleTransId());
////                            req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not save serial list of sale transaction!");
////                            this.getSession().clear();
////                            this.getSession().getTransaction().rollback();
////                            this.getSession().getTransaction().begin();
////                            return pageForward;
//                            // do nothing
//                        }
                    } else//neu la mat hang khong quan ly theo serial, khong cho chon mat hang anypay
                    if (bean.getStockModelCode() != null && bean.getStockModelCode().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
                        logdetail.info("SaleTransToRetail: dont accept Anypay " + saleTrans.getSaleTransOrderId());
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        //save giao dich ban hang that bai
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.SAE.098");
                        //log.debug("End method saveSaleTransToRetail of SaleToRetailV2DAO");
                        return pageForward;
                    }
                }

                //tinh tong gia tri hang hoa trong giao dich ban hang
                Double saleTransAmount = sumAmountByGoodsList(lstGoods);
                if (saleTransAmount != null && saleTransAmount >= 0) {
                    //Cap nhat lai gia tri hang hoa cua nhan vien
                    if (!addCurrentDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, -1 * saleTransAmount)) {
                        req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0001"));
                        this.getSession().clear();
                        this.getSession().getTransaction().rollback();
                        this.getSession().getTransaction().begin();
                        return pageForward;
                    }
                } else {
                    req.setAttribute(Constant.RETURN_MESSAGE, getText("ERR.LIMIT.0014"));
                    this.getSession().clear();
                    this.getSession().getTransaction().rollback();
                    this.getSession().getTransaction().begin();
                    return pageForward;
                }

            }

            if (saleTransForm.getPayMethod() != null && saleTransForm.getPayMethod().equals("2")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Payment.Create.Order.Trans.Success");
                //Send sms to Finance staff
                String message = ResourceBundle.getBundle("config").getString("msgToFinanceStaff");
                String staffCode = userToken.getLoginName();
//                String orderCode = saleTransForm.getOrderCode();
                String orderCode = "";
                //Send message to finance staff of branch
                String shopCode = userToken.getShopCode().substring(0, Math.min(userToken.getShopCode().length(), 3)) + "BR";
                List<String> lstIsdn = db.getIsdnFinanceStaff(shopCode);
                for (String isdn : lstIsdn) {
                    db.sendSms("258" + isdn, message.replace("XXX", staffCode).replace("YYY", orderCode), "86904");
                }
                System.out.println("chuan bi nhan tin");
//                db.sendSms("258871233799", message.replace("XXX", staffCode).replace("YYY", orderCode), "86904");
                System.out.println("chuan bi expor excel");
                // create file .pdf
                fileDowd = expOrderToExcel(lstGoods, saleTransForm);
                System.out.println("Qua expor excel");
                saleTransForm = new SaleTransForm();
                saleTransForm.setSaleTransDate(getSysdate());
                saleTransForm.setReasonIdVipCustomer(getReasonIdVipCustomer());

                saleTransForm.setPayMethod(Constant.PAY_METHOD_ACCOUNT_TRANFER);
            } else {
                req.setAttribute(Constant.RETURN_MESSAGE, "Payment.Sale.Trans.Success");
                saleTransForm = new SaleTransForm();
                saleTransForm.setSaleTransDate(getSysdate());
                saleTransForm.setReasonIdVipCustomer(getReasonIdVipCustomer());
            }

            setTabSession(SESSION_LIST_GOODS, new ArrayList());
            //LinhNBV start modified on May 22 2018: Show download order when create order success.
            if (saleTransForm.getPayMethod() != null && saleTransForm.getPayMethod().equals("2")) {
                req.getSession().setAttribute("downloadOrder", 1);
            }

        } catch (Exception ex) {
            //ghi log
            logdetail.info("Exception  Method saveSaleTransToRetail :" + ex);
            req.setAttribute(Constant.RETURN_MESSAGE, "Payment.Sale.Trans.Error");
            this.getSession().clear();
            this.getSession().getTransaction().rollback();
            this.getSession().beginTransaction();
        }
        logdetail.info("END Method saveSaleTransToRetail :..........");
        return pageForward;
    }

    private SaleTrans saveSaleTrans() throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTrans saleTrans = new SaleTrans();
        saleTrans.setSaleTransId(getSequence("SALE_TRANS_SEQ"));
        saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));

        saleTrans.setSaleTransDate(getSysdate());
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_RETAIL);
        if (saleTransForm.getPayMethod() != null
                && saleTransForm.getPayMethod().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NEW);
        } else {
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }
        saleTrans.setCheckStock("0");

        saleTrans.setCustName(saleTransForm.getCustName());
        saleTrans.setCompany(saleTransForm.getCompany());
        saleTrans.setTin(saleTransForm.getTin());


        saleTrans.setShopId(userToken.getShopId());
        saleTrans.setStaffId(userToken.getUserID());
        saleTrans.setReasonId(saleTransForm.getReasonId());
        saleTrans.setPayMethod(saleTransForm.getPayMethod());
        saleTrans.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        saleTrans.setAmountTax(saleTransForm.getAmountAfterTax());
        saleTrans.setTax(saleTransForm.getAmountTax());
        saleTrans.setDiscount(saleTransForm.getAmountDiscount());

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods != null && !lstGoods.isEmpty()) {
            SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) lstGoods.get(0);
            saleTrans.setTelecomServiceId(bean.getTelecomServiceId());
            saleTrans.setVat(bean.getVat());
            saleTrans.setCurrency(bean.getCurrency());
        }

        this.getSession().save(saleTrans);


        return saleTrans;
    }

    private SaleTransOrder saveSaleTransOrder(String pathFileScan, String orderCode) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        //LinhNBV start modified on May 22 2018: Change saleTransId --> saleTransOrderId
        Long transId = getSequence("SALE_TRANS_ORDER_SEQ");
        SaleTransOrder saleTrans = new SaleTransOrder();
        saleTrans.setSaleTransOrderId(transId);
        saleTrans.setSaleTransId(0L);
        saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransOrderId()));
        //LinhNBV end.

        saleTrans.setSaleTransDate(getSysdate());
        saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_RETAIL);
        if (saleTransForm.getPayMethod() != null
                && saleTransForm.getPayMethod().equals(Constant.SALE_TRANS_PAYMETHOD_EMOLA)) {
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NEW);
        } else {
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
        }
        saleTrans.setCheckStock("0");

        saleTrans.setCustName(saleTransForm.getCustName());
        saleTrans.setCompany(saleTransForm.getCompany());
        saleTrans.setTin(saleTransForm.getTin());


        saleTrans.setShopId(userToken.getShopId());
        saleTrans.setStaffId(userToken.getUserID());
        saleTrans.setReasonId(saleTransForm.getReasonId());
        saleTrans.setPayMethod(saleTransForm.getPayMethod());
        saleTrans.setAmountNotTax(saleTransForm.getAmountBeforeTax());
        saleTrans.setAmountTax(saleTransForm.getAmountAfterTax());
        saleTrans.setTax(saleTransForm.getAmountTax());
        saleTrans.setDiscount(saleTransForm.getAmountDiscount());

        List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
        if (lstGoods != null && !lstGoods.isEmpty()) {
            SaleTransDetailV2Bean bean = (SaleTransDetailV2Bean) lstGoods.get(0);
            saleTrans.setTelecomServiceId(bean.getTelecomServiceId());
            saleTrans.setVat(bean.getVat());
            saleTrans.setCurrency(bean.getCurrency());
        }
        saleTrans.setOrderCode(orderCode);
        saleTrans.setScanPath(pathFileScan);
        saleTrans.setIsCheck(0L);
        this.getSession().save(saleTrans);

        return saleTrans;
    }

    private SaleTransDetail saveSaleTransDetail(SaleTrans saleTrans, SaleTransDetailV2Bean saleTransDetailBean) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTransDetail saleTransDetail = new SaleTransDetail();
        saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_SEQ"));
        saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
        saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return null;
        }
        saleTransDetail.setStockModelId(stockModel.getStockModelId());

        saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
        saleTransDetail.setStockModelName(stockModel.getName());

        saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
        saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());


        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
        if (stockType == null || stockType.getStockTypeId() == null) {
            return null;
        }
        saleTransDetail.setStockTypeId(stockType.getStockTypeId());
        saleTransDetail.setStockTypeCode(stockType.getTableName());
        saleTransDetail.setStockTypeName(stockType.getName());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(saleTransDetailBean.getPriceId());
        if (price == null || price.getPriceId() == null) {
            return null;
        }
        saleTransDetail.setPriceId(price.getPriceId());
        saleTransDetail.setCurrency(price.getCurrency());
        saleTransDetail.setPriceVat(price.getVat());//thue suat
        saleTransDetail.setPrice(price.getPrice());
        saleTransDetail.setAmount(price.getPrice() * saleTransDetailBean.getQuantity());
        saleTransDetail.setQuantity(saleTransDetailBean.getQuantity());//so luong
        saleTransDetail.setDiscountAmount(0.0);//chiet khau

        Double amountAfterTax = 0.0;
        Double amountTax = 0.0;
        Double amountBeforeTax = 0.0;

        if (!Constant.PRICE_AFTER_VAT) {
            amountBeforeTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountTax = amountBeforeTax * price.getVat() / 100.0;
            amountAfterTax = amountBeforeTax + amountTax;
        } else {
            amountAfterTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
            amountTax = amountAfterTax - amountBeforeTax;
        }

        saleTransDetail.setAmountBeforeTax(amountBeforeTax);
        saleTransDetail.setAmountTax(amountTax);
        saleTransDetail.setVatAmount(amountTax);
        saleTransDetail.setAmountAfterTax(amountAfterTax);

        saleTransDetail.setStateId(Constant.STATE_NEW);
        saleTransDetail.setNote(saleTransDetailBean.getNote());

        /*neu khach hang la vip set discountGroupId and discountAmount*/
        if (isSaleToVipCustomer()) {
            saleTransDetail.setDiscountId(saleTransDetailBean.getDiscountId());
            Map<String, Map<Long, Double>> map = (Map<String, Map<Long, Double>>) getTabSession("MapStockModelDiscountAmount");
            Map<Long, Double> mapStockModelDiscount = map.get("mapStockModelDiscount");

            if (mapStockModelDiscount != null && !mapStockModelDiscount.isEmpty()) {
                Double discountAmount = mapStockModelDiscount.get(saleTransDetailBean.getStockModelId());
                if (discountAmount != null) {//chiet khau
                    saleTransDetail.setDiscountAmount(discountAmount);
                }
            }
        }

        this.getSession().save(saleTransDetail);


        //Tru kho GDV
        StockCommonDAO stockCommonDAO = new StockCommonDAO();
        stockCommonDAO.setSession(this.getSession());
        //trung dh3
        //stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW, saleTransDetail.getStockModelId(), saleTransDetail.getQuantity(), true);
        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF,
                saleTransDetail.getStockModelId(), Constant.STATUS_USE, -saleTransDetail.getQuantity(), -saleTransDetail.getQuantity(), 0L,
                userToken.getUserID(), saleTrans.getReasonId(), saleTrans.getSaleTransId(),
                saleTrans.getSaleTransCode(), saleTrans.getSaleTransType(), SourceType.SALE_TRANS);
        boolean noError = genResult.isSuccess();
        if (!noError) {
            return null;
        }


        return saleTransDetail;
    }

    private SaleTransDetailOrder saveSaleTransDetailOrder(SaleTransOrder saleTrans, SaleTransDetailV2Bean saleTransDetailBean) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTransDetailOrder saleTransDetail = new SaleTransDetailOrder();
        saleTransDetail.setSaleTransDetailId(getSequence("SALE_TRANS_DETAIL_ORDER_SEQ"));
        //LinhNBV change saleTransId --> saleTransOrderId
        saleTransDetail.setSaleTransOrderId(saleTrans.getSaleTransOrderId());
        saleTransDetail.setSaleTransId(0L);
        //end.
        saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetailBean.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return null;
        }
        saleTransDetail.setStockModelId(stockModel.getStockModelId());

        saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
        saleTransDetail.setStockModelName(stockModel.getName());

        saleTransDetail.setAccountingModelCode(stockModel.getAccountingModelCode());
        saleTransDetail.setAccountingModelName(stockModel.getAccountingModelName());


        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
        if (stockType == null || stockType.getStockTypeId() == null) {
            return null;
        }
        saleTransDetail.setStockTypeId(stockType.getStockTypeId());
        saleTransDetail.setStockTypeCode(stockType.getTableName());
        saleTransDetail.setStockTypeName(stockType.getName());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        Price price = priceDAO.findById(saleTransDetailBean.getPriceId());
        if (price == null || price.getPriceId() == null) {
            return null;
        }
        saleTransDetail.setPriceId(price.getPriceId());
        saleTransDetail.setCurrency(price.getCurrency());
        saleTransDetail.setPriceVat(price.getVat());//thue suat
        saleTransDetail.setPrice(price.getPrice());
        saleTransDetail.setAmount(price.getPrice() * saleTransDetailBean.getQuantity());
        saleTransDetail.setQuantity(saleTransDetailBean.getQuantity());//so luong
        saleTransDetail.setDiscountAmount(0.0);//chiet khau

        Double amountAfterTax = 0.0;
        Double amountTax = 0.0;
        Double amountBeforeTax = 0.0;

        if (!Constant.PRICE_AFTER_VAT) {
            amountBeforeTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountTax = amountBeforeTax * price.getVat() / 100.0;
            amountAfterTax = amountBeforeTax + amountTax;
        } else {
            amountAfterTax = price.getPrice() * saleTransDetailBean.getQuantity();
            amountBeforeTax = amountAfterTax * 100.0 / (price.getVat() + 100.0);
            amountTax = amountAfterTax - amountBeforeTax;
        }

        saleTransDetail.setAmountBeforeTax(amountBeforeTax);
        saleTransDetail.setAmountTax(amountTax);
        saleTransDetail.setVatAmount(amountTax);
        saleTransDetail.setAmountAfterTax(amountAfterTax);

        saleTransDetail.setStateId(Constant.STATE_NEW);
        saleTransDetail.setNote(saleTransDetailBean.getNote());

        /*neu khach hang la vip set discountGroupId and discountAmount*/
        if (isSaleToVipCustomer()) {
            saleTransDetail.setDiscountId(saleTransDetailBean.getDiscountId());
            Map<String, Map<Long, Double>> map = (Map<String, Map<Long, Double>>) getTabSession("MapStockModelDiscountAmount");
            Map<Long, Double> mapStockModelDiscount = map.get("mapStockModelDiscount");

            if (mapStockModelDiscount != null && !mapStockModelDiscount.isEmpty()) {
                Double discountAmount = mapStockModelDiscount.get(saleTransDetailBean.getStockModelId());
                if (discountAmount != null) {//chiet khau
                    saleTransDetail.setDiscountAmount(discountAmount);
                }
            }
        }

        this.getSession().save(saleTransDetail);


        //Tru kho GDV
//        StockCommonDAO stockCommonDAO = new StockCommonDAO();
//        stockCommonDAO.setSession(this.getSession());
        //trung dh3
        //stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW, saleTransDetail.getStockModelId(), saleTransDetail.getQuantity(), true);
//        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF,
//                saleTransDetail.getStockModelId(), Constant.STATUS_USE, -saleTransDetail.getQuantity(), -saleTransDetail.getQuantity(), 0L,
//                userToken.getUserID(), saleTrans.getReasonId(), saleTrans.getSaleTransId(),
//                saleTrans.getSaleTransCode(), saleTrans.getSaleTransType(), SourceType.SALE_TRANS);
//        boolean noError = genResult.isSuccess();
//        if (!noError) {
//            return null;
//        }
        return saleTransDetail;
    }

    public boolean saveSaleTransSerial(SaleTransDetail saleTransDetail, List<StockTransSerial> lstSerial) throws Exception {
        if (lstSerial == null || lstSerial.isEmpty()) {
            return false;
        }
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetail.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return false;
        }
        Long total = 0L;

        for (StockTransSerial stockSerial : lstSerial) {
            BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
            BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
            Long quantity = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
            total += quantity;
            SaleTransSerial saleTransSerial = new SaleTransSerial();
            saleTransSerial.setSaleTransSerialId(getSequence("SALE_TRANS_SERIAL_SEQ"));
            saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
            saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
            saleTransSerial.setStockModelId(saleTransDetail.getStockModelId());
            saleTransSerial.setFromSerial(stockSerial.getFromSerial());
            saleTransSerial.setToSerial(stockSerial.getToSerial());
            saleTransSerial.setQuantity(quantity);
            this.getSession().save(saleTransSerial);

            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                VcRequestDAO vcRequestDAO = new VcRequestDAO();
                vcRequestDAO.setSession(getSession());
                vcRequestDAO.saveVcRequest(saleTransDetail.getSaleTransDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_RETAIL, saleTransDetail.getSaleTransId());
            }

            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                reqActiveKitDAO.setSession(getSession());
                reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                        Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL), getSysdate());
            }
        }
        if (!saleTransDetail.getQuantity().equals(total)) {
            return false;
        }

        //update stock_good_serial
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        return baseStockDAO.updateSeialExp(lstSerial, saleTransDetail.getStockModelId(),
                Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATUS_USE, Constant.STATUS_SALED, saleTransDetail.getQuantity(), null);

    }

    public boolean saveSaleTransSerialOrder(SaleTransDetailOrder saleTransDetail, List<StockTransSerial> lstSerial) throws Exception {
        if (lstSerial == null || lstSerial.isEmpty()) {
            return false;
        }
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(saleTransDetail.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            return false;
        }
        Long total = 0L;

        for (StockTransSerial stockSerial : lstSerial) {
            BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
            BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
            Long quantity = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
            total += quantity;
            SaleTransSerialOrder saleTransSerial = new SaleTransSerialOrder();
            saleTransSerial.setSaleTransSerialId(getSequence("SALE_TRANS_SERIAL_ORDER_SEQ"));
            saleTransSerial.setSaleTransDetailId(saleTransDetail.getSaleTransDetailId());
            saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
            saleTransSerial.setStockModelId(saleTransDetail.getStockModelId());
            saleTransSerial.setFromSerial(stockSerial.getFromSerial());
            saleTransSerial.setToSerial(stockSerial.getToSerial());
            saleTransSerial.setQuantity(quantity);
            this.getSession().save(saleTransSerial);

//            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
//                VcRequestDAO vcRequestDAO = new VcRequestDAO();
//                vcRequestDAO.setSession(getSession());
//                //LinhNBV change saleTransId --> saleTransOrderId
//                vcRequestDAO.saveVcRequest(saleTransDetail.getSaleTransDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_RETAIL, saleTransDetail.getSaleTransOrderId());
//            }

            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                reqActiveKitDAO.setSession(getSession());
                //LinhNBV change saleTransId --> saleTransOrderId
                reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransOrderId(),
                        Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL), getSysdate());
            }
        }
        if (!saleTransDetail.getQuantity().equals(total)) {
            return false;
        }

        return true;
        //update stock_good_serial
//        BaseStockDAO baseStockDAO = new BaseStockDAO();
//        baseStockDAO.setSession(this.getSession());
//        return baseStockDAO.updateSeialExp(lstSerial, saleTransDetail.getStockModelId(),
//                Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATUS_USE, Constant.STATUS_SALED, saleTransDetail.getQuantity(), null);

    }

    /**
     * selectReasonSale
     *
     * @return
     */
    public String selectReasonSale() {
        HttpServletRequest req = getRequest();
        String pageForward = SALE_TO_RETAIL_DETAIL;
        try {
            if (this.saleTransForm.getReasonId() != null) {
                String reasonId = this.saleTransForm.getReasonId().toString();
                System.out.println("reasonId= " + reasonId);

                //Kiem tra xem co ban cho khach hang VIP khong
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(this.getSession());

                List<AppParams> listReasonId = appParamsDAO.findAppParamsByType_1(Constant.APP_PARAMS_REASON_SALE_DEFAULT_VIP_CUSTOMER);
                if (listReasonId != null && !listReasonId.isEmpty()) {
                    String reasonSaleForVIP = listReasonId.get(0).getCode();
                    System.out.println("reasonSaleForVIP= " + reasonSaleForVIP);
                    List<SaleTransDetailV2Bean> lstGoods = (List) getTabSession(SESSION_LIST_GOODS);
                    //Neu ban cho khach hang vip
                    //tinh lai gia tri hang hoa neu co
                    //hien thi giao dien ban cho khach hang vip                    
                    if (reasonSaleForVIP != null && reasonSaleForVIP.equals(reasonId)) {
                        if (lstGoods != null && !lstGoods.isEmpty()) {
                            //set chiet khau cho khach hang vip
                            for (Iterator<SaleTransDetailV2Bean> it = lstGoods.iterator(); it.hasNext();) {
                                SaleTransDetailV2Bean saleTransDetailV2Bean = it.next();
                                saleTransDetailV2Bean.setDiscountGroupId(getDiscountGroupIdVip(saleTransDetailV2Bean));
                            }
                            this.sumSaleTransAmountVip(lstGoods);
                            setTabSession(SESSION_LIST_GOODS, lstGoods);
                        }
                        req.getSession().setAttribute("isVipCustomer", "true");
                    } else {
                        if (lstGoods != null && !lstGoods.isEmpty()) {
                            this.sumSaleTransAmount(lstGoods);
                            setTabSession(SESSION_LIST_GOODS, lstGoods);
                        }
                        req.getSession().setAttribute("isVipCustomer", "false");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pageForward;
    }

    /**
     * getOTP
     *
     * @return
     */
    public String getOTP() {
        HttpServletRequest req = getRequest();
        String pageForward = GET_CUST_OTP;
        //Validate du lieu gui len tu form
        if ((saleTransForm.getReasonId() == null) || (saleTransForm.getReasonId().equals(0L))) {
            req.setAttribute("returnMsgOtp", "saleRetail.warn.reason");
            return pageForward;
        }

        if (saleTransForm.getCustMobile() == null || saleTransForm.getCustMobile().isEmpty()) {
            req.setAttribute("returnMsgOtp", "saleRetail.warn.custMobile");
            return pageForward;
        }

        saleTransForm.setCustMobile(saleTransForm.getCustMobile().trim());
        //Kiem tra sdt co la thue bao VIP hay khong
        if (!checkVipCustomer(saleTransForm.getCustMobile())) {
            req.setAttribute("returnMsgOtp", "saleRetail.warn.NotVip");
            return pageForward;
        }
        //Kiem tra thue bao VIP da ton tai giao dich VIP trong thang hay chua
        if (checkExistedTransInMonthVIPCust(saleTransForm.getCustMobile())) {
            req.setAttribute("returnMsgOtp", "saleRetail.warn.existedTransVIP");
            return pageForward;
        }

        //Gui OTP ve cho khach hang
        if (!sendOTPForVipCustomer(saleTransForm.getCustMobile())) {
            req.setAttribute("returnMsgOtp", "saleRetail.warn.sendOTPFail");
            return pageForward;
        }

        req.setAttribute("returnMsgOtp", "msg.retail.getotp");
        return pageForward;
    }

    /**
     * checkVipCustomer
     *
     * @param custMobile
     * @return
     */
    private boolean checkVipCustomer(String custMobile) {
        HttpServletRequest req = getRequest();
        try {
            Session session = getSession(Constant.DB_PRIVILEGE);
            String sql = "select 1 from subscriber where class_id <> 'N' " //mvt: <> 'N'
                    + " and status = '2' and isdn = ? ";
            Query q = session.createSQLQuery(sql);
            q.setParameter(0, StringUtils.convertToISDN(custMobile));
            List list = q.list();
            if (list != null && !list.isEmpty()) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, getText("saleRetail.warn.checkVip"));
        }
        return false;
    }

    /**
     * checkExistedTransInMonthVIPCust
     *
     * @param msisdn
     * @return
     */
    private boolean checkExistedTransInMonthVIPCust(String msisdn) {
        HttpServletRequest req = getRequest();
        try {
            Session session = getSession();
            //QuocDM1: Bo sung cau hinh dong so luong giao dich ban cho VIP dc phep
            //Bo sung join them bang SaleTrans de lay ra giao dich huy
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(session);

            AppParams app = appParamsDAO.findAppParams("NUMBER_TRANSACTION_VIP_ALLOW", "NUMBER_TRANSACTION_VIP_ALLOW");

            String sql = "from SaleTransVip stv, SaleTrans st where stv.saleTransId = st.saleTransId "
                    + " and stv.msisdn = ? and stv.saleTransDate >= ? and stv.saleTransDate <= ? and stv.saleTransType = ? and st.status <> 4 ";

//            String sql = "from SaleTransVip where msisdn = ? and saleTransDate >= ? and saleTransDate <= ? and saleTransType = ?";
            Query q = session.createQuery(sql);
            q.setParameter(0, StringUtils.convertToMsisdn(msisdn));
            Date startDate = DateTimeUtils.getStartOfDay(DateTimeUtils.getDateWithDayFirstMonth(new Date()));
            q.setParameter(1, startDate);
            q.setParameter(2, new Date());
            q.setParameter(3, Constant.SALE_TRANS_TYPE_RETAIL);
            List list = q.list();
            if (list != null && !list.isEmpty() && list.size() >= Long.valueOf(app.getValue())) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(Constant.RETURN_MESSAGE, getText("saleRetail.warn.checkExistedTransVIP"));
        }
        return false;
    }

    /**
     * sendOTPForVipCustomer
     *
     * @param msisdn
     * @return
     */
    private boolean sendOTPForVipCustomer(String msisdn) {
        Session session = null;
        try {
            //sinh ma otp cho khach hang
            String otp = NumberUtils.randomString(6);

            //send sms cho khach hang
            String content = getText("msg.retail.getotp.sms");
            content = content.replace("{0}", otp);

            SmsClient.sendSMS155(msisdn, content);

            //luu thong tin otp vao trong bang sale_trans_vip_otp
            session = getSession();
            msisdn = StringUtils.convertToMsisdn(msisdn);
            SaleTransVipOtp saleTransVipOtp = (SaleTransVipOtp) session.get(SaleTransVipOtp.class, msisdn);
            if (saleTransVipOtp == null) {
                saleTransVipOtp = new SaleTransVipOtp();
            }
            saleTransVipOtp.setMsisdn(msisdn);
            saleTransVipOtp.setOtp(encryptPassword(otp));
            saleTransVipOtp.setCreateDateOtp(new Date());

            session.saveOrUpdate(saleTransVipOtp);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null && session.isOpen()) {
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
            }
        }
        return false;
    }

    /**
     * getDiscountGroupIdVip
     *
     * @param bean
     */
    private Long getDiscountGroupIdVip(SaleTransDetailV2Bean bean) {
        try {
            String discountPolicy = "";
            List<AppParams> listDiscountPolicy = new ArrayList<AppParams>();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            listDiscountPolicy = appParamsDAO.findAppParamsByType_1(Constant.APP_PARAMS_DISCOUNT_POLICY_DEFAULT_VIP_CUSTOMER);
            if (listDiscountPolicy != null && !listDiscountPolicy.isEmpty()) {
                discountPolicy = listDiscountPolicy.get(0).getCode();
                SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
                saleCommonDAO.setSession(this.getSession());
                return saleCommonDAO.getDiscountGroupId(bean.getStockModelId(), discountPolicy);//them nhom chiet khau doi voi giao dich ban cho khach hang vip
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    /**
     * sumSaleTransAmountVip
     *
     * @param lstGoods
     */
    private void sumSaleTransAmountVip(List<SaleTransDetailV2Bean> lstGoods) {
        SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
        saleCommonDAO.setSession(this.getSession());
        Map<String, Map<Long, Double>> map = saleCommonDAO.sumDiscount(req, lstGoods, this.getSaleTransForm());
        setTabSession("MapStockModelDiscountAmount", map);
    }

    /**
     * isSaleToVipCustomer
     *
     * @return
     */
    private boolean isSaleToVipCustomer() {
        if (this.saleTransForm != null) {
            String reasonIdVip = getReasonIdVipCustomer();
            if (reasonIdVip != null && !reasonIdVip.isEmpty()) {
                if (this.saleTransForm.getReasonId() != null && this.saleTransForm.getReasonId().equals(Long.valueOf(reasonIdVip))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * getReasonIdVipCustomer
     *
     * @return
     */
    private String getReasonIdVipCustomer() {
        try {
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());

            List<AppParams> listReason = appParamsDAO.findAppParamsByType_1(Constant.APP_PARAMS_REASON_SALE_DEFAULT_VIP_CUSTOMER);
            if (listReason != null && !listReason.isEmpty()) {
                return listReason.get(0).getCode();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * validateOTPVipCustomer
     *
     * @param msisdn
     * @param otp
     * @return
     */
    private String validateOTPVipCustomer(String msisdn, String otp) {
        try {
            //check vip customer
            if (!checkVipCustomer(msisdn)) {
                return "saleRetail.warn.NotVip";
            }

            //Lay thong tin khach hang trong bang sale_trans_vip_otp
            SaleTransVipOtp saleTransVipOtp = (SaleTransVipOtp) this.getSession().get(SaleTransVipOtp.class, StringUtils.convertToMsisdn(msisdn));
            if (saleTransVipOtp != null) {
                //kiem tra otp da dung chua
                String encryptOTP = encryptPassword(otp);
                if (!saleTransVipOtp.getOtp().equals(encryptOTP)) {
                    return "saleRetail.warn.invalidOtp";
                }

                //Lay thoi gian otp expire
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(this.getSession());
                List<AppParams> listTimeOTP = appParamsDAO.findAppParamsByType_1(Constant.APP_PARAMS_TIME_EXPIRE_OTP);
                if (listTimeOTP != null && !listTimeOTP.isEmpty()) {
                    long timeExpireOtp = Long.valueOf(listTimeOTP.get(0).getValue());
                    if ((Calendar.getInstance().getTimeInMillis() - saleTransVipOtp.getCreateDateOtp().getTime()) > (timeExpireOtp * 60 * 1000)) {
                        return "saleRetail.warn.otpExpire";
                    }
                }
            } else {
                return "saleRetail.warn.NotVip";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param saleTrans
     * @return
     * @throws Exception
     */
    private SaleTransVip saveSaleTransVip(SaleTrans saleTrans) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        SaleTransVip saleTransVip = new SaleTransVip();
        saleTransVip.setSaleTransVipId(getSequence("SALE_TRANS_VIP_SEQ"));
        saleTransVip.setSaleTransId(saleTrans.getSaleTransId());
        saleTransVip.setMsisdn(StringUtils.convertToMsisdn(this.saleTransForm.getCustMobile().trim()));
        saleTransVip.setStaffId(saleTrans.getStaffId());
        saleTransVip.setShopId(saleTrans.getShopId());
        saleTransVip.setSaleTransDate(saleTrans.getSaleTransDate());
        saleTransVip.setSaleTransType(Constant.SALE_TRANS_TYPE_RETAIL);

        //Lay thong tin khach hang tu he thong privileg
        Session privilege = getSession(Constant.DB_PRIVILEGE);
        String sql = "select isdn, name, birthday, id_no idNo, precinct_code precinctCode, district_code districtCode, province_code provinceCode ";
        sql += " from subscriber";
        sql += " where class_id <> 'N' and status = '2'"; //MVT <> 'N' VN <> 'D'
        sql += " and isdn = ? ";
        Query q = privilege.createSQLQuery(sql)
                .addScalar("isdn", Hibernate.STRING)
                .addScalar("name", Hibernate.STRING)
                .addScalar("birthday", Hibernate.TIMESTAMP)
                .addScalar("precinctCode", Hibernate.STRING)
                .addScalar("districtCode", Hibernate.STRING)
                .addScalar("provinceCode", Hibernate.STRING)
                .setResultTransformer(Transformers.aliasToBean(CustInforBean.class));
        q.setParameter(0, StringUtils.convertToISDN(this.saleTransForm.getCustMobile()));
        List<CustInforBean> lstCustInforBeans = q.list();
        if (lstCustInforBeans != null && !lstCustInforBeans.isEmpty()) {
            CustInforBean custInforBean = lstCustInforBeans.get(0);
            saleTransVip.setIdNo(custInforBean.getIdNo());
            saleTransVip.setCustName(custInforBean.getName());
            saleTransVip.setBirthday(custInforBean.getBirthday());
            saleTransVip.setPrecinctCode(custInforBean.getPrecinctCode());
            saleTransVip.setDistrictCode(custInforBean.getDistrictCode());
            saleTransVip.setProvinceCode(custInforBean.getProvinceCode());
        }

        this.getSession().save(saleTransVip);
        return saleTransVip;
    }

    /**
     *
     * @param password
     * @return
     */
    private String encryptPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (Exception e) {
        }
        try {
            md.update(password.getBytes("UTF-8"));
        } catch (Exception e) {
        }
        byte raw[] = md.digest();
        String hash = (new BASE64Encoder()).encode(raw);
        return hash;
    }
    /*
     public boolean checkUserOfSH(Session session, Long staff_id ) throws Exception {
        
     String strQuery = " Select count(*) countNumber from staff st WHERE   st.staff_id = ? AND EXISTS (SELECT 'x' FROM vsa.users a "
     + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS "
     + " (SELECT 'x' FROM vsa.role_user WHERE user_id = a.user_id "
     + " AND role_id  IN (SELECT role_id FROM vsa.roles WHERE Role_Code IN ('SH_TRANS_STAFF_IM')))) ";
     SQLQuery query = session.createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
     query.setParameter(0, staff_id);
     Long count = (Long) query.list().get(0);
     if (count <= 0) {
     return false;
     }
        
     return true;
     }
     */
    /*
     public List<Shop> listShopOfBranch(Session session, String param) throws Exception {
        
     List<Shop> listShop = new ArrayList<Shop>();
     String strQuery = " SELECT Shop_Code AS shopCode, Shop_Id AS shopId FROM shop WHERE shop_Id IN (SELECT  sh.shop_id FROM shop sh " +
     " WHERE sh.status = 1 " +
     " START WITH sh.shop_code IN " +param+ " CONNECT BY PRIOR sh.shop_id = sh.parent_shop_id )";
        
     Query query = session.createSQLQuery(strQuery)
     .addScalar("shopCode", Hibernate.STRING)
     .addScalar("shopId", Hibernate.LONG)
     .setResultTransformer(Transformers.aliasToBean(Shop.class));
        
     listShop = query.list();
        
     return listShop;
     }
     */

    //Ham xuat ra excel
    public String expOrderToExcel(List<SaleTransDetailV2Bean> lstGoods, SaleTransForm saleToCollaboratorForm) throws Exception {
        logdetail.debug("# Begin method expOrderToExcel");
        HttpServletRequest req = this.getRequest();
        String pathFilePDF = null;
//        form.setExportUrl("");
//        saleToCollaboratorForm.setex
//        String pageForward = searchInvoice();
        if (lstGoods != null) {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            SimpleDateFormat hoursFormat = new SimpleDateFormat("yyyyMMddhh24mmss");
            String dateTime = hoursFormat.format(new Date());

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "list_order_retail.xls";
            String fileName = "list_order_" + userToken.getLoginName().toLowerCase() + "_" + dateTime;
            String fileType = ".xls";
            String fullFileName = fileName + fileType;
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + fullFileName;

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
//            local
//            String realPath = filePath;
//            with server
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            Map beans = new HashMap();
            beans.put("list", lstGoods);
            beans.put("userToken", userToken);
//            double sum =0;
//            for(int i=0;i< lstGoods.size() ; i++){
//                sum += lstGoods.get(i).getDiscout() + Double.parseDouble(lstGoods.get(i).getAmountMoney());
//            }
//            beans.put("sum", sum);
            Date date = new Date();
            beans.put("sysDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            beans.put("saleToCollaboratorForm", saleToCollaboratorForm);
            XLSTransformer transformer = new XLSTransformer();
            System.out.println("--------------templateRealPath : " + templateRealPath);
            System.out.println("--------------realPath : " + realPath);

            transformer.transformXLS(templateRealPath, beans, realPath);
            System.out.println("--------------transformer : ");
            //            with file excel
            pathFilePDF = realPath;

//            form.setExportUrl(filePath);
            // with server file PDF
//            String srcFile = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE")+ fileName +".pdf";
//            String pathFilePDFReal = req.getSession().getServletContext().getRealPath(srcFile);
//            
//            System.out.println("--------------in here : ");
//            System.out.println("--------------realPath : " + realPath);
//            System.out.println("--------------in here : " + pathFilePDFReal);
//            pathFilePDF = excel2pdf(realPath, pathFilePDFReal);
//            System.out.println("--------------ipathFilePDFe : " + pathFilePDF);
        }

        logdetail.debug("# End method expOrderToExcel");
        return pathFilePDF;
    }

    //tannh20180120 Ham convert excel to pdf
//      public String excel2pdf(String realPath,String pathFilePDFReal)    {
//        try {
//              System.out.println("--------------excel2pdf: ");
//            System.out.println("--------------realPath : " + realPath);
//            System.out.println("--------------in here : " + pathFilePDFReal);
//        Workbook workbook = new Workbook(realPath);
//       
//          System.out.println("--------------sau workbook : " + realPath);
////        pathFilePDF = srcFile;
//        //with server
//       
//        //Save the document in PDF format
//        workbook.save(pathFilePDFReal, SaveFormat.PDF);
//         System.out.println("--------------sau workbook.save(pathFilePDFReal : " + pathFilePDFReal);
////        pdfWatermark(pathFilePDF, fileName);
//        
//        } catch (Exception ex) {
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
