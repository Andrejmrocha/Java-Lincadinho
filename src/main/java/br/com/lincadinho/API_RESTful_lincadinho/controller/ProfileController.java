package br.com.lincadinho.API_RESTful_lincadinho.controller;

import br.com.lincadinho.API_RESTful_lincadinho.model.User;
import br.com.lincadinho.API_RESTful_lincadinho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal Jwt jwt) {
        User localUser = userService.getOrCreateByClerkId(jwt);
        return ResponseEntity.ok(Map.of(
                "LocalUserId", localUser.getId(),
                "ClerkUserId", localUser.getClerkId(),
                "email", localUser.getEmail(),
                "name", localUser.getName()
        ));
    }

}
