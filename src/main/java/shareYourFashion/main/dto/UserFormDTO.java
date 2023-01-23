package shareYourFashion.main.dto;

import lombok.*;
import shareYourFashion.main.constant.Roles;
import shareYourFashion.main.domain.valueTypeClass.Address;
import shareYourFashion.main.constant.JoinPath;
import shareYourFashion.main.constant.Sex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


/*
*  회원가입 정보 받아오는 dto
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFormDTO {

    @NotEmpty(message = "이메일을 입력해주세요! ")
    @Email(message = "부정확한 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$", message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "나이는 필수 입력 값입니다.")
    private String age;

    private Sex sex;

    private Address address;

    private Roles user_role = Roles.USER ;

    private JoinPath joinPath = JoinPath.DEFAULT_ACCOUNT;


    @Override
    public String toString() {
        return "UserFormDto{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                ", sex=" + sex +
                ", address=" + address.toString() +
                ", user_role=" + user_role.name() +
                ", joinPath=" + joinPath +
                '}';
    }
}
