import browser.NgordnetQuery;
import main.AutograderBuddy;
import main.HyponymsHandler;
import ngrams.Graph;
import ngrams.NGramMap;
import java.util.List;

/** Small-data archive check; independent of the original course autograder. */
public class SmokeCheck {
    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        var handler = AutograderBuddy.getHyponymsHandler("data/ngrams/very_short.csv",
                "data/ngrams/total_counts.csv", "data/wordnet/synsets16.txt",
                "data/wordnet/hyponyms16.txt");
        require(handler.handle(new NgordnetQuery(List.of("act"), 0, 0, 0)).equals(
                "[act, action, change, demotion, human_action, human_activity, variation]"),
                "Single-word transitive hyponyms");
        require(handler.handle(new NgordnetQuery(List.of("occurrence", "change"), 0, 0, 0)).equals(
                "[alteration, change, increase, jump, leap, modification, saltation, transition]"),
                "Multi-word hyponym intersection");
        require(handler.handle(new NgordnetQuery(List.of("missing-word"), 0, 0, 0)).equals("[]"),
                "Unknown-word response");

        var eecs = new HyponymsHandler(new Graph("data/wordnet/synsets-EECS.txt",
                "data/wordnet/hyponyms-EECS.txt"), new NGramMap("data/ngrams/frequency-EECS.csv",
                "data/ngrams/total_counts.csv"));
        require(eecs.handle(new NgordnetQuery(List.of("CS61A"), 2018, 2018, 2)).equals("[CS61A, CS61B]"),
                "Top-k counts above signed-int range");
        require(eecs.handle(new NgordnetQuery(List.of("CS61A"), 2000, 2001, 2)).equals("[]"),
                "No counts in selected year range");
        System.out.println("proj2b: graph reachability, intersection, missing word, top-k, year filter passed.");
    }
}
