package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.BrasForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Bras;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import com.viettel.im.client.bean.BrasBean;
import com.viettel.security.util.StringEscapeUtils;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 *
 * @author AnDv
 *
 */
public class BrasDAO extends BaseDAOAction {

    /// property constants
    public static final String BRAS_ID = "brasId";
    public static final String CODE = "code";
    public static final String EQUIPMENT_ID = "equipmentId";
    public static final String NAME = "name";
    public static final String IP = "ip";
    public static final String AAA_IP = "aaaIp";
    public static final String STATUS = "status";
    public static final String DESCRIPTION = "description";
    public static final String SLOT = "slot";
    public static final String PORT = "port";
    public static final String ADD_NEW_BRAS = "bras";
    public static final int SEARCH_RESULT_LIMIT = 1000;
    private String pageForward;
    private static final Log log = LogFactory.getLog(BrasDAO.class);
    private BrasForm brasForm = new BrasForm();

    public BrasForm getBrasForm() {
        return brasForm;
    }

    public void setBrasForm(BrasForm brasForm) {
        this.brasForm = brasForm;
    }

    public void save(Bras transientInstance) {
        log.debug("saving Bras instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Bras persistentInstance) {
        log.debug("deleting Bras instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Bras findById(Long id) {
        log.debug("getting Bras instance with id: " + id);
        try {
            Bras instance = (Bras) getSession().get(
                    "com.viettel.im.database.BO.Bras", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Bras instance) {
        log.debug("finding Bras instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.Bras").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Bras instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Bras as model where model." + StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            queryString += " order by name ";
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

    public List findByIp(Object ip) {
        return findByProperty(IP, ip);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findAll() {
        log.debug("finding all Bras instances");
        try {
            String queryString = "from Bras";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /**
     *
     * @purpose: preparpage of brasDAO
     * @throws java.lang.Exception
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of BrasDAO ...");

        HttpServletRequest req = getRequest();

        //
        BrasForm f = this.brasForm;
        f.resetForm();

        //lay danh sach cac bras da co
        List brasList = getBrasList();
        req.getSession().removeAttribute("brasList");
        req.getSession().setAttribute("brasList", brasList);

        //
        req.getSession().setAttribute("toEditBras", 0);

        //lay list equipmenId
        getListEquipment();

        //
        pageForward = ADD_NEW_BRAS;
        log.info("End method preparePage of BrasDAO");
        return pageForward;
    }

    //sua list
    public String addNewBras() throws Exception {
        log.info("Begin method addNewBras of BrasDAO ...");

        HttpServletRequest req = getRequest();
        BrasForm f = this.brasForm;

        //Add new
        if (checkValidateToAdd()) {
            Bras bo = new Bras();
            f.copyDataToBO(bo);
            bo.setBrasId(getSequence("BRAS_ID_SEQ"));
            save(bo);
            f.resetForm();
            List brasList = new ArrayList();
            brasList = getBrasList();
            req.getSession().removeAttribute("brasList");
            req.getSession().setAttribute("brasList", brasList);
            req.setAttribute("returnMsg", "bras.add.success");

            req.getSession().setAttribute("toEditBras", 0);
        }
        getListEquipment();
        pageForward = ADD_NEW_BRAS;
        log.info("End method preparePage of BrasDAO");
        return pageForward;
    }//end of editBras

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of BrasDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of PartnerDAO");
        return pageForward;
    }

    /*Author: CuongNT
     * Purpose: prepagePage of edit
     */
    public String prepareEditBras() throws Exception {
        log.info("Begin method preparePage of BrasDAO ...");

        HttpServletRequest req = getRequest();
        BrasForm f = this.brasForm;

        String strBrasId = (String) QueryCryptUtils.getParameter(req, "brasId");
        Long brasId;
        try {
            brasId = Long.parseLong(strBrasId);
        } catch (Exception ex) {
            brasId = -1L;
        }
        getListEquipment();

        Bras bo = findById(brasId);
        f.copyDataFromBO(bo);
        f.setMessage("");
        //lay list equipmenId


        req.getSession().setAttribute("toEditBras", 1);

        pageForward = ADD_NEW_BRAS;
        log.info("End method preparePage of BrasDAO");
        return pageForward;
    }//end of prepare edit

    private void getListEquipment() {
        HttpServletRequest req = getRequest();

        //
        String queryString = "select new com.viettel.im.client.bean.EquipmentBean(a.equipmentId, a.name, b.vendorName) "
                + "from Equipment a, EquipmentVendor b "
                + "where a.equipmentType = ? and a.status = ? "
                + "and a.equipmentVendorId = b.equipmentVendorId "
                + "order by nlssort(b.vendorName,'nls_sort=Vietnamese') asc, "
                + "nlssort(a.name,'nls_sort=Vietnamese') asc ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, Constant.EQUIPMENT_TYPE_BRAS);
        queryObject.setParameter(1, Constant.STATUS_USE);
        List lstEquipment = queryObject.list();


        req.removeAttribute("lstEquipment");
        req.setAttribute("lstEquipment", lstEquipment);

    }

    /**
     *
     * @return bras list with status = 1, 2
     */
    public List getListBrasInUse() {
        log.debug("finding all Bras instances");
        try {
            String queryString = "from Bras where not status = 0";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String editBras() throws Exception {
        log.info("Begin method preparePage of BrasDAO ...");

        HttpServletRequest req = getRequest();
        BrasForm f = this.brasForm;

        // Edit
        if (checkValidateToEdit()) {
            Bras bo = new Bras();
            f.copyDataToBO(bo);
            Long brasId = f.getBrasId();
            bo.setBrasId(brasId);
            getSession().update(bo);
            f.resetForm();
            f.setMessage(("MSG.INF.Bras.Edit.Sucesss"));
            req.getSession().setAttribute("toEditBras", 0);
            // lay lai list
            getListEquipment();
            List brasList = new ArrayList();
            brasList = getBrasList();
            req.getSession().removeAttribute("brasList");
            req.getSession().setAttribute("brasList", brasList);
            req.setAttribute("returnMsg", "bras.edit.success");

        }
        getListEquipment();
        pageForward = ADD_NEW_BRAS;
        log.info("End method preparePage of BrasDAO");
        return pageForward;
    }//end of editBras

    // actionDeleteChannelType
    public String deleteBras() throws Exception {
        log.info("Begin method actionDelete  ...");
        HttpServletRequest req = getRequest();
        BrasForm f = this.brasForm;
        String result = null;

        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                Long brasId;
                if (QueryCryptUtils.getParameter(req, "brasId") != null) {
                    brasId = Long.parseLong(QueryCryptUtils.getParameter(req, "brasId"));
                    Bras type = this.findById(brasId);
                    //delete chuyen status ve ko hoat dong status = 2
                    if (type != null) {
                        if (checkValidateToDelete(brasId)) {
                            try {
                                getSession().delete(type);
//                                type.setStatus(Constant.STATUS_DELETE);
//                                getSession().update(type);
                                req.setAttribute("returnMsg", "bras.delete.success");
                            } catch (Exception ex) {
                                req.setAttribute("returnMsg", "bras.delete.failed");
                            }
                        } else {
                            req.setAttribute("returnMsg", "bras.delete.error");

                        }
                        getListEquipment();
                        List brasList = new ArrayList();
                        brasList = getBrasList();
                        req.getSession().removeAttribute("brasList");
                        req.getSession().setAttribute("brasList", brasList);
                    }

                    req.getSession().setAttribute("toEditBras", 0);
                    pageForward = ADD_NEW_BRAS;
                }
            } catch (Exception e) {
                result = "error: " + e.getMessage();
                e.printStackTrace();
                throw e;
            }

        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }

        return pageForward;
    }//end action delete

    public String searchBras() throws Exception {
        log.info("Begin method search  ...");
        HttpServletRequest req = getRequest();
        BrasForm f = this.brasForm;
        pageForward =
                Constant.ERROR_PAGE;
        try {

            List brasList = this.getBrasList();
            req.getSession().removeAttribute("brasList");
            getListEquipment();

            req.getSession().setAttribute("brasList", brasList);
            req.getSession().setAttribute("toEditBras", 0);
            req.setAttribute("returnMsg", "equipment.add.success");

            if (brasList.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "search.result");
                List listMessageParam = new ArrayList();
                listMessageParam.add(brasList.size());
                req.setAttribute("returnMsgParam", listMessageParam);

                req.getSession().setAttribute("brasList", brasList);
                req.getSession().setAttribute("toEditBras", 0);
            }

            pageForward = ADD_NEW_BRAS;
        } catch (Exception e) {
            getSession().clear();
            e.printStackTrace();
            throw e;
        }

        return pageForward;
    }

//end Search
    /**
     * purpose: get list bras
     */
    private List<BrasBean> getBrasList() throws Exception {
        try {
            BrasForm f = getBrasForm();
            String queryString = "select distinct new com.viettel.im.client.bean.BrasBean(a.brasId, "
                    + " a.equipmentId, a.code, a.name, a.ip, a.aaaIp, a.status, a.description, a.slot, a.port, (c.vendorName || ' - ' || b.name) as equipmentName)"
                    + " from Bras a, Equipment b, EquipmentVendor c ";

            List parameterList = new ArrayList();
            queryString +=
                    " where a.equipmentId = b.equipmentId ";
            queryString +=
                    " and b.equipmentVendorId = c.equipmentVendorId ";
//            queryString += " and a.status <> ? ";
//            parameterList.add(Constant.STATUS_DELETE);

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

            if (f.getStatus() != null && !f.getStatus().trim().equals("")) {
                queryString += " and a.status = ? ";
                parameterList.add(Long.parseLong(f.getStatus().trim()));
            }

            if (f.getAaaIp() != null && !f.getAaaIp().trim().equals("")) {
                queryString += " and a.aaaIp = ? ";
                parameterList.add(f.getAaaIp().trim());
            }

            if (f.getIp() != null && !f.getIp().trim().equals("")) {
                queryString += " and a.ip = ? ";
                parameterList.add(f.getIp().trim());
            }

            if (f.getSlot() != null && !f.getSlot().trim().equals("")) {
                queryString += " and a.slot = ? ";
                parameterList.add(f.getSlot().trim());
            }

            if (f.getPort() != null && !f.getPort().trim().equals("")) {
                queryString += " and a.port = ? ";
                parameterList.add(f.getPort().trim());
            }

            if (f.getDescription() != null && !f.getDescription().trim().equals("")) {
                queryString += " and upper(a.description) like ? ";
                parameterList.add("%" + f.getDescription().trim().toUpperCase() + "%");
            }

            queryString += "order by nlssort(a.code,'nls_sort=Vietnamese') asc ";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT);
            for (int i = 0; i
                    < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }

            return queryObject.list();

        } catch (Exception ex) {
            getSession().clear();
            ex.printStackTrace();
            return null;

        }

    }

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        BrasForm f = getBrasForm();
        String name = f.getName();
        String code = f.getCode();
        String equip = f.getEquipmentId();
        String ip = f.getIp();
        String status = f.getStatus();

        if ((code == null) || code.trim().equals("")) {
            req.setAttribute("returnMsg", "bras.error.invalidCode");
            return false;
        }
        f.setCode(code.trim());

        if ((name == null) || name.trim().equals("")) {
            req.setAttribute("returnMsg", "bras.error.invalidName");
            return false;
        }
        f.setName(name.trim());

        if ((ip == null) || ip.trim().equals("")) {
            req.setAttribute("returnMsg", "bras.error.invalidIp");
            return false;
        }
        f.setIp(ip.trim());

        if ((equip == null) || equip.equals("")) {
            req.setAttribute("returnMsg", "bras.error.invalidEquipment");
            return false;
        }

        if ((status == null) || status.equals("")) {
            req.setAttribute("returnMsg", "bras.error.invalidStatus");
            return false;
        }

        return true;

    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();
        Long count;

        BrasForm f = getBrasForm();
        String code = f.getCode();

        if (checkForm()) {
            String strQuery = "select count(*) from Bras as model where upper(model.code) = ? or model.ip = ? ";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.toUpperCase());
            q.setParameter(1, f.getIp());
            count =
                    (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "ERR.INF.149");
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

        BrasForm f = getBrasForm();
        String code = f.getCode();
        Long id = f.getBrasId();
        if (checkForm()) {

            String strQuery = "select count(*) from Bras as model where (upper(model.code)=? or model.ip = ? )and not model.brasId = ?";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.toUpperCase());
            q.setParameter(1, f.getIp());
            q.setParameter(2, id);
            count =
                    (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "ERR.INF.149");
                return false;
            }

        } else {
            return false;
        }

        return true;
    }

    /**
     * Check constraints to delete equipment
     */
    private boolean checkContraint(String table, Long equipmentId) {
        HttpServletRequest req = getRequest();
        Long count;

        String strQuery = "select count(*) from ";
        strQuery +=
                table;
        strQuery +=
                " as model where model.brasId=? and not model.status = 0";

        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, equipmentId);
        //q.setParameter(1, Constant.STATUS_DELETE);
        count =
                (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            //req.setAttribute("returnMsg", "equipment.edit.duplicateName");
            return false;
        }

        return true;
    }

    private boolean checkValidateToDelete(Long equipmentId) {
        return checkContraint("Switches", equipmentId);
    }
}
