<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListChangeInfoStaff
    Created on : Jun 16, 2010, 4:42:24 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
    if (request.getAttribute("lstInformation") == null) {
        request.setAttribute("lstInformation", request.getSession().getAttribute("lstInformation"));
    }
%>
<div style="width:100%">

    <display:table id="coll" name="lstInformation" class="dataTable" pagesize="10"
                   targets="searchArea" 
                   requestURI="channelAction!selectPage.do"
                   cellpadding="0" cellspacing="0" >

        
        <display:column  title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.coll_rowNum}"/>
        </display:column>
        <%--<display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.055'))}" property="status" style="width:130px;text-align:left"/>--%>
        <%--<display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHL.056'))}" property="roleId" style="width:240px;text-align:left"/>--%>


        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.VSA.RoleCode'))}" property="roleCode" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.VSA.RoleName'))}" property="roleName" style="width:130px;text-align:center"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.338'))}" property="description" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <%-- <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct" style="width:100px;text-align:left">--%>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct" style="width:100px;text-align:center">   
            <s:if test="#attr.coll.status == 1">
                <s:text name="MSG.GOD.297"/>
            </s:if>
            <s:else>
                <s:if test="#attr.coll.status == 0">
                    <s:text name="MSG.GOD.274"/></s:if>
            </s:else> 
        </display:column>
         <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status.VSA'))}" sortable="false" headerClass="tct" style="width:100px;text-align:center">   
            <s:if test="#attr.coll.isActive == 1">
                <s:text name="MSG.GOD.297"/>
            </s:if>
            <s:else>
                <s:if test="#attr.coll.isActive == 0">
                    <s:text name="MSG.GOD.274"/></s:if>
                <s:else>
                    <s:text name="MSG.SAE.097"/>
                </s:else>
            </s:else> 
        </display:column>
        
    </display:table>
</div>