package pl.java.scalatech.rest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ErrorContoller {
  
     public ErrorContoller() {
        log.info("----------------------------------------------------------------error Controller");
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessException(AccessDeniedException ex) {
        log.info("!!!!         {}",ex);
        return "accessdenied";

    }

    @ExceptionHandler(DataAccessException.class)
    public String handleDatabaseException(DataAccessException ex) {
        log.info("!!!!         {}",ex);
        return "error";
    }

}
