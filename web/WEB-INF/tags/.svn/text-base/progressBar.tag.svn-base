<%-- 
    Document   : progressBar
    Created on : Jul 13, 2009, 2:54:06 PM
    Author     : Doan Thanh 8
--%>

<%--
    Note    : hien thi thanh progress
    Dau vao :
                - action de update progressBar
                - khoang thoi gian lap lai
--%>

<%@tag description="hien thi thanh progress" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib  prefix="sx" uri="/struts-dojo-tags"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="updateUrl" required="true"%>
<%@attribute name="intervalTime"%>

<%-- any content can be specified here e.g.: --%>
<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<div id="div8DT1" style="display:none">
    <div id="divProgressbar">
        <span id="spanProgressbar">[ Loading Progress Bar ]</span>
    </div>
</div>

<script type="text/javascript">

    //tao thanh progressbar
    manualPB2 = new JS_BRAMUS.jsProgressBar(
    $('spanProgressbar'),
    0,
    {
        showText	: true,
        animate		: true,
        width		: 120,
        height		: 12,
        boxImage	: '${contextPath}/share/js/progressbar/images/bramus/percentImage.png',
        barImage	: '${contextPath}/share/js/progressbar/images/bramus/percentImage_back1.png'
    }
    );
    
    var intervalTime = '<s:property value="%{#attr.intervalTime}"/>';
    if(intervalTime == null || intervalTime == '' || intervalTime*1 <= 0) {
        intervalTime = 1000; //mac dinh delay 1s
    }

    var myInterval;

    //bat dau thuc hien lap thanh progress bar
    startProgressBar = function() {
        if(myInterval != null) {
            clearInterval(myInterval);
        }
        $('div8DT1').style.display="block";
        myInterval = window.setInterval('updateValueForProgressBar()', intervalTime * 1);
    }

    //dung thanh progress bar
    stopProgressBar = function() {
        clearInterval(myInterval);
        $('div8DT1').style.display="none";
    }

    updateValueForProgressBar = function() {
        updateProgressBar('<s:property value="%{#attr.updateUrl}"/>');
        var currentPercentage = manualPB2.getPercentage();
        if(currentPercentage == 100){
            stopProgressBar();
        }
    }

    updateProgressBar = function(url){
        dojo.io.bind({
            url:url ,
            error: function(type, data, evt){
                alert("error");
            },
            handler: function(type, data, e) {
                if(dojo.lang.isObject(data)) {
                    //update data vao status bar
                    var progressBarValue = data['progressBarValue'];
                    if(progressBarValue != null) {
                        manualPB2.setPercentage(progressBarValue);
                    }
                }
            },
            mimetype: "text/json"
        });
    }

</script>

