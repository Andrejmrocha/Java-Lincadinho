package br.com.lincadinho.API_RESTful_lincadinho.repository;

import br.com.lincadinho.API_RESTful_lincadinho.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
