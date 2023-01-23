package shareYourFashion.main.constant;

import lombok.NoArgsConstructor;
import shareYourFashion.main.service.FileService;

import java.util.Arrays;

@NoArgsConstructor
public enum ImageType {


    USER_PROFILE_IMAGE("USER_PROFILE_IMAGE"),
    USER_BACKGROUND_PROFILE_IMAGE("USER_BACKGROUND_PROFILE_IMAGE"),
    LOOKBOOK_IMAGE("LOOKBOOK_IMAGE"),
    BOARD_THUMBNAIL("BOARD_THUMBNAIL") ,
    CHAT_IMAGE("CHAT_IMAGE"),
    NO_TYPE("NO_TYPE");

    private String type;

    ImageType(String type) {
        this.type = type;
    }


    // 타입 기준으로 식별
    public static ImageType getImageType(String type) {

        return Arrays.stream(ImageType.values())
                .filter(x -> x.type.equals(type))
                .findAny()
                .orElse(NO_TYPE);
    }

    // 필터링 된 type 반환
    // 필터링된 식별자의 이름 반환
    public static String getImageTypeName (String type) {
        return getImageType(type).getType();

    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
