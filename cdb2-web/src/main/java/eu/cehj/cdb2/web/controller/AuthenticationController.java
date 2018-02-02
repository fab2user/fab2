package eu.cehj.cdb2.web.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @RequestMapping("/user")
    public Principal user(final Principal user) {
        return user;
    }
}
