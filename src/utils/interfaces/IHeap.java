package utils.interfaces;

import utils.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface IHeap<V>
{
    int allocate(V value);
    boolean containsKey(int key);
    V getValue(int key);
    void update(int key, V value);
    void remove(int key);
    Set<Integer> keySet();
    boolean isEmpty();
    int size();

    Map<Integer,V> getclone();
    ArrayList<Pair<Integer,V>> getPairs();
    Map<Integer,V> getContent();
    void setContent(Map<Integer,V> h);
}
