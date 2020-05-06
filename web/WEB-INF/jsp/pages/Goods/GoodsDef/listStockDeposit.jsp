<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listStockDeposit
    Created on : Sep 24, 2009, 8:14:45 AM
    Author     : Doan Thanh 8
    Desc       : danh sach cac doi tuong dat coc doi voi mat hang
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
//request.setAttribute("listStockDeposits", request.getSession().getAttribute("listStockDeposits"));
%>

<sx:div id="divListSaleServicesPrice">
    <fieldset class="imFieldset">
        <legend><tags:label key="MSG.GOD.006"/></legend>
        <!--        <legend>Danh sách đối tượng phải đặt cọc</legend>-->
        <div style="height:425px; overflow:auto; ">
            <display:table id="tblListStockDeposits" name="listStockDeposits"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListStockDeposits_rowNum)}</display:column>
                <display:column escapeXml="true" property="chanelTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.011'))}" sortable="false" headerClass="tct"/>
                <display:column property="maxStock" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.265'))}" format="{0,number,#,###}" style="text-align:right" sortable="false" headerClass="tct"/>
                <display:column property="dateFrom" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.117'))}" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                <display:column property="dateTo" title="${fn:escapeXml(imDef:imGetText('MSG.generates.to_date'))}" format="{0,date,dd/MM/yyyy}" sortable="false" style="text-align:center" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.stock.stock.type'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListStockDeposits.transType == 1">
                        <tags:label key="MSG.GOD.336"/>
                    </s:if>
                    <s:if test="#attr.tblListStockDeposits.transType == 2">
                        <tags:label key="MSG.GOD.337"/>
                    </s:if>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListStockDeposits.status == 1">
                        <tags:label key="MSG.GOD.002"/>
                        <!--                        Hiệu lực-->
                    </s:if>
                    <s:elseif test="#attr.tblListStockDeposits.status == 0">
                        <tags:label key="MSG.GOD.003"/>
                        <!--                        Không hiệu lực-->
                    </s:elseif>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.047'))}" sortable="false" style="text-align:center" headerClass="tct">
                    <!-- neu row dang duoc chon -> hien thi icon khac voi icon cua cac hang con lai-->
                    <s:if test="#attr.tblListStockDeposits.stockDepositId == #attr.stockDepositForm.stockDepositId">
                        <img src="${contextPath}/share/img/accept_1.png"
                             title="<s:text name="MSG.GOD.004"/>" alt="<s:text name="MSG.GOD.004"/>"/>
                        <!--                             title="Chọn" alt="Chọn"/>-->
                    </s:if>
                    <s:else>
                        <s:url action="goodsDefAction!displayStockDeposit" id="URL1">
                            <s:param name="selectedStockDepositId" value="#attr.tblListStockDeposits.stockDepositId"/>
                        </s:url>
                        <sx:a targets="divStockDeposit" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
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


