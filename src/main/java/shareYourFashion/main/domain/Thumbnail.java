package shareYourFashion.main.domain;

import shareYourFashion.main.domain.valueTypeClass.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Data
@DynamicInsert // db에 insert 시 null인 field 제외
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Thumbnail extends BaseTimeEntity{
    @Column(name= "thumbnail_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(nullable = false)
    @Embedded
    private Image image;


}

