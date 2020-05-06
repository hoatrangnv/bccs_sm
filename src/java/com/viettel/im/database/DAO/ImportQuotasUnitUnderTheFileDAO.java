/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import static com.viettel.database.DAO.BaseDAOAction.getSafeFileName;
import com.viettel.im.client.form.QuotasAssignedSingleForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author thindm
 */
public class ImportQuotasUnitUnderTheFileDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(ImportQuotasUnitUnderTheFileDAO.class);
    private String pageForward;
    private final String IMP_QUOTAS_UNIT_FILE = "importQuotasUnitFile";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_EXCEL_FILE_PATH_MESSAGE = "filePathMessage";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    QuotasAssignedSingleForm quotasAssignedForm = new QuotasAssignedSingleForm();

    public QuotasAssignedSingleForm getQuotasAssignedForm() {
        return quotasAssignedForm;
    }

    public void setQuotasAssignedForm(QuotasAssignedSingleForm quotasAssignedForm) {
        this.quotasAssignedForm = quotasAssignedForm;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            pageForward = IMP_QUOTAS_UNIT_FILE;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }

    public String importFile() throws Exception {
        log.info("Begin method importFile of ImportQuotasUnitUnderTheFileDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session imSession = this.getSession();
//        String importType = quotasAssignedForm.getImportType();
        String serverFileName = quotasAssignedForm.getServerFileName();

        /**
         * START log
         */
        String function = "com.viettel.im.database.DAO.ImportQuotasUnitUnderTheFileDAO.importFile.do";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());
        /**
         * End log
         */
        QuotasAssignedSingleForm tmpForm = new QuotasAssignedSingleForm();

        //Check dữ liệu nhập vào
        String returnMsg = checkValidate(quotasAssignedForm);
        if (!"".equals(returnMsg.trim())) {
            req.setAttribute(REQUEST_MESSAGE, returnMsg);
            return pageForward;
        }

        List<QuotasAssignedSingleForm> resultList = new ArrayList<QuotasAssignedSingleForm>();
        tmpForm = getDataListfromFile(quotasAssignedForm);
        if (tmpForm.getShopCodeList() == null || tmpForm.getShopCodeList().isEmpty()) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.TT.25");//Khong tim thay shop code trong file!
            return pageForward;
        }

        //Xu ly lay so dong can mapping tu DB
        String sqlNumber = new String("SELECT value FROM app_params WHERE TYPE = 'TYPE_MAX_ROW_QUOTAS_ASSIGNED' AND status = 1");
        Query queryNumber = imSession.createSQLQuery(sqlNumber.toString());
        List<String> lst = queryNumber.list();
        Long rowNumber = null;
        try {
            rowNumber = Long.valueOf(lst.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
            rowNumber = 300L;
        }
        //Đọc file và xử lý với từng dòng
        int x = 0;
        int sizeList = tmpForm.getShopCodeList().size();
        if (sizeList <= rowNumber) {
                for (int i = 0; i < sizeList; i++) {
                    QuotasAssignedSingleForm impQuotasAssigned = new QuotasAssignedSingleForm();
                    returnMsg = checkvalidateFile(tmpForm.getShopCodeList().get(i), tmpForm.getStockTypeIdList().get(i),
                            tmpForm.getRequestTypeList().get(i), tmpForm.getQuotasList().get(i), tmpForm.getDebitDayTypeList().get(i));
                    if ("".equals(returnMsg.trim())) {
                        if (updateQuotas(tmpForm.getShopCodeList().get(i).trim(), tmpForm.getStockTypeIdList().get(i).trim(),
                                tmpForm.getRequestTypeList().get(i).trim(), tmpForm.getQuotasList().get(i).trim(), tmpForm.getDebitDayTypeList().get(i).trim())) {
                            impQuotasAssigned.setShopCode(tmpForm.getShopCodeList().get(i).toString().trim());
                            impQuotasAssigned.setStockTypeId(tmpForm.getStockTypeIdList().get(i).toString().trim());
                            impQuotasAssigned.setRequestType(tmpForm.getRequestTypeList().get(i).toString().trim());
                            impQuotasAssigned.setQuotas(tmpForm.getQuotasList().get(i).toString().trim());
                            impQuotasAssigned.setDebitDayType(tmpForm.getDebitDayTypeList().get(i).toString().trim());
                            impQuotasAssigned.setStatus("Import Succress!");
                            x++;
                        } else {
                            impQuotasAssigned.setShopCode(tmpForm.getShopCodeList().get(i).toString().trim());
                            impQuotasAssigned.setStockTypeId(tmpForm.getStockTypeIdList().get(i).toString().trim());
                            impQuotasAssigned.setRequestType(tmpForm.getRequestTypeList().get(i).toString().trim());
                            impQuotasAssigned.setQuotas(tmpForm.getQuotasList().get(i).toString().trim());
                            impQuotasAssigned.setDebitDayType(tmpForm.getDebitDayTypeList().get(i).toString().trim());
                            impQuotasAssigned.setStatus("Error! Could not excuquery!");
                        }

                    } else {
                        impQuotasAssigned.setShopCode(tmpForm.getShopCodeList().get(i).toString().trim());
                        impQuotasAssigned.setStockTypeId(tmpForm.getStockTypeIdList().get(i).toString().trim());
                        impQuotasAssigned.setRequestType(tmpForm.getRequestTypeList().get(i).toString().trim());
                        impQuotasAssigned.setQuotas(tmpForm.getQuotasList().get(i).toString().trim());
                        impQuotasAssigned.setDebitDayType(tmpForm.getDebitDayTypeList().get(i).toString().trim());
                        impQuotasAssigned.setStatus(returnMsg);
                    }
                    resultList.add(impQuotasAssigned);
                    req.setAttribute(REQUEST_MESSAGE, "updated record of success:" + x + "/" + (i + 1));
                }
        } else {
                for (int i = 0; i < rowNumber; i++) {
                    QuotasAssignedSingleForm impQuotasAssigned = new QuotasAssignedSingleForm();
                    returnMsg = checkvalidateFile(tmpForm.getShopCodeList().get(i), tmpForm.getStockTypeIdList().get(i),
                            tmpForm.getRequestTypeList().get(i), tmpForm.getQuotasList().get(i), tmpForm.getDebitDayTypeList().get(i).trim());
                    if ("".equals(returnMsg.trim())) {
                        if (updateQuotas(tmpForm.getShopCodeList().get(i).trim(), tmpForm.getStockTypeIdList().get(i).trim(),
                                tmpForm.getRequestTypeList().get(i).trim(), tmpForm.getQuotasList().get(i).trim(), tmpForm.getDebitDayTypeList().get(i).trim())) {
                            impQuotasAssigned.setShopCode(tmpForm.getShopCodeList().get(i).toString().trim());
                            impQuotasAssigned.setStockTypeId(tmpForm.getStockTypeIdList().get(i).toString().trim());
                            impQuotasAssigned.setRequestType(tmpForm.getRequestTypeList().get(i).toString().trim());
                            impQuotasAssigned.setQuotas(tmpForm.getQuotasList().get(i).toString().trim());
                            impQuotasAssigned.setDebitDayType(tmpForm.getDebitDayTypeList().get(i).toString().trim());
                            impQuotasAssigned.setStatus("Import Succress!");
                            x++;
                        } else {
                            impQuotasAssigned.setShopCode(tmpForm.getShopCodeList().get(i).toString().trim());
                            impQuotasAssigned.setStockTypeId(tmpForm.getStockTypeIdList().get(i).toString().trim());
                            impQuotasAssigned.setRequestType(tmpForm.getRequestTypeList().get(i).toString().trim());
                            impQuotasAssigned.setQuotas(tmpForm.getQuotasList().get(i).toString().trim());
                            impQuotasAssigned.setDebitDayType(tmpForm.getDebitDayTypeList().get(i).toString().trim());
                            impQuotasAssigned.setStatus("Error! Could not excuquery!");
                        }

                    } else {
                        impQuotasAssigned.setShopCode(tmpForm.getShopCodeList().get(i).toString().trim());
                        impQuotasAssigned.setStockTypeId(tmpForm.getStockTypeIdList().get(i).toString().trim());
                        impQuotasAssigned.setRequestType(tmpForm.getRequestTypeList().get(i).toString().trim());
                        impQuotasAssigned.setQuotas(tmpForm.getQuotasList().get(i).toString().trim());
                        impQuotasAssigned.setDebitDayType(tmpForm.getDebitDayTypeList().get(i).toString().trim());
                        impQuotasAssigned.setStatus(returnMsg);
                    }
                    resultList.add(impQuotasAssigned);
                    req.setAttribute(REQUEST_MESSAGE, "updated record of success:" + x + "/" + (i + 1));
                }
        }
        try {
            SimpleDateFormat hoursFormat = new SimpleDateFormat("yyyyMMddhh24mmss");
            String dateTime = hoursFormat.format(new Date());

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "Imp_Quotas_Unit_File.xls";
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2") + "Imp_Quotas_Unit_File_" + userToken.getLoginName().toLowerCase() + "_" + dateTime + ".xls";
//            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String realPath = filePath;
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            beans.put("list", resultList);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            DownloadDAO downloadDAO = new DownloadDAO();
            req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(filePath, req.getSession()).toString());
            req.setAttribute(REQUEST_EXCEL_FILE_PATH_MESSAGE, "ERR.CHL.102");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "MSG.not.found.records");
        }
        pageForward = IMP_QUOTAS_UNIT_FILE;
        return pageForward;
    }

    public String checkValidate(QuotasAssignedSingleForm tmp) {
        try {
            String returnMsg = "";
//            if (tmp.getImportType() == null) {
//                returnMsg = "ERR.TT.24"; //Lỗi, bạn chưa chọn loại import!
//                return returnMsg;
//            }
            if (tmp.getServerFileName() == null || tmp.getServerFileName().trim().equals("")) {
                returnMsg = "ERR.GOD.067"; //Lỗi, bạn chưa nhap file dau vao!
                return returnMsg;
            }
            return returnMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private QuotasAssignedSingleForm getDataListfromFile(QuotasAssignedSingleForm tmp) {
        quotasAssignedForm = tmp;
        try {
            quotasAssignedForm.setShopCode(null);
            quotasAssignedForm.setStockTypeId(null);
            quotasAssignedForm.setRequestType(null);
            quotasAssignedForm.setQuotas(null);
            quotasAssignedForm.setDebitDayType(null);
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            // Fix ATTT.
            String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + getSafeFileName(tmp.getServerFileName()));
            File clientFile = new File(serverFilePath);
            //File clientFile = f.getIsdnFile();
            List<String> shopCode = new ArrayList<String>();
            List<String> stockTypeId = new ArrayList<String>();
            List<String> requestType = new ArrayList<String>();
            List<String> quotas = new ArrayList<String>();
            List<String> debitDayType = new ArrayList<String>();
            List listObject = new CommonDAO().readExcelFile(clientFile, Constant.SHEET_NAME_DEFAULT, 1, 0, 4);//Lay tu dong thu 2
            for (int i = 0; i < listObject.size(); i++) {
                Object[] obj = (Object[]) listObject.get(i);
                shopCode.add(obj[0].toString().trim());
                stockTypeId.add(obj[1].toString().trim());
                requestType.add(obj[2].toString().trim());
                quotas.add(obj[3].toString().trim());
                debitDayType.add(obj[4].toString().trim());
            }
            quotasAssignedForm.setShopCodeList(shopCode);
            quotasAssignedForm.setStockTypeIdList(stockTypeId);
            quotasAssignedForm.setRequestTypeList(requestType);
            quotasAssignedForm.setQuotasList(quotas);
            quotasAssignedForm.setDebitDayTypeList(debitDayType);
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
        }
        return quotasAssignedForm;
    }

    private String checkvalidateFile(String shopCode, String stockTypeId, String requestType, String quotas, String debitDayType) {
        try {
            HttpServletRequest req = getRequest();
            Session imSession = getSession();
            String returnMsg = "";
            //Kiem tra ma nhan vien khong duoc rong
            if (shopCode.trim() == null || shopCode.trim().equals("")) {
                returnMsg = "Error! Shop code is null!";
                return returnMsg;
            }

            //Kiểm tra shop code co ton tai tren he thong khong?
            String stCheckShopCode = "select 1 from shop WHERE   lower(shop_code) = ? AND status = 1";
            Query qrCheckShopCode = imSession.createSQLQuery(stCheckShopCode.toString());
            qrCheckShopCode.setParameter(0, shopCode.toLowerCase());

            List lstCheckShopCode = qrCheckShopCode.list();
            if (lstCheckShopCode == null || lstCheckShopCode.isEmpty()) {
                returnMsg = "Error! Shop code does not exit!";
                return returnMsg;
            }

            //Kiểm tra shop_code có trong trạng thái chờ duyệt yêu cầu hay không?
            ShopDAO shopDAO = new ShopDAO();
            List<Shop> shop = shopDAO.findByShopCode(shopCode);
            Long shopId = shop.get(0).getShopId();
            String stQuery = "select 1 from debit_request_detail where owner_id = ? and owner_type = ? and stock_type_id = ? and status = ? and debit_day_type = ?";
            Query qrQuery = imSession.createSQLQuery(stQuery.toString());
            qrQuery.setParameter(0, shopId);
            qrQuery.setParameter(1, Constant.OWNER_TYPE_SHOP);
            qrQuery.setParameter(2, stockTypeId);
            qrQuery.setParameter(3, Constant.STATUS_ACTIVE);
            qrQuery.setParameter(4, debitDayType);

            List lstCheck = qrQuery.list();
            if (lstCheck != null && !lstCheck.isEmpty()) {
                returnMsg = "Error! Shop is in pending status request";
                return returnMsg;

            }

            //Kiểm tra đúng định dạng
            try {
                Long requestTypeL = Long.parseLong(requestType);
                // Kiem tra requestType = 1 (theo so lượng)
                if (requestTypeL != 2) {
                    returnMsg = "Error! Request Type is incorrect!";
                    return returnMsg;
                }
                
                Double quotasL;
                try {
                    quotasL = Double.parseDouble(quotas);
                } catch (NumberFormatException ex) {
                    returnMsg = "Error!!! Quotas is not number!";
                    return returnMsg;
                }
                if (quotasL <= 0) {
                    returnMsg = "Error! Quotas are negative !";
                    return returnMsg;
                }

//                //R8568 - check han muc goc cua nhan vien
//                String strCheckQuotas = " select original_debit from stock_original_debit where owner_id =? and owner_type = ? and stock_type_id = ? and status = ? ";
//                Query qrCheckQuotas = getSession().createSQLQuery(strCheckQuotas);
//                qrCheckQuotas.setParameter(0, shopId);
//                qrCheckQuotas.setParameter(1, Constant.OWNER_TYPE_SHOP);
//                qrCheckQuotas.setParameter(2, stockTypeId);
//                qrCheckQuotas.setParameter(3, Constant.STATUS_ACTIVE);
//                List<String> listOriDebit = qrCheckQuotas.list();
//                if (listOriDebit == null || listOriDebit.isEmpty()) {
//                    returnMsg = "Error!!! Shop does not have original quotas!";
//                    return returnMsg;
//                }
//                Long originalDebit = Long.parseLong(listOriDebit.get(0));
//                if (quotasL > originalDebit) {
//                    returnMsg = "Error!!! Max debit you input more than original quotas of shop! Original quotas of shop:" + originalDebit;
//                    return returnMsg;
//                }

                Long stockTypeIdL = Long.parseLong(stockTypeId);
                if (stockTypeIdL <= 0) {
                    returnMsg = "Error! Stock type id are negative !";
                    return returnMsg;
                }
                //Kiểm tra stockTypeId có tồn tại trên hệ thống không?
                String stCheckStockType = "SELECT 1 FROM  stock_type WHERE   status = ? AND check_exp = ? and stock_type_id = ?";
                Query qrCheckStockType = imSession.createSQLQuery(stCheckStockType.toString());
                qrCheckStockType.setParameter(0, Constant.STATUS_ACTIVE);
                qrCheckStockType.setParameter(1, Constant.STATUS_ACTIVE);
                qrCheckStockType.setParameter(2, stockTypeIdL);

                List lstCheckStock = qrCheckStockType.list();
                if (lstCheckStock == null || lstCheckStock.isEmpty()) {
                    returnMsg = "Error! Stock type id does not exit!!!";
                    return returnMsg;
                }
                Long debitDayTypeL = Long.parseLong(debitDayType);
                if (debitDayTypeL <= 0) {
                    returnMsg = "Error! Debit day type id are negative !";
                    return returnMsg;
                }
                //Kiểm tra debitDayType có tồn tại trên hệ thống không?
                String stCheckDebitDayType = "SELECT 1 FROM  app_params where type = 'DEBIT_DAY_TYPE' and code = ?";
                Query qrCheckDebitDayType = imSession.createSQLQuery(stCheckDebitDayType.toString());
                qrCheckDebitDayType.setParameter(0, debitDayType);
                List lstCheckDebit = qrCheckDebitDayType.list();
                if (lstCheckDebit == null || lstCheckDebit.isEmpty()) {
                    returnMsg = "Error! Debit day type does not exit!!!";
                    return returnMsg;
                }
                return returnMsg;
            } catch (Exception ex) {
                ex.printStackTrace();
                returnMsg = "Error!!! Request Type Id or Stock model Id or debitDayType is not number!!!";
                return returnMsg;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean updateQuotas(String shopCode, String stockTypeId, String requestType, String quotas, String debitDayType) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session imSession = this.getSession();

        try {
            ShopDAO shopDAO = new ShopDAO();
            List<Shop> shop = shopDAO.findByShopCode(shopCode);
            Long shopId = shop.get(0).getShopId();

            //Kiem tra xem shop da duoc giao chi tieu trong bang stock_debit chua
            String stCheckUpdate = "select 1 from stock_debit where owner_id = ? and owner_type = ? and stock_type_id = ? and debit_day_type = ?";
            Query qrCheckUpdate = imSession.createSQLQuery(stCheckUpdate.toString());
            qrCheckUpdate.setParameter(0, shopId);
            qrCheckUpdate.setParameter(1, Constant.OWNER_TYPE_SHOP);
            qrCheckUpdate.setParameter(2, stockTypeId);
            qrCheckUpdate.setParameter(3, debitDayType);

            List lstCheckUpdate = qrCheckUpdate.list();
            if (lstCheckUpdate == null || lstCheckUpdate.isEmpty()) {
                //insert du lieu vao bang target_staff_monthly
                StringBuffer strQuery = new StringBuffer("");
                strQuery.append(" INSERT INTO stock_debit (owner_id, owner_type, debit_type, debit_day_type, create_date, create_user, request_debit_type, stock_type_id, note) ");
                strQuery.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                Query query = imSession.createSQLQuery(strQuery.toString());
                int i = 0;
                query.setParameter(i++, shopId);
                query.setParameter(i++, Constant.OWNER_TYPE_SHOP);
                query.setParameter(i++, quotas);
                query.setParameter(i++, debitDayType);
                query.setParameter(i++, getSysdate());
                query.setParameter(i++, userToken.getStaffName());
                query.setParameter(i++, requestType);
                query.setParameter(i++, stockTypeId);
                query.setParameter(i++, "Import File");

                int resultQuery = query.executeUpdate();
                if (resultQuery <= 0) {
                    return false;
                }
            }

            //update du lieu vao bang stock_debit
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append(" update stock_debit set debit_type = ?, last_update_date = ?, last_update_user = ? ");
            strQuery.append(" where owner_id = ? and owner_type = ? and stock_type_id = ? and debit_day_type = ? ");
            Query query = imSession.createSQLQuery(strQuery.toString());
            int i = 0;
            query.setParameter(i++, quotas);
            query.setParameter(i++, getSysdate());
            query.setParameter(i++, userToken.getStaffName());
            query.setParameter(i++, shopId);
            query.setParameter(i++, Constant.OWNER_TYPE_SHOP);
            query.setParameter(i++, stockTypeId);
            query.setParameter(i++, debitDayType);
            int resultQuery = query.executeUpdate();
            if (resultQuery <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertQuotas(String shopCode, String stockTypeId, String requestType, String quotas, String debitDayType) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session imSession = this.getSession();

        try {
            ShopDAO shopDAO = new ShopDAO();
            List<Shop> shop = shopDAO.findByShopCode(shopCode);
            Long shopId = shop.get(0).getShopId();

            //Kiem tra xem shop da duoc giao chi tieu trong bang stock_debit chua
            String stCheckUpdate = "select 1 from stock_debit where owner_id = ? and owner_type = ? and stock_type_id = ? and debit_day_type = ?";
            Query qrCheckUpdate = imSession.createSQLQuery(stCheckUpdate.toString());
            qrCheckUpdate.setParameter(0, shopId);
            qrCheckUpdate.setParameter(1, Constant.OWNER_TYPE_SHOP);
            qrCheckUpdate.setParameter(2, stockTypeId);
            qrCheckUpdate.setParameter(3, debitDayType);

            List lstCheckUpdate = qrCheckUpdate.list();
            if (lstCheckUpdate != null && !lstCheckUpdate.isEmpty()) {
                return false;
            }
            // Lay debit day type trong bang app_params

            //insert du lieu vao bang target_staff_monthly
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append(" INSERT INTO stock_debit (owner_id, owner_type, debit_type, debit_day_type, create_date, create_user, request_debit_type, stock_type_id, note) ");
            strQuery.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            Query query = imSession.createSQLQuery(strQuery.toString());
            int i = 0;
            query.setParameter(i++, shopId);
            query.setParameter(i++, Constant.OWNER_TYPE_SHOP);
            query.setParameter(i++, quotas);
            query.setParameter(i++, debitDayType);
            query.setParameter(i++, getSysdate());
            query.setParameter(i++, userToken.getStaffName());
            query.setParameter(i++, requestType);
            query.setParameter(i++, stockTypeId);
            query.setParameter(i++, "Import File");

            int resultQuery = query.executeUpdate();
            if (resultQuery <= 0) {
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
