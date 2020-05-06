package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.CommItemFeeBean;
import com.viettel.im.client.form.CommItemFeeChannelForm;
import com.viettel.im.client.form.CommItemsForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.UserToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommCounterParams;
import com.viettel.im.database.BO.CommCounters;
import com.viettel.im.database.BO.CommItemFeeChannel;
import com.viettel.im.database.BO.CommItemGroups;
import com.viettel.im.database.BO.CommItems;
import com.viettel.im.database.BO.CommParamItemValues;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author DatPV, TUNGTV
 */
public class CommItemsDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(CommItemsDAO.class);

    //khai bao cac hang so forward
    private String pageForward;
    private final String ADD_COMM_ITEMS = "addCommItems";
    private final String LIST_COMM_ITEMS = "listCommItems";
    private final String LIST_COMM_ITEMS_ADVANCE = "listCommItemsAdvance";
    private final String DISPLAY_COMM_ITEM_GROUPS = "displayCommItemGroups";
    private final String DECLARE_COMM_PRICE_ITEMS = "declareCommPriceItems";
    private final String DECLARE_COMM_PRICE_ITEMS_ADVANCE = "declareCommPriceItemsAdvance";
    private final String LIST_COMM_FEES = "listCommFees";
    private final String DETAIL_COMM_ITEM_GROUPS = "detailCommItemsGroups";

    //khai bao cac ten bien session, request
    private final String SESSION_CURRENT_ITEM_ID = "sessionCurrentItemId";
    private final String SESSION_CURRENT_ITEM_GROUP_ID = "currentItemGroupId";
    private final String SESSION_LIST_COMM_ITEM = "listCommItem";
    private final String SESSION_LIST_COUNTER_PARAM = "listCounterParam";
    private final String SESSION_LIST_COMM_ITEM_FEE_CHANNEL = "listCommItemFeeChannel";
    private final String REQUEST_COMM_ITEMS_MODE = "commItemsMode";
    private final String REQUEST_LIST_COMM_COUNTERS = "listCommCounters";
    private final String REQUEST_FEE_MODE = "feeMode";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_LIST = "messageList";

    //bien form
    private CommItemsForm commItemsForm = new CommItemsForm();
    private CommItemFeeChannelForm itemChannelForm = new CommItemFeeChannelForm();

    public CommItemsForm getCommItemsForm() {
        return commItemsForm;
    }

    public void setCommItemsForm(CommItemsForm commItemsForm) {
        this.commItemsForm = commItemsForm;
    }

    public CommItemFeeChannelForm getItemChannelForm() {
        return itemChannelForm;
    }

    public void setItemChannelForm(CommItemFeeChannelForm itemChannelForm) {
        this.itemChannelForm = itemChannelForm;
    }

    /**
     *
     * tamdt1, 28/05/2009
     * hien thi danh sach cac khoan muc
     *
     */
    public String listCommItems() throws Exception {
        log.info("begin method listCommItems of CommItemsDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                //
                req.getSession().setAttribute(SESSION_CURRENT_ITEM_ID, -1L);

                //tim danh sach tat ca cac khoan muc thuoc nhom khoan muc hien tai
                Long currentItemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                List listCommItems = getListCommItems(currentItemGroupId);
                req.getSession().setAttribute(SESSION_LIST_COMM_ITEM, listCommItems);

                pageForward = LIST_COMM_ITEMS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("end method listCommItems of CommItemsDAO");
        return pageForward;
    }

    /**
     *
     * tamdt1, 28/05/2009
     * phuc vu viec phan trang
     *
     */
    public String pageNavigatorCommItem() throws Exception {
        log.info("Begin method pageNavigatorCommItem of CommItemsDAO ...");

        pageForward = LIST_COMM_ITEMS;

        log.info("End method pageNavigatorCommItem of CommItemsDAO");
        return pageForward;
    }

    public String pageNavigatorCommItemsAdvance() throws Exception {
        log.info("Begin method pageNavigatorCommItemsAdvance of CommItemsDAO ...");

        pageForward = LIST_COMM_ITEMS_ADVANCE;

        log.info("End method pageNavigatorCommItemsAdvance of CommItemsDAO");
        return pageForward;
    }

    /**
     *
     * tamdt1, 03/06/2009
     * xu ly su kien khi thay doi kieu nhap du lieu
     *
     */
    public String changeInputType() throws Exception {
        log.info("begin method changeInputType of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                //xoa danh sach cac tham so cua bo dem
                req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, null);

                //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                if (Constant.INPUT_TYPE_AUTO.equals(this.commItemsForm.getInputType())) {
                    CommCountersDAO commCountersDAO = new CommCountersDAO();
                    List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                            CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                    req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                }

                //chuyen doi dinh dang cua textbox ngay thang, vi du lieu mac dinh cua datetimepicker la yyyy-MM-dd nhung du lieu day len la dd/MM/yyyy
                if (this.commItemsForm.getStartDate() != null && !this.commItemsForm.getStartDate().trim().equals("")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date startDate = simpleDateFormat.parse(this.commItemsForm.getStartDate());
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    this.commItemsForm.setStartDate(simpleDateFormat.format(startDate));
                }
                if (this.commItemsForm.getEndDate() != null && !this.commItemsForm.getEndDate().trim().equals("")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date endDate = simpleDateFormat.parse(this.commItemsForm.getEndDate());
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    this.commItemsForm.setEndDate(simpleDateFormat.format(endDate));
                }



                //thiet lap che do chuan bi them/sua thong tin nhom khoan muc
                req.setAttribute(REQUEST_COMM_ITEMS_MODE, "prepareAddOrEdit");

                pageForward = ADD_COMM_ITEMS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method changeInputType of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * tamdt1, 03/06/2009
     * xu ly su kien khi thay doi bo dem
     *
     */
    public String changeCounter() throws Exception {
        log.info("begin method changeCounter of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                //xoa danh sach cac tham so cua bo dem
                req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, null);

                Long counterId = this.commItemsForm.getCounterId();
                if (counterId != null) {
                    CommCounterParamsDAO commCounterParamsDAO = new CommCounterParamsDAO();
                    commCounterParamsDAO.setSession(this.getSession());
                    List<CommCounterParams> listCommCounterParams = commCounterParamsDAO.findByProperty(
                            CommCounterParamsDAO.COUNTER_ID, counterId, Constant.STATUS_USE);
                    req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, listCommCounterParams);
                }

                CommCountersDAO commCountersDAO = new CommCountersDAO();
                List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                        CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);

                //chuyen doi dinh dang cua textbox ngay thang, vi du lieu mac dinh cua datetimepicker la yyyy-MM-dd nhung du lieu day len la dd/MM/yyyy
                if (this.commItemsForm.getStartDate() != null && !this.commItemsForm.getStartDate().trim().equals("")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date startDate = simpleDateFormat.parse(this.commItemsForm.getStartDate());
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    this.commItemsForm.setStartDate(simpleDateFormat.format(startDate));
                }
                if (this.commItemsForm.getEndDate() != null && !this.commItemsForm.getEndDate().trim().equals("")) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date endDate = simpleDateFormat.parse(this.commItemsForm.getEndDate());
                    simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    this.commItemsForm.setEndDate(simpleDateFormat.format(endDate));
                }

                //thiet lap che do chuan bi them/sua thong tin nhom khoan muc
                req.setAttribute(REQUEST_COMM_ITEMS_MODE, "prepareAddOrEdit");

                pageForward = ADD_COMM_ITEMS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method changeCounter of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * hien thi thong tin ve khoan muc
     *
     */
    public String displayCommItems() throws Exception {
        log.info("begin method displayCommItems of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                //xoa danh sach cac tham so cua bo dem
                req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, null);

                Long itemId = -1L;

                String strCommItemsId = QueryCryptUtils.getParameter(req, "selectedCommItemsId");
                if (strCommItemsId != null && !strCommItemsId.trim().equals("")) {
                    itemId = Long.valueOf(strCommItemsId);
                } else {
                    itemId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_ID);
                }

                CommItems commItems = getCommItemsById(itemId);
                if (commItems != null) {
                    //thiet lap len bien session
                    req.getSession().setAttribute(SESSION_CURRENT_ITEM_ID, itemId);

                    //thiet lap du lieu len form
                    this.commItemsForm.copyDataFromBO(commItems);

                    CommItemGroups commItemGroups = getCommItemGroupsById(commItems.getItemGroupId());
                    if (commItemGroups != null) {
                        this.commItemsForm.setCommItemGroupName(commItemGroups.getGroupName());
                    }

                    //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                    if (Constant.INPUT_TYPE_AUTO.equals(commItems.getInputType())) {
                        CommCountersDAO commCountersDAO = new CommCountersDAO();
                        List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                                CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                        req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                    }

                    //thiet lap du lieu cho cac textbox chua gia tri tham so bo dem
                    if (commItems.getCounterId() != null && commItems.getCounterId().compareTo(0L) > 0) {
                        CommCounterParamsDAO commCounterParamsDAO = new CommCounterParamsDAO();
                        commCounterParamsDAO.setSession(this.getSession());
                        List<CommCounterParams> listCounterParam = commCounterParamsDAO.findByProperty(
                                CommCounterParamsDAO.COUNTER_ID, commItems.getCounterId(), Constant.STATUS_USE);
                        req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, listCounterParam);

                        //lay danh sach cac tham so
                        List<CommParamItemValues> listCommParamItemValues = getListCommParamItemValues(commItems.getItemId());

                        //
                        this.commItemsForm.setArrCounterParam(new String[listCounterParam.size()]);
                        for (int i = 0; i < listCounterParam.size(); i++) {
                            Long tmpCounterParamId = listCounterParam.get(i).getCounterParamId();
                            for (int j = 0; j < listCommParamItemValues.size(); j++) {
                                CommParamItemValues tmpCommParamItemValues = listCommParamItemValues.get(j);
                                if (tmpCounterParamId.equals(tmpCommParamItemValues.getCounterParamId())) {
                                    //neu tim thay dung tham so (cung counterParamId), thiet lap vao bien form
                                    this.commItemsForm.getArrCounterParam()[i] = tmpCommParamItemValues.getValue();
                                    break;
                                }
                            }
                        }
                    }

                    //lay danh sach cac muc phi cua khoan muc
                    List<CommItemFeeChannel> listCommItemFeeChannel = getListCommItemFeeChannel(itemId);
                    req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL, listCommItemFeeChannel);

                    pageForward = ADD_COMM_ITEMS;

                } else {
                    //tim danh sach tat ca cac khoan muc cua nhom khoan muc hien tai
//                    Long currentItemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
//                    List listCommItems = getListCommItems(currentItemGroupId);
//                    req.getSession().setAttribute(SESSION_LIST_COMM_ITEM, listCommItems);

                    //neu khong tim thay thong tin ve nhom khoan muc can hien thi -> hien thi thong tin cua nhom khoan muc
                    pageForward = DETAIL_COMM_ITEM_GROUPS;
                }

            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method displayCommItems of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * tamdt1, 03/06/2009
     * chuan bi form them item moi
     *
     */
    public String prepareAddCommItems() throws Exception {
        log.info("begin method prepareAddCommItems of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                //xoa danh sach cac tham so cua bo dem
                req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, null);

                //xoa danh sach cac phi
                req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL, null);

                //
                this.commItemsForm.resetForm();

                Long commItemGroupsId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                CommItemGroups commItemGroups = getCommItemGroupsById(commItemGroupsId);
                if (commItemGroups != null) {
                    this.commItemsForm.setItemGroupId(commItemGroups.getItemGroupId());
                    this.commItemsForm.setCommItemGroupName(commItemGroups.getGroupName());
                }

                //thiet lap che do chuan bi them/sua thong tin nhom khoan muc
                req.setAttribute(REQUEST_COMM_ITEMS_MODE, "prepareAddOrEdit");

                pageForward = ADD_COMM_ITEMS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method prepareAddCommItems of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * tamdt1, 03/06/2009
     * chuan bi form sua thong tin cua item
     *
     */
    public String prepareEditCommItems() throws Exception {
        log.info("begin method prepareEditCommItems of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                //xoa danh sach cac tham so cua bo dem
                req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, null);

                Long itemId = this.commItemsForm.getItemId();
                CommItems commItems = getCommItemsById(itemId);
                if (commItems != null) {
                    //thiet lap du lieu len form
                    this.commItemsForm.copyDataFromBO(commItems);
                    CommItemGroups commItemGroups = getCommItemGroupsById(commItems.getItemGroupId());
                    if (commItemGroups != null) {
                        this.commItemsForm.setCommItemGroupName(commItemGroups.getGroupName());
                    }


                    //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                    if (Constant.INPUT_TYPE_AUTO.equals(commItems.getInputType())) {
                        CommCountersDAO commCountersDAO = new CommCountersDAO();
                        List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                                CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                        req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                    }

                    //thiet lap du lieu cho cac textbox chua gia tri tham so bo dem
                    if (commItems.getCounterId() != null && commItems.getCounterId().compareTo(0L) > 0) {
                        CommCounterParamsDAO commCounterParamsDAO = new CommCounterParamsDAO();
                        commCounterParamsDAO.setSession(this.getSession());
                        List<CommCounterParams> listCounterParam = commCounterParamsDAO.findByProperty(
                                CommCounterParamsDAO.COUNTER_ID, commItems.getCounterId(), Constant.STATUS_USE);
                        req.getSession().setAttribute(SESSION_LIST_COUNTER_PARAM, listCounterParam);

                        //lay danh sach cac tham so
                        List<CommParamItemValues> listCommParamItemValues = getListCommParamItemValues(commItems.getItemId());

                        //
                        this.commItemsForm.setArrCounterParam(new String[listCounterParam.size()]);
                        for (int i = 0; i < listCounterParam.size(); i++) {
                            Long tmpCounterParamId = listCounterParam.get(i).getCounterParamId();
                            for (int j = 0; j < listCommParamItemValues.size(); j++) {
                                CommParamItemValues tmpCommParamItemValues = listCommParamItemValues.get(j);
                                if (tmpCounterParamId.equals(tmpCommParamItemValues.getCounterParamId())) {
                                    //neu tim thay dung tham so (cung counterParamId), thiet lap vao bien form
                                    this.commItemsForm.getArrCounterParam()[i] = tmpCommParamItemValues.getValue();
                                    break;
                                }
                            }
                        }
                    }

                    //thiet lap che do chuan bi them/sua thong tin nhom khoan muc
                    req.setAttribute(REQUEST_COMM_ITEMS_MODE, "prepareAddOrEdit");
                    req.setAttribute("toEditCommItems", "toEdit");
                    //
                    pageForward = ADD_COMM_ITEMS;

                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method prepareEditCommItems of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * lay danh sach gia tri tham so bo dem, doi voi 1 khoan muc
     * //
     *
     */
    private List<CommParamItemValues> getListCommParamItemValues(Long itemId) {
        List listResult = new ArrayList();
        if (itemId == null) {
            return listResult;
        }

        try {
            String queryString = "from CommParamItemValues where itemId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, itemId);
            listResult = queryObject.list();
            return listResult;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     *
     * lay danh sach cac muc phi cua 1 khoan muc
     * //
     *
     */
    private List<CommItemFeeChannel> getListCommItemFeeChannel(Long itemId) {
        List listResult = new ArrayList();
        if (itemId == null) {
            return listResult;
        }

        try {
            String queryString = "select new CommItemFeeChannel(a.itemFeeChannelId, a.itemId, " +
                    "a.channelTypeId, a.fee, a.vat, a.staDate, a.endDate, a.status, a.userCreate, " +
                    "a.createDate, b.name) " +
                    "from CommItemFeeChannel a, ChannelType b " +
                    "where a.channelTypeId = b.channelTypeId and a.itemId = ? and a.status <> ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, itemId);
            queryObject.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
            listResult = queryObject.list();
            return listResult;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     *
     * lay CommParamItemValues khi biet item va counterparam
     * //
     *
     */
    private CommParamItemValues getCommParamItemValues(Long itemId, Long counterParamId) {
        CommParamItemValues commParamItemValues = null;

        if (itemId == null || counterParamId == null) {
            return commParamItemValues;
        }

        try {
            String queryString = "from CommParamItemValues where itemId = ? and counterParamId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, itemId);
            queryObject.setParameter(1, counterParamId);
            List<CommParamItemValues> listResult = queryObject.list();
            if (listResult != null && listResult.size() > 0) {
                commParamItemValues = listResult.get(0);
            }
            return commParamItemValues;

        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     *
     * author: tamdt1, 02/06/2009
     * them hoac sua thong tin commItems
     *
     */
    public String addOrEditCommItems() throws Exception {
        log.info("begin method addOrEditCommItemGroups of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                Long itemId = this.commItemsForm.getItemId();
                CommItems commItems = getCommItemsById(itemId);
                if (commItems == null) {
                    //truong hop them CommItems moi
                    if (!checkValidCommItemsToAdd()) {
                        //thiet lap du lieu cho cac combobox
                        //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                        if (Constant.INPUT_TYPE_AUTO.equals(this.commItemsForm.getInputType())) {
                            CommCountersDAO commCountersDAO = new CommCountersDAO();
                            List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                                    CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                            req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                        }

                        //thiet lap che do chuan bi them/ sua thong tin khoan muc
                        req.setAttribute(REQUEST_COMM_ITEMS_MODE, "prepareAddOrEdit");

                        log.info("end method addOrEditCommItems of CommItemsDAO");
                        pageForward = ADD_COMM_ITEMS;

                        return pageForward;

                    }

                    commItems = new CommItems();
                    this.commItemsForm.copyDataToBO(commItems);
                    itemId = getSequence("COMM_ITEMS_SEQ");
                    commItems.setItemId(itemId);
                    getSession().save(commItems);

                    //
                    this.commItemsForm.setItemId(itemId);

                    //luu gia tri cua cac tham so bo dem
                    List<CommCounterParams> listCommCounterParams = (List<CommCounterParams>) req.getSession().getAttribute(SESSION_LIST_COUNTER_PARAM);
                    String[] arrCounterParam = this.commItemsForm.getArrCounterParam();
                    if (arrCounterParam != null) {
                        for (int i = 0; i < arrCounterParam.length; i++) {
                            CommParamItemValues commParamItemValues = new CommParamItemValues();
                            Long paramItemValueId = getSequence("COMM_PARAM_ITEM_VALUES_SEQ");
                            commParamItemValues.setParamItemValueId(paramItemValueId);
                            commParamItemValues.setCounterParamId(listCommCounterParams.get(i).getCounterParamId());
                            commParamItemValues.setItemId(itemId);
                            commParamItemValues.setValue(arrCounterParam[i]);
                            getSession().save(commParamItemValues);
                        }
                    }

                    //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                    if (Constant.INPUT_TYPE_AUTO.equals(commItems.getInputType())) {
                        CommCountersDAO commCountersDAO = new CommCountersDAO();
                        List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                                CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                        req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                    }

                    //
                    req.getSession().setAttribute(SESSION_CURRENT_ITEM_ID, itemId);
                    req.setAttribute(REQUEST_MESSAGE, "commItems.create.success");

                } else {
                    //truong hop sua thong tin CommItemGroups da co
                    if (!checkValidCommItemsToEdit()) {
                        //thiet lap du lieu cho cac combobox
                        //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                        if (Constant.INPUT_TYPE_AUTO.equals(this.commItemsForm.getInputType())) {
                            CommCountersDAO commCountersDAO = new CommCountersDAO();
                            List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                                    CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                            req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                        }

                        //thiet lap che do chuan bi them/ sua thong tin khoan muc
                        req.setAttribute(REQUEST_COMM_ITEMS_MODE, "prepareAddOrEdit");

                        log.info("end method addOrEditCommItems of CommItemsDAO");
                        pageForward = ADD_COMM_ITEMS;

                        return pageForward;
                    }


                    this.commItemsForm.copyDataToBO(commItems);
                    getSession().update(commItems);

                    if (!Constant.INPUT_TYPE_AUTO.equals(commItems.getInputType())) {
                        //xoa du lieu trong bang CommParamItemValues, trong truong hop kieu nhap du lieu ko p la auto
                        try {
                            String queryString = "delete from CommParamItemValues where itemId = ?";
                            Query queryObject = getSession().createQuery(queryString);
                            queryObject.setParameter(0, itemId);
                            int result = queryObject.executeUpdate();

                        } catch (Exception ex) {
                            log.error("find by property name failed", ex);
                            throw ex;
                        }
                    } else {
                        //trong truong hop kieu du lieu la auto
                        //luu gia tri cua cac tham so bo dem
                        if (commItems.getCounterId() != null && commItems.getCounterId().compareTo(0L) > 0) {
                            List<CommCounterParams> listCommCounterParams = (List<CommCounterParams>) req.getSession().getAttribute(SESSION_LIST_COUNTER_PARAM);
                            String[] arrCounterParam = this.commItemsForm.getArrCounterParam();
                            if (arrCounterParam != null) {
                                for (int i = 0; i < arrCounterParam.length; i++) {
                                    CommParamItemValues commParamItemValues = getCommParamItemValues(itemId, listCommCounterParams.get(i).getCounterParamId());
                                    if (commParamItemValues != null) {
                                        commParamItemValues.setValue(arrCounterParam[i]);
                                        getSession().update(commParamItemValues);
                                    } else {
                                        //truong hop tham so hoan toan moi
                                        commParamItemValues = new CommParamItemValues();
                                        Long paramItemValueId = getSequence("COMM_PARAM_ITEM_VALUES_SEQ");
                                        commParamItemValues.setParamItemValueId(paramItemValueId);
                                        commParamItemValues.setCounterParamId(listCommCounterParams.get(i).getCounterParamId());
                                        commParamItemValues.setItemId(itemId);
                                        commParamItemValues.setValue(arrCounterParam[i]);
                                        getSession().save(commParamItemValues);
                                    }
                                }
                            }
                        }
                    }

                    //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                    if (Constant.INPUT_TYPE_AUTO.equals(commItems.getInputType())) {
                        CommCountersDAO commCountersDAO = new CommCountersDAO();
                        List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                                CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                        req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                    }

                    //
                    req.setAttribute(REQUEST_MESSAGE, "commItems.edit.success");
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method addOrEditCommItems of CommItemsDAO");
        pageForward = ADD_COMM_ITEMS;

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * kiem tra cac dieu kien hop le truoc khi them khoan muc vao CSDL
     *
     */
    private boolean checkValidCommItemsToAdd() {
        HttpServletRequest req = getRequest();

        String strItemName = this.commItemsForm.getItemName();
        String strStartDate = this.commItemsForm.getStartDate();
        String strStatus = this.commItemsForm.getStatus();
        String strReportType = this.commItemsForm.getReportType();
        String itemOrder = this.commItemsForm.getItemOrder();
        String strUnit = this.commItemsForm.getUnit();
        String strCheckedDoc = this.commItemsForm.getCheckedDoc();
        String strInputType = this.commItemsForm.getInputType();

        Long itemGroupId = this.commItemsForm.getItemGroupId();

        //kiem tra cac truong bat buoc phai nhap
        if (strItemName == null || strItemName.trim().equals("") ||
                strStartDate == null || strStartDate.trim().equals("") ||
                strStatus == null || strStatus.trim().equals("") ||
                strReportType == null || strReportType.trim().equals("") ||
                itemOrder == null ||
                strCheckedDoc == null || strCheckedDoc.trim().equals("") ||
                strInputType == null || strInputType.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "commItems.error.requiredFieldsEmpty");
            return false;
        }
        //Kiem tra itemOrder co phai la so hay ko
        try {
            Long i = Long.parseLong(itemOrder);
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "commItems.error.itemOderInvalid");
            return false;
        }
        //kiem tra trung lap ten
        String strQuery = "from CommItems where lower(itemName) = ? " +
                "and itemGroupId = ? and status <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, strItemName.trim().toLowerCase());
        query.setParameter(1, itemGroupId);
        query.setParameter(2, String.valueOf(Constant.STATUS_DELETE));
        List<CommItems> listCommItems = query.list();
        if (listCommItems != null && listCommItems.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "commItems.error.duplicateItemName");
            return false;
        }

        //kiem tra tinh hop le gia tri cua cac tham so bo dem nhap vao
        List<CommCounterParams> listCommCounterParams = (List<CommCounterParams>) req.getSession().getAttribute(SESSION_LIST_COUNTER_PARAM);
        String[] arrCounterParam = this.commItemsForm.getArrCounterParam();
        if (arrCounterParam != null) {
            for (int i = 0; i < arrCounterParam.length; i++) {
                String dataType = listCommCounterParams.get(i).getDataType();
                if (dataType.equals(Constant.DATA_TYPE_DATE)) {
                    try {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date tmpDate = simpleDateFormat.parse(arrCounterParam[i]);
                    } catch (Exception ex) {
                        req.setAttribute(REQUEST_MESSAGE, "commItems.error.counterParamInvalidFormat");
                        return false;
                    }
                } else if (dataType.equals(Constant.DATA_TYPE_DATE)) {
                    try {
                        Long tmpLong = Long.valueOf(arrCounterParam[i]);
                    } catch (Exception ex) {
                        req.setAttribute(REQUEST_MESSAGE, "commItems.error.counterParamInvalidFormat");
                        return false;
                    }
                }
            }
        }

        //loai bo cac khoang trang o dau va cuoi xau du lieu nhap vao
        this.commItemsForm.setItemName(strItemName);
        this.commItemsForm.setStartDate(strStartDate);
        this.commItemsForm.setStatus(strStatus);
        this.commItemsForm.setReportType(strReportType);
        this.commItemsForm.setUnit(strUnit);
        this.commItemsForm.setCheckedDoc(strCheckedDoc);
        this.commItemsForm.setInputType(strInputType);

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * kiem tra cac dieu kien hop le truoc khi sua thong tin ve khoan muc
     *
     */
    private boolean checkValidCommItemsToEdit() {
        HttpServletRequest req = getRequest();

        String strItemName = this.commItemsForm.getItemName();
        String strStartDate = this.commItemsForm.getStartDate();
        String strStatus = this.commItemsForm.getStatus();
        String strReportType = this.commItemsForm.getReportType();
        String itemOrder = this.commItemsForm.getItemOrder();
        String strUnit = this.commItemsForm.getUnit();
        String strCheckedDoc = this.commItemsForm.getCheckedDoc();
        String strInputType = this.commItemsForm.getInputType();

        Long itemGroupId = this.commItemsForm.getItemGroupId();
        Long itemId = this.commItemsForm.getItemId();

        //kiem tra cac truong bat buoc phai nhap
        if (strItemName == null || strItemName.trim().equals("") ||
                strStartDate == null || strStartDate.trim().equals("") ||
                strStatus == null || strStatus.trim().equals("") ||
                strReportType == null || strReportType.trim().equals("") ||
                itemOrder == null ||
                strCheckedDoc == null || strCheckedDoc.trim().equals("") ||
                strInputType == null || strInputType.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "commItems.error.requiredFieldsEmpty");
            return false;
        }
        //Kiem tra itemOrder co phai la so hay ko
        try {
            Long i = Long.parseLong(itemOrder);
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "commItems.error.itemOderInvalid");
            return false;
        }
        //kiem tra trung lap ten
        String strQuery = "from CommItems where lower(itemName) = ? " +
                "and itemGroupId = ? and status <> ? and itemId <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, strItemName.trim().toLowerCase());
        query.setParameter(1, itemGroupId);
        query.setParameter(2, String.valueOf(Constant.STATUS_DELETE));
        query.setParameter(3, itemId);
        List<CommItems> listCommItems = query.list();
        if (listCommItems != null && listCommItems.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "commItems.error.duplicateItemName");
            return false;
        }

        //loai bo cac khoang trang o dau va cuoi xau du lieu nhap vao
        this.commItemsForm.setItemName(strItemName);
        this.commItemsForm.setStartDate(strStartDate);
        this.commItemsForm.setStatus(strStatus);
        this.commItemsForm.setReportType(strReportType);
        this.commItemsForm.setUnit(strUnit);
        this.commItemsForm.setCheckedDoc(strCheckedDoc);
        this.commItemsForm.setInputType(strInputType);

        return true;
    }

    /**
     *
     * xoa nhom khoan muc
     *
     */
    public String deleteCommItems() throws Exception {
        log.info("begin method deleteCommItems of CommItemsDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                Long itemId = this.commItemsForm.getItemId();
                CommItems commItems = getCommItemsById(itemId);
                if (commItems == null) {
                    req.setAttribute(REQUEST_MESSAGE, "commItems.delete.error.commItemsNotExist");
                    log.info("end method deleteCommItems of CommItemsDAO");
                    pageForward = ADD_COMM_ITEMS;

                    return pageForward;
                }

                if (!checkValidCommItemsToDelete()) {
                    //neu kieu du lieu nhap vao la tu dong -> lay du lieu cho cac combobox (danh sach cac bo dem)
                    if (Constant.INPUT_TYPE_AUTO.equals(this.commItemsForm.getInputType())) {
                        CommCountersDAO commCountersDAO = new CommCountersDAO();
                        List<CommCounters> listCommCounters = commCountersDAO.findByProperty(
                                CommCountersDAO.STATUS, String.valueOf(Constant.STATUS_USE));

                        req.setAttribute(REQUEST_LIST_COMM_COUNTERS, listCommCounters);
                    }

                    //thiet lap lai len form(do mot so component bi disable khong con giu duoc gia tri)
                    this.commItemsForm.copyDataFromBO(commItems);

                    log.info("end method deleteCommItems of CommItemsDAO");
                    pageForward = ADD_COMM_ITEMS;

                    return pageForward;
                }

                //update trang thai ve xoa
                commItems.setStatus(String.valueOf(Constant.STATUS_DELETE));
                getSession().update(commItems);

                //xoa du lieu trong bang CommParamItemValues
                try {
                    String queryString = "delete from CommParamItemValues where itemId = ?";
                    Query queryObject = getSession().createQuery(queryString);
                    queryObject.setParameter(0, itemId);
                    int result = queryObject.executeUpdate();

                } catch (Exception ex) {
                    log.error("find by property name failed", ex);
                    throw ex;
                }

                getSession().flush();


                req.getSession().setAttribute(SESSION_CURRENT_ITEM_ID, -1L);

                //quay tro lai man hinh hien thi thong tin cua nhom khoan muc
                pageForward = DISPLAY_COMM_ITEM_GROUPS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }


        log.info("end method deleteCommItems of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * kiem tra cac dieu kien hop le truoc khi xoa thong tin ve khoan muc
     *
     */
    private boolean checkValidCommItemsToDelete() {
        HttpServletRequest req = getRequest();

        Long itemId = this.commItemsForm.getItemId();

        //kiem tra nhom khoan muc co dang duoc su dung hay khong
        String strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and status <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, itemId);
        query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        List<Long> list = query.list();
        if (list != null && list.size() > 0) {
            Long count = list.get(0);
            if (count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "commItems.delete.error.commItemsIsUsing");
                return false;
            }
        }

        return true;
    }

    /**
     *
     * tamdt1, 28/05/2009
     * lay danh sach tat ca cac khoan muc khong o trang thai Xoa co itemGroupId = itemGroupId
     *
     */
    private List<CommItems> getListCommItems(Long itemGroupId) {
        if (itemGroupId == null) {
            return new ArrayList<CommItems>();
        }

        try {
            String queryString = "from CommItems where itemGroupId = ? and status <> ? " +
                    "order by nlssort(itemName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, itemGroupId);
            queryObject.setParameter(1, Constant.STATUS_DELETE.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * lay CommItems co id = itemId
     *
     */
    private CommItems getCommItemsById(Long itemId) {
        CommItems commItems = null;
        if (itemId == null) {
            return commItems;
        }
        String strQuery = "from CommItems where itemId = ? and status <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, itemId);
        query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        List<CommItems> listCommItems = query.list();
        if (listCommItems != null && listCommItems.size() > 0) {
            commItems = listCommItems.get(0);
        }

        return commItems;
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * lay CommItemGroup co id = itemGroupId
     *
     */
    private CommItemGroups getCommItemGroupsById(Long itemGroupId) {
        CommItemGroups commItemGroups = null;
        if (itemGroupId == null) {
            return commItemGroups;
        }
        String strQuery = "from CommItemGroups where itemGroupId = ? and status <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, itemGroupId);
        query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        List<CommItemGroups> listCommItemGroups = query.list();
        if (listCommItemGroups != null && listCommItemGroups.size() > 0) {
            commItemGroups = listCommItemGroups.get(0);
        }

        return commItemGroups;
    }

    /**
     *
     * author tamdt1
     * date: 21/04/2009
     * chuan bi form them phi khoan muc moi
     *
     */
    public String prepareAddCommFees() throws Exception {
        log.info("Begin method prepareAddCommFees of CommItemsDAO ...");

        HttpServletRequest req = getRequest();

        Long itemId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_ID);

        CommItems commItems = getCommItemsById(itemId);
        if (commItems != null) {
            this.itemChannelForm.resetForm();
            this.itemChannelForm.setItemId(itemId);
            this.itemChannelForm.setItemName(commItems.getItemName());

            //lay du lieu cho combobox
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            List<ChannelType> listChannelType = commonDAO.getChanelType();
            req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        }

        req.setAttribute("isAddMode", true); //thiet lap che do them moi
        req.setAttribute("toEditCommItems", "toAdd");
        pageForward = DECLARE_COMM_PRICE_ITEMS;

        log.info("End method prepareAddCommFees of CommItemsDAO");

        return pageForward;
    }

    //Open popup for add multi CommFee
    public String prepareAddCommFeesAdvance() throws Exception {
        log.info("Begin method prepareAddCommFeesAdvance of CommItemsDAO ...");
        HttpServletRequest req = getRequest();
        req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, null);

        Long itemId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_ID);
        CommItems commItems = getCommItemsById(itemId);
        if (commItems != null) {
            this.itemChannelForm.resetForm();
            this.itemChannelForm.setItemId(itemId);
            this.itemChannelForm.setItemName(commItems.getItemName());

            //lay du lieu cho combobox
//            CommonDAO commonDAO = new CommonDAO();
//            commonDAO.setSession(this.getSession());
            //List<ChannelType> listChannelType = commonDAO.getChanelType();
            List<CommItemFeeChannel> listChannelType = getCommItemFeeChannelList();
            req.getSession().setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
        }

        req.setAttribute("isAddMode", true); //thiet lap che do them moi
        pageForward = DECLARE_COMM_PRICE_ITEMS_ADVANCE;
        log.info("End method prepareAddCommFeesAdvance of CommItemsDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 21/04/2009
     * chuan bi form sua thong tinn price price moi
     *
     */
    public String prepareEditCommFees() throws Exception {
        log.info("Begin method prepareEditCommFees of CommItemsDAO ...");

        HttpServletRequest req = getRequest();
        String strItemFeeChannelId = req.getParameter("selectedItemFeeChannelId");
        Long itemFeeChannelId = -1L;
        if (strItemFeeChannelId != null) {
            try {
                itemFeeChannelId = Long.valueOf(strItemFeeChannelId);
            } catch (NumberFormatException ex) {
                itemFeeChannelId = -1L;
            }
        }

        CommItemFeeChannel commItemFeeChannel = getCommItemFeeChannelById(itemFeeChannelId);
        if (commItemFeeChannel != null) {
            this.itemChannelForm.copyDataFromBO(commItemFeeChannel);
        } else {
            this.itemChannelForm.resetForm();
        }

        Long itemId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_ID);

        CommItems commItems = getCommItemsById(itemId);
        if (commItems != null) {
            this.itemChannelForm.setItemName(commItems.getItemName());

            //lay du lieu cho combobox
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            List<ChannelType> listChannelType = commonDAO.getChanelType();
            req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

        }

        pageForward = DECLARE_COMM_PRICE_ITEMS;

        log.info("End method prepareEditCommFees of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * author: tamdt1, 03/06/2009
     * them hoac sua thong tin commItemFeeChannel
     *
     */
    public String addOrEditItemFeeChannel() throws Exception {
        log.info("begin method addOrEditItemFeeChannel of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                List<CommItemFeeChannel> listCommItemFeeChannel = (List<CommItemFeeChannel>) req.getSession().getAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL);
                if (listCommItemFeeChannel == null) {
                    listCommItemFeeChannel = new ArrayList<CommItemFeeChannel>();
                    req.setAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL, listCommItemFeeChannel);
                }

                Long itemFeeChannelId = this.itemChannelForm.getItemFeeChannelId();
                CommItemFeeChannel commItemFeeChannel = getCommItemFeeChannelById(itemFeeChannelId);
                if (commItemFeeChannel == null) {
                    //truong hop them commItemFeeChannel moi
                    if (!checkValidFeeToAdd()) {
                        //lay du lieu cho combobox
                        CommonDAO commonDAO = new CommonDAO();
                        commonDAO.setSession(this.getSession());
                        List<ChannelType> listChannelType = commonDAO.getChanelType();
                        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

                        req.setAttribute("isAddMode", true); //thiet lap che do them moi

                        //
                        pageForward = DECLARE_COMM_PRICE_ITEMS;
                        return pageForward;
                    }

                    commItemFeeChannel = new CommItemFeeChannel();
                    this.itemChannelForm.copyDataToBO(commItemFeeChannel);
                    itemFeeChannelId = getSequence("COMM_ITEM_FEE_CHANNEL_SEQ");
                    commItemFeeChannel.setItemFeeChannelId(itemFeeChannelId);
                    commItemFeeChannel.setCreateDate(new Date());
                    commItemFeeChannel.setUserCreate(userToken.getLoginName());
                    getSession().save(commItemFeeChannel);

                    //
                    ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                    channelTypeDAO.setSession(this.getSession());
                    ChannelType channelType = channelTypeDAO.findById(commItemFeeChannel.getChannelTypeId());
                    commItemFeeChannel.setChannelTypeName(channelType.getName());
                    listCommItemFeeChannel.add(commItemFeeChannel);

                } else {
                    //truong hop cap nhat thong tin cua price da co
                    if (!checkValidFeeToEdit()) {
                        //lay du lieu cho combobox
                        CommonDAO commonDAO = new CommonDAO();
                        commonDAO.setSession(this.getSession());
                        List<ChannelType> listChannelType = commonDAO.getChanelType();
                        req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);

                        //
                        pageForward = DECLARE_COMM_PRICE_ITEMS;
                        return pageForward;
                    }


                    this.itemChannelForm.copyDataToBO(commItemFeeChannel);
                    getSession().update(commItemFeeChannel);

                    //thay doi tren bien session
                    for (int i = 0; i < listCommItemFeeChannel.size(); i++) {
                        if (listCommItemFeeChannel.get(i).getItemFeeChannelId().equals(itemFeeChannelId)) {
                            commItemFeeChannel = listCommItemFeeChannel.get(i);
                            this.itemChannelForm.copyDataToBO(commItemFeeChannel);

                            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                            channelTypeDAO.setSession(this.getSession());
                            ChannelType channelType = channelTypeDAO.findById(commItemFeeChannel.getChannelTypeId());
                            commItemFeeChannel.setChannelTypeName(channelType.getName());

                            break;
                        }
                    }

                }

                //
                pageForward = DECLARE_COMM_PRICE_ITEMS;
                //
                req.setAttribute(REQUEST_FEE_MODE, "closePopup");

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method addOrEditItemFeeChannel of CommItemsDAO");

        return pageForward;
    }

    //Add ItemFeeChannel with multi channel
    public String addOrEditItemFeeChannelAdvance() throws Exception {
        log.info("begin method addOrEditItemFeeChannelAdvance of CommItemsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                //Sau khi xu ly xong, neu thanh cong se gan = false
                req.setAttribute("isAddMode", true); //thiet lap che do them moi

                List<CommItemFeeChannel> listCommItemFeeChannel = (List<CommItemFeeChannel>) req.getSession().getAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL);
                if (listCommItemFeeChannel == null) {
                    listCommItemFeeChannel = new ArrayList<CommItemFeeChannel>();
                    req.setAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL, listCommItemFeeChannel);
                    pageForward = DECLARE_COMM_PRICE_ITEMS_ADVANCE;
                    return pageForward;
                }

                List<CommItemFeeChannel> listChannelType = (List<CommItemFeeChannel>) req.getSession().getAttribute(REQUEST_LIST_CHANNEL_TYPE);
                if (listChannelType == null) {
                    listChannelType = new ArrayList<CommItemFeeChannel>();
                    req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
                    pageForward = DECLARE_COMM_PRICE_ITEMS_ADVANCE;
                    return pageForward;
                }

                //Kiem tra gia tri FEE & VAT tung doi tuong duoc check tren danh sach
//                listChannelType = validateCommItemFeeChannelList(itemChannelForm, listChannelType);
//                req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);
//                if (!itemChannelForm.getMessage().equals("")){
//                    req.setAttribute(REQUEST_MESSAGE, itemChannelForm.getMessage());
//
//                    pageForward = DECLARE_COMM_PRICE_ITEMS_ADVANCE;
//                    return pageForward;
//                }

                //Kiem tra gia tri ngay thang co hop le khong?
                if (!checkValidFeeToAddAdvance()) {
                    pageForward = DECLARE_COMM_PRICE_ITEMS_ADVANCE;
                    return pageForward;
                }

                //Thuc hien insert tung doi tuong duoc chon
                boolean isChecked = false;
                for (int index = 0; index < listChannelType.size(); index++) {
                    for (int i = 0; i < this.itemChannelForm.getChannelTypeIdCheckedList().length; i++) {
                        if (listChannelType.get(index).getChannelTypeId().compareTo(Long.valueOf(this.itemChannelForm.getChannelTypeIdCheckedList()[i])) == 0) {
                            listChannelType.get(index).setChecked(true);
                            break;
                        }
                    }
                    if (!listChannelType.get(index).isChecked()) {
                        continue;
                    }
                    CommItemFeeChannel commItemFeeChannel = new CommItemFeeChannel();

                    Long itemFeeChannelId = getSequence("COMM_ITEM_FEE_CHANNEL_SEQ");
                    commItemFeeChannel.setItemFeeChannelId(itemFeeChannelId);
                    commItemFeeChannel.setCreateDate(new Date());
                    commItemFeeChannel.setUserCreate(userToken.getLoginName());
                    commItemFeeChannel.setItemId(this.itemChannelForm.getItemId());
                    commItemFeeChannel.setChannelTypeId(listChannelType.get(index).getChannelTypeId());

                    commItemFeeChannel.setStatus(Constant.STATUS_USE.toString());
//                    commItemFeeChannel.setFee(listChannelType.get(index).getFee());
//                    commItemFeeChannel.setVat(listChannelType.get(index).getVat());

                    commItemFeeChannel.setFee(this.itemChannelForm.getFee());
                    commItemFeeChannel.setVat(this.itemChannelForm.getVat());

                    try {
                        commItemFeeChannel.setStaDate(DateTimeUtils.convertStringToDate(this.itemChannelForm.getStaDate()));
                    } catch (Exception ex) {
                        commItemFeeChannel.setStaDate(null);
                    }
                    try {
                        commItemFeeChannel.setEndDate(DateTimeUtils.convertStringToDate(this.itemChannelForm.getEndDate()));
                    } catch (Exception ex) {
                        commItemFeeChannel.setEndDate(null);
                    }
                    commItemFeeChannel.setStatus(Constant.STATUS_USE.toString());

                    getSession().save(commItemFeeChannel);

                    commItemFeeChannel.setChannelTypeName(listChannelType.get(index).getChannelTypeName());
                    listCommItemFeeChannel.add(commItemFeeChannel);
                    isChecked = true;
                }

                if (!isChecked) {
                    req.setAttribute(REQUEST_MESSAGE, "!!! Bạn bạn chưa chọn đối tượng nào trong danh sách");
                    pageForward = DECLARE_COMM_PRICE_ITEMS_ADVANCE;
                    return pageForward;
                }


                req.setAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL, listCommItemFeeChannel);
                req.setAttribute("isAddMode", false);

                pageForward = DECLARE_COMM_PRICE_ITEMS_ADVANCE;
                req.setAttribute(REQUEST_FEE_MODE, "closePopup");
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method addOrEditItemFeeChannelAdvance of CommItemsDAO");

        return pageForward;
    }

    private List<CommItemFeeChannel> validateCommItemFeeChannelList(CommItemFeeChannelForm form, List<CommItemFeeChannel> list) {
        try {
            String[] channelTypeIdCheckedList = form.getChannelTypeIdCheckedList();
            String[] channelTypeIdList = form.getChannelTypeIdList();
            String[] feeList = form.getFeeList();
            String[] vatList = form.getVatList();

            //Duyet danh sach, kiem tra tung phan tu trong danh sach
            //found = false;
            //Neu phan tu do duoc check (bang cach duyet mang duoc check)
            //Duyet mang danh sach kenh, lay STT phan tu
            //Neu tim thay, dinh dang lai fee & vat & bao da tim thay found = true
            //Neu found = false thi gan fee & vat = 0


            itemChannelForm.setMessage("");
            String msg1 = "";
            String msg2 = "";
            boolean isChecked = false;
            boolean found = false;
            for (int index_1 = 0; index_1 < list.size(); index_1++) {
                found = false;
                for (int index_2 = 0; index_2 < channelTypeIdCheckedList.length; index_2++) {
                    if (Long.valueOf(channelTypeIdCheckedList[index_2]).compareTo(list.get(index_1).getChannelTypeId()) == 0) {
                        for (int index_3 = 0; index_3 < channelTypeIdList.length; index_3++) {
                            if (Long.valueOf(channelTypeIdList[index_3]).compareTo(list.get(index_1).getChannelTypeId()) == 0) {
                                String feeTemp = feeList[index_3].trim();
                                String vatTemp = vatList[index_3].trim();
                                while (feeTemp.indexOf(",") >= 0) {
                                    feeTemp = feeTemp.replace(",", "");
                                    feeTemp = feeTemp.replace(".", "");
                                }
                                while (vatTemp.indexOf(",") >= 0) {
                                    vatTemp = vatTemp.replace(",", "");
                                    vatTemp = vatTemp.replace(".", "");
                                }
                                list.get(index_1).setChecked(true);
                                isChecked = true;
                                try {
                                    list.get(index_1).setFee(Long.valueOf(feeTemp));
                                    list.get(index_1).setFeeString(feeList[index_3].trim());
                                } catch (Exception ex) {
                                    list.get(index_1).setFee(Long.valueOf(0L));
                                    list.get(index_1).setFeeString("");
                                    if (msg1.equals("")) {
                                        msg1 = "Giá tiền không đúng định dạng";
                                    }
                                }
                                try {
                                    list.get(index_1).setVat(Long.valueOf(vatTemp));
                                    list.get(index_1).setVatString(vatList[index_3].trim());
                                    if (Long.valueOf(vatTemp).compareTo(100L) >= 0) {
                                        throw new Exception("VAT phải nằm trong khoảng  từ 0 đến 100");
                                    }

                                } catch (Exception ex) {
                                    list.get(index_1).setVat(Long.valueOf(0L));
                                    list.get(index_1).setVatString("");
                                    if (msg2.equals("")) {
                                        msg2 = "VAT không đúng định dạng";
                                    }
                                }
                                found = true;
                                break;
                            }
                        }
                    }
                    if (found) {
                        break;
                    }
                }
                if (!found) {
                    list.get(index_1).setChecked(false);
                    list.get(index_1).setFee(null);
                    list.get(index_1).setVat(null);
                    list.get(index_1).setFeeString("");
                    list.get(index_1).setVatString("");
                }
            }
            if (!msg1.equals("") && !msg2.equals("")) {
                itemChannelForm.setMessage("!!! " + msg1 + ", " + msg2);
            } else {
                itemChannelForm.setMessage(msg1 + msg2);
            }

            if (!isChecked) {
                itemChannelForm.setMessage("!!! Bạn bạn chưa chọn đối tượng nào trong danh sách");
            }

            return list;
        } catch (Exception ex) {
            return null;
        }
    }
    //Get ChannelList for inputting fee & vat

    private List<CommItemFeeChannel> getCommItemFeeChannelList() {
        try {
            String queryString = "select channel_type_id as channelTypeId " +
                    ", name as channelTypeName " +
                    ", '' as feeString" +
                    ", '' as vatString " +
                    " from Channel_Type where status = ? and check_Comm = ? ";
            Query queryObject = getSession().createSQLQuery(queryString).
                    addScalar("channelTypeId", Hibernate.LONG).
                    addScalar("channelTypeName", Hibernate.STRING).
                    addScalar("feeString", Hibernate.STRING).
                    addScalar("vatString", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(CommItemFeeChannel.class));
            queryObject.setParameter(0, Constant.STATUS_USE.toString());
            queryObject.setParameter(1, Constant.CHECK_COM);
            return queryObject.list();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            return null;
        }
    }

    private boolean checkValidFeeToAddAdvance() {
        HttpServletRequest req = getRequest();

        this.itemChannelForm.setStatus(Constant.STATUS_USE.toString());

        Long price = this.itemChannelForm.getFee();
        Long vat = this.itemChannelForm.getVat();
        String status = this.itemChannelForm.getStatus();
//        Long channelTypeId = this.itemChannelForm.getChannelTypeId();
        String strStaDate = this.itemChannelForm.getStaDate();
        String strEndDate = this.itemChannelForm.getEndDate();

        if (price == null || vat == null ||
                status == null || status.trim().equals("") ||
                //                channelTypeId == null || channelTypeId.compareTo(0L) == 0 ||
                strStaDate == null || strStaDate.trim().equals("")) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0L) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) >= 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDate");
                return false;
            }
        }

        if (this.itemChannelForm.getStatus().equals(String.valueOf(Constant.STATUS_USE))) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot khoan muc, doi voi 1 doi tuong ap phi, chi co 1 gia active vao 1 thoi diem

            String[] channelTypeIdList = this.itemChannelForm.getChannelTypeIdCheckedList();

            for (String channelTypeIdTemp : channelTypeIdList) {
                try {
                    Long channelTypeId = Long.valueOf(channelTypeIdTemp);
                    Long count;

                    if (endDate != null) {
                        //kiem tra dieu kien doi voi staDate nhap vao xem co nam trong cac khoang da ton tai hay khong
                        String strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                                "and status = ? and staDate <= ? and (endDate >= ? or endDate is null)";
                        Query query = getSession().createQuery(strQuery);
                        query.setParameter(0, this.itemChannelForm.getItemId());
                        query.setParameter(1, channelTypeId);
                        query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                        query.setParameter(3, staDate);
                        query.setParameter(4, staDate);
                        count = (Long) query.list().get(0);

                        if (count != null && count.compareTo(0L) > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                            return false;
                        }

                        //kiem tra dieu kien doi voi endDate nhap vao xem co nam trong cac khoang da ton tai hay khong
                        query.setParameter(3, endDate);
                        query.setParameter(4, endDate);
                        count = (Long) query.list().get(0);

                        if (count != null && count.compareTo(0L) > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                            return false;
                        }

                        //kiem tra xem co khoang nao nam trong doan tu staDate den endDate nhap vao hay khong
                        strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                                "and status = ? and staDate >= ? and staDate <= ? ";
                        query = getSession().createQuery(strQuery);
                        query.setParameter(0, this.itemChannelForm.getItemId());
                        query.setParameter(1, channelTypeId);
                        query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                        query.setParameter(3, staDate);
                        query.setParameter(4, endDate);
                        count = (Long) query.list().get(0);

                        if (count != null && count.compareTo(0L) > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                            return false;
                        }

                        //
                        strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                                "and status = ? and endDate >= ? and endDate <= ? ";
                        query = getSession().createQuery(strQuery);
                        query.setParameter(0, this.itemChannelForm.getItemId());
                        query.setParameter(1, channelTypeId);
                        query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                        query.setParameter(3, staDate);
                        query.setParameter(4, endDate);
                        count = (Long) query.list().get(0);

                        if (count != null && count.compareTo(0L) > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                            return false;
                        }


                    } else {
                        String strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                                "and status = ? and staDate >= ? ";
                        Query query = getSession().createQuery(strQuery);
                        query.setParameter(0, this.itemChannelForm.getItemId());
                        query.setParameter(1, channelTypeId);
                        query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                        query.setParameter(3, staDate);
                        count = (Long) query.list().get(0);

                        if (count != null && count.compareTo(0L) > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                            return false;
                        }

                        strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                                "and status = ? and endDate is null ";
                        query = getSession().createQuery(strQuery);
                        query.setParameter(0, this.itemChannelForm.getItemId());
                        query.setParameter(1, channelTypeId);
                        query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                        count = (Long) query.list().get(0);

                        if (count != null && count.compareTo(0L) > 0) {
                            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                            return false;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                    return false;
                }
            }
        }

        this.itemChannelForm.setStaDate(strStaDate.trim());
        this.itemChannelForm.setEndDate(strEndDate.trim());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 04/06/2009
     * xoa phi khoan muc
     *
     */
    public String deleteCommFees() throws Exception {
        log.info("Begin method deletePrice of CommItemsDAO ...");

        HttpServletRequest req = getRequest();
        String strItemFeeChannelId = req.getParameter("selectedItemFeeChannelId");
        Long itemFeeChannelId = -1L;
        if (strItemFeeChannelId != null) {
            try {
                itemFeeChannelId = Long.valueOf(strItemFeeChannelId);
            } catch (NumberFormatException ex) {
                itemFeeChannelId = -1L;
            }
        }

        CommItemFeeChannel commItemFeeChannel = getCommItemFeeChannelById(itemFeeChannelId);
        if (commItemFeeChannel != null) {
            if (!checkValidFeeToDelete(itemFeeChannelId)) {
                //
                pageForward = LIST_COMM_FEES;
                log.info("End method deletePrice of CommItemsDAO");

                return pageForward;
            }


            commItemFeeChannel.setStatus(String.valueOf(Constant.STATUS_DELETE));
            getSession().update(commItemFeeChannel);

            //cap nhat lai bien session
            List<CommItemFeeChannel> listCommItemFeeChannel = (List<CommItemFeeChannel>) req.getSession().getAttribute(SESSION_LIST_COMM_ITEM_FEE_CHANNEL);
            if (listCommItemFeeChannel != null) {
                for (int i = 0; i < listCommItemFeeChannel.size(); i++) {
                    if (listCommItemFeeChannel.get(i).getItemFeeChannelId().equals(itemFeeChannelId)) {
                        listCommItemFeeChannel.remove(i);
                        break;
                    }
                }
            }
        }

        pageForward = LIST_COMM_FEES;

        log.info("End method deletePrice of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 03/06/2009
     * them refresh lai danh sach phi cua khoan muc
     *
     */
    public String refreshListCommFees() throws Exception {
        log.info("Begin method refreshListCommFees of CommItemsDAO ...");

        pageForward = LIST_COMM_FEES;

        log.info("End method refreshListCommFees of CommItemsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 03/06/2009
     * lay CommItemFeeChannel co id = itemFeeChannelId
     *
     */
    private CommItemFeeChannel getCommItemFeeChannelById(Long itemFeeChannelId) {
        CommItemFeeChannel commItemFeeChannel = null;
        if (itemFeeChannelId == null) {
            return commItemFeeChannel;
        }
        String strQuery = "from CommItemFeeChannel where itemFeeChannelId = ? and status <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, itemFeeChannelId);
        query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        List<CommItemFeeChannel> listCommItemFeeChannel = query.list();
        if (listCommItemFeeChannel != null && listCommItemFeeChannel.size() > 0) {
            commItemFeeChannel = listCommItemFeeChannel.get(0);
        }

        return commItemFeeChannel;
    }

    /**
     *
     * author tamdt1
     * date: 03/06/2009
     * kiem tra tinh hop le cua 1 fee truoc khi them muc phi moi cho khoan muc
     *
     */
    private boolean checkValidFeeToAdd() {
        HttpServletRequest req = getRequest();

        Long price = this.itemChannelForm.getFee();
        Long vat = this.itemChannelForm.getVat();
        String status = this.itemChannelForm.getStatus();
        Long channelTypeId = this.itemChannelForm.getChannelTypeId();
        String strStaDate = this.itemChannelForm.getStaDate();
        String strEndDate = this.itemChannelForm.getEndDate();

        if (price == null || vat == null ||
                status == null || status.trim().equals("") ||
                channelTypeId == null || channelTypeId.compareTo(0L) == 0 ||
                strStaDate == null || strStaDate.trim().equals("")) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0L) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDate");
                return false;
            }
        }

        if (this.itemChannelForm.getStatus().equals(String.valueOf(Constant.STATUS_USE))) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot khoan muc, doi voi 1 doi tuong ap phi, chi co 1 gia active vao 1 thoi diem
            try {
                Long count;

                if (endDate != null) {
                    //kiem tra dieu kien doi voi staDate nhap vao xem co nam trong cac khoang da ton tai hay khong
                    String strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and staDate <= ? and (endDate >= ? or endDate is null)";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    query.setParameter(4, staDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    //kiem tra dieu kien doi voi endDate nhap vao xem co nam trong cac khoang da ton tai hay khong
                    query.setParameter(3, endDate);
                    query.setParameter(4, endDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    //kiem tra xem co khoang nao nam trong doan tu staDate den endDate nhap vao hay khong
                    strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and staDate >= ? and staDate <= ? ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    query.setParameter(4, endDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    //
                    strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and endDate >= ? and endDate <= ? ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    query.setParameter(4, endDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }


                } else {
                    String strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and staDate >= ? ";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and endDate is null ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }

        this.itemChannelForm.setStaDate(strStaDate.trim());
        this.itemChannelForm.setEndDate(strEndDate.trim());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 03/06/2009
     * kiem tra tinh hop le cua 1 fee truoc khi sua thong tin muc phi moi cho khoan muc
     *
     */
    private boolean checkValidFeeToEdit() {
        HttpServletRequest req = getRequest();

        Long price = this.itemChannelForm.getFee();
        Long vat = this.itemChannelForm.getVat();
        String status = this.itemChannelForm.getStatus();
        Long channelTypeId = this.itemChannelForm.getChannelTypeId();
        String strStaDate = this.itemChannelForm.getStaDate();
        String strEndDate = this.itemChannelForm.getEndDate();
        Long itemFeeChannelId = this.itemChannelForm.getItemFeeChannelId();

        if (price == null || vat == null ||
                status == null || status.trim().equals("") ||
                channelTypeId == null || channelTypeId.compareTo(0L) == 0 ||
                strStaDate == null || strStaDate.trim().equals("")) {
            //cac truong bat buoc khong duoc de trong
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.requiredFieldsEmpty");
            return false;
        }
        if (price.compareTo(0L) < 0) {
            //gia khong duoc < 0
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.priceNegative");
            return false;
        }
        if (vat.compareTo(0L) < 0 || vat.compareTo(100L) > 0) {
            //vat phai nam trong khoang tu 0 - 100
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidVat");
            return false;
        }

        Date staDate = null;
        try {
            staDate = DateTimeUtils.convertStringToDate(strStaDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDateFormat");
                return false;
            }
            if (staDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.invalidDate");
                return false;
            }
        }

        if (this.itemChannelForm.getStatus().equals(String.valueOf(Constant.STATUS_USE))) {
            //kiem tra cac dieu kien trung lap gia' dam bao dieu kien:
            //mot khoan muc, doi voi 1 doi tuong ap phi, chi co 1 gia active vao 1 thoi diem
            try {
                Long count;

                if (endDate != null) {
                    //kiem tra dieu kien doi voi staDate nhap vao xem co nam trong cac khoang da ton tai hay khong
                    String strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and staDate <= ? and (endDate >= ? or endDate is null) and itemFeeChannelId <> ? ";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    query.setParameter(4, staDate);
                    query.setParameter(5, itemFeeChannelId);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    //kiem tra dieu kien doi voi endDate nhap vao xem co nam trong cac khoang da ton tai hay khong
                    query.setParameter(3, endDate);
                    query.setParameter(4, endDate);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    //kiem tra xem co khoang nao nam trong doan tu staDate den endDate nhap vao hay khong
                    strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and staDate >= ? and staDate <= ? and itemFeeChannelId <> ? ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    query.setParameter(4, endDate);
                    query.setParameter(5, itemFeeChannelId);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    //
                    strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and endDate >= ? and endDate <= ? and itemFeeChannelId <> ? ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    query.setParameter(4, endDate);
                    query.setParameter(5, itemFeeChannelId);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }


                } else {
                    String strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and staDate >= ? and itemFeeChannelId <> ? ";
                    Query query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, staDate);
                    query.setParameter(4, itemFeeChannelId);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }

                    strQuery = "select count(*) from CommItemFeeChannel where itemId = ? and channelTypeId = ? " +
                            "and status = ? and endDate is null and itemFeeChannelId <> ? ";
                    query = getSession().createQuery(strQuery);
                    query.setParameter(0, this.itemChannelForm.getItemId());
                    query.setParameter(1, this.itemChannelForm.getChannelTypeId());
                    query.setParameter(2, String.valueOf(Constant.STATUS_USE));
                    query.setParameter(3, itemFeeChannelId);
                    count = (Long) query.list().get(0);

                    if (count != null && count.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "commItemFee.error.duplicateTime");
                        return false;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
                return false;
            }
        }

        this.itemChannelForm.setStaDate(strStaDate.trim());
        this.itemChannelForm.setEndDate(strEndDate.trim());

        return true;
    }

    /**
     *
     * author tamdt1, 20/07/2009
     * kiem tra tinh hop le cua 1 fee truoc khi xoa
     *
     */
    private boolean checkValidFeeToDelete(Long itemFeeChannelId) {
        HttpServletRequest req = getRequest();

        //kiem tra fee da duoc su dung de tinh phi hay chua
        String strQuery = "select count(*) from CommTransaction where feeId = ? and status <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, itemFeeChannelId);
        query.setParameter(1, Constant.STATUS_DELETE);
        List<Long> list = query.list();
        if (list != null && list.size() > 0) {
            Long count = list.get(0);
            if (count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE_LIST, "commItemFee.delete.error.commItemFeeIsUsing");
                return false;
            }
        }

        return true;
    }

//    private List listItems = new ArrayList();
//
//    public List getListItems() {
//        return listItems;
//    }
//
//    public void setListItems(List listItems) {
//        this.listItems = listItems;
//    }

//    /**
//     * Prepare infor when go to interface
//     * @return list of commission to display
//     * @throws java.lang.Exception
//     */
//    public String preparePage() throws Exception {
//        log.info("Begin.");
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                CommItemGroupsDAO commItemGroupsDAO = new CommItemGroupsDAO();
//                commItemGroupsDAO.setSession(getSession());
//                List<CommItemGroups> commissionGroupList = getSession().createCriteria(CommItemGroups.class).
//                    add(Restrictions.eq("parentItemGroupId", ROOT_COMM)).
//                    add(Restrictions.eq("status", Constant.STATUS_USE.toString())).
//                    addOrder(Order.asc("groupName")).
//                    list();
//                session.setAttribute("commissionGroupList", commissionGroupList);
//                session.setAttribute("interfaceType", CREATE_COMM_INTERFACE);
//                clearSession();
//                pageForward = TREE_GROUP_COMMISSION_ITEM;
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End.");
//        return pageForward;
//    }
//
//
//    /**
//     *
//     * Display Tree
//     * @return list of commission to display
//     * @throws java.lang.Exception
//     * tra ve danh sach cay khoan muc can hien thi thong tin
//     *
//     */
//    public String getCommTree() throws Exception {
//        log.info("begin method getCommTree of CommItemsDAO...");
//        try {
//            this.listItems = new ArrayList();
//            Session hbnSession = getSession();
//
//            HttpServletRequest httpServletRequest = getRequest();
//
//            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");
//            if (node.indexOf("0_") == 0) {
//                node = "0"; //mac dinh nhom khoan muc o muc cao nhat se co parent = 0
//                List listItemMainGroup = new ArrayList();
//
//                //chon nhung nhom khoan muc chua bi xoa
//                listItemMainGroup = hbnSession.createCriteria(CommItemGroups.class).
//                        add(Restrictions.eq("parentItemGroupId", Long.parseLong(node))).
//                        add(Restrictions.ne("status", Constant.STATUS_DELETE.toString())).
//                        addOrder(Order.asc("groupName")).
//                        list();
//                Iterator iterSaleServices = listItemMainGroup.iterator();
//                while (iterSaleServices.hasNext()) {
//                    CommItemGroups commGroup = (CommItemGroups) iterSaleServices.next();
//                    String nodeGroupId = "1_" + commGroup.getItemGroupId().toString();
//                    getListItems().add(new Node(commGroup.getGroupName(), "true", nodeGroupId, httpServletRequest.getContextPath() + "/commitemgroupsAction!prepareEditCommItemGroups.do?selectedCommId=" + commGroup.getItemGroupId().toString()));
//                }
//            } else if (node.indexOf("0_") == -1) {
//                node = node.substring(2);
//                List listItemGroup = new ArrayList();
//
//                listItemGroup = hbnSession.createCriteria(CommItemGroups.class).
//                        add(Restrictions.eq("parentItemGroupId", Long.parseLong(node))).
//                        add(Restrictions.ne("status", Constant.STATUS_DELETE.toString())).
//                        addOrder(Order.asc("groupName")).
//                        list();
//                Iterator iterSaleServices = listItemGroup.iterator();
//                while (iterSaleServices.hasNext()) {
//                    CommItemGroups commGroup = (CommItemGroups) iterSaleServices.next();
//                    String nodeGroupId = "1_" + commGroup.getItemGroupId().toString();
//                    getListItems().add(new Node(commGroup.getGroupName(), "true", nodeGroupId, httpServletRequest.getContextPath() + "/commitemgroupsAction!prepareEditCommItemGroups.do?selectedCommId=" + commGroup.getItemGroupId().toString()));
//                }
//            }
//
//            log.info("end method getCommTree of CommItemsDAO");
//
//            return "success";
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//
//    /**
//     * Delete comm item
//     * @return list of commission to display
//     * @throws java.lang.Exception
//     */
//    public String deleteCommItem() throws Exception {
//        log.info("Begin.");
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                Long commItemsId = (Long)session.getAttribute("commItemsId");
//                    if (commItemsId == null) {
//                        log.info("Comm items id");
//                        return pageForward;
//                    }
//
//                    CommItemsDAO commItemsDAO = new CommItemsDAO();
//                    commItemsDAO.setSession(getSession());
//                    CommItems commItems = commItemsDAO.findById(commItemsId);
//                    commItems.setStatus(CommItemGroupsDAO.STATUS_DELETE.toString());
//                    commItemsDAO.saveOrUpdate(commItems);
//
//                    /** Update comm price */
//                    CommItemFeeChannelDAO cifcdao = new CommItemFeeChannelDAO();
//                    cifcdao.setSession(getSession());
//                    List<CommItemFeeChannel> listCommItemFeeChannel = cifcdao.getAllCommItemFee(commItems.getItemId());
//
//                    for (CommItemFeeChannel commItemFeeChannel:listCommItemFeeChannel)
//                    {
//                        commItemFeeChannel.setStatus(CommItemGroupsDAO.STATUS_DELETE);
//                        cifcdao.saveOrUpdate(commItemFeeChannel);
//                    }
//
//                    Long commItemGroupId = (Long)session.getAttribute("commItemGroupId");
//
//                    if (commItemGroupId == null) {
//                        log.info("Comm items group id is missing");
//                        return pageForward;
//                    }
//
//                    List<CommItems> commItemsList = findByItemGroupId(commItemGroupId);
//                    session.setAttribute("listCommItems", commItemsList);
//                    addActionError("Đã xóa thành công một khoản mục");
//                    pageForward = LIST_COMMISSION_ITEM;
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End.");
//        return pageForward;
//    }
//
//
//    /**
//     *
//     * Display Items in the group
//     * @return list of commission to display
//     * @throws java.lang.Exception
//     * hien thi thong tin ve nhom khoan muc
//     *
//     */
//    public String displayGroupItem() throws Exception {
//        log.info("begin method displayGroupItem of CommItemsDAO...");
//
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                String selectedCommIdTemp = QueryCryptUtils.getParameter(req, "selectedCommId");
//                if (selectedCommIdTemp == null || selectedCommIdTemp.trim().equals("")) {
//                    log.debug("Commission ID was missing");
//                    return Constant.ERROR_PAGE;
//                }
//
//                Long selectedCommId = new Long(selectedCommIdTemp);
//                String interfaceType = (String)session.getAttribute("interfaceType");
//                List<CommItems> commItemsList = new ArrayList();
//
//                /** Display depend on interface type */
//                if (interfaceType.equals(CREATE_COMM_INTERFACE)) {
//                    commItemsList = findByItemGroupId(selectedCommId);
//                } else {
//                    commItemsList = findActiveByItemGroupId(selectedCommId);
//                }
//
//                CommItemGroupsDAO commItemGroupsDAO = new CommItemGroupsDAO();
//                commItemGroupsDAO.setSession(getSession());
//
//                CommItemGroups commItemGroups = commItemGroupsDAO.findById(selectedCommId);
//
//                session.setAttribute("commItemGroupName", commItemGroups.getGroupName());
//                session.setAttribute("commItemGroupId", selectedCommId);
//                session.setAttribute("listCommItems", commItemsList);
//
//                pageForward = LIST_COMMISSION_ITEM;
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.info("end method displayGroupItem of CommItemsDAO");
//
//        return pageForward;
//    }
//
//
//    /**
//     *
//     * update commitem or create new commitem
//     * @return list of commission to display
//     * @throws java.lang.Exception
//     *
//     *
//     */
//    public String changActionType() throws Exception {
//        log.info("begin method changActionType of CommItemsDAO...");
//
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                String actionType = (String)req.getParameter("actionType");
//
//                if (!StringUtils.validString(actionType)) {
//                    log.debug("Not find action type");
//                    return pageForward;
//                }
//
//                if (actionType.equals(NEW_TYPE)) {
//                    session.setAttribute("actionType", NEW_TYPE);
//                    form = new CommissionItemsForm();
//
//                    /** List bo dem */
//
//                    CommCountersDAO commCountersDAO = new CommCountersDAO();
//                    commCountersDAO.setSession(getSession());
//                    List<CommCounters> commCountersList = commCountersDAO.findAllActive();
//                    session.setAttribute("commCountersList", commCountersList);
//
//                } else if (actionType.equals(UPDATE_TYPE)) {
//                    session.setAttribute("actionType", UPDATE_TYPE);
//                    Long commItemsId = (Long)session.getAttribute("commItemsId");
//                    if (commItemsId == null) {
//                        log.info("Comm items id is null in update type");
//                        return pageForward;
//                    }
//
//                    CommItemsDAO commItemsDAO = new CommItemsDAO();
//                    commItemsDAO.setSession(getSession());
//                    CommItems commItems = commItemsDAO.findById(commItemsId);
//                    setFormInfor(commItems, null);
//
//                } else if (actionType.equals(BACK)) {
//                    pageForward = ADD_COMMISSION_ITEM;
//                    session.setAttribute("actionType", VIEW_TYPE);
//                    return pageForward;
//                } else {
//                    session.setAttribute("actionType", VIEW_TYPE);
//                    Long commItemsId = (Long)session.getAttribute("commItemsId");
//                    if (commItemsId == null) {
//                        log.info("Comm items id is null in view type");
//
//                        /** Return to previous page */
//                        pageForward = LIST_COMMISSION_ITEM;
//                        return pageForward;
//                    }
//
//                    CommItemsDAO commItemsDAO = new CommItemsDAO();
//                    commItemsDAO.setSession(getSession());
//                    CommItems commItems = commItemsDAO.findById(commItemsId);
//                    setFormInfor(commItems, null);
//
//                    pageForward = LIST_COMMISSION_ITEM;
//                    return pageForward;
//                }
//                pageForward = ADD_COMMISSION_ITEM;
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.info("begin method changActionType of CommItemsDAO...");
//
//        return pageForward;
//    }
//
//
//    public String pageNavigator() {
//        log.info("In pageNavigator");
//        pageForward = LIST_COMMISSION_ITEM;
//        return pageForward;
//    }
//
//
//    /**
//     * update commitem or create new commitem
//     * @return list of commission to display
//     * @throws java.lang.Exception
//     */
//    public String updateCommItem() throws Exception {
//        log.info("Begin.");
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//
//                String actionType = (String)session.getAttribute("actionType");
//
//                if (!StringUtils.validString(actionType)) {
//                    log.info("Not find action type");
//                    return pageForward;
//                }
//
//                Long commItemGroupId = (Long)session.getAttribute("itemGroupId");
//
//                if (commItemGroupId == null) {
//                    log.info("Not find comm item group id");
//                    return pageForward;
//                }
//
//                if (actionType.equals(NEW_TYPE)) {
//                    insertCommItem(commItemGroupId);
//                    addActionError("Đã đăng ký thành công khoản mục mới");
//                } else if (actionType.equals(UPDATE_TYPE)) {
//                    Long commItemsId = (Long)session.getAttribute("commItemsId");
//                    if (commItemsId == null) {
//                        log.info("Comm items id");
//                        return pageForward;
//                    }
//                    CommItemsDAO commItemsDAO = new CommItemsDAO();
//                    commItemsDAO.setSession(getSession());
//                    CommItems commItems = commItemsDAO.findById(commItemsId);
//                    updateCommItem(commItems, commItemGroupId);
//                    addActionError("Đã cập nhật thành công khoản mục");
//                }
//                //session.setAttribute("actionType", VIEW_TYPE);
//                //pageForward = ADD_COMMISSION_ITEM;
//
//                /** Hien thi danh sach khoan muc tinh hoa hong cua nhom khoan muc */
//                List<CommItems> commItemsList = new ArrayList();
//
//                /** Display depend on interface type */
//                CommItemsDAO commItemsDAO = new CommItemsDAO();
//                commItemsDAO.setSession(getSession());
//
//                commItemsList = commItemsDAO.findByItemGroupId(commItemGroupId);
//
//                CommItemGroupsDAO commItemGroupsDAO = new CommItemGroupsDAO();
//                commItemGroupsDAO.setSession(getSession());
//
//                CommItemGroups commItemGroups = commItemGroupsDAO.findById(commItemGroupId);
//
//                session.setAttribute("commItemGroupName", commItemGroups.getGroupName());
//                session.setAttribute("listCommItems", commItemsList);
//
//                pageForward = LIST_COMMISSION_ITEM;
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End.");
//        return pageForward;
//    }
//
//
//    /**
//     * insert new comm item
//     * @return
//     * @throws java.lang.Exception
//     */
//    public void insertCommItem(Long commItemGroupId) throws Exception {
//        log.info("Begin.");
//
//            try {
//                CommItems commItems = new CommItems();
//                commItems.setItemId(getSequence("COMM_ITEMS_SEQ"));
//                session.setAttribute("commItemsId", commItems.getItemId());
//                updateCommItem(commItems, commItemGroupId);
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//
//        log.info("End.");
//    }
//
//    /**
//     * insert new comm item
//     * @return
//     * @throws java.lang.Exception
//     */
//    public void updateCommItem(CommItems commItems, Long commItemGroupId) throws Exception {
//        log.info("Begin.");
//
//            try {
//                commItems.setItemName(form.getCommItemName());
//                commItems.setStatus(form.getCommItemStatus());
//                commItems.setInputType(form.getCommItemCountType());
//                commItems.setReportType(form.getCommItemReportType());
//                commItems.setUnit(form.getCommItemUnit());
//                commItems.setItemOrder(new Long(form.getCommItemOrder()));
//                commItems.setStartDate(DateTimeUtils.convertStringToDate(form.getCommItemStart()));
//                commItems.setEndDate(DateTimeUtils.convertStringToDate(form.getCommItemEnd()));
//                commItems.setDescription(form.getCommItemDescription());
//                commItems.setItemGroupId(commItemGroupId);
//                if (form.isCommItemCheckDoc()){
//                    commItems.setCheckedDoc("1");
//                } else {
//                    commItems.setCheckedDoc("0");
//                }
//                if (form.getCommItemCounter() != null && !form.getCommItemCounter().trim().equals("")) {
//                    commItems.setCounterId(new Long(form.getCommItemCounter()));
//                }
//
//                CommItemsDAO commItemsDAO = new CommItemsDAO();
//                commItemsDAO.setSession(getSession());
//                commItemsDAO.saveOrUpdate(commItems);
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//
//        log.info("End.");
//    }
//
//    /**
//     * Prepare infor when go to interface
//     * @return list of commission to display
//     * @throws java.lang.Exception
//     */
//    public String displayCommItem() throws Exception {
//        log.info("Begin.");
//        getReqSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//                String selectedCommIdTemp = QueryCryptUtils.getParameter(req, "selectedCommId");
//
//                if (selectedCommIdTemp == null || selectedCommIdTemp.trim().equals("")) {
//                    log.debug("Commission ID was missing");
//                    return Constant.ERROR_PAGE;
//                }
//
//                Long selectedCommId = new Long(selectedCommIdTemp);
//                CommItems commItems = findById(selectedCommId);
//
//                CommItemGroupsDAO commItemGroupsDAO = new CommItemGroupsDAO();
//                commItemGroupsDAO.setSession(getSession());
//                CommItemGroups commItemGroups = commItemGroupsDAO.findById(commItems.getItemGroupId());
//
//                CommCountersDAO commCountersDAO = new CommCountersDAO();
//                commCountersDAO.setSession(getSession());
//
//                List<CommCounters> commCountersList = commCountersDAO.findAllActive();
//
//                setFormInfor(commItems, commItemGroups.getGroupName());
//
//                session.setAttribute("commItemGroupId", commItemGroups.getItemGroupId());
//
//                session.setAttribute("commItemGroupName", commItemGroups.getGroupName());
//                session.setAttribute("commCountersList", commCountersList);
//                session.setAttribute("commItemsId", commItems.getItemId());
//                session.setAttribute("actionType", VIEW_TYPE);
//
//                pageForward = ADD_COMMISSION_ITEM;
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End.");
//        return pageForward;
//    }
//
//
//    private void clearSession() {
//        session.setAttribute("listCommItems", null);
//        session.setAttribute("commItemGroupName", null);
//        session.setAttribute("commItemGroupId", null);
//        session.setAttribute("actionType", null);
//        session.setAttribute("commItemsId", null);
//    }
//    private void setFormInfor(CommItems commItems, String groupName) throws Exception {
//        form = new CommissionItemsForm();
//        form.setCommItemName(commItems.getItemName());
//        if (groupName != null) {
//            form.setCommItemGroup(groupName);
//        }
//        form.setCommItemStatus(commItems.getStatus());
//        form.setCommItemCountType(commItems.getInputType());
//        form.setCommItemReportType(commItems.getReportType());
//        form.setCommItemUnit(commItems.getUnit());
//        if (commItems.getItemOrder() != null) {
//            form.setCommItemOrder(commItems.getItemOrder().toString());
//        }
//        form.setCommItemStart(DateTimeUtils.convertDateToString(commItems.getStartDate()));
//        form.setCommItemEnd(DateTimeUtils.convertDateToString(commItems.getEndDate()));
//        form.setCommItemDescription(commItems.getDescription());
//        if (commItems.getCounterId() != null) {
//            form.setCommItemCounter(commItems.getCounterId().toString());
//        }
//        if (commItems.getCheckedDoc() != null && commItems.getCheckedDoc().equals("1")) {
//            form.setCommItemCheckDoc(true);
//        } else {
//            form.setCommItemCheckDoc(false);
//        }
//    }
    public List<CommItems> findByItemGroupId(java.lang.Long id) {
        log.info("getting CommItems instance with id: " + id);
        try {
            String query = "from CommItems where itemGroupId = ?";
            Query queryObject = getSession().createQuery(query);
            queryObject.setParameter(0, id);
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List<CommItems> findActiveByItemGroupId(java.lang.Long id) {
        log.info("getting CommItems instance with id: " + id);
        try {
            String query = "from CommItems where itemGroupId = ? and status = ? order by nlssort(itemName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(query);
            queryObject.setParameter(0, id);
            queryObject.setParameter(1, Constant.STATUS_USE.toString());
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    /**
     * 
     * tamdt1, 03/06/2009
     * tim tat ca cac CommItem khong bi xoa (dang o trang thai hieu luc hoac ko hieu luc)
     * 
     */
    public List<CommItems> findNotDeleteByItemGroupId(java.lang.Long id) {
        log.info("getting CommItems instance with id: " + id);
        try {
            String query = "from CommItems where itemGroupId = ? and status <> ? order by nlssort(itemName,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(query);
            queryObject.setParameter(0, id);
            queryObject.setParameter(1, Constant.STATUS_DELETE.toString());
            log.info("End.");
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public CommItems findById(java.lang.Long id) {
        log.debug("getting CommItems instance with id: " + id);
        try {
            CommItems instance = (CommItems) getSession().get(
                    "com.viettel.im.database.BO.CommItems", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
}

