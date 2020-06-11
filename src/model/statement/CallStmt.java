package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.Pair;
import utils.SymTable;
import utils.interfaces.*;

import java.util.ArrayList;

public class CallStmt implements IStatement
{
    private String fname;
    ArrayList<Expression> explist;

    public CallStmt(String fname, ArrayList<Expression> explist) {
        this.fname = fname;
        this.explist = explist;
    }

    @Override
    public String toString() {
        return "call " + fname + "(" + this.explist.toString() +")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String,Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();
        IProcTable proctable = state.getProcTable();

        Pair<ArrayList<String>,IStatement> func = proctable.getValue(fname);

        ArrayList<Integer> res = new ArrayList<>();
        for (Expression arg : explist) {
            res.add(arg.evaluate(symtable,heap));
        }

        ISymTable<String,Integer> funcsymtable = new SymTable<>();
        for (int i=0; i<explist.size(); i++) {
            funcsymtable.add(func.getFirst().get(i),res.get(i));
        }
        state.addToSymTableStack(funcsymtable);

        IExeStack<IStatement> exestack = state.getExeStack();
        exestack.push(new returnStmt());
        exestack.push(func.getSecond());

        return null;
    }
}
