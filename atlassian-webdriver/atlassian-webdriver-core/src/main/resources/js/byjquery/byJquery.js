(function(window, undefined) {

var proto = "prototype",
    toString = "toString",
    objectToString = Object[proto][toString],
    lowerCase = String[proto].toLowerCase;

var oldjQuery = window.jQuery;
var old$ = window.$;

// potential to namspace things away for WebDriver
var ATLWD = window.ATLWD ? ATLWD : {};

ATLWD.byJquery = {};

//Silence console calls if there is no console.
if(typeof console !== 'object') {
    console = {
        log: function() {}, alert: function() {}, warn: function() {}, info: function() {},
        time: function() {}, timeEnd: function() {}, error: function() {}
    };
}

ATLWD.loadJquery = function()
{
    ATLWD.byJquery.$ = $.noConflict();

    if (oldjQuery) {
        window.jQuery = oldjQuery;
    }

    if (old$) {
        window.$ = old$;
    }
}

ATLWD.byJquery.execute = function(jq, context) {
    console.log("ATLWD.byJquery.execute: " + jq + ", context:" + context);

    var result = ATLWD.byJquery.$.makeArray(eval(jq));

    console.log("execute result: ");
    console.log(result);
    
    return result;
};

ATLWD.byJquery.executeOne = function(jq, context) {
    console.log("ATLWD.byJquery.executeOne: " + jq + ", context:" + context);
    var result = eval(jq)[0];

    console.log("executeOne result: " + result);
    return result;
};

window.ATLWD = ATLWD;

})(window);