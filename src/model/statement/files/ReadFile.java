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

public class ReadFile implements IStatement
{
    private Expression exp_file_id;
    private String var_name;

    public ReadFile(Expression exp_file_id, String var_name) {
        this.exp_file_id = exp_file_id;
        this.var_name = var_name;
    }

    @Override
    public String toString() {
        return "readFile(" + this.exp_file_id.toString() + "," + this.var_name + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String,Integer> symtable = state.getSymTable();
        IHeap<Integer> heap = state.getHeap();

        int result = this.exp_file_id.evaluate(symtable,heap);

        IFileTable<String,BufferedReader> filetable = state.getFileTable();
        BufferedReader br = filetable.getFd(result);

        try {
            String line;
            int fromfile;
            line = br.readLine();
            if (line == null)
                fromfile = 0;
            else
                fromfile = Integer.parseInt(line);

            if (symtable.containsKey(this.var_name))
                symtable.update(this.var_name, fromfile);
            else
                symtable.add(this.var_name, fromfile);
        }
        catch (IOException io) {
            throw new FileException("file read error");
        }
        finally {
            return null;
        }
    }
}
