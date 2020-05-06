/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.common;

/**
 *
 * @Author      :   TuanPV
 * define constant to mapping the kind or report with its action
 * using for prepaid system
 */
public class ReportConstant {

    public static final int BIG_QUEUE_SIZE = 100;
    //for test
    //public static final int REPORT_MESSAGE_TIME_OUT = 1500;
    //Thoi gian timeout bao cao (tinh bang mili giay)
    public static final int REPORT_MESSAGE_TIME_OUT = 600000;
    //Thoi gian timeout cau lenh query tinh bang giay (10phut)
    public static final int QUERY_TIME_OUT = 600;
    public static final String ERROR_CODE = "ErrorCode";
    public static final String ERROR_FULL_REPORT_QUEUE = "REPORT_FULL_QUEUE";
    public static final String ERROR_FULL_THREAD_QUEUE = "FULL_THREAD_QUEUE";
    public static final String ERROR_NOT_VIETTEL_MSG = "NOT_VIETTEL_MSG";
    public static final String ERROR_EXCEPTION = "ERROR_EXCEPTION";
    public static final String WARNING_PARAMETER_NOT_VALID = "WARNING_PARAMETER_NOT_VALID";
    public static final String REPORT_SUCCESS = "1";
    public static final String CHECK_DB = "checkDb";
    public static final String REPORT_KIND = "reportKind";
    public static final String RESULT_FILE = "resultFile";
    public static final int IM_REPORT_REVENUE_NICE_NUMBER = 1;
    public static final int IM_REPORT_REVENUE = 2;
    public static final int IM_REPORT_BANK_RECEIPT = 3;
    public static final int IM_REPORT_BANK_RECEIPT2 = 4;
    public static final int IM_REPORT_BANK_RECIPIENTS_DETAIL = 5;
    public static final int IM_REPORT_RECEIPIENTS_EXPENSE = 6;
    public static final int IM_REPORT_IMPORT_EXPORT_STOCK = 7;
    /*ANHTT  - 06/02/2010 - DINH NGHIA CHO CAC LOAI BAO CAO DOANH THU  */
    public static final int IM_REPORT_REVENUE_INVOICE = 8;
    /*ANHTT  - 06/02/2010 - DINH NGHIA CHO CAC LOAI BAO CAO TINH HINH SU DUNG HOA DON */
    public static final int IM_REPORT_INVOICE = 9;
    public static final int IM_REPORT_DEPOSIT = 10;
    /*ANHTT- 10/02/2010 - BÁO cáo đổi hàng hỏng :*/
    public static final int IM_REPORT_CHANGE_GOOD = 11;
    /*ANHTT-10/02/2010 - Định nghĩa báo cáo thẻ kho:*/
    public static final int IM_REPORT_STOCK_TRANS = 12;
    public static final int IM_REPORT_GENERAL_STOCK_MODEL = 13;
    /*ANHTT-22/02/2010 - Báo cáo hủy hóa đơn và VAT*/
    public static final int IM_REPORT_DESTROY_INVOICE_VAT = 14;
    /*ANHTT-23/02/2010 - Báo cáo số đẹp*/
    public static final int IM_REPORT_NICE_NUMBER = 15;
    public static final int IM_REPORT_ACTIVE_SUBSCRIBER = 16;
    public static final int IM_REPORT_ANYPAY = 17;
    public static final int IM_REPORT_LIST_ACCOUNT_AGENT = 18;
    /* Vunt bao cao chi tiet BL */
    public static final int IM_REPORT_ACCOUNT_AGENT_BL_DETAIL = 19;
    /* Vunt bao cao phat trien thue bao */
    public static final int IM_REPORT_DEVELOP_SUBSCRIBER = 20;
    /* Vunt bao cao tai khoan thanh toan */
    public static final int IM_REPORT_ACCOUNT_AGENT = 21;
    //Bao cao thu chi dat coc cua hang
    public static final int IM_REPORT_DEPOSIT_BRANCH = 22;
    //Bao cao hoa don ton
    public static final int IM_REPORT_INVOICE_DAILY_REMAIN = 23;
    // Vunt bao cao vay tin dung
    public static final int IM_REPORT_CREDIT_BOOK = 24;
    // Vunt bao cao thanh toan phi ban hang moi
    public static final int IM_REPORT_ACTIVE_SUBSCRIBER_NEW = 25;
    // Vunt bao cao thanh toan phi ban hang chi tiet
    public static final int IM_REPORT_ACTIVE_SUBSCRIBER_DETAIL = 26;
    //tamdt1, 23/06/2010, bao cao mua hang cua CTV
    public static final int IM_REPORT_REVENUE_BOUGHT_BY_COLLABORATOR = 27;
    //Bao cao hàng chờ nhập
    public static final int IM_REPORT_STOCK_UNIMPORT = 28;
    public static final int IM_REPORT_DESTROY_TRANS = 29;
    //Bao cao doanh thu offline
    public static final int IM_REPORT_REVENUE_OFFLINE = 30;
    /* bao cao dang ky phan phoi so */
    public static final int IM_REPORT_REGISTER_RESULT = 31;
    public static final int IM_REPORT_REVENUE_BOUGHT_BY_SHOP = 32;
    public static final int IM_REPORT_STOCK_TOTAL_DETAIL = 33;
    public static final int IM_REPORT_STOCK_IMPORT_PENDING = 34;
    //trung dh3 báo cáo xuất nhập tồn
    public static final int IM_REPORT_STOCK_IMP_EXP_V2 = 73;
    //trung dh 3 end
    //trung dh3 báo cáo chi tiết xuất nhập
    public static final int IM_REPORT_DETAIL_STOCK_TRANS = 72;
    //trung dh 3 end
    public static final int IM_REPORT_LIST_CHANNEL = 35;
    public static final int IM_REPORT_ISDN_NUMBER = 36;
    public static final int IM_REPORT_LIST_SALE_SERVICES = 37;
    public static final int IM_REPORT_INVALID_SALES_KIT = 38;
    public static final int IM_REPORT_SMS_BY_DAY = 39;
    public static final int IM_REPORT_SMS_BY_MONTH = 40;
    public static final int IM_REPORT_SMS_BY_SUBJECT = 41;
    public static final int IM_REPORT_SMS_STATISTIC = 42;
    //Bao cao tong hop cong no
    public static final int IM_REPORT_DEBIT = 30030;//Haiti_IM bat dau tu 30.000
    //Bao cao GNT cho Haiti
    public static final int IM_REPORT_BANK_RECEIPT_IN_EX = 30031;//Haiti_IM bat dau tu 30.000
    // Bao cao doanh thu theo chuc danh nhan vien quan ly dai ly
    public static final int IM_REPORT_REVENUE_BY_AGENT_MANAGER = 30032; //Haiti_IM bat dau tu 30.000
    //haint41 7/2/2012 : bao cao so du dat coc
    public static final int IM_REPORT_BALANCE_DEPOSIT = 30033;
    //thaiph1: 5/6/201: bao cao phat trien kinh doanh doi thu
    public static final int IM_REPORT_COMPETITOR_BUSINESS = 44;
    public static final int IM_REPORT_CHANNEL_BUY_NOT_BUY_DETAIL = 45;
    public static final int IM_REPORT_SALE_OF_CHANNEL = 46;
    public static final int IM_REPORT_SALE_ANYPAY_TO_RETAIL = 47;
    //LeVT - bao cao cong tru anypay
    public static final int IM_REPORT_ADD_MINUS_ANYPAY = 48;
     //loint - Báo cáo hạn mức
    public static final int IM_REPORT_MAX_DEBIT = 49;
    public static final int IM_REPORT_MAX_DEBIT_CATALOGUE = 50;
    //trung dh3
    public static final int REPORT_STOCK_SENIOR_TOTAL  = 74;//bao cao xuát trả theo số lượng
    public static final int REPORT_STOCK_SENIOR_SERIAL  = 75;//bao cao xuát trả theo serial
    public static final int IM_REPORT_IMPORT_EXPORT_STOCK_V2  = 76;//bao cao xuất nhập tôp
    public static final int IM_REPORT_STOCK_REMAIL  = 77;//bao cao ton kho serial
    public static final int IM_REPORT_STOCK_GOODSBETWENTHEBRANCHES_TOTAL  = 78;//bao cao dieu chuyen theo so luong
    public static final int IM_REPORT_STOCK_GOODSBETWENTHEBRANCHES_DETAIL  = 79;//bao cao dieu chuyen theo chi tiet
    public static final int IM_REPORT_INVENTORY_LIMIT = 80;
    public static final int IM_REPORT_INVENTORY_LIMIT_STAFF = 81;
    //TruongNQ5 20140905 R6505
    public static final int IM_REPORT_STOCK_UNSOLD = 8888; // Báo cáo hàng hóa tồn kho chưa bán hàng
    //toancx R8388
    public static final int IM_REPORT_RENVENUE_VIP_CUSTOMER = 88; //bao cao doanh thu khach hang vip
}
