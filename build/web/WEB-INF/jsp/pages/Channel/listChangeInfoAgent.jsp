<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Copyright 2010 Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 

    Document   : listChangeInfoAgent
    Created on : Nov 22, 2011, 4:13:15 PM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
    String pageId = request.getParameter("pageId");
    if (request.getAttribute("listAgent") == null) {
        request.setAttribute("listAgent", request.getSession().getAttribute("listAgent" + pageId));
    }
    request.setAttribute("contextPath", request.getContextPath());
    //request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
    //request.setAttribute("flag", request.getSession().getAttribute("flag" + pageId));
    request.setAttribute("changeStatus", request.getSession().getAttribute("changeStatus" + pageId));
    //request.setAttribute("changeInfo", request.getSession().getAttribute("changeInfo" + pageId));

    request.setAttribute("listProfileState", request.getSession().getAttribute("listProfileState" + pageId));
    request.setAttribute("listBoardState", request.getSession().getAttribute("listBoardState" + pageId));
    request.setAttribute("listShopStatus", request.getSession().getAttribute("listShopStatus" + pageId));
    request.setAttribute("listCheckVatStatus", request.getSession().getAttribute("listCheckVatStatus" + pageId));
    request.setAttribute("listAgentType", request.getSession().getAttribute("listAgentType" + pageId));
%>
<div style="width:100%">
    <s:hidden name="changeInfoAgentForm.agentId" id="changeInfoAgentForm.agentId"/>
    <display:table id="coll" name="listAgent" class="dataTable" pagesize="10"
                   targets="searchArea" requestURI="changeInformationAgentAction!selectPage.do"
                   cellpadding="0" cellspacing="0">
        <display:column  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.001'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.coll_rowNum}"/>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.002'))}" property="agentCode" style="width:130px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.003'))}" property="agentName" style="width:240px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.004'))}" property="parShopCode" style="width:130px;text-align:center"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.005'))}" property="parShopName" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.006'))}" property="tradeName" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.007'))}" property="contactName" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.008'))}" sortable="false" headerClass="tct" style="width:100px;text-align:left">
            <s:if test = "#attr.coll.status*1 == 1">
                <s:text name="Active"/>
            </s:if>
            <s:elseif test = "#attr.coll.status*1 == 2">
                <s:text name="Locked"/>
            </s:elseif>
            <s:else>
                <s:text name="Cancelled"/>
            </s:else>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.009'))}" property="address" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.010'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <div align="center">
                <s:url action="changeInformationAgentAction!clickAgent" id="URL" encode="true" escapeAmp="false">
                    <s:param name="agentId" value="#attr.coll.agentId"/>                    
                </s:url>
                <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                    <%--<img src="${contextPath}/share/img/accept.png" title="Cập nhật/Sửa" alt="Sửa"/>--%>
                    <img src="${contextPath}/share/img/accept.png" title="<s:text name="MES.CHL.061"/>"  alt="<s:text name="MES.CHL.062"/>"/>
                </sx:a>
            </div>
        </display:column>
        <%--       <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.011'))}" sortable="false" headerClass="tct" style="width:80px;text-align:center">
                   <div align="center">                
                       <s:url action="changeInformationAgentAction!showLogAccountAgent" id="URLView" encode="true" escapeAmp="false">
                           <s:param name="agentId" value="#attr.coll.agentId"/>
                       </s:url>
                       <a href="javascript: void(0);" onclick="openPopup('<s:property escapeJavaScript="true"  value="#URLView"/>',800,700)">
                           ${fn:escapeXml(imDef:imGetText('MES.CHL.138'))}
                       </a>
                   </div>
               </display:column> --%>
        <display:footer> <tr> <td colspan="11" style="color:green">
                    <s:text name="MSG.totalRecord"/>
                    <s:property escapeJavaScript="true"  value="%{#request.listAgent.size()}" /></td> <tr> </display:footer>
            </display:table>

    <fieldset class="imFieldset">
        <legend>
            <s:text name="MES.CHANGE.INFO.AGENT.012"/>
        </legend>
        <div>
            <tags:displayResult id="displayResultMsgUpdate" property="messageUpdate" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl8Col">
            <sx:div id="displayParamDetail">
                <tr>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.002" required="true"/></td>
                    <td class="text">
                        <s:textfield theme="simple" name="changeInfoAgentForm.agentCode" id="agentCode" maxlength="14" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="text" colspan="2">
                        <s:textfield theme="simple" name="changeInfoAgentForm.agentName" id="agentName" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.004" required="true"/></td>
                    <td class="text">
                        <s:textfield theme="simple" name="changeInfoAgentForm.parShopCode" id="parShopCode" maxlength="14" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="text" colspan="2">
                        <s:textfield theme="simple" name="changeInfoAgentForm.parShopName" id="parShopName" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.006" required="true"/></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.tradeName" id="tradeName" theme="simple" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.014" /></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.tin" id="tin" theme="simple" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.015" /></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.account" id="account" cssClass="txtInputFull"  insertFrame="false" theme="simple"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.016" /></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.bank" id="bank" cssClass="txtInputFull" theme="simple" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.017" required="true"/></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.contactName" id="contactName" theme="simple" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.018" /></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.contactTitle" id="contactTitle" theme="simple" maxlength="100" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="Mail" /></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.mail" id="mail" cssClass="txtInputFull"  insertFrame="false" theme="simple"/>
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.019" required="true"/></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.idNo" id="idNo" cssClass="txtInputFull" theme="simple" />
                    </td>
                </tr>                
                <tr>
                    <td class="label"><tags:label key="MSG.SIK.020" required="true"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="changeInfoAgentForm.provinceCode" nameVariable="changeInfoAgentForm.provinceName"
                                       codeLabel="MSG.SIK.021" nameLabel="MSG.SIK.022"
                                       searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                       elementNeedClearContent="changeInfoAgentForm.districtCode;changeInfoAgentForm.districtName;changeInfoAgentForm.precinctCode;changeInfoAgentForm.precinctName"
                                       searchMethodName="getProvinceList"
                                       getNameMethod="getProvinceName" postAction="changeArea()"                                       
                                       readOnly="true"/>
                    </td>

                    <td class="label"><tags:label key="MSG.SIK.023" required="true"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="changeInfoAgentForm.districtCode" nameVariable="changeInfoAgentForm.districtName"
                                       codeLabel="MSG.SIK.024" nameLabel="MSG.SIK.025"
                                       searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                       searchMethodName="getDistrictList"
                                       elementNeedClearContent="changeInfoAgentForm.precinctCode;changeInfoAgentForm.precinctName"
                                       otherParam="changeInfoAgentForm.provinceCode"
                                       getNameMethod="getDistrictName" postAction="changeArea()"/>
                    </td>
                </tr>
                <tr>
                    <%--<td>Phường/Xã (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MSG.SIK.026" required="true"/></td>
                    <td colspan="3">
                        <tags:imSearch codeVariable="changeInfoAgentForm.precinctCode" nameVariable="changeInfoAgentForm.precinctName"
                                       codeLabel="MSG.SIK.027" nameLabel="MSG.SIK.028"
                                       searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                                       searchMethodName="getWardList"
                                       otherParam="changeInfoAgentForm.districtCode;changeInfoAgentForm.provinceCode"
                                       getNameMethod="getWardName" postAction="changeArea()"/>
                    </td>
                    <!-- haint41 11/02/2012 : them thong tin to,doi,so nha -->
                    <td class="label"><tags:label key="Tổ"/></td>
                    <td>
                        <s:textfield name="changeInfoAgentForm.streetBlockName" theme="simple" id="streetBlockName"  maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>
                    <td class="label"><tags:label key="Đường"/></td>
                    <td>
                        <s:textfield name="changeInfoAgentForm.streetName" theme="simple" id="streetName" maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>                    
                </tr>
                <tr>
                    <td class="label"><tags:label key="Số nhà"/></td>
                    <td colspan="3">
                        <s:textfield name="changeInfoAgentForm.home" theme="simple" id="home" maxlength="50" cssClass="txtInputFull" onchange="changeArea()"/>
                    </td>
                    <%--<td class="label">Địa chỉ (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MES.CHL.070" required="false"/></td>
                    <td class="text" colspan="3">
                        <s:textfield name="changeInfoAgentForm.address" theme="simple" id="address" maxlength="100" cssClass="txtInputFull" />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.020" required="false"/></td>
                    <td class="text" colspan="1">
                        <s:textfield name="changeInfoAgentForm.usefulWidth" theme="simple" id="usefulWidth" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.021" required="false"/></td>
                    <td class="text" colspan="1">
                        <s:textfield name="changeInfoAgentForm.surfaceArea" theme="simple" id="surfaceArea" maxlength="100" cssClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.022" required="false"/></td>
                    <td class="text">
                        <tags:imSelect name="changeInfoAgentForm.boardState"
                                       id="boardState"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listBoardState" listKey="code" listValue="name"
                                       />
                    </td>
                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.023" required="true"/></td>
                    <td class="text" colspan="1">
                        <tags:imSelect name="changeInfoAgentForm.status"
                                       id="status"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listShopStatus" listKey="code" listValue="name"
                                       />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="label.contact.phone" required="true"/></td>
                    <td class="text">
                        <s:textfield name="changeInfoAgentForm.telNumber" theme="simple" id="telNumber" maxlength="15" cssClass="txtInputFull" />
                    </td>
                    <td class="label"><tags:label key="label.sign.date.contract" required="true"/><%--Ngày ký hợp đồng--%></td>
                    <td class="text">
                        <tags:dateChooser property="changeInfoAgentForm.registryDate" id="registryDate" styleClass="txtInput" insertFrame="false"/>
                    </td>

                    <td class="label"><tags:label key="label.check.vat" required="true"/><%--Có thuế/không thuế--%></td>
                    <td class="text">
                        <tags:imSelect name="changeInfoAgentForm.checkVAT"
                                       id="checkVAT"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listCheckVatStatus" listKey="code" listValue="name"
                                       />
                    </td>

                    <td class="label"><tags:label key="MES.CHANGE.INFO.AGENT.024" required="false"/></td>
                    <td>
                        <tags:imSelect name="changeInfoAgentForm.agentType"
                                       id="agentType"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listAgentType" listKey="code" listValue="name"
                                       />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="L.100008" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="changeInfoAgentForm.profileState"
                                       id="profileState"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="listProfileState" listKey="code" listValue="name"
                                       />
                    </td>
                    <td colspan="2"></td>
                    <td class="label"><tags:label key="MSG.comment" required="false"/><%--Note--%></td>
                    <td class="text" colspan="3">
                        <s:textfield name="changeInfoAgentForm.note" theme="simple" id="note" maxlength="500" cssClass="txtInputFull" /> 
                    </td>
                </tr>
            </sx:div>
        </table>

        <div style="height:10px">
        </div>
        <div align="center" style="padding-bottom:5px;">
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="update" formId="changeInfoAgentForm"
                             showLoadingText="true" targets="searchArea" value="MES.CHL.071"
                             cssStyle="width:60px;"
                             disabled="${fn:escapeXml(requestScope.changeStatus)}"
                             validateFunction="checkupdate()"
                             confirm="true" confirmText="MES.CHANGE.INFO.AGENT.013"
                             preAction="changeInformationAgentAction!updateInfo.do"
                             />
            </td>
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="cancel" confirm="false" formId="changeInfoAgentForm"
                             cssStyle="width:60px;"
                             disabled="${fn:escapeXml(requestScope.changeStatus)}"
                             showLoadingText="true" targets="searchArea" value="MES.CHL.073"
                             preAction="changeInformationAgentAction!cancel.do"
                             />
            </td>
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="destroy" confirm="false" formId="changeInfoAgentForm"
                             cssStyle="width:60px;"                             
                             disabled="${fn:escapeXml(requestScope.changeStatus)}" confirm="true" confirmText="MSG.destroy.channel"
                             showLoadingText="true" targets="searchArea" value="MSG.destroy"
                             preAction="changeInformationAgentAction!destroy.do"
                             />
            </td>
        </div>
    </fieldset>

</div>

<script language="javascript">  
    checkupdate = function(){
        /* check ten giao dich */
        var tradeName = $('tradeName').value;
        if (tradeName ==null || trim(tradeName) ==""){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.005')"/>';
            $('tradeName').select();
            $('tradeName').focus();
            return false;
        }
        if (isHtmlTagFormat(trim(tradeName))){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.006')"/>';
            $('tradeName').select();
            $('tradeName').focus();
            return false;
        }
        /* check ten nguoi dai dien */
        var contactName = $('contactName').value;
        if (contactName ==null || trim(contactName) == ""){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.007')"/>';
            $('contactName').select();
            $('contactName').focus();
            return false;
        }
        if (isHtmlTagFormat(trim(contactName))){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.008')"/>';
            $('contactName').select();
            $('contactName').focus();
            return false;
        }
        /* check Id_no */
        var idNo = $('idNo').value; 
        if (idNo ==null || trim(idNo) ==""){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.016')"/>';
            $('idNo').select();
            $('idNo').focus();
            return false;
        }
        if (isHtmlTagFormat(trim(idNo))){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.017')"/>';
            $('idNo').select();
            $('idNo').focus();
            return false;
        }   
        /* check province */
        var province = document.getElementById("changeInfoAgentForm.provinceCode");
        if (trim(province.value).length ==0){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.SIK.006')"/>';
            province.focus();
            return false;
        }        
        /* check district */
        var district = document.getElementById("changeInfoAgentForm.districtCode");
        if (trim(district.value).length ==0){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.SIK.007')"/>';
            district.focus();
            return false;
        }
        /* check precinct */
        var precinct = document.getElementById("changeInfoAgentForm.precinctCode");
        if (trim(precinct.value).length ==0){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.SIK.008')"/>';
            precinct.focus();
            return false;
        }
        /* check address */
        var address = document.getElementById("address");
        if (address.value !=null && trim(address.value) != "" && isHtmlTagFormat(trim(address.value))){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.CHL.017')"/>';
            address.focus();
            return false;
        }
        /* check dien thoai lien he */
        var tel = document.getElementById("telNumber");
        if (tel.value ==null || trim(tel.value) ==""){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.009')"/>';
            tel.focus();
            return false;
        }
        if (tel.value != null && trim(tel.value) != ""){
            if(!isInteger(trim(tel.value))){
                $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0004')"/>';
                tel.focus();
                return false;
            }
        }
        /* check ngay ky hop dong */
        var registryDate=dojo.widget.byId("registryDate");
        if (registryDate.domNode.childNodes[1].value == null || registryDate.domNode.childNodes[1].value == "" ){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.010')"/>';
            registryDate.domNode.childNodes[1].focus();
            return false;
        }
        if(registryDate.domNode.childNodes[1].value != null && registryDate.domNode.childNodes[1].value != "" && !isDateFormat(registryDate.domNode.childNodes[1].value)){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0005')"/>';
            registryDate.domNode.childNodes[1].focus();
            return false;
        }
        if(registryDate.domNode.childNodes[1].value != null && registryDate.domNode.childNodes[1].value != "" && compareDates(GetDateSys(),registryDate.domNode.childNodes[1].value)){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.STAFF.0008')"/>';
            registryDate.domNode.childNodes[1].focus();
            return false;
        }
        /* check trang thai dai ly */
        var status = document.getElementById("status");
        if (status.value ==null || status.value == ""){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.011')"/>';
            status.focus();
            return false;
        }
        /* check co thue/k thue */
        var checkVAT = document.getElementById("checkVAT");
        if (checkVAT.value ==null || checkVAT.value == ""){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.012')"/>';
            checkVAT.focus();
            return false;
        }
        /* check trang thai ho so */ 
        var profileState = document.getElementById("profileState");
        if (profileState.value ==null || profileState.value == ""){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.013')"/>';
            checkVAT.focus();
            return false;
        }
        var account = document.getElementById("account");
        if (isHtmlTagFormat(trim(account.value))){

            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.037')"/>';
            account.select();
            account.focus();
            return false;
        }
        var bank = document.getElementById("bank");
        if (isHtmlTagFormat(trim(bank.value))){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.038')"/>';
            bank.select();
            bank.focus();
            return false;
        }
        var mail = document.getElementById("mail");
        if (isHtmlTagFormat(trim(mail.value))){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.039')"/>';
            mail.select();
            mail.focus();
            return false;
        }
        var note = document.getElementById("note");
        if (isHtmlTagFormat(trim(note.value))){
            $( 'displayResultMsgUpdate' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.040')"/>';
            note.select();
            note.focus();
            return false;
        }

        var note = document.getElementById("tin");
        if (isHtmlTagFormat(trim(note.value))){
            $( 'tin' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.AGENT.041')"/>';
            note.select();
            note.focus();
            return false;
        }


        return true;
    }   

    function GetDateSys() {
        var time = new Date();
        var dd=time.getDate();
        var mm=time.getMonth()+1;
        var yy=time.getFullYear() ;
        var temp = '';
        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;
        temp = dd + "/" + mm + "/" + yy ;
        return(temp);
    }
</script>


