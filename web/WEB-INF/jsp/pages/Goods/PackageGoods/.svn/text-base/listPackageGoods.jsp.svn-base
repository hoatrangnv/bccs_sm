<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listSaleServices
    Created on : Apr 16, 2009, 2:53:45 PM
    Author     : tamdt1
--%>

<%--
    Notes   : danh sach cac saleServices
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
    <div  style="width:100%; height:450px;">
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>

        <!-- phan tim kiem thong tin (loc danh sach) -->
        <div>
            <s:form action="packageGoodsAction" theme="simple" enctype="multipart/form-data"  method="post" id="saleServicesForm">
<s:token/>

                <table class="inputTbl6Col">
                    <tr>
                        <td><tags:label key="MSG.GOD.code"/></td>
                        <td class="oddColumn">
                            <s:textfield name="saleServicesForm.code" id="saleServicesForm.code" maxlength="50" cssClass="txtInputFull"/>
                        </td>
                        <td><tags:label key="MSG.package.goods.name"/></td>
                        <td class="oddColumn">
                            <s:textfield name="saleServicesForm.name" id="saleServicesForm.name" maxlength="100" cssClass="txtInputFull"/>
                        </td>
                        <%--td>Ghi chú</td>
                        <td class="oddColumn">
                            <s:textfield name="saleServicesForm.notes" id="saleServicesForm.notes" maxlength="450" cssClass="txtInputFull"/>
                        </td--%>
                        <td style="width:85px; ">
                            <div align="right">
                                <sx:submit parseContent="true" executeScripts="true"
                                           formId="saleServicesForm" loadingText="Processing..."
                                           cssStyle="width:80px;"
                                           showLoadingText="true" targets="divDisplayInfo"
                                           key="MSG.find"  beforeNotifyTopics="packageGoodsOverviewAction/searchPackageGoods"/>

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

        <!-- phan hien thi danh sach cac dich vu ban hang-->
        <div style="width:100%;">

            <display:table id="tblListPackageGoods" name="listPackageGoods" class="dataTable"
                           targets="divDisplayInfo" pagesize="15"
                           requestURI="packageGoodsAction!pageNavigator.do"
                           cellpadding="1" cellspacing="1">
                <display:column  escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListPackageGoods_rowNum)}</display:column>
                <display:column  escapeXml="true" property="code" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.code'))}" sortable="false" headerClass="tct"/>
                <display:column  escapeXml="true" property="name" title=" ${fn:escapeXml(imDef:imGetText('MSG.package.goods.name'))}" sortable="false" headerClass="tct"/>
                <display:column  escapeXml="true" property="notes" title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.020'))}" sortable="false" headerClass="tct"/>
                <display:column  escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.SAE.146'))}" sortable="false" style="width:80px; text-align:center" headerClass="tct">
                    <s:url action="packageGoodsAction!displayPackageGoods" id="URL1">
                        <s:param name="selectedPackageGoodsId" value="#attr.tblListPackageGoods.saleServicesId"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
<!--                        <img src="${contextPath}/share/img/accept.png" title="Thông tin chi tiết gói hàng" alt="Chi tiết"/>-->
                        <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.070)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.070)"/>"/>
                    </sx:a>
                </display:column>
            </display:table>

        </div>


    </div>
</tags:imPanel>


<script language="javascript">
    //lang nghe, xu ly su kien khi click nut "Tim kiem"
    dojo.event.topic.subscribe("packageGoodsOverviewAction/searchPackageGoods", function(event, widget){
        widget.href = "packageGoodsAction!searchPackageGoods.do";
    });

    //xu ly su kien onclick cua nut "Them" (them saleservices)
    prepareAddPackageGoods = function() {
        gotoAction("divDisplayInfo", "${contextPath}/packageGoodsAction!prepareAddPackageGoods.do");
    }
</script>


