import browser.NgordnetQuery;
import main.HistoryHandler;
import main.HistoryTextHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import java.util.Base64;
import java.util.List;

/** Small-data archive check; independent of the original course autograder. */
public class SmokeCheck {
    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void main(String[] args) {
        TimeSeries a = new TimeSeries();
        a.put(2006, 2.0);
        TimeSeries b = new TimeSeries();
        b.put(2006, 3.0);
        b.put(2007, 7.0);
        require(a.plus(b).get(2006) == 5.0, "TimeSeries overlapping-year sum");
        require(a.plus(b).get(2007) == 7.0, "TimeSeries sparse-year sum");

        NGramMap map = new NGramMap("data/ngrams/very_short.csv", "data/ngrams/total_counts.csv");
        require(map.countHistory("request", 2006, 2007).years().equals(List.of(2006, 2007)),
                "Count history year range");
        require(map.countHistory("request", 2006, 2007).get(2006) == 677820.0,
                "Count history fixture value");
        require(map.countHistory("missing-word").isEmpty(), "Unknown-word history");

        NgordnetQuery q = new NgordnetQuery(List.of("request", "airport"), 2006, 2007, 0);
        String expected = "request: {2006=2.44740192927834E-5, 2007=2.464488338318067E-5}\n"
                + "airport: {2007=6.2068176510855946E-6}\n";
        require(new HistoryTextHandler(map).handle(q).equals(expected), "History text output");
        byte[] image = Base64.getDecoder().decode(new HistoryHandler(map).handle(q));
        require(image.length > 8 && image[0] == (byte) 0x89 && image[1] == 'P'
                && image[2] == 'N' && image[3] == 'G', "History plot PNG encoding");
        System.out.println("proj2a: sparse time series, count lookup, text response, PNG plot passed.");
    }
}
