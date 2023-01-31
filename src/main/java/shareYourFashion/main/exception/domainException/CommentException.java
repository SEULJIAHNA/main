package shareYourFashion.main.exception.domainException;

import shareYourFashion.main.exception.global.BaseException;
import shareYourFashion.main.exception.global.BaseExceptionType;

public class CommentException extends BaseException {
    private BaseExceptionType baseExceptionType;

    public CommentException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }

}
