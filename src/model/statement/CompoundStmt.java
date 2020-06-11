package model.statement;

import model.ProgramState;
import utils.interfaces.IExeStack;

public class CompoundStmt implements IStatement
{
    private IStatement first;
    private IStatement second;

    public CompoundStmt() { } //PARSER
    public CompoundStmt(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public IStatement getFirst() {
        return this.first;
    }
    public IStatement getSecond() {
        return this.second;
    }

    public void setFirst(IStatement first) {
        this.first = first;
    } //PARSER
    public void setSecond(IStatement second) {
        this.second = second;
    } //PARSER

    @Override
    public String toString() {
        return this.first.toString() + "; " + this.second.toString();
    }


    @Override
    public ProgramState execute(ProgramState state) {
        IExeStack<IStatement> exestack = state.getExeStack();
        exestack.push(this.second);
        //exestack.push(this.first);
        //return null;
        ProgramState maybe = first.execute(state);
        return maybe;
    }
}
