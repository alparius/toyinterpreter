package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IExeStack;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

public class returnStmt implements IStatement
{
    public returnStmt() { }

    @Override
    public String toString() {
        return  "return";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        state.popSymTable();
        return null;
    }
}