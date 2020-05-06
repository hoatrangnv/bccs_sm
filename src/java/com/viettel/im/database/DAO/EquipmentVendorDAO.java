/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.EquipmentVendorForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.EquipmentVendor;
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
public class EquipmentVendorDAO extends BaseDAOAction {

    private EquipmentVendorForm equipmentVendorForm = new EquipmentVendorForm();
    public final int SEARCH_RESULT_LIMIT = 100;
    public final int SEARCH_MAX_RECORD = 1000;

    public EquipmentVendorForm getEquipmentVendorForm() {
        return equipmentVendorForm;
    }

    public void setEquipmentVendorForm(EquipmentVendorForm equipmentVendorForm) {
        this.equipmentVendorForm = equipmentVendorForm;
    }
    //----------------------------------------------------------------------------------------
    private static final Log log = LogFactory.getLog(EquipmentVendorDAO.class);
    private String pageForward;
    //dinh nghia cac hang so pageForward
    public static final String ADD_NEW_EQUIPMENT_VENDOR = "addEquipmentVendor";


    /*Author: DucDD
     * Date created: 11/05/2009
     * Purpose: PreparePage of EquipmentVendorDAO
     */
    public String preparePage() throws Exception {
        log.info("Bắt đầu hàm preparePage của EquipmentVendorDAO ...");
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        if (userToken != null) {
            try {
                EquipmentVendorForm f = getEquipmentVendorForm();
                if (f != null) {
                    f.resetForm();
                }
                List listEquipmentVendor = getListEquipmentVendor();
                req.getSession().setAttribute("listEquipmentVendor", listEquipmentVendor);
                pageForward = ADD_NEW_EQUIPMENT_VENDOR;
                req.getSession().setAttribute("toEditEquipmentVendor", 0);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }
        log.info("Kết thúc hàm preparePage của EquipmentVendorDAO");
        return pageForward;
    }

    /*Author: DucDD
     * Date created: 11/05/2009
     * Purpose: search Equipment Vendor
     */
    public String searchEquipmentVendor() throws Exception {
        EquipmentVendorForm f = getEquipmentVendorForm();
        log.info("Bắt đầu hàm searchEquipmentVendor của EquipmentVendorDAO ...");
        try {
            HttpServletRequest req = getRequest();
            List listEquipmentVendor = new ArrayList();
            listEquipmentVendor = getListEquipmentVendor();
            req.getSession().setAttribute("toEditEquipmentVendor", 0);
            req.getSession().setAttribute("listEquipmentVendor", listEquipmentVendor);
            if (listEquipmentVendor.size() > 999) {
                req.setAttribute("returnMsg", "MSG.FAC.Search.List1000");
            } else {
                req.setAttribute("returnMsg", "search.result");
                List listMessageParam = new ArrayList();
                listMessageParam.add(listEquipmentVendor.size());
                req.setAttribute("returnMsgParam", listMessageParam);
            }
            log.info("Kết thúc hàm searchEquipmentVendor của EquipmentVendorDAO");
        } catch (Exception ex) {
            getSession().clear();
            // f.setEquipmentVendorMessage("Xuất hiện lỗi khi tìm kiếm nhà cung cấp thiết bị");
            log.debug("Lỗi khi tìm kiếm: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT_VENDOR;
    }

    public List getListEquipmentVendorInUse() {
        log.debug("finding all EquipmentVendor instances");
        try {
            String queryString = "from EquipmentVendor ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }


    /*Author: DucDD
     * Date created: 18/04/2009
     * Purpose: Get List Equipment Vendor
     * Modify by TrongLV at 28/05/2010
     */
    private List<EquipmentVendor> getListEquipmentVendor() {
        try {
            EquipmentVendorForm f = getEquipmentVendorForm();

            String queryString = " from EquipmentVendor";
            List parameterList = new ArrayList();

            queryString += " where 1=1 ";

            if (f.getVendorCode() != null && !f.getVendorCode().trim().equals("")) {
                queryString += " and upper(vendorCode) like ? ";
                parameterList.add("%" + f.getVendorCode().trim().toUpperCase() + "%");
            }

            if (f.getVendorName() != null && !f.getVendorName().trim().equals("")) {
                queryString += " and upper(vendorName) like ? ";
                parameterList.add("%" + f.getVendorName().trim().toUpperCase() + "%");
            }
            
             if (f.getStatus() != null) {
                queryString += " and status = ? ";
                parameterList.add(f.getStatus());
            }


            if (f.getDescription() != null && !f.getDescription().trim().equals("")) {
                queryString += " and upper(description) like ? ";
                parameterList.add("%" + f.getDescription().trim().toUpperCase() + "%");
            }

            queryString += "order by vendorCode, nlssort(vendorName,'nls_sort=Vietnamese') asc ";

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setMaxResults(SEARCH_MAX_RECORD);
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
     * Date created: 11/05/2009
     * Purpose: add new Bank Receipt
     */
    public String addEquipmentVendor() throws Exception {
        EquipmentVendorForm f = getEquipmentVendorForm();
        try {
            log.info("Bắt đầu hàm addEquipmentVendor của EquipmentVendorDAO ...");
            HttpServletRequest req = getRequest();
            if (checkValidateToAdd()) {
                EquipmentVendor bo = new EquipmentVendor();
                f.copyDataToBO(bo);
                Long equipmentVendorId = getSequence("EQUIPMENT_VENDOR_SEQ");
                bo.setEquipmentVendorId(equipmentVendorId);
                getSession().save(bo);
                f.resetForm();
                //f.setEquipmentVendorMessage("Đã thêm giấy nộp tiền mới !");
                req.setAttribute("returnMsg", "equipmentVendor.add.success");
                req.getSession().setAttribute("listEquipmentVendor", getListEquipmentVendor());
                log.info("Kết thúc hàm addEquipmentVendor của EquipmentVendorDAO");
            }
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentVendorMessage("Xuất hiện lỗi khi thêm nhà cung cấp");
            log.debug("Lỗi khi them mới: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT_VENDOR;
    }

    /*Author: DucDD
     * Date created: 11/05/2009
     * Purpose: prepare Edit EquipmentVendor
     */
    public String prepareEditEquipmentVendor() throws Exception {
        EquipmentVendorForm f = getEquipmentVendorForm();
        Long equipmentVendorId;
        try {
            log.info("Bắt đầu hàm prepareEditEquipmentVendor of EquipmentVendorDAO ...");
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            //req.setAttribute("listEquipmentVendor", getListEquipmentVendor());
            String strEquipmentVendorId = (String) QueryCryptUtils.getParameter(req, "equipmentVendorId");
            equipmentVendorId = Long.parseLong(strEquipmentVendorId);
//            String queryString = " from EquipmentVendor ";
//            List parameterList = new ArrayList();
//            queryString += "where 1=1";
//            queryString += "and equipmentVendorId = ? ";
//            parameterList.add(equipmentVendorId);
//            Query queryObject = getSession().createQuery(queryString);
//            for (int i = 0; i < parameterList.size(); i++) {
//                queryObject.setParameter(i, parameterList.get(i));
//            }
//            if (!queryObject.list().isEmpty()) {
//                f.copyDataFromBO((EquipmentVendor) queryObject.list().get(0));
//                session.setAttribute("toEditEquipmentVendor", 1);
//            } else {
//                f.resetForm();
//                session.setAttribute("toEditEquipmentVendor", 0);
//            }
            //Modified by NamNX 05/09/2009
            EquipmentVendor bo = findById(equipmentVendorId);
            f.copyDataFromBO(bo);
            req.getSession().setAttribute("toEditEquipmentVendor", 1);
            //NamNX- end
            log.info("Kết thúc hàm prepare prepareEditBankReceipt của EquipmentVendorDAO");
        } catch (Exception ex) {
            getSession().clear();
            log.debug("Lỗi khi thực hiện hàm prepareEditEquipmentVendor" + ex.toString());
            equipmentVendorId = -1L;
        }
        return ADD_NEW_EQUIPMENT_VENDOR;
    }

    /*Author: DucDD
     * Date created: 11/05/2009
     * Purpose: Edit equipmentVendor
     */
    public String editEquipmentVendor() throws Exception {
        EquipmentVendorForm f = getEquipmentVendorForm();
        try {
            log.info("Bắt đầu hàm editEquipmentVendor của EquipmentVendorDAO ...");
            HttpServletRequest req = getRequest();
            if (checkValidateToEdit()) {
                EquipmentVendor bo = new EquipmentVendor();
                f.copyDataToBO(bo);
                getSession().update(bo);
                f.resetForm();
                //f.setEquipmentVendorMessage("Đã sửa giấy nộp tiền !");

                req.getSession().setAttribute("toEditEquipmentVendor", 0);
                req.setAttribute("returnMsg", "equipmentVendor.edit.success");

                req.getSession().setAttribute("listEquipmentVendor", getListEquipmentVendor());
                log.info("Kết thúc hàm Edit EquipmentVendor của EquipmentVendorDAO");
            }
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentVendorMessage("Xuất hiện lỗi khi sửa nhà cung cấp thiết bị");
            log.info("Lỗi khi edit danh sách nhà cung cấp thiết bị: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT_VENDOR;
    }

    /*Author: DucDD
     * Date created: 12/05/2009
     * Purpose: thực hiện hàm deleteEquipment
     */
    public String deleteEquipment() throws Exception {
        EquipmentVendorForm f = getEquipmentVendorForm();
        try {
            log.info("Bắt đầu hàm delete EquipmentVendor của EquipmentVendorDAO ...");
            HttpServletRequest req = getRequest();
            String strEquipmentVendorId = (String) QueryCryptUtils.getParameter(req, "equipmentVendorId");
            Long equipmentVendorId;

            try {
                equipmentVendorId = Long.parseLong(strEquipmentVendorId);
            } catch (Exception ex) {
                equipmentVendorId = -1L;
                log.info("lỗi khi xóa nhà cung cấp thiết bị: " + ex.toString());
            }
            if (checkValidateToDelete(equipmentVendorId)) {
                EquipmentVendor bo = findById(equipmentVendorId);
                getSession().delete(bo);

                f.resetForm();
                //f.setEquipmentVendorMessage("Đã xoá nhà cung cấp !");
                req.setAttribute("returnMsg", "equipmentVendor.delete.success");
            } else {
                req.setAttribute("returnMsg", "equipmentVendor.delete.error");

            }
            req.getSession().setAttribute("toEditEquipmentVendor", 0);
            req.getSession().setAttribute("listEquipmentVendor", getListEquipmentVendor());
            log.info("Kết thúc hàm xóa EquipmentVendor của EquipmentVendorDAO");
        } catch (Exception ex) {
            getSession().clear();
            //f.setEquipmentVendorMessage("Xuất hiện lỗi khi xóa nhà cung cấp thiết bị");
            log.info("Lỗi khi xóa nhà cung cấp thiết bị: " + ex.toString());
        }
        return ADD_NEW_EQUIPMENT_VENDOR;
    }

    /**
     * author ducdd
     * date: 11/05/2009
     * ham lay nhà cung cấp thiết bị
     */
    public EquipmentVendor findById(
            java.lang.Long id) {
        log.debug("getting EquipmentVendor instance with id: " + id);
        try {
            EquipmentVendor instance = (EquipmentVendor) getSession().get(
                    "com.viettel.im.database.BO.EquipmentVendor", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method page Navigator of DocDepositDAO ...");
        pageForward = "pageNavigator";
        log.info("End method page Navigator of DocDepositDAO");
        return pageForward;
    }

    private boolean checkForm() {
        HttpServletRequest req = getRequest();
        EquipmentVendorForm f = getEquipmentVendorForm();
        String code = f.getVendorCode().trim();
        if ((code == null) || code.equals("")) {
            req.setAttribute("returnMsg", "MSG.INF.EquipmentVendor.SupplierEmpty");
            return false;
        }
        String name = f.getVendorName().trim();
        if ((name == null) || name.equals("")) {
            req.setAttribute("returnMsg", "equipmentVendor.error.invalidName");
            return false;
        }

        return true;

    }

    private boolean checkValidateToAdd() {
        HttpServletRequest req = getRequest();
        Long count;
        EquipmentVendorForm f = getEquipmentVendorForm();

        String code = f.getVendorCode();
        if (code != null){
            code = code.trim();
        }else{
            code = "";            
        }        
        f.setVendorCode(code);
        if (code.trim().equals("")){
            req.setAttribute("returnMsg", "MSG.INF.EquipmentVendor.SupplierEmpty");
            return false;
        }        

        String name = f.getVendorName();
        if (name != null){
            name = name.trim();            
        }else{
            name = "";
        }
        f.setVendorName(name);
        
        if (checkForm()) {
            String strQuery = "select count(*) from EquipmentVendor as model where model.vendorCode=?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code);
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "MSG.INF.EquipmentVendor.SupplierDupilicate");
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
        EquipmentVendorForm f = getEquipmentVendorForm();
        String code = f.getVendorCode();
        if (code != null){
            code = code.trim();
        }else{
            code = "";
        }
        f.setVendorCode(code);
        if (code.trim().equals("")){
            req.setAttribute("returnMsg", "MSG.INF.EquipmentVendor.SupplierEmpty");
            return false;
        }

        String name = f.getVendorName();
        if (name != null){
            name = name.trim();
        }else{
            name = "";
        }
        f.setVendorName(name);
        
        Long id = f.getEquipmentVendorId();
        if (checkForm()) {

            String strQuery = "select count(*) from EquipmentVendor as model where model.vendorCode=? and not model.equipmentVendorId = ?";

            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, code);
            q.setParameter(1, id);
            count = (Long) q.list().get(0);

            if ((count != null) && (count.compareTo(0L) > 0)) {
                req.setAttribute("returnMsg", "MSG.INF.EquipmentVendor.SupplierDupilicate");
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean checkContraint(String table, Long equipmentVendorId) {
        HttpServletRequest req = getRequest();
        Long count;
        String strQuery = "select count(*) from ";
        strQuery += table;
        strQuery += " as model where model.equipmentVendorId = ?";

        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, equipmentVendorId);
        //q.setParameter(1, Constant.STATUS_DELETE);
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            //req.setAttribute("returnMsg", "equipment.edit.duplicateName");
            return false;
        }
        return true;
    }

    private boolean checkValidateToDelete(Long equipmentVendorId) {
        return checkContraint("Equipment", equipmentVendorId);
    }
}
