package main;

import browser.NgordnetQueryHandler;
import ngrams.Graph;
import ngrams.NGramMap;

public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        Graph x = new Graph(synsetFile, hyponymFile);
        NGramMap y = new NGramMap(wordFile, countFile);
        return new HyponymsHandler(x, y);
    }
}

