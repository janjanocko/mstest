package jj.mstest.controller;

import jj.mstest.Credentials;
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
        CentralController centralController = mock(CentralController.class);
        UserRestController subject = new UserRestController(centralController);
        Credentials newCredentials = new Credentials();
        newCredentials.setUsername("user");
        newCredentials.setPassword("pass");
        when(centralController.addCredentials(any(Credentials.class))).thenReturn(1L);
        ResponseEntity result = subject.addCredentials(newCredentials);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        verify(centralController).addCredentials(newCredentials);
    }
}