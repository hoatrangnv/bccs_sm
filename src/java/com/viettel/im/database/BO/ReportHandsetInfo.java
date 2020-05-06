/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.BO;

import java.util.List;

/**
 *
 * @author dev_linh
 */
public class ReportHandsetInfo {

    private String branch;
    private List<ChannelHandsetDetail> listChannel;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<ChannelHandsetDetail> getListChannel() {
        return listChannel;
    }

    public void setListChannel(List<ChannelHandsetDetail> listChannel) {
        this.listChannel = listChannel;
    }
}
