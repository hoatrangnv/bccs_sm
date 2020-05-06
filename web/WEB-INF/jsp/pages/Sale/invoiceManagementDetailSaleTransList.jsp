<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : invoiceManagementDetailSaleTransList
    Created on : Aug 13, 2009, 8:49:24 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>

<%
        request.setAttribute("saleTransList", request.getSession().getAttribute("saleTransList"));
%>
<s:if test="#session.saleTransList != null && #session.saleTransList.size() != 0">
    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Danh sách các giao dịch</legend>
        <display:table id="saleTrans" targets="saleTransList" name="saleTransList" pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="searchSaleTransAction!pageNavigator.do">
            <display:column escapeXml="true" title="STT" sortable="false" style="width:40px; text-align:center" headerClass="tct">
                ${fn:escapeXml(saleTrans_rowNum)}
            </display:column>            
            <display:column escapeXml="true" property="saleTransCode" title="Mã giao dịch" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="shopName" title="Kho GD" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="staffName" title="Nhân viên GD" sortable="false" headerClass="sortable"/>            
            <display:column escapeXml="true" property="saleTransDate" title="Ngày GD" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" title="Số hoá đơn" property="invoiceNo" sortable="false" headerClass="tct"/>            
            <display:column escapeXml="true" property="amountTax" format="{0}" title="Tổng tiền" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="custName" title="Tên KH" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="isdn" title="Số TB" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="contractNo" title="Số hợp đồng" sortable="false" headerClass="sortable"/>
            
            <%--<display:column escapeXml="true" title="Chi tiết" sortable="false" style="text-align:center;" headerClass="tct">
                <s:a onclick="viewIDSaleTransDetail('%{#attr.saleTrans.saleTransId}')">
                        <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/edit_icon.jpg" title="Chi tiết hóa đơn" alt="Chi tiết"/>
                </s:a>
            </display:column>--%>

            <display:column escapeXml="false" title="Chi tiết" sortable="false" style="text-align:center;" headerClass="tct">
                <s:url action="destroySaleInvoiceAction!getInvoiceManagementDetailSaleTransDetail.do" id="URLDetail">
                    <s:param name="saleTransId" value="#attr.saleTrans.saleTransId"/>
                </s:url>
                <sx:a targets="IMSaleTransDetail" href="%{#URLDetail}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/accept.png" title="Danh sách hàng hoá trong giao dịch" alt="Chi tiết"/>
                </sx:a>
            </display:column>

        </display:table>
    </fieldset>

    <br/>    
    <div id="IMSaleTransDetail">
        <jsp:include page="invoiceManagementDetailSaleTransDetail.jsp"/>
    </div>
    
</s:if>

    <script>
        viewIDSaleTransDetail = function(saleTransId){
            gotoAction("IMSaleTransDetail", "destroySaleInvoiceAction!getInvoiceManagementDetailSaleTransDetail.do?saleTransId=" + saleTransId, "saleForm")
        }
    </script>
