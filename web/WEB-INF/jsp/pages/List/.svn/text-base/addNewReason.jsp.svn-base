<%--
    Document   : addNewReason
    Created on : Mar 7, 2009, 3:42:28 PM
    Author     : namnx
--%>
<%--
    Note: quan ly danh muc ly do
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.LST.021">

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg"  propertyValue="paramMsg" type="key" />
    </div>

    <!-- phan hien thi thong tin ly do -->
    <div>
        <s:form action="reasonAction" theme="simple" enctype="multipart/form-data"  method="post" id="reasonForm">
<s:token/>

            <s:hidden name="reasonForm.reasonId" id="reasonForm.reasonId"/>
            <tags:Reason toInputData="true" property="reasonForm"/>
        </s:form>
    </div>
    <br />
    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; ">
        <s:if test="#session.toEditReason == 1">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.accept"  beforeNotifyTopics="reasonAction/updateReason"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.cancel2"  beforeNotifyTopics="reasonAction/cancel"/>
        </s:if>
        <s:if test="#session.toEditReason == 2">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.copy"  beforeNotifyTopics="reasonAction/copyReason"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.cancel2"  beforeNotifyTopics="reasonAction/cancel"/>
        </s:if>
        <s:if test="#session.toEditReason == 0">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.generates.addNew"  beforeNotifyTopics="reasonAction/addNewReason"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.generates.find"  beforeNotifyTopics="reasonAction/searchReason"/>
        </s:if>
    </div>

</tags:imPanel>

<br />

<tags:imPanel title="MSG.LST.017">
    <div id="listReason">
        <jsp:include page="addNewReasonResult.jsp"/>
    </div>
</tags:imPanel>


<script type="text/javascript">

    //focus vao truong dau tien
    $('reasonForm.reasonCode').select();
    $('reasonForm.reasonCode').focus();

    validateForm=function(){
        $('displayResultMsgClient').innerHTML = "";
        if($('reasonForm.reasonCode').value ==null || $('reasonForm.reasonCode').value.trim() == ""){
            <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Nhập mã lý do";--%>
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.038')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.038"/>';
            $('reasonForm.reasonCode').select();
            $('reasonForm.reasonCode').focus();

            return false;
        }
        if(!isValidInput($('reasonForm.reasonCode').value.trim())){
            <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Mã lý do chỉ được phép chứa các ký tự A-Z, a-z, 0-9, _";--%>
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.039')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.039"/>';
            $('reasonForm.reasonCode').select();
            $('reasonForm.reasonCode').focus();

            return false;
        }


        if($('reasonForm.reasonName').value ==null || $('reasonForm.reasonName').value.trim() == ""){
            <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Nhập tên lý do";--%>
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.040')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.040"/>';
            $('reasonForm.reasonName').select();
            $('reasonForm.reasonName').focus();

            return false;
        }

/*
 *bo doan nay di do dai ca TrongLV them vao
        if(!isValidInput($('reasonForm.reasonName').value.trim())){
            <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Mã lý do chỉ được phép chứa các ký tự A-Z, a-z, 0-9, _";--%>
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.107')"/>';
            $('reasonForm.reasonName').select();
            $('reasonForm.reasonName').focus();

            return false;
        }*/

        if($('reasonForm.reasonType').value ==null || $('reasonForm.reasonType').value.trim() == ""){
            <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Chọn nhóm lý do";--%>
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.041')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.041"/>';
            $('reasonForm.reasonType').focus();

            return false;
        }
        if($('reasonForm.reasonStatus').value ==null || $('reasonForm.reasonStatus').value.trim() == ""){
            <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Chọn trạng thái";--%>
//            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.042')"/>';
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.042"/>';
            $('reasonForm.reasonStatus').focus();

            return false;
        }

        //trim cac truong can thiet
        $('reasonForm.reasonCode').value = trim($('reasonForm.reasonCode').value);
        $('reasonForm.reasonName').value = trim($('reasonForm.reasonName').value);
        $('reasonForm.reasonDescription').value = trim($('reasonForm.reasonDescription').value);

        return true;

    }

    //lang nghe, xu ly su kien khi click nut "Them moi"
    dojo.event.topic.subscribe("reasonAction/addNewReason", function(event, widget){
        if (!validateForm()) {
            event.cancel = true;
        }
        widget.href = "reasonAction!addNewReason.do?" + token.getTokenParamString();
    });

    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("reasonAction/searchReason", function(event, widget){
        //trim cac truong can thiet
        $('reasonForm.reasonCode').value = trim($('reasonForm.reasonCode').value);
        $('reasonForm.reasonName').value = trim($('reasonForm.reasonName').value);
        $('reasonForm.reasonDescription').value = trim($('reasonForm.reasonDescription').value);

        widget.href = "reasonAction!searchReason.do?"+  token.getTokenParamString();
    });

    //lang nghe, xu ly su kien khi click nut "Dong y"
    dojo.event.topic.subscribe("reasonAction/updateReason", function(event, widget){
        if (!validateForm()) {
            event.cancel = true;
        }
        widget.href = "reasonAction!editReason.do?"+  token.getTokenParamString();
    });
    //xử lý sự kiện onclick của nut copy
    dojo.event.topic.subscribe("reasonAction/copyReason", function(event, widget){
        if(!validateForm()) {
            event.cancel = true;
        }
        widget.href = "reasonAction!copyReason.do?"+  token.getTokenParamString();
    
});

//lang nghe, xu ly su kien khi click nut "Bo qua"
dojo.event.topic.subscribe("reasonAction/cancel", function(event, widget){
    widget.href = "reasonAction!preparePage.do?"+  token.getTokenParamString();
    //event: set event.cancel = true, to cancel request
});


//
delReason = function(reasonId) {
    if(confirm("Bạn có thực sự muốn xóa lý do này?")){
        gotoAction('bodyContent','reasonAction!deleteReason.do?reasonId=' + reasonId + '&' +  token.getTokenParamString());
    }
}
copyReason = function(reasonId) {
    gotoAction('bodyContent', "${contextPath}/reasonAction!prepareCopyReason.do?reasonId=" + reasonId+ '&' + token.getTokenParamString());
}
</script>

