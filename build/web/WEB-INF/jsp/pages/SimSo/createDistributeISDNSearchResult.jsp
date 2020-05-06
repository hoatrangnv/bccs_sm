


<%--
    Document   : rangeSim
    Created on : Jan 15, 2008, 2:54:01 PM
    Author     : Tuannv
--%>
<%@ page contentType="text/html"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<table class="dataTable">
    <tr>
        <th>
            Loại Số
        </th>
        <th>
            Số thuê bao đầu dải
        </th>
        <th>
            Số thuê bao cuối dải
        </th>
        <th>
            Số lượng thuê bao trong dải
        </th>
        <th>
            Ngày nhập
        </th>
        <th>
            <div align="center">
                Chọn
            </div>
        </th>
    </tr>
    <tr class="odd">
        <td>Số Prepaid</td>
        <td>0169955100</td>
        <td>0169955200</td>
        <td>101</td>
        <td>06/04/2009 </td>
        <td>
            <div align="center">
                <div style="cursor:pointer"
                     onclick="">
                    <input type="checkbox" property="select" styleId="select" value="1"/>
                </div>
            </div>
        </td>
    </tr>
    <tr class="even">
        <td>Số Postpaid</td>
        <td>0972528170</td>
        <td>0972528270</td>
        <td>10</td>
        <td>06/04/2009 </td>
        <td>
            <div align="center">
                <div style="cursor:pointer"
                     onclick="">
                     <input type="checkbox" property="select" styleId="select" value="1"/>
                </div>
            </div>
        </td>
    </tr>
</table>

