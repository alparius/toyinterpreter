package exceptions;

public class ExecutionStackEmptyException extends RuntimeException
{
    public ExecutionStackEmptyException() {}

    @Override
    public String getMessage() {
        return "no more steps";
    }
}