<%@page import="com.viettel.security.util.StringEscapeUtils"%>
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
        request.setAttribute("invoiceListDisplay", request.getSession().getAttribute("invoiceListDisplay"));
%>

<div id="invoiceUsedList">
    <s:if test="#session.invoiceListDisplay != null && #session.invoiceListDisplay.size() != 0">
        <fieldset style="width:95%; padding:10px 10px 10px 10px; background-color: #F5F5F5;">
            <legend class="transparent">Danh sách hóa đơn</legend>

                <inventoryDisplay:table targets="invoiceUsedList" id="invoiceUsed"
                                    name="invoiceListDisplay" pagesize="10"
                                    class="dataTable" cellpadding="1" cellspacing="1"
                                    requestURI="destroySaleInvoiceAction!pageNavigator.do" >
                <display:column escapeXml="true" title="STT" sortable="false" headerClass="tct" style="text-align:center; width:50px" >
                ${fn:escapeXml(invoiceUsed_rowNum)}
                </display:column>
                <display:column escapeXml="true" property="staffName" title="Người lập" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true" property="createdate" title="Ngày lập HĐ" sortable="false" format="{0,date,dd/MM/yyyy}" headerClass="tct" style="text-align:left;"/>
                
                <display:column escapeXml="true" property="serialNo" title="Serial HĐ" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="invoiceId" title="Số hóa đơn" format="{0,number,0000000}" sortable="false" style="text-align:left;" headerClass="tct"/>
                <display:column escapeXml="true" property="custName" title="Tên khách hàng" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true" property="company" title="Công ty" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true" property="address" title="Địa chỉ" sortable="false" headerClass="tct" style="text-align:left;"/>
                <display:column escapeXml="true" property="amountNotTax" title="Trước thuế" sortable="false" headerClass="tct" style="text-align:right;" format=" {0} "/>
                <display:column escapeXml="true" property="tax" title="Thuế" sortable="false" headerClass="tct" style="text-align:right;width:75px" format="{0}"/>
                <display:column escapeXml="true" property="discount" title="Chiết khấu" sortable="false" headerClass="tct" style="text-align:right;width:75px" format=" {0} "/>
                <display:column escapeXml="true" property="amountTax" title="Tổng tiền" sortable="false" headerClass="tct" style="text-align:right;" format=" {0} "/>
                
                 <display:column escapeXml="false" title=" In HĐ " sortable="false" headerClass="tct" style="text-align:center;">
                     <div align="center" style="vertical-align:middle; width:50px">
                        <s:if test = "#attr.invoiceUsed.invoiceStatus = 1">
                            <s:url action="destroySaleInvoiceAction!printInvoice" id="URL1">
                                <s:param name="invoiceUsedId" value="%{#attr.invoiceUsed.invoiceUsedId}"/>
                            </s:url>
                            <sx:a targets="InvoicePrinterArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                <img src="${contextPath}/share/img/accept.png" title="In Hoá đơn" alt="In HĐ"/>
                            </sx:a>
                        </s:if>
                        <s:else>
                            <sx:a executeScripts="true" parseContent="true">
                                <img src="${contextPath}/share/img/accept.png" title="HĐ đã bị huỷ" alt="HĐ đã bị huỷ"/>
                            </sx:a>
                        </s:else>
                    </div>
                </display:column>
                <display:column title="Chi tiết" sortable="false" headerClass="tct" style="text-align:center;">
                    <s:a href="" onclick="aOnclick('%{#attr.invoiceUsed.invoiceUsedId}')">
                        <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/edit_icon.jpg" title="Chi tiết hóa đơn" alt="Chi tiết"/>
                    </s:a>
                </display:column>
                <display:column escapeXml="false" title="Hủy" sortable="false" headerClass="tct" style="text-align:center; width:50px" >
                    <s:if test = "#attr.invoiceUsed.invoiceStatus = 1">
                        <s:checkbox name="saleForm.invoiceUsedId" fieldValue="%{#attr.invoiceUsed.invoiceUsedId}" id="invoiceUsedId" theme="simple">
                        </s:checkbox>
                    </s:if>
                    <s:else>
                        <s:checkbox name="saleForm.invoiceUsedId" fieldValue="%{#attr.invoiceUsed.invoiceUsedId}" id="invoiceUsedId" theme="simple" disabled="true">
                        </s:checkbox>
                    </s:else>
                </display:column>
            </inventoryDisplay:table>

            <table>
                <tr><td colspan="3"></td></tr>
                <tr>
                    <td style="width:80%"></td>
                    <td style="text-align:left; width:15%" >
                        <s:select name="saleForm.reasonId" id="reasonId"
                                  cssClass="txtInput"
                                  headerKey="" headerValue="--Chọn lý do hủy--"
                                  list="%{#session.reasonList != null?#session.reasonList:#{}}"
                                  listKey="reasonId" listValue="reasonName" theme="simple"/>
                    </td>
                    <td style="text-align:right;">
                        <tags:submit targets="bodyContent" formId="saleForm"
                                    value="Hủy HĐ"
                                    showLoadingText="true" validateFunction="validateDelete()"
                                    confirm="true" confirmText="Bạn chắc chắn muốn huỷ hoa đơn?"
                                    preAction="destroySaleInvoiceAction!destroyInvoiceUsedComplete.do"/>
                    </td>                    
                </tr>

                <tr><td colspan="3"></td></tr>
            </table>

        </fieldset>
    </s:if>

    <sx:div id="InvoicePrinterArea">
        <jsp:include page="printInvoiceResult.jsp"/>
    </sx:div>
    
</div>

<script type="text/javascript" language="javascript">

    validateDelete = function(){        
        if (!isCheckedBox()){
            $( 'displayResultMsgClient' ).innerHTML='Chưa chọn hoá đơn nào!';
            return false;
        }
        if ($('reasonId').value.trim() == ''){
            $( 'displayResultMsgClient' ).innerHTML='Chưa chọn lý do huỷ HĐ!';
            return false;
        }
        return true;
    }

    isCheckedBox = function(){
        var tbl = $('invoiceUsed');
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

    dojo.event.topic.subscribe("destroySaleInvoiceAction/destroy", function(event, widget){
        widget.href = "destroySaleInvoiceAction!destroyInvoiceUsedComplete.do";
    });

    aOnclick = function(invoiceUsedId) {
        <%--openDialog('destroySaleInvoiceAction!displayInvoiceUsedDetail.do?selectedInvoiceUsedId=' + invoiceUsedId,1000,750,"0");--%>
        openDialog('destroySaleInvoiceAction!getInvoiceManagementDetail.do?selectedInvoiceUsedId=' + invoiceUsedId,1000,750,false);
    }

</script>
