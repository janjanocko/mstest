package jj.mstest.restout;

import jj.mstest.model.LoginResponse;
import jj.mstest.model.Project;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class ServiceCallTest {
    private ServiceCall subject;
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        restTemplate = Mockito.mock(RestTemplate.class);
        subject = new ServiceCall(restTemplate);
    }

    @Test
    public void testLoginSuccessful() throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("secretToken");
        ResponseEntity<LoginResponse> positiveResponse = new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(anyString(), isNull(), eq(LoginResponse.class))).thenReturn(positiveResponse);
        assertFalse(subject.loggedIn());
        String result = subject.login("user", "pwd");
        assertTrue(subject.loggedIn());
        assertEquals("secretToken", result);
    }

    @Test
    public void testGetProjects() throws Exception {
        Project project1 = new Project();
        project1.setName("1");
        Project project2 = new Project();
        project2.setName("2");
        when(restTemplate.getForObject(anyString(), eq(Project[].class))).thenReturn(new Project[]{project1, project2});
        List<Project> projects = subject.getProjects();
        assertEquals(2, projects.size());
    }
}