package com.viettel.im.database.DAO;

import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;

import com.viettel.im.client.form.CommCountersForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.CommCounters;
import com.viettel.im.database.BO.CommCounterParams;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * CommCounters entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see com.viettel.im.database.DAO.CommCounters
 * @author MyEclipse Persistence Tools
 */
public class CommCountersDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(CommCountersDAO.class);
    // property constants
    public static final String COUNTER_ID = "counterId";
    public static final String COUNTER_CODE = "counterCode";
    public static final String COUNTER_NAME = "counterName";
    public static final String PARAM_NAME = "paramName";
    public static final String FUNCTION_NAME = "functionName";
    public static final String STATUS = "status";
    public static final String DETAIL_FUNCTION_NAME = "detailFunctionName";
    public static final String REPORT_TEMPLATE = "reportTemplate";

    //dinh nghia cac hang so forward
    private String pageForward;
    private static final String ADD_NEW_COMMCOUNTERS = "addNewCommCounters";
    private static final String COUNTERS_PARAM = "countersParam";

    //private dinh nghia cac bien session, request
    private final String REQUEST_MESSAGGE = "message";
    private final String REQUEST_MESSAGGE_PARAM = "messageParam";

    //dinh nghia bien form
    private CommCountersForm commCountersForm = new CommCountersForm();

    public CommCountersForm getCommCountersForm() {
        return commCountersForm;
    }

    public void setCommCountersForm(CommCountersForm commCountersForm) {
        this.commCountersForm = commCountersForm;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * chuan bi form khai bao bo dem
     *
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of CommCountersDAO ...");

        HttpServletRequest req = getRequest();

        this.commCountersForm.resetForm();

        //lay danh sach cac bo dem da co
        String queryString = "from CommCounters " +
                "where status <> ? " +
                "order by nlssort(counterCode,'nls_sort=Vietnamese') asc, nlssort(counterName,'nls_sort=Vietnamese') asc ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, String.valueOf(Constant.STATUS_DELETE));
        List listCommCounters = queryObject.list();
        req.getSession().setAttribute("listCommCounters", listCommCounters);

        pageForward = ADD_NEW_COMMCOUNTERS;

        log.info("End method preparePage of CommCountersDAO");

        return pageForward;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * forward list
     *
     */
    public String pageNavigator() throws Exception {
        log.info("Begin method preparePage of CommCountersDAO ...");

        HttpServletRequest req = getRequest();

        pageForward = "addNewCommCountersResult";

        log.info("End method preparePage of CommCountersDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 10/08/2009
     * xu ly su kien onclick cua nut bo qua
     *
     */
    public String cancelEditCommCounter() throws Exception {
        log.info("Begin method cancelEditCommCounter of CommCountersDAO ...");

        this.commCountersForm.resetForm();

        pageForward = ADD_NEW_COMMCOUNTERS;

        log.info("End method cancelEditCommCounter of CommCountersDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 10/08/2009
     * phan trang cho danh sach cac tham so bo dem
     *
     */
    public String pageNavigatorForCounterParam() throws Exception {
        log.info("Begin method pageNavigatorForCounterParam of CommCountersDAO ...");

        HttpServletRequest req = getRequest();

        pageForward = "countersParamResult";

        log.info("End method pageNavigatorForCounterParam of CommCountersDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 10/08/2009
     * Tim kiem bo dem
     *
     */
    public String searchCommCounters() throws Exception {
        log.info("Begin method searchCommCounters of CommCountersDAO...");

        HttpServletRequest req = getRequest();

        String counterCode = this.commCountersForm.getCounterCode();
        String counterName = this.commCountersForm.getCounterName();
        String status = this.commCountersForm.getStatus();
        String functionName = this.commCountersForm.getFunctionName();
        String detailFunctionName = this.commCountersForm.getDetailfunctionName();
        String reportTemplate = this.commCountersForm.getReportTemplate();

        StringBuffer strQuery = new StringBuffer("from CommCounters a " +
                "where 1 = 1 ");
        List listParameter = new ArrayList();

        strQuery.append("and a.status <> ? ");
        listParameter.add(String.valueOf(Constant.STATUS_DELETE));

        if (counterCode != null && !counterCode.equals("")) {
            strQuery.append("and lower(a.counterCode) = ? ");
            listParameter.add(counterCode.trim().toLowerCase());
        }

        if (counterName != null && !counterName.equals("")) {
            strQuery.append("and lower(a.counterName) like ? ");
            listParameter.add("%" + counterName.trim().toLowerCase() + "%");
        }

        if (status != null && !status.equals("")) {
            strQuery.append("and a.status = ? ");
            listParameter.add(status);
        }

        if (functionName != null && !functionName.equals("")) {
            strQuery.append("and lower(a.functionName) like ? ");
            listParameter.add("%" + functionName.trim().toLowerCase() + "%");
        }

        if (detailFunctionName != null && !detailFunctionName.equals("")) {
            strQuery.append("and lower(a.detailFunctionName) like ? ");
            listParameter.add("%" + detailFunctionName.trim().toLowerCase() + "%");
        }

        if (reportTemplate != null && !reportTemplate.equals("")) {
            strQuery.append("and lower(a.reportTemplate) like ? ");
            listParameter.add("%" + reportTemplate.trim().toLowerCase() + "%");
        }

        strQuery.append("order by nlssort(counterCode,'nls_sort=Vietnamese') asc, nlssort(counterName,'nls_sort=Vietnamese') asc ");

        Query query = getSession().createQuery(strQuery.toString());

        for (int i = 0; i < listParameter.size(); i++) {
            query.setParameter(i, listParameter.get(i));
        }

        List<CommCounters> listCommCounter = query.list();

        //thiet lap len bien session
        req.getSession().setAttribute("listCommCounters", listCommCounter);

        if (listCommCounter != null) {
            req.setAttribute(REQUEST_MESSAGGE, "commItemGroups.searchMessage");
            List listParam = new ArrayList();
            listParam.add(listCommCounter.size());
            req.setAttribute(REQUEST_MESSAGGE_PARAM, listParam);
        }


        pageForward = ADD_NEW_COMMCOUNTERS;

        log.info("End method searchCommCounters of CommCountersDAO");

        return pageForward;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * Them moi bo dem
     *
     */
    public String addCommCounters() throws Exception {
        log.info("Begin method addCommCounters of CommCountersDAO ...");

        HttpServletRequest req = getRequest();

        if (!checkValidCommCounterToAdd()) {
            pageForward = ADD_NEW_COMMCOUNTERS;
            log.info("End method addCommCounters of CommCountersDAO");
            return pageForward;
        }

        //them commcounter moi
        CommCounters commCounters = new CommCounters();
        commCountersForm.setCreateDate(DateTimeUtils.getSysdate());
        this.commCountersForm.copyDataToBO(commCounters);
        Long counterId = getSequence("COMM_COUNTER_SEQ");
        commCounters.setCounterId(counterId);
        getSession().save(commCounters);

        //them vao bien session
        List<CommCounters> listCommCounters = (List<CommCounters>) req.getSession().getAttribute("listCommCounters");
        if (listCommCounters == null) {
            listCommCounters = new ArrayList<CommCounters>();
            req.getSession().setAttribute("listCommCounters", listCommCounters);
        }
        listCommCounters.add(0, commCounters);

        //reset form
        this.commCountersForm.resetForm();

        //
        req.setAttribute(REQUEST_MESSAGGE, "commCounter.add.success");


        pageForward = ADD_NEW_COMMCOUNTERS;

        log.info("End method addCommCounters of CommCountersDAO");

        return pageForward;
    }

    /**
     *
     * author tamdt1, 10/08/2009
     * kiem tra cac dieu kien truoc khi them bo dem moi
     *
     */
    private boolean checkValidCommCounterToAdd() {
        HttpServletRequest req = getRequest();

        String counterCode = this.commCountersForm.getCounterCode();
        String counterName = this.commCountersForm.getCounterName();
        String status = this.commCountersForm.getStatus();
        String functionName = this.commCountersForm.getFunctionName();
        String detailFunctionName = this.commCountersForm.getDetailfunctionName();
        String reportTemplate = this.commCountersForm.getReportTemplate();

        //kiem tra cac truong bat buoc
        if (counterCode == null || counterCode.trim().equals("") ||
                counterName == null || counterName.trim().equals("") ||
                status == null || status.trim().equals("") ||
                functionName == null || functionName.trim().equals("") ||
                detailFunctionName == null || detailFunctionName.trim().equals("") ||
                reportTemplate == null || reportTemplate.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.requiredFieldsEmpty");
            return false;

        }

        //kiem tra viec trung lap ma
        String strQuery = "select count(*) from CommCounters where counterCode = ? and status = 1 ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, counterCode.trim());
        Long count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.duplicateCounterCode");
            return false;
        }

        //kiem tra viec trung lap ten
        strQuery = "select count(*) from CommCounters where counterName = ? and status = 1 ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, counterName.trim());
        count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.duplicateCounterName");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1, 10/08/2009
     * kiem tra cac dieu kien truoc khi them tham so cho bo dem
     *
     */
    private boolean checkValidCommCounterParamToAdd() {
        HttpServletRequest req = getRequest();

        Long counterId = this.commCountersForm.getCounterId();
        String paramName = this.commCountersForm.getParamName();
        String dataType = this.commCountersForm.getDataType();

        //kiem tra cac truong bat buoc
        if (counterId == null || counterId.compareTo(0L) <= 0 ||
                paramName == null || paramName.trim().equals("") ||
                dataType == null || dataType.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGGE, "counterParam.error.requiredFieldsEmpty");
            return false;

        }

        //kiem tra viec trung lap ten tham so
        String strQuery = "select count(*) from CommCounterParams where paramName = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, paramName.trim());
        Long count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGGE, "counterParam.error.duplicateParamName");
            return false;
        }

        return true;
    }

    /**
     *
     * author tamdt1, 10/08/2009
     * kiem tra cac dieu kien truoc khi sua thong tin bo dem
     *
     */
    private boolean checkValidCommCounterToEdit() {
        HttpServletRequest req = getRequest();

        Long counterId = this.commCountersForm.getCounterId();
        String counterCode = this.commCountersForm.getCounterCode();
        String counterName = this.commCountersForm.getCounterName();
        String status = this.commCountersForm.getStatus();
        String functionName = this.commCountersForm.getFunctionName();
        String detailFunctionName = this.commCountersForm.getDetailfunctionName();
        String reportTemplate = this.commCountersForm.getReportTemplate();

        //kiem tra cac truong bat buoc
        if (counterCode == null || counterCode.trim().equals("") ||
                counterName == null || counterName.trim().equals("") ||
                status == null || status.trim().equals("") ||
                functionName == null || functionName.trim().equals("") ||
                detailFunctionName == null || detailFunctionName.trim().equals("") ||
                reportTemplate == null || reportTemplate.trim().equals("")) {

            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.requiredFieldsEmpty");
            return false;

        }

        //kiem tra viec trung lap ma
        String strQuery = "select count(*) from CommCounters where counterCode = ? and counterId <> ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, counterCode.trim());
        query.setParameter(1, counterId);
        Long count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.duplicateCounterCode");
            return false;
        }

        //kiem tra viec trung lap ten
        strQuery = "select count(*) from CommCounters where counterName = ? and counterId <> ? ";
        query = getSession().createQuery(strQuery);
        query.setParameter(0, counterName.trim());
        query.setParameter(1, counterId);
        count = (Long) query.list().get(0);
        if (count.compareTo(0L) > 0) {
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.duplicateCounterName");
            return false;
        }

        return true;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * Cap nhat bo dem
     *
     */
    public String editCommCounters() throws Exception {
        log.info("Begin method editCommCounters of CommCountersDAO ...");

        HttpServletRequest req = getRequest();
        pageForward = Constant.ERROR_PAGE;

        if (!checkValidCommCounterToEdit()) {
            //thiet lap che do chinh sua thong tin commcounter
            req.setAttribute("toEditCommCounters", 1);
            //
            pageForward = ADD_NEW_COMMCOUNTERS;
            log.info("End method editCommCounters of CommCountersDAO");

            return pageForward;
        }


        Long counterId = this.commCountersForm.getCounterId();
        //
        CommCounters commCounters = findById(counterId);
        if (commCounters == null) {
            //
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.counterNotFount");

            //
            pageForward = ADD_NEW_COMMCOUNTERS;
            log.info("End method editCommCounters of CommCountersDAO");

            return pageForward;

        }

        this.commCountersForm.copyDataToBO(commCounters);
        getSession().update(commCounters);

        req.setAttribute(REQUEST_MESSAGGE, "commCounter.edit.success");

        //
        req.getSession().setAttribute("toEditCommCounters", 0);

        //sua thong tin trong bien session
        List<CommCounters> listCommCounters = (List<CommCounters>) req.getSession().getAttribute("listCommCounters");
        if (listCommCounters != null && listCommCounters.size() > 0) {
            for (int i = 0; i < listCommCounters.size(); i++) {
                CommCounters tmpCommCounters = listCommCounters.get(i);
                if (tmpCommCounters.getCounterId().equals(counterId)) {
                    this.commCountersForm.copyDataToBO(tmpCommCounters);
                    break;
                }
            }
        }

        //reset form
        this.commCountersForm.resetForm();

        pageForward = ADD_NEW_COMMCOUNTERS;

        log.info("End method editCommCounters of CommCountersDAO");

        return pageForward;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * chuan bi cap nhat bo dem
     *
     */
    public String prepareEditCommCounters() throws Exception {
        log.info("Begin method prepareEditCommCounters of CommCountersDAO...");

        HttpServletRequest req = getRequest();
        pageForward = Constant.ERROR_PAGE;

        String strCounterId = (String) QueryCryptUtils.getParameter(req, "counterId");
        Long counterId;
        try {
            counterId = Long.parseLong(strCounterId);
        } catch (Exception ex) {
            counterId = -1L;
        }

        //
        CommCounters commCounters = findById(counterId);
        if (commCounters == null) {
            //
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.counterNotFount");

            //
            pageForward = ADD_NEW_COMMCOUNTERS;
            log.info("End method editCommCounters of CommCountersDAO");

            return pageForward;

        }

        //chuyen du lieu len form
        this.commCountersForm.copyDataFromBO(commCounters);

        //thiet lap che do chinh sua thong tin commcounter
        req.setAttribute("toEditCommCounters", 1);

        pageForward = ADD_NEW_COMMCOUNTERS;

        log.info("End method prepareEditCommCounters of CommCountersDAO");
        return pageForward;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * Xoa bo dem
     *
     */
    public String deleteCommCounters() throws Exception {
        log.info("Begin method deleteCommCounters of CommCountersDAO ...");

        HttpServletRequest req = getRequest();
        pageForward = Constant.ERROR_PAGE;


        String strCommCountersId = (String) QueryCryptUtils.getParameter(req, "counterId");
        Long counterId;
        try {
            counterId = Long.parseLong(strCommCountersId);
        } catch (Exception ex) {
            counterId = -1L;
        }

        //
        CommCounters commCounters = findById(counterId);
        if (commCounters == null) {
            //
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.counterNotFount");

            //
            pageForward = ADD_NEW_COMMCOUNTERS;
            log.info("End method editCommCounters of CommCountersDAO");

            return pageForward;

        }
        if (checkValidateToDelete(counterId)) {


            commCounters.setStatus(String.valueOf(Constant.STATUS_DELETE));
            getSession().update(commCounters);

            //xoa du lieu tren session
            List<CommCounters> listCommCounters = (List<CommCounters>) req.getSession().getAttribute("listCommCounters");
            if (listCommCounters != null && listCommCounters.size() > 0) {
                for (int i = 0; i < listCommCounters.size(); i++) {
                    CommCounters tmpCommCounters = listCommCounters.get(i);
                    if (tmpCommCounters.getCounterId().equals(counterId)) {
                        listCommCounters.remove(i);
                        break;
                    }
                }
            }

            //
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.delete.success");
            req.getSession().setAttribute("toEditCommCounters", 0);
        }
        else{
           req.setAttribute(REQUEST_MESSAGGE, "commCounter.delete.error");
        }
        pageForward = ADD_NEW_COMMCOUNTERS;

        log.info("End method deleteCommCounters of CommCountersDAO");
        return pageForward;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * chuan bi cap nhat tham so bo dem
     *
     */
    public String actionPrepareCountersParamDialog() throws Exception {
        log.info("Begin method  of actionPrepareCountersParam of CommCountersDAO...");
        HttpServletRequest req = getRequest();
        String strCounterId = req.getParameter("counterId");
        Long counterId = -1L;
        try {
            counterId = Long.valueOf(strCounterId);
        } catch (Exception ex) {
            ex.printStackTrace();
            counterId = -1L;
        }

        //
        CommCounters commCounters = findById(counterId);
        if (commCounters == null) {
            //
            req.setAttribute(REQUEST_MESSAGGE, "commCounter.error.counterNotFount");

            //
            pageForward = COUNTERS_PARAM;
            log.info("End method actionPrepareCountersParamDialog of CommCountersDAO");

            return pageForward;

        }

        //
        this.commCountersForm.setCounterId(counterId);

        //lay danh sach tham so thuoc bo dem
        List lstcountersParam = new ArrayList();
        lstcountersParam = findByCounterId(counterId);
        req.getSession().setAttribute("lstcountersParam", lstcountersParam);

        log.info("End method  of actionPrepareCountersParam of CommCountersDAO");

        return "countersParam";
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * Them moi tham so bo dem
     *
     */
    public String addCommCountersParam() throws Exception {
        log.info("Begin method addCommCountersParam of CommCountersDAO ...");

        HttpServletRequest req = getRequest();

        if (!checkValidCommCounterParamToAdd()) {
            //
            pageForward = COUNTERS_PARAM;

            log.info("End method addCommCountersParam of CommCountersDAO");

            return pageForward;
        }

        //them du lieu vao DB
        CommCounterParams commCounterParams = new CommCounterParams();
        commCounterParams.setCounterId(this.commCountersForm.getCounterId());
        commCounterParams.setParamName(this.commCountersForm.getParamName());
        commCounterParams.setDataType(this.commCountersForm.getDataType());
        Long counterParamId = getSequence("COMM_COUNTER_PARAM_SEQ");
        commCounterParams.setCounterParamId(counterParamId);
        Long paramOrder = findMaxParamId(String.valueOf(this.commCountersForm.getCounterId()));
        commCounterParams.setParamOrder(paramOrder);
        commCounterParams.setStatus(Constant.STATUS_USE);
        getSession().save(commCounterParams);

        //them tham so vao bien session
        List<CommCounterParams> lstcountersParam = (List<CommCounterParams>) req.getSession().getAttribute("lstcountersParam");
        if (lstcountersParam == null) {
            lstcountersParam = new ArrayList<CommCounterParams>();
            req.getSession().setAttribute("lstcountersParam", lstcountersParam);
        }

        lstcountersParam.add(0, commCounterParams);

        //
        req.setAttribute(REQUEST_MESSAGGE, "counterParam.add.success");

        //reset form
        this.commCountersForm.setParamName("");
        this.commCountersForm.setDataType("");

        pageForward = COUNTERS_PARAM;

        log.info("End method addCommCountersParam of CommCountersDAO");

        return pageForward;

    }

//    /**
//     *
//     * author tuannv
//     * date: 28/04/2009
//     * chuan bi cap nhat bo dem
//     *
//     */
//    public String prepareEditCommCountersParam() throws Exception {
//        log.info("Begin method preparePage of CommCountersDAO ...");
//        HttpServletRequest req = getRequest();
//        HttpSession session = req.getSession();
//        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
//        pageForward = Constant.ERROR_PAGE;
//        if (userToken != null) {
//            try {
//                CommCountersForm f = this.commCountersForm;
//
//                String strCounterParamId = (String) QueryCryptUtils.getParameter(req, "counterParamId");
//                if (strCounterParamId == null) {
//                    f.setMessage("Lỗi cập nhật tham số !");
//                    return pageForward;
//                }
//                try {
//                    Long counterParamId = Long.parseLong(strCounterParamId);
//
//                    CommCounterParams bo = findByCounterParamsId(counterParamId);
//
//                    f.setParamName(bo.getParamName());
//                    f.setDataType(bo.getDataType());
//
//                } catch (RuntimeException re) {
//                    log.error("find all failed", re);
//                    throw re;
//                }
//                pageForward = "countersParam";
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw e;
//            }
//        } else {
//            pageForward = Constant.SESSION_TIME_OUT;
//        }
//        log.info("End method preparePage of CommCountersDAO");
//        return pageForward;
//    }
    /**
     *
     * author tuannv
     * date: 28/04/2009
     * Cap nhat bo dem
     *
     */
    public String editCommCountersParam() throws Exception {
        log.info("Begin method preparePage of CommCountersDAO ...");

        //    ActionResultBO actionResult = new ActionResultBO();
        HttpServletRequest req = getRequest();
        HttpSession session = req.getSession();
        UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
        pageForward = Constant.ERROR_PAGE;
        CommCountersForm f = this.commCountersForm;

        if (userToken != null) {
            try {

                String strCounterParamId = (String) QueryCryptUtils.getParameter(req, "counterParamId");
                Long counterParamId = Long.parseLong(strCounterParamId);

                CommCounterParams bo = findByCounterParamsId(counterParamId);

                bo.setParamName(f.getParamName());
                bo.setDataType(f.getDataType());

                getSession().update(bo);

                f.resetForm();

                f.setMessage("Ðã sửa thông tin tham số bộ đếm !");

                req.getSession().setAttribute("toEditCommCounters", 0);

                List listCommCounters = new ArrayList();
                listCommCounters = findAll();
                req.getSession().removeAttribute("listCommCounters");
                req.getSession().setAttribute("listCommCounters", listCommCounters);

                pageForward = "countersParamResult";

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            pageForward = Constant.SESSION_TIME_OUT;
        }


        log.info("End method preparePage of CommCountersDAO");

        //actionResult.setPageForward(pageForward);
        return pageForward;
    }

    /**
     *
     * author tuannv
     * date: 28/04/2009
     * Xoa bo dem
     *
     */
    public String deleteCommCountersParam() throws Exception {
        log.info("Begin method deleteCommCountersParam of CommCountersDAO ...");

        HttpServletRequest req = getRequest();
        pageForward = Constant.ERROR_PAGE;

        try {

            String strCounterParamId = (String) QueryCryptUtils.getParameter(req, "counterParamId");
            Long counterParamId = -1L;
            try {
                counterParamId = Long.valueOf(strCounterParamId);
            } catch (Exception ex) {
                counterParamId = -1L;
            }

            CommCounterParams commCounterParams = findByCounterParamsId(counterParamId);
            if (commCounterParams == null) {
            }

            getSession().delete(commCounterParams);
            getSession().flush();

            req.setAttribute(REQUEST_MESSAGGE, "counterParam.delete.success");

            List<CommCounterParams> listCountersParam = (List<CommCounterParams>) req.getSession().getAttribute("lstcountersParam");
            if (listCountersParam != null) {
                for (int i = 0; i < listCountersParam.size(); i++) {
                    CommCounterParams tmpCommCounterParams = listCountersParam.get(i);
                    if (tmpCommCounterParams.getCounterParamId().equals(counterParamId)) {
                        listCountersParam.remove(i);
                        break;
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            req.setAttribute(REQUEST_MESSAGGE, "counterParam.delete.error.counterParamIsUsing");

        } finally {
            pageForward = "countersParam";

            log.info("End method deleteCommCountersParam of CommCountersDAO");

            return pageForward;
        }


    }

    public List findByCounterId(Long counterId) {
        try {
            String queryString = "from CommCounterParams as model where model.counterId= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, counterId);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public void save(CommCounters transientInstance) {
        log.debug("saving CommCounters instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(CommCounters persistentInstance) {
        log.debug("deleting CommCounters instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public CommCounters findById(java.lang.Long id) {
        log.debug("getting CommCounters instance with id: " + id);
        try {
            CommCounters instance = (CommCounters) getSession().get(
                    "com.viettel.im.database.BO.CommCounters", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public CommCounterParams findByCounterParamsId(java.lang.Long id) {
        log.debug("getting CommCounters instance with id: " + id);
        try {
            CommCounterParams instance = (CommCounterParams) getSession().get(
                    "com.viettel.im.database.BO.CommCounterParams", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CommCounters instance) {
        log.debug("finding CommCounters instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.CommCounters").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding CommCounters instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from CommCounters as model where model." + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(propertyName) + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByCounterCode(Object counterCode) {
        return findByProperty(COUNTER_CODE, counterCode);
    }

    public List findByCounterName(Object counterName) {
        return findByProperty(COUNTER_NAME, counterName);
    }

    public List findByParamName(String paramName, String counterId) {
        log.debug("finding all CommCountersParam instances");
        try {
            String queryString = "from CommCounterParams where paramName=? and counterId=?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, paramName);
            queryObject.setParameter(1, Long.parseLong(counterId));

            return queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Long findMaxParamId(String counterId) {
        log.debug("finding all CommCountersParam instances");
        try {

            String queryString = "from CommCounterParams where counterId=?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Long.parseLong(counterId));

            if ((queryObject.list() != null) && (queryObject.list().size() > 0)) {

                queryString = "select max(param_Order) max from Comm_Counter_Params where counter_Id=?";
                queryObject = getSession().createSQLQuery(queryString);
                queryObject.setParameter(0, Long.parseLong(counterId));
                Long max = Long.parseLong(queryObject.list().get(0).toString());
                return (max + 1);

            } else {
                return 1L;
            }
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findByFunctionName(Object functionName) {
        return findByProperty(FUNCTION_NAME, functionName);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findByDetailFunctionName(Object detailFunctionName) {
        return findByProperty(DETAIL_FUNCTION_NAME, detailFunctionName);
    }

    public List findByReportTemplate(Object reportTemplate) {
        return findByProperty(REPORT_TEMPLATE, reportTemplate);
    }

    public List findAll() {
        log.debug("finding all CommCounters instances");
        try {
            String queryString = "from CommCounters";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public List findAllActive() {
        log.debug("finding all CommCounters instances");
        try {
            String queryString = "from CommCounters where status = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, Constant.STATUS_USE.toString());
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public CommCounters merge(CommCounters detachedInstance) {
        log.debug("merging CommCounters instance");
        try {
            CommCounters result = (CommCounters) getSession().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CommCounters instance) {
        log.debug("attaching dirty CommCounters instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CommCounters instance) {
        log.debug("attaching clean CommCounters instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    /***
     * AnDV
     * Check rang buoc khi xoa trong bang COMM_ITEMS va COMM_COUNTER_PARAMS
     */
    private boolean checkValidateToDelete(Long counterId) {
        HttpServletRequest req = getRequest();
        Long count;

        //kiem tra xem con ton tai Bras thuoc thiet bi nay khong
        String strQuery = "select count(*) from CommCounterParams as model " +
                "where model.counterId = ? and  model.status <> ?";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, counterId);
        q.setParameter(1, Constant.STATUS_DELETE);
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return false;
        }

        strQuery = "select count(*) from CommItems as model  " +
                "where model.counterId = ? and  model.status <> ? ";
        q = getSession().createQuery(strQuery);
        q.setParameter(0, counterId);
        q.setParameter(1, String.valueOf(Constant.STATUS_DELETE));
        count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {
            return false;
        }
        return true;

    }
}
