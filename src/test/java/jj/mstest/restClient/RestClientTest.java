package jj.mstest.restClient;

import jj.mstest.restClient.dto.LoginResponse;
import jj.mstest.restClient.dto.Project;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

public class RestClientTest {
    private RestClient subject;
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        restTemplate = Mockito.mock(RestTemplate.class);
        subject = new RestClient(restTemplate);
    }

    @Test
    public void testLogin() throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("secretToken");
        ResponseEntity<LoginResponse> positiveResponse = new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);

        assertFalse(subject.loggedIn());

        when(restTemplate.postForEntity(anyString(), isNull(), eq(LoginResponse.class), anyMapOf(String.class, String.class))).thenReturn(positiveResponse);
        ResponseEntity<LoginResponse> result = subject.login("user", "pwd");

        assertTrue(subject.loggedIn());
        assertEquals("secretToken", result.getBody().getToken());
    }

    @Test
    public void testGetProjects() throws Exception {
        //prepare the state to be able to call getProjects
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("secretToken");
        ResponseEntity<LoginResponse> positiveResponse = new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
        when(restTemplate.postForEntity(anyString(), isNull(), eq(LoginResponse.class), anyMapOf(String.class, String.class))).thenReturn(positiveResponse);
        subject.login("user", "pwd");
        //subject prepared

        Project project1 = new Project();
        project1.setName("1");
        Project project2 = new Project();
        project2.setName("2");
        ResponseEntity<Project[]> positiveGetResponse = new ResponseEntity<>(new Project[]{project1, project2}, HttpStatus.ACCEPTED);

        when(restTemplate.getForEntity(anyString(), eq(Project[].class))).thenReturn(positiveGetResponse);
        ResponseEntity<Project[]> responseEntity = subject.getProjects();
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().length);
    }
}