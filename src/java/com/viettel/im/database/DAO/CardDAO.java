package com.viettel.im.database.DAO;

/**
 *
 * @author Andv
 */
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Card;
import java.util.ArrayList;
import com.viettel.im.client.form.CardForm;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Query;
import com.viettel.im.database.BO.UserToken;
import javax.servlet.http.HttpSession;
import com.viettel.im.client.bean.CardBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.security.util.StringEscapeUtils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for Card
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see com.viettel.bccs.im.database.BO.Card
 * @author MyEclipse Persistence Tools
 */
public class CardDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(CardDAO.class);
    // property constants
    private String pageForward;
    public static final int SEARCH_RESULT_LIMIT = 1000;
    public static final String MANAGE = "manage";
    public static final String ADD_CARD = "addCard";
    public static final String CODE = "code";
    public static final String EQUIPMENT_ID = "equipmentId";
    public static final String NAME = "name";
    public static final String CARD_TYPE = "cardType";
    public static final String TOTAL_PORT = "totalPort";
    public static final String STATUS = "status";
    public static final String DESCRIPTION = "description";
    private CardForm cardForm = new CardForm();

    public CardForm getCardForm() {
        return cardForm;
    }

    public void setCardForm(CardForm cardForm) {
        this.cardForm = cardForm;
    }

    public void save(Card transientInstance) {
        log.debug("saving Card instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Card persistentInstance) {
        log.debug("deleting Card instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Card findById(java.lang.Long id) {
        log.debug("getting Card instance with id: " + id);
        try {
            Card instance = (Card) getSession().get(
                    "com.viettel.im.database.BO.Card", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Card instance) {
        log.debug("finding Card instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.bccs.database.BO.Card").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Card instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Card as model where model." + StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            queryString += " order by model.name ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByCode(Object code) {
        return findByProperty(CODE, code);
    }

    public List findByEquipmentId(Object equipmentId) {
        return findByProperty(EQUIPMENT_ID, equipmentId);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByCardType(Object cardType) {
        return findByProperty(CARD_TYPE, cardType);
    }

    public List findByTotalPort(Object totalPort) {
        return findByProperty(TOTAL_PORT, totalPort);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findAll() {
        log.debug("finding all Card instances");
        try {
            String queryString = "from Card ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Card merge(Card detachedInstance) {
        log.debug("merging Card instance");
        try {
            Card result = (Card) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Card instance) {
        log.debug("attaching dirty Card instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /*  public void attachClean(Card instance) {
    log.debug("attaching clean Card instance");
    try {
    getSession().lock(instance, LockMode.NONE);
    log.debug("attach successful");
    } catch (RuntimeException re) {
    log.error("attach failed", re);
    throw re;
    }
    }
     */
    public String preparePage() throws Exception {

        log.info("Bắt đầu hàm preparePage của  ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                CardForm f = getCardForm();
                f.resetForm();
                req.getSession().removeAttribute("lstCard");
                req.getSession().setAttribute("lstCard", getListCard());
                req.getSession().setAttribute("lstEquipment", getListEquipment());
                req.getSession().setAttribute("lstCardType", getListCardType());

                req.getSession().setAttribute("toEditCard", 0);
                f.resetForm();
                req.getSession().setAttribute("toEditEquipment", 0);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("Kết thúc hàm preparePage của EquipmentVendorDAO");
        pageForward = ADD_CARD;
        return pageForward;
    }
    /*
    public List getlistSerchedCard() throws Exception {
    try {
    List card = new ArrayList();
    List colParameter = new ArrayList();
    String strHQL = "from Card where 1=1";
    strHQL += "and status = 1 ";

    //querry
    Query query = getSession().createQuery(strHQL);
    for (int i = 0; i < colParameter.size(); i++) {
    query.setParameter(i, colParameter.get(i));
    }
    int resultLimit = SEARCH_RESULT_LIMIT + 1;
    query.setMaxResults(resultLimit);
    card = query.list();
    return card;


    } catch (Exception e) {
    e.printStackTrace();
    throw e;
    }
    }
     * */

    public String prepareEditCard() throws Exception {

        log.info("Begin method preparePage  ...");

        HttpServletRequest req = getRequest();
        CardForm f = this.cardForm;
        String strCardId = (String) QueryCryptUtils.getParameter(req, "cardId");
        Long cardId;
        cardId = Long.parseLong(strCardId);
        Card bo = new Card();
        bo = this.findById(cardId);
        f.copyDataFromBO(bo);
        f.setCardId(cardId);

        req.getSession().setAttribute("lstEquipment", getListEquipment());
        req.getSession().setAttribute("lstCardType", getListCardType());

        req.getSession().setAttribute("toEditCard", 1);


        pageForward = ADD_CARD;

        log.info("End method ");

        return pageForward;
    }

    public String addNewCard() throws Exception {
        log.info("Begin method preparePage of ...");
        HttpServletRequest req = getRequest();
        CardForm f = getCardForm();
        if (checkValidateToAdd()) {

            Card bo = new Card();
            f.copyDataToBO(bo);
            Long cardId = getSequence("CARD_SEQ");
            bo.setCardId(cardId);
            save(bo);
            f.resetForm();
            req.getSession().removeAttribute("lstCard");
            req.getSession().setAttribute("lstCard", getListCard());
            req.getSession().setAttribute("lstEquipment", getListEquipment());
            req.getSession().setAttribute("lstCardType", getListCardType());

            req.getSession().setAttribute("toEditCard", 0);
            req.setAttribute("returnMsg", "card.add.success");

        }
        pageForward = ADD_CARD;

        log.info("End method ");

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of cardDao ...");

        HttpServletRequest req = getRequest();


        CardForm f = this.cardForm;

        req.getSession().setAttribute("toEditCard", 0);
        pageForward = "cardResult";

        log.info("End method preparePage ");

        return pageForward;
    }

    public String editCard() throws Exception {

        HttpServletRequest req = getRequest();
        CardForm f = getCardForm();
        if (checkValidateToEdit()) {


            Card bo = new Card();
            f.copyDataToBO(bo);
            getSession().update(bo);
            f.resetForm();
            req.setAttribute("returnMsg", "card.edit.success");

            req.getSession().removeAttribute("lstCard");
            req.getSession().setAttribute("lstCard", getListCard());
            req.getSession().setAttribute("lstEquipment", getListEquipment());
            req.getSession().setAttribute("lstCardType", getListCardType());

            req.getSession().setAttribute("toEditCard", 0);
        }

        pageForward = ADD_CARD;

        log.info("End method ");

        return pageForward;
    }

    public String deleteCard() throws Exception {
        log.info("Begin method Delete ...");

        HttpServletRequest req = getRequest();
        CardForm f = getCardForm();

        String strCardId = (String) QueryCryptUtils.getParameter(req, "cardId");
        Long cardId;
        try {
            cardId = Long.parseLong(strCardId);
        } catch (Exception ex) {
            cardId = -1L;
        }

        Card bo = findById(cardId);        
        getSession().delete(bo);
        f.resetForm();
        req.getSession().removeAttribute("lstCard");
        req.getSession().setAttribute("lstCard", getListCard());
        req.getSession().setAttribute("lstEquipment", getListEquipment());
        req.getSession().setAttribute("lstCardType", getListCardType());

        req.setAttribute("returnMsg", "card.delete.success");

        pageForward = ADD_CARD;

        log.info("End method ");

        return pageForward;
    }

    private List getListEquipment() {
        try {
            EquipmentDAO equipmentDAO = new EquipmentDAO();
            equipmentDAO.setSession(this.getSession());
            return equipmentDAO.findByEquipmentTypeAndStatus(Constant.EQUIPMENT_TYPE_CARD);
            
//            String queryString = " from Equipment ";
//            queryString += " where not status = 0 ";
//            queryString += "and equipmentType = " + Constant.EQUIPMENT_TYPE_CARD;
//            Query queryObject = getSession().createQuery(queryString);
//
//            return queryObject.list();
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi : " + ex.toString());
            return null;
        }
    }

    private List getListCardType() {
        try {
            AppParamsDAO app = new AppParamsDAO();
            app.setSession(this.getSession());
            return app.findAppParamsByType(Constant.APP_PARAMS_CARD_TYPE);
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi : " + ex.toString());
            return null;
        }

    }

    public String cardSearch() throws Exception {
        try {
            log.info("Begin method of ...");
            CardForm f = getCardForm();
            HttpServletRequest req = getRequest();

            List lstCard = new ArrayList();
            lstCard = getListCard();
            if (lstCard.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "search.result");
                List listMessageParam = new ArrayList();
                listMessageParam.add(lstCard.size());
                req.setAttribute("returnMsgParam", listMessageParam);
            }

            req.getSession().removeAttribute("lstCard");
            req.getSession().setAttribute("lstCard", lstCard);

            req.getSession().removeAttribute("lstEquipment");
            req.getSession().setAttribute("lstEquipment", getListEquipment());
            req.getSession().setAttribute("lstCardType", getListCardType());

            req.getSession().setAttribute("toEditCard", 0);
            f.resetForm();

            log.info("End method ");
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentMessage("Xuất hiện lỗi khi tìm kiếm nhà cung cấp thiết bị");
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
        }

        return ADD_CARD;



    }

    private List<CardBean> getListCard() {
        try {
            CardForm f = getCardForm();
            List parameterList = new ArrayList();
            String queryString = "select distinct new com.viettel.im.client.bean.CardBean( a.cardId, a.code, " +
                    " a.equipmentId, a.name, a.cardType, a.totalPort, a.status, a.description, b.name as equipmentName,c.name as cardTypeName)" +
                    " from Card a, Equipment b, AppParams c ";
            queryString += "where a.equipmentId = b.equipmentId ";
            queryString+=" and a.cardType=c.code ";
            queryString+=" and c.type = ? ";
            parameterList.add(Constant.APP_PARAMS_CARD_TYPE);


            if (f.getName() != null && !f.getName().trim().equals("")) {
                queryString += " and upper(a.name) like ? ";
                parameterList.add("%" + f.getName().trim().toUpperCase() + "%");
            }
            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                queryString += " and upper(a.code) = ? ";
                parameterList.add(f.getCode().trim().toUpperCase());
            }
            if (f.getEquipmentId() != null && !f.getEquipmentId().trim().equals("")) {
                queryString += " and a.equipmentId = ? ";
                parameterList.add(Long.parseLong(f.getEquipmentId().trim()));
            }
            if (f.getTotalPort() != null && !f.getTotalPort().trim().equals("")) {
                queryString += " and a.totalPort = ? ";
                parameterList.add(Long.parseLong(f.getTotalPort().trim()));
            }
            if (f.getCardType() != null && !f.getCardType().trim().equals("")) {
                queryString += " and a.cardType = ? ";
                parameterList.add(f.getCardType().trim());
            }
            if (f.getStatus() != null) {
                queryString += " and a.status = ? ";
                parameterList.add(f.getStatus());
            }
            if (f.getDescription() != null && !f.getDescription().trim().equals("")) {
                queryString += " and upper(a.description) like ? ";
                parameterList.add("%" + f.getDescription().trim().toUpperCase() + "%");
            }
            queryString += "order by nlssort(c.name,'nls_sort=Vietnamese') asc, ";
            queryString += "nlssort(a.code,'nls_sort=Vietnamese') asc, ";
            queryString += "nlssort(a.name,'nls_sort=Vietnamese') asc ";
            // queryString += "order by nlssort(a.vendorName,'nls_sort=Vietnamese') asc, ";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi : " + ex.toString());
            return null;
        }
    }

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        CardForm f = getCardForm();

        String name = f.getName().trim();
        String code = f.getCode().trim();
        String equip = f.getEquipmentId().trim();
        String totalPort = f.getTotalPort().trim();


        if ((name == null) || name.equals("")) {
            req.setAttribute("returnMsg", "card.error.invalidName");
            return false;
        }
        if ((code == null) || code.equals("")) {
            req.setAttribute("returnMsg", "card.error.invalidCode");
            return false;
        }
        if ((equip == null) || equip.equals("")) {
            req.setAttribute("returnMsg", "card.error.invalidEquipment");
            return false;
        }
        if ((totalPort != null) && !totalPort.equals("")) {

            try {

                if (Long.parseLong(totalPort) < 0) {
                    req.setAttribute("returnMsg", "card.error.invalidTotalPort2");
                    return false;
                }

            } catch (NumberFormatException e) {
                req.setAttribute("returnMsg", "card.error.invalidTotalPort");
                return false;
            }
        }

        //trim cac truong can thiet
        f.setName(name.trim());
        f.setCode(code.trim());
        f.setEquipmentId(equip.trim());

        return true;

    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();
        Long count;
        CardForm f = getCardForm();
        String code = f.getCode().trim();

        if (checkForm()) {
            //kiem tra co trung ten thiet bi ko
            String strQuery = "select count(*) from Card as model where upper(model.code)=?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.toUpperCase());
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "card.add.duplicateCode");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean checkValidateToEdit() {
        HttpServletRequest req = getRequest();
        Long count;
        CardForm f = getCardForm();
        if (checkForm()) {
            //kiem tra co trung ten thiet bi ko
            String strQuery = "select count(*) from Card as model where upper(model.code) = ? and not model.cardId = ?";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, f.getCode().trim().toUpperCase());
            q.setParameter(1, f.getCardId());
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "card.edit.duplicateCode");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

   
    
}

