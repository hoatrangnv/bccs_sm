package com.viettel.security.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.RequestBorrowCash;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class PdfUtils {

    private Document document;

    public PdfUtils(Document document, Logger logger) {
        this.document = document;
    }

    public byte[] getBytes(String fileName)
            throws FileNotFoundException, IOException {
        byte[] buffer = new byte[4096];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytes = 0;
        while ((bytes = bis.read(buffer, 0, buffer.length)) > 0) {
            baos.write(buffer, 0, bytes);
        }
        baos.close();
        bis.close();
        return baos.toByteArray();
    }

    public String createRequestBorrowCash(RequestBorrowCash request, String fileName, UserToken userToken, HttpServletRequest servletRequest) {
        System.out.println("start create file pdf, fileName: " + fileName);
        DecimalFormat dfnd = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
        dfnd.applyPattern("#,###,###,###");

        Font header = FontFactory.getFont("Times", 14.0F, Font.BOLD, new CMYKColor(0, 0, 0, 255));

        Font boldFont = FontFactory.getFont("Times", 10.0F, Font.BOLD, new CMYKColor(0, 0, 0, 255));


        Font normalFont = FontFactory.getFont("Times", 8.0F, Font.NORMAL, new CMYKColor(0, 0, 0, 255));

        Font content = FontFactory.getFont("Times", 8.0F, Font.NORMAL, BaseColor.BLACK);
        Font contentItalic = FontFactory.getFont("Times", 8.0F, Font.ITALIC, BaseColor.BLACK);

        String unFilePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
        String filePath = unFilePath + fileName;
        String realPath = servletRequest.getSession().getServletContext().getRealPath(filePath);
//        File myFile = new File(path);
        System.out.println("Real path: " + realPath);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(realPath);
            PdfWriter.getInstance(document, output);
            document.open();
            System.out.println("Start create pdfCell");
//            Image imageLogo = Image.getInstance(getBytes("D:\\Restart\\WEB_SM\\etc\\logo_movitel.png"));
            Image imageLogo = Image.getInstance(getBytes("../etc/logo_movitel.png"));
            imageLogo.scaleAbsolute(120.0f, 40.0f);
            PdfPCell movitelLogo = new PdfPCell(imageLogo);
            movitelLogo.setPaddingTop(15.0F);
            movitelLogo.setBorderColor(BaseColor.WHITE);
            movitelLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
            movitelLogo.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell tmpSequence = new PdfPCell(new Paragraph("", boldFont));
            tmpSequence.setBorderColor(BaseColor.WHITE);
            tmpSequence.setPaddingLeft(5.0F);
            tmpSequence.setHorizontalAlignment(Element.ALIGN_LEFT);
            tmpSequence.setVerticalAlignment(Element.ALIGN_MIDDLE);
//
            PdfPCell sequence = new PdfPCell(new Paragraph("No.   :          " + request.getRequestId() + "\nDebit:          " + dfnd.format(request.getAmount()) + " MZN", boldFont));
            sequence.setBorderColor(BaseColor.WHITE);
            sequence.setPaddingLeft(5.0F);
            sequence.setHorizontalAlignment(Element.ALIGN_LEFT);
            sequence.setVerticalAlignment(Element.ALIGN_MIDDLE);


            PdfPCell title = new PdfPCell(new Paragraph("BORROW MONEY REQUEST", header));
            title.setBorderColor(BaseColor.WHITE);
            title.setPaddingLeft(5.0F);
            title.setHorizontalAlignment(Element.ALIGN_CENTER);
            title.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell bookKeeping = new PdfPCell(new Paragraph("(Book Keeping)", normalFont));
            bookKeeping.setBorderColor(BaseColor.WHITE);
            bookKeeping.setPaddingBottom(15.0F);
            bookKeeping.setHorizontalAlignment(Element.ALIGN_CENTER);
            bookKeeping.setVerticalAlignment(Element.ALIGN_MIDDLE);

            PdfPCell titleTable1 = new PdfPCell(new Paragraph("MOVITEL SA.", boldFont));
            titleTable1.setBorderColor(BaseColor.WHITE);
            titleTable1.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleTable1.setVerticalAlignment(Element.ALIGN_CENTER);
            titleTable1.setPaddingBottom(5.0F);

            PdfPCell titleTable1Date = new PdfPCell(new Paragraph(request.getCreateDate(), normalFont));
            titleTable1Date.setBorderColor(BaseColor.WHITE);
            titleTable1Date.setHorizontalAlignment(Element.ALIGN_LEFT);
            titleTable1Date.setVerticalAlignment(Element.ALIGN_CENTER);
            titleTable1Date.setPaddingBottom(5.0F);

            PdfPCell name = new PdfPCell(new Paragraph("Name:", content));
            name.setBorderColor(BaseColor.WHITE);
            name.setPaddingLeft(10.0F);
            name.setPaddingBottom(5.0F);
            name.setHorizontalAlignment(Element.ALIGN_LEFT);
            name.setVerticalAlignment(Element.ALIGN_CENTER);

            PdfPCell nameContent = new PdfPCell(new Paragraph(request.getName(), content));
            nameContent.setBorderColor(BaseColor.WHITE);
            nameContent.setPaddingLeft(10.0F);
            nameContent.setHorizontalAlignment(Element.ALIGN_LEFT);
            nameContent.setVerticalAlignment(Element.ALIGN_CENTER);
            nameContent.setPaddingBottom(5.0F);

            PdfPCell staffCode = new PdfPCell(new Paragraph("User BCCS:", content));
            staffCode.setBorderColor(BaseColor.WHITE);
            staffCode.setPaddingLeft(10.0F);
            staffCode.setPaddingBottom(5.0F);
            staffCode.setHorizontalAlignment(Element.ALIGN_LEFT);
            staffCode.setVerticalAlignment(Element.ALIGN_CENTER);

            PdfPCell staffCodeContent = new PdfPCell(new Paragraph(request.getStaffCode(), content));
            staffCodeContent.setBorderColor(BaseColor.WHITE);
            staffCodeContent.setPaddingLeft(10.0F);
            staffCodeContent.setHorizontalAlignment(Element.ALIGN_LEFT);
            staffCodeContent.setVerticalAlignment(Element.ALIGN_CENTER);
            staffCodeContent.setPaddingBottom(5.0F);

            PdfPCell address = new PdfPCell(new Paragraph("Address:", content));
            address.setBorderColor(BaseColor.WHITE);
            address.setPaddingLeft(10.0F);
            address.setPaddingBottom(5.0F);
            address.setHorizontalAlignment(Element.ALIGN_LEFT);
            address.setVerticalAlignment(Element.ALIGN_CENTER);

            PdfPCell addressContent = new PdfPCell(new Paragraph(request.getBranch().replace("BR", "") + " branch", content));
            addressContent.setBorderColor(BaseColor.WHITE);
            addressContent.setPaddingLeft(10.0F);
            addressContent.setHorizontalAlignment(Element.ALIGN_LEFT);
            addressContent.setVerticalAlignment(Element.ALIGN_CENTER);
            addressContent.setPaddingBottom(5.0F);

            PdfPCell payFor = new PdfPCell(new Paragraph("Pay for:", content));
            payFor.setBorderColor(BaseColor.WHITE);
            payFor.setPaddingLeft(10.0F);
            payFor.setHorizontalAlignment(Element.ALIGN_LEFT);
            payFor.setVerticalAlignment(Element.ALIGN_CENTER);
            payFor.setPaddingBottom(5.0F);

            PdfPCell payForContent = new PdfPCell(new Paragraph(request.getPayFor(), content));
            payForContent.setBorderColor(BaseColor.WHITE);
            payForContent.setPaddingLeft(10.0F);
            payForContent.setHorizontalAlignment(Element.ALIGN_LEFT);
            payForContent.setVerticalAlignment(Element.ALIGN_CENTER);
            payForContent.setPaddingBottom(5.0F);

            PdfPCell amount = new PdfPCell(new Paragraph("Amount:", content));
            amount.setBorderColor(BaseColor.WHITE);
            amount.setPaddingLeft(10.0F);
            amount.setHorizontalAlignment(Element.ALIGN_LEFT);
            amount.setVerticalAlignment(Element.ALIGN_CENTER);
            amount.setPaddingBottom(5.0F);


            PdfPCell amountContent = new PdfPCell(new Paragraph(dfnd.format(request.getAmount()) + " MZN", content));

            amountContent.setBorderColor(BaseColor.WHITE);
            amountContent.setPaddingLeft(10.0F);
            amountContent.setHorizontalAlignment(Element.ALIGN_LEFT);
            amountContent.setVerticalAlignment(Element.ALIGN_CENTER);
            amountContent.setPaddingBottom(5.0F);

            PdfPCell inword = new PdfPCell(new Paragraph("(In word):", contentItalic));
            inword.setBorderColor(BaseColor.WHITE);
            inword.setPaddingLeft(10.0F);
            inword.setHorizontalAlignment(Element.ALIGN_LEFT);
            inword.setVerticalAlignment(Element.ALIGN_CENTER);
            inword.setPaddingBottom(5.0F);

            PdfPCell inwordContent = new PdfPCell(new Paragraph("", content));
            inwordContent.setBorderColor(BaseColor.WHITE);
            inwordContent.setPaddingLeft(10.0F);
            inwordContent.setHorizontalAlignment(Element.ALIGN_LEFT);
            inwordContent.setVerticalAlignment(Element.ALIGN_CENTER);
            inwordContent.setPaddingBottom(5.0F);
            PdfPCell limitedBranch = null;
            PdfPCell limitedBranchContent = null;
            PdfPCell openingBalance = null;
            PdfPCell openingBalanceContent = null;

            if ("ERP".equals(request.getSrcMoney())) {
                limitedBranch = new PdfPCell(new Paragraph("Limmited of branch:", content));
                limitedBranch.setBorderColor(BaseColor.WHITE);
                limitedBranch.setPaddingLeft(10.0F);
                limitedBranch.setHorizontalAlignment(Element.ALIGN_LEFT);
                limitedBranch.setVerticalAlignment(Element.ALIGN_CENTER);
                limitedBranch.setPaddingBottom(5.0F);

                limitedBranchContent = new PdfPCell(new Paragraph(dfnd.format(request.getCurrentLimit()) + " MZN", content));
                limitedBranchContent.setBorderColor(BaseColor.WHITE);
                limitedBranchContent.setPaddingLeft(10.0F);
                limitedBranchContent.setHorizontalAlignment(Element.ALIGN_LEFT);
                limitedBranchContent.setVerticalAlignment(Element.ALIGN_CENTER);
                limitedBranchContent.setPaddingBottom(5.0F);

                openingBalance = new PdfPCell(new Paragraph("Opening balance:", content));
                openingBalance.setBorderColor(BaseColor.WHITE);
                openingBalance.setPaddingLeft(10.0F);
                openingBalance.setPaddingBottom(25.0F);
                openingBalance.setHorizontalAlignment(Element.ALIGN_LEFT);
                openingBalance.setVerticalAlignment(Element.ALIGN_CENTER);

                openingBalanceContent = new PdfPCell(new Paragraph(dfnd.format(request.getOpeningBalance()) + " MZN", content));
                System.out.println("Openning Balance: " + request.getOpeningBalance());
                openingBalanceContent.setBorderColor(BaseColor.WHITE);
                openingBalanceContent.setPaddingLeft(10.0F);
                openingBalanceContent.setHorizontalAlignment(Element.ALIGN_LEFT);
                openingBalanceContent.setVerticalAlignment(Element.ALIGN_CENTER);
                openingBalanceContent.setPaddingBottom(25.0F);
            }


            PdfPCell director = new PdfPCell(new Paragraph("Director\n\n\n\n\n\n", boldFont));
            director.setBorderColor(BaseColor.BLACK);
            director.setPaddingLeft(10.0F);
            director.setHorizontalAlignment(Element.ALIGN_CENTER);
            director.setVerticalAlignment(Element.ALIGN_CENTER);
            director.setPaddingBottom(5.0F);

            PdfPCell cfo = new PdfPCell(new Paragraph("CFO\n\n\n\n\n\n", boldFont));
            cfo.setBorderColor(BaseColor.BLACK);
            cfo.setPaddingLeft(10.0F);
            cfo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cfo.setVerticalAlignment(Element.ALIGN_CENTER);
            cfo.setPaddingBottom(5.0F);

            PdfPCell accountant = new PdfPCell(new Paragraph("Accountant\n\n\n\n\n\n", boldFont));
            accountant.setBorderColor(BaseColor.BLACK);
            accountant.setPaddingLeft(10.0F);
            accountant.setHorizontalAlignment(Element.ALIGN_CENTER);
            accountant.setVerticalAlignment(Element.ALIGN_CENTER);
            accountant.setPaddingBottom(5.0F);

            PdfPCell cashier = new PdfPCell(new Paragraph("Cashier\n\n\n\n\n\n", boldFont));
            cashier.setBorderColor(BaseColor.BLACK);
            cashier.setPaddingLeft(10.0F);
            cashier.setHorizontalAlignment(Element.ALIGN_CENTER);
            cashier.setVerticalAlignment(Element.ALIGN_CENTER);
            cashier.setPaddingBottom(5.0F);

            PdfPCell requester = new PdfPCell(new Paragraph("Requester\n\n\n\n\n\n", boldFont));
            requester.setBorderColor(BaseColor.BLACK);
            requester.setPaddingLeft(10.0F);
            requester.setHorizontalAlignment(Element.ALIGN_CENTER);
            requester.setVerticalAlignment(Element.ALIGN_CENTER);
            requester.setPaddingBottom(5.0F);
            System.out.println("End create pdfCell, start create pdfTable");
            PdfPTable tableLogo = new PdfPTable(3);
            tableLogo.addCell(movitelLogo);
            tableLogo.addCell(tmpSequence);
            tableLogo.addCell(sequence);
            PdfPTable table0 = new PdfPTable(1);
            table0.addCell(title);
            System.out.println("End table 0");
            PdfPTable table1 = new PdfPTable(1);
            table1.addCell(bookKeeping);
            System.out.println("End table 1");
            PdfPTable table2 = new PdfPTable(2);
            table2.addCell(titleTable1);
            table2.addCell(titleTable1Date);
            System.out.println("End table 2");
            float[] baseColumn = {0.2f, 0.2f, 0.2f, 0.2f};
            PdfPTable table3 = new PdfPTable(4);
            table3.setWidths(baseColumn);
            table3.addCell(name);
            table3.addCell(nameContent);
            table3.addCell(tmpSequence);
            table3.addCell(tmpSequence);
            System.out.println("pdfTable 4");
            PdfPTable table4 = new PdfPTable(4);
            table4.setWidths(baseColumn);
            table4.addCell(staffCode);
            table4.addCell(staffCodeContent);
            table4.addCell(tmpSequence);
            table4.addCell(tmpSequence);

            PdfPTable table5 = new PdfPTable(4);
            table5.setWidths(baseColumn);
            table5.addCell(address);
            table5.addCell(addressContent);
            table5.addCell(tmpSequence);
            table5.addCell(tmpSequence);

            PdfPTable table6 = new PdfPTable(4);
            table6.setWidths(baseColumn);
            table6.addCell(payFor);
            table6.addCell(payForContent);
            table6.addCell(tmpSequence);
            table6.addCell(tmpSequence);

            PdfPTable table7 = new PdfPTable(4);
            table7.setWidths(baseColumn);
            table7.addCell(amount);
            table7.addCell(amountContent);
            table7.addCell(tmpSequence);
            table7.addCell(tmpSequence);

            PdfPTable table8 = new PdfPTable(4);
            table8.setWidths(baseColumn);
            table8.addCell(inword);
            table8.addCell(inwordContent);
            table8.addCell(tmpSequence);
            table8.addCell(tmpSequence);
            PdfPTable table9 = null;
            PdfPTable table10 = null;
            if ("ERP".equals(request.getSrcMoney())) {
                table9 = new PdfPTable(4);
                table9.setWidths(baseColumn);
                table9.addCell(limitedBranch);
                table9.addCell(limitedBranchContent);
                table9.addCell(tmpSequence);
                table9.addCell(tmpSequence);

                table10 = new PdfPTable(4);
                table10.setWidths(baseColumn);
                table10.addCell(openingBalance);
                table10.addCell(openingBalanceContent);
                table10.addCell(tmpSequence);
                table10.addCell(tmpSequence);
            }


            float[] base5Column = {0.2f, 0.2f, 0.2f, 0.2f, 0.2f};
            PdfPTable table11 = new PdfPTable(5);
            table11.setWidths(base5Column);
            table11.addCell(director);
            table11.addCell(cfo);
            table11.addCell(accountant);
            table11.addCell(cashier);
            table11.addCell(requester);

            document.add(tableLogo);
            document.add(table0);
            document.add(table1);
            document.add(table2);
            document.add(table3);
            document.add(table4);
            document.add(table5);
            document.add(table6);
            document.add(table7);
            document.add(table8);
            if ("ERP".equals(request.getSrcMoney())) {
                document.add(table9);
                document.add(table10);
            }

            document.add(table11);

            document.close();
            System.out.println("End create  pdfTable, start make text rotate");
            PdfReader reader = new PdfReader(realPath);
            int n = reader.getNumberOfPages();
            String dest = realPath.replace(".pdf", "") + "_" + request.getBranch() + ".pdf";
            FileOutputStream out = new FileOutputStream(dest);
            PdfStamper stamper = new PdfStamper(reader, out);
            stamper.setRotateContents(false);

            Font f = new Font(Font.FontFamily.HELVETICA, 25.0F);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String strDate = dateFormat.format(date);
            String strPrintingBy = userToken.getLoginName().toUpperCase() + " - " + userToken.getFullName() + strDate.substring(11, 16) + ":" + strDate.substring(0, 10);
            Phrase p = new Phrase(strPrintingBy, f);


            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5F);
            for (int i = 1; i <= n; i++) {
                Rectangle pagesize = reader.getPageSize(i);
                float x = (pagesize.getLeft() + pagesize.getRight()) / 2.0F;
                float y = (pagesize.getTop() + pagesize.getBottom()) / 2.0F;
                PdfContentByte over = stamper.getOverContent(i);
                over.saveState();
                over.setGState(gs1);
                ColumnText.showTextAligned(over, 1, p, x, y, 45.0F);

                over.restoreState();
            }
            stamper.close();
            reader.close();
            out.close();
            output.close();
//            File file = new File(dest);
//            FileInputStream fis = new FileInputStream(finalFile);
//            ftpClient.storeFile(myFile.getName(), fis);
//            fis.close();
//            finalFile.delete();
            File tmpFile = new File(realPath);
            tmpFile.delete();
            System.out.println("Finish create file pdf request borrow cash..., filePath: " + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Have exception: " + e.getLocalizedMessage());
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("Have exception: " + e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Have exception: " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Have exception: " + e.getLocalizedMessage());
        }
        System.out.println("Finally...return filePath");
        return filePath;
    }
}
