---
layout: null
scripts:
  - "cookielib.js"
  - "modernizr.js"
  - "theme-select.js"
  - "feature-detect.js"
  - "simple-jekyll-search.min.js"
  - "search.js"
  - "nav.js"
---
{% for script in page.scripts %}/*---------Start of {{ script }}---------*/
{% include_relative {{ script }} %}
/*---------End of {{ script }}---------*/
{% endfor %}
