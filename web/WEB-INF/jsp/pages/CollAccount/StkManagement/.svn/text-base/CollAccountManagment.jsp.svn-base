<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : CollAccountManagment.jsp
    Created on : Feb 17, 2009, 10:51:45 AM
    Author     : VuNT7
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            String flag = (String) request.getSession().getAttribute("flag");
%>

<tags:imPanel title="MSG.SIK.239">
    <s:form method="POST" id="collAccountManagmentForm" action="CollAccountManagmentAction" theme="simple">
        <s:token/>
        <div class="divHasBorder">
            <sx:div id="searchAreaColl" theme="simple">
                <jsp:include page="searchAreaColl.jsp"/>
            </sx:div>
        </div>
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.SIK.112'))}</legend>
            <sx:div id="searchArea" theme="simple">
                <jsp:include page="ListSearchColl.jsp"/>
            </sx:div>
        </fieldset>
        <%--
            <sx:div id="showView" theme="simple">
                <jsp:include page="ShowViewStaffAndAccount.jsp"/>
            </sx:div>
        --%>
    </s:form>
</tags:imPanel>

<script language="javascript">

    vuntChange = function () {

        updateCombo('shopId','channelTypeId','CollAccountManagmentAction!getChannelType.do');
    }


    checkValidFields = function() {        
        var typeId = document.getElementById("channelTypeId");
        var shopcode = document.getElementById("collAccountManagmentForm.shopcode");
        if(shopcode.value == null || shopcode.value == ""){
    <%--$( 'displayResultMsg' ).innerHTML='Bạn phải chọn đơn vị';--%>
                $('displayResultMsg').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.032')"/>';
                $('collAccountManagmentForm.shopcode').focus();
                return false;
            }
            if(typeId.value == null || typeId.value == ""){
    <%--$( 'displayResultMsg' ).innerHTML='Bạn phải chọn loại tài khoản';--%>
                $('displayResultMsg').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.033')"/>';
                $('channelTypeId').focus();
                return false;
            }
            if(typeId.value == 3){
                $( 'displayResultMsg' ).innerHTML='';
                var staffManage = document.getElementById("collAccountManagmentForm.staffManageCode");
                var collCode = document.getElementById("collAccountManagmentForm.collCode");
    <%--if (collCode.value =="" && staffManage.value ==""){
        $( 'displayResultMsg' ).innerHTML='Bạn phải chọn nhân viên quản lý';
        $('collAccountManagmentForm.staffManageCode').focus();
        return false;
    }--%>
            }
            else{
            
            }
            return true;
        }
        selectChannelTypeStaff = function(){
            $( 'displayResultMsg' ).innerHTML='';
            var channelTypeId = document.getElementById("channelTypeId");
            var shopName = document.getElementById("collAccountManagmentForm.shopName");
            var shopCode = document.getElementById("collAccountManagmentForm.shopcode");
            var typeSearch = document.getElementById("typeSearch");
            var staffName= document.getElementById("collAccountManagmentForm.staffManageName");
            var staffCode = document.getElementById("collAccountManagmentForm.staffManageCode");
            //var shopId = document.getElementById("shopId");
    

            if (shopName.value != "" && channelTypeId.value != ""){
                // TuTM1 12/03/2012 Fix ATTT CSRF
                gotoAction("searchAreaColl",'CollAccountManagmentAction!getAllCollManagerOrShop.do?shopCode=' + shopCode.value +'&channelTypeId='+ channelTypeId.value+'&shopName='+ shopName.value
                    +'&staffName='+ staffName.value+'&staffCode='+ staffCode.value + "&" + token.getTokenParamString());
            }
            else{
                if (channelTypeId.value != ""){
    <%--$( 'displayResultMsg' ).innerHTML='Bạn vui lòng chọn đúng đơn vị';--%>
                    $('displayResultMsg').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.034')"/>';
                    $('channelTypeId').value ='';
                    $('collAccountManagmentForm.shopCode').focus();
                }

            }
        }
    <%--'${fn:escapeXml(sessionScope.typeId)}'--%>
        selectChannelTypeShop = function(){
            var shopId = document.getElementById("shopId");
            var channelTypeId = document.getElementById("channelTypeId");
            if (shopId.value != "" && channelTypeId.value != ""){
                gotoAction("searchAreaColl",'CollAccountManagmentAction!getAllCollManagerOrShop.do?shopId=' 
                    + shopId.value +'&channelTypeId='+ channelTypeId.value + "&" + token.getTokenParamString());
            }
        }

</script>
