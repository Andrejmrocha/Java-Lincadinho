package br.com.lincadinho.API_RESTful_lincadinho.service;

import br.com.lincadinho.API_RESTful_lincadinho.model.Organization;
import br.com.lincadinho.API_RESTful_lincadinho.model.User;
import br.com.lincadinho.API_RESTful_lincadinho.repository.OrganizationRepository;
import br.com.lincadinho.API_RESTful_lincadinho.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    public User getOrCreateByClerkId(Jwt jwt) {
        String idClerk = jwt.getSubject();
        Optional<User> user = userRepository.findByClerkId(idClerk);
        if (user.isPresent()){
            return user.get();
        } else {
            String email = jwt.getClaimAsString("email");
            String name = jwt.getClaimAsString("name");
            if (name == null) {
                String firstName = jwt.getClaimAsString("firstName");
                String lastName = jwt.getClaimAsString("lastName");
                if (firstName != null && lastName != null) {
                    name = firstName + " " + lastName;
                } else if (firstName != null) {
                    name = firstName;
                } else {
                    name = email;
                }
            }
            User newUser = new User(idClerk, name, email);
            return userRepository.save(newUser);
        }
    }

    public User updateUser(Jwt jwt, Long orgId) {
        User user = getOrCreateByClerkId(jwt);
        Organization organization = organizationRepository.findById(orgId)
                    .orElseThrow(() -> new EntityNotFoundException("Organização com id " + orgId + " não encontrada"));
        if (organization.getMembers() != null) {
            organization.getMembers().add(user);
        }
        user.setOrganization(organization);
        userRepository.save(user);
        return user;
    }
}
