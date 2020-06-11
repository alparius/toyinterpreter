package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class AssignmentStmt implements IStatement
{
    private String variable;
    private Expression expression;

    public AssignmentStmt(String var, Expression exp) {
        this.variable = var;
        this.expression = exp;
    }

    @Override
    public String toString() {
        return this.variable + "=" + this.expression.toString();
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String,Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int result = this.expression.evaluate(symtable,heap);
        if (symtable.containsKey(this.variable))
            symtable.update(this.variable, result);
        else
            symtable.add(this.variable, result);

        return null;
    }
}
