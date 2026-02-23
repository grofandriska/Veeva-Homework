package hu.grofandriska.veeva.service.registration;

import hu.grofandriska.veeva.model.account.Lead;
import hu.grofandriska.veeva.model.registration.RegistrationResult;
import hu.grofandriska.veeva.model.registration.RegistrationServerResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import hu.grofandriska.veeva.model.registration.lead.CreateLeadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;


import java.util.List;

@Service
public class AccountRegistrationService {
    private final String URL;
    private final String leadURL;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AccountRegistrationService(RestTemplate restTemplate,
                                      ObjectMapper objectMapper,
                                      @Value("${vql.url}") String URL,
                                      @Value("${lead.url}") String leadURL) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.leadURL = leadURL;
        this.URL = URL;
    }


    public RegistrationResult register(String sessionId, Lead lead, String vaultId) throws Exception {
        RegistrationServerResponse serverResponse = findAccountByEmail(lead, sessionId);
        if ("WARNING".equals(serverResponse.getResponseStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vault error: " + serverResponse
            );
        }
        if (!"SUCCESS".equals(serverResponse.getResponseStatus())) {
            System.out.println("!Succes!");
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Unexpected Vault response");
        }
        int size = serverResponse.getResponseDetails().getSize();

        if (size == 1) {
            return new RegistrationResult(HttpStatus.OK, vaultId);
        }

        String leadObj = convertLeadToCsv(lead);

        CreateLeadResponse leadResponse;
        leadResponse = createLead(sessionId, leadObj);

        if (leadResponse.getData() == null || leadResponse.getData().isEmpty()) {
            throw new RuntimeException("Invalid Vault response - no data returned");
        }

        var item = leadResponse.getData().get(0);
        System.out.println(item);

        if (!"SUCCESS".equals(leadResponse.getResponseStatus())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Account already exists"
                );
            }


        return new RegistrationResult(HttpStatus.CREATED, vaultId);
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
        ResponseEntity<String> response = restTemplate.postForEntity(URL, request, String.class);
        return objectMapper.readValue(response.getBody(), RegistrationServerResponse.class);
    }

    public CreateLeadResponse createLead(String sessionId, String csvPayload) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(leadURL))
                .header("Authorization", sessionId)
                .header("Content-Type", "text/csv")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(csvPayload, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException(
                    "Vault create failed. HTTP: " + response.statusCode() +
                            " Body: " + response.body()
            );
        }
        return objectMapper.readValue(response.body(), CreateLeadResponse.class);
    }

    public String convertLeadToCsv(Lead lead) {

        StringBuilder sb = new StringBuilder();
        sb.append("email__c,first_name__c,last_name__c\n");

        sb.append(lead.getEmailAddress()).append(",")
                .append(lead.getFirstName()).append(",")
                .append(lead.getLastName());

        return sb.toString();
    }
}
