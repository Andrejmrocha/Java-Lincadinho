package br.com.lincadinho.API_RESTful_lincadinho.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String clerkId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public User(String clerkId, String name, String email) {
        this.clerkId = clerkId;
        this.name = name;
        this.email = email;
        this.role = UserRole.ADMIN;
    }

    public User(String clerkId, String email) {
        this.clerkId = clerkId;
        this.email = email;
    }

    public Long getOrganizationIdOrNull() {
        return (this.organization != null) ? this.organization.getId() : null;
    }
}
