package jj.mstest.db;

import jj.mstest.Credentials;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DbLayerTest {
    private DbLayer subject;
    CredentialsRepository credentialsRepository;

    @Before
    public void setUp() throws Exception {
        credentialsRepository = mock(CredentialsRepository.class);
        subject = new DbLayer(credentialsRepository);
    }

    @Test
    public void testSaveCredentials() throws Exception {
        Credentials newCredentials = new Credentials();
        newCredentials.setUsername("user");
        newCredentials.setPassword("pass");
        subject.saveCredentials(newCredentials);
        verify(credentialsRepository).save(newCredentials);
        assertEquals(new Long(1L), newCredentials.getId());
    }

    @Test
    public void testGetCredentials() throws Exception {
        Credentials newCredentials = new Credentials();
        newCredentials.setUsername("user");
        newCredentials.setPassword("pass");
        newCredentials.setId(1L);
        when(credentialsRepository.findOne(1L)).thenReturn(newCredentials);
        Credentials result = subject.getCredentials();
        assertEquals(newCredentials, result);
    }
}