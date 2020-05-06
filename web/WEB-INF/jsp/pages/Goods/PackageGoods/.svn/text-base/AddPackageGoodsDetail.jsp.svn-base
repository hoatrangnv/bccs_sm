<%-- 
    Document   : ${fn:escapeXml(name)}
    Created on : ${fn:escapeXml(date)}, ${fn:escapeXml(time)}
    Author     : ${fn:escapeXml(user)}
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            //request.setAttribute("messageAdd", request.getSession().getAttribute("messageAdd"));
            //request.setAttribute("flag", request.getSession().getAttribute("flag"));
%>

<s:form theme="simple" action="packageGoodsNewAction!addPackageGoodsDetailPopUp.do" id="saleServicesDetailForm" method="post">
<s:token/>

    <%--<tags:imPanel title="Thông tin mặt hàng">--%>
    <table class="inputTbl8Col">
        <sx:div id="divAddPackage">
            <tags:imPanel title="MSG.GOD.103">
                <!-- phan hien thi message -->
                <div>
                    <tags:displayResult id="messageAdd" property="message"  type="key"/>

                </div>
                <!-- phan thong tin loai mat hang -->

                <s:hidden name="saleServicesDetailForm.id" id="saleServicesDetailForm.id"/>
                <s:hidden name="saleServicesDetailForm.packageGoodsMapId" id="saleServicesDetailForm.packageGoodsMapId"/>

                <table class="inputTbl2Col">
                    <tr>
                        <td style="width:35%;" colspan="2">
                            <tags:label key="MSG.GOD.027" required="true"/>
                        </td>
                        <td>
                            <tags:imSelect name="saleServicesDetailForm.stockTypeId"
                                           id="saleServicesDetailForm.stockTypeId"
                                           cssClass="txtInputFull"
                                           disabled="true"
                                           headerKey="" headerValue="MSG.GOD.216"
                                           list="listStockTypes"
                                           listKey="stockTypeId" listValue="name"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <tags:label key="MSG.GOD.104" required="true"/>
                            <!--                    Mặt hàng<font color="red">*</font>-->
                        </td>
                        <td>
                            <tags:imSelect name="saleServicesDetailForm.stockModelId"
                                           id="saleServicesDetailForm.stockModelId"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="MSG.GOD.217"
                                           list="listStockModels"
                                           listKey="stockModelId" listValue="name"
                                           />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <tags:label key="MSG.GOD.031"/>
                            <!--                    Ghi chú-->
                        </td>
                        <td>
                            <s:textarea name="saleServicesDetailForm.notes" id="saleServicesDetailForm.notes" cssClass="txtInputFull"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <tags:label key="MSG.requiredPakage"/>
                        </td>
                        <td>
                            <input type="checkbox" name="saleServicesDetailForm.requiredCheck" onchange="changeRequiredCheck()" id="saleServicesDetailForm.requiredCheck" cssClass="txtInputFull"/>
                        </td>
                        <td></td>
                    </tr>
                </table>

                <div align="center" style="width:100%; padding-bottom:5px; padding-top:15px;">
                   
                    <tags:submit id="add" formId="saleServicesDetailForm"
                                 showLoadingText="false"
                                 targets="popupBody" value="MSG.add"
                                 cssStyle="width:80px;"
                                 disabled="false"
                                 preAction="packageGoodsNewAction!addPackageGoodsDetailPopUp.do"
                                 />
                    
     
                    <input type="button" value="<s:text name="MES.CHL.073"/>" style="width:80px;" onclick="closeForm()">
                    
                    <%--tags:submit id="cancel" confirm="false"
                                 formId="saleServicesDetailForm"
                                 cssStyle="width:80px;"
                                 disabled="false"
                                 showLoadingText="false"
                                 targets="bodyContent" value="MES.CHL.073"
                                 preAction="packageGoodsNewAction!cancelAddPackageGoodsDetail.do"
                                 /--%>
                </div>
            </tags:imPanel>
        </sx:div>
    </table>
</s:form>
<script language="javascript">
    
    closeForm = function (){
        window.close();        
    }
    
    changeRequiredCheck = function(){
        var requiredCheck=document.getElementById("saleServicesDetailForm.requiredCheck");
        if(requiredCheck.checked){
            requiredCheck.value='true';
        }
    }
</script>
