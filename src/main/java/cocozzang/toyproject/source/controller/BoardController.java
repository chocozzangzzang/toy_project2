package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.BoardDTO;
import cocozzang.toyproject.source.service.BoardService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public String board(Model model) {
        List<BoardDTO> boardDTOList = boardService.boardTotal();
        model.addAttribute("boardList", boardDTOList);
        return "board";
    }

    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        String writer = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("writer", writer);
        return "boardWrite";
    }

    @ResponseBody
    @PostMapping("/board/write")
    public String boardWrite(@RequestBody Map<String, Object> map) {

        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle((String) map.get("title"));
        boardDTO.setWriter(SecurityContextHolder.getContext().getAuthentication().getName());
        boardDTO.setContent((String) map.get("content"));

        boardService.boardWrite(boardDTO);

        return "redirect:/board";
    }

    @GetMapping("/board/{bid}")
    public String boardDetail(@PathVariable Long bid, Model model) {
        BoardDTO boardDTO = boardService.boardDetail(bid);
        model.addAttribute("bid", boardDTO.getBoardId());
        model.addAttribute("writer", boardDTO.getWriter());
        model.addAttribute("title", boardDTO.getTitle());
        model.addAttribute("content", boardDTO.getContent());
        model.addAttribute("nowId", SecurityContextHolder.getContext().getAuthentication().getName());

        return "boardDetail";
    }

    @ResponseBody
    @GetMapping("/board/delete")
    public void boardDetail(@RequestParam(value="bid") Long bid) {
        boardService.boardDelete(bid);
    }

    @ResponseBody
    @PostMapping("/board/modify")
    public void boardModify(@RequestBody Map<String, Object> map) {
        Long bid = Long.valueOf((String) map.get("boardId"));
    }

    @GetMapping("/board/modifyPage")
    public String modifyPage(@RequestParam(value="bid") Long bid, Model model) {

        BoardDTO boardDTO = boardService.boardDetail(bid);
        model.addAttribute("board", boardDTO);
        model.addAttribute("modTime", String.valueOf(LocalDate.now()));
        return "boardModify";
    }

    @ResponseBody
    @PostMapping("/board/modifyPage")
    public void modifyPage(@RequestBody Map<String, Object> map) {

        BoardDTO boardDTO = new BoardDTO(
                Long.valueOf((String) map.get("boardId")),
                (String) map.get("title"),
                (String) map.get("writer"),
                (String) map.get("content"),
                (String) map.get("regTime"),
                (String) map.get("modTime")
        );

        boardService.boardUpdate(boardDTO);
    }

}
