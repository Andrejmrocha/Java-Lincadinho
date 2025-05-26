package br.com.lincadinho.API_RESTful_lincadinho.controller;

import br.com.lincadinho.API_RESTful_lincadinho.DTO.request.UpdateUserOranization;
import br.com.lincadinho.API_RESTful_lincadinho.DTO.response.UserProfile;
import br.com.lincadinho.API_RESTful_lincadinho.model.Organization;
import br.com.lincadinho.API_RESTful_lincadinho.model.User;
import br.com.lincadinho.API_RESTful_lincadinho.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserProfile> getUserProfile(@AuthenticationPrincipal Jwt jwt) {
        User localUser = userService.getOrCreateByClerkId(jwt);
        return ResponseEntity.ok(new UserProfile(localUser));
    }

    @PutMapping
    public ResponseEntity<User> updateUserOrganization(@AuthenticationPrincipal Jwt jwt, @RequestBody UpdateUserOranization data) {
        User user = userService.updateUser(jwt, data.orgId());
        return ResponseEntity.ok(user);
    }


}
