<%--
    Document   : addNewReasonGroup
    Created on : Mar 26, 2009, 9:57:34 AM
    Author     : NamNX
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

<tags:imPanel title="MSG.LST.018">

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="returnMsg"  propertyValue="paramMsg" type="key" />
    </div>

    <!-- phan hien thi thong tin ve nhom ly do -->
    <div>
        <s:form action="reasonGroupAction" theme="simple" enctype="multipart/form-data"  method="post" id="reasonGroupForm">
<s:token/>

            <s:hidden name="reasonGroupForm.reasonGroupId" id="reasonGroupForm.reasonGroupId"/>
            <tags:ReasonGroup toInputData="true" property="reasonGroupForm"/>
        </s:form>
    </div>
    <br />
    <!-- phan nut tac vu -->
    <div align="center" style="padding-bottom:5px; ">
        <s:if test="#session.toEditReasonGroup == 0">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonGroupForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       validate="validateForm()"
                       cssStyle="width:80px"
                       key="MSG.generates.addNew"  beforeNotifyTopics="reasonGroupAction/addNewReasonGroup"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonGroupForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.generates.find"  beforeNotifyTopics="reasonGroupAction/searchReasonGroup"/>
        </s:if>
        <s:if test="#session.toEditReasonGroup == 2">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonGroupForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       validate="validateForm()"
                       cssStyle="width:80px"
                       key="MSG.copy"  beforeNotifyTopics="reasonGroupAction/copyReasonGroup"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonGroupForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.cancel2"  beforeNotifyTopics="reasonGroupAction/cancel"/>
        </s:if>
        <s:if test="#session.toEditReasonGroup == 1">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonGroupForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       validate="validateForm();"
                       cssStyle="width:80px"
                       key="MSG.accept"  beforeNotifyTopics="reasonGroupAction/editReasonGroup"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="reasonGroupForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.cancel2"  beforeNotifyTopics="reasonGroupAction/cancel"/>
        </s:if>
    </div>

</tags:imPanel>

<br />

<tags:imPanel title="MSG.LST.019">
    <div id="listReasonGroup">
        <jsp:include page="addNewReasonGroupResult.jsp"/>
    </div>
</tags:imPanel>









<script type="text/javascript">

    //focus vao truong dau tien
    $('reasonGroupForm.reasonGroupCode').select();
    $('reasonGroupForm.reasonGroupCode').focus();

    //kiem tra tinh hop le cua form truoc khi submit
    validateForm=function(){
        alert("CHECK");
        $('displayResultMsgClient' ).innerHTML = "";
        if($('reasonGroupForm.reasonGroupCode').value ==null || $('reasonGroupForm.reasonGroupCode').value.trim() == ""){
    <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Nhập mã nhóm lý do";--%>
//                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.104')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.104"/>';
                $('reasonGroupForm.reasonGroupCode').select();
                $('reasonGroupForm.reasonGroupCode').focus();

                return false;
            }
            if($('reasonGroupForm.reasonGroupName').value ==null || $('reasonGroupForm.reasonGroupName').value.trim() == ""){
    <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Nhập tên nhóm lý do";--%>
//                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.105')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.105"/>';
                $('reasonGroupForm.reasonGroupName').select();
                $('reasonGroupForm.reasonGroupName').focus();

                return false;
            }
            if($('reasonGroupForm.status').value ==null || $('reasonGroupForm.status').value.trim() == ""){
    <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Chọn trạng thái";--%>
//                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.042')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.042"/>';
                $('reasonGroupForm.status').focus();

                return false;
            }

            //kiem tra ma ly do, ten ly do, mo ta khong duoc chua cac ky tu dac biet
            if(!isValidInput(trim($('reasonGroupForm.reasonGroupCode').value))) {
    <%--$('displayResultMsgClient' ).innerHTML = "!!!Lỗi. Mã nhóm lý do chỉ được chứa các ký tự A-Z, a-z, 0-9, _";--%>
//                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.106')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.106"/>';
                $('reasonGroupForm.reasonGroupCode').select();
                $('reasonGroupForm.reasonGroupCode').focus();

                return false;
            }

            /*
        if(checkingSpecialCharacter(trim($('reasonGroupForm.reasonGroupName').value))) {
            $( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Tên nhóm lý do không được chứa các ký tự đặc biệt";
            $('reasonGroupForm.reasonGroupName').select();
            $('reasonGroupForm.reasonGroupName').focus();

            return false;
        }
        if(checkingSpecialCharacter(trim($('reasonGroupForm.description').value))) {
            $( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường mô tả không được chứa các ký tự đặc biệt";
            $('reasonGroupForm.description').select();
            $('reasonGroupForm.description').focus();

            return false;
        }*/

            //
            if(trim($('reasonGroupForm.description').value).length > 200) {
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường mô tả quá dài";--%>
//                $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.LST.026')"/>';
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.026"/>';
                $('reasonGroupForm.description').select();
                $('reasonGroupForm.description').focus();

                return false;
            }


            //trim cac truong can thiet
            $('reasonGroupForm.reasonGroupCode').value = trim($('reasonGroupForm.reasonGroupCode').value);
            $('reasonGroupForm.reasonGroupName').value = trim($('reasonGroupForm.reasonGroupName').value);
            $('reasonGroupForm.description').value = trim($('reasonGroupForm.description').value);

            return true;

        }

        //lang nghe, xu ly su kien khi click nut "Them moi"
        dojo.event.topic.subscribe("reasonGroupAction/addNewReasonGroup", function(event, widget){
            if (!validateForm()) {
                event.cancel = true;
            }
            widget.href = "reasonGroupAction!addNewReasonGroup.do?"+ token.getTokenParamString();
        });

        //lang nghe, xu ly su kien khi click nut "Tim kiem"
        dojo.event.topic.subscribe("reasonGroupAction/searchReasonGroup", function(event, widget){
            widget.href = "reasonGroupAction!searchReasonGroup.do?" +token.getTokenParamString() ;
        });

        //lang nghe, xu ly su kien khi click nut "Dong y"
        dojo.event.topic.subscribe("reasonGroupAction/editReasonGroup", function(event, widget){
            if (!validateForm()) {
                event.cancel = true;
            }
            widget.href = "reasonGroupAction!editReasonGroup.do?"+ token.getTokenParamString();
            //event: set event.cancel = true, to cancel request
        });

        //lang nghe, xu ly su kien khi click nut "Bo qua"
        dojo.event.topic.subscribe("reasonGroupAction/cancel", function(event, widget){
            widget.href = "reasonGroupAction!prepareReasonGroupPage.do?"+ token.getTokenParamString();
            //event: set event.cancel = true, to cancel request
        });
        //xử lý sự kiện onclick của nut copy
        dojo.event.topic.subscribe("reasonGroupAction/copyReasonGroup", function(event, widget){
            if(validateForm()) {
                widget.href = "reasonGroupAction!copyReasonGroup.do?" +token.getTokenParamString() ;
            } else {
                event.cancel = true;
            }
        });
        //
        delReasonGroup = function(reasonGroupId) {
//            var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('ERR.LST.103')"/>');
            var strConfirm = getUnicodeMsg('<s:text name="ERR.LST.103"/>');
            if (confirm(strConfirm)) {
    <%--if(confirm("Bạn có thực sự muốn xóa nhóm lý do này?")){--%>
                gotoAction('bodyContent','reasonGroupAction!deleteReasonGroup.do?reasonGroupId=' + reasonGroupId+ '&' + token.getTokenParamString() );
            }
        }
        copyReasonGroup = function(reasonGroupId) {
            gotoAction('bodyContent', "${contextPath}/reasonGroupAction!prepareCopyReasonGroup.do?reasonGroupId=" + reasonGroupId+ '&' + token.getTokenParamString());
        }


</script>
