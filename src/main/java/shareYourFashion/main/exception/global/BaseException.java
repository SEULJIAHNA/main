package shareYourFashion.main.exception.global;

public abstract class BaseException extends RuntimeException{
    public abstract BaseExceptionType getExceptionType();
}