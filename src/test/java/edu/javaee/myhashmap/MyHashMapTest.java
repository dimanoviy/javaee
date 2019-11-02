package edu.javaee.myhashmap;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class MyHashMapTest {
    MyHashMap<String, String> map = new MyHashMap<>(3);
    {
        map.put("key1","value1");
        map.put("key2","value2");
        map.put("key3","value3");
        map.put("key4","value4");
        map.put("key5","value5");

    }
    HashMap<String, String> mapOrig = new HashMap<>(3);
    {
        mapOrig.put("key1","value1");
        mapOrig.put("key2","value2");
        mapOrig.put("key3","value3");
        mapOrig.put("key4","value4");
        mapOrig.put("key5","value5");

    }
    @Test
    public void size() {
        assertEquals(map.size(), mapOrig.size());
    }

    @Test
    public void isEmpty() {
        assertEquals(map.isEmpty(), mapOrig.isEmpty());
    }

    @Test
    public void containsKey() {
        assertEquals(map.containsKey("key1"), mapOrig.containsKey("key1"));
    }

    @Test
    public void containsValue() {
        assertEquals(map.containsValue("value1"), mapOrig.containsValue("value1"));
        assertEquals(map.containsValue("value11"), mapOrig.containsValue("value11"));
    }

    @Test
    public void get() {
    }

    @Test
    public void put() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void putAll() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void keySet() {
    }

    @Test
    public void values() {
    }

    @Test
    public void entrySet() {
    }
}