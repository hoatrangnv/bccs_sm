package com.viettel.im.database.DAO;

import com.viettel.database.BO.ActionResultBO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Dslam;
import com.viettel.im.client.form.BoardsForm;
import com.viettel.im.database.BO.Area;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Boards;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

public class BoardsDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(BoardsDAO.class);
    // property constants
    public static final String DSLAM_ID = "dslamId";
    public static final String BOARD_ID = "boardId";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String MAX_PORTS = "maxPorts";
    public static final String USED_PORTS = "usedPorts";
    public static final String STATUS = "status";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String RESERVED_PORT = "reservedPort";
    private static final String REQUEST_DSLAM_CODE = "dslamCode";
    private static final String REQUEST_DSLAM_ID = "dslamId";
    public static final String SESSION_LIST_BOARD = "listBoards";
    private String pageForward;
    public static final String ADD_NEW_BOARDS = "addNewBoards";
    public static final int SEARCH_RESULT_LIMIT = 1000;
    //bien form
    private BoardsForm boardsForm = new BoardsForm();

    public BoardsForm getBoardsForm() {
        return boardsForm;
    }

    /**
     * @param boardsForm the BoardsForm to set
     */
    public void setBoardsForm(BoardsForm boardsForm) {
        this.boardsForm = boardsForm;
    }

    /**
     * @return the BoardsForm
     */
    public void save(Boards transientInstance) {
        log.debug("saving Boards instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Boards persistentInstance) {
        log.debug("deleting Boards instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Boards findById(java.lang.Long id) {
        log.debug("getting Boards instance with id: " + id);
        try {
            Boards instance = (Boards) getSession().get(
                    "com.viettel.im.database.BO.Boards", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Boards instance) {
        log.debug("finding Boards instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.Boards").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Boards instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Boards as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByDslamId(Object dslamId) {
        return findByProperty(DSLAM_ID, dslamId);
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

    public List findByReservedPort(Object reservedPort) {
        return findByProperty(RESERVED_PORT, reservedPort);
    }

    public List findAll() {
        log.debug("finding all Boards instances");
        try {
//            String queryString = "select new Boards(a.boardId,a.dslamId,a.code,a.name,a.address,a.maxPorts,a.usedPorts,a.status," + "a.x,a.y,a.reservedPort,b.code) from Boards a,Dslam b where a.dslamId = b.dslamId and not a.status = ?" + "order by nlssort(a.code,'nls_sort=Vietnamese') asc";
            String queryString = "select new Boards(a.boardId,a.dslamId,a.code,a.name,a.address,a.maxPorts,a.usedPorts,a.status," + "a.x,a.y,a.reservedPort,b.code) from Boards a,Dslam b where a.dslamId = b.dslamId"
                    + " order by nlssort(a.code,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, Constant.STATUS_DELETE.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Boards merge(Boards detachedInstance) {
        log.debug("merging Boards instance");
        try {
            Boards result = (Boards) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Boards instance) {
        log.debug("attaching dirty Boards instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Boards instance) {
        log.debug("attaching clean Boards instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    // Tim list DSLAM ID cho combo box  BO DSLAM

    public List findDSLAMbyDSLAMCode(String province, String DSLAMCode) {
        log.debug("finding DSLAM with DSLAM code");
        try {
            String queryString = "from Dslam as model where model.province = ? and  model.code = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, province);
            queryObject.setParameter(1, DSLAMCode);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by DSLAM name failed", re);
            throw re;
        }
    }

    public Dslam findbyDSLAMId(java.lang.Long dslamId) {
        log.debug("finding DSLAM with DSLAM id");
        try {
            Dslam instance = (Dslam) getSession().get(
                    "com.viettel.im.database.BO.Dslam", dslamId);
            return instance;
        } catch (RuntimeException re) {
            log.error("find by DSLAM Id failed", re);
            throw re;
        }
    }

    private List<Boards> findBoardsByDslamId(Long dslamId){
        try {
            StringBuilder queryString = new StringBuilder();
            queryString.append("from Boards where 1=1");
            if(dslamId != null){
                queryString.append(" and dslamId = ? ");
            }

            log.debug("dslamId : " + dslamId);

            queryString.append(" and rownum <= ? order by dslamId, code ");

            log.debug("SQL:" + queryString.toString());
            Query queryObject = getSession().createQuery(queryString.toString());
            int index = 0;
            if (dslamId != null) {
                queryObject.setParameter(index++, dslamId);
            }

            queryObject.setParameter(index++, Long.parseLong(String.valueOf(SEARCH_RESULT_LIMIT)));

            //List<Boards> listBoards = this.findAll("where 1=1 and dslamId = " + dslamId.toString() + " and rownum <= " + String.valueOf(SEARCH_RESULT_LIMIT), "order by dslamId, code ");
            List<Boards> listBoards = queryObject.list();
            return listBoards;
        } catch (Exception e) {
            log.error("Error :",e);
            return null;
        }
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        HttpServletRequest req = getRequest();
        BoardsForm f = this.getBoardsForm();
        Long dslamId = f.getDslamId();
        f.resetForm();
        List<Boards> listBoards = findBoardsByDslamId(dslamId);
        req.getSession().removeAttribute("listBoards");
        req.getSession().setAttribute("listBoards", listBoards);
        req.getSession().setAttribute("toEditBoards", 0);
        pageForward = ADD_NEW_BOARDS;
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        searchBoards();
        pageForward = "pageNavigator";

        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    public String addBoards() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        HttpServletRequest req = getRequest();
        BoardsForm f = this.getBoardsForm();
        Long dslamId = f.getDslamId();
        try {
            if (checkValidateToAdd()) {
                Boards bo = new Boards();
                f.copyDataToBO(bo);
                Long boardId = getSequence("BOARD_SEQ");
                bo.setBoardId(boardId);
                bo.setDslamId(dslamId);
                save(bo);
                getSession().flush();
                f.resetForm();
                req.setAttribute("message", "boards.add.success");
            }

            List<Boards> listBoards = findBoardsByDslamId(dslamId);// this.findAll("where 1=1 and dslamId = " + dslamId + " and rownum <= " + String.valueOf(SEARCH_RESULT_LIMIT), "order by dslamId, code ");
            String dslamCode = findbyDSLAMId(dslamId).getCode();
            req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
            req.setAttribute(REQUEST_DSLAM_ID, dslamId);
            req.getSession().removeAttribute("listBoards");
            req.setAttribute(SESSION_LIST_BOARD, listBoards);
            pageForward = ADD_NEW_BOARDS;
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", e.getMessage());
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

            if (dslamId == null) {
                dslamId = -1L;
            }
            List<Boards> listBoards = findBoardsByDslamId(dslamId) ;//this.findAll("where 1=1 and dslamId = " + dslamId + " and rownum <= " + String.valueOf(SEARCH_RESULT_LIMIT), "order by dslamId, code ");
            String dslamCode = findbyDSLAMId(dslamId).getCode();
            req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
            req.setAttribute(REQUEST_DSLAM_ID, dslamId);
            req.getSession().removeAttribute("listBoards");
            req.setAttribute(SESSION_LIST_BOARD, listBoards);
            pageForward = ADD_NEW_BOARDS;
        }
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    public String editBoards() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        BoardsForm f = this.getBoardsForm();
        Long dslamId = f.getDslamId();
        if (userToken != null) {
            try {
                if (checkValidateToEdit()) {
                    Boards bo = new Boards();
                    f.copyDataToBO(bo);
                    bo.setBoardId(f.getBoardId());
                    bo.setDslamId(dslamId);
                    getSession().update(bo);
                    getSession().flush();
                    f.resetForm();
                    req.setAttribute("message", "boards.edit.success");
                    req.getSession().setAttribute("toEditBoards", 0);
                }

                List<Boards> listBoards = findBoardsByDslamId(dslamId) ;//this.findAll("where 1=1 and dslamId = " + dslamId.toString() + " and rownum <= " + String.valueOf(SEARCH_RESULT_LIMIT), "order by dslamId, code ");
                String dslamCode = findbyDSLAMId(dslamId).getCode();
                req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
                req.setAttribute(REQUEST_DSLAM_ID, dslamId);
                req.getSession().removeAttribute("listBoards");
                req.setAttribute(SESSION_LIST_BOARD, listBoards);
                pageForward = ADD_NEW_BOARDS;
            } catch (Exception e) {
                e.printStackTrace();
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                if (dslamId == null) {
                    dslamId = -1L;
                }
                List<Boards> listBoards = findBoardsByDslamId(dslamId) ;// this.findAll("where 1=1 and dslamId = " + dslamId.toString() + " and rownum <= " + String.valueOf(SEARCH_RESULT_LIMIT), "order by dslamId, code ");
                String dslamCode = findbyDSLAMId(dslamId).getCode();
                req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
                req.setAttribute(REQUEST_DSLAM_ID, dslamId);
                req.getSession().removeAttribute("listBoards");
                req.setAttribute(SESSION_LIST_BOARD, listBoards);
                req.setAttribute("message", e.getMessage());
                pageForward = ADD_NEW_BOARDS;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    public String prepareEditBoards() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        BoardsForm f = this.getBoardsForm();
        if (userToken != null) {
            try {
                String strBoardId = (String) QueryCryptUtils.getParameter(req, "boardId");
                Long boardId;
                try {
                    boardId = Long.parseLong(strBoardId);
                } catch (Exception ex) {
                    boardId = -1L;
                }
                try {
                    Boards bo = new Boards();
                    bo = findById(boardId);
                    f.copyDataFromBO(bo);
                    String dslamCode = findbyDSLAMId(bo.getDslamId()).getCode();
                    req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
                    req.setAttribute(REQUEST_DSLAM_ID, bo.getDslamId());
                    req.getSession().setAttribute("toEditBoards", 1);
                } catch (Exception ex) {
                }
                pageForward = ADD_NEW_BOARDS;
            } catch (Exception e) {
                e.printStackTrace();
                pageForward = ADD_NEW_BOARDS;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }

    public String deleteBoards() throws Exception {
        log.info("Begin method preparePage of BoardsDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        BoardsForm f = this.getBoardsForm();
        pageForward = ADD_NEW_BOARDS;
        if (userToken != null) {
            try {
                String strBoardId = (String) QueryCryptUtils.getParameter(req, "boardId");
                Long boardId;
                try {
                    boardId = Long.parseLong(strBoardId);
                } catch (Exception ex) {
                    boardId = -1L;
                }
                Boards bo = new Boards();
                bo = findById(boardId);
                if (bo == null) {
                    req.setAttribute("message", "boards.delete.error");
                    log.info("End method preparePage of BoardsDAO");
                    return pageForward;
                }
                // Kiem tra xem cap nhanh co con duoc su dung hay khong
                String strQuery = "select count(*) from CableBox where boardId = ? and status = ? ";
                Query query = getSession().createQuery(strQuery);
                query.setParameter(0, boardId);
                query.setParameter(1, Constant.STATUS_USE);
                Long count = (Long) query.list().get(0);
                if (count.compareTo(0L) > 0) {
                    req.setAttribute("message", "boards.error.boardsInUse");
                    req.getSession().setAttribute("toEditBoards", 0);
                    List<Boards> listBoards = findBoardsByDslamId(bo.getDslamId());// this.findAll("where 1=1 and dslamId = " + bo.getDslamId().toString() + " and rownum <= " + String.valueOf(SEARCH_RESULT_LIMIT), "order by dslamId, code ");
                    req.getSession().removeAttribute("listBoards");
                    req.setAttribute(SESSION_LIST_BOARD, listBoards);
                    log.info("End method deleteReasonGroup of ReasonDAO");
                    return pageForward;
                }
                getSession().delete(bo);

                Long dslamId = bo.getDslamId();
                req.setAttribute("message", "boards.delete.success");
                req.getSession().setAttribute("toEditBoards", 0);
                List<Boards> listBoards = findBoardsByDslamId(dslamId);// this.findAll("where 1=1 and dslamId = " + dslamId.toString() + " and rownum <= " + String.valueOf(SEARCH_RESULT_LIMIT), "order by dslamId, code ");
                String dslamCode = findbyDSLAMId(dslamId).getCode();
                req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
                req.setAttribute(REQUEST_DSLAM_ID, dslamId);

                req.getSession().removeAttribute("listBoards");
                req.setAttribute(SESSION_LIST_BOARD, listBoards);
                f.resetForm();
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("message", "boards.delete.error");
                log.info("End method preparePage of BoardsDAO");
                return pageForward;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("End method preparePage of BoardsDAO");
        return pageForward;
    }
//Modified by TheTM <--End-->

    public String searchBoards() throws Exception {
        log.info("Begin method searchBoards of BoardsDAO ...");
        HttpServletRequest req = getRequest();
        BoardsForm f = this.getBoardsForm();
        pageForward = ADD_NEW_BOARDS;

        if (f.getDslamId() == null || f.getDslamId().intValue() <= 0) {
            return pageForward;
        }

        List listParameter = new ArrayList();
        String strQuery = "select new Boards(a.boardId,a.dslamId,a.code,a.name,a.address,a.maxPorts,a.usedPorts,"
                + "a.status,a.x,a.y,a.reservedPort,b.code) from Boards a,Dslam b where a.dslamId = b.dslamId";

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
        if (f.getDslamcode() != null && !f.getDslamcode().trim().equals("")) {
            List temp = findbyDSLAMcode(f.getDslamcode());
            Dslam tempobj = (Dslam) temp.get(0);
            Long tempLong = tempobj.getDslamId();
            f.setDslamId(tempLong);
            listParameter.add(f.getDslamId());
            strQuery += " and a.dslamId = ? ";
        }
        if (f.getProvince() != null && !f.getProvince().trim().equals("")) {
            strQuery += " and a.dslamId in (select dslamId from Dslam a where a.province = ?) ";
            listParameter.add(f.getProvince().trim());

        }
        if (f.getX() != null && !f.getX().trim().equals("")) {
            try {
                if (Long.parseLong(f.getX().trim()) < 0) {
                    req.setAttribute("message", "boards.error.XNegative");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getX().trim()));
                    strQuery += " and a.x = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.XNumberFormat");
                return pageForward;
            }
        }
        if (f.getY() != null && !f.getY().trim().equals("")) {
            try {
                if (Long.parseLong(f.getY().trim()) < 0) {
                    req.setAttribute("message", "boards.error.YNegative");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getY().trim()));
                    strQuery += " and a.y = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.YNumberFormat");
                return pageForward;
            }
        }
        if (f.getMaxPorts() != null && !f.getMaxPorts().trim().equals("")) {
            try {
                if (Long.parseLong(f.getMaxPorts().trim()) < 0) {
                    req.setAttribute("message", "boards.error.maxPortNegative");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getMaxPorts().trim()));
                    strQuery += " and a.maxPorts = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.maxPortNumberFormat");
                return pageForward;
            }
        }
        if (f.getUsedPorts() != null && !f.getUsedPorts().trim().equals("")) {
            try {
                if (Long.parseLong(f.getUsedPorts().trim()) < 0) {
                    req.setAttribute("message", "boards.error.usedPortNegative");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getUsedPorts().trim()));
                    strQuery += " and a.usedPorts = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.usedPortNumberFormat");
                return pageForward;
            }
        }
        if (f.getReservedPort() != null && !f.getReservedPort().trim().equals("")) {
            try {
                if (Long.parseLong(f.getReservedPort().trim()) < 0) {
                    req.setAttribute("message", "boards.error.reservedPortNegative");
                    return pageForward;
                } else {
                    listParameter.add(Long.parseLong(f.getReservedPort().trim()));
                    strQuery += " and a.reservedPort = ? ";
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.reservedPortNumberFormat");
                return pageForward;
            }
        }
        strQuery += " order by nlssort( a.code ,'nls_sort=Vietnamese') asc ";

        Query q = getSession().createQuery(strQuery);
        for (int i = 0; i < listParameter.size(); i++) {
            q.setParameter(i, listParameter.get(i));
        }
        List listBoards = new ArrayList();
        listBoards = q.list();
        req.getSession().setAttribute("toEditBoards", 0);
        if (listBoards != null) {
            req.setAttribute("message", "boards.search");
            List paramMsg = new ArrayList();
            paramMsg.add(listBoards.size());
            req.setAttribute("paramMsg", paramMsg);
        } else {
            req.setAttribute("message", "boards.search.Notsucess");
        }
        String dslamCode = findbyDSLAMId(f.getDslamId()).getCode();
        req.setAttribute(REQUEST_DSLAM_CODE, dslamCode);
        req.setAttribute(REQUEST_DSLAM_ID, f.getDslamId());
        req.setAttribute(SESSION_LIST_BOARD, listBoards);

        pageForward = ADD_NEW_BOARDS;
        log.info("End method searchPartner of BoardsDAO ...");
        return pageForward;
    }
    private Map ListProvinceNoCombo = new HashMap();
    private Map ListDSLNameCombo = new HashMap();
    private Map provinceName = new HashMap();
    private Map ListDslamcodeCombo = new HashMap();

    public String getListProvince() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String province = httpServletRequest.getParameter("BoardsForm.province");
            List<Area> ListProvinceNo = new ArrayList();
            if (province != null) {
                if ("".equals(province)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Area where district IS NULL and precinct IS NULL ";
                queryString += " and lower(areaCode) like ? ";
                parameterList.add("%" + province.toLowerCase() + "%");
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    ListProvinceNo = queryObject.list();
                    for (int i = 0; i < ListProvinceNo.size(); i++) {
                        ListProvinceNoCombo.put(ListProvinceNo.get(i).getFullName(), ListProvinceNo.get(i).getAreaCode());
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
//    public String getListDSLName() throws Exception {
//        try {
//            HttpServletRequest httpServletRequest = getRequest();
//            HttpSession session = httpServletRequest.getSession();
//            String province = httpServletRequest.getParameter("province");
//            session.setAttribute("provinceValue",province);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return "success";
//    }

    public String getListDslamcode() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String dslamcode = httpServletRequest.getParameter("BoardsForm.dslamcode");
            String province = (String) httpServletRequest.getSession().getAttribute("provinceValue");
            if (httpServletRequest.getParameter("province") != null) {
                province = httpServletRequest.getParameter("province");
                getRequest().getSession().setAttribute("provinceValue", province);
            } else {
                try {
                    province = (String) httpServletRequest.getSession().getAttribute("provinceValue");
                } catch (Exception e) {
                    province = null;
                }
            }
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
                if (province != null) {
                    queryString += " and province = ? ";
                    parameterList.add(province);
                }
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                // queryObject.setMaxResults(8); //lay 8 ket qua thoa man
                List<Dslam> listDslam = queryObject.list();
                if (listDslam != null && listDslam.size() > 0) {
                    for (int i = 0; i < listDslam.size(); i++) {
                        ListDslamcodeCombo.put(listDslam.get(i).getName(), listDslam.get(i).getCode());
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

    public Map getListProvinceNoCombo() {
        return ListProvinceNoCombo;
    }

    public void setListProvinceNoCombo(Map ListProvinceNoCombo) {
        this.ListProvinceNoCombo = ListProvinceNoCombo;
    }

    public Map getListDSLNameCombo() {
        return ListDSLNameCombo;
    }

    public void setListDSLNameCombo(Map ListDSLNameCombo) {
        this.ListDSLNameCombo = ListDSLNameCombo;
    }

    public Map getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(Map provinceName) {
        this.provinceName = provinceName;
    }

    public Map getListDslamcodeCombo() {
        return ListDslamcodeCombo;
    }

    public void setListDslamcodeCombo(Map ListDslamcodeCombo) {
        this.ListDslamcodeCombo = ListDslamcodeCombo;
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

    public List findbyprovince(String province) {
        log.debug("finding Province ");
        try {
            String queryString = "from Dslam as model where model.province = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, province);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find Province failed", re);
            throw re;
        }
    }

    public List findProvinceName(String province) {
        log.debug("finding Province ");
        try {
            String queryString = "from Area as model where model.parentCode is null and model.province = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, province);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find ProvinceName failed", re);
            throw re;
        }
    }

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        BoardsForm f = this.getBoardsForm();
        String province = f.getProvince();
        String dslamcode = f.getDslamcode();
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
            req.setAttribute("message", "boards.error.invalidCode");
            return false;
        } else {
            code = code.trim().toUpperCase();
        }
        if ((name == null) || name.trim().equals("")) {
            req.setAttribute("message", "boards.error.invalidName");
            return false;
        } else {
            name = name.trim();
        }
        if ((maxPort == null) || maxPort.trim().equals("")) {
            req.setAttribute("message", "boards.error.invalidmaxPort");
            return false;
        } else {
            maxPort = maxPort.trim();
        }
        if ((maxPort != null) && !maxPort.trim().equals("")) {
            try {
                if (Integer.parseInt(maxPort.trim()) < 0) {
                    req.setAttribute("message", "boards.error.maxPortNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.maxPortNumberFormat");
                return false;
            }
        }

        if ((usedPort != null) && !usedPort.trim().equals("")) {
            try {
                if (Integer.parseInt(usedPort.trim()) < 0) {
                    req.setAttribute("message", "boards.error.usedPortNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.usedPortNumberFormat");
                return false;
            }
        }
        if (status == null) {
            req.setAttribute("message", "boards.error.invalidstatus");
            return false;
        }
        if ((reservedPort == null) || reservedPort.trim().equals("")) {
            req.setAttribute("message", "ERR.MDF.OUTSIDE.044");
            return false;
        } else {
            reservedPort = reservedPort.trim();
        }
        if ((reservedPort != null) && !reservedPort.trim().equals("")) {
            try {

                if (Integer.parseInt(reservedPort.trim()) < 0) {
                    req.setAttribute("message", "boards.error.reservedPortNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.reservedPortNumberFormat");
                return false;
            }
        }

        if ((x != null) && !x.trim().equals("")) {
            try {
                if (Long.parseLong(x.trim()) < 0) {
                    req.setAttribute("message", "boards.error.XNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.XNumberFormat");
                return false;
            }
        }
        if ((y != null) && !y.trim().equals("")) {
            try {
                if (Long.parseLong(y.trim()) < 0) {
                    req.setAttribute("message", "boards.error.YNegative");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("message", "boards.error.YNumberFormat");
                return false;
            }
        }
        if (((province != null) && !province.trim().equals("")) && ((dslamcode != null) && !dslamcode.trim().equals("")) && findDSLAMbyDSLAMCode(province, dslamcode).size() <= 0) {
            req.setAttribute("message", "boards.error.NoNameDslam");
            return false;
        }
        if ((maxPort != null) && !maxPort.trim().equals("") && (reservedPort != null) && !reservedPort.trim().equals("")) {
            Long maxPorts = Long.parseLong(maxPort.trim());
            Long reservedPorts = Long.parseLong(reservedPort.trim());
            if (maxPorts < reservedPorts) {
                req.setAttribute("message", "ERR.MDF.OUTSIDE.043");
                return false;
            }
        }
        if ((maxPort != null) && !maxPort.trim().equals("") && (usedPort != null) && !usedPort.trim().equals("")) {
            Long maxPorts = Long.parseLong(maxPort.trim());
            Long usedPorts = Long.parseLong(usedPort.trim());
            if (maxPorts < usedPorts) {
                req.setAttribute("message", "boards.error.InvalidPort1");
                return false;
            }
        }
        if ((maxPort != null) && !maxPort.trim().equals("") && (usedPort != null) && !usedPort.trim().equals("") && (reservedPort != null) && !reservedPort.trim().equals("")) {
            Long maxPorts = Long.parseLong(maxPort.trim());
            Long usedPorts = Long.parseLong(usedPort.trim());
            Long reservedPorts = Long.parseLong(reservedPort);

            if (maxPorts < usedPorts + reservedPorts) {
                req.setAttribute("message", "boards.error.InvalidPort");
                return false;
            }
        }
        return true;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : kiem tra tinh hop le cua Board truoc khi them vao CSDL
     *
     */
    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();

        if (checkForm()) {
            String name = this.boardsForm.getName().trim();
            String code = this.boardsForm.getCode().trim();

            //kiem tra tinh trung lap cua ten hoac ma
            Long count;
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select     count(*) ");
            strQuery.append("from       Boards as model ");
            strQuery.append("where      upper(model.name) = ? or upper(model.code) = ? ");
            Query q = getSession().createQuery(strQuery.toString());
            q.setParameter(0, name.toUpperCase());
            q.setParameter(1, code.toUpperCase());
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "boards.add.duplicateName");
                return false;
            }

            //truong hop them moi Board hieu luc -> kiem tra dslam co hieu luc khong?
            if (Constant.STATUS_ACTIVE.equals(this.boardsForm.getStatus())) {
                strQuery = new StringBuilder("");
                strQuery.append("select     count(*) ");
                strQuery.append("from       Dslam ");
                strQuery.append("where      status = ? and dslamId = ? ");

                q = getSession().createQuery(strQuery.toString());
                q.setParameter(0, Constant.STATUS_DELETE);
                q.setParameter(1, this.boardsForm.getDslamId());
                count = (Long) q.list().get(0);

                if ((count != null) && (count.compareTo(0L) > 0)) {
                    req.setAttribute("message", "ERR.MDF.OUTSIDE.036");
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
        Long count;
        BoardsForm f = getBoardsForm();
        String name = f.getName().trim();
        String code = f.getCode().trim();
        Long id = f.getBoardId();
        if (checkForm()) {
            String strQuery = "select count(*) from Boards as model where ( upper(model.name) = ? or upper(model.code) = ? ) and not model.boardId = ? ";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, name.toUpperCase());
            q.setParameter(1, code.toUpperCase());
            q.setParameter(2, id);
//            q.setParameter(3, Constant.STATUS_DELETE.toString());
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "boards.edit.duplicateName");
                return false;
            }
        } else {
            return false;
        }

        if (this.boardsForm.getStatus() != Constant.STATUS_USE) {
//            String strQuery = "select count(*) from CableBox where status = ? and boardId = ? ";
//
//            Query q = getSession().createQuery(strQuery);
//            q.setParameter(0, Constant.STATUS_USE);
//            q.setParameter(1, id);
//            count = (Long) q.list().get(0);
//
//            if ((count != null) && (count.compareTo(0L) > 0)) {
//                req.setAttribute("message", "boards.edit.error.hasCableBoxInBoard");
//                return false;
//            }
        } else {
            String strQuery = "select count(*) from Dslam where status = ? and dslamId = ? ";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, Constant.STATUS_DELETE);
            q.setParameter(1, f.getDslamId());
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("message", "boards.edit.error.dslamNotEnable");
                return false;
            }
        }
        return true;
    }
    
    private boolean checkPortOutsideExist(Long boardId) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select count(*) ");
        strQuery.append(" from Boards b,CableBox cb,PortOutside po ");
        strQuery.append(" where b.boardId = cb.boardId");
        strQuery.append(" and cb.cableBoxId = po.cableBoxId");
        strQuery.append(" and b.boardId = ? ");
        strQuery.append(" and po.status in (?,?) ");
        Query query = getSession().createQuery(strQuery.toString());
        query.setParameter(0, boardId);
        query.setParameter(1, Constant.PORT_OUTSIDE_STATUS_USING);
        query.setParameter(2, Constant.PORT_OUTSIDE_STATUS_LOCK);
        List list = query.list();
        if (list != null && list.size() > 0) {
            Long count = (Long) list.get(0);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
