package shareYourFashion.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.dto.BoardRequestDTO;
import shareYourFashion.main.dto.BoardResponseDTO;
import shareYourFashion.main.dto.BoardSaveRequestDTO;
import shareYourFashion.main.dto.BoardUpdateDTO;
import shareYourFashion.main.repository.BoardRepository;
import shareYourFashion.main.repository.CommentRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;



}
