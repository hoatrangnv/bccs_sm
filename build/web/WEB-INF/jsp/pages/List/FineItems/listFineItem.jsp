<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listFineItem
    Created on : Sep 14, 2009, 2:52:38 PM
    Author     : TheTM
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
            request.setAttribute("listFineItem", request.getSession().getAttribute("listFineItem"));
%>

<tags:imPanel title="MSG.list.reason.fine">
           <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>

    <div class="divHasBorder">
        <s:form action="finePriceAction" theme="simple" enctype="multipart/form-data"  method="post" id="fineItemForm">
<s:token/>

            <s:hidden name="fineItemForm.telecomServiceId" id="fineItemForm.telecomServiceId" />
            <table class="inputTbl4Col">
                <tr>
                    <td style="width:30%; "><tags:label key="MSG.reason2.name" /></td>
                    <td class="oddColumn" style="width:60%; ">
                        <s:textfield name="fineItemForm.fineItemsName" id="fineItemForm.fineItemsName" maxlength="50" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <tags:submit id="btnSearch"
                                         formId="fineItemForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divDisplayInfo"
                                         value="MSG.generates.find"
                                         preAction="finePriceAction!searchFineItems.do"/>
                        </div>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <tags:submit id="btnAdd"
                                         formId="fineItemForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divDisplayInfo"
                                         value="MSG.generates.addNew"
                                         preAction="finePriceAction!prepareAddFineItems.do"/>
                        </div>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>
    <div id="divlstFineItems">
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.LST.017'))}</legend>
            <s:if test="#request.listFineItem != null && #request.listFineItem.size() > 0">
                <display:table id="tblListFineItem" name="listFineItem"
                               class="dataTable" cellpadding="1" cellspacing="1"
                               targets="divDisplayInfo"
                               pagesize="15"
                               requestURI="${contextPath}/finePriceAction!pageNavigatorFineItem.do">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:10%; text-align:center" headerClass="tct">${fn:escapeXml(tblListFineItem_rowNum)}</display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.reason2.name'))}" sortable="false" style="width:30%" headerClass="tct">
                        <s:property escapeJavaScript="true"  value="#attr.tblListFineItem.fineItemsName"/>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.discription'))}" sortable="false" maxLength="500" style="width:40%" headerClass="tct">
                        <s:property escapeJavaScript="true"  value="#attr.tblListFineItem.description"/>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" style="width:10%" headerClass="tct">
                        <s:if test="#attr.tblListFineItem.status == 1">
                            ${fn:escapeXml(imDef:imGetText('MSG.active'))}
                        </s:if>
                        <s:else>
                            ${fn:escapeXml(imDef:imGetText('MSG.inactive'))}
                        </s:else>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.detail'))}" sortable="false" style="width:10%; text-align:center" headerClass="tct">
                        <s:url action="finePriceAction!displayFineItems" id="URL1">
                            <s:param name="selectedFineItemId" value="#attr.tblListFineItem.fineItemsId"/>
                        </s:url>
                        <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/accept.png" title="${fn:escapeXml(imDef:imGetText('MSG.detail'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.detail'))}"/>
                        </sx:a>
                    </display:column>
                </display:table>
            </s:if>
            <s:else>
                <table class="dataTable">
                    <tr>
                        <th class="tct">STT</th>
                        <th class="tct">Tên lý do phạt</th>
                        <th class="tct">Mô tả</th>
                        <th class="tct">Trạng thái</th>
                        <th class="tct">Chi tiết</th>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </s:else>
        </fieldset>
    </div>



</tags:imPanel>

