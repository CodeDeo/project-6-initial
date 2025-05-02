package com.example.aggregator.controller;

import com.example.aggregator.model.Entry;
import com.example.aggregator.service.AggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AggregatorController {
    private static final Logger log = LoggerFactory.getLogger(AggregatorController.class);
    private final AggregatorService service;

    public AggregatorController(AggregatorService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Entry> helloWorld() {
        return List.of(
                service.getDefinitionFor("hello"),
                service.getDefinitionFor("world")
        );

    }

    @GetMapping("/getDefinitionFor/{word}")
    public Entry getDefinitionFor(@PathVariable String word) {

        StopWatch sw = new StopWatch();
        sw.start();
        Entry result = service.getDefinitionFor(word);
        sw.stop();
        log.info("retrieved entry for [{}] in {}ms", word, sw.getTotalTimeMillis());
        return result;
    }
    @GetMapping("/getWordsStartingWith/{chars}")
    public List<Entry> getWordsStartingWith(@PathVariable String chars) {

        StopWatch sw = new StopWatch();
        sw.start();
        List<Entry> result = service.getWordsStartingWith(chars);
        sw.stop();
        log.info("Retrieved [{}] entries for words starting with [{}] in {}ms", result.size(), chars, sw.getTotalTimeMillis());
        return result;
    }

    @GetMapping("getWordsEndingWith/{chars}")
    public List<Entry> getWordsEndingWith(@PathVariable String chars) {

        StopWatch sw = new StopWatch();
        sw.start();
        List<Entry> result = service.getWordsEndingWith(chars);
        sw.stop();
        log.info("Retrieved [{}] entries for words ending with [{}] in {}ms", result.size(), chars, sw.getTotalTimeMillis());
        return result;
    }
    @GetMapping("getWordsThatContain/{chars}")
    public List<Entry> getWordsThatContain(@PathVariable String chars) {

        StopWatch sw = new StopWatch();
        sw.start();
        List<Entry> result = service.getWordsThatContain(chars);
        sw.stop();
        log.info("Retrieved [{}] entries for words that contain [{}] in {}ms", result.size(), chars, sw.getTotalTimeMillis());
        return result;
    }
    @GetMapping("getWordsThatContainSpecificConsecutiveLetters/{chars}")
    public List<Entry> getWordsThatContainSpecificConsecutiveLetters(@PathVariable String chars) {

        StopWatch sw = new StopWatch();
        sw.start();
        List<Entry> result = service.getWordsThatContainSpecificConsecutiveLetters(chars);
        sw.stop();
        log.info("Retrieved [{}] entries for words that contain specific consecutive letters [{}] in {}ms", result.size(), chars, sw.getTotalTimeMillis());
        return result;
    }
    @GetMapping("getWordsThatContainSuccessiveLettersAndStartWith/{chars}")
    public List<Entry> getWordsThatContainSuccessiveLettersAndStartWith(@PathVariable String chars) {

        StopWatch sw = new StopWatch();
        sw.start();
        List<Entry> result = service.getWordsThatContainSuccessiveLettersAndStartsWith(chars);
        sw.stop();
        log.info("Retrieved [{}] entries for words that contain specific consecutive letters and start with [{}] in {}ms", result.size(), chars, sw.getTotalTimeMillis());
        return result;
    }
    @GetMapping("getAllPalindromes")
    public List<Entry> getAllPalindromes() {

        StopWatch sw = new StopWatch();
        sw.start();
        List<Entry> result = service.getAllPalindromes();
        sw.stop();
        log.info("Retrieved [{}] entries for all palindromes in {}ms", result.size(), sw.getTotalTimeMillis());
        return result;
    }

}
