package utils;


import exceptions.adtRuntimeExceptions.DictionaryException;
import utils.interfaces.ISymTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SymTable<K,V> implements ISymTable<K,V>
{
    private Map<K,V> dict;

    public SymTable() {
        this.dict = new HashMap<>();
    }

    private SymTable(Map<K, V> dict) {
        this.dict = dict;
    }


    @Override
    public void add(K key, V value) {
        this.dict.put(key, value);
    }

    @Override
    public boolean containsKey(K key) {
        return this.dict.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        if (this.dict.containsKey(key))
            return this.dict.get(key);
        else
            throw new DictionaryException();
    }

    @Override
    public void update(K key, V value) {
        this.dict.replace(key, value);
    }

    @Override
    public void remove(K key) {
        this.dict.remove(key);
    }

    @Override
    public Set<K> keySet() {
        return this.dict.keySet();
    }

    @Override
    public boolean isEmpty() {
        return this.dict.isEmpty();
    }

    @Override
    public int size() {
        return this.dict.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{ ");

        for ( K key : this.dict.keySet()) {
            result.append(key.toString()).append("->").append(this.dict.get(key).toString()).append(" | ");
        }
        if (result.length() > 4)
            result = new StringBuilder(result.substring(0, result.length() - 3));
        result.append(" }");
        return result.toString();

//        String result = "{ ";
//
//        for ( K key : this.dict.keySet()) {
//            result += key.toString() + "->" + this.dict.get(key).toString() + " | ";
//        }
//        if (result.length() > 4)
//            result = result.substring(0, result.length() - 3);
//        result += " }";
//        return result;
    }


    @Override
    public ISymTable<K, V> deepcopy() {
        return new SymTable<>(new HashMap<>(this.dict));
    }

    @Override
    public Map<K, V> getclone() {
        return new HashMap<>(this.dict);
    }

    @Override
    public ArrayList<Pair<K, V>> getPairs() {
        ArrayList<Pair<K,V>> data = new ArrayList<>();
        for ( K key : this.dict.keySet()) {
            data.add(new Pair<>(key,this.dict.get(key)));
        }
        return data;
    }

    @Override
    public Map<K,V> getContent() {
        return this.dict;
    }

    @Override
    public void setContent(Map<K, V> dict) {
        this.dict = dict;
    }
}
