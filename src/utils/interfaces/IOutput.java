package utils.interfaces;

import java.util.ArrayList;

public interface IOutput<T>
{
    void add(T elem);
    boolean isEmpty();
    int size();
    T get(int index);
    void set(int index, T elem);
    void remove(int index);

    ArrayList<T> getclone();

    ArrayList<T> getContent();
    void setContent(ArrayList<T> list);
}
