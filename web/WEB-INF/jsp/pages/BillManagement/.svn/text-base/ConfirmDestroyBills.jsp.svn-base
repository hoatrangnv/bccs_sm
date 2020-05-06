<%--
    Document   : CreateNewBills
    Created on : Feb 18, 2009, 10:51:45 AM
    Author     : TungTV
--%>

<%@page contentType="text/s" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/s4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
        if (request.getAttribute("invoiceList") == null) {
            request.setAttribute("invoiceList", request.getSession().getAttribute("invoiceList"));
        }
        request.setAttribute("contextPath", request.getContextPath());
%>


<s:form action="confirmDestroyBillsAction" theme="simple" method="POST" id="form">
  <tags:imPanel title="MSG.invoice.info">
    <s:hidden property="className" styleId="className" value=""/>
    <s:hidden property="methodName"  styleId="methodName" value=""/>

    <table class="inputTbl4Col">
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td width="50%"></td>
        </tr>
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
            <td class="label"><tags:label key="MSG.bin.book.type" required="true"/></td>
            <td class="text">
                <s:textfield name="form.billCategory" id="billCategory" maxlength="80" cssClass="txtInput" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td class="label">
                 <tags:label key="MSG.unit.under" required="true"/>
            </td>
            <td class="text">
                <sx:autocompleter name="form.subDepartmentName"
                                      id="subDepartmentName"
                                      cssClass="txtInput"
                                      href="confirmDestroyBillsAction!getListShopChildCode.do"
                                      loadOnTextChange="true"
                                      loadMinimumCount="1"
                                      maxlength="25"
                                      valueNotifyTopics="displayShopName"/>
            </td>
            <td class="label"> <tags:label key="MSG.unit.name"/></td>
            <td class="text">
                <s:textfield name="form.shopName" id="shopName" maxlength="80" cssClass="txtInput" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td colspan="4">
                <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
            </td>
        </tr>
    </table>
    <br/>
    <div style="width:100%; padding-top: 10px" align="center">
     <tags:submit targets="displayTagFrame" formId="form"
                     value="MSG.SAE.138"
                      cssStyle="width:80px;"
                     preAction="confirmDestroyBillsAction!searchBills.do"
                     showLoadingText="true" validateFunction="validateSearch();"/>
</div>
</tags:imPanel>

    <jsp:include page="ConfirmDestroyBillsSearchResult.jsp"/>
    <s:token/>
</s:form>
<script type="text/javascript" language="javascript">
    var htmlTag = '<[^>]*>';

    var _myWidget = dojo.widget.byId("billSerial");
    _myWidget.textInputNode.focus();

    validateSearch = function(){
        var _myWidget = dojo.widget.byId("billSerial");
        if (_myWidget.textInputNode.value == "")
        {
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.015')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.015"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập Ký hiệu hoá đơn";
            _myWidget.textInputNode.focus();
            return false;
        }
        if (_myWidget.textInputNode.value.trim() == "")
        {
            //$('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.015')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.015"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa nhập Ký hiệu hoá đơn";
            _myWidget.textInputNode.focus();
            return false;
        }
        if (_myWidget.textInputNode.value.trim().match(htmlTag) != null)
        {
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.001')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.001"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Không nên nhập thẻ html ở Ký hiệu hoá đơn !";
            _myWidget.textInputNode.focus();
            _myWidget.textInputNode.select();
            return false;
        }
       
        return true;
    }

    dojo.event.topic.subscribe("displayShopName", function(value, key, text, widget){
        if (key != null){
            $("shopName").value = key;
        }
        else{
            $("shopName").value = "";
        }
    });

    dojo.event.topic.subscribe("ConfirmBillsForm/complete", function(event, widget){
        widget.href = "confirmDestroyBillsAction!searchBills.do?"+token.getTokenParamString();
    });

    dojo.event.topic.subscribe("getBillCategoryName/getBookTypesName", function(value, key, text, widget){
        if (key != null){
            getInputText("getBillCategoryName.do?billSerialId="+key+"&"+token.getTokenParamString());
        }
        else{
            $("billCategory").value = "";
        }
    });

    validateDestroy = function(){
        if(isCheckBoxChecked() == false){
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.BIL.016')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.BIL.016"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn hóa đơn cần duyệt hủy";
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
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

</script>
