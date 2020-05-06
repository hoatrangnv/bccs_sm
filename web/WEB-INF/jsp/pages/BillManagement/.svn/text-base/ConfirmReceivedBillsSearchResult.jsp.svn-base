<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ConfirmReceivedBillsSearchResult.jsp
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : 
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>


<sx:div id="displayTagFrame">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.list.range.invoice.accept'))}</legend>
        <div style="height:350px; overflow:auto; ">
            <display:table name="invoiceList" id="invoice"
                           class="dataTable" cellpadding="1" cellspacing="1">
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" style="width: 40px; text-align:center" headerClass="tct">
                    ${fn:escapeXml(invoice_rowNum)}
                </display:column>
                <display:column property="serialNo" title="${fn:escapeXml(imDef:imGetText('MSG.bill.sign'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
                <display:column escapeXml="true"  property="blockName" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.type'))}" sortable="false" headerClass="sortable"/>
                <display:column property="blockNo" title="${fn:escapeXml(imDef:imGetText('MSG.bin.book.number'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
                <display:column property="fromToInvoice" title="${fn:escapeXml(imDef:imGetText('MSG.bill.range'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
                <display:column escapeXml="true"  property="userAssign" title="${fn:escapeXml(imDef:imGetText('MSG.consign.user'))}" sortable="false" headerClass="sortable"/>
                <display:column property="dateAssign" title="${fn:escapeXml(imDef:imGetText('MSG.consign.date'))}" format="{0,date,dd/MM/yyyy hh:mm:ss}" />
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="sortable">
                    <s:if test="#attr.invoice.status == 2">
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.256'))}
                    </s:if>
                </display:column>
                <display:column title="<input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\">"
                                sortable="false" headerClass="tct" style="width: 50px; text-align:center">
                    <s:checkbox name="form.receivedBill" id="checkBoxID%{#attr.invoice.invoiceListId}"
                                onclick="checkSelectCbAll();"
                                fieldValue="%{#attr.invoice.invoiceListId}" theme="simple"/>
                </display:column>
            </display:table>
        </div>
    </fieldset>
</sx:div>


<script type="text/javascript" language="javascript">
    selectCbAll = function(){
        selectAll("checkAllID", "form.receivedBill", "checkBoxID");
    }
    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "form.receivedBill", "checkBoxID");
    }

    isCheckBoxChecked = function(){
        if(document.getElementById('invoice') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'invoice' );
        var inputs = [];
        //var chkNum = 0;
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            //if( inputs[i].type == "checkbox" ) chkNum++;
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

    validateRecevied = function(){
        if(isCheckBoxChecked() == false){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.020"/>';
            //$('displayResultMsgClient').innerHTML = "!!!Lỗi. Chưa chọn dải hóa đơn cần xác nhận";
            return false;
        }
        return true;
    }
</script>
