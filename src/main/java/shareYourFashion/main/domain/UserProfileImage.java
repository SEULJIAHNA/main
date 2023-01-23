package shareYourFashion.main.domain;

import lombok.*;
import shareYourFashion.main.domain.valueTypeClass.Image;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter @Setter
@DynamicInsert // db에 insert 시 null인 field 제외
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileImage extends BaseTimeEntity implements ImageEntity {
    @Column(name= "userProfileImage_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Column(nullable = false)
    @Embedded
    private Image image;



    // user 생성 시 default PROFILE IMAGE 저장 하는 함수
    public static UserProfileImage createDefaultProfileImageEntity() {
        return new UserProfileImage().builder().image(Image.createDefaultProfileImage())
                .build();
    }


    @Override
    public String toString() {
        return "UserProfileImage{" +
                "id=" + id +
                ", image=" + image +
                '}';
    }
}
