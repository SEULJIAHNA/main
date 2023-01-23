package shareYourFashion.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shareYourFashion.main.domain.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

//    List<Board> findAll(String title);
//    List<Board> findByTitlteOrContent(String title, String content);
    Page<Board> findByTitleContainingOrContentContaining(String title, String Content, Pageable Pageable);

}
