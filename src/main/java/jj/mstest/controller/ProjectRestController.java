package jj.mstest.controller;

import jj.mstest.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/project")
public class ProjectRestController {
    @Autowired
    public ProjectRestController(CentralController centralController){
        this.centralController = centralController;
    }

    private CentralController centralController;


    @RequestMapping(method= RequestMethod.GET)
    public List<Project> getAllProjects() {
        return centralController.getProjects();
    }
}
