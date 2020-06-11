package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IExeStack;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class WhileStmt implements IStatement
{
    private Expression expression;
    private IStatement doThis;

    public WhileStmt(Expression expression, IStatement statement) {
        this.expression = expression;
        this.doThis = statement;
    }

    @Override
    public String toString() {
        return "while(" + this.expression.toString() + ") {" + doThis.toString() + "}";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String,Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int result = expression.evaluate(symtable,heap);

        if (result != 0) {
            IExeStack<IStatement> exestack = state.getExeStack();
            exestack.push(this);
            exestack.push(this.doThis);
        }
        return null;
    }
}
