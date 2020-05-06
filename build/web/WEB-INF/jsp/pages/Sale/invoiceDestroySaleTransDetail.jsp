<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : invoiceSaleTransSearchResult
    Created on : 22/04/2009, 16:43:14 PM
    Author     : tungtv
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<sx:div id="saleTransList">
    <s:if test="#request.saleTransList != null && #request.saleTransList.size() != 0">
        <fieldset style="width:95%; padding:10px 10px 10px 10px; background-color: #F5F5F5;">
            <legend class="transparent">Danh sách các giao dịch</legend>
                <display:table id="saleTrans" targets = "saleTransList" name="saleTransList" class="dataTable" cellpadding="1" cellspacing="1" requestURI="destroySaleInvoiceAction!pageNavigator.do">
                    <display:column escapeXml="false" title="STT" sortable="false" headerClass="tct">
                        <div align="center" style="vertical-align:middle">${fn:escapeXml(saleTrans_rowNum)}</div>
                    </display:column>
                    <display:column escapeXml="true" property="saleTransCode" title="Mã giao dịch" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                    <display:column escapeXml="true" property="custName" title="Tên khách hàng" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                    <display:column escapeXml="true" property="saleTransDate" title="Ngày giao dịch" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                    <display:column escapeXml="false" title="Hình thức bán" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct">
                        <s:if test = "#attr.saleTrans.saleTransType = 1">
                            Bán lẻ cho khách hàng
                        </s:if>
                        <s:elseif test = "#attr.saleTrans.saleTransType = 2">
                            Bán lẻ cho đại lý, CTV
                        </s:elseif>
                        <s:elseif test = "#attr.saleTrans.saleTransType = 3">
                            Làm dịch vụ
                        </s:elseif>
                    </display:column>
                    <display:column escapeXml="true" property="payMethodName" title="Hình thức thanh toán" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                    <display:column escapeXml="true" property="reasonName" title="Lý do bán" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                    <display:column escapeXml="true" property="staffName" title="Người tạo giao dịch" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                    <display:column escapeXml="true" property="amountTax" title="Tổng tiền" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                </display:table>
        </fieldset>
    </s:if>
</sx:div>
