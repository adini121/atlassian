(function(window, undefined) {

var proto = "prototype",
    toString = "toString",
    objectToString = Object[proto][toString],
    lowerCase = String[proto].toLowerCase;

var oldjQuery = window.jQuery;
var old$ = window.$;

ATLWD.byJquery = {};

ATLWD.loadJquery = function() {
    ATLWD.byJquery.$ = $.noConflict();

    if (oldjQuery) {
        window.jQuery = oldjQuery;
    }

    if (old$) {
        window.$ = old$;
    }
};

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

})(window);