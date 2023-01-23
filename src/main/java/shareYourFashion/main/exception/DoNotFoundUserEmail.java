package shareYourFashion.main.exception;

public class DoNotFoundUserEmail extends Exception{
    public DoNotFoundUserEmail() {
        super();
    }

    public DoNotFoundUserEmail(String message) {
        super(message);
    }

    public DoNotFoundUserEmail(String message, Throwable cause) {
        super(message, cause);
    }

    public DoNotFoundUserEmail(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
