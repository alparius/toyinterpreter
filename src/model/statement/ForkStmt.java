package model.statement;

import model.ProgramState;
import utils.ExeStack;
import utils.interfaces.IExeStack;
import utils.interfaces.ISymTable;

import java.util.Stack;

public class ForkStmt implements IStatement
{
    private IStatement statement;

    public ForkStmt(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "Fork(" + this.statement.toString() + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        IExeStack<IStatement> newstack = new ExeStack<>();
        Stack<ISymTable<String,Integer>> newsymstack = state.getSymTableStackClone();

        return new ProgramState(newstack,newsymstack,state.getHeap(),state.getOutput(),state.getFileTable(), state.getProcTable(), statement);
    }
}
