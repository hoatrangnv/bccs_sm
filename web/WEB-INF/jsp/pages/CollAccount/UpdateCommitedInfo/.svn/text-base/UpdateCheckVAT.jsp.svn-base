<%-- 
    Document   : UpdateCheckVAT
    Created on : Jul 9, 2010, 5:55:40 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>

<%
            UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
            String shopCode = userToken.getShopCode();
            String shopName = userToken.getShopName();
            String staffCode = userToken.getLoginName();
            String staffName = userToken.getStaffName();
            request.setAttribute("contextPath", request.getContextPath());

            request.setAttribute("shopName", shopCode + " - " + shopName);
            request.setAttribute("staffName", staffCode + " - " + staffName);
%>

<s:form action="SaleAnyPayToAgentOrStaffAction" theme="simple" method="post" id="saleToAnyPayForm">
<s:token/>

    <tags:imPanel title="MSG.SIK.265">
        <table class="inputTbl4Col">
            <tr>
                <td colspan="2">
                    <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                    <s:hidden name="saleToAnyPayForm.acountAnyPayId" id="acountAnyPayId"/>
                </td>
            </tr>            
            <tr>
            <tr id="trImpByFile">
                <td  class="label" ><tags:label key="MSG.generates.file_data" required="true"/></td>
                <td class="text" >
                    <tags:imFileUpload name="saleToAnyPayForm.clientFileName" id="saleToAnyPayForm.clientFileName" serverFileName="saleToAnyPayForm.serverFileName" extension="xls"/>
                </td>
            </tr>
        </tr>        
    </table>
    <br/>    
    <div style="width:100%" align="center">
        <tags:submit targets="bodyContent" formId="saleToAnyPayForm"
                     value="MSG.SIK.009"
                     cssStyle="width:80px;"
                     confirm="true"
                     confirmText="INF.SIK.003"
                     preAction="SaleAnyPayToAgentOrStaffAction!updateCheckVAT.do"
                     showLoadingText="true"/>
    </div>
</tags:imPanel>
</s:form>

<script type="text/javascript" language="javascript">
    validateSaleToAccountAnyPay = function(){    
        return true;
    }
</script>
