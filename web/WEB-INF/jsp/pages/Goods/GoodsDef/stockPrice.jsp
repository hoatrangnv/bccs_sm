<%--
    Document   : price
    Created on : Apr 21, 2009, 3:31:56 PM
    Author     : tamdt1
    Desc       : hien thi thong tin ve gia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%


            if (request.getAttribute("listCurrency") == null) {
                request.setAttribute("listCurrency", request.getSession().getAttribute("listCurrency"));
            }

            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="divStockPrice" cssStyle="padding:3px; ">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult property="message" id="messageStockPrice" type="key"/>
    </div>

    <!-- phan thong tin ve gia mat hang -->
    <div class="divHasBorder">
        <s:form action="goodsDefAction!addOrEditPrice" theme="simple" method="post" id="priceForm">
            <s:token/>
            <s:hidden name="priceForm.stockModelId" id="priceForm.stockModelId"/>
            <s:hidden name="priceForm.priceId" id="priceForm.priceId"/>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:13%">
                        <tags:label key="MSG.GOD.041" required="${fn:escapeXml(requestScope.isAddMode)}"/>
                        <!--                        Loại giá-->
                        <%--s:if test="#request.isAddMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn" style="width:20%">
                        <tags:imSelect name="priceForm.type"
                                       id="priceForm.type"
                                       cssClass="txtInputFull"
                                       disabled="${!fn:escapeXml(requestScope.isAddMode)}"
                                       list="listPriceTypes"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.300"/>
                    </td>
                    <td style="width:13%">
                        <tags:label key="MSG.GOD.042" required="${fn:escapeXml(requestScope.isAddMode)}"/>
                        <!--                        Chính sách giá-->
                        <%--s:if test="#request.isAddMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn" style="width:20%">
                        <tags:imSelect name="priceForm.pricePolicy"
                                       id="priceForm.pricePolicy"
                                       cssClass="txtInputFull"
                                       disabled="${!fn:escapeXml(requestScope.isAddMode)}"
                                       list="listPricePolicies"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.301"/>
                    </td>
                    <td style="width:13%">
                        <tags:label key="Currency"/>
                    </td>
                    <td>
                        <tags:imSelect name="priceForm.currency"
                                       id="priceForm.currency"
                                       cssClass="txtInputFull"
                                       disabled="${!fn:escapeXml(requestScope.isAddMode)}"
                                       list="listCurrency"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="--Select Currency--"/>
                    </td>
                </tr>
                <tr>
                    <!-- gia mat hang va VAT khong duoc phep sua -->
                    <td>
                        <tags:label key="MSG.GOD.043" required="${fn:escapeXml(requestScope.isAddMode)}"/>
                        <!--                        Giá mặt hàng-->
                        <%--s:if test="#request.isAddMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="priceForm.price" id="priceForm.price" readonly="!#request.isAddMode" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.044" required="${fn:escapeXml(requestScope.isAddMode)}"/>
                        <!--                        VAT-->
                        <%--s:if test="#request.isAddMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="priceForm.vat" id="priceForm.vat" readonly="!#request.isAddMode" onkeyup="textFieldNF(this)" maxlength="15" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.045"/>
                        <!--                        Mô tả-->
                    </td>
                    <td>
                        <s:textfield name="priceForm.description" id="priceForm.description" readonly="%{!(#request.isAddMode || #request.isEditMode)}" maxlength="250" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.013" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        Trạng thái-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td--%>
                    <td class="oddColumn">
                        <tags:imSelect name="priceForm.status"
                                       id="priceForm.status"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.014" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        Ngày bắt đầu-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if></td--%>
                    <td class="oddColumn">
                        <tags:dateChooser property="priceForm.staDate" readOnly="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}" styleClass="txtInputFull"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.015" />
                        <!--                        Ngày kết thúc-->
                    </td>
                    <td>
                        <tags:dateChooser property="priceForm.endDate" readOnly="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}" styleClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.046" />
                        <!--                        Thời gian cam kết-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="priceForm.pledgeTime" id="priceForm.pledgeTime" readonly="%{!(#request.isAddMode || #request.isEditMode)}" onkeyup="textFieldNF(this)"  maxlength="10" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.047" />
                        <!--                        Mức cam kết-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="priceForm.pledgeAmount" id="priceForm.pledgeAmount" readonly="%{!(#request.isAddMode || #request.isEditMode)}" onkeyup="textFieldNF(this)" maxlength="20" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.048" />
                        <!--                        Số tháng ứng trước-->
                    </td>
                    <td>
                        <s:textfield name="priceForm.priorPay" id="priceForm.priorPay" readonly="%{!(#request.isAddMode || #request.isEditMode)}" onkeyup="textFieldNF(this)" maxlength="10" cssClass="txtInputFull" cssStyle="text-align:right; "/>
                    </td>
                </tr>

            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddOrEditPrice"
                             targets="divStockPrice"
                             formId="priceForm"
                             cssStyle="width:80px;"
                             confirm="true"
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             preAction="goodsDefAction!addOrEditPrice.do"
                             validateFunction="checkValidDataToAddOrEditPrice()"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplayPrice"
                             targets="divStockPrice"
                             formId="priceForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="goodsDefAction!displayPrice.do"
                             validateFunction="validDataBeforeDisplay()"
                             showLoadingText="true"/>

            </div>
            <script language="javscript">
                disableTab('sxdivStockModel');
                disableTab('sxdivStockDeposit');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddPrice"
                             targets="divStockPrice"
                             formId="priceForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.019"
                             preAction="goodsDefAction!prepareAddPrice.do"
                             showLoadingText="true"/>

                <!-- chi hien thi sua, xoa trong truong hop da co it nhat 1 phan tu -->
                <s:if test="#attr.priceForm.priceId.compareTo(0L) > 0">
                    <tags:submit id="btnEditPrice"
                                 targets="divStockPrice"
                                 formId="priceForm"
                                 cssStyle="width:80px;"
                                 value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}"
                                 preAction="goodsDefAction!prepareEditPrice.do"
                                 showLoadingText="true"/>

                    <tags:submit id="btnDelPrice"
                                 targets="divStockPrice"
                                 formId="priceForm" disabled="true"
                                 cssStyle="width:80px;"
                                 value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}"
                                 confirm="true"
                                 confirmText="${fn:escapeXml(imDef:imGetText('MSG.GOD.049'))}"
                                 preAction="goodsDefAction!deletePrice.do"
                                 showLoadingText="true"/>

                </s:if>
                <s:else>
                    <tags:submit id="btnEditPrice" disabled="true"
                                 targets="divStockPrice"
                                 formId="priceForm"
                                 cssStyle="width:80px;"
                                 value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}"
                                 preAction="goodsDefAction!prepareEditPrice.do"
                                 showLoadingText="true"/>

                    <tags:submit id="btnDelPrice" disabled="true"
                                 targets="divStockPrice"
                                 formId="priceForm" 
                                 cssStyle="width:80px;"
                                 value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}"
                                 confirm="true"
                                 confirmText="${fn:escapeXml(imDef:imGetText('MSG.GOD.049'))}"
                                 preAction="goodsDefAction!deletePrice.do"
                                 showLoadingText="true"/>

                    <!--                    <input type="button" value="Sửa" disabled style="width:80px;">
                                        <input type="button" value="Xóa" disabled style="width:80px;">-->
                </s:else>
            </div>

            <script language="javscript">
                enableTab('sxdivStockModel');
                enableTab('sxdivStockDeposit');
            </script>

        </s:else>
    </div>

    <!-- phan hien thi danh sach gia thuoc mat hang -->
    <div>
        <jsp:include page="listPrices.jsp"/>
    </div>

</sx:div>

<script language="javascript">

    //dinh danh cho cac truong so
//    textFieldNF($('priceForm.price'));

    textFieldNF($('priceForm.vat'));
    textFieldNF($('priceForm.pledgeTime'));
    textFieldNF($('priceForm.pledgeAmount'));
    textFieldNF($('priceForm.priorPay'));


    //loai bo cac truong ko hop le truoc khi submit form (doi voi cac truong hop so Long)
    validDataBeforeDisplay = function() {
        $('priceForm.price').value = "";
        $('priceForm.vat').value = "";
        $('priceForm.pledgeTime').value = "";
        $('priceForm.pledgeAmount').value = "";
        $('priceForm.priorPay').value = "";
        return true;
    }

    //
    checkValidDataToAddOrEditPrice = function() {
        
        if(checkRequiredFields_price() && checkValidFields_price()) {
            return true;
        } else {
            return false;
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields_price = function() {
        if(trim($('priceForm.type').value) == "") {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Chọn loại giá";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.050')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.050"/>';
            $('priceForm.type').focus();
            return false;
        }
        if(trim($('priceForm.pricePolicy').value) == "") {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Chọn chính sách giá";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.051')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.051"/>';
            $('priceForm.pricePolicy').focus();
            return false;
        }
        if(trim($('priceForm.price').value) == "") {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Giá mặt hàng không được để trống";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.052')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.052"/>';
            $('priceForm.price').select();
            $('priceForm.price').focus();
            return false;
        }
        if(trim($('priceForm.vat').value) == "") {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. VAT không được để trống";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.053')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.053"/>';
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        if(trim($('priceForm.status').value) == "") {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Chọn trạng thái";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.025')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.025"/>';
            $('priceForm.status').focus();
            return false;
        }

        if(trim($('priceForm.currency').value) == "") {
            $( 'messageStockPrice' ).innerHTML = '<s:text name="Warring. Pls select currency!"/>';
            $('priceForm.currency').focus();
            return false;
        }

        return true;

    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields_price = function() {
        var price = $('priceForm.price').value.replace(/\,/g,""); //loai bo dau , trong chuoi
        if(!isDouble(trim(price))) {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Giá mặt hàng phải là số không âm";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.054')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.054"/>';
            $('priceForm.price').select();
            $('priceForm.price').focus();
            return false;
        }
        var vat =  trim($('priceForm.vat').value.replace(/\,/g,""));
        if(!isInteger(trim(vat))) {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. VAT phải là số không âm";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.055')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.055"/>';
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        if(vat < 0 || vat > 100) {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. VAT phải là số không âm nằm trong khoảng 0 đến 100";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.056')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.056"/>';
            $('priceForm.vat').select();
            $('priceForm.vat').focus();
            return false;
        }
        var pledgeTime = trim($('priceForm.pledgeTime').value.replace(/\,/g,""));
        if(pledgeTime != "" && !isInteger(pledgeTime)) {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Thời gian cam kết phải là số không âm";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.057')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.057"/>';
            $('priceForm.pledgeTime').select();
            $('priceForm.pledgeTime').focus();
            return false;
        }
        var pledgeAmount = trim($('priceForm.pledgeAmount').value.replace(/\,/g,""));
        if(pledgeAmount != "" && !isInteger(pledgeAmount)) {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Mức cam kết phải là số không âm";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.058')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.058"/>';
            $('priceForm.pledgeAmount').select();
            $('priceForm.pledgeAmount').focus();
            return false;
        }
        var priorPay = trim($('priceForm.priorPay').value.replace(/\,/g,""));
        if(priorPay != "" && !isInteger(priorPay)) {
            //            $('messageStockPrice').innerHTML = "!!!Lỗi. Số tháng ứng trước phải là số không âm";
            //            $( 'messageStockPrice' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.059')"/>';
            $( 'messageStockPrice' ).innerHTML = '<s:text name="MSG.GOD.059"/>';
            $('priceForm.priorPay').select();
            $('priceForm.priorPay').focus();
            return false;
        }

        //trim cac truogn can thiet
        $('priceForm.price').value = trim($('priceForm.price').value);
        $('priceForm.description').value = trim($('priceForm.description').value);
        $('priceForm.pledgeTime').value = trim($('priceForm.pledgeTime').value);
        $('priceForm.pledgeAmount').value = trim($('priceForm.pledgeAmount').value);
        $('priceForm.priorPay').value = trim($('priceForm.priorPay').value);

        return true;
    }

</script>
