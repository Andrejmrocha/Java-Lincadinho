package br.com.lincadinho.API_RESTful_lincadinho.service;

import br.com.lincadinho.API_RESTful_lincadinho.DTO.request.CreateOrganization;
import br.com.lincadinho.API_RESTful_lincadinho.model.Organization;
import br.com.lincadinho.API_RESTful_lincadinho.model.User;
import br.com.lincadinho.API_RESTful_lincadinho.repository.OrganizationRepository;
import br.com.lincadinho.API_RESTful_lincadinho.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private OrganizationService organizationService;

    private CreateOrganization dto;
    private User admin;
    private Organization createdOrganization;

    @BeforeEach
    void setUp(){
        dto = new CreateOrganization("OrgOne",1L);

        admin = new User();
        admin.setId(1L);
        admin.setName("Andre");

        createdOrganization = new Organization("OrgOne",admin);
        createdOrganization.setAdmin(admin);
        createdOrganization.getMembers().add(admin);

    }

    @Test
    @DisplayName("Should create a new organization.")
    void shouldCreateNewOrganization(){
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(admin));
        when(organizationRepositoryMock.save(any(Organization.class))).thenReturn(createdOrganization);

        Organization result = organizationService.create(dto);
        assertNotNull(result);
        assertEquals(createdOrganization.getId(), result.getId());
        assertEquals("OrgOne", result.getName());
        assertEquals(admin, result.getAdmin());
        assertEquals(result, admin.getOrganization());

        verify(userRepositoryMock, times(1)).findById(1L);
        verify(organizationRepositoryMock, times(1)).save(any(Organization.class));
    }

    @Test
    @DisplayName("Should throw exception when admin not found")
    void shouThrowExceptionWhenAdminNotFound(){
        Long adminId = 2L;
        dto = new CreateOrganization("OrgTwo",2L);
        when(userRepositoryMock.findById(adminId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            organizationService.create(dto);
        });

        assertEquals("Admin user not found", exception.getMessage());
        verify(organizationRepositoryMock, never()).save(any(Organization.class));

    }
}
