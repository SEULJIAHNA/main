/*
    body header
*/

let horizontalBar = document.getElementById("horizontal-underline");
let horizontalMenus = document.querySelectorAll(".nav-bar:first-child a");

horizontalMenus.forEach((menu) =>
  menu.addEventListener("click", (e) =>
    horizontalIndicator(e.currentTarget)
  ));



function horizontalIndicator(e) {
  horizontalBar.style.left = e.offsetLeft + "px";
  horizontalBar.style.width = e.offsetWidth + "px";
  horizontalBar.style.top = e.offsetTop + e.offsetHeight + "px";
}

/*
    navbar event js
*/

$(document).ready(function() {
    const toggleBtn = $(".navbar__toggleBtn");
    const menu = document.querySelector(".navbar__menu");

    toggleBtn.click(function() {
        menu.classList.toggle('active');
        $(this).toggle('active');
    });
});


