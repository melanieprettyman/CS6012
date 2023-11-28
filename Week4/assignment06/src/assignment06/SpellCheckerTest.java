package assignment06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {
    SpellChecker spellChecker;

    List<String> words;
    @BeforeEach
    void setUp() {
        spellChecker = new SpellChecker();
        spellChecker.addToDictionary("apple");
        spellChecker.addToDictionary("banana");
        spellChecker.addToDictionary("cherry");
        spellChecker.addToDictionary("and");
        spellChecker.addToDictionary("a");

    }

    @Test
    void removeFromDictionary() {
        List<String> initialWords = Arrays.asList("apple", "banana", "orange", "grape");
        SpellChecker spellChecker = new SpellChecker(initialWords);

        // Verify words exist before removal using spellCheck method
        List<String> misspelledBeforeRemoval = spellChecker.spellCheck(new File("document.txt"));
        assertTrue(misspelledBeforeRemoval.isEmpty(), "No misspelled words before removal");

        // Remove words
        spellChecker.removeFromDictionary("apple");
        spellChecker.removeFromDictionary("banana");

        // Verify removal using spellCheck method
        List<String> misspelledAfterRemoval = spellChecker.spellCheck(new File("document.txt"));
        assertTrue(misspelledAfterRemoval.contains("apple"));
        assertTrue(misspelledAfterRemoval.contains("banana"));
    }

    @Test
    void spellCheck() {
        List<String> misspelledWords = spellChecker.spellCheck(new File("test.txt"));

        List<String> expectedMisspelledWords = Arrays.asList("aple", "banan", "chery");
        assertEquals(expectedMisspelledWords, misspelledWords);
    }
}