package edu.javaee.myhashmap;

import edu.javaee.myhashmap.MyHashMap;

public class TestClass {
    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap(3);
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");
        map.put("key6", "value6");
        map.put("key7", "value7");
        map.put("key8", "value8");
        MyHashMap<String, String> map2 = new MyHashMap<>();
//        map.remove("key1");
//        map.put("key7", "value77");
//        System.out.println(map.getAllEntries());
//        System.out.println(map.entrySet());
//        System.out.println(map.searchKey("key8"));
//        map.resize(30);
        map.print();
//        printArray(map.getAllEntries());
//        map.print();
//        HashMap hm = new HashMap();
    }
//
//    private static <T> void printArray(T[] array) {
//        for (int i = 0; i < array.length; i++) {
//            System.out.println(array[i]);
//        }
//    }
}
