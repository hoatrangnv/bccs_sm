<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : assignShopDslamListDslam
    Created on : May 14, 2010, 12:00:40 AM
    Author     : TrongLV
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            if (request.getAttribute("listDslam") == null) {
                request.setAttribute("listDslam", request.getSession().getAttribute("listDslam"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="assignShopDslamAction" theme="simple" method="post" id="addShopDslamForm">
<s:token/>

    <tags:imPanel title="MSG.dslam_dlu.info">
        <s:hidden id="addShopDslamForm.shopId" name="addShopDslamForm.shopId" />
        <div align="left" >
            <table class="inputTbl4Col">
                <tr>
                    <td style="width:10%; " class="label">
                        <tags:label key="MSG.dslam.code" />
                    </td>
                    <td style="width:30%; " class="text">
                        <tags:imSearch codeVariable="addShopDslamForm.dslamCode" nameVariable="addShopDslamForm.dslamName"
                                       codeLabel="MSG.dslam.code" nameLabel="MSG.dslam.name"
                                       searchClassName="com.viettel.im.database.DAO.AssignShopDslamDAO"
                                       searchMethodName="getListDslam"
                                       getNameMethod="getListDslamName"
                                       otherParam="addShopDslamForm.shopId"
                                       roleList=""/>
                    </td>
                    <td class="label" align="right">
                        <tags:submit targets="divDisplayInfo" formId="addShopDslamForm"
                                     confirm="true" confirmText="MSG.add.dslam.to.shopid.mn.confirm"
                                     showLoadingText="true" cssStyle="width:80px;"
                                     value="MSG.add" preAction="assignShopDslamAction!addShopDslam.do"
                                     validateFunction="checkValidate()"
                                     />
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
                    </td>
                </tr>
            </table>
        </div>
    </tags:imPanel>

    <tags:imPanel title="MSG.dslam_du.mn.list">

        <sx:div id="displayTagFrame">

            <display:table id="tbllistDslam"  name="listDslam" targets="divDisplayInfo" class="dataTable"  cellpadding="1" cellspacing="1" requestURI="assignShopDslamAction!pageNavigator.do" pagesize="25" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                    ${fn:escapeXml(tbllistDslam_rowNum)}
                </display:column>

                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.dslam.code'))}" property="code" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.area.name'))}" property="name" sortable="false" headerClass="tct" style="width:150px; text-align:left"/>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.address'))}" property="address" sortable="false" headerClass="tct" />
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct" style="width:100px; text-align:left">
                    <s:if test="#attr.tbllistDslam.status == 1"><tags:label key="MSG.active" /></s:if>
                    <s:elseif test="#attr.tbllistDslam.status == 0"><tags:label key="MSG.inactive" /></s:elseif>
                    <s:else></s:else>
                </display:column>

                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" style="width:80px; text-align:center" headerClass="sortable">
                    <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vDelSuDsAlarmId" >                        
                        <s:param name="queryName">dslamId</s:param>
                        <s:param name="httpRequestWeb" value="request" />
                        <s:param name="parameterId" value="#attr.tbllistDslam.dslamId" />
                    </s:bean>  
                    <a id="deleDlsId_<s:property escapeJavaScript="true"  value="#attr.tbllistDslam.dslamId"/>" onclick="deleteShopDslam('<s:property escapeJavaScript="true"  value="#vDelSuDsAlarmId.printParamCrypt()" />')">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError(MSG.generates.delete)" />" alt="<s:property escapeJavaScript="true"  value="getError(MSG.generates.delete)" />"/>
                    </a>                     
                </display:column>
            </display:table>
        </sx:div>

    </tags:imPanel>

</s:form>
