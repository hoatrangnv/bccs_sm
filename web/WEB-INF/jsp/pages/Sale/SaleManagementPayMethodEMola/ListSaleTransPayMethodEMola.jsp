<%-- 
    Document   : ListSaleTransPayMethodEMola
    Created on : Aug 3, 2016, 5:01:34 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("listSaleTransPayMethodEMola", request.getSession().getAttribute("listSaleTransPayMethodEMola"));
%>
<sx:div id="displayTagFrame">
    <display:table targets="displayTagFrame" name="listSaleTransPayMethodEMola"
                   id="listSaleTransPayMethodEMolaId" pagesize="10" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="saleTransPayMethodEmolaAction!pageNavigator.do">
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            ${fn:escapeXml(listSaleTransPayMethodEMolaId_rowNum)}
        </display:column>
        <display:column property="saleTransCode" title="${fn:escapeXml(imDef:imGetText('MSG.SaleTrans.Code'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="isdnEmola" title="${fn:escapeXml(imDef:imGetText('MSG.SDT.EMola'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="custName" title="${fn:escapeXml(imDef:imGetText('MSG.CustName'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
        <display:column property="saleTransDate" title="${fn:escapeXml(imDef:imGetText('msg.saleTransDate'))}" format="{0,date,dd/MM/yyyy}" style="text-align:center" sortable="false" headerClass="sortable"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.Status'))}" escapeXml="true">
            <s:if test="#attr.listSaleTransPayMethodEMolaId.status == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.SaleTrans.Success'))}
            </s:if>
            <s:elseif test="#attr.listSaleTransPayMethodEMolaId.status == 2">
                ${fn:escapeXml(imDef:imGetText('MSG.Saletrans.Fail.Result.EMola'))}
            </s:elseif>
            <s:elseif test="#attr.listSaleTransPayMethodEMolaId.status == 0">
                ${fn:escapeXml(imDef:imGetText('MSG.Saletrans.Fail.Error.IM'))}
            </s:elseif>
            <s:else>
                undefined - <s:property escapeJavaScript="true"  value="#attr.listSaleTransPayMethodEMolaId.status"/>
            </s:else>
        </display:column>       
        <display:column property="note" title="${fn:escapeXml(imDef:imGetText('MSG.Note'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
    </display:table>
</sx:div>
