package exceptions.adtRuntimeExceptions;

public class DictionaryException extends RuntimeException
{
    public DictionaryException() {}

    @Override
    public String getMessage() {
        return "invalid variable name";
    }
}