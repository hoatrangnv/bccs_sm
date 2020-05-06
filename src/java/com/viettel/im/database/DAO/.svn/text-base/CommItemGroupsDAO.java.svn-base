package com.viettel.im.database.DAO;

import com.viettel.im.database.BO.CommItemGroups;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.CommItemGroupsForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.CommItems;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * A data access object (DAO) providing persistence and search support for
 * CommItemGroups entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.DAO.CommItemGroups
 * @author MyEclipse Persistence Tools
 */
public class CommItemGroupsDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(CommItemGroupsDAO.class);
    private String pageForward;
    //khai bao cac bien session, request
    private final String SESSION_LIST_COMM_ITEMS = "listCommItems";
    private final String SESSION_TO_DETAIL_COMM_ITEM_GROUP = "toDetail";
    private final String SESSION_LIST_COMM_ITEM_GROUP = "listCommItemGroup";
    private final String SESSION_CURRENT_ITEM_ID = "sessionCurrentItemId";
    private final String SESSION_CURRENT_ITEM_GROUP_ID = "currentItemGroupId";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_LIST_PARRENT_ITEM_GROUPS = "listParentItemGroups";
    private final String REQUEST_COMM_ITEM_GROUPS_MODE = "commItemGroupsMode"; //che do hien thi, view, add, edit
    //cac hang so foward
    private final String LIST_GROUP_COMM_ITEM = "listGroupCommItem";
    private final String GET_COMM_ITEM_GROUPS_TREE = "getCommItemGroupsTree";
    private final String DETAIL_COMM_ITEM_GROUPS = "detailCommItemsGroups";
    // property constants
    public static final String PARENT_ITEM_GROUP_ID = "parentItemGroupId";
    public static final String GROUP_NAME = "groupName";
    public static final String REPORT_TYPE = "reportType";
    public static final String STATUS = "status";
    public static final String ADD_NEW_COMM_ITEM_GROUPS = "addNewCommItemGroups";
    public static final String CREATE_COMM_GROUP_INTERFACE = "3";
    public static final String VIEW_TYPE = "view";
    public static final String NEW_TYPE = "new";
    public static final String UPDATE_TYPE = "update";
    public static final String DELETE_TYPE = "delete";
    public static final String STATUS_DELETE = "2";
    //khai bao bien form
    private CommItemGroupsForm commItemGroupsForm = new CommItemGroupsForm();

    public CommItemGroupsForm getCommItemGroupsForm() {
        return commItemGroupsForm;
    }

    public void setCommItemGroupsForm(CommItemGroupsForm commItemGroupsForm) {
        this.commItemGroupsForm = commItemGroupsForm;
    }

    //--------------------------------------------------------------------------------
    /**
     *
     * man hinh dau tien phan khai bao nhom khoan muc
     *
     */
    public String preparePage() throws Exception {
        log.info("begin method preparePage of CommItemGroupsDAO...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        req.getSession().setAttribute(SESSION_LIST_COMM_ITEMS, null);
        req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, null);

        if (userToken != null) {
            try {
                //tim danh sach tat ca cac nhom khoan muc cap 1
                List listCommItemGroups = findAllNotDelete(0L);
                req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, listCommItemGroups);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("end method preparePage of CommItemGroupsDAO");

        pageForward = ADD_NEW_COMM_ITEM_GROUPS;
        req.getSession().removeAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP);


        return pageForward;
    }

    /**
     *
     * tamdt1, 28/05/2009
     * hien thi danh sach cac nhom khoan muc
     *
     */
    public String listCommItemGroups() throws Exception {
        log.info("begin method listCommItemGroups of CommItemGroupsDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                //
                req.getSession().setAttribute(SESSION_CURRENT_ITEM_GROUP_ID, -1L);

                //tim danh sach tat ca cac nhom khoan muc cap 1
                List listCommItemGroups = findAllNotDelete(0L);
                req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, listCommItemGroups);
                req.getSession().removeAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP);
                req.getSession().setAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP, 0);

                pageForward = LIST_GROUP_COMM_ITEM;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("end method listCommItemGroups of CommItemGroupsDAO");
        return pageForward;
    }

    /**
     *
     * tamdt1, 28/05/2009
     * phuc vu viec phan trang
     *
     */
    public String pageNavigatorCommItemGroup() throws Exception {
        log.info("Begin method pageNavigatorCommItemGroup of CommItemGroupsDAO ...");
        HttpServletRequest req = getRequest();
        pageForward = LIST_GROUP_COMM_ITEM;
        req.getSession().removeAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP);
        req.getSession().setAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP, 0);

        log.info("End method pageNavigatorCommItemGroup of CommItemGroupsDAO");
        return pageForward;
    }

    /**
     *
     * hien thi thong tin ve nhom khoan muc
     *
     */
    public String displayCommItemGroups() throws Exception {
        log.info("begin method displayCommItemGroups of CommItemGroupsDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                Long itemGroupId = -1L;

                String strCommItemGroupsId = QueryCryptUtils.getParameter(req, "selectedCommItemGroupsId");
                if (strCommItemGroupsId != null && !strCommItemGroupsId.trim().equals("")) {
                    itemGroupId = Long.valueOf(strCommItemGroupsId);
                } else {
                    itemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                }

                CommItemGroups commItemGroups = getCommItemGroupsById(itemGroupId);
                if (commItemGroups != null) {
                    //thiet lap len bien session
                    req.getSession().setAttribute(SESSION_CURRENT_ITEM_GROUP_ID, itemGroupId);

                    this.commItemGroupsForm.copyDataFromBO(commItemGroups);

                    //thiet lap du lieu cho cac combobox
                    List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
                    CommItemGroups parentCommItemGroups = getCommItemGroupsById(commItemGroups.getParentItemGroupId());
                    if (parentCommItemGroups != null) {
                        listParentItemGroups.add(parentCommItemGroups);
                    }
                    req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);


                    List listCommItemGroups_1 = findAllNotDelete(itemGroupId);
                    if (listCommItemGroups_1 != null && listCommItemGroups_1.size() > 0) {
                        //lay danh sach nhom khoan muc con cua khoan muc hien hanh
                        req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, listCommItemGroups_1);
                        req.getSession().setAttribute(SESSION_LIST_COMM_ITEMS, null);
                        pageForward = DETAIL_COMM_ITEM_GROUPS;
                    } else {
                        //lay danh sach cac khoan muc thuoc nhom khoan muc nay
                        CommItemsDAO commItemsDAO = new CommItemsDAO();
                        commItemsDAO.setSession(this.getSession());
                        List<CommItems> listCommItems = commItemsDAO.findNotDeleteByItemGroupId(itemGroupId);
                        req.getSession().setAttribute(SESSION_LIST_COMM_ITEMS, listCommItems);
                        req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, null);
                        pageForward = DETAIL_COMM_ITEM_GROUPS;

                    }
                } else {
                    //tim danh sach tat ca cac nhom khoan muc cap 1
                    List listCommItemGroups = findAllNotDelete(0L);
                    req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, listCommItemGroups);
                    req.getSession().removeAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP);
                    req.getSession().setAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP, 0);
                    //
                    pageForward = LIST_GROUP_COMM_ITEM;
                }

                //xoa bien session khoan muc hien tai
                req.getSession().setAttribute(SESSION_CURRENT_ITEM_ID, null);

            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method displayCommItemGroups of CommItemGroupsDAO");

        return pageForward;
    }

    /**
     *
     * chuan bi form them itemGroup moi
     *
     */
    public String prepareAddCommItemGroups() throws Exception {
        log.info("begin method prepareAddCommItemGroups of CommItemGroupsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                Long itemGroupId = this.commItemGroupsForm.getItemGroupId();
                CommItemGroups commItemGroups = getCommItemGroupsById(itemGroupId);
                if (commItemGroups != null) {
                    this.commItemGroupsForm.resetForm(); //

                    //thiet lap du lieu cho cac combobox
                    List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
                    CommItemGroups parentCommItemGroups = getCommItemGroupsById(commItemGroups.getParentItemGroupId());
                    if (parentCommItemGroups != null) {
                        listParentItemGroups.add(parentCommItemGroups); //them nhom khoan muc cung cap voi nhom khoan muc hien tai
                        this.commItemGroupsForm.setParentItemGroupId(parentCommItemGroups.getItemGroupId());
                    }
                    listParentItemGroups.add(commItemGroups); //them nhom khoan muc cap con cua nhom khoan muc hien tai
                    req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);

                }

                //reset danh sach cac khoan muc thuoc nhom khoan muc nay
                req.getSession().setAttribute(SESSION_LIST_COMM_ITEMS, new ArrayList());

                //thiet lap che do chuan bi sua thong tin nhom khoan muc
                req.setAttribute(REQUEST_COMM_ITEM_GROUPS_MODE, "prepareAddOrEdit");
                req.setAttribute("toEditCommItemGroups", "toAdd");
                req.getSession().removeAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP);
                req.getSession().setAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP, 1);
                pageForward = DETAIL_COMM_ITEM_GROUPS;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method prepareAddCommItemGroups of CommItemGroupsDAO");

        return pageForward;
    }

    /**
     *
     * chuan bi form sua thong tin cua itemGroup
     *
     */
    public String prepareEditCommItemGroups() throws Exception {
        log.info("begin method prepareEditCommItemGroups of CommItemGroupsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                Long itemGroupId = this.commItemGroupsForm.getItemGroupId();
                CommItemGroups commItemGroups = getCommItemGroupsById(itemGroupId);
                if (commItemGroups != null) {
                    this.commItemGroupsForm.copyDataFromBO(commItemGroups);

                    //thiet lap du lieu cho cac combobox
                    List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
                    CommItemGroups parentCommItemGroups = getCommItemGroupsById(commItemGroups.getParentItemGroupId());
                    if (parentCommItemGroups != null) {
                        listParentItemGroups.add(parentCommItemGroups);
                    }
                    req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);

                    //thiet lap che do chuan bi sua thong tin nhom khoan muc
                    req.setAttribute(REQUEST_COMM_ITEM_GROUPS_MODE, "prepareAddOrEdit");
                    req.setAttribute("toEditCommItemGroups", "toEdit");
                    pageForward = DETAIL_COMM_ITEM_GROUPS;

                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }


        log.info("end method prepareEditCommItemGroups of CommItemGroupsDAO");

        return pageForward;
    }

    /**
     *
     * them hoac sua thong tin commItemGroups
     *
     */
    public String addOrEditCommItemGroups() throws Exception {
        log.info("begin method addOrEditCommItemGroups of CommItemGroupsDAO...");

        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

        if (userToken != null) {
            try {
                Long itemGroupId = this.commItemGroupsForm.getItemGroupId();
                CommItemGroups commItemGroups = getCommItemGroupsById(itemGroupId);
                if (commItemGroups == null) {
                    //truong hop them CommItemGroups moi
                    if (!checkValidCommItemGroupsToAdd()) {

                        //thiet lap du lieu cho cac combobox
                        Long currentItemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                        CommItemGroups currentCommItemGroups = getCommItemGroupsById(currentItemGroupId);
                        if (currentCommItemGroups != null) {
                            List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
                            CommItemGroups parentCommItemGroups = getCommItemGroupsById(currentCommItemGroups.getParentItemGroupId());
                            if (parentCommItemGroups != null) {
                                listParentItemGroups.add(parentCommItemGroups); //them nhom khoan muc cung cap voi nhom khoan muc hien tai
                                this.commItemGroupsForm.setParentItemGroupId(parentCommItemGroups.getItemGroupId());
                            }
                            listParentItemGroups.add(currentCommItemGroups); //them nhom khoan muc cap con cua nhom khoan muc hien tai
                            req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);

                        }

                        //thiet lap che do chuan bi sua thong tin nhom khoan muc
                        req.setAttribute(REQUEST_COMM_ITEM_GROUPS_MODE, "prepareAddOrEdit");

                        log.info("end method addOrEditCommItemGroups of CommItemGroupsDAO");
                        pageForward = DETAIL_COMM_ITEM_GROUPS;

                        return pageForward;

                    }

                    commItemGroups = new CommItemGroups();
//                    this.commItemGroupsForm.copyDataToBO(commItemGroups);
//                    itemGroupId = getSequence("COMM_ITEM_GROUPS_SEQ");
//                    commItemGroups.setItemGroupId(itemGroupId);
//                    commItemGroups.setCreateDate(new Date());
//                    getSession().save(commItemGroups);

                    //
                    this.commItemGroupsForm.setItemGroupId(itemGroupId);

                    //
                    req.getSession().setAttribute(SESSION_CURRENT_ITEM_GROUP_ID, itemGroupId);
                    req.setAttribute(REQUEST_MESSAGE, "commItemGroups.create.success");

                    //thiet lap du lieu cho cac combobox
//                    Long currentItemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
//                    CommItemGroups currentCommItemGroups = getCommItemGroupsById(currentItemGroupId);
//                    if (currentCommItemGroups != null) {
//                        List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
//                        CommItemGroups parentCommItemGroups = getCommItemGroupsById(currentCommItemGroups.getParentItemGroupId());
//                        if (parentCommItemGroups != null) {
//                            listParentItemGroups.add(parentCommItemGroups); //them nhom khoan muc cung cap voi nhom khoan muc hien tai
//                            this.commItemGroupsForm.setParentItemGroupId(parentCommItemGroups.getItemGroupId());
//                        } else {
//                            this.commItemGroupsForm.setParentItemGroupId(null);
//                        }
//                        req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);
//
//
//                    } else {
//                        this.commItemGroupsForm.setParentItemGroupId(null);
//                    }
                    //tuannv,chuyen xuong phia duoi de lay gia tri parentItemGroup moi
                    this.commItemGroupsForm.copyDataToBO(commItemGroups);
                    itemGroupId = getSequence("COMM_ITEM_GROUPS_SEQ");
                    commItemGroups.setItemGroupId(itemGroupId);
                    if (this.commItemGroupsForm.getParentItemGroupId() != 0) {
                        commItemGroups.setParentItemGroupId(this.commItemGroupsForm.getParentItemGroupId());
                    }
                    commItemGroups.setCreateDate(new Date());
                    getSession().save(commItemGroups);
                    this.commItemGroupsForm.setItemGroupId(itemGroupId);
                    req.getSession().removeAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                    req.getSession().setAttribute(SESSION_CURRENT_ITEM_GROUP_ID, itemGroupId);

                } else {
                    //truong hop sua thong tin CommItemGroups da co
                    if (!checkValidCommItemGroupsToEdit()) {
                        //thiet lap du lieu cho cac combobox
                        Long currentItemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                        CommItemGroups currentCommItemGroups = getCommItemGroupsById(currentItemGroupId);
                        if (currentCommItemGroups != null) {
                            List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
                            CommItemGroups parentCommItemGroups = getCommItemGroupsById(currentCommItemGroups.getParentItemGroupId());
                            if (parentCommItemGroups != null) {
                                listParentItemGroups.add(parentCommItemGroups);
                                this.commItemGroupsForm.setParentItemGroupId(parentCommItemGroups.getItemGroupId());
                            } else {
                                this.commItemGroupsForm.setParentItemGroupId(null);
                            }
                            req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);

                        }

                        //thiet lap che do chuan bi sua thong tin nhom khoan muc
                        req.setAttribute(REQUEST_COMM_ITEM_GROUPS_MODE, "prepareAddOrEdit");

                        log.info("end method addOrEditCommItemGroups of CommItemGroupsDAO");
                        pageForward = DETAIL_COMM_ITEM_GROUPS;

                        return pageForward;
                    }


                    this.commItemGroupsForm.copyDataToBO(commItemGroups);
                    getSession().update(commItemGroups);

                    //
                    req.setAttribute(REQUEST_MESSAGE, "commItemGroups.edit.success");

                    //thiet lap du lieu cho cac combobox
                    Long currentItemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                    CommItemGroups currentCommItemGroups = getCommItemGroupsById(currentItemGroupId);
                    if (currentCommItemGroups != null) {
                        List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
                        CommItemGroups parentCommItemGroups = getCommItemGroupsById(currentCommItemGroups.getParentItemGroupId());
                        if (parentCommItemGroups != null) {
                            listParentItemGroups.add(parentCommItemGroups); //them nhom khoan muc cung cap voi nhom khoan muc hien tai
                            this.commItemGroupsForm.setParentItemGroupId(parentCommItemGroups.getItemGroupId());
                        }
                        req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("end method addOrEditCommItemGroups of CommItemGroupsDAO");
        pageForward = DETAIL_COMM_ITEM_GROUPS;
        List listCommItemGroups = findAllNotDelete(0L);
        req.getSession().removeAttribute(SESSION_LIST_COMM_ITEM_GROUP);
        req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, listCommItemGroups);

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * kiem tra cac dieu kien hop le truoc khi them khoan muc vao CSDL
     *
     */
    private boolean checkValidCommItemGroupsToAdd() {
        HttpServletRequest req = getRequest();

        String strGroupName = this.commItemGroupsForm.getGroupName();
        String strReportType = this.commItemGroupsForm.getReportType();
        String status = this.commItemGroupsForm.getStatus();
        Long parentItemGroupId = this.commItemGroupsForm.getParentItemGroupId();

        //kiem tra cac truong bat buoc phai nhap
        if (strGroupName == null || strGroupName.trim().equals("") ||
                strReportType == null || strReportType.trim().equals("") ||
                status == null || status.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "commItemGroups.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra trung lap ten
        String strQuery = "from CommItemGroups where lower(groupName) = ? " +
                "and parentItemGroupId = ? and status <> ?";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, strGroupName.trim().toLowerCase());
        query.setParameter(1, parentItemGroupId);
        query.setParameter(2, String.valueOf(Constant.STATUS_DELETE));
        List<CommItemGroups> listCommItemGroups = query.list();
        if (listCommItemGroups != null && listCommItemGroups.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "commItemGroups.error.duplicateGroupName");
            return false;
        }

        //loai bo cac khoang trang o dau va cuoi xau du lieu nhap vao
        this.commItemGroupsForm.setGroupName(strGroupName.trim());

        return true;
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * kiem tra cac dieu kien hop le truoc khi sua thong tin ve nhom khoan muc
     *
     */
    private boolean checkValidCommItemGroupsToEdit() {
        HttpServletRequest req = getRequest();

        String strGroupName = this.commItemGroupsForm.getGroupName();
        String strReportType = this.commItemGroupsForm.getReportType();
        String status = this.commItemGroupsForm.getStatus();
        Long parentItemGroupId = this.commItemGroupsForm.getParentItemGroupId();
        Long itemGroupId = this.commItemGroupsForm.getItemGroupId();

        //kiem tra cac truong bat buoc phai nhap
        if (strGroupName == null || strGroupName.trim().equals("") ||
                strReportType == null || strReportType.trim().equals("") ||
                status == null || status.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGE, "commItemGroups.error.requiredFieldsEmpty");
            return false;
        }

        //kiem tra trung lap ten
        String strQuery = "from CommItemGroups where lower(groupName) = ? " +
                "and parentItemGroupId = ? and status <> ? and itemGroupId <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, strGroupName.trim().toLowerCase());
        query.setParameter(1, parentItemGroupId);
        query.setParameter(2, String.valueOf(Constant.STATUS_DELETE));
        query.setParameter(3, itemGroupId);
        List<CommItemGroups> listCommItemGroups = query.list();
        if (listCommItemGroups != null && listCommItemGroups.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "commItemGroups.error.duplicateGroupName");
            return false;
        }

        //loai bo cac khoang trang o dau va cuoi xau du lieu nhap vao
        this.commItemGroupsForm.setGroupName(strGroupName.trim());

        return true;
    }

    /**
     *
     * xoa nhom khoan muc
     *
     */
    public String deleteCommItemGroups() throws Exception {
        log.info("begin method deleteCommItemGroups of CommItemGroupsDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                Long itemGroupId = this.commItemGroupsForm.getItemGroupId();
                CommItemGroups commItemGroups = getCommItemGroupsById(itemGroupId);
                if (commItemGroups == null) {
                    req.setAttribute(REQUEST_MESSAGE, "commItemGroups.delete.error.commItemGroupsNotExist");
                    log.info("end method addOrEditCommItemGroups of CommItemGroupsDAO");
                    pageForward = DETAIL_COMM_ITEM_GROUPS;

                    return pageForward;
                }

                if (!checkValidCommItemGroupsToDelete()) {
                    //thiet lap du lieu cho cac combobox
                    Long currentItemGroupId = (Long) req.getSession().getAttribute(SESSION_CURRENT_ITEM_GROUP_ID);
                    CommItemGroups currentCommItemGroups = getCommItemGroupsById(currentItemGroupId);
                    if (currentCommItemGroups != null) {
                        List<CommItemGroups> listParentItemGroups = new ArrayList<CommItemGroups>();
                        CommItemGroups parentCommItemGroups = getCommItemGroupsById(currentCommItemGroups.getParentItemGroupId());
                        if (parentCommItemGroups != null) {
                            listParentItemGroups.add(parentCommItemGroups);
                            this.commItemGroupsForm.setParentItemGroupId(parentCommItemGroups.getItemGroupId());
                        }
                        req.setAttribute(REQUEST_LIST_PARRENT_ITEM_GROUPS, listParentItemGroups);

                    }

                    //thiet lap lai len form(do mot so component bi disable khong con giu duoc gia tri)
                    this.commItemGroupsForm.copyDataFromBO(commItemGroups);

                    log.info("end method deleteCommItemGroups of CommItemGroupsDAO");
                    pageForward = DETAIL_COMM_ITEM_GROUPS;

                    return pageForward;
                }

                //update trang thai ve xoa
                commItemGroups.setStatus(String.valueOf(Constant.STATUS_DELETE));
                getSession().update(commItemGroups);

                //tim danh sach cac nhom khoan muc cung cap voi nhom vua bi xoa
                //
                req.getSession().setAttribute(SESSION_CURRENT_ITEM_GROUP_ID, -1L);

                //tim danh sach tat ca cac nhom khoan muc cap 1
                List listCommItemGroups = findAllNotDelete(commItemGroups.getParentItemGroupId());
                req.getSession().setAttribute(SESSION_LIST_COMM_ITEM_GROUP, listCommItemGroups);
                req.getSession().removeAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP);
                req.getSession().setAttribute(SESSION_TO_DETAIL_COMM_ITEM_GROUP, 0);
                pageForward = LIST_GROUP_COMM_ITEM;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }


        log.info("end method deleteCommItemGroups of CommItemGroupsDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 29/05/2009
     * kiem tra cac dieu kien hop le truoc khi xoa thong tin ve nhom khoan muc
     *
     */
    private boolean checkValidCommItemGroupsToDelete() {
        HttpServletRequest req = getRequest();

        Long itemGroupId = this.commItemGroupsForm.getItemGroupId();

        //kiem tra nhom khoan muc co dang duoc su dung hay khong (co' ton tai nhom khoan muc con hay khong)
        String strQuery = "from CommItemGroups where parentItemGroupId = ? and status <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, itemGroupId);
        query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        List<CommItemGroups> listCommItemGroups = query.list();
        if (listCommItemGroups != null && listCommItemGroups.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "commItemGroups.delete.error.commItemGroupsIsUsing");
            return false;
        }

        //kiem tra co' ton tai khoan muc nao thuoc nhom khoan muc nay hay khong
        strQuery = "from CommItems where itemGroupId = ? and status <> ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, itemGroupId);
        query.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        listCommItemGroups = query.list();
        if (listCommItemGroups != null && listCommItemGroups.size() > 0) {
            req.setAttribute(REQUEST_MESSAGE, "commItemGroups.delete.error.commItemGroupsIsUsing");
            return false;
        }

        return true;
    }

    /**
     *
     * tamdt1, 28/05/2009
     * lay danh sach tat ca cac nhom khoan muc khong o trang thai Xoa co parentId = parentItemGroupId
     *
     */
    public List<CommItemGroups> findAllNotDelete(Long parentItemGroupId) {
        log.debug("finding all CommItemGroups instances");

        if (parentItemGroupId == null) {
            return new ArrayList<CommItemGroups>();
        }

        try {
            String queryString = "";
            if (parentItemGroupId != null && parentItemGroupId.compareTo(0L) > 0) {
                queryString = "from CommItemGroups where parentItemGroupId = ? and status <> ? " +
                        "order by nlssort(groupName,'nls_sort=Vietnamese') asc";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, parentItemGroupId);
                queryObject.setParameter(1, Constant.STATUS_DELETE.toString());
                return queryObject.list();
            } else {
                queryString = "from CommItemGroups where parentItemGroupId is null and status <> ? " +
                        "order by nlssort(groupName,'nls_sort=Vietnamese') asc";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, Constant.STATUS_DELETE.toString());
                return queryObject.list();
            }
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
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
    //khai bao bien tra lai ket qua cho tree
    private List listItems = new ArrayList();

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    /**
     *
     *
     * tra ve danh sach cay khoan muc can hien thi thong tin
     *
     */
    public String getCommItemGroupsTree() throws Exception {
        log.info("begin method getCommItemGroupsTree of CommItemGroupsDAO...");
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");
            if (node.indexOf("0_") == 0) {
                node = "0"; //mac dinh nhom khoan muc o muc cao nhat se co parent = 0
                List listItemMainGroup = new ArrayList();

                //chon nhung nhom khoan muc chua bi xoa
                listItemMainGroup = hbnSession.createCriteria(CommItemGroups.class).
                        //                        add(Restrictions.eq("parentItemGroupId", Long.parseLong(node))).
                        add(Restrictions.isNull("parentItemGroupId")).
                        add(Restrictions.ne("status", Constant.STATUS_DELETE.toString())).
                        addOrder(Order.asc("groupName")).
                        list();

                Iterator iterSaleServices = listItemMainGroup.iterator();
                while (iterSaleServices.hasNext()) {
                    CommItemGroups commGroup = (CommItemGroups) iterSaleServices.next();
                    String nodeGroupId = "1_" + commGroup.getItemGroupId().toString();
                    getListItems().add(new Node(commGroup.getGroupName(), "true", nodeGroupId, httpServletRequest.getContextPath() + "/commItemGroupsAction!displayCommItemGroups.do?selectedCommItemGroupsId=" + commGroup.getItemGroupId().toString()));
                }
            } else if (node.indexOf("1_") == 0) {
                node = node.substring(2);
                List listItemGroup = new ArrayList();

                listItemGroup = hbnSession.createCriteria(CommItemGroups.class).
                        add(Restrictions.eq("parentItemGroupId", Long.parseLong(node))).
                        add(Restrictions.ne("status", Constant.STATUS_DELETE.toString())).
                        addOrder(Order.asc("groupName")).
                        list();
                Iterator iterSaleServices = listItemGroup.iterator();
                while (iterSaleServices.hasNext()) {
                    CommItemGroups commGroup = (CommItemGroups) iterSaleServices.next();
                    String nodeGroupId = "1_" + commGroup.getItemGroupId().toString();
                    getListItems().add(new Node(commGroup.getGroupName(), "true", nodeGroupId, httpServletRequest.getContextPath() + "/commItemGroupsAction!displayCommItemGroups.do?selectedCommItemGroupsId=" + commGroup.getItemGroupId().toString()));
                }

                //lay danh sach cac khoan muc thuoc nhom khoan muc nay
                List listItem = hbnSession.createCriteria(CommItems.class).
                        add(Restrictions.eq("itemGroupId", Long.parseLong(node))).
                        add(Restrictions.ne("status", Constant.STATUS_DELETE.toString())).
                        addOrder(Order.asc("itemName")).
                        list();
                Iterator iterSaleServices1 = listItem.iterator();
                while (iterSaleServices1.hasNext()) {
                    CommItems commItems = (CommItems) iterSaleServices1.next();
                    String nodeId = "2_" + commItems.getItemId().toString();
                    getListItems().add(new Node(commItems.getItemName(), "false", nodeId, httpServletRequest.getContextPath() + "/commItemsAction!displayCommItems.do?selectedCommItemsId=" + commItems.getItemId().toString()));
                }
            }

            log.info("end method getCommItemGroupsTree of CommItemGroupsDAO");

            return GET_COMM_ITEM_GROUPS_TREE;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void save(CommItemGroups transientInstance) {
        log.debug("saving CommItemGroups instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CommItemGroups persistentInstance) {
        log.debug("deleting CommItemGroups instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CommItemGroups findById(java.lang.Long id) {
        log.debug("getting CommItemGroups instance with id: " + id);
        try {
            CommItemGroups instance = (CommItemGroups) getSession().get(
                    "com.viettel.im.database.BO.CommItemGroups", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CommItemGroups instance) {
        log.debug("finding CommItemGroups instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.CommItemGroups").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CommItemGroups instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CommItemGroups as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByParentItemGroupId(Object parentItemGroupId) {
        return findByProperty(PARENT_ITEM_GROUP_ID, parentItemGroupId);
    }

    public List findByGroupName(Object groupName) {
        return findByProperty(GROUP_NAME, groupName);
    }

    public List findByReportType(Object reportType) {
        return findByProperty(REPORT_TYPE, reportType);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all CommItemGroups instances");
        try {
            String queryString = "from CommItemGroups";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List<CommItemGroups> findAllActive() {
        log.debug("finding all CommItemGroups instances");
        try {
            String queryString = "from CommItemGroups where status = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CommItemGroups merge(CommItemGroups detachedInstance) {
        log.debug("merging CommItemGroups instance");
        try {
            CommItemGroups result = (CommItemGroups) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CommItemGroups instance) {
        log.debug("attaching dirty CommItemGroups instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CommItemGroups instance) {
        log.debug("attaching clean CommItemGroups instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
//    public String addNewCommItemGroups() throws Exception {
//        log.info("Begin method preparePage of CommItemGroupsDAO ...");
//        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = DECLARE_COMMITEM_GROUPS;
//        if (userToken != null) {
//            try {
//
//                CommItemGroupsForm f = getCommItemGroupsForm();
//
//
//                CommItemGroups bo = new CommItemGroups();
//
//                copyDataToBO(bo);
//                bo.setItemGroupId(getSequence("COMM_ITEM_GROUPS_SEQ"));
//                save(bo);
//
//                f.resetForm();
//
//                f.setMessage("Đã thêm nhóm khoản mục mới !");
//
//                List listCommItemGroups = new ArrayList();
//
//                listCommItemGroups = findAll();
//
//                req.getSession().removeAttribute("listCommItemGroups");
//                req.getSession().setAttribute("listCommItemGroups", listCommItemGroups);
//
//                addActionError("Đã tạo thành công nhóm khoản mục mới");
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End method preparePage of CommItemGroupsDAO");
//
//        return pageForward;
//    }
//    public String editCommItemGroups() throws Exception {
//        log.info("Begin method preparePage of CommItemGroupsDAO ...");
//        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//
//        if (userToken != null) {
//            try {
//
//                String itemGroupId = session.getAttribute("itemGroupId").toString();
//
//                CommItemGroups bo = this.findById(Long.parseLong(itemGroupId));
//
//                copyDataToBO(bo);
//
//                update(bo);
//
//                req.getSession().setAttribute("toEditCommItemGroups", 0);
//
//                List listCommItemGroups = new ArrayList();
//                listCommItemGroups = findAll();
//                req.getSession().removeAttribute("listCommItemGroups");
//                req.getSession().setAttribute("listCommItemGroups", listCommItemGroups);
//
//                req.getSession().setAttribute("actionCommGroupType", VIEW_TYPE);
//
//                req.setAttribute("readOnly", "true");
//
//                addActionError("Đã cập nhật thành công nhóm khoản mục");
//
////                pageForward = DECLARE_COMMITEM_GROUPS;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.info("End method preparePage of CommItemGroupsDAO");
//
//        return pageForward;
//    }
//    /**
//     * change state of interface
//     * @return
//     * @throws java.lang.Exception
//     */
//    public String changActionType() throws Exception {
//        log.info("begin method changActionType of CommItemGroupsDAO...");
//
//        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//        if (userToken != null) {
//            try {
//                String actionType = (String) req.getParameter("actionType");
//
//                if (!StringUtils.validString(actionType)) {
//                    log.debug("Not find action type");
//                    return pageForward;
//                }
//
//                if (actionType.equals(NEW_TYPE)) {
//                    session.setAttribute("actionCommGroupType", NEW_TYPE);
//                    commItemGroupsForm = new CommItemGroupsForm();
//                } else if (actionType.equals(UPDATE_TYPE)) {
//                    session.setAttribute("actionCommGroupType", UPDATE_TYPE);
//                    Long itemGroupId = (Long) session.getAttribute("itemGroupId");
//                    if (itemGroupId != null) {
//                        CommItemGroups bo = findById(itemGroupId);
//                        copyDataFromBO(bo);
//                    }
//                } else {
//                    session.setAttribute("actionCommGroupType", VIEW_TYPE);
//                    req.setAttribute("readOnly", "true");
//                    Long itemGroupId = (Long) session.getAttribute("itemGroupId");
//                    if (itemGroupId != null) {
//                        CommItemGroups bo = findById(itemGroupId);
//                        copyDataFromBO(bo);
//                    }
//                }
//
////                pageForward = DECLARE_COMMITEM_GROUPS;
//
//            } catch (Exception e) {
//                log.debug("WEB. " + e.toString());
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//
//        log.info("end method changActionType of CommItemGroupsDAO...");
//
//        return pageForward;
//    }
//    public void copyDataToBO(CommItemGroups commItemGroups) {
//
//        commItemGroups.setGroupName(commItemGroupsForm.getGroupName());
//        commItemGroups.setReportType(commItemGroupsForm.getReportType());
//        commItemGroups.setStatus(commItemGroupsForm.getStatus());
//
//        try {
////            commItemGroups.setCreateDate(DateTimeUtils.convertStringToDate(commItemGroupsForm.getCreateDate()));
//        } catch (Exception ex) {
//            log.debug("WEB. " + ex.toString());
//        }
//        if (commItemGroupsForm.getParentItemGroupId() != null) {
//            commItemGroups.setParentItemGroupId(commItemGroupsForm.getParentItemGroupId());
//        } else {
//            commItemGroups.setParentItemGroupId(0L);
//        }
//
//    }
//
//    public void copyDataFromBO(CommItemGroups commItemGroups) {
//        commItemGroupsForm.setGroupName(commItemGroups.getGroupName());
//        commItemGroupsForm.setReportType(String.valueOf(commItemGroups.getReportType()));
//        commItemGroupsForm.setStatus(String.valueOf(commItemGroups.getStatus()));
//        try {
////            commItemGroupsForm.setCreateDate(DateTimeUtils.convertDateToString(commItemGroups.getCreateDate()));
//        } catch (Exception ex) {
//            log.debug("WEB. " + ex.toString());
//        }
//        commItemGroupsForm.setParentItemGroupId(commItemGroups.getParentItemGroupId());
//    }
}
