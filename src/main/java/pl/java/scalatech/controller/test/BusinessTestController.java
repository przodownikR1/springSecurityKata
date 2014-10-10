package pl.java.scalatech.controller.test;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = BusinessTestController.API, produces = { MediaType.APPLICATION_JSON_VALUE })
@Slf4j
public class BusinessTestController extends CommonTestController{
    protected static final String API = "/api/business/test";
    
 
}
