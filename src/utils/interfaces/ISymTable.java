package utils.interfaces;

import utils.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface ISymTable<K,V>
{
    void add(K key, V value);
    boolean containsKey(K key);
    V getValue(K key);
    void update(K key, V value);
    void remove(K key);
    Set<K> keySet();
    boolean isEmpty();
    int size();

    ISymTable<K, V> deepcopy();
    Map<K, V> getclone();
    ArrayList<Pair<K,V>> getPairs();
    Map<K,V> getContent();
    void setContent (Map<K,V> dict);
}
