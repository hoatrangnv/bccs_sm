package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.DiscountForm;
import com.viettel.im.client.form.DiscountGroupForm;
import com.viettel.im.client.form.StockModelForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Discount;
import com.viettel.im.database.BO.DiscountGroup;
import com.viettel.im.database.BO.DiscountModelMap;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.TelecomService;
import java.util.ArrayList;
import java.util.Date;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.DiscountModelMapForm;
import com.viettel.im.database.BO.Price;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author tamdt1
 * date 14/03/2009
 * thuc hien tat ca cac tac vu lien quan den nhom chiet khau
 *
 */
public class DiscountGroupDAO extends BaseDAOAction {
    private static final Log log = LogFactory.getLog(DiscountGroupDAO.class);
    private String pageForward;
    //khai bao cac hang so forward
    private static final String DISCOUNT_GROUP_OVERVIEW = "discountGroupOverview";
    private static final String DISCOUNT_GROUP_INFO = "discountGroupInfo";
    private static final String SEARCH_DISCOUNT_GROUP = "searchDiscountGroup";
    private static final String DISCOUNT_GROUP = "discountGroup";
    private static final String DISCOUNT_DETAIL = "discountDetail";
    private static final String DISCOUNT_GOODS = "discountGoods";

    //khai bao cac ten bien session hoac request
    private final String REQUEST_IS_ADD_MODE = "isAddMode"; //flag thiet lap che do them thong tin moi
    private final String REQUEST_IS_EDIT_MODE = "isEditMode"; //flag thiet lap che do sua thong tin da co
    private final String REQUEST_IS_VIEW_MODE = "isViewMode"; //flag thiet lap che do xem thong tin
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_LIST_DISCOUNT_GROUP = "listDiscountGroup";
    private final String REQUEST_LIST_DISCOUNT_DETAIL = "listDiscountDetail";
    private final String REQUEST_LIST_DISCOUNT_GOODS = "listDiscountGoods";
    private final String REQUEST_LIST_DISCOUNT_POLICY = "listDiscountPolicy";
    private final String REQUEST_LIST_TELECOM_SERVICES = "listTelecomServices";
    private final String REQUEST_LIST_STOCK_TYPE = "listStockType";
    private final String SESSION_CURR_DISCOUNT_GROUP_ID = "currentDiscountGroupId";

    //khai bao bien form - start
    private DiscountGroupForm discountGroupForm = new DiscountGroupForm();
    private DiscountForm discountForm = new DiscountForm();
    private DiscountModelMapForm discountModelMapForm = new DiscountModelMapForm();

    public DiscountGroupForm getDiscountGroupForm() {
        return discountGroupForm;
    }

    public void setDiscountGroupForm(DiscountGroupForm discountGroupForm) {
        this.discountGroupForm = discountGroupForm;
    }

    public DiscountForm getDiscountForm() {
        return discountForm;
    }

    public void setDiscountForm(DiscountForm discountForm) {
        this.discountForm = discountForm;
    }

    public DiscountModelMapForm getDiscountModelMapForm() {
        return discountModelMapForm;
    }

    public void setDiscountModelMapForm(DiscountModelMapForm discountModelMapForm) {
        this.discountModelMapForm = discountModelMapForm;
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
     * author tamdt1
     * date: 18/04/2009
     * man hinh dau tien, hien thi tree va danh sach cac discountGroup
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();

        //lay danh sach cac discountGroup
        List<DiscountGroup> listDiscountGroup = getListDiscountGroups();
        req.setAttribute(REQUEST_LIST_DISCOUNT_GROUP, listDiscountGroup);

        //xoa bien session
        setTabSession(SESSION_CURR_DISCOUNT_GROUP_ID, -1L);

        //
        getDataForCombobox();

        //
        this.discountGroupForm.setStatus(null);

        //return
        pageForward = DISCOUNT_GROUP_OVERVIEW;
        log.info("End method preparePage of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * tim kiem thong tin ve nhom CK
     *
     */
    public String searchDiscountGroup() throws Exception {
        log.info("Begin method searchDiscountGroup of DiscountGroupDAO ...");
        HttpServletRequest request = getRequest();

        String discountGroupName = this.discountGroupForm.getName();
        String discountPolicy = this.discountGroupForm.getDiscountPolicy();
        String stockModelCode = this.discountGroupForm.getStockModelCode();
        Long status = this.discountGroupForm.getStatus();

        List parameterList = new ArrayList();
        String strQuery = ""
                + "SELECT new com.viettel.im.database.BO.DiscountGroup("
                + "       a.discountGroupId, a.name, a.status, a.notes, a.discountPolicy, "
                + "       a.telecomServiceId, b.telServiceName, c.name) "
                + "FROM   DiscountGroup a, TelecomService b, AppParams c "
                + "WHERE  a.telecomServiceId = b.telecomServiceId "
                + "       and a.discountPolicy = c.id.code "
                + "       and c.id.type = ? ";
        parameterList.add(Constant.APP_PARAMS_DISCOUNT_POLICY);

        if (discountGroupName != null && !discountGroupName.trim().equals("")) {
            strQuery += " and upper(a.name) like ? ";
            parameterList.add("%" + discountGroupName.trim().toUpperCase() + "%");
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
            if (listStockModel == null || listStockModel.size() != 1) {
                request.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Mã mặt hàng không tồn tại");
                pageForward = SEARCH_DISCOUNT_GROUP;
                log.info("End method searchDiscountGroup of DiscountGroupDAO");
                return pageForward;
            } else {
                StockModel tmpStockModel = listStockModel.get(0);
                stockModelId = tmpStockModel.getStockModelId();
                this.discountGroupForm.setStockModelId(tmpStockModel.getStockModelId());
                this.discountGroupForm.setStockModelCode(tmpStockModel.getStockModelCode());
                this.discountGroupForm.setStockModelName(tmpStockModel.getName());
            }
        }

        if (stockModelId != null && stockModelId.compareTo(0L) > 0) {
            strQuery += " and exists (  SELECT  'X' "
                    + "                   FROM    DiscountModelMap "
                    + "                   WHERE   stockModelId = ? "
                    + "                           and status = ? "
                    + "                           and discountGroupId = a.discountGroupId) ";
            parameterList.add(stockModelId);
            parameterList.add(Constant.STATUS_ACTIVE);
        }

        if(discountPolicy != null && !"".equals(discountPolicy)) {
            strQuery += " and a.discountPolicy = ? ";
            parameterList.add(discountPolicy);
        }

        if(status != null) {
            strQuery += " and a.status = ? ";
            parameterList.add(status);
        }

        strQuery += " order by nlssort(a.name,'nls_sort=Vietnamese') asc ";
        Query query = getSession().createQuery(strQuery);
        query.setMaxResults(1000);
        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }

        List<DiscountGroup> listDiscountGroup = query.list();
        if (listDiscountGroup == null) {
            listDiscountGroup = new ArrayList();
        }

        request.setAttribute(REQUEST_MESSAGE, "discountGroup.search");
        List listMessageParam = new ArrayList();
        listMessageParam.add(listDiscountGroup.size());
        request.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);

        request.setAttribute(REQUEST_LIST_DISCOUNT_GROUP, listDiscountGroup);

        //
        getDataForCombobox();

        pageForward = SEARCH_DISCOUNT_GROUP;
        log.info("End method prepareAddDiscountGroup of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * hien thi thong tin discountGroup
     *
     */
    public String displayDiscountGroup() throws Exception {
        log.info("Begin method displayDiscountGroup of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        String strSelectedDiscountGroupId = req.getParameter("selectedDiscountGroupId");
        Long discountGroupId;
        if (strSelectedDiscountGroupId != null) {
            try {
                discountGroupId = new Long(strSelectedDiscountGroupId);
            } catch (NumberFormatException ex) {
                discountGroupId = -1L;
            }
        } else {
            //discountGroupId = (Long) req.getSession().getAttribute(SESSION_CURR_DISCOUNT_GROUP_ID);
            discountGroupId = (Long) getTabSession(SESSION_CURR_DISCOUNT_GROUP_ID);
        }

        DiscountGroup discountGroup = getDiscountGroupById(discountGroupId);
        if (discountGroup != null) {
            setTabSession(SESSION_CURR_DISCOUNT_GROUP_ID, discountGroupId);
            //chuyen du lieu len form
            this.discountGroupForm.copyDataFromBO(discountGroup);
        } else {
            //neu khong tim thay nhom CK de hien thi, quay ve man hinh
        }

        //lay du lieu cho cac combobox
        getDataForCombobox();
        //xac lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        pageForward = DISCOUNT_GROUP_INFO;
        log.info("End method displayDiscountGroup of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * chuan bi form them discountGroup
     *
     */
    public String prepareAddDiscountGroup() throws Exception {
        log.info("Begin method prepareAddDiscountGroup of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        this.discountGroupForm.resetForm();
        
        //lay du lieu cho cac combobox
        getDataForCombobox();

        //xac lap che do chuan bi them discountGroup moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = DISCOUNT_GROUP;
        log.info("End method prepareAddDiscountGroup of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * chuan bi form them discountGroup cho lan dau tien
     *
     */
    public String prepareAddDiscountGroupFirstTime() throws Exception {
        log.info("Begin method prepareAddDiscountGroupFirstTime of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        this.discountGroupForm.resetForm();

        //lay du lieu cho cac combobox
        getDataForCombobox();

        //xac lap che do chuan bi them discountGroup moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = DISCOUNT_GROUP_INFO;
        log.info("End method prepareAddDiscountGroupFirstTime of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * chuan bi form sua thong tin discountGroup
     *
     */
    public String prepareEditDiscountGroup() throws Exception {
        log.info("Begin method prepareEditDiscountGroup of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Long discountGroupId = this.discountGroupForm.getDiscountGroupId();
        DiscountGroup discountGroup = getDiscountGroupById(discountGroupId);
        if (discountGroup != null) {
            this.discountGroupForm.copyDataFromBO(discountGroup);
            //xac lap che do chuan bi sua thong tin discountGroup
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);

            //lay du lieu cho cac combobox
            getDataForCombobox();
        }

        pageForward = DISCOUNT_GROUP;
        log.info("End method prepareEditDiscountGroup of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * them discountGroup moi hoac sua thong tin discountGroup da co
     *
     */
    public String addOrEditDiscountGroup() throws Exception {
        log.info("Begin method addOrEditDiscountGroup of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        try {
            Long discountGroupId = this.discountGroupForm.getDiscountGroupId();
            DiscountGroup discountGroup = getDiscountGroupById(discountGroupId);
            if (discountGroup == null) {
                //----------------------------------------------------------------
                //them discountGroup moi
                if (checkValidDiscountGroupToAdd()) {
                    //cap nhat CSDL
                    discountGroup = new DiscountGroup();
                    this.discountGroupForm.copyDataToBO(discountGroup);
                    discountGroupId = getSequence("DISCOUNT_GROUP_SEQ");
                    discountGroup.setDiscountGroupId(discountGroupId);
                    discountGroup.setStatus(Constant.STATUS_USE);
                    session.save(discountGroup);
                    session.flush();

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, discountGroup));
                    saveLog(Constant.ACTION_SAVE_DISCOUNT_GROUP, "ACTION_SAVE_DISCOUNT_GROUP", lstLogObj,discountGroup.getDiscountGroupId(), Constant.DEFINE_DISCOUNT, Constant.ADD);

                    //
                    this.discountGroupForm.setDiscountGroupId(discountGroupId);
                    setTabSession(SESSION_CURR_DISCOUNT_GROUP_ID, discountGroupId);

                    //xac lap che do xem thong tin
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountgroup.addsuccessful");

                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_ADD_MODE, true);
                }

            } else {
                //----------------------------------------------------------------
                //sua thong tin discountGroup da co
                if (checkValidDiscountGroupToEdit()) {
                    //disable hoac enable cac mat hang tuong ung thuoc nhom CK
                    Long discountGroupStatus = this.discountGroupForm.getStatus();
                    String strQueryUpdateDiscountModelMap = ""
                            + "update DiscountModelMap  "
                            + "set status = ? "
                            + "where discountGroupId = ? ";
                    Query queryUpdateDiscountModelMap = session.createQuery(strQueryUpdateDiscountModelMap);
                    queryUpdateDiscountModelMap.setParameter(0, discountGroupStatus);
                    queryUpdateDiscountModelMap.setParameter(1, discountGroupId);
                    queryUpdateDiscountModelMap.executeUpdate();

                    //
                    DiscountGroup oldDiscountGroup = new DiscountGroup();
                    BeanUtils.copyProperties(oldDiscountGroup, discountGroup);

                    //cap nhat CSDL
                    this.discountGroupForm.copyDataToBO(discountGroup);
                    session.update(discountGroup);
                    session.flush();

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldDiscountGroup, discountGroup));
                    saveLog(Constant.ACTION_UPDATE_DISCOUNT_GROUP, "ACTION_UPDATE_DISCOUNT_GROUP", lstLogObj,discountGroup.getDiscountGroupId(), Constant.DEFINE_DISCOUNT, Constant.EDIT);

                    //xac lap che do xem thong tin
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountgroup.editsuccessful");

                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_EDIT_MODE, true);
                }
            }

            //lay du lieu cho cac combobox
            getDataForCombobox();

        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }
        
        //return
        pageForward = DISCOUNT_GROUP;
        log.info("End method addOrEditDiscountGroup of DiscountGroupDAO");
        return pageForward;
        
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * xoa discountGroup
     *
     */
    public String delDiscountGroup() throws Exception {
        log.info("Begin method delDiscountGroup of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();
        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        try {
            Long discountGroupId = this.discountGroupForm.getDiscountGroupId();
            DiscountGroup discountGroup = getDiscountGroupById(discountGroupId);
            if (discountGroup != null) {
                if (checkValidDiscountGroupToDel()) {
                    //----------------------------------------------------------------
                    //doan nay xoa dua tren contraint trong db, neu vi fam contraint se log ra exception
                    try {
                        DiscountGroup oldDiscountGroup = new DiscountGroup();
                        BeanUtils.copyProperties(oldDiscountGroup, discountGroup);

                        //cap nhat du lieu vao db
                        //do discountDetail va discountModelMap khong xoa truc tiep khoi DB nen p xoa cac du lieu nay truoc khi xoa discountGroup//                
//                        String strQueryDeleteDiscountDetail = "delete from Discount where discountGroupId = ? and status = ? ";
//                        Query queryDeleteDiscountDetail = session.createQuery(strQueryDeleteDiscountDetail);
//                        queryDeleteDiscountDetail.setParameter(0, discountGroupId);
//                        queryDeleteDiscountDetail.setParameter(1, Constant.STATUS_DELETE);
//                        int resultDeleteDiscountDetail = queryDeleteDiscountDetail.executeUpdate();                        
                        
                        //xoa discountDetail
                        DiscountDAO discoutDAO = new DiscountDAO();
                        List<Discount> lstDiscountDetail = discoutDAO.findByProperty("discountGroupId", discountGroupId);
                        if (!lstDiscountDetail.isEmpty()) {
                            for (Discount discount : lstDiscountDetail) {
                                session.delete(discount);
                                lstLogObj.add(new ActionLogsObj(discount, null));
                            }
                        }    
                        
                        //xoa discountModelMap
                        DiscountModelMapDAO discountModelMapDAO = new DiscountModelMapDAO();
                        List<DiscountModelMap> lstDiscountModelMap = discountModelMapDAO.findByProperty("discountGroupId", discountGroupId);
                        if (!lstDiscountModelMap.isEmpty()) {
                            for (DiscountModelMap discountModel : lstDiscountModelMap) {
                                session.delete(discountModel);
                                lstLogObj.add(new ActionLogsObj(discountModel, null));
                            }
                        }    

//                        String strQueryDeleteDiscountModelMap = "delete from DiscountModelMap where discountGroupId = ? and status = ? ";
//                        Query queryDeleteDiscountModelMap = session.createQuery(strQueryDeleteDiscountModelMap);
//                        queryDeleteDiscountModelMap.setParameter(0, discountGroupId);
//                        queryDeleteDiscountModelMap.setParameter(1, Constant.STATUS_DELETE);
//                        int resultDeleteDiscountModelMap = queryDeleteDiscountModelMap.executeUpdate();
                         
                        session.delete(discountGroup);
                        

                        //ghi log
                       
                        lstLogObj.add(new ActionLogsObj(oldDiscountGroup, null));
                        saveLog(Constant.ACTION_DELETE_DISCOUNT_GROUP, "ACTION_DELETE_DISCOUNT_GROUP", lstLogObj,discountGroup.getDiscountGroupId(),Constant.DEFINE_DISCOUNT, Constant.DELETE);
                        session.flush();
                        //
                        this.discountGroupForm.resetForm();

                        //xac lap che do view discountGroup
                        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                        //hien thi message
                        req.setAttribute(REQUEST_MESSAGE, "discountgroup.delsuccessful");


                    } catch (Exception ex) {
                        //rollback
                        session.clear();

                        //ghi log
                        ex.printStackTrace();

                        //hien thi message
                        req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.delete.discountGroupIsUsing");

                    }
                    //----------------------------------------------------------------

                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);
                }

            } else {
                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.discountGroupNotFound");

                //xac lap che do chuan bi them discountGroup
                req.setAttribute(REQUEST_IS_VIEW_MODE, true);
            }

            //lay du lieu cho cac combobox
            getDataForCombobox();

        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }

        //return
        pageForward = DISCOUNT_GROUP;
        log.info("End method delDiscountGroup of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 14/05/2009
     * desc     : hien thi thogn tin chi tiet CK
     *
     */
    public String displayDiscountDetail() throws Exception {
        log.info("Begin method displayDiscountDetail of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        String strDiscountId = req.getParameter("selectedDiscountId");
        Long discountId;
        if (strDiscountId != null) {
            try {
                discountId = Long.valueOf(strDiscountId);
            } catch (NumberFormatException ex) {
                discountId = -1L;
            }
        } else {
            discountId = -1L;
        }

        Discount discount = getDiscountById(discountId);
        if (discount != null) {
            //
            this.discountForm.copyDataFromBO(discount);
        } else {
            //
            this.discountForm.resetForm();
        }

        //lay danh sach chi tiet CK thuoc nhom CK
        Long discountGroupId = (Long) getTabSession(SESSION_CURR_DISCOUNT_GROUP_ID);
        List<Discount> listDiscount = getListDiscount(session, discountGroupId);
        req.setAttribute(REQUEST_LIST_DISCOUNT_DETAIL, listDiscount);

        //thiet lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        log.info("End method displayDiscountDetail of DiscountGroupDAO");
        pageForward = DISCOUNT_DETAIL;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 20/04/2009
     * desc     : chuan bi form them discount moi
     *
     */
    public String prepareAddDiscountDetail() throws Exception {
        log.info("Begin method prepareAddDiscountDetail of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();

        //giu lai id cua discountGroup ma discount thuoc ve
        Long discountGroupId = (Long) getTabSession(SESSION_CURR_DISCOUNT_GROUP_ID);
        this.discountForm.resetForm();
        this.discountForm.setDiscountGroupId(discountGroupId);

        //thiet lap che do them moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = DISCOUNT_DETAIL;
        log.info("End method prepareAddDiscountDetail of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 23/04/2009
     * desc     : chuan bi sua thong tin discount
     *
     */
    public String prepareEditDiscountDetail() throws Exception {
        log.info("Begin method prepareEditDiscountDetail of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();

        Long discountId = this.discountForm.getDiscountId();
        Discount discount =  getDiscountById(discountId);
        if (discount != null) {
            //
            this.discountForm.copyDataFromBO(discount);

            //thiet lap che do sua thong tin
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);

        }

        pageForward = DISCOUNT_DETAIL;
        log.info("End method prepareEditDiscountDetail of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/04/2009
     * desc     : them discount moi hoac sua thong tin discount da co
     *
     */
    public String addOrEditDiscountDetail() throws Exception {
        log.info("Begin method addOrEditDiscountDetail of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        try {
            Long discountId = this.discountForm.getDiscountId();
            Discount discount = getDiscountById(discountId);

            if (discount == null) {
                //----------------------------------------------------------------
                //truong hop them discount moi
                if (checkValidDiscountToAdd()) {
                    //cap nhat CSDL
                    discount = new Discount();
                    this.discountForm.copyDataToBO(discount);
                    discountId = getSequence("DISCOUNT_SEQ");
                    discount.setDiscountId(discountId);
                    discount.setStatus(Constant.STATUS_USE);
                    session.save(discount);
                    session.flush();

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, discount));
                    saveLog(Constant.ACTION_SAVE_DISCOUNT, "ACTION_SAVE_DISCOUNT", lstLogObj,discount.getDiscountId(), Constant.DEFINE_DISCOUNT, Constant.ADD);

                    //dua du lieu len form
                    this.discountForm.setDiscountId(discountId);

                    //xac lap che do xem thong tin
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountdetail.addsuccessful");

                    //cap nhat lai list
                    List<Discount> listDiscount = getListDiscount(session, discount.getDiscountGroupId());
                    req.setAttribute(REQUEST_LIST_DISCOUNT_DETAIL, listDiscount);

                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_ADD_MODE, true);
                }

            } else {
                //truong hop cap nhat thong tin cua discount da co
                if (checkValidDiscountToEdit()) {
                    //
                    Discount oldDiscount = new Discount();
                    BeanUtils.copyProperties(oldDiscount, discount);

                    //cap nhat CSDL
                    this.discountForm.copyDataToBO(discount);
                    session.update(discount);
                    session.flush();

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldDiscount, discount));
                    saveLog(Constant.ACTION_UPDATE_DISCOUNT, "ACTION_UPDATE_DISCOUNT", lstLogObj,discount.getDiscountId(), Constant.DEFINE_DISCOUNT, Constant.EDIT);

                    //xac lap che do xem thong tin
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountdetail.editsuccessful");

                    //cap nhat lai list
                    List<Discount> listDiscount = getListDiscount(session, discount.getDiscountGroupId());
                    req.setAttribute(REQUEST_LIST_DISCOUNT_DETAIL, listDiscount);

                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_EDIT_MODE, true);
                }

            }
            
        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }
        
        //return
        this.pageForward = DISCOUNT_DETAIL;
        log.info("End method addOrEditDiscountDetail of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/04/2009
     * desc     : xoa thong tin discount
     *
     */
    public String delDiscountDetail() throws Exception {
        log.info("Begin method delDiscountDetail of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        try {
            Long discountId = this.discountForm.getDiscountId();
            Discount discount = getDiscountById(discountId);
            if (discount != null) {
                if (checkValidDiscountToDel()) {
                    //----------------------------------------------------------------
                    //doan nay xoa dua tren contraint trong db, neu vi fam contraint se log ra exception
                    try {
                        Discount oldDiscount = new Discount();
                        BeanUtils.copyProperties(oldDiscount, discount);

                        //cap nhat vao CSDL
//                        discount.setStatus(Constant.STATUS_DELETE);
//                        session.update(discount);
//                        session.flush();
                        session.delete(discount);
                        session.flush();

                        //ghi log
                        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                        lstLogObj.add(new ActionLogsObj(oldDiscount, null));
                        saveLog(Constant.ACTION_DELETE_DISCOUNT, "ACTION_DELETE_DISCOUNT", lstLogObj,discount.getDiscountId(), Constant.DEFINE_DISCOUNT, Constant.DELETE);

                        //reset form
                        this.discountForm.resetForm();

                        //xac lap che do view discountGroup
                        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                        //hien thi message
                        req.setAttribute(REQUEST_MESSAGE, "discountdetail.delsuccessful");

                    } catch (Exception ex) {
                        //rollback
                        session.clear();

                        //ghi log
                        ex.printStackTrace();

                        //hien thi message
                        req.setAttribute(REQUEST_MESSAGE, "discountdetail.error.delete.discountDetailIsUsing");
                    }
                    //----------------------------------------------------------------
                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);
                }

            } else {
                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "discount.error.discountNotFound");

                //xac lap che do chuan bi them discountGroup
                req.setAttribute(REQUEST_IS_VIEW_MODE, true);

            }

            //cap nhat lai list
            List<Discount> listDiscount = getListDiscount(session, discount.getDiscountGroupId());
            req.setAttribute(REQUEST_LIST_DISCOUNT_DETAIL, listDiscount);

        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }

        //return
        pageForward = DISCOUNT_DETAIL;
        log.info("End method delDiscountDetail of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 14/05/2009
     * desc     : hien thi mat hang thuoc nhom CK
     *
     */
    public String displayDiscountGoods() throws Exception {
        log.info("Begin method displayDiscountGoods of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        String strDiscountGoodsId = req.getParameter("selectedDiscountGoodsId");
        Long discountGoodsId;
        if (strDiscountGoodsId != null) {
            try {
                discountGoodsId = Long.valueOf(strDiscountGoodsId);
            } catch (NumberFormatException ex) {
                discountGoodsId = -1L;
            }
        } else {
            discountGoodsId = -1L;
        }

        DiscountModelMap discountModelMap = getDiscountModelMapById(discountGoodsId);
        if (discountModelMap != null) {
            //
            this.discountModelMapForm.copyDataFromBO(discountModelMap);
        } else {
            //
            this.discountModelMapForm.resetForm();
        }

        //lay danh sach mat hang thuoc nhom CK
        Long discountGroupId = (Long) getTabSession(SESSION_CURR_DISCOUNT_GROUP_ID);
        List<DiscountModelMap> listDiscountModelMap = getListDiscountModelMap(session, discountGroupId);
        req.setAttribute(REQUEST_LIST_DISCOUNT_GOODS, listDiscountModelMap);

        //
        getDataForComboboxDiscountGoods();

        //thiet lap che do hien thi thong tin
        req.setAttribute(REQUEST_IS_VIEW_MODE, true);

        log.info("End method displayDiscountGoods of DiscountGroupDAO");
        pageForward = DISCOUNT_GOODS;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 14/05/2009
     * desc     : chuan bi form them discountGoods moi
     *
     */
    public String prepareAddDiscountGoods() throws Exception {
        log.info("Begin method prepareAddDiscountGoods of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();

        //giu lai id cua discountGroup ma discount thuoc ve
        Long discountGroupId = (Long) getTabSession(SESSION_CURR_DISCOUNT_GROUP_ID);
        this.discountModelMapForm.resetForm();
        this.discountModelMapForm.setDiscountGroupId(discountGroupId);

        //
        getDataForComboboxDiscountGoods();

        //thiet lap che do them moi
        req.setAttribute(REQUEST_IS_ADD_MODE, true);

        pageForward = DISCOUNT_GOODS;
        log.info("End method prepareAddDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 13/05/2009
     * desc     : chuan bi sua thong tin discountGoods
     *
     */
    public String prepareEditDiscountGoods() throws Exception {
        log.info("Begin method prepareEditDiscountGoods of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();

        Long discountModelMapId = this.discountModelMapForm.getDiscountModelMapId();
        DiscountModelMap discountModelMap =  getDiscountModelMapById(discountModelMapId);
        if (discountModelMap != null) {
            //
            this.discountModelMapForm.copyDataFromBO(discountModelMap);

            //
            getDataForComboboxDiscountGoods();

            //thiet lap che do sua thong tin
            req.setAttribute(REQUEST_IS_EDIT_MODE, true);

        }

        pageForward = DISCOUNT_GOODS;
        log.info("End method prepareEditDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 14/05/2009
     * desc     : them discountGoods moi hoac sua thong tin discount da co
     *
     */
    public String addOrEditDiscountGoods() throws Exception {
        log.info("Begin method addOrEditDiscountGoods of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        try {
            Long discountModelMapId = this.discountModelMapForm.getDiscountModelMapId();
            DiscountModelMap discountModelMap = getDiscountModelMapById(discountModelMapId);
            if (discountModelMap == null) {
                //----------------------------------------------------------------
                //truong hop them discount moi
                if (checkValidDiscountModelMapToAdd()) {
                    //luu vao CSDL
                    discountModelMap = new DiscountModelMap();
                    this.discountModelMapForm.copyDataToBO(discountModelMap);
                    discountModelMapId = getSequence("DISCOUNT_MODEL_MAP_SEQ");
                    discountModelMap.setDiscountModelMapId(discountModelMapId);
                    discountModelMap.setStatus(Constant.STATUS_USE);
                    session.save(discountModelMap);
                    session.flush();

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(null, discountModelMap));
                    saveLog(Constant.ACTION_SAVE_DISCOUNT_MODEL_MAP, "ACTION_SAVE_DISCOUNT_MODEL_MAP", lstLogObj,discountModelMap.getDiscountModelMapId(), Constant.DEFINE_DISCOUNT, Constant.ADD);

                    //
                    this.discountModelMapForm.setDiscountModelMapId(discountModelMapId);

                    //xac lap che do xem thong tin
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.addsuccessful");

                    //cap nhat lai list
                    List<DiscountModelMap> listDiscountModelMap = getListDiscountModelMap(session, discountModelMap.getDiscountGroupId());
                    req.setAttribute(REQUEST_LIST_DISCOUNT_GOODS, listDiscountModelMap);

                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_ADD_MODE, true);
                }

            } else {
                //----------------------------------------------------------------
                //truong hop cap nhat thong tin cua discount da co
                if (checkValidDiscountModelMapToEdit()) {
                    //
                    DiscountModelMap oldDiscountModelMap = new DiscountModelMap();
                    BeanUtils.copyProperties(oldDiscountModelMap, discountModelMap);

                    //luu vao CSDL
                    this.discountModelMapForm.copyDataToBO(discountModelMap);
                    session.update(discountModelMap);
                    session.flush();

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldDiscountModelMap, discountModelMap));
                    saveLog(Constant.ACTION_UPDATE_DISCOUNT_MODEL_MAP, "ACTION_UPDATE_DISCOUNT_MODEL_MAP", lstLogObj,discountModelMap.getDiscountModelMapId(), Constant.DEFINE_DISCOUNT, Constant.EDIT);

                    //xac lap che do xem thong tin
                    req.setAttribute(REQUEST_IS_VIEW_MODE, true);

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.editsuccessful");

                    //cap nhat lai list
                    List<DiscountModelMap> listDiscountModelMap = getListDiscountModelMap(session, discountModelMap.getDiscountGroupId());
                    req.setAttribute(REQUEST_LIST_DISCOUNT_GOODS, listDiscountModelMap);

                } else {
                    //xac lap che do chuan bi them discountGroup
                    req.setAttribute(REQUEST_IS_EDIT_MODE, true);
                }

            }

            //lay du lieu cho cac combobox
            getDataForComboboxDiscountGoods();

        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }
        
        //return
        pageForward = DISCOUNT_GOODS;
        log.info("End method addOrEditDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/04/2009
     * desc     : xoa thong tin discountModelMap
     *
     */
    public String delDiscountGoods() throws Exception {
        log.info("Begin method delDiscountGoods of DiscountGroupDAO ...");

        HttpServletRequest req = getRequest();
        Session session = getSession();

        try {
            //xoa thong tin trong CSDL
            Long discountModelMapId = this.discountModelMapForm.getDiscountModelMapId();
            DiscountModelMap discountModelMap = getDiscountModelMapById(discountModelMapId);
            if (discountModelMap != null) {
                //----------------------------------------------------------------
                //doan nay xoa dua tren contraint trong db, neu vi fam contraint se log ra exception
                try {
                    DiscountModelMap oldDiscountModelMap = new DiscountModelMap();
                    BeanUtils.copyProperties(oldDiscountModelMap, discountModelMap);

                    //cap nhat CSDL
//                    discountModelMap.setStatus(Constant.STATUS_DELETE);
//                    session.update(discountModelMap);
//                    session.flush();
                    session.delete(discountModelMap);
                    session.flush();

                    //ghi log
                    List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                    lstLogObj.add(new ActionLogsObj(oldDiscountModelMap, null));
                    saveLog(Constant.ACTION_DELETE_DISCOUNT_MODEL_MAP, "ACTION_DELETE_DISCOUNT_MODEL_MAP", lstLogObj,discountModelMap.getDiscountModelMapId(), Constant.DEFINE_DISCOUNT, Constant.DELETE);

                    //reset form
                    this.discountModelMapForm.resetForm();

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.delsuccessful");

                } catch (Exception ex) {
                    //rollback
                    session.clear();

                    //ghi log
                    ex.printStackTrace();

                    //hien thi message
                    req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.delete.discountModelMapIsUsing");

                }
                //----------------------------------------------------------------

            } else {
                //hien thi message
                req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.discountModelMapNotFound");

                //xac lap che do chuan bi them discountGroup
                req.setAttribute(REQUEST_IS_VIEW_MODE, true);

            }

            //cap nhat lai list
            List<DiscountModelMap> listDiscountModelMap = getListDiscountModelMap(session, discountModelMap.getDiscountGroupId());
            req.setAttribute(REQUEST_LIST_DISCOUNT_GOODS, listDiscountModelMap);

        } catch (Exception ex) {
            //rollback
            session.clear();

            //ghi log
            ex.printStackTrace();

            //hien thi message
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Exception: " + ex.toString());
        }

        //return
        pageForward = DISCOUNT_GOODS;
        log.info("End method delDiscountGoods of DiscountGroupDAO");
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * tra ve du lieu cho cay discountGroup
     *
     */
    public String getDiscountTree() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");

            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 0, tra ve danh sach cac discountGroup
                List listDiscountGroups = hbnSession.createCriteria(DiscountGroup.class).
                        //add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterDiscountGroup = listDiscountGroups.iterator();
                while (iterDiscountGroup.hasNext()) {
                    DiscountGroup discountGroup = (DiscountGroup) iterDiscountGroup.next();
                    String nodeId = "1_" + discountGroup.getDiscountGroupId().toString(); //them prefix 1_ de xac dinh la node level
                    String doString = httpServletRequest.getContextPath() + "/discountGroupAction!displayDiscountGroup.do?selectedDiscountGroupId=" + discountGroup.getDiscountGroupId().toString();
                    getListItems().add(new Node(discountGroup.getName(), "false", nodeId, doString));
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
     * author tamdt1
     * date: 18/04/2009
     * kiem tra tinh hop le cua 1 discountGroup truoc khi them discount group moi
     *
     */
    private boolean checkValidDiscountGroupToAdd() {
        HttpServletRequest req = getRequest();

        String strName = this.discountGroupForm.getName();
        String strDiscountPolicy = this.discountGroupForm.getDiscountPolicy();
        Long telecomServiceId = this.discountGroupForm.getTelecomServiceId();
        Long status = this.discountGroupForm.getStatus();
        Long discountMethod = this.discountGroupForm.getDiscountMethod();

        //kiem tra cac dieu kien cac truong bat buoc
        if (strName == null || strName.trim().equals("") ||
                strDiscountPolicy == null || strDiscountPolicy.trim().equals("") ||
                telecomServiceId == null || telecomServiceId.compareTo(0L) <= 0 ||
                status == null || status.compareTo(0L) < 0 ||
                discountMethod == null || discountMethod.compareTo(0L) < 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra du lieu co bi trung ten khong
        try {
            String strQuery = "select count(*) from DiscountGroup where lower(name) = ? ";

            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, strName.trim().toLowerCase());

            Long count = 0L;
            List list = query.list();
            if (list != null && list.size() > 0) {
                count = (Long) list.get(0);
            }

            if (count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.duplicateName");
                return false;
            }
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }

        this.discountGroupForm.setName(strName.trim());
        this.discountGroupForm.setDiscountPolicy(strDiscountPolicy.trim());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * kiem tra tinh hop le cua 1 discountGroup truoc khi sua thong tin discountgroup
     *
     */
    private boolean checkValidDiscountGroupToEdit() {
        HttpServletRequest req = getRequest();

        String strName = this.discountGroupForm.getName();
        String strDiscountPolicy = this.discountGroupForm.getDiscountPolicy();
        Long telecomServiceId = this.discountGroupForm.getTelecomServiceId();
        Long status = this.discountGroupForm.getStatus();
        Long discountMethod = this.discountGroupForm.getDiscountMethod();

        //kiem tra cac dieu kien cac truong bat buoc
        if (strName == null || strName.trim().equals("") ||
                strDiscountPolicy == null || strDiscountPolicy.trim().equals("") ||
                telecomServiceId == null || telecomServiceId.compareTo(0L) <= 0 ||
                status == null || status.compareTo(0L) < 0 ||
                discountMethod == null || discountMethod.compareTo(0L) < 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.requiredFieldsEmpty");
            return false;
        }

        Long discountGroupId = this.discountGroupForm.getDiscountGroupId();
        try {
            Session session = getSession();
            //kiem tra du lieu co bi trung ten khong
            String strQuery = "select count(*) from DiscountGroup " +
                    "where lower(name) = ? and discountGroupId <> ?";
            Query query = session.createQuery(strQuery);
            query.setParameter(0, strName.trim().toLowerCase());
            query.setParameter(1, discountGroupId);
            Long count = 0L;
            List list = query.list();
            if (list != null && list.size() > 0) {
                count = (Long) list.get(0);
            }
            if (count.compareTo(0L) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.duplicateName");
                return false;
            }

            //kiem tra viec thay doi chinh sach co bi trung mat hang thuoc nhom chiet khau khac khong
            List<DiscountModelMap> listDiscountModelMap = getListDiscountModelMap(session, discountGroupId);
            if (listDiscountModelMap != null && listDiscountModelMap.size() > 0) {
                for (int i = 0; i < listDiscountModelMap.size(); i++) {
                    DiscountModelMap tmpDiscountModelMap = listDiscountModelMap.get(i);
                    String strQuery_1 = "" +
                            "select count(*) " +
                            "from   DiscountModelMap a " +
                            "where  a.stockModelId = ? " +
                            "       and a.status = ? " +
                            "       and exists (" +
                            "               select  'X' " +
                            "               from    DiscountGroup b " +
                            "               where   b.discountGroupId <> ? " +
                            "                       and b.discountPolicy = ? " +
                            "                       and b.discountGroupId= a.discountGroupId" +
                            "               )";

                    Query query_1 = session.createQuery(strQuery_1);
                    query_1.setParameter(0, tmpDiscountModelMap.getStockModelId());
                    query_1.setParameter(1, Constant.STATUS_USE);
                    query_1.setParameter(2, discountGroupId);
                    query_1.setParameter(3, strDiscountPolicy);

                    Long count_1 = 0L;
                    List list_1 = query_1.list();
                    if (list_1 != null && list_1.size() > 0) {
                        count_1 = (Long) list_1.get(0);
                    }

                    if (count_1.compareTo(0L) > 0) {
                        req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.edit.duplicateStockModel");
                        return false;
                    }
                }
            }

            //kiem tra viec thay doi phuong thuc CK co bi trung mat hang thuoc nhom chiet khau khac khong



        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }

        this.discountGroupForm.setName(strName);
        this.discountGroupForm.setDiscountPolicy(strDiscountPolicy);

        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/04/2009
     * desc     : kiem tra tinh hop le cua 1 discountGroup truoc khi xoa
     *
     */
    private boolean checkValidDiscountGroupToDel() {
        HttpServletRequest req = getRequest();

        Long discountGroupId = this.discountGroupForm.getDiscountGroupId();
        Long count;

        //kiem tra dieu kien doi voi chi tiet CK
//        String strQuery = "select count(*) from Discount where discountGroupId = ? ";
        String strQuery = "select count(*) from Discount where discountGroupId = ? and status = ? "; //thay doi cau lenh do hien tai ko xoa thagn du lieu khoi DB
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, discountGroupId);
        query.setParameter(1, Constant.STATUS_ACTIVE);
        count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.delete.discountGroupIsUsing");
            return false;
        }

        //kiem tra dieu kien doi voi  cac mat hang thuoc nhom chiet khau nay
//        strQuery = "select count(*) from DiscountModelMap where discountGroupId = ? ";
        strQuery = "select count(*) from DiscountModelMap where discountGroupId = ? and status = ? "; //thay doi cau lenh do hien tai ko xoa thagn du lieu khoi DB
        query = getSession().createQuery(strQuery);
        query.setParameter(0, discountGroupId);
        query.setParameter(1, Constant.STATUS_ACTIVE);
        count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.delete.discountGroupIsUsing");
            return false;
        }

        //kiem tra dieu kien doi voi cac kenh an theo chiet khau nay
        strQuery = "select count(*) from DiscountChannelMap where discountGroupId = ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, discountGroupId);
        count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountgroup.error.delete.discountGroupIsUsing");
            return false;
        }

        return true;

    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * kiem tra tinh hop le cua 1 discount truoc khi them vao db
     *
     */
    private boolean checkValidDiscountToAdd() {
        HttpServletRequest req = getRequest();
        Long discountGroupId = this.discountForm.getDiscountGroupId();
        Long fromAmount = this.discountForm.getFromAmount();
        Long toAmount = this.discountForm.getToAmount();
        String type = this.discountForm.getType();
        String strStartDate = this.discountForm.getStartDatetime();
        String strEndDate = this.discountForm.getEndDatetime();
        String strDiscountRateNumerator = this.discountForm.getDiscountRateNumerator();
        String strDiscountRateDenominator = this.discountForm.getDiscountRateDenominator();
        Long discountAmount = this.discountForm.getDiscountAmount();

        if (fromAmount == null || toAmount == null ||
                type == null || type.trim().equals("") ||
                strStartDate == null || strStartDate.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.requiredFieldsEmpty");
            return false;
        }

        if (type.equals(Constant.DISCOUNT_TYPE_RATE)) {
            Double discountRateNumerator = 0.0;
            Double discountRateDenominator = 0.0;
            if (strDiscountRateNumerator == null || strDiscountRateNumerator.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.requiredFieldsEmpty");
                return false;
            } else {
                try {
                    discountRateNumerator = Double.parseDouble(strDiscountRateNumerator.trim());
                } catch (NumberFormatException ex) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDiscountRate");
                    return false;
                }
            }
            if (strDiscountRateDenominator == null || strDiscountRateDenominator.trim().equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.requiredFieldsEmpty");
                return false;
            } else {
                try {
                    discountRateDenominator = Double.parseDouble(strDiscountRateDenominator.trim());
                } catch (NumberFormatException ex) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDiscountRate");
                    return false;
                }
            }

            if (discountRateNumerator.compareTo(0.0) < 0
                    || discountRateDenominator.compareTo(0.0) <= 0
                    || (Double.valueOf(discountRateNumerator / discountRateDenominator)).compareTo(100.0) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDiscountRate");
                return false;
            }
        } else {
            if (discountAmount == null) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.requiredFieldsEmpty");
                return false;
            }
            if (discountAmount.compareTo(0L) < 0) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDiscountAmount");
                return false;
            }
        }

        if (fromAmount.compareTo(0L) < 0 || toAmount.compareTo(0L) < 0) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.toAmountNegative");
            return false;

        }

        if (fromAmount.compareTo(toAmount) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidAmount");
            return false;
        }

        //check trung khoang thoi gian
        Date startDate = null;
        try {
            startDate = DateTimeUtils.convertStringToDate(strStartDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDateFormat");
                return false;
            }
            if (startDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDate");
                return false;
            }
        }

        //kiem tra cac dieu kien trung lap tong so tien dam bao dieu kien:
        //doi voi 1 nhom chiet khau, 1 tong so tien ko duoc phep thuoc 2 khoang thoi gian khac nhau
        try {
            Long count;

            //kiem tra dieu kien doi voi "tu so tien"
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_fromAmount and toAmount >= :_fromAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_fromAmount and toAmount >= :_fromAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }

            //kiem tra dieu kien "den so tien"
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_toAmount and toAmount >= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_toAmount and toAmount >= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }

            //kiem tra dieu kien doi voi truong fromAmount co nam trong khoang so tien nhap vao khog
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount >= :_fromAmount and fromAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount >= :_fromAmount and fromAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }

            //kiem tra dieu kien doi voi truong toAmount co nam trong khoang so tien nhap vao khog
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where toAmount >= :_fromAmount and toAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where toAmount >= :_fromAmount and toAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * kiem tra tinh hop le cua 1 discount truoc thay doi thong tin trong db
     *
     */
    private boolean checkValidDiscountToEdit() {
        HttpServletRequest req = getRequest();

        Long fromAmount = this.discountForm.getFromAmount();
        Long toAmount = this.discountForm.getToAmount();
        String type = this.discountForm.getType();
//        Double discountRate = this.discountForm.getDiscountRate();
        Double discountRateNumerator = 0.0;
        Double discountRateDenominator = 0.0;
        if (this.discountForm.getDiscountRateNumerator() != null) {
            discountRateNumerator = Double.parseDouble(this.discountForm.getDiscountRateNumerator());
        }
        if (this.discountForm.getDiscountRateDenominator() != null) {
            discountRateDenominator = Double.parseDouble(this.discountForm.getDiscountRateDenominator());
        }
        Long discountAmount = this.discountForm.getDiscountAmount();
        String strStartDate = this.discountForm.getStartDatetime();
        String strEndDate = this.discountForm.getEndDatetime();
        Long discountGroupId = this.discountForm.getDiscountGroupId();
        Long discountId = this.discountForm.getDiscountId();

        if (fromAmount == null || toAmount == null ||
                type == null || type.trim().equals("") ||
                strStartDate == null || strStartDate.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.requiredFieldsEmpty");
            return false;
        }

        if (type.equals(Constant.DISCOUNT_TYPE_RATE)) {
            if (discountRateNumerator == null || discountRateDenominator == null) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.requiredFieldsEmpty");
                return false;
            }
            if (discountRateNumerator.compareTo(0.0) < 0 ||
                    discountRateDenominator.compareTo(0.0) <= 0 ||
                    (Double.valueOf(discountRateNumerator / discountRateDenominator)).compareTo(100.0) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDiscountRate");
                return false;
            }
        } else {
            if (discountAmount == null) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.requiredFieldsEmpty");
                return false;
            }
            if (discountAmount.compareTo(0L) < 0) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDiscountAmount");
                return false;
            }
        }

        if (fromAmount.compareTo(0L) < 0 || toAmount.compareTo(0L) < 0) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.toAmountNegative");
            return false;
        }

        if (fromAmount.compareTo(toAmount) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidAmount");
            return false;
        }

        //check trung khoang thoi gian
        Date startDate = null;
        try {
            startDate = DateTimeUtils.convertStringToDate(strStartDate.trim());
        } catch (Exception ex) {
            req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDateFormat");
            return false;
        }

        Date endDate = null;
        if (strEndDate != null && !strEndDate.trim().equals("")) {
            try {
                endDate = DateTimeUtils.convertStringToDate(strEndDate.trim());
            } catch (Exception ex) {
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDateFormat");
                return false;
            }
            if (startDate.compareTo(endDate) > 0) {
                //ngay bat dau khong duoc lon hon ngay ket thuc
                req.setAttribute(REQUEST_MESSAGE, "discount.error.invalidDate");
                return false;
            }
        }

        //kiem tra cac dieu kien trung lap tong so tien dam bao dieu kien:
        //doi voi 1 nhom chiet khau, 1 tong so tien ko duoc phep thuoc 2 khoang thoi gian khac nhau
        try {
            Long count;

            //kiem tra dieu kien doi voi "tu so tien"
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_fromAmount and toAmount >= :_fromAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_fromAmount and toAmount >= :_fromAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }

            //kiem tra dieu kien "den so tien"
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_toAmount and toAmount >= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount <= :_toAmount and toAmount >= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }

            //kiem tra dieu kien doi voi truong fromAmount co nam trong khoang so tien nhap vao khog
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount >= :_fromAmount and fromAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where fromAmount >= :_fromAmount and fromAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }

            //kiem tra dieu kien doi voi truong toAmount co nam trong khoang so tien nhap vao khog
            if (endDate != null) {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where toAmount >= :_fromAmount and toAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and (endDatetime >= :_startDate or endDatetime is null)) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime <= :_endDate and (endDatetime >= :_endDate or endDatetime is null)) " + //_endDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate and startDatetime <= :_endDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate and endDatetime <= :_endDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_endDate", endDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            } else {
                String strQuery = "select count(*) " +
                        "from Discount " +
                        "where toAmount >= :_fromAmount and toAmount <= :_toAmount " +
                        "and (" +
                        "          (startDatetime <= :_startDate and endDatetime is null) " + //_startDate roi vao khoang da co'
                        "       or (startDatetime >= :_startDate) " + //startDatetime roi vao khoang nhap vao
                        "       or (endDatetime >= :_startDate) " + //endDatetime roi vao khoang nhap vao
                        "    ) " +
                        "and status = :_status and discountGroupId = :_discountGroupId and discountId <> :_discountId ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter("_fromAmount", fromAmount);
                query.setParameter("_toAmount", toAmount);
                query.setParameter("_startDate", startDate);
                query.setParameter("_status", Constant.STATUS_USE);
                query.setParameter("_discountGroupId", discountGroupId);
                query.setParameter("_discountId", discountId);
                count = (Long) query.list().get(0);

                if (count != null && count.compareTo(0L) > 0) {
                    req.setAttribute(REQUEST_MESSAGE, "discount.error.duplicateTime");
                    return false;
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. " + ex.toString());
            return false;
        }
        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/04/2009
     * desc     : kiem tra tinh hop le cua 1 discount truoc khi xoa
     *
     */
    private boolean checkValidDiscountToDel() {
        HttpServletRequest req = getRequest();

        Long discountId = this.discountForm.getDiscountId();
        Long count;

        //kiem tra dieu kien doi voi chi tiet CK
        String strQuery = "select count(*) from SaleTransDetail where discountId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, discountId);
        count = (Long) query.list().get(0);
        if (count != null && count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountdetail.error.delete.discountDetailIsUsing");
            return false;
        }

        return true;

    }

    /**
     *
     * author   : tamdt1
     * date     : 14/05/2009
     * desc     : kiem tra tinh hop le cua mat hang truoc khi them vao nhom CK
     *
     */
    private boolean checkValidDiscountModelMapToAdd() {
        HttpServletRequest req = getRequest();
        Long stockTypeId = this.discountModelMapForm.getStockTypeId();
        String stockModelCode = this.discountModelMapForm.getStockModelCode();

        //kiem tra cac truong bat buoc
        if (stockTypeId == null || stockTypeId.compareTo(0L) < 0 ||
                stockModelCode == null || stockModelCode.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh hop le cua nhom CK
        Long discountGroupId = this.discountModelMapForm.getDiscountGroupId();
        discountGroupId = discountGroupId != null ? discountGroupId : -1L;
        DiscountGroup discountGroup = getDiscountGroupById(discountGroupId);
        if (discountGroup == null) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.discountGroupNotFound");
            return false;
        }

        //kiem tra tinh hop le cua ma mat hang
        StockModel stockModel = getStockModelByCode(stockTypeId, stockModelCode.trim());
        if (stockModel == null) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.stockModelCodeNotValid");
            return false;
        } else {
            this.discountModelMapForm.setStockModelId(stockModel.getStockModelId());
        }

        //kiem tra mat hang da thuoc nhom chiet khau khac chua
        String strQuery = "select count(*) from DiscountModelMap a " +
                "where a.stockModelId = ? and a.status = ? and exists " +
                " (select 'X' from DiscountGroup b where b.discountPolicy = ? and b.status = ? and b.discountGroupId= a.discountGroupId)";

        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModel.getStockModelId());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, discountGroup.getDiscountPolicy());
        query.setParameter(3, Constant.STATUS_USE);

        Long count = 0L;
        List list = query.list();
        if (list != null && list.size() > 0) {
            count = (Long) list.get(0);
        }

        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.duplicateStockModel");
            return false;
        }

        return true;
    }

    /**
     *
     * author   : tamdt1
     * date     : 14/05/2009
     * desc     : kiem tra tinh hop le cua mat hang truoc khi them vao nhom CK
     *
     */
    private boolean checkValidDiscountModelMapToEdit() {
        HttpServletRequest req = getRequest();
        Long stockTypeId = this.discountModelMapForm.getStockTypeId();
        String stockModelCode = this.discountModelMapForm.getStockModelCode();

        //kiem tra cac truong bat buoc
        if (stockTypeId == null || stockTypeId.compareTo(0L) < 0 ||
                stockModelCode == null || stockModelCode.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra tinh hop le cua nhom CK
        Long discountGroupId = this.discountModelMapForm.getDiscountGroupId();
        discountGroupId = discountGroupId != null ? discountGroupId : -1L;
        DiscountGroup discountGroup = getDiscountGroupById(discountGroupId);
        if (discountGroup == null) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.discountGroupNotFound");
            return false;
        }

        //kiem tra tinh hop le cua ma mat hang
        StockModel stockModel = getStockModelByCode(stockTypeId, stockModelCode);
        if (stockModel == null) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.stockModelCodeNotValid");
            return false;
        } else {
            this.discountModelMapForm.setStockModelId(stockModel.getStockModelId());
        }

        //kiem tra mat hang da thuoc nhom chiet khau khac chua
        String strQuery = "select count(*) from DiscountModelMap a " +
                "where a.stockModelId = ? and a.status = ? "
                + "and exists (select 'X' from DiscountGroup b where b.discountPolicy = ? and b.status = ? and b.discountGroupId= a.discountGroupId and b.discountGroupId <> ?)";

        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, stockModel.getStockModelId());
        query.setParameter(1, Constant.STATUS_USE);
        query.setParameter(2, discountGroup.getDiscountPolicy());
        query.setParameter(3, Constant.STATUS_USE);
        query.setParameter(4, discountGroupId);

        Long count = 0L;
        List list = query.list();
        if (list != null && list.size() > 0) {
            count = (Long) list.get(0);
        }

        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGE, "discountmodelmap.error.duplicateStockModel");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * lay discountGroup co id = discountGroupId
     *
     */
    private DiscountGroup getDiscountGroupById(Long discountGroupId) {
        DiscountGroup discountGroup = null;
        if (discountGroupId == null) {
            discountGroupId = -1L;
        }
        String strQuery = "from DiscountGroup where discountGroupId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, discountGroupId);
        List<DiscountGroup> listDiscountGroups = query.list();
        if (listDiscountGroups != null && listDiscountGroups.size() > 0) {
            discountGroup = listDiscountGroups.get(0);
        }

        return discountGroup;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * lay danh sach tat ca cac discountGroup
     *
     */
    private List<DiscountGroup> getListDiscountGroups() {
        List<DiscountGroup> listDiscountGroups = new ArrayList();
        String strQuery = "select new com.viettel.im.database.BO.DiscountGroup(a.discountGroupId, a.name, " +
                "a.status, a.notes, a.discountPolicy, a.telecomServiceId, " +
                "b.telServiceName, c.name) " +
                "from DiscountGroup a, TelecomService b, AppParams c " +
                "where a.telecomServiceId = b.telecomServiceId and a.discountPolicy = c.id.code " +
                "and c.id.type = ? order by nlssort(a.name,'nls_sort=Vietnamese') asc ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
        listDiscountGroups = query.list();
        return listDiscountGroups;
    }

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay discount co id = discountId tu DB
     *
     */
    private Discount getDiscountById(Long discountId) {
        Discount discount = null;

        if (discountId == null) {
            return discount;
        }

        String strQuery = "from Discount where discountId = ? and status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, discountId);
        query.setParameter(1, Constant.STATUS_USE);
        List<Discount> listDiscounts = query.list();
        if (listDiscounts != null && listDiscounts.size() > 0) {
            discount = listDiscounts.get(0);
        }

        return discount;
    }

    /**
     *
     * author tamdt1
     * date: 18/04/2009
     * lay danh sach tat ca cac dai tinh chiet khau cua 1 discountGroup
     *
     */
    private List<Discount> getListDiscount(Session session, Long discountGroupId) throws Exception {
        try {
            if (discountGroupId == null) {
                discountGroupId = -1L;
            }

            List<Discount> listDiscounts = new ArrayList<Discount>();
            String strQuery = "from Discount " +
                    "where discountGroupId = ? and status = ? " +
                    "order by fromAmount, startDatetime ";
            Query query = session.createQuery(strQuery);
            query.setParameter(0, discountGroupId);
            query.setParameter(1, Constant.STATUS_USE);
            listDiscounts = query.list();
            return listDiscounts;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay discount co id = discountId tu DB
     *
     */
    private DiscountModelMap getDiscountModelMapById(Long discountModelMapId) {
        DiscountModelMap discountModelMap = null;

        if (discountModelMapId == null) {
            return discountModelMap;
        }

        String strQuery = "" +
                "select new com.viettel.im.database.BO.DiscountModelMap(" +
                "       a.discountModelMapId, a.discountGroupId, a.stockModelId, a.staDate, a.endDate, a.status, " +
                "       b.stockModelCode, b.name, c.stockTypeId, c.name)  " +
                "from   DiscountModelMap a, StockModel b, StockType c " +
                "where  a.stockModelId = b.stockModelId " +
                "       and b.stockTypeId = c.stockTypeId " +
                "       and a.discountModelMapId = ? and a.status = ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, discountModelMapId);
        query.setParameter(1, Constant.STATUS_USE);
        List<DiscountModelMap> listDiscountModelMap = query.list();
        if (listDiscountModelMap != null && listDiscountModelMap.size() > 0) {
            discountModelMap = listDiscountModelMap.get(0);
        }

        return discountModelMap;
    }

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay discount co discountGroupId = discountGroupId tu DB
     *
     */
    private List<DiscountModelMap> getListDiscountModelMap(Session sesion, Long discountGroupId) {
        List<DiscountModelMap> listDiscountModelMap = new ArrayList<DiscountModelMap>();

        if (discountGroupId == null) {
            return listDiscountModelMap;
        }

        String strQuery = "" +
                "select new com.viettel.im.database.BO.DiscountModelMap(" +
                "       a.discountModelMapId, a.discountGroupId, a.stockModelId, a.staDate, a.endDate, a.status, " +
                "       b.stockModelCode, b.name, c.stockTypeId, c.name)  " +
                "from   DiscountModelMap a, StockModel b, StockType c " +
                "where  a.stockModelId = b.stockModelId " +
                "       and b.stockTypeId = c.stockTypeId " +
                "       and a.discountGroupId = ? and a.status = ?";
        Query query = sesion.createQuery(strQuery);
        query.setParameter(0, discountGroupId);
        query.setParameter(1, Constant.STATUS_USE);
        listDiscountModelMap = query.list();

        return listDiscountModelMap;
    }

    /**
     *
     * author andv
     * date: 26/02/2009
     * lay StockModel co code = stockModeCode
     *
     */
    private StockModel getStockModelByCode(Long stockTypeId, String stockModelCode) {
        StockModel stockModel = null;
        if (stockModelCode != null || !stockModelCode.trim().equals("")) {

            String strQuery = "from StockModel where lower(stockModelCode) = ? and status = ? and stockTypeId = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, stockModelCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, stockTypeId);
            List<StockModel> listStockTypes = query.list();
            if (listStockTypes != null && listStockTypes.size() > 0) {
                stockModel = listStockTypes.get(0);

                if (stockModel.getUnit() != null && !stockModel.getUnit().trim().equals("")) {

                    AppParamsDAO appParamsDAO = new AppParamsDAO();
                    appParamsDAO.setSession(this.getSession());
                    AppParams appParams = appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());
                    if (appParams != null) {
                        stockModel.setUnitName(appParams.getName());
                    }
                }
            }
        }

        return stockModel;
    }

    /**
     *
     * author tamdt1
     * date: 23/04/2009
     * lay du lieu cho cac combobox (du lieu duoc thiet lap len cac bien request)
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();
        //lay danh sach cac chinh sach chiet khau
        req.setAttribute(REQUEST_LIST_DISCOUNT_POLICY, getListDiscountPolicy());
        //lay danh sach cac dich vu VT
        req.setAttribute(REQUEST_LIST_TELECOM_SERVICES, getListTelecomServices());

    }

    /**
     *
     * author tamdt1
     * date: 23/04/2009
     * lay du lieu cho cac combobox (du lieu duoc thiet lap len cac bien request)
     *
     */
    private void getDataForComboboxDiscountGoods() {
        HttpServletRequest req = getRequest();
        //lay danh sach cac loai mat hang
        req.setAttribute(REQUEST_LIST_STOCK_TYPE, getListStockType());

    }

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay danh sach tat ca cac chinh sach chiet khau
     *
     */
    private List<AppParams> getListDiscountPolicy() {
        List<AppParams> listAppParams = new ArrayList<AppParams>();
        try {
//            String strQuery = "from AppParams where id.type = ? and status = ? order by name";
//            Query query = getSession().createQuery(strQuery);
//            query.setParameter(0, Constant.APP_PARAMS_DISCOUNT_POLICY);
//            query.setParameter(1, String.valueOf(Constant.STATUS_USE));
//            listAppParams = query.list();

            //danh sach chinh sach chiet khau
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            listAppParams = appParamsDAO.findAppParamsByType_1(Constant.APP_PARAMS_DISCOUNT_POLICY);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listAppParams;
    }

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay danh sach tat ca cac dich vu vien thong
     *
     */
    private List<TelecomService> getListTelecomServices() {
        List<TelecomService> listTelecomServices = new ArrayList<TelecomService>();
        try {
            String strQuery = "from TelecomService where status = ? order by telServiceName";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, Constant.STATUS_USE);
            listTelecomServices = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listTelecomServices;
    }

    /**
     *
     * author tamdt1
     * date: 22/04/2009
     * lay danh sach tat ca loai mat hang
     *
     */
    private List<StockType> getListStockType() {
        List<StockType> listStockType = new ArrayList<StockType>();
        try {
            String strQuery = "from StockType where status = ? order by name ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, Constant.STATUS_USE);
            listStockType = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return listStockType;
    }


    //tamdt1 - end
    //----------------------------------------------------------------
    public void save(DiscountGroup transientInstance) {
        log.debug("saving DiscountGroup instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(DiscountGroup persistentInstance) {
        log.debug("deleting DiscountGroup instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public DiscountGroup findById(Long id) {
        log.debug("getting DiscountGroup instance with id: " + id);
        try {
            DiscountGroup instance = (DiscountGroup) getSession().get(
                    "com.viettel.im.database.BO.DiscountGroup", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(DiscountGroup instance) {
        log.debug("finding DiscountGroup instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.DiscountGroup").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding DiscountGroup instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from DiscountGroup as model where model." +com.viettel.security.util.StringEscapeUtils.getSafeFieldName( propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByName(Object name) {
        return findByProperty("name", name);
    }

    public List findByStatus(Object status) {
        return findByProperty("status", status);
    }

    public List findByNotes(Object notes) {
        return findByProperty("notes", notes);
    }

    public List findAll() {
        log.debug("finding all DiscountGroup instances");
        try {
            String queryString = "from DiscountGroup";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public DiscountGroup merge(DiscountGroup detachedInstance) {
        log.debug("merging DiscountGroup instance");
        try {
            DiscountGroup result = (DiscountGroup) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(DiscountGroup instance) {
        log.debug("attaching dirty DiscountGroup instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(DiscountGroup instance) {
        log.debug("attaching clean DiscountGroup instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}
