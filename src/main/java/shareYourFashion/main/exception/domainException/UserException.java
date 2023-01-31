package shareYourFashion.main.exception.domainException;

import shareYourFashion.main.exception.global.BaseException;
import shareYourFashion.main.exception.global.BaseExceptionType;

public class UserException extends BaseException {
    private BaseExceptionType exceptionType;


    public UserException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
