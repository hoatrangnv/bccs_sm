function openPopup(path,width,height) {
    path = path + (path.indexOf("?") > -1 ? "&" : "?") + "pageId="+pageId;    
    my_window = window.open(path,'',"location=" + (screen.width) + ",status=1, scrollbars=1, titlebar=0, width=" +width + ",height=" + height);
    my_window.focus();
    return false;
}
function openDialog( url, _w, _h, _modal ) {    
    url = url + (url.indexOf("?") > -1 ? "&" : "?") + "pageId="+pageId;    
    var brwVer  = navigator.appVersion;
    var brwName = navigator.appName;
    var brwAgent = navigator.userAgent;

    var _t = 0, _l = 0; // top / left
    var modal;

    var dialog = null;

    switch( arguments.length ) {
        case 6:
            _t = arguments[3];
            _l = arguments[4];
            modal = arguments[5];
            break;
        default:
            _t = 100;
            _l = ( screen.width - _w ) / 2;
            modal = arguments[3];
            break;
    }

    var t = _t;
    var l = _l;

    var w = _w;
    var h = _h;

    var sep = (brwVer.indexOf('MSIE') == -1 ) ? ";" : ",";
    if(modal) {
        if(brwVer.indexOf('MSIE') == -1 && brwAgent.indexOf('Firefox/3') == -1) {
            dialog = window.open(url, '_blank',
                "modal=yes" + sep + "location=no" + sep + "status=no" + sep + "scrollbars=yes" + sep + "width=" + w + "" + sep + "height=" + h + sep + "top=" + t + sep + "left=" + l );
        }
        else {
            dialog = window.showModalDialog(url, "",
                "dialogwidth:" + w + "px" + sep + "dialogheight:" + h + "px" + sep + "center:yes" + sep + "status:no" + sep + "resizable:no" + sep + "location:no" + sep + "toolbar:no" + sep + "menubar:no" + sep + "dialogleft:" + l + "px" + sep + "dialogtop:" + t );
        }
    }
    else {
        sep = ",";
        dialog = window.open(url, '_blank',
            "modal=no" + sep + "location=no" + sep + "status=no" + sep + "scrollbars=yes" + sep + "width=" + w + "" + sep + "height=" + h + "" + sep + "top=" + t + sep + "left=" + l );
    }

    return dialog;
}

function builtTree(

    rootId,

    rootTitle,

    rootLink,

    treeId,

    action, actionParamName,

    containerId,

    loadingContainerId,

    contextPath,

    target,
    
    lazyLoad,

    hasCheckbox,

    cbName,

    isExpandedLevel1){
    var selectedIconTree = null;
    var treeTimeOut = null;
    setEventTreeDiv = function(node)
    {
        var rootNode = node.tree.children[0];
        var treeDiv = rootNode.domNode.parentNode.parentNode.parentNode;
        treeDiv.setAttribute("onmouseover","this.focus()");
        //        treeDiv.setAttribute("onmouseout","this.blur()");
        treeDiv.setAttribute("onkeydown","onTreeDivKeyDown(event)");
        treeDiv.setAttribute("onkeyup","onTreeDivKeyUp(event)");
    }
    treeOpenNodeLink = function(nodeId)
    {
        var myTreeSelector = dojo.widget.byId(treeId+'_treeSelector');

        if(nodeId == myTreeSelector.selectedNode.widgetId)
        {
            if(myTreeSelector.selectedNode.link != null && myTreeSelector.selectedNode.link != undefined && myTreeSelector.selectedNode.link != '')
            {
                if (myTreeSelector.selectedNode.link=="#"){
                    return ;
                }
                else {
                    gotoAction(target, myTreeSelector.selectedNode.link);
                }
            }
        }
    }
    onTreeDivKeyUp = function(evt)
    {
        var myTreeSelector = dojo.widget.byId(treeId+'_treeSelector');
        var event = ( window.event ) ? window.event : evt;
        if(myTreeSelector.selectedNode != null)
        {
            if(event.keyCode == 37)
            {

            }
            else if (event.keyCode == 38)
            {
                var nodeId = myTreeSelector.selectedNode.widgetId;
                treeTimeOut = setTimeout(function() {
                    treeOpenNodeLink(nodeId)
                }, 1000);
            }
            else if (event.keyCode == 39)
            {

            }
            else if (event.keyCode == 40)
            {
                var nodeId = myTreeSelector.selectedNode.widgetId;
                treeTimeOut = setTimeout(function() {
                    treeOpenNodeLink(nodeId)
                }, 1000);
            }
        }
    }
    onTreeDivKeyDown = function(evt)
    {
        
        var myTreeSelector = dojo.widget.byId(treeId+'_treeSelector');
        var event = ( window.event ) ? window.event : evt;
       
        var nextNodeForLeaf = function(node)
        {
            if (node.parent == undefined)
            {
                return null;
            }
            else
            {
                for (var i=0; i < node.parent.children.length;i++)
                {
                    if(node.parent.children[i] == node)
                    {
                        var index = i;
                        break;
                    }
                }
                if (index != node.parent.children.length -1)
                {
                    return node.parent.children[index +1];
                }
                else
                {
                    return nextNodeForLeaf(node.parent);
                }
            }
        }
        var nextNode = function(node)
        {
            if (node.children.length==0 || (node.children[0].domNode.clientHeight == 0 && node.children[0].domNode.clientWidth ==0))
            {
                return nextNodeForLeaf(node);
            }
            else
            {
                return node.children[0];
            }
        }
        // tim node cuoi cung cua 1 node
        var lastNode = function(node)
        {
            if(!node.isExpanded)
            {
                return node;
            }
            else
            {
                if (node.children.length ==0)
                {
                    return node;
                }
                else
                {
                    return lastNode(node.children[node.children.length-1]);
                }
            }
        }
        var previousNode = function(node)
        {
            if (node.parent.widgetType == "Tree")
            {
                return null;
            }
            else
            {
                for (var i=0; i < node.parent.children.length;i++)
                {
                    if(node.parent.children[i] == node)
                    {
                        var index = i;
                        break;
                    }
                }
                if (index != 0)
                {
                    return lastNode(node.parent.children[index -1]);
                }
                else
                {
                    return node.parent;
                }
            }
        }
        var setCurrentNode = function (node)
        {
            selectedIconTree = node;
            if (node.isFolder)
            {
                node.childIcon.src = contextPath + "/share/img/TreeFolderOpenIcon.gif";
            }
            if (myTreeSelector.selectedNode) {
                myTreeSelector.deselect();
            }
            myTreeSelector.doSelect(node);
        }

        if(myTreeSelector.selectedNode != null)
        {
            if(event.keyCode == 37)
            {
                if (myTreeSelector.selectedNode.isFolder)
                {
                    var myRpcController = dojo.widget.byId(treeId + '_treeController');
                    myRpcController.collapse(myTreeSelector.selectedNode);
                }
            }
            else if (event.keyCode == 38)
            {
                if (selectedIconTree.isFolder)
                {
                    selectedIconTree.childIcon.src = contextPath + "/share/img/TreeFolderCloseIcon.gif";
                }
                if(myTreeSelector.selectedNode!=null)
                {
                    var _previousNode = previousNode(myTreeSelector.selectedNode)
                    if( _previousNode!= null)
                    {
                        setCurrentNode(_previousNode);
                    }
                }
                clearTimeout(treeTimeOut);
            }
            else if (event.keyCode == 39)
            {
                if (myTreeSelector.selectedNode.isFolder)
                {
                    var myRpcController = dojo.widget.byId(treeId + '_treeController');
                    var actionUrl = "";
                    if (action.indexOf("?", 0)!= -1){
                        actionUrl = action +'&' + actionParamName + '=' + myTreeSelector.selectedNode.objectId + '&isCheck='+myTreeSelector.selectedNode.isCheck;
                    }else{
                        actionUrl = action +'?' + actionParamName + '=' + myTreeSelector.selectedNode.objectId + '&isCheck='+myTreeSelector.selectedNode.isCheck;
                    }
                    myRpcController.RPCUrl = actionUrl;
                    myRpcController.expand(myTreeSelector.selectedNode);

                }
            }
            else if (event.keyCode == 40)
            {
                if (selectedIconTree.isFolder)
                {
                    selectedIconTree.childIcon.src = contextPath + "/share/img/TreeFolderCloseIcon.gif";
                }
                if(myTreeSelector.selectedNode!=null)
                {
                    var _nextNode = nextNode(myTreeSelector.selectedNode)
                    if( _nextNode!= null)
                    {
                        setCurrentNode(_nextNode);
                    }
                }
                clearTimeout(treeTimeOut);
            }
        }
        else
        {
            if(event.keyCode == 37 || event.keyCode == 38 || event.keyCode == 39 || event.keyCode == 40 )
            {
                setCurrentNode(dojo.widget.byId(treeId).children[0]);
            }
        }
    }
    dojo.widget.TreeNode.prototype.templateString = ("<div class=\"dojoTreeNode\"> " + "<span treeNode=\"${this.widgetId}\" class=\"dojoTreeNodeLabel\" dojoAttachPoint=\"labelNode\"> " + "\t\t<span  dojoAttachPoint=\"titleNode\" dojoAttachEvent=\"onClick: onTitleClick\" class=\"dojoTreeNodeLabelTitle\">${this.title}</span> " + "</span> " + "<span class=\"dojoTreeNodeAfterLabel\" dojoAttachPoint=\"afterLabelNode\">${this.afterLabel}</span> " + "<div dojoAttachPoint=\"containerNode\" style=\"display:none\"></div> " + "</div>");
    dojo.widget.TreeSelector.prototype.select = function(message){
        var node = message.source;
        setEventTreeDiv(node);
        if (this.selectedNode) {
            this.deselect();
        }
        this.doSelect(node);
        var e = message.event;
        // alert(e.target.classNames());
        if (e.target.classNames() == 'dojoTreeNodeLabelTitle'||e.target.classNames() =='dojoTreeNodeLabelTitle dojoTreeNodeLabelSelected'|| e.target.tagName=="IMG"){

            if(node.link != null && node.link != undefined && node.link != ''){
                if (node.link=="#"){
                    return ;
                }
                else {
                    gotoAction(target, node.link);
                }
            }
        }else{
            if (hasCheckbox){
                check(node.widgetId);
            }
        }
    }
    dojo.widget.TreeNode.prototype.onTitleClick = function (e) {
        dojo.event.topic.publish(this.tree.eventNames.titleClick, {
            source:this,
            event:e
        });
        if (selectedIconTree != null)
        {
            if (selectedIconTree.isFolder)
            {
                selectedIconTree.childIcon.src = contextPath + "/share/img/TreeFolderCloseIcon.gif";
            }
        }
        selectedIconTree = this;
        if (this.isFolder)
        {
            this.childIcon.src = contextPath + "/share/img/TreeFolderOpenIcon.gif";
            if (!this.isExpanded)
            {
                this.destroyChildren();
                var myRpcController = dojo.widget.byId(treeId + '_treeController');
                var actionUrl = "";
                if (action.indexOf("?", 0)!= -1){
                    actionUrl = action +'&' + actionParamName + '=' + this.objectId + '&isCheck='+this.isCheck;
                }else{
                    actionUrl = action +'?' + actionParamName + '=' + this.objectId + '&isCheck='+this.isCheck;
                }
                myRpcController.RPCUrl = actionUrl;
                myRpcController.expand(this);
            }
        }
    }
    dojo.widget.TreeNode.prototype.onIconClick = function (e) {
        if (selectedIconTree != null)
        {
            if (selectedIconTree.isFolder)
            {
                selectedIconTree.childIcon.src = contextPath + "/share/img/TreeFolderCloseIcon.gif";
            }
        }
        if (this.isFolder)
        {
            this.childIcon.src = contextPath + "/share/img/TreeFolderOpenIcon.gif";
            selectedIconTree = this;
            if (!this.isExpanded)
            {
                this.destroyChildren();
                var myRpcController = dojo.widget.byId(treeId + '_treeController');
                var actionUrl = "";
                if (action.indexOf("?", 0)!= -1){
                    actionUrl = action +'&' + actionParamName + '=' + this.objectId + '&isCheck='+this.isCheck;
                }else{
                    actionUrl = action +'?' + actionParamName + '=' + this.objectId + '&isCheck='+this.isCheck;
                }
                myRpcController.RPCUrl = actionUrl;
                myRpcController.expand(this);
            }
        }
        dojo.event.topic.publish(this.tree.eventNames.iconClick, {
            source:this,
            event:e
        });
    }

    var treeDat = {

        treeNodes: [

        {

            objectId:rootId,

            title:rootTitle,

            isFolder:true,

            link:rootLink,

            isCheck:false

        }



        ]

    };



    var TreeBuilder = {
        
        buildTreeNodes:function (dataObjs, treeParentNode){

            for(var i=0; i<dataObjs.length;i++){

                if (dataObjs[i].isCheck){
                    checkbox = '<input class=treeCheckBox name='+cbName+' checked value='+dataObjs[i].objectId+' id=\'cb_'+dataObjs[i].objectId+'\' type=\'checkbox\' onclick=check(\''+dataObjs[i].objectId+'\');>';
                }
                else{
                    checkbox = '<input class=treeCheckBox name='+cbName+' value='+dataObjs[i].objectId+' id=\'cb_'+dataObjs[i].objectId+'\' type=\'checkbox\' onclick=check(\''+dataObjs[i].objectId+'\');>';
                }
                var node = null;
                if (dataObjs[i].isFolder)
                {
                    var myChildIconSrc = contextPath + "/share/img/TreeFolderCloseIcon.gif";
                }
                else
                {
                    var myChildIconSrc = contextPath + "/share/img/TreeLeafIcon.gif";
                }
                if (!hasCheckbox){

                    node = dojo.widget.createWidget("TreeNode",{

                        title       : dataObjs[i].title,

                        objectId    : dataObjs[i].objectId,

                        link        : dataObjs[i].link,

                        isFolder    : dataObjs[i].isFolder,

                        widgetId    : dataObjs[i].objectId,

                        childIconSrc : myChildIconSrc

                    });

                }else{

                    node = dojo.widget.createWidget("TreeNode",{

                        title       : checkbox + " " + dataObjs[i].title,

                        objectId    : dataObjs[i].objectId,

                        link        : dataObjs[i].link,

                        isFolder    : dataObjs[i].isFolder,

                        widgetId    : dataObjs[i].objectId,

                        isCheck     : dataObjs[i].isCheck,

                        childIconSrc : myChildIconSrc

                    });
                }
                treeParentNode.addChild(node);
                if(dataObjs[i].children){

                    this.buildTreeNodes(dataObjs[i].children, node);

                }

            }

        },

        buildTree:function (){

            var myTreeSelector = dojo.widget.byId(treeId+'_treeSelector');

            if (myTreeSelector == undefined){

                myTreeSelector = dojo.widget.createWidget( "TreeSelector",
                {
                    widgetId           :  treeId+ "_treeSelector"
                });
            }
            var myTreeWidget = dojo.widget.byId(treeId);

            if (myTreeWidget == undefined){

                myTreeWidget = dojo.widget.createWidget("Tree",{

                    widgetId : treeId,

                    selector : treeId + "_treeSelector"

                //expandIconSrcPlus  : contextPath + "/share/img/arrow_1.gif",

                //expandIconSrcMinus : contextPath + "/share/img/arrow_down.gif",

                //iconWidth          : "16",

                //iconHeight         : "16"
                });
            }
            myTreeWidget.destroyChildren();
            
            this.buildTreeNodes(treeDat.treeNodes,myTreeWidget);
            // Datbt
            // neu khi load, not goc duoc expand
            if (isExpandedLevel1)
            {
                var myRpcController = dojo.widget.byId(treeId + '_treeController');

                if(myRpcController == undefined){

                    myRpcController = dojo.widget.createWidget("TreeRPCController",{
                        widgetId: treeId + "_treeController"
                    });
                }
                for (var i = 0; i < treeDat.treeNodes.length; i++)
                {
                    var node = dojo.widget.byId(treeDat.treeNodes[i].objectId);

                    var actionUrl = "";
                    if (action.indexOf("?", 0)!= -1){
                        actionUrl = action +'&' + actionParamName + '=' + node.objectId + '&isCheck='+node.isCheck;
                    }else{
                        actionUrl = action +'?' + actionParamName + '=' + node.objectId + '&isCheck='+node.isCheck;
                    }
                    myRpcController.RPCUrl = actionUrl;
                    myRpcController.expand(node);
                }
            }
            var treeContainer = document.getElementById(containerId);
            var placeHolder = document.getElementById(loadingContainerId);
            if (placeHolder != null){
                treeContainer.replaceChild(myTreeWidget.domNode,placeHolder);
            }
            if(document.getElementById(containerId).childNodes[1]!=null){
                dojo.html.setStyleAttributes(document.getElementById(containerId).childNodes[1],"overflow: visible");
            }
        }
    }

    dojo.addOnLoad(function(){

        dojo.require("dojo.lang.*");

        dojo.require("dojo.widget.Tree");

        dojo.require("dojo.widget.TreeNode");

        dojo.require("dojo.widget.TreeRPCController");

        var treeContainer = document.getElementById(containerId);

        TreeBuilder.buildTree();

        var myRpcController = dojo.widget.byId(treeId + '_treeController');

        if(myRpcController == undefined){

            myRpcController = dojo.widget.createWidget("TreeRPCController",{

                widgetId: treeId + "_treeController"

            });
        }
        
        myRpcController.loadProcessResponse = function(node, result, callObj, callFunc){

            setEventTreeDiv(node);
            if (!dojo.lang.isUndefined(result.error)) {

                this.RPCErrorHandler("server", result.error);

                return false;

            }

            var newChildren = result;

            if (!dojo.lang.isArray(newChildren)) {

                dojo.raise('loadProcessResponse: Not array loaded: '+newChildren);

            }
            
            for(var i=0; i<newChildren.length; i++) {

                // looks like dojo.widget.manager needs no special "add" command

                if (newChildren[i].isCheck){

                    checkbox = '<input class=treeCheckBox name=\'' + cbName +'\' checked value='+newChildren[i].objectId+' id=\'cb_'+newChildren[i].objectId+'\' type=\'checkbox\' />';
                    
                }

                else{

                    checkbox = '<input class=treeCheckBox name=\'' + cbName +'\' value='+newChildren[i].objectId+' type=\'checkbox\' id=\'cb_'+newChildren[i].objectId+'\' />';

                }
                var bFolder = (newChildren[i].isFolder == "true")?true:false;
                if (bFolder)
                {
                    var myChildIconSrc = contextPath + "/share/img/TreeFolderCloseIcon.gif";
                }
                else
                {
                    myChildIconSrc = contextPath + "/share/img/TreeLeafIcon.gif";
                }
                if (!hasCheckbox){

                    newChildren[i] = dojo.widget.createWidget("TreeNode",{

                        title       : newChildren[i].title,

                        objectId    : newChildren[i].objectId,

                        link        : newChildren[i].link,

                        isFolder    : bFolder,

                        widgetId    : newChildren[i].objectId,

                        childIconSrc : myChildIconSrc

                    });

                }else{

                    newChildren[i] = dojo.widget.createWidget("TreeNode",{

                        title       : checkbox + " " + newChildren[i].title,

                        objectId    : newChildren[i].objectId,

                        link        : newChildren[i].link,

                        isFolder    : bFolder,

                        widgetId    : newChildren[i].objectId,

                        isCheck     : newChildren[i].isCheck,

                        childIconSrc : myChildIconSrc

                    });

                }

                node.addChild(newChildren[i]);
            }
            if (selectedIconTree != null)
            {
                if (selectedIconTree.isFolder)
                {
                    selectedIconTree.childIcon.src = contextPath + "/share/img/TreeFolderCloseIcon.gif";
                }
            }
            node.childIcon.src = contextPath + "/share/img/TreeFolderOpenIcon.gif";
            selectedIconTree = node;
            node.state = node.loadStates.LOADED;

            if (dojo.lang.isFunction(callFunc)) {
                callFunc.apply(dojo.lang.isUndefined(callObj) ? this : callObj, [node, newChildren]);
            }
        }
        myRpcController.onTreeClick = function(message){
            var node = message.source;
            if (message.event.target.src.indexOf("treenode_loading.gif") != -1)
            {
                return;
            }
            if (hasCheckbox){
                lazyLoad = true;
            }
            if(!lazyLoad){
                node.state = node.loadStates.UNCHECKED;
            }
            var actionUrl = "";
            if (action.indexOf("?", 0)!= -1){
                actionUrl = action +'&' + actionParamName + '=' + node.objectId + '&isCheck='+node.isCheck;
            }else{
                actionUrl = action +'?' + actionParamName + '=' + node.objectId + '&isCheck='+node.isCheck;
            }
            node.lock();
            if (node.isExpanded){
                myRpcController.RPCUrl = actionUrl;
                if (!lazyLoad){
                    node.destroyChildren();
                }
                this.expand(node);
            } else {
                this.collapse(node);
            }
            node.unlock();
        };

        var treeContainer = document.getElementById(containerId);

        //   Tao treeNode tu gia tri tra ve

        treeContainer.appendChild(myRpcController.domNode);

        myRpcController.listenTree(dojo.widget.manager.getWidgetById(treeId));
    });

    function check(node){

        // Check xem node nay co phai la nut cha ko? new fai thi check het cho nut con

        //if (node_tmp.children.length > 0){

        // Neu check vao nut cha

        if (document.getElementById("cb_" + node).checked){

            //alert("da check");

            // co check

            checkChild(node,true);

            // Dem xem nut cha da duoc check het chua?

            countCheck(node);
        }
        else{
            checkChild(node, false);
            checkParent(node,false);
        }
    }

    function countCheck(node){

        var node_tmp = getParentNode(node);

        if (node_tmp != null&& node_tmp.widgetId != treeId){

            for (var i=0; i < node_tmp.children.length; i++){

                node_tmp_child = node_tmp.children[i];

                if (!node_tmp_child.isCheck){

                    return false;

                }

            }

            document.getElementById("cb_" + node_tmp.widgetId).checked = true;

            dojo.widget.byId(node_tmp).isCheck = true;

            countCheck(node_tmp);

        }
        return true;
    }

    function checkChild(node,checkValue){

        var node_tmp = dojo.widget.byId(node);

        document.getElementById("cb_" + node_tmp.widgetId).checked = checkValue;

        dojo.widget.byId(node_tmp).isCheck = checkValue;

        if (node_tmp.children.length > 0){

            for (var i=0; i < node_tmp.children.length; i++){

                var node_tmp_child = node_tmp.children[i];

                checkChild(node_tmp_child.widgetId,checkValue);

            }

        }

    }

    function checkParent(node,checkValue){

        var node_tmp = getParentNode(node);

        //myTreeWidget

        if (node_tmp != null){

            if(node_tmp.widgetId != treeId){

                // day chua phai la node root, bo check

                dojo.widget.byId(node_tmp).isCheck = checkValue;

                document.getElementById("cb_" + node_tmp.widgetId).checked = checkValue;

                checkParent(node_tmp,checkValue);

            }

        }

    }

    function getParentNode(node){

        return (dojo.widget.byId(node).parent!=undefined?dojo.widget.byId(node).parent:null);

    }

}

function notify(source, target, eventName, url){

    dojo.widget.byId(target).dataUrl=url + '?' + source + '='+ document.getElementById(source).value;

    dojo.event.topic.publish(eventName);

}

function notifyEvent(source,value, target, eventName, url){

    dojo.widget.byId(target).dataUrl=url + '?' + source + '='+ value;

    dojo.event.topic.publish(eventName);

}

function clearWidget(){

    for(var o in dojo.event.topic.topics){

        switch (o){

            case 'gotoMenu':

                break;

            case 'dojo.widget.Editor2::onLoad':

                break;

            case 'gotoURL':

                break;

            case 'errorAction':

                break;

            case 'updateBodyContent':

                break;

            default:
                if(dojo.event.topic.getTopic(o).sendMessage$joinpoint != undefined)
                {
                    for(i=0; i<dojo.event.topic.getTopic(o).sendMessage$joinpoint.length; i++){
                        var _joinPoint = dojo.event.topic.getTopic(o).sendMessage$joinpoint.after[i][1];
                        _joinPoint = _joinPoint + "$joinpoint";
                        window[_joinPoint] = null;
                    }
                    dojo.event.topic.destroy(o);
                }
                break;
        }
    }



    for(var wg in dojo.widget.widgetIds){

        switch (wg){

            case 'bodyContent':

                break;

            case 'controlMenu':

                break;

            case 'controlAction':

                break;

            default:
                if (dojo.widget.byId(wg).domNode!= undefined)
                {
                    if (dojo.widget.byId(wg).domNode.onclick$joinpoint != undefined)
                        _joinPoint = dojo.widget.byId(wg).domNode.onclick$joinpoint.after[0][1];
                    _joinPoint = _joinPoint + "$joinpoint";
                    window[_joinPoint] = null;
                }
                dojo.widget.removeWidgetById(wg);
                break;

        }
    }
}

function clearWidgetByArea(area){

    if (dojo.widget.manager.getWidgetById(area)!= undefined)
    {

        var unloadPanel = dojo.widget.manager.getWidgetById(area);

        // Xoa tat ca cac client object cua no

        for (i = 0; i< unloadPanel.children.length;i++)
        {
            wg = unloadPanel.children[i];

            if (wg.popupWidget != undefined)
            {
                var popupWidgetId = wg.popupWidget.widgetId;
                dojo.widget.manager.removeById(popupWidgetId);
            }
            if (wg.popup != undefined)
            {
                popupWidgetId = wg.popup.widgetId;
                dojo.widget.manager.removeById(popupWidgetId);
            }
            if (wg.domNode.onclick$joinpoint != undefined)
            {
                var _joinPoint = wg.domNode.onclick$joinpoint.after[0][1];
                _joinPoint = _joinPoint + "$joinpoint";
                window[_joinPoint] = null;
            }
        }
        unloadPanel.destroyChildren();
    }
}

function clearTopicInArea(area){

    for(var o in dojo.event.topic.topics){

        if (dojo.event.topic.getTopic(o).area != undefined){

            if (dojo.event.topic.getTopic(o).area == area){

                dojo.event.topic.destroy(o);

            }

        }

    }

}

function setAction(widget,area,action){

    clearWidgetByArea(area);

    widget.href = action;

}
/*
function gotoAction(area,action){


    clearWidgetByArea(area);

    var widget = dojo.widget.byId("controlAction");
    if(widget== undefined){
        setTimeout('gotoAction(\''+area+'\',\''+action+'\')',1000);
        return;
    }
    clearBindTag(widget);
    widget.href = action;

    widget.targets=area;

    widget.targetsArray =[area];

    dojo.event.topic.publish('gotoURL')
}
*/
function clearBindTag(widget)
{
    widget.href = null;
    widget.formNode = null;
    widget.formId =null;
}
function gotoAction(area,action,formId){

    clearWidgetByArea(area);

    var widget = dojo.widget.byId("controlAction");
    if(widget== undefined){
        setTimeout('gotoAction(\''+area+'\',\''+action+'\')',1000);
        return;
    }
    clearBindTag(widget);
    if (formId != undefined)
    {
        var _formNode = document.getElementById(formId);
        widget.formId = formId;
        widget.formNode = _formNode;
    }
    widget.href = action;
    widget.targets=area;
    widget.targetsArray =[area];
    dojo.event.topic.publish('gotoURL')
}

function registerTopic(area,topicName,action){



    dojo.event.topic.subscribe(topicName, function(event, widget){

        widget.href = action;

    });

    dojo.event.topic.getTopic(topicName).area = area;

}

function test(area){

    var unloadPanel = dojo.widget.byId(area);

    // Xoa tat ca cac client object cua no

    alert(unloadPanel.children.length);

    clearTopicInArea('bodyContent');

}
var arrElementToFocus = new Array();
function initWidget(){

    
    dojo.event.topic.subscribe("errorAction", function(error, request, widget){

        //alert(error.message + ' ::::: ' + request.responseText);

        widget.targets="noTarget";

        widget.targetsArray = ["noTarget"];
        var win = window.open('', 100, config='height=800,width=900, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, directories=no, status=no');
        win.document.write(request.responseText);
    });

    dojo.event.topic.subscribe("updateBodyContent", function(error,request, widget){

        //dojo.widget.byId("controlMenu").;
        widget.targets="bodyContent";

        widget.targetsArray =["bodyContent"];

        //unlock man hinh
        resetProgress();

    });

    dojo.event.topic.subscribe = function (topic, obj, funcName) {
        var topicName = topic;
        var numbWidgets;
        var topic = this.getTopic(topic);
        topic.subscribe(obj, funcName);

        if(dojo.event.topic.getTopic(topicName).sendMessage$joinpoint != undefined)
        {
            numbWidgets = dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after.length;
            if(numbWidgets >=2)
            {
                var typeObject = dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after[numbWidgets-1][0];
                for (i =0; i<= numbWidgets -2;i++)
                {
                    if (dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after[i][0]== typeObject)
                    {
                        var _joinPoint = dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after[i][1];
                        _joinPoint = _joinPoint + "$joinpoint";
                        window[_joinPoint] = null;
                        dojo.event.topic.getTopic(topicName).sendMessage$joinpoint.after.splice(i, 1);
                        break;
                    }
                }
            }
        }
    };
   
    //overide dojo function
    dojo.widget.buildWidgetFromParseTree = function (type, frag, parser, parentComp, insertionIndex, localProps) {
        var myWidgetId = "";
        dojo.a11y.setAccessibleMode();
        var stype = type.split(":");
        stype = (stype.length == 2) ? stype[1] : type;
        ///
        if (stype == "bindevent")
        {
            myWidgetId = frag.id;
            for (var i = 0; i < dojo.widget.manager.widgets.length; i++)
            {
                if (myWidgetId == dojo.widget.manager.widgets[i].widgetId)
                {
                    dojo.widget.manager.widgets.splice(i,1);
                    break;
                }
            }
        }
        else
        {
            if (frag[frag["ns"] + ":" + stype].id != undefined)
            {
                myWidgetId = frag[frag["ns"] + ":" + stype].id[0].value;
                if (dojo.widget.manager.getWidgetById(myWidgetId) != undefined)
                {
                    myWidgetId = frag[frag["ns"] + ":" + stype].id[0].value;
                    var wg = dojo.widget.manager.getWidgetById(myWidgetId);
                    if (wg != undefined)
                    {
                        if (wg.domNode.onclick$joinpoint != undefined)
                        {
                            var _joinPoint = wg.domNode.onclick$joinpoint.after[0][1];
                            _joinPoint = _joinPoint + "$joinpoint";
                            window[_joinPoint] = null;
                        }
                        dojo.widget.manager.removeById(myWidgetId);
                    }
                }
            }
        }
        ///
        var localProperties = localProps || parser.parseProperties(frag[frag["ns"] + ":" + stype]);
        var twidget = dojo.widget.manager.getImplementation(stype, null, null, frag["ns"]);
        if (!twidget) {
            throw new Error("cannot find \"" + type + "\" widget");
        } else {
            if (!twidget.create) {
                throw new Error("\"" + type + "\" widget object has no \"create\" method and does not appear to implement *Widget");
            }
        }
        localProperties["dojoinsertionindex"] = insertionIndex;
        var ret = twidget.create(localProperties, frag, parentComp, frag["ns"]);
        return ret;
    };
    // Datbt edit DateTimePicker
    dojo.widget.DropdownContainer.prototype.templateString = "<span style=\"white-space:nowrap\"><input type=\"hidden\"  name=\"\" value=\"\" dojoAttachPoint=\"valueNode\" /><input name=\"\" type=\"text\" onkeypress=\"onDataPickerKeyPress(event)\" value=\"\" style=\"vertical-align:middle;\" dojoAttachPoint=\"inputNode\" autocomplete=\"off\" /> <img src=\"${this.iconURL}\" alt=\"${this.iconAlt}\" dojoAttachEvent=\"onclick:onIconClick\" dojoAttachPoint=\"buttonNode\" style=\"vertical-align:middle; cursor:pointer; cursor:hand\" /></span>";
    //
    //    Datbt, comment for IM
    //    dojo.widget.DropdownDatePicker.prototype.onInputChange=function(){
    //        var input = dojo.string.trim(this.inputNode.value);
    //        if (input) {
    //            var inputDate = dojo.date.parse(input, {
    //                formatLength:this.formatLength,
    //                datePattern:this.displayFormat,
    //                selector:"dateOnly",
    //                locale:this.lang
    //                });
    //            if (!this.datePicker._isDisabledDate(inputDate)&& inputDate!=null) {
    //                this.setDate(inputDate);
    //            }
    //            else{
    //                return;
    //            }
    //        } else {
    //            if (input == "") {
    //                //this.datePicker.setDate("");
    //            }
    //            this.valueNode.value = input;
    //        }
    //        if (input) {
    //            this._updateText();
    //        }
    //    }
    //    }
    dojo.widget.DropdownDatePicker.prototype.onInputChange=function(){
        var input = dojo.string.trim(this.inputNode.value);
        //[datbt
        try{
            var _displayFormat ="";
            if (this.displayFormat!=""){
                _displayFormat = this.displayFormat;
            }
            else{
                _displayFormat= "dd/mm/yy";
            }
            _displayFormat = _displayFormat.toLowerCase();
            var beforeEditInput = input;
            for (var i=0;i<_displayFormat.length;i++){
                var d=0;
                if ( ( _displayFormat.charAt(i)!='d' )
                    && ( _displayFormat.charAt(i)!='m' )
                    && ( _displayFormat.charAt(i)!='y' )
                    && ( _displayFormat.charAt(i)!='h' )
                    && ( _displayFormat.charAt(i)!='s' ) ){
                    if (input.indexOf( _displayFormat.charAt(i)) != -1){
                        input = beforeEditInput;
                        break;
                    }else{
                        if ( ( i+d < input.length ) && ( _displayFormat.charAt(i) != input.charAt(i+d).toLowerCase() ) ){
                            input = input.substring(0,i+d) + _displayFormat.charAt(i) + input.substring(i+d,input.length) ;
                            d++;
                        }
                    }
                }
            }
        }catch(e){
            alert(e.message);
        }
        //]Datbt
        if (input) {
            var inputDate = dojo.date.parse(input, {
                formatLength:this.formatLength,
                datePattern:this.displayFormat,
                selector:"dateOnly",
                locale:this.lang
            });
            //[Datbt
            if (inputDate == null){
                alert("Sai định dạng ngày tháng");
//                this.inputNode.focus();
//                return true;
            }
            //]Datbt
            if (!this.datePicker._isDisabledDate(inputDate) && inputDate!=null) {
                this.setDate(inputDate);
            }
        } else {
            if (input == "") {
            //this.datePicker.setDate("");
            }
            this.valueNode.value = input;
        }
        //[Datbt
        if (inputDate < this.datePicker.startDate || inputDate > this.datePicker.endDate) {
            alert("Ngày tháng quá lớn hoặc quá nhỏ");
            this.inputNode.focus();
            return true;
        }
        //]Datbt
        if (input) {
            this._updateText();
        }
    }
    onDataPickerKeyPress= function(evt)
    {
        var event = ( window.event ) ? window.event : evt;
        if(event.keyCode == 40||event.keyCode==38)
        {
            var wg =dojo.widget.getWidgetById(event.target.parentNode.id);
            if (!wg.popup.isShowingNow) {
                wg.popup.open(wg.inputNode, wg, wg.buttonNode);
            } else {
                wg.popup.close();
            }
        }
    }
    // end edit DateTimePicker
    
    
    // edit Bind
    
    struts.widget.Bind.prototype.bindHandler = function(type, data, e) {
        dojo.html.hide(this.indicator);
        //dialog.closeLoadingDialog();
        
        //publish topics
        this.notify(data, type, e);

        if(type == "load") {
            if(this.validate) {
                StrutsUtils.clearValidationErrors(this.formNode);
                //validation is active for this action
                var errors = StrutsUtils.getValidationErrors(data);
                if(errors && errors.fieldErrors) {
                    //validation failed
                    StrutsUtils.showValidationErrors(this.formNode, errors);
                    return;
                } else {
                    //validation passed
                    if(!this.ajaxAfterValidation && this.formNode) {
                        //regular submit
                        this.formNode.submit();
                        return;
                    }
                }
            }

            // no validation or validation passed
            if(this.executeScripts) {
                //parse text to extract content and javascript
                var parsed = this.parse(data);
                if (this.targets != null)
                {
                    clearWidgetByArea(this.targets);
                }
                //update targets content
                this.setContent(parsed.text);

                //execute scripts
                this._executeScripts(parsed.scripts);
            }
            else {
                if (this.targets != null)
                {
                    clearWidgetByArea(this.targets);
                }
                this.setContent(data);
            }
            // Datbt
            arrElementToFocus = new Array();
            var arrAllElement = document.getElementsByTagName('*');
            if (arrAllElement!=undefined)
            {
                for (var index in arrAllElement)
                {
                    var _element = arrAllElement[index];
                    if(_element != undefined)
                    {
                        if (_element.tagName!=undefined)
                        {
                            if ((_element.tagName == "INPUT") || (_element.tagName == "SELECT") || (_element.tagName == "TEXTAREA"))
                            {
                                if ((_element.clientHeight != 0) && (_element.clientWidth != 0))
                                {
                                    arrElementToFocus.push(_element);
                                }
                            }
                        }
                    }
                }
            }
            // datbt end

            this.highlight();
        } else {
            if(this.showError) {
                var message = dojo.string.isBlank(this.errorText) ? e.message : this.errorText;
                this.setContent(message);
            }
        }
    }
    // end edit Bind

    // edit ComboBox
   
    struts.widget.ComboBox.prototype.showResultList= function() {
        // Our dear friend IE doesnt take max-height so we need to calculate that on our own every time
        var childs = this.optionsListNode.childNodes;
        if(childs.length){
            
            if(this.itemHeight === 0 || dojo.string.isBlank(this.textInputNode.value)) {
                this.optionsListNode.style.height = this.dropdownHeight + "px";
                this.optionsListNode.style.display = "";
                this.itemHeight = dojo.html.getMarginBox(childs[0]).height;
            }

            //if there is extra space, adjust height
            var totalHeight = this.itemHeight * childs.length;
            if(totalHeight < this.dropdownHeight) {
                this.optionsListNode.style.height = totalHeight + 2 + "px";
            } else {
                this.optionsListNode.style.height = this.dropdownHeight + "px";
            }
            // Datbt
            var span = document.createElement("span");
            dojo.html.addClass(span, "dojoComboBoxOptions");
            span.innerHTML = this.optionsListNode.childNodes[0].innerHTML;
            document.body.appendChild(span); // adding it to the DOM
            // chieu rong toi da cua popup
            var _maxPopUpWidth = 600;
            // so phan tu toi da cua list se duyet do dai
            var _maxListLength = 30;
            // xet xem co scrollBar ko; neu co thi phai cong them do dai
            var _scrollBarWidth = (childs.length > this.maxListLength) ? 18 : 2;
            var _maxTextWidth = span.offsetWidth + _scrollBarWidth;
            document.body.removeChild(span);
            var _listLength = (_maxListLength > childs.length)? childs.length : _maxListLength;
            for (var k = 0; k <_listLength; k++)
            {
                var span = document.createElement("span");
                dojo.html.addClass(span, "dojoComboBoxOptions");
                span.innerHTML = this.optionsListNode.childNodes[k].innerHTML;

                document.body.appendChild(span);
                if (_maxTextWidth < span.offsetWidth + _scrollBarWidth)
                {
                    _maxTextWidth = span.offsetWidth + _scrollBarWidth;
                }
                document.body.removeChild(span);
            }
            _maxTextWidth = _maxTextWidth;
            
            var _popUpWidth = (_maxTextWidth > dojo.html.getMarginBox(this.domNode).width-2) ? _maxTextWidth:dojo.html.getMarginBox(this.domNode).width-2;
            _popUpWidth = (_popUpWidth < _maxPopUpWidth)? _popUpWidth : _maxPopUpWidth;
            this.optionsListNode.style.width = _popUpWidth+"px";
            //this.optionsListNode.style.width = this.dropdownWidth === 0 ? (dojo.html.getMarginBox(this.domNode).width-2)+"px" : this.dropdownWidth + "px";
            // Datbt end

            this.popupWidget.open(this.domNode, this, this.downArrowNode);
        } else {
            this._hideResultList();
        }
    }
    
    struts.widget.ComboBoxDataProvider = function(combobox, node){
        this.data = [];
        this.searchLimit = combobox.searchLimit;
        this.searchType = "STARTSTRING"; // may also be "STARTWORD" or "SUBSTRING"
        this.caseSensitive = false;
        // for caching optimizations
        this._lastSearch = "";
        this._lastSearchResults = null;

        this.firstRequest = true;

        this.cbox = combobox;
        this.formId = this.cbox.formId;
        this.formFilter = this.cbox.formFilter;
        this.transport = this.cbox.transport;

        this.getData = function(/*String*/ url){
            //show indicator
            dojo.html.show(this.cbox.indicator);

            dojo.io.bind({
                url: url,
                formNode: dojo.byId(this.formId),
                formFilter: window[this.formFilter],
                transport: this.transport,
                handler: dojo.lang.hitch(this, function(type, data, evt) {
                    //hide indicator
                    dojo.html.hide(this.cbox.indicator);

                    //if notifyTopics is published on the first request (onload)
                    //the value of listeners will be reset
                    if(!this.firstRequest || type == "error") {
                        this.cbox.notify.apply(this.cbox, [data, type, evt]);
                    }

                    this.firstRequest = false;
                    var arrData = null;
                    var dataByName = data[dojo.string.isBlank(this.cbox.dataFieldName) ? this.cbox.name : this.cbox.dataFieldName];
                    if(!dojo.lang.isArray(data)) {
                        //if there is a dataFieldName, take it
                        if(dataByName) {
                            if(dojo.lang.isArray(dataByName)) {
                                //ok, it is an array
                                arrData = dataByName;
                            } else if(dojo.lang.isObject(dataByName)) {
                                //it is an object, treat it like a map
                                arrData = [];
                                for(var key in dataByName){
                                    //arrData.push([key, dataByName[key]]);
                                    //Datbt
                                    arrData.push([dataByName[key],key]);
                                }
                            }
                        } else {
                            //try to find a match
                            var tmpArrData = [];
                            if (data.message == undefined)
                            {
                                for(var key in data){
                                    //does it start with the field name? take it
                                    if(dojo.string.startsWith(key, this.cbox.name)) {
                                        arrData = data[key];
                                        break;
                                    } else {
                                        //if nathing else is found, we will use values in this
                                        //object as the data
                                        //tmpArrData.push([key, data[key]]);
                                        //Datbt
                                        tmpArrData.push([data[key],key]);
                                    }
                                    //grab the first array found, we will use it if nothing else
                                    //is found
                                    if(!arrData && dojo.lang.isArray(data[key]) && !dojo.lang.isString(data[key])) {
                                        arrData = data[key];
                                    }
                                }
                            }
                            if(!arrData) {
                                arrData = tmpArrData;
                            }
                        }

                        data = arrData;
                    }
                    this.setData(data);
                }),
                mimetype: "text/json"
            });
        };

        this.startSearch = function (searchStr, callback) {
            // FIXME: need to add timeout handling here!!
            this._preformSearch(searchStr, callback);
        };

        this._preformSearch = function(/*String*/ searchStr, callback){
            //
            //  NOTE: this search is LINEAR, which means that it exhibits perhaps
            //  the worst possible speed characteristics of any search type. It's
            //  written this way to outline the responsibilities and interfaces for
            //  a search.
            //
            var st = this.searchType;
            // FIXME: this is just an example search, which means that we implement
            // only a linear search without any of the attendant (useful!) optimizations
            var ret = [];
            if(!this.caseSensitive){
                searchStr = searchStr.toLowerCase();
            }
            for(var x=0; x<this.data.length; x++){
                if(!this.data[x] || !this.data[x][0]) {
                    //needed for IE
                    continue;
                }
                if((this.searchLimit > 0) && (ret.length >= this.searchLimit)) {
                    break;
                }
                // FIXME: we should avoid copies if possible!
                var dataLabel = new String((!this.caseSensitive) ? this.data[x][0].toLowerCase() : this.data[x][0]);
                if(dataLabel.length < searchStr.length){
                    // this won't ever be a good search, will it? What if we start
                    // to support regex search?
                    continue;
                }

                if(st == "STARTSTRING"){
                    if(searchStr == dataLabel.substr(0, searchStr.length)){
                        ret.push(this.data[x]);
                    }
                }else if(st == "SUBSTRING"){
                    // this one is a gimmie
                    if(dataLabel.indexOf(searchStr) >= 0){
                        ret.push(this.data[x]);
                    }
                }else if(st == "STARTWORD"){
                    // do a substring search and then attempt to determine if the
                    // preceeding char was the beginning of the string or a
                    // whitespace char.
                    var idx = dataLabel.indexOf(searchStr);
                    if(idx == 0){
                        // implicit match
                        ret.push(this.data[x]);
                    }
                    if(idx <= 0){
                        // if we didn't match or implicily matched, march onward
                        continue;
                    }
                    // otherwise, we have to go figure out if the match was at the
                    // start of a word...
                    // this code is taken almost directy from nWidgets
                    var matches = false;
                    while(idx!=-1){
                        // make sure the match either starts whole string, or
                        // follows a space, or follows some punctuation
                        if(" ,/(".indexOf(dataLabel.charAt(idx-1)) != -1){
                            // FIXME: what about tab chars?
                            matches = true;
                            break;
                        }
                        idx = dataLabel.indexOf(searchStr, idx+1);
                    }
                    if(!matches){
                        continue;
                    }else{
                        ret.push(this.data[x]);
                    }
                }
            }
            callback(ret);
        };

        this.addData = function(/*Array*/ pairs){
            // FIXME: incredibly naive and slow!
            this.data = this.data.concat(pairs);
        };

        this.setData = function(/*Array*/ pdata){
            // populate this.data and initialize lookup structures
            this.data = pdata;
            // Datbt
            if (this.data[0]!= undefined)
            {
                //all ellements must be a key and value pair
                for(var i = 0; i < this.data.length; i++) {
                    var element = this.data[i];
                    if(!dojo.lang.isArray(element)) {
                        this.data[i] = [element, element];
                    }
                }
                if (this.cbox.getSelectedKey() != this.data[0][1])
                {
                    this.cbox.selectedResult = null;
                    this.cbox.comboBoxSelectionValue.value = "";
                    this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [null, null, null, this.cbox]);
                }
                if (this.cbox.domNode.childNodes[2].value.toUpperCase() == this.data[0][0] && this.cbox.getSelectedKey() != this.data[0][1] && !this.cbox._hasFocus)
                {
                    this.cbox.selectedResult = [this.data[0][0],this.data[0][1]];
                    this.cbox.comboBoxSelectionValue.value = this.data[0][0];
                    this.cbox.comboBoxValue.value = this.data[0][1];
                    this.cbox.domNode.childNodes[2].value = this.data[0][0];
                    this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [this.data[0][0], this.data[0][1], this.cbox.getText(), this.cbox]);
                }
            }
            else
            {
                if (this.cbox.getSelectedKey() == "")
                {
                //                    this.data = new Array();
                //                    this.cbox.selectedResult = null;
                //                    this.cbox.comboBoxSelectionValue.value = "";
                //                    this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [null, null, null, this.cbox]);
                }
                else
                {
                    if (this.cbox.domNode.childNodes[2].value.toUpperCase() == this.cbox.getSelectedValue())
                    {
                        this.cbox.domNode.childNodes[2].value =  this.cbox.getSelectedValue();
                        this.cbox.notifyTo(this.cbox.valueNotifyTopicsArray, [this.cbox.comboBoxSelectionValue.value, this.cbox.comboBoxSelectionValue.value, this.cbox.comboBoxSelectionValue.value, this.cbox]);
                    }
                }
            }
        // Datbt end
        };


        if(!dojo.string.isBlank(this.cbox.dataUrl) && this.cbox.preload){
            this.getData(this.cbox.dataUrl);
        } else {
            // check to see if we can populate the list from <option> elements
            if((node)&&(node.nodeName.toLowerCase() == "select")){
                // NOTE: we're not handling <optgroup> here yet
                var opts = node.getElementsByTagName("option");
                var ol = opts.length;
                var data = [];
                for(var x=0; x<ol; x++){
                    var text = opts[x].textContent || opts[x].innerText || opts[x].innerHTML;
                    var keyValArr = [ String(text),String(opts[x].value)];
                    // Datbt
                    //var keyValArr = [ String(opts[x].value),String(text)];
                    data.push(keyValArr);
                    if(opts[x].selected){
                        this.cbox.setAllValues(keyValArr[0], keyValArr[1]);
                    }
                }
                this.setData(data);
            }
        }
    }
    dojo.widget.ComboBox.prototype._focusOptionNode=function (node) {
        if (this._highlighted_option != node) {
            this._blurOptionNode();
            this._highlighted_option = node;
            dojo.html.addClass(this._highlighted_option, "dojoComboBoxItemHighlight");
        }
    }
    dojo.widget.ComboBox.prototype._handleKeyEvents=function (evt) {
        if (evt.ctrlKey || evt.altKey || !evt.key) {
            return;
        }
        this._prev_key_backspace = false;
        this._prev_key_esc = false;
        var k = dojo.event.browser.keys;
        var doSearch = true;
        switch (evt.key) {
            case k.KEY_DOWN_ARROW:
                //evt.preventDefault();
                if (!this.popupWidget.isShowingNow) {
                    this._startSearchFromInput();
                }
                this._highlightNextOption();
                dojo.event.browser.stopEvent(evt);
                return;
            case k.KEY_UP_ARROW:
                this._highlightPrevOption();
                dojo.event.browser.stopEvent(evt);
                return;
            case k.KEY_TAB:
                if (!this.autoComplete && this.popupWidget.isShowingNow && this._highlighted_option) {
                    dojo.event.browser.stopEvent(evt);
                    this._selectOption({
                        "target":this._highlighted_option,
                        "noHide":false
                    });
                    this._setSelectedRange(this.textInputNode, this.textInputNode.value.length, null);
                } else {
                    this._selectOption();
                    return;
                }
                break;
            case k.KEY_ENTER:
                if (mystopEvent)
                {
                    mystopEvent = false;
                    return;
                }
                if (this.popupWidget.isShowingNow) {
                    dojo.event.browser.stopEvent(evt);
                }
                if (this.autoComplete) {
                    this._selectOption();
                //return;
                }
                // Datbt
                var bFocus = false;
                for (var i=0; i < arrElementToFocus.length; i++)
                {
                    if (this.domNode.childNodes[2] == arrElementToFocus[i])
                    {
                        bFocus = true;
                        // neu khong phai la phan tu cuoi cung
                        if (i < arrElementToFocus.length -1)
                        {
                            var bFound = false;
                            for (var j = i+1; j < arrElementToFocus.length; j++)
                            {
                                var _element = arrElementToFocus[j];
                                if (!_element.disabled)
                                {
                                    bFound = true;
                                    if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                                    {
                                        mystopEvent = true;
                                    }
                                    _element.focus();
                                    return;
                                }
                            }
                            if (!bFound)
                            {
                                for (var j = 0; j < arrElementToFocus.length; j++)
                                {
                                    var _element = arrElementToFocus[j];
                                    if (!_element.disabled)
                                    {
                                        if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                                        {
                                            mystopEvent = true;
                                        }
                                        _element.focus();
                                        return;
                                    }
                                }
                            }
                            else
                            {
                                break;
                            }
                        }
                        // neu la phan tu cuoi cung
                        else
                        {
                            for (var j = 0; j < arrElementToFocus.length; j++)
                            {
                                var _element = arrElementToFocus[j];
                                if (!_element.disabled)
                                {
                                    if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                                    {
                                        mystopEvent = true;
                                    }
                                    _element.focus();
                                    return;
                                }
                            }
                        }
                    }
                }
                if (!bFocus)
                {
                    for (var j = 0; j < arrElementToFocus.length; j++)
                    {
                        var _element = arrElementToFocus[j];
                        if (!_element.disabled)
                        {
                            if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                            {
                                mystopEvent = true;
                            }
                            _element.focus();
                            return;
                        }
                    }
                }
                break;
            // Datbt end
            case " ":
                if (this.popupWidget.isShowingNow && this._highlighted_option) {
                    dojo.event.browser.stopEvent(evt);
                    this._selectOption();
                    this._hideResultList();
                    return;
                }
                break;
            case k.KEY_ESCAPE:
                this._hideResultList();
                this._prev_key_esc = true;
                return;
            case k.KEY_BACKSPACE:
                this._prev_key_backspace = true;
                if (!this.textInputNode.value.length) {
                    this.setAllValues("", "");
                    this._hideResultList();
                    doSearch = false;
                }
                break;
            // Datbt
            case k.KEY_DELETE:
                this._prev_key_backspace = true;
                if (!this.textInputNode.value.length) {
                    this.setAllValues("", "");
                    this._hideResultList();
                    doSearch = false;
                }
                break;
            // Datbt end
            case k.KEY_RIGHT_ARROW:
            case k.KEY_LEFT_ARROW:
                doSearch = false;
                break;
            default:
                if (evt.charCode == 0) {
                    doSearch = false;
                }
        }
        if (this.searchTimer) {
            clearTimeout(this.searchTimer);
        }
        if (doSearch) {
            this._blurOptionNode();
            this.searchTimer = setTimeout(dojo.lang.hitch(this, this._startSearchFromInput), this.searchDelay);
        }
    }
    
    // Datbt
    // khi xu ly phim enter tren comboBox phai them ham nay de dispath event change value
    dojo.widget.ComboBox.prototype._selectOption=function (evt) {
        var tgt = null;
        if (!evt) {
            evt = {
                target:this._highlighted_option
            };
        }
        if (!dojo.html.isDescendantOf(evt.target, this.optionsListNode)) {
            if (!this.textInputNode.value.length) {
                return;
            }
            tgt = dojo.html.firstElement(this.optionsListNode);
            // Datbt
            // neu input.value != cac gia tri autocompleter thi chon gia tri dau tien
            //            if (!tgt || !this._isInputEqualToResult(tgt.getAttribute("resultName"))) {
            if (!tgt) {
                return;
            }
        } else {
            tgt = evt.target;
        }
        while ((tgt.nodeType != 1) || (!tgt.getAttribute("resultName"))) {
            tgt = tgt.parentNode;
            if (tgt === dojo.body()) {
                return false;
            }
        }
        this.textInputNode.value="";
        this.selectedResult = [tgt.getAttribute("resultName"), tgt.getAttribute("resultValue")];
        this.setAllValues(tgt.getAttribute("resultName"), tgt.getAttribute("resultValue"));
        if (!evt.noHide) {
            this._hideResultList();
            this._setSelectedRange(this.textInputNode, 0, null);
        }
        this._tryFocus();
    }
    dojo.widget.ComboBox.prototype.buttonSrc = null;
    dojo.widget.ComboBox.prototype.templateString = "<span _=\"whitespace and CR's between tags adds &nbsp; in FF\"\n\tclass=\"dojoComboBoxOuter\"\n\t><input style=\"display:none\"  tabindex=\"-1\" name=\"\" value=\"\" \n\t\tdojoAttachPoint=\"comboBoxValue\"\n\t><input style=\"display:none\"  tabindex=\"-1\" name=\"\" value=\"\" \n\t\tdojoAttachPoint=\"comboBoxSelectionValue\"\n\t><input type=\"text\"  autocomplete=\"off\" class=\"dojoComboBox\"\n\t\tdojoAttachEvent=\"key:_handleKeyEvents; keyUp: onKeyUp; compositionEnd; blur: onBlur; onResize;\"\n\t\tdojoAttachPoint=\"textInputNode\"\n\t><img hspace=\"0\"\n\t\tvspace=\"0\"\n\t\tclass=\"dojoComboBox\"\n\t\tdojoAttachPoint=\"downArrowNode\"\n\t\tdojoAttachEvent=\"onMouseUp: handleArrowClick; onResize;\"\n\t\tsrc=\"${this.buttonSrc}\"\n></span>\n";
    // Datbt
    dojo.widget.ComboBox.prototype.onBlur = function()
    {
        if (this.dataProvider.data[0]!= undefined)
        {
            if ( this.getSelectedKey() != this.dataProvider.data[0][1])
            {
                if (this.dataProvider.data[0][0].toUpperCase() == this.domNode.childNodes[2].value.toUpperCase())
                {
                    this.selectedResult = [this.dataProvider.data[0][0],this.dataProvider.data[0][1]];
                    this.domNode.childNodes[2].value = this.dataProvider.data[0][0];
                    this.comboBoxValue.value = this.dataProvider.data[0][0];
                    this.comboBoxSelectionValue.value = this.dataProvider.data[0][1];
                    this.notifyTo(this.valueNotifyTopicsArray, [this.dataProvider.data[0][0],this.dataProvider.data[0][1],this.domNode.childNodes[2].value, this]);
                }
            }
        }
    }
    // Datbt end
    dojo.widget.ComboBox.prototype.onResize = function (){
        var inputSize = dojo.html.getContentBox(this.textInputNode);
        if (inputSize.height <= 0) {
            dojo.lang.setTimeout(this, "onResize", 100);
            return;
        }
        var buttonSize = {
            width:inputSize.height,
            height:inputSize.height
        };
    //dojo.html.setContentBox(this.downArrowNode, buttonSize);
    }
    
    // end edit ComboBox
    struts.widget.Bind.prototype.reloadContents = function(evt) {
        //dialog.showLoadingDialog();
        if(!dojo.string.isBlank(this.handler)) {
            //use custom handler
            this.log("Invoking handler: " + this.handler);
            window[this.handler](this, this.domNode);
        }
        else {
            try {
                var self = this;
                var request = {
                    cancel: false
                };
                this.notify(this.widgetId, "before", request);
                if(request.cancel) {
                    this.log("Request canceled");
                    return;
                }

                //if the href is null, we still publish the notify topics
                if(dojo.string.isBlank(this.href)) {
                    return;
                }

                //if there is a parent form, and it has a "onsubmit"
                //execute it, validation is usually there, except is validation == true
                //on which case it is ajax validation, instead of client side
                if(!this.validate && this.formNode && this.formNode.onsubmit != null) {
                    var makeRequest = this.formNode.onsubmit.call(evt);
                    if(makeRequest != null && !makeRequest) {
                        this.log("Request canceled by 'onsubmit' of the form");
                        return;
                    }
                }

                //show indicator
                //dojo.html.show(this.indicator);
                if(this.showLoading) {
                    this.setContent(this.loadingText);
                }

                var tmpHref = this.href;
                tmpHref = tmpHref + (tmpHref.indexOf("?") > -1 ? "&" : "?") + "struts.enableJSONValidation=true&pageId="+pageId;
                if(!this.ajaxAfterValidation && this.validate) {
                    tmpHref = tmpHref + (tmpHref.indexOf("?") > -1 ? "&" : "?") + "struts.validateOnly=true";
                }

                if(dojo.dom.isTag(this.domNode, "INPUT", "input")
                    && this.events == "onclick"
                    && this.domNode.type == "submit"
                    && !dojo.string.isBlank(this.domNode.name)
                    && !dojo.string.isBlank(this.domNode.value)) {
                    var enc = /utf/i.test("") ? encodeURIComponent : dojo.string.encodeAscii
                    tmpHref = tmpHref + (tmpHref.indexOf("?") > -1 ? "&" : "?") + enc(this.domNode.name) + "=" + enc(this.domNode.value);
                }

                dojo.io.bind({
                    url: tmpHref,
                    useCache: false,
                    preventCache: true,
                    formNode: self.formNode,
                    formFilter: window[self.formFilter],
                    transport: self.transport,
                    handler: function(type, data, e) {
                        dojo.lang.hitch(self, "bindHandler")(type, data, e);
                    },
                    mimetype: "text/html"
                });
            }
            catch(ex) {
                if(this.showError) {
                    var message = dojo.string.isBlank(this.errorText) ? ex : this.errorText;
                    this.setContent(message);
                }
            }
        }
    }
}
//bo sung xu ly cay - dinhvv

function topUpNode(id, contextPath) {

    var treeTable = $('OfferSubTree');

    var trs = treeTable.getElementsByTagName("tr");

    var check = null;

    for( var i = 0; i < trs.length; i++ ) {

        if( trs[i].id  == id ) {

            check = ( trs[i].style.display == 'none' ) ? '' : 'none';

            trs[i].style.display = check;

            if(check == 'none') {

                $('expan_'+id).src = contextPath+ '/share/img/treeview/plusbottom.gif';

                $('folder_'+id).src = contextPath+ '/share/img/treeview/folder.gif';

            } else {

                $('expan_'+id).src = contextPath+ '/share/img/treeview/minusbottom.gif';

                $('folder_'+id).src = contextPath+ '/share/img/treeview/folderopen.gif';

            }

        }

    }
}
function clearAllChilds(domeNode){
    while(domeNode.hasChildNodes()){
        domeNode.removeChild(domeNode.firstChild);
    }
}
function updateChildToCombo( combo, data, originComboWidth ){
    var val, label;
    var brwStr = dom.brwInfo[0].toString().toLowerCase();
    var isIE = ( brwStr.indexOf( "msie" ) != -1 );
    var isIEVer6 = ( brwStr.indexOf( "msie 6.0" ) != -1 );

    for(var i=0;i<data.length;i++){
        val = data[i][0];
        label = data[i][1];
        var option = document.createElement("option");

        if (val != null && val != '')  {
            val= escapeHtml(val);
        }
        
        option.value =val;
        
        if( isIEVer6 ) {
            option.text = escapeHtml(label);
        } else {
            option.innerHTML =  escapeHtml(label);
        }
        
        if (window.XMLHttpRequest) {
            combo.appendChild(option);
        }else{
            combo.add(option);
        }
    }

    if( isIE && !isIEVer6 ) {
        combo.style.width = originComboWidth + 'px';
    }
}

function updateCombo(source,targets,url){
    //ThanhNC sua
    var tmp_url;
    if (url.indexOf("?", 0)!= -1){
        tmp_url = url+'&' + source+ '=' + document.getElementById(source).value;
    }else{
        tmp_url = url+'?' + source+ '=' + document.getElementById(source).value;
    }
    //var tmp_url = url+'?' + source+ '=' + document.getElementById(source).value;
    dojo.io.bind({
        url:tmp_url ,
        error: function(type, data, evt){
            alert("error");
        },
        handler: function(type, data, e) {
            if(dojo.lang.isObject(data)) {
                //it is an object, treat it like a map
                var arrTargets = targets.split(',');
                for(var i=0;i<arrTargets.length;i++){
                    var combo = document.getElementById(arrTargets[i]);
                    var ogCbW = combo.offsetWidth;
                    clearAllChilds(combo);
                    updateChildToCombo(combo, data, ogCbW );
                }
            }
        },
        mimetype: "text/json"
    });
}
function getInputText(url){
    dojo.io.bind({
        url: url,
        error: function(type, data, evt){
        //alert("error");
        },
        handler: function(type, data, e) {
            if(dojo.lang.isArray(data)) {
                //ok, it is an array
                arrData = data;
            } else if(dojo.lang.isObject(data)) {
                //it is an object, treat it like a map
                for(var key in data){
                    if (document.getElementById(key)!= null){
                        document.getElementById(key).value = data[key];
                    }
                }
            }
        },
        mimetype: "text/json"
    });
}

function updateData(url){
    dojo.io.bind({
        url:url ,
        error: function(type, data, evt){
            alert("error");
        },
        handler: function(type, data, e) {
            if(dojo.lang.isObject(data)) {
                
                // Duyet qua data tra ve de tim cac truong can update
                for(var keyValue in data){
                    var obj = document.getElementById(keyValue);
                    if(obj != null){
                        updateElement(obj,data[keyValue]);
                    }
                }
            }
        },
        mimetype: "text/json"
    });
}
function updateElement(obj,data){
//    alert(data);
//    data = escapeHtml(data);
//    alert(data);

    var ogCbW;

    switch (obj.tagName) {
        case 'SELECT':
            ogCbW = obj.offsetWidth;
            clearAllChilds(obj);
            updateChildToCombo(obj, data, ogCbW );
            break;
        case 'TEXTAREA':
            obj.value = data;
            break;
        case 'INPUT':
            if (obj.type=='checkbox'){
                alert('Checkbox');
                obj.checked = true;
            }
            if (obj.type=='text'){
                obj.value = data;
            }
            break;
        default:
            break;
    }

}
// Datbt
var Allow=false;
var mystopEvent = false;
function handleKeyPress(evt) {

    //    var nbr, chr;
    var event = ( window.event ) ? window.event : evt;
    //nbr = window.Event?evt.which: event.keyCode;
    var nbr = event.keyCode;
    //var k = dojo.event.browser.keys;

    if (nbr == 40)
    {
        if (event.target.getAttribute("dojoattachpoint")=="textInputNode")
        {
            
    }
    }
    if(nbr==13 && !Allow)
    {
        mystopEvent = false;
        if (event.target.getAttribute("dojotype")==undefined && event.target.getAttribute("dojoattachpoint")!="textInputNode" && event.target.tagName != "TEXTAREA")
        {
            var bFocus = false;
            for (var i=0; i < arrElementToFocus.length; i++)
            {
                if (event.target == arrElementToFocus[i])
                {
                    bFocus = true;
                    // neu khong phai la phan tu cuoi cung
                    if (i < arrElementToFocus.length -1)
                    {
                        var bFound = false;
                        for (var j = i+1; j < arrElementToFocus.length; j++)
                        {
                            var _element = arrElementToFocus[j];
                            if (!_element.disabled)
                            {
                                bFound = true;
                                if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                                {
                                    mystopEvent = true;
                                }
                                _element.focus();
                                return false;
                            }
                        }
                        if (!bFound)
                        {
                            for (var j = 0; j < arrElementToFocus.length; j++)
                            {
                                var _element = arrElementToFocus[j];
                                if (!_element.disabled)
                                {
                                    if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                                    {
                                        mystopEvent = true;
                                    }
                                    _element.focus();
                                    return false;
                                }
                            }
                        }
                        else
                        {
                            break;
                        }
                    }
                    // neu la phan tu cuoi cung
                    else
                    {
                        for (var j = 0; j < arrElementToFocus.length; j++)
                        {
                            var _element = arrElementToFocus[j];
                            if (!_element.disabled)
                            {
                                if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                                {
                                    mystopEvent = true;
                                }
                                _element.focus();
                                return false;
                            }
                        }
                        break;
                    }
                }
            }
            if (!bFocus)
            {
                for (var j = 0; j < arrElementToFocus.length; j++)
                {
                    var _element = arrElementToFocus[j];
                    if (!_element.disabled)
                    {
                        if (_element.getAttribute("dojoattachpoint")=="textInputNode")
                        {
                            mystopEvent = true;
                        }
                        _element.focus();
                        return false;
                    }
                }
            }
            return false;
        }
        else
        {
            if (event.target.getAttribute("dojoattachpoint")!="textInputNode")
            {
                return event.keyCode;
            }
            else
            {
                return false;
            }
        }
    }
}
function AllowSubmit(X){
    Allow=X;
}
document.onkeydown= handleKeyPress;
// end Datbt

function myGetHTMLElement(strutsId, strustType)
{
    if (strustType == "autocompleter")
    {
        var wg = dojo.widget.byId(strutsId);
        if (wg.domNode.childNodes[2] != undefined)
        {
            return wg.domNode.childNodes[2];
        }
        else
        {
            return null;
        }
    }
}
function sxSubmitDisable(id, bDisabled)
{
    var wg = dojo.widget.byId(id);

    if (bDisabled)
    {
        wg.domNode.disabled = "true";
    }
    else
    {
        wg.domNode.attributes.removeNamedItem("disabled");
    }
}


// [ LongH
// ajax-get-query
function ajax() {
    var areaId, action, param, afterCallback, errorCallback, retType, showLoading;
    var _url;

    switch( arguments.length ) {
        case 2:
            areaId = arguments[0];
            action = arguments[1];
            param = [];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 3:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 4:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 5:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = "text/html";
            showLoading = true;
            break;
        case 6:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = true;
            break;
        case 7:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = arguments[6];
            break;
    }

    _url = action;
    if( param.length > 0 ) {
        _url += "?" + param.join( "&" );
    }

    if( showLoading ) {
        dialog.showLoadingDialog();
    }

    dojo.io.bind({
        useCache:false,
        preventCache:true,
        url:_url,
        error: function( type, data, evt ) {
            if( showLoading ) {
                dialog.closeLoadingDialog();
            }
            
            // [ Display error info
            var str = "<div style='color:red;font-weight:bold;'>Error in Ajax @ LongH: </div>";
            for( var prop in data ) {
                str += prop + " = " + data[prop] + "<br/>";
            }
            dialog.warning( Dialog.SYS_WARNING_DIALOG_TITLE, str );
            // ]
            if( errorCallback != null || errorCallback != undefined ) {
                errorCallback( areaId, data );
            }
        },
        handler: function( type, data, e ) {
            if( showLoading ) {
                dialog.closeLoadingDialog();
            }

            if( areaId != null || areaId != undefined ) {
                byId( areaId ).innerHTML = data;

                var xmlParser = new dojo.xml.Parse();
                var node = dojo.byId( areaId );
                var frag  = xmlParser.parseElement( node, null, true );
                dojo.widget.getParser().createSubComponents( frag, dojo.widget.byId( areaId ) );

                try {
                    var strut = new struts.widget.Bind();
                    strut._executeScripts( strut.parse( data ).scripts );
                } catch( e ) {
                    dialog.warning( "Custom error: " + e.message );
                }
            }

            

            if( afterCallback != null || afterCallback != undefined ) {
                afterCallback( areaId, data );
            }
        },
        mimetype: retType
    });

    // [ inner functions
    function byId( id ) {
        return document.getElementById( id );
    }
// ] inner functions
}

// ajax-post-submit
function ajaxPost() {
    var areaId, action, param, afterCallback, errorCallback, retType, showLoading;
    var _url;

    switch( arguments.length ) {
        case 2:
            areaId = arguments[0];
            action = arguments[1];
            param = [];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 3:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = null;
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 4:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = null;
            retType = "text/html";
            showLoading = true;
            break;
        case 5:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = "text/html";
            showLoading = true;
            break;
        case 6:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = true;
            break;
        case 7:
            areaId = arguments[0];
            action = arguments[1];
            param = arguments[2];
            afterCallback = arguments[3];
            errorCallback = arguments[4];
            retType = arguments[5];
            showLoading = arguments[6];
            break;
    }

    _url = action;
    var cntObj = {};
    var i, tmpArr, code = "";
    if( param.length > 0 ) {
        for( i = 0; i < param.length; i++ ) {
            tmpArr = param[i].split( "=" );
            code += "cntObj[\"" + tmpArr[0] + "\"] = \"" + tmpArr[1] + "\";";
        }

        if( code.length > 0 ) {
            eval( code );
        }
    }

    if( showLoading ) {
        dialog.showLoadingDialog();
    }

    dojo.io.bind({
        useCache:false,
        preventCache:true,
        url:_url,
        method: "post",
        content: cntObj,
        error: function( type, data, evt ) {
            if( showLoading ) {
                dialog.closeLoadingDialog();
            }

            // [ Display error info
            var str = "<div style='color:red;font-weight:bold;'>Error in Ajax @ LongH: </div>";
            for( var prop in data ) {
                str += prop + " = " + data[prop] + "<br/>";
            }
            dialog.warning( Dialog.SYS_WARNING_DIALOG_TITLE, str );
            // ]
            if( errorCallback != null || errorCallback != undefined ) {
                errorCallback( areaId, data );
            }
        },
        handler: function( type, data, e ) {
            if( showLoading ) {
                dialog.closeLoadingDialog();
            }

            if( areaId != null || areaId != undefined ) {
                byId( areaId ).innerHTML = data;

                var xmlParser = new dojo.xml.Parse();
                var node = dojo.byId( areaId );
                var frag  = xmlParser.parseElement( node, null, true );
                dojo.widget.getParser().createSubComponents( frag, dojo.widget.byId( areaId ) );

                try {
                    var strut = new struts.widget.Bind();
                    strut._executeScripts( strut.parse( data ).scripts );
                } catch( e ) {
                    dialog.warning( "Custom error: " + e.message );
                }
            }



            if( afterCallback != null || afterCallback != undefined ) {
                afterCallback( areaId, data );
            }
        },
        mimetype: retType
    });

    // [ inner functions
    function byId( id ) {
        return document.getElementById( id );
    }
// ] inner functions
}

// get dojo stupid datetime
function getDojoDate() {
    var id, obj;
    id = arguments[0];
    obj = dojo.widget.byId( id ).domNode.childNodes[1];

    if( arguments.length == 2 ) {
        return obj;
    }

    return obj.value;
}

// [ Message
function shortcutMsg( shortcutDiv ) {
    var msgDiv = shortcutDiv.parentNode;
    msgDiv.className = msgDiv.orgClassName;
}

function closeMsg( iconDiv ) {
    var msgDiv = iconDiv.parentNode;
    var shortcutDiv = null;

    for( var i = 0; i < msgDiv.childNodes.length; i++ ) {
        if( msgDiv.childNodes[i].className == 'shortcut' ) {
            shortcutDiv = msgDiv.childNodes[i];
            break;
        }
    }

    msgDiv.orgClassName = msgDiv.className;
    msgDiv.className = 'label-none-' + msgDiv.orgClassName;

    shortcutDiv.style.display = 'block';
}

function shortcutGlbMsg( iconSpan ) {
    var name = iconSpan.id.split( 'Span' )[0];
    var msgDiv = document.getElementById( name );
    if( msgDiv && msgDiv.style.display == 'none' ) {
        iconSpan.className = iconSpan.id + '_out';
        msgDiv.style.display = 'block';
    }
}

function closeGlbMsg( iconDiv ) {
    var msgDiv = iconDiv.parentNode;
    var name = msgDiv.id;
    var iconSpan = document.getElementById( msgDiv.id + 'Span' );
    if( msgDiv.style.display != 'none' ) {
        iconSpan.className = name + 'Span_in';
        msgDiv.style.display = 'none';
    }
}

function resetMsgBar() {
    var ids = ['Message', 'Notice', 'Alert', 'Warning'];
    var i, span;
    for( i = 0; i < ids.length; i++ ) {
        span = document.getElementById( 'glb' + ids[i] + 'Span' );
        span.className = span.id + '_out';
    }
}
// ] Message


// [ Fieldset
function toggleDisplayFS( iconSpan ) {
    var fs = iconSpan.parentNode.parentNode;// fs = fieldset
    var tbl = fs.getElementsByTagName( 'table' )[0];
    var div = document.getElementById( fs.id + 'Container' );

    if( tbl.style.display == 'none' ) {
        tbl.style.display = '';
        iconSpan.style.backgroundPosition = "-15px -60px";
    } else {
        tbl.style.display = 'none';
        iconSpan.style.backgroundPosition = "-15px -75px";
    }
}

function displayFS() {
    var id, state;
    
    id = arguments[0];

    var fs = document.getElementById( id );
    var iconSpan = fs.getElementsByTagName( 'span' )[0];
    var tbl = fs.getElementsByTagName( 'table' )[0];

    switch( arguments.length ) {
        case 1:
            return ( tbl.style.display == 'none' ) ? false : true;
            break;
        case 2:
            state = arguments[1];
            
            if( state ) {
                tbl.style.display = '';
                iconSpan.style.backgroundPosition = "-15px -60px";
            } else {
                tbl.style.display = 'none';
                iconSpan.style.backgroundPosition = "-15px -75px";
            }

            return state;
            break;
    }
}

function titleFS() {
    var dlg = dom.$( arguments[0] );
    var spanTitle = dom.getElementsByClass( dlg, 'titleFS' )[0];
    var title;

    switch( arguments.length ) {
        case 1:
            break;
        case 2:
            spanTitle.innerHTML = arguments[1];
            break;
    }

    title = spanTitle.innerHTML;
    return title;
}
// ] Fieldset


// [ Form table
function toggleDisplayFrmTbl( _div ) {
    var div = _div;
    var tbl, trs;
    var isOpened;
    var i;

    tbl = div.parentNode.parentNode.parentNode.parentNode;
    var oldDivBgPos = div.style.backgroundPosition.toString();
    isOpened = ( oldDivBgPos.indexOf( "-30px" ) != -1 || oldDivBgPos.length == 0 ) ? true : false;

    // [ Collapse form content
    if( isOpened ) {
        div.style.backgroundPosition = "0% -45px";
        displayTrs( false );
    }
    // ]
    // [ Expand form content
    else {
        div.style.backgroundPosition = "0% -30px";
        displayTrs( true );
    }
    // ]

    function byId( id ) {
        return document.getElementById( id );
    }
    function byTag( target, tagName ) {
        return target.getElementsByTagName( tagName );
    }
    function displayTrs( state ) {
        var displayValue = ( state ) ? "" : "none";
        trs = byTag( tbl, 'tr' );
        for( i = 1; i < trs.length; i++ ) {
            trs[i].style.display = displayValue;
        }
    }
}

function displayFrmTbl() {
    var div;
    var tbl, trs;
    var i;
    var id, state;

    id = arguments[0];

    tbl = document.getElementById( id );
    div = tbl.getElementsByTagName( 'tr' )[0].getElementsByTagName( 'th' )[0].getElementsByTagName( 'div' )[1];

    switch( arguments.length ) {
        case 1:
            return ( div.style.backgroundPosition.toString().indexOf( "-45" ) != -1 ) ? false : true;
            break;
        case 2:
            state = arguments[1];
            // [ Collapse form content
            if( !state ) {
                div.style.backgroundPosition = "0% -45px";
                displayTrs( false );
            }
            // ]
            // [ Expand form content
            else {
                div.style.backgroundPosition = "0% -30px";
                displayTrs( true );
            }
            // ]

            return state;
            break;
    }

    function byId( id ) {
        return document.getElementById( id );
    }
    function byTag( target, tagName ) {
        return target.getElementsByTagName( tagName );
    }
    function displayTrs( state ) {
        var displayValue = ( state ) ? "" : "none";
        trs = byTag( tbl, 'tr' );
        for( i = 1; i < trs.length; i++ ) {
            trs[i].style.display = displayValue;
        }
    }
}

function titleFrmTbl() {
    var dlg = dom.$( arguments[0] );
    var divTitle = dom.getElementsByClass( dlg, 'label' )[0];
    var title;

    switch( arguments.length ) {
        case 1:
            break;
        case 2:
            divTitle.innerHTML = arguments[1];
            break;
    }

    title = divTitle.innerHTML;
    return title;
}
// ] Form table


// [ Stylish button
function turnon() {
    var btnContainer;
    var leftBtn, middleBtn, rightBtn;

    btnContainer = arguments[0];
    var btns = btnContainer.getElementsByTagName( 'button' );
    var inputs = btnContainer.getElementsByTagName( 'input' );

    switch( arguments.length ) {
        case 1: // Native button
            leftBtn = btns[0];
            middleBtn = btns[1];
            rightBtn = btns[2];
            break;
        case 2: // SX Submit
            leftBtn = btns[0];
            middleBtn = inputs[0];
            rightBtn = btns[1];
            break;
    }

    btnContainer.style.cursor = "pointer";

    leftBtn.style.cursor = "pointer";
    middleBtn.style.cursor = "pointer";
    rightBtn.style.cursor = "pointer";

    leftBtn.style.backgroundImage = "url( "+ g_jsContextPath+ "/share/images/buttons/type1/btn1_hover_left.gif )";
    middleBtn.style.backgroundImage = "url( "+ g_jsContextPath+ "/share/images/buttons/type1/btn1_hover_middle.gif )";
    rightBtn.style.backgroundImage = "url( "+ g_jsContextPath+ "/share/images/buttons/type1/btn1_hover_right.gif )";
}

function turnoff() {
    var btnContainer;
    var leftBtn, middleBtn, rightBtn;

    btnContainer = arguments[0];
    var btns = btnContainer.getElementsByTagName( 'button' );
    var inputs = btnContainer.getElementsByTagName( 'input' );

    switch( arguments.length ) {
        case 1: // Native button
            leftBtn = btns[0];
            middleBtn = btns[1];
            rightBtn = btns[2];
            break;
        case 2: // SX submit
            leftBtn = btns[0];
            middleBtn = inputs[0];
            rightBtn = btns[1];
            break;
    }

    btnContainer.style.cursor = "default";

    leftBtn.style.cursor = "default";
    middleBtn.style.cursor = "default";
    rightBtn.style.cursor = "default";

    leftBtn.style.backgroundImage = "url( "+ g_jsContextPath+ "/share/images/buttons/type1/btn1_left.gif )";
    middleBtn.style.backgroundImage = "url( "+ g_jsContextPath+ "/share/images/buttons/type1/btn1_middle.gif )";
    rightBtn.style.backgroundImage = "url( "+ g_jsContextPath+ "/share/images/buttons/type1/btn1_right.gif )";
}

function displayBtn() {
    var obj = dom.$( arguments[0] );
    var parentObj;
    var state;

    // SXButton
    if( obj.nodeName.toString().toLowerCase() == 'input' ) {
        parentObj = obj.parentNode;
    }
    // NormalButton
    else {
        parentObj = obj;
    }

    switch( arguments.length ) {
        case 1:
            break;
        case 2:
            parentObj.style.display = ( arguments[1] == false ) ? 'none' : '';
            break;
    }

    state = !( parentObj.style.display == 'none' );
    return state;
}

function titleBtn() {
    var obj = dom.$( arguments[0] );
    var titleObj;
    var title;
    
    // SXButton
    if( obj.nodeName.toString().toLowerCase() == 'input' ) {
        titleObj = obj;
    }
    // NormalButton
    else {
        titleObj = obj.getElementsByTagName( 'button' )[1];
    }
    
    switch( arguments.length ) {
        case 1:
            break;
        case 2:
            titleObj.innerHTML = arguments[1];
            break;
    }

    title = titleObj.innerHTML;
    return title;
}

reloadPage = function (){
    gotoAction('divPackageGoodsList','packageGoodsV2Action!reloadListPackageGoods.do?');
}
reloadPackageGoodsDetailList = function(packageGoodsId){
    gotoAction('divPackageGoodsDetailV2List','packageGoodsV2Action!searchPackageGoodsDetail.do?packageGoodsId=' + packageGoodsId);
}
// ] Stylish button

// ] LongH
var hex=new Array('0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f');


function escapeHtml(originalText) {
    var preescape="" + originalText;
    var escaped="";

    var i=0;
    for(i=0;i<preescape.length;i++)
    {
        var p=preescape.charAt(i);

        p=""+escapeCharOther(p);
        p=""+escapeTags(p);
        p=""+escapeBR(p);

        escaped=escaped+p;
    }
    return escaped;
}

function escapeHtmlTextArea(originalText) {
    var preescape="" + originalText;
    var escaped="";

    var i=0;
    for(i=0;i<preescape.length;i++)
    {
        var p=preescape.charAt(i);

        p=""+escapeCharOther(p);
        p=""+escapeTags(p);

        escaped=escaped+p;
    }

    return escaped;
}


function escapeBR(original) {
    var thechar=original.charCodeAt(0);

    switch(thechar) {
        case 10:
            return "<br/>";
            break; //newline
        case '\r':
            break;
    }
    return original;
}

function escapeNBSP(original) {
    var thechar=original.charCodeAt(0);
    switch(thechar) {
        case 32:
            return "&nbsp;";
            break; //space
    }
    return original;
}


function escapeTags(original) {
    var thechar=original.charCodeAt(0);
    switch(thechar) {
        case 60:
            return "&lt;";
            break; //<
        case 62:
            return "&gt;";
            break; //>
        case 34:
            return "&quot;";
            break; //"
    }
    return original;

}

function escapeCharOther(original) {
    var found=true;
    var thechar=original.charCodeAt(0);
    switch(thechar) {
        case 38:
            return "&amp;";
            break; //&
        case 198:
            return "&AElig;";
            break; //Æ
        case 193:
            return "&Aacute;";
            break; //Á
        case 194:
            return "&Acirc;";
            break; //Â
        case 192:
            return "&Agrave;";
            break; //À
        case 197:
            return "&Aring;";
            break; //Å
        case 195:
            return "&Atilde;";
            break; //Ã
        case 196:
            return "&Auml;";
            break; //Ä
        case 199:
            return "&Ccedil;";
            break; //Ç
        case 208:
            return "&ETH;";
            break; //Ð
        case 201:
            return "&Eacute;";
            break; //É
        case 202:
            return "&Ecirc;";
            break; //Ê
        case 200:
            return "&Egrave;";
            break; //È
        case 203:
            return "&Euml;";
            break; //Ë
        case 205:
            return "&Iacute;";
            break; //Í
        case 206:
            return "&Icirc;";
            break; //Î
        case 204:
            return "&Igrave;";
            break; //Ì
        case 207:
            return "&Iuml;";
            break; //Ï
        case 209:
            return "&Ntilde;";
            break; //Ñ
        case 211:
            return "&Oacute;";
            break; //Ó
        case 212:
            return "&Ocirc;";
            break; //Ô
        case 210:
            return "&Ograve;";
            break; //Ò
        case 216:
            return "&Oslash;";
            break; //Ø
        case 213:
            return "&Otilde;";
            break; //Õ
        case 214:
            return "&Ouml;";
            break; //Ö
        case 222:
            return "&THORN;";
            break; //Þ
        case 218:
            return "&Uacute;";
            break; //Ú
        case 219:
            return "&Ucirc;";
            break; //Û
        case 217:
            return "&Ugrave;";
            break; //Ù
        case 220:
            return "&Uuml;";
            break; //Ü
        case 221:
            return "&Yacute;";
            break; //Ý
        case 225:
            return "&aacute;";
            break; //á
        case 226:
            return "&acirc;";
            break; //â
        case 230:
            return "&aelig;";
            break; //æ
        case 224:
            return "&agrave;";
            break; //à
        case 229:
            return "&aring;";
            break; //å
        case 227:
            return "&atilde;";
            break; //ã
        case 228:
            return "&auml;";
            break; //ä
        case 231:
            return "&ccedil;";
            break; //ç
        case 233:
            return "&eacute;";
            break; //é
        case 234:
            return "&ecirc;";
            break; //ê
        case 232:
            return "&egrave;";
            break; //è
        case 240:
            return "&eth;";
            break; //ð
        case 235:
            return "&euml;";
            break; //ë
        case 237:
            return "&iacute;";
            break; //í
        case 238:
            return "&icirc;";
            break; //î
        case 236:
            return "&igrave;";
            break; //ì
        case 239:
            return "&iuml;";
            break; //ï
        case 241:
            return "&ntilde;";
            break; //ñ
        case 243:
            return "&oacute;";
            break; //ó
        case 244:
            return "&ocirc;";
            break; //ô
        case 242:
            return "&ograve;";
            break; //ò
        case 248:
            return "&oslash;";
            break; //ø
        case 245:
            return "&otilde;";
            break; //õ
        case 246:
            return "&ouml;";
            break; //ö
        case 223:
            return "&szlig;";
            break; //ß
        case 254:
            return "&thorn;";
            break; //þ
        case 250:
            return "&uacute;";
            break; //ú
        case 251:
            return "&ucirc;";
            break; //û
        case 249:
            return "&ugrave;";
            break; //ù
        case 252:
            return "&uuml;";
            break; //ü
        case 253:
            return "&yacute;";
            break; //ý
        case 255:
            return "&yuml;";
            break; //ÿ
        case 162:
            return "&cent;";
            break; //¢
        default:
            found=false;
            break;
    }
    if(!found) {
        if(thechar>127) {
            var c=thechar;
            var a4=c%16;
            c=Math.floor(c/16);
            var a3=c%16;
            c=Math.floor(c/16);
            var a2=c%16;
            c=Math.floor(c/16);
            var a1=c%16;
            return "&#x"+hex[a1]+hex[a2]+hex[a3]+hex[a4]+";";
        }
        else
        {
            return original;
        }
    }
}
