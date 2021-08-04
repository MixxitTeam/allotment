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
    maxstacksize: 64,     // number: Between 0 (inclusive) and 64 (inclusive)
    potionEffect: null    // string: Name of potion effect (as namespaced id)
  };

  // Defaults for item properties
  // Set in front matter of page under "blockprops" key (there is no "itemprops" key)
  const itemPropsDefault = {
    harvestLevel: null,
        hardness: null,
      resistance: null,
    requiresTool: null,
     harvestTool: null,
      lightlevel: null,
     transparent: null,
    potionEffect: null,
    maxstacksize: 64,
  };

  // Display names for block properties
  const blockPropsDisplayNames = {
    hardness: "Hardness",
    resistance: "Blast Resistance",
    lightlevel: "Luminant",
    transparent: "Transparent",
    maxstacksize: "Stackable",
    potionEffect: "Potion Effect"
  };

  // Display names for harvest levels
  const harvestLevelDisplayNames = {
    0: "",
    1: "Wooden/Gold",
    2: "Stone",
    3: "Iron",
    4: "Diamond",
    5: "Netherite"
  };

  // Display names for tools
  const harvestToolsDisplayNames = {
    "none": "no tool",
    "axe": "Axe",
    "pickaxe": "Pickaxe",
    "shovel": "Shovel",
    "hoe": "Hoe",
    "sword": "Sword",
    "shears": "Shears"
  };

  document.querySelectorAll("block-info[data-for-id]").forEach(el => {
    const img = new Image(256, 256);
    img.addEventListener("error", () => {
      img.src = {{ "/img/missing_lg.png" | relative_url | jsonify }};
    });
    img.src = {{ "/img/textures/allotment/" | relative_url | jsonify }} + el.dataset.forId + ".png";
    img.alt = "";
    img.className = "preview-icon";
    el.appendChild(img);

    const invimg = new Image(32, 32);
    invimg.addEventListener("error", () => {
      invimg.src = {{ "/img/missing_lg.png" | relative_url | jsonify }};
    });
    invimg.src = {{ "/img/inventory_textures/allotment/" | relative_url | jsonify }} + el.dataset.forId + ".png";
    invimg.alt = "";
    invimg.className = "inventory-icon";

    const blockProps = deepMergeObjects(_isItem ? itemPropsDefault : blockPropsDefault, _blockProps || {});
    
    const propsTable = document.createElement("table");
    const propsTbody = document.createElement("tbody");
    const propsTableTr1 = document.createElement("tr");
    const propsInvTd = document.createElement("td");
    let propsTableTr2;

    if (!_isItem) {
      propsTableTr2 = document.createElement("tr");
      const propsToolTd = document.createElement("td");
      const toolInfo = document.createElement("div");
      toolInfo.className = "tool-info";
      const hLevel = blockProps.harvestLevel;
      const hTool = blockProps.harvestTool;
      const rTool = blockProps.requiresTool;
      toolInfo.classList.add("tool-" + hTool);
      toolInfo.classList.add("tool-level-" + hLevel);

      const toolName = [
        hTool === "shears" ? "" : harvestLevelDisplayNames[hLevel],
        harvestToolsDisplayNames[hTool]
      ].filter(Boolean).join(" ");

      const article = hTool === "shears" ? "" : "aeiou".split("").indexOf(toolName.toLowerCase()[0]) >= 0 ? "an" : "a";

      const toolTip = (!rTool && hLevel < 1 && hTool === "none") ? "Does not require or break faster with any tool" : [
        (rTool || hLevel > 0) ? "Requires" : "Breaks faster with",
        article,
        toolName
      ].filter(Boolean).join(" ");

      toolInfo.title = toolTip;

      propsTableTr2.appendChild(propsToolTd);
      propsToolTd.setAttribute("colspan", "2");
      propsToolTd.appendChild(toolInfo);
    }

    propsInvTd.setAttribute("colspan", "2");
    propsInvTd.appendChild(invimg);
    propsTableTr1.appendChild(propsInvTd);
    propsTbody.appendChild(propsTableTr1);

    if (!_isItem)
      propsTbody.appendChild(propsTableTr2);

    Object.keys(blockProps).forEach(k => {
      if (
        k === "requiresTool" ||
        k === "harvestLevel" ||
        k === "harvestTool"
      ) return;

      const v = blockProps[k];

      if (v === null) return;

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
      else if (k === "potionEffect") {
        const img = new Image(18, 18);
        const eff = mc_en_us["effect.minecraft." + v];
        img.src = {{ "/img/mob_effect/" | relative_url | jsonify }} + v + ".png";
        img.alt = eff;
        img.title = eff;
        img.className = "effect-icon";
        td2.appendChild(img);
      } else if (t === "boolean")
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
