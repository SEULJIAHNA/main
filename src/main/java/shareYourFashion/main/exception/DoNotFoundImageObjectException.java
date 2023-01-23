package shareYourFashion.main.exception;

public class DoNotFoundImageObjectException extends Exception{
    public DoNotFoundImageObjectException() {
        super();
    }

    public DoNotFoundImageObjectException(String message) {
        super(message);
    }

    public DoNotFoundImageObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoNotFoundImageObjectException(Throwable cause) {
        super(cause);
    }
}
