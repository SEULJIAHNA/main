package shareYourFashion.main.domain.valueTypeClass;

import lombok.*;
import shareYourFashion.main.constant.ImageType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {

    @NotEmpty
    private String fileName;

    @NotEmpty
    private String fileOriginName;

    @NotEmpty
    private String storedFilePath;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private ImageType imageType;


    // default image file url
    private static String DEFAULT_PROFILE_IMAGE_URL = "file:///Users/ahnkwanghyun/uploads/fashionCommunity/user/USER_PROFILE_IMAGE/";
    private static String DEFAULT_BACKGROUND_IMAGE_URL  = "file:///Users/ahnkwanghyun/uploads/fashionCommunity/user/USER_BACKGROUND_PROFILE_IMAGE/";
    private static String DEFAULT_PROFILE_IMAGE_NAME = "defaultProfileImage.jpeg";
    private static String DEFAULT_BACKGROUND_IMAGE_NAME = "defaultBDProfileImg.jpeg";

    private static UUID uuid;

    public static Image createDefaultProfileImage() {
        // 랜덤 네임 부여
        uuid = UUID.randomUUID();

        return new Image().builder()
                .fileName(uuid.toString() + "_defaultProfileImage.jpeg").fileOriginName("defaultProfileImage.jpeg")
                        .storedFilePath(getDefaultProfileURL()).imageType(ImageType.USER_PROFILE_IMAGE).build();
    }

    // default background image 생성
    public static Image createDefaultBackgroundProfileImage() {

        // 랜덤 네임 부여
        uuid = UUID.randomUUID();

        return new Image().builder()
                .fileName(uuid.toString() + "_defaultBDProfileImg.jpg").fileOriginName("defaultBDProfileImg.jpg")
                .storedFilePath(getDefaultBackgroundImageURL()).imageType(ImageType.USER_BACKGROUND_PROFILE_IMAGE).build();
    }


    public static String getDefaultProfileURL() {
        return DEFAULT_PROFILE_IMAGE_URL + DEFAULT_PROFILE_IMAGE_NAME ;
    }

    public static String getDefaultBackgroundImageURL() {
        return DEFAULT_BACKGROUND_IMAGE_URL + DEFAULT_BACKGROUND_IMAGE_NAME;
    }


}
