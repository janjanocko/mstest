package jj.mstest.controller;

import jj.mstest.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {
    @Autowired
    public UserRestController(CentralController centralController) {
        this.centralController = centralController;
    }

    private CentralController centralController;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> addCredentials(Credentials credentials) {
        Long credentialsId = centralController.addCredentials(credentials);

        return ResponseEntity.status(credentialsId != null ? 201 : 401).build();
    }


}
