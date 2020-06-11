package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class HeapWriteStmt implements IStatement
{
    private String var_name;
    private Expression expression;

    public HeapWriteStmt(String var_name, Expression expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "wH(" + this.var_name + "," + this.expression.toString() + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String, Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int address = symtable.getValue(this.var_name);
        int result = this.expression.evaluate(symtable,heap);

        heap.update(address, result);

        return null;
    }
}
