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
            request.setAttribute("listPackageGoodsPrices", request.getSession().getAttribute("listPackageGoodsPrices"));
%>

<sx:div id="packageGoodsPrices" cssStyle="width:100%">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult id="listPackageGoodsPriceMessage" property="listPackageGoodsPriceMessage" type="key"/>
    </div>


    <display:table id="tblPackageGoodsPrices" name="listPackageGoodsPrices"
                   class="dataTable" cellpadding="1" cellspacing="1"
                   targets="packageGoodsDetail">
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblPackageGoodsPrices_rowNum)}</display:column>
        <display:column  escapeXml="false" property="price" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.083'))}" format="{0,number,#,###}" sortable="false"  style="text-align:right" headerClass="tct"/>
        <display:column  escapeXml="true" property="pricePolicyName" title=" ${fn:escapeXml(imDef:imGetText('MSG.policy.price'))}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="false" property="vat" title=" ${fn:escapeXml(imDef:imGetText('MSG.vat'))}" format="{0}%" style="text-align:right" sortable="false" headerClass="tct"/>
        <display:column escapeXml="false"  property="staDate" title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.087'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="false" property="endDate" title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.127'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" property="description" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.045'))}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct">
            <s:if test="#attr.tblPackageGoodsPrices.status == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.RET.025'))}
            </s:if>
            <s:else>
                ${fn:escapeXml(imDef:imGetText('MSG.GOD.274'))}
            </s:else>
        </display:column>
        <display:column  escapeXml="true" property="username" title=" ${fn:escapeXml(imDef:imGetText('MSG.creater'))}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="false" property="createDate" title=" ${fn:escapeXml(imDef:imGetText('MSG.create.date'))}" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" style="text-align:center" headerClass="tct">
            <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.generates.edit)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.generates.edit)"/>"/>
            </s:if>
            <s:else>
                <sx:a onclick="prepareEditPrice('%{#attr.tblPackageGoodsPrices.saleServicesPriceId}')">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.generates.edit)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.generates.edit)"/>"/>
                </sx:a>
            </s:else>
        </display:column>
        <display:column  escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" sortable="false" style="text-align:center" headerClass="tct">
            <s:if test="#request.saleServicesMode == 'prepareAddOrEdit'">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.delete)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.delete)"/>"/>
            </s:if>
            <s:else>
                <s:a onclick="deletePrice('%{#attr.tblPackageGoodsPrices.saleServicesPriceId}')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.delete)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.delete)"/>"/>
                </s:a>
            </s:else>
        </display:column>
    </display:table>
</sx:div>
<script language="javascript">
    //bat popup them price moi
    prepareEditPrice = function(priceId) {
        openDialog("${contextPath}/packageGoodsAction!prepareEditPackageGoodsPrice.do?selectedPriceId=" + priceId, 750, 700, true);
    }

    //xoa gia hien co
    deletePrice = function(priceId) {
        //if (confirm('Bạn có chắc chắn muốn xóa giá này không?'))
        if(confirm("'<s:property escapeJavaScript="true"  value="getText('MSG.GOD.288')"/>'")){
            gotoAction('packageGoodsPrices', '${contextPath}/packageGoodsAction!delPackageGoodsPrice.do?selectedPriceId=' + priceId + '&' + token.getTokenParamString());
        }
    }

</script>


