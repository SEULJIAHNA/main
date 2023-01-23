$(document).ready(()=>{
    // shows dots.gif when user profile button hovering
    let button = $(".user_profile_image_button");

    button.hover(function() {
        // mouse enter
        $(this).children(".user_profile_image").css("opacity" , "30%");
    }, function() { // mouse leave
        $(this).children(".user_profile_image").css("opacity" , "100%");
    });

    //  open image upload modal
//    $(".user_profile_image_button").on("click" , function() {
//        $("#imageUploadModal").removeClass("fade");
//        $("#imageUploadModal").css("display" , "block");
//        });

    // close image upload modal



})