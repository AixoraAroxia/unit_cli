package com.unitTestGenerator.analyzers.services.interfaces;
import java.util.regex.*;

public interface ITodoDetectorService {

    default String getTodo(String text) {
        StringBuffer buffer = new StringBuffer();

        Pattern pattern = Pattern.compile("//TODO|//todo", Pattern.CASE_INSENSITIVE);
        String[] lines = text.split("\\n");

        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = pattern.matcher(lines[i]);
            if (matcher.find()) {
                buffer.append("Line " + (i + 1) + ": " + lines[i]).append("\n");
            }
        }
        return buffer.toString();
    }
}
