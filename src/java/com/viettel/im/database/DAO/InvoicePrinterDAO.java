package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.lowagie.text.DocumentException;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.PrinterParam;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfTemplate;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.BookType;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import com.viettel.im.client.bean.InvoiceNoSaleItem;
import com.viettel.im.client.bean.SaleTransBean;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.PrinterUser;
import com.viettel.im.database.BO.UserToken;

/**
 * A data access object (DAO) providing persistence and search support for
 * InvoiceDestroyed entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.InvoiceDestroyed
 * @author MyEclipse Persistence Tools
 */
// return value = 2 : không tồn tại cấu hình cho dải hóa đơn này - bảng PrinterParam
// return value = 3 : không tồn tại cấu hình pageWidth, pageHeight cho hóa đơn - bảng BookType
// return value = 0 : Lỗi exception
// return filePath : trả ra đường dẫn của file
public class InvoicePrinterDAO extends BaseDAOAction{

    long xAmplitude = 0L;
    long yAmplitude = 0L;
    int yearLength = 2;
    boolean checkUbuntuOS = true;
    float widthPage = 210;
    float heightPage = 297 / 2;
    Long smallStep = 5L;//TIN
    Long bigStep = 6L;//TIN
    Long yStep = 7L;
    Long sttDiscountX = 0L;
    Long discountX = 0L;
    Double VAT = 10D;
    String BOOK_TYPE_STEP = "BOOK_TYPE_STEP";
    String SMALL_STEP = "SMALL_STEP";
    String BIG_STEP = "BIG_STEP";
    String Y_STEP = "Y_STEP";
    String STRING_PROMOTION = "Hàng khuyến mại không thu tiền";

    /**
     * 
     * @param invoiceUsedId
     * @param invoiceType
     * @return
     * @throws Exception
     */
    public String printSaleTransInvoice_1(Long invoiceUsedId, Long invoiceType) throws Exception {
        HttpServletRequest request = getRequest();
        try {
            xAmplitude = 0L;//do lech truc x khi in tren OSSS Ubuntu
            yAmplitude = 0L;//do lech truc y khi in tren OSSS Ubuntu

            SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
            saleTransDetailDAO.setSession(this.getSession());
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            invoiceUsedDAO.setSession(this.getSession());
            PrinterParamDAO printerParamDAO = new PrinterParamDAO();
            printerParamDAO.setSession(this.getSession());
            SaleTransDAO saleTransDAO = new SaleTransDAO();
            saleTransDAO.setSession(getSession());
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            BookTypeDAO bookTypeDAO = new BookTypeDAO();
            bookTypeDAO.setSession(this.getSession());
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(this.getSession());

            //Chi tiet hoa don
            InvoiceSaleListBean invoiceSaleListBean = invoiceUsedDAO.getInvoiceUsedDetail(invoiceUsedId);
            if (invoiceSaleListBean == null
                    || invoiceSaleListBean.getSerialNo() == null
                    || invoiceSaleListBean.getSerialNo().trim().equals("")) {
                return "0";//Khong tim thay hoa don
            }
            String serialNo = invoiceSaleListBean.getSerialNo();

            //Danh sach cau hinh hoa don
            List<PrinterParam> listPrinterParam = printerParamDAO.findByProperty("serialCode", serialNo);
            if (listPrinterParam == null || listPrinterParam.isEmpty()) {
                return "2";//Danh sach rong
            }

            //Thong tin serial hoa don
            List<BookType> listBookType = bookTypeDAO.findByProperty("serialCode", serialNo);
            if (listBookType.get(0).getPageWidth() == null || listBookType.get(0).getPageHeight() == null) {
                return "3";
            } else {
                widthPage = listBookType.get(0).getPageWidth();//do rong
                heightPage = listBookType.get(0).getPageHeight();//do cao
                yStep = listBookType.get(0).getRowSpacing();//do gian dong
            }

            //Do lech toa do in khi in OSS Ubuntu
            PrinterUserDAO printerUserDAO = new PrinterUserDAO();
            printerUserDAO.setSession(this.getSession());
            String[] propertyName = new String[3];
            propertyName[0] = "serialCode";
            propertyName[1] = "invoiceType";
            propertyName[2] = "userName";
            Object[] propertyValue = new Object[3];
            propertyValue[0] = serialNo.trim();
            if (invoiceType == null) {
                invoiceType = 1L;
            }
            if (invoiceType.compareTo(2L) == 0) {
                xAmplitude = -9L;
                yAmplitude = 22L;
            }
            propertyValue[1] = invoiceType;
            UserToken userToken = (UserToken) request.getSession().getAttribute(Constant.USER_TOKEN);
            //propertyValue[2] = userToken.getLoginName();
            propertyValue[2] = userToken.getShopCode().trim();

            List<PrinterUser> listPrinterUser = printerUserDAO.findByProperty(propertyName, propertyValue);
//            List<PrinterUser> listPrinterUser = printerUserDAO.findByProperty("serialCode", serialNo);
            if (listPrinterUser != null && listPrinterUser.size() > 0) {
                xAmplitude = listPrinterUser.get(0).getXAmplitude();
                yAmplitude = listPrinterUser.get(0).getYAmplitude();
            }

            //Do gian khi in ma so thue TIN
            List<AppParams> listAppParams = appParamsDAO.findAppParamsByType(BOOK_TYPE_STEP);
            if (listAppParams != null && listAppParams.size() > 0) {
                for (int i = 0; i < listAppParams.size(); i++) {
                    AppParams appParams = listAppParams.get(i);
                    if (appParams.getCode().trim().toUpperCase().equals(BIG_STEP)) {
                        bigStep = Long.valueOf(appParams.getValue().trim());
                    } else if (appParams.getCode().trim().toUpperCase().equals(SMALL_STEP)) {
                        smallStep = Long.valueOf(appParams.getValue().trim());
                    }
                }
            }

            //Danh sach hang hoa trong hoa don
            List<SaleTransBean> listSaleTrans = saleTransDAO.getSaleTransList(invoiceSaleListBean.getInvoiceUsedId());

            //Tao file FDF
            Document documentPDF = new Document(new Rectangle(0, 0, widthPage, heightPage));

            //Duong dan download file PDF
            String unFilePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            //So hoa don
            String invoiceNo = invoiceSaleListBean.getInvoiceNo();
            while (invoiceNo.indexOf("\\") >= 0) {
                invoiceNo = invoiceNo.replace("\\", "_");
            }
            while (invoiceNo.indexOf("/") >= 0) {
                invoiceNo = invoiceNo.replace("/", "_");
            }

            String filePath = unFilePath + invoiceNo + ".pdf";
            String realPath = request.getSession().getServletContext().getRealPath(filePath);
            PdfWriter pdf = PdfWriter.getInstance(documentPDF,
                    new FileOutputStream(realPath));
            documentPDF.open();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = dateFormat.format(invoiceSaleListBean.getCreatedate());
            String[] arrStrDate = strDate.split("-");


            Long step = 0L;//Vi tri toa do y khi in danh sach hang hoa & dich vu
            Long discount = 0L;//Tong chiet khau chua thue            
            //Long promotion = 0L;//Tong khuyen mai
            Long count = 1L;//Dung de hien thi so thu tu            
            Long quantity = 0L;//Dung de tinh tien cho mat hang
            Long price = 0L;//Dung de tinh tien cho mat hang

            //In danh sach mat hang
            Double dAmountNotTax = 0d;//Tong tien chua thue (kieu double)
            Double dAmountTax = 0d;//Tong tien co thue (kieu double)

            Double dSumAmountNotTax = 0d;//Tinh theo vong for
            Double dSumTax = 0d;//Tinh theo vong for
            Double dSumAmountTax = 0d;//Tinh theo vong for


            Double dTax = 0d;//Tong tien thue (kieu double)

            //Them cho Hoa don moi (DV/2010T)
            Double dAmountNotTaxNull = 0d;//Tien hang khong chiu thue (hien tai khong dung vi == tong tien thue 0%)            
            Double dAmountNotTax10 = 0d;//Tien hang chua thue 10%
            Double dTax10 = 0d;//Tien thue 10%
            //Double dAmountTax10 = 0d;//Tong tien sau thue 10%

            boolean isPromotion = false;//Co phai la giao dich khuyen mai
            boolean isTCDT = false;//Co phai la giao dich ban TCDT


            //check co phai la giao dich ban khuyen mai
            for (SaleTransBean saleTrans : listSaleTrans) {
                if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION)) {
                    isPromotion = true;
                    break;
                }
            }

            //Tim ti suat VAT cua hoa don
            if (null != invoiceSaleListBean.getVAT()) {
                VAT = Double.valueOf(invoiceSaleListBean.getVAT().toString());
            }
            if (isPromotion) {
                VAT = 0D;
            }

            Long[] lstSaleTransId = new Long[listSaleTrans.size()];
            Long isFineTrans = 0L;
            String saleTransType = "";
            int index = 0;

            //Lay danh sach sale_trans_id 
            for (SaleTransBean saleTrans : listSaleTrans) {
                lstSaleTransId[index++] = saleTrans.getSaleTransId();
                isFineTrans = saleTrans.getIsFineTrans();
                saleTransType = saleTrans.getSaleTransType();

                //Tinh tong chiet khau cua hoa don
//                if (saleTrans.getDiscount() != null) {
//                    discount += saleTrans.getDiscount();
//                }
                //Tinh tong khuyen mai cua hoa don
//                if (saleTrans.getPromotion() != null) {
//                    promotion += saleTrans.getPromotion();
//                }
            }

            //Tong tien chiet khau
            if (invoiceSaleListBean.getDiscount() != null) {
                discount = invoiceSaleListBean.getDiscount().longValue();
            } else {
                discount = 0L;
            }


            //Tam thoi xu ly:
            //neu la hoa don khuyen mai thi gan tong tien 0vat la tong tien hoa don, tong tien 10vat = 0;
            //neu la hoa don co vat thi gan tong tien 0vat = 0, tong tien 10vat = tong tien hoa don
            if (isPromotion) {
                dAmountNotTaxNull = invoiceSaleListBean.getAmountNotTax().doubleValue();
                dAmountNotTax10 = 0d;
                dTax10 = 0d;
            } else {
                dAmountNotTaxNull = 0d;
                dAmountNotTax10 = invoiceSaleListBean.getAmountNotTax().doubleValue();
                dTax10 = invoiceSaleListBean.getTax().doubleValue();
            }


            //Danh sach hang hoa co amount > 0 trong hoa don
            List<SaleTransDetailBean> listSaleTransDetailBean = saleTransDetailDAO.getSaleTransDetailList(lstSaleTransId, isFineTrans, invoiceSaleListBean.getCreatedate());
            if (listSaleTransDetailBean == null) {
                return "0";
            }

            boolean checkPrint  = false;
            for (SaleTransDetailBean saleTransDetailBean : listSaleTransDetailBean) {
                //Kiem tra giao dich co ban TCDT hay khong
                if (!isTCDT && saleTransDetailBean.getStockModelCode() != null && saleTransDetailBean.getStockModelCode().trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
                    isTCDT = true;
                }
                //Neu la hoa don khuyen mai => in them 1 dong HOA DON KHUYEN MAI KHONG THU TIEN
                if (isPromotion && !checkPrint) {
                    for (PrinterParam printerParam : listPrinterParam) {
                        if (printerParam.getIsDetailField().equals(1L) && printerParam.getFieldName().equals("STOCK_MODEL_NAME")) {
                            printOneColumn(pdf, this.STRING_PROMOTION, printerParam, step);
                            step = step + yStep;
                            checkPrint = true;
                            break;
                        }
                    }
                }

                //In danh sach hang hoa & dich vu
                for (PrinterParam printerParam : listPrinterParam) {
                    if (printerParam.getIsDetailField().equals(1L)) {
                        //Chi tiet hang hoa
                        if (printerParam.getFieldName().equals("STT")) {
                            sttDiscountX = printerParam.getXCoordinates();
                            printOneColumn(pdf, count.toString(), printerParam, step);
                        }
                        if (printerParam.getFieldName().equals("STOCK_MODEL_NAME")) {
                            discountX = printerParam.getXCoordinates();
                            printOneColumn(pdf, saleTransDetailBean.getStockModelName(), printerParam, step);
                        }
                        if (printerParam.getFieldName().equals("UNIT_NAME")) {
                            printOneColumn(pdf, saleTransDetailBean.getUnitName(), printerParam, step);
                        }
                        //So luong
                        if (printerParam.getFieldName().equals("QUANTITY") && saleTransDetailBean.getQuantity() != null) {
                            quantity = saleTransDetailBean.getQuantity().longValue();
                            if (quantity == null) {
                                quantity = 1L;//Bo sung
                            }
                            String strQuantity = StringUtils.formatMoney(quantity);
                            if (!isTCDT) {
                                printOneColumn(pdf, strQuantity, printerParam, step);
                            }
                        }
                        //Don gia chua thue
                        if (printerParam.getFieldName().equals("PRICE") && saleTransDetailBean.getPrice() != null) {
                            Double dPrice = saleTransDetailBean.getPrice() / (1 + (VAT / 100));
                            price = Math.round(dPrice);
                            String strPrice = StringUtils.formatMoney(price);
                            if (!isTCDT) {
                                printOneColumn(pdf, strPrice, printerParam, step);
                            }
                        }
                        //Thanh tien chua thue
                        if (printerParam.getFieldName().equals("AMOUNT") && saleTransDetailBean.getPrice() != null) {
                            Double dPrice = saleTransDetailBean.getPrice() / (1 + (VAT / 100));
                            quantity = saleTransDetailBean.getQuantity().longValue();
                            if (quantity == null) {
                                quantity = 1L;//Bo sung
                            }
                            Long amount = Math.round(quantity.doubleValue() * dPrice);
                            //Luu gia tri tong tien truoc thue
                            dAmountNotTax += quantity.doubleValue() * dPrice;

                            dSumAmountNotTax += quantity.doubleValue() * dPrice;

                            String strAmount = StringUtils.formatMoney(amount);
                            printOneColumn(pdf, strAmount, printerParam, step);
                        }

                        //Bo sung cho hoa don moi (DV/2010T)
                        //Ti suat VAT
                        //Tam thoi lay theo ti suat VAT cua hoa don
                        if (printerParam.getFieldName().equals("DETAIL_VAT")) {
                            if (isPromotion) {
                                printOneColumn(pdf, "\\", printerParam, step);
                            } else {
                                printOneColumn(pdf, StringUtils.formatMoney(VAT), printerParam, step);
                            }
                        }

                        //Bo sung cho hoa don moi (DV/2010T)
                        //Tien thue
                        if (printerParam.getFieldName().equals("DETAIL_TAX") && saleTransDetailBean.getPrice() != null) {
                            if (isPromotion) {
                                printOneColumn(pdf, "\\", printerParam, step);
                            } else {
                                Double dPrice = (saleTransDetailBean.getPrice() * VAT) / ((1 + (VAT / 100)) * 100);
                                quantity = saleTransDetailBean.getQuantity().longValue();
                                if (quantity == null) {
                                    quantity = 1L;//Bo sung
                                }
                                Long amount = Math.round(quantity.doubleValue() * dPrice);

                                dSumTax += quantity.doubleValue() * dPrice;

                                String strAmount = StringUtils.formatMoney(amount);
                                printOneColumn(pdf, strAmount, printerParam, step);
                            }
                        }

                        //Bo sung cho hoa don moi (DV/2010T)
                        //Thanh tien sau thue
                        if (printerParam.getFieldName().equals("DETAIL_AMOUNT_TAX") && saleTransDetailBean.getPrice() != null) {
                            Double dPrice = saleTransDetailBean.getPrice() / (1 + VAT - VAT);
                            quantity = saleTransDetailBean.getQuantity().longValue();
                            if (quantity == null) {
                                quantity = 1L;//Bo sung
                            }
                            Long amount = Math.round(quantity.doubleValue() * dPrice);

                            dSumAmountTax += quantity.doubleValue() * dPrice;

                            //Luu gia tri tong tien truoc thue
                            String strAmount = StringUtils.formatMoney(amount);
                            printOneColumn(pdf, strAmount, printerParam, step);
                        }
                    }
                }
                step = step + yStep;
                count++;
            }

            //Tong tien chua thue (da tru chiet khau)
            if (discount != null) {
                dAmountNotTax -= discount.doubleValue();
            }
            //Tong tien co thue (da tru chiet khau)
            if (invoiceSaleListBean.getAmountTax() != null) {
                dAmountTax = invoiceSaleListBean.getAmountTax().doubleValue();
            }
            //Tong tien thue (da tru chiet khau)
            dTax = dAmountTax - dAmountNotTax;

            for (PrinterParam printerParam : listPrinterParam) {
                if (printerParam.getIsDetailField() != 1) {
                    step = 0L;
                    if (printerParam.getFieldName().equals("DAY")) {
                        printOneColumn(pdf, arrStrDate[0], printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("MONTH")) {
                        printOneColumn(pdf, arrStrDate[1], printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("YEAR")) {
                        printOneColumn(pdf, arrStrDate[2].substring(4 - yearLength), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("CUST_NAME")) {
                        printOneColumn(pdf, invoiceSaleListBean.getCustName(), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("COMPANY")) {
                        printOneColumn(pdf, invoiceSaleListBean.getCompany(), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("ADDRESS")) {
                        printOneColumn(pdf, invoiceSaleListBean.getAddress(), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("TIN")) {
                        String tin = invoiceSaleListBean.getTin();
                        if (tin != null) {
                            Long normalXco = printerParam.getXCoordinates();
                            String[] inputString = new String[30];
                            for (int i = 0; i < tin.length(); i++) {
                                inputString[i] = tin.substring(i, i + 1);
                            }
                            for (int i = 0; i < inputString.length; i++) {
                                Long xCo = printerParam.getXCoordinates();
                                if (inputString[i] != null) {
                                    if (i == 0) {
                                        printerParam.setXCoordinates(xCo);
                                    } else if (i != 2 && i != 10 && i != 13) {
                                        printerParam.setXCoordinates(xCo + smallStep);
                                    } else {
                                        printerParam.setXCoordinates(xCo + bigStep);
                                    }
                                    printOneColumn(pdf, inputString[i], printerParam, step);
                                }
                            }
                            printerParam.setXCoordinates(normalXco);
                            continue;
                        }
                    }
                    if (printerParam.getFieldName().equals("ACCOUNT")) {
                        printOneColumn(pdf, invoiceSaleListBean.getAccount(), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("PAY_METHOD")) {
                        //Neu la ban hang khuyen mai & khong phai la hoa don da dich vu
//                        if (isPromotion && serialNo.indexOf("DV") < 0) {
                        if (isPromotion) {
                            printOneColumn(pdf, STRING_PROMOTION, printerParam, step);
                        } else {
                            printOneColumn(pdf, invoiceSaleListBean.getPayMethodName(), printerParam, step);
                        }
                        continue;
                    }




//                    SUM_DETAIL_AMOUNT
//SUM_DETAIL_TAX
//SUM_DETAIL_AMOUNT_TAX
//
//SUM_DETAIL_DISCOUNT_AMOUNT
//SUM_DETAIL_DISCOUNT_TAX
//SUM_DETAIL_DISCOUNT_AMOUNT_TAX

                    //Tong tien chua tru chiet khau
                    if (printerParam.getFieldName().equals("SUM_DETAIL_AMOUNT")) {
                        printOneColumn(pdf, StringUtils.formatMoney(Math.round(dSumAmountNotTax)), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("SUM_DETAIL_TAX")) {
                        printOneColumn(pdf, StringUtils.formatMoney(Math.round(dSumTax)), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("SUM_DETAIL_AMOUNT_TAX")) {
                        printOneColumn(pdf, StringUtils.formatMoney(Math.round(dSumAmountTax)), printerParam, step);
                        continue;
                    }

                    //Chi tiet tien chiet khau
                    if (printerParam.getFieldName().equals("DETAIL_DISCOUNT_AMOUNT")) {
                        if (discount != null && discount.compareTo(0L) > 0) {
                            String strDiscount = StringUtils.formatMoney(discount);
                            printOneColumn(pdf, strDiscount, printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("DETAIL_DISCOUNT_VAT")) {
                        if (!isPromotion && VAT.intValue() > 0) {
                            printOneColumn(pdf, StringUtils.formatMoney(VAT), printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("DETAIL_DISCOUNT_TAX")) {
                        if (discount != null && discount.compareTo(0L) > 0) {
                            String strDiscount = StringUtils.formatMoney(Math.round(discount * VAT / 100));
                            printOneColumn(pdf, strDiscount, printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("DETAIL_DISCOUNT_AMOUNT_TAX")) {
                        if (discount != null && discount.compareTo(0L) > 0) {
                            String strDiscount = StringUtils.formatMoney(Math.round(discount * (VAT / 100 + 1)));
                            printOneColumn(pdf, strDiscount, printerParam, step);
                        }
                        continue;
                    }

                    //Tong tien chiet khau
                    if (printerParam.getFieldName().equals("SUM_DETAIL_DISCOUNT_AMOUNT")) {
                        if (discount != null && discount.compareTo(0L) > 0) {
                            String strDiscount = StringUtils.formatMoney(discount);
                            printOneColumn(pdf, strDiscount, printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("SUM_DETAIL_DISCOUNT_TAX")) {
                        if (discount != null && discount.compareTo(0L) > 0) {
                            String strDiscount = StringUtils.formatMoney(Math.round(discount * VAT / 100));
                            printOneColumn(pdf, strDiscount, printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("SUM_DETAIL_DISCOUNT_AMOUNT_TAX")) {
                        if (discount != null && discount.compareTo(0L) > 0) {
                            String strDiscount = StringUtils.formatMoney(Math.round(discount * (VAT / 100 + 1)));
                            printOneColumn(pdf, strDiscount, printerParam, step);
                        }
                        continue;
                    }


                    if (printerParam.getFieldName().equals("FINE")) {
                        if (saleTransType != null && saleTransType.equals(Constant.SALE_TRANS_TYPE_RETAIL)) {
                            printOneColumn(pdf, "", printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("PROMOTION")) {
                        if (invoiceSaleListBean.getPromotion() != null && invoiceSaleListBean.getPromotion().longValue() > 0) {
                            String amount = StringUtils.formatMoney(invoiceSaleListBean.getPromotion());
                            printOneColumn(pdf, amount, printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("AMOUNT_TAX")) {
                        String amountTax = StringUtils.formatMoney(invoiceSaleListBean.getAmountTax());
                        printOneColumn(pdf, amountTax, printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("AMOUNT_NOT_TAX")) {
                        String amountNotTax = StringUtils.formatMoney(invoiceSaleListBean.getAmountNotTax());
                        //Luu gia tri tong tien truoc thue
                        amountNotTax = StringUtils.formatMoney(Math.round(dAmountNotTax));
                        printOneColumn(pdf, amountNotTax, printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("TAX")) {
                        if (isPromotion) {
                            printOneColumn(pdf, "\\", printerParam, step);
                        } else {
//                            String tax = StringUtils.formatMoney(invoiceSaleListBean.getTax());
                            //Luu gia tri tong tien truoc thue
                            String tax = StringUtils.formatMoney(Math.round(dTax));
                            printOneColumn(pdf, tax, printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("VAT")) {
                        if (isPromotion) {
                            printOneColumn(pdf, "\\", printerParam, step);
                        } else {
                            printOneColumn(pdf, StringUtils.formatMoney(VAT), printerParam, step);
                        }
                        continue;
                    }
                    if (printerParam.getFieldName().equals("AMOUNT_TAX_STRING")) {
                        Long amountTax = invoiceSaleListBean.getAmountTax().longValue();
                        printOneColumn(pdf, commonDAO.speakMoney(amountTax), printerParam, step);
                        continue;
                    }
                    if (printerParam.getFieldName().equals("DISCOUNT")) {
                        if (discount != null && discount.compareTo(0L) > 0) {
                            String strDiscount = "";
                            strDiscount = StringUtils.formatMoney(discount);
                            printOneColumn(pdf, strDiscount, printerParam, step);
                        }
                        continue;
                    }




                    //BO SUNG CHO HOA DON MOI (DV/2010T)
                    if (printerParam.getFieldName().equals("AMOUNT_NOT_TAX_NULL")) {
                        if (!dAmountNotTaxNull.equals(0d)) {
                            String strAmount = StringUtils.formatMoney(dAmountNotTaxNull);
                            printOneColumn(pdf, strAmount, printerParam, step);
                            continue;
                        }
                    }
                    if (printerParam.getFieldName().equals("AMOUNT_NOT_TAX_10")) {
                        if (!dAmountNotTax10.equals(0d)) {
                            String strAmount = StringUtils.formatMoney(dAmountNotTax10);
                            printOneColumn(pdf, strAmount, printerParam, step);
                            continue;
                        }
                    }
                    if (printerParam.getFieldName().equals("TAX_10")) {
                        if (!dTax10.equals(0d)) {
                            String strAmount = StringUtils.formatMoney(dTax10);
                            printOneColumn(pdf, strAmount, printerParam, step);
                            continue;
                        }
                    }
                }
            }
            documentPDF.close();
            return filePath;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }

    private void printOneColumn(PdfWriter pdf, String inputString, PrinterParam printerParam, Long step) {
        try {
            HttpServletRequest request = getRequest();
            if (inputString == null || inputString.equals("")) {
                return;
            }

            float w = widthPage;
            float h = heightPage;

            Long xCo = printerParam.getXCoordinates();
            Long yCo = Long.parseLong(String.valueOf((int) heightPage)) - (printerParam.getYCoordinates() + step);

            xCo += xAmplitude;
            yCo += yAmplitude;

            PdfContentByte cb = pdf.getDirectContent();

            PdfTemplate tp = cb.createTemplate(w, h);
//            Graphics2D g2 = tp.createGraphics(w, h, mapper);
//            Graphics2D g2 = cb.createGraphicsShapes(PageSize.A5.getWidth(), PageSize.A5.getHeight());
            Integer thisFontSize = 3;

            String unFilePath = ResourceBundleUtils.getResource("LINK_TO_FONT_FOLDER");
            String thisFontPath = unFilePath + "arial.ttf";


            if (printerParam.getFont() != null && !printerParam.getFont().equals("")) {
                thisFontPath = unFilePath + printerParam.getFont();
            }
            if (printerParam.getFontSize() != null) {
                thisFontSize = Integer.parseInt(printerParam.getFontSize().toString());
            }
            String thisFont = request.getSession().getServletContext().getRealPath(thisFontPath);

            BaseFont bf = BaseFont.createFont(thisFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            cb.beginText();
            cb.setFontAndSize(bf, thisFontSize);
            if (printerParam.getFieldName().equals("PRICE")
                    || printerParam.getFieldName().equals("AMOUNT")
                    || printerParam.getFieldName().equals("SUM_DETAIL_AMOUNT")
                    || printerParam.getFieldName().equals("SUM_DETAIL_TAX")
                    || printerParam.getFieldName().equals("SUM_DETAIL_AMOUNT_TAX")
                    || printerParam.getFieldName().equals("SUM_DETAIL_DISCOUNT_AMOUNT")
                    || printerParam.getFieldName().equals("SUM_DETAIL_DISCOUNT_TAX")
                    || printerParam.getFieldName().equals("SUM_DETAIL_DISCOUNT_AMOUNT_TAX")
                    || printerParam.getFieldName().equals("QUANTITY")
                    || printerParam.getFieldName().equals("TAX")
                    || printerParam.getFieldName().equals("AMOUNT_TAX")
                    || printerParam.getFieldName().equals("AMOUNT_NOT_TAX")
                    || printerParam.getFieldName().equals("GRANT_AMOUNT_TAX")//Bo sung theo mau hoa don moi DV/2010T

                    || printerParam.getFieldName().equals("DETAIL_VAT")
                    || printerParam.getFieldName().equals("DETAIL_TAX")
                    || printerParam.getFieldName().equals("DETAIL_AMOUNT_TAX")
                    || printerParam.getFieldName().equals("DETAIL_DISCOUNT_AMOUNT") //Bo sung theo mau hoa don moi DV/2010T
                    || printerParam.getFieldName().equals("DETAIL_DISCOUNT_TAX")//Bo sung theo mau hoa don moi DV/2010T
                    || printerParam.getFieldName().equals("DETAIL_DISCOUNT_AMOUNT_TAX") //Bo sung theo mau hoa don moi DV/2010T
                    //                    ||printerParam.getFieldName().equals("DISCOUNT")
                    ) {
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, inputString, xCo, yCo, 0);

                if (printerParam.getFieldName().equals("DETAIL_DISCOUNT_AMOUNT")) {

                    cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "1", sttDiscountX, yCo, 0);
                    cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Chiết khấu TM", discountX, yCo, 0);
                }


//                if(printerParam.getFieldName().equals("DISCOUNT"))
//                    cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Chiết khấu", discountX, yCo, 0);
            } else if (printerParam.getFieldName().equals("DISCOUNT")) {
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, inputString, xCo, yCo, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Chiết khấu", discountX, yCo, 0);
            } else if (printerParam.getFieldName().equals("PROMOTION")) {
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, inputString, xCo, yCo, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Khuyến mại", discountX, yCo, 0);
            } else if (printerParam.getFieldName().equals("FINE")) {
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "Hoá đơn thu phạt", discountX, yCo, 0);
            } else if (printerParam.getFieldName().equals("STT")) {
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER, inputString, xCo, yCo, 0);
            } else {
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, inputString, xCo, yCo, 0);
            }
            cb.endText();
            cb.addTemplate(tp, 0, 0);
        } catch (DocumentException ex) {
            ex.printStackTrace();
            Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * In hoa don khong tu giao dich
     */
    private Double INVOICE_NO_SALE_VAT = 0D;

    public Double getINVOICE_NO_SALE_VAT() {
        return INVOICE_NO_SALE_VAT;
    }

    public void setINVOICE_NO_SALE_VAT(Double INVOICE_NO_SALE_VAT) {
        this.INVOICE_NO_SALE_VAT = INVOICE_NO_SALE_VAT;
    }

    public String printInvoiceNoSale(Long invoiceUsedId, List<InvoiceNoSaleItem> lstItems) throws Exception {
        HttpServletRequest request = getRequest();
        try {
            SaleTransDetailDAO saleTransDetailDAO = new SaleTransDetailDAO();
            InvoiceUsedDAO invoiceUsedDAO = new InvoiceUsedDAO();
            PrinterParamDAO printerParamDAO = new PrinterParamDAO();
            SaleTransDAO saleTransDAO = new SaleTransDAO();
            CommonDAO commonDAO = new CommonDAO();

            InvoiceSaleListBean invoiceSaleListBean = invoiceUsedDAO.getInvoiceUsedDetail(invoiceUsedId);
            String serialNo = invoiceSaleListBean.getSerialNo();

            List<PrinterParam> listPrinterParam = printerParamDAO.findByProperty("serialCode", serialNo);
            if (listPrinterParam == null || listPrinterParam.size() == 0) {
                return "2";
            }
            BookTypeDAO bookTypeDAO = new BookTypeDAO();
            //bookTypeDAO.setSession(this.getSession());
            List<BookType> listBookType = bookTypeDAO.findByProperty("serialCode", serialNo);
            if (listBookType.get(0).getPageWidth() == null || listBookType.get(0).getPageHeight() == null) {
                return "3";
            } else {
                widthPage = listBookType.get(0).getPageWidth();
                heightPage = listBookType.get(0).getPageHeight();
                yStep = listBookType.get(0).getRowSpacing();
            }


            List<SaleTrans> listSaleTrans = saleTransDAO.findByProperty("invoiceUsedId", invoiceSaleListBean.getInvoiceUsedId());

            Document documentPDF = new Document(new Rectangle(0, 0, widthPage, heightPage));

            String unFilePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String invoiceNo = invoiceSaleListBean.getInvoiceNo();
            while (invoiceNo.indexOf("\\") >= 0) {
                invoiceNo = invoiceNo.replace("\\", "_");
            }
            while (invoiceNo.indexOf("/") >= 0) {
                invoiceNo = invoiceNo.replace("/", "_");
            }
            //String filePath = unFilePath + invoiceSaleListBean.getInvoiceNo() + ".pdf";
            String filePath = unFilePath + invoiceNo + ".pdf";

            String realPath = request.getSession().getServletContext().getRealPath(filePath);
            PdfWriter pdf = PdfWriter.getInstance(documentPDF,
                    new FileOutputStream(realPath));

            documentPDF.open();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String strDate = dateFormat.format(invoiceSaleListBean.getCreatedate());
            String[] arrStrDate = strDate.split("-");


            Long step = 0L;
            Long count = 1L;
            Long discount = 0L;
            Long quantity = 0L;
            Long price = 0L;

            for (InvoiceNoSaleItem saleTransDetailBean : lstItems) {
                for (PrinterParam printerParam : listPrinterParam) {
                    if (printerParam.getIsDetailField() == 1) {
                        if (printerParam.getFieldName().equals("STT")) {
                            printOneColumn(pdf, count.toString(), printerParam, step);
                        }
                        if (printerParam.getFieldName().equals("STOCK_MODEL_NAME")) {
                            discountX = printerParam.getXCoordinates();
                            //printOneColumn(pdf, saleTransDetailBean.getStockModelName(), printerParam, step);
                            printOneColumn(pdf, saleTransDetailBean.getItemName(), printerParam, step);
                        }
                        if (printerParam.getFieldName().equals("UNIT_NAME")) {
                            //printOneColumn(pdf, saleTransDetailBean.getUnitName(), printerParam, step);
                            printOneColumn(pdf, saleTransDetailBean.getItemUnit(), printerParam, step);
                        }
                        //if (printerParam.getFieldName().equals("QUANTITY") && saleTransDetailBean.getQuantity() != null) {
                        if (printerParam.getFieldName().equals("QUANTITY") && saleTransDetailBean.getItemQty() != null) {
                            //quantity = saleTransDetailBean.getQuantity();
                            quantity = saleTransDetailBean.getItemQty();
                            String strQuantity = StringUtils.formatMoney(quantity);
                            printOneColumn(pdf, strQuantity, printerParam, step);
                        }
                        //if (printerParam.getFieldName().equals("PRICE") && saleTransDetailBean.getPrice() != null) {
                        if (printerParam.getFieldName().equals("PRICE") && saleTransDetailBean.getItemPrice() != null) {
                            //Double dPrice = saleTransDetailBean.getPrice() / (1 + (VAT / 100));
                            Double dPrice = saleTransDetailBean.getItemPrice() / (1 + (INVOICE_NO_SALE_VAT / 100));
                            price = dPrice.longValue();
                            String strPrice = StringUtils.formatMoney(price);
                            printOneColumn(pdf, strPrice, printerParam, step);
                        }
                        //if (printerParam.getFieldName().equals("AMOUNT") && saleTransDetailBean.getAmount() != null) {
                        if (printerParam.getFieldName().equals("AMOUNT")) {
                            //Double dPrice = saleTransDetailBean.getPrice() / (1 + (VAT / 100));
                            Double dPrice = saleTransDetailBean.getItemPrice() / (1 + (INVOICE_NO_SALE_VAT / 100));
                            price = dPrice.longValue();
                            //quantity = saleTransDetailBean.getQuantity();
                            quantity = saleTransDetailBean.getItemQty();
                            Long amount = quantity * price;
                            String strAmount = StringUtils.formatMoney(amount);
                            printOneColumn(pdf, strAmount, printerParam, step);
                        }
                    }
                }
                step = step + yStep;
                count++;
            }

            for (PrinterParam printerParam : listPrinterParam) {
                if (printerParam.getIsDetailField() != 1) {
                    step = 0L;
                    if (printerParam.getFieldName().equals("DAY")) {
                        printOneColumn(pdf, arrStrDate[0], printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("MONTH")) {
                        printOneColumn(pdf, arrStrDate[1], printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("YEAR")) {
                        printOneColumn(pdf, arrStrDate[2].substring(3), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("CUST_NAME")) {
                        printOneColumn(pdf, invoiceSaleListBean.getCustName(), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("COMPANY")) {
                        printOneColumn(pdf, invoiceSaleListBean.getCompany(), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("ADDRESS")) {
                        printOneColumn(pdf, invoiceSaleListBean.getAddress(), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("PAY_METHOD")) {
                        printOneColumn(pdf, invoiceSaleListBean.getPayMethodName(), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("TIN")) {

                        String tin = invoiceSaleListBean.getTin();
                        if (tin != null) {
                            Long normalXco = printerParam.getXCoordinates();
                            String[] inputString = new String[20];
                            for (int i = 0; i < tin.length(); i++) {
                                inputString[i] = tin.substring(i, i + 1);
                            }
                            for (int i = 0; i < inputString.length; i++) {
                                Long xCo = printerParam.getXCoordinates();
                                if (inputString[i] != null) {
                                    if (i == 0) {
                                        printerParam.setXCoordinates(xCo);
                                    } else if (i != 2 && i != 10 && i != 13) {
                                        printerParam.setXCoordinates(xCo + smallStep);
                                    } else {
                                        printerParam.setXCoordinates(xCo + bigStep);
                                    }
                                    printOneColumn(pdf, inputString[i], printerParam, step);
                                }
                            }
                            printerParam.setXCoordinates(normalXco);
                        }
                    }
                    if (printerParam.getFieldName().equals("ACCOUNT")) {
                        printOneColumn(pdf, invoiceSaleListBean.getAccount(), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("PAY_METHOD")) {
                        printOneColumn(pdf, invoiceSaleListBean.getPayMethodName(), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("ACCOUNT")) {
                        printOneColumn(pdf, invoiceSaleListBean.getAccount(), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("AMOUNT_TAX")) {
                        String amountTax = StringUtils.formatMoney(invoiceSaleListBean.getAmountTax());
                        printOneColumn(pdf, amountTax, printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("AMOUNT_NOT_TAX")) {
                        String amountNotTax = StringUtils.formatMoney(invoiceSaleListBean.getAmountNotTax());
                        printOneColumn(pdf, amountNotTax, printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("TAX")) {
                        //String tax = StringUtils.formatMoney(invoiceSaleListBean.getTax());
                        //printOneColumn(pdf, tax, printerParam, step);
                        printOneColumn(pdf, "\\", printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("VAT")) {
                        //String VAT = StringUtils.convertFromLongToString(invoiceSaleListBean.getVAT());
                        //printOneColumn(pdf, String.valueOf(INVOICE_NO_SALE_VAT.longValue()), printerParam, step);
                        printOneColumn(pdf, "\\", printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("AMOUNT_TAX_STRING")) {
                        Long amountTax = invoiceSaleListBean.getAmountTax().longValue();
                        printOneColumn(pdf, commonDAO.speakMoney(amountTax), printerParam, step);
                    }
                    if (printerParam.getFieldName().equals("DISCOUNT")) {
                        //String strDiscount = "(" + StringUtils.formatMoney(discount) + ")";
                        String strDiscount = "" + StringUtils.formatMoney(discount) + "";
                        printOneColumn(pdf, strDiscount, printerParam, step);
                    }

                }
            }

            documentPDF.close();
            return filePath;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }
}
