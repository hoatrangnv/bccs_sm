<%-- 
    Document   : catalogConfigPayDateListResult
    Created on : May 29, 2013, 8:41:55 AM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
            if (request.getAttribute("listResult") == null) {
                request.setAttribute("listResult", request.getSession().getAttribute("listResult"));
            }
%>
<!DOCTYPE html>
<c:set var="roleUnApprove" scope="page" value="${imDef:checkAuthority('roleUnAppoveSalePayStatus', request)}" />
<div id="messageDiv" style="text-align: center">
    <s:if test="#request.returnMessage != null">
        <s:if test="#request.isSuccess">
            <span style="color: blue"><s:text name="%{#request.returnMessage}"/></span>
        </s:if>
        <s:else>
            <span style="color: red"><s:text name="%{#request.returnMessage}"/></span>
        </s:else>
    </s:if>
</div>
<div style="max-height: 600px;overflow-y: scroll">
    <display:table name="listResult" targets="divListResultId" id="result" pagesize="10" class="dataTable"
                   cellpadding="3px" cellspacing="1" requestURI="catalogApParamsDebit!preparePage.do">
        <display:column escapeXml="false" title="${imDef:imGetText('MSG.SAE.048')}" sortable="false" headerClass="tct" style="text-align:left">
            <s:property value="%{#attr.result_rowNum}"/>
        </display:column>
        <display:column escapeXml="true" property="name" title="${imDef:imGetText('lbl.ten_tham_so')}" sortable="false" headerClass="sortable"/>
        <display:column escapeXml="true" property="code" title="${imDef:imGetText('lbl.ma_tham_so')}" sortable="false" headerClass="sortable" style="text-align:right; padding-right:3px"/>
        <display:column escapeXml="true" title="${imDef:imGetText('MSG.LIMITED.06')}" sortable="false" headerClass="sortable">
            <s:if test="#attr.result.status==1">
                <s:text name="MSG.GOD.297"/>
            </s:if>
            <s:else>
                <s:text name="MSG.GOD.274"/>
            </s:else>
        </display:column>
        <display:column title="${imDef:imGetText('lbl.sua')}" sortable="false" headerClass="tct" style="width:20px;text-align:center;">
            <a style="cursor:pointer" onclick="preUpdate('<s:property value="#attr.result.type"/>', '<s:property value="#attr.result.code"/>')">
                <img src="${contextPath}/share/img/edit_icon.jpg" title='<s:property value="getText('lbl.sua')"/>'/>                                               
            </a>                                                                                               
        </display:column>
        <display:column title="${imDef:imGetText('lbl.xoa')}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:if test="#attr.result.status==1">
                <a style="cursor:pointer" onclick="deletes('<s:property value="#attr.result.type"/>',
                    '<s:property value="#attr.result.code"/>')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title='<s:property value="getText('lbl.xoa')"/>'/>                                               
                </a> 
            </s:if>
        </display:column>
    </display:table>
</div>
<script type="text/javascript">
    <s:if test="#request.isSuccess">
        resetForm();
    </s:if>
</script>
