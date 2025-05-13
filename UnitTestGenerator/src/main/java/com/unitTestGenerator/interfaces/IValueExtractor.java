package com.unitTestGenerator.interfaces;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface IValueExtractor {

    default String extractListContent(String input) {
        String regex = "<(.*?)>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    default String extractMapContent(String input) {

        String regex = "(?:Map<([^,]+),([^>]+)>|Map\\s*\\([^)]+\\))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String type = matcher.group(1);
            if (type.contains(",")) {
                String[] parts = type.split(",");
                String keyType = parts[0].trim();
                String valueType = parts[1].trim();
                return String.format("Key: %s, Value: %s", keyType, valueType);
            }
        }
        return "null";
    }

    default String getKey(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        String[] parts = input.split(",");
        if (parts.length < 2) {
            return "";
        }
        String keyPart = parts[0].trim();
        if (keyPart.startsWith("Key: ")) {
            return keyPart.substring("Key: ".length()).trim();
        }
        return "";
    }

    default String getValue(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        String[] parts = input.split(",");
        if (parts.length < 2) {
            return "";
        }
        String valuePart = parts[1].trim();
        if (valuePart.startsWith("Value: ")) {
            return valuePart.substring("Value: ".length()).trim();
        }
        return "";
    }


}
