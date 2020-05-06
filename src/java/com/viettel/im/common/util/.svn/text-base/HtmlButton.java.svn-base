/**
 * @author: Pham Van Thang
 * @Desciption: contains the attributes of HMTL buttons
 * @Date: 26 Jan 2008
 */
package com.viettel.im.common.util;

public class HtmlButton {

    /**
     * default style sheet of button
     */
    private final String DEFAULT_STYLE_SHEET = "footbutton";
    /**
     * Displaying Text of button
     */
    private String text;
    /**
     * name of javascript
     */
    private String javascriptText;
    /**
     * Style sheet of button
     */
    private String styleSheet;
    /**
     * position of button
     */
    private int position;
    /**
     * status of button
     * true: enable
     * false: disable
     */
    private boolean enable;
    /**
     * name of button
     */
    private String name;
    /**
     * Check whether this button need confirming message or not
     * true: need confirming message
     * false: no need confirming message
     */
    private boolean confirm;

    /**
     * Constructor of Html button with all attributes
     * @param text: 
     * @param javascriptText
     * @param styleSheet
     * @param pos
     * @param enable
     */
    public HtmlButton(String text, String javascriptText, String styleSheet, int pos, boolean enable) {
        this.text = text;
        this.javascriptText = javascriptText;
        if (!"".equals(styleSheet)) {
            this.styleSheet = styleSheet;
        } else {
            this.styleSheet = DEFAULT_STYLE_SHEET;
        }
        this.enable = enable;
        this.position = pos;
        setName(text);
    }

    /**
     * Constructor of Html button with all attributes
     * @param text
     * @param pos
     * @param enable
     */
    public HtmlButton(String text, int pos, boolean enable) {
        this.text = text;
        this.javascriptText = "";
        this.styleSheet = DEFAULT_STYLE_SHEET;
        position = pos;
        this.enable = enable;
        setName(text);
    }

    /**
     * Constructor of Html button with all attributes
     * @param name
     * @param text
     * @param pos
     * @param enable
     */
    public HtmlButton(String name, String text, int pos, boolean enable) {
        this.text = text;
        this.javascriptText = "";
        this.styleSheet = DEFAULT_STYLE_SHEET;
        position = pos;
        this.enable = enable;
        this.confirm = false;
        setName(name);
    }

    /**
     * Constructor of Html button with all attribute
     * @param name
     * @param text
     * @param pos
     * @param enable
     * @param confirm
     */
    public HtmlButton(String name, String text, int pos, boolean enable, boolean confirm) {
        this.text = text;
        this.javascriptText = "";
        this.styleSheet = DEFAULT_STYLE_SHEET;
        position = pos;
        this.enable = enable;
        this.confirm = confirm;
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavascriptText() {
        return javascriptText;
    }

    public void setJavascriptText(String javascriptText) {
        this.javascriptText = javascriptText;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getStyleSheet() {
        return styleSheet;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
