package shareYourFashion.main.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Like extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user; // user와 다대일 관계 -> like를 연관관계 주인으로 설정

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="lookBook_id")
    private LookBook lookBook;

//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="chat_id")
//    private Like like;


    //= 연관관계 메소드 =//
    public void setBoardLike(Board board){
        this.board = board;
        board.getLikes().add(this);
    }
}
