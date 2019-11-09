package edu.javaee.cleanup;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CleanUpObject {
    public static void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws NoSuchFieldException, IllegalAccessException {
        cleanUpFields(object, fieldsToCleanup);
        outputFields(object, fieldsToOutput);
    }

    private static void cleanUpFields(Object object, Set<String> fieldsToCleanup) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = object.getClass();
        for (String fieldNameToCleanup : fieldsToCleanup) {
            Field fieldToCleanup = clazz.getDeclaredField(fieldNameToCleanup);
            purgeField(object, fieldToCleanup);
        }
    }

    private static void purgeField(Object object, Field fieldToCleanup) throws IllegalAccessException {
        fieldToCleanup.setAccessible(true);

        if (object.getClass().isPrimitive()) {
            System.out.println("Need to set to default value!!!");
        } else {
            fieldToCleanup.set(object, null);
        }
    }

    private static void outputFields(Object object, Set<String> fieldsToOutput) {

    }

//    private static void purgeField(Field field) {
//        field = null;
//    }


    public static <T> void printInstanceOfClass(T object) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = object.getClass();
        printClass(clazz);
    }

    public static <T> void printClass(Class clazz) throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        for (Field fieldIterator : fields) {
            System.out.println(fieldIterator.getName());
            Field field = clazz.getDeclaredField(fieldIterator.getName());
            field.setAccessible(true);
            System.out.println(field.get(clazz));
        }
    }

    public static class ClassTest {
        int index = 7;
        String value = "Value of item";
        public ClassTest(int index, String value) {
            this.index = index;
            this.value = value;
        }
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
//        HashMap map = new HashMap<String, String>();
//        map.put("key1", "value1");
//        printClass(map);
        ClassTest instanceClassTest = new ClassTest(23, "value23");
        Set<String> fieldsToCleanup = new HashSet<>();
//        fieldsToCleanup.add("index");
        fieldsToCleanup.add("value");

        printInstanceOfClass(instanceClassTest);
        CleanUpObject.cleanUpFields(instanceClassTest, fieldsToCleanup);
        System.out.println("===Cleaned class===");
        printInstanceOfClass(instanceClassTest);
        System.out.println("===Class - ClassTest===");
        printClass(ClassTest.class);
    }
}
