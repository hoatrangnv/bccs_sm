/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

import com.viettel.anypay.util.DateUtil;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author itbl_linh
 */
public class FTPUtils {

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham upload file
     *
     */
    public static String uploadListFileFromFTPServer(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory("ACCOUNT_TRANS");
//                client.changeWorkingDirectory(destinationDir);
                //new forder By DateTime


                // create temp file on temp directory
                for (int i = 0; i < fileName.size(); i++) {
                    String lStr = fileName.get(i).substring(fileName.get(i).lastIndexOf(File.separator));
                    lStr = lStr.substring(1);
                    InputStream inputStream = new FileInputStream(new File(fileName.get(i)));
                    client.storeFile(lStr, inputStream);
                }
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return fileNameDir;
    }

    /**
     * author tannh date create : 12/11/2017
     *
     * @return Ham lay file tu tren server ve local may
     *
     */
    public static String retrieveListFileFromFTPServer(String host, String username, String password, List<String> fileName, String destinationDir, String tempDir) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        String fileNameDir = "";
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(username, password);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory("ACCOUNT_TRANS");
//                client.changeWorkingDirectory(destinationDir);
                // create temp file on temp directory
                for (int i = 0; i < fileName.size(); i++) {
                    // retrieve file from server and logout
                    String[] filesrc = fileName.get(i).split("\\/", 5);
                    ///u01/scan_doc/CONTRACT_CHANNEL/20171122104349_tony luis.rar.zip
                    File tempFile = new File(tempDir + filesrc[3] + "/" + filesrc[4]);
                    boolean success = (new File(tempFile.getParent())).mkdirs();
                    if (!success) {
                        File file = new File(tempDir + filesrc[3] + "/" + filesrc[4]);
                        file.createNewFile();
                    }
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));

                    if (client.retrieveFile(filesrc[4], outputStream)) {
                        outputStream.close();
                    }
                }
                // close connection
                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return fileNameDir;
    }

    public static boolean uploadListFileFromFTPServer(String host, String userFTP, String passFTP,
            String workingDir, String fileName, String staffCode, String filePath) throws Exception {
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
        try {

            // connect, login, set timeout
            client.connect(host);
            client.login(userFTP, passFTP);

            // set transfer type
            client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            // set timeout
            client.setSoTimeout(60000); // default 1 minute

            // check connection
            int reply = client.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                // switch to working folder
                client.changeWorkingDirectory(workingDir);
                //new forder By DateTime
                String dateDir = "";
                dateDir = DateUtil.date2yyMMddStringNoSlash(new Date());
                if (!dateDir.isEmpty()) {
                    client.makeDirectory(dateDir);
                    client.changeWorkingDirectory(dateDir);
                }
                // create temp file on temp directory
//                String lStr;
//                if (fileName.contains("\\")) {
//                    lStr = fileName.substring(fileName.lastIndexOf("\\"));
//                } else {
//                    lStr = fileName.substring(fileName.lastIndexOf("/"));
//                }
//
//                lStr = lStr.substring(1);
                long fileSize = new File(filePath).length();
                if (fileSize <= 0 || fileSize > (5 * 1024 * 1024)) {
                    return false;
                }
                InputStream inputStream = new FileInputStream(new File(filePath));
                client.storeFile(fileName, inputStream);
//                String fixedPath = lStr.substring(0, lStr.lastIndexOf("/") + 1 + 14);// 14 mean: yyyymmddhhmmss
//                String newName = lStr.replace(lStr.substring(fixedPath.length() + 1, lStr.lastIndexOf(".")), staffCode);
//                System.out.println("After rename: " + newName);
//                client.rename(lStr, fileName);

                client.logout();
            } else {
                client.disconnect();
                System.err.println("FTP server refused connection !");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        }
        return true;
    }
}
