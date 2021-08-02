(function() {
  var supported = [
    "CSS" in window && CSS.supports("color: var(--test)"),
    Modernizr.flexbox,
    Modernizr.flexwrap,
    Modernizr.customproperties
  ];

  for (var i = 0; i < supported.length; ++i) {
    if (!supported[i]) {
      var unsupportedOverlay = document.createElement("table");
      unsupportedOverlay.className = "unsupported-browser";
      var unsupportedInner = document.createElement("tr");
      unsupportedInner.className = "unsupported-browser__inner";
      var unsupportedMessage = document.createElement("td");
      unsupportedMessage.className = "unsupported-browser__inner_message";

      unsupportedMessage.appendChild(document.createTextNode("Your browser is not supported! Please upgrade to a modern browser like Chrome, Firefox or Edge!"));
      unsupportedInner.appendChild(unsupportedMessage);
      unsupportedOverlay.appendChild(unsupportedInner);
      document.body.appendChild(unsupportedOverlay);

      break;
    }
  }
}());
