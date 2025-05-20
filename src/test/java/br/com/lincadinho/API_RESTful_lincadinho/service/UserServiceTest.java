package br.com.lincadinho.API_RESTful_lincadinho.service;

import br.com.lincadinho.API_RESTful_lincadinho.model.User;
import br.com.lincadinho.API_RESTful_lincadinho.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private Jwt jwtMock;

    @InjectMocks
    private UserService userService;

    private String clerkId;
    private String email;
    private String name;
    private User existingUser;

    @BeforeEach
    void setUp() {
        clerkId = "user_clerk_123";
        name = "User test";
        email = "teste@example.com";
        existingUser = new User(clerkId, name, email);
        existingUser.setId(1L);
        when(jwtMock.getSubject()).thenReturn(clerkId);
        when(jwtMock.getClaimAsString("name")).thenReturn(name);
        when(jwtMock.getClaimAsString("email")).thenReturn(email);
    }

    @Test
    void findOrCreateUsuario_whenUserAlreadyExists_shouldReturnExistingUser() {
        when(userRepositoryMock.findByClerkId(clerkId)).thenReturn(Optional.of(existingUser));
        User result = userService.getOrCreateByClerkId(jwtMock);
        assertNotNull(result);
        assertEquals(clerkId, result.getClerkId());
        assertEquals(existingUser.getEmail(), result.getEmail());
        assertSame(existingUser, result,"The returned user should be the same existing object");
        verify(userRepositoryMock, never()).save(any(User.class));
    }

    @Test
    void findOrCreateUsuario_whenUserNotExists_shouldCreateNewUser() {
        when(userRepositoryMock.findByClerkId(clerkId)).thenReturn(Optional.empty());
        when(userRepositoryMock.save(any(User.class))).thenAnswer(invocation -> {
            User saveUser = invocation.getArgument(0);
            return saveUser;
        });

        User result = userService.getOrCreateByClerkId(jwtMock);
        assertNotNull(result);
        assertEquals(clerkId, result.getClerkId());
        assertEquals(email, result.getEmail());
        assertEquals("User test", result.getName());
        verify(userRepositoryMock, times(1)).save(any(User.class));

    }
}
