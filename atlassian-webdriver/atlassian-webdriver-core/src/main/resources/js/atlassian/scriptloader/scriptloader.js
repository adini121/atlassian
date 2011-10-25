(function(window) {
    var ATLWD = window.ATLWD || {};

//Silence console calls if there is no console.
    if(typeof window.console !== 'object') {
        window.console = {
            log: function() {}, alert: function() {}, warn: function() {}, info: function() {},
            time: function() {}, timeEnd: function() {}, error: function() {}
        };
    }

    ATLWD.scriptloader = {};
    ATLWD.scriptloader.loadedScripts = [];
    ATLWD.scriptloader.scriptLoaded = function(scriptName) {
        window.console.log("scriptLoaded called!");
        ATLWD.scriptloader.loadedScripts.push(scriptName);
    };
    ATLWD.scriptloader.isLoaded = function(scriptName) {
        window.console.log("scriptLoaded called!");
        return contains(ATLWD.scriptloader.loadedScripts, scriptName);
    };

    window.ATLWD = ATLWD;
})(window);

function contains(array, obj) {
    for (var i = 0; i < array.length; i++) {
        if (array[i] === obj) {
            return true;
        }
    }
    return false;
}
