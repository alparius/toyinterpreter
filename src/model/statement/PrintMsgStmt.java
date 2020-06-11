package model.statement;

import model.ProgramState;

public class PrintMsgStmt implements IStatement
{
    //PARSER

    private String message;

    public PrintMsgStmt(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "printmsg(" + this.message + ")";
    }


    @Override
    public ProgramState execute(ProgramState state)
    {
        if (this.message.equals("\\n")) {
            System.out.println();
        }
        else if (this.message.startsWith("\\n")) {
            System.out.println();
            System.out.println(message.substring(2));
        }
        else {
            System.out.println(this.message);
        }
        return null;
    }
}
