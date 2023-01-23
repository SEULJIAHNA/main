package shareYourFashion.main.service;

import org.springframework.web.multipart.MultipartFile;
import shareYourFashion.main.domain.BackgroundProfileImage;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.domain.UserProfileImage;
import shareYourFashion.main.domain.valueTypeClass.Image;

import java.util.List;

public interface FileService {

//    void storeImageToDB(User user , Image image);

    /*
     *  image crud 서비스
     * */


    UserProfileImage findProfileImageByImageFileName(String fileName);

    BackgroundProfileImage findBackgroundImageByFileName(String fileName);

    UserProfileImage createUserProfileEntity(Image image);

    BackgroundProfileImage createBackgroundProfileImageEntity(Image image);

    String findProfileImageURL(UserProfileImage profileImage);

    String findBgProfileURL(BackgroundProfileImage bgProfileImage);
}
