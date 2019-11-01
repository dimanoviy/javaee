package edu.javaee;

import edu.javaee.myhashmap.MyHashMap;

public class TestClass {
    public static void main(String[] args) {

        MyHashMap<String, String> map = new MyHashMap();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value4");
        map.put("key6", "value4");
        map.put("key7", "value4");
        map.put("key8", "value4");

        System.out.println(map.size());

//        map.resize(30);
        map.print();
        System.out.println(map.size());
        System.out.println();

        printArray(map.getAllEntries());
        map.print();
//        HashMap hm = new HashMap();
    }

    static <T> void printArray(T[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }


}
