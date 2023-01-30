package shareYourFashion.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ResponseDTO<T> {

    @Getter
    @AllArgsConstructor
    public static class ResponseDto<T>{

        private boolean success;
        private T data;
        private Error error;

        public static <T> ResponseDto<T> success(T data) {
            return new ResponseDto<>(true, data, null);
        }
        public static <T> ResponseDto<T> fail(String code, String message) {
            return new ResponseDto<>(false, null, new Error(code, message));
        }

        @Getter
        @AllArgsConstructor
        static class Error {
            private String code;
            private String message;
        }
    }
}
