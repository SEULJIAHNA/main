package shareYourFashion.main.dto;

import lombok.Data;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@Data
public class KakaoUserDTO {

    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    @Generated("jsonschema2pojo")
    @Data
    public class Properties {
        public String nickname;
    }

    @Data
    public class KakaoAccount {

        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;
        public String password;

        @Generated("jsonschema2pojo")
        @Data
        public class Profile {

            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
            public Boolean is_default_image;

        }

    }

    @Override
    public String toString() {
        return "KakaoProfile{" +
                "id=" + id +
                ", connected_at='" + connected_at + '\'' +
                ", properties=" + properties.toString() +
                ", kakao_account=" + kakao_account.toString() +
                '}';
    }
}

