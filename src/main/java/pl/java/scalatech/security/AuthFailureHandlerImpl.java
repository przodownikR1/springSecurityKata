package pl.java.scalatech.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import pl.java.scalatech.annotation.SecurityComponent;

@SecurityComponent
@Slf4j
public class AuthFailureHandlerImpl extends ExceptionMappingAuthenticationFailureHandler {

	private Map<String, String> failureUrlMap = new HashMap<>();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException,
			ServletException {
		String url = failureUrlMap.get(exception.getClass().getName());
		String username = null;
		if (exception.getAuthentication() != null) {
			username = exception.getAuthentication().getName();
		} else {
            log.info("login failure username :" + username);
		}
		if (url != null) {
			getRedirectStrategy().sendRedirect(request, response, url);
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}

	}

	@Override
    public void setExceptionMappings(Map<?, ?> failureUrlMap) {
		this.failureUrlMap.clear();
		for (Map.Entry<?, ?> entry : failureUrlMap.entrySet()) {
			Object exception = entry.getKey();
			Object url = entry.getValue();
			Assert.isInstanceOf(String.class, exception, "Exception key must be a String (the exception classname)."); //$NON-NLS-1$
			Assert.isInstanceOf(String.class, url, "URL must be a String"); //$NON-NLS-1$
			Assert.isTrue(UrlUtils.isValidRedirectUrl((String) url), "Not a valid redirect URL: " + url); //$NON-NLS-1$
			this.failureUrlMap.put((String) exception, (String) url);
		}
	}

}
