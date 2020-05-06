

<%-- 
    Document   : SelectBillDepartment
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
        request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
%>


<s:form action="recoverBillsAction" method="POST" id="frm" theme="simple">
<s:token/>

    <tags:imPanel title="MSG.invoice.info">
        <table class="inputTbl4Col">
            <tr>
                <td class="label">
                    <tags:label key="MSG.bill.sign" required="true"/>
                </td>
                <td class="text">
                    <s:url action="getListBillSerial" var="listBillSerial" >
                        <s:param name="billSerial" value="form.billSerial"></s:param>
                    </s:url>
                    <sx:autocompleter
                        name = "form.billSerial"
                        id="billSerial"
                        href="%{listBillSerial}"
                        loadOnTextChange="true"
                        loadMinimumCount="0"
                        cssClass="txtInput"
                        valueNotifyTopics="getBillCategoryName/getBookTypesName"/>
                </td>
                <td class="label"> <tags:label key="MSG.bin.book.type"/></td>
                <td class="text">
                    <s:textfield name="form.billCategory" id="billCategory" maxlength="80" cssClass="txtInput" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                </td>
            </tr>
        </table>
        <br/>
        <div style="width:100%; padding-top: 10px" align="center">
            <tags:submit targets="bodyContent" formId="frm"
                         value="MSG.find"
                         cssStyle="width:80px;"
                         preAction="recoverBillsAction!searchBills.do"
                         showLoadingText="true" validateFunction="validateSearch();"/>

        </div>
    </tags:imPanel>
    <br/>
    <sx:div id="tblDisplay">
        <s:if test="#session.invoiceList != null && #session.invoiceList.size() != 0" >
            <jsp:include page="RecoverBillsSearchResult.jsp"/>
        </s:if>
    </sx:div>

</s:form>

<script type="text/javascript" language="javascript">
    var _myWidget = dojo.widget.byId("billSerial");
    _myWidget.textInputNode.focus();

    var htmlTag = '<[^>]*>';
    validateSearch = function(){
        var _myWidget = dojo.widget.byId("billSerial");
        if (_myWidget.textInputNode.value.trim().match(htmlTag) != null)
        {   $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.001')"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Không nên nhập thẻ html ở Ký hiệu hoá đơn !";
            _myWidget.textInputNode.focus();
            _myWidget.textInputNode.select();
            return false;
        }
        return true;
    }

    dojo.event.topic.subscribe("getBillCategoryName/getBookTypesName", function(value, key, text, widget){
        if (key != null){
            getInputText("getBillCategoryName.do?billSerialId="+key+"&"+token.getTokenParamString());
        }
    });


    validateRecover = function(){
        if(isCheckBoxChecked() == false){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.022')"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn hóa đơn cần khôi phục";
            return false;
        }
        return true;
    }

    isCheckBoxChecked = function()
    {
        if(document.getElementById('invoice') == null)
        {
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




</script>
