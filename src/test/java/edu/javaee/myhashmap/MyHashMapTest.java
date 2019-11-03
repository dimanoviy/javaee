package edu.javaee.myhashmap;

import org.junit.Test;

import java.util.EmptyStackException;
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
    public void values() {
        assertEquals(map.values(), mapOrig.values());
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
        assertEquals(map.containsKey("key11"), mapOrig.containsKey("key11"));
    }

    @Test
    public void containsValue() {
        assertEquals(map.containsValue("value1"), mapOrig.containsValue("value1"));
        assertEquals(map.containsValue("value11"), mapOrig.containsValue("value11"));
    }

    @Test
    public void get() {
        assertEquals(map.get("key1"), mapOrig.get("key1"));
        assertEquals(map.get("key11"), mapOrig.get("key11"));
    }

    @Test
    public void put() {
        assertEquals(map.put("key1", "value1"), mapOrig.put("key1", "value1"));
        assertEquals(map.put("key2", "value2"), mapOrig.put("key2", "value2"));
        assertEquals(map.put("key3", "value3"), mapOrig.put("key3", "value3"));
        assertEquals(map.put("key4", "value4"), mapOrig.put("key4", "value4"));
    }

    @Test
    public void remove() {
        assertEquals(map.remove("key", "value"), mapOrig.remove("key", "value"));
    }

    @Test
    public void putAll() {
//        assertEquals(assertTrue(map.putAll(mapOrig)), assertTrue(mapOrig.putAll(map)));
    }

    @Test
    public void clear() {
        map.clear();
        for (MyEntry e :
                map.getAllEntries()) {
            if (e != null) throw new RuntimeException();//WHAT EXCEPTION SHOULD I USE? OR HOW TO TEST?
        }
    }

    @Test
    public void keySet() {
        assertEquals(map.keySet(), mapOrig.keySet());
    }



    @Test
    public void entrySet() {
//        assertEquals(map.entrySet(), mapOrig.entrySet());
    }
}