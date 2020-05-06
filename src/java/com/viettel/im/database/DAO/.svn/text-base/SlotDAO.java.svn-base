package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Slot;
import com.viettel.im.database.BO.Chassic;
import com.viettel.im.client.form.SlotForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import org.hibernate.Session;
import java.util.ArrayList;
import org.hibernate.transform.Transformers;
import com.viettel.im.common.util.DateTimeUtils;

import com.viettel.im.common.util.QueryCryptUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Example;
import com.viettel.im.client.bean.SlotBean;
import com.viettel.im.client.form.PortForm;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Card;
import java.util.Date;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import java.util.Map;
import java.util.HashMap;

/**
 * A data access object (DAO) providing persistence and search support for Slot
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see com.viettel.bccs.im.database.BO.Slot
 * @author MyEclipse Persistence Tools
 */
public class SlotDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SlotDAO.class);
    // property constants
    private String pageForward;
    public static final int SEARCH_RESULT_LIMIT = 1000;
    public static final String ADD_SLOT = "addSlot";
    public static final String CHASSIC_ID = "chassicId";
    public static final String SLOT_POSITION = "slotPosition";
    public static final String FREE_PORT = "freePort";
    public static final String STATUS = "status";
    public static final String SLOT_TYPE = "slotType";
    public static final String MAX_PORT = "maxPort";
    public static final String USED_PORT = "usedPort";
    public static final String INVALID_PORT = "invalidPort";
    public static final String STA_PORT_POSITION = "staPortPosition";
    public static final String CARD_ID = "cardId";
    public static final String TABLE_SLOT = "Slot";
    private SlotForm slotForm = new SlotForm();
    private Map lstChassicCode = new HashMap();
    private Map listElement = new HashMap();

    public Map getListElement() {
        return listElement;
    }

    public void setListElement(Map listElement) {
        this.listElement = listElement;
    }

    public Map getLstChassicCode() {
        return lstChassicCode;
    }

    public void setLstChassicCode(Map lstChassicCode) {
        this.lstChassicCode = lstChassicCode;
    }

    public SlotForm getSlotForm() {
        return slotForm;
    }

    public void setSlotForm(SlotForm slotForm) {
        this.slotForm = slotForm;
    }

    //Phan thong tin tu sinh
    public void save(Slot transientInstance) {
        log.debug("saving Slot instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Slot persistentInstance) {
        log.debug("deleting Slot instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Slot findById(java.lang.Long id) {
        log.debug("getting Slot instance with id: " + id);
        try {
            Slot instance = (Slot) getSession().get(
                    "com.viettel.im.database.BO.Slot", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Slot instance) {
        log.debug("finding Slot instance by example");
        try {
            List results = getSession().createCriteria(
                    "ccom.viettel.im.database.BO.Slot").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Slot instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Slot as model where not model.status = ? and model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, String.valueOf(Constant.STATUS_DELETE));
            queryObject.setParameter(1, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByProperty(String[] propertyName, Object[] value) {
        log.debug("finding Slot instance with property: " + propertyName
                + ", value: " + value);
        try {
            if (propertyName.length != value.length) {
                return null;
            }
            List lstParam = new ArrayList();
            String queryString = "from Slot as model where 1=1 ";
            for (int i = 0; i < propertyName.length; i++) {
                if (propertyName[i] == null || propertyName[i].trim().equals("")) {
                    continue;
                }
                queryString += " and model." + propertyName[i].trim() + " = ? ";
                lstParam.add(value[i]);
            }
            queryString += " order by model.chassicId, model.slotPosition ";
            Query queryObject = getSession().createQuery(queryString);
            for (int idx = 0; idx < lstParam.size(); idx++) {
                queryObject.setParameter(idx, lstParam.get(idx));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    private List getListSlot() throws Exception {
        log.debug("finding Slot instance with property:");
        try {

            SlotForm f = getSlotForm();
            List parameterList = new ArrayList();
            Long chassicId = f.getChassicId();
            String queryString = "select new Slot(a.slotId,a.chassicId,a.slotPosition,a.freePort,a.status,a.cardInstalledDate,a.slotType,a.maxPort,a.usedPort,a.invalidPort,a.staPortPosition,a.cardId,b.code || ' - ' || b.name, c.name, (select name from AppParams b where b.type = ? and b.code = a.status )) "
                    + "from Slot a, Card b ,AppParams c ";
            parameterList.add(Constant.APP_PARAMS_SLOT_STATUS);

            queryString += " where a.cardId=b.cardId ";
            queryString += " and a.chassicId = ?";
            parameterList.add(chassicId);
            queryString += " and a.slotType=c.code ";
            queryString += " and c.type = ? ";
            parameterList.add(Constant.APP_PARAMS_SLOT_TYPE);
            if (f.getChassicId() != null) {
                queryString += " and a.chassicId = ? ";
                parameterList.add(f.getChassicId());
            }
            if (f.getSlotPosition() != null && !f.getSlotPosition().trim().equals("")) {
                queryString += " and a.slotPosition = ? ";
                parameterList.add(Long.parseLong(f.getSlotPosition().trim()));
            }
            if (f.getFreePort() != null && !f.getFreePort().trim().equals("")) {
                queryString += " and a.freePort = ? ";
                parameterList.add(Long.parseLong(f.getFreePort().trim()));
            }
            if (f.getSlotType() != null && !f.getSlotType().trim().equals("")) {
                queryString += " and a.slotType = ? ";
                parameterList.add(f.getSlotType().trim());
            }
            if (f.getStatus() != null) {
                queryString += " and a.status = ? ";
                parameterList.add(f.getStatus());
            }
            if (f.getMaxPort() != null && !f.getMaxPort().trim().equals("")) {
                queryString += " and a.maxPort = ? ";
                parameterList.add(Long.parseLong(f.getMaxPort().trim()));
            }
            if (f.getUsedPort() != null && !f.getUsedPort().trim().equals("")) {
                queryString += " and a.usedPort = ? ";
                parameterList.add(Long.parseLong(f.getUsedPort().trim()));
            }
            if (f.getInvalidPort() != null && !f.getInvalidPort().trim().equals("")) {
                queryString += " and a.invalidPort = ? ";
                parameterList.add(Long.parseLong(f.getInvalidPort().trim()));
            }
            if (f.getCardInstalledDate() != null && !f.getCardInstalledDate().trim().equals("")) {
                queryString += " and a.cardInstalledDate = ? ";
                parameterList.add(DateTimeUtils.convertStringToDate(f.getCardInstalledDate().trim()));
            }
            if (f.getStaPortPosition() != null && !f.getStaPortPosition().trim().equals("")) {
                queryString += " and a.staPortPosition = ? ";
                parameterList.add(Long.parseLong(f.getStaPortPosition().trim()));
            }
            if (f.getCardId() != null) {
                queryString += " and a.cardId = ? ";
                parameterList.add(f.getCardId());
            }


            queryString += " order by a.slotPosition ";

            Query queryObject = getSession().createQuery(queryString);


            queryObject.setMaxResults(SEARCH_RESULT_LIMIT);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }

    }

    public List findByChassicId(Object chassicId) {
        log.debug("finding Slot instance with property:");
        try {

            String queryString = "select new Slot(a.slotId,a.chassicId,a.slotPosition,a.freePort,a.status,a.cardInstalledDate,a.slotType,a.maxPort,a.usedPort,a.invalidPort,a.staPortPosition,a.cardId,b.code || ' - ' || b.name,c.name, (select name from AppParams app where app.type = '" + Constant.APP_PARAMS_SLOT_STATUS + "' and app.code = a.status )) "
                    + "from Slot a, Card b,AppParams c "
                    + "where a.cardId=b.cardId and a.chassicId=? ";
            queryString += " and a.slotType=c.code ";
            queryString += " and c.type = ? ";
            queryString += " order by a.slotPosition ";
            Query queryObject = getSession().createQuery(queryString);
            if (chassicId == null) {
                chassicId = -1L;
            }
            queryObject.setParameter(0, chassicId);
            queryObject.setParameter(1, Constant.APP_PARAMS_SLOT_TYPE);
            //lay ten Chassic
            HttpServletRequest req = getRequest();
            ChassicDAO chassicDAO = new ChassicDAO();
            chassicDAO.setSession(this.getSession());
            Chassic chassicBO = chassicDAO.findById((Long) chassicId);
            req.getSession().setAttribute("chassicCode", chassicBO.getCode());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }

    }

    public List findBySlotPosition(Object slotPosition) {
        return findByProperty(SLOT_POSITION, slotPosition);
    }

    public List findByFreePort(Object freePort) {
        return findByProperty(FREE_PORT, freePort);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findBySlotType(Object slotType) {
        return findByProperty(SLOT_TYPE, slotType);
    }

    public List findByMaxPort(Object maxPort) {
        return findByProperty(MAX_PORT, maxPort);
    }

    public List findByUsedPort(Object usedPort) {
        return findByProperty(USED_PORT, usedPort);
    }

    public List findByInvalidPort(Object invalidPort) {
        return findByProperty(INVALID_PORT, invalidPort);
    }

    public List findByStaPortPosition(Object staPortPosition) {
        return findByProperty(STA_PORT_POSITION, staPortPosition);
    }

    public List findByCardId(Object cardId) {
        return findByProperty(CARD_ID, cardId);
    }

    public List findAll() {
        log.debug("finding all Slot instances");
        try {
            String queryString = "from Slot";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Slot merge(Slot detachedInstance) {
        log.debug("merging Slot instance");
        try {
            Slot result = (Slot) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Slot instance) {
        log.debug("attaching dirty Slot instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Slot instance) {
        log.debug("attaching clean Slot instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    private void getListComboBox() throws Exception {
        HttpServletRequest req = getRequest();
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());

        List<AppParams> lstSlotStatus = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_SLOT_STATUS);
        req.getSession().setAttribute("lstSlotStatus", lstSlotStatus);

        List<AppParams> lstSlotType = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_SLOT_TYPE);
        req.getSession().setAttribute("lstSlotType", lstSlotType);
    }

    public String displaySlot() throws Exception {
        log.info("Begin method preparePage of ChassicDAO ...");

        HttpServletRequest req = getRequest();

        SlotForm f = this.slotForm;
        String strselectedChassicId = req.getParameter("selectedChassicId");
        Long chassicId;

        if (strselectedChassicId != null) {
            try {
                chassicId = new Long(strselectedChassicId);
            } catch (NumberFormatException ex) {
                chassicId = -1L;
            }
        } else {
            chassicId = f.getChassicId();
        }
        f.resetForm();
        f.setChassicId(chassicId);
        List lstSlot = new ArrayList();
        lstSlot = this.findByChassicId(chassicId);
        req.getSession().setAttribute("lstSlot", lstSlot);
        req.getSession().setAttribute("toEditSlot", 0);

//        CardDAO cardDAO = new CardDAO();
//        cardDAO.setSession(this.getSession());
//        List<Card> lstCard = cardDAO.findByStatus(Constant.STATUS_USE);
//        req.getSession().setAttribute("lstCard", lstCard);

        getListComboBox();

        pageForward = ADD_SLOT;

        log.info("End method preparePage of ChassicDAO");

        return pageForward;
    }

    public List getlistSerchedSlot() throws Exception {
        try {
            List slot = new ArrayList();
//            List colParameter = new ArrayList();
            String strHQL = "from Slot where 1=1";
//            strHQL += "and status = 1 ";

            //querry
            Query query = getSession().createQuery(strHQL);
//            for (int i = 0; i < colParameter.size(); i++) {
//                query.setParameter(i, colParameter.get(i));
//            }
            int resultLimit = SEARCH_RESULT_LIMIT + 1;
            query.setMaxResults(resultLimit);
            slot = query.list();
            return slot;


        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String prepareEditSlot() throws Exception {

        log.info("Begin method preparePage  ...");

        HttpServletRequest req = getRequest();
        SlotForm f = this.slotForm;
        String strSlotId = (String) QueryCryptUtils.getParameter(req, "slotId");
        Long slotID;
        slotID = Long.parseLong(strSlotId);
        Slot bo = new Slot();
        bo = this.findById(slotID);
        f.copyDataFromBO(bo);


        req.getSession().setAttribute("toEditSlot", 1);


        pageForward = ADD_SLOT;

        log.info("End method preparePage of SlotDao");

        return pageForward;
    }

    public String copySlot() throws Exception {

        log.info("Begin method preparePage  ...");

        HttpServletRequest req = getRequest();
        SlotForm f = this.slotForm;
        String strSlotId = (String) QueryCryptUtils.getParameter(req, "slotId");
        Long slotID;
        slotID = Long.parseLong(strSlotId);
        Slot bo = new Slot();
        bo = this.findById(slotID);
        f.copyDataFromBO(bo);
        f.setSlotId(0L);

        req.getSession().setAttribute("toEditSlot", 1);


        pageForward = ADD_SLOT;

        log.info("End method preparePage of SlotDao");

        return pageForward;
    }

    //THEM & SUA & COPY
    public String saveSlot() throws Exception {
        log.info("Begin method saveSlot of ...");
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        pageForward = ADD_SLOT;
        SlotForm f = this.slotForm;
        Long chassicId = f.getChassicId();

        try {
            if (f.getSlotId() == 0L) {
                if (checkValidateToAdd()) {
                    //TrongLV
                    //Check thong tin Chassic phai co hieu luc,
                    ChassicDAO chassicDAO = new ChassicDAO();
                    chassicDAO.setSession(mySession);
                    Chassic chassic = chassicDAO.findById(f.getChassicId());
                    if (chassic == null) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.171");
                        return pageForward;
                    }
                    if (chassic.getStatus() != null && !chassic.getStatus().toString().trim().equals(Constant.STATUS_USE.toString())) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.172");
                        return pageForward;
                    }

                    Slot bo = new Slot();
                    f.copyDataToBO(bo);
                    Long slotId = getSequence("SLOT_SEQ");
                    bo.setSlotId(slotId);
                    getSession().save(bo);


                    //Create Port
                    try {
                        if (bo.getStatus() != null && bo.getStatus().equals(Constant.STATUS_USE)) {
                            PortDAO portDAO = new PortDAO();
                            portDAO.setSession(getSession());
                            portDAO.setPortForm(new PortForm());
                            portDAO.getPortForm().setSlotId(bo.getSlotId());
                            String createPortResult = portDAO.genPort();

                            createPortResult = (String) req.getAttribute(Constant.RETURN_MESSAGE);
                            if (createPortResult == null || !createPortResult.equals("MSG.INF.Port.Success")) {
                                req.setAttribute(Constant.RETURN_MESSAGE, "Error. Can not create Port on Card!");
                                return pageForward;
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //Create Port

                    getSession().flush();
                    req.getSession().setAttribute("toEditSlot", 0);
                    req.setAttribute("returnMsg", "slot.add.success");
                    f.resetForm();
                }
            } else {
                if (checkValidateToEdit()) {
                    //TrongLV
                    //Check thong tin Chassic phai co hieu luc,
                    ChassicDAO chassicDAO = new ChassicDAO();
                    chassicDAO.setSession(mySession);
                    Chassic chassic = chassicDAO.findById(f.getChassicId());
                    if (chassic == null) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.171");
                        return pageForward;
                    }
                    if (chassic.getStatus() != null && !chassic.getStatus().toString().trim().equals(Constant.STATUS_USE.toString())) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.172");
                        return pageForward;
                    }

                    //TrongLV
                    //KIEM TRA DIEU KIEN CAP NHAT TRANG THAI VE KHONG HIEU LUC
                    //NEU CON Port CO HIEU LUC THI KHONG DUOC PHEP
                    if (f.getStatus() != null && f.getStatus().toString().trim().equals(Constant.SLOT_STATUS_NOT_USE.toString())) {
                        String strQuery = " select count(*) from Port as model where 1=1 and model.slotId = ? and (model.status= ? or model.status = ? ) ";
                        Query q = getSession().createQuery(strQuery);
                        q.setParameter(0, f.getSlotId());
                        q.setParameter(1, Constant.PORT_STATUS_USE);
                        q.setParameter(2, Constant.PORT_STATUS_LOCK);
                        Long count = (Long) q.list().get(0);
                        if ((count != null) && (count.compareTo(0L) > 0)) {
                            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.173");
                            return pageForward;
                        }
                    }

                    Slot bo = new Slot();
                    f.copyDataToBO(bo);
                    getSession().update(bo);
                    getSession().flush();
                    f.resetForm();
                    f.setChassicId(chassicId);
                    req.getSession().setAttribute("toEditSlot", 0);
                    req.setAttribute("returnMsg", "slot.edit.success");
                } else {
                    req.getSession().setAttribute("toEditSlot", 1);
                }
            }
            f.setChassicId(chassicId);
            pageForward = ADD_SLOT;
            List lstSlot = this.findByChassicId(chassicId);
            req.getSession().removeAttribute("lstSlot");
            req.getSession().setAttribute("lstSlot", lstSlot);
            log.info("End method preparePage of ");

        } catch (Exception ex) {
            ex.printStackTrace();
            pageForward = ADD_SLOT;
            List lstSlot = this.findByChassicId(chassicId);
            req.getSession().removeAttribute("lstSlot");
            req.getSession().setAttribute("lstSlot", lstSlot);

        }
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of SlotDao ...");

        HttpServletRequest req = getRequest();

        req.getSession().setAttribute("toEditSlot", 0);
        pageForward = "slotResult";

        log.info("End method preparePage of SlotDao");

        return pageForward;
    }

    public String deleteSlot() throws Exception {
        log.info("Begin method Delete ...");

        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        pageForward = ADD_SLOT;

        SlotForm f = this.slotForm;
        String strSlotId = (String) QueryCryptUtils.getParameter(req, "slotId");
        if (strSlotId == null || strSlotId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.168");
            return pageForward;
        }
        Long slotId = Long.parseLong(strSlotId);
        Long chassicId = -1L;

        try {
            HashMap<String, Object> result = new HashMap();
            result = deleteSlotBySlotId(mySession, slotId);

            if (result.get(SlotDAO.CHASSIC_ID) != null) {
                chassicId = Long.valueOf(result.get(SlotDAO.CHASSIC_ID).toString());
            }

            int count = -1;
            if (result.get(PortDAO.HASH_MAP_RESULT) != null) {
                count = Integer.valueOf(result.get(PortDAO.HASH_MAP_RESULT).toString());
            }
            if (count < 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, result.get(PortDAO.HASH_MAP_MESSAGE));
                mySession.clear();
                mySession.getTransaction().rollback();
                mySession.beginTransaction();
                return pageForward;
            }



            //TrongLV
            //KIEM TRA DIEU KIEN XOA
            //NEU CON PORT CO HIEU LUC THI KHONG DUOC PHEP

//            String strQuery = " select count(*) from Port as model where 1=1 and model.slotId = ? and (model.status= ? or model.status = ? ) ";
//            Query q = getSession().createQuery(strQuery);
//            q.setParameter(0, slotId);
//            q.setParameter(1, Constant.PORT_STATUS_USE);
//            q.setParameter(2, Constant.PORT_STATUS_LOCK);
//            Long count = (Long) q.list().get(0);
//            if ((count != null) && (count.compareTo(0L) > 0)) {
//                req.setAttribute(Constant.RETURN_MESSAGE, "Không được xoá slot nếu còn port có đang sử dụng hoặc đang khoá!");
//                return pageForward;
//            }
//
//            mySession.delete(bo);



            req.setAttribute(Constant.RETURN_MESSAGE, "slot.delete.success");
        } catch (Exception ex) {
            req.setAttribute(Constant.RETURN_MESSAGE, "slot.delete.failed");
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
        }
        req.getSession().setAttribute("toEditSlot", 0);
        f.resetForm();
        f.setChassicId(chassicId);

        CardDAO card = new CardDAO();
        card.setSession(mySession);

        List lstSlot = this.findByChassicId(chassicId);
        List lstCard = card.findAll();
        req.getSession().removeAttribute("lstSlot");
        req.getSession().removeAttribute("lstCard");

        req.getSession().setAttribute("lstSlot", lstSlot);
        req.getSession().setAttribute("lstCard", lstCard);

        pageForward = ADD_SLOT;

        log.info("End method preparePage ");

        return pageForward;
    }

    public String slotSearch() throws Exception {
        HttpServletRequest req = getRequest();
        log.info("Bắt đầu hàm searchDslam của ...");
        try {
            List lstSlot = new ArrayList();
            lstSlot = getListSlot();
            req.getSession().setAttribute("toEditSlot", 2);
            req.getSession().removeAttribute("lstSlot");
            req.getSession().setAttribute("lstSlot", lstSlot);
            if (lstSlot.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "slot.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(lstSlot.size());
                req.setAttribute("returnMsgParam", listMessageParam);
            }
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
        }

        ChassicDAO chassic = new ChassicDAO();
        chassic.setSession(this.getSession());
        CardDAO card = new CardDAO();
        card.setSession(this.getSession());
        List lstChassic = chassic.findAll();

        List lstCard = card.findAll();
        req.getSession().removeAttribute("lstCard");
        req.getSession().removeAttribute("lstChassic");
        req.getSession().setAttribute("lstCard", lstCard);
        req.getSession().setAttribute("lstChassic", lstChassic);



        pageForward = ADD_SLOT;

        log.info("End method");

        return pageForward;
    }

    private boolean checkValidateToAdd() {

        Long count;
        HttpServletRequest req = getRequest();


        Long cardId = this.slotForm.getCardId();


        Long status = this.slotForm.getStatus();

        if (cardId == null || cardId.equals(0L)) {
            req.setAttribute("returnMsg", "slot.error.invalidCardId");
            return false;
        }

        if ((status == null) || status.equals("")) {
            req.setAttribute("returnMsg", "slot.error.invalidStatus");
            return false;
        }


        String vlanStart = this.slotForm.getVlanStart();
        String vlanStop = this.slotForm.getVlanStop();
        if (vlanStart != null && !vlanStart.trim().equals("")) {
            try {
                int a = Integer.valueOf(vlanStart.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.076");
                return false;
            }
            this.slotForm.setVlanStart(vlanStart.trim());
        }
        if (vlanStop != null && !vlanStop.trim().equals("")) {
            try {
                int a = Integer.valueOf(vlanStop.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.077");
                return false;
            }
            this.slotForm.setVlanStop(vlanStop.trim());
        }


        String usedPort = this.slotForm.getUsedPort();
        String freePort = this.slotForm.getFreePort();
        String invalidPort = this.slotForm.getInvalidPort();
        String maxPort = this.slotForm.getMaxPort();

        int iUsedPort = 0;
        int iFreePort = 0;
        int iInvalidPort = 0;
        int iMaxPort = 0;

        if (usedPort != null && !usedPort.trim().equals("")) {
            try {
                iUsedPort = Integer.valueOf(usedPort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.083");
                return false;
            }
        }
        if (freePort != null && !freePort.trim().equals("")) {
            try {
                iFreePort = Integer.valueOf(freePort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.174");
                return false;
            }
        }
        if (invalidPort != null && !invalidPort.trim().equals("")) {
            try {
                iInvalidPort = Integer.valueOf(invalidPort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.175");
                return false;
            }
        }
        if (maxPort != null && !maxPort.trim().equals("")) {
            try {
                iMaxPort = Integer.valueOf(maxPort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.092");
                return false;
            }
        }
        if (iMaxPort < (iUsedPort + iFreePort + iInvalidPort)) {
            req.setAttribute("returnMsg", "ERR.INF.176");
            return false;
        }


        String sCardInstalledDate = this.slotForm.getCardInstalledDate();
        Date currDate = new Date();
        if ((sCardInstalledDate != null) && !sCardInstalledDate.trim().equals("")) {
            try {
                Date dCardInstalledDate = DateTimeUtils.convertStringToDate(sCardInstalledDate.trim());
                if (dCardInstalledDate.after(currDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.177");
                    return false;
                }

            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.178");
                return false;
            }
        }

        String sSlotPosition = this.slotForm.getSlotPosition();
        if ((sSlotPosition == null) || sSlotPosition.trim().equals("")) {
            req.setAttribute("returnMsg", "ERR.INF.179");
            return false;
        }

        String strQuery = "select count(*) from Slot model where 1=1 and model.slotPosition = ? and model.chassicId = ? ";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, Long.valueOf(sSlotPosition));
        q.setParameter(1, this.slotForm.getChassicId());

        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "Lỗi vị trí slot đã tồn tại!");
            return false;
        }

        return true;

    }

    private boolean checkValidateToEdit() {

//        Long count;
        HttpServletRequest req = getRequest();
        Long cardId = this.slotForm.getCardId();

        Long status = this.slotForm.getStatus();
        if (cardId == null || cardId.equals(0L)) {
            req.setAttribute("returnMsg", "slot.error.invalidCardId");
            return false;
        }

        if ((status == null) || status.equals("")) {
            req.setAttribute("returnMsg", "slot.error.invalidStatus");
            return false;
        }

        String usedPort = this.slotForm.getUsedPort();
        String freePort = this.slotForm.getFreePort();
        String invalidPort = this.slotForm.getInvalidPort();
        String maxPort = this.slotForm.getMaxPort();

        int iUsedPort = 0;
        int iFreePort = 0;
        int iInvalidPort = 0;
        int iMaxPort = 0;

        if (usedPort != null && !usedPort.trim().equals("")) {
            try {
                iUsedPort = Integer.valueOf(usedPort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "Lỗi số port sử dụng phải là kiểu số!");
                return false;
            }
        }
        if (freePort != null && !freePort.trim().equals("")) {
            try {
                iFreePort = Integer.valueOf(freePort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "Lỗi số port free phải là kiểu số!");
                return false;
            }
        }
        if (invalidPort != null && !invalidPort.trim().equals("")) {
            try {
                iInvalidPort = Integer.valueOf(invalidPort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "Lỗi số port hỏng phải là kiểu số!");
                return false;
            }
        }
        if (maxPort != null && !maxPort.trim().equals("")) {
            try {
                iMaxPort = Integer.valueOf(maxPort.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "Lỗi số port tối đa phải là kiểu số!");
                return false;
            }
        }
        if (iMaxPort < (iUsedPort + iFreePort + iInvalidPort)) {
            req.setAttribute("returnMsg", "Lỗi tổng port phải lớn port sử dụng + port free + port hỏng!");
            return false;
        }


        String sCardInstalledDate = this.slotForm.getCardInstalledDate();
        Date currDate = new Date();
        if ((sCardInstalledDate != null) && !sCardInstalledDate.trim().equals("")) {
            try {
                Date dCardInstalledDate = DateTimeUtils.convertStringToDate(sCardInstalledDate.trim());
                if (dCardInstalledDate.after(currDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi ngày cài đặt card không được lớn hơn ngày hiện tại!");
                    return false;
                }

            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi ngày cài đặt card không chính xác!");
                return false;
            }
        }

//210.245.12.221/democms
//        host:admin123

        String sSlotPosition = this.slotForm.getSlotPosition();
        if ((sSlotPosition == null) || sSlotPosition.trim().equals("")) {
            req.setAttribute("returnMsg", "Lỗi chưa nhập vị trí Slot!");
            return false;
        }

        String strQuery = "select count(*) from Slot model where 1=1 and model.slotPosition = ? and model.chassicId = ? and model.slotId <> ? ";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, Long.valueOf(sSlotPosition));
        q.setParameter(1, this.slotForm.getChassicId());
        q.setParameter(2, this.slotForm.getSlotId());
        Long count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "ERR.INF.180");
            return false;
        }

        return true;

    }

    public HashMap<String, Object> deleteSlotBySlotId(Session mySession, Long slotId) throws Exception {
        try {
            HashMap<String, Object> result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);

            Long chassicId = -1L;

            if (mySession == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "session time out!");
                return result;
            }
            if (slotId == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "slotId is null!");
                return result;
            }

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put(PortDAO.SLOT_ID, slotId);

            List listSlot = CommonDAO.findByProperty(mySession, SlotDAO.TABLE_SLOT, hashMap);
            if (listSlot != null && listSlot.size() > 0) {
                chassicId = ((Slot) listSlot.get(0)).getChassicId();
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.181");
                return result;
            }

            PortDAO portDAO = new PortDAO();
            result = portDAO.deletePortBySlotId(mySession, slotId);
            int count = -1;
            if (result.get(PortDAO.HASH_MAP_RESULT) != null) {
                count = Integer.valueOf(result.get(PortDAO.HASH_MAP_RESULT).toString());
            }
            if (count < 0) {
                return result;
            }

            result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);
            result.put(SlotDAO.CHASSIC_ID, chassicId);

            count = -1;
            count = CommonDAO.deleteObject(mySession, SlotDAO.TABLE_SLOT, hashMap);
            if (count > 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.182");
                result.put(PortDAO.HASH_MAP_RESULT, count);
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.183");
            }

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public HashMap<String, Object> deleteSlotByChassicId(Session mySession, Long chassicId) throws Exception {
        try {
            HashMap<String, Object> result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);

            if (mySession == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "session time out!");
                return result;
            }
            if (chassicId == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "chassicId is null!");
                return result;
            }

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put(SlotDAO.CHASSIC_ID, chassicId);
            hashMap.put("NOT_EQUAL." + SlotDAO.STATUS, Constant.SLOT_STATUS_NOT_USE);
            hashMap.put("NOT_EQUAL.." + SlotDAO.STATUS, Constant.SLOT_STATUS_LOCK);
            List listSlot = CommonDAO.findByProperty(mySession, SlotDAO.TABLE_SLOT, hashMap);
            if (listSlot != null && listSlot.size() > 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.184");
                return result;
            }

            hashMap = new HashMap();
            hashMap.put(SlotDAO.CHASSIC_ID, chassicId);
            listSlot = CommonDAO.findByProperty(mySession, SlotDAO.TABLE_SLOT, hashMap);
            if (listSlot == null || listSlot.size() == 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.181");
                result.put(PortDAO.HASH_MAP_RESULT, 0);
                return result;
            }

            int count = -1;
            int total = 0;
            for (int i = 0; i < listSlot.size(); i++) {
                HashMap<String, Object> resultTemp = this.deleteSlotBySlotId(mySession, ((Slot) listSlot.get(0)).getSlotId());

                count = -1;
                if (resultTemp.get(PortDAO.HASH_MAP_RESULT) != null) {
                    count = Integer.valueOf(resultTemp.get(PortDAO.HASH_MAP_RESULT).toString());
                }
                if (count < 0) {
                    result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.185");
                    return result;
                }
                total += count;
            }
            result.put(PortDAO.HASH_MAP_RESULT, total);
            if (total > 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.182");
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "ERR.INF.183");
            }

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public String displayMaxPort() throws Exception {
        try {

            HttpServletRequest req = getRequest();
            String cardId = req.getParameter("cardId");
            if (cardId != null && !cardId.trim().equals("")) {
                String queryString = "select totalPort from Card where cardId = ?";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, cardId.trim());
                List listObject = queryObject.list();
                if (listObject != null && !listObject.isEmpty() && listObject.get(0) != null) {
                    Object object = listObject.get(0).toString();
                    System.out.println("maxPort = ");
                    System.out.println(object);
                    this.listElement.put("maxPort", object);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            this.listElement.put("maxPort", "");
        }

        return "maxPort";
    }

    public String updateListCard() throws Exception {
        List listItems = new ArrayList();
        String[] header = {"", "--Select Card---"};
        listItems.add(header);

        try {
            HttpServletRequest httpServletRequest = getRequest();
            String cardType = httpServletRequest.getParameter("cardType");
            if (cardType != null && !"".equals(cardType.trim())) {
                String SQL_SELECT_EXCHANGE = "select cardId, code from Card where cardType = ? and status = ? order by code ";
                Query q = getSession().createQuery(SQL_SELECT_EXCHANGE);
                q.setParameter(0, cardType.trim());
                q.setParameter(1, Constant.STATUS_USE);
                List lst = q.list();
                if (lst != null) {
                    System.out.println("updateListCard: ");
                    System.out.println(lst.size());
                    listItems.addAll(lst);
                }                
            }
            this.listElement.put("cardId", listItems);

        } catch (Exception e) {
            e.printStackTrace();
            this.listElement.put("cardId", "");
        }

        return "listCard";
    }
}
