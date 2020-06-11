package utils;


import exceptions.adtRuntimeExceptions.FileTableException;
import utils.interfaces.IFileTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTable<U,V> implements IFileTable<U,V>
{
    private Map<Integer,Pair<U,V>> dict;
    private int fdcount;

    public FileTable() {
        this.dict = new HashMap<>();
        this.fdcount = 1;
    }


    @Override
    public int add(U path, V javafd) {
        this.dict.put(fdcount, new Pair<>(path, javafd));
        this.fdcount++;
        return this.fdcount - 1;
    }

    @Override
    public boolean containsFd(int fd) {
        return this.dict.containsKey(fd);
    }

    @Override
    public boolean containsFile(U filename) {
        for (Integer fd : this.dict.keySet()) {
            if (this.dict.get(fd).getFirst().equals(filename))
                return true;
        }
        return false;
    }

    @Override
    public U getPath(int fd) {
        if (this.dict.containsKey(fd))
            return this.dict.get(fd).getFirst();
        else
            throw new FileTableException();
    }

    @Override
    public V getFd(int fd) {
        if (this.dict.containsKey(fd))
            return this.dict.get(fd).getSecond();
        else
            throw new FileTableException();
    }

    @Override
    public void update(int fd, U path, V javafd) {
        this.dict.replace(fd, new Pair<>(path, javafd));
    }

    @Override
    public void remove(int fd) {
        this.dict.remove(fd);
    }

    @Override
    public Set<Integer> fdSet() {
        return this.dict.keySet();
    }

    @Override
    public ArrayList<V> fileList() {
        ArrayList<V> files = new ArrayList<>();
        this.dict.values().forEach(e->files.add(e.getSecond()));
        return files;
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

        for ( int fd : this.dict.keySet()) {
            result.append(fd).append("->").append(this.dict.get(fd).getFirst().toString()).append(" | ");
        }
        if (result.length() > 4)
            result = new StringBuilder(result.substring(0, result.length() - 3));
        result.append(" }");
        return result.toString();

//        String result = "{ ";
//
//        for ( int fd : this.dict.keySet()) {
//            result += fd + "->" + this.dict.get(fd).getFirst().toString() + " | ";
//        }
//        if (result.length() > 4)
//            result = result.substring(0, result.length() - 3);
//        result += " }";
//        return result;
    }


    @Override
    public Map<Integer, Pair<U, V>> getclone() {
        return new HashMap<>(this.dict);
    }

    @Override
    public ArrayList<Pair<Integer,U>> getPairs() {
        ArrayList<Pair<Integer,U>> data = new ArrayList<>();
        for ( int key : this.dict.keySet()) {
            data.add(new Pair<>(key,this.dict.get(key).getFirst()));
        }
        return data;
    }

    @Override
    public Map<Integer, Pair<U, V>> getContent() {
        return dict;
    }

    @Override
    public void setContent(Map<Integer, Pair<U, V>> f) {
        this.dict = f;
    }
}
