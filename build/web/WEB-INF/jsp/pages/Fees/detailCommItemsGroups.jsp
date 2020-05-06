<%-- 
    Document   : detailCommItemsGroups
    Created on : Mar 27, 2009, 9:58:38 AM
    Author     : DatPV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.commItemGroupsMode == 'prepareAddOrEdit'">
    <s:set var="readonly" value="false" scope="request"/>
</s:if>
<s:else>
    <s:set var="readonly" value="true" scope="request"/>
</s:else>

<div style="height:600px;">
    <tags:imPanel title="Thông tin nhóm khoản mục">    
        <div>
            <tags:displayResult id="message" property="message" type="key"/>
        </div>

        <!-- phan thong tin ve nhom khoan muc -->
        <s:form action="commItemGroupsAction" id="commItemGroupsForm" method="POST" theme="simple">
<s:token/>

            <s:hidden name="commItemGroupsForm.itemGroupId" id="commItemGroupsForm.itemGroupId"/>

            <table class="inputTbl4Col">
                <tr>
                    <td class="label">Nhóm khoản mục cha</td>
                    <td class="text">
                        <s:select name="commItemGroupsForm.parentItemGroupId" id="parentItemGroupId"
                                  disabled="%{#request.readonly}"
                                  cssClass="txtInputFull"
                                  list="#request.listParentItemGroups != null ? #request.listParentItemGroups : #{}"
                                  headerKey="0" headerValue="--Chọn nhóm khoản mục cha--"
                                  listKey="itemGroupId" listValue="groupName"/>
                    </td>
                    <td class="label">Tên nhóm khoản mục <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                    <td class="text">
                        <s:textfield name="commItemGroupsForm.groupName" id="groupName" maxlength="80" readonly="%{#request.readonly}" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">Loại báo cáo <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                    <td class="text">
                        <s:select name="commItemGroupsForm.reportType" id="reportType"
                                  disabled="%{#request.readonly}"
                                  cssClass="txtInputFull"
                                  list="#{'1':'Bảng tính phí bán hàng','2':'Bảng tính khoản giảm trừ','3':'Group mục 1 có cả 2 loại trên'}"
                                  headerKey="" headerValue="--Chọn loại báo cáo--"/>
                    </td>
                    <td class="label">Trạng thái <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                    <td class="text">
                        <s:select name="commItemGroupsForm.status" id="status"
                                  disabled="%{#request.readonly}"
                                  cssClass="txtInputFull"
                                  list="#{'1':'Hiệu lực','2':'Không hiệu lực'}"
                                  headerKey="" headerValue="--Chọn trạng thái--"/>

                    </td>
                </tr>
            </table>
            <br/>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.readonly == false">
            <div align="center" style="width:100%; padding-bottom:5px;">
                <s:if test="#request.toEditCommItemGroups == 'toEdit'">
                    <sx:submit parseContent="true" executeScripts="true"
                               targets="divDisplayInfo"
                               formId="commItemGroupsForm"
                               value="Cập nhật"
                               cssStyle="width:80px; "
                               beforeNotifyTopics="commItemGroupsAction/editCommItemGroups"
                               errorNotifyTopics="errorAction" />
                </s:if>
                <s:else>
                    <sx:submit parseContent="true" executeScripts="true"
                               targets="divDisplayInfo"
                               formId="commItemGroupsForm"
                               value="Thêm mới"
                               cssStyle="width:80px; "
                               beforeNotifyTopics="commItemGroupsAction/addCommItemGroups"
                               errorNotifyTopics="errorAction" />
                </s:else>
                <sx:submit parseContent="true" executeScripts="true"
                           targets="divDisplayInfo"
                           formId="commItemGroupsForm"
                           value="Bỏ qua"
                           cssStyle="width:80px; "
                           beforeNotifyTopics="commItemGroupsAction/displayCommItemGroups"
                           errorNotifyTopics="errorAction" />
            </div>

            <script language="javascript">

                //lang nghe xu ly su kien click cua nut "Dong y"
                dojo.event.topic.subscribe("commItemGroupsAction/addCommItemGroups", function(event, widget){

                    if(checkRequiredFields() && checkValidFields()) {
                        if(confirm("Bạn có thực sự muốn thêm mới nhóm khoản mục?")){
                            widget.href = "commItemGroupsAction!addOrEditCommItemGroups.do?"+token.getTokenParamString();
                        } else {
                            event.cancel = true;
                        }
                    }else {
                        event.cancel = true;
                    }

                });
               dojo.event.topic.subscribe("commItemGroupsAction/editCommItemGroups", function(event, widget){

                    if(checkRequiredFields() && checkValidFields()) {
                        if(confirm("Bạn có thực sự muốn sửa nhóm khoản mục?")){
                            widget.href = "commItemGroupsAction!addOrEditCommItemGroups.do?"+token.getTokenParamString();
                        } else {
                            event.cancel = true;
                        }
                    }else {
                        event.cancel = true;
                    }

                });

                //lang nghe xu ly su kien click cua nut "Bo qua"
                dojo.event.topic.subscribe("commItemGroupsAction/displayCommItemGroups", function(event, widget){
                    widget.href = "commItemGroupsAction!displayCommItemGroups.do";
                });

                //ham kiem tra cac truong bat buoc
                checkRequiredFields = function() {
                    //truong ten nhom khoan muc
                    if(trim($('groupName').value) == "") {
                        $('message').innerHTML = "!!!Lỗi. Tên nhóm khoản mục không được để trống";
                        $('groupName').select();
                        $('groupName').focus();
                        return false;
                    }
                    //truong loai bao cao
                    if(trim($('reportType').value) == "") {
                        $('message').innerHTML = "!!!Lỗi. Chọn loại báo cáo";
                        $('reportType').focus();
                        return false;
                    }
                    //truong trang thai
                    if(trim($('status').value) == "") {
                        $('message').innerHTML = "!!!Lỗi. Chọn trạng thái";
                        $('status').focus();
                        return false;
                    }

                    return true;
                }

                //kiem tra ma khong duoc chua cac ky tu dac biet
                checkValidFields = function() {
                    if(isHtmlTagFormat($('groupName').value)) {
                        $('message').innerHTML = "!!!Lỗi. Tên nhóm khoản mục không được chứa các thẻ HTML";
                        $('groupName').select();
                        $('groupName').focus();
                        return false;
                    }

                    return true;
                }

            </script>

        </s:if>
        <s:else>
            <div align="center" style="width:100%; padding-bottom:5px;">
                <sx:submit parseContent="true" executeScripts="true"
                           targets="divDisplayInfo"
                           formId="commItemGroupsForm"
                           value="Thêm"
                           cssStyle="width:80px; "
                           beforeNotifyTopics="commItemGroupsAction/prepareAddCommItemGroups"
                           errorNotifyTopics="errorAction" href="" />
                <sx:submit parseContent="true" executeScripts="true"
                           targets="divDisplayInfo"
                           formId="commItemGroupsForm"
                           value="Sửa"
                           cssStyle="width:80px; "
                           beforeNotifyTopics="commItemGroupsAction/prepareEditCommItemGroups"
                           errorNotifyTopics="errorAction" />
                <sx:submit parseContent="true" executeScripts="true"
                           targets="divDisplayInfo"
                           formId="commItemGroupsForm"
                           value="Xóa"
                           cssStyle="width:80px; "
                           beforeNotifyTopics="commItemGroupsAction/deleteCommItemGroups"
                           errorNotifyTopics="errorAction" />
                <input type="button" value="Thêm KM" style="width:80px;" onclick="prepareAddCommItems()">
            </div>

            <script language="javascript">

                //lang nghe xu ly su kien click cua nut "Them"
                dojo.event.topic.subscribe("commItemGroupsAction/prepareAddCommItemGroups", function(event, widget){
                    widget.href = "commItemGroupsAction!prepareAddCommItemGroups.do";
                });

                //lang nghe xu ly su kien click cua nut "Sua"
                dojo.event.topic.subscribe("commItemGroupsAction/prepareEditCommItemGroups", function(event, widget){
                    widget.href = "commItemGroupsAction!prepareEditCommItemGroups.do";
                });

                //lang nghe xu ly su kien click cua nut "Xoa"
                dojo.event.topic.subscribe("commItemGroupsAction/deleteCommItemGroups", function(event, widget){
                    widget.href = "commItemGroupsAction!deleteCommItemGroups.do?"+token.getTokenParamString();
                });

                //xu ly su kien onclick cua nut "Them" (them commItems)
                prepareAddCommItems = function() {
                    gotoAction("divDisplayInfo", "${contextPath}/commItemsAction!prepareAddCommItems.do");
                }
            </script>
        </s:else>

        <%--Danh sach nhom khoan muc--%>
        <sx:div id="listCommItemGroup">
            <s:if test="#session.listCommItemGroup != null && #session.listCommItemGroup.size() > 0">
                <jsp:include page="listCommGroupItems.jsp"/>
            </s:if>
        </sx:div>
        
        <%--Danh sach khoan muc thuoc nhom--%>
        <sx:div id = "listCommItems">
            <s:if test="#session.listCommItems != null && #session.listCommItems.size() > 0">
                <jsp:include page="listCommItems.jsp"/>
            </s:if>
        </sx:div>
        
    </tags:imPanel>
</div>

