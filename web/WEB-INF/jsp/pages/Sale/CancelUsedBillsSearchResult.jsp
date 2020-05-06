<%@page import="com.viettel.security.util.StringEscapeUtils"%>


<%-- 
    Document   : CreateNewBills
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<%int iIndex = 0;%>

<s:if test="#session.invoiceListDisplay != null && #session.invoiceListDisplay.size() != 0">
    <ajax:displayTag id="displayTagFrame" ajaxFlag="displayAjax" >
        <%
        request.setAttribute("invoiceListDisplay", request.getSession().getAttribute("invoiceListDisplay"));
        %>
        <font color='red'>
            <html:errors/>
        </font>
        <display:table name="invoiceListDisplay" id="invoice" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="/destroySaleInvoiceAction.do?ajax=1">
            <display:column escapeXml="true" title="STT" sortable="false" headerClass="tct">
                <div align="center"><%=StringEscapeUtils.escapeHtml(++iIndex)%></div>
            </display:column>
            <display:column escapeXml="true" property="invoiceNo" title="Số hóa đơn" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="createdate" title="Ngày lập" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="staffId" title="Người lập" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="custName" title="Tên khách hàng" sortable="false" headerClass="sortable"/>
            <display:column title="Chi tiết" sortable="false" headerClass="tct">
                <s:url action="destroySaleInvoiceAction!billDetail" id="URL" encode="true" escapeAmp="false">
                    <saram name="invoiceId" value="#attr.invoice.invoiceId"/>
                </s:url>

                <sx:a targets="bodyContent" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <div align="center"> <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/accept_1.png" title="Xem chi tiết về hóa đơn" alt="Chi tiết"></div>
                </sx:a>
            </display:column>
            <display:column title="Hủy" sortable="false" headerClass="tct">
                <div align="center"><s:checkbox  id="invoiceId" name="invoiceId"></s:checkbox></div>
            </display:column>

        </display:table>
    </ajax:displayTag>
</s:if>
<s:else>
    <font color='red'>
        <tags:label key="MSG.not.found.records"/>
    </font>
</s:else>

