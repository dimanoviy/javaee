package edu.javaee.cleanup;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class CleanUpObject {
    public static void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws NoSuchFieldException, IllegalAccessException {
        if (isMapImplemented(object)) {
            outputMapFields((Map) object, fieldsToOutput);
            cleanupMapFields((Map) object, fieldsToCleanup);
        } else {
            outputFields(object, fieldsToOutput);
            cleanUpFields(object, fieldsToCleanup);
        }
    }

    public static boolean isMapImplemented (Object object) {
        for (Class aclass : object.getClass().getInterfaces()) {
            if (aclass.getName() == "java.util.Map") {
                return true;
            }
        }
        return false;
    }

    private static void outputMapFields(Map object, Set<String> fieldsToOutput) {
        for (String fieldToOutput : fieldsToOutput) {
            if (object.containsKey(fieldToOutput)) {
                System.out.println("Map value " + fieldToOutput + ": " + object.get(fieldToOutput));
            }
        }
    }

    private static void cleanupMapFields(Map object, Set<String> fieldsToCleanup) {
        for (String fieldToCleanup : fieldsToCleanup) {
            if (object.containsKey(fieldToCleanup)) {
                object.remove(fieldToCleanup);
            } else throw new IllegalArgumentException("No such key in the map: " + fieldToCleanup);
        }
    }

    private static void cleanUpFields(Object object, Set<String> fieldsToCleanup) throws IllegalAccessException {
        for (String fieldToCleanup : fieldsToCleanup) {
            for (Field field : inquiryFields(object)) {
                if (field.getName().equals(fieldToCleanup)) {
                    purgeField(object, field);
                }
            }
        }
    }

    private static void outputFields(Object object, Set<String> fieldsToOutput) throws IllegalAccessException {
        for (String fieldToCleanup : fieldsToOutput) {
            for (Field field : inquiryFields(object)) {
                if (field.getName().equals(fieldToCleanup)) {
                    field.setAccessible(true);
                    System.out.println("Field " + field.getName() + ": " + field.get(object));
                }
            }
        }
    }

    private static Field[] inquiryFields(Object object) {
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        return fields;
    }

    private static void purgeField(Object object, Field fieldToCleanup) throws IllegalAccessException {
        fieldToCleanup.setAccessible(true);
        if (fieldToCleanup.getType().isPrimitive()) {
            purgePrimitiveField(fieldToCleanup, object);
        } else {
            fieldToCleanup.set(object, null);
        }
    }

    private static void purgePrimitiveField(Field field, Object object) throws IllegalAccessException {
        switch (field.getType().getName()) {
            case "byte" : field.set(object, 0); break;
            case "short" : field.set(object, 0); break;
            case "int" : field.set(object, 0); break;
            case "long" : field.set(object, 0L); break;
            case "float" : field.set(object, 0f); break;
            case "double" : field.set(object, 0d); break;
            case "boolean" : field.set(object, false); break;
            case "char" : field.set(object, '\u0000'); break;
        }
    }

    public static void printFieldsViaReflection(Object object) throws IllegalAccessException {
        for (Field field : inquiryFields(object)) {
            System.out.println("Name of field: " + field.getName());
            field.setAccessible(true);
            System.out.println(field.get(object));
        }
    }
}
