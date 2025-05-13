package com.unitTestGenerator.util.interfaces;

import java.util.List;

public interface IStringEnsambleService {

    default String stringEnsamble(List<String> stringPaths) {
        StringBuffer newString = new StringBuffer();
        stringPaths.stream().forEach((path) -> {
            newString.append(path);
        });
        return newString.toString();
    }

    default String stringEnsamble(String... stringPaths) {
        StringBuffer newString = new StringBuffer();
        String[] var3 = stringPaths;
        int var4 = stringPaths.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String path = var3[var5];
            newString.append(path);
        }
        return newString.toString();
    }

}
