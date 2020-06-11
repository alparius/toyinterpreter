package exceptions.interpreterRuntimeExceptions;

public class FileException extends RuntimeException
{
    private String message;

    public FileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
