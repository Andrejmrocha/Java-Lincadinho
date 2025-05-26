package br.com.lincadinho.API_RESTful_lincadinho.DTO.request;

public record CreateOrganization(
        String name,
        Long adminId
) {
}
