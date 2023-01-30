package shareYourFashion.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.domain.Comment;
import shareYourFashion.main.dto.BoardRequestDTO;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {




}
