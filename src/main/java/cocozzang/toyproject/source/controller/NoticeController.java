package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.NoticeDTO;
import cocozzang.toyproject.source.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String noticePage(Model model) {

        List<NoticeDTO> noticeDTOList = noticeService.getAllNotices();

        model.addAttribute("noticeList", noticeDTOList);
        return "/notice";
    }

    @GetMapping("/notice/write")
    public String noticeWritePage() {
        return "/noticeWrite";
    }


    @ResponseBody
    @PostMapping("/notice/write")
    public String noticeWrite(@RequestBody Map<String, Object> map) {
        NoticeDTO noticeDTO = new NoticeDTO();

        System.out.println(map);

        noticeDTO.setNoticeTitle((String) map.get("noticeTitle"));
        noticeDTO.setNoticeContent((String) map.get("noticeContent"));
        noticeDTO.setRegTime(String.valueOf(LocalDate.now()));
        noticeDTO.setModTime(String.valueOf(LocalDate.now()));

        noticeService.noticeWrite(noticeDTO);

        return "redirect:/notice";
    }
}
