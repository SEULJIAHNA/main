package shareYourFashion.main.domain.valueTypeClass;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    String postCode; // 우편번호
    String roadAddress; // 도로명주소
    String jibunAddress; // 지번주소
    String detailAddress; // 상세주소
    String extraAddress;  // 참고항목


    public String getFullAddress(){
        return "[" + postCode + "] " + roadAddress + " " + detailAddress + " " + "(" + extraAddress + ")";
    }

    @Override
    public String toString() {
        return "Address{" +
                "postCode='" + postCode + '\'' +
                ", roadAddress='" + roadAddress + '\'' +
                ", jibunAddress='" + jibunAddress + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", extraAddress='" + extraAddress + '\'' +
                '}';
    }


}
