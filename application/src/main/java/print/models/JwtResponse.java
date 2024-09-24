package print.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse {

    @JsonProperty("access_token")
    private String accessToken;

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}