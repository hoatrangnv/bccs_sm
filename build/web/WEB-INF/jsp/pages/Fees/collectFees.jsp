<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
    Document   : collectFees.jsp
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : Tuannv
    Note: Loc so dep
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="collectFeesAction.do" id="collectFeesForm" method="post" theme="simple">
<s:token/>

    <table style="width:100%; background-color:#F5F5F5;" >
           <tr>
            <td colspan="5">
                <sx:tabbedpanel id="tabContainer">

                    <sx:div label="Kênh tính phí"  id="ListChannelFees">
                        <jsp:include page="ListChannelCollect.jsp" flush="true"/>

                        <%--<jsp:include page="treeChannelFees.jsp">
                            <jsp:param name="channelTypeId" value="${fn:escapeXml(channelType)}"/>
                        </jsp:include>--%>
                    </sx:div>
                    <sx:div label="Khoản mục tính phí"  id="ListItemFees">
                        <tags:imPanel title="Danh sách khoản mục tính phí">
                            <jsp:include page="ListItemsCollect.jsp" flush="true"/>
                        </tags:imPanel>
                    </sx:div>
                    <sx:div label="Tổng hợp"  id="Collect">
                        <tags:imPanel title="Tổng hợp">
                            <table class="inputTbl4Col">
                                <tr>
                                    <td class="label">Loại tổng hợp</td>
                                    <td class="text">
                                        <s:select name="collectFeesForm.money" id="status" cssClass="txtInput"
                                                  list="#{'0':'Tổng hợp số lượng','1':'Tổng hợp tiền'}"/>
                                    </td>
                                    <td class="label">Chu kỳ tính</td>
                                    <td class="text">
                                        <tags:dateChooser property="collectFeesForm.billcycle" styleClass="txtInputFull"/>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <div align="center" style="padding-bottom:5px;">
                                <%-- <tags:submit formId="collectFeesForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodycontent"
                                 value="Tổng hợp"
                                 preAction="collectFeesAction!collectFees.do"/>--%>

                                <sx:submit parseContent="true" executeScripts="true" id="btnCollectFees"
                                           formId="collectFeesForm" highlightColor="blue" loadingText="Đang tổng hợp vui lòng đợi......." showLoadingText="true"
                                           targets="ListCollectFees"  value="Tổng hợp"  beforeNotifyTopics="collectFeesForm/collectFees"
                                           errorNotifyTopics="errorAction" cssStyle="width:100px" cssClass="txtInputFull"/>
                            </div>
                            <div>
                                <sx:div id="ListCollectFees">
                                    <jsp:include page="ListFeesCollect.jsp" flush="true"/>
                                </sx:div>
                            </div>

                        </tags:imPanel>
                    </sx:div>


                </sx:tabbedpanel>

            </td>
        </tr>

        <%-- <tr>
             <td>&nbsp;Loại tổng hợp</td>
             <td>
                 <s:select name="collectFeesForm.money" id="status" cssClass="txtInput"
                           list="#{'0':'Tổng hợp số lượng','1':'Tổng hợp tiền'}"/>
             </td>

            <td>
                &nbsp;Chu kỳ tính

                &nbsp;<tags:dateChooser property="collectFeesForm.billcycle" styleClass="txtInputFull"/>

            </td>
        </tr>

        <tr>
            <td colspan="5" align="center">
                <!--tags:submit targets="ListCollectFees" formId="collectFeesForm"
                             value="Tổng hợp"
                             cssStyle="width:80px;"
                             preAction="collectFeesForm/collectFees"
                             showLoadingText="true"/-->

                <div align="center" style="">
                    <sx:submit parseContent="true" executeScripts="true" id="btnCollectFees"
                               formId="collectFeesForm" highlightColor="blue" loadingText="Đang tổng hợp vui lòng đợi......." showLoadingText="true"
                               targets="ListCollectFees"  value="Tổng hợp"  beforeNotifyTopics="collectFeesForm/collectFees"
                               errorNotifyTopics="errorAction" cssStyle="width:100px" cssClass="txtInputFull"/>

                </div>

            </td>
        </tr>--%>


    </table>
</s:form>

<script>
    dojo.event.topic.subscribe("collectFeesForm/collectFees", function(event, widget){
        widget.href = "collectFeesAction!collectFees.do?"+ token.getTokenParamString();
    });
    <%-- dojo.event.topic.subscribe("collectFeesForm/ListItemFees", function(event, widget){

         var cboChannelTypeId = document.getElementById("channelTypeId");
         var channelTypeId = cboChannelTypeId.options[cboChannelTypeId.selectedIndex].value;
         widget.href = "collectFeesAction!getListItemByChannelTypeId.do?channelTypeId="+channelTypeId;
     });
    --%>
    <%--    var cboChannelTypeId = document.getElementById("channelTypeId");
    var channelTypeId = cboChannelTypeId.options[cboChannelTypeId.selectedIndex].value;

    if (channelTypeId == null || channelTypeId.trim() == ""){
        $( 'btnItemList' ).disabled = true;
        $( 'btnCollectFees' ).disabled = true;
    }
    else{
        $( 'btnItemList' ).disabled = false;
        $( 'btnCollectFees' ).disabled = false;
    }
    --%>

    <%-- changeChannelType = function(){
         var cboChannelTypeId = document.getElementById("channelTypeId");
         var channelTypeId = cboChannelTypeId.options[cboChannelTypeId.selectedIndex].value;
    --%>
    <%--if (channelTypeId == null || channelTypeId.trim() == ""){
        $( 'btnCollectFees' ).disabled = true;
    }
    else{
        $( 'btnCollectFees' ).disabled = false;
    }--%>

    <%--   gotoAction('treeArea', '${contextPath}/collectFeesAction!changeChannelType.do?channelTypeId='+channelTypeId);
   }--%>
</script>
