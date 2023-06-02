package api.customer.apicustomer.Error;

public class ErrorResponse {
    int status;
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
