<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listStaffs
    Created on : Apr 20, 2009, 8:31:38 AM
    Author     : Doan Thanh 8
    Modified by NamNX 20/11/2009 : bo sung chuc nang copy
--%>

<%--
    Notes   : danh sach cac staff
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
    request.setAttribute("listStaffs", request.getSession().getAttribute("listStaffs"));
%>


<table class="inputTbl">
    <tr>
        <td>
            <fieldset style="width:100%">
                <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.038'))}</legend>
                <%--<s:form action="channelAction" theme="simple" method="post" id="listSearchForm">
<s:token/>
                --%>
                <div>
                    <tags:displayResult id="listStaffMessage" property="listStaffMessage" type="key"/>
                </div>
                <table class="inputTbl6Col">
                    <tr>
                        <%--<td class="label"  style="width:4%">Mã NV</td>--%>
                        <td class="label" style="width:4%"><tags:label key="MES.CHL.078" /></td>
                        <td class="text" style="width:8%">
                            <s:textfield theme="simple" name="shopForm.staffCodeSearch" id="staffCodeSearch" maxlength="50" cssClass="txtInputFull"/>
                        </td>
                        <td>
                            <tags:submit  id="searchButton" confirm="false" formId="shopForm"
                                          showLoadingText="true" targets="divListStaff" value="MES.CHL.086" preAction="channelAction!searchListStaff.do"/>
                        </td>
                        <td>
                        </td>
                    </tr>
                </table>
                <%--     </s:form>--%>
            </fieldset>
        </td>
    </tr>
    <tr>
        <td>

            <fieldset style="width:100%">
                <legend>${fn:escapeXml(imDef:imGetText('MES.CHL.104'))}</legend>                
                <display:table id="tbllistStaffs" name="listStaffs"
                               class="dataTable" cellpadding="1" cellspacing="1"
                               pagesize="5" requestURI="${contextPath}/channelAction!pageNavigatorStaff.do"
                               targets="divListStaff">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.054'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                        ${fn:escapeXml(tbllistStaffs_rowNum)}
                    </display:column>
                    <display:column escapeXml="true"  property="staffCode" title="${fn:escapeXml(imDef:imGetText('MES.CHL.105'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true"  property="name" title="${fn:escapeXml(imDef:imGetText('MES.CHL.106'))}" sortable="false" headerClass="tct"/>
                    <display:column property="birthday" title="${fn:escapeXml(imDef:imGetText('MES.CHL.082'))}" sortable="false" format="{0,date,dd/MM/yyyy}" headerClass="tct"/>
                    <%--display:column escapeXml="true"  property="address" title="${fn:escapeXml(imDef:imGetText('MES.CHL.070'))}" sortable="false" headerClass="tct"/--%>

                    <display:column escapeXml="true"  property="tel" title="${fn:escapeXml(imDef:imGetText('MES.CHL.095'))}" sortable="false" headerClass="tct"/>
                    <%--display:column escapeXml="true"  property="typeName" title="${fn:escapeXml(imDef:imGetText('MSG.staff.type'))}" sortable="false" headerClass="tct"/--%>
                    <%--  <display:column escapeXml="true"  property= "maxDebitDisplay" title="${fn:escapeXml(imDef:imGetText('label.maxDebit'))}"  sortable="false" headerClass="tct" style="text-align:right"   format="{0,number,#,###.00}"/> --%>
                    <display:column escapeXml="true"  property= "limitMoney" title="${fn:escapeXml(imDef:imGetText('label.limitMoney'))}"  sortable="false" headerClass="tct" style="text-align:right"   format="{0,number,#,###.00}"/>
                    <display:column escapeXml="true"  property= "limitDay" title="${fn:escapeXml(imDef:imGetText('label.limitDay'))}"  sortable="false" headerClass="tct" style="text-align:right"   format="{0,number,#,###.00}"/>
                    <display:column escapeXml="true"  property= "referenceId" title="Reference Id"  sortable="false" headerClass="tct" style="text-align:right"   format="{0,number,#,###.00}"/>
                    <display:column escapeXml="true" property="currentDebitDisplay" title="${fn:escapeXml(imDef:imGetText('L.200056'))}" sortable="false" headerClass="tct" style="text-align:right"  format="{0,number,#,###.00}"/>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct" style="width:100px;text-align:center">   
                        <s:if test="#attr.tbllistStaffs.status == 1">
                            <s:text name="MSG.GOD.297"/>
                        </s:if>
                        <s:else>
                            <s:if test="#attr.coll.status == 0">
                                <s:text name="MSG.GOD.274"/></s:if>
                            <s:else>
                                <s:text name="MSG.lock"/>
                            </s:else>
                        </s:else> 
                    </display:column>
                    <s:if test="#attr.tbllistStaffs.isChecked == 1">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MES.Title.Limit'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px">
                            <s:a href="" onclick="prepareEditLimitStaff('%{#attr.tbllistStaffs.staffId}')">
                                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError(MES.CHL.109)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MES.CHL.062)"/>"/>
                            </s:a>
                        </display:column>
                    </s:if>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.107'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;" >
                        <sx:a onclick="prepareCopyStaff('%{#attr.tbllistStaffs.staffId}')">
                            <%--<img src="${contextPath}/share/img/clone.jpg" title="Sao chép thông tin nhân viên" alt="Sao chép"/>--%>
                            <img src="${contextPath}/share/img/clone.jpg" title="<s:property escapeJavaScript="true"  value="getError(MES.CHL.108)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MES.CHL.107)"/>"/>
                        </sx:a>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.062'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px">
                        <s:a href="" onclick="prepareEditStaff('%{#attr.tbllistStaffs.staffId}')">
                            <%--<img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa thông tin nhân viên" alt="Sửa"/>--%>
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError(MES.CHL.109)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MES.CHL.062)"/>"/>
                        </s:a>
                    </display:column>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.138'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px">
                        <s:a href="" onclick="prepareInforStaff('%{#attr.tbllistStaffs.staffCode}')">
                            <%--<img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa thông tin nhân viên" alt="Sửa"/>--%>
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError(MES.CHL.138)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MES.CHL.138)"/>"/>
                        </s:a>
                    </display:column>
                    <%--<display:column title="Xoá" sortable="false" headerClass="tct" style="text-align:center; width:50px">
                        <s:a onclick="deleteStaff('%{#attr.tbllistStaffs.staffId}')">
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa thông tin nhân viên" alt="Xóa"/>
                        </s:a>
                    </display:column>--%>
                    <%-- loint9
                    <display:column title="${fn:escapeXml(imDef:imGetText('MES.CHL.110'))}" sortable="false" headerClass="tct" style="text-align:center; width:50px">
                        <s:a href="" onclick="prepareAddCollaborator('%{#attr.tbllistStaffs.staffId}')">
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="Danh sách CTV/ Điểm bán thuộc nhân viên quản lý" alt="Danh sách CTV/ Điểm bán"/>
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getError(MES.CHL.111)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MES.CHL.110)"/>"/>
                        </s:a>
                    </display:column --%>
                </display:table>

                <script language="javascript">
                    textFieldNF($('maxDebit'));
                    prepareInforStaff = function(staffCode) {
                        openDialog("${contextPath}/channelAction!prepareInforStaff.do?selectedStaffId=" + staffCode + '&' + token.getTokenParamString(), true, true, true);
                    }


                    //bat popup sua thong tin nhan vien
                    prepareEditStaff = function(staffId) {
                        openDialog("${contextPath}/channelAction!prepareEditStaff.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString(), 1100, 650, true);
                    }
                    prepareEditLimitStaff = function(staffId) {
                        openDialog("${contextPath}/channelAction!prepareEditLimitStaff.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString(), 1100, 650, true);
                    }
                    //sao chep thong tin ve nhan vien
                    prepareCopyStaff = function(staffId) {
                        openDialog("${contextPath}/channelAction!prepareCopyStaff.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString(), 1100, 650, true);
                    }

                    //xoa thong tin ve nhan vien
                    deleteStaff = function(staffId) {
                        if (confirm('Bạn có chắc chắn muốn xóa nhân viên này không?')) {
                            gotoAction('divListStaff', "${contextPath}/channelAction!deleteStaff.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString());
                        }
                    }

                    //bat popup hien thi danh sach CTV/ Diem ban
                    prepareAddCollaborator = function(staffId) {
                        openDialog("${contextPath}/channelAction!prepareAddCollaborator.do?selectedStaffId=" + staffId + '&' + token.getTokenParamString(), true, true, true);
                    }

                </script>
            </fieldset>
        </td>
    </tr>

</table>



