<%--
    Document   : PayFeesInvoice
    Created on : Jan 20, 2009
    Author     : Tuannv
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>


<fieldset style="width:100%; background-color: #F5F5F5; ">
    <legend class="transparent">Thông tin tham số</legend>

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult property="message" id="message" type="key"/>
    </div>

    <!-- thong tin tham so -->
    <s:form action="CommCountersAction!addCommCountersParam.do" id="commCountersForm" method="post" theme="simple">
        <s:token/>

        <s:hidden name="commCountersForm.counterId"/>

        <table class="inputTbl">
            <tr>
                <td>Tên tham số (<font color="red">*</font>)</td>
                <td>
                    <s:textfield name="commCountersForm.paramName" id="commCountersForm.paramName" maxlength="80" cssClass="txtInput"/>
                </td>
                <td>Kiểu dữ liệu (<font color="red">*</font>)</td>
                <td>
                    <s:select name="commCountersForm.dataType" id="commCountersForm.dataType"
                              cssClass="txtInput"
                              list="#{'Varchar2':'Varchar2','Date':'Date','Number':'Number','Nvarchar2':'Nvarchar2'}"
                              headerKey="" headerValue="--Chọn kiểu dữ liệu--"/>
                </td>
                <td style="text-align:right; ">
                    <input type="button" onclick="submitForm()" style="width:100px; " value="Thêm tham số">
                </td>
            </tr>
        </table>
        <s:token/>
    </s:form>

</fieldset>  

<br />

<fieldset style="width:100%; background-color: #F5F5F5; ">
    <legend class="transparent">Danh sách tham số bộ đếm</legend>
    <div id="lstcountersParam">
        <jsp:include page="CountersParamResult.jsp"/>
    </div>
</fieldset>

<br />
<div align="center">
    <input type="button" value="Đóng" style="width:80px; " onclick="closeForm()">
</div>

<script type="text/javascript" language="javascript">
    //dong form popup

    closeForm = function() {
        window.close();
    }

    //submit form
    submitForm = function() {
        if(checkValidate()){
            $('commCountersForm.paramName').value = trim($('commCountersForm.paramName').value);
            $('commCountersForm').action = "CommCountersAction!addCommCountersParam.do?"+token.getTokenParamString();
            $('commCountersForm').submit();
        }
    }
    

    //kiem tra cac dieu kien hop le truoc khi submit form
    checkValidate = function(){
        if (trim($('commCountersForm.paramName').value) == ""){
            $('message').innerHTML = "!!!Lỗi. Nhập tên tham số";
            $('commCountersForm.paramName').select();
            $('commCountersForm.paramName').focus();
            return false;
        }

        if (trim($('commCountersForm.dataType').value) == ""){
            $('message').innerHTML = "!!!Lỗi. Chọn kiểu dữ liệu";
            $('commCountersForm.dataType').focus();
            return false;
        }

        return true;

    }

</script>

