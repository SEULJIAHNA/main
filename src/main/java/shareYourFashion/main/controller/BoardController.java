package shareYourFashion.main.controller;


import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.dto.BoardRequestDTO;
import shareYourFashion.main.repository.BoardRepository;
import shareYourFashion.main.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.logging.Logger;
@Controller
@RequiredArgsConstructor

public class BoardController {


    private final BoardRepository boardRepository;
    private final BoardService boardService;

    private static Logger logger = Logger.getLogger(BoardController.class.getName());

    private final String BOARD_MAIN_URL = "/boards";
    private final String BOARD_MAIN_HTML = "pc/board/mainBoardPage";
    private final String BOARD_FORM_URL = "/boards/form";
    private final String BOARD_FORM_HTML = "pc/board/boardPageForm";
    private final String BOARD_CONTENT_VIEW_PAGE_URL = "/boards/view";
    private final String BOARD_CONTENT_VIEW_PAGE_HTML = "pc/board/boardViewPage";
    private final String BOARD_UPDATE_PAGE_URL = "/boards/updateForm";
    private final String BOARD_DELETE_PAGE_URL = "/boards/deletePage";
    private final String BOARD_DELETE_ALL_URL = "/boards/deleteAll";

    /* return main board pages */
    @GetMapping(value = BOARD_MAIN_URL) //"/boards";
    public String board(Long id, BoardRequestDTO requestDTO, Model m, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);


        m.addAttribute("startPage", startPage);
        m.addAttribute("endPage", endPage);
        m.addAttribute("boards", boards);

        return BOARD_MAIN_HTML;
    }

    @GetMapping(value = BOARD_FORM_URL)  // "/boards/form";
    public String boardForm(Model m, @RequestParam(required = false) Long id) {
        if (id == null) {
            m.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            m.addAttribute("board", board);
        }

        return BOARD_FORM_HTML;
    }

    @PostMapping(value = BOARD_FORM_URL) //게시판 글 작성 폼
    public String pastForm(@Valid Board board, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return BOARD_FORM_HTML;
        }
        boardRepository.save(board);
        return BOARD_CONTENT_VIEW_PAGE_URL;//바로 위 if 모델앤뷰와 걸리지 않는지 체크해야함
    }

    @GetMapping(value = BOARD_CONTENT_VIEW_PAGE_URL) //게시글 상세조회 "/boards/view";
    public String getBoardViewPage(Model m, BoardRequestDTO boardRequestDTO, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            if(boardRequestDTO.getId() != null){
                    boardService.updateView(boardRequestDTO.getId(), request, response);
                m.addAttribute("board", boardService.findById(boardRequestDTO.getId()));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return BOARD_CONTENT_VIEW_PAGE_HTML;
    }

//    @GetMapping(value = BOARD_MAIN_URL)//조회수
//    public String updateView(@PathVariable("id") Long id, Model m){
//        Board board = boardRepository.findById(id).get();
//        int view = board.getView() + 1;
//
//        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
//                .view(view)
//                .build();
//
//        boardService.updateView(board.getId(), boardRequestDTO);
//
//        m.addAttribute(board);
//        return BOARD_CONTENT_VIEW_PAGE_HTML;
//    }


//    @PostMapping(value = BOARD_DELETE_PAGE_URL)
//    public String boardDeletePage(Model m, @RequestParam() Long id, Criteria cri, RedirectAttributes re) throws Exception{
//        try {
//            boardService.deleteById(id);
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
//        return BOARD_MAIN_HTML;
//    }
    @PostMapping(value = BOARD_DELETE_ALL_URL)
    public String boardDeleteALL(Model m, @RequestParam() Long[] deleteId) throws Exception{
        try {
            boardService.deleteAll(deleteId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return BOARD_MAIN_HTML;
    }


}


