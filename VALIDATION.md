# Validation record

Environment: macOS, OpenJDK 23.0.1, and the existing local CS 61B `library-fa24` JAR directory. No dependencies were installed or downloaded for verification.

`./scripts/check.sh` passed for both modules:

| Module | Checks |
| --- | --- |
| 2A | Separate source compilation; sparse time-series addition; known count values and year slicing; unknown-word count lookup; exact weighted-history text output; decoding a generated chart as PNG. |
| 2B | Separate source compilation; transitive single-word hyponyms; multi-word intersection; unknown-word response; top-k ranking with counts above signed 32-bit integer range; empty result outside the available years. |

The compiler emits existing unchecked-operation notes for `plotting/Plotter.java`. These notes do not prevent compilation or the PNG check.

An HTTP startup attempt reached Spark/Jetty initialization, but this execution sandbox denied binding port 4567 (`SocketException: Operation not permitted`). Browser/server end-to-end operation was therefore not verified here. Direct calls to the real handlers passed the checks above. The run scripts use each module as the working directory so its `data/` and `static/` paths resolve correctly.

The original course JUnit tests are preserved but were not run as a complete suite. Some depend on the omitted large data files. Full-data performance, every edge case, and equivalence to the final historical Gradescope submission have not been established. The preserved 2B interface has additional unsupported controls; the README identifies the working hyponyms route and the separate 2A history module.

One existing 2A formatting edge case was confirmed separately: querying `request` for 1800–1801 with the bundled fixture produces `request:}` rather than an empty `{}` history. `HistoryTextHandler` removes trailing characters even when no years were appended. This source was left unchanged to preserve the recovered implementation; use the documented 2006–2007 sample for the small-data demo.
