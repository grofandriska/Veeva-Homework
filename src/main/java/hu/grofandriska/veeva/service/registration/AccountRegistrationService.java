package hu.grofandriska.veeva.service.registration;

import hu.grofandriska.veeva.model.registration.RegistrationServerResponse;

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
public class AccountRegistrationService {
    private final String URL;
    private final RestTemplate restTemplate;

    public AccountRegistrationService(RestTemplate restTemplate,
                                      @Value("${vql.url}") String URL) {
        this.restTemplate = restTemplate;
        this.URL = URL;
    }

    public RegistrationServerResponse finAccountByEmail(String emailAddress, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", sessionId);
        headers.set("Bearer", sessionId);
        headers.set("X-VaultAPI-DescribeQuery", "true");
        headers.setAccept((List.of(MediaType.APPLICATION_JSON)));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String SQL = "Select email_cda__v,id from account__v WHERE email_cda__v ='" + emailAddress + "'";
        body.add("q", SQL);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        System.out.println(response);
        ObjectMapper mapper = new ObjectMapper();;
        return mapper.readValue(response.getBody(), RegistrationServerResponse.class);
    }
}
