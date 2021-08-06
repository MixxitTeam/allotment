# Allotment Wiki

## Development

### Dependencies

You need the following things installed on your development machine:

- Jekyll
- Python 3.8.x
- NodeJS (optional, to build SimpleJekyllSearch<sup name="a1">[1](#f1)</sup>)

You also need the [main Allotment repository](https://github.com/MixxitTeam/Allotment) and the [wikirender repository](https://github.com/MixxitTeam/wikirender).

First of all, edit the `allotmentconfig.cfg` file in the root of this repository.  
Under `[GLOBAL]` change the `allotment_repo_dir` to point to the *Allotment* repo, e.g. `E:\repos\Allotment` or `~/Allotment/`. Do the same for `wikirender_repo_dir` and point it to the *wikirender* repo.

Then install the required Python packages:

```
pip install colorama
```

### Managing pages

The bulk of the data is managed by the `pagegen.py` script. If you are on anything other than Windows, 
you first have to make that script executable by using

```
$ chmod +x ./pagegen.py
```

First, check which pages are already listed inside of the catalog:

```
pagegen.py -a checkcatalog
```

To populate the page catalog (more on that below), run the following command:

```
pagegen.py -a updatecatalog
```

This will populate the text file `CATALOG.txt` which contains the categories of each page.

When you have populated all the categories inside of the catalog, you can create all page skeletons
automatically by using

```
pagegen.py -a generatepages
```

All you have to do then is to fill the pages with content!

If you are using *wikirender*, this script can also automatically check for any missing preview renders:

```
pagegen.py -a checktextures
```

<small><b name="f1">1:</b> This wiki uses a patched version of SimpleJekyllSearch. See [this pull request](https://github.com/christian-fei/Simple-Jekyll-Search/pull/178). Until the pull request will be merged, please [compile the fork from source](https://github.com/jonaskohl/Simple-Jekyll-Search). <a href="#a1">↩</a></small>
