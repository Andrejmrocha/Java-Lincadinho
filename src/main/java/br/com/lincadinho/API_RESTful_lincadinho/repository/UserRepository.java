package br.com.lincadinho.API_RESTful_lincadinho.repository;

import br.com.lincadinho.API_RESTful_lincadinho.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByClerkId(String clerkId);
}
