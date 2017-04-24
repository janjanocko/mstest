package jj.mstest.controller;

import jj.mstest.db.Credentials;
import jj.mstest.db.PersistenceService;
import jj.mstest.restClient.RestClient;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRestControllerTest {
    @Test
    public void testAddCredentials() throws Exception {
        RestClient restClient = mock(RestClient.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        Credentials newCredentials = new Credentials();
        newCredentials.setUsername("user");
        newCredentials.setPassword("pass");
        when(persistenceService.saveCredentials(any(Credentials.class))).thenReturn(newCredentials);

        UserRestController subject = new UserRestController(restClient, persistenceService);
        ResponseEntity result = subject.addCredentials(newCredentials);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        verify(persistenceService).saveCredentials(newCredentials);
    }
}