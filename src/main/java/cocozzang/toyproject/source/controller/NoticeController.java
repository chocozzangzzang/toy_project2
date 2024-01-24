package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.NoticeDTO;
import cocozzang.toyproject.source.service.NoticeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
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
        return "notice";
    }

    @GetMapping("/notice/write")
    public String noticeWritePage(Model model) {

        model.addAttribute("noticeWriter", SecurityContextHolder.getContext().getAuthentication().getName());
        return "noticeWrite";
    }


    @ResponseBody
    @PostMapping("/notice/write")
    public String noticeWrite(@RequestBody Map<String, Object> map) {
        NoticeDTO noticeDTO = new NoticeDTO();

        System.out.println(map);
        noticeDTO.setNoticeWriter(SecurityContextHolder.getContext().getAuthentication().getName());
        noticeDTO.setNoticeTitle((String) map.get("noticeTitle"));
        noticeDTO.setNoticeContent((String) map.get("noticeContent"));
        noticeDTO.setRegTime(String.valueOf(LocalDate.now()));
        noticeDTO.setModTime(String.valueOf(LocalDate.now()));

        noticeService.noticeWrite(noticeDTO);

        return "redirect:/notice";
    }

    @GetMapping("/notice/{nid}")
    public String noticeDetail(@PathVariable(value = "nid") Long nid, Model model) {

        NoticeDTO noticeDTO = noticeService.getNotice(nid);

        model.addAttribute("nowId", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("notice", noticeDTO);

        return "noticeDetail";
    }

    @ResponseBody
    @DeleteMapping("/notice/delete")
    public void noticeDelete(@RequestParam(value="nid") Long nid) {

        System.out.println("DELETE : " + nid);
        noticeService.deleteNotice(nid);
    }

    @GetMapping("/notice/modifyPage")
    public String noticeModifyPage(@RequestParam(value = "nid") Long nid, Model model) {
        System.out.println(nid);
        NoticeDTO noticeDTO = noticeService.getNotice(nid);
        model.addAttribute("nowUser", SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("notice", noticeDTO);
        return "noticeModify";
    }

    @ResponseBody
    @PutMapping("/notice/modify")
    public void noticeModify(@RequestBody Map<String, Object> map) {

        System.out.println("PutMapping!!!!");

        NoticeDTO noticeDTO = noticeService.getNotice(Long.valueOf((String) map.get("noticeId")));

        noticeDTO.setNoticeTitle((String) map.get("noticeModifyTitle"));
        noticeDTO.setNoticeWriter((String) map.get("noticeWriter"));
        noticeDTO.setNoticeContent((String) map.get("noticeModifyContent"));
        noticeDTO.setModTime(String.valueOf(LocalDate.now()));

        noticeService.modifyNotice(noticeDTO);
    }
}
