package model.statement.files;

import exceptions.interpreterRuntimeExceptions.FileException;
import model.ProgramState;
import model.expression.Expression;
import model.statement.IStatement;
import utils.interfaces.IFileTable;
import utils.interfaces.IHeap;
import utils.interfaces.ISymTable;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement
{
    private Expression exp_file_id;

    public CloseRFile(Expression exp_file_id) {
        this.exp_file_id = exp_file_id;
    }

    @Override
    public String toString() {
        return "closeRFile(" + this.exp_file_id.toString() + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String,Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int result = this.exp_file_id.evaluate(symtable,heap);

        IFileTable<String,BufferedReader> filetable = state.getFileTable();
        BufferedReader br = filetable.getFd(result);

        try {
            br.close();
            filetable.remove(result);
        }
        catch (IOException e) {
            throw new FileException("file close error");
        }
        finally {
            return null;
        }
    }
}
