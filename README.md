# Ngordnet Word Explorer

Java coursework from **UC Berkeley CS 61B, Fall 2024**, combining Project 2A (NGrams) and Project 2B (WordNet) in one private archive.

| Module | Implemented behavior |
| --- | --- |
| `proj2a` | Year-indexed time series, word-count and relative-frequency queries, text responses, and PNG history charts. |
| `proj2b` | Directed synset graph traversal, single-word hyponyms, intersections for multiple input words, and frequency-based top-k selection over a year range. |

The modules retain their original separate Java packages and source trees. Compile and run **one module at a time**; both define classes such as `main.Main` and `ngrams.NGramMap`. The browser interface, server framework, plotting support, and some tests originate from course scaffolding. Existing source attributions are preserved.

## Run locally

Prerequisites: Bash, a JDK (verified with OpenJDK 23.0.1), and the CS 61B Fall 2024 `library-fa24` JAR directory. JARs are intentionally kept outside this repository. Use an existing local copy, or follow the [official Java-library setup instructions](https://fa24.datastructur.es/labs/lab01/#java-libraries). The official library repository is [Berkeley-CS61B/library-fa24](https://github.com/Berkeley-CS61B/library-fa24).

From the repository root:

```bash
export NGORDNET_LIB="/absolute/path/to/library-fa24"
# Set JAVA_HOME if the intended JDK is not already on PATH.
./scripts/check.sh
./scripts/run.sh proj2a
```

Open [localhost:4567/ngordnet_2a.html](http://localhost:4567/ngordnet_2a.html). With the bundled small dataset, enter `request, airport`, start `2006`, end `2007`, then select **history** or **history (text)**. Stop the process with Ctrl-C before starting the other module:

```bash
./scripts/run.sh proj2b
```

Open [localhost:4567/ngordnet.html](http://localhost:4567/ngordnet.html). The default 2B demo uses the small **synthetic EECS course fixture**, rather than the full WordNet corpus. Enter `CS61A`, start `2018`, end `2018`, k `2`, and click **hyponyms**. The expected result is `[CS61A, CS61B]`. Use k `0` to return all matching words without frequency ranking.

Only the **hyponyms** control is connected in the 2B module. The preserved course interface also shows history, hypohist, and common-ancestors controls; those routes are not implemented in this module. Use 2A for word-history plots. Query matching follows the dataset's spelling and case.

To try the included English synset fixture with k `0`:

```bash
./scripts/run.sh proj2b data/ngrams/very_short.csv \
  data/wordnet/synsets16.txt data/wordnet/hyponyms16.txt
```

Enter `act`, or `occurrence, change`. This fixture's words have little overlap with `very_short.csv`, so it is intended for k `0` queries. See [DATA.md](DATA.md) for full datasets and frequency-ranked English queries.

## Verification and archive changes

`scripts/check.sh` compiles each module separately and runs small-data checks for time-series arithmetic, count lookup, text output, PNG encoding, graph traversal, multi-word intersection, top-k selection, and year filtering. These checks passed with the existing local course libraries and JDK 23. The original course tests remain in each module's `tests/` directory; some require the omitted large datasets and were not run as a complete suite.

The only change to copied application source is `proj2b/src/main/Main.java`: it now connects the existing `HyponymsHandler` to `/hyponyms`, uses the included EECS fixtures by default, and accepts optional data-file paths. The original graph, time-series, and ranking implementations are unchanged. Build scripts and verification checks were added for this archive. See [VALIDATION.md](VALIDATION.md) for the results and limits.

The recovered 2A text handler has a known formatting issue when the selected word has no observations in the requested years. That legacy edge case is documented in the validation record and was left unchanged.

## Private coursework archive

**Keep this repository private. Do not publish its solution code or share it with current or future students.** The [Fall 2024 course policy](https://fa24.datastructur.es/policies/#academic-misconduct) prohibits posting solutions online; the [setup guide](https://fa24.datastructur.es/labs/lab01/#git-and-remote-repos) specifically identifies public solution repositories as a violation. Private archiving does not grant permission for broader redistribution.

This repository combines student implementations, supplied course material, small course data fixtures, and third-party front-end code. No blanket open-source license has been added. See [PROVENANCE.md](PROVENANCE.md).
