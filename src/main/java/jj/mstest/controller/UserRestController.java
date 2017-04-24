package jj.mstest.controller;

import jj.mstest.db.Credentials;
import jj.mstest.db.PersistenceService;
import jj.mstest.restClient.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {
    @Autowired
    public UserRestController(RestClient restClient, PersistenceService persistenceService) {
        this.restClient = restClient;
        this.persistenceService = persistenceService;
    }

    private RestClient restClient;
    private PersistenceService persistenceService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addCredentials(Credentials credentials) {
        persistenceService.saveCredentials(credentials);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
