package jj.mstest.controller;

import jj.mstest.controller.dto.UsernamePassword;
import jj.mstest.db.Credentials;
import jj.mstest.db.PersistenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {
    @Autowired
    public UserRestController(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    private PersistenceService persistenceService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addCredentials(@RequestBody UsernamePassword usernamePassword) {
        Credentials credentials = new Credentials();
        credentials.setUsername(usernamePassword.getUsername());
        credentials.setPassword(usernamePassword.getPassword());
        persistenceService.saveCredentials(credentials);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
