package shareYourFashion.main.controller.api;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.util.StringUtils;
import shareYourFashion.main.domain.Board;
import shareYourFashion.main.dto.BoardRequestDTO;
import shareYourFashion.main.dto.BoardSaveRequestDTO;
import shareYourFashion.main.dto.BoardUpdateDTO;
import shareYourFashion.main.repository.BoardRepository;
import shareYourFashion.main.service.BoardService;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor

public class BoardApiController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;


    @GetMapping("/api/board")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return boardRepository.findAll();
        } else {
            return boardRepository.findByTitleOrContent(title, content);
        }

    }

    @PostMapping("/api/board")//글저장
    ResponseEntity<?>  newBoardSave(@RequestBody BoardSaveRequestDTO newBoardSaveRequestDTO, Errors errors , Model model , UriComponentsBuilder b) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        UriComponents uriComponents;
        try {
            Long saveId = boardService.save(newBoardSaveRequestDTO);
            /*저장 성공 후 boards 페이지로 리다이렉트*/
            uriComponents = b.fromUriString ("/boards/view?id=" + saveId).build();
            headers.setLocation(uriComponents.toUri());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Void>(headers , HttpStatus.OK);

    }

    @GetMapping("/api/board/{id}")//글 하나만 보기
    Board one(@PathVariable Long id){
        return boardRepository.findById(id).orElse(null);
    }

//    @PutMapping("/api/board/{id}")//게시글 수정
//    public Long update(@PathVariable Long id, @RequestBody BoardUpdateDTO updateDTO){
//        return boardService.update(id, updateDTO);
//    }
    @PostMapping("/api/board/update")//게시글 수정
    ResponseEntity<?>  update2(@RequestBody BoardUpdateDTO updateDTO, Errors errors , Model model , UriComponentsBuilder b) throws Exception{

        HttpHeaders headers = new HttpHeaders();
        UriComponents uriComponents;
        try {
            boardService.update(updateDTO.getId(), updateDTO);
            /*저장 성공 후 boards 페이지로 리다이렉트*/
            uriComponents = b.fromUriString("/boards/view?id=" + updateDTO.getId()).build();
            headers.setLocation(uriComponents.toUri());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Void>(headers , HttpStatus.OK);

    }

    @ResponseBody
    @PostMapping("/api/board/delete/{id}")//글 삭제
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        HttpHeaders headers = new HttpHeaders();

        try {
            boardService.deleteById(id);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(headers , HttpStatus.BAD_REQUEST);
        }

        // 성공적으로 게시글 삭제 시 게시글 목록 페이지로 이동
        headers.setLocation(URI.create("/boards"));

        return new ResponseEntity<>(headers , HttpStatus.MOVED_PERMANENTLY);
    }

}


