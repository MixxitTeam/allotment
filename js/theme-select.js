(function() {
  const themeSelector = document.querySelector("#theme");

  if (themeSelector === null) {
    console.warn("No theme selector found on page!");
    return;
  }

  if ("localStorage" in window) {
    (function() {
      const strVal = localStorage.getItem("theme");
      if (strVal === null)
        return;
      const target = themeSelector.querySelector("option[value='" + strVal + "']");
      if (target === null)
        return;
      target.selected = true;
      applyTheme(parseInt(strVal, 10));
    }());
  }

  themeSelector.addEventListener("change", () => {
    const val = parseInt(themeSelector.value, 10);

    applyTheme(val);

    if ("localStorage" in window) {
      localStorage.setItem("theme", val);
    }
  });

  function applyTheme(val) {
    switch (val) {
      case -1: // system default
      document.body.classList.remove("light");
      document.body.classList.remove("dark");
        break;
      case 0: // light
      document.body.classList.add("light");
      document.body.classList.remove("dark");
        break;
      case 1: // dark
        document.body.classList.remove("light");
        document.body.classList.add("dark");
        break;
      default:
        console.warn("Invalid value for theme: ", val);
        return;
    }
  }
}());
