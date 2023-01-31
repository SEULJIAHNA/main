package shareYourFashion.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shareYourFashion.main.domain.Comment;
import shareYourFashion.main.dto.CommentSaveDTO;
import shareYourFashion.main.exception.domainException.*;
import shareYourFashion.main.repository.BoardRepository;
import shareYourFashion.main.repository.CommentRepository;
import shareYourFashion.main.repository.UserRepository;
import shareYourFashion.main.config.auth.SecurityUtil;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository; //User랑 상의
    private final BoardRepository boardRepository;

    public void save(Long boardId, CommentSaveDTO commentSaveDTO){
        Comment comment = commentSaveDTO.toEntity();
//        comment.confirmWriter(UserRepository.findByUserName(SecurityUtil.getLoginUsername()).orElseThrow(()-> new UserException(UserExceptionType.NOT_FOUND_MEMBER)));//User랑 상의
        comment.confirmBoard(boardRepository.findById(boardId).orElseThrow(()->new BoardException(BoardExceptionType.POST_NOT_POUND)));
        commentRepository.save(comment);

    }

    public void saveReComment(Long postId, Long parentId, CommentSaveDTO commentSaveDto) {
        Comment comment = commentSaveDto.toEntity();
        //User랑 상의
        comment.confirmWriter(userRepository.findByNickname(SecurityUtil.getLoginUsername()).orElseThrow(() -> new UserException(UserExceptionType.NOT_FOUND_MEMBER)));

        comment.confirmBoard(boardRepository.findById(postId).orElseThrow(() -> new BoardException(BoardExceptionType.POST_NOT_POUND)));

        comment.confirmParent(commentRepository.findById(parentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_POUND_COMMENT)));

        commentRepository.save(comment);

    }

    public void remove(Long id) throws CommentException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_POUND_COMMENT));

        if(!comment.getWriter().getNickname().equals(SecurityUtil.getLoginUsername())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_DELETE_COMMENT);
        }

        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);
    }
}




