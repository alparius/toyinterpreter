package exceptions;

public class RepoException extends RuntimeException
{
    private String message;

    public RepoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}