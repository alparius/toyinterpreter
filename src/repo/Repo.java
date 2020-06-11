package repo;

import exceptions.RepoException;
import model.ProgramState;
import model.statement.CompoundStmt;
import model.statement.IStatement;
import utils.Pair;

import java.io.*;
import java.util.*;

public class Repo implements IRepo {

    private List<ProgramState> states;
    private String logFilePath;

    public Repo(ProgramState state, String logFilePath) {
        this.states = new ArrayList<>();
        this.states.add(state);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.states;
    }

    @Override
    public void setProgramList(List<ProgramState> states) {
        this.states = states;
    }


    @Override
    public void logProgramStateExec(ProgramState state) {
        // is it ok to get collection clone?
        // TODO ?do this with iterators?

        PrintWriter logFile = null;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));

            logFile.println("\n\n\n----- thread: " + state.getId());

            logFile.println("\nExeStack:");
            Stack<IStatement> exestack = state.getExeStack().getclone();
            Collections.reverse(exestack);
            for (IStatement elem : exestack) {
                if (elem instanceof CompoundStmt)
                    printCompoundStmt((CompoundStmt)elem, logFile);
                else
                    logFile.println("\t" + elem.toString());
            }

            logFile.println("\nSymTable:");
            Map<String,Integer> symtable = state.getSymTable().getclone();
            for (String elem : symtable.keySet()) {
                logFile.println("\t" + elem + " --> " + symtable.get(elem).toString());
            }

            logFile.println("\nHeap:");
            Map<Integer,Integer> heap = state.getHeap().getclone();
            for (int elem : heap.keySet()) {
                logFile.println("\t" + elem + " --> " + heap.get(elem).toString());
            }

            logFile.println("\nOut:");
            ArrayList<Integer> output = state.getOutput().getclone();
            for (Integer elem : output) {
                logFile.println("\t" + elem.toString());
            }

            logFile.println("\nFileTable:");
            Map<Integer,Pair<String,BufferedReader>> filetable = state.getFileTable().getclone();
            for (Integer elem : filetable.keySet()) {
                logFile.println("\t" + elem.toString() + " --> " + filetable.get(elem).getFirst());
            }

            logFile.println("\nProcTable:");
            Map<String,Pair<ArrayList<String>,IStatement>> proctable = state.getProcTable().getclone();
            for (String elem : proctable.keySet()) {
                logFile.println("\t" + elem + "(" + proctable.get(elem).getFirst().toString() + ") {" + proctable.get(elem).getSecond().toString() + "}");
            }

        }
        catch (IOException e) {
            throw new RepoException("file IO error");
        }
        finally {
            if (logFile != null)
                logFile.close();
        }
    }

    // aux function for printing the degenerate binary tree-like exestack
    private static void printCompoundStmt(CompoundStmt i, PrintWriter pw) {
        pw.println("\t" + i.getFirst());
        if (i.getSecond() instanceof CompoundStmt)
            printCompoundStmt((CompoundStmt)i.getSecond(), pw);
        else
            pw.println("\t" + i.getSecond());

    }
}