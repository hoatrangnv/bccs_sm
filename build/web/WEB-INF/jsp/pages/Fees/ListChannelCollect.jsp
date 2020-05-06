<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListChannelCollect
    Created on : Dec 15, 2009, 4:42:25 PM
    Author     : AnDV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>


<%
            request.setAttribute("lstChannelType", request.getSession().getAttribute("lstChannelType"));
            request.setAttribute("contextPath", request.getContextPath());
            if (request.getAttribute("lstShopDetail") != null) {
                request.setAttribute("lstShopDetail", request.getAttribute("lstShopDetail"));
            }
            if (request.getAttribute("lstStaffDetail") != null) {
                request.setAttribute("lstStaffDetail", request.getAttribute("lstStaffDetail"));
            }

%>

<%------------------------------------------------------------------------------------------------------------------------
Danh sach loai kenh--%>
<s:hidden name="collectFeesForm.channelTypeId" id="channelTypeId"/>
<s:hidden name="collectFeesForm.detail" id="detail"/>
<s:if test="#session.toDetail == 0">
    <tags:imPanel title="Tổng hợp theo loại kênh">
        <div id="lstChannelType" style="width:100%; height:100px; overflow:auto;">
            <sx:div id="displayTagFrame" >
                <s:if test="#request.lstChannelType != null && #request.lstChannelType.size()>0">

                    <display:table  id="tblChannelType" name="lstChannelType"
                                    targets="displayTagFrame"
                                    class="dataTable" cellpadding="1" cellspacing="1" >
                        <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center" >
                            ${fn:escapeXml(tblChannelType_rowNum)}
                        </display:column>
                        <display:column title="Chọn" sortable="false" style="width:50px; text-align:center"headerClass="tct">
                            <s:checkbox name="collectFeesForm.selectedTypes" theme="simple" id="selectedTypes" fieldValue="%{#attr.tblChannelType.channelTypeId}" />
                        </display:column>
                        <display:column escapeXml="true"  property="name" title="Tên kênh" sortable="false" headerClass="tct" />
                        <display:column title="Chi tiết" sortable="false" headerClass="tct" style="text-align:center">
                            <sx:a onclick="detail('%{#attr.tblChannelType.channelTypeId}','%{#attr.tblChannelType.name}')" >
                                Tổng hợp chi tiết
                            </sx:a>
                        </display:column>
                    </display:table>
                </s:if>
            </sx:div>
        </div>
    </tags:imPanel>
</s:if>

<%----------------------------------------------------------------------------------------------
Danh sach chi tiet cac channel ung voi channelType
--%>
<s:if test="#session.toDetail == 1">
    <tags:imPanel title="Tổng hợp theo từng loại kênh">
        <table class="inputTbl6Col">
            <tr>
                <td class="label">Mã kênh</td>
                <td class="text">
                    <s:textfield name="collectFeesForm.channelCode" theme="simple" id="channelCode" maxlength="30" cssClass="txtInputFull"/>
                </td>
                <td class="label">Tên kênh</td>
                <td class="text">
                    <s:textfield name="collectFeesForm.channelName" theme="simple" id="channelName" maxlength="50" cssClass="txtInputFull"/>
                </td>
                <td class="label">Loại kênh</td>
                <td class="text">
                    <s:textfield value="%{#session.channelTypeName}" theme="simple" maxlength="50" cssClass="txtInputFull" disabled="true"/>
                </td>
            </tr>
        </table>
        <%-- -------------------
             object ung voi shop va staff--%>

        <s:if test="#session.object == 2">
            <!-- phan nut tac vu -->
            <div align="center" style="padding-bottom:5px;">
                <tags:submit formId="collectFeesForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="ListChannelFees"
                             value="Tìm kiếm"
                             preAction="collectFeesAction!searchStaff.do"/>
            </s:if>
            <s:if test="#session.object == 1">

                <!-- phan nut tac vu -->
                <div align="center" style="padding-bottom:5px;">
                    <tags:submit formId="collectFeesForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="ListChannelFees"
                                 value="Tìm kiếm"
                                 preAction="collectFeesAction!searchShop.do"/>

                </s:if>
                <sx:submit parseContent="true" executeScripts="true"
                           formId="collectFeesForm" loadingText="Đang thực hiện..."
                           showLoadingText="true" targets="ListChannelFees"
                           cssStyle="width:180"
                           value="Tổng hợp theo loại kênh"  beforeNotifyTopics="collectFeesAction/collectByType"/>

            </div>

        </tags:imPanel>
        <%-- --------------------------------------------------------------------------------------------------
         Phan danh sach kenh chi tiet theo loai kenh--%>
        <tags:imPanel title="Danh sách kênh chi tiết">

            <s:if test="#session.object == 2">

                <div id="ListStaffChannelDetail" style="width:100%; height:400px; overflow:auto;">
                    <sx:div id="displayStaffDetail">
                        <display:table id="staffDetail" targets="displayStaffDetail" name="lstStaffDetail"  class="dataTable"  cellpadding="1" cellspacing="1">
                            <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center" >
                                ${fn:escapeXml(staffDetail_rowNum)}
                            </display:column>
                            <display:column title="<input id ='checkAllChannelId' type='checkbox' onclick=\"selectCbChannelAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                                <s:checkbox name="collectFeesForm.selectedChannelDetail" id="checkBoxChannelId%{#attr.staffDetail.staffCode}"
                                            theme="simple"
                                            fieldValue="%{#attr.staffDetail.staffCode}"
                                            onclick="checkSelectCbChannelAll();"/>
                            </display:column>
                            <%--                            <display:column title="Chọn" sortable="false" style="text-align:center" headerClass="tct">
                                                            <s:checkbox name="collectFeesForm.selectedChannelDetail" theme="simple" id="selectedChannelDetail" fieldValue="%{#attr.staffDetail.staffCode}" />
                                                        </display:column>
                            --%>                            <display:column escapeXml="true"  title="Tên kênh" property="name"/>
                            <display:column escapeXml="true"  title="Mã kênh"  property="staffCode"/>
                        </display:table>

                    </sx:div>
                </div>
            </s:if>
            <s:if test="#session.object == 1">

                <div id="ListShopChannelDetail" style="width:100%; height:400px; overflow:auto;">
                    <sx:div id="displayShopDetail">
                        <display:table id="shopDetail" targets="displayShopDetail" name="lstShopDetail"  class="dataTable"  cellpadding="1" cellspacing="1">
                             <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center" >
                                ${fn:escapeXml(shopDetail_rowNum)}
                            </display:column>
                            <display:column title="<input id ='checkAllChannelId' type='checkbox' onclick=\"selectCbChannelAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                                <s:checkbox name="collectFeesForm.selectedChannelDetail" id="checkBoxChannelId%{#attr.shopDetail.shopCode}"
                                            theme="simple"
                                            fieldValue="%{#attr.shopDetail.shopCode}"
                                            onclick="checkSelectCbChannelAll();"/>
                            </display:column>
                            <%--<display:column title="Chọn" sortable="false" style="text-align:center" headerClass="tct">
                                <s:checkbox name="collectFeesForm.selectedChannelDetail" theme="simple" id="selectedChannelDetail" fieldValue="%{#attr.shopDetail.shopCode}" />
                            </display:column>
                            --%><display:column escapeXml="true"  title="Tên kênh" property="name"/>
                            <display:column escapeXml="true"  title="Mã kênh"  property="shopCode"/>
                        </display:table>
                    </sx:div>
                </div>
            </s:if>
        </tags:imPanel>

    </s:if>

    <script type="text/javascript">
        dojo.event.topic.subscribe("collectFeesAction/searchStaff", function(event, widget){
            widget.href = "collectFeesAction!searchStaff.do?"+ token.getTokenParamString();
        });
        dojo.event.topic.subscribe("collectFeesAction/searchShop", function(event, widget){
            widget.href = "collectFeesAction!searchShop.do?"+ token.getTokenParamString();
        });
        detail = function(channelTypeId,name){
            gotoAction('ListChannelFees', "${contextPath}/collectFeesAction!Detail.do?channelTypeId=" + channelTypeId+'&channelTypeName='+ name+"&"+ token.getTokenParamString());

        }
        dojo.event.topic.subscribe("collectFeesAction/collectByType", function(event, widget){
            widget.href = "collectFeesAction!collectByType.do?"+ token.getTokenParamString();
        });
        selectCbChannelAll = function(){
            selectAll("checkAllChannelId", "collectFeesForm.selectedChannelDetail", "checkBoxChannelId");
        }

        checkSelectCbChannelAll = function(){
            checkSelectAll("checkAllChannelId", "collectFeesForm.selectedChannelDetail", "checkBoxChannelId");
        }


    </script>
