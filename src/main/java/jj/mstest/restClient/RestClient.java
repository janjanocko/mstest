package jj.mstest.restClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
//@Scope(value = "session")
public class RestClient {
    @Autowired
    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private RestTemplate restTemplate;
    //in real application all URLs and paths would be in app properties
    private static final String serverUrl = "https://cloud.memsource.com/web";

    private String token;

    public boolean loggedIn() {
        return token != null;
    }

    public ResponseEntity<LoginResponse> login(String username, String password) {
        //strange, but it only works with parameters in the URL
        //for login it also works with GET, but since it changes state POST is better
        String url = serverUrl + "/api/v3/auth/login?userName={userName}&password={password}";
        Map<String, String> params = new HashMap<>();
        params.put("userName", username);
        params.put("password", password);
        try {
            ResponseEntity<LoginResponse> responseEntity = restTemplate.postForEntity(url, null, LoginResponse.class, params);
            token = responseEntity.getBody().getToken();
            return responseEntity;
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getStatusCode());
        }
    }

    public ResponseEntity<Project[]> getProjects() {
        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String url = serverUrl + "/api/v3/project/list?token=" + token;
        try {
            return restTemplate.getForEntity(url, Project[].class);
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            return new ResponseEntity<>(status);
        }
    }
}
