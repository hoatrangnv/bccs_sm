<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : packageGoodsV2
    Created on : Aug 8, 2011, 4:31:33 PM
    Author     : kdvt_tronglv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="L.100016">
    <s:form action="packageGoodsV2Action" theme="simple"  enctype="multipart/form-data" method="post" id="packageGoodsV2Form">
<s:token/>

        <div style="width:100%;" id="divSearchInfo">

            <div align="center" class="txtError" id="divErrorFilePath">
                <s:if test="#request.filePath != null">
                    <a class="cursorHand" onclick="downloadFile('${contextPath}/${fn:escapeXml(filePath)}')">
                        ${fn:escapeXml(imDef:imGetText('Download tai day'))}
                    </a>
                </s:if>
            </div>

            <div class="divHasBorder">
                <table class="inputTbl6Col">
                    <tr>
                        <td><tags:label key="L.100017"/></td>
                        <td class="oddColumn">
                            <s:textfield name="packageGoodsV2Form.codeSearch" id="packageGoodsV2Form.codeSearch" cssClass="txtInputFull" maxlength="50"/>
                        </td>
                        <td><tags:label key="L.100018"/></td>
                        <td class="oddColumn">
                            <s:textfield name="packageGoodsV2Form.nameSearch" id="packageGoodsV2Form.nameSearch" cssClass="txtInputFull" maxlength="200"/>
                        </td>
                        <td><tags:label key="L.100019"/></td>
                        <td>
                            <tags:imSelect name="packageGoodsV2Form.statusSearch"
                                           id="packageGoodsV2Form.statusSearch"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="L.100025"
                                           list="0:L.100020, 1:L.100021"/>
                        </td>
                    </tr>

                </table>

                <div style="text-align: center; padding-top: 10px; padding-bottom: 5px;">
                    <tags:submit  id="searchButton" cssStyle="width:80px;"
                                  formId="packageGoodsV2Form"
                                  showLoadingText="true"
                                  targets="bodyContent"
                                  value="L.100022"
                                  preAction="packageGoodsV2Action!searchPackageGoods.do"/>
                    <input type="button" style="width:80px;" onclick="prepareAddNewPackageGoods();"  value="<s:text name="L.100023"/>"/>
                    <input type="button" value="Xuất Excel" style="width:80px;" onclick="onSearchExcelExport()">
                </div>

            </div>

        </div>
        <div style="width:100%;" id="divPackageGoodsList">
            <jsp:include page="packageGoodsV2List.jsp"/>
        </div>

    </s:form>

</tags:imPanel>

<script language="javascript">
    prepareAddNewPackageGoods = function (){        
        openDialog("${contextPath}/packageGoodsV2Action!prepareAddNewPackageGoods.do?" + token.getTokenParamString(), 800, 600,true);
    }
    downloadFile = function(excelFileUrl) {
        window.open(excelFileUrl, '', 'toolbar=yes,scrollbars=yes');
    }
    
    onSearchExcelExport = function() {
        //document.forms["packageGoodsV2Action"].submit();
        var strConfirm = strConfirm = getUnicodeMsg('<s:text name="MES.package.goods.014"/>');
        if (!confirm(strConfirm)) {
            return;
        }
        document.getElementById("packageGoodsV2Form").action="exportToExcelAction.do?" + token.getTokenParamString();
        document.getElementById("packageGoodsV2Form").submit();
        
    }

    reloadSearchPage = function(){
        gotoAction('divPackageGoodsList','packageGoodsV2Action!reloadListPackageGoods.do?');//reloadListPackageGoods
        //document.getElementById("returnMsgClient").innerHTML=getUnicodeMsg('<s:text name="saleRetail.warn.saveSuccess"/>');
    }

</script>
