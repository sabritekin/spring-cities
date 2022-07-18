package com.dreamix.springcities.user;

import com.dreamix.springcities.user.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "dev")
@RunWith(SpringRunner.class)
public class UserTests {
    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_PASSWORD = "Test Password";
    private static final String TEST_ROLE = "Test Role";
    private User testUser;

    @Before
    public void createTestUser() {
        testUser = new User(TEST_USER_NAME, TEST_PASSWORD, TEST_ROLE);
    }

    @Test
    public void givenUser_whenUserConstructor_thenObjectCreated() {
        assertNotNull(testUser);
    }

    @Test
    public void givenUser_whenUserNameGetter_thenGetUserName() {
        assertEquals(TEST_USER_NAME, testUser.getUserName());
    }

    @Test
    public void givenUser_whenPasswordGetter_thenGetPassword() {
        assertEquals(TEST_PASSWORD, testUser.getPassword());
    }

    @Test
    public void givenUser_whenRoleGetter_thenGetRole() {
        assertEquals(TEST_ROLE, testUser.getRole());
    }

    @Test
    public void givenUser_whenUserNameSetter_thenSetUserName() {
        String newTestUserName = "New Test User";
        testUser.setUserName(newTestUserName);
        assertEquals(newTestUserName, testUser.getUserName());
    }

    @Test
    public void givenUser_whenPasswordSetter_thenSetPassword() {
        String newTestPassword = "New Test Password";
        testUser.setPassword(newTestPassword);
        assertEquals(newTestPassword, testUser.getPassword());
    }

    @Test
    public void givenUser_whenRoleSetter_thenSetRole() {
        String newTestRole = "New Test Role";
        testUser.setRole(newTestRole);
        assertEquals(newTestRole, testUser.getRole());
    }

}
