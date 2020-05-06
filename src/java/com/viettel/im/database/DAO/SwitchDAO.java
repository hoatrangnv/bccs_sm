 /*
 * @author NamNX
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.SwitchesForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Query;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Bras;
import com.viettel.im.database.BO.Switches;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.client.bean.SwitchesBean;
import com.viettel.im.common.Constant;

/**
 * A data access object (DAO) providing persistence and search support for
 * Switches entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @author MyEclipse Persistence Tools
 */
public class SwitchDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(SwitchDAO.class);
    // property constants
    private String pageForward;
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String STATUS = "status";
    public static final String IP = "ip";
    public static final String NMS_VLAN = "nmsVlan";
    public static final String BRAS_ID = "brasId";
    public static final String EQUIPMENT_ID = "equipmentId";
    public static final String SWITCH = "switch";
    private static final int SEARCH_RESULT_LIMIT = 1000;
    private SwitchesForm switchesForm = new SwitchesForm();
    private Map<String, String> lstBras = new HashMap();

    public SwitchesForm getSwitchesForm() {
        return switchesForm;
    }

    public void setSwitchesForm(SwitchesForm switchesForm) {
        this.switchesForm = switchesForm;
    }

    public void save(Switches transientInstance) {
        log.debug("saving Switch instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Switches persistentInstance) {
        log.debug("deleting Switch instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Switches findById(java.lang.Long id) {
        log.debug("getting Switch instance with id: " + id);
        try {
            Switches instance = (Switches) getSession().get(
                    "com.viettel.im.database.BO.Switches", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Switches instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Switches as model where model." + propertyName + "= ?";
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

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByIp(Object ip) {
        return findByProperty(IP, ip);
    }

    public List findByEquipmentId(Object equipmentId) {
        return findByProperty(EQUIPMENT_ID, equipmentId);
    }

    public List findByBrastId(Object brasId) {
        return findByProperty(BRAS_ID, brasId);
    }

    public List findBrasByEquipmentId(Long equipmentId) {
        log.debug("finding all SwitchByEquipmentId instances");

        try {
            String queryString = "from Bras as model where model.equipmentId=" + equipmentId;
            Query queryObject = getSession().createQuery(queryString);

            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find SwitchByEquipmentId failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all Switches instances");
        try {
            String queryString = "from Switches";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage of SwitchDAO ...");
        HttpServletRequest req = getRequest();

        //
        SwitchesForm f = this.switchesForm;
        f.resetForm();

        //
        req.getSession().setAttribute("toEditSwitch", 0);

        //lay danh sach switch
        getList();

        pageForward = SWITCH;

        log.info("End method preparePage of SwitchDAO");

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of ChassicDAO ...");

        HttpServletRequest req = getRequest();

//        SwitchesForm f = this.switchesForm;

        req.getSession().setAttribute("ToEditChassic", 0);
        pageForward = "switchResult";

        log.info("End method preparePage ChassicDAO");

        return pageForward;
    }

    public String deleteSwitch() throws Exception {
        log.info("Begin method deleteSwitch of SwitchDAO ...");

        HttpServletRequest req = getRequest();

        SwitchesForm f = this.switchesForm;

        String strSwitchId = (String) QueryCryptUtils.getParameter(req, "switchId");
        Long switchId;
        try {
            switchId = Long.parseLong(strSwitchId);
        } catch (Exception ex) {
            switchId = -1L;
        }

        Switches bo = findById(switchId);
        try {
            getSession().delete(bo);
            req.setAttribute("returnMsg", "switch.delete.success");
        } catch (Exception ex) {
            req.setAttribute("returnMsg", "switch.delete.failed");
        }
//        bo.setStatus("0");
//        getSession().update(bo);
        f.resetForm();
        getList();
        List listSwitch = new ArrayList();
        listSwitch = getListSwitches();
        req.getSession().removeAttribute("listSwitch");
        req.getSession().setAttribute("listSwitch", listSwitch);
        req.getSession().setAttribute("ToEditSwitch", 0);
        req.setAttribute("returnMsg", "switch.delete.success");

        pageForward = SWITCH;
        log.info("End method deleteSwitch of SwitchDAO");

        return pageForward;
    }

    public String searchSwitch() throws Exception {
        log.info("Begin method searchSwitch of SwitchDAO ...");
        HttpServletRequest req = getRequest();
        List listSwitch = new ArrayList();
        listSwitch = getListSwitches();
        req.getSession().removeAttribute("listSwitch");
        req.getSession().setAttribute("listSwitch", listSwitch);
        req.getSession().setAttribute("ToEditSwitch", 0);
        getList();
        if (listSwitch.size() > 999) {
            req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
        } else {
            req.setAttribute("returnMsg", "search.result");
            List listMessageParam = new ArrayList();
            listMessageParam.add(listSwitch.size());
            req.setAttribute("returnMsgParam", listMessageParam);
        }

        pageForward = SWITCH;
        log.info("End method searchSwitch of SwitchDAO");
        return pageForward;
    }

    private List<SwitchesBean> getListSwitches() {
        try {
            SwitchesForm f = getSwitchesForm();

            String queryString = "select distinct new com.viettel.im.client.bean.SwitchesBean(a.switchId, "
                    + " a.equipmentId, a.name, a.code, a.ip, a.status, a.brasId, b.name as equipmentName, c.name as brasName, d.vendorName as vendorName, a.nmsVlan as nmsVlan) "
                    + " from Switches a, Equipment b, Bras c, EquipmentVendor d ";
            List parameterList = new ArrayList();
            queryString += "where a.equipmentId = b.equipmentId ";
            queryString += "and a.brasId = c.brasId ";
            queryString += "and b.equipmentVendorId = d.equipmentVendorId ";
//            queryString += "and a.status <> ? ";
//            parameterList.add(String.valueOf(Constant.STATUS_DELETE));


            if (f.getName() != null && !f.getName().trim().equals("")) {
                queryString += " and upper(a.name) like ? ";
                parameterList.add("%" + f.getName().trim().toUpperCase() + "%");
            }
            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                queryString += " and upper(a.code) = ? ";
                parameterList.add(f.getCode().trim().toUpperCase());
            }
            if (f.getEquipmentId() != null && !f.getEquipmentId().equals("")) {
                queryString += " and a.equipmentId = ? ";
                parameterList.add(Long.parseLong(f.getEquipmentId()));
            }
            if (f.getBrasId() != null && !f.getBrasId().equals("")) {
                queryString += " and a.brasId = ? ";
                parameterList.add(Long.parseLong(f.getBrasId()));
            }
            if (f.getStatus() != null) {
                queryString += " and a.status = ? ";
                parameterList.add(f.getStatus());
            }


            if (f.getIp() != null && !f.getIp().trim().equals("")) {
                queryString += " and upper(a.ip) like ? ";
                parameterList.add("%" + f.getIp().trim().toUpperCase() + "%");
            }

            queryString += "order by nlssort(d.vendorName,'nls_sort=Vietnamese') asc, ";
            queryString += "nlssort(b.name,'nls_sort=Vietnamese') asc, ";
            queryString += "nlssort(a.code,'nls_sort=Vietnamese') asc ";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT);

            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            return queryObject.list();

        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            log.debug("Loi: " + ex.toString());
            return null;
        }
    }

    public String prepareEditSwitch() throws Exception {
        log.info("Begin method prepareEditSwitch of SwitchDAO ...");

        HttpServletRequest req = getRequest();

        //
        getList();

        SwitchesForm f = this.switchesForm;
        String strSwitchId = (String) QueryCryptUtils.getParameter(req, "switchId");
        Long switchId;
        try {
            switchId = Long.parseLong(strSwitchId);
        } catch (Exception ex) {
            switchId = -1L;
        }
        Switches bo = findById(switchId);
        f.copyDataFromBO(bo);

        req.getSession().setAttribute("toEditSwitch", 1);

        pageForward = SWITCH;

        log.info("End method prepareEditSwitch of SwitchDAO");

        return pageForward;
    }

    /**
     * @return the lstBras
     */
    public Map<String, String> getLstBras() {
        return lstBras;
    }

    /**
     * @param lstBras the lstBras to set
     */
    public void setLstBras(Map<String, String> lstBras) {
        this.lstBras = lstBras;
    }

    private void getList() {
        HttpServletRequest req = getRequest();

        //lay danh sach switches da co
        List listSwitch = new ArrayList();
        listSwitch = getListSwitches();
        req.getSession().removeAttribute("listSwitch");
        req.getSession().setAttribute("listSwitch", listSwitch);

        //lay danh sach bras
        BrasDAO bras = new BrasDAO();
        bras.setSession(this.getSession());
        List listBras = bras.findByProperty(BrasDAO.STATUS, Constant.STATUS_USE);
        req.setAttribute("lstBras", listBras);

        //lay danh sach cac thiet bi
        List lstEquipment = getListEquipment();
        req.setAttribute("lstEquipment", lstEquipment);



    }

    private List getListEquipment() {
        HttpServletRequest req = getRequest();

        //
        String queryString = "select new com.viettel.im.client.bean.EquipmentBean(a.equipmentId, a.name, b.vendorName) "
                + "from Equipment a, EquipmentVendor b "
                + "where a.equipmentType = ? "
                + "and a.equipmentVendorId = b.equipmentVendorId "
                + "order by nlssort(b.vendorName,'nls_sort=Vietnamese') asc, "
                + "nlssort(a.name,'nls_sort=Vietnamese') asc ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, Constant.EQUIPMENT_TYPE_SWITCHES);
//        queryObject.setParameter(1, Constant.STATUS_USE);
        List lstEquipment = queryObject.list();


        return lstEquipment;

    }

    public String addSwitch() throws Exception {
        SwitchesForm f = getSwitchesForm();
        try {
            log.info("Bắt đầu hàm  ...");
            HttpServletRequest req = getRequest();



            if (checkValidateToAdd()) {
                Switches bo = new Switches();
                f.copyDataToBO(bo);

                Long id = getSequence("SWITCH_SEQ");
                bo.setSwitchId(id);
                getSession().save(bo);

                f.resetForm();

                req.setAttribute("returnMsg", "switch.add.success");

                log.info("kết thúc");
            }

        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentMessage("Xu?t hi?n l?i khi thêm thi?t b?");
            log.debug("L?i khi them m?i: " + ex.toString());
        }

        //lay danh sach
        getList();

        pageForward = SWITCH;
        log.info("End method addSwitch of BrasDAO");
        return pageForward;
    }

    public String editSwitch() throws Exception {
        log.info("Begin ...");

        HttpServletRequest req = getRequest();
        SwitchesForm f = getSwitchesForm();



        // Edit
        if (checkValidateToEdit()) {
            Switches bo = new Switches();
            f.copyDataToBO(bo);
            Long id = f.getSwitchId();
            bo.setSwitchId(id);
            getSession().update(bo);
            f.resetForm();
            req.getSession().setAttribute("toEditSwitch", 0);

            //
            req.setAttribute("returnMsg", "switch.edit.success");
        }

        //lay lai list
        getList();

        pageForward = SWITCH;
        log.info("End method editSwitch of BrasDAO");
        return pageForward;
    }//end of editBras

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        SwitchesForm f = getSwitchesForm();
        String name = f.getName().trim();
        String code = f.getCode().trim();
        String bras = f.getBrasId().trim();
        String eq = f.getEquipmentId().trim();
        String ip = f.getIp().trim();
        Long nmsVlan = f.getNmsVlan();

        if ((code == null) || code.equals("")) {
            req.setAttribute("returnMsg", "switch.error.invalidCode");
            return false;
        }
        if (code.indexOf(" ") >= 0) {
            req.setAttribute("returnMsg", "switch.error.SpaceDecideCode");
            return false;
        }

        if ((name == null) || name.equals("")) {
            req.setAttribute("returnMsg", "switch.error.SpaceDecideCode");
            return false;
        }

        if ((bras == null) || bras.equals("")) {
            req.setAttribute("returnMsg", "switch.error.invalidBras");
            return false;
        }
        if ((eq == null) || eq.equals("")) {
            req.setAttribute("returnMsg", "switch.error.invalidEquipment");
            return false;
        }
        if ((ip == null) || ip.equals("")) {
            req.setAttribute("returnMsg", "switch.error.invalidIp");
            return false;
        }

        if (nmsVlan == null)  {
            req.setAttribute("returnMsg", "switch.error.invalidNmsVlan");
            return false;
        }

        return true;

    }

    public List getListSwitchInUse() {
        log.debug("finding all switch instances");
        try {
            String queryString = "from Switches where not status = 0";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();
        Long count;
        SwitchesForm f = getSwitchesForm();
        String code = f.getCode().trim();

        if (checkForm()) {
            String strQuery = "select count(*) from Switches as model where upper(model.code)=?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.toUpperCase());
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "switch.add.duplicateCode");
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
        SwitchesForm f = getSwitchesForm();
        String code = f.getCode();
        Long id = f.getSwitchId();
        if (checkForm()) {

            String strQuery = "select count(*) from Switches as model where upper(model.code)=? and not model.switchId = ?";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code.trim().toUpperCase());
            q.setParameter(1, id);
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "switch.edit.duplicateCode");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
