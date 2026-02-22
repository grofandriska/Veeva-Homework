package hu.grofandriska.veeva.service.vault;

import hu.grofandriska.veeva.model.vault.VaultAuthResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;



@Service
public class VaultAuthService {

    private final String authURL;
    private final String username;
    private final String password;
    private final RestTemplate restTemplate;

    public VaultAuthService(@Value("${auth.url}") String authURL,
                            @Value("${auth.username}") String username,
                            @Value("${auth.password}") String password,
                            RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.username = username;
        this.password = password;
        this.authURL = authURL;
    }

    @Cacheable(value = "vaultSession", sync = true)
    public VaultAuthResponse authenticate() {
        String body = "username=" + username + "&password=" + password;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept((List.of(MediaType.APPLICATION_JSON)));

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            ResponseEntity<VaultAuthResponse> response = restTemplate.postForEntity(authURL, request, VaultAuthResponse.class);

            VaultAuthResponse responseBody = response.getBody();

            if (response.getStatusCode() == HttpStatus.OK && responseBody != null && responseBody.getSessionId() != null ) {
                return responseBody;
            } else {
                throw new RuntimeException("Vault auth failed: " + response.getStatusCode());
            }
    }
}
