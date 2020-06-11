package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class HeapAllocStmt implements IStatement
{
    private String var_name;
    private Expression expression;

    public HeapAllocStmt(String var_name, Expression expression) {
        this.var_name = var_name;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "new(" + this.var_name + "," + this.expression.toString() + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String,Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int result = this.expression.evaluate(symtable,heap);

        int location = heap.allocate(result);

        if (symtable.containsKey(this.var_name))
            symtable.update(this.var_name, location);
        else
            symtable.add(this.var_name, location);

        return null;
    }
}
