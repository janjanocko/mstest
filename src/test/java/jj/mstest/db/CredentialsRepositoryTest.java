package jj.mstest.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class CredentialsRepositoryTest {
    @Autowired
    CredentialsRepository subject;


    @Test
    public void testSaveAndFindOne() {
        Credentials c = new Credentials();
        c.setId(1L);
        c.setUsername("un");
        c.setPassword("pass");
        subject.save(c);

        Credentials credentials = subject.findOne(1L);
        assertNotNull(credentials);
        assertEquals(c.getUsername(), credentials.getUsername());
        assertEquals(c.getUsername(), credentials.getUsername());
    }

}