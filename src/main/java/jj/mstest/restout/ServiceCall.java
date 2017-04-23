package jj.mstest.restout;

import jj.mstest.model.LoginResponse;
import jj.mstest.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
//@Scope(value = "session")
public class ServiceCall {
    @Autowired
    public ServiceCall(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private RestTemplate restTemplate;

    private static final String serverUrl = "https://cloud.memsource.com/web";
    private String token;


    public boolean loggedIn() {
        return token != null;
    }


    public String login(String username, String password) {
        //  jan.janocko/msheslo!
        //very strange, but it only works with parameters in the URL
        //for login it also works with GET, but since it changes state POST is better
        String url = serverUrl + "/api/v3/auth/login?userName=" + username + "&password=" + password;
        ResponseEntity<LoginResponse> loginResponseEntity = restTemplate.postForEntity(url, null, LoginResponse.class);
        if (loginResponseEntity.getStatusCode().is2xxSuccessful()) {
            this.token = loginResponseEntity.getBody().getToken();
            return this.token;
        } else return null;
    }

    public List<Project> getProjects() {
        if (token == null) {
            return new ArrayList<>();
            //TODO throw error
        }
        String url = serverUrl + "/api/v3/project/list?token=" + token;
        //String token = "8SFNNjrSnRcNL1kPV3PoZnciR07ZUlr6eJCeOhuGSlrDJvsRzsGpg8p9Fzpgj3O70";
        Project[] projects = restTemplate.getForObject(url, Project[].class);
        ResponseEntity<Project[]> projectsResponseEntity = restTemplate.getForEntity(url, Project[].class);
        if (projectsResponseEntity.getStatusCode().is4xxClientError() && projectsResponseEntity.getStatusCode().is5xxServerError()) {
            this.token = null;
            //TODO should throw error instead?
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(projects));
    }
}
