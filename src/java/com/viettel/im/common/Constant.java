package com.viettel.im.common;

/*
 * 
 * khai bao cac hang so su dung trong prroject
 * 
 */
public interface Constant {

    /****************************** START : CONFIG CHO MOZAMBIQUE ******************************/
    public final int SEARCH_MAX_RESULT = 2000;
    public final int SEARCH_MAX_DISTANCE_DAYS = 31;
    public final int LENGTH_SHOP_CODE = 5;
    public final int LENGTH_CHANNEL_CODE = 5;
    public final String PREFIX_MOBILE_LIST = "2:8:3:8;4:8;86:9";
    public boolean PRICE_AFTER_VAT = true;
    public String CURRENCY_DEFAULT = "MT";
    public String PREFIX_ISDN_HAITI = "86";
    public final String FREFIX_ROOT_STAFF = "MV";
    public Long KIT_INTEGRATION_SIM_STOCK_MODEL_ID = 202184L; //id cua mat hang sim dem dau lo
    public String KIT_INTEGRATION_SIM_STOCK_MODEL_CODE = "SIM_MB"; //id cua mat hang sim dem dau lo
    public Long KIT_INTEGRATION_SHOP_ID = 7282L; //id kho Viettel Telecom
    public String KIT_INTEGRATION_SHOP_CODE = "ISDN_FOR_KIT"; //(NATCOM : INTEGRATION_KIT)
    /****************************** END : CONFIG CHO MOZAMBIQUE ******************************/
    /******************************   START : CONFIG CHO HAITI   ******************************/
//    public final int LENGTH_SHOP_CODE = 2;
//    public final int LENGTH_CHANNEL_CODE = 4;
//    public final String PREFIX_MOBILE_LIST = "3:8;4:8;86:9";
//    public boolean PRICE_AFTER_VAT = false;
//    public String CURRENCY_DEFAULT = "HTG";
//    public String PREFIX_ISDN_HAITI = "509";
//    public final String FREFIX_ROOT_STAFF = "NC";
//    public Long KIT_INTEGRATION_SIM_STOCK_MODEL_ID = 144L; //id cua mat hang sim dem dau lo
    /******************************   END : CONFIG CHO HAITI   ******************************/
    public static final String SALE_TRANS_TYPE_LIST_SALE_CHANNEL = "3,9,23,";
    public static final String FILE_CONFIG = "config";//file config
    public static final String FILE_TEMPLATE_FILE_CONFIG = "template_file_config";//file template_file_config
    public static final String KEY_TEMPLATE_FILE_PATH = "TEMPLATE_PATH";//duong dan file template
    public static final String KEY_REPORT_FILE_PATH = "LINK_TO_DOWNLOAD_FILE";//duong dan file ket qua bc
    public static final String KEY_DOWNLOAD_REPORT_FILE_PATH = "LINK_TO_DOWNLOAD_FILE";//link down file ket qua bc
    //ACTIVE CARD
    public int LENGTH_CARD_NO = 12;
    public int LENGTH_BATCH_NO = 6;
    public final Long STATUS_NOT_SYNC = 0L;
    public final Long STATUS_IS_SYNC = 1L;
    
    public final boolean CHECK_DB_CTV_DDV = false;
//    public final int LEVEL_ASSIGN_CHANNEL_TO_GOODS = 1; //MZB
    public final String APP_PARAMS_TYPE_LEVEL_ASSIGN_CHANNEL_TO_GOODS = "LEVEL_ASSIGN_CHANNEL_TO_GOODS";
    public final String APP_PARAMS_TYPE_PROFILE_STATE = "PROFILE_STATE";
    public final String APP_PARAMS_TYPE_BOARD_STATE = "BOARD_STATE";
    public static final String ACTION_DELETE_PCK_GOOD_MAP_DETAIL = "DELETE_PCK_GOOD_MAP_DETAIL";
    public static final String ACTION_DELETE_PCK_GOOD_MAP = "DELETE_PCK_GOOD_MAP";
    public static final String ACTION_ADD_PCK_GOOD_MAP = "ADD_PCK_GOOD_MAP";
    public static final String ACTION_UPDATE_PCK_GOOD_MAP = "UPDATE_PCK_GOOD_MAP";
    public static final String ACTION_ADD_PCK_GOOD_MAP_DETAIL = "ADD_PCK_GOOD_MAP_DETAIL";
    public final Long PROFILE_STATE_HAS_NOT_PROFILE = 0L;
    public final Long PROFILE_STATE_PROFILE_ACTIVE = 1L;
    public final Long PROFILE_STATE_PROFILE_INACTIVE = 2L;
    public final Long BOARD_STATE_NOT_ISSUE = 0L;
    public final Long BOARD_STATE_ISSUE = 1L;
    public final Long BOARD_STATE_DAMAGED = 2L;
    public final String APP_PARAMS_TYPE_STAFF_STATUS = "STAFF_STATUS";
    public final String APP_PARAMS_TYPE_STAFF_CHECK_VAT = "STAFF_CHECK_VAT";
    public final String APP_PARAMS_TYPE_STAFF_AGENT_TYPE = "STAFF_AGENT_TYPE";
    public final Long STAFF_STATUS_DELETE = 0L;
    public final Long STAFF_STATUS_ACTIVE = 1L;
    public final Long STAFF_STATUS_INACTIVE = 2L;
    public final Long CHANNEL_TYPE_COLLECTOR = 1000100L; //Loai kenh CTV thu cuoc
    public final Long CHANNEL_TYPE_COMMUNE = 1000341L; //Loai kenh CTV Lang xa
    public final Long CHANNEL_TYPE_RETAIL = 1000321L;//fix cung
    public final Long CHANNEL_TYPE_IS_PRIVATE = 1L;
    public final Long CHANNEL_TYPE_IS_NOT_PRIVATE = 0L;
    public final Long CHANNEL_TYPE_CTV = 10L; //Loai kenh cong tac vien, doi voi Haiti la kenh dai ly door to door
    public final Long CHANNEL_TYPE_CTV_AP = 13L; //Loai kenh cong tac vien AP
    public final Long CHANNEL_TYPE_DB = 80043L; //Loai kenh diem ban    
    public final Long CHANNEL_TYPE_DS = 11L;// Loai kenh ban hang truc tiep (Direct Sale Channel)
    public final Long CHANNEL_TYPE_AG = 1000400L;// Loai kenh dai ly (Agent Channel)
    public final Long CHANNEL_TYPE_SS = 12L;// Loai kenh duong pho (Street channel)
    public final Long CHANNEL_TYPE_NC = 12L;// Kenh du phong (NC channel) => KH y/c the' ???
    public final boolean CHECK_POINT_OF_SALE = false; //PHAN BIET CTV DB qua pointOfSale (1:DB; 2:CTV):HAITI:false
    public Long CHANNEL_TYPE_STAFF = 14L; //id cua kenh nhan vien
    public Long CHANNEL_TYPE_SHOP = 1L; //id cua kenh ban hang
    public Long CHANNEL_TYPE_AGENT = 4L; //id cua kenh dai ly
    public Long CHANNEL_TYPE_SHOP_TECH = 22L;
    //Bo sung theo giong nhu o VN
    public Long CHANNEL_TYPE_AGENT_GRANT = 5L; //id cua kenh dai ly
    public Long CHANNEL_TYPE_AGENT_XNK = 6L; //id cua kenh dai ly
    public Long CHANNEL_TYPE_AGENT_MB = 7L; //id cua kenh dai ly MB
    public String CHANNEL_TYPE_AGENT_STR = "(4,5,6,7)"; //id cua kenh dai ly
    //Kho thu hoi - cho phep lay so tu kho thu hoi de dau KIT
    public final Long NORMAL_RELEASE_OWNER_ID = 105766L; //ma kho thu hoi thuong //NORMAL_RELEASE_STOCK
    public final Long NICE_RELEASE_OWNER_ID = 105519L; //ma kho thu hoi so dep //SPECIAL_RELEASE_STOCK
    public final String FUNCTION_STOCK_NORMAL_RELEASE_STOCK_CODE = "NORMAL_RELEASE_STOCK"; //ma kho thu hoi thuong //NORMAL_RELEASE_STOCK
    public final String FUNCTION_STOCK_SPECIAL_RELEASE_STOCK_CODE = "SPECIAL_RELEASE_STOCK"; //ma kho thu hoi so dep //SPECIAL_RELEASE_STOCK
    public final String FUNCTION_STOCK_INTEGRATION_KIT_STOCK_CODE = "INTEGRATION_KIT"; //ma kho dau kit //INTEGRATION_KIT
    public String DLU_STANDS = "SU";
    //check ky tu dac biet
    public java.util.regex.Pattern htmlTag = java.util.regex.Pattern.compile("<[^>]*>");
    //loai giay nop tien
    public Long BANK_RECEIPT_TYPE_IN = 1L;
    public Long BANK_RECEIPT_TYPE_EX = 2L;
    //dinh dang ma GNT
    public int BANK_RECEIPT_LEN_DATE = 8;
    public int BANK_RECEIPT_LEN_SERVICE = 2;
    public String BANK_RECEIPT_DATE_FORMAT = "dd/MM/yyyy";
    public String BANK_RECEIPT_CODE_DATE_FORMAT = "yyyyMMdd";
    public int BANK_RECEIPT_ACCOUNT_ROW = 7;
    public int BANK_RECEIPT_ACCOUNT_COLUMN = 8;
    public int BANK_RECEIPT_DATE_ROW = 13;
    public int BANK_RECEIPT_DATE_COLUMN = 8;
    public int BANK_RECEIPT_START_ROW = 24;
    public int BANK_RECEIPT_START_COLUMN = 2;
    public int BANK_RECEIPT_END_COLUMN = 10;
    public String BANK_RECEIPT_BALANCE_AT_PERIOD_START = "BALANCE AT PERIOD START";
    public String BANK_RECEIPT_BALANCE_AT_PERIOD_END = "BALANCE AT PERIOD END";
    //khai bao cac trang thai cua bang bank_receipt_external
    public Long BANK_RECEIPT_NOT_APPROVE = 1L; //chua duyet
    public Long BANK_RECEIPT_HAS_CANCELED = 2L; //da quet tinh cong no (cho GNT ngan hang)
    public Long BANK_RECEIPT_HAS_APPROVED = 3L; //da duyet
    public Long BANK_RECEIPT_HAS_DESTROYED = 4L; //da huy
    //khai bao cac loai dieu chinh
    public Long BANK_RECEIPT_ADJUSTMENT_TYPE_POSITIVE = 1L; //dieu chinh duong
    public Long BANK_RECEIPT_ADJUSTMENT_TYPE_NEGATIVE = -1L; //dieu chinh am
    //Duong dan file import GNT
    public String FOLDER_DIRECTORY_BANK_RECEIPT = "C:\\";
    //cac hang so khai bao cho bang book_type
    public Long INVOICE_TYPE_SALE = 1L; //loai hoa don cho sale
    public Long INVOICE_TYPE_PAYMENT = 2L; //loai hoa don cho payment
    //Dat coc
    //trang thai cua phieu thu/chi tien dat coc tai cua hang
    public Long STATUS_NON_DEPOSIT = 0L; //chua dat coc, chua lay lai tien
    public Long STATUS_DEPOSIT = 1L; //da dat coc, da lay lai tien
    //Trang thai hang ban dut dat coc
    public static Long STRANS_TYPE_SALE = 2L; //ban dut
    public static Long STRANS_TYPE_DEPOSIT = 1L; //dat coc
//    public Long TYPE_RECEIVE = 1L; //dat coc
//    public Long TYPE_PAY = 2L; //lay lai tien
    //trang thai cua phieu thu chi
    //public Long STATUS_NOT_APPROVE = 3L; //khong duyet
    //public Long STATUS_NONE_APPROVE = 1L; //chua duyet
    //public Long STATUS_APPROVE = 2L; //da duyet
    //loai chung tu
//    public Long RECEIVE_TYPE_CDCCH = 7L; //chi tra dat coc tai cua hang
//    public Long RECEIVE_TYPE_TDCCH = 4L; //thu dat coc tai cua hang
//    public Long RECEIVE_TYPE_CVTD = 11L; //chi vay tin dung
//    public Long RECEIVE_TYPE_TVTD = 12L; //thu vay tin dung
    //Kieu chung tu
    public String RECEIVE_KIND_REVENUE = "1"; //loai chung tu thu
    public String RECEIVE_KIND_EXPEND = "2"; //loai chung tu chi
    //Loai thanh toan
    public Long PAY_STATUS = 1L;
    public Long UNPAY_STATUS = 0L;
    //Duyet va khong duyet
    public Long APPROVED = 1L;
    public Long UN_APPROVED = 0L;
    //LOAI CHUNG TU (receipt_expense.receipt_type_id)
    public static final Long RECEIPT_TYPE_ID_SIMT = 3L;//Thu tien dat coc hang cua dai ly
    public static final Long RECEIPT_TYPE_ID_DCCTV = 8L;//Thu tien dat coc CTB
    public static final Long RECEIVE_TYPE_ID_CDCCH = 7L; //chi tra dat coc tai cua hang
    public static final Long RECEIVE_TYPE_ID_TDCCH = 4L; //thu dat coc tai cua hang
    public static final Long RECEIVE_TYPE_ID_CVTD = 11L; //chi vay tin dung
    public static final Long RECEIVE_TYPE_ID_TVTD = 12L; //thu vay tin dung
    //LOAI THU/CHI (receipt_expense.type)
    public static final String RECEIPT_EXPENSE_TYPE_OUT = "2";//Phieu chi
    public static final String RECEIPT_EXPENSE_TYPE_IN = "1";//Phieu thu
    public static final String RECEIPT_EXPENSE_STATUS_NONE_APPROVE = "1";//Chua duyet
    public static final String RECEIPT_EXPENSE_STATUS_APPROVE = "2";//Da duyet
    public static final String RECEIPT_EXPENSE_STATUS_UN_APPROVE = "3";//Tu choi duyet
    public static final String RECEIPT_EXPENSE_STATUS_CANCEL = "4";//Huy
    //LOAI THU/CHI (deposit.type)
    public static final String DEPOSIT_TYPE_OUT = "2";//Chi tien (rut tien)
    public static final String DEPOSIT_TYPE_IN = "1";//Thu tien (nop tien)
    //Loai dat coc(deposit.deposit_type)
    public static final Long DEPOSIT_TYPE_SIMT = 1L;//Tien dat coc dai ly
    public static final Long DEPOSIT_TYPE_SIMR = 4L;//Tien chi tra dai ly
    //HAN MUC SU DUNG (deposit.deposit_type)
    public static final Long DEPOSIT_TYPE_RMQT = 2L;//Roaming quoc te
    public static final Long DEPOSIT_TYPE_HMSD = 3L;//Han muc su dung Mobile
    public static final Long DEPOSIT_TYPE_HMSDP = 8L;//Han muc su dung Pstn
    public static final Long DEPOSIT_TYPE_HMSDA = 9L;//Han muc su dung Adsl
    //Set language default
    public java.util.Locale LOCALE_DEFAULT = java.util.Locale.JAPAN;
    public int NUMBER_DIGITS_AFTER_DECIMAL_DEFAULT = 2;
    public int NUMBER_DIGITS_AFTER_DECIMAL_AMOUNT = 2;
    public int NUMBER_DIGITS_AFTER_DECIMAL_QUANTITY = 0;
    //Cuong hoa don
    public boolean IS_VER_HAITI = true;
    public Long INVOICE_COUPON_STATUS_USE = 1L;
    public Long INVOICE_COUPON_STATUS_DELETE = 0L;
    public Long INVOICE_COUPON_STATUS_CONFIRM = 2L;
//    public Long DISCOUNT_METHOD_AMOUNT = 1L;//CK theo tong tien
//    public Long DISCOUNT_METHOD_QUANTITY = 2L;//CK theo so luong
//    public String DISCOUNT_TYPE_RATE = "1";//CK theo ti le %
//    public String DISCOUNT_TYPE_AMOUNT = "2";//CK theo so tien
    //Loai ma giao dich
    public String TRANS_CODE_LX = "LX";//Lenh xuat
    public String TRANS_CODE_PX = "PX";//Phieu xuat
    public String TRANS_CODE_LN = "LN";//Lenh nhap
    public String TRANS_CODE_PN = "PN";//Phieu nhap
    public String TRANS_CODE_PT = "PT";//Phieu thu
    public String TRANS_CODE_PC = "PC";//Phieu chi
    //loai tac dong doi voi hoa don
    public Long INV_RPT_ACTION_TYPE_RECEIVED = 1L; //so hoa don nhan duoc
    public Long INV_RPT_ACTION_TYPE_USED = 2L; //so hoa don su dung
    public Long INV_RPT_ACTION_TYPE_DESTROYED = 3L; //so hoa don huy
    public Long INV_RPT_ACTION_TYPE_LOSED = 4L; //so hoa don mat
    public Long INV_RPT_ACTION_TYPE_RETURNED = 5L; //so hoa don tra lai
    /*
    //Trang thai hang hoa trong kho
    //LamNV, 14/11/2009
     */
    public Long GOOD_SALE_STATUS = 0L;
    public Long GOOD_IN_STOCK_STATUS = 1L; //Dang trong kho
    public Long GOOD_OUT_STOCK_CONFIRM_STATUS = 3L; //Xuat kho tro xac nhan
    //tamdt1, 13/11/2009, start
    //dinh nghia hang so import
    public String LAST_UPDATE_KEY = "88888888";
    //dinh nghia hang so, cac loai dieu chuyen so
    public Long ISDN_TRANS_TYPE_CREATE = 1L; //tao dai so
    public Long ISDN_TRANS_TYPE_FILTER = 2L; //loc so
    public Long ISDN_TRANS_TYPE_ASSIGN_TYPE = 3L; //gan loai so
    public Long ISDN_TRANS_TYPE_DISTRIBUTE = 4L; //phan phoi so
    public Long ISDN_TRANS_TYPE_ASSIGN_MODEL = 5L; //gan mat hang
    public Long ISDN_TRANS_TYPE_REVOKE = 6L; //thu hoi so ngugn su dung
    public Long ISDN_TRANS_TYPE_UPDATE_PRICE = 7L; //cap nhat gia
    //tamdt1, 13/11/2009, end
    public String SALE_TRANS_NON_CHECK_STOCK = "0";
    public String SALE_TRANS_CHECK_STOCK = "1";
    //Loai gia
    //Giá bán đại lý
    public String PRICE_TYPE_AGENT = "1";//trung loai gia ban le ==> chua trien khai nen chua check lai
    //Giá đặt cọc
    public String PRICE_TYPE_DEPOSIT = "5";//ok
    //Giá bán kèm dịch vụ
    public String PRICE_TYPE_WITH_SERVICE = "4";//???
    //Giá khuyến mại
    public String PRICE_TYPE_PROMOTION = "3";//ok
    //Giá thực hiện dịch vụ
    public String PRICE_TYPE_SERVICE = "2";//ok
    //Giá bán CTV/DB
    public String PRICE_TYPE_COLLABOR = "9";//trung loai gia ban le ==> sua bang 9
    //Giá bán lẻ
    public String PRICE_TYPE_RETAIL = "1";//ok
    //Gia ban noi bo
    public String PRICE_TYPE_INTERNAL = "11";//ok
    public String PRICE_TYPE_PUNISH = "18";//ban phat
    //trang thai cua so (status)
    public Long STATUS_ISDN_ORIGINAL = 0L; //so chua duoc phan loai
    public Long STATUS_ISDN_NEW = 1L; //so moi
    public Long STATUS_ISDN_USING = 2L; //so dang su dung
    public Long STATUS_ISDN_SUSPEND = 3L; //so ngung su dung
    public Long STATUS_ISDN_LOCK = 5L; //so bi khoa
    //loai so (isdn_type)
    public String ISDN_TYPE_PRE = "1"; //so tra truoc
    public String ISDN_TYPE_POST = "2"; //so tra sau
    public String ISDN_TYPE_SPEC = "3"; //so dep
    public String ISDN_TYPE_UNKOWN = "-1"; //so chua phan loai
//    //Trạng thái số
//    public Long ISDN_STATUS_NEW= 1L;    //Số mới
//    public Long ISDN_STATUS_PAUSE= 2L;  //Số ngưng sử dụng
//    public Long ISDN_STATUS_USED= 3L;   //Số đang sử dụng
    //Trang thái sim, Số đã ghép thành kit
    public Long STATUS_KIT_INTEGRATED_BK = 4L;
    //loai isdn
//    public String ISDN_TYPE_PRE_PAID = "1"; //so tra truoc
//    public String ISDN_TYPE_POST_PAID = "2"; //so tra sau
    //Loại sale_service
    public Long SALE_TYPE_PACKAGE = 2L; //Gói hàng (bên IM bán)
    public Long SALE_TYPE_SERVICE = 1L; //Dịch vụ bán hàng (bên CM bán)
    //Trang thai giao dich
    public String SALE_TRANS_STATUS_NEW = "0";
    public String SALE_TRANS_STATUS_NOT_PAID = "1"; //chua thanh toan
    /**GIAO DICH CHUA LAP HOA DON_SALE_TRANS.STATUS = 2*/
    public String SALE_TRANS_STATUS_NOT_BILLED = "2"; //da thanh toan nhung chua lap hoa don
    public String SALE_TRANS_STATUS_BILLED = "3"; //da lap hoa don
    public String SALE_TRANS_STATUS_CANCEL = "4"; //huy
    //Loai giao dich
    public String SALE_TRANS_TYPE_RETAIL = "1"; //ban le
    public String SALE_TRANS_TYPE_PACKAGE = "30"; //ban theo goi
    public String SALE_TRANS_TYPE_AGENT = "2"; //ban dai ly
    public String SALE_TRANS_TYPE_COLLABORATOR = "3"; //ban cho CTV/ diem ban
    public String SALE_TRANS_TYPE_SERVICE = "4"; //dich vu do CM gui sang
    public String SALE_TRANS_TYPE_PROMOTION = "5"; //giao dich khuyen mai
    public String SALE_TRANS_TYPE_INTERNAL = "6"; //ban hang noi bo
    public String SALE_TRANS_TYPE_COL_RETAIL = "7"; //CTV ban cho khach le
    public String SALE_TRANS_TYPE_ANYPAY = "3"; //ban cho anypay
    public String SALE_TRANS_TYPE_RETAIL_FOR_COL = "9"; //Giao dich ban thay CTV
    public String SALE_TRANS_TYPE_PUNISH = "10"; //Giao dich ban phat
    public static Long SALE_TRANS_TYPE_STK_MANAGER_SALE_RETAIL = 20L; //NVQL ban le
    public static Long SALE_TRANS_TYPE_STK_MANAGER_SALE_CHANNEL = 21L; //NVQL ban dut cho kenh
    public static Long SALE_TRANS_TYPE_STK_MANAGER_SALE_ANYPAY = 22L; //NVQL ban Anypay cho khach le    
    public static Long SALE_TRANS_TYPE_RETAIL_ANYPAY = 23L; //Ban Anypay cho khach le (tren web va tren sdn)
    public String SALE_GROUP_WHOS = "1"; //ban cho dai ly
    public String SALE_GROUP_NOT_SALE = "2"; //khong tu giao dich
    public String SALE_GROUP_RETAIL = "3"; //ban le & lam DV
    public String SALE_GROUP_COL_RETAIL = "4"; //CTV ban le cho khach
    public String SALE_PRICE_BASIC = "23"; //gia goc
    //Nhom giao dich lap hoa don
    public Long SALE_INVOICE_GROUP_RETAIL = 3L; //Nhom giao dich ban le/ban CTV/ ban DB, lam DV
    //Trang thai giao hang
    public String SALE_TRANSFER_GOOD = "1"; //Đã giao hàng
    public String SALE_NON_TRANSFER_GOOD = "0"; //Chưa giao hàng
    //Loai kho
    public Long STOCK_NON_EXP = 0L; //Kho số
    public Long STOCK_MUST_EXP = 1L; //Kho hàng hoá
    //Loai hang
    public Long GOODS_HAVE_SERIAL = 1L; //Có quản lý theo serial
    public Long GOODS_NON_SERIAL = 0L; //Không quản lý theo serial
    //Don vi    
    public String VT_UNIT = "1"; //Don vi thuoc viettel
    public String NOT_IS_VT_UNIT = "2"; //Don vi khong thuoc viettel
    public String OBJECT_TYPE_SHOP = "1"; //Cua hang dai ly
    public String OBJECT_TYPE_STAFF = "2"; //Chi nhanh
    public String OBJECT_TYPE_CTV = "5"; //CTV
    //Trang thai hang
    public Long STATE_NEW = 1L; //Hang moi
    public Long STATE_OLD = 2L; //Hang cu
    public Long STATE_DAMAGE = 3L; //Hang hong
    public String STATE_NEW_DESC = "NEW"; //Hang moi
    public String STATE_DAMAGE_DESC = "DAMAGE"; //Hang hong
    //Loai giao dich
    public Long TRANS_IMPORT = 2L; //Nhap kho
    public Long TRANS_EXPORT = 1L; //Xuat kho
    public Long TRANS_RECOVER = 3L; //Thu hoi hang
    //Trang thai co the boc tham cua doi tuong nhan
    public String OWNER_CAN_DIAL = "dial"; // xuat kho doi tuong nhan co the phai boc tham (xuat cho cap duoi)
    public String OWNER_NON_DIAL = "nonDial"; // xuat kho doi tuong nhan khong phai boc tham (xuat cho cap tren, xuat cho nhan vien)
    public Long EXP_CAN_DIAL = 1L; //Mat hang trong lenh xuat co the phai boc tham
    public Long EXP_NON_DIAL = 2L; //Mat hang trong lenh xuat khong phai boc tham
    //Loai action
    public Long ACTION_TYPE_CMD = 1L; // LENH
    public Long ACTION_TYPE_NOTE = 2L; //phieu
    //Trang thai giao dich
    public Long TRANS_NON_NOTE = 1L; //Chua lap phieu
    public Long TRANS_NOTED = 2L; //Da lap phieu
    public Long TRANS_DONE = 3L; //Da xuat (nhap)
    public Long TRANS_EXP_IMPED = 4L; //phieu xuat da lap lenh (phieu) nhap
    public Long TRANS_CANCEL = 5L; //Da huy giao dich
    public Long TRANS_REJECT = 6L; //giao dich xuat kho bi tu choi nhap
    public Long TRANS_CTV_WAIT = 3L; //Lap phieu cho xac nhan cua CTV
    public Long TRANS_CTV_DONE = 4L; //CTV da xac nhan va giao dich thanh cong
    public Long TRANS_CTV_CANCLE = 6L; //CTV tu choi giao dich
    //Shop Type
    public String SHOP_TYPE = "1";
    public String AGENT_TYPE = "2";
    public String STAFF_TYPE = "2";
    //Loai ban hang
    public Long SELL_BREAK = 0L; //Ban dut
    public Long SELL_DEPOSIT = 1L; //Ban dat coc
    public Long NON_DRAW = 0L; //Khong phai boc tham
    public Long MUST_DRAW = 1L; //Phai boc tham
    //Trang thai dat coc
    public Long NON_DEPOSIT = 0L; //Khong phai dat coc
    public Long MUST_DEPOSIT = 1L; //Phai dat coc
    public Long NOT_DEPOSIT = 0L; //Chua dat coc
    public Long DEPOSIT_HAVE = 1L; //da dat coc
    //Trang thai boc tham
    public Long NOT_DRAW = 0L;
    public Long DRAW_HAVE = 1L;
    //Trang thai thanh toan
    public Long NOT_PAY = 0L;
    public Long PAY_HAVE = 1L;
    public Long PAY_CANCLE = 2L; //Huy giao dich cong tien cho CTV
    //Trang thai giao dich ban hang
    public Long SALE_NON_PAY = 1L; //GIAO DICH CHUA THANH TOAN
    public Long SALE_PAY_NOT_BILL = 2L;//GIAO DICH DA THANH TOAN CHUA LAP  HOA DON
    public Long SALE_BILLED = 3L;//GIAO DICH DA LAP HOA DON
    //co trang thai chung
    public Long STATUS_DELETE = 0L; //trang thai da bi xoa
    public Long STATUS_USE = 1L; //trang thai dang duoc su dung
    // Trang thai cua cua hang
    public Long STATUS_ACTIVE = 1L; //trang thai co hieu luc
    public Long STATUS_INACTIVE = 0L; //trang thai khong hieu luc
    //Trang thai lenh xuat, nhap
    public Long STATUS_CMD_NOTED = 2L; //Da la phieu
    //Trang thai phieu xuat, nhap
    public Long STATUS_NOTE_EXPORTED = 2L;
    //Trang thai hang hoa trong phieu xuat, nhap
    public Long STATUS_DETAIL_CMD = 1L; //Da lap lenh xuat
    public Long STATUS_DETAIL_NOTE = 2L; //Da lap phieu xuat
    public Long STATUS_DETAIL_EXPED = 3L; //Da xuat kho
    public Long STATUS_EXP_CRE_IMP_CMD = 4L; //Đơn vị nhận đã lập phiếu nhập
    public Long STATUS_EXP_CRE_IMP_STA = 5L; //
    public Long STATUS_EXP_CRE_IMP = 6L;
    //Dung chung cho tat ca cac class
    public String ERROR_PAGE = "errorPage";
    public String SESSION_TIME_OUT = "sessionTimeout";
    public String USER_TOKEN = "userToken";
    public String EMPTY_STRING = "";
    public String DATE_FORMAT = "dd/MM/yyyy";
    //Loai khoan muc
    public Long REPORT_TYPE_PLUS = 1L;//Khoan muc bo sung
    public Long REPORT_TYPE_MINUS = 2L;//Khoan muc giam tru
    //
    public Long STATUS_SIM_IN_STOCK = 1L;//Trang thai SIM trong kho, TrongLV update at 28/10/2009 (modify function cancelTrans())
    public String STATUS_SIM_PRE_PAID = "1";
    public String STATUS_SIM_POST_PAID = "2";
    //Trang thai trong bang Import_partner va import_partner_detail
    public Long STATUS_IMPORT_EXPORTED = 0L;
    public Long STATUS_IMPORT_COMPLETED = 1L;
    public Long STATUS_IMPORT_ERROR = 2L;
    public Long STATUS_IMPORT_NOT_EXECUTE = 3L;
    public Long STATUS_IMPORT_BY_FILE = 1L;
    public Long STATUS_IMPORT_BY_RANGE = 2L;
    public Long STATUS_SALED = 0L;//DA BAN
    //
    public Long OWNER_TYPE_SHOP = 1L; //Doi tuong shop
    public Long OWNER_TYPE_STAFF = 2L; //Doi tuong la staff
    public Long OWNER_TYPE_CUST = 3L; //Doi tuong la khach hang
    public Long OWNER_TYPE_PARTNER = 4L;
    public Long OWNER_TYPE_CTV = 5L; //Doi tuong la staff
    public Long OWNER_CUST_ID = 0L; //OwnerId chung cua cac doi tuong khach hang le
    public Long INVOICE_TYPE_CREDIT = 9L; // hoa don dieu chinh
    public static String CREDIT_INVOICE_USED = "CREDIT_INVOICE_USED";
    public static final Long RECEIVER_TYPE_SHOP = 1L;
    public static final Long RECEIVER_TYPE_STAFF = 2L;
    public static Long MOV_SHOP_ID = 7282L;
    public static String GET_INVOICE_USED = "GET_INVOICE_USED";
    public static String INVOICE_USED = "INVOICE_USED";
    public static Long CONNECTION_TYPE_SOLD = 1L; //dau noi mat hang da ban dut
    public static String SALE_TRANS_TYPE_PROMOTION_CHANNEL = "34"; //giao dich ban hang khuyen mai cho kenh
    public static String SALE_TRANS_TYPE_CREATE_INVOICE = "13";
    public static String SALE_TRANS_TYPE_RETAIL_BY_STK = "11";
    public static String SALE_TRANS_TYPE_ANYPAY_FROM_BANK = "31";
    public static String SALE_TRANS_TYPE_PROMOTION_AGENT = "35"; //giao dich ban hang khuyen mai cho kenh
    public static String SALE_TRANS_TYPE_DAMAGE = "12"; //gia goc
    public static String SALE_TRANS_TYPE_ADJUST = "68"; //giao dich dieu chinh
    public static String STOCK_MODEL_CODE_AIRTIME = "TCANYPAY2";       //Ma TCDT
    public static Long STATUS_SIM_HAS_SOLD = 0L; //sim da ban
    public Long OWNER_TYPE_MASTER_AGENT = 9L; //Doi tuong la Dai ly Master Agent
    //id cua cac stocktype
    public Long STOCK_TYPE_EXP = 1L;
    public Long STOCK_TYPE_NON_EXP = 0L;
    public Long STOCK_ISDN_MOBILE = 1L;
    public Long STOCK_ISDN_HOMEPHONE = 2L;
    public Long STOCK_ISDN_PSTN = 3L;
    public Long STOCK_SIM_PRE_PAID = 4L;
    public Long STOCK_SIM_POST_PAID = 5L;
    public Long STOCK_CARD = 6L;
    public Long STOCK_HANDSET = 7L;
    public Long STOCK_KIT = 8L;
    public Long STOCK_SUMO = 9L;
    public Long STOCK_ACCESSORIES = 10L;
    public Long STOCK_LAPTOP = 13L; //loai mat hang moi, laptop, khai bao de CM dau dich vu combo3
    public Long STOCK_SALE_SERVICES = -1L; //tamdt1, cau hinh gia dinh cho stock_type cua dich vu phuc vu bao cao doanh thu
    public Long STOCK_GOOD_PACKAGE = -2L; //tamdt1, cau hinh gia dinh cho stock_type cua goi hang phuc vu bao cao doanh thu
    //mo ta loai dich vu trong file excel
    public String STOCK_ISDN_MOBILE_DESC = "MOBILE";
    public String STOCK_ISDN_HOMEPHONE_DESC = "HOMEPHONE";
    public String STOCK_ISDN_PSTN_DESC = "PSTN";
    //mo ta loai so trong file excel
    public String ISDN_TYPE_PRE_DESC = "PRE_PAID"; //so tra truoc
    public String ISDN_TYPE_POST_DESC = "POST_PAID"; //so tra sau
    public String ISDN_TYPE_SPEC_DESC = "SPECIAL"; //so dep
    //mo ta tran thai so trong file excel
    public String ISDN_STATUS_NEW_DESC = "NEW"; //so moi
    public String ISDN_STATUS_SUSPEND_DESC = "SUSPEND"; //ngung su dung
    //Chia dai boc tham on 09/05/2009
    public Long CHECK_DIAL = 1L; //Can boc tham
    public Long UN_CHECK_DIAL = 0L;//Khong can boc tham
    //Trang thai boc tham
    public Long DIALED = 1L; //Da phan vao block boc tham
    public Long NON_DIAL = 0L;//chua phan vao block
    //tamdt1, 14/05/2009
    public String PRICE_TYPE_CODE_OF_SALE_SERVICES = "2"; //id cua loai gia ban kem dich vu
    //public String REVOKE_SHOP_CODE = "KHOTHUHOI"; //ma cua kho thu hoi
//    public String COMMON_SHOP_CODE = "KHODAULO"; //ma cua kho dau lo
//    public String COMMON_SIM_CODE = "SIMDAULO"; //ma cua mat hang sim dau lo
//    public Long KHO_DAU_LO = 461L; //ma cua kho dau lo
//    public Long SIM_DAU_LO = 10724L; //ma cua mat hang sim dau lo
    //phan khai bao loai chiet khau
    public String DISCOUNT_TYPE_RATE = "1"; //chiet khau theo %
    public String DISCOUNT_TYPE_AMOUNT = "2"; //chiet khau theo tong tien
    //
    public Long DISCOUNT_METHOD_AMOUNT = 1L; //chiet khau theo tong tien
    public Long DISCOUNT_METHOD_QUANTITY = 2L; //chiet khau theo so luong
    //tuannv,23-05-2009
    public String CHECK_COM = "1"; //Tinh hoa hong
    public String IS_VT_UNIT = "1"; //thuoc viettel
    public String IS_NOT_VT_UNIT = "2"; //Khong thuoc viettel
    public String INPUT_TYPE_AUTO = "1"; //kieu nhap du lieu vao la tu dong (su dung bo dem)
    public String INPUT_TYPE_MANUAL_BY_QUANTITY = "2"; //kieu nhap du lieu vao bang tay theo so luong
    public String INPUT_TYPE_MANUAL_BY_AMOUNT = "3"; //kieu nhap du lieu vao bang tay theo so tien
    public String APP_BLOCK_TYPE = "BLOCK_TYPE"; //Khong thuoc viettel
    //other
    public Long SPECIAL_SHOP_ID = 8L; //id kho chuc nang rieng cua Viettel
//    public Long VT_SHOP_ID = 1L; //id kho Viettel Telecom
    //tamdt1, kho viettel chuyen sang id = 7282, migrate
    public Long VT_SHOP_ID = 7282L; //id kho Viettel Telecom
    public Long VT_AP_SHOP_ID = 7282L; //id kho AP cua Viettel Telecom
//    public Long REVOKE_SHOP_ID = 1968L; //id kho thu hoi
    //tamdt1, kho thu hoi chuyen sang ma 8450
    public Long REVOKE_SHOP_ID_BK = 8450L; //id kho thu hoi  -- thay bang SHOP_CODE = FUNCTION_STOCK_NORMAL_RELEASE_STOCK_CODE
//    public Long KIT_INTEGRATION_SIM_STOCK_MODEL_ID = 10807L; //id cua mat hang sim dem dau lo
    //tamdt1, mat hang sim trang tra truoc chuyen sang ma 144
    public Long ROOT_CHANNEL_ID = -888L; //id cua kenh goc (trong db = null, nhung kenh cap 1 co parentId = null -> -888 de xu ly tren code)
    //dinh nghia cac type trong bang app_params
    public String APP_PARAMS_DISTANCE_STEP = "DISTANCE_STEP"; //Buoc nhay khi nhap hang tu doi tac theo dai serial
    public String APP_PARAMS_PRICE_POLICY = "PRICE_POLICY"; //chinh sach gia
    public String APP_PARAMS_PRICE_POLICY_DEFAUT_CTV = "PRICE_POLICY_DEFAUT_CTV"; //chinh sach gia mac dinh cho NVDB
    public String APP_PARAMS_PRICE_POLICY_DEFAUT_DB = "PRICE_POLICY_DEFAUT_DB"; //chinh sach gia mac dinh cho DB
    public String APP_PARAMS_PRICE_TYPE = "PRICE_TYPE"; //loai gia
    public String APP_PARAMS_DISCOUNT_POLICY = "DISCOUNT_POLICY"; //chinh sach chiet khau
    public String APP_PARAMS_DISCOUNT_POLICY_DEFAUT_CTV = "DISCOUNT_POLICY_DEFAUT_CTV"; //chinh sach chiet khau mac dinh cho CTV
    public String APP_PARAMS_DISCOUNT_POLICY_DEFAUT_DB = "DISCOUNT_POLICY_DEFAUT_DB"; //chinh sach chiet khau mac dinh cho DB
    public String APP_PARAMS_DISCOUNT_POLICY_DEFAULT_VIP_CUSTOMER = "DISCOUNT_POLICY_DEFAULT_VIP_CUSTOMER"; //cau hinh chiet khau cho khach hang vip
    public String APP_PARAMS_REASON_SALE_DEFAULT_VIP_CUSTOMER = "REASON_SALE_DEFAULT_VIP_CUSTOMER"; //cau hinh ly do ban cho khach hang vip
    public String APP_PARAMS_TIME_EXPIRE_OTP = "TIME_EXPIRE_OTP"; //cau hinh thoi gian OTP expire: cau hinh theo phut
    public String APP_PARAMS_STAFF_TYPE = "STAFF_TYPE"; //chuc vu nhan vien
    public String APP_PARAMS_STOCK_MODEL_UNIT = "STOCK_MODEL_UNIT"; //don vi mat hang
    public String PARAM_TYPE_BLOCK = "BLOCK_TYPE"; //loai param block
    public String PARAM_TYPE_AMIN_FILTER_NUMBER = "AMIN_FILTER_NUMBER"; //So thue bao admin gui tin nhan khi loc so dep
    //ly do giao dich
    public String SALE_REASON_GROUP_CODE = "SALE_EXP"; //ma cua nhom ly do ban hang
    public static Long IMPORT_STOCK_FROM_PARTNER_REASON_ID = 4453L; //ma cua ly do nhap hang tu doi tac (fix cung)
    public static Long EXPORT_STOCK_FROM_PARTNER_REASON_ID = 4454L; //ma cua ly do nhap hang tu doi tac (fix cung)
    public static Long IMPORT_STOCK_ANYPAY_FROM_PARTNER_REASON_ID = 400L; //ma cua ly do nhap hang tu doi tac (fix cung)
    public static Long EXPORT_STOCK_ANYPAY_TO_PARTNER_REASON_ID = 401L; //ma cua ly do nhap hang tu doi tac (fix cung)
    
    public static Long IMPORT_STOCK_PROCESS_REASON_ID = 200141L; //ma cua ly do nhap hang tu doi tac (fix cung)
    public static Long EXPORT_STOCK_PROCESS_REASON_ID = 200140L; //ma cua ly do nhap hang tu doi tac (fix cung)
    public static Long EXP_SIM_WHEN_IMP_KIT_FROM_PARTNER_REASON_ID = 200120L; //ma cua ly do xuat sim khi nhap kit tu doi tac
    public static Long EXP_STOCK_TO_UNDERLYING_REASON_ID = 4544L; //ma cua ly do lap lenh xuat kho cho cap duoi (fix cung)
    //Lap phieu thu/chi
    public String REASON_GROUP_RECEIVE_DEPOSIT_SHOP = "RECEIVE_DEPOSIT_SHOP";//Thu tien dat coc dai ly/cua hang
    public String REASON_GROUP_PAY_DEPOSIT_SHOP = "PAY_DEPOSIT_SHOP";//Chi tra tien dat coc khach hang tai cua hang
    public String REASON_GROUP_PAY_MONEY = "PAY_MONEY";//Thu tien dat coc dai ly
    //Ly do xuat nhap kho
    //Ly do xuat kho cho cap duoi
    public String STOCK_EXP_UNDER = "STOCK_EXP_UNDER";
    //Ly do xuat kho cho cap duoi
    public String STOCK_EXP_PARTNER = "STOCK_EXP_PARTNER";
    //Ly do xuat kho cho cap duoi
    public String STOCK_EXP_AGENTS = "STOCK_EXP_AGENTS";
    //Ly do xuat cho cap tren
    public String STOCK_EXP_SERNIOR = "STOCK_EXP_SERNIOR";
    //Ly do xuat cho nhan vien
    public String STOCK_EXP_STAFF = "STOCK_EXP_STAFF";
    //Ly do xuat cho cong tac vien
    public String STOCK_EXP_COLLABORATOR = "STOCK_EXP_COLL";
    //Ly do thu hoi hang hong
    public String STOCK_EXP_RECOVER = "STOCK_EXP_RECOVER";
    //Ly do nhan vien xuat tra hang 
    public String STOCK_EXP_STAFF_SHOP = "STOCK_EXP_STAFF_SHOP";
    //Ly do nhap kho
    //Ly do nhap tu cap duoi
    public String STOCK_IMP_UNDER = "STOCK_IMP_UNDER";
    //Ly do nhap tu cap tren
    public String STOCK_IMP_SERNIOR = "STOCK_IMP_SERNIOR";
    //Ly do nhan vien nhap tu cua hang
    public String STOCK_IMP_STAFF_SHOP = "STOCK_IMP_STAFF_SHOP";
    //Ly do nhap tu nhan vien
    public String STOCK_IMP_FROM_STAFF = "STOCK_IMP_FROM_STAFF";
    //Thu hoi hang cua CTV
    public String STOCK_IMP_CTV = "STOCK_IMP_CTV";
    //Ly do thu tien nap TK CTV
    public String REASON_ADD_MONEY_TO_COLL = "DCCTV";//group: add to db at 1/4
    //Ly do rut tien nap TK CTV
    public String REASON_GET_MONEY_TO_COLL = "RTTKTT";//group: add to db at 1/4
    //So tien Max tin dung
    public Long REAL_DEBIT_MAX = 1000000L;
    //So tien Min tai khoan
    public Long REAL_BALANCE_MIN = 2000000L;
    //Ban the cao dien tử
    public String SALE_TO_ANYPAY = "SALE_TO_ANYPAY";
    //LOAI GIAO DICH CUA TK ANYPAY
    public String ANYPAY_TRANS_TYPE_TRANS = "TRANS";
    public String ANYPAY_TRANS_TYPE_LOAD = "LOAD";
    //LOAI BAO CAO GIAO DICH CUA ANYPAY:
    public String ANYPAY_REPORT_TYPE_GENERAL = "1";
    public String ANYPAY_REPORT_TYPE_DETAIL = "2";
    //LOAI BAO CAO GIAO DICH CUA DEPOSIT:
    public String DEPOSIT_REPORT_TYPE_GENERAL = "1";
    public String DEPOSIT_REPORT_TYPE_DETAIL = "2";
    public Long DEPOSIT_STATUS_CANCEL = 2L;
    public String DEPOSIT_TYPE_ROAMING = "(2)";
    //ThanhNC--Bao cao xuat nhap ton kho
    //Du lieu bao cao bao gom ca nhan vien
    public Long REPORT_INCLUDE_STAFF = 1L;
    //Du lieu bao cao bao khong co du lieu cua nhan vien
    public Long REPORT_NOT_INCLUDE_STAFF = 0L;
    //Loai Bao cao
    //Bao cao tong hop
    public Long REPORT_GENERAL = 0L;
    //Bao cao chi tiet
    public Long REPORT_DETAIL = 1L;
    //
    public Long CREATE_STOCK_ISDN_MAX_QUANTITY = 2000000L; //maximum so luong so duoc phep tao/ 1 lan
    public Long ASSIGN_ISDN_MAX_QUANTITY = 2000000L; //maximum so luong so duoc phep tao/ 1 lan
    public Long DISTRIBUTE_ISDN_MAX_QUANTITY = 2000000L; //maximum so luong so duoc phep tao/ 1 lan
    //cac kieu du lieu cho tham so bo dem
    public String DATA_TYPE_DATE = "Date";
    public String DATA_TYPE_NUMBER = "Number";
    public String DATA_TYPE_Varchar2 = "Varchar2";
    //id cua cac loai telecomservice khac nhau, dong bo voi bang telecomservice
    public Long TELECOM_SERVICE_ID_MOBILE = 1L;
    public Long TELECOM_SERVICE_ID_HP = 2L;
    public Long TELECOM_SERVICE_ID_PSTN = 3L;
    public Long TELECOM_SERVICE_ID_ADSL = 4L;
    public Long TELECOM_SERVICE_ID_LL = 5L;
//    TrongLV
    public Double DISCOUNT_RATE_NUMERATOR_DEFAULT = 0d;        //Tu so (x/y)
    public Double DISCOUNT_RATE_DENOMINATOR_DEFAULT = 100d;    //Mau so (x/y)
    public String REASON_DESTROY_ANYPAY_TRANS = "ANYPAY_TRANS_DES";        //Ly do huy giao dich ban Anypay
    public String REASON_DESTROY_INVOICE_LIST = "INVOICE_DES_REASON";      //Ly do huy hoa don chua su dung
    public String REASON_DESTROY_INVOICE_USED = "SALE_INVOICE_DES";        //Ly do huy hoa don ban hang
    public String REASON_PAY_DEPOSIT_AT_SHOP = "PAY_DEPOSIT_SHOP";         //Ly do dat coc tai cua hang
    public String REASON_CREATE_INVOICE_ANYPAY = "INVOICE_ANYPAY";         //Ly do lap hoa don ban Anypay
    public String REASON_CREATE_INVOICE_COL_RETAIL = "INVOICE_COL_RETAIL"; //Ly do lap hoa don ban le & lam dich vu
    public String REASON_CREATE_SALE_COL_RETAIL = "SALE_COL_RETAIL";       //Ly do lap giao dich ban le
    public String REASON_CREATE_SALE_ANYPAY = "SALE_ANYPAY";               //Ly do lap giao dich ban Anypay
    /**
     *App_Params_Type
     */
    public String PARAM_TYPE_PAY_METHOD = "PAY_METHOD";
    public String PARAM_TYPE_PAY_SALE_TRANS_STATUS = "SALE_TRANS.STATUS";
    /*
     * Trang thai hoa don
     *
     */
    public String INVOICE_USED_STATUS_USE = "1"; // Trang thai hoa don ban hang - da lap
    public String INVOICE_USED_STATUS_DELETE = "4"; // Trang thai hoa don ban hang - da huy    
    public Long INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP = 1L;         //Trong kho don vi
    public Long INVOICE_LIST_STATUS_ASSIGNED_UNCONFIRMED = 2L;      //Da giao - don vi chua xac nhan
    public Long INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF = 3L;        //Trong kho nhan vien
    public Long INVOICE_LIST_STATUS_USING_COMPLETED = 4L;           //Da su dung het
    public String INVOICE_LIST_STATUS_COMPLETED_BY_USING = "1_4";           //Da su dung het do da lap hoa don ban hang
    public String INVOICE_LIST_STATUS_COMPLETED_BY_SPLIT = "1_4_1";         //Da su dung het do tach dai khi tra cuu hoa don
    public String INVOICE_LIST_STATUS_AVAILABLE_BY_NOT_USING = "1_5";       //Chua su dung do chua lap hoa don ban hang nao
    public String INVOICE_LIST_STATUS_AVAILABLE_BY_SPLIT = "1_5_1";         //Chua su dung do tach dai khi tra cuu hoa don
    public Long INVOICE_DESTROYED_STATUS_DESTROYED_UNAPPROVED = 0L;         //Chua duyet huy
    public Long INVOICE_DESTROYED_STATUS_DESTROYED_APPROVED = 1L;           //Da duyet huy
    public Long INVOICE_LOG_STATUS_CREATED = 1L;                    //Tao dai hoa don
    public Long INVOICE_LOG_STATUS_ASSIGNED_UNCONFIRMED = 2L;       //Da giao - don vi chua xac nhan
    public Long INVOICE_LOG_STATUS_ASSIGNED_CONFIRMED = 3L;         //Da giao - don vi da xac nhan    
    public Long INVOICE_LOG_STATUS_DESTROYED_UNAPPROVED = 5L;       //Da huy - VT chua duyet
    public Long INVOICE_LOG_STATUS_DESTROYED_CANCEL = 6L;           //Da huy - don vi khoi phuc lai (khong huy nua khi chua duoc VT duyet)
    public Long INVOICE_LOG_STATUS_DESTROYED_APPROVED = 7L;         //Da huy - VT da duyet
    public Long INVOICE_LOG_STATUS_DESTROYED_BY_VT = 8L;            //Da huy boi Viettel Telecom
    public Long INVOICE_LOG_STATUS_RETRIEVED = 4L;                  //Thu hoi hoa don
    public Long INVOICE_LOG_STATUS_RECOVERED = 9L;                 //Khoi phuc hoa don da lap cho giao dich ban hang (de su dung lai)
    public String SHEET_NAME_DEFAULT = "Sheet1";
    public Long DATE_DIS_INVOICE_DAY = -0L;
    public Long DATE_DIS_DEBIT_DAY = -0L;
    public String VIETTEL_TELECOM_NAME = "Natcom S.A";
    public String VIETTEL_TELECOM_ADDRESS = "Port-au-Prince";
    public String RETURN_MESSAGE = "returnMsg";
    public String RETURN_MESSAGE_VALUE = "returnMsgValue";
    public String STOCK_MODEL_CODE_TCDT = "ANYPAY";       //Ma TCDT
    public String STOCK_MODEL_CODE_KITCTV = "(KITCTV,KIT_MFS,KIT_MOVITAKO,KIT_MOVITAKO_MA)";//Ma KIT CTV
    public String PAY_METHOD_MONNEY = "1";              //
    public String PAY_METHOD_ACCOUNT_TRANFER = "2";   //
    public String PAY_METHOD_POS = "3";              //
    public Long TRANS_RECEIVER_TYPE_ANYPAY = 1L;        //
    //AnyPay ZTE
    public Long ACCOUNT_AGENT_CHECK_ISDN = 1L;//Kich hoat co SIM
    public Long ACCOUNT_AGENT_CHECK_NOT_ISDN = 0L;//Kich hoat khong can SIM
    public Long ACCOUNT_TYPE_ANYPAY = 1L; // Loai tai khoan anypay (e-load)
    public Long ACCOUNT_TYPE_BALANCE = 2L; // Loai tai khoan thanh toan (e-pay)
    public Long ACCOUNT_TYPE_PAYMENT = 5L; // Loai tai khoan gach no (payment)
    public Long ACCOUNT_BALANCE_DEFAULT = 0L; // So du khoi tao mac dinh cua tai khoan anypay
    public String ACCOUNT_PASSWORD_DEFAULT = "123456"; // Mat khau mac dinh cua tai khoan anypay
    public String ACCOUNT_ACCESS_TYPE_SMS = "1"; //
    public String ACCOUNT_ACCESS_TYPE_USSD = "1"; //
    public String ACCOUNT_ACCESS_TYPE_SMSUSSD = "1"; //
    public String ACCOUNT_SERIALID_PREFIX = "00"; // Tien to serial (+DateTime.getTime()->serialId)
    public String ACCOUNT_TRANSSERIAL_PREFIX = "50"; // Tien to serial (+DateTime.getTime()->serialId)
    public String ACCOUNT_ISDN_PREFIX = "0"; // Ma vung so ISDN
    public String ACCOUNT_ID_LENGTH = "18"; // Do dai Id cua account AnyPay
    public String ACCOUNT_RESULT_SUCCESS = "0"; // Ket qua tra ve thanh cong (create, query, modify, cancel)
//    public String ACCOUNT_STATUS_CANCEL = "0"; // Trang thai tai khoan anypaytren IN
//    public String ACCOUNT_STATUS_NORMAL = "1"; // Trang thai tai khoan anypay tren IN
//    public String ACCOUNT_STATUS_SUSPEND = "2"; // Trang thai tai khoan anypay tren IN
//    public String ACCOUNT_STATUS_LOCK = "3"; // Trang thai tai khoan anypay tren IN
    public String ACTION_TYPE_CREATE = "1"; // Tao moi tai khoan
    public String ACTION_TYPE_QUERY = "2"; // Xem thong tin tai khoan
    public String ACTION_TYPE_MODIFY = "3"; // Sua thong tin tai khoan
    public String ACTION_TYPE_CANCEL = "4"; // Xoa tai khoan
    public String ACTION_TYPE_CANCEL_BALANCE = "5"; // Xoa balance cua tai khoan
    public Long ANYPAY_TRANS_TYPE_RECHARGE = 1L;//Nap tien cho tai khoan anypay
    public Long ANYPAY_TRANS_TYPE_ANYPAY = 2L;//Chuyen tien sang tai khoan anypay khac
    public Long ANYPAY_TRANS_TYPE_CUSTOMER = 3L;//Nap tien cho khach hang
    /* 
    //Kieu giao dich doi voi tai khoan dat coc deposit
     */
    //0: Khoi tao TK. 1: Nap tien 2: Tru tien xuat dat coc, 3: Cong tien hang dat coc thu hoi. 
    //4: Cong tien hang dat coc DN. 5: Tru tien DN. 6: Cong tien CTV nop HD ve. 
    //7: hoa hong. 8: Tra cuu. 9: 
    public Long DEPOSIT_TRANS_TYPE_CREATE = 0L;//Khoi tao tai khoan
    public Long DEPOSIT_TRANS_TYPE_CHARGE = 1L;//Nap tien/rut tien cho tai khoan deposit
    public Long DEPOSIT_TRANS_TYPE_MINUS_EXPORT = 2L;//Tru tien xuat dat coc/ cong tien thu hoi    
    public Long DEPOSIT_TRANS_TYPE_INVOICE = 8L;//Lap/huy hoa don thay
    public Long DEPOSIT_TRANS_TYPE_DESTROY_RECEIPT = 9L;//Huy phieu thu/chi
    //MERGE VTT
    public Long DEPOSIT_TRANS_TYPE_ADD_PUNISH = 22L;//Cong tien dat coc ban phat
    public Long DEPOSIT_TRANS_TYPE_MINUS_PUNISH = 23L;//Tru tien dat coc ban phat
    public Long DEPOSIT_TRANS_TYPE_ADD_COLL_RETAIL = 50L;//Cong tien dat coc ban thay
    public Long DEPOSIT_TRANS_TYPE_MINUS_COLL_RETAIL = 51L;//Tru tien dat coc ban thay
    //MERGE VTT
    /*
    //Trang thai giao dich doi voi tai khoan dat coc deposit
    //LamNV, 14/11/2009
     */
    //Trang thai. 1: Dang xu ly, 2: Xu ly thanh cong, 3: That bai
    public Long DEPOSIT_TRANS_STATUS_PROGRESS = 1L;
    public Long DEPOSIT_TRANS_STATUS_DONE = 2L;
    public Long DEPOSIT_TRANS_STATUS_CANCEL = 3L;
    /*
    //Trang thai mat hang SIM so
    //LamNV, 14/11/2009
     */
    //1:Dang trong kho, 2: da su dung,  3: Da xuat kho dang cho confirm nhap, 
    //4: Da dau KIT;5: huy;6:lock
    public Long STOCK_SIM_STATUS_IN_STOCK = 1L; //dang trong kho
    public Long STOCK_SIM_STATUS_USED = 2L; //da su dung
    public Long STOCK_SIM_STATUS_LOCK = 6L; //bi khoa
    public boolean ANYPAY_TRANS_INSERT_BCCS = true;//Co insert ban ghi tren BCCS hay khong?
    public Long ANYPAY_TRANS_DATE_DELETE = 30L;//So ngay gioi han cho phep huy giao dich
    public int maxRowsResultCombo = 10;//So ban ghi toi da trong autocomplete
//    TrongLV
    //Select nhieu loại giá
    public String SELECT_PRICE_TYPE_SHOP_RETAIL = "(1,9,11,13,14,15)";
    //Select nhieu loại giá cho ban KM
    public String SELECT_PRICE_TYPE_SHOP_PROMOTION = "(3,7)";
    //loai dich vu (truong sale_type trong bang sale_services)
    public static Long SALE_SERVICES_TYPE_SERVICE = 1L; //loai dich vu phuc vu dau noi CM
    public static Long SALE_SERVICES_TYPE_PACKAGE = 2L; //goi hang IM ban
    //chieu dai toi da cua isdn, serial, trong truong hop nhap du lieu tu file
    public static Long MAX_LENGTH_ISDN = 12L;
    public static Long MAX_LENGTH_SERIAL = 20L;
    public static int HOUR_WATING = -12;
    public static int MUNITE_WATING = -5; //tinh theo phut
    public static Long TIME_WAIT_COL_RESPONE = 3600000L; //thoi gian doi message confirm cua CTV tinh theo milisecond
    public Long REPORT_DEPOSIT_SET = 0L;
    public Long REPORT_DEPOSIT_GET = 1L;
    public Long REPORT_DEPOSIT_STAFF = 2L;
    public Long REPORT_DEPOSIT_GENERAL = 3L;
    //* KHAI BAO TRANG THAI CUA TAI KHOAN SIMTK, LOAI TK SIMTK, TRANGG THAI CUA TUNG LOAI TK SIMTK *//
    public Long BALANCE_TYPE_EPAY = 2L;
    public Long BALANCE_TYPE_ANYPAY = 1L;
    public Long BALANCE_STATUS_INACTIVE = 0L;
    public Long BALANCE_STATUS_ACTIVE = 1L;
    public Long BALANCE_STATUS_SUSPENDED = 2L;
    public Long BALANCE_STATUS_DESTROY = 3L;
    public Long ACCOUNT_AGENT_STATUS_ACTIVE = 1L;
    public Long ACCOUNT_AGENT_STATUS_SUSPENDED = 0L;
    public Long ACCOUNT_AGENT_STATUS_DESTROY = 2L;
    public Long ACCOUNT_ANYPAY_STATUS_ACTIVE = 1L;
    public Long ACCOUNT_ANYPAY_STATUS_SUSPENDED = 0L;
    public Long ACCOUNT_ANYPAY_STATUS_DESTROY = 9L;
    public Long ACCOUNT_STATUS_INACTIVE = 0L;
    public Long ACCOUNT_STATUS_ACTIVE = 1L;
    public Long ACCOUNT_STATUS_LOCKED = 2L;
    //* KHAI BAO TRANG THAI CUA TAI KHOAN SIMTK, LOAI TK SIMTK, TRANGG THAI CUA TUNG LOAI TK SIMTK *//
    public Long CHECK_INVOICE_AGENT = 98L;
    public Long HEAD_SHOP_TYPE = 99L;
    public String CARD_TYPE_INTERNATIONAL = "2";
    public Long TELECOM_SERVICE_ANYPAY = 1L;
    public String ERROR_UPDATE_PASS = "ORA-01400: cannot insert NULL into (\"EVOUCHER_OWNER\".\"TRANS_LOG\".\"USER_ID\")";
    //Log Type
    public String ACTION_CHANGE_SHOP = "ACTION_CHANGE_SHOP";
    public String ACTION_CHANGE_MANAGEMENT = "ACTION_CHANGE_MANAGEMENT";
    public String ACTION_CHANGE_SHOP_CTVDB = "ACTION_CHANGE_SHOP_CTVDB";
    public String ACTION_CHANGE_PARENT_SHOP = "ACTION_CHANGE_PARENT_SHOP";
    public String ACTION_CHANGE_INFO_STAFF = "ACTION_CHANGE_INFO_STAFF";
    public String ACTION_CHANGE_INFO_STAFF_AP = "ACTION_CHANGE_INFO_STAFF_AP";
    /**DUCTM_THÊM MỚI ĐƠN LẺ*/
    public String ACTION_ADD_COLLABORATOR = "ACTION_ADD_COLLABORATOR";
    public String ACTION_ADD_STAFF_BY_FILE = "ACTION_ADD_STAFF_BY_FILE";
    public String ACTION_ADD_STAFF_CTV_DB_BY_FILE = "ACTION_ADD_STAFF_CTV_DB_BY_FILE";
    public String ACTION_ADD_SHOP_BY_FILE = "ACTION_ADD_SHOP_BY_FILE";
    //haint41 23/11/2011 : action add đại lý theo file
    public String ACTION_ADD_AGENT_BY_FILE = "ACTION_ADD_AGENT_BY_FILE";
    //haint41 23/11/2011 : action cap nhat thong tin dai ly
    public String ACTION_CHANGE_INFO_AGENT = "ACTION_CHANGE_INFO_AGENT";
    public String ACTION_OFF_STAFF = "ACTION_OFF_STAFF";
    public String ACTION_ON_STAFF = "ACTION_ON_STAFF";
    public String ACTION_ON_STAFF_BY_FILE = "ACTION_ON_STAFF_BY_FILE";
    public String ACTION_OFF_STAFF_BY_FILE = "ACTION_OFF_STAFF_BY_FILE";
    public String ACTION_OFF_SHOP_BY_FILE = "ACTION_OFF_SHOP_BY_FILE";
    // ANHTT thuc hien ghi log cho Danh muc loai kenh :
    public String ACTION_EDIT_TYPE_CHANNEL = "EDIT_TYPE_CHANNEL";
    public String ACTION_ADD_TYPE_CHANNEL = "ADD_TYPE_CHANNEL";
    public String ACTION_DEL_TYPE_CHANNEL = "DEL_TYPE_CHANNEL";
    public String ACTION_COPY_TYPE_CHANNEL = "COPY_TYPE_CHANNEL";
    // ANHTT thuc hien ghi log cho danh muc kenh :
    public String ACTION_ADD_STAFF = "ADD_STAFF";
    public String ACTION_EDIT_STAFF = "EDIT_STAFF";
    public String ACTION_DEL_STAFF = "DEL_STAFF";
    public String ACTION_COPY_STAFF = "COPY_STAFF";
    public String ACTION_ADD_CTV = "ADD_CTV";
    public String ACTION_EDIT_CTV = "EDIT_CTV";
    public String ACTION_DEL_CTV = "DEL_CTV";
    public String ACTION_ADD_CHANNEL = "ADD_CHANNEL";
    public String ACTION_EDIT_CHANNEL = "EDIT_CHANNEL";
    public String ACTION_DEL_CHANNEL = "DEL_CHANNEL";
    //action Partner
    public String ACTION_ADD_PARTNER = "ADD_PARTNER";
    public String ACTION_UPDATE_PARTNER = "UPDATE_PARTNER";
    public String ACTION_DELETE_PARTNER = "DELETE_PARTNER";
    public String ACTION_COPY_PARTNER = "COPY_PARTNER";
    //action bank_account
    public String ACTION_ADD_BANKACCOUNT = "ADD_BANK_ACCOUNT";
    public String ACTION_UPDATE_BANKACCOUNT = "UPDATE_BANK_ACCOUNT";
    public String ACTION_DELETE_BANKACCOUNT = "DELETE_BANK_ACCOUNT";
    public String ACTION_COPY_BANKACCOUNT = "COPY_BANK_ACCOUNT";
    //action book_type
    public String ACTION_ADD_BOOKTYPE = "ADD_BOOK_TYPE";
    public String ACTION_UPDATE_BOOKTYPE = "UPDATE_BOOK_TYPE";
    public String ACTION_DELETE_BOOKTYPE = "DELETE_BOOK_TYPE";
    public String ACTION_COPY_BOOKTYPE = "COPY_BOOK_TYPE";
    //action printer_param
    public String ACTION_ADD_PRINTERPARAM = "ADD_PRINTER_PARAM";
    public String ACTION_UPDATE_PRINTERPARAM = "UPDATE_PRINTER_PARAM";
    public String ACTION_DELETE_PRINTERPARAM = "DELETE_PRINTER_PARAM";
    //action group_flter_rule
    public String ACTION_ADD_GROUPFILTERRULE = "ADD_GROUP_FILTER_RULE";
    public String ACTION_UPDATE_GROUPFILTERRULE = "UPDATE_GROUP_FILTER_RULE";
    public String ACTION_DELETE_GROUPFILTERRULE = "DELETE_GROUP_FILTER_RULE";
    public String ACTION_COPY_GROUPFILTERRULE = "COPY_GROUP_FILTER_RULE";
    //action filter_type
    public String ACTION_ADD_FILTERTYPE = "ADD_FILTER_TYPE";
    public String ACTION_UPDATE_FILTERTYPE = "UPDATE_FILTER_TYPE";
    public String ACTION_DELETE_FILTERTYPE = "DELETE_FILTER_TYPE";
    public String ACTION_COPY_FILTERTYPE = "COPY_FILTER_TYPE";
    //action isdn_filter_type_rules
    public String ACTION_ADD_ISDNFILTERRULES = "ADD_ISDN_FILTER_RULES";
    public String ACTION_UPDATE_ISDNFILTERRULES = "UPDATE_ISDN_FILTER_RULES";
    public String ACTION_DELETE_ISDNFILTERRULES = "DELETE_ISDN_FILTER_RULES";
    public String ACTION_COPY_ISDNFILTERRULES = "COPY_ISDN_FILTER_RULES";
//    PricePolicyDAO
    public String ACTION_SAVE_PRICE_POLICY = "SAVE_PRICE_POLICY";
    public String ACTION_EDIT_PRICE_POLICY = "EDIT_PRICE_POLICY";
    public String ACTION_DELETE_PRICE_POLICY = "DELETE_PRICE_POLICY";
    public String ACTION_COPY_PRICE_POLICY = "COPY_PRICE_POLICY";
//    DiscountGroupDAO
    public String ACTION_SAVE_DISCOUNT_GROUP = "ACTION_SAVE_DISCOUNT_GROUP";
    public String ACTION_UPDATE_DISCOUNT_GROUP = "ACTION_UPDATE_DISCOUNT_GROUP";
    public String ACTION_DELETE_DISCOUNT_GROUP = "ACTION_DELETE_DISCOUNT_GROUP";
    public String ACTION_SAVE_DISCOUNT = "ACTION_SAVE_DISCOUNT";
    public String ACTION_UPDATE_DISCOUNT = "ACTION_UPDATE_DISCOUNT";
    public String ACTION_DELETE_DISCOUNT = "ACTION_DELETE_DISCOUNT";
    public String ACTION_DELETE_DISCOUNT_MODEL_MAP = "ACTION_DELETE_DISCOUNT_MODEL_MAP";
    public String ACTION_DELETE_DISCOUNT_CHANNEL_MAP = "ACTION_DELETE_DISCOUNT_CHANNEL_MAP";
    public String ACTION_SAVE_DISCOUNT_MODEL_MAP = "ACTION_SAVE_DISCOUNT_MODEL_MAP";
    public String ACTION_UPDATE_DISCOUNT_MODEL_MAP = "ACTION_UPDATE_DISCOUNT_MODEL_MAP";
    public String ACTION_SAVE_DISCOUNT_CHANNEL_MAP = "ACTION_SAVE_DISCOUNT_CHANNEL_MAP";
    public String ACTION_UPDATE_SHOP_POLICY = "ACTION_UPDATE_SHOP_POLICY";
    public String ACTION_UPDATE_STAFF_POLICY = "ACTION_UPDATE_STAFF_POLICY";
//    PRINTER_USER_DAO
    public String ACTION_SAVE_PRINTER_USER = "ACTION_SAVE_PRINTER_USER";
    public String ACTION_UPDATE_PRINTER_USER = "ACTION_UPDATE_PRINTER_USER";
    public String ACTION_DELETE_PRINTER_USER = "ACTION_DELETE_PRINTER_USER";
    //
    //reason_group
    public String ACTION_ADD_REASON_GROUP = "ADD_REASON_GROUP";
    public String ACTION_UPDATE_REASON_GROUP = "UPDATE_REASON_GROUP";
    public String ACTION_DELETE_REASON_GROUP = "DELETE_REASON_GROUP";
    public String ACTION_COPY_REASON_GROUP = "COPY_REASON_GROUP";
    //reason
    public String ACTION_ADD_REASON = "ADD_REASON";
    public String ACTION_UPDATE_REASON = "UPDATE_REASON";
    public String ACTION_DELETE_REASON = "DELETE_REASON";
    public String ACTION_COPY_REASON = "COPY_REASON";
    // fine_items_price
    public String ACTION_ADD_FINE_ITEM_PRICE = "ADD_FINE_ITEM_PRICE";
    public String ACTION_UPDATE_FINE_ITEM_PRICE = "UPDATE_FINE_ITEM_PRICE";
    public String ACTION_DELETE_FINE_ITEM_PRICE = "DELETE_FINE_ITEM_PRICE";
    public String ACTION_COPY_FINE_ITEM_PRICE = "COPY_FINE_ITEM_PRICE";
    // fine_items
    public String ACTION_ADD_FINE_ITEM = "ADD_FINE_ITEM";
    public String ACTION_UPDATE_FINE_ITEM = "UPDATE_FINE_ITEM";
    public String ACTION_DELETE_FINE_ITEM = "DELETE_FINE_ITEM";
    public String ACTION_COPY_FINE_ITEM = "COPY_FINE_ITEM";
    //Assign isdn to exchange
    public String ACTION_ADD_PSTN_EXCHANGE = "ADD_PSTN_EXCHANGE";
    public String ACTION_EDIT_PSTN_EXCHANGE = "EDIT_PSTN_EXCHANGE";
    public String ACTION_DELETE_PSTN_EXCHANGE = "DELETE_PSTN_EXCHANGE";
    public static final String ACTION_ADD_DISCOUNT_POLICY = "ADD_DISCOUNT_POLICY";
    public static final String ACTION_UPDATE_DISCOUNT_POLICY = "UPDATE_DISCOUNT_POLICY";
    public static final String ACTION_DELETE_DISCOUNT_POLICY = "DELETE_DISCOUNT_POLICY";
    public static final String DISCOUNT_POLICY_DEFAULT = "1";
    public static final String PRICE_POLICY_DEFAULT = "0";

    /* LamNV ADD START */
    //define good
    public static final String ACTION_DEFINE_GOOD = "DEF_GOOD";
    public static final String ACTION_UPDATE_GOOD = "UPDATE_GOOD";
    public static final String ACTION_DELETE_GOOD = "DELETE_GOOD";
    //
    public static final String ACTION_ADD_GOOD_DEPOSIT = "ADD_GOOD_DEPOSIT";
    public static final String ACTION_UPDATE_GOOD_DEPOSIT = "UPDATE_GOOD_DEPOSIT";
    public static final String ACTION_DELETE_GOOD_DEPOSIT = "DELETE_GOOD_DEPOSIT";
    //
    public static final String ACTION_ADD_GOOD_PRICE = "ADD_GOOD_PRICE";
    public static final String ACTION_UPDATE_GOOD_PRICE = "UPDATE_GOOD_PRICE";
    public static final String ACTION_DELETE_GOOD_PRICE = "DELETE_GOOD_PRICE";
    //sale service
    public static final String ACTION_ADD_SALE_SERVICE = "ADD_SALE_SERVICE";
    public static final String ACTION_UPDATE_SALE_SERVICE = "UPDATE_SALE_SERVICE";
    public static final String ACTION_DELETE_SALE_SERVICE = "DELETE_SALE_SERVICE";
    public static final String ACTION_ADD_SALE_SERVICE_PRICE = "ADD_SALE_SERVICE_PRICE";
    public static final String ACTION_UPDATE_SALE_SERVICE_PRICE = "UPDATE_SALE_SERVICE_PRICE";
    public static final String ACTION_DELETE_SALE_SERVICE_PRICE = "DELETE_SALE_SERVICE_PRICE";
    public static final String ACTION_ADD_SALE_SERVICE_MODEL = "ADD_SALE_SERVICE_MODEL";
    public static final String ACTION_UPDATE_SALE_SERVICE_MODEL = "UPDATE_SALE_SERVICE_MODEL";
    public static final String ACTION_DELETE_SALE_SERVICE_MODEL = "DELETE_SALE_SERVICE_MODEL";
    public static final String ACTION_ADD_SALE_SERVICE_STOCK = "ACTION_ADD_SALE_SERVICE_STOCK";
    public static final String ACTION_UPDATE_SALE_SERVICE_STOCK = "ACTION_UPDATE_SALE_SERVICE_STOCK";
    /* LamNV ADD STOP */
    public static final String ACTION_ADD_SALE_SERVICE_DETAIL = "ADD_SALE_SERVICE_DETAIL";
    public static final String ACTION_UPDATE_SALE_SERVICE_DETAIL = "UPDATE_SALE_SERVICE_DETAIL";
    public static final String ACTION_DELETE_SALE_SERVICE_DETAIL = "DELETE_SALE_SERVICE_DETAIL";
    /* LamNV ADD START */
    //sale service
    public static final String ACTION_ADD_PCK_GOOD = "ADD_PCK_GOOD";
    public static final String ACTION_UPDATE_PCK_GOOD = "UPDATE_PCK_GOOD";
    public static final String ACTION_DELETE_PCK_GOOD = "DELETE_PCK_GOOD";
    //good profile
    public static final String ACTION_ADD_GOOD_PROFILE = "ADD_GOOD_PROFILE";
    public static final String ACTION_UPDATE_GOOD_PROFILE = "UPDATE_GOOD_PROFILE";
    public static final String ACTION_DEL_GOOD_PROFILE = "DEL_GOOD_PROFILE";
    //cap nhat mat hang moi
    public static final String ACTION_ASSIGN_NEW_STOCK_MODEL = "ASSIGN_NEW_STOCK_MODEL";
    //action account
    public String ACTION_ACTIVE_ACCOUNT_AGENT = "ACTIVE_ACCOUNT_AGENT";
    public String ACTION_INACTIVE_ACCOUNT_AGENT = "IN-ACTIVE_ACCOUNT_AGENT";
    public String ACTION_REACTIVE_ACCOUNT_AGENT = "RE-ACTIVE_ACCOUNT_AGENT";
    public String ACTION_UPDATE_ACCOUNT_AGENT = "UPDATE_ACCOUNT_AGENT";
    public String ACTION_UNLOCK_ACCOUNT_AGENT = "UNLOCK_ACCOUNT_AGENT";
    public String ACTION_ACTIVE_ACCOUNT_BALANCE = "ACTIVE_ACCOUNT_BALANCE";
    public String ACTION_INACTIVE_ACCOUNT_BALANCE = "IN-ACTIVE_ACCOUNT_BALANCE";
    public String ACTION_REACTIVE_ACCOUNT_BALANCE = "RE-ACTIVE_ACCOUNT_BALANCE";
    public String ACTION_UPDATE_ACCOUNT_BALANCE = "UPDATE_ACCOUNT_BALANCE";
    public String ACTION_ACTIVE_ACCOUNT_ANYPAY = "ACTION_ACTIVE_ACCOUNT_ANYPAY";
    public String ACTION_INACTIVE_ACCOUNT_ANYPAY = "IN-ACTIVE_ACCOUNT_ANYPAY";
    public String ACTION_REACTIVE_ACCOUNT_ANYPAY = "RE-ACTIVE_ACCOUNT_ANYPAY";
    public String ACTION_UPDATE_ACCOUNT_ANYPAY = "ACTION_UPDATE_ACCOUNT_ANYPAY";
    public String ACTION_ACTIVE_ACCOUNT_PAYMENT = "ACTION_ACTIVE_ACCOUNT_PAYMENT";
    public String ACTION_INACTIVE_ACCOUNT_PAYMENT = "IN-ACTIVE_ACCOUNT_PAYMENT";
    public String ACTION_REACTIVE_ACCOUNT_PAYMENT = "RE-ACTIVE_ACCOUNT_PAYMENT";
    public String ACTION_UPDATE_ACCOUNT_PAYMENT = "ACTION_UPDATE_ACCOUNT_PAYMENT";
    public String ACTION_CHANGE_STATUS_ACCOUNT_AGENT = "ACTION_CHANGE_STATUS_ACCOUNT_AGENT";
    public String ACTION_CHANGE_PASS_ACCOUNT_AGENT = "ACTION_CHANGE_PASS_ACCOUNT_AGENT";
    public String ACTION_CHANGE_INFO_ACCOUNT_AGENT = "ACTION_CHANGE_INFO_ACCOUNT_AGENT";
    public String ACTION_CHANGE_REPAIR_ACCOUNT_AGENT = "ACTION_CHANGE_REPAIR_ACCOUNT_AGENT";
    public String ACTION_DESTROY_ACCOUNT_AGENT = "ACTION_DESTROY_ACCOUNT_AGENT";
    public String ACTION_LOCK_ACCOUNT_AGENT = "ACTION_LOCK_ACCOUNT_AGENT";
    public String ACTION_CREATE_ACCOUNT_BALANCE = "ACTION_CREATE_ACCOUNT_BALANCE";
    public String ACTION_DESTROY_ACCOUNT_BALANCE = "ACTION_DESTROY_ACCOUNT_BALANCE";
    public String ACTION_CHANGE_STATUS_ACCOUNT_BALANCE = "ACTION_CHANGE_STATUS_ACCOUNT_BALANCE";
    public String ACTION_CREATE_ACCOUNT_ANYPAY = "ACTION_CREATE_ACCOUNT_ANYPAY";
    public String ACTION_CHANGE_STATUS_ACCOUNT_ANYPAY = "ACTION_CHANGE_STATUS_ACCOUNT_ANYPAY";
    public String ACTION_DESTROY_ACCOUNT_ANYPAY = "ACTION_DESTROY_ACCOUNT_ANYPAY";
    public String ACTION_TYPE = "ACTION_LOG";
    public String ACTION_LOG_STAFF = "ACTION_LOG_STAFF";
    //Huy giao dich nap chuyen tien
    public String ACTION_CANCEL_ANYPAY_TRANS = "ACTION_CANCEL_ANYPAY_TRANS";

    /* LamNV ADD STOP */
    public Long STOCK_TYPE_CARD = 6L;
    public Long REQUEST_TYPE_SALE_RETAIL = 1L;
    public Long REQUEST_TYPE_SALE_AGENTS = 4L;
    public Long REQUEST_TYPE_SALE_CTV = 3L;
    public Long REQUEST_TYPE_SALE_CTV_REPLACE = 6L;
    public Long REQUEST_TYPE_SALE_PROMOTION = 5L;
    public Long REQUEST_TYPE_CHANGE_GOODS = 2L;
    public Long REQUEST_TYPE_SALE_INTERNAL = 7L;
    public String TKTT_WITHDRAW_TIMEOUT = "TKTT_WITHDRAW_TIMEOUT";
    public String REAL_DEBIT_MAX_TYPE = "REAL_DEBIT_MAX";
// Can kho hang - TheTM
    public static final String ACTION_BALANCE_STOCK_TOTAL = "BALANCE_STOCK_TOTAL";
    public String TRANS_CODE_PREFIX = "GD0000"; // Ma giao dich ban hang (default: GD)
    public String TRANS_ID_LENGTH = "9"; // Do dai ma giao dich ban hang (default: 13)
    //Trans_code = TRANS_CODE_PREFIX + Id (len = 9)
    public String UN_LOCK_TYPE = "UNLOCK_ACCOUNT"; // Do dai ma giao dich ban hang (default: 13)
    public Long STATUS_ANYPAY_FPT_DESTROY = 9L; //Trang thai tk da huy
    public String HLR_STATUS_DEFAULT = "1";
    public String HLR_STATUS_REG = "2";
    public String AUC_STATUS_DEFAULT = "0";
    public String AUC_STATUS_REG = "1";
    //TRANG THAI PORT
    public Long PORT_STATUS_NOT_USE = 0L;
    public Long PORT_STATUS_FREE = 1L;
    public Long PORT_STATUS_USE = 2L;
    public Long PORT_STATUS_LOCK = 3L;
    public Long PORT_STATUS_LEASEDLINE = 4L;
    //TRANG THAI BRAS IP POOL
    public Long BRAS_IPPOOL_STATUS_NOT_USE = 0L;
    public Long BRAS_IPPOOL_STATUS_FREE = 1L;
    public Long BRAS_IPPOOL_STATUS_USE = 2L;
    public Long BRAS_IPPOOL_STATUS_LOCK = 3L;
    //TRANG THAI SLOT
    public Long SLOT_STATUS_NOT_USE = 0L;
    public Long SLOT_STATUS_FREE = 1L;
    public Long SLOT_STATUS_USE = 2L;
    public Long SLOT_STATUS_LOCK = 3L;
    //LOAI THIET BI
    public String EQUIPMENT_TYPE_CHASSIC = "0";
    public String EQUIPMENT_TYPE_CARD = "1";
    public String EQUIPMENT_TYPE_BRAS = "2";
    public String EQUIPMENT_TYPE_SWITCHES = "3";
    public String EQUIPMENT_TYPE_ROUTER = "4";
    public String APP_PARAMS_EQUIPMENT_TYPE = "EQUIPMENT_TYPE";
    public String APP_PARAMS_CARD_TYPE = "CARD_TYPE";
    public String APP_PARAMS_EXCHANGE_TYPE = "EXCHANGE_TYPE";
    public String APP_PARAMS_BRAS_IPPOOL_STATUS = "BRAS_IPPOOL_STATUS";
    public String APP_PARAMS_PORT_STATUS = "PORT_STATUS";
    public String APP_PARAMS_SLOT_STATUS = "SLOT_STATUS";
    public String APP_PARAMS_SLOT_TYPE = "CARD_TYPE";//SLOT_TYPE
    public String APP_PARAMS_PORT_FORMAT_TYPE = "PORT_FORMAT_TYPE";
    public Long PRODUCT_ID_DSLAM = 1L;
    public Long PRODUCT_ID_DLU = 6L;
    public int MAX_DATE_FIND = 45;
    public String LAST_UPDATE_KEY_WEB = "Normal";
    public int MAX_RESULT_PSTN = 1000;
    //dinh nghia cac kieu file xuat bao cao
    public Long REPORT_FILE_TYPE_EXCEL = 1L; //kieu file excel
    public Long REPORT_FILE_TYPE_TEXT = 2L; //kieu file text
    public Long STAFFF_LOCKED = 1L;
    public Long STAFFF_UNLOCK = 0L;
    public Long STATUS_DEBIT_DEPOSIT = 1L;
    public Long STATUS_SALE_DEBIT = 1L;
    public String APP_PARAMS_CHECK_DEBIT_TOTAL = "APP_PARAMS_CHECK_DEBIT_TOTAL"; //check tang giam han muc dat coc tien
    public String APP_PARAMS_CHECK_DEBIT = "APP_PARAMS_CHECK_DEBIT"; //check tang giam han muc dat coc tien
    public String ACTION_ASSIGN_DEBIT_BY_PAGE = "ACTION_ASSIGN_DEBIT_BY_PAGE";
    public String ACTION_ASSIGN_DEBIT_BY_FILE = "ACTION_ASSIGN_DEBIT_BY_FILE";
    public String ACTION_DELETE_DEBIT = "ACTION_DELETE_DEBIT";
    public String DSLAM_CABLE_TYPE = "1";
    public Long SALE_TYPE = 1L; //gd ban
    public Long SALE_TYPE_DESTROY = 2L; //gd huy
    //action BankReceiptExternal
    public String ACTION_ADD_BANK_RECEIPT_EXTERNAL = "ADD_BANK_RECEIPT_EXTERNAL";
    public String ACTION_UPDATE_BANK_RECEIPT_EXTERNAL = "UPDATE_BANK_RECEIPT_EXTERNAL";
    public String ACTION_DELETE_BANK_RECEIPT_EXTERNAL = "DELETE_BANK_RECEIPT_EXTERNAL";
    public String CHASSIC_TYPE_IP = "0";
    public String CHASSIC_TYPE_ATM = "1";
    public String CHASSIC_TYPE_ECI = "2";
    public String RECEIPT_EXPENSE_SERIAL_CODE_IN = "RR";
    public String RECEIPT_EXPENSE_SERIAL_CODE_OUT = "RP";
    public int BATCH_CARD_NUM = 50000;
    public static final int CONNECTION_TIMEOUT = 200000;
    //haint41 29/9/2011: loai hoa don
    public Long INVOICE_FOR_PAYMENT = 2L;  //Hoa don gach no
    public Long VOUCHER_FOR_PAYMENT = 8L;  //Bien lai gach no
    // tutm1 03/11/2011 loai mat hang 
    public Long STOCKMODEL_TRANSTYPE_DEPOSIT = 1L; // dat coc
    public Long STOCKMODEL_TRANSTYPE_SALE = 2L; // ban dut
    public Long IDNO_MIN_LEN = 1L; // do dai ngan nhat cua idNo
    public Long IDNO_MAX_LEN = 25L; // do dai lon nhat cua idNo
    //haint41 29/10/2011
    public Long CABLE_BOX_STATUS_ACTIVE = 1L;
    public Long CABLE_BOX_STATUS_INACTIVE = 0L;
    public Long BOARDS_STATUS_ACTIVE = 1L;
    public String APP_PARAMS_STATUS_ACTIVE = "1";
    public Long PORT_OUTSIDE_STATUS_FREE = 1L;
    public Long PORT_OUTSIDE_STATUS_USING = 2L;
    public Long PORT_OUTSIDE_STATUS_LOCK = 3L;
    public Long PORT_INSIDE_STATUS_USING = 2L;
    public Long PORT_INSIDE_STATUS_LOCK = 3L;
    //haint41 8/11/2011 : phan quyen cong anypay cho don vi
    public static final String ADD_SINGLE_ANYPAY_SHOP = "addSingleAnyPayShop";
    //haint41 8/11/2011 : phan quyen cong anypay cho NVQL
    public static final String ADD_SINGLE_ANYPAY_STAFF = "addSingleAnyPayStaff";
    public String TRANS_TYPE_ANYPAY = "KMAN";
    //haint41 25/11/2011 Them cac truong cho shop trong bang app_params
    public final String APP_PARAMS_TYPE_SHOP_STATUS = "SHOP_STATUS";
    public final String APP_PARAMS_TYPE_SHOP_CHECK_VAT = "SHOP_CHECK_VAT";
    public final String APP_PARAMS_TYPE_SHOP_AGENT_TYPE = "SHOP_AGENT_TYPE";
    public static final String ACTION_ADD_EXCHANGE_RATE_CURRENCY = "ADD_EXCHANGE_RATE_CURRENCY";
    public static final String ACTION_UPDATE_EXCHANGE_RATE_CURRENCY = "UPDATE_EXCHANGE_RATE_CURRENCY";
    public static final String ACTION_DELETE_EXCHANGE_RATE_CURRENCY = "DELETE_EXCHANGE_RATE_CURRENCY";
    //haint41 11/02/2012
    public static final String CHANNEL_STATUS = "CHANNEL_STATUS";
    public static final String CHANNEL_STATUS_CANCEL = "CHANNEL_STATUS_CANCEL";
    public static final String CHANNEL_STATUS_NOT_CANCEL = "CHANNEL_STATUS_NOT_CANCEL";
    public static final Long ACTIVE_STATUS_CARD_NOT_ACTIVE = 0L;
    public static final Long ACTIVE_STATUS_CARD_ACTIVING = 1L;
    public static final Long ACTIVE_STATUS_CARD_ACTIVE_SUCCESS = 2L;
    public static final Long ACTIVE_STATUS_CARD_ACTIVE_FAIL = 3L;
    public static final Long ACTIVE_STATUS_CARD_DEACTIVING = 5L;//4L;--cho thanh cong luon
    public static final Long ACTIVE_STATUS_CARD_DEACTIVE_SUCCESS = 5L;
    public static final Long ACTIVE_STATUS_CARD_DEACTIVE_FAIL = 6L;
    public static final Long ACTIVE_TYPE_CARD_SALE = 1L;
    public static final Long ACTIVE_TYPE_CARD_DESTROY = 2L;
    public static final String ACTION_ADD_PRICE_POLICY = "ACTION_ADD_PRICE_POLICY";
    public static final String ACTION_UPDATE_PRICE_POLICY = "ACTION_UPDATE_PRICE_POLICY";
    public static final Long PURPOSE_INPUT_SERIAL_SALE_TO_RETAIL = 1L;//ban le : dung list khac de luu ds serial
    public static final Long PURPOSE_INPUT_SERIAL_SALE_TO_PUNISH_STAFF = 4L;//ban phạt nhân viên : trung dh3
    public static final Long PURPOSE_INPUT_SERIAL_SALE_TO_PROMOTION = 2L;//ban khuyen mai : khong check hang hoa theo kenh
    public static final Long PURPOSE_INPUT_SERIAL_SALE = 3L;//ban hang : nhap serial de ban hang binh thuong 
    public static final Long PURPOSE_INPUT_SERIAL_EXP_TO_PARTNER = 4L;//nhap serial de xuat tra doi tac
    public static final Long PURPOSE_INPUT_SERIAL_EXP_TO_STOCK = 5L;// nhap serial de xuat kho cho kho khac
    public static final Long PURPOSE_INPUT_SERIAL_EXP_TO_STOCK_TO_CHANNEL = 6L;// nhap serial de xuat cho kenh
	
	    // tutm1 21/08/2012 cac trang thai tren tong dai cua the cao
    public static String CARD_STATUS_ACTIVED = "0"; //Actived, nhưng chưa được nạp
    public static String CARD_STATUS_RECHARGED = "1"; //Recharged, thẻ đã được sử dụng
    public static String CARD_STATUS_GENERATE = "3"; //Used (Thẻ đã được sử dụng) 
    public static String CARD_STATUS_LOCK = "4"; //Lock, thẻ bị khóa
    public static String CARD_STATUS_ISSUE = "5"; //Issue, đã được tạo trên VC chuẩn bị kích hoạt
    public static String CARD_STATUS_LOCK_PER = "6"; //Lock permanently, khóa vĩnh viễn không mở được
    public static int MAX_REQUEST_VIEW_CARD = 1000; //so luong toi da the cao co the xem thong tin trong 1 thoi diem.
    
    
        
    //Hoangpm3 5/9/2012
    public static final String ADD = "ADD";
    public static final String EDIT = "EDIT";
    public static final String DELETE = "DELETE";
    
    public static final String  DEFINE_GOODS= "27";
    public static final String  DEFINE_PROFILE_OF_GOODS= "28";
    public static final String  DEFINE_DISCOUNT= "29";
    public static final String  DEFINE_SERVICE_OF_SALE= "30";
    public static final String  FILTER_SPECIAL_NUMBER= "32";
    public static final String  ASSIGN_TYPE_AND_STATUS_FOR_NUMBER= "33";
    public static final String  DISTRIBUT_NUMBER= "34";
    public static final String  CATALOGUE_TYPE_OF_CHANNEL= "36";
    public static final String  CATALOGUE_OF_CHANNEL= "37";
    public static final String  SWITCH_STAFF_CHANNEL= "38";
    public static final String  OFF_STAFF_CODE= "39";
    public static final String  ON_STAFF_CODE= "40";
    public static final String  SWITCH_MANAGEMENT_CHANNEL= "41";
    public static final String  SWITCH_SHOP_FOR_D2D_AGENT= "42";
    public static final String  POLICY_PRICE_DISCOUNT= "44";
    public static final String  INFRASTRUCTURE_MANAGEMENT= "45";
    
    //trung dh3 R499 start
    public static Long STOCK_EXP_NOTE = 1L;
    public static Long STOCK_IMP_NOTE = 2L;
    public static Long STOCK_TRANS_TYPE_PARTNER = 1L;
    public static Long STOCK_TRANS_TYPE_EXP_CMD = 2L;
    public static Long STOCK_TRANS_TYPE_EXP_STICK = 3L;
    public static Long STOCK_TRANS_TYPE_EXP_STAFF = 4L;
    public static Long STOCK_TRANS_TYPE_STAFF_EXP = 5L;
    public static Long STOCK_TRANS_TYPE_DAMAGE = 6L;
    public static Long STOCK_TRANS_TYPE_EXP_MASTER_AGENT = 9L;

    public String SMTP_EMAIL="smtp.viettel.com.vn";
    public String SMTP_EMAIL_SERVER="trungdh3@viettel.com.vn";
    public String SMTP_EMAIL_PSW="huutrung.no1";
    //trung dh3 R499 end
    //TruongNQ5 20140725 R6237
    public static String SCHEMA_RVN_SEVICE = "SCHEMA_RVN_SEVICE";//schema cho bang rvn_service va bang rvn_service_dept_map 
    public static String SOURCE_IM = "IM";//Truong source trong bang rvn_service
    public static Long DEPT_ID_VTT = 1L;//-- 6 STL -- 3 NAT -- 5 VTC -- 1 VTT -- 2 MOV -- 4 VTL -- 7 Peru
    public static Long DEPT_ID_MOV = 2L;//-- 6 STL -- 3 NAT -- 5 VTC -- 1 VTT -- 2 MOV -- 4 VTL -- 7 Peru
    public static Long TYPE_GOODS = 1L;//--1 loai mat hang | 2 loai dich vu
    public static Long TYPE_SERVICES = 2L;//--1 loai mat hang | 2 loai dich vu
    public static Long TYPE_REVENUE = 1L;//--1 loai chi tieu doanh thu | 2 loai chi tieu san luong
    public static Long TYPE_QUALITY = 2L;//--1 loai chi tieu doanh thu | 2 loai chi tieu san luong
    //TruongNQ5 20140712 R6037
    //Ly do nhap tu doi tac
    public String STOCK_IMP_PARTNER = "STOCK_IMP_PARTNER";
    public static String PARAM_TYPE_FINANCE_TYPE = "FINANCE_TYPE"; //muc han muc
    public static String PARAM_TYPE_DEBIT_TYPE = "DEBIT_TYPE"; //muc han muc
    public static String PARAM_TYPE_DEBIT_DATE_TYPE = "DEBIT_DAY_TYPE"; //loai han muc
    public static String ACTION_ADD_DEBIT_TYPE = "ADD_DEBIT_TYPE";
    public static String ACTION_UPDATE_DEBIT_TYPE = "UPDATE_DEBIT_TYPE";
    public static String ACTION_DELETE_DEBIT_TYPE = "DELETE_DEBIT_TYPE";
    public static String DEBIT_DAY_TYPE_TG = "1";
    public static String DEBIT_TYPE = "DEBIT_TYPE";
    public static String DEBIT_DAY_TYPE = "DEBIT_DAY_TYPE";
    public static String FINANCE_TYPE = "FINANCE_TYPE";
    public static Long FINANCE_TYPE_VAL = 2L;
    public static Long DEBIT_TYPE_VAL = 1L;
    public static Long REQ_TYPE_ADD = 1L;
    public static Long REQ_TYPE_UPDATE = 2L;
    public static String ACTION_ADD_DEBIT_DAY_TYPE = "ADD_DEBIT_DAY_TYPE";
    public static String ACTION_UPDATE_DEBIT_DAY_TYPE = "UPDATE_DEBIT_DAY_TYPE";
    public static String ACTION_DELETE_DEBIT_DAY_TYPE = "DELETE_DEBIT_DAY_TYPE";
    public static String UPLOAD_DIR = "./share/report_out/";
    public static Long STOCK_MODEL_ANYPAY = 202084L;
    public static String ADD_DISCOUNT_ANYPAY_STRING = "DISC";
    public static String CHECK_ANYPAY = "CHECK_ANYPAY";
//    tannh20180504 : ghi log khi check not accept limit cho staff
     public static String ACTION_NOT_ACCEPT_LIMIT_STAFF = "ACTION_NOT_ACCEPT_LIMIT_STAFF";
      public static String ACTION_ACCEPT_LIMIT_STAFF = "ACTION_ACCEPT_LIMIT_STAFF";
    
    //End TruongNQ5 20140712 R6037
    public static final String BRANCH_TYPE = "2";
    public static final String APPS_NAME = "";
    public static final Long SHOP_NOT_CHECK_DEBIT_ID = Long.valueOf(7282L);
    public static final String PRICE_UNIT_DEBIT = "MT";
    public static final Long STATE_ID = Long.valueOf(1L);
	// ThinDM R6762
    public static Long CHANNEL_TYPE_NVDBCD = 18L; //kenh NVDBCD
    public static Long CHANNEL_TYPE_CTV_KHDN = 49L; //id cua kenh CTV KHDN
    public static Long CHANNEL_TYPE_COLLABORATOR = 10L; //id cua kenh CTV
    public static Long CHANNEL_TYPE_BHLD = 15L; //kenh BHLD
    public static final Long SHOP_LEVEL_SHOWROOM = 5L; // SHOWROOM
    public String TRANS_CODE_LXS = "LXS";//Lenh xuat
    public String TRANS_CODE_PXS = "PXS";//Phieu xuat
    public String TRANS_CODE_LNS = "LNS";//Lenh nhap
    public String TRANS_CODE_PNS = "PNS";//Phieu nhap
    public static int LENGTH_OF_TRANS_CODE_NUMBER = 5;// chieu dai STT ma phieu/lenh
    public static Long VT_SHOP = 7282L;
    public static final Long SHOP_LEVEL_ROOT = 2L; // cap cao nhat
    public static final Long SHOP_LEVEL_BRANCH = 3L; // chi nhanh
    public static final Long SHOP_LEVEL_CENTER = 4L; // TTKD
    public static final String PREFIX_ROOT = "RO"; // cap cao nhat
    public static final String PREFIX_BRANCH = "BR"; // chi nhanh
    public static final String PREFIX_CENTER = "CE"; // TTKD
    public static final String PREFIX_SHOWROOM = "SH"; // SHOWROOM
    public static Long TRANS_EXPORT_ISDN = 6L; //Xuat so
    public static Long TRANS_RECOVER_ISDN = 7L; //Thu hoi so
    Long STOCK_TRANS_TYPE_NOTE = 2L;
    Long STOCK_TRANS_TYPE_COMMAND = 1L;
    Long STOCK_TRANS_TYPE_IMPORT = 2L;
    Long STOCK_TRANS_TYPE_EXPORT = 1L;
    //End ThinDM
    public static final String DB_PRIVILEGE = "PRIVILEGE"; //TOANCX
    //Minhtn7 R8452
    public static final String SUBCRIBER_ACT_STATUS_NORMAL     = "00";  //Bình thường
    public static final String SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_CUST = "10";  //Chan 1 chieu kh y/c
    public static final String SUBCRIBER_ACT_STATUS_BLOCK_1_WAY_BY_SYS = "01";  //Chan 1 chieu
    public static final String SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_CUST = "20";  //Chan 2 chieu kh y/c
    public static final String SUBCRIBER_ACT_STATUS_BLOCK_2_WAY_BY_SYS  = "02";  //Chan 2 chieu
    public static final String MULTI_FUNCTION_SIM  = "PAGALOGO";  //SDN
    public static final String SUBCRIBER_ACT_STATUS_NOT_ACTIVE_900  = "03";  // Chua active 900
    //End Minhtn7
    //longtq
    public static final String IMP_TYPE_UPDATE = "1";
    public static final String IMP_TYPE_INSERT = "2";
    //DINHDC ADD
    public String PRICE_POLICY_SHOWROOM = "1001222";
    public String PRICE_POLICY_BRANCH = "1001242";
    public String PRICE_POLICY_LIST_BRANCH = "1001262";
    public static String SALE_TRANS_PAYMETHOD_EMOLA = "99";
    public static Long CHANNEL_TYPE_ID_SA = 1000522L;
    public static Long CHANNEL_TYPE_ID_MA = 1000485L;
    
    //Giao tiep voi Provisioning
    public static final String PROV_FILE_CONFIG = "provisioning";
    /**
     * IP ket noi toi Provisioning
     */
    public static final String PROV_IP = "prov_ip";
    /**
     * Port ket noi toi Provisioning
     */
    public static final String PROV_PORT = "prov_port";
    /**
     *User ket noi toi Provisioning
     */
    public static final String PROV_USER = "prov_user";
    /**
     *Pass ket noi toi Provisioning
     */
    public static final String PROV_PASS = "prov_pass";
    /**
     *Syn trong ket noi Provisioning
     */
    public static final String PROV_SYN = "prov_syn";
    /**
     *Message Type: tham so trong ket noi Provisioning
     *
     */
    public static final String PROV_MESSAGE_TYPE = "prov_message_type";
    /**
     * Client Id: Id ben goi Provisioning
     */
    public static final String PROV_CLIENT_ID = "prov_client_id";
   
    public static final String TIME_OUT_REQUEST_VIEW_CARD = "time_out_request_view_card";
    /**Time out cho channel view card*/
    public static final String TIME_OUT_CHANNEL_VIEW_CARD = "time_out_channel_view_card";
    
    //SEQUENCE : dung trong xem thong tin the cao
    public static final String PROV_PARAM_SEQUENCE_NAME = "SEQUENCE";
    public static String SEQUENCE = "SEQUENCE";
    public static final String PROV_PROCESS_CODE_VIEW_CARD = "prov_process_code_view_card";
}