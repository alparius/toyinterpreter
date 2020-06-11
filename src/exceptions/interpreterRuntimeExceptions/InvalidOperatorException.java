package exceptions.interpreterRuntimeExceptions;

public class InvalidOperatorException extends RuntimeException
{
    public InvalidOperatorException() {}

    @Override
    public String getMessage() {
        return "invalid operator";
    }
}