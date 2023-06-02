package api.customer.apicustomer.Models;

import lombok.Data;

@Data
public class GitHubRepository {
    private String name;
    private boolean fork;
    private Owner owner;
}