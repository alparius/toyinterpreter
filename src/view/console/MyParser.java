package view.console;

import controller.Controller;
import exceptions.RepoException;
import exceptions.StopExecution;
import model.ProgramState;
import model.expression.*;
import model.statement.*;
import model.statement.files.CloseRFile;
import model.statement.files.OpenRFile;
import model.statement.files.ReadFile;
import repo.IRepo;
import repo.Repo;
import utils.*;
import utils.interfaces.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MyParser
{
    public MyParser() { }

    public void start()
    {
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("INPUT PROGRAM FILE: ");
            String input = scanner.nextLine();
            System.out.println();
            if (input.equals("0")) {
                System.out.println("BYE..");
                break;
            }
            else{
                runprogram(input);
            }
            System.out.println("\n");
        }
    }

    private void runprogram( String filename)
    {
        // open file if it exists
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(filename));
        }
        catch (IOException e) {
            System.out.println("file not found");
            return;
        }

        // delete previous logfile
        try {
            Files.deleteIfExists(Paths.get("log_" + filename));
        } catch (IOException io) { }


        // read program into an execution queue represented as a list
        ArrayList<IStatement> exec_queue = parse(file);

        // transform it into a big and degenerate binary tree of a compoundstatement
        IStatement originalprogram = createCompoundStmt(exec_queue);


        // create stuff needed for the program
        IExeStack<IStatement> exestack = new ExeStack<>();
        ISymTable<String,Integer> symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> symtablestack = new Stack<>();
        symtablestack.push(symtable);
        IHeap<Integer> heap = new Heap<>();
        IOutput<Integer> output = new Output<>();
        IFileTable<String,BufferedReader> filetable = new FileTable<>();
        IProcTable proctable = new ProcTable();

        ProgramState state = new ProgramState(exestack, symtablestack, heap, output, filetable, proctable,originalprogram);
        IRepo repo = new Repo(state, "log_" + filename);
        Controller ctrl = new Controller(repo);


        // run program
        try{
            //ctrl.allSteps();

        } catch(StopExecution | RepoException e) {
            System.out.println("execution stopped because: " + e.getMessage());
        }
    }


    private ArrayList<IStatement> parse(BufferedReader file)
    {
        ArrayList<IStatement> exec = new ArrayList<>();
        try {
            String line;
            while ((line = file.readLine()) != null)
            {
                if (line.length()<3 || line.startsWith("//")) continue;
                if (line.contains("//")) line = line.split("\\/\\/")[0];
                line = line.trim();

                IStatement stmt = createSimpleStmt(line);
                exec.add(stmt);
            }
            return exec;
        }
        catch (IOException e) {
            System.out.println("read error: " + e);
        }
        catch (StopExecution se) {
            System.out.println(se.getMessage());
        }
        finally {
            try { file.close(); }
            catch (IOException e) { }
            return exec;
        }
    }


    private IStatement createSimpleStmt(String line)
    {
        IStatement stmt;
        //TODO substring(mystr.indexOf("{"),mystr.lastIndexOf("}"))

        if (line.startsWith("openRFile(")) {
            line = line.substring(10,line.length()-1);
            String[] elems = line.split(",");

            String var_file_id = elems[0];
            String filename = elems[1];

            stmt = new OpenRFile(var_file_id, filename);
        }
        else if (line.startsWith("readFile(")) {
            line = line.substring(9,line.length()-1);
            String[] elems = line.split(",");

            Expression exp_file_id = createExp(elems[0]);
            String var_name = elems[1];

            stmt = new ReadFile(exp_file_id, var_name);
        }
        else if (line.startsWith("closeRFile(")) {
            line = line.substring(11,line.length()-1);

            Expression exp_file_id = createExp(line);

            stmt = new CloseRFile(exp_file_id);
        }
        else if (line.startsWith("new(")) {
            line = line.substring(4,line.length()-1);
            String[] elems = line.split(",");

            String var_name = elems[0];
            Expression exp = createExp(elems[1]);

            stmt = new HeapAllocStmt(var_name, exp);
        }
        else if (line.startsWith("wH(")) {
            line = line.substring(3,line.length()-1);
            String[] elems = line.split(",");

            String var_name = elems[0];
            Expression exp = createExp(elems[1]);

            stmt = new HeapWriteStmt(var_name, exp);
        }
        else if (line.startsWith("print(")) {
            line = line.substring(6,line.length()-1);

            Expression exp = createExp(line);

            stmt = new PrintStmt(exp);
        }
        else if (line.startsWith("while(")) {
            line = line.substring(6,line.length()-1);
            String[] elems = line.split("\\{");

            String str_while = elems[0].substring(0, elems[0].length()-2);
            Expression _while = createExp(str_while);

            ArrayList<IStatement> stmtList = getStatementList(elems[1]);
            IStatement dothis = createCompoundStmt(stmtList);

            stmt = new WhileStmt(_while, dothis);
        }
        else if (line.startsWith("if(")) {
            line = line.substring(3,line.length()-1);
            String[] elems = line.split("\\{");

            String str_if = elems[0].substring(0,elems[0].length()-6);
            Expression _if = createExp(str_if);

            String str_then = elems[1].substring(0,elems[1].length()-6);
            ArrayList<IStatement> then_stmtList = getStatementList(str_then);
            IStatement _then = createCompoundStmt(then_stmtList);

            ArrayList<IStatement> else_stmtList = getStatementList(elems[2]);
            IStatement _else = createCompoundStmt(else_stmtList);

            stmt = new ConditionalStmt(_if, _then, _else);
        }
        else if (line.startsWith("printmsg(")) {
            line = line.substring(9,line.length()-1);

            stmt = new PrintMsgStmt(line);
        }
        else if (line.startsWith("fork(")) {
            line = line.substring(5,line.length()-1);

            ArrayList<IStatement> subprogram_stmtList = getStatementList(line);
            IStatement subprogram = createCompoundStmt(subprogram_stmtList);

            stmt = new ForkStmt(subprogram);
        }
        else if (line.contains("=")) {
            String[] elems = line.split("=",2);

            String var = elems[0];
            Expression exp = createExp(elems[1]);

            stmt = new AssignmentStmt(var,exp);
        }
        else {
            throw new StopExecution("invalid statement");
        }

        return stmt;
    }

    private ArrayList<IStatement> getStatementList(String line)
    {
        ArrayList<IStatement> stmts = new ArrayList<>();

        // split line for statements
        String[] elems = line.split(";");

        // create statements one by one
        for (String elem : elems)
            stmts.add(createSimpleStmt(elem));

        return stmts;
    }


    private IStatement createCompoundStmt(ArrayList<IStatement> stmts)
    {
        // if only one statement, return that
        if (stmts.size() == 1)
            return stmts.get(0);

        // else we create the degenerate binary tree
        else {
            CompoundStmt stmt = new CompoundStmt(); // the big compoundstatement
            CompoundStmt stmtc = stmt; // the current sublevel of the big compoundstatement

            while (stmts.size()>2) {
                stmtc.setFirst(stmts.get(0));
                stmts.remove(0);

                // progressing down in the degenerate binary tree
                CompoundStmt stmtc2 = new CompoundStmt();
                stmtc.setSecond(stmtc2);
                stmtc = stmtc2;
            }

            stmtc.setFirst(stmts.get(0));
            stmtc.setSecond(stmts.get(1));

            return stmt;
        }
    }


    private Expression createExp(String line)
    {
        Expression exp;

        String[] elems = line.split(" ");
        List<String> opers = Arrays.asList("+","-","*","/");

        if (elems.length == 1) {
            exp = createSimpleExp(elems[0]);
        }
        else if (opers.contains(elems[1])) {
            exp =  new ArithmeticExp(elems[1].toCharArray()[0], createSimpleExp(elems[0]), createSimpleExp(elems[2]));
        }
        else {
            exp = new BooleanExp(elems[1], createSimpleExp(elems[0]), createSimpleExp(elems[2]));
        }

        return exp;
    }


    private Expression createSimpleExp(String line)
    {
        Expression exp;

        if (line.startsWith("rH(")) {
            line = line.substring(3,line.length()-1);
            exp = new HeapReadExp(line);
        }
        else if (line.matches("-?(0|[1-9]\\d*)")) {
            exp = new ConstantExp(Integer.parseInt(line));
        }
        else {
            exp = new VariableExp(line);
        }

        return exp;
    }
}
