package cocozzang.toyproject.source.controller;

import cocozzang.toyproject.source.dto.NoticeDTO;
import cocozzang.toyproject.source.service.NoticeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Controller
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;

    }

    @GetMapping("/notice")
    public String noticePage(Model model) {

        List<NoticeDTO> noticeDTOList = noticeService.getAllNotices();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> collection = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        if (Objects.equals(role, "ROLE_ADMIN")) {
            model.addAttribute("noticeList", noticeDTOList);
            return "notice";
        } else {
            return "redirect:/board";
        }
    }

    @GetMapping("/notice/write")
    public String noticeWritePage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> collection = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        if (Objects.equals(role, "ROLE_ADMIN")) {
            model.addAttribute("noticeWriter", SecurityContextHolder.getContext().getAuthentication().getName());
            return "noticeWrite";
        } else {
            return "redirect:/board";
        }


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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> collection = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        if (Objects.equals(role, "ROLE_ADMIN")) {
            model.addAttribute("nowId", SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("notice", noticeDTO);
            return "noticeDetail";
        } else {
            return "redirect:/board";
        }
    }

    @ResponseBody
    @DeleteMapping("/notice/delete")
    public String noticeDelete(@RequestParam(value="nid") Long nid) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> collection = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        if (Objects.equals(role, "ROLE_ADMIN")) {
            noticeService.deleteNotice(nid);
            return null;
        } else {
            return "redirect:/board";
        }

    }

    @GetMapping("/notice/modifyPage")
    public String noticeModifyPage(@RequestParam(value = "nid") Long nid, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> collection = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        if (Objects.equals(role, "ROLE_ADMIN")) {
            NoticeDTO noticeDTO = noticeService.getNotice(nid);
            model.addAttribute("nowUser", SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("notice", noticeDTO);
            return "noticeModify";
        } else {
            return "redirect:/board";
        }

    }

    @ResponseBody
    @PutMapping("/notice/modify")
    public String noticeModify(@RequestBody Map<String, Object> map) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> collection = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = collection.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        if (Objects.equals(role, "ROLE_ADMIN")) {
            NoticeDTO noticeDTO = noticeService.getNotice(Long.valueOf((String) map.get("noticeId")));

            noticeDTO.setNoticeTitle((String) map.get("noticeModifyTitle"));
            noticeDTO.setNoticeWriter((String) map.get("noticeWriter"));
            noticeDTO.setNoticeContent((String) map.get("noticeModifyContent"));
            noticeDTO.setModTime(String.valueOf(LocalDate.now()));

            noticeService.modifyNotice(noticeDTO);

            return null;
        } else {
            return "redirect:/board";
        }
    }
}
