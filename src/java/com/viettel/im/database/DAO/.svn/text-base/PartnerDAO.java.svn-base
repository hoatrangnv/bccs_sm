package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.form.PartnerForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.Partner;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * Partner entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.Partner
 * @author MyEclipse Persistence Tools
 */
public class PartnerDAO extends BaseDAOAction {
    //----------------------------------------------------------------------------------------
    //bien form

    private PartnerForm partnerForm = new PartnerForm();

    public PartnerForm getPartnerForm() {
        return partnerForm;
    }

    public void setPartnerForm(PartnerForm partnerForm) {
        this.partnerForm = partnerForm;
    }
    //----------------------------------------------------------------------------------------
    private static final Log log = LogFactory.getLog(PartnerDAO.class);
    private String pageForward;
    // property constants
    public static final String PARTNER_NAME = "partnerName";
    public static final String PARTNER_TYPE = "partnerType";
    public static final String ADDRESS = "address";
    public static final String PHONE = "phone";
    public static final String FAX = "fax";
    public static final String CONTACT_NAME = "contactName";
    public static final String STATUS = "status";
    public static final String ADD_NEW_PARTNER = "addNewPartner";

    public void save(Partner transientInstance) {
        log.debug("saving Partner instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Partner persistentInstance) {
        log.debug("deleting Partner instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Partner findById(Long id) {
        log.debug("getting Partner instance with id: " + id);
        try {
            Partner instance = (Partner) getSession().get(
                    "com.viettel.im.database.BO.Partner", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Partner instance) {
        log.debug("finding Partner instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.Partner").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Partner instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from Partner as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByPartnerName(Object partnerName) {
        return findByProperty(PARTNER_NAME, partnerName);
    }

    public List findByPartnerType(Object partnerType) {
        return findByProperty(PARTNER_TYPE, partnerType);
    }

    public List findByAddress(Object address) {
        return findByProperty(ADDRESS, address);
    }

    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
    }

    public List findByFax(Object fax) {
        return findByProperty(FAX, fax);
    }

    public List findByContactName(Object contactName) {
        return findByProperty(CONTACT_NAME, contactName);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all Partner instances");
        try {
            String queryString = "from Partner order by nlssort(partnerCode,'nls_sort=Vietnamese') asc";
            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, Constant.STATUS_DELETE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Partner merge(Partner detachedInstance) {
        log.debug("merging Partner instance");
        try {
            Partner result = (Partner) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Partner instance) {
        log.debug("attaching dirty Partner instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Partner instance) {
        log.debug("attaching clean Partner instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: PreparePage of addNewPartner
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of PartnerDAO ...");

        HttpServletRequest req = getRequest();
        PartnerForm f = this.partnerForm;

        f.resetForm();
        List listPartner = new ArrayList();
        listPartner = findAll();
        req.getSession().removeAttribute("listPartner");
        req.getSession().setAttribute("listPartner", listPartner);
        req.getSession().setAttribute("toEditPartner", 0);

        pageForward = ADD_NEW_PARTNER;

        log.info("End method preparePage of PartnerDAO");

        return pageForward;
    }

    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: addNewPartner tao moi Partner
     */
    public String addNewPartner() throws Exception {
        log.info("Begin method preparePage of PartnerDAO ...");

        HttpServletRequest req = getRequest();
        PartnerForm f = this.partnerForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();

        } else {


            if (checkValidateToAdd()) {
                Partner bo = new Partner();
                f.copyDataToBO(bo);
                Long partnerId = getSequence("PARTNER_SEQ");
                bo.setPartnerId(partnerId);
                save(bo);

                f.resetForm();
                //f.setMessage("Đã thêm đối tác mới !");
                req.setAttribute("returnMsg", "partner.add.success");
                List listPartner = new ArrayList();
                listPartner = findAll();
                req.getSession().removeAttribute("listPartner");
                req.getSession().setAttribute("listPartner", listPartner);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(null, bo));
                saveLog(Constant.ACTION_ADD_PARTNER, "Thêm mới đối tác", lstLogObj, bo.getPartnerId());
            }
//            select count(*) from Partner where partnerName = strName and ;
//            select count(*) from Partner where partnerName = strName and partnerId <> ;





        }

        pageForward = ADD_NEW_PARTNER;

        log.info("End method preparePage of PartnerDAO");

        return pageForward;
    }

    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of PartnerDAO ...");
        pageForward = "pageNavigator";
        log.info("End method preparePage of PartnerDAO");

        return pageForward;
    }
    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: prepagePage of editPartner
     */

    public String prepareEditPartner() throws Exception {
        log.info("Begin method preparePage of PartnerDAO ...");

        HttpServletRequest req = getRequest();
        PartnerForm f = this.partnerForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.getSession().setAttribute("toEditPartner", 0);
        } else {
            String strPartnerId = (String) QueryCryptUtils.getParameter(req, "partnerId");
            Long partnerId;
            try {
                partnerId = Long.parseLong(strPartnerId);
            } catch (Exception ex) {
                partnerId = -1L;
            }

            Partner bo = findById(partnerId);
            f.copyDataFromBO(bo);
            f.setMessage("");

            req.getSession().setAttribute("toEditPartner", 1);
        }

        pageForward = ADD_NEW_PARTNER;

        log.info("End method preparePage of PartnerDAO");

        return pageForward;
    }

    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: editPartner luu lai thay doi
     */
    public String editPartner() throws Exception {
        log.info("Begin method preparePage of PartnerDAO ...");

        HttpServletRequest req = getRequest();
        PartnerForm f = this.partnerForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.getSession().setAttribute("toEditPartner", 0);
        } else {


            if (checkValidateToEdit()) {
                Partner bo = findById(f.getPartnerId());
                Partner oldPartner = new Partner();
                BeanUtils.copyProperties(oldPartner, bo);
                f.copyDataToBO(bo);
                getSession().update(bo);

                f.resetForm();
                //f.setMessage("Đã sửa thông tin đối tác !");
                req.setAttribute("returnMsg", "partner.edit.success");
                req.getSession().setAttribute("toEditPartner", 0);

                List listPartner = new ArrayList();
                listPartner = findAll();
                req.getSession().removeAttribute("listPartner");
                req.getSession().setAttribute("listPartner", listPartner);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(oldPartner, bo));
                saveLog(Constant.ACTION_UPDATE_PARTNER, "Cập nhật đối tác", lstLogObj, bo.getPartnerId());
            }


        }

        pageForward = ADD_NEW_PARTNER;

        log.info("End method preparePage of PartnerDAO");

        return pageForward;
    }

    /*Author: NamNX
     * Date created: 20/03/2009
     * Purpose: deletePartner xoa doi tac
     */
    public String deletePartner() throws Exception {
//        log.info("Begin method preparePage of PartnerDAO ...");
//
//        HttpServletRequest req = getRequest();
//        PartnerForm f = this.partnerForm;
//        String ajax = QueryCryptUtils.getParameter(req, "ajax");
//        if ("1".equals(ajax)) {
//            f.resetForm();
//            req.getSession().setAttribute("toEditPartner", 0);
//        } else {
//            String strPartnerId = (String) QueryCryptUtils.getParameter(req, "partnerId");
//            Long partnerId;
//            try {
//                partnerId = Long.parseLong(strPartnerId);
//            } catch (Exception ex) {
//                partnerId = -1L;
//            }
//
//            Partner bo = findById(partnerId);
//            bo.setStatus(Constant.STATUS_DELETE);
//            getSession().update(bo);
//
//            f.resetForm();
//            //f.setMessage("Đã xoá đối tác!");
//            req.setAttribute("returnMsg", "partner.delete.success");
//            req.getSession().setAttribute("toEditPartner", 0);
//
//            List listPartner = new ArrayList();
//            listPartner = findAll();
//            req.getSession().removeAttribute("listPartner");
//            req.getSession().setAttribute("listPartner", listPartner);
//        }
//        pageForward = ADD_NEW_PARTNER;
//
//        log.info("End method preparePage of PartnerDAO");
//
//        return pageForward;

// Modified by TheTM --Start--
        log.info("Begin method preparePage of PartnerDAO ...");
        HttpServletRequest req = getRequest();
        PartnerForm f = this.partnerForm;
        pageForward = ADD_NEW_PARTNER;
        try {
            String strPartnerId = (String) QueryCryptUtils.getParameter(req, "partnerId");
            Long partnerId;
            try {
                partnerId = Long.parseLong(strPartnerId);
            } catch (Exception ex) {
                partnerId = -1L;
            }
            Partner bo = findById(partnerId);
            if (bo == null) {
                req.setAttribute("returnMsg", "partner.delete.notExistBO");
                log.info("End method preparePage of PartnerDAO");
                return pageForward;
            }
            getSession().delete(bo);
            f.resetForm();
            req.setAttribute("returnMsg", "partner.delete.success");
            req.getSession().setAttribute("toEditPartner", 0);
            List listPartner = new ArrayList();
            listPartner = findAll();
            req.getSession().removeAttribute("listPartner");
            req.getSession().setAttribute("listPartner", listPartner);
            List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
            lstLogObj.add(new ActionLogsObj(bo, null));
            saveLog(Constant.ACTION_DELETE_PARTNER, "Xóa đối tác", lstLogObj, bo.getPartnerId());

        } catch (Exception ex) {
            req.setAttribute("returnMsg", "partner.delete.error");
            log.info("End method preparePage of PartnerDAO");
            return pageForward;
        }
        log.info("End method preparePage of PartnerDAO");
        return pageForward;
    }
// Modified by TheTM --End--

    /*Author: NamNX
     * Date created: 30/03/2009
     * Purpose: tim kiem Partner
     */
    public String searchPartner() throws Exception {
        log.info("Begin method searchPartner of PartnerDAO ...");

        HttpServletRequest req = getRequest();
        PartnerForm f = this.partnerForm;
        String ajax = QueryCryptUtils.getParameter(req, "ajax");
        if ("1".equals(ajax)) {
            f.resetForm();
            req.getSession().setAttribute("toEditPartner", 0);
        } else {
            List listParameter = new ArrayList();

            String strQuery = "from Partner as model where 1=1";
            if (f.getStatus() != null && !f.getStatus().trim().equals("")) {
                listParameter.add(Long.parseLong(f.getStatus()));
                strQuery += " and model.status = ? ";
            }

            if (f.getPartnerName() != null && !f.getPartnerName().trim().equals("")) {
                listParameter.add("%" + f.getPartnerName().trim().toUpperCase() + "%");
                strQuery += " and upper(model.partnerName) like ? ";
            }
//            if (f.getContactName() != null && !f.getContactName().trim().equals("")) {
//                listParameter.add("%" + f.getContactName().trim().toUpperCase() + "%");
//                strQuery += " and upper(model.contactName) like ? ";
//            }
            if (f.getAddress() != null && !f.getAddress().trim().equals("")) {
                listParameter.add("%" + f.getAddress().trim().toUpperCase() + "%");
                strQuery += " and upper(model.address) like ? ";
            }
            if (f.getPartnerCode() != null && !f.getPartnerCode().trim().equals("")) {
                listParameter.add(f.getPartnerCode().trim().toUpperCase());
                strQuery += " and upper(model.partnerCode) = ? ";
            }
            if (f.getFax() != null && !f.getFax().trim().equals("")) {
                listParameter.add("%" + f.getFax().trim().toUpperCase() + "%");
                strQuery += " and upper(model.fax) like ? ";
            }
            if (f.getPhone() != null && !f.getPhone().trim().equals("")) {
                listParameter.add("%" + f.getPhone().trim().toUpperCase() + "%");
                strQuery += " and upper(model.phone) like ? ";
            }

            if (f.getPartnerType() != null && !f.getPartnerType().trim().equals("")) {
                listParameter.add(Long.parseLong(f.getPartnerType()));
                strQuery += " and model.partnerType = ? ";
            }

            strQuery += "order by nlssort(partnerName,'nls_sort=Vietnamese') asc";
            Query q = getSession().createQuery(strQuery);
            for (int i = 0; i < listParameter.size(); i++) {
                q.setParameter(i, listParameter.get(i));
            }

            List listPartner = new ArrayList();
            listPartner = q.list();
            //Add tim kiem theo ngay
            //Hieptd
            listPartner = createQueryFindByDate(f.getStaDate(), f.getEndDate(), listPartner);

            req.getSession().setAttribute("toEditPartner", 0);

//            if (listPartner != null) {
//                f.setMessage("Tìm thấy " + listPartner.size() + " kết quả thỏa mãn điều kiện tìm kiếm");
//            } else {
//                f.setMessage("");
//            }

            req.getSession().removeAttribute("listPartner");
            req.getSession().setAttribute("listPartner", listPartner);

            req.setAttribute("returnMsg", "partner.search");
            List paramMsg = new ArrayList();
            paramMsg.add(listPartner.size());
            req.setAttribute("paramMsg", paramMsg);

        }

        pageForward = ADD_NEW_PARTNER;

        log.info("End method searchPartner of PartnerDAO");

        return pageForward;
    }

    /*
     * @Contends : Add find by date
     * @Author : hieptd
     */
    private List<Partner> createQueryFindByDate(String fDate, String eDate, List<Partner> listPartner) {
        List parteners = new ArrayList();
        Date fromDate;
        Date endDate;
        try {
            fromDate = DateTimeUtils.convertStringToDate(fDate);
        } catch (Exception ex) {
            fromDate = null;
        }
        try {
            endDate = DateTimeUtils.convertStringToDate(eDate);
        } catch (Exception ex) {
            endDate = null;
        }
        if (fromDate == null && endDate == null) {
            return listPartner;
        }
        for (int i = 0; i < listPartner.size(); i++) {
            if (fromDate != null && endDate == null) {
                if (listPartner.get(i).getEndDate() != null && listPartner.get(i).getEndDate().compareTo(fromDate) <= 0) {
                    continue;
                }
            } else if (fromDate == null && endDate != null) {
                if (listPartner.get(i).getStaDate() != null && listPartner.get(i).getStaDate().compareTo(endDate) >= 0) {
                    continue;
                }
            } else if (fromDate == null && endDate != null) {
                if (listPartner.get(i).getEndDate() != null && listPartner.get(i).getEndDate().compareTo(fromDate) <= 0) {
                    continue;
                }
                if (listPartner.get(i).getStaDate() != null && listPartner.get(i).getStaDate().compareTo(endDate) >= 0) {
                    continue;
                }
            }
            parteners.add(listPartner.get(i));
        }
        return parteners;
    }

    /*Author: NamNX
     * Date created: 10/06/2009
     * Purpose: kiem tra du leu nhap partner truoc khi them moi hoac sua
     */
    private boolean checkValidateToAdd() {

        Long count;
        HttpServletRequest req = getRequest();

        String strPartnerName = this.partnerForm.getPartnerName().trim();
//        String strContactName = this.partnerForm.getContactName().trim();
        String strPartnerCode = this.partnerForm.getPartnerCode().trim();

        String strPartnerType = this.partnerForm.getPartnerType();
        String strStatus = this.partnerForm.getStatus();
        if ((strPartnerName == null) || strPartnerName.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidPartnerName");
            return false;
        }
//        if ((strContactName == null) || strContactName.equals("")) {
//            req.setAttribute("returnMsg", "partner.error.invalidContact");
//            return false;
//        }
        if ((strPartnerType == null) || strPartnerType.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidPartnerType");
            return false;
        }
        if ((strPartnerCode == null) || strPartnerCode.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidPartnerCode");
            return false;
        }
        if ((strStatus == null) || strStatus.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidStatus");
            return false;
        }


        String strQuery = "select count(*) from Partner as model where lower(model.partnerCode)=?";
        Query q = getSession().createQuery(strQuery);
//        q.setParameter(0, Constant.STATUS_DELETE);
        q.setParameter(0, strPartnerCode.toLowerCase().trim());
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "partner.add.duplicateCode");
            return false;
        }
        return true;

    }

    private boolean checkValidateToEdit() {

        Long count;
        HttpServletRequest req = getRequest();
        Long id = this.partnerForm.getPartnerId();
        String strPartnerName = this.partnerForm.getPartnerName().trim();
        String strPartnerCode = this.partnerForm.getPartnerCode().trim();

//        String strContactName = this.partnerForm.getContactName().trim();
        String strPartnerType = this.partnerForm.getPartnerType();
        String strStatus = this.partnerForm.getStatus();
        if ((strPartnerName == null) || strPartnerName.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidPartnerName");
            return false;
        }
//        if ((strContactName == null) || strContactName.equals("")) {
//            req.setAttribute("returnMsg", "partner.error.invalidContact");
//            return false;
//        }
        if ((strPartnerType == null) || strPartnerType.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidPartnerType");
            return false;
        }
        if ((strPartnerCode == null) || strPartnerCode.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidPartnerCode");
            return false;
        }
        if ((strStatus == null) || strStatus.equals("")) {
            req.setAttribute("returnMsg", "partner.error.invalidStatus");
            return false;
        }

        //Modify by hieptd
        //String strQuery = "select count(*) from Partner where (lower(partnerCode)=? or lower(partnerName)=? )and not partnerId = ?";
        String strQuery = "select count(*) from Partner where ( lower(partnerCode) = ? )and not partnerId = ? ";
        Query q = getSession().createQuery(strQuery);
//        q.setParameter(0, Constant.STATUS_DELETE);
        q.setParameter(0, strPartnerCode.toLowerCase().trim());
        //q.setParameter(1, strPartnerName.toLowerCase().trim());
        q.setParameter(1, id);
        count = (Long) q.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "partner.edit.duplicateCode");
            return false;
        }
        return true;

    }

    /* author TheTM
     * Ham chuan bi copy 1 partner */
    public String prepareCopyPartner() throws Exception {
        log.info("Begin method prepareCopyPartner of PartnerDAO ...");
        try {
            HttpServletRequest req = getRequest();
            PartnerForm f = this.partnerForm;
            String strPartnerId = (String) QueryCryptUtils.getParameter(req, "partnerId");
            Long partnerId;
            try {
                partnerId = Long.parseLong(strPartnerId);
            } catch (Exception ex) {
                partnerId = -1L;
            }
            Partner bo = findById(partnerId);
            if (bo == null) {
                req.setAttribute("returnMsg", "partner.copy.error");
                pageForward = ADD_NEW_PARTNER;
                log.info("End method prepareCopyPartner of PartnerDAO");
                return pageForward;
            }
            f.copyDataFromBO(bo);
            req.getSession().setAttribute("toEditPartner", 2);
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_PARTNER;
            log.info("End method prepareCopyPartner of PartnerDAO");
            return pageForward;
        }
        pageForward = ADD_NEW_PARTNER;
        log.info("End method prepareCopyPartner of PartnerDAO");
        return pageForward;
    }

    /* author TheTM
     * Ham copy 1 partner */
    public String copyPartner() throws Exception {
        log.info("Begin method copyPartner of PartnerDAO ...");
        try {
            HttpServletRequest req = getRequest();
            PartnerForm f = this.partnerForm;
            Partner bo = findById(f.getPartnerId());
            f.setPartnerId(getSequence("PARTNER_SEQ"));
            if (checkValidateToAdd()) {
                Partner copyPartner = new Partner();
                f.copyDataToBO(copyPartner);
                getSession().save(copyPartner);
                f.resetForm();
                req.setAttribute("returnMsg", "partner.copy.success");
                req.getSession().setAttribute("toEditPartner", 0);
                List listPartner = new ArrayList();
                listPartner = findAll();
                req.getSession().removeAttribute("listPartner");
                req.getSession().setAttribute("listPartner", listPartner);
                List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
                lstLogObj.add(new ActionLogsObj(bo, copyPartner));
                saveLog(Constant.ACTION_COPY_PARTNER, "Sao chép đối tác", lstLogObj, bo.getPartnerId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = ADD_NEW_PARTNER;
            log.info("End method copyPartner of PartnerDAO");
            return pageForward;
        }
        pageForward = ADD_NEW_PARTNER;
        log.info("End method copyPartner of PartnerDAO");
        return pageForward;
    }

    public Partner getPartnerByCode(String partnerCode) {
        Partner partner = null;
        if (partnerCode != null || !partnerCode.trim().equals("")) {

            String strQuery = "from Partner where lower(partnerCode) = ? and status = ?  ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, partnerCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_USE);
            List<Partner> listPartner = query.list();
            if (listPartner != null && listPartner.size() == 1) {
                partner = listPartner.get(0);
            }
        }

        return partner;
    }
}
