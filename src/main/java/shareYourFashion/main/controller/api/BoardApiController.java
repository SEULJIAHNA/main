package shareYourFashion.main.controller.api;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor

public class BoardApiController {
    @Autowired
    BoardRepository boardRepository;

    private static Logger logger = Logger.getLogger(BoardApiController.class.getName());

    private final String BOARD_MAIN_HTML = "/boards";
    private final String BOARD_FORM_HTML = "/boards/form";
    private final String BOARD_CONTENT_COMPL_HTML = "/boards/complete";

    /* return main board pages */
    @GetMapping(value = BOARD_MAIN_HTML)
    public ModelAndView board(Model m, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);


        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        m.addAttribute("boards", boards);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pc/board/mainBoardPage.html");

        return modelAndView;
    }

    @GetMapping(value = BOARD_FORM_HTML)
    public ModelAndView boardForm(Model m, @RequestParam(required = false) Long id) {
        if (id == null) {
            m.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            m.addAttribute("board", board);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pc/board/boardPageForm.html");

        return modelAndView;
    }

    @PostMapping(value = BOARD_FORM_HTML)
    public ModelAndView boardSubmit(@Valid Board board, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/pc/board/boardPageForm.html");

            return modelAndView;
        }
        boardRepository.save(board);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/pc/board/boardPageCompl.html");
        return modelAndView;//바로 위 if 모델앤뷰와 걸리지 않는지 체크해야함
    }


}


