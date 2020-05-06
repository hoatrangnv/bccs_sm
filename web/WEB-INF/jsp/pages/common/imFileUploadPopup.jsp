<%-- 
    Document   : imFileUploadPopup
    Created on : Sep 12, 2009, 1:58:22 PM
    Author     : Doan Thanh 8
--%>

<%--
    Note       : bat popup chon file, upload len server
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://www.davidjc.com/taglibs" prefix="afile"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<head>
    <script src="http://yui.yahooapis.com/2.4.1/build/yahoo/yahoo-min.js"></script>
    <script src="http://yui.yahooapis.com/2.4.1/build/event/event-min.js"></script>
    <script src="http://yui.yahooapis.com/2.4.1/build/connection/connection-min.js"></script>

    <afile:head />

</head>

<style>
    body {font-family:"Tahoma";}
    .file_container {width:480px;font-size:11px;border: 1px solid black;margin:0px;border-collapse:collapse;}
    .filelist {font-size:11px;width:100%;margin:0px;border-collapse:collapse;}
    #ajaxFileUploadForm .label {font-size:11px;}
    #ajaxFileUploadForm input {font-size:11px;}
    .filelist tr.odd {background-color:#eef;}
    .filelist tr.even { background-color:#dde;}
    .filelist tr td {padding:4px;}
</style>

<script>
    var callback =
    {
        cache:false,
        success: function(o) {document.getElementById('did').innerHTML = o.responseText;}
    }
    function show_list() {
        alert('show_list');
        //var transaction = YAHOO.util.Connect.asyncRequest('GET', '/StrutsPluginDemo/FileUploadDetailsAction.action', callback, null);
    }
</script>

<body>
    <table class="file_container">
        <tr>
            <td>This demonstrates the use of Struts2 Ajax upload plugin and YUI Ajax connect library.
                For more details see <a href="">here</a>.
            </td>
        </tr>
        <tr>
            <td><afile:ajaxfileuploadform action="ImFileUploadAction" dobefore="" doafter="show_list" /></td>
        </tr>
        <tr>
            <td><div id='did'></div></td>
        </tr>
    </table>
</body>

