/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.DebitRequestDetailBean;
import com.viettel.im.client.bean.DebitRequestDetailShopBean;
import com.viettel.im.client.form.CreateRequestDebitForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
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
public class CreateRequestDebitStockShopDAO extends BaseDAO {

    CreateRequestDebitForm dateOfPaymentForm = new CreateRequestDebitForm();
    String CREATE_REQUEST_MAIN = "preparePage";
    String LIST_REQUEST_RESULT = "listResult";
    String LIST_DATE_OF_PAYMENT_SESSION_OBJ = "lstRequestDebit";
    String DEBIT_REQUEST_DETAIL_SEQ = "debit_request_detail_seq";
    String DEBIT_REQUEST_SEQ = "debit_request_seq";
    String CREATE_DATE_OF_PAYMENT_FOR_EMP = "queryFormDetail";
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
        String pareparePage = CREATE_REQUEST_MAIN;
        getCombobox(req);

        dateOfPaymentForm.resetFormQuery();
        dateOfPaymentForm.resetForm();
        return pareparePage;
    }

    public String addRequestDebit() {
        HttpServletRequest req = getRequest();
        String preparePage = CREATE_DATE_OF_PAYMENT_FOR_EMP;
        try {
            List<DebitRequestDetailShopBean> lstReqDebit = (List) getTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ);
            if (lstReqDebit == null) {
                lstReqDebit = new ArrayList();
                setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, lstReqDebit);
            }
            String validateResult = validateInsert(dateOfPaymentForm);
            //Validate du lieu dau vao
            if (validateResult != null) {
                req.setAttribute("returnMsg", validateResult);
                getCombobox(req);
                //dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }
            //gioi han so ban ghi yeu cau
            int maxRow = getMaxRowImport();
            if (lstReqDebit.size() > maxRow - 1) {
                req.setAttribute("returnMsg", "Danh sách yêu cầu không được vượt quá " + maxRow);
                getCombobox(req);
                dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }

            DebitRequestDetailShopBean debitRequestDetailBean = new DebitRequestDetailShopBean();
            //Kiem tra shop co ton tai khong
            Shop shop = null;
            shop = new ShopDAO().findShopsAvailableByShopCode(dateOfPaymentForm.getShopCode().trim());
            if (shop == null) {
                req.setAttribute("returnMsg", "ERR.MSG.no.shop");
                getCombobox(req);
                //dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }
            //kiem tra don vi nhap vao co trong danh sach chua duyet yc hay ko
            if (!checkDebitRequestExistsNotActive(shop.getShopId(), getSession(), dateOfPaymentForm.getDebitDayType().trim(), "1",dateOfPaymentForm.getRequestDebitType(),dateOfPaymentForm.getStockTypeId())) {
                req.setAttribute("returnMsg", "err.loi_cua_hang_nhap_da_co_trong_dsyc");
                getCombobox(req);
                //dateOfPaymentForm.resetFormQuery();
                return preparePage;
            }

//            AppParams appParamsFinanceType = new AppParamsDAO().findAppParams(Constant.FINANCE_TYPE, dateOfPaymentForm.getFinanceType().trim());
//            AppParams appParamsDebitType = new AppParamsDAO().findAppParams(Constant.DEBIT_TYPE, dateOfPaymentForm.getDebitType().trim());
            AppParams appParamsDebitDayType = new AppParamsDAO().findAppParams(Constant.DEBIT_DAY_TYPE, dateOfPaymentForm.getDebitDayType().trim());

            DebitRequestDetailShopBean debitRequestDetailOld = this.getRequestDebitOld(getSession(), shop.getShopId(), dateOfPaymentForm.getDebitDayType().trim(), dateOfPaymentForm.getRequestDebitType(), dateOfPaymentForm.getStockTypeId());
            if (debitRequestDetailOld.getDebitTypeOld() != null) {
                debitRequestDetailBean.setDebitTypeOld(debitRequestDetailOld.getDebitTypeOld());
                debitRequestDetailBean.setRequestDebitTypeOld(debitRequestDetailOld.getRequestDebitTypeOld());
            } else {
                debitRequestDetailBean.setDebitTypeOld("--");
                debitRequestDetailBean.setRequestDebitTypeOld(3L);
            }
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            StockType stockType = stockTypeDAO.findById(dateOfPaymentForm.getStockTypeId());
            if (stockType != null) {
                debitRequestDetailBean.setStockTypeId(dateOfPaymentForm.getStockTypeId());
                debitRequestDetailBean.setStockTypeName(stockType.getName());
            } else {
                req.setAttribute("returnMsg", "Không tìm thấy mặt hàng này");
                getCombobox(req);
                return preparePage;
            }

            debitRequestDetailBean.setOwnerId(shop.getShopId());
            debitRequestDetailBean.setOwnerType(1L);
            debitRequestDetailBean.setDebitDayType(appParamsDebitDayType.getCode().trim());
            debitRequestDetailBean.setDebitDayTypeDisplay(appParamsDebitDayType.getName().trim());
            debitRequestDetailBean.setDebitType(Long.parseLong(dateOfPaymentForm.getDebitType().trim()));
            debitRequestDetailBean.setDebitTypeDisplay(dateOfPaymentForm.getDebitType().trim());
            debitRequestDetailBean.setRequestDate(getSysdate());
            debitRequestDetailBean.setRequestDetailId(getSequence(DEBIT_REQUEST_DETAIL_SEQ));
            debitRequestDetailBean.setShopCode(dateOfPaymentForm.getShopCode().toString().trim());
            debitRequestDetailBean.setShopName(dateOfPaymentForm.getShopName().toString().trim());
            debitRequestDetailBean.setStatus(1L);
            debitRequestDetailBean.setDescription(dateOfPaymentForm.getDescription().trim());
            debitRequestDetailBean.setRequestDebitType(dateOfPaymentForm.getRequestDebitType());
            for (DebitRequestDetailShopBean reqDebit : lstReqDebit) {
                if (reqDebit.getOwnerId().equals(debitRequestDetailBean.getOwnerId()) 
                        && reqDebit.getDebitDayType().trim().equals(debitRequestDetailBean.getDebitDayType().trim())
                        && reqDebit.getStockTypeId().compareTo(debitRequestDetailBean.getStockTypeId()) == 0
                        ) {
                    req.setAttribute("returnMsg", "err.don_vi_da_ton_tai");
                    getCombobox(req);
                    return preparePage;
                }
            }
            lstReqDebit.add(debitRequestDetailBean);
            setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, lstReqDebit);
            getCombobox(req);
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
                getCombobox(req);
                return preparePage;
            }
            String requestDetailId = req.getParameter("requestDetailId");
            String debitDayType = req.getParameter("debitDayType");

            if (requestDetailId != null && !"".equals(requestDetailId.trim()) && debitDayType != null && !"".equals(debitDayType.trim())) {
                Long requestDetail = Long.parseLong(requestDetailId.trim());
                for (int i = 0; i < lstRequestDebit.size(); i++) {
                    if (lstRequestDebit.get(i) instanceof DebitRequestDetailShopBean) {
                        DebitRequestDetailShopBean debitRequestDetailBean = (DebitRequestDetailShopBean) lstRequestDebit.get(i);
                        if (debitRequestDetailBean.getRequestDetailId().equals(requestDetail) && debitRequestDetailBean.getDebitDayType().equals(debitDayType)) {
                            lstRequestDebit.remove(i);
                        }
                    }
                }
            }
            getCombobox(req);
            dateOfPaymentForm.resetFormQuery();
            setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, lstRequestDebit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return preparePage;
    }

    public String saveDebitRequest() {
        String preparePage = CREATE_REQUEST_MAIN;
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
                    getCombobox(req);
                    return preparePage;
                }
            } else {
                req.setAttribute("returnMsg", "Lỗi!!!. Chưa chọn file đính kèm.");
                tx.rollback();
                getCombobox(req);
                return preparePage;
            }
            int result = this.InsertDebitRequest(session, dateOfPaymentForm, userToken, requestDebit);
            if (result > 0) {
                List<DebitRequestDetailShopBean> lstReqDebit = (List) getTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ);
                if (lstReqDebit == null) {
                    req.setAttribute("returnMsg", "err.khong_tim_thay_ds_yeu_cau");
                    tx.rollback();
                    getCombobox(req);
                    return preparePage;
                }
                int rsUpdate = this.InsertDebitRequestDetail(lstReqDebit, session, requestDebit, dateOfPaymentForm);
                if (rsUpdate > 0) {
                    req.setAttribute("returnMsg", "msg.lap_yc_thanh_cong");
                    List<DebitRequestDetailBean> listTemp = new ArrayList<DebitRequestDetailBean>();
                    setTabSession(LIST_DATE_OF_PAYMENT_SESSION_OBJ, listTemp);
                    dateOfPaymentForm.resetForm();
                } else {
                    req.setAttribute("returnMsg", "msg.lap_yc_that_bai");
                    tx.rollback();
                    getCombobox(req);
                    return preparePage;
                }
            } else {
                req.setAttribute("returnMsg", "msg.lap_yc_that_bai");
                tx.rollback();
                getCombobox(req);
                return preparePage;
            }
            getCombobox(req);
        } catch (Exception ex) {
            Logger.getLogger(CreateRequestDebitStockShopDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        tx.commit();
        return preparePage;
    }

    public String validateInsert(CreateRequestDebitForm inputForm) {
        String megResult = null;
        String shopCode = inputForm.getShopCode() == null ? "" : inputForm.getShopCode().trim();
        String shopName = inputForm.getShopName() == null ? "" : inputForm.getShopName().trim();
        String debitType = inputForm.getDebitType() == null ? "" : inputForm.getDebitType().trim();
        String debitDayType = inputForm.getDebitDayType() == null ? "" : inputForm.getDebitDayType().trim();

        if (shopCode == null || shopCode.equals("")) {
            megResult = "Lỗi!!!. Chưa nhập mã cửa hàng";
            return megResult;
        }

        if (shopName == null || shopName.equals("err.chua_nhap_ten_cua_hang")) {
            megResult = "";
            return megResult;
        }

        if (debitType == null || debitType.equals("")) {
            megResult = "Lỗi!!!. Chưa chọn hạn mức";
            return megResult;
        }

        try {
            Long.parseLong(debitDayType);
        } catch (Exception ex) {
            megResult = "Lỗi!!!. Hạn mức kho không phải là số";
            return megResult;
        }
        if (debitDayType.length() > 20) {
            megResult = "Lỗi!!!. Hạn mức kho không được vượt quá 20 chữ số";
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
                    + "file_name,file_content,descripttion,status,request_object_type ) "
                    + " values(:request_id,:request_code, :create_date,:create_user, "
                    + ":file_name,:file_content,:descripttion,:status,:request_object_type)";
            Query query = session.createSQLQuery(sql);
            query.setParameter("request_id", requestDebit);
            query.setParameter("request_code", "RC_" + requestDebit);
            query.setParameter("create_date", getSysdate());
            query.setParameter("create_user", userToken.getLoginName().trim());
            query.setParameter("file_name", inputForm.getClientFileName().trim().replace(" ", "_"));
            query.setParameter("file_content", inputForm.getFileContent());
            query.setParameter("descripttion", inputForm.getNote().trim());
            query.setParameter("status", 1);
            query.setParameter("request_object_type", 1);
            result = query.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public int InsertDebitRequestDetail(List<DebitRequestDetailShopBean> listDebitRequest, Session session, Long requestDebit, CreateRequestDebitForm inputForm) {
        int result = 0;
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("Insert into debit_request_detail(request_detail_id,request_id,request_date,owner_id,owner_type,");
            sql.append("debit_type,debit_day_type,note,status,request_type,request_debit_type, stock_type_id) ");
            sql.append("values(:request_detail_id,");
            sql.append(":request_id,");
            sql.append(":request_date,");
            sql.append(":owner_id,");
            sql.append(":owner_type,");
            sql.append(":debit_type,");
            sql.append(":debit_day_type,");
            sql.append(":note,");
            sql.append(":status,");
            sql.append(":request_type,");
            sql.append(":request_debit_type,");
            sql.append(":stock_type_id)");
            for (DebitRequestDetailShopBean debitRequestBean : listDebitRequest) {
                Query query = session.createSQLQuery(sql.toString());
                query.setLong("request_detail_id", getSequence(DEBIT_REQUEST_DETAIL_SEQ));
                query.setLong("request_id", requestDebit);
                query.setDate("request_date", debitRequestBean.getRequestDate());
                query.setLong("owner_id", debitRequestBean.getOwnerId());
                query.setLong("owner_type", debitRequestBean.getOwnerType());
                query.setParameter("debit_type", debitRequestBean.getDebitType());
                query.setParameter("debit_day_type", debitRequestBean.getDebitDayType().trim());
                query.setString("note", debitRequestBean.getDescription().trim());
                query.setLong("status", 1);
                if (debitRequestBean.getDebitTypeOld().trim().equals("--")) {
                    query.setLong("request_type", Constant.REQ_TYPE_ADD);
                } else {
                    query.setLong("request_type", Constant.REQ_TYPE_UPDATE);
                }
                query.setLong("request_debit_type", debitRequestBean.getRequestDebitType());
                query.setLong("stock_type_id", debitRequestBean.getStockTypeId());
                int rsUpdate = query.executeUpdate();
                result += rsUpdate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkDebitRequestExistsNotActive(Long shopId, Session session, String debitDayType, String ownerType,
            Long requestDebitType, Long stockTypeId) {
        try {
            String sql = "select 1 from dual where  EXISTS(select 1 from debit_request_detail where owner_id = ? and status = 1 "
                    + "and debit_day_type = ? and owner_type = ? and stock_type_id = ?)";
            Query query = session.createSQLQuery(sql);
            query.setLong(0, shopId);
            query.setParameter(1, debitDayType);
            query.setParameter(2, ownerType);
//            query.setParameter(3, requestDebitType);
            query.setParameter(3, stockTypeId);
            if (!query.list().isEmpty()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String pageNavigator() {
        String pareparePage = CREATE_REQUEST_MAIN;
        return pareparePage;
    }

    public DebitRequestDetailShopBean getRequestDebitOld(Session session, Long shopId, String debitDayType, Long requestDebit, Long stockTypeId) {
        DebitRequestDetailShopBean debitRequestDetailBean = new DebitRequestDetailShopBean();
        String sql = "SELECT   a.debit_type  AS debitTypeOld, a.request_debit_type requestDebitTypeOld  FROM   stock_debit a "
                + " WHERE       a.owner_id = ?  AND a.owner_type = 1  AND a.debit_day_type = ? and a.stock_type_id = ? ";
        System.out.println("shopId|"+shopId+"|debitDayType|"+debitDayType+"|requestDebit|"+requestDebit+"|stockTypeId|"+stockTypeId);
        try {
            Query query = session.createSQLQuery(sql)
                    .addScalar("debitTypeOld", Hibernate.STRING)
                    .addScalar("requestDebitTypeOld", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(DebitRequestDetailShopBean.class));
            query.setParameter(0, shopId);
            query.setParameter(1, debitDayType);
//            query.setParameter(2, requestDebit);
            query.setParameter(2, stockTypeId);
            List<DebitRequestDetailShopBean> listDebitRequestDetailBean = query.list();
            if (!listDebitRequestDetailBean.isEmpty()) {
                debitRequestDetailBean = listDebitRequestDetailBean.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return debitRequestDetailBean;
    }

    public int getMaxRowImport() {
        String sql = "select code from app_params where status = 1 and type = 'MAX_ROW_IMPORT_TYPE'";
        String maxRow = "0";
        try {
            Query query = getSession().createSQLQuery(sql);
            List lst = query.list();
            if (!lst.isEmpty()) {
                maxRow = (String) lst.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(maxRow);
    }

    public void getCombobox(HttpServletRequest req) {
        try {
            req.setAttribute("listDebitDayType", new AppParamsDAO().findAppParamsByType(Constant.DEBIT_DAY_TYPE));
            req.setAttribute("lstStockTypeId", new StockTypeDAO().findByStatus(1L));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
