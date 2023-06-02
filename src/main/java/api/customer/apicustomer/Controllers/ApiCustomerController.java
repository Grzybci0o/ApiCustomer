package api.customer.apicustomer.Controllers;

import api.customer.apicustomer.Services.ApiCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiCustomerController {

    @Autowired
    private ApiCustomerService apiCustomerService;


    @GetMapping(value = "/repositories/{username}")
    public ResponseEntity<Object> getUserRepositories(@PathVariable String username, @RequestHeader(HttpHeaders.ACCEPT) String acceptHeader){
        return apiCustomerService.getUserGitHubRepositories(username, acceptHeader);
    }
}