package jj.mstest.controller;

import com.google.common.collect.Lists;
import jj.mstest.model.Project;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectRestControllerTest {
    @Test
    public void testGetAllProjects() throws Exception {
        CentralController centralController = mock(CentralController.class);
        ProjectRestController subject = new ProjectRestController(centralController);
        Project project1 = new Project("first", "new", "en", newArrayList("cz", "sk"));
        Project project2 = new Project("second", "old", "de", newArrayList("fi", "se"));
        List<Project> projects = newArrayList(project1, project2);
        when(centralController.getProjects()).thenReturn(projects);
        List<Project> result = subject.getAllProjects();
        assertEquals(2, result.size());
        assertTrue(result.contains(project1));
        assertTrue(result.contains(project2));
        verify(centralController).getProjects();
    }
}