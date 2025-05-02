package com.example.aggregator.service;

import com.example.aggregator.client.AggregatorRestClient;
import com.example.aggregator.model.Entry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AggregatorService {

    private final AggregatorRestClient aggregatorRestClient;

    public AggregatorService(AggregatorRestClient aggregatorRestClient) {
        this.aggregatorRestClient = aggregatorRestClient;
    }

    public Entry getDefinitionFor(String word) {
        return aggregatorRestClient.getDefinitionFor(word);
    }

    public List<Entry> getWordsStartingWith(String chars) {
        return aggregatorRestClient.getWordsStartingWith(chars);
    }

    public List<Entry> getWordsEndingWith(String chars) {
        return aggregatorRestClient.getWordsEndingWith(chars);
    }

    public List<Entry> getWordsThatContain(String chars) {
        return aggregatorRestClient.getWordsThatContain(chars);
    }

    public List<Entry> getWordsThatContainSuccessiveLettersAndStartsWith(String chars) {
        List<Entry> wordsThatStartWith = aggregatorRestClient.getWordsStartingWith(chars);
        List<Entry> wordsThatContainSuccessiveLetters = aggregatorRestClient.getWordsThatContainConsecutiveLetters();
        List<Entry> common = new ArrayList<>(wordsThatStartWith);
        common.retainAll(wordsThatContainSuccessiveLetters);

        return common;
    }

    public List<Entry> getWordsThatContainSpecificConsecutiveLetters(String chars) {
        List<Entry> wordsThatContainSuccessiveLetters = aggregatorRestClient.getWordsThatContainConsecutiveLetters();

        List<Entry> common = new ArrayList<>(wordsThatContainSuccessiveLetters);
        common.removeIf(entry -> !entry.getWord().contains(chars));
        return common;
    }

    public List<Entry> getAllPalindromes() {
        List<Entry> candidates = new ArrayList<>();

        for (char ch = 'a'; ch <= 'z'; ch++) {
            String letter = String.valueOf(ch);
            List<Entry> startsWith = aggregatorRestClient.getWordsStartingWith(letter);
            List<Entry> endsWith = aggregatorRestClient.getWordsEndingWith(letter);

            for (Entry start : startsWith) {
                for (Entry end : endsWith) {
                    if (start.getWord().equalsIgnoreCase(end.getWord())) {
                        candidates.add(start);
                        break;
                    }
                }
            }
        }
        List<Entry> palindromes = new ArrayList<>();
        for (Entry entry : candidates) {
            String word = entry.getWord();
            String reversed = new StringBuilder(word).reverse().toString();
            if (word.equalsIgnoreCase(reversed)) {
                palindromes.add(entry);
            }
        }
        palindromes.sort(Comparator.comparing(Entry::getWord, String.CASE_INSENSITIVE_ORDER));
        return palindromes;
    }
}
