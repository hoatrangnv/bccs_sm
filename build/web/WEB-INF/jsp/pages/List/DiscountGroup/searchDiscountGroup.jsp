<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : searchDiscountGroup
    Created on : May 13, 2010, 8:39:11 AM
    Author     : Doan Thanh 8
    Note       : tim kiem thong tin nhom CK
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
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listDiscountGroups", request.getSession().getAttribute("listDiscountGroups" + pageId));
%>

<tags:imPanel title="MSG.GOD.283">
    <div>
        <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
    </div>

    <div class="divHasBorder">
        <s:form action="discountGroupAction!searchDiscountGroup.do" theme="simple" method="post" id="discountGroupForm">
<s:token/>

            <s:hidden name="discountGroupForm.discountGroupId" id="discountGroupForm.discountGroupId"/>

            <table class="inputTbl6Col">
                <tr>
                    <td style="width:20%; "><tags:label key="MSG.group.discount.name"/></td>
                    <td class="oddColumn" style="width:30%; ">
                        <s:textfield name="discountGroupForm.name" id="name" maxlength="50" cssClass="txtInputFull"/>
                    </td>
                    <td style="width:15%;"><tags:label key="MSG.GOD.313" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn"  style="width:20%;">
                        <tags:imSelect name="discountGroupForm.discountPolicy"
                                       id="discountGroupForm.discountPolicy"
                                       cssClass="txtInputFull"
                                       list="listDiscountPolicy"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.317"/>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.discount.goods"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="discountGroupForm.stockModelCode" nameVariable="discountGroupForm.stockModelName"
                                       codeLabel="MSG.GOD.007" nameLabel="MSG.GOD.008"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListStockModel"
                                       getNameMethod="getStockModelName"/>

                    </td>
                    <td><tags:label key="MSG.GOD.013" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="discountGroupForm.status"
                                       id="discountGroupForm.status"
                                       cssClass="txtInputFull"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <tags:submit id="btnSearch"
                                         formId="discountGroupForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divDisplayInfo"
                                         value="MSG.GOD.009"
                                         preAction="discountGroupAction!searchDiscountGroup.do"/>


                        </div>
                    </td>
                    <td style="width:85px; ">
                        <div align="right">
                            <tags:submit id="btnSearch"
                                         formId="discountGroupForm"
                                         showLoadingText="true"
                                         cssStyle="width:80px;"
                                         targets="divDisplayInfo"
                                         value="MSG.GOD.010"
                                         preAction="discountGroupAction!prepareAddDiscountGroupFirstTime.do"/>
                        </div>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <div id="divDiscountGroup">
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.315'))}</legend>
            <display:table id="tblListDiscountGroups" name="listDiscountGroup"
                           requestURI="${contextPath}/discountGroupAction!searchDiscountGroup.do?discountGroupForm.name=${fn:escapeXml(requestScope.discountGroupForm.name)}&stockModelForm.stockModelCode=${fn:escapeXml(requestScope.stockModelForm.stockModelCode)}&discountGroupForm.discountPolicy=${fn:escapeXml(requestScope.discountGroupForm.discountPolicy)}&discountGroupForm.status=${fn:escapeXml(requestScope.discountGroupForm.status)}"
                           pagesize="20"
                           targets="divDisplayInfo"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListDiscountGroups_rowNum)}</display:column>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.group.discount.name'))}" sortable="false" headerClass="tct">
                    <s:property escapeJavaScript="true"  value="#attr.tblListDiscountGroups.name"/>
                </display:column>
                <display:column escapeXml="true"  property="discountPolicyName" title=" ${fn:escapeXml(imDef:imGetText('MSG.discount.policy'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="telecomServiceName" title=" ${fn:escapeXml(imDef:imGetText('MSG.telecom.service'))}" sortable="false" headerClass="tct"/>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListDiscountGroups.status.equals(1L)">
                        ${fn:escapeXml(imDef:imGetText('MSG.active'))}
                    </s:if>
                    <s:else>
                        ${fn:escapeXml(imDef:imGetText('MSG.GOD.284'))}
                    </s:else>
                </display:column>
                <display:column escapeXml="true"  property="notes" title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.020'))}" sortable="false" headerClass="tct"/>
                <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.146'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    <s:url action="discountGroupAction!displayDiscountGroup" id="URL1">
                        <s:param name="selectedDiscountGroupId" value="#attr.tblListDiscountGroups.discountGroupId"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getError(MSG.SAE.146)"/>" alt="<s:property escapeJavaScript="true"  value="getError(MSG.SAE.146)"/>"/>
                    </sx:a>
                </display:column>
            </display:table>
        </fieldset>
    </div>

</tags:imPanel>
