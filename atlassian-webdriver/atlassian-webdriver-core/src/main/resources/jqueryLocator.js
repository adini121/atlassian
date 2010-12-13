(function() {

var proto = "prototype",
    toString = "toString",
    objectToString = Object[proto][toString],
    lowerCase = String[proto].toLowerCase;

var oldjQuery = window.jQuery;
var old$ = window.$;

// potential to namspace things away for WebDriver
var WD = window.WD ? WD : {};

WD.byJquery = {};

WD.loadJquery = function()
{
    WD.byJquery.$ = $.noConflict();

    if (oldjQuery) {
        window.jQuery = oldjQuery;
    }

    if (old$) {
        window.$ = old$;
    }
}

WD.byJquery.execute = function(jq, context) {
    console.log("execute: " + jq);

    if(context) {
        console.log("CONTEXT: ");
        console.log(context);
        console.log(context[0]);
    }

    /*if (context) {
        for (i in context) {
            console.log(i + " : " + context[i]);
        }
    }*/

    var result = WD.byJquery.$.makeArray(eval(jq));

    console.log("execute result: ");
    console.log(result);
    
    return result;
};

WD.byJquery.executeOne = function(jq, context) {
    console.log("executeOne: " + jq);
    console.log("contextOne: " + context);
    var result = eval(jq)[0];

    console.log("executeOne result: " + result);
    return result;
};

//Silence console calls if there is no console.    
if(typeof console !== 'object') {
    console = {
        log: function() {}, alert: function() {}, warn: function() {}, info: function() {},
        time: function() {}, timeEnd: function() {}, error: function() {}
    };
}

window.WD = WD;

})();