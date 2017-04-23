package jj.mstest.db;

import jj.mstest.Credentials;
import org.springframework.data.repository.CrudRepository;


public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
}
