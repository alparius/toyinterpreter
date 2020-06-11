package model.statement.files;

import exceptions.interpreterRuntimeExceptions.FileException;
import model.ProgramState;
import model.statement.IStatement;
import utils.interfaces.IFileTable;
import utils.interfaces.ISymTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStatement
{
    private String var_file_id;
    private String filename;

    public OpenRFile(String r_file_id, String filepath) {
        this.var_file_id = r_file_id;
        this.filename = filepath;
    }

    @Override
    public String toString() {
        return "openRFile(" + this.var_file_id + "," + this.filename + ")";
    }


    @Override
    public ProgramState execute(ProgramState state) {
        ISymTable<String,Integer> symtable = state.getSymTable();
        IFileTable<String,BufferedReader> filetable = state.getFileTable();

        if (filetable.containsFile(this.filename))
            throw new FileException("file already open");

        try {
            BufferedReader file = new BufferedReader(new FileReader(this.filename));
            int fd = filetable.add(this.filename, file);
            symtable.add(this.var_file_id, fd);
        }
        catch (IOException e) {
            throw new FileException("file open error");
        }
        finally {
            return null;
        }
    }
}

