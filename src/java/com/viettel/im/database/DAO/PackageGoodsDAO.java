package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.SaleServicesDetailForm;
import com.viettel.im.client.form.SaleServicesForm;
import com.viettel.im.client.form.SaleServicesPriceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.SaleServicesDetail;
import com.viettel.im.database.BO.SaleServicesModel;
import com.viettel.im.database.BO.SaleServicesPrice;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import com.viettel.lib.sm.common.util.NumberUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author ThanhNC
 * xu ly cac tac vu lien quan den goi hang
 *
 */
public class PackageGoodsDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PackageGoodsDAO.class);
    private String pageForward;
    //cac bien session, request
    private final String SESSION_LIST_PACKAGE_GOODS = "listPackageGoods";
    private final String SESSION_LIST_PACKAGE_GOODS_PRICES = "listPackageGoodsPrices";
    private final String SESSION_LIST_PACKAGE_GOODS_DETAIL = "listPackageGoodsDetail";
    private final String SESSION_CURR_PACKAGE_GOODS_ID = "currentPackageGoodsId";
    private final String REQUEST_LIST_TELECOM_SERVICES = "listTelecomServices";
    private final String REQUEST_PACKAGE_GOODS_MODE = "packageGoodsMode";
    private final String REQUEST_LIST_STOCK_TYPES = "listStockTypes";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModel";
    private final String REQUEST_LIST_PRICE_POLICIES = "listPricePolicies";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_PACKAGE_GOODS_PRICE_MESSAGE = "listPackageGoodsPriceMessage";
    private final String REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE = "listPackageGoodsModelMessage";
    private final String REQUEST_PACKAGE_GOODS_DETAIL_MODE = "saleServicesDetailMode";
    private final String REQUEST_IS_ADD_MODE = "isAddMode";
    //dinh nghia cac hang so pageForward
    private final String PACKAGE_GOODS_OVERVIEW = "packageGoodsOverview";
    private final String PACKAGE_GOODS = "packageGoods";
    private final String PACKAGE_GOODS_INFO = "packageGoodsInfo";
    private final String PACKAGE_GOODS_DETAIL = "packageGoodsDetail";
    private final String PACKAGE_GOODS_PRICE = "packageGoodsPrice";
    private final String LIST_PACKAGE_GOODS = "listPackageGoods";
    private final String LIST_PACKAGE_GOODS_PRICES = "listPackageGoodsPrices";
    private final String LIST_PACKAGE_GOODS_MODELS = "listPackageGoodsModels";
    private final String GET_PACKAGE_GOODS_TREE = "getPakageGoodsTree";
    //bien form
    private SaleServicesForm saleServicesForm = new SaleServicesForm();
    private SaleServicesDetailForm saleServicesDetailForm = new SaleServicesDetailForm();
    private SaleServicesPriceForm saleServicesPriceForm = new SaleServicesPriceForm();

    public SaleServicesForm getSaleServicesForm() {
        return saleServicesForm;
    }

    public void setSaleServicesForm(SaleServicesForm saleServicesForm) {
        this.saleServicesForm = saleServicesForm;
    }

    public SaleServicesDetailForm getSaleServicesDetailForm() {
        return saleServicesDetailForm;
    }

    public void setSaleServicesDetailForm(SaleServicesDetailForm saleServicesDetailForm) {
        this.saleServicesDetailForm = saleServicesDetailForm;
    }

    public SaleServicesPriceForm getSaleServicesPriceForm() {
        return saleServicesPriceForm;
    }

    public void setSaleServicesPriceForm(SaleServicesPriceForm saleServicesPriceForm) {
        this.saleServicesPriceForm = saleServicesPriceForm;
    }
    private List listItems = new ArrayList();
    private List listStockModel = new ArrayList();

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
    }

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    /**
     *
     * author tamdt1
     * date: 12/03/2009
     * home page cua phan sale services
     *
     */
    public String packageGoodsOverview() throws Exception {
        log.info("Begin method packageGoodsOverview of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();

        //reset tat ca cac bien session ve null
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, new ArrayList());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, new ArrayList());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES, new ArrayList());
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, -1L);

        //danh sach tat ca cac saleServices
        List<SaleServices> listPackageGoods = getListSaleServices();
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, listPackageGoods);

        pageForward = PACKAGE_GOODS_OVERVIEW;

        log.info("End method packageGoodsOverview of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 09/04/2009
     * tim kiem danh sach cac saleservices thoa man dieu kien tim kiem
     *
     */
    public String searchPackageGoods() throws Exception {
        log.info("Begin method searchPackageGoods of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();
        List<SaleServices> listPackageGoods = getListSaleServices();

        StringBuffer strQuery = new StringBuffer("from SaleServices where saleType = ? ");
        List listParameters = new ArrayList();
        listParameters.add(Constant.SALE_TYPE_PACKAGE);
        strQuery.append(" and status = ?");
        listParameters.add(Constant.STATUS_USE);

        String saleServicesCode = this.saleServicesForm.getCode();
        String saleServicesName = this.saleServicesForm.getName();
        String saleServicesNotes = this.saleServicesForm.getNotes();
        if (saleServicesCode != null && !saleServicesCode.trim().equals("")) {
            strQuery.append(" and code = ?");
            listParameters.add(saleServicesCode.trim());
        }
        if (saleServicesName != null && !saleServicesName.trim().equals("")) {
            strQuery.append(" and lower(name) like ?");
            listParameters.add("%" + saleServicesName.trim().toLowerCase() + "%");
        }
        if (saleServicesNotes != null && !saleServicesNotes.trim().equals("")) {
            strQuery.append(" and lower(notes) like ?");
            listParameters.add("%" + saleServicesNotes.trim().toLowerCase() + "%");
        }
        strQuery.append(" order by code");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameters.size(); i++) {
            query.setParameter(i, listParameters.get(i));
        }

        listPackageGoods = query.list();

        if (listPackageGoods != null) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.searchMessage");
            List listParam = new ArrayList();
            listParam.add(listPackageGoods.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
        }


        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, listPackageGoods);
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, -1L);

        pageForward = LIST_PACKAGE_GOODS;

        log.info("End method searchPackageGoods of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 13/05/2009
     * ham phuc vu muc dich phan tran
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of PackageGoodsDAO ...");

        pageForward = LIST_PACKAGE_GOODS;

        log.info("End method pageNavigator of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 13/03/2009
     * hien thi thong tin saleServices
     *
     */
    public String displayPackageGoods() throws Exception {
        log.info("Begin method displayPackageGoods of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();

        String strSelectedPackageGoodsId = req.getParameter("selectedPackageGoodsId");
        Long saleServicesId;
        if (strSelectedPackageGoodsId != null) {
            try {
                saleServicesId = Long.valueOf(strSelectedPackageGoodsId);
            } catch (NumberFormatException ex) {
                saleServicesId = -1L;
            }
        } else {
            saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
        }

        SaleServices saleServices = getSaleServicesById(saleServicesId);
        if (saleServices != null) {
            req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, saleServicesId);
            saleServicesForm.copyDataFromBO(saleServices);

            //lay danh sach cac telecomServices
            List<TelecomService> listTelecomServices = getListTelecomServices();
            req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);

//            //lay danh sach cac stockType
//            List<StockType> listStockTypes = getListStockTypes();
//            req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);

            //lay danh sach gia cua dich vu
            List<SaleServicesPrice> listPackageGoodsPrices = getListSaleServicesPrice(saleServicesId);
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            HashMap<String, String> pricePolicyHashMap = appParamsDAO.getAppParamsHashMap("PRICE_POLICY");
            if (listPackageGoodsPrices != null) {
                for (int i = 0; i < listPackageGoodsPrices.size(); i++) {
                    SaleServicesPrice tmpSaleServicesPrice = listPackageGoodsPrices.get(i);
                    tmpSaleServicesPrice.setPricePolicyName(pricePolicyHashMap.get(tmpSaleServicesPrice.getPricePolicy()));
                }
            }
            req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES, listPackageGoodsPrices);

            //lay danh sach cac saleserives hien thi duoi dang cay
            List<SaleServicesDetail> listPackageGoodsDetail = getListSaleServicesDetail(saleServicesId);

            req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);

            //xac lap che do hien thi thong tin
            req.setAttribute("packageGoodsMode", "view");

            pageForward = PACKAGE_GOODS_INFO;

        } else {
            pageForward = searchPackageGoods();
        }

        log.info("End method displaySaleServices of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 13/03/2009
     * chuan bi form de them saleServices moi
     *
     */
    public String prepareAddPackageGoods() throws Exception {
        log.info("Begin method prepareAddPackageGoods of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();

        //reset form
        saleServicesForm.resetForm();
        //
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES, new ArrayList<SaleServicesPrice>());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, new ArrayList<SaleServicesModel>());

        //lay danh sach cac telecomServices
        List<TelecomService> listTelecomServices = getListTelecomServices();
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);

        //xac lap che do chuan bi them thong tin
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");

        pageForward = PACKAGE_GOODS;

        log.info("End method prepareAddPackageGoods of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 14/03/2009
     * chuan bi form de sua thong tin cua saleServices
     *
     */
    public String prepareEditPackageGoods() throws Exception {
        log.info("Begin method prepareEditSaleServices of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();

        SaleServices saleServices = getSaleServicesById(this.saleServicesForm.getSaleServicesId());
        if (saleServices != null) {
            this.saleServicesForm.copyDataFromBO(saleServices);

            //lay danh sach cac telecomServices
            List<TelecomService> listTelecomServices = getListTelecomServices();
            req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);

            //xac lap che do hien thi thong tin
            req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");

            pageForward = PACKAGE_GOODS;
        }

        log.info("End method prepareEditSaleServices of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 14/03/2009
     * them saleServices moi hoac update thong tin saleServices vao DB
     *
     */
    public String addOrEditPackageGoods() throws Exception {
        log.info("Begin method addOrEditSaleServices of PackageGoodsDAO ...");
        pageForward = PACKAGE_GOODS;

        HttpServletRequest req = getRequest();
        Long saleServicesId = this.saleServicesForm.getSaleServicesId();
        SaleServices saleServices = getSaleServicesById(saleServicesId);
        if (saleServices == null) {
            //truong hop them saleservices moi
            if (!checkValidPackageGoodsToAdd()) {
                //lay danh sach cac telecomServices
                List<TelecomService> listTelecomServices = getListTelecomServices();
                req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);

                //xac lap che do chuan bi them thong tin
                req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
                return pageForward;
            }

            saleServicesId = getSequence("SALE_SERVICES_SEQ");
            saleServices = new SaleServices();
            saleServicesForm.copyDataToBO(saleServices);
            saleServices.setSaleServicesId(saleServicesId);
            saleServices.setStatus(Constant.STATUS_USE);
            saleServices.setSaleType(Constant.SALE_TYPE_PACKAGE);
            getSession().save(saleServices);

            //dua id len bien form
            this.saleServicesForm.setSaleServicesId(saleServicesId);

            //
            req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, saleServicesId);
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.addsuccessful");

            /* LamNV ADD START */
            //Ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, saleServices));
//            saveLog(Constant.ACTION_ADD_PCK_GOOD, "Thêm mới gói hàng", lstLogObj, saleServices.getSaleServicesId());
            saveLog(Constant.ACTION_ADD_PCK_GOOD, "add.pkg.goods", lstLogObj, saleServices.getSaleServicesId());
            /* LamNV ADD STOP */
        } else {
            //truong hop sua thong tin saleservices da co'
            if (!checkValidPackageGoodsToEdit()) {
                //lay danh sach cac telecomServices
                List<TelecomService> listTelecomServices = getListTelecomServices();
                req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);

                //xac lap che do chuan bi them thong tin
                req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "prepareAddOrEdit");
                return pageForward;
            }

            /* LamNV ADD START */
            SaleServices oldSaleService = new SaleServices();
            BeanUtils.copyProperties(oldSaleService, saleServices);
            /* LamNV ADD STOP */
            saleServicesForm.copyDataToBO(saleServices);
            getSession().update(saleServices);

            //
            req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, saleServicesId);
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.editsuccessful");
            /* LamNV ADD START */
            //Ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldSaleService, saleServices));
//            saveLog(Constant.ACTION_UPDATE_PCK_GOOD, "Cập nhật gói hàng hóa", lstLogObj, saleServices.getSaleServicesId());
            saveLog(Constant.ACTION_UPDATE_PCK_GOOD, "update.pkg.goods", lstLogObj, saleServices.getSaleServicesId());
            /* LamNV ADD STOP */
        }

        //lay danh sach cac telecomServices
        List<TelecomService> listTelecomServices = getListTelecomServices();
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);

        //lay danh sach cac stockTypes


        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_PACKAGE_GOODS_MODE, "view");

        pageForward = PACKAGE_GOODS;

        log.info("End method addOrEditSaleServices of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 14/03/2009
     * xoa thong tin saleServices trong DB, danh dau truong status thanh delete
     *
     */
    public String delPackageGoods() throws Exception {
        log.info("Begin method delSaleServices of PackageGoodsDAO ...");
        /* LamNV ADD START */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /* LamNV ADD STOP */
        HttpServletRequest req = getRequest();

        if (!checkValidPackageGoodsToDel()) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.error.delete");
            pageForward = PACKAGE_GOODS;
            return pageForward;
        }

        String strQuery;
        Query query;

        //cap nhat lai bang SaleServicesPrice
        strQuery = "update SaleServicesPrice set status = ? where saleServicesId = ? and status = ?";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_DELETE);
        query.setParameter(1, saleServicesForm.getSaleServicesId());
        query.setParameter(2, Constant.STATUS_USE);
        query.executeUpdate();
        /* LamNV ADD START */
        lstLogObj.add(new ActionLogsObj("SaleServicesPrice", "status", "", String.valueOf(Constant.STATUS_DELETE)));
        /* LamNV ADD STOP */
        //tim list cac packageGoodsModel cua saleServices nay
        strQuery = "select packageGoodsModelId from SaleServicesModel where saleServicesId = ? and status = ?";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesForm.getSaleServicesId());
        query.setParameter(1, Constant.STATUS_DELETE);
        List<Long> listPackageGoodsModelId = query.list();
        if (listPackageGoodsModelId != null && listPackageGoodsModelId.size() > 0) {

            //xoa thong tin trong bang saleServicesDetail
            String tmpStrQuery = "update SaleServicesDetail set status = ? "
                    + "where status = ? AND packageGoodsModelId in (:plist) ";
                    
            Query tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, Constant.STATUS_DELETE);
            tmpQuery.setParameter(1, Constant.STATUS_USE);
            tmpQuery.setParameterList("plist", listPackageGoodsModelId);


            tmpQuery.executeUpdate();
            /* LamNV ADD START */
            lstLogObj.add(new ActionLogsObj("SaleServicesDetail", "status", "", String.valueOf(Constant.STATUS_DELETE)));
            /* LamNV ADD STOP */
        }

        //cap nhat lai bang SaleServicesModel
        strQuery = "update SaleServicesModel set status = ? where saleServicesId = ? and status = ?";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_DELETE);
        query.setParameter(1, saleServicesForm.getSaleServicesId());
        query.setParameter(2, Constant.STATUS_USE);
        query.executeUpdate();
        /* LamNV ADD START */
        lstLogObj.add(new ActionLogsObj("SaleServicesModel", "status", "", String.valueOf(Constant.STATUS_DELETE)));
        /* LamNV ADD STOP */
        //cap nhat lai bang SaleServices
        strQuery = "update SaleServices set status = ? where saleServicesId = ? and status = ?";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_DELETE);
        query.setParameter(1, saleServicesForm.getSaleServicesId());
        query.setParameter(2, Constant.STATUS_USE);
        query.executeUpdate();
        /* LamNV ADD START */
        lstLogObj.add(new ActionLogsObj("SaleServices", "status", "", String.valueOf(Constant.STATUS_DELETE)));
        /* LamNV ADD STOP */
        //
        req.getSession().setAttribute(SESSION_CURR_PACKAGE_GOODS_ID, -1L);

        //danh sach tat ca cac saleServices
        List<SaleServices> listPackageGoods = getListSaleServices();
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS, listPackageGoods);

        pageForward = LIST_PACKAGE_GOODS;

        log.info("End method delSaleServices of PackageGoodsDAO");
        /* LamNV ADD START */
//        saveLog(Constant.ACTION_DELETE_PCK_GOOD, "Xóa gói hàng hóa", lstLogObj, saleServicesForm.getSaleServicesId());
        saveLog(Constant.ACTION_DELETE_PCK_GOOD, "delete.pkg.goods", lstLogObj, saleServicesForm.getSaleServicesId());
        /* LamNV ADD STOP */

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 10/06/2009
     * chuan bi form them saleServicesDetail moi
     *
     */
    public String prepareAddPackageGoodsDetail() throws Exception {
        log.info("Begin method prepareAddPackageGoodsDetail of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        Long saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
        saleServicesDetailForm.setSaleServicesId(saleServicesId);
        //lay danh sach cac stockType cho combobox
        List<StockType> listStockTypes = getListStockTypes();
        req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);
        //thiet lap che do them moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);
        pageForward = PACKAGE_GOODS_DETAIL;
        log.info("End method prepareAddPackageGoodsDetail of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 16/03/2009
     * chuan bi form de sua thong tin cua saleServicesDetail
     *
     */
    public String prepareEditPackageGoodsDetail() throws Exception {
        log.info("Begin method prepareEditPackageGoodsDetail of PackageGoodsDAO ...");
        pageForward = PACKAGE_GOODS_DETAIL;
        HttpServletRequest req = getRequest();
        try {
            Session session = this.getSession();
            String strId = req.getParameter("id");
            if (strId == null || strId.trim().equals("")) {
                return PACKAGE_GOODS_DETAIL;
            }
            Long id = Long.parseLong(strId);
            SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(id);
            saleServicesDetailForm.setId(id);
            saleServicesDetailForm.setSaleServicesId(saleServicesDetail.getSaleServicesId());
            saleServicesDetailForm.setStockModelId(saleServicesDetail.getStockModelId());
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(session);
            StockModel stockModel = stockModelDAO.findById(saleServicesDetail.getStockModelId());
            saleServicesDetailForm.setStockTypeId(stockModel.getStockTypeId());
            saleServicesDetailForm.setNotes(saleServicesDetail.getNotes());
            List<StockType> listStockTypes = getListStockTypes();
            req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);
            List lst = getStockModelByStockTypeWithBO(stockModel.getStockTypeId());
            req.setAttribute(REQUEST_LIST_STOCK_MODEL, lst);
            //xac lap che do hien thi thong tin
            req.setAttribute("saleServicesDetailMode", "prepareEdit");
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            throw ex;
        }
        log.info("End method prepareEditPackageGoodsDetail of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 15/04/2009
     * xoa packageGoodsDetail khoi goi hang
     *
     */
    public String delPackageGoodsDetail() throws Exception {
        log.info("Begin method delPackageGoodsDetail of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();
        String strId = req.getParameter("id");
        Long id;
        if (strId == null || strId.trim().equals("")) {
            req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE, "packageGoodsDetail.error.delete");
            pageForward = LIST_PACKAGE_GOODS_MODELS;
            return pageForward;
        }
        try {
            id = new Long(strId);
        } catch (NumberFormatException ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE, "packageGoodsDetail.error.delete");
            pageForward = LIST_PACKAGE_GOODS_MODELS;
            return pageForward;
        }

        if (!checkValidPackageGoodsDetailToDel(id)) {
            req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE, "packageGoodsDetail.error.delete");
            pageForward = LIST_PACKAGE_GOODS_MODELS;
            return pageForward;
        }


        SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(id);
        if (saleServicesDetail != null) {
            //cap nhat bang packageGoodsModel
            saleServicesDetail.setStatus(Constant.STATUS_DELETE);
            session.update(saleServicesDetail);
            session.flush();
            List lstSaleServicesDetail = getListSaleServicesDetail(saleServicesDetail.getSaleServicesId());
            req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, lstSaleServicesDetail);
        }
        req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE, "packageGoodsDetail.delete.success");
        pageForward = LIST_PACKAGE_GOODS_MODELS;

        log.info("End method delPackageGoodsDetail of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 31/08/2009
     * kiem tra tinh hop le cua 1 saleservicesdetail truoc khi xoa
     *
     */
    private boolean checkValidPackageGoodsDetailToDel(Long saleServicesDetailId) {
        HttpServletRequest req = getRequest();


        SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(saleServicesDetailId);
        if (saleServicesDetail == null) {
            req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE, "pacakgeGoodsDetail.error.delete");
            return false;
        }

        //gia dich vu da duoc su dung trong 1 giao dich nao chua
        String strQuery = "select count(*) "
                + "from SaleTrans a, SaleTransDetail b "
                + "where a.saleTransId = b.saleTransId and a.saleServiceId = ?  and b.stockModelId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesDetail.getSaleServicesId());
        query.setParameter(1, saleServicesDetail.getStockModelId());

        Long count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_MODEL_MESSAGE, "packageGoodsDetail.error.delete");
            return false;
        }

        return true;
    }
//    private List listItemsCombo = new ArrayList();
//
//    public List getListItemsCombo() {
//        return listItemsCombo;
//    }
//
//    public void setListItemsCombo(List listItemsCombo) {
//        this.listItemsCombo = listItemsCombo;
//    }

    /**
     *
     * author tamdt1
     * date: 10/06/2009
     * lay danh sach cac gia thuc hien dich vu cua 1 mat hang
     *
     */
//    public String getDataForPriceCombobox() throws Exception {
//        try {
//            HttpServletRequest req = getRequest();
//            //lay danh sach gia thuc hien dich vu cua 1 mat hang
//            String strStockModelId = req.getParameter("saleServicesDetailForm.stockModelId");
//            if (strStockModelId != null && !strStockModelId.trim().equals("")) {
//                Long stockModelId = Long.parseLong(strStockModelId);
//                List listPrice = getListPriceForStockModel(stockModelId);
//                String[] header = {"", "--Chọn giá bán kèm dịch vụ--"};
//                this.listItemsCombo.add(header);
//                this.listItemsCombo.addAll(listPrice);
////                for (Object object : listPrice) {
////                    Price price = (Price) object;
////                    String priceDescription = price.getDescription() != null ? (" - " + price.getDescription()) : "";
////                    String[] item = {price.getPriceId().toString(), price.getPrice() + priceDescription};
////                    listItemsCombo.add(item);
////                }
//            }
//
//
//        } catch (Exception e) {
//            String str = CommonDAO.readStackTrace(e);                 log.info(str);
//            throw e;
//        }
//
//        return GET_DATA_FOR_PRICE_COMBOBOX;
//    }
    /**
     *
     * author tamdt1
     * date: 20/03/2009
     * chuan bi form them saleServicesPrice moi
     *
     */
    public String prepareAddPackageGoodsPrice() throws Exception {
        log.info("Begin method prepareAddSaleServicesPrice of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();
        this.saleServicesPriceForm.resetForm();
        Long saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_PACKAGE_GOODS_ID);
        this.saleServicesPriceForm.setSaleServicesId(saleServicesId);

        //thiet lap cac bien can thiet cho combobox
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicies = appParamsDAO.findAppParamsByType("PRICE_POLICY");
        req.setAttribute(REQUEST_LIST_PRICE_POLICIES, listPricePolicies);

        req.setAttribute("isAddMode", true); //thiet lap che do them moi

        pageForward = PACKAGE_GOODS_PRICE;

        log.info("End method prepareAddSaleServicesPrice of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/04/2009
     * chuan bi form sua thong tin price
     *
     */
    public String prepareEditPackageGoodsPrice() throws Exception {
        log.info("Begin method prepareEditSaleServicesPrice of PackageGoodsDAO ...");

        HttpServletRequest req = getRequest();
        String strPriceId = req.getParameter("selectedPriceId");
        Long priceId = -1L;
        if (strPriceId != null) {
            try {
                priceId = new Long(strPriceId);
            } catch (NumberFormatException ex) {
                priceId = -1L;
            }
        }

        SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(priceId);
        if (saleServicesPrice != null) {
            this.saleServicesPriceForm.copyDataFromBO(saleServicesPrice);
            //thiet lap cac bien can thiet cho combobox
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            List<AppParams> listPricePolicies = appParamsDAO.findAppParamsByType("PRICE_POLICY");
            req.setAttribute(REQUEST_LIST_PRICE_POLICIES, listPricePolicies);
        } else {
            this.saleServicesPriceForm.resetForm();
        }

        pageForward = PACKAGE_GOODS_PRICE;

        log.info("End method prepareEditSaleServicesPrice of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 14/04/2009
     * them saleservicesprice vao bien session
     *
     */
    public String addOrEditPackageGoodsPrice() throws Exception {
        log.info("Begin method addOrEditPackageGoodsPrice of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();

        List<SaleServicesPrice> listPackageGoodsPrices = (List<SaleServicesPrice>) req.getSession().getAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES);
        if (listPackageGoodsPrices == null) {
            //neu chua co bien session, thiet lap bien nay
            listPackageGoodsPrices = new ArrayList<SaleServicesPrice>();
            req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES, listPackageGoodsPrices);
        }

        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        HashMap<String, String> pricePolicyHashMap = appParamsDAO.getAppParamsHashMap("PRICE_POLICY");

        Long saleServicesPriceId = this.saleServicesPriceForm.getSaleServicesPriceId();
        SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(saleServicesPriceId);
        if (saleServicesPrice == null) {
            //truong hop them saleServicePrice moi
            if (!checkValidPriceToAdd()) {
                //thiet lap cac bien can thiet cho combobox
                appParamsDAO.setSession(this.getSession());
                List<AppParams> listPricePolicies = appParamsDAO.findAppParamsByType("PRICE_POLICY");
                req.setAttribute(REQUEST_LIST_PRICE_POLICIES, listPricePolicies);

                req.setAttribute("isAddMode", true); //thiet lap che do them moi

                pageForward = PACKAGE_GOODS_PRICE;
                return pageForward;
            }

            saleServicesPrice = new SaleServicesPrice();
            this.saleServicesPriceForm.copyDataToBO(saleServicesPrice);
            saleServicesPriceId = getSequence("SALE_SERVICES_PRICE_SEQ");
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            saleServicesPrice.setSaleServicesPriceId(saleServicesPriceId);
            saleServicesPrice.setCreateDate(getSysdate());
            saleServicesPrice.setUsername(userToken.getLoginName());
            getSession().save(saleServicesPrice);

            saleServicesPrice.setPricePolicyName(pricePolicyHashMap.get(saleServicesPrice.getPricePolicy()));
            listPackageGoodsPrices.add(saleServicesPrice);

        } else {
            //truong hop sua thong tin saleServicePrice da co
            if (!checkValidPriceToEdit()) {
                //thiet lap cac bien can thiet cho combobox
                appParamsDAO.setSession(this.getSession());
                List<AppParams> listPricePolicies = appParamsDAO.findAppParamsByType("PRICE_POLICY");
                req.setAttribute(REQUEST_LIST_PRICE_POLICIES, listPricePolicies);

                pageForward = PACKAGE_GOODS_PRICE;
                return pageForward;
            }

            this.saleServicesPriceForm.copyDataToBO(saleServicesPrice);
            getSession().update(saleServicesPrice);

            saleServicesPrice = getPriceInListById(listPackageGoodsPrices, saleServicesPriceId);
            this.saleServicesPriceForm.copyDataToBO(saleServicesPrice);
            saleServicesPrice.setPricePolicyName(pricePolicyHashMap.get(saleServicesPrice.getPricePolicy()));

        }

        req.setAttribute("packageGoodsPrice", "closePopup");
        pageForward = PACKAGE_GOODS_PRICE;

        log.info("End method addOrEditSaleServicesPrice of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 14/04/2009
     * xoa saleservicesprice khoi bien session
     *
     */
    public String delPackageGoodsPrice() throws Exception {
        log.info("Begin method delPackageGoodsPrice of GoodsDAO ...");

        HttpServletRequest req = getRequest();
        String strPriceId = req.getParameter("selectedPriceId");
        Long priceId = -1L;
        if (strPriceId != null) {
            try {
                priceId = new Long(strPriceId);
            } catch (NumberFormatException ex) {
                priceId = -1L;
            }
        }

        if (checkValidPackageGoodsPriceToDel(priceId)) {
            SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(priceId);

            //cap nhat CSDL
            saleServicesPrice.setStatus(Constant.STATUS_DELETE);
            getSession().update(saleServicesPrice);

            //xoa phan tu o bien sesion
            List<SaleServicesPrice> listPrices = (List<SaleServicesPrice>) req.getSession().getAttribute(SESSION_LIST_PACKAGE_GOODS_PRICES);
            saleServicesPrice = getPriceInListById(listPrices, priceId);
            if (saleServicesPrice != null) {
                listPrices.remove(saleServicesPrice);
            }
        }
        req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_PRICE_MESSAGE, "packageGoodsPrice.delete.success");
        pageForward = LIST_PACKAGE_GOODS_PRICES;

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 31/08/2009
     * kiem tra tinh hop le cua 1 saleservicesprice truoc khi xoa
     *
     */
    private boolean checkValidPackageGoodsPriceToDel(Long priceId) {
        HttpServletRequest req = getRequest();


        SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(priceId);
        if (saleServicesPrice == null) {
            req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_PRICE_MESSAGE, "packageGoodsPrice.error.priceNotFound");
            return false;
        }

        //gia dich vu da duoc su dung trong 1 giao dich nao chua
        String strQuery = "select count(*) "
                + "from SaleTrans "
                + "where saleServicePriceId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, priceId);
        Long count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_LIST_PACKAGE_GOODS_PRICE_MESSAGE, "packageGoodsPrice.error.delete");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 14/04/2009
     * xoa refesh lai danh sach gia dich vu sau khi dong popup
     *
     */
    public String refreshPriceList() throws Exception {
        log.info("Begin method refreshPriceList of PackageGoodsDAO ...");

        pageForward = LIST_PACKAGE_GOODS_PRICES;

        log.info("End method refreshPriceList of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author ThanhNC
     * date: 17/09/2009
     * them hoac sua thong tin packageGoodsDetail
     *
     */
    public String addOrEditPackageGoodsDetail() throws Exception {
        log.info("Begin method addOrEditPackageGoodsDetail of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();
        Session session = getSession();
        List<SaleServicesDetail> listPackageGoodsDetail = (List<SaleServicesDetail>) req.getSession().getAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL);
        if (listPackageGoodsDetail == null) {
            //neu chua co bien session, thiet lap bien nay
            listPackageGoodsDetail = new ArrayList<SaleServicesDetail>();
            req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);
        }

        Long saleServicesDetailId = this.saleServicesDetailForm.getId();
        Long saleServicesId = this.saleServicesDetailForm.getSaleServicesId();
        SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(saleServicesDetailId);
        //Truong hop them moi
        if (saleServicesDetail == null) {
            //truong hop them saleServiceModel moi
            if (!checkValidPackageGoodsDetailToAdd()) {
                //lay danh sach cac stockType cho combobox
                List<StockType> listStockTypes = getListStockTypes();
                req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);
                //chuan bi che do them moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);
                pageForward = PACKAGE_GOODS_DETAIL;
                return pageForward;
            }

            //luu du lieu vao DB
            //Luu du lieu vao bang saleServiceModelDetail

            saleServicesDetail = new SaleServicesDetail();

            saleServicesDetailId = getSequence("SALE_SERVICES_DETAIL_SEQ");
            saleServicesDetail.setId(saleServicesDetailId);
            saleServicesDetail.setStockModelId(saleServicesDetailForm.getStockModelId());
            saleServicesDetail.setNotes(saleServicesDetailForm.getNotes());
            saleServicesDetail.setStatus(Constant.STATUS_USE);
            saleServicesDetail.setSaleServicesId(saleServicesId);
            session.save(saleServicesDetail);
            session.flush();
            listPackageGoodsDetail = getListSaleServicesDetail(saleServicesId);
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsDetail.add.sucess");

        } else {
            //truong hop sua thong tin saleServiceModel da co
            if (!checkValidPackageGoodsDetailToAdd()) {
                List<StockType> listStockTypes = getListStockTypes();
                req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);
                Long stockTypeId = saleServicesDetailForm.getStockTypeId();
                List lst = getStockModelByStockType(stockTypeId);
                req.setAttribute(REQUEST_LIST_STOCK_MODEL, lst);
                pageForward = PACKAGE_GOODS_DETAIL;
                return pageForward;
            }

            //cap nhat vao DB
            saleServicesDetail.setStockModelId(saleServicesDetailForm.getStockModelId());
            saleServicesDetail.setNotes(saleServicesDetailForm.getNotes());
            session.update(saleServicesDetail);
            session.flush();
            listPackageGoodsDetail = getListSaleServicesDetail(saleServicesId);
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsDetail.edit.sucess");

        }

        req.setAttribute(REQUEST_PACKAGE_GOODS_DETAIL_MODE, "closePopup");
        pageForward = PACKAGE_GOODS_DETAIL;

        log.info("End method addOrEditPackageGoodsDetail of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author ThanhNC
     * date: 18/09/2009
     * refesh lai danh sach packageGoodsDetail dau khi dong popup
     *
     */
    public String refreshListPackageGoodsDetail() throws Exception {
        log.info("Begin method refreshListPackageGoodsDetail of PackageGoodsDAO ...");
        HttpServletRequest req = getRequest();

        List listPackageGoodsDetail = getListSaleServicesDetail(saleServicesForm.getSaleServicesId());
        req.getSession().setAttribute(SESSION_LIST_PACKAGE_GOODS_DETAIL, listPackageGoodsDetail);

        pageForward = LIST_PACKAGE_GOODS_MODELS;

        log.info("End method refreshListPackageGoodsDetail of PackageGoodsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 13/03/2009
     * lay danh sach tat ca cac saleServices
     *
     */
    private List<SaleServices> getListSaleServices() {
        List<SaleServices> listPackageGoods = new ArrayList<SaleServices>();

        String strQuery = "from SaleServices where status = ? and saleType= ? order by code";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, Constant.SALE_TYPE_PACKAGE);
        listPackageGoods = query.list();

        return listPackageGoods;
    }

    /**
     *
     * author tamdt1
     * date: 13/03/2009
     * lay danh sach tat ca cac saleServices
     *
     */
    private List<SaleServices> getListSaleServices(Long telecomServiceId) {
        List<SaleServices> listPackageGoods = new ArrayList<SaleServices>();
        if (telecomServiceId == null) {
            return listPackageGoods;
        }

        String strQuery = "from SaleServices where telecomServicesId = ? and status = ? and saleType= ? order by nlssort(name,'nls_sort=Vietnamese') asc ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, telecomServiceId);
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, Constant.SALE_TYPE_PACKAGE);
        listPackageGoods = query.list();

        return listPackageGoods;
    }

    /**
     *
     * author tamdt1
     * date: 02/04/2009
     * lay danh sach tat ca cac stockType
     *
     */
    private List getListStockTypes() {
        List listStockTypes = new ArrayList();

        String strQuery = "from StockType where status = ? order by name";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listStockTypes = query.list();

        return listStockTypes;
    }

    /**
     *
     * author tamdt1
     * date: 15/04/2009
     * lay danh sach tat ca cac telecomServices
     *
     */
    private List<TelecomService> getListTelecomServices() {
        List<TelecomService> listTelecomServices = new ArrayList<TelecomService>();
        String strQuery = "from TelecomService where status = ? order by nlssort(telServiceName,'nls_sort=Vietnamese') asc ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listTelecomServices = query.list();

        return listTelecomServices;
    }

    /**
     *
     * author tamdt1
     * date: 14/03/2009
     * lay SaleServices co id = saleServicesId
     *
     */
    private SaleServices getSaleServicesById(Long saleServicesId) {
        SaleServices saleServices = null;
        if (saleServicesId == null) {
            saleServicesId = -1L;
        }
        String strQuery = "from SaleServices where saleServicesId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesId);
        query.setParameter(1, Constant.STATUS_USE);
        List listPackageGoods = query.list();
        if (listPackageGoods != null && listPackageGoods.size() > 0) {
            saleServices = (SaleServices) listPackageGoods.get(0);
        }

        return saleServices;
    }

    /**
     *
     * author tamdt1
     * date: 15/03/2009
     * lay SaleServicesPrice co id = saleServicesPriceId
     *
     */
    private SaleServicesPrice getSaleServicesPriceById(Long saleServicesPriceId) {
        SaleServicesPrice saleServicesPrice = null;
        if (saleServicesPriceId == null) {
            return saleServicesPrice;
        }
        String strQuery = "from SaleServicesPrice where saleServicesPriceId = ? and status <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesPriceId);
        query.setParameter(1, Constant.STATUS_DELETE);
        List<SaleServicesPrice> listPackageGoodsPrices = query.list();
        if (listPackageGoodsPrices != null && listPackageGoodsPrices.size() > 0) {
            saleServicesPrice = listPackageGoodsPrices.get(0);
        }

        return saleServicesPrice;
    }

    /**
     *
     * author tamdt1
     * date: 16/03/2009
     * lay SaleServicesDetail co id = saleServicesDetailId
     *
     */
    private SaleServicesDetail getSaleServicesDetailById(Long saleServicesDetailId) {
        SaleServicesDetail saleServicesDetail = null;
        if (saleServicesDetailId == null) {
            return null;
        }
        String strQuery = "from SaleServicesDetail where id = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesDetailId);
        query.setParameter(1, Constant.STATUS_USE);
        List listPackageGoodsDetails = query.list();
        if (listPackageGoodsDetails != null && listPackageGoodsDetails.size() > 0) {
            saleServicesDetail = (SaleServicesDetail) listPackageGoodsDetails.get(0);
        }

        return saleServicesDetail;
    }

    /**
     *
     * author tamdt1
     * date: 16/03/2009
     * lay danh sach tat ca cac price cua 1 stock model
     *
     */
    private List getListSaleServicesPrice(Long saleServicesId) {
        List listPrices = new ArrayList();
        if (saleServicesId == null) {
            saleServicesId = -1L;
        }

        String strQuery = "from SaleServicesPrice where saleServicesId = ? and status = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesId);
        query.setParameter(1, Constant.STATUS_USE);
        listPrices = query.list();

        return listPrices;
    }

    /**
     *
     * author tamdt1
     * date: 15/03/2009
     * lay danh sach tat ca cac packageGoodsModel
     *
     */
    private List getListSaleServicesDetail(Long saleServicesId) throws Exception {

        List lstSaleServiceDetail = new ArrayList();
        try {

            if (saleServicesId == null) {
                saleServicesId = -1L;
            }
            String strQuery = "select a.id as id, a.stock_model_id as stockModelId, b.stock_model_code as stockModelCode , "
                    + " b.name as stockModelName, c.name as stockTypeName , a.status as status, a.notes as notes "
                    + " from sale_services_detail a, stock_model b, stock_type c  "
                    + " where a.stock_model_id = b.stock_model_id and b.stock_type_id =c.stock_type_id and a.status =? "
                    + " and b.status = ? and c.status = ?  and a.sale_services_id =? "
                    + " order by nlssort(c.name,'nls_sort=Vietnamese') asc";

            Query query = getSession().createSQLQuery(strQuery).addScalar("id", Hibernate.LONG).addScalar("stockModelId", Hibernate.LONG).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).addScalar("stockTypeName", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("notes", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(SaleServicesDetail.class));
            query.setParameter(0, Constant.STATUS_USE);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, Constant.STATUS_USE);
            query.setParameter(3, saleServicesId);

            lstSaleServiceDetail = query.list();
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
            throw ex;
        }
        return lstSaleServiceDetail;
    }

    /**
     *
     * author tamdt1
     * date: 06/04/2009
     * tra du lieu cho cay saleServices
     *
     */
    public String getPackageGoodsTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();
            HttpServletRequest httpServletRequest = getRequest();
            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");

            if (node.indexOf("0_") == 0) {
                //neu la cay du lieu muc 0, tra ve danh sach cac telecomServices
                TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
                telecomServiceDAO.setSession(this.getSession());
                List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
                Iterator iterTelecomService = listTelecomService.iterator();
                while (iterTelecomService.hasNext()) {
                    TelecomService telecomService = (TelecomService) iterTelecomService.next();
                    String nodeId = "1_" + telecomService.getTelecomServiceId().toString(); //them prefix 1_ de xac dinh la node level
                    getListItems().add(new Node(telecomService.getTelServiceName(), "true", nodeId, "#"));
                }

            } else if (node.indexOf("1_") == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac saleServices tuong ung voi telecomService
                node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                List listPackageGoods = getListSaleServices(Long.valueOf(node));
                Iterator iterPackageGoods = listPackageGoods.iterator();
                while (iterPackageGoods.hasNext()) {
                    SaleServices saleServices = (SaleServices) iterPackageGoods.next();
                    String nodeId = "2_" + saleServices.getSaleServicesId().toString(); //them prefix 2_ de xac dinh la node level
                    getListItems().add(new Node(saleServices.getName(), "true", nodeId, httpServletRequest.getContextPath() + "/packageGoodsAction!displayPackageGoods.do?selectedPackageGoodsId=" + saleServices.getSaleServicesId().toString()));
                }
            } else if (node.indexOf("2_") == 0) {
                //neu la lan lay cay du lieu muc 2, tra ve danh sach cac packageGoodsModel tuong ung voi saleServicesId
                node = node.substring(2); //bo phan prefix danh dau level cua cay (2_)
                List listPackageGoodsModel = hbnSession.createCriteria(SaleServicesModel.class).
                        add(Restrictions.eq("saleServicesId", Long.parseLong(node))).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("stockTypeId")).
                        list();

                Iterator iterSaleServicesModel = listPackageGoodsModel.iterator();
                while (iterSaleServicesModel.hasNext()) {
                    SaleServicesModel packageGoodsModel = (SaleServicesModel) iterSaleServicesModel.next();
                    //ung voi moi packageGoodsModel, tim stockType tuong ung
                    List listStockTypes = hbnSession.createCriteria(StockType.class).
                            add(Restrictions.eq("stockTypeId", packageGoodsModel.getStockTypeId())).
                            add(Restrictions.eq("status", 1L)).
                            list();
                    StockType stockType = (StockType) listStockTypes.get(0);
                    String nodeId = "3_" + node + "_" + packageGoodsModel.getSaleServicesModelId().toString(); //them prefix 3_ de xac dinh node level
                    getListItems().add(new Node(stockType.getName(), "true", nodeId, "#"));
                }
            } else if (node.indexOf("3_") == 0) {
                //neu la lan lay cay du lieu muc 3, tra ve danh sach cac saleServiceDetail tuong ung voi packageGoodsModel
                node = node.substring(2); //bo phan prefix danh dau level cua cay (3_)
                String packageGoodsModelId = node.substring(node.lastIndexOf("_") + 1); //lay id, vi cac id phan cach nhau boi dau "_"
                List listPackageGoodsDetail = hbnSession.createCriteria(SaleServicesDetail.class).
                        add(Restrictions.eq("packageGoodsModelId", Long.parseLong(packageGoodsModelId))).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("id")).
                        list();

                Iterator iterSaleServicesDetail = listPackageGoodsDetail.iterator();
                while (iterSaleServicesDetail.hasNext()) {
                    SaleServicesDetail saleServicesDetail = (SaleServicesDetail) iterSaleServicesDetail.next();
                    //ung voi moi saleServicesDetail, tim stockModel tuong ung
                    List listStockModels = hbnSession.createCriteria(StockModel.class).
                            add(Restrictions.eq("stockModelId", saleServicesDetail.getStockModelId())).
                            add(Restrictions.eq("status", 1L)).
                            list();
                    StockModel stockModel = (StockModel) listStockModels.get(0);
                    String nodeId = "4_" + node + "_" + saleServicesDetail.getId().toString(); //them prefix L_ de xac dinh node level (nut la')
                    getListItems().add(new Node(stockModel.getName(), "false", nodeId, "#"));
                }
            }

            return GET_PACKAGE_GOODS_TREE;

        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
            throw e;
        }
    }

    /**
     *
     * author tamdt1
     * date: 04/05/2009
     * kiem tra tinh hop le cua 1 saleservices truoc khi them vao DB
     *
     */
    private boolean checkValidPackageGoodsToAdd() {
        HttpServletRequest req = getRequest();

        String saleServicesCode = this.saleServicesForm.getCode();
        String saleServicesName = this.saleServicesForm.getName();
        Long telecomServicesId = this.saleServicesForm.getTelecomServicesId();

        if (saleServicesCode == null || saleServicesCode.trim().equals("")
                || saleServicesName == null || saleServicesName.trim().equals("")
                || telecomServicesId == null) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra ma dich vu chi gom cac chu cai a-z, A-Z, 0-9, _
        saleServicesCode = saleServicesCode.trim();
        char[] arrTmp = saleServicesCode.toCharArray();
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
                req.setAttribute(REQUEST_MESSAGE, "packageGoods.error.codeContainsInvalidCharacters");
                return false;
            }
        }

        //kiem tra tinh trung lap cua ma dich vu
        String strQuery = "from SaleServices where code = ? and status = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesCode.trim());
        query.setParameter(1, Constant.STATUS_USE);
        List listPackageGoods = query.list();
        if (listPackageGoods != null && listPackageGoods.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.error.duplicateCode");
            return false;
        }

        //
        this.saleServicesForm.setCode(saleServicesCode.trim());
        this.saleServicesForm.setName(saleServicesName.trim());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 04/05/2009
     * kiem tra tinh hop le cua 1 saleservices truoc khi cap nhat vao DB
     *
     */
    private boolean checkValidPackageGoodsToEdit() {
        HttpServletRequest req = getRequest();

        String saleServicesCode = this.saleServicesForm.getCode();
        String saleServicesName = this.saleServicesForm.getName();
        Long telecomServicesId = this.saleServicesForm.getTelecomServicesId();

        if (saleServicesCode == null || saleServicesCode.trim().equals("")
                || saleServicesName == null || saleServicesName.trim().equals("")
                || telecomServicesId == null) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh trung lap cua ma dich vu va ten dich vu
        String strQuery = "from SaleServices where code = ? and status = ? and saleServicesId <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesCode.trim());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, this.saleServicesForm.getSaleServicesId());
        List listPackageGoods = query.list();
        if (listPackageGoods != null && listPackageGoods.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.error.duplicateCode");
            return false;
        }

        //
        this.saleServicesForm.setCode(saleServicesCode.trim());
        this.saleServicesForm.setName(saleServicesName.trim());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 04/05/2009
     * kiem tra tinh hop le cua 1 saleservices truoc khi xoa
     *
     */
    private boolean checkValidPackageGoodsToDel() {
        HttpServletRequest req = getRequest();

        Long saleServicesId = this.saleServicesForm.getSaleServicesId();
        if (saleServicesId == null) {
            return false;
        }

        //dich vu da duoc su dung trong 1 giao dich nao chua
        String strQuery = "select count(*) "
                + "from SaleTrans "
                + "where saleServiceId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesId);
        Long count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            return false;
        }

        //dich vu co chua gia nao khong
        strQuery = "select count(*) "
                + "from SaleServicesPrice "
                + "where saleServicesId = ? and status <> ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesId);
        query.setParameter(1, Constant.STATUS_DELETE);
        count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            return false;
        }

        //dich vu co stockModel nao khong
        strQuery = "select count(*) "
                + "from SaleServicesModel "
                + "where saleServicesId = ? and status <> ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesId);
        query.setParameter(1, Constant.STATUS_DELETE);
        count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 17/04/2009
     * kiem tra tinh hop le cua 1 price truoc khi sua thong tin ve gia cua 1 mat hang
     *
     */
    private boolean checkValidPriceToEdit() {
        HttpServletRequest req = getRequest();
        Double price = null;

        price = NumberUtils.convertStringtoNumber(saleServicesPriceForm.getPrice());
        
        Long vat = this.saleServicesPriceForm.getVat();
        String strStaDate = this.saleServicesPriceForm.getStaDate();
        String strEndDate = this.saleServicesPriceForm.getEndDate();
        String pricePolicy = this.saleServicesPriceForm.getPricePolicy();
        Long status = this.saleServicesPriceForm.getStatus();

        if (price == null || vat == null
                || pricePolicy == null || pricePolicy.trim().equals("")
                || strStaDate == null || strStaDate.trim().equals("") || status == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0.0) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidDate");
                return false;
            }
        }

        if (this.saleServicesPriceForm.getStatus().equals(Constant.STATUS_USE)) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot dich vu, doi voi 1 chinh sach gia chi co 1 gia active vao 1 thoi diem
            try {
                List<SaleServicesPrice> listPrices = new ArrayList<SaleServicesPrice>();

                //kiem tra dieu kien doi voi staDate
                String strQuery = "from SaleServicesPrice where saleServicesId = ? and pricePolicy = ? "
                        + "and status = ? and staDate <= ? and (endDate >= ? or endDate is null) and saleServicesPriceId <> ?";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, this.saleServicesPriceForm.getSaleServicesId());
                query.setParameter(1, this.saleServicesPriceForm.getPricePolicy());
                query.setParameter(2, Constant.STATUS_USE);
                query.setParameter(3, staDate);
                query.setParameter(4, staDate);
                query.setParameter(5, this.saleServicesPriceForm.getSaleServicesPriceId());
                listPrices = query.list();

                if (listPrices != null && listPrices.size() > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.duplicateTime");
                    return false;
                }

                //kiem tra dieu kien doi voi endDate
                if (endDate != null) {
                    query.setParameter(3, endDate);
                    query.setParameter(4, endDate);
                    listPrices = query.list();

                    if (listPrices != null && listPrices.size() > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.duplicateTime");
                        return false;
                    }
                } else {
                    strQuery = "from SaleServicesPrice where saleServicesId = ? and pricePolicy = ? "
                            + "and status = ? and staDate >= ? and saleServicesPriceId <> ?";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.saleServicesPriceForm.getSaleServicesId());
                    query.setParameter(1, this.saleServicesPriceForm.getPricePolicy());
                    query.setParameter(2, Constant.STATUS_USE);
                    query.setParameter(3, staDate);
                    query.setParameter(4, this.saleServicesPriceForm.getSaleServicesPriceId());
                    listPrices = query.list();

                    if (listPrices != null && listPrices.size() > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.duplicateTime");
                        return false;
                    }
                }


            } catch (Exception ex) {
                String str = CommonDAO.readStackTrace(ex);
                log.info(str);
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 17/04/2009
     * kiem tra tinh hop le cua 1 price truoc khi them gia moi cho dich vu
     *
     */
    private boolean checkValidPriceToAdd() {
        HttpServletRequest req = getRequest();

        Double price = null;

        price = NumberUtils.convertStringtoNumber(saleServicesPriceForm.getPrice());
        
        Long vat = this.saleServicesPriceForm.getVat();
        String strStaDate = this.saleServicesPriceForm.getStaDate();
        String strEndDate = this.saleServicesPriceForm.getEndDate();
        String pricePolicy = this.saleServicesPriceForm.getPricePolicy();
        Long status = this.saleServicesPriceForm.getStatus();

        if (price == null || vat == null
                || pricePolicy == null || pricePolicy.trim().equals("")
                || strStaDate == null || strStaDate.trim().equals("") || status == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0.0) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.invalidDate");
                return false;
            }
        }

        if (this.saleServicesPriceForm.getStatus().equals(Constant.STATUS_USE)) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot dich vu, doi voi 1 chinh sach gia chi co 1 gia active vao 1 thoi diem
            try {
                List<SaleServicesPrice> listPrices = new ArrayList<SaleServicesPrice>();

                //kiem tra dieu kien doi voi staDate
                String strQuery = "from SaleServicesPrice where saleServicesId = ? and pricePolicy = ? "
                        + "and status = ? and staDate <= ? and (endDate >= ? or endDate is null)";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, this.saleServicesPriceForm.getSaleServicesId());
                query.setParameter(1, this.saleServicesPriceForm.getPricePolicy());
                query.setParameter(2, Constant.STATUS_USE);
                query.setParameter(3, staDate);
                query.setParameter(4, staDate);
                listPrices = query.list();

                if (listPrices != null && listPrices.size() > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.duplicateTime");
                    return false;
                }

                //kiem tra dieu kien doi voi endDate
                if (endDate != null) {
                    query.setParameter(3, endDate);
                    query.setParameter(4, endDate);
                    listPrices = query.list();

                    if (listPrices != null && listPrices.size() > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.duplicateTime");
                        return false;
                    }
                } else {
                    strQuery = "from SaleServicesPrice where saleServicesId = ? and pricePolicy = ? "
                            + "and status = ? and staDate >= ?";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.saleServicesPriceForm.getSaleServicesId());
                    query.setParameter(1, this.saleServicesPriceForm.getPricePolicy());
                    query.setParameter(2, Constant.STATUS_USE);
                    query.setParameter(3, staDate);
                    listPrices = query.list();

                    if (listPrices != null && listPrices.size() > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "packageGoodsprice.error.duplicateTime");
                        return false;
                    }
                }


            } catch (Exception ex) {
                String str = CommonDAO.readStackTrace(ex);
                log.info(str);
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }


        return true;
    }

    /**
     *
     * author tamdt1
     * date: 08/06/2009
     * kiem tra tinh hop le cua 1 packageGoodsModel truoc khi them moi
     *
     */
    private boolean checkValidPackageGoodsDetailToAdd() {
        HttpServletRequest req = getRequest();

        Long stockTypeId = this.saleServicesDetailForm.getStockTypeId();
        Long stockModelId = this.saleServicesDetailForm.getStockModelId();

        if (stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || stockModelId == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "packageGoods.error.requiredFieldsEmpty");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 21/04/2009
     * lay Price co id = pricelId trong bien session
     *
     */
    private SaleServicesPrice getPriceInListById(List<SaleServicesPrice> listPrices, Long priceId) {
        SaleServicesPrice saleServicesPrice = null;
        if (listPrices == null || listPrices.size() == 0 || priceId == null) {
            return saleServicesPrice;
        }

        for (int i = 0; i < listPrices.size(); i++) {
            if (listPrices.get(i).getSaleServicesPriceId().equals(priceId)) {
                saleServicesPrice = listPrices.get(i);
                break;
            }
        }

        return saleServicesPrice;
    }

    /**
     *
     * author tamdt1
     * date: 14/05/2009
     * phuc vu muc dich phan tran doi voi saleServicesPrice
     *
     */
    public String pageNagivatorPrice() throws Exception {
        log.info("Begin method pageNagivatorPrice of PackageGoodsDAO ...");
        pageForward = LIST_PACKAGE_GOODS_PRICES;
        log.info("end method pageNagivatorPrice of PackageGoodsDAO");

        return pageForward;
    }

    /* Author: ThanhNC
     * Date created: 10/03/2009
     * Purpose: Chon loai mat hang --> mat hang (con trong kho)
     */
    public String getStockModel() throws Exception {
        try {

            HttpServletRequest httpServletRequest = getRequest();
            //Chon hang hoa tu loai hang hoa          
            String stockTypeId = httpServletRequest.getParameter("stockTypeId");
            if (stockTypeId != null && !stockTypeId.trim().equals("")) {
                Long id = Long.parseLong(stockTypeId);
                listStockModel.addAll(getStockModelByStockType(id));
            }


        } catch (Exception e) {
            String str = CommonDAO.readStackTrace(e);
            log.info(str);
            throw e;
        }

        return "getStockModel";
    }

    private List getStockModelByStockTypeWithBO(Long stockTypeId) {

        List lstResult = new ArrayList();

        if (stockTypeId != null) {
            String SQL_SELECT_STOCK_MODEL = "select new StockModel(stockModelId, stockModelCode ||' --- '|| name) from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
            q.setParameter(0, stockTypeId);
            q.setParameter(1, Constant.STATUS_USE);
            List lstStockModel = q.list();
            lstResult.addAll(lstStockModel);

        }
        return lstResult;
    }

    private List getStockModelByStockType(Long stockTypeId) {
        String SELECT_RES = "--Chọn mặt hàng--";
        //Chon hang hoa tu loai hang hoa
        String[] header = {"", SELECT_RES};
        List lstResult = new ArrayList();
        lstResult.add(header);
        if (stockTypeId != null) {
            String SQL_SELECT_STOCK_MODEL = "select stockModelId, stockModelCode ||' --- '|| name from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
            q.setParameter(0, stockTypeId);
            q.setParameter(1, Constant.STATUS_USE);
            List lstStockModel = q.list();
            lstResult.addAll(lstStockModel);

        }
        return lstResult;
    }
}
