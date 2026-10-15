package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.Graph;
import ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final NGramMap map;
    private final Graph graph;

    public HyponymsHandler(Graph x, NGramMap y) {
        this.graph = x;
        this.map = y;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();

        if (words == null || words.isEmpty()) {
            return "[]";
        }

        List<String> hyponyms = graph.findHyponyms(words);
        if (hyponyms.isEmpty()) {
            return "[]";
        }

        if (k == 0) {
            Collections.sort(hyponyms);
            return formatResponse(hyponyms);
        }

        Map<String, Double> wordCounts = new HashMap<>();
        for (String word : hyponyms) {
            TimeSeries history = map.countHistory(word, startYear, endYear);
            if (history != null) {
                double count = 0;
                for (double value : history.values()) {
                    count += value;
                }
                if (count > 0) {
                    wordCounts.put(word, count);
                }
            }
        }

        if (wordCounts.isEmpty()) {
            return "[]";
        }

        List<String> sortedWords = new ArrayList<>(wordCounts.keySet());

        sortedWords.sort((a, b) -> wordCounts.get(b).compareTo(wordCounts.get(a)));
        if (k > 0 && k < sortedWords.size()) {
            sortedWords = sortedWords.subList(0, k);
        }
        Collections.sort(sortedWords);

        return sortedWords.toString();
    }

    private String formatResponse(List<String> words) {
        if (words.isEmpty()) {
            return "[]";
        }
        return words.toString();
    }
}
