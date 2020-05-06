<%-- 
    Document   : changTypeDownload
    Created on : Jun 22, 2016, 3:09:33 PM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<!--<script type="text/javascript">

</script>-->
<a onclick="downloadTemFile('<s:property escapeJavaScript="true"  value="#attr.fileName" />')" ><tags:label key="MSG.download.fileaddNew.BTS"/></a>

