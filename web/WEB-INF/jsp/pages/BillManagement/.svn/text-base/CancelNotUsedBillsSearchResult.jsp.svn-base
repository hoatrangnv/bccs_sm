<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%-- 
    Document   : CreateNewBills
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>


<%
            request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
            request.setAttribute("reasonList", request.getSession().getAttribute("reasonList"));

%>

<sx:div id="displayTagFrame" >
    <s:if test="#session.invoiceList != null && #session.invoiceList.size() != 0">
        <display:table name="invoiceList" targets="displayTagFrame"
                       id="invoice" pagesize="10" class="dataTable"
                       cellpadding="1" cellspacing="1"
                       requestURI="cancelNotUsedBillsAction!pageNavigator.do">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" style="text-align:center" headerClass="tct">
                ${fn:escapeXml(invoice_rowNum)}
            </display:column>
            <display:column property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
            <display:column escapeXml="true"  property="blockName" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.type'))}" sortable="false" headerClass="sortable"/>
            <display:column property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
            <display:column property="currToInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.bill.range'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
            <display:column property="currInvoiceNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.range.current'))}" sortable="false" headerClass="sortable" escapeXml="true"/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.bill.range.cancel'))}" style="text-align:left;width:200px;">
                <s:if test="#attr.invoice.status == 1">
                    <div style="width:20px;float:left;height:20px;text-align:right;">
                        <span id ="currInvoiceNo['<s:property escapeJavaScript="true"  value="#attr.invoice.invoiceListId"/>']span" style="display:none">
                            <font color="red">*</font>
                        </span>
                    </div>
                    <div style="float:left;">
                        <s:textfield size="10" name="currInvoiceNoMap['%{#attr.invoice.invoiceListId}']"
                                     id="currInvoiceNo['%{#attr.invoice.invoiceListId}']"
                                     value="%{#attr.invoice.currInvoiceNo}"
                                     theme="simple" style="text-align:right;" maxLength="10">
                        </s:textfield>
                        -
                    </div>
                    <div style="float:left;">
                        <s:textfield size="10" name="toInvoiceMap['%{#attr.invoice.invoiceListId}']"
                                     id="toInvoiceNo['%{#attr.invoice.invoiceListId}']"
                                     value="%{#attr.invoice.toInvoice}"
                                     theme="simple" style="text-align:right;" maxlength="10">
                        </s:textfield>
                    </div>
                    <div style="width:20px;float:left;height:20px;text-align:left;">
                        <span id ="toInvoiceNo['<s:property escapeJavaScript="true"  value="#attr.invoice.invoiceListId"/>']span" style="display:none">
                            <font color="red">*</font>
                        </span>
                    </div>
                </s:if>
                <s:if test="#attr.invoice.status == 3">
                    <s:hidden  name="currInvoiceNoMap['%{#attr.invoice.invoiceListId}']"
                               id="currInvoiceNo['%{#attr.invoice.invoiceListId}']"
                               value="%{#attr.invoice.currInvoiceNo}">
                    </s:hidden>
                </s:if>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
                <s:if test="#attr.invoice.status == 1">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.257'))}
                </s:if>
                <s:elseif test="#attr.invoice.status == 3">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.258'))}
                </s:elseif>
            </display:column>

            <display:column escapeXml="true"  property="billOwnerName" title="${fn:escapeXml(imDef:imGetText('MSG.unit.used'))}" sortable="false" headerClass="sortable"/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.reasonCancel'))}" style="text-align:center">
                <tags:imSelect name="cancelBillReasonMap['${fn:escapeXml(attr.invoice.invoiceListId)}']"
                               id="cancelBillReason['${fn:escapeXml(attr.invoice.invoiceListId)}']"
                               cssClass="txtInput"
                               headerKey="" headerValue="MSG.SAE.013"
                               list="reasonList"
                               value="reasonId"
                               listKey="reasonId" listValue="reasonName"
                               theme="simple"/>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.052'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px; ">
                <s:url action="cancelNotUsedBillsAction!cancelNotUsedBills" id="URL1">
                    <s:token/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                    <s:param name="invoiceListId" value="#attr.invoice.invoiceListId"/>
                </s:url>
                <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true"
                      onclick="destroyClick('%{#attr.invoice.invoiceListId}','%{#attr.invoice.currInvoiceNo}','%{#attr.invoice.toInvoice}')">
                    <img src="<%=StringEscapeUtils.escapeHtml(request.getContextPath())%>/share/img/delete_icon.jpg"
                         title="<s:text name="MSG.BIL.001"/>"
                         alt="<s:text name="MSG.BIL.001"/>"/>
                </sx:a>
            </display:column>
        </display:table>
    </s:if>
</sx:div >

<script language="javascript">
    resetProgress();
    destroyClick = function(invoiceListId, startNum, endNum) {
        //        alert("currInvoiceNo: " +startNum + ", toInvoiceNo: " + endNum);
        
        var i = 0;
        var tbl = $( 'invoice' );
        var inputs = [];
        inputs = tbl.getElementsByTagName( "input" );

        

        for( i = 0; i < inputs.length; i++ ) {
            if(inputs[i].type == "text"){
                //alert(inputs[i].id + 'span');
                $(inputs[i].id + 'span').style.display = 'none';
                //alert('end');
            }
        }

        
        
        var inputStartNumId = "currInvoiceNo['" + invoiceListId + "']";
        var inputEndNumId = "toInvoiceNo['" + invoiceListId + "']";
        
        //        alert($(inputStartNumId) +", "+ $(inputEndNumId));
        
        if( $(inputStartNumId) != null && $(inputEndNumId) != null){
            
            if(!isNumberFormat($(inputStartNumId).value.trim())){
                //                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.002')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.002"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn bắt đầu không hợp lệ";
                $(inputStartNumId + 'span').style.display = 'inline';
                $(inputStartNumId).select();
                event.cancel = true;
                return false;
            }
            if(!isNumberFormat($(inputEndNumId).value.trim())){
                //                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.003')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.003"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn kết thúc không hợp lệ";
                $(inputEndNumId + 'span').style.display = 'inline';
                $(inputEndNumId).select();
                event.cancel = true;
                return false;
            }

            var inputStartNum =  $(inputStartNumId).value.trim() *1;
            var inputEndNum =  $(inputEndNumId).value.trim() *1;
            if(inputStartNum > inputEndNum){
                //                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.004')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.004"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn bắt đầu phải nhỏ hơn số hóa đơn kết thúc";
                $(inputStartNumId + 'span').style.display = 'inline';
                $(inputStartNumId).select();
                event.cancel = true;
            }
            if(inputStartNum < startNum || inputStartNum > endNum){
                //                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.002')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.002"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn bắt đầu không hợp lệ";
                $(inputStartNumId + 'span').style.display = 'inline';
                $(inputStartNumId).select();
                event.cancel = true;
            }
            if(inputEndNum < startNum || inputEndNum > endNum){
                //                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.003')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.003"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Số hóa đơn kết thúc không hợp lệ";
                $(inputEndNumId + 'span').style.display = 'inline';
                $(inputEndNumId).select();
                event.cancel = true;
            }
        }
        var reasonId = "cancelBillReason['" + invoiceListId + "']";
        if($(reasonId).value == ""){
            //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.005')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.005"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn lý do";
            //$(reasonId + 'span').style.display = 'inline';
            $(reasonId).focus();
            event.cancel = true;
            return false;
        }
        //if(!confirm("Bạn có muốn hủy dải hóa đơn này không?!"))
        
        //        if(!confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.BIL.002')"/>'))){
        if(!confirm(getUnicodeMsg('<s:text name="MSG.BIL.002"/>'))){
            event.cancel = true;
            return false;
        }
        initProgress();

    };

</script>









