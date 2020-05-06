/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.viettel.im.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author LamNV
 */
public class WriteLogUtil {
    public static final Long LOG_MODE_DAILY = 1L; //Moi ngay log trong 1 file log rieng
    public static final Long LOG_MODE_HOURLY = 2L; //Moi gio log trong 1 file log rieng    

    private String prefix;
    private String filePath;
    private Long logMode;


    public WriteLogUtil(String filePath, String prefix, Long logMode) {
        this.filePath = filePath;
        this.logMode = logMode;
        this.prefix = prefix;
    }

    public void writeLog(String content) {
        FileWriter writer = null;
        String contentFull = "";

        Date sysDate = new Date();
        String logFileName = "";
        if (LOG_MODE_DAILY.compareTo(logMode) == 0) {
         logFileName = String.format("%s%s_%s%s",
                filePath, prefix, DateTimeUtils.date2yyyyMMddStringNoSlash(sysDate), ".log");
        } else if (LOG_MODE_HOURLY.compareTo(logMode) == 0) {
         logFileName = String.format("%s%s_%s%s",
                filePath, prefix, DateTimeUtils.date2yyyyMMddHHStringNoSlash(sysDate), ".log");
        } else {
            logFileName = String.format("%s%s", filePath, prefix + ".log");
        }
        
        contentFull = String.format("%s: %s", DateTimeUtils.date2YYYYMMddTime(sysDate), content);
    
        try {
            File logFile = new File(logFileName);
            logFile.createNewFile();
            FileWriter fstream = new FileWriter(logFile, true);
            fstream.write(contentFull + "\n");
            fstream.flush();
        } catch (IOException ex) {
            System.out.println("Loi tai ham writeLog: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getLogMode() {
        return logMode;
    }

    public void setLogMode(Long logMode) {
        this.logMode = logMode;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSortPath(String filePath) {
        String usrDir = System.getProperty("user.dir");
        System.out.println(usrDir);
        this.filePath = usrDir + filePath;
    }

}
