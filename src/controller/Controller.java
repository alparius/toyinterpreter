package controller;

import exceptions.StopExecution;
import model.ProgramState;
import model.statement.CompoundStmt;
import model.statement.IStatement;
import repo.IRepo;
import utils.Pair;
import utils.Triple;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller
{
    private IRepo repo;
    private ExecutorService executor;

    public Controller(IRepo repo) {
        this.repo = repo;
    }


    public ArrayList<Integer> getOutputList() {
        return repo.getProgramList().get(0).getOutput().getclone();
    }
    public ArrayList<Pair<Integer,Integer>> getHeapTable() { return repo.getProgramList().get(0).getHeap().getPairs(); }
    public ArrayList<Pair<Integer,String>> getFileTable() { return repo.getProgramList().get(0).getFileTable().getPairs(); }
    public ArrayList<Pair<String,String>> getProcTable() { return repo.getProgramList().get(0).getProcTable().getPairs(); }

    public Integer nrofthreads() { return repo.getProgramList().size(); }
    public ArrayList<Integer> getThreadList() {
        ArrayList<Integer> threaddata = new ArrayList<>();
        repo.getProgramList().forEach(s-> threaddata.add(s.getId()));
        return threaddata;
    }
    public ArrayList<Pair<String,Integer>> getSymTable(Integer threadid) {
        for (ProgramState ps : repo.getProgramList()) {
            if (ps.getId() == threadid) {
                return ps.getSymTable().getPairs();
            }
        }
        throw new StopExecution("invalid thread selection");
    }
    public ArrayList<String> getExestackList(Integer threadid) {
        for (ProgramState ps : repo.getProgramList()) {
            if (ps.getId() == threadid) {
                return ps.getExestackString();
            }
        }
        throw new StopExecution("invalid thread selection");
    }
    public boolean activeButton() {
        return ((this.repo.getProgramList().get(0).getExeStack().size() > 0) || (this.nrofthreads() > 1));
    }



    private List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }


    private void oneStepForAllPrograms(List<ProgramState> prgList) {
        //prepare the list of callables
        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .collect(Collectors.toList());

        try {
            //start the execution of the callables
            //it returns the list of new created PrgStates (namely threads)
            List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try { return future.get(); }
                        catch (ExecutionException | InterruptedException e) {
                            throw new StopExecution(e.getMessage()); }
                    })
                    .filter(Objects::nonNull)
                    //.filter(p -> p != null)
                    .collect(Collectors.toList());

            //add the new created threads to the list of existing threads
            prgList.addAll(newPrgList);
        }
        catch (InterruptedException e) {
            //closing all open files
            fileCloser(repo.getProgramList().get(0).getFileTable().fileList());

            throw new StopExecution(e.getMessage());
        }


        //save the current programs in the repository
        repo.setProgramList(prgList);
    }



    public void oneStep() {
        this.executor = Executors.newFixedThreadPool(8); // OR 2?

        //remove the completed programs
        List<ProgramState> prgList = removeCompletedPrg(repo.getProgramList());

        if (prgList.size() > 0) {

            oneStepForAllPrograms(prgList);

            //collecting all the symboltable values and calling the garbage collector
            ArrayList<Integer> symbolTableValues = new ArrayList<>();
            prgList.forEach(s -> symbolTableValues.addAll(s.getSymTable().getContent().values()));
            prgList.get(0).getHeap().setContent(conservativeGarbageCollector(symbolTableValues, prgList.get(0).getHeap().getContent()));

            //after a step execution, print the PrgState List into the log file
            prgList.forEach(prg -> repo.logProgramStateExec(prg));

            removeCompletedPrg(repo.getProgramList());
            executor.shutdownNow();

        }
        else {
            throw new StopExecution("no more steps");
        }
    }


    private Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer,Integer> heap) {
        return heap.entrySet().stream()
                .filter(e->symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void fileCloser(Collection<BufferedReader> fileTableValues) {
        fileTableValues.forEach(e -> {
                    try { e.close(); }
                    catch (IOException io) { }
                });
    }

    @Override
    public String toString() {
        return  this.repo.getProgramList().get(0).getExeStack().getContent().get(0).toString();
    }
}

/*


    public void allSteps() {
        this.executor = Executors.newFixedThreadPool(2);

        //remove the completed programs
        List<ProgramState> prgList = removeCompletedPrg(repo.getProgramList());

        while(prgList.size() > 0) {
            //collecting all the symboltable values and calling the garbage collector
            ArrayList<Integer> symbolTableValues = new ArrayList<>();
            prgList.forEach(s-> symbolTableValues.addAll(s.getSymTable().getContent().values()));
            //prgList.forEach(s->s.getSymTable().getContent().values().forEach(symbolTableValues::add));
            //prgList.forEach(s->s.getSymTable().getContent().values().forEach(p->symbolTableValues.add(p)));

            prgList.get(0).getHeap().setContent(conservativeGarbageCollector(symbolTableValues,  prgList.get(0).getHeap().getContent()));

            oneStepForAllPrograms(prgList);

            //remove the completed programs
            prgList = removeCompletedPrg(repo.getProgramList());
        }
        executor.shutdownNow();
        //here the repository still contains at least one completed Prg and its List<PrgState> is not empty.
        //note that oneStepForAllPrg calls the method setPrgList of repository in order to change the repository;

        //closing all open files
        fileCloser(repo.getProgramList().get(0).getFileTable().fileList());

        //update the repository state
        repo.setProgramList(prgList);
    }
 */
