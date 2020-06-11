package exceptions.adtRuntimeExceptions;

public class ReadAccessViolationException extends RuntimeException
{
    public ReadAccessViolationException() {}

    @Override
    public String getMessage() {
        return "read access violation";
    }
}
