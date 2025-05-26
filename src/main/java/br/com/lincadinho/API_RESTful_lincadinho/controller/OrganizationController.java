package br.com.lincadinho.API_RESTful_lincadinho.controller;

import br.com.lincadinho.API_RESTful_lincadinho.DTO.request.CreateOrganization;
import br.com.lincadinho.API_RESTful_lincadinho.DTO.response.CreatedOrganization;
import br.com.lincadinho.API_RESTful_lincadinho.model.Organization;
import br.com.lincadinho.API_RESTful_lincadinho.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<CreatedOrganization> create(@RequestBody CreateOrganization data,
                                                      UriComponentsBuilder uriComponentsBuilder){
        Organization createdOrganization = organizationService.create(data);
        return ResponseEntity.ok(new CreatedOrganization(createdOrganization));
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<CreatedOrganization>>> list(@PageableDefault(sort = {"name"}) Pageable pageable, PagedResourcesAssembler<CreatedOrganization> assembler) {
        var page = organizationService.listAll(pageable).map(CreatedOrganization::new);
        PagedModel<EntityModel<CreatedOrganization>> pagedModel = assembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }
}
