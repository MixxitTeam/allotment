---
layout: none
---
var sjs = (function() {
  var USE_CTRL_F = false;
  var searchInput = document.getElementById('search-input');
  var resultsContainer = document.getElementById('results-container');
  var results = [];
  var currentIndex = -1;
  var _sjs = SimpleJekyllSearch({
    searchInput: searchInput,
    resultsContainer: resultsContainer,
    json: {{ "/search.json" | relative_url | jsonify }},
    fuzzy: true,
    onSearch: onSearchSuccess,
    debounceTime: 100
  });

  if (USE_CTRL_F)
    window.addEventListener("keydown", function(event) {
      if (
        event.ctrlKey &&
        !event.shiftKey &&
        !event.altKey &&
        !event.metaKey &&
        event.key.toLowerCase() === "f"
      ) {
        event.preventDefault();
        searchInput.focus();
      }
    });
  searchInput.addEventListener("keydown", function(event) {
    if (
      !event.ctrlKey &&
      !event.shiftKey &&
      !event.altKey &&
      !event.metaKey &&
      (event.key === "ArrowUp" || event.key === "ArrowDown")
    ) {
      if (highlightSearchResult(event.key === "ArrowUp" ? -1 : 1))
        event.preventDefault();
    }
  });
  searchInput.addEventListener("focus", function() {
    currentIndex = -1;
  });

  function onSearchSuccess() {
    results = resultsContainer.querySelectorAll("a");
    currentIndex = -1;
    applyResultHandler();
  }

  function applyResultHandler() {
    resultsContainer.querySelectorAll("a:not([data-applied-handler])").forEach(el => {
      el.dataset.appliedHandler = "true";
      el.addEventListener("keydown", function(event) {
        if (
          !event.ctrlKey &&
          !event.shiftKey &&
          !event.altKey &&
          !event.metaKey &&
          (event.key === "ArrowUp" || event.key === "ArrowDown")
        ) {
          if (highlightSearchResult(event.key === "ArrowUp" ? -1 : 1))
            event.preventDefault();
        } else if (event.key !== "Enter") {
          var ev = new KeyboardEvent(event.type, event);
          searchInput.focus();
          searchInput.dispatchEvent(ev);
        }
      });
    });
  }

  function highlightSearchResult(direction) {
    var newIndex = currentIndex + direction;

    if (newIndex < 0) {
      searchInput.focus();
    } else if (newIndex >= 0 && newIndex < results.length) {
      results[newIndex].focus();
    }

    currentIndex = Math.max(-1, Math.min(newIndex, results.length - 1));

    return true;
  }
  
  window._getCurrentIndex = function() {
    return currentIndex;
  }
  
  window._getResults = function() {
    return results;
  }

  return _sjs;
}());
