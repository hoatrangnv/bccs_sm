<%--
    Document   : reportGoodsBetwenTheBranches
    Created on : Jan 15, 2013, 3:52:24 PM
    Author     : trungdh3_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>
<tags:imPanel title="MENU.200030">
    <s:form action="reportGoodsBetwenTheBranches" theme="simple"  enctype="multipart/form-data" method="post" id="reportStockImpExpForm">
        <s:token/>
        <s:if test="reportStockImpExpForm.reportType==1">
            <script>
                var title ="${fn:escapeXml(imDef:imGetText('IM_REPORT_GOODS_BETWEEN_THE_BRANCHES_V2_QUANTITY'))}";
                document.title=title.trim();
                document.getElementById("myHeader").innerHTML=title;
            </script>
        </s:if>
        <s:if test="reportStockImpExpForm.reportType==2">
            <script>
                var title ="${fn:escapeXml(imDef:imGetText('IM_REPORT_GOODS_BETWEEN_THE_BRANCHES_V2_DETAIL'))}";
                document.title=title.trim();
                document.getElementById("myHeader").innerHTML=title;
            </script>
        </s:if>
        <div class="divHasBorder">
            <table class="inputTbl8Col" >
                <tr>
                    <td style="width:10%; "><tags:label key="FROM_BANCHES" /></td>
                    <td style="width:25%; " class="text" >
                        <tags:imSearch codeVariable="reportStockImpExpForm.shopCodeFrom"
                                       nameVariable="reportStockImpExpForm.shopNameFrom"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent=""
                                       />
                    </td>

                    <td style="width:10%; "><tags:label key="TO_BANCHES" /></td>
                    <td style="width:25%; " class="text" >
                        <tags:imSearch codeVariable="reportStockImpExpForm.shopCodeTo"
                                       nameVariable="reportStockImpExpForm.shopNameTo"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       elementNeedClearContent=""
                                       />
                    </td>
                    <td colspan="2" >
                        <tags:imRadio id="reportStockImpExpForm.reportType"
                                      name="reportStockImpExpForm.reportType"
                                      list="1:MSG.GOD.221,2:MSG.SAE.041"
                                      onchange="showHide(this.value)"
                                      />
                    </td>
                </tr>
                <tr>

                    <td style="width:10%; "><tags:label key="MSG.fromDate" required="true" /></td>
                    <td   class="text">
                        <tags:dateChooser property="reportStockImpExpForm.fromDate" id="fromDate"/>
                    </td>
                    <td style="width:10%; "><tags:label key="MSG.generates.to_date" required="true" /></td>
                    <td class="text">
                        <tags:dateChooser property="reportStockImpExpForm.toDate" id="toDate"/>
                    </td>
                    <td></td>
                    <td></td>

                </tr>
            </table>
        </div>

        <div>
            <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
        </div>

        <div>
            <s:if test="#request.filePath !=null && #request.filePath!=''">
                <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(filePath)}', '', 'toolbar=yes,scrollbars=yes');
                    <%--window.open('${contextPath}<s:property value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                        window.open('<s:property value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                </script>
                <div>
                    <a href="${contextPath}/download.do?${fn:escapeXml(filePath)}">
                        <tags:displayResult id="reportInvoiceMessage" property="reportInvoiceMessage" type="key"/>
                    </a>
                    <%--<a href='${contextPath}<s:property value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>
                    <a href='<s:property value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" /></a>--%>
                </div>
            </s:if>

        </div>
    </s:form>
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="reportStockImpExpForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MSG.report"
                     validateFunction="checkValidate()"
                     preAction="reportGoodsBetwenTheBranches!createReport.do"/>
    </div>
</tags:imPanel>
<script>
    checkValidate=function(){
        var fromDate=dojo.widget.byId('fromDate');
        var toDate=dojo.widget.byId('toDate');
        if(fromDate.domNode.childNodes[1].value == null || fromDate.domNode.childNodes[1].value == "" ){
            $( 'message' ).innerHTML='<s:text name="Report.PunishSaleToAgent.FromDate.Empty"/>';
            fromDate.domNode.childNodes[1].focus();
            return false;
        }
        if(toDate.domNode.childNodes[1].value == null || toDate.domNode.childNodes[1].value == "" ){
            $( 'message' ).innerHTML='<s:text name="Report.PunishSaleToAgent.ToDate.Empty"/>';
            toDate.domNode.childNodes[1].focus();
            return false;
        }
        return true;
    }

    showHide=function(reportType){
        if(reportType=="2"){
            var title ="${fn:escapeXml(imDef:imGetText('IM_REPORT_GOODS_BETWEEN_THE_BRANCHES_V2_DETAIL'))}";
            document.title=title.trim();
            document.getElementById("myHeader").innerHTML=title;
        }
        if(reportType=="1"){
            var title ="${fn:escapeXml(imDef:imGetText('IM_REPORT_GOODS_BETWEEN_THE_BRANCHES_V2_QUANTITY'))}";
            document.title=title.trim();
            document.getElementById("myHeader").innerHTML=title;
        }
    }
 
  

    
</script>