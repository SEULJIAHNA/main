package shareYourFashion.main.exception.domainException;

import shareYourFashion.main.exception.global.BaseException;
import shareYourFashion.main.exception.global.BaseExceptionType;

public class BoardException extends BaseException {

    private BaseExceptionType baseExceptionType;


    public BoardException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
