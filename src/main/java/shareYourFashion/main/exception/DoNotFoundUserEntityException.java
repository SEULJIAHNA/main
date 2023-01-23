package shareYourFashion.main.exception;

public class DoNotFoundUserEntityException extends Exception {
    public DoNotFoundUserEntityException() {
        super();
    }

    public DoNotFoundUserEntityException(String message) {
        super(message);
    }

    public DoNotFoundUserEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoNotFoundUserEntityException(Throwable cause) {
        super(cause);
    }

    protected DoNotFoundUserEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
