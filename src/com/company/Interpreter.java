package com.company;

import controller.Controller;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.WindowEvent;
import model.ProgramState;
import model.expression.*;
import model.statement.*;
import model.statement.files.OpenRFile;
import model.statement.files.ReadFile;
import repo.IRepo;
import repo.Repo;
import utils.*;
import utils.interfaces.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.gui.RunGUI;
import view.gui.SelectGUI;

// TODO TODO TODO
// https://stackoverflow.com/questions/52682195/how-to-get-javafx-and-java-11-working-in-intellij-idea
// https://stackoverflow.com/questions/52467561/intellij-cant-recognize-javafx-11-with-openjdk-11

public class Interpreter extends Application
{
    // TODO macros
    // TODO sdjfhkdshfsdkjf
    // CTRL + SHIFT + F search everywhere
    // CTRL + R replace
    // ALT + INSERT generate
    // SHIFT + F6 rename
    // CTRL + / comment/uncomment
    // CTRL + W selection wizard
    // CTRL + CLICK jump to class
    // CTRL + ALT + T surround with



    // log of what is different compared to original problem statements
        // CTRL + SHIFT + F : //PARSER
        // log before launch in allsteps and after every step in onestep, not twice every step

    // rediscover exception handling, try out error stuff


/*
    public static void main2(String[] args)
    {
        MyParser p = new MyParser();
        p.start();
    }
*/
    @Override
    public void start(Stage primaryStage) throws Exception {

        // default separate log files
        List<String> logfiles = Arrays.asList("log1.txt","log2.txt","log3.txt","log4.txt","log5.txt","log6.txt","log7.txt","log8.txt","log9.txt");

        // delete old default separate log files
        try {
            for (String logfile: logfiles)
                Files.deleteIfExists(Paths.get(logfile));
        } catch (IOException io) { }


        String testin = "test.in"; // real file:   (15,50)
        //String testin = "test1.in"; // empty file: ()
        //String testin = "test2.in"; // half file:   (15)


        /*
        a=2+3*5;
        b=a+1;
        Print(b)
         */
        IStatement ex1 = new CompoundStmt(
                new AssignmentStmt("a", new ArithmeticExp('+',new ConstantExp(2),
                        new ArithmeticExp('*',new ConstantExp(3), new ConstantExp(5)))),
                new CompoundStmt(
                        new AssignmentStmt("b",new ArithmeticExp('+',new VariableExp("a"), new ConstantExp(1))),
                        new PrintStmt(new VariableExp("b"))));

        IExeStack<IStatement> ex1_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex1_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex1_symtablestack = new Stack<>();
        ex1_symtablestack.push(ex1_symtable);
        IHeap<Integer> ex1_heap = new Heap<>();
        IOutput<Integer> ex1_output = new Output<>();
        IFileTable<String,BufferedReader> ex1_filetable = new FileTable<>();
        IProcTable ex1_proctable = new ProcTable();
        ProgramState ex1_state = new ProgramState(ex1_exestack, ex1_symtablestack, ex1_heap, ex1_output, ex1_filetable, ex1_proctable, ex1);
        IRepo repo1 = new Repo(ex1_state, logfiles.get(0));
        Controller ctrl1 = new Controller(repo1);


        /*
        a=2-2;
        If a Then v=2 Else v=3;
        Print(v)
         */
        IStatement ex2 = new CompoundStmt(
                new AssignmentStmt("a", new ArithmeticExp('-',new ConstantExp(2), new ConstantExp(2))),
                new CompoundStmt(
                        new ConditionalStmt(new VariableExp("a"),new AssignmentStmt("v",new ConstantExp(2)), new AssignmentStmt("v", new ConstantExp(3))),
                        new PrintStmt(new VariableExp("v"))));

        IExeStack<IStatement> ex2_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex2_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex2_symtablestack = new Stack<>();
        ex2_symtablestack.push(ex2_symtable);
        IHeap<Integer> ex2_heap = new Heap<>();
        IOutput<Integer> ex2_output = new Output<>();
        IFileTable<String,BufferedReader> ex2_filetable = new FileTable<>();
        IProcTable ex2_proctable = new ProcTable();
        ProgramState ex2_state = new ProgramState(ex2_exestack, ex2_symtablestack, ex2_heap, ex2_output, ex2_filetable, ex2_proctable, ex2);
        IRepo repo2 = new Repo(ex2_state, logfiles.get(1));
        Controller ctrl2 = new Controller(repo2);


        /*
        openRFile(var_f,"test.in");
        readFile(var_f,var_c);      // readFile(var_f+2,var_c);
        print(var_c);
        if (var_c) then
            readFile(var_f,var_c);
            print(var_c)
        else
            print(0);
        */
        // new ArithmeticExp('+', new VariableExp("file"), new ConstantExp(2)) INSTEAD OF new VariableExp("file")
        IStatement ex3 = new CompoundStmt(new OpenRFile("file",testin),
                new CompoundStmt(new ReadFile(new VariableExp("file"),"buffer"),
                        new CompoundStmt(new PrintStmt(new VariableExp("buffer")),
                                new ConditionalStmt(new VariableExp("buffer"),
                                        new CompoundStmt(new ReadFile(new VariableExp("file"), "buffer"), new PrintStmt(new VariableExp("buffer"))),
                                        new PrintStmt(new ConstantExp(0))))));

        IExeStack<IStatement> ex3_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex3_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex3_symtablestack = new Stack<>();
        ex3_symtablestack.push(ex3_symtable);
        IHeap<Integer> ex3_heap = new Heap<>();
        IOutput<Integer> ex3_output = new Output<>();
        IFileTable<String,BufferedReader> ex3_filetable = new FileTable<>();
        IProcTable ex3_proctable = new ProcTable();
        ProgramState ex3_state = new ProgramState(ex3_exestack, ex3_symtablestack, ex3_heap, ex3_output, ex3_filetable, ex3_proctable, ex3);
        IRepo repo3 = new Repo(ex3_state, logfiles.get(2));
        Controller ctrl3 = new Controller(repo3);


        /*
        v=10;
        new(v,20);
        new(a,22);
        wH(a,30);
        print(a);
        print(rH(a));
        a=0
        Before the end of the execution: Heap={1->20, 2->30}, SymTable={v->1, a->2} and Out={2,30}
        At the end of execution: Heap={1->20}, SymTable={v->1, a->0} and Out={2,30}
        */
        IStatement ex4 = new CompoundStmt(new AssignmentStmt("v",new ConstantExp(10)),
                new CompoundStmt(new HeapAllocStmt("v",new ConstantExp(20)),
                        new CompoundStmt(new HeapAllocStmt("a", new ConstantExp(22)),
                                new CompoundStmt(new HeapWriteStmt("a",new ConstantExp(40)),
                                        new CompoundStmt(new PrintStmt(new VariableExp("a")),
                                                new CompoundStmt(new PrintStmt(new HeapReadExp("a")),new AssignmentStmt("a", new ConstantExp(0))))))));

        IExeStack<IStatement> ex4_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex4_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex4_symtablestack = new Stack<>();
        ex4_symtablestack.push(ex4_symtable);
        IHeap<Integer> ex4_heap = new Heap<>();
        IOutput<Integer> ex4_output = new Output<>();
        IFileTable<String,BufferedReader> ex4_filetable = new FileTable<>();
        IProcTable ex4_proctable = new ProcTable();
        ProgramState ex4_state = new ProgramState(ex4_exestack, ex4_symtablestack, ex4_heap, ex4_output, ex4_filetable, ex4_proctable, ex4);
        IRepo repo4 = new Repo(ex4_state, logfiles.get(3));
        Controller ctrl4 = new Controller(repo4);


        /*
        v=6;
        while (v-4)
            print(v);
            v=v-1;
        print(v);
        print(10+(2<=6));
        print((10+2)<=6)
        */
        IStatement ex5 = new CompoundStmt( new AssignmentStmt("v",new ConstantExp(6)),
                new CompoundStmt(new WhileStmt(new ArithmeticExp('-', new VariableExp("v"), new ConstantExp(4)),
                        new CompoundStmt(new PrintStmt(new VariableExp("v")),new AssignmentStmt("v", new ArithmeticExp('-',new VariableExp("v"), new ConstantExp(1))))),
                        new CompoundStmt(new PrintStmt(new VariableExp("v")),
                                new CompoundStmt(new PrintStmt(new ArithmeticExp('+', new ConstantExp(10), new BooleanExp("<=",new ConstantExp(2), new ConstantExp(6)))),
                                        new PrintStmt(new BooleanExp("<=", new ArithmeticExp('+', new ConstantExp(10), new ConstantExp(2)),new ConstantExp(6)))))));

        IExeStack<IStatement> ex5_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex5_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex5_symtablestack = new Stack<>();
        ex5_symtablestack.push(ex5_symtable);
        IHeap<Integer> ex5_heap = new Heap<>();
        IOutput<Integer> ex5_output = new Output<>();
        IFileTable<String,BufferedReader> ex5_filetable = new FileTable<>();
        IProcTable ex5_proctable = new ProcTable();
        ProgramState ex5_state = new ProgramState(ex5_exestack, ex5_symtablestack, ex5_heap, ex5_output, ex5_filetable, ex5_proctable, ex5);
        IRepo repo5 = new Repo(ex5_state, logfiles.get(4));
        Controller ctrl5 = new Controller(repo5);

        /*
        v=10
        new(a,22)
        fork(
            wH(a,30)
            v=32
            print(v)
            print(rH(a))
        )
        print(v)
        print(rH(a))

        At the end:
        Id=1 SymTable_1={v->10,a->1}
        Id=10 SymTable_10={v->32,a->1}
        Heap={1->30}
        Out={10,30,32,30}
        */
        IStatement ex6 = new CompoundStmt(new AssignmentStmt("v",new ConstantExp(10)),
                new CompoundStmt(new HeapAllocStmt("a",new ConstantExp(22)),
                        new CompoundStmt(new ForkStmt(
                                new CompoundStmt(new HeapWriteStmt("a",new ConstantExp(30)),
                                        new CompoundStmt(new AssignmentStmt("v", new ConstantExp(32)),
                                                new CompoundStmt(new PrintStmt(new VariableExp("v")),new PrintStmt(new HeapReadExp("a")))))),
                                new CompoundStmt(new PrintStmt(new VariableExp("v")),new PrintStmt(new HeapReadExp("a"))))));

        IExeStack<IStatement> ex6_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex6_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex6_symtablestack = new Stack<>();
        ex6_symtablestack.push(ex6_symtable);
        IHeap<Integer> ex6_heap = new Heap<>();
        IOutput<Integer> ex6_output = new Output<>();
        IFileTable<String,BufferedReader> ex6_filetable = new FileTable<>();
        IProcTable ex6_proctable = new ProcTable();
        ProgramState ex6_state = new ProgramState(ex6_exestack, ex6_symtablestack, ex6_heap, ex6_output, ex6_filetable, ex6_proctable, ex6);
        IRepo repo6 = new Repo(ex6_state, logfiles.get(5));
        Controller ctrl6 = new Controller(repo6);


        IStatement ex7 = new CompoundStmt(new AssignmentStmt("v", new ConstantExp(3)),
                new CompoundStmt(new WhileStmt(new BooleanExp("==",new VariableExp("v"), new ConstantExp(3)), new CompoundStmt(new ForkStmt(new CompoundStmt(new PrintStmt(new VariableExp("v")), new AssignmentStmt("v", new ArithmeticExp('-',new VariableExp("v"), new ConstantExp(1))))), new AssignmentStmt("v", new ArithmeticExp('+',new VariableExp("v"), new ConstantExp(1))))),
                        new CompoundStmt(new AssignmentStmt("x", new ConstantExp(1)),
                                new CompoundStmt(new AssignmentStmt("y", new ConstantExp(2)),
                                        new CompoundStmt(new AssignmentStmt("z", new ConstantExp(3)),
                                                new CompoundStmt(new AssignmentStmt("w", new ConstantExp(4)),
                                                        new PrintStmt(new ArithmeticExp('*',new VariableExp("v"), new ConstantExp(10)))))))));
        IExeStack<IStatement> ex7_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex7_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex7_symtablestack = new Stack<>();
        ex7_symtablestack.push(ex7_symtable);
        IHeap<Integer> ex7_heap = new Heap<>();
        IOutput<Integer> ex7_output = new Output<>();
        IFileTable<String,BufferedReader> ex7_filetable = new FileTable<>();
        IProcTable ex7_proctable = new ProcTable();
        ProgramState ex7_state = new ProgramState(ex7_exestack, ex7_symtablestack, ex7_heap, ex7_output, ex7_filetable,ex7_proctable, ex7);
        IRepo repo7 = new Repo(ex7_state, logfiles.get(6));
        Controller ctrl7 = new Controller(repo7);
        
        // v=10;
        //(fork(v=v-1;v=v-1;print(v)); sleep(10);print(v*10)
        IStatement ex8 = new CompoundStmt(new AssignmentStmt("v",new ConstantExp(10)),
                new CompoundStmt(new ForkStmt(new CompoundStmt(new AssignmentStmt("v", new ArithmeticExp('-', new VariableExp("v"), new ConstantExp(1))),new CompoundStmt(new AssignmentStmt("v", new ArithmeticExp('-', new VariableExp("v"), new ConstantExp(1))),new PrintStmt(new VariableExp("v"))))),
                        new CompoundStmt(new SleepStmt(10),new PrintStmt(new ArithmeticExp('*', new VariableExp("v"), new ConstantExp(10))))));
        IExeStack<IStatement> ex8_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex8_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex8_symtablestack = new Stack<>();
        ex8_symtablestack.push(ex8_symtable);
        IHeap<Integer> ex8_heap = new Heap<>();
        IOutput<Integer> ex8_output = new Output<>();
        IFileTable<String,BufferedReader> ex8_filetable = new FileTable<>();
        IProcTable ex8_proctable = new ProcTable();
        ProgramState ex8_state = new ProgramState(ex8_exestack, ex8_symtablestack, ex8_heap, ex8_output, ex8_filetable, ex8_proctable, ex8);
        IRepo repo8 = new Repo(ex8_state, logfiles.get(7));
        Controller ctrl8 = new Controller(repo8);

        /*
        procedure sum(a,b) v=a+b;print(v)
        procedure product(a,b) v=a*b;print(v)

        v=2;
        w=5;
        call sum(v*10,w);
        print(v);
        fork(call product(v,w);
        fork(call sum(v,w)))

        The final Out should be {25,2,10,7}
        */

        IStatement ex9 = new CompoundStmt(new AssignmentStmt("v",new ConstantExp(2)),
                new CompoundStmt(new AssignmentStmt("w", new ConstantExp(5)),
                        new CompoundStmt(new CallStmt("sum",new ArrayList<Expression>(){{add(new ArithmeticExp('*', new VariableExp("v"), new ConstantExp(10))); add(new VariableExp("w"));}}),
                                new CompoundStmt(new PrintStmt(new VariableExp("v")),
                                        new CompoundStmt(new ForkStmt(new CallStmt("product",new ArrayList<Expression>(){{add(new VariableExp("v")); add(new VariableExp("w"));}})),
                                                new ForkStmt(new CallStmt("sum", new ArrayList<>(){{add(new VariableExp("v"));add(new VariableExp("w"));}})))))));
        IExeStack<IStatement> ex9_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex9_symtable = new SymTable<>();
        Stack<ISymTable<String,Integer>> ex9_symtablestack = new Stack<>();
        ex9_symtablestack.push(ex9_symtable);
        IHeap<Integer> ex9_heap = new Heap<>();
        IOutput<Integer> ex9_output = new Output<>();
        IFileTable<String,BufferedReader> ex9_filetable = new FileTable<>();
        IProcTable ex9_proctable = new ProcTable();
        ex9_proctable.add("sum",new ArrayList<String>() {{add("a");add("b");}},new CompoundStmt(new AssignmentStmt("v",new ArithmeticExp('+', new VariableExp("a"), new VariableExp("b"))),new PrintStmt(new VariableExp("v"))));
        ex9_proctable.add("product",new ArrayList<String>() {{add("a");add("b");}},new CompoundStmt(new AssignmentStmt("v",new ArithmeticExp('*', new VariableExp("a"), new VariableExp("b"))),new PrintStmt(new VariableExp("v"))));
        ProgramState ex9_state = new ProgramState(ex9_exestack, ex9_symtablestack, ex9_heap, ex9_output, ex9_filetable, ex9_proctable, ex9);
        IRepo repo9 = new Repo(ex9_state, logfiles.get(8));
        Controller ctrl9 = new Controller(repo9);



        ArrayList<Controller> ctrls = new ArrayList<>();
        ctrls.add(ctrl1); ctrls.add(ctrl2); ctrls.add(ctrl3); ctrls.add(ctrl4); ctrls.add(ctrl5); ctrls.add(ctrl6); ctrls.add(ctrl7);
        ctrls.add(ctrl8); ctrls.add(ctrl9);


        try {
            //load root layout from fxml file
            FXMLLoader runLoader = new FXMLLoader();
            runLoader.setLocation(getClass().getResource("/view/gui/runwindow.fxml")); //URL
            Parent runWindow = runLoader.load();

            // create n show the scene containing the root layout
            Scene runScene = new Scene(runWindow);

            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

            primaryStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 750);

            runScene.getStylesheets().add(getClass().getResource("/view/gui/style.css").toExternalForm());
            primaryStage.setTitle("Interpreter");
            primaryStage.setScene(runScene);
            primaryStage.show();

            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });


            FXMLLoader selectLoader = new FXMLLoader();
            selectLoader.setLocation(getClass().getResource("/view/gui/selectwindow.fxml"));
            Parent selectWindow = selectLoader.load();

            Scene selectScene = new Scene(selectWindow);
            Stage selectStage = new Stage();

            selectStage.setX(primaryStage.getX() + selectStage.getWidth());
            selectStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 600);

            selectScene.getStylesheets().add(getClass().getResource("/view/gui/style.css").toExternalForm());
            selectStage.setTitle("Select a program to run");
            selectStage.setScene(selectScene);
            selectStage.show();

            selectStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });


            // get gui controller object
            RunGUI runctrl = (RunGUI) runLoader.getController();
            SelectGUI selectctrl = (SelectGUI) selectLoader.getController();
            selectctrl.set_my_stuff(runctrl,ctrls);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*
        IStatement ex1 = new CompoundStatement(new AssignStatement("v", new ConstantExpression(2)),
                new PrintStatement(new VariableExpression("v")));

        IStatement ex2 =  new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ConstantExpression(2), new
                ArithmeticExpression(new ConstantExpression(3), new ConstantExpression(5), OperationEnum.MULTIPLY), OperationEnum.PLUS)),
                new CompoundStatement(new AssignStatement("b", new ArithmeticExpression(new VariableExpression("a"), new
                        ConstantExpression(1), OperationEnum.PLUS)), new PrintStatement(new VariableExpression("b"))));

        IStatement ex3 = new CompoundStatement(new AssignStatement("a", new ArithmeticExpression(new ConstantExpression(2), new ConstantExpression(2), OperationEnum.MINUS)),
                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ConstantExpression(2)),
                        new AssignStatement("v", new ConstantExpression(3))), new PrintStatement(new VariableExpression("v"))));

        IStatement ex4 = new CompoundStatement(new OpenStatement("var_f", "test.in"),
                new CompoundStatement(new ReadStatement(new VariableExpression("var_f"), "var_c"),
                        new CompoundStatement(new PrintStatement(new VariableExpression("var_c")),
                                new CompoundStatement(new IfStatement(new VariableExpression("var_c"),
                                        new CompoundStatement(new ReadStatement(new VariableExpression("var_f"), "var_c"),
                                                new PrintStatement(new VariableExpression("var_c"))), new PrintStatement(new ConstantExpression(0))),
                                        new CloseStatement(new VariableExpression("var_f"))))));


        IStatement ex5 = new CompoundStatement(new AssignStatement("v",
                new ConstantExpression(10)), new CompoundStatement(new NewStatement("v", new ConstantExpression(20)),
                new CompoundStatement(new NewStatement("a", new ConstantExpression(22)), new PrintStatement(new VariableExpression("v")))));


        IStatement ex6 = new CompoundStatement(new AssignStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstantExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstantExpression(22)),
                                new CompoundStatement(new PrintStatement(
                                        new ArithmeticExpression(new ConstantExpression(100), new HeapReadingExpression("v"), OperationEnum.PLUS)),
                                        new PrintStatement(new ArithmeticExpression(new ConstantExpression(100), new HeapReadingExpression("a"), OperationEnum.PLUS))))));

        IStatement ex7 = new CompoundStatement(new AssignStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstantExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstantExpression(20)),
                                new CompoundStatement(new WriteStatement("a", new ConstantExpression(30)),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                new PrintStatement(new HeapReadingExpression("a")))))));

        IStatement ex8 = new CompoundStatement(new AssignStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new NewStatement("v", new ConstantExpression(20)),
                        new CompoundStatement(new NewStatement("a", new ConstantExpression(20)),
                                new CompoundStatement(new WriteStatement("a", new ConstantExpression(30)),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                                                new CompoundStatement(new PrintStatement(new HeapReadingExpression("a")),
                                                        new AssignStatement("a", new ConstantExpression(0))))))));

        IStatement ex9 = new PrintStatement(new ArithmeticExpression(new ConstantExpression(10),
                new BooleanExpression(new ConstantExpression(2), new ConstantExpression(6), "<"), OperationEnum.PLUS));


        IStatement ex10 = new PrintStatement(new BooleanExpression(new ArithmeticExpression(new ConstantExpression(10), new ConstantExpression(2), OperationEnum.PLUS),
                new ConstantExpression(6), "<"));


        IStatement ex11 = new CompoundStatement(new AssignStatement("v", new ConstantExpression(6)),
                new CompoundStatement(new WhileStatement(new ArithmeticExpression(new VariableExpression("v"), new ConstantExpression(4), OperationEnum.MINUS),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v",
                                new ArithmeticExpression(new VariableExpression("v"), new ConstantExpression(1), OperationEnum.MINUS)))), new PrintStatement(new VariableExpression("v"))));

        IStatement ex12 = new CompoundStatement(new OpenStatement("var_f", "test2.in"),
                new CompoundStatement(new ReadStatement(new VariableExpression("var_f"), "var_c"), new PrintStatement(new VariableExpression("var_c"))));

        IStatement ex13 = new CompoundStatement(new AssignStatement("v", new ConstantExpression(10)),
                new CompoundStatement(new NewStatement("a", new ConstantExpression(22)),
                        new CompoundStatement(
                                new ForkStatement(new CompoundStatement(new CompoundStatement(new WriteStatement("a", new ConstantExpression(30)),
                                        new CompoundStatement(new AssignStatement("v", new ConstantExpression(32)),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadingExpression("a"))))), new PrintStatement(new ArithmeticExpression(new ConstantExpression(23), new VariableExpression("c"), OperationEnum.PLUS)))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new HeapReadingExpression("a"))))));

        IStatement ex14 = new CompoundStatement(new AssignStatement("v", new ConstantExpression(0)),
                new CompoundStatement(new RepeatStatement(new CompoundStatement(new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ConstantExpression(1), OperationEnum.MINUS)))), new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ConstantExpression(1), OperationEnum.PLUS))), new BooleanExpression(new VariableExpression("v"), new ConstantExpression(3), "==")),
                        new CompoundStatement(new AssignStatement("x", new ConstantExpression(1)),
                                new CompoundStatement(new AssignStatement("y", new ConstantExpression(2)),
                                        new CompoundStatement(new AssignStatement("z", new ConstantExpression(3)),
                                                new CompoundStatement(new AssignStatement("w", new ConstantExpression(4)),
                                                        new PrintStatement(new ArithmeticExpression(new VariableExpression("v"), new ConstantExpression(10), OperationEnum.MULTIPLY))))))));

 */

        /* BORING
        v=10;
        new(v,20);
        new(a,22);
        print(100+rH(v));
        print(100+rH(a))
        At the end of execution: Heap={1->20, 2->22}, SymTable={v->1, a->2} and Out={120,122}


        IStatement ex6 = new CompoundStmt(new AssignmentStmt("v",new ConstantExp(10)),
                new CompoundStmt(new HeapAllocStmt("v",new ConstantExp(20)),
                        new CompoundStmt(new HeapAllocStmt("a", new ConstantExp(22)),
                                new CompoundStmt(new PrintStmt(new ArithmeticExp('+', new ConstantExp(100),new HeapReadExp("v"))),
                                        new PrintStmt(new ArithmeticExp('+', new ConstantExp(100),new HeapReadExp("a")))))));

        IExeStack<IStatement> ex6_exestack = new ExeStack<>();
        ISymTable<String,Integer> ex6_symtable = new SymTable<>();
        IHeap<Integer> ex6_heap = new Heap<>();
        IOutput<Integer> ex6_output = new Output<>();
        IFileTable<String,BufferedReader> ex6_filetable = new FileTable<>();
        ProgramState ex6_state = new ProgramState(ex6_exestack, ex6_symtable, ex6_heap, ex6_output, ex6_filetable, ex6);
        IRepo repo6 = new Repo(ex6_state, logfiles.get(5));
        Controller ctrl6 = new Controller(repo6);
        */

//
//
//
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", ex1.toString(), ctrl1));
//        menu.addCommand(new RunExample("2", ex2.toString(), ctrl2));
//        menu.addCommand(new RunExample("3", ex3.toString(), ctrl3));
//        menu.addCommand(new RunExample("4", ex4.toString(), ctrl4));
//        menu.addCommand(new RunExample("5", ex5.toString(), ctrl5));
//        menu.addCommand(new RunExample("6", ex6.toString(), ctrl6));
//
//        menu.show();

