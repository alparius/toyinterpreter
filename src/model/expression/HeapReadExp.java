package model.expression;

import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class HeapReadExp extends Expression
{
    private String var_name;

    public HeapReadExp(String var_name) {
        this.var_name = var_name;
    }


    @Override
    public int evaluate(ISymTable<String, Integer> symtable, IHeap<Integer> heap) {
        int address = symtable.getValue(this.var_name);
        return heap.getValue(address);
    }

    @Override
    public String toString() {
        return "rH(" + this.var_name + ")";
    }
}
