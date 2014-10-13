package pl.java.scalatech.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import pl.java.scalatech.annotation.SecurityComponent;
import pl.java.scalatech.service.user.UserService;

@Slf4j
@SecurityComponent
public class LogoutHandler implements LogoutSuccessHandler {
    
    @Autowired
    private UserService userService;
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String url = request.getContextPath() + "/";
		log.info("logout user  : {}        " ,authentication.getPrincipal());
		
		response.sendRedirect(url);
	}

}