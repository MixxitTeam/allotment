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
    const strval = themeSelector.value;
    const val = parseInt(strval, 10);

    function setAndSaveTheme() {
      applyTheme(val);

      if ("localStorage" in window) {
        localStorage.setItem("theme", val);
      }
    }

    function listener(success) {
      if (success) {
        setAndSaveTheme();
        themeSelector.value = strval;
      } else
        cookieMessageRemoveListener(listener);
    }

    if (getHasAcceptedCookies()) {
      setAndSaveTheme();
    } else {
      themeSelector.value = -1;
      cookieMessageAddListener(listener);
      showCookieMsg();
    }
  });

  function applyTheme(val) {
    switch (val) {
      case -1: // system default
      document.documentElement.classList.remove("light");
      document.documentElement.classList.remove("dark");
        break;
      case 0: // light
      document.documentElement.classList.add("light");
      document.documentElement.classList.remove("dark");
        break;
      case 1: // dark
        document.documentElement.classList.remove("light");
        document.documentElement.classList.add("dark");
        break;
      default:
        console.warn("Invalid value for theme: ", val);
        return;
    }
  }
}());
