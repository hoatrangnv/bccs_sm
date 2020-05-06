package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.PriceForm;
import com.viettel.im.client.form.StockDepositForm;
import com.viettel.im.client.form.StockModelForm;
import com.viettel.im.common.util.QueryCryptUtils;
import java.util.List;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockModel;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.StockDeposit;
import com.viettel.im.database.BO.TelecomService;
import java.util.Date;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import com.viettel.im.client.bean.ListMapBean;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.ActionLogDetail;
import java.util.ResourceBundle;

/**
 *
 * @author tamdt1
 *
 */
public class GoodsDefDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(GoodsDefDAO.class);
    private String pageForward;
    //dinh nghia cac hang so pageForward
    private final String STOCK_MODEL_OVERVIEW = "stockModelOverview";
    private final String SEARCH_STOCK_MODEL = "searchStockModel";
    private final String STOCK_MODEL_INFO = "stockModelInfo";
    private final String STOCK_MODEL = "stockModel";
    private final String STOCK_PRICE = "stockPrice";
    private final String STOCK_DEPOSIT = "stockDeposit";
    private final String LIST_STOCK_MODELS = "listStockModels";
    private final String LIST_PRICES = "listPrices";
    private final String LIST_STOCK_DEPOSIT = "listStockDeposit";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_IS_ADD_MODE = "isAddMode"; //flag thiet lap che do them thong tin moi
    private final String REQUEST_IS_EDIT_MODE = "isEditMode"; //flag thiet lap che do sua thong tin da co
    private final String REQUEST_IS_VIEW_MODE = "isViewMode"; //flag thiet lap che do xem thong tin
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_STOCK_TYPES = "listStockTypes";
    private final String REQUEST_LIST_CHANNELS = "listChannelTypes";
    private final String REQUEST_LIST_PRICE_TYPES = "listPriceTypes";
    private final String REQUEST_LIST_PRICE_POLICIES = "listPricePolicies";
    private final String REQUEST_LIST_TELECOM_SERVICES = "listTelecomServices";
    private final String REQUEST_LIST_STOCK_MODEL_UNITS = "listStockModelUnits";
    private final String REQUEST_LIST_STOCK_MODELS = "listStockModels";
    private final String REQUEST_LIST_PRICES = "listPrices";
    private final String REQUEST_LIST_STOCK_DEPOSIT = "listStockDeposits";
    private final String SESSION_CURR_STOCK_TYPE_ID = "currentStockTypeId";
    private final String SESSION_CURR_STOCK_MODEL_ID = "currentStockModelId";
    private final String SESSION_CURR_PRICE_ID = "currentPriceId";
    private final String SESSION_CURR_STOCK_DEPOSIT_ID = "currentStockDepositId";
    private final String REQUEST_LIST_CURRENCY = "listCurrency";
    //#TruongNQ5 20140725 R6237
    private final String REQUEST_LIST_RVN_SEVICE_QUALITY = "listRvnServiceQuality";
    private final String REQUEST_LIST_RVN_SEVICE = "listRvnService";
    String schemaRvnService = ResourceBundleUtils.getResource(Constant.SCHEMA_RVN_SEVICE);
    //End TruongNQ5
    /*
     * @Author : hieptd
     */
    private final String REQUEST_LIST_CHECK_STOCK_CHANNEL = "listCheckStockChannel";
    //
    private List listItems = new ArrayList();

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }
    //khai bao bien form
    private StockModelForm stockModelForm = new StockModelForm();
    private PriceForm priceForm = new PriceForm();
    private StockDepositForm stockDepositForm = new StockDepositForm();

    public StockModelForm getStockModelForm() {
        return stockModelForm;
    }

    public void setStockModelForm(StockModelForm stockModelForm) {
        this.stockModelForm = stockModelForm;
    }

    public PriceForm getPriceForm() {
        return priceForm;
    }

    public void setPriceForm(PriceForm priceForm) {
        this.priceForm = priceForm;
    }

    public StockDepositForm getStockDepositForm() {
        return stockDepositForm;
    }

    public void setStockDepositForm(StockDepositForm stockDepositForm) {
        this.stockDepositForm = stockDepositForm;
    }

    /**
     *
     * author datpv date: 16/04/2009 man hinh dau tien, hien thi tree va danh
     * sach cac stockType modified tamdt1, 17/04/2009
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();

        //xoa tat ca cac bien session
        removeAllSessionAttribute();

        //lay danh sach cac mat hang
        List<StockModel> listStockModel = getListStockModels(null);
        req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModel);
        /*
         * author hieptd truyen gia tri vao select box chon kenh
         */
        List listCheckStockChannel = getListCheckStockChannel();
        req.setAttribute(REQUEST_LIST_CHECK_STOCK_CHANNEL, listCheckStockChannel);

        log.info("End method preparePage of GoodsDefDAO");
        pageForward = STOCK_MODEL_OVERVIEW;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 05/10/2009 xoa tat ca cac bien session
     *
     */
    private void removeAllSessionAttribute() {
        HttpServletRequest req = getRequest();

        //xoa tat ca cac bien session
        req.getSession().removeAttribute(SESSION_CURR_STOCK_TYPE_ID);
        req.getSession().removeAttribute(SESSION_CURR_STOCK_MODEL_ID);
        req.getSession().removeAttribute(SESSION_CURR_PRICE_ID);
        req.getSession().removeAttribute(SESSION_CURR_STOCK_DEPOSIT_ID);
    }

    /**
     *
     * author : tamdt1 date : 23/11/2009 purpose : tim kiem danh sach cac
     * stockModel thoa man dieu kien tim kiem
     *
     */
    public String searchStockModel() throws Exception {
        log.info("Begin method searchStockModel of GoodsDefDAO...");

        HttpServletRequest req = getRequest();
        List<StockModel> listStockModel = new ArrayList<StockModel>();

        StringBuffer strQuery = new StringBuffer("from StockModel where 1 = 1 ");
        List listParameters = new ArrayList();

        Long stockTypeId = this.stockModelForm.getStockTypeId();
        String stockModelCode = this.stockModelForm.getStockModelCode();
        String stockModelName = this.stockModelForm.getName();
        Integer checkStockChannel = this.stockModelForm.getCheckStockChannel();

        if (stockTypeId != null && stockTypeId.compareTo(0L) > 0) {
            strQuery.append("and stockTypeId = ? ");
            listParameters.add(stockTypeId);
        }
        /*
         * @author : hieptd @descirption : modify (equal --> like)
         */
        if (stockModelCode != null && !stockModelCode.trim().equals("")) {
            strQuery.append("and lower(stockModelCode) like ? ");
            listParameters.add("%" + stockModelCode.trim().toLowerCase() + "%");
        }
        if (stockModelName != null && !stockModelName.trim().equals("")) {
            strQuery.append("and lower(name) like ? ");
            listParameters.add("%" + stockModelName.trim().toLowerCase() + "%");
        }
        /*
         * @author : hieptd @descirption : add Check stock channel
         */
        if (checkStockChannel != null) {
            strQuery.append("and lower(checkStockChannel) = ? ");
            listParameters.add(checkStockChannel);
        }

        strQuery.append("order by stockModelCode ");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameters.size(); i++) {
            query.setParameter(i, listParameters.get(i));
        }

        listStockModel = query.list();

        if (listStockModel != null) {
            req.setAttribute(REQUEST_MESSAGE, "saleservices.searchMessage");
            List listParam = new ArrayList();
            listParam.add(listStockModel.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
        }

        req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModel);
        stockModelForm.setCheckStockChannel(checkStockChannel);
        log.info("End method searchStockModel of GoodsDefDAO");
        pageForward = LIST_STOCK_MODELS;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 23/11/2009 purpose : hien thi danh sach tat cac
     * cac stock_model
     *
     */
    public String displayAllStockModel() throws Exception {
        log.info("Begin method displayAllStockModel of GoodsDefDAO...");

        HttpServletRequest req = getRequest();

        //lay danh sach cac mat hang chuyen len bien request
        List<StockModel> listStockModel = getListStockModels(null);
        req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModel);
        //req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModel);
        /*
         * author hieptd truyen gia tri vao select box chon kenh
         */
        List listCheckStockChannel = getListCheckStockChannel();
        req.setAttribute(REQUEST_LIST_CHECK_STOCK_CHANNEL, listCheckStockChannel);

        //reset form
        this.stockModelForm.resetForm();

        log.info("End method displayAllStockModel of GoodsDefDAO");
        pageForward = SEARCH_STOCK_MODEL;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 16/04/2009 hien thi danh sach cac stockModel cua 1
     * stockType
     *
     */
    public String displayAllStockModelInStockType() throws Exception {
        log.info("Begin method displayAllStockModelInStockType of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();

        //lay stockTypeId can hien thi danh sach stockModel
        String strSelectedStockTypeId = req.getParameter("selectedStockTypeId");
        Long stockTypeId = 0L;
        if (strSelectedStockTypeId != null) {
            try {
                stockTypeId = new Long(strSelectedStockTypeId);
            } catch (NumberFormatException ex) {
                stockTypeId = -1L;
            }
        }
        //lay danh sach tat ca cac stockModel cua stockTypeId
        List<StockModel> listStockModels = getListStockModels(stockTypeId);
        req.setAttribute(REQUEST_LIST_STOCK_MODELS, listStockModels);
        /*
         * author hieptd truyen gia tri vao select box chon kenh
         */
        List listCheckStockChannel = getListCheckStockChannel();
        req.setAttribute(REQUEST_LIST_CHECK_STOCK_CHANNEL, listCheckStockChannel);
        //reset form
        this.stockModelForm.resetForm();

        //thiet lap stock_type
        this.stockModelForm.setStockTypeId(stockTypeId);

        log.info("End method displayAllStockModelInStockType of GoodsDefDAO");
        pageForward = SEARCH_STOCK_MODEL;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 24/09/2009 hien thi thong tin price cua 1 stockModel
     *
     */
    public String displayPrice() throws Exception {
        log.info("Begin method displayPrice of GoodsDAO...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        String strPriceId = req.getParameter("selectedPriceId");
        Long priceId;
        if (strPriceId != null) {
            try {
                priceId = Long.valueOf(strPriceId);
            } catch (NumberFormatException ex) {
                priceId = -1L;
            }
        } else {
            priceId = (Long) req.getSession().getAttribute(SESSION_CURR_PRICE_ID);
        }

        Price price = getPriceById(priceId);
        if (price != null) {
            //
            this.priceForm.copyDataFromBO(price);
            //thiet lap cac bien can thiet cho combobox
            getComboboxDataPrice();
        } else {
            //
            this.priceForm.resetForm();
        }

        //lay danh sach gia thuoc mat hang
        Long stockModelId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_MODEL_ID);
        List<Price> listPrice = getListPrices(session, stockModelId);
        req.setAttribute(REQUEST_LIST_PRICES, listPrice);

        //thiet lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        log.info("End method displayPrice of GoodsDefDAO");
        pageForward = STOCK_PRICE;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 21/04/2009 chuan bi form them price moi
     *
     */
    public String prepareAddPrice() throws Exception {
        log.info("Begin method prepareAddPrice of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();

        //giu lai id cua stockModel ma gia thuoc ve
        Long stockModelId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_MODEL_ID);
        this.priceForm.resetForm();
        this.priceForm.setStockModelId(stockModelId);

        //thiet lap cac bien can thiet cho combobox
        getComboboxDataPrice();

        //thiet lap che do them moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = STOCK_PRICE;
        log.info("End method prepareAddPrice of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 21/04/2009 chuan bi form sua thong tinn price price
     * moi
     *
     */
    public String prepareEditPrice() throws Exception {
        log.info("Begin method prepareEditPrice of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();
        Long priceId = this.priceForm.getPriceId();
        Price price = getPriceById(priceId);
        if (price != null) {
            //
            this.priceForm.copyDataFromBO(price);

            //thiet lap cac bien can thiet cho combobox
            getComboboxDataPrice();

            //thiet lap che do sua thong tin gia
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);

        }

        pageForward = STOCK_PRICE;
        log.info("End method prepareEditPrice of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 21/04/2009 them price moi vao DB moi hoac sua thong
     * tin DB da co
     *
     */
    public String addOrEditPrice() throws Exception {
        log.info("Begin method addOrEditPrice of GoodsDefDAO ...");

        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */

        HttpServletRequest req = getRequest();
        Session session = getSession();

        Long priceId = this.priceForm.getPriceId();
        Price price = getPriceById(priceId);
        if (price == null) {

            //----------------------------------------------------------------
            //truong hop them price moi
            if (!checkValidPriceToAdd()) {
                //thiet lap cac bien can thiet cho combobox
                getComboboxDataPrice();
                //thiet lap che do them moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);
                //return
                pageForward = STOCK_PRICE;
                log.info("End method addOrEditPrice of GoodsDefDAO");
                return pageForward;
            }

            //luu du lieu vao db
            price = new Price();
            this.priceForm.copyDataToBO(price);
            priceId = getSequence("PRICE_SEQ");
            price.setPriceId(priceId);
            price.setCreateDate(getSysdate());
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            price.setUserName(userToken.getLoginName());
            session.save(price);
            session.flush();

            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(null, price));
            saveLog(Constant.ACTION_ADD_GOOD_PRICE, "Thêm giá mặt hàng", lstLogObj, price.getPriceId(), Constant.DEFINE_GOODS, Constant.ADD);
            /*
             * LamNV ADD STOP
             */

            //
            this.priceForm.setPriceId(priceId);
            req.getSession().setAttribute(SESSION_CURR_PRICE_ID, priceId);

            //
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.addsuccessful");

        } else {
            //----------------------------------------------------------------
            //truong hop cap nhat thong tin cua price da co
            if (!checkValidPriceToEdit()) {
                //thiet lap cac bien can thiet cho combobox
                getComboboxDataPrice();

                //thiet lap che do them moi
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);

                //return
                log.info("End method addOrEditPrice of GoodsDefDAO");
                pageForward = STOCK_PRICE;
                return pageForward;
            }

            /*
             * LamNV ADD START
             */
            Price oldPrice = new Price();
            BeanUtils.copyProperties(oldPrice, price);
            /*
             * LamNV ADD STOT
             */

            //luu du lieu vao db
            this.priceForm.copyDataToBO(price);
            session.update(price);
            session.flush();

            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(oldPrice, price));
            saveLog(Constant.ACTION_UPDATE_GOOD_PRICE, "Cập nhật giá mặt hàng", lstLogObj, price.getPriceId(), Constant.DEFINE_GOODS, Constant.EDIT);
            /*
             * LamNV ADD STOP
             */

            //
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.editsuccessful");
        }

        //
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //cap nhat lai list
        List<Price> listPrices = (List<Price>) getListPrices(session, price.getStockModelId());
        req.setAttribute(REQUEST_LIST_PRICES, listPrices);

        //thiet lap cac bien can thiet cho combobox
        getComboboxDataPrice();

        pageForward = STOCK_PRICE;
        log.info("End method addOrEditPrice of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 29/04/2009 xoa gia mat mat hang
     *
     */
    public String deletePrice() throws Exception {
        log.info("Begin method deletePrice of GoodsDefDAO ...");

        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */

        HttpServletRequest req = getRequest();
        Session session = getSession();

        Long priceId = this.priceForm.getPriceId();
        Price price = getPriceById(priceId);
        if (price != null) {
            try {
                //cap nhat CSDL
                session.delete(price);
                session.flush();
                /*
                 * LamNV ADD START
                 */
                lstLogObj.add(new ActionLogsObj(price, null));
                saveLog(Constant.ACTION_DELETE_GOOD_PRICE, "Xóa giá mặt hàng", lstLogObj, price.getPriceId(), Constant.DEFINE_GOODS, Constant.DELETE);
                /*
                 * LamNV ADD STOP
                 */

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.delete.priceIsUsing");

                pageForward = STOCK_PRICE;
                log.info("End method deletePrice of GoodsDefDAO");
                return pageForward;
            }

            //cap nhat lai list
            List<Price> listPrices = (List<Price>) getListPrices(session, price.getStockModelId());
            req.setAttribute(REQUEST_LIST_PRICES, listPrices);

            //day phan tu dau tien trong list len thay the phan tu vua bi xoa
            if (listPrices != null && listPrices.size() > 0) {
                Price tmpPrice = listPrices.get(0);
                req.getSession().setAttribute(SESSION_CURR_PRICE_ID, tmpPrice.getPriceId());
                displayPrice();
            }

            //reset form
            this.priceForm.resetForm();
            req.getSession().removeAttribute(SESSION_CURR_PRICE_ID);
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.delsuccessful");

        }

        //
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //return
        pageForward = STOCK_PRICE;
        log.info("End method deletePrice of GoodsDefDAO");
        return pageForward;

    }

//    /**
//     *
//     * author tamdt1
//     * date: 21/04/2009
//     * them refresh lai danh sach gia cua mat hang
//     *
//     */
//    public String refreshListPrices() throws Exception {
//        log.info("Begin method refreshListPrices of GoodsDefDAO ...");
//        HttpServletRequest req = getRequest();
//        //Boolean check = Boolean.parseBoolean(req.getSession().getAttribute("checkView").toString());
//        Long stockModelId = Long.parseLong(req.getSession().getAttribute("stockModelId").toString());
//        req.getSession().setAttribute(SESSION_LIST_PRICES, getListPrices(stockModelId));
//
//        pageForward = LIST_PRICES;
//
//        log.info("End method refreshListPrices of GoodsDefDAO");
//
//        return pageForward;
//    }
//    /**
//     *
//     * author Vunt
//     * date: 12/09/2009
//     * refresh lai gia của mặt hang khi click checkbox
//     *
//     */
//    public String onChangeClick() throws Exception {
//        log.info("Begin method refreshListPrices of GoodsDefDAO ...");
//        HttpServletRequest req = getRequest();
//        Boolean check = Boolean.parseBoolean(req.getParameter("checkView"));
//        Long stockModelId = Long.parseLong(req.getParameter("stockModelId"));
//        req.getSession().setAttribute("check", check);
//        req.getSession().setAttribute("stockModelId", stockModelId);
//        req.getSession().setAttribute(SESSION_LIST_PRICES, getListPrices(stockModelId));
//        pageForward = LIST_PRICES;
//        log.info("End method refreshListPrices of GoodsDefDAO");
//        return pageForward;
//    }
    /**
     *
     * author tamdt1 date: 21/04/2009 lay Price co id = pricelId
     *
     */
    private Price getPriceById(Long priceId) {
        Price price = null;
        if (priceId == null) {
            return price;
        }
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(this.getSession());
        price = priceDAO.findById(priceId);

        return price;
    }

    /**
     *
     * author tamdt1 date: 21/04/2009 lay Price co id = pricelId
     *
     */
    private StockDeposit getStockDepositById(Long stockDepositId) {
        StockDeposit stockDeposit = null;
        if (stockDepositId == null) {
            return stockDeposit;
        }
        StockDepositDAO stockDepositDAO = new StockDepositDAO();
        stockDepositDAO.setSession(this.getSession());
        stockDeposit = stockDepositDAO.findById(stockDepositId);

        return stockDeposit;
    }

    /**
     *
     * author tamdt1 date: 21/04/2009 lay Price co id = pricelId trong bien
     * session
     *
     */
    private Price getPriceInListById(List<Price> listPrices, Long priceId) {
        Price price = null;
        if (listPrices == null || listPrices.size() == 0 || priceId == null) {
            return price;
        }

        for (int i = 0; i < listPrices.size(); i++) {
            if (listPrices.get(i).getPriceId().equals(priceId)) {
                price = listPrices.get(i);
                break;
            }
        }

        return price;
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 lay StockModel co id = stockModelId trong
     * bien session
     *
     */
    private StockModel getStockModelInListById(List<StockModel> listStockModels, Long stockModelId) {
        StockModel stockModel = null;
        if (listStockModels == null || listStockModels.size() == 0 || stockModelId == null) {
            return stockModel;
        }

        for (int i = 0; i < listStockModels.size(); i++) {
            if (listStockModels.get(i).getStockModelId().equals(stockModelId)) {
                stockModel = listStockModels.get(i);
                break;
            }
        }

        return stockModel;
    }

    /**
     *
     * author tamdt1 date: 16/04/2009 hien thi thong tin stockModel
     *
     */
    public String displayStockModel() throws Exception {
        log.info("Begin method displayStockModel of GoodsDAO ...");

        HttpServletRequest req = getRequest();

        String strSelectedStockModelId = req.getParameter("selectedStockModelId");
        Long stockModelId;
        if (strSelectedStockModelId != null) {
            try {
                stockModelId = new Long(strSelectedStockModelId);
            } catch (NumberFormatException ex) {
                stockModelId = -1L;
            }
        } else {
            stockModelId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_MODEL_ID);
        }

        req.getSession().setAttribute(SESSION_CURR_STOCK_MODEL_ID, stockModelId);

        //chuyen du lieu len form
        StockModel stockModel = getStockModelById(stockModelId);
        if (stockModel != null) {
            stockModelForm.copyDataFromBO(stockModel);
            //thiet lap danh sach du lieu cho cac combobox stockModel
            //TruongNQ5 20140725 R6237 Khoi tao gia tri cho 2 combobox Chi tieu
            if (stockModel.getStockModelCode() != null && !"".equals(stockModel.getStockModelCode())) {
                prepareEditRVNService(stockModel.getStockModelCode());
            }
            //End TruongNQ5
        }

        //lay du lieu cho combobox
        getComboboxDataModel();

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        pageForward = STOCK_MODEL_INFO;
        log.info("End method displayStockModel of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 chuan bi form them stockModel
     *
     */
    public String prepareAddStockModel() throws Exception {
        log.info("Begin method prepareAddStockModel of GoodsDefDAO...");

        HttpServletRequest req = getRequest();

        //giu lai 2 bien de thiet lap sau khi reset form
        Long stockTypeId = this.stockModelForm.getStockTypeId();
        String stockTypeName = this.stockModelForm.getStockTypeName();
        //
        this.stockModelForm.resetForm();
        //
        this.stockModelForm.setStockTypeId(stockTypeId);
        this.stockModelForm.setStockTypeName(stockTypeName);

        //kiem tra loai mat hang co quan ly theo serial hay khong
        StockType stockType = getStockTypeById(stockTypeId);
        if (stockType != null && stockType.getTableName() != null) {
            //doi voi mat hang quan ly theo serial
            this.stockModelForm.setCheckSerial(true);
        }

        //thiet lap danh sach du lieu cho cac combobox
        getComboboxDataModel();

        //xac lap che do chuan bi them stockModel moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = STOCK_MODEL;
        log.info("End method prepareAddStockModel of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 21/11/2010 desc : chuan bi form them stockModel
     * cho lan dau tien
     *
     */
    public String prepareAddStockModelFirstTime() throws Exception {
        log.info("Begin method prepareAddStockModelFirstTime of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();

        //
        this.stockModelForm.resetForm();

        //thiet lap danh sach du lieu cho cac combobox
        getComboboxDataModel();

        //xac lap che do chuan bi them discountGroup moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = STOCK_MODEL_INFO;
        log.info("End method prepareAddStockModelFirstTime of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 chuan bi form sua thong tin stockModel
     *
     */
    public String prepareEditStockModel() throws Exception {
        log.info("Begin method prepareEditStockModel of GoodsDefDAO...");

        HttpServletRequest req = getRequest();

        Long stockModelId = this.stockModelForm.getStockModelId();
        StockModel stockModel = getStockModelById(stockModelId);
        if (stockModel != null) {
            this.stockModelForm.copyDataFromBO(stockModel);
            //TruongNQ5 20140725 R6237 Khoi tao gia tri cho 2 combobox Chi tieu
            if (stockModel.getStockModelCode() != null && !"".equals(stockModel.getStockModelCode())) {
                prepareEditRVNService(stockModel.getStockModelCode());
            }
            //End TruongNQ5

            //xac lap che do chuan bi sua thong tin stockModel
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);
        }

        //thiet lap danh sach du lieu cho cac combobox
        getComboboxDataModel();

        pageForward = STOCK_MODEL;
        log.info("End method prepareEditStockModel of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 them stockModel moi hoac sua thong tin
     * stockModel da co
     *
     */
    public String addOrEditStockModel() throws Exception {
        log.info("Begin method addOrEditStockModel of GoodsDefDAO...");
        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */

        HttpServletRequest req = getRequest();
        //TruongNQ5 20140725 Chi tieu san luong va chi tieu doanh thu
        Long rvnServiceId = this.stockModelForm.getRvnServiceId();
        Long rvnServiceQualityId = this.stockModelForm.getRvnServiceQualityId();
        //End TruongNQ5 
        Long stockModelId = this.stockModelForm.getStockModelId();
        StockModel stockModel = getStockModelById(stockModelId);
        if (stockModel == null) {
            //----------------------------------------------------------------
            //them stockModel moi
            if (!checkValidStockModelToAdd()) {
                //thiet lap danh sach du lieu cho cac combobox
                getComboboxDataModel();
                //xac lap che do chuan bi them stockModel moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);
                //return
                pageForward = STOCK_MODEL;
                log.info("End method addOrEditStockModel of GoodsDefDAO");
                return pageForward;
            }

            //luu du lieu vao db
            stockModel = new StockModel();
            this.stockModelForm.copyDataToBO(stockModel);
            stockModelId = getSequence("STOCK_MODEL_SEQ");
            stockModel.setStockModelId(stockModelId);
            stockModel.setStatus(Constant.STATUS_USE);
            getSession().save(stockModel);
            //TruongNQ5 20140725 Insert 2 Chi tieu san luong va chi tieu doanh thu vao DB
            try {
                if (rvnServiceId != null) {
                    String sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query query = getSession().createSQLQuery(sql);
                    query.setParameter(0, index);
                    query.setParameter(1, Constant.DEPT_ID_MOV);
                    query.setParameter(2, this.stockModelForm.getRvnServiceId());
                    query.setParameter(3, this.stockModelForm.getStockModelCode());
                    query.setParameter(4, Constant.TYPE_GOODS);
                    query.setParameter(5, Constant.STATUS_ACTIVE);
                    query.executeUpdate();
                    saveLogRVNServiceDeptMap(Constant.ACTION_DEFINE_GOOD, getText("add.new.sales.criteria"),"rvn_service_dept_map"
            , index, "service_id", "",this.stockModelForm.getRvnServiceId().toString());
                }
                if (rvnServiceQualityId != null) {
                    String sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query query = getSession().createSQLQuery(sql);
                    query.setParameter(0, index);
                    query.setParameter(1, Constant.DEPT_ID_MOV);
                    query.setParameter(2, this.stockModelForm.getRvnServiceQualityId());
                    query.setParameter(3, this.stockModelForm.getStockModelCode());
                    query.setParameter(4, Constant.TYPE_GOODS);
                    query.setParameter(5, Constant.STATUS_ACTIVE);
                    query.executeUpdate();
                    saveLogRVNServiceDeptMap(Constant.ACTION_DEFINE_GOOD, getText("add.new.quality.criteria"),"rvn_service_dept_map"
            , index, "service_id", "",this.stockModelForm.getRvnServiceQualityId().toString());
                }
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();
            } catch (Exception e) {
                e.printStackTrace();
                this.getSession().getTransaction().rollback();
                req.setAttribute(REQUEST_MESSAGE, "Error.RVN.Service.Dept.Map");
                pageForward = STOCK_MODEL;
                return pageForward;
            }
            //End TruongNQ5
            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(null, stockModel));
            saveLog(Constant.ACTION_DEFINE_GOOD, "Thêm mới mặt hàng mới", lstLogObj, stockModel.getStockModelId(), Constant.DEFINE_GOODS, Constant.ADD);
            /*
             * LamNV ADD STOP
             */

            //
            this.stockModelForm.setStockModelId(stockModelId);
            req.getSession().setAttribute(SESSION_CURR_STOCK_MODEL_ID, stockModelId);

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "goodsdef.addsuccessful");

        } else {
            //----------------------------------------------------------------
            //sua thong tin stockModel da co
            if (!checkValidStockModelToEdit()) {
                //thiet lap danh sach du lieu cho cac combobox
                getComboboxDataModel();
                //xac lap che do chuan bi them stockModel moi
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);
                //return
                pageForward = STOCK_MODEL;
                log.info("End method addOrEditStockModel of GoodsDefDAO");
                return pageForward;
            }

            //luu du lieu vao db
            /*
             * LamNV ADD START
             */
            StockModel oldStockModel = new StockModel();
            BeanUtils.copyProperties(oldStockModel, stockModel);
            /*
             * LamNV ADD STOP
             */

            this.stockModelForm.copyDataToBO(stockModel);
            getSession().update(stockModel);
            //TruongNQ5 20140725  R6237 cap nhat 2 gia tri cua 2 combobox chi tieu
            updateRVNService(rvnServiceId, this.stockModelForm.getStockModelCode(), this.stockModelForm.getServiceIndex());
            updateRVNServiceQuality(rvnServiceQualityId, this.stockModelForm.getStockModelCode(), this.stockModelForm.getServiceQualityIndex());
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();
            //End TruongNQ5

            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(oldStockModel, stockModel));
            saveLog(Constant.ACTION_UPDATE_GOOD, "Cập nhật mặt hàng", lstLogObj, stockModel.getStockModelId(), Constant.DEFINE_GOODS, Constant.EDIT);
            /*
             * LamNV ADD STOP
             */

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "goodsdef.editsuccessful");

        }

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //lay du lieu cho combobox
        getComboboxDataModel();

        //retutn
        pageForward = STOCK_MODEL;
        log.info("End method addOrEditStockModel of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 xoa stockModel
     *
     */
    public String delStockModel() throws Exception {
        log.info("Begin method delStockModel of GoodsDefDAO ...");
        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */

        Session session = getSession();
        HttpServletRequest req = getRequest();
        Long stockModelId = this.stockModelForm.getStockModelId();
        StockModel stockModel = getStockModelById(stockModelId);
        if (stockModel != null) {
            try {
                //xoa price
                PriceDAO priceDAO = new PriceDAO();
                List<Price> lstPrice = priceDAO.findByProperty("stockModelId", stockModelId);
                if (!lstPrice.isEmpty()) {
                    for (Price price : lstPrice) {
                        session.delete(price);
                        lstLogObj.add(new ActionLogsObj(price, null));
                    }
                }

                //xoa stockmodel
                StockDepositDAO stockDepositDAO = new StockDepositDAO();
                List<StockDeposit> lstStockDeposit = stockDepositDAO.findByStockModelId(stockModelId);
                if (!lstStockDeposit.isEmpty()) {
                    for (StockDeposit stockDeposit : lstStockDeposit) {
                        session.delete(stockDeposit);
                        lstLogObj.add(new ActionLogsObj(stockDeposit, null));
                    }
                }

                session.delete(stockModel);
                lstLogObj.add(new ActionLogsObj(stockModel, null));
                saveLog(Constant.ACTION_DELETE_GOOD, "Xóa mặt hàng", lstLogObj, stockModel.getStockModelId(), Constant.DEFINE_GOODS, Constant.DELETE);
                session.flush();
                /*
                 * LamNV ADD STOP
                 */

                //reset form
                this.stockModelForm.resetForm();

                //
                req.getSession().removeAttribute(SESSION_CURR_STOCK_MODEL_ID);

                //
                req.setAttribute(REQUEST_MESSAGE, "goodsdef.delsuccessful");

            } catch (Exception ex) {
                ex.printStackTrace();

                req.setAttribute(REQUEST_MESSAGE, "goodsdef.error.delete");

                pageForward = STOCK_MODEL;
                log.info("End method delStockModel of GoodsDefDAO");
                return pageForward;
            }
        }

        pageForward = STOCK_MODEL;
        log.info("End method delStockModel of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 kiem tra tinh hop le cua 1 price truoc khi
     * them gia moi cho mat hang
     *
     */
    private boolean checkValidPriceToAdd() {
        HttpServletRequest req = getRequest();

        if (this.priceForm.getCurrency() == null || this.priceForm.getCurrency().trim().equals("")) {
            this.priceForm.setCurrency(Constant.CURRENCY_DEFAULT);
        }

//        Long price = this.priceForm.getPrice();
        String strPrice = this.priceForm.getPrice();
        if (strPrice == null || strPrice.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "Error. Price is invalid!");
            return false;
        }
        strPrice = strPrice.trim();
        this.priceForm.setPrice(strPrice);

        Double price = null;
        try {
            price = NumberUtils.convertStringtoNumber(strPrice);
        } catch (Exception ex) {
            ex.printStackTrace();
            price = null;
        }

        Long vat = this.priceForm.getVat();
        String strStaDate = this.priceForm.getStaDate();
        String strEndDate = this.priceForm.getEndDate();
        String priceType = this.priceForm.getType();
        String pricePolicy = this.priceForm.getPricePolicy();
        Long status = this.priceForm.getStatus();

        Long stockModelId = this.priceForm.getStockModelId();
        StockModel stockModel = getStockModelById(stockModelId);
        if (stockModel == null) {
            //khogn tim thay mat hang gan voi gia
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.stockModelNotFound");
            return false;
        }

        if (price == null || vat == null
                || priceType == null || priceType.trim().equals("")
                || pricePolicy == null || pricePolicy.trim().equals("")
                || status == null
                || strStaDate == null || strStaDate.trim().equals("")) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0.0) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidDate");
                return false;
            }
        }

        String priceTypeWithService = ResourceBundleUtils.getResource("PRICE_TYPE_WITH_SERVICE");
        if (this.priceForm.getStatus().equals(Constant.STATUS_USE) && !priceType.trim().equals(priceTypeWithService)) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot mat hang, doi voi 1 chinh sach gia, 1 loai gia, chi co 1 gia active vao 1 thoi diem
            //ngoai tru truong hop gia ban kem dich vu co the bao gom nhieu gia vao 1 thoi diem
            try {
                Long count;

                //kiem tra dieu kien doi voi staDate
                String strQuery = "select count(*) from Price where stockModelId = ? and pricePolicy = ? and type = ? "
                        + "and status = ? and staDate <= ? and (endDate >= ? or endDate is null)";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, this.priceForm.getStockModelId());
                query.setParameter(1, this.priceForm.getPricePolicy());
                query.setParameter(2, this.priceForm.getType());
                query.setParameter(3, Constant.STATUS_USE);
                query.setParameter(4, staDate);
                query.setParameter(5, staDate);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.duplicateTime");
                    return false;
                }

                //kiem tra dieu kien doi voi endDate
                if (endDate != null) {
                    query.setParameter(4, endDate);
                    query.setParameter(5, endDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.duplicateTime");
                        return false;
                    }
                } else {
                    strQuery = "select count(*) from Price where stockModelId = ? and pricePolicy = ? and type = ? "
                            + "and status = ? and staDate >= ? ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.priceForm.getStockModelId());
                    query.setParameter(1, this.priceForm.getPricePolicy());
                    query.setParameter(2, this.priceForm.getType());
                    query.setParameter(3, Constant.STATUS_USE);
                    query.setParameter(4, staDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.duplicateTime");
                        return false;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }

        this.priceForm.setStaDate(strStaDate.trim());
        this.priceForm.setEndDate(strEndDate.trim());
        this.priceForm.setType(priceType.trim());
        this.priceForm.setPricePolicy(pricePolicy.trim());

        return true;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 kiem tra tinh hop le cua 1 price truoc khi
     * sua thong tin ve gia cua 1 mat hang
     *
     */
    private boolean checkValidPriceToEdit() {
        HttpServletRequest req = getRequest();

        if (this.priceForm.getCurrency() == null || this.priceForm.getCurrency().trim().equals("")) {
            this.priceForm.setCurrency(Constant.CURRENCY_DEFAULT);
        }

//        Long price = this.priceForm.getPrice();
        String strPrice = this.priceForm.getPrice();
        if (strPrice == null || strPrice.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "Error. Price is invalid!");
            return false;
        }
        strPrice = strPrice.trim();
        this.priceForm.setPrice(strPrice);
        Double price = null;
        try {
            price = NumberUtils.convertStringtoNumber(strPrice);
        } catch (Exception ex) {
            ex.printStackTrace();
            price = null;
        }

        Long vat = this.priceForm.getVat();
        String strStaDate = this.priceForm.getStaDate();
        String strEndDate = this.priceForm.getEndDate();
        String priceType = this.priceForm.getType();
        String pricePolicy = this.priceForm.getPricePolicy();
        Long status = this.priceForm.getStatus();

        Long stockModelId = this.priceForm.getStockModelId();
        StockModel stockModel = getStockModelById(stockModelId);
        if (stockModel == null) {
            //khogn tim thay mat hang gan voi gia
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.stockModelNotFound");
            return false;
        }

        if (price == null || vat == null
                || priceType == null || priceType.trim().equals("")
                || pricePolicy == null || pricePolicy.trim().equals("")
                || status == null
                || strStaDate == null || strStaDate.trim().equals("")) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0.0) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.invalidDate");
                return false;
            }
        }

        String priceTypeWithService = ResourceBundleUtils.getResource("PRICE_TYPE_WITH_SERVICE");
        if (this.priceForm.getStatus().equals(Constant.STATUS_USE) && !priceType.trim().equals(priceTypeWithService)) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot mat hang, doi voi 1 chinh sach gia, 1 loai gia, chi co 1 gia active vao 1 thoi diem
            //ngoai tru truong hop gia ban kem dich vu co the bao gom nhieu gia vao 1 thoi diem
            try {
                Long count;

                //kiem tra dieu kien doi voi staDate
                String strQuery = "select count(*) from Price where stockModelId = ? and pricePolicy = ? and type = ? "
                        + "and status = ? and staDate <= ? and (endDate >= ? or endDate is null) and priceId <> ? ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, this.priceForm.getStockModelId());
                query.setParameter(1, this.priceForm.getPricePolicy());
                query.setParameter(2, this.priceForm.getType());
                query.setParameter(3, Constant.STATUS_USE);
                query.setParameter(4, staDate);
                query.setParameter(5, staDate);
                query.setParameter(6, this.priceForm.getPriceId());
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.duplicateTime");
                    return false;
                }

                //kiem tra dieu kien doi voi endDate
                if (endDate != null) {
                    query.setParameter(4, endDate);
                    query.setParameter(5, endDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.duplicateTime");
                        return false;
                    }
                } else {
                    strQuery = "select count(*) from Price where stockModelId = ? and pricePolicy = ? and type = ? "
                            + "and status = ? and staDate >= ? and priceId <> ? ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.priceForm.getStockModelId());
                    query.setParameter(1, this.priceForm.getPricePolicy());
                    query.setParameter(2, this.priceForm.getType());
                    query.setParameter(3, Constant.STATUS_USE);
                    query.setParameter(4, staDate);
                    query.setParameter(5, this.priceForm.getPriceId());
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.duplicateTime");
                        return false;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }

        this.priceForm.setStaDate(strStaDate.trim());
        this.priceForm.setEndDate(strEndDate.trim());
        this.priceForm.setType(priceType.trim());
        this.priceForm.setPricePolicy(pricePolicy.trim());

        return true;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 kiem tra tinh hop le cua stockModel truoc
     * khi them
     *
     */
    private boolean checkValidStockModelToAdd() {
        HttpServletRequest req = getRequest();

        String stockModelName = this.stockModelForm.getName();
        String stockModelCode = this.stockModelForm.getStockModelCode();
        Long telecomServicesId = this.stockModelForm.getTelecomServiceId();
        String strUnit = this.stockModelForm.getUnit();

        //kiem tra cac truong bat buoc
        if (stockModelName == null || stockModelName.trim().equals("")
                || stockModelCode == null || stockModelCode.trim().equals("")
                || telecomServicesId == null
                || strUnit == null || strUnit.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "goodsdef.error.requiredFieldsEmpty");
            return false;
        }
        //TruongNQ5 20140725 Validate chon 1 trong 2 chi tieu
        Long rvnServiceId = this.stockModelForm.getRvnServiceId();
        Long rvnServiceQualityId = this.stockModelForm.getRvnServiceQualityId();
        if (rvnServiceId == null && rvnServiceQualityId == null) {
            req.setAttribute(REQUEST_MESSAGE, "err.rvn.service");
            return false;
        }

        //kiem tra ma mat hang chi gom cac chu cai a-z, A-Z, 0-9, _
        stockModelCode = stockModelCode.trim();
        char[] arrTmp = stockModelCode.toCharArray();
        for (int i = 0; i < arrTmp.length; i++) {
            if (arrTmp[i] >= 'a' && arrTmp[i] <= 'z') {
                continue;
            } else if (arrTmp[i] >= 'A' && arrTmp[i] <= 'Z') {
                continue;
            } else if (arrTmp[i] >= '0' && arrTmp[i] <= '9') {
                continue;
            } else if (arrTmp[i] == '_') {
                continue;
            } else {
                req.setAttribute(REQUEST_MESSAGE, "goodsdef.error.codeContainsInvalidCharacters");
                return false;
            }
        }

        //kiem tra tinh trung lap cua ma mat hang
        String strQuery = "from StockModel where stockModelCode = ? and status = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModelCode.trim());
        query.setParameter(1, Constant.STATUS_USE);
        List listStockModels = query.list();
        if (listStockModels != null && listStockModels.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "Goodsdef.error.duplicateCode");
            return false;
        }

        this.stockModelForm.setName(stockModelName.trim());
        this.stockModelForm.setStockModelCode(stockModelCode.trim());

        return true;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 kiem tra tinh hop le cua stockModel truoc
     * khi update
     *
     */
    private boolean checkValidStockModelToEdit() {
        HttpServletRequest req = getRequest();

        String stockModelName = this.stockModelForm.getName();
        String stockModelCode = this.stockModelForm.getStockModelCode();
        Long telecomServicesId = this.stockModelForm.getTelecomServiceId();
        String strUnit = this.stockModelForm.getUnit();

        //kiem tra cac truong bat buoc
        if (stockModelName == null || stockModelName.trim().equals("")
                || stockModelCode == null || stockModelCode.trim().equals("")
                || telecomServicesId == null
                || strUnit == null || strUnit.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "goodsdef.error.requiredFieldsEmpty");
            return false;
        }
        //TruongNQ5 20140725 Validate chon 1 trong 2 chi tieu
        Long rvnServiceId = this.stockModelForm.getRvnServiceId();
        Long rvnServiceQualityId = this.stockModelForm.getRvnServiceQualityId();
        if (rvnServiceId == null && rvnServiceQualityId == null) {
            req.setAttribute(REQUEST_MESSAGE, "err.rvn.service");
            return false;
        }

        //kiem tra ma mat hang chi gom cac chu cai a-z, A-Z, 0-9, _
        stockModelCode = stockModelCode.trim();
        char[] arrTmp = stockModelCode.toCharArray();
        for (int i = 0; i < arrTmp.length; i++) {
            if (arrTmp[i] >= 'a' && arrTmp[i] <= 'z') {
                continue;
            } else if (arrTmp[i] >= 'A' && arrTmp[i] <= 'Z') {
                continue;
            } else if (arrTmp[i] >= '0' && arrTmp[i] <= '9') {
                continue;
            } else if (arrTmp[i] == '_') {
                continue;
            } else {
                req.setAttribute(REQUEST_MESSAGE, "goodsdef.error.codeContainsInvalidCharacters");
                return false;
            }
        }

        //kiem tra tinh trung lap cua ma mat hang
        String strQuery = "from StockModel where stockModelCode = ? and status = ? and stockModelId <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModelCode.trim());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, this.stockModelForm.getStockModelId());
        List listStockModels = query.list();
        if (listStockModels != null && listStockModels.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "goodsdef.error.duplicateCode");
            return false;
        }

        this.stockModelForm.setName(stockModelName.trim());
        this.stockModelForm.setStockModelCode(stockModelCode.trim());

        return true;
    }

    /**
     *
     * author tamdt1 date: 15/03/2009 lay StockType co id = stockTypeId
     *
     */
    private StockType getStockTypeById(Long stockTypeId) {
        StockType stockType = null;
        if (stockTypeId == null) {
            return stockType;
        }
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        stockType = stockTypeDAO.findById(stockTypeId);
        return stockType;
    }

    /**
     *
     * author tamdt1 date: 16/04/2009 lay StockModel co id = stockModelId
     *
     */
    private StockModel getStockModelById(Long stockModelId) {
        StockModel stockModel = null;
        if (stockModelId == null) {
            return stockModel;
        }
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        stockModel = stockModelDAO.findById(stockModelId);

        return stockModel;
    }

    /**
     *
     * author tamdt1 date: 16/04/2009 tra du lieu cho cay mat hang
     *
     */
    public String getStockModelTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");

            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac stockType
                List listStockTypes = hbnSession.createCriteria(StockType.class).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterStockType = listStockTypes.iterator();
                while (iterStockType.hasNext()) {
                    StockType stockType = (StockType) iterStockType.next();
                    String nodeId = "1_" + stockType.getStockTypeId().toString(); //them prefix 2_ de xac dinh la node level
                    String doString = httpServletRequest.getContextPath() + "/goodsDefAction!displayAllStockModelInStockType.do?selectedStockTypeId=" + stockType.getStockTypeId().toString();
                    getListItems().add(new Node(stockType.getName(), "true", nodeId, doString));
                }
            } else if (node.indexOf("1_") == 0) {
                //neu la cau du lieu muc 1, tra ve danh sach cac stockmodel tuong ung voi stockType
                node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                List listStockModels = hbnSession.createCriteria(StockModel.class).
                        add(Restrictions.eq("stockTypeId", Long.parseLong(node))).
                        //                        add(Restrictions.eq("status", 1L)). //lay ra tat ca cac loai mat hang (bao gom ca hieu luc va ko hieu luc)
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterStockModel = listStockModels.iterator();
                while (iterStockModel.hasNext()) {
                    StockModel stockModel = (StockModel) iterStockModel.next();
                    String nodeId = "2_" + stockModel.getStockModelId().toString(); //them prefix 2_ de xac dinh la node level
                    String doString = httpServletRequest.getContextPath() + "/goodsDefAction!displayStockModel.do?selectedStockModelId=" + stockModel.getStockModelId().toString();
                    getListItems().add(new Node(stockModel.getName(), "false", nodeId, doString));
                }
            }


            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    /**
//     *
//     * author tamdt1
//     * date: 02/04/2009
//     * lay danh sach tat ca cac stockType
//     *
//     */
//    private List<StockType> getListStockTypes() {
//        List<StockType> listStockTypes = new ArrayList();
//        String strQuery = "from StockType where status = ? order by name";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, Constant.STATUS_USE);
//        listStockTypes = query.list();
//
//        return listStockTypes;
//    }
    /**
     *
     * author tamdt1 date: 02/04/2009 lay danh sach tat ca cac stockModel cua 1
     * sotckType
     *
     */
    private List<StockModel> getListStockModels(Long stockTypeId) {
        List<StockModel> listStockModels = new ArrayList();
        StringBuffer strQuery = new StringBuffer("");
        List listParam = new ArrayList();
        strQuery.append("select new StockModel(a.stockModelId, a.stockModelCode, a.stockTypeId, a.name, a.checkSerial, a.checkDial, b.telServiceName, c.name, d.name) ");
        strQuery.append("from StockModel a, TelecomService b, AppParams c, StockType d ");
        strQuery.append("where 1 = 1 ");
        strQuery.append("and a.telecomServiceId = b.telecomServiceId ");
        strQuery.append("and a.stockTypeId = d.stockTypeId ");

        strQuery.append("and a.unit = c.id.code and c.id.type = ? ");
        listParam.add(Constant.APP_PARAMS_STOCK_MODEL_UNIT);

        if (stockTypeId != null) {
            strQuery.append("and a.stockTypeId = ? ");
            listParam.add(stockTypeId);
        }

        strQuery.append("order by nlssort(a.stockModelCode,'nls_sort=Vietnamese') asc");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParam.size(); i++) {
            query.setParameter(i, listParam.get(i));
        }

        listStockModels = query.list();

        return listStockModels;
    }

    /**
     *
     * author tamdt1 date: 16/04/2009 lay danh sach tat ca cac price cua 1
     * stockModelId
     *
     */
    private List<Price> getListPrices(Session session, Long stockModelId) throws Exception {
        try {
            HttpServletRequest req = getRequest();
            List<Price> listPrices = new ArrayList<Price>();

            if (stockModelId == null) {
                return listPrices;
            }

            String strQuery = "select new com.viettel.im.database.BO.Price("
                    + "a.priceId, a.stockModelId, a.price, a.status, a.staDate, a.endDate, "
                    + "a.vat, a.createDate, a.userName, a.type, a.pricePolicy, b.name, c.name) "
                    + "from Price a, AppParams b, AppParams c "
                    + "where a.stockModelId = ? "
                    + "and a.type = b.id.code and b.id.type = ? "
                    + "and a.pricePolicy = c.id.code and c.id.type = ? "
                    + "order by a.status desc, cast(a.type as int) asc, a.staDate asc, a.endDate asc ";
            Query query = session.createQuery(strQuery);
            query.setParameter(0, stockModelId);
            query.setParameter(1, Constant.APP_PARAMS_PRICE_TYPE);
            query.setParameter(2, Constant.APP_PARAMS_PRICE_POLICY);
            listPrices = query.list();

            return listPrices;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

//    /**
//     *
//     * author tamdt1
//     * date: 16/04/2009
//     * kiem tra tinh hop le cua stockModel truoc khi xoa
//     *
//     */
//    private boolean checkValidStockModelToDel() {
//        Long stockModelId = this.stockModelForm.getStockModelId();
//
//        String strQuery;
//        Query query;
//        Long count;
//
//        try {
//            //kiem tra stockModel co con su dung trong stockTotal hay khong
//            strQuery = "select count(*) from StockTotal where id.stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//
//            //kiem tra stockModel co con su dung trong saleServicesDetail hay khong
//            strQuery = "select count(*) from SaleServicesDetail where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//
//            //kiem tra stockModel co con su dung trong cac bang stock hay khong
//            //bang StockAccessories
//            strQuery = "select count(*) from StockAccessories where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockCard
//            strQuery = "select count(*) from StockCard where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockHandset
//            strQuery = "select count(*) from StockHandset where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockIsdnHomePhone
//            strQuery = "select count(*) from StockIsdnHomephone where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockIsdnMobile
//            strQuery = "select count(*) from StockIsdnMobile where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockIsdnPstn
//            strQuery = "select count(*) from StockIsdnPstn where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockKit
//            strQuery = "select count(*) from StockKit where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockSimPostPaid
//            strQuery = "select count(*) from StockSimPostPaid where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockSimPrePaid
//            strQuery = "select count(*) from StockSimPrePaid where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//            //bang StockSumo
//            strQuery = "select count(*) from StockSumo where stockModelId = ? and status <> ? ";
//            query = getSession().createQuery(strQuery);
//            query.setParameter(0, stockModelId);
//            query.setParameter(1, Constant.STATUS_DELETE);
//            count = (Long) query.list().get(0);
//            if (count.compareTo(0L) > 0) {
//                return false;
//            }
//
//
//            return true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//
//    }
    /**
     *
     * author tamdt1 date: 14/05/2009 phan trang cho danh sach cac stockModel
     *
     */
    public String pageNavigatorStockModel() throws Exception {
        log.info("Begin method pageNavigatorStockModel of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();
        Long stockTypeId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_TYPE_ID);
        //lay thong tin ve stockType
        StockType stockType = getStockTypeById(stockTypeId);
        if (stockType != null) {
            this.stockModelForm.setStockTypeId(stockTypeId);
            this.stockModelForm.setStockTypeName(stockType.getName());
        }

        pageForward = LIST_STOCK_MODELS;

        log.info("End method pageNavigatorStockModel of GoodsDefDAO ...");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 24/09/2009 hien thi thong tin stockDeposit cua 1
     * stockModel
     *
     */
    public String displayStockDeposit() throws Exception {
        log.info("Begin method displayStockDeposit of GoodsDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        String strStockDepositId = req.getParameter("selectedStockDepositId");
        Long stockDepositId;
        if (strStockDepositId != null) {
            try {
                stockDepositId = Long.valueOf(strStockDepositId);
            } catch (NumberFormatException ex) {
                stockDepositId = -1L;
            }
        } else {
            stockDepositId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_DEPOSIT_ID);
        }

        StockDeposit stockDeposit = getStockDepositById(stockDepositId);
        if (stockDeposit != null) {
            //
            this.stockDepositForm.copyDataFromBO(stockDeposit);

            //thiet lap cac bien can thiet cho combobox
            getComboboxDataDeposit();
        }


        //lay danh sach stockDeposit thuoc mat hang
        Long stockModelId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_MODEL_ID);
        List<StockDeposit> listStockDeposit = getListStockDeposits(session, stockModelId);
        req.setAttribute(REQUEST_LIST_STOCK_DEPOSIT, listStockDeposit);

        //thiet lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);


        pageForward = STOCK_DEPOSIT;
        log.info("End method displayStockDeposit of GoodsDefDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 chuan bi form them stockDeposit moi
     *
     */
    public String prepareAddStockDeposit() throws Exception {
        log.info("Begin method prepareAddStockDeposit of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();

        //giu lai stockModelId phuc vu viec luu vao DB sau nay
        Long stockModelId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_MODEL_ID);
        this.stockDepositForm.resetForm();
        this.stockDepositForm.setStockModelId(stockModelId);

        //thiet lap cac bien can thiet cho combobox
        getComboboxDataDeposit();

        //thiet lap che do them moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = STOCK_DEPOSIT;
        log.info("End method prepareAddStockDeposit of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 chuan bi form sua thong tinn stockDeposit
     *
     */
    public String prepareEditStockDeposit() throws Exception {
        log.info("Begin method prepareEditStockDeposit of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();
        Long stockDepositId = this.stockDepositForm.getStockDepositId();
        StockDepositDAO stockDepositDAO = new StockDepositDAO();
        stockDepositDAO.setSession(this.getSession());
        StockDeposit stockDeposit = stockDepositDAO.findById(stockDepositId);
        if (stockDeposit != null) {
            //
            this.stockDepositForm.copyDataFromBO(stockDeposit);

            //thiet lap cac bien can thiet cho combobox
            getComboboxDataDeposit();

            //thiet lap che do sua thong tin stockDeposit
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);

        }

        pageForward = STOCK_DEPOSIT;
        log.info("End method prepareEditStockDeposit of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 them stockDeposit moi vao DB moi hoac sua
     * thong tin DB da co
     *
     */
    public String addOrEditStockDeposit() throws Exception {
        log.info("Begin method addOrEditStockDeposit of GoodsDefDAO ...");
        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */

        HttpServletRequest req = getRequest();
        Session session = getSession();

        Long stockDepositId = this.stockDepositForm.getStockDepositId();
        StockDepositDAO stockDepositDAO = new StockDepositDAO();
        stockDepositDAO.setSession(session);
        StockDeposit stockDeposit = stockDepositDAO.findById(stockDepositId);
        if (stockDepositId == null) {
            stockDepositId = 0L;
        }
        Long channelTypeId = stockDepositForm.getChanelTypeId();
        Long status = stockDepositForm.getStatus();
        Long transType = stockDepositForm.getTransType();
        Date fromDate = stockDepositForm.getDateFrom();
        Date toDate = stockDepositForm.getDateTo();
        Long stockModelId = stockDepositForm.getStockModelId();


        if (stockDeposit == null) {

            //----------------------------------------------------------------
            //truong hop them stockDeposit moi
            // tutm1 07/11/2011 - kiem tra tinh hop le truoc khi them 
            // tu ngay ko dc lon hon den ngay
            if (fromDate != null && toDate != null && fromDate.compareTo(toDate) > 0) {
                //thiet lap cac bien can thiet cho combobox
                getComboboxDataDeposit();
                //thiet lap che do them moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.138");
                pageForward = STOCK_DEPOSIT;
                log.info("End method addOrEditStockDeposit of GoodsDefDAO");
                return pageForward;
            }
            if (!checkValidStockDepositToAdd(stockModelId, stockDepositId, transType, status, channelTypeId,
                    fromDate, toDate)) {
                //thiet lap cac bien can thiet cho combobox
                getComboboxDataDeposit();
                //thiet lap che do them moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);
                req.setAttribute(REQUEST_MESSAGE, "stockDeposit.date.notvalid");
                //return
                pageForward = STOCK_DEPOSIT;
                log.info("End method addOrEditStockDeposit of GoodsDefDAO");
                return pageForward;
            }

            //luu du lieu vao db
            stockDeposit = new StockDeposit();
            this.stockDepositForm.copyDataToBO(stockDeposit);
            stockDepositId = getSequence("STOCK_DEPOSIT_SEQ");
            stockDeposit.setStockDepositId(stockDepositId);
            session.save(stockDeposit);
            session.flush();
            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(null, stockDeposit));
            saveLog(Constant.ACTION_ADD_GOOD_DEPOSIT, "Thêm mới đối tượng đặt cọc", lstLogObj, stockDeposit.getStockDepositId(), Constant.DEFINE_GOODS, Constant.ADD);
            /*
             * LamNV ADD STOP
             */

            //
            this.stockDepositForm.setStockDepositId(stockDepositId);
            req.getSession().setAttribute(SESSION_CURR_STOCK_DEPOSIT_ID, stockDepositId);

            //
            req.setAttribute(REQUEST_MESSAGE, "stockDeposit.addsuccessful");

        } else {
            //----------------------------------------------------------------
            //truong hop cap nhat thong tin cua stockDeposit da co
            // tu ngay ko dc lon hon den ngay
            if (fromDate != null && toDate != null && fromDate.compareTo(toDate) > 0) {
                //thiet lap cac bien can thiet cho combobox
                getComboboxDataDeposit();
                //thiet lap che do them moi
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);
                req.setAttribute(REQUEST_MESSAGE, "ERR.SAE.138");
                pageForward = STOCK_DEPOSIT;
                log.info("End method addOrEditStockDeposit of GoodsDefDAO");
                return pageForward;
            }
            if (!checkValidStockDepositToEdit(stockModelId, stockDepositId, transType, status, channelTypeId,
                    fromDate, toDate)) {
                //thiet lap cac bien can thiet cho combobox
                getComboboxDataDeposit();
                //thiet lap che do sua thong tin
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);
                req.setAttribute(REQUEST_MESSAGE, "stockDeposit.date.notvalid");
                //return
                pageForward = STOCK_DEPOSIT;
                log.info("End method addOrEditStockDeposit of GoodsDefDAO");
                return pageForward;
            }

            /*
             * LamNV ADD START
             */
            StockDeposit oldStockDeposit = new StockDeposit();
            BeanUtils.copyProperties(oldStockDeposit, stockDeposit);
            /*
             * LamNV ADD STOP
             */

            //luu du lieu vao db
            this.stockDepositForm.copyDataToBO(stockDeposit);
            session.update(stockDeposit);
            session.flush();
            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(oldStockDeposit, stockDeposit));
            saveLog(Constant.ACTION_UPDATE_GOOD_DEPOSIT, "Cập nhật đối tượng đặt cọc", lstLogObj, stockDeposit.getStockDepositId(), Constant.DEFINE_GOODS, Constant.EDIT);
            /*
             * LamNV ADD STOP
             */

            //
            req.setAttribute(REQUEST_MESSAGE, "stockDeposit.editsuccessful");
        }

        //
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //cap nhat lai list
        List<StockDeposit> listStockDeposit = getListStockDeposits(session, stockDeposit.getStockModelId());
        req.setAttribute(REQUEST_LIST_STOCK_DEPOSIT, listStockDeposit);

        //thiet lap cac bien can thiet cho combobox
        getComboboxDataDeposit();

        pageForward = STOCK_DEPOSIT;
        log.info("End method addOrEditStockDeposit of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 xoa stockDeposit
     *
     */
    public String deleteStockDeposit() throws Exception {
        log.info("Begin method deleteStockDeposit of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        Long stockDepositId = this.stockDepositForm.getStockDepositId();
        StockDepositDAO stockDepositDAO = new StockDepositDAO();
        stockDepositDAO.setSession(session);
        StockDeposit stockDeposit = stockDepositDAO.findById(stockDepositId);

        if (stockDeposit != null) {
            try {
                //cap nhat CSDL
                session.delete(stockDeposit);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(stockDeposit, null));
                saveLog(Constant.ACTION_DELETE_GOOD_DEPOSIT, "Xóa đối tượng đặt cọc", lstLogObj, stockDeposit.getStockDepositId(), Constant.DEFINE_GOODS, Constant.DELETE);

                session.flush();

            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "goodsdefprice.error.delete.stockDepositIsUsing");

                pageForward = STOCK_DEPOSIT;
                log.info("End method deleteStockDeposit of GoodsDefDAO");
                return pageForward;
            }

            //
            req.setAttribute(REQUEST_IS_VIEW_MODE, true);

            //cap nhat lai list
            List<StockDeposit> listStockDeposit = getListStockDeposits(session, stockDeposit.getStockModelId());
            req.setAttribute(REQUEST_LIST_STOCK_DEPOSIT, listStockDeposit);

            //thiet lap cac bien can thiet cho combobox
            getComboboxDataDeposit();

            //
            this.stockDepositForm.resetForm();

            //
            req.setAttribute(REQUEST_MESSAGE, "stockDeposit.delsuccessful");

        }

        pageForward = STOCK_DEPOSIT;
        log.info("End method deleteStockDeposit of GoodsDefDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 lay StockDeposit co id = stockDepositId
     * trong bien session
     *
     */
    private StockDeposit getStockDepositInListById(List<StockDeposit> listStockDeposits, Long stockDepositId) {
        StockDeposit stockDeposit = null;
        if (listStockDeposits == null || listStockDeposits.size() == 0 || stockDepositId == null) {
            return stockDeposit;
        }

        for (int i = 0; i < listStockDeposits.size(); i++) {
            if (listStockDeposits.get(i).getStockDepositId().equals(stockDepositId)) {
                stockDeposit = listStockDeposits.get(i);
                break;
            }
        }

        return stockDeposit;
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 kiem tra tinh hop le cua 1 stockDeposit
     * truoc khi them moi cho mat hang
     *
     */
    private boolean checkValidStockDepositToAdd(Long stockModelId, Long stockDepositId, Long transType, Long status, Long channelTypeId,
            Date fromDate, Date toDate) {
        try {
            StringBuffer sql = new StringBuffer("select * from stock_deposit where stock_model_id = :stockModelId and "
                    + " trans_type = :transType and status = :status and chanel_type_id  = :channelTypeId "
                    + " and stock_deposit_id <> :stockDepositId ");

            Query query = null;
            if (fromDate != null && toDate != null) {
                sql.append(" and  not ( "
                        + " (date_from > :toDate and date_from is not null) "
                        + " or (date_to < :fromDate and date_to is not null))");
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("transType", transType);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
                query.setParameter("toDate", toDate);
                query.setParameter("fromDate", fromDate);
            } else if (fromDate == null && toDate != null) {
                sql.append(" and  not ( "
                        + " date_from > :toDate and date_from is not null "
                        + " )");
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("transType", transType);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
                query.setParameter("toDate", toDate);
            } else if (fromDate != null && toDate == null) {
                sql.append(" and  not ( "
                        + " date_to < :fromDate and date_to is not null "
                        + " )");
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("transType", transType);
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
                query.setParameter("fromDate", fromDate);
            } else {// ca fromdate, todat deu null
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("transType", transType);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
            }

            List<Long> list = query.list();
            if (list != null && list.size() >= 1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 kiem tra tinh hop le cua 1 stockDeposit
     * truoc khi sua thong tin
     *
     */
    private boolean checkValidStockDepositToEdit(Long stockModelId, Long stockDepositId, Long transType, Long status, Long channelTypeId,
            Date fromDate, Date toDate) {
        try {
            StringBuffer sql = new StringBuffer("select * from stock_deposit where stock_model_id = :stockModelId and "
                    + " trans_type = :transType and status = :status and chanel_type_id  = :channelTypeId "
                    + " and stock_deposit_id <> :stockDepositId ");

            Query query = null;
            if (fromDate != null && toDate != null) {
                sql.append(" and  not ( "
                        + " (date_from > :toDate and date_from is not null) "
                        + " or (date_to < :fromDate and date_to is not null))");
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("transType", transType);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
                query.setParameter("toDate", toDate);
                query.setParameter("fromDate", fromDate);
            } else if (fromDate == null && toDate != null) {
                sql.append(" and  not ( "
                        + " date_from > :toDate and date_from is not null "
                        + " )");
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("transType", transType);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
                query.setParameter("toDate", toDate);
            } else if (fromDate != null && toDate == null) {
                sql.append(" and  not ( "
                        + " date_to < :fromDate and date_to is not null "
                        + " )");
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("transType", transType);
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
                query.setParameter("fromDate", fromDate);
            } else {// ca fromdate, todat deu null
                query = getSession().createSQLQuery(sql.toString());
                query.setParameter("stockModelId", stockModelId);
                query.setParameter("transType", transType);
                query.setParameter("status", status);
                query.setParameter("channelTypeId", channelTypeId);
                query.setParameter("stockDepositId", stockDepositId);
            }

            List<Long> list = query.list();
            if (list != null && list.size() >= 1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * author tamdt1 date: 23/09/2009 lay danh sach tat ca cac stockDeposit cua
     * 1 stockModelId
     *
     */
    private List<StockDeposit> getListStockDeposits(Session session, Long stockModelId) throws Exception {
        try {
            if (stockModelId == null) {
                stockModelId = -1L;
            }
            HttpServletRequest req = getRequest();
            List<StockDeposit> listStockDeposits = new ArrayList<StockDeposit>();

            String strQuery = "select new com.viettel.im.database.BO.StockDeposit("
                    + "a.stockDepositId, a.stockModelId, a.chanelTypeId, a.maxStock, "
                    + "a.status, a.dateFrom, a.dateTo, b.name, a.transType) "
                    + "from StockDeposit a, ChannelType b "
                    + "where a.stockModelId = ? and a.chanelTypeId = b.channelTypeId ";
            Query query = session.createQuery(strQuery);
            query.setParameter(0, stockModelId);
            listStockDeposits = query.list();

            return listStockDeposits;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tamdt1 date: 24/09/2009 thiet lap du lieu can thiet cho cac
     * combobox doi voi stockModel
     *
     */
    private void getComboboxDataModel() throws Exception {
        try {
            //thiet lap cac bien can thiet cho combobox
            HttpServletRequest req = getRequest();

            //danh sach cac loai mat hang
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List<StockType> listStockTypes = stockTypeDAO.findByProperty(StockTypeDAO.STATUS, Constant.STATUS_USE, StockTypeDAO.NAME);
            req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);

            //danh sach dich vu
            TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
            telecomServiceDAO.setSession(this.getSession());
            List<TelecomService> listTelecomServices = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);

            //danh sach don vi mat hang
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listStockModelUnits = appParamsDAO.findAppParamsByType_1(Constant.APP_PARAMS_STOCK_MODEL_UNIT);
            req.setAttribute(REQUEST_LIST_STOCK_MODEL_UNITS, listStockModelUnits);
            //#TruongNQ5 20140725 R6237
            List<ListMapBean> listRvnServiceQuality = getListRvnServiceQuality();
            List<ListMapBean> listRvnService = getListRvnService();
            req.setAttribute(REQUEST_LIST_RVN_SEVICE_QUALITY, listRvnServiceQuality);
            req.setAttribute(REQUEST_LIST_RVN_SEVICE, listRvnService);
            //End TruongNQ

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tamdt1 date: 24/09/2009 thiet lap du lieu can thiet cho cac
     * combobox doi voi stockModel
     *
     */
    private void getComboboxDataPrice() throws Exception {
        try {
            //thiet lap cac bien can thiet cho combobox
            HttpServletRequest req = getRequest();

            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());

            //danh sach cac loai gia
            List<AppParams> listPriceTypes = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_TYPE);
            req.setAttribute(REQUEST_LIST_PRICE_TYPES, listPriceTypes);

            //danh sach chinh sach gia
            List<AppParams> listPricePolicies = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PRICE_POLICY);
            req.setAttribute(REQUEST_LIST_PRICE_POLICIES, listPricePolicies);

            List<AppParams> listCurrency = appParamsDAO.findAppParamsByType("CURRENCY");
            req.setAttribute(REQUEST_LIST_CURRENCY, listCurrency);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tamdt1 date: 24/09/2009 thiet lap du lieu can thiet cho cac
     * combobox doi voi stockDeposit
     *
     */
    private void getComboboxDataDeposit() throws Exception {
        try {
            //thiet lap cac bien can thiet cho combobox
            HttpServletRequest req = getRequest();

            //danh sach loai kenh
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(this.getSession());
            List<ChannelType> listChannelType = channelTypeDAO.findByProperty(ChannelTypeDAO.STATUS, Constant.STATUS_USE);
            req.setAttribute(REQUEST_LIST_CHANNELS, listChannelType);
            Long stockModelId = (Long) req.getSession().getAttribute(SESSION_CURR_STOCK_MODEL_ID);
            List<StockDeposit> listStockDeposit = getListStockDeposits(getSession(), stockModelId);
            req.setAttribute(REQUEST_LIST_STOCK_DEPOSIT, listStockDeposit);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /*
     * add by hieptd class Check Stock Model
     */
    private List getListCheckStockChannel() throws Exception {
        try {
            List listCheckStockChannel = new ArrayList();
            listCheckStockChannel.add(0, new CheckStockChannel(0, getText("MSG.GD.DontManagementByChannel")));
            listCheckStockChannel.add(1, new CheckStockChannel(1, getText("MSG.GD.ManagementByChannel")));
            return listCheckStockChannel;
        } catch (Exception ex) {
            throw ex;
        }
    }

    class CheckStockChannel {

        private Integer idCheckStockChannel;
        private String valueCheckStockChannel;

        public CheckStockChannel(Integer idCheckStockChannel, String valueCheckStockChannel) {
            this.idCheckStockChannel = idCheckStockChannel;
            this.valueCheckStockChannel = valueCheckStockChannel;
        }

        public Integer getIdCheckStockChannel() {
            return idCheckStockChannel;
        }

        public void setIdCheckStockChannel(Integer idCheckStockChannel) {
            this.idCheckStockChannel = idCheckStockChannel;
        }

        public String getValueCheckStockChannel() {
            return valueCheckStockChannel;
        }

        public void setValueCheckStockChannel(String valueCheckStockChannel) {
            this.valueCheckStockChannel = valueCheckStockChannel;
        }
    }
    //TruongNQ5 20140729 R6237 
    //Ham get data combobox tieu chi doanh thu
    public List<ListMapBean> getListRvnService() {
        StringBuffer sql = new StringBuffer();
        sql.append("select id, id || ' - ' || name as name from  " + schemaRvnService + ".rvn_service where source = 'IM' and service_type_id = ? and status = ? order by id");
        Query query = getSession().createSQLQuery(sql.toString()).addScalar("id", Hibernate.LONG).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ListMapBean.class));
        query.setParameter(0, Constant.TYPE_REVENUE);
        query.setParameter(1, Constant.STATUS_ACTIVE);
        List<ListMapBean> list = query.list();
        return list;
    }

    //Ham get data combobox tieu chi san luong
    public List<ListMapBean> getListRvnServiceQuality() {
        StringBuffer sql = new StringBuffer();
        sql.append("select id, id || ' - ' || name as name from  " + schemaRvnService + ".rvn_service where source = 'IM' and service_type_id = ? and status = ? order by id");
        Query query = getSession().createSQLQuery(sql.toString()).addScalar("id", Hibernate.LONG).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(ListMapBean.class));
        query.setParameter(0, Constant.TYPE_QUALITY);
        query.setParameter(1, Constant.STATUS_ACTIVE);
        List<ListMapBean> list = query.list();
        return list;
    }
    //TruongNQ5 20140725 Ham update combobox Chi tieu doanh thu

    public void updateRVNService(Long rvnServiceId, String stockModelCode, Long serviceIndex) {
        String sql = "";
        try {
            if (rvnServiceId != null) {
                if (serviceIndex != null && serviceIndex > 0L) {
                    String oldRvnServiceId = getRVNServiceDeptMap(serviceIndex).toString();
                    if (oldRvnServiceId.trim() != rvnServiceId.toString().trim()) {
                    sql = "UPDATE  " + schemaRvnService + ".rvn_service_dept_map set service_id = ? where id = ? ";
                    Query queryUpdate = getSession().createSQLQuery(sql);
                    queryUpdate.setParameter(0, rvnServiceId);
                    queryUpdate.setParameter(1, serviceIndex);
                    queryUpdate.executeUpdate();
                    
                        saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_GOOD, getText("update.sales.criteria"),"rvn_service_dept_map"
                , serviceIndex, "service_id", oldRvnServiceId,rvnServiceId.toString());
                    }
                } else {
                    sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query queryInsert = getSession().createSQLQuery(sql);
                    queryInsert.setParameter(0, index);
                    queryInsert.setParameter(1, Constant.DEPT_ID_MOV);
                    queryInsert.setParameter(2, rvnServiceId);
                    queryInsert.setParameter(3, stockModelCode);
                    queryInsert.setParameter(4, Constant.TYPE_GOODS);
                    queryInsert.setParameter(5, Constant.STATUS_ACTIVE);
                    queryInsert.executeUpdate();
                    
                    saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_GOOD, getText("add.new.sales.criteria"),"rvn_service_dept_map"
            , index, "service_id", "",rvnServiceId.toString());
                }
            } else if (serviceIndex != null && serviceIndex > 0L) {
                String oldRvnServiceId = getRVNServiceDeptMap(serviceIndex).toString();
                sql = "DELETE FROM  " + schemaRvnService + ".rvn_service_dept_map where id = ? ";
                Query queryDelete = getSession().createSQLQuery(sql);
                queryDelete.setParameter(0, serviceIndex);
                queryDelete.executeUpdate();
                
                saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_GOOD, getText("delete.sales.criteria"),"rvn_service_dept_map"
            , serviceIndex, "service_id", oldRvnServiceId,"");
                this.stockModelForm.setServiceIndex(0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TruongNQ5 20140725 Ham update combobox Chi tieu san luong

    public void updateRVNServiceQuality(Long rvnServiceQualityId, String stockModelCode, Long serviceQualityIndex) {
        String sql = "";
        try {
            if (rvnServiceQualityId != null) {
                if (serviceQualityIndex != null && serviceQualityIndex > 0L) {
                    String oldRvnServiceQualityId = getRVNServiceDeptMap(serviceQualityIndex).toString();
                    if (oldRvnServiceQualityId.trim() != rvnServiceQualityId.toString().trim()) {
                    sql = "UPDATE  " + schemaRvnService + ".rvn_service_dept_map set service_id = ? where id = ? ";
                    Query queryUpdate = getSession().createSQLQuery(sql);
                    queryUpdate.setParameter(0, rvnServiceQualityId);
                    queryUpdate.setParameter(1, serviceQualityIndex);
                    queryUpdate.executeUpdate();
                    
                        saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_GOOD, getText("update.quality.criteria"),"rvn_service_dept_map"
                , serviceQualityIndex, "service_id", oldRvnServiceQualityId,rvnServiceQualityId.toString());
                    }
                } else {
                    sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query queryInsert = getSession().createSQLQuery(sql);
                    queryInsert.setParameter(0, index);
                    queryInsert.setParameter(1, Constant.DEPT_ID_MOV);
                    queryInsert.setParameter(2, rvnServiceQualityId);
                    queryInsert.setParameter(3, stockModelCode);
                    queryInsert.setParameter(4, Constant.TYPE_GOODS);
                    queryInsert.setParameter(5, Constant.STATUS_ACTIVE);
                    queryInsert.executeUpdate();
                    
                    saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_GOOD, getText("add.new.quality.criteria"),"rvn_service_dept_map"
            , index, "service_id", "",rvnServiceQualityId.toString());
                }
            } else if (serviceQualityIndex != null && serviceQualityIndex > 0L) {
                String oldRvnServiceQualityId = getRVNServiceDeptMap(serviceQualityIndex).toString();
                sql = "DELETE FROM  " + schemaRvnService + ".rvn_service_dept_map where id = ? ";
                Query queryDelete = getSession().createSQLQuery(sql);
                queryDelete.setParameter(0, serviceQualityIndex);
                queryDelete.executeUpdate();
                
                saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_GOOD, getText("delete.quality.criteria"),"rvn_service_dept_map"
            , serviceQualityIndex, "service_id", oldRvnServiceQualityId,"");
                this.stockModelForm.setServiceQualityIndex(0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Ham load du lieu len 2 combobox chi tieu khi click vao button sua - cap nhat
    public void prepareEditRVNService(String stockModelCode) {
        //Chi tieu doanh thu
        List lstParams = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT   rs.id as rvnServiceId, rsm.id as serviceIndex");
        sql.append(" FROM      " + schemaRvnService + ".rvn_service_dept_map rsm,  " + schemaRvnService + ".rvn_service rs");
        sql.append(" WHERE    rs.id = rsm.service_id");
        sql.append("          AND rs.service_type_id = ? ");
        lstParams.add(Constant.TYPE_REVENUE);
        sql.append("          AND rsm.goods_type_id = ? ");
        lstParams.add(Constant.TYPE_GOODS);
        sql.append("          AND rsm.goods_code = ? ");
        lstParams.add(stockModelCode);
        sql.append("          AND rs.source = ? ");
        lstParams.add(Constant.SOURCE_IM);
        sql.append("          AND rsm.status = ? ");
        lstParams.add(Constant.STATUS_ACTIVE);

        Query query = getSession().createSQLQuery(sql.toString()).addScalar("rvnServiceId", Hibernate.LONG).addScalar("serviceIndex", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(StockModelForm.class));
        for (int i = 0; i < lstParams.size(); i++) {
            query.setParameter(i, lstParams.get(i));
        }
        List<StockModelForm> list = query.list();

        if (list != null && !list.isEmpty()) {
            this.stockModelForm.setServiceIndex(list.get(0).getServiceIndex());
            this.stockModelForm.setRvnServiceId(list.get(0).getRvnServiceId());
        }

        //Chi tieu san luong
        lstParams = new ArrayList();
        StringBuffer sqlQuality = new StringBuffer();
        sqlQuality.append(" SELECT   rs.id as rvnServiceQualityId, rsm.id as serviceQualityIndex");
        sqlQuality.append(" FROM      " + schemaRvnService + ".rvn_service_dept_map rsm,  " + schemaRvnService + ".rvn_service rs");
        sqlQuality.append(" WHERE    rs.id = rsm.service_id");
        sqlQuality.append("          AND rs.service_type_id = ? ");
        lstParams.add(Constant.TYPE_QUALITY);
        sqlQuality.append("          AND rsm.goods_type_id = ? ");
        lstParams.add(Constant.TYPE_GOODS);
        sqlQuality.append("          AND rsm.goods_code = ? ");
        lstParams.add(stockModelCode);
        sqlQuality.append("          AND rs.source = ? ");
        lstParams.add(Constant.SOURCE_IM);
        sqlQuality.append("          AND rsm.status = ? ");
        lstParams.add(Constant.STATUS_ACTIVE);
        Query queryQuality = getSession().createSQLQuery(sqlQuality.toString()).addScalar("rvnServiceQualityId", Hibernate.LONG).addScalar("serviceQualityIndex", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(StockModelForm.class));
        for (int i = 0; i < lstParams.size(); i++) {
            queryQuality.setParameter(i, lstParams.get(i));
        }
        List<StockModelForm> listQuality = queryQuality.list();

        if (listQuality != null && !listQuality.isEmpty()) {
            this.stockModelForm.setServiceQualityIndex(listQuality.get(0).getServiceQualityIndex());
            this.stockModelForm.setRvnServiceQualityId(listQuality.get(0).getRvnServiceQualityId());
        }
    }
    //TruongNQ5 Ham ghi log khi thuc hien thay doi tieu chi cua mat hang
    public void saveLogRVNServiceDeptMap(String actionType, String description,String dbTableName
            , Long objectId, String dbColumnName, String oldValue,String newValue){
        Session session = getSession();
        HttpServletRequest req = getRequest();
        com.viettel.im.database.BO.UserToken userToken = (com.viettel.im.database.BO.UserToken) req.getSession().getAttribute("userToken");
        try {
            //Ghi log vao action log
        ActionLog actionLog = new ActionLog();
            Long actionId = getSequence("ACTION_LOG_SEQ");
            actionLog.setActionId(actionId);
            actionLog.setActionDate(getSysdate());
            actionLog.setActionType(actionType);
            actionLog.setActionIp(getIpAddress());
            actionLog.setActionUser(userToken.getLoginName());
            actionLog.setDescription(getText(description));
            actionLog.setObjectId(objectId);
            session.save(actionLog);
            //Ghi log vao action log detail
            ActionLogDetail actionLogDetail = new ActionLogDetail();
            actionLogDetail.setActionDetailId(getSequence("ACTION_LOG_DETAIL_SEQ"));
            actionLogDetail.setActionId(actionId);
            actionLogDetail.setTableName(dbTableName);
            actionLogDetail.setColumnName(dbColumnName);
            actionLogDetail.setOldValue(oldValue);
            actionLogDetail.setNewValue(newValue);
            actionLogDetail.setActionDate(getSysdate());
            session.save(actionLogDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getRVNServiceDeptMap(Long id){
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT   service_id");
        sql.append(" FROM      " + schemaRvnService + ".rvn_service_dept_map");
        sql.append(" WHERE    id = ?");
        Query query = getSession().createSQLQuery(sql.toString());
        query.setParameter(0, id);
        List lst = query.list();
        if (lst != null && !lst.isEmpty()) {
            return lst.get(0).toString();
        }
        return  "";
    }
    //End TruongNQ5 20140729
}
