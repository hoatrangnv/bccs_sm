<%-- 
    Document   : shopDKTTDetail
    Created on : Mar 10, 2016, 3:56:37 PM
    Author     : mov_itbl_dinhdc
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Notes   : tim kiem cac cac shop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listShops", request.getSession().getAttribute("listShops"));
%>


<tags:imPanel title="MSG.sale.channel.search">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key" />
    </div>
    <s:form action="authenShopDKTTAction" theme="simple" enctype="multipart/form-data"  method="post" id="shopForm">
<s:token/>

        <table class="inputTbl6Col">
            <tr>
                <td class="label"><tags:label key="MSG.SAE.109" required="true"/></td>
                <td class="text">
                    <s:textfield name="shopForm.shopCode" id="shopForm.shopCode"  maxlength="1000" cssClass="txtInputFull" readonly="false" />
                </td>
                <td class="label"><tags:label key="MSG.SAE.110" /></td>
                <td class="text">
                    <s:textfield name="shopForm.name" id="shopForm.name" maxlength="1000"  cssClass="txtInputFull" readonly="false"/>
                </td>
            </tr>
        </table>
        <%--        
        <div class="divHasBorder" style="margin-top:10px; padding:3px;">
            <tags:imRadio name="shopForm.searchType" id="searchType"
                          list="1:TYPE.CHECK.AUTHEN,2:TYPE.REMOVE.AUTHEN"
                          onchange="radioClick(this.value)" />
        </div> 
        --%>
        <div align="center" style="vertical-align:top; padding-top:10px;">
            <tags:submit formId="shopForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="divDisplayInfo"
                         value="MSG.find"
                         preAction="authenShopDKTTAction!searchShop.do"
                         validateFunction="checkValidFieldsSearch();"/>
                         
            <%--
           <tags:submit formId="shopForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="divDisplayInfo"
                         value="MSG.Authen"
                         preAction="authenShopDKTTAction!authenShop.do"/>
            --%>
        </div>
        <sx:div id="resultArea">
            <jsp:include page="shopDKTTResult.jsp"></jsp:include>
        </sx:div>
    </s:form>
</tags:imPanel>

<script language="javascript">
   
    checkValidFieldsSearch = function() {
           
            var shopCode = document.getElementById("shopForm.shopCode");
            if (shopCode.value == ''){
                <%--"Bạn chưa nhap ma cua hang"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.shopcode')"/>';
                shopCode.focus();
                return false;
            }
            
            return true;
     }

</script>


