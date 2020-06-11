package model.expression;

import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class VariableExp extends Expression
{
    private String name;

    public VariableExp(String name) {
        this.name = name;
    }


    @Override
    public int evaluate(ISymTable<String,Integer> symtable, IHeap<Integer> heap) {
        return symtable.getValue(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
