 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.guhesan.querycrypt.QueryCrypt;
import com.guhesan.querycrypt.beans.RequestParameterObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import com.viettel.im.client.bean.FileName;
import com.viettel.payment.common.util.ResourceBundleUtils;
import com.viettel.rd.crypto.AESPKCSBASE64URIEncode;
import com.viettel.payment.common.util.StringUtils;
import java.util.ResourceBundle;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kdvt_thinhdd4
 */
public class DownloadDAO extends BaseDAO {
    // tutm1 29/01/13 : bo sung thuoc tinh inputStream sua loi Can not find a java.io.InputStream with the name [inputStream]
    private InputStream inputStream;
    // tutm1 22/05/2014: sua loi dau xuong dong khong hien thi duoc popup download file
    private String REPLACE_END_LINE_NUMBER = "_endline_";
    private String REPLACE_BREAK_LINE_NUMBER = "_breakline_";

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    // end tutm1
    
    public String downloadFile() {
        try {
            RequestParameterObject rpo = QueryCrypt.decrypt(getRequest());
            String fileName = rpo.getParameter("filename");
            if (StringUtils.validString(fileName)) {
                String path = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
                String pathFileName = path + fileName;
                File file = new File(pathFileName);
                inputStream = new FileInputStream(file);
                HttpServletResponse response = getResponse();
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Content-Disposition", "attachment;filename =\"" + fileName + "\"");
                response.setHeader("Pragma", "public");
                response.setHeader("Expires", "0");
                response.setHeader("Content-Transfer-Encoding", "binary");
                ServletOutputStream sos = response.getOutputStream();
                rewrite(inputStream, sos);
                sos.flush();
            } else {
                return "error";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
        return "download";
    }
    
    /*
    public String downloadFile() {
        try {
            HttpServletRequest req = getRequest();
            AESPKCSBASE64URIEncode aes = (AESPKCSBASE64URIEncode) this.getRequest().getSession().getAttribute("aes");
            String fileName = getRequest().getParameter("filename");
            String down = getRequest().getParameter("down");
            String downEm = getRequest().getParameter("downEm");
            if (fileName.indexOf(REPLACE_END_LINE_NUMBER) >= 0) {
                fileName = fileName.replace(REPLACE_END_LINE_NUMBER, "\n");
            }

            if (fileName.indexOf(REPLACE_BREAK_LINE_NUMBER) >= 0) {
                fileName = fileName.replaceAll(REPLACE_BREAK_LINE_NUMBER, "\r");
            }

            //lay cau hinh tu file config
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            String reportPath = resourceBundle.getString("LINK_TO_DOWNLOAD_FILE_2");
            String patternPath = com.viettel.im.common.util.ResourceBundleUtils.getResource("LINK_PATTERN_FILE");
            String filePath = com.viettel.im.common.util.ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
//            String realPath = req.getSession().getServletContext().getRealPath(filePath);
//            reportPath = realPath;

            if (StringUtils.validString(fileName)) {
                fileName = aes.decrypt(fileName);
                System.out.println(fileName);
                File file;
                if (down != null && down.equals("1")) {
                    file = new File(getRequest().getRealPath(patternPath + fileName));
                }else if(down != null && down.equals("2")) {
                    file = new File(fileName);
                }else {
                    if (reportPath.contains("\\")) {
                        reportPath += "\\";
                    } else {
                        reportPath += "/";
                    }
                    System.out.println(reportPath);
                    file = new File(reportPath + fileName);
                }
                inputStream = new FileInputStream(file);
                HttpServletResponse response = getResponse();
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Content-Disposition", "attachment;filename =\"" + fileName + "\"");
                response.setHeader("Pragma", "public");
                response.setHeader("Expires", "0");
                response.setHeader("Content-Transfer-Encoding", "binary");
                ServletOutputStream sos = response.getOutputStream();
                rewrite(inputStream, sos);
                sos.flush();
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "download";
    }
    */
    protected void rewrite(InputStream input, OutputStream output) throws IOException {
        int numRead;
        if ((input == null) || (output == null)) {
            return;
        }
        byte[] buf = new byte[1024];
        while (!((numRead = input.read(buf)) < 0)) {
            output.write(buf, 0, numRead);
        }
    }

    /**
     * @author ThinhDD
     * @date: 18/12/2012
     * @param
     * @return lay ten file theo path nhap vao
     */
    public String getFileNameEnc(String path, HttpSession session) throws Exception {
        FileName myHomePage = new FileName(path, '/', '.');
        AESPKCSBASE64URIEncode aes = (AESPKCSBASE64URIEncode) session.getAttribute("aes");
        System.out.println("path: " + path);
        System.out.println("myHomePage: " + myHomePage);
        System.out.println("aes: " + aes);
        System.out.println("aes.encrypt(myHomePage.getFileName()): " + aes.encrypt(myHomePage.getFileName()));
        
        String fileName = "filename=" + aes.encrypt(myHomePage.getFileName());
        if (fileName.indexOf("\n") >= 0) {
            fileName = fileName.replaceAll("\n", REPLACE_END_LINE_NUMBER);
        }

        if (fileName.indexOf("\r") >= 0) {
            fileName = fileName.replaceAll("\r", REPLACE_BREAK_LINE_NUMBER);
        }

        return fileName;
    }
    
     public String getFileNameEncNew(String path, HttpSession session) throws Exception {
    	String fileName = null;
    	if (path != null && !path.equals(""))
    	{
    		FileName myHomePage = new FileName(path, '/', '.');
    		fileName = QueryCrypt.getInstance().encrypt(getRequest(),"filename="+myHomePage.getFileName());
    	};
    	return fileName;
    }

    public String getFilePathEnc(String path, HttpSession session) throws Exception {
        AESPKCSBASE64URIEncode aes = (AESPKCSBASE64URIEncode) session.getAttribute("aes");
        System.out.println("path: " + path);
        String fileName = "filename=" + aes.encrypt(path);
        return fileName;
    }

    /**
     * @author Thuannx1
     * @date: 24/08/2013
     * @param
     * @return lay ten file theo path truyen tu report server
     */
    public String getFileNameServer(String path, HttpSession session) throws Exception {
        FileName myHomePage = new FileName(path, '/', '.');
        AESPKCSBASE64URIEncode aes = (AESPKCSBASE64URIEncode) session.getAttribute("aes");
        System.out.println("path: " + path);
        String fileName = "filename=" + aes.encrypt(myHomePage.getFileName());
        System.out.println(fileName);
        return fileName;
    }
    
    public String downloadFileTemplate() {
        try {
            AESPKCSBASE64URIEncode aes = (AESPKCSBASE64URIEncode) this.getRequest().getSession().getAttribute("aes");
            String fileName = getRequest().getParameter("filename");
            //lay cau hinh tu file config
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            String patternPath = resourceBundle.getString("LINK_PATTERN_FILE");
            if (StringUtils.validString(fileName)) {
                fileName = aes.decrypt(fileName);
                File file;
                file = new File(getRequest().getRealPath(patternPath + fileName));
                // tutm1 29/01/13 : sua loi Can not find a java.io.InputStream with the name [inputStream]
                inputStream = new FileInputStream(file);
                // end tutm1
                HttpServletResponse response = getResponse();
                response.setHeader("Cache-Control", "no-cache");
                response.setHeader("Content-Disposition", "attachment;filename =\"" + fileName + "\"");
                response.setHeader("Pragma", "public");
                response.setHeader("Expires", "0");
                response.setHeader("Content-Transfer-Encoding", "binary");
                ServletOutputStream sos = response.getOutputStream();
                rewrite(inputStream, sos);
                sos.flush();
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "downloadTemplate";
    }

}
