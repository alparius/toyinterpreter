package utils;

import utils.interfaces.IOutput;

import java.util.ArrayList;

public class Output<T> implements IOutput<T>
{
    private ArrayList<T> list;

    public Output() {
        this.list = new ArrayList<>();
    }


    @Override
    public void add(T elem) {
        this.list.add(elem);
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public T get(int index) {
        return this.list.get(index);
    }

    @Override
    public void set(int index, T elem) {
        this.list.set(index, elem);
    }

    @Override
    public void remove(int index) {
        this.list.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{ ");
        for (T elem : this.list) {
            result.append(elem.toString()).append(" | ");
        }
        if (result.length() > 4)
            result = new StringBuilder(result.substring(0, result.length() - 3));
        result.append(" }");
        return result.toString();

//        String result = "{ ";
//        for (T elem : this.list) {
//            result += elem.toString() + " | ";
//        }
//        if (result.length() > 4)
//            result = result.substring(0, result.length() - 3);
//        result += " }";
//        return result;
    }


    @Override
    public ArrayList<T> getclone() {
        return (ArrayList<T>) this.list.clone();
    }


    @Override
    public ArrayList<T> getContent() {
        return this.list;
    }

    @Override
    public void setContent(ArrayList<T> list) {
        this.list = list;
    }
}
