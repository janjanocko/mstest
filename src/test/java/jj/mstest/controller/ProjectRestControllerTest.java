package jj.mstest.controller;

import com.google.common.collect.Lists;
import jj.mstest.db.Credentials;
import jj.mstest.db.PersistenceService;
import jj.mstest.restClient.RestClient;
import jj.mstest.restClient.dto.Project;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
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