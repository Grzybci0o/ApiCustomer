package api.customer.apicustomer.Repositories;

import api.customer.apicustomer.Models.RepositoryInfo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserGitHubRepository {
    List<RepositoryInfo> getReposInfo(String username);

    boolean userExists(String username);
}

