package model.expression;

import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class ConstantExp extends Expression
{
    private int value;

    public ConstantExp(int value) {
        this.value = value;
    }


    @Override
    public int evaluate(ISymTable<String,Integer> symtable, IHeap<Integer> heap) {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
