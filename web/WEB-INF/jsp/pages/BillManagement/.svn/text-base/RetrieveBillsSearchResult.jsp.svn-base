<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- 
    Document   : RetrieveBills
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
%>

<sx:div id="displayTagFrame">
    <fieldset class="imFieldset">
        <legend> ${fn:escapeXml(imDef:imGetText('MSG.list.range.invoice.recover'))}</legend>
        <div style="height:350px; overflow:auto; ">
            <display:table name="invoiceList" id="invoice" 
                           class="dataTable" cellpadding="1" cellspacing="1">
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center">
                    ${fn:escapeXml(invoice_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable"/>
                <display:column escapeXml="true"  property="blockName" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.type'))}" sortable="false" headerClass="sortable"/>
                <display:column escapeXml="true"  property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable" style="width:40px; text-align:right"/>


                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.bill.range'))}" style="width:200px; text-align:left">
                    &nbsp;<s:property escapeJavaScript="true"  value="%{#attr.invoice.fromInvoice}" />
                    -
                    <s:property escapeJavaScript="true"  value="%{#attr.invoice.toInvoice}" />
                    &nbsp;(<s:property escapeJavaScript="true"  value="%{#attr.invoice.currInvoiceNo}" />)
                </display:column>


                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.bill.range.recovery'))}" style="width:170px; text-align:right">
                    <s:if test="form.ownerId != null && form.ownerId > 0 ">

                        <s:textfield size="10" name="fromInvoiceMap['%{#attr.invoice.invoiceListId}']"
                                     id="currInvoiceNo['%{#attr.invoice.invoiceListId}']"
                                     value="%{#attr.invoice.currInvoiceNo}"
                                     theme="simple" style="text-align:right;"
                                     maxlength="10">
                        </s:textfield>
                    </s:if>
                    <s:else>

                        <s:textfield size="10" name="fromInvoiceMap['%{#attr.invoice.invoiceListId}']"
                                     id="currInvoiceNo['%{#attr.invoice.invoiceListId}']"
                                     value="%{#attr.invoice.currInvoiceNo}"
                                     theme="simple" style="text-align:right;"
                                     maxlength="10"
                                     readonly="true">
                        </s:textfield>
                    </s:else>
                    -
                    <s:textfield size="10" name="toInvoiceMap['%{#attr.invoice.invoiceListId}']"
                                 id="toInvoice['%{#attr.invoice.invoiceListId}']"
                                 value="%{#attr.invoice.toInvoice}"
                                 theme="simple" style="text-align:right;"
                                 readonly="true">
                    </s:textfield>

                </display:column>

                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
                    <s:if test="#attr.invoice.status == 1">
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.257'))}
                    </s:if>
                    <s:if test="#attr.invoice.status == 3">
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.258'))}
                    </s:if>
                    <s:if test="#attr.invoice.status == 2">
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.259'))}
                    </s:if>
                </display:column>
                <display:column escapeXml="true"  property="ownerName" title="${fn:escapeXml(imDef:imGetText('MSG.unit.used'))}" sortable="false" headerClass="sortable"/>
                <display:column title="<input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\">"
                                sortable="false" headerClass="tct" style="width: 50px; text-align:center">
                    <div align="center" style="vertical-align:middle; width:50px">
                        <s:if test="form.ownerId != null && form.ownerId > 0 ">
                            <s:checkbox name="form.lstSelectedInvoice"
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
                    </div>
                </display:column>
            </display:table>
        </div>
    </fieldset>    
    <div class="divHasBorder" style="padding:3px; margin-top:5px; text-align:center;">
        <s:if test="form.ownerId != null && form.ownerId > 0 ">
            <tags:submit targets="bodyContent" formId="form"
                         value="MSG.recover" cssStyle="width:80px;"
                         preAction="retrieveBillsAction!retrieveBillComplete.do"
                         showLoadingText="true" validateFunction="validateRetrieve()"/>
        </s:if>
        <s:else>
            <input type="button" style="width: 80px; " disabled value="${fn:escapeXml(imDef:imGetText('MSG.confirm'))}">
        </s:else>
    </div>
</sx:div >

<script type="text/javascript">
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
        for( i = 0; i < inputs.length-3; i++ ) {
            
            if(inputs[i].type == "text" && inputs[i+3].type == "checkbox"){
                //alert(inputs[i].value);
                if(inputs[i+3].checked == true){
                    if(!isNumberFormat(inputs[i].value.trim())){
                        $(inputs[i].id + 'span').style.display = 'inline';
                        inputs[i].select();
                        return false;
                    }
                    var inputNum = inputs[i].value.trim() *1;
                    var endNum = inputs[i+1].value.trim() *1;
                    var startNum = inputs[i+2].value.trim() *1;
                    //alert(startNum + ',' + endNum + ',' + inputNum);
                    if(inputNum < startNum || inputNum > endNum){
                        $(inputs[i].id + 'span').style.display = 'inline';
                        inputs[i].select();
                        return false;
                    }
                }
            }
        }
        return true;
    }
</script>
