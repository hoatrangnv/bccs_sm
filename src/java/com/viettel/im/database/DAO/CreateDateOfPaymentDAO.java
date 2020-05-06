/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.DebitRequestDetailBean;
import com.viettel.im.client.form.CreateRequestDebitForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.DebitType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

/**
 *
 * @author ThinhPH2
 */
public class CreateDateOfPaymentDAO extends BaseDAO {

    CreateRequestDebitForm dateOfPaymentForm = new CreateRequestDebitForm();
    String REQUEST_FINANCE_MAIN = "preparePage";
    String LIST_REQUEST_RESULT = "listResult";
    String LIST_DATE_OF_PAYMENT_SESSION_OBJ = "lstRequestDebit";
    String DEBIT_REQUEST_DETAIL_SEQ = "debit_request_detail_seq";
    String DEBIT_REQUEST_SEQ = "debit_request_seq";
    String CREATE_DATE_OF_PAYMENT_FOR_EMP = "dateOfPaymentForEmp";
    private Map listDebitDayCombo = new HashMap();

    public Map getListDebitDayCombo() {
        return listDebitDayCombo;
    }

    public void setListDebitDayCombo(Map listDebitDayCombo) {
        this.listDebitDayCombo = listDebitDayCombo;
    }

    public CreateRequestDebitForm getCreateRequestDebitForm() {
        return dateOfPaymentForm;
    }

    public void setCreateRequestDebitForm(CreateRequestDebitForm createRequestDebitForm) {
        this.dateOfPaymentForm = createRequestDebitForm;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        String pareparePage = REQUEST_FINANCE_MAIN;
        req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
        dateOfPaymentForm.resetFormQuery();
        dateOfPaymentForm.resetForm();
        return pareparePage;
    }

    public String addRequestDebit() {
        HttpServletRequest req = getRequest();
        String preparePage = CREATE_DATE_OF_PAYMENT_FOR_EMP;
        try {
            List<DebitRequestDetailBean> lstReqDebit = (List) getTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ);
            if (lstReqDebit == null) {
                lstReqDebit = new ArrayList();
                setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, lstReqDebit);
            }
            String validateResult = validateInsert(dateOfPaymentForm);
            //Validate du lieu dau vao
            if (validateResult != null) {
                req.setAttribute("returnMsg", validateResult);
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
//                dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }

            //thinhph2 bo sung check qua so ban ghi toi da
            int maxRow = new ImportDebitTypeDAO().getMaxRowImport();
            if (lstReqDebit.size() > maxRow - 1) {
                req.setAttribute("returnMsg", "Danh sách yêu cầu không được vượt quá " + maxRow);
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
//                dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }
            //end thinhph2

            DebitRequestDetailBean debitRequestDetailBean = new DebitRequestDetailBean();
            Staff staff = new StaffDAO().findAvailableByStaffCode(dateOfPaymentForm.getStaffCode().trim());

            //kiem tra nhan vien nhap vao co trong danh sach chua duyet yc hay ko
            if (!checkDebitRequestExistsNotActive(staff.getStaffId(), getSession(), dateOfPaymentForm.getDebitDayType().trim(), "2")) {
                req.setAttribute("returnMsg", "err.nhan_vien_da_co_trong_dsyc");
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
//                dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }
            
            Shop shop = new ShopDAO().findById(staff.getShopId());
            if(shop == null){
                req.setAttribute("returnMsg", "err.khong_tim_thay_don_vi");
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
//                dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }
            
            //kiem tra han muc moi phai > gia tri kho
            Double currentDebitStaff = getCurrentDebit(2L, staff.getStaffId(), shop.getPricePolicy());
            String[] propertyName = {"debitType","debitDayType"};
            Object[] value = {dateOfPaymentForm.getDebitType(), dateOfPaymentForm.getDebitDayType()};
            List lstDebitType = new DebitTypeDAO().findByProperty(propertyName, value);
            Long debitTypeValue = 0L;
            if(!lstDebitType.isEmpty()){
                DebitType dt = (DebitType) lstDebitType.get(0);
                debitTypeValue = dt.getMaxDebit();
            }
//            System.out.println("----------------------HMG----------------------------"+debitTypeValue);
//            System.out.println("----------------------GTK----------------------------"+currentDebitStaff);
            /*if(currentDebitStaff > debitTypeValue){
                req.setAttribute("returnMsg", "err.limit_value_great_than_current_value");
                List params = new ArrayList();
                params.add(debitTypeValue);
                params.add(currentDebitStaff);
                req.setAttribute("returnMsgParam", params);
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
//                dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }*/
            
            AppParams appParamsDebitType = new AppParamsDAO().findAppParams(Constant.DEBIT_TYPE, dateOfPaymentForm.getDebitType().trim());
            AppParams appParamsDebitDayType = new AppParamsDAO().findAppParams(Constant.DEBIT_DAY_TYPE, dateOfPaymentForm.getDebitDayType().trim());

            
            String[] property = new String[]{"debitType","debitDayType"};
            Object[] object = new Object[]{dateOfPaymentForm.getDebitType(),dateOfPaymentForm.getDebitDayType()};
            List listDebitType = new DebitTypeDAO().findByProperty(property,object);
            if(!listDebitType.isEmpty()){
                DebitType dbt =(DebitType) listDebitType.get(0);
                debitRequestDetailBean.setDebitTypeOld(dbt.getMaxDebit().toString());
            }else{
                debitRequestDetailBean.setDebitTypeOld("-");
            }

            debitRequestDetailBean.setOwnerId(staff.getStaffId());
            debitRequestDetailBean.setOwnerType(2L);
            debitRequestDetailBean.setDebitDayType(appParamsDebitDayType.getCode().trim());
            debitRequestDetailBean.setDebitDayTypeDisplay(appParamsDebitDayType.getName().trim());
            debitRequestDetailBean.setDebitType(appParamsDebitType.getCode().trim());
            debitRequestDetailBean.setDebitTypeDisplay(appParamsDebitType.getName().trim());
            debitRequestDetailBean.setRequestDate(getSysdate());
            debitRequestDetailBean.setRequestDetailId(getSequence(DEBIT_REQUEST_DETAIL_SEQ));
            debitRequestDetailBean.setShopCode(dateOfPaymentForm.getShopCode().toString().trim());
            debitRequestDetailBean.setShopName(dateOfPaymentForm.getShopName().toString().trim());
            debitRequestDetailBean.setStaffCode(dateOfPaymentForm.getStaffCode().toString().trim());
            debitRequestDetailBean.setStaffName(dateOfPaymentForm.getStaffName().toString().trim());
            debitRequestDetailBean.setStatus(1L);
            debitRequestDetailBean.setDescription(dateOfPaymentForm.getDescription().trim());

            for (DebitRequestDetailBean reqDebit : lstReqDebit) {
                if (reqDebit.getOwnerId().equals(debitRequestDetailBean.getOwnerId())
                        && reqDebit.getDebitDayType().trim().equals(debitRequestDetailBean.getDebitDayType().trim())) {
                    req.setAttribute("returnMsg", "err.nhan_vien_nhap_da_ton_tai");
                    req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                    req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
//                    dateOfPaymentForm.resetFormQuery();
                    return preparePage;
                }
            }

            lstReqDebit.add(debitRequestDetailBean);
            setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, lstReqDebit);
            req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
            req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
            dateOfPaymentForm.resetFormQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparePage;
    }

    public String delRequetstDebit() {
        HttpServletRequest req = getRequest();
        String preparePage = CREATE_DATE_OF_PAYMENT_FOR_EMP;
        getReqSession();
        try {
            List lstRequestDebit = (List) getTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ);
            if (lstRequestDebit == null) {
                req.setAttribute("returnMsg", "saleRetail.warn.Empty");
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
                return preparePage;
            }
            String requestDetailId = req.getParameter("requestDetailId");
            String debitDayType = req.getParameter("debitDayType");

            if (requestDetailId != null && !"".equals(requestDetailId.trim()) && debitDayType != null && !"".equals(debitDayType.trim())) {
                Long requestDetail = Long.parseLong(requestDetailId.trim());
                for (int i = 0; i < lstRequestDebit.size(); i++) {
                    if (lstRequestDebit.get(i) instanceof DebitRequestDetailBean) {
                        DebitRequestDetailBean debitRequestDetailBean = (DebitRequestDetailBean) lstRequestDebit.get(i);
                        if (debitRequestDetailBean.getRequestDetailId().equals(requestDetail) && debitRequestDetailBean.getDebitDayType().equals(debitDayType)) {
                            lstRequestDebit.remove(i);
                        }
                    }
                }
            }
            req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
            req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
            dateOfPaymentForm.resetFormQuery();
            setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, lstRequestDebit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparePage;
    }

    public String saveDebitRequest() {
        String preparePage = REQUEST_FINANCE_MAIN;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session session = getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        try {
            Long requestDebit = getSequence(DEBIT_REQUEST_SEQ);
            if (StringUtils.validString(dateOfPaymentForm.getServerFileName())) {
                String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + dateOfPaymentForm.getServerFileName());
                File serverFile = new File(serverFilePath);
                if (serverFile.exists()) {
                    dateOfPaymentForm.setFileContent(FileUtils.readFileToByteArray(serverFile));
                } else {
                    req.setAttribute("returnMsg", "Lỗi!!!. Chưa chọn file đính kèm.");
                    tx.rollback();
                    req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                    req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
                    return preparePage;
                }
            } else {
                req.setAttribute("returnMsg", "Lỗi!!!. Chưa chọn file đính kèm.");
                tx.rollback();
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
                return preparePage;
            }
            int result = this.InsertDebitRequest(session, dateOfPaymentForm, userToken, requestDebit);
            if (result > 0) {
                List<DebitRequestDetailBean> lstReqDebit = (List) getTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ);
                if (lstReqDebit == null) {
                    req.setAttribute("returnMsg", "err.khong_tim_thay_dsyc");
                    tx.rollback();
                    req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                    req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
                    return preparePage;
                }
                int rsUpdate = this.InsertDebitRequestDetail(lstReqDebit, session, requestDebit, dateOfPaymentForm);
                if (rsUpdate > 0) {
                    req.setAttribute("returnMsg", "msg.lap_yc_thanh_cong");
                    List<DebitRequestDetailBean> listTemp = new ArrayList<DebitRequestDetailBean>();
                    setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, listTemp);
                    dateOfPaymentForm.resetForm();
                } else {
                    req.setAttribute("returnMsg", "");
                    tx.rollback();
                    req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                    req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
                    return preparePage;
                }
            } else {
                req.setAttribute("returnMsg", "Lập yêu cầu thất bại");
                tx.rollback();
                req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
                req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
                return preparePage;
            }
            req.setAttribute("listFinanceType", new AppParamsDAO().findAppParamsByType(Constant.FINANCE_TYPE));
            req.setAttribute("listDebitType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_TYPE));
        } catch (Exception ex) {
            Logger.getLogger(CreateDateOfPaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        tx.commit();
        return preparePage;
    }

    public String validateInsert(CreateRequestDebitForm inputForm) {
        String megResult = null;
        String shopCode = inputForm.getShopCode();
        String shopName = inputForm.getShopName();
        String staffCode = inputForm.getStaffCode();
        String staffName = inputForm.getStaffName();
        String debitType = inputForm.getDebitType();
        String debitDayType = inputForm.getDebitDayType();

        if (shopCode == null || shopCode.equals("")) {
            megResult = "Lỗi!!!. Chưa nhập mã cửa hàng";
            return megResult;
        }
        if (shopName == null || shopName.equals("")) {
            megResult = "err.chua_nhap_ten_cua_hang";
            return megResult;
        }
        if (staffCode == null || staffCode.equals("")) {
            megResult = "Lỗi!!!. Chưa nhập mã nhân viên";
            return megResult;
        }
        if (staffName == null || staffName.equals("")) {
            megResult = "err.chua_nhap_ten_nv";
            return megResult;
        }
        if (debitType == null || debitType.equals("")) {
            megResult = "Lỗi!!!. Chưa chọn hạn mức";
            return megResult;
        }
        if (debitDayType == null || debitDayType.equals("")) {
            megResult = "Lỗi!!!. Chưa chọn ngày áp dụng";
            return megResult;
        }
        return megResult;
    }

    public int InsertDebitRequest(Session session, CreateRequestDebitForm inputForm, UserToken userToken, Long requestDebit) {
        int result = 0;
        try {
            String sql = "insert into debit_request(request_id,request_code, create_date,create_user, "
                    + "file_name,file_content,descripttion,status) "
                    + " values(:request_id,:request_code, :create_date,:create_user, "
                    + ":file_name,:file_content,:descripttion,:status)";
            Query query = session.createSQLQuery(sql);
            query.setParameter("request_id", requestDebit);
            query.setParameter("request_code", "RC_" + requestDebit);
            query.setParameter("create_date", getSysdate());
            query.setParameter("create_user", userToken.getLoginName().trim());
            query.setParameter("file_name", inputForm.getClientFileName().trim().replace(" ", "_"));
            query.setParameter("file_content", inputForm.getFileContent());
            query.setParameter("descripttion", inputForm.getNote().trim());
            query.setParameter("status", 1);
            result = query.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public int InsertDebitRequestDetail(List<DebitRequestDetailBean> listDebitRequest, Session session, Long requestDebit, CreateRequestDebitForm inputForm) {
        int result = 0;
        try {
            String sql = "Insert into debit_request_detail(request_detail_id,request_id,request_date,owner_id,owner_type,debit_type,debit_day_type,note,status,request_type) "
                    + "values(:request_detail_id,"
                    + ":request_id,"
                    + ":request_date,"
                    + ":owner_id,"
                    + ":owner_type,"
                    + ":debit_type,"
                    + ":debit_day_type,"
                    + ":note,"
                    + ":status,"
                    + ":request_type)";
            for (DebitRequestDetailBean debitRequestBean : listDebitRequest) {
                Query query = session.createSQLQuery(sql);
                query.setLong("request_detail_id", getSequence(DEBIT_REQUEST_DETAIL_SEQ));
                query.setLong("request_id", requestDebit);
                query.setDate("request_date", debitRequestBean.getRequestDate());
                query.setLong("owner_id", debitRequestBean.getOwnerId());
                query.setLong("owner_type", debitRequestBean.getOwnerType());
                query.setParameter("debit_type", debitRequestBean.getDebitType().trim());
                query.setParameter("debit_day_type", debitRequestBean.getDebitDayType().trim());
                query.setString("note", debitRequestBean.getDescription().trim());
                query.setLong("status", 1);
                if (debitRequestBean.getDebitTypeOld().equals("-")) {
                    query.setLong("request_type", Constant.REQ_TYPE_ADD);
                } else {
                    query.setLong("request_type", Constant.REQ_TYPE_UPDATE);
                }
                int rsUpdate = query.executeUpdate();
                result += rsUpdate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkDebitRequestExistsNotActive(Long staffId, Session session, String debitDayType, String ownerType) {
        try {
            String sql = "select 1 from dual where  EXISTS(select 1 from debit_request_detail where owner_id = ? and status = 1 and debit_day_type = ? and owner_type = ?)";
            Query query = session.createSQLQuery(sql);
            query.setLong(0, staffId);
            query.setParameter(1, debitDayType);
            query.setParameter(2, ownerType);
            if (!query.list().isEmpty()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String pageNavigator() {
        String pareparePage = REQUEST_FINANCE_MAIN;
        return pareparePage;
    }

    public String getListDebitDayType() {
        HttpServletRequest req = getRequest();
        String debitType = req.getParameter("debitType");

        try {
            String sql = " SELECT   app.code, app.name AS debit_day_type_name "
                    + " FROM   debit_type dt, app_params app "
                    + " WHERE       dt.debit_type = ?  "
                    + " AND app.TYPE = 'DEBIT_DAY_TYPE' "
                    + " AND dt.status = 1 "
                    + " AND app.status = 1 "
                    + " AND dt.debit_day_type = app.code ";
            Query query = getSession().createSQLQuery(sql);
            query.setParameter(0, debitType);
            List collectionGroup = query.list();

            List tmpList = new ArrayList();
            String[] header = {"", "---Loại ngày AD---"};
            tmpList.add(header);
            tmpList.addAll(collectionGroup);
            listDebitDayCombo.put("debitDayType", tmpList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    public DebitRequestDetailBean getRequestDebitOld(Session session, Long staffId) {
        DebitRequestDetailBean debitRequestDetailBean = new DebitRequestDetailBean();
        String sql = "SELECT   a.debit_type AS debitTypeOld, c.name AS debitDayTypeOld "
                + " FROM   stock_debit a, app_params b, app_params c  "
                + " WHERE       a.owner_id = ? "
                + " AND a.owner_type = 2 "
                + " AND b.status = 1 "
                + " AND c.status = 1 "
                + " AND b.TYPE = 'DEBIT_TYPE' "
                + " AND b.code = a.debit_type "
                + " AND c.TYPE = 'DEBIT_DAY_TYPE' "
                + " AND c.code = a.debit_day_type";
        try {
            Query query = session.createSQLQuery(sql).addScalar("debitTypeOld", Hibernate.STRING).addScalar("debitDayTypeOld", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(DebitRequestDetailBean.class));
            query.setParameter(0, staffId);
            List<DebitRequestDetailBean> listDebitRequestDetailBean = query.list();
            if (!listDebitRequestDetailBean.isEmpty()) {
                debitRequestDetailBean = listDebitRequestDetailBean.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return debitRequestDetailBean;
    }

    public Long getDebitTypeOld(Session session, String debitType, String debitDayType) {
        BigDecimal debitTypeOld = BigDecimal.ZERO;
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append(" dt.max_debit ");
        sql.append(" from ");
        sql.append(" debit_type dt, app_params ap ");
        sql.append(" where dt.debit_day_type = ap.code ");
        sql.append(" and ap.type = 'DEBIT_DAY_TYPE' ");
        sql.append(" and ap.status = 1 ");
        sql.append(" and dt.debit_type = ? ");
        sql.append(" and ap.code = ? ");
        try {
            Query query = session.createSQLQuery(sql.toString());
            query.setParameter(0, debitType);
            query.setParameter(1, debitDayType);

            List lst = query.list();
            if (!lst.isEmpty()) {
                debitTypeOld = (BigDecimal) lst.get(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return debitTypeOld.longValue();
    }
}
