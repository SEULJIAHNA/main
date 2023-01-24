package shareYourFashion.main.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.repository.BoardRepository;

import javax.validation.Valid;
import java.util.logging.Logger;
@Controller
@RequiredArgsConstructor

public class BoardController {
    @Autowired
    BoardRepository boardRepository;

    private static Logger logger = Logger.getLogger(BoardController.class.getName());

    private final String BOARD_MAIN_URL = "/boards";
    private final String BOARD_MAIN_HTML = "pc/board/mainBoardPage";
    private final String BOARD_FORM_URL = "/boards/form";
    private final String BOARD_FORM_HTML = "pc/board/boardPageForm";
    private final String BOARD_CONTENT_POST_URL = "/boards/post";//미작성, view도 작성해야함
    private final String BOARD_CONTENT_POST_HTML = "pc/board/boardPost";//미작성, view도 작성해야함


    /* return main board pages */
    @GetMapping(value = BOARD_MAIN_URL)
    public String board(Model m, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);


        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        m.addAttribute("boards", boards);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("/pc/board/mainBoardPage.html");

        return BOARD_MAIN_HTML;
    }

    @GetMapping(value = BOARD_FORM_URL)
    public String boardForm(Model m, @RequestParam(required = false) Long id) {
        if (id == null) {
            m.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            m.addAttribute("board", board);
        }

        return BOARD_FORM_HTML;
    }

    @PostMapping(value = BOARD_CONTENT_POST_URL)
    public String boardSubmit(@Valid Board board, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/pc/board/boardPageForm.html");

            return BOARD_FORM_HTML;
        }
        boardRepository.save(board);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pc/board/boardPost.html");
        return BOARD_CONTENT_POST_HTML;//바로 위 if 모델앤뷰와 걸리지 않는지 체크해야함
    }


}


