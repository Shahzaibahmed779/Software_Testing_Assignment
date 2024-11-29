import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginAppTest {
    private LoginApp loginApp;

    @BeforeEach
    void setup() {
        loginApp = new LoginApp();
    }


    @Test
    void testAuthenticateUserWhenNameIsNullInDatabase() {
        String emailWithNullName = "nullname@example.com";

        String result = loginApp.authenticateUser(emailWithNullName);

        assertNull(result, "The result should be null when the associated name is null in the database");
    }


    @Test
    void testAuthenticateUserWithValidEmail() {
        String validEmail = "mikejohnson@example.com";
        String expectedName = "Mike Johnson";

        String result = loginApp.authenticateUser(validEmail);

        // Multiple assertions
        assertAll(
                () -> assertNotNull(result, "The result should not be null for a valid email"),
                () -> assertEquals(expectedName, result, "The username should match the expected name"),
                () -> assertTrue(result.length() > 0, "The username should not be an empty string")
        );
    }

    @Test
    void testAuthenticateUserWithInvalidEmail() {
        String invalidEmail = "notfound@example.com";

        String result = loginApp.authenticateUser(invalidEmail);

        assertAll(
                () -> assertNull(result, "The result should be null for an invalid email"),
                () -> assertTrue(result == null || result.isEmpty(), "The result should be null or an empty string")
        );
    }



    @Test
    void testAuthenticateUserWithEmptyAndNullEmail() {
        // Empty email test
        String emptyEmail = "";

        String emptyResult = loginApp.authenticateUser(emptyEmail);
        assertNull(emptyResult, "The result should be null for an empty email");

        // Null email test
        String nullResult = loginApp.authenticateUser(null);
        assertNull(nullResult, "The result should be null for a null email");
    }

    @Test
    void testAuthenticateUserWithSQLInjectionAttempt() {
        String maliciousInput = "' OR '1'='1";

        String result = loginApp.authenticateUser(maliciousInput);

        assertAll(
                () -> assertNull(result, "The result should be null for an SQL injection attempt"),
                () -> assertTrue(result == null || result.isEmpty(), "The result should be null or an empty string")
        );
    }



}