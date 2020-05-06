<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : assignDslamAreaListArea
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
            if (request.getAttribute("listArea") == null) {
                request.setAttribute("listArea", request.getSession().getAttribute("listArea"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="assignDslamAreaAction" theme="simple" method="post" id="addDslamAreaForm">

    <tags:imPanel title="MSG.area.info">
        <s:hidden id="addDslamAreaForm.dslamId" name="addDslamAreaForm.dslamId" />
        <div align="left" >
            <table class="inputTbl2Col">
                <tr>
                    <td style="width:10%; " class="label"><tags:label key="MSG.generates.province" required="true"/> </td>
                    <td style="width:30%; " class="text" colspan="1">
                        <tags:imSearch codeVariable="addDslamAreaForm.provinceCode" nameVariable="addDslamAreaForm.provinceName"
                                       codeLabel="MSG.province.code" nameLabel="MSG.province.name"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListProvince"
                                       getNameMethod="getListProvinceName" readOnly="true"
                                       otherParam="addDslamAreaForm.dslamId"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%; " class="label"><tags:label key="MSG.district" required="true"/> </td>
                    <td style="width:30%; " class="text" colspan="1">
                        <tags:imSearch codeVariable="addDslamAreaForm.districtCode" nameVariable="addDslamAreaForm.districtName"
                                       codeLabel="MSG.district.code" nameLabel="MSG.district.name"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListDistrict"
                                       getNameMethod="getListDistrictName"
                                       otherParam="addDslamAreaForm.provinceCode"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>
                    <td style="width:10%; " class="label"><tags:label key="MSG.village" required="true"/> </td>
                    <td style="width:30%; " class="text" colspan="1">
                        <tags:imSearch codeVariable="addDslamAreaForm.precinctCode" nameVariable="addDslamAreaForm.precinctName"
                                       codeLabel="MSG.village.code" nameLabel="MSG.village.name"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListPrecinct"
                                       getNameMethod="getListPrecinctName"
                                       otherParam="addDslamAreaForm.provinceCode;addDslamAreaForm.districtCode"
                                       roleList=""/>
                    </td>
                </tr>
                <tr>

                    <%--
                    <td style="width:10%; " class="label">Mã khu vực</td>
                    <td style="width:30%; " class="text" colspan="2">
                        <tags:imSearch codeVariable="addDslamAreaForm.areaCode" nameVariable="addDslamAreaForm.areaName"
                                       codeLabel="Mã khu vực" nameLabel="Tên khu vực"
                                       searchClassName="com.viettel.im.database.DAO.AssignDslamAreaDAO"
                                       searchMethodName="getListArea"
                                       getNameMethod="getListAreaName"
                                       otherParam="addDslamAreaForm.dslamId"
                                       roleList=""/>
                    </td>                    --%>


                    <td class="label" align="center" colspan="4">
                        <div align="center">
                            <s:if test="addDslamAreaForm.dslamId != null && addDslamAreaForm.dslamId * 1 > 0 ">
                                <tags:submit targets="divDisplayInfo" formId="addDslamAreaForm"
                                             confirm="true" confirmText="MSG.add.area.mn.confirm"
                                             showLoadingText="true" cssStyle="width:80px;"
                                             value="MSG.add" preAction="assignDslamAreaAction!addDslamArea.do"
                                             validateFunction="checkValidate()"/>
                            </s:if>
                            <s:else>
<!--                                <input type="button" disabled value="<s:property escapeJavaScript="true"  value="getError('MSG.add')"/>" style="width:80px;"/>-->
                                <input type="button" disabled value="<s:text name="MSG.add"/>" style="width:80px;"/>
                            </s:else>
                        </div>
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

    <tags:imPanel title="MSG.area.mn.list">
        <sx:div id="displayTagFrame">
            <display:table id="tbllistArea"  name="listArea" targets="displayTagFrame" class="dataTable"  cellpadding="1" cellspacing="1" requestURI="dslamAction!pageNavigator.do" pagesize="25">
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                    ${fn:escapeXml(tbllistArea_rowNum)}
                </display:column>

                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.area.code'))}" property="areaCode" sortable="false" headerClass="tct" style="width:100px; text-align:center"/>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.area.name'))}" property="name" sortable="false" headerClass="tct" style="width:150px; text-align:center"/>
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.fullname'))}" property="fullName" sortable="false" headerClass="tct" />

                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.delete'))}" sortable="false" style="width:80px; text-align:center" headerClass="sortable">
                    <s:bean name="com.viettel.im.common.util.QueryCryptSessionBean" var="vDelDsAlarmId" >
                        <s:token/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                        <s:param name="queryName">areaCode</s:param>
                        <s:param name="httpRequestWeb" value="request" />
                        <s:param name="parameterId" value="#attr.tbllistArea.areaCode" />
                    </s:bean>  
                    <a id="deleDlsId_<s:property escapeJavaScript="true"  value="#attr.tbllistArea.areaCode"/>" onclick="deleteDslamArea('<s:property escapeJavaScript="true"  value="#vDelDsAlarmId.printParamCrypt()" />')">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />" alt="<s:property escapeJavaScript="true"  value="getError('MSG.generates.delete')" />"/>
                    </a> 
                </display:column>
            </display:table>
        </sx:div>
    </tags:imPanel>
</s:form>
