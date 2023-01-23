package shareYourFashion.main.exception;


public class DoNotFoundUserNickname extends Exception {
    public DoNotFoundUserNickname() {
        super();
    }

    public DoNotFoundUserNickname(String message) {
        super(message);
    }

    public DoNotFoundUserNickname(String message, Throwable cause) {
        super(message, cause);
    }

    public DoNotFoundUserNickname(Throwable cause) {
        super(cause);
    }
}
