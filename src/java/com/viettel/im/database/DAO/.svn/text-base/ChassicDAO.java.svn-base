package com.viettel.im.database.DAO;

/**
 *
 *  @author NamNX
 */
import com.viettel.im.database.DAO.CommonDAO;
import java.util.ArrayList;


import com.viettel.im.client.form.ChassicForm;
import com.viettel.im.database.BO.Chassic;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.Dslam;
import com.viettel.im.database.BO.Switches;

import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Card;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Slot;
import com.viettel.im.database.BO.UserToken;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * Chassic entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.viettel.bccs.im.database.BO.Chassic
 *
 */
public class ChassicDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ChassicDAO.class);
    // property constants
    private String pageForward;
    public static final int SEARCH_RESULT_LIMIT = 1000;
    public static final String DSLAM_ID = "dslamId";
    public static final String EQUIPMENT_ID = "equipmentId";
    public static final String CODE = "code";
    public static final String HOST_NAME = "hostName";
    public static final String IP = "ip";
    public static final String VLAN_START = "vlanStart";
    public static final String VLAN_STOP = "vlanStop";
    public static final String NMS_VLAN = "nmsVlan";
    public static final String SWITCH_ID = "switchId";
    public static final String CHANNEL_NUMBER = "channelNumber";
    public static final String TOTAL_SLOT = "totalSlot";
    public static final String TOTAL_PORT = "totalPort";
    public static final String USED_PORT = "usedPort";
    public static final String INVALID_PORT = "invalidPort";
    public static final String ADD_CHASSIC = "addChassic";
    public static final String STATUS = "status";
    public static final String CHASSIC_TYPE = "chassicType";
    public static final String SUB_SLOT = "subSlot";
    public static final String PORT_BRAS = "portBras";
    public static final String CHASSIC = "chassic";
    public static final String REQUEST_LIST_PROVINCE = "listProvince";
    public static final String LIST_PROVINCE = "listProvince";
    public static final String REQUEST_LIST_DSLAM = "listDslam";
    public static final String LIST_DSLAM = "listDslam";
    public static final String DSLAM_RESULT = "dslamResult";
    public static final String SLOT_RESULT = "slotResult";
    public static final String CHASSIC_OVERVIEW = "chassicOverview";
    public static final String TABLE_CHASSIC = "Chassic";
    private Map listElement = new HashMap();

    public Map getListElement() {
        return listElement;
    }

    public void setListElement(Map listElement) {
        this.listElement = listElement;
    }
    private List listItems = new ArrayList();
    private List listSwitchBox = new ArrayList();

    public List getListSwitchBox() {
        return listSwitchBox;
    }

    public void setListSwitchBox(List listSwitchBox) {
        this.listSwitchBox = listSwitchBox;
    }

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }
    private ChassicForm chassicForm = new ChassicForm();
    Map lstDslamCode = new HashMap();
    Map lstSwitchCode = new HashMap();

    public Map getLstDslamCode() {
        return lstDslamCode;
    }

    public void setLstDslamCode(Map lstDslamCode) {
        this.lstDslamCode = lstDslamCode;
    }

    public Map getLstSwitchCode() {
        return lstSwitchCode;
    }

    public void setLstSwitchCode(Map lstSwitchCode) {
        this.lstSwitchCode = lstSwitchCode;
    }

    public ChassicForm getChassicForm() {
        return chassicForm;
    }

    public void setChassicForm(ChassicForm chassicForm) {
        this.chassicForm = chassicForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of ChassicDAO ...");

        HttpServletRequest req = getRequest();

        ChassicForm f = this.chassicForm;

        f.resetForm();

        List listEquipment = new ArrayList();
        listEquipment = findAllEquipment();
        req.getSession().setAttribute("listEquipment", listEquipment);

        List listSwitch = findAllSwitch();
        req.getSession().setAttribute("listSwitch", listSwitch);

        List listChassic = new ArrayList();
        listChassic = findAll();
        req.getSession().removeAttribute("listChassic");
        req.getSession().setAttribute("listChassic", listChassic);
        req.getSession().setAttribute("ToEditChassic", 0);

        //lay danh sach cac province
        Session hbnSession = this.getSession();
        List<Area> listProvince = checkRole(hbnSession, req);
        req.setAttribute("listProvince", listProvince);

        pageForward = CHASSIC_OVERVIEW;

        log.info("End method preparePage of ChassicDAO");

        return pageForward;
    }

    public String displayChassic() throws Exception {
        log.info("Begin method preparePage of ChassicDAO ...");

        HttpServletRequest req = getRequest();

        ChassicForm f = this.chassicForm;
        String strselectedDslamId = req.getParameter("selectedDslamId");

        Long dslamId;

        if (strselectedDslamId != null) {
            try {
                dslamId = new Long(strselectedDslamId);
            } catch (NumberFormatException ex) {
                dslamId = -1L;
            }
        } else {
            dslamId = f.getDslamId();
        }

        f.resetForm();
        f.setDslamId(dslamId);
        List listChassic = new ArrayList();
        if (!Constant.IS_VER_HAITI) {
            listChassic = findAllByDslamId(dslamId);
        } else {
            f.setDslamId(-1L);

            String strselectedProvince = req.getParameter("selectedProvince");
            if (strselectedProvince == null) {
                strselectedProvince = "NULL";
            } else {
                strselectedProvince = strselectedProvince.trim().toUpperCase();
            }
            listChassic = findAllByProvince(strselectedProvince);
            if (listChassic != null && !listChassic.isEmpty()) {
                Chassic chassic = (Chassic) listChassic.get(0);
                if (chassic != null) {
                    f.setDslamId(chassic.getDslamId());
                }
            }
        }
        req.setAttribute("listChassic", listChassic);

        List listEquipment = new ArrayList();
        listEquipment = findAllEquipment();
        req.setAttribute("listEquipment", listEquipment);

        List listSwitch = findAllSwitch();
        req.setAttribute("listSwitch", listSwitch);


        DslamDAO dslamDAO = new DslamDAO();
        dslamDAO.setSession(this.getSession());
        List<Dslam> listDslam = dslamDAO.findAll("where status = " + Constant.STATUS_USE.toString(), " order by code ");
        req.getSession().setAttribute("listDslam", listDslam);

        req.getSession().setAttribute("toEditChassic", 0);
//        req.setAttribute("chassicMode", "view");
        pageForward = CHASSIC;

        log.info("End method preparePage of ChassicDAO");

        return pageForward;
    }

    public String prepareEditChassic() throws Exception {
        log.info("Begin method prepareEditChassic of ChassicDAO ...");

        HttpServletRequest req = getRequest();

        ChassicForm f = this.chassicForm;

        String strChassicId = (String) QueryCryptUtils.getParameter(req, "chassicId");
//        String strDslamName = (String) QueryCryptUtils.getParameter(req, "dslamName");
//        req.setAttribute("dslamName", strDslamName);
        Long chassicId;
        try {
            chassicId = Long.parseLong(strChassicId);
        } catch (Exception ex) {
            chassicId = -1L;
        }

        Chassic bo = findById(chassicId);

        f.CopyDataFromBO(bo);
        //Lay thong tin nmsVlan tu bang switches
        if (f.getSwitchId() != null) {
            SwitchDAO switchDAO = new SwitchDAO();
            switchDAO.setSession(this.getSession());
            Switches switches = switchDAO.findById(f.getSwitchId());
            if (switches != null && switches.getSwitchId() != null && switches.getNmsVlan() != null) {
                f.setNmsVlan(switches.getNmsVlan());
            }
        }

//        req.getSession().removeAttribute("ToEditChassic");
        req.getSession().setAttribute("toEditChassic", 1);
        req.getSession().removeAttribute("chassicId");
        req.getSession().setAttribute("chassicId", chassicId);
//        req.setAttribute("chassicMode", "prepareAddOrEdit");
        Long dslamId = bo.getDslamId();
        List listChassic = new ArrayList();

        if (!Constant.IS_VER_HAITI) {
            listChassic = findAllByDslamId(dslamId);
        } else {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(dslamId);
            if (dslam != null && dslam.getDslamId() != null && dslam.getProvince() != null) {
                listChassic = findAllByProvince(dslam.getProvince());
            }
        }

        req.setAttribute("listChassic", listChassic);

        pageForward = CHASSIC;

        log.info("End method prepareEditChassic of ChassicDAO");

        return pageForward;
    }

    //Sao chep Chassic
    public String copyChassic() throws Exception {
        log.info("Begin method copyChassic of ChassicDAO ...");

        HttpServletRequest req = getRequest();

        ChassicForm f = this.chassicForm;

        String strChassicId = (String) QueryCryptUtils.getParameter(req, "chassicId");

        Long chassicId;
        try {
            chassicId = Long.parseLong(strChassicId);
        } catch (Exception ex) {
            chassicId = -1L;
        }
        Chassic bo = findById(chassicId);
        f.CopyDataFromBO(bo);
        f.setChassicId(0L);
        req.getSession().setAttribute("toEditChassic", 1);
        req.getSession().removeAttribute("chassicId");
        req.getSession().setAttribute("chassicId", chassicId);

        Long dslamId = bo.getDslamId();
        List listChassic = new ArrayList();
        listChassic = findAllByDslamId(dslamId);
        req.setAttribute("listChassic", listChassic);

        pageForward = CHASSIC;

        log.info("End method copyChassic of ChassicDAO");

        return pageForward;
    }

    public String saveChassic() throws Exception {
        log.info("Begin method saveChassic of ChassicDAO ...");
        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        pageForward = CHASSIC;

        ChassicForm f = this.chassicForm;
        Long dslamId = f.getDslamId();

        //Neu la them moi chassic (khong xay ra voi version HAITI - vi khi tao dslam da tu dong tao chassic roi)
        if (f.getChassicId() == null || f.getChassicId().equals(0L)) {
            if (checkValidateToAdd()) {
                Chassic bo = new Chassic();
                f.CopyDataToBO(bo);
                bo.setChassicId(getSequence("CHASSIC_SEQ"));
                getSession().save(bo);
                getSession().flush();
                req.setAttribute("returnMsg", "chassic.add.success");

            } else {//validate input data failure
                req.getSession().setAttribute("toEditChassic", 1);
                List listChassic = new ArrayList();
                req.getSession().setAttribute("listChassic", listChassic);
                if (!Constant.IS_VER_HAITI) {
                    listChassic = findAllByDslamId(dslamId);
                    req.getSession().setAttribute("listChassic", listChassic);
                } else {
                    DslamDAO dslamDAO = new DslamDAO();
                    dslamDAO.setSession(this.getSession());
                    Dslam dslam = dslamDAO.findById(dslamId);
                    if (dslam != null && dslam.getDslamId() != null && dslam.getProvince() != null) {
                        listChassic = findAllByProvince(dslam.getProvince());
                        req.getSession().setAttribute("listChassic", listChassic);
                    }
                }
                log.info("End method saveChassic of ChassicDAO");
                return pageForward;
            }
        } else {//neu la sua thong tin chassic
            if (checkValidateToEdit()) {
                //TrongLV
                //KIEM TRA DIEU KIEN CAP NHAT TRANG THAI VE KHONG HIEU LUC
                //NEU CON SLOT CO HIEU LUC THI KHONG DUOC PHEP
                if (f.getStatus() != null && f.getStatus().intValue() != Constant.STATUS_USE.intValue()) {
                    SlotDAO slotDAO = new SlotDAO();
                    slotDAO.setSession(mySession);
                    String[] propertyName = {"chassicId", "status"};
                    Object[] value = {f.getChassicId(), Constant.STATUS_USE};
                    List<Slot> listSlot = slotDAO.findByProperty(propertyName, value);
                    if (listSlot != null && listSlot.size() > 0) {
                        req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.186");
                        List listChassic = new ArrayList();
                        req.getSession().setAttribute("listChassic", listChassic);
                        //Lay lai danh sach chassic thuoc node dang chon (dslam dang chon / tinh dang chon)
                        if (!Constant.IS_VER_HAITI) {
                            listChassic = findAllByDslamId(dslamId);
                            req.getSession().setAttribute("listChassic", listChassic);
                        } else {
                            DslamDAO dslamDAO = new DslamDAO();
                            dslamDAO.setSession(this.getSession());
                            Dslam dslam = dslamDAO.findById(dslamId);
                            if (dslam != null && dslam.getDslamId() != null && dslam.getProvince() != null) {
                                listChassic = findAllByProvince(dslam.getProvince());
                                req.getSession().setAttribute("listChassic", listChassic);
                            }
                        }
                        log.info("End method saveChassic of ChassicDAO");
                        return pageForward;
                    }
                }

                Chassic bo = new Chassic();
                bo = findById(f.getChassicId());
                //check exist in db
                if (bo == null || bo.getChassicId() == null) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "Error. Chassic is not exist!");
                    List listChassic = new ArrayList();
                    req.getSession().setAttribute("listChassic", listChassic);
                    //Lay lai danh sach chassic thuoc node dang chon (dslam dang chon / tinh dang chon)
                    if (!Constant.IS_VER_HAITI) {
                        listChassic = findAllByDslamId(dslamId);
                        req.getSession().setAttribute("listChassic", listChassic);
                    } else {
                        DslamDAO dslamDAO = new DslamDAO();
                        dslamDAO.setSession(this.getSession());
                        Dslam dslam = dslamDAO.findById(dslamId);
                        if (dslam != null && dslam.getDslamId() != null && dslam.getProvince() != null) {
                            listChassic = findAllByProvince(dslam.getProvince());
                            req.getSession().setAttribute("listChassic", listChassic);
                        }
                    }
                    log.info("End method saveChassic of ChassicDAO");
                    return pageForward;
                }
                //copy vao BO
                f.CopyDataToBO(bo);

                getSession().update(bo);
                getSession().flush();

                req.setAttribute("returnMsg", "chassic.edit.success");

            } else {//validate input data failure
                req.getSession().setAttribute("toEditChassic", 1);
                List listChassic = new ArrayList();
                req.getSession().setAttribute("listChassic", listChassic);
                if (!Constant.IS_VER_HAITI) {
                    listChassic = findAllByDslamId(dslamId);
                    req.getSession().setAttribute("listChassic", listChassic);
                } else {
                    DslamDAO dslamDAO = new DslamDAO();
                    dslamDAO.setSession(this.getSession());
                    Dslam dslam = dslamDAO.findById(dslamId);
                    if (dslam != null && dslam.getDslamId() != null && dslam.getProvince() != null) {
                        listChassic = findAllByProvince(dslam.getProvince());
                        req.getSession().setAttribute("listChassic", listChassic);
                    }
                }
                log.info("End method saveChassic of ChassicDAO");
                return pageForward;
            }
        }


        req.getSession().setAttribute("toEditChassic", 0);
        f.resetForm();
        f.setDslamId(dslamId);


        //Truong hop add / edit success or validate failure ==> lay lai danh sach chassic theo node da chon

        List listChassic = new ArrayList();
        req.getSession().setAttribute("listChassic", listChassic);
        if (!Constant.IS_VER_HAITI) {
            listChassic = findAllByDslamId(dslamId);
            req.getSession().setAttribute("listChassic", listChassic);
        } else {
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            Dslam dslam = dslamDAO.findById(dslamId);
            if (dslam != null && dslam.getDslamId() != null && dslam.getProvince() != null) {
                listChassic = findAllByProvince(dslam.getProvince());
                req.getSession().setAttribute("listChassic", listChassic);
            }
        }
        log.info("End method saveChassic of ChassicDAO");
        return pageForward;
    }

    public String deleteChassic() throws Exception {
        log.info("Begin method deleteChassic of ChassicDAO ...");

        HttpServletRequest req = getRequest();
        Session mySession = this.getSession();
        pageForward = CHASSIC;

        ChassicForm f = this.chassicForm;
        String strChassicId = (String) QueryCryptUtils.getParameter(req, "chassicId");
        if (strChassicId == null || strChassicId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.169");
            return pageForward;
        }
        Long chassicId = Long.parseLong(strChassicId);
        Long dslamId = -1L;

//        Chassic bo = findById(chassicId);
//        Long dslamId = bo.getDslamId();

        try {

            HashMap<String, Object> result = new HashMap();
            result = deleteChassicByChassicId(mySession, chassicId);

            if (result.get(ChassicDAO.DSLAM_ID) != null) {
                dslamId = Long.valueOf(result.get(ChassicDAO.DSLAM_ID).toString());
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

                List listChassic = new ArrayList();
                listChassic = findAllByDslamId(dslamId);
                req.setAttribute("listChassic", listChassic);

                return pageForward;
            }

            //TrongLV
            //KIEM TRA DIEU KIEN XOA
            //NEU CON SLOT CO HIEU LUC THI KHONG DUOC PHEP
//            SlotDAO slotDAO = new SlotDAO();
//            slotDAO.setSession(mySession);
//            String[] propertyName = {"chassicId","status"};
//            Object[] value = {f.getChassicId(),Constant.STATUS_USE};
//            List<Slot> listSlot = slotDAO.findByProperty(propertyName,value);
//            if (listSlot != null && listSlot.size()>0){
//                req.setAttribute(Constant.RETURN_MESSAGE, "Không được xoá Chassic nếu còn Slot có hiệu lực!");
//                return pageForward;
//            }
//
//            getSession().delete(bo);


            req.setAttribute("returnMsg", "chassic.delete.success");
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "chassic.delete.failed");
            mySession.clear();
            mySession.getTransaction().rollback();
            mySession.beginTransaction();

        }
//        getSession().delete(bo);
        f.resetForm();
        f.setDslamId(dslamId);
        List listChassic = new ArrayList();
        listChassic = findAllByDslamId(dslamId);
        req.getSession().removeAttribute("listChassic");
        req.setAttribute("listChassic", listChassic);

        req.getSession().setAttribute("toEditchassic", 0);

        pageForward = CHASSIC;

        log.info("End method deleteChassic of ChassicDAO");

        return pageForward;
    }

    public String searchChassic() throws Exception {
        log.info("Begin method searchChassic of ChassicDAO ...");

        HttpServletRequest req = getRequest();
        ChassicForm f = getChassicForm();
        try {
            Long dslamId = f.getDslamId();

            if (dslamId == null || dslamId.intValue() == 0) {
                req.setAttribute("returnMsg", "ERR.INF.170");
                pageForward = CHASSIC;
                log.info("End method searchChassic of ChassicDAO");
                return pageForward;
            }

            List listChassic = new ArrayList();
            if (!Constant.IS_VER_HAITI) {
                listChassic = findAllByDslamId(dslamId);
            } else {
                DslamDAO dslamDAO = new DslamDAO();
                dslamDAO.setSession(this.getSession());
                Dslam dslam = dslamDAO.findById(dslamId);
                if (dslam != null && dslam.getDslamId() != null && dslam.getProvince() != null) {
                    listChassic = findAllByProvince(dslam.getProvince());
                    if (listChassic != null && !listChassic.isEmpty()) {
                        Chassic chassic = (Chassic) listChassic.get(0);
                        if (chassic != null) {
                            f.setDslamId(chassic.getDslamId());
                        }
                    }
                }
            }

            req.getSession().setAttribute("toEditChassic", 2);
            req.getSession().removeAttribute("listChassic");
            req.getSession().removeAttribute("listChassic");
            req.getSession().setAttribute("listChassic", listChassic);
            if (listChassic.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "dslam.search");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listChassic.size());
                req.setAttribute("returnMsgParam", listMessageParam);
            }
        } catch (Exception ex) {
            getSession().clear();
            //req.setAttribute("returnMsg", "Xuất hiện lỗi khi thêm nhà cung cấp");
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
        }
        pageForward = CHASSIC;

        log.info("End method searchChassic of ChassicDAO");

        return pageForward;
    }

    public String getListDslamCode() throws Exception {
        try {
            //Chon hang hoa --> lay don vi tinh
            HttpServletRequest httpServletRequest = getRequest();
            String dslamCode = httpServletRequest.getParameter("chassicForm.dslamCode");

            List<Dslam> listDslam = new ArrayList();
            if (dslamCode != null) {
                if ("".equals(dslamCode)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Dslam ";
                queryString += " where status = 1 ";
                //parameterList.add(String.valueOf(Constant.STATUS_USE));

                queryString += " and upper(code) like ? ";
                parameterList.add("%" + dslamCode.trim().toUpperCase() + "%");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listDslam = queryObject.list();

                    for (int i = 0; i < listDslam.size(); i++) {
                        lstDslamCode.put(listDslam.get(i).getDslamId(), listDslam.get(i).getCode());
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

    public String getListSwitchCode() throws Exception {
        try {
            //Chon hang hoa --> lay don vi tinh
            HttpServletRequest httpServletRequest = getRequest();
            String switchCode = httpServletRequest.getParameter("chassicForm.switchCode");

            List<Switches> listSwitch = new ArrayList();
            if (switchCode != null) {
                if ("".equals(switchCode.trim())) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from Switches ";
                queryString += " where status = 1 ";
                // parameterList.add(String.valueOf(Constant.STATUS_USE));

                queryString += " and upper(code) like ? ";
                parameterList.add("%" + switchCode.trim().toUpperCase() + "%");

                queryString += " order by code ";

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                if (!queryObject.list().isEmpty()) {
                    listSwitch = queryObject.list();

                    for (int i = 0; i < listSwitch.size(); i++) {
                        lstSwitchCode.put(listSwitch.get(i).getSwitchId(), listSwitch.get(i).getCode());
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

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of ChassicDAO ...");

        HttpServletRequest req = getRequest();

//        ChassicForm f = this.chassicForm;

//        req.getSession().setAttribute("ToEditChassic", 0);
        pageForward = "chassicResult";

        searchChassic();
        req.setAttribute("returnMsg", "");

        log.info("End method preparePage ChassicDAO");

        return pageForward;
    }
    //Phan ham tu sinh

    public void save(Chassic transientInstance) {
        log.debug("saving Chassic instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Chassic persistentInstance) {
        log.debug("deleting Chassic instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Chassic findById(Long id) {
        log.debug("getting Chassic instance with id: " + id);
        try {
            Chassic instance = (Chassic) getSession().get(
                    "com.viettel.im.database.BO.Chassic", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Chassic instance) {
        log.debug("finding Chassic instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.Chassic").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Chassic instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Chassic as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
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

    public List findByEquipmentId(Object equipmentId) {
        return findByProperty(EQUIPMENT_ID, equipmentId);
    }

    public List findByCode(Object code) {
        return findByProperty(CODE, code);
    }

    public List findByHostName(Object hostName) {
        return findByProperty(HOST_NAME, hostName);
    }

    public List findByIp(Object ip) {
        return findByProperty(IP, ip);
    }

    public List findByVlanStart(Object vlanStart) {
        return findByProperty(VLAN_START, vlanStart);
    }

    public List findByVlanStop(Object vlanStop) {
        return findByProperty(VLAN_STOP, vlanStop);
    }

    public List findByNmsVlan(Object nmsVlan) {
        return findByProperty(NMS_VLAN, nmsVlan);
    }

    public List findBySwitchId(Object switchId) {
        return findByProperty(SWITCH_ID, switchId);
    }

    public List findByChannelNumber(Object channelNumber) {
        return findByProperty(CHANNEL_NUMBER, channelNumber);
    }

    public List findByTotalSlot(Object totalSlot) {
        return findByProperty(TOTAL_SLOT, totalSlot);
    }

    public List findByTotalPort(Object totalPort) {
        return findByProperty(TOTAL_PORT, totalPort);
    }

    public List findByUsedPort(Object usedPort) {
        return findByProperty(USED_PORT, usedPort);
    }

    public List findByInvalidPort(Object invalidPort) {
        return findByProperty(INVALID_PORT, invalidPort);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByChassicType(Object chassicType) {
        return findByProperty(CHASSIC_TYPE, chassicType);
    }

    public List findBySubSlot(Object subSlot) {
        return findByProperty(SUB_SLOT, subSlot);
    }

    public List findByPortBras(Object portBras) {
        return findByProperty(PORT_BRAS, portBras);
    }

    public List findAll() {
        log.debug("finding all Chassic instances");

        try {
            String queryString = "from Chassic order by nlssort(code,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, String.valueOf(Constant.STATUS_DELETE));
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllByDslamId(Long dslamId) {
        log.debug("finding all Chassic instances");
        HttpServletRequest req = getRequest();
        List parameterList = new ArrayList();

        try {
            //Lay ten DSLAM
            DslamDAO dslam = new DslamDAO();
            dslam.setSession(this.getSession());
            Dslam dslamBO = dslam.findById(dslamId);
            if (dslamBO == null) {
                return null;
            }
            req.getSession().setAttribute("dslamName", dslamBO.getName());
//            req.setAttribute("dslamName", dslamBO.getName());
            //Lay danh sach Chassic
            String queryString = "select new Chassic("
                    + "a.chassicId,a.dslamId,a.equipmentId,a.code,a.hostName,a.ip,a.vlanStart,a.vlanStop,"
                    + "c.nmsVlan, a.switchId,a.channelNumber,a.totalSlot,a.totalPort,a.usedPort,"
                    + "a.invalidPort, a.setupDate,a.startupDate,a.status,"
                    + "a.chassicType,a.subSlot,a.portBras,b.name,c.name, a.macAddress)";
            queryString += " from Chassic a,Equipment b, Switches c ";
            queryString += " where a.equipmentId = b.equipmentId ";
            queryString += " and c.switchId = a.switchId ";
            queryString += " and a.dslamId= ? ";
            parameterList.add(dslamId);
            queryString += " order by nlssort(a.code,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }


            return queryObject.list();



        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllByProvince(String province) {
        log.debug("finding all Chassic instances");
        HttpServletRequest req = getRequest();
        List parameterList = new ArrayList();

        try {
            if (province == null) {
                province = "";
            } else {
                province = province.trim().toUpperCase();
            }
            String queryString = "select "
                    + "a.chassic_Id as chassicId "
                    + ",a.dslam_Id as dslamId"
                    + ",a.equipment_Id as equipmentId"
                    + ",a.code"
                    + ",a.host_Name as hostName"
                    + ",a.ip"
                    + ",a.vlan_Start as vlanStart"
                    + ",a.vlan_Stop as vlanStop"
                    + ",c.nms_Vlan as nmsVlan"
                    + ",a.switch_Id as switchId"
                    + ",a.channel_Number as channelNumber"
                    + ",a.total_Slot as totalSlot"
                    + ",a.total_Port as totalPort"
                    + ",a.used_Port as usedPort"
                    + ",a.invalid_Port as invalidPort"
                    + ",a.setup_Date as setupDate"
                    + ",a.startup_Date as startupDate"
                    + ",a.status"
                    + ",a.chassic_Type as chassicType"
                    + ",a.sub_Slot as subSlot"
                    + ",a.port_Bras as portBras"
                    + ",b.name as equipmentName"
                    + ",c.name as switchName "
                    + ",a.mac_Address as macAddress ";
            queryString += " from Chassic a ,Equipment b, Switches c ";
            queryString += " where a.equipment_Id = b.equipment_Id (+) ";
            queryString += " and a.switch_Id  = c.switch_Id (+) ";
            queryString += " and a.dslam_Id in (select dslam_Id from Dslam where upper(province) = ?) ";
            parameterList.add(province);
            
            //Bo sung
            ChassicForm f = getChassicForm();
            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                queryString += " and upper(a.code) = ? ";
                parameterList.add(f.getCode().trim().toUpperCase());
            }

            if (f.getEquipmentId() != null && !f.getEquipmentId().equals(0L)) {
                queryString += " and a.equipment_Id = ? ";
                parameterList.add(f.getEquipmentId());
            }
            if (f.getHostName() != null && !f.getHostName().trim().equals("")) {
                queryString += " and a.host_Name = ? ";
                parameterList.add(f.getHostName().trim());
            }
            if (f.getIp() != null && !f.getIp().trim().equals("")) {
                queryString += " and a.ip like ? ";
                parameterList.add("%" + f.getIp().trim() + "%");
            }
            if (f.getVlanStart() != null && !f.getVlanStart().trim().equals("")) {
                queryString += " and a.vlan_Start = ? ";
                parameterList.add(Long.parseLong(f.getVlanStart().trim()));
            }
            if (f.getVlanStop() != null && !f.getVlanStop().trim().equals("")) {
                queryString += " and a.vlan_Stop = ? ";
                parameterList.add(Long.parseLong(f.getVlanStop().trim()));
            }
            if (f.getNmsVlan() != null) {
                queryString += " and c.nms_Vlan = ? ";
                parameterList.add((f.getNmsVlan()));
            }
            if (f.getSwitchId() != null  && !f.getSwitchId().equals(0L)) {
                queryString += " and a.switch_Id = ? ";
                parameterList.add(f.getSwitchId());
            }
            if (f.getChannelNumber() != null && !f.getChannelNumber().trim().equals("")) {
                queryString += " and a.channel_Number = ? ";
                parameterList.add(Long.parseLong(f.getChannelNumber().trim()));
            }
            if (f.getTotalPort() != null && !f.getTotalPort().trim().equals("")) {
                queryString += " and a.total_Port = ? ";
                parameterList.add(Long.parseLong(f.getTotalPort().trim()));
            }
            if (f.getTotalSlot() != null && !f.getTotalSlot().trim().equals("")) {
                queryString += " and a.total_Slot = ? ";
                parameterList.add(Long.parseLong(f.getTotalSlot().trim()));
            }
            if (f.getUsedPort() != null && !f.getUsedPort().trim().equals("")) {
                queryString += " and a.used_Port = ? ";
                parameterList.add(Long.parseLong(f.getUsedPort().trim()));
            }
            if (f.getInvalidPort() != null && !f.getInvalidPort().trim().equals("")) {
                queryString += " and a.invalid_Port = ? ";
                parameterList.add(Long.parseLong(f.getInvalidPort().trim()));
            }
            if (f.getChassicType() != null && !f.getChassicType().trim().equals("")) {
                queryString += " and a.chassic_Type = ? ";
                parameterList.add(f.getChassicType().trim());
            }
            if (f.getSubSlot() != null && !f.getSubSlot().trim().equals("")) {
                queryString += " and a.sub_Slot = ? ";
                parameterList.add(Long.parseLong(f.getSubSlot().trim()));
            }
            if (f.getPortBras() != null && !f.getPortBras().trim().equals("")) {
                queryString += " and a.port_Bras = ? ";
                parameterList.add(Long.parseLong(f.getPortBras().trim()));
            }
            if (f.getStatus() != null) {
                queryString += " and a.status = ? ";
                parameterList.add(f.getStatus());
            }
            try{
            if (f.getSetupDate() != null && !f.getSetupDate().trim().equals("")) {
                queryString += " and a.setup_Date = ? ";
                parameterList.add(DateTimeUtils.convertStringToDate(f.getSetupDate().trim()));
            }
            if (f.getStartupDate() != null && !f.getStartupDate().trim().equals("")) {
                queryString += " and a.startup_Date = ? ";
                parameterList.add(DateTimeUtils.convertStringToDate(f.getStartupDate().trim()));
            }
            }catch(Exception ex){
                log.debug(ex.getMessage());
            }
            
            if (f.getMacAddress() != null && !f.getMacAddress().trim().equals("")) {
                queryString += " and a.mac_Address = ? ";
                parameterList.add((f.getMacAddress().trim()));
            }            
            //Bo sung: het
            
            
            queryString += " order by nlssort(a.code,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createSQLQuery(queryString).addScalar("chassicId", Hibernate.LONG).addScalar("dslamId", Hibernate.LONG).
                    addScalar("equipmentId", Hibernate.LONG).
                    addScalar("code", Hibernate.STRING).
                    addScalar("hostName", Hibernate.STRING).
                    addScalar("ip", Hibernate.STRING).
                    addScalar("vlanStart", Hibernate.LONG).
                    addScalar("vlanStop", Hibernate.LONG).
                    addScalar("nmsVlan", Hibernate.LONG).
                    addScalar("switchId", Hibernate.LONG).
                    addScalar("channelNumber", Hibernate.LONG).
                    addScalar("totalSlot", Hibernate.LONG).
                    addScalar("totalPort", Hibernate.LONG).
                    addScalar("setupDate", Hibernate.DATE).
                    addScalar("startupDate", Hibernate.DATE).
                    addScalar("status", Hibernate.LONG).
                    addScalar("chassicType", Hibernate.STRING).
                    addScalar("subSlot", Hibernate.LONG).
                    addScalar("portBras", Hibernate.LONG).
                    addScalar("equipmentName", Hibernate.STRING).
                    addScalar("switchName", Hibernate.STRING).
                    addScalar("macAddress", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(Chassic.class));
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }

            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List getListChassic(Long dslamId) throws Exception {
        log.debug("finding all Chassic instances");
        HttpServletRequest req = getRequest();
        ChassicForm f = getChassicForm();
        List parameterList = new ArrayList();

        try {

            String queryString = "select "
                    + "a.chassic_Id as chassicId "
                    + ",a.dslam_Id as dslamId"
                    + ",a.equipment_Id as equipmentId"
                    + ",a.code"
                    + ",a.host_Name as hostName"
                    + ",a.ip"
                    + ",a.vlan_Start as vlanStart"
                    + ",a.vlan_Stop as vlanStop"
                    + ",c.nms_Vlan as nmsVlan"
                    + ",a.switch_Id as switchId"
                    + ",a.channel_Number as channelNumber"
                    + ",a.total_Slot as totalSlot"
                    + ",a.total_Port as totalPort"
                    + ",a.used_Port as usedPort"
                    + ",a.invalid_Port as invalidPort"
                    + ",a.setup_Date as setupDate"
                    + ",a.startup_Date as startupDate"
                    + ",a.status"
                    + ",a.chassic_Type as chassicType"
                    + ",a.sub_Slot as subSlot"
                    + ",a.port_Bras as portBras"
                    + ",b.name as equipmentName"
                    + ",c.name as switchName "
                    + ",a.mac_Address as macAddress ";
            queryString += " from Chassic a ,Equipment b, Switches c ";
            queryString += " where a.equipment_Id = b.equipment_Id (+) ";
            queryString += " and a.switch_Id  = c.switch_Id (+) ";
            queryString += " and a.dslam_Id= ? ";
            parameterList.add(dslamId);

            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                queryString += " and upper(a.code) = ? ";
                parameterList.add(f.getCode().trim().toUpperCase());
            }

            if (f.getEquipmentId() != null) {
                queryString += " and a.equipment_Id = ? ";
                parameterList.add(f.getEquipmentId());
            }
            if (f.getHostName() != null && !f.getHostName().trim().equals("")) {
                queryString += " and a.host_Name = ? ";
                parameterList.add(f.getHostName().trim());
            }
            if (f.getIp() != null && !f.getIp().trim().equals("")) {
                queryString += " and a.ip like ? ";
                parameterList.add("%" + f.getIp().trim() + "%");
            }
            if (f.getVlanStart() != null && !f.getVlanStart().trim().equals("")) {
                queryString += " and a.vlan_Start = ? ";
                parameterList.add(Long.parseLong(f.getVlanStart().trim()));
            }
            if (f.getVlanStop() != null && !f.getVlanStop().trim().equals("")) {
                queryString += " and a.vlan_Stop = ? ";
                parameterList.add(Long.parseLong(f.getVlanStop().trim()));
            }
            if (f.getNmsVlan() != null) {
                queryString += " and c.nms_Vlan = ? ";
                parameterList.add((f.getNmsVlan()));
            }
            if (f.getSwitchId() != null) {
                queryString += " and a.switch_Id = ? ";
                parameterList.add(f.getSwitchId());
            }
            if (f.getChannelNumber() != null && !f.getChannelNumber().trim().equals("")) {
                queryString += " and a.channel_Number = ? ";
                parameterList.add(Long.parseLong(f.getChannelNumber().trim()));
            }
            if (f.getTotalPort() != null && !f.getTotalPort().trim().equals("")) {
                queryString += " and a.total_Port = ? ";
                parameterList.add(Long.parseLong(f.getTotalPort().trim()));
            }
            if (f.getTotalSlot() != null && !f.getTotalSlot().trim().equals("")) {
                queryString += " and a.total_Slot = ? ";
                parameterList.add(Long.parseLong(f.getTotalSlot().trim()));
            }
            if (f.getUsedPort() != null && !f.getUsedPort().trim().equals("")) {
                queryString += " and a.used_Port = ? ";
                parameterList.add(Long.parseLong(f.getUsedPort().trim()));
            }
            if (f.getInvalidPort() != null && !f.getInvalidPort().trim().equals("")) {
                queryString += " and a.invalid_Port = ? ";
                parameterList.add(Long.parseLong(f.getInvalidPort().trim()));
            }
            if (f.getChassicType() != null && !f.getChassicType().trim().equals("")) {
                queryString += " and a.chassic_Type = ? ";
                parameterList.add(f.getChassicType().trim());
            }
            if (f.getSubSlot() != null && !f.getSubSlot().trim().equals("")) {
                queryString += " and a.sub_Slot = ? ";
                parameterList.add(Long.parseLong(f.getSubSlot().trim()));
            }
            if (f.getPortBras() != null && !f.getPortBras().trim().equals("")) {
                queryString += " and a.port_Bras = ? ";
                parameterList.add(Long.parseLong(f.getPortBras().trim()));
            }
            if (f.getStatus() != null) {
                queryString += " and a.status = ? ";
                parameterList.add(f.getStatus());
            }
            if (f.getSetupDate() != null && !f.getSetupDate().trim().equals("")) {
                queryString += " and a.setup_Date = ? ";
                parameterList.add(DateTimeUtils.convertStringToDate(f.getSetupDate().trim()));
            }
            if (f.getStartupDate() != null && !f.getStartupDate().trim().equals("")) {
                queryString += " and a.startup_Date = ? ";
                parameterList.add(DateTimeUtils.convertStringToDate(f.getStartupDate().trim()));
            }

            queryString += " order by nlssort(a.code,'nls_sort=Vietnamese') asc";

            Query queryObject = getSession().createSQLQuery(queryString).addScalar("chassicId", Hibernate.LONG).addScalar("dslamId", Hibernate.LONG).addScalar("equipmentId", Hibernate.LONG).addScalar("code", Hibernate.STRING).addScalar("hostName", Hibernate.STRING).addScalar("ip", Hibernate.STRING).addScalar("vlanStart", Hibernate.LONG).addScalar("vlanStop", Hibernate.LONG).addScalar("nmsVlan", Hibernate.LONG).addScalar("switchId", Hibernate.LONG).addScalar("channelNumber", Hibernate.LONG).addScalar("totalSlot", Hibernate.LONG).addScalar("totalPort", Hibernate.LONG).addScalar("setupDate", Hibernate.DATE).addScalar("startupDate", Hibernate.DATE).addScalar("status", Hibernate.LONG).addScalar("chassicType", Hibernate.STRING).addScalar("subSlot", Hibernate.LONG).addScalar("portBras", Hibernate.LONG).addScalar("equipmentName", Hibernate.STRING).addScalar("switchName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(Chassic.class));

            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }

//            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
//            for (int i = 0; i < parameterList.size(); i++) {
//                queryObject.setParameter(i, parameterList.get(i));
//            }


            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllEquipment() {
        log.debug("finding all Equipment instances");

        try {
            String queryString = " from Equipment ";
            //queryString += " where not status = 0 ";
            queryString += " where status = 1L ";
            queryString += "and equipmentType = " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(Constant.EQUIPMENT_TYPE_CHASSIC) + " ";
            queryString += " order by name ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllSwitch() {
        log.debug("finding all Switch instances");

        try {
            String queryString = " from Switches ";
//            queryString += " where not status = 0 ";
            queryString += " where status = 1 ";
            queryString += " order by name ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findSwitchByEquipmentId(Long equipmentId) {
        log.debug("finding all SwitchByEquipmentId instances");

        try {
            String queryString = "from Switches as model where model.equipmentId=?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, equipmentId);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT + 1);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find SwitchByEquipmentId failed", re);
            throw re;
        }
    }

    public Chassic merge(Chassic detachedInstance) {
        log.debug("merging Chassic instance");
        try {
            Chassic result = (Chassic) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Chassic instance) {
        log.debug("attaching dirty Chassic instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Chassic instance) {
        log.debug("attaching clean Chassic instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /**
     *
     *  @author NamNX
     * 15/06/2009
     */
    public String getChassicTree() throws Exception {

        if (!Constant.IS_VER_HAITI) {

            try {
                this.listItems = new ArrayList();
                Session hbnSession = getSession();

                HttpServletRequest httpServletRequest = getRequest();

                UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);



                String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");

                if (node.indexOf("0_") == 0) {

                    HashMap<String, Object> hashMap = new HashMap();
                    if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(AssignDslamAreaDAO.ROLE_VIEW_ALL_PROVINCE), httpServletRequest)) {
                        //LAY TINH CUA USER DANG NHAP
                        ShopDAO shopDAO = new ShopDAO();
                        shopDAO.setSession(hbnSession);
                        Shop shop = shopDAO.findById(userToken.getShopId());
                        if (shop != null && shop.getShopId() != null && shop.getProvince() != null) {
                            hashMap.put(AreaDAO.AREA_CODE, shop.getProvince());
                        }
                    }
                    hashMap.put("IS_NULL." + AreaDAO.PARENT_CODE, "");

                    hashMap.put("ORDER_BY", "areaCode");

                    List<Area> listProvince = CommonDAO.findByProperty(hbnSession, AreaDAO.TABLE_NAME, hashMap);

                    //neu la lan lay cay du lieu muc 1, tra ve danh sach cac province
//                AreaDAO areaDAO = new AreaDAO();
//                areaDAO.setSession(this.getSession());
//                List<Area> listProvince = areaDAO.findAllProvince();

                    Iterator iterProvince = listProvince.iterator();
                    while (iterProvince.hasNext()) {
                        Area area = (Area) iterProvince.next();
                        String nodeId = "1_" + area.getProvince(); //them prefix 2_ de xac dinh la node level
                        String doString = httpServletRequest.getContextPath() + "/chassicAction!dslamResult.do?selectedProvince=" + area.getProvince();
                        getListItems().add(new Node(area.getAreaCode() + " - " + area.getName(), "true", nodeId, doString));
                    }
                } else if (node.indexOf("1_") == 0) {
                    //neu la cau du lieu muc 1, tra ve danh sach cac DSLAM cua province da chon
                    node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                    List listDslam = findDslamByProvince(node);
                    Iterator iterDslam = listDslam.iterator();
                    while (iterDslam.hasNext()) {
                        Dslam dslam = (Dslam) iterDslam.next();
                        String nodeId = "2_" + dslam.getDslamId().toString(); //them prefix 2_ de xac dinh la node level
                        String doString = httpServletRequest.getContextPath() + "/chassicAction!displayChassic.do?selectedDslamId=" + dslam.getDslamId().toString();
                        getListItems().add(new Node(dslam.getCode() + " - " + dslam.getName(), "true", nodeId, doString));
                    }
                } else if (node.indexOf("2_") == 0) {
                    //neu la cau du lieu muc 1, tra ve danh sach cac stockmodel tuong ung voi stockType
                    node = node.substring(2); //bo phan prefix danh dau level cua cay (2_)

                    List listChassic = this.findAllByDslamId(Long.parseLong(node));
                    Iterator iterChassic = listChassic.iterator();
                    while (iterChassic.hasNext()) {
                        Chassic chassic = (Chassic) iterChassic.next();

                        String nodeId = "3_" + chassic.getChassicId().toString(); //them prefix 3_ de xac dinh la node level

                        String doString = httpServletRequest.getContextPath() + "/slotAction!displaySlot.do?selectedChassicId=" + chassic.getChassicId().toString();
                        getListItems().add(new Node(chassic.getCode() + " - " + chassic.getHostName(), "true", nodeId, doString));

                    }
                } else if (node.indexOf("3_") == 0) {
                    //neu la cau du lieu muc 1, tra ve danh sach cac stockmodel tuong ung voi stockType
                    node = node.substring(2); //bo phan prefix danh dau level cua cay (2_)
                    SlotDAO slot = new SlotDAO();
                    slot.setSession(this.getSession());
                    List listSlot = slot.findByChassicId(Long.parseLong(node));
                    Iterator iterSlot = listSlot.iterator();
                    while (iterSlot.hasNext()) {
                        Slot slotBO = (Slot) iterSlot.next();
                        String nodeId = "4_" + slotBO.getSlotId().toString(); //them prefix 4_ de xac dinh la node level
                        String doString = httpServletRequest.getContextPath() + "/portAction!displayPort.do?selectedSlotId=" + slotBO.getSlotId().toString();

                        CardDAO cardDAO = new CardDAO();
                        cardDAO.setSession(this.getSession());
                        Card card = cardDAO.findById(slotBO.getCardId());
                        String node4 = card.getCode() + " - " + slotBO.getSlotPosition().toString();

                        getListItems().add(new Node(node4, "true", nodeId, doString));
                    }
                }

                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {

            try {
                this.listItems = new ArrayList();
                Session hbnSession = getSession();
                HttpServletRequest httpServletRequest = getRequest();
                UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);
                String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");
                if (node.indexOf("0_") == 0) {
                    HashMap<String, Object> hashMap = new HashMap();
                    if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(AssignDslamAreaDAO.ROLE_VIEW_ALL_PROVINCE), httpServletRequest)) {
                        //LAY TINH CUA USER DANG NHAP
                        ShopDAO shopDAO = new ShopDAO();
                        shopDAO.setSession(hbnSession);
                        Shop shop = shopDAO.findById(userToken.getShopId());
                        if (shop != null && shop.getShopId() != null && shop.getProvince() != null) {
                            hashMap.put(AreaDAO.AREA_CODE, shop.getProvince());
                        }
                    }
                    hashMap.put("IS_NULL." + AreaDAO.PARENT_CODE, "");
                    hashMap.put("ORDER_BY", "areaCode");

                    List<Area> listProvince = CommonDAO.findByProperty(hbnSession, AreaDAO.TABLE_NAME, hashMap);

                    Iterator iterProvince = listProvince.iterator();
                    while (iterProvince.hasNext()) {
                        Area area = (Area) iterProvince.next();
                        String nodeId = "1_" + area.getProvince(); //them prefix 2_ de xac dinh la node level
                        String doString = httpServletRequest.getContextPath() + "/chassicAction!displayChassic.do?selectedProvince=" + area.getProvince();
                        getListItems().add(new Node(area.getAreaCode() + " - " + area.getName(), "true", nodeId, doString));
                    }
                } else if (node.indexOf("1_") == 0) {
                    //neu la cau du lieu muc 1, tra ve danh sach cac DSLAM cua province da chon
                    node = node.substring(2); //bo phan prefix danh dau level cua cay (1_)
                    List listChassic = findAllByProvince(node);
                    Iterator iterChassic = listChassic.iterator();
                    while (iterChassic.hasNext()) {
                        Chassic chassic = (Chassic) iterChassic.next();
                        String nodeId = "2_" + chassic.getChassicId().toString(); //them prefix 2_ de xac dinh la node level
                        if (chassic.getHostName() != null && !chassic.getHostName().trim().equals("")) {
                            String doString = httpServletRequest.getContextPath() + "/slotAction!displaySlot.do?selectedChassicId=" + chassic.getChassicId().toString();
                            getListItems().add(new Node(chassic.getCode() + " - " + chassic.getHostName(), "true", nodeId, doString));
                        } else {
                            getListItems().add(new Node(chassic.getCode() + " - chassic is not config", "false", nodeId, ""));
                        }
                    }
                } else if (node.indexOf("2_") == 0) {
                    //neu la cau du lieu muc 1, tra ve danh sach cac stockmodel tuong ung voi stockType
                    node = node.substring(2); //bo phan prefix danh dau level cua cay (2_)
                    SlotDAO slot = new SlotDAO();
                    slot.setSession(this.getSession());
                    List listSlot = slot.findByChassicId(Long.parseLong(node));
                    Iterator iterSlot = listSlot.iterator();
                    while (iterSlot.hasNext()) {
                        Slot slotBO = (Slot) iterSlot.next();
                        String nodeId = "3_" + slotBO.getSlotId().toString(); //them prefix 4_ de xac dinh la node level
                        String doString = httpServletRequest.getContextPath() + "/portAction!displayPort.do?selectedSlotId=" + slotBO.getSlotId().toString();

                        CardDAO cardDAO = new CardDAO();
                        cardDAO.setSession(this.getSession());
                        Card card = cardDAO.findById(slotBO.getCardId());
                        String node3 = "Position: " + slotBO.getSlotPosition() + " - Card: " + card.getCode().toString();

                        getListItems().add(new Node(node3, "true", nodeId, doString));
                    }
                }

                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public List findDslamByProvince(String provinceCode) {
        log.debug("finding all Dslam instances");
        try {
            HttpServletRequest req = getRequest();
            String queryString = "from Dslam where province = ? and status = ? and productId = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, provinceCode);
            queryObject.setParameter(1, Constant.STATUS_USE);
            queryObject.setParameter(2, Constant.PRODUCT_ID_DSLAM);

            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(this.getSession());
            List<Area> listProvince = areaDAO.findByProvince(provinceCode);
            if (listProvince != null && listProvince.size() > 0) {
                req.setAttribute("provinceName", listProvince.get(0).getName());
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String dslamResult() throws Exception {
        log.info("Begin method listDslam of DslamDAO ...");

        HttpServletRequest req = getRequest();

        //lay danh sach cac Dslam

        String strProvince = req.getParameter("selectedProvince");
        List<Dslam> listDslam = findDslamByProvince(strProvince);

        req.setAttribute("listDslam", listDslam);

        pageForward = DSLAM_RESULT;

        log.info("End method listDslam of DslamDAO");

        return pageForward;
    }

    public String slotResult() throws Exception {
        log.info("Begin method listDslam of DslamDAO ...");

        HttpServletRequest req = getRequest();

        //lay danh sach cac Dslam

        String strSelectedChassicId = req.getParameter("selectedChassicId");
        Long selectedChassicId;
        try {
            selectedChassicId = Long.parseLong(strSelectedChassicId);
        } catch (Exception ex) {
            selectedChassicId = -1L;
        }
        SlotDAO slotDAO = new SlotDAO();
        slotDAO.setSession(this.getSession());
        List<Slot> listSlot = slotDAO.findByChassicId(selectedChassicId);


        req.setAttribute("listSlot", listSlot);

        pageForward = SLOT_RESULT;

        log.info("End method listDslam of DslamDAO");

        return pageForward;
    }

    public String listProvince() throws Exception {
        log.info("Begin method listDslam of DslamDAO ...");

        HttpServletRequest req = getRequest();

        //lay danh sach cac Dslam
        Session hbnSession = getSession();
        List<Area> listProvince = checkRole(hbnSession, req);

        req.setAttribute("listProvince", listProvince);

        pageForward = LIST_PROVINCE;

        log.info("End method listDslam of DslamDAO");

        return pageForward;
    }

    private boolean checkValidateToAdd() {

        Long count;
        HttpServletRequest req = getRequest();

        String hostName = this.chassicForm.getHostName();
        Long equipmentId = this.chassicForm.getEquipmentId();
        Long switchId = this.chassicForm.getSwitchId();
        Long status = this.chassicForm.getStatus();

        if (hostName == null || hostName.trim().equals("")) {
            req.setAttribute("returnMsg", "MSG.INF.Chassic.HostEmpty");
            return false;
        }
        if (equipmentId == null || equipmentId.equals(0L)) {
            req.setAttribute("returnMsg", "chassic.error.invalidEquipmentId");
            return false;
        }
        if (switchId == null || switchId.equals(0L)) {
            req.setAttribute("returnMsg", "chassic.error.invalidSwitchId");
            return false;
        }
        if ((status == null)) {
            req.setAttribute("returnMsg", "chassic.error.invalidStatus");
            return false;
        }

        String vlanStart = this.chassicForm.getVlanStart();
        String vlanStop = this.chassicForm.getVlanStop();
        if (vlanStart != null && !vlanStart.trim().equals("")) {
            try {
                int a = Integer.valueOf(vlanStart.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.076");
                return false;
            }
            this.chassicForm.setVlanStart(vlanStart.trim());
        }
        if (vlanStop != null && !vlanStop.trim().equals("")) {
            try {
                int a = Integer.valueOf(vlanStop.trim());
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "ERR.INF.077");
                return false;
            }
            this.chassicForm.setVlanStop(vlanStop.trim());
        }


        //TrongLV
        //KIEM TRA THONG TIN TONG PORT
        String sTotalPort = this.chassicForm.getTotalPort();
        String sUsedPort = this.chassicForm.getUsedPort();
        String sInvalidPort = this.chassicForm.getInvalidPort();
        int totalPort = 0;
        int usedPort = 0;
        int invalidPort = 0;
        if ((sTotalPort != null) && !sTotalPort.trim().equals("")) {
            totalPort = Integer.valueOf(sTotalPort.trim());
        }
        if ((sUsedPort != null) && !sUsedPort.trim().equals("")) {
            usedPort = Integer.valueOf(sUsedPort.trim());
        }
        if ((sInvalidPort != null) && !sInvalidPort.trim().equals("")) {
            invalidPort = Integer.valueOf(sInvalidPort.trim());
        }
        if (totalPort < usedPort + invalidPort) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.150");
            return false;
        }


        //TrongLV
        //KIEM TRA THONG TIN NGAY CAI DAT VA NGAY KHAI THAC
        //KHONG DUOC LON HON NGAY HIEN TAI
        String setupDate = this.chassicForm.getSetupDate();
        String startupDate = this.chassicForm.getStartupDate();
        Date currDate = new Date();
        if ((setupDate != null) && !setupDate.trim().equals("")) {
            try {
                Date dSetupDate = DateTimeUtils.convertStringToDate(setupDate.trim());
                if (dSetupDate.after(currDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassic.InstallDateError");
                    return false;
                }

            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassic.InstallDateNotValid");
                return false;
            }
        }

        if ((startupDate != null) && !startupDate.trim().equals("")) {
            try {
                Date dStartupDate = DateTimeUtils.convertStringToDate(startupDate.trim());
                if (dStartupDate.after(currDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassic.ExploitDateError");
                    return false;
                }

            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassicl.ExploitDateNotValid");
                return false;
            }
        }


        //String strQuery = "select count(*) from Chassic as model where not model.status= ? and upper(model.code) = ?";

        String strQuery = "select count(*) from Chassic as model where 1=1 and upper(model.hostName) = ?";

        Query q = getSession().createQuery(strQuery);
//        q.setParameter(0, String.valueOf(Constant.STATUS_DELETE));
        q.setParameter(0, hostName.toUpperCase());
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "MSG.INF.Chassic.HostExist");
            return false;
        }
        return true;

    }

    private boolean checkValidateToEdit() {

        Long count;
        HttpServletRequest req = getRequest();
        Long id = this.chassicForm.getChassicId();
        String hostName = this.chassicForm.getHostName();
        Long equipmentId = this.chassicForm.getEquipmentId();
        Long switchId = this.chassicForm.getSwitchId();
        String setupDate = this.chassicForm.getSetupDate();
        String startupDate = this.chassicForm.getStartupDate();
        Long status = this.chassicForm.getStatus();

        if (hostName == null || hostName.trim().equals("")) {
            req.setAttribute("returnMsg", "MSG.INF.Chassic.HostEmpty");
            return false;
        }
        this.chassicForm.setHostName(hostName.trim());

        if ((equipmentId == null) || equipmentId.equals(0L)) {
            req.setAttribute("returnMsg", "chassic.error.invalidEquipmentId");
            return false;
        }
        if ((switchId == null) || switchId.equals(0L)) {
            req.setAttribute("returnMsg", "chassic.error.invalidSwitchId");
            return false;
        }

        if ((status == null)) {
            req.setAttribute("returnMsg", "chassic.error.invalidStatus");
            return false;
        }

        //TrongLV
        //KIEM TRA THONG TIN TONG PORT
        String sTotalPort = this.chassicForm.getTotalPort();
        String sUsedPort = this.chassicForm.getUsedPort();
        String sInvalidPort = this.chassicForm.getInvalidPort();
        int totalPort = 0;
        int usedPort = 0;
        int invalidPort = 0;
        if ((sTotalPort != null) && !sTotalPort.trim().equals("")) {
            totalPort = Integer.valueOf(sTotalPort.trim());
        }
        if ((sUsedPort != null) && !sUsedPort.trim().equals("")) {
            usedPort = Integer.valueOf(sUsedPort.trim());
        }
        if ((sInvalidPort != null) && !sInvalidPort.trim().equals("")) {
            invalidPort = Integer.valueOf(sInvalidPort.trim());
        }
        if (totalPort < usedPort + invalidPort) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.INF.150");
            return false;
        }

        //TrongLV
        //KIEM TRA THONG TIN NGAY CAI DAT VA NGAY KHAI THAC
        //KHONG DUOC LON HON NGAY HIEN TAI
        Date currDate = new Date();
        if ((setupDate != null) && !setupDate.trim().equals("")) {
            try {
                Date dSetupDate = DateTimeUtils.convertStringToDate(setupDate);
                if (dSetupDate.after(currDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassic.InstallDateError");
                    return false;
                }

            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassic.InstallDateNotValid");
                return false;
            }
        }

        if ((startupDate != null) && !startupDate.trim().equals("")) {
            try {
                Date dStartupDate = DateTimeUtils.convertStringToDate(startupDate);
                if (dStartupDate.after(currDate)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassic.ExploitDateError");
                    return false;
                }

            } catch (Exception ex) {
                req.setAttribute(Constant.RETURN_MESSAGE, "MSG.INF.Chassicl.ExploitDateNotValid");
                return false;
            }
        }


//        String strQuery = "select count(*) from Chassic as model where not model.status= ? and upper(model.code) = ? and not model.chassicId=?";

        String strQuery = "select count(*) from Chassic as model where 1=1 and upper(model.hostName) = ? and not model.chassicId=?";

        Query q = getSession().createQuery(strQuery);
//        q.setParameter(0, String.valueOf(Constant.STATUS_DELETE));
        q.setParameter(0, hostName.toUpperCase());
        q.setParameter(1, id);
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "MSG.INF.Chassic.HostExist");
            return false;
        }
        return true;

    }

    public String getSwitch() throws Exception {
        try {

            HttpServletRequest httpServletRequest = getRequest();

            //Chon hang hoa tu loai hang hoa
            String equipmentId = httpServletRequest.getParameter("equipmentId");
            String[] header = {"", "--Chọn Switch---"};
            listSwitchBox.add(header);
            if (equipmentId != null && !"".equals(equipmentId.trim())) {
                Long id = Long.parseLong(equipmentId);
                String SQL_SELECT_EXCHANGE = "select switchId, name from Switches where equipmentId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query q = getSession().createQuery(SQL_SELECT_EXCHANGE);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);
                List lstExchange = q.list();
                listSwitchBox.addAll(lstExchange);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "listSwitchBoxSuccess";
    }

    public HashMap<String, Object> deleteChassicByDslamId_backup(Session mySession, Long dslamId) throws Exception {
        try {
            HashMap<String, Object> result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);

            if (mySession == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "session time out!");
                return result;
            }
            if (dslamId == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "Error. DslamId is null!");
                return result;
            }

            result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);
            int count = -1;

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put(ChassicDAO.DSLAM_ID, dslamId);

            List listChassic = CommonDAO.findByProperty(mySession, ChassicDAO.TABLE_CHASSIC, hashMap);

            if (listChassic != null && listChassic.size() > 0) {
                for (int iChassic = 0; iChassic < listChassic.size(); iChassic++) {
                    Long chassicId = ((Chassic) listChassic.get(iChassic)).getChassicId();
                    result = this.deleteChassicByChassicId(mySession, chassicId);

                    if (result.get(PortDAO.HASH_MAP_RESULT) != null) {
                        count += Integer.valueOf(result.get(PortDAO.HASH_MAP_RESULT).toString());
                    }
                    if (count < 0) {
                        return result;
                    }
                }
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "Error. Dslam is not exists!");
                return result;
            }

            if (count > 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.Success");
                result.put(PortDAO.HASH_MAP_RESULT, count);
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.UnSuccess");
            }

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public HashMap<String, Object> deleteChassicByChassicId(Session mySession, Long chassicId) throws Exception {
        try {
            HashMap<String, Object> result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);

            Long dslamId = -1L;

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

            List listChassic = CommonDAO.findByProperty(mySession, ChassicDAO.TABLE_CHASSIC, hashMap);
            if (listChassic != null && listChassic.size() > 0) {
                dslamId = ((Chassic) listChassic.get(0)).getDslamId();
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.NotExist");
                return result;
            }

            SlotDAO slotDAO = new SlotDAO();
            result = slotDAO.deleteSlotByChassicId(mySession, chassicId);
            int count = -1;
            if (result.get(PortDAO.HASH_MAP_RESULT) != null) {
                count = Integer.valueOf(result.get(PortDAO.HASH_MAP_RESULT).toString());
            }
            if (count < 0) {
                return result;
            }

            result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);
            result.put(ChassicDAO.DSLAM_ID, dslamId);

            count = -1;
            count = CommonDAO.deleteObject(mySession, ChassicDAO.TABLE_CHASSIC, hashMap);
            if (count > 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.Success");
                result.put(PortDAO.HASH_MAP_RESULT, count);
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.UnSuccess");
            }

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public HashMap<String, Object> deleteChassicByDslamId(Session mySession, Long dslamId) throws Exception {
        try {
            HashMap<String, Object> result = new HashMap();
            result.put(PortDAO.HASH_MAP_RESULT, -1);

            if (mySession == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "session time out!");
                return result;
            }
            if (dslamId == null) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "dslamId is null!");
                return result;
            }

            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put(ChassicDAO.DSLAM_ID, dslamId);
            hashMap.put(ChassicDAO.STATUS, Constant.STATUS_USE);
            hashMap.put("NOT_NULL" + ChassicDAO.EQUIPMENT_ID, "");
            List listChassic = CommonDAO.findByProperty(mySession, ChassicDAO.TABLE_CHASSIC, hashMap);
            if (listChassic != null && listChassic.size() > 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.ActiveChasset");
                return result;
            }

            hashMap = new HashMap();
            hashMap.put(ChassicDAO.DSLAM_ID, dslamId);
            listChassic = CommonDAO.findByProperty(mySession, ChassicDAO.TABLE_CHASSIC, hashMap);
            if (listChassic == null || listChassic.size() == 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.NotExist");
                result.put(PortDAO.HASH_MAP_RESULT, 0);
                return result;
            }

            int count = -1;
            int total = 0;
            for (int i = 0; i < listChassic.size(); i++) {
                HashMap<String, Object> resultTemp = this.deleteChassicByChassicId(mySession, ((Chassic) listChassic.get(0)).getChassicId());

                count = -1;
                if (resultTemp.get(PortDAO.HASH_MAP_RESULT) != null) {
                    count = Integer.valueOf(resultTemp.get(PortDAO.HASH_MAP_RESULT).toString());
                }
                if (count < 0) {
                    result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.ChassicDslam");
                    return result;
                }
                total += count;
            }
            result.put(PortDAO.HASH_MAP_RESULT, total);
            if (total > 0) {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.Success");
            } else {
                result.put(PortDAO.HASH_MAP_MESSAGE, "MSG.INF.Chassic.Delete.UnSuccess");
            }

            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    /*
     * Author: TheTM
     * Date created: 26/10/2010
     * Purpose: Lay danh sach province theo quyen cua Component :ROLE_VIEW_ALL_PROVINCE
     */
    private List<Area> checkRole(Session session, HttpServletRequest httpServletRequest) {
        List<Area> listProvince = null;
        try {
            HashMap<String, Object> hashMap = new HashMap();
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);
            if (!AuthenticateDAO.checkAuthority(ResourceBundleUtils.getResource(AssignDslamAreaDAO.ROLE_VIEW_ALL_PROVINCE), httpServletRequest)) {
                //LAY TINH CUA USER DANG NHAP
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(session);
                Shop shop = shopDAO.findById(userToken.getShopId());
                if (shop != null && shop.getShopId() != null && shop.getProvince() != null) {
                    hashMap.put(AreaDAO.AREA_CODE, shop.getProvince());
                }
            }
            hashMap.put("IS_NULL." + AreaDAO.PARENT_CODE, "");
            hashMap.put("ORDER_BY", "areaCode");
            listProvince = CommonDAO.findByProperty(session, AreaDAO.TABLE_NAME, hashMap);

        } catch (Exception ex) {
            Logger.getLogger(ChassicDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listProvince;
    }

    public String displayNmsVlan() throws Exception {
        try {

            HttpServletRequest req = getRequest();
            String switchId = req.getParameter("switchId");
            if (switchId != null && !switchId.trim().equals("")) {
                String queryString = "select nmsVlan from Switches where switchId = ?";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, switchId.trim());
                List listObject = queryObject.list();
                if (listObject != null && !listObject.isEmpty() && listObject.get(0) != null) {
                    Object object = listObject.get(0).toString();
                    System.out.println("nmsVlan = ");
                    System.out.println(object);
                    this.listElement.put("nmsVlan", object);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            this.listElement.put("nmsVlan", "");
        }

        return "nmsVlan";
    }

    public String updateListChassic() throws Exception {
        try {

            HttpServletRequest req = getRequest();
            String dslamId = req.getParameter("dslamId");
            if (dslamId != null && !dslamId.trim().equals("")) {
                String queryString = "from Dslam where dslamId = ?";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, dslamId.trim());
                List<Dslam> listObject = queryObject.list();
                if (listObject != null && !listObject.isEmpty()) {
                    Object object = listObject.get(0).toString();
                    System.out.println("listChassic = ");
                    System.out.println(listObject.size());
                    this.listElement.put("nmsVlan", object);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            this.listElement.put("nmsVlan", "");
        }

        return "nmsVlan";
    }
}
