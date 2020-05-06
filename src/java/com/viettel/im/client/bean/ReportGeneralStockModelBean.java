/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.client.bean;

/**
 *
 * @author User
 */
public class ReportGeneralStockModelBean implements java.io.Serializable {

    private String tree;
    private String treename;
    private String nameshoporstaff;
    private String leveltree;
    private Long shopid;
    private String shoppath;
    private Long quantity;
    private Long quantityissue;
    private Long quantityTotal;
    private Long stateid;
    private String statename;
    private Long quantityissuebroken;
    private Long quantitybrokenTotal;

    public ReportGeneralStockModelBean() {
    }

    public ReportGeneralStockModelBean(String tree, String treename, String nameshoporstaff, String leveltree, Long shopid, String shoppath, Long quantity, Long quantityissue, Long quantityTotal, Long stateid, String statename, Long quantityissuebroken, Long quantitybrokenTotal) {
        this.tree = tree;
        this.treename = treename;
        this.nameshoporstaff = nameshoporstaff;
        this.leveltree = leveltree;
        this.shopid = shopid;
        this.shoppath = shoppath;
        this.quantity = quantity;
        this.quantityissue = quantityissue;
        this.quantityTotal = quantityTotal;
        this.stateid = stateid;
        this.statename = statename;
        this.quantityissuebroken = quantityissuebroken;
        this.quantitybrokenTotal = quantitybrokenTotal;
    }

    public String getLeveltree() {
        return leveltree;
    }

    public void setLeveltree(String leveltree) {
        this.leveltree = leveltree;
    }

    public String getNameshoporstaff() {
        return nameshoporstaff;
    }

    public void setNameshoporstaff(String nameshoporstaff) {
        this.nameshoporstaff = nameshoporstaff;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getQuantityTotal() {
        return quantityTotal;
    }

    public void setQuantityTotal(Long quantityTotal) {
        this.quantityTotal = quantityTotal;
    }

    public Long getQuantitybrokenTotal() {
        return quantitybrokenTotal;
    }

    public void setQuantitybrokenTotal(Long quantitybrokenTotal) {
        this.quantitybrokenTotal = quantitybrokenTotal;
    }

    public Long getQuantityissue() {
        return quantityissue;
    }

    public void setQuantityissue(Long quantityissue) {
        this.quantityissue = quantityissue;
    }

    public Long getQuantityissuebroken() {
        return quantityissuebroken;
    }

    public void setQuantityissuebroken(Long quantityissuebroken) {
        this.quantityissuebroken = quantityissuebroken;
    }

    public Long getShopid() {
        return shopid;
    }

    public void setShopid(Long shopid) {
        this.shopid = shopid;
    }

    public String getShoppath() {
        return shoppath;
    }

    public void setShoppath(String shoppath) {
        this.shoppath = shoppath;
    }

    public Long getStateid() {
        return stateid;
    }

    public void setStateid(Long stateid) {
        this.stateid = stateid;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public String getTreename() {
        return treename;
    }

    public void setTreename(String treename) {
        this.treename = treename;
    }
    
    
    
    
}
