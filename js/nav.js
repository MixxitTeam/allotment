(function() {
  const na = document.querySelector(".nav-active");

  if (!na) return;

  na.parentElement.scrollLeft = na.offsetLeft - 24;
}());
