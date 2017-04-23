package jj.mstest.controller;

import jj.mstest.Credentials;
import jj.mstest.db.DbLayer;
import jj.mstest.model.Project;
import jj.mstest.restout.ServiceCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentralController {
    @Autowired
    public CentralController(ServiceCall serviceCall, DbLayer dbLayer) {
        this.serviceCall = serviceCall;
        this.dbLayer = dbLayer;
    }

    private ServiceCall serviceCall;
    private DbLayer dbLayer;

    public List<Project> getProjects() {
        if(!serviceCall.loggedIn()) {
            Credentials credentials = dbLayer.getCredentials();
            String token = serviceCall.login(credentials.getUsername(), credentials.getPassword());
            if(token == null) {
                throw new RuntimeException("Not able to login using stored credentials");
            }
        }
        return serviceCall.getProjects();
    }


    public Long addCredentials(Credentials credentials) {
        credentials = dbLayer.saveCredentials(credentials);
        return credentials.getId();
    }


}
