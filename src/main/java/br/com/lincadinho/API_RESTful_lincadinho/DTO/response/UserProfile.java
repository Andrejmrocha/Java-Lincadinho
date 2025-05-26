package br.com.lincadinho.API_RESTful_lincadinho.DTO.response;

import br.com.lincadinho.API_RESTful_lincadinho.model.User;

public record UserProfile(
        Long LocalUserId,
        String ClerkUserId,
        String email,
        String name,
        Long organizationId
) {
    public UserProfile(User localUser) {
        this(localUser.getId(), localUser.getClerkId(), localUser.getEmail(), localUser.getName(), localUser.getOrganizationIdOrNull());
    }
}
