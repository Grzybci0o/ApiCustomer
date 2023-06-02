package api.customer.apicustomer.Models;

import lombok.Data;

@Data
public class BranchInfo {
    private String name;
    private String lastCommitSha;

    public BranchInfo(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }
}
