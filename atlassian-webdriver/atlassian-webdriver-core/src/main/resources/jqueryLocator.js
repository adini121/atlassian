(function() {

var proto = "prototype",
    toString = "toString",
    objectToString = Object[proto][toString],
    lowerCase = String[proto].toLowerCase;

var oldjQuery = window.jQuery;
var old$ = window.$;

// potential to namspace things away for WebDriver
var WD = window.WD != undefined ? WD : {};

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
    return WD.byJquery.$.makeArray(eval(jq));
};

WD.byJquery.executeOne = function(jq, context) {
    return eval(jq)[0];
};

window.WD = WD;

})();