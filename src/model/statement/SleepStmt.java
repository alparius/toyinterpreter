package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IExeStack;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class SleepStmt implements IStatement
{
    private int time;

    public SleepStmt(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return  "sleep(" + this.time + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {

        if (time > 0) {
            IExeStack<IStatement> exestack = state.getExeStack();
            exestack.push(new SleepStmt(time-1));
        }
        return null;
    }
}