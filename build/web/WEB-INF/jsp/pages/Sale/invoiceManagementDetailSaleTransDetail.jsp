<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : invoiceManagementDetailSaleTransDetail
    Created on : Aug 13, 2009, 8:49:42 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
        request.setAttribute("saleTransDetailList", request.getSession().getAttribute("saleTransDetailList"));
%>
<s:if test="#session.saleTransDetailList != null && #session.saleTransDetailList.size() != 0">
    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Danh sách các hàng hóa trong giao dịch</legend>
        <display:table id="saleTransDetail" name="saleTransDetailList" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" >
            <display:column escapeXml="true" title="STT" sortable="false" headerClass="tct" style="width:40px; text-align:center">
                ${fn:escapeXml(saleTransDetail_rowNum)}
            </display:column>
            <display:column escapeXml="true" property="stockModelCode" title="Mã hàng hóa" sortable="false" style="text-align:left; " headerClass="tct"/>
            <display:column escapeXml="true" property="name" title="Tên hàng hóa" sortable="false" style="text-align:left;" headerClass="tct"/>
            <display:column escapeXml="true" property="quantity" title="Số lượng" sortable="false" style="text-align:right;" headerClass="tct"/>
            <display:column escapeXml="true" property="price" title="Đơn giá" sortable="false" style="text-align:right;" format="{0,number,#,###}" headerClass="tct"/>
            <display:column escapeXml="true" property="amount" title="Tổng tiền" sortable="false" style="text-align:right;" format="{0,number,#,###}" headerClass="tct"/>
        </display:table>
    </fieldset>
</s:if>
<%--<s:else>
    <br>
    <font color='red' size="2px">
        Không có hàng hóa nào trong giao dịch
    </font>
</s:else>--%>
