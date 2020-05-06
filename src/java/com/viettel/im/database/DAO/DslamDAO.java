/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Andv
 */
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Dslam;
import com.viettel.im.client.form.DslamForm;
import com.viettel.im.client.form.BoardsForm;
import com.viettel.im.client.bean.DslamBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.CableBoxForm;
import com.viettel.im.client.form.CableNoForm;
import com.viettel.im.database.BO.UserToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Query;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Chassic;
import com.viettel.im.database.BO.Boards;
import com.viettel.im.database.BO.CableBox;
import com.viettel.im.database.BO.Mdf;
import java.util.HashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import java.util.Iterator;
import java.util.Map;

public class DslamDAO extends BaseDAOAction {
    //dinh nghia cac bien page forward

    private static final Log log = LogFactory.getLog(DslamDAO.class);
    private String pageForward;
    private static final String ADD_NEW_DSLAM = "addDslam";
    private static final String PAGE_FORWARD_DSLAM_CABLE_OVERVIEW = "pageDslamCableOverview";
    private static final String PAGE_FORWARD_LIST_DSLAM_CABLE = "pageListDslamCable";
    private static final String PAGE_FORWARD_LIST_CABLE_BOX_OF_DSLAM = "pageListCableBoxOfDslam";
    private static final String PAGE_FORWARD_OVERVIEW = "pageOverview";
    private static final String PAGE_FORWARD_LIST_DSLAM = "pageListDslam";
    private static final String PAGE_FORWARD_LIST_BOARD = "pageListBoard";
    private static final String PAGE_FORWARD_LIST_CABLE_BOX = "pageListCableBox";
    private static final String PAGE_FORWARD_LIST_CABLE_NO = "pageListCableNo";
    private static final String PAGE_FORWARD_SUCCESS = "success";
    //dinh nghia cac bien thuoc tinh
    public static final String TABLE_NAME = "Dslam";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    //dinh nghia cac bien form
    private DslamForm dslamForm = new DslamForm();

    public DslamForm getDslamForm() {
        return dslamForm;
    }

    public void setDslamForm(DslamForm dslamForm) {
        this.dslamForm = dslamForm;
    }
    private BoardsForm BoardsForm = new BoardsForm();

    public BoardsForm getBoardsForm() {
        return BoardsForm;
    }

    public void setBoardsForm(BoardsForm BoardsForm) {
        this.BoardsForm = BoardsForm;
    }
    private CableBoxForm cableBoxForm = new CableBoxForm();

    public CableBoxForm getCableBoxForm() {
        return cableBoxForm;
    }

    public void setCableBoxForm(CableBoxForm cableBoxForm) {
        this.cableBoxForm = cableBoxForm;
    }
    private Map listItem = new HashMap();

    public Map getListItem() {
        return listItem;
    }

    public void setListItem(Map listItem) {
        this.listItem = listItem;
    }
    private CableNoForm cableNoForm = new CableNoForm();

    public CableNoForm getCableNoForm() {
        return cableNoForm;
    }

    public void setCableNoForm(CableNoForm cableNoForm) {
        this.cableNoForm = cableNoForm;
    }
    //
    private static final Long SEARCH_RESULT_LIMIT = 300L;
    private static final String TREE_NODE_ROOT = "0_";
    private static final String TREE_NODE_DSLAM = "1_";
    private static final String TREE_NODE_BOARD = "2_";
    private static final String TREE_NODE_CABLE_BOX = "3_";
    private static final String TREE_NODE_PROVINCE = "9_";
    private static final String TREE_NODE_PRODUCT = "8_";
    //dinh nghia cac bien request, session
    private static final String REQUEST_MESSAGE = "returnMsg";
    private static final String REQUEST_MESSAGE_PARAM = "returnMsgParam";
    private static final String REQUEST_LIST_PROVINCE = "listProvince";
    private static final String REQUEST_LIST_BRAS = "listBras";
    private static final String REQUEST_LIST_DSLAM = "listDslam";
    private static final String SESSION_LIST_BOARD = "listBoards";
    private static final String SESSION_LIST_CABLE_BOX = "listCableBox";
    private static final String SESSION_LIST_CABLE_NO = "listCableNo";
    private static final String SESSION_DSLAM_ID = "dslamId";
    private static final String SESSION_DSLAM_CODE = "dslamCode";
    private static final String SESSION_BOARD_ID = "boardId";
    private static final String SESSION_BOARD_CODE = "boardCode";
    private static final String SESSION_CABLE_BOX_ID = "cableBoxId";
    private static final String SESSION_CABLE_BOX_CODE = "cableBoxCode";
    private List listItems = new ArrayList();

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public Mdf findByMdfId(java.lang.Long id) {
        log.debug("getting Mdf instance with mdfId: " + id);
        try {
            Mdf instance = (Mdf) getSession().get("com.viettel.im.database.BO.Mdf", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public Dslam findById(java.lang.Long id) {
        log.debug("getting Dslam instance with dslamId: " + id);
        try {
            Dslam instance = (Dslam) getSession().get("com.viettel.im.database.BO.Dslam", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Dslam instance) {
        log.debug("finding Dslam instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.Dslam").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Dslam instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Dslam as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findAll(String whereString, String orderByString) {
        log.debug("finding all Dslam  instances");
        try {
            String queryString = "from Dslam " + whereString + " " + orderByString;
            Query queryObject = getSession().createQuery(queryString);
            //queryObject.setMaxResults(50);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all Dslam  instances");
        try {
            String queryString = "from Dslam order by code ";
            Query queryObject = getSession().createQuery(queryString);
            //queryObject.setMaxResults(50);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findDluByProvince(String provinceCode) {
        log.debug("finding all Dslam instances");
        try {
            Session session = getSession();
            String queryString = "from Dslam where province = ? " + "and productId = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, provinceCode);
            queryObject.setParameter(1, Constant.PRODUCT_ID_DLU);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

//    public String pageNavigator() throws Exception {
//        log.info("Begin method...");
//
//        HttpServletRequest req = getRequest();
//        pageForward = "pageNavigator";
//        req.setAttribute(REQUEST_LIST_DSLAM, getLstDslam());
//
//        log.info("End method preparePage of DslamDAO");
//        return pageForward;
//    }
    /**
     * ----------------------------------------------------------------------- *
     */
    //TRONGLV
    //HIEN THI TREEVIEW BAO GOM DSLAM -> BOARD -> CABLE_BOX
    /**
     *
     * author   : tronglv
     * date     :
     * desc     : HIEN THI TREEVIEW BAO GOM DSLAM -> BOARD -> CABLE_BOX
     * modified : tamdt1, 08/10/2010
     *
     */
    public String preparePageOverview() throws Exception {
        log.info("Begin preparePageOverview of DslamDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            //
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        log.info("End preparePageOverview of DslamDAO");
        pageForward = PAGE_FORWARD_OVERVIEW;
        return pageForward;
    }

    /**
     *
     * author   : tronglv
     * date     :
     * desc     : lay thong tin ve tree dslam
     *
     */
    public String getListDslamTree() throws Exception {
        try {
            HttpServletRequest req = getRequest();

            String node = QueryCryptUtils.getParameter(req, "nodeId");
            if (node == null) {
                return PAGE_FORWARD_SUCCESS;
            }

            node = node.trim();

            if (node.indexOf(TREE_NODE_PROVINCE) == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac province
                AreaDAO areaDAO = new AreaDAO();
                areaDAO.setSession(this.getSession());
                List<Area> listProvince = areaDAO.findAllProvince();

                Iterator iterProvince = listProvince.iterator();
                while (iterProvince.hasNext()) {
                    Area area = (Area) iterProvince.next();

                    String nodeId = TREE_NODE_ROOT + area.getProvince() + ";" + Constant.PRODUCT_ID_DSLAM.toString();
                    String doString = req.getContextPath() + "/dslamAction!getListDslam.do?province=" + area.getProvince() + "&productId=" + Constant.PRODUCT_ID_DSLAM.toString();
                    getListItems().add(new Node(area.getAreaCode() + " - " + area.getName() + " - DSLAM", "true", nodeId, doString));

                    nodeId = TREE_NODE_ROOT + area.getProvince() + ";" + Constant.PRODUCT_ID_DLU.toString();
                    doString = req.getContextPath() + "/dslamAction!getListDslam.do?province=" + area.getProvince() + "&productId=" + Constant.PRODUCT_ID_DLU.toString();
                    getListItems().add(new Node(area.getAreaCode() + " - " + area.getName() + " - " + Constant.DLU_STANDS, "true", nodeId, doString));
                }
            } else if (node.indexOf(TREE_NODE_ROOT) == 0) {
                node = node.substring(2);
                List<Dslam> listDslam = getListDslamByProvinceAndProduct(node.split(";")[0], node.split(";")[1]);
                Iterator iterDslam = listDslam.iterator();
                while (iterDslam.hasNext()) {
                    Dslam dslam = (Dslam) iterDslam.next();
                    String nodeId = TREE_NODE_DSLAM + dslam.getDslamId();
                    String doString = req.getContextPath() + "/dslamAction!getListBoard.do?dslamId=" + dslam.getDslamId();
                    getListItems().add(new Node(dslam.getCode() + " - " + dslam.getName(), "true", nodeId, doString));
                }
            } else if (node.indexOf(TREE_NODE_DSLAM) == 0) {
                node = node.substring(2);
                BoardsDAO boardsDAO = new BoardsDAO();
                boardsDAO.setSession(this.getSession());

                String queryString = "from Boards where 1=1 and dslamId = ? and rownum <= ? order by dslamId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, Long.valueOf(node));
                queryObject.setParameter(1, SEARCH_RESULT_LIMIT);

                List<Boards> listBoards = queryObject.list();

                Iterator iterBoards = listBoards.iterator();
                while (iterBoards.hasNext()) {
                    Boards boards = (Boards) iterBoards.next();
                    String nodeId = TREE_NODE_BOARD + boards.getBoardId();
                    String doString = req.getContextPath() + "/dslamAction!getListCableBox.do?boardId=" + boards.getBoardId();
                    getListItems().add(new Node(boards.getCode() + " - " + boards.getName(), "true", nodeId, doString));
                }
            } else if (node.indexOf(TREE_NODE_BOARD) == 0) {
                node = node.substring(2);
                CableBoxDAO cableBoxDAO = new CableBoxDAO();
                cableBoxDAO.setSession(this.getSession());
                
                String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, Long.valueOf(node));
                queryObject.setParameter(1, SEARCH_RESULT_LIMIT);
                List<CableBox> listCableBox = queryObject.list();

                Iterator iterCableBox = listCableBox.iterator();
                while (iterCableBox.hasNext()) {
                    CableBox cableBox = (CableBox) iterCableBox.next();
                    String nodeId = TREE_NODE_CABLE_BOX + cableBox.getCableBoxId();
                    String doString = req.getContextPath() + "/dslamAction!getListCableNo.do?cableBoxId=" + cableBox.getCableBoxId();
                    getListItems().add(new Node(cableBox.getCode() + " - " + cableBox.getName(), "true", nodeId, doString));
                }
            }


            return PAGE_FORWARD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //TRONGLV
    //BAM NUT DANH SACH DSLAM
    public String getListDslam() throws Exception {

        pageForward = PAGE_FORWARD_LIST_DSLAM;
        HttpServletRequest req = getRequest();
        String province = req.getParameter("province");
        String productId = req.getParameter("productId");

        if (province == null || province.equals("")) {
            province = dslamForm.getProvince();
        }
        if (productId == null || productId.equals("")) {
            productId = dslamForm.getProductId();

        }

        dslamForm.resetForm();

        dslamForm.setProvince(province);
        dslamForm.setProductId(productId);

        this.getDataForCombobox();

        this.searchDslam();
        req.setAttribute(REQUEST_MESSAGE, "");

//        req.setAttribute(REQUEST_LIST_DSLAM, this.getListDslamByProvinceAndProduct(province, productId));


        pageForward = PAGE_FORWARD_LIST_DSLAM;
        return pageForward;
    }

    public String updateDistrictList() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String province = req.getParameter("province");
            String target = req.getParameter("target");

            if (province != null && !province.trim().equals("")) {
                String queryString = "select areaCode, areaCode || '-' || name as name "
                        + "from Area as model "
                        + "where model.parentCode = ? "
                        + "order by areaCode asc";
                Session session = getSession();
                Query queryObject = session.createQuery(queryString);
                queryObject.setParameter(0, province.trim());
                List tmpList = queryObject.list();
                String[] tmpHeader = {"", getText("Chọn quận/huyện")};
                //String[] tmpHeader = {"", "--Chọn mặt hàng--"};
                List list = new ArrayList();
                list.add(tmpHeader);
                list.addAll(tmpList);
                this.listItem.put(target, list);

            } else {
                String[] tmpHeader = {"", getText("Chọn quận/huyện")};
                //String[] tmpHeader = {"", "--Chọn mặt hàng--"};
                List list = new ArrayList();
                list.add(tmpHeader);
                this.listItem.put(target, list);

            }

        } catch (Exception ex) {
            throw ex;
        }
        return "updateDistrictList";
    }

    public String updatePrecinctList() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String district = req.getParameter("district");
            String target = req.getParameter("target");

            if (district != null && !district.trim().equals("")) {
                String queryString = "select areaCode, areaCode || '-' || name as name "
                        + "from Area as model "
                        + "where model.parentCode = ? and "
                        + "order by areaCode asc";
                Session session = getSession();
                Query queryObject = session.createQuery(queryString);
                queryObject.setParameter(0, district.trim());
                List tmpList = queryObject.list();
                String[] tmpHeader = {"", getText("Chọn phường/xã")};
                //String[] tmpHeader = {"", "--Chọn mặt hàng--"};
                List list = new ArrayList();
                list.add(tmpHeader);
                list.addAll(tmpList);
                this.listItem.put(target, list);

            } else {
                String[] tmpHeader = {"", getText("Chọn phường/xã")};
                //String[] tmpHeader = {"", "--Chọn mặt hàng--"};
                List list = new ArrayList();
                list.add(tmpHeader);
                this.listItem.put(target, list);

            }

        } catch (Exception ex) {
            throw ex;
        }
        return "updatePrecinctList";
    }

    //TRONGLV
    //BAM NUT DANH SACH BOARD
    public String getListBoard() throws Exception {
        pageForward = PAGE_FORWARD_LIST_BOARD;
        HttpServletRequest req = getRequest();
        String strDslamId = req.getParameter("dslamId");
        Long dslamId = -1L;
        if (strDslamId != null && !strDslamId.trim().equals("")) {
            dslamId = Long.valueOf(strDslamId);
        } else {
            dslamId = this.BoardsForm.getDslamId();
        }

        this.BoardsForm.resetForm();

        BoardsDAO boardsDAO = new BoardsDAO();
        boardsDAO.setSession(this.getSession());

        String queryString = "from Boards where 1=1 and dslamId = ? and rownum <= ? order by dslamId, code ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, dslamId);
        queryObject.setParameter(1, SEARCH_RESULT_LIMIT);

        List<Boards> listBoards = queryObject.list();

        req.setAttribute(SESSION_LIST_BOARD, listBoards);

        req.setAttribute(SESSION_DSLAM_ID, dslamId);

        Dslam dslam = this.findById(dslamId);
        if (dslam != null) {
            req.setAttribute(SESSION_DSLAM_CODE, dslam.getCode());

            this.BoardsForm.setDslamId(dslam.getDslamId());
            this.BoardsForm.setDslamcode(dslam.getCode());
            this.BoardsForm.setDSLName(dslam.getName());
        }

        return pageForward;
    }

    //TRONGLV
    //BAM NUT DANH SACH CABLE_BOX
    public String getListCableBox() throws Exception {
        pageForward = PAGE_FORWARD_LIST_CABLE_BOX;
        HttpServletRequest req = getRequest();

        String strBoardId = req.getParameter("boardId");
        Long boardId = -1L;
        if (strBoardId != null && !strBoardId.trim().equals("")) {
            boardId = Long.valueOf(strBoardId);
        } else {
            boardId = this.cableBoxForm.getBoardId();
        }

        this.cableBoxForm.resetForm();

        CableBoxDAO cableBoxDAO = new CableBoxDAO();
        cableBoxDAO.setSession(this.getSession());

        String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, boardId);
        queryObject.setParameter(1, SEARCH_RESULT_LIMIT);
                List<CableBox> listCableBox = queryObject.list();

        req.setAttribute(SESSION_LIST_CABLE_BOX, listCableBox);

        req.setAttribute(SESSION_BOARD_ID, boardId);

        BoardsDAO boardDAO = new BoardsDAO();
        boardDAO.setSession(this.getSession());
        Boards board = boardDAO.findById(boardId);
        if (board != null) {
            req.setAttribute(SESSION_BOARD_CODE, board.getCode());

            this.cableBoxForm.setBoardId(board.getBoardId());
            this.cableBoxForm.setBoardscode(board.getCode());
            this.cableBoxForm.setBoardsName(board.getName());

        }

        return pageForward;
    }

    //TRONGLV
    //BAM NUT DANH SACH CABLE_NO
    public String getListCableNo() throws Exception {
        pageForward = PAGE_FORWARD_LIST_CABLE_NO;
        HttpServletRequest req = getRequest();

        String strCableBoxId = req.getParameter("cableBoxId");
        Long cableBoxId = -1L;
        if (strCableBoxId != null && !strCableBoxId.trim().equals("")) {
            cableBoxId = Long.valueOf(strCableBoxId);
        } else {
            cableBoxId = this.cableNoForm.getCableBoxId();
        }

        this.cableNoForm = new CableNoForm();
        cableNoForm.setCableBoxId(cableBoxId);

        CableNoDAO cableNoDAO = new CableNoDAO();
        cableNoDAO.setSession(this.getSession());

        List<CableNoForm> listCableNo = cableNoDAO.getListCableNo(cableNoForm);

        req.setAttribute(SESSION_LIST_CABLE_NO, listCableNo);

        req.setAttribute(SESSION_CABLE_BOX_ID, cableBoxId);

        CableBoxDAO cableBoxDAO = new CableBoxDAO();
        cableBoxDAO.setSession(this.getSession());
        CableBox cableBox = cableBoxDAO.findById(cableBoxId);
        if (cableBox != null) {
            req.setAttribute(SESSION_CABLE_BOX_CODE, cableBox.getCode());

            this.cableNoForm.setCableBoxId(cableBox.getBoardId());
            this.cableNoForm.setCableBoxCode(cableBox.getCode());
            this.cableNoForm.setCableBoxName(cableBox.getName());
        }

        return pageForward;
    }

    private List<Dslam> getListDslamByProvinceAndProduct(String province, String productId) {

        if (province == null) {
            province = "";
        }

        List params = new ArrayList<Object>();

        StringBuilder sSql = new StringBuilder();
        sSql.append(" FROM Dslam ");
        sSql.append(" WHERE 1=1 ");
        if (province != null && !province.trim().equals("")) {
            sSql.append("   AND province = ? ");
            params.add(province.trim());
        }

        if (productId != null && !productId.trim().equals("")) {
            sSql.append("   AND productId = ? ");
            params.add(Long.valueOf(productId.trim()));
        }

        sSql.append("   AND rownum <= ? ");
        sSql.append("  order by code  ");
        params.add(SEARCH_RESULT_LIMIT);

        Query queryObject = getSession().createQuery(sSql.toString());
        for (int i = 0; i < params.size(); i++) {
            queryObject.setParameter(i, params.get(i));
        }

        return queryObject.list();
    }

    //END
    public String preapageAddDslam() throws Exception {
        log.info("Bắt đầu hàm preapageAddDslam của EquipmentDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                DslamForm f = getDslamForm();
                if (f != null) {
                    f.resetForm();
                    req.getSession().setAttribute("toEditDslam", 0);
                }
                //lấy danh mục giấy nộp tiền
                pageForward = ADD_NEW_DSLAM;
                getListProvince();
                //getListDslam();
                req.getSession().setAttribute("toEditDslam", 0);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("Kết thúc hàm preapageAddDslam của EquipmentVendorDAO");
        return pageForward;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : them dslam moi
     *
     */
    public String addNewDslam() throws Exception {
        log.info("Begin addNewDslam of DslamDAO ...");

        HttpServletRequest req = getRequest();
        DslamForm f = getDslamForm();

        try {
            if (checkValidateToAdd()) {
                Dslam bo = new Dslam();
                f.copyDataToBO(bo);
                Long dslamId = getSequence("DSLAM_SEQ");
                bo.setDslamId(dslamId);
                getSession().save(bo);

                if (Constant.IS_VER_HAITI) {//1 dslam == 1 chassic
                    if (bo.getProductId().equals(Constant.PRODUCT_ID_DSLAM)) {
                        Chassic chassic = new Chassic();
                        chassic.setChassicId(getSequence("CHASSIC_SEQ"));
                        chassic.setDslamId(dslamId);
                        chassic.setCode(bo.getCode());
                        chassic.setIp(bo.getDslamIp());
                        chassic.setBrasId(bo.getBrasId());
                        chassic.setTotalPort(bo.getMaxPort());
                        chassic.setStatus(bo.getStatus());
                        getSession().save(chassic);
                    }
                }

//                DslamBean ds = new DslamBean();
//                f.copyDataToBean(ds);
//                ds.setDslamId(dslamId);
//
//                String queryString = " from Area ";
//                queryString += " where district IS NULL and precinct IS NULL ";
//                queryString += " and province = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, f.getProvince().trim());
//                List<Area> l = queryObject.list();
//                ds.setProvinceName(l.get(0).getName());

//                List<DslamBean> list = (List<DslamBean>) req.getSession().getAttribute("listDslam");
//                if (list == null) {
//                    list = new ArrayList<DslamBean>();
//                    req.getSession().setAttribute("listDslam", list);
//                }
//                list.add(0, ds);

                String province = f.getProvince();
                String productId = f.getProductId();
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);

                f.resetForm();
                f.setProvince(province);
                f.setProductId(productId);
                getSession().flush();
                this.searchDslam();
                req.setAttribute(REQUEST_MESSAGE, "dslam.add.success");
            }

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        log.info("End addNewDslam of DslamDAO");
        pageForward = ADD_NEW_DSLAM;
        return pageForward;
    }

    private List getListBras() {
        try {
            String queryString = "from Bras where status = ? order by code ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE);
            return queryObject.list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;

        }
    }

    private List getListProvince() {
        try {
            AreaDAO area = new AreaDAO();
            area.setSession(this.getSession());
            return area.findAllProvince();
        } catch (Exception e) {
            getSession().clear();
            log.debug("Lỗi khi lấy danh sách tỉnh/thành phố: " + e.toString());
            return null;
        }
    }

    /**
     *
     * author   :
     * date     :
     * desc     : tim kiem danh sach dslam
     * modified : tamdt1, 08/10/2010
     *
     */
    public String searchDslam() throws Exception {
        log.info("Begin searchDslam of DslamDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        try {
            //lay danh sach dslam
            String dslamCode = this.dslamForm.getCode();
            String dslamName = this.dslamForm.getName();
            String strProductId = this.dslamForm.getProductId();
            String province = this.dslamForm.getProvince();
            String dslamIp = this.dslamForm.getDslamIp();
            Long brasId = this.dslamForm.getBrasId();
            String strMaxPort = this.dslamForm.getMaxPort();
            String strUsedPort = this.dslamForm.getUsedPort();
            String strReservePort = this.dslamForm.getReservedPort();
            String strXCoordinate = this.dslamForm.getX();
            String strYCoordinate = this.dslamForm.getY();
            String strStatus = this.dslamForm.getStatus();
            String address = this.dslamForm.getAddress();

            Long productId = null;
            Long maxPort = null;
            Long usedPort = null;
            Long reservePort = null;
            Long xCoordinate = null;
            Long yCoordinate = null;
            Long status = null;

            if (strProductId != null && !strProductId.trim().equals("")) {
                try {
                    productId = Long.parseLong(strProductId.trim());
                } catch (NumberFormatException ex) {
                    productId = -1L;
                    //
                    ex.printStackTrace();
                    //
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception. Invalid data");
                    //
                    log.info("End searchDslam of DslamDAO");
                    pageForward = PAGE_FORWARD_LIST_DSLAM;
                    return pageForward;
                }
            }

            if (strMaxPort != null && !strMaxPort.trim().equals("")) {
                try {
                    maxPort = Long.parseLong(strMaxPort.trim());
                } catch (NumberFormatException ex) {
                    maxPort = -1L;
                    //
                    ex.printStackTrace();
                    //
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception. Invalid data");
                    //
                    log.info("End searchDslam of DslamDAO");
                    pageForward = PAGE_FORWARD_LIST_DSLAM;
                    return pageForward;
                }
            }

            if (strUsedPort != null && !strUsedPort.trim().equals("")) {
                try {
                    usedPort = Long.parseLong(strUsedPort.trim());
                } catch (NumberFormatException ex) {
                    usedPort = -1L;
                    //
                    ex.printStackTrace();
                    //
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception. Invalid data");
                    //
                    log.info("End searchDslam of DslamDAO");
                    pageForward = PAGE_FORWARD_LIST_DSLAM;
                    return pageForward;
                }
            }

            if (strReservePort != null && !strReservePort.trim().equals("")) {
                try {
                    reservePort = Long.parseLong(strReservePort.trim());
                } catch (NumberFormatException ex) {
                    reservePort = -1L;
                    //
                    ex.printStackTrace();
                    //
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception. Invalid data");
                    //
                    log.info("End searchDslam of DslamDAO");
                    pageForward = PAGE_FORWARD_LIST_DSLAM;
                    return pageForward;
                }
            }

            if (strXCoordinate != null && !strXCoordinate.trim().equals("")) {
                try {
                    xCoordinate = Long.parseLong(strXCoordinate.trim());
                } catch (NumberFormatException ex) {
                    usedPort = -1L;
                    //
                    ex.printStackTrace();
                    //
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception. Invalid data");
                    //
                    log.info("End searchDslam of DslamDAO");
                    pageForward = PAGE_FORWARD_LIST_DSLAM;
                    return pageForward;
                }
            }

            if (strYCoordinate != null && !strYCoordinate.trim().equals("")) {
                try {
                    yCoordinate = Long.parseLong(strYCoordinate.trim());
                } catch (NumberFormatException ex) {
                    usedPort = -1L;
                    //
                    ex.printStackTrace();
                    //
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception. Invalid data");
                    //
                    log.info("End searchDslam of DslamDAO");
                    pageForward = PAGE_FORWARD_LIST_DSLAM;
                    return pageForward;
                }
            }

            if (strStatus != null && !strStatus.trim().equals("")) {
                try {
                    status = Long.parseLong(strStatus.trim());
                } catch (NumberFormatException ex) {
                    usedPort = -1L;
                    //
                    ex.printStackTrace();
                    //
                    req.setAttribute(REQUEST_MESSAGE, "!!!Exception. Invalid data");
                    //
                    log.info("End searchDslam of DslamDAO");
                    pageForward = PAGE_FORWARD_LIST_DSLAM;
                    return pageForward;
                }
            }


            List parameterList = new ArrayList();
            StringBuilder queryString = new StringBuilder("");
            queryString.append("select  distinct new com.viettel.im.client.bean.DslamBean( ");
            queryString.append("        a.code, a.name, a.address, ");
//            queryString.append("        a.province, a.dslamIp, a.brasId, c.ip as brasIp, ");
            queryString.append("        a.province, a.dslamIp, a.brasId, (select c.ip from Bras c where c.brasId = a.brasId) as brasIp, ");
            queryString.append("        a.dslamId, a.status, a.productId, ");
            queryString.append("        a.maxPort, a.usedPort, a.reservedPort, ");
            queryString.append("        a.x, a.y, a.shopId, b.name as provinceName ");
            queryString.append("        ,a.mdfId ");
            queryString.append("        ,(select code from Mdf d where d.mdfId = a.mdfId) as mdfCode ");
            queryString.append("        ,(select name from Mdf d where d.mdfId = a.mdfId) as mdfName ");

            queryString.append("        ) ");
//            queryString.append("from    Dslam a, Area b , Bras c ");
            queryString.append("from    Dslam a, Area b ");
            queryString.append("where   1 = 1 ");
            queryString.append("        and b.district IS NULL ");
            queryString.append("        and b.precinct IS NULL ");
            queryString.append("        and a.province = b.province ");

            //Bo sung brasIp
//            queryString.append("        and a.brasId = c.brasId (+) ");


            if (dslamCode != null && !dslamCode.trim().equals("")) {
                queryString.append("    and upper(a.code) like ? ");
                parameterList.add("%" + dslamCode.trim().toUpperCase() + "%");
            }

            if (dslamName != null && !dslamName.trim().equals("")) {
                queryString.append("    and upper(a.name) like ? ");
                parameterList.add("%" + dslamName.trim().toUpperCase() + "%");
            }

            if (productId != null) {
                queryString.append("    and a.productId = ? ");
                parameterList.add(productId);
            }

            if (province != null && !province.trim().equals("")) {
                queryString.append("    and a.province = ? ");
                parameterList.add(province.trim());
            }

            if (dslamIp != null && !dslamIp.trim().equals("")) {
                queryString.append("    and a.dslamIp like ? ");
                parameterList.add("%" + dslamIp.trim() + "%");
            }

            if (brasId != null && !brasId.equals(0L)) {
                queryString.append("    and a.brasId = ? ");
                parameterList.add(brasId);
            }

            if (this.getDslamForm().getMdfCode() != null && !this.getDslamForm().getMdfCode().trim().equals("")) {
                queryString.append("    and a.mdfId in (select mdfId from Mdf where lower(code) = ? ) ");
                parameterList.add(this.getDslamForm().getMdfCode().trim().toLowerCase());
            }

            if (maxPort != null) {
                queryString.append("    and a.maxPort = ? ");
                parameterList.add(maxPort);
            }

            if (usedPort != null) {
                queryString.append("    and a.usedPort = ? ");
                parameterList.add(usedPort);
            }

            if (reservePort != null) {
                queryString.append("    and a.reservedPort = ? ");
                parameterList.add(reservePort);
            }

            if (xCoordinate != null) {
                queryString.append("    and a.x = ? ");
                parameterList.add(xCoordinate);
            }
            if (yCoordinate != null) {
                queryString.append("    and a.y = ? ");
                parameterList.add(yCoordinate);
            }

            if (status != null) {
                queryString.append("    and a.status = ? ");
                parameterList.add(status);
            }

            if (address != null && !address.trim().equals("")) {
                queryString.append("    and upper(a.address) like ? ");
                parameterList.add("%" + address.trim().toUpperCase() + "%");
            }

            queryString.append("        and rownum <= ? ");
            parameterList.add(SEARCH_RESULT_LIMIT);

            queryString.append("order by nlssort( a.code ,'nls_sort=Vietnamese') asc, nlssort(a.name,'nls_sort=Vietnamese') asc ");

            Query queryObject = getSession().createQuery(queryString.toString());
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            List listDslam = queryObject.list();
            listDslam = listDslam != null ? listDslam : new ArrayList(); //truong hop tra ve null

            //thiet lap len bien request
            req.setAttribute(REQUEST_LIST_DSLAM, listDslam);

            //
            if (listDslam.size() > SEARCH_RESULT_LIMIT) {
                req.setAttribute(REQUEST_MESSAGE, "common.message.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(SEARCH_RESULT_LIMIT);
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
            } else {
                req.setAttribute(REQUEST_MESSAGE, "dslam.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listDslam.size());
                req.setAttribute(REQUEST_MESSAGE_PARAM, listMessageParam);
            }

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            //

            ex.printStackTrace();
            //
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        log.info("End searchDslam of DslamDAO");
        pageForward = PAGE_FORWARD_LIST_DSLAM;
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 08/10/2010
     * desc     : lay du lieu cho combobox
     *
     */
    private void getDataForCombobox() {
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_LIST_PROVINCE, this.getListProvince());
        req.setAttribute(REQUEST_LIST_BRAS, this.getListBras());
    }

    private List<DslamBean> getLstDslam() {
        try {
            DslamForm f = getDslamForm();
            List parameterList = new ArrayList();
            String queryString = "select distinct new com.viettel.im.client.bean.DslamBean( a.code, a.name, " + " a.address, a.province, a.dslamIp, a.brasId, "
                    //                    + "c.ip as brasIp, "
                    + " (select c.ip from Bras c where c.brasId = a.brasId) as brasIp, "
                    + "a.dslamId, a.status, a.productId, a.maxPort, a.usedPort, a.reservedPort, a.x, a.y, a.shopId, b.name as provinceName)"
                    //                    + " from Dslam a, Area b, Bras c ";
                    + " from Dslam a, Area b ";

            queryString += "where 1=1 ";
            queryString += "and b.district IS NULL and b.precinct IS NULL ";
            queryString += "and a.province = b.province ";
//            queryString += "and a.brasIp = b.brasIp (+) ";

            if (f.getDslamId() != null && f.getDslamId().intValue() > 0) {
                queryString += " and dslamId = ? ";
                parameterList.add(f.getDslamId());
            }

            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                queryString += " and upper(a.code) like ? ";
                parameterList.add("%" + f.getCode().trim().toUpperCase() + "%");
            }

            if (f.getName() != null && !f.getName().trim().equals("")) {
                queryString += " and upper(a.name) like ? ";
                parameterList.add("%" + f.getName().trim().toUpperCase() + "%");
            }

            if (f.getBrasId() != null && !f.getBrasId().equals(0L)) {
                queryString += " and a.brasId = ? ";
                parameterList.add(f.getBrasId());
            }
            if (f.getProductId() != null && !f.getProductId().trim().equals("")) {
                queryString += " and a.productId = ? ";
                parameterList.add(Long.parseLong(f.getProductId().trim()));
            }
            if (f.getDslamIp() != null && !f.getDslamIp().trim().equals("")) {
                queryString += " and a.dslamIp like ? ";
                parameterList.add("%" + f.getDslamIp().trim() + "%");
            }
            if (f.getProvince() != null && !f.getProvince().trim().equals("")) {
                queryString += " and a.province = ? ";
                parameterList.add(f.getProvince());
            } else {
                return null;
            }

            if (f.getShopId() != null && !f.getShopId().trim().equals("")) {
                queryString += " and a.shopId = ? ";
                parameterList.add(Long.parseLong(f.getShopId()));
            }
            if (f.getMaxPort() != null && !f.getMaxPort().trim().equals("")) {
                queryString += " and a.maxPort = ? ";
                parameterList.add(Long.parseLong(f.getMaxPort().trim()));
            }
            if (f.getUsedPort() != null && !f.getUsedPort().trim().equals("")) {
                queryString += " and a.usePort = ? ";
                parameterList.add(Long.parseLong(f.getUsedPort().trim()));
            }
            if (f.getReservedPort() != null && !f.getReservedPort().trim().equals("")) {
                queryString += " and a.reservedPort = ? ";
                parameterList.add(Long.parseLong(f.getReservedPort().trim()));
            }
            if (f.getX() != null && !f.getX().trim().equals("")) {
                queryString += " and a.x = ? ";
                parameterList.add(Long.parseLong(f.getX().trim()));
            }
            if (f.getY() != null && !f.getY().trim().equals("")) {
                queryString += " and a.y = ? ";
                parameterList.add(Long.parseLong(f.getY().trim()));
            }
            if (f.getStatus() != null && !f.getStatus().trim().equals("")) {
                queryString += " and a.status = ? ";
                parameterList.add(Long.parseLong(f.getStatus().trim()));
            }


            if (f.getAddress() != null && !f.getAddress().trim().equals("")) {
                queryString += " and upper(a.address) like ? ";
                parameterList.add("%" + f.getAddress().trim().toUpperCase() + "%");
            }

            queryString += " and rownum < ? ";
            parameterList.add(SEARCH_RESULT_LIMIT);

            queryString += " order by nlssort( a.code ,'nls_sort=Vietnamese') asc, ";
            queryString += "nlssort(a.name,'nls_sort=Vietnamese') asc ";





            Query queryObject = getSession().createQuery(queryString);

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi khi them: " + ex.toString());
            return null;
        }
    }

    /**
     *
     * author   :
     * date     :
     * desc     : sua thong tin dslam
     *
     */
    public String editDslam() throws Exception {
        log.info("Begin editDslam of DslamDAO ...");

        HttpServletRequest req = getRequest();
        DslamForm f = getDslamForm();

        try {
            if (checkValidateToEdit()) {
                Dslam bo = new Dslam();
                f.copyDataToBO(bo);



                if (Constant.IS_VER_HAITI) {
                    if (bo.getProductId() != null && bo.getProductId().equals(Constant.PRODUCT_ID_DSLAM)) {

                        if (bo.getStatus() != null && bo.getStatus().equals(Constant.STATUS_DELETE)) {
                            //Check Chassic
                            StringBuilder strQuery = new StringBuilder("");
                            strQuery.append("select   count(*) ");
                            strQuery.append("from     Chassic ");
                            strQuery.append("where    1 = 1 and dslamId = ? and equipmentId != null and status = ? ");

                            Query q = getSession().createQuery(strQuery.toString());
                            q.setParameter(0, bo.getDslamId());
                            q.setParameter(1, Constant.STATUS_USE);
                            Long count = (Long) q.list().get(0);
                            if ((count != null) && (count.compareTo(0L) > 0)) {
                                //lay du lieu cho combobox
                                getDataForCombobox();

                                req.setAttribute(REQUEST_MESSAGE, "Error. Error. Chassic must be inactive first!");
                                String province = bo.getProvince();
                                String productId = bo.getProductId().toString();
                                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
                                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                                return ADD_NEW_DSLAM;
                            }
                        }


                        ChassicDAO chassicDAO = new ChassicDAO();
                        chassicDAO.setSession(this.getSession());
                        List<Chassic> listChassic = chassicDAO.findByDslamId(bo.getDslamId());
                        if (listChassic == null || listChassic.isEmpty()) {
                            //lay du lieu cho combobox
                            getDataForCombobox();
                            throw new Exception("Error. Not found chassic in dslam!");
                        }

                        for (int iChassic = 0; iChassic < listChassic.size(); iChassic++) {
                            Chassic chassic = listChassic.get(iChassic);
                            chassic.setCode(bo.getCode());
                            chassic.setIp(bo.getDslamIp());
                            chassic.setBrasId(bo.getBrasId());
                            chassic.setTotalPort(bo.getMaxPort());
                            chassic.setStatus(bo.getStatus());
                            getSession().update(chassic);
                        }
                    }
                }

                getSession().update(bo);


                String province = f.getProvince();
                String productId = f.getProductId();
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);

                f.resetForm();
                //
                f.setProvince(province);
                f.setProductId(productId);

                getSession().flush();
                this.searchDslam();

                req.setAttribute(REQUEST_MESSAGE, "dslam.edit.success");
            }

            //lay du lieu cho combobox
            getDataForCombobox();

        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();

            ex.printStackTrace();

            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        log.info("End editDslam of DslamDAO");
        pageForward = ADD_NEW_DSLAM;
        return pageForward;

    }

    public String prepareEditDslam() throws Exception {
        HttpServletRequest req = getRequest();
        DslamForm f = getDslamForm();
        Long dslamId;
        try {
            log.info("Bắt đầu hàm prepareEditDslam của DslamDAO ...");
            //req.setAttribute("listDslam", getListDslam());
            String strDslamId = (String) QueryCryptUtils.getParameter(req, "dslamId");
            dslamId = Long.parseLong(strDslamId);

            Dslam bo = findById(dslamId);

            f.copyDataFromBO(bo);

            if (bo.getMdfId() != null && !bo.getMdfId().equals(0L)) {
                Mdf mdf = this.findByMdfId(bo.getMdfId());
                if (mdf != null || mdf.getMdfId() != null) {
                    f.setMdfCode(mdf.getCode());
                    f.setMdfName(mdf.getName());
                }
            }

            req.getSession().setAttribute("toEditDslam", 1);
//            req.setAttribute("listProvince", getListProvince());
//            req.setAttribute("listTechShop", getListShopTechAtPageLoad());
            //NamNX- end
            log.info("Kết thúc hàm prepare prepareEditDslam của DslamDAO");
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Lỗi khi thực hiện hàm prepareEditDslam" + ex.toString());
            dslamId = -1L;
        }
        getDataForCombobox();
        return ADD_NEW_DSLAM;
    }

    /**
     *
     * author   :
     * date     :
     * desc     : xoa thong tin dslam
     *
     */
    public String actionDeleteDslam() throws Exception {
        log.info("Begin actionDeleteDslam of DslamDAO ...");

        HttpServletRequest req = getRequest();
        DslamForm f = getDslamForm();

        try {
            String strDslamId = (String) QueryCryptUtils.getParameter(req, "dslamId");
            Long dslamId = Long.parseLong(strDslamId);
            Dslam bo = findById(dslamId);

            //kiem tra xem co Cap tong nao con dang duoc su dung hay khong
            StringBuilder strQuery = new StringBuilder("");
            strQuery.append("select   count(*) ");
            strQuery.append("from     Boards ");
            strQuery.append("where    1 = 1 and dslamId = ? ");

            Query q = getSession().createQuery(strQuery.toString());
            q.setParameter(0, dslamId);
            Long count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();

                String province = bo.getProvince();
                String productId = bo.getProductId().toString();
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                f.resetForm();
                f.setProvince(province);
                f.setProductId(productId);
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                this.searchDslam();
                getDataForCombobox();
                req.setAttribute(REQUEST_MESSAGE, "dslam.delete.error.hasBoardInDslam");
                return ADD_NEW_DSLAM;
            }

            //kiem tra xem co Cap nhanh nao con dang duoc su dung hay khong
            strQuery = new StringBuilder("");
            strQuery.append("select   count(*) ");
            strQuery.append("from     CableBox ");
            strQuery.append("where    1 = 1 and dslamId = ? ");

            q = getSession().createQuery(strQuery.toString());
            q.setParameter(0, dslamId);
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                String province = bo.getProvince();
                String productId = bo.getProductId().toString();
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                f.resetForm();
                f.setProvince(province);
                f.setProductId(productId);
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                this.searchDslam();
                getDataForCombobox();
                req.setAttribute(REQUEST_MESSAGE, "dslam.delete.error.hasCableBoxInDslam");
                return ADD_NEW_DSLAM;
            }




            /**
             * Modified by :        TrongLV
             * Modify date :        2011-06-27
             * Purpose :            
             */
            //kiem tra xem co SHOP_DSLAM nao con dang hieu luc hay khong
            strQuery = new StringBuilder("");
            strQuery.append("select   count(*) ");
            strQuery.append("from     ShopDslam ");
            strQuery.append("where    1 = 1 and dslamId = ? and status = ? ");

            q = getSession().createQuery(strQuery.toString());
            q.setParameter(0, dslamId);
            q.setParameter(1, Constant.STATUS_USE);

            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                String province = bo.getProvince();
                String productId = bo.getProductId().toString();
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                f.resetForm();
                f.setProvince(province);
                f.setProductId(productId);
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                this.searchDslam();
                getDataForCombobox();
                req.setAttribute(REQUEST_MESSAGE, "E.100010");
                return ADD_NEW_DSLAM;
            }


            //Check Chassic
            //Neu ton tai chassic da update thong tin && trang thai hoat dong
            if (bo.getProductId().equals(Constant.PRODUCT_ID_DSLAM)) {
                strQuery = new StringBuilder("");
                strQuery.append("select   count(*) ");
                strQuery.append("from     Chassic ");
                strQuery.append("where    1 = 1 and dslamId = ? and (equipmentId != null and status = ?) ");

                q = getSession().createQuery(strQuery.toString());
                q.setParameter(0, dslamId);
                q.setParameter(1, Constant.STATUS_USE);

                count = (Long) q.list().get(0);
                if ((count != null) && (count.compareTo(0L) > 0)) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    String province = bo.getProvince();
                    String productId = bo.getProductId().toString();
//                    List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                    req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                    f.resetForm();
                    f.setProvince(province);
                    f.setProductId(productId);
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                    this.searchDslam();
                    getDataForCombobox();
                    req.setAttribute(REQUEST_MESSAGE, "Error. Chassic on DSLAM must be deleted first ");
                    return ADD_NEW_DSLAM;
                }
            }
            
            //Xoa SHOP_DSLAM
            strQuery = new StringBuilder("");
            strQuery.append("delete ");
            strQuery.append("from     ShopDslam ");
            strQuery.append("where    1 = 1 and dslamId = ? ");
            q = getSession().createQuery(strQuery.toString());
            q.setParameter(0, dslamId);
            q.executeUpdate();
            

            //Delete Chassic
            HashMap<String, Object> result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);
            int qtyChassic = -1;
            ChassicDAO chassicDAO = new ChassicDAO();
            result = chassicDAO.deleteChassicByDslamId(getSession(), dslamId);
            if (result.get(PortDAO.HASH_MAP_RESULT) != null) {
                qtyChassic = Integer.valueOf(result.get(PortDAO.HASH_MAP_RESULT).toString());
            }
            if (qtyChassic >= 0) {
                getSession().delete(bo);
                String province = bo.getProvince();
                String productId = bo.getProductId().toString();
                f.resetForm();
                f.setProvince(province);
                f.setProductId(productId);
                getSession().flush();
                this.searchDslam();
                req.setAttribute(REQUEST_MESSAGE, "dslam.delete.success");
                getDataForCombobox();

            } else {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().beginTransaction();
                String province = bo.getProvince();
                String productId = bo.getProductId().toString();
                f.resetForm();
                f.setProvince(province);
                f.setProductId(productId);
//                List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//                req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
                this.searchDslam();
                getDataForCombobox();
                req.setAttribute(REQUEST_MESSAGE, "Error. Delete Chassic fail!");
                pageForward = ADD_NEW_DSLAM;
                return pageForward;
            }

            


//            List<Dslam> listDslam = this.getListDslamByProvinceAndProduct(province, productId);
//            req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
            //

        } catch (Exception ex) {
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        log.info("End actionDeleteDslam of DslamDAO");
        pageForward = ADD_NEW_DSLAM;
        return pageForward;
    }

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        DslamForm f = getDslamForm();
        String code = f.getCode().trim();
        String x = f.getX().trim();
        String y = f.getY().trim();
        String name = f.getName().trim();
        String address = f.getAddress().trim();
        String maxPort = f.getMaxPort().trim();
        String status = f.getStatus().trim();
        String reservedPort = f.getReservedPort().trim();
        String usedPort = f.getUsedPort().trim();
        if ((code == null) || code.equals("")) {
            req.setAttribute("returnMsg", "dslam.error.invalidCode");
            return false;
        }
        if ((name == null) || name.equals("")) {
            req.setAttribute("returnMsg", "dslam.error.invalidName");
            return false;
        }
//        if ((address == null) || address.equals("")) {
//            req.setAttribute("returnMsg", "dslam.error.invalidAddress");
//            return false;
//        }
        if ((status == null) || status.equals("")) {
            req.setAttribute("returnMsg", "dslam.error.invalidStatus");
            return false;
        }
        if ((maxPort != null) && !maxPort.equals("")) {

            try {

                if (Integer.parseInt(maxPort) < 0) {
                    req.setAttribute("returnMsg", "dslam.error.invalidMaxPort2");
                    return false;
                }

            } catch (NumberFormatException e) {
                req.setAttribute("returnMsg", "dslam.error.invalidMaxPort");
                return false;
            }
        }
        if ((reservedPort != null) && !reservedPort.equals("")) {

            try {

                if (Integer.parseInt(reservedPort) < 0) {
                    req.setAttribute("returnMsg", "dslam.error.invalidReservedPort2");
                    return false;
                }
            } catch (NumberFormatException e) {
                req.setAttribute("returnMsg", "dslam.error.invalidReservedPort");
                return false;
            }
        }
        if ((usedPort != null) && !usedPort.equals("")) {

            try {

                if (Integer.parseInt(usedPort) < 0) {
                    req.setAttribute("returnMsg", "dslam.error.invalidUsedPort2");
                    return false;
                }

            } catch (NumberFormatException e) {
                req.setAttribute("returnMsg", "dslam.error.invalidUsedPort");
                return false;
            }
        }
        if ((x != null) && !x.equals("")) {

            try {

                if (Long.parseLong(x) < 0) {
                    req.setAttribute("returnMsg", "dslam.error.invalidX2");
                    return false;
                }

            } catch (NumberFormatException e) {
                req.setAttribute("returnMsg", "dslam.error.invalidX");
                return false;
            }
        }
        if ((y != null) && !y.equals("")) {

            try {

                if (Long.parseLong(y) < 0) {
                    req.setAttribute("returnMsg", "dslam.error.invalidY2");
                    return false;
                }

            } catch (NumberFormatException e) {
                req.setAttribute("returnMsg", "dslam.error.invalidY");
                return false;
            }
        }
        if ((usedPort == null) || (usedPort.equals(""))) {
            usedPort = "0";

        }
        if ((reservedPort == null) || (reservedPort.equals(""))) {
            reservedPort = "0";
        }
        if ((usedPort != null) && !usedPort.equals("") && (reservedPort != null) && !reservedPort.equals("") && (maxPort != null) && !maxPort.equals("") && ((Integer.parseInt(usedPort) + Integer.parseInt(reservedPort) - Integer.parseInt(maxPort)) > 0)) {
            req.setAttribute("returnMsg", "dslam.error.invalid");
            return false;
        }


        if (f.getMdfCode() != null && !f.getMdfCode().trim().equals("")) {
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("from Mdf a where 1=1 and status = ? ");
            listParameter1.add(Constant.STATUS_USE);
            if (f.getProvince() != null) {
                strQuery1.append(" and lower(a.province) = ? ");
                listParameter1.add(f.getProvince().trim().toLowerCase());
            }
            if (f.getMdfCode() != null) {
                strQuery1.append(" and lower(a.code) = ? ");
                listParameter1.add(f.getMdfCode().trim().toLowerCase());
            }

            Query query1 = getSession().createQuery(strQuery1.toString());
            for (int i = 0; i < listParameter1.size(); i++) {
                query1.setParameter(i, listParameter1.get(i));
            }

            List<Mdf> tmpList1 = query1.list();
            if (tmpList1 == null || tmpList1.isEmpty() || tmpList1.size() > 1) {
                req.setAttribute("returnMsg", "Error. Mdf is invalid!");
                return false;
            }
            f.setMdfId(tmpList1.get(0).getMdfId());
        }

        return true;

    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();
        Long count;
        DslamForm f = getDslamForm();
        String code = f.getCode();

        if (checkForm()) {
            String strQuery = "select count(*) from Dslam as model where upper(model.code)=?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.toUpperCase());
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute(REQUEST_MESSAGE, "dslam.add.duplicateCode");
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
        DslamForm f = getDslamForm();
        String code = f.getCode();
        Long id = f.getDslamId();
        if (checkForm()) {

            String strQuery = "select count(*) from Dslam as model where upper(model.code) = ? and not model.dslamId = ?";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.toUpperCase());
            q.setParameter(1, id);
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "dslam.edit.duplicateCode");
                return false;
            }
        } else {
            return false;
        }

        if (!this.dslamForm.getStatus().equals(Constant.STATUS_USE.toString())) {
            String strQuery = "select count(*) from Boards where status = ? and dslamId = ? ";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, Constant.STATUS_USE);
            q.setParameter(1, id);
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "dslam.edit.error.hasActiveBoardInDslam");
                return false;
            }
        }

        return true;
    }

    /**
     * @Author              :   TrongLV
     * @CreateDate          :   14/05/2010
     * @Purpose             :
     * @param propertyName  :
     * @param value         :
     * @return              :
     */
    public List findByProperty(String[] propertyName, Object[] value) {
        log.debug("finding Dslam instance with property: " + propertyName + ", value: " + value);
        try {
            if (propertyName.length != value.length) {
                return null;
            }
            List lstParam = new ArrayList();
            String queryString = "from Dslam as model where 1=1 ";
            for (int i = 0; i < propertyName.length; i++) {
                if (propertyName[i] == null || propertyName[i].trim().equals("")) {
                    continue;
                }
                queryString += " and upper(model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName[i].trim()) + ") = upper(?) ";
                lstParam.add(value[i]);
            }
            queryString += " order by province, productId, code ";
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

    /**
     * ----------------------------------------------------------------------- *
     * DSLAM_CABLE
     */
    public String preparePageDslamCableOverview() throws Exception {
        pageForward = PAGE_FORWARD_DSLAM_CABLE_OVERVIEW;
        HttpServletRequest req = getRequest();
        req.setAttribute(REQUEST_LIST_PROVINCE, this.getListProvince());
        return pageForward;
    }

    public String getListDslamCableTree() throws Exception {
        try {
            HttpServletRequest req = getRequest();

            String node = QueryCryptUtils.getParameter(req, "nodeId");
            if (node == null) {
                return PAGE_FORWARD_SUCCESS;
            }

            node = node.trim();

            if (node.indexOf(TREE_NODE_PROVINCE) == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac province
                AreaDAO areaDAO = new AreaDAO();
                areaDAO.setSession(this.getSession());
                List<Area> listProvince = areaDAO.findAllProvince();

                Iterator iterProvince = listProvince.iterator();
                while (iterProvince.hasNext()) {
                    Area area = (Area) iterProvince.next();

                    String nodeId = TREE_NODE_ROOT + area.getProvince() + ";" + Constant.PRODUCT_ID_DSLAM.toString();
                    String doString = req.getContextPath() + "/dslamAction!getListDslam.do?province=" + area.getProvince() + "&productId=" + Constant.PRODUCT_ID_DSLAM.toString();
                    getListItems().add(new Node(area.getAreaCode() + " - " + area.getName() + " - DSLAM", "true", nodeId, doString));

                    nodeId = TREE_NODE_ROOT + area.getProvince() + ";" + Constant.PRODUCT_ID_DLU.toString();
                    doString = req.getContextPath() + "/dslamAction!getListDslam.do?province=" + area.getProvince() + "&productId=" + Constant.PRODUCT_ID_DLU.toString();
                    getListItems().add(new Node(area.getAreaCode() + " - " + area.getName() + " - DLU", "true", nodeId, doString));
                }
            } else if (node.indexOf(TREE_NODE_ROOT) == 0) {
                node = node.substring(2);
                List<Dslam> listDslam = getListDslamByProvinceAndProduct(node.split(";")[0], node.split(";")[1]);
                Iterator iterDslam = listDslam.iterator();
                while (iterDslam.hasNext()) {
                    Dslam dslam = (Dslam) iterDslam.next();
                    String nodeId = TREE_NODE_DSLAM + dslam.getDslamId();
                    String doString = req.getContextPath() + "/dslamAction!getListCableBoxByDslamId.do?dslamId=" + dslam.getDslamId();
                    getListItems().add(new Node(dslam.getCode() + " - " + dslam.getName(), "true", nodeId, doString));
                }
            }

            return PAGE_FORWARD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getListCableBoxByDslamId() throws Exception {
        pageForward = PAGE_FORWARD_LIST_CABLE_BOX_OF_DSLAM;
        HttpServletRequest req = getRequest();

        String strDslamId = req.getParameter("dslamId");
        Long dslamId = -1L;
        if (strDslamId != null && !strDslamId.trim().equals("")) {
            dslamId = Long.valueOf(strDslamId);
        }

        CableBoxDAO cableBoxDAO = new CableBoxDAO();
        cableBoxDAO.setSession(this.getSession());

        String queryString = "from CableBox where 1=1 and boardsId = ? and rownum <= ? order by boardId, code ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, dslamId.toString());
        queryObject.setParameter(1, SEARCH_RESULT_LIMIT);
        List<CableBox> listCableBox = queryObject.list();

        req.setAttribute(SESSION_LIST_CABLE_BOX, listCableBox);

        req.setAttribute(SESSION_DSLAM_ID, dslamId);

        Dslam dslam = this.findById(dslamId);
        if (dslam != null) {
            req.setAttribute(SESSION_DSLAM_CODE, dslam.getCode());
        }

        return pageForward;
    }

    public String searchDslamCable() throws Exception {
        log.info("Bắt đầu hàm searchDslamCable của DslamDAO ...");

        HttpServletRequest req = getRequest();
        pageForward = PAGE_FORWARD_LIST_DSLAM_CABLE;

        try {

            getDataForCombobox();

            DslamForm f = getDslamForm();
            if (f.getProvince() == null || f.getProvince().trim().equals("")) {
                req.setAttribute("returnMsg", "Bạn phải chọn một tỉnh!");
                return pageForward;
            }

            List listDslam = new ArrayList();
            listDslam = getLstDslam();
            req.getSession().setAttribute("toEditDslam", 2);
            req.setAttribute(REQUEST_LIST_DSLAM, listDslam);
            if (listDslam.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "dslam.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listDslam.size());
                req.setAttribute("returnMsgParam", listMessageParam);
            }
            log.info("Kết thúc hàm searchDslamCable của DslamDAO");
        } catch (Exception ex) {
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
        }

        return pageForward;
    }

    public String pageNavigatorDslamCable() throws Exception {
        log.info("Begin method pageNavigatorDslamCable of DslamDAO");

        HttpServletRequest req = getRequest();
        pageForward = "pageNavigatorDslamCable";
        req.setAttribute(REQUEST_LIST_DSLAM, getLstDslam());

        log.info("End method pageNavigatorDslamCable of DslamDAO");
        return pageForward;
    }

    public Long getListMdfSize(ImSearchBean imSearchBean) {

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) from Mdf a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and lower(a.province) = ? ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            return 0L;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            return tmpList1.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getMdfName(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from Mdf a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and lower(a.province) = ? ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public List<ImSearchBean> getListMdf(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        strQuery1.append("from Mdf a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and lower(a.province) = ? ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.code) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.code, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

        /**
     * @author haint41
     * @date 06/10/2011
     * @param Code
     * @return dslam
     */
    public Dslam getDslamByCode(String code) {
        try {
            String strQuery = "from Dslam where lower(code) = ?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.toLowerCase());
            Dslam dslam = (Dslam) q.list().get(0);
            if (dslam != null) {
                return dslam;
            }
            return null;
        } catch (Exception e) {
            log.error("Loi khi goi ham getDslamByCode : " + e);
            return null;
        }
    }
}
