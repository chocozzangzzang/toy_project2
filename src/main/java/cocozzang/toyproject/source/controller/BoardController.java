package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.AttachedFileDTO;
import cocozzang.toyproject.source.dto.BoardDTO;
import cocozzang.toyproject.source.dto.CommentDTO;
import cocozzang.toyproject.source.dto.TempBoardDTO;
import cocozzang.toyproject.source.service.BoardService;
import cocozzang.toyproject.source.service.CommentService;
import cocozzang.toyproject.source.service.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

import java.time.LocalDate;
import java.util.*;

@Controller
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final FileService fileService;

    public BoardController(BoardService boardService, CommentService commentService, FileService fileService) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.fileService = fileService;
    }

    @GetMapping("/board")
    public String board(Model model) {
        List<BoardDTO> boardDTOList = boardService.boardTotal();

        List<TempBoardDTO> boardmap = new ArrayList<>();
        for (BoardDTO boardDTO : boardDTOList) {
            boardmap.add(new TempBoardDTO(boardDTO, fileService.isFileExists(boardDTO.getBoardId())));
        }
        System.out.println(boardmap);
        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("boardMap", boardmap);
        return "board";
    }

    @GetMapping("/board/write")
    public String boardWrite(Model model) {
        String writer = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("writer", writer);
        return "boardWrite";
    }

    @Transactional
    @ResponseBody
    @PostMapping(value = "/board/write", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String boardWrite(
            HttpServletRequest hsRequest,
            @RequestPart("uploadFiles") MultipartFile[] files,
            @RequestParam("boardDetail") String map) throws ParseException {

        BoardDTO boardDTO = new BoardDTO();

        JSONParser jsonParser = new JSONParser();
        System.out.println(map);
        JSONObject obj = (JSONObject) jsonParser.parse(map);

        boardDTO.setTitle((String) obj.get("title"));
        boardDTO.setWriter(SecurityContextHolder.getContext().getAuthentication().getName());
        boardDTO.setContent((String) obj.get("content"));

        List<AttachedFileDTO> attachedFileDTOList = new ArrayList<>();

        System.out.println(obj);

        for (MultipartFile mf : files) {
            System.out.println(mf.getOriginalFilename());
            System.out.println(mf.getSize());

            UUID uuid = UUID.randomUUID();
            String savedFileName = uuid + "_" + mf.getOriginalFilename();

            if (mf.getOriginalFilename() != null) {
                File saveFile = new File("D:\\testFolder", savedFileName);
                try {
                    mf.transferTo(saveFile);
                    AttachedFileDTO attachedFileDTO = new AttachedFileDTO();
                    attachedFileDTO.setStoredFileName(savedFileName);
                    attachedFileDTO.setOriginalFileName(mf.getOriginalFilename());
                    attachedFileDTOList.add(attachedFileDTO);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println(boardDTO);
        Long bid = boardService.boardWrite(boardDTO);

        if (attachedFileDTOList.size() > 0) {
            for (AttachedFileDTO attachedFileDTO : attachedFileDTOList) {
                attachedFileDTO.setBid(bid);
                fileService.fileSave(attachedFileDTO);
            }
        }

        return "redirect:/board";
    }

    @Transactional
    @ResponseBody
    @PostMapping(value = "/board/write2")
    public String boardWrite2(
            HttpServletRequest hsRequest,
            @RequestBody Map<String, Object> obj) throws ParseException {

        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setTitle((String) obj.get("title"));
        boardDTO.setWriter(SecurityContextHolder.getContext().getAuthentication().getName());
        boardDTO.setContent((String) obj.get("content"));

        System.out.println(obj);
        System.out.println(boardDTO);
        Long bid = boardService.boardWrite(boardDTO);

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

        List<CommentDTO> commentDTO = commentService.boardComment(bid);
        model.addAttribute("comments", commentDTO);
        model.addAttribute("nowUser", SecurityContextHolder.getContext().getAuthentication().getName());

        List<AttachedFileDTO> attachedFileDTOList = fileService.findAllFiles(bid);

        if (attachedFileDTOList.size() > 0) {
            model.addAttribute("fileExists", true);
            model.addAttribute("files", attachedFileDTOList);
        } else {
            model.addAttribute("fileExists", false);
        }



        return "boardDetail";
    }

    @ResponseBody
    @GetMapping("/board/delete")
    public void boardDetail(@RequestParam(value="bid") Long bid) {
        boardService.boardDelete(bid);
        commentService.commentDeleteByBid(bid);

        List<AttachedFileDTO> attachedFileDTOList = fileService.findAllFiles(bid);
        fileService.deleteAllFiles(bid);

        for (AttachedFileDTO attachedFileDTO : attachedFileDTOList) {
            File file = new File("D:/testfolder/" + attachedFileDTO.getStoredFileName());
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println(attachedFileDTO.getOriginalFileName() + " is deleted !!");
                } else {
                    System.out.println(attachedFileDTO.getOriginalFileName() + " is not deleted !!");
                }
            }
        }
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
