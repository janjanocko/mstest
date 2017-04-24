package jj.mstest.controller;

import jj.mstest.controller.dto.UsernamePassword;
import jj.mstest.db.Credentials;
import jj.mstest.db.PersistenceService;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UserRestControllerTest {
    @Test
    public void testAddCredentials() throws Exception {
        PersistenceService persistenceService = mock(PersistenceService.class);

        Credentials newCredentials = new Credentials();
        newCredentials.setUsername("user");
        newCredentials.setPassword("pass");

        UsernamePassword usernamePassword = new UsernamePassword();
        usernamePassword.setUsername("user");
        usernamePassword.setPassword("pass");
        when(persistenceService.saveCredentials(any(Credentials.class))).thenReturn(newCredentials);

        UserRestController subject = new UserRestController(persistenceService);

        ResponseEntity result = subject.addCredentials(usernamePassword);

        assertTrue(result.getStatusCode().is2xxSuccessful());
        verify(persistenceService).saveCredentials(newCredentials);
    }
}