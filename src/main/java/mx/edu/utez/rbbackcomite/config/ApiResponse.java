package mx.edu.utez.rbbackcomite.config;

public class ApiResponse {

    private Object data;
    private boolean error;
    private String message;

    public ApiResponse(Object data, boolean error, String message) {
        this.data = data;
        this.error = error;
        this.message = message;
    }

    public ApiResponse() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
