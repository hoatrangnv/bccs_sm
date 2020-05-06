/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.form.CommissionFeeForm;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.CommItemFeeChannel;
import com.viettel.im.database.BO.CommItemGroups;
import com.viettel.im.database.BO.CommItems;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author DatPV, TUNGTV
 */
public class CommFeeDAO extends BaseDAOAction {

    private final String DETAIL_COMM_ITEM_GROUPS = "detailCommItemsGroups";


    private Log log = LogFactory.getLog(SaleChannelTypeDAO.class);


    private String pageForward;
    
    //dinh nghia cac hang so pageForward
    public static final String DECLARE_COMM_ITEM_FEE = "declareCommissionFee";


    public static final String DECLARE_COMM_PRICE_ITEM = "declareCommPriceItems";


    private CommissionFeeForm formPrice = new CommissionFeeForm();


    public static final String GROUP_COMMISSION_ITEM = "groupCommissionItem";


    public static final String LIST_COMMISSION_ITEM = "listCommissionItem";


    public static final String TREE_GROUP_COMMISSION_ITEM = "treeGroupCommItems";


    public static final String VIEW_TYPE = "view";


    public static final String NEW_TYPE = "new";


    public static final String UPDATE_TYPE = "update";


    public static final String DELETE_TYPE = "delete";


    public static final String BACK = "back";


    public static final String CREATE_COMM_INTERFACE = "1";


    public static final String CREATE_COMM_PRICE_INTERFACE = "2";


    public static final Long ROOT_COMM = 0L;


    public static final String COMM_ABILITY = "1";


    public CommissionFeeForm getFormPrice() {
        return formPrice;
    }

    public void setFormPrice(CommissionFeeForm formPrice) {
        this.formPrice = formPrice;
    }


    public String preparePage() throws Exception {

        log.info("Begin method prepareAddNewSaleChannel of SaleChannelDAO ...");

        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        
        if (userToken != null) {
            try {
                CommItemGroupsDAO commItemGroupsDAO = new CommItemGroupsDAO();
                commItemGroupsDAO.setSession(getSession());
                List<CommItemGroups> commissionGroupList = getSession().createCriteria(CommItemGroups.class).
                    add(Restrictions.eq("parentItemGroupId", ROOT_COMM)).
                    add(Restrictions.eq("status", Constant.STATUS_USE.toString())).
                    addOrder(Order.asc("groupName")).
                    list();

                ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
                channelTypeDAO.setSession(getSession());
                
                List<ChannelType> listChannelType = channelTypeDAO.findAllActive(COMM_ABILITY);

                reqSession.setAttribute("listChannelType", listChannelType);
                reqSession.setAttribute("commissionGroupList", commissionGroupList);
                reqSession.setAttribute("interfaceType", CREATE_COMM_PRICE_INTERFACE);
                
                pageForward = TREE_GROUP_COMMISSION_ITEM;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        log.info("End method preparePage of CommItemsDAO");
        return pageForward;        
    }


    /**
     * Display comm price item
     * @return list of comm price and create new price interface to display
     * @throws java.lang.Exception
     */
    public String displayCommPrices() throws Exception {
        log.info("Begin.");
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                String selectedCommIdTemp = QueryCryptUtils.getParameter(req, "selectedCommId");

                if (selectedCommIdTemp == null || selectedCommIdTemp.trim().equals("")) {
                    log.debug("Commission ID was missing");
                    return Constant.ERROR_PAGE;
                }

                Long selectedCommId = new Long(selectedCommIdTemp);

                CommItemsDAO commItemsDAO = new CommItemsDAO();
                commItemsDAO.setSession(getSession());

                CommItems commItems = commItemsDAO.findById(selectedCommId);

                displayListPrice(selectedCommId);

                /** Name to display in interface */
                reqSession.setAttribute("commItemsName", commItems.getItemName());
                
                /** Type of action to display button */
                reqSession.setAttribute("actionPriceType", NEW_TYPE);

                /** Current comm item id for insert new item fee channel */
                reqSession.setAttribute("commPriceItemId", selectedCommId);

                pageForward = DECLARE_COMM_PRICE_ITEM;
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End.");
        return pageForward;
    }


    /**
     * Display detail of comm price item
     * @return 
     * @throws java.lang.Exception
     */
    public String detailCommPrice() throws Exception {
        log.info("Begin.");
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                String selectedCommItemFeeChannelIdTemp = QueryCryptUtils
                        .getParameter(req, "selectedCommItemFeeChannelId");

                if (selectedCommItemFeeChannelIdTemp == null || selectedCommItemFeeChannelIdTemp.trim().equals("")) {
                    log.debug("Commission ID was missing");
                    return Constant.ERROR_PAGE;
                }

                Long selectedCommItemFeeChannelId = new Long(selectedCommItemFeeChannelIdTemp);
                CommItemFeeChannelDAO cifcdao = new CommItemFeeChannelDAO();
                cifcdao.setSession(getSession());
                CommItemFeeChannel commItemFeeChannel = cifcdao.findById(selectedCommItemFeeChannelId);
                setFormFeeInfor(commItemFeeChannel, null);

                /** Type of action to display button */
                reqSession.setAttribute("actionPriceType", VIEW_TYPE);

                /** For changType action */
                reqSession.setAttribute("selectedCommItemFeeChannelId", selectedCommItemFeeChannelId);

                pageForward = DECLARE_COMM_PRICE_ITEM;
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End.");
        return pageForward;
    }


    /**
     * delete comm price item
     * @return
     * @throws java.lang.Exception
     */
    public String deleteCommPrice() throws Exception {
        log.info("Begin.");
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                
                Long commItemFeeChannelId = (Long)reqSession.getAttribute("selectedCommItemFeeChannelId");
                if (commItemFeeChannelId == null) {
                    log.info("selectedCommItemFeeChannelId is null");
                    return pageForward;
                }

                Long commItemId = (Long)reqSession.getAttribute("commPriceItemId");

                if (commItemId == null) {
                    log.info("Not find comm item group id");
                    return pageForward;
                }

                CommItemFeeChannelDAO cifcdao = new CommItemFeeChannelDAO();
                cifcdao.setSession(getSession());
                CommItemFeeChannel commItemFeeChannel = cifcdao.findById(commItemFeeChannelId);
                commItemFeeChannel.setStatus(Constant.STATUS_DELETE.toString());
                cifcdao.save(commItemFeeChannel);

                displayListPrice(commItemId);

                /** Type of action to display button */
                formPrice = new CommissionFeeForm();
                reqSession.setAttribute("actionPriceType", NEW_TYPE);

                addActionError("Đã xóa thành công");
                pageForward = DECLARE_COMM_PRICE_ITEM;
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End.");
        return pageForward;
    }


    /**
     * update comm price or create new comm price
     * @return list of commission to display
     * @throws java.lang.Exception
     */
    public String updateCommPrice() throws Exception {
        log.info("Begin.");
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {

                String actionType = (String)reqSession.getAttribute("actionPriceType");

                if (!StringUtils.validString(actionType)) {
                    log.info("Not find action type");
                    return pageForward;
                }

                Long commItemId = (Long)reqSession.getAttribute("commPriceItemId");

                if (commItemId == null) {
                    log.info("Not find comm item group id");
                    return pageForward;
                }

                if (actionType.equals(NEW_TYPE)) {
                    insertCommPriceItem(commItemId);
                    formPrice = new CommissionFeeForm();
                    reqSession.setAttribute("actionPriceType", NEW_TYPE);
                    displayListPrice(commItemId);
                    addActionError("Đã đăng ký thành công giá mới");
                } else if (actionType.equals(UPDATE_TYPE)) {
                    Long commItemFeeChannelId = (Long)reqSession.getAttribute("selectedCommItemFeeChannelId");
                    if (commItemFeeChannelId == null) {
                        log.info("selectedCommItemFeeChannelId is null");
                        return pageForward;
                    }

                    CommItemFeeChannelDAO commItemFeeChannelDAO = new CommItemFeeChannelDAO();
                    commItemFeeChannelDAO.setSession(getSession());
                    CommItemFeeChannel commItemFeeChannel = commItemFeeChannelDAO.findById(commItemFeeChannelId);
                    
                    updateCommPriceItem(commItemFeeChannel, commItemId);

                    displayListPrice(commItemId);

                    addActionError("Đã cập nhật thành công giá");

                    reqSession.setAttribute("actionPriceType", VIEW_TYPE);
                }
                
                pageForward = DECLARE_COMM_PRICE_ITEM;
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End.");
        return pageForward;
    }


    /**
     * insert new comm price
     * @return
     * @throws java.lang.Exception
     */
    public void insertCommPriceItem(Long commItemId) throws Exception {
        log.info("Begin.");

            try {
                CommItemFeeChannel commItemFeeChannel = new CommItemFeeChannel();
                commItemFeeChannel.setItemFeeChannelId(getSequence("COMM_ITEM_FEE_CHANNEL_SEQ"));
                reqSession.setAttribute("commItemFeeChannelId", commItemFeeChannel.getItemFeeChannelId());
                updateCommPriceItem(commItemFeeChannel, commItemId);

            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }

        log.info("End.");
    }


    /**
     * update comm price
     * @return
     * @throws java.lang.Exception
     */
    public void updateCommPriceItem(CommItemFeeChannel commItemFeeChannel, Long commItemId) throws Exception {
        log.info("Begin.");

            try {
                commItemFeeChannel.setItemId(commItemId);
                commItemFeeChannel.setChannelTypeId(new Long(formPrice.getChannelType()));
                commItemFeeChannel.setStatus(formPrice.getStatus());

                commItemFeeChannel.setStaDate(DateTimeUtils.convertStringToDate(formPrice.getDateStart()));
                commItemFeeChannel.setEndDate(DateTimeUtils.convertStringToDate(formPrice.getDateEnd()));
                commItemFeeChannel.setFee(new Long(formPrice.getFee()));
                commItemFeeChannel.setVat(new Long(formPrice.getVat()));
                
                UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
                commItemFeeChannel.setCreateDate(new Date());
                commItemFeeChannel.setUserCreate(userToken.getStaffName());
                
                CommItemFeeChannelDAO commItemFeeChannelDAO = new CommItemFeeChannelDAO();
                commItemFeeChannelDAO.setSession(getSession());
                commItemFeeChannelDAO.save(commItemFeeChannel);
                
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }

        log.info("End.");
    }


    /**
     * change state of interface
     * @return 
     * @throws java.lang.Exception
     */
    public String changActionPriceType() throws Exception {
        log.info("Begin.");
        getReqSession();
        UserToken userToken = (UserToken) reqSession.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {

                String actionType = (String)req.getParameter("actionPriceType");

                if (!StringUtils.validString(actionType)) {
                    log.debug("Not find action type");
                    return pageForward;
                }

                if (actionType.equals(NEW_TYPE)) {
                    reqSession.setAttribute("actionPriceType", NEW_TYPE);
                    formPrice = new CommissionFeeForm();
                } else if (actionType.equals(UPDATE_TYPE)) {
                    reqSession.setAttribute("actionPriceType", UPDATE_TYPE);
                    Long commItemFeeChannelId = (Long)reqSession.getAttribute("selectedCommItemFeeChannelId");
                    if (commItemFeeChannelId != null) {
                        CommItemFeeChannelDAO commItemFeeChannelDAO = new CommItemFeeChannelDAO();
                        commItemFeeChannelDAO.setSession(getSession());
                        CommItemFeeChannel commItemFeeChannel = commItemFeeChannelDAO.findById(commItemFeeChannelId);
                        setFormFeeInfor(commItemFeeChannel, null);
                    }
                } else if (actionType.equals(BACK)) {
                    pageForward = DETAIL_COMM_ITEM_GROUPS;

                    /** Page state */
                    req.getSession().setAttribute("actionCommGroupType", NEW_TYPE);

                    req.setAttribute("displayCommList", "true");

                    return pageForward;
                } else {
                    reqSession.setAttribute("actionPriceType", VIEW_TYPE);
                    Long commItemFeeChannelId = (Long)reqSession.getAttribute("selectedCommItemFeeChannelId");
                    if (commItemFeeChannelId != null) {
                        CommItemFeeChannelDAO commItemFeeChannelDAO = new CommItemFeeChannelDAO();
                        commItemFeeChannelDAO.setSession(getSession());
                        CommItemFeeChannel commItemFeeChannel = commItemFeeChannelDAO.findById(commItemFeeChannelId);
                        setFormFeeInfor(commItemFeeChannel, null);
                    }
                }


                pageForward = DECLARE_COMM_PRICE_ITEM;
            } catch (Exception e) {
                log.debug("WEB. " + e.toString());
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End.");
        return pageForward;
    }


    private void displayListPrice(Long selectedCommId) {
        CommItemFeeChannelDAO cifcdao = new CommItemFeeChannelDAO();
        cifcdao.setSession(getSession());
        List<CommItemFeeChannel> listCommItemFeeChannel = cifcdao.getAllCommItemFee(selectedCommId);

        reqSession.setAttribute("listCommItemFeeChannel", listCommItemFeeChannel);

    }


    /** Save infor to display in the interface */
    private void setFormFeeInfor(CommItemFeeChannel commItemFeeChannel, String groupName)
            throws Exception {
        formPrice = new CommissionFeeForm();
        formPrice.setChannelType(commItemFeeChannel.getChannelTypeId().toString());
        formPrice.setStatus(commItemFeeChannel.getStatus());
        formPrice.setDateStart(DateTimeUtils.convertDateToString(commItemFeeChannel.getStaDate()));
        formPrice.setDateEnd(DateTimeUtils.convertDateToString(commItemFeeChannel.getEndDate()));
        formPrice.setVat(commItemFeeChannel.getVat().toString());
        formPrice.setFee(commItemFeeChannel.getFee().toString());
    }
}
