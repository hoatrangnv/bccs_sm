<%-- 
    Document   : simtkManagement
    Created on : Jan 17, 2012, 1:49:10 PM
    Author     : TrongLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>

<s:form action="simtkManagmentAction!searchSimtoolkit" theme="simple"  enctype="multipart/form-data" method="post" id="form">
<s:token/>

    <tags:imPanel title="menu.simtk.management">
        <table class="inputTbl2Col">
            <tr>
                <td class="label"><tags:label key="MSG.SIK.015" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="form.channelTypeIdSearch"
                                   id="form.channelTypeIdSearch"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="" headerValue="MSG.SIK.260"
                                   list="lstChannelType" listKey="channelTypeId" listValue="name"
                                   onchange="selectChannelType();"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.108" required="true"/></td>
                <td class="text">
                    <tags:imSearch codeVariable="form.shopCodeSearch" nameVariable="form.shopNameSearch"
                                   codeLabel="MSG.SAE.109" nameLabel="MSG.SAE.110"                                   
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   getNameMethod="getNameShop" elementNeedClearContent="form.staffCodeSearch;form.staffNameSearch;form.ownerCodeSearch;form.ownerNameSearch"
                                   roleList=""/>
                </td>
            </tr>

            <s:if test="#attr.form.objectTypeSearch == 2 ">
                <tr>
                    <td class="label"><tags:label key="MSG.SIK.234"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="form.staffCodeSearch" nameVariable="form.staffNameSearch"
                                       codeLabel="MSG.SAE.111" nameLabel="MSG.SAE.112"
                                       searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="form.shopCodeSearch"
                                       getNameMethod="getNameStaff" elementNeedClearContent="form.ownerCodeSearch;form.ownerNameSearch"
                                       roleList=""/>
                    </td>
                </tr>
                <s:if test="#attr.form.isVtUnitSearch == 2 ">
                    <tr>
                        <td class="label"><tags:label key="MSG.SIK.092"/></td>
                        <td class="text">
                            <tags:imSearch codeVariable="form.ownerCodeSearch" nameVariable="form.ownerNameSearch"
                                           codeLabel="MSG.SAE.109" nameLabel="MSG.SAE.110"
                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                           otherParam="form.shopCodeSearch;form.staffCodeSearch"
                                           searchMethodName="getListChannel"
                                           getNameMethod="getChannelName" 
                                           roleList=""/>
                        </td>
                    </tr>
                </s:if>
            </s:if>
            <s:else>
                <tr>
                    <td class="label"><tags:label key="MSG.SIK.229"/></td>
                    <td class="text">
                        <tags:imSearch codeVariable="form.ownerCodeSearch" nameVariable="form.ownerNameSearch"
                                       codeLabel="MSG.SIK.235" nameLabel="MSG.SIK.236"                                   
                                       searchClassName="com.viettel.im.database.DAO.CollAccountManagmentDAO"
                                       searchMethodName="getListOwner"
                                       getNameMethod="getNameOwner"
                                       roleList=""/>
                    </td>
                </tr>
            </s:else>
            <tr>
                <td class="label"><tags:label key="MSG.SIK.018"/></td>
                <td class="text">
                    <s:textfield id="form.isdnSearch" name="form.isdnSearch" theme="simple" cssClass="txtInputFull" maxlength="20"/>
                </td>                            
            </tr>
            <tr>
                <td colspan="2" class="text" align="center" theme="simple">
                    <tags:displayResult id="displayResultMsg" property="message" propertyValue="paramMsg" type="key" />
                </td>
            </tr>
            <tr>
                <td colspan="2" class="text" align="center" theme="simple">
                    <tags:submit  formId="form" cssStyle="width:120px;"
                                  showLoadingText="true"
                                  targets="bodyContent"
                                  value="MSG.SIK.115"
                                  validateFunction="checkValidateSearch();"
                                  preAction="simtkManagmentAction!searchSimtk.do"
                                  />
                </td>
            </tr>
        </table>
    </tags:imPanel>
    <sx:div id="searchResultArea" theme="simple">
        <jsp:include page="listSimtk.jsp"/>
    </sx:div>
</s:form>

<script language="javascript">
    
    selectChannelType = function(){
        $( 'displayResultMsg' ).innerHTML='';
        var channelTypeId = "";
        var shopCode = "";
        var staffCode = "";
        if (document.getElementById("form.channelTypeIdSearch") != null && document.getElementById("form.channelTypeIdSearch").value != null){
            channelTypeId = trim(document.getElementById("form.channelTypeIdSearch").value);
        }
        if (document.getElementById("form.shopCodeSearch") != null && document.getElementById("form.shopCodeSearch").value != null){
            shopCode = trim(document.getElementById("form.shopCodeSearch").value);
        }
        if (document.getElementById("form.staffCodeSearch") != null && document.getElementById("form.staffCodeSearch").value != null){
            staffCode = trim(document.getElementById("form.staffCodeSearch").value);
        }
        
        if (channelTypeId == ""){
            $( 'displayResultMsg' ).innerHTML='Error. You must select channel type!';
            return;
        }
        if (shopCode == ""){
            $( 'displayResultMsg' ).innerHTML='';
            return;
        }
        gotoAction("bodyContent",'simtkManagmentAction!selectChannelType.do?channelTypeId=' + channelTypeId +'&shopCode='+ shopCode 
            +'&staffCode='+ staffCode + "&" + token.getTokenParamString());
    }
    
    checkValidateSearch = function(){
        
        $( 'displayResultMsg' ).innerHTML='';
        var channelTypeId = "";
        var shopCode = "";
        var isdn = "";
        if (document.getElementById("form.channelTypeIdSearch") != null && document.getElementById("form.channelTypeIdSearch").value != null){
            channelTypeId = trim(document.getElementById("form.channelTypeIdSearch").value);
        }
        if (document.getElementById("form.shopCodeSearch") != null && document.getElementById("form.shopCodeSearch").value != null){
            shopCode = trim(document.getElementById("form.shopCodeSearch").value);
        }
        if (document.getElementById("form.isdnSearch") != null && document.getElementById("form.isdnSearch").value != null){
            isdn = trim(document.getElementById("form.isdnSearch").value);
        }
        
        if (channelTypeId == ""){
            $( 'displayResultMsg' ).innerHTML='Error. You must select channel type!';
            return false;
        }
        if (shopCode == "" && isdn == ""){
            $( 'displayResultMsg' ).innerHTML='Error. You must select shop code!';
            return false;
        }
        return true;
    }
    
    
</script>
