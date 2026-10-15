# Data and dependencies

## Bundled fixtures

The small files were copied unchanged from the local Fall 2024 course distribution. Both modules include `very_short.csv`, `less_short.csv`, `words_that_start_with_q.csv`, and `total_counts.csv`. The 2B module also includes `frequency-EECS.csv` and small matched synset/hyponym pairs for 11, 14, 16, and 1000-subgraph datasets, plus the synthetic EECS pair.

`very_short.csv` contains observations for `airport`, `request`, and `wandered`. The default 2A entry point already used this fixture in the recovered source. The default 2B entry point now uses the synthetic EECS fixture, which has matching frequency data for top-k demonstrations. Synthetic course fixture counts are examples, not observed English usage.

## Full course data

The large files are omitted from this archive:

- `top_14377_words.csv` (about 129 MB).
- `top_49887_words.csv` (about 352 MB).
- `shorter_words_file.csv` (about 11 MB, present only in the recovered 2A directory).
- Full WordNet `synsets.txt` and `hyponyms.txt`.

Obtain the data from the official [Project 2A setup page](https://fa24.datastructur.es/projects/proj2a/#getting-started) and [Project 2B setup page](https://fa24.datastructur.es/projects/proj2b/#skeleton-setup), which link to the course download bundles. Place `data/` alongside `src/` and `static/` in the corresponding module. The full-data filenames are ignored by Git.

For 2B, after obtaining the full data, run from the repository root:

```bash
./scripts/run.sh proj2b data/ngrams/top_14377_words.csv \
  data/wordnet/synsets.txt data/wordnet/hyponyms.txt
```

The three paths are interpreted relative to `proj2b/`, because the run script sets that working directory. `total_counts.csv` remains the module's counts file. Larger input files require more memory and load time; this archive's verification uses only the small fixtures.

For a larger 2A demo, change the `NGramMap` words-file argument in `proj2a/src/main/Main.java` from `SHORT_WORDS_FILE` to `TOP_14337_WORDS_FILE` or `TOP_49887_WORDS_FILE` after obtaining that file. Those constant names are retained from the recovered source, including the `14337` spelling.

## Java libraries

Set `NGORDNET_LIB` to an existing `library-fa24` directory containing the course JAR files. Alternatively, put those JARs in a local ignored `lib/` directory. The scripts do not download or install anything. Runtime dependencies include Princeton's `algs4`, Spark Java and Jetty, Gson, SLF4J, and XChart; the course library directory supplies their dependencies. Original JUnit/Truth tests require their corresponding course JARs as well.

The [official lab setup guide](https://fa24.datastructur.es/labs/lab01/#java-libraries) links the library distribution. Keep each dependency's upstream license and notices when obtaining or redistributing it.
