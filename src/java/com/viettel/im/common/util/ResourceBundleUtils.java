/*
 * ResourceBundleUtils.java
 *
 * Created on September 18, 2007, 11:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.viettel.im.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.net.URL;
import java.net.URLDecoder;
import com.viettel.im.client.bean.DbInfo;
import com.viettel.common.util.EncryptDecryptUtils;
import com.viettel.database.DAO.BaseDAOAction;
import java.util.Locale;

/**
 *
 * @author DatTV
 */
public class ResourceBundleUtils {

    static private ResourceBundle rb = null;

    /** Creates a new instance of ResourceBundleUtils */
    public ResourceBundleUtils() {
    }

    /**
     * Author:      Tuanpv
     * CreateDate:  15/10/2008
     * Purpose:     Lay thong tin theo key
     * @param key
     * @return
     */
    public static String getResource(String key) {
        return getResource(key, com.viettel.database.DAO.BaseDAOAction.locale);
    }

    /**
     * Author:      Tuanpv
     * CreateDate:  15/10/2008
     * Purpose:     Lay thong tin theo key
     * @param key
     * @param locale
     * @return
     */
    public static String getResource(String key, Locale locale) {
        try {
            if (locale == null) {
                rb = ResourceBundle.getBundle("config");
            } else {
                rb = ResourceBundle.getBundle("config", locale);
            }
            return rb.getString(key);
        } catch (Exception ex) {
            ex.printStackTrace();
            return key;
        }
    }

    public static String getResource(String configName, String key) {
        return getResource(configName, key, BaseDAOAction.locale);
    }

    public static String getResource(String configName, String key, Locale locale) {
        try {
            if (locale == null) {
                if (BaseDAOAction.locale != null) {
                    locale = BaseDAOAction.locale;
                } else {
                    locale = Locale.getDefault();
                }
            }
            rb = ResourceBundle.getBundle(configName, locale);
            return rb.getString(key);
        } catch (Exception ex) {
            ex.printStackTrace();
            return key;
        }
    }

    public static DbInfo getDbInfoEncrypt(String key) {
        rb = ResourceBundle.getBundle("config");
        String resKey = rb.getString(key);
        return decryptDBConfig(resKey);
    }

    /*
     * Author:      Tuanpv
     * CreateDate:  15/10/2008
     * Purpose:     Lấy ra roleID của Admin phụ trách bộ phận chăm sóc khách hàng
     */
    public static List getVaiTroNguoiDungViettelTelecom() {
        rb = ResourceBundle.getBundle("config");
        String value = rb.getString("role.user.vietteltelecom");
        String arrValue[] = value.split(",");
        List lstValue = new ArrayList();
        if (arrValue.length > 0) {
            for (int index = 0; index < arrValue.length; index++) {
                lstValue.add(Long.parseLong(arrValue[index]));
            }
        }
        return lstValue;
    }

    /*
     * Author:      Tuanpv
     * CreateDate:  15/10/2008
     * Purpose:     Lấy ra roleID của nhân viên chăm sóc khách hàng vietteltelecom
     */
    public static List getVaiTroNguoiDungChamSocKhachHangTinh() {
        rb = ResourceBundle.getBundle("config");
        String value = rb.getString("role.user.cskhtinh");
        String arrValue[] = value.split(",");
        List lstValue = new ArrayList();
        if (arrValue.length > 0) {
            for (int index = 0; index < arrValue.length; index++) {
                lstValue.add(Long.parseLong(arrValue[index]));
            }
        }
        return lstValue;
    }

    /*
     * Author:      Tuanpv
     * CreateDate:  15/10/2008
     * Purpose:     Lấy ra roleID của trưởng phòng chăm sóc khách hàng tỉnh
     */
    public static List getVaiTroGiaoDichVienCuaHang() {
        rb = ResourceBundle.getBundle("config");
        String value = rb.getString("role.user.giaodichvien");
        String arrValue[] = value.split(",");
        List lstValue = new ArrayList();
        if (arrValue.length > 0) {
            for (int index = 0; index < arrValue.length; index++) {
                lstValue.add(Long.parseLong(arrValue[index]));
            }
        }
        return lstValue;
    }

    private static DbInfo decryptDBConfig(String encryptFilePath) {
        try {
            URL file = Thread.currentThread().getContextClassLoader().getResource(encryptFilePath);
            String decryptString = EncryptDecryptUtils.decryptFile(URLDecoder.decode(file.getPath()));
            String[] appProperties = decryptString.split("\r\n");
            DbInfo dbInfo = new DbInfo();
            String dbConnStr = appProperties[0].split("=")[1].trim();
            String dbUser = appProperties[1].split("=")[1].trim();
            String dbPass = appProperties[2].split("=")[1].trim();
            dbInfo.setConnStr(dbConnStr);
            dbInfo.setUserName(dbUser);
            dbInfo.setPassWord(dbPass);
            return dbInfo;

        } catch (Exception e) {
            System.err.println("%%%% Error decode info %%%%");
            e.printStackTrace();
        }
        return null;
    }
}
