<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListSearchResetLimit
    Created on : Jun 25, 2010, 6:24:29 PM
    Author     : User
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            String pageId = request.getParameter("pageId");
            if (request.getAttribute("listStaff") == null) {
                request.setAttribute("listStaff", request.getSession().getAttribute("listStaff" + pageId));
            }
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("typeId", request.getSession().getAttribute("typeId" + pageId));
            request.setAttribute("flag", request.getSession().getAttribute("flag" + pageId));
            request.setAttribute("changeStatus", request.getSession().getAttribute("changeStatus" + pageId));
%>
<div style="width:100%">
    <s:hidden name="collAccountManagmentForm.accountId" id="collAccountManagmentForm.accountId"/>
    <display:table id="coll" name="listStaff" class="dataTable" pagesize="10"
                   targets="searchArea" requestURI="CollAccountManagmentAction!nextPageReset.do"
                   cellpadding="0" cellspacing="0">
        <display:column  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.147'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <s:property escapeJavaScript="true"  value="%{#attr.coll_rowNum}"/>
        </display:column>
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.151'))}" property="staffCode" style="width:130px;text-align:left"/>
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.152'))}" property="name" style="width:240px;text-align:left"/>
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.148'))}" property="idNo" sortable="false" headerClass="tct" style="width:100px;text-align:left"/>
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.153'))}" property="numPreMob" style="width:50px;text-align:center"/>
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.154'))}" property="numPosMob" style="width:50px;text-align:center"/>
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.155'))}" property="numPreHpn" style="width:50px;text-align:center"/>
        <display:column escapeXml="true"  title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.156'))}" property="numPosHpn" style="width:50px;text-align:center"/>
        <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SIK.009'))}" sortable="false" headerClass="tct" style="width:20px;text-align:center">
            <div align="center">
                <s:url action="CollAccountManagmentAction!clickStaff" id="URL" encode="true" escapeAmp="false">
                    <!--CSRF Hieptd-->
                    <s:token/>
                    <s:param name="accountId" value="#attr.coll.accountId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                </s:url>
                <sx:a targets="searchArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                    <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getError('MSG.SIK.009')"/>" alt="<s:property escapeJavaScript="true"  value="getError('MSG.SIK.157')"/>"/>
                </sx:a>
            </div>
        </display:column>
        <display:footer> <tr> <td colspan="11" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SIK.150'))}<s:property escapeJavaScript="true"  value="%{#request.listStaff.size()}" /></td> <tr> </display:footer>
        </display:table>

    <fieldset class="imFieldset">
        <legend><tags:label key="MSG.SIK.158"/></legend>
        <div>
            <tags:displayResult id="displayResultMsgUpdate" property="messageUpdate" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl6Col">
            <sx:div id="displayParamDetail">
                <tr>                    
                    <%--<td class="label">Mã CTV/ĐB </td>--%>
                    <td class="label"><tags:label key="MSG.SIK.151"/></td>
                    <td class="text">
                        <s:textfield name="collAccountManagmentForm.staffCodeReset" id="staffCodeReset" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <td class="text">
                        <s:textfield name="collAccountManagmentForm.staffNameReset" id="staffNameReset" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    <%--<td class="label">Dịch vụ (<font color="red">*</font>)</td>--%>
                    <td class="label"><tags:label key="MSG.SIK.159" required="true"/></td>
                    <td class="text">
                        <%--<s:select name="collAccountManagmentForm.telecomserviceId"
                                  id="telecomserviceId"
                                  cssClass="txtInputFull"
                                  theme="simple"
                                  headerKey="" headerValue="--Chọn loại dịch vụ--"
                                  list="#{'1':'Prepaid HP','2':'Postpaid HP','3':'Prepaid MB','4':'Postpaid MB'}"
                                  />--%>
                        <tags:imSelect name="collAccountManagmentForm.telecomserviceId" id="telecomserviceId"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       list="1:MSG.SIK.160,2:MSG.SIK.161,3:MSG.SIK.162,4:MSG.SIK.163"
                                       headerKey="" headerValue="MSG.SIK.254"/>
                    </td>
                </tr>                
            </sx:div>
        </table>

        <div style="height:10px">
        </div>
        <div align="center" style="padding-bottom:5px;">
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="update" confirm="false" formId="collAccountManagmentForm"
                             showLoadingText="true" targets="searchArea" value="MSG.SIK.102"
                             cssStyle="width:60px;"
                             disabled="${fn:escapeXml(requestScope.changeStatus)}"
                             validateFunction="checkupdate()"
                             confirm="true" confirmText="MSG.SIK.164"
                             preAction="CollAccountManagmentAction!updateInfo.do"
                             />
            </td>
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="cancel" confirm="false" formId="collAccountManagmentForm"
                             cssStyle="width:60px;"
                             disabled="${fn:escapeXml(requestScope.changeStatus)}"
                             showLoadingText="true" targets="searchArea" value="MSG.SIK.065"
                             preAction="CollAccountManagmentAction!cancelReset.do"
                             />
            </td>
        </div>
    </fieldset>

</div>

<script language="javascript">
    checkupdate = function(){
        var telecomserviceId = document.getElementById("telecomserviceId");        
        if (telecomserviceId.value == null || telecomserviceId.value == '' ){            
    <%--$('displayResultMsgUpdate').innerHTML="Bạn phải chọn loại dịch vụ"--%>
                $('displayResultMsgUpdate').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.040')"/>';
                telecomserviceId.focus();
                return false;
            }
            return true;
        }
</script>

