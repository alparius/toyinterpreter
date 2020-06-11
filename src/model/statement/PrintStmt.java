package model.statement;

import model.ProgramState;
import model.expression.Expression;
import utils.interfaces.IHeap;
import utils.interfaces.IOutput;
import utils.interfaces.ISymTable;

public class PrintStmt implements IStatement
{
    private Expression expression;

    public PrintStmt(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() +")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        IOutput<Integer> output = state.getOutput();
        ISymTable<String,Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int result = this.expression.evaluate(symtable,heap);
        output.add(result);
        //System.out.println(result); //PARSER

        return null;
    }
}
