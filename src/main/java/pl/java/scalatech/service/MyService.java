package pl.java.scalatech.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    public static String RESULT = "hello przodownik";

    @Secured(value = { "USER", "ADMIN" })
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

    @PreAuthorize("hasRole('USER')")
    public String other() {
        return RESULT;
    }
}