package api.customer.apicustomer.Models;

import lombok.Data;

import java.util.List;

@Data
public class RepositoryInfo {
    private String name;
    private String owner;
    private List<BranchInfo> branches;

    public RepositoryInfo(String name, String owner, List<BranchInfo> branches) {
        this.name = name;
        this.owner = owner;
        this.branches = branches;
    }
}