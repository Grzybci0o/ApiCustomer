package api.customer.apicustomer.Repositories;

import org.springframework.http.ResponseEntity;

public interface UserGitHubRepository {
    ResponseEntity<Object> getReposInfo(String username);

    boolean userExists(String username);
}
