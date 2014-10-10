package pl.java.scalatech.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public class AuthFailureHandlerImpl extends ExceptionMappingAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger logger = LoggerFactory.getLogger(AuthFailureHandlerImpl.class);

	private Map<String, String> failureUrlMap = new HashMap<>();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException,
			ServletException {
		String url = failureUrlMap.get(exception.getClass().getName());
		String username = null;
		if (exception.getAuthentication() != null) {
			username = exception.getAuthentication().getName();
		} else {
			logger.info("login failure username :" + username);
		}
		if (url != null) {
			getRedirectStrategy().sendRedirect(request, response, url);
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}

	}

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
