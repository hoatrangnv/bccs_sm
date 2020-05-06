/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
TableMapShortcut = function(){
    this.list=[]
}
TableMapShortcut = function(list){
    this.list = list
}

TableMapShortcut.prototype = {
    initialize: function(list){
        this.list = [];
        if(list)this.list = list;
    },
    push: function(obj){
        this.list.push(obj);
    },
    addAt: function(obj,indexAt){
        if (indexAt>=this.list.length)
            this.list.push(obj);
        else{
            for(var i = this.list.length-1;i>=indexAt;i--)
                this.list[i+1]=this.list[i];
        }
        this.list[indexAt] = obj;
    },
    pop: function(){
        this.list.pop();
    },
    fireEvent: function(){
        var listvalidated =[];
        var i=0;
        for (i = 0; i < this.list.length; i++){
            if (document.getElementById(this.list[i])!=null)
                listvalidated.push(this.list[i]);
        }
        for (i = 0; i < listvalidated.length-1; i++){
            var context = this;
            var item = listvalidated[i+1];
            shortcut.add("Enter",context.createDelegate(context.focusElement, context, item),
            {
                'type':'keydown',
                'propagate':false,
                'target':listvalidated[i]
            });
        }
    },

    // ------------------- Private function -----------------------------
    focusElement: function(ele){
        if(typeof ele == 'string') ele = document.getElementById(ele);
        ele.focus();
    },
    createDelegate: function(func, context,params){
        return function(){
            func.call(context,params);
        }
    }
// ------------------------------------------------------------------
}