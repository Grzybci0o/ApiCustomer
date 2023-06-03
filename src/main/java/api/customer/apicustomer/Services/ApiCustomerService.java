package api.customer.apicustomer.Services;

import api.customer.apicustomer.Exception.InvalidHeaderException;
import api.customer.apicustomer.Exception.UserNotFoundException;
import api.customer.apicustomer.Models.RepositoryInfo;
import api.customer.apicustomer.Repositories.UserGitHubRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiCustomerService {

    private final UserGitHubRepository userGitHubRepository;

    public ApiCustomerService(UserGitHubRepository userGitHubRepository) {
        this.userGitHubRepository = userGitHubRepository;
    }

    public List<RepositoryInfo> getUserGitHubRepositories(String username, String acceptHeader) {
        if (!acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            throw new InvalidHeaderException("The requested response format is not supported. Please use Accept: application/json!");
        }

        if (!userGitHubRepository.userExists(username)) {
            throw new UserNotFoundException("GitHub user not found!");
        }

        return userGitHubRepository.getReposInfo(username);
    }
}

