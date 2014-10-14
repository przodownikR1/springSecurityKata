package pl.java.scalatech.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import pl.java.scalatech.annotation.SecurityComponent;

@Slf4j
@SecurityComponent
public class AccessDeniedHandlerNature implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        log.info("handle access denied  path {} , message {}  , cause {}: ", request.getPathInfo(), accessDeniedException.getMessage());
        log.info("++++   {}", request.getContextPath() + "/accessdenied");
        String path = request.getRequestURI();
        // response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        response.sendRedirect(request.getContextPath() + "/accessdenied?error=" + accessDeniedException.getMessage() + "&url=" + path);

    }

}
