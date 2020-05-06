<%--
    Document   : addNewCommCounters
    Created on : Mar 19, 2009, 8:39:06 AM
    Author     : NamNt
--%>
<%--
    Note: Them bo dem khoan muc
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset style="width:100%; background-color: #F5F5F5; ">
    <tags:imPanel title="Thông tin bộ đếm">
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>
    <s:form action="CommCountersAction" theme="simple" enctype="multipart/form-data"  method="post" id="commCountersForm">
<s:token/>

        <s:hidden name="commCountersForm.counterId" id="commCountersForm.counterId"/>
        <table class="inputTbl6Col">
            <tr>
                <td class="label">Mã bộ đếm (<font color="red">*</font>)</td>
                <td class="text">
                    <s:textfield name="commCountersForm.counterCode" id="commCountersForm.counterCode" maxlength="50" cssClass="txtInputFull"/>
                </td>
                <td class="label">Tên bộ đếm (<font color="red">*</font>)</td>
                <td class="text">
                    <s:textfield name="commCountersForm.counterName" id="commCountersForm.counterName" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td class="label">Trạng thái (<font color="red">*</font>)</td>
                <td class="text">
                    <s:select name="commCountersForm.status" id="commCountersForm.status"
                              cssClass="txtInputFull"
                              list="#{'1':'Hiệu lực','2':'Hết hiệu lực'}"
                              headerKey="" headerValue="--Chọn trạng thái--"/>
                </td>
            </tr>
            <tr>
                <td class="label">Tên thủ tục (<font color="red">*</font>)</td>
                <td class="text">
                    <s:textfield name="commCountersForm.functionName" id="commCountersForm.functionName" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td class="label">Chi tiết thủ tục (<font color="red">*</font>)</td>
                <td class="text">
                    <s:textfield name="commCountersForm.detailfunctionName" id="commCountersForm.detailfunctionName" maxlength="100" cssClass="txtInputFull"/>
                </td>
                <td class="label">Template báo cáo (<font color="red">*</font>)</td>
                <td class="text">
                    <s:textfield name="commCountersForm.reportTemplate" id="commCountersForm.reportTemplate" maxlength="100" cssClass="txtInputFull"/>
                </td>
            </tr>
        </table>
    </s:form>
    <br/>
    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; ">
        <s:if test="#request.toEditCommCounters == 1">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="commCountersForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px; "
                       value="Cập nhật"  beforeNotifyTopics="CommCountersAction/editCommCounters"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="commCountersForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px; "
                       value="Bỏ qua"  beforeNotifyTopics="CommCountersAction/cancelEditCommCounters"/>
        </s:if>
        <s:else>
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="commCountersForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80px; "
                        value="Thêm mới"  beforeNotifyTopics="CommCountersAction/addCommCounters"/>
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="commCountersForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80px; "
                        value="Tìm kiếm"  beforeNotifyTopics="CommCountersAction/searchCommCounters"/>
        </s:else>
    </div>
</tags:imPanel>
<br />
<tags:imPanel title="Danh sách bộ đếm">
    <div id="listCommCounters">
        <jsp:include page="addNewCommCounterResult.jsp"/>
    </div>
</tags:imPanel>

<script type="text/javascript">

    //Add
    dojo.event.topic.subscribe("CommCountersAction/addCommCounters", function(event, widget){
        if (!checkRequiredFields() || !checkValidFields() || !confirm("Bạn có chắc chắn muốn thêm mới bộ đếm?")) {
            event.cancel = true;
            return;
        }
        widget.href = "CommCountersAction!addCommCounters.do?"+token.getTokenParamString();
    });

    //Search
    dojo.event.topic.subscribe("CommCountersAction/searchCommCounters", function(event, widget){
        //trim cac truong can thiet
        $('commCountersForm.counterCode').value = trim($('commCountersForm.counterCode').value);
        $('commCountersForm.counterName').value = trim($('commCountersForm.counterName').value);
        $('commCountersForm.functionName').value = trim($('commCountersForm.functionName').value);
        $('commCountersForm.detailfunctionName').value = trim($('commCountersForm.detailfunctionName').value);
        $('commCountersForm.reportTemplate').value = trim($('commCountersForm.reportTemplate').value);

        widget.href = "CommCountersAction!searchCommCounters.do";
    });

    //Update
    dojo.event.topic.subscribe("CommCountersAction/editCommCounters", function(event, widget){
        if (!checkRequiredFields() || !checkValidFields() || !confirm("Bạn có chắc chắn muốn sửa thông tin bộ đếm?")) {
            event.cancel = true;
            return;
        }
        widget.href = "CommCountersAction!editCommCounters.do?"+token.getTokenParamString();
 
    });

    //Cancel Update
    dojo.event.topic.subscribe("CommCountersAction/cancelEditCommCounters", function(event, widget){
        widget.href = "CommCountersAction!cancelEditCommCounter.do";
    });

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('commCountersForm.counterCode').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Mã bộ đếm không được để trống";
            $('commCountersForm.counterCode').select();
            $('commCountersForm.counterCode').focus();
            return false;
        }
        if(trim($('commCountersForm.counterName').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Tên bộ đếm không được để trống";
            $('commCountersForm.counterName').select();
            $('commCountersForm.counterName').focus();
            return false;
        }
        if(trim($('commCountersForm.status').value) == "") {
            $('message').innerHTML = "Lỗi. Trạng thái không được để trống";
            $('commCountersForm.status').focus();
            return false;
        }
        if(trim($('commCountersForm.functionName').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Tên thủ tục không được để trống";
            $('commCountersForm.functionName').select();
            $('commCountersForm.functionName').focus();
            return false;
        }
        if(trim($('commCountersForm.detailfunctionName').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Chi tiết thủ tục không được để trống";
            $('commCountersForm.detailfunctionName').select();
            $('commCountersForm.detailfunctionName').focus();
            return false;
        }
        if(trim($('commCountersForm.reportTemplate').value) == "") {
            $('message').innerHTML = "!!!Lỗi. Template báo cáo không được để trống";
            $('commCountersForm.reportTemplate').select();
            $('commCountersForm.reportTemplate').focus();
            return false;
        }
        return true;
    }

    //ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        if(!isValidInput($('commCountersForm.counterCode').value, false, false)) {
            $('message').innerHTML = "!!!Lỗi. Mã bộ đếm chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('commCountersForm.counterCode').select();
            $('commCountersForm.counterCode').focus();
            return false;
        }

        //trim cac truong can thiet
        $('commCountersForm.counterCode').value = trim($('commCountersForm.counterCode').value);
        $('commCountersForm.counterName').value = trim($('commCountersForm.counterName').value);
        $('commCountersForm.functionName').value = trim($('commCountersForm.functionName').value);
        $('commCountersForm.detailfunctionName').value = trim($('commCountersForm.detailfunctionName').value);
        $('commCountersForm.reportTemplate').value = trim($('commCountersForm.reportTemplate').value);
        return true;
    }
</script>



