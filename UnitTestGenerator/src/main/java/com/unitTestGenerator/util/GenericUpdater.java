package com.unitTestGenerator.util;

import java.lang.reflect.Field;
import java.util.Objects;

public class GenericUpdater {
    /*
     * @param target is old value
     * @param source is a new value
     */
    public static <T> void update(T target, T source) {
        if (source != null && target != null) {
            Class<?> clazz = source.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object sourceValue = field.get(source);
                    Object targetValue = field.get(target);
                    if (sourceValue != null && !Objects.equals(sourceValue, targetValue)) {
                        field.set(target, sourceValue);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error updating field: " + field.getName(), e);
                }
            }
        }
    }

    public static <T> T updateObject(T target, T source) {
        if (source != null && target != null) {
            update(target, source);
        }
        return target;
    }

}





