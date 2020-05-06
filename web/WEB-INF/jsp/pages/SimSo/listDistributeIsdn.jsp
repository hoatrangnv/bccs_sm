<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listDistributeIsdn
    Created on : May 13, 2009, 4:41:48 PM
    Author     : tamdt1
--%>

<%--
    danh sach cac dai isdn can phan phoi vao kho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("listIsdnRange", request.getSession().getAttribute("listIsdnRange" + pageId));
%>

<!-- hien thi link dowload chi tiet so isdn -->
<s:if test="#request.detailIsdnRangePath != null">
    <a href="${contextPath}/${fn:escapeXml(detailIsdnRangePath)}">
        <tags:displayResult id="detailIsdnRangeMessage" property="detailIsdnRangeMessage" propertyValue="detailIsdnRangeMessageParam" type="key"/>
    </a>
</s:if>

<sx:div id="divListIsdnRange">
    <fieldset class="imFieldset">
        <legend>
            ${fn:escapeXml(imDef:imGetText('MSG.ISN.007'))}
        </legend>
        <div style="height:450px; overflow:auto; ">
            <s:if test="#request.listIsdnRange != null">
                <table cellspacing="1" cellpadding="1" class="dataTable" id="tblListIsdnRange">
                    <thead>
                        <tr>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.fromIsdn'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.toIsdn'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.quantity'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.SAE.016'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.isdnStoreId'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.storeName'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.isdnStatus'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.isdn_type'))}</th>
                            <th class="tct"><input type="checkbox" onclick="checkAllCheckbox()" id="cbCheckAll"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="#request.listIsdnRange" id="tblListIsdnRange" status="rowStatus">
                            <tr class="<s:if test="#rowStatus.odd == true ">odd</s:if><s:else>even</s:else>">
                                <td style="width: 50px; text-align: center;" >
                                    <s:property escapeJavaScript="true"  value="#attr.rowStatus.index + 1"/>
                                </td>
                                <td><s:property escapeJavaScript="true"  value="#attr.tblListIsdnRange.startIsdn"/></td>
                                <td><s:property escapeJavaScript="true"  value="#attr.tblListIsdnRange.endIsdn"/></td>
                                <td><s:property escapeJavaScript="true"  value="#attr.tblListIsdnRange.countIsdn"/></td>
                                <td>
                                    <s:if test="#attr.tblListIsdnRange.serviceType == 1">${fn:escapeXml(imDef:imGetText('MSG.mobileNumber'))}</s:if>
                                    <s:elseif test="#attr.tblListIsdnRange.serviceType == 2">${fn:escapeXml(imDef:imGetText('MSG.homephoneNumber'))}</s:elseif>
                                    <s:elseif test="#attr.tblListIsdnRange.serviceType == 3">${fn:escapeXml(imDef:imGetText('MSG.pstnNumber'))}</s:elseif>
                                    <s:else>${fn:escapeXml(imDef:imGetText('MSG.ISN.008'))}</s:else>
                                </td>
                                <td><s:property escapeJavaScript="true"  value="#attr.tblListIsdnRange.fromShopCode"/></td>
                                <td><s:property escapeJavaScript="true"  value="#attr.tblListIsdnRange.fromShopName"/></td>
                                <td><s:property escapeJavaScript="true"  value="#attr.tblListIsdnRange.stockModelCode"/></td>
                                <td><s:property escapeJavaScript="true"  value="#attr.tblListIsdnRange.stockModelName"/></td>
                                <td>
                                    <s:if test="#attr.tblListIsdnRange.status == 1">${fn:escapeXml(imDef:imGetText('MSG.newIsdn'))}</s:if>
                                    <s:elseif test="#attr.tblListIsdnRange.status == 3">${fn:escapeXml(imDef:imGetText('MSG.stopIsdn'))}</s:elseif>
                                    <s:else>${fn:escapeXml(imDef:imGetText('MSG.ISN.008'))}</s:else>
                                </td>
                                <td>
                                    <s:if test="#attr.tblListIsdnRange.isdnType == 1">${fn:escapeXml(imDef:imGetText('MSG.ISN.005'))}</s:if>
                                    <s:elseif test="#attr.tblListIsdnRange.isdnType == 2">${fn:escapeXml(imDef:imGetText('MSG.ISN.004'))}</s:elseif>
                                    <s:elseif test="#attr.tblListIsdnRange.isdnType == 3">${fn:escapeXml(imDef:imGetText('Số đặc biệt'))}</s:elseif>
                                    <s:else>${fn:escapeXml(imDef:imGetText('MSG.ISN.008'))}</s:else>
                                </td>
                                <td>
                                    <s:checkbox name="distributeIsdnForm.selectedFormId"
                                                id="arrSelectedFormId_%{#attr.tblListIsdnRange.formId}"
                                                onclick="cbOnclick();"
                                                fieldValue="%{#attr.tblListIsdnRange.formId}"
                                                theme="simple"/>
                                </td>
                            </tr>
                        </s:iterator>

                    </tbody>
                </table>
            </s:if>
            <s:else>
                <table cellspacing="1" cellpadding="1" class="dataTable" id="tblListIsdnRange">
                    <thead>
                        <tr>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.fromIsdn'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.toIsdn'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.quantity'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.SAE.016'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.isdnStoreId'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.storeName'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.isdnStatus'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.isdn_type'))}</th>
                            <th class="tct"><input type="checkbox" onclick="checkAllCheckbox()" id="cbCheckAll"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="empty">
                            <td colspan="12">${fn:escapeXml(imDef:imGetText('MSG.ISN.009'))}.</td>
                        </tr>
                    </tbody>
                </table>
            </s:else>
        </div>
    </fieldset>
</sx:div>

<script language="javascript">
    //
    checkAllCheckbox = function(){
        selectAll("cbCheckAll", "distributeIsdnForm.selectedFormId", "arrSelectedFormId_");
    }
    //
    cbOnclick = function(){
        checkSelectAll("cbCheckAll", "distributeIsdnForm.selectedFormId", "arrSelectedFormId_");
    }
    //
    autoDownloadFile = function(filePath) {
        window.open(filePath,'','toolbar=yes,scrollbars=yes');
    }
</script>
