package exceptions.adtRuntimeExceptions;

public class ProcTableException extends RuntimeException {
    public ProcTableException() { }

    @Override
    public String getMessage() {
        return "function doesn't exist";
    }
}