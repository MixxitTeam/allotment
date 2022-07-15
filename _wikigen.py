from ast import parse
import collections
import configparser
import json
import math
import os
from unicodedata import category

def getjson(name):
    with open(name, "r", encoding="utf-8") as f:
        return json.load(f)

def getresult(r):
    if "result" in r:
        if "item" in r["result"]:
            return r["result"]["item"]
        else:
            return r["result"]
    else:
        raise Exception("No result in recipe " + json.dumps(r))

def getLocalizedName(id):
    global lang, mclang, fallbacklang
    key = id.replace(":", ".")
    if "block." + key in lang:
        return lang["block." + key]
    elif "item." + key in lang:
        return lang["item." + key]
    elif "block." + key in mclang:
        return mclang["block." + key]
    elif "item." + key in mclang:
        return mclang["item." + key]
    elif "block." + key in fallbacklang:
        return fallbacklang["block." + key]
    elif "item." + key in fallbacklang:
        return fallbacklang["item." + key]
    elif "tag." + key in fallbacklang:
        return fallbacklang["tag." + key]
    else:
        return id

def resultisminecraft(r):
    return getresult(r)[:10] == "minecraft:"

def getidpath(n):
    return n.split(":")[1]

def truthy(v):
    if isinstance(v, str):
        return len(v) > 0
    elif v:
        return True
    else:
        return False

def getHarvestToolIcon(block):
    names = {
        "none": "no tool",
        "axe": "Axe",
        "pickaxe": "Pickaxe",
        "shovel": "Shovel",
        "hoe": "Hoe",
        "sword": "Sword",
        "shears": "Shears"
    }
    levelNames = {
        0: "",
        1: "Wooden/Gold",
        2: "Stone",
        3: "Iron",
        4: "Diamond",
        5: "Netherite"
    }

    tool = block["harvestTool"] or "none"
    req = block["requiresTool"] or False
    level = math.floor(block["harvestLevel"]) + 1

    toolName = " ".join([i for i in [
        "" if tool == "shears" else levelNames[level],
        names[tool]
    ] if i])

    article = "" if tool == "shears" or tool == "none" else ("an" if toolName[0].lower() in ["a", "e", "i", "o", "u"] else "a")

    toolTip = " ".join([i for i in (
        ["Does not require or break faster with any tool"] if not req and level < 1 and tool == "none" else [
                "Requires" if req or level > 0 else "Breaks faster with",
                article,
                toolName
        ]) if truthy(i)])

    return '<span class="tool-info tool-{tool} tool-level-{level}" title="{tip}"></span>'.format(tool=tool, level=level, tip=toolTip)

def mmdReplaceVars(ln, ctx):
    for k in ctx:
        ln = ln.replace("%{" + k + "}", ctx[k])
    return ln

def parseMmd(contents, ctx = {}):
    sections = {}
    lines = contents.split("\n")
    
    currentSection = None
    currentSectionBuffer = []
    for ln in lines:
        if ln.startswith(".") and ln.endswith(":"):
            if currentSection is not None:
                sections[currentSection] = "\n".join(currentSectionBuffer)
            currentSection = ln[1:-1]
            currentSectionBuffer = []
        else:
            currentSectionBuffer.append(mmdReplaceVars(ln, ctx))
    sections[currentSection] = "\n".join(currentSectionBuffer).strip()

    return sections

def htmlFormatRecipe(recipe):
    buf = []
    if recipe["type"] == "minecraft:crafting_shaped":
        lookup = recipe["key"]

        buf.append('<table class="crafting-recipe crafting-shaped"><tbody><tr>')

        buf.append('<td><table class="crafting-grid"><tbody>')

        pattern = recipe["pattern"]
        while len(pattern) < 3:
            pattern.append(" " * 3)

        pattern = [r.ljust(3) for r in pattern]

        for row in pattern:
            buf.append("<tr>")
            for letter in row:
                buf.append("<td>")
                if letter == " ":
                    buf.append('<span class="item item-empty-space"></span>')
                else:
                    key = lookup[letter]
                    if "item" in key:
                        item = key["item"]
                        itype = "item"
                    elif "tag" in key:
                        item = key["tag"]
                        itype = "tag"
                    else:
                        item = "?"
                        itype = "?"
                    buf.append('<span title="{tip}" class="item item-{item} item-type-{type}" style="background-image:url(&quot;/allotment/img/inventory_textures/{tex}.png&quot;)"></span>'.format(item=item, type=itype, tex=item.replace(":", "/"), tip=getLocalizedName(item)))
                buf.append("</td>")
            buf.append("</tr>")

        buf.append('</tbody></table></td>')

        buf.append('<td class="result">')
        buf.append('<div class="result-inner">')
        buf.append('<div class="result-slot">')
        buf.append('<span title="{tip}" class="item item-{item}" style="background-image:url(&quot;/allotment/img/inventory_textures/{tex}.png&quot;)"></span>'.format(item=recipe["result"]["item"], tex=recipe["result"]["item"].replace(":", "/"), tip=getLocalizedName(recipe["result"]["item"])))
        buf.append('</div>')
        buf.append('</div>')
        buf.append('</td>')

        buf.append('</tr></tbody></table>')
    elif recipe["type"] == "minecraft:crafting_shapeless" or recipe["type"] == "allotment:crafting_shapeless_with_tool":
        buf.append('<table class="crafting-recipe crafting-shapeless"><tbody><tr>')

        buf.append('<td><div class="crafting-ingredients">')

        ingredients = recipe["ingredients"]

        for ing in ingredients:
            key = ing
            if "item" in key:
                item = key["item"]
                itype = "item"
                itemMult = False
            elif "tag" in key:
                item = key["tag"]
                itype = "tag"
                itemMult = False
            elif isinstance(key, list):
                itemMult = True
            else:
                item = "?"
                itype = "?"
                itemMult = False
            buf.append('<div class="crafting-ingredient">')
            if not itemMult:
                buf.append('<span title="{tip}" class="item item-{item} item-type-{type}" style="background-image:url(&quot;/allotment/img/inventory_textures/{tex}.png&quot;)"></span>'.format(item=item, type=itype, tex=item.replace(":", "/"), tip=getLocalizedName(item)))
            else:
                buf.append('<span class="item item-type-multiple" style="--item-count:{}">'.format(len(key)))
                for e in key:
                    if "item" in e:
                        item = e["item"]
                        itype = "item"
                    elif "tag" in e:
                        item = e["tag"]
                        itype = "tag"
                    else:
                        item = "?"
                        itype = "?"
                    buf.append('<span title="{tip}" class="item item-child item-{item} item-type-{type}" style="background-image:url(&quot;/allotment/img/inventory_textures/{tex}.png&quot;)"></span>'.format(item=item, type=itype, tex=item.replace(":", "/"), tip=getLocalizedName(item)))
                buf.append('</span>')
            buf.append('</div>')

        buf.append('</div></td>')

        buf.append('<td class="result">')
        buf.append('<div class="result-inner">')
        buf.append('<div class="result-slot">')
        buf.append('<span title="{tip}" class="item item-{item}" style="background-image:url(&quot;/allotment/img/inventory_textures/{tex}.png&quot;)"></span>'.format(item=recipe["result"]["item"], tex=recipe["result"]["item"].replace(":", "/"), tip=getLocalizedName(recipe["result"]["item"])))
        buf.append('</div>')
        buf.append('</div>')
        buf.append('</td>')

        buf.append('</tr></tbody></table>')
    elif recipe["type"] == "minecraft:smelting":
        buf.append('<table class="crafting-recipe crafting-smelting"><tbody><tr>')

        buf.append('<td><div class="crafting-ingredients">')

        key = recipe["ingredient"]

        if "item" in key:
            item = key["item"]
            itype = "item"
        elif "tag" in key:
            item = key["tag"]
            itype = "tag"
        else:
            item = "?"
            itype = "?"
        buf.append('<div class="crafting-ingredient">')
        buf.append('<span title="{tip}" class="item item-{item} item-type-{type}" style="background-image:url(&quot;/allotment/img/inventory_textures/{tex}.png&quot;)"></span>'.format(item=item, type=itype, tex=item.replace(":", "/"), tip=getLocalizedName(item)))
        buf.append('</div>')

        buf.append('</div></td>')

        result = recipe["result"]

        buf.append('<td class="result">')
        buf.append('<div class="result-inner">')
        buf.append('<div class="result-slot">')
        buf.append('<span title="{tip}" class="item item-{item}" style="background-image:url(&quot;/allotment/img/inventory_textures/{tex}.png&quot;)"></span>'.format(item=result, tex=result.replace(":", "/"), tip=getLocalizedName(result)))
        buf.append('</div>')
        buf.append('</div>')
        buf.append('</td>')

        buf.append('</tr></tbody></table>')
    else:
        buf.append("TODO " + recipe["type"])

    return "\n".join(buf)


cfg = configparser.ConfigParser()
cfg.read("_wikigen.cfg")

recipesdir = cfg.get("paths", "recipesdir")
outputpath = cfg.get("paths", "outputpath")

flowerClassNames = [
    "team.mixxit.allotment.blocks.TintedDoublePlantBlock",
    "team.mixxit.allotment.blocks.OrientableFlower",
    "team.mixxit.allotment.blocks.ModFlowerBlock",
    "team.mixxit.allotment.blocks.ModSapling",
    "team.mixxit.allotment.blocks.SmallCactusBlock",
    "team.mixxit.allotment.blocks.ModTallFlowerBlockWithDye",
    "team.mixxit.allotment.blocks.ModMushroomBlock",
    "team.mixxit.allotment.blocks.ModVineBlock",
    "net.minecraft.block.TallFlowerBlock",
    "team.mixxit.allotment.blocks.TallThistleBlock"
]

ignoredClassNames = [
    "net.minecraft.block.FlowerPotBlock",
    "net.minecraft.item.BlockItem",
    "net.minecraft.item.BlockNamedItem"
]

reverserecipelookup = {}

_recipes = [getjson(os.path.join(recipesdir, f)) for f in os.listdir(recipesdir) if os.path.isfile(os.path.join(recipesdir, f))]
for r in _recipes:
    if resultisminecraft(r):
        continue

    key = getresult(r)
    if not key in reverserecipelookup:
        reverserecipelookup[key] = []
    
    reverserecipelookup[key].append(r)

datapath = cfg.get("paths", "datapath")
blocks = getjson(cfg.get("paths", "blocksjson"))
items = getjson(cfg.get("paths", "itemsjson"))
lang = getjson(cfg.get("paths", "langjson"))
mclang = getjson(cfg.get("paths", "mclangjson"))
fallbacklang = {
    "tag.minecraft.logs": "Any log"
}

for block in blocks:
    print(block["registryName"])

    buf = []

    blockBaseName = getidpath(block["registryName"])
    blockName = block["translationKey"]
    className = block["className"]

    if className in ignoredClassNames:
        continue

    try:
        blockName = lang[blockName]
    except KeyError:
        continue

    mmdCtx = {
        "localizedName": blockName
    }

    mmd = {}
    mmdPath = os.path.join(datapath, blockBaseName + ".mmd")
    if os.path.isfile(mmdPath):
        with open(mmdPath, "r", encoding="utf-8") as f:
            mmd = parseMmd(f.read(), mmdCtx)

    category = "block"

    if className in flowerClassNames:
        category = "flower"

    buf.append('---\n')
    buf.append('# Auto-generated by _wikigen.py. DO NOT EDIT DIRECTLY!\n')
    buf.append('# Instead, edit {}'.format(mmdPath))
    buf.append('\n')
    buf.append('layout: block\n')
    buf.append('category: {}\n'.format(category))
    buf.append('title: {}\n'.format(blockName))
    buf.append('mcid: {}\n'.format(blockBaseName))
    buf.append('sourcefile:\n')
    buf.append('  exists: {}\n'.format("true" if os.path.isfile(mmdPath) else "false"))
    buf.append('  fullpath: {}\n'.format(mmdPath.strip("/")))
    buf.append('  path: {}\n'.format(datapath.strip("/")))
    buf.append('  name: {}\n'.format(blockBaseName + ".mmd"))
    buf.append('todo: false\n')
    buf.append('---\n')
    buf.append('\n')
    # buf += '# ' + blockName + '\n\n'
    if "lead" in mmd:
        buf.append('{}\n\n'.format(mmd["lead"]))
    buf.append('<table class="block-info"><thead><tr>\n')
    buf.append('<th colspan=2>{}</th>\n'.format(blockName))
    buf.append('</tr></thead><tbody>\n')
    buf.append('<tr><td colspan=2 class="cell-image-big" style="text-align:center"><img src="/allotment/img/textures/allotment/{}.png" width="256" height="256" alt="" class="preview-icon"></td></tr>\n'.format(blockBaseName))
    buf.append('<tr><td colspan=2 class="cell-image-small" style="text-align:center"><img src="/allotment/img/inventory_textures/allotment/{}.png" width="32" height="32" alt="" class="inventory-icon"></td></tr>\n'.format(blockBaseName))
    buf.append('<tr><td colspan=2 style="text-align:center">{}</td></tr>\n'.format(getHarvestToolIcon(block)))
    buf.append('<tr><td>Hardness:</td><td>{}</td></tr>\n'.format(block["hardness"]))
    buf.append('<tr><td>Blast resistance:</td><td>{}</td></tr>\n'.format(block["blastResistance"]))
    buf.append('<tr><td>Luminant:</td><td>{}</td></tr>\n'.format("No" if block["lightLevel"] < 1 else "Yes ({})".format(block["lightLevel"])))
    buf.append('<tr><td>Stackable:</td><td>{}</td></tr>\n'.format("No" if block["maxStackSize"] < 2 else "Yes ({})".format(block["maxStackSize"])))
    buf.append('</tbody></table>\n\n')

    if block["registryName"] in reverserecipelookup:
        buf.append("## Crafting\n\n")
        for rec in reverserecipelookup[block["registryName"]]:
            buf.append(htmlFormatRecipe(rec))

    if "misc" in mmd:
        buf.append('{}\n\n'.format(mmd["misc"]))

    outname = os.path.join(outputpath, blockBaseName + ".md")
    with open(outname, "w", encoding="utf-8") as f:
        f.write("".join(buf))

for item in items:
    print(item["registryName"])
    print(" " * 8 + item["className"])

    buf = []

    itemBaseName = getidpath(item["registryName"])
    itemName = item["translationKey"]
    className = item["className"]

    if className in ignoredClassNames:
        continue

    try:
        itemName = lang[itemName]
    except KeyError:
        continue

    mmdCtx = {
        "localizedName": itemName
    }

    rarities = {
        "COMMON": "Common",
        "UNCOMMON": "Uncommon",
        "RARE": "Rare",
        "EPIC": "Epic"
    }

    mmd = {}
    mmdPath = os.path.join("data", itemBaseName + ".mmd")
    if os.path.isfile(mmdPath):
        with open(mmdPath, "r", encoding="utf-8") as f:
            mmd = parseMmd(f.read(), mmdCtx)

    category = "item"

    buf.append('---\n')
    buf.append('# Auto-generated by _wikigen.py. DO NOT EDIT DIRECTLY!\n')
    buf.append('# Instead, edit {}'.format(mmdPath))
    buf.append('\n')
    buf.append('layout: block\n')
    buf.append('category: ' + category + '\n')
    buf.append('title: ' + itemName + '\n')
    buf.append('mcid: ' + itemBaseName + '\n')
    buf.append('sourcefile:\n')
    buf.append('  exists: {}\n'.format("true" if os.path.isfile(mmdPath) else "false"))
    buf.append('  fullpath: {}\n'.format(mmdPath.strip("/")))
    buf.append('  path: {}\n'.format(datapath.strip("/")))
    buf.append('  name: {}\n'.format(blockBaseName + ".mmd"))
    buf.append('todo: false\n')
    buf.append('---\n')
    buf.append('\n')
    # buf += '# ' + blockName + '\n\n'
    if "lead" in mmd:
        buf.append('{}\n\n'.format(mmd["lead"]))
    buf.append('<table class="block-info"><thead><tr>\n')
    buf.append('<th colspan=2>{}</th>\n'.format(itemName))
    buf.append('</tr></thead><tbody>\n')
    buf.append('<tr><td colspan=2 class="cell-image-big" style="text-align:center"><img src="/allotment/img/inventory_textures/allotment/{}.png" width="256" height="256" alt="" class="preview-icon item-icon"></td></tr>\n'.format(itemBaseName))
    buf.append('<tr><td colspan=2 class="cell-image-small" style="text-align:center"><img src="/allotment/img/inventory_textures/allotment/{}.png" width="32" height="32" alt="" class="inventory-icon"></td></tr>\n'.format(itemBaseName))
    buf.append('<tr><td>Rarity:</td><td><span class="rarity-{RARITY}">{RARNAME}</span></td></tr>\n'.format(RARITY=item["rarity"].lower(), RARNAME=rarities[item["rarity"]]))
    buf.append('<tr><td>Stackable:</td><td>{}</td></tr>\n'.format("No" if item["maxStackSize"] < 2 else "Yes ({})".format(item["maxStackSize"])))
    buf.append('</tbody></table>\n\n')

    if item["registryName"] in reverserecipelookup:
        buf.append("## Crafting\n\n")
        for rec in reverserecipelookup[item["registryName"]]:
            buf.append(htmlFormatRecipe(rec))

    if "misc" in mmd:
        buf.append('{}\n\n'.format(mmd["misc"]))

    outname = os.path.join(outputpath, itemBaseName + ".md")
    with open(outname, "w", encoding="utf-8") as f:
        f.write("".join(buf))
