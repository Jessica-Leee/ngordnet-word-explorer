package ngrams;
import java.util.*;
import edu.princeton.cs.algs4.In;

public class Graph {
    private final HashMap<Integer, Set<Integer>> numOrder;
    private final HashMap<Integer, Set<String>> idToWords;
    private final HashMap<String, Set<Integer>> wordToIDs;

    public Graph(String synsetsFile, String hyponymsFile) {
        numOrder = new HashMap<>();
        wordToIDs = new HashMap<>();
        idToWords = new HashMap<>();
        In synsetFile = new In(synsetsFile);
        while (synsetFile.hasNextLine()) {
            String line = synsetFile.readLine();
            String[] splitLine = line.split(",");
            int id = Integer.parseInt(splitLine[0]);
            String[] words = splitLine[1].trim().split("\\s+");

            for (String word : words) {
                if (!word.isEmpty()) {
                    idToWords.putIfAbsent(id, new HashSet<>());
                    idToWords.get(id).add(word);
                    wordToIDs.putIfAbsent(word, new HashSet<>());
                    wordToIDs.get(word).add(id);
                }
            }
        }

        In hyponymFile = new In(hyponymsFile);
        while (hyponymFile.hasNextLine()) {
            String line = hyponymFile.readLine();
            String[] splitLine = line.split(",");
            int hypernym = Integer.parseInt(splitLine[0]);
            numOrder.putIfAbsent(hypernym, new HashSet<>());

            for (int i = 1; i < splitLine.length; i++) {
                numOrder.get(hypernym).add(Integer.parseInt(splitLine[i]));
            }
        }
    }

    public List<String> findHyponyms(String word) {
        if (word == null || word.isEmpty() || !wordToIDs.containsKey(word)) {
            return new ArrayList<>();
        }

        Set<String> hyponymsSet = new HashSet<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> wordIDs = wordToIDs.get(word);

        for (int id : wordIDs) {
            findHyponymsHelper(id, hyponymsSet, visited);
        }

        List<String> sortedHyponyms = new ArrayList<>(hyponymsSet);
        Collections.sort(sortedHyponyms);
        return sortedHyponyms;
    }

    private void findHyponymsHelper(int currentID, Set<String> hyponyms, Set<Integer> visited) {
        if (visited.contains(currentID)) {
            return;
        }

        visited.add(currentID);

        if (idToWords.containsKey(currentID)) {
            hyponyms.addAll(idToWords.get(currentID));
        }

        if (numOrder.containsKey(currentID)) {
            for (int childID : numOrder.get(currentID)) {
                findHyponymsHelper(childID, hyponyms, visited);
            }
        }
    }

    public List<String> findHyponyms(List<String> words) {
        if (words == null || words.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> result = findHyponyms(words.get(0));
        if (result.isEmpty()) {
            return result;
        }

        for (int i = 1; i < words.size(); i++) {
            List<String> nextHyponyms = findHyponyms(words.get(i));
            if (nextHyponyms.isEmpty()) {
                return new ArrayList<>();
            }
            result.retainAll(nextHyponyms);
            if (result.isEmpty()) {
                return result;
            }
        }

        Collections.sort(result);
        return result;
    }
}
