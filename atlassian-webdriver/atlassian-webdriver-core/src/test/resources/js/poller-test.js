
(function($){

    $(function() {
        $("#dialog-one-show-button").click(function() {
            $("#test").remove();
            setTimeout(function() {
                $("#dialog-one").show();
            }, 3500);
        });

        $("#dialog-one-hide-button").click(function() {
            setTimeout(function(){
                $("#dialog-one").hide();
            }, 3500);
        });

    });

})(jQuery);