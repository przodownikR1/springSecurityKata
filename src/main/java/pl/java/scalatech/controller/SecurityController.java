package pl.java.scalatech.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.java.scalatech.annotation.CurrentUser;
import pl.java.scalatech.domain.User;

@Controller
public class SecurityController {
    // Error page
    @RequestMapping("/error.html")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = null;
        if (throwable != null) {
            errorMessage = throwable.getMessage();
        }
        model.addAttribute("errorMessage", errorMessage.toString());
        return "error.html";
    }

    @RequestMapping("/currentUser")
    public ResponseEntity<User> findMessagesForUser(@CurrentUser User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
