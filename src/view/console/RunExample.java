package view.console;

import controller.Controller;
import exceptions.RepoException;
import exceptions.StopExecution;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class RunExample extends Command
{
    private Controller ctrl;

    public RunExample(String key, String desc,Controller ctrl) {
        super(key, desc);
        this.ctrl = ctrl;
    }

    @Override
    public void execute() {
        try{
            //this.ctrl.allSteps();

        } catch(StopExecution | RepoException e) {
            System.out.println("execution stopped because: " + e.getMessage());
        }
    }
}