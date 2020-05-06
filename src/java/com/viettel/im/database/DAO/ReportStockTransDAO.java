package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.client.bean.ReportStockTransBean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import com.viettel.im.client.form.ReportStockTransForm;
import java.io.File;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.common.ReportConstant;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockKit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see com.viettel.im.database.BO.StockKit
 * @author MyEclipse Persistence Tools
 */
public class ReportStockTransDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportStockTransDAO.class);
    public String pageForward;
    //MrSun    
    private String CHANGE_STOCK_TYPE = "changeStockType";
    private String GET_SHOP_CODE = "getShopCode";
    private String GET_SHOP_NAME = "getShopName";
    private String REPORT_STOCK_TRANS = "reportStockTrans";
    private String LIST_STOCK_TYPE = "lstStockType";
    private String LIST_ITEMS = "lstItems";
    private String DATE_CREATE = "dateCreate";
    private String USER_CREATE = "userCreate";
    private String FROM_DATE = "fromDate";
    private String TO_DATE = "toDate";
    private String SHOP_NAME = "shopName";
    private String STOCK_TRANS_ID = "stockTransId";
    private String ACTION_CODE = "actionCode";
    private String FROM_OWNER_ID = "fromOwnerId";
    private String FROM_OWNER_TYPE = "from_OwnerType";
    private String TO_OWNER_ID = "toOwnerId";
    private String TO_OWNER_TYPE = "toOwnerType";
    private String CREATE_DATETIME = "createDatetime";
    private String STOCK_TRANS_TYPE = "stockTransType";
    private String QUANTITY_RES = "quantityRes";
    private String IMP_QTY = "impQty";
    private String EXP_QTY = "expQty";
    private String STOCK_MODEL_ID = "stockModelId";
    private String STOCK_MODEL_CODE = "stockModelCode";
    private String STOCK_MODEL_NAME = "stockModelName";
    private String NOTE = "note";
    private String FIRST_QTY = "firstQty";
    private String LAST_QTY = "lastQty";
    private final String REQUEST_REPORT_STOCK_TRANS_PATH = "reportStockTransPath";
    private final String REQUEST_REPORT_STOCK_TRANS_MESSAGE = "reportStockTransMessage";
    private ReportStockTransForm reportStockTransForm = new ReportStockTransForm();
    private final String REQUEST_MESSAGE = "returnMsg";

    public ReportStockTransForm getReportStockTransForm() {
        return reportStockTransForm;
    }

    public void setReportStockTransForm(ReportStockTransForm reportStockTransForm) {
        this.reportStockTransForm = reportStockTransForm;
    }
    private Map lstItems = new HashMap();

    public Map getLstItems() {
        return lstItems;
    }

    public void setLstItems(Map lstItems) {
        this.lstItems = lstItems;
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method ReportStockTransDAO.pageNavigator");
        pageForward = "";
        return pageForward;
    }

    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportStockTransDAO.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_STOCK_TRANS;

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findByStatus(Constant.STATUS_USE);
        //req.setAttribute(LIST_STOCK_TYPE, lstStockType);
        req.getSession().setAttribute(LIST_STOCK_TYPE, lstStockType);

        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, (-1) * cal.get(Calendar.DAY_OF_MONTH) + 1);
        reportStockTransForm.setFromDate(sdf.format(cal.getTime()));
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        reportStockTransForm.setDepartmentKind(Constant.OWNER_TYPE_SHOP.toString());
        reportStockTransForm.setShopCode(userToken.getShopCode());
        reportStockTransForm.setShopName(userToken.getShopName());
        reportStockTransForm.setShopId(userToken.getShopId());
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        reportStockTransForm.setToDate(sdf.format(cal.getTime()));

        log.debug("# End method ReportStockTransDAO.preparePage");
        return pageForward;
    }

    public String changeStockType() throws Exception {
        log.info("Begin method changeStockType of ReportStockTransDAO ...");

        try {
            HttpServletRequest req = getRequest();
            String strStockTypeId = req.getParameter("stockTypeId");
            String strTarget = req.getParameter("target");

            if (strStockTypeId != null && !strStockTypeId.trim().equals("")) {
                Long stockTypeId = Long.valueOf(strStockTypeId);
                //lay danh sach cac mat hang
                String strQueryStockModel = "select stockModelId, name from StockModel where stockTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Query queryStockModel = getSession().createQuery(strQueryStockModel);
                queryStockModel.setParameter(0, stockTypeId);
                queryStockModel.setParameter(1, Constant.STATUS_USE);
                List listStockModel = queryStockModel.list();

                String[] headerStockModel = {"", getText("MSG.RET.168")};
                List tmpListStockModel = new ArrayList();
                tmpListStockModel.add(headerStockModel);
                tmpListStockModel.addAll(listStockModel);

                //them vao ket qua tra ve, cap nhat danh sach mat hang
                this.lstItems.put(strTarget, tmpListStockModel);

            } else {
                //reset lai cac vung tuong ung

                String[] headerStockModel = {"", getText("MSG.RET.168")};
                List tmpListStockModel = new ArrayList();
                tmpListStockModel.add(headerStockModel);

                //them vao ket qua tra ve, cap nhat danh sach mat hang
                this.lstItems.put(strTarget, tmpListStockModel);

            }

        } catch (Exception ex) {
            throw ex;
        }
        pageForward = CHANGE_STOCK_TYPE;

        log.info("Begin method changeStockType of ReportStockTransDAO ...");
        return pageForward;
    }

    public String getShopCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);

            String objCode = req.getParameter("reportStockTransForm.shopCode");
            String objType = reportStockTransForm.getDepartmentKind();
            if (objCode == null || objCode.equals("") || objType == null || objType.equals("")) {
                return GET_SHOP_CODE;
            }
            if (objCode != null && objCode.trim().length() > 0) {
                CommonDAO commonDAO = new CommonDAO();
                List<ComboListBean> listShop = commonDAO.getShopAndStaffList(userToken.getShopId(), null, objCode, objType, 0, false, false);
                if (listShop != null && listShop.size() > 0) {
                    for (int i = 0; i < listShop.size(); i++) {
                        this.lstItems.put(listShop.get(i).getObjId(), listShop.get(i).getObjCode());
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return GET_SHOP_CODE;
    }

    public String getShopName() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String strShopId = req.getParameter("shopId");
            String target = req.getParameter("target");
            String departmentKind = req.getParameter("departmentKind");

            if (strShopId != null && strShopId.trim().length() > 0) {

                CommonDAO commonDAO = new CommonDAO();
//                List<ComboListBean> listShop = commonDAO.getShopAndStaffList(null, Long.valueOf(strShopId), "",departmentKind,0, false, false);
                List<ComboListBean> listShop = commonDAO.getShopAndStaffList(null, Long.valueOf(strShopId), "", departmentKind, 0, false, false);

//                Long shopId = Long.valueOf(strShopId);
//                String queryString = "from Shop where shopId = ? ";
//                Query queryObject = getSession().createQuery(queryString);
//                queryObject.setParameter(0, shopId);
//                queryObject.setMaxResults(8);
//                List<Shop> listShop = queryObject.list();

                if (listShop != null && listShop.size() > 0) {
                    this.lstItems.put(target, listShop.get(0).getObjName());
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
        return GET_SHOP_NAME;
    }

    /**
     * @Author : ANHTT
     * @return : Xuất báo cáo thẻ kho theo cách mới
     * @throws : Exception
     */
    public String reportStockTrans() throws Exception {
        log.info("Begin method reportStockTrans of ReportStockTransDAO...");
//        pageForward = Constant.ERROR_PAGE;
        pageForward = "errorPage";
//        pageForward = REPORT_STOCK_TRANS;
        HttpServletRequest req = getRequest();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String objCode = reportStockTransForm.getShopCode();
            String objType = reportStockTransForm.getDepartmentKind();
            //String status = reportStockTransForm.getStatus();

            CommonDAO commonDAO = new CommonDAO();
            List<ComboListBean> listShop;

            if (objType == null || objType.equals("")) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noObjectType");
                return pageForward;
            }
            if (objCode == null || "".equals(objCode.trim())) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noShopCode");
                return pageForward;
            }
            /*
            if (objCode == null || "".equals(objCode.trim())) {
            if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
            listShop = commonDAO.getShopAndStaffList(null, userToken.getShopId(), "", objType, 0, true, false);
            } else if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
            listShop = commonDAO.getShopAndStaffList(null, userToken.getUserID(), "", objType, 0, false, false);
            } else {
            req.setAttribute("returnMsg", "report.stocktrans.error.noShopCode");
            return pageForward;
            }
            } else {
            listShop = commonDAO.getShopAndStaffList(userToken.getShopId(), null, objCode, objType, 0, true, true);
            }
             */
            listShop = commonDAO.getShopAndStaffList(userToken.getShopId(), null, objCode, objType, 0, true, true);
            if (listShop == null || listShop.size() != 1) {
                req.setAttribute("returnMsg", "report.stocktrans.error.shopCodeNotValid");
                return pageForward;
            }
//            if (status == null || "".equals(status.trim())) {
//                req.setAttribute("returnMsg", "report.stocktrans.error.noStatus");
//                return pageForward;
//            }
            ComboListBean shop = listShop.get(0);

            String sFromDate = reportStockTransForm.getFromDate();
            String sToDate = reportStockTransForm.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noFromDate");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noToDate");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "report.stocktrans.error.fromDateNotValid");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "report.stocktrans.error.toDateNotValid");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("returnMsg", "report.stocktrans.error.fromDateToDateNotValid");
                return pageForward;
            }
//            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
//                req.setAttribute("returnMsg", "stock.report.impExp.error.fromDateToDateNotSame");
//                return pageForward;
//            }
            Long stockModelId = reportStockTransForm.getStockModelId();
            if (stockModelId == null) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noStockModelId");
                return pageForward;
            }
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                req.setAttribute("returnMsg", "report.stocktrans.error.stockModelIdNotValid");
                return pageForward;
            }

            try {
                ViettelMsg request = new OriginalViettelMsg();
                request.set("SHOP_NAME", shop.getObjName());
                request.set("SHOP_CODE", shop.getObjCode());
                request.set("USER_CREATE", userToken.getFullName());
                request.set("USER_NAME", userToken.getLoginName());

                request.set("FROM_DATE", sFromDate);
                request.set("TO_DATE", sToDate);
                request.set("CREATE_DATE", DateTimeUtils.getSysdate());


                request.set("OBJ_TYPE", shop.getObjType());
                request.set("OBJ_ID", shop.getObjId());
                request.set("STOCK_MODEL_ID", stockModel.getStockModelId());

//                request.set("STATUS", status);

                request.set("STOCK_MODEL_CODE", stockModel.getStockModelCode());
                request.set("STOCK_MODEL_NAME", stockModel.getName());

                /*Thiet lap tham so loai bao cao*/
                //  request.set(ReportConstant.REPORT_KIND, String.valueOf(ReportConstant.POS_CT_CHANGE_PRODUCT_CT));
                request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_STOCK_TRANS);

                ViettelMsg response = ReportRequestSender.sendRequest(request);
                if (response != null && response.get(ReportConstant.RESULT_FILE) != null && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                    //Thong bao len jsp
//                    req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thành công!");
                    req.setAttribute(REQUEST_MESSAGE, "MSG.RET.030");
                    //req.setAttribute(REQUEST_REPORT_STOCK_TRANS_PATH, response.get(ReportConstant.RESULT_FILE).toString());
                    DownloadDAO downloadDAO = new DownloadDAO();
                    req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                    req.setAttribute(REQUEST_REPORT_STOCK_TRANS_MESSAGE, "report.stocktrans.error.successMessage");
                } else {
//                    req.setAttribute(REQUEST_MESSAGE, "Đã xuất ra file Excel thất bại!");
                    //req.setAttribute(REQUEST_MESSAGE, "MSG.RET.031");
                    req.setAttribute("returnMsg", "report.warning.noResult");
                    return pageForward;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("returnMsg", "stock.report.impExp.error.undefine");
            return pageForward;
        }
//        pageForward = REPORT_STOCK_TRANS;
        log.info("End method reportStockTrans of ReportStockTransDAO");

        return pageForward;
    }

    public String reportStockTrans1() throws Exception {
        log.info("Begin method reportStockTrans of ReportStockTransDAO...");
//        pageForward = Constant.ERROR_PAGE;
        pageForward = "errorPage";
        HttpServletRequest req = getRequest();
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            String objCode = reportStockTransForm.getShopCode();
            String objType = reportStockTransForm.getDepartmentKind();

            CommonDAO commonDAO = new CommonDAO();
            List<ComboListBean> listShop;
            if (objCode == null || "".equals(objCode.trim())) {
                if (objType.equals(Constant.OBJECT_TYPE_SHOP)) {
                    listShop = commonDAO.getShopAndStaffList(null, userToken.getShopId(), "", objType, 0, false, false);
                } else if (objType.equals(Constant.OBJECT_TYPE_STAFF)) {
                    listShop = commonDAO.getShopAndStaffList(null, userToken.getUserID(), "", objType, 0, false, false);
                } else {
                    req.setAttribute("returnMsg", "report.stocktrans.error.noShopCode");
                    return pageForward;
                }
            } else {
                listShop = commonDAO.getShopAndStaffList(userToken.getShopId(), null, objCode, objType, 0, false, true);
            }

            if (listShop == null || listShop.size() != 1) {
                req.setAttribute("returnMsg", "report.stocktrans.error.shopCodeNotValid");
                return pageForward;
            }
            ComboListBean shop = listShop.get(0);

            String sFromDate = reportStockTransForm.getFromDate();
            String sToDate = reportStockTransForm.getToDate();
            if (sFromDate == null || "".equals(sFromDate.trim())) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noFromDate");
                return pageForward;
            }
            if (sToDate == null || "".equals(sToDate.trim())) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noToDate");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "report.stocktrans.error.fromDateNotValid");
                return pageForward;
            }
            try {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            } catch (Exception ex) {
                req.setAttribute("returnMsg", "report.stocktrans.error.toDateNotValid");
                return pageForward;
            }
            if (fromDate.after(toDate)) {
                req.setAttribute("returnMsg", "report.stocktrans.error.fromDateToDateNotValid");
                return pageForward;
            }

            Long stockModelId = reportStockTransForm.getStockModelId();
            if (stockModelId == null) {
                req.setAttribute("returnMsg", "report.stocktrans.error.noStockModelId");
                return pageForward;
            }
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockModel = stockModelDAO.findById(stockModelId);
            if (stockModel == null) {
                req.setAttribute("returnMsg", "report.stocktrans.error.stockModelIdNotValid");
                return pageForward;
            }

            /*ThanhNC - Old*/
            //Nhap/Xuat trong ky            
            StringBuffer queryString = new StringBuffer(" SELECT stock_trans_id AS " + STOCK_TRANS_ID);
            queryString.append(" ,action_code AS " + ACTION_CODE);
            queryString.append(" ,from_owner_id AS " + FROM_OWNER_ID);
            queryString.append(" ,from_owner_type AS " + FROM_OWNER_TYPE);
            queryString.append(" ,to_owner_id AS " + TO_OWNER_ID);
            queryString.append(" ,to_owner_type AS " + TO_OWNER_TYPE);
            queryString.append(" ,create_datetime AS " + CREATE_DATETIME);
            queryString.append(" ,stock_trans_type AS " + STOCK_TRANS_TYPE);
            queryString.append(" ,IMP_QTY AS " + IMP_QTY);
            queryString.append(" ,EXP_QTY AS " + EXP_QTY);
            queryString.append(" ,stock_model_id AS " + STOCK_MODEL_ID);
            queryString.append(" ,stock_model_code AS " + STOCK_MODEL_CODE);
            queryString.append(" ,stock_model_name AS " + STOCK_MODEL_NAME);
            queryString.append(" ,note AS " + NOTE);
            queryString.append(" from v_card_stock ");

            queryString.append(" WHERE ((from_owner_type = ? and  from_owner_id = ? AND exp_qty >0) OR ");
            queryString.append(" (to_owner_type = ? and to_owner_id=? AND imp_qty >0))");
            queryString.append(" AND stock_model_id = ? ");
            queryString.append(" AND create_datetime >= ? ");
            queryString.append(" AND create_datetime <= ? ");
            queryString.append(" ORDER BY create_datetime ,stock_trans_type DESC ");

            Query queryObject = getSession().createSQLQuery(queryString.toString()).
                    addScalar(STOCK_TRANS_ID, Hibernate.LONG).
                    addScalar(ACTION_CODE, Hibernate.STRING).
                    addScalar(FROM_OWNER_ID, Hibernate.LONG).
                    addScalar(FROM_OWNER_TYPE, Hibernate.LONG).
                    addScalar(TO_OWNER_ID, Hibernate.LONG).
                    addScalar(TO_OWNER_TYPE, Hibernate.LONG).
                    addScalar(CREATE_DATETIME, Hibernate.STRING).
                    addScalar(STOCK_TRANS_TYPE, Hibernate.LONG).
                    addScalar(IMP_QTY, Hibernate.LONG).
                    addScalar(EXP_QTY, Hibernate.LONG).
                    addScalar(STOCK_MODEL_ID, Hibernate.LONG).
                    addScalar(STOCK_MODEL_CODE, Hibernate.STRING).
                    addScalar(STOCK_MODEL_NAME, Hibernate.STRING).
                    addScalar(NOTE, Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(ReportStockTransBean.class));

            queryObject.setParameter(0, shop.getObjType());
            queryObject.setParameter(1, shop.getObjId());
            queryObject.setParameter(2, shop.getObjType());
            queryObject.setParameter(3, shop.getObjId());

            queryObject.setParameter(4, stockModel.getStockModelId());
            queryObject.setParameter(5, fromDate);
            queryObject.setParameter(6, toDate);

            List<ReportStockTransBean> lstTemp = queryObject.list();

            //Ton dau ky
            String firstQty = "0";
            String SELECT_REMAIN = " SELECT sdr.remain_quantity " +
                    " FROM STOCK_DAILY_RPT sdr " +
                    " WHERE sdr.stock_model_id = ? " +
                    " AND sdr.owner_id = ? " +
                    " AND sdr.created_date = ? ";
            Query q = getSession().createSQLQuery(SELECT_REMAIN);
            q.setParameter(0, stockModel.getStockModelId());
            q.setParameter(1, shop.getObjId());
            q.setParameter(2, fromDate);
            List lstFirstQty = q.list();
            if (lstFirstQty != null && lstFirstQty.size() > 0) {
                firstQty = lstFirstQty.get(0).toString();
            }
            /*ThanhNC - Old */

            /*TrongLV - New
            //Nhap/Xuat trong ky
            String queryString = "";
            queryString += " SELECT st.stock_trans_id AS " + STOCK_TRANS_ID;
            queryString += " ,stac.action_code AS " + ACTION_CODE;
            queryString += " ,st.from_owner_id AS " + FROM_OWNER_ID;
            queryString += " ,st.from_owner_type AS " + FROM_OWNER_TYPE;
            queryString += " ,st.to_owner_id AS " + TO_OWNER_ID;
            queryString += " ,st.to_owner_type AS " + TO_OWNER_TYPE;
            queryString += " ,st.create_datetime AS " + CREATE_DATETIME;
            queryString += " ,st.stock_trans_type AS " + STOCK_TRANS_TYPE;
            queryString += " ,std.quantity_res AS " + QUANTITY_RES;
            queryString += " ,std.stock_model_id AS " + STOCK_MODEL_ID;
            queryString += " ,sm.stock_model_code AS " + STOCK_MODEL_CODE;
            queryString += " ,sm.name AS " + STOCK_MODEL_NAME;
            queryString += "  ";
            queryString += " FROM stock_trans st ";
            queryString += " ,stock_trans_action stac ";
            queryString += " ,stock_trans_detail std ";
            queryString += " ,stock_model sm ";
            queryString += "  ";
            queryString += " WHERE std.stock_trans_id = st.stock_trans_id  ";
            queryString += " AND stac.stock_trans_id = st.stock_trans_id ";
            queryString += " AND std.stock_model_id = sm.stock_model_id ";
            queryString += " AND stac.action_type = 2 ";
            queryString += " AND (st.from_owner_id = ? OR st.to_owner_id = ?) ";
            queryString += " AND std.stock_model_id = ? ";
            queryString += " AND st.create_datetime >= ? ";
            queryString += " AND st.create_datetime <= ? ";
            queryString += "  ";
            queryString += " ORDER BY std.stock_model_id  ";
            queryString += " ,st.create_datetime ";
            queryString += " ,st.stock_trans_type DESC ";
            queryString += "  ";

            Query queryObject = getSession().createSQLQuery(queryString).
            addScalar(STOCK_TRANS_ID, Hibernate.LONG).
            addScalar(ACTION_CODE, Hibernate.STRING).
            addScalar(FROM_OWNER_ID, Hibernate.LONG).
            addScalar(FROM_OWNER_TYPE, Hibernate.LONG).
            addScalar(TO_OWNER_ID, Hibernate.LONG).
            addScalar(TO_OWNER_TYPE, Hibernate.LONG).
            addScalar(CREATE_DATETIME, Hibernate.STRING).
            addScalar(STOCK_TRANS_TYPE, Hibernate.LONG).
            addScalar(QUANTITY_RES, Hibernate.LONG).
            addScalar(STOCK_MODEL_ID, Hibernate.LONG).
            addScalar(STOCK_MODEL_CODE, Hibernate.STRING).
            addScalar(STOCK_MODEL_NAME, Hibernate.STRING).
            setResultTransformer(Transformers.aliasToBean(ReportStockTransBean.class));

            queryObject.setParameter(0, shop.getObjId());
            queryObject.setParameter(1, shop.getObjId());
            queryObject.setParameter(2, stockModel.getStockModelId());
            queryObject.setParameter(3, fromDate);
            queryObject.setParameter(4, toDate);

            List<ReportStockTransBean> lstTemp = queryObject.list();

            //Ton dau ky
            String firstQty = "0";
            queryString = " SELECT sdr.remain_quantity ";
            queryString += " FROM STOCK_DAILY_RPT sdr ";
            queryString += " WHERE sdr.stock_model_id = ? ";
            queryString += "     AND sdr.owner_id = ? ";
            queryString += "     AND sdr.created_date = ? ";
            queryObject = null;
            queryObject = getSession().createSQLQuery(queryString);
            queryObject.setParameter(0, stockModel.getStockModelId());
            queryObject.setParameter(1, shop.getObjId());
            queryObject.setParameter(2, fromDate);
            List lstFirstQty = queryObject.list();
            if (lstFirstQty != null && lstFirstQty.size()>0)
            firstQty = lstFirstQty.get(0).toString();

            TrongLV - New*/

            //Tinh ton cuoi moi ngay nhap/xuat
            String DATE_FORMAT = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Long tmpQty = 0L;
            if (firstQty != null && !firstQty.trim().equals("")) {
                tmpQty = Long.parseLong(firstQty);
            }
            if (lstTemp != null) {
                for (int iRow = 0; iRow < lstTemp.size(); iRow++) {
                    ReportStockTransBean tmpBean = lstTemp.get(iRow);
                    tmpBean.setTransCode(tmpBean.getStockTransId() + tmpBean.getActionCode());
                    tmpBean.setTransDate(sdf.format(DateTimeUtils.convertStringToDate(tmpBean.getCreateDatetime())));
                    tmpBean.setLastQty(tmpBean.getImpQty() - tmpBean.getExpQty() + tmpQty);
                    tmpQty = tmpBean.getLastQty();
                }
            }



            //UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            DATE_FORMAT = "yyyyMMddhh24mmss";
            sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "ReportStockTrans";
            templatePath = tmp + prefixPath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();

            beans.put(DATE_CREATE, DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put(USER_CREATE, userToken.getFullName());
            beans.put(FROM_DATE, fromDate);
            beans.put(TO_DATE, toDate);
            beans.put(SHOP_NAME, shop.getObjName());
            beans.put(STOCK_MODEL_CODE, stockModel.getStockModelCode());
            beans.put(STOCK_MODEL_NAME, stockModel.getName());
            beans.put(FIRST_QTY, firstQty);
            //beans.put(LAST_QTY, tmpQty);
            beans.put(LIST_ITEMS, lstTemp);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);

            //Thong bao len jsp
            req.setAttribute(REQUEST_REPORT_STOCK_TRANS_PATH, filePath);
            req.setAttribute(REQUEST_REPORT_STOCK_TRANS_MESSAGE, "report.stocktrans.error.successMessage");

            //Lay lai danh sach mat hang
//            String strQueryStockModel = " from StockModel where stockTypeId = ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
//            Query queryStockModel = getSession().createQuery(strQueryStockModel);
//            queryStockModel.setParameter(0, reportStockTransForm.getStockTypeId());
//            queryStockModel.setParameter(1, Constant.STATUS_USE);
//            List<StockModel> listStockModel = queryStockModel.list();
//            req.setAttribute("lstStockModel", listStockModel);

        } catch (Exception ex) {
            ex.printStackTrace();
            return pageForward;
        }
        //pageForward = REPORT_STOCK_TRANS;
        log.info("End method reportStockTrans of ReportStockTransDAO");

        return pageForward;
    }
}
