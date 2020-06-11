package utils.interfaces;

import utils.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface IFileTable<U,V>
{
    int add(U path, V javafd);
    boolean containsFd(int fd);
    boolean containsFile(U filename);
    U getPath(int fd);
    V getFd(int fd);
    void update(int fd, U path, V javafd);
    void remove(int fd);
    Set<Integer> fdSet();
    ArrayList<V> fileList();
    boolean isEmpty();
    int size();

    Map<Integer, Pair<U,V>> getclone();
    ArrayList<Pair<Integer,U>> getPairs();
    Map<Integer,Pair<U,V>> getContent();
    void setContent(Map<Integer,Pair<U,V>> f);
}
