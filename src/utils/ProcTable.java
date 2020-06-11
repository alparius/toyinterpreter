package utils;

import exceptions.adtRuntimeExceptions.ProcTableException;
import model.statement.IStatement;
import utils.interfaces.IProcTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProcTable implements IProcTable {
    private Map<String,Pair<ArrayList<String>,IStatement>> dict;

    public ProcTable() {
        this.dict = new HashMap<>();
    }

    @Override
    public void add(String name, ArrayList<String> params, IStatement body) {
        dict.put(name, new Pair<>(params,body));
    }

    @Override
    public boolean containsKey(String key) {
        return dict.containsKey(key);
    }

    @Override
    public Pair<ArrayList<String>, IStatement> getValue(String key) {
        if (this.dict.containsKey(key))
            return this.dict.get(key);
        else
            throw new ProcTableException();
    }

    @Override
    public Set<String> keySet() {
        return dict.keySet();
    }

    @Override
    public boolean isEmpty() {
        return dict.isEmpty();
    }

    @Override
    public int size() {
        return dict.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("{ ");

        for ( String name : this.dict.keySet()) {
            result.append(name).append("(").append(this.dict.get(name).getFirst().toString()).append(") -> ").append(dict.get(name).getSecond().toString());
        }
        if (result.length() > 4)
            result = new StringBuilder(result.substring(0, result.length() - 3));
        result.append(" }");
        return result.toString();
    }

    /*@Override
    public IProcTable deepcopy() {
        return null;
    }*/

    @Override
    public Map<String, Pair<ArrayList<String>, IStatement>> getclone() {
        return new HashMap<>(dict);
    }

    @Override
    public ArrayList<Pair<String, String>> getPairs() {
        ArrayList<Pair<String,String>> data = new ArrayList<>();
        for ( String key : dict.keySet()) {
            data.add(new Pair<String,String>(new String(key + "(" + this.dict.get(key).getFirst().toString() + ")"),dict.get(key).getSecond().toString()));
        }
        return data;
    }

    @Override
    public Map<String, Pair<ArrayList<String>, IStatement>> getContent() {
        return dict;
    }
}
