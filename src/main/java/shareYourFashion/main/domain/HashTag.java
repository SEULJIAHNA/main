package shareYourFashion.main.domain;

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
public class HashTag {

    @Column(name = "hashTag_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false , name = "tagName" , length = 20)
    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lookBook_id")
    private LookBook lookBook;


    //= 연관관계 메소드 =//
    public void setBoardHashTag(Board board){
        this.board = board;
        board.getHashTags().add(this);
    }

}
