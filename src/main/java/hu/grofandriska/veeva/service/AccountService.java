package hu.grofandriska.veeva.service;

import hu.grofandriska.veeva.model.vault.VaultAuthResponse;
import hu.grofandriska.veeva.model.vault.VaultQueryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    private final RestTemplate restTemplate;
    private final String URL;

    public AccountService(RestTemplate restTemplate, @Value("${vql.url}") String URL) {
        this.restTemplate = restTemplate;
        this.URL = URL;
    }

    public String findEmail(String emailAddress, String sessionId) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", sessionId);
        headers.set("X-VaultAPI-DescribeQuery", "true");
        headers.setAccept((List.of(MediaType.APPLICATION_JSON)));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        System.out.println(emailAddress);
        String SQL = "Select email_cda__v,id from account__v WHERE email_cda__v ='" + emailAddress+"'";
        body.add("q", SQL);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        System.out.println(response.getBody());
        /*VaultQueryResponse vaultResponse = response.getBody();
        System.out.println(vaultResponse);

        if (vaultResponse != null) {
            if (validateEmail(vaultResponse,emailAddress)){
                return true;
            }
            else return false;
        }*/
        return response.getBody();
    }

    private boolean validateEmail(VaultQueryResponse vaultResponse, String targetEmail) {
        return vaultResponse.getData().stream()
                .filter(account -> account.getEmail_cda__v() != null)
                .anyMatch(account -> account.getEmail_cda__v().equalsIgnoreCase(targetEmail));
    }

}
