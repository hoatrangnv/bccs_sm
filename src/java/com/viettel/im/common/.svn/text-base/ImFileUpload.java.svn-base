package com.viettel.im.common;

/**
 *
 * @author Doan Thanh 8, 12/09/2009 thuc hien cac action lien quan den upload
 * file
 *
 */
import java.io.File;
import javax.servlet.http.HttpServletRequest;
import com.viettel.im.client.form.UploadForm;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.DAO.BaseDAO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImFileUpload extends BaseDAO {

    HttpServletRequest request;
    private UploadForm uploadForm = new UploadForm();

    public UploadForm getUploadForm() {
        return uploadForm;
    }

    public void setUploadForm(UploadForm uploadForm) {
        this.uploadForm = uploadForm;
    }

    public String prepareUploadFile() {
        request = getRequest();
        String clientFileNameId = request.getParameter("clientFileNameId");
        String serverFileNameId = request.getParameter("serverFileNameId");

        String impBankReceipt = request.getParameter("impBankReceipt");

        if (clientFileNameId != null) {
            uploadForm.setClientFileNameId(clientFileNameId);
        }
        if (serverFileNameId != null) {
            uploadForm.setServerFileNameId(serverFileNameId);
        }

        //import GNT
        if (impBankReceipt != null && impBankReceipt.trim().equals("true")) {
            uploadForm.setImpBankReceipt(Boolean.TRUE);
        }

        return "uploadSuccess";
    }

    public String uploadFile() {
        request = getRequest();
        request.setAttribute("Okie", "false");
        File uploadedFile = uploadForm.getFileUpload();
//        String contentType = this.getUploadContentType();
        if (uploadedFile == null) {
            request.setAttribute("message", "!!!Lỗi.Chưa chọn file upload");
            return "uploadSuccess";
        }
        /*** Edit by hieptd
         * 
         */
        
//        String fileName = uploadedFile.getName();
        String fileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(uploadForm.getFileUploadFileName());
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        String strNow = simpleDateFormat.format(now);

        String serverFileName = "";
        String tempPath = "";
        String serverFilePath = "";
        if (uploadForm.isImpBankReceipt() == true) {//import GNT
            SimpleDateFormat simpleDateFormatFolder = new SimpleDateFormat("yyyyMM");//Ten thu muc (phan theo nam + thang)
            //serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(strNow);
            fileName = "File_Approved_Stock_Export.pdf";
            serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(strNow + "_" + fileName);
            tempPath = ResourceBundleUtils.getResource("LINK_FILE_EXPORT_STOCK_MASTER_AGENT");

            //Kiem tra thu muc thang hien tai
            String serverFolderPath = tempPath + simpleDateFormatFolder.format(now) + "/";
            File serverFolder = new File(serverFolderPath);
            if (serverFolder == null || !serverFolder.exists()) {//Neu chua ton tai
                //Kiem tra thu muc goc
                serverFolderPath = tempPath;
                serverFolder = new File(serverFolderPath);
                if (serverFolder == null || !serverFolder.exists()) {//Neu khong ton tai
                    request.setAttribute("message", "!!!Lỗi. Đường dẫn thư mục gốc không tồn tại trên server.");
                    request.setAttribute("Okie", "false");
                    return "uploadSuccess";
                }
                //Tao thu muc thang hien tai
                tempPath = tempPath + simpleDateFormatFolder.format(now) + "/";
                serverFolderPath = tempPath;
                serverFolder = new File(serverFolderPath);
                if (!serverFolder.mkdir()) {
                    request.setAttribute("message", "!!!Lỗi. Không tạo được thư mục trên server.");
                    request.setAttribute("Okie", "false");
                    return "uploadSuccess";
                }
            } else {
                tempPath = tempPath + simpleDateFormatFolder.format(now) + "/";
            }
            serverFilePath = tempPath + serverFileName;
        } else {
            serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(strNow + "_" + fileName);
            //String serverFileName = fileName;
            tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            serverFilePath = this.request.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        }
        
        File serverFile = new File(serverFilePath);

        if (serverFile.exists()) {
            //truong hop da ton tai file tren server        
            request.setAttribute("message", "!!!Lỗi. File đã tồn tại trên server.");
            request.setAttribute("Okie", "false");
            return "uploadSuccess";
        } else {
            int iResultCopyFile = copyFile(uploadedFile, serverFile);
            if (iResultCopyFile == 0) {
            } else {
                request.setAttribute("message", "!!!Lỗi.Error code=  " + iResultCopyFile);
                request.setAttribute("Okie", "false");
                return "uploadSuccess";
            }
        }


        //tra ve ten file tren server, ten file client
//        this.request.setAttribute("clientFileName", fileName);
        this.request.setAttribute("clientFileName", serverFilePath);
        //edit by hieptd
        this.request.setAttribute("serverFileName", com.viettel.security.util.StringEscapeUtils.getSafeFileName(serverFile.getName()));
        
        request.setAttribute("message", "Upload file thành công");
        request.setAttribute("Okie", "true");

        return "uploadSuccess";
    }

    /**
     *
     * author tamdt1, 12/09/2009
     * copy du lieu tu file nguon sang file dich
     * dau vao:
     *          - file nguon
     *          - file dich
     * dau ra:
     *          - 0 neu copy thanh cong
     *          - -1 neu copy bi loi
     *
     */
    private int copyFile(File sourceFile, File destinationFile) {
        //
        if (sourceFile == null || destinationFile == null) {
            return -1;
        }
        //
        try {
            InputStream in = new FileInputStream(sourceFile);

//            //for Append the file.
//            OutputStream out = new FileOutputStream(destinationFile, true);

            //for Overwrite the file.
            OutputStream out = new FileOutputStream(destinationFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

            System.out.println("tamdt1 - file copied");

            return 0;

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return -1;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return -1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    } 
}
