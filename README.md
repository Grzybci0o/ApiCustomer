# GitHub Repositories API Consumer

This application consumes the GitHub API to retrieve a user's repositories that are not forks. It provides the repository name, owner login, branch names, and the last commit SHA for each branch.

## Prerequisites

- Java 17
- Maven
- Spring Boot 3.1.0

## Getting Started

1. Clone the repository: `git clone https://github.com/Grzybci0o/ApiCustomer`
2. Navigate to the project directory: `cd \apicustomer\apicustomer`
3. Build the application: `mvn clean install`
4. Run the application: `java -jar .\target\apicustomer-0.0.1-SNAPSHOT.jar`
5. Access the application in your browser at: `http://localhost:8080/api/repositories/{username}`
6. You can acces the application via Postman at: `http://localhost:8080/api/repositories/{username}`
## API Endpoints

### Get User's GitHub Repositories

- Endpoint: `/api/repositories/{username}`
- Method: GET
- Parameters:
    - `username`: The GitHub username for which to retrieve repositories
- Headers:
    - `Accept: application/json`

## Example Usage

1. Send a GET request to `http://localhost:8080/api/repositories/{username}`, replacing `{username}` with the desired GitHub username.
2. The response will contain the user's GitHub repositories that are not forks, including the repository name, owner login, branch names, and the last commit SHA for each branch.

## Notes

- This application uses the GitHub API v3 as a backing API.
- Ensure that the provided GitHub username exists and is accessible via the API.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvement, please open an issue or submit a pull request.
