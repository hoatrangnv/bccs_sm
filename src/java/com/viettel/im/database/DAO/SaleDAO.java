package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.common.ObjectClientChannel;
import com.viettel.common.ViettelMsg;
import com.viettel.common.ViettelService;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.*;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.InvoiceUsedDetailBean;
import com.viettel.im.client.bean.StockTransBean;
import com.viettel.im.client.form.SaleForm;
import com.viettel.im.client.form.SaleToCollaboratorForm;
import com.viettel.im.client.form.SaleTransForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Discount;
import com.viettel.im.database.BO.DiscountGroup;
import com.viettel.im.database.BO.DiscountModelMap;
import com.viettel.im.database.BO.ErrorChangeChannelBean;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.hibernate.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import com.viettel.common.ViettelMsg;
import com.viettel.common.ObjectClientChannel;

/**
 *
 * @author tamdt1 date 11/03/2009 thuc hien tat ca cac tac vu lien quan den quan
 * ly ban hang
 *
 */
public class SaleDAO extends BaseDAOAction {

    private List listItemsSelectBox = new ArrayList();
    private static final long serialVersionUID = 1L;
    private final String REQUEST_FILE_PATH = "requestFilePath";
    private final String REQUEST_FILE_MESSAGE = "requestFileMessage";
    //bien form
    private SaleForm saleForm = new SaleForm();

    public String getPageForward() {
        return pageForward;
    }

    public void setPageForward(String pageForward) {
        this.pageForward = pageForward;
    }

    public SaleForm getSaleForm() {
        return saleForm;
    }

    public void setSaleForm(SaleForm saleForm) {
        this.saleForm = saleForm;
    }
    //
    //----------------------------------------------------------------
    //tamdt1 - start
    private static final Log log = LogFactory.getLog(SaleDAO.class);
    private String pageForward;    //dinh nghia cac hang so pageForward
    public static final String SALE_FROM_EXP_COMMAND = "saleFromExpCommand";
    public static final String DESTROY_SALE_INVOICE = "destroySaleInvoice";
    public static final String CHANGE_DAMAGED_GOODS = "changeDamagedGoods";
    private String userId = null;
    private String accountId = null;
    private String taxId = null;
    private String addressId = null;
    private String sellDate = null;
    private String reasonsId = null;
    private String paramesId = null;
    private String messageChangeModel = null;
    private List<Shop> listShops = new ArrayList<Shop>();
    private List<Reason> listReasons = new ArrayList<Reason>();
    private List<AppParams> paramsest = new ArrayList<AppParams>();
    private List<StockTransBean> listStockTrans = null;
    public static final long PAY_STATUS = 0L;
    public static final long OWNER_TYPE = 1L;
    public static final long CHECK_DEPOSIT = 0L;
    public static final String DISCOUNT_TYPE = "1";
    public static final String TYPE_APPS = "PRICE_TYPE";
    public static final String CODE_APPS = "5";
    public static final long PRICE_STATUS_ACTICE = 1L;
    public static final Long STOCK_MODEL_STATUS_ACTIVE = 1L;
    private Double totalPriceRate = 0.0;
    private Double totalPrice = 0.0;
    private Double totalPriceAfterRate = 0.0;
    private Double totalTaxMoney = 0.0;
    private Double totalNotTaxMoney = 0.0;
    private String startDate = null;
    private String endDate = null;
    private String shopId = null;
    private String shopCode = "";
    private String shopName = "";
    private long idShop = 0L;
    //Tien chiet khau truoc thue
    private Double discountNotTax;
    private List<StockTransDetail> listTransDetails = null;
    private List<DiscountBean> listModelMaps = null;
    public static final String CHANEL_TYPE_OBJECT_TYPE = "1";
    public static final String CHANEL_TYPE_IS_VT_UNIT = "2";
    public static final String REASION_TYPE = "SALE_EXP";
    public static final String APP_PARAMES_TYPE = "PAY_METHOD";
    public static final Long STOCK_TRANS_ACTION_ACTION_TYPE = 1L;
    public static final Long STOCK_TRANS_TYPE_EXPORT = 1L;
    public static final Long STOCK_TRANS_STATUS = 1L;
    public static final Long STOCK_TRANS_ACTION_ACTION_TYPE_RECOVER = 2L;
    public static final Long STOCK_TRANS_TYPE_EXPORT_RECOVER = 2L;

    public Double getDiscountNotTax() {
        return discountNotTax;
    }

    public void setDiscountNotTax(Double discountNotTax) {
        this.discountNotTax = discountNotTax;
    }
    //private int GOODS_NO_DEFINE_VAT=1;

    public List getListItemsSelectBox() {
        return listItemsSelectBox;
    }

    public void setListItemsSelectBox(List listItemsSelectBox) {
        this.listItemsSelectBox = listItemsSelectBox;
    }
    /////////////////////////

    public List<Reason> getListReasons() {
        return listReasons;
    }

    public void setListReasons(List<Reason> listReasons) {
        this.listReasons = listReasons;
    }

    public List<StockTransBean> getListStockTrans() {
        return listStockTrans;
    }

    public List<Shop> getListShops() {
        return listShops;
    }

    public void setListShops(List<Shop> listShops) {
        this.listShops = listShops;
    }

    public void setListStockTrans(List<StockTransBean> listStockTrans) {
        this.listStockTrans = listStockTrans;
    }

    public List<AppParams> getParamsest() {
        return paramsest;
    }

    public void setParamsest(List<AppParams> paramsest) {
        this.paramsest = paramsest;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMessageChangeModel() {
        return messageChangeModel;
    }

    public void setMessageChangeModel(String messageChangeModel) {
        this.messageChangeModel = messageChangeModel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getParamesId() {
        return paramesId;
    }

    public void setParamesId(String paramesId) {
        this.paramesId = paramesId;
    }

    public String getReasonsId() {
        return reasonsId;
    }

    public void setReasonsId(String reasonsId) {
        this.reasonsId = reasonsId;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DiscountBean> getListModelMaps() {
        return listModelMaps;
    }

    public void setListModelMaps(List<DiscountBean> listModelMaps) {
        this.listModelMaps = listModelMaps;
    }

    public List<StockTransDetail> getListTransDetails() {
        return listTransDetails;
    }

    public void setListTransDetails(List<StockTransDetail> listTransDetails) {
        this.listTransDetails = listTransDetails;
    }
    private Map<String, String> listPrices = null;

    public Map<String, String> getListPrices() {
        return listPrices;
    }

    public void setListPrices(Map<String, String> listPrices) {
        this.listPrices = listPrices;
    }
    private String totalModels = null;

    public String getTotalModels() {
        return totalModels;
    }

    public void setTotalModels(String totalModels) {
        this.totalModels = totalModels;
    }
    private String discountId = null;

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }
    private String sTransId = null;

    public String getSTransId() {
        return sTransId;
    }

    public void setSTransId(String sTransId) {
        this.sTransId = sTransId;
    }
    public Map<String, String> mapStockModels = null;

    public Map<String, String> getMapStockModels() {
        return mapStockModels;
    }

    public void setMapStockModels(Map<String, String> mapStockModels) {
        this.mapStockModels = mapStockModels;
    }
    private Map<String, List<String>> mapStockModelDiscount = null;

    public Map<String, List<String>> getMapStockModelDiscount() {
        return mapStockModelDiscount;
    }

    public void setMapStockModelDiscount(Map<String, List<String>> mapStockModelDiscount) {
        this.mapStockModelDiscount = mapStockModelDiscount;
    }
    private Map<String, String> mapStockModelQuanity = null;

    public Map<String, String> getMapStockModelQuanity() {
        return mapStockModelQuanity;
    }

    public void setMapStockModelQuanity(Map<String, String> mapStockModelQuanity) {
        this.mapStockModelQuanity = mapStockModelQuanity;
    }
    private Map<String, String> mapTotalModel = null;

    public Map<String, String> getMapTotalModel() {
        return mapTotalModel;
    }

    public void setMapTotalModel(Map<String, String> mapTotalModel) {
        this.mapTotalModel = mapTotalModel;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPriceAfterRate() {
        return totalPriceAfterRate;
    }

    public void setTotalPriceAfterRate(Double totalPriceAfterRate) {
        this.totalPriceAfterRate = totalPriceAfterRate;
    }

    public Double getTotalPriceRate() {
        return totalPriceRate;
    }

    public void setTotalPriceRate(Double totalPriceRate) {
        this.totalPriceRate = totalPriceRate;
    }
    Map<Long, Double> MapStockModelDiscountAmount = new HashMap<Long, Double>();
    Map<Long, Long> MapStockModelDiscountGroup = new HashMap<Long, Long>();
    Map<Long, Long> MapStockModelDiscount = new HashMap<Long, Long>();
    Map<Long, Double> MapTotalSingle = new HashMap<Long, Double>();
    Map<Long, Double> MapCK = new HashMap<Long, Double>();
    Map<Long, Double> MapMoney = new HashMap<Long, Double>();
    Map<Long, Long> MapVat = new HashMap<Long, Long>();
    Map<Integer, Long> mapDiscountGroupId = new HashMap<Integer, Long>();

    /**
     *
     * author tuannv date: 28/04/2009 Xoa di cac chiet khau khong dung cho mat
     * hang
     *
     */
    public List<DiscountBean> delDiscount(List<DiscountBean> discountModelMaps, List<DiscountModelMap> listDiscountGroup, Long amount, Long checkExisted) throws Exception {
        List<DiscountBean> dcModelMaps = new ArrayList(discountModelMaps);


        try {

            //Tien chưa thue
            if (discountModelMaps != null) {
                for (int j = 0; j < discountModelMaps.size(); j++) {
                    DiscountBean discountBean = (DiscountBean) discountModelMaps.get(j);
                    Long checkExist = -1L;
                    for (int i = 0; i < listDiscountGroup.size(); i++) {

                        Long discountGroupId = listDiscountGroup.get(i).getDiscountGroupId();
                        Long stockModelId = listDiscountGroup.get(i).getStockModelId();
                        if (discountBean.getStockModelId().equals(stockModelId)) {
                            if (discountBean.getDiscountGroupId().equals(discountGroupId)) {

                                checkExist = 1L;
                                break;

                            } else {//bo qua
                                checkExist = 0L;
                                break;

                            }


                        }
                    }
                    if (checkExist.equals(0L)) {//Neu ko thuoc nhom ck cua cua hang thi xoa

                        dcModelMaps.remove(discountBean);

                    }

                    if (checkExisted.equals(1L)) {
                        if (checkExist.equals(1L)) {//Neu thuoc se kiem tra khoang gia tri cua mat hang
                            Discount discountBO = getDiscountId(discountBean.getDiscountId(), amount);
                            if (discountBO == null) {//khong ap dung chinh sach chiet khau duoc
                                dcModelMaps.remove(discountBean);
                            }

                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dcModelMaps;
    }

    /**
     *
     * author tuannv date: 28/04/2009 Tinh tien trước thue
     *
     */
    public Long sumAmountNotTax(
            List<StockTransDetail> listDetails) throws Exception {
        //Tien chưa thue
        if (listDetails != null) {

            Double AmountNotTax = 0.0;

            for (int i = 0; i < listDetails.size(); i++) {
                //AmountNotTax = 0.0;
                StockTransDetail gF = (StockTransDetail) listDetails.get(i);
                Price price = gF.getPrices().get(0);
                PriceDAO priceDAO = new PriceDAO();

                //Tim kiem danh sach nhom chiet khau cua mat hang
                Double totalSingle = 0.0;
                if (MapTotalSingle.containsKey(gF.getStockModelId())) {
                    totalSingle = MapTotalSingle.get(gF.getStockModelId());
                } else {
                    totalSingle = 0.0;
                }

                priceDAO.setSession(this.getSession());
                Price price1 = priceDAO.findById(price.getPriceId());

                //sum Tax
                if (price1 != null) {
                    Double Sum = (price1.getPrice().doubleValue() * gF.getQuantityRes().doubleValue()) - totalSingle.doubleValue();
                    Double NotTax = 0.0;
                    if (price1.getVat() != null) {
                        NotTax = Sum / (1 + (price1.getVat().doubleValue() / 100));
                    }

                    AmountNotTax += NotTax;
                    Long Tax = Math.round(Sum - NotTax);
                    MapVat.put(gF.getStockModelId(), Tax);
//
//                    Double Tax = (price1.getPrice().doubleValue() * gF.getQuantityRes().doubleValue());
//                    Tax -= totalSingle.doubleValue();
//                    if (price1.getVat() != null) {
//                        Tax = Tax / (1 + (price1.getVat().doubleValue() / 100));
//                    }
//                    AmountNotTax += Tax;
                }

            }
            return Math.round(AmountNotTax);// - totalSingle;
        } else {
            return 0L;
        }

    }

    /**
     *
     * author tuannv1 date: 16/04/2009 Lay ve thong tin chiet khau
     *
     */
    private Discount getDiscountId(Long discountId, Long discountAmmount) throws Exception {
        List listDiscount = new ArrayList();

        String strQuery = "from Discount where fromAmount< ? and toAmount>=? and discountId=? and status=1";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, discountAmmount);
        query.setParameter(1, discountAmmount);
        query.setParameter(2, discountId);

//        query.setParameter(3, DateTimeUtils.convertStringToDateTime(curDate.trim() + " 23:59:59"));
//
//        query.setParameter(4, DateTimeUtils.convertStringToDateTime(curDate.trim() + " 00:00:00"));

        listDiscount = query.list();
        if (listDiscount.size() > 0) {
            return (Discount) listDiscount.get(0);
        }

        return null;
    }

    public void setResetForSellAgent() {
        setAccountId("");
        setReasonsId("");
        setParamesId("");
        setUserId("");
        setTaxId("");
        setAddressId("");
        setShopId("");
        setSellDate("");
        setStartDate("");
        setEndDate("");
    }

    public String searchSaleAction() throws Exception {
        log.info("Begin method searchSaleAction of SaleDAO ...");

        HttpServletRequest req = getRequest();

        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward =
                Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                String agentId = getShopId();
                String shopCodeSearch = getShopCode();
                //kiem tra shop co ton tai hay ko                
                List listParameter2 = new ArrayList();
                StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.database.BO.Shop(a.shopId, a.shopCode) ");
                strQuery2.append("from Shop a, ");
                strQuery2.append(" ChannelType chty ");
                strQuery2.append("where 1 = 1 ");
                strQuery2.append(" AND chty.channelTypeId = a.channelTypeId ");
                strQuery2.append(" AND a.status = ? ");
                listParameter2.add(Constant.STATUS_ACTIVE);
                strQuery2.append(" AND ");
                strQuery2.append(" chty.objectType = ? ");
                listParameter2.add(CHANEL_TYPE_OBJECT_TYPE);
                strQuery2.append(" AND ");
                strQuery2.append(" chty.isVtUnit = ? ");
                listParameter2.add(CHANEL_TYPE_IS_VT_UNIT);
                if (shopCodeSearch != null && !shopCodeSearch.trim().equals("")) {
                    strQuery2.append("and lower(a.shopCode) = ? ");
                    listParameter2.add(shopCodeSearch.trim().toLowerCase());
                }
                strQuery2.append(" and a.parentShopId = ? ");
                listParameter2.add(userToken.getShopId());
                strQuery2.append("and rownum < ? ");
                listParameter2.add(300L);

                strQuery2.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

                Query query2 = getSession().createQuery(strQuery2.toString());
                for (int i = 0; i < listParameter2.size(); i++) {
                    query2.setParameter(i, listParameter2.get(i));
                }

                List<Shop> tmpList2 = query2.list();
                if (tmpList2 != null && tmpList2.size() > 0) {
                    agentId = tmpList2.get(0).getShopId().toString();
                } else {
                    agentId = "0";
                }

                String sDate = getStartDate();
                String eDate = getEndDate();

                if ((agentId != null) && (!agentId.equals(""))) {
                    boolean find = true;
                    setDataForSaleAction(PAY_STATUS, OWNER_TYPE, agentId, sDate, eDate, userToken.getShopId(), find);

                    //Tuannv, bo sung phan hien thi ma so thue va dia chi khi tim kiem lenh xuat cua dai ly

                    displayTinAddress(Long.parseLong(agentId), Constant.STATUS_ACTIVE);
                }

                pageForward = SALE_FROM_EXP_COMMAND;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareSaleFromExpCommand of SaleDAO");

        return pageForward;
    }

    /**
     *
     * date: 29/04/2009 hien thi ma so thue va dia chi khi tim kiem lenh xuat
     * cua dai ly
     *
     */
    public List<Shop> displayTinAddress(Long ShopId, Long status) throws Exception {
        try {
            Query query = getSession().createQuery(" from Shop sh where sh.shopId = ? and sh.status=?");
            query.setLong(0, ShopId);
            query.setLong(1, status);

            for (int i = 0; i < query.list().size(); i++) {
                Shop sh = (Shop) query.list().get(i);
                setTaxId(sh.getTin());
                setAddressId(sh.getAddress());
            }

            return query.list();

        } catch (HibernateException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

    }

    private void setDataForSaleAction(Long payStatus, Long ownerType, String shopId, String sDate, String eDate, Long parentShopId, boolean find) throws Exception {
        try {

            ReasonDAO reasonDAO = new ReasonDAO();
            AppParamsDAO paramsDAO = new AppParamsDAO();
            ShopDAO shopDAO = new ShopDAO();
            StockTransDAO stockTransDAO = new StockTransDAO();

            reasonDAO.setSession(getSession());
            paramsDAO.setSession(getSession());
            shopDAO.setSession(getSession());
            stockTransDAO.setSession(getSession());

            shopDAO.setIsVTUnitFilter(CHANEL_TYPE_IS_VT_UNIT);
            shopDAO.setObjectTypeFilter(CHANEL_TYPE_OBJECT_TYPE);

            List<Shop> shops = shopDAO.findAgentShop(parentShopId, 1L);

            //MrSun
//            ReasonDAO reasonDAO = new ReasonDAO();
//            reasonDAO.setSession(getSession());
            Long[] saleTransType = new Long[1];
            saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_AGENT);
            List<Reason> reasons = reasonDAO.getReasonBySaleTransType(saleTransType);
            //List<Reason> reasons = reasonDAO.findByPropertyWithStatus("reasonType", REASION_TYPE, Constant.STATUS_USE.toString());


            List<AppParams> appParamses = paramsDAO.findByProperty("id.type", APP_PARAMES_TYPE);
            if (find == true) {
                List<StockTransBean> stockTranses = stockTransDAO.findStockTrainsJoinStockTransDetailByFomOwnerType(payStatus,
                        ownerType, shopId, STOCK_TRANS_TYPE_EXPORT, STOCK_TRANS_ACTION_ACTION_TYPE, null, 1L, null,
                        sDate, eDate, parentShopId, true, STOCK_TRANS_STATUS);
                setListStockTrans(stockTranses);

//                stockTranses.get(0).getShopIdImport();
            }

            setListShops(shops);
            setListReasons(reasons);
            setParamsest(appParamses);


        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    public String prepareSaleFromExpCommand() throws Exception {

        log.info("Begin method prepareSaleFromExpCommand of SaleDAO ...");

        HttpServletRequest req = getRequest();

        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward =
                Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                boolean find = false;
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, null, null, null, userToken.getShopId(), find);

//                Date date = DateTimeUtils.convertStringToDate(DateTimeUtils.getSysDateTime("yyyy-MM-dd"));

                setSellDate(DateTimeUtils.getSysDateTime("yyyy-MM-dd"));
                Calendar currentDate = Calendar.getInstance();
                Calendar tenDayBefore = Calendar.getInstance();
                tenDayBefore.add(Calendar.DATE, -10);

                setStartDate(DateTimeUtils.convertDateToString(tenDayBefore.getTime()));
                setEndDate(DateTimeUtils.convertDateToString(currentDate.getTime()));

                pageForward =
                        SALE_FROM_EXP_COMMAND;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareSaleFromExpCommand of SaleDAO");

        return pageForward;
    }
    /**
     *
     * author tamdt1 date: 23/03/2009 chuan bi form de doi hang hong
     *
     */
    private List<StockType> stockTypes = new ArrayList<StockType>();

    public List<StockType> getStockTypes() {
        return stockTypes;
    }

    public void setStockTypes(List<StockType> stockTypes) {
        this.stockTypes = stockTypes;
    }

    public String getNewSerial() {
        return newSerial;
    }

    public void setNewSerial(String newSerial) {
        this.newSerial = newSerial;
    }

    public String getOldSerial() {
        return oldSerial;
    }

    public void setOldSerial(String oldSerial) {
        this.oldSerial = oldSerial;
    }
    private String oldSerial = null;
    private String newSerial = null;
    private String typeId = null;
    private Long impType;
    private String serverFileName;

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    private String stockTypeId = null;

    public String getStockTypeId() {
        return stockTypeId;
    }

    public void setStockTypeId(String stockTypeId) {
        this.stockTypeId = stockTypeId;
    }
    private String stockId = null;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    private void findStockTypeBindToPage() throws Exception {
        try {

            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(getSession());

            List<StockType> lstStockTypes = stockTypeDAO.findByPropertyAndStatus("checkExp", Constant.STOCK_MUST_EXP, Constant.STATUS_USE);
            getRequest().setAttribute("lstStockTypes", lstStockTypes);

        } catch (Exception e) {
            throw new Exception(e);
        }

    }


    /*
     * Author: ThanhNC Date created: 25/05/2009 Purpose: Chon stockModelId tu
     * stockTypeId
     */
    public String getStockModel() throws Exception {
        try {

            HttpServletRequest httpServletRequest = getRequest();
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);

            //Chon hang hoa tu loai hang hoa
            String stockType = httpServletRequest.getParameter("stockTypeId");
            String[] header = {"", getText("MSG.stock.select.goods")};
            listItemsSelectBox =
                    new ArrayList();
            listItemsSelectBox.add(header);
            if (stockType != null && !"".equals(stockType.trim())) {
                Long id = Long.parseLong(stockType.trim());

//                StockTypeDAO stockTypeDAO = new StockTypeDAO();
//                stockTypeDAO.setSession(this.getSession());
//                StockType stockType = stockTypeDAO.findById(stockTypeId);
//                if (stockType == null) {
//                    return "success";
//                }
//                String tableName = new BaseStockDAO().getTableNameByStockType(stockType.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);

                String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc ";
                Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);
                List lstStockModel = q.list();
                listItemsSelectBox.addAll(lstStockModel);

//                String tableName = new BaseStockDAO().getTableNameByStockType(id, BaseStockDAO.NAME_TYPE_NORMAL);
//                if (tableName == null || tableName.trim().equals("")){
//                    return "success";
//                }

//                String SQL_SELECT_STOCK_MODEL = " SELECT   a.stock_model_id AS stockModelId, a.NAME AS name " +
//                        "    FROM stock_model a " +
//                        "   WHERE stock_type_id = ? " +
//                        "     AND status = ? " +
//                        "     AND EXISTS ( " +
//                        "            SELECT * " +
//                        "              FROM stock_total " +
//                        "             WHERE stock_model_id = a.stock_model_id " +
//                        "               AND owner_id = ? " +
//                        "               AND owner_type = ?" +
//                        "               AND quantity_issue>0) " +
//                        "ORDER BY NLSSORT (NAME, 'nls_sort=Vietnamese') ASC ";
//                Query q = getSession().createSQLQuery(SQL_SELECT_STOCK_MODEL);
//                q.setParameter(0, id);
//                q.setParameter(1, Constant.STATUS_USE);
//                q.setParameter(2, userToken.getUserID());
//                q.setParameter(3, Constant.OWNER_TYPE_STAFF);
//                List lstStockModel = q.list();
//                listItemsSelectBox.addAll(lstStockModel);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "success";
    }

    public String prepareChangeDamagedGoods() throws Exception {

        log.info("Begin method prepareChangeDamagedGoods of SaleDAO ...");

        HttpServletRequest req = getRequest();

        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                findStockTypeBindToPage();
                setImpType(2L);
                pageForward =
                        CHANGE_DAMAGED_GOODS;
                req.setAttribute("template_download_file", getText("MSG.download.file.maxrows",
                        new String[]{String.valueOf(Constant.MAX_REQUEST_VIEW_CARD)}));

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }

        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method prepareChangeDamagedGoods of SaleDAO");

        return pageForward;
    }

    /**
     *
     * author : date : modified : tamdt1, 21/03/2010, thay doi dieu kien check
     * the cao hong
     *
     */
    public String saveChangeDamagedGoods() throws Exception {
        log.info("Begin method saveChangeDamagedGoods of SaleDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = CHANGE_DAMAGED_GOODS;

        if (userToken != null) {
            try {
//                BaseDAOAction baseDAOAction = new BaseDAOAction(); //them vao phuc vu viec multilanguage
                req.setAttribute("template_download_file", getText("MSG.download.file.maxrows",
                        new String[]{String.valueOf(Constant.MAX_REQUEST_VIEW_CARD)}));
                findStockTypeBindToPage();
                //Lay danh sach stockModel tuong ung neu stockTypeId co gia tri
                String sTypeId = getStockTypeId();
                if (sTypeId == null || "".equals(sTypeId.trim())) {
//                    req.setAttribute("returnMsg", "Bạn chưa ch�?n loại mặt hàng");
                    req.setAttribute("returnMsg", "ERR.STK.100");
                    return pageForward;
                }

                Long stockType = Long.parseLong(sTypeId);
                List lstStockModel = null;
                if (stockType != null) {
                    String SQL_SELECT_STOCK_MODEL = " from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc ";
                    Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                    q.setParameter(0, stockType);
                    q.setParameter(1, Constant.STATUS_USE);
                    lstStockModel = q.list();
                    req.setAttribute("lstStockModel", lstStockModel);
                }


                String stockModel = getStockId();
                Long importType = getImpType();
                Long stockModelId = Long.parseLong(stockModel);
                if (importType.compareTo(2L) == 0) {
                    String newNumber = getNewSerial();
                    String oldNumber = getOldSerial();
                    if (newNumber == null || "".equals(newNumber.trim()) || oldNumber == null || "".equals(oldNumber.trim()) || stockModel == null || "".equals(stockModel.trim())) {
//                    req.setAttribute("returnMsg", "Bạn chưa nhập các điều kiện bắt buộc");
                        req.setAttribute("returnMsg", "ERR.STK.101");
                        return pageForward;
                    }
                    newNumber = newNumber.trim();
                    oldNumber = oldNumber.trim();


                    BaseStockDAO baseStockDAO = new BaseStockDAO();
                    baseStockDAO.setSession(this.getSession());

                    //Tim kiem serial cu tren he thong (chi tim nhung serial da ban)
                    //Object oldObj = baseStockDAO.findBySerialAndModel(stockType, stockModelId, oldNumber, Constant.STATUS_DELETE);
                    boolean isFoundInOldStockCard = false; //bien co trang thai danh dau co p tim trong bang cu hay ko
                    Object oldObj = baseStockDAO.findBySerialAndModel(stockType, stockModelId, oldNumber);
                    if (oldObj == null) {

                        //tamdt1, start, 21/03/23010 -- them dieu kien check trong view cu
                        oldObj = getVOldStockCardObjectByModelAndSerial(stockModelId, oldNumber);
                        if (oldObj == null) {
//                        req.setAttribute("returnMsg", "!!!Lỗi. Serial h�?ng không tồn tại trên hệ thống");
                            req.setAttribute("returnMsg", "ERR.STK.102");
                            return pageForward;
                        } else {
                            isFoundInOldStockCard = true;
                        }
                        //tamdt1, end, 21/03/23010

//                    req.setAttribute("returnMsg", "Serial h�?ng không tồn tại trên hệ thống hoặc chưa được bán kh�?i hệ thống.");
//                    return pageForward;
                    }
                    Long status = (Long) CommonDAO.getObjectField(oldObj, "getStatus");
                    Long ownerType = (Long) CommonDAO.getObjectField(oldObj, "getOwnerType");
                    Long ownerId = (Long) CommonDAO.getObjectField(oldObj, "getOwnerId");
                    if (ownerType == null || ownerId == null || status == null) {
//                    req.setAttribute("returnMsg", "Serial h�?ng thiếu thông tin kho và trạng thái.");
                        req.setAttribute("returnMsg", "ERR.STK.103");
                        return pageForward;
                    }
                    //chi cho phep doi hang hong trong truong hop la serial hong da ban hoac trong kho doi tuong ngoai viettel
                    if (!status.equals(Constant.STATUS_SALED) && !status.equals(Constant.STATUS_USE)) {
//                    req.setAttribute("returnMsg", "Serial h�?ng không tồn tại trên hệ thống hoặc chưa được bán kh�?i hệ thống.");
                        req.setAttribute("returnMsg", "ERR.STK.104");
                        return pageForward;
                    }
                    //Neu trang thai moi va trong kho doi tuong ngoai viettel (CTV -Dai ly) thi cho phep doi hang hong
                    //Nguoc lai bao loi
                    if (status.equals(Constant.STATUS_USE)) {
                        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                        channelTypeDAO.setSession(this.getSession());
                        ChannelType channelType = channelTypeDAO.findByOwerTypeAndOwnerId(ownerType, ownerId);
                        //La doi tuong thuoc viettel --> bao loi
                        if (channelType != null && channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
//                        req.setAttribute("returnMsg", "Serial h�?ng không tồn tại trên hệ thống hoặc chưa được bán kh�?i hệ thống.");
                            req.setAttribute("returnMsg", "ERR.STK.104");
                            return pageForward;
                        }
                    }

//Tim serial moi trong kho nhan vien

                    Object newObj = baseStockDAO.findInShopBySerialAndModel(stockType, stockModelId, newNumber, Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATUS_USE);
                    if (newObj == null) {
//                    req.setAttribute("returnMsg", "Serial đổi lại không tồn tại trong kho nhân viên.");
                        req.setAttribute("returnMsg", "ERR.STK.105");
                        return pageForward;
                    }

                    Long stateId = (Long) CommonDAO.getObjectField(newObj, "getStateId");
                    if (stateId != null && !stateId.equals(Constant.STATE_NEW)) {
//                    req.setAttribute("returnMsg", "Serial đổi lại không phải là hàng mới.");
                        req.setAttribute("returnMsg", "ERR.STK.106");
                        return pageForward;
                    }
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(this.getSession());
//Cap nhap trang thai cua serial cua la hàng h�?ng và đẩy v�? kho nhân viên đăng nhập                
                    String tableName = new BaseStockDAO().getTableNameByStockType(stockType, BaseStockDAO.NAME_TYPE_NORMAL);

                    if ("".equals(tableName)) {
//                    req.setAttribute("returnMsg", "Không tìm thấy tên bảng dữ liệu chứa hàng hoá.");
                        req.setAttribute("returnMsg", "ERR.STK.107");
                        return pageForward;
                    }

                    // tutm1 21/08/2012 kiem tra trang thai the cao tren tong dai 
                    StockModelDAO stockModelDAO = new StockModelDAO();
                    StockModel stockModelcheck = stockModelDAO.findById(stockModelId);
                    System.out.println("Check the cao : ");
                    if (stockModelcheck != null && stockModelcheck.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                        String errCardStatus = checkCardStatus(oldNumber);
                        if (errCardStatus != null && !errCardStatus.equals("")) {
                            req.setAttribute("returnMsg", errCardStatus);
                            return pageForward;
                        }
                    }
                    System.out.println("Check xong the cao ! ");
                    //Khoi tao tong tien han muc
                    Long amountDebit = 0L;
                    String[] arrMess = new String[3];

                    //check gia goc
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    Shop shop = shopDAO.findById(userToken.getShopId());
                    String pricePolicy = shop.getPricePolicy();

                    PriceDAO priceDAO = new PriceDAO();
                    priceDAO.setSession(getSession());
                    Long price = priceDAO.findBasicPrice(stockModelId, pricePolicy);
                    if (price == null && checkStockOwnerTmpDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF)) {
                        req.setAttribute("returnMsg", "ERR.SAE.143");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    } else {
                        //tru han muc so luong cua NV
                        if (price == null) {
                            price = 0L;
                        }
                        amountDebit += price * 1L;
                        arrMess = new String[3];
                        arrMess = reduceDebitTotal(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, stockModelId,
                                Constant.STATE_NEW, Constant.STATUS_DEBIT_DEPOSIT, 1L, false, null);
                        if (!arrMess[0].equals("")) {
                            req.setAttribute("returnMsg", arrMess[0]);
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            return pageForward;

                        }
                        //cong han muc hang hong cua NV
                        arrMess = new String[3];
                        arrMess = addDebitTotal(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, stockModelId,
                                Constant.STATE_DAMAGE, Constant.STATUS_DEBIT_DEPOSIT, 1L, false, null);
                        if (!arrMess[0].equals("")) {
                            req.setAttribute("returnMsg", arrMess[0]);
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            return pageForward;

                        }
                    }

                    //tru han muc tong tien
                    arrMess = new String[3];
                    arrMess = reduceDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF, amountDebit, false, null);
                    if (!arrMess[0].equals("")) {
                        req.setAttribute("returnMsg", arrMess[0]);
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }


                    //tamdt1, 21/03/2010, bo sung them,
                    //cap nhat trang thai cua serial la hong va day ve kho nhan vien dang nhap trong truong hop co serial trog bang stock_card
                    //trong truong hop serial hong duoc tim thay trong view VOldStockCard thi p insert 1 ban ghi vao stockCard truoc khi cap nhat
                    if (isFoundInOldStockCard) {
                        String strInsertQuery = "insert into stock_card (id, stock_model_id, serial, card_type, amount_type, owner_id, owner_type, create_date, expired_date,status,telecom_service_id, state_id, check_dial, dial_status, user_session_id, create_user) (select id, stock_model_id, serial, card_type, amount_type, owner_id, owner_type, create_date, expired_date,status,telecom_service_id, state_id, check_dial, dial_status, user_session_id, create_user from v_old_stock_card where serial = ? and stock_model_id = ? and rownum < 2) ";
                        Query insertQuery = getSession().createSQLQuery(strInsertQuery);
                        insertQuery.setParameter(0, oldSerial);
                        insertQuery.setParameter(1, stockModelId);
                        insertQuery.executeUpdate();
                    }

                    String queryUpdateSerialOld = "update " + tableName + " set state_id= ?,owner_type= ? , owner_id = ?,  status = ? where " + " to_number(serial) = ? and stock_model_id = ? ";
                    Query updateOld = getSession().createSQLQuery(queryUpdateSerialOld);
                    updateOld.setParameter(0, Constant.STATE_DAMAGE);
                    updateOld.setParameter(1, Constant.OWNER_TYPE_STAFF);
                    updateOld.setParameter(2, userToken.getUserID());
                    updateOld.setParameter(3, Constant.STATUS_USE);
                    updateOld.setParameter(4, oldSerial);
                    updateOld.setParameter(5, stockModelId);
                    updateOld.executeUpdate();
                    //R499 :trung dh3 comment
//                    if (!stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW, stockModelId, 1L, true)) {
////                    req.setAttribute("returnMsg", "Kho nhân viên không còn đủ hàng đáp ứng.");
//                        req.setAttribute("returnMsg", baseDAOAction.getText("ERR.STK.108"));
//                        getSession().clear();
//                        getSession().getTransaction().rollback();
//                        getSession().beginTransaction();
//                        logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), "Kho nhân viên không còn đủ hàng đáp ứng.");
//                        return pageForward;
//                    }
                    //R499 :trung dh3 comment end


                    //Cap nhap trang thai cua serial moi la da ban
                    String queryUpdateSerialNew = "update " + tableName + " set status = ? where " + " to_number(serial) = ? and stock_model_id = ? ";
                    Query updateNew = getSession().createSQLQuery(queryUpdateSerialNew);
                    updateNew.setParameter(0, Constant.STATUS_DELETE);
                    updateNew.setParameter(1, newSerial);
                    updateNew.setParameter(2, stockModelId);
                    updateNew.executeUpdate();
                    /*
                    HashMap oldMapValue = new HashMap();
                    //Trang thai hang la hang hong                
                    oldMapValue.put("setStateId", Constant.STATE_DAMAGE);
                    //Day hang ve kho nhan vien
                    oldMapValue.put("setOwnerType", Constant.OWNER_TYPE_STAFF);
                    oldMapValue.put("setOwnerId", userToken.getUserID());
                    //Cap nhap trang thai hang dang trong kho
                    oldMapValue.put("setStatus", Constant.STATUS_USE);
                    CommonDAO commonDAO = new CommonDAO();
                    oldObj =
                    commonDAO.setFieldsForObject(oldObj, oldMapValue);
                    getSession().save(oldObj);
                     */
                    /*
                    //Cap nhap trang thai cua serial moi la da ban
                    HashMap newMapValue = new HashMap();
                    //Cap nhap trang thai hang da ban
                    newMapValue.put("setStatus", Constant.STATUS_DELETE);
                    newObj =
                    commonDAO.setFieldsForObject(newObj, newMapValue);
                    getSession().save(newObj);
                    // tru kho di 1 serial moi
                    StockCommonDAO stockCommonDAO = new StockCommonDAO();
                    stockCommonDAO.setSession(this.getSession());
                    if (!stockCommonDAO.expStockTotal(Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW, stockModelId, 1L, true)) {
                    req.setAttribute("returnMsg", "Kho nhân viên không còn đủ hàng đáp ứng.");
                    req.setAttribute("lstStockModel", lstStockModel);
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return pageForward;
                    }
                     * */


                    ReasonDAO reasonDAO = new ReasonDAO();
                    reasonDAO.setSession(this.getSession());
                    //Luu thong tin giao dich xuat kho (stock_transaction)
                    Date createDate = Calendar.getInstance().getTime();
                    Long expTransId = getSequence("STOCK_TRANS_SEQ");
                    StockTrans expStockTrans = new StockTrans();
                    expStockTrans.setStockTransId(expTransId);

                    expStockTrans.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
                    expStockTrans.setFromOwnerId(userToken.getUserID());
                    expStockTrans.setToOwnerType(Constant.OWNER_TYPE_CUST);
                    expStockTrans.setToOwnerId(Constant.OWNER_CUST_ID);
                    //trung dh3
                    expStockTrans.setTransType(Constant.STOCK_TRANS_TYPE_DAMAGE);
                    expStockTrans.setCreateDatetime(createDate);
                    expStockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat
                    expStockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho
                    String reasonExp = ResourceBundleUtils.getResource("REASON_EXP_CHANGE_DEMAGED_GOODS");
                    if (reasonExp == null || "".equals(reasonExp)) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do xuất đổi hàng h�?ng");
                        req.setAttribute("returnMsg", "ERR.STK.109");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }

                    Reason reasonEx = reasonDAO.findByReasonCode(reasonExp);
                    if (reasonEx == null) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do xuất đổi hàng h�?ng");
                        req.setAttribute("returnMsg", "ERR.STK.109");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }
                    expStockTrans.setReasonId(reasonEx.getReasonId());

//                    expStockTrans.setNote("Xuất đổi hàng h�?ng cho khách hàng.");
                    expStockTrans.setNote(getText("SaleDAO.009"));
                    

                    getSession().save(expStockTrans);

                    //Tao stock_trans_action

                    StockTransAction stockTransActionExp = new StockTransAction();
                    Long stockTransActionIdExp = getSequence("STOCK_TRANS_ACTION_SEQ");

                    stockTransActionExp.setActionId(stockTransActionIdExp);
//                    stockTransActionExp.setActionCode("XDHH00" + stockTransActionIdExp);
//                    stockTransActionExp.setActionCode(getTransCode(Constant.TRANS_CODE_PX));
                    if (shop != null) {
                        stockCommonDAO.setSession(getSession());
                        StaffDAO staffDAO = new StaffDAO();
                        staffDAO.setSession(getSession());
                        Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                        String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PX);
                        stockTransActionExp.setActionCode(actionCode);
                    }
                    stockTransActionExp.setNote(getText("SaleDAO.009"));
                    stockTransActionExp.setStockTransId(expTransId);
                    stockTransActionExp.setActionType(Constant.ACTION_TYPE_NOTE);
                    stockTransActionExp.setActionStaffId(userToken.getUserID());
                    stockTransActionExp.setActionStatus(Constant.STATUS_USE);
                    stockTransActionExp.setCreateDatetime(createDate);
                    stockTransActionExp.setUsername(userToken.getLoginName());
//                    stockTransActionExp.setNote("Xuất đổi hàng h�?ng cho khách hàng");
                    getSession().save(stockTransActionExp);

                    //Luu chi tiet phieu xuat
                    StockTransDetail expTransDetail = new StockTransDetail();

                    expTransDetail.setStockTransId(expTransId);
                    expTransDetail.setStockModelId(stockModelId);
                    expTransDetail.setStateId(Constant.STATE_NEW);
                    expTransDetail.setQuantityRes(1L);
                    expTransDetail.setCreateDatetime(createDate);
//                    expTransDetail.setNote("Xuất đổi hàng h�?ng cho khách hàng.");
                    expStockTrans.setNote(getText("SaleDAO.009"));
                    getSession().save(expTransDetail);

                    StockTransSerial expStockSerial = new StockTransSerial();

                    expStockSerial.setStockModelId(stockModelId);
                    expStockSerial.setStateId(Constant.STATE_NEW);
                    expStockSerial.setStockTransId(expTransId);
                    expStockSerial.setFromSerial(newNumber);
                    expStockSerial.setToSerial(newNumber);
                    expStockSerial.setQuantity(1L);
                    expStockSerial.setCreateDatetime(createDate);
                    getSession().save(expStockSerial);
                    //R499 :trung dh3 add tru kho  mot mat hang moi
                    GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF, stockModelId,
                            Constant.STATE_NEW, -1L, -1L, null,
                            userToken.getUserID(), expStockTrans.getReasonId(), expStockTrans.getStockTransId(), "", expStockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                    if (genResult.isSuccess() != true) {
                        req.setAttribute("returnMsg", "Kho nhân viên không còn đủ hàng đáp ứng.");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }
                    //R499 :trung dh3 add end
                    //Cong kho nhan vien 1 mat hang hong

//                    boolean noError = stockCommonDAO.impStockTotalDebit(this.getSession(), Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_DAMAGE, stockModelId, 1L);
//                    if (!noError) {
//                        req.setAttribute("returnMsg", "SaleDAO.010");
//                        getSession().clear();
//                        getSession().getTransaction().rollback();
//                        getSession().beginTransaction();
//                        logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), "Có lỗi trong quá trình hồi hàng về kho");
//                        return pageForward;
//                    }

                    //Tao giao dich  nhap kho

                    Long impTrasId = getSequence("STOCK_TRANS_SEQ");
                    StockTrans impStockTrans = new StockTrans();
                    impStockTrans.setStockTransId(impTrasId);

                    impStockTrans.setFromStockTransId(expTransId);
                    impStockTrans.setFromOwnerType(Constant.OWNER_TYPE_CUST);
                    impStockTrans.setFromOwnerId(Constant.OWNER_CUST_ID);
                    impStockTrans.setToOwnerType(Constant.OWNER_TYPE_STAFF);
                    impStockTrans.setToOwnerId(userToken.getUserID());
//                    trung dh3
                    impStockTrans.setTransType(Constant.STOCK_TRANS_TYPE_DAMAGE);
                    impStockTrans.setCreateDatetime(createDate);
                    impStockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
                    impStockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho
                    impStockTrans.setNote(getText("SaleDAO.011"));
                    String reasonImp = ResourceBundleUtils.getResource("REASON_IMP_CHANGE_DEMAGED_GOODS");

                    if (reasonImp == null || "".equals(reasonImp)) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do nhập đổi hàng h�?ng");
                        req.setAttribute("returnMsg", "ERR.STK.110");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }

                    Reason reasonIm = reasonDAO.findByReasonCode(reasonImp);
                    if (reasonIm == null) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do nhập đổi hàng h�?ng");
                        req.setAttribute("returnMsg", "ERR.STK.110");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }
                    impStockTrans.setReasonId(reasonIm.getReasonId());

                    impStockTrans.setNote(getText("SaleDAO.011"));
//                    impStockTrans.setNote("Nhập đổi hàng h�?ng của khách hàng");

                    getSession().save(impStockTrans);

                    //Tao stock_trans_action

                    StockTransAction stockTransActionImp = new StockTransAction();
                    Long stockTransActionId = getSequence("STOCK_TRANS_ACTION_SEQ");

                    stockTransActionImp.setActionId(stockTransActionId);
//                    stockTransActionImp.setActionCode("NDHH00" + stockTransActionId);
                    // doi thanh sinh ma tu dong
//                    stockTransActionImp.setActionCode(getTransCode(Constant.TRANS_CODE_PN));
                    if (shop != null) {
                        stockCommonDAO.setSession(getSession());
                        StaffDAO staffDAO = new StaffDAO();
                        staffDAO.setSession(getSession());
                        Staff staff = staffDAO.findAvailableByStaffCode(userToken.getLoginName());
                        String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PN);
                        stockTransActionImp.setActionCode(actionCode);
                    }
                    stockTransActionImp.setStockTransId(impTrasId);
                    stockTransActionImp.setActionType(Constant.ACTION_TYPE_NOTE);
                    stockTransActionImp.setActionStaffId(userToken.getUserID());
                    stockTransActionImp.setActionStatus(Constant.STATUS_USE);
                    stockTransActionImp.setCreateDatetime(createDate);
                    stockTransActionImp.setUsername(userToken.getLoginName());
//                    stockTransActionImp.setNote("Nhập đổi hàng h�?ng của khách hàng");
                    stockTransActionImp.setNote(getText("SaleDAO.011"));
                    getSession().save(stockTransActionImp);


                    //Luu chi tiet phieu nhap
                    StockTransDetail impTransDetail = new StockTransDetail();

                    impTransDetail.setStockTransId(impTrasId);
                    impTransDetail.setStockModelId(stockModelId);
                    //Nhap hang hong
                    impTransDetail.setStateId(Constant.STATE_DAMAGE);
                    impTransDetail.setQuantityRes(1L);
                    impTransDetail.setCreateDatetime(createDate);
//                    impTransDetail.setNote("Nhập đổi hàng h�?ng của khách hàng");
                    impTransDetail.setNote(getText("SaleDAO.011"));
                    getSession().save(impTransDetail);

                    //Luu chi tiet serial
                    StockTransSerial impStockSerial = new StockTransSerial();
                    impStockSerial.setStockModelId(stockModelId);
                    impStockSerial.setStateId(Constant.STATE_DAMAGE);
                    impStockSerial.setStockTransId(impTrasId);
                    impStockSerial.setCreateDatetime(createDate);

                    impStockSerial.setFromSerial(oldNumber);
                    impStockSerial.setToSerial(oldNumber);
                    impStockSerial.setQuantity(1L);

                    getSession().save(impStockSerial);

                    //luu thong tin vao bang VcRequets
                    stockModelDAO.setSession(getSession());
                    if (stockModelcheck != null && stockModelcheck.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                        VcRequestDAO vcRequestDAO = new VcRequestDAO();
                        vcRequestDAO.setSession(getSession());
                        vcRequestDAO.saveVcRequest(createDate, newNumber.toString(), newNumber.toString(), Constant.REQUEST_TYPE_CHANGE_GOODS, expTransId);
                    }

                    if (stockModelcheck != null && stockModelcheck.getStockTypeId().equals(Constant.STOCK_KIT)) {
                        ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                        reqActiveKitDAO.setSession(getSession());
                        reqActiveKitDAO.saveReqActiveKit(newNumber, newNumber, Constant.SALE_TYPE, expTransId,
                                Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL), getSysdate());
                    }
//trung dh 3 công kho nhân viên 1 mặt hàng hỏng
                    GenResult genResult1 = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF, stockModelId,
                            Constant.STATE_DAMAGE, 1L, 1L, null,
                            userToken.getUserID(), impStockTrans.getReasonId(), impStockTrans.getStockTransId(), "", Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                    if (genResult1.isSuccess() != true) {
                        req.setAttribute("returnMsg", "SaleDAO.010");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }
                } //NamLT bo sung ngay 27/5/2011 nhap theo file
                else if (importType.compareTo(1L) == 0) {
                    String serverFileName = getServerFileName();
                    String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                    // Fix ATTT.
                    serverFileName = getSafeFileName(serverFileName);
                    String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
                    File impFile = new File(serverFilePath);
                    List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 1);
                    List<ErrorChangeChannelBean> listError = new ArrayList<ErrorChangeChannelBean>();
                    boolean checkInsert = true;
                    if (list == null || list.size() == 0) {
                        // req.setAttribute("resultImportDataInShop", "ERR.CHL.059");
                        req.setAttribute("returnMsg", "ERR.CHL.059");
                        req.setAttribute("importsucess", "true");
                        return pageForward;
                    }

                    if (list != null && list.size() > Constant.MAX_REQUEST_VIEW_CARD) {
                        req.setAttribute("returnMsg", "SaleDAO.012");
                        List listMessageParam = new ArrayList();
                        listMessageParam.add(new Integer(Constant.MAX_REQUEST_VIEW_CARD));
                        req.setAttribute("returnMsgValue", listMessageParam);
                        req.setAttribute("importsucess", "true");
                        return pageForward;
                    }

                    Long count = 0L;
                    Long total = 0L;
                    Long expTransId = getSequence("STOCK_TRANS_SEQ");
                    Long reportChangeGoodId = expTransId;
                    Long impTrasId = getSequence("STOCK_TRANS_SEQ");

                    StockTrans expStockTrans = new StockTrans();
                    expStockTrans.setStockTransId(expTransId);

                    expStockTrans.setFromOwnerType(Constant.OWNER_TYPE_STAFF);
                    expStockTrans.setFromOwnerId(userToken.getUserID());
                    expStockTrans.setToOwnerType(Constant.OWNER_TYPE_CUST);
                    expStockTrans.setToOwnerId(Constant.OWNER_CUST_ID);

                    expStockTrans.setCreateDatetime(getSysdate());
                    expStockTrans.setStockTransType(Constant.TRANS_EXPORT);//Loai giao dich la xuat
                    expStockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho

                    String reasonExp = ResourceBundleUtils.getResource("REASON_EXP_CHANGE_DEMAGED_GOODS");
                    if (reasonExp == null || "".equals(reasonExp)) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do xuất đổi hàng hỏng");
                        req.setAttribute("returnMsg", "ERR.STK.109");
                        req.setAttribute("importsucess", "true");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }

                    ReasonDAO reasonDAO = new ReasonDAO();
                    reasonDAO.setSession(this.getSession());

                    Reason reasonEx = reasonDAO.findByReasonCode(reasonExp);
                    if (reasonEx == null) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do xuất đổi hàng hỏng");
                        req.setAttribute("returnMsg", "ERR.STK.109");
                        req.setAttribute("importsucess", "true");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }
                    expStockTrans.setReasonId(reasonEx.getReasonId());

                    expStockTrans.setNote(getText("SaleDAO.009"));

                    getSession().save(expStockTrans);



                    StockTrans impStockTrans = new StockTrans();
                    impStockTrans.setStockTransId(impTrasId);
                    impStockTrans.setFromStockTransId(expTransId);
                    impStockTrans.setFromOwnerType(Constant.OWNER_TYPE_CUST);
                    impStockTrans.setFromOwnerId(Constant.OWNER_CUST_ID);
                    impStockTrans.setToOwnerType(Constant.OWNER_TYPE_STAFF);
                    impStockTrans.setToOwnerId(userToken.getUserID());

                    impStockTrans.setCreateDatetime(getSysdate());
                    impStockTrans.setStockTransType(Constant.TRANS_IMPORT);//Loai giao dich la nhap kho
                    impStockTrans.setStockTransStatus(Constant.TRANS_DONE); //Giao dich da nhap kho
                    String reasonImp = ResourceBundleUtils.getResource("REASON_IMP_CHANGE_DEMAGED_GOODS");

                    if (reasonImp == null || "".equals(reasonImp)) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do nhập đổi hàng hỏng");
                        req.setAttribute("returnMsg", "ERR.STK.110");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }

                    Reason reasonIm = reasonDAO.findByReasonCode(reasonImp);
                    if (reasonIm == null) {
//                    req.setAttribute("returnMsg", "Không tìm thấy lý do nhập đổi hàng hỏng");
                        req.setAttribute("returnMsg", "ERR.STK.110");
                        req.setAttribute("importsucess", "true");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }
                    impStockTrans.setReasonId(reasonIm.getReasonId());

                    impStockTrans.setNote(getText("SaleDAO.011"));

                    getSession().save(impStockTrans);

                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(getSession());
                    Staff staff = staffDAO.findById(userToken.getUserID());

                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(getSession());
                    Shop shop = shopDAO.findById(userToken.getShopId());

                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                    channelTypeDAO.setSession(this.getSession());

                    StockModelDAO stockModelDAO = new StockModelDAO();
                    stockModelDAO.setSession(getSession());
                    StockModel stockModelcheck = stockModelDAO.findById(stockModelId);
                    //Cap nhap trang thai cua serial cua la hàng hỏng và đẩy về kho nhân viên đăng nhập
                    String tableName = new BaseStockDAO().getTableNameByStockType(stockType, BaseStockDAO.NAME_TYPE_NORMAL);

                    for (int i = 0; i < list.size(); i++) {
                        Object[] obj = (Object[]) list.get(i);
                        String oldNumber = obj[0].toString().replaceAll(",", "");
                        String newNumber = obj[1].toString().replaceAll(",", "");
                        BaseStockDAO baseStockDAO = new BaseStockDAO();
                        baseStockDAO.setSession(this.getSession());
                        total++;
                        StringBuffer errCode = new StringBuffer();
                        checkInsert = true;

                        if ("".equals(tableName)) {
                            errCode.append(";" + getText("ERR.STK.107"));
//                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
//                            errorBean.setNewNumber(newNumber);
//                            errorBean.setOldNumber(oldNumber);
//                            errorBean.setError(baseDAOAction.getText("ERR.STK.107"));
//                            listError.add(errorBean);
                            checkInsert = false;
                        }

                        if (newNumber == null || "".equals(newNumber.trim()) || stockModel == null || "".equals(stockModel.trim())) {
                            errCode.append(getText("SaleDAO.013"));
//                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
//                            errorBean.setNewNumber(newNumber);
//                            errorBean.setOldNumber(oldNumber);
//                            errorBean.setError("Serial đổi không được để trống");
//                            listError.add(errorBean);
                            checkInsert = false;
//                            break;

                        } else if (!chkNumber(newNumber)) {
                            errCode.append(getText("SaleDAO.015"));
//                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
//                            errorBean.setNewNumber(newNumber);
//                            errorBean.setOldNumber(oldNumber);
//                            errorBean.setError("Serial đổi không hợp lệ");
//                            listError.add(errorBean);
                            checkInsert = false;
//                            break;
                        } else {

//Tim serial moi trong kho nhan vien

                            Object newObj = baseStockDAO.findInShopBySerialAndModel(stockType, stockModelId, newNumber, Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATUS_USE);
                            if (newObj == null) {
                                errCode.append(";" + getText("ERR.STK.105"));
//                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
//                            errorBean.setNewNumber(newNumber);
//                            errorBean.setOldNumber(oldNumber);
//                            errorBean.setError(baseDAOAction.getText("ERR.STK.105"));
//                            listError.add(errorBean);
                                checkInsert = false;
                            } else {
                                Long stateId = (Long) CommonDAO.getObjectField(newObj, "getStateId");
                                if (stateId != null && !stateId.equals(Constant.STATE_NEW)) {
                                    errCode.append(";" + getText("ERR.STK.106"));
                                    //                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                                    //                            errorBean.setNewNumber(newNumber);
                                    //                            errorBean.setOldNumber(oldNumber);
                                    //                            errorBean.setError(baseDAOAction.getText("ERR.STK.106"));
                                    //                            listError.add(errorBean);
                                    checkInsert = false;
                                }
                            }
//                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
//                        stockCommonDAO.setSession(this.getSession());



                        }


                        if (oldNumber == null || "".equals(oldNumber.trim()) || stockModel == null || "".equals(stockModel.trim())) {
                            errCode.append(getText("SaleDAO.014"));
//                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
//                            errorBean.setNewNumber(newNumber);
//                            errorBean.setOldNumber(oldNumber);
//                            errorBean.setError("Serial cần đổi không được để trống");
//                            listError.add(errorBean);
                            checkInsert = false;
//                            break;

                        } else if (!chkNumber(oldNumber)) {
                            errCode.append(getText("SaleDAO.016"));
//                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
//                            errorBean.setNewNumber(newNumber);
//                            errorBean.setOldNumber(oldNumber);
//                            errorBean.setError("Serial cần đổi không hợp lệ");
//                            listError.add(errorBean);
                            checkInsert = false;
//                            break;
                        } else {

                            //Tim kiem serial cu tren he thong (chi tim nhung serial da ban)
                            //Object oldObj = baseStockDAO.findBySerialAndModel(stockType, stockModelId, oldNumber, Constant.STATUS_DELETE);
                            boolean isFoundInOldStockCard = false; //bien co trang thai danh dau co p tim trong bang cu hay ko
                            Object oldObj = baseStockDAO.findBySerialAndModel(stockType, stockModelId, oldNumber);
                            if (oldObj == null) {

                                //tamdt1, start, 21/03/23010 -- them dieu kien check trong view cu
                                oldObj = getVOldStockCardObjectByModelAndSerial(stockModelId, oldNumber);
                                if (oldObj == null) {
                                    //                        req.setAttribute("returnMsg", "!!!Lỗi. Serial hỏng không tồn tại trên hệ thống");
                                    errCode.append(getText("SaleDAO.017"));
                                    //                                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                                    //                                errorBean.setNewNumber(newNumber);
                                    //                                errorBean.setOldNumber(oldNumber);
                                    //                                errorBean.setError(baseDAOAction.getText("ERR.STK.102"));
                                    //                                listError.add(errorBean);
                                    checkInsert = false;

                                } else {
                                    isFoundInOldStockCard = true;
                                }
                            } else { //TruongNQ5 20141124
                            Long status = (Long) CommonDAO.getObjectField(oldObj, "getStatus");
                            Long ownerType = (Long) CommonDAO.getObjectField(oldObj, "getOwnerType");
                            Long ownerId = (Long) CommonDAO.getObjectField(oldObj, "getOwnerId");
                            if (oldObj != null && ownerType == null || ownerId == null || status == null) {
                                errCode.append(getText("SaleDAO.018"));
                                //                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                                //                            errorBean.setNewNumber(newNumber);
                                //                            errorBean.setOldNumber(oldNumber);
                                //                            errorBean.setError(baseDAOAction.getText("ERR.STK.103"));
                                //                            listError.add(errorBean);
                                checkInsert = false;

                            }
                            //chi cho phep doi hang hong trong truong hop la serial hong da ban hoac trong kho doi tuong ngoai viettel
                            if (status != null && !status.equals(Constant.STATUS_SALED) && !status.equals(Constant.STATUS_USE)) {
                                errCode.append(";" + "ERR.STK.104");
                                //                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                                //                            errorBean.setNewNumber(newNumber);
                                //                            errorBean.setOldNumber(oldNumber);
                                //                            errorBean.setError(baseDAOAction.getText("ERR.STK.104"));
                                //                            listError.add(errorBean);
                                checkInsert = false;

                            }
                            //Neu trang thai moi va trong kho doi tuong ngoai viettel (CTV -Dai ly) thi cho phep doi hang hong
                            //Nguoc lai bao loi
                            if (status != null && status.equals(Constant.STATUS_USE)) {
                                ChannelType channelType = channelTypeDAO.findByOwerTypeAndOwnerId(ownerType, ownerId);
                                //La doi tuong thuoc viettel --> bao loi
                                if (channelType != null && channelType.getIsVtUnit().equals(Constant.IS_VT_UNIT)) {
                                    errCode.append(getText("SaleDAO.019"));
                                    //                                ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                                    //                                errorBean.setNewNumber(newNumber);
                                    //                                errorBean.setOldNumber(oldNumber);
                                    //                                errorBean.setError(baseDAOAction.getText("ERR.STK.104"));
                                    //                                listError.add(errorBean);
                                    checkInsert = false;
                                }
                            }



                            }//TruongNQ5 20141124
                            // tutm1 21/08/2012 kiem tra trang thai the cao tren tong dai 
                            System.out.println("Check the cao : ");
                            if (stockModelcheck != null && stockModelcheck.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                                String errCardStatus = checkCardStatus(oldNumber);
                                if (errCardStatus != null && !errCardStatus.equals("")) {
                                    errCode.append(";" + errCardStatus);
                                    checkInsert = false;
                                }
                            }
                            System.out.println("Check xong ! ");


                            //check gia goc

                            //  String pricePolicy = shop.getPricePolicy();
                            //
                            //                        PriceDAO priceDAO = new PriceDAO();
                            //                        priceDAO.setSession(getSession());
                            //                        Long price = priceDAO.findBasicPrice(stockModelId, pricePolicy);
                            //                        if (price == null && checkStockOwnerTmpDebit(userToken.getUserID(), Constant.OWNER_TYPE_STAFF)) {
                            //                            errCode.append(";" + baseDAOAction.getText(baseDAOAction.getText("ERR.SAE.143")));
                            ////                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                            ////                            errorBean.setNewNumber(newNumber);
                            ////                            errorBean.setOldNumber(oldNumber);
                            ////                            errorBean.setError(baseDAOAction.getText("ERR.SAE.143"));
                            ////                            listError.add(errorBean);
                            //                            checkInsert = false;
                            //
                            //                        } else {
                            //                            //tru han muc so luong cua NV
                            //                            if (price == null) {
                            //                                price = 0L;
                            //                            }
                            //                        }

                            //  logStartUser(new Date(), "checkStockOwnerTmpDebit", callId, userToken.getLoginName());

                            if (isFoundInOldStockCard) {
                                String strInsertQuery = "insert into " + tableName + " value (select * from v_old_stock_card where serial = ? and stock_model_id = ? and rownum < 2) ";
                                Date timeSQL = new Date();

                                Query insertQuery = getSession().createSQLQuery(strInsertQuery);
                                insertQuery.setParameter(0, oldNumber);
                                insertQuery.setParameter(1, stockModelId);
                                insertQuery.executeUpdate();
                            }
                        }

                        if (errCode != null && !"".equals(errCode.toString())) {
                            ErrorChangeChannelBean errorBean = new ErrorChangeChannelBean();
                            errorBean.setNewNumber(newNumber);
                            errorBean.setOldNumber(oldNumber);
                            errorBean.setError(errCode.toString());
                            listError.add(errorBean);

                        }


                        if (checkInsert) {
                            count++;
                            Date timeSQL1 = new Date();
                            String queryUpdateSerialOld = "update " + tableName + " set state_id= ?,owner_type= ? , owner_id = ?,  status = ? where " + " to_number(serial) = ? and stock_model_id = ? and status =? ";
                            Query updateOld = getSession().createSQLQuery(queryUpdateSerialOld);
                            updateOld.setParameter(0, Constant.STATE_DAMAGE);
                            updateOld.setParameter(1, Constant.OWNER_TYPE_STAFF);
                            updateOld.setParameter(2, userToken.getUserID());
                            updateOld.setParameter(3, Constant.STATUS_USE);
                            updateOld.setParameter(4, oldNumber);
                            updateOld.setParameter(5, stockModelId);
                            updateOld.setParameter(6, Constant.STATUS_SALED);
                            //updateOld.executeUpdate();
                            int resultOld = updateOld.executeUpdate();
                            if (resultOld != 1) {
                                req.setAttribute("returnMsg", "Có lỗi xảy ra khi cập nhật trạng thái Serial cần đối với Serial:" + oldNumber);
                                req.setAttribute("importsucess", "true");
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                return pageForward;
                            }


                            //Cong kho hang hong


                            //Cap nhap trang thai cua serial moi la da ban
                            String queryUpdateSerialNew = "update " + tableName + " set status = ?, sale_date = sysdate where " + " to_number(serial) = ? and stock_model_id = ? and owner_id= ? and owner_type= ? ";
                            Query updateNew = getSession().createSQLQuery(queryUpdateSerialNew);
                            updateNew.setParameter(0, Constant.STATUS_DELETE);
                            updateNew.setParameter(1, newNumber);
                            updateNew.setParameter(2, stockModelId);
                            updateNew.setParameter(3, userToken.getUserID());
                            updateNew.setParameter(4, Constant.OWNER_TYPE_STAFF);
                            int resultNew = updateNew.executeUpdate();


                            if (resultNew != 1) {
                                req.setAttribute("returnMsg", "SaleDAO.020");
                                req.setAttribute("importsucess", "true");
                                getSession().clear();
                                getSession().getTransaction().rollback();
                                getSession().beginTransaction();
                                return pageForward;
                            }


                            //Luu giao dich Serial
                            StockTransSerial expStockSerial = new StockTransSerial();

                            expStockSerial.setStockModelId(stockModelId);
                            expStockSerial.setStateId(Constant.STATE_NEW);
                            expStockSerial.setStockTransId(expTransId);
                            expStockSerial.setFromSerial(newNumber);
                            expStockSerial.setToSerial(newNumber);
                            expStockSerial.setQuantity(1L);
                            expStockSerial.setCreateDatetime(getSysdate());
                            getSession().save(expStockSerial);


                            //Luu chi tiet serial
                            StockTransSerial impStockSerial = new StockTransSerial();
                            impStockSerial.setStockModelId(stockModelId);
                            impStockSerial.setStateId(Constant.STATE_DAMAGE);
                            impStockSerial.setStockTransId(impTrasId);
                            impStockSerial.setCreateDatetime(getSysdate());

                            impStockSerial.setFromSerial(oldNumber);
                            impStockSerial.setToSerial(oldNumber);
                            impStockSerial.setQuantity(1L);
                            getSession().save(impStockSerial);


                            if (stockModelcheck != null && stockModelcheck.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                                VcRequestDAO vcRequestDAO = new VcRequestDAO();
                                vcRequestDAO.setSession(getSession());
                                vcRequestDAO.saveVcRequest(getSysdate(), newNumber.toString(), newNumber.toString(), Constant.REQUEST_TYPE_CHANGE_GOODS, expTransId);
                            }

                            if (stockModelcheck != null && stockModelcheck.getStockTypeId().equals(Constant.STOCK_KIT)) {
                                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                                reqActiveKitDAO.setSession(getSession());
                                reqActiveKitDAO.saveReqActiveKit(newNumber, newNumber, Constant.SALE_TYPE, expTransId,
                                        Long.parseLong(Constant.SALE_TRANS_TYPE_RETAIL), getSysdate());
                            }

                        }
                    }

                    //Nếu không có lỗi -->Tiến hành tạo GD kho
                    if (count.compareTo(total) == 0) {

                        //Luu thong tin giao dich xuat kho (stock_transaction)
                        Date createDate = Calendar.getInstance().getTime();

                        //COng kho hangh hong
                        StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        stockCommonDAO.setSession(this.getSession());
//                        boolean noError = stockCommonDAO.impStockTotalDebit(this.getSession(), Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_DAMAGE, stockModelId, count);
//                        if (!noError) {
                        //R499:trung dh3 comment end
                        //R499:trung dh3 add
                        GenResult genResult2 = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF, stockModelId,
                                Constant.STATE_DAMAGE, count, count, null,
                                userToken.getUserID(), impStockTrans.getReasonId(), impStockTrans.getStockTransId(), "", Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                        if (genResult2.isSuccess() != true) {
                            req.setAttribute("returnMsg", "SaleDAO.010");
                            req.setAttribute("importsucess", "true");
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            return pageForward;
                        }

                        //R499:trung dh3 add end
                        //Thuc hien tru kho hang moi
                        //R499 : trung dh3 comment
//                        if (!stockCommonDAO.expStockTotal(this.getSession(), Constant.OWNER_TYPE_STAFF, userToken.getUserID(), Constant.STATE_NEW, stockModelId, count, true)) {
//                            req.setAttribute("returnMsg", baseDAOAction.getText("ERR.STK.108"));
//                            req.setAttribute("importsucess", "true");
//                            getSession().clear();
//                            getSession().getTransaction().rollback();
//                            getSession().beginTransaction();
//                            logEndUser(startTime, new Date(), function, callId, userToken.getLoginName(), "Không tìm thấy lý do xuất đổi hàng hỏng");
//                            return pageForward;
//                        }
                        //R499 : trung dh3 comment end
                        //R499 : trung dh3 add
                        GenResult genResult3 = StockTotalAuditDAO.changeStockTotal(getSession(), userToken.getUserID(), Constant.OWNER_TYPE_STAFF, stockModelId,
                                Constant.STATE_NEW, -count, -count, null,
                                userToken.getUserID(), impStockTrans.getReasonId(), impStockTrans.getFromStockTransId(), "", impStockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                        if (genResult3.isSuccess() != true) {
                            req.setAttribute("returnMsg", "ERR.STK.108");
                            req.setAttribute("importsucess", "true");
                            getSession().clear();
                            getSession().getTransaction().rollback();
                            getSession().beginTransaction();
                            return pageForward;
                        }






                        //Tao stock_trans_action

                        StockTransAction stockTransActionExp = new StockTransAction();
                        Long stockTransActionIdExp = getSequence("STOCK_TRANS_ACTION_SEQ");

                        stockTransActionExp.setActionId(stockTransActionIdExp);
//                        stockTransActionExp.setActionCode("XDHH00" + stockTransActionIdExp);
//                        stockTransActionExp.setActionCode(getTransCode(Constant.TRANS_CODE_PX));
                        if (shop != null) {
                            stockCommonDAO.setSession(getSession());
                            staffDAO.setSession(getSession());
                            String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PX);
                            stockTransActionExp.setActionCode(actionCode);
                        }
                        stockTransActionExp.setStockTransId(expTransId);
                        stockTransActionExp.setActionType(Constant.ACTION_TYPE_NOTE);
                        stockTransActionExp.setActionStaffId(userToken.getUserID());
                        stockTransActionExp.setActionStatus(Constant.STATUS_USE);
                        stockTransActionExp.setCreateDatetime(createDate);
                        stockTransActionExp.setUsername(userToken.getLoginName());
//                        stockTransActionExp.setNote("Xuất đổi hàng hỏng cho khách hàng");
                        stockTransActionExp.setNote(getText("SaleDAO.009"));
                        getSession().save(stockTransActionExp);


                        //Luu chi tiet phieu xuat
                        StockTransDetail expTransDetail = new StockTransDetail();

                        expTransDetail.setStockTransId(expTransId);
                        expTransDetail.setStockModelId(stockModelId);
                        expTransDetail.setStateId(Constant.STATE_NEW);
                        expTransDetail.setQuantityRes(count);
                        expTransDetail.setQuantityReal(count);
                        expTransDetail.setCreateDatetime(createDate);
                        expTransDetail.setNote(getText("SaleDAO.009"));
                        getSession().save(expTransDetail);


                        //  StockCommonDAO stockCommonDAO = new StockCommonDAO();
                        // stockCommonDAO.setSession(this.getSession());

                        //Tao giao dich  nhap kho



                        //Tao stock_trans_action

                        StockTransAction stockTransActionImp = new StockTransAction();
                        Long stockTransActionId = getSequence("STOCK_TRANS_ACTION_SEQ");

                        stockTransActionImp.setActionId(stockTransActionId);
//                        stockTransActionImp.setActionCode("NDHH00" + stockTransActionId);
//                        stockTransActionImp.setActionCode(getTransCode(Constant.TRANS_CODE_PN));
                        if (shop != null) {
                            stockCommonDAO.setSession(getSession());
                            staffDAO.setSession(getSession());
                            String actionCode = stockCommonDAO.genTransactionCode(shop.getShopId(), shop.getShopCode(), staff.getStaffId(), Constant.TRANS_CODE_PN);
                            stockTransActionImp.setActionCode(actionCode);
                        }
                        stockTransActionImp.setNote(getText("SaleDAO.011"));
                        stockTransActionImp.setStockTransId(impTrasId);
                        stockTransActionImp.setActionType(Constant.ACTION_TYPE_NOTE);
                        stockTransActionImp.setActionStaffId(userToken.getUserID());
                        stockTransActionImp.setActionStatus(Constant.STATUS_USE);
                        stockTransActionImp.setCreateDatetime(createDate);
                        stockTransActionImp.setUsername(userToken.getLoginName());
                        getSession().save(stockTransActionImp);


                        //Luu chi tiet phieu nhap
                        StockTransDetail impTransDetail = new StockTransDetail();

                        impTransDetail.setStockTransId(impTrasId);
                        impTransDetail.setStockModelId(stockModelId);
                        //Nhap hang hong
                        impTransDetail.setStateId(Constant.STATE_DAMAGE);
                        impTransDetail.setQuantityRes(count);
                        impTransDetail.setQuantityReal(count);
                        impTransDetail.setCreateDatetime(createDate);
                        impTransDetail.setNote(getText("SaleDAO.011"));
                        getSession().save(impTransDetail);
                    }
                    if (count.compareTo(total) != 0) {
                        downloadFile("errorImporChangeGoodsByFile", listError);
                        req.setAttribute("returnMsg", "ERR.SMS.008");
                        req.setAttribute("importsucess", "true");
                        getSession().clear();
                        getSession().getTransaction().rollback();
                        getSession().beginTransaction();
                        return pageForward;
                    }
                    req.setAttribute("importsucess", "true");
                }

                getSession().getTransaction().commit();
                getSession().beginTransaction();
                req.setAttribute("returnMsg", "ERR.STK.111");
            } catch (Exception e) {
                log.error("Error at Number NumberFormatException ");
                e.printStackTrace();
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                throw e;
            }

        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method saveChangeDamagedGoods of SaleDAO");

        return pageForward;
    }

    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        req.setAttribute(REQUEST_FILE_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute(REQUEST_FILE_MESSAGE, "ERR.SMS.001");

    }

    /**
     *
     * author : tamdt1 date : 21/03/2010 desc : lay doi tuong view stock_card cu
     *
     */
    private Object getVOldStockCardObjectByModelAndSerial(Object stockModelId, Object serial) {
        try {
            HashMap<String, Object> hash = new HashMap<String, Object>();
            hash.put("stockModelId", stockModelId);
            hash.put("to_number(serial)", serial);

            List listParam = new ArrayList();
            StringBuffer strQuery = new StringBuffer("from VOldStockCard where 1 = 1 ");
            Set set = hash.entrySet();
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                Map.Entry me = (Map.Entry) iter.next();
                strQuery.append(" and " + (String) me.getKey() + " = ? ");
                listParam.add(me.getValue());
            }
            strQuery.append(" and rownum < 2 ");

            Query query = getSession().createQuery(strQuery.toString());
            for (int i = 0; i < listParam.size(); i++) {
                query.setParameter(i, listParam.get(i));
            }

            List listResult = query.list();
            if (listResult != null && listResult.size() > 0) {
                return listResult.get(0);
            } else {
                return null;
            }

        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        }
    }

    /**
     *
     * author tuannv1 date: 16/04/2009 Tim kiem lenh xuat boc tham hang hoa
     *
     */
    protected List<InvoiceSaleListBean> searchInvoiceSaleList(String billSerial, String billNum, Date startDate,
            Date endDate, Long staffId, Long shopId) {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "select new com.viettel.im.client.bean.InvoiceSaleListBean(a.invoiceId, a.serialNo, a.invoiceNo, a.createDate, " + " a.staffId, a.custName) " + "from InvoiceUsed a ";// +
            //"join InvoiceUsed b on a.invoiceListId =b.invoiceListId";
            List parameterList = new ArrayList();

            /*
             * ShopId of Bill
             */
            queryString +=
                    "where a.shopId = ? ";
            parameterList.add(shopId);

            if ((billSerial != null && !billSerial.equals("")) || billNum != null || startDate != null || endDate != null || staffId != null) {

                if (billSerial != null && !billSerial.equals("")) {
                    queryString += "and a.serialNo like ? ";
                    parameterList.add(billSerial);
                }

                if (billNum != null && !billNum.equals("")) {
                    queryString += "and a.invoiceNo >= ? ";
                    queryString +=
                            "and a.invoiceNo <= ? ";
                    parameterList.add(billNum);
                    parameterList.add(billNum);
                }

                if (staffId != null) {
                    queryString += "and a.staffId = ? ";
                    parameterList.add(staffId);
                }

                if (startDate != null) {
                    queryString += "and a.createDate >= ? ";
                    parameterList.add(startDate);
                }

                if (endDate != null) {
                    queryString += "and a.createDate <= ? ";
                    parameterList.add(endDate);
                }

                queryString += "order by a.invoiceNo, a.invoiceId ";

            }

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }

            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

    }

    /**
     *
     * author tuannv1 date: 10/04/2009 Tim kiem thông tin chi tiết hóa đơn
     *
     */
    public String billDetail() {
        log.debug("finding all InvoiceList instances");
        InvoiceUsedDetailBean invoiceUsedDetail;

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        //Long invoiceId = (Long) session.getAttribute("invoiceId");
        Long invoiceId = Long.parseLong("100");
//        d.saleTransId,a.custName,a.company,a.address,a.tin,e.accountNo,a.payMethod,a.reasonId,a.note,a.amount,a.amountTax,b.invoiceListId,b.fromInvoice,b.toInvoice,b.currInvoiceNo
        try {
            String queryString = "select new com.viettel.im.client.bean.InvoiceUsedDetailBean(d.saleTransId,a.custName,a.company,a.address,a.tin,e.accountNo,a.payMethod,a.reasonId,a.note,a.amount,a.amountTax,b.invoiceListId,b.fromInvoice,b.toInvoice,b.currInvoiceNo) " + "from InvoiceUsed a,InvoiceList b,BankReceipt c,SaleTrans d, BankAccount e ";// +
            List parameterList = new ArrayList();


            queryString +=
                    "where a.invoiceId = ? ";
            parameterList.add(invoiceId);
            queryString +=
                    "and a.invoiceListId = b.invoiceListId ";
            queryString +=
                    "and a.bankReceiptId = c.bankReceiptId ";
            queryString +=
                    "and a.invoiceId = d.invoiceId ";
            queryString +=
                    "and c.bankAccountId = e.bankAccountId ";


            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }

            if (queryObject.list() != null) {

                invoiceUsedDetail = (InvoiceUsedDetailBean) queryObject.list().get(0);
                saleForm.setTransId(invoiceUsedDetail.getSaleTransId().toString());
                saleForm.setAccount(invoiceUsedDetail.getAccount());
                saleForm.setAddress(invoiceUsedDetail.getAddress());
                saleForm.setCompany(invoiceUsedDetail.getCompany());
                saleForm.setCustNameBill(invoiceUsedDetail.getCustName());
                saleForm.setPayMethod(invoiceUsedDetail.getPayMethod());
                saleForm.setTin(invoiceUsedDetail.getTin());
                saleForm.setReasonId(invoiceUsedDetail.getReasonId().toString());
                saleForm.setAmount(invoiceUsedDetail.getAmount());
                saleForm.setAmountTax(invoiceUsedDetail.getAmountTax());
                saleForm.setNote(invoiceUsedDetail.getNote());
                saleForm.setInvoiceListId(invoiceUsedDetail.getInvoiceListId());
                saleForm.setFromInvoice(invoiceUsedDetail.getFromInvoice());
                saleForm.setToInvoice(invoiceUsedDetail.getToInvoice());
                saleForm.setCurrInvoiceNo(invoiceUsedDetail.getCurrInvoiceNo());

            }
//            session.setAttribute("invoiceUsed", invoiceUsed);

            return "destroySaleInvoice";
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

    }

    /**
     * @return the totalTaxMoney
     */
    public Double getTotalTaxMoney() {
        return totalTaxMoney;
    }

    /**
     * @param totalTaxMoney the totalTaxMoney to set
     */
    public void setTotalTaxMoney(Double totalTaxMoney) {
        this.totalTaxMoney = totalTaxMoney;
    }

    /**
     * @return the totalNotTaxMoney
     */
    public Double getTotalNotTaxMoney() {
        return totalNotTaxMoney;
    }

    /**
     * @param totalNotTaxMoney the totalNotTaxMoney to set
     */
    public void setTotalNotTaxMoney(Double totalNotTaxMoney) {
        this.totalNotTaxMoney = totalNotTaxMoney;
    }

    /**
     * @return the idShop
     */
    public long getIdShop() {
        return idShop;
    }

    /**
     * @param idShop the idShop to set
     */
    public void setIdShop(long idShop) {
        this.idShop = idShop;
    }

    public Price findPrice(
            java.lang.Long id) {
        log.debug("getting Price instance with id: " + id);
        try {
            Price instance = (Price) getSession().get("com.viettel.im.database.BO.Price", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append(" from Shop a, ");
        strQuery1.append(" ChannelType chty ");
        strQuery1.append(" where 1 = 1 ");
        strQuery1.append(" AND a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);
        strQuery1.append(" and chty.channelTypeId = a.channelTypeId ");
        strQuery1.append(" and a.parentShopId = ? ");
        //listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append(" and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append(" AND ");
        strQuery1.append(" chty.objectType = ? ");
        listParameter1.add(CHANEL_TYPE_OBJECT_TYPE);
        strQuery1.append(" AND ");
        strQuery1.append(" chty.isVtUnit = ? ");
        listParameter1.add(CHANEL_TYPE_IS_VT_UNIT);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay ten shop
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append(" from Shop a, ");
        strQuery1.append(" ChannelType chty ");
        strQuery1.append(" where 1 = 1 ");
        strQuery1.append(" AND a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);
        strQuery1.append(" and chty.channelTypeId = a.channelTypeId ");
        strQuery1.append(" and a.parentShopId = ? ");
        //listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append(" and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append(" AND ");
        strQuery1.append(" chty.objectType = ? ");
        listParameter1.add(CHANEL_TYPE_OBJECT_TYPE);
        strQuery1.append(" AND ");
        strQuery1.append(" chty.isVtUnit = ? ");
        listParameter1.add(CHANEL_TYPE_IS_VT_UNIT);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    /**
     * @author : haint41 @date : 29/11/2011 @desc : lay so luong ban ghi cua
     * shop
     * @param imSearchBean
     * @return
     */
    public Long getListShopSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuilder strQuery1 = new StringBuilder("select count(*) ");
        strQuery1.append(" from Shop a, ");
        strQuery1.append(" ChannelType chty ");
        strQuery1.append(" where 1 = 1 ");
        strQuery1.append(" AND a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);
        strQuery1.append(" and chty.channelTypeId = a.channelTypeId ");
        strQuery1.append(" and a.parentShopId = ? ");
        //listParameter1.add("%_" + userToken.getShopId() + "_%");
        listParameter1.add(userToken.getShopId());

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append(" and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append(" and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append(" AND ");
        strQuery1.append(" chty.objectType = ? ");
        listParameter1.add(CHANEL_TYPE_OBJECT_TYPE);
        strQuery1.append(" AND ");
        strQuery1.append(" chty.isVtUnit = ? ");
        listParameter1.add(CHANEL_TYPE_IS_VT_UNIT);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    /**
     *
     * Purpose: Xem chi tiet giao dich xuat kho cho Dai ly de lap giao dich ban
     * dut
     *
     * @return
     * @throws Exception
     */
    public String viewStockTransDetailAction() throws Exception {
        log.info("Begin method viewStockTransDetailAction of SaleDAO");

        pageForward = Constant.ERROR_PAGE;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        HttpSession session = req.getSession();
        session.removeAttribute("telecomService");

        try {
            //Tinh tong tien chiet khau
            setTotalPriceRate(0.0);
            //Tong tien phai tra
            setTotalPriceAfterRate(0.0);
            //Tinh tong tien chua thue
            setTotalPrice(0.0);
            //Tong tien thue
            setTotalTaxMoney(0.0);

            String stockTransId = req.getParameter("stockTransId");

            //Kiem tra id cua giao dich
            if (stockTransId == null || "".equals(stockTransId.trim())) {
                return pageForward;
            }

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(getSession());
            StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
            stockTransDetailDAO.setSession(getSession());
            SaleCommonDAO saleCommonDAO = new SaleCommonDAO();
            saleCommonDAO.setSession(getSession());
            DiscountGroupDAO discountGroupDAO = new DiscountGroupDAO();
            discountGroupDAO.setSession(getSession());

            //Tim giao dich kho
            StockTrans stockTrans = stockTransDAO.findById(Long.valueOf(stockTransId.trim()));
            if (stockTrans == null
                    || stockTrans.getStockTransId() == null) {
                req.setAttribute("returnMsg", "saleToAgent.error.noShopInported");
                return "viewFromExpCommand";
            }
            if (stockTrans.getPayStatus() != null && stockTrans.getPayStatus().equals(1L)) {
                req.setAttribute("returnMsg", "Lỗi. Giao dịch đã được thanh toán");
                return "viewFromExpCommand";
            }

            //Tim dai ly
            Shop agent = shopDAO.findById(stockTrans.getToOwnerId());
            if (agent == null
                    || agent.getShopId() == null) {
                req.setAttribute("returnMsg", "saleToAgent.error.noShopInported");
                return "viewFromExpCommand";
            }
            //Lay thong tin CSCK & CSGia cua Dai ly
            String discountPolicy = agent.getDiscountPolicy();
            String pricePolicy = agent.getPricePolicy();
            if (discountPolicy == null) {
                discountPolicy = "";
            }
            if (pricePolicy == null) {
                pricePolicy = "";
            }



            //Danh sach hang hoa ban dut trong giao dich
            // tutm1 21/12/2011 bo check mat hang chua duoc cau hinh ban dut, do trong buoc lap lenh xuat da check roi
            List<StockTransDetail> lstStockTransDetail = stockTransDetailDAO.findJoinStockModelByStockTransId(Long.parseLong(stockTransId), CHECK_DEPOSIT);



            //Kiem tra cac hang hoa cung VAT va telecomServiceId
            Long vatRate = null;
            Long telecomServiceId = null;
            List<SaleTransDetailV2Bean> saleTransDetailBeanList = new ArrayList();

            for (StockTransDetail stockTransDetail : lstStockTransDetail) {

                //Kiem tra hang hoa co cung telecomServiceId hay khong?
                if (stockTransDetail.getTelecomServiceId() == null
                        || (telecomServiceId != null && !stockTransDetail.getTelecomServiceId().equals(telecomServiceId))) {
                    req.setAttribute("returnMsg", "saleExp.warn.NotSameTelecomServiceId");
                    return "viewFromExpCommand";
                }

                //Tim nhom chiet khau discountGroupId
                stockTransDetail.setDiscountGroupId(saleCommonDAO.getDiscountGroupId(stockTransDetail.getStockModelId(), discountPolicy));


                //Neu san pham ban theo goi
                //Tinh theo san pham ban theo goi
                boolean checkPackagePrice = checkProductSaleToPackage(stockTransDetail.getStockModelId(), true);              
                
                //Tim gia ban dut cho Dai ly (la gia ban le)
                List<Price> lstPrice = priceDAO.findByPolicyTypeAndStockModel(pricePolicy,
                        checkPackagePrice ? Constant.SALE_TRANS_TYPE_PACKAGE : Constant.PRICE_TYPE_RETAIL,
                        stockTransDetail.getStockModelId());
                if (lstPrice == null || lstPrice.isEmpty()) {
                    req.setAttribute("returnMsg", "saleExp.warn.NotPrice");
                    return "viewFromExpCommand";
                }
                Price price = lstPrice.get(0);
                //Check hang hoa phai cung VAT
                if (price.getVat() == null
                        || (vatRate != null && !price.getVat().equals(vatRate))) {
                    req.setAttribute("returnMsg", "saleExp.warn.NotSameVat");
                    return "viewFromExpCommand";
                }

                //Gan priceId cho hang hoa
                stockTransDetail.setPriceId(price.getPriceId());
                stockTransDetail.setPrices(lstPrice);

                stockTransDetail.setPrice(price.getPrice());
                stockTransDetail.setVat(price.getVat());
                stockTransDetail.setCurrency(price.getCurrency());
                if (Constant.PRICE_AFTER_VAT) {
                    stockTransDetail.setAmountAfterTax(stockTransDetail.getQuantityRes() * price.getPrice());
                    stockTransDetail.setAmountBeforeTax(stockTransDetail.getAmountAfterTax() / (1 + price.getVat() / 100));
                    stockTransDetail.setAmountTax(stockTransDetail.getAmountBeforeTax() * price.getVat() / 100);
                } else {
                    stockTransDetail.setAmountBeforeTax(stockTransDetail.getQuantityRes() * price.getPrice());
                    stockTransDetail.setAmountTax(stockTransDetail.getAmountBeforeTax() * price.getVat() / 100);
                    stockTransDetail.setAmountAfterTax(stockTransDetail.getAmountBeforeTax() + stockTransDetail.getAmountTax());
                }
                stockTransDetail.setAmount(stockTransDetail.getQuantityRes() * price.getPrice());


                //Copy ra list moi ==> dung chung function tinh chiet khau
                SaleTransDetailV2Bean newBean = new SaleTransDetailV2Bean();
                newBean.setStockModelId(stockTransDetail.getStockModelId());
                newBean.setQuantity(stockTransDetail.getQuantityRes());
                newBean.setPriceId(stockTransDetail.getPriceId());
                newBean.setPrice(stockTransDetail.getPrice());
                newBean.setVat(stockTransDetail.getVat());
                newBean.setPriceDisplay(NumberUtils.rounđAndFormatAmount(stockTransDetail.getPrice()));

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

                newBean.setDiscountGroupId(stockTransDetail.getDiscountGroupId());
                newBean.setDiscountId(stockTransDetail.getDiscountId());

                saleTransDetailBeanList.add(newBean);

            }

            //Tinh chiet khau




            //Tinh chiet khau
//            SaleToCollaboratorForm f = new SaleToCollaboratorForm();
            SaleTransForm saleTransForm = new SaleTransForm();
//            Map<String, Map<Long, Double>> lstMap = saleCommonDAO.sumDiscountForAgent(req, lstStockTransDetail, f);
            Map<String, Map<Long, Double>> lstMap = saleCommonDAO.sumDiscount(req, saleTransDetailBeanList, saleTransForm);
            Map<Long, Double> mapDiscountGroupAmount = lstMap.get("mapDiscountGroupAmount");
            Map<Long, Double> mapDiscountRate = lstMap.get("mapDiscountRate");
            Map<Long, Double> mapStockModelDiscount = lstMap.get("mapStockModelDiscount");
            setTabSession("MapStockModelDiscountAmount", mapStockModelDiscount);//Luu vao session de khi lap giao dich se su dung

            //Tam thoi update discountId lai list ban dau / sau nay se thay doi su dung list moi
            for (int idx = 0; idx < saleTransDetailBeanList.size(); idx++) {
                for (int j = 0; j < lstStockTransDetail.size(); j++) {
                    StockTransDetail oldBean = lstStockTransDetail.get(j);
                    if (oldBean != null && oldBean.getStockModelId() != null && saleTransDetailBeanList.get(idx).getStockModelId().equals(oldBean.getStockModelId())) {
                        oldBean.setDiscountGroupId(saleTransDetailBeanList.get(idx).getDiscountGroupId());
                        oldBean.setDiscountId(saleTransDetailBeanList.get(idx).getDiscountId());
                        break;
                    }
                }
            }


            //Lay thong tin danh sach nhom chiet khau, ti le & so tien ck cua nhom CK
            List<DiscountBean> lstStockModelDiscount = new ArrayList();
            if (mapDiscountGroupAmount != null && mapDiscountGroupAmount.size() > 0) {
                Set<Long> key = mapDiscountGroupAmount.keySet();
                Iterator<Long> iteratorKey = key.iterator();
                while (iteratorKey.hasNext()) {
                    Long discountGroupId = iteratorKey.next();
                    if (discountGroupId == null) {
                        continue;
                    }
                    DiscountGroup discountGroup = discountGroupDAO.findById(discountGroupId);
                    if (discountGroup == null
                            || discountGroup.getDiscountGroupId() == null) {
                        continue;
                    }
                    DiscountBean discountBean = new DiscountBean();
                    discountBean.setDiscountGroupId(discountGroup.getDiscountGroupId());
                    discountBean.setName(discountGroup.getName());

                    //So tien & ti le % cua nhom CK
                    Double discountAmount = mapDiscountGroupAmount.get(discountGroupId);
                    Double discountRate = mapDiscountRate.get(discountGroupId);
                    if (discountAmount != null) {
                        discountBean.setDiscountAmount(discountAmount);
                    }
                    if (discountRate != null) {
                        discountBean.setDiscountRate(discountRate * 100);
                    }

                    lstStockModelDiscount.add(discountBean);
                }
            }

            setTabSession("lstStockTransDetail", lstStockTransDetail);//Luu vao session de khi lap giao dich se su dung
            setListTransDetails(lstStockTransDetail);//Danh sach hang hoa thuoc lenh xuat
            setListModelMaps(lstStockModelDiscount);//Danh sach chiet khau

            //Gan tong tien giao dich
            setTotalPriceAfterRate(saleTransForm.getAmountAfterTax());//Tong tien sau thue (da tru CK)
            setTotalPrice(saleTransForm.getAmountBeforeTax());//Tong tien truoc thue (da tru CK)
//            setTotalPriceRate(saleTransForm.getVat());//Tong chiet khau truoc thue
            setTotalTaxMoney(saleTransForm.getAmountTax());//Tong thue
            setDiscountNotTax(saleTransForm.getAmountDiscount());//Tong chiet khau truoc thue


            ReasonDAO reasonDAO = new ReasonDAO();
            AppParamsDAO paramsDAO = new AppParamsDAO();
            reasonDAO.setSession(getSession());
            paramsDAO.setSession(getSession());
            Long[] saleTransType = new Long[1];
            saleTransType[0] = Long.parseLong(Constant.SALE_TRANS_TYPE_AGENT);
            List<Reason> reasons = reasonDAO.getReasonBySaleTransType(saleTransType);
            List<AppParams> appParamses = paramsDAO.findByProperty("id.type", APP_PARAMES_TYPE);
            setListReasons(reasons);
            if (reasons.size() > 0) {
                Reason reason = reasons.get(0);
                setReasonsId(reason.getReasonId().toString());
            }
            setParamsest(appParamses);
            pageForward = "viewFromExpCommand";
        } catch (Exception e) {
            log.info(e.getMessage());
            throw e;
        }
        log.info("End method prepareSaleFromExpCommand of SaleDAO");
        return pageForward;
    }

    /**
     *
     * @return @throws Exception
     */
    public String saveCommand() throws Exception {
        log.info("Begin method saveCommand of SaleDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        String sDate = getStartDate();
        String eDate = getEndDate();
        try {
            String idShop = getShopId();//id cua Dai ly
            String tin = getTaxId();//ma so thue
            String address = getAddressId();//dia chi
            String reason = getReasonsId();//ly do
            String payMethod = getParamesId();//hinh thuc thanh toan

            if (payMethod == null || payMethod.equals("")) {
                req.setAttribute("returnMsg", "saleExp.warn.pay");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            if (reason == null || reason.equals("")) {
                req.setAttribute("returnMsg", "saleExp.warn.reason");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }

            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(getSession());
            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(getSession());
            SaleTransDetailDAO detailDAO = new SaleTransDetailDAO();
            detailDAO.setSession(getSession());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());

            List<StockTransDetail> lstStockTransDetail = (List<StockTransDetail>) getTabSession("lstStockTransDetail");
            Map<Long, Double> mapStockModelDiscount = (Map<Long, Double>) getTabSession("MapStockModelDiscountAmount");

            if (lstStockTransDetail == null || lstStockTransDetail.isEmpty()) {
                req.setAttribute("returnMsg", "Lỗi. Danh sách hàng hoá rỗng!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            StockTransDetail stockTransDetail = lstStockTransDetail.get(0);
            Long stockTransId = stockTransDetail.getStockTransId();
            Long telecomServiceId = stockTransDetail.getTelecomServiceId();
            Long priceId = stockTransDetail.getPriceId();

            if (stockTransId == null) {
                req.setAttribute("returnMsg", "Lỗi. Không có mã giao dịch kho!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            if (telecomServiceId == null) {
                req.setAttribute("returnMsg", "Lỗi. Không có loại dịch vụ!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            if (priceId == null) {
                req.setAttribute("returnMsg", "Lỗi mặt hàng chưa được khai báo giá!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            Price price = priceDAO.findById(priceId);
            if (price == null) {
                req.setAttribute("returnMsg", "Lỗi mặt hàng chưa được khai báo giá!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }

            StockTrans stockTransBO = stockTransDAO.findById(stockTransId);
            if (stockTransBO == null || stockTransBO.getStockTransId() == null) {
                req.setAttribute("returnMsg", "Lỗi. Mã phiếu xuất không hợp lệ!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            if (stockTransBO.getPayStatus().equals(Constant.PAY_HAVE)) {
                req.setAttribute("returnMsg", "Lỗi. Lệnh xuất đã lập giao dịch bán hàng!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            Shop shopBO = shopDAO.findById(stockTransBO.getToOwnerId());
            if (shopBO == null || shopBO.getShopId() == null) {
                req.setAttribute("returnMsg", "Lỗi. Mã đại lý nhận không hợp lệ!");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                return "saveFromExpCommand";
            }
            getSession().refresh(stockTransBO, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            stockTransBO.setPayStatus(Constant.PAY_HAVE);
            getSession().update(stockTransBO);

            //Luu saleTrans
            SaleTrans saleTrans = new SaleTrans();
            saleTrans.setSaleTransId(getSequence("sale_trans_seq"));
            saleTrans.setSaleTransCode(CommonDAO.formatTransCode(saleTrans.getSaleTransId()));
            saleTrans.setSaleTransDate(getSysdate());
            saleTrans.setSaleTransType(Constant.SALE_TRANS_TYPE_AGENT);
            saleTrans.setStatus(Constant.SALE_TRANS_STATUS_NOT_BILLED);
            saleTrans.setCheckStock(Constant.SALE_TRANS_NON_CHECK_STOCK);
            saleTrans.setShopId(userToken.getShopId());
            saleTrans.setStaffId(userToken.getUserID());
            saleTrans.setPayMethod(payMethod);
            saleTrans.setAmountTax(getTotalPriceAfterRate());//Tong tien phai tra
            saleTrans.setDiscount(getDiscountNotTax());//Tong tien chiet khau111
            saleTrans.setAmountNotTax(getTotalPrice());//Tổng ti�?n chưa thuế
            saleTrans.setTax(getTotalTaxMoney());//Tông ti�?n thuế
            saleTrans.setCustName(shopBO.getShopCode() + " - " + shopBO.getName());
            saleTrans.setTin(tin);
            saleTrans.setAddress(address);
            saleTrans.setReasonId(Long.parseLong(reason));
            saleTrans.setReceiverId(stockTransBO.getToOwnerId());
            saleTrans.setReceiverType(stockTransBO.getToOwnerType());
            saleTrans.setStockTransId(stockTransBO.getStockTransId());//Giao dich kho
            saleTrans.setTelecomServiceId(telecomServiceId);//Loai dich vu
            saleTrans.setVat(price.getVat());//Ti gia VAT
            saleTrans.setCurrency(price.getCurrency());
            saleTransDAO.save(saleTrans);


            //thuc hien check han muc so luong va han muc tien
            Long amountDebit = 0L;
            Shop shop = shopDAO.findById(userToken.getShopId());
            String pricePolicy = shop.getPricePolicy();

            for (StockTransDetail iSTD : lstStockTransDetail) {
                SaleTransDetail saleTransDetail = saveSaleTransDetail(saleTrans, iSTD, mapStockModelDiscount);
                if (saleTransDetail == null) {
                    req.setAttribute("returnMsg", "Lỗi tạo giao dịch bán cho đại lý.");
                    setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().getTransaction().begin();
                    return "saveFromExpCommand";
                }
                detailDAO.save(saleTransDetail);

//                Long basePrice = priceDAO.findBasicPrice(iSTD.getStockModelId(), pricePolicy);
//                if (basePrice == null && checkStockOwnerTmpDebit(shop.getShopId(), Constant.OWNER_TYPE_SHOP)) {
//                    req.setAttribute("returnMsg", "ERR.SAE.143");
//                    setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
//                    getSession().clear();
//                    getSession().getTransaction().rollback();
//                    getSession().getTransaction().begin();
//                    return "saveFromExpCommand";
//                } else {
//                    if (basePrice == null) {
//                        basePrice = 0L;
//                    }
//                    amountDebit += basePrice * iSTD.getQuantityRes();
//                    //check han muc so luong
//                    if (!checkAddDebitTotal(stockTransBO.getToOwnerId(), stockTransBO.getFromOwnerType(), iSTD.getStockModelId(), Constant.STATE_NEW, Constant.STATUS_DEBIT_DEPOSIT, iSTD.getQuantityRes())) {
//                        req.setAttribute("returnMsg", "ERR.SAE.148");
//                        setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
//                        getSession().clear();
//                        getSession().getTransaction().rollback();
//                        getSession().getTransaction().begin();
//                        return "saveFromExpCommand";
//                    }
//                }
            }

            //check han muc tien
            if (!checkAddDebit(shopBO.getShopId(), Constant.OWNER_TYPE_SHOP, amountDebit)) {
                req.setAttribute("returnMsg", "ERR.SAE.146");
                setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().getTransaction().begin();
                return "saveFromExpCommand";
            }
            getSession().getTransaction().commit();
            getSession().getTransaction().begin();

            //Thong bao xu ly thanh cong
//            setDataForSaleAction(PAY_STATUS, OWNER_TYPE, idShop, sDate, eDate, userToken.getShopId(), true);
            searchSaleAction();
            req.setAttribute("returnMsg", "saleExp.warn.makeSaleService");
            pageForward = "saveFromExpCommand";

        } catch (Exception e) {
            log.info(e.getMessage());
            req.setAttribute("returnMsg", "saleExp.warn.error");
            setDataForSaleAction(PAY_STATUS, OWNER_TYPE, String.valueOf(idShop), sDate, eDate, userToken.getShopId(), true);
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().getTransaction().begin();
            return "saveFromExpCommand";
        }
        log.info("End method saveCommand of SaleDAO");
        return pageForward;
    }

    /**
     *
     * @param saleTrans
     * @param stockTransDetail
     * @param mapStockModelDiscount
     * @return
     * @throws Exception
     */
    private SaleTransDetail saveSaleTransDetail(SaleTrans saleTrans, StockTransDetail stockTransDetail, Map<Long, Double> mapStockModelDiscount) throws Exception {
        try {
            SaleTransDetail saleTransDetail = new SaleTransDetail();

            //Lay thong tin hang hoa
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(stockTransDetail.getStockModelId());
            if (stockModel != null) {
                saleTransDetail.setStockModelId(stockModel.getStockModelId());
                saleTransDetail.setStockModelCode(stockModel.getStockModelCode());
                saleTransDetail.setStockModelName(stockModel.getName());
            } else {
                throw new Exception("stockModel is null");
            }
            //Lay thong tin nhom hang hoa
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            StockType stockType = stockTypeDAO.findById(stockModel.getStockTypeId());
            if (stockType != null) {
                saleTransDetail.setStockTypeId(stockType.getStockTypeId());
                saleTransDetail.setStockTypeName(stockType.getName());
            } else {
                throw new Exception("stockType is null");
            }
            
            PriceDAO priceDAO = new PriceDAO();
            priceDAO.setSession(this.getSession());
            Price price = priceDAO.findById(stockTransDetail.getPriceId());
            if (price == null || price.getPriceId() == null) {
                throw new Exception("price is null");
            }
            

            saleTransDetail.setSaleTransDetailId(getSequence("sale_trans_detail_seq"));
            saleTransDetail.setSaleTransId(saleTrans.getSaleTransId());
            saleTransDetail.setSaleTransDate(saleTrans.getSaleTransDate());

            saleTransDetail.setStateId(Constant.STATE_NEW);
            saleTransDetail.setPriceId(price.getPriceId());//priceId
            
            if (!Constant.PRICE_AFTER_VAT) {//luu gia truoc thue
                saleTransDetail.setPrice(price.getPrice());
            } else {
                saleTransDetail.setPrice(price.getPrice() / (1 + price.getVat() / 100));
            }
            
            saleTransDetail.setPriceVat(price.getVat());
            saleTransDetail.setCurrency(price.getCurrency());            
            saleTransDetail.setQuantity(stockTransDetail.getQuantityRes());//so luong
            saleTransDetail.setAmount(saleTransDetail.getPrice() * saleTransDetail.getQuantity());//thanh tien

            saleTransDetail.setDiscountId(stockTransDetail.getDiscountId());//discountId
            saleTransDetail.setDiscountAmount(0.0);
            if (mapStockModelDiscount != null && !mapStockModelDiscount.isEmpty()) {
                Double discountAmount = mapStockModelDiscount.get(stockModel.getStockModelId());
                if (discountAmount != null) {//chiet khau
                    saleTransDetail.setDiscountAmount(discountAmount);
                }
            }

           saleTransDetail.setAmountBeforeTax(saleTransDetail.getAmount() - saleTransDetail.getDiscountAmount());//truoc thue
            saleTransDetail.setVatAmount(saleTransDetail.getAmountBeforeTax() * saleTransDetail.getPriceVat() / 100.0);//thue
            saleTransDetail.setAmountTax(saleTransDetail.getAmountBeforeTax() * saleTransDetail.getPriceVat() / 100.0);//thue
            saleTransDetail.setAmountAfterTax(saleTransDetail.getAmountBeforeTax() + saleTransDetail.getAmountTax());//sau thue

            saleTransDetail.setTransferGood("0");
            return saleTransDetail;
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return null;
    }
    public boolean chkNumber(String sNumber) {
        if (sNumber == null || "".equals(sNumber.trim())) {
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
     *
     * @param serial
     * @return
     * @author tutm1
     * @date 21/08/2012
     * @purpose kiem tra trang thai serial da khoa hay chua, neu da khoa
     */
    public String checkCardStatus(String serial) {
        String error = "";
        try {
            // tao ket noi toi tong dai Provisioning
            String processCode = ResourceBundleUtils.getResource("PRO_SER_ACT_PROCESS_CODE");

            ObjectClientChannel channel = new ObjectClientChannel(ResourceBundleUtils.getResource("PRO_SER_ACT_URL"),
                    Integer.parseInt(ResourceBundleUtils.getResource("PRO_SER_ACT_PORT")),
                    ResourceBundleUtils.getResource("PRO_SER_ACT_USER"),
                    ResourceBundleUtils.getResource("PRO_SER_ACT_PASS"), true);
            //Thoi gian timeout
            channel.setReceiverTimeout(Long.valueOf(ResourceBundleUtils.getResource("PRO_SER_ACT_TIMEOUT"))); //10 phut                
            channel.reconnect();

            ActiveCardDAO activeCard = new ActiveCardDAO();
            ViettelMsg response = activeCard.querySerial(serial, channel);

            if (response == null) {
                error = getText("ERR.ACTIVE.CARD.0003");
                System.out.println("Loi the cao : " + error);
            } else {
                System.out.println(response.toString());
                String valueCard = "";
                Object cardSupended = response.get("HOTCARDFLAG");
                String responseCode = String.valueOf(response.get("responseCode"));
                if (!"0".equals(responseCode)) {
                    error = getText("ChangeDamageGoods.card.notViewStatus");
                } else {
                    valueCard = String.valueOf(cardSupended);
                    //Neu trang thai khong hop le hoac khong o trang thai khoa ==> khong cho doi hang hong
                    if (valueCard == null || !valueCard.equals(Constant.CARD_STATUS_LOCK)) {
                        error = getText("ChangeDamageGoods.card.notlocked");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getText("ERR.ACTIVE.CARD.0003");
        }
        return error;
    }
}
