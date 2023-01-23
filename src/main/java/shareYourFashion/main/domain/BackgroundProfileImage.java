package shareYourFashion.main.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import shareYourFashion.main.domain.valueTypeClass.Image;

import javax.persistence.*;

@Entity
@Data
@DynamicInsert // db에 insert 시 null인 field 제외
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackgroundProfileImage extends BaseTimeEntity implements ImageEntity {
    @Column(name= "backgroundProfileImage_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    @Embedded
    private Image image;

    /* 엔티티 메소드 */


    // user 생성 시 default PROFILE IMAGE 저장 하는 함수
    public static BackgroundProfileImage createDefaultBackgroundProfileImageEntity() {
        return new BackgroundProfileImage().builder().image(Image.createDefaultBackgroundProfileImage())
                .build();
    }


}
