package jj.mstest.controller;

import jj.mstest.db.Credentials;
import jj.mstest.db.PersistenceService;
import jj.mstest.restClient.LoginResponse;
import jj.mstest.restClient.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/project")
public class ProjectRestController {
    @Autowired
    public ProjectRestController(RestClient restClient, PersistenceService persistenceService) {
        this.restClient = restClient;
        this.persistenceService = persistenceService;
    }

    private RestClient restClient;
    private PersistenceService persistenceService;

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<?> getAllProjects() {
        if(!restClient.loggedIn()) {
            Credentials credentials = persistenceService.getCredentials();
            ResponseEntity<LoginResponse> responseEntity = restClient.login(credentials.getUsername(), credentials.getPassword());
            if(!responseEntity.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        return restClient.getProjects();


    }
}
