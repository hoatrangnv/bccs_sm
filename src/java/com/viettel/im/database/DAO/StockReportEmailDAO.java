/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.StockReportEmailForm;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.StockReportEmail;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author LeVt1_S
 */
public class StockReportEmailDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(StockReportEmailDAO.class);
    private String pageForward;
    private StockReportEmailForm stockReportEmailForm = new StockReportEmailForm();

    public StockReportEmailForm getStockReportEmailForm() {
        return stockReportEmailForm;
    }

    public void setStockReportEmailForm(StockReportEmailForm stockReportEmailForm) {
        this.stockReportEmailForm = stockReportEmailForm;
    }
    //dinh nghia cac hang so pageForward
    public static final String ADD_NEW_STOCK_REPORT_EMAIL = "addNewStockReportEmail";
    public static final String PAGE_STOCK_REPORT_EMAIL_NAVIGATOR = "pageStockReportEmailNavigator";
    //dinh nghia cac hang so bien request hoac session
    public static final String SESSION_LIST_STOCK_REPORT_EMAIL = "listStockReportEmail";

    public String prepareStockReportEmailPage() {
        log.info("Begin method prepareStockReportEmailPage of StockReportEmailDAO...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        try {
            HttpServletRequest req = getRequest();
            //reset form
            this.stockReportEmailForm.resetForm();
            //lay danh sach vu viec tai chinh
            List listStockReportEmail = findAllStockReportEmail();
            req.getSession().setAttribute(SESSION_LIST_STOCK_REPORT_EMAIL, listStockReportEmail);
            req.getSession().setAttribute("toEditStockReportEmail", 0);
            log.info("End method prepareStockReportEmailPage of StockReportEmailDAO");
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            log.info("End method prepareStockReportEmailPage of StockReportEmailDAO");
        }
        return pageForward;
    }

    // Tim kiem email
    public String searchStockReportEmail() {
        log.info("Begin method searchStockReportEmail of StockReportEmailDAO ...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        HttpServletRequest req = getRequest();
        Session session = getSession();
        try {
            StockReportEmailForm f = this.stockReportEmailForm;
            String ajax = QueryCryptUtils.getParameter(req, "ajax");
            if ("1".equals(ajax)) {
                f.resetForm();
                req.getSession().setAttribute("toEditStockReportEmail", 0);
            } else {
                List listParameter = new ArrayList();
                String strQuery = "from StockReportEmail as model where 1 = 1 ";
                if (f.getStatus() != null && !f.getStatus().trim().equals("")) {
                    listParameter.add(f.getStatus().trim());
                    strQuery += " and model.status = ? ";
                }
                if (f.getEmail() != null && !f.getEmail().trim().equals("")) {
                    listParameter.add("%" + f.getEmail().trim().toLowerCase() + "%");
                    strQuery += " and lower(model.email) like ? ";
                }
                if (f.getReportType() != null && !f.getReportType().trim().equals("")) {
                    listParameter.add(f.getReportType().trim());
                    strQuery += " and model.reportType = ? ";
                }
                Query q = session.createQuery(strQuery);
                for (int i = 0; i < listParameter.size(); i++) {
                    q.setParameter(i, listParameter.get(i));
                }
                List listStockReportEmail = new ArrayList();
                listStockReportEmail = q.list();

                req.getSession().setAttribute("toEditStockReportEmail", 0);
                req.setAttribute("returnMsg", "stockIsdnTransManagement.searchMessage");
                List paramMsg = new ArrayList();
                paramMsg.add(listStockReportEmail.size());
                req.setAttribute("paramMsg", paramMsg);

                req.getSession().removeAttribute("listStockReportEmail");
                req.getSession().setAttribute(SESSION_LIST_STOCK_REPORT_EMAIL, listStockReportEmail);
                log.info("End method searchStockReportEmail of StockReportEmailDAO");
            }
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            log.info("End method searchStockReportEmail of StockReportEmailDAO");
        }
        return pageForward;
    }

    public String pageStockReportEmailNavigator() throws Exception {
        log.info("Begin method pageStockReportEmailNavigator of StockReportEmailDAO...");
        pageForward = PAGE_STOCK_REPORT_EMAIL_NAVIGATOR;
        log.info("End method pageStockReportEmailNavigator of StockReportEmailDAO");
        return pageForward;
    }

    public String addNewStockReportEmail() throws Exception {
        log.info("Begin method addNewStockReportEmail of StockReportEmailDAO...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        HttpServletRequest req = getRequest();
        Session session = getSession();
        try {
            if (checkStockReportEmailToAdd()) {
                StockReportEmail bo = new StockReportEmail();
                this.stockReportEmailForm.copyStockReportEmailToBO(bo);
                Long id = getSequence("STOCK_REPORT_EMAIL_SEQ");
                bo.setId(id);

                session.save(bo);
                session.getTransaction().commit();
                session.beginTransaction();

                this.stockReportEmailForm.resetForm();

                req.setAttribute("returnMsg", "M.200007");
                List listStockReportEmail = new ArrayList();
                listStockReportEmail = findAllStockReportEmail();

                req.getSession().removeAttribute(SESSION_LIST_STOCK_REPORT_EMAIL);
                req.getSession().setAttribute(SESSION_LIST_STOCK_REPORT_EMAIL, listStockReportEmail);
                log.info("End method addNewStockReportEmail of StockReportEmailDAO");
            }
        } catch (Exception ex) {
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            log.debug(ex.getMessage());
            log.info("End method addNewStockReportEmail of StockReportEmailDAO");
        }
        return pageForward;
    }

    public String copyStockReportEmail() throws Exception {
        log.info("Begin method copyStockReportEmail of StockReportEmailDAO ...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        HttpServletRequest req = getRequest();
        Session session = getSession();
        try {
            StockReportEmailForm f = this.stockReportEmailForm;
            if (checkStockReportEmailToAdd()) {
                StockReportEmail bo = new StockReportEmail();
                f.copyStockReportEmailToBO(bo);
                bo.setId(getSequence("STOCK_REPORT_EMAIL_SEQ"));

                session.save(bo);
                session.getTransaction().commit();
                session.beginTransaction();

                f.resetForm();

                req.setAttribute("returnMsg", "M.200007");
                req.getSession().setAttribute("toEditStockReportEmail", 0);
                List listStockReportEmail = new ArrayList();
                listStockReportEmail = findAllStockReportEmail();

                req.getSession().removeAttribute(SESSION_LIST_STOCK_REPORT_EMAIL);
                req.getSession().setAttribute(SESSION_LIST_STOCK_REPORT_EMAIL, listStockReportEmail);
                log.info("End method copyStockReportEmail of StockReportEmailDAO");
            }
        } catch (Exception ex) {
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            log.debug(ex.getMessage());
            log.info("End method copyStockReportEmail of StockReportEmailDAO");
        }
        return pageForward;
    }

    public String editStockReportEmail() throws Exception {
        log.info("Begin method editStockReportEmail of StockReportEmailDAO ...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        HttpServletRequest req = getRequest();
        Session session = getSession();
        try {
            StockReportEmailForm f = this.stockReportEmailForm;
            if (checkStockReportEmailToEdit(1)) {
                StockReportEmail stockReportEmail = new StockReportEmail();
                stockReportEmail = findStockReportEmailById(f.getId());

                f.copyStockReportEmailToBO(stockReportEmail);
                session.update(stockReportEmail);

                session.getTransaction().commit();
                session.beginTransaction();

                f.resetForm();

                req.setAttribute("returnMsg", "M.100010");
                req.getSession().setAttribute("toEditStockReportEmail", 0);

                List listStockReportEmail = new ArrayList();
                listStockReportEmail = findAllStockReportEmail();
                req.getSession().removeAttribute(SESSION_LIST_STOCK_REPORT_EMAIL);
                req.getSession().setAttribute(SESSION_LIST_STOCK_REPORT_EMAIL, listStockReportEmail);
                log.info("End method editStockReportEmail of StockReportEmailDAO");
            }
        } catch (Exception ex) {
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            log.debug(ex.getMessage());
            log.info("End method editStockReportEmail of StockReportEmailDAO");
        }
        return pageForward;
    }

    public String deleteStockReportEmail() throws Exception {
        log.info("Begin method deleteStockReportEmail of StockReportEmailDAO ...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        HttpServletRequest req = getRequest();
        Session session = getSession();
        try {
            StockReportEmailForm f = this.stockReportEmailForm;
            String strId = (String) QueryCryptUtils.getParameter(req, "id");
            Long id;
            try {
                id = Long.parseLong(strId);
            } catch (Exception ex) {
                id = -1L;
            }
            StockReportEmail stockReportEmail = findStockReportEmailById(id);

            session.delete(stockReportEmail);
            session.getTransaction().commit();
            session.beginTransaction();

            f.resetForm();

            req.setAttribute("returnMsg", "MSG.ISN.044");
            req.getSession().setAttribute("toEditStockReportEmail", 0);

            List listStockReportEmail = new ArrayList();
            listStockReportEmail = findAllStockReportEmail();
            req.getSession().removeAttribute(SESSION_LIST_STOCK_REPORT_EMAIL);
            req.getSession().setAttribute(SESSION_LIST_STOCK_REPORT_EMAIL, listStockReportEmail);
            log.info("End method deleteStockReportEmail of StockReportEmailDAO");
        } catch (Exception ex) {
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            req.setAttribute("returnMsg", "Exception !!!");
            log.debug(ex.getMessage());
            log.info("End method deleteStockReportEmail of StockReportEmailDAO");
        }
        return pageForward;
    }

    public String prepareCopyStockReportEmail() throws Exception {
        log.info("Begin method prepareCopyStockReportEmail of StockReportEmailDAO ...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        HttpServletRequest req = getRequest();
        try {
            StockReportEmailForm f = this.stockReportEmailForm;
            String strId = (String) QueryCryptUtils.getParameter(req, "id");
            Long id;
            try {
                id = Long.parseLong(strId);
            } catch (Exception ex) {
                id = -1L;
            }
            StockReportEmail stockReportEmail = findStockReportEmailById(id);
            if (stockReportEmail == null) {
                req.setAttribute("returnMsg", "Error. Not exist");
                log.info("End method prepareCopyStockReportEmail of StockReportEmailDAO");
                return pageForward;
            }
            f.copyStockReportEmailFromBO(stockReportEmail);
            req.getSession().setAttribute("toEditStockReportEmail", 2);
            log.info("End method prepareCopyStockReportEmail of StockReportEmailDAO");
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            log.info("End method prepareCopyStockReportEmail of StockReportEmailDAO");
        }
        return pageForward;
    }

    public String prepareEditStockReportEmail() throws Exception {
        log.info("Begin method prepareEditStockReportEmail of StockReportEmailDAO ...");
        pageForward = ADD_NEW_STOCK_REPORT_EMAIL;
        HttpServletRequest req = getRequest();
        try {
            StockReportEmailForm f = this.stockReportEmailForm;
            String strId = (String) QueryCryptUtils.getParameter(req, "id");
            Long id;
            try {
                id = Long.parseLong(strId);
            } catch (Exception ex) {
                id = -1L;
            }
            StockReportEmail bo = findStockReportEmailById(id);
            f.copyStockReportEmailFromBO(bo);
            req.getSession().setAttribute("toEditStockReportEmail", 1);
            log.info("End method prepareEditStockReportEmail of StockReportEmailDAO");
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            log.info("End method prepareEditStockReportEmail of StockReportEmailDAO");
        }
        return pageForward;
    }

    public List findAllStockReportEmail() {
        log.debug("finding all StockReportEmail instances");
        try {
            String queryString = "from StockReportEmail ";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            re.printStackTrace();
            log.error("find all failed", re);
            throw re;
        }
    }

    private boolean checkStockReportEmailToAdd() {
        HttpServletRequest req = getRequest();
        Long count;
        String email = this.stockReportEmailForm.getEmail();
        email = email != null ? email.trim() : "";
        String reportType = this.stockReportEmailForm.getReportType();
        reportType = reportType != null ? reportType.trim() : "";
        String status = this.stockReportEmailForm.getStatus();
        status = status != null ? status.trim() : "";
        String description = this.stockReportEmailForm.getDescription();
        description = description != null ? description.trim() : "";
        //kiem tra cac truong bat buoc
        if (email.equals("") || reportType.equals("") || status.equals("")) {
            req.setAttribute("returnMsg", "ERR.STK.114");
            return false;
        }
        String[] array = email.split("@");
        if (array.length != 2 || !array[1].equals("viettel.com.vn")) {
            req.setAttribute("returnMsg", "Err.email.invalid");
            return false;
        }
        if (!"".equals(description) && description.length() > 200) {
            req.setAttribute("returnMsg", "ERR.GOD.020");
            return false;
        }

        //kiem tra trung email
        String strQuery1 = "select count(*) "
                + "from StockReportEmail as model "
                + "where model.status <> 0 and lower(model.email) = lower(?) and reportType = ? ";
        Query q1 = getSession().createQuery(strQuery1);
        q1.setParameter(0, email);
        q1.setParameter(1, reportType);
        count = (Long) q1.list().get(0);

        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "stockReportEmail.duplicateCode");
            return false;
        }
        return true;
    }

    private boolean checkStockReportEmailToEdit(int type) {
        HttpServletRequest req = getRequest();
        Long count;
        Long id = this.stockReportEmailForm.getId();
        String email = this.stockReportEmailForm.getEmail();
        email = email != null ? email.trim() : "";
        String reportType = this.stockReportEmailForm.getReportType();
        reportType = reportType != null ? reportType.trim() : "";
        String status = this.stockReportEmailForm.getStatus();
        status = status != null ? status.trim() : "";
        String description = this.stockReportEmailForm.getDescription();
        description = description != null ? description.trim() : "";
        //kiem tra cac truong bat buoc
        if (reportType.equals("") || email.equals("") || status.equals("")) {
            req.setAttribute("returnMsg", "ERR.STK.114");
            return false;
        }
        String[] array = email.split("@");
        if (array.length != 2 || !array[1].equals("viettel.com.vn")) {
            req.setAttribute("returnMsg", "Err.email.invalid");
            return false;
        }
        if (!"".equals(description) && description.length() > 200) {
            req.setAttribute("returnMsg", "ERR.GOD.020");
            return false;
        }

        //kiem tra trung email
        String strQuery1 = "select count(*) "
                + "from StockReportEmail as model "
                + "where model.status <> 0 and lower(model.email) = lower(?) and model.id <> ? and model.reportType = ? ";
        Query q1 = getSession().createQuery(strQuery1);
        q1.setParameter(0, email);
        q1.setParameter(1, id);
        q1.setParameter(2, reportType);
        count = (Long) q1.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            req.setAttribute("returnMsg", "stockReportEmail.duplicateCode");
            return false;
        }

        return true;
    }

    public StockReportEmail findStockReportEmailById(Long id) {
        log.debug("getting StockReportEmail instance with id: " + id);
        try {
            StockReportEmail instance = (StockReportEmail) getSession().get(
                    "com.viettel.im.database.BO.StockReportEmail", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public static StockReportEmail validateStockReportEmail(Session session, String email, String reportType) {
        StringBuilder sQuery = new StringBuilder();
        sQuery.append("from StockReportEmail as model ");
        sQuery.append("where model.status = 1 and lower(model.email) = lower(?) and model.reportType = ? ");

        Query q = session.createQuery(sQuery.toString());
        q.setParameter(0, email);
        q.setParameter(1, reportType);

        List list = q.list();
        if (!list.isEmpty()) {
            StockReportEmail stockReportEmail = (StockReportEmail) q.list().get(0);
            return stockReportEmail;
        }
        return null;

    }
}
