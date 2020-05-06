package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.FineItemForm;
import com.viettel.im.client.form.FineItemsPriceForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import com.viettel.im.common.util.QueryCryptUtils;
import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.TelecomService;
import com.viettel.im.database.BO.FineItems;
import com.viettel.im.database.BO.FineItemPrice;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;

/**
 *
 * @author thetm
 *
 */
public class FinePriceDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(FinePriceDAO.class);
    private String pageForward;
    //dinh nghia cac hang so pageForward
    private final String LIST_TELECOM_SERVICE = "listTelecomService";
    private final String LIST_FINE_ITEM = "listFineItem";
    private final String LIST_FINE_ITEM_PRICE = "listFineItemPrice";
    private final String FINE_ITEMS = "fineItems";
    private final String FINE_ITEM_PRICE = "fineItemsPrice";
    private final String FINE_ITEMS_OVERVIEW = "FineItemsOverview";
    private final String FINE_ITEMS_INFO = "fineItemInfo";
    //dinh nghia ten cac bien session hoac bien request
    private final String REQUEST_PRICE_MODE = "priceMode";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_LIST_TELECOM_SERVICE = "listTelecomService";
    private final String REQUEST_CHECK_STATUS = "checkStatus";
    private final String SESSION_LIST_FINE_PRICE = "listFineItemPrices";
    private final String SESSION_LIST_FINE_ITEMS = "listFineItem";
    private final String SESSION_CURR_TELECOM_SERVICE_ID = "currentTelecomServiceId";
    private final String SESSION_CURR_FINE_ITEMS_ID = "currentFineItemlId";
    //khai bao bien form
    private List listItems = new ArrayList();
    private FineItemForm fineItemForm = new FineItemForm();
    private FineItemsPriceForm fineItemsPriceForm = new FineItemsPriceForm();

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public FineItemForm getFineItemForm() {
        return fineItemForm;
    }

    public void setFineItemForm(FineItemForm fineItemForm) {
        this.fineItemForm = fineItemForm;
    }

    public FineItemsPriceForm getFineItemsPriceForm() {
        return fineItemsPriceForm;
    }

    public void setFineItemsPriceForm(FineItemsPriceForm fineItemsPriceForm) {
        this.fineItemsPriceForm = fineItemsPriceForm;
    }

    /**
     *
     * author thetm
     * man hinh dau tien, hien thi tree va danh sach cac telecomService
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of FinePriceDAO ...");

        HttpServletRequest req = getRequest();

//        //lay danh sach cac telecomService
//        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
//        telecomServiceDAO.setSession(this.getSession());
//        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
//        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);
        List<FineItems> listFineItems = getListFineItems();
        req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItems);
        this.fineItemForm.setTelecomServiceId(null);

        pageForward = FINE_ITEMS_OVERVIEW;

        log.info("End method preparePage of FinePriceDAO");

        return pageForward;
    }

    public String listTelecomService() throws Exception {
        log.info("Begin method preparePage of FinePriceDAO ...");

        HttpServletRequest req = getRequest();

//        //lay danh sach cac telecomService
//        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
//        telecomServiceDAO.setSession(this.getSession());
//        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
//        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);
        List<FineItems> listFineItems = getListFineItems();
        req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItems);
        this.fineItemForm.setTelecomServiceId(null);
        pageForward = LIST_FINE_ITEM;
        log.info("End method preparePage of FinePriceDAO");

        return pageForward;
    }

    private List<FineItems> getListFineItems() {
        try {
            List<FineItems> listFineItems = new ArrayList();
            String strQuery = "from FineItems where 1 = 1 order by nlssort(fineItemsName,'nls_sort=Vietnamese') asc";
            Query query = getSession().createQuery(strQuery);
            listFineItems = query.list();

            return listFineItems;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * author thetm
     * man hinh hien thi danh sach cac loai dich vu
     *
     */
    public String getTelecomServiceTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();
            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");

            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac laoi dich vu
                List listTelecomService = hbnSession.createCriteria(TelecomService.class).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("telServiceName")).
                        list();
                Iterator iterTelecomService = listTelecomService.iterator();
                while (iterTelecomService.hasNext()) {
                    TelecomService telecomService = (TelecomService) iterTelecomService.next();
                    String nodeId = "1_" + telecomService.getTelecomServiceId().toString(); //them prefix 2_ de xac dinh la node level
                    String doString = httpServletRequest.getContextPath() + "/finePriceAction!listFineItem.do?selectedTelecomServiceId=" + telecomService.getTelecomServiceId().toString();
                    getListItems().add(new Node(telecomService.getTelServiceName(), "true", nodeId, doString));
                }
            } else if (node.indexOf("1_") == 0) {
                //neu la cau du lieu muc 1, tra ve danh sach cac fineitem tuong ung voi cac dich vu
                node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                List listFineItem = hbnSession.createCriteria(FineItems.class).
                        add(Restrictions.eq("telecomServiceId", Long.parseLong(node))).
                        //                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("fineItemsName")).
                        list();
                Iterator iterFineItem = listFineItem.iterator();
                while (iterFineItem.hasNext()) {
                    FineItems fineItem = (FineItems) iterFineItem.next();
                    String nodeId = "2_" + fineItem.getFineItemsId().toString(); //them prefix 2_ de xac dinh la node level
                    String doString = httpServletRequest.getContextPath() + "/finePriceAction!displayFineItems.do?selectedFineItemId=" + fineItem.getFineItemsId().toString();
                    getListItems().add(new Node(fineItem.getFineItemsName(), "false", nodeId, doString));
                }
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * author thetm
     * man hinh hien thi danh sach ly do phat
     *
     */
    public String listFineItem() throws Exception {
        log.info("Begin method listStockModel of GoodsDefDAO ...");

        HttpServletRequest req = getRequest();

        //lay telecomserviceId can hien thi danh sach fineitem
        String strSelectedTelecomServiceId = req.getParameter("selectedTelecomServiceId");
        Long telecomServiceId = 0L;
        if (strSelectedTelecomServiceId != null) {
            try {
                telecomServiceId = new Long(strSelectedTelecomServiceId);
            } catch (NumberFormatException ex) {
                telecomServiceId = -1L;
            }
        } else {
            telecomServiceId = (Long) req.getSession().getAttribute(SESSION_CURR_TELECOM_SERVICE_ID);
        }

        //lay thong tin ve telecomService
        TelecomService telecomService = getTelecomServiceById(telecomServiceId);
        if (telecomService != null) {

            req.getSession().setAttribute(SESSION_CURR_TELECOM_SERVICE_ID, telecomServiceId);

            this.fineItemForm.setTelecomServiceId(telecomServiceId);
//            this.fineItemForm.setTelecomServiceName(telecomService.getTelServiceName());
            //lay danh sach tat ca cac fineItem cua telecomserviceId
            List<FineItems> listFineItem = getListFineItems(telecomServiceId);
            req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItem);
        }

        pageForward = LIST_FINE_ITEM;

        log.info("End method listStockModel of GoodsDefDAO ...");

        return pageForward;
    }

    public String displayFineItems() throws Exception {
        log.info("Begin method displayStockModel of GoodsDAO ...");

        HttpServletRequest req = getRequest();
        Long fineItemId = 0L;
        String strSelectedFineItemId = req.getParameter("selectedFineItemId");
        if (req.getSession().getAttribute("current_fineItemId") != null) {

            fineItemId = Long.parseLong((String) req.getSession().getAttribute("current_fineItemId"));
        }
        if (strSelectedFineItemId != null) {
            try {
                fineItemId = Long.parseLong(strSelectedFineItemId);
            } catch (NumberFormatException ex) {
                fineItemId = -1L;
            }
        } else {
//            fineItemId = (Long) req.getSession().getAttribute(SESSION_CURR_FINE_ITEMS_ID);
        }
        if (fineItemId != null) {
            String currentFineItemId = fineItemId.toString();
            req.getSession().setAttribute("current_fineItemId", currentFineItemId);
        } else {
            String currentFineItemId = null;
            req.getSession().setAttribute("current_fineItemId", currentFineItemId);
        }
        FineItems fineItem = getFineItemById(fineItemId);
        if (fineItem != null) {

            req.getSession().setAttribute(SESSION_CURR_TELECOM_SERVICE_ID, fineItem.getTelecomServiceId());
            req.getSession().setAttribute(SESSION_CURR_FINE_ITEMS_ID, fineItemId);
            fineItemForm.copyDataFromBO(fineItem);
//            TelecomService telecomService = getTelecomServiceById(fineItem.getTelecomServiceId());
//            if (telecomService != null) {
//                fineItemForm.setTelecomServiceName(telecomService.getTelServiceName());
//            }
            fineItemForm.setTelecomServiceId(fineItem.getTelecomServiceId());

            //lay danh sach phi nop phat            
            List<FineItemPrice> listFineItemPrices = getListFineItemPrice(fineItemId);
            req.getSession().setAttribute(SESSION_LIST_FINE_PRICE, listFineItemPrices);

            //xac lap che do hien thi thong tin
            req.setAttribute("fineItemMode", "view");

            pageForward = FINE_ITEMS_INFO;
        } else {
            Long telecomServiceId = fineItemForm.getTelecomServiceId();
            String del_telecomServiceId = (String) req.getSession().getAttribute("del_telecomServiceId");
            if (telecomServiceId != null) {
                List<FineItems> listFineItem = getListFineItems(telecomServiceId);
                req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItem);
            }
            if (del_telecomServiceId != null && telecomServiceId == null) {
                TelecomService telecomService = getTelecomServiceById(Long.parseLong(del_telecomServiceId));
//                fineItemForm.setTelecomServiceName(telecomService.getTelServiceName());
                fineItemForm.setTelecomServiceId(telecomService.getTelecomServiceId());
                List<FineItems> listFineItem = getListFineItems(telecomService.getTelecomServiceId());
                req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItem);
            }
            pageForward = FINE_ITEMS_INFO;
        }
        req.getSession().setAttribute("isEditFinePrice", "0");
        req.getSession().setAttribute(REQUEST_CHECK_STATUS, "0");
        log.info("End method displayStockModel of GoodsDefDAO");
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);

        return pageForward;
    }

    private List<FineItems> getListFineItems(Long telecomServiceId) {
        if (telecomServiceId == null) {
            telecomServiceId = -1L;
        }
        List<FineItems> listFineItems = new ArrayList();
        String strQuery = "from FineItems where telecomServiceId = ? order by nlssort(fineItemsName,'nls_sort=Vietnamese') asc";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, telecomServiceId);
        listFineItems = query.list();

        return listFineItems;
    }

    private TelecomService getTelecomServiceById(Long telecomServiceId) {
        TelecomService telecomService = null;
        if (telecomServiceId == null) {
            telecomServiceId = -1L;
        }
        String strQuery = "from TelecomService where telecomServiceId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, telecomServiceId);
        query.setParameter(1, Constant.STATUS_USE);
        List listTelecomService = query.list();
        if (listTelecomService != null && listTelecomService.size() > 0) {
            telecomService = (TelecomService) listTelecomService.get(0);
        }

        return telecomService;
    }

    private FineItems getFineItemById(Long fineItemId) {
        FineItems fineItem = null;
        if (fineItemId == null) {
            fineItemId = -1L;
        }
        String strQuery = "from FineItems where fineItemsId = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, fineItemId);
//        query.setParameter(1, Constant.STATUS_USE);
        List<FineItems> listFineItem = query.list();
        if (listFineItem != null && listFineItem.size() > 0) {
            fineItem = listFineItem.get(0);
        }

        return fineItem;
    }

    public String prepareAddFineItems() throws Exception {
        log.info("Begin method prepareAddFineItem of FinePriceDAO ...");

        HttpServletRequest req = getRequest();

        //giu lai 2 bien de thiet lap sau khi reset form
        Long telecomServiceId = this.fineItemForm.getTelecomServiceId();
//        String telecomServiceName = this.fineItemForm.getTelecomServiceName();
        //
        this.fineItemForm.resetForm();
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);
        //
        if (telecomServiceId != null) {
            this.fineItemForm.setTelecomServiceId(telecomServiceId);
//            this.fineItemForm.setTelecomServiceName(telecomServiceName);
        }

        req.getSession().setAttribute(SESSION_LIST_FINE_PRICE, new ArrayList<FineItemPrice>());

        //xac lap che do chuan bi them fineItem moi
        req.setAttribute("fineItemMode", "prepareAddOrEdit");

        pageForward = FINE_ITEMS_INFO;

        log.info("End method prepareAddFineItem of FinePriceDAO");

        return pageForward;
    }

    public String prepareEditFineItems() throws Exception {
        log.info("Begin method prepareEditFineItem of FinePriceDAO ...");

        HttpServletRequest req = getRequest();
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);

        Long fineItemId = this.fineItemForm.getFineItemsId();
        FineItems fineItems = getFineItemById(fineItemId);
        if (fineItems != null) {
            this.fineItemForm.copyDataFromBO(fineItems);

            //xac lap che do chuan bi sua thong tin fineItem
            req.setAttribute("fineItemMode", "prepareAddOrEdit");
        }

        pageForward = FINE_ITEMS_INFO;

        log.info("End method prepareEditFineItem of FinePriceDAO");

        return pageForward;
    }

    public String addOrEditFineItems() throws Exception {
        log.info("Begin method addOrEditFineItems of FinePriceDAO ...");

        HttpServletRequest req = getRequest();

        Long fineItemId = this.fineItemForm.getFineItemsId();
        FineItems fineItems = getFineItemById(fineItemId);
        if (fineItems == null) {
            //----------------------------------------------------------------
            //them fineItem moi
            if (!checkValidFineItemToAdd()) {

                pageForward = LIST_FINE_ITEM;

                log.info("End method addOrEditFineItems of FinePriceDAO");

                return pageForward;
            }
            fineItems = new FineItems();
            this.fineItemForm.copyDataToBO(fineItems);
            fineItemId = getSequence("FINE_ITEMS_SEQ");
            fineItems.setFineItemsId(fineItemId);
            getSession().save(fineItems);
            this.fineItemForm.setFineItemsId(fineItemId);
            req.setAttribute(REQUEST_MESSAGE, "fineItem.add.sucess");

            //thiet lap bien session
            req.getSession().setAttribute(SESSION_CURR_FINE_ITEMS_ID, fineItemId);

            //TheTM bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, fineItems));
            saveLog(Constant.ACTION_ADD_FINE_ITEM, "Thêm lý do phạt", lstLogObj,fineItems.getFineItemsId());

        } else {
            //----------------------------------------------------------------
            //sua thong tin fineItem da co
            if (!checkValidFineItemToEdit()) {

                pageForward = LIST_FINE_ITEM;

                log.info("End method addOrEditFineItems of FinePriceDAO");

                return pageForward;
            }
            FineItems oldFineItems = new FineItems();
            BeanUtils.copyProperties(oldFineItems, fineItems);

            this.fineItemForm.copyDataToBO(fineItems);
            getSession().update(fineItems);
            req.setAttribute(REQUEST_MESSAGE, "fineItem.edit.sucess");

            //TheTM bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldFineItems, fineItems));
            saveLog(Constant.ACTION_UPDATE_FINE_ITEM, "Cập nhật lý do phạt", lstLogObj,fineItems.getFineItemsId());

        }
        //xac lap che do hien thi thong tin
        req.setAttribute("fineItemMode", "view");

        List<FineItems> listFineItems = getListFineItems(fineItems.getTelecomServiceId());
        req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItems);
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);

        pageForward = FINE_ITEMS_INFO;

        log.info("End method addOrEditFineItems of FinePriceDAO");

        return pageForward;
    }

    public String delFineItems() throws Exception {
        log.info("Begin method delFineItems of FinePriceDAO ...");

        HttpServletRequest req = getRequest();
        Long fineItemId = fineItemForm.getFineItemsId();
        FineItems fineItems = getFineItemById(fineItemId);
        if (fineItems != null) {
            try {
                // xoa fineItems trong DB
                getSession().delete(fineItems);
                getSession().flush();
                req.getSession().setAttribute("current_fineItemId", "0");
                req.setAttribute("fineItemMode", "prepareAddOrEdit");

                this.fineItemForm.resetForm();
            } catch (Exception ex) {
                ex.printStackTrace();

                req.setAttribute(REQUEST_MESSAGE, "fineItem.error.delete");

                pageForward = FINE_ITEMS_INFO;
                log.info("End method delFineItems of FinePriceDAO");

                return pageForward;
            }



            //lay danh sach tat ca cac fineItem cua telecomServiceId
            List<FineItems> listFineItems = getListFineItems(fineItems.getTelecomServiceId());
            req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItems);
            req.getSession().setAttribute("del_telecomServiceId", fineItems.getTelecomServiceId().toString());
            req.setAttribute(REQUEST_MESSAGE, "fineItem.delete.sucess");
            pageForward = FINE_ITEMS_INFO;

            //TheTM bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(fineItems, null));
            saveLog(Constant.ACTION_DELETE_FINE_ITEM, "Xóa lý do phạt", lstLogObj,fineItems.getFineItemsId());

        } else {
            //neu xoa bi loi, tra ve trang cu
            pageForward = FINE_ITEMS_INFO;
        }

        log.info("End method delFineItems of FinePriceDAO");
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List<TelecomService> listTelecomService = telecomServiceDAO.findTelecomServicesByStatus(Constant.STATUS_USE);
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICE, listTelecomService);

        return pageForward;
    }

    /**
     *
     * author thetm
     * chuan bi form them fineItemPrice moi
     *
     */
//    public String prepareAddFineItemPrice() throws Exception {
//        log.info("Begin method prepareAddFineItemPrice of FinePriceDAO ...");
//
//        HttpServletRequest req = getRequest();
//
//        this.fineItemsPriceForm.resetForm();
//        Long fineItemId = (Long) req.getSession().getAttribute(SESSION_CURR_FINE_ITEMS_ID);
//        this.fineItemsPriceForm.setFineItemId(fineItemId);
//
//        req.setAttribute("isAddMode", true); //thiet lap che do them moi
//
//        pageForward = FINE_ITEM_PRICE;
//
//        log.info("End method prepareAddFineItemPrice of FinePriceDAO");
//
//        return pageForward;
//    }
    /**
     *
     * author thetm
     * chuan bi form sua thong tin fineItemPrice
     *
     */
    public String prepareEditFineItemPrice() throws Exception {
        log.info("Begin method prepareEditFineItemPrice of FinePriceDAO ...");
//        FineItemsPriceForm f=this.fineItemsPriceForm;
        HttpServletRequest req = getRequest();
        String strPriceId = req.getParameter("selectedFineItemPriceId");
        Long fineItemPriceId = -1L;
        if (strPriceId != null) {
            try {
                fineItemPriceId = new Long(strPriceId);
            } catch (NumberFormatException ex) {
                fineItemPriceId = -1L;
            }
        }
        FineItemPrice fineItemPrice = getFineItemPriceById(fineItemPriceId);
        if (fineItemPrice != null) {
            this.fineItemsPriceForm.copyDataFromBO(fineItemPrice);
        } else {
            this.fineItemsPriceForm.resetForm();
        }
        String userName = fineItemPrice.getUserName();
        String creatDate = DateTimeUtils.convertDateToString(fineItemPrice.getCreateDate());
        req.getSession().setAttribute("userName", userName);
        req.getSession().setAttribute("creatDate", creatDate);
        req.getSession().setAttribute("isEditFinePrice", "1");
        if (req.getSession().getAttribute(REQUEST_CHECK_STATUS).toString().equals("1")) {
            this.fineItemsPriceForm.setCheckStatus(Boolean.TRUE);
        }
        pageForward = FINE_ITEM_PRICE;

        log.info("End method prepareEditFineItemPrice of FinePriceDAO");

        return pageForward;
    }

//    /**
//     *
//     * author thetm
//     * them fineItemPrice moi vao DB moi hoac sua thong tin DB da co
//     *
//     */
    public String addOrEditFineItemsPrice() throws Exception {
        log.info("Begin method addOrEditFineItemsPrice of FinePriceDAO ...");

        HttpServletRequest req = getRequest();
        Long fineItemId = (Long) req.getSession().getAttribute(SESSION_CURR_FINE_ITEMS_ID);
        this.fineItemsPriceForm.setFineItemId(fineItemId);

        List<FineItemPrice> listFineItemPrices = (List<FineItemPrice>) req.getSession().getAttribute(SESSION_LIST_FINE_PRICE);
        if (listFineItemPrices == null) {
            listFineItemPrices = new ArrayList<FineItemPrice>();
            req.getSession().setAttribute(SESSION_LIST_FINE_PRICE, listFineItemPrices);
        }

        Long fineItemPriceId = this.fineItemsPriceForm.getFineItemPriceId();
        FineItemPrice fineItemPrice = getFineItemPriceById(fineItemPriceId);
        if (fineItemPrice == null) {

            //truong hop them price moi
            if (!checkValidFineItemPriceToAdd()) {

                pageForward = FINE_ITEM_PRICE;

                log.info("End method prepareAddFineItemPrice of FinePriceDAO");

                return pageForward;
            }
//            req.setAttribute("isAddMode", true); //thiet lap che do them moi
            fineItemPrice = new FineItemPrice();
            this.fineItemsPriceForm.copyDataToBO(fineItemPrice);
            fineItemPriceId = getSequence("FINE_ITEM_PRICE_SEQ");
            fineItemPrice.setFineItemPriceId(fineItemPriceId);
            fineItemPrice.setCreateDate(new Date());
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            fineItemPrice.setUserName(userToken.getLoginName());
            getSession().save(fineItemPrice);
            if (req.getSession().getAttribute(REQUEST_CHECK_STATUS).toString().equals("1")) {
                listFineItemPrices.add(fineItemPrice);
            } else {
                if (fineItemPrice.getStatus().equals(1L)) {
                    listFineItemPrices.add(fineItemPrice);
                }
            }
            this.fineItemsPriceForm.setFineItemPriceId(-1L);
            req.setAttribute(REQUEST_MESSAGE, "finePrice.add.success");

            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(null, fineItemPrice));
            saveLog(Constant.ACTION_ADD_FINE_ITEM_PRICE, "Thêm phí lý do phạt", lstLogObj,fineItemPrice.getFineItemPriceId());

        } else {
            if (!checkValidFineItemPriceToEdit()) {

                pageForward = FINE_ITEM_PRICE;

                log.info("End method prepareAddFineItemPrice of FinePriceDAO");

                return pageForward;
            }
            //truong hop cap nhat thong tin cua price da co
            FineItemPrice oldFineItemPrice = new FineItemPrice();
            BeanUtils.copyProperties(oldFineItemPrice, fineItemPrice);

            this.fineItemsPriceForm.copyDataToBO(fineItemPrice);
            fineItemPrice.setCreateDate(DateTimeUtils.convertStringToDate((String) req.getSession().getAttribute("creatDate")));
            fineItemPrice.setUserName((String) req.getSession().getAttribute("userName"));
            getSession().update(fineItemPrice);

            //Tuannv bo sung ghi log

            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(oldFineItemPrice, fineItemPrice));
            saveLog(Constant.ACTION_UPDATE_FINE_ITEM_PRICE, "Cập nhật phí lý do phạt", lstLogObj,fineItemPrice.getFineItemPriceId());

//            fineItemPrice = getFineItemPriceInListById(listFineItemPrices, fineItemPriceId);
//            this.fineItemsPriceForm.copyDataToBO(fineItemPrice);
//            fineItemPrice.setCreateDate(DateTimeUtils.convertStringToDate((String) req.getSession().getAttribute("creatDate")));
//            fineItemPrice.setUserName((String) req.getSession().getAttribute("userName"));
//            this.fineItemsPriceForm.setFineItemPriceId(-1L);
            if (req.getSession().getAttribute(REQUEST_CHECK_STATUS).toString().equals("1")) {
                String strQuery = "from FineItemPrice where fineItemId = ? order by price ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, fineItemId);
                listFineItemPrices = query.list();
            } else {
                String strQuery = "from FineItemPrice where fineItemId = ? and status = ? order by price ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, fineItemId);
                query.setParameter(1, Constant.STATUS_USE);
                listFineItemPrices = query.list();
            }
            this.fineItemsPriceForm.setFineItemPriceId(-1L);





        }
        req.getSession().setAttribute(SESSION_LIST_FINE_PRICE, listFineItemPrices);
        req.setAttribute(REQUEST_PRICE_MODE, "closePopup");
        req.getSession().setAttribute("isEditFinePrice", "0");
        this.fineItemsPriceForm.resetForm();
        pageForward = FINE_ITEM_PRICE;

        log.info("End method addOrEditFineItemsPrice of FinePriceDAO");

        return pageForward;
    }

//    /**
//     *
//     * author thetm
//     * xoa phi nop phat
//     *
//     */
    public String deleteFineItemPrice() throws Exception {
        log.info("Begin method deleteFineItemPrice of FinePriceDAO ...");

        HttpServletRequest req = getRequest();
        String strPriceId = req.getParameter("selectedFineItemPriceId");
        Long fineItemPriceId = -1L;
        if (strPriceId != null) {
            try {
                fineItemPriceId = new Long(strPriceId);
            } catch (NumberFormatException ex) {
                fineItemPriceId = -1L;
            }
        }

        FineItemPrice fineItemPrice = getFineItemPriceById(fineItemPriceId);
        if (fineItemPrice != null) {
            try {
                //xoa fineItemPrice trong DB
                getSession().delete(fineItemPrice);
                getSession().flush();
            } catch (Exception ex) {
                ex.printStackTrace();

                req.setAttribute(REQUEST_MESSAGE, "finePrice.error.delete");

                pageForward = FINE_ITEM_PRICE;
                log.info("End method delStockModel of FinePriceDAO");

                return pageForward;
            }
            //xoa phan tu o bien sesion
            List<FineItemPrice> listFineItemPrices = (List<FineItemPrice>) req.getSession().getAttribute(SESSION_LIST_FINE_PRICE);
            FineItemPrice tmpPrice = getFineItemPriceInListById(listFineItemPrices, fineItemPriceId);
            if (tmpPrice != null) {
                listFineItemPrices.remove(tmpPrice);
            }
            //Tuannv bo sung ghi log
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(fineItemPrice, null));
            saveLog(Constant.ACTION_DELETE_FINE_ITEM_PRICE, "Xóa phí lý do phạt", lstLogObj,fineItemPrice.getFineItemPriceId());
        }

        pageForward = FINE_ITEM_PRICE;

        log.info("End method deleteFineItemPrice of FinePriceDAO");

        return pageForward;
    }

//    /**
//     *
//     * author thetm
//     * them refresh lai danh sach phi nop phat
//     *
//     */
    public String refreshListFineItemPrice() throws Exception {
        log.info("Begin method refreshListFineItemPrice of FinePriceDAO ...");
        HttpServletRequest req = getRequest();
        Long fineItemId = Long.parseLong(req.getSession().getAttribute(SESSION_CURR_FINE_ITEMS_ID).toString());
        req.getSession().setAttribute(SESSION_LIST_FINE_PRICE, getListFineItemPrice(fineItemId));

        pageForward = LIST_FINE_ITEM_PRICE;

        log.info("End method refreshListFineItemPrice of FinePriceDAO");

        return pageForward;
    }

    /**
     *
     * author thetm
     * lay FineItemPrice co id = fineItemPricelId
     *
     */
    private FineItemPrice getFineItemPriceById(Long fineItemPricelId) {
        FineItemPrice fineItemPrice = null;
        if (fineItemPricelId == null) {
            fineItemPricelId = -1L;
        }
        String strQuery = "from FineItemPrice where fineItemPriceId = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, fineItemPricelId);
        List<FineItemPrice> listFineItemPrice = query.list();
        if (listFineItemPrice != null && listFineItemPrice.size() > 0) {
            fineItemPrice = listFineItemPrice.get(0);
        }

        return fineItemPrice;
    }

//    /**
//     *
//     * author thetm
//     * lay Price co id = pricelId trong bien session
//     *
//     */
    private FineItemPrice getFineItemPriceInListById(List<FineItemPrice> listFineItemPrice, Long fineItemPricelId) {
        FineItemPrice fineItemPrice = null;
        if (listFineItemPrice == null || listFineItemPrice.size() == 0 || fineItemPricelId == null) {
            return fineItemPrice;
        }

        for (int i = 0; i < listFineItemPrice.size(); i++) {
            if (listFineItemPrice.get(i).getFineItemPriceId().equals(fineItemPricelId)) {
                fineItemPrice = listFineItemPrice.get(i);
                break;
            }
        }

        return fineItemPrice;
    }

//    /**
//     *
//     * author thetm
//     * lay listFineItemPrice tu fineItemId
//     *
//     */
    private List<FineItemPrice> getListFineItemPrice(Long fineItemId) throws Exception {
        try {
            if (fineItemId == null) {
                fineItemId = -1L;
            }
            HttpServletRequest req = getRequest();
            List<FineItemPrice> listFineItemPrices = new ArrayList<FineItemPrice>();
            String strQuery = "from FineItemPrice where fineItemId = ? and status = ? order by price ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, fineItemId);
            query.setParameter(1, Constant.STATUS_USE);
            listFineItemPrices = query.list();
            return listFineItemPrices;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

//    /**
//     *
//     * author thetm
//     * kiem tra tinh hop le cua 1 fineItemPrice truoc khi them moi
//     *
//     */
    private boolean checkValidFineItemPriceToAdd() {
        HttpServletRequest req = getRequest();

        Long price = this.fineItemsPriceForm.getPrice();
        Long vat = this.fineItemsPriceForm.getVat();
        String strStaDate = this.fineItemsPriceForm.getStaDate();
        String strEndDate = this.fineItemsPriceForm.getEndDate();

        Long status = this.fineItemsPriceForm.getStatus();

        if (price == null || vat == null || status == null || strStaDate == null || strStaDate.trim().equals("")) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0L) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidVat");
            return false;
        }
        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidDate");
                return false;
            }
        }

        try {
            Long count;

            //kiem tra dieu kien doi voi staDate
            String strQuery = "select count(*) from FineItemPrice where fineItemId = ? " +
                    "and staDate <= ? and (endDate >= ? or endDate is null) and status = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, this.fineItemsPriceForm.getFineItemId());
            query.setParameter(1, staDate);
            query.setParameter(2, staDate);
            query.setParameter(3, Constant.STATUS_USE);
            count = (Long) query.list().get(0);

            if (count != null && count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                return false;
            }

            //kiem tra dieu kien doi voi endDate
            if (endDate != null) {
                query.setParameter(1, endDate);
                query.setParameter(2, endDate);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                    return false;
                }
            } else {
                strQuery = "select count(*) from FineItemPrice where fineItemId = ? and staDate >= ? and status = ? ";
                query = getSession().createQuery(strQuery);
                query.setParameter(0, this.fineItemsPriceForm.getFineItemId());
                query.setParameter(1, staDate);
                query.setParameter(2, Constant.STATUS_USE);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                    return false;
                }
            }
            //Kiem tra dieu kien doi voi stadate va endDate
            if (endDate != null) {
                String strQuery2 = "select count(*) from FineItemPrice where fineItemId = ? " +
                        "and staDate >= ? and endDate <= ? and status = ? ";
                Query query2 = getSession().createQuery(strQuery2);
                query2.setParameter(0, this.fineItemsPriceForm.getFineItemId());
                query2.setParameter(1, staDate);
                query2.setParameter(2, endDate);
                query2.setParameter(3, Constant.STATUS_USE);
                count = (Long) query2.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }
        this.fineItemsPriceForm.setStaDate(strStaDate.trim());
        this.fineItemsPriceForm.setEndDate(strEndDate.trim());
        return true;
    }

//    /**
//     *
//     * kiem tra tinh hop le cua 1 price truoc khi sua thong tin ve gia cua 1 mat hang
//     *
//     */
    private boolean checkValidFineItemPriceToEdit() {
        HttpServletRequest req = getRequest();

        Long price = this.fineItemsPriceForm.getPrice();
        Long vat = this.fineItemsPriceForm.getVat();
        String strStaDate = this.fineItemsPriceForm.getStaDate().toString();
        String strEndDate = this.fineItemsPriceForm.getEndDate().toString();


        Long status = this.fineItemsPriceForm.getStatus();

        if (price == null || vat == null ||
                status == null ||
                strStaDate == null || strStaDate.trim().equals("")) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0L) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "finePrice.error.invalidDate");
                return false;
            }
        }

        try {
            Long count;

            //kiem tra dieu kien doi voi staDate
            String strQuery = "select count(*) from FineItemPrice where fineItemId = ? " +
                    "and fineItemPriceId <> ? and staDate <= ? and (endDate >= ? or endDate is null) and status = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, this.fineItemsPriceForm.getFineItemId());
            query.setParameter(1, this.fineItemsPriceForm.getFineItemPriceId());
            query.setParameter(2, staDate);
            query.setParameter(3, staDate);
            query.setParameter(4, Constant.STATUS_USE);
            count = (Long) query.list().get(0);

            if (count != null && count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                return false;
            }

            //kiem tra dieu kien doi voi endDate
            if (endDate != null) {
                query.setParameter(2, endDate);
                query.setParameter(3, endDate);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                    return false;
                }
            } else {
                strQuery = "select count(*) from FineItemPrice where fineItemId = ? and fineItemPriceId <> ? and staDate >= ? and status = ? ";
                query = getSession().createQuery(strQuery);
                query.setParameter(0, this.fineItemsPriceForm.getFineItemId());
                query.setParameter(1, this.fineItemsPriceForm.getFineItemPriceId());
                query.setParameter(2, staDate);
                query.setParameter(3, Constant.STATUS_USE);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                    return false;
                }
            }
            //Kiem tra dieu kien doi voi stadate va endDate
            if (endDate != null) {
                String strQuery2 = "select count(*) from FineItemPrice where fineItemId = ? " +
                        "and fineItemPriceId <> ? and staDate >= ? and endDate <= ? and status = ? ";
                Query query2 = getSession().createQuery(strQuery2);
                query2.setParameter(0, this.fineItemsPriceForm.getFineItemId());
                query2.setParameter(1, this.fineItemsPriceForm.getFineItemPriceId());
                query2.setParameter(2, staDate);
                query2.setParameter(3, endDate);
                query2.setParameter(4, Constant.STATUS_USE);
                count = (Long) query2.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "finePrice.error.duplicateTime");
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }
        this.fineItemsPriceForm.setStaDate(strStaDate.trim());
        this.fineItemsPriceForm.setEndDate(strEndDate.trim());


        return true;
    }
//
//    /**
//     *
//     *author thetm
//     * kiem tra tinh hop le cua fineItem truoc khi them
//     *
//     */

    private boolean checkValidFineItemToAdd() {
        HttpServletRequest req = getRequest();

        String fineItemName = this.fineItemForm.getFineItemsName();
        Long status = this.fineItemForm.getStatus();

        //kiem tra cac truong bat buoc
        if (fineItemName == null || fineItemName.trim().equals("") || status == null) {

            req.setAttribute(REQUEST_MESSAGE, "fineItem.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh trung lap cua ten ly do phat
        String strQuery = "from FineItems where fineItemsName = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, fineItemName.trim());
        List listFineItems = query.list();
        if (listFineItems != null && listFineItems.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "fineItem.error.duplicateName");
            return false;
        }

        this.fineItemForm.setFineItemsName(fineItemName.trim());
        return true;
    }

//    /**
//     *
//     * author thetm
//     * kiem tra tinh hop le cua fineItem truoc khi update
//     *
//     */
    private boolean checkValidFineItemToEdit() {
        HttpServletRequest req = getRequest();

        String fineItemName = this.fineItemForm.getFineItemsName();
        Long status = this.fineItemForm.getStatus();

        //kiem tra cac truong bat buoc
        if (fineItemName == null || fineItemName.trim().equals("") || status == null) {

            req.setAttribute(REQUEST_MESSAGE, "fineItem.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh trung lap cua ly do phat
        String strQuery = "from FineItems where fineItemsName = ? and fineItemsId <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, fineItemName.trim());
        query.setParameter(1, this.fineItemForm.getFineItemsId());
        List listFineItems = query.list();
        if (listFineItems != null && listFineItems.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "fineItem.error.duplicateName");
            return false;
        }

        this.fineItemForm.setFineItemsName(fineItemName.trim());

        return true;
    }

    public String displayListFineItemPrice() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String strCurrentFineItemId = (String) req.getSession().getAttribute("current_fineItemId");
            Long fineItemId = 0L;
            String checkStatus = (String) req.getParameter("checkStatus");
            if (strCurrentFineItemId != null) {
                try {
                    fineItemId = new Long(strCurrentFineItemId);
                } catch (NumberFormatException ex) {
                    fineItemId = -1L;
                }
            }
            List<FineItemPrice> listFineItemPrices = new ArrayList<FineItemPrice>();
            if (("true").equals(checkStatus)) {
                String strQuery = "from FineItemPrice where fineItemId = ? order by price ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, fineItemId);
                listFineItemPrices = query.list();
                this.fineItemsPriceForm.setCheckStatus(Boolean.TRUE);
                req.getSession().setAttribute(REQUEST_CHECK_STATUS, "1");

            } else {
                String strQuery = "from FineItemPrice where fineItemId = ? and status = ? order by price ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, fineItemId);
                query.setParameter(1, Constant.STATUS_USE);
                listFineItemPrices = query.list();
                this.fineItemsPriceForm.setCheckStatus(Boolean.FALSE);
                req.getSession().setAttribute(REQUEST_CHECK_STATUS, "0");

            }

            req.getSession().setAttribute(SESSION_LIST_FINE_PRICE, listFineItemPrices);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return LIST_FINE_ITEM_PRICE;
    }

    public String pageNavigatorFineItem() throws Exception {
        log.info("Begin method pageNavigatorFineItem of FinePriceDAO ...");

        HttpServletRequest req = getRequest();
        Long telecomServiceId = (Long) req.getSession().getAttribute(SESSION_CURR_TELECOM_SERVICE_ID);
        //lay thong tin ve telecomService
        TelecomService telecomService = getTelecomServiceById(telecomServiceId);
        if (telecomService != null) {
            this.fineItemForm.setTelecomServiceId(telecomServiceId);
//            this.fineItemForm.setTelecomServiceName(telecomService.getTelServiceName());
        }

        pageForward = LIST_FINE_ITEM;

        log.info("End method pageNavigatorFineItem of FinePriceDAO ...");

        return pageForward;
    }

    /**
     * andv
     * 24/3/10
     * search ly do phat
     *
     */
    public String searchFineItems() throws Exception {
        log.info("Begin method prepareAddDiscountGroup of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        FineItemForm f = this.getFineItemForm();
        String name = f.getFineItemsName();
        List<FineItems> listFineItems = new ArrayList();
        List parameterList = new ArrayList();
        String strQuery = "from FineItems where 1 = 1";

        if (name != null && !name.trim().equals("")) {
            strQuery += " and upper(fineItemsName) like ? ";
            parameterList.add("%" + name.trim().toUpperCase() + "%");
        }
        if (f.getTelecomServiceId() != null) {
            strQuery += " and telecomServiceId = ? ";
            parameterList.add(f.getTelecomServiceId());
        }
        strQuery += " order by nlssort(fineItemsName,'nls_sort=Vietnamese') asc ";
        Query query = getSession().createQuery(strQuery);
        query.setMaxResults(1000);
        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }

        listFineItems = query.list();
        if (listFineItems.size() > 999) {
            req.setAttribute("message", "Danh sách 1000 kết quả tìm kiếm đầu tiên");
        } else {
            req.setAttribute("message", "discountGroup.search");
            List listMessageParam = new ArrayList();
            listMessageParam.add(listFineItems.size());
            req.setAttribute("messageParam", listMessageParam);
        }
        req.getSession().removeAttribute(SESSION_LIST_FINE_ITEMS);
        req.getSession().setAttribute(SESSION_LIST_FINE_ITEMS, listFineItems);
        pageForward = LIST_FINE_ITEM;

        log.info("End method prepareAddDiscountGroup of DiscountGroupDAO");

        return pageForward;
    }
}
