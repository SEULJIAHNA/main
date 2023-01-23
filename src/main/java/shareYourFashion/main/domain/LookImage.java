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
public class LookImage extends BaseTimeEntity{
    @Column(name= "lookImage_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lookBook_id")
    private LookBook lookBook;

    @Column(nullable = false)
    @Embedded
    private Image image;


}
