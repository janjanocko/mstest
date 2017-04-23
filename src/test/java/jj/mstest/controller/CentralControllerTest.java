package jj.mstest.controller;

import com.google.common.collect.Lists;
import jj.mstest.Credentials;
import jj.mstest.db.DbLayer;
import jj.mstest.model.Project;
import jj.mstest.restout.ServiceCall;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CentralControllerTest {
    CentralController subject;
    ServiceCall serviceCall;
    DbLayer dbLayer;

    @Before
    public void setUp() throws Exception {
        serviceCall = mock(ServiceCall.class);
        dbLayer = mock(DbLayer.class);
        subject = new CentralController(serviceCall, dbLayer);
    }

    @Test
    public void testGetProjectsAlreadyLoggedIn() throws Exception {
        when(serviceCall.loggedIn()).thenReturn(true);
        Project project1 = new Project("first", "new", "en", Lists.newArrayList("cz", "sk"));
        Project project2 = new Project("second", "old", "de", Lists.newArrayList("fi", "se"));

        when(serviceCall.getProjects()).thenReturn(Lists.newArrayList(project1, project2));
        List<Project> result = subject.getProjects();
        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));
        verify(serviceCall).getProjects();
    }

    @Test
    public void testGetProjectsANotLoggedIn() throws Exception {
        when(serviceCall.loggedIn()).thenReturn(false);
        when(serviceCall.login(anyString(), anyString())).thenReturn("token");
        Credentials savedCredentials = new Credentials();
        savedCredentials.setUsername("user");
        savedCredentials.setPassword("pass");
        when(dbLayer.getCredentials()).thenReturn(savedCredentials);
        Project project1 = new Project("first", "new", "en", Lists.newArrayList("cz", "sk"));
        Project project2 = new Project("second", "old", "de", Lists.newArrayList("fi", "se"));

        when(serviceCall.getProjects()).thenReturn(Lists.newArrayList(project1, project2));
        List<Project> result = subject.getProjects();
        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));
        verify(serviceCall).login("user", "pass");
        verify(serviceCall).getProjects();
    }

    @Test
    public void testAddCredentials() throws Exception {
        Credentials newCredentials = new Credentials();
        newCredentials.setUsername("user");
        newCredentials.setPassword("pass");
        subject.addCredentials(newCredentials);
        verify(dbLayer).saveCredentials(newCredentials);
    }
}