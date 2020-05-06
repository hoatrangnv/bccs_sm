<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : saleServicesPrice
    Created on : Mar 20, 2009, 2:16:30 PM
    Author     : tamdt1
--%>

<%--
    Note: hien thi danh sach cac gia cua saleService
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


<fieldset style="width:100%; background-color: #F5F5F5;">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.296'))}</legend>
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- LAMNV START -->
    <s:url action="packageGoodsAction!addOrEditPackageGoodsPrice.do" id="URLaddOrEditPackageGoodsPrice">
        <s:param name="struts.token.name" value="'struts.token'"/>
        <s:param name="struts.token" value="struts.token"/>
    </s:url>
    <!-- LAMNV STOP -->

    <!-- phan thong tin ve gia -->
    <s:form action="%{#URLaddOrEditPackageGoodsPrice}" theme="simple" id="saleServicesPriceForm" method="post">
<s:token/>

        <s:hidden name="saleServicesPriceForm.saleServicesPriceId" id="saleServicesPriceForm.saleServicesPriceId"/>
        <s:hidden name="saleServicesPriceForm.saleServicesId" id="saleServicesPriceForm.saleServicesId"/>
        <table class="inputTbl">
            <tr>
                <td><tags:label key="MSG.GOD.083" required="true"/> </td>
                <td class="oddColumn">
                    <s:textfield name="saleServicesPriceForm.price" id="saleServicesPriceForm.price" maxlength="10" onkeyup="textFieldNF(this)" readonly="!#request.isAddMode" cssClass="txtInputFull"/>
                </td>
                <td><tags:label key="MSG.GOD.044" required="true"/></td>
                <td>
                    <s:textfield name="saleServicesPriceForm.vat" id="saleServicesPriceForm.vat" maxlength="10" readonly="!#request.isAddMode" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td> <tags:label key="MSG.GOD.045"/></td>
                <td class="oddColumn">
                    <s:textfield name="saleServicesPriceForm.description" id="description" maxlength="250" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td><tags:label key="MSG.GOD.014" required="true"/></td>
                <td class="oddColumn">
                    <tags:dateChooser id="saleServicesPriceForm.staDate" property="saleServicesPriceForm.staDate" styleClass="txtInputFull"/>
                </td>
                <td><tags:label key="MSG.GOD.015"/></td>
                <td class="oddColumn">
                    <tags:dateChooser property="saleServicesPriceForm.endDate" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td><tags:label key="MSG.policy" required="true"/></td>
                <td class="oddColumn">
                    <%--<s:select name="saleServicesPriceForm.pricePolicy"
                              id="saleServicesPriceForm.pricePolicy"
                              cssClass="txtInputFull"
                              list="%{#request.listPricePolicies != null ? #request.listPricePolicies : #{}}"
                              listKey="code" listValue="name"
                              headerKey="" headerValue="--Chọn chính sách giá--"/>--%>
                    <tags:imSelect name="saleServicesPriceForm.pricePolicy" id="saleServicesPriceForm.pricePolicy"
                                   cssClass="txtInputFull"
                                   list="listPricePolicies"
                                   listKey="code" listValue="name"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChoosePricePolicy"/>
                </td>
                <td><tags:label key="MSG.GOD.013" required="true"/></td>
                <td>
                    <%--<s:select name="saleServicesPriceForm.status"
                              id="saleServicesPriceForm.status"
                              cssClass="txtInputFull"
                              list="#{'1':'Hoạt động', '2':'Không hoạt động'}"/>--%>
                    <tags:imSelect name="saleServicesPriceForm.status" id="saleServicesPriceForm.status"
                                   cssClass="txtInputFull"
                                   list="1: MSG.GOD.297,2:MSG.GOD.274"
                                   listKey="code" listValue="name"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChoosePricePolicy"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu, dong popup -->
    <div align="center" style="width:100%; padding-bottom:5px; padding-top:15px;">
        <button style="width:80px;" onclick="addPrice()"><tags:label key="MSG.GOD.016"/></button>
        <button style="width:80px;" onclick="cancelAddPrice()"><tags:label key="MSG.GOD.018"/></button>
    </div>

</fieldset>

<s:if test="#request.packageGoodsPrice == 'closePopup'">
    <script language="javascript">
        window.opener.refreshPriceList();
        window.close();
    </script>
</s:if>

<script language="javascript">
    //focus vao truogn dau tien
    $('saleServicesPriceForm.price').select();
    $('saleServicesPriceForm.price').focus();


    //var dateExported= dojo.widget.byId("saleServicesPriceForm.staDate");
    //alert(dateExported);
    //dateExported.domNode.childNodes[1].focus();

    //
    //dinh danh cho truong price
    textFieldNF($('saleServicesPriceForm.price'));

    addPrice = function() {
        if(checkRequiredFields() && checkValidFields()) {
            $('saleServicesPriceForm').submit();
        }
    }

    cancelAddPrice = function() {
        window.close();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('saleServicesPriceForm.price').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.011')"/>';
            //$('message').innerHTML='!!!Lỗi. Giá gói hàng không được để trống';
            $('saleServicesPriceForm.price').select();
            $('saleServicesPriceForm.price').focus();
            return false;
        }
        if(trim($('saleServicesPriceForm.vat').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.012')"/>';
            //$('message').innerHTML='!!!Lỗi. VAT không được để trống';
            $('saleServicesPriceForm.vat').select();
            $('saleServicesPriceForm.vat').focus();
            return false;
        }


        var wgStarDate = dojo.widget.byId("saleServicesPriceForm.staDate");

        if(trim(wgStarDate.domNode.childNodes[1].value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.013')"/>';
            //$('message').innerHTML='!!!Lỗi. Ngày bắt đầu không được để trống';
            wgStarDate.domNode.childNodes[1].select();
            wgStarDate.domNode.childNodes[1].focus();
            return false;
        }

        if(trim($('saleServicesPriceForm.pricePolicy').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.014')"/>';
            //$('message').innerHTML='!!!Lỗi. Chọn Chính sách giá';
            $('saleServicesPriceForm.pricePolicy').focus();
            return false;
        }
        if(trim($('saleServicesPriceForm.status').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.015')"/>';
            //$('message').innerHTML='!!!Lỗi. Chọn trạng thái';
            $('saleServicesPriceForm.status').focus();
            return false;
        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var price = $('saleServicesPriceForm.price').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isInteger(trim(price))) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.016')"/>';
            //$('message').innerHTML= '!!!Lỗi. Giá dịch vụ phải là số không âm';
            $('saleServicesPriceForm.price').select();
            $('saleServicesPriceForm.price').focus();
            return false;
        }
        if(!isInteger(trim($('saleServicesPriceForm.vat').value))) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.017')"/>';
            //$('message').innerHTML='!!!Lỗi. VAT phải là số không âm';
            $('saleServicesPriceForm.vat').select();
            $('saleServicesPriceForm.vat').focus();
            return false;
        }
        if($('saleServicesPriceForm.vat').value < 0 || $('saleServicesPriceForm.vat').value > 100) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.018')"/>';
            //$('message').innerHTML='!!!Lỗi. VAT phải là số không âm nằm trong khoảng 0 đến 100';
            $('saleServicesPriceForm.vat').select();
            $('saleServicesPriceForm.vat').focus();
            return false;
        }

        if(isHtmlTagFormat(trim($('description').value))) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.019')"/>';
            //$('message').innerHTML="!!!Lỗi. Trường mô tả không được chứa các thẻ HTML";
            $('description').select();
            $('description').focus();
            return false;
        }

        if($('description').value.length > 250) {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.020')"/>';
            //$('message').innerHTML="!!!Lỗi. Trường mô tả quá dài";
            $('description').select();
            $('description').focus();
            return false;
        }

        //trim cac truong can thiet
        $('saleServicesPriceForm.price').value = trim($('saleServicesPriceForm.price').value);
        $('saleServicesPriceForm.vat').value = trim($('saleServicesPriceForm.vat').value);
        $('description').value = trim($('description').value);

        return true;
    }


</script>



