package main;

import browser.NgordnetServer;
import org.slf4j.LoggerFactory;

public class Main {
    // ngrams files
    public static final String VERY_SHORT_WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    private static final String SMALL_WORDS_FILE = "data/ngrams/top_14377_words.csv";
    private static final String WORDS_FILE = "data/ngrams/top_49887_words.csv";

    // wordnet Files
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";
    private static final String HYPONYMS_FILE_SUBSET = "data/wordnet/hyponyms1000-subgraph.txt";
    private static final String SYNSETS_FILE_SUBSET = "data/wordnet/synsets1000-subgraph.txt";

    // EECS files
    private static final String FREQUENCY_EECS_FILE = "data/ngrams/frequency-EECS.csv";
    private static final String HYPONYMS_EECS_FILE = "data/wordnet/hyponyms-EECS.txt";
    private static final String SYNSETS_EECS_FILE = "data/wordnet/synsets-EECS.txt";

    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    public static void main(String[] args) {
        if (args.length != 0 && args.length != 3) {
            throw new IllegalArgumentException("Expected no arguments, or: wordsFile synsetsFile hyponymsFile");
        }
        // Archive preparation: connect the existing implementation to the demo UI.
        // The bundled EECS fixtures keep the default demo small and self-contained.
        String wordsFile = args.length == 3 ? args[0] : FREQUENCY_EECS_FILE;
        String synsetsFile = args.length == 3 ? args[1] : SYNSETS_EECS_FILE;
        String hyponymsFile = args.length == 3 ? args[2] : HYPONYMS_EECS_FILE;
        NgordnetServer hns = new NgordnetServer();

        hns.startUp();
        hns.register("hyponyms", AutograderBuddy.getHyponymsHandler(
                wordsFile, TOTAL_COUNTS_FILE, synsetsFile, hyponymsFile));
        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
