<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : filterIsdn.jsp
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : ThanhNC
    Note       : Loc so dep
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%@page import="com.viettel.im.common.util.ResourceBundleUtils" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("stockType", request.getSession().getAttribute("stockTypeId"));
%>

<style>
    .divSMOLeft {
        width: 265px;
        float: left;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .divSMORight {
        margin-left: 275px;
        border: 1px #99BBE8 inset;
        padding: 3px;
    }
    .clearStyle {
        clear:both;
    }
</style>

<s:form action="filterIsdnAction.do" id="filterIsdnForm" theme="simple">
<s:token/>

    <tags:imPanel title="MSG.filter.beauty.isdn">
        <div>
            <div class="divSMOLeft">
                <fieldset class="imFieldset">
                    <legend> 
                        ${fn:escapeXml(imDef:imGetText('MSG.list.filter.rule'))}
                    </legend>
                    <div style="width:100%">
                        <tags:imSelect name="filterIsdnForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.271"
                                       list="1:MSG.mobileNumber, 2:MSG.homephoneNumber,3:MSG.pstnNumber"
                                       onchange="changeService()"/>
                    </div>

                    <div align="left" style="height:575px; width:250px; overflow:auto; margin-left:3px; margin-top:3px; ">
                        <sx:div id="treeArea">
                            <s:if test="filterIsdnForm.stockTypeId != null">
                                <jsp:include page="treeFilterIsdn.jsp">
                                    <jsp:param name="stockTypeId" value="${fn:escapeXml(stockType)}"/>
                                </jsp:include>
                            </s:if>
                        </sx:div>
                    </div>

                </fieldset>
            </div>
            <div class="divSMORight">
                <fieldset class="imFieldset">
                    <legend>
                        ${fn:escapeXml(imDef:imGetText('MSG.ISN.036'))}
                    </legend>

                    <div class="divHasBorder">
                        <table class="inputTbl6Col">
                            <tr>
                                <td> <tags:label key="MSG.isdn.area" required="true"/></td>
                                <td class="oddColumn">
                                    <table style="width:100%; border-spacing:0px; ">
                                        <tr>
                                            <td style="width:50%;">
                                                <s:textfield name="filterIsdnForm.fromIsdn" id="fromIsdn" maxlength="20" cssClass="txtInputFull"/>
                                            </td>
                                            <td>-</td>
                                            <td style="width:50%;">
                                                <s:textfield name="filterIsdnForm.toIsdn" id="toIsdn" maxlength="20" cssClass="txtInputFull"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td> <tags:label key="MSG.fromIsdnStore"/></td>
                                <td>
                                    <tags:imSelect name="filterIsdnForm.shopId"
                                                   id="shopId"
                                                   cssClass="txtInputFull"
                                                   headerKey="" headerValue="MSG.selectStoreGetIsdn"
                                                   listKey="shopId" listValue="name"
                                                   list="lstShop" /></td>
                            </tr>
                            <tr>
                                <td> <tags:label key="MSG.conditionFilter" required="true"/></td>
                                <td colspan="3">
                                    <s:checkbox name="filterIsdnForm.statusNew" id="statusNew" />
                                    <label>${fn:escapeXml(imDef:imGetText('MSG.newIsdn'))}</label>
                                    <s:checkbox name="filterIsdnForm.statusPause" id="statusPause"/>
                                    <label>
                                        ${fn:escapeXml(imDef:imGetText('MSG.stopIsdn'))}
                                    </label>

                                    <s:checkbox name="filterIsdnForm.statusUsed" id="statusUsed"/><label>
                                        ${fn:escapeXml(imDef:imGetText('MSG.nowIsdn'))}</label>
                                        <s:checkbox name="filterIsdnForm.filterAgain" id="filterAgain" />
                                    <label>
                                        ${fn:escapeXml(imDef:imGetText('MSG.cancelRule'))}
                                    </label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" align="center">
                                    <tags:submit formId="filterIsdnForm" showLoadingText="true" targets="ListIsdnFilter" value="MSG.filterIsdn"
                                                 validateFunction="validateBeforeFilter();"
                                                 cssStyle="width:80px; "
                                                 preAction="filterIsdnAction!filterNumber.do" confirm="true" 
                                                 confirmText="MSG.confirm.filter.beauty.isdn"
                                                 showProgressDiv="true"
                                                 showProgressClass="com.viettel.im.database.DAO.FilterISDNDAO"
                                                 showProgressMethod="updateProgressDiv"
                                                 />
                                </td>
                            </tr>
                        </table>
                    </div>

                    <sx:div id="ListIsdnFilter">
                        <jsp:include page="ListIsdnFiltered.jsp" flush="true"/>
                    </sx:div>
                </fieldset>
            </div>
            <div class="clearStyle"></div>
        </div>
    </tags:imPanel>
</s:form>

<script>
    $('stockTypeId').focus();
    validateBeforeFilter = function(){
        var fromIsdn=$('fromIsdn');
        var toIsdn=$('toIsdn');
        var stockTypeId =$('stockTypeId');
        var statusNew=$('statusNew');
        var statusPause=$('statusPause');
        var statusUsed=$('statusUsed');
        fromIsdn.value=trim(fromIsdn.value);
        toIsdn.value=trim(toIsdn.value);
        if(stockTypeId.value ==null || stockTypeId.value.trim()==''){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.013"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Chưa chọn dịch vụ';
            stockTypeId.focus();
            return false;
        }
        if(fromIsdn.value ==null ||fromIsdn.value=='' ){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.014"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Trường từ số không được để trống';
            fromIsdn.focus();
            return false;
        }
        if(!isInteger(trim(fromIsdn.value))){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.015"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Trường từ số phải là số nguyên dương';
            fromIsdn.focus();
            return false;
        }
        if(trim(toIsdn.value) ==null ||trim(toIsdn.value)=='' ){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.016"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Trường đến số không được để trống';
            toIsdn.focus();
            return false;
        }
        if(!isInteger(trim(toIsdn.value))){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.017"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Trường đến số phải là số nguyên dương';
            toIsdn.focus();
            return false;
        }
        if(fromIsdn.value*1 > toIsdn.value*1){
            //alert("resultCreateDrawRangeClient")
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.018"/>';
            //$('resultFilterIsdnClient').innerHTML='Trường từ số phải nhỏ hơn hoặc bằng trường đến số';
            toIsdn.focus();
            return false;
        }
        
        if(statusUsed.checked==false && statusNew.checked==false && statusPause.checked==false){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.019"/>';
            //$('resultFilterIsdnClient').innerHTML='Phải chọn ít nhất một loại số để lọc';
            return false;
        }
        
        

        var wg = dojo.widget.byId("0_")

        var cb = wg.cb_0_;

        var form = cb.form;

        var count=0;
        // alert(form['filterIsdnForm.ruleSelected']);
        var arrCb = form['filterIsdnForm.ruleSelected'];


        if(arrCb==null){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.020"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Chưa chọn luật để lọc';
            stockTypeId.focus();
            return false;
        }
        for (var i = 0; i< arrCb.length; i++){
            if (arrCb[i].checked == true){
                count++;
            }
        }
        if(count==0){
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.020"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Chưa chọn luật để lọc';
            stockTypeId.focus();
            return false;
        }

        //tamdt1, 21/08/2009 - start
        var isdnQuantity = '<%=StringEscapeUtils.escapeHtml(ResourceBundleUtils.getResource("MAX_ISDN_FILTER"))%>';
        if((toIsdn.value * 1 - fromIsdn.value * 1 + 1) > (isdnQuantity * 1) ) {
            $('resultFilterIsdnClient').innerHTML= '<s:text name="ERR.ISN.022"/>';
            //$('resultFilterIsdnClient').innerHTML='!!!Lỗi. Số lượng số cần lọc vượt quá ' + isdnQuantity + ' số';
            fromIsdn.select();
            fromIsdn.focus();
            return false;
        }
        //tamdt1, 21/08/2009 - end



        $('resultFilterIsdnClient').innerHTML='';

        return true;
    }


    changeService = function(){
        var cboStockTypeId = document.getElementById("stockTypeId");
        var stockTypeId = cboStockTypeId.options[cboStockTypeId.selectedIndex].value;
        // TuTM1 04/03/2012 Fix ATTT CSRF
        gotoAction('treeArea', '${contextPath}/filterIsdnAction!changeService.do?stockTypeId='+stockTypeId+ "&" + token.getTokenParamString());
    }
</script>
