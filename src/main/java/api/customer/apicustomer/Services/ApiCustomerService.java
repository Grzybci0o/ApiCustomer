package api.customer.apicustomer.Services;

import api.customer.apicustomer.Error.ErrorResponse;
import api.customer.apicustomer.Repositories.UserGitHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ApiCustomerService {

    @Autowired
    private UserGitHubRepository userGitHubRepository;

    public ResponseEntity<Object> getUserGitHubRepositories(String username, String acceptHeader) {
        if (!acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
            errorResponse.setMessage("The requested response format is not supported. Please use Accept: application/json!");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!userGitHubRepository.userExists(username)) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
            errorResponse.setMessage("GitHub user not found!");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return userGitHubRepository.getReposInfo(username);
    }
}
