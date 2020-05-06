<%-- 
    Document   : adminPage
    Created on : Dec 29, 2009, 9:56:59 AM
    Author     : Doan Thanh 8
    Desc       : trang quan tri he thong
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="MSG.command.code.info">
    <!-- phan hien thi thong tin lenh can thuc hien -->
    <div class="divHasBorder">
        <s:form action="lookupIsdnAction" theme="simple" method="post" id="iMAdminForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:150px; "><tags:label key="MSG.command.code" /></td>
                    <td>
                        <s:textfield name="iMAdminForm.command" id="iMAdminForm.command" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:100px; text-align:right; ">
                        <tags:submit formId="iMAdminForm"
                                     showLoadingText="true"
                                     cssStyle="width:80px;"
                                     targets="divActionResult"
                                     value="MSG.generates.execute"
                                     preAction="imAdminAction!doAction.do"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
    
    <sx:div id="divActionResult">
        
    </sx:div>



</tags:imPanel>
