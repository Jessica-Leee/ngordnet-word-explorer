package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap map;

    public HistoryTextHandler(NGramMap map) {
        this.map = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder response = new StringBuilder();
        for (String word : words) {
            TimeSeries timeSeries = map.weightHistory(word, startYear, endYear);
            response.append(word).append(": {");
            for (int i = 0; i < timeSeries.size(); i++) {
                response.append(timeSeries.years().get(i)).append("=").append(timeSeries.data().get(i)).append(", ");
            }
            response.setLength(response.length() - 2);
            response.append("}\n");

        }
        return response.toString();
    }
}
