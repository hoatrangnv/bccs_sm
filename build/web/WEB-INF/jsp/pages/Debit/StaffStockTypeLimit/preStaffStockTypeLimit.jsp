<%--
    Document   : preStaffStockTypeLimit
    Created on : 15/08/2011
    Author     : tutm1
--%>
<%--
    gan han muc cho kenh nhan vien
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


<s:form action="staffStockTypeLimitAction" theme="simple" enctype="multipart/form-data"  method="post" id="saleChannelTypeForm">
<s:token/>

    <tags:imPanel title="MSG.channel.info">

        <s:hidden name="saleChannelTypeForm.channelTypeId" id="saleChannelTypeForm.channelTypeId"/>
        <table class="inputTbl6Col" style="width:100%" >
            <tr>
                <td class="text" colspan="6" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="paramMsg" type="key" />
                </td>
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.chanel.type.name" />
                </td>
                <td class="text">
                    <s:if test="#session.toEditChannelType == 0">
                        <s:textfield name="saleChannelTypeForm.name" id="saleChannelTypeForm.name" cssClass="txtInputFull" maxLength="50"/>
                    </s:if>
                    <s:else>
                        <s:textfield name="saleChannelTypeForm.name" id="saleChannelTypeForm.name" readOnly="true"
                                     cssClass="txtInputFull" maxLength="50"/>
                    </s:else>
                </td>
                <td class="label">
                    <s:if test="#session.toEditChannelType == 1">
                        <tags:label key="MES.CHL.174" required="true" />
                    </s:if>
                    <s:else>
                        <tags:label key="MES.CHL.174" />
                    </s:else>
                </td>
                <td class="label">
                    <s:textfield name="saleChannelTypeForm.debitDefaultStr" id="saleChannelTypeForm.debitDefault" cssClass="txtInputFull" maxLength="17"/>
                </td>
                <td class="label">
                    <s:if test="#session.toEditChannelType == 1">
                        <tags:label key="MES.CHL.175" required="true" />
                    </s:if>
                    <s:else>
                        <tags:label key="MES.CHL.175"/>
                    </s:else>                    
                    
                </td>
                <td class="text">
                    <s:textfield name="saleChannelTypeForm.rateDebitStr" id="saleChannelTypeForm.rateDebit"  cssClass="txtInputFull" maxLength="17"/>
                </td>
            </tr>
        </table>
        <br />
        <s:if test="#session.toEditChannelType == 0">
            <tags:submit formId="saleChannelTypeForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.find"
                         validateFunction="validateSearchForm();"
                         preAction="staffStockTypeLimitAction!searchChannelType.do"/>
        </s:if>
        <s:if test="#session.toEditChannelType == 1">
            <tags:submit formId="saleChannelTypeForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.accept"
                         validateFunction="validateForm();"
                         preAction="staffStockTypeLimitAction!editChannelType.do"/>
            <tags:submit formId="saleChannelTypeForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MES.CHL.073"
                         preAction="staffStockTypeLimitAction!preparePage.do"/>
        </s:if>
    </tags:imPanel>
</s:form>
<br/>
<div id="listChannelType">
    <jsp:include page="searchStaffStockTypeLimit.jsp"/>
</div>

<script type="text/javascript">
    // trim cac truong du lieu
    clearSpace = function() {
        if ($('saleChannelTypeForm.name').value != null)
            $('saleChannelTypeForm.name').value = trim($('saleChannelTypeForm.name').value);
        if ($('saleChannelTypeForm.debitDefault').value != null)
            $('saleChannelTypeForm.debitDefault').value = trim($('saleChannelTypeForm.debitDefault').value);
        if ($('saleChannelTypeForm.rateDebit').value != null)
            $('saleChannelTypeForm.rateDebit').value = trim($('saleChannelTypeForm.rateDebit').value);
        return true;
    }

    validateForm=function(){
        clearSpace();
        $('displayResultMsgClient' ).innerHTML = "";
            
        if ($('saleChannelTypeForm.debitDefault').value < 0){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LIMIT.0010"/>';
            $('saleChannelTypeForm.debitDefault').focus();
            return false;
        }
        if ($('saleChannelTypeForm.rateDebit').value < 1){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LIMIT.0024"/>';
            $('saleChannelTypeForm.rateDebit').focus();
            return false;
        }
            
        if (!isDouble($('saleChannelTypeForm.debitDefault').value)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LIMIT.0010"/>';
            $('saleChannelTypeForm.debitDefault').focus();
            return false;
        }

        if (!isDouble($('saleChannelTypeForm.rateDebit').value)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LIMIT.0023"/>';
            $('saleChannelTypeForm.rateDebit').focus();
            return false;
        }
        
//        defaultDebit = Math.floor($('saleChannelTypeForm.debitDefault').value);
//        rateDebit = Math.floor($('saleChannelTypeForm.rateDebit').value);
//        
//        if (String(defaultDebit).length == 17) {
//            $('displayResultMsgClient').innerHTML= 'Phần nguyên hạn mức mặc định không dài hơn 17 ký tự';
//            $('saleChannelTypeForm.debitDefault').focus();
//            return false;
//        }
        
        return true;
    }
    
    
        validateSearchForm=function(){
        clearSpace();
        $('displayResultMsgClient' ).innerHTML = "";
        if (!isDouble($('saleChannelTypeForm.debitDefault').value)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LIMIT.0010"/>';
            $('saleChannelTypeForm.debitDefault').focus();
            return false;
        }

        if (!isDouble($('saleChannelTypeForm.rateDebit').value)){
            $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LIMIT.0023"/>';
            $('saleChannelTypeForm.rateDebit').focus();
            return false;
        }

        return true;

    }
        

</script>

