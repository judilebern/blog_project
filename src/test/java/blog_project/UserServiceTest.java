package blog_project;

import blog_project.entities.Role;
import blog_project.entities.User;
import blog_project.repo.UserRepository;
import blog_project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        User loadedUser = (User) userService.loadUserByUsername(username);

        assertNotNull(loadedUser);
        assertEquals(username, loadedUser.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }

    @Test
    void testAddUser_UserDoesNotExist() {
        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setPassword("password"); // Insecure, for testing purposes only

        when(userRepository.findByUsername(newUser.getUsername())).thenReturn(null);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");

        boolean result = userService.addUser(newUser);

        assertTrue(result);
        assertTrue(newUser.isActive());
        assertEquals(Collections.singleton(Role.USER), newUser.getRoles());
        assertEquals("encodedPassword", newUser.getPassword()); // Ensure the password was encoded
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testAddUser_UserExists() {
        User existingUser = new User();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(existingUser);

        boolean result = userService.addUser(existingUser);

        assertFalse(result);
        verify(userRepository, times(0)).save(existingUser); // Verify that save is not called for an existing user
    }

}
