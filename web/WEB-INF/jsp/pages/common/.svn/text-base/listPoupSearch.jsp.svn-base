<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : imFileUploadList
    Created on : Sep 12, 2009, 2:45:56 PM
    Author     : Doan Thanh 8
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%

            com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
            //String code = "Mã kho";
            //String name = "Tên kho";
            String code = baseDAOAction.getText("MSG.RET.066");
            String name = baseDAOAction.getText("MSG.RET.067");

            if (request.getAttribute("returnType") != null && request.getAttribute("returnType").equals("staff")) {
                //code = "Mã nhân viên";
                //name = "Tên nhân viên";
                code = baseDAOAction.getText("MSG.SAE.111");
                name = baseDAOAction.getText("MSG.SAE.112");
            }
            request.setAttribute("code", code);
            request.setAttribute("name", name);

%>
<s:form action="CommonStockAction.do" theme="simple"  id="autoCompleteSearchBean" method="post">
<s:token/>

    <s:hidden id="autoCompleteSearchBean.parentCode" name="autoCompleteSearchBean.parentCode"/>
    <s:hidden id="autoCompleteSearchBean.notAllChild" name="autoCompleteSearchBean.notAllChild"/>
    <s:hidden id="autoCompleteSearchBean.functionName" name="autoCompleteSearchBean.functionName"/>
    <div style="width:100%">
        <tags:imPanel title="MSG.GOD.009">
<!--            Tìm kiếm-->
            <table class="inputTbl4Col">
                <tr>
                    <td class="label">
                        <tags:label key="MSG.RET.066"/>
<!--                        Mã kho-->
                    </td>
                    <td class="text">
                        <s:textfield  id="autoCompleteSearchBean.code" name="autoCompleteSearchBean.code" cssClass="txtInputFull"/>
                    </td>
                    <td class="label">
                        <tags:label key="MSG.RET.067"/>
                    </td>
                    <td class="text">
                        <s:textfield id="autoCompleteSearchBean.name" name="autoCompleteSearchBean.name" cssClass="txtInputFull"/>
                    </td>
<!--                    <td class="label"> <input type="submit" value="Tìm kiếm" onclick="search()"></td>-->
                    <td class="label"> <input type="submit" value="${fn:escapeXml(imDef:imGetText('MSG.SAE.138'))}" onclick="search()"></td>
                </tr>
            </table>
            <sx:div id="#attr.id" cssStyle="height:500;overflow:auto">

                <display:table  id="result" name="lstResult" class="dataTable" requestURI="openAccountAction!pagingLst.do"  cellpadding="1" cellspacing="1"  >
                    <display:column style="width:5%;" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" headerClass="tct" sortable="true">
<!--                        STT-->
                        ${fn:escapeXml(result_rowNum)}
                    </display:column>
                    <display:column style="width:20%;" title="${fn:escapeXml(code)}" headerClass="tct" sortable="true">
                        <div class="cursorHand" ondblclick="selectCode('<s:property escapeJavaScript="true"  value="#attr.result.code"/>','<s:property escapeJavaScript="true"  value="#attr.result.name"/>')">
                            <s:property escapeJavaScript="true"  value="#attr.result.code"/>
                        </div>
                    </display:column>
                    <display:column style="width:60%;" title="${fn:escapeXml(name)}" headerClass="tct" sortable="true">
                        <div class="cursorHand" ondblclick="selectCode('<s:property escapeJavaScript="true"  value="#attr.result.code"/>','<s:property escapeJavaScript="true"  value="#attr.result.name"/>')">
                            <s:property escapeJavaScript="true"  value="#attr.result.name"/>
                        </div>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.select'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                        <div class="cursorHand" ondblclick="selectCode('<s:property escapeJavaScript="true"  value="#attr.result.code"/>','<s:property escapeJavaScript="true"  value="#attr.result.name"/>')">
                            <img src="${contextPath}/share/img/accept.png" title="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.select'))}" alt="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.select'))}"/>
                        </div>
                    </display:column>
                </display:table>
            </sx:div>
        </tags:imPanel>
    </div>
</s:form>
<script>
    $('autoCompleteSearchBean.code').focus();
    selectCode = function(code,name){
    <s:if test="#request.returnType=='shop'">
        <s:if test="autoCompleteSearchBean.functionName!=null  && autoCompleteSearchBean.functionName!=''" >
                var functionName ='<s:property escapeJavaScript="true"  value="autoCompleteSearchBean.functionName"/>';
                functionName ='window.opener.'+functionName+'("'+code+'","'+name+'")';
                //  alert(functionName);
                eval(functionName);
        </s:if>
        <s:else>
                window.opener.setShopValue(code,name);
        </s:else>

    </s:if>
    <s:else>
        <s:if test="autoCompleteSearchBean.functionName!=null && autoCompleteSearchBean.functionName!=''">
                var functionName ='<s:property escapeJavaScript="true"  value="autoCompleteSearchBean.functionName"/>';
                functionName ='window.opener.'+functionName+'("'+code+'","'+name+'")';
                eval(functionName);
        </s:if>
        <s:else>
                window.opener.setStaffValue(code,name);
        </s:else>

    </s:else>
            window.close();
        }
        search = function(){

            var code=$('autoCompleteSearchBean.code').value;
            var name=$('autoCompleteSearchBean.name').value;

    <s:if test="#request.returnType=='shop'">
            document.getElementById("autoCompleteSearchBean").action="CommonStockAction!popupSearchShop.do?shopCode="+code+"&shopName="+name;
            document.getElementById("autoCompleteSearchBean").submit();
    </s:if>
    <s:else>
        <s:if test="#request.returnType =='Collaborator' " >
            var shopCode='<s:property escapeJavaScript="true"  value="#session.userToken.shopCode"/>';
            var staffOwnerId='<s:property escapeJavaScript="true"  value="#session.userToken.userID"/>';
            document.getElementById("autoCompleteSearchBean").action='CommonStockAction!popupSearchColl.do?staffCode='+code+"&staffName="+name+"&shopCode="+shopCode +"&staffOwnerId="+staffOwnerId;
            document.getElementById("autoCompleteSearchBean").submit();            
        </s:if>
        <s:else>
            <s:if test="#request.returnType =='RevokeColl' " >
            var shopCode='<s:property escapeJavaScript="true"  value="#session.userToken.shopCode"/>';
            var staffOwnerId='<s:property escapeJavaScript="true"  value="#session.userToken.userID"/>';
            document.getElementById("autoCompleteSearchBean").action='CommonStockAction!popupSearchRevokeColl.do?staffCode='+code+"&staffName="+name+"&shopCode="+shopCode +"&staffOwnerId="+staffOwnerId;
            document.getElementById("autoCompleteSearchBean").submit();
            </s:if>
            <s:else>
            document.getElementById("autoCompleteSearchBean").action="CommonStockAction!popupSearchStaff.do?staffCode="+code+"&staffName="+name;
            document.getElementById("autoCompleteSearchBean").submit();
            </s:else>
        </s:else>
    </s:else>
    }
</script>


