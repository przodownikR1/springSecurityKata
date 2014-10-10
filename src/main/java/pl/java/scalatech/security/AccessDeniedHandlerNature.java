package pl.java.scalatech.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
public class AccessDeniedHandlerNature implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException,
            ServletException {
        log.info("handle access denied  path {} , message {}: ", request.getPathInfo(), accessDeniedException.getMessage());
        String path = request.getRequestURI();
        request.setAttribute("errorDetails", accessDeniedException.getMessage());
        request.setAttribute("path", path);
        request.setAttribute("user", request.getUserPrincipal().getName());
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());

    }

}
