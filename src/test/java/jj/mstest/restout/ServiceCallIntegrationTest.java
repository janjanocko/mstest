package jj.mstest.restout;

import jj.mstest.model.Project;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test that calls real interface.
 * Ignored because I don't want real API be called too often
 *
 */

@Ignore
public class ServiceCallIntegrationTest {
    //    @Autowired
    private ServiceCall subject;

    @Before
    public void setup() {
        subject = new ServiceCall(new RestTemplate());
    }

    @Test
    public void shouldLoginToRealServer() {
        String token = subject.login("jan.janocko", "msheslo!");
        assertNotNull(token);
    }

    @Test
    public void shouldGetRealProjects() {
        //replace with real token when running
        String token = "8SFNNjrSnRcNL1kPV3PoZnciR07ZUlr6eJCeOhuGSlrDJvsRzsGpg8p9Fzpgj3O70";
        List<Project> projects = subject.getProjects();
        assertEquals(2, projects.size());
    }
}
