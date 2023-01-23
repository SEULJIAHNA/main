package shareYourFashion.main.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter @Setter

public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lookBook_id")
    private LookBook lookBook;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Null
    @Max(2)
    @Min(0)
    private int cDepth;

    @OneToMany(mappedBy = "parent" , orphanRemoval = true)
    @Builder.Default // 특정 속성에 기본값을 지정
    private List<Comment> children = new ArrayList<>();


    //== 생성자 ==//
    public Comment(){};

    //= 연관관계 메소드 =//
    public void setBoardComment(Board board){
        this.board = board;
        board.getComments().add(this);
    }

}
