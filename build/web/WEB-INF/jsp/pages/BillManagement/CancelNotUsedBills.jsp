<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : CreateNewBills
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

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="title.cancelNotUsedBills.page">
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
    </div>

    <s:form action="cancelNotUsedBillsAction" method="post" id="form" theme="simple">
        <!-- phan thong tin tim kiem dai hoa don can thu hoi -->
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%;"><tags:label key="MSG.invoice.type" required="true"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSelect name="form.invoiceType"
                                       id="form.invoiceType"
                                       cssClass="txtInputFull"
                                       list="1:msg.invoiceTypeSale, 2:msg.invoiceTypePayment"
                                       headerKey="" headerValue="msg.invoiceTypeHeader"/>

                    </td>
                    <td style="width:10%;"><tags:label key="MSG.signBill" required="true"/></td>
                    <td class="oddColumn" style="width:23%;">
                        <tags:imSearch codeVariable="form.billSerial" nameVariable="form.billCategory"
                                       codeLabel="MSG.signBill" nameLabel="MSG.bin.book.type"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListBookType"
                                       getNameMethod="getBookTypeName"
                                       otherParam="form.invoiceType"/>
                    </td>
                    <td style="width:10%;"><tags:label key="MSG.status"/></td>
                    <td>
                        <tags:imSelect name="form.billSituation" id="billSituation"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.GOD.257:3: MSG.GOD.258 "
                                       listKey="abc" listValue=""
                                       headerKey="" headerValue="MSG.GOD.260"/>
                    </td>
                </tr>
            </table>

            <div style="width:100%" align="center">
                <tags:submit targets="bodyContent" formId="form"
                             value="MSG.SAE.138"
                             cssStyle="width:80px;"
                             preAction="cancelNotUsedBillsAction!searchBills.do"
                             showLoadingText="true" validateFunction="validateSearch();"/>
            </div>

        </div>

        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.invoice.list'))}</legend>
                <sx:div id="tblDisplay">
                    <jsp:include page="CancelNotUsedBillsSearchResult.jsp"/>
                </sx:div>
            </fieldset>
        </div>
        <s:token/>
    </s:form>

</tags:imPanel>


<script type="text/javascript" language="javascript">
    var htmlTag = '<[^>]*>';

    //    var _myWidget = dojo.widget.byId("form.billSerial");
    //    _myWidget.textInputNode.focus();
    $( 'form.billSerial' ).focus();
    validateSearch = function(){
        //        var _myWidget = dojo.widget.byId("form.billSerial");
        //        if (_myWidget.textInputNode.value.trim().match(htmlTag) != null)
        var _myWidget =$( 'form.billSerial' );
        if (_myWidget.value.trim().match(htmlTag) != null)
        {
            //            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.ISN.001')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.ISN.001"/>';
            //$( 'displayResultMsgClient' ).innerHTML = "Không nên nhập thẻ html ở Ký hiệu hoá đơn !";
            _myWidget.textInputNode.focus();
            _myWidget.textInputNode.select();
            event.cancel = true;
            return false;
        }
        return true;
    }

    updateStatus = function(){
        dojo.event.topic.publish('searchNow');
        return false;
    }

    dojo.event.topic.subscribe("getBillCategoryName/getBookTypesName", function(value, key, text, widget){
        if (key != null){
            getInputText("getBillCategoryName.do?billSerialId="+key);
        }
    });
</script>
