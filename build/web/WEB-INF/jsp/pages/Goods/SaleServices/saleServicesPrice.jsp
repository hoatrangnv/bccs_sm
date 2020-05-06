<%-- 
    Document   : saleServicesPrice
    Created on : Mar 20, 2009, 2:16:30 PM
    Author     : tamdt1
    Desc       : hien thi danh sach cac gia cua saleService
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
	
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="divSaleServicesPrice" cssStyle="padding:3px; ">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="messageSaleServicesPrice" property="message" type="key"/>
    </div>

    <!-- phan thong tin ve gia dich vu -->
    <div class="divHasBorder">
        <s:form action="saleServicesAction!addOrEditSaleServicesPrice.do" theme="simple" id="saleServicesPriceForm" method="post">
<s:token/>

            <s:hidden name="saleServicesPriceForm.saleServicesPriceId" id="saleServicesPriceForm.saleServicesPriceId"/>
            <s:hidden name="saleServicesPriceForm.saleServicesId" id="saleServicesPriceForm.saleServicesId"/>

            <table class="inputTbl4Col">
                <tr>
                    <td style="width:10%;">
                        <tags:label key="MSG.GOD.042" required="true"/>
                        <!--                        Chính sách-->
                        <%--s:if test="#request.isAddMode"><font color="red">*</font></s:if></td--%>
                    <td>
                        <tags:imSelect name="saleServicesPriceForm.pricePolicy"
                                       id="pricePolicy"
                                       cssClass="txtInputFull"
                                       disabled="${!fn:escapeXml(requestScope.isAddMode)}"
                                       list="listPricePolicies"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.301"/>
                    </td>

                    <td style="width:13%">
                        <tags:label key="Currency"/>
                    </td>
                    <td colspan="1">
                        <tags:imSelect name="saleServicesPriceForm.currency"
                                       id="saleServicesPriceForm.currency"
                                       cssClass="txtInputFull"
                                       disabled="${!fn:escapeXml(requestScope.isAddMode)}"
                                       list="listCurrency"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="--Select Currency--"/>
                    </td>

                </tr>
                <tr>
                    <td style="width:10%;" class="label">
                        <tags:label key="MSG.GOD.083" required="true"/>
                        <!--                        Giá-->
                        <%--s:if test="#request.isAddMode"><font color="red">*</font></s:if></td--%>
                    </td>
                    <td class="text" style="width:23%;">
                        <s:textfield name="saleServicesPriceForm.price" cssStyle="text-align:right; " id="saleServicesPriceForm.price" maxlength="13" onkeyup="textFieldNF(this)" readonly="!#request.isAddMode" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:10%;" class="label">
                        <tags:label key="MSG.GOD.044" required="true"/>
                        <!--                        VAT-->
                        <%--s:if test="#request.isAddMode"><font color="red">*</font></s:if></td--%>
                    </td>
                    <td class="text" style="width:23%;">
                        <s:textfield name="saleServicesPriceForm.vat" id="saleServicesPriceForm.vat" maxlength="10" readonly="!#request.isAddMode" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.117" required="true"/>
                        <!--                        Từ ngày117-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <tags:dateChooser id="saleServicesPriceForm.staDate"
                                          property="saleServicesPriceForm.staDate"
                                          readOnly="${!fn:escapeXml(requestScope.isAddMode) && !fn:escapeXml(requestScope.isEditMode)}"
                                          styleClass="txtInputFull"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.118" required="false"/>
                        <!--                        Đến ngày118-->
                    </td>
                    <td class="oddColumn">
                        <tags:dateChooser id="saleServicesPriceForm.endDate"
                                          property="saleServicesPriceForm.endDate"
                                          readOnly="${!fn:escapeXml(requestScope.isAddMode) && !fn:escapeXml(requestScope.isEditMode)}"
                                          styleClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.013" required="true"/>
                        <!--                        Trạng thái013-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td>
                        <tags:imSelect name="saleServicesPriceForm.status"
                                       id="saleServicesPriceForm.status"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       cssClass="txtInputFull"
                                       list="1:MSG.GOD.297, 2:MSG.GOD.274"/>
                    </td>

                    <td>
                        <tags:label key="MSG.GOD.119" required="false"/>
                        <!--                        Mô tả-->
                    </td>
                    <td colspan="1">
                        <s:textfield name="saleServicesPriceForm.description" id="description"
                                     disabled="%{!(#request.isAddMode || #request.isEditMode)}"
                                     maxlength="250" cssClass="txtInputFull"/>
                    </td>
                </tr>


            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top">
                <tags:submit id="btnAddSaleServicesPrice"
                             targets="divSaleServicesPrice"
                             formId="saleServicesPriceForm"
                             cssStyle="width:80px;"
                             confirm="true"
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             preAction="saleServicesAction!addOrEditSaleServicesPrice.do"
                             validateFunction="checkValidSaleServicesPriceToAddOrEdit()"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplaySaleServicesPrice"
                             targets="divSaleServicesPrice"
                             formId="saleServicesPriceForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="saleServicesAction!displaySaleServicesPrice.do"
                             validateFunction="validDataBeforeDisplaySaleServicesPrice()"
                             showLoadingText="true"/>
            </div>
            <script language="javscript">
                disableTab('sxdivSaleServices');
                disableTab('sxdivSaleServicesModel');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top">
                <tags:submit id="btnPrepareAddSaleServicesPrice"
                             formId="saleServicesPriceForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="divSaleServicesPrice"
                             value="MSG.GOD.019"
                             preAction="saleServicesAction!prepareAddSaleServicesPrice.do"/>

                <!-- chi hien thi sua, xoa trong truong hop da co it nhat 1 phan tu -->
                <s:if test="saleServicesPriceForm.saleServicesPriceId.compareTo(0L) > 0">
                    <tags:submit id="btnPrepareEditSaleServicesPrice"
                                 formId="saleServicesPriceForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divSaleServicesPrice"
                                 value="MSG.GOD.020"
                                 preAction="saleServicesAction!prepareEditSaleServicesPrice.do"/>

                    <tags:submit id="btnDelSaleServicesPrice"
                                 formId="saleServicesPriceForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divSaleServicesPrice" disabled="true"
                                 confirm="true"
                                 confirmText="MSG.GOD.120"
                                 value="MSG.GOD.021"
                                 preAction="saleServicesAction!delSaleServicesPrice.do"/>
                </s:if>
                <s:else>
                    <button style="width:80px;" disabled>
                        <tags:label key="MSG.GOD.020"/>
                    </button>
                    <button style="width:80px;" disabled>
                        <tags:label key="MSG.GOD.021"/>
                    </button>

                    <!--                    <input type="button" value="Sửa" disabled style="width:80px;"/>
                                        <input type="button" value="Xóa" disabled style="width:80px;"/>-->
                </s:else>
            </div>
            <script language="javscript">
                enableTab('sxdivSaleServices');
                enableTab('sxdivSaleServicesModel');
            </script>
        </s:else>
    </div>

    <!-- danh sach gia dich vu ban hang -->
    <div>
        <jsp:include page="listSaleServicesPrices.jsp" />
    </div>

</sx:div>

<!-- focus vao truong dau tien -->
<s:if test="#request.isAddMode">
    <script language="javascript">
        //focus vao truong dau tien
        $('saleServicesPriceForm.price').select();
        $('saleServicesPriceForm.price').focus();

        //dinh danh cho truong price
//        textFieldNF($('saleServicesPriceForm.price'));
    </script>
</s:if>
<s:else>
    <script language="javascript">

        //dinh danh cho truong price
//        textFieldNF($('saleServicesPriceForm.price'));
    </script>
</s:else>



<script language="javascript">

    validDataBeforeDisplaySaleServicesPrice = function() {
        $('saleServicesPriceForm.price').value = "";
        $('saleServicesPriceForm.vat').value = "";
        return true;
    }


    checkValidSaleServicesPriceToAddOrEdit = function() {
        if(checkRequiredFields_saleServicesPrice() && checkValidFields_saleServicesPrice()) {
            return true;
        } else {
            return false;
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields_saleServicesPrice = function() {
        if(trim($('saleServicesPriceForm.price').value) == "") {
            //$('messageSaleServicesPrice').innerHTML='!!!Lỗi. Giá dịch vụ không được để trống';
            //            $('messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.021')"/>';
            $('messageSaleServicesPrice' ).innerHTML = '<s:text name="ERR.GOD.021"/>';
            $('saleServicesPriceForm.price').select();
            $('saleServicesPriceForm.price').focus();
            return false;
        }
        if(trim($('saleServicesPriceForm.vat').value) == "") {
            //$('messageSaleServicesPrice').innerHTML='!!!Lỗi. VAT không được để trống';
            //            $( 'messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.012')"/>';
            $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="ERR.GOD.012"/>';
            $('saleServicesPriceForm.vat').select();
            $('saleServicesPriceForm.vat').focus();
            return false;
        }

        /*
        var wgStarDate = dojo.widget.byId("saleServicesPriceForm.staDate");

        if(trim(wgStarDate.domNode.childNodes[1].value) == "") {
            $('messageSaleServicesPrice').innerHTML='!!!Lỗi. Ngày bắt đầu không được để trống';
            wgStarDate.domNode.childNodes[1].select();
            wgStarDate.domNode.childNodes[1].focus();
            return false;
        }*/

        if(trim($('pricePolicy').value) == "") {
            //$('messageSaleServicesPrice').innerHTML='!!!Lỗi. Chọn Chính sách giá';
            //            $( 'messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.014')"/>';
            $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="ERR.GOD.014"/>';
            $('pricePolicy').focus();
            return false;
        }


         if(trim($('saleServicesPriceForm.currency').value) == "") {
            $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="Warring. Pls select currency!"/>';
            $('saleServicesPriceForm.currency').focus();
            return false;
        }


    <%--if(trim($('saleServicesPriceForm.status').value) == "") {
        $('messageSaleServicesPrice').innerHTML='!!!Lỗi. Chọn trạng thái';
        $('saleServicesPriceForm.status').focus();
        return false;
    }--%>

            return true;
        }

        //ham kiem tra du lieu cac truong co hop le hay ko
        checkValidFields_saleServicesPrice = function() {
            var price = $('saleServicesPriceForm.price').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            if(!isDouble(trim(price))) {
                //            $('messageSaleServicesPrice').innerHTML= '!!!Lỗi. Giá dịch vụ phải là số không âm';
                //            $( 'messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.121')"/>';
                $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="MSG.GOD.121"/>';
                $('saleServicesPriceForm.price').select();
                $('saleServicesPriceForm.price').focus();
                return false;
            }
            if(!isInteger(trim($('saleServicesPriceForm.vat').value))) {
                //            $('messageSaleServicesPrice').innerHTML='!!!Lỗi. VAT phải là số không âm';
                //            $( 'messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.055')"/>';
                $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="MSG.GOD.055"/>';
                $('saleServicesPriceForm.vat').select();
                $('saleServicesPriceForm.vat').focus();
                return false;
            }
            if($('saleServicesPriceForm.vat').value < 0 || $('saleServicesPriceForm.vat').value > 100) {
                //            $('messageSaleServicesPrice').innerHTML='!!!Lỗi. VAT phải là số không âm nằm trong khoảng 0 đến 100';
                //            $( 'messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.056')"/>';
                $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="MSG.GOD.056"/>';
                $('saleServicesPriceForm.vat').select();
                $('saleServicesPriceForm.vat').focus();
                return false;
            }

            if(isHtmlTagFormat(trim($('description').value))) {
                //            $('messageSaleServicesPrice').innerHTML="!!!Lỗi. Trường mô tả không được chứa các thẻ HTML";
                //            $( 'messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.022')"/>';
                $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="MSG.GOD.022"/>';
                $('description').select();
                $('description').focus();
                return false;
            }

            if($('description').value.length > 250) {
                //            $('messageSaleServicesPrice').innerHTML="!!!Lỗi. Trường mô tả quá dài";
                //            $( 'messageSaleServicesPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.123')"/>';
                $( 'messageSaleServicesPrice' ).innerHTML = '<s:text name="MSG.GOD.123"/>';
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



