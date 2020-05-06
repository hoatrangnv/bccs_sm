package com.viettel.im.database.BO;

/**
 * PrinterParam entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PrinterParam implements java.io.Serializable {

	// Fields

	private Long printerParamId;
	private String serialCode;
	private String fieldName;
	private Long XCoordinates;
	private Long YCoordinates;
	private Long width;
	private Long height;
	private Long isDetailField;
	private String font;
	private Long fontSize;
	private Long fontStyle;
	private Long status;

	// Constructors

	/** default constructor */
	public PrinterParam() {
	}

	/** minimal constructor */
	public PrinterParam(Long printerParamId, String serialCode, String fieldName) {
		this.printerParamId = printerParamId;
		this.serialCode = serialCode;
		this.fieldName = fieldName;
	}

	/** full constructor */
	public PrinterParam(Long printerParamId, String serialCode,
			String fieldName, Long XCoordinates, Long YCoordinates, Long width,
			Long height, Long isDetailField, String font, Long fontSize,
			Long fontStyle, Long status) {
		this.printerParamId = printerParamId;
		this.serialCode = serialCode;
		this.fieldName = fieldName;
		this.XCoordinates = XCoordinates;
		this.YCoordinates = YCoordinates;
		this.width = width;
		this.height = height;
		this.isDetailField = isDetailField;
		this.font = font;
		this.fontSize = fontSize;
		this.fontStyle = fontStyle;
		this.status = status;
	}

	// Property accessors

	public Long getPrinterParamId() {
		return this.printerParamId;
	}

	public void setPrinterParamId(Long printerParamId) {
		this.printerParamId = printerParamId;
	}

	public String getSerialCode() {
		return this.serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Long getXCoordinates() {
		return this.XCoordinates;
	}

	public void setXCoordinates(Long XCoordinates) {
		this.XCoordinates = XCoordinates;
	}

	public Long getYCoordinates() {
		return this.YCoordinates;
	}

	public void setYCoordinates(Long YCoordinates) {
		this.YCoordinates = YCoordinates;
	}

	public Long getWidth() {
		return this.width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return this.height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getIsDetailField() {
		return this.isDetailField;
	}

	public void setIsDetailField(Long isDetailField) {
		this.isDetailField = isDetailField;
	}

	public String getFont() {
		return this.font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Long getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(Long fontSize) {
		this.fontSize = fontSize;
	}

	public Long getFontStyle() {
		return this.fontStyle;
	}

	public void setFontStyle(Long fontStyle) {
		this.fontStyle = fontStyle;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}