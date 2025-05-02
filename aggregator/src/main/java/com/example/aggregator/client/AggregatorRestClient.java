package com.example.aggregator.client;

import com.example.aggregator.model.Entry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AggregatorRestClient {

    private RestTemplate restTemplate;

    public AggregatorRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @CircuitBreaker(name = "getDefinitionForceCB", fallbackMethod = "fallbackGetDefinitionFor")
    public Entry getDefinitionFor(String word) {
        String uri = "http://localhost:9091/getWord/" + word;
         return restTemplate.getForObject(uri, Entry.class);
    }

    @CircuitBreaker(name = "fallbackGetWordsStartingWithCB", fallbackMethod = "fallbackGetWordsStartingWith")
    public List<Entry> getWordsStartingWith(String chars) {
        String uri = "http://localhost:9091/getWordsStartingWith/" + chars;
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);
        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray).collect(Collectors.toList());
    }
    @CircuitBreaker(name = "getWordsEndingWithCB", fallbackMethod = "fallbackGetWordsEndingWith")
    public List<Entry> getWordsEndingWith(String chars) {
        String uri = "http://localhost:9091/getWordsThatContain/" + chars;
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);
        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray).collect(Collectors.toList());
    }
    @CircuitBreaker(name = "getWordsThatContainCB", fallbackMethod = "getWordsThatContain")
    public List<Entry> getWordsThatContain(String chars) {
        String uri = "http://localhost:9091/getWordsThatContain/" + chars;
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);
        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray).collect(Collectors.toList());
    }
    @CircuitBreaker(name = "getWordsThatContainConsecutiveLettersCB", fallbackMethod = "fallbackGetWordsThatContainConsecutiveLetters")
    public List<Entry> getWordsThatContainConsecutiveLetters() {
        String uri = "http://localhost:9091/getWordsThatContainConsecutiveLetters";
        ResponseEntity<Entry[]> responseEntity = restTemplate.getForEntity(uri, Entry[].class);
        Entry[] entryArray = responseEntity.getBody();
        return Arrays.stream(entryArray).collect(Collectors.toList());
    }
    public Entry fallbackGetDefinitionFor(String word, Throwable t) {
        return new Entry();
    }
    public List<Entry> fallbackGetWordsStartingWith(String chars, Throwable t) {
        return List.of(new Entry());
    }
    public List<Entry> fallbackGetWordsEndingWith(String chars, Throwable t) {
        return List.of(new Entry());
    }
    public List<Entry> fallbackGetWordsThatContain(String chars, Throwable t) {
        return List.of(new Entry());
    }
    public List<Entry> fallbackGetWordsThatContainConsecutiveLetters(Throwable t) {
        return List.of(new Entry());
    }
}
