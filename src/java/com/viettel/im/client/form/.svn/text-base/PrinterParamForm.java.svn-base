/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.form;

/**
 *
 * @author User
 */
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.PrinterParam;
import org.apache.struts.action.ActionForm;

public class PrinterParamForm extends ActionForm {

    private String printerParamId;
    private String serialCode;
    private String fieldName;
    private String XCoordinates;
    private String YCoordinates;
    private String width;
    private String height;
    private String isDetailField;
    private String font;
    private String fontSize;
    private String fontStyle;
    private String status;

    public String getXCoordinates() {
        return XCoordinates;
    }

    public void setXCoordinates(String XCoordinates) {
        this.XCoordinates = XCoordinates;
    }

    public String getYCoordinates() {
        return YCoordinates;
    }

    public void setYCoordinates(String YCoordinates) {
        this.YCoordinates = YCoordinates;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIsDetailField() {
        return isDetailField;
    }

    public void setIsDetailField(String isDetailField) {
        this.isDetailField = isDetailField;
    }

    public String getPrinterParamId() {
        return printerParamId;
    }

    public void setPrinterParamId(String printerParamId) {
        this.printerParamId = printerParamId;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void resetForm() {
        this.setPrinterParamId("");
        //this.setSerialCode("");
        this.setFieldName("");
        this.setXCoordinates("");
        this.setYCoordinates("");
        this.setWidth("");
        this.setHeight("");
        this.setIsDetailField("");
        this.setFont("");
        this.setFontSize("");
        this.setFontStyle("");
        this.setStatus("");
    }

    public void copyDataToBO(PrinterParam PrinterParam) {
        if (this.getPrinterParamId() != null && !this.getPrinterParamId().trim().equals("")) {
            PrinterParam.setPrinterParamId(Long.parseLong(this.getPrinterParamId().trim()));
        }
        PrinterParam.setSerialCode(this.getSerialCode().trim());
        PrinterParam.setFieldName(this.getFieldName().trim());
        if (this.getXCoordinates() != null && !this.getXCoordinates().trim().equals("")) {
            PrinterParam.setXCoordinates(Long.parseLong(this.getXCoordinates().trim()));
        }
        if (this.getYCoordinates() != null && !this.getYCoordinates().trim().equals("")) {
            PrinterParam.setYCoordinates(Long.parseLong(this.getYCoordinates().trim()));
        }
        if (this.getWidth() != null && !this.getWidth().trim().equals("")) {
            PrinterParam.setWidth(Long.parseLong(this.getWidth().trim()));
        }
        if (this.getHeight() != null && !this.getHeight().trim().equals("")) {
            PrinterParam.setHeight(Long.parseLong(this.getHeight().trim()));
        }
        if (this.getIsDetailField() != null && !this.getIsDetailField().trim().equals("")) {
            PrinterParam.setIsDetailField(Long.parseLong(this.getIsDetailField().trim()));
        }
        PrinterParam.setFont(this.getFont().trim());
        if (this.getFontSize() != null && !this.getFontSize().trim().equals("")) {
            PrinterParam.setFontSize(Long.parseLong(this.getFontSize().trim()));
        }
        if (this.getFontStyle() != null && !this.getFontStyle().trim().equals("")) {
            PrinterParam.setFontStyle(Long.parseLong(this.getFontStyle().trim()));
        }
        if (this.getStatus() != null && !this.getStatus().trim().equals("")) {
            PrinterParam.setStatus(Long.parseLong(this.getStatus().trim()));
        }
    }

    public void copyDataFromBO(PrinterParam PrinterParam) {
        this.setPrinterParamId(PrinterParam.getPrinterParamId().toString());
        this.setSerialCode(PrinterParam.getSerialCode().toString());
        this.setFieldName(PrinterParam.getFieldName().toString());
        this.setXCoordinates((PrinterParam.getXCoordinates() == null) ? "" : String.valueOf(PrinterParam.getXCoordinates()));
        this.setYCoordinates((PrinterParam.getYCoordinates() == null) ? "" : String.valueOf(PrinterParam.getYCoordinates()));
        this.setWidth((PrinterParam.getWidth() == null) ? "" : String.valueOf(PrinterParam.getWidth()));
        this.setHeight((PrinterParam.getHeight() == null) ? "" : String.valueOf(PrinterParam.getHeight()));
        this.setIsDetailField((PrinterParam.getIsDetailField() == null) ? "" : String.valueOf(PrinterParam.getIsDetailField()));
        this.setFont((PrinterParam.getFont() == null) ? "" : String.valueOf(PrinterParam.getFont()));
        this.setFontSize((PrinterParam.getFontSize() == null) ? "" : String.valueOf(PrinterParam.getFontSize()));
        this.setFontStyle((PrinterParam.getFontStyle() == null) ? "" : String.valueOf(PrinterParam.getFontStyle()));
        this.setStatus((PrinterParam.getStatus() == null) ? "" : String.valueOf(PrinterParam.getStatus()));
    }
}


