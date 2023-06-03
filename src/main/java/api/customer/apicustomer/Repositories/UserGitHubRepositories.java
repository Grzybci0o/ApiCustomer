package api.customer.apicustomer.Repositories;

import api.customer.apicustomer.Exception.RetriveRepositoriesException;
import api.customer.apicustomer.Models.BranchInfo;
import api.customer.apicustomer.Models.GitHubBranch;
import api.customer.apicustomer.Models.GitHubRepository;
import api.customer.apicustomer.Models.RepositoryInfo;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserGitHubRepositories implements UserGitHubRepository {

    private static final String USER_REPOS_URL = "https://api.github.com/users/";
    private static final String REPOS_URL = "https://api.github.com/repos/";

    private final RestTemplate restTemplate;

    public UserGitHubRepositories(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RepositoryInfo> getReposInfo(String username) {
        String url = USER_REPOS_URL + username + "/repos";
        GitHubRepository[] repositories = restTemplate.getForObject(url, GitHubRepository[].class);

        if (repositories != null) {
            List<RepositoryInfo> repositoryInfos = new ArrayList<>();
            for (GitHubRepository repository : repositories) {
                if (!repository.isFork()) {
                    List<BranchInfo> branchInfos = getBranches(repository.getOwner().getLogin(), repository.getName());
                    repositoryInfos.add(new RepositoryInfo(repository.getName(), repository.getOwner().getLogin(), branchInfos));
                }
            }
            return repositoryInfos;
        } else {
            throw new RetriveRepositoriesException("An error occurred while retrieving the repositories.");
        }
    }

    @Override
    public boolean userExists(String username) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + username))
                .build();

        try {
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() == 200;
        } catch (Exception ex) {
            return false;
        }
    }

    private List<BranchInfo> getBranches(String owner, String repository) {
        String url = REPOS_URL + owner + "/" + repository + "/branches";
        GitHubBranch[] branches = restTemplate.getForObject(url, GitHubBranch[].class);

        List<BranchInfo> branchInfos = new ArrayList<>();
        if (branches != null) {
            for (GitHubBranch branch : branches) {
                String commitSha = getLastCommitSha(owner, repository, branch.getName());
                branchInfos.add(new BranchInfo(branch.getName(), commitSha));
            }
        }
        return branchInfos;
    }

    private String getLastCommitSha(String owner, String repository, String branch) {
        String url = REPOS_URL + owner + "/" + repository + "/branches/" + branch;
        GitHubBranch branchData = restTemplate.getForObject(url, GitHubBranch.class);

        if (branchData != null && branchData.getCommit() != null) {
            return branchData.getCommit().getSha();
        }
        return null;
    }
}
