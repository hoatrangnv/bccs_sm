/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.EquipmentForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Equipment;
import com.viettel.im.client.bean.EquipmentBean;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 * @author tuan
 * @updated anhlt 05/05/2009
 */
public class EquipmentDAO extends BaseDAOAction {

    public static final String EQUIPMENT_TYPE = "equipmentType";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String EQUIPMENT_VENDOR_ID = "equipmentVendorId";
    public final int SEARCH_RESULT_LIMIT = 1000;

    public Equipment findById(java.lang.Long id) {
        log.debug("getting Equipment instance with id: " + id);
        try {
            Equipment instance = (Equipment) getSession().get(
                    "com.viettel.im.database.BO.Equipment", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Equipment instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Equipment as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            queryString += " order by model." + EquipmentDAO.EQUIPMENT_TYPE  + "model." + EquipmentDAO.NAME ;

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPropertyAndStatus(String propertyName, Object value) {
        log.debug("finding Equipment instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Equipment as model where model." + propertyName + "= ? ";
            queryString += "and model.status = ? ";
            queryString += "order by model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(EquipmentDAO.EQUIPMENT_TYPE)  + ", model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(EquipmentDAO.NAME) ;

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    
    public List findByEquipmentType(Object equipmentType) {
        return findByProperty(EQUIPMENT_TYPE, equipmentType);
    }

    public List findByEquipmentTypeAndStatus(Object equipmentType) {
        return findByPropertyAndStatus(EQUIPMENT_TYPE, equipmentType);
    }

    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    public List findByDescription(Object description) {
        return findByProperty(DESCRIPTION, description);
    }

    public List findByEquipmentVendorId(Object equipmentVendorId) {
        return findByProperty(EQUIPMENT_VENDOR_ID, equipmentVendorId);
    }

    public List findAll() {
        log.debug("finding all Equipment instances");
        try {
            String queryString = "from Equipment";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
    private EquipmentForm equipmentForm = new EquipmentForm();

    public EquipmentForm getEquipmentForm() {
        return equipmentForm;
    }

    public void setEquipmentForm(EquipmentForm equipmentForm) {
        this.equipmentForm = equipmentForm;
    }

    //----------------------------------------------------------------------------------------
    private static final Log log = LogFactory.getLog(EquipmentDAO.class);
    private String pageForward;
    //dinh nghia cac hang so pageForward
    public static final String ADD_NEW_EQUIPMENT = "addEquipment";


    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: PreparePage of DSLAMDAO
     */
    /** ToEditEquipment:    1: Trang thai Edit
     *                      0: default
     *
     *
     */
    public String preparePage() throws Exception {
        log.info("Bắt đầu hàm preparePage của EquipmentDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                EquipmentForm f = getEquipmentForm();
                f.resetForm();

                //
                getList();

                //
                req.getSession().setAttribute("toEditEquipment", 0);

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("Kết thúc hàm preparePage của EquipmentVendorDAO");

        pageForward = ADD_NEW_EQUIPMENT;

        return pageForward;
    }

    private void getList() {
        HttpServletRequest req = getRequest();

        //lay danh sach cac thiet bi da co
        List listEquipment = getListEquipment();
        req.getSession().setAttribute("listEquipment", listEquipment);

        //lay du lieu cho cac combobobx
        req.setAttribute("listEquipmentType", getListEquipmentType());
        req.setAttribute("listEquipmentVendorType", getListEquipmentVendorType());

    }


    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: Get List EquipmentType
     * Modify by TrongLV at 28/05/2010
     */
    public List getListEquipmentType() {
        try {
            List listEquipmentType = new ArrayList();
            String queryString = " from AppParams ";
            List parameterList = new ArrayList();
            queryString += "where status = ? ";
            parameterList.add(String.valueOf(Constant.STATUS_USE));
            queryString += "and type = ? ";
            parameterList.add(Constant.APP_PARAMS_EQUIPMENT_TYPE);
            queryString += "order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            listEquipmentType = queryObject.list();
            return listEquipmentType;
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi khi lấy danh sách thiết bị: " + ex.toString());
            return null;
        }
    }

    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: Get List Equipment Vendor Type
     * Modify by TrongLV at 28/05/2010
     */
    public List getListEquipmentVendorType() {
        try {
            List listEquipmentType = new ArrayList();
            String queryString = " from EquipmentVendor ";
            List parameterList = new ArrayList();
            queryString += "where status = ? order by nlssort(vendorName,'nls_sort=Vietnamese') asc ";
            parameterList.add(Constant.STATUS_USE);
            
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            listEquipmentType = queryObject.list();
            return listEquipmentType;
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Loi khi lấy danh sách nhà cung cấp: " + ex.toString());
            return null;
        }
    }

    public List getListEquipmentInUse() {
        log.debug("finding all Bras instances");
        try {
            String queryString = "from Equipment where not status = 0";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: search Equipment Vendor
     */
    public String searchEquipment() throws Exception {
        EquipmentForm f = getEquipmentForm();
        log.info("Bắt đầu hàm searchEquipment của EquipmentDAO ...");
        try {
            HttpServletRequest req = getRequest();
            List listEquipment = new ArrayList();
            listEquipment = getListEquipment();
            req.getSession().setAttribute("toEditEquipment", 0);
            /*if (listEquipment != null) {
            f.setEquipmentMessage("Tìm thấy " + listEquipment.size() + " kết quả thỏa mãn điều kiện tìm kiếm");
            } else {
            f.setEquipmentMessage("");
            }*/
            req.getSession().setAttribute("listEquipment", listEquipment);
            if (listEquipment.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "search.result");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listEquipment.size());
                req.setAttribute("returnMsgParam", listMessageParam);
            }
            log.info("Kết thúc hàm searchEquipment của EquipmentDAO");
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentMessage("Xuất hiện lỗi khi tìm kiếm nhà cung cấp thiết bị");
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT;
    }

    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: Get List Equipment Vendor
     * Modify by TrongLV at 28/05/2010
     */
    private List<EquipmentBean> getListEquipment() {
        try {
            EquipmentForm f = getEquipmentForm();

            String queryString = "select distinct new com.viettel.im.client.bean.EquipmentBean(a.equipmentId, " +
                    " a.equipmentType, a.code, a.name, a.description, a.equipmentVendorId, a.status, b.vendorName as equipmentVendorName, c.name as equipmentTypeName)" +
                    " from Equipment a, EquipmentVendor b, AppParams c ";
            List parameterList = new ArrayList();
            queryString += "where 1=1";
            queryString += "and a.equipmentVendorId = b.equipmentVendorId ";
            queryString += "and a.equipmentType = c.code ";
            queryString += "and c.type = ? ";
            parameterList.add(Constant.APP_PARAMS_EQUIPMENT_TYPE);
            queryString += "and c.status = ? ";
            parameterList.add(String.valueOf(Constant.STATUS_USE));

            if (f.getCode() != null && !f.getCode().trim().equals("")) {
                queryString += " and upper(a.code) like upper(?) ";
                parameterList.add("%" + f.getCode().trim().toUpperCase() + "%");
            }
            if (f.getName() != null && !f.getName().trim().equals("")) {
                queryString += " and upper(a.name) like upper(?) ";
                parameterList.add("%" + f.getName().trim().toUpperCase() + "%");
            }
            if (f.getEquipmentType() != null && !f.getEquipmentType().trim().equals("")) {
                queryString += " and a.equipmentType = ? ";
                parameterList.add(f.getEquipmentType().trim());
            }
            if (f.getEquipmentVendorId() != null && !f.getEquipmentVendorId().trim().equals("")) {
                queryString += " and a.equipmentVendorId = ? ";
                parameterList.add(Long.parseLong(f.getEquipmentVendorId().trim()));
            }
            if (f.getStatus() != null && !f.getStatus().trim().equals("")) {
                queryString += " and a.status = ? ";
                parameterList.add(Long.parseLong(f.getStatus().trim()));
            }


            if (f.getDescription() != null && !f.getDescription().trim().equals("")) {
                queryString += " and upper(a.description) like ? ";
                parameterList.add("%" + f.getDescription().trim().toUpperCase() + "%");
            }
            queryString += "order by nlssort(b.vendorName,'nls_sort=Vietnamese') asc, ";
            queryString += "nlssort(c.name,'nls_sort=Vietnamese') asc, ";
            queryString += "nlssort(a.name,'nls_sort=Vietnamese') asc ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_RESULT_LIMIT);
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

    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: add new Bank Receipt
     */
    public String addEquipment() throws Exception {
        EquipmentForm f = getEquipmentForm();
        try {
            log.info("Bắt đầu hàm addEquipmentVendor của EquipmentVendorDAO ...");
            HttpServletRequest req = getRequest();
            if (checkValidateToAdd()) {
                Equipment bo = new Equipment();
                f.copyDataToBO(bo);
                Long equipmentId = getSequence("EQUIPMENT_SEQ");
                bo.setEquipmentId(equipmentId);
                getSession().save(bo);
                f.resetForm();
                req.setAttribute("returnMsg", "equipment.add.success");
                getList();
                //f.setEquipmentMessage("Đã thêm giấy nộp tiền mới !");
                req.getSession().setAttribute("listEquipment", getListEquipment());
                log.info("Kết thúc hàm addEquipmentVendor của EquipmentVendorDAO");
            }
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentMessage("Xuất hiện lỗi khi thêm thiết bị");
            log.debug("Lỗi khi them mới: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT;
    }

    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: prepare Edit EquipmentVendor
     */
    public String prepareEditEquipment() throws Exception {
        EquipmentForm f = getEquipmentForm();
        Long equipmentId;
        try {
            log.info("Bắt đầu hàm prepareEditEquipment of EquipmentDAO ...");
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            //req.setAttribute("listEquipment", getListEquipment());
            String strEquipmentId = (String) QueryCryptUtils.getParameter(req, "equipmentId");
            equipmentId = Long.parseLong(strEquipmentId);

//            String queryString = " from Equipment ";
//            List parameterList = new ArrayList();
//            queryString += "where not status = 0 ";
//            queryString += "and equipmentId = ? ";
//            parameterList.add(equipmentId);
//            Query queryObject = getSession().createQuery(queryString);
//            for (int i = 0; i < parameterList.size(); i++) {
//                queryObject.setParameter(i, parameterList.get(i));
//            }
//            if (!queryObject.list().isEmpty()) {
//                f.copyDataFromBO((Equipment) queryObject.list().get(0));
//                session.setAttribute("toEditEquipment", 1);
//            } else {
//                f.resetForm();
//                session.setAttribute("toEditEquipment", 0);
//            }
            //Modified by NamNX 05/09/2009
            Equipment bo = findById(equipmentId);
            f.copyDataFromBO(bo);
            req.getSession().setAttribute("toEditEquipment", 1);
            //NamNX- end

            log.info("Kết thúc hàm prepare prepareEditEquipment của EquipmentDAO");
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentMessage("Xuất hiện lỗi khi chuẩn bị sửa thiết bị");
            log.debug("Lỗi khi thực hiện hàm prepareEditBankReceipt" + ex.toString());
            equipmentId = -1L;
        }
        return ADD_NEW_EQUIPMENT;
    }

    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: Edit equipmentVendor
     */
    public String editEquipment() throws Exception {
        EquipmentForm f = getEquipmentForm();
        try {
            log.info("Bắt đầu hàm editEquipmentcủa EquipmentDAO ...");
            HttpServletRequest req = getRequest();
            if (checkValidateToEdit()) {
                Equipment bo = new Equipment();
                f.copyDataToBO(bo);

                getSession().update(bo);
                req.setAttribute("returnMsg", "equipment.edit.success");
                getList();
                f.resetForm();
                //f.setEquipmentMessage("Đã sửa giấy nộp tiền !");

                req.getSession().setAttribute("toEditEquipment", 0);
                req.getSession().setAttribute("listEquipment", getListEquipment());
                log.info("Kết thúc hàm Edit Equipment của EquipmentDAO");
            }
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentMessage("Xuất hiện lỗi khi sửa thiết bị");
            log.info("Lỗi khi edit danh sách nhà cung cấp thiết bị: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT;
    }

    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: thực hiện hàm deleteEquipmentVendor
     */
    public String deleteEquipment() throws Exception {
        EquipmentForm f = getEquipmentForm();
        try {
            log.info("Bắt đầu hàm delete Equipment của EquipmentDAO ...");
            HttpServletRequest req = getRequest();
            String strEquipmentId = (String) QueryCryptUtils.getParameter(req, "equipmentId");
            Long equipmentId;

            try {
                equipmentId = Long.parseLong(strEquipmentId);
            } catch (Exception ex) {
                equipmentId = -1L;
                log.info("lỗi khi xóa thiết bị: " + ex.toString());
            }
            if (checkValidateToDelete(equipmentId)) {
                Equipment bo = findById(equipmentId);

//                bo.setStatus(0L);
//                getSession().update(bo);

                //modified by NamNX-05/09/2009
                getSession().delete(bo);
                //NamNX - end
                f.resetForm();
                //f.setEquipmentMessage("Đã xoá thiết bị !");
                req.setAttribute("returnMsg", "equipment.delete.success");
            } else {
                req.setAttribute("returnMsg", "equipment.delete.error");

            }
            req.getSession().setAttribute("toEditEquipment", 0);

            req.getSession().setAttribute("listEquipment", getListEquipment());

            log.info("Kết thúc hàm xóa Equipment của EquipmentDAO");
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentMessage("Xuất hiện lỗi khi xóa nhà cung cấp thiết bị");
            log.info("Lỗi khi xóa nhà cung cấp thiết bị: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method page Navigator of EquipmentDAO ...");
        pageForward = "pageNavigator";
        log.info("End method page Navigator of EquipmentDAO");
        return pageForward;
    }

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        EquipmentForm f = getEquipmentForm();

        String code = f.getCode();
        String name = f.getName();
        String type = f.getEquipmentType();
        String vendor = f.getEquipmentVendorId();

        if ((code == null) || code.equals("")) {
            req.setAttribute("returnMsg", "equipment.error.invalidCode");
            return false;
        }
        if (code.trim().indexOf(" ")>=0) {
            req.setAttribute("returnMsg", "MSG.equipment.error.emptyDeviceCode");
            return false;
        }
        
        if ((name == null) || name.equals("")) {
            req.setAttribute("returnMsg", "equipment.error.invalidName");
            return false;
        }
        if ((type == null) || type.equals("")) {
            req.setAttribute("returnMsg", "equipment.error.invalidType");
            return false;
        }
        if ((vendor == null) || vendor.equals("")) {
            req.setAttribute("returnMsg", "equipment.error.invalidVendor");
            return false;
        }

        //trim cac truong can thiet
        f.setCode(code.trim().toUpperCase());
        f.setName(name.trim());
        f.setEquipmentType(type.trim());
        f.setEquipmentVendorId(vendor.trim());

        return true;

    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();
        Long count;
        EquipmentForm f = getEquipmentForm();
        if (checkForm()) {
            //kiem tra co trung ten thiet bi ko
            String strQuery = "select count(*) from Equipment as model where upper(model.code)=?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, f.getCode());
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "equipment.edit.duplicateCode");
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
        EquipmentForm f = getEquipmentForm();
        if (checkForm()) {
            //kiem tra co trung ten thiet bi ko
            String strQuery = "select count(*) from Equipment as model where upper(model.code)=? and not model.equipmentId = ?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, f.getCode());
            q.setParameter(1, f.getEquipmentId());
            count = (Long) q.list().get(0);
            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "equipment.edit.duplicateCode");
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
        strQuery += table;
        strQuery += " as model where model.equipmentId=? and not model.status = 0";

        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, equipmentId);
        //q.setParameter(1, Constant.STATUS_DELETE);
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            //req.setAttribute("returnMsg", "equipment.edit.duplicateCode");
            return false;
        }
        return true;
    }

    private boolean checkValidateToDelete(Long equipmentId) {
        HttpServletRequest req = getRequest();
        Long count;

        //kiem tra xem con ton tai Bras thuoc thiet bi nay khong
        String strQuery = "select count(*) from Bras as model " +
                "where model.equipmentId = ? ";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, equipmentId);
//        q.setParameter(1, Constant.STATUS_DELETE);
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return false;
        }

        //kiem tra xem con ton tai Switches thuoc thiet bi nay khong
        strQuery = "select count(*) from Switches as model " +
                "where model.equipmentId = ? ";
        q = getSession().createQuery(strQuery);
        q.setParameter(0, equipmentId);
//        q.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return false;
        }

        //kiem tra xem con ton tai Card thuoc thiet bi nay khong
        strQuery = "select count(*) from Card as model " +
                "where model.equipmentId = ? ";
        q = getSession().createQuery(strQuery);
        q.setParameter(0, equipmentId);
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return false;
        }

        return true;

    }
}
