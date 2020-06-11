package model.expression;

import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public abstract class Expression
{
    public abstract int evaluate(ISymTable<String,Integer> symtable, IHeap<Integer> heap);
}
