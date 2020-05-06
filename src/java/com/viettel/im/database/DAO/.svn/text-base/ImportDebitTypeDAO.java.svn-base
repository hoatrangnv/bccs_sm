/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.client.bean.AppParamsBean;
import com.viettel.im.client.form.ImportCommonForm;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ImportDebitTypeBO;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thinhph2_s
 */
public class ImportDebitTypeDAO extends ImportFileCommonDAO<ImportDebitTypeBO> {

    ImportCommonForm importCommonForm = new ImportCommonForm();
    String DEBIT_REQUEST_DETAIL_SEQ = "debit_request_detail_seq";
    String DEBIT_REQUEST_SEQ = "debit_request_seq";

    public ImportCommonForm getImportCommonForm() {
        return importCommonForm;
    }

    public void setImportCommonForm(ImportCommonForm importCommonForm) {
        this.importCommonForm = importCommonForm;
    }

    @Override
    protected ImportDebitTypeBO getRowData(Sheet sheet, int row) throws Exception {
        ImportDebitTypeBO importBO = new ImportDebitTypeBO();
        String staffCode = sheet.getCell(1, row).getContents().trim();
        String debitType = sheet.getCell(2, row).getContents().trim();
        String debitDayType = sheet.getCell(3, row).getContents().trim();
        Long staffId = null;
        String debitTypeL = "";
        if (StringUtils.validString(staffCode)) {
            importBO.setStaffCode(staffCode);
            try {
                staffId = new StaffDAO().findAvailableByStaffCodeNotCollaborator(staffCode).getStaffId();
                if (staffId == null || staffId == 0L) {
                    throw new Exception("Sai mã nhân viên");
                }
                importBO.setStaffId(staffId);
            } catch (Exception e) {
                throw new Exception("Sai mã nhân viên");
            }
        } else {
            //log.error("Ma tram rong. Row:" + row);
            throw new Exception("Không có dữ liệu");
        }
        if (StringUtils.validString(debitType)) {
            try {
                debitTypeL = new AppParamsDAO().findAppParamsByCode("DEBIT_TYPE", debitType).getCode();//kiem tra xem ma nay co hoat dong ko
                if (StringUtils.validString(debitTypeL)) {
                    importBO.setDebitType(Long.parseLong(debitTypeL));
                } else {
                    throw new Exception("Mã hạn mức kho không tồn tại");
                }
            } catch (Exception e) {
                throw new Exception("Lỗi!!!. Mã hạn mức kho không đúng");
            }
        } else {
            //log.error("Ma tram rong. Row:" + row);
            throw new Exception("Không có dữ liệu");
        }
        if (StringUtils.validString(debitDayType)) {
            try {
                String debitDayTypeL = new AppParamsDAO().findAppParamsByCode("DEBIT_DAY_TYPE", debitDayType).getCode();
                System.out.println("debitDayTypeL-"+debitDayTypeL);
                if (StringUtils.validString(debitDayTypeL)) {
                    if (checkMappingDebitTypeWithDayType(getSession(), debitTypeL, debitDayTypeL)) {//check xem dayType nhap vao da mapping voi debitType chua
                        importBO.setDebitDayType(Long.parseLong(debitDayTypeL));
                    } else {
                        throw new Exception("Mã ngày áp dụng chưa mapping với Mã hạn mức kho");
                    }
                } else {
                    throw new Exception("Mã ngày áp dụng không tồn tại");
                }
            } catch (Exception e) {
                throw new Exception("Lỗi!!!. Mã ngày áp dụng không đúng");
            }
        } else {
            //log.error("Ma tram rong. Row:" + row);
            throw new Exception("Không có dữ liệu");
        }
        
        if (!checkDebitRequestExistsNotActive(staffId, getSession(), debitDayType, "2")) {
            throw new Exception("Nhân viên đã có trong danh sách yêu cầu");
        }
        return importBO;
    }

    @Override
    protected void saveData(ImportDebitTypeBO bo, HttpServletRequest request) {
        HttpServletRequest req = getRequest();
        Session session = getSession();
        Long debitType = bo.getDebitType();
        Long debitDayType = bo.getDebitDayType();
        Long staffId = bo.getStaffId();
        try {

            Long requestDetailSeq = getSequence(DEBIT_REQUEST_DETAIL_SEQ);
            Long requestSeq = (Long) req.getAttribute("requestIdR693");
            String sqlInsertReqDetail = "Insert into debit_request_detail(request_detail_id,request_id,request_date,owner_id,owner_type,debit_type,debit_day_type,status,request_type) "
                    + "values(:request_detail_id,"
                    + ":request_id,"
                    + ":request_date,"
                    + ":owner_id,"
                    + ":owner_type,"
                    + ":debit_type,"
                    + ":debit_day_type,"
                    + ":status,"
                    + ":request_type)";
            Query query = getSession().createSQLQuery(sqlInsertReqDetail);
            query.setParameter("request_detail_id", requestDetailSeq);
            query.setParameter("request_id", requestSeq);
            query.setParameter("request_date", getSysdate());
            query.setParameter("owner_id", staffId);
            query.setParameter("owner_type", 2);
            query.setParameter("debit_type", debitType);
            query.setParameter("debit_day_type", debitDayType);
            query.setParameter("status", 1);
            query.setParameter("request_type", new ImportDebitTypeDAO().checkDebitRequestExistsActive(staffId, session, debitDayType.toString(), "1"));
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isDataExists(ImportDebitTypeBO bo) {
        return false;
    }

    public String importFile() {
        HttpServletRequest req = getRequest();
        Date nowDate = null;
        try {
            nowDate = getSysdate();
        } catch (Exception ex) {
            System.out.println("Get sys date error :" + ex.toString());
        }
        this.importFile(req, importCommonForm, nowDate);
        importCommonForm.setReturnMessage(getText(importCommonForm.getReturnMessage()));
        return "importFile";
    }

    public String preparePage() {

        return "preparePage";
    }

    @Override
    public void initReportInfo() {
        setBEGIN_DATA_COL(0);
        setBEGIN_DATA_ROW(1);
        needReportFailResult = true;
        setROW_NUMBER_MAX_IN_FILE(this.getMaxRowImport());
    }

    @Override
    protected void saveParent() {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        try {
            String sqlInsertDebit = "insert into debit_request(request_id,request_code, create_date,create_user, "
                    + "status, file_name, file_content) "
                    + " values(:request_id,:request_code, :create_date,:create_user, "
                    + ":status, :fileName, :fileContent)";

            Long requestSeq = (Long) req.getAttribute("requestIdR693");
            Query query = getSession().createSQLQuery(sqlInsertDebit);
            query.setParameter("request_id", requestSeq);
            query.setParameter("request_code", "RC_" + requestSeq);
            query.setParameter("create_date", getSysdate());
            query.setParameter("create_user", userToken.getLoginName().trim());
            query.setParameter("status", 1);
            query.setParameter("fileName", importCommonForm.getClientFileName().trim().replace(" ", "_"));
            query.setParameter("fileContent", importCommonForm.getFileContent());
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void getSequenceParent() {
        try {
            HttpServletRequest req = getRequest();
            Long requestId = getSequence(DEBIT_REQUEST_SEQ);
            req.setAttribute("requestIdR693", requestId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private boolean checkMappingDebitTypeWithDayType(Session session, String debitType, String dayType) {
        String sql = "select 'x' from dual where ? in  ( "
                + " SELECT   app.code AS debit_day_type_name  "
                + " FROM    debit_type dt,  app_params app  "
                + " WHERE       dt.debit_type = ?  "
                + " AND app.TYPE = 'DEBIT_DAY_TYPE'  "
                + " AND dt.status = 1  "
                + " AND app.status = 1  "
                + " AND dt.debit_day_type = app.code )";
        try {
            Query query = session.createSQLQuery(sql);
            query.setParameter(0, dayType);
            query.setParameter(1, debitType);
            List list = query.list();
            if (list.isEmpty()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected String otherValidate(ImportCommonForm inputForm) {
        try {
            if (StringUtils.validString(importCommonForm.getServerFileName())) {
                String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
                String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + importCommonForm.getServerFileName());
                File serverFile = new File(serverFilePath);
                if (serverFile.exists()) {
                    importCommonForm.setFileContent(FileUtils.readFileToByteArray(serverFile));
                } else {
                    //bao loi
                    return "Chưa nhập File đính kèm !!!";
                }
            } else {
                //bao loi
                return "Chưa nhập File đính kèm !!!";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public String checkDebitRequestExistsActive(Long staffId, Session session, String debitDayType, String ownerType) {
        try {
            String sql = "select 1 from dual where  EXISTS(select 1 from debit_request_detail where owner_id = ? and status = 2 and debit_day_type = ? and owner_type = ?)";
            Query query = session.createSQLQuery(sql);
            query.setLong(0, staffId);
            query.setParameter(1, debitDayType);
            query.setParameter(2, ownerType);
            if (!query.list().isEmpty()) {
                return "2";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
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
}
