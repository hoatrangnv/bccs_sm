package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.client.form.PortForm;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.UserToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Query;
import com.viettel.im.database.BO.Port;
import com.viettel.im.database.BO.Slot;
import java.util.Date;
import com.viettel.im.common.util.DateTimeUtils;

import com.viettel.im.database.BO.Card;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for Port
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 *
 * @see com.viettel.bccs.im.database.BO.Port
 * @author MyEclipse Persistence Tools
 */
public class PortDAO extends BaseDAOAction {

    public static final String ADD_PORT = "addPort";
    public static final String MANAGE = "manage";
    public static final int SEARCH_RESULT_LIMIT = 1000;
    public String pageForward;
    private static final Log log = LogFactory.getLog(PortDAO.class);
    // property constants
    public static final String VLAN_ID = "vlanId";
    public static final String SLOT_ID = "slotId";
    public static final String PORT_POSITION = "portPosition";
    public static final String STATUS = "status";
    public static final String TABLE_PORT = "Port";
    private Map<String, String> listSlot = new HashMap();
    private PortForm portForm = new PortForm();
    public static final String HASH_MAP_RESULT = "HASH_MAP_RESULT";
    public static final String HASH_MAP_MESSAGE = "HASH_MAP_MESSAGE";

    public void save(Port transientInstance) {
        log.debug("saving Port instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Port persistentInstance) {
        log.debug("deleting Port instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Port findById(java.lang.Long id) {
        log.debug("getting Port instance with id: " + id);
        try {
            Port instance = (Port) getSession().get(
                    "com.viettel.im.database.BO.Port", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Port instance) {
        log.debug("finding Port instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.bccs.im.database.BO.Port").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Port instance with property: " + propertyName + ", value: " + value);

        try {
            String queryString = "from Port as model where model." + propertyName + "= ? order by portPosition ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByVlanId(Object vlanId) {
        return findByProperty(VLAN_ID, vlanId);
    }

    public List findBySlotId(Object slotId) {
        if (slotId == null) {
            return null;
        }
        //Lay ten Card + vi tri Slot
        HttpServletRequest req = getRequest();
        SlotDAO slotDAO = new SlotDAO();
        slotDAO.setSession(this.getSession());
        Slot slotBO = slotDAO.findById((Long) slotId);
        CardDAO cardDAO = new CardDAO();
        cardDAO.setSession(this.getSession());
        Card card = cardDAO.findById(slotBO.getCardId());
        String node4 = card.getCode() + " - " + slotBO.getSlotPosition().toString();

        req.getSession().setAttribute("slotPosition", node4);

        List listParameter = new ArrayList();
        String strQuery = "from Port as model where 1=1";

        strQuery = "select new com.viettel.im.database.BO.Port(a.portId, a.vlanId, a.slotId, a.portPosition, a.status, a.createDate, (select name from AppParams b where b.type = ? and b.code = a.status )) "
                + "from Port a where 1=1 ";
        listParameter.add(Constant.APP_PARAMS_PORT_STATUS);

        strQuery += " and a.slotId = ? ";
        listParameter.add((Long) slotId);

        strQuery += " order by slotId, a.portPosition ";

        Query queryObject = getSession().createQuery(strQuery);
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }

        List listPort = new ArrayList();
        listPort = queryObject.list();


        return listPort;
    }

    public List findByPortPosition(Object portPosition) {
        return findByProperty(PORT_POSITION, portPosition);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all Port instances");
        try {
            String queryString = "from Port order by portPosition ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Port merge(Port detachedInstance) {
        log.debug("merging Port instance");
        try {
            Port result = (Port) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Port instance) {
        log.debug("attaching dirty Port instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Port instance) {
        log.debug("attaching clean Port instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------------------------*/
    public String preparePage_BK() throws Exception {
        HttpServletRequest req = getRequest();
        setPortForm(new PortForm());
        getPortForm().resetForm();

        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                List lstSlot = this.getSlot();
                List lstPort = this.findAll();
                req.getSession().setAttribute("lstSlot", lstSlot);
                req.getSession().setAttribute("lstPort", lstPort);
                pageForward = ADD_PORT;
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        return pageForward;
    }

    private void getListComboBox() throws Exception {
        HttpServletRequest req = getRequest();
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(this.getSession());

        List<AppParams> lstPortStatus = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_PORT_STATUS);
        req.getSession().setAttribute("lstPortStatus", lstPortStatus);
    }

    public String displayPort() throws Exception {
        log.info("Begin method preparePage of ChassicDAO ...");

        HttpServletRequest req = getRequest();

        PortForm f = this.portForm;
        String strselectedSlotId = req.getParameter("selectedSlotId");
        Long slotId;

        if (strselectedSlotId != null) {
            try {
                slotId = new Long(strselectedSlotId);
            } catch (NumberFormatException ex) {
                slotId = -1L;
            }
        } else {
            slotId = f.getSlotId();
        }
        f.resetForm();
        Date date = getSysdate();
        f.setCreateDate(DateTimeUtils.convertDateToString(date));
        f.setSlotId(slotId);
        List lstPort = new ArrayList();
        lstPort = this.findBySlotId(slotId);

        getListComboBox();


        req.getSession().setAttribute("lstPort", lstPort);
        req.getSession().setAttribute("toEditPort", 0);
//        req.setAttribute("chassicMode", "view");
        pageForward = ADD_PORT;

        log.info("End method preparePage of ChassicDAO");

        return pageForward;
    }

    public String addPort_BK() throws Exception {
        log.info("Begin method preparePage of PortDAO ...");
        HttpServletRequest req = getRequest();
        PortForm p = getPortForm();
        Long slotId = this.portForm.getSlotId();

        if (checkValidate()) {
            Port pBO = new Port();

//                    pBO.setSlotId(getSlotID(p.getSlotId()));
//                    pBO.setVlanId(p.getVlanId());
//                    pBO.setPortPosition(p.getPortPosition());
//                    pBO.setStatus(Constant.STATUS_USE.toString());

            p.copyDataToBO(pBO);
            Long portID = getSequence("PORT_SEQ");
            pBO.setPortId(portID);
            //Date tempDate = com.viettel.common.util.DateTimeUtils.getDate();
            //pBO.setCreateDate(tempDate);
            getSession().save(pBO);
            getSession().flush();
            p.resetForm();

            List lstPort = this.findBySlotId(slotId);
            req.getSession().setAttribute("lstPort", lstPort);
            req.getSession().setAttribute("toEditPort", 0);
            req.setAttribute("returnMsg", "port.add.success");
            p.resetForm();
        }
//                else {
//                    p.setMessage("Bạn phải nhập đầy đủ thông tin.");
//                    List lstPort = new ArrayList();
//                    lstPort = this.findAll();
//                    req.getSession().removeAttribute("lstPort");
//                    req.getSession().setAttribute("lstPort", lstPort);
//                }

        this.portForm.setSlotId(slotId);
        pageForward = ADD_PORT;
        log.info("End method preparePage of PortDAO");
        return pageForward;
    }

    /**
     * add/edit
     * @return
     * @throws Exception
     */
    public String savePort() throws Exception {
        log.info("Begin method preparePage of PortDAO ...");
        pageForward = ADD_PORT;

        Session mySession = this.getSession();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        PortForm p = getPortForm();
        Long slotId = this.portForm.getSlotId();

        if (p.getPortId() == 0L) {
            if (checkValidate()) {

                String[] propertyName = {"slotId", "portPosition"};
                Object[] value = {p.getSlotId(), Long.valueOf(p.getPortPosition())};
                List<Port> listPort = this.findByProperty(propertyName, value);
                if (listPort != null && listPort.size() > 0) {

                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute("returnMsg", "ERR.INF.125");
                    return pageForward;
                }

                SlotDAO slotDAO = new SlotDAO();
                slotDAO.setSession(mySession);
                Slot slot = slotDAO.findById(p.getSlotId());
                if (slot == null) {
                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.126");
                    return pageForward;
                }

                if (slot.getStatus() != null && slot.getStatus().toString().trim().equals(Constant.SLOT_STATUS_NOT_USE.toString())) {
                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute("returnMsg", "ERR.INF.127");
                    return pageForward;
                }


                CardDAO cardDAO = new CardDAO();
                cardDAO.setSession(mySession);
                Card card = cardDAO.findById(slot.getCardId());
                if (card == null) {
                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.128");
                    return pageForward;
                }

                int portPosition = Integer.valueOf(p.getPortPosition());
                int staPortPosition = 0;
                int totalCard = 0;
                if (slot.getStaPortPosition() != null) {
                    staPortPosition = slot.getStaPortPosition().intValue();
                }
                if (card.getTotalPort() != null) {
                    totalCard = card.getTotalPort().intValue();
                }
                if (portPosition < staPortPosition || portPosition >= totalCard - staPortPosition) {
                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.129");
                    return pageForward;
                }


                Port pBO = new Port();
                p.copyDataToBO(pBO);
                Long portID = getSequence("PORT_SEQ");
                pBO.setPortId(portID);
                pBO.setCreateDate(getSysdate());
                pBO.setCreateUserId(userToken.getUserID());
                pBO.setLastUpdateDate(getSysdate());
                pBO.setLastUpdateUserId(userToken.getUserID());
                pBO.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
                getSession().save(pBO);
                getSession().flush();
                req.getSession().setAttribute("toEditPort", 0);
                req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                req.setAttribute("returnMsg", "port.add.success");
                p.resetForm();
            }
        } else {
            if (checkValidate()) {

                Port bo = new Port();

                String[] propertyName = {"slotId", "portPosition"};
                Object[] value = {p.getSlotId(), Long.valueOf(p.getPortPosition())};
                List<Port> listPort = this.findByProperty(propertyName, value);
                if (listPort != null && listPort.size() > 0) {
                    if (listPort.get(0).getPortId().compareTo(p.getPortId()) != 0) {
                        req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                        req.setAttribute("returnMsg", "ERR.INF.125");
                        return pageForward;
                    }
                    bo = listPort.get(0);
                }

                SlotDAO slotDAO = new SlotDAO();
                slotDAO.setSession(mySession);
                Slot slot = slotDAO.findById(p.getSlotId());
                if (slot == null) {
                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.126");
                    return pageForward;
                }

                CardDAO cardDAO = new CardDAO();
                cardDAO.setSession(mySession);
                Card card = cardDAO.findById(slot.getCardId());
                if (card == null) {
                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.128");
                    return pageForward;
                }

                int portPosition = Integer.valueOf(p.getPortPosition());
                int staPortPosition = 0;
                int totalCard = 0;
                if (slot.getStaPortPosition() != null) {
                    staPortPosition = slot.getStaPortPosition().intValue();
                }
                if (card.getTotalPort() != null) {
                    totalCard = card.getTotalPort().intValue();
                }
                if (portPosition < staPortPosition || portPosition >= totalCard - staPortPosition) {
                    req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.129");
                    return pageForward;
                }


                p.copyDataToBO(bo);
                bo.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);
                bo.setLastUpdateDate(getSysdate());
                bo.setLastUpdateUserId(userToken.getUserID());
                getSession().update(bo);
                req.getSession().setAttribute("toEditPort", 0);
                req.getSession().setAttribute("lstPort", this.findBySlotId(slotId));

                req.setAttribute("returnMsg", "port.edit.success");
                p.resetForm();
            } else {
                req.getSession().setAttribute("toEditPort", 1);
            }
        }
        p.setSlotId(slotId);
        List lstPort = this.findBySlotId(slotId);
        req.getSession().removeAttribute("lstPort");
        req.getSession().setAttribute("lstPort", lstPort);
        this.portForm.setSlotId(slotId);

        log.info("End method preparePage of PortDAO");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of PortDAO ...");

        HttpServletRequest req = getRequest();

        req.getSession().setAttribute("toEditPort", 0);
        pageForward = "portResult";

        log.info("End method preparePage of PortDAO");

        return pageForward;
    }

    public String prepareEditPort() throws Exception {
        log.info("Begin method preparePage of PortDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        PortForm f = this.getPortForm();
        try {


            String strPortId = (String) QueryCryptUtils.getParameter(req, "portId");
            Long portId;
            try {
                portId = Long.parseLong(strPortId);
            } catch (Exception ex) {
                portId = -1L;
            }
            Port bo = this.findById(portId);
            f.copyDataFromBO(bo);
            session.setAttribute("toEditPort", 1);

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
        pageForward = ADD_PORT;
        log.info("End method preparePage of PortDAO");
        return pageForward;
    }

    public String copyPort() throws Exception {
        log.info("Begin method preparePage of PortDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        PortForm f = this.getPortForm();
        try {


            String strPortId = (String) QueryCryptUtils.getParameter(req, "portId");
            Long portId;
            try {
                portId = Long.parseLong(strPortId);
            } catch (Exception ex) {
                portId = -1L;
            }
            Port bo = this.findById(portId);
            f.copyDataFromBO(bo);
            f.setPortId(0L);
            session.setAttribute("toEditPort", 1);

        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
        pageForward = ADD_PORT;
        log.info("End method preparePage of PortDAO");
        return pageForward;
    }

    public String editPort() throws Exception {
        log.info("Begin method preparePage of PortDAO ...");

        HttpServletRequest req = getRequest();

        PortForm f = this.getPortForm();
        Long slotId = f.getSlotId();
        if (checkValidate()) {
            Port bo = new Port();
            f.copyDataToBO(bo);
            getSession().update(bo);

            f.setSlotId(slotId);
            req.getSession().setAttribute("toEditPort", 0);

            CardDAO card = new CardDAO();
            card.setSession(this.getSession());

            List lstPort = this.findBySlotId(slotId);

            req.getSession().removeAttribute("lstPort");
            req.setAttribute("returnMsg", "port.edit.success");
            req.getSession().setAttribute("lstPort", lstPort);
            f.resetForm();
        }
        f.setSlotId(slotId);
        pageForward = ADD_PORT;
        log.info("End method preparePage of PortDAO");
        return pageForward;
    }

    public String deletePort() throws Exception {
        log.info("Begin method preparePage of PortDAO ...");

        pageForward = ADD_PORT;

        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();

        PortForm f = this.getPortForm();


        String strPortId = (String) QueryCryptUtils.getParameter(req, "portId");
        if (strPortId == null || strPortId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Bạn chưa chọn Port!");
            return pageForward;
        }
        Long portId = Long.parseLong(strPortId);
        Long slotId = -1L;

//        Port bo = findById(portId);
//        if (bo == null){
//            req.setAttribute("returnMsg", "Lỗi không tồn tại thông tin Port!");
//            return pageForward;
//        }
//
//        slotId = bo.getSlotId();
//
//        if (bo.getStatus() != null && !bo.getStatus().trim().equals(Constant.PORT_STATUS_FREE)){
//            req.setAttribute("returnMsg", "Lỗi chỉ cho phép xoá port có trạng thái FREE!");
//            return pageForward;
//        }
//
//        getSession().delete(bo);
//        getSession().flush();
//        f.resetForm();
//        f.setSlotId(slotId);
//        req.getSession().setAttribute("toEditPort", 0);

        HashMap<String, Object> result = new HashMap();
        try {
            result = deletePortByPortId(mySession, portId);

            if (result.get(PortDAO.SLOT_ID) != null) {
                slotId = Long.valueOf(result.get(PortDAO.SLOT_ID).toString());
            }

            int count = -1;
            if (result.get(HASH_MAP_RESULT) != null) {
                count = Integer.valueOf(result.get(HASH_MAP_RESULT).toString());
            }
            if (count < 0) {
                req.setAttribute("returnMsg", result.get(HASH_MAP_MESSAGE));
                mySession.clear();
                mySession.getTransaction().rollback();
                mySession.beginTransaction();
                return pageForward;
            }

        } catch (Exception ex) {
            req.setAttribute("returnMsg", "ERR.INF.130");
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();
            return pageForward;
        }


        List lstPort = this.findBySlotId(slotId);
        req.getSession().removeAttribute("lstPort");
        req.getSession().setAttribute("lstPort", lstPort);
        req.setAttribute("returnMsg", "port.delete.success");

        pageForward = ADD_PORT;
        return pageForward;
    }

    public String searchPort() throws Exception {
        log.info("Begin method searchPort of PortDAO ...");

        HttpServletRequest req = getRequest();
        PortForm f = this.getPortForm();
        Session session = getSession();
        try {

            List listParameter = new ArrayList();
            //@anhlt: Get all port data
            String strQuery = "from Port as model where 1=1";

            strQuery = "select new com.viettel.im.database.BO.Port(a.portId, a.vlanId, a.slotId, a.portPosition, a.status, a.createDate, (select name from AppParams b where b.type = ? and b.code = a.status )) "
                    + "from Port a where 1=1 ";
            listParameter.add(Constant.APP_PARAMS_PORT_STATUS);

            if (f.getStatus() != null && !f.getStatus().toString().trim().equals("")) {
                strQuery += " and a.status = ? ";
                listParameter.add(f.getStatus());
            }
            if (f.getPortPosition() != null && !f.getPortPosition().trim().equals("")) {
                strQuery += " and a.portPosition = ? ";
                listParameter.add(Long.parseLong(f.getPortPosition().trim()));
            }
            if (f.getSlotId() != null) {
                strQuery += " and a.slotId = ? ";
                listParameter.add(f.getSlotId());
            }

            if (f.getVlanId() != null && !f.getVlanId().trim().equals("")) {
                strQuery += " and a.vlanId = ? ";
                listParameter.add(Long.parseLong(f.getVlanId().trim()));
            }

            if (f.getCreateDate() != null && !f.getCreateDate().trim().equals("")) {
                strQuery += " and a.createDate <= ? and a.createDate >=? ";
                listParameter.add(DateTimeUtils.convertStringToTime(f.getCreateDate().substring(0, 10) + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
                listParameter.add(DateTimeUtils.convertStringToTime(f.getCreateDate().substring(0, 10) + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }

            strQuery += " order by slotId, a.portPosition ";

            Query queryObject = session.createQuery(strQuery);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT);
            for (int i = 0; i < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));
            }

            List listPort = new ArrayList();
            listPort = queryObject.list();

            req.getSession().setAttribute("toEditPort", 0);
            if (listPort.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "dslam.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listPort.size());
                req.setAttribute("returnMsgParam", listMessageParam);
            }



            req.getSession().removeAttribute("lstPort");
            req.getSession().setAttribute("lstPort", listPort);
            log.info("End method searchPort of PortDAO");

        } catch (Exception ex) {
            log.debug("Loi khi GET: " + ex.toString());
            ex.printStackTrace();
            req.getSession().setAttribute("lstPort", null);
        }
        pageForward = ADD_PORT;
        return pageForward;

    }

    public List getSlot() throws Exception {
        try {
            SlotDAO slot = new SlotDAO();
            List lstSlot = new ArrayList();
            //lstSlot = slot.findAll();
            return lstSlot;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getSlotAuto() throws Exception {

        try {
            HttpServletRequest httpServletRequest = getRequest();
            HttpSession session = httpServletRequest.getSession();
            String pSlot = httpServletRequest.getParameter("portForm.slotId");
            List<Slot> lSlot = new ArrayList();
            if (pSlot != null && pSlot.length() > 0) {
                lSlot = findSlot(pSlot);
                if (lSlot != null && lSlot.size() > 0) {
                    Iterator iterator = lSlot.iterator();
                    while (iterator.hasNext()) {
                        Slot a = (Slot) iterator.next();
                        listSlot.put(a.getSlotId().toString(), a.getSlotPosition().toString());
                    }
                }
            }
            pageForward = SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pageForward;
    }

    public List findSlot(String pSlot) throws Exception {
        log.debug("finding findAreaByPSTN available Area instances");
        try {

            if (pSlot == null || pSlot.trim().length() == 0) {
                return null;
            }
            String queryString = " from Slot a where a.status=1 AND to_char(a.slotPosition) like  ?  ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, '%' + pSlot.trim() + '%');
            return queryObject.list();
        } catch (HibernateException re) {
            log.error("HibernateException at findAreaByPSTN : " + re.getMessage());
            re.printStackTrace();
            throw new Exception(re);
        }
    }

    public Long getSlotID(Long slotPost) {
        log.debug("get shop ID by staff ID");
        try {
            String queryString = "from Slot where slotPosition = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, slotPost);
            if (queryObject.list() != null && queryObject.list().size() > 0) {
                Slot staff = (Slot) queryObject.list().get(0);
                return staff.getSlotId();
            }
            log.debug("get successful");
            return null;
        } catch (RuntimeException re) {
            log.error("get fail", re);
            throw re;
        }
    }

    /**
     * @return the portForm
     */
    public PortForm getPortForm() {
        return portForm;
    }

    /**
     * @param portForm the portForm to set
     */
    public void setPortForm(PortForm portForm) {
        this.portForm = portForm;
    }

    /**
     * @return the listSlot
     */
    public Map<String, String> getListSlot() {
        return listSlot;
    }

    /**
     * @param listSlot the listSlot to set
     */
    public void setListSlot(Map<String, String> listSlot) {
        this.listSlot = listSlot;
    }

    private boolean checkValidate() {
        HttpServletRequest req = getRequest();

        Long slotId = this.portForm.getSlotId();
        if (slotId == null) {
            req.setAttribute("returnMsg", "ERR.INF.168");
            return false;
        }

        String portPosition = this.portForm.getPortPosition();
        Long status = this.portForm.getStatus();
        String vlanId = this.portForm.getVlanId();
        String dateCreated = this.portForm.getCreateDate();
        if (portPosition == null || portPosition.trim().equals("")) {
            req.setAttribute("returnMsg", "ERR.INF.131");
            return false;
        }
//        if (vlanId == null || vlanId.trim().equals("")) {
//            req.setAttribute("returnMsg", "port.error.invalidVlanId");
//            return false;
//        }
        if ((status == null)) {
            req.setAttribute("returnMsg", "port.error.invalidStatus");
            return false;
        }

        if ((dateCreated != null) && !dateCreated.trim().equals("")) {

            Date date;
            Date currentDate = DateTimeUtils.getSysDate();
            try {
                date = DateTimeUtils.convertStringToDate(dateCreated.trim());
                if (date.after(currentDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.132");
                    return false;
                }

            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.133");
                return false;
            }
        }

        this.portForm.setPortPosition(portPosition.trim());
        if (vlanId != null && !vlanId.trim().equals("")) {
            this.portForm.setVlanId(vlanId.trim());
            try {
                int a = Integer.valueOf(this.portForm.getVlanId());
                if (a < 0) {
                    req.setAttribute("returnMsg", "ERR.INF.134");
                    return false;
                }
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.135");
                return false;
            }
        }
        this.portForm.setStatus(status);

        try {
            int a = Integer.valueOf(this.portForm.getPortPosition());
            if (a < 0) {
                req.setAttribute("returnMsg", "ERR.INF.136");
                return false;
            }
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "ERR.INF.135");
            return false;
        }

        return true;

    }

    /**
     * Sinh port tu dong
     * @return
     * @throws Exception
     */
    public String genPort() throws Exception {
        pageForward = ADD_PORT;
        Session mySession = this.getSession();
        if (mySession == null) {
            return "sessionTimeout";
        }
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        
        PortForm f = this.getPortForm();
        if (f.getSlotId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.126");
            return pageForward;
        }
//        if (f.getCardId() == null){
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi không tìm thấy thông tin card!");
//            return pageForward;
//        }

        SlotDAO slotDAO = new SlotDAO();
        slotDAO.setSession(mySession);
        Slot slot = slotDAO.findById(f.getSlotId());
        if (slot == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.126");
            return pageForward;
        }
        if (slot.getStatus().toString().equals(Constant.SLOT_STATUS_NOT_USE.toString())) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.127");
            return pageForward;
        }

        CardDAO cardDAO = new CardDAO();
        cardDAO.setSession(mySession);
        Card card = cardDAO.findById(slot.getCardId());
        if (card == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.128");
            return pageForward;
        }

        try {
            HashMap<String, Object> hashMap = this.deletePortBySlotId(mySession, f.getSlotId());

            int count = -1;
            if (hashMap.get(PortDAO.HASH_MAP_RESULT) != null) {
                count = Integer.valueOf(hashMap.get(PortDAO.HASH_MAP_RESULT).toString());
            }
            if (count < 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.137");
                return pageForward;
            }
        } catch (Exception ex) {
        }

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("delete from Port where slotId = ? ");
        listParameter.add(f.getSlotId());
        Query queryObject = mySession.createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }

        int result = queryObject.executeUpdate();

        int totalPort = 0;
        Long count = 0L;

        if (card.getTotalPort() != null) {
            totalPort = card.getTotalPort().intValue();
        }
        if (slot.getStaPortPosition() != null) {
            count = slot.getStaPortPosition();
        }

        while (totalPort > 0) {
            Port newPort = new Port();
            newPort.setPortId(this.getSequence("PORT_SEQ"));
            newPort.setSlotId(slot.getSlotId());
            newPort.setPortPosition(count);
            newPort.setStatus(Constant.PORT_STATUS_FREE);
            newPort.setCreateDate(getSysdate());
            newPort.setCreateUserId(userToken.getUserID());
            newPort.setLastUpdateDate(getSysdate());
            newPort.setLastUpdateUserId(userToken.getUserID());
            newPort.setLastUpdateKey(Constant.LAST_UPDATE_KEY_WEB);

            mySession.save(newPort);

            count = count + 1;
            totalPort--;
        }

        List lstPort = this.findBySlotId(slot.getSlotId());
        req.getSession().setAttribute("lstPort", lstPort);

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Port.Success");
        return pageForward;
    }

    /**
     * @Author          :   TrongLV
     * @CreateDate      :   14/05/2010
     * @Purpose         :
     * @param request   :
     * @param logger    :
     * @return          :
     */
    public List findByProperty(String[] propertyName, Object[] value) {
        log.debug("finding Port instance with property: " + propertyName
                + ", value: " + value);
        try {
            if (propertyName.length != value.length) {
                return null;
            }
            List lstParam = new ArrayList();
            String queryString = "from Port as model where 1=1 ";
            for (int i = 0; i < propertyName.length; i++) {
                if (propertyName[i] == null || propertyName[i].trim().equals("")) {
                    continue;
                }
                queryString += " and model." + propertyName[i].trim() + " = ? ";
                lstParam.add(value[i]);
            }
            queryString += " order by model.slotId, model.portPosition ";
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

    public HashMap<String, Object> deletePortByPortId(Session mySession, Long portId) throws Exception {
        try {
            HashMap<String, Object> result = new HashMap();
            result.put(HASH_MAP_RESULT, -1);

            Long slotId = -1L;

            if (mySession == null) {
                result.put(HASH_MAP_MESSAGE, "session time out!");
                return result;
            }
            if (portId == null) {
                result.put(HASH_MAP_MESSAGE, "portId is null!");
                return result;
            }

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("portId", portId);
            List listPort = CommonDAO.findByProperty(mySession, PortDAO.TABLE_PORT, hashMap);
            if (listPort != null && listPort.size() > 0) {
                Long status = ((Port) listPort.get(0)).getStatus();
                if (status.equals(Constant.PORT_STATUS_USE)) {
                    result.put(HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.Use");
                    return result;
                }
                if (status.equals(Constant.PORT_STATUS_LOCK)) {
                    result.put(HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.Lock");
                    return result;
                }

                slotId = ((Port) listPort.get(0)).getSlotId();
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.NotExist");
                return result;
            }

            hashMap = new HashMap();
            hashMap.put("portId", portId);
            int count = CommonDAO.deleteObject(mySession, PortDAO.TABLE_PORT, hashMap);
            if (count > 0) {
                result.put(HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.Success");
                result.put(HASH_MAP_RESULT, count);
            } else {
                result.put(HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.Not");
            }

            result.put(PortDAO.SLOT_ID, slotId);

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public HashMap<String, Object> deletePortBySlotId(Session mySession, Long slotId) throws Exception {
        try {
            HashMap<String, Object> result = new HashMap();
            result.put(HASH_MAP_RESULT, -1);

            if (mySession == null) {
                result.put(HASH_MAP_MESSAGE, "Session time out!");
                return result;
            }
            if (slotId == null) {
                result.put(HASH_MAP_MESSAGE, "slotId is null!");
                return result;
            }

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put(PortDAO.SLOT_ID, slotId);
            hashMap.put("NOT_EQUAL...status", Constant.PORT_STATUS_FREE);
            hashMap.put("NOT_EQUAL..status", Constant.PORT_STATUS_NOT_USE);
            hashMap.put("NOT_EQUAL.status", Constant.PORT_STATUS_LEASEDLINE);
            List listPort = CommonDAO.findByProperty(mySession, PortDAO.TABLE_PORT, hashMap);
            if (listPort != null && listPort.size() > 0) {
                result.put(HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.SlotUnAvaiable");
                return result;
            }

            hashMap = new HashMap();
            hashMap.put(PortDAO.SLOT_ID, slotId);
            int count = CommonDAO.deleteObject(mySession, PortDAO.TABLE_PORT, hashMap);
            if (count >= 0) {
                result.put(HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.Success");
                result.put(HASH_MAP_RESULT, count);
            } else {
                result.put(HASH_MAP_MESSAGE, "MSG.INF.Port.Delete.Not");
            }

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }
}
