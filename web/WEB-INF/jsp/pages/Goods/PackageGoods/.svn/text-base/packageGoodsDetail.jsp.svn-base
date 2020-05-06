<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : pacakgeGoodsDetail
    Created on : Mar 16, 2009, 7:41:10 AM
    Author     : tamdt1
--%>

<%--
    Note: hien thi thong tin ve goi hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
%>
<tags:imPanel title="MSG.GOD.292">

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

<!-- LAMNV START -->
<s:url action="packageGoodsAction!addOrEditPackageGoodsDetail.do" id="URLaddOrEditPackageGoodsDetail">
    <s:param name="struts.token.name" value="'struts.token'"/>
    <s:param name="struts.token" value="struts.token"/>
</s:url>
<!-- LAMNV STOP -->

    <!-- phan thong tin loai mat hang -->
    <s:form theme="simple" action="%{#URLaddOrEditPackageGoodsDetail}" id="saleServicesDetailForm" method="post">
<s:token/>

        <s:hidden name="saleServicesDetailForm.id" id="saleServicesDetailForm.id"/>
        <s:hidden name="saleServicesDetailForm.saleServicesId" id="saleServicesDetailForm.saleServicesId"/>

        <table class="inputTbl">
            <tr>
                <td><tags:label key="MSG.GOD.027" required="true"/></td>
                <td>
                    <%--<s:select name="saleServicesDetailForm.stockTypeId"
                              id="stockTypeId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn loại mặt hàng--"
                              list="%{#request.listStockTypes!=null ? #request.listStockTypes : #{}}"
                              listKey="stockTypeId" listValue="name"
                              onchange="updateCombo('stockTypeId','stockModelId','packageGoodsAction!getStockModel.do');"/>--%>
                    <tags:imSelect name="saleServicesDetailForm.stockTypeId" id="stockTypeId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.216"
                                   list="listStockTypes"
                                   listKey="stockTypeId"
                                   listValue="name"
                                   onchange="updateCombo('stockTypeId','stockModelId','packageGoodsAction!getStockModel.do');"/>
                </td>
            </tr>
            <tr>
                <td><tags:label key="MSG.sale.goods" required="true"/></td>
                <td>
                    <%--<s:select name="saleServicesDetailForm.stockModelId"
                              id="stockModelId"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="--Chọn mặt hàng--"
                              list="%{#request.listStockModel!=null ? #request.listStockModel : #{}}"
                              listKey="stockModelId" listValue="name"/>--%>
                    <tags:imSelect name="saleServicesDetailForm.stockModelId" id="stockModelId"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.GOD.217"
                                   list="listStockModel"
                                   listKey="stockModelId"
                                   listValue="name"/>
                </td>
            </tr>
            <tr>
                <td> <tags:label key="MSG.GOD.141"/></td>
                <td>
                    <s:textarea name="saleServicesDetailForm.notes" id="saleServicesDetailForm.notes" cssClass="txtInputFull"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu, dong popup -->
    <div align="center" style="width:100%; padding-bottom:5px; padding-top:15px;">
        <button style="width:80px;" onclick="addPackageGoodsDetail()"> ${fn:escapeXml(imDef:imGetText('MSG.GOD.016'))}</button>
        <button style="width:80px;" onclick="cancelAddPackageGoodsDetail()"> ${fn:escapeXml(imDef:imGetText('MSG.GOD.018'))}</button>
    </div>

</tags:imPanel>

<s:if test="#request.saleServicesDetailMode == 'closePopup'">
    <script language="javascript">
        window.opener.refreshListPackageGoodsDetail();
        window.close();
    </script>
</s:if>


<script language="javascript">
    //focus vao truogn dau tien
    $('stockTypeId').focus();

    addPackageGoodsDetail = function() {
        if(checkRequiredFields() && checkValidFields()) {            
            $('saleServicesDetailForm').submit();
        }
    }

    cancelAddPackageGoodsDetail = function() {
        window.close();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('stockTypeId').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.009')"/>';
            //$('message').innerHTML="!!!Lỗi. Chọn loại mặt hàng";
            $('stockTypeId').focus();
            return false;
        }
        if(trim($('stockModelId').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.010')"/>';
            //$('message').innerHTML="!!!Lỗi. Chọn mặt hàng";
            $('stockModelId').focus();
            return false;
        }
        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        if(isHtmlTagFormat(trim($('saleServicesDetailForm.notes').value))) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.006')"/>';
            //$('message').innerHTML="!!!Lỗi. Trường ghi chú không được chứa các thẻ HTML";
            $('saleServicesDetailForm.notes').select();
            $('saleServicesDetailForm.notes').focus();
            return false;
        }

        if($('saleServicesDetailForm.notes').value.length > 50) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.007')"/>';
            //$('message').innerHTML="!!!Lỗi. Trường ghi chú quá dài";
            $('saleServicesDetailForm.notes').select();
            $('saleServicesDetailForm.notes').focus();
            return false;
        }

        return true;
    }

</script>
