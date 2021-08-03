---
layout: none
---
const deepMergeObjects = (base, patch) => Object.assign({},JSON.parse(JSON.stringify(base)), JSON.parse(JSON.stringify(patch)));

(function() {
  // Defaults for block properties
  // Set in front matter of page under "blockprops" key
  const blockPropsDefault = {
    harvestLevel: 0,      // number: 0 - None, 1 - Wood/Gold, 2 - Stone, 3 - Iron, 4 - Diamond, 5 - Netherite
        hardness: 0,      // number: Hardness, set in code
      resistance: 0,      // number: Resistance, set in code
    requiresTool: false,  // boolean
     harvestTool: "none", // enum: "none", "axe", "pickaxe", "shovel", "hoe", "sword", "shears"
      lightlevel: 0,      // number: Between 0 (inclusive) and 15 (inclusive)
     transparent: false,  // boolean
    maxstacksize: 64      // number: Between 0 (inclusive) and 64 (inclusive)
  };

  // Display names for block properties
  const blockPropsDisplayNames = {
    hardness: "Hardness",
    resistance: "Blast Resistance",
    lightlevel: "Luminant",
    transparent: "Transparent",
    maxstacksize: "Stackable"
  };

  document.querySelectorAll("block-info[data-for-id]").forEach(el => {
    const img = new Image(256, 256);
    img.addEventListener("error", () => {
      img.src = {{ "/img/missing.png" | relative_url | jsonify }};
    });
    img.src = {{ "/img/textures/allotment/" | relative_url | jsonify }} + el.dataset.forId + ".png";
    img.alt = "";
    el.appendChild(img);

    const blockProps = deepMergeObjects(blockPropsDefault, _blockProps || {});
    
    const propsTable = document.createElement("table");
    const propsTbody = document.createElement("tbody");
    const propsTableTr1 = document.createElement("tr");
    const propsToolTd = document.createElement("td");
    const toolInfo = document.createElement("div");
    toolInfo.className = "tool-info";
    const hLevel = blockProps.harvestLevel;
    const hTool = blockProps.harvestTool;
    toolInfo.classList.add("tool-" + hTool);
    toolInfo.classList.add("tool-level-" + hLevel);

    propsToolTd.setAttribute("colspan", "2");
    propsToolTd.appendChild(toolInfo);
    propsTableTr1.appendChild(propsToolTd);
    propsTbody.appendChild(propsTableTr1);

    Object.keys(blockProps).forEach(k => {
      if (
        k === "requiresTool" ||
        k === "harvestLevel" ||
        k === "harvestTool"
      ) return;

      const v = blockProps[k];

      const tr = document.createElement("tr");
      const td1 = document.createElement("td");
      const td2 = document.createElement("td");

      if (blockPropsDisplayNames.hasOwnProperty(k))
        td1.innerText = blockPropsDisplayNames[k] + ":";
      else
        td1.innerText = k + ":";

      const t = typeof v;
      if (k === "maxstacksize" || k === "lightlevel")
        td2.innerText = (v > 1 ? ("Yes (" + v + ")") : "No");
      else if (t === "boolean")
        td2.innerText = v ? "Yes" : "No";
      else
        td2.innerText = v;
      tr.appendChild(td1);
      tr.appendChild(td2);
      propsTbody.appendChild(tr);
    });

    propsTable.appendChild(propsTbody);
    el.appendChild(propsTable);
  });
}());
