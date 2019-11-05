package edu.javaee.myhashmap;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * собственная реализация HashMap на основе хэштаблиц
 *
 * @param <K> Тип ключа
 * @param <V> Тип значения
 */
public class MyHashMap<K, V> implements Map<K, V> {

    private static int DEFAULT_CAPACITY = 3;
    private static double DEFAULT_LOAD_FACTOR = 0.75;

    private MyEntry<K, V>[] entries;
    private int size = 0;
    private int capacity;
    private double loadFactor;
    private int threshold;

    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * @param capacity   исходный размер внутреннего массива
     * @param loadFactor число от 0 до 1 определяющий при каком проценте заполненности внутреннего массива произойдет его расширение
     */
    public MyHashMap(int capacity, double loadFactor) {
        if (checkLoadCapacity(capacity, loadFactor)) {
            throw new InvalidParameterException("Invalid load-capacity parameter");
        }
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        entries = new MyEntry[capacity];
        calculateThreshold();
    }

    private void calculateThreshold() {
        this.threshold = (int) (this.loadFactor * this.capacity);
    }

    private boolean checkLoadCapacity(int capacity, double loadFactor) {
        return loadFactor < 0 || loadFactor > 1 || capacity < 0;
    }

    private int generateID(K key) {
        validKey(key);
        return key.hashCode() % capacity;
    }

    private boolean isEmptyID(int id) {
        return entries[id] == null;
    }

    private boolean isEmptyEntry(MyEntry entry) {
        return entry == null;
    }

    private void validKey(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    private void checkCapacity() {
        if (size >= threshold) {
            resize();
        }
    }

    private void resize() {
        resize(capacity * 2);
    }

    /**
     * Изменить размер массива на @newCapacity
     * @param newCapacity
     */
    public void resize(int newCapacity) {
        MyEntry<K, V>[] oldEntries = this.getAllEntries();
        this.capacity = newCapacity;
        this.entries = new MyEntry[newCapacity];
        this.size = 0;
        for (int i = 0; i < oldEntries.length; i++) {
            oldEntries[i].setNextEntry(null);
            put(oldEntries[i]);
        }
    }

    /**
     * Получить элементы в массиве элементов MyEntry
     * @return
     */
    public MyEntry<K, V>[] getAllEntries() {
        MyEntry<K, V>[] allEntries = new MyEntry[size];
        int id = 0;
        for (MyEntry entry : entries) {
            if (isEmptyEntry(entry)) {
                continue;
            }
            allEntries[id] = entry;
            id++;
            while ((allEntries[id - 1].hasNext())) {
                allEntries[id] = allEntries[id - 1].getNextEntry();
                id++;
            }
        }
        return allEntries;
    }

    /**
     * Получить элементы в массиве ArrayList
     * @return
     */
    public ArrayList<MyEntry> getAllEntriesArrayList() {
        ArrayList<MyEntry> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, getAllEntries());
        return arrayList;
    }

    /**
     * Получить количество элементов
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Проверить пустой ли массив
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Содержится ли объект с таким ключом key в массиве
     * @param key Ключ
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        return searchKey((K) key) != null;
    }

    /** Содержится ли объект с таким значением value в массиве
     *
     * @param value
     * @return
     */
    @Override
    public boolean containsValue(Object value) {
        return !(searchValue((V) value) == null);
    }

    private MyEntry searchKey(K key) {
        for (MyEntry entry : this.entries) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
            while (entry.hasNext()) {
                entry = entry.getNextEntry();
                if (entry.getKey().equals(key)) {
                    return entry;
                }
            }
        }
        return null;
    }

    private MyEntry searchValue(V value) {
        for (MyEntry entry : this.entries) {
            if (entry.getValue().equals(value)) {
                return entry;
            }
            while (entry.hasNext()) {
                entry = entry.getNextEntry();
                if (entry.getKey().equals(value)) {
                    return entry;
                }
            }
        }
        return null;
    }

    /**
     * Получить значение по ключу
     * @param key
     * @return V
     */
    @Override
    public V get(Object key) {
        return ((V) searchKey((K) key) == null) ? null : (V) searchKey((K) key).getValue();
    }

    /**
     * Добавить (положить) элемент (@key, @value) в массив
     * @param key Ключ
     * @param value Значение
     * @return
     */
    @Override
    public V put(K key, V value) {
        MyEntry entry = new MyEntry(key, value);
        put(entry);
        return value;
    }

    /**
     * Добавить (положить) новый елемент @newEntry в массив
     *
     * @param newEntry
     */
    public void put(MyEntry<K, V> newEntry) {
        int id = generateID(newEntry.getKey());
        if (isEmptyID(id)) {
            putEntryByID(newEntry, id);
            this.size++;
            return;
        }
        MyEntry existEntry = this.entries[id];
        if (existEntry.getKey().equals(newEntry.getKey())) {
            updateEntryWithValue(existEntry, newEntry);
            return;
        } else {
            while (existEntry.hasNext()) {
                existEntry = existEntry.getNextEntry();
                if (existEntry.getKey().equals(newEntry.getKey())) {
                    updateEntryWithValue(existEntry, newEntry);
                    return;
                }
            }
        }
        existEntry.setNextEntry(newEntry);
        size++;
    }

    private V updateValue(MyEntry<K, V> entry, V value) {
        V oldValue = (V) entry.getValue();
        entry.setValue(value);
        return oldValue;
    }

    private MyEntry<K, V> updateEntryWithValue(MyEntry<K, V> existEntry, MyEntry<K, V> newEntry) {
        existEntry.setValue(newEntry.getValue());
        return existEntry;
    }

    /**
     * Метод удаления элемента по ключу @key
     *
     * @param key
     * @return Значение value удаленного элемента
     */
    @Override
    public V remove(Object key) {
        int id = generateID((K) key);
        if (isEmptyID(id)) {
            return null;
        }
        MyEntry prevEntry;
        MyEntry existEntry = this.entries[id];
        if (existEntry.getKey().equals(key)) {
            if (existEntry.hasNext()) {
                putEntryByID(existEntry.getNextEntry(), id);
            } else {
                putEntryByID(null, id);
            }
            this.size--;
            return (V) existEntry.getValue();
        } else {
            while (existEntry.hasNext()) {
                prevEntry = existEntry;
                existEntry = existEntry.getNextEntry();
                if (existEntry.getKey().equals(key)) {
                    if (!existEntry.hasNext()) {
                        prevEntry.setNextEntry(null);
                    } else {
                        prevEntry.setNextEntry(existEntry.getNextEntry());
                    }
                    this.size--;
                    return (V) existEntry.getValue();
                }
            }
        }
        return null;
    }

    private void putEntryByID(MyEntry<K, V> entry, int ID) {
        this.entries[ID] = entry;
    }

    private void putEntryByKeyValue(MyEntry<K, V> entry, K key, V value) {
        int id = generateID(key);
        putEntryByID(entry, id);
    }

    /** Добавляет (укладывает) элемент класса map целиком
     *
     * @param map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        if (map.size() > this.capacity) {
            resize((int) ((map.size() + this.size) / this.loadFactor));
        }
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * присваивает всем ячейкам массива null
     */
    @Override
    public void clear() {
        Arrays.fill(entries, null);
    }

    /**
     * Получить ключи в виде множества (Set)
     * @return Set множество
     */
    @Override
    public Set<K> keySet() {
        Set<K> hashSet = new HashSet<>();
        for (MyEntry e : getAllEntriesArrayList()) {
            hashSet.add((K) e.getKey());
        }
        return hashSet;
    }

    /**
     * Получить в виде коллекции (Collection)
     * @return
     */
    @Override
    public Collection<V> values() {
        Collection collection = new ArrayList();
        for (MyEntry e : getAllEntriesArrayList()) {
            collection.add(e.getValue());
        }
        return collection;
    }

    /**
     * Получить весь HashMap в виде множества (Set)
     * @return
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> hashSet = new HashSet<Entry<K, V>>();
        for (MyEntry e : getAllEntriesArrayList()) hashSet.add(e);
        return hashSet;
    }

    public void print() {
        for (MyEntry entry : this.entries) {
            if (isEmptyEntry(entry)) {
                continue;
            }
            System.out.println(entry);
            while (entry.hasNext()) {
                entry = entry.getNextEntry();
                System.out.println("--" + entry);
            }
        }
    }
}
