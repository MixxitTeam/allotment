#!/usr/bin/env python3

import configparser
import os
import json
import re
import colorama
import io
import math
import argparse


parser = argparse.ArgumentParser()
parser.add_argument("-a", "--action", required=True)
parser.add_argument("--overwrite", action="store_true")
parser.add_argument("--filter")
args = parser.parse_args()

colorama.init()

def unique(seq):
    seen = set()
    seen_add = seen.add
    return [x for x in seq if not (x in seen or seen_add(x))]

#region Class definitions
class StringBuilder:
    _file_str = None

    def __init__(self):
        self._file_str = io.StringIO()

    def append(self, str):
        self._file_str.write(str)

    def appendLine(self, str):
        self._file_str.write(str + "\n")

    def __str__(self):
        return self._file_str.getvalue()

class Catalog:
    __cat_list = []
    __tab_re = None

    def __init__(self):
        self.__tab_re = re.compile(r"\t+")

    def keys(self):
        return [i["content"][0] for i in self.__cat_list if i["type"] == "entry"]

    def fields_as_list(self) -> list:
        return [i["content"] for i in self.__cat_list if i["type"] == "entry"]

    def fields_as_dict(self) -> dict:
        return {i["content"][0]: i["content"][1:] for i in self.__cat_list if i["type"] == "entry"}
    
    def parse(self, contents, empty = True):
        if empty:
            self.empty()
        lines = contents.splitlines()
        for ln in lines:
            if ln.startswith("#") or len(ln.strip()) < 1:
                self.add_ignored_line(ln)
            else:
                self.add_entry(ln)

    def empty(self):
        self.__cat_list = []

    def add_ignored_line(self, ln):
        self.__cat_list.append({
            "type": "ignored",
            "content": None,
            "line": ln
        })
    
    def add_entry(self, ln):
        self.__cat_list.append({
            "type": "entry",
            "content": self.__tab_re.split(ln),
            "line": ln
        })
    
    def __getitem__(self, arg) -> list:
        for l in self.__cat_list:
            if l["type"] == "entry" and l["content"][0] == arg:
                return l["content"]
        return None

    def __str__(self) -> str:
        sb = StringBuilder()

        keylens = [len(k) for k in self.keys()]
        max_tab_count = math.ceil((max(keylens) + 1) / 8) if len(keylens) > 0 else 1

        for e in self.__cat_list:
            if e["type"] == "ignored":
                sb.appendLine(e["line"])
            else:
                thiscount = math.ceil((len(e["content"][0]) + 1) / 8)
                nen = [e["content"][0] + ("\t"*(max_tab_count-thiscount))] + e["content"][1:]
                sb.appendLine("\t".join(nen))
        
        return str(sb)
#endregion

#region Helper methods
def getIds(lst):
    regex = re.compile(r"^(item|block)\.allotment\.")
    nlst = []
    for s in lst:
        if not regex.search(s):
            continue
        nlst.append(regex.sub("", s))
    return unique(nlst)

def parse_catalog() -> Catalog:
    global catalog_file

    with open(catalog_file, "r") as f:
        catalog_contents = f.read()
    
    catalog = Catalog()
    catalog.parse(catalog_contents)

    return catalog

def write_catalog(cat) -> Catalog:
    global catalog_file

    with open(catalog_file, "w") as f:
        f.write(str(cat))

def getTranslatedName(mcid):
    global lang_dict

    for n in ["block", "item"]:
        key = n + ".allotment." + mcid

        if key in lang_dict:
            return lang_dict[key]
    
    return mcid


def indent_log_message(msg) -> str:
    lines = msg.splitlines()
    return ("\n" + (" " * 9)).join(lines)

def log_error(msg):
    print("[" + colorama.Fore.RED + "ERROR" + colorama.Style.RESET_ALL + "]: " + colorama.Fore.RED + indent_log_message(msg) + colorama.Style.RESET_ALL)

def log_ok(msg):
    print("   [" + colorama.Fore.GREEN + "OK" + colorama.Style.RESET_ALL + "]: " + colorama.Fore.GREEN + indent_log_message(msg) + colorama.Style.RESET_ALL)

def log_warn(msg):
    print(" [" + colorama.Fore.YELLOW + "WARN" + colorama.Style.RESET_ALL + "]: " + colorama.Fore.YELLOW + indent_log_message(msg) + colorama.Style.RESET_ALL)

def log_normal(msg):
    print("  [" + colorama.Fore.WHITE + "LOG" + colorama.Style.RESET_ALL + "]: " + colorama.Fore.WHITE + indent_log_message(msg) + colorama.Style.RESET_ALL)

def log_info(msg):
    print(" [" + colorama.Fore.CYAN + "INFO" + colorama.Style.RESET_ALL + "]: " + colorama.Fore.CYAN + indent_log_message(msg) + colorama.Style.RESET_ALL)

def log_debug(msg):
    print("[" + colorama.Fore.MAGENTA + "DEBUG" + colorama.Style.RESET_ALL + "]: " + colorama.Fore.MAGENTA + indent_log_message(msg) + colorama.Style.RESET_ALL)
#endregion

config = configparser.ConfigParser()
config.read("allotmentconfig.cfg")

allotment_dir = config["GLOBAL"]["allotment_repo_dir"]
pages_dir = config["pages"]["page_output_dir"]
pages_ext = config["pages"]["page_extension"]
preview_texture_dir = config["textures"]["preview_texture_dir"]
inventory_texture_dir = config["textures"]["inventory_texture_dir"]

lang_file = os.path.join(allotment_dir, "src/main/resources/assets/allotment/lang/en_us.json")
catalog_file = "CATALOG.txt"

cat = parse_catalog()

with open(lang_file) as f:
    lang_dict = json.load(f)

page_names = getIds(lang_dict.keys())

#region Action methods
def a_checkpages(args):
    global page_names, pages_dir, pages_ext

    page_count_exist = 0
    page_count_total = len(page_names)

    for p in page_names:
        ppath = os.path.join(pages_dir, p + pages_ext)
        if not os.path.isfile(ppath):
            log_error("Page does not exist for block/item: " + p)
        else:
            log_ok("Page exists for " + p)
            page_count_exist += 1

    print("="*40)

    if page_count_total == page_count_exist:
        log_ok("All pages exist")
        return 0
    else:
        log_error("There are " + str(page_count_total - page_count_exist) + " out of " + str(page_count_total) +  " page(s) missing!")
        return 1

def a_generatepages(args):
    global cat, page_names, pages_dir, pages_ext

    pages = cat.keys()

    if not os.path.isdir(pages_dir):
        os.makedirs(pages_dir)

    for p in pages:
        ppath = os.path.join(pages_dir, p + pages_ext)
        if not os.path.isfile(ppath) or args.overwrite:
            log_ok("Creating page " + p + pages_ext)
            # log_debug(ppath)
            with open(ppath, "w") as f:
                f.write("---\nlayout: block\ntitle: {title}\ncategory: {category}\nmcid: {mcid}\ntodo: true\n---\n\n\n**TODO**\n".format(
                    title=getTranslatedName(p),
                    category=cat[p][1] if len(cat[p]) > 1 else "null",
                    mcid=p
                ))
        else:
            log_info("Page already exists: " + p + pages_ext)
    
    return 0

def a_checkcatalog(args):
    global cat, page_names

    keys = cat.keys()

    returnValue = 0

    for p in page_names:
        if p not in keys:
            log_error("ID " + p + " not in catalog")
            returnValue = 1
        else:
            log_ok("ID " + p + " found in catalog")

    return returnValue


def a_updatecatalog(args):
    global cat, page_names

    keys = cat.keys()

    for p in page_names:
        if p not in keys:
            print("Adding id " + p)
            cat.add_entry("\t".join([p, "TODO"]))
    
    return 0

def a_checktextures(args):
    global cat, preview_texture_dir, inventory_texture_dir

    keys = cat.keys()
    prevDir = os.path.realpath(preview_texture_dir)
    invDir = os.path.realpath(inventory_texture_dir)

    returnValue = 0

    ofilter = [] if args.filter == None else args.filter.split(",")

    for k in keys:
        prevPath = os.path.join(prevDir, k + ".png")
        invPath = os.path.join(invDir, k + ".png")

        prevExists = os.path.isfile(prevPath)
        invExists = os.path.isfile(invPath)

        if not prevExists and not invExists:
            if "all" in ofilter:
                log_error("Both images for {} missing!".format(k))
                returnValue = 1
        elif not prevExists and invExists:
            if "preview" in ofilter:
                log_error("Preview image for {} missing, but inventory image exists!".format(k))
                returnValue = 1
        elif prevExists and not invExists:
            if "inventory" in ofilter:
                log_error("Inventory image for {} missing, but preview image exists!".format(k))
                returnValue = 1
        else:
            if "none" in ofilter:
                log_ok("Both textures exist for {}".format(k))
    
    return returnValue
#endregion

_ACTIONS_ = {
    "checkpages": a_checkpages,
    "checkcatalog": a_checkcatalog,
    "updatecatalog": a_updatecatalog,
    "generatepages": a_generatepages,
    "checktextures": a_checktextures,
}

actionMethod = _ACTIONS_.get(args.action, None)

returnValue = 0

if actionMethod == None:
    log_error("Invalid action: " + args.action)
    log_error("Valid actions are:\n" + "\n".join(_ACTIONS_.keys()))
    returnValue = 2
else:
    returnValue = actionMethod(args)

write_catalog(cat)

exit(returnValue)
