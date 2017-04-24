package jj.mstest.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersistenceService {
    @Autowired
    public PersistenceService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    private CredentialsRepository credentialsRepository;

    public Credentials saveCredentials(Credentials credentials){
        credentials.setId(1L);
        return credentialsRepository.save(credentials);
    }

    public Credentials getCredentials() {
        return credentialsRepository.findOne(1L);
    }
}
