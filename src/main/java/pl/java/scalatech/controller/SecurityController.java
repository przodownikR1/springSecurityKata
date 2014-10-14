package pl.java.scalatech.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.java.scalatech.annotation.CurrentUser;
import pl.java.scalatech.domain.User;

@Controller
@Slf4j
public class SecurityController {

    @RequestMapping("/accessdenied")
    public String accessDenied(@RequestParam("error") String error, @RequestParam("url") String url, HttpServletRequest request, Model model,
            @CurrentUser User user) {
        model.addAttribute("errorMessage", error);
        model.addAttribute("user", user);
        model.addAttribute("path", request.getContextPath()+url);

        return "accessdenied";
    }

    // Error page
    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = null;
        if (throwable != null) {
            errorMessage = throwable.getMessage();
        }
        model.addAttribute("errorMessage", errorMessage.toString());
        return "error";
    }

    @RequestMapping("/currentUser")
    public ResponseEntity<User> currentUser(@CurrentUser User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @RequestMapping("/principal")
    public ResponseEntity<String> principal(Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("++++    {}", auth.getAuthorities());

        return new ResponseEntity<>(principal.getName(), HttpStatus.OK);

    }
}
