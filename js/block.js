---
layout: none
---
(function() {
  document.querySelectorAll("block-info[data-for-id]").forEach(el => {
    const img = new Image(256, 256);
    img.addEventListener("error", () => {
      img.src = {{ "/img/missing.png" | relative_url | jsonify }};
    });
    img.src = {{ "/img/textures/allotment/" | relative_url | jsonify }} + el.dataset.forId + ".png";
    img.alt = "";
    el.appendChild(img);
  })
}());
