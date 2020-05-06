<%-- 
    Document   : lookupItemHistory
    Created on : Sep 5, 2012, 4:37:35 PM
    Author     : os_hoangpm3
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

<tags:imPanel title="L.200041">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="lookupItemHistoryAction" theme="simple" method="post" id="lookupItemHistoryForm">
<s:token/>

            <table class="inputTbl3Col">
                <tr>
                    <td style="width: 100px"><tags:label key="MSG.items"/></td>
                    <td class="oddColumn" style="width: 400px">
                        <s:select name="lookupItemHistoryForm.tableName"
                                  id="tableName"
                                  theme="simple"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="%{getText('L.200045')}"
                                  list="%{#request.listTable!=null ? #request.listTable : # {}}"
                                  listKey="itemCode" listValue="itemName"/>                       


                    </td>

                    <td style="padding-left: 170px">
                        <div class="divHasBorder" style="width: 510px">
                            <table class="inputTbl2Col">
                                <tr>
                                    <td style="width: 100px"><tags:label key="MSG.impact.type"/></td>
                                    <td>
                                        <s:checkbox name="lookupItemHistoryForm.checkAdd" id="lookupItemHistoryForm.checkAdd" /> <s:text name="MSG.LST.811" />
                                        &nbsp;
                                        <s:checkbox name="lookupItemHistoryForm.checkEdit" id="lookupItemHistoryForm.checkEdit"/> <s:text name="MSG.LST.812" />
                                        &nbsp;
                                        <s:checkbox name="lookupItemHistoryForm.checkDelete" id="lookupItemHistoryForm.checkDelete" /> <s:text name="MSG.LST.813" />
                                                                             
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>

            

                <tr>
                    <td style="width: 100px"><tags:label key="MSG.GOD.117" required="true"/></td>
                    <td>
                        <tags:dateChooser property="lookupItemHistoryForm.fromDate" id="fromDate" styleClass="txtInputFull" />
                    </td>
                    <td style="padding-left: 170px">
                        <table class="inputTbl2Col">
                            <tr>
                                <td style="width: 100px"><tags:label key="MSG.GOD.118" required="true"/></td>
                                <td>
                                    <tags:dateChooser property="lookupItemHistoryForm.toDate" id="toDate" styleClass="txtInputFull" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <div align="center" style="padding-top:3px; padding-bottom:10px">
            <tags:submit formId="lookupItemHistoryForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="listSearch"
                         validateFunction="checkValidFields();"
                         value="MSG.generates.find"
                         preAction="lookupItemHistoryAction!lookupItemHistory.do"/>
            <tags:submit formId="lookupItemHistoryForm"
                         showLoadingText="true"
                         cssStyle="width:120px; "
                         targets="bodyContent"
                         validateFunction="checkValidFields();"
                         value="L.200043"
                         preAction="lookupItemHistoryAction!exportIsdnHistory.do"/>
        </div>
    </div>

    <div id="listSearch">
        <s:if test="#request.listLookupItemHistory != ''">
            <jsp:include page="listLookupItemHistory.jsp"/>
        </s:if>
    </div>
</tags:imPanel>

<s:if test="#request.filePath!=null && #request.filePath!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
</s:if>

<s:if test="lookupItemHistoryForm.filePath!=null && lookupItemHistoryForm.filePath!=''">
    <script>
        window.open('${contextPath}<s:property escapeJavaScript="true"  value="lookupItemHistoryForm.filePath"/>','','toolbar=yes,scrollbars=yes');
    </script>
    <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="lookupItemHistoryForm.filePath"/>' ><tags:label key="MSG.download2.file.here"/></a></div>
</s:if>

<script language="javascript">

    $('tableName').focus();

    checkValidFields = function() {
        if ($('message') != null)
            $('message').innerHTML="";
        var dateExported= dojo.widget.byId("fromDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
            //            $( 'message' ).innerHTML='Lỗi. Trường từ ngày không được để trống.';
            $('message').innerHTML="<s:text name="E.200081"/>";
            //$('fromDate').select();
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
            //            $( 'message' ).innerHTML='Lỗi. Trường từ ngày nhập không hợp lệ.';
            $('message').innerHTML="<s:text name="E.200082"/>";
            dateExported.domNode.childNodes[1].focus();
            return false;
        }

        var dateExported1 = dojo.widget.byId("toDate");
        if(dateExported1.domNode.childNodes[1].value.trim() == ""){
            //            $( 'message' ).innerHTML='Lỗi. Trường đến ngày không được để trống.';
            $('message').innerHTML="<s:text name="E.200083"/>";
            dateExported1.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(dateExported1.domNode.childNodes[1].value)){
            //            $( 'message' ).innerHTML='Lỗi. Trường đến ngày nhập không hợp lệ.';
            $('message').innerHTML="<s:text name="E.200084"/>";
            dateExported1.domNode.childNodes[1].focus();
            return false;
        }
        if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
            //            $( 'message' ).innerHTML='Lỗi. Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến';
            $('message').innerHTML="<s:text name="E.200085"/>";
            dateExported1.domNode.childNodes[1].focus();
            return false;
        }

        if(compareDates(GetDateSys(),dateExported.domNode.childNodes[1].value)){
            //            $('message').innerHTML='Lỗi. Trường từ ngày không được lớn hơn ngày hiện tại';
            $('message').innerHTML="<s:text name="E.200086"/>";
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if(compareDates(GetDateSys(),dateExported1.domNode.childNodes[1].value)){
            //            $('message').innerHTML='Lỗi. Trường đến ngày không được lớn hơn ngày hiện tại';
            $('message').innerHTML="<s:text name="E.200087"/>";
            dateExported1.domNode.childNodes[1].focus();
            return false;
        }

        return true;
    }

    function GetDateSys() {
        var time = new Date();
        var dd=time.getDate();
        var mm=time.getMonth()+1;
        var yy=time.getFullYear() ;
        var temp = '';
        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;
        temp = dd + "/" + mm + "/" + yy ;
        return(temp);
    }

</script>

