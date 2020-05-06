<%-- 
    Document   : stockDeposit
    Created on : Sep 23, 2009, 2:50:15 PM
    Author     : Doan Thanh 8
    Desc       : thong tin doi tuong dat coc doi voi mat hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="divStockDeposit" cssStyle="padding:3px; ">
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult property="message" id="messageStockDeposit" type="key"/>
    </div>

    <!-- phan thong tin ve gia mat hang -->
    <div class="divHasBorder">
        <s:form action="goodsDefAction!addOrEditStockDeposit" theme="simple" method="post" id="stockDepositForm">
<s:token/>

            <s:hidden name="stockDepositForm.stockModelId" id="stockDepositForm.stockModelId"/>
            <s:hidden name="stockDepositForm.stockDepositId" id="stockDepositForm.stockDepositId"/>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:10%">
                        <tags:label key="MSG.GOD.011" required="%{#request.isEditMode}"/>
<!--                        Loại kênh-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn" style="width:20%">

                        <tags:imSelect name="stockDepositForm.chanelTypeId"
                                  id="stockDepositForm.chanelTypeId"
                                  cssClass="txtInputFull"
                                  disabled="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}"
                                  list="listChannelTypes"
                                  listKey="channelTypeId" listValue="name"
                                  headerKey="" headerValue="MSG.GOD.266"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.012" required="%{#request.isEditMode}"/>
<!--                        Số lượng ĐC tối đa-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td>
                        <s:textfield name="stockDepositForm.maxStock" id="stockDepositForm.maxStock" readonly="%{!(#request.isAddMode || #request.isEditMode)}" maxlength="10" cssClass="txtInputFull"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.027" required="%{#request.isEditMode}"/>
                    </td>
                    <td class="oddColumn">
                        <tags:imSelect name="stockDepositForm.transType"
                                       id="stockDepositForm.transType"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}"
                                       list="1:MSG.GOD.336,2:MSG.GOD.337"
                                       headerKey="" headerValue="MSG.GOD.216"/>
                    </td>                    
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.013" required="%{#request.isEditMode}"/>
<!--                        Trạng thái-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <tags:imSelect name="stockDepositForm.status"
                                  id="stockDepositForm.status"
                                  cssClass="txtInputFull"
                                  disabled="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}"
                                  list="1:MSG.GOD.002,0:MSG.GOD.003"
                                  headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.014" required="%{#request.isEditMode}"/>
<!--                        Ngày bắt đầu-->
                        <%--s:if test="#request.isAddMode || #request.isEditMode"><font color="red">*</font></s:if--%>
                    </td>
                    <td class="oddColumn">
                        <tags:dateChooser property="stockDepositForm.dateFrom" readOnly="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}" styleClass="txtInputFull"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.015"/>
<!--                        Ngày kết thúc-->
                    </td>
                    <td>
                        <tags:dateChooser property="stockDepositForm.dateTo" readOnly="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}" styleClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddOrEditStockDeposit"
                             targets="divStockDeposit"
                             formId="stockDepositForm"
                             cssStyle="width:80px;"
                             confirm="true"
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             preAction="goodsDefAction!addOrEditStockDeposit.do"
                             validateFunction="checkValidDataToAddOrEditStockDeposit()"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplayStockDeposit"
                             targets="divStockDeposit"
                             formId="stockDepositForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="goodsDefAction!displayStockDeposit.do"
                             showLoadingText="true"/>             
            </div>
            <script language="javscript">
                disableTab('sxdivStockModel');
                disableTab('sxdivStockPrice');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddStockdeposit"
                             targets="divStockDeposit"
                             formId="stockDepositForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.019"
                             preAction="goodsDefAction!prepareAddStockDeposit.do"
                             showLoadingText="true"/>

                <!-- chi hien thi sua, xoa trong truong hop da co it nhat 1 phan tu -->
                <s:if test="#attr.stockDepositForm.stockDepositId.compareTo(0L) > 0">
                    <tags:submit id="btnEditStockDeposit"
                                 targets="divStockDeposit"
                                 formId="stockDepositForm"
                                 cssStyle="width:80px;"
                                 value=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}"
                                 preAction="goodsDefAction!prepareEditStockDeposit.do"
                                 showLoadingText="true"/>

                    <tags:submit id="btnDelStockDeposit"
                                 targets="divStockDeposit"
                                 formId="stockDepositForm"
                                 cssStyle="width:80px;"
                                 value=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}"
                                 confirm="true"
                                 confirmText="${fn:escapeXml(imDef:imGetText('MSG.GOD.022'))}"
                                 preAction="goodsDefAction!deleteStockDeposit.do"
                                 showLoadingText="true"/>

                </s:if>
                <s:else>
                    <tags:submit id="btnEditStockDeposit" disabled="true"
                                 targets="divStockDeposit"
                                 formId="stockDepositForm"
                                 cssStyle="width:80px;"
                                 value="${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}"
                                 preAction="goodsDefAction!prepareEditStockDeposit.do"
                                 showLoadingText="true"/>

                     <tags:submit id="btnDelStockDeposit" disabled="true"
                                 targets="divStockDeposit"
                                 formId="stockDepositForm"
                                 cssStyle="width:80px;"
                                 value="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}"
                                 confirm="true"
                                 confirmText="${fn:escapeXml(imDef:imGetText('MSG.GOD.022'))}"
                                 preAction="goodsDefAction!deleteStockDeposit.do"
                                 showLoadingText="true"/>

<!--                    <input type="button" value="Sửa" disabled style="width:80px;">
                    <input type="button" value="Xóa" disabled style="width:80px;">-->
                </s:else>
            </div>

            <script language="javscript">
                enableTab('sxdivStockModel');
                enableTab('sxdivStockPrice');
            </script>

        </s:else>
    </div>

    <!-- phan hien thi danh sach doi tuong can dat coc cho mat hang -->
    <div>
        <jsp:include page="listStockDeposit.jsp"/>
    </div>

</sx:div>

<script>

    //
    checkValidDataToAddOrEditStockDeposit = function() {
        if(checkRequiredFields_deposit() && checkValidFields_deposit()) {
            return true;
        } else {
            return false;
        }
    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields_deposit = function() {
        if(trim($('stockDepositForm.chanelTypeId').value) == "") {
//            $('messageStockDeposit').innerHTML = "!!!Lỗi. Chọn loại kênh";
//            $( 'messageStockDeposit' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.023')"/>';
            $( 'messageStockDeposit' ).innerHTML = '<s:text name="MSG.GOD.023"/>';
            $('stockDepositForm.chanelTypeId').focus();
            return false;
        }
        if(trim($('stockDepositForm.maxStock').value) == "") {
//            $('messageStockDeposit').innerHTML = "!!!Lỗi. Số lượng đặt cọc tối đa không được để trống";
//            $( 'messageStockDeposit' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.024')"/>';
            $( 'messageStockDeposit' ).innerHTML = '<s:text name="MSG.GOD.024"/>';
            $('stockDepositForm.maxStock').select();
            $('stockDepositForm.maxStock').focus();
            return false;
        }
        if(trim($('stockDepositForm.transType').value) == "") {
//            $('messageStockDeposit').innerHTML = "!!!Lỗi. Chọn loại mặt hàng";
            $( 'messageStockDeposit' ).innerHTML = '<s:text name="ERR.STK.007"/>';
            $('stockDepositForm.transType').focus();
            return false;
        }        
        if(trim($('stockDepositForm.status').value) == "") {
//            $('messageStockDeposit').innerHTML = "!!!Lỗi. Chọn trạng thái";
//            $( 'messageStockDeposit' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.025')"/>';
            $( 'messageStockDeposit' ).innerHTML = '<s:text name="MSG.GOD.025"/>';
            $('stockDepositForm.status').focus();
            return false;
        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields_deposit = function() {
        if(!isInteger(trim($('stockDepositForm.maxStock').value))) {
//            $('messageStockDeposit').innerHTML = "!!!Lỗi. Số lượng đặt cọc tối đa phải là số không âm";
//            $( 'messageStockDeposit' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('MSG.GOD.026')"/>';
            $( 'messageStockDeposit' ).innerHTML = '<s:text name="MSG.GOD.026"/>';
            $('stockDepositForm.maxStock').select();
            $('stockDepositForm.maxStock').focus();
            return false;
        }

        return true;
    }

</script>



