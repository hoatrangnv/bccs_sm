<%-- 
    Copyright 2010 Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 

    Document   : searchAgent
    Created on : Nov 22, 2011, 4:11:49 PM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
    String pageId = request.getParameter("pageId");
    request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));

    request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType" + pageId));
%>



<div>
    <tags:displayResult id="displayResultMsg" property="message" propertyValue="paramMsg" type="key" />
</div>
<table class="inputTbl6Col">
    <tr>
        <%--<td style="width:10%; ">Mã cửa hàng<font color="red">*</font></td>--%>
        <td class="label" style="width:10%" ><tags:label key="MES.CHL.015" required="true" /></td>
        <td style="width:25%; " >
            <tags:imSearch codeVariable="changeInfoAgentForm.parShopCodeSearch" nameVariable="changeInfoAgentForm.parShopNameSearch"
                           codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"
                           searchClassName="com.viettel.im.database.DAO.ChangeInformationAgentDAO"
                           elementNeedClearContent="changeInfoAgentForm.agentCodeSearch;changeInfoAgentForm.agentNameSearch"
                           searchMethodName="getListShop"                          
                           roleList="f9ChangeInfoAgentRole"/>
        </td>
        <td class="label" style="width:10%" ><tags:label key="MSG.shopId" /></td>
        <td style="width:25%; " >
            <tags:imSearch codeVariable="changeInfoAgentForm.agentCodeSearch" nameVariable="changeInfoAgentForm.agentNameSearch"
                           codeLabel="MSG.shopId" nameLabel="MSG.shopName"
                           searchClassName="com.viettel.im.database.DAO.ChangeInformationAgentDAO"
                           otherParam="changeInfoAgentForm.parShopCodeSearch"
                           searchMethodName="getListAgent"/>
        </td>
        <%--<td style="width:10%">Đối tượng </td>--%>
        <td class="label" style="width:10%" ><tags:label key="MES.CHL.058" /></td>
        <td style="width:25%">
            <tags:imSelect name="changeInfoAgentForm.channelType"
                           id="channelType"
                           cssClass="txtInputFull"
                           theme="simple"
                           headerKey="" headerValue="label.option"
                           list="listChannelType" listKey="channelTypeId" listValue="name"
                           />
        </td>
        <tags:submit formId="changeInfoAgentForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="searchArea"
                     value="MES.CHL.086"
                     validateFunction="checkValidFields()"
                     preAction="changeInformationAgentAction!findAgent.do"/>
    </tr>
</table>
<%--<div class="divHasBorder">
</div>
--%>
