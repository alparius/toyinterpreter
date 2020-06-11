package exceptions.adtRuntimeExceptions;

public class FileTableException extends RuntimeException
{
    public FileTableException() {}

    @Override
    public String getMessage() {
        return "file doesn't exist";
    }
}
