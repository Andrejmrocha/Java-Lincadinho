package br.com.lincadinho.API_RESTful_lincadinho.service;

import br.com.lincadinho.API_RESTful_lincadinho.DTO.request.CreateOrganization;
import br.com.lincadinho.API_RESTful_lincadinho.model.Organization;
import br.com.lincadinho.API_RESTful_lincadinho.model.User;
import br.com.lincadinho.API_RESTful_lincadinho.repository.OrganizationRepository;
import br.com.lincadinho.API_RESTful_lincadinho.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    public Organization create(CreateOrganization data){
        User admin = userRepository.findById(data.adminId())
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        Organization newOrganization = new Organization(data.name(), admin);
        admin.setOrganization(newOrganization);
        newOrganization.getMembers().add(admin);

        return organizationRepository.save(newOrganization);
    }

    public Page<Organization> listAll(Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }
}
