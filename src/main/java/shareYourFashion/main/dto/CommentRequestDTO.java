package shareYourFashion.main.dto;

import lombok.*;
import shareYourFashion.main.domain.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDTO extends BaseTimeEntity {

    private Long id;
    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(max = 100, message = "댓글은 100자 이상 입력할 수 없습니다.")
    private String content;

    private User writer;

    private Board board;

    private LookBook lookBook;


    private CommentRequestDTO parent;

    @Null
    private int cDepth;

    private List<CommentRequestDTO> children = new ArrayList<>();


//    public Comment toEntity(){
//        return Comment.builder(){
//
//        }

//    }

}
