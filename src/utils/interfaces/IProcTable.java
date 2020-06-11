package utils.interfaces;

import model.statement.IStatement;
import utils.Pair;
import utils.Triple;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface IProcTable {
    void add(String name, ArrayList<String> params, IStatement body);
    boolean containsKey(String key);
    Pair<ArrayList<String>,IStatement> getValue(String key);
    //void update(String key, ArrayList<String>> value);
    //void remove(int key);
    Set<String> keySet();
    boolean isEmpty();
    int size();

    //IProcTable deepcopy();
    Map<String, Pair<ArrayList<String>,IStatement>> getclone();
    ArrayList<Pair<String,String>> getPairs();
    Map<String, Pair<ArrayList<String>,IStatement>> getContent();
    //void setContent (Map<Integer, Pair<Integer,ArrayList<Integer>>> dict);
}