<%--
    Document   : stockModel
    Created on : Apr 16, 2009, 7:09:21 PM
    Author     : tamdt1
    Desc       : hien thi thong tin cua mat hang
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
    request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="divStockModel" cssStyle="padding:3px; ">
    <!-- hien thi message -->
    <div>
        <tags:displayResult property="message" id="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve mat hang -->
    <div class="divHasBorder">
        <s:form action="goodsDefAction" theme="simple" method="post" id="stockModelForm">
            <s:token/>

            <s:hidden name="stockModelForm.stockModelId" id="stockModelForm.stockModelId"/>
            <!-- TruongNQ5 20140725 R6237 ID ban ghi cua 2 chi tieu -->
            <s:hidden name="stockModelForm.serviceIndex" id="serviceIndex"/>
            <s:hidden name="stockModelForm.serviceQualityIndex" id="serviceQualityIndex"/>
            <!-- trong truong hop cac combobox bi disable phai them cac bien hidden de luu tru cac gia tri nay -->
            <s:if test="!(#request.isAddMode || #request.isEditMode)">
                <s:hidden name="stockModelForm.checkSerial" id="stockModelForm.checkSerial"/>
                <s:hidden name="stockModelForm.checkDial" id="stockModelForm.checkDial"/>
                <s:hidden name="stockModelForm.checkStockChannelInfor" id="stockModelForm.checkStockChannelInfor"/>
                <s:hidden name="stockModelForm.checkItem" id="stockModelForm.checkItem"/>
            </s:if>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:13%; ">
                        <tags:label key="goods.group" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        Nhom hang hoa-->
                    </td>
                    <td class="oddColumn" style="width:26%; ">
                        <tags:imSelect name="stockModelForm.stockTypeGroup"
                                       id="stockModelForm.stockTypeGroup"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       list="1:Mobile,2:AP"
                                       />
                    </td>
                </tr>
                <tr>
                    <td style="width:13%; ">
                        <tags:label key="MSG.GOD.027" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        Loại mặt hàng-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn" style="width:26%; ">
                        <tags:imSelect name="stockModelForm.stockTypeId"
                                       id="stockModelForm.stockTypeId"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       list="listStockTypes"
                                       listKey="stockTypeId" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.216"/>
                    </td>
                    <td style="width:13%; ">
                        <tags:label key="MSG.GOD.028" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                    </td>
                    <td class="oddColumn" style="width:26%; ">
                        <tags:imSelect name="stockModelForm.telecomServiceId"
                                       id="stockModelForm.telecomServiceId"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"
                                       list="listTelecomServices"
                                       listKey="telecomServiceId" listValue="telServiceName"
                                       headerKey="" headerValue="MSG.selectServiceType"/>
                    </td>
                    <td>
                        <s:checkbox name="stockModelForm.checkSerial" id="stockModelForm.checkSerial" disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.029'))}
                        <!--                        Quản lý serial-->
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.007" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        Mã mặt hàng-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="stockModelForm.stockModelCode" id="stockModelForm.stockModelCode" maxlength="15" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.008" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/>
                        <!--                        Tên mặt hàng-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="stockModelForm.name" id="stockModelForm.name" maxlength="50" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                    <td>
                        <s:checkbox name="stockModelForm.checkDial" id="stockModelForm.checkDial" disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.030'))}
                        <!--                        Có thể bốc thăm-->
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.032" required="${(fn:escapeXml(requestScope.isAddMode) || fn:escapeXml(requestScope.isEditMode))}"/>
                        <!--                        Đơn vị-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <tags:imSelect name="stockModelForm.unit"
                                       id="stockModelForm.unit"
                                       cssClass="txtInputFull"
                                       disabled="${fn:escapeXml(!(requestScope.isAddMode || requestScope.isEditMode))}"
                                       list="listStockModelUnits"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.299"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.031"/>
                        <!--                        Ghi chú-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="stockModelForm.notes" id="stockModelForm.notes" maxlength="240" readonly="%{!(#request.isAddMode || #request.isEditMode)}" cssClass="txtInputFull"/>
                    </td>
                    <td>
                        <s:checkbox name="stockModelForm.checkStockChannelInfor" id="stockModelForm.checkStockChannelInfor" disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                        ${fn:escapeXml(imDef:imGetText('MSG.GD.ManagementByChannel'))}
                        <!--                        Mat hang quan ly boi kenh-->
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.GOD.328"/></td>
                    <!--                    <td>Mã MH hạch toán</td>-->
                    <td class="oddColumn">
                        <s:textfield name="stockModelForm.accountingModelCode" id="stockModelForm.accountingModelCode" maxlength="15" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                    <td><tags:label key="MSG.GOD.329"/></td>
                    <!--                    <td>Tên MH hạch toán</td>-->
                    <td class="oddColumn">
                        <s:textfield name="stockModelForm.accountingModelName" id="stockModelForm.accountingModelName" maxlength="50" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                    <td>
                        <s:checkbox name="stockModelForm.checkItem" id="stockModelForm.checkItem" disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                        ${fn:escapeXml(imDef:imGetText('goods.item'))}
                        <!--                        check la vat tu-->
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="goods.source.price"/></td>
                    <!--Gia von-->
                    <td class="oddColumn">
                        <s:textfield name="stockModelForm.sourcePriceStr" id="stockModelForm.sourcePriceStr" maxlength="15" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <!--TruongNQ5 201407 R6237 Tiêu chí doanh thu-->
                <tr>
                    <td style="width:13%; ">
                        <tags:label key="rvn.service.003" /><!--Tiêu chí doanh thu-->
                    </td>
                    <td class="oddColumn" style="width:26%; ">
                        <%--<tags:imSelect name="stockModelForm.rvnServiceId"
                                       id="stockModelForm.rvnServiceId"
                                       cssClass="txtInputFull"
                                       disabled="${!(requestScope.isAddMode || requestScope.isEditMode)}"
                                       list="listRvnService"
                                       listKey="id" listValue="name"
                                       headerKey="" headerValue="rvn.service.001"/>--%>

                        <s:if test="(#request.isAddMode == true || #request.isEditMode == true)">
                            <s:select name="stockModelForm.rvnServiceId"
                                      id="stockModelForm.rvnServiceId"
                                      disabled="false"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.001')}"
                                      list="%{#request.listRvnService != null ? #request.listRvnService :#{}}"
                                      listKey="id" listValue="name"/>
                        </s:if> 
                        <s:else>
                            <s:select name="stockModelForm.rvnServiceId"
                                      id="stockModelForm.rvnServiceId"
                                      disabled="true"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.001')}"
                                      list="%{#request.listRvnService != null ? #request.listRvnService :#{}}"
                                      listKey="id" listValue="name"/>    
                        </s:else>
                    </td>
                    <td style="width:13%; ">
                        <tags:label key="rvn.service.004" /><!--Tiêu chí doanh thu-->
                    </td>
                    <td class="oddColumn" style="width:26%; ">
                        <s:if test="(#request.isAddMode == true || #request.isEditMode == true)">
                            <s:select name="stockModelForm.rvnServiceQualityId"
                                      id="stockModelForm.rvnServiceQualityId"
                                      disabled="false"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.002')}"
                                      list="%{#request.listRvnServiceQuality != null ? #request.listRvnServiceQuality :#{}}"
                                      listKey="id" listValue="name"/>
                        </s:if> 
                        <s:else>
                            <s:select name="stockModelForm.rvnServiceQualityId"
                                      id="stockModelForm.rvnServiceQualityId"
                                      disabled="true"
                                      cssClass="txtInputFull"
                                      headerKey="" headerValue="%{getText('rvn.service.002')}"
                                      list="%{#request.listRvnServiceQuality != null ? #request.listRvnServiceQuality :#{}}"
                                      listKey="id" listValue="name"/>    
                        </s:else>
                        <%--<tags:imSelect name="stockModelForm.rvnServiceQualityId"
                                       id="stockModelForm.rvnServiceQualityId"
                                       cssClass="txtInputFull"
                                       disabled="${!(requestScope.isAddMode || requestScope.isEditMode)}"
                                       list="listRvnServiceQuality"
                                       listKey="id" listValue="name"
                                       headerKey="" headerValue="rvn.service.002"/>--%>
                    </td>
                </tr>
                <!--End TruongNQ5 201407 R6237 Tiêu chí doanh thu-->
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddOrEditStockModel"
                             targets="divStockModel"
                             formId="stockModelForm"
                             cssStyle="width:80px;"
                             confirm="true"
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             preAction="goodsDefAction!addOrEditStockModel.do"
                             validateFunction="checkValidDataToAddOrEditStockModel()"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplayStockModel"
                             targets="divDisplayInfo"
                             formId="stockModelForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="goodsDefAction!displayStockModel.do"
                             showLoadingText="true"/>
            </div>
            <script language="javscript">
                disableTab('sxdivStockPrice');
                disableTab('sxdivStockDeposit');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddStockModel"
                             targets="divStockModel"
                             formId="stockModelForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.019"
                             preAction="goodsDefAction!prepareAddStockModel.do"
                             showLoadingText="true"/>

                <!-- chi hien thi sua, xoa trong truong hop da co it nhat 1 phan tu -->
                <s:if test="#attr.stockModelForm.stockModelId.compareTo(0L) > 0">
                    <tags:submit id="btnEditStockModel"
                                 targets="divStockModel"
                                 formId="stockModelForm"
                                 cssStyle="width:80px;"
                                 value="MSG.GOD.020"
                                 preAction="goodsDefAction!prepareEditStockModel.do"
                                 showLoadingText="true"/>

                    <tags:submit id="btnDelStockModel"
                                 targets="divStockModel"
                                 formId="stockModelForm"
                                 cssStyle="width:80px;"
                                 value="MSG.GOD.021"
                                 confirm="true"
                                 confirmText="MSG.GOD.268"
                                 preAction="goodsDefAction!delStockModel.do"
                                 showLoadingText="true"/>

                    <script language="javscript">
                        enableTab('sxdivStockPrice');
                        enableTab('sxdivStockDeposit');
                    </script>
                </s:if>
                <s:else>
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" disabled style="width:80px;">
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" disabled style="width:80px;">

                    <script language="javscript">
                        disableTab('sxdivStockPrice');
                        disableTab('sxdivStockDeposit');
                    </script>
                </s:else>
            </div>

        </s:else>

    </div>

    <!-- danh sach mat hang -->
    <div>
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.006'))}</legend>
            <br /><br /><br /><br /><br />
        </fieldset>
    </div>

</sx:div>

<script language="javascript">

    checkValidDataToAddOrEditStockModel = function() {
        if (checkRequiredFields() && checkValidFields() && checkRequiredRVNService()) {
            return true;
        } else {
            return false;
        }
    }
    //TruongNQ5 20140725 Check RVNServiceOutputId
    checkRequiredRVNService = function() {
        if (trim($('stockModelForm.rvnServiceId').value) == "" && trim($('stockModelForm.rvnServiceQualityId').value) == "") {
            $('message').innerHTML = '<s:property value="getText('err.rvn.service')"/>';//Bạn phải chọn một trong hai chỉ tiêu danh thu hoặc sản lượng.
            $('stockModelForm.rvnServiceId').select();
            $('stockModelForm.rvnServiceId').focus();
            return false;
        }
        return true;
    }
    //End TruongNQ5
    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        stockModelCode = $('stockModelForm.stockModelCode');
        if (stockModelCode != null && stockModelCode.value.trim() != null)
            stockModelCode.value = stockModelCode.value.trim();

        //truong loai mat hang
        if (trim($('stockModelForm.stockTypeId').value) == "") {
//            $('message').innerHTML = "!!!Lỗi. Chọn loại mặt hàng";
//            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.033')"/>';
            $('message').innerHTML = '<s:text name="MSG.GOD.033"/>';

            $('stockModelForm.stockTypeId').select();
            $('stockModelForm.stockTypeId').focus();
            return false;
        }
        //truong dich vu vien thong
        if (trim($('stockModelForm.telecomServiceId').value) == "") {
//            $('message').innerHTML = "!!!Lỗi. Chọn loại dịch vụ";
//            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.034')"/>';
            $('message').innerHTML = '<s:text name="MSG.GOD.034"/>';
            $('stockModelForm.telecomServiceId').focus();
            return false;
        }
        //truong ma mat hang
        if (trim($('stockModelForm.stockModelCode').value) == "") {
//            $('message').innerHTML = "!!!Lỗi. Nhập mã mặt hàng";
//            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.035')"/>';
            $('message').innerHTML = '<s:text name="MSG.GOD.035"/>';
            $('stockModelForm.stockModelCode').select();
            $('stockModelForm.stockModelCode').focus();
            return false;
        }
        //truong ten mat hang
        if (trim($('stockModelForm.name').value) == "") {
//            $('message').innerHTML = "!!!Lỗi. Nhập tên mặt hàng";
//            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.036')"/>';
            $('message').innerHTML = '<s:text name="MSG.GOD.036"/>';
            $('stockModelForm.name').select();
            $('stockModelForm.name').focus();
            return false;
        }
        //truong don vi
        if (trim($('stockModelForm.unit').value) == "") {
//            $('message').innerHTML = "!!!Lỗi. Chọn đơn vị tính";
//            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.037')"/>';
            $('message').innerHTML = '<s:text name="MSG.GOD.037"/>';
            $('stockModelForm.unit').focus();
            return false;
        }

        return true;
    }

    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        if (!isValidInput($('stockModelForm.stockModelCode').value, false, false)) {
//            $('message').innerHTML = "!!!Lỗi. Trường mã mặt hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
//            $( 'message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.038')"/>';
            $('message').innerHTML = '<s:text name="MSG.GOD.038"/>';
            $('stockModelForm.stockModelCode').select();
            $('stockModelForm.stockModelCode').focus();
            return false;
        }
        return true;
    }

</script>



