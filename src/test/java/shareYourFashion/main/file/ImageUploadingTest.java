package shareYourFashion.main.file;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.constant.ImageType;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.constant.Sex;
import shareYourFashion.main.domain.BackgroundProfileImage;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.domain.User;
import shareYourFashion.main.constant.JoinPath;
import shareYourFashion.main.domain.UserProfileImage;
import shareYourFashion.main.domain.valueTypeClass.Image;
import shareYourFashion.main.repository.BackgroundProfileImageRepository;
import shareYourFashion.main.repository.ProfileImageRepository;
import shareYourFashion.main.repository.UserRepository;
import shareYourFashion.main.service.FileUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import java.io.FileInputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional(readOnly = true)
class ImageUploadingTest {

    static User user;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private BackgroundProfileImageRepository backgroundProfileImageRepository;

    @Autowired
    private UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager em;

    @BeforeClass
    public static void createUserEntity() {
        user = User.builder().age("24").roles(Roles.USER)
                .email("agh0314@naver.com").nickname("ahn").password("21312321")
                .joinPath(JoinPath.DEFAULT_ACCOUNT)
                .build();

        System.out.println("user.toString() = " + user.toString());
    }

    @Test
    @Transactional
    public void 기본이미지엔터티생성테스트() throws Exception{
        //given

        user = User.builder().age("24").roles(Roles.USER)
            .email("agh0338@naver.com").nickname("ahn2213").password("21312321")
            .joinPath(JoinPath.DEFAULT_ACCOUNT)
            .build();

        UserProfileImage profileImage = UserProfileImage.createDefaultProfileImageEntity();
        System.out.println("user = " + user);
        user.userToProfileImage(profileImage);

        //when

        em.persist(user);
        em.persist(profileImage);
        em.flush();
        //then

        assertThat(profileImage).isEqualTo(user.getProfileImage());

    }

    @Test
    @Transactional
    public void 이미지업로드테스트() throws Exception{
        //given
        File file = new File(new File("").getAbsolutePath() +"/src/main/resources/static/images/main-logo-image.png");
        DiskFileItem fileItem = new DiskFileItem("file",
                Files.probeContentType(file.toPath()), false, file.getName(),
                (int) file.length() , file.getParentFile());
        
        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            // Or faster..
            // IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        MultipartFile testImage = new CommonsMultipartFile(fileItem);

        // when
        // multipartFile 특정 폴더에 업로드
        fileUtils.saveImage(testImage , ImageType.USER_PROFILE_IMAGE.toString());

        // then
        System.out.println("testImage.getName() = " + testImage.getName());
        profileImageRepository.findByImageFileName(testImage.getName());
    }

    @Test
    @Transactional
    public void 이미지엔티티유저엔티티에등록() throws Exception{
        //given

        User user = User.builder().age("24").roles(Roles.USER)
                .email("agh0318@naver.com").nickname("ahn3213").password("21312321")
                .joinPath(JoinPath.DEFAULT_ACCOUNT)
                .build();


        UserProfileImage profileImage = UserProfileImage.createDefaultProfileImageEntity();
        BackgroundProfileImage bgProfileImage = BackgroundProfileImage.createDefaultBackgroundProfileImageEntity();

        System.out.println("user.toString() = " + user.toString());
        // 1대1 매핑 연관관계 메소드 (양방향) 
        user.setProfileImage(profileImage);
        user.setBackgroundImage(bgProfileImage);



        //when
        userRepository.save(user);
        backgroundProfileImageRepository.save(user.getBackgroundImage());
        profileImageRepository.save(user.getProfileImage());

        //then

        System.out.println("user.getProfileImage() = " + user.getProfileImage().toString());

    }

}
