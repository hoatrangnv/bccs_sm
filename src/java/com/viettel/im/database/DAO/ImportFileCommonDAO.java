/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ImportCommonForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.FileUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.BaseBO;
import java.io.File;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.hibernate.Session;
import com.viettel.im.common.util.UploadFileValidateUtils;
import java.text.SimpleDateFormat;

/**
 * @author thinhph2_s des: base import file
 */
public abstract class ImportFileCommonDAO<BO extends BaseBO> extends BaseDAOAction {

    private int readSheet;
    protected int ROW_NUMBER_MAX_IN_FILE;
    protected int BEGIN_DATA_ROW;//Dong du lieu dau tien
    protected int BEGIN_DATA_COL;//Cot du lieu dau tien
    protected boolean needReportFailResult = false;//Co can xuat bao cao ket qua loi hay khong
    public static final int MAX_MONTH = 12;
    public static final int MAX_SHEET = 10;
    private Date nowDate = null;

    public Date getNowDate() {
        return nowDate;
    }

    public int getROW_NUMBER_MAX_IN_FILE() {
        return ROW_NUMBER_MAX_IN_FILE;
    }

    public void setROW_NUMBER_MAX_IN_FILE(int ROW_NUMBER_MAX_IN_FILE) {
        this.ROW_NUMBER_MAX_IN_FILE = ROW_NUMBER_MAX_IN_FILE;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public void setBEGIN_DATA_ROW(int BEGIN_DATA_ROW) {
        this.BEGIN_DATA_ROW = BEGIN_DATA_ROW;
    }

    public void setBEGIN_DATA_COL(int BEGIN_DATA_COL) {
        this.BEGIN_DATA_COL = BEGIN_DATA_COL;
    }

    public boolean isNeedReportFailResult() {
        return needReportFailResult;
    }

    public void setNeedReportFailResult(boolean needReportFailResult) {
        this.needReportFailResult = needReportFailResult;
    }

    public void importFile(HttpServletRequest request, ImportCommonForm inputForm, Date nowDate) {
        Session session = getSession();
        try {
            //session.beginTransaction();
            setNowDate(nowDate);
            inputForm.setMessageType("fail");
            Workbook workbook;
            WritableWorkbook failedWorkBook = null;
            WritableSheet writableSheet = null;
            File inputFile = inputForm.getReportFile();
            String fileName = inputForm.getReportFileFileName();
            //Validate du lieu
            String validateResult = validateImportFile(inputForm);
            if (!validateResult.equals("")) {
                inputForm.setReturnMessage(validateResult);
                return;
            }
            initReportInfo();
            File uploadDirectory = new File(Constant.UPLOAD_DIR);//FileUtils.getAppsRealPath() +
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            if (inputForm.getSheet() != null) {
                readSheet = inputForm.getSheet().intValue();
            } else {
                readSheet = 0;
            }
            FileUtils.saveFile(inputFile, fileName, uploadDirectory);
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setEncoding("Cp1252");
            workbook = Workbook.getWorkbook(inputFile, wbSettings);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileOut = format.format(nowDate) + "_RESULT_FILE.xls";

            String uploadFolder = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            File outputFile = new File(request.getSession().getServletContext().getRealPath(uploadFolder + fileOut));//uploadDirectory.getAbsolutePath() +
            if (isNeedReportFailResult()) {
                failedWorkBook = Workbook.createWorkbook(outputFile, workbook, wbSettings);
                writableSheet = failedWorkBook.getSheet(readSheet);
                writableSheet.addCell(new Label(writableSheet.getColumns(), BEGIN_DATA_ROW - 1, "Nguyên nhân lỗi",
                        writableSheet.getCell(BEGIN_DATA_COL, BEGIN_DATA_ROW - 1).getCellFormat()));
            }
            //doc file du lieu
            Sheet sheet;
            try {
                sheet = workbook.getSheet(readSheet);
            } catch (Exception ex) {
                //log.error("Get sheet error: " + ex.toString());
                inputForm.setReturnMessage("Sheet không tồn tại");
                return;
            }
            if (sheet != null && sheet.getRows() >= BEGIN_DATA_ROW - 1) {
                //Doc file
                if (sheet.getRows() > ROW_NUMBER_MAX_IN_FILE + 1) {
                    inputForm.setReturnMessage("Hệ thống chỉ cho phép import " + String.valueOf(ROW_NUMBER_MAX_IN_FILE) + " dòng/1 lần.");
                    return;
                }
                int successCount = 0;
                int failCount = 0;
                getSequenceParent();
                for (int i = BEGIN_DATA_ROW; i < sheet.getRows(); i++) {

                    if (!isBlankRow(sheet, i)) {
                        try {
                            BO bo = getRowData(sheet, i);
                            if (!isDataValid(session, bo, request)) {
                                throw new Exception("Dữ liệu không đúng định dạng");
                            }
                            if (isDataExists(bo)) {
                                throw new Exception("Dữ liệu không đúng định dạng");
                            }
                            addMoreInfo(inputForm, bo);
                            saveData(bo, request);
                            successCount++;
                        } catch (Exception ex) {
                            System.out.println("Exception---------------------------->" + ex.getMessage());
                            if (isNeedReportFailResult()) {
                                System.out.println("isNeedReportFailResult-true");
                                failCount++;
                                writeErrorToResultSheet(writableSheet, ex.getMessage(), i);
                            } else {
                                System.out.println("isNeedReportFailResult-false");
                                //log.error("Error happenned at row " + i + ":" + ex.toString());
                                throw ex;
                            }
                        }
                    }
                }
                if (successCount == 0) {
                    inputForm.setReturnMessage("msg.khong_import_duoc_du_lieu_nao");
                } else {
                    saveParent();
                    if (failCount > 0) {
                        int total = sheet.getRows() - 1;
                        inputForm.setReturnMessage("Import dữ liệu không thành công " + failCount + "/" + total + " bản ghi");
                    } else {
                        inputForm.setReturnMessage("msg.import_thanh_cong");
                    }
                }
                if (failCount > 0 && isNeedReportFailResult()) {
                    failedWorkBook.write();
                    failedWorkBook.close();
                    inputForm.setResultSheetLink(QueryCryptUtils.setDownloadPath(fileOut));
                } else {
                    inputForm.setMessageType("success");
                }
                session.getTransaction().commit();
            } else {
                inputForm.setReturnMessage("Định dạng dữ liệu trong sheet không đúng");
            }
        } catch (Exception ex) {
            //log.error("Error when import file: " + ex.toString());
            if (ex.getMessage() != null && ex.getMessage().equals("Dữ liệu đã tồn tại")) {
                inputForm.setReturnMessage("Dữ liệu đã tồn tại");
            } else {
                inputForm.setReturnMessage("Định dạng dữ liệu trong sheet không đúng");
            }
            session.getTransaction().rollback();
        }
    }

    protected abstract BO getRowData(Sheet sheet, int row) throws Exception;

    protected abstract void saveData(BO bo, HttpServletRequest request);

    protected void addMoreInfo(ImportCommonForm inputForm, BO bo) {
    }

    protected String nomalizeNumber(String input) {
        return input.replace(",", "").replace(" ", "");
    }

    protected String validateImportFile(ImportCommonForm inputForm) {
        File importFile = inputForm.getReportFile();
        String fileName = inputForm.getReportFileFileName();
        if (importFile == null || fileName.isEmpty()) {
            return "Bạn chưa nhập file dữ liệu";
        }
        String error = UploadFileValidateUtils.validateFileName1(fileName);
        if (error != null) {
            return error;
        }
        String[] fileNameArr = fileName.split("\\.");
        if (fileNameArr == null || fileNameArr.length != 2) {
            return "Tên file dữ liệu không đúng hoặc chứa dấu chấm(.) !";
        }
        //kiem tra ten file mo rong co fai la excel ko
        String extensionFileName = fileNameArr[1];
        if (!"xls".equalsIgnoreCase(extensionFileName)) {
            return "File dữ liệu gửi lên không đúng định dạng. Hệ thống chỉ hỗ trợ file dữ liệu excel (*.xls)";
        }
        return otherValidate(inputForm);
    }

    protected String otherValidate(ImportCommonForm inputForm) {
        return "";
    }

    private boolean isBlankRow(Sheet sheet, int row) {
        int numColumn = sheet.getColumns();
        for (int i = 0; i < numColumn; i++) {
            if (!sheet.getCell(i, row).getContents().equals("")) {
                return false;
            }
        }
        return true;
    }

    protected void writeErrorToResultSheet(WritableSheet writableSheet, String error, int row) {
        try {
            writableSheet.addCell(new Label(writableSheet.getColumns() - 1, row, error));
        } catch (Exception ex) {
            ex.printStackTrace();
            //log.error("Error when write error in result sheet. Row " + row + ". Detail:" + ex.toString());
        }
    }

    protected void initReportInfo() {
        setBEGIN_DATA_COL(0);
        setBEGIN_DATA_ROW(1);
    }

    protected abstract boolean isDataExists(BO bo);

    protected boolean isDataValid(Session session, BO bo, HttpServletRequest request) throws Exception {
        return true;
    }

    protected void saveParent() {
    }

    protected void getSequenceParent() {
    }
}
