<%-- 
    Document   : PrinterUser
    Created on : Jan 8, 2010, 9:51:58 AM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.printer.config.info">
    <!--fieldset style="width:100%; background-color: #F5F5F5;">
    <legend>ThÃ´ng tin phÃ­ ná»™p pháº¡t</legend>
    <!-- phan hien thi message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="paramMsg" type="key"/>
    </div>

    <!-- phan thong tin ve phi nop phat -->
    <s:form action="GetBookTypeAction" theme="simple" enctype="multipart/form-data" method="post" id="printerUserForm">
<s:token/>

        <s:hidden name="printerUserForm.id" id="printerUserForm.id"/>
        <!--s:hidden name="printerUserForm.fineItemPriceId" id="printerUserForm.fineItemPriceId"/-->
        <table class="inputTbl6Col">
            <sx:div id="displayPrinterUser">
                <tr>
                    <td class="label"><tags:label key="MSG.shop.code" /></td>
                    <td class="text">
                        <s:textfield name="printerUserForm.userName" id="printerUserForm.userName" readonly="true" maxlength="50" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="meessages.generates.ip_address" /></td>
                    <td class="text">
                        <s:textfield name="printerUserForm.ipAddress" id="printerUserForm.ipAddress" readonly="true" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.generates.X_amplitude" required="true"/></td>
                    <td class="text">
                        <s:textfield name="printerUserForm.XAmplitude" id="printerUserForm.XAmplitude" maxlength="3" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.generates.Y_amplitude" required="true"/> </td>
                    <td class="text">
                        <s:textfield name="printerUserForm.YAmplitude" id="printerUserForm.YAmplitude" maxlength="3" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.generates.invoice_type" required="true"/> </td>
                    <td class="text">
                        <tags:imSelect name="printerUserForm.invoiceType" id="printerUserForm.invoiceType"
                              cssClass="txtInputFull" disabled="false"
                              list="1:Windows:2: Linux "                              
                              headerKey="" headerValue="MSG.UTY.001"/>

                        <%--<s:select name="printerUserForm.invoiceType"
                                  id="printerUserForm.invoiceType"
                                  cssClass="txtInputFull"
                                  list="#{'1':'Windows', '2':'Linux'}"
                                  headerKey="" headerValue="--Chọn Hệ điều hành--"/>--%>
                    </td>
                    <td class="label"><tags:label key="MSG.generates.serial_code" required="true"/> </td>
                    <td class="text">
                        <tags:imSelect name="printerUserForm.serialCode"
                                  id="printerUserForm.serialCode"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="MSG.UTY.002"
                                  list="listBookType"
                                  listKey="serialCode" listValue="serialCode"/>

                        <%--<s:select name="printerUserForm.serialCode"
                                  id="printerUserForm.serialCode"
                                  cssClass="txtInputFull"
                                  list="%{#request.listBookType != null ? #request.listBookType : #{}}"
                                  listKey="serialCode" listValue="serialCode"
                                  headerKey="" headerValue="--Chọn mã Serial--"/>--%>
                    </td>
                </tr>
            </sx:div>
        </table>
    </s:form>

    <!-- phan nut tac vu, dong popup -->
    <div align="center" style="padding-bottom:5px;">
        <br />
        <s:if test="#session.toEditPrinterUser != 1">
            <tags:submit formId="printerUserForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.generates.addNew"
                         preAction="GetBookTypeAction!addOrEditPrinterUser.do"/>
            <tags:submit formId="printerUserForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.find"
                         preAction="GetBookTypeAction!searchPrinterUser.do"/>

           <%-- <sx:submit parseContent="true" executeScripts="true"
                       formId="printerUserForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       value="Thêm mới"  beforeNotifyTopics="GetBookTypeAction/addOrEditPrinterUser"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="printerUserForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       value="Tìm kiếm"  beforeNotifyTopics="GetBookTypeAction/searchPrinterUser"/>--%>
            <%--<input type="button" style="width:80px;" onclick="addPrinterUser();"  value="Thêm mới"/>
            <input type="button" style="width:80px;" onclick="searchPrinterUser();"  value="Tìm kiếm"/>--%>
        </s:if>
        <s:else>
             <tags:submit formId="printerUserForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.generates.addNew"
                         preAction="GetBookTypeAction!addOrEditPrinterUser.do"/>
            <tags:submit formId="printerUserForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.cancel"
                         preAction="GetBookTypeAction!preparePagePrinterUser.do"/>

          <%--  <sx:submit parseContent="true" executeScripts="true"
                       formId="printerUserForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       value="Đồng ý"  beforeNotifyTopics="GetBookTypeAction/addOrEditPrinterUser"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="printerUserForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       value="Bỏ qua"  beforeNotifyTopics="GetBookTypeAction/cancel"/>--%>
            <%--<input type="button" style="width:80px;" onclick="editParamBookType();"  value="Đồng ý"/>
            <input type="button" style="width:80px;" onclick="cancel();"  value="Bỏ qua"/--%>
        </s:else>
    </div>

</tags:imPanel>

<div id="listBoards">
    <tags:imPanel title="MSG.printer.config.list">
        <jsp:include page="listPrinterUser.jsp"/>
    </tags:imPanel>
</div>



<script type="text/javascript" language="javascript">
    
    dojo.event.topic.subscribe("GetBookTypeAction/addOrEditPrinterUser", function(event, widget){
        if (checkRequiredFields()) {
            widget.href = "GetBookTypeAction!addOrEditPrinterUser.do?"+ token.getTokenParamString();
        } else {
            event.cancel = true;
        }
    });
    dojo.event.topic.subscribe("GetBookTypeAction/searchPrinterUser", function(event, widget){

        widget.href = "GetBookTypeAction!searchPrinterUser.do?"+ token.getTokenParamString();

    });
    dojo.event.topic.subscribe("GetBookTypeAction/cancel", function(event, widget){

        widget.href = "GetBookTypeAction!preparePagePrinterUser.do?"+ token.getTokenParamString();

    });
    
    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        
        if(trim($('printerUserForm.XAmplitude').value) == "") {
            //$('message').innerHTML = "!!!Lỗi. Biên độ X không được để trống ";
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.UTY.021')" />';
            $('printerUserForm.XAmplitude').select();
            $('printerUserForm.XAmplitude').focus();
            return false;
        }
        if(trim($('printerUserForm.YAmplitude').value) == "") {
            //$('message').innerHTML = "!!!Lỗi. Biên độ Y không được để trống ";
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.UTY.022')" />';
            $('printerUserForm.YAmplitude').select();
            $('printerUserForm.YAmplitude').focus();
            return false;
        }
        if(trim($('printerUserForm.invoiceType').value) == "") {
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn hệ điều hành";
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.UTY.023')" />';
            $('printerUserForm.invoiceType').focus();
            return false;
        }
        if(trim($('printerUserForm.serialCode').value) == "") {
            //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn mã Serial";
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.UTY.024')" />';
            $('printerUserForm.serialCode').focus();
            return false;
        }

        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
             
        if(!isInteger(trim($('printerUserForm.XAmplitude').value))) {
            //$('message').innerHTML = "!!!Lỗi. Biên độ X phải là số nguyên";
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.UTY.025')" />';
            $('printerUserForm.XAmplitude').select();
            $('printerUserForm.XAmplitude').focus();
            return false;
        }
        if(!isInteger(trim($('printerUserForm.YAmplitude').value))) {
            //$('message').innerHTML = "!!!Lỗi. Biên độ Y phải là số nguyên";
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.UTY.026')" />';
            $('printerUserForm.YAmplitude').select();
            $('printerUserForm.YAmplitude').focus();
            return false;
        }
             
        $('printerUserForm.XAmplitude').value = trim($('printerUserForm.XAmplitude').value);
        $('printerUserForm.YAmplitude').value = trim($('printerUserForm.YAmplitude').value);

        return true;
    }



</script>
