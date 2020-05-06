<%-- 
    Document   : lookupSerialHistory
    Created on : Jan 20, 2010, 8:51:42 AM
    Author     : Doan Thanh 8
    Desc       : tim kiem lich su serial
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.FAC.find.serial">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="lookupSerialHistoryAction" theme="simple" method="post" id="lookupSerialHistoryForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>

                    <td style="width:10%; "><tags:label key="MSG.GOD.type" required="true"/></td>
                    <td class="oddColumn" style="width:23%; ">
                           <tags:imSelect name="lookupSerialForm.stockTypeId"
                                  id="lookupSerialForm.stockTypeId"
                                  cssClass="txtInputFull"
                                  list="listStockType"
                                  listKey="stockTypeId" listValue="name"
                                  onchange="changeStockType(this.value)"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.goods" required="true"/></td>
                    <td class="oddColumn" style="width:23%; ">
                        <tags:imSelect name="lookupSerialHistoryForm.stockModelId"
                                  id="lookupSerialHistoryForm.stockModelId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.GOD.217"
                                  list="listStockModel"
                                  listKey="stockModelId" listValue="name"/>
                    </td>

                    <td style="width:10%; "><tags:label key="MSG.generates.imsi" required="true"/></td>
                    <td>
                        <s:textfield name="lookupSerialHistoryForm.serial" id="lookupSerialHistoryForm.serial" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.fromDate" /></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="lookupSerialHistoryForm.fromDate"/>
                    </td>
                    <td><tags:label key="MSG.generates.to_date"/></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="lookupSerialHistoryForm.toDate"/>
                    </td>
                    <td></td>
                    <td>
                        <tags:submit formId="lookupSerialHistoryForm"
                                     validateFunction="checkValidFields()"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     targets="bodyContent"
                                     value="MSG.generates.find"
                                     preAction="lookupSerialHistoryAction!lookupSerialHistory.do"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <!-- ket qua tim kiem -->
    <div>
        <jsp:include page="listLookupSerialHistory.jsp"/>
    </div>

</tags:imPanel>

<script language="javascript">

    //focus vao truong dau tien
    //$('lookupSerialForm.fromSerial').select();
    //$('lookupSerialForm.fromSerial').focus();

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var fromSerial = trim($('lookupSerialForm.fromSerial').value);
        var toSerial = trim($('lookupSerialForm.toSerial').value);

        if(fromSerial != "" && !isInteger(trim($('lookupSerialForm.fromSerial').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.001')"/>';
            $('lookupSerialForm.fromSerial').select();
            $('lookupSerialForm.fromSerial').focus();
            return false;
        }
        if(toSerial != "" && !isInteger(trim($('lookupSerialForm.toSerial').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.002')"/>';
            $('lookupSerialForm.toSerial').select();
            $('lookupSerialForm.toSerial').focus();
            return false;
        }


        if(fromSerial != "" && toSerial != "" && (fromSerial * 1 > toSerial * 1)) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.003')"/>';
            $('lookupSerialForm.fromSerial').select();
            $('lookupSerialForm.fromSerial').focus();
            return false;
        }

        //trim cac truong can thiet
        $('lookupSerialForm.fromSerial').value = trim($('lookupSerialForm.fromSerial').value);
        $('lookupSerialForm.toSerial').value = trim($('lookupSerialForm.toSerial').value);
        /*
        var _myWidget = dojo.widget.byId("lookupSerialForm.shopCode");
        alert('>' + _myWidget.textInputNode.value + '<');
        _myWidget.textInputNode.value = trim(_myWidget.textInputNode.value);
        alert('>' + _myWidget.textInputNode.value + '<');*/


        return true;
    }

    //cap nhat lai danh sach mat hang
    changeStockType = function(stockTypeId) {
        updateData("${contextPath}/lookupSerialAction!changeStockType.do?target=lookupSerialForm.stockModelId&stockTypeId=" + stockTypeId);
    }

</script>
