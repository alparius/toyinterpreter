package model;

import exceptions.ExecutionStackEmptyException;
import model.statement.CompoundStmt;
import model.statement.IStatement;
import utils.interfaces.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class ProgramState
{
    private static int count = 1;

    private int id;
    private IExeStack<IStatement> exeStack;
    private Stack<ISymTable<String,Integer>> symTableStack;
    private IHeap<Integer> heap;
    private IOutput<Integer> output;
    private IFileTable<String, BufferedReader> fileTable;
    private IProcTable procTable;

    public ProgramState(IExeStack<IStatement> myexestack, Stack<ISymTable<String,Integer>> mysymtablestack, IHeap<Integer> myheap, IOutput<Integer> myoutput, IFileTable<String,BufferedReader> myfiletable, IProcTable proctable, IStatement myoriginalprogram) {
        this.id = count++;
        this.exeStack = myexestack;
        this.symTableStack = mysymtablestack;
        this.heap = myheap;
        this.output = myoutput;
        this.fileTable = myfiletable;
        this.procTable = proctable;

        this.exeStack.push(myoriginalprogram);
    }

    public Boolean isNotCompleted() {
        return !(this.exeStack.isEmpty());
    }
    public Boolean isCompleted() {
        return this.exeStack.isEmpty();
    }

    public ProgramState oneStep() {
        if(isCompleted())
            throw new ExecutionStackEmptyException();
        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString() {
        return  "thread with id: " + this.id +
                "\nexeStack: " + this.exeStack.toString() +
                "\nsymTable: " + this.getSymTable().toString() +
                "\nheap: " + this.heap.toString() +
                "\noutput:   " + this.output.toString() +
                "\nfileTable: " + this.fileTable.toString() +
                "\n";
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public IExeStack<IStatement> getExeStack() {
        return this.exeStack;
    }
    public void setExeStack(IExeStack<IStatement> exeStack) {
        this.exeStack = exeStack;
    }

    public IProcTable getProcTable() {
        return procTable;
    }
    public void setProcTable(IProcTable procTable) {
        this.procTable = procTable;
    }

    public ISymTable<String, Integer> getSymTable() {
        return this.symTableStack.peek();
    }
    public void addToSymTableStack( ISymTable<String,Integer> symtable) { this.symTableStack.push(symtable); }
    public ISymTable<String, Integer> popSymTable() {
        return this.symTableStack.pop();
    }
    public Stack<ISymTable<String,Integer>> getSymTableStack() { return this.symTableStack; }
    public synchronized Stack<ISymTable<String,Integer>> getSymTableStackClone() {
        Stack<ISymTable<String,Integer>> result = new Stack<>();
        Collections.reverse(symTableStack);
        for (ISymTable<String,Integer> sym : symTableStack)
            result.push(sym.deepcopy());
        Collections.reverse(symTableStack);
        return result;
    }
    /*public void setSymTable(ISymTable<String, Integer> symTable) {
        this.symTable = symTable;
    }*/

    public IHeap<Integer> getHeap() {
        return heap;
    }
    public void setHeap(IHeap<Integer> heap) {
        this.heap = heap;
    }

    public IOutput<Integer> getOutput() {
        return this.output;
    }
    public void setOutput(IOutput<Integer> output) {
        this.output = output;
    }

    public IFileTable<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }
    public void setFileTable(IFileTable<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public ArrayList<String> getExestackString() {
        ArrayList<String> result = new ArrayList<>();
        Stack<IStatement> exestack = this.exeStack.getclone();
        Collections.reverse(exestack);
        for (IStatement elem : exestack) {
            if (elem instanceof CompoundStmt)
                addCompoundStmt((CompoundStmt)elem, result);
            else
                result.add(elem.toString());
        }
        return result;
    }

    // aux function for printing the degenerate binary tree-like exestack
    private static void addCompoundStmt(CompoundStmt i, ArrayList<String> result) {
        result.add(i.getFirst().toString());
        if (i.getSecond() instanceof CompoundStmt)
            addCompoundStmt((CompoundStmt)i.getSecond(), result);
        else
            result.add(i.getSecond().toString());

    }
}