package exceptions.interpreterRuntimeExceptions;

public class ZeroDivisionException extends RuntimeException
{
    public ZeroDivisionException() { }

    @Override
    public String getMessage() {
        return "division by zero";
    }
}