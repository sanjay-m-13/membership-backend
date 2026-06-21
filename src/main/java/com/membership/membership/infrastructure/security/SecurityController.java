package com.membership.membership.infrastructure.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(HttpServletRequest httpServletRequest){
        return new ResponseEntity<>("Still breathing, despite the codebase. \uD83D\uDE01 " + httpServletRequest.getSession().getId(), HttpStatus.OK);
    }
}
