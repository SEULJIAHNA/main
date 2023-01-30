package shareYourFashion.main.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.dto.*;
import shareYourFashion.main.repository.BoardRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final static String VIEWCOOKIENAME = "alreadyViewCookie";

    @Transactional
    public Long save(BoardSaveRequestDTO boardSaveDTO) {
        return boardRepository.save(boardSaveDTO.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BoardUpdateDTO updateDTO){
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalStateException("게시글이 없습니다. id="+id));
        board.update(updateDTO.getTitle(), updateDTO.getContent());
        return  id;
    }

    @Transactional(readOnly = true)
    public HashMap< String, Object > findAll(Integer page, Integer size) {

        HashMap < String, Object > resultMap = new HashMap < String, Object > ();

        Page<Board> list = boardRepository.findAll(PageRequest.of(page, size));

        resultMap.put("list", list.stream().map(BoardResponseDTO::new).collect(Collectors.toList()));
        resultMap.put("paging", list.getPageable());
        resultMap.put("totalCnt", list.getTotalElements());
        resultMap.put("totalPage", list.getTotalPages());

        return resultMap;
    }



    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findAll() {
        return boardRepository.findAll().stream()
                .map(BoardResponseDTO::new).collect(Collectors.toList());
    }

    public BoardResponseDTO findById(Long id){
        return new BoardResponseDTO(boardRepository.findById(id).get());

    }

//    @Transactional
//    public Optional<Board> updateBoard(BoardUpdateDTO boardUpdateDTO){
//        Optional<Board> entity = this.boardRepository.findById(boardUpdateDTO.getId());
//        entity.ifPresent(board ->{
//            if(boardUpdateDTO.getContent() !=null) {
//                board.setContent(boardUpdateDTO.getContent());
//            }
//            if(boardUpdateDTO.getThumbnail() !=null){
//                board.setTitle(boardUpdateDTO.getTitle());
//            }
//            this.boardRepository.save(board);
//        });
//        return entity;
//    }

//    public int updateBoardReadCntInc(Long id) {
//        return boardRepository.updateBoardReadCntInc(id);
//    }


    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    } //한 게시물만 삭제

    public void deleteAll(Long[] deleteId) {
        boardRepository.deleteBoard(deleteId);
    } //다수의 게시물 삭제

    @Transactional
    public void updateView(Long id, BoardRequestDTO boardRequestDTO){
        Board board = boardRepository.findById(id).orElseThrow((()->
                new IllegalStateException("게시글이 존재하지 않습니다.")));

                board.updateView(boardRequestDTO.getView()+1);
    }
    @Transactional // 조회수증가 <적용안됨>
    public int updateView(Long id, HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();
        boolean checkCookie = false;
        int result = 0;
        if(cookies != null){
            for(Cookie cookie : cookies)
            {
                if(cookie.getName().equals((VIEWCOOKIENAME+id))) checkCookie = true;
            }
            if(!checkCookie){
                Cookie newCookie = createCookieForForNotOverlap(id);
                response.addCookie(newCookie);
                result = boardRepository.updateView(id);
            }
        }else{
            Cookie newCookie = createCookieForForNotOverlap(id);
            response.addCookie(newCookie);
            result = boardRepository.updateView(id);
        }
        return result;

    }
    //조회수 중복 방지
    private Cookie createCookieForForNotOverlap(Long id){
        Cookie cookie = new Cookie(VIEWCOOKIENAME+id, String.valueOf(id));
        cookie.setComment("조회수 중복 방지 체크 쿠키");
        cookie.setMaxAge(60 * 60 * 24);//하루의 기한
        cookie.setHttpOnly(true);//서버에서만 조작
        return cookie;
    }

    //==================댓글 서비스 시작 =====//
//
//    @Transactional
//    public ResponseDto<?> createComment(CommentRequestDTO requestDTO, HttpServletRequest request) {
//
//        return null;
//    }




}
