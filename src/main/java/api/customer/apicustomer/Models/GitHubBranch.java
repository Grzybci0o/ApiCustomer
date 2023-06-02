package api.customer.apicustomer.Models;

import lombok.Data;

@Data
public class GitHubBranch {
    private String name;
    private Commit commit;
}
