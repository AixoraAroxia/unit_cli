package com.unitTestGenerator.util.random.sevicesrandomimplement;

import com.unitTestGenerator.ioc.anotations.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class RealWordTextRandom {

    private static final List<String> WORD_BANK = Arrays.asList(
            "hello", "goodbye", "please", "thank", "sorry", "yes", "no", "maybe", "sure", "welcome",
            "morning", "afternoon", "evening", "night", "day", "week", "month", "year", "today", "tomorrow",
            "friend", "love", "happy", "sad", "angry", "excited", "tired", "hungry", "thirsty", "bored",
            "house", "home", "apartment", "building", "street", "park", "beach", "mountain", "river", "lake",
            "school", "university", "library", "hospital", "airport", "station", "restaurant", "hotel", "store", "market",
            "business", "company", "factory", "office", "shop", "warehouse", "bank", "farm", "mine", "construction",
            "transportation", "energy", "technology", "finance", "education", "healthcare", "media", "tourism", "retail", "telecom",
            "mother", "father", "brother", "sister", "son", "daughter", "grandmother", "grandfather", "uncle", "aunt",
            "cousin", "nephew", "niece", "husband", "wife", "child", "parent", "baby", "teenager", "adult",
            "eat", "drink", "run", "walk", "talk", "listen", "watch", "see", "hear", "sleep",
            "read", "write", "buy", "sell", "pay", "work", "study", "drive", "travel", "cook",
            "big", "small", "fast", "slow", "hot", "cold", "hard", "soft", "strong", "weak",
            "beautiful", "ugly", "clean", "dirty", "new", "old", "happy", "sad", "easy", "difficult",
            "car", "bike", "bus", "train", "plane", "phone", "computer", "television", "radio", "lamp",
            "table", "chair", "door", "window", "bed", "mirror", "book", "pen", "bag", "shoes",
            "sun", "moon", "star", "cloud", "rain", "snow", "wind", "tree", "flower", "grass",
            "rock", "sand", "ocean", "island", "forest", "desert", "valley", "hill", "waterfall", "cave"
    );

    private static final Random random = new Random();

    public RealWordTextRandom() {
    }

    public String generateMeaningfulText(int wordCount) {
        Collections.shuffle(WORD_BANK);
        List<String> selectedWords = WORD_BANK.stream().limit(wordCount).collect(Collectors.toList());
        return processText(selectedWords);
    }

    private static String processText(List<String> words) {
        if(words.isEmpty()) return "";
        words.set(0, capitalize(words.get(0)));
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < words.size(); i++) {
            text.append(words.get(i));
            if(i < words.size() - 1) {
                double chance = random.nextDouble();
                if(chance < 0.15) text.append(", ");
                else if(chance < 0.2) text.append("; ");
                else text.append(" ");
            }
        }
        char[] endPunctuation = {'.', '!', '?'};
        text.append(endPunctuation[random.nextInt(endPunctuation.length)]);
        return text.toString();
    }

    private static String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }


}