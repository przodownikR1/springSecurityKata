package pl.java.scalatech.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    public static String RESULT = "hello przodownik";

    @Secured("ROLE_USER" )
    public String secure() {
        return RESULT;
    }

    @PreAuthorize("true")
    public String authorized() {
        return RESULT;
    }

    @PreAuthorize("false")
    public String denied() {
        return RESULT;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String other() {
        return RESULT;
    }
}