<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListPackageGoodsNew
    Created on : Sep 27, 2010, 9:02:36 AM
    Author     : User
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
    request.setAttribute("listPackageGoods", request.getSession().getAttribute("listPackageGoods"));
%>
<tags:imPanel title="MSG.ISN.038">
    <div  style="width:100%; height:650px;">
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>
        <!-- phan tim kiem thong tin (loc danh sach) -->
        <div>
            <s:form action="packageGoodsNewAction" theme="simple" enctype="multipart/form-data"  method="post" id="packageGoodsForm">
                <s:token/>

                <table class="inputTbl6Col">
                    <tr>
                        <td><tags:label key="MSG.GOD.code"/></td>
                        <td class="oddColumn">
                            <s:textfield name="packageGoodsForm.packageCode" id="packageGoodsForm.packageCode" maxlength="50" cssClass="txtInputFull"/>
                        </td>
                        <td><tags:label key="MSG.package.goods.name"/></td>
                        <td class="oddColumn">
                            <s:textfield name="packageGoodsForm.packageName" id="packageGoodsForm.packageName" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                        <%--td>Ghi chú</td>
                        <td class="oddColumn">
                            <s:textfield name="packageGoodsForm.notes" id="packageGoodsForm.notes" maxlength="450" cssClass="txtInputFull"/>
                        </td--%>
                        <td style="width:85px; ">
                            <div align="right">
                                <sx:submit parseContent="true" executeScripts="true"
                                           formId="packageGoodsForm" loadingText="Processing..."
                                           cssStyle="width:80px;"
                                           showLoadingText="true" targets="divDisplayInfo"
                                           key="MSG.find"  beforeNotifyTopics="packageGoodsNewAction/searchPackageGoodsNew"/>

                            </div>
                        </td>
                        <td style="width:85px; ">

                            <div align="right">
                                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.010'))}" style="width:80px;" onclick="prepareAddPackageGoods()">
                            </div>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
        <div style="height: 600px;overflow: auto">
            <!-- phan hien thi danh sach cac dich vu ban hang-->
            <display:table id="tblListPackageGoods" name="listPackageGoods" class="dataTable"
                           targets="divDisplayInfo" pagesize="15"
                           requestURI="packageGoodsNewAction!pageNavigator.do"
                           cellpadding="1" cellspacing="1">
                <display:column escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListPackageGoods_rowNum)}</display:column>
                <display:column escapeXml="true" property="packageCode" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.code'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="packageName" title=" ${fn:escapeXml(imDef:imGetText('MSG.package.goods.name'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('MSG.LST.038'))}" property="fromDate" style="text-align:center" headerClass="tct" format="{0,date,dd/MM/yyyy}"/>
                <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('MSG.LST.039'))}" property="endDate" style="text-align:center" headerClass="tct" format="{0,date,dd/MM/yyyy}"/>
                <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('MES.CHL.060'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListPackageGoods.status == 0">
                        <s:property escapeJavaScript="true"  value="getText('MES.CHL.130')"/>
                    </s:if>
                    <s:else>
                        <s:property escapeJavaScript="true"  value="getText('MES.CHL.129')"/>
                    </s:else>
                </display:column>
                <display:column  escapeXml="true" property="decriptions" title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.020'))}" sortable="false" headerClass="tct"/>
                <display:column  escapeXml="false" title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.146'))}" sortable="false" style="width:80px; text-align:center" headerClass="tct">
                    <s:url action="packageGoodsNewAction!displayPackageGoods" id="URL1">
                        <s:param name="selectedPackageGoodsId" value="#attr.tblListPackageGoods.packageGoodsId"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
    <!--                        <img src="${contextPath}/share/img/accept.png" title="Thông tin chi tiết gói hàng" alt="Chi tiết"/>-->
                        <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.070')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.GOD.070')"/>"/>
                    </sx:a>
                </display:column>
            </display:table>
        </div>
    </div>
</tags:imPanel>


<script language="javascript">
    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("packageGoodsNewAction/searchPackageGoodsNew", function(event, widget){
        widget.href = "packageGoodsNewAction!searchPackageGoods.do?" + token.getTokenParamString();
    });

    //xu ly su kien onclick cua nut "Them" (them saleservices)
    prepareAddPackageGoods = function() {
        gotoAction("divDisplayInfo", "${contextPath}/packageGoodsNewAction!prepareAddPackageGoods.do?" + token.getTokenParamString());
    }
</script>
