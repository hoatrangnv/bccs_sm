package com.viettel.im.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * cac ham lien quan den thoi gian
 * modified date: 13/11/2009
 *
 */
public class DateTimeUtils {

    /**
     * @Author: TrongLV
     * @Purpose: Get date with day is first day of month
     * @param date
     * @return
     */
    public static Date getDateWithDayFirstMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        Date lastDate = calendar.getTime();
        return lastDate;


    }

    /**
     * @Author: TrongLV
     * @Purpose: Get date with day is last day of month
     * @param date
     * @return
     */
    public static Date getDateWithDayLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDay);
        Date lastDate = calendar.getTime();
        return lastDate;


    }

    /**
     * @Author: TrongLV
     * @Purpose: Get sysdate 
     * @return
     */
    public static String getSysdateForWeb() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @Author: TrongLV
     * @Purpose: Get sysdate before x day
     * @param numberAddedDate
     * @return
     */
    public static String getSysdateForWeb(int numberAddedDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, numberAddedDate);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @Author: TrongLV
     * @Purpose: convert string datetime of WebUI to date
     * @param date
     * @return
     * @throws Exception
     */
    public static Date convertStringToDateForWeb(String date) throws Exception {
        String pattern = "yyyy-MM-dd";
        return convertStringToTime(date, pattern);
    }

    /**
     *
     * purpose  : chuyen kieu string -> kieu date
     * in       :
     *              - chuoi can chuyen
     *              - dinh dang format cua chuoi
     * out      :
     *              - kieu date tuong ung. trong truong hop loi tra ve null
     *
     *
     */
    public static Date convertStringToTime(String strTime, String strPattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(strPattern);
            return dateFormat.parse(strTime);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date convertStringToDate(String date) throws Exception {
        //String pattern = "dd/MM/yyyy";
        String pattern = "yyyy-MM-dd";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDateVunt(String date) throws Exception {
        String pattern = "MM/dd/yyyy HH:mm";
        //"dd/MM/yyyy HH:mm:ss"
        //String pattern = "yyyy-MM-dd";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDate(String date, String pattern) throws Exception {
        return convertStringToTime(date, pattern);
    }

    /**
     * @purpose: Lay ve dinh dang ngay dang yyyymmdd
     **/
    public static String getPosProcessCodeDate(Date date) {

        Date curr = date;

        int yyyy = curr.getYear() + 1900;

        String temp = new Long(yyyy).toString();

        int month = curr.getMonth() + 1;

        String monthTemp = StringUtils.addZeroToString(new Long(month).toString(), 2);

        temp += monthTemp;

        int dd = curr.getDate();

        String ddTemp = StringUtils.addZeroToString(new Long(dd).toString(), 2);

        temp += ddTemp;

        return temp;

    }

    public static Date convertPosDate(String posDate) throws Exception {
        if (posDate == null || posDate.length() < 14) {
            return null;
        }
        String year = posDate.substring(0, 4);
        String month = posDate.substring(4, 6);
        String day = posDate.substring(6, 8);
        String hh = posDate.substring(8, 10);
        String mm = posDate.substring(10, 12);
        String ss = posDate.substring(12, 14);

        String date = day + "/" + month + "/" + year + " " + hh + ":" + mm + ":" + ss;
        Date tempDate = convertStringToDate(date, "dd/MM/yyyy HH:mm:ss");
        return tempDate;
    }

    public static String convertDateToString(Date date) throws Exception {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String date2ddMMyyyy(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String convertDateToString_tuannv(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    //so sanh ngay neu bang nhau tra ve true, khac tra ve false -- khong tinh thoi gian
    public static Boolean compareDate(Date date1, Date date2) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date1 == null || date2 == null) {
            return false;
        }
        try {
            if (dateFormat.format(date1).equals(dateFormat.format(date2))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     *  @author: dungnt
     *  @todo: get sysdate
     *  @return: String sysdate
     */
    public static String getSysdate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        return convertDateToString(calendar.getTime());
    }

    /*
     *  @author: dungnt
     *  @todo: get sysdate detail
     *  @return: String sysdate
     */
    public static String getSysDateTime() throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
    }
    /*
     *  @author: ThanhNC
     *  @todo: get sysdate detail formated in pattern
     *  @return: String sysdate 
     */

    public static String getSysDateTime(String pattern) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     *  @author: dungnt
     *  @todo: convert from String to DateTime detail
     *  @param: String date
     *  @return: Date
     */
    public static Date convertStringToDateTime(String date) throws Exception {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDateTimeVunt(String date) throws Exception {
        String pattern = "dd/MM/yyyy";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDateTimePOS(String date) throws Exception {
        String pattern = "yyyy/MM/dd HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static String convertDateTimeToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    public static String convertDateTimeToString(Date date, String pattern) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @author: ThangPV
     * @todo: convert from java.util.Date to java.sql.Date
     */
    public static java.sql.Date convertToSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * @anhlt - Get the first day on month selected.
     * @param dateInput
     * @return
     */
    public static String parseDate(int monthInput) {
        String dateReturn = "01/01/";
        Calendar cal = Calendar.getInstance();
        switch (monthInput) {
            case 1:
                dateReturn = "01/01/";
                break;
            case 2:
                dateReturn = "01/02/";
                break;
            case 3:
                dateReturn = "01/03/";
                break;
            case 4:
                dateReturn = "01/04/";
                break;
            case 5:
                dateReturn = "01/05/";
                break;
            case 6:
                dateReturn = "01/06/";
                break;
            case 7:
                dateReturn = "01/07/";
                break;
            case 8:
                dateReturn = "01/08/";
                break;
            case 9:
                dateReturn = "01/09/";
                break;
            case 10:
                dateReturn = "01/10/";
                break;
            case 11:
                dateReturn = "01/11/";
                break;
            case 12:
                dateReturn = "01/12/";
                break;
        }
        return dateReturn + cal.get(Calendar.YEAR);
    }

    /*
     *  @author: TUNGTV
     *  @todo: get End Of Day
     *  @return: Date
     */
    public static Date getEndOfDay(Date date) throws Exception {
        String strToDate = DateTimeUtils.convertDateToString(date);
        String strTDate = strToDate.substring(0, 10) + " 23:59:59";
        Date tDate = DateTimeUtils.convertStringToTime(strTDate, "yyyy-MM-dd HH:mm:ss");
        return tDate;
    }

    /*
     *  @author: TUNGTV
     *  @todo: get Start Of Day
     *  @return: Date
     */
    public static Date getStartOfDay(Date date) throws Exception {
        String strToDate = DateTimeUtils.convertDateToString(date);
        String strTDate = strToDate.substring(0, 10) + " 00:00:00";
        Date tDate = DateTimeUtils.convertStringToTime(strTDate, "yyyy-MM-dd HH:mm:ss");
        return tDate;
    }

    /**
     *
     * author tamdt1, 18/09/2009
     * ham cong them ngay
     * dau vao:
     *          - ngay can cong
     *          - so luong ngay can cong
     * dau ra:
     *          - ngay moi sau khi duoc cong them so ngay
     *
     *
     */
    public static Date addDate(Date date, int numberAddedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numberAddedDate);
        Date result = calendar.getTime();
        return result;
    }
    public static Date addYear(Date date, int numberAddedYear) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, numberAddedYear);
        Date result = calendar.getTime();
        return result;
    }

    public static int distanceBeetwen2Date(Date date1, Date date2) {
        Long time1 = date1.getTime();
        Long time2 = date2.getTime();
        Long distance = time2 - time1;
        Long dayDistance = distance / (24 * 60 * 60 * 1000);
        return dayDistance.intValue();
    }

    /**
     *
     * author tamdt1, 18/09/2009
     * ham cong them ngay
     * dau vao:
     *          - ngay can cong
     *          - so luong ngay can cong
     * dau ra:
     *          - ngay moi sau khi duoc cong them so ngay
     *
     *
     */
    public static Date addMonth(Date date, int numberAddedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, numberAddedDate);
        Date result = calendar.getTime();
        return result;
    }

    /**
     *
     * author tamdt1, 18/09/2009
     * ham cong them gio
     * dau vao:
     *          - ngay can cong
     *          - so luong gio can cong
     * dau ra:
     *          - ngay moi sau khi duoc cong them so gio
     *
     *
     */
    public static Date addHour(Date date, int numberAddedTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, numberAddedTime);
        Date result = calendar.getTime();
        return result;
    }

    //cong them phut
    public static Date addMINUTE(Date date, int numberAddedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, numberAddedDate);
        Date result = calendar.getTime();
        return result;
    }

    /**
     *
     * author tamdt1, 18/09/2009
     * ham lay ngay cuoi cung cua thang
     * dau vao:
     *          - ngay can lay ngay cuoi cung
     * dau ra:
     *          - ngay cuoi cung cua thang cung thang voi ngay dau vao
     *
     *
     */
    public static int getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        return lastDay;


    }

    /**
     *
     * author tamdt1, 18/09/2009
     * ham lay ngay cuoi cung cua thang
     * dau vao:
     *          - ngay can lay ngay cuoi cung
     * dau ra:
     *          - ngay cuoi cung cua thang cung thang voi ngay dau vao
     *
     *
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDay);
        Date lastDate = calendar.getTime();
        return lastDate;


    }

    /**
     *
     * author   : tamdt1, 13/11/2009
     * purpose  : ham lay ngay, gio he thong
     * dau ra   :
     *              - ngay gio hien tai
     *
     */
    public static Date getSysDate() {
        Date now = new Date();
        return now;
    }

    /**
     * author: lamnv, 05/03/2010
     * purpose: convert date sang dinh dang yyyymmdd
     * @param value
     * @return
     */
    public static String date2yyyyMMddStringNoSlash(Date value) {
        if (value != null) {
            SimpleDateFormat yyyymm = new SimpleDateFormat("yyyyMMdd");
            return yyyymm.format(value);
        }
        return "";
    }

    /**
     * author: lamnv, 05/03/2010
     * purpose: convert date sang dinh dang yyyymmddHH
     * @param value
     * @return
     */
    public static String date2yyyyMMddHHStringNoSlash(Date value) {
        if (value != null) {
            SimpleDateFormat yyyymm = new SimpleDateFormat("yyyyMMddHH");
            return yyyymm.format(value);
        }
        return "";
    }

    /**
     * author: lamnv, 05/03/2010
     * purpose: convert date sang dinh dang yyyy/MM/dd HH:mm:ss
     * @param value
     * @return
     */
    public static String date2YYYYMMddTime(Date value) {
        if (value != null) {
            SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return yyyyMMdd.format(value);
        }
        return "";
    }

    
    public static Timestamp date2Timestamp(Date value) {
            if (value != null) {
                    return new Timestamp(value.getTime());
            }
            return null;
    }
    //ThinDM R6762
    public static int getYearOfDate(String currentDate, String pattern) {
        try {
            Date date = convertStringToDate(currentDate, pattern);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            return year;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static String getFirstDayOfYear(String currentDate, String pattern) {
        try {
            Date date = convertStringToDate(currentDate, pattern);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.MONTH, 0);
            Date startDate = cal.getTime();
            return convertDateTimeToString(startDate, pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getLastDayOfYear(String currentDate, String pattern) {
        try {
            Date date = convertStringToDate(currentDate, pattern);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            cal.set(Calendar.YEAR, year + 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.MONTH, 0);
            Date startDateNextYear = cal.getTime();
            cal.setTime(startDateNextYear);
            cal.add(Calendar.DATE, -1);
            Date lastDayOfYear = cal.getTime();
            return convertDateTimeToString(lastDayOfYear, pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
