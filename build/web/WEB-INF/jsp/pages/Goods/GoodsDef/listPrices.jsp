<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listPrices
    Created on : Apr 21, 2009, 2:34:45 PM
    Author     : tamdt1
    Desc       : danh sach cac gia cua 1 stockmodel
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
        //request.setAttribute("listPrices", request.getSession().getAttribute("listPrices"));
%>

<sx:div id="divListSaleServicesPrice">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.001'))}</legend>
<!--        <legend>Danh sách giá mặt hàng</legend>-->
        <div style="height:375px; overflow:auto; ">
            <display:table id="tblListPrices" name="listPrices"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListPrices_rowNum)}</display:column>
                <display:column escapeXml="true" property="typeName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.041'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="pricePolicyName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.042'))}" sortable="false" headerClass="tct"/>
                <display:column property="price" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.083'))}" format="{0,number,#,###.00}" style="text-align:right" sortable="false" headerClass="tct"/>
                <display:column property="vat" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.044'))}" format="{0}%" style="text-align:right" sortable="false" headerClass="tct"/>
                <display:column property="staDate" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.117'))}" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                <display:column property="endDate" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.118'))}" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListPrices.status == 1">
                        <tags:label key="MSG.GOD.002"/>
<!--                        Hiệu lực-->
                    </s:if>
                    <s:elseif test="#attr.tblListPrices.status == 0">
                        <tags:label key="MSG.GOD.003"/>
<!--                        Không hiệu lực-->
                    </s:elseif>
                </display:column>
                <display:column escapeXml="true" property="userName" title="${fn:escapeXml(imDef:imGetText('MSG.creater'))}" sortable="false" headerClass="tct"/>
                <display:column property="createDate" title="${fn:escapeXml(imDef:imGetText('MSG.create.date'))}" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.047'))}" sortable="false" style="text-align:center" headerClass="tct">
                    <!-- neu row dang duoc chon -> hien thi icon khac voi icon cua cac hang con lai-->
                    <s:if test="#attr.tblListPrices.priceId == #attr.priceForm.priceId">
                        <img src="${contextPath}/share/img/accept_1.png"
                             title="<s:text name="'MSG.GOD.004"/>" alt="<s:text name="MSG.GOD.004"/>"/>
<!--                             title="Chọn" alt="Chọn"/>-->
                    </s:if>
                    <s:else>
                        <s:url action="goodsDefAction!displayPrice" id="urlDisplayPrice">
                            <s:param name="selectedPriceId" value="#attr.tblListPrices.priceId"/>
                        </s:url>
                        <sx:a targets="divStockPrice" href="%{#urlDisplayPrice}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png"
                                 title="<s:text name="MSG.GOD.004"/>" alt="<s:text name="MSG.GOD.004"/>"/>
<!--                                 title="Chọn" alt="Chọn"/>-->
                        </sx:a>
                    </s:else>
                </display:column>
            </display:table>
        </div>
    </fieldset>
</sx:div>

