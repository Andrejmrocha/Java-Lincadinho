package br.com.lincadinho.API_RESTful_lincadinho.DTO.response;

import br.com.lincadinho.API_RESTful_lincadinho.model.Organization;

public record CreatedOrganization(Long id, String name) {
    public CreatedOrganization(Organization organization){
        this(organization.getId(), organization.getName());
    }
}
