package hu.grofandriska.veeva.service;

import hu.grofandriska.veeva.model.account.response.AccountResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class AccountService {
    private final RestTemplate restTemplate;
    private final String URL;

    public AccountService(RestTemplate restTemplate, @Value("${vql.url}") String URL) {
        this.restTemplate = restTemplate;
        this.URL = URL;
    }

    public AccountResponse findEmail(String emailAddress, String sessionId) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", sessionId);
        headers.set("X-VaultAPI-DescribeQuery", "true");
        headers.setAccept((List.of(MediaType.APPLICATION_JSON)));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        System.out.println(emailAddress);
        String SQL = "Select email_cda__v,id from account__v WHERE email_cda__v ='" + emailAddress + "'";
        body.add("q", SQL);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);

        ObjectMapper mapper = new ObjectMapper();
        AccountResponse accountResponse = mapper.readValue(response.getBody(), AccountResponse.class);
        System.out.println("RAW RESPONSE BODY: " + response.getBody());
        System.out.println("--------------------");

        return accountResponse;
    }
}
