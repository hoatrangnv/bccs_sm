token = {
    reloadToken : function() {
        
        dojo.io.bind({
            useCache:false,
            preventCache:true,
            url:"token!reloadToken.do",
            method: "post",
            handler: function( type, data, e ) {
                try{
                    dojo.byId("token").innerHTML = data;
                }catch(e){
                    console.log(e);
                    throw e;
                }
            },
            sync:true,
            mimetype: "text/html"
        });
    },
    getTokenParam:function(){
        var myParam;
        
        switch(arguments.length) {
            case 1:
                myParam = arguments[0];
                break;
            default:
                myParam = new Object();
                break;
        }

        this.reloadToken();
        
        var tokenDiv = document.getElementById("token");
        var els = tokenDiv.getElementsByTagName("input");
        
        myParam[els[0].name] = els[0].value;
        myParam[els[1].name] = els[1].value;
        return myParam;
    },
    getTokenParamString:function(){
        var myParam;
        this.isLoaded = false;
        this.reloadToken();
        var tokenDiv = document.getElementById("token");
        var els = tokenDiv.getElementsByTagName("input");
        
        myParam=els[0].name+"="+els[0].value;
        myParam+="&"+els[1].name +"="+ els[1].value;
        return myParam;
    
    },
    setTokenToParam:function(param){
        this.reloadToken();
        var tokenDiv = document.getElementById("token");
        var els = tokenDiv.getElementsByTagName("input");
        param.push( [els[0].name, els[0].value].join( "=" ) );
        param.push( [els[1].name, els[1].value].join( "=" ) );
        return param;
    }
}
