$(document).ready(function () {
    entryAnimations();
    formsFunctional();
});

function entryAnimations() {
    window.onload = function () {
        $("body").addClass("play");

        setTimeout(function () {
            $("body").removeClass("entry");
        }, 1000)
    }
}

function formsFunctional() {

    // $(".numeric .icon-top").on('click', function () {
    //     var i = Number($(".numeric input").val());
    //     $(".numeric input").val(i + 1);
    //     $(".numeric input").trigger("input");
    // });
    //
    // $(".numeric .icon-down").on('click', function () {
    //     var i = Number($(".numeric input").val());
    //     if (i > 0) {
    //         $(".numeric input").val(i - 1);
    //         $(".numeric input").trigger("input");
    //     }
    // });

    $(".icon-eye").on('click', function () {
        if ($(this).hasClass('visible')) {
            $('.icon-eye').removeClass('visible');
            $('input[id="password"]').attr('type', 'password');
            $('input[id="confirmPassword"]').attr('type', 'password');
        } else {
            $('.group .icon-eye').addClass('visible');
            $('input[id="password"]').attr('type', 'text');
            $('input[id="confirmPassword"]').attr('type', 'text');
        }

    });

}