<%-- 
    Document   : listDebitDayType
    Created on : Apr 26, 2013, 8:53:07 AM
    Author     : linhnt28
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<sx:div id="displayTagFrame">
    <%
        if (request.getAttribute("lstDebitDayType") == null) {
            request.setAttribute("lstDebitDayType", request.getSession().getAttribute("lstDebitDayType"));
        }
        request.setAttribute("contextPath", request.getContextPath());
    %>
    <fieldset class="imFieldset">
        <legend><s:property value="getText('TITLE.DEBIT.DAY.TYPE.008')"/></legend>
        <s:if test="#session.lstDebitDayType != null && #session.lstDebitDayType.size() > 0">
            <display:table id="tblListDebitDayType" name="lstDebitDayType"
                           pagesize="10" targets="displayTagFrame"
                           requestURI="debitDayTypeAction!pageNavigator.do"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${imDef:imGetText('MSG.GOD.073')}" style="text-align:center" headerClass="tct" sortable="false">
                    ${tblListDebitDayType_rowNum}
                </display:column>
                <display:column title="${imDef:imGetText('MSG.DEBIT.TYPE.002')}" property="debitDayTypeName" headerClass="tct" sortable="false"/>
                <display:column title="${imDef:imGetText('TITLE.DEBIT.DAY.TYPE.006')}" headerClass="tct" sortable="false">
                    <s:if test="#attr.tblListDebitDayType.status == 1">
                        <s:property value="getText('MSG.active')"/>
                        <!--                        Ngày thường -->
                    </s:if>
                    <s:if test="#attr.tblListDebitDayType.status == 0">
                        <s:property value="getText('MSG.inactive')"/>
                        <!--                        Ngày khuyên mại -->
                    </s:if>
                </display:column>
                <display:column title="${imDef:imGetText('lbl_ten_dot_km')}" property="ddtName" headerClass="tct" style="text-align:center; "/>
                <display:column title="${imDef:imGetText('TITLE.DEBIT.DAY.TYPE.009')}" format="{0,date,dd/MM/yyyy}" property="createDate" headerClass="tct" style="width:100px; text-align:center; "/>
                <display:column title="${imDef:imGetText('TITLE.DEBIT.DAY.TYPE.010')}" format="{0,date,dd/MM/yyyy}" property="staDate" headerClass="tct" style="width:100px; text-align:center; "/>
                <display:column title="${imDef:imGetText('TITLE.DEBIT.DAY.TYPE.011')}" format="{0,date,dd/MM/yyyy}" property="endDate" headerClass="tct" style="width:100px; text-align:center; "/>
                <display:column title="${imDef:imGetText('lbl.tai_file')}" sortable="false" headerClass="tct" style="width:50px;text-align:left;padding-left:5px">
                    <s:if test ="#attr.tblListDebitDayType.fileName != null">
                        <div align="center">
                            <a style="cursor:pointer" onclick="downloads('<s:property value="#attr.tblListDebitDayType.Id"/>')">
                                <img height="16px" width="16px" src="${contextPath}/share/img/download_icon.gif" title='<s:property value="getText('lbl.tai_file')"/>'/>                                               
                            </a>                                                                                               
                        </div>
                    </s:if>
                </display:column>
                <display:column title="${imDef:imGetText('MSG.generates.edit')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <s:url action="debitDayTypeAction!prepareEditDebitDayType" id="URL1">
                        <s:param name="debitDayTypeId" value="#attr.tblListDebitDayType.Id"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property value="getText('MSG.generates.edit')"/>" alt="<s:property value="getText('MSG.generates.edit')"/>"/>
                    </sx:a>
                </display:column>

                <display:column title="${imDef:imGetText('MSG.delete')}" sortable="false" headerClass="tct" style="text-align:center; width:50px;">
                    <s:if test="#attr.tblListDebitDayType.status == 1">
                        <sx:a onclick="delDebitDayType('%{#attr.tblListDebitDayType.Id}')" >
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property value="getText('MSG.delete')"/>" alt="<s:property value="getText('MSG.delete')"/>"/>
                        </sx:a>
                    </s:if>
                </display:column>

                <display:footer> <tr> <td colspan="9" style="color:green"><s:property value="getText('MSG.totalRecord')"/>
                            <s:property escapeJavaScript="true"  value="%{#session.lstDebitDayType.size()}" /></td> <tr> </display:footer>
                    </display:table>
                </s:if>
                <s:else>
            <font color='red'>
                <s:property value="getText('MSG.blank.item')"/>
            </font>
        </s:else>
    </fieldset>
</sx:div>
