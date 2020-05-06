<%-- 
    Document   : anyPayTransManagement
    Created on : Oct 13, 2009, 11:58:50 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<s:form method="POST" id="anyPayTransForm" action="SaleToAnyPayAction" theme="simple">
<s:token/>

    <tags:imPanel title="Thông tin tìm kiếm">
    <table class="inputTbl6Col">
        <tr>
            <td colspan="6">
                <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
            </td>
        </tr>
        <tr>
            <td class="label">Mã giao dịch</td>
            <td class="text">
                <s:textfield name="anyPayTransForm.pTransIdS" id="pTransIdS" theme="simple" maxlength="100" cssClass="txtInputFull"   readonly="false"/>
            </td>
            <td class="label">Loại giao dịch</td>
            <td class="text">
                <s:select name="anyPayTransForm.pTransTypeS" id="pTransTypeS"
                          list="#{'2':'CASHTRANSFER','3':'RELOAD'}"
                          headerKey="" headerValue="-Chọn loại GD--" disabled="false" theme="simple"  cssClass="txtInputFull"
                          />
            </td>
            <td class="label">Trạng thái GD</td>
            <td class="text">
                <s:select name="anyPayTransForm.pStatusS" id="pStatusS"
                          list="#{'1':'Hiệu lực','0':'Đã huỷ'}"
                          headerKey="" headerValue="-Chọn trạng thái--" disabled="false" theme="simple"  cssClass="txtInputFull"
                          />
            </td>
        </tr>
        <tr>
            <td class="label">Từ ngày</td>
            <td class="text">
                <tags:dateChooser property="anyPayTransForm.pFromDateS"  id="pFromDateS" styleClass="txtInput"  insertFrame="false"
                                  readOnly="false"/>
            </td>
            <td class="label">Đến ngày</td>
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
                     value="Tìm kiếm"
                     preAction="SaleToAnyPayAction!searchAnyPayTrans.do"
                   />
</div>
</tags:imPanel>

<jsp:include page="anyPayTransList.jsp"/>
</s:form>
<script>
    
</script>
