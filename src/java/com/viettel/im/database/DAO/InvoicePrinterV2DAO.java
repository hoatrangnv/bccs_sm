/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.InvoiceSaleListBean;
import com.viettel.im.client.bean.SaleTransBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.MultiLanguageNumberToWords;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author MrSun
 */
public class InvoicePrinterV2DAO extends BaseDAOAction {

	String LANGUAGE_DEFAULT = "pt";
	String GOODS_ORDER_NO = "ORDER_NO";
	String GOODS_CODE = "CODE";
	String GOODS_NAME = "NAME";
	String GOODS_UNIT = "UNIT";
	String GOODS_QUANTITY = "QUANTITY";
	String GOODS_PRICE = "PRICE";
	String GOODS_AMOUNT = "AMOUNT";
	String INVOICE_PROMOTION = "PROMOTION";
	String INVOICE_DISCOUNT = "DISCOUNT";
	String INVOICE_CUST_NAME = "CUST_NAME";
	String INVOICE_ADDRESS = "ADDRESS";
	String INVOICE_TEL = "TEL";
	String INVOICE_NUIT = "NUIT";
	String INVOICE_PAY_METHOD = "PAY_METHOD";
	String INVOICE_SERVICE_CODE = "SERVICE_CODE";
	String INVOICE_INVOICE_DATE = "INVOICE_DATE";
	String INVOICE_INVOICE_NUMBER = "INVOICE_NUMBER";
	String INVOICE_GRANT_SUBTOTAL = "GRANT_SUBTOTAL";
	String INVOICE_GRANT_DISCOUNT = "GRANT_DISCOUNT";
	String INVOICE_GRANT_AMOUNT_BEFORE_TAX = "GRANT_AMOUNT_BEFORE_TAX";
	String INVOICE_GRANT_AMOUNT_TAX = "GRANT_AMOUNT_TAX";
	String INVOICE_GRANT_AMOUNT_AFTER_TAX = "GRANT_AMOUNT_AFTER_TAX";
	String INVOICE_DEPOSIT = "DEPOSIT";
	String INVOICE_TOTAL_PAYED = "TOTAL_PAYED";
	String INVOICE_TOTAL_BY_WORD = "TOTAL_BY_WORD";
	long xAmplitude = 0L;
	long yAmplitude = 0L;
	int yearLength = 2;
	boolean checkUbuntuOS = true;
	float widthPage = 297;
	float heightPage = 210;
	Long smallStep = 5L;//TIN
	Long bigStep = 6L;//TIN
	Long yStep = 7L;
	Long sttDiscountX = 0L;
	Long discountX = 0L;
	Double VAT = 17D;
	String BOOK_TYPE_STEP = "BOOK_TYPE_STEP";
	String SMALL_STEP = "SMALL_STEP";
	String BIG_STEP = "BIG_STEP";
	String Y_STEP = "Y_STEP";
	String STRING_PROMOTION = "Promotion Invoice";
	String STRING_DISCOUNT = "Discount";
	boolean isPrintPromotionReason = false;
	boolean isPrintInvoiceId = false;
	boolean isPrintGoodsPriceNull = false;

	private static final Log log = LogFactory.getLog(InvoicePrinterV2DAO.class);

	public List<SaleTransDetailV2Bean> getSaleTransDetailList(Long[] lstSaleTransId, Date invoiceDate) {

		if (null == lstSaleTransId || lstSaleTransId.length == 0) {
			return null;
		}

		StringBuffer sqlBuffer = new StringBuffer();
		List parameterList = new ArrayList();

		String sqlDate = "";
		if (invoiceDate != null) {
			try {
				String fromDate = DateTimeUtils.convertDateToString(invoiceDate);
				String toDate = DateTimeUtils.convertDateToString(DateTimeUtils.addDate(invoiceDate, 1));
				sqlDate = " saleTransDate >= to_date('" + fromDate + "','yyyy-MM-dd') and saleTransDate < to_date('" + toDate + "','yyyy-MM-dd') ";
			} catch (Exception ex) {
				ex.printStackTrace();
				sqlDate = "";
			}
		}

		String sqlId = "";
		for (Long id : lstSaleTransId) {
			if (id == null || id.equals(0L)) {
				continue;
			}
			if (!sqlId.equals("")) {
				sqlId += " OR ";
			}
			sqlId += " saleTransId = ? ";
			parameterList.add(id);
		}

		sqlBuffer.append("   SELECT   stockModelId, ");
		sqlBuffer.append("            stockModelCode, ");
		sqlBuffer.append("            stockModelName, ");
		sqlBuffer.append("            SUM (quantity) AS quantity, ");
		sqlBuffer.append("            price, ");
		sqlBuffer.append("            vat, ");
		sqlBuffer.append("            SUM (amount) AS amount, ");
		sqlBuffer.append("            SUM (discountamount) AS discountAmount, ");
		sqlBuffer.append("            SUM (amountbeforetax) AS amountBeforeTax, ");
		sqlBuffer.append("            SUM (amounttax) AS amountTax, ");
		sqlBuffer.append("            SUM (amountaftertax) AS amountAfterTax ");
		sqlBuffer.append("     FROM   (SELECT   stock_model_id AS stockmodelid, ");
		sqlBuffer.append("                      SALE_TRANS_ID AS saleTransId, ");
		sqlBuffer.append("                      SALE_TRANS_DATE AS saleTransDate, ");
		sqlBuffer.append("                      stock_model_code AS stockmodelcode, ");
		sqlBuffer.append("                      stock_model_name AS stockmodelname, ");
		sqlBuffer.append("                      NVL (quantity, 0) AS quantity, ");
		sqlBuffer.append("                      NVL (price, 0) AS price, ");
		sqlBuffer.append("                      NVL (price_vat, 0) AS vat, ");
		sqlBuffer.append("                      NVL (amount, 0) AS amount, ");
		sqlBuffer.append("                      NVL (discount_amount, 0) AS discountamount, ");
		sqlBuffer.append("                      NVL (amount_before_tax, 0) AS amountbeforetax, ");
		sqlBuffer.append("                      NVL (amount_tax, 0) AS amounttax, ");
		sqlBuffer.append("                      NVL (amount_after_tax, 0) AS amountaftertax ");
		sqlBuffer.append("               FROM   sale_trans_detail ");
		sqlBuffer.append("              WHERE   stock_model_id IS NOT NULL ");
		sqlBuffer.append("                      AND ( (stock_model_id IN ");
		sqlBuffer.append("                                     (SELECT   stock_model_id ");
		sqlBuffer.append("                                        FROM   stock_model ");
		sqlBuffer.append("                                       WHERE   stock_type_id IN (1, 2, 3)) ");
		sqlBuffer.append("                             AND price > 0) ");
		sqlBuffer.append("                           OR (stock_model_id IN ");
		sqlBuffer.append("                                       (SELECT   stock_model_id ");
		sqlBuffer.append("                                          FROM   stock_model ");
		sqlBuffer.append("                                         WHERE   stock_type_id NOT IN (1, 2, 3)))) ");
		sqlBuffer.append("             UNION ALL ");
		sqlBuffer.append("             SELECT   sale_services_id AS stockmodelid, ");
		sqlBuffer.append("                      SALE_TRANS_ID AS saleTransId, ");
		sqlBuffer.append("                      SALE_TRANS_DATE AS saleTransDate, ");
		sqlBuffer.append("                      sale_services_code AS stockmodelcode, ");
		sqlBuffer.append("                      sale_services_name AS stockmodelname, ");
		sqlBuffer.append("                      NVL (quantity, 0) AS quantity, ");
		sqlBuffer.append("                      NVL (sale_services_price, 0) AS price, ");
		sqlBuffer.append("                      NVL (sale_services_price_vat, 0) AS vat, ");
		sqlBuffer.append("                      NVL (amount, 0) AS amount, ");
		sqlBuffer.append("                      NVL (discount_amount, 0) AS discountamount, ");
		sqlBuffer.append("                      NVL (amount_before_tax, 0) AS amountbeforetax, ");
		sqlBuffer.append("                      NVL (amount_tax, 0) AS amounttax, ");
		sqlBuffer.append("                      NVL (amount_after_tax, 0) AS amountaftertax ");
		sqlBuffer.append("               FROM   sale_trans_detail ");
		sqlBuffer.append("              WHERE   stock_model_id IS NULL) ");

		sqlBuffer.append(" WHERE 1=1 ");

		if (sqlDate != null && !sqlDate.trim().equals("")) {
			sqlBuffer.append(" AND ( ");
			sqlBuffer.append(sqlDate);
			sqlBuffer.append(" ) ");
		}

		if (sqlId != null && !sqlId.trim().equals("")) {
			sqlBuffer.append(" AND ( ");
			sqlBuffer.append(sqlId);
			sqlBuffer.append(" ) ");
		} else {
			return null;
		}

		sqlBuffer.append(" GROUP BY   stockModelId, ");
		sqlBuffer.append("            stockModelCode, ");
		sqlBuffer.append("            stockModelName, ");
		sqlBuffer.append("            price, ");
		sqlBuffer.append("            vat ");
		sqlBuffer.append(" ORDER BY   stockModelCode ");

		Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
				addScalar("stockModelId", Hibernate.LONG).
				addScalar("stockModelCode", Hibernate.STRING).
				addScalar("stockModelName", Hibernate.STRING).
				addScalar("quantity", Hibernate.LONG).
				addScalar("price", Hibernate.DOUBLE).
				addScalar("vat", Hibernate.DOUBLE).
				addScalar("amount", Hibernate.DOUBLE).
				addScalar("discountAmount", Hibernate.DOUBLE).
				addScalar("amountBeforeTax", Hibernate.DOUBLE).
				addScalar("amountTax", Hibernate.DOUBLE).
				addScalar("amountAfterTax", Hibernate.DOUBLE).
				setResultTransformer(Transformers.aliasToBean(SaleTransDetailV2Bean.class));
		for (int i = 0; i < parameterList.size(); i++) {
			queryObject.setParameter(i, parameterList.get(i));
		}
		return queryObject.list();

	}

	public InvoiceSaleListBean getInvoiceUsedDetail(Long invoiceUsedId) {
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			List parameterList = new ArrayList();

			sqlBuffer.append(" SELECT DISTINCT ");
			sqlBuffer.append(" invud.INVOICE_USED_ID as invoiceUsedId, ");
			sqlBuffer.append(" invud.SERIAL_NO as serialNo, ");
			sqlBuffer.append(" invud.INVOICE_ID as invoiceId, ");
			sqlBuffer.append(" invud.invoice_date as createdate, ");
			sqlBuffer.append(" invud.CUST_NAME as custName, ");
			sqlBuffer.append(" invud.ACCOUNT as account, ");
			sqlBuffer.append(" invud.ADDRESS as address, ");
			sqlBuffer.append(" invud.COMPANY as company, ");
			sqlBuffer.append(" invud.AMOUNT_TAX as amountTax, ");
			sqlBuffer.append(" invud.AMOUNT as amountNotTax, ");
			sqlBuffer.append(" invud.TIN as tin, ");
			sqlBuffer.append(" invud.NOTE as note, ");
			sqlBuffer.append(" invud.ISDN as isdn, ");
			sqlBuffer.append(" invud.telecom_service_id as telecomServiceId, ");
			sqlBuffer.append(" invud.DISCOUNT as discount, ");
			sqlBuffer.append(" invud.PROMOTION as promotion, ");
			sqlBuffer.append(" invud.TAX as tax, ");
			sqlBuffer.append(" invud.VAT as VAT, ");
			sqlBuffer.append(" invud.INVOICE_NO as invoiceNo, ");
			sqlBuffer.append(" appara.NAME as payMethodName, ");
			sqlBuffer.append(" rea.REASON_NAME as reasonName, ");
			sqlBuffer.append(" stf.NAME as staffName ");
			sqlBuffer.append(" ,sp.NAME AS shopName ");
			sqlBuffer.append(" ,il.from_invoice AS fromInvoice ");
			sqlBuffer.append(" ,il.to_invoice AS toInvoice ");
			sqlBuffer.append(" ,il.curr_invoice_no AS currInvoice ");
			sqlBuffer.append(" FROM ");
			sqlBuffer.append(" APP_PARAMS appara, ");
			sqlBuffer.append(" INVOICE_USED invud ");
			sqlBuffer.append(" JOIN ");
			sqlBuffer.append(" STAFF stf ");
			sqlBuffer.append(" ON ");
			sqlBuffer.append(" invud.STAFF_ID =  stf.STAFF_ID ");
			sqlBuffer.append(" LEFT JOIN ");
			sqlBuffer.append(" REASON rea ");
			sqlBuffer.append(" ON ");
			sqlBuffer.append(" rea.REASON_ID =  invud.REASON_ID ");
			sqlBuffer.append(" JOIN ");
			sqlBuffer.append(" SHOP sp  ");
			sqlBuffer.append(" ON ");
			sqlBuffer.append(" invud.shop_id =  sp.shop_id ");
			sqlBuffer.append(" JOIN ");
			sqlBuffer.append(" invoice_list il ");
			sqlBuffer.append(" ON ");
			sqlBuffer.append(" invud.invoice_list_id = il.invoice_list_id ");
			sqlBuffer.append(" WHERE ");
			sqlBuffer.append(" 1 = 1 ");
			sqlBuffer.append(" AND ");
			sqlBuffer.append(" invud.INVOICE_USED_ID = ? ");
			sqlBuffer.append(" AND ");
			sqlBuffer.append(" appara.TYPE = ? ");
			sqlBuffer.append(" AND ");
			sqlBuffer.append(" appara.CODE = invud.PAY_METHOD ");
			parameterList.add(invoiceUsedId);
			parameterList.add(Constant.PARAM_TYPE_PAY_METHOD);

			Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
					addScalar("invoiceUsedId", Hibernate.LONG).
					addScalar("serialNo", Hibernate.STRING).
					addScalar("invoiceId", Hibernate.LONG).
					addScalar("createdate", Hibernate.DATE).
					addScalar("custName", Hibernate.STRING).
					addScalar("account", Hibernate.STRING).
					addScalar("address", Hibernate.STRING).
					addScalar("company", Hibernate.STRING).
					addScalar("amountTax", Hibernate.DOUBLE).
					addScalar("amountNotTax", Hibernate.DOUBLE).
					addScalar("tin", Hibernate.STRING).
					addScalar("note", Hibernate.STRING).
					addScalar("isdn", Hibernate.STRING).
					addScalar("telecomServiceId", Hibernate.LONG).
					addScalar("discount", Hibernate.DOUBLE).
					addScalar("promotion", Hibernate.DOUBLE).
					addScalar("tax", Hibernate.DOUBLE).
					addScalar("VAT", Hibernate.DOUBLE).
					addScalar("invoiceNo", Hibernate.STRING).
					addScalar("payMethodName", Hibernate.STRING).
					addScalar("reasonName", Hibernate.STRING).
					addScalar("staffName", Hibernate.STRING).
					addScalar("shopName", Hibernate.STRING).
					addScalar("fromInvoice", Hibernate.STRING).
					addScalar("toInvoice", Hibernate.STRING).
					addScalar("currInvoice", Hibernate.STRING).
					setResultTransformer(Transformers.aliasToBean(InvoiceSaleListBean.class));

			for (int i = 0; i < parameterList.size(); i++) {
				queryObject.setParameter(i, parameterList.get(i));
			}
			List result = queryObject.list();
			if (result != null && !result.isEmpty()) {
				return (InvoiceSaleListBean) queryObject.list().get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<SaleTransDetailBean> getSaleTransDetailList_bk(Long[] lstSaleTransId, Date invoiceDate) {

		if (null == lstSaleTransId || lstSaleTransId.length == 0) {
			return null;
		}

		String sqlDate = "";
		if (invoiceDate != null) {
			try {
				String fromDate = DateTimeUtils.convertDateToString(invoiceDate);
				String toDate = DateTimeUtils.convertDateToString(DateTimeUtils.addDate(invoiceDate, 1));
				sqlDate = " std.sale_trans_date >= to_date('" + fromDate + "','yyyy-MM-dd') and std.sale_trans_date < to_date('" + toDate + "','yyyy-MM-dd') ";
			} catch (Exception ex) {
				ex.printStackTrace();
				sqlDate = "";
			}
		}

		StringBuffer sqlBuffer = new StringBuffer();
		List parameterList = new ArrayList();
		String sql = "";
		for (Long id : lstSaleTransId) {
			if (id == null || id.equals(0L)) {
				continue;
			}
			if (!sql.equals("")) {
				sql += " OR ";
			}
			sql += " std.sale_trans_id = ? ";
			parameterList.add(id);
		}
		int length = parameterList.size();
		for (int i = 0; i < length; i++) {
			parameterList.add(parameterList.get(i));
		}
		if (!sql.equals("")) {
			sqlBuffer.append("     AND ( ");
			sqlBuffer.append(sql);
			sqlBuffer.append("     ) ");
		} else {
			return null;
		}

		sqlBuffer.append("  ");
		sqlBuffer.append(" SELECT  ss.code AS stockModelCode ");
		sqlBuffer.append("     ,ss.name AS stockModelName  ");
		sqlBuffer.append("     ,'' AS unitName ");
//        sqlBuffer.append("     ,ssp.price ");
		sqlBuffer.append("     ,std.sale_services_price ");
		sqlBuffer.append("     ,sum(std.quantity) AS quantity ");
//        sqlBuffer.append(" FROM sale_trans_detail std, sale_services ss, sale_services_price ssp ");
		sqlBuffer.append(" FROM sale_trans_detail std, sale_services ss ");
		sqlBuffer.append(" WHERE 1=1 ");

		if (!isPrintGoodsPriceNull) {
//            sqlBuffer.append(" and ssp.price>0 ");
			sqlBuffer.append(" and std.price>0 ");
		}

		if (!sql.equals("")) {
			sqlBuffer.append("     AND ( ");
			sqlBuffer.append(sql);
			sqlBuffer.append("     ) ");
		}
		if (!sqlDate.equals("")) {
			sqlBuffer.append("     AND ( ");
			sqlBuffer.append(sqlDate);
			sqlBuffer.append("     ) ");
		}
		sqlBuffer.append("     AND std.sale_services_id = ss.sale_services_id and std.stock_model_id is null ");
		//sqlBuffer.append("     AND std.sale_services_price_id = ssp.sale_services_price_id ");
		sqlBuffer.append("      ");
//        sqlBuffer.append(" GROUP BY std.sale_services_id, ss.code,ss.name, std.sale_services_price_id, ssp.price ");
		sqlBuffer.append(" GROUP BY std.sale_services_id, ss.code,ss.name, std.sale_services_price_id, std.price ");
		sqlBuffer.append("  ");
		sqlBuffer.append(" UNION ALL ");
		sqlBuffer.append("  ");
		sqlBuffer.append(" SELECT  sm.stock_model_code AS stockModelCode  ");
		sqlBuffer.append("     ,sm.NAME AS stockModelName  ");
		sqlBuffer.append("     ,(SELECT NAME FROM app_params WHERE TYPE='STOCK_MODEL_UNIT' AND code=sm.unit) AS unitName ");
		sqlBuffer.append("     ,p.price ");
		sqlBuffer.append("     ,sum(std.quantity) AS quantity ");
//        sqlBuffer.append(" FROM sale_trans_detail std, stock_model sm, price p ");
		sqlBuffer.append(" FROM sale_trans_detail std, stock_model sm ");
		sqlBuffer.append(" WHERE 1=1 ");

		if (!isPrintGoodsPriceNull) {
//            sqlBuffer.append(" and p.price>0 ");
			sqlBuffer.append(" and std.price>0 ");
		}

		if (!sql.equals("")) {
			sqlBuffer.append("     AND ( ");
			sqlBuffer.append(sql);
			sqlBuffer.append("     ) ");
		}
		if (!sqlDate.equals("")) {
			sqlBuffer.append("     AND ( ");
			sqlBuffer.append(sqlDate);
			sqlBuffer.append("     ) ");
		}
		sqlBuffer.append("     AND std.stock_model_id = sm.stock_model_id ");
		sqlBuffer.append("     AND std.price_id = p.price_id     ");
		sqlBuffer.append(" GROUP BY std.stock_model_id, sm.stock_model_code, sm.NAME, sm.unit, std.price_id, p.price ");

		Query queryObject = getSession().createSQLQuery(sqlBuffer.toString()).
				addScalar("stockModelCode", Hibernate.STRING).
				addScalar("stockModelName", Hibernate.STRING).
				addScalar("unitName", Hibernate.STRING).
				addScalar("price", Hibernate.DOUBLE).
				addScalar("quantity", Hibernate.LONG).
				setResultTransformer(Transformers.aliasToBean(SaleTransDetailBean.class));
		for (int i = 0; i < parameterList.size(); i++) {
			queryObject.setParameter(i, parameterList.get(i));
		}
		return queryObject.list();
	}

	public String printSaleTransInvoice(Long invoiceUsedId, Long invoiceType, Long printType) throws Exception {
		System.out.println("START printSaleTransInvoice ");
		log.debug("START printSaleTransInvoice");
		HttpServletRequest request = getRequest();
		try {
			xAmplitude = 0L;//do lech truc x khi in tren OS Ubuntu
			yAmplitude = 0L;//do lech truc y khi in tren OS Ubuntu

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

			log.debug(" printSaleTransInvoice 1");
			//Chi tiet hoa don
			InvoiceSaleListBean invoiceSaleListBean = this.getInvoiceUsedDetail(invoiceUsedId);
			if (invoiceSaleListBean == null
					|| invoiceSaleListBean.getSerialNo() == null
					|| invoiceSaleListBean.getSerialNo().trim().equals("")) {
				return "0";//Khong tim thay hoa don
			}
			log.debug(" printSaleTransInvoice 2");
			String serialNo = invoiceSaleListBean.getSerialNo();

			//Danh sach cau hinh hoa don
			List<PrinterParam> listPrinterParam = printerParamDAO.findByProperty(printerParamDAO.SERIAL_CODE, serialNo);
			if (listPrinterParam == null || listPrinterParam.isEmpty()) {
				return "2";//Danh sach rong
			}

			log.debug(" printSaleTransInvoice 3");
			//Thong tin serial hoa don
			List<BookType> listBookType = bookTypeDAO.findByProperty("serialCode", serialNo);
			if (listBookType.get(0).getPageWidth() == null || listBookType.get(0).getPageHeight() == null) {
				return "3";
			} else {
				widthPage = listBookType.get(0).getPageWidth();//do rong
				heightPage = listBookType.get(0).getPageHeight();//do cao
				yStep = listBookType.get(0).getRowSpacing();//do gian dong
			}
			log.debug(" printSaleTransInvoice 4");
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
			if (invoiceType.compareTo(3L) == 0) {
				xAmplitude = -9L;
				yAmplitude = 22L;
			}
			propertyValue[1] = invoiceType;
			UserToken userToken = (UserToken) request.getSession().getAttribute(Constant.USER_TOKEN);
			//propertyValue[2] = userToken.getLoginName();
			propertyValue[2] = userToken.getShopCode().trim();

			log.debug(" printSaleTransInvoice 5");
			List<PrinterUser> listPrinterUser = printerUserDAO.findByProperty(propertyName, propertyValue);
//            List<PrinterUser> listPrinterUser = printerUserDAO.findByProperty("serialCode", serialNo);
			if (listPrinterUser != null && listPrinterUser.size() > 0) {
				xAmplitude = listPrinterUser.get(0).getXAmplitude();
				yAmplitude = listPrinterUser.get(0).getYAmplitude();
			}

			log.debug(" printSaleTransInvoice 6");
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
			log.debug(" printSaleTransInvoice 7");
			//Danh sach hang hoa trong hoa don
			List<SaleTransBean> listSaleTrans = saleTransDAO.getSaleTransList(invoiceSaleListBean.getInvoiceUsedId());

			//Tao file FDF
			Document documentPDF = new Document(new Rectangle(0, 0, widthPage, heightPage));

			//Duong dan download file PDF
			String unFilePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

			//So hoa don
			String invoiceNo = invoiceSaleListBean.getInvoiceNo();
//            invoiceNo = invoiceNo.replaceAll("\\", "_").replaceAll("/", "_");
			log.debug(" printSaleTransInvoice 8");

			while (invoiceNo.indexOf("\\") >= 0) {
				invoiceNo = invoiceNo.replace("\\", "_");
			}
			invoiceNo = invoiceNo.replaceAll("/", "_");
			invoiceNo = invoiceNo.replaceAll("-", "_");

			String filePath = unFilePath + invoiceNo + ".pdf";
			String realPath = request.getSession().getServletContext().getRealPath(filePath);

			FileOutputStream streamOut = new FileOutputStream(realPath);
			PdfWriter pdf = PdfWriter.getInstance(documentPDF, streamOut);

			String templatePath = ResourceBundleUtils.getResource("INVOICE_PATTERN_PATH");
			String serialCode2 = listBookType.get(0).getSerialCode().trim();
			while (serialCode2.indexOf("\\") >= 0) {
				serialCode2 = serialCode2.replace("\\", "_");
			}
			serialCode2 = serialCode2.replaceAll("/", "_");
			serialCode2 = serialCode2.replaceAll("-", "_");
			if (printType == 1L) {
				templatePath = templatePath + serialCode2 + "_L1.pdf";
			} else {
				templatePath = templatePath + serialCode2 + "_L2.pdf";
			}

            //templatePath = templatePath + serialCode2 + ".pdf";
			log.debug(" printSaleTransInvoice 9");
			String realTemplatePath = request.getSession().getServletContext().getRealPath(templatePath);
//            PdfReader pdfReader = new PdfReader("D:\\IT_2010A_CV.pdf");//duong link file pdf goc
			PdfReader pdfReader = new PdfReader(realTemplatePath);//duong link file pdf goc
			PdfStamper pdfStamper = new PdfStamper(pdfReader, streamOut);
			PdfContentByte cb = pdfStamper.getOverContent(1);
//            documentPDF.open();

//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            String strDate = dateFormat.format(invoiceSaleListBean.getCreatedate());
//            String[] arrStrDate = strDate.split("-");
			Long count = 1L;//Dung de hien thi so thu tu            
			Long step = 0L;//Vi tri toa do y khi in danh sach hang hoa & dich vu
			boolean isPromotion = false;//Co phai la giao dich khuyen mai
			boolean isTCDT = false;//Co phai la giao dich ban TCDT
			boolean checkPrint = false;
			double grantSubTotal = 0.0;  //GRANT_SUBTOTAL
			double discountAmount = 0.0;  //GRANT_SUBTOTAL

			//check co phai la giao dich ban khuyen mai
			for (SaleTransBean saleTrans : listSaleTrans) {
				if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION)) {
					isPromotion = true;
					break;
				}
			}

			Long[] lstSaleTransId = new Long[listSaleTrans.size()];
			int index = 0;

			//Lay danh sach sale_trans_id
			for (SaleTransBean saleTrans : listSaleTrans) {
				lstSaleTransId[index++] = saleTrans.getSaleTransId();
			}
			log.debug(" printSaleTransInvoice 10");
			//Danh sach hang hoa co amount > 0 trong hoa don
			List<SaleTransDetailV2Bean> listSaleTransDetailBean = this.getSaleTransDetailList(lstSaleTransId, invoiceSaleListBean.getCreatedate());
			if (listSaleTransDetailBean == null || listSaleTransDetailBean.isEmpty()) {
				return "0";
			}

			for (SaleTransDetailV2Bean saleTransDetailBean : listSaleTransDetailBean) {

				isTCDT = false;//ductm6_fix loi in hoa don_20150212
				//Chia cho thue suat : vi luu trong DB la gia sau thue
				try {
					if (saleTransDetailBean.getVat() != null && !saleTransDetailBean.getVat().equals(0.0)) {
						saleTransDetailBean.setPrice(saleTransDetailBean.getPrice() / (1 + saleTransDetailBean.getVat() / 100.0));
						saleTransDetailBean.setAmount(saleTransDetailBean.getAmount() / (1 + saleTransDetailBean.getVat() / 100.0));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				log.debug(" printSaleTransInvoice 11");
//                grantSubTotal += saleTransDetailBean.getPrice() * saleTransDetailBean.getQuantity();
				grantSubTotal += saleTransDetailBean.getAmount();
				discountAmount += saleTransDetailBean.getDiscountAmount();

				//Kiem tra giao dich co ban TCDT hay khong
				if (!isTCDT && saleTransDetailBean.getStockModelCode() != null && saleTransDetailBean.getStockModelCode().trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
					isTCDT = true;
				}
                //Neu la hoa don khuyen mai => in them 1 dong HOA DON KHUYEN MAI KHONG THU TIEN
				//MZB : khong can in : isPrintPromotionReason = false
				if (isPrintPromotionReason && isPromotion && !checkPrint) {
					for (PrinterParam printerParam : listPrinterParam) {
						if (printerParam.getIsDetailField() != null && printerParam.getIsDetailField().equals(1L) && printerParam.getFieldName().equals(this.GOODS_NAME)) {
							printOneColumn(pdfStamper, this.STRING_PROMOTION, printerParam, step);
							step = step + yStep;
							checkPrint = true;
							break;
						}
					}
				}
				log.debug(" printSaleTransInvoice 12");
				for (PrinterParam printerParam : listPrinterParam) {
					if (printerParam.getIsDetailField() != null && printerParam.getIsDetailField().equals(1L)) {
						//Chi tiet hang hoa
						if (printerParam.getFieldName().equals(this.GOODS_ORDER_NO)) {
							sttDiscountX = printerParam.getXCoordinates();
							printOneColumn(pdfStamper, count.toString(), printerParam, step);
						}
						if (printerParam.getFieldName().equals(this.GOODS_NAME)) {
							discountX = printerParam.getXCoordinates();
							printOneColumn(pdfStamper, saleTransDetailBean.getStockModelName(), printerParam, step);
						}
//                        if (printerParam.getFieldName().equals(this.GOODS_UNIT)) {
//                            printOneColumn(pdfStamper, saleTransDetailBean.getUnitName(), printerParam, step);
//                        }
						//So luong
						if (printerParam.getFieldName().equals(this.GOODS_QUANTITY) && saleTransDetailBean.getQuantity() != null) {
							if (!isTCDT) {
								printOneColumn(pdfStamper, NumberUtils.formatNumber(saleTransDetailBean.getQuantity()), printerParam, step);
							}
						}
						//Don gia chua thue
						if (printerParam.getFieldName().equals(this.GOODS_PRICE) && saleTransDetailBean.getPrice() != null) {
							if (!isTCDT) {
								printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(saleTransDetailBean.getPrice()), printerParam, step);
							}
						}
						//Thanh tien chua thue
						if (printerParam.getFieldName().equals(this.GOODS_AMOUNT) && saleTransDetailBean.getPrice() != null && saleTransDetailBean.getQuantity() != null) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(saleTransDetailBean.getQuantity() * saleTransDetailBean.getPrice()), printerParam, step);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(saleTransDetailBean.getAmount()), printerParam, step);
						}
					}
				}
				step = step + yStep;
				count++;
			}
			log.debug(" printSaleTransInvoice 13");
			for (PrinterParam printerParam : listPrinterParam) {
				if (printerParam.getIsDetailField() == null || !printerParam.getIsDetailField().equals(1L)) {
					if (printerParam.getFieldName().equals(this.INVOICE_INVOICE_DATE)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld + 11;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, (new SimpleDateFormat("MMMM dd, yyyy", new Locale(LANGUAGE_DEFAULT, LANGUAGE_DEFAULT.toUpperCase()))).format(invoiceSaleListBean.getCreatedate()), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_CUST_NAME)) {
						printOneColumn(pdfStamper, invoiceSaleListBean.getCustName(), printerParam, 0L);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_ADDRESS)) {
//                        printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
//                        continue;

                        //do dai truong dia chi tren 1 dong
						//khoang cach giua 2 dong cua truong dia chi
						if (printerParam.getWidth() == null
								|| printerParam.getHeight() == null
								|| printerParam.getWidth().equals(0L)
								|| printerParam.getHeight().equals(0L)) {
							printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
							continue;
						}

						if (invoiceSaleListBean.getAddress() == null || invoiceSaleListBean.getAddress().trim().equals("")) {
							continue;
						}

						if (invoiceSaleListBean.getAddress().trim().length() <= printerParam.getWidth().intValue()) {
							printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
						} else {
							String[] tmp = invoiceSaleListBean.getAddress().trim().split(" ");
							String tmp1 = "";
							String tmp2 = "";
							int i = 0;
							while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < printerParam.getWidth().intValue()) {
								tmp1 = tmp1 + " " + (tmp[i++]);
							}
							tmp2 = invoiceSaleListBean.getAddress().trim().substring(tmp1.length());
							printOneColumn(pdfStamper, tmp1, printerParam, 0L);
							printOneColumn(pdfStamper, tmp2, printerParam, printerParam.getHeight());
						}
					}
					if (printerParam.getFieldName().equals(this.INVOICE_TEL)) {
						printOneColumn(pdfStamper, invoiceSaleListBean.getIsdn(), printerParam, 0L);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_NUIT)) {
						printOneColumn(pdfStamper, invoiceSaleListBean.getTin(), printerParam, 0L);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_SERVICE_CODE)) {
						if (invoiceSaleListBean.getTelecomServiceId() != null && invoiceSaleListBean.getTelecomServiceId().equals(1L)) {
							printOneColumn(pdfStamper, "M001", printerParam, 0L);
						}
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_PAY_METHOD)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld + 11;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getPayMethodName(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_INVOICE_NUMBER)) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date date = new Date();
						String strDate = dateFormat.format(date);
						String invoiceNoNew = "";
						if (isPrintInvoiceId) {
							// tannh20180604 : bo invoiceNo con 7 chu so 
//                            invoiceNoNew = invoiceNo.substring(invoiceNo.length() - 7);
							printOneColumn(pdfStamper, invoiceSaleListBean.getInvoiceId().toString(), printerParam, 0L);
							// tannh20180604 : them barcode vao file pdf
							Barcode128 code128 = new Barcode128();
							code128.setCode(strDate.substring(0, 2) + invoiceSaleListBean.getInvoiceId().toString() + strDate.substring(3, 5));
							code128.setFont(null);
							PdfTemplate template1 = code128.createTemplateWithBarcode(cb, null, null);
							cb.addTemplate(template1, 420, 750);
						} else {
							// tannh20180604 : bo invoiceNo con 7 chu so 
							if (invoiceSaleListBean != null && invoiceSaleListBean.getInvoiceNo().length() > 7) {
								invoiceNoNew = invoiceSaleListBean.getInvoiceNo().substring(invoiceSaleListBean.getInvoiceNo().length() - 7);
							}
							printOneColumn(pdfStamper, invoiceNoNew, printerParam, 0L);
							// tannh20180604 : them barcode vao file pdf
							Barcode128 code128 = new Barcode128();
							code128.setCode(strDate.substring(0, 2) + invoiceNoNew + strDate.substring(3, 5));
							code128.setFont(null);
							PdfTemplate template1 = code128.createTemplateWithBarcode(cb, null, null);
							cb.addTemplate(template1, 420, 750);
						}
						continue;
					}
					log.debug(" printSaleTransInvoice 14");
					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_SUBTOTAL)) {
						if (!isPromotion) {
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(grantSubTotal), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "", printerParam, 0L);
						}

						continue;
					}

//                    if (printerParam.getFieldName().equals(this.INVOICE_DISCOUNT) && discountAmount > 0.0) {
					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_DISCOUNT) && discountAmount > 0.0) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getDiscount()), printerParam, 0L);
						printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(discountAmount), printerParam, 0L);
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_BEFORE_TAX)) {
//                        printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountNotTax()), printerParam, 0L);
						printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(grantSubTotal - discountAmount), printerParam, 0L);
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_TAX)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getTax()), printerParam, 0L);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount((grantSubTotal - discountAmount) * invoiceSaleListBean.getVAT() / 100), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "\\", printerParam, 0L);
						}
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_AFTER_TAX)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountTax()), printerParam, 0L);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount((grantSubTotal - discountAmount) * (1 + invoiceSaleListBean.getVAT() / 100)), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "", printerParam, 0L);
						}

						continue;
					}

//                    if (printerParam.getFieldName().equals(this.INVOICE_GRANT_DISCOUNT)) {
//                        if (!isPromotion && invoiceSaleListBean.getDiscount() != null && !invoiceSaleListBean.getDiscount().equals(0.0)) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getDiscount() * 1.17), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "", printerParam, 0L);
//                        }
//
//                        continue;
//                    }
//                    if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_PAYED)) {
//                        if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountTax()), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "\\", printerParam, 0L);
//                        }
//                        continue;
//                    }
//                    if (printerParam.getFieldName().equals(this.INVOICE_DEPOSIT)) {
//                        if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(0.0), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "", printerParam, 0L);
//                        }
//                        continue;
//                    }
					if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_BY_WORD)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, MultiLanguageNumberToWords.convert(invoiceSaleListBean.getAmountTax(), this.LANGUAGE_DEFAULT), printerParam, 0L);
							String inWord = MultiLanguageNumberToWords.convert(NumberUtils.roundNumber((grantSubTotal - discountAmount) * (1 + invoiceSaleListBean.getVAT() / 100)), this.LANGUAGE_DEFAULT);

                            //do dai truong dia chi tren 1 dong
							//khoang cach giua 2 dong cua truong dia chi
							if (printerParam.getWidth() == null
									|| printerParam.getHeight() == null
									|| printerParam.getWidth().equals(0L)
									|| printerParam.getHeight().equals(0L)) {
								printOneColumn(pdfStamper, inWord, printerParam, 0L);
								continue;
							}

							if (inWord.trim().length() <= printerParam.getWidth().intValue()) {
								printOneColumn(pdfStamper, inWord, printerParam, 0L);
							} else {
								String[] tmp = inWord.trim().split(" ");
								String tmp1 = "";
								String tmp2 = "";
								int i = 0;
								while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < printerParam.getWidth().intValue()) {
									tmp1 = tmp1 + " " + (tmp[i++]);
								}
								tmp2 = inWord.trim().substring(tmp1.length());
								printOneColumn(pdfStamper, tmp1, printerParam, 0L);
								printOneColumn(pdfStamper, tmp2, printerParam, printerParam.getHeight());
							}

                            //Xu ly theo cach moi : luu tham so trong DB
/* 
							 Long invoiceWordLength = 0L;
							 String invoiceWordLengthTmp = ResourceBundleUtils.getResource("INVOICE_WORD_LENGTH");
							 if (invoiceWordLengthTmp != null && !invoiceWordLengthTmp.trim().equals("")) {
							 invoiceWordLength = Long.valueOf(invoiceWordLengthTmp);
							 }
							 Long invoiceWordDistance = 0L;
							 String invoiceWordDistanceTmp = ResourceBundleUtils.getResource("INVOICE_WORD_DISTANCE");
							 if (invoiceWordDistanceTmp != null && !invoiceWordDistanceTmp.trim().equals("")) {
							 invoiceWordDistance = Long.valueOf(invoiceWordDistanceTmp);
							 }
							 if (inWord.length() <= invoiceWordLength.intValue()) {
							 printOneColumn(pdfStamper, inWord, printerParam, 0L);
							 } else {
							 String[] tmp = inWord.split(" ");
							 String tmp1 = "";
							 String tmp2 = "";
							 int i = 0;
							 while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < invoiceWordLength.intValue()) {
							 tmp1 = tmp1 + " " + (tmp[i++]);
							 }
							 tmp2 = inWord.substring(tmp1.length());
							 printOneColumn(pdfStamper, tmp1, printerParam, 0L);
							 printOneColumn(pdfStamper, tmp2, printerParam, invoiceWordDistance);
							 }*/
						} else {
							printOneColumn(pdfStamper, "\\", printerParam, 0L);
						}

						continue;
					}
				}
			}
			pdfStamper.close();
			log.debug("END printSaleTransInvoice");
			System.out.println("END printSaleTransInvoice ");
			return filePath;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug(ex.getMessage());
			log.debug(ex.getLocalizedMessage());
			return "0";
		}
	}

	public String printSaleTransInvoiceT2(Long invoiceUsedId, Long invoiceType, Long printType) throws Exception {
		System.out.println("START printSaleTransInvoice ");
		HttpServletRequest request = getRequest();
		try {
			xAmplitude = 0L;//do lech truc x khi in tren OS Ubuntu
			yAmplitude = 0L;//do lech truc y khi in tren OS Ubuntu

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
			InvoiceSaleListBean invoiceSaleListBean = this.getInvoiceUsedDetail(invoiceUsedId);
			if (invoiceSaleListBean == null
					|| invoiceSaleListBean.getSerialNo() == null
					|| invoiceSaleListBean.getSerialNo().trim().equals("")) {
				return "0";//Khong tim thay hoa don
			}
			String serialNo = invoiceSaleListBean.getSerialNo();

			//Danh sach cau hinh hoa don
			List<PrinterParam> listPrinterParam = printerParamDAO.findByProperty(printerParamDAO.SERIAL_CODE, serialNo);
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
			if (invoiceType.compareTo(3L) == 0) {
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
//            invoiceNo = invoiceNo.replaceAll("\\", "_").replaceAll("/", "_");

			while (invoiceNo.indexOf("\\") >= 0) {
				invoiceNo = invoiceNo.replace("\\", "_");
			}
			invoiceNo = invoiceNo.replaceAll("/", "_");
			invoiceNo = invoiceNo.replaceAll("-", "_");

			String filePath = unFilePath + invoiceNo + "_T2.pdf";
			String realPath = request.getSession().getServletContext().getRealPath(filePath);

			FileOutputStream streamOut = new FileOutputStream(realPath);
			PdfWriter pdf = PdfWriter.getInstance(documentPDF, streamOut);

			String templatePath = ResourceBundleUtils.getResource("INVOICE_PATTERN_PATH");
			String serialCode2 = listBookType.get(0).getSerialCode().trim();
			while (serialCode2.indexOf("\\") >= 0) {
				serialCode2 = serialCode2.replace("\\", "_");
			}
			serialCode2 = serialCode2.replaceAll("/", "_");
			serialCode2 = serialCode2.replaceAll("-", "_");
			if (printType == 1L) {
				templatePath = templatePath + serialCode2 + "_L1_T2.pdf";
			} else {
				templatePath = templatePath + serialCode2 + "_L2_T2.pdf";
			}

            //templatePath = templatePath + serialCode2 + ".pdf";
			String realTemplatePath = request.getSession().getServletContext().getRealPath(templatePath);
//            PdfReader pdfReader = new PdfReader("D:\\IT_2010A_CV.pdf");//duong link file pdf goc
			PdfReader pdfReader = new PdfReader(realTemplatePath);//duong link file pdf goc
			PdfStamper pdfStamper = new PdfStamper(pdfReader, streamOut);
			PdfContentByte cb = pdfStamper.getOverContent(1);
//            documentPDF.open();

//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            String strDate = dateFormat.format(invoiceSaleListBean.getCreatedate());
//            String[] arrStrDate = strDate.split("-");
			Long count = 1L;//Dung de hien thi so thu tu            
			Long step = 0L;//Vi tri toa do y khi in danh sach hang hoa & dich vu
			boolean isPromotion = false;//Co phai la giao dich khuyen mai
			boolean isTCDT = false;//Co phai la giao dich ban TCDT
			boolean checkPrint = false;
			double grantSubTotal = 0.0;  //GRANT_SUBTOTAL
			double discountAmount = 0.0;  //GRANT_SUBTOTAL

			//check co phai la giao dich ban khuyen mai
			for (SaleTransBean saleTrans : listSaleTrans) {
				if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION)) {
					isPromotion = true;
					break;
				}
			}

			Long[] lstSaleTransId = new Long[listSaleTrans.size()];
			int index = 0;

			//Lay danh sach sale_trans_id
			for (SaleTransBean saleTrans : listSaleTrans) {
				lstSaleTransId[index++] = saleTrans.getSaleTransId();
			}

			//Danh sach hang hoa co amount > 0 trong hoa don
			List<SaleTransDetailV2Bean> listSaleTransDetailBean = this.getSaleTransDetailList(lstSaleTransId, invoiceSaleListBean.getCreatedate());
			if (listSaleTransDetailBean == null || listSaleTransDetailBean.isEmpty()) {
				return "0";
			}

			for (SaleTransDetailV2Bean saleTransDetailBean : listSaleTransDetailBean) {

				isTCDT = false;//ductm6_fix loi in hoa don_20150212
				//Chia cho thue suat : vi luu trong DB la gia sau thue
				try {
					if (saleTransDetailBean.getVat() != null && !saleTransDetailBean.getVat().equals(0.0)) {
						saleTransDetailBean.setPrice(saleTransDetailBean.getPrice() / (1 + saleTransDetailBean.getVat() / 100.0));
						saleTransDetailBean.setAmount(saleTransDetailBean.getAmount() / (1 + saleTransDetailBean.getVat() / 100.0));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

//                grantSubTotal += saleTransDetailBean.getPrice() * saleTransDetailBean.getQuantity();
				grantSubTotal += saleTransDetailBean.getAmount();
				discountAmount += saleTransDetailBean.getDiscountAmount();

				//Kiem tra giao dich co ban TCDT hay khong
				if (!isTCDT && saleTransDetailBean.getStockModelCode() != null && saleTransDetailBean.getStockModelCode().trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
					isTCDT = true;
				}
                //Neu la hoa don khuyen mai => in them 1 dong HOA DON KHUYEN MAI KHONG THU TIEN
				//MZB : khong can in : isPrintPromotionReason = false
				if (isPrintPromotionReason && isPromotion && !checkPrint) {
					for (PrinterParam printerParam : listPrinterParam) {
						if (printerParam.getIsDetailField() != null && printerParam.getIsDetailField().equals(1L) && printerParam.getFieldName().equals(this.GOODS_NAME)) {
							printOneColumn(pdfStamper, this.STRING_PROMOTION, printerParam, step);
							step = step + yStep;
							checkPrint = true;
							break;
						}
					}
				}

				for (PrinterParam printerParam : listPrinterParam) {
					if (printerParam.getIsDetailField() != null && printerParam.getIsDetailField().equals(1L)) {
						//Chi tiet hang hoa
						if (printerParam.getFieldName().equals(this.GOODS_ORDER_NO)) {
							sttDiscountX = printerParam.getXCoordinates();
							printOneColumn(pdfStamper, count.toString(), printerParam, step);
						}
						if (printerParam.getFieldName().equals(this.GOODS_NAME)) {
							discountX = printerParam.getXCoordinates();
							printOneColumn(pdfStamper, saleTransDetailBean.getStockModelName(), printerParam, step);
						}
//                        if (printerParam.getFieldName().equals(this.GOODS_UNIT)) {
//                            printOneColumn(pdfStamper, saleTransDetailBean.getUnitName(), printerParam, step);
//                        }
						//So luong
						if (printerParam.getFieldName().equals(this.GOODS_QUANTITY) && saleTransDetailBean.getQuantity() != null) {
							if (!isTCDT) {
								printOneColumn(pdfStamper, NumberUtils.formatNumber(saleTransDetailBean.getQuantity()), printerParam, step);
							}
						}
						//Don gia chua thue
						if (printerParam.getFieldName().equals(this.GOODS_PRICE) && saleTransDetailBean.getPrice() != null) {
							if (!isTCDT) {
								printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(saleTransDetailBean.getPrice()), printerParam, step);
							}
						}
						//Thanh tien chua thue
						if (printerParam.getFieldName().equals(this.GOODS_AMOUNT) && saleTransDetailBean.getPrice() != null && saleTransDetailBean.getQuantity() != null) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(saleTransDetailBean.getQuantity() * saleTransDetailBean.getPrice()), printerParam, step);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(saleTransDetailBean.getAmount()), printerParam, step);
						}
					}
				}
				step = step + yStep;
				count++;
			}

			for (PrinterParam printerParam : listPrinterParam) {
				if (printerParam.getIsDetailField() == null || !printerParam.getIsDetailField().equals(1L)) {
					if (printerParam.getFieldName().equals(this.INVOICE_INVOICE_DATE)) {
						printOneColumn(pdfStamper, (new SimpleDateFormat("MMMM dd, yyyy", new Locale(LANGUAGE_DEFAULT, LANGUAGE_DEFAULT.toUpperCase()))).format(invoiceSaleListBean.getCreatedate()), printerParam, 0L);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_CUST_NAME)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 13;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getCustName(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals("ADDRESS2")) {
//                        printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
//                        continue;

                        //do dai truong dia chi tren 1 dong
						//khoang cach giua 2 dong cua truong dia chi
						if (printerParam.getWidth() == null
								|| printerParam.getHeight() == null
								|| printerParam.getWidth().equals(0L)
								|| printerParam.getHeight().equals(0L)) {
							printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
							continue;
						}

						if (invoiceSaleListBean.getAddress() == null || invoiceSaleListBean.getAddress().trim().equals("")) {
							continue;
						}

						if (invoiceSaleListBean.getAddress().trim().length() <= printerParam.getWidth().intValue()) {
							printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
						} else {
							String[] tmp = invoiceSaleListBean.getAddress().trim().split(" ");
							String tmp1 = "";
							String tmp2 = "";
							int i = 0;
							while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < printerParam.getWidth().intValue()) {
								tmp1 = tmp1 + " " + (tmp[i++]);
							}
							tmp2 = invoiceSaleListBean.getAddress().trim().substring(tmp1.length());
							printOneColumn(pdfStamper, tmp1, printerParam, 0L);
							printOneColumn(pdfStamper, tmp2, printerParam, printerParam.getHeight());
						}
					}
					if (printerParam.getFieldName().equals(this.INVOICE_TEL)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 13;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getIsdn(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_NUIT)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 13;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getTin(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_SERVICE_CODE)) {
						if (invoiceSaleListBean.getTelecomServiceId() != null && invoiceSaleListBean.getTelecomServiceId().equals(1L)) {
							printOneColumn(pdfStamper, "M001", printerParam, 0L);
						}
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_PAY_METHOD)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 20;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getPayMethodName(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_INVOICE_NUMBER)) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date date = new Date();
						String strDate = dateFormat.format(date);
						String invoiceNoNew = "";
						if (isPrintInvoiceId) {
							// tannh20180604 : bo invoiceNo con 7 chu so 
//                            invoiceNoNew = invoiceNo.substring(invoiceNo.length() - 7);
							printOneColumn(pdfStamper, invoiceSaleListBean.getInvoiceId().toString(), printerParam, 0L);
							// tannh20180604 : them barcode vao file pdf
							Barcode128 code128 = new Barcode128();
							code128.setCode(strDate.substring(0, 2) + invoiceSaleListBean.getInvoiceId().toString() + strDate.substring(3, 5));
							code128.setFont(null);
							PdfTemplate template1 = code128.createTemplateWithBarcode(cb, null, null);
							cb.addTemplate(template1, 420, 750);
						} else {
							// tannh20180604 : bo invoiceNo con 7 chu so 
							if (invoiceSaleListBean != null && invoiceSaleListBean.getInvoiceNo().length() > 7) {
								invoiceNoNew = invoiceSaleListBean.getInvoiceNo().substring(invoiceSaleListBean.getInvoiceNo().length() - 7);
							}
							printOneColumn(pdfStamper, invoiceNoNew, printerParam, 0L);
							// tannh20180604 : them barcode vao file pdf
							Barcode128 code128 = new Barcode128();
							code128.setCode(strDate.substring(0, 2) + invoiceNoNew + strDate.substring(3, 5));
							code128.setFont(null);
							PdfTemplate template1 = code128.createTemplateWithBarcode(cb, null, null);
							cb.addTemplate(template1, 420, 750);
						}
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_SUBTOTAL)) {
						if (!isPromotion) {
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(grantSubTotal), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "", printerParam, 0L);
						}

						continue;
					}

//                    if (printerParam.getFieldName().equals(this.INVOICE_DISCOUNT) && discountAmount > 0.0) {
					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_DISCOUNT) && discountAmount > 0.0) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getDiscount()), printerParam, 0L);
//                         Long yOld = printerParam.getYCoordinates();
//                        Long yNew = yOld - 13;
//                        printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(discountAmount), printerParam, 0L);
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_BEFORE_TAX)) {
//                        printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountNotTax()), printerParam, 0L);
						printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(grantSubTotal - discountAmount), printerParam, 0L);
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_TAX)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getTax()), printerParam, 0L);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount((grantSubTotal - discountAmount) * invoiceSaleListBean.getVAT() / 100), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "\\", printerParam, 0L);
						}
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_AFTER_TAX)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountTax()), printerParam, 0L);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount((grantSubTotal - discountAmount) * (1 + invoiceSaleListBean.getVAT() / 100)), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "", printerParam, 0L);
						}

						continue;
					}

//                    if (printerParam.getFieldName().equals(this.INVOICE_GRANT_DISCOUNT)) {
//                        if (!isPromotion && invoiceSaleListBean.getDiscount() != null && !invoiceSaleListBean.getDiscount().equals(0.0)) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getDiscount() * 1.17), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "", printerParam, 0L);
//                        }
//
//                        continue;
//                    }
//                    if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_PAYED)) {
//                        if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountTax()), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "\\", printerParam, 0L);
//                        }
//                        continue;
//                    }
//                    if (printerParam.getFieldName().equals(this.INVOICE_DEPOSIT)) {
//                        if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(0.0), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "", printerParam, 0L);
//                        }
//                        continue;
//                    }
					if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_BY_WORD)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, MultiLanguageNumberToWords.convert(invoiceSaleListBean.getAmountTax(), this.LANGUAGE_DEFAULT), printerParam, 0L);
							String inWord = MultiLanguageNumberToWords.convert(NumberUtils.roundNumber((grantSubTotal - discountAmount) * (1 + invoiceSaleListBean.getVAT() / 100)), this.LANGUAGE_DEFAULT);

                            //do dai truong dia chi tren 1 dong
							//khoang cach giua 2 dong cua truong dia chi
							if (printerParam.getWidth() == null
									|| printerParam.getHeight() == null
									|| printerParam.getWidth().equals(0L)
									|| printerParam.getHeight().equals(0L)) {
								printOneColumn(pdfStamper, inWord, printerParam, 0L);
								continue;
							}

							if (inWord.trim().length() <= printerParam.getWidth().intValue()) {
								printOneColumn(pdfStamper, inWord, printerParam, 0L);
							} else {
								String[] tmp = inWord.trim().split(" ");
								String tmp1 = "";
								String tmp2 = "";
								int i = 0;
								while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < printerParam.getWidth().intValue()) {
									tmp1 = tmp1 + " " + (tmp[i++]);
								}
								tmp2 = inWord.trim().substring(tmp1.length());
								printOneColumn(pdfStamper, tmp1, printerParam, 0L);
								printOneColumn(pdfStamper, tmp2, printerParam, printerParam.getHeight());
							}

                            //Xu ly theo cach moi : luu tham so trong DB
/* 
							 Long invoiceWordLength = 0L;
							 String invoiceWordLengthTmp = ResourceBundleUtils.getResource("INVOICE_WORD_LENGTH");
							 if (invoiceWordLengthTmp != null && !invoiceWordLengthTmp.trim().equals("")) {
							 invoiceWordLength = Long.valueOf(invoiceWordLengthTmp);
							 }
							 Long invoiceWordDistance = 0L;
							 String invoiceWordDistanceTmp = ResourceBundleUtils.getResource("INVOICE_WORD_DISTANCE");
							 if (invoiceWordDistanceTmp != null && !invoiceWordDistanceTmp.trim().equals("")) {
							 invoiceWordDistance = Long.valueOf(invoiceWordDistanceTmp);
							 }
							 if (inWord.length() <= invoiceWordLength.intValue()) {
							 printOneColumn(pdfStamper, inWord, printerParam, 0L);
							 } else {
							 String[] tmp = inWord.split(" ");
							 String tmp1 = "";
							 String tmp2 = "";
							 int i = 0;
							 while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < invoiceWordLength.intValue()) {
							 tmp1 = tmp1 + " " + (tmp[i++]);
							 }
							 tmp2 = inWord.substring(tmp1.length());
							 printOneColumn(pdfStamper, tmp1, printerParam, 0L);
							 printOneColumn(pdfStamper, tmp2, printerParam, invoiceWordDistance);
							 }*/
						} else {
							printOneColumn(pdfStamper, "\\", printerParam, 0L);
						}

						continue;
					}
				}
			}
			pdfStamper.close();

			System.out.println("END printSaleTransInvoice ");
			return filePath;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "0";
		}
	}

	public String printSaleTransInvoiceT3(Long invoiceUsedId, Long invoiceType, Long printType) throws Exception {
		System.out.println("START printSaleTransInvoice ");
		HttpServletRequest request = getRequest();
		try {
			xAmplitude = 0L;//do lech truc x khi in tren OS Ubuntu
			yAmplitude = 0L;//do lech truc y khi in tren OS Ubuntu

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
			InvoiceSaleListBean invoiceSaleListBean = this.getInvoiceUsedDetail(invoiceUsedId);
			if (invoiceSaleListBean == null
					|| invoiceSaleListBean.getSerialNo() == null
					|| invoiceSaleListBean.getSerialNo().trim().equals("")) {
				return "0";//Khong tim thay hoa don
			}
			String serialNo = invoiceSaleListBean.getSerialNo();

			//Danh sach cau hinh hoa don
			List<PrinterParam> listPrinterParam = printerParamDAO.findByProperty(printerParamDAO.SERIAL_CODE, serialNo);
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
			if (invoiceType.compareTo(3L) == 0) {
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
//            invoiceNo = invoiceNo.replaceAll("\\", "_").replaceAll("/", "_");

			while (invoiceNo.indexOf("\\") >= 0) {
				invoiceNo = invoiceNo.replace("\\", "_");
			}
			invoiceNo = invoiceNo.replaceAll("/", "_");
			invoiceNo = invoiceNo.replaceAll("-", "_");

			String filePath = unFilePath + invoiceNo + "_T3.pdf";
			String realPath = request.getSession().getServletContext().getRealPath(filePath);

			FileOutputStream streamOut = new FileOutputStream(realPath);
			PdfWriter pdf = PdfWriter.getInstance(documentPDF, streamOut);

			String templatePath = ResourceBundleUtils.getResource("INVOICE_PATTERN_PATH");
			String serialCode2 = listBookType.get(0).getSerialCode().trim();
			while (serialCode2.indexOf("\\") >= 0) {
				serialCode2 = serialCode2.replace("\\", "_");
			}
			serialCode2 = serialCode2.replaceAll("/", "_");
			serialCode2 = serialCode2.replaceAll("-", "_");
			if (printType == 1L) {
				templatePath = templatePath + serialCode2 + "_L1_T3.pdf";
			} else {
				templatePath = templatePath + serialCode2 + "_L2_T3.pdf";
			}

            //templatePath = templatePath + serialCode2 + ".pdf";
			String realTemplatePath = request.getSession().getServletContext().getRealPath(templatePath);
//            PdfReader pdfReader = new PdfReader("D:\\IT_2010A_CV.pdf");//duong link file pdf goc
			PdfReader pdfReader = new PdfReader(realTemplatePath);//duong link file pdf goc
			PdfStamper pdfStamper = new PdfStamper(pdfReader, streamOut);
			PdfContentByte cb = pdfStamper.getOverContent(1);
//            documentPDF.open();

//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            String strDate = dateFormat.format(invoiceSaleListBean.getCreatedate());
//            String[] arrStrDate = strDate.split("-");
			Long count = 1L;//Dung de hien thi so thu tu            
			Long step = 0L;//Vi tri toa do y khi in danh sach hang hoa & dich vu
			boolean isPromotion = false;//Co phai la giao dich khuyen mai
			boolean isTCDT = false;//Co phai la giao dich ban TCDT
			boolean checkPrint = false;
			double grantSubTotal = 0.0;  //GRANT_SUBTOTAL
			double discountAmount = 0.0;  //GRANT_SUBTOTAL

			//check co phai la giao dich ban khuyen mai
			for (SaleTransBean saleTrans : listSaleTrans) {
				if (saleTrans.getSaleTransType().equals(Constant.SALE_TRANS_TYPE_PROMOTION)) {
					isPromotion = true;
					break;
				}
			}

			Long[] lstSaleTransId = new Long[listSaleTrans.size()];
			int index = 0;

			//Lay danh sach sale_trans_id
			for (SaleTransBean saleTrans : listSaleTrans) {
				lstSaleTransId[index++] = saleTrans.getSaleTransId();
			}

			//Danh sach hang hoa co amount > 0 trong hoa don
			List<SaleTransDetailV2Bean> listSaleTransDetailBean = this.getSaleTransDetailList(lstSaleTransId, invoiceSaleListBean.getCreatedate());
			if (listSaleTransDetailBean == null || listSaleTransDetailBean.isEmpty()) {
				return "0";
			}

			for (SaleTransDetailV2Bean saleTransDetailBean : listSaleTransDetailBean) {

				isTCDT = false;//ductm6_fix loi in hoa don_20150212
				//Chia cho thue suat : vi luu trong DB la gia sau thue
				try {
					if (saleTransDetailBean.getVat() != null && !saleTransDetailBean.getVat().equals(0.0)) {
						saleTransDetailBean.setPrice(saleTransDetailBean.getPrice() / (1 + saleTransDetailBean.getVat() / 100.0));
						saleTransDetailBean.setAmount(saleTransDetailBean.getAmount() / (1 + saleTransDetailBean.getVat() / 100.0));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

//                grantSubTotal += saleTransDetailBean.getPrice() * saleTransDetailBean.getQuantity();
				grantSubTotal += saleTransDetailBean.getAmount();
				discountAmount += saleTransDetailBean.getDiscountAmount();

				//Kiem tra giao dich co ban TCDT hay khong
				if (!isTCDT && saleTransDetailBean.getStockModelCode() != null && saleTransDetailBean.getStockModelCode().trim().equals(Constant.STOCK_MODEL_CODE_TCDT)) {
					isTCDT = true;
				}
                //Neu la hoa don khuyen mai => in them 1 dong HOA DON KHUYEN MAI KHONG THU TIEN
				//MZB : khong can in : isPrintPromotionReason = false
				if (isPrintPromotionReason && isPromotion && !checkPrint) {
					for (PrinterParam printerParam : listPrinterParam) {
						if (printerParam.getIsDetailField() != null && printerParam.getIsDetailField().equals(1L) && printerParam.getFieldName().equals(this.GOODS_NAME)) {
							printOneColumn(pdfStamper, this.STRING_PROMOTION, printerParam, step);
							step = step + yStep;
							checkPrint = true;
							break;
						}
					}
				}

				for (PrinterParam printerParam : listPrinterParam) {
					if (printerParam.getIsDetailField() != null && printerParam.getIsDetailField().equals(1L)) {
						//Chi tiet hang hoa
						if (printerParam.getFieldName().equals(this.GOODS_ORDER_NO)) {
							sttDiscountX = printerParam.getXCoordinates();
							printOneColumn(pdfStamper, count.toString(), printerParam, step);
						}
						if (printerParam.getFieldName().equals(this.GOODS_NAME)) {
							discountX = printerParam.getXCoordinates();
							printOneColumn(pdfStamper, saleTransDetailBean.getStockModelName(), printerParam, step);
						}
//                        if (printerParam.getFieldName().equals(this.GOODS_UNIT)) {
//                            printOneColumn(pdfStamper, saleTransDetailBean.getUnitName(), printerParam, step);
//                        }
						//So luong
						if (printerParam.getFieldName().equals(this.GOODS_QUANTITY) && saleTransDetailBean.getQuantity() != null) {
							if (!isTCDT) {
								printOneColumn(pdfStamper, NumberUtils.formatNumber(saleTransDetailBean.getQuantity()), printerParam, step);
							}
						}
						//Don gia chua thue
						if (printerParam.getFieldName().equals(this.GOODS_PRICE) && saleTransDetailBean.getPrice() != null) {
							if (!isTCDT) {
								printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(saleTransDetailBean.getPrice()), printerParam, step);
							}
						}
						//Thanh tien chua thue
						if (printerParam.getFieldName().equals(this.GOODS_AMOUNT) && saleTransDetailBean.getPrice() != null && saleTransDetailBean.getQuantity() != null) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(saleTransDetailBean.getQuantity() * saleTransDetailBean.getPrice()), printerParam, step);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(saleTransDetailBean.getAmount()), printerParam, step);
						}
					}
				}
				step = step + yStep;
				count++;
			}

			for (PrinterParam printerParam : listPrinterParam) {
				if (printerParam.getIsDetailField() == null || !printerParam.getIsDetailField().equals(1L)) {
					if (printerParam.getFieldName().equals(this.INVOICE_INVOICE_DATE)) {
						printOneColumn(pdfStamper, (new SimpleDateFormat("MMMM dd, yyyy", new Locale(LANGUAGE_DEFAULT, LANGUAGE_DEFAULT.toUpperCase()))).format(invoiceSaleListBean.getCreatedate()), printerParam, 0L);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_CUST_NAME)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 13;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getCustName(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals("ADDRESS3")) {
//                        printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
//                        continue;

                        //do dai truong dia chi tren 1 dong
						//khoang cach giua 2 dong cua truong dia chi
						if (printerParam.getWidth() == null
								|| printerParam.getHeight() == null
								|| printerParam.getWidth().equals(0L)
								|| printerParam.getHeight().equals(0L)) {
							printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
							continue;
						}

						if (invoiceSaleListBean.getAddress() == null || invoiceSaleListBean.getAddress().trim().equals("")) {
							continue;
						}

						if (invoiceSaleListBean.getAddress().trim().length() <= printerParam.getWidth().intValue()) {
							printOneColumn(pdfStamper, invoiceSaleListBean.getAddress(), printerParam, 0L);
						} else {
							String[] tmp = invoiceSaleListBean.getAddress().trim().split(" ");
							String tmp1 = "";
							String tmp2 = "";
							int i = 0;
							while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < printerParam.getWidth().intValue()) {
								tmp1 = tmp1 + " " + (tmp[i++]);
							}
							tmp2 = invoiceSaleListBean.getAddress().trim().substring(tmp1.length());
							printOneColumn(pdfStamper, tmp1, printerParam, 0L);
							printOneColumn(pdfStamper, tmp2, printerParam, printerParam.getHeight());
						}
					}
					if (printerParam.getFieldName().equals(this.INVOICE_TEL)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 13;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getIsdn(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_NUIT)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 13;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getTin(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_SERVICE_CODE)) {
						if (invoiceSaleListBean.getTelecomServiceId() != null && invoiceSaleListBean.getTelecomServiceId().equals(1L)) {
							printOneColumn(pdfStamper, "M001", printerParam, 0L);
						}
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_PAY_METHOD)) {
						Long yOld = printerParam.getYCoordinates();
						Long yNew = yOld - 20;
						printerParam.setYCoordinates(yNew);
						printOneColumn(pdfStamper, invoiceSaleListBean.getPayMethodName(), printerParam, 0L);
						printerParam.setYCoordinates(yOld);
						continue;
					}
					if (printerParam.getFieldName().equals(this.INVOICE_INVOICE_NUMBER)) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date date = new Date();
						String strDate = dateFormat.format(date);
						String invoiceNoNew = "";
						if (isPrintInvoiceId) {
							// tannh20180604 : bo invoiceNo con 7 chu so 
//                            invoiceNoNew = invoiceNo.substring(invoiceNo.length() - 7);
							printOneColumn(pdfStamper, invoiceSaleListBean.getInvoiceId().toString(), printerParam, 0L);
							// tannh20180604 : them barcode vao file pdf
							Barcode128 code128 = new Barcode128();
							code128.setCode(strDate.substring(0, 2) + invoiceSaleListBean.getInvoiceId().toString() + strDate.substring(3, 5));
							code128.setFont(null);
							PdfTemplate template1 = code128.createTemplateWithBarcode(cb, null, null);
							cb.addTemplate(template1, 420, 750);
						} else {
							// tannh20180604 : bo invoiceNo con 7 chu so 
							if (invoiceSaleListBean != null && invoiceSaleListBean.getInvoiceNo().length() > 7) {
								invoiceNoNew = invoiceSaleListBean.getInvoiceNo().substring(invoiceSaleListBean.getInvoiceNo().length() - 7);
							}
							printOneColumn(pdfStamper, invoiceNoNew, printerParam, 0L);
							// tannh20180604 : them barcode vao file pdf
							Barcode128 code128 = new Barcode128();
							code128.setCode(strDate.substring(0, 2) + invoiceNoNew + strDate.substring(3, 5));
							code128.setFont(null);
							PdfTemplate template1 = code128.createTemplateWithBarcode(cb, null, null);
							cb.addTemplate(template1, 420, 750);
						}
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_SUBTOTAL)) {
						if (!isPromotion) {
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(grantSubTotal), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "", printerParam, 0L);
						}

						continue;
					}

//                    if (printerParam.getFieldName().equals(this.INVOICE_DISCOUNT) && discountAmount > 0.0) {
					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_DISCOUNT) && discountAmount > 0.0) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getDiscount()), printerParam, 0L);
						printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(discountAmount), printerParam, 0L);
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_BEFORE_TAX)) {
//                        printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountNotTax()), printerParam, 0L);
						printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount(grantSubTotal - discountAmount), printerParam, 0L);
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_TAX)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getTax()), printerParam, 0L);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount((grantSubTotal - discountAmount) * invoiceSaleListBean.getVAT() / 100), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "\\", printerParam, 0L);
						}
						continue;
					}

					if (printerParam.getFieldName().equals(this.INVOICE_GRANT_AMOUNT_AFTER_TAX)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountTax()), printerParam, 0L);
							printOneColumn(pdfStamper, NumberUtils.rounđAndFormatAmount((grantSubTotal - discountAmount) * (1 + invoiceSaleListBean.getVAT() / 100)), printerParam, 0L);
						} else {
							printOneColumn(pdfStamper, "", printerParam, 0L);
						}

						continue;
					}

//                    if (printerParam.getFieldName().equals(this.INVOICE_GRANT_DISCOUNT)) {
//                        if (!isPromotion && invoiceSaleListBean.getDiscount() != null && !invoiceSaleListBean.getDiscount().equals(0.0)) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getDiscount() * 1.17), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "", printerParam, 0L);
//                        }
//
//                        continue;
//                    }
//                    if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_PAYED)) {
//                        if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(invoiceSaleListBean.getAmountTax()), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "\\", printerParam, 0L);
//                        }
//                        continue;
//                    }
//                    if (printerParam.getFieldName().equals(this.INVOICE_DEPOSIT)) {
//                        if (!isPromotion) {
//                            printOneColumn(pdfStamper, NumberUtils.rounÄ‘AndFormatAmount(0.0), printerParam, 0L);
//                        } else {
//                            printOneColumn(pdfStamper, "", printerParam, 0L);
//                        }
//                        continue;
//                    }
					if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_BY_WORD)) {
						if (!isPromotion) {
//                            printOneColumn(pdfStamper, MultiLanguageNumberToWords.convert(invoiceSaleListBean.getAmountTax(), this.LANGUAGE_DEFAULT), printerParam, 0L);
							String inWord = MultiLanguageNumberToWords.convert(NumberUtils.roundNumber((grantSubTotal - discountAmount) * (1 + invoiceSaleListBean.getVAT() / 100)), this.LANGUAGE_DEFAULT);

                            //do dai truong dia chi tren 1 dong
							//khoang cach giua 2 dong cua truong dia chi
							if (printerParam.getWidth() == null
									|| printerParam.getHeight() == null
									|| printerParam.getWidth().equals(0L)
									|| printerParam.getHeight().equals(0L)) {
								printOneColumn(pdfStamper, inWord, printerParam, 0L);
								continue;
							}

							if (inWord.trim().length() <= printerParam.getWidth().intValue()) {
								printOneColumn(pdfStamper, inWord, printerParam, 0L);
							} else {
								String[] tmp = inWord.trim().split(" ");
								String tmp1 = "";
								String tmp2 = "";
								int i = 0;
								while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < printerParam.getWidth().intValue()) {
									tmp1 = tmp1 + " " + (tmp[i++]);
								}
								tmp2 = inWord.trim().substring(tmp1.length());
								printOneColumn(pdfStamper, tmp1, printerParam, 0L);
								printOneColumn(pdfStamper, tmp2, printerParam, printerParam.getHeight());
							}

                            //Xu ly theo cach moi : luu tham so trong DB
/* 
							 Long invoiceWordLength = 0L;
							 String invoiceWordLengthTmp = ResourceBundleUtils.getResource("INVOICE_WORD_LENGTH");
							 if (invoiceWordLengthTmp != null && !invoiceWordLengthTmp.trim().equals("")) {
							 invoiceWordLength = Long.valueOf(invoiceWordLengthTmp);
							 }
							 Long invoiceWordDistance = 0L;
							 String invoiceWordDistanceTmp = ResourceBundleUtils.getResource("INVOICE_WORD_DISTANCE");
							 if (invoiceWordDistanceTmp != null && !invoiceWordDistanceTmp.trim().equals("")) {
							 invoiceWordDistance = Long.valueOf(invoiceWordDistanceTmp);
							 }
							 if (inWord.length() <= invoiceWordLength.intValue()) {
							 printOneColumn(pdfStamper, inWord, printerParam, 0L);
							 } else {
							 String[] tmp = inWord.split(" ");
							 String tmp1 = "";
							 String tmp2 = "";
							 int i = 0;
							 while (i < tmp.length - 1 && (tmp1 + " " + tmp[i + 1]).length() < invoiceWordLength.intValue()) {
							 tmp1 = tmp1 + " " + (tmp[i++]);
							 }
							 tmp2 = inWord.substring(tmp1.length());
							 printOneColumn(pdfStamper, tmp1, printerParam, 0L);
							 printOneColumn(pdfStamper, tmp2, printerParam, invoiceWordDistance);
							 }*/
						} else {
							printOneColumn(pdfStamper, "\\", printerParam, 0L);
						}

						continue;
					}
				}
			}
			pdfStamper.close();

			System.out.println("END printSaleTransInvoice ");
			return filePath;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "0";
		}
	}

	private void printOneColumn(PdfStamper pdf, String inputString, PrinterParam printerParam, Long step) {
		try {
			HttpServletRequest request = getRequest();
			if (inputString == null || inputString.equals("")) {
				return;
			}

			Long xCo = printerParam.getXCoordinates();
//            Long yCo = Long.parseLong(String.valueOf((int) heightPage)) - (printerParam.getYCoordinates() + step);
			Long yCo = (printerParam.getYCoordinates() - step);
//            if(printerParam.getFieldName().equals(this.INVOICE_CUST_NAME) || printerParam.getFieldName().equals(this.INVOICE_ADDRESS)
//                    ||  printerParam.getFieldName().equals(this.INVOICE_TEL) || printerParam.getFieldName().equals(this.INVOICE_NUIT)){
//                yCo = yCo -11;
//            }

			xCo += xAmplitude;
			yCo += yAmplitude;

//            PdfContentByte cb = pdf.getDirectContent();
			int count = 0;
			while (true) {
				if (++count > 1) {//in 1 trang
					break;
				}

				PdfContentByte cb = pdf.getOverContent(count);
				Integer thisFontSize = 10;

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

//                
				//             if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_BY_WORD)){                    
				//                  Font a = bf.get;
                // Font.createFont(thisFont, BaseFont.TIMES_ITALIC, BaseFont.EMBEDDED);                     
				//           }
				cb.beginText();

				cb.moveTextWithLeading(0, 0);

				cb.setFontAndSize(bf, thisFontSize);
                //if (printerParam.getFieldName().equals(this.INVOICE_TOTAL_BY_WORD)){
				//Font times = new Font(bf, 12, Font.ITALIC, Color.RED);
				//cb.setFontAndSize(times, thisFontSize);
				//}

				if (printerParam.getFieldName().equals(GOODS_PRICE)
						|| printerParam.getFieldName().equals(INVOICE_GRANT_SUBTOTAL)
						|| printerParam.getFieldName().equals(INVOICE_GRANT_DISCOUNT)
						|| printerParam.getFieldName().equals(GOODS_QUANTITY)
						|| printerParam.getFieldName().equals(GOODS_AMOUNT)
						|| printerParam.getFieldName().equals(INVOICE_GRANT_AMOUNT_BEFORE_TAX)
						|| printerParam.getFieldName().equals(INVOICE_GRANT_AMOUNT_TAX)
						|| printerParam.getFieldName().equals(INVOICE_GRANT_AMOUNT_AFTER_TAX)
						|| printerParam.getFieldName().equals(INVOICE_TOTAL_PAYED)) {
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, inputString, xCo, yCo, 0);//
//                    if (printerParam.getFieldName().equals(INVOICE_DISCOUNT)) {
//                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, "1", sttDiscountX, yCo, 0);
//                        cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.STRING_DISCOUNT, discountX, yCo, 0);
//                    }
				} else if (printerParam.getFieldName().equals(this.INVOICE_DISCOUNT)) {
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, inputString, xCo, yCo, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.STRING_DISCOUNT, discountX, yCo, 0);
				} else if (printerParam.getFieldName().equals(this.INVOICE_PROMOTION)) {
					cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, inputString, xCo, yCo, 0);
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, this.STRING_PROMOTION, discountX, yCo, 0);
				} else if (printerParam.getFieldName().equals(this.GOODS_ORDER_NO)) {
					cb.showTextAligned(PdfContentByte.ALIGN_CENTER, inputString, xCo, yCo, 0);
				} else {
					cb.showTextAligned(PdfContentByte.ALIGN_LEFT, inputString, xCo, yCo, 0);
				}
				cb.endText();
			}

		} catch (DocumentException ex) {
			ex.printStackTrace();
			Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/*
	 * Ap dung cho in phieu thu/phieu chi tien dat coc
	 */
	private void printOneColumn(PdfStamper pdf, String inputString, PrinterParam printerParam) {
		try {
			HttpServletRequest request = getRequest();
			if (inputString == null || inputString.equals("")) {
				return;
			}
			int count = 0;
			while (true) {
				if (++count > 3) {//in 3 trang
					break;
				}
				PdfContentByte cb = pdf.getOverContent(count);
				Integer thisFontSize = 10;
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
				cb.moveTextWithLeading(0, 0);
				cb.setFontAndSize(bf, thisFontSize);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, inputString, printerParam.getXCoordinates(), printerParam.getYCoordinates(), 0);
				cb.endText();
			}
		} catch (DocumentException ex) {
			ex.printStackTrace();
			Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public String printReceiptNo(Long receiptId) throws Exception {
		HttpServletRequest request = getRequest();
		try {
			ReceiptExpenseDAO reDAO = new ReceiptExpenseDAO();
			reDAO.setSession(this.getSession());
			ReceiptExpense re = reDAO.findById(receiptId);
			if (re != null) {
				String custName = "";
				String custAddress = "";
				String reason = "";

				DepositDAO depositDAO = new DepositDAO();
				depositDAO.setSession(this.getSession());
				List<Deposit> deposit = depositDAO.findByReceiptId(receiptId);
				if (deposit != null && !deposit.isEmpty()) {
					custName = deposit.get(0).getCustName();
					custAddress = deposit.get(0).getAddress();
					ReasonDAO reasonDAO = new ReasonDAO();
					reasonDAO.setSession(this.getSession());
					Reason rs = reasonDAO.findById(deposit.get(0).getReasonId());
					if (rs != null) {
						reason = rs.getReasonName();
					}
				}
                //Tao file FDF
				//Document documentPDF = new Document(new Rectangle(0, 0, widthPage, heightPage));
				//Duong dan download file PDF
				String unFilePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

				String exportFileName = re.getReceiptNo();//lay ma phieu thu/chi lam ten file
				if (exportFileName != null && !"".equals(exportFileName)) {
					exportFileName = exportFileName.trim();
					while (exportFileName.indexOf("\\") >= 0) {
						exportFileName = exportFileName.replace("\\", "_");
					}
					exportFileName = exportFileName.replaceAll("/", "_");
					exportFileName = exportFileName.replaceAll("-", "_");
				}
				String filePath = unFilePath + exportFileName + ".pdf";
				String realPath = request.getSession().getServletContext().getRealPath(filePath);
				FileOutputStream streamOut = new FileOutputStream(realPath);
				//PdfWriter pdf = PdfWriter.getInstance(documentPDF, streamOut);

				String serialCode = re.getSerialCode();
				if (serialCode != null && !"".equals(serialCode)) {
					serialCode = serialCode.trim();
					while (serialCode.indexOf("\\") >= 0) {
						serialCode = serialCode.replace("\\", "_");
					}
					serialCode = serialCode.replaceAll("/", "_");
					serialCode = serialCode.replaceAll("-", "_");
				}
				serialCode = serialCode;//+ "_" + locale.toString();
				//duong dan file template
				String templateFilePath = ResourceBundleUtils.getResource("INVOICE_PATTERN_PATH");
				templateFilePath = templateFilePath + serialCode + ".pdf";
				String realTemplateFilePath = request.getSession().getServletContext().getRealPath(templateFilePath);
				PdfReader pdfReader = new PdfReader(realTemplateFilePath);
				PdfStamper pdfStamper = new PdfStamper(pdfReader, streamOut);

				//Danh sach cau hinh phieu thu/chi
				PrinterParamDAO printerParamDAO = new PrinterParamDAO();
				List<PrinterParam> lstPrinterParam = printerParamDAO.findByProperty(printerParamDAO.SERIAL_CODE, re.getSerialCode());
				if (lstPrinterParam != null && !lstPrinterParam.isEmpty()) {
					for (PrinterParam printerParam : lstPrinterParam) {
						if ("SERIAL_CODE".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, re.getSerialCode(), printerParam);
							continue;
						}
						if ("RECEIPT_NO".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, re.getReceiptNo(), printerParam);
							continue;
						}
						if ("RECEIPT_DATE".equals(printerParam.getFieldName())) {
							String receiptDate = getText("receiptExpense.createDate");
							receiptDate = receiptDate.trim() + " " + (new SimpleDateFormat("MMMM dd, yyyy", new Locale(LANGUAGE_DEFAULT, LANGUAGE_DEFAULT.toUpperCase()))).format(re.getReceiptDate());
							printOneColumn_Receipt(pdfStamper, receiptDate, printerParam);
							continue;
						}

						if ("RECEIPT_DATE_DAY".equals(printerParam.getFieldName())) {
							String receiptDate = (new SimpleDateFormat("dd")).format(re.getReceiptDate());
							printOneColumn_Receipt(pdfStamper, receiptDate, printerParam);
							continue;
						}
						if ("RECEIPT_DATE_MONTH".equals(printerParam.getFieldName())) {
							String receiptDate = (new SimpleDateFormat("MM")).format(re.getReceiptDate());
							printOneColumn_Receipt(pdfStamper, receiptDate, printerParam);
							continue;
						}
						if ("RECEIPT_DATE_YEAR".equals(printerParam.getFieldName())) {
							String receiptDate = (new SimpleDateFormat("yyyy")).format(re.getReceiptDate());
							printOneColumn_Receipt(pdfStamper, receiptDate, printerParam);
							continue;
						}

						if ("CUST_NAME".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, custName, printerParam);
							continue;
						}
						if ("ADDRESS".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, custAddress, printerParam);
							continue;
						}
						if ("REASON".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, reason, printerParam);
							continue;
						}
						if ("TOTAL_PAYED".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, NumberUtils.rounđAndFormatAmount(re.getAmount()), printerParam);
							continue;
						}
						if ("TOTAL_BY_WORD".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, MultiLanguageNumberToWords.convert(NumberUtils.roundNumber(re.getAmount()), this.LANGUAGE_DEFAULT), printerParam);
							continue;
						}
						if ("TOTAL_FINAL_BY_WORD".equals(printerParam.getFieldName())) {
							printOneColumn_Receipt(pdfStamper, MultiLanguageNumberToWords.convert(NumberUtils.roundNumber(re.getAmount()), this.LANGUAGE_DEFAULT), printerParam);
							continue;
						}
					}
				}
				pdfStamper.close();

				return request.getContextPath() + filePath;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
		return "";
	}

	private void printOneColumn_Receipt(PdfStamper pdf, String inputString, PrinterParam printerParam) {
		try {
			HttpServletRequest request = getRequest();
			if (inputString == null || inputString.equals("")) {
				return;
			}
			PdfContentByte cb = pdf.getOverContent(1);
			Integer thisFontSize = 10;
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
			cb.moveTextWithLeading(0, 0);
			cb.setFontAndSize(bf, thisFontSize);
			cb.showTextAligned(PdfContentByte.ALIGN_LEFT, inputString, printerParam.getXCoordinates(), printerParam.getYCoordinates(), 0);
			cb.endText();

			Long yDistance = 0L;
			String yDistanceTemp = ResourceBundleUtils.getResource("Y_DISTANCE");
			if (yDistanceTemp != null && !yDistanceTemp.trim().equals("")) {
				yDistance = Long.valueOf(yDistanceTemp);
			}
			if (yDistance != null && !yDistance.equals(0L)) {
				cb.beginText();
				cb.moveTextWithLeading(0, 0);
				cb.setFontAndSize(bf, thisFontSize);
				cb.showTextAligned(PdfContentByte.ALIGN_LEFT, inputString, printerParam.getXCoordinates(), printerParam.getYCoordinates() - yDistance, 0);
				cb.endText();
			}

		} catch (DocumentException ex) {
			ex.printStackTrace();
			Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(InvoicePrinterDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
