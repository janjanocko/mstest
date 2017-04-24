package jj.mstest.controller;

import com.google.common.collect.Lists;
import jj.mstest.db.Credentials;
import jj.mstest.db.PersistenceService;
import jj.mstest.restClient.Project;
import jj.mstest.restClient.RestClient;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectRestControllerTest {
    @Test
    public void testGetAllProjects() throws Exception {

        RestClient restClient = mock(RestClient.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        when(restClient.loggedIn()).thenReturn(true);
        Project project1 = new Project("first", "new", "en", Lists.newArrayList("cz", "sk"));
        Project project2 = new Project("second", "old", "de", Lists.newArrayList("fi", "se"));

        when(restClient.getProjects()).thenReturn(new ResponseEntity<>(new Project[]{project1, project2}, HttpStatus.OK));

        ProjectRestController subject = new ProjectRestController(restClient, persistenceService);

        ResponseEntity<?> result = subject.getAllProjects();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        Project[] projects = (Project[]) result.getBody();
        assertEquals(2, projects.length);
        assertTrue(projects[0].equals(project1));
        assertTrue(projects[1].equals(project2));
    }

    @Test
    public void testGetProjectsANotLoggedIn() throws Exception {
        RestClient restClient = mock(RestClient.class);
        PersistenceService persistenceService = mock(PersistenceService.class);

        when(restClient.loggedIn()).thenReturn(false);
        when(restClient.login(anyString(), anyString())).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        Credentials savedCredentials = new Credentials();
        savedCredentials.setUsername("user");
        savedCredentials.setPassword("pass");
        when(persistenceService.getCredentials()).thenReturn(savedCredentials);
        Project project1 = new Project("first", "new", "en", Lists.newArrayList("cz", "sk"));
        Project project2 = new Project("second", "old", "de", Lists.newArrayList("fi", "se"));
        when(restClient.getProjects()).thenReturn(new ResponseEntity<>(new Project[]{project1, project2}, HttpStatus.OK));

        ProjectRestController subject = new ProjectRestController(restClient, persistenceService);

        ResponseEntity<?> result = subject.getAllProjects();
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}