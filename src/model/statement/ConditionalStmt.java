package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IExeStack;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class ConditionalStmt implements IStatement
{
    private Expression expression;
    private IStatement then;
    private IStatement els;

    public ConditionalStmt(Expression expression, IStatement then, IStatement els) {
        this.expression = expression;
        this.then = then;
        this.els = els;
    }

    @Override
    public String toString() {
        return "IF(" + this.expression.toString() + ") THEN(" + this.then.toString() + ") ELSE(" + this.els.toString() + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String, Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();
        IExeStack<IStatement> exestack = state.getExeStack();

        if (this.expression.evaluate(symtable,heap) != 0)
            exestack.push(this.then);
        else
            exestack.push(this.els);

        return null;
    }
}
