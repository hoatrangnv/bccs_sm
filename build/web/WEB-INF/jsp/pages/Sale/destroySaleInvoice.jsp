<%--
    Document   : destroySaleInvoice
    Created on : Mar 11, 2009, 6:49:55 PM
    Author     : tamdt1
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


<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="destroySaleInvoiceAction" theme="simple" method="post" id="saleForm">
<s:token/>

    <!-- phan tim kiem thong tin ve hoa don can huy -->
    <fieldset style="width:95%; padding:10px 10px 10px 10px; background-color: #F5F5F5;">
        <legend class="transparent">Tìm kiếm hóa đơn</legend>
        <s:actionerror/>
        <table class="inputTbl">
            <tr>
                <td>Tên KH</td>
                <td class="oddColumn">
                    <s:textfield name="saleForm.custName" id="custName" maxlength="100" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td>Số serial</td>
                <td class="oddColumn">
                    <s:textfield name="saleForm.billSerial" id="saleForm.billSerial" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td>Số HĐ</td>
                <td class="oddColumn">
                    <s:textfield name="saleForm.billNum" id="saleForm.billNum" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td>Trạng thái HĐ</td>
                <td>
                    <s:select name="saleForm.invoiceStatus" id="invoiceStatus"
                              cssClass="txtInputFull"
                              list="#{'1':'HĐ đã lập','4':'HĐ đã huỷ'}"
                              headerKey="" headerValue="--Chọn trạng thái HĐ--"/>
                </td>
            </tr>
            <tr>
                <td>Người lập</td>
                <td class="oddColumn">
                    <s:textfield name="saleForm.staffName" id="saleForm.staffName" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td>Từ ngày</td>
                <td class="oddColumn">
                    <tags:dateChooser property="saleForm.startDate" id="startDate" styleClass="txtInput"/>
                </td>
                <td>Đến ngày</td>
                <td>
                    <tags:dateChooser property="saleForm.endDate" id="endDate" styleClass="txtInput"/>
                </td>
            </tr>
            <tr><td></td></tr>
            <tr><td></td></tr>
            <tr>
                <td colspan="6">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                </td>
            </tr>
            <tr><td></td></tr>
            <tr><td></td></tr>
        </table>

        <div style="width:100%" align="center">
            <sx:submit targets="bodyContent" formId="saleForm"
            value="Tìm kiếm" cssStyle="width:80px;"
                       beforeNotifyTopics="destroySaleInvoiceAction/searchBills"/>
        </div>
    </fieldset>
        
    <br/>
        <s:if test="#session.invoiceListDisplay != null && #session.invoiceListDisplay.size() != 0">
           <jsp:include page="destroySaleInvoiceSearchResult.jsp"/>
       </s:if>
    <br/>
</s:form>

    
<script type="text/javascript" language="javascript">
    // Tuannv -- Tim kiem hoa don ban hang
    dojo.event.topic.subscribe("destroySaleInvoiceAction/searchBills", function(event, widget){
        var checkDate = true;
        var dateExported= dojo.widget.byId("startDate");
        if(dateExported.domNode.childNodes[1].value.trim() != "" &&
            !isDateFormat(dateExported.domNode.childNodes[1].value)){
            $( 'displayResultMsgClient' ).innerHTML='Ngày lập HĐ từ không hợp lệ';
            dateExported.domNode.childNodes[1].focus();
            event.cancel = true;
            return;
        }
        if (dateExported.domNode.childNodes[1].value.trim() == "")
            checkDate = false;

        var dateExported1 = dojo.widget.byId("endDate");
        if(dateExported1.domNode.childNodes[1].value.trim() != "" &&
            !isDateFormat(dateExported1.domNode.childNodes[1].value)){
            $( 'displayResultMsgClient' ).innerHTML='Ngày lập HĐ đến không hợp lệ';
            dateExported1.domNode.childNodes[1].focus();
            event.cancel = true;
            return;
        }
        if (dateExported1.domNode.childNodes[1].value.trim() == "")
            checkDate = false;

        if (checkDate)
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
                $( 'displayResultMsgClient' ).innerHTML='Ngày lập HĐ từ phải nhỏ hơn Ngày lập HĐ đến';
                dateExported.domNode.childNodes[1].focus();
                dateExported.domNode.childNodes[1].select();
                event.cancel = true;
                return;
            }
        widget.href = "destroySaleInvoiceAction!searchDestroyBills.do";
    });
</script>
