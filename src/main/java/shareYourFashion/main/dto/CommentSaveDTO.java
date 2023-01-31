package shareYourFashion.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import shareYourFashion.main.domain.Comment;
import shareYourFashion.main.domain.Board;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentSaveDTO{



    private String content;

    public String getContent() {
        return content;
    }
    public Comment toEntity() {
        return Comment.builder().content(content).build();
    }



}
