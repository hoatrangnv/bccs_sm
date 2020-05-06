<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listSaleServices
    Created on : Apr 16, 2009, 2:53:45 PM
    Author     : tamdt1
    Desc       : danh sach cac saleServices
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("listSaleServices", request.getSession().getAttribute("listSaleServices"));
%>

<style>
    .dataTable th {
        font-family: Tahoma;
        text-align: center;
        clear:both;
        background: #f0f0f0;
        color: #0B55C4 /**#000**/;
        border-bottom: 1px solid #999;
        border-left: 1px solid #fff;
        font-size: 13px;
        padding-top: 5px;
        padding-bottom: 5px;
    }
</style>

<sx:div id="divListSaleServices">
    <fieldset class="imFieldset">
        <legend> ${fn:escapeXml(imDef:imGetText('MSG.GOD.075'))}
<!--            075.Danh sách dịch vụ bán hàng-->
        </legend>
        <display:table id="tblListSaleServices"
                       name="listSaleServices"
                       class="dataTable"
                       targets="divListSaleServices" pagesize="20"
                       requestURI="saleServicesAction!pageNavigator.do"
                       cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListSaleServices_rowNum)}</display:column>
            <display:column escapeXml="true" property="code" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.094'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.095'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="notes" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.141'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.tblListSaleServices.status == 1">
                    <tags:label key="MSG.GOD.002"/>
<!--                    002Hoạt động-->
                </s:if>
                <s:elseif test="#attr.tblListSaleServices.status == 0">
                    <tags:label key="MSG.GOD.003"/>
<!--                    003Không hoạt động-->
                </s:elseif>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.047'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                <s:url action="saleServicesAction!displaySaleServices" id="URL1">
                    <s:param name="selectedSaleServicesId" value="#attr.tblListSaleServices.saleServicesId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/accept.png"
                         title="<s:text name="MSG.GOD.076" />" alt="<s:text name="MSG.GOD.004"/>"/>
<!--                         title="076Thông tin chi tiết về dịch vụ bán hàng" alt="004Chọn"/>-->
                </sx:a>
            </display:column>
        </display:table>
    </fieldset>
</sx:div>


