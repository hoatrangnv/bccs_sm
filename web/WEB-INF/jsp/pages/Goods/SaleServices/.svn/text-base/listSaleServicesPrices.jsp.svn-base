<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listSaleServicesPrices
    Created on : Apr 16, 2009, 12:53:51 PM
    Author     : Doan Thanh 8
--%>

<%--
    Notes   : danh sach cac saleServicesPrice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("listSaleServicesPrices", request.getSession().getAttribute("listSaleServicesPrices"));
%>

<sx:div id="divListSaleServicesPrice">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.086'))}
<!--            086Danh sách giá dịch vụ bán hàng-->
        </legend>
        <display:table id="tblListSaleServicesPrices"
                       name="listSaleServicesPrices"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListSaleServicesPrices_rowNum)}</display:column>
            <display:column property="price" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.083'))}" format="{0,number,#,###.00}" sortable="false"  style="text-align:right" headerClass="tct"/>
            <display:column property="vat" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.044'))}" format="{0,number,#,###}" style="text-align:center" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="pricePolicyName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.042'))}" sortable="false" headerClass="tct"/>
            <display:column property="staDate" title="${fn:escapeXml(imDef:imGetText('MSG.fromDate'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct" style="text-align:center"/>
            <display:column property="endDate" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.118'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct" style="text-align:center"/>
            <display:column escapeXml="true" property="description" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.045'))}" sortable="false" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.tblListSaleServicesPrices.status == 1">
                    <tags:label key="MSG.GOD.002"/>
<!--                    Hoạt động-->
                </s:if>
                <s:else>
                    <tags:label key="MSG.GOD.003"/>
<!--                    Không hoạt động-->
                </s:else>
            </display:column>
            <display:column escapeXml="true" property="username" title="${fn:escapeXml(imDef:imGetText('MSG.creater'))}" sortable="false" headerClass="tct"/>
            <display:column property="createDate" title="${fn:escapeXml(imDef:imGetText('MSG.create.date'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct" style="text-align:center"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.047'))}" sortable="false" style="text-align:center" headerClass="tct">
                <sx:a onclick="displaySaleServicesPrice('%{#attr.tblListSaleServicesPrices.saleServicesPriceId}')">
                    <img src="${contextPath}/share/img/edit_icon.jpg"
                         title="<s:text name="MSG.GOD.087"/>" alt="<s:text name="MSG.GOD.004"/>"/>
<!--                         title="087Thông tin chi tiết giá dịch vụ bán hàng" alt="004Chọn"/>-->
                </sx:a>
            </display:column>
        </display:table>
    </fieldset>
</sx:div>


<script language="javascript">
    //view thong tin gia dich vu
    displaySaleServicesPrice = function(selectedSaleServicesPriceId) {
        gotoAction('divSaleServicesPrice', "${contextPath}/saleServicesAction!displaySaleServicesPrice.do?selectedSaleServicesPriceId=" + selectedSaleServicesPriceId);
    }

</script>

