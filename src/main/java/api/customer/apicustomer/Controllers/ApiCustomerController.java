package api.customer.apicustomer.Controllers;
import api.customer.apicustomer.Models.RepositoryInfo;
import api.customer.apicustomer.Services.ApiCustomerService;
import org.springframework.http.HttpHeaders;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiCustomerController {

    private final ApiCustomerService apiCustomerService;

    public ApiCustomerController(ApiCustomerService apiCustomerService) {
        this.apiCustomerService = apiCustomerService;
    }


    @GetMapping(value = "/repositories/{username}")
    public List<RepositoryInfo> getUserRepositories(@PathVariable String username, @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader) {
        return apiCustomerService.getUserGitHubRepositories(username, acceptHeader);
    }
}
