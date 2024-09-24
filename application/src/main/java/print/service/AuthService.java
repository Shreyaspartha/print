package print.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import print.models.JwtResponse;
import print.utils.VcapProperties;

import java.util.Base64;
import java.util.Objects;

@Service
public class AuthService {
    @Autowired
    private VcapProperties vcapProperties;

    @Autowired
    private RestTemplate restTemplate;

    public String getToken() {
        String url = String.format("https://%s.%s/oauth/token?grant_type=client_credentials", vcapProperties.getSubscriberDomain(), vcapProperties.getUaaDomain());

        // Set the Authorization header (clientId:clientSecret in base64)
        String basicAuth = Base64.getEncoder().encodeToString((vcapProperties.getClientId() + ":" + vcapProperties.getClientSecret()).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + basicAuth);

        // Create HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Execute the GET request
        ResponseEntity<JwtResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, JwtResponse.class);

        return Objects.requireNonNull(response.getBody()).getAccessToken();
    }
}