<%-- 
    Document   : SaleAnyPayTransManagement
    Created on : Dec 26, 2009, 9:09:12 AM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<s:form method="POST" id="anyPayTransForm" action="SaleAnyPayToAgentOrStaffAction" theme="simple">
<s:token/>

    <tags:imPanel title="MSG.SIK.193">
    <table class="inputTbl6Col">
        <tr>
            <td colspan="6">
                <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
            </td>
        </tr>
        <tr>
            <%--<td class="label">Mã giao dịch (<font color="red">*</font>)</td>--%>
            <td ><tags:label key="MSG.SIK.181" required="true"/></td>
            <td class="text">
                <s:textfield name="anyPayTransForm.pTransIdS" id="pTransIdS" theme="simple" maxlength="100" cssClass="txtInputFull"   readonly="false"/>
            </td>
            <%--<td class="label">Loại giao dịch</td>--%>
            <td ><tags:label key="MSG.SIK.182"/></td>
            <td class="text">
                <%--<s:select name="anyPayTransForm.pTransTypeS" id="pTransTypeS"
                          list="#{'TRANS':'CASHTRANSFER','LOAD':'RELOAD'}"
                          headerKey="" headerValue="--Chọn loại GD--" disabled="false" theme="simple"  cssClass="txtInputFull"
                          />--%>
                <tags:imSelect name="anyPayTransForm.pTransTypeS" id="pTransTypeS"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SIK.256"
                                       list="TRANS:MSG.SIK.194,LOAD:MSG.SIK.195"
                                       />
            </td>
            <%--<td class="label">Trạng thái GD</td>--%>
            <td ><tags:label key="MSG.SIK.196"/></td>
            <td class="text">
                <%--<s:select name="anyPayTransForm.pStatusS" id="pStatusS"
                          list="#{'3':'Hiệu lực','9':'Đã huỷ'}"
                          headerKey="" headerValue="--Chọn trạng thái--" disabled="false" theme="simple"  cssClass="txtInputFull"
                          />--%>
                <tags:imSelect name="anyPayTransForm.pStatusS" id="pStatusS"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SIK.257"
                                       list="3:Created,5:Cancelled"
                                       />
            </td>
        </tr>
        <tr>
            <%--<td class="label">Từ ngày</td>--%>
            <td ><tags:label key="MSG.SIK.113"/></td>
            <td class="text">
                <tags:dateChooser property="anyPayTransForm.pFromDateS"  id="pFromDateS" styleClass="txtInput"  insertFrame="false"
                                  readOnly="false"/>
            </td>
            <%--<td class="label">Đến ngày</td>--%>
            <td ><tags:label key="MSG.SIK.114"/></td>
            <td class="text">
                <tags:dateChooser property="anyPayTransForm.pToDateS"  id="pToDateS" styleClass="txtInput"  insertFrame="false"
                                  readOnly="false"/>
            </td>            
        </tr>        
    </table>
<br/>
<div align="center">
    <tags:submit targets="bodyContent" formId="anyPayTransForm"
                     showLoadingText="true" cssStyle="width:80px;"
                     value="MSG.SIK.115" validateFunction="validateForm()"
                     preAction="SaleAnyPayToAgentOrStaffAction!searchAnyPayTrans.do"
                   />
</div>
</tags:imPanel>

<jsp:include page="SaleAnyPayTransList.jsp"/>
</s:form>
<script>
    validateForm = function (){
        pTransIdS =$('pTransIdS').value;
        if(pTransIdS==null || pTransIdS ==''){
<%--$('returnMsgClient').innerHTML="!!! Chưa nhập mã giao dịch" ;--%>
            $('returnMsgClient').innerHTML='<s:text name="MSG.SIK.199"/>';
            return false;
        }
        return true;
    }
</script>
