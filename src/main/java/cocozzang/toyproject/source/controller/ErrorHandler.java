package cocozzang.toyproject.source.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class ErrorHandler implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest, Model model) {
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null) {
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorCode", "error_403");
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "error_404");
            }
        } else {
            model.addAttribute("errorCode", "Not Completed Error Page");
        }

        return "errorPage";
    }

}
