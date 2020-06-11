package utils;

import exceptions.adtRuntimeExceptions.ReadAccessViolationException;
import utils.interfaces.IHeap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap<V> implements IHeap<V>
{
    private Map<Integer,V> dict;
    private int newfreelocation;

    public Heap() {
        this.dict = new HashMap<>();
        this.newfreelocation = 1;
    }


    @Override
    public int allocate(V value) {
        this.dict.put(this.newfreelocation, value);
        this.newfreelocation++;
        return this.newfreelocation-1;
    }

    @Override
    public boolean containsKey(int key) {
        return this.dict.containsKey(key);
    }

    @Override
    public V getValue(int key) {
        if (this.dict.containsKey(key))
            return this.dict.get(key);
        else
            throw new ReadAccessViolationException();
    }

    @Override
    public void update(int key, V value) {
        if (this.dict.containsKey(key))
            this.dict.replace(key, value);
        else
            throw new ReadAccessViolationException();
    }

    @Override
    public void remove(int key) {
        this.dict.remove(key);
    }

    @Override
    public Set<Integer> keySet() {
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

        StringBuilder resultBuilder = new StringBuilder("{ ");
        for ( int key : this.dict.keySet()) {
            resultBuilder.append(key).append("->").append(this.dict.get(key).toString()).append(" | ");
        }
        String result = resultBuilder.toString();
        if (result.length() > 4)
            result = result.substring(0, result.length() - 3);
        result += " }";
        return result;

//        String result = "{ ";
//
//        for ( int key : this.dict.keySet()) {
//            result += key + "->" + this.dict.get(key).toString() + " | ";
//        }
//        if (result.length() > 4)
//            result = result.substring(0, result.length() - 3);
//        result += " }";
//        return result;
    }


    @Override
    public Map<Integer,V> getclone() {
        return new HashMap<>(this.dict);
    }


    @Override
    public Map<Integer,V> getContent() {
        return this.dict;
    }

    @Override
    public ArrayList<Pair<Integer,V>> getPairs() {
        ArrayList<Pair<Integer,V>> data = new ArrayList<>();
        for ( int key : this.dict.keySet()) {
            data.add(new Pair<>(key,this.dict.get(key)));
        }
        return data;
    }

    @Override
    public void setContent(Map<Integer,V> h) {
        this.dict = h;
    }
}
