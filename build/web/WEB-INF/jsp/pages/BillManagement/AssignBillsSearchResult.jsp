<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : BillManagement
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

<%@page  import="com.viettel.im.common.util.ResourceBundleUtils"%>

<%

            request.setAttribute("contextPath", request.getContextPath());

            String channelTypeVT = ResourceBundleUtils.getResource("VT_TELECOM");
            if (channelTypeVT != null && !"".equals(channelTypeVT.trim())) {
                request.setAttribute("channelTypeVT", channelTypeVT);
            }
%>


<s:if test="#request.invoiceList != null && #request.invoiceList.size() != 0">
    <sx:div id="displayTagFrame">
        <display:table targets="displayTagFrame" name="invoiceList" id="invoice"
                       pagesize="10" class="dataTable" cellpadding="1" cellspacing="1" requestURI="assignBillAction!pageNavigator.do" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                ${fn:escapeXml(invoice_rowNum)}
            </display:column>
            <display:column escapeXml="true"  property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="blockName" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.type'))}" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true"  property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable"/>


            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.consign.bill.range'))}" style="width:180px; text-align:left">
                <s:textfield size="10" name="fromInvoiceMap['%{#attr.invoice.invoiceListId}']"
                             id="fromInvoice['%{#attr.invoice.invoiceListId}']"
                             value="%{#attr.invoice.currInvoiceNo}"
                             theme="simple" style="text-align:right;"
                             readonly="true">
                </s:textfield>
                -
                <s:if test="#attr.invoice.status == 1">
                    <s:textfield size="10" name="toInvoiceMap['%{#attr.invoice.invoiceListId}']"
                                 id="toInvoice['%{#attr.invoice.invoiceListId}']"
                                 value="%{#attr.invoice.toInvoice}"
                                 theme="simple" style="text-align:right;"
                                 maxlength="10">
                    </s:textfield>
      
                </s:if>
                <s:elseif test="#attr.invoice.status == 2">
                    <s:textfield size="10" name="toInvoiceMap['%{#attr.invoice.invoiceListId}']"
                                 id="toInvoice['%{#attr.invoice.invoiceListId}']"
                                 value="%{#attr.invoice.toInvoice}"
                                 theme="simple" style="text-align:right;"
                                 readonly="true">
                    </s:textfield>
                </s:elseif>                

            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
                <s:if test="#attr.invoice.status == 1">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.255'))}
                </s:if>
                <s:elseif test="#attr.invoice.status == 2">
                    ${fn:escapeXml(imDef:imGetText('MSG.GOD.256'))}
                </s:elseif>
            </display:column>

            <display:column property="ownerName" title="${fn:escapeXml(imDef:imGetText('MSG.unit.receive'))}" sortable="false" headerClass="sortable">

            </display:column>

            <display:column title="<input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\"> " sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                <s:if test="#attr.invoice.status == 1">
                    <s:checkbox  name="form.lstSelectedInvoice"
                                 id="checkBoxID%{#attr.invoice.invoiceListId}"
                                 fieldValue="%{#attr.invoice.invoiceListId}"
                                 onclick="checkSelectCbAll();"
                                 theme="simple"/>
                </s:if>
                <s:else>
                    <s:checkbox id="checkBoxID%{#attr.invoice.invoiceListId}" 
                                name = "checkBoxID%{#attr.invoice.invoiceListId}"
                                disabled="true"
                                theme="simple"/>
                </s:else>
            </display:column>
        </display:table>
    </sx:div>
</s:if>

<script>
    selectCbAll = function(){
        selectAll("checkAllID", "form.lstSelectedInvoice", "checkBoxID");
    }
    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "form.lstSelectedInvoice", "checkBoxID");
    }

    isTextBoxChecked = function(){
        if(document.getElementById('invoice') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'invoice' );
        var inputs = [];
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            if(inputs[i].type == "text" && inputs[i+2].type == "checkbox"){
                if(inputs[i+2].checked == true){

                    //MrSun
                    if(!isNumberFormat(inputs[i].value.trim())){
                        $(inputs[i].id + 'span').style.display = 'inline';
                        inputs[i].select();
                        return false;
                    }
                    inputs[i].value = inputs[i].value.trim();
                    
                    var startNum = inputs[i-1].value.trim() *1;
                    var endNum = inputs[i+1].value.trim() *1;
                    var inputNum = inputs[i].value.trim() *1;
                    if(!isNumberFormat(inputs[i].value.trim())){                        
                        $(inputs[i].id + 'span').style.display = 'inline';
                        inputs[i].select();
                        return false;
                    }
                    else if(inputNum < startNum || inputNum > endNum){
                        $(inputs[i].id + 'span').style.display = 'inline';
                        inputs[i].select();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    aOnclick = function(invoiceListId, a, b) {
        openPopup('splitBillAction!splitBillInfo.do?invoiceListId=' + invoiceListId+"&"+ token.getTokenParamString(),1050,550);
    }

  
</script>



