(function() {
  document.querySelectorAll("block-info[data-for-id]").forEach(el => {
    const img = new Image(256, 256);
    img.addEventListener("error", () => {
      img.src = "/img/missing.png";
    });
    img.src = "/img/textures/allotment/" + el.dataset.forId + ".png";
    img.alt = "";
    el.appendChild(img);
  })
}());
