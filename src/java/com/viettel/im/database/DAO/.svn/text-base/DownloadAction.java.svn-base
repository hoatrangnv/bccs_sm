/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.database.DAO;



import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.common.util.ResourceBundleUtils;
import java.io.File;
import java.io.FileInputStream;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author thinhph2_s
 */
public class DownloadAction extends BaseDAOAction{
    private FileInputStream fileInputStream;
    private String filename;
    private String contentType;
    private HttpServletRequest request = null;
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = getSafeFilename(filename);
    }

    public FileInputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(FileInputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String download() {
        request = getRequest();
        try {
//            String realFilePath = QueryCryptUtils.getParameter(request, "filePath");
            String realFilePath = request.getParameter("filePath");
            setFilename(realFilePath);
            realFilePath = getSafeFilename(realFilePath);
            setContentType("application/vnd.ms-excel");
            String uploadFolder = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            fileInputStream = new FileInputStream(new File(request.getSession().getServletContext().getRealPath(uploadFolder + realFilePath)));
        } catch (Exception ex) {
            return ERROR;
        }
        return "download";
    }

    public String downloadTemplate() {
        request = getRequest();
        try {
            String realFilePath = request.getParameter("filePath");
            setFilename(realFilePath);
            realFilePath = getSafeFilename(realFilePath);
            setContentType("application/vnd.ms-excel");
            String uploadFolder = "./share/";//ResourceBundleUtils.getResource("TEMPLATE_AREA_PATH");

            fileInputStream = new FileInputStream(new File(request.getSession().getServletContext().getRealPath(uploadFolder + realFilePath)));
        } catch (Exception ex) {
            return ERROR;
        }
        return "download";
    }

    public String downloadManual() {
        request = getRequest();
        try {
            String realFilePath = request.getParameter("filePath");
            setFilename(realFilePath);
            realFilePath = getSafeFilename(realFilePath);
            String[] arrFileName = realFilePath.split("\\.");
            String ext = arrFileName[arrFileName.length - 1];
            if (ext.equals("docx")) {
                setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else if (ext.equals("xls")) {
                setContentType("application/vnd.ms-excel");
            } else if (ext.equals("xlsx")) {
                setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            } else {
                setContentType("application/msword");
            }
            String uploadFolder = ResourceBundleUtils.getResource("MANUAL_PATH");
            fileInputStream = new FileInputStream(new File(request.getSession().getServletContext().getRealPath(uploadFolder + realFilePath)));
        } catch (Exception ex) {
            return ERROR;
        }
        return "download";
    }

    private String getSafeFilename(String filename) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filename.length(); i++) {
            char c = filename.charAt(i);
            if (c!= '/' && c != '\\' && c != 0) {
                sb.append(c);
            }
        }
        return sb.toString();

    }
}
