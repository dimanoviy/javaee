package edu.javaee.cleanup;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CleanUpObjectTest {

    @Test
    public void cleanup() {
        class ClassTest {
            int uid = 7;
            String name = "Default name";
            String value = "Value of item";
            public ClassTest(){

            }
            public ClassTest(int uid, String name, String value) {
                this.uid = uid;
                this.name = name;
                this.value = value;
            }
        }

//        public static void printFieldsViaReflection(Object object) throws IllegalAccessException {
//            for (Field field : inquiryFields(object)) {
//                System.out.println("Name of field: " + field.getName());
//                field.setAccessible(true);
//                System.out.println(field.get(object));
//            }
//        }

        HashMap map = new HashMap<String, String>();
        map.put("key1", "value1");
//        printClass(map);
//        CleanUpObject.ClassTest instanceClassTest = new CleanUpObject.ClassTest();
//        ClassTest instanceClassTest = new ClassTest(23, "name23", "value23");

        Set<String> fieldsToCleanup = new HashSet<>();
        Set<String> fieldsToOutput = new HashSet<>();
        fieldsToOutput.add("uid");
        fieldsToOutput.add("index");
        fieldsToOutput.add("name");
        fieldsToOutput.add("serialVersionUID");
        fieldsToCleanup.add("value");
        fieldsToCleanup.add("uid");
        fieldsToCleanup.add("name");

//        cleanup(map, fieldsToCleanup, fieldsToOutput);
//        printFieldsViaReflection(instanceClassTest);
//        CleanUpObject.cleanUpFields(instanceClassTest, fieldsToCleanup);
//        printFieldsViaReflection(instanceClassTest);
//        printInstanceOfClass(instanceClassTest);
//        printClass(ClassTest.class);
    }
}