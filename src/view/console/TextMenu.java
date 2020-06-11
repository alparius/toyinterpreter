package view.console;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu
{
    private Map<String,Command> commands;

    public TextMenu(){
        this.commands = new HashMap<>();
    }


    public void addCommand(Command c){
        this.commands.put(c.getKey(),c);
    }

    private void printMenu(){
        for(Command com : this.commands.values()){
            String line = String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("choose: ");
            String key = scanner.nextLine();
            Command com = this.commands.get(key);
            if (com == null){
                System.out.println("invalid command");
                continue; }
            com.execute();
        }
    }
}