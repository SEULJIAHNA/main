package shareYourFashion.main.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import shareYourFashion.main.dto.CommentSaveDTO;
import shareYourFashion.main.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/boards/view/comment/{boardId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void commentSave(@PathVariable("boardId") Long boardId, CommentSaveDTO commentSaveDTO){
        commentService.save(boardId, commentSaveDTO);
    }

    @PostMapping("/boards/view/comment/{boardId}/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void reCommentSave(@PathVariable("boardId") Long boardId,
                              @PathVariable("commentId") Long commentId,
                              CommentSaveDTO commentSaveDTO){
        commentService.saveReComment(boardId, commentId, commentSaveDTO);
    }

    @DeleteMapping("/boards/view/comment/{commentId}")
    public void commentDelete(@PathVariable("commentId") Long commentId){
        commentService.remove(commentId);
    }
}
