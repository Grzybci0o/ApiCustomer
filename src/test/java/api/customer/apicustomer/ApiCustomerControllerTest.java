package api.customer.apicustomer;

import api.customer.apicustomer.Controllers.ApiCustomerController;
import api.customer.apicustomer.Exception.InvalidHeaderException;
import api.customer.apicustomer.Exception.UserNotFoundException;
import api.customer.apicustomer.Models.RepositoryInfo;
import api.customer.apicustomer.Services.ApiCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiCustomerControllerTest {

    @Mock
    private ApiCustomerService apiCustomerService;

    private ApiCustomerController apiCustomerController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        apiCustomerController = new ApiCustomerController(apiCustomerService);
        mockMvc = MockMvcBuilders.standaloneSetup(apiCustomerController).build();
    }

    @Test
    public void testGetUserRepositories_ValidInput_ReturnsRepositoryInfoList() throws Exception {
        // Given
        String username = "john_doe";
        String acceptHeader = "application/json";
        List<RepositoryInfo> expectedRepositoryInfoList = Arrays.asList(
                new RepositoryInfo("repo1", "john_doe", null),
                new RepositoryInfo("repo2", "john_doe", null)
        );

        when(apiCustomerService.getUserGitHubRepositories(username, acceptHeader)).thenReturn(expectedRepositoryInfoList);

        // When/Then
        mockMvc.perform(get("/api/repositories/{username}", username)
                        .header(HttpHeaders.ACCEPT, acceptHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("repo1"))
                .andExpect(jsonPath("$[0].owner").value("john_doe"))
                .andExpect(jsonPath("$[0].branches").isEmpty())
                .andExpect(jsonPath("$[1].name").value("repo2"))
                .andExpect(jsonPath("$[1].owner").value("john_doe"))
                .andExpect(jsonPath("$[1].branches").isEmpty());

        verify(apiCustomerService, times(1)).getUserGitHubRepositories(username, acceptHeader);
    }

    @Test
    public void testGetUserRepositoriesInvalidAcceptHeaderThrowsInvalidHeaderException() {
        // Given
        String username = "Grzegorz Brzeczyszczykiewicz";
        String acceptHeader = "application/xml";

        when(apiCustomerService.getUserGitHubRepositories(username, acceptHeader))
                .thenThrow(new InvalidHeaderException("Invalid Accept Header"));

        // When/Then
        assertThrows(InvalidHeaderException.class,
                () -> apiCustomerController.getUserRepositories(username, acceptHeader));

        verify(apiCustomerService, times(1)).getUserGitHubRepositories(username, acceptHeader);
    }

    @Test
    public void testGetUserRepositoriesUserNotFoundThrowsUserNotFoundException() {
        // Given
        String username = "Grzegorz Brzeczyszczykiewicz";
        String acceptHeader = "application/json";

        when(apiCustomerService.getUserGitHubRepositories(username, acceptHeader))
                .thenThrow(new UserNotFoundException("User not found"));

        // When/Then
        assertThrows(UserNotFoundException.class,
                () -> apiCustomerController.getUserRepositories(username, acceptHeader));

        verify(apiCustomerService, times(1)).getUserGitHubRepositories(username, acceptHeader);
    }
}

