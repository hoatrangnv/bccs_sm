<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : detailsdebit
    Created on : Dec 5, 2012, 9:15:15 AM
    Author     : ITHC
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="L.200057">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- phan thong tin ve staff -->
    <s:form action="channelAction!prepareDetailsDebit" theme="simple" enctype="multipart/form-data"  method="post" id="shopForm">
        <s:token/>

        <table class="inputTbl">
            <tr>
                <td class="ollabel"><b><tags:label key="MSG.debit.info" /></b></td>
            </tr>
            <tr>
                <td class="label1"><tags:label key="L.200058"/></td>
                <td class="oddColumn" style="width: 25%">
                    <s:textfield name="shopForm.TotalDebitDisplay" id="shopForm.TotalDebit" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/>
                </td>
            </tr>
            <tr>
                <td class="label1"><tags:label key="L.200059" /></td>
                <td class="oddColumn">
                    <s:textfield name="shopForm.MaxDebitEmployeesDisplay" id="shopForm.MaxDebit" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/>
                </td>
            </tr>
            <tr>
                <td class="label1"><tags:label key="L.200060"/></td>
                <td class="oddColumn">
                    <s:textfield name="shopForm.MaxDebitDisplay" id="shopForm.MaxDebitEmployees" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/>
                </td>
            </tr>
            <tr>
                <td class="ollabel"><b><tags:label key="L.200054" /></b></td>
                <td class="oddColumn">
                    <b>   <s:textfield name="shopForm.totalMaxDebitDisplay" id="shopForm.TotalMaxDebit" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/></b>
                </td>
            </tr>
            <tr style="width: 20%; height: 20">

            </tr>

            <tr>
                <td class="ollabel"><b><tags:label key="L.200061" /></b></td>
            </tr>
            <tr>
                <td class="label1"><tags:label key="L.200062"/></td>
                <td class="oddColumn">
                    <s:textfield name="shopForm.TotalMaxCurrentDebitDisplay" id="shopForm.TotalMaxCurrentDebit" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/>
                </td>
            </tr>
            <tr>
                <td class="label1"><tags:label key="L.200063" /></td>
                <td class="oddColumn">
                    <s:textfield name="shopForm.MaxdCurrentDebitEmployeesDisplay" id="shopForm.MaxCurrentDebit" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/>
                </td>
            </tr>
            <tr>
                <td class="label1"><tags:label key="L.200064"/></td>
                <td class="oddColumn">
                    <s:textfield name="shopForm.MaxCurrentDebitDisplay" id="shopForm.MaxdCurrentDebitEmployees" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/>
                </td>
            </tr>
            <tr>
                <td class="ollabel"><b><tags:label key="L.200055" /></b></td>
                <td class="oddColumn">
                    <b>  <s:textfield name="shopForm.totalCurrentDebitDisplay" id="shopForm.TotalCurrentDebit" maxlength="1000" cssClass="txtInputFull" readonly="true"cssStyle="text-align:right;"/></b>
                </td>
            </tr>



        </table>
    </s:form>
    <!-- phan nut tac vu, dong popup -->

</tags:imPanel>



<s:if test="#request.staffMode == 'closePopup'">

    <script language="javascript">
        window.opener.refreshListStaff();
        window.close();
    </script>

</s:if>

<script language="javascript">
    textFieldNF($('shopForm.TotalMaxDebit'));
    textFieldNF($('shopForm.TotalCurrentDebit'));
    textFieldNF($('shopForm.TotalDebit'));
    textFieldNF($('shopForm.MaxDebit'));
    textFieldNF($('shopForm.TotalMaxCurrentDebit'));
    textFieldNF($('shopForm.MaxDebitEmployees'));
    textFieldNF($('shopForm.MaxCurrentDebit'));
    textFieldNF($('shopForm.MaxdCurrentDebitEmployees'));
    addStaff = function() {
        $('staffForm').submit();
        /*
        if(checkRequiredFields() && checkValidFields()) {
            $(priceForm).submit();
        }*/
    }

    cancelAddStaff = function() {
        window.close();
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('priceForm.price').value) == "") {
    <%--$('message').innerHTML = "Giá mặt hàng không được để trống";--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.028')"/>';
                $('priceForm.price').select();
                $('priceForm.price').focus();
                return false;
            }
            if(trim($('priceForm.vat').value) == "") {
    <%--$('message').innerHTML = "VAT không được để trống";--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.029')"/>';
                $('priceForm.vat').select();
                $('priceForm.vat').focus();
                return false;
            }
            /*
        if(trim($('saleServicesPriceForm.staDate').value) == "") {
            alert('Ngày bắt đầu không được để trống');
            $('saleServicesPriceForm.staDate').select();
            $('saleServicesPriceForm.staDate').focus();
            return false;
        }
             */
            if(trim($('priceForm.pricePolicy').value) == "") {
    <%--$('message').innerHTML = "Chọn Chính sách giá";--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.040')"/>';
                $('priceForm.pricePolicy').focus();
                return false;
            }
            if(trim($('priceForm.status').value) == "") {
    <%--$('message').innerHTML = "Chọn trạng thái";--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.031')"/>';
                $('priceForm.status').focus();
                return false;
            }

            return true;
        }

        //ham kiem tra du lieu cac truong co hop le hay ko
        checkValidFields = function() {
            if(!isInteger(trim($('priceForm.price').value))) {
    <%--$('message').innerHTML = "Giá dịch vụ phải là số không âm";--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.032')"/>';
                $('priceForm.price').select();
                $('priceForm.price').focus();
                return false;
            }
            if(!isInteger(trim($('priceForm.vat').value))) {
    <%--$('message').innerHTML = "VAT phải là số không âm";--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.033')"/>';
                $('priceForm.vat').select();
                $('priceForm.vat').focus();
                return false;
            }
            if($('priceForm.vat').value < 0 || $('priceForm.vat').value > 100) {
    <%--$('message').innerHTML = "VAT phải là số không âm nằm trong khoảng 0 đến 100";--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.034')"/>';
                $('priceForm.vat').select();
                $('priceForm.vat').focus();
                return false;
            }

            return true;
        }
        priceChange = function(id) {
            //alert('ukie');
            var tmp = $(id).value;
            tmp = tmp.replace(/\,/g,"");
            var tmp1 = addSeparatorsNF(tmp, '.', '.', ',');
            $(id).value = tmp1;
        }
        textFieldNF($('priceForm.price'));
</script>
