package com.viettel.im.common;

/**
 *
 * @author Doan Thanh 8, 12/09/2009
 * thuc hien cac action lien quan den upload file
 *
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
//import com.davidjc.ajaxfileupload.action.FileUpload;
import com.opensymphony.xwork2.Action;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImFileUploadAction{
//public class ImFileUploadAction extends FileUpload  implements ServletRequestAware{
//    HttpServletRequest request;
//
//    @Override
//    public String execute() {
//        File uploadedFile = this.getUpload();
////        String contentType = this.getUploadContentType();
//        String fileName = this.getUploadFileName();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date now = new Date();
//        String strNow = simpleDateFormat.format(now);
////        String serverFileName = this.request.getParameter("serverFileName");
////        //neu khong truyen tham so cho server file name thi lay ten file trung voi ten file upload
////        if(serverFileName != null && !serverFileName.trim().equals("")) {
////            serverFileName = serverFileName.trim();
////        } else {
////            serverFileName = fileName;
////        }
//        String serverFileName = strNow + "_" + this.request.getSession().getId() + "_" + fileName;
//        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
//        String serverFilePath = this.request.getSession().getServletContext().getRealPath(tempPath + serverFileName);
//        File serverFile = new File(serverFilePath);
//        String strMessage = "--------tamdt1--------";
//        boolean hasImUploadFileError = false;
//        if(serverFile.exists()) {
//            //truong hop da ton tai file tren server
//            strMessage = "!!!Error. Duplicate file on server";
//            hasImUploadFileError = true;
//        } else {
//            int iResultCopyFile = copyFile(uploadedFile, serverFile);
//            if(iResultCopyFile == 0) {
//                strMessage = "File " + fileName + " has copied";
//            } else {
//                strMessage = "!!!Error. Copy file to server. Error code = " + iResultCopyFile;
//                hasImUploadFileError = true;
//            }
//        }
//
//        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        strNow = simpleDateFormat.format(now);
//
//        //
//        List<String> l = (List<String>) this.request.getSession(true).getAttribute("uploaded_list");
//        if(l== null) {
//            l = new ArrayList<String>();
//            this.request.getSession(true).setAttribute("uploaded_list", l);
//        }
//        //luon chi giu 8 element tren list, > 8 so luong loai bo phan tu lau nhat
//        if(l.size() >= 8) {
//            l.remove(7);
//        }
//        l.add(0, strNow + "   " + strMessage);
//
//        //tra ve ten file tren server, ten file client
//        this.request.getSession(true).setAttribute("clientFileName", fileName);
//        this.request.getSession(true).setAttribute("serverFileName", serverFileName);
//        this.request.getSession(true).setAttribute("hasImUploadFileError", hasImUploadFileError);
//
//        return Action.SUCCESS;
//    }
//
//    @Override
//    public void setServletRequest(HttpServletRequest req) {
//        request = req;
//    }
//
//    /**
//     *
//     * author tamdt1, 12/09/2009
//     * copy du lieu tu file nguon sang file dich
//     * dau vao:
//     *          - file nguon
//     *          - file dich
//     * dau ra:
//     *          - 0 neu copy thanh cong
//     *          - -1 neu copy bi loi
//     *
//     */
//    private int copyFile(File sourceFile, File destinationFile) {
//        //
//        if(sourceFile == null || destinationFile == null) {
//            return -1;
//        }
//        //
//        try {
//            InputStream in = new FileInputStream(sourceFile);
//
////            //for Append the file.
////            OutputStream out = new FileOutputStream(destinationFile, true);
//
//            //for Overwrite the file.
//            OutputStream out = new FileOutputStream(destinationFile);
//
//            byte[] buf = new byte[1024];
//            int len;
//            while ((len = in.read(buf)) > 0) {
//                out.write(buf, 0, len);
//            }
//
//            in.close();
//            out.close();
//
//            System.out.println("tamdt1 - file copied");
//
//            return 0;
//
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//            return -1;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            return -1;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return -1;
//        }
//  }
//

}