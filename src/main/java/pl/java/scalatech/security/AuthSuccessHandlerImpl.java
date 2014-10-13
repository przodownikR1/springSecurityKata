package pl.java.scalatech.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import pl.java.scalatech.annotation.SecurityComponent;
import pl.java.scalatech.service.user.UserService;

@Slf4j
@SecurityComponent
public class AuthSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {

    @SuppressWarnings("unused")
    private RequestCache requestCache = new HttpSessionRequestCache();
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {

        super.onAuthenticationSuccess(request, response, authentication);
        // TODO moze byc kilka rodzajow userow
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        log.info("++++       authSuccessHandler           {}", userDetails.getUsername());

        if (userDetails != null && userDetails.getUsername() != null) {

            log.info("!!!!!!!!!!! TODO");
        }

        return;

    }

}