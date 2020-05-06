<%--
    Document   : addNewPartner
    Created on : Mar 19, 2009, 8:39:06 AM
    Author     : Namnx
    Desc       : danh muc doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#session.toEditPartner == 0">
    <s:set var="readonly" value="0" scope="request"/>
</s:if>
<s:else>
    <s:if test="#session.toEditPartner == 1">
        <s:set var="readonly" value="1" scope="request"/>
    </s:if>
    <s:else>
        <s:set var="readonly" value="2" scope="request"/>
    </s:else>
</s:else>

<tags:imPanel title="MSG.LST.001">
    <div class="divHasBorder">

        <s:form action="partnerAction" theme="simple" enctype="multipart/form-data"  method="post" id="partnerForm">
<s:token/>

            <s:hidden name="partnerForm.partnerId" id="partnerForm.partnerId"/>

            <tags:Partner toInputData="true" property="partnerForm"/>

        </s:form>

        <div align="center" style="vertical-align:top">
            <s:if test="#request.readonly == 0">
                <tags:submit targets="bodyContent" formId="partnerForm"
                             validateFunction="checkRaV();"
                             cssStyle="width:80px;" value="MSG.GOD.010"
                             confirm="true" confirmText="confirm.insertPartner"
                             showLoadingText="true"
                             preAction="partnerAction!addNewPartner.do"
                             />

                <tags:submit targets="bodyContent" formId="partnerForm"
                             cssStyle="width:80px;" value="MSG.GOD.009"
                             showLoadingText="true"
                             validateFunction="checkSearch();"
                             preAction="partnerAction!searchPartner.do"
                             />

                <%--sx:submit  parseContent="true" executeScripts="true"
                            formId="partnerForm" loadingText="Đang thực hiện..."
                            cssStyle="width:80px;"
                            showLoadingText="true" targets="bodyContent"
                            value="Thêm mới"  beforeNotifyTopics="partnerAction/addNewPartner"/>

                <sx:submit parseContent="true" executeScripts="true"
                           formId="partnerForm" loadingText="Đang thực hiện..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="bodyContent"
                           value="Tìm kiếm"  beforeNotifyTopics="partnerAction/searchPartner"/--%>

            </s:if>
            <s:if test="#request.readonly == 1">

                <tags:submit targets="bodyContent" formId="partnerForm"
                             cssStyle="width:80px;" value="MSG.GOD.020"
                             validateFunction="checkRaV();"
                             confirm="true" confirmText="confirm.updatePartner"
                             showLoadingText="true"
                             preAction="partnerAction!editPartner.do"
                             />
                <tags:submit targets="bodyContent" formId="partnerForm"
                             cssStyle="width:80px;" value="MSG.GOD.018"
                             showLoadingText="true"
                             preAction="partnerAction!preparePage.do"
                             />

                <%--sx:submit parseContent="true" executeScripts="true"
                           formId="partnerForm" loadingText="Đang thực hiện..."
                           showLoadingText="true" targets="bodyContent"
                           cssStyle="width:80px"
                           value="Đồng ý"  beforeNotifyTopics="partnerAction/editPartner"/>
                <sx:submit parseContent="true" executeScripts="true"
                           formId="partnerForm" loadingText="Đang thực hiện..."
                           showLoadingText="true" targets="bodyContent"
                           cssStyle="width:80px"
                           value="Bỏ qua"  beforeNotifyTopics="partnerAction/cancel"/--%>

            </s:if>
            <s:if test="#request.readonly == 2">


                <tags:submit targets="bodyContent" formId="partnerForm"
                             cssStyle="width:80px;" value="MSG.copy"
                             validateFunction="checkRaV();"
                             confirm="true" confirmText="confirm.copyPartner"
                             showLoadingText="true"
                             preAction="partnerAction!copyPartner.do"
                             />
                <tags:submit targets="bodyContent" formId="partnerForm"
                             cssStyle="width:80px;" value="MSG.GOD.018"
                             showLoadingText="true"
                             preAction="partnerAction!preparePage.do"
                             />

                <%--sx:submit  parseContent="true" executeScripts="true"
                            formId="partnerForm" loadingText="Đang thực hiện..."
                            cssStyle="width:80px;"
                            showLoadingText="true" targets="bodyContent"
                            value="Sao chép"  beforeNotifyTopics="partnerAction/copyPartner"/>

                <sx:submit parseContent="true" executeScripts="true"
                           formId="partnerForm" loadingText="Đang thực hiện..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="bodyContent"
                           value="Bỏ qua"  beforeNotifyTopics="partnerAction/cancel"/--%>
            </s:if>

        </div>

    </div>

    <div id="listPartner">
        <jsp:include page="listPartner.jsp"/>
    </div>

</tags:imPanel>


<script type="text/javascript">
    //ham phuc vu viec phan trang
    pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
        dojo.widget.byId("updateContent").href = 'partnerAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber+"&"+ token.getTokenParamString();
        dojo.event.topic.publish('updateContent');
    }

    checkRaV=function(){
        return (checkRequiredFields() && checkValidFields());
    }

    checkRequiredFields=function(){
        var dateStart= dojo.widget.byId("partnerForm.staDate");
        var dateEnd= dojo.widget.byId("partnerForm.endDate");
        $('displayResultMsgClient' ).innerHTML = "";
        if($('partnerForm.partnerCode').value ==null || $('partnerForm.partnerCode').value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Bạn chưa nhập mã đối tác";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.044"/>';
                $('partnerForm.partnerCode').focus();
                return false;
            }
            if($('partnerForm.partnerName').value ==null || $('partnerForm.partnerName').value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Bạn chưa nhập tên đối tác";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.045"/>';
                $('partnerForm.partnerName').focus();

                return false;
            }
            if( dateStart.domNode.childNodes[1].value.trim() != "" && !isDateFormat(dateStart.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày bắt đầu không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.SIK.025"/>';
                $('partnerForm.staDate').focus();
                return false;
            }
            if( dateEnd.domNode.childNodes[1].value !="" && !isDateFormat(dateEnd.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày kết thúc không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.046"/>';
                $('partnerForm.endDate').focus();
                return false;
            }
            if(dateEnd.domNode.childNodes[1].value !="" &&  dateStart.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateStart.domNode.childNodes[1].value.trim(),dateEnd.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày bắt đầu phải nhỏ hơn ngày kết thúc';--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.SIK.027"/>';
                $('partnerForm.staDate').focus();
                return false;
            }
            if($('partnerForm.partnerType').value ==null || $('partnerForm.partnerType').value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Bạn chưa chọn loại đối tác";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.047"/>';
                $('partnerForm.partnerType').focus();

                return false;
            }
            if($('partnerForm.status').value ==null || $('partnerForm.status').value.trim() == ""){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Bạn chưa chọn trạng thái";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.STK.026"/>';
                $('partnerForm.status').focus();

                return false;
            }

            return true;
        }

        checkValidFields = function() {
            //trim cac truong can thiet
            $('partnerForm.partnerCode').value = trim($('partnerForm.partnerCode').value);
            $('partnerForm.partnerName').value = trim($('partnerForm.partnerName').value);

            if(isHtmlTagFormat(trim($('partnerForm.partnerCode').value))) {
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường mã Partner không được chứa các thẻ HTML";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.048"/>';
                $('partnerForm.partnerCode').select();
                $('partnerForm.partnerCode').focus();
                return false;
            }
            if(isHtmlTagFormat(trim($('partnerForm.partnerName').value))) {
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường tên đối không được chứa các thẻ HTML";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.049"/>';
                $('partnerForm.partnerName').select();
                $('partnerForm.partnerName').focus();
                return false;
            }
            if(isHtmlTagFormat(trim($('partnerForm.address').value))) {
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường địa chỉ không được chứa các thẻ HTML";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.050"/>';
                $('partnerForm.address').select();
                $('partnerForm.address').focus();
                return false;
            }
            if($('partnerForm.phone').value !=null && trim($('partnerForm.phone').value) !="" && !isInteger(trim($('partnerForm.phone').value))){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường số điện thoại không được nhập số âm";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name = "ERR.LST.051"/>';
                $('partnerForm.phone').select();
                $('partnerForm.phone').focus();
                return false;
            }
            if( $('partnerForm.fax').value !=null && trim($('partnerForm.fax').value) !="" && !isInteger(trim($('partnerForm.fax').value))){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường số Fax không được nhập số âm";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.052"/>';
                $('partnerForm.fax').select();
                $('partnerForm.fax').focus();
                return false;
            }

            if(!isValidInput($('partnerForm.partnerCode').value, false, false)) {
    <%--$('displayResultMsgClient').innerHTML = "!!!Lỗi. Mã Partner chỉ được chứa các ký tự A-Z, a-z, 0-9, _";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.053"/>';
                $('partnerForm.partnerCode').select();
                $('partnerForm.partnerCode').focus();
                return false;
            }
            //trim cac truong can thiet

            $('partnerForm.partnerName').value = trim($('partnerForm.partnerName').value);
            $('partnerForm.partnerCode').value = trim($('partnerForm.partnerCode').value);


            return true;
        }

        checkSearch = function() {
            var dateStart= dojo.widget.byId("partnerForm.staDate");
            var dateEnd= dojo.widget.byId("partnerForm.endDate");
            if($('partnerForm.phone').value !=null && trim($('partnerForm.phone').value) !="" && !isInteger(trim($('partnerForm.phone').value))){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường số điện thoại không được nhập số âm";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.051"/>';
                $('partnerForm.phone').select();
                $('partnerForm.phone').focus();
                return false;
            }
            if( $('partnerForm.fax').value !=null && trim($('partnerForm.fax').value) !="" && !isInteger(trim($('partnerForm.fax').value))){
    <%--$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Trường số Fax không được nhập số âm";--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.052"/>';
                $('partnerForm.fax').select();
                $('partnerForm.fax').focus();
                return false;
            }
            if( dateStart.domNode.childNodes[1].value.trim() != "" && !isDateFormat(dateStart.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày bắt đầu không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.SIK.025"/>';
                $('partnerForm.staDate').focus();
                return false;
            }

            if( dateEnd.domNode.childNodes[1].value !="" && !isDateFormat(dateEnd.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày kết thúc không hợp lệ';--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.046"/>';
                $('partnerForm.endDate').focus();
                return false;
            }
            if(dateStart.domNode.childNodes[1].value.trim() != "" && dateEnd.domNode.childNodes[1].value !="" && !isCompareDate(dateStart.domNode.childNodes[1].value.trim(),dateEnd.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClient' ).innerHTML='Ngày bắt đầu phải nhỏ hơn ngày kết thúc';--%>
                $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.SIK.027"/>';
                $('partnerForm.staDate').focus();
                return false;
            }
            return true;
        }

        //lang nghe, xu ly su kien khi click nut "Them doi tac moi"
        dojo.event.topic.subscribe("partnerAction/addNewPartner", function(event, widget){
            if (validateForm()&&checkValidFields()) {
                widget.href = "partnerAction!addNewPartner.do?"+ token.getTokenParamString();

            }else{
                event.cancel = true;
            }

            //event: set event.cancel = true, to cancel request
        });

        //lang nghe, xu ly su kien khi click nut "Tim kiem"
        dojo.event.topic.subscribe("partnerAction/searchPartner", function(event, widget){
            if(checkSearch()){
                widget.href = "partnerAction!searchPartner.do?"+ token.getTokenParamString();
            }
            else{
                event.cancel = true;
            }

            //event: set event.cancel = true, to cancel request
        });

        //lang nghe, xu ly su kien khi click nut "Reset"
        dojo.event.topic.subscribe("partnerAction/resetPartner", function(event, widget){
            document.getElementById('partnerForm.partnerName').value="";
            document.getElementById('partnerForm.partnerType').value="";
            document.getElementById('partnerForm.status').value="";
            document.getElementById('partnerForm.address').value="";
            document.getElementById('partnerForm.phone').value="";
            document.getElementById('partnerForm.partCode').value="";

            document.getElementById('partnerForm.fax').value="";
            document.getElementById('partnerForm.status').value="";
            document.getElementById('partnerForm.staDate').value="";
            document.getElementById('partnerForm.endDate').value="";

            event.cancel = true;
        });


        dojo.event.topic.subscribe("partnerAction/editPartner", function(event, widget){
            if (validateForm()&&checkValidFields()) {
                widget.href = "partnerAction!editPartner.do?"+ token.getTokenParamString();

            }
            else{
                event.cancel = true;
            }
        });

        //lang nghe, xu ly su kien khi click nut "bo qua"
        dojo.event.topic.subscribe("partnerAction/cancel", function(event, widget){
            widget.href = "partnerAction!preparePage.do?"+ token.getTokenParamString();
        });

        delPartner = function(partnerId) {
//            var strConfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('ERR.LST.054')"/>');
            var strConfirm = getUnicodeMsg('<s:text name="ERR.LST.054"/>');
            if (confirm(strConfirm)) {
    <%--if(confirm("Bạn có thực sự muốn xóa đối tác này?")){--%>
                gotoAction('bodyContent','partnerAction!deletePartner.do?partnerId=' + partnerId+"&"+ token.getTokenParamString());
            }
        }
        copyPartner = function(partnerId) {
            gotoAction('bodyContent', "${contextPath}/partnerAction!prepareCopyPartner.do?partnerId=" + partnerId+"&"+ token.getTokenParamString());
        }

        //xử lý sự kiện onclick của nut copy
        dojo.event.topic.subscribe("partnerAction/copyPartner", function(event, widget){
            if(validateForm()&&checkValidFields()) {
                widget.href = "partnerAction!copyPartner.do?"+ token.getTokenParamString();
            } else {
                event.cancel = true;
            }
        });


</script>

