package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.BoardDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class BoardController {

    @GetMapping("/board")
    public String board() {

        // 게시물 리스트 조회

        return "board";
    }

    @GetMapping("/board/write")
    public String boardWrite() {
        return "boardWrite";
    }

    @PostMapping("/board/write")
    @ResponseBody
    public String boardWrite(@RequestParam Map<String, Object> map) {

        System.out.println("1 : " + map.get("writer"));
        System.out.println("2 : " + map.get("content"));
        System.out.println("3 : " + map.get("title"));

        return "redirect:/board";
    }

}
