(function(window, undefined) {

var proto = "prototype",
    toString = "toString",
    objectToString = Object[proto][toString],
    lowerCase = String[proto].toLowerCase;

var oldjQuery = window.jQuery;
var old$ = window.$;

// potential to namspace things away for WebDriver
var WD = window.WD ? WD : {};

WD.byJquery = {};

//Silence console calls if there is no console.
if(typeof console !== 'object') {
    console = {
        log: function() {}, alert: function() {}, warn: function() {}, info: function() {},
        time: function() {}, timeEnd: function() {}, error: function() {}
    };
}

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
    console.log("WD.byJquery.execute: " + jq + ", context:" + context);

    var result = WD.byJquery.$.makeArray(eval(jq));

    console.log("execute result: ");
    console.log(result);
    
    return result;
};

WD.byJquery.executeOne = function(jq, context) {
    console.log("WD.byJquery.executeOne: " + jq + ", context:" + context);
    var result = eval(jq)[0];

    console.log("executeOne result: " + result);
    return result;
};

window.WD = WD;

})(window);