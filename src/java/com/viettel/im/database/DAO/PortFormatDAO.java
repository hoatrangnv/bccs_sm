package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.database.BO.PortFormat;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.client.form.PortFormatForm;
import com.viettel.im.client.bean.PortFormatBean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Equipment;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * PortFormat entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.PortFormat
 * @author MyEclipse Persistence Tools
 */
public class PortFormatDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PortFormatDAO.class);
    // property constants
    public static final String ID = "id";
    public static final String EQ_ID = "eqId";
    public static final String PORT_FORMAT = "portFormat";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    private static final String PAGE_FORWARD_PORT_FORMAT = "pagePortFormat";
    private static final String PAGE_FORWARD_LIST_PORT_FORMAT = "pageListPortFormat";
    private static final String SESSION_LIST_PORT_FORMAT = "listPortFormat";
    private final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem
    private final Long RESULT_MAX_RECORD = 1000L;
    private String pageForward;
    PortFormatForm portFormatForm = new PortFormatForm();

    public PortFormatForm getPortFormatForm() {
        return portFormatForm;
    }

    public void setPortFormatForm(PortFormatForm portFormatForm) {
        this.portFormatForm = portFormatForm;
    }

    // property constants
    public void save(PortFormat transientInstance) {
        log.debug("saving PortFormat instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(PortFormat persistentInstance) {
        log.debug("deleting PortFormat instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public PortFormat findByEqIdAndportFormat_BK(java.lang.Long eqId, java.lang.String portFormat) {
        try {
            String queryString = "from PortFormat as model where model.eqId = ? ";
            if (portFormat != null & !portFormat.trim().equals("")) {
                queryString += " and model.portFormat = ? ";
            }
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, eqId);
            if (portFormat != null & !portFormat.trim().equals("")) {
                queryObject.setParameter(1, portFormat.trim());
            }


            List<PortFormat> list = queryObject.list();
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
            return null;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public PortFormat findById(java.lang.Long id) {
        log.debug("getting PortFormat instance with id: " + id);
        try {
            PortFormat instance = (PortFormat) getSession().get(
                    "com.viettel.im.database.BO.PortFormat", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public PortFormat findByEqIdAndType(java.lang.Long eqId, java.lang.String type) {
        try {
            String queryString = "from PortFormat as model where model.eqId = ? and model.type = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, eqId);
            queryObject.setParameter(1, type);
            List<PortFormat> list = queryObject.list();
            if (list != null && list.size() == 1) {
                return list.get(0);
            }else 
              return null;
            

        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(PortFormat instance) {
        log.debug("finding PortFormat instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.PortFormat").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PortFormat instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from PortFormat as model where model."
                    + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all PortFormat instances");
        try {
            String queryString = "from PortFormat";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public PortFormat merge(PortFormat detachedInstance) {
        log.debug("merging PortFormat instance");
        try {
            PortFormat result = (PortFormat) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PortFormat instance) {
        log.debug("attaching dirty PortFormat instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(PortFormat instance) {
        log.debug("attaching clean PortFormat instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     * ----------------------------------------------------------------------- *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_PORT_FORMAT;

        HttpServletRequest req = getRequest();

        portFormatForm = new PortFormatForm();

        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

        log.info("End method preparePage of BrasIppoolDAO");

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method...");
        searchPortFormat();
        pageForward = PAGE_FORWARD_LIST_PORT_FORMAT;

        log.info("End method preparePage of DslamDAO");
        return pageForward;
    }

    public String searchPortFormat() throws Exception {
        log.info("Begin method searchPortFormat of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_PORT_FORMAT;

        HttpServletRequest req = getRequest();

        if (portFormatForm.getEqCode() != null && !portFormatForm.getEqCode().trim().equals("")) {
            EquipmentDAO eqDAO = new EquipmentDAO();
            eqDAO.setSession(this.getSession());
            List<Equipment> listEq = eqDAO.findByPropertyAndStatus(EquipmentDAO.CODE, portFormatForm.getEqCode().trim());
            if (listEq == null || listEq.size() <= 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.138");
                return pageForward;
            }
        }


        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

        log.info("End method searchPortFormat of BrasIppoolDAO");

        return pageForward;
    }

    public List<PortFormatBean> getListPortFormat() {

        List<PortFormatBean> lstResult = new ArrayList<PortFormatBean>();
        List lstParam = new ArrayList();
        StringBuffer queryString = new StringBuffer();

//        queryString.append("from Equipment a, AppParams b  where 1=1 and a.status = ? and a.equipmentType = b.code and b.type = ? ");
//        listParameter.add(Constant.STATUS_USE);
//
        queryString.append("select new com.viettel.im.client.bean.PortFormatBean"
                + "( "
                + "a.id, a.eqId, a.portFormat, a.status, a.type, b.code, b.name, "
                + "(select name from AppParams c where c.code = b.equipmentType and c.type = ?), "
                + "(select name from AppParams d where d.code = a.type and d.type = ? )"
                + ") "
                + "from PortFormat a, Equipment b "
                + "where 1=1 and a.eqId = b.equipmentId ");

        lstParam.add(Constant.APP_PARAMS_EQUIPMENT_TYPE);
        lstParam.add(Constant.APP_PARAMS_PORT_FORMAT_TYPE);

        if (portFormatForm.getEqId() != null) {
            queryString.append("and a.eqId = ? ");
            lstParam.add(portFormatForm.getEqId());
        }

        if (portFormatForm.getEqCode() != null && !portFormatForm.getEqCode().trim().equals("")) {
            queryString.append("and upper(b.code) = ? ");
            lstParam.add(portFormatForm.getEqCode().trim().toUpperCase());
        }

        if (portFormatForm.getPortFormat() != null && !portFormatForm.getPortFormat().equals("")) {
            queryString.append("and a.portFormat like ? ");
            lstParam.add("%" + portFormatForm.getPortFormat().trim() + "%");
        }

        if (portFormatForm.getStatus() != null) {
            queryString.append("and a.status = ? ");
            lstParam.add(portFormatForm.getStatus());
        }
        if (portFormatForm.getType() != null && !portFormatForm.getType().equals("")) {
            queryString.append("and a.type = ? ");
            lstParam.add(portFormatForm.getType());
        }

        queryString.append("and rownum <= ? ");
        lstParam.add(RESULT_MAX_RECORD);

        queryString.append("order by b.code ");
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            queryObject.setParameter(i, lstParam.get(i));
        }
        lstResult = queryObject.list();
        return lstResult;
    }

    public String addPortFormat() throws Exception {
        log.info("Begin method addPortFormat of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_PORT_FORMAT;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

        if (!validatePortFormat(req)) {
            getList(req, mySession);
            return pageForward;
        }

        if (portFormatForm.getEqId() == null) {
            getList(req, mySession);
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.117");
            return pageForward;
        }

        //CHECK DA TON TAI
        //PortFormat p = this.findByEqIdAndportFormat(portFormatForm.getEqId(), portFormatForm.getPortFormat());
        //PortFormat p = this.findByEqId(portFormatForm.getEqId());
        PortFormat p = this.findByEqIdAndType(portFormatForm.getEqId(), portFormatForm.getType());
        if (p != null) {
            portFormatForm.setEqId(null);
            getList(req, mySession);
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.139");
            return pageForward;
        }

        PortFormat portFormat = new PortFormat();
        portFormat.setEqId(portFormatForm.getEqId());
        portFormat.setPortFormat(portFormatForm.getPortFormat());
        portFormat.setStatus(portFormatForm.getStatus());
        portFormat.setType(portFormatForm.getType());

        portFormat.setId(this.getSequence("PORT_FORMAT_SEQ"));

        mySession.save(portFormat);

        portFormatForm = new PortFormatForm();

        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Port.AddNew.Success");



        log.info("End method addPortFormat of BrasIppoolDAO");

        return pageForward;
    }

    private boolean validatePortFormat(HttpServletRequest req) {
        boolean result = false;

        if (portFormatForm.getEqCode() == null || portFormatForm.getEqCode().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.117");
            return result;
        }
        portFormatForm.setEqCode(portFormatForm.getEqCode().trim().toUpperCase());

        if (portFormatForm.getType() == null || portFormatForm.getType().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.140");
            return result;
        }

        if (portFormatForm.getPortFormat() == null || portFormatForm.getPortFormat().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.141");
            return result;
        }
        portFormatForm.setPortFormat(portFormatForm.getPortFormat().trim());

        if (portFormatForm.getStatus() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.006");
            return result;
        }

        EquipmentDAO eqDAO = new EquipmentDAO();
        eqDAO.setSession(this.getSession());
        List<Equipment> listEq = eqDAO.findByPropertyAndStatus(EquipmentDAO.CODE, portFormatForm.getEqCode().trim());
        if (listEq == null || listEq.size() <= 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.147");
            return result;
        }
        portFormatForm.setEqId(listEq.get(0).getEquipmentId());



        result = !result;
        return result;
    }

    public String editPortFormat() throws Exception {
        log.info("Begin method editPortFormat of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_PORT_FORMAT;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

        if (!validatePortFormat(req)) {
            getList(req, mySession);
            return pageForward;
        }

        if (portFormatForm.getEqId() == null) {
            getList(req, mySession);
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.142");
            return pageForward;
        }

        //PortFormat portFormat = this.findByEqIdAndportFormat(portFormatForm.getEqId(), portFormatForm.getPortFormat());
        //PortFormat portFormat = this.findByEqId(portFormatForm.getEqId());
        PortFormat portFormat = this.findByEqIdAndType(portFormatForm.getEqId(), portFormatForm.getType());
        if (portFormat == null) {
            getList(req, mySession);
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.143");
            return pageForward;
        }

        portFormat.setPortFormat(portFormatForm.getPortFormat());
        portFormat.setStatus(portFormatForm.getStatus());

        mySession.update(portFormat);

        portFormatForm = new PortFormatForm();

        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.144");

        log.info("End method editPortFormat of BrasIppoolDAO");

        return pageForward;
    }

    public String prepareEditPortFormat() throws Exception {
        log.info("Begin method prepareEditBras of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_PORT_FORMAT;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

//        String eqId = req.getParameter("eqId");
//        String portFormatString = req.getParameter("portFormat");
//        if (eqId == null || eqId.trim().equals("") || portFormatString == null || portFormatString.trim().equals("")) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi chưa chọn định danh port cần sửa!");
//            return pageForward;
//        }

        //String id = req.getParameter("id");
        String id = (String) QueryCryptUtils.getParameter(req, "id");
        if (id == null || id.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.145");
            return pageForward;
        }


        //PortFormat portFormat = this.findByEqIdAndportFormat(Long.valueOf(eqId),portFormatString);
        //PortFormat portFormat = this.findByEqId(Long.valueOf(eqId));
        PortFormat portFormat = this.findById(Long.valueOf(id));

        if (portFormat == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.146");
            return pageForward;
        }

        EquipmentDAO eqDAO = new EquipmentDAO();
        eqDAO.setSession(this.getSession());
        Equipment eq = eqDAO.findById(portFormat.getEqId());
        if (eq != null) {
            portFormatForm.setEqId(eq.getEquipmentId());
            //portFormatForm.setEqCode(eq.getName());
            portFormatForm.setEqCode(eq.getCode());
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());
            AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_EQUIPMENT_TYPE, eq.getEquipmentType());
            if (appParams != null) {
                portFormatForm.setEqName(appParams.getName());
            }
        }

        portFormatForm.setId(portFormat.getId());
        portFormatForm.setPortFormat(portFormat.getPortFormat());
        portFormatForm.setStatus(portFormat.getStatus());
        portFormatForm.setType(portFormat.getType());
        req.setAttribute("portFormatType", portFormat.getType());
        req.setAttribute("editPortFormat","editPortFormat");

        log.info("End method prepareEditBras of BrasIppoolDAO");

        return pageForward;
    }

    public String deletePortFormat() throws Exception {
        log.info("Begin method deleteBras of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_PORT_FORMAT;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

//        String eqId = req.getParameter("eqId");
//        String portFormatString = req.getParameter("portFormat");
//        if (eqId == null || eqId.trim().equals("") || portFormatString == null || portFormatString.trim().equals("")) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "Lỗi chưa chọn định danh port cần xoá!");
//        }

        //String id = req.getParameter("id");
        String id = (String) QueryCryptUtils.getParameter(req, "id");
        if (id == null || id.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.145");
            return pageForward;
        }


        //PortFormat portFormat = this.findByEqIdAndportFormat(Long.valueOf(eqId),portFormatString);
        //PortFormat portFormat = this.findByEqId(Long.valueOf(eqId));
        PortFormat portFormat = this.findById(Long.valueOf(id));
        if (portFormat == null) {
            req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.146");
            return pageForward;
        }

        mySession.delete(portFormat);

        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.148");

        log.info("End method deleteBras of BrasIppoolDAO");

        return pageForward;
    }

    public String cancelEditPortFormat() throws Exception {
        log.info("Begin method cancelEditPortFormat of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_PORT_FORMAT;

        HttpServletRequest req = getRequest();

        portFormatForm = new PortFormatForm();

        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

        log.info("End method cancelEditPortFormat of BrasIppoolDAO");

        return pageForward;
    }

    public List<ImSearchBean> getListEq(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, b.name) ");
        queryString.append("from Equipment a, AppParams b  where 1=1 and a.status = ? and a.equipmentType = b.code and b.type = ? ");
        listParameter.add(Constant.STATUS_USE);
        listParameter.add(Constant.APP_PARAMS_EQUIPMENT_TYPE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(b.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.code ");
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }
        return listImSearchBean;
    }

    public Long getListEqSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Equipment a, AppParams b  where 1=1 and a.status = ? and a.equipmentType = b.code and b.type = ? ");
        listParameter.add(Constant.STATUS_USE);
        listParameter.add(Constant.APP_PARAMS_EQUIPMENT_TYPE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(b.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);

        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<Long> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getListEqName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, b.name) ");
        queryString.append("from Equipment a, AppParams b  where 1=1 and a.status = ? and a.equipmentType = b.code and b.type = ? ");
        listParameter.add(Constant.STATUS_USE);
        listParameter.add(Constant.APP_PARAMS_EQUIPMENT_TYPE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);

        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }

        return listImSearchBean;
    }

    private void getList(HttpServletRequest req, Session mySession) {

        req.setAttribute(SESSION_LIST_PORT_FORMAT, this.getListPortFormat());

        if (portFormatForm.getEqCode() != null && !portFormatForm.getEqCode().trim().equals("")) {
            EquipmentDAO eqDAO = new EquipmentDAO();
            eqDAO.setSession(mySession);
            List<Equipment> listEq = eqDAO.findByPropertyAndStatus(EquipmentDAO.CODE, portFormatForm.getEqCode().trim());
            if (listEq != null && listEq.size() > 0) {
                AppParamsDAO appParamsDAO = new AppParamsDAO();
                appParamsDAO.setSession(mySession);
                AppParams appParams = appParamsDAO.findAppParams(Constant.APP_PARAMS_EQUIPMENT_TYPE, listEq.get(0).getEquipmentType());
                if (appParams != null) {
                    portFormatForm.setEqName(appParams.getName());
                }
            }
        }

    }
}
