<%--
    Document   : dslam
    Created on : Mar 24, 2009, 2:33:35 PM
    Author     : andv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="dslamAction" theme="simple" method="POST" id="dslamForm">
<s:token/>


    <tags:imPanel title="MSG.dslam_dlu.info">
        <div>
            <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
        </div>

        <s:hidden name="dslamForm.dslamId" id="dslamId"/>
        <tags:Dslam property="dslamForm" toInputData="true"/>    
        <br/>
        <div align="center" style="padding-bottom:5px;">


            <tags:submit targets="divDisplayInfo" formId="dslamForm"
                         showLoadingText="true" cssStyle="width:80px;"
                         value="MSG.find" preAction="dslamAction!searchDslamCable.do"
                         />


            <%--
                    <s:if test="(dslamForm.dslamId == null) || ((dslamForm.dslamId*1) <= 0) ">
                        <sx:submit parseContent="true" executeScripts="true"
                                   formId="dslamForm" loadingText="Đang thực hiện..."
                                   showLoadingText="true" targets="divDisplayInfo"
                                   cssStyle="width:80px"
                                   value="Thêm mới"  beforeNotifyTopics="dslamAction/addNewDslam"/>
                        <sx:submit parseContent="true" executeScripts="true"
                                   formId="dslamForm" loadingText="Đang thực hiện..."
                                   cssStyle="width:80px"
                                   showLoadingText="true" targets="divDisplayInfo"
                                   value="Tìm kiếm"  beforeNotifyTopics="dslamAction/dslamSearch"/>

        </s:if>
        <s:else>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="dslamForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="divDisplayInfo"
                       cssStyle="width:80px"
                       value="Đồng ý"  beforeNotifyTopics="dslamAction/editDslam"/>


            <sx:submit parseContent="true" executeScripts="true"
                       formId="dslamForm" loadingText="Đang thực hiện..."
                       showLoadingText="true" targets="divDisplayInfo"
                       cssStyle="width:80px"
                       value="Bỏ qua"  beforeNotifyTopics="dslamAction/cancel_1"/>
        </s:else>

            --%>

        </div>
    </tags:imPanel>
    <br/>


    <div id="listDslamArea">
        <tags:imPanel title = "MSG.dslam_du.list">
            <jsp:include page="listDslamCable.jsp"/>
        </tags:imPanel>
    </div>

</s:form>

<script type="text/javascript">
    $('code').select();
    $('code').focus();
    if($('productId').value=="1"){
        $('dslamIp').disabled=false;
        $('brasIp').disabled=false;

    }
    else{
        $('dslamIp').value="";
        $('brasIp').value="";
        $('dslamIp').disabled=true;
        $('brasIp').disabled=true;

    }

    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('code').value) == "") {
            //            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.086')"/>';
            $('message').innerHTML = '<s:text name="ERR.INF.086"/>';
            $('code').select();
            $('code').focus();
            return false;
        }
        //truong ma tap luat
        if(trim($('name').value) == "") {
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.087')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.087"/>';
            $('name').select();
            $('name').focus();
            return false;
        }
        if(trim($('province').value) == "") {
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.088')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.088"/>';
            $('address').select();
            $('address').focus();
            return false;
        }

        if(trim($('status').value) == "") {
            //            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.INF.089')"/>';
            $('message').innerHTML = '<s:text name="ERR.INF.089"/>';
            $('status').select();

            $('status').focus();
            return false;
        }
        if(trim($('address').value) == "") {
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.090')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.090"/>';
            $('address').select();
            $('address').focus();
            return false;
        }


        return true;
    }
    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        $('code').value=trim($('code').value);
        /*
        if(!isValidInput($('code').value, false, false)) {
            $('message').innerHTML = "!!!Lỗi. Mã Dslam chỉ được chứa các ký tự A-Z, a-z, 0-9, _";
            $('code').select();
            $('code').focus();
            return false;
        }
         */
        if(isHtmlTagFormat(trim($('code').value))) {
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.091')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.091"/>';
            $('code').select();
            $('code').focus();
            return false;
        }
        /*
        if(isHtmlTagFormat(trim($('name').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường tên DSLAM/DLU không được chứa các thẻ HTML";
            $('name').select();
            $('name').focus();
            return false;
        }*/


        if(!isInteger((trim($('maxPort').value)))&&(!trim($('maxPort').value) == "")){
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.092')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.092"/>';
            $('maxPort').select();
            $('maxPort').focus();
            return false;
        }
        if(!isInteger(trim($('usedPort').value))&&(!trim($('usedPort').value) == "")){
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.093')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.093"/>';
            $('usedPort').select();
            $('usedPort').focus();
            return false;
        }
        if(!isInteger(trim($('reservedPort').value))&&(!trim($('reservedPort').value) == "")){
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.094')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.094"/>';
            $('reservedPort').select();
            $('reservedPort').focus();
            return false;
        }
        if(!isInteger(trim($('x').value))&&(!trim($('x').value) == "")){
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.095')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.095"/>';
            $('x').select();
            $('x').focus();
            return false;
        }
        if(!isInteger(trim($('y').value))&&(!trim($('y').value) == "")){
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.096')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.096"/>';
            $('y').select();
            $('y').focus();
            return false;
        }
        /*
        if(isHtmlTagFormat(trim($('address').value))) {
            $('message').innerHTML = "!!!Lỗi. Trường địa chỉ DSLAM/DLU không được chứa các thẻ HTML";
            $('address').select();
            $('address').focus();
            return false;
        }*/
        var address = trim($('address').value);
        if(address.length > 150) {
            //            $('message').innerHTML =  '<s:property escapeJavaScript="true"  value="getError('ERR.INF.097')"/>';
            $('message').innerHTML =  '<s:text name="ERR.INF.097"/>';
            $('address').select();
            $('address').focus();
            return false;
        }


        return true;
    }

    //lang nghe, xu ly su liem khi click nut them moi
    dojo.event.topic.subscribe("dslamAction/addNewDslam", function(event, widget){

        if (checkRequiredFields() && checkValidFields()) {
            widget.href = "dslamAction!addNewDslam.do?"+token.getTokenParamString();
        } else {
            event.cancel = true;
        }
    });

    //lang nghe, xu ly su kien khi click nut "Dong y"
    dojo.event.topic.subscribe("dslamAction/editDslam", function(event, widget){

        if (checkRequiredFields() && checkValidFields()) {
            widget.href = "dslamAction!editDslam.do?"+token.getTokenParamString();
        } else {
            event.cancel = true;
        }
    });



    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("dslamAction/dslamSearch", function(event, widget){
        widget.href = "dslamAction!searchDslam.do";
    });

    //lang nghe, xu ly su kien khi click nut "bo qua"
    dojo.event.topic.subscribe("dslamAction/cancel", function(event, widget){
        widget.href = "dslamAction!getListDslam.do";
        //$('dslamId').value='0';
    });

    //lang nghe, xu ly su kien khi click nut "Reset"
    dojo.event.topic.subscribe("dslamAction/reset", function(event, widget){
        $('name').value="";
        $('code').value="";
        $('brasIp').value="";
        $('maxPort').value="";
        $('usedPort').value="";
        $('reservedPort').value="";
        $('province').value="";

        $('shopId').value="";
        $('x').value="";
        $('y').value="";
        $('productId').value="";
        $('address').value="";
        $('dslamIp').value="";
        event.cancel = true;
    });
    /*
    dojo.event.topic.subscribe("dslamAction/displayProvinceName", function(value, key, text, widget){
        $('provinceName').value = key;
        updateData('getListTechShop.do?target=shopId&province='+ value);
        //updateData('${contextPath}/processPSTNAction!getDluCombobox.do?target=dslamId,form.stockPstnDistrict&location='+ location);
    });*/
    changeProvince = function(province) {
        updateData("getListTechShop.do?target=shopId&province=" + province);
    }
    changeProductId=function(productId){
        
        if(productId=='1'){
            $('dslamIp').disabled=false;
            $('brasIp').disabled=false;

        }
        else{
            $('dslamIp').value="";
            $('brasIp').value="";
            $('dslamIp').disabled=true;
            $('brasIp').disabled=true;

        }
    }


    confirmDelete = function (dslamId){
        //        if(confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.INF.00021')"/>'))){
        if(confirm(getUnicodeMsg('<s:text name="MSG.INF.00021"/>'))){
            gotoAction('divDisplayInfo', 'dslamAction!actionDeleteDslam.do?dslamId='+dslamId+'&'+token.getTokenParamString());
        }
    }
</script>
