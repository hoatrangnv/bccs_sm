<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : saleServicesDetail
    Created on : Mar 16, 2009, 7:41:10 AM
    Author     : tamdt1
    Desc       : hien thi thong tin ve saleServiceDetail
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<%--<tags:imPanel title="Thông tin mặt hàng">--%>
<tags:imPanel title="MSG.GOD.103">

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- phan thong tin loai mat hang -->
    <s:form theme="simple" action="saleServicesAction!addOrEditSaleServicesDetail.do" id="saleServicesDetailForm" method="post">
<s:token/>

        <s:hidden name="saleServicesDetailForm.id" id="saleServicesDetailForm.id"/>
        <s:hidden name="saleServicesDetailForm.saleServicesModelId" id="saleServicesDetailForm.saleServicesModelId"/>

        <table class="inputTbl2Col">
            <tr>
                <td style="width:35%;">
                    <tags:label key="MSG.GOD.027" required="true"/>
                    <!--
                    MSG.GOD.027
                    Loại mặt hàng<font color="red">*</font>-->
                </td>
                <td>
                    <tags:imSelect name="saleServicesDetailForm.stockTypeId"
                                   id="saleServicesDetailForm.stockTypeId"
                                   cssClass="txtInputFull"
                                   disabled="true"
                                   headerKey="" headerValue="MSG.GOD.216"
                                   list="listStockTypes"
                                   listKey="stockTypeId" listValue="name"/>
                    </td>
            </tr>
            <tr>
                <td>
                    <tags:label key="MSG.GOD.104" required="true"/>
                    <!--                    Mặt hàng<font color="red">*</font>-->
                </td>
                <td>
                    <tags:imSelect name="saleServicesDetailForm.stockModelId"
                                   id="saleServicesDetailForm.stockModelId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.217"
                                   list="listStockModels"
                                   listKey="stockModelId" listValue="name"
                                   onchange="changeStockModel()"/>
                </td>
            </tr>
            <tr>
                <td>
                    <tags:label key="MSG.GOD.043" required="true"/>
                    <!--                    Giá mặt hàng<font color="red">*</font>-->
                </td>
                <td>
                    <tags:imSelect name="saleServicesDetailForm.priceId"
                                   id="saleServicesDetailForm.priceId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.303"
                                   list="listPrices"
                                   listKey="priceId" listValue="priceAndDessciption"/>
                </td>
            </tr>
            <tr>
                <td>
                    <tags:label key="MSG.GOD.031"/>
                    <!--                    Ghi chú-->
                </td>
                <td>
                    <s:textarea name="saleServicesDetailForm.notes" id="saleServicesDetailForm.notes" cssClass="txtInputFull"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu, dong popup -->
    <div align="center" style="width:100%; padding-bottom:5px; padding-top:15px;">
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.016'))}" onclick="addSaleServicesDetail()" style="width:80px">
        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.018'))}" onclick="cancelAddSaleServicesDetail()" style="width:80px">
        <%--
                <button style="width:80px;" onclick="addSaleServicesDetail()">
                    <tags:label key="MSG.GOD.016"/>
        <!--            Đồng ý-->
                </button>
                <button style="width:80px;" onclick="cancelAddSaleServicesDetail()">
                    <tags:label key="MSG.GOD.018"/>
        <!--            Bỏ qua-->
                </button>
        --%>
    </div>

</tags:imPanel>

<s:if test="#request.saleServicesDetailMode == 'closePopup'">
    <script language="javascript">
        window.opener.refreshListSaleServicesModel();
        window.close();
    </script>
</s:if>


<script language="javascript">
    //focus vao truogn dau tien
    $('saleServicesDetailForm.stockModelId').focus();
    
    //xu ly su kien khi chon mat hang thay doi
    changeStockModel = function() {
        updateCombo('saleServicesDetailForm.stockModelId', 'saleServicesDetailForm.priceId', '${contextPath}/saleServicesAction!getDataForPriceCombobox.do');
    }


    addSaleServicesDetail = function() {
        if(checkRequiredFields() && checkValidFields()) {
            document.getElementById("saleServicesDetailForm").action="saleServicesAction!addOrEditSaleServicesDetail.do?"+token.getTokenParamString();
            $('saleServicesDetailForm').submit();
        }
    }

    cancelAddSaleServicesDetail = function() {
        window.close();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('saleServicesDetailForm.stockModelId').value) == "") {
            //            $('message').innerHTML="!!!Lỗi. Chọn mặt hàng";
            //            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.105')"/>';
            $( 'message' ).innerHTML = '<s:text name="MSG.GOD.105"/>';
            $('saleServicesDetailForm.stockModelId').focus();
            return false;
        }
        if(trim($('saleServicesDetailForm.priceId').value) == "") {
            //            $('message').innerHTML="!!!Lỗi. Chọn giá bán";
            //            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.106')"/>';
            $( 'message' ).innerHTML = '<s:text name="MSG.GOD.106"/>';
            $('saleServicesDetailForm.priceId').focus();
            return false;
        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        if(isHtmlTagFormat(trim($('saleServicesDetailForm.notes').value))) {
            //            $('message').innerHTML="!!!Lỗi. Trường ghi chú không được chứa các thẻ HTML";
            //            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.101')"/>';
            $( 'message' ).innerHTML = '<s:text name="MSG.GOD.101"/>';
            $('saleServicesDetailForm.notes').select();
            $('saleServicesDetailForm.notes').focus();
            return false;
        }

        if($('saleServicesDetailForm.notes').value.length > 50) {
            //            $('message').innerHTML="!!!Lỗi. Trường ghi chú quá dài";
            //            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.102')"/>';
            $( 'message' ).innerHTML = '<s:text name="MSG.GOD.102"/>';
            $('saleServicesDetailForm.notes').select();
            $('saleServicesDetailForm.notes').focus();
            return false;
        }

        return true;
    }

</script>
