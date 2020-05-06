<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : destroySaleInvoice
    Created on : Mar 11, 2009, 6:49:55 PM
    Author     : TungTV
--%>

<%--
    Note: huy hoa don ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>

<%
        request.setAttribute("contextPath", request.getContextPath());

        request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods"));
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

            <tags:imPanel title="MSG.list.reason.fine">
            <div style="width:100%; height:200px; vertical-align:top">
            <table class="inputTbl">
                <tr>
                    <td>
                        <%--TrongLV--%>
                        <div style="overflow:auto; height:100px" >
                        <s:if test="#session.lstGoods != null && #session.lstGoods.size() > 0">
                            <inventoryDisplay:table targets="goodList" id="rootNode" name="lstGoods" class="dataTable" cellpadding="1" cellspacing="1" requestURI="destroySaleInvoiceAction!pageNavigator.do" >

                                <display:column title="STT" sortable="false" headerClass="tct" style="width:15px; text-align:center" >
                                    <div align="center" style="vertical-align:middle">${fn:escapeXml(rootNode_rowNum)}</div>
                                </display:column>

                                <display:column escapeXml="true" property="stockModelName" title="Lý do phạt" sortable="false" style="text-align:left; width:75px" headerClass="tct"/>

                                <%--<display:column escapeXml="true"  property="quantity" title="Số lượng" sortable="false" style="text-align:right" headerClass="tct"/>--%>

                                <display:column property="price" title="Số tiền" format="{0}" sortable="false" style="width:25px; text-align:right" headerClass="tct"/>

                                <%--<display:column property="amount" title="Thành tiền" format="{0}" sortable="false" style="text-align:right" headerClass="tct"/>--%>

                                <display:column escapeXml="true" property="note" title="Ghi chú" sortable="false" style="text-align:left; width:75px;" headerClass="tct"/>
                                <%--<display:column title="Sửa" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                                    <div align="center" style="vertical-align:middle; width:50px">
                                        <s:url action="SaleTransInvoiceAction!prepareEditGoods" id="URL">
                                            <s:param name="stockModelId" value="#attr.rootNode.stockModelId"/>
                                        </s:url>
                                        <sx:a targets="inputGood" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                            <img src="${contextPath}/share/img/accept.png" title="Sửa hàng hóa" alt="Sửa"/>
                                        </sx:a>
                                    </div>
                                </display:column>--%>

                                <display:column title="Xóa" sortable="false" style="width:15px; text-align:center" headerClass="tct">
                                    <div align="center" style="vertical-align:middle">
                                        <s:url action="SaleTransInvoiceAction!delGoods" id="URL1">
                                            <s:param name="stockModelId" value="#attr.rootNode.stockModelId"/>
                                        </s:url>
                                        <sx:a targets="createInvoiceNotSaleDetailArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                            <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa hàng hóa" alt="Xóa"/>
                                        </sx:a>
                                    </div>
                                </display:column>
                                
                            </inventoryDisplay:table>
                        </s:if>
                        <s:else>
                            <table class="dataTable">
                                <tr>
                                    <th width="25px"><tags:label key="MSG.generate.order_no"/></th>
                                    <th width="100px"><tags:label key="MSG.reasonFine"/></th>
                                    <th class="tct" width="50px"><tags:label key="MSG.price.money"/></th>
                                    <th class="tct"><tags:label key="MSG.comment"/></th>
                                    <th class="tct" width="25px"><tags:label key="MSG.delete"/></th>
                                </tr>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
                        </s:else>
                        </div>
                    </td>                   
                </tr>
            </table>
            <table class="inputTbl">
                <tr>
                    <td><tags:label key="MSG.total.amount.before.tax"/></td>
                    <td>
                        <s:textfield name="form.itemAmountNotTaxDisplay" theme="simple" cssStyle="text-align:right" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>

                        <s:hidden name="form.itemAmountTax"/>
                        <s:hidden name="form.itemAmountNotTax"/>
                        <s:hidden name="form.itemTax"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.money.tax"/></td>
                    <td>
                        <s:textfield name="form.itemTaxDisplay" theme="simple" cssStyle="text-align:right" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                    </td>
                </tr>               
                <tr>
                    <td><tags:label key="MSG.totalPriceAfterRate"/></td>
                    <td>
                        <s:textfield name="form.itemAmountTaxDisplay" theme="simple" cssStyle="text-align:right" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
            </div>
        </tags:imPanel>
    </body>
</html>
