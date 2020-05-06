<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleTransManagmentDetail.jsp
    Created on : 19/06/2009
    Author     : ThanhNC
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<div style="width:100%">

    <tags:displayResult id="resultViewSaleDetailClient" property="resultViewSaleDetail" type="key"/>

    <table style="width:100%">
        <tr>
            <td style="width:55%;vertical-align:center">
                <s:if test="#attr.channelForm.urlReport != null">
                    <s:url id="fileguide" namespace="/" action="form02Download" >
                        <s:param name="fileDownloadForm02" value="#attr.channelForm.urlReport"/>
                    </s:url>
                    <s:a  href="%{fileguide}">Download Report</s:a>  
                </s:if>
            </td>
        </tr>
    </table>
</div>

<script language="javascript">


</script>


