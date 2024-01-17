package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.CommentDTO;
import cocozzang.toyproject.source.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseBody
    @PostMapping("/comment/write")
    public void commentWrite(@RequestBody Map<String, Object> map) {
        System.out.println(map);
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setBid(Long.valueOf((String) map.get("boardId")));
        commentDTO.setCommentWriter((String) map.get("commentWriter"));
        commentDTO.setComment((String) map.get("comment"));
        commentDTO.setCommRegTime(String.valueOf(LocalDate.now()));
        commentDTO.setCommModTime(String.valueOf(LocalDate.now()));

        commentService.commentWrite(commentDTO);
    }

    @ResponseBody
    @GetMapping("/comment/delete")
    public void commentDelete(@RequestParam Long cid) {
        commentService.commentDelete(cid);
    }

    @ResponseBody
    @PostMapping("/comment/update")
    public void commentUpdate(@RequestBody Map<String, Object> map) {

        System.out.println(map);

        Long cid = Long.valueOf((String) map.get("commentID"));
        CommentDTO commentDTO = commentService.getComment(cid);
        commentDTO.setComment((String) map.get("commentUpdate"));
        commentDTO.setCommModTime(String.valueOf(LocalDate.now()));

        System.out.println(commentDTO);
        commentService.commentUpdate(commentDTO);
    }

}
