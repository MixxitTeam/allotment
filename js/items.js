(function() {
  document.querySelectorAll(".item.item-type-multiple").forEach(cont => {
    const children = Array.from(cont.querySelectorAll(".item-child"));

    let index = 0;

    function changeVisib() {
      children.forEach(el => el.style.display = "none");
      children[index].style.display = "";
    }

    changeVisib();
    setInterval(function() {
      index = (index + 1) % children.length;
      changeVisib();
    }, 1000);
  });
}());
