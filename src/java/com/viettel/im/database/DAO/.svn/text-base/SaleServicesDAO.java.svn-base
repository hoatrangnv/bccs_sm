package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.SaleServicesDetailForm;
import com.viettel.im.client.form.SaleServicesForm;
import com.viettel.im.client.form.SaleServicesModelForm;
import com.viettel.im.client.form.SaleServicesPriceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.SaleServicesDetail;
import com.viettel.im.database.BO.SaleServicesModel;
import com.viettel.im.database.BO.SaleServicesPrice;
import com.viettel.im.database.BO.SaleServicesStock;
import com.viettel.im.database.BO.SaleServicesStockId;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.StockTypeProfile;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import com.viettel.im.client.bean.ListMapBean;
import com.viettel.im.client.form.StockModelForm;
import com.viettel.im.common.util.ResourceBundleUtils;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ActionLog;
import com.viettel.im.database.BO.ActionLogDetail;
import java.util.ResourceBundle;

/**
 *
 * @author Doan Thanh 8 xu ly cac tac vu lien quan den dich vu ban hang
 *
 */
public class SaleServicesDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SaleServicesDAO.class);
    private String pageForward;
    //cac bien session, request
    private final String SESSION_LIST_SALE_SERVICES = "listSaleServices";
    private final String SESSION_LIST_SALE_SERVICES_PRICES = "listSaleServicesPrices";
    private final String SESSION_LIST_SALE_SERVICES_MODELS = "listSaleServicesModels";
    private final String SESSION_CURR_SALE_SERVICES_ID = "currentSaleServicesId";
    private final String SESSION_CURR_SALE_SERVICES_PRICE_ID = "currentSaleServicesPriceId";
    private final String SESSION_CURR_SALE_SERVICES_MODEL_ID = "currentSaleServicesModelId";
    private final String REQUEST_LIST_TELECOM_SERVICES = "listTelecomServices";
    private final String REQUEST_LIST_STOCK_TYPES = "listStockTypes";
    private final String REQUEST_LIST_STOCK_MODEL = "listStockModels";
    private final String REQUEST_LIST_PRICES = "listPrices";
    private final String REQUEST_LIST_PRICE_POLICIES = "listPricePolicies";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_SALE_SERVICES_MODEL_MESSAGE = "listSaleServicesModelMessage";
    private final String REQUEST_SALE_SERVICES_MODEL_MODE = "saleServicesModelMode";
    private final String REQUEST_SALE_SERVICES_DETAIL_MODE = "saleServicesDetailMode";
    private final String REQUEST_LIST_AVAILABLE_SHOP_ID = "listAvailableShopId";
    private final String REQUEST_LIST_SELECTED_SHOP_ID = "listSelectedShopId";
    private final String REQUEST_IS_ADD_MODE = "isAddMode"; //flag thiet lap che do them thong tin moi
    private final String REQUEST_IS_EDIT_MODE = "isEditMode"; //flag thiet lap che do sua thong tin da co
    private final String REQUEST_IS_VIEW_MODE = "isViewMode"; //flag thiet lap che do xem thong tin
    //dinh nghia cac hang so pageForward
    private final String SALE_SERVICES_OVERVIEW = "saleServicesOverview";
    private final String SEARCH_SALE_SERVICES = "searchSaleServices";
    private final String SALE_SERVICES_INFO = "saleServicesInfo";
    private final String SALE_SERVICES = "saleServices";
    private final String SALE_SERVICES_DETAIL = "saleServicesDetail";
    private final String SALE_SERVICES_PRICE = "saleServicesPrice";
    private final String SALE_SERVICES_MODEL = "saleServicesModel";
    private final String LIST_SALE_SERVICES = "listSaleServices";
    private final String LIST_SALE_SERVICES_PRICES = "listSaleServicesPrices";
    private final String LIST_SALE_SERVICES_MODELS = "listSaleServicesModels";
    private final String GET_DATA_FOR_SHOP_AUTOCOMPLETER = "getDataForShopAutocompleter";
    private final String UPDATE_SHOP_NAME = "updateShopName";
    private final String GET_DATA_FOR_PRICE_COMBOBOX = "getDataForPriceCombobox";
    private final String GET_SALE_SERVICES_TREE = "getSaleServicesTree";
    private final String REQUEST_LIST_CURRENCY = "listCurrency";
    //bien form
    private SaleServicesForm saleServicesForm = new SaleServicesForm();
    private SaleServicesModelForm saleServicesModelForm = new SaleServicesModelForm();
    private SaleServicesDetailForm saleServicesDetailForm = new SaleServicesDetailForm();
    private SaleServicesPriceForm saleServicesPriceForm = new SaleServicesPriceForm();
    //#TruongNQ5 20140725 R6237
    private final String REQUEST_LIST_RVN_SEVICE_QUALITY = "listRvnServiceQuality";
    private final String REQUEST_LIST_RVN_SEVICE = "listRvnService";
    String schemaRvnService = ResourceBundleUtils.getResource(Constant.SCHEMA_RVN_SEVICE);
    //End TruongNQ5

    //TrongLV, start
    public SaleServices findById(java.lang.Long id) {
        log.debug("getting SaleServices instance with id: " + id);
        try {
            SaleServices instance = (SaleServices) getSession().get("com.viettel.im.database.BO.SaleServices", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public SaleServicesPrice findSSPById(java.lang.Long id) {
        log.debug("getting SaleServicesPrice instance with id: " + id);
        try {
            SaleServicesPrice instance = (SaleServicesPrice) getSession().get("com.viettel.im.database.BO.SaleServicesPrice", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findSSDBySaleServicesId(Long saleServicesId) {
        try {
            String queryString = "from SaleServicesDetail as model where model.saleServicesId=? and model.status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, saleServicesId);
            queryObject.setParameter(1, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by saleServicesId name failed", re);
            throw re;
        }
    }
    //TrongLV, end

    public SaleServicesForm getSaleServicesForm() {
        return saleServicesForm;
    }

    public void setSaleServicesForm(SaleServicesForm saleServicesForm) {
        this.saleServicesForm = saleServicesForm;
    }

    public SaleServicesModelForm getSaleServicesModelForm() {
        return saleServicesModelForm;
    }

    public void setSaleServicesModelForm(SaleServicesModelForm saleServicesModelForm) {
        this.saleServicesModelForm = saleServicesModelForm;
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

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    /**
     *
     * author : tamdt1 date : 12/03/2009 purpose : home page cua phan sale
     * services
     *
     */
    public String saleServicesOverview() throws Exception {
        log.info("Begin method saleServicesOverview of SaleServicesDAO...");

        HttpServletRequest req = getRequest();

        //reset lai cac bien session
        clearSessionAttribute();

        //danh sach tat ca cac saleServices
        List<SaleServices> listSaleServices = getListSaleServices();
        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES, listSaleServices);

        pageForward = SALE_SERVICES_OVERVIEW;

        log.info("End method saleServicesOverview of SaleServicesDAO");

        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 02/11/2009 purpose : reset lai cac bien session
     *
     */
    private void clearSessionAttribute() {
        HttpServletRequest req = getRequest();
        //reset tat ca cac bien session ve null
        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES, new ArrayList());
        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES_MODELS, new ArrayList());
        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES_PRICES, new ArrayList());
        req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_ID, -1L);
    }

    /**
     *
     * author : tamdt1 date : 09/04/2009 purpose : tim kiem danh sach cac
     * saleservices thoa man dieu kien tim kiem
     *
     */
    public String searchSaleServices() throws Exception {
        log.info("Begin method searchSaleServices of SaleServicesDAO...");

        HttpServletRequest req = getRequest();
        List<SaleServices> listSaleServices = new ArrayList<SaleServices>();

        StringBuffer strQuery = new StringBuffer("from SaleServices where 1 = 1 ");
        List listParameters = new ArrayList();

        String saleServicesCode = this.saleServicesForm.getCode();
        String saleServicesName = this.saleServicesForm.getName();
        String saleServicesNotes = this.saleServicesForm.getNotes();
        if (saleServicesCode != null && !saleServicesCode.trim().equals("")) {
            strQuery.append("and lower(code) like ? ");
            listParameters.add("%" + saleServicesCode.trim().toLowerCase() + "%");
        }
        if (saleServicesName != null && !saleServicesName.trim().equals("")) {
            strQuery.append("and lower(name) like ? ");
            listParameters.add("%" + saleServicesName.trim().toLowerCase() + "%");
        }
        if (saleServicesNotes != null && !saleServicesNotes.trim().equals("")) {
            strQuery.append("and lower(notes) like ? ");
            listParameters.add("%" + saleServicesNotes.trim().toLowerCase() + "%");
        }
        strQuery.append("order by code ");

        Query query = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameters.size(); i++) {
            query.setParameter(i, listParameters.get(i));
        }

        listSaleServices = query.list();

        if (listSaleServices != null) {
            req.setAttribute(REQUEST_MESSAGE, "saleservices.searchMessage");
            List listParam = new ArrayList();
            listParam.add(listSaleServices.size());
            req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
        }


        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES, listSaleServices);
        req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_ID, -1L);

        pageForward = SEARCH_SALE_SERVICES;

        log.info("End method searchSaleServices of SaleServicesDAO");

        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 13/05/2009 purpose : ham phuc vu muc dich phan
     * trang
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method pageNavigator of SaleServicesDAO ...");

        pageForward = LIST_SALE_SERVICES;

        log.info("End method pageNavigator of SaleServicesDAO");

        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 13/03/2009 purpose : hien thi thong tin
     * saleServices
     *
     */
    public String displaySaleServices() throws Exception {
        log.info("Begin method displaySaleServices of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();

        //lay id cua saleServices can hien thi
        String strSelectedSaleServicesId = req.getParameter("selectedSaleServicesId");
        Long saleServicesId;
        if (strSelectedSaleServicesId != null) {
            try {
                saleServicesId = Long.valueOf(strSelectedSaleServicesId);
            } catch (NumberFormatException ex) {
                saleServicesId = -1L;
            }
        } else {
            saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_SALE_SERVICES_ID);
        }

        req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_ID, saleServicesId);

        //chuyen du lieu len form
        SaleServices saleServices = getSaleServicesById(saleServicesId);
        if (saleServices != null) {
            this.saleServicesForm.copyDataFromBO(saleServices);
            //TruongNQ5 20140725 R6237 Khoi tao gia tri cho 2 combobox Chi tieu
            if (saleServices.getCode() != null && !"".equals(saleServices.getCode())) {
                prepareEditRVNService(saleServices.getCode());
            }
            //End TruongNQ5
        }

        //lay du lieu cho combobox
        getDataForSaleServicesComboBox();

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        log.info("End method displaySaleServices of SaleServicesDAO");
        pageForward = SALE_SERVICES_INFO;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 13/03/2009 purpose : chuan bi form de them
     * saleServices moi
     *
     */
    public String prepareAddSaleServices() throws Exception {
        log.info("Begin method prepareAddSaleServices of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();

        //reset form
        this.saleServicesForm.resetForm();

        //lay du lieu cho combobox
        getDataForSaleServicesComboBox();

        //xac lap che do them thong tin moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        log.info("End method prepareAddSaleServices of SaleServicesDAO");
        pageForward = SALE_SERVICES;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 21/11/2010 purpose : chuan bi form de them
     * saleServices moi lan dau tien
     *
     */
    public String prepareAddSaleServicesFirstTime() throws Exception {
        log.info("Begin method prepareAddSaleServicesFirstTime of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();

        //reset form
        this.saleServicesForm.resetForm();

        //lay du lieu cho combobox
        getDataForSaleServicesComboBox();

        //xac lap che do them thong tin moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        log.info("End method prepareAddSaleServicesFirstTime of SaleServicesDAO");
        pageForward = SALE_SERVICES_INFO;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 14/03/2009 purrpose : chuan bi form de sua thong
     * tin cua saleServices
     *
     */
    public String prepareEditSaleServices() throws Exception {
        log.info("Begin method prepareEditSaleServices of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();

        Long saleServicesId = this.saleServicesForm.getSaleServicesId();
        SaleServices saleServices = getSaleServicesById(saleServicesId);
        //lay du lieu len form
        if (saleServices != null) {
            this.saleServicesForm.copyDataFromBO(saleServices);
            //TruongNQ5 20140725 R6237 Khoi tao gia tri cho 2 combobox Chi tieu
            if (saleServices.getCode() != null && !"".equals(saleServices.getCode())) {
                prepareEditRVNService(saleServices.getCode());
            }
            //End TruongNQ5
            //xac lap che do sua thong tin
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);
        }

        //lay du lieu cho combobox
        getDataForSaleServicesComboBox();

        log.info("End method prepareEditSaleServices of SaleServicesDAO");
        pageForward = SALE_SERVICES;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 14/03/2009 purpose : them saleServices moi hoac
     * update thong tin saleServices vao DB
     *
     */
    public String addOrEditSaleServices() throws Exception {
        log.info("Begin method addOrEditSaleServices of SaleServicesDAO ...");

        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */
        HttpServletRequest req = getRequest();
        //TruongNQ5 20140725 Chi tieu san luong va chi tieu doanh thu
        Long rvnServiceId = this.saleServicesForm.getRvnServiceId();
        Long rvnServiceQualityId = this.saleServicesForm.getRvnServiceQualityId();
        //End TruongNQ5

        Long saleServicesId = this.saleServicesForm.getSaleServicesId();
        SaleServices saleServices = getSaleServicesById(saleServicesId);
        if (saleServices == null) {
            //truong hop them saleservices moi
            if (!checkValidSaleServicesToAdd()) {
                //xac lap che do chuan bi them thong tin
                req.setAttribute(REQUEST_IS_ADD_MODE, true);

                //lay du lieu cho combobox
                getDataForSaleServicesComboBox();

                log.info("End method addOrEditSaleServices of SaleServicesDAO");
                pageForward = SALE_SERVICES;
                return pageForward;
            }

            saleServicesId = getSequence("SALE_SERVICES_SEQ");
            saleServices = new SaleServices();
            this.saleServicesForm.copyDataToBO(saleServices);
            saleServices.setSaleServicesId(saleServicesId);
            saleServices.setSaleType(Constant.SALE_SERVICES_TYPE_SERVICE); //loai dich vu dau noi cm
            getSession().save(saleServices);
            //TruongNQ5 20140725 Insert 2 Chi tieu san luong va chi tieu doanh thu vao DB
            try {
                if (rvnServiceId != null) {
                    String sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query query = getSession().createSQLQuery(sql);
                    query.setParameter(0, index);
                    query.setParameter(1, Constant.DEPT_ID_MOV);
                    query.setParameter(2, this.saleServicesForm.getRvnServiceId());
                    query.setParameter(3, this.saleServicesForm.getCode());
                    query.setParameter(4, Constant.TYPE_SERVICES);
                    query.setParameter(5, Constant.STATUS_ACTIVE);
                    query.executeUpdate();
                    saveLogRVNServiceDeptMap(Constant.ACTION_ADD_SALE_SERVICE, getText("add.new.sales.criteria"), "rvn_service_dept_map", index, "service_id", "", this.saleServicesForm.getRvnServiceId().toString());
                }
                if (rvnServiceQualityId != null) {
                    String sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query query = getSession().createSQLQuery(sql);
                    query.setParameter(0, index);
                    query.setParameter(1, Constant.DEPT_ID_MOV);
                    query.setParameter(2, this.saleServicesForm.getRvnServiceQualityId());
                    query.setParameter(3, this.saleServicesForm.getCode());
                    query.setParameter(4, Constant.TYPE_SERVICES);
                    query.setParameter(5, Constant.STATUS_ACTIVE);
                    query.executeUpdate();
                    saveLogRVNServiceDeptMap(Constant.ACTION_ADD_SALE_SERVICE, getText("add.new.quality.criteria"), "rvn_service_dept_map", index, "service_id", "", this.saleServicesForm.getRvnServiceQualityId().toString());
                }
                this.getSession().getTransaction().commit();
                this.getSession().beginTransaction();
            } catch (Exception e) {
                e.printStackTrace();
                this.getSession().getTransaction().rollback();
                req.setAttribute(REQUEST_MESSAGE, "Error.RVN.Service.Dept.Map");
                pageForward = SALE_SERVICES;
                return pageForward;
            }
            //End TruongNQ5

            //dua id len bien form
            this.saleServicesForm.setSaleServicesId(saleServicesId);

            //
            req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_ID, saleServicesId);
            req.setAttribute(REQUEST_MESSAGE, "saleservices.addsuccessful");

            /*
             * LamNV ADD START
             */
            //Ghi log
            lstLogObj.add(new ActionLogsObj(null, saleServices));
            saveLog(Constant.ACTION_ADD_SALE_SERVICE, "Thêm mới dịch vụ bán hàng", lstLogObj, saleServices.getSaleServicesId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.ADD);
            /*
             * LamNV ADD STOP
             */
        } else {
            //truong hop sua thong tin saleservices da co'
            if (!checkValidSaleServicesToEdit()) {
                //xac lap che do chuan bi sua thong tin
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);

                //lay du lieu cho combobox
                getDataForSaleServicesComboBox();

                log.info("End method addOrEditSaleServices of SaleServicesDAO");
                pageForward = SALE_SERVICES;
                return pageForward;
            }

            /*
             * LamNV ADD START
             */
            SaleServices oldSaleService = new SaleServices();
            BeanUtils.copyProperties(oldSaleService, saleServices);
            /*
             * LamNV ADD STOP
             */
            saleServicesForm.copyDataToBO(saleServices);
            getSession().update(saleServices);
            //TruongNQ5 20140725  R6237 cap nhat 2 gia tri cua 2 combobox chi tieu
            updateRVNService(rvnServiceId, this.saleServicesForm.getCode(), this.saleServicesForm.getServiceIndex());
            updateRVNServiceQuality(rvnServiceQualityId, this.saleServicesForm.getCode(), this.saleServicesForm.getServiceQualityIndex());
            this.getSession().getTransaction().commit();
            this.getSession().beginTransaction();

            //
            req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_ID, saleServicesId);
            req.setAttribute(REQUEST_MESSAGE, "saleservices.editsuccessful");
            /*
             * LamNV ADD START
             */
            //Ghi log
            lstLogObj.add(new ActionLogsObj(oldSaleService, saleServices));
            saveLog(Constant.ACTION_UPDATE_SALE_SERVICE, "Cập nhật dịch vụ bán hàng", lstLogObj, saleServices.getSaleServicesId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.EDIT);
            /*
             * LamNV ADD STOP
             */
        }

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //lay du lieu cho combobox
        getDataForSaleServicesComboBox();

        log.info("End method addOrEditSaleServices of SaleServicesDAO");
        pageForward = SALE_SERVICES;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 14/03/2009 purpose : xoa thong tin saleServices
     * trong DB
     *
     */
    public String delSaleServices() throws Exception {
        log.info("Begin method delSaleServices of SaleServicesDAO ...");

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        HttpServletRequest req = getRequest();

        if (!checkValidSaleServicesToDel()) {
            req.setAttribute(REQUEST_MESSAGE, "saleservices.error.delete");
            pageForward = SALE_SERVICES;
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

        /*
         * LamNV ADD START
         */
        lstLogObj.add(new ActionLogsObj("SaleServicesPrice", "status", String.valueOf(Constant.STATUS_DELETE), ""));
        /*
         * LamNV ADD STOP
         */
        //tim list cac saleServicesModel cua saleServices nay
        strQuery = "select saleServicesModelId from SaleServicesModel where saleServicesId = ? and status = ?";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesForm.getSaleServicesId());
        query.setParameter(1, Constant.STATUS_DELETE);
        List<Long> listSaleServicesModelId = query.list();
        if (listSaleServicesModelId != null && listSaleServicesModelId.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i = 0; i < listSaleServicesModelId.size(); i++) {
                stringBuffer.append(",");
                stringBuffer.append(listSaleServicesModelId.get(i).toString());
            }
            stringBuffer.delete(0, 1);

            //xoa thong tin trong bang saleServicesDetail
            String tmpStrQuery = "update SaleServicesDetail set status = ? "
                    + "where saleServicesModelId in (" + stringBuffer.toString() + ") "
                    + "and status = ? ";
            Query tmpQuery = getSession().createQuery(tmpStrQuery);
            tmpQuery.setParameter(0, Constant.STATUS_DELETE);
            tmpQuery.setParameter(1, Constant.STATUS_USE);
            tmpQuery.executeUpdate();
            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj("SaleServicesDetail", "status", String.valueOf(Constant.STATUS_DELETE), ""));
            /*
             * LamNV ADD STOP
             */
        }

        //cap nhat lai bang SaleServicesModel
        strQuery = "update SaleServicesModel set status = ? where saleServicesId = ? and status = ?";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_DELETE);
        query.setParameter(1, saleServicesForm.getSaleServicesId());
        query.setParameter(2, Constant.STATUS_USE);
        query.executeUpdate();
        /*
         * LamNV ADD START
         */
        lstLogObj.add(new ActionLogsObj("SaleServicesModel", "status", String.valueOf(Constant.STATUS_DELETE), ""));
        /*
         * LamNV ADD STOP
         */

        //cap nhat lai bang SaleServices
        strQuery = "update SaleServices set status = ? where saleServicesId = ? and status = ?";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_DELETE);
        query.setParameter(1, saleServicesForm.getSaleServicesId());
        query.setParameter(2, Constant.STATUS_USE);
        query.executeUpdate();
        /*
         * LamNV ADD START
         */
        lstLogObj.add(new ActionLogsObj("SaleServices", "status", String.valueOf(Constant.STATUS_DELETE), ""));
        /*
         * LamNV ADD STOP
         */

        //
        req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_ID, -1L);

        //danh sach tat ca cac saleServices
        List<SaleServices> listSaleServices = getListSaleServices();
        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES, listSaleServices);

        pageForward = LIST_SALE_SERVICES;

        log.info("End method delSaleServices of SaleServicesDAO");

        /*
         * LamNV ADD START
         */
        saveLog(Constant.ACTION_DELETE_SALE_SERVICE, "Xóa dịch vụ bán hàng", lstLogObj, saleServicesForm.getSaleServicesId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.DELETE);
        /*
         * LamNV ADD STOP
         */
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 04/11/2009 purpose : lay du lieu can thiet cho cac
     * combobox khi thao tac voi saleServices
     *
     */
    private void getDataForSaleServicesComboBox() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac telecomServices
        List<TelecomService> listTelecomServices = getListTelecomServices();
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, listTelecomServices);
        //#TruongNQ5 20140725 R6237
        List<ListMapBean> listRvnServiceQuality = getListRvnServiceQuality();
        List<ListMapBean> listRvnService = getListRvnService();
        req.setAttribute(REQUEST_LIST_RVN_SEVICE_QUALITY, listRvnServiceQuality);
        req.setAttribute(REQUEST_LIST_RVN_SEVICE, listRvnService);
        //End TruongNQ

        //

    }

    /**
     *
     * author tamdt1 date: 10/06/2009 chuan bi form them saleServicesDetail moi
     *
     */
    public String prepareAddSaleServicesDetail() throws Exception {
        log.info("Begin method prepareAddSaleServicesDetail of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();
        String strSaleServicesModelId = req.getParameter("saleServicesModelId");
        if (strSaleServicesModelId == null || strSaleServicesModelId.trim().equals("")) {
            pageForward = SALE_SERVICES_DETAIL;
            log.info("End method prepareAddSaleServicesDetail of SaleServicesDAO");
            return pageForward;
        }

        Long saleServicesModelId = Long.valueOf(strSaleServicesModelId);
        SaleServicesModel saleServicesModel = getSaleServicesModelById(saleServicesModelId);
        if (saleServicesModel == null) {
            pageForward = SALE_SERVICES_DETAIL;
            log.info("End method prepareAddSaleServicesDetail of SaleServicesDAO");
            return pageForward;
        }

        //chuan bi du lieu cho cac combobox
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        StockType stockType = stockTypeDAO.findById(saleServicesModel.getStockTypeId());
        List<StockType> listStockType = new ArrayList<StockType>();
        listStockType.add(stockType);
        req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockType);


        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                StockModelDAO.STOCK_TYPE_ID, stockType.getStockTypeId(), Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);

        this.saleServicesDetailForm.resetForm();
        this.saleServicesDetailForm.setSaleServicesModelId(saleServicesModelId);
        this.saleServicesDetailForm.setStockTypeId(stockType.getStockTypeId());

        pageForward = SALE_SERVICES_DETAIL;

        log.info("End method prepareAddSaleServicesDetail of SaleServicesDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 chuan bi form de sua thong tin cua
     * saleServicesDetail
     *
     */
    public String prepareEditSaleServicesDetail() throws Exception {
        log.info("Begin method prepareEditSaleServicesDetail of GoodsDAO ...");

        HttpServletRequest req = getRequest();

        //xac lap che do hien thi thong tin
        req.setAttribute("saleServicesDetailMode", "prepareEdit");

        pageForward = SALE_SERVICES_DETAIL;

        log.info("End method prepareEditSaleServicesDetail of GoodsDAO");

        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 16/03/2009 purpose : them saleServicesDetail bien
     * session
     *
     */
    public String addOrEditSaleServicesDetail() throws Exception {
        log.info("Begin method addOrEditSaleServicesDetail of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();
        /*
         * Hoangpm3 ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * Hoangom3 ADD STOP
         */

        List<SaleServicesModel> listSaleServicesModels = (List<SaleServicesModel>) req.getSession().getAttribute(SESSION_LIST_SALE_SERVICES_MODELS);
        Long saleServicesModelId = this.saleServicesDetailForm.getSaleServicesModelId();
        Long saleServicesDetailId = this.saleServicesDetailForm.getId();
        Long stockTypeId = this.saleServicesDetailForm.getStockTypeId();

        SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(saleServicesDetailId);
        if (saleServicesDetail == null) {
            //truong hop them moi
            if (!checkValidSaleServicesDetailToAdd()) {
                //chuan bi du lieu cho cac combobox
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                StockType stockType = stockTypeDAO.findById(stockTypeId);
                List<StockType> listStockType = new ArrayList<StockType>();
                listStockType.add(stockType);
                req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockType);

                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                List<StockModel> listStockModel = stockModelDAO.findByPropertyWithStatus(
                        StockModelDAO.STOCK_TYPE_ID, stockType.getStockTypeId(), Constant.STATUS_USE);
                req.setAttribute(REQUEST_LIST_STOCK_MODEL, listStockModel);

                List<Price> listPrice = getListPrice(this.saleServicesDetailForm.getStockModelId());
                req.setAttribute(REQUEST_LIST_PRICES, listPrice);

                pageForward = SALE_SERVICES_DETAIL;
                log.info("End method addOrEditSaleServicesDetail of SaleServicesDAO");
                return pageForward;
            }

            //them du lieu vao DB
            saleServicesDetail = new SaleServicesDetail();
            this.saleServicesDetailForm.copyDataToBO(saleServicesDetail);
            saleServicesDetailId = getSequence("SALE_SERVICES_DETAIL_SEQ");
            saleServicesDetail.setId(saleServicesDetailId);
            saleServicesDetail.setStatus(Constant.STATUS_USE);
            getSession().save(saleServicesDetail);

            //
            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(null, saleServicesDetail));
            saveLog(Constant.ACTION_ADD_SALE_SERVICE_DETAIL, "Thêm mới chi tiết hàng hóa", lstLogObj, saleServicesDetailId, Constant.DEFINE_SERVICE_OF_SALE, Constant.ADD);
            /*
             * LamNV ADD STOP
             */


            //them du lieu vao bien session
            for (int i = 0; i < listSaleServicesModels.size(); i++) {
                SaleServicesModel saleServicesModel = listSaleServicesModels.get(i);
                if (saleServicesModel.getSaleServicesModelId().equals(saleServicesModelId)) {
                    List<SaleServicesDetail> listSaleServicesDetail = saleServicesModel.getListSaleServicesDetail();
                    if (listSaleServicesDetail == null) {
                        listSaleServicesDetail = new ArrayList<SaleServicesDetail>();
                    }

                    //lay du lieu phuc vu viec hien thi
                    StockModel stockModel = getStockModelById(saleServicesDetail.getStockModelId());
                    if (stockModel != null) {
                        saleServicesDetail.setStockModelName(stockModel.getName());
                    }
                    Price price = getPriceById(saleServicesDetail.getPriceId());
                    if (price != null) {
                        saleServicesDetail.setPrice(price.getPrice());
                        saleServicesDetail.setPriceDescription(price.getDescription());
                    }


                    listSaleServicesDetail.add(saleServicesDetail);
                    break;
                }
            }
        }

        req.setAttribute(REQUEST_SALE_SERVICES_DETAIL_MODE, "closePopup");
        pageForward = SALE_SERVICES_DETAIL;
        log.info("End method addOrEditSaleServicesDetail of SaleServicesDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 10/06/2009 kiem tra tinh hop le cua 1
     * saleServicesDetail truoc khi them moi
     *
     */
    private boolean checkValidSaleServicesDetailToAdd() {
        HttpServletRequest req = getRequest();

        List<SaleServicesModel> listSaleServicesModels = (List<SaleServicesModel>) req.getSession().getAttribute(SESSION_LIST_SALE_SERVICES_MODELS);
        if (listSaleServicesModels == null) {
            //truong hop danh sach loai mat hang thuoc dich vu bi null
            req.setAttribute(REQUEST_MESSAGE, "saleservicesdetail.error.listSaleServicesModelsEmpty");
            return false;
        }

        Long saleServicesModelId = this.saleServicesDetailForm.getSaleServicesModelId();
        Long stockModelId = this.saleServicesDetailForm.getStockModelId();
        Long priceId = this.saleServicesDetailForm.getPriceId();

        if (saleServicesModelId == null || saleServicesModelId.compareTo(0L) <= 0
                || stockModelId == null || stockModelId.compareTo(0L) <= 0
                || priceId == null || priceId.compareTo(0L) <= 0) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "saleservicesdetail.error.requiredFieldsEmpty");
            return false;
        }


        //kiem tra mat hang them vao da ton tai chua
        String strQuery = "select count(*) "
                + "from SaleServicesDetail "
                + "where saleServicesModelId = ? and stockModelId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesModelId);
        query.setParameter(1, stockModelId);
        Long count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "saleservicesdetail.error.duplicateStockModel");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 15/04/2009 xoa saleServicesModel khoi dich vu ban
     * hang
     *
     */
    public String delSaleServicesModel() throws Exception {
        log.info("Begin method deleteSaleServicesModel of SaleServicesDAO ...");

        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */
        HttpServletRequest req = getRequest();

        Long saleServicesModelId = this.saleServicesModelForm.getSaleServicesModelId();
        if (!checkValidSaleServicesModelToDel(saleServicesModelId)) {
            req.setAttribute(REQUEST_MESSAGE, "saleservicesmodel.error.delete");

            log.info("End method deleteSaleServicesModel of SaleServicesDAO");
            pageForward = SALE_SERVICES_MODEL;
            return pageForward;
        }

        try {
            SaleServicesModel saleServicesModel = getSaleServicesModelById(saleServicesModelId);
            if (saleServicesModel != null) {
                //cap nhat bang saleServicesModel
                getSession().delete(saleServicesModel);
                /*
                 * LamNV ADD START
                 */
                lstLogObj.add(new ActionLogsObj(saleServicesModel, null));

                /*
                 * LamNV ADD STOP
                 */

                //cap nhat bang saleServicesStock
                //xoa cac kho dac biet
                String strQuery = "delete from SaleServicesStock where id.saleServicesModelId = ?  ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, saleServicesModelId);
                int iResult = query.executeUpdate();
                /*          
                 * Hoangpm3 ADD START
                 */
                lstLogObj.add(new ActionLogsObj("SaleServicesStock", "status", String.valueOf(Constant.STATUS_DELETE), ""));
                saveLog(Constant.ACTION_DELETE_SALE_SERVICE_MODEL, "Xóa mặt hàng DVBH", lstLogObj, saleServicesModel.getSaleServicesModelId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.DELETE);
                /*
                 * Hoangpm3 ADD STOP
                 */

                //cap nhat lai bien session
                List<SaleServicesModel> listSaleServicesModels = (List<SaleServicesModel>) req.getSession().getAttribute(LIST_SALE_SERVICES_MODELS);
                if (listSaleServicesModels != null) {
                    for (int i = 0; i < listSaleServicesModels.size(); i++) {
                        saleServicesModel = listSaleServicesModels.get(i);
                        if (saleServicesModel.getSaleServicesModelId().equals(saleServicesModelId)) {
                            listSaleServicesModels.remove(i);
                            break;
                        }
                    }
                }

                //
                this.saleServicesModelForm.resetForm();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        log.info("End method deleteSaleServicesModel of SaleServicesDAO");
        pageForward = SALE_SERVICES_MODEL;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 15/04/2009 xoa saleServicesDetail khoi bien session
     *
     */
    public String delSaleServicesDetail() throws Exception {
        log.info("Begin method deleteSaleServicesDetail of GoodsDAO ...");

        HttpServletRequest req = getRequest();

        String strSaleServicesModelId = req.getParameter("saleServicesModelId");
        String strSaleServicesDetailId = req.getParameter("saleServicesDetailId");

        Long saleServicesModelId;
        Long saleServicesDetailId;

        if (strSaleServicesModelId != null && strSaleServicesDetailId != null) {
            try {
                saleServicesModelId = new Long(strSaleServicesModelId);
                saleServicesDetailId = new Long(strSaleServicesDetailId);
            } catch (NumberFormatException ex) {
                saleServicesModelId = -1L;
                saleServicesDetailId = -1L;
            }

            if (!checkValidSaleServicesDetailToDel(saleServicesDetailId)) {
                //
                log.info("End method deleteSaleServicesDetail of GoodsDAO");
                pageForward = LIST_SALE_SERVICES_MODELS;
                return pageForward;
            }

            SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(saleServicesDetailId);
            if (saleServicesDetail != null) {
                //cap nhat lai bang saleServicesDetail
                getSession().delete(saleServicesDetail);
            }
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(saleServicesDetail, null));
            saveLog(Constant.ACTION_DELETE_SALE_SERVICE_DETAIL, "Xóa mặt hàng khỏi dv bán hàng", lstLogObj, saleServicesDetail.getSaleServicesId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.DELETE);
            //cap nhat lai bien session
            List<SaleServicesModel> listSaleServicesModels = (List<SaleServicesModel>) req.getSession().getAttribute(SESSION_LIST_SALE_SERVICES_MODELS);
            if (listSaleServicesModels != null) {
                SaleServicesModel saleServicesModel = new SaleServicesModel();
                for (int i = 0; i < listSaleServicesModels.size(); i++) {
                    saleServicesModel = listSaleServicesModels.get(i);
                    if (saleServicesModel.getSaleServicesModelId().equals(saleServicesModelId)) {
                        List<SaleServicesDetail> listSaleServicesDetail = saleServicesModel.getListSaleServicesDetail();
                        for (int j = 0; j < listSaleServicesDetail.size(); j++) {
                            saleServicesDetail = listSaleServicesDetail.get(j);
                            if (saleServicesDetail.getId().equals(saleServicesDetailId)) {
                                listSaleServicesDetail.remove(j);
                                break;
                            }
                        }
                        break;
                    }
                }
            }

            //
            req.setAttribute(REQUEST_LIST_SALE_SERVICES_MODEL_MESSAGE, "saleservicesdetail.delsuccessful");
        }

        log.info("End method deleteSaleServicesDetail of GoodsDAO");
        pageForward = LIST_SALE_SERVICES_MODELS;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 31/08/2009 kiem tra tinh hop le cua 1
     * saleservicesdetail truoc khi xoa
     *
     */
    private boolean checkValidSaleServicesDetailToDel(Long saleServicesDetailId) {
        HttpServletRequest req = getRequest();

        SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(saleServicesDetailId);
        if (saleServicesDetail == null) {
            req.setAttribute(REQUEST_LIST_SALE_SERVICES_MODEL_MESSAGE, "saleservicesdetail.error.delete");
            return false;
        }

        //gia dich vu da duoc su dung trong 1 giao dich nao chua
        //ThanhNC comment on 4/4/2010 cho phep xoa mat hang khoi dich vu ban hang
        /*
         * String strQuery = "select count(*) " + "from SaleTransDetail " +
         * "where stockModelId = ? " + "and saleServicesId in (select
         * saleServicesId from SaleServicesModel where saleServicesModelId = ? )
         * "; Query query = getSession().createQuery(strQuery);
         * query.setParameter(0, saleServicesDetail.getStockModelId());
         * query.setParameter(1, saleServicesDetail.getSaleServicesModelId());
         * Long count = (Long) query.list().get(0); if(count != null &&
         * count.compareTo(0L) > 0) {
         * req.setAttribute(REQUEST_LIST_SALE_SERVICES_MODEL_MESSAGE,
         * "saleservicesdetail.error.delete"); return false; }
         */
        return true;
    }
    private List listItemsCombo = new ArrayList();

    public List getListItemsCombo() {
        return listItemsCombo;
    }

    public void setListItemsCombo(List listItemsCombo) {
        this.listItemsCombo = listItemsCombo;
    }

    /**
     *
     * author tamdt1 date: 10/06/2009 lay danh sach cac gia thuc hien dich vu
     * cua 1 mat hang
     *
     */
    public String getDataForPriceCombobox() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            //lay danh sach gia thuc hien dich vu cua 1 mat hang
            String strStockModelId = req.getParameter("saleServicesDetailForm.stockModelId");
            if (strStockModelId != null && !strStockModelId.trim().equals("")) {
                Long stockModelId = Long.parseLong(strStockModelId);
                List listPrice = getListPriceForStockModel(stockModelId);
                String[] header = {"", "--Chọn giá bán kèm dịch vụ--"};
                this.listItemsCombo.add(header);
                this.listItemsCombo.addAll(listPrice);
//                for (Object object : listPrice) {
//                    Price price = (Price) object;
//                    String priceDescription = price.getDescription() != null ? (" - " + price.getDescription()) : "";
//                    String[] item = {price.getPriceId().toString(), price.getPrice() + priceDescription};
//                    listItemsCombo.add(item);
//                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return GET_DATA_FOR_PRICE_COMBOBOX;
    }

//    /**
//     *
//     * author tamdt1
//     * date: 16/03/2009
//     * sua thong tin saleServicesDetail trong DB
//     * tuong ung voi action saleServicesModelAction, form saleServicesModelForm
//     *
//     */
//    public String editSaleServicesDetail() throws Exception {
//        log.info("Begin method editSaleServicesDetail of GoodsDAO ...");
//
//        HttpServletRequest req = getRequest();
//
//        //save thong tin ve SaleServicesDetail vao bang SALE_SERVICES_DETAIL
//        SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(saleServicesDetailForm.getId());
//        if (saleServicesDetail != null) {
//            saleServicesDetail.setPriceId(saleServicesDetailForm.getPriceId());
//            saleServicesDetail.setNotes(saleServicesDetailForm.getNotes());
////                    saleServicesModelForm.copyDataToBO(saleServicesModel);
//            getSession().save(saleServicesDetail);
//            req.getSession().setAttribute("message", "saleservicesdetail.editsuccessful");
//        }
//
//        req.getSession().setAttribute("displayInfo", "saleServicesDetail");
//        pageForward = SALE_SERVICES_OVERVIEW;
//
//        log.info("End method editSaleServicesDetail of GoodsDAO");
//
//        return pageForward;
//    }
//    /**
//     *
//     * author tamdt1
//     * date: 16/03/2009
//     * xoa thong tin saleServicesDetail trong DB, danh dau truong status thanh delete
//     * tuong ung voi action saleServicesAction, form saleServicesForm
//     *
//     */
//    public String delSaleServicesDetail() throws Exception {
//        log.info("Begin method delSaleServicesDetail of GoodsDAO ...");
//
//        HttpServletRequest req = getRequest();
//
//        //cap nhat lai bang SaleServicesDetail
//        String strQuery = "update SaleServicesDetail set status = ? where id = ?";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, Constant.STATUS_DELETE);
//        query.setParameter(1, saleServicesDetailForm.getId());
//        query.executeUpdate();
//
//        //
//        req.getSession().setAttribute("message", "saleservicesdetail.delsuccessful");
//        req.getSession().setAttribute("currentSaleServicesDetailId", -1L);
//
//        req.getSession().setAttribute("displayInfo", "saleServicesDetail");
//        pageForward = SALE_SERVICES_OVERVIEW;
//
//        log.info("End method delSaleServicesDetail of GoodsDAO");
//
//        return pageForward;
//    }
//    /**
//     *
//     * author tamdt1
//     * date: 16/03/2009
//     * lay thong tin, dua len form SaleServicesModel duoc chon
//     *
//     */
//    public String selectSaleServicesDetail() throws Exception {
//        log.info("Begin method selectSaleServicesDetail of GoodsDAO ...");
//
//        HttpServletRequest req = getRequest();
//
//        Long saleServicesDetailId;
//        try {
//            saleServicesDetailId = new Long(req.getParameter("selectedSaleServicesDetailId"));
//        } catch (NumberFormatException ex) {
//            saleServicesDetailId = -1L;
//        }
//
//        SaleServicesDetail saleServicesDetail = getSaleServicesDetailById(saleServicesDetailId);
//        if (saleServicesDetail != null) {
//            saleServicesDetailForm.copyDataFromBO(saleServicesDetail);
//            req.getSession().setAttribute("currentSaleServicesDetailId", saleServicesDetail.getId());
//        }
//
//        //xac lap che do hien thi thong tin
//        req.setAttribute("saleServicesDetailMode", "view");
//
//        pageForward = SALE_SERVICES_DETAIL;
//
//        log.info("End method selectSaleServicesDetail of GoodsDAO");
//
//        return pageForward;
//    }
    /**
     *
     * author : tamdt1 date : 03/11/2009 purpose : hien thi thong tin
     * saleServicesPrice
     *
     */
    public String displaySaleServicesPrice() throws Exception {
        log.info("Begin method displaySaleServicesPrice of SaleServicesDAO...");

        HttpServletRequest req = getRequest();

        String strSelectedSaleServicesPriceId = req.getParameter("selectedSaleServicesPriceId");
        Long saleServicesPriceId;
        if (strSelectedSaleServicesPriceId != null) {
            try {
                saleServicesPriceId = Long.valueOf(strSelectedSaleServicesPriceId);
            } catch (NumberFormatException ex) {
                saleServicesPriceId = -1L;
            }
        } else {
            saleServicesPriceId = (Long) req.getSession().getAttribute(SESSION_CURR_SALE_SERVICES_PRICE_ID);
        }

        //chuyen du lieu len form
        SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(saleServicesPriceId);
        if (saleServicesPrice != null) {
            req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_PRICE_ID, saleServicesPriceId);
            saleServicesPriceForm.copyDataFromBO(saleServicesPrice);
        }

        //lay du lieu can thiet cho combobox
        getDataForSaleServicesPriceComboBox();

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //lay danh sach gia cua dich vu
        Long saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_SALE_SERVICES_ID);
        List<SaleServicesPrice> listSaleServicesPrices = getListSaleServicesPrice(saleServicesId);
        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES_PRICES, listSaleServicesPrices);

        log.info("End method displaySaleServicesPrice of SaleServicesDAO");
        pageForward = SALE_SERVICES_PRICE;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 20/03/2009 purpose : chuan bi form them
     * saleServicesPrice moi
     *
     */
    public String prepareAddSaleServicesPrice() throws Exception {
        log.info("Begin method prepareAddSaleServicesPrice of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();
        this.saleServicesPriceForm.resetForm();
        Long saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_SALE_SERVICES_ID);
        this.saleServicesPriceForm.setSaleServicesId(saleServicesId);

        //lay du lieu can thiet cho combobox
        getDataForSaleServicesPriceComboBox();

        //xac lap che do them thong tin
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        log.info("End method prepareAddSaleServicesPrice of SaleServicesDAO");
        pageForward = SALE_SERVICES_PRICE;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 21/04/2009 purrpose : chuan bi form sua thong tin
     * price
     *
     */
    public String prepareEditSaleServicesPrice() throws Exception {
        log.info("Begin method prepareEditSaleServicesPrice of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();

        Long saleServicesPriceId = this.saleServicesPriceForm.getSaleServicesPriceId();
        SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(saleServicesPriceId);

        //chuyen du lieu len form
        if (saleServicesPrice != null) {
            this.saleServicesPriceForm.copyDataFromBO(saleServicesPrice);
        }

        //lay du lieu can thiet cho combobox
        getDataForSaleServicesPriceComboBox();

        //xac lap che do them thong tin
        req.setAttribute(REQUEST_IS_EDIT_MODE, true);

        log.info("End method prepareEditSaleServicesPrice of SaleServicesDAO");
        pageForward = SALE_SERVICES_PRICE;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 14/04/2009 purpose : them saleservicesprice
     *
     */
    public String addOrEditSaleServicesPrice() throws Exception {
        log.info("Begin method addOrEditSaleServicesPrice of SaleServicesDAO ...");
        HttpServletRequest req = getRequest();

        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */
        List<SaleServicesPrice> listSaleServicesPrices = (List<SaleServicesPrice>) req.getSession().getAttribute(SESSION_LIST_SALE_SERVICES_PRICES);
        if (listSaleServicesPrices == null) {
            //neu chua co bien session, thiet lap bien nay
            listSaleServicesPrices = new ArrayList<SaleServicesPrice>();
            req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES_PRICES, listSaleServicesPrices);
        }

        Long saleServicesPriceId = this.saleServicesPriceForm.getSaleServicesPriceId();
        SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(saleServicesPriceId);
        if (saleServicesPrice == null) {
            //truong hop them saleServicePrice moi
            if (!checkValidPriceToAdd()) {
                //lay du lieu can thiet cho combobox
                getDataForSaleServicesPriceComboBox();

                //xac lap che do them thong tin
                req.setAttribute(REQUEST_IS_ADD_MODE, true);

                log.info("End method addOrEditSaleServicesPrice of SaleServicesDAO");
                pageForward = SALE_SERVICES_PRICE;
                return pageForward;
            }

            //luu du lieu vao DB
            saleServicesPrice = new SaleServicesPrice();
            this.saleServicesPriceForm.copyDataToBO(saleServicesPrice);
            saleServicesPriceId = getSequence("SALE_SERVICES_PRICE_SEQ");
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            saleServicesPrice.setSaleServicesPriceId(saleServicesPriceId);
            saleServicesPrice.setCreateDate(getSysdate());
            saleServicesPrice.setUsername(userToken.getLoginName());
            getSession().save(saleServicesPrice);

            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(null, saleServicesPrice));
            saveLog(Constant.ACTION_ADD_SALE_SERVICE_PRICE, "Thêm mới phí DVBH", lstLogObj, saleServicesPrice.getSaleServicesPriceId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.ADD);
            /*
             * LamNV ADD STOP
             */

            //them vao bien session
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            HashMap<String, String> pricePolicyHashMap = appParamsDAO.getAppParamsHashMap("PRICE_POLICY");
            saleServicesPrice.setPricePolicyName(pricePolicyHashMap.get(saleServicesPrice.getPricePolicy()));
            listSaleServicesPrices.add(0, saleServicesPrice);

            //
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.addsuccessful");

        } else {
            //truong hop sua thong tin saleServicePrice da co
            if (!checkValidPriceToEdit()) {
                //lay du lieu can thiet cho combobox
                getDataForSaleServicesPriceComboBox();

                //xac lap che do them thong tin
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);

                log.info("End method addOrEditSaleServicesPrice of SaleServicesDAO");
                pageForward = SALE_SERVICES_PRICE;
                return pageForward;
            }

            /*
             * LamNV ADD START
             */
            SaleServicesPrice oldSaleServicePrice = new SaleServicesPrice();
            BeanUtils.copyProperties(oldSaleServicePrice, saleServicesPrice);
            /*
             * LamNV ADD STOP
             */
            //luu du lieu vao DB
            this.saleServicesPriceForm.copyDataToBO(saleServicesPrice);
            getSession().update(saleServicesPrice);

            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(oldSaleServicePrice, saleServicesPrice));
            saveLog(Constant.ACTION_UPDATE_SALE_SERVICE_PRICE, "Cập nhật phí DVBH", lstLogObj, saleServicesPrice.getSaleServicesPriceId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.EDIT);
            /*
             * LamNV ADD STOP
             */
            //cap nhat lai thong tin bien session
            saleServicesPrice = getPriceInListById(listSaleServicesPrices, saleServicesPriceId);
            this.saleServicesPriceForm.copyDataToBO(saleServicesPrice);
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            HashMap<String, String> pricePolicyHashMap = appParamsDAO.getAppParamsHashMap("PRICE_POLICY");
            saleServicesPrice.setPricePolicyName(pricePolicyHashMap.get(saleServicesPrice.getPricePolicy()));

            //
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.editsuccessful");

        }

        //lay du lieu can thiet cho combobox
        getDataForSaleServicesPriceComboBox();

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        log.info("End method addOrEditSaleServicesPrice of SaleServicesDAO");
        pageForward = SALE_SERVICES_PRICE;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 14/04/2009 xoa saleservicesprice khoi bien session
     *
     */
    public String delSaleServicesPrice() throws Exception {
        log.info("Begin method delSaleServicesPrice of SaleServicesDAO ...");

        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */
        HttpServletRequest req = getRequest();

        try {
            Long saleServicesPriceId = this.saleServicesPriceForm.getSaleServicesPriceId();

            if (checkValidSaleServicesPriceToDel(saleServicesPriceId)) {
                SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(saleServicesPriceId);
                /*
                 * LamNV ADD START
                 */
                lstLogObj.add(new ActionLogsObj(null, saleServicesPrice));
                saveLog(Constant.ACTION_DELETE_SALE_SERVICE_PRICE, "Xóa giá DVBH", lstLogObj, saleServicesPrice.getSaleServicesPriceId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.DELETE);
                /*
                 * LamNV ADD STOP
                 */

                //cap nhat CSDL
                getSession().delete(saleServicesPrice);

                //xoa phan tu o bien sesion
                List<SaleServicesPrice> listPrices = (List<SaleServicesPrice>) req.getSession().getAttribute(SESSION_LIST_SALE_SERVICES_PRICES);
                saleServicesPrice = getPriceInListById(listPrices, saleServicesPriceId);
                if (saleServicesPrice != null) {
                    listPrices.remove(saleServicesPrice);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        //reset form
        this.saleServicesPriceForm.resetForm();

        //lay du lieu can thiet cho combobox
        getDataForSaleServicesPriceComboBox();

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //
        req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.delsuccessful");

        pageForward = SALE_SERVICES_PRICE;
        log.info("End method delSaleServicesPrice of SaleServicesDAO");
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 05/11/2009 purpose : lay du lieu can thiet cho cac
     * combobox khi thao tac voi saleServicesPrice
     *
     */
    private void getDataForSaleServicesPriceComboBox() throws Exception {
        HttpServletRequest req = getRequest();

        //thiet lap cac bien can thiet cho combobox
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());
        List<AppParams> listPricePolicies = appParamsDAO.findAppParamsByType("PRICE_POLICY");
        req.setAttribute(REQUEST_LIST_PRICE_POLICIES, listPricePolicies);

        List<AppParams> listCurrency = appParamsDAO.findAppParamsByType("CURRENCY");
        req.setAttribute(REQUEST_LIST_CURRENCY, listCurrency);
    }

    /**
     *
     * author tamdt1 date: 31/08/2009 kiem tra tinh hop le cua 1
     * saleservicesprice truoc khi xoa
     *
     */
    private boolean checkValidSaleServicesPriceToDel(Long priceId) {
        HttpServletRequest req = getRequest();

        SaleServicesPrice saleServicesPrice = getSaleServicesPriceById(priceId);
        if (saleServicesPrice == null) {
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.priceNotFound");
            return false;
        }

        //gia dich vu da duoc su dung trong 1 giao dich nao chua
        String strQuery = "select count(*) "
                + "from SaleTransDetail "
                + "where saleServicesPriceId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, priceId);
        Long count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.delete");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 14/04/2009 xoa refesh lai danh sach gia dich vu sau
     * khi dong popup
     *
     */
    public String refreshPriceList() throws Exception {
        log.info("Begin method refreshPriceList of SaleServicesDAO ...");

        pageForward = LIST_SALE_SERVICES_PRICES;

        log.info("End method refreshPriceList of SaleServicesDAO");

        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 10/11/2009 purpose : hien thi thong tin
     * saleServicesModel
     *
     */
    public String displaySaleServicesModel() throws Exception {
        log.info("Begin method displaySaleServicesModel of SaleServicesDAO...");

        HttpServletRequest req = getRequest();

        String strSelectedSaleServicesModelId = req.getParameter("selectedSaleServicesModelId");
        Long saleServicesModelId;
        if (strSelectedSaleServicesModelId != null) {
            try {
                saleServicesModelId = Long.valueOf(strSelectedSaleServicesModelId);
            } catch (NumberFormatException ex) {
                saleServicesModelId = -1L;
            }
        } else {
            saleServicesModelId = (Long) req.getSession().getAttribute(SESSION_CURR_SALE_SERVICES_MODEL_ID);
        }

        //chuyen du lieu len form
        SaleServicesModel saleServicesModel = getSaleServicesModelById(saleServicesModelId);
        if (saleServicesModel != null) {
            req.getSession().setAttribute(SESSION_CURR_SALE_SERVICES_MODEL_ID, saleServicesModelId);
            this.saleServicesModelForm.copyDataFromBO(saleServicesModel);

            //lay danh sach cac kho chuc nang
            String strQuery = "select new com.viettel.im.database.BO.Shop(a.id.shopId, b.shopCode, b.name, b.shopCode || ' - ' || b.name) "
                    + "from SaleServicesStock a, Shop b "
                    + "where a.id.shopId = b.shopId and a.id.saleServicesModelId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, saleServicesModelId);
            List<SaleServicesStock> listSaleServicesStock = query.list();
            req.setAttribute(REQUEST_LIST_SELECTED_SHOP_ID, listSaleServicesStock);
        }

        //lay du lieu can thiet cho combobox
        getDataForSaleServicesModelComboBox();

        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        //lay danh sach cac saleserives hien thi duoi dang cay
        Long saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_SALE_SERVICES_ID);
        List<SaleServicesModel> listSaleServicesModels = getListSaleServicesModel(saleServicesId);
        if (listSaleServicesModels != null && listSaleServicesModels.size() > 0) {
            for (int i = 0; i < listSaleServicesModels.size(); i++) {
                SaleServicesModel tmpSaleServicesModel = listSaleServicesModels.get(i);

                //lay danh sach cac kho chuc nang
                String strQuery = "from SaleServicesStock a "
                        + "where a.id.saleServicesModelId = ? ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, tmpSaleServicesModel.getSaleServicesModelId());
                List<SaleServicesStock> listSaleServicesStock = query.list();
                StringBuffer tmpShopName = new StringBuffer("");
                if (listSaleServicesStock != null) {
                    for (int j = 0; j < listSaleServicesStock.size(); j++) {
                        tmpShopName.append(listSaleServicesStock.get(j).getShopCode() + ", ");
                    }
                }
                tmpSaleServicesModel.setShopName(tmpShopName.toString());

                //lay danh sach cac mat hang thuoc loai mat hang nay
                List<SaleServicesDetail> listSaleServicesDetail = getListSaleServicesDetail(tmpSaleServicesModel.getSaleServicesModelId());
                tmpSaleServicesModel.setListSaleServicesDetail(listSaleServicesDetail);
            }
        }
        req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES_MODELS, listSaleServicesModels);

        log.info("End method displaySaleServicesModel of SaleServicesDAO");
        pageForward = SALE_SERVICES_MODEL;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 08/06/2009 chuan bi form them saleServicesModel moi
     *
     */
    public String prepareAddSaleServicesModel() throws Exception {
        log.info("Begin method prepareAddSaleServicesModel of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();

        this.saleServicesModelForm.resetForm();
        Long saleServicesId = (Long) req.getSession().getAttribute(SESSION_CURR_SALE_SERVICES_ID);
        this.saleServicesModelForm.setSaleServicesId(saleServicesId);

        //lay danh sach cac stockType cho combobox
        List<StockType> listStockTypes = getListStockTypes();
        req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);

        //lay danh sach cac avaiableShopId dua len bien request
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        List<Shop> listAvailbleShop = shopDAO.findByPropertyWithStatus(ShopDAO.CHANNEL_TYPE_ID,
                Constant.SPECIAL_SHOP_ID, Constant.STATUS_ACTIVE, ShopDAO.NAME);
        req.setAttribute(REQUEST_LIST_AVAILABLE_SHOP_ID, listAvailbleShop);

        //thiet lap che do them moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = SALE_SERVICES_MODEL;

        log.info("End method prepareAddSaleServicesModel of SaleServicesDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 06/06/2009 chuan bi form sua thong tin
     * saleServicesModel
     *
     */
    public String prepareEditSaleServicesModel() throws Exception {
        log.info("Begin method prepareEditSaleServicesModel of SaleServicesDAO ...");

        HttpServletRequest req = getRequest();

        Long saleServicesModelId = this.saleServicesModelForm.getSaleServicesModelId();
        SaleServicesModel saleServicesModel = getSaleServicesModelById(saleServicesModelId);

        //chuyen du lieu len form
        if (saleServicesModel != null) {
            this.saleServicesModelForm.copyDataFromBO(saleServicesModel);

            //lay danh sach cac kho chuc nang
            String strQuery = "select new com.viettel.im.database.BO.Shop(a.id.shopId, b.shopCode, b.name, b.shopCode || ' - ' || b.name) "
                    + "from SaleServicesStock a, Shop b "
                    + "where a.id.shopId = b.shopId and a.id.saleServicesModelId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, saleServicesModelId);
            List<SaleServicesStock> listSaleServicesStock = query.list();
            req.setAttribute(REQUEST_LIST_SELECTED_SHOP_ID, listSaleServicesStock);
        }

        //lay danh sach cac stockType cho combobox
        getDataForSaleServicesModelComboBox();

        //chuan bi che do sua thong tin
        req.setAttribute(REQUEST_IS_EDIT_MODE, true);

        log.info("End method prepareEditSaleServicesModel of SaleServicesDAO");
        pageForward = SALE_SERVICES_MODEL;
        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 06/06/2009 them hoac sua thong tin saleServicesModel
     *
     */
    public String addOrEditSaleServicesModel() throws Exception {
        log.info("Begin method addOrEditSaleServicesModel of SaleServicesDAO ...");
        HttpServletRequest req = getRequest();

        /*
         * LamNV ADD START
         */
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        /*
         * LamNV ADD STOP
         */
        List<SaleServicesModel> listSaleServicesModels = (List<SaleServicesModel>) req.getSession().getAttribute(SESSION_LIST_SALE_SERVICES_MODELS);
        if (listSaleServicesModels == null) {
            //neu chua co bien session, thiet lap bien nay
            listSaleServicesModels = new ArrayList<SaleServicesModel>();
            req.getSession().setAttribute(SESSION_LIST_SALE_SERVICES_MODELS, listSaleServicesModels);
        }

        Long saleServicesModelId = this.saleServicesModelForm.getSaleServicesModelId();
        SaleServicesModel saleServicesModel = getSaleServicesModelById(saleServicesModelId);
        if (saleServicesModel == null) {
            //truong hop them saleServiceModel moi
            if (!checkValidSaleServicesModelToAdd()) {
                //lay danh sach cac stockType cho combobox
                getDataForSaleServicesModelComboBox();

                //lay danh sach cac ko chuc nang dac biet da duoc chon
                String[] arrSelectedShopId = this.saleServicesModelForm.getArrSelectedShopId();
                if (arrSelectedShopId != null && arrSelectedShopId.length > 0) {
                    List<Shop> listSelectedShop = new ArrayList<Shop>();
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    for (int i = 0; i < arrSelectedShopId.length; i++) {
                        Shop tmpShop = shopDAO.findById(Long.valueOf(arrSelectedShopId[i]));
                        tmpShop.setShopCodeAndName(tmpShop.getShopCode() + " - " + tmpShop.getName());
                        listSelectedShop.add(tmpShop);
                    }

                    req.setAttribute(REQUEST_LIST_SELECTED_SHOP_ID, listSelectedShop);
                }

                //chuan bi che do them moi
                req.setAttribute(REQUEST_IS_ADD_MODE, true);

                pageForward = SALE_SERVICES_MODEL;
                log.info("End method addOrEditSaleServicesModel of SaleServicesDAO");
                return pageForward;
            }

            //luu du lieu vao DB
            saleServicesModel = new SaleServicesModel();
            this.saleServicesModelForm.copyDataToBO(saleServicesModel);
            saleServicesModelId = getSequence("SALE_SERVICES_MODEL_SEQ");
            saleServicesModel.setSaleServicesModelId(saleServicesModelId);
            saleServicesModel.setStatus(Constant.STATUS_USE);
            getSession().save(saleServicesModel);

            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(null, saleServicesModel));
            /*
             * LamNV ADD STOP
             */
            //neu co kho dac biet cua viettel, luu vao bang saleServicesStock
            String[] arrSelectedShopId = this.saleServicesModelForm.getArrSelectedShopId();
            //danh sach ten cac kho chuc nang
            StringBuffer tmpShopName = new StringBuffer("");
            if (arrSelectedShopId != null && arrSelectedShopId.length > 0) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                for (int i = 0; i < arrSelectedShopId.length; i++) {
                    Shop tmpShop = shopDAO.findById(Long.valueOf(arrSelectedShopId[i]));
                    SaleServicesStockId saleServicesStockId = new SaleServicesStockId(saleServicesModelId, Long.valueOf(arrSelectedShopId[i]));
                    SaleServicesStock saleServicesStock = new SaleServicesStock(saleServicesStockId, tmpShop.getShopCode());
                    getSession().save(saleServicesStock);
                    /*
                     * LamNV ADD START
                     */
                    lstLogObj.add(new ActionLogsObj(null, saleServicesStock));
                    /*
                     * LamNV ADD STOP
                     */
                    tmpShopName.append(tmpShop.getShopCode() + ", ");
                }
            }
            saleServicesModel.setShopName(tmpShopName.toString());

            //dua du lieu len form
            this.saleServicesModelForm.copyDataFromBO(saleServicesModel);

            //them du lieu vao bien session
            StockType stockType = getStockTypeById(saleServicesModel.getStockTypeId());
            if (stockType != null) {
                saleServicesModel.setStockTypeName(stockType.getName());
            }
            listSaleServicesModels.add(saleServicesModel);

            /*
             * LamNV ADD START
             */
            saveLog(Constant.ACTION_ADD_SALE_SERVICE_STOCK, "Thêm mới kho hàng vào DVBH sale_services_model_id = " + saleServicesModel.getSaleServicesModelId(), lstLogObj, saleServicesModel.getSaleServicesModelId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.ADD);
            /*
             * LamNV ADD STOP
             */
            //
            req.setAttribute(REQUEST_MESSAGE, "saleservicesmodel.addsuccessful");

        } else {
            //truong hop sua thong tin saleServiceModel da co
            if (!checkValidSaleServicesModelToEdit()) {
                //lay danh sach cac stockType cho combobox
                getDataForSaleServicesModelComboBox();

                //lay danh sach cac ko chuc nang dac biet da duoc chon
                String[] arrSelectedShopId = this.saleServicesModelForm.getArrSelectedShopId();
                if (arrSelectedShopId != null && arrSelectedShopId.length > 0) {
                    List<Shop> listSelectedShop = new ArrayList<Shop>();
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    for (int i = 0; i < arrSelectedShopId.length; i++) {
                        Shop tmpShop = shopDAO.findById(Long.valueOf(arrSelectedShopId[i]));
                        tmpShop.setShopCodeAndName(tmpShop.getShopCode() + " - " + tmpShop.getName());
                        listSelectedShop.add(tmpShop);
                    }

                    req.setAttribute(REQUEST_LIST_SELECTED_SHOP_ID, listSelectedShop);
                }

                //chuan bi che do sua thong tin
                req.setAttribute(REQUEST_IS_EDIT_MODE, true);

                pageForward = SALE_SERVICES_MODEL;
                log.info("End method addOrEditSaleServicesModel of SaleServicesDAO");
                return pageForward;
            }

            //cap nhat vao DB
            /*
             * LamNV ADD START
             */
            SaleServicesModel oldSaleServicesModel = new SaleServicesModel();
            BeanUtils.copyProperties(oldSaleServicesModel, saleServicesModel);
            /*
             * LamNV ADD STOP
             */
            this.saleServicesModelForm.copyDataToBO(saleServicesModel);
            /*
             * LamNV ADD START
             */
            lstLogObj.add(new ActionLogsObj(oldSaleServicesModel, saleServicesModel));
            /*
             * LamNV ADD STOP
             */
            getSession().update(saleServicesModel);

            //
            //edit lai danh sach cac kho chuc nang
            //xoa cac kho da co
            String strQuery = "delete from SaleServicesStock where id.saleServicesModelId = ?  ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, saleServicesModelId);
            int iResult = query.executeUpdate();
            //them cac kho chuc nang moi
            String[] arrSelectedShopId = this.saleServicesModelForm.getArrSelectedShopId();
            //danh sach ten cac kho chuc nang
            StringBuffer tmpShopName = new StringBuffer("");

            if (arrSelectedShopId != null && arrSelectedShopId.length > 0) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                for (int i = 0; i < arrSelectedShopId.length; i++) {
                    SaleServicesStock oldSaleServicesStock = new SaleServicesStock();
                    Shop tmpShop = shopDAO.findById(Long.valueOf(arrSelectedShopId[i]));
                    SaleServicesStockId saleServicesStockId = new SaleServicesStockId(saleServicesModelId, Long.valueOf(arrSelectedShopId[i]));
                    SaleServicesStock saleServicesStock = new SaleServicesStock(saleServicesStockId, tmpShop.getShopCode());
                    BeanUtils.copyProperties(oldSaleServicesStock, saleServicesStock);
                    getSession().save(saleServicesStock);
                    /*
                     * LamNV ADD START
                     */
                    lstLogObj.add(new ActionLogsObj(oldSaleServicesStock, saleServicesStock));
                    /*
                     * LamNV ADD STOP
                     */
                    tmpShopName.append(tmpShop.getShopCode() + ", ");
                }
            }

            //cap nhat thong tin tren bien session
            SaleServicesModel tmpSaleServicesModel = null;
            for (int i = 0; i < listSaleServicesModels.size(); i++) {
                if (listSaleServicesModels.get(i).getSaleServicesModelId().equals(saleServicesModelId)) {
                    tmpSaleServicesModel = listSaleServicesModels.get(i);
                    break;
                }
            }
            if (tmpSaleServicesModel != null) {
                this.saleServicesModelForm.copyDataToBO(tmpSaleServicesModel);
                tmpSaleServicesModel.setShopName(tmpShopName.toString());
            }

            /*
             * LamNV ADD START
             */
            saveLog(Constant.ACTION_UPDATE_SALE_SERVICE_STOCK, "Cập nhật kho hàng trong DVBH sale_services_model_id = " + saleServicesModel.getSaleServicesModelId(), lstLogObj, saleServicesModel.getSaleServicesModelId(), Constant.DEFINE_SERVICE_OF_SALE, Constant.EDIT);
            /*
             * LamNV ADD STOP
             */
            //
            req.setAttribute(REQUEST_MESSAGE, "saleservicesmodel.editsuccessful");
        }


        //lay danh sach cac stockType cho combobox
        getDataForSaleServicesModelComboBox();

        //lay danh sach cac ko chuc nang dac biet da duoc chon
        String[] arrSelectedShopId = this.saleServicesModelForm.getArrSelectedShopId();
        if (arrSelectedShopId != null && arrSelectedShopId.length > 0) {
            List<Shop> listSelectedShop = new ArrayList<Shop>();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            for (int i = 0; i < arrSelectedShopId.length; i++) {
                Shop tmpShop = shopDAO.findById(Long.valueOf(arrSelectedShopId[i]));
                tmpShop.setShopCodeAndName(tmpShop.getShopCode() + " - " + tmpShop.getName());
                listSelectedShop.add(tmpShop);
            }

            req.setAttribute(REQUEST_LIST_SELECTED_SHOP_ID, listSelectedShop);
        }

        //chuan bi che do view thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        log.info("End method addOrEditSaleServicesModel of SaleServicesDAO");
        pageForward = SALE_SERVICES_MODEL;
        return pageForward;
    }

    /**
     *
     * author : tamdt1 date : 10/11/2009 purpose : lay du lieu can thiet cho cac
     * combobox khi thao tac voi saleServicesModel
     *
     */
    private void getDataForSaleServicesModelComboBox() throws Exception {
        HttpServletRequest req = getRequest();

        //lay danh sach cac stockType cho combobox
        List<StockType> listStockTypes = getListStockTypes();
        req.setAttribute(REQUEST_LIST_STOCK_TYPES, listStockTypes);
    }

    /**
     *
     * author tamdt1 date: 08/06/2009 refesh lai danh sach saleServicesModel dau
     * khi dong popup
     *
     */
    public String refreshListSaleServicesModel() throws Exception {
        log.info("Begin method refreshListSaleServicesModel of SaleServicesDAO ...");

        pageForward = LIST_SALE_SERVICES_MODELS;

        log.info("End method refreshListSaleServicesModel of SaleServicesDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay danh sach tat ca cac stockType
     *
     */
    private List getListStockModel(Long stockTypeId) {
        List listStockModels = new ArrayList();
        if (stockTypeId == null) {
            stockTypeId = -1L;
        }

        String strQuery = "from StockModel where stockTypeId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockTypeId);
        query.setParameter(1, Constant.STATUS_USE);
        listStockModels = query.list();

        return listStockModels;
    }

    /**
     *
     * author tamdt1 date: 15/03/2009 lay StockType co id = stockTypeId
     *
     */
    private StockType getStockTypeById(Long stockTypeId) {
        StockType stockType = null;
        if (stockTypeId == null) {
            stockTypeId = -1L;
        }
        String strQuery = "from StockType where stockTypeId = ? and status = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockTypeId);
        query.setParameter(1, Constant.STATUS_USE);
        List listStockTypes = query.list();
        if (listStockTypes != null && listStockTypes.size() > 0) {
            stockType = (StockType) listStockTypes.get(0);
        }

        return stockType;
    }

    /**
     *
     * author tamdt1 date: 13/03/2009 lay danh sach tat ca cac saleServices
     *
     */
    private List<SaleServices> getListSaleServices() {
        List<SaleServices> listSaleServices = new ArrayList<SaleServices>();
        String strQuery = "from SaleServices "
                + "order by code ";
        Query query = getSession().createQuery(strQuery);
        listSaleServices = query.list();

        return listSaleServices;
    }

    /**
     *
     * author tamdt1 date: 13/03/2009 lay danh sach tat ca cac saleServices theo
     * telecomservices
     *
     */
    private List<SaleServices> getListSaleServices(Long telecomServiceId) {
        List<SaleServices> listSaleServices = new ArrayList<SaleServices>();
        if (telecomServiceId == null) {
            return listSaleServices;
        }

        String strQuery = "from SaleServices "
                + "where telecomServicesId = ? and status = ? "
                + "order by code ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, telecomServiceId);
        query.setParameter(1, Constant.STATUS_USE);
        listSaleServices = query.list();

        return listSaleServices;
    }

    /**
     *
     * author tamdt1 date: 02/04/2009 lay danh sach tat ca cac stockType
     *
     */
    private List getListStockTypes() {
        List listStockTypes = new ArrayList();

//        for(int i = 0; i < 5; i++){
//            SaleServices saleServices = new SaleServices();
//            saleServices.setCode("SS00000" + i);
//            saleServices.setName("sale services " + i);
//            saleServices.setNotes("note " + i);
//            saleServices.setStatus(1L);
//
//            listSaleServices.add(saleServices);
//        }

        String strQuery = "from StockType where status = ? order by name";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        listStockTypes = query.list();

        return listStockTypes;
    }

    /**
     *
     * author tamdt1 date: 15/04/2009 lay danh sach tat ca cac telecomServices
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
     * author tamdt1 date: 09/04/2009 lay danh sach tat ca cac stockType cua 1
     * saleservices
     *
     */
    private List<StockType> getListStockTypes(Long saleServicesId) {
        if (saleServicesId == null) {
            saleServicesId = -1L;
        }

        List<StockType> listStockTypes = new ArrayList<StockType>();

        String strQuery = "from SaleServicesModel where status = ? and saleServicesId = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, saleServicesId);
        List<SaleServicesModel> listSaleServicesModels = query.list();
        if (listSaleServicesModels != null) {
            for (int i = 0; i < listSaleServicesModels.size(); i++) {
                SaleServicesModel saleServicesModel = listSaleServicesModels.get(i);
                StockType stockType = getStockTypeById(saleServicesModel.getStockTypeId());
                listStockTypes.add(stockType);
            }
        }

        return listStockTypes;
    }

    /**
     *
     * author : tamdt1 date : 14/03/2009 purpose : lay SaleServices co id =
     * saleServicesId
     *
     */
    private SaleServices getSaleServicesById(Long saleServicesId) {
        SaleServices saleServices = null;
        if (saleServicesId == null) {
            saleServicesId = -1L;
        }
        String strQuery = "from SaleServices where saleServicesId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesId);
        List listSaleServices = query.list();
        if (listSaleServices != null && listSaleServices.size() > 0) {
            saleServices = (SaleServices) listSaleServices.get(0);
        }

        return saleServices;
    }

    /**
     *
     * author tamdt1 date: 15/03/2009 lay SaleServicesModel co id =
     * saleServicesModelId
     *
     */
    private SaleServicesModel getSaleServicesModelById(Long saleServicesModelId) {
        SaleServicesModel saleServicesModel = null;
        if (saleServicesModelId == null) {
            saleServicesModelId = -1L;
        }
        String strQuery = "from SaleServicesModel where saleServicesModelId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesModelId);
        query.setParameter(1, Constant.STATUS_USE);
        List listSaleServicesModels = query.list();
        if (listSaleServicesModels != null && listSaleServicesModels.size() > 0) {
            saleServicesModel = (SaleServicesModel) listSaleServicesModels.get(0);
        }

        return saleServicesModel;
    }

    /**
     *
     * author tamdt1 date: 15/03/2009 lay SaleServicesPrice co id =
     * saleServicesPriceId
     *
     */
    private SaleServicesPrice getSaleServicesPriceById(Long saleServicesPriceId) {
        SaleServicesPrice saleServicesPrice = null;
        if (saleServicesPriceId == null) {
            return saleServicesPrice;
        }
        String strQuery = "from SaleServicesPrice where saleServicesPriceId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesPriceId);
        List<SaleServicesPrice> listSaleServicesPrices = query.list();
        if (listSaleServicesPrices != null && listSaleServicesPrices.size() > 0) {
            saleServicesPrice = listSaleServicesPrices.get(0);
        }

        return saleServicesPrice;
    }

    //TrongLV
    //Lay gia goi dich vu
    public List getSaleServicesPriceBySaleServicesId(Long saleServicesId, String pricePolicy) {
        try {
            if (saleServicesId == null) {
                return null;
            }
            if (pricePolicy == null || pricePolicy.trim().equals("")) {
                return null;
            }
            String strQuery = " select sale_Services_Price_Id as priceid, price from Sale_Services_Price where sale_Services_Id = ? and status = ? and price_policy = ? "
                    + " AND TO_DATE( ? ,'dd/mm/yyyy') >= sta_date AND (end_date is null OR to_date( ? ,'dd/mm/yyyy') <= end_date) ";
            Query query = getSession().createSQLQuery(strQuery);
            query.setParameter(0, saleServicesId);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, pricePolicy.trim());

            Date currDate = new Date();
            String date = DateTimeUtils.convertDateTimeToString(currDate);
            query.setParameter(3, date);
            query.setParameter(4, date);

            List saleServicesPrice = query.list();
            return saleServicesPrice;

        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay SaleServicesDetail co id =
     * saleServicesDetailId
     *
     */
    private SaleServicesDetail getSaleServicesDetailById(Long saleServicesDetailId) {
        SaleServicesDetail saleServicesDetail = null;
        if (saleServicesDetailId == null) {
            saleServicesDetailId = -1L;
        }
        String strQuery = "from SaleServicesDetail where id = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesDetailId);
        List listSaleServicesDetails = query.list();
        if (listSaleServicesDetails != null && listSaleServicesDetails.size() > 0) {
            saleServicesDetail = (SaleServicesDetail) listSaleServicesDetails.get(0);
        }

        return saleServicesDetail;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay StockModel co id = stockModelId
     *
     */
    private StockModel getStockModelById(Long stockModelId) {
        StockModel stockModel = null;
        if (stockModelId == null) {
            stockModelId = -1L;
        }
        String strQuery = "from StockModel where stockModelId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModelId);
        query.setParameter(1, Constant.STATUS_USE);
        List listStockModels = query.list();
        if (listStockModels != null && listStockModels.size() > 0) {
            stockModel = (StockModel) listStockModels.get(0);
        }

        return stockModel;
    }

    /**
     *
     * author tamdt1 date: 02/04/2009 lay StockTypeProfile co id = profileId
     *
     */
    private StockTypeProfile getStockTypeProfileById(Long profileId) {
        StockTypeProfile stockTypeProfile = null;

        if (profileId == null) {
            profileId = -1L;
        }

        String strQuery = "from StockTypeProfile where profileId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, profileId);
        query.setParameter(1, Constant.STATUS_USE);
        List<StockTypeProfile> listStockTypeProfiles = query.list();
        if (listStockTypeProfiles != null && listStockTypeProfiles.size() > 0) {
            stockTypeProfile = listStockTypeProfiles.get(0);
        }

        return stockTypeProfile;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay price co id = priceId
     *
     */
    private Price getPriceById(Long priceId) {
        Price price = null;
        if (priceId == null) {
            priceId = -1L;
        }
        String strQuery = "from Price where priceId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, priceId);
        query.setParameter(1, Constant.STATUS_USE);
        List listPrices = query.list();
        if (listPrices != null && listPrices.size() > 0) {
            price = (Price) listPrices.get(0);
        }

        return price;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay danh sach tat ca cac price cua 1 stock
     * model
     *
     */
    private List getListPriceForStockModel(Long stockModelId) {
        List listPrices = new ArrayList();
        if (stockModelId == null) {
            stockModelId = -1L;
        }

        String strQuery = "select priceId, price || ' ' || description as desciption from Price where stockModelId = ? and status = ? and type = ? "
                + "and sta_date <= trunc(?) and (end_date is null or end_date >= trunc(?))";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModelId);
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, Constant.PRICE_TYPE_CODE_OF_SALE_SERVICES);
        Date today = new Date();
        query.setParameter(3, today);
        query.setParameter(4, today);
        listPrices = query.list();

        return listPrices;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay danh sach tat ca cac price cua 1 stock
     * model
     *
     */
    private List getListPrice(Long stockModelId) {
        List listPrices = new ArrayList();
        if (stockModelId == null) {
            stockModelId = -1L;
        }

        String strQuery = "select new Price(priceId, price || ' ' || description) "
                + "from Price where stockModelId = ? and status = ? and type = ? "
                + "and sta_date <= trunc(?) and (end_date is null or end_date >= trunc(?))";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModelId);
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, Constant.PRICE_TYPE_CODE_OF_SALE_SERVICES);
        Date today = new Date();
        query.setParameter(3, today);
        query.setParameter(4, today);
        listPrices = query.list();

        return listPrices;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay danh sach tat ca cac price cua 1
     * saleServices
     *
     */
    private List getListSaleServicesPrice(Long saleServicesId) {
        List listPrices = new ArrayList();
        if (saleServicesId == null) {
            return listPrices;
        }

        String strQuery = "select new com.viettel.im.database.BO.SaleServicesPrice("
                + "a.saleServicesPriceId, a.saleServicesId, a.price, a.status, a.description, a.staDate, a.endDate, a.vat, a.createDate, a.username, a.pricePolicy, b.name as pricePolicyName) "
                + "from SaleServicesPrice a, AppParams b "
                + "where a.saleServicesId = ? and a.pricePolicy = b.id.code and b.id.type = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesId);
        query.setParameter(1, "PRICE_POLICY");
        listPrices = query.list();

        return listPrices;
    }

    /**
     *
     * author tamdt1 date: 15/03/2009 lay danh sach tat ca cac saleServicesModel
     *
     */
    private List getListSaleServicesModel(Long saleServicesId) {
        List listSaleServicesModels = new ArrayList();
        if (saleServicesId == null) {
            saleServicesId = -1L;
        }
        String strQuery = "select new SaleServicesModel(a.saleServicesModelId, a.saleServicesId, a.stockTypeId, a.status, a.notes, a.updateStock, a.checkStaffStock, a.checkShopStock, b.name) "
                + "from SaleServicesModel a, StockType b "
                + "where a.status = ? and a.saleServicesId = ? and a.stockTypeId = b.stockTypeId "
                + "order by nlssort(b.name,'nls_sort=Vietnamese') asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.STATUS_USE);
        query.setParameter(1, saleServicesId);

        listSaleServicesModels = query.list();

//        List tmpListSaleServicesModels = query.list();
//        if (tmpListSaleServicesModels != null) {
//            for (int i = 0; i < tmpListSaleServicesModels.size(); i++) {
//                SaleServicesModel saleServicesModel = (SaleServicesModel) tmpListSaleServicesModels.get(i);
//                StockType stockType = getStockTypeById(saleServicesModel.getStockTypeId());
//                if (stockType != null) {
//                    saleServicesModel.setStockTypeName(stockType.getName());
//                }
//                listSaleServicesModels.add(saleServicesModel);
//            }
//        }

        return listSaleServicesModels;
    }

    /**
     *
     * author tamdt1 date: 16/03/2009 lay danh sach tat ca cac
     * saleServicesDetail cua saleServicesModelId
     *
     */
    private List getListSaleServicesDetail(Long saleServicesModelId) {
        List listSaleServicesDetails = new ArrayList();
        if (saleServicesModelId == null) {
            saleServicesModelId = -1L;
        }

//        String strQuery = "from SaleServicesDetail where saleServicesModelId = ? and status = ?";
        String strQuery =
                "select new com.viettel.im.database.BO.SaleServicesDetail(a.id, a.stockModelId, a.saleServicesModelId, a.priceId, a.status, a.notes, b.name, c.price, c.description) "
                + "from SaleServicesDetail a, StockModel b, Price c "
                + "where a.saleServicesModelId = ? and a.status = ? and a.stockModelId = b.stockModelId and a.priceId = c.priceId";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesModelId);
        query.setParameter(1, Constant.STATUS_USE);

        listSaleServicesDetails = query.list();
//        List tmpListSaleServicesDetails = query.list();
//        
//        if (tmpListSaleServicesDetails != null) {
//            for (int i = 0; i < tmpListSaleServicesDetails.size(); i++) {
//                SaleServicesDetail saleServicesDetail = (SaleServicesDetail) tmpListSaleServicesDetails.get(i);
//                //thiet lap stockModelName cho saleServicesDetail
//                StockModel stockModel = getStockModelById(saleServicesDetail.getStockModelId());
//                if(stockModel !=  null) saleServicesDetail.setStockModelName(stockModel.getName());
//                //thiet lap price cho saleServicesDetail
//                Price price = getPriceId(saleServicesDetail.getPriceId());
//                if(price != null) saleServicesDetail.setPrice(price.getPrice());
//
//                listSaleServicesDetails.add(saleServicesDetail);
//            }
//        }

        return listSaleServicesDetails;
    }

    /**
     *
     * author tamdt1 date: 06/04/2009 tra du lieu cho cay saleServices
     *
     */
    public String getSaleServicesTree() throws Exception {
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
                List listSaleServices = getListSaleServices(Long.valueOf(node));
                Iterator iterSaleServices = listSaleServices.iterator();
                while (iterSaleServices.hasNext()) {
                    SaleServices saleServices = (SaleServices) iterSaleServices.next();
                    String nodeId = "2_" + saleServices.getSaleServicesId().toString(); //them prefix 2_ de xac dinh la node level
                    getListItems().add(new Node(saleServices.getName(), "false", nodeId, httpServletRequest.getContextPath() + "/saleServicesAction!displaySaleServices.do?selectedSaleServicesId=" + saleServices.getSaleServicesId().toString()));
                }
            }

//            else if(node.indexOf("2_") == 0) {
//                //neu la lan lay cay du lieu muc 2, tra ve danh sach cac saleServicesModel tuong ung voi saleServicesId
//                node = node.substring(2); //bo phan prefix danh dau level cua cay (2_)
//                List listSaleServicesModel = hbnSession.createCriteria(SaleServicesModel.class).
//                        add(Restrictions.eq("saleServicesId", Long.parseLong(node))).
//                        add(Restrictions.eq("status", 1L)).
//                        addOrder(Order.asc("stockTypeId")).
//                        list();
//
//                Iterator iterSaleServicesModel = listSaleServicesModel.iterator();
//                while (iterSaleServicesModel.hasNext()) {
//                    SaleServicesModel saleServicesModel = (SaleServicesModel) iterSaleServicesModel.next();
//                    //ung voi moi saleServicesModel, tim stockType tuong ung
//                    List listStockTypes = hbnSession.createCriteria(StockType.class).
//                        add(Restrictions.eq("stockTypeId", saleServicesModel.getStockTypeId())).
//                        add(Restrictions.eq("status", 1L)).
//                        list();
//                    StockType stockType = (StockType)listStockTypes.get(0);
//                    String nodeId = "3_" + node + "_" + saleServicesModel.getSaleServicesModelId().toString(); //them prefix 3_ de xac dinh node level
//                    getListItems().add(new Node(stockType.getName(), "true", nodeId, "#"));
//                }
//            } else if(node.indexOf("3_") == 0) {
//                //neu la lan lay cay du lieu muc 3, tra ve danh sach cac saleServiceDetail tuong ung voi saleServicesModel
//                node = node.substring(2); //bo phan prefix danh dau level cua cay (3_)
//                String saleServicesModelId = node.substring(node.lastIndexOf("_") + 1); //lay id, vi cac id phan cach nhau boi dau "_"
//                List listSaleServicesDetail = hbnSession.createCriteria(SaleServicesDetail.class).
//                        add(Restrictions.eq("saleServicesModelId", Long.parseLong(saleServicesModelId))).
//                        add(Restrictions.eq("status", 1L)).
//                        addOrder(Order.asc("id")).
//                        list();
//
//                Iterator iterSaleServicesDetail = listSaleServicesDetail.iterator();
//                while (iterSaleServicesDetail.hasNext()) {
//                    SaleServicesDetail saleServicesDetail = (SaleServicesDetail) iterSaleServicesDetail.next();
//                    //ung voi moi saleServicesDetail, tim stockModel tuong ung
//                    List listStockModels = hbnSession.createCriteria(StockModel.class).
//                        add(Restrictions.eq("stockModelId", saleServicesDetail.getStockModelId())).
//                        add(Restrictions.eq("status", 1L)).
//                        list();
//                    StockModel stockModel = (StockModel)listStockModels.get(0);
//                    String nodeId = "4_" + node + "_" + saleServicesDetail.getId().toString(); //them prefix L_ de xac dinh node level (nut la')
//                    getListItems().add(new Node(stockModel.getName(), "false", nodeId, "#"));
//                }
//            }

            return GET_SALE_SERVICES_TREE;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    /**
//     *
//     * author tamdt1
//     * date: 15/04/2009
//     * tra du lieu cho combobox
//     *
//     */
//    private Map listItemsCombo = new HashMap();
//
//    public Map getListItemsCombo() {
//        return listItemsCombo;
//    }
//
//    public void setListItemsCombo(Map listItemsCombo) {
//        this.listItemsCombo = listItemsCombo;
//    }
//
//    public String getComboboxSource() throws Exception {
//        try {
//            HttpServletRequest httpServletRequest = getRequest();
//
//            List lstRes = null;
//            //Lay danh sach mat hang tu loai mat hang
//            String strStockTypeId = httpServletRequest.getParameter("stockTypeId");
//            if (strStockTypeId != null) {
//                Long stockTypeId = Long.parseLong(strStockTypeId);
//                lstRes = getListStockModel(stockTypeId);
//                listItemsCombo.put("", "--Chọn mặt hàng--");
//                for (Object object : lstRes) {
//                    StockModel sm = (StockModel) object;
//                    listItemsCombo.put(sm.getStockModelId(), sm.getName());
//                }
//            }
//
//            //Lay danh sach gia tu mat hang
//            String strStockModelId = httpServletRequest.getParameter("stockModelId");
//            if (strStockModelId != null) {
//                Long stockModelId = Long.parseLong(strStockModelId);
//                lstRes = getListPriceForStockModel(stockModelId);
//                listItemsCombo.put("", "--Chọn giá--");
//                for (Object object : lstRes) {
//                    Price price = (Price) object;
//                    String priceDescription = price.getDescription() != null ? (" - " + price.getDescription()) : "";
//                    listItemsCombo.put(price.getPriceId(), price.getPrice() + priceDescription);
//                }
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//        return "getComboboxSource";
//    }
    /**
     *
     * author tamdt1 date: 04/05/2009 kiem tra tinh hop le cua 1 saleservices
     * truoc khi them vao DB
     *
     */
    private boolean checkValidSaleServicesToAdd() {
        HttpServletRequest req = getRequest();

        String saleServicesCode = this.saleServicesForm.getCode();
        String saleServicesName = this.saleServicesForm.getName();
        Long telecomServicesId = this.saleServicesForm.getTelecomServicesId();

        if (saleServicesCode == null || saleServicesCode.trim().equals("")
                || saleServicesName == null || saleServicesName.trim().equals("")
                || telecomServicesId == null) {
            req.setAttribute(REQUEST_MESSAGE, "saleservices.error.requiredFieldsEmpty");
            return false;
        }
        //TruongNQ5 20140725 Validate chon 1 trong 2 chi tieu
        Long rvnServiceId = this.saleServicesForm.getRvnServiceId();
        Long rvnServiceQualityId = this.saleServicesForm.getRvnServiceQualityId();
        if (rvnServiceId == null && rvnServiceQualityId == null) {
            req.setAttribute(REQUEST_MESSAGE, "err.rvn.service");
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
                req.setAttribute(REQUEST_MESSAGE, "saleservices.error.codeContainsInvalidCharacters");
                return false;
            }
        }

        //kiem tra tinh trung lap cua ma dich vu
        String strQuery = "from SaleServices where code = ? and status = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesCode.trim());
        query.setParameter(1, Constant.STATUS_USE);
        List listSaleServices = query.list();
        if (listSaleServices != null && listSaleServices.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "saleservices.error.duplicateCode");
            return false;
        }

        //
        this.saleServicesForm.setCode(saleServicesCode.trim());
        this.saleServicesForm.setName(saleServicesName.trim());

        return true;
    }

    /**
     *
     * author tamdt1 date: 04/05/2009 kiem tra tinh hop le cua 1 saleservices
     * truoc khi cap nhat vao DB
     *
     */
    private boolean checkValidSaleServicesToEdit() {
        HttpServletRequest req = getRequest();

        String saleServicesCode = this.saleServicesForm.getCode();
        String saleServicesName = this.saleServicesForm.getName();
        Long telecomServicesId = this.saleServicesForm.getTelecomServicesId();

        if (saleServicesCode == null || saleServicesCode.trim().equals("")
                || saleServicesName == null || saleServicesName.trim().equals("")
                || telecomServicesId == null) {
            req.setAttribute(REQUEST_MESSAGE, "saleservices.error.requiredFieldsEmpty");
            return false;
        }
        //TruongNQ5 20140725 Validate chon 1 trong 2 chi tieu
        Long rvnServiceId = this.saleServicesForm.getRvnServiceId();
        Long rvnServiceQualityId = this.saleServicesForm.getRvnServiceQualityId();
        if (rvnServiceId == null && rvnServiceQualityId == null) {
            req.setAttribute(REQUEST_MESSAGE, "err.rvn.service");
            return false;
        }

        //kiem tra tinh trung lap cua ma dich vu va ten dich vu
        String strQuery = "from SaleServices where code = ? and status = ? and saleServicesId <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesCode.trim());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, this.saleServicesForm.getSaleServicesId());
        List listSaleServices = query.list();
        if (listSaleServices != null && listSaleServices.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "saleservices.error.duplicateCode");
            return false;
        }

        //
        this.saleServicesForm.setCode(saleServicesCode.trim());
        this.saleServicesForm.setName(saleServicesName.trim());

        return true;
    }

    /**
     *
     * author tamdt1 date: 04/05/2009 kiem tra tinh hop le cua 1 saleservices
     * truoc khi xoa
     *
     */
    private boolean checkValidSaleServicesToDel() {
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
     * author tamdt1 date: 29/07/2009 kiem tra tinh hop le cua 1
     * saleservicesmodel truoc khi xoa
     *
     */
    private boolean checkValidSaleServicesModelToDel(Long saleServicesModelId) {
        HttpServletRequest req = getRequest();

        if (saleServicesModelId == null) {
            return false;
        }

        //loai mat hang cua dich vu dang duoc su dung
        String strQuery = "select count(*) "
                + "from SaleServicesDetail "
                + "where saleServicesModelId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, saleServicesModelId);
        Long count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "saleservicesmodel.error.delete");
            return false;
        }

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

        if (this.saleServicesPriceForm.getCurrency() == null || this.saleServicesPriceForm.getCurrency().trim().equals("")) {
            this.saleServicesPriceForm.setCurrency(Constant.CURRENCY_DEFAULT);
        }

//        Long price = this.saleServicesPriceForm.getPrice();
        String strPrice = this.saleServicesPriceForm.getPrice();
        if (strPrice == null || strPrice.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "Error. Price is invalid!");
            return false;
        }
        strPrice = strPrice.trim();
        this.saleServicesPriceForm.setPrice(strPrice);
        Double price = null;
        try {
            price = NumberUtils.convertStringtoNumber(strPrice);
        } catch (Exception ex) {
            ex.printStackTrace();
            price = null;
        }

        Long vat = this.saleServicesPriceForm.getVat();
        String strStaDate = this.saleServicesPriceForm.getStaDate();
        String strEndDate = this.saleServicesPriceForm.getEndDate();
        String pricePolicy = this.saleServicesPriceForm.getPricePolicy();
        Long status = this.saleServicesPriceForm.getStatus();

        if (price == null || vat == null
                || pricePolicy == null || pricePolicy.trim().equals("")
                || strStaDate == null || strStaDate.trim().equals("") || status == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0.0) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidDate");
                return false;
            }
        }

        if (this.saleServicesPriceForm.getStatus().equals(Constant.STATUS_USE)) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot dich vu, doi voi 1 chinh sach gia chi co 1 gia active vao 1 thoi diem
            try {
                List<SaleServicesPrice> listPrices = new ArrayList<SaleServicesPrice>();

                //kiem tra dieu kien doi voi staDate
                String strQuery = "from SaleServicesPrice "
                        + "where saleServicesId = ? and pricePolicy = ? "
                        + "and status = ? and staDate <= ? and (endDate >= ? or endDate is null) "
                        + "and saleServicesPriceId <> ?";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, this.saleServicesPriceForm.getSaleServicesId());
                query.setParameter(1, this.saleServicesPriceForm.getPricePolicy());
                query.setParameter(2, Constant.STATUS_USE);
                query.setParameter(3, staDate);
                query.setParameter(4, staDate);
                query.setParameter(5, this.saleServicesPriceForm.getSaleServicesPriceId());
                listPrices = query.list();

                if (listPrices != null && listPrices.size() > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.duplicateTime");
                    return false;
                }

                //kiem tra dieu kien doi voi endDate
                if (endDate != null) {
                    query.setParameter(3, endDate);
                    query.setParameter(4, endDate);
                    listPrices = query.list();

                    if (listPrices != null && listPrices.size() > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.duplicateTime");
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
                        req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.duplicateTime");
                        return false;
                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 17/04/2009 kiem tra tinh hop le cua 1 price truoc khi
     * them gia moi cho dich vu
     *
     */
    private boolean checkValidPriceToAdd() {
        HttpServletRequest req = getRequest();

        if (this.saleServicesPriceForm.getCurrency() == null || this.saleServicesPriceForm.getCurrency().trim().equals("")) {
            this.saleServicesPriceForm.setCurrency(Constant.CURRENCY_DEFAULT);
        }

        String strPrice = this.saleServicesPriceForm.getPrice();
        if (strPrice == null || strPrice.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "Error. Price is invalid!");
            return false;
        }
        strPrice = strPrice.trim();
        this.saleServicesPriceForm.setPrice(strPrice);
        Double price = null;
        try {
            price = NumberUtils.convertStringtoNumber(strPrice);
        } catch (Exception ex) {
            ex.printStackTrace();
            price = null;
        }

        Long vat = this.saleServicesPriceForm.getVat();
        String strStaDate = this.saleServicesPriceForm.getStaDate();
        String strEndDate = this.saleServicesPriceForm.getEndDate();
        String pricePolicy = this.saleServicesPriceForm.getPricePolicy();
        Long status = this.saleServicesPriceForm.getStatus();

        if (price == null || vat == null
                || pricePolicy == null || pricePolicy.trim().equals("")
                || strStaDate == null || strStaDate.trim().equals("") || status == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0.0) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.invalidDate");
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
                    req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.duplicateTime");
                    return false;
                }

                //kiem tra dieu kien doi voi endDate
                if (endDate != null) {
                    query.setParameter(3, endDate);
                    query.setParameter(4, endDate);
                    listPrices = query.list();

                    if (listPrices != null && listPrices.size() > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.duplicateTime");
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
                        req.setAttribute(REQUEST_MESSAGE, "saleservicesprice.error.duplicateTime");
                        return false;
                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }


        return true;
    }

    /**
     *
     * author tamdt1 date: 08/06/2009 kiem tra tinh hop le cua 1
     * saleServicesModel truoc khi them moi
     *
     */
    private boolean checkValidSaleServicesModelToAdd() {
        HttpServletRequest req = getRequest();

        Long stockTypeId = this.saleServicesModelForm.getStockTypeId();
        Long updateStock = this.saleServicesModelForm.getUpdateStock();

        if (stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || updateStock == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "saleservicesmodel.error.requiredFieldsEmpty");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 08/06/2009 kiem tra tinh hop le cua 1
     * saleServicesModel truoc khi sua thong tin
     *
     */
    private boolean checkValidSaleServicesModelToEdit() {
        HttpServletRequest req = getRequest();

        Long stockTypeId = this.saleServicesModelForm.getStockTypeId();
        Long updateStock = this.saleServicesModelForm.getUpdateStock();

        if (stockTypeId == null || stockTypeId.compareTo(0L) <= 0
                || updateStock == null) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "saleservicesmodel.error.requiredFieldsEmpty");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1 date: 21/04/2009 lay Price co id = pricelId trong bien
     * session
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
     * author tamdt1 date: 14/05/2009 phuc vu muc dich phan tran doi voi
     * saleServicesPrice
     *
     */
    public String pageNagivatorPrice() throws Exception {
        log.info("Begin method pageNagivatorPrice of SaleServicesDAO ...");
        pageForward = LIST_SALE_SERVICES_PRICES;
        log.info("end method pageNagivatorPrice of SaleServicesDAO");

        return pageForward;
    }
    /**
     *
     * author tamdt1 date: 11/05/2009 lay du lieu cho autocomplete ma cua hang
     *
     */
    private Map hashMapShopCode = new HashMap();

    public Map getHashMapShopCode() {
        return hashMapShopCode;
    }

    public void setHashMapShopCode(Map hashMapShopCode) {
        this.hashMapShopCode = hashMapShopCode;
    }

    public String getDataForShopAutocompleter() throws Exception {
        try {
            //go ma kho chuc nang -> danh sach cac kho chuc nang rieng
            HttpServletRequest httpServletRequest = getRequest();
            String shopCode = httpServletRequest.getParameter("saleServicesForm.shopCode");

            if (shopCode != null && !shopCode.trim().equals("")) {
                String strQuery = "from Shop where lower(shopCode) like ? and status = ? and channelTypeId = ?";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, shopCode.trim().toLowerCase() + "%");
                query.setParameter(1, Constant.STATUS_USE);
                query.setParameter(2, Constant.SPECIAL_SHOP_ID);
                query.setMaxResults(8);
                List<Shop> listShop = query.list();
                for (int i = 0; i < listShop.size(); i++) {
                    hashMapShopCode.put(listShop.get(i).getShopId(), listShop.get(i).getShopCode());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return GET_DATA_FOR_SHOP_AUTOCOMPLETER;
    }
    HashMap hashMapShopName = new HashMap();

    public HashMap getHashMapShopName() {
        return hashMapShopName;
    }

    public void setHashMapShopName(HashMap hashMapShopName) {
        this.hashMapShopName = hashMapShopName;
    }

    /**
     *
     * author tamdt1 date: 09/05/2009 cap nhat ten shop
     *
     */
    public String updateShopName() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();

        String strTarget = httpServletRequest.getParameter("target");
        String strShopId = httpServletRequest.getParameter("shopId");
        if (strShopId != null && !strShopId.trim().equals("")) {
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findById(Long.parseLong(strShopId));
            String strShopName = shop != null ? shop.getName() : "";
            this.hashMapShopName.put(strTarget, strShopName);
        }

        return UPDATE_SHOP_NAME;
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

    public void updateRVNService(Long rvnServiceId, String serviceCode, Long serviceIndex) {
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
                        saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_SALE_SERVICE, getText("update.sales.criteria"), "rvn_service_dept_map", serviceIndex, "service_id", oldRvnServiceId, rvnServiceId.toString());
                    }
                } else {
                    sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query queryInsert = getSession().createSQLQuery(sql);
                    queryInsert.setParameter(0, index);
                    queryInsert.setParameter(1, Constant.DEPT_ID_MOV);
                    queryInsert.setParameter(2, rvnServiceId);
                    queryInsert.setParameter(3, serviceCode);
                    queryInsert.setParameter(4, Constant.TYPE_SERVICES);
                    queryInsert.setParameter(5, Constant.STATUS_ACTIVE);
                    queryInsert.executeUpdate();
                    saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_SALE_SERVICE, getText("add.new.sales.criteria"), "rvn_service_dept_map", index, "service_id", "", rvnServiceId.toString());
                }
            } else if (serviceIndex != null && serviceIndex > 0L) {
                String oldRvnServiceId = getRVNServiceDeptMap(serviceIndex).toString();
                sql = "DELETE FROM  " + schemaRvnService + ".rvn_service_dept_map where id = ? ";
                Query queryDelete = getSession().createSQLQuery(sql);
                queryDelete.setParameter(0, serviceIndex);
                queryDelete.executeUpdate();

                saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_SALE_SERVICE, getText("delete.sales.criteria"), "rvn_service_dept_map", serviceIndex, "service_id", oldRvnServiceId, "");
                this.saleServicesForm.setServiceIndex(0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TruongNQ5 20140725 Ham update combobox Chi tieu san luong

    public void updateRVNServiceQuality(Long rvnServiceQualityId, String serviceCode, Long serviceQualityIndex) {
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
                        saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_SALE_SERVICE, getText("update.quality.criteria"), "rvn_service_dept_map", serviceQualityIndex, "service_id", oldRvnServiceQualityId, rvnServiceQualityId.toString());
                    }
                } else {
                    sql = "INSERT INTO  " + schemaRvnService + ".rvn_service_dept_map (id, dept_id, service_id, goods_code, goods_type_id, create_date,status)"
                            + "VALUES(?,?,?,?,?,sysdate,?)";
                    Long index = getSequence(schemaRvnService + ".rvn_service_dept_map_seq");
                    Query queryInsert = getSession().createSQLQuery(sql);
                    queryInsert.setParameter(0, index);
                    queryInsert.setParameter(1, Constant.DEPT_ID_MOV);
                    queryInsert.setParameter(2, rvnServiceQualityId);
                    queryInsert.setParameter(3, serviceCode);
                    queryInsert.setParameter(4, Constant.TYPE_SERVICES);
                    queryInsert.setParameter(5, Constant.STATUS_ACTIVE);
                    queryInsert.executeUpdate();
                    saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_SALE_SERVICE, getText("add.new.quality.criteria"), "rvn_service_dept_map", index, "service_id", "", rvnServiceQualityId.toString());
                }
            } else if (serviceQualityIndex != null && serviceQualityIndex > 0L) {
                String oldRvnServiceQualityId = getRVNServiceDeptMap(serviceQualityIndex).toString();
                sql = "DELETE FROM  " + schemaRvnService + ".rvn_service_dept_map where id = ? ";
                Query queryDelete = getSession().createSQLQuery(sql);
                queryDelete.setParameter(0, serviceQualityIndex);
                queryDelete.executeUpdate();
                saveLogRVNServiceDeptMap(Constant.ACTION_UPDATE_SALE_SERVICE, getText("delete.quality.criteria"), "rvn_service_dept_map", serviceQualityIndex, "service_id", oldRvnServiceQualityId, "");
                this.saleServicesForm.setServiceQualityIndex(0L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Ham load du lieu len 2 combobox chi tieu khi click vao button sua - cap nhat

    public void prepareEditRVNService(String serviceCode) {
        //Chi tieu doanh thu
        List lstParams = new ArrayList();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT   rs.id as rvnServiceId, rsm.id as serviceIndex");
        sql.append(" FROM      " + schemaRvnService + ".rvn_service_dept_map rsm,  " + schemaRvnService + ".rvn_service rs");
        sql.append(" WHERE    rs.id = rsm.service_id");
        sql.append("          AND rs.service_type_id = ? ");
        lstParams.add(Constant.TYPE_REVENUE);
        sql.append("          AND rsm.goods_type_id = ? ");
        lstParams.add(Constant.TYPE_SERVICES);
        sql.append("          AND rsm.goods_code = ? ");
        lstParams.add(serviceCode);
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
            this.saleServicesForm.setServiceIndex(list.get(0).getServiceIndex());
            this.saleServicesForm.setRvnServiceId(list.get(0).getRvnServiceId());
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
        lstParams.add(Constant.TYPE_SERVICES);
        sqlQuality.append("          AND rsm.goods_code = ? ");
        lstParams.add(serviceCode);
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
            this.saleServicesForm.setServiceQualityIndex(listQuality.get(0).getServiceQualityIndex());
            this.saleServicesForm.setRvnServiceQualityId(listQuality.get(0).getRvnServiceQualityId());
        }
    }
    //TruongNQ5 Ham ghi log khi thuc hien thay doi tieu chi cua mat hang

    public void saveLogRVNServiceDeptMap(String actionType, String description, String dbTableName, Long objectId, String dbColumnName, String oldValue, String newValue) {
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

    public String getRVNServiceDeptMap(Long id) {
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
        return "";
    }
    //End TruongNQ5 20140729
}
