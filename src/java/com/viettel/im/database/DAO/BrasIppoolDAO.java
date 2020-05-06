package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.BrasIppoolForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Bras;
import com.viettel.im.database.BO.Domain;
import com.viettel.im.database.BO.BrasIppool;
import com.viettel.security.util.StringEscapeUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * BrasIppool entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.BrasIppool
 * @author MyEclipse Persistence Tools
 */
public class BrasIppoolDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(BrasIppoolDAO.class);
    // property constants
    public static final String POOL_ID = "poolId";
    public static final String BRAS_IP = "brasIp";
    public static final String POOLNAME = "poolname";
    public static final String POOLUNIQ = "pooluniq";
    public static final String DOMAIN = "domain";
    public static final String IP_POOL = "ipPool";
    public static final String IP_MASK = "ipMask";
    public static final String STATUS = "status";
    public static final String TABLE_NAME = "BrasIppool";
    private static final String PAGE_FORWARD_BRAS_IP_POOL = "pageBrasIpPool";
    private static final String PAGE_FORWARD_LIST_BRAS_IP_POOL = "pageListBrasIpPool";
    private static final String SESSION_LIST_BRAS_IP_POOL = "listBrasIppool";
    private final Long MAX_SEARCH_RESULT = 100L; //gioi han so row tra ve doi voi tim kiem
    private final Long RESULT_MAX_RECORD = 1000L;
    private String pageForward;
    BrasIppoolForm brasIppoolForm = new BrasIppoolForm();

    public BrasIppoolForm getBrasIppoolForm() {
        return brasIppoolForm;
    }

    public void setBrasIppoolForm(BrasIppoolForm brasIppoolForm) {
        this.brasIppoolForm = brasIppoolForm;
    }

    public void save(BrasIppool transientInstance) {
        log.debug("saving BrasIppool instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(BrasIppool persistentInstance) {
        log.debug("deleting BrasIppool instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public BrasIppool findById(Long id) {
        log.debug("getting BrasIppool instance with id: " + id);
        try {
            BrasIppool instance = (BrasIppool) getSession().get(
                    "com.viettel.im.database.BO.BrasIppool", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(BrasIppool instance) {
        log.debug("finding BrasIppool instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.BrasIppool").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding BrasIppool instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from BrasIppool as model where model." + StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByBrasIp(Object brasIp) {
        return findByProperty(BRAS_IP, brasIp);
    }

    public List findByPoolname(Object poolname) {
        return findByProperty(POOLNAME, poolname);
    }

    public List findByPooluniq(Object pooluniq) {
        return findByProperty(POOLUNIQ, pooluniq);
    }

    public List findByDomain(Object domain) {
        return findByProperty(DOMAIN, domain);
    }

    public List findByIpPool(Object ipPool) {
        return findByProperty(IP_POOL, ipPool);
    }

    public List findByIpMask(Object ipMask) {
        return findByProperty(IP_MASK, ipMask);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all BrasIppool instances");
        try {
            String queryString = "from BrasIppool";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public BrasIppool merge(BrasIppool detachedInstance) {
        log.debug("merging BrasIppool instance");
        try {
            BrasIppool result = (BrasIppool) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(BrasIppool instance) {
        log.debug("attaching dirty BrasIppool instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(BrasIppool instance) {
        log.debug("attaching clean BrasIppool instance");
        try {
            getSession().lock(instance, LockMode.NONE);
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

        List<AppParams> lstBrasIppoolStatus = appParamsDAO.findAppParamsByType(Constant.APP_PARAMS_BRAS_IPPOOL_STATUS);
        req.setAttribute("lstBrasIppoolStatus", lstBrasIppoolStatus);

        if (brasIppoolForm != null && brasIppoolForm.getDomain() != null && !brasIppoolForm.getDomain().trim().equals("")) {
            DomainDAO domainDAO = new DomainDAO();
            domainDAO.setSession(this.getSession());
            List<Domain> listDomain = domainDAO.findByDomain(brasIppoolForm.getDomain().trim());
            if (listDomain != null && listDomain.size() > 0) {
                brasIppoolForm.setDomainDescription(listDomain.get(0).getDescription());
            }
        }

        if (brasIppoolForm != null && brasIppoolForm.getBrasIp() != null && !brasIppoolForm.getBrasIp().trim().equals("")) {
            BrasDAO brasDAO = new BrasDAO();
            brasDAO.setSession(this.getSession());
            List<Bras> listBras = brasDAO.findByIp(brasIppoolForm.getBrasIp().trim());
            if (listBras != null && listBras.size() > 0) {
                brasIppoolForm.setBrasAaaIp(listBras.get(0).getAaaIp());
            }
        }
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        getListComboBox();

        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());

        brasIppoolForm.setDateUpdate(getSysdate());

        log.info("End method preparePage of BrasIppoolDAO");

        return pageForward;

    }

    public String pageNavigator() throws Exception {
        log.info("Begin method...");
        HttpServletRequest req = getRequest();
        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppoolNavigator());

        log.info("End method preparePage of DslamDAO");
        pageForward = PAGE_FORWARD_LIST_BRAS_IP_POOL;
        return pageForward;
    }

    public String searchBrasIppool() throws Exception {
        log.info("Begin method searchBrasIppool of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        List lst = this.getListBrasIppool();
        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, lst);

        if (lst != null) {
            req.setAttribute(Constant.RETURN_MESSAGE, getText("MSG.INF.BrasIpool.Found") + String.valueOf(lst.size()) + getText("MSG.INF.BrasIpool.Avaiable"));
        } else {
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.NotFound");
        }

        getListComboBox();

        log.info("End method searchBrasIppool of BrasIppoolDAO");

        return pageForward;
    }

    public List<BrasIppool> getListBrasIppool() {

        List<BrasIppool> lstResult = new ArrayList<BrasIppool>();
        List lstParam = new ArrayList();
        StringBuffer queryString = new StringBuffer();

        queryString.append("select new BrasIppool(a.poolId,a.brasIp,a.poolname,a.pooluniq,a.domain,a.ipPool,a.ipMask,a.status,a.dateUpdate, (select name from AppParams ap where ap.type = ? and ap.code = a.status )) ");
        queryString.append("from BrasIppool a where 1=1 ");
        lstParam.add(Constant.APP_PARAMS_BRAS_IPPOOL_STATUS);

        queryString.append("and rownum <= ? ");
        lstParam.add(RESULT_MAX_RECORD);

        if (brasIppoolForm.getIpPool() != null && !brasIppoolForm.getIpPool().trim().equals("")) {
            queryString.append("and a.ipPool = ? ");
            lstParam.add(brasIppoolForm.getIpPool().trim());
        }

        if (brasIppoolForm.getPoolname() != null && !brasIppoolForm.getPoolname().trim().equals("")) {
            queryString.append("and a.poolname = ? ");
            lstParam.add(brasIppoolForm.getPoolname().trim());
        }

        if (brasIppoolForm.getDomain() != null && !brasIppoolForm.getDomain().trim().equals("")) {
            queryString.append("and a.domain = ? ");
            lstParam.add(brasIppoolForm.getDomain().trim());
        }

        if (brasIppoolForm.getDomain() != null && !brasIppoolForm.getDomain().trim().equals("")) {
            queryString.append("and upper(a.domain) like ? ");
            lstParam.add("%" + brasIppoolForm.getDomain().trim().toUpperCase() + "%");
        }

        if (brasIppoolForm.getBrasIp() != null && !brasIppoolForm.getBrasIp().trim().equals("")) {
            queryString.append("and a.brasIp = ? ");
            lstParam.add(brasIppoolForm.getBrasIp().trim());
        }

        if (brasIppoolForm.getStatus() != null) {
            queryString.append("and a.status = ? ");
            lstParam.add(brasIppoolForm.getStatus());
        }

        try {
            if (brasIppoolForm.getDateUpdate() != null) {
                queryString.append("and a.dateUpdate >= to_date(?,'dd/MM/yyyy') ");
                lstParam.add(DateTimeUtils.convertDateTimeToString(brasIppoolForm.getDateUpdate(), "dd/MM/yyyy"));
                queryString.append("and a.dateUpdate < to_date(?,'dd/MM/yyyy') + 1 ");
                lstParam.add(DateTimeUtils.convertDateTimeToString(brasIppoolForm.getDateUpdate(), "dd/MM/yyyy"));
            }
        } catch (Exception ex) {
        }

        queryString.append("order by a.ipPool ");
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            queryObject.setParameter(i, lstParam.get(i));
        }
        lstResult = queryObject.list();
        return lstResult;
    }

    public List<BrasIppool> getListBrasIppoolNavigator() {

        List<BrasIppool> lstResult = new ArrayList<BrasIppool>();
        List lstParam = new ArrayList();
        StringBuffer queryString = new StringBuffer();

        queryString.append("select new BrasIppool(a.poolId,a.brasIp,a.poolname,a.pooluniq,a.domain,a.ipPool,a.ipMask,a.status,a.dateUpdate, (select name from AppParams ap where ap.type = ? and ap.code = a.status )) ");
        queryString.append("from BrasIppool a where 1=1 ");
        lstParam.add(Constant.APP_PARAMS_BRAS_IPPOOL_STATUS);

        queryString.append("and rownum <= ? ");
        lstParam.add(RESULT_MAX_RECORD);

        queryString.append("order by a.ipPool ");
        Query queryObject = getSession().createQuery(queryString.toString());
        for (int i = 0; i < lstParam.size(); i++) {
            queryObject.setParameter(i, lstParam.get(i));
        }
        lstResult = queryObject.list();
        return lstResult;
    }

    public String addBrasIppool() throws Exception {
        log.info("Begin method addBrasIppool of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

        if (!validateBrasIppool(req, mySession)) {
            getListComboBox();
            req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
            return pageForward;
        }

        BrasIppool brasIppool = new BrasIppool();
        brasIppool.setPoolId(this.getSequence("BRAS_IPPOOL_SEQ"));
        brasIppool.setBrasIp(brasIppoolForm.getBrasIp());
        brasIppool.setPoolname(brasIppoolForm.getPoolname());
        brasIppool.setPooluniq(brasIppoolForm.getPooluniq());
        brasIppool.setDomain(brasIppoolForm.getDomain());
        brasIppool.setIpPool(brasIppoolForm.getIpPool());
        brasIppool.setIpMask(brasIppoolForm.getIpMask());
        brasIppool.setStatus(brasIppoolForm.getStatus());
        brasIppool.setDateUpdate(brasIppoolForm.getDateUpdate());

        mySession.save(brasIppool);

        brasIppoolForm = new BrasIppoolForm();

        getListComboBox();
        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
        brasIppoolForm.setDateUpdate(getSysdate());

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.AddNew.Success");
        getListComboBox();

        log.info("End method addBrasIppool of BrasIppoolDAO");

        return pageForward;
    }

    private boolean validateBrasIppool(HttpServletRequest req, Session mySession) {
        boolean result = false;
        try {
            if (brasIppoolForm.getIpPool() == null || brasIppoolForm.getIpPool().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.IppoolEmpty");
                return result;
            }
            if (brasIppoolForm.getPoolname() == null || brasIppoolForm.getPoolname().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.PoolNameEmpty");
                return result;
            }
            if (brasIppoolForm.getDomain() == null || brasIppoolForm.getDomain().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.DomainEmpty");
                return result;
            }

            //KIEM TRA DOMAIN
            DomainDAO domainDAO = new DomainDAO();
            domainDAO.setSession(mySession);
            List<Domain> listDomain = domainDAO.findByDomain(brasIppoolForm.getDomain().trim());
            if (listDomain == null || listDomain.size() == 0 || listDomain.get(0).getStatus().intValue() != Constant.STATUS_USE.intValue()) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.DomainInvalid");
                return result;
            }


            if (brasIppoolForm.getBrasIp() == null || brasIppoolForm.getBrasIp().trim().equals("")) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasIPEmpty");
                return result;
            }
            if (brasIppoolForm.getStatus() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.105");
                return result;
            }

            if (brasIppoolForm.getDateUpdate() == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.UpdateDateEmpty");
                return result;
            }

            Date currentDate = getSysdate();
            if (brasIppoolForm.getDateUpdate().after(currentDate)) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.UpdateNotValid");
                return result;
            }

            BrasDAO brasDAO = new BrasDAO();
            brasDAO.setSession(this.getSession());
            List<Bras> listBras = brasDAO.findByProperty("ip", brasIppoolForm.getBrasIp().trim());
            if (listBras == null || listBras.size() == 0 || listBras.get(0).getStatus().compareTo(Constant.STATUS_USE) != 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasNotExist");
                return result;
            }

            brasIppoolForm.setIpPool(brasIppoolForm.getIpPool().trim());
            brasIppoolForm.setPoolname(brasIppoolForm.getPoolname().trim());
            brasIppoolForm.setDomain(brasIppoolForm.getDomain().trim());
            brasIppoolForm.setBrasIp(brasIppoolForm.getBrasIp().trim());

            if (brasIppoolForm.getIpMask() != null) {
                brasIppoolForm.setIpMask(brasIppoolForm.getIpMask().trim());
            }
            if (brasIppoolForm.getPooluniq() != null) {
                brasIppoolForm.setPooluniq(brasIppoolForm.getPooluniq().trim());
            }

            //CHECK IP da ton tai hay chua trong cung IP BRAS
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put(BrasIppoolDAO.IP_POOL, brasIppoolForm.getIpPool());
            //hashMap.put(BrasIppoolDAO.BRAS_IP, brasIppoolForm.getBrasIp());--Khong check duy nhat trong tung brasIp nua
            if (brasIppoolForm.getPoolId() != null) {
                hashMap.put("NOT_EQUAL." + BrasIppoolDAO.POOL_ID, brasIppoolForm.getPoolId());
            }
            List list = CommonDAO.findByProperty(mySession, BrasIppoolDAO.TABLE_NAME, hashMap);
            if (list != null && list.size() > 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.IPPoolNotExist");
                return result;
            }

            result = !result;
            return result;
        } catch (Exception ex) {
            return result;
        }
    }

    public String editBrasIppool() throws Exception {
        log.info("Begin method editBrasIppool of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

        if (brasIppoolForm.getPoolId() == null) {
            getListComboBox();
            req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasCodeEmpty");
            return pageForward;
        }
        if (!validateBrasIppool(req, mySession)) {
            getListComboBox();
            req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
            return pageForward;
        }

        BrasIppool brasIppool = findById(brasIppoolForm.getPoolId());
        if (brasIppool == null) {
            getListComboBox();
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasIPNotFound");
            return pageForward;
        }
        brasIppool.setBrasIp(brasIppoolForm.getBrasIp());
        brasIppool.setPoolname(brasIppoolForm.getPoolname());
        brasIppool.setPooluniq(brasIppoolForm.getPooluniq());
        brasIppool.setDomain(brasIppoolForm.getDomain());
        brasIppool.setIpPool(brasIppoolForm.getIpPool());
        brasIppool.setIpMask(brasIppoolForm.getIpMask());
        brasIppool.setStatus(brasIppoolForm.getStatus());
        brasIppool.setDateUpdate(brasIppoolForm.getDateUpdate());

        mySession.update(brasIppool);

        brasIppoolForm = new BrasIppoolForm();

        getListComboBox();
        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
        brasIppoolForm.setDateUpdate(getSysdate());

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.EditSuccess");

        log.info("End method editBrasIppool of BrasIppoolDAO");

        return pageForward;
    }

    public String prepareEditBras() throws Exception {
        log.info("Begin method prepareEditBras of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());


        //String poolId = req.getParameter("poolId");
        String poolId = (String) QueryCryptUtils.getParameter(req, "poolId");
        if (poolId == null || poolId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasCodeEmpty");
        }

        BrasIppool brasIppool = findById(Long.valueOf(poolId));
        if (brasIppool == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasIPNotFound");
            return pageForward;
        }

        brasIppoolForm = new BrasIppoolForm();

        brasIppoolForm.setPoolId(brasIppool.getPoolId());
        brasIppoolForm.setBrasIp(brasIppool.getBrasIp());
        brasIppoolForm.setPoolname(brasIppool.getPoolname());
        brasIppoolForm.setPooluniq(brasIppool.getPooluniq());
        brasIppoolForm.setDomain(brasIppool.getDomain());
        brasIppoolForm.setIpPool(brasIppool.getIpPool());
        brasIppoolForm.setIpMask(brasIppool.getIpMask());
        brasIppoolForm.setStatus(brasIppool.getStatus());
        brasIppoolForm.setDateUpdate(brasIppool.getDateUpdate());

        getListComboBox();

        log.info("End method prepareEditBras of BrasIppoolDAO");

        return pageForward;
    }

    public String deleteBras() throws Exception {
        log.info("Begin method deleteBras of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        Session mySession = this.getSession();

        //String poolId = req.getParameter("poolId");
        String poolId = (String) QueryCryptUtils.getParameter(req, "poolId");
        if (poolId == null || poolId.trim().equals("")) {
            getListComboBox();
            req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasCodeEmpty");
            return pageForward;
        }

        BrasIppool brasIppool = findById(Long.valueOf(poolId));
        if (brasIppool == null) {
            getListComboBox();
            req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasIPNotFound");
            return pageForward;
        }

        if (brasIppool.getStatus() != null) {
            if ((brasIppool.getStatus().intValue() == Constant.BRAS_IPPOOL_STATUS_USE.intValue()) || (brasIppool.getStatus().intValue() == Constant.BRAS_IPPOOL_STATUS_LOCK.intValue())) {
                getListComboBox();
                req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.CanNotDelete");
                return pageForward;
            }
        }

        mySession.delete(brasIppool);

        getListComboBox();
        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());

        req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.DeleteSuccess");

        log.info("End method deleteBras of BrasIppoolDAO");

        return pageForward;
    }

    public String cancelEditBrasIppool() throws Exception {
        log.info("Begin method cancelEditBrasIppool of BrasIppoolDAO ...");

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        brasIppoolForm = new BrasIppoolForm();

        getListComboBox();
        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
        brasIppoolForm.setDateUpdate(getSysdate());

        log.info("End method cancelEditBrasIppool of BrasIppoolDAO");

        return pageForward;
    }

    public List<ImSearchBean> getListBrasIp(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.ip, a.aaaIp) ");
        queryString.append("from Bras a where 1=1 and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.ip) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.aaaIp) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.ip ");
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

    public Long getListBrasIpSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Bras a where 1=1 and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.ip) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.aaaIp) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
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

    public List<ImSearchBean> getListBrasIpName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.ip, a.aaaIp) ");
        queryString.append("from Bras a where 1=1  and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.ip) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
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

    public List<ImSearchBean> getListDomain(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.domain, a.description) ");
        queryString.append("from Domain a where 1=1 and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.domain) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.description) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        queryString.append("order by a.domain ");
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

    public Long getListDomainSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Domain a where 1=1 and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.domain) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.description) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");
        }
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

    public List<ImSearchBean> getListDomainName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.domain, a.description) ");
        queryString.append("from Domain a where 1=1  and status = ? ");
        listParameter.add(Constant.STATUS_USE);
        queryString.append("and rownum <= ? ");
        listParameter.add(MAX_SEARCH_RESULT);
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.domain) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());
        }
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

    public String prepareCopyBras() throws Exception {

        pageForward = PAGE_FORWARD_BRAS_IP_POOL;

        HttpServletRequest req = getRequest();

        brasIppoolForm = new BrasIppoolForm();

        //String poolId = req.getParameter("poolId");
        String poolId = (String) QueryCryptUtils.getParameter(req, "poolId");
        if (poolId == null || poolId.trim().equals("")) {
            req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasCodeEmpty");
            return pageForward;
        }

        BrasIppool brasIppool = findById(Long.valueOf(poolId));
        if (brasIppool == null) {
            req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());
            req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.BrasIpool.BrasIPNotFound");
            return pageForward;
        }

        req.setAttribute(SESSION_LIST_BRAS_IP_POOL, this.getListBrasIppool());



        brasIppoolForm.setBrasIp(brasIppool.getBrasIp());
        brasIppoolForm.setPoolname(brasIppool.getPoolname());
        brasIppoolForm.setPooluniq(brasIppool.getPooluniq());
        brasIppoolForm.setDomain(brasIppool.getDomain());

        brasIppoolForm.setIpPool(brasIppool.getIpPool());
        brasIppoolForm.setIpMask(brasIppool.getIpMask());
        brasIppoolForm.setStatus(brasIppool.getStatus());
        brasIppoolForm.setDateUpdate(brasIppool.getDateUpdate());

        getListComboBox();

        return pageForward;
    }
}
