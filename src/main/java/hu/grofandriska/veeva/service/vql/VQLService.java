package hu.grofandriska.veeva.service.vql;

import hu.grofandriska.veeva.model.account.Lead;
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
public class VQLService {

    private final String url;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public VQLService(@Value("${vql.url}") String url, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.url = url;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public RegistrationServerResponse findAccountByEmail(Lead lead, String sessionId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", sessionId);
        headers.set("Bearer", sessionId);
        headers.set("X-VaultAPI-DescribeQuery", "true");
        headers.setAccept((List.of(MediaType.APPLICATION_JSON)));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        String safeEmail = lead.getEmailAddress().replace("'", "''");
        String sql = "SELECT email_cda__v,id FROM account__v WHERE email_cda__v = '" + safeEmail + "'";
        body.add("q", sql);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return objectMapper.readValue(response.getBody(), RegistrationServerResponse.class);
    }
}
