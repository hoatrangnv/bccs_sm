package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.CableBox;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import com.viettel.im.client.form.CableBoxForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Boards;
import com.viettel.im.database.BO.Dslam;
import com.viettel.security.util.StringEscapeUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * A data access object (DAO) providing persistence and search support for
 * CableBox entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the  
 * desired type of transaction control.
 * 
 * @see com.viettel.bccs.im.database.BO.CableBox
 * @author MyEclipse Persistence Tools
 */
public class CableBoxDAO extends BaseDAOAction {

    private String pageForward;
    private static final Log log = LogFactory.getLog(CableBoxDAO.class);
    public static final String CABLE_BOX_OF_DSLAM = "addCableBoxOfDslam";
    // property constants
    public static final String CABLE_BOX_ID = "cableboxId";
    public static final String BOARD_ID = "boardId";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String MAX_PORTS = "maxPorts";
    public static final String USED_PORTS = "usedPorts";
    public static final String STATUS = "status";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String DSLAM_ID = "dslamId";
    public static final String RESERVED_PORT = "reservedPort";
    public static final String TYPE = "type";
    public static final String CABLE_BOX = "addCableBox";
    public static final String SESSION_LIST_CABLE_BOX = "listCableBox";
    public static final String REQUEST_BOARDS_ID = "boardId";
    public static final String REQUEST_BOARDS_CODE = "boardCode";
    public static final String REQUEST_DSLAM_CODE = "dslamCode";
    public static final int SEARCH_RESULT_LIMIT = 1000;
    private CableBoxForm cableBoxForm = new CableBoxForm();
    public static final String message = "";

    public CableBoxForm getCableBoxForm() {
        return this.cableBoxForm;
    }

    public void setCableBoxForm(CableBoxForm cableBoxForm) {
        this.cableBoxForm = cableBoxForm;
    }

    public void save(CableBox transientInstance) {
        log.debug("saving CableBox instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CableBox persistentInstance) {
        log.debug("deleting CableBox instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CableBox findById(java.lang.Long id) {
        log.debug("getting CableBox instance with id: " + id);
        try {
            CableBox instance = (CableBox) getSession().get(
                    "com.viettel.im.database.BO.CableBox", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CableBox instance) {
        log.debug("finding CableBox instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.bccs.im.database.BO.CableBox").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CableBox instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CableBox as model where model." + StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByBoardId(Object boardId) {
        return findByProperty(BOARD_ID, boardId);
    }

    public List findByCode(Object code) {
        return findByProperty(CODE, code);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByMaxPorts(Object maxPorts) {
        return findByProperty(MAX_PORTS, maxPorts);
    }

    public List findByUsedPorts(Object usedPorts) {
        return findByProperty(USED_PORTS, usedPorts);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByX(Object x) {
        return findByProperty(X, x);
    }

    public List findByY(Object y) {
        return findByProperty(Y, y);
    }

    public List findByDslamId(Object dslamId) {
        return findByProperty(DSLAM_ID, dslamId);
    }

    public List findByReservedPort(Object reservedPort) {
        return findByProperty(RESERVED_PORT, reservedPort);
    }

    public List findByType(Object type) {
        return findByProperty(TYPE, type);
    }


    public List findAll() {
        log.debug("finding all CableBox instances");
        try {
            String queryString = "select new CableBox(a.cableBoxId,a.boardId,a.code,a.name,a.address,a.maxPorts,a.usedPorts," + "a.status,a.x,a.y,a.dslamId,a.reservedPort,a.type,b.code) from CableBox a,Boards b where a.boardId = b.boardId" + " order by nlssort(a.code,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, Constant.STATUS_DELETE.toString());
//            queryObject.setMaxResults(1000);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CableBox merge(CableBox detachedInstance) {
        log.debug("merging CableBox instance");
        try {
            CableBox result = (CableBox) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CableBox instance) {
        log.debug("attaching dirty CableBox instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CableBox instance) {
        log.debug("attaching clean CableBox instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = CABLE_BOX;
        cableBoxForm = new CableBoxForm();
        try {
            Long boardsId = cableBoxForm.getBoardId();
            cableBoxForm.resetForm();

            String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, boardsId.toString());
            queryObject.setParameter(1, String.valueOf(SEARCH_RESULT_LIMIT));
            List<CableBox> listcablebox = queryObject.list();

            req.getSession().removeAttribute("listcablebox");
            req.getSession().setAttribute("listcablebox", listcablebox);
            req.getSession().setAttribute("toEditCableBox", 0);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = CABLE_BOX;
        }
        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");

        searchCableBox();
        pageForward = "pageNavigator";

        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }

    public String pageNavigatorOfDslam() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");

        searchCableBox();
        pageForward = "pageNavigatorOfDslam";

        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }

    public String addNewCableBox() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = CABLE_BOX;
        CableBoxForm f = this.getCableBoxForm();
        Long boardsId = f.getBoardId();
        try {
            if (checkValidateToAdd()) {
                CableBox bo = new CableBox();
                bo.setCableBoxId(getSequence("CABLE_BOX_SEQ"));
                f.copyDataToBO(bo);

                Long dslamid = findbyBOARDId(boardsId).getDslamId();
                bo.setDslamId(dslamid);
                bo.setBoardId(boardsId);
                save(bo);

                f.resetForm();
                req.setAttribute("message", "Cablebox.add.success");
            }

            String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, boardsId.toString());
            queryObject.setParameter(1, String.valueOf(SEARCH_RESULT_LIMIT));
            List<CableBox> listcablebox = queryObject.list();

            req.getSession().removeAttribute("listcablebox");
            req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);

            if(boardsId != null){//haidd fix
                String boardsCode = findbyBOARDId(boardsId).getCode();
                req.setAttribute(REQUEST_BOARDS_ID, boardsId);
                req.setAttribute(REQUEST_BOARDS_CODE, boardsCode);
            }                                    
        } catch (Exception e) {
            e.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            if (boardsId == null) boardsId = -1L;
            
            String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, boardsId.toString());
            queryObject.setParameter(1, String.valueOf(SEARCH_RESULT_LIMIT));
            List<CableBox> listcablebox = queryObject.list();

            req.getSession().removeAttribute("listcablebox");
            req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);

            String boardsCode = findbyBOARDId(boardsId).getCode();
            req.setAttribute(REQUEST_BOARDS_ID, boardsId);
            req.setAttribute(REQUEST_BOARDS_CODE, boardsCode);
            req.setAttribute("message", e.getMessage());

            pageForward = CABLE_BOX;
        }
        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }

    public String prepareEditCableBox() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                CableBoxForm f = this.cableBoxForm;
                String strcableBoxId = (String) QueryCryptUtils.getParameter(req, "cableBoxId");
                Long cableBoxId;
                try {
                    cableBoxId = Long.parseLong(strcableBoxId);
                    session.setAttribute("cableBoxId", cableBoxId);
                } catch (Exception ex) {
                    cableBoxId = -1L;
                }
                CableBox bo = this.findById(cableBoxId);
                f.copyDataFromBO(bo);
                req.getSession().setAttribute("toEditCableBox", 1);
                pageForward = CABLE_BOX;
                String boardsCode = findbyBOARDId(bo.getBoardId()).getCode();
                req.setAttribute(REQUEST_BOARDS_ID, bo.getBoardId());
                req.setAttribute(REQUEST_BOARDS_CODE, boardsCode);
            } catch (Exception e) {
                e.printStackTrace();
                pageForward = CABLE_BOX;
            }
            log.info("End method preparePage of CableBoxDAO");
        }
        return pageForward;
    }

    public String editCableBox() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        CableBoxForm f = this.cableBoxForm;
        Long boardsId = f.getBoardId();
        if (userToken != null) {
            try {
                if (checkValidateToEdit()) {
                    String cableBoxId = session.getAttribute("cableBoxId").toString();
                    CableBox bo = this.findById(Long.parseLong(cableBoxId));
                    f.copyDataToBO(bo);
                    Long dslamId = findbyBOARDId(boardsId).getDslamId();
                    bo.setDslamId(dslamId);
                    getSession().update(bo);
                    boardsId = f.getBoardId();
                    req.setAttribute("message", "Cablebox.edit.success");
                    req.getSession().setAttribute("toEditCableBox", 0);
                    f.resetForm();
                }

                String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, boardsId.toString());
                queryObject.setParameter(1, String.valueOf(SEARCH_RESULT_LIMIT));
                List<CableBox> listcablebox = queryObject.list();

                String boardsCode = findbyBOARDId(boardsId).getCode();
                req.setAttribute(REQUEST_BOARDS_ID, boardsId);
                req.setAttribute(REQUEST_BOARDS_CODE, boardsCode);

                req.getSession().removeAttribute("listcablebox");
                req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
                pageForward = CABLE_BOX;
            } catch (Exception e) {
                e.printStackTrace();
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                req.setAttribute("message", e.getMessage());
                if (boardsId == null) boardsId = -1L;

                String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, boardsId.toString());
                queryObject.setParameter(1, String.valueOf(SEARCH_RESULT_LIMIT));
                List<CableBox> listcablebox = queryObject.list();

                String boardsCode = findbyBOARDId(boardsId).getCode();
                req.setAttribute(REQUEST_BOARDS_ID, boardsId);
                req.setAttribute(REQUEST_BOARDS_CODE, boardsCode);

                req.getSession().removeAttribute("listcablebox");
                req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);

                pageForward = CABLE_BOX;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }

    public String deleteCableBox() throws Exception {
//        log.info("Begin method preparePage of CableBoxDAO ...");
//        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = CABLE_BOX;//Constant.ERROR_PAGE;
//        CableBoxForm f = this.getCableBoxForm();
//        if (userToken != null) {
//            try {
//                String ajax = QueryCryptUtils.getParameter(req, "ajax");
//                if ("1".equals(ajax)) {
//                    f.resetForm();
//                    req.getSession().setAttribute("toEditCableBox", 0);
//
//                } else {
//                    String strcableBoxId = (String) QueryCryptUtils.getParameter(req, "cableBoxId");
//                    Long cableBoxId;
//                    try {
//                        cableBoxId = Long.parseLong(strcableBoxId);
//                    } catch (Exception ex) {
//                        cableBoxId = -1L;
//                    }
//                    CableBox bo = findById(cableBoxId);
//                    bo.setStatus(Constant.STATUS_DELETE.toString());
//                    req.setAttribute("message", "Cablebox.delete.success");
//                    req.getSession().setAttribute("toEditCableBox", 0);
//                    List listcablebox = new ArrayList();
//                    listcablebox = findAll();
//                    req.getSession().removeAttribute("listcablebox");
//                    req.getSession().setAttribute("listcablebox", listcablebox);
//                }
//                pageForward = CABLE_BOX;
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End method preparePage of CommItemGroupsDAO");
//        return pageForward;

//Modified by TheTM <--Start-->

        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = CABLE_BOX;//Constant.ERROR_PAGE;
        CableBoxForm f = this.getCableBoxForm();
        pageForward = CABLE_BOX;
        Long boardsId = null;
        if (userToken != null) {
            try {
                String strcableBoxId = (String) QueryCryptUtils.getParameter(req, "cableBoxId");
                Long cableBoxId;
                try {
                    cableBoxId = Long.parseLong(strcableBoxId);
                } catch (Exception ex) {
                    cableBoxId = -1L;
                }
                CableBox bo = findById(cableBoxId);
                if (bo == null) {
                    req.setAttribute("message", "Cablebox.delete.error");
                    log.info("End method preparePage of CommItemGroupsDAO");
                    return pageForward;
                }
                getSession().delete(bo);
                getSession().flush();
                boardsId = bo.getBoardId();
                req.setAttribute("message", "Cablebox.delete.success");
                req.getSession().setAttribute("toEditCableBox", 0);

                String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, boardsId.toString());
                queryObject.setParameter(1, String.valueOf(SEARCH_RESULT_LIMIT));
                List<CableBox> listcablebox = queryObject.list();

                String boardsCode = findbyBOARDId(boardsId).getCode();
                req.setAttribute(REQUEST_BOARDS_ID, boardsId);
                req.setAttribute(REQUEST_BOARDS_CODE, boardsCode);

                req.getSession().removeAttribute("listcablebox");
                req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
            } catch (Exception e) {
                e.printStackTrace();
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                log.info("End method preparePage of CommItemGroupsDAO");
                return pageForward;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of CommItemGroupsDAO");
        return pageForward;
    }
//Modified By TheTM <--End-->

    public String searchCableBox() throws Exception {
        log.info("Begin method searchCableBox of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        pageForward = CABLE_BOX;

        CableBoxForm f = this.getCableBoxForm();
        Long boardsId = f.getBoardId();

        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.getSession().setAttribute("toEditCableBox", 0);
        } else {
            if (f.getBoardId() == null || f.getBoardId().intValue() <= 0) {
                return pageForward;
            }

            List listParameter = new ArrayList();
//            String strQuery = "select new CableBox(a.cableBoxId,a.boardId,a.code,a.name,a.address,a.maxPorts,a.usedPorts,a.status,a.x,a.y," + "a.dslamId,a.reservedPort,a.type,b.code) from CableBox a,Boards b where a.boardId = b.boardId and not a.status =" + Constant.STATUS_DELETE;
            String strQuery = "select new CableBox(a.cableBoxId,a.boardId,a.code,a.name,a.address,a.maxPorts,a.usedPorts,a.status,a.x,a.y," + "a.dslamId,a.reservedPort,a.type,b.code) from CableBox a,Boards b where a.boardId = b.boardId";

            if (f.getBoardId() != null) {
                strQuery += " and a.boardId = ? ";
                listParameter.add(f.getBoardId());
            }

            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                listParameter.add("%" + f.getCode().trim().toUpperCase() + "%");
                strQuery += " and upper(a.code) like ? ";
            }
            if (f.getName() != null && !f.getName().trim().equals("")) {
                listParameter.add("%" + f.getName().trim() + "%");
                strQuery += " and lower(a.name) like lower (?) ";
            }
            if (f.getAddress() != null && !f.getAddress().trim().equals("")) {
                listParameter.add("%" + f.getAddress().trim() + "%");
                strQuery += " and lower(a.address) like lower (?) ";
            }
            if (f.getStatus() != null) {
                listParameter.add(f.getStatus());
                strQuery += " and a.status = ? ";
            }
            if (f.getType() != null && !f.getType().trim().equals("")) {
                listParameter.add(f.getType());
                strQuery += " and a.type = ? ";
            }
            if (f.getBoardscode() != null && !f.getBoardscode().trim().equals("")) {
                List temp = findbyBoardscode(f.getBoardscode());
                Boards tempobj = (Boards) temp.get(0);
                Long tempLong = tempobj.getBoardId();
                f.setBoardId(tempLong);
                listParameter.add(f.getBoardId());
                strQuery += " and a.boardId = ? ";
            }
            if (f.getX() != null && !f.getX().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getX().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.XNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getX().trim()));
                        strQuery += " and a.x = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.XNumberFormat");
                    return pageForward;
                }
            }
            if (f.getY() != null && !f.getY().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getY().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.YNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getY().trim()));
                        strQuery += " and a.y = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.YNumberFormat");
                    return pageForward;
                }
            }
            if (f.getMaxPorts() != null && !f.getMaxPorts().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getMaxPorts().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.maxPortNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getMaxPorts().trim()));
                        strQuery += " and a.maxPorts = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.maxPortNumberFormat");
                    return pageForward;
                }
            }
            if (f.getUsedPorts() != null && !f.getUsedPorts().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getUsedPorts().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.usedPortNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getUsedPorts().trim()));
                        strQuery += " and a.usedPorts = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.usedPortNumberFormat");
                    return pageForward;
                }
            }
            if (f.getReservedPort() != null && !f.getReservedPort().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getReservedPort().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.reservedPortNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getReservedPort().trim()));
                        strQuery += " and a.reservedPort = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.reservedPortNumberFormat");
                    return pageForward;
                }
            }
            strQuery += " order by nlssort( a.code ,'nls_sort=Vietnamese') asc ";

            Query q = getSession().createQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                q.setParameter(i, listParameter.get(i));
            }
            List listcablebox = new ArrayList();
            listcablebox = q.list();
            req.getSession().setAttribute("toEditCableBox", 0);
            if (listcablebox != null) {
                req.setAttribute("message", "Cablebox.search");
                List paramMsg = new ArrayList();
                paramMsg.add(listcablebox.size());
                req.setAttribute("paramMsg", paramMsg);
            } else {
                req.setAttribute("message", "Cablebox.searchNotsucess");
            }
            req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
        }
        String boardsCode = findbyBOARDId(boardsId).getCode();
        req.setAttribute(REQUEST_BOARDS_ID, boardsId);
        req.setAttribute(REQUEST_BOARDS_CODE, boardsCode);

        pageForward = CABLE_BOX;
        log.info("End method CableBox of CableBoxDAO ...");
        return pageForward;
    }
    private Map ListBoardIdCombo = new HashMap();

    public String getListBoardId() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String BoardId = httpServletRequest.getParameter("cableBoxForm.boardId");
            List<CableBox> ListBoardId = new ArrayList();
            if (BoardId != null) {
                if ("".equals(BoardId)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from CableBox";
                queryString += " where status = ? ";
                parameterList.add(String.valueOf(Constant.STATUS_USE));
                queryString += " and lower(to_char(boardId)) like ? ";
                parameterList.add("%" + BoardId.toLowerCase() + "%");
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    ListBoardId = queryObject.list();
                    for (int i = 0; i < ListBoardId.size(); i++) {
                        ListBoardIdCombo.put(ListBoardId.get(i).getCableBoxId(), ListBoardId.get(i).getBoardId());
                    }
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }
    private Map ListDslamIdCombo = new HashMap();
    private Map ListdslamcodeCombo = new HashMap();
    private Map ListboardscodeCombo = new HashMap();

    public String getListDslamId() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String dslamId = httpServletRequest.getParameter("cableBoxForm.dslamId");
            List<CableBox> ListDslamId = new ArrayList();
            if (dslamId != null) {
                if ("".equals(dslamId)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from CableBox";
                queryString += " where status = ? ";
                parameterList.add(String.valueOf(Constant.STATUS_USE));
                queryString += " and lower(to_char(dslamId)) like ? ";
                parameterList.add("%" + dslamId.toLowerCase() + "%");
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    ListDslamId = queryObject.list();
                    for (int i = 0; i < ListDslamId.size(); i++) {
                        ListDslamIdCombo.put(ListDslamId.get(i).getCableBoxId(), ListDslamId.get(i).getDslamId());
                    }
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public Map getListBoardIdCombo() {
        return ListBoardIdCombo;
    }

    public void setListBoardIdCombo(Map ListBoardIdCombo) {
        this.ListBoardIdCombo = ListBoardIdCombo;
    }

    public Map getListDslamIdCombo() {
        return ListDslamIdCombo;
    }

    public void setListDslamIdCombo(Map ListDslamIdCombo) {
        this.ListDslamIdCombo = ListDslamIdCombo;
    }

    public List findbyDSLAMId(Long dslamId) {
        log.debug("finding DSLAM with DSLAM id");
        try {
            String queryString = "from Dslam as model where model.dslamId = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, dslamId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by DSLAM Id failed", re);
            throw re;
        }
    }

    public Dslam findbyDslamId1(Long dslamId) {
        log.debug("finding DSLAM with DSLAM id");
        try {
            Dslam instance = (Dslam) getSession().get(
                    "com.viettel.im.database.BO.Dslam", dslamId);
            return instance;
        } catch (RuntimeException re) {
            log.error("find by BOARD Id failed", re);
            throw re;
        }
    }

    public Boards findbyBOARDId(Long boardId) {
        log.debug("finding BOARD with BOARD id");
        try {
            Boards instance = (Boards) getSession().get(
                    "com.viettel.im.database.BO.Boards", boardId);
            return instance;
        } catch (RuntimeException re) {
            log.error("find by BOARD Id failed", re);
            throw re;
        }
    }

    public List findbyDSLAMcode(String dslamcode) {
        log.debug("finding DSLAM with DSLAM code");
        try {
            String queryString = "from Dslam as model where model.code = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, dslamcode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by DSLAM code failed", re);
            throw re;
        }
    }

    public List findbyBOARDcode(String boardscode) {
        log.debug("finding BOARD with BOARD code");
        try {
            String queryString = "from Boards as model where model.code = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, boardscode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by BOARD code failed", re);
            throw re;
        }
    }

    public String getListDslamcode() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String dslamcode = httpServletRequest.getParameter("cableBoxForm.dslamcode");
            if (dslamcode != null) {
                if ("".equals(dslamcode)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Dslam";
                queryString += " where status = ? ";
                parameterList.add(Constant.STATUS_USE);
                queryString += " and lower(code) like ? ";
                parameterList.add("%" + dslamcode.trim().toLowerCase() + "%");
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                queryObject.setMaxResults(8); //lay 8 ket qua thoa man
                List<Dslam> listDslam = queryObject.list();
                if (listDslam != null && listDslam.size() > 0) {
                    for (int i = 0; i < listDslam.size(); i++) {
                        ListdslamcodeCombo.put(listDslam.get(i).getCode(), listDslam.get(i).getName());
                    }
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public String getListboardscode() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String boardscode = httpServletRequest.getParameter("cableBoxForm.boardscode");
            if (boardscode != null) {
                if ("".equals(boardscode)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Boards";
                queryString += " where status = ? ";
                parameterList.add(String.valueOf(Constant.STATUS_USE));
                queryString += " and lower(code) like ? ";
                parameterList.add("%" + boardscode.trim().toLowerCase() + "%");
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
//                queryObject.setMaxResults(1); //lay 8 ket qua thoa man
                List<Boards> listBoards = queryObject.list();
                if (listBoards != null && listBoards.size() > 0) {
                    for (int i = 0; i < listBoards.size(); i++) {
                        ListboardscodeCombo.put(listBoards.get(i).getName(), listBoards.get(i).getCode());
                    }
                    return "success";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public Map getListdslamcodeCombo() {
        return ListdslamcodeCombo;
    }

    public void setListdslamcodeCombo(Map ListDslamcodeCombo) {
        this.ListdslamcodeCombo = ListDslamcodeCombo;
    }

    public Map getListboardscodeCombo() {
        return ListboardscodeCombo;
    }

    public void setListboardscodeCombo(Map ListboardscodeCombo) {
        this.ListboardscodeCombo = ListboardscodeCombo;
    }

    public List findbyBoardscode(String boardscode) {
        log.debug("finding BOARDS with BOARDS code");
        try {
            String queryString = "from Boards as model where model.code = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, boardscode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by BOARDS code failed", re);
            throw re;
        }
    }

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        CableBoxForm f = this.cableBoxForm;
        String code = f.getCode();
        String name = f.getName();
        String address = f.getAddress();
        Long status = f.getStatus();
        String maxPort = f.getMaxPorts();
        String usedPort = f.getUsedPorts();
        String reservedPort = f.getReservedPort();
        String x = f.getX();
        String y = f.getY();

        if ((code == null) || code.trim().equals("")) {
            req.setAttribute("message", "Cablebox.error.invalidCode");
            return false;
        } else {
            code = code.trim().toUpperCase();
        }
        if ((name == null) || name.trim().equals("")) {
            req.setAttribute("message", "Cablebox.error.invalidName");
            return false;
        } else {
            name = name.trim().toUpperCase();
        }
        if ((maxPort == null) || maxPort.trim().equals("")) {
            req.setAttribute("message", "Cablebox.error.invalidmaxPort");
            return false;
        } else {
            maxPort = maxPort.trim();
        }
        if ((maxPort != null) && !maxPort.trim().equals("")) {
            try {
                if (Integer.parseInt(maxPort.trim()) < 0) {
                    req.setAttribute("message", "Cablebox.error.maxPortNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Cablebox.error.maxPortNumberFormat");
                return false;
            }
        }

        if ((usedPort != null) && !usedPort.trim().equals("")) {
            try {
                if (Integer.parseInt(usedPort.trim()) < 0) {
                    req.setAttribute("message", "Cablebox.error.usedPortNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Cablebox.error.usedPortNumberFormat");
                return false;
            }
        }

        if (status == null) {
            req.setAttribute("message", "Cablebox.error.invalidstatus");
            return false;
        }
        if ((reservedPort == null) || reservedPort.trim().equals("")) {
            req.setAttribute("message", "Error. Pls input reserved port!");
            return false;
        } else {
            reservedPort = reservedPort.trim();
        }
        if ((reservedPort != null) && !reservedPort.trim().equals("")) {
            try {

                if (Integer.parseInt(reservedPort.trim()) < 0) {
                    req.setAttribute("message", "Cablebox.error.reservedPortNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Cablebox.error.reservedPortNumberFormat");
                return false;
            }
        }

        if ((x != null) && !x.trim().equals("")) {
            try {
                if (Long.parseLong(x.trim()) < 0) {
                    req.setAttribute("message", "Cablebox.error.XNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Cablebox.error.XNumberFormat");
                return false;
            }
        }
        if ((y != null) && !y.trim().equals("")) {
            try {
                if (Long.parseLong(y.trim()) < 0) {
                    req.setAttribute("message", "Cablebox.error.YNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "Cablebox.error.YNumberFormat");
                return false;
            }
        }
        if ((maxPort != null) && !maxPort.trim().equals("") && (reservedPort != null) && !reservedPort.trim().equals("")) {
            Long maxPorts = Long.parseLong(maxPort.trim());
            Long reservedPorts = Long.parseLong(reservedPort.trim());
            if (maxPorts < reservedPorts) {
                req.setAttribute("message", "Error. reserved Port must be less than max Port!");
                return false;
            }
        }
        if ((maxPort != null) && !maxPort.trim().equals("") && (usedPort != null) && !usedPort.trim().equals("")) {
            Long maxPorts = Long.parseLong(maxPort.trim());
            Long usedPorts = Long.parseLong(usedPort.trim());
            if (maxPorts < usedPorts) {
                req.setAttribute("message", "Cablebox.error.InvalidPort1");
                return false;
            }
        }
        if ((maxPort != null) && !maxPort.trim().equals("") && (usedPort != null) && !usedPort.trim().equals("") && (reservedPort != null) && !reservedPort.trim().equals("")) {
            Long maxPorts = Long.parseLong(maxPort.trim());
            Long usedPorts = Long.parseLong(usedPort.trim());
            Long reservedPorts = Long.parseLong(reservedPort.trim());
            if (maxPorts < usedPorts + reservedPorts) {
                req.setAttribute("message", "Cablebox.error.InvalidPort");
                return false;
            }
        }

        return true;
    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();

        if (checkForm()) {
            String name = this.cableBoxForm.getName().trim();
            String code = this.cableBoxForm.getCode().trim();

            //kiem tra trung lap ma hoac ten
            Long count;
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select count(*) ");
            strQuery.append("from   CableBox as model ");
            strQuery.append("where  upper(model.name) = ? or upper(model.code) = ? ");
            Query q = getSession().createQuery(strQuery.toString());
            q.setParameter(0, name.toUpperCase());
            q.setParameter(1, code.toUpperCase());
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "Cablebox.add.duplicateName");
                return false;
            }

            //kiem tra cap tong co hieu luc khong
            if (Constant.STATUS_ACTIVE.equals(this.cableBoxForm.getStatus())) {
                strQuery = new StringBuilder("");
                strQuery.append("select count(*) ");
                strQuery.append("from   Boards ");
                strQuery.append("where  status = ? ");
                
                if(this.cableBoxForm.getBoardId() != null){//haidd
                    strQuery.append(" and boardId = ?  ");
                }

                q = getSession().createQuery(strQuery.toString());
                q.setParameter(0, Constant.STATUS_DELETE);
                
                if (this.cableBoxForm.getBoardId() != null) {//haidd
                    q.setParameter(1, this.cableBoxForm.getBoardId());
                }
                
                count = (Long) q.list().get(0);

                if ((count != null) && (count.compareTo(0L) > 0)) {
                    req.setAttribute("message", "Cablebox.edit.error.boardNotEnable");
                    return false;
                }
            }

        } else {
            return false;
        }

        return true;
    }

    private boolean checkValidateToAddDslam() {
        HttpServletRequest req = getRequest();

        if (checkForm()) {
            String name = this.cableBoxForm.getName().trim();
            String code = this.cableBoxForm.getCode().trim();

            //kiem tra trung lap ma hoac ten
            Long count;
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select count(*) ");
            strQuery.append("from   CableBox as model ");
            strQuery.append("where  upper(model.name) = ? or upper(model.code) = ? ");
            Query q = getSession().createQuery(strQuery.toString());
            q.setParameter(0, name.toUpperCase());
            q.setParameter(1, code.toUpperCase());
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "Cablebox.add.duplicateName");
                return false;
            }

            //kiem tra cap tong co hieu luc khong
            if (Constant.STATUS_ACTIVE.equals(this.cableBoxForm.getStatus())) {
                strQuery = new StringBuilder("");
                strQuery.append("select count(*) ");
                strQuery.append("from   Dslam ");
                strQuery.append("where  status = ? and code = ? ");

                q = getSession().createQuery(strQuery.toString());
                q.setParameter(0, Constant.STATUS_DELETE);
                q.setParameter(1, this.cableBoxForm.getDslamcode());
                count = (Long) q.list().get(0);

                if ((count != null) && (count.compareTo(0L) > 0)) {
                    req.setAttribute("message", "Cablebox.edit.error.dslamNotEnable");
                    return false;
                }
            }

        } else {
            return false;
        }

        return true;
    }

    private boolean checkValidateToEdit() {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        Long count;
        CableBoxForm f = this.getCableBoxForm();
        String name = f.getName().trim();
        String code = f.getCode().trim();
        Long id = (Long) session.getAttribute("cableBoxId");
        if (checkForm()) {
//            String strQuery = "select count(*) from CableBox as model where (upper(model.name)=? or upper(model.code)= ? )and not model.cableBoxId = ? and not model.status= ?";
            String strQuery = "select count(*) from CableBox as model where (upper(model.name)=? or upper(model.code)= ? ) and model.cableBoxId != ? ";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, name.toUpperCase());
            q.setParameter(1, code.toUpperCase());
            q.setParameter(2, id);
//            q.setParameter(3, String.valueOf(Constant.STATUS_DELETE));
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "Cablebox.edit.duplicateName");
                return false;
            }
        } else {
            return false;
        }
        if (f.getStatus() == Constant.STATUS_USE) {
            String strQuery = "select count(*) from Boards where status = ? and boardId = ? ";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, Constant.STATUS_DELETE);
            q.setParameter(1, f.getBoardId());
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "Cablebox.edit.error.boardNotEnable");
                return false;
            }
        }
        return true;
    }

//    public String searchCableBoxOfDslam() throws Exception {
//        searchCableBox();
//        pageForward = CABLE_BOX_OF_DSLAM;
//        return pageForward;
//    }

    /* Ham tim kiem Cap nhanh thuoc Dslam
     * Author NamLT
     * Date 20/09/2010
     */
    public String searchCableBoxOfDslam() throws Exception {
        log.info("Begin method searchCableBox of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        pageForward = CABLE_BOX_OF_DSLAM;

        CableBoxForm f = this.getCableBoxForm();
        Long dslamId = f.getDslamId();

        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.getSession().setAttribute("toEditCableBox", 0);
        } else {
            if (f.getDslamId() == null || f.getDslamId().intValue() <= 0) {
                return pageForward;
            }

            List listParameter = new ArrayList();
//            String strQuery = "select new CableBox(a.cableBoxId,a.boardId,a.code,a.name,a.address,a.maxPorts,a.usedPorts,a.status,a.x,a.y," + "a.dslamId,a.reservedPort,a.type,b.code) from CableBox a,Boards b where a.boardId = b.boardId and not a.status =" + Constant.STATUS_DELETE;
            String strQuery = "select new CableBox(a.cableBoxId,a.code,a.name,a.address,a.maxPorts,a.usedPorts,a.status,a.x,a.y," + "a.dslamId,a.reservedPort,a.type,b.code) from CableBox a,Dslam b where a.dslamId = b.dslamId";

            if (f.getDslamId() != null) {
                strQuery += " and a.dslamId = ? ";
                listParameter.add(f.getDslamId());
            }

            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                listParameter.add("%" + f.getCode().trim().toUpperCase() + "%");
                strQuery += " and upper(a.code) like ? ";
            }
            if (f.getName() != null && !f.getName().trim().equals("")) {
                listParameter.add("%" + f.getName().trim() + "%");
                strQuery += " and lower(a.name) like lower (?) ";
            }
            if (f.getAddress() != null && !f.getAddress().trim().equals("")) {
                listParameter.add("%" + f.getAddress().trim() + "%");
                strQuery += " and lower(a.address) like lower (?) ";
            }
            if (f.getStatus() != null) {
                listParameter.add(f.getStatus());
                strQuery += " and a.status = ? ";
            }
//            if (f.getType() != null && !f.getType().trim().equals("")) {

            //  }
            if (f.getDslamcode() != null && !f.getDslamcode().trim().equals("")) {
//                List temp = findbyBoardscode(f.getBoardscode());
//                Boards tempobj = (Boards) temp.get(0);
//                Long tempLong = tempobj.getBoardId();
//                f.setBoardId(tempLong);

//                DslamDAO dslamDAO=new DslamDAO();
//                dslamDAO.setSession(this.getSession());
                List<Dslam> lstDslam = findbyDSLAMcode(f.getDslamcode());
                f.setDslamId(lstDslam.get(0).getDslamId());
                listParameter.add(lstDslam.get(0).getDslamId());
                strQuery += " and a.dslamId = ? ";

                req.setAttribute("dslamCode", f.getDslamcode());

            }
            if (f.getX() != null && !f.getX().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getX().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.XNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getX().trim()));
                        strQuery += " and a.x = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.XNumberFormat");
                    return pageForward;
                }
            }
            if (f.getY() != null && !f.getY().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getY().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.YNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getY().trim()));
                        strQuery += " and a.y = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.YNumberFormat");
                    return pageForward;
                }
            }
            if (f.getMaxPorts() != null && !f.getMaxPorts().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getMaxPorts().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.maxPortNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getMaxPorts().trim()));
                        strQuery += " and a.maxPorts = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.maxPortNumberFormat");
                    return pageForward;
                }
            }
            if (f.getUsedPorts() != null && !f.getUsedPorts().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getUsedPorts().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.usedPortNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getUsedPorts().trim()));
                        strQuery += " and a.usedPorts = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.usedPortNumberFormat");
                    return pageForward;
                }
            }
            if (f.getReservedPort() != null && !f.getReservedPort().trim().equals("")) {
                try {
                    if (Long.parseLong(f.getReservedPort().trim()) < 0) {
                        req.setAttribute("message", "Cablebox.error.reservedPortNegative");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(f.getReservedPort().trim()));
                        strQuery += " and a.reservedPort = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Cablebox.error.reservedPortNumberFormat");
                    return pageForward;
                }
            }


            strQuery += " and a.type = ? ";
            listParameter.add(Constant.DSLAM_CABLE_TYPE);
            strQuery += " order by nlssort( a.code ,'nls_sort=Vietnamese') asc ";

            Query q = getSession().createQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                q.setParameter(i, listParameter.get(i));
            }
            List listcablebox = new ArrayList();
            listcablebox = q.list();
            req.getSession().setAttribute("toEditCableBox", 0);
            if (listcablebox != null) {
                req.setAttribute("message", "Cablebox.search");
                List paramMsg = new ArrayList();
                paramMsg.add(listcablebox.size());
                req.setAttribute("paramMsg", paramMsg);
            } else {
                req.setAttribute("message", "Cablebox.searchNotsucess");
            }
            req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
        }

        // List<Dslam> lstDslam=  (List<Dslam>) findByDslamId(dslamId);
        DslamDAO dslamDAO = new DslamDAO();
        dslamDAO.setSession(this.getSession());
        Dslam dslam = dslamDAO.findById(dslamId);
        String dslamCode = dslam.getDslamcode();
        req.setAttribute(DSLAM_ID, dslamId);
        req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);

        pageForward = CABLE_BOX_OF_DSLAM;
        log.info("End method CableBox of CableBoxDAO ...");
        return pageForward;
    }


    /* Load thong tin can sua cua cap nhanh thuoc Dslam
     * Author NamLT
     * Date 20/9/2010
     */
    public String prepareEditCableBoxOfDslam() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;

        if (userToken != null) {
            try {
                CableBoxForm f = this.cableBoxForm;
                String strcableBoxId = (String) QueryCryptUtils.getParameter(req, "cableBoxId");
                Long cableBoxId;
                try {
                    cableBoxId = Long.parseLong(strcableBoxId);
                    session.setAttribute("cableBoxId", cableBoxId);
                } catch (Exception ex) {
                    cableBoxId = -1L;
                }
                Long dslamId = f.getDslamId();
                CableBox bo = this.findById(cableBoxId);
                f.copyDataFromBO(bo);
                req.getSession().setAttribute("toEditCableBox", 1);

                String queryString = "from CableBox where 1=1 and dslamId = ? and type = ? and rownum <= ? order by dslamId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, dslamId);
                queryObject.setParameter(1, Constant.DSLAM_CABLE_TYPE);
                queryObject.setParameter(2, String.valueOf(SEARCH_RESULT_LIMIT));
                List<CableBox> listcablebox = queryObject.list();
                
                pageForward = CABLE_BOX_OF_DSLAM;
                String dslamCode = this.findbyDslamId1(bo.getDslamId()).getCode();
                req.setAttribute(DSLAM_ID, bo.getDslamId());
                req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
                req.getSession().removeAttribute("listcablebox");
                req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
            } catch (Exception e) {
                e.printStackTrace();
                pageForward = CABLE_BOX_OF_DSLAM;
            }
            log.info("End method preparePage of CableBoxDAO");
        }
        return pageForward;
    }


    /* Ham xoa thong tin cap nhanh thuoc Dslam
     * Author NamLT
     * Date 20/9/2010
     */
    public String deleteCableBoxOfDslam() throws Exception {


        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = CABLE_BOX_OF_DSLAM;//Constant.ERROR_PAGE;
        CableBoxForm f = this.getCableBoxForm();
        //   pageForward = CABLE_BOX;
        Long dslamId = null;
        if (userToken != null) {
            try {
                String strcableBoxId = (String) QueryCryptUtils.getParameter(req, "cableBoxId");
                Long cableBoxId;
                try {
                    cableBoxId = Long.parseLong(strcableBoxId);
                } catch (Exception ex) {
                    cableBoxId = -1L;
                }
                CableBox bo = findById(cableBoxId);
                if (bo == null) {
                    req.setAttribute("message", "Cablebox.delete.error");
                    log.info("End method preparePage of CommItemGroupsDAO");
                    return pageForward;
                }
                getSession().delete(bo);
                getSession().flush();
                dslamId = bo.getDslamId();
                req.setAttribute("message", "Cablebox.delete.success");
                req.getSession().setAttribute("toEditCableBox", 0);

                String queryString = "from CableBox where 1=1 and dslamId = ? and type = ? and rownum <= ? order by dslamId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, dslamId);
                queryObject.setParameter(1, Constant.DSLAM_CABLE_TYPE);
                queryObject.setParameter(2, String.valueOf(SEARCH_RESULT_LIMIT));
                List<CableBox> listcablebox = queryObject.list();

                String dslamCode = this.findbyDslamId1(bo.getDslamId()).getCode();
                req.setAttribute(DSLAM_ID, bo.getDslamId());
                req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);

                req.getSession().removeAttribute("listcablebox");
                req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("End method preparePage of CommItemGroupsDAO");
                return pageForward;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of CommItemGroupsDAO");
        return pageForward;
    }


    /* Ham sua thong tin cap nhanh thuoc Dslam
     * Author NamLT
     * Date 20/9/2010
     */
    public String editCableBoxOfDslam() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        CableBoxForm f = this.cableBoxForm;
        Long dslamId = f.getDslamId();
        if (userToken != null) {
            try {
                if (checkValidateToEditDslam()) {
                    String cableBoxId = session.getAttribute("cableBoxId").toString();
                    CableBox bo = this.findById(Long.parseLong(cableBoxId));
                    f.copyDataToBO(bo);
//                    List<CableBox> lstCablebox = (List<CableBox>) this.findByDslamId(dslamId);
//                    Long boardId = lstCablebox.get(0).getBoardId();
                    // bo.setDslamId(dslamId);
                    // bo.setBoardId(boardId);
                    getSession().update(bo);
                    // boardsId = f.getBoardId();
                    req.setAttribute("message", "Cablebox.edit.success");
                    req.getSession().setAttribute("toEditCableBox", 0);
                    f.resetForm();

                    String queryString = "from CableBox where 1=1 and dslamId = ? and type = ? and rownum <= ? order by dslamId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, dslamId);
                queryObject.setParameter(1, Constant.DSLAM_CABLE_TYPE);
                queryObject.setParameter(2, String.valueOf(SEARCH_RESULT_LIMIT));
                List<CableBox> listcablebox = queryObject.list();

                    String dslamCode = this.findbyDslamId1(bo.getDslamId()).getCode();
                    req.setAttribute(DSLAM_ID, bo.getDslamId());
                    req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);

                    req.getSession().removeAttribute("listcablebox");
                    req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
                    pageForward = CABLE_BOX_OF_DSLAM;
                } else {
                    pageForward = CABLE_BOX_OF_DSLAM;
                    return pageForward;
                }
            } catch (Exception e) {
                e.printStackTrace();
                pageForward = CABLE_BOX_OF_DSLAM;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }


    /* Ham check thong tin can sua
     * Author NamLT
     * Date 20/9/2010
     */
    private boolean checkValidateToEditDslam() {
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        Long count;
        CableBoxForm f = this.getCableBoxForm();
        String name = f.getName().trim();
        String code = f.getCode().trim();
        Long id = (Long) session.getAttribute("cableBoxId");
        if (checkForm()) {
//            String strQuery = "select count(*) from CableBox as model where (upper(model.name)=? or upper(model.code)= ? )and not model.cableBoxId = ? and not model.status= ?";
            String strQuery = "select count(*) from CableBox as model where (upper(model.name)=? and upper(model.code)= ? ) and model.cableBoxId != ? ";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, name.toUpperCase());
            q.setParameter(1, code.toUpperCase());
            q.setParameter(2, id);
//            q.setParameter(3, String.valueOf(Constant.STATUS_DELETE));
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "Cablebox.edit.duplicateName");
                return false;
            }
        } else {
            return false;
        }
        if (f.getStatus() == Constant.STATUS_USE) {
            String strQuery = "select count(*) from Dslam where status = ? and dslamId = ?";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, Constant.STATUS_DELETE);
            q.setParameter(1, f.getDslamId());
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "Cablebox.edit.error.dslamNotEnable");
                return false;
            }
        }
        return true;
    }

    /* Ham them moi cap nhanh thuoc Dslam
     * Author NamLT
     * Date 20/9/2010
     */
    public String addNewCableBoxOfDslam() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = CABLE_BOX_OF_DSLAM;
        CableBoxForm f = this.getCableBoxForm();
        Long dslamId = f.getDslamId();
        try {
            if (checkValidateToAddDslam()) {
                CableBox bo = new CableBox();
                bo.setCableBoxId(getSequence("CABLE_BOX_SEQ"));
                f.copyDataToBO(bo);
                List<CableBox> lstCablebox = (List<CableBox>) this.findByDslamId(dslamId);
                // Long boardId = lstCablebox.get(0).getBoardId();
                bo.setDslamId(dslamId);
                // bo.setBoardId(boardId);
                bo.setType(Constant.DSLAM_CABLE_TYPE);
                save(bo);
                f.resetForm();
                req.setAttribute("message", "Cablebox.add.success");

                String queryString = "from CableBox where 1=1 and dslamId = ? and type = ? and rownum <= ? order by dslamId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, dslamId);
                queryObject.setParameter(1, Constant.DSLAM_CABLE_TYPE);
                queryObject.setParameter(2, String.valueOf(SEARCH_RESULT_LIMIT));
                List<CableBox> listcablebox = queryObject.list();

                req.getSession().removeAttribute("listcablebox");
                req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);

                String dslamCode = this.findbyDslamId1(dslamId).getCode();
                req.setAttribute(DSLAM_ID, dslamId);
                req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
            } else {
                return pageForward;
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = CABLE_BOX_OF_DSLAM;
        }
        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }

    public String Cancel() throws Exception {
        log.info("Begin method preparePage of CableBoxDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = CABLE_BOX_OF_DSLAM;
        CableBoxForm f = this.cableBoxForm;
        try {
            Long dslamId = f.getDslamId();
            String dslamCode = f.getDslamcode();
            f.resetForm1();

            String queryString = "from CableBox where 1=1 and dslamId = ? and type = ? and rownum <= ? order by dslamId, code ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, dslamId);
            queryObject.setParameter(1, Constant.DSLAM_CABLE_TYPE);
            queryObject.setParameter(2, String.valueOf(SEARCH_RESULT_LIMIT));
            List<CableBox> listcablebox = queryObject.list();

            req.getSession().removeAttribute("listcablebox");
            req.setAttribute(DSLAM_ID, dslamId);
            req.setAttribute(SESSION_LIST_CABLE_BOX, listcablebox);
            req.setAttribute("dslamCode", dslamCode);

            //  req.setAttribute("toEditCableBox", 0);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = CABLE_BOX_OF_DSLAM;
        }
        log.info("End method preparePage of CableBoxDAO");
        return pageForward;
    }
}
