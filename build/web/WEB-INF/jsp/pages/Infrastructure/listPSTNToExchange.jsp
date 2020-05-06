<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listPSTNToExchange
    Created on : May 15, 2009, 4:09:59 PM
    Author     : User one
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%@ page import="java.util.*,java.io.*;"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame">
    <display:table pagesize="30" id="tblCal" targets="displayTagFrame" name="lstPSTN"
                   requestURI="PSTNExchangeAction!searchPSTNExchange.do" class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tblCal_rowNum)}
        </display:column>
        <display:column escapeXml="true" property="provinceCode" title="${fn:escapeXml(imDef:imGetText('MSG.province.code'))}" style="text-align:center" sortable="false" headerClass="tct"/>
        <display:column escapeXml="true" property="prefix" title="${fn:escapeXml(imDef:imGetText('MSG.pstn.first.number'))}" style="text-align:left" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.switchboard'))}" property="exchangeName" sortable="false" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tblCal.exchangeName"/>
        </display:column>
        
        <display:column property="createdate" title="${fn:escapeXml(imDef:imGetText('MSG.change.date'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:60px;text-align:center">
            <s:url action="PSTNExchangeAction!preupdate" id="URL1">
                <s:param name="pstnIsdnExchangeId" value="#attr.tblCal.pstnIsdnExchangeId"/>                
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />" alt="<s:text name="MSG.generates.edit" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" headerClass="sortable" style="width:60px;text-align:center">
            <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vPstnExId" >                        
                <s:param name="queryName">pstnIsdnExchangeId</s:param>
                <s:param name="httpRequestWeb" value="request" />
                <s:param name="parameterId" value="#attr.tblCal.pstnIsdnExchangeId" />
            </s:bean>  
            <a id="delePstnExtId_<s:property escapeJavaScript="true"  value="#attr.tblCal.pstnIsdnExchangeId"/>" onclick="delPstn('<s:property escapeJavaScript="true"  value="#vPstnExId.printParamCrypt()" />')">
                <img src="<s:property escapeJavaScript="true"  value="#request.contextPath"/>/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:text name="MSG.generates.delete" />"/>
            </a>            
        </display:column>
    </display:table>    
</sx:div>
